package com.iemr.mmu.service.dataSyncActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.mmu.data.syncActivity_syncLayer.DataSyncGroups;
import com.iemr.mmu.data.syncActivity_syncLayer.SyncUtilityClass;
import com.iemr.mmu.repo.syncActivity_syncLayer.DataSyncGroupsRepo;

/***
 * 
 * @author NE298657
 * @date 16-08-2018
 * @purpose "This service is user for data sync activity from van side. Means
 *          taking unprocessed data from van and sync to server and based on
 *          success or failure update local tables processed flag"
 *
 */
@Service
@PropertySource("classpath:application.properties")
public class UploadDataToServerImpl implements UploadDataToServer {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	// rest URLs from server to consume local van data and to sync to DB server
	@Value("${dataSyncUploadUrl}")
	private String dataSyncUploadUrl;

	@Autowired
	private DataSyncRepository dataSyncRepository;
	@Autowired
	private DataSyncGroupsRepo dataSyncGroupsRepo;

	// batch size for data upload
	private static final int BATCH_SIZE = 30;

	/**
	 * 
	 * @param groupName
	 * @param Authorization
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public String getDataToSyncToServer(Integer groupID, String user, String Authorization) throws Exception {

		String syncData = null;
		syncData = syncIntercepter(groupID, user, Authorization);

		return syncData;
	}

	/**
	 * 
	 * @param Authorization
	 * @return
	 */
	public String syncIntercepter(Integer groupID, String user, String Authorization) throws Exception {

		// sync activity trigger
		String serverAcknowledgement = startDataSync(groupID, user, Authorization);

		return serverAcknowledgement;
	}

	/**
	 * 
	 * @param syncTableDetailsIDs
	 * @param Authorization
	 * @return
	 */

	private String startDataSync(Integer groupID, String user, String Authorization) throws Exception {
		String serverAcknowledgement = null;
		// fetch table-name, van-side-columns, server-side-columns
		List<SyncUtilityClass> syncUtilityClassList = getVanAndServerColumns(groupID);
		List<Map<String, Object>> syncData;
		List<Map<String, Object>> syncDataBatch;
		for (SyncUtilityClass obj : syncUtilityClassList) {
			// get data from DB to sync to server
			syncData = getDataToSync(obj.getSchemaName(), obj.getTableName(), obj.getVanColumnName());
			// System.out.println(new Gson().toJson(syncData));
			if (syncData != null && syncData.size() > 0) {
				int dataSize = syncData.size();
				int startIndex = 0;
				int fullBatchCount = dataSize / BATCH_SIZE;
				int remainder = dataSize % BATCH_SIZE;

				// sync data to server for batches
				for (int i = 0; i < fullBatchCount; i++) {
					// get data for each batch
					syncDataBatch = getBatchOfAskedSizeDataToSync(syncData, startIndex, BATCH_SIZE);
					// for each batch sync data to central server
					serverAcknowledgement = syncDataToServer(obj.getSchemaName(), obj.getTableName(),
							obj.getVanAutoIncColumnName(), obj.getServerColumnName(), syncDataBatch, user,
							Authorization);
					startIndex += BATCH_SIZE;
				}
				// sync data to server for rest data left from batch
				if (remainder > 0) {
					// get data for extra data from batch
					syncDataBatch = getBatchOfAskedSizeDataToSync(syncData, startIndex, remainder);
					// for extra data from batch sync data to central server
					serverAcknowledgement = syncDataToServer(obj.getSchemaName(), obj.getTableName(),
							obj.getVanAutoIncColumnName(), obj.getServerColumnName(), syncDataBatch, user,
							Authorization);
				}

			} else {
				// nothing to sync
				serverAcknowledgement = "Data successfully synced";
			}
		}
		return serverAcknowledgement;
	}

	/**
	 * 
	 * @param syncTableDetailsIDs
	 * @return
	 */

	private List<SyncUtilityClass> getVanAndServerColumns(Integer groupID) throws Exception {
		List<SyncUtilityClass> syncUtilityClassList = dataSyncRepository.getVanAndServerColumnList(groupID);
		return syncUtilityClassList;
	}

