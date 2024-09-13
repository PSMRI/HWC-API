package com.iemr.hwc.service.nurse;

import static org.mockito.Mockito.*;

import java.security.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.nurse.BenFamilyCancerHistory;
import com.iemr.hwc.data.nurse.BenObstetricCancerHistory;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.fhir.dto.visitDetailsMain.visitDetails.BenVisitsDTO;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;

public class TestNurseServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private BenVisitDetailRepo benVisitDetailRepo;

	@Mock
	private BeneficiaryFlowStatusRepo benFlowStatusRepo;

	@InjectMocks
	private NurseServiceImpl nurseServiceImpl;

	private List<BeneficiaryVisitDetail> benVisitDetails;

	private List<BeneficiaryFlowStatus> beneficiaryFlowStatusList;
	private BeneficiaryVisitDetail beneficiaryVisitDetail;
	private Timestamp lastModifDate;

	@BeforeEach
	public void setUp() {
		// Any setup if needed
	}

	@Test
	public void testSavePatientVisitDetails() {
		// Arrange
		BenFamilyCancerHistory obj = new BenFamilyCancerHistory();
		BenObstetricCancerHistory obj1 = new BenObstetricCancerHistory();
		obj.setCreatedBy("neeraj");
		obj1.setCreatedBy("neeraj");

		when(restTemplate.postForEntity(eq("http://localhost:8080/nurse/testrest1"), eq(obj), eq(String.class)))
				.thenReturn(ResponseEntity.ok("Response1"));
		when(restTemplate.postForEntity(eq("http://localhost:8080/nurse/testrest2"), eq(obj1), eq(String.class)))
				.thenReturn(ResponseEntity.ok("Response2"));

		// Act
		String result = nurseServiceImpl.savePatientVisitDetails();

		// Assert
		assertEquals("hii", result);
		verify(restTemplate, times(1)).postForEntity(eq("http://localhost:8080/nurse/testrest1"), eq(obj),
				eq(String.class));
		verify(restTemplate, times(1)).postForEntity(eq("http://localhost:8080/nurse/testrest2"), eq(obj1),
				eq(String.class));
	}

	@Test
	public void testGetBeneficiaryVisitHistory() {
		Long benRegID = 1L;
		when(benVisitDetailRepo.getBeneficiaryVisitHistory(benRegID)).thenReturn(benVisitDetails);

		String response = nurseServiceImpl.getBeneficiaryVisitHistory(benRegID);

		Gson gson = new Gson();
		String expectedResponse = gson.toJson(benVisitDetails);

		assertEquals(expectedResponse, response);
		verify(benVisitDetailRepo, times(1)).getBeneficiaryVisitHistory(benRegID);
	}

	@Test
	public void testGetVisitByLocationAndLastModifDate_ValidInputs() {
		when(benFlowStatusRepo.getVisitByLocationAndLastModifDate(anyInt(), any(Timestamp.class)))
				.thenReturn(beneficiaryFlowStatusList);
		when(benVisitDetailRepo.getVisitDetailsByVisitIDAndLastModifDate(anyLong(), any(Timestamp.class)))
				.thenReturn(beneficiaryVisitDetail);

		List<BenVisitsDTO> result = nurseServiceImpl.getVisitByLocationAndLastModifDate(1, lastModifDate);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(1L, result.get(0).getBenFlowID());
		verify(benFlowStatusRepo, times(1)).getVisitByLocationAndLastModifDate(anyInt(), any(Timestamp.class));
		verify(benVisitDetailRepo, times(1)).getVisitDetailsByVisitIDAndLastModifDate(anyLong(), any(Timestamp.class));
	}

	@Test
	public void testGetVisitByLocationAndLastModifDate_EmptyResults() {
		when(benFlowStatusRepo.getVisitByLocationAndLastModifDate(anyInt(), any(Timestamp.class)))
				.thenReturn(new ArrayList<>());

		List<BenVisitsDTO> result = nurseServiceImpl.getVisitByLocationAndLastModifDate(1, lastModifDate);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(benFlowStatusRepo, times(1)).getVisitByLocationAndLastModifDate(anyInt(), any(Timestamp.class));
		verify(benVisitDetailRepo, times(0)).getVisitDetailsByVisitIDAndLastModifDate(anyLong(), any(Timestamp.class));
	}

	@Test
	public void testGetVisitByLocationAndLastModifDate_NullInputs() {
		List<BenVisitsDTO> result = nurseServiceImpl.getVisitByLocationAndLastModifDate(null, null);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(benFlowStatusRepo, times(1)).getVisitByLocationAndLastModifDate(isNull(), isNull());
		verify(benVisitDetailRepo, times(0)).getVisitDetailsByVisitIDAndLastModifDate(anyLong(), any(Timestamp.class));
	}
}
