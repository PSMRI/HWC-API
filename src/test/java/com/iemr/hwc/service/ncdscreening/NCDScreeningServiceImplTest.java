package com.iemr.hwc.service.ncdscreening;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.anc.BenFamilyHistory;
import com.iemr.hwc.data.anc.BenPersonalHabit;
import com.iemr.hwc.data.location.Districts;
import com.iemr.hwc.data.location.States;
import com.iemr.hwc.data.ncdScreening.BreastCancerScreening;
import com.iemr.hwc.data.ncdScreening.CbacDetails;
import com.iemr.hwc.data.ncdScreening.DiabetesScreening;
import com.iemr.hwc.data.ncdScreening.HypertensionScreening;
import com.iemr.hwc.data.ncdScreening.IDRSData;
import com.iemr.hwc.data.ncdScreening.PhysicalActivityType;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.provider.ProviderServiceMapping;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.location.ZoneDistrictMapping;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.BreastCancerScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.CbacDetailsRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.CervicalCancerScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.DiabetesScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.HypertensionScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.IDRSDataRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.OralCancerScreeningRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class NCDScreeningServiceImplTest {
  @Mock
  private BenAdherenceRepo benAdherenceRepo;

  @Mock
  private BenChiefComplaintRepo benChiefComplaintRepo;

  @Mock
  private BenVisitDetailRepo benVisitDetailRepo;

  @Mock
  private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

  @Mock
  private BreastCancerScreeningRepo breastCancerScreeningRepo;

  @Mock
  private CDSSRepo cDSSRepo;

  @Mock
  private CbacDetailsRepo cbacDetailsRepo;

  @Mock
  private CervicalCancerScreeningRepo cervicalCancerScreeningRepo;

  @Mock
  private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

  @Mock
  private CommonDoctorServiceImpl commonDoctorServiceImpl;

  @Mock
  private CommonNcdScreeningService commonNcdScreeningService;

  @Mock
  private CommonNurseServiceImpl commonNurseServiceImpl;

  @Mock
  private CommonServiceImpl commonServiceImpl;

  @Mock
  private DiabetesScreeningRepo diabetesScreeningRepo;

  @Mock
  private HypertensionScreeningRepo hypertensionScreeningRepo;

  @Mock
  private IDRSDataRepo iDRSDataRepo;

  @Mock
  private LabTechnicianServiceImpl labTechnicianServiceImpl;

  @Mock
  private NCDSCreeningDoctorServiceImpl nCDSCreeningDoctorServiceImpl;

  @Mock
  private NCDScreeningNurseServiceImpl nCDScreeningNurseServiceImpl;

  @InjectMocks
  private NCDScreeningServiceImpl nCDScreeningServiceImpl;

  @Mock
  private OralCancerScreeningRepo oralCancerScreeningRepo;

  @Mock
  private PrescriptionDetailRepo prescriptionDetailRepo;

  @Mock
  private SMSGatewayServiceImpl sMSGatewayServiceImpl;

  @Mock
  private TeleConsultationServiceImpl teleConsultationServiceImpl;

  @Test
  void testDeleteVisitDetails() throws Exception {
    // Arrange
    when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
    when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

    JsonObject requestOBJ = new JsonObject();
    requestOBJ.add("visitDetails", new JsonArray(3));

    // Act
    nCDScreeningServiceImpl.deleteVisitDetails(requestOBJ);

    // Assert
    verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
    verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
    verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
    verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
    verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
  }

  @Test
  void testDeleteVisitDetails2() throws Exception {
    // Arrange
    when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
    when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenThrow(new RuntimeException("visitDetails"));

    JsonObject requestOBJ = new JsonObject();
    requestOBJ.add("visitDetails", new JsonArray(3));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> nCDScreeningServiceImpl.deleteVisitDetails(requestOBJ));
    verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
    verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
    verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
    verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
  }

  @Test
  void testDeleteVisitDetails3() throws Exception {
    // Arrange
    when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(null);

    JsonObject requestOBJ = new JsonObject();
    requestOBJ.add("visitDetails", new JsonArray(3));

    // Act
    nCDScreeningServiceImpl.deleteVisitDetails(requestOBJ);

    // Assert
    verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
  }

  @Test
  void testDeleteVisitDetails4() throws Exception {
    // Arrange
    when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
    when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

    JsonObject requestOBJ = new JsonObject();
    requestOBJ.add("Property", new JsonArray(3));
    requestOBJ.add("visitDetails", new JsonArray(3));

    // Act
    nCDScreeningServiceImpl.deleteVisitDetails(requestOBJ);

    // Assert
    verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
    verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
    verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
    verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
    verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
  }

  @Test
  void testDeleteVisitDetails5() throws Exception {
    // Arrange
    when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
    when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

    JsonObject requestOBJ = new JsonObject();
    requestOBJ.addProperty("Property", "visitDetails");
    requestOBJ.add("visitDetails", new JsonArray(3));

    // Act
    nCDScreeningServiceImpl.deleteVisitDetails(requestOBJ);

    // Assert
    verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
    verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
    verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
    verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
    verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
  }

  @Test
  void testDeleteVisitDetails6() throws Exception {
    // Arrange
    when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
    when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

    JsonObject requestOBJ = new JsonObject();
    requestOBJ.addProperty("Property", Integer.valueOf(1));
    requestOBJ.add("visitDetails", new JsonArray(3));

    // Act
    nCDScreeningServiceImpl.deleteVisitDetails(requestOBJ);

    // Assert
    verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
    verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
    verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
    verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
    verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
  }

  @Test
  void testDeleteVisitDetails7() throws Exception {
    // Arrange
    when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
    when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
    when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

    JsonObject requestOBJ = new JsonObject();
    requestOBJ.addProperty("Property", false);
    requestOBJ.add("visitDetails", new JsonArray(3));

    // Act
    nCDScreeningServiceImpl.deleteVisitDetails(requestOBJ);

    // Assert
    verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
    verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
    verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
    verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
    verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
  }

  @Test
  void testDeleteVisitDetails8() throws Exception {
    // Arrange
    JsonElement value = mock(JsonElement.class);
    when(value.isJsonNull()).thenReturn(true);

    JsonObject requestOBJ = new JsonObject();
    requestOBJ.add("visitDetails", value);

    // Act
    nCDScreeningServiceImpl.deleteVisitDetails(requestOBJ);

    // Assert that nothing has changed
    verify(value).isJsonNull();
  }

  @Test
  void testDeleteVisitDetails9() throws Exception {
    // Arrange
    JsonElement value = mock(JsonElement.class);
    when(value.isJsonNull()).thenThrow(new RuntimeException("visitDetails"));

    JsonObject requestOBJ = new JsonObject();
    requestOBJ.add("visitDetails", value);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> nCDScreeningServiceImpl.deleteVisitDetails(requestOBJ));
    verify(value).isJsonNull();
  }

  @Test
  void testSaveBenVisitDetails() throws Exception {
    // Arrange
    JsonObject visitDetailsOBJ = new JsonObject();

    CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
    nurseUtilityClass.setAge(1);
    nurseUtilityClass.setAgeUnits("Age Units");
    nurseUtilityClass.setAuthorization("JaneDoe");
    nurseUtilityClass.setBenFlowID(1L);
    nurseUtilityClass.setBenRegID(1L);
    nurseUtilityClass.setBenVisitID(1L);
    nurseUtilityClass.setBeneficiaryID(1L);
    nurseUtilityClass.setBeneficiaryRegID(1L);
    nurseUtilityClass.setBeneficiaryRegId(1L);
    nurseUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    nurseUtilityClass.setDistrictID(1);
    nurseUtilityClass.setDistrictName("District Name");
    nurseUtilityClass.setFacilityID(1);
    nurseUtilityClass.setFirstName("Jane");
    nurseUtilityClass.setGenderID((short) 1);
    nurseUtilityClass.setGenderName("Gender Name");
    nurseUtilityClass.setIsCovidFlowDone(true);
    nurseUtilityClass.setIsMobile(true);
    nurseUtilityClass.setIsSpecialist(true);
    nurseUtilityClass.setLastName("Doe");
    nurseUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    nurseUtilityClass.setParkingPlaceID(1);
    nurseUtilityClass.setParkingPlaceId(1);
    nurseUtilityClass.setPrescriptionID(1L);
    nurseUtilityClass.setProviderServiceMapID(1);
    nurseUtilityClass.setServiceID((short) 1);
    nurseUtilityClass.setSessionID(1);
    nurseUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
    nurseUtilityClass.setSubVisitCategory("Sub Visit Category");
    nurseUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
    nurseUtilityClass.setVanID(1);
    nurseUtilityClass.setVanId(1);
    nurseUtilityClass.setVillageId(1);
    nurseUtilityClass.setVillageName("Village Name");
    nurseUtilityClass.setVisitCategoryID(1);
    nurseUtilityClass.setVisitCode(1L);

    // Act and Assert
    assertTrue(nCDScreeningServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass).isEmpty());
  }

  @Test
  void testSaveBenVisitDetails2() throws Exception {
    // Arrange
    CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
    nurseUtilityClass.setAge(1);
    nurseUtilityClass.setAgeUnits("Age Units");
    nurseUtilityClass.setAuthorization("JaneDoe");
    nurseUtilityClass.setBenFlowID(1L);
    nurseUtilityClass.setBenRegID(1L);
    nurseUtilityClass.setBenVisitID(1L);
    nurseUtilityClass.setBeneficiaryID(1L);
    nurseUtilityClass.setBeneficiaryRegID(1L);
    nurseUtilityClass.setBeneficiaryRegId(1L);
    nurseUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    nurseUtilityClass.setDistrictID(1);
    nurseUtilityClass.setDistrictName("District Name");
    nurseUtilityClass.setFacilityID(1);
    nurseUtilityClass.setFirstName("Jane");
    nurseUtilityClass.setGenderID((short) 1);
    nurseUtilityClass.setGenderName("Gender Name");
    nurseUtilityClass.setIsCovidFlowDone(true);
    nurseUtilityClass.setIsMobile(true);
    nurseUtilityClass.setIsSpecialist(true);
    nurseUtilityClass.setLastName("Doe");
    nurseUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    nurseUtilityClass.setParkingPlaceID(1);
    nurseUtilityClass.setParkingPlaceId(1);
    nurseUtilityClass.setPrescriptionID(1L);
    nurseUtilityClass.setProviderServiceMapID(1);
    nurseUtilityClass.setServiceID((short) 1);
    nurseUtilityClass.setSessionID(1);
    nurseUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
    nurseUtilityClass.setSubVisitCategory("Sub Visit Category");
    nurseUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
    nurseUtilityClass.setVanID(1);
    nurseUtilityClass.setVanId(1);
    nurseUtilityClass.setVillageId(1);
    nurseUtilityClass.setVillageName("Village Name");
    nurseUtilityClass.setVisitCategoryID(1);
    nurseUtilityClass.setVisitCode(1L);

    // Act and Assert
    assertTrue(nCDScreeningServiceImpl.saveBenVisitDetails(null, nurseUtilityClass).isEmpty());
  }

  @Test
  void testSaveBenVisitDetails3() throws Exception {
    // Arrange
    JsonObject visitDetailsOBJ = new JsonObject();
    visitDetailsOBJ.add("Property", new JsonArray(3));

    CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
    nurseUtilityClass.setAge(1);
    nurseUtilityClass.setAgeUnits("Age Units");
    nurseUtilityClass.setAuthorization("JaneDoe");
    nurseUtilityClass.setBenFlowID(1L);
    nurseUtilityClass.setBenRegID(1L);
    nurseUtilityClass.setBenVisitID(1L);
    nurseUtilityClass.setBeneficiaryID(1L);
    nurseUtilityClass.setBeneficiaryRegID(1L);
    nurseUtilityClass.setBeneficiaryRegId(1L);
    nurseUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    nurseUtilityClass.setDistrictID(1);
    nurseUtilityClass.setDistrictName("District Name");
    nurseUtilityClass.setFacilityID(1);
    nurseUtilityClass.setFirstName("Jane");
    nurseUtilityClass.setGenderID((short) 1);
    nurseUtilityClass.setGenderName("Gender Name");
    nurseUtilityClass.setIsCovidFlowDone(true);
    nurseUtilityClass.setIsMobile(true);
    nurseUtilityClass.setIsSpecialist(true);
    nurseUtilityClass.setLastName("Doe");
    nurseUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    nurseUtilityClass.setParkingPlaceID(1);
    nurseUtilityClass.setParkingPlaceId(1);
    nurseUtilityClass.setPrescriptionID(1L);
    nurseUtilityClass.setProviderServiceMapID(1);
    nurseUtilityClass.setServiceID((short) 1);
    nurseUtilityClass.setSessionID(1);
    nurseUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
    nurseUtilityClass.setSubVisitCategory("Sub Visit Category");
    nurseUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
    nurseUtilityClass.setVanID(1);
    nurseUtilityClass.setVanId(1);
    nurseUtilityClass.setVillageId(1);
    nurseUtilityClass.setVillageName("Village Name");
    nurseUtilityClass.setVisitCategoryID(1);
    nurseUtilityClass.setVisitCode(1L);

    // Act and Assert
    assertTrue(nCDScreeningServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass).isEmpty());
  }

  @Test
  void testSaveBenVisitDetails4() throws Exception {
    // Arrange
    JsonObject visitDetailsOBJ = new JsonObject();
    visitDetailsOBJ.addProperty("visitDetails", (String) null);

    CommonUtilityClass nurseUtilityClass = new CommonUtilityClass();
    nurseUtilityClass.setAge(1);
    nurseUtilityClass.setAgeUnits("Age Units");
    nurseUtilityClass.setAuthorization("JaneDoe");
    nurseUtilityClass.setBenFlowID(1L);
    nurseUtilityClass.setBenRegID(1L);
    nurseUtilityClass.setBenVisitID(1L);
    nurseUtilityClass.setBeneficiaryID(1L);
    nurseUtilityClass.setBeneficiaryRegID(1L);
    nurseUtilityClass.setBeneficiaryRegId(1L);
    nurseUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    nurseUtilityClass.setDistrictID(1);
    nurseUtilityClass.setDistrictName("District Name");
    nurseUtilityClass.setFacilityID(1);
    nurseUtilityClass.setFirstName("Jane");
    nurseUtilityClass.setGenderID((short) 1);
    nurseUtilityClass.setGenderName("Gender Name");
    nurseUtilityClass.setIsCovidFlowDone(true);
    nurseUtilityClass.setIsMobile(true);
    nurseUtilityClass.setIsSpecialist(true);
    nurseUtilityClass.setLastName("Doe");
    nurseUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    nurseUtilityClass.setParkingPlaceID(1);
    nurseUtilityClass.setParkingPlaceId(1);
    nurseUtilityClass.setPrescriptionID(1L);
    nurseUtilityClass.setProviderServiceMapID(1);
    nurseUtilityClass.setServiceID((short) 1);
    nurseUtilityClass.setSessionID(1);
    nurseUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
    nurseUtilityClass.setSubVisitCategory("Sub Visit Category");
    nurseUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
    nurseUtilityClass.setVanID(1);
    nurseUtilityClass.setVanId(1);
    nurseUtilityClass.setVillageId(1);
    nurseUtilityClass.setVillageName("Village Name");
    nurseUtilityClass.setVisitCategoryID(1);
    nurseUtilityClass.setVisitCode(1L);

    // Act and Assert
    assertTrue(nCDScreeningServiceImpl.saveBenVisitDetails(visitDetailsOBJ, nurseUtilityClass).isEmpty());
  }

  @Test
  void testSaveBenNCDCareHistoryDetails() throws Exception {
    // Arrange, Act and Assert
    assertEquals(1L, nCDScreeningServiceImpl.saveBenNCDCareHistoryDetails(new JsonObject(), 1L, 1L).longValue());
    assertEquals(1L, nCDScreeningServiceImpl.saveBenNCDCareHistoryDetails(null, 1L, 1L).longValue());
  }

  @Test
  void testSaveBenNCDCareHistoryDetails2() throws Exception {
    // Arrange
    JsonObject ncdCareHistoryOBJ = new JsonObject();
    ncdCareHistoryOBJ.add("Property", new JsonArray(3));

    // Act and Assert
    assertEquals(1L, nCDScreeningServiceImpl.saveBenNCDCareHistoryDetails(ncdCareHistoryOBJ, 1L, 1L).longValue());
  }

  @Test
  void testSaveBenNCDCareHistoryDetails3() throws Exception {
    // Arrange
    JsonObject ncdCareHistoryOBJ = new JsonObject();
    ncdCareHistoryOBJ.addProperty("pastHistory", (String) null);

    // Act and Assert
    assertEquals(1L, nCDScreeningServiceImpl.saveBenNCDCareHistoryDetails(ncdCareHistoryOBJ, 1L, 1L).longValue());
  }

  @Test
  void testSaveBenNCDCareVitalDetails() throws Exception {
    // Arrange
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
        .thenReturn(1L);
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
        .thenReturn(1L);

    // Act
    Long actualSaveBenNCDCareVitalDetailsResult = nCDScreeningServiceImpl.saveBenNCDCareVitalDetails(new JsonObject(),
        1L, 1L);

    // Assert
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
    assertEquals(1L, actualSaveBenNCDCareVitalDetailsResult.longValue());
  }

  @Test
  void testSaveBenNCDCareVitalDetails2() throws Exception {
    // Arrange
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
        .thenReturn(0L);
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
        .thenReturn(1L);

    // Act
    Long actualSaveBenNCDCareVitalDetailsResult = nCDScreeningServiceImpl.saveBenNCDCareVitalDetails(new JsonObject(),
        1L, 1L);

    // Assert
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
    assertNull(actualSaveBenNCDCareVitalDetailsResult);
  }

  @Test
  void testSaveBenNCDCareVitalDetails3() throws Exception {
    // Arrange
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
        .thenReturn(null);
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
        .thenReturn(1L);

    // Act
    Long actualSaveBenNCDCareVitalDetailsResult = nCDScreeningServiceImpl.saveBenNCDCareVitalDetails(new JsonObject(),
        1L, 1L);

    // Assert
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
    assertNull(actualSaveBenNCDCareVitalDetailsResult);
  }

  @Test
  void testSaveBenNCDCareVitalDetails4() throws Exception {
    // Arrange
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
        .thenReturn(1L);
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
        .thenReturn(0L);

    // Act
    Long actualSaveBenNCDCareVitalDetailsResult = nCDScreeningServiceImpl.saveBenNCDCareVitalDetails(new JsonObject(),
        1L, 1L);

    // Assert
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
    assertNull(actualSaveBenNCDCareVitalDetailsResult);
  }

  @Test
  void testSaveBenNCDCareVitalDetails5() throws Exception {
    // Arrange
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
        .thenReturn(1L);
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
        .thenReturn(null);

    // Act
    Long actualSaveBenNCDCareVitalDetailsResult = nCDScreeningServiceImpl.saveBenNCDCareVitalDetails(new JsonObject(),
        1L, 1L);

    // Assert
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
    assertNull(actualSaveBenNCDCareVitalDetailsResult);
  }

  @Test
  void testSaveBenNCDCareVitalDetails6() throws Exception {
    // Arrange, Act and Assert
    assertNull(nCDScreeningServiceImpl.saveBenNCDCareVitalDetails(null, 1L, 1L));
  }

  @Test
  void testSaveBenNCDCareVitalDetails7() throws Exception {
    // Arrange
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
        .thenReturn(1L);
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
        .thenReturn(1L);

    JsonObject vitalDetailsOBJ = new JsonObject();
    vitalDetailsOBJ.add("Property", new JsonArray(3));

    // Act
    Long actualSaveBenNCDCareVitalDetailsResult = nCDScreeningServiceImpl.saveBenNCDCareVitalDetails(vitalDetailsOBJ,
        1L, 1L);

    // Assert
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
    assertEquals(1L, actualSaveBenNCDCareVitalDetailsResult.longValue());
  }

  @Test
  void testSaveBenNCDCareVitalDetails8() throws Exception {
    // Arrange
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
        .thenReturn(1L);
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
        .thenReturn(1L);

    JsonObject vitalDetailsOBJ = new JsonObject();
    vitalDetailsOBJ.addProperty("Property", "42");

    // Act
    Long actualSaveBenNCDCareVitalDetailsResult = nCDScreeningServiceImpl.saveBenNCDCareVitalDetails(vitalDetailsOBJ,
        1L, 1L);

    // Assert
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
    assertEquals(1L, actualSaveBenNCDCareVitalDetailsResult.longValue());
  }

  @Test
  void testSaveBenNCDCareVitalDetails9() throws Exception {
    // Arrange
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
        .thenReturn(1L);
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
        .thenReturn(1L);

    JsonObject vitalDetailsOBJ = new JsonObject();
    vitalDetailsOBJ.addProperty("Property", Integer.valueOf(1));

    // Act
    Long actualSaveBenNCDCareVitalDetailsResult = nCDScreeningServiceImpl.saveBenNCDCareVitalDetails(vitalDetailsOBJ,
        1L, 1L);

    // Assert
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
    assertEquals(1L, actualSaveBenNCDCareVitalDetailsResult.longValue());
  }

  @Test
  void testSaveBenNCDCareVitalDetails10() throws Exception {
    // Arrange
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
        .thenReturn(1L);
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(Mockito.<BenPhysicalVitalDetail>any()))
        .thenReturn(1L);

    JsonObject vitalDetailsOBJ = new JsonObject();
    vitalDetailsOBJ.addProperty("Property", true);

    // Act
    Long actualSaveBenNCDCareVitalDetailsResult = nCDScreeningServiceImpl.saveBenNCDCareVitalDetails(vitalDetailsOBJ,
        1L, 1L);

    // Assert
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalVitalDetails(isA(BenPhysicalVitalDetail.class));
    assertEquals(1L, actualSaveBenNCDCareVitalDetailsResult.longValue());
  }

  @Test
  void testSaveBenNCDCareVitalDetails11() throws Exception {
    // Arrange
    when(commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(Mockito.<BenAnthropometryDetail>any()))
        .thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class,
        () -> nCDScreeningServiceImpl.saveBenNCDCareVitalDetails(new JsonObject(), 1L, 1L));
    verify(commonNurseServiceImpl).saveBeneficiaryPhysicalAnthropometryDetails(isA(BenAnthropometryDetail.class));
  }

  @Test
  void testGetNCDScreeningDetails() {
    // Arrange
    when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Beneficiary Physical Anthropometry Details");
    when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Beneficiary Physical Vital Details");
    when(nCDScreeningNurseServiceImpl.getNCDScreeningDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Ncd Screening Details");

    // Act
    String actualNCDScreeningDetails = nCDScreeningServiceImpl.getNCDScreeningDetails(1L, 1L);

    // Assert
    verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
    verify(commonNurseServiceImpl).getBeneficiaryPhysicalVitalDetails(isA(Long.class), isA(Long.class));
    verify(nCDScreeningNurseServiceImpl).getNCDScreeningDetails(isA(Long.class), isA(Long.class));
    assertEquals("{vitalDetails=Beneficiary Physical Vital Details, ncdScreeningDetails=Ncd Screening Details,"
        + " anthropometryDetails=Beneficiary Physical Anthropometry Details}", actualNCDScreeningDetails);
  }

  @Test
  void testGetNCDScreeningDetails2() {
    // Arrange
    when(nCDScreeningNurseServiceImpl.getNCDScreeningDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> nCDScreeningServiceImpl.getNCDScreeningDetails(1L, 1L));
    verify(nCDScreeningNurseServiceImpl).getNCDScreeningDetails(isA(Long.class), isA(Long.class));
  }

  @Test
  void testGetNCDScreeningDetails3() {
    // Arrange
    when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(null);
    when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Beneficiary Physical Vital Details");
    when(nCDScreeningNurseServiceImpl.getNCDScreeningDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Ncd Screening Details");

    // Act
    String actualNCDScreeningDetails = nCDScreeningServiceImpl.getNCDScreeningDetails(1L, 1L);

    // Assert
    verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
    verify(commonNurseServiceImpl).getBeneficiaryPhysicalVitalDetails(isA(Long.class), isA(Long.class));
    verify(nCDScreeningNurseServiceImpl).getNCDScreeningDetails(isA(Long.class), isA(Long.class));
    assertEquals("{}", actualNCDScreeningDetails);
  }

  @Test
  void testGetNCDScreeningDetails4() {
    // Arrange
    when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Beneficiary Physical Anthropometry Details");
    when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(null);
    when(nCDScreeningNurseServiceImpl.getNCDScreeningDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Ncd Screening Details");

    // Act
    String actualNCDScreeningDetails = nCDScreeningServiceImpl.getNCDScreeningDetails(1L, 1L);

    // Assert
    verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
    verify(commonNurseServiceImpl).getBeneficiaryPhysicalVitalDetails(isA(Long.class), isA(Long.class));
    verify(nCDScreeningNurseServiceImpl).getNCDScreeningDetails(isA(Long.class), isA(Long.class));
    assertEquals("{}", actualNCDScreeningDetails);
  }

  @Test
  void testGetNCDScreeningDetails5() {
    // Arrange
    when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Beneficiary Physical Anthropometry Details");
    when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Beneficiary Physical Vital Details");
    when(nCDScreeningNurseServiceImpl.getNCDScreeningDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(null);

    // Act
    String actualNCDScreeningDetails = nCDScreeningServiceImpl.getNCDScreeningDetails(1L, 1L);

    // Assert
    verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
    verify(commonNurseServiceImpl).getBeneficiaryPhysicalVitalDetails(isA(Long.class), isA(Long.class));
    verify(nCDScreeningNurseServiceImpl).getNCDScreeningDetails(isA(Long.class), isA(Long.class));
    assertEquals("{}", actualNCDScreeningDetails);
  }

  @Test
  void testGetNcdScreeningVisitCnt() {
    // Arrange
    when(beneficiaryFlowStatusRepo.getNcdScreeningVisitCount(Mockito.<Long>any())).thenReturn(3L);

    // Act
    String actualNcdScreeningVisitCnt = nCDScreeningServiceImpl.getNcdScreeningVisitCnt(1L);

    // Assert
    verify(beneficiaryFlowStatusRepo).getNcdScreeningVisitCount(isA(Long.class));
    assertEquals("{\"ncdScreeningVisitCount\":4}", actualNcdScreeningVisitCnt);
  }

  @Test
  void testGetNcdScreeningVisitCnt2() {
    // Arrange
    when(beneficiaryFlowStatusRepo.getNcdScreeningVisitCount(Mockito.<Long>any()))
        .thenThrow(new RuntimeException("ncdScreeningVisitCount"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> nCDScreeningServiceImpl.getNcdScreeningVisitCnt(1L));
    verify(beneficiaryFlowStatusRepo).getNcdScreeningVisitCount(isA(Long.class));
  }

  @Test
  void testGettersAndSetters() {
    NCDScreeningServiceImpl ncdScreeningServiceImpl = new NCDScreeningServiceImpl();

    // Act
    ncdScreeningServiceImpl.setBeneficiaryFlowStatusRepo(mock(BeneficiaryFlowStatusRepo.class));
    ncdScreeningServiceImpl.setCommonBenStatusFlowServiceImpl(new CommonBenStatusFlowServiceImpl());
    ncdScreeningServiceImpl.setCommonDoctorServiceImpl(new CommonDoctorServiceImpl());
    ncdScreeningServiceImpl.setCommonNurseServiceImpl(new CommonNurseServiceImpl());
    ncdScreeningServiceImpl.setLabTechnicianServiceImpl(new LabTechnicianServiceImpl());
    ncdScreeningServiceImpl.setNcdScreeningNurseServiceImpl(new NCDScreeningNurseServiceImpl());
  }

  @Test
  void testUpdateIDRSScreen() throws Exception {
    // Arrange, Act and Assert
    assertNull(nCDScreeningServiceImpl.UpdateIDRSScreen(new JsonObject()));
    assertNull(nCDScreeningServiceImpl.UpdateIDRSScreen(null));
  }

  @Test
  void testGetBenCaseRecordFromDoctorNCDScreening() {
    // Arrange
    when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenThrow(new RuntimeException("findings"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> nCDScreeningServiceImpl.getBenCaseRecordFromDoctorNCDScreening(1L, 1L));
    verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
  }

  @Test
  void testGetBenCaseRecordFromDoctorNCDScreening2() {
    // Arrange
    when(nCDSCreeningDoctorServiceImpl.getNCDDiagnosisData(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(null);
    when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Investigation Details");
    when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Prescribed Drugs");
    when(commonDoctorServiceImpl.getReferralDetails(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any()))
        .thenReturn("Referral Details");
    when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Findings Details");
    when(commonNurseServiceImpl.getGraphicalTrendData(Mockito.<Long>any(), Mockito.<String>any()))
        .thenReturn(new HashMap<>());
    when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Last 3 Archived Test Visit List");
    when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualBenCaseRecordFromDoctorNCDScreening = nCDScreeningServiceImpl
        .getBenCaseRecordFromDoctorNCDScreening(1L, 1L);

    // Assert
    verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
    verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
    verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
    verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
    verify(commonNurseServiceImpl).getGraphicalTrendData(isA(Long.class), eq("ncdCare"));
    verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
    verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
    verify(nCDSCreeningDoctorServiceImpl).getNCDDiagnosisData(isA(Long.class), isA(Long.class));
    assertEquals("{Refer=Referral Details, prescription=Prescribed Drugs, findings=Findings Details, LabReport=[],"
        + " diagnosis=null, investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived"
        + " Test Visit List, GraphData={}}", actualBenCaseRecordFromDoctorNCDScreening);
  }

  @Test
  void testGetBenCaseRecordFromDoctorNCDScreening3() {
    // Arrange
    when(nCDSCreeningDoctorServiceImpl.getNCDDiagnosisData(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(null);
    when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Investigation Details");
    when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Prescribed Drugs");
    when(commonDoctorServiceImpl.getReferralDetails(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any()))
        .thenReturn("Referral Details");
    when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Findings Details");
    when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenThrow(new RuntimeException("findings"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> nCDScreeningServiceImpl.getBenCaseRecordFromDoctorNCDScreening(1L, 1L));
    verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
    verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
    verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
    verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
    verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
    verify(nCDSCreeningDoctorServiceImpl).getNCDDiagnosisData(isA(Long.class), isA(Long.class));
  }

  @Test
  void testGetBenCaseRecordFromDoctorNCDScreening4() {
    // Arrange
    when(nCDSCreeningDoctorServiceImpl.getNCDDiagnosisData(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn("");
    when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Investigation Details");
    when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Prescribed Drugs");
    when(commonDoctorServiceImpl.getReferralDetails(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any()))
        .thenReturn("Referral Details");
    when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Findings Details");
    when(commonNurseServiceImpl.getGraphicalTrendData(Mockito.<Long>any(), Mockito.<String>any()))
        .thenReturn(new HashMap<>());
    when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Last 3 Archived Test Visit List");
    when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualBenCaseRecordFromDoctorNCDScreening = nCDScreeningServiceImpl
        .getBenCaseRecordFromDoctorNCDScreening(1L, 1L);

    // Assert
    verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
    verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
    verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
    verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
    verify(commonNurseServiceImpl).getGraphicalTrendData(isA(Long.class), eq("ncdCare"));
    verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
    verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
    verify(nCDSCreeningDoctorServiceImpl).getNCDDiagnosisData(isA(Long.class), isA(Long.class));
    assertEquals("{Refer=Referral Details, prescription=Prescribed Drugs, findings=Findings Details, LabReport=[],"
        + " diagnosis=, investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived Test"
        + " Visit List, GraphData={}}", actualBenCaseRecordFromDoctorNCDScreening);
  }

  @Test
  void testGetBenCaseRecordFromDoctorNCDScreening5() {
    // Arrange
    when(nCDSCreeningDoctorServiceImpl.getNCDDiagnosisData(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(null);
    when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Investigation Details");
    when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Prescribed Drugs");
    when(commonDoctorServiceImpl.getReferralDetails(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any()))
        .thenReturn("Referral Details");
    when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Findings Details");

    HashMap<String, Object> stringObjectMap = new HashMap<>();
    stringObjectMap.put("findings", "42");
    when(commonNurseServiceImpl.getGraphicalTrendData(Mockito.<Long>any(), Mockito.<String>any()))
        .thenReturn(stringObjectMap);
    when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Last 3 Archived Test Visit List");
    when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualBenCaseRecordFromDoctorNCDScreening = nCDScreeningServiceImpl
        .getBenCaseRecordFromDoctorNCDScreening(1L, 1L);

    // Assert
    verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
    verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
    verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
    verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
    verify(commonNurseServiceImpl).getGraphicalTrendData(isA(Long.class), eq("ncdCare"));
    verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
    verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
    verify(nCDSCreeningDoctorServiceImpl).getNCDDiagnosisData(isA(Long.class), isA(Long.class));
    assertEquals("{Refer=Referral Details, prescription=Prescribed Drugs, findings=Findings Details, LabReport=[],"
        + " diagnosis=null, investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived"
        + " Test Visit List, GraphData={\"findings\":\"42\"}}", actualBenCaseRecordFromDoctorNCDScreening);
  }

  @Test
  void testGetBenCaseRecordFromDoctorNCDScreening6() {
    // Arrange
    when(nCDSCreeningDoctorServiceImpl.getNCDDiagnosisData(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(null);
    when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Investigation Details");
    when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Prescribed Drugs");
    when(commonDoctorServiceImpl.getReferralDetails(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any()))
        .thenReturn("Referral Details");
    when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Findings Details");

    HashMap<String, Object> stringObjectMap = new HashMap<>();
    stringObjectMap.put("diagnosis", "42");
    stringObjectMap.put("findings", "42");
    when(commonNurseServiceImpl.getGraphicalTrendData(Mockito.<Long>any(), Mockito.<String>any()))
        .thenReturn(stringObjectMap);
    when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Last 3 Archived Test Visit List");
    when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualBenCaseRecordFromDoctorNCDScreening = nCDScreeningServiceImpl
        .getBenCaseRecordFromDoctorNCDScreening(1L, 1L);

    // Assert
    verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
    verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
    verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
    verify(commonDoctorServiceImpl).getReferralDetails(isA(Long.class), isA(Long.class), isA(Boolean.class));
    verify(commonNurseServiceImpl).getGraphicalTrendData(isA(Long.class), eq("ncdCare"));
    verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
    verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
    verify(nCDSCreeningDoctorServiceImpl).getNCDDiagnosisData(isA(Long.class), isA(Long.class));
    assertEquals(
        "{Refer=Referral Details, prescription=Prescribed Drugs, findings=Findings Details, LabReport=[],"
            + " diagnosis=null, investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived"
            + " Test Visit List, GraphData={\"findings\":\"42\",\"diagnosis\":\"42\"}}",
        actualBenCaseRecordFromDoctorNCDScreening);
  }

  @Test
  void testGetBenVisitDetailsFrmNurseNCDScreening() {
    // Arrange
    Timestamp createdDate = mock(Timestamp.class);
    when(createdDate.getTime()).thenReturn(10L);
    Timestamp lastModDate = mock(Timestamp.class);
    when(lastModDate.getTime()).thenReturn(10L);

    States states = new States();
    states.setCountryID(1);
    states.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    states.setCreatedDate(mock(Timestamp.class));
    states.setDeleted(true);
    states.setLastModDate(mock(Timestamp.class));
    states.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    states.setStateCode("MD");
    states.setStateID(1);
    states.setStateIName("MD");
    states.setStateName("MD");

    ZoneDistrictMapping zoneDistrictMapping = new ZoneDistrictMapping();
    zoneDistrictMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    zoneDistrictMapping.setCreatedDate(mock(Timestamp.class));
    zoneDistrictMapping.setDeleted(true);
    zoneDistrictMapping.setDistrictID(1);
    zoneDistrictMapping.setDistrictsSet(new HashSet<>());
    zoneDistrictMapping.setLastModDate(mock(Timestamp.class));
    zoneDistrictMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    zoneDistrictMapping.setProcessed("Processed");
    zoneDistrictMapping.setProviderServiceMapID(1);
    zoneDistrictMapping.setZoneDistrictMapID(1);
    zoneDistrictMapping.setZoneID(1);

    Districts m_district = new Districts();
    m_district.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    m_district.setCreatedDate(mock(Timestamp.class));
    m_district.setDeleted(true);
    m_district.setDistrictID(1);
    m_district.setDistrictName("District Name");
    m_district.setGovtLGDDistrictID(1);
    m_district.setGovtLGDStateID(1);
    m_district.setLastModDate(mock(Timestamp.class));
    m_district.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    m_district.setStateID(1);
    m_district.setStates(states);
    m_district.setZoneDistrictMapping(zoneDistrictMapping);

    States state = new States();
    state.setCountryID(1);
    state.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    state.setCreatedDate(mock(Timestamp.class));
    state.setDeleted(true);
    state.setLastModDate(mock(Timestamp.class));
    state.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    state.setStateCode("MD");
    state.setStateID(1);
    state.setStateIName("MD");
    state.setStateName("MD");

    ProviderServiceMapping providerServiceMapping = new ProviderServiceMapping();
    providerServiceMapping.setAddress("42 Main St");
    providerServiceMapping.setCityID(1);
    providerServiceMapping.setCountryID(1);
    providerServiceMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    providerServiceMapping.setCreatedDate(mock(Timestamp.class));
    providerServiceMapping.setDeleted(true);
    providerServiceMapping.setDistrictBlockID(1);
    providerServiceMapping.setDistrictID(1);
    providerServiceMapping.setLastModDate(mock(Timestamp.class));
    providerServiceMapping.setM_district(m_district);
    providerServiceMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    providerServiceMapping.setProviderServiceMapID(1);
    providerServiceMapping.setServiceID((short) 1);
    providerServiceMapping.setServiceProviderID(1);
    providerServiceMapping.setState(state);
    providerServiceMapping.setStateID(1);
    providerServiceMapping.setStatusID(1);
    Timestamp syncedDate = mock(Timestamp.class);
    when(syncedDate.getTime()).thenReturn(10L);
    Timestamp visitDateTime = mock(Timestamp.class);
    when(visitDateTime.getTime()).thenReturn(10L);

    BeneficiaryVisitDetail beneficiaryVisitDetail = new BeneficiaryVisitDetail();
    beneficiaryVisitDetail.setAction("Action");
    beneficiaryVisitDetail.setActionId(1);
    beneficiaryVisitDetail.setActionIdPc(1);
    beneficiaryVisitDetail.setActionPc("Action Pc");
    beneficiaryVisitDetail.setAlgorithm("Algorithm");
    beneficiaryVisitDetail.setAlgorithmPc("Algorithm Pc");
    beneficiaryVisitDetail.setBenVisitID(1L);
    beneficiaryVisitDetail.setBeneficiaryRegID(1L);
    beneficiaryVisitDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    beneficiaryVisitDetail.setCreatedDate(createdDate);
    beneficiaryVisitDetail.setDeleted(true);
    beneficiaryVisitDetail.setDiseaseSummary("Disease Summary");
    beneficiaryVisitDetail.setDiseaseSummaryID(1);
    beneficiaryVisitDetail.setFileIDs(new Integer[]{1});
    beneficiaryVisitDetail.setFollowUpForFpMethod(new String[]{"Follow Up For Fp Method"});
    beneficiaryVisitDetail.setFpMethodFollowup("Fp Method Followup");
    beneficiaryVisitDetail.setFpSideeffects("Fp Sideeffects");
    beneficiaryVisitDetail.setHealthFacilityLocation("Health Facility Location");
    beneficiaryVisitDetail.setHealthFacilityType("Health Facility Type");
    beneficiaryVisitDetail.setInformationGiven("Information Given");
    beneficiaryVisitDetail.setLastModDate(lastModDate);
    beneficiaryVisitDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    beneficiaryVisitDetail.setOtherFollowUpForFpMethod("Other Follow Up For Fp Method");
    beneficiaryVisitDetail.setOtherSideEffects("Other Side Effects");
    beneficiaryVisitDetail.setParkingPlaceID(1);
    beneficiaryVisitDetail.setPregnancyStatus("Pregnancy Status");
    beneficiaryVisitDetail.setPresentChiefComplaint("Present Chief Complaint");
    beneficiaryVisitDetail.setPresentChiefComplaintID("Present Chief Complaint ID");
    beneficiaryVisitDetail.setProcessed("Processed");
    beneficiaryVisitDetail.setProviderServiceMapID(1);
    beneficiaryVisitDetail.setProviderServiceMapping(providerServiceMapping);
    beneficiaryVisitDetail.setRecommendedAction("Recommended Action");
    beneficiaryVisitDetail.setRecommendedActionPc("Recommended Action Pc");
    beneficiaryVisitDetail.setRemarks("Remarks");
    beneficiaryVisitDetail.setRemarksPc("Remarks Pc");
    beneficiaryVisitDetail.setReportFilePath("/directory/foo.txt");
    beneficiaryVisitDetail.setReservedForChange("Reserved For Change");
    beneficiaryVisitDetail.setSelectedDiagnosis("Selected Diagnosis");
    beneficiaryVisitDetail.setSelectedDiagnosisID("Selected Diagnosis ID");
    beneficiaryVisitDetail.setServiceProviderName("Service Provider Name");
    beneficiaryVisitDetail.setSideEffects(new String[]{"Side Effects"});
    beneficiaryVisitDetail.setSubVisitCategory("Sub Visit Category");
    beneficiaryVisitDetail.setSyncedBy("Synced By");
    beneficiaryVisitDetail.setSyncedDate(syncedDate);
    beneficiaryVisitDetail.setVanID(1);
    beneficiaryVisitDetail.setVanSerialNo(1L);
    beneficiaryVisitDetail.setVehicalNo("Vehical No");
    beneficiaryVisitDetail.setVisitCategory("Visit Category");
    beneficiaryVisitDetail.setVisitCategoryID(1);
    beneficiaryVisitDetail.setVisitCode(1L);
    beneficiaryVisitDetail.setVisitDateTime(visitDateTime);
    beneficiaryVisitDetail.setVisitFlowStatusFlag("Visit Flow Status Flag");
    beneficiaryVisitDetail.setVisitNo((short) 1);
    beneficiaryVisitDetail.setVisitReason("Just cause");
    beneficiaryVisitDetail.setVisitReasonID((short) 1);
    beneficiaryVisitDetail.setrCHID("R CHID");
    when(commonNurseServiceImpl.getCSVisitDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(beneficiaryVisitDetail);
    when(commonNurseServiceImpl.getBenCdss(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn("Ben Cdss");
    when(commonNurseServiceImpl.getBenChiefComplaints(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Ben Chief Complaints");

    // Act
    nCDScreeningServiceImpl.getBenVisitDetailsFrmNurseNCDScreening(1L, 1L);

    // Assert
    verify(commonNurseServiceImpl).getBenCdss(isA(Long.class), isA(Long.class));
    verify(commonNurseServiceImpl).getBenChiefComplaints(isA(Long.class), isA(Long.class));
    verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
    verify(createdDate).getTime();
    verify(lastModDate).getTime();
    verify(syncedDate).getTime();
    verify(visitDateTime).getTime();
  }

  @Test
  void testGetBenVisitDetailsFrmNurseNCDScreening2() {
    // Arrange
    Timestamp createdDate = mock(Timestamp.class);
    when(createdDate.getTime()).thenReturn(10L);
    Timestamp lastModDate = mock(Timestamp.class);
    when(lastModDate.getTime()).thenReturn(10L);

    States states = new States();
    states.setCountryID(1);
    states.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    states.setCreatedDate(mock(Timestamp.class));
    states.setDeleted(true);
    states.setLastModDate(mock(Timestamp.class));
    states.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    states.setStateCode("MD");
    states.setStateID(1);
    states.setStateIName("MD");
    states.setStateName("MD");

    ZoneDistrictMapping zoneDistrictMapping = new ZoneDistrictMapping();
    zoneDistrictMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    zoneDistrictMapping.setCreatedDate(mock(Timestamp.class));
    zoneDistrictMapping.setDeleted(true);
    zoneDistrictMapping.setDistrictID(1);
    zoneDistrictMapping.setDistrictsSet(new HashSet<>());
    zoneDistrictMapping.setLastModDate(mock(Timestamp.class));
    zoneDistrictMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    zoneDistrictMapping.setProcessed("Processed");
    zoneDistrictMapping.setProviderServiceMapID(1);
    zoneDistrictMapping.setZoneDistrictMapID(1);
    zoneDistrictMapping.setZoneID(1);

    Districts m_district = new Districts();
    m_district.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    m_district.setCreatedDate(mock(Timestamp.class));
    m_district.setDeleted(true);
    m_district.setDistrictID(1);
    m_district.setDistrictName("District Name");
    m_district.setGovtLGDDistrictID(1);
    m_district.setGovtLGDStateID(1);
    m_district.setLastModDate(mock(Timestamp.class));
    m_district.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    m_district.setStateID(1);
    m_district.setStates(states);
    m_district.setZoneDistrictMapping(zoneDistrictMapping);

    States state = new States();
    state.setCountryID(1);
    state.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    state.setCreatedDate(mock(Timestamp.class));
    state.setDeleted(true);
    state.setLastModDate(mock(Timestamp.class));
    state.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    state.setStateCode("MD");
    state.setStateID(1);
    state.setStateIName("MD");
    state.setStateName("MD");

    ProviderServiceMapping providerServiceMapping = new ProviderServiceMapping();
    providerServiceMapping.setAddress("42 Main St");
    providerServiceMapping.setCityID(1);
    providerServiceMapping.setCountryID(1);
    providerServiceMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    providerServiceMapping.setCreatedDate(mock(Timestamp.class));
    providerServiceMapping.setDeleted(true);
    providerServiceMapping.setDistrictBlockID(1);
    providerServiceMapping.setDistrictID(1);
    providerServiceMapping.setLastModDate(mock(Timestamp.class));
    providerServiceMapping.setM_district(m_district);
    providerServiceMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    providerServiceMapping.setProviderServiceMapID(1);
    providerServiceMapping.setServiceID((short) 1);
    providerServiceMapping.setServiceProviderID(1);
    providerServiceMapping.setState(state);
    providerServiceMapping.setStateID(1);
    providerServiceMapping.setStatusID(1);
    Timestamp syncedDate = mock(Timestamp.class);
    when(syncedDate.getTime()).thenReturn(10L);
    Timestamp visitDateTime = mock(Timestamp.class);
    when(visitDateTime.getTime()).thenThrow(new RuntimeException("NCDScreeningNurseVisitDetail"));

    BeneficiaryVisitDetail beneficiaryVisitDetail = new BeneficiaryVisitDetail();
    beneficiaryVisitDetail.setAction("Action");
    beneficiaryVisitDetail.setActionId(1);
    beneficiaryVisitDetail.setActionIdPc(1);
    beneficiaryVisitDetail.setActionPc("Action Pc");
    beneficiaryVisitDetail.setAlgorithm("Algorithm");
    beneficiaryVisitDetail.setAlgorithmPc("Algorithm Pc");
    beneficiaryVisitDetail.setBenVisitID(1L);
    beneficiaryVisitDetail.setBeneficiaryRegID(1L);
    beneficiaryVisitDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    beneficiaryVisitDetail.setCreatedDate(createdDate);
    beneficiaryVisitDetail.setDeleted(true);
    beneficiaryVisitDetail.setDiseaseSummary("Disease Summary");
    beneficiaryVisitDetail.setDiseaseSummaryID(1);
    beneficiaryVisitDetail.setFileIDs(new Integer[]{1});
    beneficiaryVisitDetail.setFollowUpForFpMethod(new String[]{"Follow Up For Fp Method"});
    beneficiaryVisitDetail.setFpMethodFollowup("Fp Method Followup");
    beneficiaryVisitDetail.setFpSideeffects("Fp Sideeffects");
    beneficiaryVisitDetail.setHealthFacilityLocation("Health Facility Location");
    beneficiaryVisitDetail.setHealthFacilityType("Health Facility Type");
    beneficiaryVisitDetail.setInformationGiven("Information Given");
    beneficiaryVisitDetail.setLastModDate(lastModDate);
    beneficiaryVisitDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    beneficiaryVisitDetail.setOtherFollowUpForFpMethod("Other Follow Up For Fp Method");
    beneficiaryVisitDetail.setOtherSideEffects("Other Side Effects");
    beneficiaryVisitDetail.setParkingPlaceID(1);
    beneficiaryVisitDetail.setPregnancyStatus("Pregnancy Status");
    beneficiaryVisitDetail.setPresentChiefComplaint("Present Chief Complaint");
    beneficiaryVisitDetail.setPresentChiefComplaintID("Present Chief Complaint ID");
    beneficiaryVisitDetail.setProcessed("Processed");
    beneficiaryVisitDetail.setProviderServiceMapID(1);
    beneficiaryVisitDetail.setProviderServiceMapping(providerServiceMapping);
    beneficiaryVisitDetail.setRecommendedAction("Recommended Action");
    beneficiaryVisitDetail.setRecommendedActionPc("Recommended Action Pc");
    beneficiaryVisitDetail.setRemarks("Remarks");
    beneficiaryVisitDetail.setRemarksPc("Remarks Pc");
    beneficiaryVisitDetail.setReportFilePath("/directory/foo.txt");
    beneficiaryVisitDetail.setReservedForChange("Reserved For Change");
    beneficiaryVisitDetail.setSelectedDiagnosis("Selected Diagnosis");
    beneficiaryVisitDetail.setSelectedDiagnosisID("Selected Diagnosis ID");
    beneficiaryVisitDetail.setServiceProviderName("Service Provider Name");
    beneficiaryVisitDetail.setSideEffects(new String[]{"Side Effects"});
    beneficiaryVisitDetail.setSubVisitCategory("Sub Visit Category");
    beneficiaryVisitDetail.setSyncedBy("Synced By");
    beneficiaryVisitDetail.setSyncedDate(syncedDate);
    beneficiaryVisitDetail.setVanID(1);
    beneficiaryVisitDetail.setVanSerialNo(1L);
    beneficiaryVisitDetail.setVehicalNo("Vehical No");
    beneficiaryVisitDetail.setVisitCategory("Visit Category");
    beneficiaryVisitDetail.setVisitCategoryID(1);
    beneficiaryVisitDetail.setVisitCode(1L);
    beneficiaryVisitDetail.setVisitDateTime(visitDateTime);
    beneficiaryVisitDetail.setVisitFlowStatusFlag("Visit Flow Status Flag");
    beneficiaryVisitDetail.setVisitNo((short) 1);
    beneficiaryVisitDetail.setVisitReason("Just cause");
    beneficiaryVisitDetail.setVisitReasonID((short) 1);
    beneficiaryVisitDetail.setrCHID("R CHID");
    when(commonNurseServiceImpl.getCSVisitDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(beneficiaryVisitDetail);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> nCDScreeningServiceImpl.getBenVisitDetailsFrmNurseNCDScreening(1L, 1L));
    verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
    verify(visitDateTime).getTime();
  }

  @Test
  void testGetBenVisitDetailsFrmNurseNCDScreening3() {
    // Arrange
    States states = new States();
    states.setCountryID(1);
    states.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    states.setCreatedDate(mock(Timestamp.class));
    states.setDeleted(true);
    states.setLastModDate(mock(Timestamp.class));
    states.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    states.setStateCode("MD");
    states.setStateID(1);
    states.setStateIName("MD");
    states.setStateName("MD");

    ZoneDistrictMapping zoneDistrictMapping = new ZoneDistrictMapping();
    zoneDistrictMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    zoneDistrictMapping.setCreatedDate(mock(Timestamp.class));
    zoneDistrictMapping.setDeleted(true);
    zoneDistrictMapping.setDistrictID(1);
    zoneDistrictMapping.setDistrictsSet(new HashSet<>());
    zoneDistrictMapping.setLastModDate(mock(Timestamp.class));
    zoneDistrictMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    zoneDistrictMapping.setProcessed("Processed");
    zoneDistrictMapping.setProviderServiceMapID(1);
    zoneDistrictMapping.setZoneDistrictMapID(1);
    zoneDistrictMapping.setZoneID(1);

    Districts m_district = new Districts();
    m_district.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    m_district.setCreatedDate(mock(Timestamp.class));
    m_district.setDeleted(true);
    m_district.setDistrictID(1);
    m_district.setDistrictName("District Name");
    m_district.setGovtLGDDistrictID(1);
    m_district.setGovtLGDStateID(1);
    m_district.setLastModDate(mock(Timestamp.class));
    m_district.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    m_district.setStateID(1);
    m_district.setStates(states);
    m_district.setZoneDistrictMapping(zoneDistrictMapping);

    States state = new States();
    state.setCountryID(1);
    state.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    state.setCreatedDate(mock(Timestamp.class));
    state.setDeleted(true);
    state.setLastModDate(mock(Timestamp.class));
    state.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    state.setStateCode("MD");
    state.setStateID(1);
    state.setStateIName("MD");
    state.setStateName("MD");

    ProviderServiceMapping providerServiceMapping = new ProviderServiceMapping();
    providerServiceMapping.setAddress("42 Main St");
    providerServiceMapping.setCityID(1);
    providerServiceMapping.setCountryID(1);
    providerServiceMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    providerServiceMapping.setCreatedDate(mock(Timestamp.class));
    providerServiceMapping.setDeleted(true);
    providerServiceMapping.setDistrictBlockID(1);
    providerServiceMapping.setDistrictID(1);
    providerServiceMapping.setLastModDate(mock(Timestamp.class));
    providerServiceMapping.setM_district(m_district);
    providerServiceMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    providerServiceMapping.setProviderServiceMapID(1);
    providerServiceMapping.setServiceID((short) 1);
    providerServiceMapping.setServiceProviderID(1);
    providerServiceMapping.setState(state);
    providerServiceMapping.setStateID(1);
    providerServiceMapping.setStatusID(1);
    BeneficiaryVisitDetail beneficiaryVisitDetail = mock(BeneficiaryVisitDetail.class);
    doNothing().when(beneficiaryVisitDetail).setAction(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setActionId(Mockito.<Integer>any());
    doNothing().when(beneficiaryVisitDetail).setActionIdPc(Mockito.<Integer>any());
    doNothing().when(beneficiaryVisitDetail).setActionPc(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setAlgorithm(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setAlgorithmPc(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(beneficiaryVisitDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(beneficiaryVisitDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(beneficiaryVisitDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(beneficiaryVisitDetail).setDiseaseSummary(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setDiseaseSummaryID(Mockito.<Integer>any());
    doNothing().when(beneficiaryVisitDetail).setFileIDs(Mockito.<Integer[]>any());
    doNothing().when(beneficiaryVisitDetail).setFollowUpForFpMethod(Mockito.<String[]>any());
    doNothing().when(beneficiaryVisitDetail).setFpMethodFollowup(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setFpSideeffects(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setHealthFacilityLocation(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setHealthFacilityType(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setInformationGiven(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(beneficiaryVisitDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setOtherFollowUpForFpMethod(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setOtherSideEffects(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(beneficiaryVisitDetail).setPregnancyStatus(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setPresentChiefComplaint(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setPresentChiefComplaintID(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setProcessed(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(beneficiaryVisitDetail).setProviderServiceMapping(Mockito.<ProviderServiceMapping>any());
    doNothing().when(beneficiaryVisitDetail).setRecommendedAction(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setRecommendedActionPc(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setRemarks(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setRemarksPc(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setReportFilePath(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setSelectedDiagnosis(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setSelectedDiagnosisID(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setServiceProviderName(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setSideEffects(Mockito.<String[]>any());
    doNothing().when(beneficiaryVisitDetail).setSubVisitCategory(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(beneficiaryVisitDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(beneficiaryVisitDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(beneficiaryVisitDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setVisitCategory(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setVisitCategoryID(Mockito.<Integer>any());
    doNothing().when(beneficiaryVisitDetail).setVisitCode(Mockito.<Long>any());
    doNothing().when(beneficiaryVisitDetail).setVisitDateTime(Mockito.<Timestamp>any());
    doNothing().when(beneficiaryVisitDetail).setVisitFlowStatusFlag(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setVisitNo(Mockito.<Short>any());
    doNothing().when(beneficiaryVisitDetail).setVisitReason(Mockito.<String>any());
    doNothing().when(beneficiaryVisitDetail).setVisitReasonID(Mockito.<Short>any());
    doNothing().when(beneficiaryVisitDetail).setrCHID(Mockito.<String>any());
    beneficiaryVisitDetail.setAction("Action");
    beneficiaryVisitDetail.setActionId(1);
    beneficiaryVisitDetail.setActionIdPc(1);
    beneficiaryVisitDetail.setActionPc("Action Pc");
    beneficiaryVisitDetail.setAlgorithm("Algorithm");
    beneficiaryVisitDetail.setAlgorithmPc("Algorithm Pc");
    beneficiaryVisitDetail.setBenVisitID(1L);
    beneficiaryVisitDetail.setBeneficiaryRegID(1L);
    beneficiaryVisitDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    beneficiaryVisitDetail.setCreatedDate(mock(Timestamp.class));
    beneficiaryVisitDetail.setDeleted(true);
    beneficiaryVisitDetail.setDiseaseSummary("Disease Summary");
    beneficiaryVisitDetail.setDiseaseSummaryID(1);
    beneficiaryVisitDetail.setFileIDs(new Integer[]{1});
    beneficiaryVisitDetail.setFollowUpForFpMethod(new String[]{"Follow Up For Fp Method"});
    beneficiaryVisitDetail.setFpMethodFollowup("Fp Method Followup");
    beneficiaryVisitDetail.setFpSideeffects("Fp Sideeffects");
    beneficiaryVisitDetail.setHealthFacilityLocation("Health Facility Location");
    beneficiaryVisitDetail.setHealthFacilityType("Health Facility Type");
    beneficiaryVisitDetail.setInformationGiven("Information Given");
    beneficiaryVisitDetail.setLastModDate(mock(Timestamp.class));
    beneficiaryVisitDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    beneficiaryVisitDetail.setOtherFollowUpForFpMethod("Other Follow Up For Fp Method");
    beneficiaryVisitDetail.setOtherSideEffects("Other Side Effects");
    beneficiaryVisitDetail.setParkingPlaceID(1);
    beneficiaryVisitDetail.setPregnancyStatus("Pregnancy Status");
    beneficiaryVisitDetail.setPresentChiefComplaint("Present Chief Complaint");
    beneficiaryVisitDetail.setPresentChiefComplaintID("Present Chief Complaint ID");
    beneficiaryVisitDetail.setProcessed("Processed");
    beneficiaryVisitDetail.setProviderServiceMapID(1);
    beneficiaryVisitDetail.setProviderServiceMapping(providerServiceMapping);
    beneficiaryVisitDetail.setRecommendedAction("Recommended Action");
    beneficiaryVisitDetail.setRecommendedActionPc("Recommended Action Pc");
    beneficiaryVisitDetail.setRemarks("Remarks");
    beneficiaryVisitDetail.setRemarksPc("Remarks Pc");
    beneficiaryVisitDetail.setReportFilePath("/directory/foo.txt");
    beneficiaryVisitDetail.setReservedForChange("Reserved For Change");
    beneficiaryVisitDetail.setSelectedDiagnosis("Selected Diagnosis");
    beneficiaryVisitDetail.setSelectedDiagnosisID("Selected Diagnosis ID");
    beneficiaryVisitDetail.setServiceProviderName("Service Provider Name");
    beneficiaryVisitDetail.setSideEffects(new String[]{"Side Effects"});
    beneficiaryVisitDetail.setSubVisitCategory("Sub Visit Category");
    beneficiaryVisitDetail.setSyncedBy("Synced By");
    beneficiaryVisitDetail.setSyncedDate(mock(Timestamp.class));
    beneficiaryVisitDetail.setVanID(1);
    beneficiaryVisitDetail.setVanSerialNo(1L);
    beneficiaryVisitDetail.setVehicalNo("Vehical No");
    beneficiaryVisitDetail.setVisitCategory("Visit Category");
    beneficiaryVisitDetail.setVisitCategoryID(1);
    beneficiaryVisitDetail.setVisitCode(1L);
    beneficiaryVisitDetail.setVisitDateTime(mock(Timestamp.class));
    beneficiaryVisitDetail.setVisitFlowStatusFlag("Visit Flow Status Flag");
    beneficiaryVisitDetail.setVisitNo((short) 1);
    beneficiaryVisitDetail.setVisitReason("Just cause");
    beneficiaryVisitDetail.setVisitReasonID((short) 1);
    beneficiaryVisitDetail.setrCHID("R CHID");
    when(commonNurseServiceImpl.getCSVisitDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(beneficiaryVisitDetail);
    when(commonNurseServiceImpl.getBenCdss(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn("Ben Cdss");
    when(commonNurseServiceImpl.getBenChiefComplaints(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Ben Chief Complaints");

    // Act
    String actualBenVisitDetailsFrmNurseNCDScreening = nCDScreeningServiceImpl
        .getBenVisitDetailsFrmNurseNCDScreening(1L, 1L);

    // Assert
    verify(beneficiaryVisitDetail).setAction(eq("Action"));
    verify(beneficiaryVisitDetail).setActionId(isA(Integer.class));
    verify(beneficiaryVisitDetail).setActionIdPc(isA(Integer.class));
    verify(beneficiaryVisitDetail).setActionPc(eq("Action Pc"));
    verify(beneficiaryVisitDetail).setAlgorithm(eq("Algorithm"));
    verify(beneficiaryVisitDetail).setAlgorithmPc(eq("Algorithm Pc"));
    verify(beneficiaryVisitDetail).setBenVisitID(isA(Long.class));
    verify(beneficiaryVisitDetail).setBeneficiaryRegID(isA(Long.class));
    verify(beneficiaryVisitDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(beneficiaryVisitDetail).setCreatedDate(isA(Timestamp.class));
    verify(beneficiaryVisitDetail).setDeleted(isA(Boolean.class));
    verify(beneficiaryVisitDetail).setDiseaseSummary(eq("Disease Summary"));
    verify(beneficiaryVisitDetail).setDiseaseSummaryID(isA(Integer.class));
    verify(beneficiaryVisitDetail).setFileIDs(isA(Integer[].class));
    verify(beneficiaryVisitDetail).setFollowUpForFpMethod(isA(String[].class));
    verify(beneficiaryVisitDetail).setFpMethodFollowup(eq("Fp Method Followup"));
    verify(beneficiaryVisitDetail).setFpSideeffects(eq("Fp Sideeffects"));
    verify(beneficiaryVisitDetail).setHealthFacilityLocation(eq("Health Facility Location"));
    verify(beneficiaryVisitDetail).setHealthFacilityType(eq("Health Facility Type"));
    verify(beneficiaryVisitDetail).setInformationGiven(eq("Information Given"));
    verify(beneficiaryVisitDetail).setLastModDate(isA(Timestamp.class));
    verify(beneficiaryVisitDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(beneficiaryVisitDetail).setOtherFollowUpForFpMethod(eq("Other Follow Up For Fp Method"));
    verify(beneficiaryVisitDetail).setOtherSideEffects(eq("Other Side Effects"));
    verify(beneficiaryVisitDetail).setParkingPlaceID(isA(Integer.class));
    verify(beneficiaryVisitDetail).setPregnancyStatus(eq("Pregnancy Status"));
    verify(beneficiaryVisitDetail).setPresentChiefComplaint(eq("Present Chief Complaint"));
    verify(beneficiaryVisitDetail).setPresentChiefComplaintID(eq("Present Chief Complaint ID"));
    verify(beneficiaryVisitDetail).setProcessed(eq("Processed"));
    verify(beneficiaryVisitDetail).setProviderServiceMapID(isA(Integer.class));
    verify(beneficiaryVisitDetail).setProviderServiceMapping(isA(ProviderServiceMapping.class));
    verify(beneficiaryVisitDetail).setRecommendedAction(eq("Recommended Action"));
    verify(beneficiaryVisitDetail).setRecommendedActionPc(eq("Recommended Action Pc"));
    verify(beneficiaryVisitDetail).setRemarks(eq("Remarks"));
    verify(beneficiaryVisitDetail).setRemarksPc(eq("Remarks Pc"));
    verify(beneficiaryVisitDetail).setReportFilePath(eq("/directory/foo.txt"));
    verify(beneficiaryVisitDetail).setReservedForChange(eq("Reserved For Change"));
    verify(beneficiaryVisitDetail).setSelectedDiagnosis(eq("Selected Diagnosis"));
    verify(beneficiaryVisitDetail).setSelectedDiagnosisID(eq("Selected Diagnosis ID"));
    verify(beneficiaryVisitDetail).setServiceProviderName(eq("Service Provider Name"));
    verify(beneficiaryVisitDetail).setSideEffects(isA(String[].class));
    verify(beneficiaryVisitDetail).setSubVisitCategory(eq("Sub Visit Category"));
    verify(beneficiaryVisitDetail).setSyncedBy(eq("Synced By"));
    verify(beneficiaryVisitDetail).setSyncedDate(isA(Timestamp.class));
    verify(beneficiaryVisitDetail).setVanID(isA(Integer.class));
    verify(beneficiaryVisitDetail).setVanSerialNo(isA(Long.class));
    verify(beneficiaryVisitDetail).setVehicalNo(eq("Vehical No"));
    verify(beneficiaryVisitDetail).setVisitCategory(eq("Visit Category"));
    verify(beneficiaryVisitDetail).setVisitCategoryID(isA(Integer.class));
    verify(beneficiaryVisitDetail).setVisitCode(isA(Long.class));
    verify(beneficiaryVisitDetail).setVisitDateTime(isA(Timestamp.class));
    verify(beneficiaryVisitDetail).setVisitFlowStatusFlag(eq("Visit Flow Status Flag"));
    verify(beneficiaryVisitDetail).setVisitNo(isA(Short.class));
    verify(beneficiaryVisitDetail).setVisitReason(eq("Just cause"));
    verify(beneficiaryVisitDetail).setVisitReasonID(isA(Short.class));
    verify(beneficiaryVisitDetail).setrCHID(eq("R CHID"));
    verify(commonNurseServiceImpl).getBenCdss(isA(Long.class), isA(Long.class));
    verify(commonNurseServiceImpl).getBenChiefComplaints(isA(Long.class), isA(Long.class));
    verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
    assertEquals("{BenChiefComplaints=Ben Chief Complaints, NCDScreeningNurseVisitDetail={}, Cdss=Ben Cdss}",
        actualBenVisitDetailsFrmNurseNCDScreening);
  }

  @Test
  void testGetBenHistoryDetails() {
    BenFamilyHistory benFamilyHistory = new BenFamilyHistory();
    benFamilyHistory.setBenVisitID(1L);
    benFamilyHistory.setBeneficiaryRegID(1L);
    benFamilyHistory.setCaptureDate(mock(Date.class));
    benFamilyHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benFamilyHistory.setCreatedDate(mock(Timestamp.class));
    benFamilyHistory.setDeleted(true);
    benFamilyHistory.setDiseaseType("Disease Type");
    benFamilyHistory.setDiseaseTypeID((short) 1);
    benFamilyHistory.setFamilyDiseaseList(new ArrayList<>());
    benFamilyHistory.setFamilyMember("Family Member");
    benFamilyHistory.setGeneticDisorder("Genetic Disorder");
    benFamilyHistory.setID(1L);
    benFamilyHistory.setIsConsanguineousMarrige("Is Consanguineous Marrige");
    benFamilyHistory.setIsGeneticDisorder("Is Genetic Disorder");
    benFamilyHistory.setLastModDate(mock(Timestamp.class));
    benFamilyHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benFamilyHistory.setOtherDiseaseType("Other Disease Type");
    benFamilyHistory.setParkingPlaceID(1);
    benFamilyHistory.setProcessed("Processed");
    benFamilyHistory.setProviderServiceMapID(1);
    benFamilyHistory.setReservedForChange("Reserved For Change");
    benFamilyHistory.setSnomedCode("Snomed Code");
    benFamilyHistory.setSnomedTerm("Snomed Term");
    benFamilyHistory.setSyncedBy("Synced By");
    benFamilyHistory.setSyncedDate(mock(Timestamp.class));
    benFamilyHistory.setVanID(1);
    benFamilyHistory.setVanSerialNo(1L);
    benFamilyHistory.setVehicalNo("Vehical No");
    benFamilyHistory.setVisitCode(1L);

    BenPersonalHabit benPersonalHabit = new BenPersonalHabit();
    benPersonalHabit.setAlcoholDuration(mock(Timestamp.class));
    benPersonalHabit.setAlcoholIntakeFrequency("Alcohol Intake Frequency");
    benPersonalHabit.setAlcoholIntakeStatus("Alcohol Intake Status");
    benPersonalHabit.setAlcoholList(new ArrayList<>());
    benPersonalHabit.setAlcoholType("Alcohol Type");
    benPersonalHabit.setAlcoholTypeID("Alcohol Type ID");
    benPersonalHabit.setAlcohol_use_duration(mock(Date.class));
    benPersonalHabit.setAllergicList(new ArrayList<>());
    benPersonalHabit.setAllergyStatus("Allergy Status");
    benPersonalHabit.setAvgAlcoholConsumption("Avg Alcohol Consumption");
    benPersonalHabit.setBenPersonalHabitID(1);
    benPersonalHabit.setBenVisitID(1L);
    benPersonalHabit.setBeneficiaryRegID(1L);
    benPersonalHabit.setCaptureDate(mock(Date.class));
    benPersonalHabit.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benPersonalHabit.setCreatedDate(mock(Timestamp.class));
    benPersonalHabit.setDeleted(true);
    benPersonalHabit.setDietaryType("Dietary Type");
    benPersonalHabit.setLastModDate(mock(Timestamp.class));
    benPersonalHabit.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benPersonalHabit.setNumberperDay((short) 1);
    benPersonalHabit.setNumberperWeek((short) 1);
    benPersonalHabit.setOtherAlcoholType("Other Alcohol Type");
    benPersonalHabit.setOtherTobaccoUseType("Other Tobacco Use Type");
    benPersonalHabit.setParkingPlaceID(1);
    benPersonalHabit.setPhysicalActivityType("Physical Activity Type");
    benPersonalHabit.setProcessed("Processed");
    benPersonalHabit.setProviderServiceMapID(1);
    benPersonalHabit.setReservedForChange("Reserved For Change");
    benPersonalHabit.setRiskySexualPracticeStatus("Risky Sexual Practice Status");
    benPersonalHabit.setRiskySexualPracticesStatus('A');
    benPersonalHabit.setSyncedBy("Synced By");
    benPersonalHabit.setSyncedDate(mock(Timestamp.class));
    benPersonalHabit.setTobaccoList(new ArrayList<>());
    benPersonalHabit.setTobaccoUseDuration(mock(Timestamp.class));
    benPersonalHabit.setTobaccoUseStatus("Tobacco Use Status");
    benPersonalHabit.setTobaccoUseType("Tobacco Use Type");
    benPersonalHabit.setTobaccoUseTypeID("Tobacco Use Type ID");
    benPersonalHabit.setTobacco_use_duration(mock(Date.class));
    benPersonalHabit.setVanID(1);
    benPersonalHabit.setVanSerialNo(1L);
    benPersonalHabit.setVehicalNo("Vehical No");
    benPersonalHabit.setVisitCode(1L);

    PhysicalActivityType physicalActivityType = new PhysicalActivityType();
    physicalActivityType.setActivityType("Activity Type");
    physicalActivityType.setBenVisitID(1L);
    physicalActivityType.setBeneficiaryRegID(1L);
    physicalActivityType.setCaptureDate(mock(Date.class));
    physicalActivityType.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    physicalActivityType.setCreatedDate(mock(Timestamp.class));
    physicalActivityType.setDeleted(true);
    physicalActivityType.setID(1L);
    physicalActivityType.setLastModDate(mock(Timestamp.class));
    physicalActivityType.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    physicalActivityType.setParkingPlaceID(1);
    physicalActivityType.setPhysicalActivityType("Physical Activity Type");
    physicalActivityType.setProcessed("Processed");
    physicalActivityType.setProviderServiceMapID(1);
    physicalActivityType.setVanID(1);
    physicalActivityType.setVanSerialNo(1L);
    physicalActivityType.setVehicalNo("Vehical No");
    physicalActivityType.setVisitCode(1L);
    physicalActivityType.setpAID(1L);
    when(commonNurseServiceImpl.getFamilyHistoryDetail(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(benFamilyHistory);
    when(commonNurseServiceImpl.getPersonalHistory(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(benPersonalHabit);
    when(commonNurseServiceImpl.getPhysicalActivityType(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(physicalActivityType);

    // Act
    nCDScreeningServiceImpl.getBenHistoryDetails(1L, 1L);
  }

  @Test
  void testGetBenIdrsDetailsFrmNurse() {
    IDRSData idrsData = new IDRSData();
    idrsData.setAnswer("Answer");
    idrsData.setBenVisitID(1L);
    idrsData.setBeneficiaryRegID(1L);
    idrsData.setConfirmArray(new String[]{"Confirm Array"});
    idrsData.setConfirmedDisease("Confirmed Disease");
    idrsData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    idrsData.setCreatedDate(mock(Timestamp.class));
    idrsData.setDeleted(true);
    idrsData.setDiseaseQuestionType("Disease Question Type");
    idrsData.setId(1L);
    idrsData.setIdrsDetails(new ArrayList<>());
    idrsData.setIdrsQuestionID(1);
    idrsData.setIdrsScore(1);
    idrsData.setIsDiabetic(true);
    idrsData.setLastModDate(mock(Timestamp.class));
    idrsData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    idrsData.setParkingPlaceID(1);
    idrsData.setProcessed("Processed");
    idrsData.setProviderServiceMapID(1);
    idrsData.setQuestion("Question");
    idrsData.setQuestionArray(new IDRSData[]{new IDRSData()});
    idrsData.setSuspectArray(new String[]{"Suspect Array"});
    idrsData.setSuspectDetails(new ArrayList<>());
    idrsData.setSuspectedDisease("Suspected Disease");
    idrsData.setVanID(1);
    idrsData.setVanSerialNo(1L);
    idrsData.setVehicalNo("Vehical No");
    idrsData.setVisitCode(1L);

    IDRSData idrsData2 = new IDRSData();
    idrsData2.setAnswer("Answer");
    idrsData2.setBenVisitID(1L);
    idrsData2.setBeneficiaryRegID(1L);
    idrsData2.setConfirmArray(new String[]{"Confirm Array"});
    idrsData2.setConfirmedDisease("Confirmed Disease");
    idrsData2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    idrsData2.setCreatedDate(mock(Timestamp.class));
    idrsData2.setDeleted(true);
    idrsData2.setDiseaseQuestionType("Disease Question Type");
    idrsData2.setId(1L);
    idrsData2.setIdrsDetails(new ArrayList<>());
    idrsData2.setIdrsQuestionID(1);
    idrsData2.setIdrsScore(1);
    idrsData2.setIsDiabetic(true);
    idrsData2.setLastModDate(mock(Timestamp.class));
    idrsData2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    idrsData2.setParkingPlaceID(1);
    idrsData2.setProcessed("Processed");
    idrsData2.setProviderServiceMapID(1);
    idrsData2.setQuestion("Question");
    idrsData2.setQuestionArray(new IDRSData[]{idrsData});
    idrsData2.setSuspectArray(new String[]{"Suspect Array"});
    idrsData2.setSuspectDetails(new ArrayList<>());
    idrsData2.setSuspectedDisease("Suspected Disease");
    idrsData2.setVanID(1);
    idrsData2.setVanSerialNo(1L);
    idrsData2.setVehicalNo("Vehical No");
    idrsData2.setVisitCode(1L);

    IDRSData idrsData3 = new IDRSData();
    idrsData3.setAnswer("Answer");
    idrsData3.setBenVisitID(1L);
    idrsData3.setBeneficiaryRegID(1L);
    idrsData3.setConfirmArray(new String[]{"Confirm Array"});
    idrsData3.setConfirmedDisease("Confirmed Disease");
    idrsData3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    idrsData3.setCreatedDate(mock(Timestamp.class));
    idrsData3.setDeleted(true);
    idrsData3.setDiseaseQuestionType("Disease Question Type");
    idrsData3.setId(1L);
    idrsData3.setIdrsDetails(new ArrayList<>());
    idrsData3.setIdrsQuestionID(1);
    idrsData3.setIdrsScore(1);
    idrsData3.setIsDiabetic(true);
    idrsData3.setLastModDate(mock(Timestamp.class));
    idrsData3.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    idrsData3.setParkingPlaceID(1);
    idrsData3.setProcessed("Processed");
    idrsData3.setProviderServiceMapID(1);
    idrsData3.setQuestion("Question");
    idrsData3.setQuestionArray(new IDRSData[]{idrsData2});
    idrsData3.setSuspectArray(new String[]{"Suspect Array"});
    idrsData3.setSuspectDetails(new ArrayList<>());
    idrsData3.setSuspectedDisease("Suspected Disease");
    idrsData3.setVanID(1);
    idrsData3.setVanSerialNo(1L);
    idrsData3.setVehicalNo("Vehical No");
    idrsData3.setVisitCode(1L);
    when(commonNurseServiceImpl.getBeneficiaryIdrsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(idrsData3);

    // Act
    nCDScreeningServiceImpl.getBenIdrsDetailsFrmNurse(1L, 1L);
  }

  @Test
  void testGetBeneficiaryVitalDetails() {
    // Arrange
    when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Beneficiary Physical Anthropometry Details");
    when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Beneficiary Physical Vital Details");

    // Act
    String actualBeneficiaryVitalDetails = nCDScreeningServiceImpl.getBeneficiaryVitalDetails(1L, 1L);

    // Assert
    verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
    verify(commonNurseServiceImpl).getBeneficiaryPhysicalVitalDetails(isA(Long.class), isA(Long.class));
    assertEquals(
        "{benAnthropometryDetail=Beneficiary Physical Anthropometry Details, benPhysicalVitalDetail=Beneficiary"
            + " Physical Vital Details}",
        actualBeneficiaryVitalDetails);
  }

  @Test
  void testGetBeneficiaryVitalDetails2() {
    // Arrange
    when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenThrow(new RuntimeException("benAnthropometryDetail"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> nCDScreeningServiceImpl.getBeneficiaryVitalDetails(1L, 1L));
    verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
  }

  @Test
  void testGetBeneficiaryCdssDetails() {
    // Arrange
    when(commonNurseServiceImpl.getBenCdssDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Ben Cdss Details");

    // Act
    String actualBeneficiaryCdssDetails = nCDScreeningServiceImpl.getBeneficiaryCdssDetails(1L, 1L);

    // Assert
    verify(commonNurseServiceImpl, atLeast(1)).getBenCdssDetails(isA(Long.class), isA(Long.class));
    assertEquals("{diseaseSummary=Ben Cdss Details, presentChiefComplaint=Ben Cdss Details}",
        actualBeneficiaryCdssDetails);
  }

  @Test
  void testGetBeneficiaryCdssDetails2() {
    // Arrange
    when(commonNurseServiceImpl.getBenCdssDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenThrow(new RuntimeException("presentChiefComplaint"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> nCDScreeningServiceImpl.getBeneficiaryCdssDetails(1L, 1L));
    verify(commonNurseServiceImpl).getBenCdssDetails(isA(Long.class), isA(Long.class));
  }

  @Test
  void testUpdateNCDScreeningHistory() throws Exception {
    // Arrange, Act and Assert
    assertEquals(0, nCDScreeningServiceImpl.UpdateNCDScreeningHistory(new JsonObject()).intValue());
    assertEquals(0, nCDScreeningServiceImpl.UpdateNCDScreeningHistory(null).intValue());
  }

  @Test
  void testUpdateNCDScreeningHistory2() throws Exception {
    // Arrange
    JsonObject historyOBJ = new JsonObject();
    historyOBJ.add("Property", new JsonArray(3));

    // Act and Assert
    assertEquals(0, nCDScreeningServiceImpl.UpdateNCDScreeningHistory(historyOBJ).intValue());
  }

  @Test
  void testUpdateNCDScreeningHistory3() throws Exception {
    // Arrange
    JsonObject historyOBJ = new JsonObject();
    historyOBJ.addProperty("familyHistory", (String) null);

    // Act and Assert
    assertEquals(0, nCDScreeningServiceImpl.UpdateNCDScreeningHistory(historyOBJ).intValue());
  }

  @Test
  void testUpdateNCDScreeningHistory4() throws Exception {
    // Arrange
    when(commonNurseServiceImpl.updateBenFamilyHistoryNCDScreening(Mockito.<BenFamilyHistory>any())).thenReturn(1);

    JsonObject historyOBJ = new JsonObject();
    historyOBJ.add("familyHistory", new JsonObject());

    // Act
    Integer actualUpdateNCDScreeningHistoryResult = nCDScreeningServiceImpl.UpdateNCDScreeningHistory(historyOBJ);

    // Assert
    verify(commonNurseServiceImpl).updateBenFamilyHistoryNCDScreening(isA(BenFamilyHistory.class));
    assertEquals(0, actualUpdateNCDScreeningHistoryResult.intValue());
  }

  @Test
  void testGetBenNCDScreeningNurseData() throws IEMRException {
    BreastCancerScreening breastCancerScreening = new BreastCancerScreening();
    breastCancerScreening.setBeneficiaryRegId(1L);
    breastCancerScreening.setConfirmed(true);
    breastCancerScreening.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    breastCancerScreening.setCreatedDate(mock(Timestamp.class));
    breastCancerScreening.setDeleted(true);
    breastCancerScreening.setId(1L);
    breastCancerScreening.setInspectionBreasts("Inspection Breasts");
    breastCancerScreening.setInspectionBreastsId(1);
    breastCancerScreening.setLastModDate(mock(Timestamp.class));
    breastCancerScreening.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    breastCancerScreening.setPalpationBreasts("Palpation Breasts");
    breastCancerScreening.setPalpationBreastsId(1);
    breastCancerScreening.setPalpationLymphNodes("Palpation Lymph Nodes");
    breastCancerScreening.setPalpationLymphNodesId(1);
    breastCancerScreening.setParkingPlaceID(1);
    breastCancerScreening.setProcessed('A');
    breastCancerScreening.setSuspected(true);
    breastCancerScreening.setVanID(1);
    breastCancerScreening.setVanSerialNo(1L);
    breastCancerScreening.setVehicalNo("Vehical No");
    breastCancerScreening.setVisitcode(1L);
    when(breastCancerScreeningRepo.findByBeneficiaryRegIdAndVisitcode(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(breastCancerScreening);

    CbacDetails cbacDetails = new CbacDetails();
    cbacDetails.setBeneficiaryRegId(1L);
    cbacDetails.setCabcTB("Cabc TB");
    cbacDetails.setCbacAge("Cbac Age");
    cbacDetails.setCbacAgeScore(3);
    cbacDetails.setCbacAlcohol("Cbac Alcohol");
    cbacDetails.setCbacAlcoholScore(3);
    cbacDetails.setCbacAntiTBDrugs("Cbac Anti TBDrugs");
    cbacDetails.setCbacBleedingIntercourse("Cbac Bleeding Intercourse");
    cbacDetails.setCbacBleedingMenopause("Cbac Bleeding Menopause");
    cbacDetails.setCbacBleedingPeriods("Cbac Bleeding Periods");
    cbacDetails.setCbacBloodnippleDischarge("Cbac Bloodnipple Discharge");
    cbacDetails.setCbacBloodsputum("Cbac Bloodsputum");
    cbacDetails.setCbacBlurredVision("Cbac Blurred Vision");
    cbacDetails.setCbacBreastsizechange("Cbac Breastsizechange");
    cbacDetails.setCbacClawingfingers("Cbac Clawingfingers");
    cbacDetails.setCbacConsumeGutka("Cbac Consume Gutka");
    cbacDetails.setCbacConsumeGutkaScore(3);
    cbacDetails.setCbacCough2weeks("Cbac Cough2weeks");
    cbacDetails.setCbacDifficultHoldingObjects("Cbac Difficult Holding Objects");
    cbacDetails.setCbacDifficultyHearing("Cbac Difficulty Hearing");
    cbacDetails.setCbacDifficultyreading("Cbac Difficultyreading");
    cbacDetails.setCbacFamilyHistoryBpdiabetes("Cbac Family History Bpdiabetes");
    cbacDetails.setCbacFamilyHistoryBpdiabetesScore(3);
    cbacDetails.setCbacFeelingUnsteady("Cbac Feeling Unsteady");
    cbacDetails.setCbacFeetweakness("Cbac Feetweakness");
    cbacDetails.setCbacFever2weeks("Cbac Fever2weeks");
    cbacDetails.setCbacFitsHistory("Cbac Fits History");
    cbacDetails.setCbacForgetnearones("Cbac Forgetnearones");
    cbacDetails.setCbacHandTingling("Cbac Hand Tingling");
    cbacDetails.setCbacHypopigmentedpatches("Cbac Hypopigmentedpatches");
    cbacDetails.setCbacInabilityCloseeyelid("Cbac Inability Closeeyelid");
    cbacDetails.setCbacLumpBreast("Cbac Lump Breast");
    cbacDetails.setCbacMouthUlcers("Cbac Mouth Ulcers");
    cbacDetails.setCbacMouthUlcersGrowth("Cbac Mouth Ulcers Growth");
    cbacDetails.setCbacMouthopeningDifficulty("Cbac Mouthopening Difficulty");
    cbacDetails.setCbacMouthredpatch("Cbac Mouthredpatch");
    cbacDetails.setCbacNeedhelpEverydayActivities("Cbac Needhelp Everyday Activities");
    cbacDetails.setCbacNightSweats("Cbac Night Sweats");
    cbacDetails.setCbacNodulesonskin("Cbac Nodulesonskin");
    cbacDetails.setCbacPainchewing("Cbac Painchewing");
    cbacDetails.setCbacPainineyes("Cbac Painineyes");
    cbacDetails.setCbacPhysicalActivity("Cbac Physical Activity");
    cbacDetails.setCbacPhysicalActivityScore(3);
    cbacDetails.setCbacPhysicalDisabilitySuffering("Cbac Physical Disability Suffering");
    cbacDetails.setCbacRecurrentNumbness("Cbac Recurrent Numbness");
    cbacDetails.setCbacRecurrentTingling("Cbac Recurrent Tingling");
    cbacDetails.setCbacRednessPain("Cbac Redness Pain");
    cbacDetails.setCbacShortnessBreath("Cbac Shortness Breath");
    cbacDetails.setCbacTBHistory("Cbac TBHistory");
    cbacDetails.setCbacTb("Cbac Tb");
    cbacDetails.setCbacThickenedskin("Cbac Thickenedskin");
    cbacDetails.setCbacTonechange("Cbac Tonechange");
    cbacDetails.setCbacUlceration("Cbac Ulceration");
    cbacDetails.setCbacVaginalDischarge("Cbac Vaginal Discharge");
    cbacDetails.setCbacWaistFemale("Cbac Waist Female");
    cbacDetails.setCbacWaistFemaleScore(3);
    cbacDetails.setCbacWaistMale("Cbac Waist Male");
    cbacDetails.setCbacWaistMaleScore(3);
    cbacDetails.setCbacWeightLoss("Cbac Weight Loss");
    cbacDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    cbacDetails.setCreatedDate(mock(Timestamp.class));
    cbacDetails.setDeleted(true);
    cbacDetails.setId(1L);
    cbacDetails.setLastModDate(mock(Timestamp.class));
    cbacDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    cbacDetails.setParkingPlaceId(1);
    cbacDetails.setProcessed('A');
    cbacDetails.setProviderServiceMapId(1);
    cbacDetails.setSyncedBy("Synced By");
    cbacDetails.setSyncedDate(mock(Timestamp.class));
    cbacDetails.setTotalScore(3);
    cbacDetails.setVanId(1);
    cbacDetails.setVanSerialNo(1);
    cbacDetails.setVehicalNo("Vehical No");
    cbacDetails.setVisitCode(1L);
    cbacDetails.setVisitcode(1L);
    when(cbacDetailsRepo.findByBeneficiaryRegIdAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(cbacDetails);

    BenFamilyHistory benFamilyHistory = new BenFamilyHistory();
    benFamilyHistory.setBenVisitID(1L);
    benFamilyHistory.setBeneficiaryRegID(1L);
    benFamilyHistory.setCaptureDate(mock(Date.class));
    benFamilyHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benFamilyHistory.setCreatedDate(mock(Timestamp.class));
    benFamilyHistory.setDeleted(true);
    benFamilyHistory.setDiseaseType("Disease Type");
    benFamilyHistory.setDiseaseTypeID((short) 1);
    benFamilyHistory.setFamilyDiseaseList(new ArrayList<>());
    benFamilyHistory.setFamilyMember("Family Member");
    benFamilyHistory.setGeneticDisorder("Genetic Disorder");
    benFamilyHistory.setID(1L);
    benFamilyHistory.setIsConsanguineousMarrige("Is Consanguineous Marrige");
    benFamilyHistory.setIsGeneticDisorder("Is Genetic Disorder");
    benFamilyHistory.setLastModDate(mock(Timestamp.class));
    benFamilyHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benFamilyHistory.setOtherDiseaseType("Other Disease Type");
    benFamilyHistory.setParkingPlaceID(1);
    benFamilyHistory.setProcessed("Processed");
    benFamilyHistory.setProviderServiceMapID(1);
    benFamilyHistory.setReservedForChange("Reserved For Change");
    benFamilyHistory.setSnomedCode("Snomed Code");
    benFamilyHistory.setSnomedTerm("Snomed Term");
    benFamilyHistory.setSyncedBy("Synced By");
    benFamilyHistory.setSyncedDate(mock(Timestamp.class));
    benFamilyHistory.setVanID(1);
    benFamilyHistory.setVanSerialNo(1L);
    benFamilyHistory.setVehicalNo("Vehical No");
    benFamilyHistory.setVisitCode(1L);

    BenPersonalHabit benPersonalHabit = new BenPersonalHabit();
    benPersonalHabit.setAlcoholDuration(mock(Timestamp.class));
    benPersonalHabit.setAlcoholIntakeFrequency("Alcohol Intake Frequency");
    benPersonalHabit.setAlcoholIntakeStatus("Alcohol Intake Status");
    benPersonalHabit.setAlcoholList(new ArrayList<>());
    benPersonalHabit.setAlcoholType("Alcohol Type");
    benPersonalHabit.setAlcoholTypeID("Alcohol Type ID");
    benPersonalHabit.setAlcohol_use_duration(mock(Date.class));
    benPersonalHabit.setAllergicList(new ArrayList<>());
    benPersonalHabit.setAllergyStatus("Allergy Status");
    benPersonalHabit.setAvgAlcoholConsumption("Avg Alcohol Consumption");
    benPersonalHabit.setBenPersonalHabitID(1);
    benPersonalHabit.setBenVisitID(1L);
    benPersonalHabit.setBeneficiaryRegID(1L);
    benPersonalHabit.setCaptureDate(mock(Date.class));
    benPersonalHabit.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benPersonalHabit.setCreatedDate(mock(Timestamp.class));
    benPersonalHabit.setDeleted(true);
    benPersonalHabit.setDietaryType("Dietary Type");
    benPersonalHabit.setLastModDate(mock(Timestamp.class));
    benPersonalHabit.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benPersonalHabit.setNumberperDay((short) 1);
    benPersonalHabit.setNumberperWeek((short) 1);
    benPersonalHabit.setOtherAlcoholType("Other Alcohol Type");
    benPersonalHabit.setOtherTobaccoUseType("Other Tobacco Use Type");
    benPersonalHabit.setParkingPlaceID(1);
    benPersonalHabit.setPhysicalActivityType("Physical Activity Type");
    benPersonalHabit.setProcessed("Processed");
    benPersonalHabit.setProviderServiceMapID(1);
    benPersonalHabit.setReservedForChange("Reserved For Change");
    benPersonalHabit.setRiskySexualPracticeStatus("Risky Sexual Practice Status");
    benPersonalHabit.setRiskySexualPracticesStatus('A');
    benPersonalHabit.setSyncedBy("Synced By");
    benPersonalHabit.setSyncedDate(mock(Timestamp.class));
    benPersonalHabit.setTobaccoList(new ArrayList<>());
    benPersonalHabit.setTobaccoUseDuration(mock(Timestamp.class));
    benPersonalHabit.setTobaccoUseStatus("Tobacco Use Status");
    benPersonalHabit.setTobaccoUseType("Tobacco Use Type");
    benPersonalHabit.setTobaccoUseTypeID("Tobacco Use Type ID");
    benPersonalHabit.setTobacco_use_duration(mock(Date.class));
    benPersonalHabit.setVanID(1);
    benPersonalHabit.setVanSerialNo(1L);
    benPersonalHabit.setVehicalNo("Vehical No");
    benPersonalHabit.setVisitCode(1L);

    IDRSData idrsData = new IDRSData();
    idrsData.setAnswer("Answer");
    idrsData.setBenVisitID(1L);
    idrsData.setBeneficiaryRegID(1L);
    idrsData.setConfirmArray(new String[]{"Confirm Array"});
    idrsData.setConfirmedDisease("Confirmed Disease");
    idrsData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    idrsData.setCreatedDate(mock(Timestamp.class));
    idrsData.setDeleted(true);
    idrsData.setDiseaseQuestionType("Disease Question Type");
    idrsData.setId(1L);
    idrsData.setIdrsDetails(new ArrayList<>());
    idrsData.setIdrsQuestionID(1);
    idrsData.setIdrsScore(1);
    idrsData.setIsDiabetic(true);
    idrsData.setLastModDate(mock(Timestamp.class));
    idrsData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    idrsData.setParkingPlaceID(1);
    idrsData.setProcessed("Processed");
    idrsData.setProviderServiceMapID(1);
    idrsData.setQuestion("Question");
    idrsData.setQuestionArray(new IDRSData[]{new IDRSData()});
    idrsData.setSuspectArray(new String[]{"Suspect Array"});
    idrsData.setSuspectDetails(new ArrayList<>());
    idrsData.setSuspectedDisease("Suspected Disease");
    idrsData.setVanID(1);
    idrsData.setVanSerialNo(1L);
    idrsData.setVehicalNo("Vehical No");
    idrsData.setVisitCode(1L);

    IDRSData idrsData2 = new IDRSData();
    idrsData2.setAnswer("Answer");
    idrsData2.setBenVisitID(1L);
    idrsData2.setBeneficiaryRegID(1L);
    idrsData2.setConfirmArray(new String[]{"Confirm Array"});
    idrsData2.setConfirmedDisease("Confirmed Disease");
    idrsData2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    idrsData2.setCreatedDate(mock(Timestamp.class));
    idrsData2.setDeleted(true);
    idrsData2.setDiseaseQuestionType("Disease Question Type");
    idrsData2.setId(1L);
    idrsData2.setIdrsDetails(new ArrayList<>());
    idrsData2.setIdrsQuestionID(1);
    idrsData2.setIdrsScore(1);
    idrsData2.setIsDiabetic(true);
    idrsData2.setLastModDate(mock(Timestamp.class));
    idrsData2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    idrsData2.setParkingPlaceID(1);
    idrsData2.setProcessed("Processed");
    idrsData2.setProviderServiceMapID(1);
    idrsData2.setQuestion("Question");
    idrsData2.setQuestionArray(new IDRSData[]{idrsData});
    idrsData2.setSuspectArray(new String[]{"Suspect Array"});
    idrsData2.setSuspectDetails(new ArrayList<>());
    idrsData2.setSuspectedDisease("Suspected Disease");
    idrsData2.setVanID(1);
    idrsData2.setVanSerialNo(1L);
    idrsData2.setVehicalNo("Vehical No");
    idrsData2.setVisitCode(1L);

    IDRSData idrsData3 = new IDRSData();
    idrsData3.setAnswer("Answer");
    idrsData3.setBenVisitID(1L);
    idrsData3.setBeneficiaryRegID(1L);
    idrsData3.setConfirmArray(new String[]{"Confirm Array"});
    idrsData3.setConfirmedDisease("Confirmed Disease");
    idrsData3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    idrsData3.setCreatedDate(mock(Timestamp.class));
    idrsData3.setDeleted(true);
    idrsData3.setDiseaseQuestionType("Disease Question Type");
    idrsData3.setId(1L);
    idrsData3.setIdrsDetails(new ArrayList<>());
    idrsData3.setIdrsQuestionID(1);
    idrsData3.setIdrsScore(1);
    idrsData3.setIsDiabetic(true);
    idrsData3.setLastModDate(mock(Timestamp.class));
    idrsData3.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    idrsData3.setParkingPlaceID(1);
    idrsData3.setProcessed("Processed");
    idrsData3.setProviderServiceMapID(1);
    idrsData3.setQuestion("Question");
    idrsData3.setQuestionArray(new IDRSData[]{idrsData2});
    idrsData3.setSuspectArray(new String[]{"Suspect Array"});
    idrsData3.setSuspectDetails(new ArrayList<>());
    idrsData3.setSuspectedDisease("Suspected Disease");
    idrsData3.setVanID(1);
    idrsData3.setVanSerialNo(1L);
    idrsData3.setVehicalNo("Vehical No");
    idrsData3.setVisitCode(1L);

    PhysicalActivityType physicalActivityType = new PhysicalActivityType();
    physicalActivityType.setActivityType("Activity Type");
    physicalActivityType.setBenVisitID(1L);
    physicalActivityType.setBeneficiaryRegID(1L);
    physicalActivityType.setCaptureDate(mock(Date.class));
    physicalActivityType.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    physicalActivityType.setCreatedDate(mock(Timestamp.class));
    physicalActivityType.setDeleted(true);
    physicalActivityType.setID(1L);
    physicalActivityType.setLastModDate(mock(Timestamp.class));
    physicalActivityType.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    physicalActivityType.setParkingPlaceID(1);
    physicalActivityType.setPhysicalActivityType("Physical Activity Type");
    physicalActivityType.setProcessed("Processed");
    physicalActivityType.setProviderServiceMapID(1);
    physicalActivityType.setVanID(1);
    physicalActivityType.setVanSerialNo(1L);
    physicalActivityType.setVehicalNo("Vehical No");
    physicalActivityType.setVisitCode(1L);
    physicalActivityType.setpAID(1L);
    when(commonNurseServiceImpl.getFamilyHistoryDetail(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(benFamilyHistory);
    when(commonNurseServiceImpl.getPersonalHistory(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(benPersonalHabit);
    when(commonNurseServiceImpl.getBeneficiaryIdrsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(idrsData3);
    when(commonNurseServiceImpl.getPhysicalActivityType(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(physicalActivityType);
    when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Beneficiary Physical Anthropometry Details");
    when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn("Beneficiary Physical Vital Details");

    // Act
    nCDScreeningServiceImpl.getBenNCDScreeningNurseData(1L, 1L);
  }

  @Test
  void testFetchConfirmedScreeningDisease() throws IEMRException {
    // Arrange
    when(hypertensionScreeningRepo.fetchConfirmedScreening(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualFetchConfirmedScreeningDiseaseResult = nCDScreeningServiceImpl.fetchConfirmedScreeningDisease(1L);

    // Assert
    verify(hypertensionScreeningRepo).fetchConfirmedScreening(isA(Long.class));
    assertEquals("No records found for the beneficiary", actualFetchConfirmedScreeningDiseaseResult);
  }

  @Test
  void testFetchConfirmedScreeningDisease2() throws IEMRException {
    // Arrange
    ArrayList<Object[]> objectArrayList = new ArrayList<>();
    objectArrayList.add(new Object[]{"42"});
    when(hypertensionScreeningRepo.fetchConfirmedScreening(Mockito.<Long>any())).thenReturn(objectArrayList);

    // Act and Assert
    assertThrows(IEMRException.class, () -> nCDScreeningServiceImpl.fetchConfirmedScreeningDisease(1L));
    verify(hypertensionScreeningRepo).fetchConfirmedScreening(isA(Long.class));
  }

  @Test
  void testFetchConfirmedScreeningDisease3() throws IEMRException {
    // Arrange
    when(hypertensionScreeningRepo.fetchConfirmedScreening(Mockito.<Long>any()))
        .thenThrow(new RuntimeException("No records found for the beneficiary"));

    // Act and Assert
    assertThrows(IEMRException.class, () -> nCDScreeningServiceImpl.fetchConfirmedScreeningDisease(1L));
    verify(hypertensionScreeningRepo).fetchConfirmedScreening(isA(Long.class));
  }

  @Test
  void testFetchConfirmedScreeningDisease4() throws IEMRException {
    // Arrange
    ArrayList<Object[]> objectArrayList = new ArrayList<>();
    objectArrayList.add(null);
    when(hypertensionScreeningRepo.fetchConfirmedScreening(Mockito.<Long>any())).thenReturn(objectArrayList);

    // Act and Assert
    assertThrows(IEMRException.class, () -> nCDScreeningServiceImpl.fetchConfirmedScreeningDisease(1L));
    verify(hypertensionScreeningRepo).fetchConfirmedScreening(isA(Long.class));
  }

  @Test
  void testGetNCDScreeningData() throws IEMRException {
    // Arrange
    DiabetesScreening diabetesScreening = new DiabetesScreening();
    diabetesScreening.setBeneficiaryRegId(1L);
    diabetesScreening.setBloodGlucose(1);
    diabetesScreening.setBloodGlucoseRemarks("Blood Glucose Remarks");
    diabetesScreening.setBloodGlucoseType("Blood Glucose Type");
    diabetesScreening.setBloodGlucoseTypeId(1);
    diabetesScreening.setConfirmed(true);
    diabetesScreening.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    diabetesScreening.setCreatedDate(mock(Timestamp.class));
    diabetesScreening.setDeleted(true);
    diabetesScreening.setId(1L);
    diabetesScreening.setLastModDate(mock(Timestamp.class));
    diabetesScreening.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    diabetesScreening.setParkingPlaceID(1);
    diabetesScreening.setProcessed("Processed");
    diabetesScreening.setSuspected(true);
    diabetesScreening.setVanID(1);
    diabetesScreening.setVanSerialNo(1L);
    diabetesScreening.setVehicalNo("Vehical No");
    diabetesScreening.setVisitcode(1L);
    when(diabetesScreeningRepo.findByBeneficiaryRegIdAndVisitcode(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(diabetesScreening);

    HypertensionScreening hypertensionScreening = new HypertensionScreening();
    hypertensionScreening.setAverageDiastolicBP(1);
    hypertensionScreening.setAverageSystolicBP(1);
    hypertensionScreening.setBeneficiaryRegId(1L);
    hypertensionScreening.setConfirmed(true);
    hypertensionScreening.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    hypertensionScreening.setCreatedDate(mock(Timestamp.class));
    hypertensionScreening.setDeleted(true);
    hypertensionScreening.setDiastolicBP_1stReading(1);
    hypertensionScreening.setDiastolicBP_2ndReading(1);
    hypertensionScreening.setDiastolicBP_3rdReading(1);
    hypertensionScreening.setId(1L);
    hypertensionScreening.setLastModDate(mock(Timestamp.class));
    hypertensionScreening.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    hypertensionScreening.setParkingPlaceID(1);
    hypertensionScreening.setProcessed("Processed");
    hypertensionScreening.setSuspected(true);
    hypertensionScreening.setSystolicBP_1stReading(1);
    hypertensionScreening.setSystolicBP_2ndReading(1);
    hypertensionScreening.setSystolicBP_3rdReading(1);
    hypertensionScreening.setVanID(1);
    hypertensionScreening.setVanSerialNo(1L);
    hypertensionScreening.setVehicalNo("Vehical No");
    hypertensionScreening.setVisitcode(1L);
    when(hypertensionScreeningRepo.findByBeneficiaryRegIdAndVisitcode(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(hypertensionScreening);
    when(oralCancerScreeningRepo.findByBeneficiaryRegIdAndVisitcode(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenThrow(new RuntimeException("diabetes"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> nCDScreeningServiceImpl.getNCDScreeningData(1L, 1L));
    verify(diabetesScreeningRepo).findByBeneficiaryRegIdAndVisitcode(isA(Long.class), isA(Long.class));
    verify(hypertensionScreeningRepo).findByBeneficiaryRegIdAndVisitcode(isA(Long.class), isA(Long.class));
    verify(oralCancerScreeningRepo).findByBeneficiaryRegIdAndVisitcode(isA(Long.class), isA(Long.class));
  }

  @Test
  void testGetCbacData() throws IEMRException {
    // Arrange
    CbacDetails cbacDetails = new CbacDetails();
    cbacDetails.setBeneficiaryRegId(1L);
    cbacDetails.setCabcTB("Cabc TB");
    cbacDetails.setCbacAge("Cbac Age");
    cbacDetails.setCbacAgeScore(3);
    cbacDetails.setCbacAlcohol("Cbac Alcohol");
    cbacDetails.setCbacAlcoholScore(3);
    cbacDetails.setCbacAntiTBDrugs("Cbac Anti TBDrugs");
    cbacDetails.setCbacBleedingIntercourse("Cbac Bleeding Intercourse");
    cbacDetails.setCbacBleedingMenopause("Cbac Bleeding Menopause");
    cbacDetails.setCbacBleedingPeriods("Cbac Bleeding Periods");
    cbacDetails.setCbacBloodnippleDischarge("Cbac Bloodnipple Discharge");
    cbacDetails.setCbacBloodsputum("Cbac Bloodsputum");
    cbacDetails.setCbacBlurredVision("Cbac Blurred Vision");
    cbacDetails.setCbacBreastsizechange("Cbac Breastsizechange");
    cbacDetails.setCbacClawingfingers("Cbac Clawingfingers");
    cbacDetails.setCbacConsumeGutka("Cbac Consume Gutka");
    cbacDetails.setCbacConsumeGutkaScore(3);
    cbacDetails.setCbacCough2weeks("Cbac Cough2weeks");
    cbacDetails.setCbacDifficultHoldingObjects("Cbac Difficult Holding Objects");
    cbacDetails.setCbacDifficultyHearing("Cbac Difficulty Hearing");
    cbacDetails.setCbacDifficultyreading("Cbac Difficultyreading");
    cbacDetails.setCbacFamilyHistoryBpdiabetes("Cbac Family History Bpdiabetes");
    cbacDetails.setCbacFamilyHistoryBpdiabetesScore(3);
    cbacDetails.setCbacFeelingUnsteady("Cbac Feeling Unsteady");
    cbacDetails.setCbacFeetweakness("Cbac Feetweakness");
    cbacDetails.setCbacFever2weeks("Cbac Fever2weeks");
    cbacDetails.setCbacFitsHistory("Cbac Fits History");
    cbacDetails.setCbacForgetnearones("Cbac Forgetnearones");
    cbacDetails.setCbacHandTingling("Cbac Hand Tingling");
    cbacDetails.setCbacHypopigmentedpatches("Cbac Hypopigmentedpatches");
    cbacDetails.setCbacInabilityCloseeyelid("Cbac Inability Closeeyelid");
    cbacDetails.setCbacLumpBreast("Cbac Lump Breast");
    cbacDetails.setCbacMouthUlcers("Cbac Mouth Ulcers");
    cbacDetails.setCbacMouthUlcersGrowth("Cbac Mouth Ulcers Growth");
    cbacDetails.setCbacMouthopeningDifficulty("Cbac Mouthopening Difficulty");
    cbacDetails.setCbacMouthredpatch("Cbac Mouthredpatch");
    cbacDetails.setCbacNeedhelpEverydayActivities("Cbac Needhelp Everyday Activities");
    cbacDetails.setCbacNightSweats("Cbac Night Sweats");
    cbacDetails.setCbacNodulesonskin("Cbac Nodulesonskin");
    cbacDetails.setCbacPainchewing("Cbac Painchewing");
    cbacDetails.setCbacPainineyes("Cbac Painineyes");
    cbacDetails.setCbacPhysicalActivity("Cbac Physical Activity");
    cbacDetails.setCbacPhysicalActivityScore(3);
    cbacDetails.setCbacPhysicalDisabilitySuffering("Cbac Physical Disability Suffering");
    cbacDetails.setCbacRecurrentNumbness("Cbac Recurrent Numbness");
    cbacDetails.setCbacRecurrentTingling("Cbac Recurrent Tingling");
    cbacDetails.setCbacRednessPain("Cbac Redness Pain");
    cbacDetails.setCbacShortnessBreath("Cbac Shortness Breath");
    cbacDetails.setCbacTBHistory("Cbac TBHistory");
    cbacDetails.setCbacTb("Cbac Tb");
    cbacDetails.setCbacThickenedskin("Cbac Thickenedskin");
    cbacDetails.setCbacTonechange("Cbac Tonechange");
    cbacDetails.setCbacUlceration("Cbac Ulceration");
    cbacDetails.setCbacVaginalDischarge("Cbac Vaginal Discharge");
    cbacDetails.setCbacWaistFemale("Cbac Waist Female");
    cbacDetails.setCbacWaistFemaleScore(3);
    cbacDetails.setCbacWaistMale("Cbac Waist Male");
    cbacDetails.setCbacWaistMaleScore(3);
    cbacDetails.setCbacWeightLoss("Cbac Weight Loss");
    cbacDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    cbacDetails.setCreatedDate(mock(Timestamp.class));
    cbacDetails.setDeleted(true);
    cbacDetails.setId(1L);
    cbacDetails.setLastModDate(mock(Timestamp.class));
    cbacDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    cbacDetails.setParkingPlaceId(1);
    cbacDetails.setProcessed('A');
    cbacDetails.setProviderServiceMapId(1);
    cbacDetails.setSyncedBy("Synced By");
    cbacDetails.setSyncedDate(mock(Timestamp.class));
    cbacDetails.setTotalScore(3);
    cbacDetails.setVanId(1);
    cbacDetails.setVanSerialNo(1);
    cbacDetails.setVehicalNo("Vehical No");
    cbacDetails.setVisitCode(1L);
    cbacDetails.setVisitcode(1L);
    when(cbacDetailsRepo.findByBeneficiaryRegIdAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(cbacDetails);

    // Act and Assert
    assertThrows(IEMRException.class, () -> nCDScreeningServiceImpl.getCbacData(1L, 1L));
    verify(cbacDetailsRepo).findByBeneficiaryRegIdAndVisitCode(isA(Long.class), isA(Long.class));
  }

  @Test
  void testGetCbacData2() throws IEMRException {
    // Arrange
    CbacDetails cbacDetails = mock(CbacDetails.class);
    doNothing().when(cbacDetails).setBeneficiaryRegId(Mockito.<Long>any());
    doNothing().when(cbacDetails).setCabcTB(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacAge(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacAgeScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacAlcohol(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacAlcoholScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacAntiTBDrugs(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBleedingIntercourse(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBleedingMenopause(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBleedingPeriods(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBloodnippleDischarge(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBloodsputum(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBlurredVision(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBreastsizechange(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacClawingfingers(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacConsumeGutka(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacConsumeGutkaScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacCough2weeks(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacDifficultHoldingObjects(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacDifficultyHearing(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacDifficultyreading(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacFamilyHistoryBpdiabetes(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacFamilyHistoryBpdiabetesScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacFeelingUnsteady(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacFeetweakness(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacFever2weeks(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacFitsHistory(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacForgetnearones(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacHandTingling(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacHypopigmentedpatches(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacInabilityCloseeyelid(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacLumpBreast(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacMouthUlcers(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacMouthUlcersGrowth(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacMouthopeningDifficulty(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacMouthredpatch(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacNeedhelpEverydayActivities(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacNightSweats(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacNodulesonskin(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacPainchewing(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacPainineyes(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacPhysicalActivity(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacPhysicalActivityScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacPhysicalDisabilitySuffering(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacRecurrentNumbness(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacRecurrentTingling(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacRednessPain(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacShortnessBreath(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacTBHistory(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacTb(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacThickenedskin(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacTonechange(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacUlceration(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacVaginalDischarge(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacWaistFemale(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacWaistFemaleScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacWaistMale(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacWaistMaleScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacWeightLoss(Mockito.<String>any());
    doNothing().when(cbacDetails).setCreatedBy(Mockito.<String>any());
    doNothing().when(cbacDetails).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(cbacDetails).setDeleted(Mockito.<Boolean>any());
    doNothing().when(cbacDetails).setId(Mockito.<Long>any());
    doNothing().when(cbacDetails).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(cbacDetails).setModifiedBy(Mockito.<String>any());
    doNothing().when(cbacDetails).setParkingPlaceId(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setProcessed(Mockito.<Character>any());
    doNothing().when(cbacDetails).setProviderServiceMapId(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setSyncedBy(Mockito.<String>any());
    doNothing().when(cbacDetails).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(cbacDetails).setTotalScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setVanId(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setVanSerialNo(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setVehicalNo(Mockito.<String>any());
    doNothing().when(cbacDetails).setVisitCode(Mockito.<Long>any());
    doNothing().when(cbacDetails).setVisitcode(Mockito.<Long>any());
    cbacDetails.setBeneficiaryRegId(1L);
    cbacDetails.setCabcTB("Cabc TB");
    cbacDetails.setCbacAge("Cbac Age");
    cbacDetails.setCbacAgeScore(3);
    cbacDetails.setCbacAlcohol("Cbac Alcohol");
    cbacDetails.setCbacAlcoholScore(3);
    cbacDetails.setCbacAntiTBDrugs("Cbac Anti TBDrugs");
    cbacDetails.setCbacBleedingIntercourse("Cbac Bleeding Intercourse");
    cbacDetails.setCbacBleedingMenopause("Cbac Bleeding Menopause");
    cbacDetails.setCbacBleedingPeriods("Cbac Bleeding Periods");
    cbacDetails.setCbacBloodnippleDischarge("Cbac Bloodnipple Discharge");
    cbacDetails.setCbacBloodsputum("Cbac Bloodsputum");
    cbacDetails.setCbacBlurredVision("Cbac Blurred Vision");
    cbacDetails.setCbacBreastsizechange("Cbac Breastsizechange");
    cbacDetails.setCbacClawingfingers("Cbac Clawingfingers");
    cbacDetails.setCbacConsumeGutka("Cbac Consume Gutka");
    cbacDetails.setCbacConsumeGutkaScore(3);
    cbacDetails.setCbacCough2weeks("Cbac Cough2weeks");
    cbacDetails.setCbacDifficultHoldingObjects("Cbac Difficult Holding Objects");
    cbacDetails.setCbacDifficultyHearing("Cbac Difficulty Hearing");
    cbacDetails.setCbacDifficultyreading("Cbac Difficultyreading");
    cbacDetails.setCbacFamilyHistoryBpdiabetes("Cbac Family History Bpdiabetes");
    cbacDetails.setCbacFamilyHistoryBpdiabetesScore(3);
    cbacDetails.setCbacFeelingUnsteady("Cbac Feeling Unsteady");
    cbacDetails.setCbacFeetweakness("Cbac Feetweakness");
    cbacDetails.setCbacFever2weeks("Cbac Fever2weeks");
    cbacDetails.setCbacFitsHistory("Cbac Fits History");
    cbacDetails.setCbacForgetnearones("Cbac Forgetnearones");
    cbacDetails.setCbacHandTingling("Cbac Hand Tingling");
    cbacDetails.setCbacHypopigmentedpatches("Cbac Hypopigmentedpatches");
    cbacDetails.setCbacInabilityCloseeyelid("Cbac Inability Closeeyelid");
    cbacDetails.setCbacLumpBreast("Cbac Lump Breast");
    cbacDetails.setCbacMouthUlcers("Cbac Mouth Ulcers");
    cbacDetails.setCbacMouthUlcersGrowth("Cbac Mouth Ulcers Growth");
    cbacDetails.setCbacMouthopeningDifficulty("Cbac Mouthopening Difficulty");
    cbacDetails.setCbacMouthredpatch("Cbac Mouthredpatch");
    cbacDetails.setCbacNeedhelpEverydayActivities("Cbac Needhelp Everyday Activities");
    cbacDetails.setCbacNightSweats("Cbac Night Sweats");
    cbacDetails.setCbacNodulesonskin("Cbac Nodulesonskin");
    cbacDetails.setCbacPainchewing("Cbac Painchewing");
    cbacDetails.setCbacPainineyes("Cbac Painineyes");
    cbacDetails.setCbacPhysicalActivity("Cbac Physical Activity");
    cbacDetails.setCbacPhysicalActivityScore(3);
    cbacDetails.setCbacPhysicalDisabilitySuffering("Cbac Physical Disability Suffering");
    cbacDetails.setCbacRecurrentNumbness("Cbac Recurrent Numbness");
    cbacDetails.setCbacRecurrentTingling("Cbac Recurrent Tingling");
    cbacDetails.setCbacRednessPain("Cbac Redness Pain");
    cbacDetails.setCbacShortnessBreath("Cbac Shortness Breath");
    cbacDetails.setCbacTBHistory("Cbac TBHistory");
    cbacDetails.setCbacTb("Cbac Tb");
    cbacDetails.setCbacThickenedskin("Cbac Thickenedskin");
    cbacDetails.setCbacTonechange("Cbac Tonechange");
    cbacDetails.setCbacUlceration("Cbac Ulceration");
    cbacDetails.setCbacVaginalDischarge("Cbac Vaginal Discharge");
    cbacDetails.setCbacWaistFemale("Cbac Waist Female");
    cbacDetails.setCbacWaistFemaleScore(3);
    cbacDetails.setCbacWaistMale("Cbac Waist Male");
    cbacDetails.setCbacWaistMaleScore(3);
    cbacDetails.setCbacWeightLoss("Cbac Weight Loss");
    cbacDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    cbacDetails.setCreatedDate(mock(Timestamp.class));
    cbacDetails.setDeleted(true);
    cbacDetails.setId(1L);
    cbacDetails.setLastModDate(mock(Timestamp.class));
    cbacDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    cbacDetails.setParkingPlaceId(1);
    cbacDetails.setProcessed('A');
    cbacDetails.setProviderServiceMapId(1);
    cbacDetails.setSyncedBy("Synced By");
    cbacDetails.setSyncedDate(mock(Timestamp.class));
    cbacDetails.setTotalScore(3);
    cbacDetails.setVanId(1);
    cbacDetails.setVanSerialNo(1);
    cbacDetails.setVehicalNo("Vehical No");
    cbacDetails.setVisitCode(1L);
    cbacDetails.setVisitcode(1L);
    when(cbacDetailsRepo.findByBeneficiaryRegIdAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(cbacDetails);

    // Act and Assert
    assertThrows(IEMRException.class, () -> nCDScreeningServiceImpl.getCbacData(1L, 1L));
    verify(cbacDetails).setBeneficiaryRegId(isA(Long.class));
    verify(cbacDetails).setCabcTB(eq("Cabc TB"));
    verify(cbacDetails).setCbacAge(eq("Cbac Age"));
    verify(cbacDetails).setCbacAgeScore(isA(Integer.class));
    verify(cbacDetails).setCbacAlcohol(eq("Cbac Alcohol"));
    verify(cbacDetails).setCbacAlcoholScore(isA(Integer.class));
    verify(cbacDetails).setCbacAntiTBDrugs(eq("Cbac Anti TBDrugs"));
    verify(cbacDetails).setCbacBleedingIntercourse(eq("Cbac Bleeding Intercourse"));
    verify(cbacDetails).setCbacBleedingMenopause(eq("Cbac Bleeding Menopause"));
    verify(cbacDetails).setCbacBleedingPeriods(eq("Cbac Bleeding Periods"));
    verify(cbacDetails).setCbacBloodnippleDischarge(eq("Cbac Bloodnipple Discharge"));
    verify(cbacDetails).setCbacBloodsputum(eq("Cbac Bloodsputum"));
    verify(cbacDetails).setCbacBlurredVision(eq("Cbac Blurred Vision"));
    verify(cbacDetails).setCbacBreastsizechange(eq("Cbac Breastsizechange"));
    verify(cbacDetails).setCbacClawingfingers(eq("Cbac Clawingfingers"));
    verify(cbacDetails).setCbacConsumeGutka(eq("Cbac Consume Gutka"));
    verify(cbacDetails).setCbacConsumeGutkaScore(isA(Integer.class));
    verify(cbacDetails).setCbacCough2weeks(eq("Cbac Cough2weeks"));
    verify(cbacDetails).setCbacDifficultHoldingObjects(eq("Cbac Difficult Holding Objects"));
    verify(cbacDetails).setCbacDifficultyHearing(eq("Cbac Difficulty Hearing"));
    verify(cbacDetails).setCbacDifficultyreading(eq("Cbac Difficultyreading"));
    verify(cbacDetails).setCbacFamilyHistoryBpdiabetes(eq("Cbac Family History Bpdiabetes"));
    verify(cbacDetails).setCbacFamilyHistoryBpdiabetesScore(isA(Integer.class));
    verify(cbacDetails).setCbacFeelingUnsteady(eq("Cbac Feeling Unsteady"));
    verify(cbacDetails).setCbacFeetweakness(eq("Cbac Feetweakness"));
    verify(cbacDetails).setCbacFever2weeks(eq("Cbac Fever2weeks"));
    verify(cbacDetails).setCbacFitsHistory(eq("Cbac Fits History"));
    verify(cbacDetails).setCbacForgetnearones(eq("Cbac Forgetnearones"));
    verify(cbacDetails).setCbacHandTingling(eq("Cbac Hand Tingling"));
    verify(cbacDetails).setCbacHypopigmentedpatches(eq("Cbac Hypopigmentedpatches"));
    verify(cbacDetails).setCbacInabilityCloseeyelid(eq("Cbac Inability Closeeyelid"));
    verify(cbacDetails).setCbacLumpBreast(eq("Cbac Lump Breast"));
    verify(cbacDetails).setCbacMouthUlcers(eq("Cbac Mouth Ulcers"));
    verify(cbacDetails).setCbacMouthUlcersGrowth(eq("Cbac Mouth Ulcers Growth"));
    verify(cbacDetails).setCbacMouthopeningDifficulty(eq("Cbac Mouthopening Difficulty"));
    verify(cbacDetails).setCbacMouthredpatch(eq("Cbac Mouthredpatch"));
    verify(cbacDetails).setCbacNeedhelpEverydayActivities(eq("Cbac Needhelp Everyday Activities"));
    verify(cbacDetails).setCbacNightSweats(eq("Cbac Night Sweats"));
    verify(cbacDetails).setCbacNodulesonskin(eq("Cbac Nodulesonskin"));
    verify(cbacDetails).setCbacPainchewing(eq("Cbac Painchewing"));
    verify(cbacDetails).setCbacPainineyes(eq("Cbac Painineyes"));
    verify(cbacDetails).setCbacPhysicalActivity(eq("Cbac Physical Activity"));
    verify(cbacDetails).setCbacPhysicalActivityScore(isA(Integer.class));
    verify(cbacDetails).setCbacPhysicalDisabilitySuffering(eq("Cbac Physical Disability Suffering"));
    verify(cbacDetails).setCbacRecurrentNumbness(eq("Cbac Recurrent Numbness"));
    verify(cbacDetails).setCbacRecurrentTingling(eq("Cbac Recurrent Tingling"));
    verify(cbacDetails).setCbacRednessPain(eq("Cbac Redness Pain"));
    verify(cbacDetails).setCbacShortnessBreath(eq("Cbac Shortness Breath"));
    verify(cbacDetails).setCbacTBHistory(eq("Cbac TBHistory"));
    verify(cbacDetails).setCbacTb(eq("Cbac Tb"));
    verify(cbacDetails).setCbacThickenedskin(eq("Cbac Thickenedskin"));
    verify(cbacDetails).setCbacTonechange(eq("Cbac Tonechange"));
    verify(cbacDetails).setCbacUlceration(eq("Cbac Ulceration"));
    verify(cbacDetails).setCbacVaginalDischarge(eq("Cbac Vaginal Discharge"));
    verify(cbacDetails).setCbacWaistFemale(eq("Cbac Waist Female"));
    verify(cbacDetails).setCbacWaistFemaleScore(isA(Integer.class));
    verify(cbacDetails).setCbacWaistMale(eq("Cbac Waist Male"));
    verify(cbacDetails).setCbacWaistMaleScore(isA(Integer.class));
    verify(cbacDetails).setCbacWeightLoss(eq("Cbac Weight Loss"));
    verify(cbacDetails).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(cbacDetails).setCreatedDate(isA(Timestamp.class));
    verify(cbacDetails).setDeleted(isA(Boolean.class));
    verify(cbacDetails).setId(isA(Long.class));
    verify(cbacDetails).setLastModDate(isA(Timestamp.class));
    verify(cbacDetails).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(cbacDetails).setParkingPlaceId(isA(Integer.class));
    verify(cbacDetails).setProcessed(isA(Character.class));
    verify(cbacDetails).setProviderServiceMapId(isA(Integer.class));
    verify(cbacDetails).setSyncedBy(eq("Synced By"));
    verify(cbacDetails).setSyncedDate(isA(Timestamp.class));
    verify(cbacDetails).setTotalScore(isA(Integer.class));
    verify(cbacDetails).setVanId(isA(Integer.class));
    verify(cbacDetails).setVanSerialNo(isA(Integer.class));
    verify(cbacDetails).setVehicalNo(eq("Vehical No"));
    verify(cbacDetails).setVisitCode(isA(Long.class));
    verify(cbacDetails).setVisitcode(isA(Long.class));
    verify(cbacDetailsRepo).findByBeneficiaryRegIdAndVisitCode(isA(Long.class), isA(Long.class));
  }

  @Test
  void testGetCbacData3() throws IEMRException {
    // Arrange
    CbacDetails cbacDetails = mock(CbacDetails.class);
    doNothing().when(cbacDetails).setBeneficiaryRegId(Mockito.<Long>any());
    doNothing().when(cbacDetails).setCabcTB(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacAge(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacAgeScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacAlcohol(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacAlcoholScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacAntiTBDrugs(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBleedingIntercourse(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBleedingMenopause(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBleedingPeriods(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBloodnippleDischarge(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBloodsputum(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBlurredVision(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBreastsizechange(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacClawingfingers(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacConsumeGutka(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacConsumeGutkaScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacCough2weeks(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacDifficultHoldingObjects(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacDifficultyHearing(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacDifficultyreading(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacFamilyHistoryBpdiabetes(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacFamilyHistoryBpdiabetesScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacFeelingUnsteady(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacFeetweakness(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacFever2weeks(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacFitsHistory(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacForgetnearones(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacHandTingling(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacHypopigmentedpatches(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacInabilityCloseeyelid(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacLumpBreast(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacMouthUlcers(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacMouthUlcersGrowth(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacMouthopeningDifficulty(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacMouthredpatch(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacNeedhelpEverydayActivities(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacNightSweats(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacNodulesonskin(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacPainchewing(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacPainineyes(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacPhysicalActivity(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacPhysicalActivityScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacPhysicalDisabilitySuffering(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacRecurrentNumbness(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacRecurrentTingling(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacRednessPain(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacShortnessBreath(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacTBHistory(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacTb(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacThickenedskin(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacTonechange(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacUlceration(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacVaginalDischarge(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacWaistFemale(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacWaistFemaleScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacWaistMale(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacWaistMaleScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacWeightLoss(Mockito.<String>any());
    doNothing().when(cbacDetails).setCreatedBy(Mockito.<String>any());
    doNothing().when(cbacDetails).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(cbacDetails).setDeleted(Mockito.<Boolean>any());
    doNothing().when(cbacDetails).setId(Mockito.<Long>any());
    doNothing().when(cbacDetails).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(cbacDetails).setModifiedBy(Mockito.<String>any());
    doNothing().when(cbacDetails).setParkingPlaceId(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setProcessed(Mockito.<Character>any());
    doNothing().when(cbacDetails).setProviderServiceMapId(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setSyncedBy(Mockito.<String>any());
    doNothing().when(cbacDetails).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(cbacDetails).setTotalScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setVanId(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setVanSerialNo(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setVehicalNo(Mockito.<String>any());
    doNothing().when(cbacDetails).setVisitCode(Mockito.<Long>any());
    doNothing().when(cbacDetails).setVisitcode(Mockito.<Long>any());
    cbacDetails.setBeneficiaryRegId(8L);
    cbacDetails.setCabcTB("Cabc TB");
    cbacDetails.setCbacAge("Cbac Age");
    cbacDetails.setCbacAgeScore(3);
    cbacDetails.setCbacAlcohol("Cbac Alcohol");
    cbacDetails.setCbacAlcoholScore(3);
    cbacDetails.setCbacAntiTBDrugs("Cbac Anti TBDrugs");
    cbacDetails.setCbacBleedingIntercourse("Cbac Bleeding Intercourse");
    cbacDetails.setCbacBleedingMenopause("Cbac Bleeding Menopause");
    cbacDetails.setCbacBleedingPeriods("Cbac Bleeding Periods");
    cbacDetails.setCbacBloodnippleDischarge("Cbac Bloodnipple Discharge");
    cbacDetails.setCbacBloodsputum("Cbac Bloodsputum");
    cbacDetails.setCbacBlurredVision("Cbac Blurred Vision");
    cbacDetails.setCbacBreastsizechange("Cbac Breastsizechange");
    cbacDetails.setCbacClawingfingers("Cbac Clawingfingers");
    cbacDetails.setCbacConsumeGutka("Cbac Consume Gutka");
    cbacDetails.setCbacConsumeGutkaScore(3);
    cbacDetails.setCbacCough2weeks("Cbac Cough2weeks");
    cbacDetails.setCbacDifficultHoldingObjects("Cbac Difficult Holding Objects");
    cbacDetails.setCbacDifficultyHearing("Cbac Difficulty Hearing");
    cbacDetails.setCbacDifficultyreading("Cbac Difficultyreading");
    cbacDetails.setCbacFamilyHistoryBpdiabetes("Cbac Family History Bpdiabetes");
    cbacDetails.setCbacFamilyHistoryBpdiabetesScore(3);
    cbacDetails.setCbacFeelingUnsteady("Cbac Feeling Unsteady");
    cbacDetails.setCbacFeetweakness("Cbac Feetweakness");
    cbacDetails.setCbacFever2weeks("Cbac Fever2weeks");
    cbacDetails.setCbacFitsHistory("Cbac Fits History");
    cbacDetails.setCbacForgetnearones("Cbac Forgetnearones");
    cbacDetails.setCbacHandTingling("Cbac Hand Tingling");
    cbacDetails.setCbacHypopigmentedpatches("Cbac Hypopigmentedpatches");
    cbacDetails.setCbacInabilityCloseeyelid("Cbac Inability Closeeyelid");
    cbacDetails.setCbacLumpBreast("Cbac Lump Breast");
    cbacDetails.setCbacMouthUlcers("Cbac Mouth Ulcers");
    cbacDetails.setCbacMouthUlcersGrowth("Cbac Mouth Ulcers Growth");
    cbacDetails.setCbacMouthopeningDifficulty("Cbac Mouthopening Difficulty");
    cbacDetails.setCbacMouthredpatch("Cbac Mouthredpatch");
    cbacDetails.setCbacNeedhelpEverydayActivities("Cbac Needhelp Everyday Activities");
    cbacDetails.setCbacNightSweats("Cbac Night Sweats");
    cbacDetails.setCbacNodulesonskin("Cbac Nodulesonskin");
    cbacDetails.setCbacPainchewing("Cbac Painchewing");
    cbacDetails.setCbacPainineyes("Cbac Painineyes");
    cbacDetails.setCbacPhysicalActivity("Cbac Physical Activity");
    cbacDetails.setCbacPhysicalActivityScore(3);
    cbacDetails.setCbacPhysicalDisabilitySuffering("Cbac Physical Disability Suffering");
    cbacDetails.setCbacRecurrentNumbness("Cbac Recurrent Numbness");
    cbacDetails.setCbacRecurrentTingling("Cbac Recurrent Tingling");
    cbacDetails.setCbacRednessPain("Cbac Redness Pain");
    cbacDetails.setCbacShortnessBreath("Cbac Shortness Breath");
    cbacDetails.setCbacTBHistory("Cbac TBHistory");
    cbacDetails.setCbacTb("Cbac Tb");
    cbacDetails.setCbacThickenedskin("Cbac Thickenedskin");
    cbacDetails.setCbacTonechange("Cbac Tonechange");
    cbacDetails.setCbacUlceration("Cbac Ulceration");
    cbacDetails.setCbacVaginalDischarge("Cbac Vaginal Discharge");
    cbacDetails.setCbacWaistFemale("Cbac Waist Female");
    cbacDetails.setCbacWaistFemaleScore(3);
    cbacDetails.setCbacWaistMale("Cbac Waist Male");
    cbacDetails.setCbacWaistMaleScore(3);
    cbacDetails.setCbacWeightLoss("Cbac Weight Loss");
    cbacDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    cbacDetails.setCreatedDate(mock(Timestamp.class));
    cbacDetails.setDeleted(true);
    cbacDetails.setId(1L);
    cbacDetails.setLastModDate(mock(Timestamp.class));
    cbacDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    cbacDetails.setParkingPlaceId(1);
    cbacDetails.setProcessed('A');
    cbacDetails.setProviderServiceMapId(1);
    cbacDetails.setSyncedBy("Synced By");
    cbacDetails.setSyncedDate(mock(Timestamp.class));
    cbacDetails.setTotalScore(3);
    cbacDetails.setVanId(1);
    cbacDetails.setVanSerialNo(1);
    cbacDetails.setVehicalNo("Vehical No");
    cbacDetails.setVisitCode(1L);
    cbacDetails.setVisitcode(1L);
    when(cbacDetailsRepo.findByBeneficiaryRegIdAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(cbacDetails);

    // Act and Assert
    assertThrows(IEMRException.class, () -> nCDScreeningServiceImpl.getCbacData(1L, 1L));
    verify(cbacDetails).setBeneficiaryRegId(isA(Long.class));
    verify(cbacDetails).setCabcTB(eq("Cabc TB"));
    verify(cbacDetails).setCbacAge(eq("Cbac Age"));
    verify(cbacDetails).setCbacAgeScore(isA(Integer.class));
    verify(cbacDetails).setCbacAlcohol(eq("Cbac Alcohol"));
    verify(cbacDetails).setCbacAlcoholScore(isA(Integer.class));
    verify(cbacDetails).setCbacAntiTBDrugs(eq("Cbac Anti TBDrugs"));
    verify(cbacDetails).setCbacBleedingIntercourse(eq("Cbac Bleeding Intercourse"));
    verify(cbacDetails).setCbacBleedingMenopause(eq("Cbac Bleeding Menopause"));
    verify(cbacDetails).setCbacBleedingPeriods(eq("Cbac Bleeding Periods"));
    verify(cbacDetails).setCbacBloodnippleDischarge(eq("Cbac Bloodnipple Discharge"));
    verify(cbacDetails).setCbacBloodsputum(eq("Cbac Bloodsputum"));
    verify(cbacDetails).setCbacBlurredVision(eq("Cbac Blurred Vision"));
    verify(cbacDetails).setCbacBreastsizechange(eq("Cbac Breastsizechange"));
    verify(cbacDetails).setCbacClawingfingers(eq("Cbac Clawingfingers"));
    verify(cbacDetails).setCbacConsumeGutka(eq("Cbac Consume Gutka"));
    verify(cbacDetails).setCbacConsumeGutkaScore(isA(Integer.class));
    verify(cbacDetails).setCbacCough2weeks(eq("Cbac Cough2weeks"));
    verify(cbacDetails).setCbacDifficultHoldingObjects(eq("Cbac Difficult Holding Objects"));
    verify(cbacDetails).setCbacDifficultyHearing(eq("Cbac Difficulty Hearing"));
    verify(cbacDetails).setCbacDifficultyreading(eq("Cbac Difficultyreading"));
    verify(cbacDetails).setCbacFamilyHistoryBpdiabetes(eq("Cbac Family History Bpdiabetes"));
    verify(cbacDetails).setCbacFamilyHistoryBpdiabetesScore(isA(Integer.class));
    verify(cbacDetails).setCbacFeelingUnsteady(eq("Cbac Feeling Unsteady"));
    verify(cbacDetails).setCbacFeetweakness(eq("Cbac Feetweakness"));
    verify(cbacDetails).setCbacFever2weeks(eq("Cbac Fever2weeks"));
    verify(cbacDetails).setCbacFitsHistory(eq("Cbac Fits History"));
    verify(cbacDetails).setCbacForgetnearones(eq("Cbac Forgetnearones"));
    verify(cbacDetails).setCbacHandTingling(eq("Cbac Hand Tingling"));
    verify(cbacDetails).setCbacHypopigmentedpatches(eq("Cbac Hypopigmentedpatches"));
    verify(cbacDetails).setCbacInabilityCloseeyelid(eq("Cbac Inability Closeeyelid"));
    verify(cbacDetails).setCbacLumpBreast(eq("Cbac Lump Breast"));
    verify(cbacDetails).setCbacMouthUlcers(eq("Cbac Mouth Ulcers"));
    verify(cbacDetails).setCbacMouthUlcersGrowth(eq("Cbac Mouth Ulcers Growth"));
    verify(cbacDetails).setCbacMouthopeningDifficulty(eq("Cbac Mouthopening Difficulty"));
    verify(cbacDetails).setCbacMouthredpatch(eq("Cbac Mouthredpatch"));
    verify(cbacDetails).setCbacNeedhelpEverydayActivities(eq("Cbac Needhelp Everyday Activities"));
    verify(cbacDetails).setCbacNightSweats(eq("Cbac Night Sweats"));
    verify(cbacDetails).setCbacNodulesonskin(eq("Cbac Nodulesonskin"));
    verify(cbacDetails).setCbacPainchewing(eq("Cbac Painchewing"));
    verify(cbacDetails).setCbacPainineyes(eq("Cbac Painineyes"));
    verify(cbacDetails).setCbacPhysicalActivity(eq("Cbac Physical Activity"));
    verify(cbacDetails).setCbacPhysicalActivityScore(isA(Integer.class));
    verify(cbacDetails).setCbacPhysicalDisabilitySuffering(eq("Cbac Physical Disability Suffering"));
    verify(cbacDetails).setCbacRecurrentNumbness(eq("Cbac Recurrent Numbness"));
    verify(cbacDetails).setCbacRecurrentTingling(eq("Cbac Recurrent Tingling"));
    verify(cbacDetails).setCbacRednessPain(eq("Cbac Redness Pain"));
    verify(cbacDetails).setCbacShortnessBreath(eq("Cbac Shortness Breath"));
    verify(cbacDetails).setCbacTBHistory(eq("Cbac TBHistory"));
    verify(cbacDetails).setCbacTb(eq("Cbac Tb"));
    verify(cbacDetails).setCbacThickenedskin(eq("Cbac Thickenedskin"));
    verify(cbacDetails).setCbacTonechange(eq("Cbac Tonechange"));
    verify(cbacDetails).setCbacUlceration(eq("Cbac Ulceration"));
    verify(cbacDetails).setCbacVaginalDischarge(eq("Cbac Vaginal Discharge"));
    verify(cbacDetails).setCbacWaistFemale(eq("Cbac Waist Female"));
    verify(cbacDetails).setCbacWaistFemaleScore(isA(Integer.class));
    verify(cbacDetails).setCbacWaistMale(eq("Cbac Waist Male"));
    verify(cbacDetails).setCbacWaistMaleScore(isA(Integer.class));
    verify(cbacDetails).setCbacWeightLoss(eq("Cbac Weight Loss"));
    verify(cbacDetails).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(cbacDetails).setCreatedDate(isA(Timestamp.class));
    verify(cbacDetails).setDeleted(isA(Boolean.class));
    verify(cbacDetails).setId(isA(Long.class));
    verify(cbacDetails).setLastModDate(isA(Timestamp.class));
    verify(cbacDetails).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(cbacDetails).setParkingPlaceId(isA(Integer.class));
    verify(cbacDetails).setProcessed(isA(Character.class));
    verify(cbacDetails).setProviderServiceMapId(isA(Integer.class));
    verify(cbacDetails).setSyncedBy(eq("Synced By"));
    verify(cbacDetails).setSyncedDate(isA(Timestamp.class));
    verify(cbacDetails).setTotalScore(isA(Integer.class));
    verify(cbacDetails).setVanId(isA(Integer.class));
    verify(cbacDetails).setVanSerialNo(isA(Integer.class));
    verify(cbacDetails).setVehicalNo(eq("Vehical No"));
    verify(cbacDetails).setVisitCode(isA(Long.class));
    verify(cbacDetails).setVisitcode(isA(Long.class));
    verify(cbacDetailsRepo).findByBeneficiaryRegIdAndVisitCode(isA(Long.class), isA(Long.class));
  }

  @Test
  void testGetCbacData4() throws IEMRException {
    // Arrange
    CbacDetails cbacDetails = mock(CbacDetails.class);
    doNothing().when(cbacDetails).setBeneficiaryRegId(Mockito.<Long>any());
    doNothing().when(cbacDetails).setCabcTB(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacAge(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacAgeScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacAlcohol(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacAlcoholScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacAntiTBDrugs(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBleedingIntercourse(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBleedingMenopause(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBleedingPeriods(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBloodnippleDischarge(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBloodsputum(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBlurredVision(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacBreastsizechange(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacClawingfingers(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacConsumeGutka(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacConsumeGutkaScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacCough2weeks(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacDifficultHoldingObjects(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacDifficultyHearing(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacDifficultyreading(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacFamilyHistoryBpdiabetes(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacFamilyHistoryBpdiabetesScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacFeelingUnsteady(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacFeetweakness(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacFever2weeks(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacFitsHistory(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacForgetnearones(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacHandTingling(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacHypopigmentedpatches(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacInabilityCloseeyelid(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacLumpBreast(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacMouthUlcers(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacMouthUlcersGrowth(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacMouthopeningDifficulty(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacMouthredpatch(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacNeedhelpEverydayActivities(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacNightSweats(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacNodulesonskin(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacPainchewing(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacPainineyes(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacPhysicalActivity(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacPhysicalActivityScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacPhysicalDisabilitySuffering(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacRecurrentNumbness(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacRecurrentTingling(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacRednessPain(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacShortnessBreath(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacTBHistory(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacTb(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacThickenedskin(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacTonechange(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacUlceration(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacVaginalDischarge(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacWaistFemale(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacWaistFemaleScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacWaistMale(Mockito.<String>any());
    doNothing().when(cbacDetails).setCbacWaistMaleScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setCbacWeightLoss(Mockito.<String>any());
    doNothing().when(cbacDetails).setCreatedBy(Mockito.<String>any());
    doNothing().when(cbacDetails).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(cbacDetails).setDeleted(Mockito.<Boolean>any());
    doNothing().when(cbacDetails).setId(Mockito.<Long>any());
    doNothing().when(cbacDetails).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(cbacDetails).setModifiedBy(Mockito.<String>any());
    doNothing().when(cbacDetails).setParkingPlaceId(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setProcessed(Mockito.<Character>any());
    doNothing().when(cbacDetails).setProviderServiceMapId(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setSyncedBy(Mockito.<String>any());
    doNothing().when(cbacDetails).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(cbacDetails).setTotalScore(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setVanId(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setVanSerialNo(Mockito.<Integer>any());
    doNothing().when(cbacDetails).setVehicalNo(Mockito.<String>any());
    doNothing().when(cbacDetails).setVisitCode(Mockito.<Long>any());
    doNothing().when(cbacDetails).setVisitcode(Mockito.<Long>any());
    cbacDetails.setBeneficiaryRegId(1L);
    cbacDetails.setCabcTB("");
    cbacDetails.setCbacAge("Cbac Age");
    cbacDetails.setCbacAgeScore(3);
    cbacDetails.setCbacAlcohol("Cbac Alcohol");
    cbacDetails.setCbacAlcoholScore(3);
    cbacDetails.setCbacAntiTBDrugs("Cbac Anti TBDrugs");
    cbacDetails.setCbacBleedingIntercourse("Cbac Bleeding Intercourse");
    cbacDetails.setCbacBleedingMenopause("Cbac Bleeding Menopause");
    cbacDetails.setCbacBleedingPeriods("Cbac Bleeding Periods");
    cbacDetails.setCbacBloodnippleDischarge("Cbac Bloodnipple Discharge");
    cbacDetails.setCbacBloodsputum("Cbac Bloodsputum");
    cbacDetails.setCbacBlurredVision("Cbac Blurred Vision");
    cbacDetails.setCbacBreastsizechange("Cbac Breastsizechange");
    cbacDetails.setCbacClawingfingers("Cbac Clawingfingers");
    cbacDetails.setCbacConsumeGutka("Cbac Consume Gutka");
    cbacDetails.setCbacConsumeGutkaScore(3);
    cbacDetails.setCbacCough2weeks("Cbac Cough2weeks");
    cbacDetails.setCbacDifficultHoldingObjects("Cbac Difficult Holding Objects");
    cbacDetails.setCbacDifficultyHearing("Cbac Difficulty Hearing");
    cbacDetails.setCbacDifficultyreading("Cbac Difficultyreading");
    cbacDetails.setCbacFamilyHistoryBpdiabetes("Cbac Family History Bpdiabetes");
    cbacDetails.setCbacFamilyHistoryBpdiabetesScore(3);
    cbacDetails.setCbacFeelingUnsteady("Cbac Feeling Unsteady");
    cbacDetails.setCbacFeetweakness("Cbac Feetweakness");
    cbacDetails.setCbacFever2weeks("Cbac Fever2weeks");
    cbacDetails.setCbacFitsHistory("Cbac Fits History");
    cbacDetails.setCbacForgetnearones("Cbac Forgetnearones");
    cbacDetails.setCbacHandTingling("Cbac Hand Tingling");
    cbacDetails.setCbacHypopigmentedpatches("Cbac Hypopigmentedpatches");
    cbacDetails.setCbacInabilityCloseeyelid("Cbac Inability Closeeyelid");
    cbacDetails.setCbacLumpBreast("Cbac Lump Breast");
    cbacDetails.setCbacMouthUlcers("Cbac Mouth Ulcers");
    cbacDetails.setCbacMouthUlcersGrowth("Cbac Mouth Ulcers Growth");
    cbacDetails.setCbacMouthopeningDifficulty("Cbac Mouthopening Difficulty");
    cbacDetails.setCbacMouthredpatch("Cbac Mouthredpatch");
    cbacDetails.setCbacNeedhelpEverydayActivities("Cbac Needhelp Everyday Activities");
    cbacDetails.setCbacNightSweats("Cbac Night Sweats");
    cbacDetails.setCbacNodulesonskin("Cbac Nodulesonskin");
    cbacDetails.setCbacPainchewing("Cbac Painchewing");
    cbacDetails.setCbacPainineyes("Cbac Painineyes");
    cbacDetails.setCbacPhysicalActivity("Cbac Physical Activity");
    cbacDetails.setCbacPhysicalActivityScore(3);
    cbacDetails.setCbacPhysicalDisabilitySuffering("Cbac Physical Disability Suffering");
    cbacDetails.setCbacRecurrentNumbness("Cbac Recurrent Numbness");
    cbacDetails.setCbacRecurrentTingling("Cbac Recurrent Tingling");
    cbacDetails.setCbacRednessPain("Cbac Redness Pain");
    cbacDetails.setCbacShortnessBreath("Cbac Shortness Breath");
    cbacDetails.setCbacTBHistory("Cbac TBHistory");
    cbacDetails.setCbacTb("Cbac Tb");
    cbacDetails.setCbacThickenedskin("Cbac Thickenedskin");
    cbacDetails.setCbacTonechange("Cbac Tonechange");
    cbacDetails.setCbacUlceration("Cbac Ulceration");
    cbacDetails.setCbacVaginalDischarge("Cbac Vaginal Discharge");
    cbacDetails.setCbacWaistFemale("Cbac Waist Female");
    cbacDetails.setCbacWaistFemaleScore(3);
    cbacDetails.setCbacWaistMale("Cbac Waist Male");
    cbacDetails.setCbacWaistMaleScore(3);
    cbacDetails.setCbacWeightLoss("Cbac Weight Loss");
    cbacDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    cbacDetails.setCreatedDate(mock(Timestamp.class));
    cbacDetails.setDeleted(true);
    cbacDetails.setId(1L);
    cbacDetails.setLastModDate(mock(Timestamp.class));
    cbacDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    cbacDetails.setParkingPlaceId(1);
    cbacDetails.setProcessed('A');
    cbacDetails.setProviderServiceMapId(1);
    cbacDetails.setSyncedBy("Synced By");
    cbacDetails.setSyncedDate(mock(Timestamp.class));
    cbacDetails.setTotalScore(3);
    cbacDetails.setVanId(1);
    cbacDetails.setVanSerialNo(1);
    cbacDetails.setVehicalNo("Vehical No");
    cbacDetails.setVisitCode(1L);
    cbacDetails.setVisitcode(1L);
    when(cbacDetailsRepo.findByBeneficiaryRegIdAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(cbacDetails);

    // Act and Assert
    assertThrows(IEMRException.class, () -> nCDScreeningServiceImpl.getCbacData(1L, 1L));
    verify(cbacDetails).setBeneficiaryRegId(isA(Long.class));
    verify(cbacDetails).setCabcTB(eq(""));
    verify(cbacDetails).setCbacAge(eq("Cbac Age"));
    verify(cbacDetails).setCbacAgeScore(isA(Integer.class));
    verify(cbacDetails).setCbacAlcohol(eq("Cbac Alcohol"));
    verify(cbacDetails).setCbacAlcoholScore(isA(Integer.class));
    verify(cbacDetails).setCbacAntiTBDrugs(eq("Cbac Anti TBDrugs"));
    verify(cbacDetails).setCbacBleedingIntercourse(eq("Cbac Bleeding Intercourse"));
    verify(cbacDetails).setCbacBleedingMenopause(eq("Cbac Bleeding Menopause"));
    verify(cbacDetails).setCbacBleedingPeriods(eq("Cbac Bleeding Periods"));
    verify(cbacDetails).setCbacBloodnippleDischarge(eq("Cbac Bloodnipple Discharge"));
    verify(cbacDetails).setCbacBloodsputum(eq("Cbac Bloodsputum"));
    verify(cbacDetails).setCbacBlurredVision(eq("Cbac Blurred Vision"));
    verify(cbacDetails).setCbacBreastsizechange(eq("Cbac Breastsizechange"));
    verify(cbacDetails).setCbacClawingfingers(eq("Cbac Clawingfingers"));
    verify(cbacDetails).setCbacConsumeGutka(eq("Cbac Consume Gutka"));
    verify(cbacDetails).setCbacConsumeGutkaScore(isA(Integer.class));
    verify(cbacDetails).setCbacCough2weeks(eq("Cbac Cough2weeks"));
    verify(cbacDetails).setCbacDifficultHoldingObjects(eq("Cbac Difficult Holding Objects"));
    verify(cbacDetails).setCbacDifficultyHearing(eq("Cbac Difficulty Hearing"));
    verify(cbacDetails).setCbacDifficultyreading(eq("Cbac Difficultyreading"));
    verify(cbacDetails).setCbacFamilyHistoryBpdiabetes(eq("Cbac Family History Bpdiabetes"));
    verify(cbacDetails).setCbacFamilyHistoryBpdiabetesScore(isA(Integer.class));
    verify(cbacDetails).setCbacFeelingUnsteady(eq("Cbac Feeling Unsteady"));
    verify(cbacDetails).setCbacFeetweakness(eq("Cbac Feetweakness"));
    verify(cbacDetails).setCbacFever2weeks(eq("Cbac Fever2weeks"));
    verify(cbacDetails).setCbacFitsHistory(eq("Cbac Fits History"));
    verify(cbacDetails).setCbacForgetnearones(eq("Cbac Forgetnearones"));
    verify(cbacDetails).setCbacHandTingling(eq("Cbac Hand Tingling"));
    verify(cbacDetails).setCbacHypopigmentedpatches(eq("Cbac Hypopigmentedpatches"));
    verify(cbacDetails).setCbacInabilityCloseeyelid(eq("Cbac Inability Closeeyelid"));
    verify(cbacDetails).setCbacLumpBreast(eq("Cbac Lump Breast"));
    verify(cbacDetails).setCbacMouthUlcers(eq("Cbac Mouth Ulcers"));
    verify(cbacDetails).setCbacMouthUlcersGrowth(eq("Cbac Mouth Ulcers Growth"));
    verify(cbacDetails).setCbacMouthopeningDifficulty(eq("Cbac Mouthopening Difficulty"));
    verify(cbacDetails).setCbacMouthredpatch(eq("Cbac Mouthredpatch"));
    verify(cbacDetails).setCbacNeedhelpEverydayActivities(eq("Cbac Needhelp Everyday Activities"));
    verify(cbacDetails).setCbacNightSweats(eq("Cbac Night Sweats"));
    verify(cbacDetails).setCbacNodulesonskin(eq("Cbac Nodulesonskin"));
    verify(cbacDetails).setCbacPainchewing(eq("Cbac Painchewing"));
    verify(cbacDetails).setCbacPainineyes(eq("Cbac Painineyes"));
    verify(cbacDetails).setCbacPhysicalActivity(eq("Cbac Physical Activity"));
    verify(cbacDetails).setCbacPhysicalActivityScore(isA(Integer.class));
    verify(cbacDetails).setCbacPhysicalDisabilitySuffering(eq("Cbac Physical Disability Suffering"));
    verify(cbacDetails).setCbacRecurrentNumbness(eq("Cbac Recurrent Numbness"));
    verify(cbacDetails).setCbacRecurrentTingling(eq("Cbac Recurrent Tingling"));
    verify(cbacDetails).setCbacRednessPain(eq("Cbac Redness Pain"));
    verify(cbacDetails).setCbacShortnessBreath(eq("Cbac Shortness Breath"));
    verify(cbacDetails).setCbacTBHistory(eq("Cbac TBHistory"));
    verify(cbacDetails).setCbacTb(eq("Cbac Tb"));
    verify(cbacDetails).setCbacThickenedskin(eq("Cbac Thickenedskin"));
    verify(cbacDetails).setCbacTonechange(eq("Cbac Tonechange"));
    verify(cbacDetails).setCbacUlceration(eq("Cbac Ulceration"));
    verify(cbacDetails).setCbacVaginalDischarge(eq("Cbac Vaginal Discharge"));
    verify(cbacDetails).setCbacWaistFemale(eq("Cbac Waist Female"));
    verify(cbacDetails).setCbacWaistFemaleScore(isA(Integer.class));
    verify(cbacDetails).setCbacWaistMale(eq("Cbac Waist Male"));
    verify(cbacDetails).setCbacWaistMaleScore(isA(Integer.class));
    verify(cbacDetails).setCbacWeightLoss(eq("Cbac Weight Loss"));
    verify(cbacDetails).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(cbacDetails).setCreatedDate(isA(Timestamp.class));
    verify(cbacDetails).setDeleted(isA(Boolean.class));
    verify(cbacDetails).setId(isA(Long.class));
    verify(cbacDetails).setLastModDate(isA(Timestamp.class));
    verify(cbacDetails).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(cbacDetails).setParkingPlaceId(isA(Integer.class));
    verify(cbacDetails).setProcessed(isA(Character.class));
    verify(cbacDetails).setProviderServiceMapId(isA(Integer.class));
    verify(cbacDetails).setSyncedBy(eq("Synced By"));
    verify(cbacDetails).setSyncedDate(isA(Timestamp.class));
    verify(cbacDetails).setTotalScore(isA(Integer.class));
    verify(cbacDetails).setVanId(isA(Integer.class));
    verify(cbacDetails).setVanSerialNo(isA(Integer.class));
    verify(cbacDetails).setVehicalNo(eq("Vehical No"));
    verify(cbacDetails).setVisitCode(isA(Long.class));
    verify(cbacDetails).setVisitcode(isA(Long.class));
    verify(cbacDetailsRepo).findByBeneficiaryRegIdAndVisitCode(isA(Long.class), isA(Long.class));
  }
}
