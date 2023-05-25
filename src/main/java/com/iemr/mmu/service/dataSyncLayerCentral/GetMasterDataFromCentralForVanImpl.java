package com.iemr.mmu.service.dataSyncLayerCentral;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.mmu.data.syncActivity_syncLayer.SyncDownloadMaster;

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
