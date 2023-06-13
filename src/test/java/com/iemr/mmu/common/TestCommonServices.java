/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.data.anc.BenAllergyHistory;
import com.iemr.mmu.data.anc.BenFamilyHistory;
import com.iemr.mmu.data.anc.BenMedHistory;
import com.iemr.mmu.data.anc.BenMedicationHistory;
import com.iemr.mmu.data.anc.BenMenstrualDetails;
import com.iemr.mmu.data.anc.BenPersonalHabit;
import com.iemr.mmu.data.anc.BencomrbidityCondDetails;
import com.iemr.mmu.data.anc.ChildOptionalVaccineDetail;
import com.iemr.mmu.data.anc.ChildVaccineDetail1;
import com.iemr.mmu.data.anc.FemaleObstetricHistory;
import com.iemr.mmu.data.anc.PhyGeneralExamination;
import com.iemr.mmu.data.anc.PhyHeadToToeExamination;
import com.iemr.mmu.data.anc.SysCardiovascularExamination;
import com.iemr.mmu.data.anc.SysCentralNervousExamination;
import com.iemr.mmu.data.anc.SysGenitourinarySystemExamination;
import com.iemr.mmu.data.anc.SysMusculoskeletalSystemExamination;
import com.iemr.mmu.data.anc.SysRespiratoryExamination;
import com.iemr.mmu.data.nurse.BenAnthropometryDetail;
import com.iemr.mmu.data.nurse.BenPhysicalVitalDetail;
import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.data.quickConsultation.BenChiefComplaint;
import com.iemr.mmu.data.quickConsultation.BenClinicalObservations;
import com.iemr.mmu.data.quickConsultation.LabTestOrderDetail;
import com.iemr.mmu.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.mmu.data.quickConsultation.PrescriptionDetail;
import com.iemr.mmu.repo.nurse.BenAnthropometryRepo;
import com.iemr.mmu.repo.nurse.BenPhysicalVitalRepo;
import com.iemr.mmu.repo.nurse.BenVisitDetailRepo;
import com.iemr.mmu.repo.nurse.anc.BenAllergyHistoryRepo;
import com.iemr.mmu.repo.nurse.anc.BenFamilyHistoryRepo;
import com.iemr.mmu.repo.nurse.anc.BenMedHistoryRepo;
import com.iemr.mmu.repo.nurse.anc.BenMedicationHistoryRepo;
import com.iemr.mmu.repo.nurse.anc.BenMenstrualDetailsRepo;
import com.iemr.mmu.repo.nurse.anc.BenPersonalHabitRepo;
import com.iemr.mmu.repo.nurse.anc.BencomrbidityCondRepo;
import com.iemr.mmu.repo.nurse.anc.ChildOptionalVaccineDetailRepo;
import com.iemr.mmu.repo.nurse.anc.ChildVaccineDetail1Repo;
import com.iemr.mmu.repo.nurse.anc.FemaleObstetricHistoryRepo;
import com.iemr.mmu.repo.nurse.anc.PhyGeneralExaminationRepo;
import com.iemr.mmu.repo.nurse.anc.PhyHeadToToeExaminationRepo;
import com.iemr.mmu.repo.nurse.anc.SysCardiovascularExaminationRepo;
import com.iemr.mmu.repo.nurse.anc.SysCentralNervousExaminationRepo;
import com.iemr.mmu.repo.nurse.anc.SysGenitourinarySystemExaminationRepo;
import com.iemr.mmu.repo.nurse.anc.SysMusculoskeletalSystemExaminationRepo;
import com.iemr.mmu.repo.nurse.anc.SysRespiratoryExaminationRepo;
import com.iemr.mmu.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.mmu.repo.quickConsultation.BenClinicalObservationsRepo;
import com.iemr.mmu.repo.quickConsultation.LabTestOrderDetailRepo;
import com.iemr.mmu.repo.quickConsultation.PrescribedDrugDetailRepo;
import com.iemr.mmu.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.mmu.repo.registrar.RegistrarRepoBenData;
import com.iemr.mmu.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonNurseServiceImpl;

public class TestCommonServices {
	@InjectMocks
	public static CommonNurseServiceImpl commonNurseServiceImpl = spy(CommonNurseServiceImpl.class);
	public static CommonDoctorServiceImpl commonDoctorServiceImpl = spy(CommonDoctorServiceImpl.class);

	public static BenVisitDetailRepo benVisitDetailRepoMock = mock(BenVisitDetailRepo.class);
	public static BenChiefComplaintRepo benChiefComplaintRepoMock = mock(BenChiefComplaintRepo.class);
	public static BenMedHistoryRepo benMedHistoryRepoMock = mock(BenMedHistoryRepo.class);
	public static BencomrbidityCondRepo bencomrbidityCondRepoMock = mock(BencomrbidityCondRepo.class);
	public static BenMedicationHistoryRepo benMedicationHistoryRepoMock = mock(BenMedicationHistoryRepo.class);
	public static FemaleObstetricHistoryRepo femaleObstetricHistoryRepoMock = mock(FemaleObstetricHistoryRepo.class);
	public static BenMenstrualDetailsRepo benMenstrualDetailsRepoMock = mock(BenMenstrualDetailsRepo.class);
	public static BenFamilyHistoryRepo benFamilyHistoryRepoMock = mock(BenFamilyHistoryRepo.class);
	public static BenPersonalHabitRepo benPersonalHabitRepoMock = mock(BenPersonalHabitRepo.class);
	public static BenAllergyHistoryRepo benAllergyHistoryRepoMock = mock(BenAllergyHistoryRepo.class);
	public static ChildOptionalVaccineDetailRepo childOptionalVaccineDetailRepoMock = mock(
			ChildOptionalVaccineDetailRepo.class);
	public static ChildVaccineDetail1Repo childVaccineDetail1RepoMock = mock(ChildVaccineDetail1Repo.class);

	public static BenAnthropometryRepo benAnthropometryRepoMock = mock(BenAnthropometryRepo.class);
	public static BenPhysicalVitalRepo benPhysicalVitalRepoMock = mock(BenPhysicalVitalRepo.class);
	public static PhyGeneralExaminationRepo phyGeneralExaminationRepoMock = mock(PhyGeneralExaminationRepo.class);
	public static PhyHeadToToeExaminationRepo phyHeadToToeExaminationRepoMock = mock(PhyHeadToToeExaminationRepo.class);
	public static SysCardiovascularExaminationRepo sysCardiovascularExaminationRepoMock = mock(
			SysCardiovascularExaminationRepo.class);
	public static SysRespiratoryExaminationRepo sysRespiratoryExaminationRepoMock = mock(
			SysRespiratoryExaminationRepo.class);
	public static SysCentralNervousExaminationRepo sysCentralNervousExaminationRepoMock = mock(
			SysCentralNervousExaminationRepo.class);
	public static SysMusculoskeletalSystemExaminationRepo sysMusculoskeletalSystemExaminationRepoMock = mock(
			SysMusculoskeletalSystemExaminationRepo.class);
	public static SysGenitourinarySystemExaminationRepo sysGenitourinarySystemExaminationRepoMock = mock(
			SysGenitourinarySystemExaminationRepo.class);

	private static RegistrarRepoBenData registrarRepoBenData = mock(RegistrarRepoBenData.class);

	// Doctor Repo's
	public static BenClinicalObservationsRepo benClinicalObservationsRepoMock = mock(BenClinicalObservationsRepo.class);
	public static PrescriptionDetailRepo prescriptionDetailRepoMock = mock(PrescriptionDetailRepo.class);
	public static LabTestOrderDetailRepo labTestOrderDetailRepoMock = mock(LabTestOrderDetailRepo.class);
	public static PrescribedDrugDetailRepo prescribedDrugDetailRepoMock = mock(PrescribedDrugDetailRepo.class);

	public static Long beneficiaryRegID = null;
	public static Long benVisitID = null;
	public static String requestObjPve = "";
	public static String requestObjNve = "";
	public static JsonObject jsnOBJPve = null;
	public static JsonObject jsnOBJNve = null;
	public static String updateHistoryPve = "";
	public static JsonObject updateHstryJsnPve = null;
	public static String updateVitalPve = "";
	public static JsonObject updateVitalJsnPve = null;
	public static String updateExaminationPve = "";
	public static JsonObject updateExaminationJsnPve = null;
	public static String doctorSaveObjPve = null;
	public static JsonObject doctorSaveJsnPve = null;

	// Expected Responses
	public static String pastHistoryDataPveRes = "";
	public static String pastHistoryDataNveRes = "";
	public static String tobaccoHistoryDataPveRes = "";
	public static String menstrualHistoryDataPveRes = "";
	public static String alcoholHistoryDataPveRes = "";
	public static String allergyHistoryDataPveRes = "";
	public static String medicationHistoryDataPveRes = "";
	public static String comorbidHistoryDataPveRes = "";
	public static String obstetricHistoryDataPveRes = "";
	public static String familyHistoryDataPveRes = "";
	public static String immunizationHistoryDataPveRes = "";
	public static String childVaccineHistoryDataPveRes = "";

	public static String visitDetailsPveRes = "";
	public static String historyDetailsPveRes = "";
	public static String vitalDetailsPveRes = "";
	public static String examinationDetailsPveRes = "";

