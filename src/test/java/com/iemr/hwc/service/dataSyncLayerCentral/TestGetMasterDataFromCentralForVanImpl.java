package com.iemr.hwc.service.dataSyncLayerCentral;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.syncActivity_syncLayer.SyncDownloadMaster;

public class TestGetMasterDataFromCentralForVanImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private DataSyncRepositoryCentral dataSyncRepositoryCentral;

	@InjectMocks
	private GetMasterDataFromCentralForVanImpl getMasterDataFromCentralForVanImpl;

	private SyncDownloadMaster validSyncDownloadMaster;
	private List<Map<String, Object>> mockResultSetList;

	@BeforeEach
	public void setUp() {
		validSyncDownloadMaster = new SyncDownloadMaster();
		validSyncDownloadMaster.setSchemaName("schema");
		validSyncDownloadMaster.setTableName("table");
		validSyncDownloadMaster.setServerColumnName("serverColumn");
		validSyncDownloadMaster.setMasterType("masterType");
		validSyncDownloadMaster.setLastDownloadDate("2023-01-01");
		validSyncDownloadMaster.setVanID(1L);
		validSyncDownloadMaster.setProviderServiceMapID(1L);

		mockResultSetList = new ArrayList<>();
		Map<String, Object> mockData = new HashMap<>();
		mockData.put("key", "value");
		mockResultSetList.add(mockData);
	}

	@Test
	public void testGetMasterDataForVan_ValidInput() throws Exception {
		when(dataSyncRepositoryCentral.getMasterDataFromTable(anyString(), anyString(), anyString(), anyString(),
				anyString(), anyLong(), anyLong())).thenReturn(mockResultSetList);

		String response = getMasterDataFromCentralForVanImpl.getMasterDataForVan(validSyncDownloadMaster);

		Gson gson = new GsonBuilder().serializeNulls().create();
		String expectedResponse = gson.toJson(mockResultSetList);

		assertEquals(expectedResponse, response);
		verify(dataSyncRepositoryCentral, times(1)).getMasterDataFromTable(anyString(), anyString(), anyString(),
				anyString(), anyString(), anyLong(), anyLong());
	}

	@Test
	public void testGetMasterDataForVan_NullInput() throws Exception {
		String response = getMasterDataFromCentralForVanImpl.getMasterDataForVan(null);

		assertNull(response);
		verify(dataSyncRepositoryCentral, never()).getMasterDataFromTable(anyString(), anyString(), anyString(),
				anyString(), anyString(), anyLong(), anyLong());
	}

	@Test
	public void testGetMasterDataForVan_InvalidInput() throws Exception {
		SyncDownloadMaster invalidSyncDownloadMaster = new SyncDownloadMaster();
		invalidSyncDownloadMaster.setSchemaName(null);
		invalidSyncDownloadMaster.setTableName("table");

		String response = getMasterDataFromCentralForVanImpl.getMasterDataForVan(invalidSyncDownloadMaster);

		assertNull(response);
		verify(dataSyncRepositoryCentral, never()).getMasterDataFromTable(anyString(), anyString(), anyString(),
				anyString(), anyString(), anyLong(), anyLong());
	}
}
