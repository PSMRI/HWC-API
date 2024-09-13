
package com.iemr.hwc.service.cancerScreening;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class TestCSCarestreamServiceImpl
{
	
	public class CSCarestreamServiceImplTest {

	    @Mock
	    private RestTemplate restTemplate;

	    @InjectMocks
	    private CSCarestreamServiceImpl csCarestreamServiceImpl;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testCreateMamographyRequest_Positive() throws Exception {
	        // Arrange
	        ArrayList<Object[]> benDataForCareStream = new ArrayList<>();
	        long benRegID = 1L;
	        long benVisitID = 1L;
	        String Authorization = "Bearer token";

	        JSONObject jsonResponse = new JSONObject();
	        jsonResponse.put("statusCode", 200);

	        ResponseEntity<String> responseEntity = ResponseEntity.ok(jsonResponse.toString());
	        when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
	                .thenReturn(responseEntity);

	        // Act
	        int result = csCarestreamServiceImpl.createMamographyRequest(benDataForCareStream, benRegID, benVisitID, Authorization);

	        // Assert
	        assertEquals(1, result);
	    }

	    @Test
	    public void testCreateMamographyRequest_Negative() throws Exception {
	        // Arrange
	        ArrayList<Object[]> benDataForCareStream = new ArrayList<>();
	        long benRegID = 1L;
	        long benVisitID = 1L;
	        String Authorization = "Bearer token";

	        when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
	                .thenThrow(new RuntimeException("Exception occurred"));

	        // Act
	        int result = csCarestreamServiceImpl.createMamographyRequest(benDataForCareStream, benRegID, benVisitID, Authorization);

	        // Assert
	        assertEquals(0, result);
	    }
	}
}