	@BeforeClass
	public static void initializeParams() {
		commonNurseServiceImpl.setRegistrarRepoBenData(registrarRepoBenData);

		commonNurseServiceImpl.setBenVisitDetailRepo(benVisitDetailRepoMock);
		commonNurseServiceImpl.setBenChiefComplaintRepo(benChiefComplaintRepoMock);
		commonNurseServiceImpl.setBenMedHistoryRepo(benMedHistoryRepoMock);
		commonNurseServiceImpl.setBencomrbidityCondRepo(bencomrbidityCondRepoMock);
		commonNurseServiceImpl.setBenMedicationHistoryRepo(benMedicationHistoryRepoMock);
		commonNurseServiceImpl.setFemaleObstetricHistoryRepo(femaleObstetricHistoryRepoMock);
		commonNurseServiceImpl.setBenMenstrualDetailsRepo(benMenstrualDetailsRepoMock);
		commonNurseServiceImpl.setBenFamilyHistoryRepo(benFamilyHistoryRepoMock);
		commonNurseServiceImpl.setBenPersonalHabitRepo(benPersonalHabitRepoMock);
		commonNurseServiceImpl.setBenAllergyHistoryRepo(benAllergyHistoryRepoMock);
		commonNurseServiceImpl.setChildOptionalVaccineDetailRepo(childOptionalVaccineDetailRepoMock);
		commonNurseServiceImpl.setChildVaccineDetail1Repo(childVaccineDetail1RepoMock);

		commonNurseServiceImpl.setBenAnthropometryRepo(benAnthropometryRepoMock);
		commonNurseServiceImpl.setBenPhysicalVitalRepo(benPhysicalVitalRepoMock);
		commonNurseServiceImpl.setPhyGeneralExaminationRepo(phyGeneralExaminationRepoMock);
		commonNurseServiceImpl.setPhyHeadToToeExaminationRepo(phyHeadToToeExaminationRepoMock);
		commonNurseServiceImpl.setSysCardiovascularExaminationRepo(sysCardiovascularExaminationRepoMock);
		commonNurseServiceImpl.setSysRespiratoryExaminationRepo(sysRespiratoryExaminationRepoMock);
		commonNurseServiceImpl.setSysCentralNervousExaminationRepo(sysCentralNervousExaminationRepoMock);
		commonNurseServiceImpl.setSysMusculoskeletalSystemExaminationRepo(sysMusculoskeletalSystemExaminationRepoMock);
		commonNurseServiceImpl.setSysGenitourinarySystemExaminationRepo(sysGenitourinarySystemExaminationRepoMock);

		commonDoctorServiceImpl.setBenClinicalObservationsRepo(benClinicalObservationsRepoMock);
		commonDoctorServiceImpl.setBenChiefComplaintRepo(benChiefComplaintRepoMock);
		commonNurseServiceImpl.setPrescriptionDetailRepo(prescriptionDetailRepoMock);
		commonNurseServiceImpl.setLabTestOrderDetailRepo(labTestOrderDetailRepoMock);
		commonNurseServiceImpl.setPrescribedDrugDetailRepo(prescribedDrugDetailRepoMock);

		// Request's
		beneficiaryRegID = 7469L;
		benVisitID = 131L;
		requestObjPve = "{\"visitDetails\":{ \"visitDetails\":{ \"beneficiaryRegID\":\"7469\", \"providerServiceMapID\":\"1320\", \"visitNo\":null, \"visitReason\":\"FollowUp\", \"visitCategory\":\"General OPD\", \"pregnancyStatus\":null, \"rCHID\":null, \"healthFacilityType\":null, \"healthFacilityLocation\":null, \"reportFilePath\":null, \"createdBy\":\"891\" }, \"chiefComplaints\":[ { \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"chiefComplaint\":null, \"chiefComplaintID\":null, \"duration\":null, \"unitOfDuration\":null, \"description\":null, \"createdBy\":\"891\" } ] }, \"vitalDetails\":{ \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"weight_Kg\":null, \"height_cm\":null, \"waistCircumference_cm\":null, \"hipCircumference_cm\":null, \"bMI\":null, \"waistHipRatio\":null, \"temperature\":null, \"pulseRate\":null, \"systolicBP_1stReading\":null, \"diastolicBP_1stReading\":null, \"bloodGlucose_Fasting\":null, \"bloodGlucose_Random\":null, \"bloodGlucose_2hr_PP\":null, \"respiratoryRate\":null, \"createdBy\":\"891\" }, \"historyDetails\":{ \"pastHistory\":{ \"pastIllness\":[ { \"illnessTypeID\":null, \"illnessType\":null, \"otherIllnessType\":null, \"timePeriodAgo\":null, \"timePeriodUnit\":null } ], \"pastSurgery\":[ { \"surgeryID\":null, \"surgeryType\":null, \"otherSurgeryType\":null, \"timePeriodAgo\":null, \"timePeriodUnit\":null } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"comorbidConditions\":{ \"comorbidityConcurrentConditionsList\":[ { \"comorbidConditions\":null, \"otherComorbidCondition\":null, \"timePeriodAgo\":null, \"timePeriodUnit\":null, \"isForHistory\":null } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"medicationHistory\":{ \"medicationHistoryList\":[ { \"currentMedication\":null, \"timePeriodAgo\":null, \"timePeriodUnit\":null } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"femaleObstetricHistory\":{ \"totalNoOfPreg\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\", \"femaleObstetricHistoryList\":[ ] }, \"menstrualHistory\":{ \"menstrualCycleStatus\":null, \"menstrualCycleStatusID\":null, \"regularity\":null, \"cycleLength\":null, \"menstrualCyclelengthID\":null, \"menstrualFlowDurationID\":null, \"bloodFlowDuration\":null, \"menstrualProblemID\":null, \"problemName\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"familyHistory\":{ \"familyDiseaseList\":[ { \"diseaseTypeID\":null, \"diseaseType\":null, \"otherDiseaseType\":null, \"familyMembers\":null } ], \"isGeneticDisorder\":null, \"geneticDisorder\":null, \"isConsanguineousMarrige\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"personalHistory\":{ \"dietaryType\":null, \"physicalActivityType\":null, \"riskySexualPracticesStatus\":0, \"tobaccoUseStatus\":null, \"alcoholIntakeStatus\":null, \"allergyStatus\":null, \"tobaccoList\":[ ], \"alcoholList\":[ ], \"allergicList\":[ ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"childVaccineDetails\":{ \"childOptionalVaccineList\":[ { \"vaccineName\":null, \"otherVaccineName\":null, \"actualReceivingAge\":null, \"receivedFacilityName\":null } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"immunizationHistory\":{ \"immunizationList\":[ { \"defaultReceivingAge\":\"At Birth\", \"vaccines\":[ { \"vaccine\":\"BCG\", \"status\":false }, { \"vaccine\":\"HBV-0\", \"status\":false }, { \"vaccine\":\"OPV-0\", \"status\":false } ] }, { \"defaultReceivingAge\":\"6 Weeks\", \"vaccines\":[ { \"vaccine\":\"IPV-1\", \"status\":false }, { \"vaccine\":\"OPV\", \"status\":false }, { \"vaccine\":\"Pentavalent-1\", \"status\":false }, { \"vaccine\":\"Rota Vaccine-1\", \"status\":false } ] }, { \"defaultReceivingAge\":\"10 Weeks\", \"vaccines\":[ { \"vaccine\":\"IPV-2\", \"status\":false }, { \"vaccine\":\"OPV\", \"status\":false }, { \"vaccine\":\"Pentavalent-2\", \"status\":false }, { \"vaccine\":\"Rota Vaccine-2\", \"status\":false } ] }, { \"defaultReceivingAge\":\"14 Weeks\", \"vaccines\":[ { \"vaccine\":\"IPV-3 \", \"status\":false }, { \"vaccine\":\"OPV\", \"status\":false }, { \"vaccine\":\"Pentavalent-3\", \"status\":false }, { \"vaccine\":\"Rota Vaccine-3\", \"status\":false } ] }, { \"defaultReceivingAge\":\"9 Months\", \"vaccines\":[ { \"vaccine\":\"JE Vaccine\", \"status\":false }, { \"vaccine\":\"Measles Vaccine/MR\", \"status\":false }, { \"vaccine\":\"Vitamin A\", \"status\":false } ] }, { \"defaultReceivingAge\":\"16-24 Months\", \"vaccines\":[ { \"vaccine\":\"DPT-B 1\", \"status\":false }, { \"vaccine\":\"Measles/MR Vaccine\", \"status\":false }, { \"vaccine\":\"OPV\", \"status\":false } ] }, { \"defaultReceivingAge\":\"5 Years\", \"vaccines\":[ { \"vaccine\":\"\", \"status\":false } ] }, { \"defaultReceivingAge\":\"10 Years\", \"vaccines\":[ { \"vaccine\":\"TT\", \"status\":false } ] }, { \"defaultReceivingAge\":\"16 Years\", \"vaccines\":[ { \"vaccine\":\"TT\", \"status\":false } ] } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"developmentHistory\":{ \"grossMotorMilestones\":null, \"fineMotorMilestones\":null, \"socialMilestones\":null, \"languageMilestones\":null, \"developmentalProblems\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"feedingHistory\":{ \"typeOfFeed\":null, \"compFeedStartAge\":null, \"noOfCompFeedPerDay\":null, \"foodIntoleranceStatus\":0, \"typeofFoodIntolerance\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"perinatalHistroy\":{ \"deliveryPlaceID\":null, \"placeOfDelivery\":null, \"otherPlaceOfDelivery\":null, \"deliveryTypeID\":null, \"typeOfDelivery\":null, \"complicationAtBirthID\":null, \"complicationAtBirth\":null, \"otherComplicationAtBirth\":null, \"gestation\":null, \"birthWeight_kg\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" } }, \"examinationDetails\":{ \"generalExamination\":{ \"consciousness\":null, \"coherence\":null, \"cooperation\":null, \"comfortness\":null, \"builtAndAppearance\":null, \"gait\":null, \"dangerSigns\":null, \"typeOfDangerSigns\":null, \"pallor\":null, \"jaundice\":null, \"cyanosis\":null, \"clubbing\":null, \"lymphadenopathy\":null, \"lymphnodesInvolved\":null, \"typeOfLymphadenopathy\":null, \"edema\":null, \"extentOfEdema\":null, \"edemaType\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"headToToeExamination\":{ \"headtoToeExam\":null, \"head\":null, \"eyes\":null, \"ears\":null, \"nose\":null, \"oralCavity\":null, \"throat\":null, \"breastAndNipples\":null, \"trunk\":null, \"upperLimbs\":null, \"lowerLimbs\":null, \"skin\":null, \"hair\":null, \"nails\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"gastroIntestinalExamination\":{ \"inspection\":null, \"palpation_AbdomenTexture\":null, \"palpation_Liver\":null, \"palpation_Spleen\":null, \"palpation_Tenderness\":null, \"palpation_LocationOfTenderness\":null, \"percussion\":null, \"auscultation\":null, \"analRegion\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"cardioVascularExamination\":{ \"jugularVenousPulse_JVP\":null, \"apexbeatLocation\":null, \"apexbeatType\":null, \"firstHeartSound_S1\":null, \"secondHeartSound_S2\":null, \"additionalHeartSounds\":null, \"murmurs\":null, \"pericardialRub\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"respiratorySystemExamination\":{ \"trachea\":null, \"inspection\":null, \"signsOfRespiratoryDistress\":null, \"palpation\":null, \"auscultation_Stridor\":null, \"auscultation_BreathSounds\":null, \"auscultation_Crepitations\":null, \"auscultation_Wheezing\":null, \"auscultation_PleuralRub\":null, \"auscultation_ConductedSounds\":null, \"percussion\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"centralNervousSystemExamination\":{ \"handedness\":null, \"cranialNervesExamination\":null, \"motorSystem\":null, \"sensorySystem\":null, \"autonomicSystem\":null, \"cerebellarSigns\":null, \"signsOfMeningealIrritation\":null, \"skull\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"musculoskeletalSystemExamination\":{ \"joint_TypeOfJoint\":null, \"joint_Laterality\":null, \"joint_Abnormality\":null, \"upperLimb_Laterality\":null, \"upperLimb_Abnormality\":null, \"lowerLimb_Laterality\":null, \"lowerLimb_Abnormality\":null, \"chestWall\":null, \"spine\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"genitoUrinarySystemExamination\":{ \"renalAngle\":null, \"suprapubicRegion\":null, \"externalGenitalia\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" } } }";
		requestObjNve = "{\"visitDetails\":{ \"visitetails\":{ \"beneficiaryRegID\":\"7469\", \"providerServiceMapID\":\"1320\", \"visitNo\":null, \"visitReason\":\"FollowUp\", \"visitCategory\":\"General OPD\", \"pregnancyStatus\":null, \"rCHID\":null, \"healthFacilityType\":null, \"healthFacilityLocation\":null, \"reportFilePath\":null, \"createdBy\":\"891\" }, \"chiefComplaints\":[ { \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"chiefComplaint\":null, \"chiefComplaintID\":null, \"duration\":null, \"unitOfDuration\":null, \"description\":null, \"createdBy\":\"891\" } ] }, \"vitalDetails\":{ \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"weight_Kg\":null, \"height_cm\":null, \"waistCircumference_cm\":null, \"hipCircumference_cm\":null, \"bMI\":null, \"waistHipRatio\":null, \"temperature\":null, \"pulseRate\":null, \"systolicBP_1stReading\":null, \"diastolicBP_1stReading\":null, \"bloodGlucose_Fasting\":null, \"bloodGlucose_Random\":null, \"bloodGlucose_2hr_PP\":null, \"respiratoryRate\":null, \"createdBy\":\"891\" }, \"historyDetails\":{ \"pastHistory\":{ \"pastIllness\":[ { \"illnessTypeID\":null, \"illnessType\":null, \"otherIllnessType\":null, \"timePeriodAgo\":null, \"timePeriodUnit\":null } ], \"pastSurgery\":[ { \"surgeryID\":null, \"surgeryType\":null, \"otherSurgeryType\":null, \"timePeriodAgo\":null, \"timePeriodUnit\":null } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"comorbidConditions\":{ \"comorbidityConcurrentConditionsList\":[ { \"comorbidConditions\":null, \"otherComorbidCondition\":null, \"timePeriodAgo\":null, \"timePeriodUnit\":null, \"isForHistory\":null } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"medicationHistory\":{ \"medicationHistoryList\":[ { \"currentMedication\":null, \"timePeriodAgo\":null, \"timePeriodUnit\":null } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"femaleObstetricHistory\":{ \"totalNoOfPreg\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\", \"femaleObstetricHistoryList\":[ ] }, \"menstrualHistory\":{ \"menstrualCycleStatus\":null, \"menstrualCycleStatusID\":null, \"regularity\":null, \"cycleLength\":null, \"menstrualCyclelengthID\":null, \"menstrualFlowDurationID\":null, \"bloodFlowDuration\":null, \"menstrualProblemID\":null, \"problemName\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"familyHistory\":{ \"familyDiseaseList\":[ { \"diseaseTypeID\":null, \"diseaseType\":null, \"otherDiseaseType\":null, \"familyMembers\":null } ], \"isGeneticDisorder\":null, \"geneticDisorder\":null, \"isConsanguineousMarrige\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"personalHistory\":{ \"dietaryType\":null, \"physicalActivityType\":null, \"riskySexualPracticesStatus\":0, \"tobaccoUseStatus\":null, \"alcoholIntakeStatus\":null, \"allergyStatus\":null, \"tobaccoList\":[ ], \"alcoholList\":[ ], \"allergicList\":[ ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"childVaccineDetails\":{ \"childOptionalVaccineList\":[ { \"vaccineName\":null, \"otherVaccineName\":null, \"actualReceivingAge\":null, \"receivedFacilityName\":null } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"immunizationHistory\":{ \"immunizationList\":[ { \"defaultReceivingAge\":\"At Birth\", \"vaccines\":[ { \"vaccine\":\"BCG\", \"status\":false }, { \"vaccine\":\"HBV-0\", \"status\":false }, { \"vaccine\":\"OPV-0\", \"status\":false } ] }, { \"defaultReceivingAge\":\"6 Weeks\", \"vaccines\":[ { \"vaccine\":\"IPV-1\", \"status\":false }, { \"vaccine\":\"OPV\", \"status\":false }, { \"vaccine\":\"Pentavalent-1\", \"status\":false }, { \"vaccine\":\"Rota Vaccine-1\", \"status\":false } ] }, { \"defaultReceivingAge\":\"10 Weeks\", \"vaccines\":[ { \"vaccine\":\"IPV-2\", \"status\":false }, { \"vaccine\":\"OPV\", \"status\":false }, { \"vaccine\":\"Pentavalent-2\", \"status\":false }, { \"vaccine\":\"Rota Vaccine-2\", \"status\":false } ] }, { \"defaultReceivingAge\":\"14 Weeks\", \"vaccines\":[ { \"vaccine\":\"IPV-3 \", \"status\":false }, { \"vaccine\":\"OPV\", \"status\":false }, { \"vaccine\":\"Pentavalent-3\", \"status\":false }, { \"vaccine\":\"Rota Vaccine-3\", \"status\":false } ] }, { \"defaultReceivingAge\":\"9 Months\", \"vaccines\":[ { \"vaccine\":\"JE Vaccine\", \"status\":false }, { \"vaccine\":\"Measles Vaccine/MR\", \"status\":false }, { \"vaccine\":\"Vitamin A\", \"status\":false } ] }, { \"defaultReceivingAge\":\"16-24 Months\", \"vaccines\":[ { \"vaccine\":\"DPT-B 1\", \"status\":false }, { \"vaccine\":\"Measles/MR Vaccine\", \"status\":false }, { \"vaccine\":\"OPV\", \"status\":false } ] }, { \"defaultReceivingAge\":\"5 Years\", \"vaccines\":[ { \"vaccine\":\"\", \"status\":false } ] }, { \"defaultReceivingAge\":\"10 Years\", \"vaccines\":[ { \"vaccine\":\"TT\", \"status\":false } ] }, { \"defaultReceivingAge\":\"16 Years\", \"vaccines\":[ { \"vaccine\":\"TT\", \"status\":false } ] } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"developmentHistory\":{ \"grossMotorMilestones\":null, \"fineMotorMilestones\":null, \"socialMilestones\":null, \"languageMilestones\":null, \"developmentalProblems\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"feedingHistory\":{ \"typeOfFeed\":null, \"compFeedStartAge\":null, \"noOfCompFeedPerDay\":null, \"foodIntoleranceStatus\":0, \"typeofFoodIntolerance\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"perinatalHistroy\":{ \"deliveryPlaceID\":null, \"placeOfDelivery\":null, \"otherPlaceOfDelivery\":null, \"deliveryTypeID\":null, \"typeOfDelivery\":null, \"complicationAtBirthID\":null, \"complicationAtBirth\":null, \"otherComplicationAtBirth\":null, \"gestation\":null, \"birthWeight_kg\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" } }, \"examinationDetails\":{ \"generalExamination\":{ \"consciousness\":null, \"coherence\":null, \"cooperation\":null, \"comfortness\":null, \"builtAndAppearance\":null, \"gait\":null, \"dangerSigns\":null, \"typeOfDangerSigns\":null, \"pallor\":null, \"jaundice\":null, \"cyanosis\":null, \"clubbing\":null, \"lymphadenopathy\":null, \"lymphnodesInvolved\":null, \"typeOfLymphadenopathy\":null, \"edema\":null, \"extentOfEdema\":null, \"edemaType\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"headToToeExamination\":{ \"headtoToeExam\":null, \"head\":null, \"eyes\":null, \"ears\":null, \"nose\":null, \"oralCavity\":null, \"throat\":null, \"breastAndNipples\":null, \"trunk\":null, \"upperLimbs\":null, \"lowerLimbs\":null, \"skin\":null, \"hair\":null, \"nails\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"gastroIntestinalExamination\":{ \"inspection\":null, \"palpation_AbdomenTexture\":null, \"palpation_Liver\":null, \"palpation_Spleen\":null, \"palpation_Tenderness\":null, \"palpation_LocationOfTenderness\":null, \"percussion\":null, \"auscultation\":null, \"analRegion\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"cardioVascularExamination\":{ \"jugularVenousPulse_JVP\":null, \"apexbeatLocation\":null, \"apexbeatType\":null, \"firstHeartSound_S1\":null, \"secondHeartSound_S2\":null, \"additionalHeartSounds\":null, \"murmurs\":null, \"pericardialRub\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"respiratorySystemExamination\":{ \"trachea\":null, \"inspection\":null, \"signsOfRespiratoryDistress\":null, \"palpation\":null, \"auscultation_Stridor\":null, \"auscultation_BreathSounds\":null, \"auscultation_Crepitations\":null, \"auscultation_Wheezing\":null, \"auscultation_PleuralRub\":null, \"auscultation_ConductedSounds\":null, \"percussion\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"centralNervousSystemExamination\":{ \"handedness\":null, \"cranialNervesExamination\":null, \"motorSystem\":null, \"sensorySystem\":null, \"autonomicSystem\":null, \"cerebellarSigns\":null, \"signsOfMeningealIrritation\":null, \"skull\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"musculoskeletalSystemExamination\":{ \"joint_TypeOfJoint\":null, \"joint_Laterality\":null, \"joint_Abnormality\":null, \"upperLimb_Laterality\":null, \"upperLimb_Abnormality\":null, \"lowerLimb_Laterality\":null, \"lowerLimb_Abnormality\":null, \"chestWall\":null, \"spine\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"genitoUrinarySystemExamination\":{ \"renalAngle\":null, \"suprapubicRegion\":null, \"externalGenitalia\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" } } }";
		doctorSaveObjPve = "{ \"findings\":{ \"beneficiaryRegID\":\"7469\", \"benVisitID\":\"846\", \"providerServiceMapID\":\"1320\", \"clinicalObservation\":\"Lorem Ipsum is simply dummy text\", \"otherSymptoms\":\"Lorem Ipsum is simply dummy text\", \"significantFindings\":\"Lorem Ipsum \", \"complaints\":[ { \"chiefComplaint\":\"Abdominal Bloating\", \"chiefComplaintID\":1, \"duration\":\"23\", \"unitOfDuration\":\"Hours\", \"description\":\"Lorem Ipsum is simply dummy text\" }, { \"chiefComplaint\":\"Abdominal Distention\", \"chiefComplaintID\":5, \"duration\":\"24\", \"unitOfDuration\":\"Hours\", \"description\":\"Lorem Ipsum is simply dummy text\" } ], \"createdBy\":\"888\" }, \"diagnosis\":{ \"provisionalDiagnosis\":\"Lorem Ipsum is simply dummy text\", \"specialistAdvice\":\"Lorem Ipsum is simply dummy text\", \"beneficiaryRegID\":\"7469\", \"benVisitID\":\"846\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\" }, \"investigation\":{ \"externalInvestigation\":\"Lorem Ipsum is simply dummy text\", \"beneficiaryRegID\":\"7469\", \"benVisitID\":\"846\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\", \"laboratoryList\":[ { \"testID\":2, \"testName\":\"Blood Glucose Measurement\", \"isRadiologyImaging\":false }, { \"testID\":12, \"testName\":\"Blood grouping and Rh typing \", \"isRadiologyImaging\":false }, { \"testID\":17, \"testName\":\"BUN, Creatinine, Uric acid\", \"isRadiologyImaging\":false }, { \"testID\":29, \"testName\":\"CT Brain\", \"isRadiologyImaging\":true }, { \"testID\":30, \"testName\":\"CT Whole Spine \", \"isRadiologyImaging\":true }, { \"testID\":33, \"testName\":\"Flouroscopy- Urethrogram \", \"isRadiologyImaging\":true } ] }, \"prescription\":{ \"prescribedDrugs\":[ { \"specialInstruction\":\"After Food\", \"genericDrugName\":\"Glimepride Tablets IP 1mg\", \"dose\":\"Days \", \"frequency\":\"Once Daily(OD)\", \"drugForm\":\"Capsule\", \"drugDuration\":\"2\", \"qih\":10 }, { \"specialInstruction\":\"After Food\", \"genericDrugName\":\"Metformin Tablets IP 500 mg\", \"dose\":\"Days \", \"frequency\":\"Once Daily(OD)\", \"drugForm\":\"Capsule\", \"drugDuration\":\"2\", \"qih\":3 } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":\"846\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\" }, \"refer\":{ \"referredToInstituteID\":null, \"refrredToAdditionalServiceList\":[ 3, 1, 5 ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":\"846\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\" } }";
		updateHistoryPve = "{ \"pastHistory\":{ \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\", \"pastIllness\":[ { \"illnessTypeID\":\"6\", \"illnessType\":\"Asthma\", \"otherIllnessType\":null, \"timePeriodAgo\":\"21\", \"timePeriodUnit\":\"Days\" } ], \"pastSurgery\":[ { \"surgeryID\":\"3\", \"surgeryType\":\"Appendicectomy\", \"otherSurgeryType\":null, \"timePeriodAgo\":\"4\", \"timePeriodUnit\":\"Days\" } ] }, \"comorbidConditions\":{ \"comorbidityConcurrentConditionsList\":[ { \"otherComorbidCondition\":null, \"timePeriodAgo\":null, \"timePeriodUnit\":null, \"isForHistory\":null } ], \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"medicationHistory\":{ \"medicationHistoryList\":[ { \"currentMedication\":null, \"timePeriodAgo\":0, \"timePeriodUnit\":\"\" } ], \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"femaleObstetricHistory\":{ \"totalNoOfPreg\":null, \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\", \"femaleObstetricHistoryList\":[ ] }, \"menstrualHistory\":{ \"menstrualCycleStatusID\":null, \"regularity\":null, \"menstrualCyclelengthID\":null, \"menstrualFlowDurationID\":null, \"menstrualProblemID\":null, \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"familyHistory\":{ \"familyDiseaseList\":[ { \"diseaseTypeID\":null, \"diseaseType\":null, \"otherDiseaseType\":null, \"familyMembers\":[ ] } ], \"isGeneticDisorder\":null, \"geneticDisorder\":null, \"isConsanguineousMarrige\":null, \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"personalHistory\":{ \"dietaryType\":null, \"physicalActivityType\":null, \"riskySexualPracticesStatus\":0, \"tobaccoUseStatus\":null, \"alcoholIntakeStatus\":null, \"allergyStatus\":null, \"tobaccoList\":[ { \"tobaccoUseTypeID\":null, \"tobaccoUseType\":null, \"otherTobaccoUseType\":null, \"numberperDay\":null, \"duration\":null, \"durationUnit\":null } ], \"alcoholList\":[ { \"alcoholTypeID\":null, \"typeOfAlcohol\":null, \"otherAlcoholType\":null, \"alcoholIntakeFrequency\":null, \"avgAlcoholConsumption\":null, \"duration\":null, \"durationUnit\":null } ], \"allergicList\":[ { \"allergyType\":null, \"allergyName\":null, \"typeOfAllergicReactions\":[ ], \"otherAllergicReaction\":null, \"enableOtherAllergy\":false } ], \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"childVaccineDetails\":{ \"childOptionalVaccineList\":[ ], \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"immunizationHistory\":{ \"immunizationList\":[ ], \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"developmentHistory\":{ \"grossMotorMilestones\":null, \"fineMotorMilestones\":null, \"socialMilestones\":null, \"languageMilestones\":null, \"developmentProblems\":null, \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"feedingHistory\":{ \"typeOfFeed\":null, \"compFeedStartAge\":null, \"noOfCompFeedPerDay\":null, \"foodIntoleranceStatus\":null, \"typeofFoodIntolerance\":null, \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"perinatalHistroy\":{ \"deliveryPlaceID\":null, \"placeOfDelivery\":null, \"otherPlaceOfDelivery\":null, \"deliveryTypeID\":null, \"typeOfDelivery\":null, \"complicationAtBirthID\":null, \"complicationAtBirth\":null, \"otherComplicationAtBirth\":null, \"gestation\":null, \"birthWeight_kg\":null, \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" } }";
		updateVitalPve = "{\"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"weight_Kg\":null, \"height_cm\":null, \"waistCircumference_cm\":null, \"hipCircumference_cm\":null, \"bMI\":null, \"waistHipRatio\":null, \"temperature\":null, \"pulseRate\":null, \"systolicBP_1stReading\":null, \"diastolicBP_1stReading\":null, \"bloodGlucose_Fasting\":null, \"bloodGlucose_Random\":null, \"bloodGlucose_2hr_PP\":null, \"respiratoryRate\":null, \"createdBy\":\"891\" }";
		updateExaminationPve = "{ \"generalExamination\":{ \"consciousness\":null, \"coherence\":null, \"cooperation\":null, \"comfortness\":null, \"builtAndAppearance\":null, \"gait\":null, \"dangerSigns\":null, \"typeOfDangerSigns\":null, \"pallor\":null, \"jaundice\":null, \"cyanosis\":null, \"clubbing\":null, \"lymphadenopathy\":null, \"lymphnodesInvolved\":null, \"typeOfLymphadenopathy\":null, \"edema\":null, \"extentOfEdema\":null, \"edemaType\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"headToToeExamination\":{ \"headtoToeExam\":null, \"head\":null, \"eyes\":null, \"ears\":null, \"nose\":null, \"oralCavity\":null, \"throat\":null, \"breastAndNipples\":null, \"trunk\":null, \"upperLimbs\":null, \"lowerLimbs\":null, \"skin\":null, \"hair\":null, \"nails\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"gastroIntestinalExamination\":{ \"inspection\":null, \"palpation_AbdomenTexture\":null, \"palpation_Liver\":null, \"palpation_Spleen\":null, \"palpation_Tenderness\":null, \"palpation_LocationOfTenderness\":null, \"percussion\":null, \"auscultation\":null, \"analRegion\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"cardioVascularExamination\":{ \"jugularVenousPulse_JVP\":null, \"apexbeatLocation\":null, \"apexbeatType\":null, \"firstHeartSound_S1\":null, \"secondHeartSound_S2\":null, \"additionalHeartSounds\":null, \"murmurs\":null, \"pericardialRub\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"respiratorySystemExamination\":{ \"trachea\":null, \"inspection\":null, \"signsOfRespiratoryDistress\":null, \"palpation\":null, \"auscultation_Stridor\":null, \"auscultation_BreathSounds\":null, \"auscultation_Crepitations\":null, \"auscultation_Wheezing\":null, \"auscultation_PleuralRub\":null, \"auscultation_ConductedSounds\":null, \"percussion\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"centralNervousSystemExamination\":{ \"handedness\":null, \"cranialNervesExamination\":null, \"motorSystem\":null, \"sensorySystem\":null, \"autonomicSystem\":null, \"cerebellarSigns\":null, \"signsOfMeningealIrritation\":null, \"skull\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"musculoskeletalSystemExamination\":{ \"joint_TypeOfJoint\":null, \"joint_Laterality\":null, \"joint_Abnormality\":null, \"upperLimb_Laterality\":null, \"upperLimb_Abnormality\":null, \"lowerLimb_Laterality\":null, \"lowerLimb_Abnormality\":null, \"chestWall\":null, \"spine\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"genitoUrinarySystemExamination\":{ \"renalAngle\":null, \"suprapubicRegion\":null, \"externalGenitalia\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" } }";

		jsnOBJPve = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(requestObjPve);
		jsnOBJPve = jsnElmnt.getAsJsonObject();

		doctorSaveJsnPve = new JsonObject();
		jsnElmnt = jsnParser.parse(doctorSaveObjPve);
		doctorSaveJsnPve = jsnElmnt.getAsJsonObject();

		jsnOBJNve = new JsonObject();
		JsonElement jsnElmntNve = jsnParser.parse(requestObjNve);
		jsnOBJNve = jsnElmntNve.getAsJsonObject();

		updateHstryJsnPve = new JsonObject();
		JsonElement updatejsnElmntPve = jsnParser.parse(updateHistoryPve);
		updateHstryJsnPve = updatejsnElmntPve.getAsJsonObject();

		updateVitalJsnPve = new JsonObject();
		updatejsnElmntPve = jsnParser.parse(updateVitalPve);
		updateVitalJsnPve = updatejsnElmntPve.getAsJsonObject();

		updateExaminationJsnPve = new JsonObject();
		updatejsnElmntPve = jsnParser.parse(updateExaminationPve);
		updateExaminationJsnPve = updatejsnElmntPve.getAsJsonObject();

		// Expected Responses
		pastHistoryDataPveRes = "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"Illness_Type\",\"columnName\":\"Illness Type\"},{\"keyName\":\"Other_Illness_Type\",\"columnName\":\"Other Illness Type\"},{\"keyName\":\"Year_Of_Illness\",\"columnName\":\"Year Of Illness\"},{\"keyName\":\"Surgery_Type\",\"columnName\":\"Surgery Type\"},{\"keyName\":\"Other_Surgery_Type\",\"columnName\":\"Other Surgery Type\"},{\"keyName\":\"Year_Of_Surgery\",\"columnName\":\"Year Of Surgery\"}]}";
		pastHistoryDataNveRes = "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"Illness_Type\",\"columnName\":\"Illness Type\"},{\"keyName\":\"Other_Illness_Type\",\"columnName\":\"Other Illness Type\"},{\"keyName\":\"Year_Of_Illness\",\"columnName\":\"Year Of Illness\"},{\"keyName\":\"Surgery_Type\",\"columnName\":\"Surgery Type\"},{\"keyName\":\"Other_Surgery_Type\",\"columnName\":\"Other Surgery Type\"},{\"keyName\":\"Year_Of_Surgery\",\"columnName\":\"Year Of Surgery\"}]}";
		tobaccoHistoryDataPveRes = "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"dietaryType\",\"columnName\":\"Dietary Type\"},{\"keyName\":\"physicalActivityType\",\"columnName\":\"Physical Activity Type\"},{\"keyName\":\"tobaccoUseStatus\",\"columnName\":\"Tobacco Use Status\"},{\"keyName\":\"tobaccoUseType\",\"columnName\":\"Tobacco Use Type\"},{\"keyName\":\"otherTobaccoUseType\",\"columnName\":\"Other Tobacco Use Type\"},{\"keyName\":\"numberperDay\",\"columnName\":\"Number Per Day\"},{\"keyName\":\"tobacco_use_duration\",\"columnName\":\"Tobacco Use Start Date\"},{\"keyName\":\"riskySexualPracticeStatus\",\"columnName\":\"Risky Sexual Practices Status\"}]}";
		menstrualHistoryDataPveRes = "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"regularity\",\"columnName\":\"Regularity\"},{\"keyName\":\"cycleLength\",\"columnName\":\"Cycle Length\"},{\"keyName\":\"bloodFlowDuration\",\"columnName\":\"Blood Flow Duration\"},{\"keyName\":\"problemName\",\"columnName\":\"Problem Name\"},{\"keyName\":\"lmp_date\",\"columnName\":\"LMPDate\"}]}";
		alcoholHistoryDataPveRes = "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"dietaryType\",\"columnName\":\"Dietary Type\"},{\"keyName\":\"physicalActivityType\",\"columnName\":\"Physical Activity Type\"},{\"keyName\":\"alcoholIntakeStatus\",\"columnName\":\"Alcohol Intake Status\"},{\"keyName\":\"alcoholType\",\"columnName\":\"Alcohol Type\"},{\"keyName\":\"otherAlcoholType\",\"columnName\":\"Other Alcohol Type\"},{\"keyName\":\"alcoholIntakeFrequency\",\"columnName\":\"Alcohol Intake Frequency\"},{\"keyName\":\"avgAlcoholConsumption\",\"columnName\":\"Avg Alcohol Consumption\"},{\"keyName\":\"alcohol_use_duration\",\"columnName\":\"Alcohol Use Started Date\"},{\"keyName\":\"riskySexualPracticeStatus\",\"columnName\":\"Risky Sexual Practices Status\"}]}";
		allergyHistoryDataPveRes = "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"allergyStatus\",\"columnName\":\"Allergy Status\"},{\"keyName\":\"allergyType\",\"columnName\":\"Allergy Type\"},{\"keyName\":\"allergenName\",\"columnName\":\"Allergy Name\"},{\"keyName\":\"allergicReactionType\",\"columnName\":\"Allergic Reaction Type\"},{\"keyName\":\"otherAllergicReaction\",\"columnName\":\"Other Allergic Reaction\"},{\"keyName\":\"remarks\",\"columnName\":\"Remarks\"}]}";
		medicationHistoryDataPveRes = "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"currentMedication\",\"columnName\":\"Current Medication\"},{\"keyName\":\"medication_year\",\"columnName\":\"Date\"}]}";
		comorbidHistoryDataPveRes = "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"comorbidCondition\",\"columnName\":\"Comorbid Condition\"},{\"keyName\":\"otherComorbidCondition\",\"columnName\":\"Other Comorbid Condition\"},{\"keyName\":\"date\",\"columnName\":\"Date\"}]}";
		obstetricHistoryDataPveRes = "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"pregOrder\",\"columnName\":\"Preg Order\"},{\"keyName\":\"pregComplicationType\",\"columnName\":\"Preg Complication Type\"},{\"keyName\":\"otherPregComplication\",\"columnName\":\"Other Preg Complication\"},{\"keyName\":\"durationType\",\"columnName\":\"Duration Type\"},{\"keyName\":\"deliveryType\",\"columnName\":\"Delivery Type\"},{\"keyName\":\"deliveryPlace\",\"columnName\":\"Delivery Place\"},{\"keyName\":\"otherDeliveryPlace\",\"columnName\":\"Other Delivery Place\"},{\"keyName\":\"deliveryComplicationType\",\"columnName\":\"Delivery Complication Type\"},{\"keyName\":\"otherDeliveryComplication\",\"columnName\":\"Other Delivery Complication\"},{\"keyName\":\"pregOutcome\",\"columnName\":\"Preg Outcome\"},{\"keyName\":\"postpartumComplicationType\",\"columnName\":\"Postpartum Complication Type\"},{\"keyName\":\"otherPostpartumCompType\",\"columnName\":\"Other Postpartum CompType\"},{\"keyName\":\"postNatalComplication\",\"columnName\":\"Post Natal Complication\"},{\"keyName\":\"otherPostNatalComplication\",\"columnName\":\"Other Post Natal Complication\"},{\"keyName\":\"congenitalAnomalies\",\"columnName\":\"Congenital Anomalies\"},{\"keyName\":\"newBornComplication\",\"columnName\":\"New Born Complication\"},{\"keyName\":\"otherNewBornComplication\",\"columnName\":\"Other New Born Complication\"}]}";
		familyHistoryDataPveRes = "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"familyMember\",\"columnName\":\"Family Member\"},{\"keyName\":\"diseaseType\",\"columnName\":\"Disease Type\"},{\"keyName\":\"otherDiseaseType\",\"columnName\":\"Other Disease Type\"},{\"keyName\":\"isGeneticDisorder\",\"columnName\":\"Is Genetic Disorder\"},{\"keyName\":\"geneticDisorder\",\"columnName\":\"Genetic Disorder\"},{\"keyName\":\"isConsanguineousMarrige\",\"columnName\":\"Is Consanguineous Marrige\"}]}";
		immunizationHistoryDataPveRes = "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"defaultReceivingAge\",\"columnName\":\"Default Receiving Age\"},{\"keyName\":\"vaccineName\",\"columnName\":\"Vaccine Name\"},{\"keyName\":\"status\",\"columnName\":\"Status\"}]}";
		childVaccineHistoryDataPveRes = "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"defaultReceivingAge\",\"columnName\":\"Default Receiving Age\"},{\"keyName\":\"vaccineName\",\"columnName\":\"Vaccine Name\"},{\"keyName\":\"actualReceivingAge\",\"columnName\":\"Actual Receiving Age\"},{\"keyName\":\"receivedFacilityName\",\"columnName\":\"Received Facility Name\"}]}";

		visitDetailsPveRes = "{GOPDNurseVisitDetail={}, BenChiefComplaints=[]}";
		historyDetailsPveRes = "{\"childOptionalVaccineHistory\":{\"childOptionalVaccineList\":[]},\"ComorbidityConditions\":{\"comorbidityConcurrentConditionsList\":[]},\"ImmunizationHistory\":{\"immunizationList\":[]},\"MedicationHistory\":{\"medicationHistoryList\":[]},\"FemaleObstetricHistory\":{\"femaleObstetricHistoryList\":[]}}";
		vitalDetailsPveRes = "{benAnthropometryDetail={}, benPhysicalVitalDetail={}}";
		examinationDetailsPveRes = "{\"gastrointestinalExamination\":{},\"generalExamination\":{\"typeOfDangerSigns\":[]},\"headToToeExamination\":{},\"cardiovascularExamination\":{},\"centralNervousExamination\":{},\"respiratoryExamination\":{},\"musculoskeletalExamination\":{},\"genitourinaryExamination\":{}}";

		when(registrarRepoBenData.updateBenFlowStatus('N', beneficiaryRegID)).thenReturn(1);

		/* Mocking Visit Details Repo's */
		BeneficiaryVisitDetail beneficiaryVisitDetail = spy(BeneficiaryVisitDetail.class);
		beneficiaryVisitDetail.setBenVisitID(1L);
		when(benVisitDetailRepoMock.save(isA(BeneficiaryVisitDetail.class))).thenReturn(beneficiaryVisitDetail);

		BenChiefComplaint benChiefComplaint = new BenChiefComplaint();
		benChiefComplaint.setBenChiefComplaintID(1L);
		List<BenChiefComplaint> chiefcmplntres = new ArrayList<BenChiefComplaint>();
		chiefcmplntres.add(benChiefComplaint);
		when(benChiefComplaintRepoMock.save(Matchers.anyListOf(BenChiefComplaint.class))).thenReturn(chiefcmplntres);

		/* Mocking History Repo's */
		BenMedHistory benMedHistory = spy(BenMedHistory.class);
		benMedHistory.setBenMedHistoryID(1L);
		List<BenMedHistory> medres = new ArrayList<BenMedHistory>();
		medres.add(benMedHistory);
		when(benMedHistoryRepoMock.save(Matchers.anyListOf(BenMedHistory.class))).thenReturn(medres);

		BencomrbidityCondDetails bencomrbidityCondDetails = spy(BencomrbidityCondDetails.class);
		bencomrbidityCondDetails.setID(1L);
		List<BencomrbidityCondDetails> comorbidRes = new ArrayList<BencomrbidityCondDetails>();
		comorbidRes.add(bencomrbidityCondDetails);
		when(bencomrbidityCondRepoMock.save(Matchers.anyListOf(BencomrbidityCondDetails.class)))
				.thenReturn(comorbidRes);

		BenMedicationHistory benMedicationHistory = spy(BenMedicationHistory.class);
		benMedicationHistory.setID(1L);
		List<BenMedicationHistory> medRes = new ArrayList<BenMedicationHistory>();
		medRes.add(benMedicationHistory);
		when(benMedicationHistoryRepoMock.save(Matchers.anyListOf(BenMedicationHistory.class))).thenReturn(medRes);

		FemaleObstetricHistory femaleObstetricHistory = spy(FemaleObstetricHistory.class);
		femaleObstetricHistory.setObstetricHistoryID(1L);
		List<FemaleObstetricHistory> femaleObsRes = new ArrayList<FemaleObstetricHistory>();
		femaleObsRes.add(femaleObstetricHistory);
		when(femaleObstetricHistoryRepoMock.save(Matchers.anyListOf(FemaleObstetricHistory.class)))
				.thenReturn(femaleObsRes);

		BenMenstrualDetails benMenstrualDetails = spy(BenMenstrualDetails.class);
		benMenstrualDetails.setBenMenstrualID(1);
		when(benMenstrualDetailsRepoMock.save(isA(BenMenstrualDetails.class))).thenReturn(benMenstrualDetails);

		BenFamilyHistory benFamilyHistory = spy(BenFamilyHistory.class);
		benFamilyHistory.setID(1L);
		List<BenFamilyHistory> familyRes = new ArrayList<BenFamilyHistory>();
		familyRes.add(benFamilyHistory);
		when(benFamilyHistoryRepoMock.save(Matchers.anyListOf(BenFamilyHistory.class))).thenReturn(familyRes);

		BenPersonalHabit benPersonalHabit = spy(BenPersonalHabit.class);
		benPersonalHabit.setBenPersonalHabitID(1);
		List<BenPersonalHabit> habitRes = new ArrayList<BenPersonalHabit>();
		habitRes.add(benPersonalHabit);
		when(benPersonalHabitRepoMock.save(Matchers.anyListOf(BenPersonalHabit.class))).thenReturn(habitRes);

		BenAllergyHistory benAllergyHistory = spy(BenAllergyHistory.class);
		benAllergyHistory.setID(1L);
		List<BenAllergyHistory> allergyRes = new ArrayList<BenAllergyHistory>();
		allergyRes.add(benAllergyHistory);
		when(benAllergyHistoryRepoMock.save(Matchers.anyListOf(BenAllergyHistory.class))).thenReturn(allergyRes);

		ChildOptionalVaccineDetail optionalVaccineDetail = spy(ChildOptionalVaccineDetail.class);
		optionalVaccineDetail.setID(1L);
		List<ChildOptionalVaccineDetail> vaccineRes = new ArrayList<ChildOptionalVaccineDetail>();
		vaccineRes.add(optionalVaccineDetail);
		when(childOptionalVaccineDetailRepoMock.save(Matchers.anyListOf(ChildOptionalVaccineDetail.class)))
				.thenReturn(vaccineRes);

		ChildVaccineDetail1 childVaccineDetail1 = spy(ChildVaccineDetail1.class);
		childVaccineDetail1.setID(1L);
		List<ChildVaccineDetail1> childVaccineRes = new ArrayList<ChildVaccineDetail1>();
		childVaccineRes.add(childVaccineDetail1);
		when(childVaccineDetail1RepoMock.save(Matchers.anyListOf(ChildVaccineDetail1.class)))
				.thenReturn(childVaccineRes);

		/*
		 * if (jsnOBJPve != null && jsnOBJPve.has("visitDetails") &&
		 * !jsnOBJPve.get("visitDetails").isJsonNull()) { JsonObject visitDetailsOBJ =
		 * jsnOBJPve.getAsJsonObject("visitDetails"); if (visitDetailsOBJ != null &&
		 * visitDetailsOBJ.has("visitDetails") &&
		 * !visitDetailsOBJ.get("visitDetails").isJsonNull()) { // Save Beneficiary
		 * visit details BeneficiaryVisitDetail benVisitDetailsOBJ =
		 * InputMapper.gson().fromJson(visitDetailsOBJ.get("visitDetails"),
		 * BeneficiaryVisitDetail.class);
		 * 
		 * when(commonNurseServiceImpl.saveBeneficiaryVisitDetails(benVisitDetailsOBJ)).
		 * thenReturn(1L); } } BenChiefComplaint benChiefComplaint =
		 * mock(BenChiefComplaint.class); List benChiefComplaintList = new
		 * ArrayList<BenChiefComplaint>(); benChiefComplaintList.add(benChiefComplaint);
		 * when(commonNurseServiceImpl.saveBenChiefComplaints(benChiefComplaintList)).
		 * thenReturn(1);
		 * 
		 * Mocking for HistoryDetails Internal Methods BenMedHistory benMedHistory =
		 * mock(BenMedHistory.class);
		 * when(commonNurseServiceImpl.saveBenPastHistory(benMedHistory)).thenReturn(1L)
		 * ;
		 * 
		 * WrapperComorbidCondDetails wrapperComorbidCondDetails =
		 * mock(WrapperComorbidCondDetails.class);
		 * when(commonNurseServiceImpl.saveBenComorbidConditions(
		 * wrapperComorbidCondDetails)).thenReturn(1L);
		 * 
		 * WrapperMedicationHistory wrapperMedicationHistory =
		 * mock(WrapperMedicationHistory.class);
		 * when(commonNurseServiceImpl.saveBenMedicationHistory(wrapperMedicationHistory
		 * )).thenReturn(1L);
		 * 
		 * WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory =
		 * mock(WrapperFemaleObstetricHistory.class);
		 * when(commonNurseServiceImpl.saveFemaleObstetricHistory(
		 * wrapperFemaleObstetricHistory)).thenReturn(1L);
		 * 
		 * BenMenstrualDetails menstrualDetails = mock(BenMenstrualDetails.class);
		 * when(commonNurseServiceImpl.saveBenMenstrualHistory(menstrualDetails)).
		 * thenReturn(1);
		 * 
		 * BenFamilyHistory benFamilyHistory = mock(BenFamilyHistory.class);
		 * when(commonNurseServiceImpl.saveBenFamilyHistory(benFamilyHistory)).
		 * thenReturn(1L);
		 * 
		 * BenPersonalHabit personalHabit = mock(BenPersonalHabit.class);
		 * when(commonNurseServiceImpl.savePersonalHistory(personalHabit)).thenReturn(1)
		 * ;
		 * 
		 * BenAllergyHistory benAllergyHistory = mock(BenAllergyHistory.class);
		 * when(commonNurseServiceImpl.saveAllergyHistory(benAllergyHistory)).thenReturn
		 * (1L);
		 * 
		 * WrapperChildOptionalVaccineDetail wrapperChildVaccineDetail =
		 * mock(WrapperChildOptionalVaccineDetail.class);
		 * when(commonNurseServiceImpl.saveChildOptionalVaccineDetail(
		 * wrapperChildVaccineDetail)).thenReturn(1L);
		 * 
		 * WrapperImmunizationHistory wrapperImmunizationHistory =
		 * mock(WrapperImmunizationHistory.class);
		 * when(commonNurseServiceImpl.saveImmunizationHistory(
		 * wrapperImmunizationHistory)).thenReturn(1L);
		 * 
		 * BenChildDevelopmentHistory benChildDevelopmentHistory =
		 * mock(BenChildDevelopmentHistory.class);
		 * when(generalOPDNurseServiceImplMock.saveChildDevelopmentHistory(
		 * benChildDevelopmentHistory)).thenReturn(1L);
		 * 
		 * ChildFeedingDetails childFeedingDetails = mock(ChildFeedingDetails.class);
		 * when(generalOPDNurseServiceImplMock.saveChildFeedingHistory(
		 * childFeedingDetails)).thenReturn(1L);
		 * 
		 * PerinatalHistory perinatalHistory = mock(PerinatalHistory.class);
		 * when(generalOPDNurseServiceImplMock.savePerinatalHistory(perinatalHistory)).
		 * thenReturn(1L);
		 */

		/* Mocking Vital Repo's */
		BenAnthropometryDetail benAnthropometryDetail = spy(BenAnthropometryDetail.class);
		benAnthropometryDetail.setID(1L);
		when(benAnthropometryRepoMock.save(isA(BenAnthropometryDetail.class))).thenReturn(benAnthropometryDetail);

		BenPhysicalVitalDetail benPhysicalVitalDetail = spy(BenPhysicalVitalDetail.class);
		benPhysicalVitalDetail.setID(1L);
		when(benPhysicalVitalRepoMock.save(isA(BenPhysicalVitalDetail.class))).thenReturn(benPhysicalVitalDetail);

		/* Mocking Examination Repo's */
		PhyGeneralExamination phyGeneralExamination = spy(PhyGeneralExamination.class);
		phyGeneralExamination.setID(1L);
		when(phyGeneralExaminationRepoMock.save(isA(PhyGeneralExamination.class))).thenReturn(phyGeneralExamination);

		PhyHeadToToeExamination phyHeadToToeExamination = spy(PhyHeadToToeExamination.class);
		phyHeadToToeExamination.setID(1L);
		when(phyHeadToToeExaminationRepoMock.save(isA(PhyHeadToToeExamination.class)))
				.thenReturn(phyHeadToToeExamination);

		SysCardiovascularExamination sysCardiovascularExamination = spy(SysCardiovascularExamination.class);
		sysCardiovascularExamination.setID(1L);
		when(sysCardiovascularExaminationRepoMock.save(isA(SysCardiovascularExamination.class)))
				.thenReturn(sysCardiovascularExamination);

		SysRespiratoryExamination sysRespiratoryExamination = spy(SysRespiratoryExamination.class);
		sysRespiratoryExamination.setID(1L);
		when(sysRespiratoryExaminationRepoMock.save(isA(SysRespiratoryExamination.class)))
				.thenReturn(sysRespiratoryExamination);

		SysCentralNervousExamination sysCentralNervousExamination = spy(SysCentralNervousExamination.class);
		sysCentralNervousExamination.setID(1L);
		when(sysCentralNervousExaminationRepoMock.save(isA(SysCentralNervousExamination.class)))
				.thenReturn(sysCentralNervousExamination);

		SysMusculoskeletalSystemExamination sysMusculoskeletalSystemExamination = spy(
				SysMusculoskeletalSystemExamination.class);
		sysMusculoskeletalSystemExamination.setID(1L);
		when(sysMusculoskeletalSystemExaminationRepoMock.save(isA(SysMusculoskeletalSystemExamination.class)))
				.thenReturn(sysMusculoskeletalSystemExamination);

		SysGenitourinarySystemExamination sysGenitourinarySystemExamination = spy(
				SysGenitourinarySystemExamination.class);
		sysGenitourinarySystemExamination.setID(1L);
		when(sysGenitourinarySystemExaminationRepoMock.save(isA(SysGenitourinarySystemExamination.class)))
				.thenReturn(sysGenitourinarySystemExamination);

		/* Fetch API mocks */
		/*
		 * SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd"); Object[] obj= {new
		 * Date(sdf.parse("2018-02-16").getTime()), "Cataract", null, new
		 * Date(sdf.parse("2018-02-15").getTime()), "Cesarean Section/LSCS", null, new
		 * Date(sdf.parse("2018-02-09").getTime())};
		 * 
		 * ArrayList<Object[]> pastHistryRes = new ArrayList<Object[]>();
		 * pastHistryRes.add(obj);
		 * when(benMedHistoryRepoMock.getBenPastHistory(beneficiaryRegID)).thenReturn(
		 * pastHistryRes);
		 */

		ArrayList<Object[]> getGOPDRes = new ArrayList<Object[]>();
		when(benMedHistoryRepoMock.getBenPastHistory(beneficiaryRegID)).thenReturn(getGOPDRes);
		when(benPersonalHabitRepoMock.getBenPersonalTobaccoHabitDetail(beneficiaryRegID)).thenReturn(getGOPDRes);
		when(benPersonalHabitRepoMock.getBenPersonalAlcoholHabitDetail(beneficiaryRegID)).thenReturn(getGOPDRes);
		when(benMedicationHistoryRepoMock.getBenMedicationHistoryDetail(beneficiaryRegID)).thenReturn(getGOPDRes);
		when(benFamilyHistoryRepoMock.getBenFamilyHistoryDetail(beneficiaryRegID)).thenReturn(getGOPDRes);
		when(benMenstrualDetailsRepoMock.getBenMenstrualDetail(beneficiaryRegID)).thenReturn(getGOPDRes);
		when(femaleObstetricHistoryRepoMock.getBenFemaleObstetricHistoryDetail(beneficiaryRegID))
				.thenReturn(getGOPDRes);
		when(bencomrbidityCondRepoMock.getBencomrbidityCondDetails(beneficiaryRegID)).thenReturn(getGOPDRes);
		when(childOptionalVaccineDetailRepoMock.getBenOptionalVaccineDetail(beneficiaryRegID)).thenReturn(getGOPDRes);
		when(childVaccineDetail1RepoMock.getBenChildVaccineDetails(beneficiaryRegID)).thenReturn(getGOPDRes);

		BeneficiaryVisitDetail bvd = spy(BeneficiaryVisitDetail.class);
		when(benVisitDetailRepoMock.getVisitDetails(beneficiaryRegID, benVisitID)).thenReturn(bvd);

		when(benChiefComplaintRepoMock.getBenChiefComplaints(beneficiaryRegID, benVisitID)).thenReturn(getGOPDRes);

		when(benMedHistoryRepoMock.getBenPastHistory(beneficiaryRegID, benVisitID)).thenReturn(getGOPDRes);
		when(bencomrbidityCondRepoMock.getBencomrbidityCondDetails(beneficiaryRegID, benVisitID))
				.thenReturn(getGOPDRes);
		when(benMedicationHistoryRepoMock.getBenMedicationHistoryDetail(beneficiaryRegID, benVisitID))
				.thenReturn(getGOPDRes);

		when(benPersonalHabitRepoMock.getBenPersonalHabitDetail(beneficiaryRegID, benVisitID)).thenReturn(getGOPDRes);

		when(benAllergyHistoryRepoMock.getBenPersonalAllergyDetail(beneficiaryRegID, benVisitID))
				.thenReturn(getGOPDRes);

		when(benFamilyHistoryRepoMock.getBenFamilyHistoryDetail(beneficiaryRegID, benVisitID)).thenReturn(getGOPDRes);

		when(benMenstrualDetailsRepoMock.getBenMenstrualDetail(beneficiaryRegID, benVisitID)).thenReturn(getGOPDRes);

		when(femaleObstetricHistoryRepoMock.getBenFemaleObstetricHistoryDetail(beneficiaryRegID, benVisitID))
				.thenReturn(getGOPDRes);

		when(childOptionalVaccineDetailRepoMock.getBenOptionalVaccineDetail(beneficiaryRegID, benVisitID))
				.thenReturn(getGOPDRes);

		when(childVaccineDetail1RepoMock.getBenChildVaccineDetails(beneficiaryRegID, benVisitID))
				.thenReturn(getGOPDRes);

		BenAnthropometryDetail bav = new BenAnthropometryDetail();
		when(benAnthropometryRepoMock.getBenAnthropometryDetail(beneficiaryRegID, benVisitID)).thenReturn(bav);

		BenPhysicalVitalDetail bpv = new BenPhysicalVitalDetail();
		when(benPhysicalVitalRepoMock.getBenPhysicalVitalDetail(beneficiaryRegID, benVisitID)).thenReturn(bpv);

		when(phyGeneralExaminationRepoMock.getPhyGeneralExaminationData(beneficiaryRegID, benVisitID))
				.thenReturn(new PhyGeneralExamination());
		when(phyHeadToToeExaminationRepoMock.getPhyHeadToToeExaminationData(beneficiaryRegID, benVisitID))
				.thenReturn(new PhyHeadToToeExamination());

		when(sysCardiovascularExaminationRepoMock.getSysCardiovascularExaminationData(beneficiaryRegID, benVisitID))
				.thenReturn(new SysCardiovascularExamination());

		when(sysRespiratoryExaminationRepoMock.getSysRespiratoryExaminationData(beneficiaryRegID, benVisitID))
				.thenReturn(new SysRespiratoryExamination());
		when(sysCentralNervousExaminationRepoMock.getSysCentralNervousExaminationData(beneficiaryRegID, benVisitID))
				.thenReturn(new SysCentralNervousExamination());

		when(sysMusculoskeletalSystemExaminationRepoMock.getSysMusculoskeletalSystemExamination(beneficiaryRegID,
				benVisitID)).thenReturn(new SysMusculoskeletalSystemExamination());

		when(sysGenitourinarySystemExaminationRepoMock.getSysGenitourinarySystemExaminationData(beneficiaryRegID,
				benVisitID)).thenReturn(new SysGenitourinarySystemExamination());

		// Mocking Update Repo's
		/*
		 * when(commonNurseServiceImpl.updateBenChiefComplaints(Matchers.anyListOf(
		 * BenChiefComplaint.class))).thenReturn(1);
		 */

