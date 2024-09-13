package com.iemr.hwc.service.foetalmonitor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.common.io.Files;
import com.iemr.hwc.data.foetalmonitor.FoetalMonitor;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.foetalmonitor.FoetalMonitorDeviceIDRepo;
import com.iemr.hwc.repo.foetalmonitor.FoetalMonitorRepo;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.http.HttpUtils;

import io.swagger.v3.oas.models.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestFoetalMonitorServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private FoetalMonitorRepo foetalMonitorRepo;

	@Mock
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

	@Mock
	private FoetalMonitorDeviceIDRepo foetalMonitorDeviceIDRepo;

	@Mock
	private HttpUtils httpUtils;

	@InjectMocks
	private FoetalMonitorServiceImpl foetalMonitorService;

	private FoetalMonitor foetalMonitorDataOutside;
	private FoetalMonitor foetalMonitorFetchDataDB;
	private String filePath;
	private byte[] fileContent;
	private FoetalMonitor request;
	private String auth;
	private List<FoetalMonitor> foetalMonitorList;
	private Long benFlowID;

	@BeforeEach
	public void setUp() {
		foetalMonitorDataOutside = new FoetalMonitor();
		foetalMonitorDataOutside.setFoetalMonitorID(1L);
		foetalMonitorDataOutside.setAccelerationsList(new ArrayList<>());
		foetalMonitorDataOutside.setDecelerationsList(new ArrayList<>());
		foetalMonitorDataOutside.setMovementEntries(new ArrayList<>());
		foetalMonitorDataOutside.setAutoFetalMovement(new ArrayList<>());
		foetalMonitorDataOutside.setMother(new HashMap<>());

		foetalMonitorFetchDataDB = new FoetalMonitor();
		foetalMonitorFetchDataDB.setFoetalMonitorID(1L);
		foetalMonitorFetchDataDB.setBeneficiaryID(1L);
		foetalMonitorFetchDataDB.setBeneficiaryRegID(1L);
		foetalMonitorFetchDataDB.setVisitCode(1L);
		foetalMonitorFetchDataDB.setTestTime("2023-10-01");
		foetalMonitorFetchDataDB.setMotherLMPDate("2023-01-01");
		foetalMonitorFetchDataDB.setMotherName("Jane Doe");
		foetalMonitorFetchDataDB.setFoetalMonitorTestId(1L);
		foetalMonitorFetchDataDB.setProviderServiceMapID(1L);
		foetalMonitorFetchDataDB.setBenFlowID(1L);
		foetalMonitorFetchDataDB.setVanID(1L);
		foetalMonitorFetchDataDB.setTestName("Test");
		foetalMonitorFetchDataDB.setCreatedBy("User");
		foetalMonitorFetchDataDB.setDeleted(false);
	}

	@Test
	public void testUpdateFoetalMonitorData_Success() throws IEMRException, IOException {
		when(foetalMonitorRepo.getFoetalMonitorDetails(1L)).thenReturn(foetalMonitorFetchDataDB);
		when(foetalMonitorRepo.save(any(FoetalMonitor.class))).thenReturn(foetalMonitorDataOutside);
		when(beneficiaryFlowStatusRepo.updateLabTechnicianFlag(anyShort(), anyLong())).thenReturn(1);

		int result = foetalMonitorService.updateFoetalMonitorData(foetalMonitorDataOutside);

		assertEquals(1, result);
		verify(foetalMonitorRepo, times(1)).getFoetalMonitorDetails(1L);
		verify(foetalMonitorRepo, times(1)).save(any(FoetalMonitor.class));
		verify(beneficiaryFlowStatusRepo, times(1)).updateLabTechnicianFlag(anyShort(), anyLong());
	}

	@Test
	public void testUpdateFoetalMonitorData_InvalidPartnerFoetalMonitorID() {
		when(foetalMonitorRepo.getFoetalMonitorDetails(1L)).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			foetalMonitorService.updateFoetalMonitorData(foetalMonitorDataOutside);
		});

		assertEquals("Invalid partnerfoetalMonitorID", exception.getMessage());
		verify(foetalMonitorRepo, times(1)).getFoetalMonitorDetails(1L);
	}

	@Test
	public void testUpdateFoetalMonitorData_ErrorInUpdatingLabTechnicianFlag() throws IEMRException, IOException {
		when(foetalMonitorRepo.getFoetalMonitorDetails(1L)).thenReturn(foetalMonitorFetchDataDB);
		when(foetalMonitorRepo.save(any(FoetalMonitor.class))).thenReturn(foetalMonitorDataOutside);
		when(beneficiaryFlowStatusRepo.updateLabTechnicianFlag(anyShort(), anyLong())).thenReturn(0);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			foetalMonitorService.updateFoetalMonitorData(foetalMonitorDataOutside);
		});

		assertEquals("Error in updating the lab technician flag", exception.getMessage());
		verify(foetalMonitorRepo, times(1)).getFoetalMonitorDetails(1L);
		verify(foetalMonitorRepo, times(1)).save(any(FoetalMonitor.class));
		verify(beneficiaryFlowStatusRepo, times(1)).updateLabTechnicianFlag(anyShort(), anyLong());
	}

	@Test
	public void testUpdateFoetalMonitorData_ErrorInUpdatingFoetalMonitorData() throws IEMRException, IOException {
		when(foetalMonitorRepo.getFoetalMonitorDetails(1L)).thenReturn(foetalMonitorFetchDataDB);
		when(foetalMonitorRepo.save(any(FoetalMonitor.class))).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			foetalMonitorService.updateFoetalMonitorData(foetalMonitorDataOutside);
		});

		assertEquals("Error in updating foetal monitor data", exception.getMessage());
		verify(foetalMonitorRepo, times(1)).getFoetalMonitorDetails(1L);
		verify(foetalMonitorRepo, times(1)).save(any(FoetalMonitor.class));
	}

	@Test
	public void testReadPDFANDGetBase64_Success() throws IEMRException, IOException {
		String expectedBase64 = Base64.getEncoder().encodeToString(fileContent);

		String actualBase64 = foetalMonitorService.readPDFANDGetBase64(filePath);

		assertEquals(expectedBase64, actualBase64);
	}

	@Test
	public void testReadPDFANDGetBase64_FileNotFoundException() {
		String invalidFilePath = "invalid.pdf";

		assertThrows(FileNotFoundException.class, () -> {
			foetalMonitorService.readPDFANDGetBase64(invalidFilePath);
		});
	}

	@Test
	public void testReadPDFANDGetBase64_IOException() throws IOException {
		String invalidFilePath = "invalid.pdf";
		doThrow(new IOException("IO Error")).when(Files.readAllBytes(Paths.get(invalidFilePath)));

		assertThrows(IOException.class, () -> {
			foetalMonitorService.readPDFANDGetBase64(invalidFilePath);
		});
	}

	@Test
	public void testSendFoetalMonitorTestDetails_Success() throws Exception {
		when(foetalMonitorRepo.save(any(FoetalMonitor.class))).thenReturn(request);
		when(foetalMonitorRepo.getBenID(anyLong())).thenReturn(1L);
		when(foetalMonitorDeviceIDRepo.getDeviceIDForVanID(anyLong()))
				.thenReturn(new FoetalMonitorDeviceID("deviceID"));

		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("message", "Success");
		ResponseEntity<String> responseEntity = new ResponseEntity<>(responseObj.toString(), HttpStatus.OK);
		when(httpUtils.postWithResponseEntity(anyString(), anyString(), anyMap())).thenReturn(responseEntity);

		String result = foetalMonitorService.sendFoetalMonitorTestDetails(request, auth);

		assertEquals(
				"Patient details sent to foetal monitor device successfully. Please select patient name on device and start the test",
				result);
		verify(foetalMonitorRepo, times(1)).save(any(FoetalMonitor.class));
		verify(httpUtils, times(1)).postWithResponseEntity(anyString(), anyString(), anyMap());
	}

	@Test
	public void testSendFoetalMonitorTestDetails_SaveFailure() {
		when(foetalMonitorRepo.save(any(FoetalMonitor.class))).thenReturn(null);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			foetalMonitorService.sendFoetalMonitorTestDetails(request, auth);
		});

		assertTrue(exception.getMessage().contains("Unable to generate foetal monitor id in TM"));
	}

	@Test
	public void testSendFoetalMonitorTestDetails_DeviceIDNotMapped() {
		when(foetalMonitorRepo.save(any(FoetalMonitor.class))).thenReturn(request);
		when(foetalMonitorRepo.getBenID(anyLong())).thenReturn(1L);
		when(foetalMonitorDeviceIDRepo.getDeviceIDForVanID(anyLong())).thenReturn(null);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			foetalMonitorService.sendFoetalMonitorTestDetails(request, auth);
		});

		assertTrue(exception.getMessage().contains("Spoke is not mapped with foetal monitor deviceID"));
	}

	@Test
	public void testSendFoetalMonitorTestDetails_HttpError() throws Exception {
		when(foetalMonitorRepo.save(any(FoetalMonitor.class))).thenReturn(request);
		when(foetalMonitorRepo.getBenID(anyLong())).thenReturn(1L);
		when(foetalMonitorDeviceIDRepo.getDeviceIDForVanID(anyLong()))
				.thenReturn(new FoetalMonitorDeviceID("deviceID"));

		ResponseEntity<String> responseEntity = new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		when(httpUtils.postWithResponseEntity(anyString(), anyString(), anyMap())).thenReturn(responseEntity);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			foetalMonitorService.sendFoetalMonitorTestDetails(request, auth);
		});

		assertTrue(exception.getMessage().contains("Error while registering mother in foetal monitor"));
	}

	@Test
	public void testSendFoetalMonitorTestDetails_HttpClientErrorException() throws Exception {
		when(foetalMonitorRepo.save(any(FoetalMonitor.class))).thenReturn(request);
		when(foetalMonitorRepo.getBenID(anyLong())).thenReturn(1L);
		when(foetalMonitorDeviceIDRepo.getDeviceIDForVanID(anyLong()))
				.thenReturn(new FoetalMonitorDeviceID("deviceID"));

		HttpClientErrorException httpClientErrorException = mock(HttpClientErrorException.class);
		when(httpClientErrorException.getResponseBodyAsString())
				.thenReturn("{\"status\":400, \"message\":\"Bad Request\"}");
		when(httpUtils.postWithResponseEntity(anyString(), anyString(), anyMap())).thenThrow(httpClientErrorException);

		Exception exception = assertThrows(Exception.class, () -> {
			foetalMonitorService.sendFoetalMonitorTestDetails(request, auth);
		});

		assertTrue(exception.getMessage().contains("Unable to raise test request, error is : Bad Request"));
	}

	@Test
	public void testSendFoetalMonitorTestDetails_GeneralException() throws Exception {
		when(foetalMonitorRepo.save(any(FoetalMonitor.class))).thenReturn(request);
		when(foetalMonitorRepo.getBenID(anyLong())).thenReturn(1L);
		when(foetalMonitorDeviceIDRepo.getDeviceIDForVanID(anyLong()))
				.thenReturn(new FoetalMonitorDeviceID("deviceID"));

		when(httpUtils.postWithResponseEntity(anyString(), anyString(), anyMap()))
				.thenThrow(new RuntimeException("General Error"));

		Exception exception = assertThrows(Exception.class, () -> {
			foetalMonitorService.sendFoetalMonitorTestDetails(request, auth);
		});

		assertTrue(exception.getMessage().contains("Unable to raise test request, error is : General Error"));
	}

	@Test
	public void testGetFoetalMonitorDetails() throws IEMRException {
		when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(benFlowID))
				.thenReturn((ArrayList<FoetalMonitor>) foetalMonitorList);

		String response = foetalMonitorService.getFoetalMonitorDetails(benFlowID);

		assertNotNull(response);
		assertTrue(response.contains("benFetosenseData"));
		verify(foetalMonitorRepo, times(1)).getFoetalMonitorDetailsByFlowId(benFlowID);
	}

	@Test
	public void testGetFoetalMonitorDetails_EmptyList() throws IEMRException {
		when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(benFlowID)).thenReturn(new ArrayList<>());

		String response = foetalMonitorService.getFoetalMonitorDetails(benFlowID);

		assertNotNull(response);
		assertTrue(response.contains("benFetosenseData"));
		verify(foetalMonitorRepo, times(1)).getFoetalMonitorDetailsByFlowId(benFlowID);
	}

	@Test
	public void testGetFoetalMonitorDetails_Exception() {
		when(foetalMonitorRepo.getFoetalMonitorDetailsByFlowId(benFlowID))
				.thenThrow(new RuntimeException("Database error"));

		Exception exception = assertThrows(IEMRException.class, () -> {
			foetalMonitorService.getFoetalMonitorDetails(benFlowID);
		});

		assertTrue(exception.getMessage().contains("Error in fetching foetal monitor details"));
		verify(foetalMonitorRepo, times(1)).getFoetalMonitorDetailsByFlowId(benFlowID);
	}
}
