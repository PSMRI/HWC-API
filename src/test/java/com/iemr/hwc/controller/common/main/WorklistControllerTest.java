package com.iemr.hwc.controller.common.main;

import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorklistControllerTest {

    @Mock
    CommonDoctorServiceImpl commonDoctorServiceImpl;
    @Mock
    CommonNurseServiceImpl commonNurseServiceImpl;
    @Mock
    CommonServiceImpl commonServiceImpl;
    @InjectMocks
    WorklistController worklistController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDocWorkListNew() {
        when(commonDoctorServiceImpl.getDocWorkListNew(anyInt(), anyInt(), anyInt())).thenReturn("getDocWorkListNewResponse");

        String result = worklistController.getDocWorkListNew(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetDocWorkListNewFutureScheduledForTM() {
        when(commonDoctorServiceImpl.getDocWorkListNewFutureScheduledForTM(anyInt(), anyInt(), anyInt())).thenReturn("getDocWorkListNewFutureScheduledForTMResponse");

        String result = worklistController.getDocWorkListNewFutureScheduledForTM(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetNurseWorkListNew() {
        when(commonNurseServiceImpl.getNurseWorkListNew(anyInt(), anyInt())).thenReturn("getNurseWorkListNewResponse");

        String result = worklistController.getNurseWorkListNew(Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetNurseWorkListTcCurrentDateNew() {
        when(commonNurseServiceImpl.getNurseWorkListTcCurrentDate(anyInt(), anyInt())).thenReturn("getNurseWorkListTcCurrentDateResponse");

        String result = worklistController.getNurseWorkListTcCurrentDateNew(Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetNurseWorkListTcFutureDateNew() {
        when(commonNurseServiceImpl.getNurseWorkListTcFutureDate(anyInt(), anyInt())).thenReturn("getNurseWorkListTcFutureDateResponse");

        String result = worklistController.getNurseWorkListTcFutureDateNew(Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetDoctorPreviousSignificantFindings() {
        when(commonDoctorServiceImpl.fetchBenPreviousSignificantFindings(anyLong())).thenReturn("fetchBenPreviousSignificantFindingsResponse");

        String result = worklistController.getDoctorPreviousSignificantFindings("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetLabWorkListNew() {
        when(commonNurseServiceImpl.getLabWorkListNew(anyInt(), anyInt())).thenReturn("getLabWorkListNewResponse");

        String result = worklistController.getLabWorkListNew(Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetRadiologistWorklistNew() {
        when(commonNurseServiceImpl.getRadiologistWorkListNew(anyInt(), anyInt())).thenReturn("getRadiologistWorkListNewResponse");

        String result = worklistController.getRadiologistWorklistNew(Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetOncologistWorklistNew() {
        when(commonNurseServiceImpl.getOncologistWorkListNew(anyInt(), anyInt())).thenReturn("getOncologistWorkListNewResponse");

        String result = worklistController.getOncologistWorklistNew(Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetPharmaWorklistNew() {
        when(commonNurseServiceImpl.getPharmaWorkListNew(anyInt(), anyInt())).thenReturn("getPharmaWorkListNewResponse");

        String result = worklistController.getPharmaWorklistNew(Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetCasesheetPrintData() throws Exception {
        when(commonServiceImpl.getCaseSheetPrintDataForBeneficiary(any(BeneficiaryFlowStatus.class), anyString())).thenReturn("getCaseSheetPrintDataForBeneficiaryResponse");

        String result = worklistController.getCasesheetPrintData("comingReq", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenPastHistory() {
        when(commonServiceImpl.getBenPastHistoryData(anyLong())).thenReturn("getBenPastHistoryDataResponse");

        String result = worklistController.getBenPastHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenTobaccoHistory() {
        when(commonServiceImpl.getPersonalTobaccoHistoryData(anyLong())).thenReturn("getPersonalTobaccoHistoryDataResponse");

        String result = worklistController.getBenTobaccoHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenAlcoholHistory() {
        when(commonServiceImpl.getPersonalAlcoholHistoryData(anyLong())).thenReturn("getPersonalAlcoholHistoryDataResponse");

        String result = worklistController.getBenAlcoholHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenANCAllergyHistory() {
        when(commonServiceImpl.getPersonalAllergyHistoryData(anyLong())).thenReturn("getPersonalAllergyHistoryDataResponse");

        String result = worklistController.getBenANCAllergyHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenMedicationHistory() {
        when(commonServiceImpl.getMedicationHistoryData(anyLong())).thenReturn("getMedicationHistoryDataResponse");

        String result = worklistController.getBenMedicationHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenFamilyHistory() {
        when(commonServiceImpl.getFamilyHistoryData(anyLong())).thenReturn("getFamilyHistoryDataResponse");

        String result = worklistController.getBenFamilyHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenMenstrualHistory() {
        when(commonServiceImpl.getMenstrualHistoryData(anyLong())).thenReturn("getMenstrualHistoryDataResponse");

        String result = worklistController.getBenMenstrualHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenPastObstetricHistory() {
        when(commonServiceImpl.getObstetricHistoryData(anyLong())).thenReturn("getObstetricHistoryDataResponse");

        String result = worklistController.getBenPastObstetricHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenANCComorbidityConditionHistory() {
        when(commonServiceImpl.getComorbidHistoryData(anyLong())).thenReturn("getComorbidHistoryDataResponse");

        String result = worklistController.getBenANCComorbidityConditionHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenOptionalVaccineHistory() {
        when(commonServiceImpl.getChildVaccineHistoryData(anyLong())).thenReturn("getChildVaccineHistoryDataResponse");

        String result = worklistController.getBenOptionalVaccineHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenImmunizationHistory() {
        when(commonServiceImpl.getImmunizationHistoryData(anyLong())).thenReturn("getImmunizationHistoryDataResponse");

        String result = worklistController.getBenImmunizationHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenPerinatalHistory() {
        when(commonServiceImpl.getBenPerinatalHistoryData(anyLong())).thenReturn("getBenPerinatalHistoryDataResponse");

        String result = worklistController.getBenPerinatalHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenFeedingHistory() {
        when(commonServiceImpl.getBenFeedingHistoryData(anyLong())).thenReturn("getBenFeedingHistoryDataResponse");

        String result = worklistController.getBenFeedingHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenDevelopmentHistory() {
        when(commonServiceImpl.getBenDevelopmentHistoryData(anyLong())).thenReturn("getBenDevelopmentHistoryDataResponse");

        String result = worklistController.getBenDevelopmentHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBeneficiaryCaseSheetHistory() throws IEMRException {
        when(commonServiceImpl.getBenPreviousVisitDataForCaseRecord(anyString())).thenReturn("getBenPreviousVisitDataForCaseRecordResponse");

        String result = worklistController.getBeneficiaryCaseSheetHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetTCSpecialistWorkListNew() {
        when(commonDoctorServiceImpl.getTCSpecialistWorkListNewForTM(anyInt(), anyInt(), anyInt())).thenReturn("getTCSpecialistWorkListNewForTMResponse");

        String result = worklistController.getTCSpecialistWorkListNew(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetTCSpecialistWorkListNewPatientApp() {
        when(commonDoctorServiceImpl.getTCSpecialistWorkListNewForTMPatientApp(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn("getTCSpecialistWorkListNewForTMPatientAppResponse");

        String result = worklistController.getTCSpecialistWorkListNewPatientApp(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetTCSpecialistWorklistFutureScheduled() {
        when(commonDoctorServiceImpl.getTCSpecialistWorkListNewFutureScheduledForTM(anyInt(), anyInt(), anyInt())).thenReturn("getTCSpecialistWorkListNewFutureScheduledForTMResponse");

        String result = worklistController.getTCSpecialistWorklistFutureScheduled(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetKMFile() {
        when(commonServiceImpl.getOpenKMDocURL(anyString(), anyString())).thenReturn("getOpenKMDocURLResponse");

        String result = worklistController.getKMFile("request", "Authorization");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenPhysicalHistory() {
        when(commonServiceImpl.getBenPhysicalHistory(anyLong())).thenReturn("getBenPhysicalHistoryResponse");

        String result = worklistController.getBenPhysicalHistory("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenSymptomaticQuestionnaireDetails() throws Exception {
        when(commonServiceImpl.getBenSymptomaticQuestionnaireDetailsData(anyLong())).thenReturn("getBenSymptomaticQuestionnaireDetailsDataResponse");

        String result = worklistController.getBenSymptomaticQuestionnaireDetails("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenPreviousDiabetesHistoryDetails() throws Exception {
        when(commonServiceImpl.getBenPreviousDiabetesData(anyLong())).thenReturn("getBenPreviousDiabetesDataResponse");

        String result = worklistController.getBenPreviousDiabetesHistoryDetails("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetMmuNurseWorklistNew() {
        when(commonNurseServiceImpl.getMmuNurseWorkListNew(anyInt(), anyInt())).thenReturn("getMmuNurseWorkListNewResponse");

        String result = worklistController.getMmuNurseWorklistNew(Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenPreviousReferralHistoryDetails() throws Exception {
        when(commonServiceImpl.getBenPreviousReferralData(anyLong())).thenReturn("getBenPreviousReferralDataResponse");

        String result = worklistController.getBenPreviousReferralHistoryDetails("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetProviderSpecificData() throws IEMRException {
        when(commonServiceImpl.getProviderSpecificData(anyString())).thenReturn("getProviderSpecificDataResponse");

        String result = worklistController.getProviderSpecificData("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testCalculateBMIStatus() throws IEMRException {
        when(commonNurseServiceImpl.calculateBMIStatus(anyString())).thenReturn("calculateBMIStatusResponse");

        String result = worklistController.calculateBMIStatus("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBenImmunizationServiceHistory() throws Exception {
        when(commonServiceImpl.getBenImmunizationServiceHistory(anyLong())).thenReturn("getBenImmunizationServiceHistoryResponse");

        String result = worklistController.getBenImmunizationServiceHistory(new CommonUtilityClass());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testSaveBeneficiaryVisitDetail() {
        when(commonNurseServiceImpl.updateBeneficiaryStatus(anyChar(), anyLong())).thenReturn(Integer.valueOf(0));

        String result = worklistController.saveBeneficiaryVisitDetail("comingRequest");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testExtendRedisSession() {
        String result = worklistController.extendRedisSession();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testDeletePrescribedMedicine() {
        when(commonDoctorServiceImpl.deletePrescribedMedicine(any(JSONObject.class))).thenReturn("deletePrescribedMedicineResponse");

        String result = worklistController.deletePrescribedMedicine("requestOBJ");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

