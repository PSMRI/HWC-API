/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.mmu.cancerScreening;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.data.doctor.CancerAbdominalExamination;
import com.iemr.mmu.data.doctor.CancerBreastExamination;
import com.iemr.mmu.data.doctor.CancerDiagnosis;
import com.iemr.mmu.data.doctor.CancerExaminationImageAnnotation;
import com.iemr.mmu.data.doctor.CancerGynecologicalExamination;
import com.iemr.mmu.data.doctor.CancerLymphNodeDetails;
import com.iemr.mmu.data.doctor.CancerOralExamination;
import com.iemr.mmu.data.doctor.CancerSignAndSymptoms;
import com.iemr.mmu.data.nurse.BenCancerVitalDetail;
import com.iemr.mmu.data.nurse.BenFamilyCancerHistory;
import com.iemr.mmu.data.nurse.BenObstetricCancerHistory;
import com.iemr.mmu.data.nurse.BenPersonalCancerDietHistory;
import com.iemr.mmu.data.nurse.BenPersonalCancerHistory;
import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.repo.doctor.CancerAbdominalExaminationRepo;
import com.iemr.mmu.repo.doctor.CancerBreastExaminationRepo;
import com.iemr.mmu.repo.doctor.CancerDiagnosisRepo;
import com.iemr.mmu.repo.doctor.CancerExaminationImageAnnotationRepo;
import com.iemr.mmu.repo.doctor.CancerGynecologicalExaminationRepo;
import com.iemr.mmu.repo.doctor.CancerLymphNodeExaminationRepo;
import com.iemr.mmu.repo.doctor.CancerOralExaminationRepo;
import com.iemr.mmu.repo.doctor.CancerSignAndSymptomsRepo;
import com.iemr.mmu.repo.nurse.BenCancerVitalDetailRepo;
import com.iemr.mmu.repo.nurse.BenFamilyCancerHistoryRepo;
import com.iemr.mmu.repo.nurse.BenObstetricCancerHistoryRepo;
import com.iemr.mmu.repo.nurse.BenPersonalCancerDietHistoryRepo;
import com.iemr.mmu.repo.nurse.BenPersonalCancerHistoryRepo;
import com.iemr.mmu.repo.nurse.BenVisitDetailRepo;
import com.iemr.mmu.repo.registrar.RegistrarRepoBenData;
import com.iemr.mmu.repo.registrar.RegistrarRepoBenDemoData;
import com.iemr.mmu.service.cancerScreening.CSDoctorServiceImpl;
import com.iemr.mmu.service.cancerScreening.CSNurseServiceImpl;
import com.iemr.mmu.service.cancerScreening.CSOncologistServiceImpl;
import com.iemr.mmu.service.cancerScreening.CSServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.mmu.service.registrar.RegistrarServiceImpl;
import com.iemr.mmu.utils.mapper.InputMapper;

public class TestCSServices
{
	private static CSServiceImpl cSServiceImpl = spy(CSServiceImpl.class);
	private static CSNurseServiceImpl cSNurseServiceImpl = spy(CSNurseServiceImpl.class);
	private static CommonNurseServiceImpl commonNurseServiceImpl = spy(CommonNurseServiceImpl.class);
	private static CSDoctorServiceImpl cSDoctorServiceImpl = spy(CSDoctorServiceImpl.class);
	private static CSOncologistServiceImpl csOncologistServiceImpl = spy(CSOncologistServiceImpl.class);
	private static RegistrarServiceImpl registrarServiceImpl = spy(RegistrarServiceImpl.class);
	
	private static BenVisitDetailRepo benVisitDetailRepoMock = mock(BenVisitDetailRepo.class);
	private static BenFamilyCancerHistoryRepo benFamilyCancerHistoryRepoMock = mock(BenFamilyCancerHistoryRepo.class);
	private static BenPersonalCancerHistoryRepo benPersonalCancerHistoryRepoMock = mock(BenPersonalCancerHistoryRepo.class);
	private static BenPersonalCancerDietHistoryRepo benPersonalCancerDietHistoryRepoMock = mock(BenPersonalCancerDietHistoryRepo.class);
	private static BenObstetricCancerHistoryRepo benObstetricCancerHistoryRepoMock = mock(BenObstetricCancerHistoryRepo.class);
	private static CancerSignAndSymptomsRepo cancerSignAndSymptomsRepoMock = mock(CancerSignAndSymptomsRepo.class);
	private static CancerLymphNodeExaminationRepo cancerLymphNodeExaminationRepoMock = mock(CancerLymphNodeExaminationRepo.class);
	private static CancerOralExaminationRepo cancerOralExaminationRepoMock = mock(CancerOralExaminationRepo.class);
	private static CancerBreastExaminationRepo cancerBreastExaminationRepoMock = mock(CancerBreastExaminationRepo.class);
	private static CancerAbdominalExaminationRepo cancerAbdominalExaminationRepoMock = mock(CancerAbdominalExaminationRepo.class);
	private static CancerGynecologicalExaminationRepo cancerGynecologicalExaminationRepoMock = mock(CancerGynecologicalExaminationRepo.class);
	private static CancerExaminationImageAnnotationRepo cancerExaminationImageAnnotationRepoMock = mock(CancerExaminationImageAnnotationRepo.class);
	private static BenCancerVitalDetailRepo benCancerVitalDetailRepoMock = mock(BenCancerVitalDetailRepo.class);
	private static RegistrarRepoBenData registrarRepoBenDataMock = mock(RegistrarRepoBenData.class);
	private static CancerDiagnosisRepo cancerDiagnosisRepoMock = mock(CancerDiagnosisRepo.class);
	private static RegistrarRepoBenDemoData registrarRepoBenDemoDataMock = mock(RegistrarRepoBenDemoData.class);
	
	static String nurseObjPve;
	static String doctorObjPve;
	static String doctorObjNve;
	public static JsonObject nurseJsnOBJPve;
	static JsonObject nurseJsnOBJNve;
	public static JsonObject doctorJsnOBJPve;
	public static JsonObject doctorJsnOBJNve;
	public static String updateHstryObjPve;
	public static JsonObject updateHstryJsnObjPve;
	static String updateVitalObjPve;
	static String updateVitalObjNve;
	static String updateVitalObjNve2;
	static String updateExmnObjPve;
	static String updateExmnObjNve;
	static JsonObject updateExmnJsnObjNve;
	static JsonObject updateExmnJsnObjPve;
	static String fetchObjPve;
	static JSONObject fetchJsnObjPve;
	static JsonObject nurseJsnOBJNve2;
	
