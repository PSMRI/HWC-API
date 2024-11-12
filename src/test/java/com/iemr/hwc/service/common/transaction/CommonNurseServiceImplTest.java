package com.iemr.hwc.service.common.transaction;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.anc.BenAdherence;
import com.iemr.hwc.data.anc.BenAllergyHistory;
import com.iemr.hwc.data.anc.BenChildDevelopmentHistory;
import com.iemr.hwc.data.anc.BenFamilyHistory;
import com.iemr.hwc.data.anc.BenMedHistory;
import com.iemr.hwc.data.anc.BenMedicationHistory;
import com.iemr.hwc.data.anc.BenMenstrualDetails;
import com.iemr.hwc.data.anc.BenPersonalHabit;
import com.iemr.hwc.data.anc.BencomrbidityCondDetails;
import com.iemr.hwc.data.anc.ChildFeedingDetails;
import com.iemr.hwc.data.anc.ChildOptionalVaccineDetail;
import com.iemr.hwc.data.anc.ChildVaccineDetail1;
import com.iemr.hwc.data.anc.FemaleObstetricHistory;
import com.iemr.hwc.data.anc.OralExamination;
import com.iemr.hwc.data.anc.PerinatalHistory;
import com.iemr.hwc.data.anc.PhyGeneralExamination;
import com.iemr.hwc.data.anc.PhyHeadToToeExamination;
import com.iemr.hwc.data.anc.SysCardiovascularExamination;
import com.iemr.hwc.data.anc.SysCentralNervousExamination;
import com.iemr.hwc.data.anc.SysGastrointestinalExamination;
import com.iemr.hwc.data.anc.SysGenitourinarySystemExamination;
import com.iemr.hwc.data.anc.SysMusculoskeletalSystemExamination;
import com.iemr.hwc.data.anc.SysRespiratoryExamination;
import com.iemr.hwc.data.anc.WrapperBenInvestigationANC;
import com.iemr.hwc.data.anc.WrapperChildOptionalVaccineDetail;
import com.iemr.hwc.data.anc.WrapperComorbidCondDetails;
import com.iemr.hwc.data.anc.WrapperFemaleObstetricHistory;
import com.iemr.hwc.data.anc.WrapperImmunizationHistory;
import com.iemr.hwc.data.anc.WrapperMedicationHistory;
import com.iemr.hwc.data.doctor.ProviderSpecificRequest;
import com.iemr.hwc.data.location.Districts;
import com.iemr.hwc.data.location.States;
import com.iemr.hwc.data.ncdScreening.IDRSData;
import com.iemr.hwc.data.ncdScreening.PhysicalActivityType;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenCancerVitalDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CDSS;
import com.iemr.hwc.data.provider.ProviderServiceMapping;
import com.iemr.hwc.data.quickConsultation.BenChiefComplaint;
import com.iemr.hwc.data.quickConsultation.LabTestOrderDetail;
import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.data.snomedct.SCTDescription;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.bmiCalculation.BMICalculationRepo;
import com.iemr.hwc.repo.location.ZoneDistrictMapping;
import com.iemr.hwc.repo.nurse.BenAnthropometryRepo;
import com.iemr.hwc.repo.nurse.BenCancerVitalDetailRepo;
import com.iemr.hwc.repo.nurse.BenPhysicalVitalRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.nurse.anc.BenAllergyHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenChildDevelopmentHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenFamilyHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenMedHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenMedicationHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenMenstrualDetailsRepo;
import com.iemr.hwc.repo.nurse.anc.BenPersonalHabitRepo;
import com.iemr.hwc.repo.nurse.anc.BencomrbidityCondRepo;
import com.iemr.hwc.repo.nurse.anc.ChildFeedingDetailsRepo;
import com.iemr.hwc.repo.nurse.anc.ChildOptionalVaccineDetailRepo;
import com.iemr.hwc.repo.nurse.anc.ChildVaccineDetail1Repo;
import com.iemr.hwc.repo.nurse.anc.FemaleObstetricHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.OralExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.PerinatalHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.PhyGeneralExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.PhyHeadToToeExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysCardiovascularExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysCentralNervousExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysGastrointestinalExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysGenitourinarySystemExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysMusculoskeletalSystemExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysRespiratoryExaminationRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.IDRSDataRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.PhysicalActivityTypeRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repo.quickConsultation.LabTestOrderDetailRepo;
import com.iemr.hwc.repo.quickConsultation.PrescribedDrugDetailRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.repo.registrar.RegistrarRepoBenData;
import com.iemr.hwc.repo.registrar.ReistrarRepoBenSearch;
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class CommonNurseServiceImplTest {
  @Mock
  private BMICalculationRepo bMICalculationRepo;

  @Mock
  private BenAdherenceRepo benAdherenceRepo;

  @Mock
  private BenAllergyHistoryRepo benAllergyHistoryRepo;

  @Mock
  private BenAnthropometryRepo benAnthropometryRepo;

  @Mock
  private BenCancerVitalDetailRepo benCancerVitalDetailRepo;

  @Mock
  private BenChiefComplaintRepo benChiefComplaintRepo;

  @Mock
  private BenChildDevelopmentHistoryRepo benChildDevelopmentHistoryRepo;

  @Mock
  private BenFamilyHistoryRepo benFamilyHistoryRepo;

  @Mock
  private BenMedHistoryRepo benMedHistoryRepo;

  @Mock
  private BenMedicationHistoryRepo benMedicationHistoryRepo;

  @Mock
  private BenMenstrualDetailsRepo benMenstrualDetailsRepo;

  @Mock
  private BenPersonalHabitRepo benPersonalHabitRepo;

  @Mock
  private BenPhysicalVitalRepo benPhysicalVitalRepo;

  @Mock
  private BenVisitDetailRepo benVisitDetailRepo;

  @Mock
  private BencomrbidityCondRepo bencomrbidityCondRepo;

  @Mock
  private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

  @Mock
  private CDSSRepo cDSSRepo;

  @Mock
  private ChildFeedingDetailsRepo childFeedingDetailsRepo;

  @Mock
  private ChildOptionalVaccineDetailRepo childOptionalVaccineDetailRepo;

  @Mock
  private ChildVaccineDetail1Repo childVaccineDetail1Repo;

  @Mock
  private CommonDoctorServiceImpl commonDoctorServiceImpl;

  @InjectMocks
  private CommonNurseServiceImpl commonNurseServiceImpl;

  @Mock
  private FemaleObstetricHistoryRepo femaleObstetricHistoryRepo;

  @Mock
  private IDRSDataRepo iDRSDataRepo;

  @Mock
  private LabTestOrderDetailRepo labTestOrderDetailRepo;

  @Mock
  private OralExaminationRepo oralExaminationRepo;

  @Mock
  private PerinatalHistoryRepo perinatalHistoryRepo;

  @Mock
  private PhyGeneralExaminationRepo phyGeneralExaminationRepo;

  @Mock
  private PhyHeadToToeExaminationRepo phyHeadToToeExaminationRepo;

  @Mock
  private PhysicalActivityTypeRepo physicalActivityTypeRepo;

  @Mock
  private PrescribedDrugDetailRepo prescribedDrugDetailRepo;

  @Mock
  private PrescriptionDetailRepo prescriptionDetailRepo;

  @Mock
  private RegistrarRepoBenData registrarRepoBenData;

  @Mock
  private ReistrarRepoBenSearch reistrarRepoBenSearch;

  @Mock
  private SysCardiovascularExaminationRepo sysCardiovascularExaminationRepo;

  @Mock
  private SysCentralNervousExaminationRepo sysCentralNervousExaminationRepo;

  @Mock
  private SysGastrointestinalExaminationRepo sysGastrointestinalExaminationRepo;

  @Mock
  private SysGenitourinarySystemExaminationRepo sysGenitourinarySystemExaminationRepo;

  @Mock
  private SysMusculoskeletalSystemExaminationRepo sysMusculoskeletalSystemExaminationRepo;

  @Mock
  private SysRespiratoryExaminationRepo sysRespiratoryExaminationRepo;

  @Test
  void testSaveBeneficiaryVisitDetails() {
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
    when(benVisitDetailRepo.save(Mockito.<BeneficiaryVisitDetail>any())).thenReturn(beneficiaryVisitDetail);
    when(benVisitDetailRepo.getVisitCountForBeneficiary(Mockito.<Long>any())).thenReturn((short) 1);

    States states2 = new States();
    states2.setCountryID(1);
    states2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    states2.setCreatedDate(mock(Timestamp.class));
    states2.setDeleted(true);
    states2.setLastModDate(mock(Timestamp.class));
    states2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    states2.setStateCode("MD");
    states2.setStateID(1);
    states2.setStateIName("MD");
    states2.setStateName("MD");

    ZoneDistrictMapping zoneDistrictMapping2 = new ZoneDistrictMapping();
    zoneDistrictMapping2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    zoneDistrictMapping2.setCreatedDate(mock(Timestamp.class));
    zoneDistrictMapping2.setDeleted(true);
    zoneDistrictMapping2.setDistrictID(1);
    zoneDistrictMapping2.setDistrictsSet(new HashSet<>());
    zoneDistrictMapping2.setLastModDate(mock(Timestamp.class));
    zoneDistrictMapping2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    zoneDistrictMapping2.setProcessed("Processed");
    zoneDistrictMapping2.setProviderServiceMapID(1);
    zoneDistrictMapping2.setZoneDistrictMapID(1);
    zoneDistrictMapping2.setZoneID(1);

    Districts m_district2 = new Districts();
    m_district2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    m_district2.setCreatedDate(mock(Timestamp.class));
    m_district2.setDeleted(true);
    m_district2.setDistrictID(1);
    m_district2.setDistrictName("District Name");
    m_district2.setGovtLGDDistrictID(1);
    m_district2.setGovtLGDStateID(1);
    m_district2.setLastModDate(mock(Timestamp.class));
    m_district2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    m_district2.setStateID(1);
    m_district2.setStates(states2);
    m_district2.setZoneDistrictMapping(zoneDistrictMapping2);

    States state2 = new States();
    state2.setCountryID(1);
    state2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    state2.setCreatedDate(mock(Timestamp.class));
    state2.setDeleted(true);
    state2.setLastModDate(mock(Timestamp.class));
    state2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    state2.setStateCode("MD");
    state2.setStateID(1);
    state2.setStateIName("MD");
    state2.setStateName("MD");

    ProviderServiceMapping providerServiceMapping2 = new ProviderServiceMapping();
    providerServiceMapping2.setAddress("42 Main St");
    providerServiceMapping2.setCityID(1);
    providerServiceMapping2.setCountryID(1);
    providerServiceMapping2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    providerServiceMapping2.setCreatedDate(mock(Timestamp.class));
    providerServiceMapping2.setDeleted(true);
    providerServiceMapping2.setDistrictBlockID(1);
    providerServiceMapping2.setDistrictID(1);
    providerServiceMapping2.setLastModDate(mock(Timestamp.class));
    providerServiceMapping2.setM_district(m_district2);
    providerServiceMapping2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    providerServiceMapping2.setProviderServiceMapID(1);
    providerServiceMapping2.setServiceID((short) 1);
    providerServiceMapping2.setServiceProviderID(1);
    providerServiceMapping2.setState(state2);
    providerServiceMapping2.setStateID(1);
    providerServiceMapping2.setStatusID(1);

    BeneficiaryVisitDetail beneficiaryVisitDetail2 = new BeneficiaryVisitDetail();
    beneficiaryVisitDetail2.setAction("Action");
    beneficiaryVisitDetail2.setActionId(1);
    beneficiaryVisitDetail2.setActionIdPc(1);
    beneficiaryVisitDetail2.setActionPc("Action Pc");
    beneficiaryVisitDetail2.setAlgorithm("Algorithm");
    beneficiaryVisitDetail2.setAlgorithmPc("Algorithm Pc");
    beneficiaryVisitDetail2.setBenVisitID(1L);
    beneficiaryVisitDetail2.setBeneficiaryRegID(1L);
    beneficiaryVisitDetail2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    beneficiaryVisitDetail2.setCreatedDate(mock(Timestamp.class));
    beneficiaryVisitDetail2.setDeleted(true);
    beneficiaryVisitDetail2.setDiseaseSummary("Disease Summary");
    beneficiaryVisitDetail2.setDiseaseSummaryID(1);
    beneficiaryVisitDetail2.setFileIDs(new Integer[]{1});
    beneficiaryVisitDetail2.setFollowUpForFpMethod(new String[]{"Follow Up For Fp Method"});
    beneficiaryVisitDetail2.setFpMethodFollowup("Fp Method Followup");
    beneficiaryVisitDetail2.setFpSideeffects("Fp Sideeffects");
    beneficiaryVisitDetail2.setHealthFacilityLocation("Health Facility Location");
    beneficiaryVisitDetail2.setHealthFacilityType("Health Facility Type");
    beneficiaryVisitDetail2.setInformationGiven("Information Given");
    beneficiaryVisitDetail2.setLastModDate(mock(Timestamp.class));
    beneficiaryVisitDetail2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    beneficiaryVisitDetail2.setOtherFollowUpForFpMethod("Other Follow Up For Fp Method");
    beneficiaryVisitDetail2.setOtherSideEffects("Other Side Effects");
    beneficiaryVisitDetail2.setParkingPlaceID(1);
    beneficiaryVisitDetail2.setPregnancyStatus("Pregnancy Status");
    beneficiaryVisitDetail2.setPresentChiefComplaint("Present Chief Complaint");
    beneficiaryVisitDetail2.setPresentChiefComplaintID("Present Chief Complaint ID");
    beneficiaryVisitDetail2.setProcessed("Processed");
    beneficiaryVisitDetail2.setProviderServiceMapID(1);
    beneficiaryVisitDetail2.setProviderServiceMapping(providerServiceMapping2);
    beneficiaryVisitDetail2.setRecommendedAction("Recommended Action");
    beneficiaryVisitDetail2.setRecommendedActionPc("Recommended Action Pc");
    beneficiaryVisitDetail2.setRemarks("Remarks");
    beneficiaryVisitDetail2.setRemarksPc("Remarks Pc");
    beneficiaryVisitDetail2.setReportFilePath("/directory/foo.txt");
    beneficiaryVisitDetail2.setReservedForChange("Reserved For Change");
    beneficiaryVisitDetail2.setSelectedDiagnosis("Selected Diagnosis");
    beneficiaryVisitDetail2.setSelectedDiagnosisID("Selected Diagnosis ID");
    beneficiaryVisitDetail2.setServiceProviderName("Service Provider Name");
    beneficiaryVisitDetail2.setSideEffects(new String[]{"Side Effects"});
    beneficiaryVisitDetail2.setSubVisitCategory("Sub Visit Category");
    beneficiaryVisitDetail2.setSyncedBy("Synced By");
    beneficiaryVisitDetail2.setSyncedDate(mock(Timestamp.class));
    beneficiaryVisitDetail2.setVanID(1);
    beneficiaryVisitDetail2.setVanSerialNo(1L);
    beneficiaryVisitDetail2.setVehicalNo("Vehical No");
    beneficiaryVisitDetail2.setVisitCategory("Visit Category");
    beneficiaryVisitDetail2.setVisitCategoryID(1);
    beneficiaryVisitDetail2.setVisitCode(1L);
    beneficiaryVisitDetail2.setVisitDateTime(mock(Timestamp.class));
    beneficiaryVisitDetail2.setVisitFlowStatusFlag("Visit Flow Status Flag");
    beneficiaryVisitDetail2.setVisitNo((short) 1);
    beneficiaryVisitDetail2.setVisitReason("Just cause");
    beneficiaryVisitDetail2.setVisitReasonID((short) 1);
    beneficiaryVisitDetail2.setrCHID("R CHID");

    // Act
    Long actualSaveBeneficiaryVisitDetailsResult = commonNurseServiceImpl
        .saveBeneficiaryVisitDetails(beneficiaryVisitDetail2);

    // Assert
    verify(benVisitDetailRepo).getVisitCountForBeneficiary(isA(Long.class));
    verify(benVisitDetailRepo).save(isA(BeneficiaryVisitDetail.class));
    assertEquals("1,", beneficiaryVisitDetail2.getReportFilePath());
    assertEquals("Follow Up For Fp Method", beneficiaryVisitDetail2.getFpMethodFollowup());
    assertEquals("Side Effects", beneficiaryVisitDetail2.getFpSideeffects());
    assertEquals(1L, actualSaveBeneficiaryVisitDetailsResult.longValue());
    assertEquals((short) 2, beneficiaryVisitDetail2.getVisitNo().shortValue());
  }

  @Test
  void testSaveBeneficiaryVisitDetails2() {
    BeneficiaryVisitDetail beneficiaryVisitDetail = null;
    Integer sessionId = null;

    // Act
    Long actualSaveBeneficiaryVisitDetailsResult = this.commonNurseServiceImpl
        .saveBeneficiaryVisitDetails(beneficiaryVisitDetail, sessionId);

    // Assert
  }

  @Test
  void testGetMaxCurrentdate() throws IEMRException {
    // Arrange
    when(benVisitDetailRepo.getMaxCreatedDate(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(".");

    // Act and Assert
    assertThrows(IEMRException.class,
        () -> commonNurseServiceImpl.getMaxCurrentdate(1L, "Just cause", "Visitcategory"));
    verify(benVisitDetailRepo).getMaxCreatedDate(isA(Long.class), eq("Just cause"), eq("Visitcategory"));
  }

  @Test
  void testGetMaxCurrentdate2() throws IEMRException {
    // Arrange
    when(benVisitDetailRepo.getMaxCreatedDate(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(null);

    // Act
    int actualMaxCurrentdate = commonNurseServiceImpl.getMaxCurrentdate(1L, "Just cause", "Visitcategory");

    // Assert
    verify(benVisitDetailRepo).getMaxCreatedDate(isA(Long.class), eq("Just cause"), eq("Visitcategory"));
    assertEquals(0, actualMaxCurrentdate);
  }

  @Test
  void testGenerateVisitCode() {
    // Arrange
    when(benVisitDetailRepo.updateVisitCode(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(3);

    // Act
    Long actualGenerateVisitCodeResult = commonNurseServiceImpl.generateVisitCode(1L, 1, 1);

    // Assert
    verify(benVisitDetailRepo).updateVisitCode(isA(Long.class), isA(Long.class));
    assertEquals(10000100000001L, actualGenerateVisitCodeResult.longValue());
  }

  @Test
  void testGenerateVisitCode2() {
    // Arrange
    when(benVisitDetailRepo.updateVisitCode(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(0);

    // Act
    Long actualGenerateVisitCodeResult = commonNurseServiceImpl.generateVisitCode(1L, 1, 1);

    // Assert
    verify(benVisitDetailRepo).updateVisitCode(isA(Long.class), isA(Long.class));
    assertEquals(0L, actualGenerateVisitCodeResult.longValue());
  }

  @Test
  void testGenerateVisitCode3() {
    // Arrange
    when(benVisitDetailRepo.updateVisitCode(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(3);

    // Act
    Long actualGenerateVisitCodeResult = commonNurseServiceImpl.generateVisitCode(2L, 1, 1);

    // Assert
    verify(benVisitDetailRepo).updateVisitCode(isA(Long.class), isA(Long.class));
    assertEquals(10000100000002L, actualGenerateVisitCodeResult.longValue());
  }

  @Test
  void testGenerateVisitCode4() {
    // Arrange
    when(benVisitDetailRepo.updateVisitCode(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(3);

    // Act
    Long actualGenerateVisitCodeResult = commonNurseServiceImpl.generateVisitCode(3L, 1, 1);

    // Assert
    verify(benVisitDetailRepo).updateVisitCode(isA(Long.class), isA(Long.class));
    assertEquals(10000100000003L, actualGenerateVisitCodeResult.longValue());
  }

  @Test
  void testGenerateVisitCode5() {
    // Arrange
    when(benVisitDetailRepo.updateVisitCode(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(3);

    // Act
    Long actualGenerateVisitCodeResult = commonNurseServiceImpl.generateVisitCode(4L, 1, 1);

    // Assert
    verify(benVisitDetailRepo).updateVisitCode(isA(Long.class), isA(Long.class));
    assertEquals(10000100000004L, actualGenerateVisitCodeResult.longValue());
  }

  @Test
  void testGetBenVisitCount() {
    // Arrange
    when(benVisitDetailRepo.getVisitCountForBeneficiary(Mockito.<Long>any())).thenReturn((short) 1);

    // Act
    Short actualBenVisitCount = commonNurseServiceImpl.getBenVisitCount(1L);

    // Assert
    verify(benVisitDetailRepo).getVisitCountForBeneficiary(isA(Long.class));
    assertEquals((short) 2, actualBenVisitCount.shortValue());
  }

  @Test
  void testGetBenVisitCount2() {
    // Arrange
    when(benVisitDetailRepo.getVisitCountForBeneficiary(Mockito.<Long>any())).thenReturn(null);

    // Act
    Short actualBenVisitCount = commonNurseServiceImpl.getBenVisitCount(1L);

    // Assert
    verify(benVisitDetailRepo).getVisitCountForBeneficiary(isA(Long.class));
    assertEquals((short) 1, actualBenVisitCount.shortValue());
  }

  @Test
  void testGetBenVisitCount3() {
    // Arrange
    when(benVisitDetailRepo.getVisitCountForBeneficiary(Mockito.<Long>any())).thenReturn((short) -1);

    // Act
    Short actualBenVisitCount = commonNurseServiceImpl.getBenVisitCount(1L);

    // Assert
    verify(benVisitDetailRepo).getVisitCountForBeneficiary(isA(Long.class));
    assertEquals((short) 1, actualBenVisitCount.shortValue());
  }

  @Test
  void testGetCSVisitDetails() {
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
    when(beneficiaryVisitDetail.getFpMethodFollowup()).thenReturn("Fp Method Followup");
    when(beneficiaryVisitDetail.getFpSideeffects()).thenReturn("Fp Sideeffects");
    when(beneficiaryVisitDetail.getReportFilePath()).thenReturn(",");
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
    when(benVisitDetailRepo.getVisitDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(beneficiaryVisitDetail);

    // Act
    commonNurseServiceImpl.getCSVisitDetails(1L, 1L);

    // Assert
    verify(beneficiaryVisitDetail, atLeast(1)).getFpMethodFollowup();
    verify(beneficiaryVisitDetail, atLeast(1)).getFpSideeffects();
    verify(beneficiaryVisitDetail, atLeast(1)).getReportFilePath();
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
    verify(beneficiaryVisitDetail, atLeast(1)).setFileIDs(Mockito.<Integer[]>any());
    verify(beneficiaryVisitDetail, atLeast(1)).setFollowUpForFpMethod(Mockito.<String[]>any());
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
    verify(beneficiaryVisitDetail, atLeast(1)).setSideEffects(Mockito.<String[]>any());
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
    verify(benVisitDetailRepo).getVisitDetails(isA(Long.class), isA(Long.class));
  }

  @Test
  void testGetCSVisitDetails2() {
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
    when(beneficiaryVisitDetail.getFpMethodFollowup()).thenReturn("Fp Method Followup");
    when(beneficiaryVisitDetail.getFpSideeffects()).thenReturn("Fp Sideeffects");
    when(beneficiaryVisitDetail.getReportFilePath()).thenReturn(null);
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
    when(benVisitDetailRepo.getVisitDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(beneficiaryVisitDetail);

    // Act
    commonNurseServiceImpl.getCSVisitDetails(1L, 1L);

    // Assert
    verify(beneficiaryVisitDetail, atLeast(1)).getFpMethodFollowup();
    verify(beneficiaryVisitDetail, atLeast(1)).getFpSideeffects();
    verify(beneficiaryVisitDetail).getReportFilePath();
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
    verify(beneficiaryVisitDetail, atLeast(1)).setFileIDs(Mockito.<Integer[]>any());
    verify(beneficiaryVisitDetail, atLeast(1)).setFollowUpForFpMethod(Mockito.<String[]>any());
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
    verify(beneficiaryVisitDetail, atLeast(1)).setSideEffects(Mockito.<String[]>any());
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
    verify(benVisitDetailRepo).getVisitDetails(isA(Long.class), isA(Long.class));
  }

  @Test
  void testGetCSVisitDetails3() {
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
    when(beneficiaryVisitDetail.getFpMethodFollowup()).thenReturn("Fp Method Followup");
    when(beneficiaryVisitDetail.getFpSideeffects()).thenReturn("Fp Sideeffects");
    when(beneficiaryVisitDetail.getReportFilePath()).thenReturn("42");
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
    when(benVisitDetailRepo.getVisitDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(beneficiaryVisitDetail);

    // Act
    commonNurseServiceImpl.getCSVisitDetails(1L, 1L);

    // Assert
    verify(beneficiaryVisitDetail, atLeast(1)).getFpMethodFollowup();
    verify(beneficiaryVisitDetail, atLeast(1)).getFpSideeffects();
    verify(beneficiaryVisitDetail, atLeast(1)).getReportFilePath();
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
    verify(beneficiaryVisitDetail, atLeast(1)).setFileIDs(Mockito.<Integer[]>any());
    verify(beneficiaryVisitDetail, atLeast(1)).setFollowUpForFpMethod(Mockito.<String[]>any());
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
    verify(beneficiaryVisitDetail, atLeast(1)).setSideEffects(Mockito.<String[]>any());
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
    verify(benVisitDetailRepo).getVisitDetails(isA(Long.class), isA(Long.class));
  }

  @Test
  void testGetCSVisitDetails4() {
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
    when(beneficiaryVisitDetail.getFpMethodFollowup()).thenReturn("Fp Method Followup");
    when(beneficiaryVisitDetail.getFpSideeffects()).thenReturn("Fp Sideeffects");
    when(beneficiaryVisitDetail.getReportFilePath()).thenReturn("");
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
    when(benVisitDetailRepo.getVisitDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(beneficiaryVisitDetail);

    // Act
    commonNurseServiceImpl.getCSVisitDetails(1L, 1L);

    // Assert
    verify(beneficiaryVisitDetail, atLeast(1)).getFpMethodFollowup();
    verify(beneficiaryVisitDetail, atLeast(1)).getFpSideeffects();
    verify(beneficiaryVisitDetail, atLeast(1)).getReportFilePath();
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
    verify(beneficiaryVisitDetail, atLeast(1)).setFileIDs(Mockito.<Integer[]>any());
    verify(beneficiaryVisitDetail, atLeast(1)).setFollowUpForFpMethod(Mockito.<String[]>any());
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
    verify(beneficiaryVisitDetail, atLeast(1)).setSideEffects(Mockito.<String[]>any());
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
    verify(benVisitDetailRepo).getVisitDetails(isA(Long.class), isA(Long.class));
  }

  @Test
  void testGetCdssDetails() {
    // Arrange
    CDSS cdss = new CDSS();
    cdss.setAction("Action");
    cdss.setActionId(1);
    cdss.setActionIdPc(1);
    cdss.setActionPc("Action Pc");
    cdss.setAlgorithm("Algorithm");
    cdss.setAlgorithmPc("Algorithm Pc");
    cdss.setBenVisitID(1L);
    cdss.setBeneficiaryRegID(1L);
    cdss.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    cdss.setCreatedDate(mock(Timestamp.class));
    cdss.setDeleted(true);
    cdss.setDiseaseSummary("Disease Summary");
    cdss.setDiseaseSummaryID(1);
    cdss.setId(1L);
    cdss.setInformationGiven("Information Given");
    cdss.setLastModDate(mock(Timestamp.class));
    cdss.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    cdss.setParkingPlaceID(1);
    cdss.setPresentChiefComplaint("Present Chief Complaint");
    cdss.setPresentChiefComplaintID("Present Chief Complaint ID");
    cdss.setProviderServiceMapID(1);
    cdss.setRecommendedAction("Recommended Action");
    cdss.setRecommendedActionPc("Recommended Action Pc");
    cdss.setRemarks("Remarks");
    cdss.setRemarksPc("Remarks Pc");
    cdss.setReservedForChange("Reserved For Change");
    cdss.setSelectedDiagnosis("Selected Diagnosis");
    cdss.setSelectedDiagnosisID("Selected Diagnosis ID");
    cdss.setSyncedBy("Synced By");
    cdss.setSyncedDate(mock(Timestamp.class));
    cdss.setVanID(1);
    cdss.setVanSerialNo(1L);
    cdss.setVehicalNo("Vehical No");
    cdss.setVisitCode(1L);
    when(cDSSRepo.findByBeneficiaryRegIDAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(cdss);

    // Act
    CDSS actualCdssDetails = commonNurseServiceImpl.getCdssDetails(1L, 1L);

    // Assert
    verify(cDSSRepo).findByBeneficiaryRegIDAndVisitCode(isA(Long.class), isA(Long.class));
    assertSame(cdss, actualCdssDetails);
  }

  @Test
  void testSaveBenChiefComplaints() {
    // Arrange, Act and Assert
    assertEquals(1, commonNurseServiceImpl.saveBenChiefComplaints(new ArrayList<>()));
  }

  @Test
  void testSaveBenChiefComplaints2() {
    // Arrange
    when(benChiefComplaintRepo.saveAll(Mockito.<Iterable<BenChiefComplaint>>any())).thenReturn(new ArrayList<>());

    BenChiefComplaint benChiefComplaint = new BenChiefComplaint();
    benChiefComplaint.setBenChiefComplaintID(1L);
    benChiefComplaint.setBenVisitID(1L);
    benChiefComplaint.setBeneficiaryRegID(1L);
    benChiefComplaint.setChiefComplaint("Chief Complaint");
    benChiefComplaint.setChiefComplaintID(1);
    benChiefComplaint.setConceptID("Concept ID");
    benChiefComplaint.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benChiefComplaint.setCreatedDate(mock(Timestamp.class));
    benChiefComplaint.setDeleted(true);
    benChiefComplaint.setDescription("The characteristics of someone or something");
    benChiefComplaint.setDuration(1);
    benChiefComplaint.setLastModDate(mock(Timestamp.class));
    benChiefComplaint.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benChiefComplaint.setParkingPlaceID(1);
    benChiefComplaint.setProcessed("Processed");
    benChiefComplaint.setProviderServiceMapID(1);
    benChiefComplaint.setReservedForChange("Reserved For Change");
    benChiefComplaint.setSyncedBy("Synced By");
    benChiefComplaint.setSyncedDate(mock(Timestamp.class));
    benChiefComplaint.setUnitOfDuration("Unit Of Duration");
    benChiefComplaint.setVanID(1);
    benChiefComplaint.setVanSerialNo(1L);
    benChiefComplaint.setVehicalNo("Vehical No");
    benChiefComplaint.setVisitCode(1L);

    ArrayList<BenChiefComplaint> benChiefComplaintList = new ArrayList<>();
    benChiefComplaintList.add(benChiefComplaint);

    // Act
    int actualSaveBenChiefComplaintsResult = commonNurseServiceImpl.saveBenChiefComplaints(benChiefComplaintList);

    // Assert
    verify(benChiefComplaintRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenChiefComplaintsResult);
  }

  @Test
  void testSaveBenChiefComplaints3() {
    // Arrange
    BenChiefComplaint benChiefComplaint = new BenChiefComplaint();
    benChiefComplaint.setBenChiefComplaintID(1L);
    benChiefComplaint.setBenVisitID(1L);
    benChiefComplaint.setBeneficiaryRegID(1L);
    benChiefComplaint.setChiefComplaint("Chief Complaint");
    benChiefComplaint.setChiefComplaintID(1);
    benChiefComplaint.setConceptID("Concept ID");
    benChiefComplaint.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benChiefComplaint.setCreatedDate(mock(Timestamp.class));
    benChiefComplaint.setDeleted(true);
    benChiefComplaint.setDescription("The characteristics of someone or something");
    benChiefComplaint.setDuration(1);
    benChiefComplaint.setLastModDate(mock(Timestamp.class));
    benChiefComplaint.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benChiefComplaint.setParkingPlaceID(1);
    benChiefComplaint.setProcessed("Processed");
    benChiefComplaint.setProviderServiceMapID(1);
    benChiefComplaint.setReservedForChange("Reserved For Change");
    benChiefComplaint.setSyncedBy("Synced By");
    benChiefComplaint.setSyncedDate(mock(Timestamp.class));
    benChiefComplaint.setUnitOfDuration("Unit Of Duration");
    benChiefComplaint.setVanID(1);
    benChiefComplaint.setVanSerialNo(1L);
    benChiefComplaint.setVehicalNo("Vehical No");
    benChiefComplaint.setVisitCode(1L);

    ArrayList<BenChiefComplaint> benChiefComplaintList = new ArrayList<>();
    benChiefComplaintList.add(benChiefComplaint);
    when(benChiefComplaintRepo.saveAll(Mockito.<Iterable<BenChiefComplaint>>any())).thenReturn(benChiefComplaintList);

    BenChiefComplaint benChiefComplaint2 = new BenChiefComplaint();
    benChiefComplaint2.setBenChiefComplaintID(1L);
    benChiefComplaint2.setBenVisitID(1L);
    benChiefComplaint2.setBeneficiaryRegID(1L);
    benChiefComplaint2.setChiefComplaint("Chief Complaint");
    benChiefComplaint2.setChiefComplaintID(1);
    benChiefComplaint2.setConceptID("Concept ID");
    benChiefComplaint2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benChiefComplaint2.setCreatedDate(mock(Timestamp.class));
    benChiefComplaint2.setDeleted(true);
    benChiefComplaint2.setDescription("The characteristics of someone or something");
    benChiefComplaint2.setDuration(1);
    benChiefComplaint2.setLastModDate(mock(Timestamp.class));
    benChiefComplaint2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benChiefComplaint2.setParkingPlaceID(1);
    benChiefComplaint2.setProcessed("Processed");
    benChiefComplaint2.setProviderServiceMapID(1);
    benChiefComplaint2.setReservedForChange("Reserved For Change");
    benChiefComplaint2.setSyncedBy("Synced By");
    benChiefComplaint2.setSyncedDate(mock(Timestamp.class));
    benChiefComplaint2.setUnitOfDuration("Unit Of Duration");
    benChiefComplaint2.setVanID(1);
    benChiefComplaint2.setVanSerialNo(1L);
    benChiefComplaint2.setVehicalNo("Vehical No");
    benChiefComplaint2.setVisitCode(1L);

    ArrayList<BenChiefComplaint> benChiefComplaintList2 = new ArrayList<>();
    benChiefComplaintList2.add(benChiefComplaint2);

    // Act
    int actualSaveBenChiefComplaintsResult = commonNurseServiceImpl.saveBenChiefComplaints(benChiefComplaintList2);

    // Assert
    verify(benChiefComplaintRepo).saveAll(isA(Iterable.class));
    assertEquals(1, actualSaveBenChiefComplaintsResult);
  }

  @Test
  void testSaveBenChiefComplaints4() {
    // Arrange
    when(benChiefComplaintRepo.saveAll(Mockito.<Iterable<BenChiefComplaint>>any())).thenReturn(new ArrayList<>());

    BenChiefComplaint benChiefComplaint = new BenChiefComplaint();
    benChiefComplaint.setBenChiefComplaintID(1L);
    benChiefComplaint.setBenVisitID(1L);
    benChiefComplaint.setBeneficiaryRegID(1L);
    benChiefComplaint.setChiefComplaint("Chief Complaint");
    benChiefComplaint.setChiefComplaintID(1);
    benChiefComplaint.setConceptID("Concept ID");
    benChiefComplaint.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benChiefComplaint.setCreatedDate(mock(Timestamp.class));
    benChiefComplaint.setDeleted(true);
    benChiefComplaint.setDescription("The characteristics of someone or something");
    benChiefComplaint.setDuration(1);
    benChiefComplaint.setLastModDate(mock(Timestamp.class));
    benChiefComplaint.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benChiefComplaint.setParkingPlaceID(1);
    benChiefComplaint.setProcessed("Processed");
    benChiefComplaint.setProviderServiceMapID(1);
    benChiefComplaint.setReservedForChange("Reserved For Change");
    benChiefComplaint.setSyncedBy("Synced By");
    benChiefComplaint.setSyncedDate(mock(Timestamp.class));
    benChiefComplaint.setUnitOfDuration("Unit Of Duration");
    benChiefComplaint.setVanID(1);
    benChiefComplaint.setVanSerialNo(1L);
    benChiefComplaint.setVehicalNo("Vehical No");
    benChiefComplaint.setVisitCode(1L);

    BenChiefComplaint benChiefComplaint2 = new BenChiefComplaint();
    benChiefComplaint2.setBenChiefComplaintID(2L);
    benChiefComplaint2.setBenVisitID(2L);
    benChiefComplaint2.setBeneficiaryRegID(2L);
    benChiefComplaint2.setChiefComplaint("benVisitID");
    benChiefComplaint2.setChiefComplaintID(2);
    benChiefComplaint2.setConceptID("benVisitID");
    benChiefComplaint2.setCreatedBy("Created By");
    benChiefComplaint2.setCreatedDate(mock(Timestamp.class));
    benChiefComplaint2.setDeleted(false);
    benChiefComplaint2.setDescription("Description");
    benChiefComplaint2.setDuration(0);
    benChiefComplaint2.setLastModDate(mock(Timestamp.class));
    benChiefComplaint2.setModifiedBy("Modified By");
    benChiefComplaint2.setParkingPlaceID(2);
    benChiefComplaint2.setProcessed("benVisitID");
    benChiefComplaint2.setProviderServiceMapID(2);
    benChiefComplaint2.setReservedForChange("benVisitID");
    benChiefComplaint2.setSyncedBy("benVisitID");
    benChiefComplaint2.setSyncedDate(mock(Timestamp.class));
    benChiefComplaint2.setUnitOfDuration("benVisitID");
    benChiefComplaint2.setVanID(2);
    benChiefComplaint2.setVanSerialNo(0L);
    benChiefComplaint2.setVehicalNo("benVisitID");
    benChiefComplaint2.setVisitCode(0L);

    ArrayList<BenChiefComplaint> benChiefComplaintList = new ArrayList<>();
    benChiefComplaintList.add(benChiefComplaint2);
    benChiefComplaintList.add(benChiefComplaint);

    // Act
    int actualSaveBenChiefComplaintsResult = commonNurseServiceImpl.saveBenChiefComplaints(benChiefComplaintList);

    // Assert
    verify(benChiefComplaintRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenChiefComplaintsResult);
  }

  @Test
  void testSaveBenChiefComplaints5() {
    // Arrange
    when(benChiefComplaintRepo.saveAll(Mockito.<Iterable<BenChiefComplaint>>any())).thenReturn(new ArrayList<>());
    BenChiefComplaint benChiefComplaint = mock(BenChiefComplaint.class);
    when(benChiefComplaint.getChiefComplaintID()).thenReturn(1);
    doNothing().when(benChiefComplaint).setBenChiefComplaintID(Mockito.<Long>any());
    doNothing().when(benChiefComplaint).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benChiefComplaint).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benChiefComplaint).setChiefComplaint(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setChiefComplaintID(Mockito.<Integer>any());
    doNothing().when(benChiefComplaint).setConceptID(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setCreatedBy(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benChiefComplaint).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benChiefComplaint).setDescription(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setDuration(Mockito.<Integer>any());
    doNothing().when(benChiefComplaint).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benChiefComplaint).setModifiedBy(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benChiefComplaint).setProcessed(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benChiefComplaint).setReservedForChange(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setSyncedBy(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benChiefComplaint).setUnitOfDuration(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setVanID(Mockito.<Integer>any());
    doNothing().when(benChiefComplaint).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benChiefComplaint).setVehicalNo(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setVisitCode(Mockito.<Long>any());
    benChiefComplaint.setBenChiefComplaintID(1L);
    benChiefComplaint.setBenVisitID(1L);
    benChiefComplaint.setBeneficiaryRegID(1L);
    benChiefComplaint.setChiefComplaint("Chief Complaint");
    benChiefComplaint.setChiefComplaintID(1);
    benChiefComplaint.setConceptID("Concept ID");
    benChiefComplaint.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benChiefComplaint.setCreatedDate(mock(Timestamp.class));
    benChiefComplaint.setDeleted(true);
    benChiefComplaint.setDescription("The characteristics of someone or something");
    benChiefComplaint.setDuration(1);
    benChiefComplaint.setLastModDate(mock(Timestamp.class));
    benChiefComplaint.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benChiefComplaint.setParkingPlaceID(1);
    benChiefComplaint.setProcessed("Processed");
    benChiefComplaint.setProviderServiceMapID(1);
    benChiefComplaint.setReservedForChange("Reserved For Change");
    benChiefComplaint.setSyncedBy("Synced By");
    benChiefComplaint.setSyncedDate(mock(Timestamp.class));
    benChiefComplaint.setUnitOfDuration("Unit Of Duration");
    benChiefComplaint.setVanID(1);
    benChiefComplaint.setVanSerialNo(1L);
    benChiefComplaint.setVehicalNo("Vehical No");
    benChiefComplaint.setVisitCode(1L);

    ArrayList<BenChiefComplaint> benChiefComplaintList = new ArrayList<>();
    benChiefComplaintList.add(benChiefComplaint);

    // Act
    int actualSaveBenChiefComplaintsResult = commonNurseServiceImpl.saveBenChiefComplaints(benChiefComplaintList);

    // Assert
    verify(benChiefComplaint).getChiefComplaintID();
    verify(benChiefComplaint).setBenChiefComplaintID(isA(Long.class));
    verify(benChiefComplaint).setBenVisitID(isA(Long.class));
    verify(benChiefComplaint).setBeneficiaryRegID(isA(Long.class));
    verify(benChiefComplaint).setChiefComplaint(eq("Chief Complaint"));
    verify(benChiefComplaint).setChiefComplaintID(isA(Integer.class));
    verify(benChiefComplaint).setConceptID(eq("Concept ID"));
    verify(benChiefComplaint).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benChiefComplaint).setCreatedDate(isA(Timestamp.class));
    verify(benChiefComplaint).setDeleted(isA(Boolean.class));
    verify(benChiefComplaint).setDescription(eq("The characteristics of someone or something"));
    verify(benChiefComplaint).setDuration(isA(Integer.class));
    verify(benChiefComplaint).setLastModDate(isA(Timestamp.class));
    verify(benChiefComplaint).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benChiefComplaint).setParkingPlaceID(isA(Integer.class));
    verify(benChiefComplaint).setProcessed(eq("Processed"));
    verify(benChiefComplaint).setProviderServiceMapID(isA(Integer.class));
    verify(benChiefComplaint).setReservedForChange(eq("Reserved For Change"));
    verify(benChiefComplaint).setSyncedBy(eq("Synced By"));
    verify(benChiefComplaint).setSyncedDate(isA(Timestamp.class));
    verify(benChiefComplaint).setUnitOfDuration(eq("Unit Of Duration"));
    verify(benChiefComplaint).setVanID(isA(Integer.class));
    verify(benChiefComplaint).setVanSerialNo(isA(Long.class));
    verify(benChiefComplaint).setVehicalNo(eq("Vehical No"));
    verify(benChiefComplaint).setVisitCode(isA(Long.class));
    verify(benChiefComplaintRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenChiefComplaintsResult);
  }

  @Test
  void testSaveBenChiefComplaints6() {
    // Arrange
    BenChiefComplaint benChiefComplaint = mock(BenChiefComplaint.class);
    when(benChiefComplaint.getChiefComplaintID()).thenReturn(null);
    doNothing().when(benChiefComplaint).setBenChiefComplaintID(Mockito.<Long>any());
    doNothing().when(benChiefComplaint).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benChiefComplaint).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benChiefComplaint).setChiefComplaint(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setChiefComplaintID(Mockito.<Integer>any());
    doNothing().when(benChiefComplaint).setConceptID(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setCreatedBy(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benChiefComplaint).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benChiefComplaint).setDescription(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setDuration(Mockito.<Integer>any());
    doNothing().when(benChiefComplaint).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benChiefComplaint).setModifiedBy(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benChiefComplaint).setProcessed(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benChiefComplaint).setReservedForChange(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setSyncedBy(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benChiefComplaint).setUnitOfDuration(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setVanID(Mockito.<Integer>any());
    doNothing().when(benChiefComplaint).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benChiefComplaint).setVehicalNo(Mockito.<String>any());
    doNothing().when(benChiefComplaint).setVisitCode(Mockito.<Long>any());
    benChiefComplaint.setBenChiefComplaintID(1L);
    benChiefComplaint.setBenVisitID(1L);
    benChiefComplaint.setBeneficiaryRegID(1L);
    benChiefComplaint.setChiefComplaint("Chief Complaint");
    benChiefComplaint.setChiefComplaintID(1);
    benChiefComplaint.setConceptID("Concept ID");
    benChiefComplaint.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benChiefComplaint.setCreatedDate(mock(Timestamp.class));
    benChiefComplaint.setDeleted(true);
    benChiefComplaint.setDescription("The characteristics of someone or something");
    benChiefComplaint.setDuration(1);
    benChiefComplaint.setLastModDate(mock(Timestamp.class));
    benChiefComplaint.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benChiefComplaint.setParkingPlaceID(1);
    benChiefComplaint.setProcessed("Processed");
    benChiefComplaint.setProviderServiceMapID(1);
    benChiefComplaint.setReservedForChange("Reserved For Change");
    benChiefComplaint.setSyncedBy("Synced By");
    benChiefComplaint.setSyncedDate(mock(Timestamp.class));
    benChiefComplaint.setUnitOfDuration("Unit Of Duration");
    benChiefComplaint.setVanID(1);
    benChiefComplaint.setVanSerialNo(1L);
    benChiefComplaint.setVehicalNo("Vehical No");
    benChiefComplaint.setVisitCode(1L);

    ArrayList<BenChiefComplaint> benChiefComplaintList = new ArrayList<>();
    benChiefComplaintList.add(benChiefComplaint);

    // Act
    int actualSaveBenChiefComplaintsResult = commonNurseServiceImpl.saveBenChiefComplaints(benChiefComplaintList);

    // Assert
    verify(benChiefComplaint).getChiefComplaintID();
    verify(benChiefComplaint).setBenChiefComplaintID(isA(Long.class));
    verify(benChiefComplaint).setBenVisitID(isA(Long.class));
    verify(benChiefComplaint).setBeneficiaryRegID(isA(Long.class));
    verify(benChiefComplaint).setChiefComplaint(eq("Chief Complaint"));
    verify(benChiefComplaint).setChiefComplaintID(isA(Integer.class));
    verify(benChiefComplaint).setConceptID(eq("Concept ID"));
    verify(benChiefComplaint).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benChiefComplaint).setCreatedDate(isA(Timestamp.class));
    verify(benChiefComplaint).setDeleted(isA(Boolean.class));
    verify(benChiefComplaint).setDescription(eq("The characteristics of someone or something"));
    verify(benChiefComplaint).setDuration(isA(Integer.class));
    verify(benChiefComplaint).setLastModDate(isA(Timestamp.class));
    verify(benChiefComplaint).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benChiefComplaint).setParkingPlaceID(isA(Integer.class));
    verify(benChiefComplaint).setProcessed(eq("Processed"));
    verify(benChiefComplaint).setProviderServiceMapID(isA(Integer.class));
    verify(benChiefComplaint).setReservedForChange(eq("Reserved For Change"));
    verify(benChiefComplaint).setSyncedBy(eq("Synced By"));
    verify(benChiefComplaint).setSyncedDate(isA(Timestamp.class));
    verify(benChiefComplaint).setUnitOfDuration(eq("Unit Of Duration"));
    verify(benChiefComplaint).setVanID(isA(Integer.class));
    verify(benChiefComplaint).setVanSerialNo(isA(Long.class));
    verify(benChiefComplaint).setVehicalNo(eq("Vehical No"));
    verify(benChiefComplaint).setVisitCode(isA(Long.class));
    assertEquals(1, actualSaveBenChiefComplaintsResult);
  }

  @Test
  void testSaveBenPastHistory() {
    // Arrange
    BenMedHistory benMedHistory = new BenMedHistory();
    benMedHistory.setBenMedHistoryID(1L);
    benMedHistory.setBenVisitID(1L);
    benMedHistory.setBeneficiaryRegID(1L);
    benMedHistory.setCaptureDate(mock(Date.class));
    benMedHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMedHistory.setCreatedDate(mock(Timestamp.class));
    benMedHistory.setDeleted(true);
    benMedHistory.setDrugComplianceID((short) 1);
    benMedHistory.setIllnessType("Illness Type");
    benMedHistory.setIllnessTypeID(1);
    benMedHistory.setIllness_Type("Illness Type");
    benMedHistory.setLastModDate(mock(Timestamp.class));
    benMedHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMedHistory.setOtherIllnessType("Other Illness Type");
    benMedHistory.setOtherSurgeryType("Other Surgery Type");
    benMedHistory.setOther_Illness_Type("Other Illness Type");
    benMedHistory.setOther_Surgery_Type("Other Surgery Type");
    benMedHistory.setParkingPlaceID(1);
    benMedHistory.setPastIllness(new ArrayList<>());
    benMedHistory.setPastSurgery(new ArrayList<>());
    benMedHistory.setProcessed("Processed");
    benMedHistory.setProviderServiceMapID(1);
    benMedHistory.setReservedForChange("Reserved For Change");
    benMedHistory.setSurgeryID(1);
    benMedHistory.setSurgeryType("Surgery Type");
    benMedHistory.setSurgery_Type("Surgery Type");
    benMedHistory.setSyncedBy("Synced By");
    benMedHistory.setSyncedDate(mock(Timestamp.class));
    benMedHistory.setVanID(1);
    benMedHistory.setVanSerialNo(1L);
    benMedHistory.setVehicalNo("Vehical No");
    benMedHistory.setVisitCode(1L);
    benMedHistory.setYear_Of_Illness(mock(Date.class));
    benMedHistory.setYear_Of_Surgery(mock(Date.class));
    benMedHistory.setYearofIllness(mock(Timestamp.class));
    benMedHistory.setYearofSurgery(mock(Timestamp.class));

    // Act and Assert
    assertEquals(1L, commonNurseServiceImpl.saveBenPastHistory(benMedHistory).longValue());
  }

  @Test
  void testSaveBenPastHistory2() {
    // Arrange
    BenMedHistory benMedHistory = mock(BenMedHistory.class);
    when(benMedHistory.getBenPastHistory()).thenReturn(new ArrayList<>());
    doNothing().when(benMedHistory).setBenMedHistoryID(Mockito.<Long>any());
    doNothing().when(benMedHistory).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benMedHistory).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benMedHistory).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benMedHistory).setCreatedBy(Mockito.<String>any());
    doNothing().when(benMedHistory).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benMedHistory).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benMedHistory).setDrugComplianceID(Mockito.<Short>any());
    doNothing().when(benMedHistory).setIllnessType(Mockito.<String>any());
    doNothing().when(benMedHistory).setIllnessTypeID(Mockito.<Integer>any());
    doNothing().when(benMedHistory).setIllness_Type(Mockito.<String>any());
    doNothing().when(benMedHistory).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benMedHistory).setModifiedBy(Mockito.<String>any());
    doNothing().when(benMedHistory).setOtherIllnessType(Mockito.<String>any());
    doNothing().when(benMedHistory).setOtherSurgeryType(Mockito.<String>any());
    doNothing().when(benMedHistory).setOther_Illness_Type(Mockito.<String>any());
    doNothing().when(benMedHistory).setOther_Surgery_Type(Mockito.<String>any());
    doNothing().when(benMedHistory).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benMedHistory).setPastIllness(Mockito.<ArrayList<Map<String, Object>>>any());
    doNothing().when(benMedHistory).setPastSurgery(Mockito.<ArrayList<Map<String, Object>>>any());
    doNothing().when(benMedHistory).setProcessed(Mockito.<String>any());
    doNothing().when(benMedHistory).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benMedHistory).setReservedForChange(Mockito.<String>any());
    doNothing().when(benMedHistory).setSurgeryID(Mockito.<Integer>any());
    doNothing().when(benMedHistory).setSurgeryType(Mockito.<String>any());
    doNothing().when(benMedHistory).setSurgery_Type(Mockito.<String>any());
    doNothing().when(benMedHistory).setSyncedBy(Mockito.<String>any());
    doNothing().when(benMedHistory).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benMedHistory).setVanID(Mockito.<Integer>any());
    doNothing().when(benMedHistory).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benMedHistory).setVehicalNo(Mockito.<String>any());
    doNothing().when(benMedHistory).setVisitCode(Mockito.<Long>any());
    doNothing().when(benMedHistory).setYear_Of_Illness(Mockito.<Date>any());
    doNothing().when(benMedHistory).setYear_Of_Surgery(Mockito.<Date>any());
    doNothing().when(benMedHistory).setYearofIllness(Mockito.<Timestamp>any());
    doNothing().when(benMedHistory).setYearofSurgery(Mockito.<Timestamp>any());
    benMedHistory.setBenMedHistoryID(1L);
    benMedHistory.setBenVisitID(1L);
    benMedHistory.setBeneficiaryRegID(1L);
    benMedHistory.setCaptureDate(mock(Date.class));
    benMedHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMedHistory.setCreatedDate(mock(Timestamp.class));
    benMedHistory.setDeleted(true);
    benMedHistory.setDrugComplianceID((short) 1);
    benMedHistory.setIllnessType("Illness Type");
    benMedHistory.setIllnessTypeID(1);
    benMedHistory.setIllness_Type("Illness Type");
    benMedHistory.setLastModDate(mock(Timestamp.class));
    benMedHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMedHistory.setOtherIllnessType("Other Illness Type");
    benMedHistory.setOtherSurgeryType("Other Surgery Type");
    benMedHistory.setOther_Illness_Type("Other Illness Type");
    benMedHistory.setOther_Surgery_Type("Other Surgery Type");
    benMedHistory.setParkingPlaceID(1);
    benMedHistory.setPastIllness(new ArrayList<>());
    benMedHistory.setPastSurgery(new ArrayList<>());
    benMedHistory.setProcessed("Processed");
    benMedHistory.setProviderServiceMapID(1);
    benMedHistory.setReservedForChange("Reserved For Change");
    benMedHistory.setSurgeryID(1);
    benMedHistory.setSurgeryType("Surgery Type");
    benMedHistory.setSurgery_Type("Surgery Type");
    benMedHistory.setSyncedBy("Synced By");
    benMedHistory.setSyncedDate(mock(Timestamp.class));
    benMedHistory.setVanID(1);
    benMedHistory.setVanSerialNo(1L);
    benMedHistory.setVehicalNo("Vehical No");
    benMedHistory.setVisitCode(1L);
    benMedHistory.setYear_Of_Illness(mock(Date.class));
    benMedHistory.setYear_Of_Surgery(mock(Date.class));
    benMedHistory.setYearofIllness(mock(Timestamp.class));
    benMedHistory.setYearofSurgery(mock(Timestamp.class));

    // Act
    Long actualSaveBenPastHistoryResult = commonNurseServiceImpl.saveBenPastHistory(benMedHistory);

    // Assert
    verify(benMedHistory).getBenPastHistory();
    verify(benMedHistory).setBenMedHistoryID(isA(Long.class));
    verify(benMedHistory).setBenVisitID(isA(Long.class));
    verify(benMedHistory).setBeneficiaryRegID(isA(Long.class));
    verify(benMedHistory).setCaptureDate(isA(Date.class));
    verify(benMedHistory).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benMedHistory).setCreatedDate(isA(Timestamp.class));
    verify(benMedHistory).setDeleted(isA(Boolean.class));
    verify(benMedHistory).setDrugComplianceID(isA(Short.class));
    verify(benMedHistory).setIllnessType(eq("Illness Type"));
    verify(benMedHistory).setIllnessTypeID(isA(Integer.class));
    verify(benMedHistory).setIllness_Type(eq("Illness Type"));
    verify(benMedHistory).setLastModDate(isA(Timestamp.class));
    verify(benMedHistory).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benMedHistory).setOtherIllnessType(eq("Other Illness Type"));
    verify(benMedHistory).setOtherSurgeryType(eq("Other Surgery Type"));
    verify(benMedHistory).setOther_Illness_Type(eq("Other Illness Type"));
    verify(benMedHistory).setOther_Surgery_Type(eq("Other Surgery Type"));
    verify(benMedHistory).setParkingPlaceID(isA(Integer.class));
    verify(benMedHistory).setPastIllness(isA(ArrayList.class));
    verify(benMedHistory).setPastSurgery(isA(ArrayList.class));
    verify(benMedHistory).setProcessed(eq("Processed"));
    verify(benMedHistory).setProviderServiceMapID(isA(Integer.class));
    verify(benMedHistory).setReservedForChange(eq("Reserved For Change"));
    verify(benMedHistory).setSurgeryID(isA(Integer.class));
    verify(benMedHistory).setSurgeryType(eq("Surgery Type"));
    verify(benMedHistory).setSurgery_Type(eq("Surgery Type"));
    verify(benMedHistory).setSyncedBy(eq("Synced By"));
    verify(benMedHistory).setSyncedDate(isA(Timestamp.class));
    verify(benMedHistory).setVanID(isA(Integer.class));
    verify(benMedHistory).setVanSerialNo(isA(Long.class));
    verify(benMedHistory).setVehicalNo(eq("Vehical No"));
    verify(benMedHistory).setVisitCode(isA(Long.class));
    verify(benMedHistory).setYear_Of_Illness(isA(Date.class));
    verify(benMedHistory).setYear_Of_Surgery(isA(Date.class));
    verify(benMedHistory).setYearofIllness(isA(Timestamp.class));
    verify(benMedHistory).setYearofSurgery(isA(Timestamp.class));
    assertEquals(1L, actualSaveBenPastHistoryResult.longValue());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenPastHistory(BenMedHistory)}
   */
  @Test
  void testSaveBenPastHistory3() {
    // Arrange
    when(benMedHistoryRepo.saveAll(Mockito.<Iterable<BenMedHistory>>any())).thenReturn(new ArrayList<>());

    BenMedHistory benMedHistory = new BenMedHistory();
    benMedHistory.setBenMedHistoryID(1L);
    benMedHistory.setBenVisitID(1L);
    benMedHistory.setBeneficiaryRegID(1L);
    benMedHistory.setCaptureDate(mock(Date.class));
    benMedHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMedHistory.setCreatedDate(mock(Timestamp.class));
    benMedHistory.setDeleted(true);
    benMedHistory.setDrugComplianceID((short) 1);
    benMedHistory.setIllnessType("Illness Type");
    benMedHistory.setIllnessTypeID(1);
    benMedHistory.setIllness_Type("Illness Type");
    benMedHistory.setLastModDate(mock(Timestamp.class));
    benMedHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMedHistory.setOtherIllnessType("Other Illness Type");
    benMedHistory.setOtherSurgeryType("Other Surgery Type");
    benMedHistory.setOther_Illness_Type("Other Illness Type");
    benMedHistory.setOther_Surgery_Type("Other Surgery Type");
    benMedHistory.setParkingPlaceID(1);
    benMedHistory.setPastIllness(new ArrayList<>());
    benMedHistory.setPastSurgery(new ArrayList<>());
    benMedHistory.setProcessed("Processed");
    benMedHistory.setProviderServiceMapID(1);
    benMedHistory.setReservedForChange("Reserved For Change");
    benMedHistory.setSurgeryID(1);
    benMedHistory.setSurgeryType("Surgery Type");
    benMedHistory.setSurgery_Type("Surgery Type");
    benMedHistory.setSyncedBy("Synced By");
    benMedHistory.setSyncedDate(mock(Timestamp.class));
    benMedHistory.setVanID(1);
    benMedHistory.setVanSerialNo(1L);
    benMedHistory.setVehicalNo("Vehical No");
    benMedHistory.setVisitCode(1L);
    benMedHistory.setYear_Of_Illness(mock(Date.class));
    benMedHistory.setYear_Of_Surgery(mock(Date.class));
    benMedHistory.setYearofIllness(mock(Timestamp.class));
    benMedHistory.setYearofSurgery(mock(Timestamp.class));

    ArrayList<BenMedHistory> benMedHistoryList = new ArrayList<>();
    benMedHistoryList.add(benMedHistory);
    BenMedHistory benMedHistory2 = mock(BenMedHistory.class);
    when(benMedHistory2.getBenPastHistory()).thenReturn(benMedHistoryList);
    doNothing().when(benMedHistory2).setBenMedHistoryID(Mockito.<Long>any());
    doNothing().when(benMedHistory2).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benMedHistory2).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benMedHistory2).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benMedHistory2).setCreatedBy(Mockito.<String>any());
    doNothing().when(benMedHistory2).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benMedHistory2).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benMedHistory2).setDrugComplianceID(Mockito.<Short>any());
    doNothing().when(benMedHistory2).setIllnessType(Mockito.<String>any());
    doNothing().when(benMedHistory2).setIllnessTypeID(Mockito.<Integer>any());
    doNothing().when(benMedHistory2).setIllness_Type(Mockito.<String>any());
    doNothing().when(benMedHistory2).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benMedHistory2).setModifiedBy(Mockito.<String>any());
    doNothing().when(benMedHistory2).setOtherIllnessType(Mockito.<String>any());
    doNothing().when(benMedHistory2).setOtherSurgeryType(Mockito.<String>any());
    doNothing().when(benMedHistory2).setOther_Illness_Type(Mockito.<String>any());
    doNothing().when(benMedHistory2).setOther_Surgery_Type(Mockito.<String>any());
    doNothing().when(benMedHistory2).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benMedHistory2).setPastIllness(Mockito.<ArrayList<Map<String, Object>>>any());
    doNothing().when(benMedHistory2).setPastSurgery(Mockito.<ArrayList<Map<String, Object>>>any());
    doNothing().when(benMedHistory2).setProcessed(Mockito.<String>any());
    doNothing().when(benMedHistory2).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benMedHistory2).setReservedForChange(Mockito.<String>any());
    doNothing().when(benMedHistory2).setSurgeryID(Mockito.<Integer>any());
    doNothing().when(benMedHistory2).setSurgeryType(Mockito.<String>any());
    doNothing().when(benMedHistory2).setSurgery_Type(Mockito.<String>any());
    doNothing().when(benMedHistory2).setSyncedBy(Mockito.<String>any());
    doNothing().when(benMedHistory2).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benMedHistory2).setVanID(Mockito.<Integer>any());
    doNothing().when(benMedHistory2).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benMedHistory2).setVehicalNo(Mockito.<String>any());
    doNothing().when(benMedHistory2).setVisitCode(Mockito.<Long>any());
    doNothing().when(benMedHistory2).setYear_Of_Illness(Mockito.<Date>any());
    doNothing().when(benMedHistory2).setYear_Of_Surgery(Mockito.<Date>any());
    doNothing().when(benMedHistory2).setYearofIllness(Mockito.<Timestamp>any());
    doNothing().when(benMedHistory2).setYearofSurgery(Mockito.<Timestamp>any());
    benMedHistory2.setBenMedHistoryID(1L);
    benMedHistory2.setBenVisitID(1L);
    benMedHistory2.setBeneficiaryRegID(1L);
    benMedHistory2.setCaptureDate(mock(Date.class));
    benMedHistory2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMedHistory2.setCreatedDate(mock(Timestamp.class));
    benMedHistory2.setDeleted(true);
    benMedHistory2.setDrugComplianceID((short) 1);
    benMedHistory2.setIllnessType("Illness Type");
    benMedHistory2.setIllnessTypeID(1);
    benMedHistory2.setIllness_Type("Illness Type");
    benMedHistory2.setLastModDate(mock(Timestamp.class));
    benMedHistory2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMedHistory2.setOtherIllnessType("Other Illness Type");
    benMedHistory2.setOtherSurgeryType("Other Surgery Type");
    benMedHistory2.setOther_Illness_Type("Other Illness Type");
    benMedHistory2.setOther_Surgery_Type("Other Surgery Type");
    benMedHistory2.setParkingPlaceID(1);
    benMedHistory2.setPastIllness(new ArrayList<>());
    benMedHistory2.setPastSurgery(new ArrayList<>());
    benMedHistory2.setProcessed("Processed");
    benMedHistory2.setProviderServiceMapID(1);
    benMedHistory2.setReservedForChange("Reserved For Change");
    benMedHistory2.setSurgeryID(1);
    benMedHistory2.setSurgeryType("Surgery Type");
    benMedHistory2.setSurgery_Type("Surgery Type");
    benMedHistory2.setSyncedBy("Synced By");
    benMedHistory2.setSyncedDate(mock(Timestamp.class));
    benMedHistory2.setVanID(1);
    benMedHistory2.setVanSerialNo(1L);
    benMedHistory2.setVehicalNo("Vehical No");
    benMedHistory2.setVisitCode(1L);
    benMedHistory2.setYear_Of_Illness(mock(Date.class));
    benMedHistory2.setYear_Of_Surgery(mock(Date.class));
    benMedHistory2.setYearofIllness(mock(Timestamp.class));
    benMedHistory2.setYearofSurgery(mock(Timestamp.class));

    // Act
    Long actualSaveBenPastHistoryResult = commonNurseServiceImpl.saveBenPastHistory(benMedHistory2);

    // Assert
    verify(benMedHistory2).getBenPastHistory();
    verify(benMedHistory2).setBenMedHistoryID(isA(Long.class));
    verify(benMedHistory2).setBenVisitID(isA(Long.class));
    verify(benMedHistory2).setBeneficiaryRegID(isA(Long.class));
    verify(benMedHistory2).setCaptureDate(isA(Date.class));
    verify(benMedHistory2).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benMedHistory2).setCreatedDate(isA(Timestamp.class));
    verify(benMedHistory2).setDeleted(isA(Boolean.class));
    verify(benMedHistory2).setDrugComplianceID(isA(Short.class));
    verify(benMedHistory2).setIllnessType(eq("Illness Type"));
    verify(benMedHistory2).setIllnessTypeID(isA(Integer.class));
    verify(benMedHistory2).setIllness_Type(eq("Illness Type"));
    verify(benMedHistory2).setLastModDate(isA(Timestamp.class));
    verify(benMedHistory2).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benMedHistory2).setOtherIllnessType(eq("Other Illness Type"));
    verify(benMedHistory2).setOtherSurgeryType(eq("Other Surgery Type"));
    verify(benMedHistory2).setOther_Illness_Type(eq("Other Illness Type"));
    verify(benMedHistory2).setOther_Surgery_Type(eq("Other Surgery Type"));
    verify(benMedHistory2).setParkingPlaceID(isA(Integer.class));
    verify(benMedHistory2).setPastIllness(isA(ArrayList.class));
    verify(benMedHistory2).setPastSurgery(isA(ArrayList.class));
    verify(benMedHistory2).setProcessed(eq("Processed"));
    verify(benMedHistory2).setProviderServiceMapID(isA(Integer.class));
    verify(benMedHistory2).setReservedForChange(eq("Reserved For Change"));
    verify(benMedHistory2).setSurgeryID(isA(Integer.class));
    verify(benMedHistory2).setSurgeryType(eq("Surgery Type"));
    verify(benMedHistory2).setSurgery_Type(eq("Surgery Type"));
    verify(benMedHistory2).setSyncedBy(eq("Synced By"));
    verify(benMedHistory2).setSyncedDate(isA(Timestamp.class));
    verify(benMedHistory2).setVanID(isA(Integer.class));
    verify(benMedHistory2).setVanSerialNo(isA(Long.class));
    verify(benMedHistory2).setVehicalNo(eq("Vehical No"));
    verify(benMedHistory2).setVisitCode(isA(Long.class));
    verify(benMedHistory2).setYear_Of_Illness(isA(Date.class));
    verify(benMedHistory2).setYear_Of_Surgery(isA(Date.class));
    verify(benMedHistory2).setYearofIllness(isA(Timestamp.class));
    verify(benMedHistory2).setYearofSurgery(isA(Timestamp.class));
    verify(benMedHistoryRepo).saveAll(isA(Iterable.class));
    assertNull(actualSaveBenPastHistoryResult);
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenPastHistory(BenMedHistory)}
   */
  @Test
  void testSaveBenPastHistory4() {
    // Arrange
    BenMedHistory benMedHistory = new BenMedHistory();
    benMedHistory.setBenMedHistoryID(1L);
    benMedHistory.setBenVisitID(1L);
    benMedHistory.setBeneficiaryRegID(1L);
    benMedHistory.setCaptureDate(mock(Date.class));
    benMedHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMedHistory.setCreatedDate(mock(Timestamp.class));
    benMedHistory.setDeleted(true);
    benMedHistory.setDrugComplianceID((short) 1);
    benMedHistory.setIllnessType("Illness Type");
    benMedHistory.setIllnessTypeID(1);
    benMedHistory.setIllness_Type("Illness Type");
    benMedHistory.setLastModDate(mock(Timestamp.class));
    benMedHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMedHistory.setOtherIllnessType("Other Illness Type");
    benMedHistory.setOtherSurgeryType("Other Surgery Type");
    benMedHistory.setOther_Illness_Type("Other Illness Type");
    benMedHistory.setOther_Surgery_Type("Other Surgery Type");
    benMedHistory.setParkingPlaceID(1);
    benMedHistory.setPastIllness(new ArrayList<>());
    benMedHistory.setPastSurgery(new ArrayList<>());
    benMedHistory.setProcessed("Processed");
    benMedHistory.setProviderServiceMapID(1);
    benMedHistory.setReservedForChange("Reserved For Change");
    benMedHistory.setSurgeryID(1);
    benMedHistory.setSurgeryType("Surgery Type");
    benMedHistory.setSurgery_Type("Surgery Type");
    benMedHistory.setSyncedBy("Synced By");
    benMedHistory.setSyncedDate(mock(Timestamp.class));
    benMedHistory.setVanID(1);
    benMedHistory.setVanSerialNo(1L);
    benMedHistory.setVehicalNo("Vehical No");
    benMedHistory.setVisitCode(1L);
    benMedHistory.setYear_Of_Illness(mock(Date.class));
    benMedHistory.setYear_Of_Surgery(mock(Date.class));
    benMedHistory.setYearofIllness(mock(Timestamp.class));
    benMedHistory.setYearofSurgery(mock(Timestamp.class));

    ArrayList<BenMedHistory> benMedHistoryList = new ArrayList<>();
    benMedHistoryList.add(benMedHistory);
    when(benMedHistoryRepo.saveAll(Mockito.<Iterable<BenMedHistory>>any())).thenReturn(benMedHistoryList);

    BenMedHistory benMedHistory2 = new BenMedHistory();
    benMedHistory2.setBenMedHistoryID(1L);
    benMedHistory2.setBenVisitID(1L);
    benMedHistory2.setBeneficiaryRegID(1L);
    benMedHistory2.setCaptureDate(mock(Date.class));
    benMedHistory2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMedHistory2.setCreatedDate(mock(Timestamp.class));
    benMedHistory2.setDeleted(true);
    benMedHistory2.setDrugComplianceID((short) 1);
    benMedHistory2.setIllnessType("Illness Type");
    benMedHistory2.setIllnessTypeID(1);
    benMedHistory2.setIllness_Type("Illness Type");
    benMedHistory2.setLastModDate(mock(Timestamp.class));
    benMedHistory2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMedHistory2.setOtherIllnessType("Other Illness Type");
    benMedHistory2.setOtherSurgeryType("Other Surgery Type");
    benMedHistory2.setOther_Illness_Type("Other Illness Type");
    benMedHistory2.setOther_Surgery_Type("Other Surgery Type");
    benMedHistory2.setParkingPlaceID(1);
    benMedHistory2.setPastIllness(new ArrayList<>());
    benMedHistory2.setPastSurgery(new ArrayList<>());
    benMedHistory2.setProcessed("Processed");
    benMedHistory2.setProviderServiceMapID(1);
    benMedHistory2.setReservedForChange("Reserved For Change");
    benMedHistory2.setSurgeryID(1);
    benMedHistory2.setSurgeryType("Surgery Type");
    benMedHistory2.setSurgery_Type("Surgery Type");
    benMedHistory2.setSyncedBy("Synced By");
    benMedHistory2.setSyncedDate(mock(Timestamp.class));
    benMedHistory2.setVanID(1);
    benMedHistory2.setVanSerialNo(1L);
    benMedHistory2.setVehicalNo("Vehical No");
    benMedHistory2.setVisitCode(1L);
    benMedHistory2.setYear_Of_Illness(mock(Date.class));
    benMedHistory2.setYear_Of_Surgery(mock(Date.class));
    benMedHistory2.setYearofIllness(mock(Timestamp.class));
    benMedHistory2.setYearofSurgery(mock(Timestamp.class));

    ArrayList<BenMedHistory> benMedHistoryList2 = new ArrayList<>();
    benMedHistoryList2.add(benMedHistory2);
    BenMedHistory benMedHistory3 = mock(BenMedHistory.class);
    when(benMedHistory3.getBenPastHistory()).thenReturn(benMedHistoryList2);
    doNothing().when(benMedHistory3).setBenMedHistoryID(Mockito.<Long>any());
    doNothing().when(benMedHistory3).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benMedHistory3).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benMedHistory3).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benMedHistory3).setCreatedBy(Mockito.<String>any());
    doNothing().when(benMedHistory3).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benMedHistory3).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benMedHistory3).setDrugComplianceID(Mockito.<Short>any());
    doNothing().when(benMedHistory3).setIllnessType(Mockito.<String>any());
    doNothing().when(benMedHistory3).setIllnessTypeID(Mockito.<Integer>any());
    doNothing().when(benMedHistory3).setIllness_Type(Mockito.<String>any());
    doNothing().when(benMedHistory3).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benMedHistory3).setModifiedBy(Mockito.<String>any());
    doNothing().when(benMedHistory3).setOtherIllnessType(Mockito.<String>any());
    doNothing().when(benMedHistory3).setOtherSurgeryType(Mockito.<String>any());
    doNothing().when(benMedHistory3).setOther_Illness_Type(Mockito.<String>any());
    doNothing().when(benMedHistory3).setOther_Surgery_Type(Mockito.<String>any());
    doNothing().when(benMedHistory3).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benMedHistory3).setPastIllness(Mockito.<ArrayList<Map<String, Object>>>any());
    doNothing().when(benMedHistory3).setPastSurgery(Mockito.<ArrayList<Map<String, Object>>>any());
    doNothing().when(benMedHistory3).setProcessed(Mockito.<String>any());
    doNothing().when(benMedHistory3).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benMedHistory3).setReservedForChange(Mockito.<String>any());
    doNothing().when(benMedHistory3).setSurgeryID(Mockito.<Integer>any());
    doNothing().when(benMedHistory3).setSurgeryType(Mockito.<String>any());
    doNothing().when(benMedHistory3).setSurgery_Type(Mockito.<String>any());
    doNothing().when(benMedHistory3).setSyncedBy(Mockito.<String>any());
    doNothing().when(benMedHistory3).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benMedHistory3).setVanID(Mockito.<Integer>any());
    doNothing().when(benMedHistory3).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benMedHistory3).setVehicalNo(Mockito.<String>any());
    doNothing().when(benMedHistory3).setVisitCode(Mockito.<Long>any());
    doNothing().when(benMedHistory3).setYear_Of_Illness(Mockito.<Date>any());
    doNothing().when(benMedHistory3).setYear_Of_Surgery(Mockito.<Date>any());
    doNothing().when(benMedHistory3).setYearofIllness(Mockito.<Timestamp>any());
    doNothing().when(benMedHistory3).setYearofSurgery(Mockito.<Timestamp>any());
    benMedHistory3.setBenMedHistoryID(1L);
    benMedHistory3.setBenVisitID(1L);
    benMedHistory3.setBeneficiaryRegID(1L);
    benMedHistory3.setCaptureDate(mock(Date.class));
    benMedHistory3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMedHistory3.setCreatedDate(mock(Timestamp.class));
    benMedHistory3.setDeleted(true);
    benMedHistory3.setDrugComplianceID((short) 1);
    benMedHistory3.setIllnessType("Illness Type");
    benMedHistory3.setIllnessTypeID(1);
    benMedHistory3.setIllness_Type("Illness Type");
    benMedHistory3.setLastModDate(mock(Timestamp.class));
    benMedHistory3.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMedHistory3.setOtherIllnessType("Other Illness Type");
    benMedHistory3.setOtherSurgeryType("Other Surgery Type");
    benMedHistory3.setOther_Illness_Type("Other Illness Type");
    benMedHistory3.setOther_Surgery_Type("Other Surgery Type");
    benMedHistory3.setParkingPlaceID(1);
    benMedHistory3.setPastIllness(new ArrayList<>());
    benMedHistory3.setPastSurgery(new ArrayList<>());
    benMedHistory3.setProcessed("Processed");
    benMedHistory3.setProviderServiceMapID(1);
    benMedHistory3.setReservedForChange("Reserved For Change");
    benMedHistory3.setSurgeryID(1);
    benMedHistory3.setSurgeryType("Surgery Type");
    benMedHistory3.setSurgery_Type("Surgery Type");
    benMedHistory3.setSyncedBy("Synced By");
    benMedHistory3.setSyncedDate(mock(Timestamp.class));
    benMedHistory3.setVanID(1);
    benMedHistory3.setVanSerialNo(1L);
    benMedHistory3.setVehicalNo("Vehical No");
    benMedHistory3.setVisitCode(1L);
    benMedHistory3.setYear_Of_Illness(mock(Date.class));
    benMedHistory3.setYear_Of_Surgery(mock(Date.class));
    benMedHistory3.setYearofIllness(mock(Timestamp.class));
    benMedHistory3.setYearofSurgery(mock(Timestamp.class));

    // Act
    Long actualSaveBenPastHistoryResult = commonNurseServiceImpl.saveBenPastHistory(benMedHistory3);

    // Assert
    verify(benMedHistory3).getBenPastHistory();
    verify(benMedHistory3).setBenMedHistoryID(isA(Long.class));
    verify(benMedHistory3).setBenVisitID(isA(Long.class));
    verify(benMedHistory3).setBeneficiaryRegID(isA(Long.class));
    verify(benMedHistory3).setCaptureDate(isA(Date.class));
    verify(benMedHistory3).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benMedHistory3).setCreatedDate(isA(Timestamp.class));
    verify(benMedHistory3).setDeleted(isA(Boolean.class));
    verify(benMedHistory3).setDrugComplianceID(isA(Short.class));
    verify(benMedHistory3).setIllnessType(eq("Illness Type"));
    verify(benMedHistory3).setIllnessTypeID(isA(Integer.class));
    verify(benMedHistory3).setIllness_Type(eq("Illness Type"));
    verify(benMedHistory3).setLastModDate(isA(Timestamp.class));
    verify(benMedHistory3).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benMedHistory3).setOtherIllnessType(eq("Other Illness Type"));
    verify(benMedHistory3).setOtherSurgeryType(eq("Other Surgery Type"));
    verify(benMedHistory3).setOther_Illness_Type(eq("Other Illness Type"));
    verify(benMedHistory3).setOther_Surgery_Type(eq("Other Surgery Type"));
    verify(benMedHistory3).setParkingPlaceID(isA(Integer.class));
    verify(benMedHistory3).setPastIllness(isA(ArrayList.class));
    verify(benMedHistory3).setPastSurgery(isA(ArrayList.class));
    verify(benMedHistory3).setProcessed(eq("Processed"));
    verify(benMedHistory3).setProviderServiceMapID(isA(Integer.class));
    verify(benMedHistory3).setReservedForChange(eq("Reserved For Change"));
    verify(benMedHistory3).setSurgeryID(isA(Integer.class));
    verify(benMedHistory3).setSurgeryType(eq("Surgery Type"));
    verify(benMedHistory3).setSurgery_Type(eq("Surgery Type"));
    verify(benMedHistory3).setSyncedBy(eq("Synced By"));
    verify(benMedHistory3).setSyncedDate(isA(Timestamp.class));
    verify(benMedHistory3).setVanID(isA(Integer.class));
    verify(benMedHistory3).setVanSerialNo(isA(Long.class));
    verify(benMedHistory3).setVehicalNo(eq("Vehical No"));
    verify(benMedHistory3).setVisitCode(isA(Long.class));
    verify(benMedHistory3).setYear_Of_Illness(isA(Date.class));
    verify(benMedHistory3).setYear_Of_Surgery(isA(Date.class));
    verify(benMedHistory3).setYearofIllness(isA(Timestamp.class));
    verify(benMedHistory3).setYearofSurgery(isA(Timestamp.class));
    verify(benMedHistoryRepo).saveAll(isA(Iterable.class));
    assertEquals(1L, actualSaveBenPastHistoryResult.longValue());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenComorbidConditions(WrapperComorbidCondDetails)}
   */
  @Test
  void testSaveBenComorbidConditions() {
    // Arrange
    WrapperComorbidCondDetails wrapperComorbidCondDetails = new WrapperComorbidCondDetails();
    wrapperComorbidCondDetails.setBenVisitID(1L);
    wrapperComorbidCondDetails.setBeneficiaryRegID(1L);
    wrapperComorbidCondDetails.setComorbidityConcurrentConditionsList(new ArrayList<>());
    wrapperComorbidCondDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperComorbidCondDetails.setParkingPlaceID(1);
    wrapperComorbidCondDetails.setProviderServiceMapID(1);
    wrapperComorbidCondDetails.setVanID(1);
    wrapperComorbidCondDetails.setVisitCode(1L);

    // Act and Assert
    assertEquals(1L, commonNurseServiceImpl.saveBenComorbidConditions(wrapperComorbidCondDetails).longValue());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenComorbidConditions(WrapperComorbidCondDetails)}
   */
  @Test
  void testSaveBenComorbidConditions2() {
    // Arrange
    WrapperComorbidCondDetails wrapperComorbidCondDetails = mock(WrapperComorbidCondDetails.class);
    when(wrapperComorbidCondDetails.getComrbidityConds()).thenReturn(new ArrayList<>());
    doNothing().when(wrapperComorbidCondDetails).setBenVisitID(Mockito.<Long>any());
    doNothing().when(wrapperComorbidCondDetails).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(wrapperComorbidCondDetails)
        .setComorbidityConcurrentConditionsList(Mockito.<ArrayList<BencomrbidityCondDetails>>any());
    doNothing().when(wrapperComorbidCondDetails).setCreatedBy(Mockito.<String>any());
    doNothing().when(wrapperComorbidCondDetails).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(wrapperComorbidCondDetails).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(wrapperComorbidCondDetails).setVanID(Mockito.<Integer>any());
    doNothing().when(wrapperComorbidCondDetails).setVisitCode(Mockito.<Long>any());
    wrapperComorbidCondDetails.setBenVisitID(1L);
    wrapperComorbidCondDetails.setBeneficiaryRegID(1L);
    wrapperComorbidCondDetails.setComorbidityConcurrentConditionsList(new ArrayList<>());
    wrapperComorbidCondDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperComorbidCondDetails.setParkingPlaceID(1);
    wrapperComorbidCondDetails.setProviderServiceMapID(1);
    wrapperComorbidCondDetails.setVanID(1);
    wrapperComorbidCondDetails.setVisitCode(1L);

    // Act
    Long actualSaveBenComorbidConditionsResult = commonNurseServiceImpl
        .saveBenComorbidConditions(wrapperComorbidCondDetails);

    // Assert
    verify(wrapperComorbidCondDetails).getComrbidityConds();
    verify(wrapperComorbidCondDetails).setBenVisitID(isA(Long.class));
    verify(wrapperComorbidCondDetails).setBeneficiaryRegID(isA(Long.class));
    verify(wrapperComorbidCondDetails).setComorbidityConcurrentConditionsList(isA(ArrayList.class));
    verify(wrapperComorbidCondDetails).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(wrapperComorbidCondDetails).setParkingPlaceID(isA(Integer.class));
    verify(wrapperComorbidCondDetails).setProviderServiceMapID(isA(Integer.class));
    verify(wrapperComorbidCondDetails).setVanID(isA(Integer.class));
    verify(wrapperComorbidCondDetails).setVisitCode(isA(Long.class));
    assertEquals(1L, actualSaveBenComorbidConditionsResult.longValue());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenComorbidConditions(WrapperComorbidCondDetails)}
   */
  @Test
  void testSaveBenComorbidConditions3() {
    // Arrange
    when(bencomrbidityCondRepo.saveAll(Mockito.<Iterable<BencomrbidityCondDetails>>any()))
        .thenReturn(new ArrayList<>());

    BencomrbidityCondDetails bencomrbidityCondDetails = new BencomrbidityCondDetails();
    bencomrbidityCondDetails.setBenVisitID(1L);
    bencomrbidityCondDetails.setBeneficiaryRegID(1L);
    bencomrbidityCondDetails.setCaptureDate(mock(Date.class));
    bencomrbidityCondDetails.setComorbidCondition("Comorbid Condition");
    bencomrbidityCondDetails.setComorbidConditionID((short) 1);
    bencomrbidityCondDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    bencomrbidityCondDetails.setCreatedDate(mock(Timestamp.class));
    bencomrbidityCondDetails.setDate(mock(Date.class));
    bencomrbidityCondDetails.setDeleted(true);
    bencomrbidityCondDetails.setID(1L);
    bencomrbidityCondDetails.setIsForHistory(true);
    bencomrbidityCondDetails.setLastModDate(mock(Timestamp.class));
    bencomrbidityCondDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    bencomrbidityCondDetails.setOtherComorbidCondition("Other Comorbid Condition");
    bencomrbidityCondDetails.setParkingPlaceID(1);
    bencomrbidityCondDetails.setProcessed("Processed");
    bencomrbidityCondDetails.setProviderServiceMapID(1);
    bencomrbidityCondDetails.setReservedForChange("Reserved For Change");
    bencomrbidityCondDetails.setSyncedBy("Synced By");
    bencomrbidityCondDetails.setSyncedDate(mock(Timestamp.class));
    bencomrbidityCondDetails.setTimePeriodAgo(1);
    bencomrbidityCondDetails.setTimePeriodUnit("Time Period Unit");
    bencomrbidityCondDetails.setVanID(1);
    bencomrbidityCondDetails.setVanSerialNo(1L);
    bencomrbidityCondDetails.setVehicalNo("Vehical No");
    bencomrbidityCondDetails.setVisitCode(1L);
    bencomrbidityCondDetails.setYear(mock(Timestamp.class));

    ArrayList<BencomrbidityCondDetails> bencomrbidityCondDetailsList = new ArrayList<>();
    bencomrbidityCondDetailsList.add(bencomrbidityCondDetails);
    WrapperComorbidCondDetails wrapperComorbidCondDetails = mock(WrapperComorbidCondDetails.class);
    when(wrapperComorbidCondDetails.getComrbidityConds()).thenReturn(bencomrbidityCondDetailsList);
    doNothing().when(wrapperComorbidCondDetails).setBenVisitID(Mockito.<Long>any());
    doNothing().when(wrapperComorbidCondDetails).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(wrapperComorbidCondDetails)
        .setComorbidityConcurrentConditionsList(Mockito.<ArrayList<BencomrbidityCondDetails>>any());
    doNothing().when(wrapperComorbidCondDetails).setCreatedBy(Mockito.<String>any());
    doNothing().when(wrapperComorbidCondDetails).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(wrapperComorbidCondDetails).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(wrapperComorbidCondDetails).setVanID(Mockito.<Integer>any());
    doNothing().when(wrapperComorbidCondDetails).setVisitCode(Mockito.<Long>any());
    wrapperComorbidCondDetails.setBenVisitID(1L);
    wrapperComorbidCondDetails.setBeneficiaryRegID(1L);
    wrapperComorbidCondDetails.setComorbidityConcurrentConditionsList(new ArrayList<>());
    wrapperComorbidCondDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperComorbidCondDetails.setParkingPlaceID(1);
    wrapperComorbidCondDetails.setProviderServiceMapID(1);
    wrapperComorbidCondDetails.setVanID(1);
    wrapperComorbidCondDetails.setVisitCode(1L);

    // Act
    Long actualSaveBenComorbidConditionsResult = commonNurseServiceImpl
        .saveBenComorbidConditions(wrapperComorbidCondDetails);

    // Assert
    verify(wrapperComorbidCondDetails).getComrbidityConds();
    verify(wrapperComorbidCondDetails).setBenVisitID(isA(Long.class));
    verify(wrapperComorbidCondDetails).setBeneficiaryRegID(isA(Long.class));
    verify(wrapperComorbidCondDetails).setComorbidityConcurrentConditionsList(isA(ArrayList.class));
    verify(wrapperComorbidCondDetails).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(wrapperComorbidCondDetails).setParkingPlaceID(isA(Integer.class));
    verify(wrapperComorbidCondDetails).setProviderServiceMapID(isA(Integer.class));
    verify(wrapperComorbidCondDetails).setVanID(isA(Integer.class));
    verify(wrapperComorbidCondDetails).setVisitCode(isA(Long.class));
    verify(bencomrbidityCondRepo).saveAll(isA(Iterable.class));
    assertNull(actualSaveBenComorbidConditionsResult);
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenComorbidConditions(WrapperComorbidCondDetails)}
   */
  @Test
  void testSaveBenComorbidConditions4() {
    // Arrange
    BencomrbidityCondDetails bencomrbidityCondDetails = new BencomrbidityCondDetails();
    bencomrbidityCondDetails.setBenVisitID(1L);
    bencomrbidityCondDetails.setBeneficiaryRegID(1L);
    bencomrbidityCondDetails.setCaptureDate(mock(Date.class));
    bencomrbidityCondDetails.setComorbidCondition("Comorbid Condition");
    bencomrbidityCondDetails.setComorbidConditionID((short) 1);
    bencomrbidityCondDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    bencomrbidityCondDetails.setCreatedDate(mock(Timestamp.class));
    bencomrbidityCondDetails.setDate(mock(Date.class));
    bencomrbidityCondDetails.setDeleted(true);
    bencomrbidityCondDetails.setID(1L);
    bencomrbidityCondDetails.setIsForHistory(true);
    bencomrbidityCondDetails.setLastModDate(mock(Timestamp.class));
    bencomrbidityCondDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    bencomrbidityCondDetails.setOtherComorbidCondition("Other Comorbid Condition");
    bencomrbidityCondDetails.setParkingPlaceID(1);
    bencomrbidityCondDetails.setProcessed("Processed");
    bencomrbidityCondDetails.setProviderServiceMapID(1);
    bencomrbidityCondDetails.setReservedForChange("Reserved For Change");
    bencomrbidityCondDetails.setSyncedBy("Synced By");
    bencomrbidityCondDetails.setSyncedDate(mock(Timestamp.class));
    bencomrbidityCondDetails.setTimePeriodAgo(1);
    bencomrbidityCondDetails.setTimePeriodUnit("Time Period Unit");
    bencomrbidityCondDetails.setVanID(1);
    bencomrbidityCondDetails.setVanSerialNo(1L);
    bencomrbidityCondDetails.setVehicalNo("Vehical No");
    bencomrbidityCondDetails.setVisitCode(1L);
    bencomrbidityCondDetails.setYear(mock(Timestamp.class));

    ArrayList<BencomrbidityCondDetails> bencomrbidityCondDetailsList = new ArrayList<>();
    bencomrbidityCondDetailsList.add(bencomrbidityCondDetails);
    when(bencomrbidityCondRepo.saveAll(Mockito.<Iterable<BencomrbidityCondDetails>>any()))
        .thenReturn(bencomrbidityCondDetailsList);

    BencomrbidityCondDetails bencomrbidityCondDetails2 = new BencomrbidityCondDetails();
    bencomrbidityCondDetails2.setBenVisitID(1L);
    bencomrbidityCondDetails2.setBeneficiaryRegID(1L);
    bencomrbidityCondDetails2.setCaptureDate(mock(Date.class));
    bencomrbidityCondDetails2.setComorbidCondition("Comorbid Condition");
    bencomrbidityCondDetails2.setComorbidConditionID((short) 1);
    bencomrbidityCondDetails2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    bencomrbidityCondDetails2.setCreatedDate(mock(Timestamp.class));
    bencomrbidityCondDetails2.setDate(mock(Date.class));
    bencomrbidityCondDetails2.setDeleted(true);
    bencomrbidityCondDetails2.setID(1L);
    bencomrbidityCondDetails2.setIsForHistory(true);
    bencomrbidityCondDetails2.setLastModDate(mock(Timestamp.class));
    bencomrbidityCondDetails2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    bencomrbidityCondDetails2.setOtherComorbidCondition("Other Comorbid Condition");
    bencomrbidityCondDetails2.setParkingPlaceID(1);
    bencomrbidityCondDetails2.setProcessed("Processed");
    bencomrbidityCondDetails2.setProviderServiceMapID(1);
    bencomrbidityCondDetails2.setReservedForChange("Reserved For Change");
    bencomrbidityCondDetails2.setSyncedBy("Synced By");
    bencomrbidityCondDetails2.setSyncedDate(mock(Timestamp.class));
    bencomrbidityCondDetails2.setTimePeriodAgo(1);
    bencomrbidityCondDetails2.setTimePeriodUnit("Time Period Unit");
    bencomrbidityCondDetails2.setVanID(1);
    bencomrbidityCondDetails2.setVanSerialNo(1L);
    bencomrbidityCondDetails2.setVehicalNo("Vehical No");
    bencomrbidityCondDetails2.setVisitCode(1L);
    bencomrbidityCondDetails2.setYear(mock(Timestamp.class));

    ArrayList<BencomrbidityCondDetails> bencomrbidityCondDetailsList2 = new ArrayList<>();
    bencomrbidityCondDetailsList2.add(bencomrbidityCondDetails2);
    WrapperComorbidCondDetails wrapperComorbidCondDetails = mock(WrapperComorbidCondDetails.class);
    when(wrapperComorbidCondDetails.getComrbidityConds()).thenReturn(bencomrbidityCondDetailsList2);
    doNothing().when(wrapperComorbidCondDetails).setBenVisitID(Mockito.<Long>any());
    doNothing().when(wrapperComorbidCondDetails).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(wrapperComorbidCondDetails)
        .setComorbidityConcurrentConditionsList(Mockito.<ArrayList<BencomrbidityCondDetails>>any());
    doNothing().when(wrapperComorbidCondDetails).setCreatedBy(Mockito.<String>any());
    doNothing().when(wrapperComorbidCondDetails).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(wrapperComorbidCondDetails).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(wrapperComorbidCondDetails).setVanID(Mockito.<Integer>any());
    doNothing().when(wrapperComorbidCondDetails).setVisitCode(Mockito.<Long>any());
    wrapperComorbidCondDetails.setBenVisitID(1L);
    wrapperComorbidCondDetails.setBeneficiaryRegID(1L);
    wrapperComorbidCondDetails.setComorbidityConcurrentConditionsList(new ArrayList<>());
    wrapperComorbidCondDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperComorbidCondDetails.setParkingPlaceID(1);
    wrapperComorbidCondDetails.setProviderServiceMapID(1);
    wrapperComorbidCondDetails.setVanID(1);
    wrapperComorbidCondDetails.setVisitCode(1L);

    // Act
    Long actualSaveBenComorbidConditionsResult = commonNurseServiceImpl
        .saveBenComorbidConditions(wrapperComorbidCondDetails);

    // Assert
    verify(wrapperComorbidCondDetails).getComrbidityConds();
    verify(wrapperComorbidCondDetails).setBenVisitID(isA(Long.class));
    verify(wrapperComorbidCondDetails).setBeneficiaryRegID(isA(Long.class));
    verify(wrapperComorbidCondDetails).setComorbidityConcurrentConditionsList(isA(ArrayList.class));
    verify(wrapperComorbidCondDetails).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(wrapperComorbidCondDetails).setParkingPlaceID(isA(Integer.class));
    verify(wrapperComorbidCondDetails).setProviderServiceMapID(isA(Integer.class));
    verify(wrapperComorbidCondDetails).setVanID(isA(Integer.class));
    verify(wrapperComorbidCondDetails).setVisitCode(isA(Long.class));
    verify(bencomrbidityCondRepo).saveAll(isA(Iterable.class));
    assertEquals(1L, actualSaveBenComorbidConditionsResult.longValue());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenMedicationHistory(WrapperMedicationHistory)}
   */
  @Test
  void testSaveBenMedicationHistory() {
    // Arrange
    WrapperMedicationHistory wrapperMedicationHistory = new WrapperMedicationHistory();
    wrapperMedicationHistory.setBenVisitID(1L);
    wrapperMedicationHistory.setBeneficiaryRegID(1L);
    wrapperMedicationHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperMedicationHistory.setMedicationHistoryList(new ArrayList<>());
    wrapperMedicationHistory.setParkingPlaceID(1);
    wrapperMedicationHistory.setProviderServiceMapID(1);
    wrapperMedicationHistory.setVanID(1);
    wrapperMedicationHistory.setVisitCode(1L);

    // Act and Assert
    assertEquals(1L, commonNurseServiceImpl.saveBenMedicationHistory(wrapperMedicationHistory).longValue());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenMedicationHistory(WrapperMedicationHistory)}
   */
  @Test
  void testSaveBenMedicationHistory2() {
    // Arrange
    WrapperMedicationHistory wrapperMedicationHistory = mock(WrapperMedicationHistory.class);
    when(wrapperMedicationHistory.getBenMedicationHistoryDetails()).thenReturn(new ArrayList<>());
    doNothing().when(wrapperMedicationHistory).setBenVisitID(Mockito.<Long>any());
    doNothing().when(wrapperMedicationHistory).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(wrapperMedicationHistory).setCreatedBy(Mockito.<String>any());
    doNothing().when(wrapperMedicationHistory).setMedicationHistoryList(Mockito.<ArrayList<BenMedicationHistory>>any());
    doNothing().when(wrapperMedicationHistory).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(wrapperMedicationHistory).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(wrapperMedicationHistory).setVanID(Mockito.<Integer>any());
    doNothing().when(wrapperMedicationHistory).setVisitCode(Mockito.<Long>any());
    wrapperMedicationHistory.setBenVisitID(1L);
    wrapperMedicationHistory.setBeneficiaryRegID(1L);
    wrapperMedicationHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperMedicationHistory.setMedicationHistoryList(new ArrayList<>());
    wrapperMedicationHistory.setParkingPlaceID(1);
    wrapperMedicationHistory.setProviderServiceMapID(1);
    wrapperMedicationHistory.setVanID(1);
    wrapperMedicationHistory.setVisitCode(1L);

    // Act
    Long actualSaveBenMedicationHistoryResult = commonNurseServiceImpl
        .saveBenMedicationHistory(wrapperMedicationHistory);

    // Assert
    verify(wrapperMedicationHistory).getBenMedicationHistoryDetails();
    verify(wrapperMedicationHistory).setBenVisitID(isA(Long.class));
    verify(wrapperMedicationHistory).setBeneficiaryRegID(isA(Long.class));
    verify(wrapperMedicationHistory).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(wrapperMedicationHistory).setMedicationHistoryList(isA(ArrayList.class));
    verify(wrapperMedicationHistory).setParkingPlaceID(isA(Integer.class));
    verify(wrapperMedicationHistory).setProviderServiceMapID(isA(Integer.class));
    verify(wrapperMedicationHistory).setVanID(isA(Integer.class));
    verify(wrapperMedicationHistory).setVisitCode(isA(Long.class));
    assertEquals(1L, actualSaveBenMedicationHistoryResult.longValue());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenMedicationHistory(WrapperMedicationHistory)}
   */
  @Test
  void testSaveBenMedicationHistory3() {
    // Arrange
    when(benMedicationHistoryRepo.saveAll(Mockito.<Iterable<BenMedicationHistory>>any())).thenReturn(new ArrayList<>());

    BenMedicationHistory benMedicationHistory = new BenMedicationHistory();
    benMedicationHistory.setBenVisitID(1L);
    benMedicationHistory.setBeneficiaryRegID(1L);
    benMedicationHistory.setCaptureDate(mock(Date.class));
    benMedicationHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMedicationHistory.setCreatedDate(mock(Timestamp.class));
    benMedicationHistory.setCurrentMedication("Current Medication");
    benMedicationHistory.setDeleted(true);
    benMedicationHistory.setID(1L);
    benMedicationHistory.setLastModDate(mock(Timestamp.class));
    benMedicationHistory.setMedication_year(mock(Date.class));
    benMedicationHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMedicationHistory.setParkingPlaceID(1);
    benMedicationHistory.setProcessed("Processed");
    benMedicationHistory.setProviderServiceMapID(1);
    benMedicationHistory.setReservedForChange("Reserved For Change");
    benMedicationHistory.setSyncedBy("Synced By");
    benMedicationHistory.setSyncedDate(mock(Timestamp.class));
    benMedicationHistory.setTimePeriodAgo(1);
    benMedicationHistory.setTimePeriodUnit("Time Period Unit");
    benMedicationHistory.setVanID(1);
    benMedicationHistory.setVanSerialNo(1L);
    benMedicationHistory.setVehicalNo("Vehical No");
    benMedicationHistory.setVisitCode(1L);
    benMedicationHistory.setYear(mock(Timestamp.class));

    ArrayList<BenMedicationHistory> benMedicationHistoryList = new ArrayList<>();
    benMedicationHistoryList.add(benMedicationHistory);
    WrapperMedicationHistory wrapperMedicationHistory = mock(WrapperMedicationHistory.class);
    when(wrapperMedicationHistory.getBenMedicationHistoryDetails()).thenReturn(benMedicationHistoryList);
    doNothing().when(wrapperMedicationHistory).setBenVisitID(Mockito.<Long>any());
    doNothing().when(wrapperMedicationHistory).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(wrapperMedicationHistory).setCreatedBy(Mockito.<String>any());
    doNothing().when(wrapperMedicationHistory).setMedicationHistoryList(Mockito.<ArrayList<BenMedicationHistory>>any());
    doNothing().when(wrapperMedicationHistory).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(wrapperMedicationHistory).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(wrapperMedicationHistory).setVanID(Mockito.<Integer>any());
    doNothing().when(wrapperMedicationHistory).setVisitCode(Mockito.<Long>any());
    wrapperMedicationHistory.setBenVisitID(1L);
    wrapperMedicationHistory.setBeneficiaryRegID(1L);
    wrapperMedicationHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperMedicationHistory.setMedicationHistoryList(new ArrayList<>());
    wrapperMedicationHistory.setParkingPlaceID(1);
    wrapperMedicationHistory.setProviderServiceMapID(1);
    wrapperMedicationHistory.setVanID(1);
    wrapperMedicationHistory.setVisitCode(1L);

    // Act
    Long actualSaveBenMedicationHistoryResult = commonNurseServiceImpl
        .saveBenMedicationHistory(wrapperMedicationHistory);

    // Assert
    verify(wrapperMedicationHistory).getBenMedicationHistoryDetails();
    verify(wrapperMedicationHistory).setBenVisitID(isA(Long.class));
    verify(wrapperMedicationHistory).setBeneficiaryRegID(isA(Long.class));
    verify(wrapperMedicationHistory).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(wrapperMedicationHistory).setMedicationHistoryList(isA(ArrayList.class));
    verify(wrapperMedicationHistory).setParkingPlaceID(isA(Integer.class));
    verify(wrapperMedicationHistory).setProviderServiceMapID(isA(Integer.class));
    verify(wrapperMedicationHistory).setVanID(isA(Integer.class));
    verify(wrapperMedicationHistory).setVisitCode(isA(Long.class));
    verify(benMedicationHistoryRepo).saveAll(isA(Iterable.class));
    assertNull(actualSaveBenMedicationHistoryResult);
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenMedicationHistory(WrapperMedicationHistory)}
   */
  @Test
  void testSaveBenMedicationHistory4() {
    // Arrange
    BenMedicationHistory benMedicationHistory = new BenMedicationHistory();
    benMedicationHistory.setBenVisitID(1L);
    benMedicationHistory.setBeneficiaryRegID(1L);
    benMedicationHistory.setCaptureDate(mock(Date.class));
    benMedicationHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMedicationHistory.setCreatedDate(mock(Timestamp.class));
    benMedicationHistory.setCurrentMedication("Current Medication");
    benMedicationHistory.setDeleted(true);
    benMedicationHistory.setID(1L);
    benMedicationHistory.setLastModDate(mock(Timestamp.class));
    benMedicationHistory.setMedication_year(mock(Date.class));
    benMedicationHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMedicationHistory.setParkingPlaceID(1);
    benMedicationHistory.setProcessed("Processed");
    benMedicationHistory.setProviderServiceMapID(1);
    benMedicationHistory.setReservedForChange("Reserved For Change");
    benMedicationHistory.setSyncedBy("Synced By");
    benMedicationHistory.setSyncedDate(mock(Timestamp.class));
    benMedicationHistory.setTimePeriodAgo(1);
    benMedicationHistory.setTimePeriodUnit("Time Period Unit");
    benMedicationHistory.setVanID(1);
    benMedicationHistory.setVanSerialNo(1L);
    benMedicationHistory.setVehicalNo("Vehical No");
    benMedicationHistory.setVisitCode(1L);
    benMedicationHistory.setYear(mock(Timestamp.class));

    ArrayList<BenMedicationHistory> benMedicationHistoryList = new ArrayList<>();
    benMedicationHistoryList.add(benMedicationHistory);
    when(benMedicationHistoryRepo.saveAll(Mockito.<Iterable<BenMedicationHistory>>any()))
        .thenReturn(benMedicationHistoryList);

    BenMedicationHistory benMedicationHistory2 = new BenMedicationHistory();
    benMedicationHistory2.setBenVisitID(1L);
    benMedicationHistory2.setBeneficiaryRegID(1L);
    benMedicationHistory2.setCaptureDate(mock(Date.class));
    benMedicationHistory2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMedicationHistory2.setCreatedDate(mock(Timestamp.class));
    benMedicationHistory2.setCurrentMedication("Current Medication");
    benMedicationHistory2.setDeleted(true);
    benMedicationHistory2.setID(1L);
    benMedicationHistory2.setLastModDate(mock(Timestamp.class));
    benMedicationHistory2.setMedication_year(mock(Date.class));
    benMedicationHistory2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMedicationHistory2.setParkingPlaceID(1);
    benMedicationHistory2.setProcessed("Processed");
    benMedicationHistory2.setProviderServiceMapID(1);
    benMedicationHistory2.setReservedForChange("Reserved For Change");
    benMedicationHistory2.setSyncedBy("Synced By");
    benMedicationHistory2.setSyncedDate(mock(Timestamp.class));
    benMedicationHistory2.setTimePeriodAgo(1);
    benMedicationHistory2.setTimePeriodUnit("Time Period Unit");
    benMedicationHistory2.setVanID(1);
    benMedicationHistory2.setVanSerialNo(1L);
    benMedicationHistory2.setVehicalNo("Vehical No");
    benMedicationHistory2.setVisitCode(1L);
    benMedicationHistory2.setYear(mock(Timestamp.class));

    ArrayList<BenMedicationHistory> benMedicationHistoryList2 = new ArrayList<>();
    benMedicationHistoryList2.add(benMedicationHistory2);
    WrapperMedicationHistory wrapperMedicationHistory = mock(WrapperMedicationHistory.class);
    when(wrapperMedicationHistory.getBenMedicationHistoryDetails()).thenReturn(benMedicationHistoryList2);
    doNothing().when(wrapperMedicationHistory).setBenVisitID(Mockito.<Long>any());
    doNothing().when(wrapperMedicationHistory).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(wrapperMedicationHistory).setCreatedBy(Mockito.<String>any());
    doNothing().when(wrapperMedicationHistory).setMedicationHistoryList(Mockito.<ArrayList<BenMedicationHistory>>any());
    doNothing().when(wrapperMedicationHistory).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(wrapperMedicationHistory).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(wrapperMedicationHistory).setVanID(Mockito.<Integer>any());
    doNothing().when(wrapperMedicationHistory).setVisitCode(Mockito.<Long>any());
    wrapperMedicationHistory.setBenVisitID(1L);
    wrapperMedicationHistory.setBeneficiaryRegID(1L);
    wrapperMedicationHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperMedicationHistory.setMedicationHistoryList(new ArrayList<>());
    wrapperMedicationHistory.setParkingPlaceID(1);
    wrapperMedicationHistory.setProviderServiceMapID(1);
    wrapperMedicationHistory.setVanID(1);
    wrapperMedicationHistory.setVisitCode(1L);

    // Act
    Long actualSaveBenMedicationHistoryResult = commonNurseServiceImpl
        .saveBenMedicationHistory(wrapperMedicationHistory);

    // Assert
    verify(wrapperMedicationHistory).getBenMedicationHistoryDetails();
    verify(wrapperMedicationHistory).setBenVisitID(isA(Long.class));
    verify(wrapperMedicationHistory).setBeneficiaryRegID(isA(Long.class));
    verify(wrapperMedicationHistory).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(wrapperMedicationHistory).setMedicationHistoryList(isA(ArrayList.class));
    verify(wrapperMedicationHistory).setParkingPlaceID(isA(Integer.class));
    verify(wrapperMedicationHistory).setProviderServiceMapID(isA(Integer.class));
    verify(wrapperMedicationHistory).setVanID(isA(Integer.class));
    verify(wrapperMedicationHistory).setVisitCode(isA(Long.class));
    verify(benMedicationHistoryRepo).saveAll(isA(Iterable.class));
    assertEquals(1L, actualSaveBenMedicationHistoryResult.longValue());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveFemaleObstetricHistory(WrapperFemaleObstetricHistory)}
   */
  @Test
  void testSaveFemaleObstetricHistory() {
    // Arrange
    when(femaleObstetricHistoryRepo.saveAll(Mockito.<Iterable<FemaleObstetricHistory>>any()))
        .thenReturn(new ArrayList<>());

    WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory = new WrapperFemaleObstetricHistory();
    wrapperFemaleObstetricHistory.setBenVisitID(1L);
    wrapperFemaleObstetricHistory.setBeneficiaryRegID(1L);
    wrapperFemaleObstetricHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperFemaleObstetricHistory.setFemaleObstetricHistoryList(new ArrayList<>());
    wrapperFemaleObstetricHistory.setParkingPlaceID(1);
    wrapperFemaleObstetricHistory.setProviderServiceMapID(1);
    wrapperFemaleObstetricHistory.setTotalNoOfPreg((short) 1);
    wrapperFemaleObstetricHistory.setVanID(1);
    wrapperFemaleObstetricHistory.setVisitCode(1L);

    // Act
    Long actualSaveFemaleObstetricHistoryResult = commonNurseServiceImpl
        .saveFemaleObstetricHistory(wrapperFemaleObstetricHistory);

    // Assert
    verify(femaleObstetricHistoryRepo).saveAll(isA(Iterable.class));
    assertNull(actualSaveFemaleObstetricHistoryResult);
    assertEquals(1, wrapperFemaleObstetricHistory.getFemaleObstetricHistoryList().size());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveFemaleObstetricHistory(WrapperFemaleObstetricHistory)}
   */
  @Test
  void testSaveFemaleObstetricHistory2() {
    // Arrange
    FemaleObstetricHistory femaleObstetricHistory = new FemaleObstetricHistory();
    femaleObstetricHistory.setAbortionType(new HashMap<>());
    femaleObstetricHistory.setAbortionTypeID(1);
    femaleObstetricHistory.setBenVisitID(1L);
    femaleObstetricHistory.setBeneficiaryRegID(1L);
    femaleObstetricHistory.setCaptureDate(mock(Date.class));
    femaleObstetricHistory.setCongenitalAnomalies("Congenital Anomalies");
    femaleObstetricHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    femaleObstetricHistory.setCreatedDate(mock(Timestamp.class));
    femaleObstetricHistory.setDeleted(true);
    femaleObstetricHistory.setDeliveryComplicationID("Delivery Complication ID");
    femaleObstetricHistory.setDeliveryComplicationList(new ArrayList<>());
    femaleObstetricHistory.setDeliveryComplicationType("Delivery Complication Type");
    femaleObstetricHistory.setDeliveryPlace("Delivery Place");
    femaleObstetricHistory.setDeliveryPlaceID((short) 1);
    femaleObstetricHistory.setDeliveryType("Delivery Type");
    femaleObstetricHistory.setDeliveryTypeID((short) 1);
    femaleObstetricHistory.setDurationType("Duration Type");
    femaleObstetricHistory.setLastModDate(mock(Timestamp.class));
    femaleObstetricHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    femaleObstetricHistory.setNewBornComplication("New Born Complication");
    femaleObstetricHistory.setNewBornComplicationID((short) 1);
    femaleObstetricHistory.setObstetricHistoryID(1L);
    femaleObstetricHistory.setOtherDeliveryComplication("Other Delivery Complication");
    femaleObstetricHistory.setOtherDeliveryPlace("Other Delivery Place");
    femaleObstetricHistory.setOtherNewBornComplication("Other New Born Complication");
    femaleObstetricHistory.setOtherPostNatalComplication("Other Post Natal Complication");
    femaleObstetricHistory.setOtherPostpartumCompType("Other Postpartum Comp Type");
    femaleObstetricHistory.setOtherPregComplication("Other Preg Complication");
    femaleObstetricHistory.setParkingPlaceID(1);
    femaleObstetricHistory.setPostAbortionComplication(new ArrayList<>());
    femaleObstetricHistory.setPostAbortionComplication_db("Post Abortion Complication db");
    femaleObstetricHistory.setPostAbortionComplicationsValues("42");
    femaleObstetricHistory.setPostNatalComplication("Post Natal Complication");
    femaleObstetricHistory.setPostNatalComplicationID((short) 1);
    femaleObstetricHistory.setPostpartumComplicationID("Postpartum Complication ID");
    femaleObstetricHistory.setPostpartumComplicationList(new ArrayList<>());
    femaleObstetricHistory.setPostpartumComplicationType("Postpartum Complication Type");
    femaleObstetricHistory.setPregComplicationID("Preg Complication ID");
    femaleObstetricHistory.setPregComplicationList(new ArrayList<>());
    femaleObstetricHistory.setPregComplicationType("Preg Complication Type");
    femaleObstetricHistory.setPregDuration(1);
    femaleObstetricHistory.setPregDurationID((short) 1);
    femaleObstetricHistory.setPregOrder((short) 1);
    femaleObstetricHistory.setPregOutcome("Preg Outcome");
    femaleObstetricHistory.setPregOutcomeID((short) 1);
    femaleObstetricHistory.setProcessed("Processed");
    femaleObstetricHistory.setProviderServiceMapID(1);
    femaleObstetricHistory.setReservedForChange("Reserved For Change");
    femaleObstetricHistory.setServiceFacilityID(1);
    femaleObstetricHistory.setServiceFacilityValue("42");
    femaleObstetricHistory.setSyncedBy("Synced By");
    femaleObstetricHistory.setSyncedDate(mock(Timestamp.class));
    femaleObstetricHistory.setTotalNoOfPreg((short) 1);
    femaleObstetricHistory.setTypeOfAbortionValue("42");
    femaleObstetricHistory.setTypeofFacility(new HashMap<>());
    femaleObstetricHistory.setTypeofFacilityID(1);
    femaleObstetricHistory.setVanID(1);
    femaleObstetricHistory.setVanSerialNo(1L);
    femaleObstetricHistory.setVehicalNo("Vehical No");
    femaleObstetricHistory.setVisitCode(1L);

    ArrayList<FemaleObstetricHistory> femaleObstetricHistoryList = new ArrayList<>();
    femaleObstetricHistoryList.add(femaleObstetricHistory);
    when(femaleObstetricHistoryRepo.saveAll(Mockito.<Iterable<FemaleObstetricHistory>>any()))
        .thenReturn(femaleObstetricHistoryList);

    WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory = new WrapperFemaleObstetricHistory();
    wrapperFemaleObstetricHistory.setBenVisitID(1L);
    wrapperFemaleObstetricHistory.setBeneficiaryRegID(1L);
    wrapperFemaleObstetricHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperFemaleObstetricHistory.setFemaleObstetricHistoryList(new ArrayList<>());
    wrapperFemaleObstetricHistory.setParkingPlaceID(1);
    wrapperFemaleObstetricHistory.setProviderServiceMapID(1);
    wrapperFemaleObstetricHistory.setTotalNoOfPreg((short) 1);
    wrapperFemaleObstetricHistory.setVanID(1);
    wrapperFemaleObstetricHistory.setVisitCode(1L);

    // Act
    Long actualSaveFemaleObstetricHistoryResult = commonNurseServiceImpl
        .saveFemaleObstetricHistory(wrapperFemaleObstetricHistory);

    // Assert
    verify(femaleObstetricHistoryRepo).saveAll(isA(Iterable.class));
    assertEquals(1, wrapperFemaleObstetricHistory.getFemaleObstetricHistoryList().size());
    assertEquals(1L, actualSaveFemaleObstetricHistoryResult.longValue());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveFemaleObstetricHistory(WrapperFemaleObstetricHistory)}
   */
  @Test
  void testSaveFemaleObstetricHistory3() {
    // Arrange
    WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory = mock(WrapperFemaleObstetricHistory.class);
    when(wrapperFemaleObstetricHistory.getFemaleObstetricHistoryDetails()).thenReturn(new ArrayList<>());
    doNothing().when(wrapperFemaleObstetricHistory).setBenVisitID(Mockito.<Long>any());
    doNothing().when(wrapperFemaleObstetricHistory).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(wrapperFemaleObstetricHistory).setCreatedBy(Mockito.<String>any());
    doNothing().when(wrapperFemaleObstetricHistory)
        .setFemaleObstetricHistoryList(Mockito.<ArrayList<FemaleObstetricHistory>>any());
    doNothing().when(wrapperFemaleObstetricHistory).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(wrapperFemaleObstetricHistory).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(wrapperFemaleObstetricHistory).setTotalNoOfPreg(Mockito.<Short>any());
    doNothing().when(wrapperFemaleObstetricHistory).setVanID(Mockito.<Integer>any());
    doNothing().when(wrapperFemaleObstetricHistory).setVisitCode(Mockito.<Long>any());
    wrapperFemaleObstetricHistory.setBenVisitID(1L);
    wrapperFemaleObstetricHistory.setBeneficiaryRegID(1L);
    wrapperFemaleObstetricHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperFemaleObstetricHistory.setFemaleObstetricHistoryList(new ArrayList<>());
    wrapperFemaleObstetricHistory.setParkingPlaceID(1);
    wrapperFemaleObstetricHistory.setProviderServiceMapID(1);
    wrapperFemaleObstetricHistory.setTotalNoOfPreg((short) 1);
    wrapperFemaleObstetricHistory.setVanID(1);
    wrapperFemaleObstetricHistory.setVisitCode(1L);

    // Act
    Long actualSaveFemaleObstetricHistoryResult = commonNurseServiceImpl
        .saveFemaleObstetricHistory(wrapperFemaleObstetricHistory);

    // Assert
    verify(wrapperFemaleObstetricHistory).getFemaleObstetricHistoryDetails();
    verify(wrapperFemaleObstetricHistory).setBenVisitID(isA(Long.class));
    verify(wrapperFemaleObstetricHistory).setBeneficiaryRegID(isA(Long.class));
    verify(wrapperFemaleObstetricHistory).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(wrapperFemaleObstetricHistory).setFemaleObstetricHistoryList(isA(ArrayList.class));
    verify(wrapperFemaleObstetricHistory).setParkingPlaceID(isA(Integer.class));
    verify(wrapperFemaleObstetricHistory).setProviderServiceMapID(isA(Integer.class));
    verify(wrapperFemaleObstetricHistory).setTotalNoOfPreg(isA(Short.class));
    verify(wrapperFemaleObstetricHistory).setVanID(isA(Integer.class));
    verify(wrapperFemaleObstetricHistory).setVisitCode(isA(Long.class));
    assertEquals(1L, actualSaveFemaleObstetricHistoryResult.longValue());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveFemaleObstetricHistory(WrapperFemaleObstetricHistory)}
   */
  @Test
  void testSaveFemaleObstetricHistory4() {
    // Arrange
    when(femaleObstetricHistoryRepo.saveAll(Mockito.<Iterable<FemaleObstetricHistory>>any()))
        .thenReturn(new ArrayList<>());

    FemaleObstetricHistory femaleObstetricHistory = new FemaleObstetricHistory();
    femaleObstetricHistory.setAbortionType(new HashMap<>());
    femaleObstetricHistory.setAbortionTypeID(1);
    femaleObstetricHistory.setBenVisitID(1L);
    femaleObstetricHistory.setBeneficiaryRegID(1L);
    femaleObstetricHistory.setCaptureDate(mock(Date.class));
    femaleObstetricHistory.setCongenitalAnomalies("Congenital Anomalies");
    femaleObstetricHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    femaleObstetricHistory.setCreatedDate(mock(Timestamp.class));
    femaleObstetricHistory.setDeleted(true);
    femaleObstetricHistory.setDeliveryComplicationID("Delivery Complication ID");
    femaleObstetricHistory.setDeliveryComplicationList(new ArrayList<>());
    femaleObstetricHistory.setDeliveryComplicationType("Delivery Complication Type");
    femaleObstetricHistory.setDeliveryPlace("Delivery Place");
    femaleObstetricHistory.setDeliveryPlaceID((short) 1);
    femaleObstetricHistory.setDeliveryType("Delivery Type");
    femaleObstetricHistory.setDeliveryTypeID((short) 1);
    femaleObstetricHistory.setDurationType("Duration Type");
    femaleObstetricHistory.setLastModDate(mock(Timestamp.class));
    femaleObstetricHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    femaleObstetricHistory.setNewBornComplication("New Born Complication");
    femaleObstetricHistory.setNewBornComplicationID((short) 1);
    femaleObstetricHistory.setObstetricHistoryID(1L);
    femaleObstetricHistory.setOtherDeliveryComplication("Other Delivery Complication");
    femaleObstetricHistory.setOtherDeliveryPlace("Other Delivery Place");
    femaleObstetricHistory.setOtherNewBornComplication("Other New Born Complication");
    femaleObstetricHistory.setOtherPostNatalComplication("Other Post Natal Complication");
    femaleObstetricHistory.setOtherPostpartumCompType("Other Postpartum Comp Type");
    femaleObstetricHistory.setOtherPregComplication("Other Preg Complication");
    femaleObstetricHistory.setParkingPlaceID(1);
    femaleObstetricHistory.setPostAbortionComplication(new ArrayList<>());
    femaleObstetricHistory.setPostAbortionComplication_db("Post Abortion Complication db");
    femaleObstetricHistory.setPostAbortionComplicationsValues("42");
    femaleObstetricHistory.setPostNatalComplication("Post Natal Complication");
    femaleObstetricHistory.setPostNatalComplicationID((short) 1);
    femaleObstetricHistory.setPostpartumComplicationID("Postpartum Complication ID");
    femaleObstetricHistory.setPostpartumComplicationList(new ArrayList<>());
    femaleObstetricHistory.setPostpartumComplicationType("Postpartum Complication Type");
    femaleObstetricHistory.setPregComplicationID("Preg Complication ID");
    femaleObstetricHistory.setPregComplicationList(new ArrayList<>());
    femaleObstetricHistory.setPregComplicationType("Preg Complication Type");
    femaleObstetricHistory.setPregDuration(1);
    femaleObstetricHistory.setPregDurationID((short) 1);
    femaleObstetricHistory.setPregOrder((short) 1);
    femaleObstetricHistory.setPregOutcome("Preg Outcome");
    femaleObstetricHistory.setPregOutcomeID((short) 1);
    femaleObstetricHistory.setProcessed("Processed");
    femaleObstetricHistory.setProviderServiceMapID(1);
    femaleObstetricHistory.setReservedForChange("Reserved For Change");
    femaleObstetricHistory.setServiceFacilityID(1);
    femaleObstetricHistory.setServiceFacilityValue("42");
    femaleObstetricHistory.setSyncedBy("Synced By");
    femaleObstetricHistory.setSyncedDate(mock(Timestamp.class));
    femaleObstetricHistory.setTotalNoOfPreg((short) 1);
    femaleObstetricHistory.setTypeOfAbortionValue("42");
    femaleObstetricHistory.setTypeofFacility(new HashMap<>());
    femaleObstetricHistory.setTypeofFacilityID(1);
    femaleObstetricHistory.setVanID(1);
    femaleObstetricHistory.setVanSerialNo(1L);
    femaleObstetricHistory.setVehicalNo("Vehical No");
    femaleObstetricHistory.setVisitCode(1L);

    ArrayList<FemaleObstetricHistory> femaleObstetricHistoryList = new ArrayList<>();
    femaleObstetricHistoryList.add(femaleObstetricHistory);
    WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory = mock(WrapperFemaleObstetricHistory.class);
    when(wrapperFemaleObstetricHistory.getFemaleObstetricHistoryDetails()).thenReturn(femaleObstetricHistoryList);
    doNothing().when(wrapperFemaleObstetricHistory).setBenVisitID(Mockito.<Long>any());
    doNothing().when(wrapperFemaleObstetricHistory).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(wrapperFemaleObstetricHistory).setCreatedBy(Mockito.<String>any());
    doNothing().when(wrapperFemaleObstetricHistory)
        .setFemaleObstetricHistoryList(Mockito.<ArrayList<FemaleObstetricHistory>>any());
    doNothing().when(wrapperFemaleObstetricHistory).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(wrapperFemaleObstetricHistory).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(wrapperFemaleObstetricHistory).setTotalNoOfPreg(Mockito.<Short>any());
    doNothing().when(wrapperFemaleObstetricHistory).setVanID(Mockito.<Integer>any());
    doNothing().when(wrapperFemaleObstetricHistory).setVisitCode(Mockito.<Long>any());
    wrapperFemaleObstetricHistory.setBenVisitID(1L);
    wrapperFemaleObstetricHistory.setBeneficiaryRegID(1L);
    wrapperFemaleObstetricHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperFemaleObstetricHistory.setFemaleObstetricHistoryList(new ArrayList<>());
    wrapperFemaleObstetricHistory.setParkingPlaceID(1);
    wrapperFemaleObstetricHistory.setProviderServiceMapID(1);
    wrapperFemaleObstetricHistory.setTotalNoOfPreg((short) 1);
    wrapperFemaleObstetricHistory.setVanID(1);
    wrapperFemaleObstetricHistory.setVisitCode(1L);

    // Act
    Long actualSaveFemaleObstetricHistoryResult = commonNurseServiceImpl
        .saveFemaleObstetricHistory(wrapperFemaleObstetricHistory);

    // Assert
    verify(wrapperFemaleObstetricHistory).getFemaleObstetricHistoryDetails();
    verify(wrapperFemaleObstetricHistory).setBenVisitID(isA(Long.class));
    verify(wrapperFemaleObstetricHistory).setBeneficiaryRegID(isA(Long.class));
    verify(wrapperFemaleObstetricHistory).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(wrapperFemaleObstetricHistory).setFemaleObstetricHistoryList(isA(ArrayList.class));
    verify(wrapperFemaleObstetricHistory).setParkingPlaceID(isA(Integer.class));
    verify(wrapperFemaleObstetricHistory).setProviderServiceMapID(isA(Integer.class));
    verify(wrapperFemaleObstetricHistory).setTotalNoOfPreg(isA(Short.class));
    verify(wrapperFemaleObstetricHistory).setVanID(isA(Integer.class));
    verify(wrapperFemaleObstetricHistory).setVisitCode(isA(Long.class));
    verify(femaleObstetricHistoryRepo).saveAll(isA(Iterable.class));
    assertNull(actualSaveFemaleObstetricHistoryResult);
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveFemaleObstetricHistory(WrapperFemaleObstetricHistory)}
   */
  @Test
  void testSaveFemaleObstetricHistory5() {
    // Arrange
    when(femaleObstetricHistoryRepo.saveAll(Mockito.<Iterable<FemaleObstetricHistory>>any()))
        .thenReturn(new ArrayList<>());

    HashMap<String, Object> abortionType = new HashMap<>();
    abortionType.put("complicationID", "42");

    FemaleObstetricHistory femaleObstetricHistory = new FemaleObstetricHistory();
    femaleObstetricHistory.setAbortionType(abortionType);
    femaleObstetricHistory.setAbortionTypeID(1);
    femaleObstetricHistory.setBenVisitID(1L);
    femaleObstetricHistory.setBeneficiaryRegID(1L);
    femaleObstetricHistory.setCaptureDate(mock(Date.class));
    femaleObstetricHistory.setCongenitalAnomalies("Congenital Anomalies");
    femaleObstetricHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    femaleObstetricHistory.setCreatedDate(mock(Timestamp.class));
    femaleObstetricHistory.setDeleted(true);
    femaleObstetricHistory.setDeliveryComplicationID("Delivery Complication ID");
    femaleObstetricHistory.setDeliveryComplicationList(new ArrayList<>());
    femaleObstetricHistory.setDeliveryComplicationType("Delivery Complication Type");
    femaleObstetricHistory.setDeliveryPlace("Delivery Place");
    femaleObstetricHistory.setDeliveryPlaceID((short) 1);
    femaleObstetricHistory.setDeliveryType("Delivery Type");
    femaleObstetricHistory.setDeliveryTypeID((short) 1);
    femaleObstetricHistory.setDurationType("Duration Type");
    femaleObstetricHistory.setLastModDate(mock(Timestamp.class));
    femaleObstetricHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    femaleObstetricHistory.setNewBornComplication("New Born Complication");
    femaleObstetricHistory.setNewBornComplicationID((short) 1);
    femaleObstetricHistory.setObstetricHistoryID(1L);
    femaleObstetricHistory.setOtherDeliveryComplication("Other Delivery Complication");
    femaleObstetricHistory.setOtherDeliveryPlace("Other Delivery Place");
    femaleObstetricHistory.setOtherNewBornComplication("Other New Born Complication");
    femaleObstetricHistory.setOtherPostNatalComplication("Other Post Natal Complication");
    femaleObstetricHistory.setOtherPostpartumCompType("Other Postpartum Comp Type");
    femaleObstetricHistory.setOtherPregComplication("Other Preg Complication");
    femaleObstetricHistory.setParkingPlaceID(1);
    femaleObstetricHistory.setPostAbortionComplication(new ArrayList<>());
    femaleObstetricHistory.setPostAbortionComplication_db("Post Abortion Complication db");
    femaleObstetricHistory.setPostAbortionComplicationsValues("42");
    femaleObstetricHistory.setPostNatalComplication("Post Natal Complication");
    femaleObstetricHistory.setPostNatalComplicationID((short) 1);
    femaleObstetricHistory.setPostpartumComplicationID("Postpartum Complication ID");
    femaleObstetricHistory.setPostpartumComplicationList(new ArrayList<>());
    femaleObstetricHistory.setPostpartumComplicationType("Postpartum Complication Type");
    femaleObstetricHistory.setPregComplicationID("Preg Complication ID");
    femaleObstetricHistory.setPregComplicationList(new ArrayList<>());
    femaleObstetricHistory.setPregComplicationType("Preg Complication Type");
    femaleObstetricHistory.setPregDuration(1);
    femaleObstetricHistory.setPregDurationID((short) 1);
    femaleObstetricHistory.setPregOrder((short) 1);
    femaleObstetricHistory.setPregOutcome("Preg Outcome");
    femaleObstetricHistory.setPregOutcomeID((short) 1);
    femaleObstetricHistory.setProcessed("Processed");
    femaleObstetricHistory.setProviderServiceMapID(1);
    femaleObstetricHistory.setReservedForChange("Reserved For Change");
    femaleObstetricHistory.setServiceFacilityID(1);
    femaleObstetricHistory.setServiceFacilityValue("42");
    femaleObstetricHistory.setSyncedBy("Synced By");
    femaleObstetricHistory.setSyncedDate(mock(Timestamp.class));
    femaleObstetricHistory.setTotalNoOfPreg((short) 1);
    femaleObstetricHistory.setTypeOfAbortionValue("42");
    femaleObstetricHistory.setTypeofFacility(new HashMap<>());
    femaleObstetricHistory.setTypeofFacilityID(1);
    femaleObstetricHistory.setVanID(1);
    femaleObstetricHistory.setVanSerialNo(1L);
    femaleObstetricHistory.setVehicalNo("Vehical No");
    femaleObstetricHistory.setVisitCode(1L);

    ArrayList<FemaleObstetricHistory> femaleObstetricHistoryList = new ArrayList<>();
    femaleObstetricHistoryList.add(femaleObstetricHistory);
    WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory = mock(WrapperFemaleObstetricHistory.class);
    when(wrapperFemaleObstetricHistory.getFemaleObstetricHistoryDetails()).thenReturn(femaleObstetricHistoryList);
    doNothing().when(wrapperFemaleObstetricHistory).setBenVisitID(Mockito.<Long>any());
    doNothing().when(wrapperFemaleObstetricHistory).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(wrapperFemaleObstetricHistory).setCreatedBy(Mockito.<String>any());
    doNothing().when(wrapperFemaleObstetricHistory)
        .setFemaleObstetricHistoryList(Mockito.<ArrayList<FemaleObstetricHistory>>any());
    doNothing().when(wrapperFemaleObstetricHistory).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(wrapperFemaleObstetricHistory).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(wrapperFemaleObstetricHistory).setTotalNoOfPreg(Mockito.<Short>any());
    doNothing().when(wrapperFemaleObstetricHistory).setVanID(Mockito.<Integer>any());
    doNothing().when(wrapperFemaleObstetricHistory).setVisitCode(Mockito.<Long>any());
    wrapperFemaleObstetricHistory.setBenVisitID(1L);
    wrapperFemaleObstetricHistory.setBeneficiaryRegID(1L);
    wrapperFemaleObstetricHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperFemaleObstetricHistory.setFemaleObstetricHistoryList(new ArrayList<>());
    wrapperFemaleObstetricHistory.setParkingPlaceID(1);
    wrapperFemaleObstetricHistory.setProviderServiceMapID(1);
    wrapperFemaleObstetricHistory.setTotalNoOfPreg((short) 1);
    wrapperFemaleObstetricHistory.setVanID(1);
    wrapperFemaleObstetricHistory.setVisitCode(1L);

    // Act
    Long actualSaveFemaleObstetricHistoryResult = commonNurseServiceImpl
        .saveFemaleObstetricHistory(wrapperFemaleObstetricHistory);

    // Assert
    verify(wrapperFemaleObstetricHistory).getFemaleObstetricHistoryDetails();
    verify(wrapperFemaleObstetricHistory).setBenVisitID(isA(Long.class));
    verify(wrapperFemaleObstetricHistory).setBeneficiaryRegID(isA(Long.class));
    verify(wrapperFemaleObstetricHistory).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(wrapperFemaleObstetricHistory).setFemaleObstetricHistoryList(isA(ArrayList.class));
    verify(wrapperFemaleObstetricHistory).setParkingPlaceID(isA(Integer.class));
    verify(wrapperFemaleObstetricHistory).setProviderServiceMapID(isA(Integer.class));
    verify(wrapperFemaleObstetricHistory).setTotalNoOfPreg(isA(Short.class));
    verify(wrapperFemaleObstetricHistory).setVanID(isA(Integer.class));
    verify(wrapperFemaleObstetricHistory).setVisitCode(isA(Long.class));
    verify(femaleObstetricHistoryRepo).saveAll(isA(Iterable.class));
    assertNull(actualSaveFemaleObstetricHistoryResult);
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenMenstrualHistory(BenMenstrualDetails)}
   */
  @Test
  void testSaveBenMenstrualHistory() {
    // Arrange
    BenMenstrualDetails benMenstrualDetails = new BenMenstrualDetails();
    benMenstrualDetails.setBenMenstrualID(1);
    benMenstrualDetails.setBenVisitID(1L);
    benMenstrualDetails.setBeneficiaryRegID(1L);
    benMenstrualDetails.setBloodFlowDuration("Blood Flow Duration");
    benMenstrualDetails.setCaptureDate(mock(Date.class));
    benMenstrualDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMenstrualDetails.setCreatedDate(mock(Timestamp.class));
    benMenstrualDetails.setCycleLength("Cycle Length");
    benMenstrualDetails.setDeleted(true);
    benMenstrualDetails.setLastModDate(mock(Timestamp.class));
    benMenstrualDetails.setLmp_date(mock(Date.class));
    benMenstrualDetails.setMenstrualCycleStatus("Menstrual Cycle Status");
    benMenstrualDetails.setMenstrualCycleStatusID((short) 1);
    benMenstrualDetails.setMenstrualCyclelengthID((short) 1);
    benMenstrualDetails.setMenstrualFlowDurationID((short) 1);
    benMenstrualDetails.setMenstrualProblemID("Menstrual Problem ID");
    benMenstrualDetails.setMenstrualProblemList(new ArrayList<>());
    benMenstrualDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMenstrualDetails.setParkingPlaceID(1);
    benMenstrualDetails.setProblemName("Problem Name");
    benMenstrualDetails.setProcessed("Processed");
    benMenstrualDetails.setProviderServiceMapID(1);
    benMenstrualDetails.setRegularity("Regularity");
    benMenstrualDetails.setReservedForChange("Reserved For Change");
    benMenstrualDetails.setSyncedBy("Synced By");
    benMenstrualDetails.setSyncedDate(mock(Timestamp.class));
    benMenstrualDetails.setVanID(1);
    benMenstrualDetails.setVanSerialNo(1L);
    benMenstrualDetails.setVehicalNo("Vehical No");
    benMenstrualDetails.setVisitCode(1L);
    benMenstrualDetails.setlMPDate(mock(Timestamp.class));
    when(benMenstrualDetailsRepo.save(Mockito.<BenMenstrualDetails>any())).thenReturn(benMenstrualDetails);

    BenMenstrualDetails benMenstrualDetails2 = new BenMenstrualDetails();
    benMenstrualDetails2.setBenMenstrualID(1);
    benMenstrualDetails2.setBenVisitID(1L);
    benMenstrualDetails2.setBeneficiaryRegID(1L);
    benMenstrualDetails2.setBloodFlowDuration("Blood Flow Duration");
    benMenstrualDetails2.setCaptureDate(mock(Date.class));
    benMenstrualDetails2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMenstrualDetails2.setCreatedDate(mock(Timestamp.class));
    benMenstrualDetails2.setCycleLength("Cycle Length");
    benMenstrualDetails2.setDeleted(true);
    benMenstrualDetails2.setLastModDate(mock(Timestamp.class));
    benMenstrualDetails2.setLmp_date(mock(Date.class));
    benMenstrualDetails2.setMenstrualCycleStatus("Menstrual Cycle Status");
    benMenstrualDetails2.setMenstrualCycleStatusID((short) 1);
    benMenstrualDetails2.setMenstrualCyclelengthID((short) 1);
    benMenstrualDetails2.setMenstrualFlowDurationID((short) 1);
    benMenstrualDetails2.setMenstrualProblemID("Menstrual Problem ID");
    benMenstrualDetails2.setMenstrualProblemList(new ArrayList<>());
    benMenstrualDetails2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMenstrualDetails2.setParkingPlaceID(1);
    benMenstrualDetails2.setProblemName("Problem Name");
    benMenstrualDetails2.setProcessed("Processed");
    benMenstrualDetails2.setProviderServiceMapID(1);
    benMenstrualDetails2.setRegularity("Regularity");
    benMenstrualDetails2.setReservedForChange("Reserved For Change");
    benMenstrualDetails2.setSyncedBy("Synced By");
    benMenstrualDetails2.setSyncedDate(mock(Timestamp.class));
    benMenstrualDetails2.setVanID(1);
    benMenstrualDetails2.setVanSerialNo(1L);
    benMenstrualDetails2.setVehicalNo("Vehical No");
    benMenstrualDetails2.setVisitCode(1L);
    benMenstrualDetails2.setlMPDate(mock(Timestamp.class));

    // Act
    Integer actualSaveBenMenstrualHistoryResult = commonNurseServiceImpl.saveBenMenstrualHistory(benMenstrualDetails2);

    // Assert
    verify(benMenstrualDetailsRepo).save(isA(BenMenstrualDetails.class));
    assertEquals(1, actualSaveBenMenstrualHistoryResult.intValue());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenMenstrualHistory(BenMenstrualDetails)}
   */
  @Test
  void testSaveBenMenstrualHistory2() {
    // Arrange
    BenMenstrualDetails benMenstrualDetails = mock(BenMenstrualDetails.class);
    when(benMenstrualDetails.getBenMenstrualID()).thenReturn(1);
    doNothing().when(benMenstrualDetails).setBenMenstrualID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails).setBloodFlowDuration(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benMenstrualDetails).setCreatedBy(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benMenstrualDetails).setCycleLength(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benMenstrualDetails).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benMenstrualDetails).setLmp_date(Mockito.<Date>any());
    doNothing().when(benMenstrualDetails).setMenstrualCycleStatus(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setMenstrualCycleStatusID(Mockito.<Short>any());
    doNothing().when(benMenstrualDetails).setMenstrualCyclelengthID(Mockito.<Short>any());
    doNothing().when(benMenstrualDetails).setMenstrualFlowDurationID(Mockito.<Short>any());
    doNothing().when(benMenstrualDetails).setMenstrualProblemID(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setMenstrualProblemList(Mockito.<ArrayList<Map<String, Object>>>any());
    doNothing().when(benMenstrualDetails).setModifiedBy(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails).setProblemName(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setProcessed(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails).setRegularity(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setReservedForChange(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setSyncedBy(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benMenstrualDetails).setVanID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails).setVehicalNo(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setVisitCode(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails).setlMPDate(Mockito.<Timestamp>any());
    benMenstrualDetails.setBenMenstrualID(1);
    benMenstrualDetails.setBenVisitID(1L);
    benMenstrualDetails.setBeneficiaryRegID(1L);
    benMenstrualDetails.setBloodFlowDuration("Blood Flow Duration");
    benMenstrualDetails.setCaptureDate(mock(Date.class));
    benMenstrualDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMenstrualDetails.setCreatedDate(mock(Timestamp.class));
    benMenstrualDetails.setCycleLength("Cycle Length");
    benMenstrualDetails.setDeleted(true);
    benMenstrualDetails.setLastModDate(mock(Timestamp.class));
    benMenstrualDetails.setLmp_date(mock(Date.class));
    benMenstrualDetails.setMenstrualCycleStatus("Menstrual Cycle Status");
    benMenstrualDetails.setMenstrualCycleStatusID((short) 1);
    benMenstrualDetails.setMenstrualCyclelengthID((short) 1);
    benMenstrualDetails.setMenstrualFlowDurationID((short) 1);
    benMenstrualDetails.setMenstrualProblemID("Menstrual Problem ID");
    benMenstrualDetails.setMenstrualProblemList(new ArrayList<>());
    benMenstrualDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMenstrualDetails.setParkingPlaceID(1);
    benMenstrualDetails.setProblemName("Problem Name");
    benMenstrualDetails.setProcessed("Processed");
    benMenstrualDetails.setProviderServiceMapID(1);
    benMenstrualDetails.setRegularity("Regularity");
    benMenstrualDetails.setReservedForChange("Reserved For Change");
    benMenstrualDetails.setSyncedBy("Synced By");
    benMenstrualDetails.setSyncedDate(mock(Timestamp.class));
    benMenstrualDetails.setVanID(1);
    benMenstrualDetails.setVanSerialNo(1L);
    benMenstrualDetails.setVehicalNo("Vehical No");
    benMenstrualDetails.setVisitCode(1L);
    benMenstrualDetails.setlMPDate(mock(Timestamp.class));
    when(benMenstrualDetailsRepo.save(Mockito.<BenMenstrualDetails>any())).thenReturn(benMenstrualDetails);

    BenMenstrualDetails benMenstrualDetails2 = new BenMenstrualDetails();
    benMenstrualDetails2.setBenMenstrualID(1);
    benMenstrualDetails2.setBenVisitID(1L);
    benMenstrualDetails2.setBeneficiaryRegID(1L);
    benMenstrualDetails2.setBloodFlowDuration("Blood Flow Duration");
    benMenstrualDetails2.setCaptureDate(mock(Date.class));
    benMenstrualDetails2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMenstrualDetails2.setCreatedDate(mock(Timestamp.class));
    benMenstrualDetails2.setCycleLength("Cycle Length");
    benMenstrualDetails2.setDeleted(true);
    benMenstrualDetails2.setLastModDate(mock(Timestamp.class));
    benMenstrualDetails2.setLmp_date(mock(Date.class));
    benMenstrualDetails2.setMenstrualCycleStatus("Menstrual Cycle Status");
    benMenstrualDetails2.setMenstrualCycleStatusID((short) 1);
    benMenstrualDetails2.setMenstrualCyclelengthID((short) 1);
    benMenstrualDetails2.setMenstrualFlowDurationID((short) 1);
    benMenstrualDetails2.setMenstrualProblemID("Menstrual Problem ID");
    benMenstrualDetails2.setMenstrualProblemList(new ArrayList<>());
    benMenstrualDetails2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMenstrualDetails2.setParkingPlaceID(1);
    benMenstrualDetails2.setProblemName("Problem Name");
    benMenstrualDetails2.setProcessed("Processed");
    benMenstrualDetails2.setProviderServiceMapID(1);
    benMenstrualDetails2.setRegularity("Regularity");
    benMenstrualDetails2.setReservedForChange("Reserved For Change");
    benMenstrualDetails2.setSyncedBy("Synced By");
    benMenstrualDetails2.setSyncedDate(mock(Timestamp.class));
    benMenstrualDetails2.setVanID(1);
    benMenstrualDetails2.setVanSerialNo(1L);
    benMenstrualDetails2.setVehicalNo("Vehical No");
    benMenstrualDetails2.setVisitCode(1L);
    benMenstrualDetails2.setlMPDate(mock(Timestamp.class));

    // Act
    Integer actualSaveBenMenstrualHistoryResult = commonNurseServiceImpl.saveBenMenstrualHistory(benMenstrualDetails2);

    // Assert
    verify(benMenstrualDetails, atLeast(1)).getBenMenstrualID();
    verify(benMenstrualDetails).setBenMenstrualID(isA(Integer.class));
    verify(benMenstrualDetails).setBenVisitID(isA(Long.class));
    verify(benMenstrualDetails).setBeneficiaryRegID(isA(Long.class));
    verify(benMenstrualDetails).setBloodFlowDuration(eq("Blood Flow Duration"));
    verify(benMenstrualDetails).setCaptureDate(isA(Date.class));
    verify(benMenstrualDetails).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benMenstrualDetails).setCreatedDate(isA(Timestamp.class));
    verify(benMenstrualDetails).setCycleLength(eq("Cycle Length"));
    verify(benMenstrualDetails).setDeleted(isA(Boolean.class));
    verify(benMenstrualDetails).setLastModDate(isA(Timestamp.class));
    verify(benMenstrualDetails).setLmp_date(isA(Date.class));
    verify(benMenstrualDetails).setMenstrualCycleStatus(eq("Menstrual Cycle Status"));
    verify(benMenstrualDetails).setMenstrualCycleStatusID(isA(Short.class));
    verify(benMenstrualDetails).setMenstrualCyclelengthID(isA(Short.class));
    verify(benMenstrualDetails).setMenstrualFlowDurationID(isA(Short.class));
    verify(benMenstrualDetails).setMenstrualProblemID(eq("Menstrual Problem ID"));
    verify(benMenstrualDetails).setMenstrualProblemList(isA(ArrayList.class));
    verify(benMenstrualDetails).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benMenstrualDetails).setParkingPlaceID(isA(Integer.class));
    verify(benMenstrualDetails).setProblemName(eq("Problem Name"));
    verify(benMenstrualDetails).setProcessed(eq("Processed"));
    verify(benMenstrualDetails).setProviderServiceMapID(isA(Integer.class));
    verify(benMenstrualDetails).setRegularity(eq("Regularity"));
    verify(benMenstrualDetails).setReservedForChange(eq("Reserved For Change"));
    verify(benMenstrualDetails).setSyncedBy(eq("Synced By"));
    verify(benMenstrualDetails).setSyncedDate(isA(Timestamp.class));
    verify(benMenstrualDetails).setVanID(isA(Integer.class));
    verify(benMenstrualDetails).setVanSerialNo(isA(Long.class));
    verify(benMenstrualDetails).setVehicalNo(eq("Vehical No"));
    verify(benMenstrualDetails).setVisitCode(isA(Long.class));
    verify(benMenstrualDetails).setlMPDate(isA(Timestamp.class));
    verify(benMenstrualDetailsRepo).save(isA(BenMenstrualDetails.class));
    assertEquals(1, actualSaveBenMenstrualHistoryResult.intValue());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenMenstrualHistory(BenMenstrualDetails)}
   */
  @Test
  void testSaveBenMenstrualHistory3() {
    // Arrange
    BenMenstrualDetails benMenstrualDetails = mock(BenMenstrualDetails.class);
    when(benMenstrualDetails.getBenMenstrualID()).thenReturn(0);
    doNothing().when(benMenstrualDetails).setBenMenstrualID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails).setBloodFlowDuration(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benMenstrualDetails).setCreatedBy(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benMenstrualDetails).setCycleLength(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benMenstrualDetails).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benMenstrualDetails).setLmp_date(Mockito.<Date>any());
    doNothing().when(benMenstrualDetails).setMenstrualCycleStatus(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setMenstrualCycleStatusID(Mockito.<Short>any());
    doNothing().when(benMenstrualDetails).setMenstrualCyclelengthID(Mockito.<Short>any());
    doNothing().when(benMenstrualDetails).setMenstrualFlowDurationID(Mockito.<Short>any());
    doNothing().when(benMenstrualDetails).setMenstrualProblemID(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setMenstrualProblemList(Mockito.<ArrayList<Map<String, Object>>>any());
    doNothing().when(benMenstrualDetails).setModifiedBy(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails).setProblemName(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setProcessed(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails).setRegularity(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setReservedForChange(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setSyncedBy(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benMenstrualDetails).setVanID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails).setVehicalNo(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setVisitCode(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails).setlMPDate(Mockito.<Timestamp>any());
    benMenstrualDetails.setBenMenstrualID(1);
    benMenstrualDetails.setBenVisitID(1L);
    benMenstrualDetails.setBeneficiaryRegID(1L);
    benMenstrualDetails.setBloodFlowDuration("Blood Flow Duration");
    benMenstrualDetails.setCaptureDate(mock(Date.class));
    benMenstrualDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMenstrualDetails.setCreatedDate(mock(Timestamp.class));
    benMenstrualDetails.setCycleLength("Cycle Length");
    benMenstrualDetails.setDeleted(true);
    benMenstrualDetails.setLastModDate(mock(Timestamp.class));
    benMenstrualDetails.setLmp_date(mock(Date.class));
    benMenstrualDetails.setMenstrualCycleStatus("Menstrual Cycle Status");
    benMenstrualDetails.setMenstrualCycleStatusID((short) 1);
    benMenstrualDetails.setMenstrualCyclelengthID((short) 1);
    benMenstrualDetails.setMenstrualFlowDurationID((short) 1);
    benMenstrualDetails.setMenstrualProblemID("Menstrual Problem ID");
    benMenstrualDetails.setMenstrualProblemList(new ArrayList<>());
    benMenstrualDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMenstrualDetails.setParkingPlaceID(1);
    benMenstrualDetails.setProblemName("Problem Name");
    benMenstrualDetails.setProcessed("Processed");
    benMenstrualDetails.setProviderServiceMapID(1);
    benMenstrualDetails.setRegularity("Regularity");
    benMenstrualDetails.setReservedForChange("Reserved For Change");
    benMenstrualDetails.setSyncedBy("Synced By");
    benMenstrualDetails.setSyncedDate(mock(Timestamp.class));
    benMenstrualDetails.setVanID(1);
    benMenstrualDetails.setVanSerialNo(1L);
    benMenstrualDetails.setVehicalNo("Vehical No");
    benMenstrualDetails.setVisitCode(1L);
    benMenstrualDetails.setlMPDate(mock(Timestamp.class));
    when(benMenstrualDetailsRepo.save(Mockito.<BenMenstrualDetails>any())).thenReturn(benMenstrualDetails);

    BenMenstrualDetails benMenstrualDetails2 = new BenMenstrualDetails();
    benMenstrualDetails2.setBenMenstrualID(1);
    benMenstrualDetails2.setBenVisitID(1L);
    benMenstrualDetails2.setBeneficiaryRegID(1L);
    benMenstrualDetails2.setBloodFlowDuration("Blood Flow Duration");
    benMenstrualDetails2.setCaptureDate(mock(Date.class));
    benMenstrualDetails2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMenstrualDetails2.setCreatedDate(mock(Timestamp.class));
    benMenstrualDetails2.setCycleLength("Cycle Length");
    benMenstrualDetails2.setDeleted(true);
    benMenstrualDetails2.setLastModDate(mock(Timestamp.class));
    benMenstrualDetails2.setLmp_date(mock(Date.class));
    benMenstrualDetails2.setMenstrualCycleStatus("Menstrual Cycle Status");
    benMenstrualDetails2.setMenstrualCycleStatusID((short) 1);
    benMenstrualDetails2.setMenstrualCyclelengthID((short) 1);
    benMenstrualDetails2.setMenstrualFlowDurationID((short) 1);
    benMenstrualDetails2.setMenstrualProblemID("Menstrual Problem ID");
    benMenstrualDetails2.setMenstrualProblemList(new ArrayList<>());
    benMenstrualDetails2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMenstrualDetails2.setParkingPlaceID(1);
    benMenstrualDetails2.setProblemName("Problem Name");
    benMenstrualDetails2.setProcessed("Processed");
    benMenstrualDetails2.setProviderServiceMapID(1);
    benMenstrualDetails2.setRegularity("Regularity");
    benMenstrualDetails2.setReservedForChange("Reserved For Change");
    benMenstrualDetails2.setSyncedBy("Synced By");
    benMenstrualDetails2.setSyncedDate(mock(Timestamp.class));
    benMenstrualDetails2.setVanID(1);
    benMenstrualDetails2.setVanSerialNo(1L);
    benMenstrualDetails2.setVehicalNo("Vehical No");
    benMenstrualDetails2.setVisitCode(1L);
    benMenstrualDetails2.setlMPDate(mock(Timestamp.class));

    // Act
    Integer actualSaveBenMenstrualHistoryResult = commonNurseServiceImpl.saveBenMenstrualHistory(benMenstrualDetails2);

    // Assert
    verify(benMenstrualDetails).getBenMenstrualID();
    verify(benMenstrualDetails).setBenMenstrualID(isA(Integer.class));
    verify(benMenstrualDetails).setBenVisitID(isA(Long.class));
    verify(benMenstrualDetails).setBeneficiaryRegID(isA(Long.class));
    verify(benMenstrualDetails).setBloodFlowDuration(eq("Blood Flow Duration"));
    verify(benMenstrualDetails).setCaptureDate(isA(Date.class));
    verify(benMenstrualDetails).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benMenstrualDetails).setCreatedDate(isA(Timestamp.class));
    verify(benMenstrualDetails).setCycleLength(eq("Cycle Length"));
    verify(benMenstrualDetails).setDeleted(isA(Boolean.class));
    verify(benMenstrualDetails).setLastModDate(isA(Timestamp.class));
    verify(benMenstrualDetails).setLmp_date(isA(Date.class));
    verify(benMenstrualDetails).setMenstrualCycleStatus(eq("Menstrual Cycle Status"));
    verify(benMenstrualDetails).setMenstrualCycleStatusID(isA(Short.class));
    verify(benMenstrualDetails).setMenstrualCyclelengthID(isA(Short.class));
    verify(benMenstrualDetails).setMenstrualFlowDurationID(isA(Short.class));
    verify(benMenstrualDetails).setMenstrualProblemID(eq("Menstrual Problem ID"));
    verify(benMenstrualDetails).setMenstrualProblemList(isA(ArrayList.class));
    verify(benMenstrualDetails).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benMenstrualDetails).setParkingPlaceID(isA(Integer.class));
    verify(benMenstrualDetails).setProblemName(eq("Problem Name"));
    verify(benMenstrualDetails).setProcessed(eq("Processed"));
    verify(benMenstrualDetails).setProviderServiceMapID(isA(Integer.class));
    verify(benMenstrualDetails).setRegularity(eq("Regularity"));
    verify(benMenstrualDetails).setReservedForChange(eq("Reserved For Change"));
    verify(benMenstrualDetails).setSyncedBy(eq("Synced By"));
    verify(benMenstrualDetails).setSyncedDate(isA(Timestamp.class));
    verify(benMenstrualDetails).setVanID(isA(Integer.class));
    verify(benMenstrualDetails).setVanSerialNo(isA(Long.class));
    verify(benMenstrualDetails).setVehicalNo(eq("Vehical No"));
    verify(benMenstrualDetails).setVisitCode(isA(Long.class));
    verify(benMenstrualDetails).setlMPDate(isA(Timestamp.class));
    verify(benMenstrualDetailsRepo).save(isA(BenMenstrualDetails.class));
    assertNull(actualSaveBenMenstrualHistoryResult);
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenMenstrualHistory(BenMenstrualDetails)}
   */
  @Test
  void testSaveBenMenstrualHistory4() {
    // Arrange
    BenMenstrualDetails benMenstrualDetails = mock(BenMenstrualDetails.class);
    when(benMenstrualDetails.getBenMenstrualID()).thenReturn(1);
    doNothing().when(benMenstrualDetails).setBenMenstrualID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails).setBloodFlowDuration(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benMenstrualDetails).setCreatedBy(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benMenstrualDetails).setCycleLength(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benMenstrualDetails).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benMenstrualDetails).setLmp_date(Mockito.<Date>any());
    doNothing().when(benMenstrualDetails).setMenstrualCycleStatus(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setMenstrualCycleStatusID(Mockito.<Short>any());
    doNothing().when(benMenstrualDetails).setMenstrualCyclelengthID(Mockito.<Short>any());
    doNothing().when(benMenstrualDetails).setMenstrualFlowDurationID(Mockito.<Short>any());
    doNothing().when(benMenstrualDetails).setMenstrualProblemID(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setMenstrualProblemList(Mockito.<ArrayList<Map<String, Object>>>any());
    doNothing().when(benMenstrualDetails).setModifiedBy(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails).setProblemName(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setProcessed(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails).setRegularity(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setReservedForChange(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setSyncedBy(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benMenstrualDetails).setVanID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails).setVehicalNo(Mockito.<String>any());
    doNothing().when(benMenstrualDetails).setVisitCode(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails).setlMPDate(Mockito.<Timestamp>any());
    benMenstrualDetails.setBenMenstrualID(1);
    benMenstrualDetails.setBenVisitID(1L);
    benMenstrualDetails.setBeneficiaryRegID(1L);
    benMenstrualDetails.setBloodFlowDuration("Blood Flow Duration");
    benMenstrualDetails.setCaptureDate(mock(Date.class));
    benMenstrualDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMenstrualDetails.setCreatedDate(mock(Timestamp.class));
    benMenstrualDetails.setCycleLength("Cycle Length");
    benMenstrualDetails.setDeleted(true);
    benMenstrualDetails.setLastModDate(mock(Timestamp.class));
    benMenstrualDetails.setLmp_date(mock(Date.class));
    benMenstrualDetails.setMenstrualCycleStatus("Menstrual Cycle Status");
    benMenstrualDetails.setMenstrualCycleStatusID((short) 1);
    benMenstrualDetails.setMenstrualCyclelengthID((short) 1);
    benMenstrualDetails.setMenstrualFlowDurationID((short) 1);
    benMenstrualDetails.setMenstrualProblemID("Menstrual Problem ID");
    benMenstrualDetails.setMenstrualProblemList(new ArrayList<>());
    benMenstrualDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMenstrualDetails.setParkingPlaceID(1);
    benMenstrualDetails.setProblemName("Problem Name");
    benMenstrualDetails.setProcessed("Processed");
    benMenstrualDetails.setProviderServiceMapID(1);
    benMenstrualDetails.setRegularity("Regularity");
    benMenstrualDetails.setReservedForChange("Reserved For Change");
    benMenstrualDetails.setSyncedBy("Synced By");
    benMenstrualDetails.setSyncedDate(mock(Timestamp.class));
    benMenstrualDetails.setVanID(1);
    benMenstrualDetails.setVanSerialNo(1L);
    benMenstrualDetails.setVehicalNo("Vehical No");
    benMenstrualDetails.setVisitCode(1L);
    benMenstrualDetails.setlMPDate(mock(Timestamp.class));
    when(benMenstrualDetailsRepo.save(Mockito.<BenMenstrualDetails>any())).thenReturn(benMenstrualDetails);
    BenMenstrualDetails benMenstrualDetails2 = mock(BenMenstrualDetails.class);
    when(benMenstrualDetails2.getMenstrualProblemList()).thenReturn(new ArrayList<>());
    doNothing().when(benMenstrualDetails2).setBenMenstrualID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails2).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails2).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails2).setBloodFlowDuration(Mockito.<String>any());
    doNothing().when(benMenstrualDetails2).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benMenstrualDetails2).setCreatedBy(Mockito.<String>any());
    doNothing().when(benMenstrualDetails2).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benMenstrualDetails2).setCycleLength(Mockito.<String>any());
    doNothing().when(benMenstrualDetails2).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benMenstrualDetails2).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benMenstrualDetails2).setLmp_date(Mockito.<Date>any());
    doNothing().when(benMenstrualDetails2).setMenstrualCycleStatus(Mockito.<String>any());
    doNothing().when(benMenstrualDetails2).setMenstrualCycleStatusID(Mockito.<Short>any());
    doNothing().when(benMenstrualDetails2).setMenstrualCyclelengthID(Mockito.<Short>any());
    doNothing().when(benMenstrualDetails2).setMenstrualFlowDurationID(Mockito.<Short>any());
    doNothing().when(benMenstrualDetails2).setMenstrualProblemID(Mockito.<String>any());
    doNothing().when(benMenstrualDetails2).setMenstrualProblemList(Mockito.<ArrayList<Map<String, Object>>>any());
    doNothing().when(benMenstrualDetails2).setModifiedBy(Mockito.<String>any());
    doNothing().when(benMenstrualDetails2).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails2).setProblemName(Mockito.<String>any());
    doNothing().when(benMenstrualDetails2).setProcessed(Mockito.<String>any());
    doNothing().when(benMenstrualDetails2).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails2).setRegularity(Mockito.<String>any());
    doNothing().when(benMenstrualDetails2).setReservedForChange(Mockito.<String>any());
    doNothing().when(benMenstrualDetails2).setSyncedBy(Mockito.<String>any());
    doNothing().when(benMenstrualDetails2).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benMenstrualDetails2).setVanID(Mockito.<Integer>any());
    doNothing().when(benMenstrualDetails2).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails2).setVehicalNo(Mockito.<String>any());
    doNothing().when(benMenstrualDetails2).setVisitCode(Mockito.<Long>any());
    doNothing().when(benMenstrualDetails2).setlMPDate(Mockito.<Timestamp>any());
    benMenstrualDetails2.setBenMenstrualID(1);
    benMenstrualDetails2.setBenVisitID(1L);
    benMenstrualDetails2.setBeneficiaryRegID(1L);
    benMenstrualDetails2.setBloodFlowDuration("Blood Flow Duration");
    benMenstrualDetails2.setCaptureDate(mock(Date.class));
    benMenstrualDetails2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benMenstrualDetails2.setCreatedDate(mock(Timestamp.class));
    benMenstrualDetails2.setCycleLength("Cycle Length");
    benMenstrualDetails2.setDeleted(true);
    benMenstrualDetails2.setLastModDate(mock(Timestamp.class));
    benMenstrualDetails2.setLmp_date(mock(Date.class));
    benMenstrualDetails2.setMenstrualCycleStatus("Menstrual Cycle Status");
    benMenstrualDetails2.setMenstrualCycleStatusID((short) 1);
    benMenstrualDetails2.setMenstrualCyclelengthID((short) 1);
    benMenstrualDetails2.setMenstrualFlowDurationID((short) 1);
    benMenstrualDetails2.setMenstrualProblemID("Menstrual Problem ID");
    benMenstrualDetails2.setMenstrualProblemList(new ArrayList<>());
    benMenstrualDetails2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benMenstrualDetails2.setParkingPlaceID(1);
    benMenstrualDetails2.setProblemName("Problem Name");
    benMenstrualDetails2.setProcessed("Processed");
    benMenstrualDetails2.setProviderServiceMapID(1);
    benMenstrualDetails2.setRegularity("Regularity");
    benMenstrualDetails2.setReservedForChange("Reserved For Change");
    benMenstrualDetails2.setSyncedBy("Synced By");
    benMenstrualDetails2.setSyncedDate(mock(Timestamp.class));
    benMenstrualDetails2.setVanID(1);
    benMenstrualDetails2.setVanSerialNo(1L);
    benMenstrualDetails2.setVehicalNo("Vehical No");
    benMenstrualDetails2.setVisitCode(1L);
    benMenstrualDetails2.setlMPDate(mock(Timestamp.class));

    // Act
    Integer actualSaveBenMenstrualHistoryResult = commonNurseServiceImpl.saveBenMenstrualHistory(benMenstrualDetails2);

    // Assert
    verify(benMenstrualDetails, atLeast(1)).getBenMenstrualID();
    verify(benMenstrualDetails2).getMenstrualProblemList();
    verify(benMenstrualDetails).setBenMenstrualID(isA(Integer.class));
    verify(benMenstrualDetails2).setBenMenstrualID(isA(Integer.class));
    verify(benMenstrualDetails).setBenVisitID(isA(Long.class));
    verify(benMenstrualDetails2).setBenVisitID(isA(Long.class));
    verify(benMenstrualDetails).setBeneficiaryRegID(isA(Long.class));
    verify(benMenstrualDetails2).setBeneficiaryRegID(isA(Long.class));
    verify(benMenstrualDetails).setBloodFlowDuration(eq("Blood Flow Duration"));
    verify(benMenstrualDetails2).setBloodFlowDuration(eq("Blood Flow Duration"));
    verify(benMenstrualDetails).setCaptureDate(isA(Date.class));
    verify(benMenstrualDetails2).setCaptureDate(isA(Date.class));
    verify(benMenstrualDetails).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benMenstrualDetails2).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benMenstrualDetails).setCreatedDate(isA(Timestamp.class));
    verify(benMenstrualDetails2).setCreatedDate(isA(Timestamp.class));
    verify(benMenstrualDetails).setCycleLength(eq("Cycle Length"));
    verify(benMenstrualDetails2).setCycleLength(eq("Cycle Length"));
    verify(benMenstrualDetails).setDeleted(isA(Boolean.class));
    verify(benMenstrualDetails2).setDeleted(isA(Boolean.class));
    verify(benMenstrualDetails).setLastModDate(isA(Timestamp.class));
    verify(benMenstrualDetails2).setLastModDate(isA(Timestamp.class));
    verify(benMenstrualDetails).setLmp_date(isA(Date.class));
    verify(benMenstrualDetails2).setLmp_date(isA(Date.class));
    verify(benMenstrualDetails).setMenstrualCycleStatus(eq("Menstrual Cycle Status"));
    verify(benMenstrualDetails2).setMenstrualCycleStatus(eq("Menstrual Cycle Status"));
    verify(benMenstrualDetails).setMenstrualCycleStatusID(isA(Short.class));
    verify(benMenstrualDetails2).setMenstrualCycleStatusID(isA(Short.class));
    verify(benMenstrualDetails).setMenstrualCyclelengthID(isA(Short.class));
    verify(benMenstrualDetails2).setMenstrualCyclelengthID(isA(Short.class));
    verify(benMenstrualDetails).setMenstrualFlowDurationID(isA(Short.class));
    verify(benMenstrualDetails2).setMenstrualFlowDurationID(isA(Short.class));
    verify(benMenstrualDetails).setMenstrualProblemID(eq("Menstrual Problem ID"));
    verify(benMenstrualDetails2).setMenstrualProblemID(eq("Menstrual Problem ID"));
    verify(benMenstrualDetails).setMenstrualProblemList(isA(ArrayList.class));
    verify(benMenstrualDetails2).setMenstrualProblemList(isA(ArrayList.class));
    verify(benMenstrualDetails).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benMenstrualDetails2).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benMenstrualDetails).setParkingPlaceID(isA(Integer.class));
    verify(benMenstrualDetails2).setParkingPlaceID(isA(Integer.class));
    verify(benMenstrualDetails).setProblemName(eq("Problem Name"));
    verify(benMenstrualDetails2).setProblemName(eq("Problem Name"));
    verify(benMenstrualDetails).setProcessed(eq("Processed"));
    verify(benMenstrualDetails2).setProcessed(eq("Processed"));
    verify(benMenstrualDetails).setProviderServiceMapID(isA(Integer.class));
    verify(benMenstrualDetails2).setProviderServiceMapID(isA(Integer.class));
    verify(benMenstrualDetails).setRegularity(eq("Regularity"));
    verify(benMenstrualDetails2).setRegularity(eq("Regularity"));
    verify(benMenstrualDetails).setReservedForChange(eq("Reserved For Change"));
    verify(benMenstrualDetails2).setReservedForChange(eq("Reserved For Change"));
    verify(benMenstrualDetails).setSyncedBy(eq("Synced By"));
    verify(benMenstrualDetails2).setSyncedBy(eq("Synced By"));
    verify(benMenstrualDetails).setSyncedDate(isA(Timestamp.class));
    verify(benMenstrualDetails2).setSyncedDate(isA(Timestamp.class));
    verify(benMenstrualDetails).setVanID(isA(Integer.class));
    verify(benMenstrualDetails2).setVanID(isA(Integer.class));
    verify(benMenstrualDetails).setVanSerialNo(isA(Long.class));
    verify(benMenstrualDetails2).setVanSerialNo(isA(Long.class));
    verify(benMenstrualDetails).setVehicalNo(eq("Vehical No"));
    verify(benMenstrualDetails2).setVehicalNo(eq("Vehical No"));
    verify(benMenstrualDetails).setVisitCode(isA(Long.class));
    verify(benMenstrualDetails2).setVisitCode(isA(Long.class));
    verify(benMenstrualDetails).setlMPDate(isA(Timestamp.class));
    verify(benMenstrualDetails2).setlMPDate(isA(Timestamp.class));
    verify(benMenstrualDetailsRepo).save(isA(BenMenstrualDetails.class));
    assertEquals(1, actualSaveBenMenstrualHistoryResult.intValue());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenFamilyHistory(BenFamilyHistory)}
   */
  @Test
  void testSaveBenFamilyHistory() {
    // Arrange
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

    // Act and Assert
    assertEquals(1L, commonNurseServiceImpl.saveBenFamilyHistory(benFamilyHistory).longValue());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenFamilyHistory(BenFamilyHistory)}
   */
  @Test
  void testSaveBenFamilyHistory2() {
    // Arrange
    BenFamilyHistory benFamilyHistory = mock(BenFamilyHistory.class);
    when(benFamilyHistory.getBenFamilyHistory()).thenReturn(new ArrayList<>());
    doNothing().when(benFamilyHistory).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benFamilyHistory).setCreatedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benFamilyHistory).setDiseaseType(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setDiseaseTypeID(Mockito.<Short>any());
    doNothing().when(benFamilyHistory).setFamilyDiseaseList(Mockito.<List<Map<String, Object>>>any());
    doNothing().when(benFamilyHistory).setFamilyMember(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setGeneticDisorder(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory).setIsConsanguineousMarrige(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setIsGeneticDisorder(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory).setModifiedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setOtherDiseaseType(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory).setProcessed(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory).setReservedForChange(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setSnomedCode(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setSnomedTerm(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setSyncedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory).setVanID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benFamilyHistory).setVehicalNo(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setVisitCode(Mockito.<Long>any());
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

    // Act
    Long actualSaveBenFamilyHistoryResult = commonNurseServiceImpl.saveBenFamilyHistory(benFamilyHistory);

    // Assert
    verify(benFamilyHistory).getBenFamilyHistory();
    verify(benFamilyHistory).setBenVisitID(isA(Long.class));
    verify(benFamilyHistory).setBeneficiaryRegID(isA(Long.class));
    verify(benFamilyHistory).setCaptureDate(isA(Date.class));
    verify(benFamilyHistory).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benFamilyHistory).setCreatedDate(isA(Timestamp.class));
    verify(benFamilyHistory).setDeleted(isA(Boolean.class));
    verify(benFamilyHistory).setDiseaseType(eq("Disease Type"));
    verify(benFamilyHistory).setDiseaseTypeID(isA(Short.class));
    verify(benFamilyHistory).setFamilyDiseaseList(isA(List.class));
    verify(benFamilyHistory).setFamilyMember(eq("Family Member"));
    verify(benFamilyHistory).setGeneticDisorder(eq("Genetic Disorder"));
    verify(benFamilyHistory).setID(isA(Long.class));
    verify(benFamilyHistory).setIsConsanguineousMarrige(eq("Is Consanguineous Marrige"));
    verify(benFamilyHistory).setIsGeneticDisorder(eq("Is Genetic Disorder"));
    verify(benFamilyHistory).setLastModDate(isA(Timestamp.class));
    verify(benFamilyHistory).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benFamilyHistory).setOtherDiseaseType(eq("Other Disease Type"));
    verify(benFamilyHistory).setParkingPlaceID(isA(Integer.class));
    verify(benFamilyHistory).setProcessed(eq("Processed"));
    verify(benFamilyHistory).setProviderServiceMapID(isA(Integer.class));
    verify(benFamilyHistory).setReservedForChange(eq("Reserved For Change"));
    verify(benFamilyHistory).setSnomedCode(eq("Snomed Code"));
    verify(benFamilyHistory).setSnomedTerm(eq("Snomed Term"));
    verify(benFamilyHistory).setSyncedBy(eq("Synced By"));
    verify(benFamilyHistory).setSyncedDate(isA(Timestamp.class));
    verify(benFamilyHistory).setVanID(isA(Integer.class));
    verify(benFamilyHistory).setVanSerialNo(isA(Long.class));
    verify(benFamilyHistory).setVehicalNo(eq("Vehical No"));
    verify(benFamilyHistory).setVisitCode(isA(Long.class));
    assertEquals(1L, actualSaveBenFamilyHistoryResult.longValue());
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenFamilyHistory(BenFamilyHistory)}
   */
  @Test
  void testSaveBenFamilyHistory3() {
    // Arrange
    when(benFamilyHistoryRepo.saveAll(Mockito.<Iterable<BenFamilyHistory>>any())).thenReturn(new ArrayList<>());

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

    ArrayList<BenFamilyHistory> benFamilyHistoryList = new ArrayList<>();
    benFamilyHistoryList.add(benFamilyHistory);
    BenFamilyHistory benFamilyHistory2 = mock(BenFamilyHistory.class);
    when(benFamilyHistory2.getBenFamilyHistory()).thenReturn(benFamilyHistoryList);
    doNothing().when(benFamilyHistory2).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory2).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory2).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benFamilyHistory2).setCreatedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory2).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benFamilyHistory2).setDiseaseType(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setDiseaseTypeID(Mockito.<Short>any());
    doNothing().when(benFamilyHistory2).setFamilyDiseaseList(Mockito.<List<Map<String, Object>>>any());
    doNothing().when(benFamilyHistory2).setFamilyMember(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setGeneticDisorder(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory2).setIsConsanguineousMarrige(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setIsGeneticDisorder(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory2).setModifiedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setOtherDiseaseType(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory2).setProcessed(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory2).setReservedForChange(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setSnomedCode(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setSnomedTerm(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setSyncedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory2).setVanID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory2).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benFamilyHistory2).setVehicalNo(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setVisitCode(Mockito.<Long>any());
    benFamilyHistory2.setBenVisitID(1L);
    benFamilyHistory2.setBeneficiaryRegID(1L);
    benFamilyHistory2.setCaptureDate(mock(Date.class));
    benFamilyHistory2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benFamilyHistory2.setCreatedDate(mock(Timestamp.class));
    benFamilyHistory2.setDeleted(true);
    benFamilyHistory2.setDiseaseType("Disease Type");
    benFamilyHistory2.setDiseaseTypeID((short) 1);
    benFamilyHistory2.setFamilyDiseaseList(new ArrayList<>());
    benFamilyHistory2.setFamilyMember("Family Member");
    benFamilyHistory2.setGeneticDisorder("Genetic Disorder");
    benFamilyHistory2.setID(1L);
    benFamilyHistory2.setIsConsanguineousMarrige("Is Consanguineous Marrige");
    benFamilyHistory2.setIsGeneticDisorder("Is Genetic Disorder");
    benFamilyHistory2.setLastModDate(mock(Timestamp.class));
    benFamilyHistory2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benFamilyHistory2.setOtherDiseaseType("Other Disease Type");
    benFamilyHistory2.setParkingPlaceID(1);
    benFamilyHistory2.setProcessed("Processed");
    benFamilyHistory2.setProviderServiceMapID(1);
    benFamilyHistory2.setReservedForChange("Reserved For Change");
    benFamilyHistory2.setSnomedCode("Snomed Code");
    benFamilyHistory2.setSnomedTerm("Snomed Term");
    benFamilyHistory2.setSyncedBy("Synced By");
    benFamilyHistory2.setSyncedDate(mock(Timestamp.class));
    benFamilyHistory2.setVanID(1);
    benFamilyHistory2.setVanSerialNo(1L);
    benFamilyHistory2.setVehicalNo("Vehical No");
    benFamilyHistory2.setVisitCode(1L);

    // Act
    Long actualSaveBenFamilyHistoryResult = commonNurseServiceImpl.saveBenFamilyHistory(benFamilyHistory2);

    // Assert
    verify(benFamilyHistory2).getBenFamilyHistory();
    verify(benFamilyHistory2).setBenVisitID(isA(Long.class));
    verify(benFamilyHistory2).setBeneficiaryRegID(isA(Long.class));
    verify(benFamilyHistory2).setCaptureDate(isA(Date.class));
    verify(benFamilyHistory2).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benFamilyHistory2).setCreatedDate(isA(Timestamp.class));
    verify(benFamilyHistory2).setDeleted(isA(Boolean.class));
    verify(benFamilyHistory2).setDiseaseType(eq("Disease Type"));
    verify(benFamilyHistory2).setDiseaseTypeID(isA(Short.class));
    verify(benFamilyHistory2).setFamilyDiseaseList(isA(List.class));
    verify(benFamilyHistory2).setFamilyMember(eq("Family Member"));
    verify(benFamilyHistory2).setGeneticDisorder(eq("Genetic Disorder"));
    verify(benFamilyHistory2).setID(isA(Long.class));
    verify(benFamilyHistory2).setIsConsanguineousMarrige(eq("Is Consanguineous Marrige"));
    verify(benFamilyHistory2).setIsGeneticDisorder(eq("Is Genetic Disorder"));
    verify(benFamilyHistory2).setLastModDate(isA(Timestamp.class));
    verify(benFamilyHistory2).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benFamilyHistory2).setOtherDiseaseType(eq("Other Disease Type"));
    verify(benFamilyHistory2).setParkingPlaceID(isA(Integer.class));
    verify(benFamilyHistory2).setProcessed(eq("Processed"));
    verify(benFamilyHistory2).setProviderServiceMapID(isA(Integer.class));
    verify(benFamilyHistory2).setReservedForChange(eq("Reserved For Change"));
    verify(benFamilyHistory2).setSnomedCode(eq("Snomed Code"));
    verify(benFamilyHistory2).setSnomedTerm(eq("Snomed Term"));
    verify(benFamilyHistory2).setSyncedBy(eq("Synced By"));
    verify(benFamilyHistory2).setSyncedDate(isA(Timestamp.class));
    verify(benFamilyHistory2).setVanID(isA(Integer.class));
    verify(benFamilyHistory2).setVanSerialNo(isA(Long.class));
    verify(benFamilyHistory2).setVehicalNo(eq("Vehical No"));
    verify(benFamilyHistory2).setVisitCode(isA(Long.class));
    verify(benFamilyHistoryRepo).saveAll(isA(Iterable.class));
    assertNull(actualSaveBenFamilyHistoryResult);
  }

  /**
   * Method under test:
   * {@link CommonNurseServiceImpl#saveBenFamilyHistory(BenFamilyHistory)}
   */
  @Test
  void testSaveBenFamilyHistory4() {
    // Arrange
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

    ArrayList<BenFamilyHistory> benFamilyHistoryList = new ArrayList<>();
    benFamilyHistoryList.add(benFamilyHistory);
    when(benFamilyHistoryRepo.saveAll(Mockito.<Iterable<BenFamilyHistory>>any())).thenReturn(benFamilyHistoryList);

    BenFamilyHistory benFamilyHistory2 = new BenFamilyHistory();
    benFamilyHistory2.setBenVisitID(1L);
    benFamilyHistory2.setBeneficiaryRegID(1L);
    benFamilyHistory2.setCaptureDate(mock(Date.class));
    benFamilyHistory2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benFamilyHistory2.setCreatedDate(mock(Timestamp.class));
    benFamilyHistory2.setDeleted(true);
    benFamilyHistory2.setDiseaseType("Disease Type");
    benFamilyHistory2.setDiseaseTypeID((short) 1);
    benFamilyHistory2.setFamilyDiseaseList(new ArrayList<>());
    benFamilyHistory2.setFamilyMember("Family Member");
    benFamilyHistory2.setGeneticDisorder("Genetic Disorder");
    benFamilyHistory2.setID(1L);
    benFamilyHistory2.setIsConsanguineousMarrige("Is Consanguineous Marrige");
    benFamilyHistory2.setIsGeneticDisorder("Is Genetic Disorder");
    benFamilyHistory2.setLastModDate(mock(Timestamp.class));
    benFamilyHistory2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benFamilyHistory2.setOtherDiseaseType("Other Disease Type");
    benFamilyHistory2.setParkingPlaceID(1);
    benFamilyHistory2.setProcessed("Processed");
    benFamilyHistory2.setProviderServiceMapID(1);
    benFamilyHistory2.setReservedForChange("Reserved For Change");
    benFamilyHistory2.setSnomedCode("Snomed Code");
    benFamilyHistory2.setSnomedTerm("Snomed Term");
    benFamilyHistory2.setSyncedBy("Synced By");
    benFamilyHistory2.setSyncedDate(mock(Timestamp.class));
    benFamilyHistory2.setVanID(1);
    benFamilyHistory2.setVanSerialNo(1L);
    benFamilyHistory2.setVehicalNo("Vehical No");
    benFamilyHistory2.setVisitCode(1L);

    ArrayList<BenFamilyHistory> benFamilyHistoryList2 = new ArrayList<>();
    benFamilyHistoryList2.add(benFamilyHistory2);
    BenFamilyHistory benFamilyHistory3 = mock(BenFamilyHistory.class);
    when(benFamilyHistory3.getBenFamilyHistory()).thenReturn(benFamilyHistoryList2);
    doNothing().when(benFamilyHistory3).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory3).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory3).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benFamilyHistory3).setCreatedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory3).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benFamilyHistory3).setDiseaseType(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setDiseaseTypeID(Mockito.<Short>any());
    doNothing().when(benFamilyHistory3).setFamilyDiseaseList(Mockito.<List<Map<String, Object>>>any());
    doNothing().when(benFamilyHistory3).setFamilyMember(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setGeneticDisorder(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory3).setIsConsanguineousMarrige(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setIsGeneticDisorder(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory3).setModifiedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setOtherDiseaseType(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory3).setProcessed(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory3).setReservedForChange(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setSnomedCode(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setSnomedTerm(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setSyncedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory3).setVanID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory3).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benFamilyHistory3).setVehicalNo(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setVisitCode(Mockito.<Long>any());
    benFamilyHistory3.setBenVisitID(1L);
    benFamilyHistory3.setBeneficiaryRegID(1L);
    benFamilyHistory3.setCaptureDate(mock(Date.class));
    benFamilyHistory3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benFamilyHistory3.setCreatedDate(mock(Timestamp.class));
    benFamilyHistory3.setDeleted(true);
    benFamilyHistory3.setDiseaseType("Disease Type");
    benFamilyHistory3.setDiseaseTypeID((short) 1);
    benFamilyHistory3.setFamilyDiseaseList(new ArrayList<>());
    benFamilyHistory3.setFamilyMember("Family Member");
    benFamilyHistory3.setGeneticDisorder("Genetic Disorder");
    benFamilyHistory3.setID(1L);
    benFamilyHistory3.setIsConsanguineousMarrige("Is Consanguineous Marrige");
    benFamilyHistory3.setIsGeneticDisorder("Is Genetic Disorder");
    benFamilyHistory3.setLastModDate(mock(Timestamp.class));
    benFamilyHistory3.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benFamilyHistory3.setOtherDiseaseType("Other Disease Type");
    benFamilyHistory3.setParkingPlaceID(1);
    benFamilyHistory3.setProcessed("Processed");
    benFamilyHistory3.setProviderServiceMapID(1);
    benFamilyHistory3.setReservedForChange("Reserved For Change");
    benFamilyHistory3.setSnomedCode("Snomed Code");
    benFamilyHistory3.setSnomedTerm("Snomed Term");
    benFamilyHistory3.setSyncedBy("Synced By");
    benFamilyHistory3.setSyncedDate(mock(Timestamp.class));
    benFamilyHistory3.setVanID(1);
    benFamilyHistory3.setVanSerialNo(1L);
    benFamilyHistory3.setVehicalNo("Vehical No");
    benFamilyHistory3.setVisitCode(1L);

    // Act
    Long actualSaveBenFamilyHistoryResult = commonNurseServiceImpl.saveBenFamilyHistory(benFamilyHistory3);

    // Assert
    verify(benFamilyHistory3).getBenFamilyHistory();
    verify(benFamilyHistory3).setBenVisitID(isA(Long.class));
    verify(benFamilyHistory3).setBeneficiaryRegID(isA(Long.class));
    verify(benFamilyHistory3).setCaptureDate(isA(Date.class));
    verify(benFamilyHistory3).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benFamilyHistory3).setCreatedDate(isA(Timestamp.class));
    verify(benFamilyHistory3).setDeleted(isA(Boolean.class));
    verify(benFamilyHistory3).setDiseaseType(eq("Disease Type"));
    verify(benFamilyHistory3).setDiseaseTypeID(isA(Short.class));
    verify(benFamilyHistory3).setFamilyDiseaseList(isA(List.class));
    verify(benFamilyHistory3).setFamilyMember(eq("Family Member"));
    verify(benFamilyHistory3).setGeneticDisorder(eq("Genetic Disorder"));
    verify(benFamilyHistory3).setID(isA(Long.class));
    verify(benFamilyHistory3).setIsConsanguineousMarrige(eq("Is Consanguineous Marrige"));
    verify(benFamilyHistory3).setIsGeneticDisorder(eq("Is Genetic Disorder"));
    verify(benFamilyHistory3).setLastModDate(isA(Timestamp.class));
    verify(benFamilyHistory3).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benFamilyHistory3).setOtherDiseaseType(eq("Other Disease Type"));
    verify(benFamilyHistory3).setParkingPlaceID(isA(Integer.class));
    verify(benFamilyHistory3).setProcessed(eq("Processed"));
    verify(benFamilyHistory3).setProviderServiceMapID(isA(Integer.class));
    verify(benFamilyHistory3).setReservedForChange(eq("Reserved For Change"));
    verify(benFamilyHistory3).setSnomedCode(eq("Snomed Code"));
    verify(benFamilyHistory3).setSnomedTerm(eq("Snomed Term"));
    verify(benFamilyHistory3).setSyncedBy(eq("Synced By"));
    verify(benFamilyHistory3).setSyncedDate(isA(Timestamp.class));
    verify(benFamilyHistory3).setVanID(isA(Integer.class));
    verify(benFamilyHistory3).setVanSerialNo(isA(Long.class));
    verify(benFamilyHistory3).setVehicalNo(eq("Vehical No"));
    verify(benFamilyHistory3).setVisitCode(isA(Long.class));
    verify(benFamilyHistoryRepo).saveAll(isA(Iterable.class));
    assertEquals(1L, actualSaveBenFamilyHistoryResult.longValue());
  }

  @Test
  void testSavePersonalHistory() {
    // Arrange
    when(benPersonalHabitRepo.saveAll(Mockito.<Iterable<BenPersonalHabit>>any())).thenReturn(new ArrayList<>());

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

    // Act
    Integer actualSavePersonalHistoryResult = commonNurseServiceImpl.savePersonalHistory(benPersonalHabit);

    // Assert
    verify(benPersonalHabitRepo).saveAll(isA(Iterable.class));
    assertNull(actualSavePersonalHistoryResult);
  }

  @Test
  void testSavePersonalHistory2() {
    // Arrange
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
    benPersonalHabit.setRiskySexualPracticesStatus('\u0001');
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

    ArrayList<BenPersonalHabit> benPersonalHabitList = new ArrayList<>();
    benPersonalHabitList.add(benPersonalHabit);
    when(benPersonalHabitRepo.saveAll(Mockito.<Iterable<BenPersonalHabit>>any())).thenReturn(benPersonalHabitList);

    BenPersonalHabit benPersonalHabit2 = new BenPersonalHabit();
    benPersonalHabit2.setAlcoholDuration(mock(Timestamp.class));
    benPersonalHabit2.setAlcoholIntakeFrequency("Alcohol Intake Frequency");
    benPersonalHabit2.setAlcoholIntakeStatus("Alcohol Intake Status");
    benPersonalHabit2.setAlcoholList(new ArrayList<>());
    benPersonalHabit2.setAlcoholType("Alcohol Type");
    benPersonalHabit2.setAlcoholTypeID("Alcohol Type ID");
    benPersonalHabit2.setAlcohol_use_duration(mock(Date.class));
    benPersonalHabit2.setAllergicList(new ArrayList<>());
    benPersonalHabit2.setAllergyStatus("Allergy Status");
    benPersonalHabit2.setAvgAlcoholConsumption("Avg Alcohol Consumption");
    benPersonalHabit2.setBenPersonalHabitID(1);
    benPersonalHabit2.setBenVisitID(1L);
    benPersonalHabit2.setBeneficiaryRegID(1L);
    benPersonalHabit2.setCaptureDate(mock(Date.class));
    benPersonalHabit2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benPersonalHabit2.setCreatedDate(mock(Timestamp.class));
    benPersonalHabit2.setDeleted(true);
    benPersonalHabit2.setDietaryType("Dietary Type");
    benPersonalHabit2.setLastModDate(mock(Timestamp.class));
    benPersonalHabit2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benPersonalHabit2.setNumberperDay((short) 1);
    benPersonalHabit2.setNumberperWeek((short) 1);
    benPersonalHabit2.setOtherAlcoholType("Other Alcohol Type");
    benPersonalHabit2.setOtherTobaccoUseType("Other Tobacco Use Type");
    benPersonalHabit2.setParkingPlaceID(1);
    benPersonalHabit2.setPhysicalActivityType("Physical Activity Type");
    benPersonalHabit2.setProcessed("Processed");
    benPersonalHabit2.setProviderServiceMapID(1);
    benPersonalHabit2.setReservedForChange("Reserved For Change");
    benPersonalHabit2.setRiskySexualPracticeStatus("Risky Sexual Practice Status");
    benPersonalHabit2.setRiskySexualPracticesStatus('A');
    benPersonalHabit2.setSyncedBy("Synced By");
    benPersonalHabit2.setSyncedDate(mock(Timestamp.class));
    benPersonalHabit2.setTobaccoList(new ArrayList<>());
    benPersonalHabit2.setTobaccoUseDuration(mock(Timestamp.class));
    benPersonalHabit2.setTobaccoUseStatus("Tobacco Use Status");
    benPersonalHabit2.setTobaccoUseType("Tobacco Use Type");
    benPersonalHabit2.setTobaccoUseTypeID("Tobacco Use Type ID");
    benPersonalHabit2.setTobacco_use_duration(mock(Date.class));
    benPersonalHabit2.setVanID(1);
    benPersonalHabit2.setVanSerialNo(1L);
    benPersonalHabit2.setVehicalNo("Vehical No");
    benPersonalHabit2.setVisitCode(1L);

    // Act
    Integer actualSavePersonalHistoryResult = commonNurseServiceImpl.savePersonalHistory(benPersonalHabit2);

    // Assert
    verify(benPersonalHabitRepo).saveAll(isA(Iterable.class));
    assertEquals(1, actualSavePersonalHistoryResult.intValue());
  }

  @Test
  void testSavePersonalHistory3() {
    // Arrange
    BenPersonalHabit benPersonalHabit = mock(BenPersonalHabit.class);
    when(benPersonalHabit.getPersonalHistory()).thenReturn(new ArrayList<>());
    doNothing().when(benPersonalHabit).setAlcoholDuration(Mockito.<Timestamp>any());
    doNothing().when(benPersonalHabit).setAlcoholIntakeFrequency(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setAlcoholIntakeStatus(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setAlcoholList(Mockito.<List<Map<String, String>>>any());
    doNothing().when(benPersonalHabit).setAlcoholType(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setAlcoholTypeID(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setAlcohol_use_duration(Mockito.<Date>any());
    doNothing().when(benPersonalHabit).setAllergicList(Mockito.<List<BenAllergyHistory>>any());
    doNothing().when(benPersonalHabit).setAllergyStatus(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setAvgAlcoholConsumption(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setBenPersonalHabitID(Mockito.<Integer>any());
    doNothing().when(benPersonalHabit).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benPersonalHabit).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benPersonalHabit).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benPersonalHabit).setCreatedBy(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benPersonalHabit).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benPersonalHabit).setDietaryType(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benPersonalHabit).setModifiedBy(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setNumberperDay(Mockito.<Short>any());
    doNothing().when(benPersonalHabit).setNumberperWeek(Mockito.<Short>any());
    doNothing().when(benPersonalHabit).setOtherAlcoholType(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setOtherTobaccoUseType(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benPersonalHabit).setPhysicalActivityType(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setProcessed(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benPersonalHabit).setReservedForChange(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setRiskySexualPracticeStatus(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setRiskySexualPracticesStatus(Mockito.<Character>any());
    doNothing().when(benPersonalHabit).setSyncedBy(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benPersonalHabit).setTobaccoList(Mockito.<List<Map<String, String>>>any());
    doNothing().when(benPersonalHabit).setTobaccoUseDuration(Mockito.<Timestamp>any());
    doNothing().when(benPersonalHabit).setTobaccoUseStatus(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setTobaccoUseType(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setTobaccoUseTypeID(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setTobacco_use_duration(Mockito.<Date>any());
    doNothing().when(benPersonalHabit).setVanID(Mockito.<Integer>any());
    doNothing().when(benPersonalHabit).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benPersonalHabit).setVehicalNo(Mockito.<String>any());
    doNothing().when(benPersonalHabit).setVisitCode(Mockito.<Long>any());
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

    // Act
    Integer actualSavePersonalHistoryResult = commonNurseServiceImpl.savePersonalHistory(benPersonalHabit);

    // Assert
    verify(benPersonalHabit).getPersonalHistory();
    verify(benPersonalHabit).setAlcoholDuration(isA(Timestamp.class));
    verify(benPersonalHabit).setAlcoholIntakeFrequency(eq("Alcohol Intake Frequency"));
    verify(benPersonalHabit).setAlcoholIntakeStatus(eq("Alcohol Intake Status"));
    verify(benPersonalHabit).setAlcoholList(isA(List.class));
    verify(benPersonalHabit).setAlcoholType(eq("Alcohol Type"));
    verify(benPersonalHabit).setAlcoholTypeID(eq("Alcohol Type ID"));
    verify(benPersonalHabit).setAlcohol_use_duration(isA(Date.class));
    verify(benPersonalHabit).setAllergicList(isA(List.class));
    verify(benPersonalHabit).setAllergyStatus(eq("Allergy Status"));
    verify(benPersonalHabit).setAvgAlcoholConsumption(eq("Avg Alcohol Consumption"));
    verify(benPersonalHabit).setBenPersonalHabitID(isA(Integer.class));
    verify(benPersonalHabit).setBenVisitID(isA(Long.class));
    verify(benPersonalHabit).setBeneficiaryRegID(isA(Long.class));
    verify(benPersonalHabit).setCaptureDate(isA(Date.class));
    verify(benPersonalHabit).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benPersonalHabit).setCreatedDate(isA(Timestamp.class));
    verify(benPersonalHabit).setDeleted(isA(Boolean.class));
    verify(benPersonalHabit).setDietaryType(eq("Dietary Type"));
    verify(benPersonalHabit).setLastModDate(isA(Timestamp.class));
    verify(benPersonalHabit).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benPersonalHabit).setNumberperDay(isA(Short.class));
    verify(benPersonalHabit).setNumberperWeek(isA(Short.class));
    verify(benPersonalHabit).setOtherAlcoholType(eq("Other Alcohol Type"));
    verify(benPersonalHabit).setOtherTobaccoUseType(eq("Other Tobacco Use Type"));
    verify(benPersonalHabit).setParkingPlaceID(isA(Integer.class));
    verify(benPersonalHabit).setPhysicalActivityType(eq("Physical Activity Type"));
    verify(benPersonalHabit).setProcessed(eq("Processed"));
    verify(benPersonalHabit).setProviderServiceMapID(isA(Integer.class));
    verify(benPersonalHabit).setReservedForChange(eq("Reserved For Change"));
    verify(benPersonalHabit).setRiskySexualPracticeStatus(eq("Risky Sexual Practice Status"));
    verify(benPersonalHabit).setRiskySexualPracticesStatus(isA(Character.class));
    verify(benPersonalHabit).setSyncedBy(eq("Synced By"));
    verify(benPersonalHabit).setSyncedDate(isA(Timestamp.class));
    verify(benPersonalHabit).setTobaccoList(isA(List.class));
    verify(benPersonalHabit).setTobaccoUseDuration(isA(Timestamp.class));
    verify(benPersonalHabit).setTobaccoUseStatus(eq("Tobacco Use Status"));
    verify(benPersonalHabit).setTobaccoUseType(eq("Tobacco Use Type"));
    verify(benPersonalHabit).setTobaccoUseTypeID(eq("Tobacco Use Type ID"));
    verify(benPersonalHabit).setTobacco_use_duration(isA(Date.class));
    verify(benPersonalHabit).setVanID(isA(Integer.class));
    verify(benPersonalHabit).setVanSerialNo(isA(Long.class));
    verify(benPersonalHabit).setVehicalNo(eq("Vehical No"));
    verify(benPersonalHabit).setVisitCode(isA(Long.class));
    assertEquals(1, actualSavePersonalHistoryResult.intValue());
  }

  @Test
  void testSaveAllergyHistory() {
    // Arrange
    when(benAllergyHistoryRepo.saveAll(Mockito.<Iterable<BenAllergyHistory>>any())).thenReturn(new ArrayList<>());

    BenAllergyHistory benAllergyHistory = new BenAllergyHistory();
    benAllergyHistory.setAllergicList(new ArrayList<>());
    benAllergyHistory.setAllergicReactionType("Allergic Reaction Type");
    benAllergyHistory.setAllergicReactionTypeID("Allergic Reaction Type ID");
    benAllergyHistory.setAllergyName("Allergy Name");
    benAllergyHistory.setAllergyStatus("Allergy Status");
    benAllergyHistory.setAllergyType("Allergy Type");
    benAllergyHistory.setBenVisitID(1L);
    benAllergyHistory.setBeneficiaryRegID(1L);
    benAllergyHistory.setCaptureDate(mock(Date.class));
    benAllergyHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benAllergyHistory.setCreatedDate(mock(Timestamp.class));
    benAllergyHistory.setDeleted(true);
    benAllergyHistory.setID(1L);
    benAllergyHistory.setLastModDate(mock(Timestamp.class));
    benAllergyHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benAllergyHistory.setOtherAllergicReaction("Other Allergic Reaction");
    benAllergyHistory.setParkingPlaceID(1);
    benAllergyHistory.setProcessed("Processed");
    benAllergyHistory.setProviderServiceMapID(1);
    benAllergyHistory.setRemarks("Remarks");
    benAllergyHistory.setReservedForChange("Reserved For Change");
    benAllergyHistory.setSnomedCode("Snomed Code");
    benAllergyHistory.setSnomedTerm("Snomed Term");
    benAllergyHistory.setSyncedBy("Synced By");
    benAllergyHistory.setSyncedDate(mock(Timestamp.class));
    benAllergyHistory.setTypeOfAllergicReactions(new ArrayList<>());
    benAllergyHistory.setVanID(1);
    benAllergyHistory.setVanSerialNo(1L);
    benAllergyHistory.setVehicalNo("Vehical No");
    benAllergyHistory.setVisitCode(1L);

    // Act
    Long actualSaveAllergyHistoryResult = commonNurseServiceImpl.saveAllergyHistory(benAllergyHistory);

    // Assert
    verify(benAllergyHistoryRepo).saveAll(isA(Iterable.class));
    assertNull(actualSaveAllergyHistoryResult);
  }

  @Test
  void testSaveAllergyHistory2() {
    // Arrange
    BenAllergyHistory benAllergyHistory = new BenAllergyHistory();
    benAllergyHistory.setAllergicList(new ArrayList<>());
    benAllergyHistory.setAllergicReactionType("Allergic Reaction Type");
    benAllergyHistory.setAllergicReactionTypeID("Allergic Reaction Type ID");
    benAllergyHistory.setAllergyName("Allergy Name");
    benAllergyHistory.setAllergyStatus("Allergy Status");
    benAllergyHistory.setAllergyType("Allergy Type");
    benAllergyHistory.setBenVisitID(1L);
    benAllergyHistory.setBeneficiaryRegID(1L);
    benAllergyHistory.setCaptureDate(mock(Date.class));
    benAllergyHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benAllergyHistory.setCreatedDate(mock(Timestamp.class));
    benAllergyHistory.setDeleted(true);
    benAllergyHistory.setID(1L);
    benAllergyHistory.setLastModDate(mock(Timestamp.class));
    benAllergyHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benAllergyHistory.setOtherAllergicReaction("Other Allergic Reaction");
    benAllergyHistory.setParkingPlaceID(1);
    benAllergyHistory.setProcessed("Processed");
    benAllergyHistory.setProviderServiceMapID(1);
    benAllergyHistory.setRemarks("Remarks");
    benAllergyHistory.setReservedForChange("Reserved For Change");
    benAllergyHistory.setSnomedCode("Snomed Code");
    benAllergyHistory.setSnomedTerm("Snomed Term");
    benAllergyHistory.setSyncedBy("Synced By");
    benAllergyHistory.setSyncedDate(mock(Timestamp.class));
    benAllergyHistory.setTypeOfAllergicReactions(new ArrayList<>());
    benAllergyHistory.setVanID(1);
    benAllergyHistory.setVanSerialNo(1L);
    benAllergyHistory.setVehicalNo("Vehical No");
    benAllergyHistory.setVisitCode(1L);

    ArrayList<BenAllergyHistory> benAllergyHistoryList = new ArrayList<>();
    benAllergyHistoryList.add(benAllergyHistory);
    when(benAllergyHistoryRepo.saveAll(Mockito.<Iterable<BenAllergyHistory>>any())).thenReturn(benAllergyHistoryList);

    BenAllergyHistory benAllergyHistory2 = new BenAllergyHistory();
    benAllergyHistory2.setAllergicList(new ArrayList<>());
    benAllergyHistory2.setAllergicReactionType("Allergic Reaction Type");
    benAllergyHistory2.setAllergicReactionTypeID("Allergic Reaction Type ID");
    benAllergyHistory2.setAllergyName("Allergy Name");
    benAllergyHistory2.setAllergyStatus("Allergy Status");
    benAllergyHistory2.setAllergyType("Allergy Type");
    benAllergyHistory2.setBenVisitID(1L);
    benAllergyHistory2.setBeneficiaryRegID(1L);
    benAllergyHistory2.setCaptureDate(mock(Date.class));
    benAllergyHistory2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benAllergyHistory2.setCreatedDate(mock(Timestamp.class));
    benAllergyHistory2.setDeleted(true);
    benAllergyHistory2.setID(1L);
    benAllergyHistory2.setLastModDate(mock(Timestamp.class));
    benAllergyHistory2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benAllergyHistory2.setOtherAllergicReaction("Other Allergic Reaction");
    benAllergyHistory2.setParkingPlaceID(1);
    benAllergyHistory2.setProcessed("Processed");
    benAllergyHistory2.setProviderServiceMapID(1);
    benAllergyHistory2.setRemarks("Remarks");
    benAllergyHistory2.setReservedForChange("Reserved For Change");
    benAllergyHistory2.setSnomedCode("Snomed Code");
    benAllergyHistory2.setSnomedTerm("Snomed Term");
    benAllergyHistory2.setSyncedBy("Synced By");
    benAllergyHistory2.setSyncedDate(mock(Timestamp.class));
    benAllergyHistory2.setTypeOfAllergicReactions(new ArrayList<>());
    benAllergyHistory2.setVanID(1);
    benAllergyHistory2.setVanSerialNo(1L);
    benAllergyHistory2.setVehicalNo("Vehical No");
    benAllergyHistory2.setVisitCode(1L);

    // Act
    Long actualSaveAllergyHistoryResult = commonNurseServiceImpl.saveAllergyHistory(benAllergyHistory2);

    // Assert
    verify(benAllergyHistoryRepo).saveAll(isA(Iterable.class));
    assertEquals(1L, actualSaveAllergyHistoryResult.longValue());
  }

  @Test
  void testSaveAllergyHistory3() {
    // Arrange
    BenAllergyHistory benAllergyHistory = mock(BenAllergyHistory.class);
    when(benAllergyHistory.getBenAllergicHistory()).thenReturn(new ArrayList<>());
    doNothing().when(benAllergyHistory).setAllergicList(Mockito.<List<Map<String, Object>>>any());
    doNothing().when(benAllergyHistory).setAllergicReactionType(Mockito.<String>any());
    doNothing().when(benAllergyHistory).setAllergicReactionTypeID(Mockito.<String>any());
    doNothing().when(benAllergyHistory).setAllergyName(Mockito.<String>any());
    doNothing().when(benAllergyHistory).setAllergyStatus(Mockito.<String>any());
    doNothing().when(benAllergyHistory).setAllergyType(Mockito.<String>any());
    doNothing().when(benAllergyHistory).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benAllergyHistory).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benAllergyHistory).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benAllergyHistory).setCreatedBy(Mockito.<String>any());
    doNothing().when(benAllergyHistory).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benAllergyHistory).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benAllergyHistory).setID(Mockito.<Long>any());
    doNothing().when(benAllergyHistory).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benAllergyHistory).setModifiedBy(Mockito.<String>any());
    doNothing().when(benAllergyHistory).setOtherAllergicReaction(Mockito.<String>any());
    doNothing().when(benAllergyHistory).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benAllergyHistory).setProcessed(Mockito.<String>any());
    doNothing().when(benAllergyHistory).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benAllergyHistory).setRemarks(Mockito.<String>any());
    doNothing().when(benAllergyHistory).setReservedForChange(Mockito.<String>any());
    doNothing().when(benAllergyHistory).setSnomedCode(Mockito.<String>any());
    doNothing().when(benAllergyHistory).setSnomedTerm(Mockito.<String>any());
    doNothing().when(benAllergyHistory).setSyncedBy(Mockito.<String>any());
    doNothing().when(benAllergyHistory).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benAllergyHistory).setTypeOfAllergicReactions(Mockito.<List<Map<String, String>>>any());
    doNothing().when(benAllergyHistory).setVanID(Mockito.<Integer>any());
    doNothing().when(benAllergyHistory).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benAllergyHistory).setVehicalNo(Mockito.<String>any());
    doNothing().when(benAllergyHistory).setVisitCode(Mockito.<Long>any());
    benAllergyHistory.setAllergicList(new ArrayList<>());
    benAllergyHistory.setAllergicReactionType("Allergic Reaction Type");
    benAllergyHistory.setAllergicReactionTypeID("Allergic Reaction Type ID");
    benAllergyHistory.setAllergyName("Allergy Name");
    benAllergyHistory.setAllergyStatus("Allergy Status");
    benAllergyHistory.setAllergyType("Allergy Type");
    benAllergyHistory.setBenVisitID(1L);
    benAllergyHistory.setBeneficiaryRegID(1L);
    benAllergyHistory.setCaptureDate(mock(Date.class));
    benAllergyHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benAllergyHistory.setCreatedDate(mock(Timestamp.class));
    benAllergyHistory.setDeleted(true);
    benAllergyHistory.setID(1L);
    benAllergyHistory.setLastModDate(mock(Timestamp.class));
    benAllergyHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benAllergyHistory.setOtherAllergicReaction("Other Allergic Reaction");
    benAllergyHistory.setParkingPlaceID(1);
    benAllergyHistory.setProcessed("Processed");
    benAllergyHistory.setProviderServiceMapID(1);
    benAllergyHistory.setRemarks("Remarks");
    benAllergyHistory.setReservedForChange("Reserved For Change");
    benAllergyHistory.setSnomedCode("Snomed Code");
    benAllergyHistory.setSnomedTerm("Snomed Term");
    benAllergyHistory.setSyncedBy("Synced By");
    benAllergyHistory.setSyncedDate(mock(Timestamp.class));
    benAllergyHistory.setTypeOfAllergicReactions(new ArrayList<>());
    benAllergyHistory.setVanID(1);
    benAllergyHistory.setVanSerialNo(1L);
    benAllergyHistory.setVehicalNo("Vehical No");
    benAllergyHistory.setVisitCode(1L);

    // Act
    Long actualSaveAllergyHistoryResult = commonNurseServiceImpl.saveAllergyHistory(benAllergyHistory);

    // Assert
    verify(benAllergyHistory).getBenAllergicHistory();
    verify(benAllergyHistory).setAllergicList(isA(List.class));
    verify(benAllergyHistory).setAllergicReactionType(eq("Allergic Reaction Type"));
    verify(benAllergyHistory).setAllergicReactionTypeID(eq("Allergic Reaction Type ID"));
    verify(benAllergyHistory).setAllergyName(eq("Allergy Name"));
    verify(benAllergyHistory).setAllergyStatus(eq("Allergy Status"));
    verify(benAllergyHistory).setAllergyType(eq("Allergy Type"));
    verify(benAllergyHistory).setBenVisitID(isA(Long.class));
    verify(benAllergyHistory).setBeneficiaryRegID(isA(Long.class));
    verify(benAllergyHistory).setCaptureDate(isA(Date.class));
    verify(benAllergyHistory).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benAllergyHistory).setCreatedDate(isA(Timestamp.class));
    verify(benAllergyHistory).setDeleted(isA(Boolean.class));
    verify(benAllergyHistory).setID(isA(Long.class));
    verify(benAllergyHistory).setLastModDate(isA(Timestamp.class));
    verify(benAllergyHistory).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benAllergyHistory).setOtherAllergicReaction(eq("Other Allergic Reaction"));
    verify(benAllergyHistory).setParkingPlaceID(isA(Integer.class));
    verify(benAllergyHistory).setProcessed(eq("Processed"));
    verify(benAllergyHistory).setProviderServiceMapID(isA(Integer.class));
    verify(benAllergyHistory).setRemarks(eq("Remarks"));
    verify(benAllergyHistory).setReservedForChange(eq("Reserved For Change"));
    verify(benAllergyHistory).setSnomedCode(eq("Snomed Code"));
    verify(benAllergyHistory).setSnomedTerm(eq("Snomed Term"));
    verify(benAllergyHistory).setSyncedBy(eq("Synced By"));
    verify(benAllergyHistory).setSyncedDate(isA(Timestamp.class));
    verify(benAllergyHistory).setTypeOfAllergicReactions(isA(List.class));
    verify(benAllergyHistory).setVanID(isA(Integer.class));
    verify(benAllergyHistory).setVanSerialNo(isA(Long.class));
    verify(benAllergyHistory).setVehicalNo(eq("Vehical No"));
    verify(benAllergyHistory).setVisitCode(isA(Long.class));
    assertEquals(1L, actualSaveAllergyHistoryResult.longValue());
  }

  @Test
  void testSaveChildOptionalVaccineDetail() {
    // Arrange
    WrapperChildOptionalVaccineDetail wrapperChildVaccineDetail = new WrapperChildOptionalVaccineDetail();
    wrapperChildVaccineDetail.setBenVisitID(1L);
    wrapperChildVaccineDetail.setBeneficiaryRegID(1L);
    wrapperChildVaccineDetail.setChildOptionalVaccineList(new ArrayList<>());
    wrapperChildVaccineDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperChildVaccineDetail.setParkingPlaceID(1);
    wrapperChildVaccineDetail.setProviderServiceMapID(1);
    wrapperChildVaccineDetail.setVanID(1);
    wrapperChildVaccineDetail.setVisitCode(1L);

    // Act and Assert
    assertEquals(1L, commonNurseServiceImpl.saveChildOptionalVaccineDetail(wrapperChildVaccineDetail).longValue());
  }

  @Test
  void testSaveChildOptionalVaccineDetail2() {
    // Arrange
    WrapperChildOptionalVaccineDetail wrapperChildVaccineDetail = mock(WrapperChildOptionalVaccineDetail.class);
    when(wrapperChildVaccineDetail.getChildOptionalVaccineDetails()).thenReturn(new ArrayList<>());
    doNothing().when(wrapperChildVaccineDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(wrapperChildVaccineDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(wrapperChildVaccineDetail)
        .setChildOptionalVaccineList(Mockito.<ArrayList<ChildOptionalVaccineDetail>>any());
    doNothing().when(wrapperChildVaccineDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(wrapperChildVaccineDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(wrapperChildVaccineDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(wrapperChildVaccineDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(wrapperChildVaccineDetail).setVisitCode(Mockito.<Long>any());
    wrapperChildVaccineDetail.setBenVisitID(1L);
    wrapperChildVaccineDetail.setBeneficiaryRegID(1L);
    wrapperChildVaccineDetail.setChildOptionalVaccineList(new ArrayList<>());
    wrapperChildVaccineDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperChildVaccineDetail.setParkingPlaceID(1);
    wrapperChildVaccineDetail.setProviderServiceMapID(1);
    wrapperChildVaccineDetail.setVanID(1);
    wrapperChildVaccineDetail.setVisitCode(1L);

    // Act
    Long actualSaveChildOptionalVaccineDetailResult = commonNurseServiceImpl
        .saveChildOptionalVaccineDetail(wrapperChildVaccineDetail);

    // Assert
    verify(wrapperChildVaccineDetail).getChildOptionalVaccineDetails();
    verify(wrapperChildVaccineDetail).setBenVisitID(isA(Long.class));
    verify(wrapperChildVaccineDetail).setBeneficiaryRegID(isA(Long.class));
    verify(wrapperChildVaccineDetail).setChildOptionalVaccineList(isA(ArrayList.class));
    verify(wrapperChildVaccineDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(wrapperChildVaccineDetail).setParkingPlaceID(isA(Integer.class));
    verify(wrapperChildVaccineDetail).setProviderServiceMapID(isA(Integer.class));
    verify(wrapperChildVaccineDetail).setVanID(isA(Integer.class));
    verify(wrapperChildVaccineDetail).setVisitCode(isA(Long.class));
    assertEquals(1L, actualSaveChildOptionalVaccineDetailResult.longValue());
  }

  @Test
  void testSaveChildOptionalVaccineDetail3() {
    // Arrange
    when(childOptionalVaccineDetailRepo.saveAll(Mockito.<Iterable<ChildOptionalVaccineDetail>>any()))
        .thenReturn(new ArrayList<>());

    ChildOptionalVaccineDetail childOptionalVaccineDetail = new ChildOptionalVaccineDetail();
    childOptionalVaccineDetail.setActualReceivingAge("Actual Receiving Age");
    childOptionalVaccineDetail.setAgeUnit("Age Unit");
    childOptionalVaccineDetail.setAgeUnitID(1);
    childOptionalVaccineDetail.setBenVisitID(1L);
    childOptionalVaccineDetail.setBeneficiaryRegID(1L);
    childOptionalVaccineDetail.setCaptureDate(mock(Date.class));
    childOptionalVaccineDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    childOptionalVaccineDetail.setCreatedDate(mock(Timestamp.class));
    childOptionalVaccineDetail.setDefaultReceivingAge("Default Receiving Age");
    childOptionalVaccineDetail.setDeleted(true);
    childOptionalVaccineDetail.setID(1L);
    childOptionalVaccineDetail.setLastModDate(mock(Timestamp.class));
    childOptionalVaccineDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    childOptionalVaccineDetail.setOtherVaccineName("Other Vaccine Name");
    childOptionalVaccineDetail.setParkingPlaceID(1);
    childOptionalVaccineDetail.setProcessed("Processed");
    childOptionalVaccineDetail.setProviderServiceMapID(1);
    childOptionalVaccineDetail.setReceivedDate(mock(Timestamp.class));
    childOptionalVaccineDetail.setReceivedFacilityName("Received Facility Name");
    childOptionalVaccineDetail.setReservedForChange("Reserved For Change");
    childOptionalVaccineDetail.setSctCode("Sct Code");
    childOptionalVaccineDetail.setSctTerm("Sct Term");
    childOptionalVaccineDetail.setStatus("Status");
    childOptionalVaccineDetail.setSyncedBy("Synced By");
    childOptionalVaccineDetail.setSyncedDate(mock(Timestamp.class));
    childOptionalVaccineDetail.setVaccineName("Vaccine Name");
    childOptionalVaccineDetail.setVanID(1);
    childOptionalVaccineDetail.setVanSerialNo(1L);
    childOptionalVaccineDetail.setVehicalNo("Vehical No");
    childOptionalVaccineDetail.setVisitCode(1L);

    ArrayList<ChildOptionalVaccineDetail> childOptionalVaccineDetailList = new ArrayList<>();
    childOptionalVaccineDetailList.add(childOptionalVaccineDetail);
    WrapperChildOptionalVaccineDetail wrapperChildVaccineDetail = mock(WrapperChildOptionalVaccineDetail.class);
    when(wrapperChildVaccineDetail.getChildOptionalVaccineDetails()).thenReturn(childOptionalVaccineDetailList);
    doNothing().when(wrapperChildVaccineDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(wrapperChildVaccineDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(wrapperChildVaccineDetail)
        .setChildOptionalVaccineList(Mockito.<ArrayList<ChildOptionalVaccineDetail>>any());
    doNothing().when(wrapperChildVaccineDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(wrapperChildVaccineDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(wrapperChildVaccineDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(wrapperChildVaccineDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(wrapperChildVaccineDetail).setVisitCode(Mockito.<Long>any());
    wrapperChildVaccineDetail.setBenVisitID(1L);
    wrapperChildVaccineDetail.setBeneficiaryRegID(1L);
    wrapperChildVaccineDetail.setChildOptionalVaccineList(new ArrayList<>());
    wrapperChildVaccineDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperChildVaccineDetail.setParkingPlaceID(1);
    wrapperChildVaccineDetail.setProviderServiceMapID(1);
    wrapperChildVaccineDetail.setVanID(1);
    wrapperChildVaccineDetail.setVisitCode(1L);

    // Act
    Long actualSaveChildOptionalVaccineDetailResult = commonNurseServiceImpl
        .saveChildOptionalVaccineDetail(wrapperChildVaccineDetail);

    // Assert
    verify(wrapperChildVaccineDetail).getChildOptionalVaccineDetails();
    verify(wrapperChildVaccineDetail).setBenVisitID(isA(Long.class));
    verify(wrapperChildVaccineDetail).setBeneficiaryRegID(isA(Long.class));
    verify(wrapperChildVaccineDetail).setChildOptionalVaccineList(isA(ArrayList.class));
    verify(wrapperChildVaccineDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(wrapperChildVaccineDetail).setParkingPlaceID(isA(Integer.class));
    verify(wrapperChildVaccineDetail).setProviderServiceMapID(isA(Integer.class));
    verify(wrapperChildVaccineDetail).setVanID(isA(Integer.class));
    verify(wrapperChildVaccineDetail).setVisitCode(isA(Long.class));
    verify(childOptionalVaccineDetailRepo).saveAll(isA(Iterable.class));
    assertNull(actualSaveChildOptionalVaccineDetailResult);
  }

  @Test
  void testSaveChildOptionalVaccineDetail4() {
    // Arrange
    ChildOptionalVaccineDetail childOptionalVaccineDetail = new ChildOptionalVaccineDetail();
    childOptionalVaccineDetail.setActualReceivingAge("Actual Receiving Age");
    childOptionalVaccineDetail.setAgeUnit("Age Unit");
    childOptionalVaccineDetail.setAgeUnitID(1);
    childOptionalVaccineDetail.setBenVisitID(1L);
    childOptionalVaccineDetail.setBeneficiaryRegID(1L);
    childOptionalVaccineDetail.setCaptureDate(mock(Date.class));
    childOptionalVaccineDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    childOptionalVaccineDetail.setCreatedDate(mock(Timestamp.class));
    childOptionalVaccineDetail.setDefaultReceivingAge("Default Receiving Age");
    childOptionalVaccineDetail.setDeleted(true);
    childOptionalVaccineDetail.setID(1L);
    childOptionalVaccineDetail.setLastModDate(mock(Timestamp.class));
    childOptionalVaccineDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    childOptionalVaccineDetail.setOtherVaccineName("Other Vaccine Name");
    childOptionalVaccineDetail.setParkingPlaceID(1);
    childOptionalVaccineDetail.setProcessed("Processed");
    childOptionalVaccineDetail.setProviderServiceMapID(1);
    childOptionalVaccineDetail.setReceivedDate(mock(Timestamp.class));
    childOptionalVaccineDetail.setReceivedFacilityName("Received Facility Name");
    childOptionalVaccineDetail.setReservedForChange("Reserved For Change");
    childOptionalVaccineDetail.setSctCode("Sct Code");
    childOptionalVaccineDetail.setSctTerm("Sct Term");
    childOptionalVaccineDetail.setStatus("Status");
    childOptionalVaccineDetail.setSyncedBy("Synced By");
    childOptionalVaccineDetail.setSyncedDate(mock(Timestamp.class));
    childOptionalVaccineDetail.setVaccineName("Vaccine Name");
    childOptionalVaccineDetail.setVanID(1);
    childOptionalVaccineDetail.setVanSerialNo(1L);
    childOptionalVaccineDetail.setVehicalNo("Vehical No");
    childOptionalVaccineDetail.setVisitCode(1L);

    ArrayList<ChildOptionalVaccineDetail> childOptionalVaccineDetailList = new ArrayList<>();
    childOptionalVaccineDetailList.add(childOptionalVaccineDetail);
    when(childOptionalVaccineDetailRepo.saveAll(Mockito.<Iterable<ChildOptionalVaccineDetail>>any()))
        .thenReturn(childOptionalVaccineDetailList);

    ChildOptionalVaccineDetail childOptionalVaccineDetail2 = new ChildOptionalVaccineDetail();
    childOptionalVaccineDetail2.setActualReceivingAge("Actual Receiving Age");
    childOptionalVaccineDetail2.setAgeUnit("Age Unit");
    childOptionalVaccineDetail2.setAgeUnitID(1);
    childOptionalVaccineDetail2.setBenVisitID(1L);
    childOptionalVaccineDetail2.setBeneficiaryRegID(1L);
    childOptionalVaccineDetail2.setCaptureDate(mock(Date.class));
    childOptionalVaccineDetail2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    childOptionalVaccineDetail2.setCreatedDate(mock(Timestamp.class));
    childOptionalVaccineDetail2.setDefaultReceivingAge("Default Receiving Age");
    childOptionalVaccineDetail2.setDeleted(true);
    childOptionalVaccineDetail2.setID(1L);
    childOptionalVaccineDetail2.setLastModDate(mock(Timestamp.class));
    childOptionalVaccineDetail2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    childOptionalVaccineDetail2.setOtherVaccineName("Other Vaccine Name");
    childOptionalVaccineDetail2.setParkingPlaceID(1);
    childOptionalVaccineDetail2.setProcessed("Processed");
    childOptionalVaccineDetail2.setProviderServiceMapID(1);
    childOptionalVaccineDetail2.setReceivedDate(mock(Timestamp.class));
    childOptionalVaccineDetail2.setReceivedFacilityName("Received Facility Name");
    childOptionalVaccineDetail2.setReservedForChange("Reserved For Change");
    childOptionalVaccineDetail2.setSctCode("Sct Code");
    childOptionalVaccineDetail2.setSctTerm("Sct Term");
    childOptionalVaccineDetail2.setStatus("Status");
    childOptionalVaccineDetail2.setSyncedBy("Synced By");
    childOptionalVaccineDetail2.setSyncedDate(mock(Timestamp.class));
    childOptionalVaccineDetail2.setVaccineName("Vaccine Name");
    childOptionalVaccineDetail2.setVanID(1);
    childOptionalVaccineDetail2.setVanSerialNo(1L);
    childOptionalVaccineDetail2.setVehicalNo("Vehical No");
    childOptionalVaccineDetail2.setVisitCode(1L);

    ArrayList<ChildOptionalVaccineDetail> childOptionalVaccineDetailList2 = new ArrayList<>();
    childOptionalVaccineDetailList2.add(childOptionalVaccineDetail2);
    WrapperChildOptionalVaccineDetail wrapperChildVaccineDetail = mock(WrapperChildOptionalVaccineDetail.class);
    when(wrapperChildVaccineDetail.getChildOptionalVaccineDetails()).thenReturn(childOptionalVaccineDetailList2);
    doNothing().when(wrapperChildVaccineDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(wrapperChildVaccineDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(wrapperChildVaccineDetail)
        .setChildOptionalVaccineList(Mockito.<ArrayList<ChildOptionalVaccineDetail>>any());
    doNothing().when(wrapperChildVaccineDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(wrapperChildVaccineDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(wrapperChildVaccineDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(wrapperChildVaccineDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(wrapperChildVaccineDetail).setVisitCode(Mockito.<Long>any());
    wrapperChildVaccineDetail.setBenVisitID(1L);
    wrapperChildVaccineDetail.setBeneficiaryRegID(1L);
    wrapperChildVaccineDetail.setChildOptionalVaccineList(new ArrayList<>());
    wrapperChildVaccineDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperChildVaccineDetail.setParkingPlaceID(1);
    wrapperChildVaccineDetail.setProviderServiceMapID(1);
    wrapperChildVaccineDetail.setVanID(1);
    wrapperChildVaccineDetail.setVisitCode(1L);

    // Act
    Long actualSaveChildOptionalVaccineDetailResult = commonNurseServiceImpl
        .saveChildOptionalVaccineDetail(wrapperChildVaccineDetail);

    // Assert
    verify(wrapperChildVaccineDetail).getChildOptionalVaccineDetails();
    verify(wrapperChildVaccineDetail).setBenVisitID(isA(Long.class));
    verify(wrapperChildVaccineDetail).setBeneficiaryRegID(isA(Long.class));
    verify(wrapperChildVaccineDetail).setChildOptionalVaccineList(isA(ArrayList.class));
    verify(wrapperChildVaccineDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(wrapperChildVaccineDetail).setParkingPlaceID(isA(Integer.class));
    verify(wrapperChildVaccineDetail).setProviderServiceMapID(isA(Integer.class));
    verify(wrapperChildVaccineDetail).setVanID(isA(Integer.class));
    verify(wrapperChildVaccineDetail).setVisitCode(isA(Long.class));
    verify(childOptionalVaccineDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(1L, actualSaveChildOptionalVaccineDetailResult.longValue());
  }

  @Test
  void testSaveImmunizationHistory() {
    // Arrange
    WrapperImmunizationHistory wrapperImmunizationHistory = new WrapperImmunizationHistory();
    wrapperImmunizationHistory.setBenVisitID(1L);
    wrapperImmunizationHistory.setBeneficiaryRegID(1L);
    wrapperImmunizationHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperImmunizationHistory.setImmunizationList(new ArrayList<>());
    wrapperImmunizationHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    wrapperImmunizationHistory.setParkingPlaceID(1);
    wrapperImmunizationHistory.setProviderServiceMapID(1);
    wrapperImmunizationHistory.setVaccinationReceivedAt("Vaccination Received At");
    wrapperImmunizationHistory.setVanID(1);
    wrapperImmunizationHistory.setVisitCode(1L);

    // Act and Assert
    assertEquals(1L, commonNurseServiceImpl.saveImmunizationHistory(wrapperImmunizationHistory).longValue());
  }

  @Test
  void testSaveImmunizationHistory2() {
    // Arrange
    WrapperImmunizationHistory wrapperImmunizationHistory = mock(WrapperImmunizationHistory.class);
    when(wrapperImmunizationHistory.getBenChildVaccineDetails()).thenReturn(new ArrayList<>());
    doNothing().when(wrapperImmunizationHistory).setBenVisitID(Mockito.<Long>any());
    doNothing().when(wrapperImmunizationHistory).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(wrapperImmunizationHistory).setCreatedBy(Mockito.<String>any());
    doNothing().when(wrapperImmunizationHistory).setImmunizationList(Mockito.<ArrayList<ChildVaccineDetail1>>any());
    doNothing().when(wrapperImmunizationHistory).setModifiedBy(Mockito.<String>any());
    doNothing().when(wrapperImmunizationHistory).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(wrapperImmunizationHistory).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(wrapperImmunizationHistory).setVaccinationReceivedAt(Mockito.<String>any());
    doNothing().when(wrapperImmunizationHistory).setVanID(Mockito.<Integer>any());
    doNothing().when(wrapperImmunizationHistory).setVisitCode(Mockito.<Long>any());
    wrapperImmunizationHistory.setBenVisitID(1L);
    wrapperImmunizationHistory.setBeneficiaryRegID(1L);
    wrapperImmunizationHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperImmunizationHistory.setImmunizationList(new ArrayList<>());
    wrapperImmunizationHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    wrapperImmunizationHistory.setParkingPlaceID(1);
    wrapperImmunizationHistory.setProviderServiceMapID(1);
    wrapperImmunizationHistory.setVaccinationReceivedAt("Vaccination Received At");
    wrapperImmunizationHistory.setVanID(1);
    wrapperImmunizationHistory.setVisitCode(1L);

    // Act
    Long actualSaveImmunizationHistoryResult = commonNurseServiceImpl
        .saveImmunizationHistory(wrapperImmunizationHistory);

    // Assert
    verify(wrapperImmunizationHistory).getBenChildVaccineDetails();
    verify(wrapperImmunizationHistory).setBenVisitID(isA(Long.class));
    verify(wrapperImmunizationHistory).setBeneficiaryRegID(isA(Long.class));
    verify(wrapperImmunizationHistory).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(wrapperImmunizationHistory).setImmunizationList(isA(ArrayList.class));
    verify(wrapperImmunizationHistory).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(wrapperImmunizationHistory).setParkingPlaceID(isA(Integer.class));
    verify(wrapperImmunizationHistory).setProviderServiceMapID(isA(Integer.class));
    verify(wrapperImmunizationHistory).setVaccinationReceivedAt(eq("Vaccination Received At"));
    verify(wrapperImmunizationHistory).setVanID(isA(Integer.class));
    verify(wrapperImmunizationHistory).setVisitCode(isA(Long.class));
    assertEquals(1L, actualSaveImmunizationHistoryResult.longValue());
  }

  @Test
  void testSaveImmunizationHistory3() {
    // Arrange
    when(childVaccineDetail1Repo.saveAll(Mockito.<Iterable<ChildVaccineDetail1>>any())).thenReturn(new ArrayList<>());

    ChildVaccineDetail1 childVaccineDetail1 = new ChildVaccineDetail1();
    childVaccineDetail1.setActualReceivingAge("Actual Receiving Age");
    childVaccineDetail1.setBenVisitID(1L);
    childVaccineDetail1.setBeneficiaryRegID(1L);
    childVaccineDetail1.setCaptureDate(mock(Date.class));
    childVaccineDetail1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    childVaccineDetail1.setCreatedDate(mock(Timestamp.class));
    childVaccineDetail1.setDefaultReceivingAge("Default Receiving Age");
    childVaccineDetail1.setDeleted(true);
    childVaccineDetail1.setID(1L);
    childVaccineDetail1.setLastModDate(mock(Timestamp.class));
    childVaccineDetail1.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    childVaccineDetail1.setParkingPlaceID(1);
    childVaccineDetail1.setProcessed("Processed");
    childVaccineDetail1.setProviderServiceMapID(1);
    childVaccineDetail1.setReceivedDate(mock(Timestamp.class));
    childVaccineDetail1.setReceivedFacilityName("Received Facility Name");
    childVaccineDetail1.setReservedForChange("Reserved For Change");
    childVaccineDetail1.setSctCode("Sct Code");
    childVaccineDetail1.setSctTerm("Sct Term");
    childVaccineDetail1.setStatus("Status");
    childVaccineDetail1.setSyncedBy("Synced By");
    childVaccineDetail1.setSyncedDate(mock(Timestamp.class));
    childVaccineDetail1.setVaccinationReceivedAt("Vaccination Received At");
    childVaccineDetail1.setVaccineName("Vaccine Name");
    childVaccineDetail1.setVaccineNameList(new ArrayList<>());
    childVaccineDetail1.setVaccines(new ArrayList<>());
    childVaccineDetail1.setVanID(1);
    childVaccineDetail1.setVanSerialNo(1L);
    childVaccineDetail1.setVehicalNo("Vehical No");
    childVaccineDetail1.setVisitCode(1L);

    ArrayList<ChildVaccineDetail1> childVaccineDetail1List = new ArrayList<>();
    childVaccineDetail1List.add(childVaccineDetail1);
    WrapperImmunizationHistory wrapperImmunizationHistory = mock(WrapperImmunizationHistory.class);
    when(wrapperImmunizationHistory.getBenChildVaccineDetails()).thenReturn(childVaccineDetail1List);
    doNothing().when(wrapperImmunizationHistory).setBenVisitID(Mockito.<Long>any());
    doNothing().when(wrapperImmunizationHistory).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(wrapperImmunizationHistory).setCreatedBy(Mockito.<String>any());
    doNothing().when(wrapperImmunizationHistory).setImmunizationList(Mockito.<ArrayList<ChildVaccineDetail1>>any());
    doNothing().when(wrapperImmunizationHistory).setModifiedBy(Mockito.<String>any());
    doNothing().when(wrapperImmunizationHistory).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(wrapperImmunizationHistory).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(wrapperImmunizationHistory).setVaccinationReceivedAt(Mockito.<String>any());
    doNothing().when(wrapperImmunizationHistory).setVanID(Mockito.<Integer>any());
    doNothing().when(wrapperImmunizationHistory).setVisitCode(Mockito.<Long>any());
    wrapperImmunizationHistory.setBenVisitID(1L);
    wrapperImmunizationHistory.setBeneficiaryRegID(1L);
    wrapperImmunizationHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    wrapperImmunizationHistory.setImmunizationList(new ArrayList<>());
    wrapperImmunizationHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    wrapperImmunizationHistory.setParkingPlaceID(1);
    wrapperImmunizationHistory.setProviderServiceMapID(1);
    wrapperImmunizationHistory.setVaccinationReceivedAt("Vaccination Received At");
    wrapperImmunizationHistory.setVanID(1);
    wrapperImmunizationHistory.setVisitCode(1L);

    // Act
    Long actualSaveImmunizationHistoryResult = commonNurseServiceImpl
        .saveImmunizationHistory(wrapperImmunizationHistory);

    // Assert
    verify(wrapperImmunizationHistory).getBenChildVaccineDetails();
    verify(wrapperImmunizationHistory).setBenVisitID(isA(Long.class));
    verify(wrapperImmunizationHistory).setBeneficiaryRegID(isA(Long.class));
    verify(wrapperImmunizationHistory).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(wrapperImmunizationHistory).setImmunizationList(isA(ArrayList.class));
    verify(wrapperImmunizationHistory).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(wrapperImmunizationHistory).setParkingPlaceID(isA(Integer.class));
    verify(wrapperImmunizationHistory).setProviderServiceMapID(isA(Integer.class));
    verify(wrapperImmunizationHistory).setVaccinationReceivedAt(eq("Vaccination Received At"));
    verify(wrapperImmunizationHistory).setVanID(isA(Integer.class));
    verify(wrapperImmunizationHistory).setVisitCode(isA(Long.class));
    verify(childVaccineDetail1Repo).saveAll(isA(Iterable.class));
    assertEquals(1L, actualSaveImmunizationHistoryResult.longValue());
  }

  @Test
  void testSaveBeneficiaryPhysicalAnthropometryDetails() {
    // Arrange
    BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
    benAnthropometryDetail.setBenVisitID(1L);
    benAnthropometryDetail.setBeneficiaryRegID(1L);
    benAnthropometryDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benAnthropometryDetail.setCreatedDate(mock(Timestamp.class));
    benAnthropometryDetail.setDeleted(true);
    benAnthropometryDetail.setHeadCircumference_cm(10.0d);
    benAnthropometryDetail.setHeight_cm(10.0d);
    benAnthropometryDetail.setHipCircumference_cm(10.0d);
    benAnthropometryDetail.setID(1L);
    benAnthropometryDetail.setLastModDate(mock(Timestamp.class));
    benAnthropometryDetail.setMidUpperArmCircumference_MUAC_cm(10.0d);
    benAnthropometryDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benAnthropometryDetail.setParkingPlaceID(1);
    benAnthropometryDetail.setProcessed("Processed");
    benAnthropometryDetail.setProviderServiceMapID(1);
    benAnthropometryDetail.setReservedForChange("Reserved For Change");
    benAnthropometryDetail.setSyncedBy("Synced By");
    benAnthropometryDetail.setSyncedDate(mock(Timestamp.class));
    benAnthropometryDetail.setVanID(1);
    benAnthropometryDetail.setVanSerialNo(1L);
    benAnthropometryDetail.setVehicalNo("Vehical No");
    benAnthropometryDetail.setVisitCode(1L);
    benAnthropometryDetail.setWaistCircumference_cm(10.0d);
    benAnthropometryDetail.setWaistHipRatio(10.0d);
    benAnthropometryDetail.setWeight_Kg(10.0d);
    benAnthropometryDetail.setbMI(10.0d);
    when(benAnthropometryRepo.save(Mockito.<BenAnthropometryDetail>any())).thenReturn(benAnthropometryDetail);

    BenAnthropometryDetail benAnthropometryDetail2 = new BenAnthropometryDetail();
    benAnthropometryDetail2.setBenVisitID(1L);
    benAnthropometryDetail2.setBeneficiaryRegID(1L);
    benAnthropometryDetail2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benAnthropometryDetail2.setCreatedDate(mock(Timestamp.class));
    benAnthropometryDetail2.setDeleted(true);
    benAnthropometryDetail2.setHeadCircumference_cm(10.0d);
    benAnthropometryDetail2.setHeight_cm(10.0d);
    benAnthropometryDetail2.setHipCircumference_cm(10.0d);
    benAnthropometryDetail2.setID(1L);
    benAnthropometryDetail2.setLastModDate(mock(Timestamp.class));
    benAnthropometryDetail2.setMidUpperArmCircumference_MUAC_cm(10.0d);
    benAnthropometryDetail2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benAnthropometryDetail2.setParkingPlaceID(1);
    benAnthropometryDetail2.setProcessed("Processed");
    benAnthropometryDetail2.setProviderServiceMapID(1);
    benAnthropometryDetail2.setReservedForChange("Reserved For Change");
    benAnthropometryDetail2.setSyncedBy("Synced By");
    benAnthropometryDetail2.setSyncedDate(mock(Timestamp.class));
    benAnthropometryDetail2.setVanID(1);
    benAnthropometryDetail2.setVanSerialNo(1L);
    benAnthropometryDetail2.setVehicalNo("Vehical No");
    benAnthropometryDetail2.setVisitCode(1L);
    benAnthropometryDetail2.setWaistCircumference_cm(10.0d);
    benAnthropometryDetail2.setWaistHipRatio(10.0d);
    benAnthropometryDetail2.setWeight_Kg(10.0d);
    benAnthropometryDetail2.setbMI(10.0d);

    // Act
    Long actualSaveBeneficiaryPhysicalAnthropometryDetailsResult = commonNurseServiceImpl
        .saveBeneficiaryPhysicalAnthropometryDetails(benAnthropometryDetail2);

    // Assert
    verify(benAnthropometryRepo).save(isA(BenAnthropometryDetail.class));
    assertEquals(1L, actualSaveBeneficiaryPhysicalAnthropometryDetailsResult.longValue());
  }

  @Test
  void testSaveBeneficiaryPhysicalAnthropometryDetails2() {
    // Arrange
    BenAnthropometryDetail benAnthropometryDetail = mock(BenAnthropometryDetail.class);
    when(benAnthropometryDetail.getID()).thenReturn(1L);
    doNothing().when(benAnthropometryDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benAnthropometryDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benAnthropometryDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(benAnthropometryDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benAnthropometryDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benAnthropometryDetail).setHeadCircumference_cm(Mockito.<Double>any());
    doNothing().when(benAnthropometryDetail).setHeight_cm(Mockito.<Double>any());
    doNothing().when(benAnthropometryDetail).setHipCircumference_cm(Mockito.<Double>any());
    doNothing().when(benAnthropometryDetail).setID(Mockito.<Long>any());
    doNothing().when(benAnthropometryDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benAnthropometryDetail).setMidUpperArmCircumference_MUAC_cm(Mockito.<Double>any());
    doNothing().when(benAnthropometryDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(benAnthropometryDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benAnthropometryDetail).setProcessed(Mockito.<String>any());
    doNothing().when(benAnthropometryDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benAnthropometryDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(benAnthropometryDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(benAnthropometryDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benAnthropometryDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(benAnthropometryDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benAnthropometryDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(benAnthropometryDetail).setVisitCode(Mockito.<Long>any());
    doNothing().when(benAnthropometryDetail).setWaistCircumference_cm(Mockito.<Double>any());
    doNothing().when(benAnthropometryDetail).setWaistHipRatio(Mockito.<Double>any());
    doNothing().when(benAnthropometryDetail).setWeight_Kg(Mockito.<Double>any());
    doNothing().when(benAnthropometryDetail).setbMI(Mockito.<Double>any());
    benAnthropometryDetail.setBenVisitID(1L);
    benAnthropometryDetail.setBeneficiaryRegID(1L);
    benAnthropometryDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benAnthropometryDetail.setCreatedDate(mock(Timestamp.class));
    benAnthropometryDetail.setDeleted(true);
    benAnthropometryDetail.setHeadCircumference_cm(10.0d);
    benAnthropometryDetail.setHeight_cm(10.0d);
    benAnthropometryDetail.setHipCircumference_cm(10.0d);
    benAnthropometryDetail.setID(1L);
    benAnthropometryDetail.setLastModDate(mock(Timestamp.class));
    benAnthropometryDetail.setMidUpperArmCircumference_MUAC_cm(10.0d);
    benAnthropometryDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benAnthropometryDetail.setParkingPlaceID(1);
    benAnthropometryDetail.setProcessed("Processed");
    benAnthropometryDetail.setProviderServiceMapID(1);
    benAnthropometryDetail.setReservedForChange("Reserved For Change");
    benAnthropometryDetail.setSyncedBy("Synced By");
    benAnthropometryDetail.setSyncedDate(mock(Timestamp.class));
    benAnthropometryDetail.setVanID(1);
    benAnthropometryDetail.setVanSerialNo(1L);
    benAnthropometryDetail.setVehicalNo("Vehical No");
    benAnthropometryDetail.setVisitCode(1L);
    benAnthropometryDetail.setWaistCircumference_cm(10.0d);
    benAnthropometryDetail.setWaistHipRatio(10.0d);
    benAnthropometryDetail.setWeight_Kg(10.0d);
    benAnthropometryDetail.setbMI(10.0d);
    when(benAnthropometryRepo.save(Mockito.<BenAnthropometryDetail>any())).thenReturn(benAnthropometryDetail);

    BenAnthropometryDetail benAnthropometryDetail2 = new BenAnthropometryDetail();
    benAnthropometryDetail2.setBenVisitID(1L);
    benAnthropometryDetail2.setBeneficiaryRegID(1L);
    benAnthropometryDetail2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benAnthropometryDetail2.setCreatedDate(mock(Timestamp.class));
    benAnthropometryDetail2.setDeleted(true);
    benAnthropometryDetail2.setHeadCircumference_cm(10.0d);
    benAnthropometryDetail2.setHeight_cm(10.0d);
    benAnthropometryDetail2.setHipCircumference_cm(10.0d);
    benAnthropometryDetail2.setID(1L);
    benAnthropometryDetail2.setLastModDate(mock(Timestamp.class));
    benAnthropometryDetail2.setMidUpperArmCircumference_MUAC_cm(10.0d);
    benAnthropometryDetail2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benAnthropometryDetail2.setParkingPlaceID(1);
    benAnthropometryDetail2.setProcessed("Processed");
    benAnthropometryDetail2.setProviderServiceMapID(1);
    benAnthropometryDetail2.setReservedForChange("Reserved For Change");
    benAnthropometryDetail2.setSyncedBy("Synced By");
    benAnthropometryDetail2.setSyncedDate(mock(Timestamp.class));
    benAnthropometryDetail2.setVanID(1);
    benAnthropometryDetail2.setVanSerialNo(1L);
    benAnthropometryDetail2.setVehicalNo("Vehical No");
    benAnthropometryDetail2.setVisitCode(1L);
    benAnthropometryDetail2.setWaistCircumference_cm(10.0d);
    benAnthropometryDetail2.setWaistHipRatio(10.0d);
    benAnthropometryDetail2.setWeight_Kg(10.0d);
    benAnthropometryDetail2.setbMI(10.0d);

    // Act
    Long actualSaveBeneficiaryPhysicalAnthropometryDetailsResult = commonNurseServiceImpl
        .saveBeneficiaryPhysicalAnthropometryDetails(benAnthropometryDetail2);

    // Assert
    verify(benAnthropometryDetail).getID();
    verify(benAnthropometryDetail).setBenVisitID(isA(Long.class));
    verify(benAnthropometryDetail).setBeneficiaryRegID(isA(Long.class));
    verify(benAnthropometryDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benAnthropometryDetail).setCreatedDate(isA(Timestamp.class));
    verify(benAnthropometryDetail).setDeleted(isA(Boolean.class));
    verify(benAnthropometryDetail).setHeadCircumference_cm(isA(Double.class));
    verify(benAnthropometryDetail).setHeight_cm(isA(Double.class));
    verify(benAnthropometryDetail).setHipCircumference_cm(isA(Double.class));
    verify(benAnthropometryDetail).setID(isA(Long.class));
    verify(benAnthropometryDetail).setLastModDate(isA(Timestamp.class));
    verify(benAnthropometryDetail).setMidUpperArmCircumference_MUAC_cm(isA(Double.class));
    verify(benAnthropometryDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benAnthropometryDetail).setParkingPlaceID(isA(Integer.class));
    verify(benAnthropometryDetail).setProcessed(eq("Processed"));
    verify(benAnthropometryDetail).setProviderServiceMapID(isA(Integer.class));
    verify(benAnthropometryDetail).setReservedForChange(eq("Reserved For Change"));
    verify(benAnthropometryDetail).setSyncedBy(eq("Synced By"));
    verify(benAnthropometryDetail).setSyncedDate(isA(Timestamp.class));
    verify(benAnthropometryDetail).setVanID(isA(Integer.class));
    verify(benAnthropometryDetail).setVanSerialNo(isA(Long.class));
    verify(benAnthropometryDetail).setVehicalNo(eq("Vehical No"));
    verify(benAnthropometryDetail).setVisitCode(isA(Long.class));
    verify(benAnthropometryDetail).setWaistCircumference_cm(isA(Double.class));
    verify(benAnthropometryDetail).setWaistHipRatio(isA(Double.class));
    verify(benAnthropometryDetail).setWeight_Kg(isA(Double.class));
    verify(benAnthropometryDetail).setbMI(isA(Double.class));
    verify(benAnthropometryRepo).save(isA(BenAnthropometryDetail.class));
    assertEquals(1L, actualSaveBeneficiaryPhysicalAnthropometryDetailsResult.longValue());
  }

  @Test
  void testSaveIDRS() {
    // Arrange
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
    when(iDRSDataRepo.save(Mockito.<IDRSData>any())).thenReturn(idrsData3);

    IDRSData idrsData4 = new IDRSData();
    idrsData4.setAnswer("Answer");
    idrsData4.setBenVisitID(1L);
    idrsData4.setBeneficiaryRegID(1L);
    idrsData4.setConfirmArray(new String[]{"Confirm Array"});
    idrsData4.setConfirmedDisease("Confirmed Disease");
    idrsData4.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    idrsData4.setCreatedDate(mock(Timestamp.class));
    idrsData4.setDeleted(true);
    idrsData4.setDiseaseQuestionType("Disease Question Type");
    idrsData4.setId(1L);
    idrsData4.setIdrsDetails(new ArrayList<>());
    idrsData4.setIdrsQuestionID(1);
    idrsData4.setIdrsScore(1);
    idrsData4.setIsDiabetic(true);
    idrsData4.setLastModDate(mock(Timestamp.class));
    idrsData4.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    idrsData4.setParkingPlaceID(1);
    idrsData4.setProcessed("Processed");
    idrsData4.setProviderServiceMapID(1);
    idrsData4.setQuestion("Question");
    idrsData4.setQuestionArray(new IDRSData[]{new IDRSData()});
    idrsData4.setSuspectArray(new String[]{"Suspect Array"});
    idrsData4.setSuspectDetails(new ArrayList<>());
    idrsData4.setSuspectedDisease("Suspected Disease");
    idrsData4.setVanID(1);
    idrsData4.setVanSerialNo(1L);
    idrsData4.setVehicalNo("Vehical No");
    idrsData4.setVisitCode(1L);

    IDRSData idrsDetail = new IDRSData();
    idrsDetail.setAnswer("Answer");
    idrsDetail.setBenVisitID(1L);
    idrsDetail.setBeneficiaryRegID(1L);
    idrsDetail.setConfirmArray(new String[]{"Confirm Array"});
    idrsDetail.setConfirmedDisease("Confirmed Disease");
    idrsDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    idrsDetail.setCreatedDate(mock(Timestamp.class));
    idrsDetail.setDeleted(true);
    idrsDetail.setDiseaseQuestionType("Disease Question Type");
    idrsDetail.setId(1L);
    idrsDetail.setIdrsDetails(new ArrayList<>());
    idrsDetail.setIdrsQuestionID(1);
    idrsDetail.setIdrsScore(1);
    idrsDetail.setIsDiabetic(true);
    idrsDetail.setLastModDate(mock(Timestamp.class));
    idrsDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    idrsDetail.setParkingPlaceID(1);
    idrsDetail.setProcessed("Processed");
    idrsDetail.setProviderServiceMapID(1);
    idrsDetail.setQuestion("Question");
    idrsDetail.setQuestionArray(new IDRSData[]{idrsData4});
    idrsDetail.setSuspectArray(new String[]{"Suspect Array"});
    idrsDetail.setSuspectDetails(new ArrayList<>());
    idrsDetail.setSuspectedDisease("Suspected Disease");
    idrsDetail.setVanID(1);
    idrsDetail.setVanSerialNo(1L);
    idrsDetail.setVehicalNo("Vehical No");
    idrsDetail.setVisitCode(1L);

    // Act
    Long actualSaveIDRSResult = commonNurseServiceImpl.saveIDRS(idrsDetail);

    // Assert
    verify(iDRSDataRepo).save(isA(IDRSData.class));
    assertEquals(1L, actualSaveIDRSResult.longValue());
  }

  @Test
  void testSaveIDRS2() {
    // Arrange
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
    IDRSData idrsData3 = mock(IDRSData.class);
    when(idrsData3.getId()).thenReturn(1L);
    doNothing().when(idrsData3).setAnswer(Mockito.<String>any());
    doNothing().when(idrsData3).setBenVisitID(Mockito.<Long>any());
    doNothing().when(idrsData3).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(idrsData3).setConfirmArray(Mockito.<String[]>any());
    doNothing().when(idrsData3).setConfirmedDisease(Mockito.<String>any());
    doNothing().when(idrsData3).setCreatedBy(Mockito.<String>any());
    doNothing().when(idrsData3).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(idrsData3).setDeleted(Mockito.<Boolean>any());
    doNothing().when(idrsData3).setDiseaseQuestionType(Mockito.<String>any());
    doNothing().when(idrsData3).setId(Mockito.<Long>any());
    doNothing().when(idrsData3).setIdrsDetails(Mockito.<List<Map<String, Object>>>any());
    doNothing().when(idrsData3).setIdrsQuestionID(Mockito.<Integer>any());
    doNothing().when(idrsData3).setIdrsScore(Mockito.<Integer>any());
    doNothing().when(idrsData3).setIsDiabetic(Mockito.<Boolean>any());
    doNothing().when(idrsData3).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(idrsData3).setModifiedBy(Mockito.<String>any());
    doNothing().when(idrsData3).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(idrsData3).setProcessed(Mockito.<String>any());
    doNothing().when(idrsData3).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(idrsData3).setQuestion(Mockito.<String>any());
    doNothing().when(idrsData3).setQuestionArray(Mockito.<IDRSData[]>any());
    doNothing().when(idrsData3).setSuspectArray(Mockito.<String[]>any());
    doNothing().when(idrsData3).setSuspectDetails(Mockito.<List<Map<String, Object>>>any());
    doNothing().when(idrsData3).setSuspectedDisease(Mockito.<String>any());
    doNothing().when(idrsData3).setVanID(Mockito.<Integer>any());
    doNothing().when(idrsData3).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(idrsData3).setVehicalNo(Mockito.<String>any());
    doNothing().when(idrsData3).setVisitCode(Mockito.<Long>any());
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
    when(iDRSDataRepo.save(Mockito.<IDRSData>any())).thenReturn(idrsData3);

    IDRSData idrsData4 = new IDRSData();
    idrsData4.setAnswer("Answer");
    idrsData4.setBenVisitID(1L);
    idrsData4.setBeneficiaryRegID(1L);
    idrsData4.setConfirmArray(new String[]{"Confirm Array"});
    idrsData4.setConfirmedDisease("Confirmed Disease");
    idrsData4.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    idrsData4.setCreatedDate(mock(Timestamp.class));
    idrsData4.setDeleted(true);
    idrsData4.setDiseaseQuestionType("Disease Question Type");
    idrsData4.setId(1L);
    idrsData4.setIdrsDetails(new ArrayList<>());
    idrsData4.setIdrsQuestionID(1);
    idrsData4.setIdrsScore(1);
    idrsData4.setIsDiabetic(true);
    idrsData4.setLastModDate(mock(Timestamp.class));
    idrsData4.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    idrsData4.setParkingPlaceID(1);
    idrsData4.setProcessed("Processed");
    idrsData4.setProviderServiceMapID(1);
    idrsData4.setQuestion("Question");
    idrsData4.setQuestionArray(new IDRSData[]{new IDRSData()});
    idrsData4.setSuspectArray(new String[]{"Suspect Array"});
    idrsData4.setSuspectDetails(new ArrayList<>());
    idrsData4.setSuspectedDisease("Suspected Disease");
    idrsData4.setVanID(1);
    idrsData4.setVanSerialNo(1L);
    idrsData4.setVehicalNo("Vehical No");
    idrsData4.setVisitCode(1L);

    IDRSData idrsDetail = new IDRSData();
    idrsDetail.setAnswer("Answer");
    idrsDetail.setBenVisitID(1L);
    idrsDetail.setBeneficiaryRegID(1L);
    idrsDetail.setConfirmArray(new String[]{"Confirm Array"});
    idrsDetail.setConfirmedDisease("Confirmed Disease");
    idrsDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    idrsDetail.setCreatedDate(mock(Timestamp.class));
    idrsDetail.setDeleted(true);
    idrsDetail.setDiseaseQuestionType("Disease Question Type");
    idrsDetail.setId(1L);
    idrsDetail.setIdrsDetails(new ArrayList<>());
    idrsDetail.setIdrsQuestionID(1);
    idrsDetail.setIdrsScore(1);
    idrsDetail.setIsDiabetic(true);
    idrsDetail.setLastModDate(mock(Timestamp.class));
    idrsDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    idrsDetail.setParkingPlaceID(1);
    idrsDetail.setProcessed("Processed");
    idrsDetail.setProviderServiceMapID(1);
    idrsDetail.setQuestion("Question");
    idrsDetail.setQuestionArray(new IDRSData[]{idrsData4});
    idrsDetail.setSuspectArray(new String[]{"Suspect Array"});
    idrsDetail.setSuspectDetails(new ArrayList<>());
    idrsDetail.setSuspectedDisease("Suspected Disease");
    idrsDetail.setVanID(1);
    idrsDetail.setVanSerialNo(1L);
    idrsDetail.setVehicalNo("Vehical No");
    idrsDetail.setVisitCode(1L);

    // Act
    Long actualSaveIDRSResult = commonNurseServiceImpl.saveIDRS(idrsDetail);

    // Assert
    verify(idrsData3).getId();
    verify(idrsData3).setAnswer(eq("Answer"));
    verify(idrsData3).setBenVisitID(isA(Long.class));
    verify(idrsData3).setBeneficiaryRegID(isA(Long.class));
    verify(idrsData3).setConfirmArray(isA(String[].class));
    verify(idrsData3).setConfirmedDisease(eq("Confirmed Disease"));
    verify(idrsData3).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(idrsData3).setCreatedDate(isA(Timestamp.class));
    verify(idrsData3).setDeleted(isA(Boolean.class));
    verify(idrsData3).setDiseaseQuestionType(eq("Disease Question Type"));
    verify(idrsData3).setId(isA(Long.class));
    verify(idrsData3).setIdrsDetails(isA(List.class));
    verify(idrsData3).setIdrsQuestionID(isA(Integer.class));
    verify(idrsData3).setIdrsScore(isA(Integer.class));
    verify(idrsData3).setIsDiabetic(isA(Boolean.class));
    verify(idrsData3).setLastModDate(isA(Timestamp.class));
    verify(idrsData3).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(idrsData3).setParkingPlaceID(isA(Integer.class));
    verify(idrsData3).setProcessed(eq("Processed"));
    verify(idrsData3).setProviderServiceMapID(isA(Integer.class));
    verify(idrsData3).setQuestion(eq("Question"));
    verify(idrsData3).setQuestionArray(isA(IDRSData[].class));
    verify(idrsData3).setSuspectArray(isA(String[].class));
    verify(idrsData3).setSuspectDetails(isA(List.class));
    verify(idrsData3).setSuspectedDisease(eq("Suspected Disease"));
    verify(idrsData3).setVanID(isA(Integer.class));
    verify(idrsData3).setVanSerialNo(isA(Long.class));
    verify(idrsData3).setVehicalNo(eq("Vehical No"));
    verify(idrsData3).setVisitCode(isA(Long.class));
    verify(iDRSDataRepo).save(isA(IDRSData.class));
    assertEquals(1L, actualSaveIDRSResult.longValue());
  }

  @Test
  void testSavePhysicalActivity() {
    // Arrange
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
    when(physicalActivityTypeRepo.save(Mockito.<PhysicalActivityType>any())).thenReturn(physicalActivityType);

    PhysicalActivityType physicalActivityDetail = new PhysicalActivityType();
    physicalActivityDetail.setActivityType("Activity Type");
    physicalActivityDetail.setBenVisitID(1L);
    physicalActivityDetail.setBeneficiaryRegID(1L);
    physicalActivityDetail.setCaptureDate(mock(Date.class));
    physicalActivityDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    physicalActivityDetail.setCreatedDate(mock(Timestamp.class));
    physicalActivityDetail.setDeleted(true);
    physicalActivityDetail.setID(1L);
    physicalActivityDetail.setLastModDate(mock(Timestamp.class));
    physicalActivityDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    physicalActivityDetail.setParkingPlaceID(1);
    physicalActivityDetail.setPhysicalActivityType("Physical Activity Type");
    physicalActivityDetail.setProcessed("Processed");
    physicalActivityDetail.setProviderServiceMapID(1);
    physicalActivityDetail.setVanID(1);
    physicalActivityDetail.setVanSerialNo(1L);
    physicalActivityDetail.setVehicalNo("Vehical No");
    physicalActivityDetail.setVisitCode(1L);
    physicalActivityDetail.setpAID(1L);

    // Act
    Long actualSavePhysicalActivityResult = commonNurseServiceImpl.savePhysicalActivity(physicalActivityDetail);

    // Assert
    verify(physicalActivityTypeRepo).save(isA(PhysicalActivityType.class));
    assertEquals(1L, actualSavePhysicalActivityResult.longValue());
  }

  @Test
  void testSavePhysicalActivity2() {
    // Arrange
    PhysicalActivityType physicalActivityType = mock(PhysicalActivityType.class);
    when(physicalActivityType.getpAID()).thenReturn(1L);
    doNothing().when(physicalActivityType).setActivityType(Mockito.<String>any());
    doNothing().when(physicalActivityType).setBenVisitID(Mockito.<Long>any());
    doNothing().when(physicalActivityType).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(physicalActivityType).setCaptureDate(Mockito.<Date>any());
    doNothing().when(physicalActivityType).setCreatedBy(Mockito.<String>any());
    doNothing().when(physicalActivityType).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(physicalActivityType).setDeleted(Mockito.<Boolean>any());
    doNothing().when(physicalActivityType).setID(Mockito.<Long>any());
    doNothing().when(physicalActivityType).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(physicalActivityType).setModifiedBy(Mockito.<String>any());
    doNothing().when(physicalActivityType).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(physicalActivityType).setPhysicalActivityType(Mockito.<String>any());
    doNothing().when(physicalActivityType).setProcessed(Mockito.<String>any());
    doNothing().when(physicalActivityType).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(physicalActivityType).setVanID(Mockito.<Integer>any());
    doNothing().when(physicalActivityType).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(physicalActivityType).setVehicalNo(Mockito.<String>any());
    doNothing().when(physicalActivityType).setVisitCode(Mockito.<Long>any());
    doNothing().when(physicalActivityType).setpAID(Mockito.<Long>any());
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
    when(physicalActivityTypeRepo.save(Mockito.<PhysicalActivityType>any())).thenReturn(physicalActivityType);

    PhysicalActivityType physicalActivityDetail = new PhysicalActivityType();
    physicalActivityDetail.setActivityType("Activity Type");
    physicalActivityDetail.setBenVisitID(1L);
    physicalActivityDetail.setBeneficiaryRegID(1L);
    physicalActivityDetail.setCaptureDate(mock(Date.class));
    physicalActivityDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    physicalActivityDetail.setCreatedDate(mock(Timestamp.class));
    physicalActivityDetail.setDeleted(true);
    physicalActivityDetail.setID(1L);
    physicalActivityDetail.setLastModDate(mock(Timestamp.class));
    physicalActivityDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    physicalActivityDetail.setParkingPlaceID(1);
    physicalActivityDetail.setPhysicalActivityType("Physical Activity Type");
    physicalActivityDetail.setProcessed("Processed");
    physicalActivityDetail.setProviderServiceMapID(1);
    physicalActivityDetail.setVanID(1);
    physicalActivityDetail.setVanSerialNo(1L);
    physicalActivityDetail.setVehicalNo("Vehical No");
    physicalActivityDetail.setVisitCode(1L);
    physicalActivityDetail.setpAID(1L);

    // Act
    Long actualSavePhysicalActivityResult = commonNurseServiceImpl.savePhysicalActivity(physicalActivityDetail);

    // Assert
    verify(physicalActivityType).getpAID();
    verify(physicalActivityType).setActivityType(eq("Activity Type"));
    verify(physicalActivityType).setBenVisitID(isA(Long.class));
    verify(physicalActivityType).setBeneficiaryRegID(isA(Long.class));
    verify(physicalActivityType).setCaptureDate(isA(Date.class));
    verify(physicalActivityType).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(physicalActivityType).setCreatedDate(isA(Timestamp.class));
    verify(physicalActivityType).setDeleted(isA(Boolean.class));
    verify(physicalActivityType).setID(isA(Long.class));
    verify(physicalActivityType).setLastModDate(isA(Timestamp.class));
    verify(physicalActivityType).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(physicalActivityType).setParkingPlaceID(isA(Integer.class));
    verify(physicalActivityType).setPhysicalActivityType(eq("Physical Activity Type"));
    verify(physicalActivityType).setProcessed(eq("Processed"));
    verify(physicalActivityType).setProviderServiceMapID(isA(Integer.class));
    verify(physicalActivityType).setVanID(isA(Integer.class));
    verify(physicalActivityType).setVanSerialNo(isA(Long.class));
    verify(physicalActivityType).setVehicalNo(eq("Vehical No"));
    verify(physicalActivityType).setVisitCode(isA(Long.class));
    verify(physicalActivityType).setpAID(isA(Long.class));
    verify(physicalActivityTypeRepo).save(isA(PhysicalActivityType.class));
    assertEquals(1L, actualSavePhysicalActivityResult.longValue());
  }

  @Test
  void testSaveBenFamilyHistoryNCDScreening() {
    // Arrange
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

    // Act and Assert
    assertEquals(1L, commonNurseServiceImpl.saveBenFamilyHistoryNCDScreening(benFamilyHistory).longValue());
  }

  @Test
  void testSaveBenFamilyHistoryNCDScreening2() {
    // Arrange
    BenFamilyHistory benFamilyHistory = mock(BenFamilyHistory.class);
    when(benFamilyHistory.getBenFamilyHist()).thenReturn(new ArrayList<>());
    doNothing().when(benFamilyHistory).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benFamilyHistory).setCreatedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benFamilyHistory).setDiseaseType(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setDiseaseTypeID(Mockito.<Short>any());
    doNothing().when(benFamilyHistory).setFamilyDiseaseList(Mockito.<List<Map<String, Object>>>any());
    doNothing().when(benFamilyHistory).setFamilyMember(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setGeneticDisorder(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory).setIsConsanguineousMarrige(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setIsGeneticDisorder(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory).setModifiedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setOtherDiseaseType(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory).setProcessed(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory).setReservedForChange(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setSnomedCode(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setSnomedTerm(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setSyncedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory).setVanID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benFamilyHistory).setVehicalNo(Mockito.<String>any());
    doNothing().when(benFamilyHistory).setVisitCode(Mockito.<Long>any());
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

    // Act
    Long actualSaveBenFamilyHistoryNCDScreeningResult = commonNurseServiceImpl
        .saveBenFamilyHistoryNCDScreening(benFamilyHistory);

    // Assert
    verify(benFamilyHistory).getBenFamilyHist();
    verify(benFamilyHistory).setBenVisitID(isA(Long.class));
    verify(benFamilyHistory).setBeneficiaryRegID(isA(Long.class));
    verify(benFamilyHistory).setCaptureDate(isA(Date.class));
    verify(benFamilyHistory).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benFamilyHistory).setCreatedDate(isA(Timestamp.class));
    verify(benFamilyHistory).setDeleted(isA(Boolean.class));
    verify(benFamilyHistory).setDiseaseType(eq("Disease Type"));
    verify(benFamilyHistory).setDiseaseTypeID(isA(Short.class));
    verify(benFamilyHistory).setFamilyDiseaseList(isA(List.class));
    verify(benFamilyHistory).setFamilyMember(eq("Family Member"));
    verify(benFamilyHistory).setGeneticDisorder(eq("Genetic Disorder"));
    verify(benFamilyHistory).setID(isA(Long.class));
    verify(benFamilyHistory).setIsConsanguineousMarrige(eq("Is Consanguineous Marrige"));
    verify(benFamilyHistory).setIsGeneticDisorder(eq("Is Genetic Disorder"));
    verify(benFamilyHistory).setLastModDate(isA(Timestamp.class));
    verify(benFamilyHistory).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benFamilyHistory).setOtherDiseaseType(eq("Other Disease Type"));
    verify(benFamilyHistory).setParkingPlaceID(isA(Integer.class));
    verify(benFamilyHistory).setProcessed(eq("Processed"));
    verify(benFamilyHistory).setProviderServiceMapID(isA(Integer.class));
    verify(benFamilyHistory).setReservedForChange(eq("Reserved For Change"));
    verify(benFamilyHistory).setSnomedCode(eq("Snomed Code"));
    verify(benFamilyHistory).setSnomedTerm(eq("Snomed Term"));
    verify(benFamilyHistory).setSyncedBy(eq("Synced By"));
    verify(benFamilyHistory).setSyncedDate(isA(Timestamp.class));
    verify(benFamilyHistory).setVanID(isA(Integer.class));
    verify(benFamilyHistory).setVanSerialNo(isA(Long.class));
    verify(benFamilyHistory).setVehicalNo(eq("Vehical No"));
    verify(benFamilyHistory).setVisitCode(isA(Long.class));
    assertEquals(1L, actualSaveBenFamilyHistoryNCDScreeningResult.longValue());
  }

  @Test
  void testSaveBenFamilyHistoryNCDScreening3() {
    // Arrange
    when(benFamilyHistoryRepo.saveAll(Mockito.<Iterable<BenFamilyHistory>>any())).thenReturn(new ArrayList<>());

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

    ArrayList<BenFamilyHistory> benFamilyHistoryList = new ArrayList<>();
    benFamilyHistoryList.add(benFamilyHistory);
    BenFamilyHistory benFamilyHistory2 = mock(BenFamilyHistory.class);
    when(benFamilyHistory2.getBenFamilyHist()).thenReturn(benFamilyHistoryList);
    doNothing().when(benFamilyHistory2).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory2).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory2).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benFamilyHistory2).setCreatedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory2).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benFamilyHistory2).setDiseaseType(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setDiseaseTypeID(Mockito.<Short>any());
    doNothing().when(benFamilyHistory2).setFamilyDiseaseList(Mockito.<List<Map<String, Object>>>any());
    doNothing().when(benFamilyHistory2).setFamilyMember(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setGeneticDisorder(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory2).setIsConsanguineousMarrige(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setIsGeneticDisorder(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory2).setModifiedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setOtherDiseaseType(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory2).setProcessed(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory2).setReservedForChange(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setSnomedCode(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setSnomedTerm(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setSyncedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory2).setVanID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory2).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benFamilyHistory2).setVehicalNo(Mockito.<String>any());
    doNothing().when(benFamilyHistory2).setVisitCode(Mockito.<Long>any());
    benFamilyHistory2.setBenVisitID(1L);
    benFamilyHistory2.setBeneficiaryRegID(1L);
    benFamilyHistory2.setCaptureDate(mock(Date.class));
    benFamilyHistory2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benFamilyHistory2.setCreatedDate(mock(Timestamp.class));
    benFamilyHistory2.setDeleted(true);
    benFamilyHistory2.setDiseaseType("Disease Type");
    benFamilyHistory2.setDiseaseTypeID((short) 1);
    benFamilyHistory2.setFamilyDiseaseList(new ArrayList<>());
    benFamilyHistory2.setFamilyMember("Family Member");
    benFamilyHistory2.setGeneticDisorder("Genetic Disorder");
    benFamilyHistory2.setID(1L);
    benFamilyHistory2.setIsConsanguineousMarrige("Is Consanguineous Marrige");
    benFamilyHistory2.setIsGeneticDisorder("Is Genetic Disorder");
    benFamilyHistory2.setLastModDate(mock(Timestamp.class));
    benFamilyHistory2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benFamilyHistory2.setOtherDiseaseType("Other Disease Type");
    benFamilyHistory2.setParkingPlaceID(1);
    benFamilyHistory2.setProcessed("Processed");
    benFamilyHistory2.setProviderServiceMapID(1);
    benFamilyHistory2.setReservedForChange("Reserved For Change");
    benFamilyHistory2.setSnomedCode("Snomed Code");
    benFamilyHistory2.setSnomedTerm("Snomed Term");
    benFamilyHistory2.setSyncedBy("Synced By");
    benFamilyHistory2.setSyncedDate(mock(Timestamp.class));
    benFamilyHistory2.setVanID(1);
    benFamilyHistory2.setVanSerialNo(1L);
    benFamilyHistory2.setVehicalNo("Vehical No");
    benFamilyHistory2.setVisitCode(1L);

    // Act
    Long actualSaveBenFamilyHistoryNCDScreeningResult = commonNurseServiceImpl
        .saveBenFamilyHistoryNCDScreening(benFamilyHistory2);

    // Assert
    verify(benFamilyHistory2).getBenFamilyHist();
    verify(benFamilyHistory2).setBenVisitID(isA(Long.class));
    verify(benFamilyHistory2).setBeneficiaryRegID(isA(Long.class));
    verify(benFamilyHistory2).setCaptureDate(isA(Date.class));
    verify(benFamilyHistory2).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benFamilyHistory2).setCreatedDate(isA(Timestamp.class));
    verify(benFamilyHistory2).setDeleted(isA(Boolean.class));
    verify(benFamilyHistory2).setDiseaseType(eq("Disease Type"));
    verify(benFamilyHistory2).setDiseaseTypeID(isA(Short.class));
    verify(benFamilyHistory2).setFamilyDiseaseList(isA(List.class));
    verify(benFamilyHistory2).setFamilyMember(eq("Family Member"));
    verify(benFamilyHistory2).setGeneticDisorder(eq("Genetic Disorder"));
    verify(benFamilyHistory2).setID(isA(Long.class));
    verify(benFamilyHistory2).setIsConsanguineousMarrige(eq("Is Consanguineous Marrige"));
    verify(benFamilyHistory2).setIsGeneticDisorder(eq("Is Genetic Disorder"));
    verify(benFamilyHistory2).setLastModDate(isA(Timestamp.class));
    verify(benFamilyHistory2).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benFamilyHistory2).setOtherDiseaseType(eq("Other Disease Type"));
    verify(benFamilyHistory2).setParkingPlaceID(isA(Integer.class));
    verify(benFamilyHistory2).setProcessed(eq("Processed"));
    verify(benFamilyHistory2).setProviderServiceMapID(isA(Integer.class));
    verify(benFamilyHistory2).setReservedForChange(eq("Reserved For Change"));
    verify(benFamilyHistory2).setSnomedCode(eq("Snomed Code"));
    verify(benFamilyHistory2).setSnomedTerm(eq("Snomed Term"));
    verify(benFamilyHistory2).setSyncedBy(eq("Synced By"));
    verify(benFamilyHistory2).setSyncedDate(isA(Timestamp.class));
    verify(benFamilyHistory2).setVanID(isA(Integer.class));
    verify(benFamilyHistory2).setVanSerialNo(isA(Long.class));
    verify(benFamilyHistory2).setVehicalNo(eq("Vehical No"));
    verify(benFamilyHistory2).setVisitCode(isA(Long.class));
    verify(benFamilyHistoryRepo).saveAll(isA(Iterable.class));
    assertNull(actualSaveBenFamilyHistoryNCDScreeningResult);
  }

  @Test
  void testSaveBenFamilyHistoryNCDScreening4() {
    // Arrange
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

    ArrayList<BenFamilyHistory> benFamilyHistoryList = new ArrayList<>();
    benFamilyHistoryList.add(benFamilyHistory);
    when(benFamilyHistoryRepo.saveAll(Mockito.<Iterable<BenFamilyHistory>>any())).thenReturn(benFamilyHistoryList);

    BenFamilyHistory benFamilyHistory2 = new BenFamilyHistory();
    benFamilyHistory2.setBenVisitID(1L);
    benFamilyHistory2.setBeneficiaryRegID(1L);
    benFamilyHistory2.setCaptureDate(mock(Date.class));
    benFamilyHistory2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benFamilyHistory2.setCreatedDate(mock(Timestamp.class));
    benFamilyHistory2.setDeleted(true);
    benFamilyHistory2.setDiseaseType("Disease Type");
    benFamilyHistory2.setDiseaseTypeID((short) 1);
    benFamilyHistory2.setFamilyDiseaseList(new ArrayList<>());
    benFamilyHistory2.setFamilyMember("Family Member");
    benFamilyHistory2.setGeneticDisorder("Genetic Disorder");
    benFamilyHistory2.setID(1L);
    benFamilyHistory2.setIsConsanguineousMarrige("Is Consanguineous Marrige");
    benFamilyHistory2.setIsGeneticDisorder("Is Genetic Disorder");
    benFamilyHistory2.setLastModDate(mock(Timestamp.class));
    benFamilyHistory2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benFamilyHistory2.setOtherDiseaseType("Other Disease Type");
    benFamilyHistory2.setParkingPlaceID(1);
    benFamilyHistory2.setProcessed("Processed");
    benFamilyHistory2.setProviderServiceMapID(1);
    benFamilyHistory2.setReservedForChange("Reserved For Change");
    benFamilyHistory2.setSnomedCode("Snomed Code");
    benFamilyHistory2.setSnomedTerm("Snomed Term");
    benFamilyHistory2.setSyncedBy("Synced By");
    benFamilyHistory2.setSyncedDate(mock(Timestamp.class));
    benFamilyHistory2.setVanID(1);
    benFamilyHistory2.setVanSerialNo(1L);
    benFamilyHistory2.setVehicalNo("Vehical No");
    benFamilyHistory2.setVisitCode(1L);

    ArrayList<BenFamilyHistory> benFamilyHistoryList2 = new ArrayList<>();
    benFamilyHistoryList2.add(benFamilyHistory2);
    BenFamilyHistory benFamilyHistory3 = mock(BenFamilyHistory.class);
    when(benFamilyHistory3.getBenFamilyHist()).thenReturn(benFamilyHistoryList2);
    doNothing().when(benFamilyHistory3).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory3).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory3).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benFamilyHistory3).setCreatedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory3).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benFamilyHistory3).setDiseaseType(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setDiseaseTypeID(Mockito.<Short>any());
    doNothing().when(benFamilyHistory3).setFamilyDiseaseList(Mockito.<List<Map<String, Object>>>any());
    doNothing().when(benFamilyHistory3).setFamilyMember(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setGeneticDisorder(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setID(Mockito.<Long>any());
    doNothing().when(benFamilyHistory3).setIsConsanguineousMarrige(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setIsGeneticDisorder(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory3).setModifiedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setOtherDiseaseType(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory3).setProcessed(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory3).setReservedForChange(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setSnomedCode(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setSnomedTerm(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setSyncedBy(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benFamilyHistory3).setVanID(Mockito.<Integer>any());
    doNothing().when(benFamilyHistory3).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benFamilyHistory3).setVehicalNo(Mockito.<String>any());
    doNothing().when(benFamilyHistory3).setVisitCode(Mockito.<Long>any());
    benFamilyHistory3.setBenVisitID(1L);
    benFamilyHistory3.setBeneficiaryRegID(1L);
    benFamilyHistory3.setCaptureDate(mock(Date.class));
    benFamilyHistory3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benFamilyHistory3.setCreatedDate(mock(Timestamp.class));
    benFamilyHistory3.setDeleted(true);
    benFamilyHistory3.setDiseaseType("Disease Type");
    benFamilyHistory3.setDiseaseTypeID((short) 1);
    benFamilyHistory3.setFamilyDiseaseList(new ArrayList<>());
    benFamilyHistory3.setFamilyMember("Family Member");
    benFamilyHistory3.setGeneticDisorder("Genetic Disorder");
    benFamilyHistory3.setID(1L);
    benFamilyHistory3.setIsConsanguineousMarrige("Is Consanguineous Marrige");
    benFamilyHistory3.setIsGeneticDisorder("Is Genetic Disorder");
    benFamilyHistory3.setLastModDate(mock(Timestamp.class));
    benFamilyHistory3.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benFamilyHistory3.setOtherDiseaseType("Other Disease Type");
    benFamilyHistory3.setParkingPlaceID(1);
    benFamilyHistory3.setProcessed("Processed");
    benFamilyHistory3.setProviderServiceMapID(1);
    benFamilyHistory3.setReservedForChange("Reserved For Change");
    benFamilyHistory3.setSnomedCode("Snomed Code");
    benFamilyHistory3.setSnomedTerm("Snomed Term");
    benFamilyHistory3.setSyncedBy("Synced By");
    benFamilyHistory3.setSyncedDate(mock(Timestamp.class));
    benFamilyHistory3.setVanID(1);
    benFamilyHistory3.setVanSerialNo(1L);
    benFamilyHistory3.setVehicalNo("Vehical No");
    benFamilyHistory3.setVisitCode(1L);

    // Act
    Long actualSaveBenFamilyHistoryNCDScreeningResult = commonNurseServiceImpl
        .saveBenFamilyHistoryNCDScreening(benFamilyHistory3);

    // Assert
    verify(benFamilyHistory3).getBenFamilyHist();
    verify(benFamilyHistory3).setBenVisitID(isA(Long.class));
    verify(benFamilyHistory3).setBeneficiaryRegID(isA(Long.class));
    verify(benFamilyHistory3).setCaptureDate(isA(Date.class));
    verify(benFamilyHistory3).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benFamilyHistory3).setCreatedDate(isA(Timestamp.class));
    verify(benFamilyHistory3).setDeleted(isA(Boolean.class));
    verify(benFamilyHistory3).setDiseaseType(eq("Disease Type"));
    verify(benFamilyHistory3).setDiseaseTypeID(isA(Short.class));
    verify(benFamilyHistory3).setFamilyDiseaseList(isA(List.class));
    verify(benFamilyHistory3).setFamilyMember(eq("Family Member"));
    verify(benFamilyHistory3).setGeneticDisorder(eq("Genetic Disorder"));
    verify(benFamilyHistory3).setID(isA(Long.class));
    verify(benFamilyHistory3).setIsConsanguineousMarrige(eq("Is Consanguineous Marrige"));
    verify(benFamilyHistory3).setIsGeneticDisorder(eq("Is Genetic Disorder"));
    verify(benFamilyHistory3).setLastModDate(isA(Timestamp.class));
    verify(benFamilyHistory3).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benFamilyHistory3).setOtherDiseaseType(eq("Other Disease Type"));
    verify(benFamilyHistory3).setParkingPlaceID(isA(Integer.class));
    verify(benFamilyHistory3).setProcessed(eq("Processed"));
    verify(benFamilyHistory3).setProviderServiceMapID(isA(Integer.class));
    verify(benFamilyHistory3).setReservedForChange(eq("Reserved For Change"));
    verify(benFamilyHistory3).setSnomedCode(eq("Snomed Code"));
    verify(benFamilyHistory3).setSnomedTerm(eq("Snomed Term"));
    verify(benFamilyHistory3).setSyncedBy(eq("Synced By"));
    verify(benFamilyHistory3).setSyncedDate(isA(Timestamp.class));
    verify(benFamilyHistory3).setVanID(isA(Integer.class));
    verify(benFamilyHistory3).setVanSerialNo(isA(Long.class));
    verify(benFamilyHistory3).setVehicalNo(eq("Vehical No"));
    verify(benFamilyHistory3).setVisitCode(isA(Long.class));
    verify(benFamilyHistoryRepo).saveAll(isA(Iterable.class));
    assertEquals(1L, actualSaveBenFamilyHistoryNCDScreeningResult.longValue());
  }

  @Test
  void testSaveBeneficiaryPhysicalVitalDetails() {
    // Arrange
    BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();
    benPhysicalVitalDetail.setAverageDiastolicBP((short) 1);
    benPhysicalVitalDetail.setAverageSystolicBP((short) 1);
    benPhysicalVitalDetail.setBenVisitID(1L);
    benPhysicalVitalDetail.setBeneficiaryRegID(1L);
    benPhysicalVitalDetail.setBloodGlucose_2hr_PP((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_Fasting((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_NotSpecified((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_Random((short) 1);
    benPhysicalVitalDetail.setBloodPressureStatus("Blood Pressure Status");
    benPhysicalVitalDetail.setBloodPressureStatusID((short) 1);
    benPhysicalVitalDetail.setCapillaryRefillTime("Capillary Refill Time");
    benPhysicalVitalDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benPhysicalVitalDetail.setCreatedDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setDeleted(true);
    benPhysicalVitalDetail.setDiabeticStatus("Diabetic Status");
    benPhysicalVitalDetail.setDiabeticStatusID((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_1stReading((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail.setHemoglobin(10.0d);
    benPhysicalVitalDetail.setID(1L);
    benPhysicalVitalDetail.setLastModDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benPhysicalVitalDetail.setParkingPlaceID(1);
    benPhysicalVitalDetail.setProcessed("Processed");
    benPhysicalVitalDetail.setProviderServiceMapID(1);
    benPhysicalVitalDetail.setPulseRate((short) 1);
    benPhysicalVitalDetail.setRbsTestRemarks("Rbs Test Remarks");
    benPhysicalVitalDetail.setRbsTestResult("Rbs Test Result");
    benPhysicalVitalDetail.setReservedForChange("Reserved For Change");
    benPhysicalVitalDetail.setRespiratoryRate((short) 1);
    benPhysicalVitalDetail.setSyncedBy("Synced By");
    benPhysicalVitalDetail.setSyncedDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setSystolicBP_1stReading((short) 1);
    benPhysicalVitalDetail.setSystolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail.setSystolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail.setTemperature(10.0d);
    benPhysicalVitalDetail.setVanID(1);
    benPhysicalVitalDetail.setVanSerialNo(1L);
    benPhysicalVitalDetail.setVehicalNo("Vehical No");
    benPhysicalVitalDetail.setVisitCode(1L);
    benPhysicalVitalDetail.setsPO2("S PO2");
    when(benPhysicalVitalRepo.save(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(benPhysicalVitalDetail);

    BenPhysicalVitalDetail benPhysicalVitalDetail2 = new BenPhysicalVitalDetail();
    benPhysicalVitalDetail2.setAverageDiastolicBP((short) 1);
    benPhysicalVitalDetail2.setAverageSystolicBP((short) 1);
    benPhysicalVitalDetail2.setBenVisitID(1L);
    benPhysicalVitalDetail2.setBeneficiaryRegID(1L);
    benPhysicalVitalDetail2.setBloodGlucose_2hr_PP((short) 1);
    benPhysicalVitalDetail2.setBloodGlucose_Fasting((short) 1);
    benPhysicalVitalDetail2.setBloodGlucose_NotSpecified((short) 1);
    benPhysicalVitalDetail2.setBloodGlucose_Random((short) 1);
    benPhysicalVitalDetail2.setBloodPressureStatus("Blood Pressure Status");
    benPhysicalVitalDetail2.setBloodPressureStatusID((short) 1);
    benPhysicalVitalDetail2.setCapillaryRefillTime("Capillary Refill Time");
    benPhysicalVitalDetail2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benPhysicalVitalDetail2.setCreatedDate(mock(Timestamp.class));
    benPhysicalVitalDetail2.setDeleted(true);
    benPhysicalVitalDetail2.setDiabeticStatus("Diabetic Status");
    benPhysicalVitalDetail2.setDiabeticStatusID((short) 1);
    benPhysicalVitalDetail2.setDiastolicBP_1stReading((short) 1);
    benPhysicalVitalDetail2.setDiastolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail2.setDiastolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail2.setHemoglobin(10.0d);
    benPhysicalVitalDetail2.setID(1L);
    benPhysicalVitalDetail2.setLastModDate(mock(Timestamp.class));
    benPhysicalVitalDetail2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benPhysicalVitalDetail2.setParkingPlaceID(1);
    benPhysicalVitalDetail2.setProcessed("Processed");
    benPhysicalVitalDetail2.setProviderServiceMapID(1);
    benPhysicalVitalDetail2.setPulseRate((short) 1);
    benPhysicalVitalDetail2.setRbsTestRemarks("Rbs Test Remarks");
    benPhysicalVitalDetail2.setRbsTestResult("Rbs Test Result");
    benPhysicalVitalDetail2.setReservedForChange("Reserved For Change");
    benPhysicalVitalDetail2.setRespiratoryRate((short) 1);
    benPhysicalVitalDetail2.setSyncedBy("Synced By");
    benPhysicalVitalDetail2.setSyncedDate(mock(Timestamp.class));
    benPhysicalVitalDetail2.setSystolicBP_1stReading((short) 1);
    benPhysicalVitalDetail2.setSystolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail2.setSystolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail2.setTemperature(10.0d);
    benPhysicalVitalDetail2.setVanID(1);
    benPhysicalVitalDetail2.setVanSerialNo(1L);
    benPhysicalVitalDetail2.setVehicalNo("Vehical No");
    benPhysicalVitalDetail2.setVisitCode(1L);
    benPhysicalVitalDetail2.setsPO2("S PO2");

    // Act
    Long actualSaveBeneficiaryPhysicalVitalDetailsResult = commonNurseServiceImpl
        .saveBeneficiaryPhysicalVitalDetails(benPhysicalVitalDetail2);

    // Assert
    verify(benPhysicalVitalRepo).save(isA(BenPhysicalVitalDetail.class));
    assertEquals(1L, actualSaveBeneficiaryPhysicalVitalDetailsResult.longValue());
    assertEquals((short) 1, benPhysicalVitalDetail2.getAverageDiastolicBP().shortValue());
    assertEquals((short) 1, benPhysicalVitalDetail2.getAverageSystolicBP().shortValue());
  }

  @Test
  void testSaveBeneficiaryPhysicalVitalDetails2() {
    // Arrange
    BenPhysicalVitalDetail benPhysicalVitalDetail = mock(BenPhysicalVitalDetail.class);
    when(benPhysicalVitalDetail.getID()).thenReturn(1L);
    doNothing().when(benPhysicalVitalDetail).setAverageDiastolicBP(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setAverageSystolicBP(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benPhysicalVitalDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benPhysicalVitalDetail).setBloodGlucose_2hr_PP(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setBloodGlucose_Fasting(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setBloodGlucose_NotSpecified(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setBloodGlucose_Random(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setBloodPressureStatus(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setBloodPressureStatusID(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setCapillaryRefillTime(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benPhysicalVitalDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benPhysicalVitalDetail).setDiabeticStatus(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setDiabeticStatusID(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setDiastolicBP_1stReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setDiastolicBP_2ndReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setDiastolicBP_3rdReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setHemoglobin(Mockito.<Double>any());
    doNothing().when(benPhysicalVitalDetail).setID(Mockito.<Long>any());
    doNothing().when(benPhysicalVitalDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benPhysicalVitalDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benPhysicalVitalDetail).setProcessed(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benPhysicalVitalDetail).setPulseRate(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setRbsTestRemarks(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setRbsTestResult(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setRespiratoryRate(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benPhysicalVitalDetail).setSystolicBP_1stReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setSystolicBP_2ndReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setSystolicBP_3rdReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setTemperature(Mockito.<Double>any());
    doNothing().when(benPhysicalVitalDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(benPhysicalVitalDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benPhysicalVitalDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setVisitCode(Mockito.<Long>any());
    doNothing().when(benPhysicalVitalDetail).setsPO2(Mockito.<String>any());
    benPhysicalVitalDetail.setAverageDiastolicBP((short) 1);
    benPhysicalVitalDetail.setAverageSystolicBP((short) 1);
    benPhysicalVitalDetail.setBenVisitID(1L);
    benPhysicalVitalDetail.setBeneficiaryRegID(1L);
    benPhysicalVitalDetail.setBloodGlucose_2hr_PP((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_Fasting((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_NotSpecified((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_Random((short) 1);
    benPhysicalVitalDetail.setBloodPressureStatus("Blood Pressure Status");
    benPhysicalVitalDetail.setBloodPressureStatusID((short) 1);
    benPhysicalVitalDetail.setCapillaryRefillTime("Capillary Refill Time");
    benPhysicalVitalDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benPhysicalVitalDetail.setCreatedDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setDeleted(true);
    benPhysicalVitalDetail.setDiabeticStatus("Diabetic Status");
    benPhysicalVitalDetail.setDiabeticStatusID((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_1stReading((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail.setHemoglobin(10.0d);
    benPhysicalVitalDetail.setID(1L);
    benPhysicalVitalDetail.setLastModDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benPhysicalVitalDetail.setParkingPlaceID(1);
    benPhysicalVitalDetail.setProcessed("Processed");
    benPhysicalVitalDetail.setProviderServiceMapID(1);
    benPhysicalVitalDetail.setPulseRate((short) 1);
    benPhysicalVitalDetail.setRbsTestRemarks("Rbs Test Remarks");
    benPhysicalVitalDetail.setRbsTestResult("Rbs Test Result");
    benPhysicalVitalDetail.setReservedForChange("Reserved For Change");
    benPhysicalVitalDetail.setRespiratoryRate((short) 1);
    benPhysicalVitalDetail.setSyncedBy("Synced By");
    benPhysicalVitalDetail.setSyncedDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setSystolicBP_1stReading((short) 1);
    benPhysicalVitalDetail.setSystolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail.setSystolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail.setTemperature(10.0d);
    benPhysicalVitalDetail.setVanID(1);
    benPhysicalVitalDetail.setVanSerialNo(1L);
    benPhysicalVitalDetail.setVehicalNo("Vehical No");
    benPhysicalVitalDetail.setVisitCode(1L);
    benPhysicalVitalDetail.setsPO2("S PO2");
    when(benPhysicalVitalRepo.save(Mockito.<BenPhysicalVitalDetail>any())).thenReturn(benPhysicalVitalDetail);

    BenPhysicalVitalDetail benPhysicalVitalDetail2 = new BenPhysicalVitalDetail();
    benPhysicalVitalDetail2.setAverageDiastolicBP((short) 1);
    benPhysicalVitalDetail2.setAverageSystolicBP((short) 1);
    benPhysicalVitalDetail2.setBenVisitID(1L);
    benPhysicalVitalDetail2.setBeneficiaryRegID(1L);
    benPhysicalVitalDetail2.setBloodGlucose_2hr_PP((short) 1);
    benPhysicalVitalDetail2.setBloodGlucose_Fasting((short) 1);
    benPhysicalVitalDetail2.setBloodGlucose_NotSpecified((short) 1);
    benPhysicalVitalDetail2.setBloodGlucose_Random((short) 1);
    benPhysicalVitalDetail2.setBloodPressureStatus("Blood Pressure Status");
    benPhysicalVitalDetail2.setBloodPressureStatusID((short) 1);
    benPhysicalVitalDetail2.setCapillaryRefillTime("Capillary Refill Time");
    benPhysicalVitalDetail2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benPhysicalVitalDetail2.setCreatedDate(mock(Timestamp.class));
    benPhysicalVitalDetail2.setDeleted(true);
    benPhysicalVitalDetail2.setDiabeticStatus("Diabetic Status");
    benPhysicalVitalDetail2.setDiabeticStatusID((short) 1);
    benPhysicalVitalDetail2.setDiastolicBP_1stReading((short) 1);
    benPhysicalVitalDetail2.setDiastolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail2.setDiastolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail2.setHemoglobin(10.0d);
    benPhysicalVitalDetail2.setID(1L);
    benPhysicalVitalDetail2.setLastModDate(mock(Timestamp.class));
    benPhysicalVitalDetail2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benPhysicalVitalDetail2.setParkingPlaceID(1);
    benPhysicalVitalDetail2.setProcessed("Processed");
    benPhysicalVitalDetail2.setProviderServiceMapID(1);
    benPhysicalVitalDetail2.setPulseRate((short) 1);
    benPhysicalVitalDetail2.setRbsTestRemarks("Rbs Test Remarks");
    benPhysicalVitalDetail2.setRbsTestResult("Rbs Test Result");
    benPhysicalVitalDetail2.setReservedForChange("Reserved For Change");
    benPhysicalVitalDetail2.setRespiratoryRate((short) 1);
    benPhysicalVitalDetail2.setSyncedBy("Synced By");
    benPhysicalVitalDetail2.setSyncedDate(mock(Timestamp.class));
    benPhysicalVitalDetail2.setSystolicBP_1stReading((short) 1);
    benPhysicalVitalDetail2.setSystolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail2.setSystolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail2.setTemperature(10.0d);
    benPhysicalVitalDetail2.setVanID(1);
    benPhysicalVitalDetail2.setVanSerialNo(1L);
    benPhysicalVitalDetail2.setVehicalNo("Vehical No");
    benPhysicalVitalDetail2.setVisitCode(1L);
    benPhysicalVitalDetail2.setsPO2("S PO2");

    // Act
    Long actualSaveBeneficiaryPhysicalVitalDetailsResult = commonNurseServiceImpl
        .saveBeneficiaryPhysicalVitalDetails(benPhysicalVitalDetail2);

    // Assert
    verify(benPhysicalVitalDetail).getID();
    verify(benPhysicalVitalDetail).setAverageDiastolicBP(isA(Short.class));
    verify(benPhysicalVitalDetail).setAverageSystolicBP(isA(Short.class));
    verify(benPhysicalVitalDetail).setBenVisitID(isA(Long.class));
    verify(benPhysicalVitalDetail).setBeneficiaryRegID(isA(Long.class));
    verify(benPhysicalVitalDetail).setBloodGlucose_2hr_PP(isA(Short.class));
    verify(benPhysicalVitalDetail).setBloodGlucose_Fasting(isA(Short.class));
    verify(benPhysicalVitalDetail).setBloodGlucose_NotSpecified(isA(Short.class));
    verify(benPhysicalVitalDetail).setBloodGlucose_Random(isA(Short.class));
    verify(benPhysicalVitalDetail).setBloodPressureStatus(eq("Blood Pressure Status"));
    verify(benPhysicalVitalDetail).setBloodPressureStatusID(isA(Short.class));
    verify(benPhysicalVitalDetail).setCapillaryRefillTime(eq("Capillary Refill Time"));
    verify(benPhysicalVitalDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benPhysicalVitalDetail).setCreatedDate(isA(Timestamp.class));
    verify(benPhysicalVitalDetail).setDeleted(isA(Boolean.class));
    verify(benPhysicalVitalDetail).setDiabeticStatus(eq("Diabetic Status"));
    verify(benPhysicalVitalDetail).setDiabeticStatusID(isA(Short.class));
    verify(benPhysicalVitalDetail).setDiastolicBP_1stReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setDiastolicBP_2ndReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setDiastolicBP_3rdReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setHemoglobin(isA(Double.class));
    verify(benPhysicalVitalDetail).setID(isA(Long.class));
    verify(benPhysicalVitalDetail).setLastModDate(isA(Timestamp.class));
    verify(benPhysicalVitalDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benPhysicalVitalDetail).setParkingPlaceID(isA(Integer.class));
    verify(benPhysicalVitalDetail).setProcessed(eq("Processed"));
    verify(benPhysicalVitalDetail).setProviderServiceMapID(isA(Integer.class));
    verify(benPhysicalVitalDetail).setPulseRate(isA(Short.class));
    verify(benPhysicalVitalDetail).setRbsTestRemarks(eq("Rbs Test Remarks"));
    verify(benPhysicalVitalDetail).setRbsTestResult(eq("Rbs Test Result"));
    verify(benPhysicalVitalDetail).setReservedForChange(eq("Reserved For Change"));
    verify(benPhysicalVitalDetail).setRespiratoryRate(isA(Short.class));
    verify(benPhysicalVitalDetail).setSyncedBy(eq("Synced By"));
    verify(benPhysicalVitalDetail).setSyncedDate(isA(Timestamp.class));
    verify(benPhysicalVitalDetail).setSystolicBP_1stReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setSystolicBP_2ndReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setSystolicBP_3rdReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setTemperature(isA(Double.class));
    verify(benPhysicalVitalDetail).setVanID(isA(Integer.class));
    verify(benPhysicalVitalDetail).setVanSerialNo(isA(Long.class));
    verify(benPhysicalVitalDetail).setVehicalNo(eq("Vehical No"));
    verify(benPhysicalVitalDetail).setVisitCode(isA(Long.class));
    verify(benPhysicalVitalDetail).setsPO2(eq("S PO2"));
    verify(benPhysicalVitalRepo).save(isA(BenPhysicalVitalDetail.class));
    assertEquals(1L, actualSaveBeneficiaryPhysicalVitalDetailsResult.longValue());
    assertEquals((short) 1, benPhysicalVitalDetail2.getAverageDiastolicBP().shortValue());
    assertEquals((short) 1, benPhysicalVitalDetail2.getAverageSystolicBP().shortValue());
  }

  @Test
  void testGetBeneficiaryPhysicalAnthropometryDetails() {
    BenAnthropometryDetail benAnthropometryDetail = new BenAnthropometryDetail();
    benAnthropometryDetail.setBenVisitID(1L);
    benAnthropometryDetail.setBeneficiaryRegID(1L);
    benAnthropometryDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benAnthropometryDetail.setCreatedDate(mock(Timestamp.class));
    benAnthropometryDetail.setDeleted(true);
    benAnthropometryDetail.setHeadCircumference_cm(10.0d);
    benAnthropometryDetail.setHeight_cm(10.0d);
    benAnthropometryDetail.setHipCircumference_cm(10.0d);
    benAnthropometryDetail.setID(1L);
    benAnthropometryDetail.setLastModDate(mock(Timestamp.class));
    benAnthropometryDetail.setMidUpperArmCircumference_MUAC_cm(10.0d);
    benAnthropometryDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benAnthropometryDetail.setParkingPlaceID(1);
    benAnthropometryDetail.setProcessed("Processed");
    benAnthropometryDetail.setProviderServiceMapID(1);
    benAnthropometryDetail.setReservedForChange("Reserved For Change");
    benAnthropometryDetail.setSyncedBy("Synced By");
    benAnthropometryDetail.setSyncedDate(mock(Timestamp.class));
    benAnthropometryDetail.setVanID(1);
    benAnthropometryDetail.setVanSerialNo(1L);
    benAnthropometryDetail.setVehicalNo("Vehical No");
    benAnthropometryDetail.setVisitCode(1L);
    benAnthropometryDetail.setWaistCircumference_cm(10.0d);
    benAnthropometryDetail.setWaistHipRatio(10.0d);
    benAnthropometryDetail.setWeight_Kg(10.0d);
    benAnthropometryDetail.setbMI(10.0d);
    when(benAnthropometryRepo.getBenAnthropometryDetail(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(benAnthropometryDetail);

    // Act
    commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(1L, 1L);
  }

  @Test
  void testGetBenCdssDetails() {
    CDSS cdss = new CDSS();
    cdss.setAction("Action");
    cdss.setActionId(1);
    cdss.setActionIdPc(1);
    cdss.setActionPc("Action Pc");
    cdss.setAlgorithm("Algorithm");
    cdss.setAlgorithmPc("Algorithm Pc");
    cdss.setBenVisitID(1L);
    cdss.setBeneficiaryRegID(1L);
    cdss.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    cdss.setCreatedDate(mock(Timestamp.class));
    cdss.setDeleted(true);
    cdss.setDiseaseSummary("Disease Summary");
    cdss.setDiseaseSummaryID(1);
    cdss.setId(1L);
    cdss.setInformationGiven("Information Given");
    cdss.setLastModDate(mock(Timestamp.class));
    cdss.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    cdss.setParkingPlaceID(1);
    cdss.setPresentChiefComplaint("Present Chief Complaint");
    cdss.setPresentChiefComplaintID("Present Chief Complaint ID");
    cdss.setProviderServiceMapID(1);
    cdss.setRecommendedAction("Recommended Action");
    cdss.setRecommendedActionPc("Recommended Action Pc");
    cdss.setRemarks("Remarks");
    cdss.setRemarksPc("Remarks Pc");
    cdss.setReservedForChange("Reserved For Change");
    cdss.setSelectedDiagnosis("Selected Diagnosis");
    cdss.setSelectedDiagnosisID("Selected Diagnosis ID");
    cdss.setSyncedBy("Synced By");
    cdss.setSyncedDate(mock(Timestamp.class));
    cdss.setVanID(1);
    cdss.setVanSerialNo(1L);
    cdss.setVehicalNo("Vehical No");
    cdss.setVisitCode(1L);
    when(cDSSRepo.findByBeneficiaryRegIDAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(cdss);

    // Act
    commonNurseServiceImpl.getBenCdssDetails(1L, 1L);
  }

  @Test
  void testGetBeneficiaryPhysicalVitalDetails() {
    BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();
    benPhysicalVitalDetail.setAverageDiastolicBP((short) 1);
    benPhysicalVitalDetail.setAverageSystolicBP((short) 1);
    benPhysicalVitalDetail.setBenVisitID(1L);
    benPhysicalVitalDetail.setBeneficiaryRegID(1L);
    benPhysicalVitalDetail.setBloodGlucose_2hr_PP((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_Fasting((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_NotSpecified((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_Random((short) 1);
    benPhysicalVitalDetail.setBloodPressureStatus("Blood Pressure Status");
    benPhysicalVitalDetail.setBloodPressureStatusID((short) 1);
    benPhysicalVitalDetail.setCapillaryRefillTime("Capillary Refill Time");
    benPhysicalVitalDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benPhysicalVitalDetail.setCreatedDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setDeleted(true);
    benPhysicalVitalDetail.setDiabeticStatus("Diabetic Status");
    benPhysicalVitalDetail.setDiabeticStatusID((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_1stReading((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail.setHemoglobin(10.0d);
    benPhysicalVitalDetail.setID(1L);
    benPhysicalVitalDetail.setLastModDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benPhysicalVitalDetail.setParkingPlaceID(1);
    benPhysicalVitalDetail.setProcessed("Processed");
    benPhysicalVitalDetail.setProviderServiceMapID(1);
    benPhysicalVitalDetail.setPulseRate((short) 1);
    benPhysicalVitalDetail.setRbsTestRemarks("Rbs Test Remarks");
    benPhysicalVitalDetail.setRbsTestResult("Rbs Test Result");
    benPhysicalVitalDetail.setReservedForChange("Reserved For Change");
    benPhysicalVitalDetail.setRespiratoryRate((short) 1);
    benPhysicalVitalDetail.setSyncedBy("Synced By");
    benPhysicalVitalDetail.setSyncedDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setSystolicBP_1stReading((short) 1);
    benPhysicalVitalDetail.setSystolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail.setSystolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail.setTemperature(10.0d);
    benPhysicalVitalDetail.setVanID(1);
    benPhysicalVitalDetail.setVanSerialNo(1L);
    benPhysicalVitalDetail.setVehicalNo("Vehical No");
    benPhysicalVitalDetail.setVisitCode(1L);
    benPhysicalVitalDetail.setsPO2("S PO2");
    when(benPhysicalVitalRepo.getBenPhysicalVitalDetail(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(benPhysicalVitalDetail);

    // Act
    commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(1L, 1L);
  }

  @Test
  void testSavePhyGeneralExamination() throws IEMRException {
    // Arrange
    PhyGeneralExamination phyGeneralExamination = new PhyGeneralExamination();
    phyGeneralExamination.setBenVisitID(1L);
    phyGeneralExamination.setBeneficiaryRegID(1L);
    phyGeneralExamination.setBuiltAndAppearance("Built And Appearance");
    phyGeneralExamination.setClubbing("Clubbing");
    phyGeneralExamination.setCoherence("Coherence");
    phyGeneralExamination.setComfortness("Comfortness");
    phyGeneralExamination.setConsciousness("Consciousness");
    phyGeneralExamination.setCooperation("Cooperation");
    phyGeneralExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    phyGeneralExamination.setCreatedDate(mock(Timestamp.class));
    phyGeneralExamination.setCyanosis("Cyanosis");
    phyGeneralExamination.setDangerSigns("Danger Signs");
    phyGeneralExamination.setDeleted(true);
    phyGeneralExamination.setEdema("Edema");
    phyGeneralExamination.setEdemaType("Edema Type");
    phyGeneralExamination.setExtentOfEdema("Extent Of Edema");
    phyGeneralExamination.setFoetalMovements("Foetal Movements");
    phyGeneralExamination.setGait("Gait");
    phyGeneralExamination.setID(1L);
    phyGeneralExamination.setJaundice("Jaundice");
    phyGeneralExamination.setLastModDate(mock(Timestamp.class));
    phyGeneralExamination.setLymphadenopathy("Lymphadenopathy");
    phyGeneralExamination.setLymphnodesInvolved("Lymphnodes Involved");
    phyGeneralExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    phyGeneralExamination.setPallor("Pallor");
    phyGeneralExamination.setParkingPlaceID(1);
    phyGeneralExamination.setProcessed("Processed");
    phyGeneralExamination.setProviderServiceMapID(1);
    phyGeneralExamination.setQuickening("Quickening");
    phyGeneralExamination.setReservedForChange("Reserved For Change");
    phyGeneralExamination.setSyncedBy("Synced By");
    phyGeneralExamination.setSyncedDate(mock(Timestamp.class));
    phyGeneralExamination.setTypeOfDangerSign("Type Of Danger Sign");
    phyGeneralExamination.setTypeOfDangerSigns(new ArrayList<>());
    phyGeneralExamination.setTypeOfLymphadenopathy("Type Of Lymphadenopathy");
    phyGeneralExamination.setVanSerialNo(1L);
    phyGeneralExamination.setVehicalNo("Vehical No");
    phyGeneralExamination.setVisitCode(1L);
    when(phyGeneralExaminationRepo.save(Mockito.<PhyGeneralExamination>any())).thenReturn(phyGeneralExamination);

    PhyGeneralExamination generalExamination = new PhyGeneralExamination();
    generalExamination.setBenVisitID(1L);
    generalExamination.setBeneficiaryRegID(1L);
    generalExamination.setBuiltAndAppearance("Built And Appearance");
    generalExamination.setClubbing("Clubbing");
    generalExamination.setCoherence("Coherence");
    generalExamination.setComfortness("Comfortness");
    generalExamination.setConsciousness("Consciousness");
    generalExamination.setCooperation("Cooperation");
    generalExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    generalExamination.setCreatedDate(mock(Timestamp.class));
    generalExamination.setCyanosis("Cyanosis");
    generalExamination.setDangerSigns("Danger Signs");
    generalExamination.setDeleted(true);
    generalExamination.setEdema("Edema");
    generalExamination.setEdemaType("Edema Type");
    generalExamination.setExtentOfEdema("Extent Of Edema");
    generalExamination.setFoetalMovements("Foetal Movements");
    generalExamination.setGait("Gait");
    generalExamination.setID(1L);
    generalExamination.setJaundice("Jaundice");
    generalExamination.setLastModDate(mock(Timestamp.class));
    generalExamination.setLymphadenopathy("Lymphadenopathy");
    generalExamination.setLymphnodesInvolved("Lymphnodes Involved");
    generalExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    generalExamination.setPallor("Pallor");
    generalExamination.setParkingPlaceID(1);
    generalExamination.setProcessed("Processed");
    generalExamination.setProviderServiceMapID(1);
    generalExamination.setQuickening("Quickening");
    generalExamination.setReservedForChange("Reserved For Change");
    generalExamination.setSyncedBy("Synced By");
    generalExamination.setSyncedDate(mock(Timestamp.class));
    generalExamination.setTypeOfDangerSign("Type Of Danger Sign");
    generalExamination.setTypeOfDangerSigns(new ArrayList<>());
    generalExamination.setTypeOfLymphadenopathy("Type Of Lymphadenopathy");
    generalExamination.setVanSerialNo(1L);
    generalExamination.setVehicalNo("Vehical No");
    generalExamination.setVisitCode(1L);

    // Act
    Long actualSavePhyGeneralExaminationResult = commonNurseServiceImpl.savePhyGeneralExamination(generalExamination);

    // Assert
    verify(phyGeneralExaminationRepo).save(isA(PhyGeneralExamination.class));
    assertEquals(1L, actualSavePhyGeneralExaminationResult.longValue());
  }

  @Test
  void testSavePhyGeneralExamination2() throws IEMRException {
    // Arrange
    PhyGeneralExamination phyGeneralExamination = new PhyGeneralExamination();
    phyGeneralExamination.setBenVisitID(1L);
    phyGeneralExamination.setBeneficiaryRegID(1L);
    phyGeneralExamination.setBuiltAndAppearance("Built And Appearance");
    phyGeneralExamination.setClubbing("Clubbing");
    phyGeneralExamination.setCoherence("Coherence");
    phyGeneralExamination.setComfortness("Comfortness");
    phyGeneralExamination.setConsciousness("Consciousness");
    phyGeneralExamination.setCooperation("Cooperation");
    phyGeneralExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    phyGeneralExamination.setCreatedDate(mock(Timestamp.class));
    phyGeneralExamination.setCyanosis("Cyanosis");
    phyGeneralExamination.setDangerSigns("Danger Signs");
    phyGeneralExamination.setDeleted(true);
    phyGeneralExamination.setEdema("Edema");
    phyGeneralExamination.setEdemaType("Edema Type");
    phyGeneralExamination.setExtentOfEdema("Extent Of Edema");
    phyGeneralExamination.setFoetalMovements("Foetal Movements");
    phyGeneralExamination.setGait("Gait");
    phyGeneralExamination.setID(null);
    phyGeneralExamination.setJaundice("Jaundice");
    phyGeneralExamination.setLastModDate(mock(Timestamp.class));
    phyGeneralExamination.setLymphadenopathy("Lymphadenopathy");
    phyGeneralExamination.setLymphnodesInvolved("Lymphnodes Involved");
    phyGeneralExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    phyGeneralExamination.setPallor("Pallor");
    phyGeneralExamination.setParkingPlaceID(1);
    phyGeneralExamination.setProcessed("Processed");
    phyGeneralExamination.setProviderServiceMapID(1);
    phyGeneralExamination.setQuickening("Quickening");
    phyGeneralExamination.setReservedForChange("Reserved For Change");
    phyGeneralExamination.setSyncedBy("Synced By");
    phyGeneralExamination.setSyncedDate(mock(Timestamp.class));
    phyGeneralExamination.setTypeOfDangerSign("Type Of Danger Sign");
    phyGeneralExamination.setTypeOfDangerSigns(new ArrayList<>());
    phyGeneralExamination.setTypeOfLymphadenopathy("Type Of Lymphadenopathy");
    phyGeneralExamination.setVanSerialNo(1L);
    phyGeneralExamination.setVehicalNo("Vehical No");
    phyGeneralExamination.setVisitCode(1L);
    when(phyGeneralExaminationRepo.save(Mockito.<PhyGeneralExamination>any())).thenReturn(phyGeneralExamination);

    PhyGeneralExamination generalExamination = new PhyGeneralExamination();
    generalExamination.setBenVisitID(1L);
    generalExamination.setBeneficiaryRegID(1L);
    generalExamination.setBuiltAndAppearance("Built And Appearance");
    generalExamination.setClubbing("Clubbing");
    generalExamination.setCoherence("Coherence");
    generalExamination.setComfortness("Comfortness");
    generalExamination.setConsciousness("Consciousness");
    generalExamination.setCooperation("Cooperation");
    generalExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    generalExamination.setCreatedDate(mock(Timestamp.class));
    generalExamination.setCyanosis("Cyanosis");
    generalExamination.setDangerSigns("Danger Signs");
    generalExamination.setDeleted(true);
    generalExamination.setEdema("Edema");
    generalExamination.setEdemaType("Edema Type");
    generalExamination.setExtentOfEdema("Extent Of Edema");
    generalExamination.setFoetalMovements("Foetal Movements");
    generalExamination.setGait("Gait");
    generalExamination.setID(1L);
    generalExamination.setJaundice("Jaundice");
    generalExamination.setLastModDate(mock(Timestamp.class));
    generalExamination.setLymphadenopathy("Lymphadenopathy");
    generalExamination.setLymphnodesInvolved("Lymphnodes Involved");
    generalExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    generalExamination.setPallor("Pallor");
    generalExamination.setParkingPlaceID(1);
    generalExamination.setProcessed("Processed");
    generalExamination.setProviderServiceMapID(1);
    generalExamination.setQuickening("Quickening");
    generalExamination.setReservedForChange("Reserved For Change");
    generalExamination.setSyncedBy("Synced By");
    generalExamination.setSyncedDate(mock(Timestamp.class));
    generalExamination.setTypeOfDangerSign("Type Of Danger Sign");
    generalExamination.setTypeOfDangerSigns(new ArrayList<>());
    generalExamination.setTypeOfLymphadenopathy("Type Of Lymphadenopathy");
    generalExamination.setVanSerialNo(1L);
    generalExamination.setVehicalNo("Vehical No");
    generalExamination.setVisitCode(1L);

    // Act and Assert
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.savePhyGeneralExamination(generalExamination));
    verify(phyGeneralExaminationRepo).save(isA(PhyGeneralExamination.class));
  }

  @Test
  void testSavePhyHeadToToeExamination() throws IEMRException {
    // Arrange
    PhyHeadToToeExamination phyHeadToToeExamination = new PhyHeadToToeExamination();
    phyHeadToToeExamination.setBenVisitID(1L);
    phyHeadToToeExamination.setBeneficiaryRegID(1L);
    phyHeadToToeExamination.setBreastAndNipples("Breast And Nipples");
    phyHeadToToeExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    phyHeadToToeExamination.setCreatedDate(mock(Timestamp.class));
    phyHeadToToeExamination.setDeleted(true);
    phyHeadToToeExamination.setEars("Ears");
    phyHeadToToeExamination.setEyes("Eyes");
    phyHeadToToeExamination.setHair("Hair");
    phyHeadToToeExamination.setHead("Head");
    phyHeadToToeExamination.setID(1L);
    phyHeadToToeExamination.setLastModDate(mock(Timestamp.class));
    phyHeadToToeExamination.setLowerLimbs("Lower Limbs");
    phyHeadToToeExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    phyHeadToToeExamination.setNails("Nails");
    phyHeadToToeExamination.setNipples("Nipples");
    phyHeadToToeExamination.setNose("Nose");
    phyHeadToToeExamination.setOralCavity("Oral Cavity");
    phyHeadToToeExamination.setParkingPlaceID(1);
    phyHeadToToeExamination.setProcessed("Processed");
    phyHeadToToeExamination.setProviderServiceMapID(1);
    phyHeadToToeExamination.setReservedForChange("Reserved For Change");
    phyHeadToToeExamination.setSkin("Skin");
    phyHeadToToeExamination.setSyncedBy("Synced By");
    phyHeadToToeExamination.setSyncedDate(mock(Timestamp.class));
    phyHeadToToeExamination.setThroat("Throat");
    phyHeadToToeExamination.setTrunk("Trunk");
    phyHeadToToeExamination.setUpperLimbs("Upper Limbs");
    phyHeadToToeExamination.setVanID(1);
    phyHeadToToeExamination.setVanSerialNo(1L);
    phyHeadToToeExamination.setVehicalNo("Vehical No");
    phyHeadToToeExamination.setVisitCode(1L);
    when(phyHeadToToeExaminationRepo.save(Mockito.<PhyHeadToToeExamination>any())).thenReturn(phyHeadToToeExamination);

    PhyHeadToToeExamination headToToeExamination = new PhyHeadToToeExamination();
    headToToeExamination.setBenVisitID(1L);
    headToToeExamination.setBeneficiaryRegID(1L);
    headToToeExamination.setBreastAndNipples("Breast And Nipples");
    headToToeExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    headToToeExamination.setCreatedDate(mock(Timestamp.class));
    headToToeExamination.setDeleted(true);
    headToToeExamination.setEars("Ears");
    headToToeExamination.setEyes("Eyes");
    headToToeExamination.setHair("Hair");
    headToToeExamination.setHead("Head");
    headToToeExamination.setID(1L);
    headToToeExamination.setLastModDate(mock(Timestamp.class));
    headToToeExamination.setLowerLimbs("Lower Limbs");
    headToToeExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    headToToeExamination.setNails("Nails");
    headToToeExamination.setNipples("Nipples");
    headToToeExamination.setNose("Nose");
    headToToeExamination.setOralCavity("Oral Cavity");
    headToToeExamination.setParkingPlaceID(1);
    headToToeExamination.setProcessed("Processed");
    headToToeExamination.setProviderServiceMapID(1);
    headToToeExamination.setReservedForChange("Reserved For Change");
    headToToeExamination.setSkin("Skin");
    headToToeExamination.setSyncedBy("Synced By");
    headToToeExamination.setSyncedDate(mock(Timestamp.class));
    headToToeExamination.setThroat("Throat");
    headToToeExamination.setTrunk("Trunk");
    headToToeExamination.setUpperLimbs("Upper Limbs");
    headToToeExamination.setVanID(1);
    headToToeExamination.setVanSerialNo(1L);
    headToToeExamination.setVehicalNo("Vehical No");
    headToToeExamination.setVisitCode(1L);

    // Act
    Long actualSavePhyHeadToToeExaminationResult = commonNurseServiceImpl
        .savePhyHeadToToeExamination(headToToeExamination);

    // Assert
    verify(phyHeadToToeExaminationRepo).save(isA(PhyHeadToToeExamination.class));
    assertEquals(1L, actualSavePhyHeadToToeExaminationResult.longValue());
  }

  @Test
  void testSavePhyHeadToToeExamination2() throws IEMRException {
    // Arrange
    PhyHeadToToeExamination phyHeadToToeExamination = mock(PhyHeadToToeExamination.class);
    when(phyHeadToToeExamination.getID()).thenReturn(-1L);
    doNothing().when(phyHeadToToeExamination).setBenVisitID(Mockito.<Long>any());
    doNothing().when(phyHeadToToeExamination).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(phyHeadToToeExamination).setBreastAndNipples(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setCreatedBy(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(phyHeadToToeExamination).setDeleted(Mockito.<Boolean>any());
    doNothing().when(phyHeadToToeExamination).setEars(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setEyes(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setHair(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setHead(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setID(Mockito.<Long>any());
    doNothing().when(phyHeadToToeExamination).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(phyHeadToToeExamination).setLowerLimbs(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setModifiedBy(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setNails(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setNipples(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setNose(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setOralCavity(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(phyHeadToToeExamination).setProcessed(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(phyHeadToToeExamination).setReservedForChange(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setSkin(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setSyncedBy(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(phyHeadToToeExamination).setThroat(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setTrunk(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setUpperLimbs(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setVanID(Mockito.<Integer>any());
    doNothing().when(phyHeadToToeExamination).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(phyHeadToToeExamination).setVehicalNo(Mockito.<String>any());
    doNothing().when(phyHeadToToeExamination).setVisitCode(Mockito.<Long>any());
    phyHeadToToeExamination.setBenVisitID(1L);
    phyHeadToToeExamination.setBeneficiaryRegID(1L);
    phyHeadToToeExamination.setBreastAndNipples("Breast And Nipples");
    phyHeadToToeExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    phyHeadToToeExamination.setCreatedDate(mock(Timestamp.class));
    phyHeadToToeExamination.setDeleted(true);
    phyHeadToToeExamination.setEars("Ears");
    phyHeadToToeExamination.setEyes("Eyes");
    phyHeadToToeExamination.setHair("Hair");
    phyHeadToToeExamination.setHead("Head");
    phyHeadToToeExamination.setID(1L);
    phyHeadToToeExamination.setLastModDate(mock(Timestamp.class));
    phyHeadToToeExamination.setLowerLimbs("Lower Limbs");
    phyHeadToToeExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    phyHeadToToeExamination.setNails("Nails");
    phyHeadToToeExamination.setNipples("Nipples");
    phyHeadToToeExamination.setNose("Nose");
    phyHeadToToeExamination.setOralCavity("Oral Cavity");
    phyHeadToToeExamination.setParkingPlaceID(1);
    phyHeadToToeExamination.setProcessed("Processed");
    phyHeadToToeExamination.setProviderServiceMapID(1);
    phyHeadToToeExamination.setReservedForChange("Reserved For Change");
    phyHeadToToeExamination.setSkin("Skin");
    phyHeadToToeExamination.setSyncedBy("Synced By");
    phyHeadToToeExamination.setSyncedDate(mock(Timestamp.class));
    phyHeadToToeExamination.setThroat("Throat");
    phyHeadToToeExamination.setTrunk("Trunk");
    phyHeadToToeExamination.setUpperLimbs("Upper Limbs");
    phyHeadToToeExamination.setVanID(1);
    phyHeadToToeExamination.setVanSerialNo(1L);
    phyHeadToToeExamination.setVehicalNo("Vehical No");
    phyHeadToToeExamination.setVisitCode(1L);
    when(phyHeadToToeExaminationRepo.save(Mockito.<PhyHeadToToeExamination>any())).thenReturn(phyHeadToToeExamination);

    PhyHeadToToeExamination headToToeExamination = new PhyHeadToToeExamination();
    headToToeExamination.setBenVisitID(1L);
    headToToeExamination.setBeneficiaryRegID(1L);
    headToToeExamination.setBreastAndNipples("Breast And Nipples");
    headToToeExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    headToToeExamination.setCreatedDate(mock(Timestamp.class));
    headToToeExamination.setDeleted(true);
    headToToeExamination.setEars("Ears");
    headToToeExamination.setEyes("Eyes");
    headToToeExamination.setHair("Hair");
    headToToeExamination.setHead("Head");
    headToToeExamination.setID(1L);
    headToToeExamination.setLastModDate(mock(Timestamp.class));
    headToToeExamination.setLowerLimbs("Lower Limbs");
    headToToeExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    headToToeExamination.setNails("Nails");
    headToToeExamination.setNipples("Nipples");
    headToToeExamination.setNose("Nose");
    headToToeExamination.setOralCavity("Oral Cavity");
    headToToeExamination.setParkingPlaceID(1);
    headToToeExamination.setProcessed("Processed");
    headToToeExamination.setProviderServiceMapID(1);
    headToToeExamination.setReservedForChange("Reserved For Change");
    headToToeExamination.setSkin("Skin");
    headToToeExamination.setSyncedBy("Synced By");
    headToToeExamination.setSyncedDate(mock(Timestamp.class));
    headToToeExamination.setThroat("Throat");
    headToToeExamination.setTrunk("Trunk");
    headToToeExamination.setUpperLimbs("Upper Limbs");
    headToToeExamination.setVanID(1);
    headToToeExamination.setVanSerialNo(1L);
    headToToeExamination.setVehicalNo("Vehical No");
    headToToeExamination.setVisitCode(1L);

    // Act and Assert
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.savePhyHeadToToeExamination(headToToeExamination));
    verify(phyHeadToToeExamination).getID();
    verify(phyHeadToToeExamination).setBenVisitID(isA(Long.class));
    verify(phyHeadToToeExamination).setBeneficiaryRegID(isA(Long.class));
    verify(phyHeadToToeExamination).setBreastAndNipples(eq("Breast And Nipples"));
    verify(phyHeadToToeExamination).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(phyHeadToToeExamination).setCreatedDate(isA(Timestamp.class));
    verify(phyHeadToToeExamination).setDeleted(isA(Boolean.class));
    verify(phyHeadToToeExamination).setEars(eq("Ears"));
    verify(phyHeadToToeExamination).setEyes(eq("Eyes"));
    verify(phyHeadToToeExamination).setHair(eq("Hair"));
    verify(phyHeadToToeExamination).setHead(eq("Head"));
    verify(phyHeadToToeExamination).setID(isA(Long.class));
    verify(phyHeadToToeExamination).setLastModDate(isA(Timestamp.class));
    verify(phyHeadToToeExamination).setLowerLimbs(eq("Lower Limbs"));
    verify(phyHeadToToeExamination).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(phyHeadToToeExamination).setNails(eq("Nails"));
    verify(phyHeadToToeExamination).setNipples(eq("Nipples"));
    verify(phyHeadToToeExamination).setNose(eq("Nose"));
    verify(phyHeadToToeExamination).setOralCavity(eq("Oral Cavity"));
    verify(phyHeadToToeExamination).setParkingPlaceID(isA(Integer.class));
    verify(phyHeadToToeExamination).setProcessed(eq("Processed"));
    verify(phyHeadToToeExamination).setProviderServiceMapID(isA(Integer.class));
    verify(phyHeadToToeExamination).setReservedForChange(eq("Reserved For Change"));
    verify(phyHeadToToeExamination).setSkin(eq("Skin"));
    verify(phyHeadToToeExamination).setSyncedBy(eq("Synced By"));
    verify(phyHeadToToeExamination).setSyncedDate(isA(Timestamp.class));
    verify(phyHeadToToeExamination).setThroat(eq("Throat"));
    verify(phyHeadToToeExamination).setTrunk(eq("Trunk"));
    verify(phyHeadToToeExamination).setUpperLimbs(eq("Upper Limbs"));
    verify(phyHeadToToeExamination).setVanID(isA(Integer.class));
    verify(phyHeadToToeExamination).setVanSerialNo(isA(Long.class));
    verify(phyHeadToToeExamination).setVehicalNo(eq("Vehical No"));
    verify(phyHeadToToeExamination).setVisitCode(isA(Long.class));
    verify(phyHeadToToeExaminationRepo).save(isA(PhyHeadToToeExamination.class));
  }

  @Test
  void testSaveSysGastrointestinalExamination() throws IEMRException {
    // Arrange
    SysGastrointestinalExamination sysGastrointestinalExamination = new SysGastrointestinalExamination();
    sysGastrointestinalExamination.setAnalRegion("us-east-2");
    sysGastrointestinalExamination.setAuscultation("Auscultation");
    sysGastrointestinalExamination.setBenVisitID(1L);
    sysGastrointestinalExamination.setBeneficiaryRegID(1L);
    sysGastrointestinalExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysGastrointestinalExamination.setCreatedDate(mock(Timestamp.class));
    sysGastrointestinalExamination.setDeleted(true);
    sysGastrointestinalExamination.setID(1L);
    sysGastrointestinalExamination.setInspection("Inspection");
    sysGastrointestinalExamination.setLastModDate(mock(Timestamp.class));
    sysGastrointestinalExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysGastrointestinalExamination.setPalpation("Palpation");
    sysGastrointestinalExamination.setPalpation_AbdomenTexture("Palpation Abdomen Texture");
    sysGastrointestinalExamination.setPalpation_Liver("Palpation Liver");
    sysGastrointestinalExamination.setPalpation_LocationOfTenderness("Palpation Location Of Tenderness");
    sysGastrointestinalExamination.setPalpation_Spleen("Palpation Spleen");
    sysGastrointestinalExamination.setPalpation_Tenderness("Palpation Tenderness");
    sysGastrointestinalExamination.setParkingPlaceID(1);
    sysGastrointestinalExamination.setPercussion("Percussion");
    sysGastrointestinalExamination.setProcessed("Processed");
    sysGastrointestinalExamination.setProviderServiceMapID(1);
    sysGastrointestinalExamination.setReservedForChange("Reserved For Change");
    sysGastrointestinalExamination.setSyncedBy("Synced By");
    sysGastrointestinalExamination.setSyncedDate(mock(Timestamp.class));
    sysGastrointestinalExamination.setVanSerialNo(1L);
    sysGastrointestinalExamination.setVehicalNo("Vehical No");
    sysGastrointestinalExamination.setVisitCode(1L);
    when(sysGastrointestinalExaminationRepo.save(Mockito.<SysGastrointestinalExamination>any()))
        .thenReturn(sysGastrointestinalExamination);

    SysGastrointestinalExamination gastrointestinalExamination = new SysGastrointestinalExamination();
    gastrointestinalExamination.setAnalRegion("us-east-2");
    gastrointestinalExamination.setAuscultation("Auscultation");
    gastrointestinalExamination.setBenVisitID(1L);
    gastrointestinalExamination.setBeneficiaryRegID(1L);
    gastrointestinalExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    gastrointestinalExamination.setCreatedDate(mock(Timestamp.class));
    gastrointestinalExamination.setDeleted(true);
    gastrointestinalExamination.setID(1L);
    gastrointestinalExamination.setInspection("Inspection");
    gastrointestinalExamination.setLastModDate(mock(Timestamp.class));
    gastrointestinalExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    gastrointestinalExamination.setPalpation("Palpation");
    gastrointestinalExamination.setPalpation_AbdomenTexture("Palpation Abdomen Texture");
    gastrointestinalExamination.setPalpation_Liver("Palpation Liver");
    gastrointestinalExamination.setPalpation_LocationOfTenderness("Palpation Location Of Tenderness");
    gastrointestinalExamination.setPalpation_Spleen("Palpation Spleen");
    gastrointestinalExamination.setPalpation_Tenderness("Palpation Tenderness");
    gastrointestinalExamination.setParkingPlaceID(1);
    gastrointestinalExamination.setPercussion("Percussion");
    gastrointestinalExamination.setProcessed("Processed");
    gastrointestinalExamination.setProviderServiceMapID(1);
    gastrointestinalExamination.setReservedForChange("Reserved For Change");
    gastrointestinalExamination.setSyncedBy("Synced By");
    gastrointestinalExamination.setSyncedDate(mock(Timestamp.class));
    gastrointestinalExamination.setVanSerialNo(1L);
    gastrointestinalExamination.setVehicalNo("Vehical No");
    gastrointestinalExamination.setVisitCode(1L);

    // Act
    Long actualSaveSysGastrointestinalExaminationResult = commonNurseServiceImpl
        .saveSysGastrointestinalExamination(gastrointestinalExamination);

    // Assert
    verify(sysGastrointestinalExaminationRepo).save(isA(SysGastrointestinalExamination.class));
    assertEquals(1L, actualSaveSysGastrointestinalExaminationResult.longValue());
  }

  @Test
  void testSaveSysGastrointestinalExamination2() throws IEMRException {
    // Arrange
    SysGastrointestinalExamination sysGastrointestinalExamination = mock(SysGastrointestinalExamination.class);
    when(sysGastrointestinalExamination.getID()).thenReturn(-1L);
    doNothing().when(sysGastrointestinalExamination).setAnalRegion(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setAuscultation(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setBenVisitID(Mockito.<Long>any());
    doNothing().when(sysGastrointestinalExamination).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(sysGastrointestinalExamination).setCreatedBy(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(sysGastrointestinalExamination).setDeleted(Mockito.<Boolean>any());
    doNothing().when(sysGastrointestinalExamination).setID(Mockito.<Long>any());
    doNothing().when(sysGastrointestinalExamination).setInspection(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(sysGastrointestinalExamination).setModifiedBy(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setPalpation(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setPalpation_AbdomenTexture(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setPalpation_Liver(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setPalpation_LocationOfTenderness(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setPalpation_Spleen(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setPalpation_Tenderness(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(sysGastrointestinalExamination).setPercussion(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setProcessed(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(sysGastrointestinalExamination).setReservedForChange(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setSyncedBy(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(sysGastrointestinalExamination).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(sysGastrointestinalExamination).setVehicalNo(Mockito.<String>any());
    doNothing().when(sysGastrointestinalExamination).setVisitCode(Mockito.<Long>any());
    sysGastrointestinalExamination.setAnalRegion("us-east-2");
    sysGastrointestinalExamination.setAuscultation("Auscultation");
    sysGastrointestinalExamination.setBenVisitID(1L);
    sysGastrointestinalExamination.setBeneficiaryRegID(1L);
    sysGastrointestinalExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysGastrointestinalExamination.setCreatedDate(mock(Timestamp.class));
    sysGastrointestinalExamination.setDeleted(true);
    sysGastrointestinalExamination.setID(1L);
    sysGastrointestinalExamination.setInspection("Inspection");
    sysGastrointestinalExamination.setLastModDate(mock(Timestamp.class));
    sysGastrointestinalExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysGastrointestinalExamination.setPalpation("Palpation");
    sysGastrointestinalExamination.setPalpation_AbdomenTexture("Palpation Abdomen Texture");
    sysGastrointestinalExamination.setPalpation_Liver("Palpation Liver");
    sysGastrointestinalExamination.setPalpation_LocationOfTenderness("Palpation Location Of Tenderness");
    sysGastrointestinalExamination.setPalpation_Spleen("Palpation Spleen");
    sysGastrointestinalExamination.setPalpation_Tenderness("Palpation Tenderness");
    sysGastrointestinalExamination.setParkingPlaceID(1);
    sysGastrointestinalExamination.setPercussion("Percussion");
    sysGastrointestinalExamination.setProcessed("Processed");
    sysGastrointestinalExamination.setProviderServiceMapID(1);
    sysGastrointestinalExamination.setReservedForChange("Reserved For Change");
    sysGastrointestinalExamination.setSyncedBy("Synced By");
    sysGastrointestinalExamination.setSyncedDate(mock(Timestamp.class));
    sysGastrointestinalExamination.setVanSerialNo(1L);
    sysGastrointestinalExamination.setVehicalNo("Vehical No");
    sysGastrointestinalExamination.setVisitCode(1L);
    when(sysGastrointestinalExaminationRepo.save(Mockito.<SysGastrointestinalExamination>any()))
        .thenReturn(sysGastrointestinalExamination);

    SysGastrointestinalExamination gastrointestinalExamination = new SysGastrointestinalExamination();
    gastrointestinalExamination.setAnalRegion("us-east-2");
    gastrointestinalExamination.setAuscultation("Auscultation");
    gastrointestinalExamination.setBenVisitID(1L);
    gastrointestinalExamination.setBeneficiaryRegID(1L);
    gastrointestinalExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    gastrointestinalExamination.setCreatedDate(mock(Timestamp.class));
    gastrointestinalExamination.setDeleted(true);
    gastrointestinalExamination.setID(1L);
    gastrointestinalExamination.setInspection("Inspection");
    gastrointestinalExamination.setLastModDate(mock(Timestamp.class));
    gastrointestinalExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    gastrointestinalExamination.setPalpation("Palpation");
    gastrointestinalExamination.setPalpation_AbdomenTexture("Palpation Abdomen Texture");
    gastrointestinalExamination.setPalpation_Liver("Palpation Liver");
    gastrointestinalExamination.setPalpation_LocationOfTenderness("Palpation Location Of Tenderness");
    gastrointestinalExamination.setPalpation_Spleen("Palpation Spleen");
    gastrointestinalExamination.setPalpation_Tenderness("Palpation Tenderness");
    gastrointestinalExamination.setParkingPlaceID(1);
    gastrointestinalExamination.setPercussion("Percussion");
    gastrointestinalExamination.setProcessed("Processed");
    gastrointestinalExamination.setProviderServiceMapID(1);
    gastrointestinalExamination.setReservedForChange("Reserved For Change");
    gastrointestinalExamination.setSyncedBy("Synced By");
    gastrointestinalExamination.setSyncedDate(mock(Timestamp.class));
    gastrointestinalExamination.setVanSerialNo(1L);
    gastrointestinalExamination.setVehicalNo("Vehical No");
    gastrointestinalExamination.setVisitCode(1L);

    // Act and Assert
    assertThrows(IEMRException.class,
        () -> commonNurseServiceImpl.saveSysGastrointestinalExamination(gastrointestinalExamination));
    verify(sysGastrointestinalExamination).getID();
    verify(sysGastrointestinalExamination).setAnalRegion(eq("us-east-2"));
    verify(sysGastrointestinalExamination).setAuscultation(eq("Auscultation"));
    verify(sysGastrointestinalExamination).setBenVisitID(isA(Long.class));
    verify(sysGastrointestinalExamination).setBeneficiaryRegID(isA(Long.class));
    verify(sysGastrointestinalExamination).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(sysGastrointestinalExamination).setCreatedDate(isA(Timestamp.class));
    verify(sysGastrointestinalExamination).setDeleted(isA(Boolean.class));
    verify(sysGastrointestinalExamination).setID(isA(Long.class));
    verify(sysGastrointestinalExamination).setInspection(eq("Inspection"));
    verify(sysGastrointestinalExamination).setLastModDate(isA(Timestamp.class));
    verify(sysGastrointestinalExamination).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(sysGastrointestinalExamination).setPalpation(eq("Palpation"));
    verify(sysGastrointestinalExamination).setPalpation_AbdomenTexture(eq("Palpation Abdomen Texture"));
    verify(sysGastrointestinalExamination).setPalpation_Liver(eq("Palpation Liver"));
    verify(sysGastrointestinalExamination).setPalpation_LocationOfTenderness(eq("Palpation Location Of Tenderness"));
    verify(sysGastrointestinalExamination).setPalpation_Spleen(eq("Palpation Spleen"));
    verify(sysGastrointestinalExamination).setPalpation_Tenderness(eq("Palpation Tenderness"));
    verify(sysGastrointestinalExamination).setParkingPlaceID(isA(Integer.class));
    verify(sysGastrointestinalExamination).setPercussion(eq("Percussion"));
    verify(sysGastrointestinalExamination).setProcessed(eq("Processed"));
    verify(sysGastrointestinalExamination).setProviderServiceMapID(isA(Integer.class));
    verify(sysGastrointestinalExamination).setReservedForChange(eq("Reserved For Change"));
    verify(sysGastrointestinalExamination).setSyncedBy(eq("Synced By"));
    verify(sysGastrointestinalExamination).setSyncedDate(isA(Timestamp.class));
    verify(sysGastrointestinalExamination).setVanSerialNo(isA(Long.class));
    verify(sysGastrointestinalExamination).setVehicalNo(eq("Vehical No"));
    verify(sysGastrointestinalExamination).setVisitCode(isA(Long.class));
    verify(sysGastrointestinalExaminationRepo).save(isA(SysGastrointestinalExamination.class));
  }

  @Test
  void testSaveSysCardiovascularExamination() throws IEMRException {
    // Arrange
    SysCardiovascularExamination sysCardiovascularExamination = new SysCardiovascularExamination();
    sysCardiovascularExamination.setAdditionalHeartSounds("Additional Heart Sounds");
    sysCardiovascularExamination.setApexbeatLocation("Apexbeat Location");
    sysCardiovascularExamination.setApexbeatType("Apexbeat Type");
    sysCardiovascularExamination.setBenVisitID(1L);
    sysCardiovascularExamination.setBeneficiaryRegID(1L);
    sysCardiovascularExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysCardiovascularExamination.setCreatedDate(mock(Timestamp.class));
    sysCardiovascularExamination.setDeleted(true);
    sysCardiovascularExamination.setFirstHeartSound_S1("First Heart Sound S1");
    sysCardiovascularExamination.setID(1L);
    sysCardiovascularExamination.setJugularVenousPulse_JVP("Jugular Venous Pulse JVP");
    sysCardiovascularExamination.setLastModDate(mock(Timestamp.class));
    sysCardiovascularExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysCardiovascularExamination.setMurmurs("Murmurs");
    sysCardiovascularExamination.setParkingPlaceID(1);
    sysCardiovascularExamination.setPericardialRub("Pericardial Rub");
    sysCardiovascularExamination.setProcessed("Processed");
    sysCardiovascularExamination.setProviderServiceMapID(1);
    sysCardiovascularExamination.setReservedForChange("Reserved For Change");
    sysCardiovascularExamination.setSecondHeartSound_S2("Second Heart Sound S2");
    sysCardiovascularExamination.setSyncedBy("Synced By");
    sysCardiovascularExamination.setSyncedDate(mock(Timestamp.class));
    sysCardiovascularExamination.setVanSerialNo(1L);
    sysCardiovascularExamination.setVehicalNo("Vehical No");
    sysCardiovascularExamination.setVisitCode(1L);
    when(sysCardiovascularExaminationRepo.save(Mockito.<SysCardiovascularExamination>any()))
        .thenReturn(sysCardiovascularExamination);

    SysCardiovascularExamination cardiovascularExamination = new SysCardiovascularExamination();
    cardiovascularExamination.setAdditionalHeartSounds("Additional Heart Sounds");
    cardiovascularExamination.setApexbeatLocation("Apexbeat Location");
    cardiovascularExamination.setApexbeatType("Apexbeat Type");
    cardiovascularExamination.setBenVisitID(1L);
    cardiovascularExamination.setBeneficiaryRegID(1L);
    cardiovascularExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    cardiovascularExamination.setCreatedDate(mock(Timestamp.class));
    cardiovascularExamination.setDeleted(true);
    cardiovascularExamination.setFirstHeartSound_S1("First Heart Sound S1");
    cardiovascularExamination.setID(1L);
    cardiovascularExamination.setJugularVenousPulse_JVP("Jugular Venous Pulse JVP");
    cardiovascularExamination.setLastModDate(mock(Timestamp.class));
    cardiovascularExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    cardiovascularExamination.setMurmurs("Murmurs");
    cardiovascularExamination.setParkingPlaceID(1);
    cardiovascularExamination.setPericardialRub("Pericardial Rub");
    cardiovascularExamination.setProcessed("Processed");
    cardiovascularExamination.setProviderServiceMapID(1);
    cardiovascularExamination.setReservedForChange("Reserved For Change");
    cardiovascularExamination.setSecondHeartSound_S2("Second Heart Sound S2");
    cardiovascularExamination.setSyncedBy("Synced By");
    cardiovascularExamination.setSyncedDate(mock(Timestamp.class));
    cardiovascularExamination.setVanSerialNo(1L);
    cardiovascularExamination.setVehicalNo("Vehical No");
    cardiovascularExamination.setVisitCode(1L);

    // Act
    Long actualSaveSysCardiovascularExaminationResult = commonNurseServiceImpl
        .saveSysCardiovascularExamination(cardiovascularExamination);

    // Assert
    verify(sysCardiovascularExaminationRepo).save(isA(SysCardiovascularExamination.class));
    assertEquals(1L, actualSaveSysCardiovascularExaminationResult.longValue());
  }

  @Test
  void testSaveSysCardiovascularExamination2() throws IEMRException {
    // Arrange
    SysCardiovascularExamination sysCardiovascularExamination = mock(SysCardiovascularExamination.class);
    when(sysCardiovascularExamination.getID()).thenReturn(-1L);
    doNothing().when(sysCardiovascularExamination).setAdditionalHeartSounds(Mockito.<String>any());
    doNothing().when(sysCardiovascularExamination).setApexbeatLocation(Mockito.<String>any());
    doNothing().when(sysCardiovascularExamination).setApexbeatType(Mockito.<String>any());
    doNothing().when(sysCardiovascularExamination).setBenVisitID(Mockito.<Long>any());
    doNothing().when(sysCardiovascularExamination).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(sysCardiovascularExamination).setCreatedBy(Mockito.<String>any());
    doNothing().when(sysCardiovascularExamination).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(sysCardiovascularExamination).setDeleted(Mockito.<Boolean>any());
    doNothing().when(sysCardiovascularExamination).setFirstHeartSound_S1(Mockito.<String>any());
    doNothing().when(sysCardiovascularExamination).setID(Mockito.<Long>any());
    doNothing().when(sysCardiovascularExamination).setJugularVenousPulse_JVP(Mockito.<String>any());
    doNothing().when(sysCardiovascularExamination).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(sysCardiovascularExamination).setModifiedBy(Mockito.<String>any());
    doNothing().when(sysCardiovascularExamination).setMurmurs(Mockito.<String>any());
    doNothing().when(sysCardiovascularExamination).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(sysCardiovascularExamination).setPericardialRub(Mockito.<String>any());
    doNothing().when(sysCardiovascularExamination).setProcessed(Mockito.<String>any());
    doNothing().when(sysCardiovascularExamination).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(sysCardiovascularExamination).setReservedForChange(Mockito.<String>any());
    doNothing().when(sysCardiovascularExamination).setSecondHeartSound_S2(Mockito.<String>any());
    doNothing().when(sysCardiovascularExamination).setSyncedBy(Mockito.<String>any());
    doNothing().when(sysCardiovascularExamination).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(sysCardiovascularExamination).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(sysCardiovascularExamination).setVehicalNo(Mockito.<String>any());
    doNothing().when(sysCardiovascularExamination).setVisitCode(Mockito.<Long>any());
    sysCardiovascularExamination.setAdditionalHeartSounds("Additional Heart Sounds");
    sysCardiovascularExamination.setApexbeatLocation("Apexbeat Location");
    sysCardiovascularExamination.setApexbeatType("Apexbeat Type");
    sysCardiovascularExamination.setBenVisitID(1L);
    sysCardiovascularExamination.setBeneficiaryRegID(1L);
    sysCardiovascularExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysCardiovascularExamination.setCreatedDate(mock(Timestamp.class));
    sysCardiovascularExamination.setDeleted(true);
    sysCardiovascularExamination.setFirstHeartSound_S1("First Heart Sound S1");
    sysCardiovascularExamination.setID(1L);
    sysCardiovascularExamination.setJugularVenousPulse_JVP("Jugular Venous Pulse JVP");
    sysCardiovascularExamination.setLastModDate(mock(Timestamp.class));
    sysCardiovascularExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysCardiovascularExamination.setMurmurs("Murmurs");
    sysCardiovascularExamination.setParkingPlaceID(1);
    sysCardiovascularExamination.setPericardialRub("Pericardial Rub");
    sysCardiovascularExamination.setProcessed("Processed");
    sysCardiovascularExamination.setProviderServiceMapID(1);
    sysCardiovascularExamination.setReservedForChange("Reserved For Change");
    sysCardiovascularExamination.setSecondHeartSound_S2("Second Heart Sound S2");
    sysCardiovascularExamination.setSyncedBy("Synced By");
    sysCardiovascularExamination.setSyncedDate(mock(Timestamp.class));
    sysCardiovascularExamination.setVanSerialNo(1L);
    sysCardiovascularExamination.setVehicalNo("Vehical No");
    sysCardiovascularExamination.setVisitCode(1L);
    when(sysCardiovascularExaminationRepo.save(Mockito.<SysCardiovascularExamination>any()))
        .thenReturn(sysCardiovascularExamination);

    SysCardiovascularExamination cardiovascularExamination = new SysCardiovascularExamination();
    cardiovascularExamination.setAdditionalHeartSounds("Additional Heart Sounds");
    cardiovascularExamination.setApexbeatLocation("Apexbeat Location");
    cardiovascularExamination.setApexbeatType("Apexbeat Type");
    cardiovascularExamination.setBenVisitID(1L);
    cardiovascularExamination.setBeneficiaryRegID(1L);
    cardiovascularExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    cardiovascularExamination.setCreatedDate(mock(Timestamp.class));
    cardiovascularExamination.setDeleted(true);
    cardiovascularExamination.setFirstHeartSound_S1("First Heart Sound S1");
    cardiovascularExamination.setID(1L);
    cardiovascularExamination.setJugularVenousPulse_JVP("Jugular Venous Pulse JVP");
    cardiovascularExamination.setLastModDate(mock(Timestamp.class));
    cardiovascularExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    cardiovascularExamination.setMurmurs("Murmurs");
    cardiovascularExamination.setParkingPlaceID(1);
    cardiovascularExamination.setPericardialRub("Pericardial Rub");
    cardiovascularExamination.setProcessed("Processed");
    cardiovascularExamination.setProviderServiceMapID(1);
    cardiovascularExamination.setReservedForChange("Reserved For Change");
    cardiovascularExamination.setSecondHeartSound_S2("Second Heart Sound S2");
    cardiovascularExamination.setSyncedBy("Synced By");
    cardiovascularExamination.setSyncedDate(mock(Timestamp.class));
    cardiovascularExamination.setVanSerialNo(1L);
    cardiovascularExamination.setVehicalNo("Vehical No");
    cardiovascularExamination.setVisitCode(1L);

    // Act and Assert
    assertThrows(IEMRException.class,
        () -> commonNurseServiceImpl.saveSysCardiovascularExamination(cardiovascularExamination));
    verify(sysCardiovascularExamination).getID();
    verify(sysCardiovascularExamination).setAdditionalHeartSounds(eq("Additional Heart Sounds"));
    verify(sysCardiovascularExamination).setApexbeatLocation(eq("Apexbeat Location"));
    verify(sysCardiovascularExamination).setApexbeatType(eq("Apexbeat Type"));
    verify(sysCardiovascularExamination).setBenVisitID(isA(Long.class));
    verify(sysCardiovascularExamination).setBeneficiaryRegID(isA(Long.class));
    verify(sysCardiovascularExamination).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(sysCardiovascularExamination).setCreatedDate(isA(Timestamp.class));
    verify(sysCardiovascularExamination).setDeleted(isA(Boolean.class));
    verify(sysCardiovascularExamination).setFirstHeartSound_S1(eq("First Heart Sound S1"));
    verify(sysCardiovascularExamination).setID(isA(Long.class));
    verify(sysCardiovascularExamination).setJugularVenousPulse_JVP(eq("Jugular Venous Pulse JVP"));
    verify(sysCardiovascularExamination).setLastModDate(isA(Timestamp.class));
    verify(sysCardiovascularExamination).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(sysCardiovascularExamination).setMurmurs(eq("Murmurs"));
    verify(sysCardiovascularExamination).setParkingPlaceID(isA(Integer.class));
    verify(sysCardiovascularExamination).setPericardialRub(eq("Pericardial Rub"));
    verify(sysCardiovascularExamination).setProcessed(eq("Processed"));
    verify(sysCardiovascularExamination).setProviderServiceMapID(isA(Integer.class));
    verify(sysCardiovascularExamination).setReservedForChange(eq("Reserved For Change"));
    verify(sysCardiovascularExamination).setSecondHeartSound_S2(eq("Second Heart Sound S2"));
    verify(sysCardiovascularExamination).setSyncedBy(eq("Synced By"));
    verify(sysCardiovascularExamination).setSyncedDate(isA(Timestamp.class));
    verify(sysCardiovascularExamination).setVanSerialNo(isA(Long.class));
    verify(sysCardiovascularExamination).setVehicalNo(eq("Vehical No"));
    verify(sysCardiovascularExamination).setVisitCode(isA(Long.class));
    verify(sysCardiovascularExaminationRepo).save(isA(SysCardiovascularExamination.class));
  }

  @Test
  void testSaveSysRespiratoryExamination() throws IEMRException {
    // Arrange
    SysRespiratoryExamination sysRespiratoryExamination = new SysRespiratoryExamination();
    sysRespiratoryExamination.setAuscultation("Auscultation");
    sysRespiratoryExamination.setAuscultation_BreathSounds("Auscultation Breath Sounds");
    sysRespiratoryExamination.setAuscultation_ConductedSounds("Auscultation Conducted Sounds");
    sysRespiratoryExamination.setAuscultation_Crepitations("Auscultation Crepitations");
    sysRespiratoryExamination.setAuscultation_PleuralRub("Auscultation Pleural Rub");
    sysRespiratoryExamination.setAuscultation_Stridor("Auscultation Stridor");
    sysRespiratoryExamination.setAuscultation_Wheezing("Auscultation Wheezing");
    sysRespiratoryExamination.setBenVisitID(1L);
    sysRespiratoryExamination.setBeneficiaryRegID(1L);
    sysRespiratoryExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysRespiratoryExamination.setCreatedDate(mock(Timestamp.class));
    sysRespiratoryExamination.setDeleted(true);
    sysRespiratoryExamination.setID(1L);
    sysRespiratoryExamination.setInspection("Inspection");
    sysRespiratoryExamination.setLastModDate(mock(Timestamp.class));
    sysRespiratoryExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysRespiratoryExamination.setPalpation("Palpation");
    sysRespiratoryExamination.setParkingPlaceID(1);
    sysRespiratoryExamination.setPercussion("Percussion");
    sysRespiratoryExamination.setProcessed("Processed");
    sysRespiratoryExamination.setProviderServiceMapID(1);
    sysRespiratoryExamination.setReservedForChange("Reserved For Change");
    sysRespiratoryExamination.setSignsOfRespiratoryDistress("Signs Of Respiratory Distress");
    sysRespiratoryExamination.setSyncedBy("Synced By");
    sysRespiratoryExamination.setSyncedDate(mock(Timestamp.class));
    sysRespiratoryExamination.setTrachea("Trachea");
    sysRespiratoryExamination.setVanSerialNo(1L);
    sysRespiratoryExamination.setVehicalNo("Vehical No");
    sysRespiratoryExamination.setVisitCode(1L);
    when(sysRespiratoryExaminationRepo.save(Mockito.<SysRespiratoryExamination>any()))
        .thenReturn(sysRespiratoryExamination);

    SysRespiratoryExamination respiratoryExamination = new SysRespiratoryExamination();
    respiratoryExamination.setAuscultation("Auscultation");
    respiratoryExamination.setAuscultation_BreathSounds("Auscultation Breath Sounds");
    respiratoryExamination.setAuscultation_ConductedSounds("Auscultation Conducted Sounds");
    respiratoryExamination.setAuscultation_Crepitations("Auscultation Crepitations");
    respiratoryExamination.setAuscultation_PleuralRub("Auscultation Pleural Rub");
    respiratoryExamination.setAuscultation_Stridor("Auscultation Stridor");
    respiratoryExamination.setAuscultation_Wheezing("Auscultation Wheezing");
    respiratoryExamination.setBenVisitID(1L);
    respiratoryExamination.setBeneficiaryRegID(1L);
    respiratoryExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    respiratoryExamination.setCreatedDate(mock(Timestamp.class));
    respiratoryExamination.setDeleted(true);
    respiratoryExamination.setID(1L);
    respiratoryExamination.setInspection("Inspection");
    respiratoryExamination.setLastModDate(mock(Timestamp.class));
    respiratoryExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    respiratoryExamination.setPalpation("Palpation");
    respiratoryExamination.setParkingPlaceID(1);
    respiratoryExamination.setPercussion("Percussion");
    respiratoryExamination.setProcessed("Processed");
    respiratoryExamination.setProviderServiceMapID(1);
    respiratoryExamination.setReservedForChange("Reserved For Change");
    respiratoryExamination.setSignsOfRespiratoryDistress("Signs Of Respiratory Distress");
    respiratoryExamination.setSyncedBy("Synced By");
    respiratoryExamination.setSyncedDate(mock(Timestamp.class));
    respiratoryExamination.setTrachea("Trachea");
    respiratoryExamination.setVanSerialNo(1L);
    respiratoryExamination.setVehicalNo("Vehical No");
    respiratoryExamination.setVisitCode(1L);

    // Act
    Long actualSaveSysRespiratoryExaminationResult = commonNurseServiceImpl
        .saveSysRespiratoryExamination(respiratoryExamination);

    // Assert
    verify(sysRespiratoryExaminationRepo).save(isA(SysRespiratoryExamination.class));
    assertEquals(1L, actualSaveSysRespiratoryExaminationResult.longValue());
  }

  @Test
  void testSaveSysRespiratoryExamination2() throws IEMRException {
    // Arrange
    SysRespiratoryExamination sysRespiratoryExamination = mock(SysRespiratoryExamination.class);
    when(sysRespiratoryExamination.getID()).thenReturn(-1L);
    doNothing().when(sysRespiratoryExamination).setAuscultation(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setAuscultation_BreathSounds(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setAuscultation_ConductedSounds(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setAuscultation_Crepitations(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setAuscultation_PleuralRub(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setAuscultation_Stridor(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setAuscultation_Wheezing(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setBenVisitID(Mockito.<Long>any());
    doNothing().when(sysRespiratoryExamination).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(sysRespiratoryExamination).setCreatedBy(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(sysRespiratoryExamination).setDeleted(Mockito.<Boolean>any());
    doNothing().when(sysRespiratoryExamination).setID(Mockito.<Long>any());
    doNothing().when(sysRespiratoryExamination).setInspection(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(sysRespiratoryExamination).setModifiedBy(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setPalpation(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(sysRespiratoryExamination).setPercussion(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setProcessed(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(sysRespiratoryExamination).setReservedForChange(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setSignsOfRespiratoryDistress(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setSyncedBy(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(sysRespiratoryExamination).setTrachea(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(sysRespiratoryExamination).setVehicalNo(Mockito.<String>any());
    doNothing().when(sysRespiratoryExamination).setVisitCode(Mockito.<Long>any());
    sysRespiratoryExamination.setAuscultation("Auscultation");
    sysRespiratoryExamination.setAuscultation_BreathSounds("Auscultation Breath Sounds");
    sysRespiratoryExamination.setAuscultation_ConductedSounds("Auscultation Conducted Sounds");
    sysRespiratoryExamination.setAuscultation_Crepitations("Auscultation Crepitations");
    sysRespiratoryExamination.setAuscultation_PleuralRub("Auscultation Pleural Rub");
    sysRespiratoryExamination.setAuscultation_Stridor("Auscultation Stridor");
    sysRespiratoryExamination.setAuscultation_Wheezing("Auscultation Wheezing");
    sysRespiratoryExamination.setBenVisitID(1L);
    sysRespiratoryExamination.setBeneficiaryRegID(1L);
    sysRespiratoryExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysRespiratoryExamination.setCreatedDate(mock(Timestamp.class));
    sysRespiratoryExamination.setDeleted(true);
    sysRespiratoryExamination.setID(1L);
    sysRespiratoryExamination.setInspection("Inspection");
    sysRespiratoryExamination.setLastModDate(mock(Timestamp.class));
    sysRespiratoryExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysRespiratoryExamination.setPalpation("Palpation");
    sysRespiratoryExamination.setParkingPlaceID(1);
    sysRespiratoryExamination.setPercussion("Percussion");
    sysRespiratoryExamination.setProcessed("Processed");
    sysRespiratoryExamination.setProviderServiceMapID(1);
    sysRespiratoryExamination.setReservedForChange("Reserved For Change");
    sysRespiratoryExamination.setSignsOfRespiratoryDistress("Signs Of Respiratory Distress");
    sysRespiratoryExamination.setSyncedBy("Synced By");
    sysRespiratoryExamination.setSyncedDate(mock(Timestamp.class));
    sysRespiratoryExamination.setTrachea("Trachea");
    sysRespiratoryExamination.setVanSerialNo(1L);
    sysRespiratoryExamination.setVehicalNo("Vehical No");
    sysRespiratoryExamination.setVisitCode(1L);
    when(sysRespiratoryExaminationRepo.save(Mockito.<SysRespiratoryExamination>any()))
        .thenReturn(sysRespiratoryExamination);

    SysRespiratoryExamination respiratoryExamination = new SysRespiratoryExamination();
    respiratoryExamination.setAuscultation("Auscultation");
    respiratoryExamination.setAuscultation_BreathSounds("Auscultation Breath Sounds");
    respiratoryExamination.setAuscultation_ConductedSounds("Auscultation Conducted Sounds");
    respiratoryExamination.setAuscultation_Crepitations("Auscultation Crepitations");
    respiratoryExamination.setAuscultation_PleuralRub("Auscultation Pleural Rub");
    respiratoryExamination.setAuscultation_Stridor("Auscultation Stridor");
    respiratoryExamination.setAuscultation_Wheezing("Auscultation Wheezing");
    respiratoryExamination.setBenVisitID(1L);
    respiratoryExamination.setBeneficiaryRegID(1L);
    respiratoryExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    respiratoryExamination.setCreatedDate(mock(Timestamp.class));
    respiratoryExamination.setDeleted(true);
    respiratoryExamination.setID(1L);
    respiratoryExamination.setInspection("Inspection");
    respiratoryExamination.setLastModDate(mock(Timestamp.class));
    respiratoryExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    respiratoryExamination.setPalpation("Palpation");
    respiratoryExamination.setParkingPlaceID(1);
    respiratoryExamination.setPercussion("Percussion");
    respiratoryExamination.setProcessed("Processed");
    respiratoryExamination.setProviderServiceMapID(1);
    respiratoryExamination.setReservedForChange("Reserved For Change");
    respiratoryExamination.setSignsOfRespiratoryDistress("Signs Of Respiratory Distress");
    respiratoryExamination.setSyncedBy("Synced By");
    respiratoryExamination.setSyncedDate(mock(Timestamp.class));
    respiratoryExamination.setTrachea("Trachea");
    respiratoryExamination.setVanSerialNo(1L);
    respiratoryExamination.setVehicalNo("Vehical No");
    respiratoryExamination.setVisitCode(1L);

    // Act and Assert
    assertThrows(IEMRException.class,
        () -> commonNurseServiceImpl.saveSysRespiratoryExamination(respiratoryExamination));
    verify(sysRespiratoryExamination).getID();
    verify(sysRespiratoryExamination).setAuscultation(eq("Auscultation"));
    verify(sysRespiratoryExamination).setAuscultation_BreathSounds(eq("Auscultation Breath Sounds"));
    verify(sysRespiratoryExamination).setAuscultation_ConductedSounds(eq("Auscultation Conducted Sounds"));
    verify(sysRespiratoryExamination).setAuscultation_Crepitations(eq("Auscultation Crepitations"));
    verify(sysRespiratoryExamination).setAuscultation_PleuralRub(eq("Auscultation Pleural Rub"));
    verify(sysRespiratoryExamination).setAuscultation_Stridor(eq("Auscultation Stridor"));
    verify(sysRespiratoryExamination).setAuscultation_Wheezing(eq("Auscultation Wheezing"));
    verify(sysRespiratoryExamination).setBenVisitID(isA(Long.class));
    verify(sysRespiratoryExamination).setBeneficiaryRegID(isA(Long.class));
    verify(sysRespiratoryExamination).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(sysRespiratoryExamination).setCreatedDate(isA(Timestamp.class));
    verify(sysRespiratoryExamination).setDeleted(isA(Boolean.class));
    verify(sysRespiratoryExamination).setID(isA(Long.class));
    verify(sysRespiratoryExamination).setInspection(eq("Inspection"));
    verify(sysRespiratoryExamination).setLastModDate(isA(Timestamp.class));
    verify(sysRespiratoryExamination).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(sysRespiratoryExamination).setPalpation(eq("Palpation"));
    verify(sysRespiratoryExamination).setParkingPlaceID(isA(Integer.class));
    verify(sysRespiratoryExamination).setPercussion(eq("Percussion"));
    verify(sysRespiratoryExamination).setProcessed(eq("Processed"));
    verify(sysRespiratoryExamination).setProviderServiceMapID(isA(Integer.class));
    verify(sysRespiratoryExamination).setReservedForChange(eq("Reserved For Change"));
    verify(sysRespiratoryExamination).setSignsOfRespiratoryDistress(eq("Signs Of Respiratory Distress"));
    verify(sysRespiratoryExamination).setSyncedBy(eq("Synced By"));
    verify(sysRespiratoryExamination).setSyncedDate(isA(Timestamp.class));
    verify(sysRespiratoryExamination).setTrachea(eq("Trachea"));
    verify(sysRespiratoryExamination).setVanSerialNo(isA(Long.class));
    verify(sysRespiratoryExamination).setVehicalNo(eq("Vehical No"));
    verify(sysRespiratoryExamination).setVisitCode(isA(Long.class));
    verify(sysRespiratoryExaminationRepo).save(isA(SysRespiratoryExamination.class));
  }

  @Test
  void testSaveSysCentralNervousExamination() throws IEMRException {
    // Arrange
    SysCentralNervousExamination sysCentralNervousExamination = new SysCentralNervousExamination();
    sysCentralNervousExamination.setAutonomicSystem("Autonomic System");
    sysCentralNervousExamination.setBenVisitID(1L);
    sysCentralNervousExamination.setBeneficiaryRegID(1L);
    sysCentralNervousExamination.setCerebellarSigns("Cerebellar Signs");
    sysCentralNervousExamination.setCranialNervesExamination("Cranial Nerves Examination");
    sysCentralNervousExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysCentralNervousExamination.setCreatedDate(mock(Timestamp.class));
    sysCentralNervousExamination.setDeleted(true);
    sysCentralNervousExamination.setHandedness("Handedness");
    sysCentralNervousExamination.setID(1L);
    sysCentralNervousExamination.setLastModDate(mock(Timestamp.class));
    sysCentralNervousExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysCentralNervousExamination.setMotorSystem("Motor System");
    sysCentralNervousExamination.setParkingPlaceID(1);
    sysCentralNervousExamination.setProcessed("Processed");
    sysCentralNervousExamination.setProviderServiceMapID(1);
    sysCentralNervousExamination.setReservedForChange("Reserved For Change");
    sysCentralNervousExamination.setSensorySystem("Sensory System");
    sysCentralNervousExamination.setSignsOfMeningealIrritation("Signs Of Meningeal Irritation");
    sysCentralNervousExamination.setSkull("Skull");
    sysCentralNervousExamination.setSyncedBy("Synced By");
    sysCentralNervousExamination.setSyncedDate(mock(Timestamp.class));
    sysCentralNervousExamination.setVanSerialNo(1L);
    sysCentralNervousExamination.setVehicalNo("Vehical No");
    sysCentralNervousExamination.setVisitCode(1L);
    when(sysCentralNervousExaminationRepo.save(Mockito.<SysCentralNervousExamination>any()))
        .thenReturn(sysCentralNervousExamination);

    SysCentralNervousExamination centralNervousExamination = new SysCentralNervousExamination();
    centralNervousExamination.setAutonomicSystem("Autonomic System");
    centralNervousExamination.setBenVisitID(1L);
    centralNervousExamination.setBeneficiaryRegID(1L);
    centralNervousExamination.setCerebellarSigns("Cerebellar Signs");
    centralNervousExamination.setCranialNervesExamination("Cranial Nerves Examination");
    centralNervousExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    centralNervousExamination.setCreatedDate(mock(Timestamp.class));
    centralNervousExamination.setDeleted(true);
    centralNervousExamination.setHandedness("Handedness");
    centralNervousExamination.setID(1L);
    centralNervousExamination.setLastModDate(mock(Timestamp.class));
    centralNervousExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    centralNervousExamination.setMotorSystem("Motor System");
    centralNervousExamination.setParkingPlaceID(1);
    centralNervousExamination.setProcessed("Processed");
    centralNervousExamination.setProviderServiceMapID(1);
    centralNervousExamination.setReservedForChange("Reserved For Change");
    centralNervousExamination.setSensorySystem("Sensory System");
    centralNervousExamination.setSignsOfMeningealIrritation("Signs Of Meningeal Irritation");
    centralNervousExamination.setSkull("Skull");
    centralNervousExamination.setSyncedBy("Synced By");
    centralNervousExamination.setSyncedDate(mock(Timestamp.class));
    centralNervousExamination.setVanSerialNo(1L);
    centralNervousExamination.setVehicalNo("Vehical No");
    centralNervousExamination.setVisitCode(1L);

    // Act
    Long actualSaveSysCentralNervousExaminationResult = commonNurseServiceImpl
        .saveSysCentralNervousExamination(centralNervousExamination);

    // Assert
    verify(sysCentralNervousExaminationRepo).save(isA(SysCentralNervousExamination.class));
    assertEquals(1L, actualSaveSysCentralNervousExaminationResult.longValue());
  }

  @Test
  void testSaveSysCentralNervousExamination2() throws IEMRException {
    // Arrange
    SysCentralNervousExamination sysCentralNervousExamination = new SysCentralNervousExamination();
    sysCentralNervousExamination.setAutonomicSystem("Autonomic System");
    sysCentralNervousExamination.setBenVisitID(1L);
    sysCentralNervousExamination.setBeneficiaryRegID(1L);
    sysCentralNervousExamination.setCerebellarSigns("Cerebellar Signs");
    sysCentralNervousExamination.setCranialNervesExamination("Cranial Nerves Examination");
    sysCentralNervousExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysCentralNervousExamination.setCreatedDate(mock(Timestamp.class));
    sysCentralNervousExamination.setDeleted(true);
    sysCentralNervousExamination.setHandedness("Handedness");
    sysCentralNervousExamination.setID(null);
    sysCentralNervousExamination.setLastModDate(mock(Timestamp.class));
    sysCentralNervousExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysCentralNervousExamination.setMotorSystem("Motor System");
    sysCentralNervousExamination.setParkingPlaceID(1);
    sysCentralNervousExamination.setProcessed("Processed");
    sysCentralNervousExamination.setProviderServiceMapID(1);
    sysCentralNervousExamination.setReservedForChange("Reserved For Change");
    sysCentralNervousExamination.setSensorySystem("Sensory System");
    sysCentralNervousExamination.setSignsOfMeningealIrritation("Signs Of Meningeal Irritation");
    sysCentralNervousExamination.setSkull("Skull");
    sysCentralNervousExamination.setSyncedBy("Synced By");
    sysCentralNervousExamination.setSyncedDate(mock(Timestamp.class));
    sysCentralNervousExamination.setVanSerialNo(1L);
    sysCentralNervousExamination.setVehicalNo("Vehical No");
    sysCentralNervousExamination.setVisitCode(1L);
    when(sysCentralNervousExaminationRepo.save(Mockito.<SysCentralNervousExamination>any()))
        .thenReturn(sysCentralNervousExamination);

    SysCentralNervousExamination centralNervousExamination = new SysCentralNervousExamination();
    centralNervousExamination.setAutonomicSystem("Autonomic System");
    centralNervousExamination.setBenVisitID(1L);
    centralNervousExamination.setBeneficiaryRegID(1L);
    centralNervousExamination.setCerebellarSigns("Cerebellar Signs");
    centralNervousExamination.setCranialNervesExamination("Cranial Nerves Examination");
    centralNervousExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    centralNervousExamination.setCreatedDate(mock(Timestamp.class));
    centralNervousExamination.setDeleted(true);
    centralNervousExamination.setHandedness("Handedness");
    centralNervousExamination.setID(1L);
    centralNervousExamination.setLastModDate(mock(Timestamp.class));
    centralNervousExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    centralNervousExamination.setMotorSystem("Motor System");
    centralNervousExamination.setParkingPlaceID(1);
    centralNervousExamination.setProcessed("Processed");
    centralNervousExamination.setProviderServiceMapID(1);
    centralNervousExamination.setReservedForChange("Reserved For Change");
    centralNervousExamination.setSensorySystem("Sensory System");
    centralNervousExamination.setSignsOfMeningealIrritation("Signs Of Meningeal Irritation");
    centralNervousExamination.setSkull("Skull");
    centralNervousExamination.setSyncedBy("Synced By");
    centralNervousExamination.setSyncedDate(mock(Timestamp.class));
    centralNervousExamination.setVanSerialNo(1L);
    centralNervousExamination.setVehicalNo("Vehical No");
    centralNervousExamination.setVisitCode(1L);

    // Act and Assert
    assertThrows(IEMRException.class,
        () -> commonNurseServiceImpl.saveSysCentralNervousExamination(centralNervousExamination));
    verify(sysCentralNervousExaminationRepo).save(isA(SysCentralNervousExamination.class));
  }

  @Test
  void testSaveSysMusculoskeletalSystemExamination() throws IEMRException {
    // Arrange
    SysMusculoskeletalSystemExamination sysMusculoskeletalSystemExamination = new SysMusculoskeletalSystemExamination();
    sysMusculoskeletalSystemExamination.setBenVisitID(1L);
    sysMusculoskeletalSystemExamination.setBeneficiaryRegID(1L);
    sysMusculoskeletalSystemExamination.setChestWall("Chest Wall");
    sysMusculoskeletalSystemExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysMusculoskeletalSystemExamination.setCreatedDate(mock(Timestamp.class));
    sysMusculoskeletalSystemExamination.setDeleted(true);
    sysMusculoskeletalSystemExamination.setID(1L);
    sysMusculoskeletalSystemExamination.setJoint_Abnormality("Joint Abnormality");
    sysMusculoskeletalSystemExamination.setJoint_Laterality("Joint Laterality");
    sysMusculoskeletalSystemExamination.setJoint_TypeOfJoint("Joint Type Of Joint");
    sysMusculoskeletalSystemExamination.setLastModDate(mock(Timestamp.class));
    sysMusculoskeletalSystemExamination.setLowerLimb_Abnormality("Lower Limb Abnormality");
    sysMusculoskeletalSystemExamination.setLowerLimb_Laterality("Lower Limb Laterality");
    sysMusculoskeletalSystemExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysMusculoskeletalSystemExamination.setParkingPlaceID(1);
    sysMusculoskeletalSystemExamination.setProcessed("Processed");
    sysMusculoskeletalSystemExamination.setProviderServiceMapID(1);
    sysMusculoskeletalSystemExamination.setReservedForChange("Reserved For Change");
    sysMusculoskeletalSystemExamination.setSpine("Spine");
    sysMusculoskeletalSystemExamination.setSyncedBy("Synced By");
    sysMusculoskeletalSystemExamination.setSyncedDate(mock(Timestamp.class));
    sysMusculoskeletalSystemExamination.setUpperLimb_Abnormality("Upper Limb Abnormality");
    sysMusculoskeletalSystemExamination.setUpperLimb_Laterality("Upper Limb Laterality");
    sysMusculoskeletalSystemExamination.setVanSerialNo(1L);
    sysMusculoskeletalSystemExamination.setVehicalNo("Vehical No");
    sysMusculoskeletalSystemExamination.setVisitCode(1L);
    when(sysMusculoskeletalSystemExaminationRepo.save(Mockito.<SysMusculoskeletalSystemExamination>any()))
        .thenReturn(sysMusculoskeletalSystemExamination);

    SysMusculoskeletalSystemExamination musculoskeletalSystemExamination = new SysMusculoskeletalSystemExamination();
    musculoskeletalSystemExamination.setBenVisitID(1L);
    musculoskeletalSystemExamination.setBeneficiaryRegID(1L);
    musculoskeletalSystemExamination.setChestWall("Chest Wall");
    musculoskeletalSystemExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    musculoskeletalSystemExamination.setCreatedDate(mock(Timestamp.class));
    musculoskeletalSystemExamination.setDeleted(true);
    musculoskeletalSystemExamination.setID(1L);
    musculoskeletalSystemExamination.setJoint_Abnormality("Joint Abnormality");
    musculoskeletalSystemExamination.setJoint_Laterality("Joint Laterality");
    musculoskeletalSystemExamination.setJoint_TypeOfJoint("Joint Type Of Joint");
    musculoskeletalSystemExamination.setLastModDate(mock(Timestamp.class));
    musculoskeletalSystemExamination.setLowerLimb_Abnormality("Lower Limb Abnormality");
    musculoskeletalSystemExamination.setLowerLimb_Laterality("Lower Limb Laterality");
    musculoskeletalSystemExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    musculoskeletalSystemExamination.setParkingPlaceID(1);
    musculoskeletalSystemExamination.setProcessed("Processed");
    musculoskeletalSystemExamination.setProviderServiceMapID(1);
    musculoskeletalSystemExamination.setReservedForChange("Reserved For Change");
    musculoskeletalSystemExamination.setSpine("Spine");
    musculoskeletalSystemExamination.setSyncedBy("Synced By");
    musculoskeletalSystemExamination.setSyncedDate(mock(Timestamp.class));
    musculoskeletalSystemExamination.setUpperLimb_Abnormality("Upper Limb Abnormality");
    musculoskeletalSystemExamination.setUpperLimb_Laterality("Upper Limb Laterality");
    musculoskeletalSystemExamination.setVanSerialNo(1L);
    musculoskeletalSystemExamination.setVehicalNo("Vehical No");
    musculoskeletalSystemExamination.setVisitCode(1L);

    // Act
    Long actualSaveSysMusculoskeletalSystemExaminationResult = commonNurseServiceImpl
        .saveSysMusculoskeletalSystemExamination(musculoskeletalSystemExamination);

    // Assert
    verify(sysMusculoskeletalSystemExaminationRepo).save(isA(SysMusculoskeletalSystemExamination.class));
    assertEquals(1L, actualSaveSysMusculoskeletalSystemExaminationResult.longValue());
  }

  @Test
  void testSaveSysMusculoskeletalSystemExamination2() throws IEMRException {
    // Arrange
    SysMusculoskeletalSystemExamination sysMusculoskeletalSystemExamination = new SysMusculoskeletalSystemExamination();
    sysMusculoskeletalSystemExamination.setBenVisitID(1L);
    sysMusculoskeletalSystemExamination.setBeneficiaryRegID(1L);
    sysMusculoskeletalSystemExamination.setChestWall("Chest Wall");
    sysMusculoskeletalSystemExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysMusculoskeletalSystemExamination.setCreatedDate(mock(Timestamp.class));
    sysMusculoskeletalSystemExamination.setDeleted(true);
    sysMusculoskeletalSystemExamination.setID(null);
    sysMusculoskeletalSystemExamination.setJoint_Abnormality("Joint Abnormality");
    sysMusculoskeletalSystemExamination.setJoint_Laterality("Joint Laterality");
    sysMusculoskeletalSystemExamination.setJoint_TypeOfJoint("Joint Type Of Joint");
    sysMusculoskeletalSystemExamination.setLastModDate(mock(Timestamp.class));
    sysMusculoskeletalSystemExamination.setLowerLimb_Abnormality("Lower Limb Abnormality");
    sysMusculoskeletalSystemExamination.setLowerLimb_Laterality("Lower Limb Laterality");
    sysMusculoskeletalSystemExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysMusculoskeletalSystemExamination.setParkingPlaceID(1);
    sysMusculoskeletalSystemExamination.setProcessed("Processed");
    sysMusculoskeletalSystemExamination.setProviderServiceMapID(1);
    sysMusculoskeletalSystemExamination.setReservedForChange("Reserved For Change");
    sysMusculoskeletalSystemExamination.setSpine("Spine");
    sysMusculoskeletalSystemExamination.setSyncedBy("Synced By");
    sysMusculoskeletalSystemExamination.setSyncedDate(mock(Timestamp.class));
    sysMusculoskeletalSystemExamination.setUpperLimb_Abnormality("Upper Limb Abnormality");
    sysMusculoskeletalSystemExamination.setUpperLimb_Laterality("Upper Limb Laterality");
    sysMusculoskeletalSystemExamination.setVanSerialNo(1L);
    sysMusculoskeletalSystemExamination.setVehicalNo("Vehical No");
    sysMusculoskeletalSystemExamination.setVisitCode(1L);
    when(sysMusculoskeletalSystemExaminationRepo.save(Mockito.<SysMusculoskeletalSystemExamination>any()))
        .thenReturn(sysMusculoskeletalSystemExamination);

    SysMusculoskeletalSystemExamination musculoskeletalSystemExamination = new SysMusculoskeletalSystemExamination();
    musculoskeletalSystemExamination.setBenVisitID(1L);
    musculoskeletalSystemExamination.setBeneficiaryRegID(1L);
    musculoskeletalSystemExamination.setChestWall("Chest Wall");
    musculoskeletalSystemExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    musculoskeletalSystemExamination.setCreatedDate(mock(Timestamp.class));
    musculoskeletalSystemExamination.setDeleted(true);
    musculoskeletalSystemExamination.setID(1L);
    musculoskeletalSystemExamination.setJoint_Abnormality("Joint Abnormality");
    musculoskeletalSystemExamination.setJoint_Laterality("Joint Laterality");
    musculoskeletalSystemExamination.setJoint_TypeOfJoint("Joint Type Of Joint");
    musculoskeletalSystemExamination.setLastModDate(mock(Timestamp.class));
    musculoskeletalSystemExamination.setLowerLimb_Abnormality("Lower Limb Abnormality");
    musculoskeletalSystemExamination.setLowerLimb_Laterality("Lower Limb Laterality");
    musculoskeletalSystemExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    musculoskeletalSystemExamination.setParkingPlaceID(1);
    musculoskeletalSystemExamination.setProcessed("Processed");
    musculoskeletalSystemExamination.setProviderServiceMapID(1);
    musculoskeletalSystemExamination.setReservedForChange("Reserved For Change");
    musculoskeletalSystemExamination.setSpine("Spine");
    musculoskeletalSystemExamination.setSyncedBy("Synced By");
    musculoskeletalSystemExamination.setSyncedDate(mock(Timestamp.class));
    musculoskeletalSystemExamination.setUpperLimb_Abnormality("Upper Limb Abnormality");
    musculoskeletalSystemExamination.setUpperLimb_Laterality("Upper Limb Laterality");
    musculoskeletalSystemExamination.setVanSerialNo(1L);
    musculoskeletalSystemExamination.setVehicalNo("Vehical No");
    musculoskeletalSystemExamination.setVisitCode(1L);

    // Act and Assert
    assertThrows(IEMRException.class,
        () -> commonNurseServiceImpl.saveSysMusculoskeletalSystemExamination(musculoskeletalSystemExamination));
    verify(sysMusculoskeletalSystemExaminationRepo).save(isA(SysMusculoskeletalSystemExamination.class));
  }

  @Test
  void testSaveSysGenitourinarySystemExamination() throws IEMRException {
    // Arrange
    SysGenitourinarySystemExamination sysGenitourinarySystemExamination = new SysGenitourinarySystemExamination();
    sysGenitourinarySystemExamination.setBenVisitID(1L);
    sysGenitourinarySystemExamination.setBeneficiaryRegID(1L);
    sysGenitourinarySystemExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysGenitourinarySystemExamination.setCreatedDate(mock(Timestamp.class));
    sysGenitourinarySystemExamination.setDeleted(true);
    sysGenitourinarySystemExamination.setExternalGenitalia("External Genitalia");
    sysGenitourinarySystemExamination.setID(1L);
    sysGenitourinarySystemExamination.setLastModDate(mock(Timestamp.class));
    sysGenitourinarySystemExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysGenitourinarySystemExamination.setParkingPlaceID(1);
    sysGenitourinarySystemExamination.setProcessed("Processed");
    sysGenitourinarySystemExamination.setProviderServiceMapID(1);
    sysGenitourinarySystemExamination.setRenalAngle("Renal Angle");
    sysGenitourinarySystemExamination.setReservedForChange("Reserved For Change");
    sysGenitourinarySystemExamination.setSuprapubicRegion("us-east-2");
    sysGenitourinarySystemExamination.setSyncedBy("Synced By");
    sysGenitourinarySystemExamination.setSyncedDate(mock(Timestamp.class));
    sysGenitourinarySystemExamination.setVanSerialNo(1L);
    sysGenitourinarySystemExamination.setVehicalNo("Vehical No");
    sysGenitourinarySystemExamination.setVisitCode(1L);
    when(sysGenitourinarySystemExaminationRepo.save(Mockito.<SysGenitourinarySystemExamination>any()))
        .thenReturn(sysGenitourinarySystemExamination);

    SysGenitourinarySystemExamination genitourinarySystemExamination = new SysGenitourinarySystemExamination();
    genitourinarySystemExamination.setBenVisitID(1L);
    genitourinarySystemExamination.setBeneficiaryRegID(1L);
    genitourinarySystemExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    genitourinarySystemExamination.setCreatedDate(mock(Timestamp.class));
    genitourinarySystemExamination.setDeleted(true);
    genitourinarySystemExamination.setExternalGenitalia("External Genitalia");
    genitourinarySystemExamination.setID(1L);
    genitourinarySystemExamination.setLastModDate(mock(Timestamp.class));
    genitourinarySystemExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    genitourinarySystemExamination.setParkingPlaceID(1);
    genitourinarySystemExamination.setProcessed("Processed");
    genitourinarySystemExamination.setProviderServiceMapID(1);
    genitourinarySystemExamination.setRenalAngle("Renal Angle");
    genitourinarySystemExamination.setReservedForChange("Reserved For Change");
    genitourinarySystemExamination.setSuprapubicRegion("us-east-2");
    genitourinarySystemExamination.setSyncedBy("Synced By");
    genitourinarySystemExamination.setSyncedDate(mock(Timestamp.class));
    genitourinarySystemExamination.setVanSerialNo(1L);
    genitourinarySystemExamination.setVehicalNo("Vehical No");
    genitourinarySystemExamination.setVisitCode(1L);

    // Act
    Long actualSaveSysGenitourinarySystemExaminationResult = commonNurseServiceImpl
        .saveSysGenitourinarySystemExamination(genitourinarySystemExamination);

    // Assert
    verify(sysGenitourinarySystemExaminationRepo).save(isA(SysGenitourinarySystemExamination.class));
    assertEquals(1L, actualSaveSysGenitourinarySystemExaminationResult.longValue());
  }

  @Test
  void testSaveSysGenitourinarySystemExamination2() throws IEMRException {
    // Arrange
    SysGenitourinarySystemExamination sysGenitourinarySystemExamination = mock(SysGenitourinarySystemExamination.class);
    when(sysGenitourinarySystemExamination.getID()).thenReturn(-1L);
    doNothing().when(sysGenitourinarySystemExamination).setBenVisitID(Mockito.<Long>any());
    doNothing().when(sysGenitourinarySystemExamination).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(sysGenitourinarySystemExamination).setCreatedBy(Mockito.<String>any());
    doNothing().when(sysGenitourinarySystemExamination).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(sysGenitourinarySystemExamination).setDeleted(Mockito.<Boolean>any());
    doNothing().when(sysGenitourinarySystemExamination).setExternalGenitalia(Mockito.<String>any());
    doNothing().when(sysGenitourinarySystemExamination).setID(Mockito.<Long>any());
    doNothing().when(sysGenitourinarySystemExamination).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(sysGenitourinarySystemExamination).setModifiedBy(Mockito.<String>any());
    doNothing().when(sysGenitourinarySystemExamination).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(sysGenitourinarySystemExamination).setProcessed(Mockito.<String>any());
    doNothing().when(sysGenitourinarySystemExamination).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(sysGenitourinarySystemExamination).setRenalAngle(Mockito.<String>any());
    doNothing().when(sysGenitourinarySystemExamination).setReservedForChange(Mockito.<String>any());
    doNothing().when(sysGenitourinarySystemExamination).setSuprapubicRegion(Mockito.<String>any());
    doNothing().when(sysGenitourinarySystemExamination).setSyncedBy(Mockito.<String>any());
    doNothing().when(sysGenitourinarySystemExamination).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(sysGenitourinarySystemExamination).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(sysGenitourinarySystemExamination).setVehicalNo(Mockito.<String>any());
    doNothing().when(sysGenitourinarySystemExamination).setVisitCode(Mockito.<Long>any());
    sysGenitourinarySystemExamination.setBenVisitID(1L);
    sysGenitourinarySystemExamination.setBeneficiaryRegID(1L);
    sysGenitourinarySystemExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysGenitourinarySystemExamination.setCreatedDate(mock(Timestamp.class));
    sysGenitourinarySystemExamination.setDeleted(true);
    sysGenitourinarySystemExamination.setExternalGenitalia("External Genitalia");
    sysGenitourinarySystemExamination.setID(1L);
    sysGenitourinarySystemExamination.setLastModDate(mock(Timestamp.class));
    sysGenitourinarySystemExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysGenitourinarySystemExamination.setParkingPlaceID(1);
    sysGenitourinarySystemExamination.setProcessed("Processed");
    sysGenitourinarySystemExamination.setProviderServiceMapID(1);
    sysGenitourinarySystemExamination.setRenalAngle("Renal Angle");
    sysGenitourinarySystemExamination.setReservedForChange("Reserved For Change");
    sysGenitourinarySystemExamination.setSuprapubicRegion("us-east-2");
    sysGenitourinarySystemExamination.setSyncedBy("Synced By");
    sysGenitourinarySystemExamination.setSyncedDate(mock(Timestamp.class));
    sysGenitourinarySystemExamination.setVanSerialNo(1L);
    sysGenitourinarySystemExamination.setVehicalNo("Vehical No");
    sysGenitourinarySystemExamination.setVisitCode(1L);
    when(sysGenitourinarySystemExaminationRepo.save(Mockito.<SysGenitourinarySystemExamination>any()))
        .thenReturn(sysGenitourinarySystemExamination);

    SysGenitourinarySystemExamination genitourinarySystemExamination = new SysGenitourinarySystemExamination();
    genitourinarySystemExamination.setBenVisitID(1L);
    genitourinarySystemExamination.setBeneficiaryRegID(1L);
    genitourinarySystemExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    genitourinarySystemExamination.setCreatedDate(mock(Timestamp.class));
    genitourinarySystemExamination.setDeleted(true);
    genitourinarySystemExamination.setExternalGenitalia("External Genitalia");
    genitourinarySystemExamination.setID(1L);
    genitourinarySystemExamination.setLastModDate(mock(Timestamp.class));
    genitourinarySystemExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    genitourinarySystemExamination.setParkingPlaceID(1);
    genitourinarySystemExamination.setProcessed("Processed");
    genitourinarySystemExamination.setProviderServiceMapID(1);
    genitourinarySystemExamination.setRenalAngle("Renal Angle");
    genitourinarySystemExamination.setReservedForChange("Reserved For Change");
    genitourinarySystemExamination.setSuprapubicRegion("us-east-2");
    genitourinarySystemExamination.setSyncedBy("Synced By");
    genitourinarySystemExamination.setSyncedDate(mock(Timestamp.class));
    genitourinarySystemExamination.setVanSerialNo(1L);
    genitourinarySystemExamination.setVehicalNo("Vehical No");
    genitourinarySystemExamination.setVisitCode(1L);

    // Act and Assert
    assertThrows(IEMRException.class,
        () -> commonNurseServiceImpl.saveSysGenitourinarySystemExamination(genitourinarySystemExamination));
    verify(sysGenitourinarySystemExamination, atLeast(1)).getID();
    verify(sysGenitourinarySystemExamination).setBenVisitID(isA(Long.class));
    verify(sysGenitourinarySystemExamination).setBeneficiaryRegID(isA(Long.class));
    verify(sysGenitourinarySystemExamination).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(sysGenitourinarySystemExamination).setCreatedDate(isA(Timestamp.class));
    verify(sysGenitourinarySystemExamination).setDeleted(isA(Boolean.class));
    verify(sysGenitourinarySystemExamination).setExternalGenitalia(eq("External Genitalia"));
    verify(sysGenitourinarySystemExamination).setID(isA(Long.class));
    verify(sysGenitourinarySystemExamination).setLastModDate(isA(Timestamp.class));
    verify(sysGenitourinarySystemExamination).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(sysGenitourinarySystemExamination).setParkingPlaceID(isA(Integer.class));
    verify(sysGenitourinarySystemExamination).setProcessed(eq("Processed"));
    verify(sysGenitourinarySystemExamination).setProviderServiceMapID(isA(Integer.class));
    verify(sysGenitourinarySystemExamination).setRenalAngle(eq("Renal Angle"));
    verify(sysGenitourinarySystemExamination).setReservedForChange(eq("Reserved For Change"));
    verify(sysGenitourinarySystemExamination).setSuprapubicRegion(eq("us-east-2"));
    verify(sysGenitourinarySystemExamination).setSyncedBy(eq("Synced By"));
    verify(sysGenitourinarySystemExamination).setSyncedDate(isA(Timestamp.class));
    verify(sysGenitourinarySystemExamination).setVanSerialNo(isA(Long.class));
    verify(sysGenitourinarySystemExamination).setVehicalNo(eq("Vehical No"));
    verify(sysGenitourinarySystemExamination).setVisitCode(isA(Long.class));
    verify(sysGenitourinarySystemExaminationRepo).save(isA(SysGenitourinarySystemExamination.class));
  }

  @Test
  void testFetchBenPastMedicalHistory() {
    // Arrange
    when(benMedHistoryRepo.getBenPastHistory(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualFetchBenPastMedicalHistoryResult = commonNurseServiceImpl.fetchBenPastMedicalHistory(1L);

    // Assert
    verify(benMedHistoryRepo).getBenPastHistory(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"\",\"columnName"
            + "\":\"\"},{\"keyName\":\"Illness_Type\",\"columnName\":\"Illness Type\"},{\"keyName\":\"Other_Illness_Type\",\"columnName"
            + "\":\"Other Illness Type\"},{\"keyName\":\"Year_Of_Illness\",\"columnName\":\"Year of Illness\"},{\"keyName\":"
            + "\"Surgery_Type\",\"columnName\":\"Surgery Type\"},{\"keyName\":\"Other_Surgery_Type\",\"columnName\":\"Other Surgery"
            + " Type\"},{\"keyName\":\"Year_Of_Surgery\",\"columnName\":\"Year of Surgery\"},{\"keyName\":\"\",\"columnName\":\"\"}"
            + "]}",
        actualFetchBenPastMedicalHistoryResult);
  }

  @Test
  void testFetchBenPersonalTobaccoHistory() {
    // Arrange
    when(benPersonalHabitRepo.getBenPersonalTobaccoHabitDetail(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualFetchBenPersonalTobaccoHistoryResult = commonNurseServiceImpl.fetchBenPersonalTobaccoHistory(1L);

    // Assert
    verify(benPersonalHabitRepo).getBenPersonalTobaccoHabitDetail(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"dietaryType"
            + "\",\"columnName\":\"Dietary Type\"},{\"keyName\":\"physicalActivityType\",\"columnName\":\"Physical Activity"
            + " Type\"},{\"keyName\":\"tobaccoUseStatus\",\"columnName\":\"Tobacco Use Status\"},{\"keyName\":\"tobaccoUseType\""
            + ",\"columnName\":\"Tobacco Use Type\"},{\"keyName\":\"otherTobaccoUseType\",\"columnName\":\"Other Tobacco Use"
            + " Type\"},{\"keyName\":\"numberperDay\",\"columnName\":\"Number Per Day\"},{\"keyName\":\"numberperWeek\",\"columnName"
            + "\":\"Number Per Week\"},{\"keyName\":\"tobacco_use_duration\",\"columnName\":\"Tobacco Use Start Date\"},{\"keyName"
            + "\":\"riskySexualPracticeStatus\",\"columnName\":\"Risky Sexual Practices Status\"}]}",
        actualFetchBenPersonalTobaccoHistoryResult);
  }

  @Test
  void testFetchBenPersonalAlcoholHistory() {
    // Arrange
    when(benPersonalHabitRepo.getBenPersonalAlcoholHabitDetail(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualFetchBenPersonalAlcoholHistoryResult = commonNurseServiceImpl.fetchBenPersonalAlcoholHistory(1L);

    // Assert
    verify(benPersonalHabitRepo).getBenPersonalAlcoholHabitDetail(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"dietaryType"
            + "\",\"columnName\":\"Dietary Type\"},{\"keyName\":\"physicalActivityType\",\"columnName\":\"Physical Activity"
            + " Type\"},{\"keyName\":\"alcoholIntakeStatus\",\"columnName\":\"Alcohol Intake Status\"},{\"keyName\":\"alcoholType"
            + "\",\"columnName\":\"Alcohol Type\"},{\"keyName\":\"otherAlcoholType\",\"columnName\":\"Other Alcohol Type\"},{"
            + "\"keyName\":\"alcoholIntakeFrequency\",\"columnName\":\"Alcohol Intake Frequency\"},{\"keyName\":\"avgAlcoholC"
            + "onsumption\",\"columnName\":\"Avg Alcohol Consumption\"},{\"keyName\":\"alcohol_use_duration\",\"columnName\":\"Alcohol"
            + " Use Started Date\"},{\"keyName\":\"riskySexualPracticeStatus\",\"columnName\":\"Risky Sexual Practices"
            + " Status\"}]}",
        actualFetchBenPersonalAlcoholHistoryResult);
  }

  @Test
  void testFetchBenPersonalAllergyHistory() {
    // Arrange
    when(benAllergyHistoryRepo.getBenPersonalAllergyDetail(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualFetchBenPersonalAllergyHistoryResult = commonNurseServiceImpl.fetchBenPersonalAllergyHistory(1L);

    // Assert
    verify(benAllergyHistoryRepo).getBenPersonalAllergyDetail(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"allergyStatus"
            + "\",\"columnName\":\"Allergy Status\"},{\"keyName\":\"allergyType\",\"columnName\":\"Allergy Type\"},{\"keyName\":"
            + "\"allergyName\",\"columnName\":\"Allergy Name\"},{\"keyName\":\"allergicReactionType\",\"columnName\":\"Allergic"
            + " Reaction Type\"},{\"keyName\":\"otherAllergicReaction\",\"columnName\":\"Other Allergic Reaction\"}]}",
        actualFetchBenPersonalAllergyHistoryResult);
  }

  @Test
  void testFetchBenPersonalMedicationHistory() {
    // Arrange
    when(benMedicationHistoryRepo.getBenMedicationHistoryDetail(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualFetchBenPersonalMedicationHistoryResult = commonNurseServiceImpl.fetchBenPersonalMedicationHistory(1L);

    // Assert
    verify(benMedicationHistoryRepo).getBenMedicationHistoryDetail(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"currentMedication"
            + "\",\"columnName\":\"Current Medication\"},{\"keyName\":\"medication_year\",\"columnName\":\"Date\"}]}",
        actualFetchBenPersonalMedicationHistoryResult);
  }

  @Test
  void testFetchBenPersonalFamilyHistory() {
    // Arrange
    when(benFamilyHistoryRepo.getBenFamilyHistoryDetail(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualFetchBenPersonalFamilyHistoryResult = commonNurseServiceImpl.fetchBenPersonalFamilyHistory(1L);

    // Assert
    verify(benFamilyHistoryRepo).getBenFamilyHistoryDetail(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"diseaseType"
            + "\",\"columnName\":\"Disease Type\"},{\"keyName\":\"otherDiseaseType\",\"columnName\":\"Other Disease Type\"},{"
            + "\"keyName\":\"familyMember\",\"columnName\":\"Family Member\"},{\"keyName\":\"IsGeneticDisorder\",\"columnName\":\"Is"
            + " Genetic Disorder\"},{\"keyName\":\"geneticDisorder\",\"columnName\":\"Genetic Disorder\"},{\"keyName\":"
            + "\"IsConsanguineousMarrige\",\"columnName\":\"Is Consanguineous Marrige\"}]}",
        actualFetchBenPersonalFamilyHistoryResult);
  }

  @Test
  void testFetchBenPhysicalHistory() {
    // Arrange
    when(physicalActivityTypeRepo.getBenPhysicalHistoryDetail(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualFetchBenPhysicalHistoryResult = commonNurseServiceImpl.fetchBenPhysicalHistory(1L);

    // Assert
    verify(physicalActivityTypeRepo).getBenPhysicalHistoryDetail(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"activityType"
            + "\",\"columnName\":\"Activity Type\"},{\"keyName\":\"physicalActivityType\",\"columnName\":\"Physical Activity"
            + " Type\"}]}",
        actualFetchBenPhysicalHistoryResult);
  }

  @Test
  void testFetchBenMenstrualHistory() {
    // Arrange
    when(benMenstrualDetailsRepo.getBenMenstrualDetail(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualFetchBenMenstrualHistoryResult = commonNurseServiceImpl.fetchBenMenstrualHistory(1L);

    // Assert
    verify(benMenstrualDetailsRepo).getBenMenstrualDetail(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"regularity"
            + "\",\"columnName\":\"Regularity\"},{\"keyName\":\"cycleLength\",\"columnName\":\"Cycle Length\"},{\"keyName\":"
            + "\"bloodFlowDuration\",\"columnName\":\"Blood Flow Duration\"},{\"keyName\":\"problemName\",\"columnName\":\"Problem"
            + " Name\"},{\"keyName\":\"lmp_date\",\"columnName\":\"LMPDate\"}]}",
        actualFetchBenMenstrualHistoryResult);
  }

  @Test
  void testFetchBenPastObstetricHistory() {
    // Arrange
    when(femaleObstetricHistoryRepo.getBenFemaleObstetricHistoryDetail(Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualFetchBenPastObstetricHistoryResult = commonNurseServiceImpl.fetchBenPastObstetricHistory(1L);

    // Assert
    verify(femaleObstetricHistoryRepo).getBenFemaleObstetricHistoryDetail(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"pregOrder"
            + "\",\"columnName\":\"Preg Order\"},{\"keyName\":\"pregComplicationType\",\"columnName\":\"Preg Complication"
            + " Type\"},{\"keyName\":\"otherPregComplication\",\"columnName\":\"Other Preg Complication\"},{\"keyName\":"
            + "\"durationType\",\"columnName\":\"Duration Type\"},{\"keyName\":\"deliveryType\",\"columnName\":\"Delivery"
            + " Type\"},{\"keyName\":\"deliveryPlace\",\"columnName\":\"Delivery Place\"},{\"keyName\":\"otherDeliveryPlace\","
            + "\"columnName\":\"Other Delivery Place\"},{\"keyName\":\"deliveryComplicationType\",\"columnName\":\"Delivery"
            + " Complication Type\"},{\"keyName\":\"otherDeliveryComplication\",\"columnName\":\"Other Delivery Complication"
            + "\"},{\"keyName\":\"pregOutcome\",\"columnName\":\"Preg Outcome\"},{\"keyName\":\"postpartumComplicationType\","
            + "\"columnName\":\"Postpartum Complication Type\"},{\"keyName\":\"otherPostpartumCompType\",\"columnName\":\"Other"
            + " Postpartum CompType\"},{\"keyName\":\"postNatalComplication\",\"columnName\":\"Post Natal Complication\"},{"
            + "\"keyName\":\"otherPostNatalComplication\",\"columnName\":\"Other Post Natal Complication\"},{\"keyName\":"
            + "\"congenitalAnomalies\",\"columnName\":\"Congenital Anomalies\"},{\"keyName\":\"newBornComplication\",\"columnName\":\"New"
            + " Born Complication\"},{\"keyName\":\"otherNewBornComplication\",\"columnName\":\"Other New Born Complication"
            + "\"}]}",
        actualFetchBenPastObstetricHistoryResult);
  }

  @Test
  void testFetchBenComorbidityHistory() {
    // Arrange
    when(bencomrbidityCondRepo.getBencomrbidityCondDetails(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualFetchBenComorbidityHistoryResult = commonNurseServiceImpl.fetchBenComorbidityHistory(1L);

    // Assert
    verify(bencomrbidityCondRepo).getBencomrbidityCondDetails(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"comorbidCondition"
            + "\",\"columnName\":\"Comorbid Condition\"},{\"keyName\":\"otherComorbidCondition\",\"columnName\":\"Other Comorbid"
            + " Condition\"},{\"keyName\":\"date\",\"columnName\":\"Date\"}]}",
        actualFetchBenComorbidityHistoryResult);
  }

  @Test
  void testFetchBenImmunizationHistory() {
    // Arrange
    when(childVaccineDetail1Repo.getBenChildVaccineDetails(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualFetchBenImmunizationHistoryResult = commonNurseServiceImpl.fetchBenImmunizationHistory(1L);

    // Assert
    verify(childVaccineDetail1Repo).getBenChildVaccineDetails(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"defaultRe"
            + "ceivingAge\",\"columnName\":\"Default Receiving Age\"},{\"keyName\":\"vaccineName\",\"columnName\":\"Vaccine"
            + " Name\"},{\"keyName\":\"status\",\"columnName\":\"Status\"}]}",
        actualFetchBenImmunizationHistoryResult);
  }

  @Test
  void testFetchBenOptionalVaccineHistory() {
    // Arrange
    when(childOptionalVaccineDetailRepo.getBenOptionalVaccineDetail(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualFetchBenOptionalVaccineHistoryResult = commonNurseServiceImpl.fetchBenOptionalVaccineHistory(1L);

    // Assert
    verify(childOptionalVaccineDetailRepo).getBenOptionalVaccineDetail(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"defaultRe"
            + "ceivingAge\",\"columnName\":\"Default Receiving Age\"},{\"keyName\":\"vaccineName\",\"columnName\":\"Vaccine"
            + " Name\"},{\"keyName\":\"otherVaccineName\",\"columnName\":\"Other Vaccine Name\"},{\"keyName\":\"actualReceivingAge"
            + "\",\"columnName\":\"Actual Receiving Age\"},{\"keyName\":\"ageUnit\",\"columnName\":\"Age Unit\"},{\"keyName\":"
            + "\"receivedFacilityName\",\"columnName\":\"Received Facility Name\"}]}",
        actualFetchBenOptionalVaccineHistoryResult);
  }

  @Test
  void testGetBenChiefComplaints() {
    // Arrange
    when(benChiefComplaintRepo.getBenChiefComplaints(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualBenChiefComplaints = commonNurseServiceImpl.getBenChiefComplaints(1L, 1L);

    // Assert
    verify(benChiefComplaintRepo).getBenChiefComplaints(isA(Long.class), isA(Long.class));
    assertEquals("[]", actualBenChiefComplaints);
  }

  @Test
  void testGetBenCdss() {
    // Arrange
    CDSS cdss = new CDSS();
    cdss.setAction("Action");
    cdss.setActionId(1);
    cdss.setActionIdPc(1);
    cdss.setActionPc("Action Pc");
    cdss.setAlgorithm("Algorithm");
    cdss.setAlgorithmPc("Algorithm Pc");
    cdss.setBenVisitID(1L);
    cdss.setBeneficiaryRegID(1L);
    cdss.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    cdss.setCreatedDate(mock(Timestamp.class));
    cdss.setDeleted(true);
    cdss.setDiseaseSummary("Disease Summary");
    cdss.setDiseaseSummaryID(1);
    cdss.setId(1L);
    cdss.setInformationGiven("Information Given");
    cdss.setLastModDate(mock(Timestamp.class));
    cdss.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    cdss.setParkingPlaceID(1);
    cdss.setPresentChiefComplaint("Present Chief Complaint");
    cdss.setPresentChiefComplaintID("Present Chief Complaint ID");
    cdss.setProviderServiceMapID(1);
    cdss.setRecommendedAction("Recommended Action");
    cdss.setRecommendedActionPc("Recommended Action Pc");
    cdss.setRemarks("Remarks");
    cdss.setRemarksPc("Remarks Pc");
    cdss.setReservedForChange("Reserved For Change");
    cdss.setSelectedDiagnosis("Selected Diagnosis");
    cdss.setSelectedDiagnosisID("Selected Diagnosis ID");
    cdss.setSyncedBy("Synced By");
    cdss.setSyncedDate(mock(Timestamp.class));
    cdss.setVanID(1);
    cdss.setVanSerialNo(1L);
    cdss.setVehicalNo("Vehical No");
    cdss.setVisitCode(1L);
    when(cDSSRepo.findByBeneficiaryRegIDAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(cdss);

    // Act
    String actualBenCdss = commonNurseServiceImpl.getBenCdss(1L, 1L);

    // Assert
    verify(cDSSRepo).findByBeneficiaryRegIDAndVisitCode(isA(Long.class), isA(Long.class));
    assertEquals(
        "{\"vanID\":1,\"benVisitID\":1,\"beneficiaryRegID\":1,\"diseaseSummary\":{\"recommendedAction\":\"Recommended"
            + " Action\",\"diseaseSummary\":\"Disease Summary\",\"informationGiven\":\"Information Given\",\"action\":\"Action\""
            + ",\"actionId\":1,\"diseaseSummaryID\":1,\"remarks\":\"Remarks\",\"algorithm\":\"Algorithm\"},\"createdBy\":\"Jan 1,"
            + " 2020 8:00am GMT+0100\",\"parkingPlaceID\":1,\"providerServiceMapID\":1,\"presentChiefComplaint\":{"
            + "\"selectedDiagnosisID\":\"Selected Diagnosis ID\",\"remarksPc\":\"Remarks Pc\",\"actionPc\":\"Action Pc\","
            + "\"presentChiefComplaintID\":\"Present Chief Complaint ID\",\"algorithmPc\":\"Algorithm Pc\",\"actionIdPc\":1,"
            + "\"recommendedActionPc\":\"Recommended Action Pc\",\"selectedDiagnosis\":\"Selected Diagnosis\",\"presentChiefComplaint"
            + "\":\"Present Chief Complaint\"}}",
        actualBenCdss);
  }

  @Test
  void testGetBenCdss2() {
    // Arrange
    CDSS cdss = mock(CDSS.class);
    when(cdss.getActionId()).thenReturn(1);
    when(cdss.getActionIdPc()).thenReturn(1);
    when(cdss.getDiseaseSummaryID()).thenReturn(1);
    when(cdss.getParkingPlaceID()).thenReturn(1);
    when(cdss.getProviderServiceMapID()).thenReturn(1);
    when(cdss.getVanID()).thenReturn(1);
    when(cdss.getBenVisitID()).thenReturn(1L);
    when(cdss.getBeneficiaryRegID()).thenReturn(1L);
    when(cdss.getAction()).thenReturn("CDSS(id=");
    when(cdss.getActionPc()).thenReturn("Action Pc");
    when(cdss.getAlgorithm()).thenReturn("Algorithm");
    when(cdss.getAlgorithmPc()).thenReturn("Algorithm Pc");
    when(cdss.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
    when(cdss.getDiseaseSummary()).thenReturn("Disease Summary");
    when(cdss.getInformationGiven()).thenReturn("Information Given");
    when(cdss.getPresentChiefComplaint()).thenReturn("Present Chief Complaint");
    when(cdss.getPresentChiefComplaintID()).thenReturn("Present Chief Complaint ID");
    when(cdss.getRecommendedAction()).thenReturn("Recommended Action");
    when(cdss.getRecommendedActionPc()).thenReturn("Recommended Action Pc");
    when(cdss.getRemarks()).thenReturn("Remarks");
    when(cdss.getRemarksPc()).thenReturn("Remarks Pc");
    when(cdss.getSelectedDiagnosis()).thenReturn("Selected Diagnosis");
    when(cdss.getSelectedDiagnosisID()).thenReturn("Selected Diagnosis ID");
    doNothing().when(cdss).setAction(Mockito.<String>any());
    doNothing().when(cdss).setActionId(Mockito.<Integer>any());
    doNothing().when(cdss).setActionIdPc(Mockito.<Integer>any());
    doNothing().when(cdss).setActionPc(Mockito.<String>any());
    doNothing().when(cdss).setAlgorithm(Mockito.<String>any());
    doNothing().when(cdss).setAlgorithmPc(Mockito.<String>any());
    doNothing().when(cdss).setBenVisitID(Mockito.<Long>any());
    doNothing().when(cdss).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(cdss).setCreatedBy(Mockito.<String>any());
    doNothing().when(cdss).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(cdss).setDeleted(Mockito.<Boolean>any());
    doNothing().when(cdss).setDiseaseSummary(Mockito.<String>any());
    doNothing().when(cdss).setDiseaseSummaryID(Mockito.<Integer>any());
    doNothing().when(cdss).setId(Mockito.<Long>any());
    doNothing().when(cdss).setInformationGiven(Mockito.<String>any());
    doNothing().when(cdss).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(cdss).setModifiedBy(Mockito.<String>any());
    doNothing().when(cdss).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(cdss).setPresentChiefComplaint(Mockito.<String>any());
    doNothing().when(cdss).setPresentChiefComplaintID(Mockito.<String>any());
    doNothing().when(cdss).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(cdss).setRecommendedAction(Mockito.<String>any());
    doNothing().when(cdss).setRecommendedActionPc(Mockito.<String>any());
    doNothing().when(cdss).setRemarks(Mockito.<String>any());
    doNothing().when(cdss).setRemarksPc(Mockito.<String>any());
    doNothing().when(cdss).setReservedForChange(Mockito.<String>any());
    doNothing().when(cdss).setSelectedDiagnosis(Mockito.<String>any());
    doNothing().when(cdss).setSelectedDiagnosisID(Mockito.<String>any());
    doNothing().when(cdss).setSyncedBy(Mockito.<String>any());
    doNothing().when(cdss).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(cdss).setVanID(Mockito.<Integer>any());
    doNothing().when(cdss).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(cdss).setVehicalNo(Mockito.<String>any());
    doNothing().when(cdss).setVisitCode(Mockito.<Long>any());
    cdss.setAction("Action");
    cdss.setActionId(1);
    cdss.setActionIdPc(1);
    cdss.setActionPc("Action Pc");
    cdss.setAlgorithm("Algorithm");
    cdss.setAlgorithmPc("Algorithm Pc");
    cdss.setBenVisitID(1L);
    cdss.setBeneficiaryRegID(1L);
    cdss.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    cdss.setCreatedDate(mock(Timestamp.class));
    cdss.setDeleted(true);
    cdss.setDiseaseSummary("Disease Summary");
    cdss.setDiseaseSummaryID(1);
    cdss.setId(1L);
    cdss.setInformationGiven("Information Given");
    cdss.setLastModDate(mock(Timestamp.class));
    cdss.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    cdss.setParkingPlaceID(1);
    cdss.setPresentChiefComplaint("Present Chief Complaint");
    cdss.setPresentChiefComplaintID("Present Chief Complaint ID");
    cdss.setProviderServiceMapID(1);
    cdss.setRecommendedAction("Recommended Action");
    cdss.setRecommendedActionPc("Recommended Action Pc");
    cdss.setRemarks("Remarks");
    cdss.setRemarksPc("Remarks Pc");
    cdss.setReservedForChange("Reserved For Change");
    cdss.setSelectedDiagnosis("Selected Diagnosis");
    cdss.setSelectedDiagnosisID("Selected Diagnosis ID");
    cdss.setSyncedBy("Synced By");
    cdss.setSyncedDate(mock(Timestamp.class));
    cdss.setVanID(1);
    cdss.setVanSerialNo(1L);
    cdss.setVehicalNo("Vehical No");
    cdss.setVisitCode(1L);
    when(cDSSRepo.findByBeneficiaryRegIDAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(cdss);

    // Act
    String actualBenCdss = commonNurseServiceImpl.getBenCdss(1L, 1L);

    // Assert
    verify(cdss).getAction();
    verify(cdss).getActionId();
    verify(cdss).getActionIdPc();
    verify(cdss).getActionPc();
    verify(cdss).getAlgorithm();
    verify(cdss).getAlgorithmPc();
    verify(cdss).getBenVisitID();
    verify(cdss).getBeneficiaryRegID();
    verify(cdss).getCreatedBy();
    verify(cdss).getDiseaseSummary();
    verify(cdss).getDiseaseSummaryID();
    verify(cdss).getInformationGiven();
    verify(cdss).getParkingPlaceID();
    verify(cdss).getPresentChiefComplaint();
    verify(cdss).getPresentChiefComplaintID();
    verify(cdss).getProviderServiceMapID();
    verify(cdss).getRecommendedAction();
    verify(cdss).getRecommendedActionPc();
    verify(cdss).getRemarks();
    verify(cdss).getRemarksPc();
    verify(cdss).getSelectedDiagnosis();
    verify(cdss).getSelectedDiagnosisID();
    verify(cdss).getVanID();
    verify(cdss).setAction(eq("Action"));
    verify(cdss).setActionId(isA(Integer.class));
    verify(cdss).setActionIdPc(isA(Integer.class));
    verify(cdss).setActionPc(eq("Action Pc"));
    verify(cdss).setAlgorithm(eq("Algorithm"));
    verify(cdss).setAlgorithmPc(eq("Algorithm Pc"));
    verify(cdss).setBenVisitID(isA(Long.class));
    verify(cdss).setBeneficiaryRegID(isA(Long.class));
    verify(cdss).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(cdss).setCreatedDate(isA(Timestamp.class));
    verify(cdss).setDeleted(isA(Boolean.class));
    verify(cdss).setDiseaseSummary(eq("Disease Summary"));
    verify(cdss).setDiseaseSummaryID(isA(Integer.class));
    verify(cdss).setId(isA(Long.class));
    verify(cdss).setInformationGiven(eq("Information Given"));
    verify(cdss).setLastModDate(isA(Timestamp.class));
    verify(cdss).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(cdss).setParkingPlaceID(isA(Integer.class));
    verify(cdss).setPresentChiefComplaint(eq("Present Chief Complaint"));
    verify(cdss).setPresentChiefComplaintID(eq("Present Chief Complaint ID"));
    verify(cdss).setProviderServiceMapID(isA(Integer.class));
    verify(cdss).setRecommendedAction(eq("Recommended Action"));
    verify(cdss).setRecommendedActionPc(eq("Recommended Action Pc"));
    verify(cdss).setRemarks(eq("Remarks"));
    verify(cdss).setRemarksPc(eq("Remarks Pc"));
    verify(cdss).setReservedForChange(eq("Reserved For Change"));
    verify(cdss).setSelectedDiagnosis(eq("Selected Diagnosis"));
    verify(cdss).setSelectedDiagnosisID(eq("Selected Diagnosis ID"));
    verify(cdss).setSyncedBy(eq("Synced By"));
    verify(cdss).setSyncedDate(isA(Timestamp.class));
    verify(cdss).setVanID(isA(Integer.class));
    verify(cdss).setVanSerialNo(isA(Long.class));
    verify(cdss).setVehicalNo(eq("Vehical No"));
    verify(cdss).setVisitCode(isA(Long.class));
    verify(cDSSRepo).findByBeneficiaryRegIDAndVisitCode(isA(Long.class), isA(Long.class));
    assertEquals(
        "{\"vanID\":1,\"benVisitID\":1,\"beneficiaryRegID\":1,\"diseaseSummary\":{\"recommendedAction\":\"Recommended"
            + " Action\",\"diseaseSummary\":\"Disease Summary\",\"informationGiven\":\"Information Given\",\"action\":\"CDSS(id"
            + "\\u003d\",\"actionId\":1,\"diseaseSummaryID\":1,\"remarks\":\"Remarks\",\"algorithm\":\"Algorithm\"},\"createdBy\":\"Jan"
            + " 1, 2020 8:00am GMT+0100\",\"parkingPlaceID\":1,\"providerServiceMapID\":1,\"presentChiefComplaint\":{"
            + "\"selectedDiagnosisID\":\"Selected Diagnosis ID\",\"remarksPc\":\"Remarks Pc\",\"actionPc\":\"Action Pc\","
            + "\"presentChiefComplaintID\":\"Present Chief Complaint ID\",\"algorithmPc\":\"Algorithm Pc\",\"actionIdPc\":1,"
            + "\"recommendedActionPc\":\"Recommended Action Pc\",\"selectedDiagnosis\":\"Selected Diagnosis\",\"presentChiefComplaint"
            + "\":\"Present Chief Complaint\"}}",
        actualBenCdss);
  }

  @Test
  void testGetFamilyHistoryDetail() {
    // Arrange
    when(benFamilyHistoryRepo.getBenFamilyHisDetail(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    // Act
    BenFamilyHistory actualFamilyHistoryDetail = commonNurseServiceImpl.getFamilyHistoryDetail(1L, 1L);

    // Assert
    verify(benFamilyHistoryRepo).getBenFamilyHisDetail(isA(Long.class), isA(Long.class));
    assertNull(actualFamilyHistoryDetail);
  }

  @Test
  void testGetPhysicalActivityType() {
    // Arrange
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
    when(physicalActivityTypeRepo.getBenPhysicalHistoryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(physicalActivityType);

    // Act
    PhysicalActivityType actualPhysicalActivityType = commonNurseServiceImpl.getPhysicalActivityType(1L, 1L);

    // Assert
    verify(physicalActivityTypeRepo).getBenPhysicalHistoryDetails(isA(Long.class), isA(Long.class));
    assertSame(physicalActivityType, actualPhysicalActivityType);
  }

  @Test
  void testGetBeneficiaryIdrsDetails() {
    // Arrange
    when(iDRSDataRepo.getBenIdrsDetail(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    IDRSData actualBeneficiaryIdrsDetails = commonNurseServiceImpl.getBeneficiaryIdrsDetails(1L, 1L);

    // Assert
    verify(iDRSDataRepo).getBenIdrsDetail(isA(Long.class), isA(Long.class));
    assertNull(actualBeneficiaryIdrsDetails);
  }

  @Test
  void testGetPastHistoryData() {
    // Arrange
    when(benMedHistoryRepo.getBenPastHistory(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    BenMedHistory actualPastHistoryData = commonNurseServiceImpl.getPastHistoryData(1L, 1L);

    // Assert
    verify(benMedHistoryRepo).getBenPastHistory(isA(Long.class), isA(Long.class));
    assertNull(actualPastHistoryData);
  }

  @Test
  void testGetComorbidityConditionsHistory() {
    // Arrange
    ArrayList<Object[]> objectArrayList = new ArrayList<>();
    when(bencomrbidityCondRepo.getBencomrbidityCondDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(objectArrayList);

    // Act
    WrapperComorbidCondDetails actualComorbidityConditionsHistory = commonNurseServiceImpl
        .getComorbidityConditionsHistory(1L, 1L);

    // Assert
    verify(bencomrbidityCondRepo).getBencomrbidityCondDetails(isA(Long.class), isA(Long.class));
    assertEquals(objectArrayList, actualComorbidityConditionsHistory.getComorbidityConcurrentConditionsList());
  }

  @Test
  void testGetMedicationHistory() {
    // Arrange
    ArrayList<Object[]> objectArrayList = new ArrayList<>();
    when(benMedicationHistoryRepo.getBenMedicationHistoryDetail(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(objectArrayList);

    // Act
    WrapperMedicationHistory actualMedicationHistory = commonNurseServiceImpl.getMedicationHistory(1L, 1L);

    // Assert
    verify(benMedicationHistoryRepo).getBenMedicationHistoryDetail(isA(Long.class), isA(Long.class));
    assertEquals(objectArrayList, actualMedicationHistory.getBenMedicationHistoryDetails());
  }

  @Test
  void testGetPersonalHistory() {
    // Arrange
    when(benAllergyHistoryRepo.getBenPersonalAllergyDetail(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());
    when(benPersonalHabitRepo.getBenPersonalHabitDetail(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    // Act
    commonNurseServiceImpl.getPersonalHistory(1L, 1L);

    // Assert
    verify(benAllergyHistoryRepo).getBenPersonalAllergyDetail(isA(Long.class), isA(Long.class));
    verify(benPersonalHabitRepo).getBenPersonalHabitDetail(isA(Long.class), isA(Long.class));
  }

  @Test
  void testGetFamilyHistory() {
    // Arrange
    when(benFamilyHistoryRepo.getBenFamilyHistoryDetail(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    // Act
    BenFamilyHistory actualFamilyHistory = commonNurseServiceImpl.getFamilyHistory(1L, 1L);

    // Assert
    verify(benFamilyHistoryRepo).getBenFamilyHistoryDetail(isA(Long.class), isA(Long.class));
    assertNull(actualFamilyHistory);
  }

  @Test
  void testGetMenstrualHistory() {
    // Arrange
    when(benMenstrualDetailsRepo.getBenMenstrualDetail(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    // Act
    BenMenstrualDetails actualMenstrualHistory = commonNurseServiceImpl.getMenstrualHistory(1L, 1L);

    // Assert
    verify(benMenstrualDetailsRepo).getBenMenstrualDetail(isA(Long.class), isA(Long.class));
    assertNull(actualMenstrualHistory);
  }

  @Test
  void testGetFemaleObstetricHistory() {
    // Arrange
    when(femaleObstetricHistoryRepo.getBenFemaleObstetricHistoryDetail(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    // Act
    WrapperFemaleObstetricHistory actualFemaleObstetricHistory = commonNurseServiceImpl.getFemaleObstetricHistory(1L,
        1L);

    // Assert
    verify(femaleObstetricHistoryRepo).getBenFemaleObstetricHistoryDetail(isA(Long.class), isA(Long.class));
    ArrayList<FemaleObstetricHistory> expectedFemaleObstetricHistoryDetails = actualFemaleObstetricHistory
        .getFemaleObstetricHistoryList();
    assertSame(expectedFemaleObstetricHistoryDetails, actualFemaleObstetricHistory.getFemaleObstetricHistoryDetails());
  }

  @Test
  void testGetChildOptionalVaccineHistory() {
    // Arrange
    ArrayList<Object[]> objectArrayList = new ArrayList<>();
    when(childOptionalVaccineDetailRepo.getBenOptionalVaccineDetail(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(objectArrayList);

    // Act
    WrapperChildOptionalVaccineDetail actualChildOptionalVaccineHistory = commonNurseServiceImpl
        .getChildOptionalVaccineHistory(1L, 1L);

    // Assert
    verify(childOptionalVaccineDetailRepo).getBenOptionalVaccineDetail(isA(Long.class), isA(Long.class));
    assertEquals(objectArrayList, actualChildOptionalVaccineHistory.getChildOptionalVaccineDetails());
  }

  @Test
  void testGetImmunizationHistory() {
    // Arrange
    ArrayList<Object[]> objectArrayList = new ArrayList<>();
    when(childVaccineDetail1Repo.getBenChildVaccineDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(objectArrayList);

    // Act
    WrapperImmunizationHistory actualImmunizationHistory = commonNurseServiceImpl.getImmunizationHistory(1L, 1L);

    // Assert
    verify(childVaccineDetail1Repo).getBenChildVaccineDetails(isA(Long.class), isA(Long.class));
    assertEquals(objectArrayList, actualImmunizationHistory.getBenChildVaccineDetails());
  }

  @Test
  void testGetImmunizationHistory2() {
    // Arrange
    ArrayList<Object[]> objectArrayList = new ArrayList<>();
    when(childVaccineDetail1Repo.getBenChildVaccineDetailsByRegID(Mockito.<Long>any())).thenReturn(objectArrayList);

    // Act
    WrapperImmunizationHistory actualImmunizationHistory = commonNurseServiceImpl.getImmunizationHistory(1L, null);

    // Assert
    verify(childVaccineDetail1Repo).getBenChildVaccineDetailsByRegID(isA(Long.class));
    assertEquals(objectArrayList, actualImmunizationHistory.getBenChildVaccineDetails());
  }

  @Test
  void testGetGeneralExaminationData() {
    // Arrange
    PhyGeneralExamination phyGeneralExamination = new PhyGeneralExamination();
    phyGeneralExamination.setBenVisitID(1L);
    phyGeneralExamination.setBeneficiaryRegID(1L);
    phyGeneralExamination.setBuiltAndAppearance("Built And Appearance");
    phyGeneralExamination.setClubbing("Clubbing");
    phyGeneralExamination.setCoherence("Coherence");
    phyGeneralExamination.setComfortness("Comfortness");
    phyGeneralExamination.setConsciousness("Consciousness");
    phyGeneralExamination.setCooperation("Cooperation");
    phyGeneralExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    phyGeneralExamination.setCreatedDate(mock(Timestamp.class));
    phyGeneralExamination.setCyanosis("Cyanosis");
    phyGeneralExamination.setDangerSigns("Danger Signs");
    phyGeneralExamination.setDeleted(true);
    phyGeneralExamination.setEdema("Edema");
    phyGeneralExamination.setEdemaType("Edema Type");
    phyGeneralExamination.setExtentOfEdema("Extent Of Edema");
    phyGeneralExamination.setFoetalMovements("Foetal Movements");
    phyGeneralExamination.setGait("Gait");
    phyGeneralExamination.setID(1L);
    phyGeneralExamination.setJaundice("Jaundice");
    phyGeneralExamination.setLastModDate(mock(Timestamp.class));
    phyGeneralExamination.setLymphadenopathy("Lymphadenopathy");
    phyGeneralExamination.setLymphnodesInvolved("Lymphnodes Involved");
    phyGeneralExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    phyGeneralExamination.setPallor("Pallor");
    phyGeneralExamination.setParkingPlaceID(1);
    phyGeneralExamination.setProcessed("Processed");
    phyGeneralExamination.setProviderServiceMapID(1);
    phyGeneralExamination.setQuickening("Quickening");
    phyGeneralExamination.setReservedForChange("Reserved For Change");
    phyGeneralExamination.setSyncedBy("Synced By");
    phyGeneralExamination.setSyncedDate(mock(Timestamp.class));
    phyGeneralExamination.setTypeOfDangerSign("Type Of Danger Sign");
    phyGeneralExamination.setTypeOfDangerSigns(new ArrayList<>());
    phyGeneralExamination.setTypeOfLymphadenopathy("Type Of Lymphadenopathy");
    phyGeneralExamination.setVanSerialNo(1L);
    phyGeneralExamination.setVehicalNo("Vehical No");
    phyGeneralExamination.setVisitCode(1L);
    when(phyGeneralExaminationRepo.getPhyGeneralExaminationData(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(phyGeneralExamination);

    // Act
    PhyGeneralExamination actualGeneralExaminationData = commonNurseServiceImpl.getGeneralExaminationData(1L, 1L);

    // Assert
    verify(phyGeneralExaminationRepo).getPhyGeneralExaminationData(isA(Long.class), isA(Long.class));
    List<String> typeOfDangerSigns = actualGeneralExaminationData.getTypeOfDangerSigns();
    assertEquals(1, typeOfDangerSigns.size());
    assertEquals("Type Of Danger Sign", typeOfDangerSigns.get(0));
    assertSame(phyGeneralExamination, actualGeneralExaminationData);
  }

  @Test
  void testGetGeneralExaminationData2() {
    // Arrange
    PhyGeneralExamination phyGeneralExamination = mock(PhyGeneralExamination.class);
    when(phyGeneralExamination.getTypeOfDangerSign()).thenReturn("");
    doNothing().when(phyGeneralExamination).setBenVisitID(Mockito.<Long>any());
    doNothing().when(phyGeneralExamination).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(phyGeneralExamination).setBuiltAndAppearance(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setClubbing(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setCoherence(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setComfortness(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setConsciousness(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setCooperation(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setCreatedBy(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(phyGeneralExamination).setCyanosis(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setDangerSigns(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setDeleted(Mockito.<Boolean>any());
    doNothing().when(phyGeneralExamination).setEdema(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setEdemaType(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setExtentOfEdema(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setFoetalMovements(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setGait(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setID(Mockito.<Long>any());
    doNothing().when(phyGeneralExamination).setJaundice(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(phyGeneralExamination).setLymphadenopathy(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setLymphnodesInvolved(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setModifiedBy(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setPallor(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(phyGeneralExamination).setProcessed(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(phyGeneralExamination).setQuickening(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setReservedForChange(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setSyncedBy(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(phyGeneralExamination).setTypeOfDangerSign(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setTypeOfDangerSigns(Mockito.<List<String>>any());
    doNothing().when(phyGeneralExamination).setTypeOfLymphadenopathy(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(phyGeneralExamination).setVehicalNo(Mockito.<String>any());
    doNothing().when(phyGeneralExamination).setVisitCode(Mockito.<Long>any());
    phyGeneralExamination.setBenVisitID(1L);
    phyGeneralExamination.setBeneficiaryRegID(1L);
    phyGeneralExamination.setBuiltAndAppearance("Built And Appearance");
    phyGeneralExamination.setClubbing("Clubbing");
    phyGeneralExamination.setCoherence("Coherence");
    phyGeneralExamination.setComfortness("Comfortness");
    phyGeneralExamination.setConsciousness("Consciousness");
    phyGeneralExamination.setCooperation("Cooperation");
    phyGeneralExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    phyGeneralExamination.setCreatedDate(mock(Timestamp.class));
    phyGeneralExamination.setCyanosis("Cyanosis");
    phyGeneralExamination.setDangerSigns("Danger Signs");
    phyGeneralExamination.setDeleted(true);
    phyGeneralExamination.setEdema("Edema");
    phyGeneralExamination.setEdemaType("Edema Type");
    phyGeneralExamination.setExtentOfEdema("Extent Of Edema");
    phyGeneralExamination.setFoetalMovements("Foetal Movements");
    phyGeneralExamination.setGait("Gait");
    phyGeneralExamination.setID(1L);
    phyGeneralExamination.setJaundice("Jaundice");
    phyGeneralExamination.setLastModDate(mock(Timestamp.class));
    phyGeneralExamination.setLymphadenopathy("Lymphadenopathy");
    phyGeneralExamination.setLymphnodesInvolved("Lymphnodes Involved");
    phyGeneralExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    phyGeneralExamination.setPallor("Pallor");
    phyGeneralExamination.setParkingPlaceID(1);
    phyGeneralExamination.setProcessed("Processed");
    phyGeneralExamination.setProviderServiceMapID(1);
    phyGeneralExamination.setQuickening("Quickening");
    phyGeneralExamination.setReservedForChange("Reserved For Change");
    phyGeneralExamination.setSyncedBy("Synced By");
    phyGeneralExamination.setSyncedDate(mock(Timestamp.class));
    phyGeneralExamination.setTypeOfDangerSign("Type Of Danger Sign");
    phyGeneralExamination.setTypeOfDangerSigns(new ArrayList<>());
    phyGeneralExamination.setTypeOfLymphadenopathy("Type Of Lymphadenopathy");
    phyGeneralExamination.setVanSerialNo(1L);
    phyGeneralExamination.setVehicalNo("Vehical No");
    phyGeneralExamination.setVisitCode(1L);
    when(phyGeneralExaminationRepo.getPhyGeneralExaminationData(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(phyGeneralExamination);

    // Act
    commonNurseServiceImpl.getGeneralExaminationData(1L, 1L);

    // Assert
    verify(phyGeneralExamination).getTypeOfDangerSign();
    verify(phyGeneralExamination).setBenVisitID(isA(Long.class));
    verify(phyGeneralExamination).setBeneficiaryRegID(isA(Long.class));
    verify(phyGeneralExamination).setBuiltAndAppearance(eq("Built And Appearance"));
    verify(phyGeneralExamination).setClubbing(eq("Clubbing"));
    verify(phyGeneralExamination).setCoherence(eq("Coherence"));
    verify(phyGeneralExamination).setComfortness(eq("Comfortness"));
    verify(phyGeneralExamination).setConsciousness(eq("Consciousness"));
    verify(phyGeneralExamination).setCooperation(eq("Cooperation"));
    verify(phyGeneralExamination).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(phyGeneralExamination).setCreatedDate(isA(Timestamp.class));
    verify(phyGeneralExamination).setCyanosis(eq("Cyanosis"));
    verify(phyGeneralExamination).setDangerSigns(eq("Danger Signs"));
    verify(phyGeneralExamination).setDeleted(isA(Boolean.class));
    verify(phyGeneralExamination).setEdema(eq("Edema"));
    verify(phyGeneralExamination).setEdemaType(eq("Edema Type"));
    verify(phyGeneralExamination).setExtentOfEdema(eq("Extent Of Edema"));
    verify(phyGeneralExamination).setFoetalMovements(eq("Foetal Movements"));
    verify(phyGeneralExamination).setGait(eq("Gait"));
    verify(phyGeneralExamination).setID(isA(Long.class));
    verify(phyGeneralExamination).setJaundice(eq("Jaundice"));
    verify(phyGeneralExamination).setLastModDate(isA(Timestamp.class));
    verify(phyGeneralExamination).setLymphadenopathy(eq("Lymphadenopathy"));
    verify(phyGeneralExamination).setLymphnodesInvolved(eq("Lymphnodes Involved"));
    verify(phyGeneralExamination).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(phyGeneralExamination).setPallor(eq("Pallor"));
    verify(phyGeneralExamination).setParkingPlaceID(isA(Integer.class));
    verify(phyGeneralExamination).setProcessed(eq("Processed"));
    verify(phyGeneralExamination).setProviderServiceMapID(isA(Integer.class));
    verify(phyGeneralExamination).setQuickening(eq("Quickening"));
    verify(phyGeneralExamination).setReservedForChange(eq("Reserved For Change"));
    verify(phyGeneralExamination).setSyncedBy(eq("Synced By"));
    verify(phyGeneralExamination).setSyncedDate(isA(Timestamp.class));
    verify(phyGeneralExamination).setTypeOfDangerSign(eq("Type Of Danger Sign"));
    verify(phyGeneralExamination, atLeast(1)).setTypeOfDangerSigns(isA(List.class));
    verify(phyGeneralExamination).setTypeOfLymphadenopathy(eq("Type Of Lymphadenopathy"));
    verify(phyGeneralExamination).setVanSerialNo(isA(Long.class));
    verify(phyGeneralExamination).setVehicalNo(eq("Vehical No"));
    verify(phyGeneralExamination).setVisitCode(isA(Long.class));
    verify(phyGeneralExaminationRepo).getPhyGeneralExaminationData(isA(Long.class), isA(Long.class));
  }

  @Test
  void testGetHeadToToeExaminationData() {
    // Arrange
    PhyHeadToToeExamination phyHeadToToeExamination = new PhyHeadToToeExamination();
    phyHeadToToeExamination.setBenVisitID(1L);
    phyHeadToToeExamination.setBeneficiaryRegID(1L);
    phyHeadToToeExamination.setBreastAndNipples("Breast And Nipples");
    phyHeadToToeExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    phyHeadToToeExamination.setCreatedDate(mock(Timestamp.class));
    phyHeadToToeExamination.setDeleted(true);
    phyHeadToToeExamination.setEars("Ears");
    phyHeadToToeExamination.setEyes("Eyes");
    phyHeadToToeExamination.setHair("Hair");
    phyHeadToToeExamination.setHead("Head");
    phyHeadToToeExamination.setID(1L);
    phyHeadToToeExamination.setLastModDate(mock(Timestamp.class));
    phyHeadToToeExamination.setLowerLimbs("Lower Limbs");
    phyHeadToToeExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    phyHeadToToeExamination.setNails("Nails");
    phyHeadToToeExamination.setNipples("Nipples");
    phyHeadToToeExamination.setNose("Nose");
    phyHeadToToeExamination.setOralCavity("Oral Cavity");
    phyHeadToToeExamination.setParkingPlaceID(1);
    phyHeadToToeExamination.setProcessed("Processed");
    phyHeadToToeExamination.setProviderServiceMapID(1);
    phyHeadToToeExamination.setReservedForChange("Reserved For Change");
    phyHeadToToeExamination.setSkin("Skin");
    phyHeadToToeExamination.setSyncedBy("Synced By");
    phyHeadToToeExamination.setSyncedDate(mock(Timestamp.class));
    phyHeadToToeExamination.setThroat("Throat");
    phyHeadToToeExamination.setTrunk("Trunk");
    phyHeadToToeExamination.setUpperLimbs("Upper Limbs");
    phyHeadToToeExamination.setVanID(1);
    phyHeadToToeExamination.setVanSerialNo(1L);
    phyHeadToToeExamination.setVehicalNo("Vehical No");
    phyHeadToToeExamination.setVisitCode(1L);
    when(phyHeadToToeExaminationRepo.getPhyHeadToToeExaminationData(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(phyHeadToToeExamination);

    // Act
    PhyHeadToToeExamination actualHeadToToeExaminationData = commonNurseServiceImpl.getHeadToToeExaminationData(1L, 1L);

    // Assert
    verify(phyHeadToToeExaminationRepo).getPhyHeadToToeExaminationData(isA(Long.class), isA(Long.class));
    assertSame(phyHeadToToeExamination, actualHeadToToeExaminationData);
  }

  @Test
  void testGetSysGastrointestinalExamination() {
    // Arrange
    SysGastrointestinalExamination sysGastrointestinalExamination = new SysGastrointestinalExamination();
    sysGastrointestinalExamination.setAnalRegion("us-east-2");
    sysGastrointestinalExamination.setAuscultation("Auscultation");
    sysGastrointestinalExamination.setBenVisitID(1L);
    sysGastrointestinalExamination.setBeneficiaryRegID(1L);
    sysGastrointestinalExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysGastrointestinalExamination.setCreatedDate(mock(Timestamp.class));
    sysGastrointestinalExamination.setDeleted(true);
    sysGastrointestinalExamination.setID(1L);
    sysGastrointestinalExamination.setInspection("Inspection");
    sysGastrointestinalExamination.setLastModDate(mock(Timestamp.class));
    sysGastrointestinalExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysGastrointestinalExamination.setPalpation("Palpation");
    sysGastrointestinalExamination.setPalpation_AbdomenTexture("Palpation Abdomen Texture");
    sysGastrointestinalExamination.setPalpation_Liver("Palpation Liver");
    sysGastrointestinalExamination.setPalpation_LocationOfTenderness("Palpation Location Of Tenderness");
    sysGastrointestinalExamination.setPalpation_Spleen("Palpation Spleen");
    sysGastrointestinalExamination.setPalpation_Tenderness("Palpation Tenderness");
    sysGastrointestinalExamination.setParkingPlaceID(1);
    sysGastrointestinalExamination.setPercussion("Percussion");
    sysGastrointestinalExamination.setProcessed("Processed");
    sysGastrointestinalExamination.setProviderServiceMapID(1);
    sysGastrointestinalExamination.setReservedForChange("Reserved For Change");
    sysGastrointestinalExamination.setSyncedBy("Synced By");
    sysGastrointestinalExamination.setSyncedDate(mock(Timestamp.class));
    sysGastrointestinalExamination.setVanSerialNo(1L);
    sysGastrointestinalExamination.setVehicalNo("Vehical No");
    sysGastrointestinalExamination.setVisitCode(1L);
    when(
        sysGastrointestinalExaminationRepo.getSSysGastrointestinalExamination(Mockito.<Long>any(), Mockito.<Long>any()))
            .thenReturn(sysGastrointestinalExamination);

    // Act
    SysGastrointestinalExamination actualSysGastrointestinalExamination = commonNurseServiceImpl
        .getSysGastrointestinalExamination(1L, 1L);

    // Assert
    verify(sysGastrointestinalExaminationRepo).getSSysGastrointestinalExamination(isA(Long.class), isA(Long.class));
    assertSame(sysGastrointestinalExamination, actualSysGastrointestinalExamination);
  }

  @Test
  void testGetCardiovascularExamination() {
    // Arrange
    SysCardiovascularExamination sysCardiovascularExamination = new SysCardiovascularExamination();
    sysCardiovascularExamination.setAdditionalHeartSounds("Additional Heart Sounds");
    sysCardiovascularExamination.setApexbeatLocation("Apexbeat Location");
    sysCardiovascularExamination.setApexbeatType("Apexbeat Type");
    sysCardiovascularExamination.setBenVisitID(1L);
    sysCardiovascularExamination.setBeneficiaryRegID(1L);
    sysCardiovascularExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysCardiovascularExamination.setCreatedDate(mock(Timestamp.class));
    sysCardiovascularExamination.setDeleted(true);
    sysCardiovascularExamination.setFirstHeartSound_S1("First Heart Sound S1");
    sysCardiovascularExamination.setID(1L);
    sysCardiovascularExamination.setJugularVenousPulse_JVP("Jugular Venous Pulse JVP");
    sysCardiovascularExamination.setLastModDate(mock(Timestamp.class));
    sysCardiovascularExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysCardiovascularExamination.setMurmurs("Murmurs");
    sysCardiovascularExamination.setParkingPlaceID(1);
    sysCardiovascularExamination.setPericardialRub("Pericardial Rub");
    sysCardiovascularExamination.setProcessed("Processed");
    sysCardiovascularExamination.setProviderServiceMapID(1);
    sysCardiovascularExamination.setReservedForChange("Reserved For Change");
    sysCardiovascularExamination.setSecondHeartSound_S2("Second Heart Sound S2");
    sysCardiovascularExamination.setSyncedBy("Synced By");
    sysCardiovascularExamination.setSyncedDate(mock(Timestamp.class));
    sysCardiovascularExamination.setVanSerialNo(1L);
    sysCardiovascularExamination.setVehicalNo("Vehical No");
    sysCardiovascularExamination.setVisitCode(1L);
    when(sysCardiovascularExaminationRepo.getSysCardiovascularExaminationData(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(sysCardiovascularExamination);

    // Act
    SysCardiovascularExamination actualCardiovascularExamination = commonNurseServiceImpl
        .getCardiovascularExamination(1L, 1L);

    // Assert
    verify(sysCardiovascularExaminationRepo).getSysCardiovascularExaminationData(isA(Long.class), isA(Long.class));
    assertSame(sysCardiovascularExamination, actualCardiovascularExamination);
  }

  @Test
  void testGetRespiratoryExamination() {
    // Arrange
    SysRespiratoryExamination sysRespiratoryExamination = new SysRespiratoryExamination();
    sysRespiratoryExamination.setAuscultation("Auscultation");
    sysRespiratoryExamination.setAuscultation_BreathSounds("Auscultation Breath Sounds");
    sysRespiratoryExamination.setAuscultation_ConductedSounds("Auscultation Conducted Sounds");
    sysRespiratoryExamination.setAuscultation_Crepitations("Auscultation Crepitations");
    sysRespiratoryExamination.setAuscultation_PleuralRub("Auscultation Pleural Rub");
    sysRespiratoryExamination.setAuscultation_Stridor("Auscultation Stridor");
    sysRespiratoryExamination.setAuscultation_Wheezing("Auscultation Wheezing");
    sysRespiratoryExamination.setBenVisitID(1L);
    sysRespiratoryExamination.setBeneficiaryRegID(1L);
    sysRespiratoryExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysRespiratoryExamination.setCreatedDate(mock(Timestamp.class));
    sysRespiratoryExamination.setDeleted(true);
    sysRespiratoryExamination.setID(1L);
    sysRespiratoryExamination.setInspection("Inspection");
    sysRespiratoryExamination.setLastModDate(mock(Timestamp.class));
    sysRespiratoryExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysRespiratoryExamination.setPalpation("Palpation");
    sysRespiratoryExamination.setParkingPlaceID(1);
    sysRespiratoryExamination.setPercussion("Percussion");
    sysRespiratoryExamination.setProcessed("Processed");
    sysRespiratoryExamination.setProviderServiceMapID(1);
    sysRespiratoryExamination.setReservedForChange("Reserved For Change");
    sysRespiratoryExamination.setSignsOfRespiratoryDistress("Signs Of Respiratory Distress");
    sysRespiratoryExamination.setSyncedBy("Synced By");
    sysRespiratoryExamination.setSyncedDate(mock(Timestamp.class));
    sysRespiratoryExamination.setTrachea("Trachea");
    sysRespiratoryExamination.setVanSerialNo(1L);
    sysRespiratoryExamination.setVehicalNo("Vehical No");
    sysRespiratoryExamination.setVisitCode(1L);
    when(sysRespiratoryExaminationRepo.getSysRespiratoryExaminationData(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(sysRespiratoryExamination);

    // Act
    SysRespiratoryExamination actualRespiratoryExamination = commonNurseServiceImpl.getRespiratoryExamination(1L, 1L);

    // Assert
    verify(sysRespiratoryExaminationRepo).getSysRespiratoryExaminationData(isA(Long.class), isA(Long.class));
    assertSame(sysRespiratoryExamination, actualRespiratoryExamination);
  }

  @Test
  void testGetSysCentralNervousExamination() {
    // Arrange
    SysCentralNervousExamination sysCentralNervousExamination = new SysCentralNervousExamination();
    sysCentralNervousExamination.setAutonomicSystem("Autonomic System");
    sysCentralNervousExamination.setBenVisitID(1L);
    sysCentralNervousExamination.setBeneficiaryRegID(1L);
    sysCentralNervousExamination.setCerebellarSigns("Cerebellar Signs");
    sysCentralNervousExamination.setCranialNervesExamination("Cranial Nerves Examination");
    sysCentralNervousExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysCentralNervousExamination.setCreatedDate(mock(Timestamp.class));
    sysCentralNervousExamination.setDeleted(true);
    sysCentralNervousExamination.setHandedness("Handedness");
    sysCentralNervousExamination.setID(1L);
    sysCentralNervousExamination.setLastModDate(mock(Timestamp.class));
    sysCentralNervousExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysCentralNervousExamination.setMotorSystem("Motor System");
    sysCentralNervousExamination.setParkingPlaceID(1);
    sysCentralNervousExamination.setProcessed("Processed");
    sysCentralNervousExamination.setProviderServiceMapID(1);
    sysCentralNervousExamination.setReservedForChange("Reserved For Change");
    sysCentralNervousExamination.setSensorySystem("Sensory System");
    sysCentralNervousExamination.setSignsOfMeningealIrritation("Signs Of Meningeal Irritation");
    sysCentralNervousExamination.setSkull("Skull");
    sysCentralNervousExamination.setSyncedBy("Synced By");
    sysCentralNervousExamination.setSyncedDate(mock(Timestamp.class));
    sysCentralNervousExamination.setVanSerialNo(1L);
    sysCentralNervousExamination.setVehicalNo("Vehical No");
    sysCentralNervousExamination.setVisitCode(1L);
    when(sysCentralNervousExaminationRepo.getSysCentralNervousExaminationData(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(sysCentralNervousExamination);

    // Act
    SysCentralNervousExamination actualSysCentralNervousExamination = commonNurseServiceImpl
        .getSysCentralNervousExamination(1L, 1L);

    // Assert
    verify(sysCentralNervousExaminationRepo).getSysCentralNervousExaminationData(isA(Long.class), isA(Long.class));
    assertSame(sysCentralNervousExamination, actualSysCentralNervousExamination);
  }

  @Test
  void testGetMusculoskeletalExamination() {
    // Arrange
    SysMusculoskeletalSystemExamination sysMusculoskeletalSystemExamination = new SysMusculoskeletalSystemExamination();
    sysMusculoskeletalSystemExamination.setBenVisitID(1L);
    sysMusculoskeletalSystemExamination.setBeneficiaryRegID(1L);
    sysMusculoskeletalSystemExamination.setChestWall("Chest Wall");
    sysMusculoskeletalSystemExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysMusculoskeletalSystemExamination.setCreatedDate(mock(Timestamp.class));
    sysMusculoskeletalSystemExamination.setDeleted(true);
    sysMusculoskeletalSystemExamination.setID(1L);
    sysMusculoskeletalSystemExamination.setJoint_Abnormality("Joint Abnormality");
    sysMusculoskeletalSystemExamination.setJoint_Laterality("Joint Laterality");
    sysMusculoskeletalSystemExamination.setJoint_TypeOfJoint("Joint Type Of Joint");
    sysMusculoskeletalSystemExamination.setLastModDate(mock(Timestamp.class));
    sysMusculoskeletalSystemExamination.setLowerLimb_Abnormality("Lower Limb Abnormality");
    sysMusculoskeletalSystemExamination.setLowerLimb_Laterality("Lower Limb Laterality");
    sysMusculoskeletalSystemExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysMusculoskeletalSystemExamination.setParkingPlaceID(1);
    sysMusculoskeletalSystemExamination.setProcessed("Processed");
    sysMusculoskeletalSystemExamination.setProviderServiceMapID(1);
    sysMusculoskeletalSystemExamination.setReservedForChange("Reserved For Change");
    sysMusculoskeletalSystemExamination.setSpine("Spine");
    sysMusculoskeletalSystemExamination.setSyncedBy("Synced By");
    sysMusculoskeletalSystemExamination.setSyncedDate(mock(Timestamp.class));
    sysMusculoskeletalSystemExamination.setUpperLimb_Abnormality("Upper Limb Abnormality");
    sysMusculoskeletalSystemExamination.setUpperLimb_Laterality("Upper Limb Laterality");
    sysMusculoskeletalSystemExamination.setVanSerialNo(1L);
    sysMusculoskeletalSystemExamination.setVehicalNo("Vehical No");
    sysMusculoskeletalSystemExamination.setVisitCode(1L);
    when(sysMusculoskeletalSystemExaminationRepo.getSysMusculoskeletalSystemExamination(Mockito.<Long>any(),
        Mockito.<Long>any())).thenReturn(sysMusculoskeletalSystemExamination);

    // Act
    SysMusculoskeletalSystemExamination actualMusculoskeletalExamination = commonNurseServiceImpl
        .getMusculoskeletalExamination(1L, 1L);

    // Assert
    verify(sysMusculoskeletalSystemExaminationRepo).getSysMusculoskeletalSystemExamination(isA(Long.class),
        isA(Long.class));
    assertSame(sysMusculoskeletalSystemExamination, actualMusculoskeletalExamination);
  }

  @Test
  void testGetGenitourinaryExamination() {
    // Arrange
    SysGenitourinarySystemExamination sysGenitourinarySystemExamination = new SysGenitourinarySystemExamination();
    sysGenitourinarySystemExamination.setBenVisitID(1L);
    sysGenitourinarySystemExamination.setBeneficiaryRegID(1L);
    sysGenitourinarySystemExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    sysGenitourinarySystemExamination.setCreatedDate(mock(Timestamp.class));
    sysGenitourinarySystemExamination.setDeleted(true);
    sysGenitourinarySystemExamination.setExternalGenitalia("External Genitalia");
    sysGenitourinarySystemExamination.setID(1L);
    sysGenitourinarySystemExamination.setLastModDate(mock(Timestamp.class));
    sysGenitourinarySystemExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    sysGenitourinarySystemExamination.setParkingPlaceID(1);
    sysGenitourinarySystemExamination.setProcessed("Processed");
    sysGenitourinarySystemExamination.setProviderServiceMapID(1);
    sysGenitourinarySystemExamination.setRenalAngle("Renal Angle");
    sysGenitourinarySystemExamination.setReservedForChange("Reserved For Change");
    sysGenitourinarySystemExamination.setSuprapubicRegion("us-east-2");
    sysGenitourinarySystemExamination.setSyncedBy("Synced By");
    sysGenitourinarySystemExamination.setSyncedDate(mock(Timestamp.class));
    sysGenitourinarySystemExamination.setVanSerialNo(1L);
    sysGenitourinarySystemExamination.setVehicalNo("Vehical No");
    sysGenitourinarySystemExamination.setVisitCode(1L);
    when(sysGenitourinarySystemExaminationRepo.getSysGenitourinarySystemExaminationData(Mockito.<Long>any(),
        Mockito.<Long>any())).thenReturn(sysGenitourinarySystemExamination);

    // Act
    SysGenitourinarySystemExamination actualGenitourinaryExamination = commonNurseServiceImpl
        .getGenitourinaryExamination(1L, 1L);

    // Assert
    verify(sysGenitourinarySystemExaminationRepo).getSysGenitourinarySystemExaminationData(isA(Long.class),
        isA(Long.class));
    assertSame(sysGenitourinarySystemExamination, actualGenitourinaryExamination);
  }

  @Test
  void testGetOralExamination() throws IEMRException {
    // Arrange
    OralExamination oralExamination = new OralExamination();
    oralExamination.setBenVisitID(1L);
    oralExamination.setBeneficiaryRegID(1L);
    oralExamination.setChronicBurningSensation(true);
    oralExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    oralExamination.setCreatedDate(mock(Timestamp.class));
    oralExamination.setDeleted(true);
    oralExamination.setID(1L);
    oralExamination.setLastModDate(mock(Timestamp.class));
    oralExamination.setLimitedMouthOpening("Limited Mouth Opening");
    oralExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    oralExamination.setObservation("Observation");
    oralExamination.setParkingPlaceID(1);
    oralExamination.setPreMalignantLesionType("Pre Malignant Lesion Type");
    oralExamination.setPreMalignantLesionTypeList(new String[]{"Pre Malignant Lesion Type List"});
    oralExamination.setPremalignantLesions(true);
    oralExamination.setProcessed("Processed");
    oralExamination.setProlongedIrritation(true);
    oralExamination.setProviderServiceMapID(1);
    oralExamination.setReservedForChange("Reserved For Change");
    oralExamination.setSyncedBy("Synced By");
    oralExamination.setSyncedDate(mock(Timestamp.class));
    oralExamination.setVanID(1);
    oralExamination.setVanSerialNo(1L);
    oralExamination.setVehicalNo("Vehical No");
    oralExamination.setVisitCode(1L);
    when(oralExaminationRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(Mockito.<Long>any(), Mockito.<Long>any(),
        Mockito.<Boolean>any())).thenReturn(oralExamination);

    // Act
    OralExamination actualOralExamination = commonNurseServiceImpl.getOralExamination(1L, 1L);

    // Assert
    verify(oralExaminationRepo).findByBeneficiaryRegIDAndVisitCodeAndDeleted(isA(Long.class), isA(Long.class),
        isA(Boolean.class));
    assertSame(oralExamination, actualOralExamination);
    assertArrayEquals(new String[]{"Pre Malignant Lesion Type"}, actualOralExamination.getPreMalignantLesionTypeList());
  }

  @Test
  void testSavePrescriptionDetailsAndGetPrescriptionID() {
    // Arrange
    PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);
    when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);

    // Act
    Long actualSavePrescriptionDetailsAndGetPrescriptionIDResult = commonNurseServiceImpl
        .savePrescriptionDetailsAndGetPrescriptionID(1L, 1L, 1, "Jan 1, 2020 8:00am GMT+0100", "External Investigation",
            1L, 1, 1, "Instruction", "Prescription counselling Provided", new ArrayList<>());

    // Assert
    verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
    assertEquals(1L, actualSavePrescriptionDetailsAndGetPrescriptionIDResult.longValue());
  }

  @Test
  void testSavePrescriptionDetailsAndGetPrescriptionID2() {
    // Arrange
    PrescriptionDetail prescriptionDetail = mock(PrescriptionDetail.class);
    when(prescriptionDetail.getPrescriptionID()).thenReturn(-1L);
    doNothing().when(prescriptionDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBreastCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setCervicalCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setConfirmatoryDiagnosis(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvidedList(Mockito.<String[]>any());
    doNothing().when(prescriptionDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiabetesScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTCode(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTTerm(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setExternalInvestigation(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setHypertensionScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setInstruction(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setOralCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setPrescribedDrugs(Mockito.<ArrayList<PrescribedDrugDetail>>any());
    doNothing().when(prescriptionDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
    doNothing().when(prescriptionDetail).setRemarks(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setTreatmentsOnSideEffects(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVisitCode(Mockito.<Long>any());
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);
    when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);

    // Act
    Long actualSavePrescriptionDetailsAndGetPrescriptionIDResult = commonNurseServiceImpl
        .savePrescriptionDetailsAndGetPrescriptionID(1L, 1L, 1, "Jan 1, 2020 8:00am GMT+0100", "External Investigation",
            1L, 1, 1, "Instruction", "Prescription counselling Provided", new ArrayList<>());

    // Assert
    verify(prescriptionDetail).getPrescriptionID();
    verify(prescriptionDetail).setBenVisitID(isA(Long.class));
    verify(prescriptionDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescriptionDetail).setBreastCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setCervicalCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
    verify(prescriptionDetail).setCounsellingProvided(eq("Counselling Provided"));
    verify(prescriptionDetail).setCounsellingProvidedList(isA(String[].class));
    verify(prescriptionDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescriptionDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setDeleted(isA(Boolean.class));
    verify(prescriptionDetail).setDiabetesScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setDiagnosisProvided(eq("Diagnosis Provided"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTCode(eq("Diagnosis Provided SCTCode"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTTerm(eq("Diagnosis Provided SCTTerm"));
    verify(prescriptionDetail).setExternalInvestigation(eq("External Investigation"));
    verify(prescriptionDetail).setHypertensionScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setInstruction(eq("Instruction"));
    verify(prescriptionDetail).setLastModDate(isA(Timestamp.class));
    verify(prescriptionDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescriptionDetail).setOralCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescriptionDetail).setPrescribedDrugs(isA(ArrayList.class));
    verify(prescriptionDetail).setPrescriptionID(isA(Long.class));
    verify(prescriptionDetail).setProcessed(eq("Processed"));
    verify(prescriptionDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescriptionDetail).setProvisionalDiagnosisList(isA(ArrayList.class));
    verify(prescriptionDetail).setRemarks(eq("Remarks"));
    verify(prescriptionDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescriptionDetail).setSyncedBy(eq("Synced By"));
    verify(prescriptionDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setTreatmentsOnSideEffects(eq("Treatments On Side Effects"));
    verify(prescriptionDetail).setVanID(isA(Integer.class));
    verify(prescriptionDetail).setVanSerialNo(isA(Long.class));
    verify(prescriptionDetail).setVehicalNo(eq("Vehical No"));
    verify(prescriptionDetail).setVisitCode(isA(Long.class));
    verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
    assertNull(actualSavePrescriptionDetailsAndGetPrescriptionIDResult);
  }

  @Test
  void testSavePrescriptionCovid() {
    // Arrange
    PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);
    when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);

    // Act
    Long actualSavePrescriptionCovidResult = commonNurseServiceImpl.savePrescriptionCovid(1L, 1L, 1,
        "Jan 1, 2020 8:00am GMT+0100", "External Investigation", 1L, 1, 1, "Instruction", "Doctor Diagnosis",
        "Counselling Provided");

    // Assert
    verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
    assertEquals(1L, actualSavePrescriptionCovidResult.longValue());
  }

  @Test
  void testSavePrescriptionCovid2() {
    // Arrange
    PrescriptionDetail prescriptionDetail = mock(PrescriptionDetail.class);
    when(prescriptionDetail.getPrescriptionID()).thenReturn(-1L);
    doNothing().when(prescriptionDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBreastCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setCervicalCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setConfirmatoryDiagnosis(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvidedList(Mockito.<String[]>any());
    doNothing().when(prescriptionDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiabetesScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTCode(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTTerm(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setExternalInvestigation(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setHypertensionScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setInstruction(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setOralCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setPrescribedDrugs(Mockito.<ArrayList<PrescribedDrugDetail>>any());
    doNothing().when(prescriptionDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
    doNothing().when(prescriptionDetail).setRemarks(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setTreatmentsOnSideEffects(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVisitCode(Mockito.<Long>any());
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);
    when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);

    // Act
    Long actualSavePrescriptionCovidResult = commonNurseServiceImpl.savePrescriptionCovid(1L, 1L, 1,
        "Jan 1, 2020 8:00am GMT+0100", "External Investigation", 1L, 1, 1, "Instruction", "Doctor Diagnosis",
        "Counselling Provided");

    // Assert
    verify(prescriptionDetail).getPrescriptionID();
    verify(prescriptionDetail).setBenVisitID(isA(Long.class));
    verify(prescriptionDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescriptionDetail).setBreastCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setCervicalCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
    verify(prescriptionDetail).setCounsellingProvided(eq("Counselling Provided"));
    verify(prescriptionDetail).setCounsellingProvidedList(isA(String[].class));
    verify(prescriptionDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescriptionDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setDeleted(isA(Boolean.class));
    verify(prescriptionDetail).setDiabetesScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setDiagnosisProvided(eq("Diagnosis Provided"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTCode(eq("Diagnosis Provided SCTCode"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTTerm(eq("Diagnosis Provided SCTTerm"));
    verify(prescriptionDetail).setExternalInvestigation(eq("External Investigation"));
    verify(prescriptionDetail).setHypertensionScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setInstruction(eq("Instruction"));
    verify(prescriptionDetail).setLastModDate(isA(Timestamp.class));
    verify(prescriptionDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescriptionDetail).setOralCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescriptionDetail).setPrescribedDrugs(isA(ArrayList.class));
    verify(prescriptionDetail).setPrescriptionID(isA(Long.class));
    verify(prescriptionDetail).setProcessed(eq("Processed"));
    verify(prescriptionDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescriptionDetail).setProvisionalDiagnosisList(isA(ArrayList.class));
    verify(prescriptionDetail).setRemarks(eq("Remarks"));
    verify(prescriptionDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescriptionDetail).setSyncedBy(eq("Synced By"));
    verify(prescriptionDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setTreatmentsOnSideEffects(eq("Treatments On Side Effects"));
    verify(prescriptionDetail).setVanID(isA(Integer.class));
    verify(prescriptionDetail).setVanSerialNo(isA(Long.class));
    verify(prescriptionDetail).setVehicalNo(eq("Vehical No"));
    verify(prescriptionDetail).setVisitCode(isA(Long.class));
    verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
    assertNull(actualSavePrescriptionCovidResult);
  }

  @Test
  void testSaveBeneficiaryPrescription() throws Exception {
    // Arrange
    PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);
    when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);

    // Act
    Long actualSaveBeneficiaryPrescriptionResult = commonNurseServiceImpl.saveBeneficiaryPrescription(new JsonObject());

    // Assert
    verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
    assertEquals(1L, actualSaveBeneficiaryPrescriptionResult.longValue());
  }

  @Test
  void testSaveBeneficiaryPrescription2() throws Exception {
    // Arrange
    PrescriptionDetail prescriptionDetail = mock(PrescriptionDetail.class);
    when(prescriptionDetail.getPrescriptionID()).thenReturn(-1L);
    doNothing().when(prescriptionDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBreastCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setCervicalCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setConfirmatoryDiagnosis(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvidedList(Mockito.<String[]>any());
    doNothing().when(prescriptionDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiabetesScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTCode(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTTerm(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setExternalInvestigation(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setHypertensionScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setInstruction(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setOralCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setPrescribedDrugs(Mockito.<ArrayList<PrescribedDrugDetail>>any());
    doNothing().when(prescriptionDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
    doNothing().when(prescriptionDetail).setRemarks(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setTreatmentsOnSideEffects(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVisitCode(Mockito.<Long>any());
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);
    when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);

    // Act
    Long actualSaveBeneficiaryPrescriptionResult = commonNurseServiceImpl.saveBeneficiaryPrescription(new JsonObject());

    // Assert
    verify(prescriptionDetail).getPrescriptionID();
    verify(prescriptionDetail).setBenVisitID(isA(Long.class));
    verify(prescriptionDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescriptionDetail).setBreastCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setCervicalCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
    verify(prescriptionDetail).setCounsellingProvided(eq("Counselling Provided"));
    verify(prescriptionDetail).setCounsellingProvidedList(isA(String[].class));
    verify(prescriptionDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescriptionDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setDeleted(isA(Boolean.class));
    verify(prescriptionDetail).setDiabetesScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setDiagnosisProvided(eq("Diagnosis Provided"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTCode(eq("Diagnosis Provided SCTCode"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTTerm(eq("Diagnosis Provided SCTTerm"));
    verify(prescriptionDetail).setExternalInvestigation(eq("External Investigation"));
    verify(prescriptionDetail).setHypertensionScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setInstruction(eq("Instruction"));
    verify(prescriptionDetail).setLastModDate(isA(Timestamp.class));
    verify(prescriptionDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescriptionDetail).setOralCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescriptionDetail).setPrescribedDrugs(isA(ArrayList.class));
    verify(prescriptionDetail).setPrescriptionID(isA(Long.class));
    verify(prescriptionDetail).setProcessed(eq("Processed"));
    verify(prescriptionDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescriptionDetail).setProvisionalDiagnosisList(isA(ArrayList.class));
    verify(prescriptionDetail).setRemarks(eq("Remarks"));
    verify(prescriptionDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescriptionDetail).setSyncedBy(eq("Synced By"));
    verify(prescriptionDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setTreatmentsOnSideEffects(eq("Treatments On Side Effects"));
    verify(prescriptionDetail).setVanID(isA(Integer.class));
    verify(prescriptionDetail).setVanSerialNo(isA(Long.class));
    verify(prescriptionDetail).setVehicalNo(eq("Vehical No"));
    verify(prescriptionDetail).setVisitCode(isA(Long.class));
    verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
    assertNull(actualSaveBeneficiaryPrescriptionResult);
  }

  @Test
  void testSaveBeneficiaryPrescription3() throws Exception {
    // Arrange
    PrescriptionDetail prescriptionDetail = mock(PrescriptionDetail.class);
    when(prescriptionDetail.getPrescriptionID()).thenReturn(1L);
    doNothing().when(prescriptionDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBreastCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setCervicalCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setConfirmatoryDiagnosis(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvidedList(Mockito.<String[]>any());
    doNothing().when(prescriptionDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiabetesScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTCode(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTTerm(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setExternalInvestigation(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setHypertensionScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setInstruction(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setOralCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setPrescribedDrugs(Mockito.<ArrayList<PrescribedDrugDetail>>any());
    doNothing().when(prescriptionDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
    doNothing().when(prescriptionDetail).setRemarks(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setTreatmentsOnSideEffects(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVisitCode(Mockito.<Long>any());
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);
    when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);

    JsonObject caseSheet = new JsonObject();
    caseSheet.add("Property", new JsonArray(3));

    // Act
    Long actualSaveBeneficiaryPrescriptionResult = commonNurseServiceImpl.saveBeneficiaryPrescription(caseSheet);

    // Assert
    verify(prescriptionDetail, atLeast(1)).getPrescriptionID();
    verify(prescriptionDetail).setBenVisitID(isA(Long.class));
    verify(prescriptionDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescriptionDetail).setBreastCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setCervicalCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
    verify(prescriptionDetail).setCounsellingProvided(eq("Counselling Provided"));
    verify(prescriptionDetail).setCounsellingProvidedList(isA(String[].class));
    verify(prescriptionDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescriptionDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setDeleted(isA(Boolean.class));
    verify(prescriptionDetail).setDiabetesScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setDiagnosisProvided(eq("Diagnosis Provided"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTCode(eq("Diagnosis Provided SCTCode"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTTerm(eq("Diagnosis Provided SCTTerm"));
    verify(prescriptionDetail).setExternalInvestigation(eq("External Investigation"));
    verify(prescriptionDetail).setHypertensionScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setInstruction(eq("Instruction"));
    verify(prescriptionDetail).setLastModDate(isA(Timestamp.class));
    verify(prescriptionDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescriptionDetail).setOralCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescriptionDetail).setPrescribedDrugs(isA(ArrayList.class));
    verify(prescriptionDetail).setPrescriptionID(isA(Long.class));
    verify(prescriptionDetail).setProcessed(eq("Processed"));
    verify(prescriptionDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescriptionDetail).setProvisionalDiagnosisList(isA(ArrayList.class));
    verify(prescriptionDetail).setRemarks(eq("Remarks"));
    verify(prescriptionDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescriptionDetail).setSyncedBy(eq("Synced By"));
    verify(prescriptionDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setTreatmentsOnSideEffects(eq("Treatments On Side Effects"));
    verify(prescriptionDetail).setVanID(isA(Integer.class));
    verify(prescriptionDetail).setVanSerialNo(isA(Long.class));
    verify(prescriptionDetail).setVehicalNo(eq("Vehical No"));
    verify(prescriptionDetail).setVisitCode(isA(Long.class));
    verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
    assertEquals(1L, actualSaveBeneficiaryPrescriptionResult.longValue());
  }

  @Test
  void testSaveBeneficiaryPrescription4() throws Exception {
    // Arrange
    PrescriptionDetail prescriptionDetail = mock(PrescriptionDetail.class);
    when(prescriptionDetail.getPrescriptionID()).thenReturn(1L);
    doNothing().when(prescriptionDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBreastCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setCervicalCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setConfirmatoryDiagnosis(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvidedList(Mockito.<String[]>any());
    doNothing().when(prescriptionDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiabetesScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTCode(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTTerm(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setExternalInvestigation(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setHypertensionScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setInstruction(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setOralCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setPrescribedDrugs(Mockito.<ArrayList<PrescribedDrugDetail>>any());
    doNothing().when(prescriptionDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
    doNothing().when(prescriptionDetail).setRemarks(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setTreatmentsOnSideEffects(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVisitCode(Mockito.<Long>any());
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);
    when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);

    JsonObject caseSheet = new JsonObject();
    caseSheet.addProperty("Property", "42");

    // Act
    Long actualSaveBeneficiaryPrescriptionResult = commonNurseServiceImpl.saveBeneficiaryPrescription(caseSheet);

    // Assert
    verify(prescriptionDetail, atLeast(1)).getPrescriptionID();
    verify(prescriptionDetail).setBenVisitID(isA(Long.class));
    verify(prescriptionDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescriptionDetail).setBreastCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setCervicalCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
    verify(prescriptionDetail).setCounsellingProvided(eq("Counselling Provided"));
    verify(prescriptionDetail).setCounsellingProvidedList(isA(String[].class));
    verify(prescriptionDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescriptionDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setDeleted(isA(Boolean.class));
    verify(prescriptionDetail).setDiabetesScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setDiagnosisProvided(eq("Diagnosis Provided"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTCode(eq("Diagnosis Provided SCTCode"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTTerm(eq("Diagnosis Provided SCTTerm"));
    verify(prescriptionDetail).setExternalInvestigation(eq("External Investigation"));
    verify(prescriptionDetail).setHypertensionScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setInstruction(eq("Instruction"));
    verify(prescriptionDetail).setLastModDate(isA(Timestamp.class));
    verify(prescriptionDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescriptionDetail).setOralCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescriptionDetail).setPrescribedDrugs(isA(ArrayList.class));
    verify(prescriptionDetail).setPrescriptionID(isA(Long.class));
    verify(prescriptionDetail).setProcessed(eq("Processed"));
    verify(prescriptionDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescriptionDetail).setProvisionalDiagnosisList(isA(ArrayList.class));
    verify(prescriptionDetail).setRemarks(eq("Remarks"));
    verify(prescriptionDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescriptionDetail).setSyncedBy(eq("Synced By"));
    verify(prescriptionDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setTreatmentsOnSideEffects(eq("Treatments On Side Effects"));
    verify(prescriptionDetail).setVanID(isA(Integer.class));
    verify(prescriptionDetail).setVanSerialNo(isA(Long.class));
    verify(prescriptionDetail).setVehicalNo(eq("Vehical No"));
    verify(prescriptionDetail).setVisitCode(isA(Long.class));
    verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
    assertEquals(1L, actualSaveBeneficiaryPrescriptionResult.longValue());
  }

  @Test
  void testSaveBeneficiaryPrescription5() throws Exception {
    // Arrange
    PrescriptionDetail prescriptionDetail = mock(PrescriptionDetail.class);
    when(prescriptionDetail.getPrescriptionID()).thenReturn(1L);
    doNothing().when(prescriptionDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBreastCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setCervicalCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setConfirmatoryDiagnosis(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvidedList(Mockito.<String[]>any());
    doNothing().when(prescriptionDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiabetesScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTCode(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTTerm(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setExternalInvestigation(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setHypertensionScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setInstruction(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setOralCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setPrescribedDrugs(Mockito.<ArrayList<PrescribedDrugDetail>>any());
    doNothing().when(prescriptionDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
    doNothing().when(prescriptionDetail).setRemarks(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setTreatmentsOnSideEffects(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVisitCode(Mockito.<Long>any());
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);
    when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);

    JsonObject caseSheet = new JsonObject();
    caseSheet.addProperty("Property", Integer.valueOf(1));

    // Act
    Long actualSaveBeneficiaryPrescriptionResult = commonNurseServiceImpl.saveBeneficiaryPrescription(caseSheet);

    // Assert
    verify(prescriptionDetail, atLeast(1)).getPrescriptionID();
    verify(prescriptionDetail).setBenVisitID(isA(Long.class));
    verify(prescriptionDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescriptionDetail).setBreastCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setCervicalCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
    verify(prescriptionDetail).setCounsellingProvided(eq("Counselling Provided"));
    verify(prescriptionDetail).setCounsellingProvidedList(isA(String[].class));
    verify(prescriptionDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescriptionDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setDeleted(isA(Boolean.class));
    verify(prescriptionDetail).setDiabetesScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setDiagnosisProvided(eq("Diagnosis Provided"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTCode(eq("Diagnosis Provided SCTCode"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTTerm(eq("Diagnosis Provided SCTTerm"));
    verify(prescriptionDetail).setExternalInvestigation(eq("External Investigation"));
    verify(prescriptionDetail).setHypertensionScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setInstruction(eq("Instruction"));
    verify(prescriptionDetail).setLastModDate(isA(Timestamp.class));
    verify(prescriptionDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescriptionDetail).setOralCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescriptionDetail).setPrescribedDrugs(isA(ArrayList.class));
    verify(prescriptionDetail).setPrescriptionID(isA(Long.class));
    verify(prescriptionDetail).setProcessed(eq("Processed"));
    verify(prescriptionDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescriptionDetail).setProvisionalDiagnosisList(isA(ArrayList.class));
    verify(prescriptionDetail).setRemarks(eq("Remarks"));
    verify(prescriptionDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescriptionDetail).setSyncedBy(eq("Synced By"));
    verify(prescriptionDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setTreatmentsOnSideEffects(eq("Treatments On Side Effects"));
    verify(prescriptionDetail).setVanID(isA(Integer.class));
    verify(prescriptionDetail).setVanSerialNo(isA(Long.class));
    verify(prescriptionDetail).setVehicalNo(eq("Vehical No"));
    verify(prescriptionDetail).setVisitCode(isA(Long.class));
    verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
    assertEquals(1L, actualSaveBeneficiaryPrescriptionResult.longValue());
  }

  @Test
  void testSaveBeneficiaryPrescription6() throws Exception {
    // Arrange
    PrescriptionDetail prescriptionDetail = mock(PrescriptionDetail.class);
    when(prescriptionDetail.getPrescriptionID()).thenReturn(1L);
    doNothing().when(prescriptionDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBreastCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setCervicalCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setConfirmatoryDiagnosis(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvidedList(Mockito.<String[]>any());
    doNothing().when(prescriptionDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiabetesScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTCode(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTTerm(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setExternalInvestigation(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setHypertensionScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setInstruction(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setOralCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setPrescribedDrugs(Mockito.<ArrayList<PrescribedDrugDetail>>any());
    doNothing().when(prescriptionDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
    doNothing().when(prescriptionDetail).setRemarks(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setTreatmentsOnSideEffects(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVisitCode(Mockito.<Long>any());
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);
    when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);

    JsonObject caseSheet = new JsonObject();
    caseSheet.addProperty("Property", true);

    // Act
    Long actualSaveBeneficiaryPrescriptionResult = commonNurseServiceImpl.saveBeneficiaryPrescription(caseSheet);

    // Assert
    verify(prescriptionDetail, atLeast(1)).getPrescriptionID();
    verify(prescriptionDetail).setBenVisitID(isA(Long.class));
    verify(prescriptionDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescriptionDetail).setBreastCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setCervicalCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
    verify(prescriptionDetail).setCounsellingProvided(eq("Counselling Provided"));
    verify(prescriptionDetail).setCounsellingProvidedList(isA(String[].class));
    verify(prescriptionDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescriptionDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setDeleted(isA(Boolean.class));
    verify(prescriptionDetail).setDiabetesScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setDiagnosisProvided(eq("Diagnosis Provided"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTCode(eq("Diagnosis Provided SCTCode"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTTerm(eq("Diagnosis Provided SCTTerm"));
    verify(prescriptionDetail).setExternalInvestigation(eq("External Investigation"));
    verify(prescriptionDetail).setHypertensionScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setInstruction(eq("Instruction"));
    verify(prescriptionDetail).setLastModDate(isA(Timestamp.class));
    verify(prescriptionDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescriptionDetail).setOralCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescriptionDetail).setPrescribedDrugs(isA(ArrayList.class));
    verify(prescriptionDetail).setPrescriptionID(isA(Long.class));
    verify(prescriptionDetail).setProcessed(eq("Processed"));
    verify(prescriptionDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescriptionDetail).setProvisionalDiagnosisList(isA(ArrayList.class));
    verify(prescriptionDetail).setRemarks(eq("Remarks"));
    verify(prescriptionDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescriptionDetail).setSyncedBy(eq("Synced By"));
    verify(prescriptionDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setTreatmentsOnSideEffects(eq("Treatments On Side Effects"));
    verify(prescriptionDetail).setVanID(isA(Integer.class));
    verify(prescriptionDetail).setVanSerialNo(isA(Long.class));
    verify(prescriptionDetail).setVehicalNo(eq("Vehical No"));
    verify(prescriptionDetail).setVisitCode(isA(Long.class));
    verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
    assertEquals(1L, actualSaveBeneficiaryPrescriptionResult.longValue());
  }

  @Test
  void testSaveBenPrescription() {
    // Arrange
    PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);
    when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);

    PrescriptionDetail prescription = new PrescriptionDetail();
    prescription.setBenVisitID(1L);
    prescription.setBeneficiaryRegID(1L);
    prescription.setBreastCancerConfirmed(true);
    prescription.setCervicalCancerConfirmed(true);
    prescription.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescription.setCounsellingProvided("Counselling Provided");
    prescription.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescription.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescription.setCreatedDate(mock(Timestamp.class));
    prescription.setDeleted(true);
    prescription.setDiabetesScreeningConfirmed(true);
    prescription.setDiagnosisProvided("Diagnosis Provided");
    prescription.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescription.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescription.setExternalInvestigation("External Investigation");
    prescription.setHypertensionScreeningConfirmed(true);
    prescription.setInstruction("Instruction");
    prescription.setLastModDate(mock(Timestamp.class));
    prescription.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescription.setOralCancerConfirmed(true);
    prescription.setParkingPlaceID(1);
    prescription.setPrescribedDrugs(new ArrayList<>());
    prescription.setPrescriptionID(1L);
    prescription.setProcessed("Processed");
    prescription.setProviderServiceMapID(1);
    prescription.setProvisionalDiagnosisList(new ArrayList<>());
    prescription.setRemarks("Remarks");
    prescription.setReservedForChange("Reserved For Change");
    prescription.setSyncedBy("Synced By");
    prescription.setSyncedDate(mock(Timestamp.class));
    prescription.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescription.setVanID(1);
    prescription.setVanSerialNo(1L);
    prescription.setVehicalNo("Vehical No");
    prescription.setVisitCode(1L);

    // Act
    Long actualSaveBenPrescriptionResult = commonNurseServiceImpl.saveBenPrescription(prescription);

    // Assert
    verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
    assertNull(prescription.getPrescriptionID());
    assertEquals(1L, actualSaveBenPrescriptionResult.longValue());
  }

  @Test
  void testSaveBenPrescription2() {
    // Arrange
    PrescriptionDetail prescriptionDetail = mock(PrescriptionDetail.class);
    when(prescriptionDetail.getPrescriptionID()).thenReturn(-1L);
    doNothing().when(prescriptionDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBreastCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setCervicalCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setConfirmatoryDiagnosis(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvidedList(Mockito.<String[]>any());
    doNothing().when(prescriptionDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiabetesScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTCode(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTTerm(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setExternalInvestigation(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setHypertensionScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setInstruction(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setOralCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setPrescribedDrugs(Mockito.<ArrayList<PrescribedDrugDetail>>any());
    doNothing().when(prescriptionDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
    doNothing().when(prescriptionDetail).setRemarks(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setTreatmentsOnSideEffects(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVisitCode(Mockito.<Long>any());
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);
    when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);

    PrescriptionDetail prescription = new PrescriptionDetail();
    prescription.setBenVisitID(1L);
    prescription.setBeneficiaryRegID(1L);
    prescription.setBreastCancerConfirmed(true);
    prescription.setCervicalCancerConfirmed(true);
    prescription.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescription.setCounsellingProvided("Counselling Provided");
    prescription.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescription.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescription.setCreatedDate(mock(Timestamp.class));
    prescription.setDeleted(true);
    prescription.setDiabetesScreeningConfirmed(true);
    prescription.setDiagnosisProvided("Diagnosis Provided");
    prescription.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescription.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescription.setExternalInvestigation("External Investigation");
    prescription.setHypertensionScreeningConfirmed(true);
    prescription.setInstruction("Instruction");
    prescription.setLastModDate(mock(Timestamp.class));
    prescription.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescription.setOralCancerConfirmed(true);
    prescription.setParkingPlaceID(1);
    prescription.setPrescribedDrugs(new ArrayList<>());
    prescription.setPrescriptionID(1L);
    prescription.setProcessed("Processed");
    prescription.setProviderServiceMapID(1);
    prescription.setProvisionalDiagnosisList(new ArrayList<>());
    prescription.setRemarks("Remarks");
    prescription.setReservedForChange("Reserved For Change");
    prescription.setSyncedBy("Synced By");
    prescription.setSyncedDate(mock(Timestamp.class));
    prescription.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescription.setVanID(1);
    prescription.setVanSerialNo(1L);
    prescription.setVehicalNo("Vehical No");
    prescription.setVisitCode(1L);

    // Act
    Long actualSaveBenPrescriptionResult = commonNurseServiceImpl.saveBenPrescription(prescription);

    // Assert
    verify(prescriptionDetail).getPrescriptionID();
    verify(prescriptionDetail).setBenVisitID(isA(Long.class));
    verify(prescriptionDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescriptionDetail).setBreastCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setCervicalCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
    verify(prescriptionDetail).setCounsellingProvided(eq("Counselling Provided"));
    verify(prescriptionDetail).setCounsellingProvidedList(isA(String[].class));
    verify(prescriptionDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescriptionDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setDeleted(isA(Boolean.class));
    verify(prescriptionDetail).setDiabetesScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setDiagnosisProvided(eq("Diagnosis Provided"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTCode(eq("Diagnosis Provided SCTCode"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTTerm(eq("Diagnosis Provided SCTTerm"));
    verify(prescriptionDetail).setExternalInvestigation(eq("External Investigation"));
    verify(prescriptionDetail).setHypertensionScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setInstruction(eq("Instruction"));
    verify(prescriptionDetail).setLastModDate(isA(Timestamp.class));
    verify(prescriptionDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescriptionDetail).setOralCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescriptionDetail).setPrescribedDrugs(isA(ArrayList.class));
    verify(prescriptionDetail).setPrescriptionID(isA(Long.class));
    verify(prescriptionDetail).setProcessed(eq("Processed"));
    verify(prescriptionDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescriptionDetail).setProvisionalDiagnosisList(isA(ArrayList.class));
    verify(prescriptionDetail).setRemarks(eq("Remarks"));
    verify(prescriptionDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescriptionDetail).setSyncedBy(eq("Synced By"));
    verify(prescriptionDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setTreatmentsOnSideEffects(eq("Treatments On Side Effects"));
    verify(prescriptionDetail).setVanID(isA(Integer.class));
    verify(prescriptionDetail).setVanSerialNo(isA(Long.class));
    verify(prescriptionDetail).setVehicalNo(eq("Vehical No"));
    verify(prescriptionDetail).setVisitCode(isA(Long.class));
    verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
    assertNull(prescription.getPrescriptionID());
    assertNull(actualSaveBenPrescriptionResult);
  }

  @Test
  void testSaveBeneficiaryLabTestOrderDetails() {
    // Arrange, Act and Assert
    assertEquals(1L, commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(new JsonObject(), 1L).longValue());
  }

  @Test
  void testSaveBeneficiaryLabTestOrderDetails2() {
    // Arrange
    JsonObject caseSheet = new JsonObject();
    caseSheet.add("labTestOrders", new JsonArray(3));

    // Act and Assert
    assertEquals(1L, commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(caseSheet, 1L).longValue());
  }

  @Test
  void testSaveBeneficiaryLabTestOrderDetails3() {
    // Arrange
    JsonObject caseSheet = new JsonObject();
    caseSheet.addProperty("labTestOrders", "42");

    // Act and Assert
    assertEquals(1L, commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(caseSheet, 1L).longValue());
  }

  @Test
  void testSaveBeneficiaryLabTestOrderDetails4() {
    // Arrange
    JsonObject caseSheet = new JsonObject();
    caseSheet.addProperty("labTestOrders", (String) null);

    // Act and Assert
    assertEquals(1L, commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(caseSheet, 1L).longValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList() {
    // Arrange, Act and Assert
    assertEquals(1, commonNurseServiceImpl.saveBenPrescribedDrugsList(new ArrayList<>()).intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList2() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());

    PrescribedDrugDetail prescribedDrugDetail = new PrescribedDrugDetail();
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList3() {
    // Arrange
    PrescribedDrugDetail prescribedDrugDetail = new PrescribedDrugDetail();
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Tablet");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Tablet");
    prescribedDrugDetail.setDrugStrength("Tablet");
    prescribedDrugDetail.setDrugTradeOrBrandName("Tablet");
    prescribedDrugDetail.setDuration("Tablet");
    prescribedDrugDetail.setFormName("Tablet");
    prescribedDrugDetail.setFrequency("Tablet");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Tablet");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Tablet");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Tablet");
    prescribedDrugDetail.setReservedForChange("Tablet");
    prescribedDrugDetail.setRoute("Tablet");
    prescribedDrugDetail.setSctCode("Tablet");
    prescribedDrugDetail.setSctTerm("Tablet");
    prescribedDrugDetail.setSyncedBy("Tablet");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Tablet");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Tablet");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any()))
        .thenReturn(prescribedDrugDetailList);

    PrescribedDrugDetail prescribedDrugDetail2 = new PrescribedDrugDetail();
    prescribedDrugDetail2.setBenVisitID(1L);
    prescribedDrugDetail2.setBeneficiaryRegID(1L);
    prescribedDrugDetail2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail2.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail2.setDeleted(true);
    prescribedDrugDetail2.setDose("Dose");
    prescribedDrugDetail2.setDrugID(1);
    prescribedDrugDetail2.setDrugName("Drug Name");
    prescribedDrugDetail2.setDrugStrength("Drug Strength");
    prescribedDrugDetail2.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail2.setDuration("Duration");
    prescribedDrugDetail2.setFormName("Form Name");
    prescribedDrugDetail2.setFrequency("Frequency");
    prescribedDrugDetail2.setId(1L);
    prescribedDrugDetail2.setInstructions("Instructions");
    prescribedDrugDetail2.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail2.setParkingPlaceID(1);
    prescribedDrugDetail2.setPrescriptionID(1L);
    prescribedDrugDetail2.setProcessed("Processed");
    prescribedDrugDetail2.setProviderServiceMapID(1);
    prescribedDrugDetail2.setQtyPrescribed(1);
    prescribedDrugDetail2.setRelationToFood("Relation To Food");
    prescribedDrugDetail2.setReservedForChange("Reserved For Change");
    prescribedDrugDetail2.setRoute("Route");
    prescribedDrugDetail2.setSctCode("Sct Code");
    prescribedDrugDetail2.setSctTerm("Sct Term");
    prescribedDrugDetail2.setSyncedBy("Synced By");
    prescribedDrugDetail2.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail2.setUnit("Unit");
    prescribedDrugDetail2.setVanID(1);
    prescribedDrugDetail2.setVanSerialNo(1L);
    prescribedDrugDetail2.setVehicalNo("Vehical No");
    prescribedDrugDetail2.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList2 = new ArrayList<>();
    prescribedDrugDetailList2.add(prescribedDrugDetail2);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList2);

    // Assert
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(1, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList4() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());

    PrescribedDrugDetail prescribedDrugDetail = new PrescribedDrugDetail();
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    PrescribedDrugDetail prescribedDrugDetail2 = new PrescribedDrugDetail();
    prescribedDrugDetail2.setBenVisitID(2L);
    prescribedDrugDetail2.setBeneficiaryRegID(2L);
    prescribedDrugDetail2.setCreatedBy("Tablet");
    prescribedDrugDetail2.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail2.setDeleted(false);
    prescribedDrugDetail2.setDose("Capsule");
    prescribedDrugDetail2.setDrugID(2);
    prescribedDrugDetail2.setDrugName("Capsule");
    prescribedDrugDetail2.setDrugStrength("Capsule");
    prescribedDrugDetail2.setDrugTradeOrBrandName("Capsule");
    prescribedDrugDetail2.setDuration("Capsule");
    prescribedDrugDetail2.setFormName("Capsule");
    prescribedDrugDetail2.setFrequency("Capsule");
    prescribedDrugDetail2.setId(2L);
    prescribedDrugDetail2.setInstructions("Capsule");
    prescribedDrugDetail2.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail2.setModifiedBy("Tablet");
    prescribedDrugDetail2.setParkingPlaceID(2);
    prescribedDrugDetail2.setPrescriptionID(2L);
    prescribedDrugDetail2.setProcessed("Capsule");
    prescribedDrugDetail2.setProviderServiceMapID(2);
    prescribedDrugDetail2.setQtyPrescribed(0);
    prescribedDrugDetail2.setRelationToFood("Capsule");
    prescribedDrugDetail2.setReservedForChange("Capsule");
    prescribedDrugDetail2.setRoute("Capsule");
    prescribedDrugDetail2.setSctCode("Capsule");
    prescribedDrugDetail2.setSctTerm("Capsule");
    prescribedDrugDetail2.setSyncedBy("Capsule");
    prescribedDrugDetail2.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail2.setUnit("Capsule");
    prescribedDrugDetail2.setVanID(2);
    prescribedDrugDetail2.setVanSerialNo(0L);
    prescribedDrugDetail2.setVehicalNo("Capsule");
    prescribedDrugDetail2.setVisitCode(0L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail2);
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList5() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());
    PrescribedDrugDetail prescribedDrugDetail = mock(PrescribedDrugDetail.class);
    when(prescribedDrugDetail.getFormName()).thenReturn("Form Name");
    doNothing().when(prescribedDrugDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescribedDrugDetail).setDose(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setDrugName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugStrength(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugTradeOrBrandName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDuration(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFormName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFrequency(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setId(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setInstructions(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setQtyPrescribed(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setRelationToFood(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setRoute(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctCode(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctTerm(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setUnit(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVisitCode(Mockito.<Long>any());
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetail, atLeast(1)).getFormName();
    verify(prescribedDrugDetail).setBenVisitID(isA(Long.class));
    verify(prescribedDrugDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescribedDrugDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescribedDrugDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setDeleted(isA(Boolean.class));
    verify(prescribedDrugDetail).setDose(eq("Dose"));
    verify(prescribedDrugDetail).setDrugID(isA(Integer.class));
    verify(prescribedDrugDetail).setDrugName(eq("Drug Name"));
    verify(prescribedDrugDetail).setDrugStrength(eq("Drug Strength"));
    verify(prescribedDrugDetail).setDrugTradeOrBrandName(eq("Drug Trade Or Brand Name"));
    verify(prescribedDrugDetail).setDuration(eq("Duration"));
    verify(prescribedDrugDetail).setFormName(eq("Form Name"));
    verify(prescribedDrugDetail).setFrequency(eq("Frequency"));
    verify(prescribedDrugDetail).setId(isA(Long.class));
    verify(prescribedDrugDetail).setInstructions(eq("Instructions"));
    verify(prescribedDrugDetail).setLastModDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescribedDrugDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescribedDrugDetail).setPrescriptionID(isA(Long.class));
    verify(prescribedDrugDetail).setProcessed(eq("Processed"));
    verify(prescribedDrugDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescribedDrugDetail).setQtyPrescribed(isA(Integer.class));
    verify(prescribedDrugDetail).setRelationToFood(eq("Relation To Food"));
    verify(prescribedDrugDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescribedDrugDetail).setRoute(eq("Route"));
    verify(prescribedDrugDetail).setSctCode(eq("Sct Code"));
    verify(prescribedDrugDetail).setSctTerm(eq("Sct Term"));
    verify(prescribedDrugDetail).setSyncedBy(eq("Synced By"));
    verify(prescribedDrugDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setUnit(eq("Unit"));
    verify(prescribedDrugDetail).setVanID(isA(Integer.class));
    verify(prescribedDrugDetail).setVanSerialNo(isA(Long.class));
    verify(prescribedDrugDetail).setVehicalNo(eq("Vehical No"));
    verify(prescribedDrugDetail).setVisitCode(isA(Long.class));
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList6() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());
    PrescribedDrugDetail prescribedDrugDetail = mock(PrescribedDrugDetail.class);
    when(prescribedDrugDetail.getDose()).thenReturn("Dose");
    when(prescribedDrugDetail.getDuration()).thenReturn("Duration");
    when(prescribedDrugDetail.getFrequency()).thenReturn("Frequency");
    when(prescribedDrugDetail.getUnit()).thenReturn("Unit");
    when(prescribedDrugDetail.getFormName()).thenReturn("Tablet");
    doNothing().when(prescribedDrugDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescribedDrugDetail).setDose(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setDrugName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugStrength(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugTradeOrBrandName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDuration(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFormName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFrequency(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setId(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setInstructions(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setQtyPrescribed(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setRelationToFood(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setRoute(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctCode(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctTerm(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setUnit(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVisitCode(Mockito.<Long>any());
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetail).getDose();
    verify(prescribedDrugDetail).getDuration();
    verify(prescribedDrugDetail, atLeast(1)).getFormName();
    verify(prescribedDrugDetail).getFrequency();
    verify(prescribedDrugDetail).getUnit();
    verify(prescribedDrugDetail).setBenVisitID(isA(Long.class));
    verify(prescribedDrugDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescribedDrugDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescribedDrugDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setDeleted(isA(Boolean.class));
    verify(prescribedDrugDetail).setDose(eq("Dose"));
    verify(prescribedDrugDetail).setDrugID(isA(Integer.class));
    verify(prescribedDrugDetail).setDrugName(eq("Drug Name"));
    verify(prescribedDrugDetail).setDrugStrength(eq("Drug Strength"));
    verify(prescribedDrugDetail).setDrugTradeOrBrandName(eq("Drug Trade Or Brand Name"));
    verify(prescribedDrugDetail).setDuration(eq("Duration"));
    verify(prescribedDrugDetail).setFormName(eq("Form Name"));
    verify(prescribedDrugDetail).setFrequency(eq("Frequency"));
    verify(prescribedDrugDetail).setId(isA(Long.class));
    verify(prescribedDrugDetail).setInstructions(eq("Instructions"));
    verify(prescribedDrugDetail).setLastModDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescribedDrugDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescribedDrugDetail).setPrescriptionID(isA(Long.class));
    verify(prescribedDrugDetail).setProcessed(eq("Processed"));
    verify(prescribedDrugDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescribedDrugDetail, atLeast(1)).setQtyPrescribed(Mockito.<Integer>any());
    verify(prescribedDrugDetail).setRelationToFood(eq("Relation To Food"));
    verify(prescribedDrugDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescribedDrugDetail).setRoute(eq("Route"));
    verify(prescribedDrugDetail).setSctCode(eq("Sct Code"));
    verify(prescribedDrugDetail).setSctTerm(eq("Sct Term"));
    verify(prescribedDrugDetail).setSyncedBy(eq("Synced By"));
    verify(prescribedDrugDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setUnit(eq("Unit"));
    verify(prescribedDrugDetail).setVanID(isA(Integer.class));
    verify(prescribedDrugDetail).setVanSerialNo(isA(Long.class));
    verify(prescribedDrugDetail).setVehicalNo(eq("Vehical No"));
    verify(prescribedDrugDetail).setVisitCode(isA(Long.class));
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList7() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());
    PrescribedDrugDetail prescribedDrugDetail = mock(PrescribedDrugDetail.class);
    when(prescribedDrugDetail.getDose()).thenReturn(null);
    when(prescribedDrugDetail.getDuration()).thenReturn("Duration");
    when(prescribedDrugDetail.getFrequency()).thenReturn("Frequency");
    when(prescribedDrugDetail.getUnit()).thenReturn("Unit");
    when(prescribedDrugDetail.getFormName()).thenReturn("Tablet");
    doNothing().when(prescribedDrugDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescribedDrugDetail).setDose(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setDrugName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugStrength(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugTradeOrBrandName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDuration(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFormName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFrequency(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setId(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setInstructions(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setQtyPrescribed(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setRelationToFood(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setRoute(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctCode(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctTerm(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setUnit(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVisitCode(Mockito.<Long>any());
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetail).getDose();
    verify(prescribedDrugDetail).getDuration();
    verify(prescribedDrugDetail, atLeast(1)).getFormName();
    verify(prescribedDrugDetail).getFrequency();
    verify(prescribedDrugDetail).getUnit();
    verify(prescribedDrugDetail).setBenVisitID(isA(Long.class));
    verify(prescribedDrugDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescribedDrugDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescribedDrugDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setDeleted(isA(Boolean.class));
    verify(prescribedDrugDetail).setDose(eq("Dose"));
    verify(prescribedDrugDetail).setDrugID(isA(Integer.class));
    verify(prescribedDrugDetail).setDrugName(eq("Drug Name"));
    verify(prescribedDrugDetail).setDrugStrength(eq("Drug Strength"));
    verify(prescribedDrugDetail).setDrugTradeOrBrandName(eq("Drug Trade Or Brand Name"));
    verify(prescribedDrugDetail).setDuration(eq("Duration"));
    verify(prescribedDrugDetail).setFormName(eq("Form Name"));
    verify(prescribedDrugDetail).setFrequency(eq("Frequency"));
    verify(prescribedDrugDetail).setId(isA(Long.class));
    verify(prescribedDrugDetail).setInstructions(eq("Instructions"));
    verify(prescribedDrugDetail).setLastModDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescribedDrugDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescribedDrugDetail).setPrescriptionID(isA(Long.class));
    verify(prescribedDrugDetail).setProcessed(eq("Processed"));
    verify(prescribedDrugDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescribedDrugDetail, atLeast(1)).setQtyPrescribed(Mockito.<Integer>any());
    verify(prescribedDrugDetail).setRelationToFood(eq("Relation To Food"));
    verify(prescribedDrugDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescribedDrugDetail).setRoute(eq("Route"));
    verify(prescribedDrugDetail).setSctCode(eq("Sct Code"));
    verify(prescribedDrugDetail).setSctTerm(eq("Sct Term"));
    verify(prescribedDrugDetail).setSyncedBy(eq("Synced By"));
    verify(prescribedDrugDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setUnit(eq("Unit"));
    verify(prescribedDrugDetail).setVanID(isA(Integer.class));
    verify(prescribedDrugDetail).setVanSerialNo(isA(Long.class));
    verify(prescribedDrugDetail).setVehicalNo(eq("Vehical No"));
    verify(prescribedDrugDetail).setVisitCode(isA(Long.class));
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList8() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());
    PrescribedDrugDetail prescribedDrugDetail = mock(PrescribedDrugDetail.class);
    when(prescribedDrugDetail.getDose()).thenReturn("Dose");
    when(prescribedDrugDetail.getDuration()).thenReturn(null);
    when(prescribedDrugDetail.getFrequency()).thenReturn("Frequency");
    when(prescribedDrugDetail.getUnit()).thenReturn("Unit");
    when(prescribedDrugDetail.getFormName()).thenReturn("Tablet");
    doNothing().when(prescribedDrugDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescribedDrugDetail).setDose(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setDrugName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugStrength(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugTradeOrBrandName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDuration(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFormName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFrequency(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setId(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setInstructions(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setQtyPrescribed(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setRelationToFood(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setRoute(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctCode(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctTerm(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setUnit(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVisitCode(Mockito.<Long>any());
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetail).getDose();
    verify(prescribedDrugDetail).getDuration();
    verify(prescribedDrugDetail, atLeast(1)).getFormName();
    verify(prescribedDrugDetail).getFrequency();
    verify(prescribedDrugDetail).getUnit();
    verify(prescribedDrugDetail).setBenVisitID(isA(Long.class));
    verify(prescribedDrugDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescribedDrugDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescribedDrugDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setDeleted(isA(Boolean.class));
    verify(prescribedDrugDetail).setDose(eq("Dose"));
    verify(prescribedDrugDetail).setDrugID(isA(Integer.class));
    verify(prescribedDrugDetail).setDrugName(eq("Drug Name"));
    verify(prescribedDrugDetail).setDrugStrength(eq("Drug Strength"));
    verify(prescribedDrugDetail).setDrugTradeOrBrandName(eq("Drug Trade Or Brand Name"));
    verify(prescribedDrugDetail).setDuration(eq("Duration"));
    verify(prescribedDrugDetail).setFormName(eq("Form Name"));
    verify(prescribedDrugDetail).setFrequency(eq("Frequency"));
    verify(prescribedDrugDetail).setId(isA(Long.class));
    verify(prescribedDrugDetail).setInstructions(eq("Instructions"));
    verify(prescribedDrugDetail).setLastModDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescribedDrugDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescribedDrugDetail).setPrescriptionID(isA(Long.class));
    verify(prescribedDrugDetail).setProcessed(eq("Processed"));
    verify(prescribedDrugDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescribedDrugDetail, atLeast(1)).setQtyPrescribed(Mockito.<Integer>any());
    verify(prescribedDrugDetail).setRelationToFood(eq("Relation To Food"));
    verify(prescribedDrugDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescribedDrugDetail).setRoute(eq("Route"));
    verify(prescribedDrugDetail).setSctCode(eq("Sct Code"));
    verify(prescribedDrugDetail).setSctTerm(eq("Sct Term"));
    verify(prescribedDrugDetail).setSyncedBy(eq("Synced By"));
    verify(prescribedDrugDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setUnit(eq("Unit"));
    verify(prescribedDrugDetail).setVanID(isA(Integer.class));
    verify(prescribedDrugDetail).setVanSerialNo(isA(Long.class));
    verify(prescribedDrugDetail).setVehicalNo(eq("Vehical No"));
    verify(prescribedDrugDetail).setVisitCode(isA(Long.class));
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList9() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());
    PrescribedDrugDetail prescribedDrugDetail = mock(PrescribedDrugDetail.class);
    when(prescribedDrugDetail.getDose()).thenReturn("Dose");
    when(prescribedDrugDetail.getDuration()).thenReturn("Duration");
    when(prescribedDrugDetail.getFrequency()).thenReturn("Once Daily(OD)");
    when(prescribedDrugDetail.getUnit()).thenReturn("Unit");
    when(prescribedDrugDetail.getFormName()).thenReturn("Tablet");
    doNothing().when(prescribedDrugDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescribedDrugDetail).setDose(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setDrugName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugStrength(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugTradeOrBrandName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDuration(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFormName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFrequency(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setId(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setInstructions(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setQtyPrescribed(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setRelationToFood(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setRoute(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctCode(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctTerm(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setUnit(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVisitCode(Mockito.<Long>any());
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetail).getDose();
    verify(prescribedDrugDetail).getDuration();
    verify(prescribedDrugDetail, atLeast(1)).getFormName();
    verify(prescribedDrugDetail).getFrequency();
    verify(prescribedDrugDetail).getUnit();
    verify(prescribedDrugDetail).setBenVisitID(isA(Long.class));
    verify(prescribedDrugDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescribedDrugDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescribedDrugDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setDeleted(isA(Boolean.class));
    verify(prescribedDrugDetail).setDose(eq("Dose"));
    verify(prescribedDrugDetail).setDrugID(isA(Integer.class));
    verify(prescribedDrugDetail).setDrugName(eq("Drug Name"));
    verify(prescribedDrugDetail).setDrugStrength(eq("Drug Strength"));
    verify(prescribedDrugDetail).setDrugTradeOrBrandName(eq("Drug Trade Or Brand Name"));
    verify(prescribedDrugDetail).setDuration(eq("Duration"));
    verify(prescribedDrugDetail).setFormName(eq("Form Name"));
    verify(prescribedDrugDetail).setFrequency(eq("Frequency"));
    verify(prescribedDrugDetail).setId(isA(Long.class));
    verify(prescribedDrugDetail).setInstructions(eq("Instructions"));
    verify(prescribedDrugDetail).setLastModDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescribedDrugDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescribedDrugDetail).setPrescriptionID(isA(Long.class));
    verify(prescribedDrugDetail).setProcessed(eq("Processed"));
    verify(prescribedDrugDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescribedDrugDetail, atLeast(1)).setQtyPrescribed(Mockito.<Integer>any());
    verify(prescribedDrugDetail).setRelationToFood(eq("Relation To Food"));
    verify(prescribedDrugDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescribedDrugDetail).setRoute(eq("Route"));
    verify(prescribedDrugDetail).setSctCode(eq("Sct Code"));
    verify(prescribedDrugDetail).setSctTerm(eq("Sct Term"));
    verify(prescribedDrugDetail).setSyncedBy(eq("Synced By"));
    verify(prescribedDrugDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setUnit(eq("Unit"));
    verify(prescribedDrugDetail).setVanID(isA(Integer.class));
    verify(prescribedDrugDetail).setVanSerialNo(isA(Long.class));
    verify(prescribedDrugDetail).setVehicalNo(eq("Vehical No"));
    verify(prescribedDrugDetail).setVisitCode(isA(Long.class));
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList10() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());
    PrescribedDrugDetail prescribedDrugDetail = mock(PrescribedDrugDetail.class);
    when(prescribedDrugDetail.getDose()).thenReturn("Dose");
    when(prescribedDrugDetail.getDuration()).thenReturn("Duration");
    when(prescribedDrugDetail.getFrequency()).thenReturn("Twice Daily(BD)");
    when(prescribedDrugDetail.getUnit()).thenReturn("Unit");
    when(prescribedDrugDetail.getFormName()).thenReturn("Tablet");
    doNothing().when(prescribedDrugDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescribedDrugDetail).setDose(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setDrugName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugStrength(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugTradeOrBrandName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDuration(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFormName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFrequency(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setId(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setInstructions(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setQtyPrescribed(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setRelationToFood(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setRoute(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctCode(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctTerm(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setUnit(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVisitCode(Mockito.<Long>any());
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetail).getDose();
    verify(prescribedDrugDetail).getDuration();
    verify(prescribedDrugDetail, atLeast(1)).getFormName();
    verify(prescribedDrugDetail).getFrequency();
    verify(prescribedDrugDetail).getUnit();
    verify(prescribedDrugDetail).setBenVisitID(isA(Long.class));
    verify(prescribedDrugDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescribedDrugDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescribedDrugDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setDeleted(isA(Boolean.class));
    verify(prescribedDrugDetail).setDose(eq("Dose"));
    verify(prescribedDrugDetail).setDrugID(isA(Integer.class));
    verify(prescribedDrugDetail).setDrugName(eq("Drug Name"));
    verify(prescribedDrugDetail).setDrugStrength(eq("Drug Strength"));
    verify(prescribedDrugDetail).setDrugTradeOrBrandName(eq("Drug Trade Or Brand Name"));
    verify(prescribedDrugDetail).setDuration(eq("Duration"));
    verify(prescribedDrugDetail).setFormName(eq("Form Name"));
    verify(prescribedDrugDetail).setFrequency(eq("Frequency"));
    verify(prescribedDrugDetail).setId(isA(Long.class));
    verify(prescribedDrugDetail).setInstructions(eq("Instructions"));
    verify(prescribedDrugDetail).setLastModDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescribedDrugDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescribedDrugDetail).setPrescriptionID(isA(Long.class));
    verify(prescribedDrugDetail).setProcessed(eq("Processed"));
    verify(prescribedDrugDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescribedDrugDetail, atLeast(1)).setQtyPrescribed(Mockito.<Integer>any());
    verify(prescribedDrugDetail).setRelationToFood(eq("Relation To Food"));
    verify(prescribedDrugDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescribedDrugDetail).setRoute(eq("Route"));
    verify(prescribedDrugDetail).setSctCode(eq("Sct Code"));
    verify(prescribedDrugDetail).setSctTerm(eq("Sct Term"));
    verify(prescribedDrugDetail).setSyncedBy(eq("Synced By"));
    verify(prescribedDrugDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setUnit(eq("Unit"));
    verify(prescribedDrugDetail).setVanID(isA(Integer.class));
    verify(prescribedDrugDetail).setVanSerialNo(isA(Long.class));
    verify(prescribedDrugDetail).setVehicalNo(eq("Vehical No"));
    verify(prescribedDrugDetail).setVisitCode(isA(Long.class));
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList11() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());
    PrescribedDrugDetail prescribedDrugDetail = mock(PrescribedDrugDetail.class);
    when(prescribedDrugDetail.getDose()).thenReturn("Dose");
    when(prescribedDrugDetail.getDuration()).thenReturn("Duration");
    when(prescribedDrugDetail.getFrequency()).thenReturn("Thrice Daily (TID)");
    when(prescribedDrugDetail.getUnit()).thenReturn("Unit");
    when(prescribedDrugDetail.getFormName()).thenReturn("Tablet");
    doNothing().when(prescribedDrugDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescribedDrugDetail).setDose(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setDrugName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugStrength(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugTradeOrBrandName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDuration(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFormName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFrequency(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setId(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setInstructions(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setQtyPrescribed(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setRelationToFood(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setRoute(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctCode(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctTerm(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setUnit(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVisitCode(Mockito.<Long>any());
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetail).getDose();
    verify(prescribedDrugDetail).getDuration();
    verify(prescribedDrugDetail, atLeast(1)).getFormName();
    verify(prescribedDrugDetail).getFrequency();
    verify(prescribedDrugDetail).getUnit();
    verify(prescribedDrugDetail).setBenVisitID(isA(Long.class));
    verify(prescribedDrugDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescribedDrugDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescribedDrugDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setDeleted(isA(Boolean.class));
    verify(prescribedDrugDetail).setDose(eq("Dose"));
    verify(prescribedDrugDetail).setDrugID(isA(Integer.class));
    verify(prescribedDrugDetail).setDrugName(eq("Drug Name"));
    verify(prescribedDrugDetail).setDrugStrength(eq("Drug Strength"));
    verify(prescribedDrugDetail).setDrugTradeOrBrandName(eq("Drug Trade Or Brand Name"));
    verify(prescribedDrugDetail).setDuration(eq("Duration"));
    verify(prescribedDrugDetail).setFormName(eq("Form Name"));
    verify(prescribedDrugDetail).setFrequency(eq("Frequency"));
    verify(prescribedDrugDetail).setId(isA(Long.class));
    verify(prescribedDrugDetail).setInstructions(eq("Instructions"));
    verify(prescribedDrugDetail).setLastModDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescribedDrugDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescribedDrugDetail).setPrescriptionID(isA(Long.class));
    verify(prescribedDrugDetail).setProcessed(eq("Processed"));
    verify(prescribedDrugDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescribedDrugDetail, atLeast(1)).setQtyPrescribed(Mockito.<Integer>any());
    verify(prescribedDrugDetail).setRelationToFood(eq("Relation To Food"));
    verify(prescribedDrugDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescribedDrugDetail).setRoute(eq("Route"));
    verify(prescribedDrugDetail).setSctCode(eq("Sct Code"));
    verify(prescribedDrugDetail).setSctTerm(eq("Sct Term"));
    verify(prescribedDrugDetail).setSyncedBy(eq("Synced By"));
    verify(prescribedDrugDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setUnit(eq("Unit"));
    verify(prescribedDrugDetail).setVanID(isA(Integer.class));
    verify(prescribedDrugDetail).setVanSerialNo(isA(Long.class));
    verify(prescribedDrugDetail).setVehicalNo(eq("Vehical No"));
    verify(prescribedDrugDetail).setVisitCode(isA(Long.class));
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList12() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());
    PrescribedDrugDetail prescribedDrugDetail = mock(PrescribedDrugDetail.class);
    when(prescribedDrugDetail.getDose()).thenReturn("Dose");
    when(prescribedDrugDetail.getDuration()).thenReturn("Duration");
    when(prescribedDrugDetail.getFrequency()).thenReturn("Four Times in a Day (QID)");
    when(prescribedDrugDetail.getUnit()).thenReturn("Unit");
    when(prescribedDrugDetail.getFormName()).thenReturn("Tablet");
    doNothing().when(prescribedDrugDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescribedDrugDetail).setDose(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setDrugName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugStrength(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugTradeOrBrandName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDuration(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFormName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFrequency(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setId(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setInstructions(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setQtyPrescribed(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setRelationToFood(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setRoute(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctCode(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctTerm(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setUnit(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVisitCode(Mockito.<Long>any());
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetail).getDose();
    verify(prescribedDrugDetail).getDuration();
    verify(prescribedDrugDetail, atLeast(1)).getFormName();
    verify(prescribedDrugDetail).getFrequency();
    verify(prescribedDrugDetail).getUnit();
    verify(prescribedDrugDetail).setBenVisitID(isA(Long.class));
    verify(prescribedDrugDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescribedDrugDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescribedDrugDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setDeleted(isA(Boolean.class));
    verify(prescribedDrugDetail).setDose(eq("Dose"));
    verify(prescribedDrugDetail).setDrugID(isA(Integer.class));
    verify(prescribedDrugDetail).setDrugName(eq("Drug Name"));
    verify(prescribedDrugDetail).setDrugStrength(eq("Drug Strength"));
    verify(prescribedDrugDetail).setDrugTradeOrBrandName(eq("Drug Trade Or Brand Name"));
    verify(prescribedDrugDetail).setDuration(eq("Duration"));
    verify(prescribedDrugDetail).setFormName(eq("Form Name"));
    verify(prescribedDrugDetail).setFrequency(eq("Frequency"));
    verify(prescribedDrugDetail).setId(isA(Long.class));
    verify(prescribedDrugDetail).setInstructions(eq("Instructions"));
    verify(prescribedDrugDetail).setLastModDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescribedDrugDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescribedDrugDetail).setPrescriptionID(isA(Long.class));
    verify(prescribedDrugDetail).setProcessed(eq("Processed"));
    verify(prescribedDrugDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescribedDrugDetail, atLeast(1)).setQtyPrescribed(Mockito.<Integer>any());
    verify(prescribedDrugDetail).setRelationToFood(eq("Relation To Food"));
    verify(prescribedDrugDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescribedDrugDetail).setRoute(eq("Route"));
    verify(prescribedDrugDetail).setSctCode(eq("Sct Code"));
    verify(prescribedDrugDetail).setSctTerm(eq("Sct Term"));
    verify(prescribedDrugDetail).setSyncedBy(eq("Synced By"));
    verify(prescribedDrugDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setUnit(eq("Unit"));
    verify(prescribedDrugDetail).setVanID(isA(Integer.class));
    verify(prescribedDrugDetail).setVanSerialNo(isA(Long.class));
    verify(prescribedDrugDetail).setVehicalNo(eq("Vehical No"));
    verify(prescribedDrugDetail).setVisitCode(isA(Long.class));
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList13() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());
    PrescribedDrugDetail prescribedDrugDetail = mock(PrescribedDrugDetail.class);
    when(prescribedDrugDetail.getDose()).thenReturn("Dose");
    when(prescribedDrugDetail.getDuration()).thenReturn("Duration");
    when(prescribedDrugDetail.getFrequency()).thenReturn("Single Dose");
    when(prescribedDrugDetail.getUnit()).thenReturn("Unit");
    when(prescribedDrugDetail.getFormName()).thenReturn("Tablet");
    doNothing().when(prescribedDrugDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescribedDrugDetail).setDose(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setDrugName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugStrength(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugTradeOrBrandName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDuration(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFormName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFrequency(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setId(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setInstructions(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setQtyPrescribed(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setRelationToFood(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setRoute(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctCode(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctTerm(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setUnit(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVisitCode(Mockito.<Long>any());
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetail).getDose();
    verify(prescribedDrugDetail).getDuration();
    verify(prescribedDrugDetail, atLeast(1)).getFormName();
    verify(prescribedDrugDetail).getFrequency();
    verify(prescribedDrugDetail).getUnit();
    verify(prescribedDrugDetail).setBenVisitID(isA(Long.class));
    verify(prescribedDrugDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescribedDrugDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescribedDrugDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setDeleted(isA(Boolean.class));
    verify(prescribedDrugDetail).setDose(eq("Dose"));
    verify(prescribedDrugDetail).setDrugID(isA(Integer.class));
    verify(prescribedDrugDetail).setDrugName(eq("Drug Name"));
    verify(prescribedDrugDetail).setDrugStrength(eq("Drug Strength"));
    verify(prescribedDrugDetail).setDrugTradeOrBrandName(eq("Drug Trade Or Brand Name"));
    verify(prescribedDrugDetail).setDuration(eq("Duration"));
    verify(prescribedDrugDetail).setFormName(eq("Form Name"));
    verify(prescribedDrugDetail).setFrequency(eq("Frequency"));
    verify(prescribedDrugDetail).setId(isA(Long.class));
    verify(prescribedDrugDetail).setInstructions(eq("Instructions"));
    verify(prescribedDrugDetail).setLastModDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescribedDrugDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescribedDrugDetail).setPrescriptionID(isA(Long.class));
    verify(prescribedDrugDetail).setProcessed(eq("Processed"));
    verify(prescribedDrugDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescribedDrugDetail, atLeast(1)).setQtyPrescribed(Mockito.<Integer>any());
    verify(prescribedDrugDetail).setRelationToFood(eq("Relation To Food"));
    verify(prescribedDrugDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescribedDrugDetail).setRoute(eq("Route"));
    verify(prescribedDrugDetail).setSctCode(eq("Sct Code"));
    verify(prescribedDrugDetail).setSctTerm(eq("Sct Term"));
    verify(prescribedDrugDetail).setSyncedBy(eq("Synced By"));
    verify(prescribedDrugDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setUnit(eq("Unit"));
    verify(prescribedDrugDetail).setVanID(isA(Integer.class));
    verify(prescribedDrugDetail).setVanSerialNo(isA(Long.class));
    verify(prescribedDrugDetail).setVehicalNo(eq("Vehical No"));
    verify(prescribedDrugDetail).setVisitCode(isA(Long.class));
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList14() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());
    PrescribedDrugDetail prescribedDrugDetail = mock(PrescribedDrugDetail.class);
    when(prescribedDrugDetail.getDose()).thenReturn("Dose");
    when(prescribedDrugDetail.getDuration()).thenReturn("Duration");
    when(prescribedDrugDetail.getFrequency()).thenReturn("Stat Dose");
    when(prescribedDrugDetail.getUnit()).thenReturn("Unit");
    when(prescribedDrugDetail.getFormName()).thenReturn("Tablet");
    doNothing().when(prescribedDrugDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescribedDrugDetail).setDose(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setDrugName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugStrength(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugTradeOrBrandName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDuration(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFormName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFrequency(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setId(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setInstructions(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setQtyPrescribed(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setRelationToFood(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setRoute(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctCode(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctTerm(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setUnit(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVisitCode(Mockito.<Long>any());
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetail).getDose();
    verify(prescribedDrugDetail).getDuration();
    verify(prescribedDrugDetail, atLeast(1)).getFormName();
    verify(prescribedDrugDetail).getFrequency();
    verify(prescribedDrugDetail).getUnit();
    verify(prescribedDrugDetail).setBenVisitID(isA(Long.class));
    verify(prescribedDrugDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescribedDrugDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescribedDrugDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setDeleted(isA(Boolean.class));
    verify(prescribedDrugDetail).setDose(eq("Dose"));
    verify(prescribedDrugDetail).setDrugID(isA(Integer.class));
    verify(prescribedDrugDetail).setDrugName(eq("Drug Name"));
    verify(prescribedDrugDetail).setDrugStrength(eq("Drug Strength"));
    verify(prescribedDrugDetail).setDrugTradeOrBrandName(eq("Drug Trade Or Brand Name"));
    verify(prescribedDrugDetail).setDuration(eq("Duration"));
    verify(prescribedDrugDetail).setFormName(eq("Form Name"));
    verify(prescribedDrugDetail).setFrequency(eq("Frequency"));
    verify(prescribedDrugDetail).setId(isA(Long.class));
    verify(prescribedDrugDetail).setInstructions(eq("Instructions"));
    verify(prescribedDrugDetail).setLastModDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescribedDrugDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescribedDrugDetail).setPrescriptionID(isA(Long.class));
    verify(prescribedDrugDetail).setProcessed(eq("Processed"));
    verify(prescribedDrugDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescribedDrugDetail, atLeast(1)).setQtyPrescribed(Mockito.<Integer>any());
    verify(prescribedDrugDetail).setRelationToFood(eq("Relation To Food"));
    verify(prescribedDrugDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescribedDrugDetail).setRoute(eq("Route"));
    verify(prescribedDrugDetail).setSctCode(eq("Sct Code"));
    verify(prescribedDrugDetail).setSctTerm(eq("Sct Term"));
    verify(prescribedDrugDetail).setSyncedBy(eq("Synced By"));
    verify(prescribedDrugDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setUnit(eq("Unit"));
    verify(prescribedDrugDetail).setVanID(isA(Integer.class));
    verify(prescribedDrugDetail).setVanSerialNo(isA(Long.class));
    verify(prescribedDrugDetail).setVehicalNo(eq("Vehical No"));
    verify(prescribedDrugDetail).setVisitCode(isA(Long.class));
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList15() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());
    PrescribedDrugDetail prescribedDrugDetail = mock(PrescribedDrugDetail.class);
    when(prescribedDrugDetail.getDose()).thenReturn("Dose");
    when(prescribedDrugDetail.getDuration()).thenReturn("Duration");
    when(prescribedDrugDetail.getFrequency()).thenReturn(null);
    when(prescribedDrugDetail.getUnit()).thenReturn("Unit");
    when(prescribedDrugDetail.getFormName()).thenReturn("Tablet");
    doNothing().when(prescribedDrugDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescribedDrugDetail).setDose(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setDrugName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugStrength(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugTradeOrBrandName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDuration(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFormName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFrequency(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setId(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setInstructions(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setQtyPrescribed(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setRelationToFood(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setRoute(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctCode(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctTerm(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setUnit(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVisitCode(Mockito.<Long>any());
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetail).getDose();
    verify(prescribedDrugDetail).getDuration();
    verify(prescribedDrugDetail, atLeast(1)).getFormName();
    verify(prescribedDrugDetail).getFrequency();
    verify(prescribedDrugDetail).getUnit();
    verify(prescribedDrugDetail).setBenVisitID(isA(Long.class));
    verify(prescribedDrugDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescribedDrugDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescribedDrugDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setDeleted(isA(Boolean.class));
    verify(prescribedDrugDetail).setDose(eq("Dose"));
    verify(prescribedDrugDetail).setDrugID(isA(Integer.class));
    verify(prescribedDrugDetail).setDrugName(eq("Drug Name"));
    verify(prescribedDrugDetail).setDrugStrength(eq("Drug Strength"));
    verify(prescribedDrugDetail).setDrugTradeOrBrandName(eq("Drug Trade Or Brand Name"));
    verify(prescribedDrugDetail).setDuration(eq("Duration"));
    verify(prescribedDrugDetail).setFormName(eq("Form Name"));
    verify(prescribedDrugDetail).setFrequency(eq("Frequency"));
    verify(prescribedDrugDetail).setId(isA(Long.class));
    verify(prescribedDrugDetail).setInstructions(eq("Instructions"));
    verify(prescribedDrugDetail).setLastModDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescribedDrugDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescribedDrugDetail).setPrescriptionID(isA(Long.class));
    verify(prescribedDrugDetail).setProcessed(eq("Processed"));
    verify(prescribedDrugDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescribedDrugDetail, atLeast(1)).setQtyPrescribed(Mockito.<Integer>any());
    verify(prescribedDrugDetail).setRelationToFood(eq("Relation To Food"));
    verify(prescribedDrugDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescribedDrugDetail).setRoute(eq("Route"));
    verify(prescribedDrugDetail).setSctCode(eq("Sct Code"));
    verify(prescribedDrugDetail).setSctTerm(eq("Sct Term"));
    verify(prescribedDrugDetail).setSyncedBy(eq("Synced By"));
    verify(prescribedDrugDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setUnit(eq("Unit"));
    verify(prescribedDrugDetail).setVanID(isA(Integer.class));
    verify(prescribedDrugDetail).setVanSerialNo(isA(Long.class));
    verify(prescribedDrugDetail).setVehicalNo(eq("Vehical No"));
    verify(prescribedDrugDetail).setVisitCode(isA(Long.class));
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList16() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());
    PrescribedDrugDetail prescribedDrugDetail = mock(PrescribedDrugDetail.class);
    when(prescribedDrugDetail.getDose()).thenReturn("Dose");
    when(prescribedDrugDetail.getDuration()).thenReturn("Duration");
    when(prescribedDrugDetail.getFrequency()).thenReturn("Once in a Week");
    when(prescribedDrugDetail.getUnit()).thenReturn("Unit");
    when(prescribedDrugDetail.getFormName()).thenReturn("Tablet");
    doNothing().when(prescribedDrugDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescribedDrugDetail).setDose(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setDrugName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugStrength(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugTradeOrBrandName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDuration(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFormName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFrequency(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setId(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setInstructions(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setQtyPrescribed(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setRelationToFood(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setRoute(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctCode(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctTerm(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setUnit(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVisitCode(Mockito.<Long>any());
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetail).getDose();
    verify(prescribedDrugDetail).getDuration();
    verify(prescribedDrugDetail, atLeast(1)).getFormName();
    verify(prescribedDrugDetail).getFrequency();
    verify(prescribedDrugDetail).getUnit();
    verify(prescribedDrugDetail).setBenVisitID(isA(Long.class));
    verify(prescribedDrugDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescribedDrugDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescribedDrugDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setDeleted(isA(Boolean.class));
    verify(prescribedDrugDetail).setDose(eq("Dose"));
    verify(prescribedDrugDetail).setDrugID(isA(Integer.class));
    verify(prescribedDrugDetail).setDrugName(eq("Drug Name"));
    verify(prescribedDrugDetail).setDrugStrength(eq("Drug Strength"));
    verify(prescribedDrugDetail).setDrugTradeOrBrandName(eq("Drug Trade Or Brand Name"));
    verify(prescribedDrugDetail).setDuration(eq("Duration"));
    verify(prescribedDrugDetail).setFormName(eq("Form Name"));
    verify(prescribedDrugDetail).setFrequency(eq("Frequency"));
    verify(prescribedDrugDetail).setId(isA(Long.class));
    verify(prescribedDrugDetail).setInstructions(eq("Instructions"));
    verify(prescribedDrugDetail).setLastModDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescribedDrugDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescribedDrugDetail).setPrescriptionID(isA(Long.class));
    verify(prescribedDrugDetail).setProcessed(eq("Processed"));
    verify(prescribedDrugDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescribedDrugDetail, atLeast(1)).setQtyPrescribed(Mockito.<Integer>any());
    verify(prescribedDrugDetail).setRelationToFood(eq("Relation To Food"));
    verify(prescribedDrugDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescribedDrugDetail).setRoute(eq("Route"));
    verify(prescribedDrugDetail).setSctCode(eq("Sct Code"));
    verify(prescribedDrugDetail).setSctTerm(eq("Sct Term"));
    verify(prescribedDrugDetail).setSyncedBy(eq("Synced By"));
    verify(prescribedDrugDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setUnit(eq("Unit"));
    verify(prescribedDrugDetail).setVanID(isA(Integer.class));
    verify(prescribedDrugDetail).setVanSerialNo(isA(Long.class));
    verify(prescribedDrugDetail).setVehicalNo(eq("Vehical No"));
    verify(prescribedDrugDetail).setVisitCode(isA(Long.class));
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList17() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());
    PrescribedDrugDetail prescribedDrugDetail = mock(PrescribedDrugDetail.class);
    when(prescribedDrugDetail.getDose()).thenReturn("Dose");
    when(prescribedDrugDetail.getDuration()).thenReturn("Duration");
    when(prescribedDrugDetail.getFrequency()).thenReturn("SOS");
    when(prescribedDrugDetail.getUnit()).thenReturn("Unit");
    when(prescribedDrugDetail.getFormName()).thenReturn("Tablet");
    doNothing().when(prescribedDrugDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescribedDrugDetail).setDose(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setDrugName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugStrength(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugTradeOrBrandName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDuration(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFormName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFrequency(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setId(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setInstructions(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setQtyPrescribed(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setRelationToFood(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setRoute(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctCode(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctTerm(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setUnit(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVisitCode(Mockito.<Long>any());
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetail).getDose();
    verify(prescribedDrugDetail).getDuration();
    verify(prescribedDrugDetail, atLeast(1)).getFormName();
    verify(prescribedDrugDetail).getFrequency();
    verify(prescribedDrugDetail).getUnit();
    verify(prescribedDrugDetail).setBenVisitID(isA(Long.class));
    verify(prescribedDrugDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescribedDrugDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescribedDrugDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setDeleted(isA(Boolean.class));
    verify(prescribedDrugDetail).setDose(eq("Dose"));
    verify(prescribedDrugDetail).setDrugID(isA(Integer.class));
    verify(prescribedDrugDetail).setDrugName(eq("Drug Name"));
    verify(prescribedDrugDetail).setDrugStrength(eq("Drug Strength"));
    verify(prescribedDrugDetail).setDrugTradeOrBrandName(eq("Drug Trade Or Brand Name"));
    verify(prescribedDrugDetail).setDuration(eq("Duration"));
    verify(prescribedDrugDetail).setFormName(eq("Form Name"));
    verify(prescribedDrugDetail).setFrequency(eq("Frequency"));
    verify(prescribedDrugDetail).setId(isA(Long.class));
    verify(prescribedDrugDetail).setInstructions(eq("Instructions"));
    verify(prescribedDrugDetail).setLastModDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescribedDrugDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescribedDrugDetail).setPrescriptionID(isA(Long.class));
    verify(prescribedDrugDetail).setProcessed(eq("Processed"));
    verify(prescribedDrugDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescribedDrugDetail, atLeast(1)).setQtyPrescribed(Mockito.<Integer>any());
    verify(prescribedDrugDetail).setRelationToFood(eq("Relation To Food"));
    verify(prescribedDrugDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescribedDrugDetail).setRoute(eq("Route"));
    verify(prescribedDrugDetail).setSctCode(eq("Sct Code"));
    verify(prescribedDrugDetail).setSctTerm(eq("Sct Term"));
    verify(prescribedDrugDetail).setSyncedBy(eq("Synced By"));
    verify(prescribedDrugDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setUnit(eq("Unit"));
    verify(prescribedDrugDetail).setVanID(isA(Integer.class));
    verify(prescribedDrugDetail).setVanSerialNo(isA(Long.class));
    verify(prescribedDrugDetail).setVehicalNo(eq("Vehical No"));
    verify(prescribedDrugDetail).setVisitCode(isA(Long.class));
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenPrescribedDrugsList18() {
    // Arrange
    when(prescribedDrugDetailRepo.saveAll(Mockito.<Iterable<PrescribedDrugDetail>>any())).thenReturn(new ArrayList<>());
    PrescribedDrugDetail prescribedDrugDetail = mock(PrescribedDrugDetail.class);
    when(prescribedDrugDetail.getDose()).thenReturn("Dose");
    when(prescribedDrugDetail.getDuration()).thenReturn("Duration");
    when(prescribedDrugDetail.getFrequency()).thenReturn("Frequency");
    when(prescribedDrugDetail.getUnit()).thenReturn(null);
    when(prescribedDrugDetail.getFormName()).thenReturn("Tablet");
    doNothing().when(prescribedDrugDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescribedDrugDetail).setDose(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setDrugName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugStrength(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDrugTradeOrBrandName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setDuration(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFormName(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setFrequency(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setId(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setInstructions(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setQtyPrescribed(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setRelationToFood(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setRoute(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctCode(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSctTerm(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescribedDrugDetail).setUnit(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescribedDrugDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescribedDrugDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescribedDrugDetail).setVisitCode(Mockito.<Long>any());
    prescribedDrugDetail.setBenVisitID(1L);
    prescribedDrugDetail.setBeneficiaryRegID(1L);
    prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
    prescribedDrugDetail.setDeleted(true);
    prescribedDrugDetail.setDose("Dose");
    prescribedDrugDetail.setDrugID(1);
    prescribedDrugDetail.setDrugName("Drug Name");
    prescribedDrugDetail.setDrugStrength("Drug Strength");
    prescribedDrugDetail.setDrugTradeOrBrandName("Drug Trade Or Brand Name");
    prescribedDrugDetail.setDuration("Duration");
    prescribedDrugDetail.setFormName("Form Name");
    prescribedDrugDetail.setFrequency("Frequency");
    prescribedDrugDetail.setId(1L);
    prescribedDrugDetail.setInstructions("Instructions");
    prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
    prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescribedDrugDetail.setParkingPlaceID(1);
    prescribedDrugDetail.setPrescriptionID(1L);
    prescribedDrugDetail.setProcessed("Processed");
    prescribedDrugDetail.setProviderServiceMapID(1);
    prescribedDrugDetail.setQtyPrescribed(1);
    prescribedDrugDetail.setRelationToFood("Relation To Food");
    prescribedDrugDetail.setReservedForChange("Reserved For Change");
    prescribedDrugDetail.setRoute("Route");
    prescribedDrugDetail.setSctCode("Sct Code");
    prescribedDrugDetail.setSctTerm("Sct Term");
    prescribedDrugDetail.setSyncedBy("Synced By");
    prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
    prescribedDrugDetail.setUnit("Unit");
    prescribedDrugDetail.setVanID(1);
    prescribedDrugDetail.setVanSerialNo(1L);
    prescribedDrugDetail.setVehicalNo("Vehical No");
    prescribedDrugDetail.setVisitCode(1L);

    ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
    prescribedDrugDetailList.add(prescribedDrugDetail);

    // Act
    Integer actualSaveBenPrescribedDrugsListResult = commonNurseServiceImpl
        .saveBenPrescribedDrugsList(prescribedDrugDetailList);

    // Assert
    verify(prescribedDrugDetail).getDose();
    verify(prescribedDrugDetail).getDuration();
    verify(prescribedDrugDetail, atLeast(1)).getFormName();
    verify(prescribedDrugDetail).getFrequency();
    verify(prescribedDrugDetail).getUnit();
    verify(prescribedDrugDetail).setBenVisitID(isA(Long.class));
    verify(prescribedDrugDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescribedDrugDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescribedDrugDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setDeleted(isA(Boolean.class));
    verify(prescribedDrugDetail).setDose(eq("Dose"));
    verify(prescribedDrugDetail).setDrugID(isA(Integer.class));
    verify(prescribedDrugDetail).setDrugName(eq("Drug Name"));
    verify(prescribedDrugDetail).setDrugStrength(eq("Drug Strength"));
    verify(prescribedDrugDetail).setDrugTradeOrBrandName(eq("Drug Trade Or Brand Name"));
    verify(prescribedDrugDetail).setDuration(eq("Duration"));
    verify(prescribedDrugDetail).setFormName(eq("Form Name"));
    verify(prescribedDrugDetail).setFrequency(eq("Frequency"));
    verify(prescribedDrugDetail).setId(isA(Long.class));
    verify(prescribedDrugDetail).setInstructions(eq("Instructions"));
    verify(prescribedDrugDetail).setLastModDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescribedDrugDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescribedDrugDetail).setPrescriptionID(isA(Long.class));
    verify(prescribedDrugDetail).setProcessed(eq("Processed"));
    verify(prescribedDrugDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescribedDrugDetail, atLeast(1)).setQtyPrescribed(Mockito.<Integer>any());
    verify(prescribedDrugDetail).setRelationToFood(eq("Relation To Food"));
    verify(prescribedDrugDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescribedDrugDetail).setRoute(eq("Route"));
    verify(prescribedDrugDetail).setSctCode(eq("Sct Code"));
    verify(prescribedDrugDetail).setSctTerm(eq("Sct Term"));
    verify(prescribedDrugDetail).setSyncedBy(eq("Synced By"));
    verify(prescribedDrugDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescribedDrugDetail).setUnit(eq("Unit"));
    verify(prescribedDrugDetail).setVanID(isA(Integer.class));
    verify(prescribedDrugDetail).setVanSerialNo(isA(Long.class));
    verify(prescribedDrugDetail).setVehicalNo(eq("Vehical No"));
    verify(prescribedDrugDetail).setVisitCode(isA(Long.class));
    verify(prescribedDrugDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(0, actualSaveBenPrescribedDrugsListResult.intValue());
  }

  @Test
  void testSaveBenInvestigationDetails() {
    // Arrange
    PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);
    when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);
    WrapperBenInvestigationANC wrapperBenInvestigationANC = new WrapperBenInvestigationANC();

    // Act
    int actualSaveBenInvestigationDetailsResult = commonNurseServiceImpl
        .saveBenInvestigationDetails(wrapperBenInvestigationANC);

    // Assert
    verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
    assertEquals(1, actualSaveBenInvestigationDetailsResult);
    assertEquals(1L, wrapperBenInvestigationANC.getPrescriptionID().longValue());
  }

  @Test
  void testSaveBenInvestigationDetails2() {
    // Arrange
    PrescriptionDetail prescriptionDetail = mock(PrescriptionDetail.class);
    when(prescriptionDetail.getPrescriptionID()).thenReturn(-1L);
    doNothing().when(prescriptionDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBreastCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setCervicalCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setConfirmatoryDiagnosis(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvidedList(Mockito.<String[]>any());
    doNothing().when(prescriptionDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiabetesScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTCode(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTTerm(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setExternalInvestigation(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setHypertensionScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setInstruction(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setOralCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setPrescribedDrugs(Mockito.<ArrayList<PrescribedDrugDetail>>any());
    doNothing().when(prescriptionDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
    doNothing().when(prescriptionDetail).setRemarks(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setTreatmentsOnSideEffects(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVisitCode(Mockito.<Long>any());
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);
    when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);
    WrapperBenInvestigationANC wrapperBenInvestigationANC = new WrapperBenInvestigationANC();

    // Act
    int actualSaveBenInvestigationDetailsResult = commonNurseServiceImpl
        .saveBenInvestigationDetails(wrapperBenInvestigationANC);

    // Assert
    verify(prescriptionDetail).getPrescriptionID();
    verify(prescriptionDetail).setBenVisitID(isA(Long.class));
    verify(prescriptionDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescriptionDetail).setBreastCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setCervicalCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
    verify(prescriptionDetail).setCounsellingProvided(eq("Counselling Provided"));
    verify(prescriptionDetail).setCounsellingProvidedList(isA(String[].class));
    verify(prescriptionDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescriptionDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setDeleted(isA(Boolean.class));
    verify(prescriptionDetail).setDiabetesScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setDiagnosisProvided(eq("Diagnosis Provided"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTCode(eq("Diagnosis Provided SCTCode"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTTerm(eq("Diagnosis Provided SCTTerm"));
    verify(prescriptionDetail).setExternalInvestigation(eq("External Investigation"));
    verify(prescriptionDetail).setHypertensionScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setInstruction(eq("Instruction"));
    verify(prescriptionDetail).setLastModDate(isA(Timestamp.class));
    verify(prescriptionDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescriptionDetail).setOralCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescriptionDetail).setPrescribedDrugs(isA(ArrayList.class));
    verify(prescriptionDetail).setPrescriptionID(isA(Long.class));
    verify(prescriptionDetail).setProcessed(eq("Processed"));
    verify(prescriptionDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescriptionDetail).setProvisionalDiagnosisList(isA(ArrayList.class));
    verify(prescriptionDetail).setRemarks(eq("Remarks"));
    verify(prescriptionDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescriptionDetail).setSyncedBy(eq("Synced By"));
    verify(prescriptionDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setTreatmentsOnSideEffects(eq("Treatments On Side Effects"));
    verify(prescriptionDetail).setVanID(isA(Integer.class));
    verify(prescriptionDetail).setVanSerialNo(isA(Long.class));
    verify(prescriptionDetail).setVehicalNo(eq("Vehical No"));
    verify(prescriptionDetail).setVisitCode(isA(Long.class));
    verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
    assertNull(wrapperBenInvestigationANC.getPrescriptionID());
    assertEquals(1, actualSaveBenInvestigationDetailsResult);
  }

  @Test
  void testSaveBenInvestigationDetails3() {
    // Arrange
    PrescriptionDetail prescriptionDetail = mock(PrescriptionDetail.class);
    when(prescriptionDetail.getPrescriptionID()).thenReturn(1L);
    doNothing().when(prescriptionDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBreastCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setCervicalCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setConfirmatoryDiagnosis(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvidedList(Mockito.<String[]>any());
    doNothing().when(prescriptionDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiabetesScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTCode(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTTerm(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setExternalInvestigation(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setHypertensionScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setInstruction(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setOralCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setPrescribedDrugs(Mockito.<ArrayList<PrescribedDrugDetail>>any());
    doNothing().when(prescriptionDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
    doNothing().when(prescriptionDetail).setRemarks(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setTreatmentsOnSideEffects(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVisitCode(Mockito.<Long>any());
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);
    when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);
    WrapperBenInvestigationANC wrapperBenInvestigationANC = new WrapperBenInvestigationANC(1L, 1L, 1, 1L,
        "Jan 1, 2020 8:00am GMT+0100", new ArrayList<>());

    // Act
    int actualSaveBenInvestigationDetailsResult = commonNurseServiceImpl
        .saveBenInvestigationDetails(wrapperBenInvestigationANC);

    // Assert
    verify(prescriptionDetail, atLeast(1)).getPrescriptionID();
    verify(prescriptionDetail).setBenVisitID(isA(Long.class));
    verify(prescriptionDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescriptionDetail).setBreastCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setCervicalCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
    verify(prescriptionDetail).setCounsellingProvided(eq("Counselling Provided"));
    verify(prescriptionDetail).setCounsellingProvidedList(isA(String[].class));
    verify(prescriptionDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescriptionDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setDeleted(isA(Boolean.class));
    verify(prescriptionDetail).setDiabetesScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setDiagnosisProvided(eq("Diagnosis Provided"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTCode(eq("Diagnosis Provided SCTCode"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTTerm(eq("Diagnosis Provided SCTTerm"));
    verify(prescriptionDetail).setExternalInvestigation(eq("External Investigation"));
    verify(prescriptionDetail).setHypertensionScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setInstruction(eq("Instruction"));
    verify(prescriptionDetail).setLastModDate(isA(Timestamp.class));
    verify(prescriptionDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescriptionDetail).setOralCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescriptionDetail).setPrescribedDrugs(isA(ArrayList.class));
    verify(prescriptionDetail).setPrescriptionID(isA(Long.class));
    verify(prescriptionDetail).setProcessed(eq("Processed"));
    verify(prescriptionDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescriptionDetail).setProvisionalDiagnosisList(isA(ArrayList.class));
    verify(prescriptionDetail).setRemarks(eq("Remarks"));
    verify(prescriptionDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescriptionDetail).setSyncedBy(eq("Synced By"));
    verify(prescriptionDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setTreatmentsOnSideEffects(eq("Treatments On Side Effects"));
    verify(prescriptionDetail).setVanID(isA(Integer.class));
    verify(prescriptionDetail).setVanSerialNo(isA(Long.class));
    verify(prescriptionDetail).setVehicalNo(eq("Vehical No"));
    verify(prescriptionDetail).setVisitCode(isA(Long.class));
    verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
    assertEquals(1, actualSaveBenInvestigationDetailsResult);
    assertEquals(1L, wrapperBenInvestigationANC.getPrescriptionID().longValue());
  }

  @Test
  void testSaveBenInvestigationDetails4() {
    // Arrange
    PrescriptionDetail prescriptionDetail = mock(PrescriptionDetail.class);
    doNothing().when(prescriptionDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setBreastCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setCervicalCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setConfirmatoryDiagnosis(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCounsellingProvidedList(Mockito.<String[]>any());
    doNothing().when(prescriptionDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiabetesScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTCode(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTTerm(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setExternalInvestigation(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setHypertensionScreeningConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setInstruction(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setOralCancerConfirmed(Mockito.<Boolean>any());
    doNothing().when(prescriptionDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setPrescribedDrugs(Mockito.<ArrayList<PrescribedDrugDetail>>any());
    doNothing().when(prescriptionDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setProcessed(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
    doNothing().when(prescriptionDetail).setRemarks(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(prescriptionDetail).setTreatmentsOnSideEffects(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(prescriptionDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(prescriptionDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(prescriptionDetail).setVisitCode(Mockito.<Long>any());
    prescriptionDetail.setBenVisitID(1L);
    prescriptionDetail.setBeneficiaryRegID(1L);
    prescriptionDetail.setBreastCancerConfirmed(true);
    prescriptionDetail.setCervicalCancerConfirmed(true);
    prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
    prescriptionDetail.setCounsellingProvided("Counselling Provided");
    prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
    prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    prescriptionDetail.setCreatedDate(mock(Timestamp.class));
    prescriptionDetail.setDeleted(true);
    prescriptionDetail.setDiabetesScreeningConfirmed(true);
    prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
    prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
    prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
    prescriptionDetail.setExternalInvestigation("External Investigation");
    prescriptionDetail.setHypertensionScreeningConfirmed(true);
    prescriptionDetail.setInstruction("Instruction");
    prescriptionDetail.setLastModDate(mock(Timestamp.class));
    prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    prescriptionDetail.setOralCancerConfirmed(true);
    prescriptionDetail.setParkingPlaceID(1);
    prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
    prescriptionDetail.setPrescriptionID(1L);
    prescriptionDetail.setProcessed("Processed");
    prescriptionDetail.setProviderServiceMapID(1);
    prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
    prescriptionDetail.setRemarks("Remarks");
    prescriptionDetail.setReservedForChange("Reserved For Change");
    prescriptionDetail.setSyncedBy("Synced By");
    prescriptionDetail.setSyncedDate(mock(Timestamp.class));
    prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
    prescriptionDetail.setVanID(1);
    prescriptionDetail.setVanSerialNo(1L);
    prescriptionDetail.setVehicalNo("Vehical No");
    prescriptionDetail.setVisitCode(1L);

    // Act
    int actualSaveBenInvestigationDetailsResult = commonNurseServiceImpl.saveBenInvestigationDetails(null);

    // Assert
    verify(prescriptionDetail).setBenVisitID(isA(Long.class));
    verify(prescriptionDetail).setBeneficiaryRegID(isA(Long.class));
    verify(prescriptionDetail).setBreastCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setCervicalCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
    verify(prescriptionDetail).setCounsellingProvided(eq("Counselling Provided"));
    verify(prescriptionDetail).setCounsellingProvidedList(isA(String[].class));
    verify(prescriptionDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(prescriptionDetail).setCreatedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setDeleted(isA(Boolean.class));
    verify(prescriptionDetail).setDiabetesScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setDiagnosisProvided(eq("Diagnosis Provided"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTCode(eq("Diagnosis Provided SCTCode"));
    verify(prescriptionDetail).setDiagnosisProvided_SCTTerm(eq("Diagnosis Provided SCTTerm"));
    verify(prescriptionDetail).setExternalInvestigation(eq("External Investigation"));
    verify(prescriptionDetail).setHypertensionScreeningConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setInstruction(eq("Instruction"));
    verify(prescriptionDetail).setLastModDate(isA(Timestamp.class));
    verify(prescriptionDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(prescriptionDetail).setOralCancerConfirmed(isA(Boolean.class));
    verify(prescriptionDetail).setParkingPlaceID(isA(Integer.class));
    verify(prescriptionDetail).setPrescribedDrugs(isA(ArrayList.class));
    verify(prescriptionDetail).setPrescriptionID(isA(Long.class));
    verify(prescriptionDetail).setProcessed(eq("Processed"));
    verify(prescriptionDetail).setProviderServiceMapID(isA(Integer.class));
    verify(prescriptionDetail).setProvisionalDiagnosisList(isA(ArrayList.class));
    verify(prescriptionDetail).setRemarks(eq("Remarks"));
    verify(prescriptionDetail).setReservedForChange(eq("Reserved For Change"));
    verify(prescriptionDetail).setSyncedBy(eq("Synced By"));
    verify(prescriptionDetail).setSyncedDate(isA(Timestamp.class));
    verify(prescriptionDetail).setTreatmentsOnSideEffects(eq("Treatments On Side Effects"));
    verify(prescriptionDetail).setVanID(isA(Integer.class));
    verify(prescriptionDetail).setVanSerialNo(isA(Long.class));
    verify(prescriptionDetail).setVehicalNo(eq("Vehical No"));
    verify(prescriptionDetail).setVisitCode(isA(Long.class));
    assertEquals(0, actualSaveBenInvestigationDetailsResult);
  }

  @Test
  void testSaveBenInvestigation() {
    // Arrange, Act and Assert
    assertEquals(1L, commonNurseServiceImpl.saveBenInvestigation(new WrapperBenInvestigationANC()).longValue());
    assertEquals(1L,
        commonNurseServiceImpl
            .saveBenInvestigation(
                new WrapperBenInvestigationANC(1L, 1L, 1, 1L, "Jan 1, 2020 8:00am GMT+0100", new ArrayList<>()))
            .longValue());
  }

  @Test
  void testSaveBenInvestigation2() {
    // Arrange
    WrapperBenInvestigationANC wrapperBenInvestigationANC = mock(WrapperBenInvestigationANC.class);
    when(wrapperBenInvestigationANC.getLaboratoryList()).thenReturn(new ArrayList<>());

    // Act
    Long actualSaveBenInvestigationResult = commonNurseServiceImpl.saveBenInvestigation(wrapperBenInvestigationANC);

    // Assert
    verify(wrapperBenInvestigationANC).getLaboratoryList();
    assertEquals(1L, actualSaveBenInvestigationResult.longValue());
  }

  @Test
  void testSaveBenInvestigation3() {
    // Arrange
    when(labTestOrderDetailRepo.saveAll(Mockito.<Iterable<LabTestOrderDetail>>any())).thenReturn(new ArrayList<>());

    LabTestOrderDetail labTestOrderDetail = new LabTestOrderDetail();
    labTestOrderDetail.setBenVisitID(1L);
    labTestOrderDetail.setBeneficiaryRegID(1L);
    labTestOrderDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    labTestOrderDetail.setCreatedDate(mock(Timestamp.class));
    labTestOrderDetail.setDeleted(true);
    labTestOrderDetail.setLabTestOrderID(1L);
    labTestOrderDetail.setLastModDate(mock(Timestamp.class));
    labTestOrderDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    labTestOrderDetail.setParkingPlaceID(1);
    labTestOrderDetail.setPrescriptionID(1L);
    labTestOrderDetail.setProcedureID(1);
    labTestOrderDetail.setProcedureName("Procedure Name");
    labTestOrderDetail.setProcessed("Processed");
    labTestOrderDetail.setProviderServiceMapID(1);
    labTestOrderDetail.setReservedForChange("Reserved For Change");
    labTestOrderDetail.setSyncedBy("Synced By");
    labTestOrderDetail.setSyncedDate(mock(Timestamp.class));
    labTestOrderDetail.setTestingRequirements("Testing Requirements");
    labTestOrderDetail.setVanID(1);
    labTestOrderDetail.setVanSerialNo(1L);
    labTestOrderDetail.setVehicalNo("Vehical No");
    labTestOrderDetail.setVisitCode(1L);

    ArrayList<LabTestOrderDetail> labTestOrderDetailList = new ArrayList<>();
    labTestOrderDetailList.add(labTestOrderDetail);
    WrapperBenInvestigationANC wrapperBenInvestigationANC = mock(WrapperBenInvestigationANC.class);
    when(wrapperBenInvestigationANC.getParkingPlaceID()).thenReturn(1);
    when(wrapperBenInvestigationANC.getProviderServiceMapID()).thenReturn(1);
    when(wrapperBenInvestigationANC.getVanID()).thenReturn(1);
    when(wrapperBenInvestigationANC.getBenVisitID()).thenReturn(1L);
    when(wrapperBenInvestigationANC.getBeneficiaryRegID()).thenReturn(1L);
    when(wrapperBenInvestigationANC.getPrescriptionID()).thenReturn(1L);
    when(wrapperBenInvestigationANC.getVisitCode()).thenReturn(1L);
    when(wrapperBenInvestigationANC.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
    when(wrapperBenInvestigationANC.getLaboratoryList()).thenReturn(labTestOrderDetailList);

    // Act
    Long actualSaveBenInvestigationResult = commonNurseServiceImpl.saveBenInvestigation(wrapperBenInvestigationANC);

    // Assert
    verify(wrapperBenInvestigationANC).getBenVisitID();
    verify(wrapperBenInvestigationANC).getBeneficiaryRegID();
    verify(wrapperBenInvestigationANC).getCreatedBy();
    verify(wrapperBenInvestigationANC).getLaboratoryList();
    verify(wrapperBenInvestigationANC).getParkingPlaceID();
    verify(wrapperBenInvestigationANC).getPrescriptionID();
    verify(wrapperBenInvestigationANC).getProviderServiceMapID();
    verify(wrapperBenInvestigationANC).getVanID();
    verify(wrapperBenInvestigationANC).getVisitCode();
    verify(labTestOrderDetailRepo).saveAll(isA(Iterable.class));
    assertNull(actualSaveBenInvestigationResult);
  }

  @Test
  void testSaveBenInvestigation4() {
    // Arrange
    LabTestOrderDetail labTestOrderDetail = new LabTestOrderDetail();
    labTestOrderDetail.setBenVisitID(1L);
    labTestOrderDetail.setBeneficiaryRegID(1L);
    labTestOrderDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    labTestOrderDetail.setCreatedDate(mock(Timestamp.class));
    labTestOrderDetail.setDeleted(true);
    labTestOrderDetail.setLabTestOrderID(1L);
    labTestOrderDetail.setLastModDate(mock(Timestamp.class));
    labTestOrderDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    labTestOrderDetail.setParkingPlaceID(1);
    labTestOrderDetail.setPrescriptionID(1L);
    labTestOrderDetail.setProcedureID(1);
    labTestOrderDetail.setProcedureName("Procedure Name");
    labTestOrderDetail.setProcessed("Processed");
    labTestOrderDetail.setProviderServiceMapID(1);
    labTestOrderDetail.setReservedForChange("Reserved For Change");
    labTestOrderDetail.setSyncedBy("Synced By");
    labTestOrderDetail.setSyncedDate(mock(Timestamp.class));
    labTestOrderDetail.setTestingRequirements("Testing Requirements");
    labTestOrderDetail.setVanID(1);
    labTestOrderDetail.setVanSerialNo(1L);
    labTestOrderDetail.setVehicalNo("Vehical No");
    labTestOrderDetail.setVisitCode(1L);

    ArrayList<LabTestOrderDetail> labTestOrderDetailList = new ArrayList<>();
    labTestOrderDetailList.add(labTestOrderDetail);
    when(labTestOrderDetailRepo.saveAll(Mockito.<Iterable<LabTestOrderDetail>>any()))
        .thenReturn(labTestOrderDetailList);

    LabTestOrderDetail labTestOrderDetail2 = new LabTestOrderDetail();
    labTestOrderDetail2.setBenVisitID(1L);
    labTestOrderDetail2.setBeneficiaryRegID(1L);
    labTestOrderDetail2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    labTestOrderDetail2.setCreatedDate(mock(Timestamp.class));
    labTestOrderDetail2.setDeleted(true);
    labTestOrderDetail2.setLabTestOrderID(1L);
    labTestOrderDetail2.setLastModDate(mock(Timestamp.class));
    labTestOrderDetail2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    labTestOrderDetail2.setParkingPlaceID(1);
    labTestOrderDetail2.setPrescriptionID(1L);
    labTestOrderDetail2.setProcedureID(1);
    labTestOrderDetail2.setProcedureName("Procedure Name");
    labTestOrderDetail2.setProcessed("Processed");
    labTestOrderDetail2.setProviderServiceMapID(1);
    labTestOrderDetail2.setReservedForChange("Reserved For Change");
    labTestOrderDetail2.setSyncedBy("Synced By");
    labTestOrderDetail2.setSyncedDate(mock(Timestamp.class));
    labTestOrderDetail2.setTestingRequirements("Testing Requirements");
    labTestOrderDetail2.setVanID(1);
    labTestOrderDetail2.setVanSerialNo(1L);
    labTestOrderDetail2.setVehicalNo("Vehical No");
    labTestOrderDetail2.setVisitCode(1L);

    ArrayList<LabTestOrderDetail> labTestOrderDetailList2 = new ArrayList<>();
    labTestOrderDetailList2.add(labTestOrderDetail2);
    WrapperBenInvestigationANC wrapperBenInvestigationANC = mock(WrapperBenInvestigationANC.class);
    when(wrapperBenInvestigationANC.getParkingPlaceID()).thenReturn(1);
    when(wrapperBenInvestigationANC.getProviderServiceMapID()).thenReturn(1);
    when(wrapperBenInvestigationANC.getVanID()).thenReturn(1);
    when(wrapperBenInvestigationANC.getBenVisitID()).thenReturn(1L);
    when(wrapperBenInvestigationANC.getBeneficiaryRegID()).thenReturn(1L);
    when(wrapperBenInvestigationANC.getPrescriptionID()).thenReturn(1L);
    when(wrapperBenInvestigationANC.getVisitCode()).thenReturn(1L);
    when(wrapperBenInvestigationANC.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
    when(wrapperBenInvestigationANC.getLaboratoryList()).thenReturn(labTestOrderDetailList2);

    // Act
    Long actualSaveBenInvestigationResult = commonNurseServiceImpl.saveBenInvestigation(wrapperBenInvestigationANC);

    // Assert
    verify(wrapperBenInvestigationANC).getBenVisitID();
    verify(wrapperBenInvestigationANC).getBeneficiaryRegID();
    verify(wrapperBenInvestigationANC).getCreatedBy();
    verify(wrapperBenInvestigationANC).getLaboratoryList();
    verify(wrapperBenInvestigationANC).getParkingPlaceID();
    verify(wrapperBenInvestigationANC).getPrescriptionID();
    verify(wrapperBenInvestigationANC).getProviderServiceMapID();
    verify(wrapperBenInvestigationANC).getVanID();
    verify(wrapperBenInvestigationANC).getVisitCode();
    verify(labTestOrderDetailRepo).saveAll(isA(Iterable.class));
    assertEquals(1L, actualSaveBenInvestigationResult.longValue());
  }

  @Test
  void testSaveBenInvestigation5() {
    // Arrange
    when(labTestOrderDetailRepo.saveAll(Mockito.<Iterable<LabTestOrderDetail>>any())).thenReturn(new ArrayList<>());
    LabTestOrderDetail labTestOrderDetail = mock(LabTestOrderDetail.class);
    doNothing().when(labTestOrderDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(labTestOrderDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(labTestOrderDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(labTestOrderDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(labTestOrderDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(labTestOrderDetail).setLabTestOrderID(Mockito.<Long>any());
    doNothing().when(labTestOrderDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(labTestOrderDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(labTestOrderDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(labTestOrderDetail).setPrescriptionID(Mockito.<Long>any());
    doNothing().when(labTestOrderDetail).setProcedureID(Mockito.<Integer>any());
    doNothing().when(labTestOrderDetail).setProcedureName(Mockito.<String>any());
    doNothing().when(labTestOrderDetail).setProcessed(Mockito.<String>any());
    doNothing().when(labTestOrderDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(labTestOrderDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(labTestOrderDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(labTestOrderDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(labTestOrderDetail).setTestingRequirements(Mockito.<String>any());
    doNothing().when(labTestOrderDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(labTestOrderDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(labTestOrderDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(labTestOrderDetail).setVisitCode(Mockito.<Long>any());
    labTestOrderDetail.setBenVisitID(1L);
    labTestOrderDetail.setBeneficiaryRegID(1L);
    labTestOrderDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    labTestOrderDetail.setCreatedDate(mock(Timestamp.class));
    labTestOrderDetail.setDeleted(true);
    labTestOrderDetail.setLabTestOrderID(1L);
    labTestOrderDetail.setLastModDate(mock(Timestamp.class));
    labTestOrderDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    labTestOrderDetail.setParkingPlaceID(1);
    labTestOrderDetail.setPrescriptionID(1L);
    labTestOrderDetail.setProcedureID(1);
    labTestOrderDetail.setProcedureName("Procedure Name");
    labTestOrderDetail.setProcessed("Processed");
    labTestOrderDetail.setProviderServiceMapID(1);
    labTestOrderDetail.setReservedForChange("Reserved For Change");
    labTestOrderDetail.setSyncedBy("Synced By");
    labTestOrderDetail.setSyncedDate(mock(Timestamp.class));
    labTestOrderDetail.setTestingRequirements("Testing Requirements");
    labTestOrderDetail.setVanID(1);
    labTestOrderDetail.setVanSerialNo(1L);
    labTestOrderDetail.setVehicalNo("Vehical No");
    labTestOrderDetail.setVisitCode(1L);

    ArrayList<LabTestOrderDetail> labTestOrderDetailList = new ArrayList<>();
    labTestOrderDetailList.add(labTestOrderDetail);
    WrapperBenInvestigationANC wrapperBenInvestigationANC = mock(WrapperBenInvestigationANC.class);
    when(wrapperBenInvestigationANC.getParkingPlaceID()).thenReturn(1);
    when(wrapperBenInvestigationANC.getProviderServiceMapID()).thenReturn(1);
    when(wrapperBenInvestigationANC.getVanID()).thenReturn(1);
    when(wrapperBenInvestigationANC.getBenVisitID()).thenReturn(1L);
    when(wrapperBenInvestigationANC.getBeneficiaryRegID()).thenReturn(1L);
    when(wrapperBenInvestigationANC.getPrescriptionID()).thenReturn(1L);
    when(wrapperBenInvestigationANC.getVisitCode()).thenReturn(1L);
    when(wrapperBenInvestigationANC.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
    when(wrapperBenInvestigationANC.getLaboratoryList()).thenReturn(labTestOrderDetailList);

    // Act
    Long actualSaveBenInvestigationResult = commonNurseServiceImpl.saveBenInvestigation(wrapperBenInvestigationANC);

    // Assert
    verify(wrapperBenInvestigationANC).getBenVisitID();
    verify(wrapperBenInvestigationANC).getBeneficiaryRegID();
    verify(wrapperBenInvestigationANC).getCreatedBy();
    verify(wrapperBenInvestigationANC).getLaboratoryList();
    verify(wrapperBenInvestigationANC).getParkingPlaceID();
    verify(wrapperBenInvestigationANC).getPrescriptionID();
    verify(wrapperBenInvestigationANC).getProviderServiceMapID();
    verify(wrapperBenInvestigationANC).getVanID();
    verify(wrapperBenInvestigationANC).getVisitCode();
    verify(labTestOrderDetail, atLeast(1)).setBenVisitID(isA(Long.class));
    verify(labTestOrderDetail, atLeast(1)).setBeneficiaryRegID(isA(Long.class));
    verify(labTestOrderDetail, atLeast(1)).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(labTestOrderDetail).setCreatedDate(isA(Timestamp.class));
    verify(labTestOrderDetail).setDeleted(isA(Boolean.class));
    verify(labTestOrderDetail).setLabTestOrderID(isA(Long.class));
    verify(labTestOrderDetail).setLastModDate(isA(Timestamp.class));
    verify(labTestOrderDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(labTestOrderDetail, atLeast(1)).setParkingPlaceID(isA(Integer.class));
    verify(labTestOrderDetail, atLeast(1)).setPrescriptionID(isA(Long.class));
    verify(labTestOrderDetail).setProcedureID(isA(Integer.class));
    verify(labTestOrderDetail).setProcedureName(eq("Procedure Name"));
    verify(labTestOrderDetail).setProcessed(eq("Processed"));
    verify(labTestOrderDetail, atLeast(1)).setProviderServiceMapID(isA(Integer.class));
    verify(labTestOrderDetail).setReservedForChange(eq("Reserved For Change"));
    verify(labTestOrderDetail).setSyncedBy(eq("Synced By"));
    verify(labTestOrderDetail).setSyncedDate(isA(Timestamp.class));
    verify(labTestOrderDetail).setTestingRequirements(eq("Testing Requirements"));
    verify(labTestOrderDetail, atLeast(1)).setVanID(isA(Integer.class));
    verify(labTestOrderDetail).setVanSerialNo(isA(Long.class));
    verify(labTestOrderDetail).setVehicalNo(eq("Vehical No"));
    verify(labTestOrderDetail, atLeast(1)).setVisitCode(isA(Long.class));
    verify(labTestOrderDetailRepo).saveAll(isA(Iterable.class));
    assertNull(actualSaveBenInvestigationResult);
  }

  @Test
  void testGetNurseWorkList() {
    // Arrange
    when(reistrarRepoBenSearch.getNurseWorkList()).thenReturn(new ArrayList<>());

    // Act
    String actualNurseWorkList = commonNurseServiceImpl.getNurseWorkList();

    // Assert
    verify(reistrarRepoBenSearch).getNurseWorkList();
    assertEquals("[]", actualNurseWorkList);
  }

  @Test
  void testGetNurseWorkListNew() {
    // Arrange
    when(beneficiaryFlowStatusRepo.getNurseWorklistNew(Mockito.<Integer>any(), Mockito.<Integer>any(),
        Mockito.<Timestamp>any())).thenReturn(new ArrayList<>());

    // Act
    String actualNurseWorkListNew = commonNurseServiceImpl.getNurseWorkListNew(1, 1);

    // Assert
    verify(beneficiaryFlowStatusRepo).getNurseWorklistNew(isA(Integer.class), isA(Integer.class), isA(Timestamp.class));
    assertEquals("[]", actualNurseWorkListNew);
  }

  @Test
  void testGetNurseWorkListTcCurrentDate() {
    // Arrange
    when(beneficiaryFlowStatusRepo.getNurseWorklistCurrentDate(Mockito.<Integer>any(), Mockito.<Timestamp>any(),
        Mockito.<Integer>any())).thenReturn(new ArrayList<>());

    // Act
    String actualNurseWorkListTcCurrentDate = commonNurseServiceImpl.getNurseWorkListTcCurrentDate(1, 1);

    // Assert
    verify(beneficiaryFlowStatusRepo).getNurseWorklistCurrentDate(isA(Integer.class), isA(Timestamp.class),
        isA(Integer.class));
    assertEquals("[]", actualNurseWorkListTcCurrentDate);
  }

  @Test
  void testGetNurseWorkListTcFutureDate() {
    // Arrange
    when(beneficiaryFlowStatusRepo.getNurseWorklistFutureDate(Mockito.<Integer>any(), Mockito.<Integer>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualNurseWorkListTcFutureDate = commonNurseServiceImpl.getNurseWorkListTcFutureDate(1, 1);

    // Assert
    verify(beneficiaryFlowStatusRepo).getNurseWorklistFutureDate(isA(Integer.class), isA(Integer.class));
    assertEquals("[]", actualNurseWorkListTcFutureDate);
  }

  @Test
  void testGetLabWorkListNew() {
    // Arrange
    when(beneficiaryFlowStatusRepo.getLabWorklistNew(Mockito.<Integer>any(), Mockito.<Timestamp>any(),
        Mockito.<Integer>any())).thenReturn(new ArrayList<>());

    // Act
    String actualLabWorkListNew = commonNurseServiceImpl.getLabWorkListNew(1, 1);

    // Assert
    verify(beneficiaryFlowStatusRepo).getLabWorklistNew(isA(Integer.class), isA(Timestamp.class), isA(Integer.class));
    assertEquals("[]", actualLabWorkListNew);
  }

  @Test
  void testGetRadiologistWorkListNew() {
    // Arrange
    when(beneficiaryFlowStatusRepo.getRadiologistWorkListNew(Mockito.<Integer>any(), Mockito.<Timestamp>any(),
        Mockito.<Integer>any())).thenReturn(new ArrayList<>());

    // Act
    String actualRadiologistWorkListNew = commonNurseServiceImpl.getRadiologistWorkListNew(1, 1);

    // Assert
    verify(beneficiaryFlowStatusRepo).getRadiologistWorkListNew(isA(Integer.class), isA(Timestamp.class),
        isA(Integer.class));
    assertEquals("[]", actualRadiologistWorkListNew);
  }

  @Test
  void testGetOncologistWorkListNew() {
    // Arrange
    when(beneficiaryFlowStatusRepo.getOncologistWorkListNew(Mockito.<Integer>any(), Mockito.<Timestamp>any(),
        Mockito.<Integer>any())).thenReturn(new ArrayList<>());

    // Act
    String actualOncologistWorkListNew = commonNurseServiceImpl.getOncologistWorkListNew(1, 1);

    // Assert
    verify(beneficiaryFlowStatusRepo).getOncologistWorkListNew(isA(Integer.class), isA(Timestamp.class),
        isA(Integer.class));
    assertEquals("[]", actualOncologistWorkListNew);
  }

  @Test
  void testGetPharmaWorkListNew() {
    // Arrange
    when(beneficiaryFlowStatusRepo.getPharmaWorkListNew(Mockito.<Integer>any(), Mockito.<Timestamp>any(),
        Mockito.<Integer>any())).thenReturn(new ArrayList<>());

    // Act
    String actualPharmaWorkListNew = commonNurseServiceImpl.getPharmaWorkListNew(1, 1);

    // Assert
    verify(beneficiaryFlowStatusRepo).getPharmaWorkListNew(isA(Integer.class), isA(Timestamp.class),
        isA(Integer.class));
    assertEquals("[]", actualPharmaWorkListNew);
  }

  @Test
  void testSaveBenAdherenceDetails() throws IEMRException {
    // Arrange
    BenAdherence benAdherence = new BenAdherence();
    benAdherence.setBenVisitID(1L);
    benAdherence.setBeneficiaryRegID(1L);
    benAdherence.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benAdherence.setCreatedDate(mock(Timestamp.class));
    benAdherence.setDeleted(true);
    benAdherence.setDrugReason("Just cause");
    benAdherence.setID(1L);
    benAdherence.setLastModDate(mock(Timestamp.class));
    benAdherence.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benAdherence.setParkingPlaceID(1);
    benAdherence.setProcessed("Processed");
    benAdherence.setProgress("Progress");
    benAdherence.setProviderServiceMapID(1);
    benAdherence.setReferralReason("Just cause");
    benAdherence.setReservedForChange("Reserved For Change");
    benAdherence.setSyncedBy("Synced By");
    benAdherence.setSyncedDate(mock(Timestamp.class));
    benAdherence.setToDrugs(true);
    benAdherence.setToReferral(true);
    benAdherence.setVanSerialNo(1L);
    benAdherence.setVehicalNo("Vehical No");
    benAdherence.setVisitCode(1L);
    when(benAdherenceRepo.save(Mockito.<BenAdherence>any())).thenReturn(benAdherence);

    BenAdherence benAdherence2 = new BenAdherence();
    benAdherence2.setBenVisitID(1L);
    benAdherence2.setBeneficiaryRegID(1L);
    benAdherence2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benAdherence2.setCreatedDate(mock(Timestamp.class));
    benAdherence2.setDeleted(true);
    benAdherence2.setDrugReason("Just cause");
    benAdherence2.setID(1L);
    benAdherence2.setLastModDate(mock(Timestamp.class));
    benAdherence2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benAdherence2.setParkingPlaceID(1);
    benAdherence2.setProcessed("Processed");
    benAdherence2.setProgress("Progress");
    benAdherence2.setProviderServiceMapID(1);
    benAdherence2.setReferralReason("Just cause");
    benAdherence2.setReservedForChange("Reserved For Change");
    benAdherence2.setSyncedBy("Synced By");
    benAdherence2.setSyncedDate(mock(Timestamp.class));
    benAdherence2.setToDrugs(true);
    benAdherence2.setToReferral(true);
    benAdherence2.setVanSerialNo(1L);
    benAdherence2.setVehicalNo("Vehical No");
    benAdherence2.setVisitCode(1L);

    // Act
    int actualSaveBenAdherenceDetailsResult = commonNurseServiceImpl.saveBenAdherenceDetails(benAdherence2);

    // Assert
    verify(benAdherenceRepo).save(isA(BenAdherence.class));
    assertEquals(1, actualSaveBenAdherenceDetailsResult);
  }

  @Test
  void testSaveCdssDetails() throws IEMRException {
    // Arrange
    CDSS cdss = new CDSS();
    cdss.setAction("Action");
    cdss.setActionId(1);
    cdss.setActionIdPc(1);
    cdss.setActionPc("Action Pc");
    cdss.setAlgorithm("Algorithm");
    cdss.setAlgorithmPc("Algorithm Pc");
    cdss.setBenVisitID(1L);
    cdss.setBeneficiaryRegID(1L);
    cdss.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    cdss.setCreatedDate(mock(Timestamp.class));
    cdss.setDeleted(true);
    cdss.setDiseaseSummary("Disease Summary");
    cdss.setDiseaseSummaryID(1);
    cdss.setId(1L);
    cdss.setInformationGiven("Information Given");
    cdss.setLastModDate(mock(Timestamp.class));
    cdss.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    cdss.setParkingPlaceID(1);
    cdss.setPresentChiefComplaint("Present Chief Complaint");
    cdss.setPresentChiefComplaintID("Present Chief Complaint ID");
    cdss.setProviderServiceMapID(1);
    cdss.setRecommendedAction("Recommended Action");
    cdss.setRecommendedActionPc("Recommended Action Pc");
    cdss.setRemarks("Remarks");
    cdss.setRemarksPc("Remarks Pc");
    cdss.setReservedForChange("Reserved For Change");
    cdss.setSelectedDiagnosis("Selected Diagnosis");
    cdss.setSelectedDiagnosisID("Selected Diagnosis ID");
    cdss.setSyncedBy("Synced By");
    cdss.setSyncedDate(mock(Timestamp.class));
    cdss.setVanID(1);
    cdss.setVanSerialNo(1L);
    cdss.setVehicalNo("Vehical No");
    cdss.setVisitCode(1L);
    when(cDSSRepo.save(Mockito.<CDSS>any())).thenReturn(cdss);

    CDSS cdss2 = new CDSS();
    cdss2.setAction("Action");
    cdss2.setActionId(1);
    cdss2.setActionIdPc(1);
    cdss2.setActionPc("Action Pc");
    cdss2.setAlgorithm("Algorithm");
    cdss2.setAlgorithmPc("Algorithm Pc");
    cdss2.setBenVisitID(1L);
    cdss2.setBeneficiaryRegID(1L);
    cdss2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    cdss2.setCreatedDate(mock(Timestamp.class));
    cdss2.setDeleted(true);
    cdss2.setDiseaseSummary("Disease Summary");
    cdss2.setDiseaseSummaryID(1);
    cdss2.setId(1L);
    cdss2.setInformationGiven("Information Given");
    cdss2.setLastModDate(mock(Timestamp.class));
    cdss2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    cdss2.setParkingPlaceID(1);
    cdss2.setPresentChiefComplaint("Present Chief Complaint");
    cdss2.setPresentChiefComplaintID("Present Chief Complaint ID");
    cdss2.setProviderServiceMapID(1);
    cdss2.setRecommendedAction("Recommended Action");
    cdss2.setRecommendedActionPc("Recommended Action Pc");
    cdss2.setRemarks("Remarks");
    cdss2.setRemarksPc("Remarks Pc");
    cdss2.setReservedForChange("Reserved For Change");
    cdss2.setSelectedDiagnosis("Selected Diagnosis");
    cdss2.setSelectedDiagnosisID("Selected Diagnosis ID");
    cdss2.setSyncedBy("Synced By");
    cdss2.setSyncedDate(mock(Timestamp.class));
    cdss2.setVanID(1);
    cdss2.setVanSerialNo(1L);
    cdss2.setVehicalNo("Vehical No");
    cdss2.setVisitCode(1L);

    // Act
    Long actualSaveCdssDetailsResult = commonNurseServiceImpl.saveCdssDetails(cdss2);

    // Assert
    verify(cDSSRepo).save(isA(CDSS.class));
    assertEquals(1L, actualSaveCdssDetailsResult.longValue());
  }

  @Test
  void testSaveCdssDetails2() throws IEMRException {
    // Arrange
    CDSS cdss = mock(CDSS.class);
    when(cdss.getId()).thenReturn(-1L);
    doNothing().when(cdss).setAction(Mockito.<String>any());
    doNothing().when(cdss).setActionId(Mockito.<Integer>any());
    doNothing().when(cdss).setActionIdPc(Mockito.<Integer>any());
    doNothing().when(cdss).setActionPc(Mockito.<String>any());
    doNothing().when(cdss).setAlgorithm(Mockito.<String>any());
    doNothing().when(cdss).setAlgorithmPc(Mockito.<String>any());
    doNothing().when(cdss).setBenVisitID(Mockito.<Long>any());
    doNothing().when(cdss).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(cdss).setCreatedBy(Mockito.<String>any());
    doNothing().when(cdss).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(cdss).setDeleted(Mockito.<Boolean>any());
    doNothing().when(cdss).setDiseaseSummary(Mockito.<String>any());
    doNothing().when(cdss).setDiseaseSummaryID(Mockito.<Integer>any());
    doNothing().when(cdss).setId(Mockito.<Long>any());
    doNothing().when(cdss).setInformationGiven(Mockito.<String>any());
    doNothing().when(cdss).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(cdss).setModifiedBy(Mockito.<String>any());
    doNothing().when(cdss).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(cdss).setPresentChiefComplaint(Mockito.<String>any());
    doNothing().when(cdss).setPresentChiefComplaintID(Mockito.<String>any());
    doNothing().when(cdss).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(cdss).setRecommendedAction(Mockito.<String>any());
    doNothing().when(cdss).setRecommendedActionPc(Mockito.<String>any());
    doNothing().when(cdss).setRemarks(Mockito.<String>any());
    doNothing().when(cdss).setRemarksPc(Mockito.<String>any());
    doNothing().when(cdss).setReservedForChange(Mockito.<String>any());
    doNothing().when(cdss).setSelectedDiagnosis(Mockito.<String>any());
    doNothing().when(cdss).setSelectedDiagnosisID(Mockito.<String>any());
    doNothing().when(cdss).setSyncedBy(Mockito.<String>any());
    doNothing().when(cdss).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(cdss).setVanID(Mockito.<Integer>any());
    doNothing().when(cdss).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(cdss).setVehicalNo(Mockito.<String>any());
    doNothing().when(cdss).setVisitCode(Mockito.<Long>any());
    cdss.setAction("Action");
    cdss.setActionId(1);
    cdss.setActionIdPc(1);
    cdss.setActionPc("Action Pc");
    cdss.setAlgorithm("Algorithm");
    cdss.setAlgorithmPc("Algorithm Pc");
    cdss.setBenVisitID(1L);
    cdss.setBeneficiaryRegID(1L);
    cdss.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    cdss.setCreatedDate(mock(Timestamp.class));
    cdss.setDeleted(true);
    cdss.setDiseaseSummary("Disease Summary");
    cdss.setDiseaseSummaryID(1);
    cdss.setId(1L);
    cdss.setInformationGiven("Information Given");
    cdss.setLastModDate(mock(Timestamp.class));
    cdss.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    cdss.setParkingPlaceID(1);
    cdss.setPresentChiefComplaint("Present Chief Complaint");
    cdss.setPresentChiefComplaintID("Present Chief Complaint ID");
    cdss.setProviderServiceMapID(1);
    cdss.setRecommendedAction("Recommended Action");
    cdss.setRecommendedActionPc("Recommended Action Pc");
    cdss.setRemarks("Remarks");
    cdss.setRemarksPc("Remarks Pc");
    cdss.setReservedForChange("Reserved For Change");
    cdss.setSelectedDiagnosis("Selected Diagnosis");
    cdss.setSelectedDiagnosisID("Selected Diagnosis ID");
    cdss.setSyncedBy("Synced By");
    cdss.setSyncedDate(mock(Timestamp.class));
    cdss.setVanID(1);
    cdss.setVanSerialNo(1L);
    cdss.setVehicalNo("Vehical No");
    cdss.setVisitCode(1L);
    when(cDSSRepo.save(Mockito.<CDSS>any())).thenReturn(cdss);

    CDSS cdss2 = new CDSS();
    cdss2.setAction("Action");
    cdss2.setActionId(1);
    cdss2.setActionIdPc(1);
    cdss2.setActionPc("Action Pc");
    cdss2.setAlgorithm("Algorithm");
    cdss2.setAlgorithmPc("Algorithm Pc");
    cdss2.setBenVisitID(1L);
    cdss2.setBeneficiaryRegID(1L);
    cdss2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    cdss2.setCreatedDate(mock(Timestamp.class));
    cdss2.setDeleted(true);
    cdss2.setDiseaseSummary("Disease Summary");
    cdss2.setDiseaseSummaryID(1);
    cdss2.setId(1L);
    cdss2.setInformationGiven("Information Given");
    cdss2.setLastModDate(mock(Timestamp.class));
    cdss2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    cdss2.setParkingPlaceID(1);
    cdss2.setPresentChiefComplaint("Present Chief Complaint");
    cdss2.setPresentChiefComplaintID("Present Chief Complaint ID");
    cdss2.setProviderServiceMapID(1);
    cdss2.setRecommendedAction("Recommended Action");
    cdss2.setRecommendedActionPc("Recommended Action Pc");
    cdss2.setRemarks("Remarks");
    cdss2.setRemarksPc("Remarks Pc");
    cdss2.setReservedForChange("Reserved For Change");
    cdss2.setSelectedDiagnosis("Selected Diagnosis");
    cdss2.setSelectedDiagnosisID("Selected Diagnosis ID");
    cdss2.setSyncedBy("Synced By");
    cdss2.setSyncedDate(mock(Timestamp.class));
    cdss2.setVanID(1);
    cdss2.setVanSerialNo(1L);
    cdss2.setVehicalNo("Vehical No");
    cdss2.setVisitCode(1L);

    // Act and Assert
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.saveCdssDetails(cdss2));
    verify(cdss).getId();
    verify(cdss).setAction(eq("Action"));
    verify(cdss).setActionId(isA(Integer.class));
    verify(cdss).setActionIdPc(isA(Integer.class));
    verify(cdss).setActionPc(eq("Action Pc"));
    verify(cdss).setAlgorithm(eq("Algorithm"));
    verify(cdss).setAlgorithmPc(eq("Algorithm Pc"));
    verify(cdss).setBenVisitID(isA(Long.class));
    verify(cdss).setBeneficiaryRegID(isA(Long.class));
    verify(cdss).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(cdss).setCreatedDate(isA(Timestamp.class));
    verify(cdss).setDeleted(isA(Boolean.class));
    verify(cdss).setDiseaseSummary(eq("Disease Summary"));
    verify(cdss).setDiseaseSummaryID(isA(Integer.class));
    verify(cdss).setId(isA(Long.class));
    verify(cdss).setInformationGiven(eq("Information Given"));
    verify(cdss).setLastModDate(isA(Timestamp.class));
    verify(cdss).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(cdss).setParkingPlaceID(isA(Integer.class));
    verify(cdss).setPresentChiefComplaint(eq("Present Chief Complaint"));
    verify(cdss).setPresentChiefComplaintID(eq("Present Chief Complaint ID"));
    verify(cdss).setProviderServiceMapID(isA(Integer.class));
    verify(cdss).setRecommendedAction(eq("Recommended Action"));
    verify(cdss).setRecommendedActionPc(eq("Recommended Action Pc"));
    verify(cdss).setRemarks(eq("Remarks"));
    verify(cdss).setRemarksPc(eq("Remarks Pc"));
    verify(cdss).setReservedForChange(eq("Reserved For Change"));
    verify(cdss).setSelectedDiagnosis(eq("Selected Diagnosis"));
    verify(cdss).setSelectedDiagnosisID(eq("Selected Diagnosis ID"));
    verify(cdss).setSyncedBy(eq("Synced By"));
    verify(cdss).setSyncedDate(isA(Timestamp.class));
    verify(cdss).setVanID(isA(Integer.class));
    verify(cdss).setVanSerialNo(isA(Long.class));
    verify(cdss).setVehicalNo(eq("Vehical No"));
    verify(cdss).setVisitCode(isA(Long.class));
    verify(cDSSRepo).save(isA(CDSS.class));
  }

  @Test
  void testSaveChildDevelopmentHistory() throws IEMRException {
    // Arrange
    BenChildDevelopmentHistory benChildDevelopmentHistory = new BenChildDevelopmentHistory();
    benChildDevelopmentHistory.setBenVisitID(1L);
    benChildDevelopmentHistory.setBeneficiaryRegID(1L);
    benChildDevelopmentHistory.setCaptureDate(mock(Date.class));
    benChildDevelopmentHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benChildDevelopmentHistory.setCreatedDate(mock(Timestamp.class));
    benChildDevelopmentHistory.setDeleted(true);
    benChildDevelopmentHistory.setDevelopmentProblem("Development Problem");
    benChildDevelopmentHistory.setDevelopmentProblems(new ArrayList<>());
    benChildDevelopmentHistory.setFineMotorMilestone("Fine Motor Milestone");
    benChildDevelopmentHistory.setFineMotorMilestones(new ArrayList<>());
    benChildDevelopmentHistory.setGrossMotorMilestone("Gross Motor Milestone");
    benChildDevelopmentHistory.setGrossMotorMilestones(new ArrayList<>());
    benChildDevelopmentHistory.setID(1L);
    benChildDevelopmentHistory.setIsFineMotorMilestones(true);
    benChildDevelopmentHistory.setIsGrossMotorMilestones(true);
    benChildDevelopmentHistory.setIsLanguageMilestones(true);
    benChildDevelopmentHistory.setIsSocialMilestones(true);
    benChildDevelopmentHistory.setLanguageMilestone("en");
    benChildDevelopmentHistory.setLanguageMilestones(new ArrayList<>());
    benChildDevelopmentHistory.setLastModDate(mock(Timestamp.class));
    benChildDevelopmentHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benChildDevelopmentHistory.setParkingPlaceID(1);
    benChildDevelopmentHistory.setProcessed("Processed");
    benChildDevelopmentHistory.setProviderServiceMapID(1);
    benChildDevelopmentHistory.setReservedForChange("Reserved For Change");
    benChildDevelopmentHistory.setSocialMilestone("Social Milestone");
    benChildDevelopmentHistory.setSocialMilestones(new ArrayList<>());
    benChildDevelopmentHistory.setSyncedBy("Synced By");
    benChildDevelopmentHistory.setSyncedDate(mock(Timestamp.class));
    benChildDevelopmentHistory.setVanID(1);
    benChildDevelopmentHistory.setVanSerialNo(1L);
    benChildDevelopmentHistory.setVehicalNo("Vehical No");
    benChildDevelopmentHistory.setVisitCode(1L);
    when(benChildDevelopmentHistoryRepo.save(Mockito.<BenChildDevelopmentHistory>any()))
        .thenReturn(benChildDevelopmentHistory);

    BenChildDevelopmentHistory benChildDevelopmentHistory2 = new BenChildDevelopmentHistory();
    benChildDevelopmentHistory2.setBenVisitID(1L);
    benChildDevelopmentHistory2.setBeneficiaryRegID(1L);
    benChildDevelopmentHistory2.setCaptureDate(mock(Date.class));
    benChildDevelopmentHistory2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benChildDevelopmentHistory2.setCreatedDate(mock(Timestamp.class));
    benChildDevelopmentHistory2.setDeleted(true);
    benChildDevelopmentHistory2.setDevelopmentProblem("Development Problem");
    benChildDevelopmentHistory2.setDevelopmentProblems(new ArrayList<>());
    benChildDevelopmentHistory2.setFineMotorMilestone("Fine Motor Milestone");
    benChildDevelopmentHistory2.setFineMotorMilestones(new ArrayList<>());
    benChildDevelopmentHistory2.setGrossMotorMilestone("Gross Motor Milestone");
    benChildDevelopmentHistory2.setGrossMotorMilestones(new ArrayList<>());
    benChildDevelopmentHistory2.setID(1L);
    benChildDevelopmentHistory2.setIsFineMotorMilestones(true);
    benChildDevelopmentHistory2.setIsGrossMotorMilestones(true);
    benChildDevelopmentHistory2.setIsLanguageMilestones(true);
    benChildDevelopmentHistory2.setIsSocialMilestones(true);
    benChildDevelopmentHistory2.setLanguageMilestone("en");
    benChildDevelopmentHistory2.setLanguageMilestones(new ArrayList<>());
    benChildDevelopmentHistory2.setLastModDate(mock(Timestamp.class));
    benChildDevelopmentHistory2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benChildDevelopmentHistory2.setParkingPlaceID(1);
    benChildDevelopmentHistory2.setProcessed("Processed");
    benChildDevelopmentHistory2.setProviderServiceMapID(1);
    benChildDevelopmentHistory2.setReservedForChange("Reserved For Change");
    benChildDevelopmentHistory2.setSocialMilestone("Social Milestone");
    benChildDevelopmentHistory2.setSocialMilestones(new ArrayList<>());
    benChildDevelopmentHistory2.setSyncedBy("Synced By");
    benChildDevelopmentHistory2.setSyncedDate(mock(Timestamp.class));
    benChildDevelopmentHistory2.setVanID(1);
    benChildDevelopmentHistory2.setVanSerialNo(1L);
    benChildDevelopmentHistory2.setVehicalNo("Vehical No");
    benChildDevelopmentHistory2.setVisitCode(1L);

    // Act
    Long actualSaveChildDevelopmentHistoryResult = commonNurseServiceImpl
        .saveChildDevelopmentHistory(benChildDevelopmentHistory2);

    // Assert
    verify(benChildDevelopmentHistoryRepo).save(isA(BenChildDevelopmentHistory.class));
    assertEquals(1L, actualSaveChildDevelopmentHistoryResult.longValue());
  }

  @Test
  void testSaveChildDevelopmentHistory2() throws IEMRException {
    // Arrange
    BenChildDevelopmentHistory benChildDevelopmentHistory = mock(BenChildDevelopmentHistory.class);
    when(benChildDevelopmentHistory.getID()).thenReturn(-1L);
    doNothing().when(benChildDevelopmentHistory).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benChildDevelopmentHistory).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benChildDevelopmentHistory).setCaptureDate(Mockito.<Date>any());
    doNothing().when(benChildDevelopmentHistory).setCreatedBy(Mockito.<String>any());
    doNothing().when(benChildDevelopmentHistory).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benChildDevelopmentHistory).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benChildDevelopmentHistory).setDevelopmentProblem(Mockito.<String>any());
    doNothing().when(benChildDevelopmentHistory).setDevelopmentProblems(Mockito.<List<String>>any());
    doNothing().when(benChildDevelopmentHistory).setFineMotorMilestone(Mockito.<String>any());
    doNothing().when(benChildDevelopmentHistory).setFineMotorMilestones(Mockito.<List<String>>any());
    doNothing().when(benChildDevelopmentHistory).setGrossMotorMilestone(Mockito.<String>any());
    doNothing().when(benChildDevelopmentHistory).setGrossMotorMilestones(Mockito.<List<String>>any());
    doNothing().when(benChildDevelopmentHistory).setID(Mockito.<Long>any());
    doNothing().when(benChildDevelopmentHistory).setIsFineMotorMilestones(Mockito.<Boolean>any());
    doNothing().when(benChildDevelopmentHistory).setIsGrossMotorMilestones(Mockito.<Boolean>any());
    doNothing().when(benChildDevelopmentHistory).setIsLanguageMilestones(Mockito.<Boolean>any());
    doNothing().when(benChildDevelopmentHistory).setIsSocialMilestones(Mockito.<Boolean>any());
    doNothing().when(benChildDevelopmentHistory).setLanguageMilestone(Mockito.<String>any());
    doNothing().when(benChildDevelopmentHistory).setLanguageMilestones(Mockito.<List<String>>any());
    doNothing().when(benChildDevelopmentHistory).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benChildDevelopmentHistory).setModifiedBy(Mockito.<String>any());
    doNothing().when(benChildDevelopmentHistory).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benChildDevelopmentHistory).setProcessed(Mockito.<String>any());
    doNothing().when(benChildDevelopmentHistory).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benChildDevelopmentHistory).setReservedForChange(Mockito.<String>any());
    doNothing().when(benChildDevelopmentHistory).setSocialMilestone(Mockito.<String>any());
    doNothing().when(benChildDevelopmentHistory).setSocialMilestones(Mockito.<List<String>>any());
    doNothing().when(benChildDevelopmentHistory).setSyncedBy(Mockito.<String>any());
    doNothing().when(benChildDevelopmentHistory).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benChildDevelopmentHistory).setVanID(Mockito.<Integer>any());
    doNothing().when(benChildDevelopmentHistory).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benChildDevelopmentHistory).setVehicalNo(Mockito.<String>any());
    doNothing().when(benChildDevelopmentHistory).setVisitCode(Mockito.<Long>any());
    benChildDevelopmentHistory.setBenVisitID(1L);
    benChildDevelopmentHistory.setBeneficiaryRegID(1L);
    benChildDevelopmentHistory.setCaptureDate(mock(Date.class));
    benChildDevelopmentHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benChildDevelopmentHistory.setCreatedDate(mock(Timestamp.class));
    benChildDevelopmentHistory.setDeleted(true);
    benChildDevelopmentHistory.setDevelopmentProblem("Development Problem");
    benChildDevelopmentHistory.setDevelopmentProblems(new ArrayList<>());
    benChildDevelopmentHistory.setFineMotorMilestone("Fine Motor Milestone");
    benChildDevelopmentHistory.setFineMotorMilestones(new ArrayList<>());
    benChildDevelopmentHistory.setGrossMotorMilestone("Gross Motor Milestone");
    benChildDevelopmentHistory.setGrossMotorMilestones(new ArrayList<>());
    benChildDevelopmentHistory.setID(1L);
    benChildDevelopmentHistory.setIsFineMotorMilestones(true);
    benChildDevelopmentHistory.setIsGrossMotorMilestones(true);
    benChildDevelopmentHistory.setIsLanguageMilestones(true);
    benChildDevelopmentHistory.setIsSocialMilestones(true);
    benChildDevelopmentHistory.setLanguageMilestone("en");
    benChildDevelopmentHistory.setLanguageMilestones(new ArrayList<>());
    benChildDevelopmentHistory.setLastModDate(mock(Timestamp.class));
    benChildDevelopmentHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benChildDevelopmentHistory.setParkingPlaceID(1);
    benChildDevelopmentHistory.setProcessed("Processed");
    benChildDevelopmentHistory.setProviderServiceMapID(1);
    benChildDevelopmentHistory.setReservedForChange("Reserved For Change");
    benChildDevelopmentHistory.setSocialMilestone("Social Milestone");
    benChildDevelopmentHistory.setSocialMilestones(new ArrayList<>());
    benChildDevelopmentHistory.setSyncedBy("Synced By");
    benChildDevelopmentHistory.setSyncedDate(mock(Timestamp.class));
    benChildDevelopmentHistory.setVanID(1);
    benChildDevelopmentHistory.setVanSerialNo(1L);
    benChildDevelopmentHistory.setVehicalNo("Vehical No");
    benChildDevelopmentHistory.setVisitCode(1L);
    when(benChildDevelopmentHistoryRepo.save(Mockito.<BenChildDevelopmentHistory>any()))
        .thenReturn(benChildDevelopmentHistory);

    BenChildDevelopmentHistory benChildDevelopmentHistory2 = new BenChildDevelopmentHistory();
    benChildDevelopmentHistory2.setBenVisitID(1L);
    benChildDevelopmentHistory2.setBeneficiaryRegID(1L);
    benChildDevelopmentHistory2.setCaptureDate(mock(Date.class));
    benChildDevelopmentHistory2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benChildDevelopmentHistory2.setCreatedDate(mock(Timestamp.class));
    benChildDevelopmentHistory2.setDeleted(true);
    benChildDevelopmentHistory2.setDevelopmentProblem("Development Problem");
    benChildDevelopmentHistory2.setDevelopmentProblems(new ArrayList<>());
    benChildDevelopmentHistory2.setFineMotorMilestone("Fine Motor Milestone");
    benChildDevelopmentHistory2.setFineMotorMilestones(new ArrayList<>());
    benChildDevelopmentHistory2.setGrossMotorMilestone("Gross Motor Milestone");
    benChildDevelopmentHistory2.setGrossMotorMilestones(new ArrayList<>());
    benChildDevelopmentHistory2.setID(1L);
    benChildDevelopmentHistory2.setIsFineMotorMilestones(true);
    benChildDevelopmentHistory2.setIsGrossMotorMilestones(true);
    benChildDevelopmentHistory2.setIsLanguageMilestones(true);
    benChildDevelopmentHistory2.setIsSocialMilestones(true);
    benChildDevelopmentHistory2.setLanguageMilestone("en");
    benChildDevelopmentHistory2.setLanguageMilestones(new ArrayList<>());
    benChildDevelopmentHistory2.setLastModDate(mock(Timestamp.class));
    benChildDevelopmentHistory2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benChildDevelopmentHistory2.setParkingPlaceID(1);
    benChildDevelopmentHistory2.setProcessed("Processed");
    benChildDevelopmentHistory2.setProviderServiceMapID(1);
    benChildDevelopmentHistory2.setReservedForChange("Reserved For Change");
    benChildDevelopmentHistory2.setSocialMilestone("Social Milestone");
    benChildDevelopmentHistory2.setSocialMilestones(new ArrayList<>());
    benChildDevelopmentHistory2.setSyncedBy("Synced By");
    benChildDevelopmentHistory2.setSyncedDate(mock(Timestamp.class));
    benChildDevelopmentHistory2.setVanID(1);
    benChildDevelopmentHistory2.setVanSerialNo(1L);
    benChildDevelopmentHistory2.setVehicalNo("Vehical No");
    benChildDevelopmentHistory2.setVisitCode(1L);

    // Act
    Long actualSaveChildDevelopmentHistoryResult = commonNurseServiceImpl
        .saveChildDevelopmentHistory(benChildDevelopmentHistory2);

    // Assert
    verify(benChildDevelopmentHistory).getID();
    verify(benChildDevelopmentHistory).setBenVisitID(isA(Long.class));
    verify(benChildDevelopmentHistory).setBeneficiaryRegID(isA(Long.class));
    verify(benChildDevelopmentHistory).setCaptureDate(isA(Date.class));
    verify(benChildDevelopmentHistory).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benChildDevelopmentHistory).setCreatedDate(isA(Timestamp.class));
    verify(benChildDevelopmentHistory).setDeleted(isA(Boolean.class));
    verify(benChildDevelopmentHistory).setDevelopmentProblem(eq("Development Problem"));
    verify(benChildDevelopmentHistory).setDevelopmentProblems(isA(List.class));
    verify(benChildDevelopmentHistory).setFineMotorMilestone(eq("Fine Motor Milestone"));
    verify(benChildDevelopmentHistory).setFineMotorMilestones(isA(List.class));
    verify(benChildDevelopmentHistory).setGrossMotorMilestone(eq("Gross Motor Milestone"));
    verify(benChildDevelopmentHistory).setGrossMotorMilestones(isA(List.class));
    verify(benChildDevelopmentHistory).setID(isA(Long.class));
    verify(benChildDevelopmentHistory).setIsFineMotorMilestones(isA(Boolean.class));
    verify(benChildDevelopmentHistory).setIsGrossMotorMilestones(isA(Boolean.class));
    verify(benChildDevelopmentHistory).setIsLanguageMilestones(isA(Boolean.class));
    verify(benChildDevelopmentHistory).setIsSocialMilestones(isA(Boolean.class));
    verify(benChildDevelopmentHistory).setLanguageMilestone(eq("en"));
    verify(benChildDevelopmentHistory).setLanguageMilestones(isA(List.class));
    verify(benChildDevelopmentHistory).setLastModDate(isA(Timestamp.class));
    verify(benChildDevelopmentHistory).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benChildDevelopmentHistory).setParkingPlaceID(isA(Integer.class));
    verify(benChildDevelopmentHistory).setProcessed(eq("Processed"));
    verify(benChildDevelopmentHistory).setProviderServiceMapID(isA(Integer.class));
    verify(benChildDevelopmentHistory).setReservedForChange(eq("Reserved For Change"));
    verify(benChildDevelopmentHistory).setSocialMilestone(eq("Social Milestone"));
    verify(benChildDevelopmentHistory).setSocialMilestones(isA(List.class));
    verify(benChildDevelopmentHistory).setSyncedBy(eq("Synced By"));
    verify(benChildDevelopmentHistory).setSyncedDate(isA(Timestamp.class));
    verify(benChildDevelopmentHistory).setVanID(isA(Integer.class));
    verify(benChildDevelopmentHistory).setVanSerialNo(isA(Long.class));
    verify(benChildDevelopmentHistory).setVehicalNo(eq("Vehical No"));
    verify(benChildDevelopmentHistory).setVisitCode(isA(Long.class));
    verify(benChildDevelopmentHistoryRepo).save(isA(BenChildDevelopmentHistory.class));
    assertNull(actualSaveChildDevelopmentHistoryResult);
  }

  @Test
  void testSaveChildFeedingHistory() {
    // Arrange
    ChildFeedingDetails childFeedingDetails = new ChildFeedingDetails();
    childFeedingDetails.setBenMotherID(1L);
    childFeedingDetails.setBenVisitID(1L);
    childFeedingDetails.setBeneficiaryRegID(1L);
    childFeedingDetails.setCaptureDate(mock(Date.class));
    childFeedingDetails.setChildID(1L);
    childFeedingDetails.setCompFeedStartAge("Comp Feed Start Age");
    childFeedingDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    childFeedingDetails.setCreatedDate(mock(Timestamp.class));
    childFeedingDetails.setDeleted(true);
    childFeedingDetails.setFoodIntoleranceStatus("Food Intolerance Status");
    childFeedingDetails.setID(1L);
    childFeedingDetails.setLastModDate(mock(Timestamp.class));
    childFeedingDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    childFeedingDetails.setNoOfCompFeedPerDay("No Of Comp Feed Per Day");
    childFeedingDetails.setOtherFoodIntolerance("Other Food Intolerance");
    childFeedingDetails.setParkingPlaceID(1);
    childFeedingDetails.setProcessed("Processed");
    childFeedingDetails.setProviderServiceMapID(1);
    childFeedingDetails.setReservedForChange("Reserved For Change");
    childFeedingDetails.setSyncedBy("Synced By");
    childFeedingDetails.setSyncedDate(mock(Timestamp.class));
    childFeedingDetails.setTypeOfFeed("Type Of Feed");
    childFeedingDetails.setTypeOfFoodIntolerances(new String[]{"Type Of Food Intolerances"});
    childFeedingDetails.setTypeofFoodIntolerance("Typeof Food Intolerance");
    childFeedingDetails.setTypeofFoodIntoleranceID(1L);
    childFeedingDetails.setVanID(1);
    childFeedingDetails.setVanSerialNo(1L);
    childFeedingDetails.setVehicalNo("Vehical No");
    childFeedingDetails.setVisitCode(1L);
    when(childFeedingDetailsRepo.save(Mockito.<ChildFeedingDetails>any())).thenReturn(childFeedingDetails);

    ChildFeedingDetails childFeedingDetails2 = new ChildFeedingDetails();
    childFeedingDetails2.setBenMotherID(1L);
    childFeedingDetails2.setBenVisitID(1L);
    childFeedingDetails2.setBeneficiaryRegID(1L);
    childFeedingDetails2.setCaptureDate(mock(Date.class));
    childFeedingDetails2.setChildID(1L);
    childFeedingDetails2.setCompFeedStartAge("Comp Feed Start Age");
    childFeedingDetails2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    childFeedingDetails2.setCreatedDate(mock(Timestamp.class));
    childFeedingDetails2.setDeleted(true);
    childFeedingDetails2.setFoodIntoleranceStatus("Food Intolerance Status");
    childFeedingDetails2.setID(1L);
    childFeedingDetails2.setLastModDate(mock(Timestamp.class));
    childFeedingDetails2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    childFeedingDetails2.setNoOfCompFeedPerDay("No Of Comp Feed Per Day");
    childFeedingDetails2.setOtherFoodIntolerance("Other Food Intolerance");
    childFeedingDetails2.setParkingPlaceID(1);
    childFeedingDetails2.setProcessed("Processed");
    childFeedingDetails2.setProviderServiceMapID(1);
    childFeedingDetails2.setReservedForChange("Reserved For Change");
    childFeedingDetails2.setSyncedBy("Synced By");
    childFeedingDetails2.setSyncedDate(mock(Timestamp.class));
    childFeedingDetails2.setTypeOfFeed("Type Of Feed");
    childFeedingDetails2.setTypeOfFoodIntolerances(new String[]{"Type Of Food Intolerances"});
    childFeedingDetails2.setTypeofFoodIntolerance("Typeof Food Intolerance");
    childFeedingDetails2.setTypeofFoodIntoleranceID(1L);
    childFeedingDetails2.setVanID(1);
    childFeedingDetails2.setVanSerialNo(1L);
    childFeedingDetails2.setVehicalNo("Vehical No");
    childFeedingDetails2.setVisitCode(1L);

    // Act
    Long actualSaveChildFeedingHistoryResult = commonNurseServiceImpl.saveChildFeedingHistory(childFeedingDetails2);

    // Assert
    verify(childFeedingDetailsRepo).save(isA(ChildFeedingDetails.class));
    assertEquals("Type Of Food Intolerances", childFeedingDetails2.getTypeofFoodIntolerance());
    assertEquals(1L, actualSaveChildFeedingHistoryResult.longValue());
  }

  @Test
  void testSaveChildFeedingHistory2() {
    // Arrange
    ChildFeedingDetails childFeedingDetails = mock(ChildFeedingDetails.class);
    when(childFeedingDetails.getID()).thenReturn(-1L);
    doNothing().when(childFeedingDetails).setBenMotherID(Mockito.<Long>any());
    doNothing().when(childFeedingDetails).setBenVisitID(Mockito.<Long>any());
    doNothing().when(childFeedingDetails).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(childFeedingDetails).setCaptureDate(Mockito.<Date>any());
    doNothing().when(childFeedingDetails).setChildID(Mockito.<Long>any());
    doNothing().when(childFeedingDetails).setCompFeedStartAge(Mockito.<String>any());
    doNothing().when(childFeedingDetails).setCreatedBy(Mockito.<String>any());
    doNothing().when(childFeedingDetails).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(childFeedingDetails).setDeleted(Mockito.<Boolean>any());
    doNothing().when(childFeedingDetails).setFoodIntoleranceStatus(Mockito.<String>any());
    doNothing().when(childFeedingDetails).setID(Mockito.<Long>any());
    doNothing().when(childFeedingDetails).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(childFeedingDetails).setModifiedBy(Mockito.<String>any());
    doNothing().when(childFeedingDetails).setNoOfCompFeedPerDay(Mockito.<String>any());
    doNothing().when(childFeedingDetails).setOtherFoodIntolerance(Mockito.<String>any());
    doNothing().when(childFeedingDetails).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(childFeedingDetails).setProcessed(Mockito.<String>any());
    doNothing().when(childFeedingDetails).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(childFeedingDetails).setReservedForChange(Mockito.<String>any());
    doNothing().when(childFeedingDetails).setSyncedBy(Mockito.<String>any());
    doNothing().when(childFeedingDetails).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(childFeedingDetails).setTypeOfFeed(Mockito.<String>any());
    doNothing().when(childFeedingDetails).setTypeOfFoodIntolerances(Mockito.<String[]>any());
    doNothing().when(childFeedingDetails).setTypeofFoodIntolerance(Mockito.<String>any());
    doNothing().when(childFeedingDetails).setTypeofFoodIntoleranceID(Mockito.<Long>any());
    doNothing().when(childFeedingDetails).setVanID(Mockito.<Integer>any());
    doNothing().when(childFeedingDetails).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(childFeedingDetails).setVehicalNo(Mockito.<String>any());
    doNothing().when(childFeedingDetails).setVisitCode(Mockito.<Long>any());
    childFeedingDetails.setBenMotherID(1L);
    childFeedingDetails.setBenVisitID(1L);
    childFeedingDetails.setBeneficiaryRegID(1L);
    childFeedingDetails.setCaptureDate(mock(Date.class));
    childFeedingDetails.setChildID(1L);
    childFeedingDetails.setCompFeedStartAge("Comp Feed Start Age");
    childFeedingDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    childFeedingDetails.setCreatedDate(mock(Timestamp.class));
    childFeedingDetails.setDeleted(true);
    childFeedingDetails.setFoodIntoleranceStatus("Food Intolerance Status");
    childFeedingDetails.setID(1L);
    childFeedingDetails.setLastModDate(mock(Timestamp.class));
    childFeedingDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    childFeedingDetails.setNoOfCompFeedPerDay("No Of Comp Feed Per Day");
    childFeedingDetails.setOtherFoodIntolerance("Other Food Intolerance");
    childFeedingDetails.setParkingPlaceID(1);
    childFeedingDetails.setProcessed("Processed");
    childFeedingDetails.setProviderServiceMapID(1);
    childFeedingDetails.setReservedForChange("Reserved For Change");
    childFeedingDetails.setSyncedBy("Synced By");
    childFeedingDetails.setSyncedDate(mock(Timestamp.class));
    childFeedingDetails.setTypeOfFeed("Type Of Feed");
    childFeedingDetails.setTypeOfFoodIntolerances(new String[]{"Type Of Food Intolerances"});
    childFeedingDetails.setTypeofFoodIntolerance("Typeof Food Intolerance");
    childFeedingDetails.setTypeofFoodIntoleranceID(1L);
    childFeedingDetails.setVanID(1);
    childFeedingDetails.setVanSerialNo(1L);
    childFeedingDetails.setVehicalNo("Vehical No");
    childFeedingDetails.setVisitCode(1L);
    when(childFeedingDetailsRepo.save(Mockito.<ChildFeedingDetails>any())).thenReturn(childFeedingDetails);

    ChildFeedingDetails childFeedingDetails2 = new ChildFeedingDetails();
    childFeedingDetails2.setBenMotherID(1L);
    childFeedingDetails2.setBenVisitID(1L);
    childFeedingDetails2.setBeneficiaryRegID(1L);
    childFeedingDetails2.setCaptureDate(mock(Date.class));
    childFeedingDetails2.setChildID(1L);
    childFeedingDetails2.setCompFeedStartAge("Comp Feed Start Age");
    childFeedingDetails2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    childFeedingDetails2.setCreatedDate(mock(Timestamp.class));
    childFeedingDetails2.setDeleted(true);
    childFeedingDetails2.setFoodIntoleranceStatus("Food Intolerance Status");
    childFeedingDetails2.setID(1L);
    childFeedingDetails2.setLastModDate(mock(Timestamp.class));
    childFeedingDetails2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    childFeedingDetails2.setNoOfCompFeedPerDay("No Of Comp Feed Per Day");
    childFeedingDetails2.setOtherFoodIntolerance("Other Food Intolerance");
    childFeedingDetails2.setParkingPlaceID(1);
    childFeedingDetails2.setProcessed("Processed");
    childFeedingDetails2.setProviderServiceMapID(1);
    childFeedingDetails2.setReservedForChange("Reserved For Change");
    childFeedingDetails2.setSyncedBy("Synced By");
    childFeedingDetails2.setSyncedDate(mock(Timestamp.class));
    childFeedingDetails2.setTypeOfFeed("Type Of Feed");
    childFeedingDetails2.setTypeOfFoodIntolerances(new String[]{"Type Of Food Intolerances"});
    childFeedingDetails2.setTypeofFoodIntolerance("Typeof Food Intolerance");
    childFeedingDetails2.setTypeofFoodIntoleranceID(1L);
    childFeedingDetails2.setVanID(1);
    childFeedingDetails2.setVanSerialNo(1L);
    childFeedingDetails2.setVehicalNo("Vehical No");
    childFeedingDetails2.setVisitCode(1L);

    // Act
    Long actualSaveChildFeedingHistoryResult = commonNurseServiceImpl.saveChildFeedingHistory(childFeedingDetails2);

    // Assert
    verify(childFeedingDetails).getID();
    verify(childFeedingDetails).setBenMotherID(isA(Long.class));
    verify(childFeedingDetails).setBenVisitID(isA(Long.class));
    verify(childFeedingDetails).setBeneficiaryRegID(isA(Long.class));
    verify(childFeedingDetails).setCaptureDate(isA(Date.class));
    verify(childFeedingDetails).setChildID(isA(Long.class));
    verify(childFeedingDetails).setCompFeedStartAge(eq("Comp Feed Start Age"));
    verify(childFeedingDetails).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(childFeedingDetails).setCreatedDate(isA(Timestamp.class));
    verify(childFeedingDetails).setDeleted(isA(Boolean.class));
    verify(childFeedingDetails).setFoodIntoleranceStatus(eq("Food Intolerance Status"));
    verify(childFeedingDetails).setID(isA(Long.class));
    verify(childFeedingDetails).setLastModDate(isA(Timestamp.class));
    verify(childFeedingDetails).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(childFeedingDetails).setNoOfCompFeedPerDay(eq("No Of Comp Feed Per Day"));
    verify(childFeedingDetails).setOtherFoodIntolerance(eq("Other Food Intolerance"));
    verify(childFeedingDetails).setParkingPlaceID(isA(Integer.class));
    verify(childFeedingDetails).setProcessed(eq("Processed"));
    verify(childFeedingDetails).setProviderServiceMapID(isA(Integer.class));
    verify(childFeedingDetails).setReservedForChange(eq("Reserved For Change"));
    verify(childFeedingDetails).setSyncedBy(eq("Synced By"));
    verify(childFeedingDetails).setSyncedDate(isA(Timestamp.class));
    verify(childFeedingDetails).setTypeOfFeed(eq("Type Of Feed"));
    verify(childFeedingDetails).setTypeOfFoodIntolerances(isA(String[].class));
    verify(childFeedingDetails).setTypeofFoodIntolerance(eq("Typeof Food Intolerance"));
    verify(childFeedingDetails).setTypeofFoodIntoleranceID(isA(Long.class));
    verify(childFeedingDetails).setVanID(isA(Integer.class));
    verify(childFeedingDetails).setVanSerialNo(isA(Long.class));
    verify(childFeedingDetails).setVehicalNo(eq("Vehical No"));
    verify(childFeedingDetails).setVisitCode(isA(Long.class));
    verify(childFeedingDetailsRepo).save(isA(ChildFeedingDetails.class));
    assertEquals("Type Of Food Intolerances", childFeedingDetails2.getTypeofFoodIntolerance());
    assertNull(actualSaveChildFeedingHistoryResult);
  }

  @Test
  void testSavePerinatalHistory() {
    // Arrange
    PerinatalHistory perinatalHistory = new PerinatalHistory();
    perinatalHistory.setBenVisitID(1L);
    perinatalHistory.setBeneficiaryRegID(1L);
    perinatalHistory.setBirthWeightG(10.0d);
    perinatalHistory.setComplicationAtBirth("Complication At Birth");
    perinatalHistory.setComplicationAtBirthID((short) 1);
    perinatalHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    perinatalHistory.setCreatedDate(mock(Timestamp.class));
    perinatalHistory.setDeleted(true);
    perinatalHistory.setDeliveryPlaceID((short) 1);
    perinatalHistory.setDeliveryTypeID((short) 1);
    perinatalHistory.setGestation("Gestation");
    perinatalHistory.setID(1L);
    perinatalHistory.setLastModDate(mock(Timestamp.class));
    perinatalHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    perinatalHistory.setOtherComplicationAtBirth("Other Complication At Birth");
    perinatalHistory.setOtherPlaceOfDelivery("Other Place Of Delivery");
    perinatalHistory.setParkingPlaceID(1);
    perinatalHistory.setPlaceOfDelivery("Place Of Delivery");
    perinatalHistory.setProcessed("Processed");
    perinatalHistory.setProviderServiceMapID(1);
    perinatalHistory.setReservedForChange("Reserved For Change");
    perinatalHistory.setSyncedBy("Synced By");
    perinatalHistory.setSyncedDate(mock(Timestamp.class));
    perinatalHistory.setTypeOfDelivery("Type Of Delivery");
    perinatalHistory.setVanSerialNo(1L);
    perinatalHistory.setVehicalNo("Vehical No");
    perinatalHistory.setVisitCode(1L);
    when(perinatalHistoryRepo.save(Mockito.<PerinatalHistory>any())).thenReturn(perinatalHistory);

    PerinatalHistory perinatalHistory2 = new PerinatalHistory();
    perinatalHistory2.setBenVisitID(1L);
    perinatalHistory2.setBeneficiaryRegID(1L);
    perinatalHistory2.setBirthWeightG(10.0d);
    perinatalHistory2.setComplicationAtBirth("Complication At Birth");
    perinatalHistory2.setComplicationAtBirthID((short) 1);
    perinatalHistory2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    perinatalHistory2.setCreatedDate(mock(Timestamp.class));
    perinatalHistory2.setDeleted(true);
    perinatalHistory2.setDeliveryPlaceID((short) 1);
    perinatalHistory2.setDeliveryTypeID((short) 1);
    perinatalHistory2.setGestation("Gestation");
    perinatalHistory2.setID(1L);
    perinatalHistory2.setLastModDate(mock(Timestamp.class));
    perinatalHistory2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    perinatalHistory2.setOtherComplicationAtBirth("Other Complication At Birth");
    perinatalHistory2.setOtherPlaceOfDelivery("Other Place Of Delivery");
    perinatalHistory2.setParkingPlaceID(1);
    perinatalHistory2.setPlaceOfDelivery("Place Of Delivery");
    perinatalHistory2.setProcessed("Processed");
    perinatalHistory2.setProviderServiceMapID(1);
    perinatalHistory2.setReservedForChange("Reserved For Change");
    perinatalHistory2.setSyncedBy("Synced By");
    perinatalHistory2.setSyncedDate(mock(Timestamp.class));
    perinatalHistory2.setTypeOfDelivery("Type Of Delivery");
    perinatalHistory2.setVanSerialNo(1L);
    perinatalHistory2.setVehicalNo("Vehical No");
    perinatalHistory2.setVisitCode(1L);

    // Act
    Long actualSavePerinatalHistoryResult = commonNurseServiceImpl.savePerinatalHistory(perinatalHistory2);

    // Assert
    verify(perinatalHistoryRepo).save(isA(PerinatalHistory.class));
    assertEquals(1L, actualSavePerinatalHistoryResult.longValue());
  }

  @Test
  void testSavePerinatalHistory2() {
    // Arrange
    PerinatalHistory perinatalHistory = mock(PerinatalHistory.class);
    when(perinatalHistory.getID()).thenReturn(-1L);
    doNothing().when(perinatalHistory).setBenVisitID(Mockito.<Long>any());
    doNothing().when(perinatalHistory).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(perinatalHistory).setBirthWeightG(Mockito.<Double>any());
    doNothing().when(perinatalHistory).setComplicationAtBirth(Mockito.<String>any());
    doNothing().when(perinatalHistory).setComplicationAtBirthID(Mockito.<Short>any());
    doNothing().when(perinatalHistory).setCreatedBy(Mockito.<String>any());
    doNothing().when(perinatalHistory).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(perinatalHistory).setDeleted(Mockito.<Boolean>any());
    doNothing().when(perinatalHistory).setDeliveryPlaceID(Mockito.<Short>any());
    doNothing().when(perinatalHistory).setDeliveryTypeID(Mockito.<Short>any());
    doNothing().when(perinatalHistory).setGestation(Mockito.<String>any());
    doNothing().when(perinatalHistory).setID(Mockito.<Long>any());
    doNothing().when(perinatalHistory).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(perinatalHistory).setModifiedBy(Mockito.<String>any());
    doNothing().when(perinatalHistory).setOtherComplicationAtBirth(Mockito.<String>any());
    doNothing().when(perinatalHistory).setOtherPlaceOfDelivery(Mockito.<String>any());
    doNothing().when(perinatalHistory).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(perinatalHistory).setPlaceOfDelivery(Mockito.<String>any());
    doNothing().when(perinatalHistory).setProcessed(Mockito.<String>any());
    doNothing().when(perinatalHistory).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(perinatalHistory).setReservedForChange(Mockito.<String>any());
    doNothing().when(perinatalHistory).setSyncedBy(Mockito.<String>any());
    doNothing().when(perinatalHistory).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(perinatalHistory).setTypeOfDelivery(Mockito.<String>any());
    doNothing().when(perinatalHistory).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(perinatalHistory).setVehicalNo(Mockito.<String>any());
    doNothing().when(perinatalHistory).setVisitCode(Mockito.<Long>any());
    perinatalHistory.setBenVisitID(1L);
    perinatalHistory.setBeneficiaryRegID(1L);
    perinatalHistory.setBirthWeightG(10.0d);
    perinatalHistory.setComplicationAtBirth("Complication At Birth");
    perinatalHistory.setComplicationAtBirthID((short) 1);
    perinatalHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    perinatalHistory.setCreatedDate(mock(Timestamp.class));
    perinatalHistory.setDeleted(true);
    perinatalHistory.setDeliveryPlaceID((short) 1);
    perinatalHistory.setDeliveryTypeID((short) 1);
    perinatalHistory.setGestation("Gestation");
    perinatalHistory.setID(1L);
    perinatalHistory.setLastModDate(mock(Timestamp.class));
    perinatalHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    perinatalHistory.setOtherComplicationAtBirth("Other Complication At Birth");
    perinatalHistory.setOtherPlaceOfDelivery("Other Place Of Delivery");
    perinatalHistory.setParkingPlaceID(1);
    perinatalHistory.setPlaceOfDelivery("Place Of Delivery");
    perinatalHistory.setProcessed("Processed");
    perinatalHistory.setProviderServiceMapID(1);
    perinatalHistory.setReservedForChange("Reserved For Change");
    perinatalHistory.setSyncedBy("Synced By");
    perinatalHistory.setSyncedDate(mock(Timestamp.class));
    perinatalHistory.setTypeOfDelivery("Type Of Delivery");
    perinatalHistory.setVanSerialNo(1L);
    perinatalHistory.setVehicalNo("Vehical No");
    perinatalHistory.setVisitCode(1L);
    when(perinatalHistoryRepo.save(Mockito.<PerinatalHistory>any())).thenReturn(perinatalHistory);

    PerinatalHistory perinatalHistory2 = new PerinatalHistory();
    perinatalHistory2.setBenVisitID(1L);
    perinatalHistory2.setBeneficiaryRegID(1L);
    perinatalHistory2.setBirthWeightG(10.0d);
    perinatalHistory2.setComplicationAtBirth("Complication At Birth");
    perinatalHistory2.setComplicationAtBirthID((short) 1);
    perinatalHistory2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    perinatalHistory2.setCreatedDate(mock(Timestamp.class));
    perinatalHistory2.setDeleted(true);
    perinatalHistory2.setDeliveryPlaceID((short) 1);
    perinatalHistory2.setDeliveryTypeID((short) 1);
    perinatalHistory2.setGestation("Gestation");
    perinatalHistory2.setID(1L);
    perinatalHistory2.setLastModDate(mock(Timestamp.class));
    perinatalHistory2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    perinatalHistory2.setOtherComplicationAtBirth("Other Complication At Birth");
    perinatalHistory2.setOtherPlaceOfDelivery("Other Place Of Delivery");
    perinatalHistory2.setParkingPlaceID(1);
    perinatalHistory2.setPlaceOfDelivery("Place Of Delivery");
    perinatalHistory2.setProcessed("Processed");
    perinatalHistory2.setProviderServiceMapID(1);
    perinatalHistory2.setReservedForChange("Reserved For Change");
    perinatalHistory2.setSyncedBy("Synced By");
    perinatalHistory2.setSyncedDate(mock(Timestamp.class));
    perinatalHistory2.setTypeOfDelivery("Type Of Delivery");
    perinatalHistory2.setVanSerialNo(1L);
    perinatalHistory2.setVehicalNo("Vehical No");
    perinatalHistory2.setVisitCode(1L);

    // Act
    Long actualSavePerinatalHistoryResult = commonNurseServiceImpl.savePerinatalHistory(perinatalHistory2);

    // Assert
    verify(perinatalHistory).getID();
    verify(perinatalHistory).setBenVisitID(isA(Long.class));
    verify(perinatalHistory).setBeneficiaryRegID(isA(Long.class));
    verify(perinatalHistory).setBirthWeightG(isA(Double.class));
    verify(perinatalHistory).setComplicationAtBirth(eq("Complication At Birth"));
    verify(perinatalHistory).setComplicationAtBirthID(isA(Short.class));
    verify(perinatalHistory).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(perinatalHistory).setCreatedDate(isA(Timestamp.class));
    verify(perinatalHistory).setDeleted(isA(Boolean.class));
    verify(perinatalHistory).setDeliveryPlaceID(isA(Short.class));
    verify(perinatalHistory).setDeliveryTypeID(isA(Short.class));
    verify(perinatalHistory).setGestation(eq("Gestation"));
    verify(perinatalHistory).setID(isA(Long.class));
    verify(perinatalHistory).setLastModDate(isA(Timestamp.class));
    verify(perinatalHistory).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(perinatalHistory).setOtherComplicationAtBirth(eq("Other Complication At Birth"));
    verify(perinatalHistory).setOtherPlaceOfDelivery(eq("Other Place Of Delivery"));
    verify(perinatalHistory).setParkingPlaceID(isA(Integer.class));
    verify(perinatalHistory).setPlaceOfDelivery(eq("Place Of Delivery"));
    verify(perinatalHistory).setProcessed(eq("Processed"));
    verify(perinatalHistory).setProviderServiceMapID(isA(Integer.class));
    verify(perinatalHistory).setReservedForChange(eq("Reserved For Change"));
    verify(perinatalHistory).setSyncedBy(eq("Synced By"));
    verify(perinatalHistory).setSyncedDate(isA(Timestamp.class));
    verify(perinatalHistory).setTypeOfDelivery(eq("Type Of Delivery"));
    verify(perinatalHistory).setVanSerialNo(isA(Long.class));
    verify(perinatalHistory).setVehicalNo(eq("Vehical No"));
    verify(perinatalHistory).setVisitCode(isA(Long.class));
    verify(perinatalHistoryRepo).save(isA(PerinatalHistory.class));
    assertNull(actualSavePerinatalHistoryResult);
  }

  @Test
  void testGetBenAdherence() {
    // Arrange
    when(benAdherenceRepo.getBenAdherence(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualBenAdherence = commonNurseServiceImpl.getBenAdherence(1L, 1L);

    // Assert
    verify(benAdherenceRepo).getBenAdherence(isA(Long.class), isA(Long.class));
    assertEquals("null", actualBenAdherence);
  }

  @Test
  void testGetLabTestOrders() {
    // Arrange
    when(labTestOrderDetailRepo.getLabTestOrderDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualLabTestOrders = commonNurseServiceImpl.getLabTestOrders(1L, 1L);

    // Assert
    verify(labTestOrderDetailRepo).getLabTestOrderDetails(isA(Long.class), isA(Long.class));
    assertEquals("{}", actualLabTestOrders);
  }

  @Test
  void testGetDevelopmentHistory() throws IEMRException {
    // Arrange
    BenChildDevelopmentHistory benChildDevelopmentHistory = new BenChildDevelopmentHistory();
    benChildDevelopmentHistory.setBenVisitID(1L);
    benChildDevelopmentHistory.setBeneficiaryRegID(1L);
    benChildDevelopmentHistory.setCaptureDate(mock(Date.class));
    benChildDevelopmentHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benChildDevelopmentHistory.setCreatedDate(mock(Timestamp.class));
    benChildDevelopmentHistory.setDeleted(true);
    benChildDevelopmentHistory.setDevelopmentProblem("Development Problem");
    benChildDevelopmentHistory.setDevelopmentProblems(new ArrayList<>());
    benChildDevelopmentHistory.setFineMotorMilestone("Fine Motor Milestone");
    benChildDevelopmentHistory.setFineMotorMilestones(new ArrayList<>());
    benChildDevelopmentHistory.setGrossMotorMilestone("Gross Motor Milestone");
    benChildDevelopmentHistory.setGrossMotorMilestones(new ArrayList<>());
    benChildDevelopmentHistory.setID(1L);
    benChildDevelopmentHistory.setIsFineMotorMilestones(true);
    benChildDevelopmentHistory.setIsGrossMotorMilestones(true);
    benChildDevelopmentHistory.setIsLanguageMilestones(true);
    benChildDevelopmentHistory.setIsSocialMilestones(true);
    benChildDevelopmentHistory.setLanguageMilestone("en");
    benChildDevelopmentHistory.setLanguageMilestones(new ArrayList<>());
    benChildDevelopmentHistory.setLastModDate(mock(Timestamp.class));
    benChildDevelopmentHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benChildDevelopmentHistory.setParkingPlaceID(1);
    benChildDevelopmentHistory.setProcessed("Processed");
    benChildDevelopmentHistory.setProviderServiceMapID(1);
    benChildDevelopmentHistory.setReservedForChange("Reserved For Change");
    benChildDevelopmentHistory.setSocialMilestone("Social Milestone");
    benChildDevelopmentHistory.setSocialMilestones(new ArrayList<>());
    benChildDevelopmentHistory.setSyncedBy("Synced By");
    benChildDevelopmentHistory.setSyncedDate(mock(Timestamp.class));
    benChildDevelopmentHistory.setVanID(1);
    benChildDevelopmentHistory.setVanSerialNo(1L);
    benChildDevelopmentHistory.setVehicalNo("Vehical No");
    benChildDevelopmentHistory.setVisitCode(1L);
    when(benChildDevelopmentHistoryRepo.getBenDevelopmentHistory(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(benChildDevelopmentHistory);

    // Act
    BenChildDevelopmentHistory actualDevelopmentHistory = commonNurseServiceImpl.getDevelopmentHistory(1L, 1L);

    // Assert
    verify(benChildDevelopmentHistoryRepo).getBenDevelopmentHistory(isA(Long.class), isA(Long.class));
    assertEquals(1, actualDevelopmentHistory.getDevelopmentProblems().size());
    assertEquals(1, actualDevelopmentHistory.getFineMotorMilestones().size());
    assertEquals(1, actualDevelopmentHistory.getGrossMotorMilestones().size());
    assertEquals(1, actualDevelopmentHistory.getLanguageMilestones().size());
    assertEquals(1, actualDevelopmentHistory.getSocialMilestones().size());
    assertSame(benChildDevelopmentHistory, actualDevelopmentHistory);
  }

  @Test
  void testGetPerinatalHistory() throws IEMRException {
    // Arrange
    PerinatalHistory perinatalHistory = new PerinatalHistory();
    perinatalHistory.setBenVisitID(1L);
    perinatalHistory.setBeneficiaryRegID(1L);
    perinatalHistory.setBirthWeightG(10.0d);
    perinatalHistory.setComplicationAtBirth("Complication At Birth");
    perinatalHistory.setComplicationAtBirthID((short) 1);
    perinatalHistory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    perinatalHistory.setCreatedDate(mock(Timestamp.class));
    perinatalHistory.setDeleted(true);
    perinatalHistory.setDeliveryPlaceID((short) 1);
    perinatalHistory.setDeliveryTypeID((short) 1);
    perinatalHistory.setGestation("Gestation");
    perinatalHistory.setID(1L);
    perinatalHistory.setLastModDate(mock(Timestamp.class));
    perinatalHistory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    perinatalHistory.setOtherComplicationAtBirth("Other Complication At Birth");
    perinatalHistory.setOtherPlaceOfDelivery("Other Place Of Delivery");
    perinatalHistory.setParkingPlaceID(1);
    perinatalHistory.setPlaceOfDelivery("Place Of Delivery");
    perinatalHistory.setProcessed("Processed");
    perinatalHistory.setProviderServiceMapID(1);
    perinatalHistory.setReservedForChange("Reserved For Change");
    perinatalHistory.setSyncedBy("Synced By");
    perinatalHistory.setSyncedDate(mock(Timestamp.class));
    perinatalHistory.setTypeOfDelivery("Type Of Delivery");
    perinatalHistory.setVanSerialNo(1L);
    perinatalHistory.setVehicalNo("Vehical No");
    perinatalHistory.setVisitCode(1L);
    when(perinatalHistoryRepo.getBenPerinatalHistory(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(perinatalHistory);

    // Act
    PerinatalHistory actualPerinatalHistory = commonNurseServiceImpl.getPerinatalHistory(1L, 1L);

    // Assert
    verify(perinatalHistoryRepo).getBenPerinatalHistory(isA(Long.class), isA(Long.class));
    assertSame(perinatalHistory, actualPerinatalHistory);
  }

  @Test
  void testGetFeedingHistory() throws IEMRException {
    // Arrange
    ChildFeedingDetails childFeedingDetails = new ChildFeedingDetails();
    childFeedingDetails.setBenMotherID(1L);
    childFeedingDetails.setBenVisitID(1L);
    childFeedingDetails.setBeneficiaryRegID(1L);
    childFeedingDetails.setCaptureDate(mock(Date.class));
    childFeedingDetails.setChildID(1L);
    childFeedingDetails.setCompFeedStartAge("Comp Feed Start Age");
    childFeedingDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    childFeedingDetails.setCreatedDate(mock(Timestamp.class));
    childFeedingDetails.setDeleted(true);
    childFeedingDetails.setFoodIntoleranceStatus("Food Intolerance Status");
    childFeedingDetails.setID(1L);
    childFeedingDetails.setLastModDate(mock(Timestamp.class));
    childFeedingDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    childFeedingDetails.setNoOfCompFeedPerDay("No Of Comp Feed Per Day");
    childFeedingDetails.setOtherFoodIntolerance("Other Food Intolerance");
    childFeedingDetails.setParkingPlaceID(1);
    childFeedingDetails.setProcessed("Processed");
    childFeedingDetails.setProviderServiceMapID(1);
    childFeedingDetails.setReservedForChange("Reserved For Change");
    childFeedingDetails.setSyncedBy("Synced By");
    childFeedingDetails.setSyncedDate(mock(Timestamp.class));
    childFeedingDetails.setTypeOfFeed("Type Of Feed");
    childFeedingDetails.setTypeOfFoodIntolerances(new String[]{"Type Of Food Intolerances"});
    childFeedingDetails.setTypeofFoodIntolerance("Typeof Food Intolerance");
    childFeedingDetails.setTypeofFoodIntoleranceID(1L);
    childFeedingDetails.setVanID(1);
    childFeedingDetails.setVanSerialNo(1L);
    childFeedingDetails.setVehicalNo("Vehical No");
    childFeedingDetails.setVisitCode(1L);
    when(childFeedingDetailsRepo.getBenFeedingHistory(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(childFeedingDetails);

    // Act
    ChildFeedingDetails actualFeedingHistory = commonNurseServiceImpl.getFeedingHistory(1L, 1L);

    // Assert
    verify(childFeedingDetailsRepo).getBenFeedingHistory(isA(Long.class), isA(Long.class));
    assertSame(childFeedingDetails, actualFeedingHistory);
    assertArrayEquals(new String[]{"Typeof Food Intolerance"}, actualFeedingHistory.getTypeOfFoodIntolerances());
  }

  @Test
  void testFetchBenPerinatalHistory() {
    // Arrange
    when(perinatalHistoryRepo.getBenPerinatalDetail(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualFetchBenPerinatalHistoryResult = commonNurseServiceImpl.fetchBenPerinatalHistory(1L);

    // Assert
    verify(perinatalHistoryRepo).getBenPerinatalDetail(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"placeOfDelivery"
            + "\",\"columnName\":\"Place of Delivery\"},{\"keyName\":\"otherPlaceOfDelivery\",\"columnName\":\"Other Place of"
            + " Delivery\"},{\"keyName\":\"typeOfDelivery\",\"columnName\":\"Type of Delivery\"},{\"keyName\":\"complicationAtBirth"
            + "\",\"columnName\":\"Complication at Birth\"},{\"keyName\":\"otherComplicationAtBirth\",\"columnName\":\"Other"
            + " Complication at Birth\"},{\"keyName\":\"gestation\",\"columnName\":\"Gestation\"},{\"keyName\":\"birthWeight_gram"
            + "\",\"columnName\":\"Birth Weight(g)\"}]}",
        actualFetchBenPerinatalHistoryResult);
  }

  @Test
  void testFetchBenFeedingHistory() {
    // Arrange
    when(childFeedingDetailsRepo.getBenFeedingHistoryDetail(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualFetchBenFeedingHistoryResult = commonNurseServiceImpl.fetchBenFeedingHistory(1L);

    // Assert
    verify(childFeedingDetailsRepo).getBenFeedingHistoryDetail(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"childID\""
            + ",\"columnName\":\"Child ID\"},{\"keyName\":\"benMotherID\",\"columnName\":\"Beneficiary Mother ID\"},{\"keyName\""
            + ":\"typeOfFeed\",\"columnName\":\"Type of Feed\"},{\"keyName\":\"compFeedStartAge\",\"columnName\":\"Comp Feed Start"
            + " Age\"},{\"keyName\":\"noOfCompFeedPerDay\",\"columnName\":\"No of Comp Feed Per Day\"},{\"keyName\":"
            + "\"foodIntoleranceStatus\",\"columnName\":\"Food Intolerance Status\"},{\"keyName\":\"typeofFoodIntolerance\","
            + "\"columnName\":\"Type of Food Intolerance\"},{\"keyName\":\"otherFoodIntolerance\",\"columnName\":\"Other Food"
            + " Intolerance\"}]}",
        actualFetchBenFeedingHistoryResult);
  }

  @Test
  void testFetchBenDevelopmentHistory() {
    // Arrange
    when(benChildDevelopmentHistoryRepo.getBenDevelopmentHistoryDetail(Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualFetchBenDevelopmentHistoryResult = commonNurseServiceImpl.fetchBenDevelopmentHistory(1L);

    // Assert
    verify(benChildDevelopmentHistoryRepo).getBenDevelopmentHistoryDetail(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"grossMoto"
            + "rMilestone\",\"columnName\":\"Gross Motor Milestone\"},{\"keyName\":\"fineMotorMilestone\",\"columnName\":\"Fine"
            + " Motor Milestone\"},{\"keyName\":\"socialMilestone\",\"columnName\":\"Social Milestone\"},{\"keyName\":"
            + "\"languageMilestone\",\"columnName\":\"Language Milestone\"},{\"keyName\":\"developmentProblem\",\"columnName\":"
            + "\"Development Problem\"}]}",
        actualFetchBenDevelopmentHistoryResult);
  }

  @Test
  void testGetGraphicalTrendData() {
    // Arrange
    when(benVisitDetailRepo.getLastSixVisitDetailsForBeneficiary(Mockito.<Long>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    Map<String, Object> actualGraphicalTrendData = commonNurseServiceImpl.getGraphicalTrendData(1L, "Visit Category");

    // Assert
    verify(benVisitDetailRepo).getLastSixVisitDetailsForBeneficiary(isA(Long.class), eq("Visit Category"));
    assertEquals(3, actualGraphicalTrendData.size());
  }

  @Test
  void testGetGraphicalTrendData2() {
    // Arrange
    when(benAnthropometryRepo.getBenAnthropometryDetailForGraphtrends(Mockito.<ArrayList<Long>>any()))
        .thenReturn(new ArrayList<>());
    when(benPhysicalVitalRepo.getBenPhysicalVitalDetailForGraphTrends(Mockito.<ArrayList<Long>>any()))
        .thenReturn(new ArrayList<>());

    ArrayList<Object[]> objectArrayList = new ArrayList<>();
    objectArrayList.add(new Object[]{"42", "42", BigInteger.valueOf(1L)});
    when(benVisitDetailRepo.getLastSixVisitDetailsForBeneficiary(Mockito.<Long>any(), Mockito.<String>any()))
        .thenReturn(objectArrayList);

    // Act
    Map<String, Object> actualGraphicalTrendData = commonNurseServiceImpl.getGraphicalTrendData(1L, "Visit Category");

    // Assert
    verify(benAnthropometryRepo).getBenAnthropometryDetailForGraphtrends(isA(ArrayList.class));
    verify(benPhysicalVitalRepo).getBenPhysicalVitalDetailForGraphTrends(isA(ArrayList.class));
    verify(benVisitDetailRepo).getLastSixVisitDetailsForBeneficiary(isA(Long.class), eq("Visit Category"));
    assertEquals(3, actualGraphicalTrendData.size());
  }

  @Test
  void testGetGraphicalTrendData3() {
    // Arrange
    when(benCancerVitalDetailRepo.getBenCancerVitalDetailForGraph(Mockito.<ArrayList<Long>>any()))
        .thenReturn(new ArrayList<>());

    ArrayList<Object[]> objectArrayList = new ArrayList<>();
    objectArrayList.add(new Object[]{"42", "Cancer Screening", BigInteger.valueOf(1L)});
    when(benVisitDetailRepo.getLastSixVisitDetailsForBeneficiary(Mockito.<Long>any(), Mockito.<String>any()))
        .thenReturn(objectArrayList);

    // Act
    Map<String, Object> actualGraphicalTrendData = commonNurseServiceImpl.getGraphicalTrendData(1L, "Visit Category");

    // Assert
    verify(benCancerVitalDetailRepo).getBenCancerVitalDetailForGraph(isA(ArrayList.class));
    verify(benVisitDetailRepo).getLastSixVisitDetailsForBeneficiary(isA(Long.class), eq("Visit Category"));
    assertEquals(3, actualGraphicalTrendData.size());
  }

  @Test
  void testGetGraphicalTrendData4() {
    // Arrange
    Timestamp createdDate = mock(Timestamp.class);
    when(createdDate.toLocalDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    BenCancerVitalDetail benCancerVitalDetail = new BenCancerVitalDetail();
    benCancerVitalDetail.setBenVisitID(1L);
    benCancerVitalDetail.setBeneficiaryRegID(1L);
    benCancerVitalDetail.setBloodGlucose_2HrPostPrandial((short) 1);
    benCancerVitalDetail.setBloodGlucose_Fasting((short) 1);
    benCancerVitalDetail.setBloodGlucose_Random((short) 1);
    benCancerVitalDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benCancerVitalDetail.setCreatedDate(createdDate);
    benCancerVitalDetail.setDeleted(true);
    benCancerVitalDetail.setDiastolicBP_1stReading((short) 1);
    benCancerVitalDetail.setDiastolicBP_2ndReading((short) 1);
    benCancerVitalDetail.setDiastolicBP_3rdReading((short) 1);
    benCancerVitalDetail.setHbA1C((short) 1);
    benCancerVitalDetail.setHeight_cm(10.0d);
    benCancerVitalDetail.setHemoglobin(10.0d);
    benCancerVitalDetail.setID(1L);
    benCancerVitalDetail.setLastModDate(mock(Timestamp.class));
    benCancerVitalDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benCancerVitalDetail.setParkingPlaceID(1);
    benCancerVitalDetail.setProcessed("Cancer Screening");
    benCancerVitalDetail.setProviderServiceMapID(1);
    benCancerVitalDetail.setRbsTestRemarks("Cancer Screening");
    benCancerVitalDetail.setRbsTestResult("Cancer Screening");
    benCancerVitalDetail.setReservedForChange("Cancer Screening");
    benCancerVitalDetail.setSyncedBy("Cancer Screening");
    benCancerVitalDetail.setSyncedDate(mock(Timestamp.class));
    benCancerVitalDetail.setSystolicBP_1stReading((short) 1);
    benCancerVitalDetail.setSystolicBP_2ndReading((short) 1);
    benCancerVitalDetail.setSystolicBP_3rdReading((short) 1);
    benCancerVitalDetail.setVanID(1);
    benCancerVitalDetail.setVanSerialNo(1L);
    benCancerVitalDetail.setVehicalNo("Cancer Screening");
    benCancerVitalDetail.setVisitCode(1L);
    benCancerVitalDetail.setWaistCircumference_cm(10.0d);
    benCancerVitalDetail.setWeight_Kg(10.0d);
    benCancerVitalDetail.setsPO2("Cancer Screening");

    ArrayList<BenCancerVitalDetail> benCancerVitalDetailList = new ArrayList<>();
    benCancerVitalDetailList.add(benCancerVitalDetail);
    when(benCancerVitalDetailRepo.getBenCancerVitalDetailForGraph(Mockito.<ArrayList<Long>>any()))
        .thenReturn(benCancerVitalDetailList);

    ArrayList<Object[]> objectArrayList = new ArrayList<>();
    objectArrayList.add(new Object[]{"42", "Cancer Screening", BigInteger.valueOf(1L)});
    when(benVisitDetailRepo.getLastSixVisitDetailsForBeneficiary(Mockito.<Long>any(), Mockito.<String>any()))
        .thenReturn(objectArrayList);

    // Act
    Map<String, Object> actualGraphicalTrendData = commonNurseServiceImpl.getGraphicalTrendData(1L, "Visit Category");

    // Assert
    verify(benCancerVitalDetailRepo).getBenCancerVitalDetailForGraph(isA(ArrayList.class));
    verify(benVisitDetailRepo).getLastSixVisitDetailsForBeneficiary(isA(Long.class), eq("Visit Category"));
    verify(createdDate, atLeast(1)).toLocalDateTime();
    assertEquals(3, actualGraphicalTrendData.size());
    assertEquals(1, ((List<HashMap>) actualGraphicalTrendData.get("bgList")).size());
    assertEquals(1, ((List<HashMap>) actualGraphicalTrendData.get("bpList")).size());
    assertEquals(1, ((List<HashMap>) actualGraphicalTrendData.get("weightList")).size());
    assertEquals(2, ((List<HashMap>) actualGraphicalTrendData.get("weightList")).get(0).size());
    assertEquals(3, ((List<HashMap>) actualGraphicalTrendData.get("bpList")).get(0).size());
    assertEquals(4, ((List<HashMap>) actualGraphicalTrendData.get("bgList")).get(0).size());
  }

  @Test
  void testGetBenSymptomaticData() throws Exception {
    // Arrange
    when(iDRSDataRepo.isDefectiveVisionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isDiabeticCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isEpilepsyCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isHypertensionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.getBenIdrsDetailsLast_3_Month(Mockito.<Long>any(), Mockito.<Timestamp>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualBenSymptomaticData = commonNurseServiceImpl.getBenSymptomaticData(1L);

    // Assert
    verify(iDRSDataRepo).getBenIdrsDetailsLast_3_Month(isA(Long.class), isA(Timestamp.class));
    verify(iDRSDataRepo).isDefectiveVisionCheck(isA(Long.class));
    verify(iDRSDataRepo).isDiabeticCheck(isA(Long.class));
    verify(iDRSDataRepo).isEpilepsyCheck(isA(Long.class));
    verify(iDRSDataRepo).isHypertensionCheck(isA(Long.class));
    assertEquals(
        "{\"questionariesData\":[],\"isHypertension\":true,\"isDefectiveVision\":true,\"isDiabetic\":true,\"isEpilepsy"
            + "\":true}",
        actualBenSymptomaticData);
  }

  @Test
  void testGetBenSymptomaticData2() throws Exception {
    // Arrange
    when(iDRSDataRepo.isDefectiveVisionCheck(Mockito.<Long>any())).thenReturn(-3);
    when(iDRSDataRepo.isDiabeticCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isEpilepsyCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isHypertensionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.getBenIdrsDetailsLast_3_Month(Mockito.<Long>any(), Mockito.<Timestamp>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualBenSymptomaticData = commonNurseServiceImpl.getBenSymptomaticData(1L);

    // Assert
    verify(iDRSDataRepo).getBenIdrsDetailsLast_3_Month(isA(Long.class), isA(Timestamp.class));
    verify(iDRSDataRepo).isDefectiveVisionCheck(isA(Long.class));
    verify(iDRSDataRepo).isDiabeticCheck(isA(Long.class));
    verify(iDRSDataRepo).isEpilepsyCheck(isA(Long.class));
    verify(iDRSDataRepo).isHypertensionCheck(isA(Long.class));
    assertEquals(
        "{\"questionariesData\":[],\"isHypertension\":true,\"isDefectiveVision\":false,\"isDiabetic\":true,\"isEpilepsy"
            + "\":true}",
        actualBenSymptomaticData);
  }

  @Test
  void testGetBenSymptomaticData3() throws Exception {
    // Arrange
    when(iDRSDataRepo.isDefectiveVisionCheck(Mockito.<Long>any())).thenReturn(null);
    when(iDRSDataRepo.isDiabeticCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isEpilepsyCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isHypertensionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.getBenIdrsDetailsLast_3_Month(Mockito.<Long>any(), Mockito.<Timestamp>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualBenSymptomaticData = commonNurseServiceImpl.getBenSymptomaticData(1L);

    // Assert
    verify(iDRSDataRepo).getBenIdrsDetailsLast_3_Month(isA(Long.class), isA(Timestamp.class));
    verify(iDRSDataRepo).isDefectiveVisionCheck(isA(Long.class));
    verify(iDRSDataRepo).isDiabeticCheck(isA(Long.class));
    verify(iDRSDataRepo).isEpilepsyCheck(isA(Long.class));
    verify(iDRSDataRepo).isHypertensionCheck(isA(Long.class));
    assertEquals(
        "{\"questionariesData\":[],\"isHypertension\":true,\"isDefectiveVision\":false,\"isDiabetic\":true,\"isEpilepsy"
            + "\":true}",
        actualBenSymptomaticData);
  }

  @Test
  void testGetBenSymptomaticData4() throws Exception {
    // Arrange
    when(iDRSDataRepo.isDefectiveVisionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isDiabeticCheck(Mockito.<Long>any())).thenReturn(-3);
    when(iDRSDataRepo.isEpilepsyCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isHypertensionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.getBenIdrsDetailsLast_3_Month(Mockito.<Long>any(), Mockito.<Timestamp>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualBenSymptomaticData = commonNurseServiceImpl.getBenSymptomaticData(1L);

    // Assert
    verify(iDRSDataRepo).getBenIdrsDetailsLast_3_Month(isA(Long.class), isA(Timestamp.class));
    verify(iDRSDataRepo).isDefectiveVisionCheck(isA(Long.class));
    verify(iDRSDataRepo).isDiabeticCheck(isA(Long.class));
    verify(iDRSDataRepo).isEpilepsyCheck(isA(Long.class));
    verify(iDRSDataRepo).isHypertensionCheck(isA(Long.class));
    assertEquals(
        "{\"questionariesData\":[],\"isHypertension\":true,\"isDefectiveVision\":true,\"isDiabetic\":false,\"isEpilepsy"
            + "\":true}",
        actualBenSymptomaticData);
  }

  @Test
  void testGetBenSymptomaticData5() throws Exception {
    // Arrange
    when(iDRSDataRepo.isDefectiveVisionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isDiabeticCheck(Mockito.<Long>any())).thenReturn(null);
    when(iDRSDataRepo.isEpilepsyCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isHypertensionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.getBenIdrsDetailsLast_3_Month(Mockito.<Long>any(), Mockito.<Timestamp>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualBenSymptomaticData = commonNurseServiceImpl.getBenSymptomaticData(1L);

    // Assert
    verify(iDRSDataRepo).getBenIdrsDetailsLast_3_Month(isA(Long.class), isA(Timestamp.class));
    verify(iDRSDataRepo).isDefectiveVisionCheck(isA(Long.class));
    verify(iDRSDataRepo).isDiabeticCheck(isA(Long.class));
    verify(iDRSDataRepo).isEpilepsyCheck(isA(Long.class));
    verify(iDRSDataRepo).isHypertensionCheck(isA(Long.class));
    assertEquals(
        "{\"questionariesData\":[],\"isHypertension\":true,\"isDefectiveVision\":true,\"isDiabetic\":false,\"isEpilepsy"
            + "\":true}",
        actualBenSymptomaticData);
  }

  @Test
  void testGetBenSymptomaticData6() throws Exception {
    // Arrange
    when(iDRSDataRepo.isDefectiveVisionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isDiabeticCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isEpilepsyCheck(Mockito.<Long>any())).thenReturn(-3);
    when(iDRSDataRepo.isHypertensionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.getBenIdrsDetailsLast_3_Month(Mockito.<Long>any(), Mockito.<Timestamp>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualBenSymptomaticData = commonNurseServiceImpl.getBenSymptomaticData(1L);

    // Assert
    verify(iDRSDataRepo).getBenIdrsDetailsLast_3_Month(isA(Long.class), isA(Timestamp.class));
    verify(iDRSDataRepo).isDefectiveVisionCheck(isA(Long.class));
    verify(iDRSDataRepo).isDiabeticCheck(isA(Long.class));
    verify(iDRSDataRepo).isEpilepsyCheck(isA(Long.class));
    verify(iDRSDataRepo).isHypertensionCheck(isA(Long.class));
    assertEquals(
        "{\"questionariesData\":[],\"isHypertension\":true,\"isDefectiveVision\":true,\"isDiabetic\":true,\"isEpilepsy"
            + "\":false}",
        actualBenSymptomaticData);
  }

  @Test
  void testGetBenSymptomaticData7() throws Exception {
    // Arrange
    when(iDRSDataRepo.isDefectiveVisionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isDiabeticCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isEpilepsyCheck(Mockito.<Long>any())).thenReturn(null);
    when(iDRSDataRepo.isHypertensionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.getBenIdrsDetailsLast_3_Month(Mockito.<Long>any(), Mockito.<Timestamp>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualBenSymptomaticData = commonNurseServiceImpl.getBenSymptomaticData(1L);

    // Assert
    verify(iDRSDataRepo).getBenIdrsDetailsLast_3_Month(isA(Long.class), isA(Timestamp.class));
    verify(iDRSDataRepo).isDefectiveVisionCheck(isA(Long.class));
    verify(iDRSDataRepo).isDiabeticCheck(isA(Long.class));
    verify(iDRSDataRepo).isEpilepsyCheck(isA(Long.class));
    verify(iDRSDataRepo).isHypertensionCheck(isA(Long.class));
    assertEquals(
        "{\"questionariesData\":[],\"isHypertension\":true,\"isDefectiveVision\":true,\"isDiabetic\":true,\"isEpilepsy"
            + "\":false}",
        actualBenSymptomaticData);
  }

  @Test
  void testGetBenSymptomaticData8() throws Exception {
    // Arrange
    when(iDRSDataRepo.isDefectiveVisionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isDiabeticCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isEpilepsyCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isHypertensionCheck(Mockito.<Long>any())).thenReturn(-3);
    when(iDRSDataRepo.getBenIdrsDetailsLast_3_Month(Mockito.<Long>any(), Mockito.<Timestamp>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualBenSymptomaticData = commonNurseServiceImpl.getBenSymptomaticData(1L);

    // Assert
    verify(iDRSDataRepo).getBenIdrsDetailsLast_3_Month(isA(Long.class), isA(Timestamp.class));
    verify(iDRSDataRepo).isDefectiveVisionCheck(isA(Long.class));
    verify(iDRSDataRepo).isDiabeticCheck(isA(Long.class));
    verify(iDRSDataRepo).isEpilepsyCheck(isA(Long.class));
    verify(iDRSDataRepo).isHypertensionCheck(isA(Long.class));
    assertEquals(
        "{\"questionariesData\":[],\"isHypertension\":false,\"isDefectiveVision\":true,\"isDiabetic\":true,\"isEpilepsy"
            + "\":true}",
        actualBenSymptomaticData);
  }

  @Test
  void testGetBenSymptomaticData9() throws Exception {
    // Arrange
    when(iDRSDataRepo.isDefectiveVisionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isDiabeticCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isEpilepsyCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isHypertensionCheck(Mockito.<Long>any())).thenReturn(null);
    when(iDRSDataRepo.getBenIdrsDetailsLast_3_Month(Mockito.<Long>any(), Mockito.<Timestamp>any()))
        .thenReturn(new ArrayList<>());

    // Act
    String actualBenSymptomaticData = commonNurseServiceImpl.getBenSymptomaticData(1L);

    // Assert
    verify(iDRSDataRepo).getBenIdrsDetailsLast_3_Month(isA(Long.class), isA(Timestamp.class));
    verify(iDRSDataRepo).isDefectiveVisionCheck(isA(Long.class));
    verify(iDRSDataRepo).isDiabeticCheck(isA(Long.class));
    verify(iDRSDataRepo).isEpilepsyCheck(isA(Long.class));
    verify(iDRSDataRepo).isHypertensionCheck(isA(Long.class));
    assertEquals(
        "{\"questionariesData\":[],\"isHypertension\":false,\"isDefectiveVision\":true,\"isDiabetic\":true,\"isEpilepsy"
            + "\":true}",
        actualBenSymptomaticData);
  }

  @Test
  void testGetBenSymptomaticData10() throws Exception {
    // Arrange
    IDRSData idrsData = new IDRSData();
    idrsData.setAnswer("questionariesData");
    idrsData.setBenVisitID(1L);
    idrsData.setBeneficiaryRegID(1L);
    idrsData.setConfirmArray(new String[]{"questionariesData"});
    idrsData.setConfirmedDisease("questionariesData");
    idrsData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    idrsData.setCreatedDate(mock(Timestamp.class));
    idrsData.setDeleted(true);
    idrsData.setDiseaseQuestionType("questionariesData");
    idrsData.setId(1L);
    idrsData.setIdrsDetails(new ArrayList<>());
    idrsData.setIdrsQuestionID(1);
    idrsData.setIdrsScore(1);
    idrsData.setIsDiabetic(true);
    idrsData.setLastModDate(mock(Timestamp.class));
    idrsData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    idrsData.setParkingPlaceID(1);
    idrsData.setProcessed("questionariesData");
    idrsData.setProviderServiceMapID(1);
    idrsData.setQuestion("questionariesData");
    idrsData.setQuestionArray(new IDRSData[]{new IDRSData()});
    idrsData.setSuspectArray(new String[]{"questionariesData"});
    idrsData.setSuspectDetails(new ArrayList<>());
    idrsData.setSuspectedDisease("questionariesData");
    idrsData.setVanID(1);
    idrsData.setVanSerialNo(2L);
    idrsData.setVehicalNo("questionariesData");
    idrsData.setVisitCode(2L);

    IDRSData idrsData2 = new IDRSData();
    idrsData2.setAnswer("questionariesData");
    idrsData2.setBenVisitID(1L);
    idrsData2.setBeneficiaryRegID(1L);
    idrsData2.setConfirmArray(new String[]{"questionariesData"});
    idrsData2.setConfirmedDisease("questionariesData");
    idrsData2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    idrsData2.setCreatedDate(mock(Timestamp.class));
    idrsData2.setDeleted(true);
    idrsData2.setDiseaseQuestionType("questionariesData");
    idrsData2.setId(1L);
    idrsData2.setIdrsDetails(new ArrayList<>());
    idrsData2.setIdrsQuestionID(1);
    idrsData2.setIdrsScore(1);
    idrsData2.setIsDiabetic(true);
    idrsData2.setLastModDate(mock(Timestamp.class));
    idrsData2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    idrsData2.setParkingPlaceID(1);
    idrsData2.setProcessed("questionariesData");
    idrsData2.setProviderServiceMapID(1);
    idrsData2.setQuestion("questionariesData");
    idrsData2.setQuestionArray(new IDRSData[]{idrsData});
    idrsData2.setSuspectArray(new String[]{"questionariesData"});
    idrsData2.setSuspectDetails(new ArrayList<>());
    idrsData2.setSuspectedDisease("questionariesData");
    idrsData2.setVanID(1);
    idrsData2.setVanSerialNo(2L);
    idrsData2.setVehicalNo("questionariesData");
    idrsData2.setVisitCode(2L);

    ArrayList<IDRSData> idrsDataList = new ArrayList<>();
    idrsDataList.add(idrsData2);
    when(iDRSDataRepo.isDefectiveVisionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isDiabeticCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isEpilepsyCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isHypertensionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.getBenIdrsDetailsLast_3_Month(Mockito.<Long>any(), Mockito.<Timestamp>any()))
        .thenReturn(idrsDataList);

    // Act
    String actualBenSymptomaticData = commonNurseServiceImpl.getBenSymptomaticData(1L);

    // Assert
    verify(iDRSDataRepo).getBenIdrsDetailsLast_3_Month(isA(Long.class), isA(Timestamp.class));
    verify(iDRSDataRepo).isDefectiveVisionCheck(isA(Long.class));
    verify(iDRSDataRepo).isDiabeticCheck(isA(Long.class));
    verify(iDRSDataRepo).isEpilepsyCheck(isA(Long.class));
    verify(iDRSDataRepo).isHypertensionCheck(isA(Long.class));
    assertEquals(
        "{\"questionariesData\":[{\"qANS\":\"questionariesData\",\"qID\":1}],\"suspectedDisease\":\"questionariesData\","
            + "\"confirmedDisease\":\"questionariesData\",\"isHypertension\":true,\"isDefectiveVision\":true,\"isDiabetic\""
            + ":true,\"isEpilepsy\":true}",
        actualBenSymptomaticData);
  }

  @Test
  void testGetBenSymptomaticData11() throws Exception {
    // Arrange
    IDRSData idrsData = new IDRSData();
    idrsData.setAnswer("questionariesData");
    idrsData.setBenVisitID(1L);
    idrsData.setBeneficiaryRegID(1L);
    idrsData.setConfirmArray(new String[]{"questionariesData"});
    idrsData.setConfirmedDisease("questionariesData");
    idrsData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    idrsData.setCreatedDate(mock(Timestamp.class));
    idrsData.setDeleted(true);
    idrsData.setDiseaseQuestionType("questionariesData");
    idrsData.setId(1L);
    idrsData.setIdrsDetails(new ArrayList<>());
    idrsData.setIdrsQuestionID(1);
    idrsData.setIdrsScore(1);
    idrsData.setIsDiabetic(true);
    idrsData.setLastModDate(mock(Timestamp.class));
    idrsData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    idrsData.setParkingPlaceID(1);
    idrsData.setProcessed("questionariesData");
    idrsData.setProviderServiceMapID(1);
    idrsData.setQuestion("questionariesData");
    idrsData.setQuestionArray(new IDRSData[]{new IDRSData()});
    idrsData.setSuspectArray(new String[]{"questionariesData"});
    idrsData.setSuspectDetails(new ArrayList<>());
    idrsData.setSuspectedDisease("questionariesData");
    idrsData.setVanID(1);
    idrsData.setVanSerialNo(2L);
    idrsData.setVehicalNo("questionariesData");
    idrsData.setVisitCode(2L);

    IDRSData idrsData2 = new IDRSData();
    idrsData2.setAnswer("questionariesData");
    idrsData2.setBenVisitID(1L);
    idrsData2.setBeneficiaryRegID(1L);
    idrsData2.setConfirmArray(new String[]{"questionariesData"});
    idrsData2.setConfirmedDisease("questionariesData");
    idrsData2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    idrsData2.setCreatedDate(mock(Timestamp.class));
    idrsData2.setDeleted(true);
    idrsData2.setDiseaseQuestionType("questionariesData");
    idrsData2.setId(1L);
    idrsData2.setIdrsDetails(new ArrayList<>());
    idrsData2.setIdrsQuestionID(1);
    idrsData2.setIdrsScore(1);
    idrsData2.setIsDiabetic(true);
    idrsData2.setLastModDate(mock(Timestamp.class));
    idrsData2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    idrsData2.setParkingPlaceID(1);
    idrsData2.setProcessed("questionariesData");
    idrsData2.setProviderServiceMapID(1);
    idrsData2.setQuestion("questionariesData");
    idrsData2.setQuestionArray(new IDRSData[]{idrsData});
    idrsData2.setSuspectArray(new String[]{"questionariesData"});
    idrsData2.setSuspectDetails(new ArrayList<>());
    idrsData2.setSuspectedDisease("questionariesData");
    idrsData2.setVanID(1);
    idrsData2.setVanSerialNo(2L);
    idrsData2.setVehicalNo("questionariesData");
    idrsData2.setVisitCode(2L);

    IDRSData idrsData3 = new IDRSData();
    idrsData3.setAnswer("qID");
    idrsData3.setBenVisitID(1L);
    idrsData3.setBeneficiaryRegID(1L);
    idrsData3.setConfirmArray(new String[]{"qID"});
    idrsData3.setConfirmedDisease("qID");
    idrsData3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    idrsData3.setCreatedDate(mock(Timestamp.class));
    idrsData3.setDeleted(true);
    idrsData3.setDiseaseQuestionType("qID");
    idrsData3.setId(1L);
    idrsData3.setIdrsDetails(new ArrayList<>());
    idrsData3.setIdrsQuestionID(1);
    idrsData3.setIdrsScore(1);
    idrsData3.setIsDiabetic(true);
    idrsData3.setLastModDate(mock(Timestamp.class));
    idrsData3.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    idrsData3.setParkingPlaceID(1);
    idrsData3.setProcessed("qID");
    idrsData3.setProviderServiceMapID(1);
    idrsData3.setQuestion("qID");
    idrsData3.setQuestionArray(new IDRSData[]{new IDRSData()});
    idrsData3.setSuspectArray(new String[]{"qID"});
    idrsData3.setSuspectDetails(new ArrayList<>());
    idrsData3.setSuspectedDisease("qID");
    idrsData3.setVanID(1);
    idrsData3.setVanSerialNo(2L);
    idrsData3.setVehicalNo("qID");
    idrsData3.setVisitCode(2L);

    IDRSData idrsData4 = new IDRSData();
    idrsData4.setAnswer("qANS");
    idrsData4.setBenVisitID(2L);
    idrsData4.setBeneficiaryRegID(2L);
    idrsData4.setConfirmArray(new String[]{"qID"});
    idrsData4.setConfirmedDisease("qANS");
    idrsData4.setCreatedBy("qID");
    idrsData4.setCreatedDate(mock(Timestamp.class));
    idrsData4.setDeleted(false);
    idrsData4.setDiseaseQuestionType("qANS");
    idrsData4.setId(2L);
    idrsData4.setIdrsDetails(new ArrayList<>());
    idrsData4.setIdrsQuestionID(2);
    idrsData4.setIdrsScore(2);
    idrsData4.setIsDiabetic(false);
    idrsData4.setLastModDate(mock(Timestamp.class));
    idrsData4.setModifiedBy("qID");
    idrsData4.setParkingPlaceID(2);
    idrsData4.setProcessed("qANS");
    idrsData4.setProviderServiceMapID(2);
    idrsData4.setQuestion("qANS");
    idrsData4.setQuestionArray(new IDRSData[]{idrsData3});
    idrsData4.setSuspectArray(new String[]{"qID"});
    idrsData4.setSuspectDetails(new ArrayList<>());
    idrsData4.setSuspectedDisease("qANS");
    idrsData4.setVanID(2);
    idrsData4.setVanSerialNo(-3L);
    idrsData4.setVehicalNo("qANS");
    idrsData4.setVisitCode(-3L);

    ArrayList<IDRSData> idrsDataList = new ArrayList<>();
    idrsDataList.add(idrsData4);
    idrsDataList.add(idrsData2);
    when(iDRSDataRepo.isDefectiveVisionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isDiabeticCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isEpilepsyCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.isHypertensionCheck(Mockito.<Long>any())).thenReturn(1);
    when(iDRSDataRepo.getBenIdrsDetailsLast_3_Month(Mockito.<Long>any(), Mockito.<Timestamp>any()))
        .thenReturn(idrsDataList);

    // Act
    String actualBenSymptomaticData = commonNurseServiceImpl.getBenSymptomaticData(1L);

    // Assert
    verify(iDRSDataRepo).getBenIdrsDetailsLast_3_Month(isA(Long.class), isA(Timestamp.class));
    verify(iDRSDataRepo).isDefectiveVisionCheck(isA(Long.class));
    verify(iDRSDataRepo).isDiabeticCheck(isA(Long.class));
    verify(iDRSDataRepo).isEpilepsyCheck(isA(Long.class));
    verify(iDRSDataRepo).isHypertensionCheck(isA(Long.class));
    assertEquals(
        "{\"questionariesData\":[{\"qANS\":\"qANS\",\"qID\":2}],\"suspectedDisease\":\"qANS\",\"confirmedDisease\":\"qANS\","
            + "\"isHypertension\":true,\"isDefectiveVision\":true,\"isDiabetic\":true,\"isEpilepsy\":true}",
        actualBenSymptomaticData);
  }

  @Test
  void testGetBenPreviousDiabetesData() throws Exception {
    // Arrange
    when(iDRSDataRepo.getBenPreviousDiabetesDetails(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualBenPreviousDiabetesData = commonNurseServiceImpl.getBenPreviousDiabetesData(1L);

    // Assert
    verify(iDRSDataRepo).getBenPreviousDiabetesDetails(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"createdDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"visitCode"
            + "\",\"columnName\":\"Visit Code\"},{\"keyName\":\"question\",\"columnName\":\"Question\"},{\"keyName\":\"answer\","
            + "\"columnName\":\"Answer\"}]}",
        actualBenPreviousDiabetesData);
  }

  @Test
  void testGetMmuNurseWorkListNew() {
    // Arrange
    when(beneficiaryFlowStatusRepo.getMmuNurseWorklistNew(Mockito.<Integer>any(), Mockito.<Integer>any(),
        Mockito.<Timestamp>any())).thenReturn(new ArrayList<>());

    // Act
    String actualMmuNurseWorkListNew = commonNurseServiceImpl.getMmuNurseWorkListNew(1, 1);

    // Assert
    verify(beneficiaryFlowStatusRepo).getMmuNurseWorklistNew(isA(Integer.class), isA(Integer.class),
        isA(Timestamp.class));
    assertEquals("[]", actualMmuNurseWorkListNew);
  }

  @Test
  void testGetBenPreviousReferralData() throws Exception {
    // Arrange
    when(iDRSDataRepo.getBenPreviousReferredDetails(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    String actualBenPreviousReferralData = commonNurseServiceImpl.getBenPreviousReferralData(1L);

    // Assert
    verify(iDRSDataRepo).getBenPreviousReferredDetails(isA(Long.class));
    assertEquals(
        "{\"data\":[],\"columns\":[{\"keyName\":\"createdDate\",\"columnName\":\"Date of Referral\"},{\"keyName\":\"visitCode"
            + "\",\"columnName\":\"Visit Code\"},{\"keyName\":\"suspectedDisease\",\"columnName\":\"Suspected Diseases\"}]}",
        actualBenPreviousReferralData);
  }

  @Test
  void testFetchProviderSpecificdata() throws IEMRException {
    // Arrange, Act and Assert
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.fetchProviderSpecificdata("Request"));
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.fetchProviderSpecificdata(""));
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.fetchProviderSpecificdata("42"));
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.fetchProviderSpecificdata(","));
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.fetchProviderSpecificdata("N"));
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.fetchProviderSpecificdata("Tablet"));
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.fetchProviderSpecificdata("\\|\\|"));
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.fetchProviderSpecificdata("42,"));
  }

  @Test
  void testGetInvestigationData() throws IEMRException {
    // Arrange
    BenPhysicalVitalDetail benPhysicalVitalDetail = new BenPhysicalVitalDetail();
    benPhysicalVitalDetail.setAverageDiastolicBP((short) 1);
    benPhysicalVitalDetail.setAverageSystolicBP((short) 1);
    benPhysicalVitalDetail.setBenVisitID(1L);
    benPhysicalVitalDetail.setBeneficiaryRegID(1L);
    benPhysicalVitalDetail.setBloodGlucose_2hr_PP((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_Fasting((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_NotSpecified((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_Random((short) 1);
    benPhysicalVitalDetail.setBloodPressureStatus("Blood Pressure Status");
    benPhysicalVitalDetail.setBloodPressureStatusID((short) 1);
    benPhysicalVitalDetail.setCapillaryRefillTime("Capillary Refill Time");
    benPhysicalVitalDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benPhysicalVitalDetail.setCreatedDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setDeleted(true);
    benPhysicalVitalDetail.setDiabeticStatus("Diabetic Status");
    benPhysicalVitalDetail.setDiabeticStatusID((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_1stReading((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail.setHemoglobin(10.0d);
    benPhysicalVitalDetail.setID(1L);
    benPhysicalVitalDetail.setLastModDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benPhysicalVitalDetail.setParkingPlaceID(1);
    benPhysicalVitalDetail.setProcessed("Processed");
    benPhysicalVitalDetail.setProviderServiceMapID(1);
    benPhysicalVitalDetail.setPulseRate((short) 1);
    benPhysicalVitalDetail.setRbsTestRemarks("Rbs Test Remarks");
    benPhysicalVitalDetail.setRbsTestResult("Rbs Test Result");
    benPhysicalVitalDetail.setReservedForChange("Reserved For Change");
    benPhysicalVitalDetail.setRespiratoryRate((short) 1);
    benPhysicalVitalDetail.setSyncedBy("Synced By");
    benPhysicalVitalDetail.setSyncedDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setSystolicBP_1stReading((short) 1);
    benPhysicalVitalDetail.setSystolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail.setSystolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail.setTemperature(10.0d);
    benPhysicalVitalDetail.setVanID(1);
    benPhysicalVitalDetail.setVanSerialNo(1L);
    benPhysicalVitalDetail.setVehicalNo("Vehical No");
    benPhysicalVitalDetail.setVisitCode(1L);
    benPhysicalVitalDetail.setsPO2("S PO2");
    when(benPhysicalVitalRepo.getBenPhysicalVitalDetail(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(benPhysicalVitalDetail);
    when(labTestOrderDetailRepo.getLabTestOrderDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    ProviderSpecificRequest request = new ProviderSpecificRequest();
    request.setBenRegID(1L);
    request.setBenVisitID("Ben Visit ID");
    request.setFetchMMUDataFor("Fetch MMUData For");
    request.setVisitCode(1L);

    // Act
    String actualInvestigationData = commonNurseServiceImpl.getInvestigationData(request);

    // Assert
    verify(benPhysicalVitalRepo).getBenPhysicalVitalDetail(isA(Long.class), isA(Long.class));
    verify(labTestOrderDetailRepo).getLabTestOrderDetails(isA(Long.class), isA(Long.class));
    assertEquals(
        "{\"data\":{\"beneficiaryRegID\":1,\"benVisitID\":1,\"visitCode\":1,\"providerServiceMapID\":1,\"laboratoryList\""
            + ":[{\"procedureName\":\"RBS Test\"}]},\"columns\":[{\"keyName\":\"procedureName\",\"columnName\":\"Test Name\"}]}",
        actualInvestigationData);
  }

  @Test
  void testGetInvestigationData2() throws IEMRException {
    // Arrange
    BenPhysicalVitalDetail benPhysicalVitalDetail = mock(BenPhysicalVitalDetail.class);
    when(benPhysicalVitalDetail.getProviderServiceMapID()).thenReturn(1);
    when(benPhysicalVitalDetail.getBenVisitID()).thenReturn(1L);
    when(benPhysicalVitalDetail.getBeneficiaryRegID()).thenReturn(1L);
    when(benPhysicalVitalDetail.getVisitCode()).thenReturn(1L);
    when(benPhysicalVitalDetail.getRbsTestResult()).thenReturn("Rbs Test Result");
    doNothing().when(benPhysicalVitalDetail).setAverageDiastolicBP(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setAverageSystolicBP(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benPhysicalVitalDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benPhysicalVitalDetail).setBloodGlucose_2hr_PP(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setBloodGlucose_Fasting(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setBloodGlucose_NotSpecified(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setBloodGlucose_Random(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setBloodPressureStatus(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setBloodPressureStatusID(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setCapillaryRefillTime(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benPhysicalVitalDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benPhysicalVitalDetail).setDiabeticStatus(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setDiabeticStatusID(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setDiastolicBP_1stReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setDiastolicBP_2ndReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setDiastolicBP_3rdReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setHemoglobin(Mockito.<Double>any());
    doNothing().when(benPhysicalVitalDetail).setID(Mockito.<Long>any());
    doNothing().when(benPhysicalVitalDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benPhysicalVitalDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benPhysicalVitalDetail).setProcessed(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benPhysicalVitalDetail).setPulseRate(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setRbsTestRemarks(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setRbsTestResult(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setRespiratoryRate(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benPhysicalVitalDetail).setSystolicBP_1stReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setSystolicBP_2ndReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setSystolicBP_3rdReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setTemperature(Mockito.<Double>any());
    doNothing().when(benPhysicalVitalDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(benPhysicalVitalDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benPhysicalVitalDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setVisitCode(Mockito.<Long>any());
    doNothing().when(benPhysicalVitalDetail).setsPO2(Mockito.<String>any());
    benPhysicalVitalDetail.setAverageDiastolicBP((short) 1);
    benPhysicalVitalDetail.setAverageSystolicBP((short) 1);
    benPhysicalVitalDetail.setBenVisitID(1L);
    benPhysicalVitalDetail.setBeneficiaryRegID(1L);
    benPhysicalVitalDetail.setBloodGlucose_2hr_PP((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_Fasting((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_NotSpecified((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_Random((short) 1);
    benPhysicalVitalDetail.setBloodPressureStatus("Blood Pressure Status");
    benPhysicalVitalDetail.setBloodPressureStatusID((short) 1);
    benPhysicalVitalDetail.setCapillaryRefillTime("Capillary Refill Time");
    benPhysicalVitalDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benPhysicalVitalDetail.setCreatedDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setDeleted(true);
    benPhysicalVitalDetail.setDiabeticStatus("Diabetic Status");
    benPhysicalVitalDetail.setDiabeticStatusID((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_1stReading((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail.setHemoglobin(10.0d);
    benPhysicalVitalDetail.setID(1L);
    benPhysicalVitalDetail.setLastModDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benPhysicalVitalDetail.setParkingPlaceID(1);
    benPhysicalVitalDetail.setProcessed("Processed");
    benPhysicalVitalDetail.setProviderServiceMapID(1);
    benPhysicalVitalDetail.setPulseRate((short) 1);
    benPhysicalVitalDetail.setRbsTestRemarks("Rbs Test Remarks");
    benPhysicalVitalDetail.setRbsTestResult("Rbs Test Result");
    benPhysicalVitalDetail.setReservedForChange("Reserved For Change");
    benPhysicalVitalDetail.setRespiratoryRate((short) 1);
    benPhysicalVitalDetail.setSyncedBy("Synced By");
    benPhysicalVitalDetail.setSyncedDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setSystolicBP_1stReading((short) 1);
    benPhysicalVitalDetail.setSystolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail.setSystolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail.setTemperature(10.0d);
    benPhysicalVitalDetail.setVanID(1);
    benPhysicalVitalDetail.setVanSerialNo(1L);
    benPhysicalVitalDetail.setVehicalNo("Vehical No");
    benPhysicalVitalDetail.setVisitCode(1L);
    benPhysicalVitalDetail.setsPO2("S PO2");
    when(benPhysicalVitalRepo.getBenPhysicalVitalDetail(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(benPhysicalVitalDetail);
    when(labTestOrderDetailRepo.getLabTestOrderDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    ProviderSpecificRequest request = new ProviderSpecificRequest();
    request.setBenRegID(1L);
    request.setBenVisitID("Ben Visit ID");
    request.setFetchMMUDataFor("Fetch MMUData For");
    request.setVisitCode(1L);

    // Act
    String actualInvestigationData = commonNurseServiceImpl.getInvestigationData(request);

    // Assert
    verify(benPhysicalVitalDetail).getBenVisitID();
    verify(benPhysicalVitalDetail).getBeneficiaryRegID();
    verify(benPhysicalVitalDetail).getProviderServiceMapID();
    verify(benPhysicalVitalDetail, atLeast(1)).getRbsTestResult();
    verify(benPhysicalVitalDetail).getVisitCode();
    verify(benPhysicalVitalDetail).setAverageDiastolicBP(isA(Short.class));
    verify(benPhysicalVitalDetail).setAverageSystolicBP(isA(Short.class));
    verify(benPhysicalVitalDetail).setBenVisitID(isA(Long.class));
    verify(benPhysicalVitalDetail).setBeneficiaryRegID(isA(Long.class));
    verify(benPhysicalVitalDetail).setBloodGlucose_2hr_PP(isA(Short.class));
    verify(benPhysicalVitalDetail).setBloodGlucose_Fasting(isA(Short.class));
    verify(benPhysicalVitalDetail).setBloodGlucose_NotSpecified(isA(Short.class));
    verify(benPhysicalVitalDetail).setBloodGlucose_Random(isA(Short.class));
    verify(benPhysicalVitalDetail).setBloodPressureStatus(eq("Blood Pressure Status"));
    verify(benPhysicalVitalDetail).setBloodPressureStatusID(isA(Short.class));
    verify(benPhysicalVitalDetail).setCapillaryRefillTime(eq("Capillary Refill Time"));
    verify(benPhysicalVitalDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benPhysicalVitalDetail).setCreatedDate(isA(Timestamp.class));
    verify(benPhysicalVitalDetail).setDeleted(isA(Boolean.class));
    verify(benPhysicalVitalDetail).setDiabeticStatus(eq("Diabetic Status"));
    verify(benPhysicalVitalDetail).setDiabeticStatusID(isA(Short.class));
    verify(benPhysicalVitalDetail).setDiastolicBP_1stReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setDiastolicBP_2ndReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setDiastolicBP_3rdReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setHemoglobin(isA(Double.class));
    verify(benPhysicalVitalDetail).setID(isA(Long.class));
    verify(benPhysicalVitalDetail).setLastModDate(isA(Timestamp.class));
    verify(benPhysicalVitalDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benPhysicalVitalDetail).setParkingPlaceID(isA(Integer.class));
    verify(benPhysicalVitalDetail).setProcessed(eq("Processed"));
    verify(benPhysicalVitalDetail).setProviderServiceMapID(isA(Integer.class));
    verify(benPhysicalVitalDetail).setPulseRate(isA(Short.class));
    verify(benPhysicalVitalDetail).setRbsTestRemarks(eq("Rbs Test Remarks"));
    verify(benPhysicalVitalDetail).setRbsTestResult(eq("Rbs Test Result"));
    verify(benPhysicalVitalDetail).setReservedForChange(eq("Reserved For Change"));
    verify(benPhysicalVitalDetail).setRespiratoryRate(isA(Short.class));
    verify(benPhysicalVitalDetail).setSyncedBy(eq("Synced By"));
    verify(benPhysicalVitalDetail).setSyncedDate(isA(Timestamp.class));
    verify(benPhysicalVitalDetail).setSystolicBP_1stReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setSystolicBP_2ndReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setSystolicBP_3rdReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setTemperature(isA(Double.class));
    verify(benPhysicalVitalDetail).setVanID(isA(Integer.class));
    verify(benPhysicalVitalDetail).setVanSerialNo(isA(Long.class));
    verify(benPhysicalVitalDetail).setVehicalNo(eq("Vehical No"));
    verify(benPhysicalVitalDetail).setVisitCode(isA(Long.class));
    verify(benPhysicalVitalDetail).setsPO2(eq("S PO2"));
    verify(benPhysicalVitalRepo).getBenPhysicalVitalDetail(isA(Long.class), isA(Long.class));
    verify(labTestOrderDetailRepo).getLabTestOrderDetails(isA(Long.class), isA(Long.class));
    assertEquals(
        "{\"data\":{\"beneficiaryRegID\":1,\"benVisitID\":1,\"visitCode\":1,\"providerServiceMapID\":1,\"laboratoryList\""
            + ":[{\"procedureName\":\"RBS Test\"}]},\"columns\":[{\"keyName\":\"procedureName\",\"columnName\":\"Test Name\"}]}",
        actualInvestigationData);
  }

  @Test
  void testGetInvestigationData3() throws IEMRException {
    // Arrange
    BenPhysicalVitalDetail benPhysicalVitalDetail = mock(BenPhysicalVitalDetail.class);
    when(benPhysicalVitalDetail.getRbsTestResult()).thenReturn(null);
    doNothing().when(benPhysicalVitalDetail).setAverageDiastolicBP(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setAverageSystolicBP(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setBenVisitID(Mockito.<Long>any());
    doNothing().when(benPhysicalVitalDetail).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(benPhysicalVitalDetail).setBloodGlucose_2hr_PP(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setBloodGlucose_Fasting(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setBloodGlucose_NotSpecified(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setBloodGlucose_Random(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setBloodPressureStatus(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setBloodPressureStatusID(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setCapillaryRefillTime(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setCreatedBy(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(benPhysicalVitalDetail).setDeleted(Mockito.<Boolean>any());
    doNothing().when(benPhysicalVitalDetail).setDiabeticStatus(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setDiabeticStatusID(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setDiastolicBP_1stReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setDiastolicBP_2ndReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setDiastolicBP_3rdReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setHemoglobin(Mockito.<Double>any());
    doNothing().when(benPhysicalVitalDetail).setID(Mockito.<Long>any());
    doNothing().when(benPhysicalVitalDetail).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(benPhysicalVitalDetail).setModifiedBy(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(benPhysicalVitalDetail).setProcessed(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(benPhysicalVitalDetail).setPulseRate(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setRbsTestRemarks(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setRbsTestResult(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setReservedForChange(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setRespiratoryRate(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setSyncedBy(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(benPhysicalVitalDetail).setSystolicBP_1stReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setSystolicBP_2ndReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setSystolicBP_3rdReading(Mockito.<Short>any());
    doNothing().when(benPhysicalVitalDetail).setTemperature(Mockito.<Double>any());
    doNothing().when(benPhysicalVitalDetail).setVanID(Mockito.<Integer>any());
    doNothing().when(benPhysicalVitalDetail).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(benPhysicalVitalDetail).setVehicalNo(Mockito.<String>any());
    doNothing().when(benPhysicalVitalDetail).setVisitCode(Mockito.<Long>any());
    doNothing().when(benPhysicalVitalDetail).setsPO2(Mockito.<String>any());
    benPhysicalVitalDetail.setAverageDiastolicBP((short) 1);
    benPhysicalVitalDetail.setAverageSystolicBP((short) 1);
    benPhysicalVitalDetail.setBenVisitID(1L);
    benPhysicalVitalDetail.setBeneficiaryRegID(1L);
    benPhysicalVitalDetail.setBloodGlucose_2hr_PP((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_Fasting((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_NotSpecified((short) 1);
    benPhysicalVitalDetail.setBloodGlucose_Random((short) 1);
    benPhysicalVitalDetail.setBloodPressureStatus("Blood Pressure Status");
    benPhysicalVitalDetail.setBloodPressureStatusID((short) 1);
    benPhysicalVitalDetail.setCapillaryRefillTime("Capillary Refill Time");
    benPhysicalVitalDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    benPhysicalVitalDetail.setCreatedDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setDeleted(true);
    benPhysicalVitalDetail.setDiabeticStatus("Diabetic Status");
    benPhysicalVitalDetail.setDiabeticStatusID((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_1stReading((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail.setDiastolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail.setHemoglobin(10.0d);
    benPhysicalVitalDetail.setID(1L);
    benPhysicalVitalDetail.setLastModDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    benPhysicalVitalDetail.setParkingPlaceID(1);
    benPhysicalVitalDetail.setProcessed("Processed");
    benPhysicalVitalDetail.setProviderServiceMapID(1);
    benPhysicalVitalDetail.setPulseRate((short) 1);
    benPhysicalVitalDetail.setRbsTestRemarks("Rbs Test Remarks");
    benPhysicalVitalDetail.setRbsTestResult("Rbs Test Result");
    benPhysicalVitalDetail.setReservedForChange("Reserved For Change");
    benPhysicalVitalDetail.setRespiratoryRate((short) 1);
    benPhysicalVitalDetail.setSyncedBy("Synced By");
    benPhysicalVitalDetail.setSyncedDate(mock(Timestamp.class));
    benPhysicalVitalDetail.setSystolicBP_1stReading((short) 1);
    benPhysicalVitalDetail.setSystolicBP_2ndReading((short) 1);
    benPhysicalVitalDetail.setSystolicBP_3rdReading((short) 1);
    benPhysicalVitalDetail.setTemperature(10.0d);
    benPhysicalVitalDetail.setVanID(1);
    benPhysicalVitalDetail.setVanSerialNo(1L);
    benPhysicalVitalDetail.setVehicalNo("Vehical No");
    benPhysicalVitalDetail.setVisitCode(1L);
    benPhysicalVitalDetail.setsPO2("S PO2");
    when(benPhysicalVitalRepo.getBenPhysicalVitalDetail(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(benPhysicalVitalDetail);
    when(labTestOrderDetailRepo.getLabTestOrderDetails(Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    ProviderSpecificRequest request = new ProviderSpecificRequest();
    request.setBenRegID(1L);
    request.setBenVisitID("Ben Visit ID");
    request.setFetchMMUDataFor("Fetch MMUData For");
    request.setVisitCode(1L);

    // Act
    String actualInvestigationData = commonNurseServiceImpl.getInvestigationData(request);

    // Assert
    verify(benPhysicalVitalDetail).getRbsTestResult();
    verify(benPhysicalVitalDetail).setAverageDiastolicBP(isA(Short.class));
    verify(benPhysicalVitalDetail).setAverageSystolicBP(isA(Short.class));
    verify(benPhysicalVitalDetail).setBenVisitID(isA(Long.class));
    verify(benPhysicalVitalDetail).setBeneficiaryRegID(isA(Long.class));
    verify(benPhysicalVitalDetail).setBloodGlucose_2hr_PP(isA(Short.class));
    verify(benPhysicalVitalDetail).setBloodGlucose_Fasting(isA(Short.class));
    verify(benPhysicalVitalDetail).setBloodGlucose_NotSpecified(isA(Short.class));
    verify(benPhysicalVitalDetail).setBloodGlucose_Random(isA(Short.class));
    verify(benPhysicalVitalDetail).setBloodPressureStatus(eq("Blood Pressure Status"));
    verify(benPhysicalVitalDetail).setBloodPressureStatusID(isA(Short.class));
    verify(benPhysicalVitalDetail).setCapillaryRefillTime(eq("Capillary Refill Time"));
    verify(benPhysicalVitalDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(benPhysicalVitalDetail).setCreatedDate(isA(Timestamp.class));
    verify(benPhysicalVitalDetail).setDeleted(isA(Boolean.class));
    verify(benPhysicalVitalDetail).setDiabeticStatus(eq("Diabetic Status"));
    verify(benPhysicalVitalDetail).setDiabeticStatusID(isA(Short.class));
    verify(benPhysicalVitalDetail).setDiastolicBP_1stReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setDiastolicBP_2ndReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setDiastolicBP_3rdReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setHemoglobin(isA(Double.class));
    verify(benPhysicalVitalDetail).setID(isA(Long.class));
    verify(benPhysicalVitalDetail).setLastModDate(isA(Timestamp.class));
    verify(benPhysicalVitalDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(benPhysicalVitalDetail).setParkingPlaceID(isA(Integer.class));
    verify(benPhysicalVitalDetail).setProcessed(eq("Processed"));
    verify(benPhysicalVitalDetail).setProviderServiceMapID(isA(Integer.class));
    verify(benPhysicalVitalDetail).setPulseRate(isA(Short.class));
    verify(benPhysicalVitalDetail).setRbsTestRemarks(eq("Rbs Test Remarks"));
    verify(benPhysicalVitalDetail).setRbsTestResult(eq("Rbs Test Result"));
    verify(benPhysicalVitalDetail).setReservedForChange(eq("Reserved For Change"));
    verify(benPhysicalVitalDetail).setRespiratoryRate(isA(Short.class));
    verify(benPhysicalVitalDetail).setSyncedBy(eq("Synced By"));
    verify(benPhysicalVitalDetail).setSyncedDate(isA(Timestamp.class));
    verify(benPhysicalVitalDetail).setSystolicBP_1stReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setSystolicBP_2ndReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setSystolicBP_3rdReading(isA(Short.class));
    verify(benPhysicalVitalDetail).setTemperature(isA(Double.class));
    verify(benPhysicalVitalDetail).setVanID(isA(Integer.class));
    verify(benPhysicalVitalDetail).setVanSerialNo(isA(Long.class));
    verify(benPhysicalVitalDetail).setVehicalNo(eq("Vehical No"));
    verify(benPhysicalVitalDetail).setVisitCode(isA(Long.class));
    verify(benPhysicalVitalDetail).setsPO2(eq("S PO2"));
    verify(benPhysicalVitalRepo).getBenPhysicalVitalDetail(isA(Long.class), isA(Long.class));
    verify(labTestOrderDetailRepo).getLabTestOrderDetails(isA(Long.class), isA(Long.class));
    assertEquals("{\"data\":{},\"columns\":[{\"keyName\":\"procedureName\",\"columnName\":\"Test Name\"}]}",
        actualInvestigationData);
  }

  @Test
  void testCalculateBMIStatus() throws IEMRException {
    // Arrange, Act and Assert
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.calculateBMIStatus("Request"));
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.calculateBMIStatus("yyyy-MM-dd'T'HH:mm:ss.SSS"));
    assertThrows(IEMRException.class,
        () -> commonNurseServiceImpl.calculateBMIStatus("Error while calculating BMI status:"));
    assertEquals("{\"bmiStatus\":\"\"}", commonNurseServiceImpl.calculateBMIStatus(""));
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.calculateBMIStatus("42"));
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.calculateBMIStatus(","));
    assertEquals("{\"bmiStatus\":\"\"}", commonNurseServiceImpl.calculateBMIStatus(null));
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.calculateBMIStatus("N"));
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.calculateBMIStatus("Tablet"));
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.calculateBMIStatus("42,"));
    assertThrows(IEMRException.class,
        () -> commonNurseServiceImpl.calculateBMIStatus("42Error while calculating BMI status:"));
  }

  @Test
  void testSaveOralExamination() throws IEMRException {
    // Arrange
    OralExamination oralExamination = new OralExamination();
    oralExamination.setBenVisitID(1L);
    oralExamination.setBeneficiaryRegID(1L);
    oralExamination.setChronicBurningSensation(true);
    oralExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    oralExamination.setCreatedDate(mock(Timestamp.class));
    oralExamination.setDeleted(true);
    oralExamination.setID(1L);
    oralExamination.setLastModDate(mock(Timestamp.class));
    oralExamination.setLimitedMouthOpening("Limited Mouth Opening");
    oralExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    oralExamination.setObservation("Observation");
    oralExamination.setParkingPlaceID(1);
    oralExamination.setPreMalignantLesionType("Pre Malignant Lesion Type");
    oralExamination.setPreMalignantLesionTypeList(new String[]{"Pre Malignant Lesion Type List"});
    oralExamination.setPremalignantLesions(true);
    oralExamination.setProcessed("Processed");
    oralExamination.setProlongedIrritation(true);
    oralExamination.setProviderServiceMapID(1);
    oralExamination.setReservedForChange("Reserved For Change");
    oralExamination.setSyncedBy("Synced By");
    oralExamination.setSyncedDate(mock(Timestamp.class));
    oralExamination.setVanID(1);
    oralExamination.setVanSerialNo(1L);
    oralExamination.setVehicalNo("Vehical No");
    oralExamination.setVisitCode(1L);
    when(oralExaminationRepo.save(Mockito.<OralExamination>any())).thenReturn(oralExamination);

    OralExamination oralDetails = new OralExamination();
    oralDetails.setBenVisitID(1L);
    oralDetails.setBeneficiaryRegID(1L);
    oralDetails.setChronicBurningSensation(true);
    oralDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    oralDetails.setCreatedDate(mock(Timestamp.class));
    oralDetails.setDeleted(true);
    oralDetails.setID(1L);
    oralDetails.setLastModDate(mock(Timestamp.class));
    oralDetails.setLimitedMouthOpening("Limited Mouth Opening");
    oralDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    oralDetails.setObservation("Observation");
    oralDetails.setParkingPlaceID(1);
    oralDetails.setPreMalignantLesionType("Pre Malignant Lesion Type");
    oralDetails.setPreMalignantLesionTypeList(new String[]{"Pre Malignant Lesion Type List"});
    oralDetails.setPremalignantLesions(true);
    oralDetails.setProcessed("Processed");
    oralDetails.setProlongedIrritation(true);
    oralDetails.setProviderServiceMapID(1);
    oralDetails.setReservedForChange("Reserved For Change");
    oralDetails.setSyncedBy("Synced By");
    oralDetails.setSyncedDate(mock(Timestamp.class));
    oralDetails.setVanID(1);
    oralDetails.setVanSerialNo(1L);
    oralDetails.setVehicalNo("Vehical No");
    oralDetails.setVisitCode(1L);

    // Act
    Long actualSaveOralExaminationResult = commonNurseServiceImpl.saveOralExamination(oralDetails);

    // Assert
    verify(oralExaminationRepo).save(isA(OralExamination.class));
    assertEquals("Pre Malignant Lesion Type List||", oralDetails.getPreMalignantLesionType());
    assertEquals(1L, actualSaveOralExaminationResult.longValue());
  }

  @Test
  void testSaveOralExamination2() throws IEMRException {
    // Arrange
    OralExamination oralExamination = mock(OralExamination.class);
    when(oralExamination.getID()).thenReturn(-1L);
    doNothing().when(oralExamination).setBenVisitID(Mockito.<Long>any());
    doNothing().when(oralExamination).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(oralExamination).setChronicBurningSensation(Mockito.<Boolean>any());
    doNothing().when(oralExamination).setCreatedBy(Mockito.<String>any());
    doNothing().when(oralExamination).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(oralExamination).setDeleted(Mockito.<Boolean>any());
    doNothing().when(oralExamination).setID(Mockito.<Long>any());
    doNothing().when(oralExamination).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(oralExamination).setLimitedMouthOpening(Mockito.<String>any());
    doNothing().when(oralExamination).setModifiedBy(Mockito.<String>any());
    doNothing().when(oralExamination).setObservation(Mockito.<String>any());
    doNothing().when(oralExamination).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(oralExamination).setPreMalignantLesionType(Mockito.<String>any());
    doNothing().when(oralExamination).setPreMalignantLesionTypeList(Mockito.<String[]>any());
    doNothing().when(oralExamination).setPremalignantLesions(Mockito.<Boolean>any());
    doNothing().when(oralExamination).setProcessed(Mockito.<String>any());
    doNothing().when(oralExamination).setProlongedIrritation(Mockito.<Boolean>any());
    doNothing().when(oralExamination).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(oralExamination).setReservedForChange(Mockito.<String>any());
    doNothing().when(oralExamination).setSyncedBy(Mockito.<String>any());
    doNothing().when(oralExamination).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(oralExamination).setVanID(Mockito.<Integer>any());
    doNothing().when(oralExamination).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(oralExamination).setVehicalNo(Mockito.<String>any());
    doNothing().when(oralExamination).setVisitCode(Mockito.<Long>any());
    oralExamination.setBenVisitID(1L);
    oralExamination.setBeneficiaryRegID(1L);
    oralExamination.setChronicBurningSensation(true);
    oralExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    oralExamination.setCreatedDate(mock(Timestamp.class));
    oralExamination.setDeleted(true);
    oralExamination.setID(1L);
    oralExamination.setLastModDate(mock(Timestamp.class));
    oralExamination.setLimitedMouthOpening("Limited Mouth Opening");
    oralExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    oralExamination.setObservation("Observation");
    oralExamination.setParkingPlaceID(1);
    oralExamination.setPreMalignantLesionType("Pre Malignant Lesion Type");
    oralExamination.setPreMalignantLesionTypeList(new String[]{"Pre Malignant Lesion Type List"});
    oralExamination.setPremalignantLesions(true);
    oralExamination.setProcessed("Processed");
    oralExamination.setProlongedIrritation(true);
    oralExamination.setProviderServiceMapID(1);
    oralExamination.setReservedForChange("Reserved For Change");
    oralExamination.setSyncedBy("Synced By");
    oralExamination.setSyncedDate(mock(Timestamp.class));
    oralExamination.setVanID(1);
    oralExamination.setVanSerialNo(1L);
    oralExamination.setVehicalNo("Vehical No");
    oralExamination.setVisitCode(1L);
    when(oralExaminationRepo.save(Mockito.<OralExamination>any())).thenReturn(oralExamination);

    OralExamination oralDetails = new OralExamination();
    oralDetails.setBenVisitID(1L);
    oralDetails.setBeneficiaryRegID(1L);
    oralDetails.setChronicBurningSensation(true);
    oralDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    oralDetails.setCreatedDate(mock(Timestamp.class));
    oralDetails.setDeleted(true);
    oralDetails.setID(1L);
    oralDetails.setLastModDate(mock(Timestamp.class));
    oralDetails.setLimitedMouthOpening("Limited Mouth Opening");
    oralDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    oralDetails.setObservation("Observation");
    oralDetails.setParkingPlaceID(1);
    oralDetails.setPreMalignantLesionType("Pre Malignant Lesion Type");
    oralDetails.setPreMalignantLesionTypeList(new String[]{"Pre Malignant Lesion Type List"});
    oralDetails.setPremalignantLesions(true);
    oralDetails.setProcessed("Processed");
    oralDetails.setProlongedIrritation(true);
    oralDetails.setProviderServiceMapID(1);
    oralDetails.setReservedForChange("Reserved For Change");
    oralDetails.setSyncedBy("Synced By");
    oralDetails.setSyncedDate(mock(Timestamp.class));
    oralDetails.setVanID(1);
    oralDetails.setVanSerialNo(1L);
    oralDetails.setVehicalNo("Vehical No");
    oralDetails.setVisitCode(1L);

    // Act and Assert
    assertThrows(IEMRException.class, () -> commonNurseServiceImpl.saveOralExamination(oralDetails));
    verify(oralExamination).getID();
    verify(oralExamination).setBenVisitID(isA(Long.class));
    verify(oralExamination).setBeneficiaryRegID(isA(Long.class));
    verify(oralExamination).setChronicBurningSensation(isA(Boolean.class));
    verify(oralExamination).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(oralExamination).setCreatedDate(isA(Timestamp.class));
    verify(oralExamination).setDeleted(isA(Boolean.class));
    verify(oralExamination).setID(isA(Long.class));
    verify(oralExamination).setLastModDate(isA(Timestamp.class));
    verify(oralExamination).setLimitedMouthOpening(eq("Limited Mouth Opening"));
    verify(oralExamination).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(oralExamination).setObservation(eq("Observation"));
    verify(oralExamination).setParkingPlaceID(isA(Integer.class));
    verify(oralExamination).setPreMalignantLesionType(eq("Pre Malignant Lesion Type"));
    verify(oralExamination).setPreMalignantLesionTypeList(isA(String[].class));
    verify(oralExamination).setPremalignantLesions(isA(Boolean.class));
    verify(oralExamination).setProcessed(eq("Processed"));
    verify(oralExamination).setProlongedIrritation(isA(Boolean.class));
    verify(oralExamination).setProviderServiceMapID(isA(Integer.class));
    verify(oralExamination).setReservedForChange(eq("Reserved For Change"));
    verify(oralExamination).setSyncedBy(eq("Synced By"));
    verify(oralExamination).setSyncedDate(isA(Timestamp.class));
    verify(oralExamination).setVanID(isA(Integer.class));
    verify(oralExamination).setVanSerialNo(isA(Long.class));
    verify(oralExamination).setVehicalNo(eq("Vehical No"));
    verify(oralExamination).setVisitCode(isA(Long.class));
    verify(oralExaminationRepo).save(isA(OralExamination.class));
  }

  @Test
  void testGetOralExaminationDetails() throws IEMRException {
    // Arrange
    OralExamination oralExamination = new OralExamination();
    oralExamination.setBenVisitID(1L);
    oralExamination.setBeneficiaryRegID(1L);
    oralExamination.setChronicBurningSensation(true);
    oralExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    oralExamination.setCreatedDate(mock(Timestamp.class));
    oralExamination.setDeleted(true);
    oralExamination.setID(1L);
    oralExamination.setLastModDate(mock(Timestamp.class));
    oralExamination.setLimitedMouthOpening("Limited Mouth Opening");
    oralExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    oralExamination.setObservation("Observation");
    oralExamination.setParkingPlaceID(1);
    oralExamination.setPreMalignantLesionType("Pre Malignant Lesion Type");
    oralExamination.setPreMalignantLesionTypeList(new String[]{"Pre Malignant Lesion Type List"});
    oralExamination.setPremalignantLesions(true);
    oralExamination.setProcessed("Processed");
    oralExamination.setProlongedIrritation(true);
    oralExamination.setProviderServiceMapID(1);
    oralExamination.setReservedForChange("Reserved For Change");
    oralExamination.setSyncedBy("Synced By");
    oralExamination.setSyncedDate(mock(Timestamp.class));
    oralExamination.setVanID(1);
    oralExamination.setVanSerialNo(1L);
    oralExamination.setVehicalNo("Vehical No");
    oralExamination.setVisitCode(1L);

    // Act
    OralExamination actualOralExaminationDetails = commonNurseServiceImpl.getOralExaminationDetails(oralExamination);

    // Assert
    assertEquals("Pre Malignant Lesion Type List||", actualOralExaminationDetails.getPreMalignantLesionType());
    assertSame(oralExamination, actualOralExaminationDetails);
  }

  @Test
  void testGetOralExaminationDetails2() throws IEMRException {
    // Arrange
    OralExamination oralExamination = mock(OralExamination.class);
    when(oralExamination.getPreMalignantLesionTypeList()).thenReturn(new String[]{});
    doNothing().when(oralExamination).setBenVisitID(Mockito.<Long>any());
    doNothing().when(oralExamination).setBeneficiaryRegID(Mockito.<Long>any());
    doNothing().when(oralExamination).setChronicBurningSensation(Mockito.<Boolean>any());
    doNothing().when(oralExamination).setCreatedBy(Mockito.<String>any());
    doNothing().when(oralExamination).setCreatedDate(Mockito.<Timestamp>any());
    doNothing().when(oralExamination).setDeleted(Mockito.<Boolean>any());
    doNothing().when(oralExamination).setID(Mockito.<Long>any());
    doNothing().when(oralExamination).setLastModDate(Mockito.<Timestamp>any());
    doNothing().when(oralExamination).setLimitedMouthOpening(Mockito.<String>any());
    doNothing().when(oralExamination).setModifiedBy(Mockito.<String>any());
    doNothing().when(oralExamination).setObservation(Mockito.<String>any());
    doNothing().when(oralExamination).setParkingPlaceID(Mockito.<Integer>any());
    doNothing().when(oralExamination).setPreMalignantLesionType(Mockito.<String>any());
    doNothing().when(oralExamination).setPreMalignantLesionTypeList(Mockito.<String[]>any());
    doNothing().when(oralExamination).setPremalignantLesions(Mockito.<Boolean>any());
    doNothing().when(oralExamination).setProcessed(Mockito.<String>any());
    doNothing().when(oralExamination).setProlongedIrritation(Mockito.<Boolean>any());
    doNothing().when(oralExamination).setProviderServiceMapID(Mockito.<Integer>any());
    doNothing().when(oralExamination).setReservedForChange(Mockito.<String>any());
    doNothing().when(oralExamination).setSyncedBy(Mockito.<String>any());
    doNothing().when(oralExamination).setSyncedDate(Mockito.<Timestamp>any());
    doNothing().when(oralExamination).setVanID(Mockito.<Integer>any());
    doNothing().when(oralExamination).setVanSerialNo(Mockito.<Long>any());
    doNothing().when(oralExamination).setVehicalNo(Mockito.<String>any());
    doNothing().when(oralExamination).setVisitCode(Mockito.<Long>any());
    oralExamination.setBenVisitID(1L);
    oralExamination.setBeneficiaryRegID(1L);
    oralExamination.setChronicBurningSensation(true);
    oralExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    oralExamination.setCreatedDate(mock(Timestamp.class));
    oralExamination.setDeleted(true);
    oralExamination.setID(1L);
    oralExamination.setLastModDate(mock(Timestamp.class));
    oralExamination.setLimitedMouthOpening("Limited Mouth Opening");
    oralExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    oralExamination.setObservation("Observation");
    oralExamination.setParkingPlaceID(1);
    oralExamination.setPreMalignantLesionType("Pre Malignant Lesion Type");
    oralExamination.setPreMalignantLesionTypeList(new String[]{"Pre Malignant Lesion Type List"});
    oralExamination.setPremalignantLesions(true);
    oralExamination.setProcessed("Processed");
    oralExamination.setProlongedIrritation(true);
    oralExamination.setProviderServiceMapID(1);
    oralExamination.setReservedForChange("Reserved For Change");
    oralExamination.setSyncedBy("Synced By");
    oralExamination.setSyncedDate(mock(Timestamp.class));
    oralExamination.setVanID(1);
    oralExamination.setVanSerialNo(1L);
    oralExamination.setVehicalNo("Vehical No");
    oralExamination.setVisitCode(1L);

    // Act
    OralExamination actualOralExaminationDetails = commonNurseServiceImpl.getOralExaminationDetails(oralExamination);

    // Assert
    verify(oralExamination, atLeast(1)).getPreMalignantLesionTypeList();
    verify(oralExamination).setBenVisitID(isA(Long.class));
    verify(oralExamination).setBeneficiaryRegID(isA(Long.class));
    verify(oralExamination).setChronicBurningSensation(isA(Boolean.class));
    verify(oralExamination).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
    verify(oralExamination).setCreatedDate(isA(Timestamp.class));
    verify(oralExamination).setDeleted(isA(Boolean.class));
    verify(oralExamination).setID(isA(Long.class));
    verify(oralExamination).setLastModDate(isA(Timestamp.class));
    verify(oralExamination).setLimitedMouthOpening(eq("Limited Mouth Opening"));
    verify(oralExamination).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
    verify(oralExamination).setObservation(eq("Observation"));
    verify(oralExamination).setParkingPlaceID(isA(Integer.class));
    verify(oralExamination).setPreMalignantLesionType(eq("Pre Malignant Lesion Type"));
    verify(oralExamination).setPreMalignantLesionTypeList(isA(String[].class));
    verify(oralExamination).setPremalignantLesions(isA(Boolean.class));
    verify(oralExamination).setProcessed(eq("Processed"));
    verify(oralExamination).setProlongedIrritation(isA(Boolean.class));
    verify(oralExamination).setProviderServiceMapID(isA(Integer.class));
    verify(oralExamination).setReservedForChange(eq("Reserved For Change"));
    verify(oralExamination).setSyncedBy(eq("Synced By"));
    verify(oralExamination).setSyncedDate(isA(Timestamp.class));
    verify(oralExamination).setVanID(isA(Integer.class));
    verify(oralExamination).setVanSerialNo(isA(Long.class));
    verify(oralExamination).setVehicalNo(eq("Vehical No"));
    verify(oralExamination).setVisitCode(isA(Long.class));
    assertSame(oralExamination, actualOralExaminationDetails);
  }

  @Test
  void testGettersAndSetters() {
    CommonNurseServiceImpl commonNurseServiceImpl = new CommonNurseServiceImpl();

    // Act
    commonNurseServiceImpl.setBenAdherenceRepo(mock(BenAdherenceRepo.class));
    commonNurseServiceImpl.setBenAllergyHistoryRepo(mock(BenAllergyHistoryRepo.class));
    commonNurseServiceImpl.setBenAnthropometryRepo(mock(BenAnthropometryRepo.class));
    commonNurseServiceImpl.setBenCancerVitalDetailRepo(mock(BenCancerVitalDetailRepo.class));
    commonNurseServiceImpl.setBenChiefComplaintRepo(mock(BenChiefComplaintRepo.class));
    commonNurseServiceImpl.setBenChildDevelopmentHistoryRepo(mock(BenChildDevelopmentHistoryRepo.class));
    commonNurseServiceImpl.setBenFamilyHistoryRepo(mock(BenFamilyHistoryRepo.class));
    commonNurseServiceImpl.setBenMedHistoryRepo(mock(BenMedHistoryRepo.class));
    commonNurseServiceImpl.setBenMedicationHistoryRepo(mock(BenMedicationHistoryRepo.class));
    commonNurseServiceImpl.setBenMenstrualDetailsRepo(mock(BenMenstrualDetailsRepo.class));
    commonNurseServiceImpl.setBenPersonalHabitRepo(mock(BenPersonalHabitRepo.class));
    commonNurseServiceImpl.setBenPhysicalVitalRepo(mock(BenPhysicalVitalRepo.class));
    commonNurseServiceImpl.setBenVisitDetailRepo(mock(BenVisitDetailRepo.class));
    commonNurseServiceImpl.setBencomrbidityCondRepo(mock(BencomrbidityCondRepo.class));
    commonNurseServiceImpl.setBeneficiaryFlowStatusRepo(mock(BeneficiaryFlowStatusRepo.class));
    commonNurseServiceImpl.setChildFeedingDetailsRepo(mock(ChildFeedingDetailsRepo.class));
    commonNurseServiceImpl.setChildOptionalVaccineDetailRepo(mock(ChildOptionalVaccineDetailRepo.class));
    commonNurseServiceImpl.setChildVaccineDetail1Repo(mock(ChildVaccineDetail1Repo.class));
    commonNurseServiceImpl.setCommonDoctorServiceImpl(new CommonDoctorServiceImpl());
    commonNurseServiceImpl.setFemaleObstetricHistoryRepo(mock(FemaleObstetricHistoryRepo.class));
    commonNurseServiceImpl.setIDRSDataRepo(mock(IDRSDataRepo.class));
    commonNurseServiceImpl.setLabTestOrderDetailRepo(mock(LabTestOrderDetailRepo.class));
    commonNurseServiceImpl.setPerinatalHistoryRepo(mock(PerinatalHistoryRepo.class));
    commonNurseServiceImpl.setPhyGeneralExaminationRepo(mock(PhyGeneralExaminationRepo.class));
    commonNurseServiceImpl.setPhyHeadToToeExaminationRepo(mock(PhyHeadToToeExaminationRepo.class));
    commonNurseServiceImpl.setPhysicalActivityTypeRepo(mock(PhysicalActivityTypeRepo.class));
    commonNurseServiceImpl.setPrescribedDrugDetailRepo(mock(PrescribedDrugDetailRepo.class));
    commonNurseServiceImpl.setPrescriptionDetailRepo(mock(PrescriptionDetailRepo.class));
    commonNurseServiceImpl.setRegistrarRepoBenData(mock(RegistrarRepoBenData.class));
    commonNurseServiceImpl.setReistrarRepoBenSearch(mock(ReistrarRepoBenSearch.class));
    commonNurseServiceImpl.setSysCardiovascularExaminationRepo(mock(SysCardiovascularExaminationRepo.class));
    commonNurseServiceImpl.setSysCentralNervousExaminationRepo(mock(SysCentralNervousExaminationRepo.class));
    commonNurseServiceImpl.setSysGastrointestinalExaminationRepo(mock(SysGastrointestinalExaminationRepo.class));
    commonNurseServiceImpl.setSysGenitourinarySystemExaminationRepo(mock(SysGenitourinarySystemExaminationRepo.class));
    commonNurseServiceImpl
        .setSysMusculoskeletalSystemExaminationRepo(mock(SysMusculoskeletalSystemExaminationRepo.class));
    commonNurseServiceImpl.setSysRespiratoryExaminationRepo(mock(SysRespiratoryExaminationRepo.class));
  }
}
