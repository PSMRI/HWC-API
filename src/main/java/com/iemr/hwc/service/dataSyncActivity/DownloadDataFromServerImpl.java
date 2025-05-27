/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.hwc.service.dataSyncActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.iemr.hwc.data.syncActivity_syncLayer.MasterDownloadDataDigester;
import com.iemr.hwc.data.syncActivity_syncLayer.SyncDownloadMaster;
import com.iemr.hwc.data.syncActivity_syncLayer.TempVan;
import com.iemr.hwc.repo.syncActivity_syncLayer.SyncDownloadMasterRepo;
import com.iemr.hwc.repo.syncActivity_syncLayer.TempVanRepo;
import com.iemr.hwc.utils.RestTemplateUtil;
import com.iemr.hwc.utils.mapper.InputMapper;

@Service
@PropertySource("classpath:application.properties")
public class DownloadDataFromServerImpl implements DownloadDataFromServer {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	// rest URLs from server to download master data for van
	@Value("${dataSyncDownloadUrl}")
	private String dataSyncDownloadUrl;
	@Autowired
	private SyncDownloadMasterRepo syncDownloadMasterRepo;
	@Autowired
	private DataSyncRepository dataSyncRepository;
	@Autowired
	private TempVanRepo tempVanRepo;

	private static int progressCounter = 0;
	private static int totalCounter = 0;
	private static StringBuilder failedMasters;

	// private static int successCounter;
	private static int failedCounter;
	// private static int downloadProgress;

	/**
	 * 
	 * @return
	 * @throws Exception Masters download in van from central server
	 */
	public String downloadMasterDataFromServer(String ServerAuthorization, Integer vanID, Integer psmID)
			throws Exception {

		if (totalCounter != progressCounter) {
			return "inProgress";
		}

		String successFlag = " Master download started ";

		ArrayList<SyncDownloadMaster> downloadMasterList = syncDownloadMasterRepo.getDownloadTables();

		totalCounter = downloadMasterList.size();
		progressCounter = 0;

		failedMasters = new StringBuilder();
		// successCounter = 0;
		failedCounter = 0;

		final ExecutorService threadPool = Executors.newFixedThreadPool(3);
		threadPool.submit(new Callable<String>() {
			@Override
			public String call() {

				if (downloadMasterList != null && downloadMasterList.size() > 0) {
					for (SyncDownloadMaster table : downloadMasterList) {
						try {
							table.setVanID(vanID);
							table.setProviderServiceMapID(psmID);

							int i = downloadDataFromServer(table, ServerAuthorization);
							if (i > 0) {
								// successCounter++;
							} else {
								failedCounter++;
								failedMasters.append(table.getTableName() + " | ");
								logger.error(
										"Download failed for " + table.getSchemaName() + "." + table.getTableName());
							}
						} catch (Exception e) {
							failedCounter++;
							failedMasters.append(table.getTableName() + " | ");
							logger.error("Download failed for " + table.getSchemaName() + "." + table.getTableName()
									+ ". Exception : " + e);
						}

						progressCounter++;
					}
				}
				return "Master download completed";
			}

		});

		// if (downloadMasterList != null && downloadMasterList.size() > 0) {
		// for (SyncDownloadMaster table : downloadMasterList) {
		// try {
		// table.setVanID(vanID);
		// table.setProviderServiceMapID(psmID);
		//
		// int i = downloadDataFromServer(table, ServerAuthorization);
		// if (i > 0) {
		// successCounter++;
		// } else {
		// failedCounter++;
		// failedMasters.append(table.getTableName() + " | ");
		// logger.error("Download failed for " + table.getSchemaName() + "." +
		// table.getTableName());
		// }
		// } catch (Exception e) {
		// failedCounter++;
		// failedMasters.append(table.getTableName() + " | ");
		// logger.error("Download failed for " + table.getSchemaName() + "." +
		// table.getTableName()
		// + ". Exception : " + e);
		// }
		//
		// progressCounter++;
		// }
		// }
		return successFlag;
	}

	private int downloadDataFromServer(SyncDownloadMaster syncDownloadMaster, String ServerAuthorization)
			throws Exception {
		int successFlag = 0;

		// initializing RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<Object> request = RestTemplateUtil.createRequestEntity(syncDownloadMaster, ServerAuthorization);

		// Call rest-template to call API to download master data for given table
		ResponseEntity<String> response = restTemplate.exchange(dataSyncDownloadUrl, HttpMethod.POST, request,
				String.class);

		// Check if API call is success
		if (response != null && response.hasBody()) {
			JSONObject obj = new JSONObject(response.getBody());
			if (obj != null && obj.has("data") && obj.has("statusCode") && obj.getInt("statusCode") == 200) {
				// JSONArray dataList = obj.getJSONArray("data");
				// Consume the response from API in master data digester
				MasterDownloadDataDigester masterDownloadDataDigester = InputMapper.gson().fromJson(response.getBody(),
						MasterDownloadDataDigester.class);
				if (masterDownloadDataDigester != null) {
					int i = updateMastersWithLatestData(masterDownloadDataDigester, syncDownloadMaster);
					if (i > 0)
						successFlag = 1;
				} else {
					// error in parsing response data
				}
			} else {
				// error in API call
			}
		} else {
			// response is null or not valid
		}
		return successFlag;
	}