	static Long beneficiaryRegID = 7506L;
	static Long benVisitID = 131L;
	@BeforeClass
	public static void initializeParams(){
		
		cSServiceImpl.setCommonNurseServiceImpl(commonNurseServiceImpl);
		cSServiceImpl.setcSNurseServiceImpl(cSNurseServiceImpl);
		cSServiceImpl.setcSDoctorServiceImpl(cSDoctorServiceImpl);
		cSServiceImpl.setCsOncologistServiceImpl(csOncologistServiceImpl);
		//cSServiceImpl.setRegistrarServiceImpl(registrarServiceImpl);
		
		commonNurseServiceImpl.setBenVisitDetailRepo(benVisitDetailRepoMock);
		commonNurseServiceImpl.setRegistrarRepoBenData(registrarRepoBenDataMock);
		
		cSNurseServiceImpl.setBenVisitDetailRepo(benVisitDetailRepoMock);
		cSNurseServiceImpl.setBenFamilyCancerHistoryRepo(benFamilyCancerHistoryRepoMock);
		cSNurseServiceImpl.setBenPersonalCancerHistoryRepo(benPersonalCancerHistoryRepoMock);
		cSNurseServiceImpl.setBenPersonalCancerDietHistoryRepo(benPersonalCancerDietHistoryRepoMock);
		cSNurseServiceImpl.setBenObstetricCancerHistoryRepo(benObstetricCancerHistoryRepoMock);
		
		cSNurseServiceImpl.setCancerSignAndSymptomsRepo(cancerSignAndSymptomsRepoMock);
		cSNurseServiceImpl.setCancerLymphNodeExaminationRepo(cancerLymphNodeExaminationRepoMock);
		cSNurseServiceImpl.setCancerOralExaminationRepo(cancerOralExaminationRepoMock);
		cSNurseServiceImpl.setCancerBreastExaminationRepo(cancerBreastExaminationRepoMock);
		cSNurseServiceImpl.setCancerAbdominalExaminationRepo(cancerAbdominalExaminationRepoMock);
		cSNurseServiceImpl.setCancerGynecologicalExaminationRepo(cancerGynecologicalExaminationRepoMock);
		cSNurseServiceImpl.setCancerExaminationImageAnnotationRepo(cancerExaminationImageAnnotationRepoMock);
		cSNurseServiceImpl.setBenCancerVitalDetailRepo(benCancerVitalDetailRepoMock);
		cSDoctorServiceImpl.setCancerDiagnosisRepo(cancerDiagnosisRepoMock);
		csOncologistServiceImpl.setCancerDiagnosisRepo(cancerDiagnosisRepoMock);
		registrarServiceImpl.setRegistrarRepoBenDemoData(registrarRepoBenDemoDataMock);
		registrarServiceImpl.setRegistrarRepoBenData(registrarRepoBenDataMock);
		
		nurseObjPve = "{\"visitDetails\": { \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"visitNo\": null, \"visitReason\": \"Screening\", \"visitCategory\": \"Cancer Screening\", \"pregnancyStatus\": \"Yes\", \"rCHID\": \"7777\", \"healthFacilityType\": null, \"healthFacilityLocation\": null, \"reportFilePath\": null, \"createdBy\": \"888\" }, \"vitalsDetails\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"weight_Kg\": \"64\", \"height_cm\": \"166\", \"waistCircumference_cm\": \"56\", \"systolicBP_1stReading\": \"120\", \"diastolicBP_1stReading\": \"65\", \"systolicBP_2ndReading\": \"113\", \"diastolicBP_2ndReading\": \"73\", \"systolicBP_3rdReading\": \"123\", \"diastolicBP_3rdReading\": \"66\", \"hbA1C\": \"4\", \"hemoglobin\": \"14\", \"bloodGlucose_Fasting\": \"123\", \"bloodGlucose_Random\": \"143\", \"bloodGlucose_2HrPostPrandial\": \"145\", \"createdBy\": \"888\" }, \"examinationDetails\": { \"signsDetails\": { \"cancerSignAndSymptoms\": { \"shortnessOfBreath\": null, \"coughgt2Weeks\": true, \"bloodInSputum\": true, \"difficultyInOpeningMouth\": true, \"nonHealingUlcerOrPatchOrGrowth\": null, \"changeInTheToneOfVoice\": null, \"lumpInTheBreast\": null, \"bloodStainedDischargeFromNipple\": null, \"changeInShapeAndSizeOfBreasts\": null, \"vaginalBleedingBetweenPeriods\": null, \"vaginalBleedingAfterMenopause\": null, \"vaginalBleedingAfterIntercourse\": null, \"foulSmellingVaginalDischarge\": null, \"lymphNode_Enlarged\": true, \"breastEnlargement\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"cancerLymphNodeDetails\": [ { \"lymphNodeName\": \" Sub Mandibular\", \"size_Left\": \"3-6 cm\", \"mobility_Left\": true, \"size_Right\": \"3-6 cm\", \"mobility_Right\": true, \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" } ] }, \"oralDetails\": { \"limitedMouthOpening\": \"+\", \"premalignantLesions\": true, \"preMalignantLesionTypeList\": [ \"Sub muscus fibrosis\" ], \"prolongedIrritation\": null, \"chronicBurningSensation\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"breastDetails\": { \"everBreastFed\": true, \"breastFeedingDurationGTE6months\": true, \"breastsAppear_Normal\": true, \"rashOnBreast\": null, \"dimplingSkinOnBreast\": true, \"dischargeFromNipple\": null, \"peaudOrange\": null, \"lumpInBreast\": null, \"lumpSize\": null, \"lumpShape\": null, \"lumpTexture\": null, \"referredToMammogram\": null, \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"abdominalDetails\": { \"abdominalInspection_Normal\": true, \"liver\": \"Not palpable\", \"ascites_Present\": true, \"anyOtherMass_Present\": true, \"lymphNodes_Enlarged\": null, \"lymphNode_Inguinal_Left\": null, \"lymphNode_Inguinal_Right\": null, \"lymphNode_ExternalIliac_Left\": null, \"lymphNode_ExternalIliac_Right\": null, \"lymphNode_ParaAortic_Left\": null, \"lymphNode_ParaAortic_Right\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"gynecologicalDetails\": { \"appearanceOfCervix\": null, \"typeOfLesionList\": null, \"vulvalInvolvement\": null, \"vaginalInvolvement\": true, \"uterus_Normal\": true, \"sufferedFromRTIOrSTI\": null, \"rTIOrSTIDetail\": null, \"filePath\": null, \"experiencedPostCoitalBleeding\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"imageCoordinates\": [ { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 3, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 97, \"yCord\": 174, \"description\": \"one\", \"point\": 1 } ] }, { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 1, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 146, \"yCord\": 184, \"description\": \"three\", \"point\": 1 } ] }, { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 4, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 102, \"yCord\": 154, \"description\": \"four\", \"point\": 1 } ] }, { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 2, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 171, \"yCord\": 156, \"description\": \"two\", \"point\": 1 } ] } ] }, \"historyDetails\": { \"familyHistory\": { \"diseases\": [ { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"cancerDiseaseType\": \"Breast Cancer\", \"otherDiseaseType\": null, \"familyMemberList\": [ \"Aunt\", \"Brother\" ], \"createdBy\": \"888\" }, { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"cancerDiseaseType\": \"lorem ipsum\", \"otherDiseaseType\": \"lorem ipsum\", \"familyMemberList\": [ \"Grand Father\" ], \"createdBy\": \"888\" } ], \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"personalHistory\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"tobaccoUse\": \"Used in Past\", \"startAge_year\": \"23\", \"endAge_year\": \"24\", \"typeOfTobaccoProductList\": [ \"Beedi\", \"Chewing\", \"Cigarettes\" ], \"quantityPerDay\": \"2\", \"isFilteredCigaerette\": true, \"isCigaretteExposure\": false, \"isBetelNutChewing\": true, \"durationOfBetelQuid\": \"12\", \"alcoholUse\": \"Currently Using\", \"ssAlcoholUsed\": true, \"frequencyOfAlcoholUsed\": \"1-4 days/week\", \"dietType\": \"Eggetarian\", \"otherDiet\": null, \"intakeOfOutsidePreparedMeal\": \"2\", \"fruitConsumptionDays\": \"2\", \"fruitQuantityPerDay\": \"2\", \"vegetableConsumptionDays\": \"2\", \"vegetableQuantityPerDay\": \"2\", \"typeOfOilConsumedList\": [ \"Coconut Oil\", \"Corn Oil\" ], \"otherOilType\": null, \"physicalActivityType\": \"Light Activity\", \"ssRadiationExposure\": false, \"isThyroidDisorder\": false, \"createdBy\": \"888\" }, \"pastObstetricHistory\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"pregnancyStatus\": \"Yes\", \"isUrinePregTest\": null, \"pregnant_No\": \"1\", \"noOfLivingChild\": \"1\", \"isAbortion\": false, \"isOralContraceptiveUsed\": false, \"isHormoneReplacementTherapy\": false, \"menarche_Age\": \"13\", \"isMenstrualCycleRegular\": true, \"menstrualCycleLength\": \"28\", \"menstrualFlowDuration\": \"3\", \"menstrualFlowType\": \"Little\", \"isDysmenorrhea\": false, \"isInterMenstrualBleeding\": false, \"menopauseAge\": null, \"isPostMenopauseBleeding\": null, \"createdBy\": \"888\" } } }";
		String nurseObjNve = "{\"visittails\": { \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"visitNo\": null, \"visitReason\": \"Screening\", \"visitCategory\": \"Cancer Screening\", \"pregnancyStatus\": \"Yes\", \"rCHID\": \"7777\", \"healthFacilityType\": null, \"healthFacilityLocation\": null, \"reportFilePath\": null, \"createdBy\": \"888\" }, \"vitalsDetails\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"weight_Kg\": \"64\", \"height_cm\": \"166\", \"waistCircumference_cm\": \"56\", \"systolicBP_1stReading\": \"120\", \"diastolicBP_1stReading\": \"65\", \"systolicBP_2ndReading\": \"113\", \"diastolicBP_2ndReading\": \"73\", \"systolicBP_3rdReading\": \"123\", \"diastolicBP_3rdReading\": \"66\", \"hbA1C\": \"4\", \"hemoglobin\": \"14\", \"bloodGlucose_Fasting\": \"123\", \"bloodGlucose_Random\": \"143\", \"bloodGlucose_2HrPostPrandial\": \"145\", \"createdBy\": \"888\" }, \"examinationDetails\": { \"signsDetails\": { \"cancerSignAndSymptoms\": { \"shortnessOfBreath\": null, \"coughgt2Weeks\": true, \"bloodInSputum\": true, \"difficultyInOpeningMouth\": true, \"nonHealingUlcerOrPatchOrGrowth\": null, \"changeInTheToneOfVoice\": null, \"lumpInTheBreast\": null, \"bloodStainedDischargeFromNipple\": null, \"changeInShapeAndSizeOfBreasts\": null, \"vaginalBleedingBetweenPeriods\": null, \"vaginalBleedingAfterMenopause\": null, \"vaginalBleedingAfterIntercourse\": null, \"foulSmellingVaginalDischarge\": null, \"lymphNode_Enlarged\": true, \"breastEnlargement\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"cancerLymphNodeDetails\": [ { \"lymphNodeName\": \" Sub Mandibular\", \"size_Left\": \"3-6 cm\", \"mobility_Left\": true, \"size_Right\": \"3-6 cm\", \"mobility_Right\": true, \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" } ] }, \"oralDetails\": { \"limitedMouthOpening\": \"+\", \"premalignantLesions\": true, \"preMalignantLesionTypeList\": [ \"Sub muscus fibrosis\" ], \"prolongedIrritation\": null, \"chronicBurningSensation\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"breastDetails\": { \"everBreastFed\": true, \"breastFeedingDurationGTE6months\": true, \"breastsAppear_Normal\": true, \"rashOnBreast\": null, \"dimplingSkinOnBreast\": true, \"dischargeFromNipple\": null, \"peaudOrange\": null, \"lumpInBreast\": null, \"lumpSize\": null, \"lumpShape\": null, \"lumpTexture\": null, \"referredToMammogram\": null, \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"abdominalDetails\": { \"abdominalInspection_Normal\": true, \"liver\": \"Not palpable\", \"ascites_Present\": true, \"anyOtherMass_Present\": true, \"lymphNodes_Enlarged\": null, \"lymphNode_Inguinal_Left\": null, \"lymphNode_Inguinal_Right\": null, \"lymphNode_ExternalIliac_Left\": null, \"lymphNode_ExternalIliac_Right\": null, \"lymphNode_ParaAortic_Left\": null, \"lymphNode_ParaAortic_Right\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"gynecologicalDetails\": { \"appearanceOfCervix\": null, \"typeOfLesionList\": null, \"vulvalInvolvement\": null, \"vaginalInvolvement\": true, \"uterus_Normal\": true, \"sufferedFromRTIOrSTI\": null, \"rTIOrSTIDetail\": null, \"filePath\": null, \"experiencedPostCoitalBleeding\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"imageCoordinates\": [ { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 3, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 97, \"yCord\": 174, \"description\": \"one\", \"point\": 1 } ] }, { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 1, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 146, \"yCord\": 184, \"description\": \"three\", \"point\": 1 } ] }, { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 4, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 102, \"yCord\": 154, \"description\": \"four\", \"point\": 1 } ] }, { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 2, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 171, \"yCord\": 156, \"description\": \"two\", \"point\": 1 } ] } ] }, \"historyDetails\": { \"familyHistory\": { \"diseases\": [ { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"cancerDiseaseType\": \"Breast Cancer\", \"otherDiseaseType\": null, \"familyMemberList\": [ \"Aunt\", \"Brother\" ], \"createdBy\": \"888\" }, { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"cancerDiseaseType\": \"lorem ipsum\", \"otherDiseaseType\": \"lorem ipsum\", \"familyMemberList\": [ \"Grand Father\" ], \"createdBy\": \"888\" } ], \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"personalHistory\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"tobaccoUse\": \"Used in Past\", \"startAge_year\": \"23\", \"endAge_year\": \"24\", \"typeOfTobaccoProductList\": [ \"Beedi\", \"Chewing\", \"Cigarettes\" ], \"quantityPerDay\": \"2\", \"isFilteredCigaerette\": true, \"isCigaretteExposure\": false, \"isBetelNutChewing\": true, \"durationOfBetelQuid\": \"12\", \"alcoholUse\": \"Currently Using\", \"ssAlcoholUsed\": true, \"frequencyOfAlcoholUsed\": \"1-4 days/week\", \"dietType\": \"Eggetarian\", \"otherDiet\": null, \"intakeOfOutsidePreparedMeal\": \"2\", \"fruitConsumptionDays\": \"2\", \"fruitQuantityPerDay\": \"2\", \"vegetableConsumptionDays\": \"2\", \"vegetableQuantityPerDay\": \"2\", \"typeOfOilConsumedList\": [ \"Coconut Oil\", \"Corn Oil\" ], \"otherOilType\": null, \"physicalActivityType\": \"Light Activity\", \"ssRadiationExposure\": false, \"isThyroidDisorder\": false, \"createdBy\": \"888\" }, \"pastObstetricHistory\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"pregnancyStatus\": \"Yes\", \"isUrinePregTest\": null, \"pregnant_No\": \"1\", \"noOfLivingChild\": \"1\", \"isAbortion\": false, \"isOralContraceptiveUsed\": false, \"isHormoneReplacementTherapy\": false, \"menarche_Age\": \"13\", \"isMenstrualCycleRegular\": true, \"menstrualCycleLength\": \"28\", \"menstrualFlowDuration\": \"3\", \"menstrualFlowType\": \"Little\", \"isDysmenorrhea\": false, \"isInterMenstrualBleeding\": false, \"menopauseAge\": null, \"isPostMenopauseBleeding\": null, \"createdBy\": \"888\" } } }";
		String nurseObjNve2 = "{\"visitDetails\": { \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"visitNo\": null, \"visitReason\": \"Screening\", \"visitCategory\": \"Cancer Screening\", \"pregnancyStatus\": \"Yes\", \"rCHID\": \"7777\", \"healthFacilityType\": null, \"healthFacilityLocation\": null, \"reportFilePath\": null, \"createdBy\": \"888\" }, \"vitetails\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"weight_Kg\": \"64\", \"height_cm\": \"166\", \"waistCircumference_cm\": \"56\", \"systolicBP_1stReading\": \"120\", \"diastolicBP_1stReading\": \"65\", \"systolicBP_2ndReading\": \"113\", \"diastolicBP_2ndReading\": \"73\", \"systolicBP_3rdReading\": \"123\", \"diastolicBP_3rdReading\": \"66\", \"hbA1C\": \"4\", \"hemoglobin\": \"14\", \"bloodGlucose_Fasting\": \"123\", \"bloodGlucose_Random\": \"143\", \"bloodGlucose_2HrPostPrandial\": \"145\", \"createdBy\": \"888\" }, \"examinationDetails\": { \"signsDetails\": { \"cancerSignAndSymptoms\": { \"shortnessOfBreath\": null, \"coughgt2Weeks\": true, \"bloodInSputum\": true, \"difficultyInOpeningMouth\": true, \"nonHealingUlcerOrPatchOrGrowth\": null, \"changeInTheToneOfVoice\": null, \"lumpInTheBreast\": null, \"bloodStainedDischargeFromNipple\": null, \"changeInShapeAndSizeOfBreasts\": null, \"vaginalBleedingBetweenPeriods\": null, \"vaginalBleedingAfterMenopause\": null, \"vaginalBleedingAfterIntercourse\": null, \"foulSmellingVaginalDischarge\": null, \"lymphNode_Enlarged\": true, \"breastEnlargement\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"cancerLymphNodeDetails\": [ { \"lymphNodeName\": \" Sub Mandibular\", \"size_Left\": \"3-6 cm\", \"mobility_Left\": true, \"size_Right\": \"3-6 cm\", \"mobility_Right\": true, \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" } ] }, \"oralDetails\": { \"limitedMouthOpening\": \"+\", \"premalignantLesions\": true, \"preMalignantLesionTypeList\": [ \"Sub muscus fibrosis\" ], \"prolongedIrritation\": null, \"chronicBurningSensation\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"breastDetails\": { \"everBreastFed\": true, \"breastFeedingDurationGTE6months\": true, \"breastsAppear_Normal\": true, \"rashOnBreast\": null, \"dimplingSkinOnBreast\": true, \"dischargeFromNipple\": null, \"peaudOrange\": null, \"lumpInBreast\": null, \"lumpSize\": null, \"lumpShape\": null, \"lumpTexture\": null, \"referredToMammogram\": null, \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"abdominalDetails\": { \"abdominalInspection_Normal\": true, \"liver\": \"Not palpable\", \"ascites_Present\": true, \"anyOtherMass_Present\": true, \"lymphNodes_Enlarged\": null, \"lymphNode_Inguinal_Left\": null, \"lymphNode_Inguinal_Right\": null, \"lymphNode_ExternalIliac_Left\": null, \"lymphNode_ExternalIliac_Right\": null, \"lymphNode_ParaAortic_Left\": null, \"lymphNode_ParaAortic_Right\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"gynecologicalDetails\": { \"appearanceOfCervix\": null, \"typeOfLesionList\": null, \"vulvalInvolvement\": null, \"vaginalInvolvement\": true, \"uterus_Normal\": true, \"sufferedFromRTIOrSTI\": null, \"rTIOrSTIDetail\": null, \"filePath\": null, \"experiencedPostCoitalBleeding\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"imageCoordinates\": [ { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 3, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 97, \"yCord\": 174, \"description\": \"one\", \"point\": 1 } ] }, { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 1, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 146, \"yCord\": 184, \"description\": \"three\", \"point\": 1 } ] }, { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 4, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 102, \"yCord\": 154, \"description\": \"four\", \"point\": 1 } ] }, { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 2, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 171, \"yCord\": 156, \"description\": \"two\", \"point\": 1 } ] } ] }, \"historyDetails\": { \"familyHistory\": { \"diseases\": [ { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"cancerDiseaseType\": \"Breast Cancer\", \"otherDiseaseType\": null, \"familyMemberList\": [ \"Aunt\", \"Brother\" ], \"createdBy\": \"888\" }, { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"cancerDiseaseType\": \"lorem ipsum\", \"otherDiseaseType\": \"lorem ipsum\", \"familyMemberList\": [ \"Grand Father\" ], \"createdBy\": \"888\" } ], \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"personalHistory\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"tobaccoUse\": \"Used in Past\", \"startAge_year\": \"23\", \"endAge_year\": \"24\", \"typeOfTobaccoProductList\": [ \"Beedi\", \"Chewing\", \"Cigarettes\" ], \"quantityPerDay\": \"2\", \"isFilteredCigaerette\": true, \"isCigaretteExposure\": false, \"isBetelNutChewing\": true, \"durationOfBetelQuid\": \"12\", \"alcoholUse\": \"Currently Using\", \"ssAlcoholUsed\": true, \"frequencyOfAlcoholUsed\": \"1-4 days/week\", \"dietType\": \"Eggetarian\", \"otherDiet\": null, \"intakeOfOutsidePreparedMeal\": \"2\", \"fruitConsumptionDays\": \"2\", \"fruitQuantityPerDay\": \"2\", \"vegetableConsumptionDays\": \"2\", \"vegetableQuantityPerDay\": \"2\", \"typeOfOilConsumedList\": [ \"Coconut Oil\", \"Corn Oil\" ], \"otherOilType\": null, \"physicalActivityType\": \"Light Activity\", \"ssRadiationExposure\": false, \"isThyroidDisorder\": false, \"createdBy\": \"888\" }, \"pastObstetricHistory\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"pregnancyStatus\": \"Yes\", \"isUrinePregTest\": null, \"pregnant_No\": \"1\", \"noOfLivingChild\": \"1\", \"isAbortion\": false, \"isOralContraceptiveUsed\": false, \"isHormoneReplacementTherapy\": false, \"menarche_Age\": \"13\", \"isMenstrualCycleRegular\": true, \"menstrualCycleLength\": \"28\", \"menstrualFlowDuration\": \"3\", \"menstrualFlowType\": \"Little\", \"isDysmenorrhea\": false, \"isInterMenstrualBleeding\": false, \"menopauseAge\": null, \"isPostMenopauseBleeding\": null, \"createdBy\": \"888\" } } }";
		doctorObjPve = "{\"diagnosis\": { \"referredToInstituteID\": null, \"refrredToAdditionalServiceList\": [ 3, 1, 5 ], \"provisionalDiagnosisPrimaryDoctor\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. \", \"remarks\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. \", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"benVisitID\": \"937\", \"createdBy\": \"888\" } }";
		doctorObjNve = "{\"diasis\": { \"referredToInstituteID\": null, \"refrredToAdditionalServiceList\": [ 3, 1, 5 ], \"provisionalDiagnosisPrimaryDoctor\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. \", \"remarks\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. \", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"benVisitID\": \"937\", \"createdBy\": \"888\" } }";
		updateHstryObjPve = "{ \"familyHistory\": [ { \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"cancerDiseaseType\": \"Breast Cancer\", \"otherDiseaseType\": null, \"familyMemberList\": [ \"Aunt\", \"Brother\" ], \"createdBy\": null, \"modifiedBy\": \"890\" }, { \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"cancerDiseaseType\": \"Diabetes Mellitus\", \"otherDiseaseType\": null, \"familyMemberList\": [ \"Brother\", \"Daughter\" ], \"createdBy\": null, \"modifiedBy\": \"890\" } ], \"personalHistory\": { \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"tobaccoUse\": \"Currently Using\", \"startAge_year\": \"15\", \"endAge_year\": null, \"typeOfTobaccoProductList\": null, \"quantityPerDay\": null, \"isFilteredCigaerette\": null, \"isCigaretteExposure\": true, \"isBetelNutChewing\": false, \"durationOfBetelQuid\": null, \"alcoholUse\": null, \"ssAlcoholUsed\": null, \"frequencyOfAlcoholUsed\": null, \"dietType\": null, \"otherDiet\": null, \"intakeOfOutsidePreparedMeal\": null, \"fruitConsumptionDays\": null, \"fruitQuantityPerDay\": null, \"vegetableConsumptionDays\": null, \"vegetableQuantityPerDay\": null, \"typeOfOilConsumedList\": null, \"otherOilType\": null, \"physicalActivityType\": null, \"ssRadiationExposure\": null, \"isThyroidDisorder\": null, \"createdBy\": null, \"modifiedBy\": \"890\" }, \"pastObstetricHistory\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"pregnancyStatus\": \"Yes\", \"isUrinePregTest\": null, \"pregnant_No\": \"1\", \"noOfLivingChild\": \"1\", \"isAbortion\": false, \"isOralContraceptiveUsed\": false, \"isHormoneReplacementTherapy\": false, \"menarche_Age\": \"13\", \"isMenstrualCycleRegular\": true, \"menstrualCycleLength\": \"28\", \"menstrualFlowDuration\": \"3\", \"menstrualFlowType\": \"Little\", \"isDysmenorrhea\": false, \"isInterMenstrualBleeding\": false, \"menopauseAge\": null, \"isPostMenopauseBleeding\": null, \"createdBy\": \"888\" } }";
		
		updateVitalObjPve = "{ \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"weight_Kg\": \"64\", \"height_cm\": 166, \"waistCircumference_cm\": null, \"systolicBP_1stReading\": null, \"diastolicBP_1stReading\": null, \"systolicBP_2ndReading\": null, \"diastolicBP_2ndReading\": null, \"systolicBP_3rdReading\": null, \"diastolicBP_3rdReading\": null, \"hbA1C\": null, \"hemoglobin\": null, \"bloodGlucose_Fasting\": null, \"bloodGlucose_Random\": null, \"bloodGlucose_2HrPostPrandial\": null, \"createdBy\": \"888\", \"modifiedBy\": \"890\" }";
		updateVitalObjNve = "{ \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"weight_Kg\": \"64\", \"height_cm\": 166, \"waistCircumference_cm\": null, \"systolicBP_1stReading\": null, \"diastolicBP_1stReading\": null, \"systolicBP_2ndReading\": null, \"diastolicBP_2ndReading\": null, \"systolicBP_3rdReading\": null, \"diastolicBP_3rdReading\": null, \"hbA1C\": null, \"hemoglobin\": null, \"bloodGlucose_Fasting\": null, \"bloodGlucose_Random\": null, \"bloodGlucose_2HrPostPrandial\": null}";
		updateVitalObjNve2 = "{ \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"weight_Kg\": \"64\", \"height_cm\": 166, \"waistCircumference_cm\": null, \"systolicBP_1stReading\": null, \"diastolicBP_1stReading\": null, \"systolicBP_2ndReading\": null, \"diastolicBP_2ndReading\": null, \"systolicBP_3rdReading\": null, \"diastolicBP_3rdReading\": null, \"hbA1C\": null, \"hemoglobin\": null, \"bloodGlucose_Fasting\": null, \"bloodGlucose_Random\": null, \"bloodGlucose_2HrPostPrandial\": null, \"createdBy\": \"888\", \"modifiedBy\": \"890\" }";
		updateExmnObjPve  =	"{ \"signsDetails\": { \"cancerSignAndSymptoms\": { \"shortnessOfBreath\": true, \"coughgt2Weeks\": null, \"bloodInSputum\": false, \"difficultyInOpeningMouth\": null, \"nonHealingUlcerOrPatchOrGrowth\": null, \"changeInTheToneOfVoice\": null, \"lumpInTheBreast\": null, \"bloodStainedDischargeFromNipple\": null, \"changeInShapeAndSizeOfBreasts\": null, \"vaginalBleedingBetweenPeriods\": null, \"vaginalBleedingAfterMenopause\": null, \"vaginalBleedingAfterIntercourse\": null, \"foulSmellingVaginalDischarge\": null, \"lymphNode_Enlarged\": true, \"breastEnlargement\": null, \"observation\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\" }, \"cancerLymphNodeDetails\": [ { \"lymphNodeName\": \" Sub Mental\", \"size_Left\": null, \"mobility_Left\": null, \"size_Right\": null, \"mobility_Right\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Sub Mandibular\", \"size_Left\": null, \"mobility_Left\": null, \"size_Right\": null, \"mobility_Right\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Sub deep cervical\", \"size_Left\": null, \"mobility_Left\": null, \"size_Right\": null, \"mobility_Right\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Jugulo-digastric\", \"size_Left\": null, \"mobility_Left\": true, \"size_Right\": null, \"mobility_Right\": false, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Mid cervical\", \"size_Left\": null, \"mobility_Left\": false, \"size_Right\": null, \"mobility_Right\": true, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Interior cervical\", \"size_Left\": null, \"mobility_Left\": null, \"size_Right\": null, \"mobility_Right\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Supra clavicular\", \"size_Left\": null, \"mobility_Left\": null, \"size_Right\": null, \"mobility_Right\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Posterior Triangle\", \"size_Left\": null, \"mobility_Left\": null, \"size_Right\": null, \"mobility_Right\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Axillary Lymph Nodes\", \"size_Left\": null, \"mobility_Left\": null, \"size_Right\": null, \"mobility_Right\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" } ] }, \"oralDetails\": { \"limitedMouthOpening\": \"none\", \"premalignantLesions\": true, \"preMalignantLesionTypeList\": [ \"Leukoplakia\" ], \"prolongedIrritation\": false, \"chronicBurningSensation\": null, \"observation\": \"frettrter\", \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\" }, \"abdominalDetails\": { \"abdominalInspection_Normal\": true, \"liver\": null, \"ascites_Present\": true, \"anyOtherMass_Present\": false, \"lymphNodes_Enlarged\": false, \"lymphNode_Inguinal_Left\": null, \"lymphNode_Inguinal_Right\": null, \"lymphNode_ExternalIliac_Left\": null, \"lymphNode_ExternalIliac_Right\": null, \"lymphNode_ParaAortic_Left\": null, \"lymphNode_ParaAortic_Right\": null, \"observation\": \"rtretrte\", \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\" } }";
		updateExmnObjNve = 	"{ \"signsDtails\":{ \"cancerSignAndSymptoms\": { \"shortnessOfBreath\": true, \"coughgt2Weeks\": null, \"bloodInSputum\": false, \"difficultyInOpeningMouth\": null, \"nonHealingUlcerOrPatchOrGrowth\": null, \"changeInTheToneOfVoice\": null, \"lumpInTheBreast\": null, \"bloodStainedDischargeFromNipple\": null, \"changeInShapeAndSizeOfBreasts\": null, \"vaginalBleedingBetweenPeriods\": null, \"vaginalBleedingAfterMenopause\": null, \"vaginalBleedingAfterIntercourse\": null, \"foulSmellingVaginalDischarge\": null, \"lymphNode_Enlarged\": true, \"breastEnlargement\": null, \"observation\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\" }, \"cancerLymphNodeDetails\": [ { \"lymphNodeName\": \" Sub Mental\", \"size_Left\": null, \"mobility_Left\": null, \"size_Right\": null, \"mobility_Right\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Sub Mandibular\", \"size_Left\": null, \"mobility_Left\": null, \"size_Right\": null, \"mobility_Right\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Sub deep cervical\", \"size_Left\": null, \"mobility_Left\": null, \"size_Right\": null, \"mobility_Right\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Jugulo-digastric\", \"size_Left\": null, \"mobility_Left\": true, \"size_Right\": null, \"mobility_Right\": false, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Mid cervical\", \"size_Left\": null, \"mobility_Left\": false, \"size_Right\": null, \"mobility_Right\": true, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Interior cervical\", \"size_Left\": null, \"mobility_Left\": null, \"size_Right\": null, \"mobility_Right\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Supra clavicular\", \"size_Left\": null, \"mobility_Left\": null, \"size_Right\": null, \"mobility_Right\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Posterior Triangle\", \"size_Left\": null, \"mobility_Left\": null, \"size_Right\": null, \"mobility_Right\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" }, { \"lymphNodeName\": \" Axillary Lymph Nodes\", \"size_Left\": null, \"mobility_Left\": null, \"size_Right\": null, \"mobility_Right\": null, \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\", \"createdBy\": \"890\" } ] }, \"oralDetails\": { \"limitedMouthOpening\": \"none\", \"premalignantLesions\": true, \"preMalignantLesionTypeList\": [ \"Leukoplakia\" ], \"prolongedIrritation\": false, \"chronicBurningSensation\": null, \"observation\": \"frettrter\", \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\" }, \"abdominalDetails\": { \"abdominalInspection_Normal\": true, \"liver\": null, \"ascites_Present\": true, \"anyOtherMass_Present\": false, \"lymphNodes_Enlarged\": false, \"lymphNode_Inguinal_Left\": null, \"lymphNode_Inguinal_Right\": null, \"lymphNode_ExternalIliac_Left\": null, \"lymphNode_ExternalIliac_Right\": null, \"lymphNode_ParaAortic_Left\": null, \"lymphNode_ParaAortic_Right\": null, \"observation\": \"rtretrte\", \"beneficiaryRegID\": \"7416\", \"benVisitID\": \"992\", \"providerServiceMapID\": \"1320\", \"modifiedBy\": \"890\" } }";
		fetchObjPve = "{\"benRegID\":\"7506\", \"benVisitID\":\"131\"}";
		
		nurseJsnOBJPve = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(nurseObjPve);
		nurseJsnOBJPve = jsnElmnt.getAsJsonObject();
		
		nurseJsnOBJNve = new JsonObject();
		jsnElmnt = jsnParser.parse(nurseObjNve);
		nurseJsnOBJNve = jsnElmnt.getAsJsonObject();
		
		nurseJsnOBJNve2 = new JsonObject();
		jsnElmnt = jsnParser.parse(nurseObjNve2);
		nurseJsnOBJNve2 = jsnElmnt.getAsJsonObject();
		
		
		doctorJsnOBJPve = new JsonObject();
		jsnElmnt = jsnParser.parse(doctorObjPve);
		doctorJsnOBJPve = jsnElmnt.getAsJsonObject();
		
		doctorJsnOBJNve = new JsonObject();
		jsnElmnt = jsnParser.parse(doctorObjNve);
		doctorJsnOBJNve = jsnElmnt.getAsJsonObject();
		
		updateHstryJsnObjPve = new JsonObject();
		jsnElmnt = jsnParser.parse(updateHstryObjPve);
		updateHstryJsnObjPve = jsnElmnt.getAsJsonObject();
		
		updateExmnJsnObjPve =new JsonObject();
		jsnElmnt = jsnParser.parse(updateExmnObjPve);
		updateExmnJsnObjPve = jsnElmnt.getAsJsonObject();
		
		updateExmnJsnObjNve =new JsonObject();
		jsnElmnt = jsnParser.parse(updateExmnObjNve);
		updateExmnJsnObjNve = jsnElmnt.getAsJsonObject();
		
		fetchJsnObjPve = new JSONObject(fetchObjPve);
		
		BeneficiaryVisitDetail beneficiaryVisitDetail = spy(BeneficiaryVisitDetail.class);
		beneficiaryVisitDetail.setBenVisitID(1L);
		when(benVisitDetailRepoMock.save(isA(BeneficiaryVisitDetail.class))).thenReturn(beneficiaryVisitDetail);
		
		//Size of Response List we are expecting is based on the size of benFamilyHistory we are passing as input
		BenFamilyCancerHistory benFamilyHistory = spy(BenFamilyCancerHistory.class);
		List<BenFamilyCancerHistory> familyRes =  new ArrayList<BenFamilyCancerHistory>();
		familyRes.add(benFamilyHistory);
		familyRes.add(spy(BenFamilyCancerHistory.class));
		when(benFamilyCancerHistoryRepoMock.save(Matchers.anyListOf(BenFamilyCancerHistory.class))).thenReturn(familyRes);
		
		BenPersonalCancerHistory personalHstry = spy(BenPersonalCancerHistory.class);
		personalHstry.setID(1L);
		when(benPersonalCancerHistoryRepoMock.save(isA(BenPersonalCancerHistory.class))).thenReturn(personalHstry);
		
		BenPersonalCancerDietHistory personaldietHstry = spy(BenPersonalCancerDietHistory.class);
		personaldietHstry.setID(1L);
		when(benPersonalCancerDietHistoryRepoMock.save(isA(BenPersonalCancerDietHistory.class))).thenReturn(personaldietHstry);
		
		BenObstetricCancerHistory obstetricCancerHstry = spy(BenObstetricCancerHistory.class);
		obstetricCancerHstry.setID(1L);
		when(benObstetricCancerHistoryRepoMock.save(isA(BenObstetricCancerHistory.class))).thenReturn(obstetricCancerHstry);
		
		
		CancerSignAndSymptoms css= spy(CancerSignAndSymptoms.class);
		css.setID(1L);
		when(cancerSignAndSymptomsRepoMock.save(isA(CancerSignAndSymptoms.class))).thenReturn(css);
		
		List<CancerLymphNodeDetails> lymphNodes = new ArrayList<CancerLymphNodeDetails>();
		CancerLymphNodeDetails lymphNode = new CancerLymphNodeDetails();
		lymphNode.setID(1L);
		lymphNodes.add(lymphNode );
		when(cancerLymphNodeExaminationRepoMock.save(Matchers.anyListOf(CancerLymphNodeDetails.class))).thenReturn(lymphNodes);
		
		CancerOralExamination cancerOralExamination = spy(CancerOralExamination.class);
		cancerOralExamination.setID(1L);
		when(cancerOralExaminationRepoMock.save(isA(CancerOralExamination.class))).thenReturn(cancerOralExamination);
		
		CancerBreastExamination cancerBreastExamination = spy(CancerBreastExamination.class);
		cancerBreastExamination.setID(1L);
		when(cancerBreastExaminationRepoMock.save(isA(CancerBreastExamination.class))).thenReturn(cancerBreastExamination);
		
		CancerAbdominalExamination abdominalExamn = spy(CancerAbdominalExamination.class);
		abdominalExamn.setID(1L);
		when(cancerAbdominalExaminationRepoMock.save(isA(CancerAbdominalExamination.class))).thenReturn(abdominalExamn);
		
		CancerGynecologicalExamination cancerGynecologicalExamination = spy(CancerGynecologicalExamination.class);
		cancerGynecologicalExamination.setID(1L);
		when(cancerGynecologicalExaminationRepoMock.save(isA(CancerGynecologicalExamination.class))).thenReturn(cancerGynecologicalExamination);
		
		List<CancerExaminationImageAnnotation> imgAntns = new ArrayList<CancerExaminationImageAnnotation>();
		CancerExaminationImageAnnotation imgAntn = spy(CancerExaminationImageAnnotation.class);
		imgAntns.add(imgAntn);
		when(cancerExaminationImageAnnotationRepoMock.save(Matchers.anyListOf(CancerExaminationImageAnnotation.class))).thenReturn(imgAntns);
		
		BenCancerVitalDetail benCancerVitalDetail= spy(BenCancerVitalDetail.class);
		benCancerVitalDetail.setID(1L);
		when(benCancerVitalDetailRepoMock.save(isA(BenCancerVitalDetail.class))).thenReturn(benCancerVitalDetail);
		
		when(registrarRepoBenDataMock.updateBenFlowStatus('N',7056L)).thenReturn(1);
		
		CancerDiagnosis diagnosis = spy(CancerDiagnosis.class);
		diagnosis.setID(1L);
		when(cancerDiagnosisRepoMock.save(isA(CancerDiagnosis.class))).thenReturn(diagnosis);
		
		when(benVisitDetailRepoMock.updateBenFlowStatus("D", benVisitID)).thenReturn(1);
		
		//Mcoking update calls
		when(benFamilyCancerHistoryRepoMock.deleteExistingFamilyRecord(1L, "N")).thenReturn(1);
		
		when(benObstetricCancerHistoryRepoMock.getObstetricCancerHistoryStatus(
				Matchers.anyLong(), Matchers.anyLong())).thenReturn("N");
		
		//Integer, String, Boolean, String, Integer, Boolean, Boolean, Boolean, Integer, Boolean, Integer, Integer, String, Boolean, Boolean, Integer, Boolean, Boolean, String, Long, Long, String
		
		
		
		when(benObstetricCancerHistoryRepoMock.updateBenObstetricCancerHistory(Matchers.any(), Matchers.anyString(), Matchers.any(), Matchers.anyString(), 
				Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(),  Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), 
				Matchers.anyString(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.anyString(), 
				Matchers.anyLong(), Matchers.anyLong(), Matchers.anyString())).thenReturn(1);
		
		
		when(benPersonalCancerHistoryRepoMock.getPersonalCancerHistoryStatus(Matchers.anyLong(), Matchers.anyLong())).thenReturn("N");
		
		when(benPersonalCancerHistoryRepoMock.updateBenPersonalCancerHistory(Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(),
				Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), 
				Matchers.any(), Matchers.any())).thenReturn(1);
		 
		 
		when(benPersonalCancerDietHistoryRepoMock.getPersonalCancerDietHistoryStatus(Matchers.anyLong(), Matchers.anyLong())).thenReturn("N");
		 
		when(benPersonalCancerDietHistoryRepoMock.updateBenPersonalCancerDietHistory(Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(),
				Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), 
				Matchers.any(), Matchers.any())).thenReturn(1);
		
		when(benCancerVitalDetailRepoMock.updateBenCancerVitalDetail(Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), 
				Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), 
				Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any()
				, Matchers.any(), Matchers.any(), Matchers.any())).thenReturn(1);
		
		
		
		when(cancerSignAndSymptomsRepoMock.getCancerSignAndSymptomsStatus(
				Matchers.any(), Matchers.any())).thenReturn("N");
		
		
		when(cancerSignAndSymptomsRepoMock.updateCancerSignAndSymptoms(Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), 
				Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), 
				Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), 
				Matchers.any())).thenReturn(1);
		
		
		when(cancerLymphNodeExaminationRepoMock.deleteExistingLymphNodeDetails(Matchers.any(), Matchers.any())).thenReturn(1);
		
		when(cancerOralExaminationRepoMock.getCancerOralExaminationStatus(
				Matchers.any(), Matchers.any())).thenReturn("N");
		
		
		when(cancerOralExaminationRepoMock.updateCancerOralExaminationDetails(Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), 
				Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any())).thenReturn(1);
		
		when(cancerBreastExaminationRepoMock.getCancerBreastExaminationStatus(
				Matchers.any(), Matchers.any())).thenReturn("N");
		
		when(cancerBreastExaminationRepoMock.updateCancerBreastExaminatio(Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), 
				Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(),Matchers.any(), 
				Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any())).thenReturn(1);
		
		when(cancerBreastExaminationRepoMock.getCancerBreastExaminationStatus(
				Matchers.any(), Matchers.any())).thenReturn("N");
		
		when(cancerBreastExaminationRepoMock.updateCancerBreastExaminatio(Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), 
				Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(),Matchers.any(), 
				Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any())).thenReturn(1);
		
		when(cancerAbdominalExaminationRepoMock.getCancerAbdominalExaminationStatus(
				Matchers.any(), Matchers.any())).thenReturn("N");
		
		when(cancerAbdominalExaminationRepoMock.updateCancerAbdominalExamination(Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), 
				Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(),Matchers.any(), 
				Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any())).thenReturn(1);
		
		when(cancerGynecologicalExaminationRepoMock.getCancerGynecologicalExaminationStatus(
				Matchers.any(), Matchers.any())).thenReturn("N");
		
		when(cancerGynecologicalExaminationRepoMock.updateCancerGynecologicalExamination(Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), 
				Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(),Matchers.any(), 
				Matchers.any(), Matchers.any(), Matchers.any())).thenReturn(1);
		
		ArrayList list = new ArrayList<Object[]>();
		Object[] objs= {1, "N"};
		list.add(objs);
		when(cancerExaminationImageAnnotationRepoMock
		.getCancerExaminationImageAnnotationDetailsStatus(
				Matchers.any(), Matchers.any(), Matchers.anyList())).thenReturn(list);
		
		when(cancerExaminationImageAnnotationRepoMock.deleteExistingImageAnnotationDetails(Matchers.any(), Matchers.any())).thenReturn(1);
		
		when(cancerDiagnosisRepoMock.updateDetailsByOncologist(Matchers.any(), Matchers.any(),Matchers.any(), Matchers.any(), Matchers.any())).thenReturn(1);
		
		when(benVisitDetailRepoMock.getVisitDetails(beneficiaryRegID, benVisitID)).thenReturn(spy(BeneficiaryVisitDetail.class));
		
		List<BenFamilyCancerHistory> bfc=new ArrayList<BenFamilyCancerHistory>();
		when(benFamilyCancerHistoryRepoMock.getBenFamilyHistory(beneficiaryRegID, benVisitID)).thenReturn(bfc);
		
		when(benObstetricCancerHistoryRepoMock.getBenObstetricCancerHistory(beneficiaryRegID, benVisitID)).thenReturn(new BenObstetricCancerHistory());
		
		when(benPersonalCancerHistoryRepoMock.getBenPersonalHistory(beneficiaryRegID, benVisitID)).thenReturn(new BenPersonalCancerHistory());
		
		when(benPersonalCancerDietHistoryRepoMock.getBenPersonaDietHistory(beneficiaryRegID, benVisitID)).thenReturn(new BenPersonalCancerDietHistory());

		when(benCancerVitalDetailRepoMock.getBenCancerVitalDetail(beneficiaryRegID, benVisitID)).thenReturn(new BenCancerVitalDetail());
		
		when(cancerAbdominalExaminationRepoMock.getBenCancerAbdominalExaminationDetails(beneficiaryRegID, benVisitID)).thenReturn(new CancerAbdominalExamination());
		
		when(cancerBreastExaminationRepoMock.getBenCancerBreastExaminationDetails(beneficiaryRegID, benVisitID)).thenReturn(new CancerBreastExamination());
		
		when(cancerGynecologicalExaminationRepoMock.getBenCancerGynecologicalExaminationDetails(beneficiaryRegID, benVisitID)).thenReturn(new CancerGynecologicalExamination());
		
		when(cancerSignAndSymptomsRepoMock.getBenCancerSignAndSymptomsDetails(beneficiaryRegID, benVisitID)).thenReturn(new CancerSignAndSymptoms());
		
		when(cancerLymphNodeExaminationRepoMock.getBenCancerLymphNodeDetails(beneficiaryRegID, benVisitID)).thenReturn(lymphNodes);
		
		when(cancerOralExaminationRepoMock.getBenCancerOralExaminationDetails(beneficiaryRegID, benVisitID)).thenReturn(new CancerOralExamination());
		
		List<CancerExaminationImageAnnotation> imgAntnsList = new ArrayList<CancerExaminationImageAnnotation>();
		when(cancerExaminationImageAnnotationRepoMock.getCancerExaminationImageAnnotationList(beneficiaryRegID, benVisitID)).thenReturn(imgAntnsList);
		
		List<Objects[]> benDemoData = new ArrayList<Objects[]>();
		when(registrarRepoBenDemoDataMock.getBeneficiaryDemographicData(beneficiaryRegID)).thenReturn(benDemoData);
		
		List<Object[]> benData = new ArrayList<Object[]>();
		when(registrarRepoBenDataMock.getBenDetailsByRegID(beneficiaryRegID)).thenReturn(benData);
		
		
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		when(benFamilyCancerHistoryRepoMock.getBenCancerFamilyHistory(beneficiaryRegID)).thenReturn(result);
		
		when(benPersonalCancerHistoryRepoMock.getBenPersonalHistory(beneficiaryRegID)).thenReturn(result);
		
		when(benPersonalCancerDietHistoryRepoMock.getBenPersonaDietHistory(beneficiaryRegID)).thenReturn(result);
		
		when(benObstetricCancerHistoryRepoMock.getBenObstetricCancerHistoryData(beneficiaryRegID)).thenReturn(result);
	}
	
