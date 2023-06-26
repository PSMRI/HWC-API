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
package com.iemr.hwc.service.dataSyncLayerCentral;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.syncActivity_syncLayer.SyncDownloadMaster;

@Service
public class GetMasterDataFromCentralForVanImpl implements GetMasterDataFromCentralForVan {
	@Autowired
	private DataSyncRepositoryCentral dataSyncRepositoryCentral;

	public String getMasterDataForVan(SyncDownloadMaster obj) throws Exception {
		List<Map<String, Object>> resultSetList = new ArrayList<>();
		// serialize null
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.serializeNulls();
		Gson gson = gsonBuilder.create();

		if (obj != null && obj.getSchemaName() != null && obj.getTableName() != null) {
			// return applicable master data
			resultSetList = getMasterDataFromGivenTable(obj);
			return gson.toJson(resultSetList);
		} else {
			// return error (invalid request !!)
			return null;
		}

	}

	private List<Map<String, Object>> getMasterDataFromGivenTable(SyncDownloadMaster tableDetails) throws Exception {
		List<Map<String, Object>> resultSetList = new ArrayList<>();
		resultSetList = dataSyncRepositoryCentral.getMasterDataFromTable(tableDetails.getSchemaName(),
				tableDetails.getTableName(), tableDetails.getServerColumnName(), tableDetails.getMasterType(),
				tableDetails.getLastDownloadDate(), tableDetails.getVanID(), tableDetails.getProviderServiceMapID());
		return resultSetList;
	}

}
