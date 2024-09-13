package com.iemr.hwc.service.ncdscreening;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.JsonObject;
import com.iemr.hwc.data.anc.WrapperAncFindings;
import com.iemr.hwc.data.anc.WrapperBenInvestigationANC;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.utils.mapper.InputMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

public class TestNCDSCreeningDoctorServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private PrescriptionDetailRepo prescriptionDetailRepo;

	@Mock
	private CommonDoctorServiceImpl commonDoctorServiceImpl;

	@Mock
	private TeleConsultationServiceImpl teleConsultationServiceImpl;

	@Mock
	private CommonNurseServiceImpl commonNurseServiceImpl;

	@Mock
	private CommonServiceImpl commonServiceImpl;

	@Mock
	private SMSGatewayServiceImpl sMSGatewayServiceImpl;

	@Mock
	private CommonNcdScreeningService commonNcdScreeningService;

	@InjectMocks
	private NCDSCreeningDoctorServiceImpl ncdScreeningDoctorServiceImpl;

	private JsonObject requestObj;
	private String authorization;

	@BeforeEach
	public void setUp() {
		requestObj = new JsonObject();
		requestObj.addProperty("key", "value");
		authorization = "Bearer token";
	}

	@Test
	public void testUpdateDoctorData_Success() throws Exception {
		// Setup mock responses
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(new TeleconsultationRequestOBJ());
		when(commonDoctorServiceImpl.updateDocFindings(any(WrapperAncFindings.class))).thenReturn(1);
		when(commonNurseServiceImpl.saveBenInvestigation(any(WrapperBenInvestigationANC.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(anyList())).thenReturn(1);
		when(commonDoctorServiceImpl.updateBenReferDetails(any(JsonObject.class), eq(true))).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(CommonUtilityClass.class), anyBoolean(),
				anyBoolean(), any(TeleconsultationRequestOBJ.class))).thenReturn(1);

		int result = ncdScreeningDoctorServiceImpl.updateDoctorData(requestObj, authorization);

		assertEquals(1, result);
		verify(commonDoctorServiceImpl, times(1)).updateDocFindings(any(WrapperAncFindings.class));
		verify(commonNurseServiceImpl, times(1)).saveBenInvestigation(any(WrapperBenInvestigationANC.class));
		verify(commonNurseServiceImpl, times(1)).saveBenPrescribedDrugsList(anyList());
		verify(commonDoctorServiceImpl, times(1)).updateBenReferDetails(any(JsonObject.class), eq(true));
		verify(commonDoctorServiceImpl, times(1)).updateBenFlowtableAfterDocDataUpdate(any(CommonUtilityClass.class),
				anyBoolean(), anyBoolean(), any(TeleconsultationRequestOBJ.class));
	}

	@Test
	public void testUpdateDoctorData_NullRequestObj() throws Exception {
		int result = ncdScreeningDoctorServiceImpl.updateDoctorData(null, authorization);

		assertEquals(0, result);
	}

	@Test
	public void testUpdateDoctorData_MissingFields() throws Exception {
		JsonObject incompleteRequestObj = new JsonObject();
		incompleteRequestObj.addProperty("missingKey", "missingValue");

		int result = ncdScreeningDoctorServiceImpl.updateDoctorData(incompleteRequestObj, authorization);

		assertEquals(0, result);
	}

	@Test
	public void testUpdateDoctorData_InvalidData() throws Exception {
		JsonObject invalidRequestObj = new JsonObject();
		invalidRequestObj.addProperty("invalidKey", "invalidValue");

		int result = ncdScreeningDoctorServiceImpl.updateDoctorData(invalidRequestObj, authorization);

		assertEquals(0, result);
	}

	@Test
	public void testUpdateDoctorData_ServiceFailure() throws Exception {
		when(commonServiceImpl.createTcRequest(any(JsonObject.class), any(CommonUtilityClass.class), eq(authorization)))
				.thenReturn(new TeleconsultationRequestOBJ());
		when(commonDoctorServiceImpl.updateDocFindings(any(WrapperAncFindings.class))).thenReturn(1);
		when(commonNurseServiceImpl.saveBenInvestigation(any(WrapperBenInvestigationANC.class))).thenReturn(1L);
		when(commonNurseServiceImpl.saveBenPrescribedDrugsList(anyList())).thenReturn(1);
		when(commonDoctorServiceImpl.updateBenReferDetails(any(JsonObject.class), eq(true))).thenReturn(1L);
		when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(any(CommonUtilityClass.class), anyBoolean(),
				anyBoolean(), any(TeleconsultationRequestOBJ.class))).thenReturn(0);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			ncdScreeningDoctorServiceImpl.updateDoctorData(requestObj, authorization);
		});

		assertNotNull(exception);
	}

}
