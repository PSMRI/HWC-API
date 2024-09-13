package com.iemr.hwc.service.dataSyncLayerCentral;
package com.iemr.hwc.service.dataSyncLayerCentral;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.hwc.data.syncActivity_syncLayer.SyncUploadDataDigester;
import com.iemr.hwc.utils.mapper.InputMapper;

public class TestGetDataFromVanAndSyncToDBImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private DataSyncRepositoryCentral dataSyncRepositoryCentral;

	@InjectMocks
	private GetDataFromVanAndSyncToDBImpl getDataFromVanAndSyncToDBImpl;

	private String requestOBJ;
	private String authorization;
	private SyncUploadDataDigester syncUploadDataDigester;

	@BeforeEach
	public void setUp() {
		requestOBJ = "{\"tableName\":\"testTable\",\"syncData\":[{\"key\":\"value\"}],\"vanAutoIncColumnName\":\"id\",\"syncedBy\":\"user\",\"schemaName\":\"schema\",\"serverColumns\":\"col1,col2\"}";
		authorization = "Bearer token";
		syncUploadDataDigester = InputMapper.gson().fromJson(requestOBJ, SyncUploadDataDigester.class);
	}

	@Test
	public void testSyncDataToServer_NullSyncUploadDataDigester() throws Exception {
		requestOBJ = "{}";
		syncUploadDataDigester = InputMapper.gson().fromJson(requestOBJ, SyncUploadDataDigester.class);

		String result = getDataFromVanAndSyncToDBImpl.syncDataToServer(requestOBJ, authorization);

		assertNull(result);
	}

	@Test
	public void testSyncDataToServer_BeneficiaryRegIdMapping() throws Exception {
		syncUploadDataDigester.setTableName("m_beneficiaryregidmapping");
		requestOBJ = InputMapper.gson().toJson(syncUploadDataDigester);

		when(dataSyncRepositoryCentral.syncDataToCentralDB(anyString(), anyList())).thenReturn(new int[] { 1 });

		String result = getDataFromVanAndSyncToDBImpl.syncDataToServer(requestOBJ, authorization);

		assertEquals("data sync passed", result);
	}

	@Test
	public void testSyncDataToServer_InsertData() throws Exception {
		when(dataSyncRepositoryCentral.checkRecordIsAlreadyPresentOrNot(anyString(), anyString(), anyString(),
				anyString(), anyString())).thenReturn(0);
		when(dataSyncRepositoryCentral.syncDataToCentralDB(anyString(), anyList())).thenReturn(new int[] { 1 });

		String result = getDataFromVanAndSyncToDBImpl.syncDataToServer(requestOBJ, authorization);

		assertEquals("data sync passed", result);
	}

	@Test
	public void testSyncDataToServer_UpdateData() throws Exception {
		when(dataSyncRepositoryCentral.checkRecordIsAlreadyPresentOrNot(anyString(), anyString(), anyString(),
				anyString(), anyString())).thenReturn(1);
		when(dataSyncRepositoryCentral.syncDataToCentralDB(anyString(), anyList())).thenReturn(new int[] { 1 });

		String result = getDataFromVanAndSyncToDBImpl.syncDataToServer(requestOBJ, authorization);

		assertEquals("data sync passed", result);
	}

	@Test
	public void testSyncDataToServer_InsertAndUpdateData() throws Exception {
		when(dataSyncRepositoryCentral.checkRecordIsAlreadyPresentOrNot(anyString(), anyString(), anyString(),
				anyString(), anyString())).thenReturn(0, 1);
		when(dataSyncRepositoryCentral.syncDataToCentralDB(anyString(), anyList())).thenReturn(new int[] { 1 },
				new int[] { 1 });

		String result = getDataFromVanAndSyncToDBImpl.syncDataToServer(requestOBJ, authorization);

		assertEquals("data sync passed", result);
	}

	@Test
	public void testSyncDataToServer_InsertDataFailure() throws Exception {
		when(dataSyncRepositoryCentral.checkRecordIsAlreadyPresentOrNot(anyString(), anyString(), anyString(),
				anyString(), anyString())).thenReturn(0);
		when(dataSyncRepositoryCentral.syncDataToCentralDB(anyString(), anyList())).thenReturn(new int[] { 0 });

		String result = getDataFromVanAndSyncToDBImpl.syncDataToServer(requestOBJ, authorization);

		assertNull(result);
	}

	@Test
	public void testSyncDataToServer_UpdateDataFailure() throws Exception {
		when(dataSyncRepositoryCentral.checkRecordIsAlreadyPresentOrNot(anyString(), anyString(), anyString(),
				anyString(), anyString())).thenReturn(1);
		when(dataSyncRepositoryCentral.syncDataToCentralDB(anyString(), anyList())).thenReturn(new int[] { 0 });

		String result = getDataFromVanAndSyncToDBImpl.syncDataToServer(requestOBJ, authorization);

		assertNull(result);
	}

	@Test
	public void testUpdate_M_BeneficiaryRegIdMapping_for_provisioned_benID_Success() throws Exception {
		Map<String, Object> data = new HashMap<>();
		data.put("BenRegId", "1");
		data.put("BeneficiaryID", "1");
		data.put("VanID", "1");

		List<Map<String, Object>> syncData = new ArrayList<>();
		syncData.add(data);

		syncUploadDataDigester.setSyncData(syncData);

		when(dataSyncRepositoryCentral.syncDataToCentralDB(anyString(), anyList())).thenReturn(new int[] { 1 });

		String result = getDataFromVanAndSyncToDBImpl
				.update_M_BeneficiaryRegIdMapping_for_provisioned_benID(syncUploadDataDigester);

		assertEquals("data sync passed", result);
		verify(dataSyncRepositoryCentral, times(1)).syncDataToCentralDB(anyString(), anyList());
	}

	@Test
	public void testUpdate_M_BeneficiaryRegIdMapping_for_provisioned_benID_NoData() throws Exception {
		syncUploadDataDigester.setSyncData(new ArrayList<>());

		String result = getDataFromVanAndSyncToDBImpl
				.update_M_BeneficiaryRegIdMapping_for_provisioned_benID(syncUploadDataDigester);

		assertEquals("data sync passed", result);
		verify(dataSyncRepositoryCentral, never()).syncDataToCentralDB(anyString(), anyList());
	}

	@Test
	public void testUpdate_M_BeneficiaryRegIdMapping_for_provisioned_benID_MissingFields() throws Exception {
		Map<String, Object> data = new HashMap<>();
		data.put("BenRegId", "1");
		data.put("VanID", "1");

		List<Map<String, Object>> syncData = new ArrayList<>();
		syncData.add(data);

		syncUploadDataDigester.setSyncData(syncData);

		String result = getDataFromVanAndSyncToDBImpl
				.update_M_BeneficiaryRegIdMapping_for_provisioned_benID(syncUploadDataDigester);

		assertEquals("data sync passed", result);
		verify(dataSyncRepositoryCentral, never()).syncDataToCentralDB(anyString(), anyList());
	}

	@Test
	public void testGetQueryToInsertDataToServerDB_withValidInput() {
		String schemaName = "testSchema";
		String tableName = "testTable";
		String serverColumns = "col1,col2,col3";

		String expectedQuery = "INSERT INTO testSchema.testTable( col1,col2,col3) VALUES ( ?, ?, ? )";
		String actualQuery = getDataFromVanAndSyncToDBImpl.getQueryToInsertDataToServerDB(schemaName, tableName,
				serverColumns);

		assertEquals(expectedQuery, actualQuery);
	}

	@Test
	public void testGetQueryToInsertDataToServerDB_withSingleColumn() {
		String schemaName = "testSchema";
		String tableName = "testTable";
		String serverColumns = "col1";

		String expectedQuery = "INSERT INTO testSchema.testTable( col1) VALUES ( ? )";
		String actualQuery = getDataFromVanAndSyncToDBImpl.getQueryToInsertDataToServerDB(schemaName, tableName,
				serverColumns);

		assertEquals(expectedQuery, actualQuery);
	}

	@Test
	public void testGetQueryToInsertDataToServerDB_withEmptyColumns() {
		String schemaName = "testSchema";
		String tableName = "testTable";
		String serverColumns = "";

		String expectedQuery = "INSERT INTO testSchema.testTable( ) VALUES (  )";
		String actualQuery = getDataFromVanAndSyncToDBImpl.getQueryToInsertDataToServerDB(schemaName, tableName,
				serverColumns);

		assertEquals(expectedQuery, actualQuery);
	}

	@Test
	public void testGetQueryToInsertDataToServerDB_withNullColumns() {
		String schemaName = "testSchema";
		String tableName = "testTable";
		String serverColumns = null;

		String expectedQuery = "INSERT INTO testSchema.testTable( null) VALUES (  )";
		String actualQuery = getDataFromVanAndSyncToDBImpl.getQueryToInsertDataToServerDB(schemaName, tableName,
				serverColumns);

		assertEquals(expectedQuery, actualQuery);
	}

	@Test
	public void testGetQueryToUpdateDataToServerDB_ValidInput() {
		String schemaName = "testSchema";
		String tableName = "testTable";
		String serverColumns = "column1,column2,column3";

		String expectedQuery = " UPDATE  testSchema.testTable SET column1= ?, column2= ?, column3= ? WHERE VanSerialNo = ? AND VanID = ? ";
		String actualQuery = getDataFromVanAndSyncToDBImpl.getQueryToUpdateDataToServerDB(schemaName, tableName,
				serverColumns);

		assertEquals(expectedQuery, actualQuery);
	}

	@Test
	public void testGetQueryToUpdateDataToServerDB_EmptyColumns() {
		String schemaName = "testSchema";
		String tableName = "testTable";
		String serverColumns = "";

		String expectedQuery = " UPDATE  testSchema.testTable SET  WHERE VanSerialNo = ? AND VanID = ? ";
		String actualQuery = getDataFromVanAndSyncToDBImpl.getQueryToUpdateDataToServerDB(schemaName, tableName,
				serverColumns);

		assertEquals(expectedQuery, actualQuery);
	}

	@Test
	public void testGetQueryToUpdateDataToServerDB_NullColumns() {
		String schemaName = "testSchema";
		String tableName = "testTable";
		String serverColumns = null;

		String expectedQuery = " UPDATE  testSchema.testTable SET  WHERE VanSerialNo = ? AND VanID = ? ";
		String actualQuery = getDataFromVanAndSyncToDBImpl.getQueryToUpdateDataToServerDB(schemaName, tableName,
				serverColumns);

		assertEquals(expectedQuery, actualQuery);
	}
}
