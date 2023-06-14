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
package com.iemr.mmu.service.dataSyncLayerCentral;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.mmu.data.syncActivity_syncLayer.SyncUploadDataDigester;
import com.iemr.mmu.utils.mapper.InputMapper;

/***
 * 
 * @author NE298657
 *
 */

@Service
public class GetDataFromVanAndSyncToDBImpl implements GetDataFromVanAndSyncToDB {

	@Autowired
	private DataSyncRepositoryCentral dataSyncRepositoryCentral;

	public String syncDataToServer(String requestOBJ, String Authorization) throws Exception {

		// feed sync request
		SyncUploadDataDigester syncUploadDataDigester = InputMapper.gson().fromJson(requestOBJ,
				SyncUploadDataDigester.class);
		if (syncUploadDataDigester != null && syncUploadDataDigester.getTableName() != null
				&& syncUploadDataDigester.getTableName().equalsIgnoreCase("m_beneficiaryregidmapping")) {
			String s = update_M_BeneficiaryRegIdMapping_for_provisioned_benID(syncUploadDataDigester);
			return s;
		} else {

			List<Map<String, Object>> dataToBesync = syncUploadDataDigester.getSyncData();

			Object[] objArr;

			// sync data 'list of object array'
			List<Object[]> syncDataListInsert = new ArrayList<>();
			List<Object[]> syncDataListUpdate = new ArrayList<>();

			int pointer;
			String vanSerialNo;
			String vanID;
			int recordCheck;
			for (Map<String, Object> map : dataToBesync) {
				pointer = 0;
				recordCheck = 0;
				vanSerialNo = "";
				vanID = "";

				vanSerialNo = String.valueOf(map.get(syncUploadDataDigester.getVanAutoIncColumnName()));
				vanID = String.valueOf(map.get("VanID"));

				map.replace("SyncedBy", syncUploadDataDigester.getSyncedBy());
				map.replace("SyncedDate", String.valueOf(LocalDateTime.now()));

				recordCheck = dataSyncRepositoryCentral.checkRecordIsAlreadyPresentOrNot(
						syncUploadDataDigester.getSchemaName(), syncUploadDataDigester.getTableName(), vanSerialNo,
						vanID, syncUploadDataDigester.getVanAutoIncColumnName());

				if (recordCheck == 0) {
					objArr = new Object[map.size()];
				} else {
					objArr = new Object[map.size() + 2];
				}

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

				if (recordCheck == 0) {
					syncDataListInsert.add(objArr);
				} else {
					objArr[pointer] = String.valueOf(map.get(syncUploadDataDigester.getVanAutoIncColumnName()));
					objArr[pointer + 1] = String.valueOf(map.get("VanID"));
					syncDataListUpdate.add(objArr);
				}

			}

			int[] i = null;
			if (syncDataListInsert != null && syncDataListInsert.size() > 0) {
				// schema name hard coded(Insert query builder)
				String queryInsert = getQueryToInsertDataToServerDB(syncUploadDataDigester.getSchemaName(),
						syncUploadDataDigester.getTableName(), syncUploadDataDigester.getServerColumns());

				// call repository to execute the query with given data list(Insert)
				i = dataSyncRepositoryCentral.syncDataToCentralDB(queryInsert, syncDataListInsert);
			}

			int[] j = null;
			if (syncDataListUpdate != null && syncDataListUpdate.size() > 0) {
				// schema name hard coded(Update query builder)
				String queryUpdate = getQueryToUpdateDataToServerDB(syncUploadDataDigester.getSchemaName(),
						syncUploadDataDigester.getTableName(), syncUploadDataDigester.getServerColumns());

				// call repository to execute the query with given data list(Update)
				j = dataSyncRepositoryCentral.syncDataToCentralDB(queryUpdate, syncDataListUpdate);
			}

			// validating if data sync successfully
			if ((i != null && syncDataListInsert.size() != i.length)
					|| (j != null && syncDataListUpdate.size() != j.length))
				return null;
			else
				return "data sync passed";

		}

	}

	public String update_M_BeneficiaryRegIdMapping_for_provisioned_benID(
			SyncUploadDataDigester syncUploadDataDigester) {
		String returnOBJ = null;
		List<Map<String, Object>> dataToBesync = syncUploadDataDigester.getSyncData();

		Object[] objArr;
		// sync data 'list of object array'
		List<Object[]> syncData = new ArrayList<>();

		String query = getqueryFor_M_BeneficiaryRegIdMapping(syncUploadDataDigester.getSchemaName(),
				syncUploadDataDigester.getTableName());

		for (Map<String, Object> map : dataToBesync) {
			if (map.get("BenRegId") != null && map.get("BeneficiaryID") != null && map.get("VanID") != null) {
				objArr = new Object[4];
				objArr[0] = String.valueOf(syncUploadDataDigester.getSyncedBy());
				objArr[1] = String.valueOf(map.get("BenRegId"));
				objArr[2] = String.valueOf(map.get("BeneficiaryID"));
				objArr[3] = String.valueOf(map.get("VanID"));

				syncData.add(objArr);
			}
		}
		int[] i = null;

		if (syncData != null && syncData.size() > 0) {
			i = dataSyncRepositoryCentral.syncDataToCentralDB(query, syncData);

			if (i.length == syncData.size()) {
				returnOBJ = "data sync passed";
			}
		} else {
			returnOBJ = "data sync passed";
		}

		return returnOBJ;

	}

	private String getqueryFor_M_BeneficiaryRegIdMapping(String schemaName, String tableName) {
		String query = " UPDATE  " + schemaName + "." + tableName
				+ " SET Provisioned = true, SyncedDate = now(), syncedBy = ? "
				+ " WHERE BenRegId = ? AND BeneficiaryID = ? AND VanID = ? ";
		return query;
	}

	public String getQueryToInsertDataToServerDB(String schemaName, String tableName, String serverColumns) {
		String[] columnsArr = null;
		if (serverColumns != null)
			columnsArr = serverColumns.split(",");

		StringBuilder preparedStatementSetter = new StringBuilder();
		/// StringBuilder updateStatement = new StringBuilder();

		if (columnsArr != null && columnsArr.length > 0) {
			int index = 0;
			for (String column : columnsArr) {
				if (index == columnsArr.length - 1) {
					preparedStatementSetter.append(" ? ");
					/// updateStatement.append(column + "=VALUES(" + column + ")");
				} else {
					preparedStatementSetter.append(" ?, ");
					/// updateStatement.append(column + "=VALUES(" + column + "),");
				}
				index++;
			}
		}

		String query = " INSERT INTO " + schemaName + "." + tableName + "( " + serverColumns + ") VALUES ( "
				+ preparedStatementSetter + " ) ";

		return query;
	}

	public String getQueryToUpdateDataToServerDB(String schemaName, String tableName, String serverColumns) {
		String[] columnsArr = null;
		if (serverColumns != null)
			columnsArr = serverColumns.split(",");

		StringBuilder preparedStatementSetter = new StringBuilder();

		if (columnsArr != null && columnsArr.length > 0) {
			int index = 0;
			for (String column : columnsArr) {
				if (index == columnsArr.length - 1) {
					preparedStatementSetter.append(column + "= ?");
				} else {
					preparedStatementSetter.append(column + "= ?, ");
				}
				index++;
			}
		}

		String query = " UPDATE  " + schemaName + "." + tableName + " SET " + preparedStatementSetter
				+ " WHERE VanSerialNo = ? AND VanID = ? ";

		return query;
	}

}
