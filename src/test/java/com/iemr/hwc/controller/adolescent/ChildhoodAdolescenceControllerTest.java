package com.iemr.hwc.controller.adolescent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.service.adolescent.AdolescentAndChildCareService;
import com.iemr.hwc.utils.response.OutputResponse;

@ExtendWith(MockitoExtension.class)
class ChildhoodAdolescenceControllerTest {

	@InjectMocks
	ChildhoodAdolescenceController childhoodAdolescenceController;

	@Mock
	private AdolescentAndChildCareService adolescentAndChildCareService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSaveBenNurseDataCAC_Success() throws Exception {
		String requestObj = "{\"key\": \"value\"}";
		String authorization = "Bearer token";
		String serviceResponse = "Success message";
		JsonObject jsonRes = new JsonObject();
		jsonRes.addProperty("response", serviceResponse);
		String expResp = jsonRes.toString();
		when(adolescentAndChildCareService.saveNurseData(Mockito.any(), Mockito.eq(authorization))).thenReturn(expResp);
		String result = childhoodAdolescenceController.saveBenNurseDataCAC(requestObj, authorization);
		JsonObject actualResponseJson = new JsonParser().parse(result).getAsJsonObject();
		// assertEquals(expResp, result);
		assertEquals(serviceResponse, actualResponseJson.get("data").getAsJsonObject().get("response").getAsString());
	}

	@Test
	public void testSaveBenNurseDataCAC_InvalidRequest() throws Exception {
		String requestObj = null;
		String authorization = "Bearer token";
		String expectedResponse = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";
		// when(adolescentAndChildCareService.saveNurseData(Mockito.eq(null),
		// Mockito.eq(authorization))).thenReturn(expectedResponse);
		String result = childhoodAdolescenceController.saveBenNurseDataCAC(requestObj, authorization);
		assertEquals(expectedResponse, result);
	}

	@Test
	public void testSaveBenNurseDataCAC_Exception() throws Exception {
		String requestObj = "{\"key\": \"value\"}";
		String authorization = "Bearer token";
		Exception exception = new SQLException("Test exception");
		when(adolescentAndChildCareService.saveNurseData(Mockito.any(), Mockito.eq(authorization)))
				.thenThrow(exception);
		String result = childhoodAdolescenceController.saveBenNurseDataCAC(requestObj, authorization);
		assertTrue(result.contains("5000"));
	}

	@Test
	public void saveDoctorDataCAC_Success() throws Exception {
		String requestObj = "{\"example\": \"data\"}";
		String authorization = "Bearer token";
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("example", "data");
		when(adolescentAndChildCareService.saveDoctorDataCAC(jsonObject, authorization)).thenReturn(1);
		String result = childhoodAdolescenceController.saveDoctorDataCAC(requestObj, authorization);
		assertEquals(
				"{\"data\":{\"response\":\"Data saved successfully\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}",
				result);
	}

	@Test
	public void saveDoctorDataCAC_InvalidRequest() {
		String requestObj = null;
		String authorization = "Bearer token";
		String result = childhoodAdolescenceController.saveDoctorDataCAC(requestObj, authorization);
		assertEquals(
				"{\"statusCode\":5000,\"errorMessage\":\"Cannot invoke \\\"String.length()\\\" because \\\"s\\\" is null\",\"status\":\"Cannot invoke \\\"String.length()\\\" because \\\"s\\\" is null\"}",
				result);
	}

	@Test
	public void saveDoctorDataCAC_Exception() throws Exception {
		String requestObj = "{\"example\": \"data\"}";
		String authorization = "Bearer token";
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("example", "data");
		when(adolescentAndChildCareService.saveDoctorDataCAC(jsonObject, authorization))
				.thenThrow(new RuntimeException("Test Exception"));
		String result = childhoodAdolescenceController.saveDoctorDataCAC(requestObj, authorization);
		assertEquals("{\"statusCode\":5000,\"errorMessage\":\"Test Exception\",\"status\":\"Test Exception\"}", result);
	}
	
	@Test
	void testGetBenVisitDetailsFrmNurseCAC() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBenHistoryDetails() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBenVitalDetailsFrmNurse() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBenImmunizationServiceDetails() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBenCaseRecordFromDoctor() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateVitalNurseCAC() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateBirthAndImmunizationHistoryNurse() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateImmunizationServicesNurse() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateCACDoctorData() {
		fail("Not yet implemented");
	}

}
