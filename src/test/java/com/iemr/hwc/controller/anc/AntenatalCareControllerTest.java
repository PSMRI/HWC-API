package com.iemr.hwc.controller.anc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.catalina.User;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.catalina.users.MemoryUserDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemr.hwc.data.anc.HrpStatusAndReasonsRequestModel;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.service.anc.ANCService;

@ExtendWith(MockitoExtension.class)
class AntenatalCareControllerTest {
	@Mock
	private HrpStatusAndReasonsRequestModel hrpStatusAndReasonsRequestModel;

	@Mock
	private ANCService aNCService;

	@InjectMocks
	private AntenatalCareController antenatalCareController;

	@BeforeEach
	void setUp() throws Exception {
		antenatalCareController = new AntenatalCareController();
		Field field = antenatalCareController.getClass().getDeclaredField("aNCService");
		field.setAccessible(true);
		field.set(antenatalCareController, aNCService);
	}

	@Test
	void testGetBenANCDetailsFrmNurseANC() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
				.post("/ANC/getBenANCDetailsFrmNurseANC").contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("﻿"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testSaveBenANCNurseData_NullRequestObj() throws Exception {
		assertEquals("{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}",
				antenatalCareController.saveBenANCNurseData(null, "foo"));
	}

	@Test
	void testSaveBenANCNurseData_Success() throws Exception {
		String requestObj = "{\"nurseData\":{\"name\":\"John\",\"age\":30}}";
		String expectedResponse = "{\"statusCode\":200,\"errorMessage\":null,\"status\":\"SUCCESS\"}";
		when(aNCService.saveANCNurseData(Mockito.any(), Mockito.any())).thenReturn(expectedResponse);
		doNothing().when(aNCService).deleteVisitDetails(Mockito.any()); // Mock deleteVisitDetails method
		String result = antenatalCareController.saveBenANCNurseData(requestObj, "foo");
		assertEquals(expectedResponse, result);
	}

	@Test
	void testSaveBenANCNurseData_Error() throws Exception {
		String requestObj = "{\"nurseData\":{\"name\":\"John\",\"age\":30}}";
		String expectedResponse = "{\"statusCode\":5001,\"errorMessage\":\"Invalid request body\",\"status\":\"FAILURE\"}";
		when(antenatalCareController.saveBenANCNurseData(Mockito.any(), Mockito.any())).thenReturn(expectedResponse);
		String result = antenatalCareController.saveBenANCNurseData(requestObj, "foo");
		assertEquals(expectedResponse, result);
	}

	@Test
	void testUpdateANCDoctorData() {
		(new AntenatalCareController()).updateANCDoctorData("Request Obj", "JaneDoe");
	}

	@Test
	void testGetBenANCDetailsFrmNurseANC2() throws Exception {
		// Arrange
		User user = mock(User.class);
		when(user.getName()).thenReturn("Name");
		MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/ANC/getBenANCDetailsFrmNurseANC");
		postResult.principal(new UserDatabaseRealm.UserDatabasePrincipal(user, new MemoryUserDatabase()));
		MockHttpServletRequestBuilder contentTypeResult = postResult.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("﻿"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetBenANCHistoryDetails() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/ANC/getBenANCHistoryDetails")
				.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("﻿"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetBenANCHistoryDetails2() throws Exception {
		// Arrange
		User user = mock(User.class);
		when(user.getName()).thenReturn("Name");
		MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/ANC/getBenANCHistoryDetails");
		postResult.principal(new UserDatabaseRealm.UserDatabasePrincipal(user, new MemoryUserDatabase()));
		MockHttpServletRequestBuilder contentTypeResult = postResult.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("﻿"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetBenANCVitalDetailsFrmNurseANC() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
				.post("/ANC/getBenANCVitalDetailsFrmNurseANC").contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("﻿"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetBenANCVitalDetailsFrmNurseANC2() throws Exception {
		// Arrange
		User user = mock(User.class);
		when(user.getName()).thenReturn("Name");
		MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/ANC/getBenANCVitalDetailsFrmNurseANC");
		postResult.principal(new UserDatabaseRealm.UserDatabasePrincipal(user, new MemoryUserDatabase()));
		MockHttpServletRequestBuilder contentTypeResult = postResult.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("﻿"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetBenCaseRecordFromDoctorANC() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
				.post("/ANC/getBenCaseRecordFromDoctorANC").contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("﻿"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetBenCaseRecordFromDoctorANC2() throws Exception {
		// Arrange
		User user = mock(User.class);
		when(user.getName()).thenReturn("Name");
		MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/ANC/getBenCaseRecordFromDoctorANC");
		postResult.principal(new UserDatabaseRealm.UserDatabasePrincipal(user, new MemoryUserDatabase()));
		MockHttpServletRequestBuilder contentTypeResult = postResult.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("﻿"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetBenExaminationDetailsANC() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
				.post("/ANC/getBenExaminationDetailsANC").contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("﻿"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetBenExaminationDetailsANC2() throws Exception {
		// Arrange
		User user = mock(User.class);
		when(user.getName()).thenReturn("Name");
		MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/ANC/getBenExaminationDetailsANC");
		postResult.principal(new UserDatabaseRealm.UserDatabasePrincipal(user, new MemoryUserDatabase()));
		MockHttpServletRequestBuilder contentTypeResult = postResult.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("﻿"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetBenVisitDetailsFrmNurseANC() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
				.post("/ANC/getBenVisitDetailsFrmNurseANC").contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("﻿"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetBenVisitDetailsFrmNurseANC2() throws Exception {
		// Arrange
		User user = mock(User.class);
		when(user.getName()).thenReturn("Name");
		MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/ANC/getBenVisitDetailsFrmNurseANC");
		postResult.principal(new UserDatabaseRealm.UserDatabasePrincipal(user, new MemoryUserDatabase()));
		MockHttpServletRequestBuilder contentTypeResult = postResult.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("﻿"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetHRPStatus() throws Exception {
		// Arrange
		HrpStatusAndReasonsRequestModel hrpStatusAndReasonsRequestModel2 = new HrpStatusAndReasonsRequestModel();
		hrpStatusAndReasonsRequestModel2.setBeneficiaryHeight(10.0f);
		hrpStatusAndReasonsRequestModel2.setBeneficiaryRegID(1L);
		hrpStatusAndReasonsRequestModel2.setBenificiaryAge(1);
		hrpStatusAndReasonsRequestModel2.setBloodGroupType("Blood Group Type");
		hrpStatusAndReasonsRequestModel2.setComorbidConditions(new String[] { "Comorbid Conditions" });
		hrpStatusAndReasonsRequestModel2.setHemoglobin(10.0f);
		hrpStatusAndReasonsRequestModel2.setLowLyingPlacenta(true);
		hrpStatusAndReasonsRequestModel2.setMalPresentation(true);
		hrpStatusAndReasonsRequestModel2.setPastIllness(new String[] { "Past Illness" });
		hrpStatusAndReasonsRequestModel2.setPastObstetric(new ArrayList<>());
		hrpStatusAndReasonsRequestModel2.setVertebralDeformity(true);
		String content = (new ObjectMapper()).writeValueAsString(hrpStatusAndReasonsRequestModel2);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ANC/getHRPStatus")
				.contentType(MediaType.APPLICATION_JSON).content(content);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetHrpInformation() throws Exception {
		// Arrange
		CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
		commonUtilityClass.setAge(1);
		commonUtilityClass.setAgeUnits("Age Units");
		commonUtilityClass.setAuthorization("JaneDoe");
		commonUtilityClass.setBenFlowID(1L);
		commonUtilityClass.setBenRegID(1L);
		commonUtilityClass.setBenVisitID(1L);
		commonUtilityClass.setBeneficiaryID(1L);
		commonUtilityClass.setBeneficiaryRegID(1L);
		commonUtilityClass.setBeneficiaryRegId(1L);
		commonUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		commonUtilityClass.setDistrictID(1);
		commonUtilityClass.setDistrictName("District Name");
		commonUtilityClass.setFacilityID(1);
		commonUtilityClass.setFirstName("Jane");
		commonUtilityClass.setGenderID((short) 1);
		commonUtilityClass.setGenderName("Gender Name");
		commonUtilityClass.setIsCovidFlowDone(true);
		commonUtilityClass.setIsMobile(true);
		commonUtilityClass.setIsSpecialist(true);
		commonUtilityClass.setLastName("Doe");
		commonUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		commonUtilityClass.setParkingPlaceID(1);
		commonUtilityClass.setParkingPlaceId(1);
		commonUtilityClass.setPrescriptionID(1L);
		commonUtilityClass.setProviderServiceMapID(1);
		commonUtilityClass.setServiceID((short) 1);
		commonUtilityClass.setSessionID(1);
		commonUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
		commonUtilityClass.setSubVisitCategory("Sub Visit Category");
		commonUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
		commonUtilityClass.setVanID(1);
		commonUtilityClass.setVanId(1);
		commonUtilityClass.setVillageId(1);
		commonUtilityClass.setVillageName("Village Name");
		commonUtilityClass.setVisitCategoryID(1);
		commonUtilityClass.setVisitCode(1L);
		String content = (new ObjectMapper()).writeValueAsString(commonUtilityClass);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ANC/getHrpInformation")
				.contentType(MediaType.APPLICATION_JSON).content(content);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testSaveBenANCDoctorData() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/ANC/save/doctorData")
				.header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==").contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("foo"));

		// Act and Assert
		MockMvcBuilders.standaloneSetup(antenatalCareController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"statusCode\":5000,\"errorMessage\":\"Not a JSON Object: \\\"foo\\\"\",\"status\":\"Not a JSON Object:"
								+ " \\\"foo\\\"\"}"));
	}

	@Test
	void testSaveBenANCDoctorData2() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
				.post("/ANC/save/doctorData", "Uri Variables")
				.header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==").contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("foo"));

		// Act and Assert
		MockMvcBuilders.standaloneSetup(antenatalCareController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"statusCode\":5000,\"errorMessage\":\"Not a JSON Object: \\\"foo\\\"\",\"status\":\"Not a JSON Object:"
								+ " \\\"foo\\\"\"}"));
	}

	@Test
	void testUpdateANCCareNurse() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/ANC/update/ANCScreen")
				.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("foo"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testUpdateANCCareNurse2() throws Exception {
		// Arrange
		User user = mock(User.class);
		when(user.getName()).thenReturn("Name");
		MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/ANC/update/ANCScreen");
		postResult.principal(new UserDatabaseRealm.UserDatabasePrincipal(user, new MemoryUserDatabase()));
		MockHttpServletRequestBuilder contentTypeResult = postResult.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("foo"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testUpdateANCExaminationNurse() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/ANC/update/examinationScreen")
				.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("foo"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testUpdateANCExaminationNurse2() throws Exception {
		// Arrange
		User user = mock(User.class);
		when(user.getName()).thenReturn("Name");
		MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/ANC/update/examinationScreen");
		postResult.principal(new UserDatabaseRealm.UserDatabasePrincipal(user, new MemoryUserDatabase()));
		MockHttpServletRequestBuilder contentTypeResult = postResult.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("foo"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testUpdateANCHistoryNurse() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/ANC/update/historyScreen")
				.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("foo"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testUpdateANCHistoryNurse2() throws Exception {
		// Arrange
		User user = mock(User.class);
		when(user.getName()).thenReturn("Name");
		MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/ANC/update/historyScreen");
		postResult.principal(new UserDatabaseRealm.UserDatabasePrincipal(user, new MemoryUserDatabase()));
		MockHttpServletRequestBuilder contentTypeResult = postResult.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("foo"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	
	@Test
	void testUpdateANCVitalNurse() throws Exception {
		// Arrange
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/ANC/update/vitalScreen")
				.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("foo"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testUpdateANCVitalNurse2() throws Exception {
		// Arrange
		User user = mock(User.class);
		when(user.getName()).thenReturn("Name");
		MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/ANC/update/vitalScreen");
		postResult.principal(new UserDatabaseRealm.UserDatabasePrincipal(user, new MemoryUserDatabase()));
		MockHttpServletRequestBuilder contentTypeResult = postResult.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content((new ObjectMapper()).writeValueAsString("foo"));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(antenatalCareController).build()
				.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
