package com.iemr.mmu.utils.km.openkm;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iemr.mmu.utils.config.ConfigProperties;
import com.iemr.mmu.utils.km.KMService;
import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.bean.Document;
import com.openkm.sdk4j.bean.Folder;
import com.openkm.sdk4j.exception.AccessDeniedException;
import com.openkm.sdk4j.exception.AutomationException;
import com.openkm.sdk4j.exception.DatabaseException;
import com.openkm.sdk4j.exception.ExtensionException;
import com.openkm.sdk4j.exception.FileSizeExceededException;
import com.openkm.sdk4j.exception.ItemExistsException;
import com.openkm.sdk4j.exception.LockException;
import com.openkm.sdk4j.exception.PathNotFoundException;
import com.openkm.sdk4j.exception.RepositoryException;
import com.openkm.sdk4j.exception.UnknowException;
import com.openkm.sdk4j.exception.UnsupportedMimeTypeException;
import com.openkm.sdk4j.exception.UserQuotaExceededException;
import com.openkm.sdk4j.exception.VersionException;
import com.openkm.sdk4j.exception.VirusDetectedException;
import com.openkm.sdk4j.exception.WebserviceException;

public class OpenKMServiceImpl implements KMService {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	// private ConfigProperties configProperties;
	//
	// @Autowired
	// public void setConfigProperties(ConfigProperties configProperties)
	// {
	// this.configProperties = configProperties;
	// }

	private static String url;
	private static String username;
	private static String password;
	private static String kmRootPath;
	private static String guestUser;
	private static String guestPassword;

	public OpenKMServiceImpl() {
	}

	private static OKMWebservices connector = null;

	public void init() {
		if (connector == null) {
			url = ConfigProperties.getPropertyByName("km-base-url");
			username = ConfigProperties.getPropertyByName("km-username");
			password = ConfigProperties.getPropertyByName("km-password");
			kmRootPath = ConfigProperties.getPropertyByName("km-root-path");
			guestUser = ConfigProperties.getPropertyByName("km-guest-user");
			guestPassword = ConfigProperties.getPropertyByName("km-guest-password");
			connector = OpenKMConnector.initialize(url, username, password);
		}
	}

	@Override
	public String getDocumentRoot() {
		String response = null;
		this.init();
		try {
			Folder rootFolder = connector.getRootFolder();
			response = rootFolder.getPath();
		} catch (PathNotFoundException | RepositoryException | DatabaseException | UnknowException
				| WebserviceException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return response;
	}

	public String uploadFile() {
		String response = null;
		this.init();
		try {
			response = connector.checkin("", new FileInputStream(""), "").toString();
		} catch (FileSizeExceededException | UserQuotaExceededException | VirusDetectedException | LockException
				| VersionException | PathNotFoundException | AccessDeniedException | RepositoryException | IOException
				| DatabaseException | ExtensionException | AutomationException | UnknowException
				| WebserviceException e) {
			logger.error(e.getMessage());
		}
		return response;
	}

	@Override
	public String createDocument(String documentPath, String filepath) {
		String response = null;
		this.init();
		try {
			FileInputStream file = new FileInputStream(filepath);
			Document doc = connector.createDocumentSimple(kmRootPath + documentPath, file);
			response = doc.getUuid();
		} catch (PathNotFoundException e) {
			createFolder(e.getMessage());
			response = createDocument(documentPath, filepath);
		} catch (IOException | UnsupportedMimeTypeException | FileSizeExceededException | UserQuotaExceededException
				| VirusDetectedException | ItemExistsException | AccessDeniedException | RepositoryException
				| DatabaseException | ExtensionException | AutomationException | UnknowException
				| WebserviceException e) {
			logger.error(e.getMessage());
		}
		return response;
	}

	public String createFolder(String path) {
		String response = null;
		this.init();
		String[] folders = path.split("/");
		String basePath = "";
		try {
			for (int index = 1; index < folders.length - 1; index++) {
				basePath += "/" + folders[index];
				Folder folder = new Folder();
				folder.setPath(basePath + "/" + folders[index + 1]);
				List<Folder> childrens = connector.getFolderChildren(basePath);
				boolean exists = false;
				for (Folder folder2 : childrens) {
					if (folder2.getPath().toString().equals(folder.getPath().toString())) {
						exists = true;
						break;
					}
				}
				createFolder(folder.getPath(), exists);
			}
		} catch (PathNotFoundException | AccessDeniedException | RepositoryException | ItemExistsException
				| DatabaseException | ExtensionException | AutomationException | UnknowException
				| WebserviceException e) {
			logger.error(e.getMessage());
		}
		return response;
	}

	private void createFolder(String path, boolean exists)
			throws AccessDeniedException, RepositoryException, PathNotFoundException, ItemExistsException,
			DatabaseException, ExtensionException, AutomationException, UnknowException, WebserviceException {
		if (!exists) {
			Folder folder = connector.createFolderSimple(path);
		}
	}

	public String deleteDocument(String documentID) {
		String response = "failure";
		this.init();
		try {
			connector.deleteDocument(documentID);
			response = "success";
		} catch (AccessDeniedException | RepositoryException | PathNotFoundException | LockException | DatabaseException
				| ExtensionException | UnknowException | WebserviceException e) {
			logger.error(e.getMessage());
		}
		return response;
	}
}