//	@Test
//	public void  saveCancerScreeningNurseDataPveTest(){
//		
//		Long response = null;
//		try
//		{
//			response = cSServiceImpl.saveCancerScreeningNurseData(nurseJsnOBJPve);
//			
//		} catch (Exception e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		assertThat(response).isEqualTo(1);
//	}
//	@Test
//	public void  saveCancerScreeningNurseDataNveTest(){
//		
//		Long response = null;
//		try
//		{
//			response = cSServiceImpl.saveCancerScreeningNurseData(nurseJsnOBJNve);
//
//		} catch (Exception e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		assertThat(response).isEqualTo(null);
//	}
//	@Test
//	public void  saveCancerScreeningNurseDataNveTest2(){
//		
//		Long response = null;
//		try
//		{
//			response = cSServiceImpl.saveCancerScreeningNurseData(nurseJsnOBJNve2);
//
//		} catch (Exception e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		assertThat(response).isEqualTo(1L);
//	}
	@Test
	public void  saveCancerScreeningDoctorDataPveTest(){
		
		Long response = null;
		try
		{
		//	response = cSServiceImpl.saveCancerScreeningDoctorData(doctorJsnOBJPve);
			
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo(1);
	}
	
	@Test
	public void  saveCancerScreeningDoctorDataNveTest(){
		
		Long response = null;
		try
		{
			//response = cSServiceImpl.saveCancerScreeningDoctorData(doctorJsnOBJNve);
			
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo(1);
	}
	
	@Test
	public void  UpdateCSHistoryNurseDataPveTest(){
		
		int response = 0;
		try
		{
			response = cSServiceImpl.UpdateCSHistoryNurseData(updateHstryJsnObjPve);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo(1);
	}
	
	@Test
	public void  updateBenVitalDetailPveTest(){
		
		int response = 0;
		try
		{
			BenCancerVitalDetail benCancerVitalDetail = InputMapper.gson().fromJson(updateVitalObjPve,
					BenCancerVitalDetail.class);
			response = cSServiceImpl.updateBenVitalDetail(benCancerVitalDetail);
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo(1);
	}
	
	@Test
	public void  updateBenVitalDetailNveTest(){
		
		int response = 0;
		try
		{
			BenCancerVitalDetail benCancerVitalDetail = InputMapper.gson().fromJson(updateVitalObjNve,
					BenCancerVitalDetail.class);
			response = cSServiceImpl.updateBenVitalDetail(benCancerVitalDetail);
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo(1);
	}
	@Test
	public void  updateBenVitalDetailNveTest2(){
		
		int response = 0;
		try
		{
			BenCancerVitalDetail benCancerVitalDetail = InputMapper.gson().fromJson(updateVitalObjNve2,
					BenCancerVitalDetail.class);
			response = cSServiceImpl.updateBenVitalDetail(benCancerVitalDetail);
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo(1);
	}
	@Test
	public void  updateBenExaminationDetailPveTest(){
		
		int response = 0;
		try
		{
			response = cSServiceImpl.updateBenExaminationDetail(updateExmnJsnObjPve);
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo(1);
	}
	
	@Test
	public void  updateBenExaminationDetailNveTest(){
		
		int response = 0;
		try
		{
			response = cSServiceImpl.updateBenExaminationDetail(updateExmnJsnObjNve);
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo(1);
	}
	
	@Test
	public void  updateCancerDiagnosisDetailsByOncologistPveTest(){
		
		int response = 0;
		try
		{
			CancerDiagnosis cancerDiagnosis = InputMapper.gson().fromJson(updateExmnJsnObjPve, CancerDiagnosis.class);
			response = cSServiceImpl.updateCancerDiagnosisDetailsByOncologist(cancerDiagnosis);
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo(1);
	}
	
	@Test
	public void  getBenDataFrmNurseToDocVisitDetailsScreenPveTest(){
		
		String response = "";
		try
		{
			response = cSServiceImpl.getBenDataFrmNurseToDocVisitDetailsScreen(beneficiaryRegID, benVisitID);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo("{\"benVisitDetails\":{}}");
	}
	
	@Test
	public void  getBenDataFrmNurseScrnToDocScrnHistoryPveTest(){
		
		String response = "";
		try
		{
			response = cSServiceImpl.getBenDataFrmNurseToDocHistoryScreen(beneficiaryRegID, benVisitID);
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo("{\"benPersonalDietHistory\":{\"ID\":null,\"beneficiaryRegID\":null,\"benVisitID\":null,\"providerServiceMapID\":null,\"dietType\":null,\"fruitConsumptionDays\":null,\"fruitQuantityPerDay\":null,\"vegetableConsumptionDays\":null,\"vegetableQuantityPerDay\":null,\"intakeOfOutsidePreparedMeal\":null,\"typeOfOilConsumed\":null,\"physicalActivityType\":null,\"ssRadiationExposure\":null,\"isThyroidDisorder\":null,\"deleted\":null,\"processed\":null,\"createdBy\":null,\"CreatedDate\":null,\"modifiedBy\":null,\"lastModDate\":null,\"vanSerialNo\":null,\"vehicalNo\":null,\"parkingPlaceID\":null,\"syncedBy\":null,\"syncedDate\":null,\"reservedForChange\":null,\"typeOfOilConsumedList\":[],\"captureDate\":null},\"benObstetricHistory\":{\"ID\":null,\"beneficiaryRegID\":null,\"benVisitID\":null,\"providerServiceMapID\":null,\"pregnancyStatus\":null,\"isUrinePregTest\":null,\"pregnant_No\":null,\"noOfLivingChild\":null,\"isAbortion\":null,\"isOralContraceptiveUsed\":null,\"isHormoneReplacementTherapy\":null,\"menarche_Age\":null,\"isMenstrualCycleRegular\":null,\"menstrualCycleLength\":null,\"menstrualFlowDuration\":null,\"menstrualFlowType\":null,\"isDysmenorrhea\":null,\"isInterMenstrualBleeding\":null,\"menopauseAge\":null,\"isPostMenopauseBleeding\":null,\"isFoulSmellingDischarge\":null,\"deleted\":null,\"processed\":null,\"createdBy\":null,\"createdDate\":null,\"modifiedBy\":null,\"lastModDate\":null,\"vanSerialNo\":null,\"vehicalNo\":null,\"parkingPlaceID\":null,\"syncedBy\":null,\"syncedDate\":null,\"reservedForChange\":null,\"captureDate\":null},\"benFamilyHistory\":[],\"benPersonalHistory\":{\"ID\":null,\"beneficiaryRegID\":null,\"benVisitID\":null,\"providerServiceMapID\":null,\"tobaccoUse\":null,\"startAge_year\":null,\"endAge_year\":null,\"typeOfTobaccoProduct\":null,\"quantityPerDay\":null,\"isFilteredCigaerette\":null,\"isCigaretteExposure\":null,\"isBetelNutChewing\":null,\"durationOfBetelQuid\":null,\"alcoholUse\":null,\"ssAlcoholUsed\":null,\"frequencyOfAlcoholUsed\":null,\"deleted\":null,\"processed\":null,\"createdBy\":null,\"createdDate\":null,\"modifiedBy\":null,\"lastModDate\":null,\"vanSerialNo\":null,\"vehicalNo\":null,\"parkingPlaceID\":null,\"syncedBy\":null,\"syncedDate\":null,\"reservedForChange\":null,\"typeOfTobaccoProductList\":null,\"captureDate\":null}}");
	}
	@Test
	public void  getBenDataFrmNurseToDocVitalScreenPveTest(){
		String response = "";
		try
		{
			response = cSServiceImpl.getBenDataFrmNurseToDocVitalScreen(beneficiaryRegID, benVisitID);
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo("{\"benVitalDetails\":{}}");
	}
	
	@Test
	public void  getBenDataFrmNurseToDocExaminationScreenPveTest(){
		String response = "";
		try
		{
			response = cSServiceImpl.getBenDataFrmNurseToDocExaminationScreen(beneficiaryRegID, benVisitID);
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo("{\"signsAndSymptoms\":{},\"BenCancerLymphNodeDetails\":[{\"ID\":1}],\"breastExamination\":{},\"imageCoordinates\":[],\"gynecologicalExamination\":{},\"abdominalExamination\":{},\"oralExamination\":{}}");
	}
	
	@Test
	public void  getBenDataForCaseSheetPveTest(){
		String response = "";
		try
		{
			//response = cSServiceImpl.getCancerCasesheetData(fetchJsnObjPve);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo("{\"signsAndSymptoms\":{},\"BenCancerLymphNodeDetails\":[{\"ID\":1}],\"breastExamination\":{},\"benPersonalDietHistory\":{\"typeOfOilConsumedList\":[]},\"patientPersonalHistory\":{},\"patientObstetricHistory\":{},\"oralExamination\":{},\"ImageAnnotatedData\":[],\"gynecologicalExamination\":{},\"currentVitals\":{},\"abdominalExamination\":{},\"familyDiseaseHistory\":[]}");
	}
	
	@Test
	public void getBenFamilyHistoryDataPveTest(){
		String response = "";
		try
		{
			response = cSServiceImpl.getBenFamilyHistoryData(beneficiaryRegID);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo("{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"cancerDiseaseType\",\"columnName\":\"Cancer Disease Type\"},{\"keyName\":\"familyMember\",\"columnName\":\"Family Members\"}]}");
	}
	
	@Test
	public void getBenPersonalHistoryDataPveTest(){
		String response = "";
		try
		{
			response = cSServiceImpl.getBenPersonalHistoryData(beneficiaryRegID);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo("{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"tobaccoUse\",\"columnName\":\"Tobacco Use Status\"},{\"keyName\":\"startAge_year\",\"columnName\":\"Start Age(Years)\"},{\"keyName\":\"endAge_year\",\"columnName\":\"Stop Age(Years)\"},{\"keyName\":\"typeOfTobaccoProduct\",\"columnName\":\"Type Of Tobacco Product\"},{\"keyName\":\"quantityPerDay\",\"columnName\":\"Quantity(Per Day)\"},{\"keyName\":\"isFilteredCigaerette\",\"columnName\":\"Filtered Cigarette\"},{\"keyName\":\"isCigaretteExposure\",\"columnName\":\"Exposure to Cigarette\"},{\"keyName\":\"isBetelNutChewing\",\"columnName\":\"Betel Nut Chewing\"},{\"keyName\":\"durationOfBetelQuid\",\"columnName\":\"Duration Of Betel Quid in Mouth(Mins)\"},{\"keyName\":\"alcoholUse\",\"columnName\":\"Alcohol use Status\"},{\"keyName\":\"ssAlcoholUsed\",\"columnName\":\"Alcohol Consumed(within 12 months)\"},{\"keyName\":\"frequencyOfAlcoholUsed\",\"columnName\":\"Frequency Of Alcohol Use(within 12 months)\"}]}");
	}

	@Test
	public void getBenPersonalDietHistoryDataPveTest(){
		String response = "";
		try
		{
			response = cSServiceImpl.getBenPersonalDietHistoryData(beneficiaryRegID);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo("{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"dietType\",\"columnName\":\"Diet Type\"},{\"keyName\":\"fruitConsumptionDays\",\"columnName\":\"Fruit Consumption(Days/week)\"},{\"keyName\":\"fruitQuantityPerDay\",\"columnName\":\"Fruit Quantity(Cups/day)\"},{\"keyName\":\"vegetableConsumptionDays\",\"columnName\":\"Vegetable Consumption(Days/week)\"},{\"keyName\":\"vegetableQuantityPerDay\",\"columnName\":\"Vegetable Quantity(Cups/day)\"},{\"keyName\":\"intakeOfOutsidePreparedMeal\",\"columnName\":\"Weekly Intake Of Outside Prepared Meal\"},{\"keyName\":\"typeOfOilConsumed\",\"columnName\":\"Type Of Oil Consumed\"},{\"keyName\":\"physicalActivityType\",\"columnName\":\"Physical Activity Type\"},{\"keyName\":\"ssRadiationExposure\",\"columnName\":\"History of exposure to radiation\"},{\"keyName\":\"isThyroidDisorder\",\"columnName\":\"History of any thyroid disorder\"}]}");
	}
	
	@Test
	public void getBenObstetricHistoryDataPveTest(){
		String response = "";
		try
		{
			response = cSServiceImpl.getBenObstetricHistoryData(beneficiaryRegID);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(response).isEqualTo("{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"pregnancyStatus\",\"columnName\":\"Pregnancy Status\"},{\"keyName\":\"isUrinePregTest\",\"columnName\":\"Urine pregnancy test\"},{\"keyName\":\"pregnant_No\",\"columnName\":\"No of times Pregnant\"},{\"keyName\":\"noOfLivingChild\",\"columnName\":\"No of Living Children\"},{\"keyName\":\"isAbortion\",\"columnName\":\"Abortions\"},{\"keyName\":\"isOralContraceptiveUsed\",\"columnName\":\"Oral Contraceptives used in 5 years\"},{\"keyName\":\"isHormoneReplacementTherapy\",\"columnName\":\"Hormone replacement Therapy in 5yrs\"},{\"keyName\":\"menarche_Age\",\"columnName\":\"Age at menarche(Years)\"},{\"keyName\":\"isMenstrualCycleRegular\",\"columnName\":\"Regularity of Menstrual Cycle\"},{\"keyName\":\"menstrualCycleLength\",\"columnName\":\"Length of Menstrual Cycle(in days)\"},{\"keyName\":\"menstrualFlowDuration\",\"columnName\":\"Menstrual Flow Duration(Days)\"},{\"keyName\":\"menstrualFlowType\",\"columnName\":\"Type of Flow\"},{\"keyName\":\"isDysmenorrhea\",\"columnName\":\"Dysmenorrhea\"},{\"keyName\":\"isInterMenstrualBleeding\",\"columnName\":\"Inter menstrual bleeding\"},{\"keyName\":\"menopauseAge\",\"columnName\":\"Age at Menopause(Years)\"},{\"keyName\":\"isPostMenopauseBleeding\",\"columnName\":\"Post-menopausal Bleeding\"},{\"keyName\":\"isFoulSmellingDischarge\",\"columnName\":\"Foul Smelling Discharge\"}]}");
	}
	
}
