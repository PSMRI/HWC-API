package com.iemr.hwc.controller.uptsu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemr.hwc.data.uptsu.Referred104Details;
import com.iemr.hwc.service.uptsu.UptsuService;

@ExtendWith(MockitoExtension.class)
class UPTechnicalSupportUnitControllerTest {
    @InjectMocks
    private UPTechnicalSupportUnitController uPTechnicalSupportUnitController;

    @Mock
    private UptsuService uptsuService;

    @Test
    void testGetActionMaster() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/uptsu/get/action-master");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(uPTechnicalSupportUnitController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testGetActionMaster2() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/uptsu/get/action-master");
        requestBuilder.content("AXAXAXAX".getBytes("UTF-8"));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(uPTechnicalSupportUnitController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testGetActionMaster3() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/uptsu/get/action-master");
        requestBuilder.accept("https://example.org/example");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(uPTechnicalSupportUnitController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testGetActionMaster4() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/uptsu/get/action-master");
        requestBuilder.content(new byte[]{});

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(uPTechnicalSupportUnitController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testGetWolklist104Data() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/uptsu/getWorklistByVanID/{vanId}", 1);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(uPTechnicalSupportUnitController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testGetWolklist104Data2() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/uptsu/getWorklistByVanID/{vanId}", 1);
        requestBuilder.content("AXAXAXAX".getBytes("UTF-8"));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(uPTechnicalSupportUnitController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testGetWolklist104Data3() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/uptsu/getWorklistByVanID/{vanId}", 1);
        requestBuilder.accept("https://example.org/example");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(uPTechnicalSupportUnitController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testGetWolklist104Data4() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/uptsu/getWorklistByVanID/{vanId}", 1);
        requestBuilder.content(new byte[]{});

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(uPTechnicalSupportUnitController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testSaveReferred104Details() throws Exception {
        // Arrange
        Referred104Details referred104Details = new Referred104Details();
        referred104Details.setAction("Action");
        referred104Details.setActionId(1);
        referred104Details.setAlgorithm("Algorithm");
        referred104Details.setBenCallID("Ben Call ID");
        referred104Details.setBeneficiaryID(1L);
        referred104Details.setBeneficiaryRegID(1L);
        referred104Details.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        referred104Details.setId(1);
        referred104Details.setParkingPlaceID(1);
        referred104Details.setPatientAge("Patient Age");
        referred104Details.setPatientGenderID(1);
        referred104Details.setPatientName("Patient Name");
        referred104Details.setPresentChiefComplaint("Present Chief Complaint");
        referred104Details.setPresentChiefComplaintID("Present Chief Complaint ID");
        referred104Details.setProviderServiceMapID(1);
        referred104Details.setRecommendedAction("Recommended Action");
        referred104Details.setRemarks("Remarks");
        referred104Details.setSelecteDiagnosis("Selecte Diagnosis");
        referred104Details.setSelecteDiagnosisID("Selecte Diagnosis ID");
        referred104Details.setServiceID(1);
        referred104Details.setSessionID(1);
        referred104Details.setVanID(1);
        String writeValueAsStringResult = (new ObjectMapper()).writeValueAsString(referred104Details);
        String content = (new ObjectMapper()).writeValueAsString(writeValueAsStringResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/uptsu/submit/closevisit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(uPTechnicalSupportUnitController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testSaveReferred104Details2() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/uptsu/submit/closevisit");
        postResult.accept("https://example.org/example");

        Referred104Details referred104Details = new Referred104Details();
        referred104Details.setAction("Action");
        referred104Details.setActionId(1);
        referred104Details.setAlgorithm("Algorithm");
        referred104Details.setBenCallID("Ben Call ID");
        referred104Details.setBeneficiaryID(1L);
        referred104Details.setBeneficiaryRegID(1L);
        referred104Details.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        referred104Details.setId(1);
        referred104Details.setParkingPlaceID(1);
        referred104Details.setPatientAge("Patient Age");
        referred104Details.setPatientGenderID(1);
        referred104Details.setPatientName("Patient Name");
        referred104Details.setPresentChiefComplaint("Present Chief Complaint");
        referred104Details.setPresentChiefComplaintID("Present Chief Complaint ID");
        referred104Details.setProviderServiceMapID(1);
        referred104Details.setRecommendedAction("Recommended Action");
        referred104Details.setRemarks("Remarks");
        referred104Details.setSelecteDiagnosis("Selecte Diagnosis");
        referred104Details.setSelecteDiagnosisID("Selecte Diagnosis ID");
        referred104Details.setServiceID(1);
        referred104Details.setSessionID(1);
        referred104Details.setVanID(1);
        String writeValueAsStringResult = (new ObjectMapper()).writeValueAsString(referred104Details);
        String content = (new ObjectMapper()).writeValueAsString(writeValueAsStringResult);
        MockHttpServletRequestBuilder requestBuilder = postResult.contentType(MediaType.APPLICATION_JSON).content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(uPTechnicalSupportUnitController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
    }
}