	/**
	 * 
	 * @param schemaName
	 * @param tableName
	 * @param columnNames
	 * @return
	 */

	private List<Map<String, Object>> getDataToSync(String schemaName, String tableName, String columnNames)
			throws Exception {
		List<Map<String, Object>> resultSetList = dataSyncRepository.getDataForGivenSchemaAndTable(schemaName,
				tableName, columnNames);
		return resultSetList;
	}

	/**
	 * 
	 * @param syncData
	 * @param startIndex
	 * @param size
	 * @return
	 */

	private List<Map<String, Object>> getBatchOfAskedSizeDataToSync(List<Map<String, Object>> syncData, int startIndex,
			int size) throws Exception {
		List<Map<String, Object>> syncDataOfBatchSize = syncData.subList(startIndex, (startIndex + size));
		return syncDataOfBatchSize;
	}

	/**
	 * 
	 * @param schemaName
	 * @param tableName
	 * @param vanAutoIncColumnName
	 * @param serverColumns
	 * @param dataToBesync
	 * @param Authorization
	 * @return
	 */

	public String syncDataToServer(String schemaName, String tableName, String vanAutoIncColumnName,
			String serverColumns, List<Map<String, Object>> dataToBesync, String user, String Authorization)
			throws Exception {

		RestTemplate restTemplate = new RestTemplate();

		// serialize null
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.serializeNulls();
		Gson gson = gsonBuilder.create();

		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("schemaName", schemaName);
		dataMap.put("tableName", tableName);
		dataMap.put("vanAutoIncColumnName", vanAutoIncColumnName);
		dataMap.put("serverColumns", serverColumns);
		dataMap.put("syncData", dataToBesync);
		dataMap.put("syncedBy", user);

		String requestOBJ = gson.toJson(dataMap);

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		headers.add("AUTHORIZATION", Authorization);
		HttpEntity<Object> request = new HttpEntity<Object>(requestOBJ, headers);

		ResponseEntity<String> response = restTemplate.exchange(dataSyncUploadUrl, HttpMethod.POST, request,
				String.class);

		/**
		 * if data successfully synced then getVanSerialNo of synced data to update
		 * processed flag
		 */
		int i = 0;
		if (response != null && response.hasBody()) {
			JSONObject obj = new JSONObject(response.getBody());
			if (obj != null && obj.has("statusCode") && obj.getInt("statusCode") == 200) {
				StringBuilder vanSerialNos = getVanSerialNoListForSyncedData(vanAutoIncColumnName, dataToBesync);
				// update table for processed flag = "P"
				logger.info(schemaName + "|" + tableName + "|" + vanSerialNos.toString() + "|" + vanAutoIncColumnName
						+ "|" + user);
				i = dataSyncRepository.updateProcessedFlagInVan(schemaName, tableName, vanSerialNos,
						vanAutoIncColumnName, user);
			}
		}
		if (i > 0)
			return "Data successfully synced";
		else
			return null;
	}

	/**
	 * 
	 * @param vanAutoIncColumnName
	 * @param dataToBesync
	 * @return
	 */

	public StringBuilder getVanSerialNoListForSyncedData(String vanAutoIncColumnName,
			List<Map<String, Object>> dataToBesync) throws Exception {
		// comma separated van serial no
		StringBuilder vanSerialNos = new StringBuilder();

		int pointer1 = 0;
		for (Map<String, Object> map : dataToBesync) {
			if (pointer1 == dataToBesync.size() - 1)
				vanSerialNos.append(map.get(vanAutoIncColumnName.trim()));
			else
				vanSerialNos.append(map.get(vanAutoIncColumnName.trim()) + ",");

			pointer1++;
		}
		return vanSerialNos;
	}

	public String getDataSyncGroupDetails() {
		List<DataSyncGroups> dataSyncGroupList = dataSyncGroupsRepo.findByDeleted(false);
		if (dataSyncGroupList != null)
			return new Gson().toJson(dataSyncGroupList);
		else
			return null;
	}

}