//		when(benMenstrualDetailsRepoMock.updateMenstrualDetails(Matchers.anyShort(),Matchers.anyString(), Matchers.anyShort(), Matchers.anyString(), Matchers.anyShort(),
//				Matchers.anyString(), Matchers.anyShort(), Matchers.anyString(), Matchers.any(), Matchers.anyString(), Matchers.anyString(), Matchers.anyLong(), 
//				Matchers.anyLong())).thenReturn(1);

		when(childVaccineDetail1RepoMock.updateChildANCImmunization(Matchers.any(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyLong(), Matchers.anyLong(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(1);

		when(benAnthropometryRepoMock.updateANCCareDetails(Matchers.anyDouble(), Matchers.anyDouble(),
				Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble(),
				Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyLong(), Matchers.anyLong())).thenReturn(1);

		when(benPhysicalVitalRepoMock.updatePhysicalVitalDetails(Matchers.anyDouble(), Matchers.anyShort(),
				Matchers.anyShort(), Matchers.anyShort(), Matchers.anyShort(), Matchers.anyShort(), Matchers.anyShort(),
				Matchers.anyShort(), Matchers.anyShort(), Matchers.anyShort(), Matchers.anyShort(), Matchers.anyShort(),
				Matchers.anyString(), Matchers.anyShort(), Matchers.anyShort(), Matchers.anyShort(),
				Matchers.anyShort(), Matchers.anyShort(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyLong(), Matchers.anyLong(), Matchers.anyDouble())).thenReturn(1);

//		when(phyGeneralExaminationRepoMock.updatePhyGeneralExamination(Matchers.anyVararg(), Matchers.anyString(), Matchers.anyString(), 
//				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), 
//				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), 
//				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyLong(), 
//				Matchers.anyLong())).thenReturn(1);

		when(phyHeadToToeExaminationRepoMock.updatePhyHeadToToeExamination(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyLong(), Matchers.anyLong(),
				Matchers.anyString())).thenReturn(1);

		when(sysCardiovascularExaminationRepoMock.updateSysCardiovascularExamination(Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyLong(), Matchers.anyLong())).thenReturn(1);

		when(sysRespiratoryExaminationRepoMock.updateSysRespiratoryExamination(Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyLong(), Matchers.anyLong())).thenReturn(1);

		when(sysCentralNervousExaminationRepoMock.updateSysCentralNervousExamination(Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyLong(), Matchers.anyLong())).thenReturn(1);

		when(sysMusculoskeletalSystemExaminationRepoMock.updateSysMusculoskeletalSystemExamination(Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyLong(), Matchers.anyLong())).thenReturn(1);

		when(sysGenitourinarySystemExaminationRepoMock.updateSysGenitourinarySystemExamination(Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyLong(), Matchers.anyLong())).thenReturn(1);

		when(benClinicalObservationsRepoMock.save(isA(BenClinicalObservations.class)))
				.thenReturn(spy(BenClinicalObservations.class));

		PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
		prescriptionDetail.setPrescriptionID(1L);
		when(prescriptionDetailRepoMock.save(isA(PrescriptionDetail.class))).thenReturn(prescriptionDetail);

		// Size of Response List we are expecting is based on the size of Investigations
		// we are passing as input
		ArrayList<LabTestOrderDetail> LabTestOrderDetailListRS = new ArrayList<LabTestOrderDetail>();
		LabTestOrderDetailListRS.add(new LabTestOrderDetail());
		LabTestOrderDetailListRS.add(new LabTestOrderDetail());
		LabTestOrderDetailListRS.add(new LabTestOrderDetail());
		LabTestOrderDetailListRS.add(new LabTestOrderDetail());
		LabTestOrderDetailListRS.add(new LabTestOrderDetail());
		LabTestOrderDetailListRS.add(new LabTestOrderDetail());
		when(labTestOrderDetailRepoMock.save(Matchers.anyListOf(LabTestOrderDetail.class)))
				.thenReturn(LabTestOrderDetailListRS);

		PrescribedDrugDetail prescribedDrugDetail = new PrescribedDrugDetail();
		List<PrescribedDrugDetail> prescribedDrugDetailListRS = new ArrayList<PrescribedDrugDetail>();
		prescribedDrugDetailListRS.add(prescribedDrugDetail);
		when(prescribedDrugDetailRepoMock.save(Matchers.anyListOf(PrescribedDrugDetail.class)))
				.thenReturn(prescribedDrugDetailListRS);

		when(benVisitDetailRepoMock.updateBenFlowStatus("D", benVisitID)).thenReturn(1);

		when(benVisitDetailRepoMock.updateBeneficiaryVisitDetail(Matchers.anyShort(), Matchers.anyString(),
				Matchers.anyInt(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyLong())).thenReturn(1);

		when(benVisitDetailRepoMock.getVisitDetails(null, null)).thenReturn(null);
	}

	@Test
	public void saveBeneficiaryVisitDetailsPveTest() {
		Long response = null;
		try {
			BeneficiaryVisitDetail beneficiaryVisitDetail = new BeneficiaryVisitDetail();
			response = commonNurseServiceImpl.saveBeneficiaryVisitDetails(beneficiaryVisitDetail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo(1);
	}

	@Test
	public void getBenVisitCountPveTest() {
		Short response = null;
		try {
			response = commonNurseServiceImpl.getBenVisitCount(beneficiaryRegID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Short res = 1;
		assertThat(response).isEqualTo(res);
	}

	@Test
	public void updateBeneficiaryVisitDetailsPveTest() {
		int response = 0;
		try {
			BeneficiaryVisitDetail beneficiaryVisitDetail = new BeneficiaryVisitDetail();
			response = commonNurseServiceImpl.updateBeneficiaryVisitDetails(beneficiaryVisitDetail);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThat(response).isEqualTo(1);
	}

	@Test
	public void getCSVisitDetailsPveTest() {
		BeneficiaryVisitDetail response = null;
		try {
			response = commonNurseServiceImpl.getCSVisitDetails(null, null);
			System.out.println("response " + response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThat(response).isEqualTo(null);
	}
}
