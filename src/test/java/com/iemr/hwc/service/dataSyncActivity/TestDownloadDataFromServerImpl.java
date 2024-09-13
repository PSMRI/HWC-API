package com.iemr.hwc.service.dataSyncActivity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.iemr.hwc.data.covid19.SyncDownloadMaster;
import com.iemr.hwc.repository.covid19.SyncDownloadMasterRepo;
import com.iemr.hwc.repository.covid19.DataSyncRepository;
import com.iemr.hwc.utils.exception.IEMRException;

import org.hibernate.mapping.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestDownloadDataFromServerImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private SyncDownloadMasterRepo syncDownloadMasterRepo;

	@Mock
	private DataSyncRepository dataSyncRepository;

	@InjectMocks
	private DownloadDataFromServerImpl downloadDataFromServerImpl;

	private List<SyncDownloadMaster> downloadMasterList;
	private String serverAuthorization;
	private Integer vanID;
	private Integer psmID;

	@BeforeEach
	public void setUp() {
		serverAuthorization = "Bearer token";
		vanID = 1;
		psmID = 1;

		downloadMasterList = new ArrayList<>();
		SyncDownloadMaster syncDownloadMaster = new SyncDownloadMaster();
		syncDownloadMaster.setTableName("testTable");
		syncDownloadMaster.setSchemaName("testSchema");
		downloadMasterList.add(syncDownloadMaster);

		when(syncDownloadMasterRepo.getDownloadTables()).thenReturn(downloadMasterList);
	}

	@Test
	public void testDownloadMasterDataFromServer_Success() throws Exception {
		when(dataSyncRepository.updateLatestMasterInLocal(anyString(), anyList())).thenReturn(new int[] { 1 });
		when(syncDownloadMasterRepo.updateTableSyncDownloadMasterForLastDownloadDate(anyLong())).thenReturn(1);

		String result = downloadDataFromServerImpl.downloadMasterDataFromServer(serverAuthorization, vanID, psmID);

		assertNotNull(result);
		assertEquals(" Master download started ", result);
		verify(syncDownloadMasterRepo, times(1)).getDownloadTables();
	}

	@Test
	public void testDownloadMasterDataFromServer_InProgress() throws Exception {
		downloadDataFromServerImpl.totalCounter = 10;
		downloadDataFromServerImpl.progressCounter = 5;

		String result = downloadDataFromServerImpl.downloadMasterDataFromServer(serverAuthorization, vanID, psmID);

		assertNotNull(result);
		assertEquals("inProgress", result);
	}

	@Test
	public void testDownloadMasterDataFromServer_Exception() throws Exception {
		when(dataSyncRepository.updateLatestMasterInLocal(anyString(), anyList()))
				.thenThrow(new RuntimeException("Error"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			downloadDataFromServerImpl.downloadMasterDataFromServer(serverAuthorization, vanID, psmID);
		});

		assertEquals("Error", exception.getMessage());
	}

	@Test
	public void testGetDownloadStatus_ZeroProgress() {
		DownloadDataFromServerImpl.totalCounter = 10;
		DownloadDataFromServerImpl.progressCounter = 0;
		DownloadDataFromServerImpl.failedCounter = 2;
		DownloadDataFromServerImpl.failedMasters.append("Master1 | Master2 | ");

		Map<String, Object> result = downloadDataFromServerImpl.getDownloadStatus();

		assertEquals(0.0, result.get("percentage"));
		assertEquals(2, result.get("failedMasterCount"));
		assertEquals("Master1 | Master2 | ", result.get("failedMasters").toString());
	}

	@Test
	public void testGetDownloadStatus_HalfProgress() {
		DownloadDataFromServerImpl.totalCounter = 10;
		DownloadDataFromServerImpl.progressCounter = 5;
		DownloadDataFromServerImpl.failedCounter = 1;
		DownloadDataFromServerImpl.failedMasters.append("Master1 | ");

		Map<String, Object> result = downloadDataFromServerImpl.getDownloadStatus();

		assertEquals(50.0, result.get("percentage"));
		assertEquals(1, result.get("failedMasterCount"));
		assertEquals("Master1 | ", result.get("failedMasters").toString());
	}

	@Test
	public void testGetDownloadStatus_FullProgress() {
		DownloadDataFromServerImpl.totalCounter = 10;
		DownloadDataFromServerImpl.progressCounter = 10;
		DownloadDataFromServerImpl.failedCounter = 0;

		Map<String, Object> result = downloadDataFromServerImpl.getDownloadStatus();

		assertEquals(100.0, result.get("percentage"));
		assertEquals(0, result.get("failedMasterCount"));
		assertEquals("", result.get("failedMasters").toString());
	}

	@Test
	public void testGetDownloadStatus_NoTotalCounter() {
		DownloadDataFromServerImpl.totalCounter = 0;
		DownloadDataFromServerImpl.progressCounter = 0;
		DownloadDataFromServerImpl.failedCounter = 0;

		Map<String, Object> result = downloadDataFromServerImpl.getDownloadStatus();

		assertEquals(0.0, result.get("percentage"));
		assertEquals(0, result.get("failedMasterCount"));
		assertEquals("", result.get("failedMasters").toString());
	}
}
