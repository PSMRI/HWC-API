package com.iemr.hwc.service.dataSyncActivity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.iemr.hwc.data.syncActivity_syncLayer.DataSyncGroups;
import com.iemr.hwc.data.syncActivity_syncLayer.SyncUtilityClass;
import com.iemr.hwc.repo.syncActivity_syncLayer.DataSyncGroupsRepo;
import com.iemr.hwc.repo.syncActivity_syncLayer.DataSyncRepository;

import net.minidev.json.JSONObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUploadDataToServerImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private DataSyncRepository dataSyncRepository;

	@Mock
	private DataSyncGroupsRepo dataSyncGroupsRepo;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private Logger logger;

	@InjectMocks
	private UploadDataToServerImpl uploadDataToServerImpl;

	private Integer groupID;
	private String user;
	private String authorization;
	private List<SyncUtilityClass> syncUtilityClassList;
	private List<Map<String, Object>> syncData;
	private List<Map<String, Object>> syncDataBatch;
	private String dataSyncUploadUrl = "http://example.com/upload";
	private Gson gson;

	@BeforeEach
	public void setUp() {
		groupID = 1;
		user = "testUser";
		authorization = "Bearer token";
	}

	@Test
	public void testGetDataToSyncToServer_Success() throws Exception {
		SyncUtilityClass syncUtilityClass = new SyncUtilityClass();
		syncUtilityClass.setSchemaName("schema");
		syncUtilityClass.setTableName("table");
		syncUtilityClass.setVanColumnName("vanColumn");
		syncUtilityClass.setVanAutoIncColumnName("vanAutoIncColumn");
		syncUtilityClass.setServerColumnName("serverColumn");

		List<SyncUtilityClass> syncUtilityClassList = Arrays.asList(syncUtilityClass);
		List<Map<String, Object>> syncData = Arrays.asList(Map.of("key", "value"));

		when(dataSyncRepository.getVanAndServerColumnList(groupID)).thenReturn(syncUtilityClassList);
		when(dataSyncRepository.getDataForGivenSchemaAndTable("schema", "table", "vanColumn")).thenReturn(syncData);
		when(dataSyncRepository.updateProcessedFlagInVan(anyString(), anyString(), any(), anyString(), anyString()))
				.thenReturn(1);

		String result = uploadDataToServerImpl.getDataToSyncToServer(groupID, user, authorization);

		assertEquals("Data successfully synced", result);
		verify(dataSyncRepository, times(1)).getVanAndServerColumnList(groupID);
		verify(dataSyncRepository, times(1)).getDataForGivenSchemaAndTable("schema", "table", "vanColumn");
		verify(dataSyncRepository, times(1)).updateProcessedFlagInVan(anyString(), anyString(), any(), anyString(),
				anyString());
	}

	@Test
	public void testGetDataToSyncToServer_Exception() throws Exception {
		when(dataSyncRepository.getVanAndServerColumnList(groupID)).thenThrow(new RuntimeException("Exception"));

		Exception exception = assertThrows(Exception.class, () -> {
			uploadDataToServerImpl.getDataToSyncToServer(groupID, user, authorization);
		});

		assertEquals("Exception", exception.getMessage());
		verify(dataSyncRepository, times(1)).getVanAndServerColumnList(groupID);
	}

	@Test
	public void testGetDataToSyncToServer() throws Exception {
		when(dataSyncRepository.getVanAndServerColumnList(anyInt())).thenReturn(syncUtilityClassList);
		when(dataSyncRepository.getDataForGivenSchemaAndTable(anyString(), anyString(), anyString()))
				.thenReturn(syncData);
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
				.thenReturn(new ResponseEntity<>("{\"statusCode\":200}", HttpStatus.OK));
		when(dataSyncRepository.updateProcessedFlagInVan(anyString(), anyString(), anyStringBuilder(), anyString(),
				anyString())).thenReturn(1);

		String result = uploadDataToServerImpl.getDataToSyncToServer(1, "user", "Authorization");
		assertEquals("Data successfully synced", result);
	}

	@Test
	public void testSyncIntercepter() throws Exception {
		when(dataSyncRepository.getVanAndServerColumnList(anyInt())).thenReturn(syncUtilityClassList);
		when(dataSyncRepository.getDataForGivenSchemaAndTable(anyString(), anyString(), anyString()))
				.thenReturn(syncData);
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
				.thenReturn(new ResponseEntity<>("{\"statusCode\":200}", HttpStatus.OK));
		when(dataSyncRepository.updateProcessedFlagInVan(anyString(), anyString(), anyStringBuilder(), anyString(),
				anyString())).thenReturn(1);

		String result = uploadDataToServerImpl.syncIntercepter(1, "user", "Authorization");
		assertEquals("Data successfully synced", result);
	}

	@Test
	public void testStartDataSync() throws Exception {
		when(dataSyncRepository.getDataForGivenSchemaAndTable(anyString(), anyString(), anyString()))
				.thenReturn(syncData);
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
				.thenReturn(new ResponseEntity<>("{\"statusCode\":200}", HttpStatus.OK));
		when(dataSyncRepository.updateProcessedFlagInVan(anyString(), anyString(), anyStringBuilder(), anyString(),
				anyString())).thenReturn(1);

		String result = uploadDataToServerImpl.startDataSync(1, "user", "Authorization");
		assertEquals("Data successfully synced", result);
	}

	@Test
	public void testSyncDataToServer() throws Exception {
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
				.thenReturn(new ResponseEntity<>("{\"statusCode\":200}", HttpStatus.OK));
		when(dataSyncRepository.updateProcessedFlagInVan(anyString(), anyString(), anyStringBuilder(), anyString(),
				anyString())).thenReturn(1);

		String result = uploadDataToServerImpl.syncDataToServer("schema", "table", "vanAutoIncColumn", "serverColumn",
				syncDataBatch, "user", "Authorization");
		assertEquals("Data successfully synced", result);
	}

	@Test
	public void testGetVanAndServerColumns() throws Exception {
		when(dataSyncRepository.getVanAndServerColumnList(anyInt())).thenReturn(syncUtilityClassList);

		List<SyncUtilityClass> result = uploadDataToServerImpl.getVanAndServerColumns(1);
		assertEquals(syncUtilityClassList, result);
	}

	@Test
	public void testGetDataToSync() throws Exception {
		when(dataSyncRepository.getDataForGivenSchemaAndTable(anyString(), anyString(), anyString()))
				.thenReturn(syncData);

		List<Map<String, Object>> result = uploadDataToServerImpl.getDataToSync("schema", "table", "vanColumn");
		assertEquals(syncData, result);
	}

	@Test
	public void testGetBatchOfAskedSizeDataToSync() throws Exception {
		List<Map<String, Object>> result = uploadDataToServerImpl.getBatchOfAskedSizeDataToSync(syncData, 0, 1);
		assertEquals(syncDataBatch, result);
	}

	@Test
	public void testGetVanSerialNoListForSyncedData() throws Exception {
		StringBuilder result = uploadDataToServerImpl.getVanSerialNoListForSyncedData("vanAutoIncColumn", syncData);
		assertEquals("1", result.toString());
	}

	@Test
	public void testGetDataSyncGroupDetails() {
		List<DataSyncGroups> dataSyncGroupList = new ArrayList<>();
		DataSyncGroups dataSyncGroup = new DataSyncGroups();
		dataSyncGroupList.add(dataSyncGroup);

		when(dataSyncGroupsRepo.findByDeleted(false)).thenReturn(dataSyncGroupList);

		String result = uploadDataToServerImpl.getDataSyncGroupDetails();
		assertEquals(new Gson().toJson(dataSyncGroupList), result);
	}

	@Test
	public void testSyncDataToServer_Success() throws Exception {
		// Mock response from RestTemplate
		JSONObject responseBody = new JSONObject();
		responseBody.put("statusCode", 200);
		ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody.toString(), HttpStatus.OK);
		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenReturn(responseEntity);

		// Mock updateProcessedFlagInVan
		when(dataSyncRepository.updateProcessedFlagInVan(anyString(), anyString(), anyStringBuilder(), anyString(),
				anyString())).thenReturn(1);

		String result = uploadDataToServerImpl.syncDataToServer(schemaName, tableName, vanAutoIncColumnName,
				serverColumns, dataToBesync, user, authorization);

		assertEquals("Data successfully synced", result);
		verify(dataSyncRepository, times(1)).updateProcessedFlagInVan(anyString(), anyString(), anyStringBuilder(),
				anyString(), anyString());
	}

	@Test
	public void testSyncDataToServer_NoResponseBody() throws Exception {
		// Mock response from RestTemplate
		ResponseEntity<String> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenReturn(responseEntity);

		String result = uploadDataToServerImpl.syncDataToServer(schemaName, tableName, vanAutoIncColumnName,
				serverColumns, dataToBesync, user, authorization);

		assertNull(result);
		verify(dataSyncRepository, never()).updateProcessedFlagInVan(anyString(), anyString(), anyStringBuilder(),
				anyString(), anyString());
	}

	@Test
	public void testSyncDataToServer_Non200StatusCode() throws Exception {
		// Mock response from RestTemplate
		JSONObject responseBody = new JSONObject();
		responseBody.put("statusCode", 500);
		ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody.toString(),
				HttpStatus.INTERNAL_SERVER_ERROR);
		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
				.thenReturn(responseEntity);

		String result = uploadDataToServerImpl.syncDataToServer(schemaName, tableName, vanAutoIncColumnName,
				serverColumns, dataToBesync, user, authorization);

		assertNull(result);
		verify(dataSyncRepository, never()).updateProcessedFlagInVan(anyString(), anyString(), anyStringBuilder(),
				anyString(), anyString());
	}

	@Test
	public void testGetVanSerialNoListForSyncedData_MultipleEntries() throws Exception {
		List<Map<String, Object>> dataToBesync = new ArrayList<>();
		Map<String, Object> map1 = new HashMap<>();
		map1.put("vanAutoIncColumnName", 1);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("vanAutoIncColumnName", 2);
		dataToBesync.add(map1);
		dataToBesync.add(map2);

		StringBuilder result = uploadDataToServerImpl.getVanSerialNoListForSyncedData("vanAutoIncColumnName",
				dataToBesync);
		assertEquals("1,2", result.toString());
	}

	@Test
	public void testGetVanSerialNoListForSyncedData_EmptyList() throws Exception {
		List<Map<String, Object>> dataToBesync = new ArrayList<>();

		StringBuilder result = uploadDataToServerImpl.getVanSerialNoListForSyncedData("vanAutoIncColumnName",
				dataToBesync);
		assertEquals("", result.toString());
	}

	@Test
	public void testGetVanSerialNoListForSyncedData_SingleEntry() throws Exception {
		List<Map<String, Object>> dataToBesync = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("vanAutoIncColumnName", 1);
		dataToBesync.add(map);

		StringBuilder result = uploadDataToServerImpl.getVanSerialNoListForSyncedData("vanAutoIncColumnName",
				dataToBesync);
		assertEquals("1", result.toString());
	}

	@Test
	public void testGetDataSyncGroupDetails() {
		// Arrange
		DataSyncGroups group1 = new DataSyncGroups();
		group1.setId(1);
		group1.setGroupName("Group1");
		group1.setDeleted(false);

		DataSyncGroups group2 = new DataSyncGroups();
		group2.setId(2);
		group2.setGroupName("Group2");
		group2.setDeleted(false);

		List<DataSyncGroups> dataSyncGroupList = Arrays.asList(group1, group2);
		when(dataSyncGroupsRepo.findByDeleted(false)).thenReturn(dataSyncGroupList);

		// Act
		String result = uploadDataToServerImpl.getDataSyncGroupDetails();

		// Assert
		assertNotNull(result);
		assertTrue(result.contains("Group1"));
		assertTrue(result.contains("Group2"));
		verify(dataSyncGroupsRepo, times(1)).findByDeleted(false);
	}

	@Test
	public void testGetDataSyncGroupDetails_NoGroups() {
		// Arrange
		when(dataSyncGroupsRepo.findByDeleted(false)).thenReturn(null);

		// Act
		String result = uploadDataToServerImpl.getDataSyncGroupDetails();

		// Assert
		assertNull(result);
		verify(dataSyncGroupsRepo, times(1)).findByDeleted(false);
	}
}