	private int updateMastersWithLatestData(MasterDownloadDataDigester masterDownloadDataDigester,
			SyncDownloadMaster syncDownloadMaster) {
		int successFlag = 0;
		// get master data in required format
		List<Object[]> masterDataList = getMasterDataInFormatToInsertToDB(masterDownloadDataDigester);
		// get query to insert/update data in local db
		String query = getQueryToInsertUpdateMasterInLocalDB(syncDownloadMaster);

		if (masterDataList != null && query != null) {
			int[] i = dataSyncRepository.updateLatestMasterInLocal(query, masterDataList);
			if (i.length == masterDataList.size()) {
				int j = syncDownloadMasterRepo.updateTableSyncDownloadMasterForLastDownloadDate(
						syncDownloadMaster.getDownloadMasterTableID());
				if (j > 0)
					successFlag = 1;
			}
		}

		return successFlag;
	}

	private String getQueryToInsertUpdateMasterInLocalDB(SyncDownloadMaster syncDownloadMaster) {
		String[] columnsArr = null;
		if (syncDownloadMaster != null && syncDownloadMaster.getVanColumnName() != null)
			columnsArr = syncDownloadMaster.getVanColumnName().split(",");

		StringBuilder preparedStatementSetter = new StringBuilder();
		StringBuilder updateStatement = new StringBuilder();

		// temp code pointing to diff target schema
		// syncDownloadMaster.setSchemaName("db_iemr_sync");

		if (columnsArr != null && columnsArr.length > 0) {
			int index = 0;
			for (String column : columnsArr) {
				if (index == columnsArr.length - 1) {
					preparedStatementSetter.append(" ? ");
					updateStatement.append(column + "=VALUES(" + column + ")");
				} else {
					preparedStatementSetter.append(" ?, ");
					updateStatement.append(column + "=VALUES(" + column + "),");
				}
				index++;
			}
		}

		String query = "";
		if (syncDownloadMaster != null)
			query = " INSERT INTO " + syncDownloadMaster.getSchemaName() + "." + syncDownloadMaster.getTableName()
					+ "( " + syncDownloadMaster.getVanColumnName() + ") VALUES ( " + preparedStatementSetter
					+ " ) ON DUPLICATE KEY UPDATE " + updateStatement;

		return query;
	}

	private List<Object[]> getMasterDataInFormatToInsertToDB(MasterDownloadDataDigester masterDownloadDataDigester) {
		// get master data in the form of list of map of string & object
		List<Map<String, Object>> masterList = masterDownloadDataDigester.getData();
		// master data 'list of object array'
		List<Object[]> masterDataList = new ArrayList<>();

		Object[] objArr;

		int pointer;
		for (Map<String, Object> map : masterList) {
			pointer = 0;
			objArr = new Object[map.size()];
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					if (String.valueOf(entry.getValue()).equalsIgnoreCase("false")
							|| String.valueOf(entry.getValue()).equalsIgnoreCase("true"))
						objArr[pointer] = entry.getValue();
					else
						objArr[pointer] = String.valueOf(entry.getValue());
				} else
					objArr[pointer] = entry.getValue();

				pointer++;
			}
			masterDataList.add(objArr);
		}

		return masterDataList;
	}

	public String getVanDetailsForMasterDownload() throws Exception {
		List<TempVan> dataSyncGroupList = tempVanRepo.getVanID();
		if (dataSyncGroupList != null && dataSyncGroupList.size() == 1) {
			return new Gson().toJson(dataSyncGroupList.get(0));
		} else {
			throw new Exception("There are more than 1 van available. Kindly contact the administrator.");
		}
	}

	public Map<String, Object> getDownloadStatus() {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("percentage", Math
				.floor(((DownloadDataFromServerImpl.progressCounter * 100) / DownloadDataFromServerImpl.totalCounter)));
		resultMap.put("failedMasterCount", DownloadDataFromServerImpl.failedCounter);
		resultMap.put("failedMasters", DownloadDataFromServerImpl.failedMasters);

		return resultMap;
	}
}
