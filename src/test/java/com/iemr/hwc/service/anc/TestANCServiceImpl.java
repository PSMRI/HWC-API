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
package com.iemr.mmu.anc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.anc.ANCCareDetails;
import com.iemr.hwc.data.anc.ANCDiagnosis;
import com.iemr.hwc.data.anc.ANCWomenVaccineDetail;
import com.iemr.hwc.data.anc.BenAdherence;
import com.iemr.hwc.data.anc.SysObstetricExamination;
import com.iemr.hwc.repo.nurse.anc.ANCCareRepo;
import com.iemr.hwc.repo.nurse.anc.ANCDiagnosisRepo;
import com.iemr.hwc.repo.nurse.anc.ANCWomenVaccineRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.nurse.anc.SysObstetricExaminationRepo;
import com.iemr.hwc.service.anc.ANCNurseServiceImpl;
import com.iemr.hwc.service.anc.ANCServiceImpl;
import com.iemr.mmu.common.TestCommonServices;

@ExtendWith(MockitoExtension.class)
public class TestANCServices
{

	private static ANCServiceImpl ancServiceImpl = spy(ANCServiceImpl.class);
	private static ANCNurseServiceImpl ancNurseServiceImpl = spy(ANCNurseServiceImpl.class);
	//private static ANCDoctorServiceImpl ancDoctorServiceImpl = spy(ANCDoctorServiceImpl.class);
	
	private static BenAdherenceRepo benAdherenceRepoMock = mock(BenAdherenceRepo.class);
	private static ANCCareRepo ancCareRepoMock = mock(ANCCareRepo.class);
	private static ANCWomenVaccineRepo ancWomenVaccineRepoMock = mock(ANCWomenVaccineRepo.class);
	private static SysObstetricExaminationRepo sysObstetricExaminationRepoMock = mock(SysObstetricExaminationRepo.class);
	private static ANCDiagnosisRepo ancDiagnosisRepoMock = mock(ANCDiagnosisRepo.class);
	
	static String nurseSaveObjPve;
	static JsonObject nurseSavejsnOBJPve;
	static String nurseSaveObjNve;
	static JsonObject nurseSavejsnOBJNve;
	
	static String doctorSaveObjPve;
	static String doctorSaveObjNve;
	static JsonObject doctorSavejsnOBJPve;
	static JsonObject doctorSavejsnOBJNve;
	
	static Long beneficiaryRegID = 7506L;
	static Long benVisitID = 131L;
	
	@BeforeAll
	public static void initializeParams()
	{

		TestCommonServices.initializeParams();
		ancServiceImpl.setCommonNurseServiceImpl(TestCommonServices.commonNurseServiceImpl);
		ancServiceImpl.setAncNurseServiceImpl(ancNurseServiceImpl);
		ancServiceImpl.setCommonDoctorServiceImpl(TestCommonServices.commonDoctorServiceImpl);
		//ancServiceImpl.setANCDoctorServiceImpl(ancDoctorServiceImpl);
		
		ancNurseServiceImpl.setBenAdherenceRepo(benAdherenceRepoMock);
		ancNurseServiceImpl.setAncCareRepo(ancCareRepoMock);
		ancNurseServiceImpl.setAncWomenVaccineRepo(ancWomenVaccineRepoMock);
		ancNurseServiceImpl.setSysObstetricExaminationRepo(sysObstetricExaminationRepoMock);
		ancNurseServiceImpl.setLabTestOrderDetailRepo(TestCommonServices.labTestOrderDetailRepoMock);
		
		//ancDoctorServiceImpl.setAncDiagnosisRepo(ancDiagnosisRepoMock);
		
		nurseSaveObjPve = "{ \"visitDetails\": { \"visitDetails\": { \"beneficiaryRegID\": \"7403\", \"providerServiceMapID\": \"1316\", \"visitNo\": null, \"visitReason\": \"FollowUp\", \"visitCategory\": \"ANC\", \"pregnancyStatus\": \"Yes\", \"rCHID\": \"444\", \"healthFacilityType\": null, \"healthFacilityLocation\": null, \"reportFilePath\": null, \"createdBy\": \"786\" }, \"chiefComplaints\": [ { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"chiefComplaint\": \"Abdominal Bloating\", \"chiefComplaintID\": 1, \"duration\": \"23\", \"unitOfDuration\": \"Hours\", \"description\": \"dffjnsfklsndfklsdfl\", \"createdBy\": \"786\" }, { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"chiefComplaint\": \"Abdominal Mass \", \"chiefComplaintID\": 2, \"duration\": \"24\", \"unitOfDuration\": \"Hours\", \"description\": \"sdlkfnskdfnskdfnskdlf\", \"createdBy\": \"786\" } ], \"adherence\": { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\", \"toDrugs\": false, \"drugReason\": \"sdnfklsdnfklsdnf\", \"toReferral\": false, \"referralReason\": \"klsdmfklsdmfklsdfmskldfm\", \"progress\": \"Improved\" }, \"investigation\": { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\", \"laboratoryList\": [ { \"testID\": 17, \"testName\": \"BUN, Creatinine, Uric acid\", \"isRadiologyImaging\": false }, { \"testID\": 14, \"testName\": \"Complete blood count\", \"isRadiologyImaging\": false }, { \"testID\": 15, \"testName\": \"Haemogram\", \"isRadiologyImaging\": false } ] } }, \"ancDetails\": { \"ancObstetricDetails\": { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\", \"primiGravida\": false, \"lmpDate\": \"2017-08-20T05:07:37.000Z\", \"gestationalAgeOrPeriodofAmenorrhea_POA\": 22, \"expDelDt\": \"2018-05-27T05:07:37.000Z\", \"duration\": \"4\", \"trimesterNumber\": 2, \"gravida_G\": 4, \"termDeliveries_T\": \"1\", \"pretermDeliveries_P\": \"1\", \"abortions_A\": \"1\", \"livebirths_L\": \"1\", \"bloodGroup\": \"AB-\" }, \"ancImmunization\": { \"tT_1Status\": \"Received\", \"dateReceivedForTT_1\": \"2017-11-10T05:10:57.000Z\", \"facilityNameOfTT_1\": \"Govt Facility\", \"tT_2Status\": \"Received\", \"dateReceivedForTT_2\": \"2017-12-10T05:10:57.000Z\", \"facilityNameOfTT_2\": \"Govt Facility\", \"tT_3Status\": \"Received\", \"dateReceivedForTT_3\": \"2018-01-10T05:10:57.000Z\", \"facilityNameOfTT_3\": \"Govt Facility\", \"beneficiaryRegID\": \"7403\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" } }, \"vitalDetails\": { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"weight_Kg\": \"32\", \"height_cm\": \"166\", \"waistCircumference_cm\": null, \"hipCircumference_cm\": null, \"bMI\": 11.6, \"waistHipRatio\": null, \"temperature\": \"98\", \"pulseRate\": \"72\", \"systolicBP_1stReading\": \"120\", \"diastolicBP_1stReading\": \"90\", \"bloodGlucose_Fasting\": null, \"bloodGlucose_Random\": null, \"bloodGlucose_2hr_PP\": null, \"respiratoryRate\": \"34\", \"createdBy\": \"786\" }, \"examinationDetails\": { \"generalExamination\": { \"consciousness\": \"Semiconscious\", \"coherence\": \"Coherent\", \"cooperation\": \"Cooperative\", \"comfortness\": \"Comfortable\", \"builtAndAppearance\": \"Thin Built\", \"gait\": \"Normal\", \"dangerSigns\": \"Yes\", \"typeOfDangerSigns\": [ 1 ], \"pallor\": \"Present\", \"jaundice\": \"Present\", \"cyanosis\": \"Present\", \"clubbing\": \"Present\", \"lymphadenopathy\": \"Present\", \"lymphnodesInvolved\": \"Cervical LN\", \"typeOfLymphadenopathy\": \"Matting\", \"edema\": \"Present\", \"extentOfEdema\": \"Facial Puffiness\", \"edemaType\": \"Pitting\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"headToToeExamination\": { \"headtoToeExam\": \"Abnormal\", \"head\": \"lorem\", \"eyes\": \"lorem\", \"ears\": \"lorem\", \"nose\": \"lorem\", \"oralCavity\": \"lorem\", \"throat\": \"lorem\", \"breastAndNipples\": \"lorem\", \"trunk\": \"lorem\", \"upperLimbs\": \"lorem\", \"lowerLimbs\": \"lorem\", \"skin\": \"lorem\", \"hair\": \"lorem\", \"nails\": \"lorem\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"gastroIntestinalExamination\": { \"inspection\": null, \"palpation_AbdomenTexture\": null, \"palpation_Liver\": null, \"palpation_Spleen\": null, \"palpation_Tenderness\": null, \"palpation_LocationOfTenderness\": null, \"percussion\": null, \"auscultation\": null, \"analRegion\": null, \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"cardioVascularExamination\": { \"jugularVenousPulse_JVP\": \"Normal\", \"apexbeatLocation\": \"lorem\", \"apexbeatType\": \"lorem\", \"firstHeartSound_S1\": \"lorem\", \"secondHeartSound_S2\": \"lorem\", \"additionalHeartSounds\": \"lorem\", \"murmurs\": \"Present\", \"pericardialRub\": \"Present\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"respiratorySystemExamination\": { \"trachea\": null, \"inspection\": \"lorem\", \"signsOfRespiratoryDistress\": \"Present\", \"palpation\": \"lorem\", \"auscultation_Stridor\": \"Present\", \"auscultation_BreathSounds\": \"Normal\", \"auscultation_Crepitations\": \"Present\", \"auscultation_Wheezing\": \"Present\", \"auscultation_PleuralRub\": \"Present\", \"auscultation_ConductedSounds\": \"Present\", \"percussion\": \"Dull\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"centralNervousSystemExamination\": { \"handedness\": \"Right Handed\", \"cranialNervesExamination\": \"lnsakldasn\", \"motorSystem\": \"klsdmfklsmfkl\", \"sensorySystem\": \"klsdmfkls\", \"autonomicSystem\": \"klndklsdf\", \"cerebellarSigns\": \"klsdfklsdfl\", \"signsOfMeningealIrritation\": \"Present\", \"skull\": \"sdjknfsjkldfnskld\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"musculoskeletalSystemExamination\": { \"joint_TypeOfJoint\": \"Ankle\", \"joint_Laterality\": \"Left\", \"joint_Abnormality\": \"Swelling\", \"upperLimb_Laterality\": \"Left\", \"upperLimb_Abnormality\": \"Swelling\", \"lowerLimb_Laterality\": \"Left\", \"lowerLimb_Abnormality\": \"Swelling\", \"chestWall\": \"sdkjfnasklfnslkdfn\", \"spine\": \"lskdnfklsdnfklsdnfl\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"genitoUrinarySystemExamination\": { \"renalAngle\": \"sjkdnfjksdnfklsnd\", \"suprapubicRegion\": \"lknsdklfnsdklf\", \"externalGenitalia\": \"lnsdklfnsdklfnsdjkl\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"obstetricExamination\": { \"fundalHeight\": \"Not Palpable\", \"fHAndPOA_Status\": \"FH=POA\", \"fHAndPOA_Interpretation\": \"Corresponding\", \"fetalMovements\": \"Present\", \"fetalHeartSounds\": \"Audible\", \"fetalHeartRate_BeatsPerMinute\": \"<120\", \"fetalPositionOrLie\": \"Longitudinal\", \"fetalPresentation\": \"Shoulder\", \"abdominalScars\": \"Absent\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" } }, \"historyDetails\": { \"pastHistory\": { \"pastIllness\": [ { \"illnessTypeID\": \"6\", \"illnessType\": \"Asthma\", \"otherIllnessType\": null, \"timePeriodAgo\": \"23\", \"timePeriodUnit\": \"Days\" }, { \"illnessTypeID\": \"22\", \"illnessType\": \"Other\", \"otherIllnessType\": \"skjdnaksjdnasjkdn\", \"timePeriodAgo\": \"24\", \"timePeriodUnit\": \"Days\" } ], \"pastSurgery\": [ { \"surgeryID\": \"3\", \"surgeryType\": \"Appendicectomy\", \"otherSurgeryType\": null, \"timePeriodAgo\": \"22\", \"timePeriodUnit\": \"Days\" }, { \"surgeryID\": \"6\", \"surgeryType\": \"Other\", \"otherSurgeryType\": \"jndajksdnakldnakl\", \"timePeriodAgo\": \"12\", \"timePeriodUnit\": \"Days\" } ], \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"comorbidConditions\": { \"comorbidityConcurrentConditionsList\": [ { \"otherComorbidCondition\": null, \"timePeriodAgo\": \"21\", \"timePeriodUnit\": \"Days\", \"isForHistory\": true, \"comorbidCondition\": \"Asthma\", \"comorbidConditionID\": \"6\" }, { \"otherComorbidCondition\": \"askdjnajkdnajks\", \"timePeriodAgo\": \"23\", \"timePeriodUnit\": \"Days\", \"isForHistory\": true, \"comorbidCondition\": \"Other\", \"comorbidConditionID\": \"8\" } ], \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"medicationHistory\": { \"medicationHistoryList\": [ { \"currentMedication\": \"sjkdnjkasndakjsn\", \"timePeriodAgo\": \"22\", \"timePeriodUnit\": \"Days\" }, { \"currentMedication\": \"sjkdnaklsdakldjakl\", \"timePeriodAgo\": \"24\", \"timePeriodUnit\": \"Days\" } ], \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"femaleObstetricHistory\": { \"totalNoOfPreg\": \"3\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\", \"femaleObstetricHistoryList\": [ { \"pregOrder\": 1, \"pregComplicationID\": 12, \"pregComplicationType\": \"Other\", \"otherPregComplication\": \"adhkajsdkasd\", \"pregDurationID\": 1, \"durationType\": \"Preterm\", \"deliveryTypeID\": 2, \"deliveryType\": \"Assisted Delivery\", \"deliveryPlaceID\": 9, \"deliveryPlace\": \"Other\", \"otherDeliveryPlace\": \"skldfskldfmsklf\", \"deliveryComplicationID\": 19, \"deliveryComplicationType\": \"Other\", \"otherDeliveryComplication\": \"sldfnasklfmsklfskl\", \"postpartumComplicationID\": 24, \"postpartumComplicationType\": \"Other\", \"otherPostpartumCompType\": \"slkfnskldfnskldfmskld\", \"pregOutcomeID\": 1, \"pregOutcome\": \"Live Birth\", \"postNatalComplicationID\": 29, \"postNatalComplication\": \"Other\", \"otherPostNatalComplication\": \"slfknskldfnskldfnskl\", \"newBornComplicationID\": 34, \"newBornComplication\": \"Other\", \"otherNewBornComplication\": \"sklkfnsklfnsklfsl\", \"congenitalAnomalies\": \"skldnfsklfnskldf\" } ] }, \"menstrualHistory\": { \"menstrualCycleStatus\": \"Active\", \"menstrualCycleStatusID\": \"3\", \"regularity\": \"Regular\", \"cycleLength\": \"24-28 days\", \"menstrualCyclelengthID\": \"2\", \"menstrualFlowDurationID\": \"6\", \"bloodFlowDuration\": \"3 days\", \"menstrualProblemID\": \"2\", \"problemName\": \"Irregular Menstrual Bleeding\", \"lMPDate\": \"2017-09-09T18:30:00.000Z\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"familyHistory\": { \"familyDiseaseList\": [ { \"diseaseTypeID\": \"6\", \"diseaseType\": \"Asthma\", \"otherDiseaseType\": null, \"familyMembers\": [ \"Aunt\", \"Brother\" ] }, { \"diseaseTypeID\": \"8\", \"diseaseType\": \"Other\", \"otherDiseaseType\": \"sjndajksdnakldnakl\", \"familyMembers\": [ \"Aunt\", \"Brother\" ] } ], \"isGeneticDisorder\": true, \"geneticDisorder\": \"sjknasklndklasdn\", \"isConsanguineousMarrige\": false, \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"personalHistory\": { \"dietaryType\": \"Vegetarian\", \"physicalActivityType\": \"Sedentary\", \"riskySexualPracticesStatus\": 1, \"tobaccoUseStatus\": \"Yes\", \"alcoholIntakeStatus\": \"Yes\", \"allergyStatus\": \"Yes\", \"tobaccoList\": [ { \"tobaccoUseTypeID\": 8, \"tobaccoUseType\": \"Beedi\", \"otherTobaccoUseType\": null, \"numberperDay\": \"2\", \"duration\": \"3\", \"durationUnit\": \"Days\" }, { \"tobaccoUseTypeID\": 9, \"tobaccoUseType\": \"Filter Cigarette\", \"otherTobaccoUseType\": null, \"numberperDay\": \"2\", \"duration\": \"2\", \"durationUnit\": \"Days\" } ], \"alcoholList\": [ { \"alcoholTypeID\": 16, \"typeOfAlcohol\": \"Beer\", \"otherAlcoholType\": null, \"alcoholIntakeFrequency\": \"2-3 times a month\", \"avgAlcoholConsumption\": \"120-180 ml\", \"duration\": \"23\", \"durationUnit\": \"Days\" } ], \"allergicList\": [ { \"allergyType\": \"Drugs\", \"allergyName\": \"kjasdnajksdjk\", \"typeOfAllergicReactions\": [ { \"allergicReactionTypeID\": \"9\", \"name\": \"Abdominal Cramps\" }, { \"allergicReactionTypeID\": \"1\", \"name\": \"Anaphylaxis\" } ], \"otherTypeOfAllergicReaction\": null }, { \"allergyType\": \"Food\", \"allergyName\": \"jksnfklsndfklsdf\", \"typeOfAllergicReactions\": [ { \"allergicReactionTypeID\": \"9\", \"name\": \"Abdominal Cramps\" }, { \"allergicReactionTypeID\": \"1\", \"name\": \"Anaphylaxis\" }, { \"allergicReactionTypeID\": \"3\", \"name\": \"Angioedema\" } ], \"otherTypeOfAllergicReaction\": null } ], \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"immunizationHistory\": { \"beneficiaryRegID\":\"7921\", \"createdBy\":\"786\", \"immunizationList\":[], \"providerServiceMapID\":\"1316\" }, \"childVaccineDetails\": { \"childOptionalVaccineList\": [], \"beneficiaryRegID\": \"7403\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" } } }";
		nurseSaveObjNve = "{ \"visitDetails\": { \"visitDails\": { \"beneficiaryRegID\": \"7403\", \"providerServiceMapID\": \"1316\", \"visitNo\": null, \"visitReason\": \"FollowUp\", \"visitCategory\": \"ANC\", \"pregnancyStatus\": \"Yes\", \"rCHID\": \"444\", \"healthFacilityType\": null, \"healthFacilityLocation\": null, \"reportFilePath\": null, \"createdBy\": \"786\" }, \"chiefComplaints\": [ { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"chiefComplaint\": \"Abdominal Bloating\", \"chiefComplaintID\": 1, \"duration\": \"23\", \"unitOfDuration\": \"Hours\", \"description\": \"dffjnsfklsndfklsdfl\", \"createdBy\": \"786\" }, { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"chiefComplaint\": \"Abdominal Mass \", \"chiefComplaintID\": 2, \"duration\": \"24\", \"unitOfDuration\": \"Hours\", \"description\": \"sdlkfnskdfnskdfnskdlf\", \"createdBy\": \"786\" } ], \"adherence\": { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\", \"toDrugs\": false, \"drugReason\": \"sdnfklsdnfklsdnf\", \"toReferral\": false, \"referralReason\": \"klsdmfklsdmfklsdfmskldfm\", \"progress\": \"Improved\" }, \"investigation\": { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\", \"laboratoryList\": [ { \"testID\": 17, \"testName\": \"BUN, Creatinine, Uric acid\", \"isRadiologyImaging\": false }, { \"testID\": 14, \"testName\": \"Complete blood count\", \"isRadiologyImaging\": false }, { \"testID\": 15, \"testName\": \"Haemogram\", \"isRadiologyImaging\": false } ] } }, \"ancDetails\": { \"ancObstetricDetails\": { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\", \"primiGravida\": false, \"lmpDate\": \"2017-08-20T05:07:37.000Z\", \"gestationalAgeOrPeriodofAmenorrhea_POA\": 22, \"expDelDt\": \"2018-05-27T05:07:37.000Z\", \"duration\": \"4\", \"trimesterNumber\": 2, \"gravida_G\": 4, \"termDeliveries_T\": \"1\", \"pretermDeliveries_P\": \"1\", \"abortions_A\": \"1\", \"livebirths_L\": \"1\", \"bloodGroup\": \"AB-\" }, \"ancImmunization\": { \"tT_1Status\": \"Received\", \"dateReceivedForTT_1\": \"2017-11-10T05:10:57.000Z\", \"facilityNameOfTT_1\": \"Govt Facility\", \"tT_2Status\": \"Received\", \"dateReceivedForTT_2\": \"2017-12-10T05:10:57.000Z\", \"facilityNameOfTT_2\": \"Govt Facility\", \"tT_3Status\": \"Received\", \"dateReceivedForTT_3\": \"2018-01-10T05:10:57.000Z\", \"facilityNameOfTT_3\": \"Govt Facility\", \"beneficiaryRegID\": \"7403\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" } }, \"vitalDetails\": { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"weight_Kg\": \"32\", \"height_cm\": \"166\", \"waistCircumference_cm\": null, \"hipCircumference_cm\": null, \"bMI\": 11.6, \"waistHipRatio\": null, \"temperature\": \"98\", \"pulseRate\": \"72\", \"systolicBP_1stReading\": \"120\", \"diastolicBP_1stReading\": \"90\", \"bloodGlucose_Fasting\": null, \"bloodGlucose_Random\": null, \"bloodGlucose_2hr_PP\": null, \"respiratoryRate\": \"34\", \"createdBy\": \"786\" }, \"examinationDetails\": { \"generalExamination\": { \"consciousness\": \"Semiconscious\", \"coherence\": \"Coherent\", \"cooperation\": \"Cooperative\", \"comfortness\": \"Comfortable\", \"builtAndAppearance\": \"Thin Built\", \"gait\": \"Normal\", \"dangerSigns\": \"Yes\", \"typeOfDangerSigns\": [ 1 ], \"pallor\": \"Present\", \"jaundice\": \"Present\", \"cyanosis\": \"Present\", \"clubbing\": \"Present\", \"lymphadenopathy\": \"Present\", \"lymphnodesInvolved\": \"Cervical LN\", \"typeOfLymphadenopathy\": \"Matting\", \"edema\": \"Present\", \"extentOfEdema\": \"Facial Puffiness\", \"edemaType\": \"Pitting\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"headToToeExamination\": { \"headtoToeExam\": \"Abnormal\", \"head\": \"lorem\", \"eyes\": \"lorem\", \"ears\": \"lorem\", \"nose\": \"lorem\", \"oralCavity\": \"lorem\", \"throat\": \"lorem\", \"breastAndNipples\": \"lorem\", \"trunk\": \"lorem\", \"upperLimbs\": \"lorem\", \"lowerLimbs\": \"lorem\", \"skin\": \"lorem\", \"hair\": \"lorem\", \"nails\": \"lorem\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"gastroIntestinalExamination\": { \"inspection\": null, \"palpation_AbdomenTexture\": null, \"palpation_Liver\": null, \"palpation_Spleen\": null, \"palpation_Tenderness\": null, \"palpation_LocationOfTenderness\": null, \"percussion\": null, \"auscultation\": null, \"analRegion\": null, \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"cardioVascularExamination\": { \"jugularVenousPulse_JVP\": \"Normal\", \"apexbeatLocation\": \"lorem\", \"apexbeatType\": \"lorem\", \"firstHeartSound_S1\": \"lorem\", \"secondHeartSound_S2\": \"lorem\", \"additionalHeartSounds\": \"lorem\", \"murmurs\": \"Present\", \"pericardialRub\": \"Present\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"respiratorySystemExamination\": { \"trachea\": null, \"inspection\": \"lorem\", \"signsOfRespiratoryDistress\": \"Present\", \"palpation\": \"lorem\", \"auscultation_Stridor\": \"Present\", \"auscultation_BreathSounds\": \"Normal\", \"auscultation_Crepitations\": \"Present\", \"auscultation_Wheezing\": \"Present\", \"auscultation_PleuralRub\": \"Present\", \"auscultation_ConductedSounds\": \"Present\", \"percussion\": \"Dull\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"centralNervousSystemExamination\": { \"handedness\": \"Right Handed\", \"cranialNervesExamination\": \"lnsakldasn\", \"motorSystem\": \"klsdmfklsmfkl\", \"sensorySystem\": \"klsdmfkls\", \"autonomicSystem\": \"klndklsdf\", \"cerebellarSigns\": \"klsdfklsdfl\", \"signsOfMeningealIrritation\": \"Present\", \"skull\": \"sdjknfsjkldfnskld\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"musculoskeletalSystemExamination\": { \"joint_TypeOfJoint\": \"Ankle\", \"joint_Laterality\": \"Left\", \"joint_Abnormality\": \"Swelling\", \"upperLimb_Laterality\": \"Left\", \"upperLimb_Abnormality\": \"Swelling\", \"lowerLimb_Laterality\": \"Left\", \"lowerLimb_Abnormality\": \"Swelling\", \"chestWall\": \"sdkjfnasklfnslkdfn\", \"spine\": \"lskdnfklsdnfklsdnfl\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"genitoUrinarySystemExamination\": { \"renalAngle\": \"sjkdnfjksdnfklsnd\", \"suprapubicRegion\": \"lknsdklfnsdklf\", \"externalGenitalia\": \"lnsdklfnsdklfnsdjkl\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"obstetricExamination\": { \"fundalHeight\": \"Not Palpable\", \"fHAndPOA_Status\": \"FH=POA\", \"fHAndPOA_Interpretation\": \"Corresponding\", \"fetalMovements\": \"Present\", \"fetalHeartSounds\": \"Audible\", \"fetalHeartRate_BeatsPerMinute\": \"<120\", \"fetalPositionOrLie\": \"Longitudinal\", \"fetalPresentation\": \"Shoulder\", \"abdominalScars\": \"Absent\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" } }, \"historyDetails\": { \"pastHistory\": { \"pastIllness\": [ { \"illnessTypeID\": \"6\", \"illnessType\": \"Asthma\", \"otherIllnessType\": null, \"timePeriodAgo\": \"23\", \"timePeriodUnit\": \"Days\" }, { \"illnessTypeID\": \"22\", \"illnessType\": \"Other\", \"otherIllnessType\": \"skjdnaksjdnasjkdn\", \"timePeriodAgo\": \"24\", \"timePeriodUnit\": \"Days\" } ], \"pastSurgery\": [ { \"surgeryID\": \"3\", \"surgeryType\": \"Appendicectomy\", \"otherSurgeryType\": null, \"timePeriodAgo\": \"22\", \"timePeriodUnit\": \"Days\" }, { \"surgeryID\": \"6\", \"surgeryType\": \"Other\", \"otherSurgeryType\": \"jndajksdnakldnakl\", \"timePeriodAgo\": \"12\", \"timePeriodUnit\": \"Days\" } ], \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"comorbidConditions\": { \"comorbidityConcurrentConditionsList\": [ { \"otherComorbidCondition\": null, \"timePeriodAgo\": \"21\", \"timePeriodUnit\": \"Days\", \"isForHistory\": true, \"comorbidCondition\": \"Asthma\", \"comorbidConditionID\": \"6\" }, { \"otherComorbidCondition\": \"askdjnajkdnajks\", \"timePeriodAgo\": \"23\", \"timePeriodUnit\": \"Days\", \"isForHistory\": true, \"comorbidCondition\": \"Other\", \"comorbidConditionID\": \"8\" } ], \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"medicationHistory\": { \"medicationHistoryList\": [ { \"currentMedication\": \"sjkdnjkasndakjsn\", \"timePeriodAgo\": \"22\", \"timePeriodUnit\": \"Days\" }, { \"currentMedication\": \"sjkdnaklsdakldjakl\", \"timePeriodAgo\": \"24\", \"timePeriodUnit\": \"Days\" } ], \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"femaleObstetricHistory\": { \"totalNoOfPreg\": \"3\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\", \"femaleObstetricHistoryList\": [ { \"pregOrder\": 1, \"pregComplicationID\": 12, \"pregComplicationType\": \"Other\", \"otherPregComplication\": \"adhkajsdkasd\", \"pregDurationID\": 1, \"durationType\": \"Preterm\", \"deliveryTypeID\": 2, \"deliveryType\": \"Assisted Delivery\", \"deliveryPlaceID\": 9, \"deliveryPlace\": \"Other\", \"otherDeliveryPlace\": \"skldfskldfmsklf\", \"deliveryComplicationID\": 19, \"deliveryComplicationType\": \"Other\", \"otherDeliveryComplication\": \"sldfnasklfmsklfskl\", \"postpartumComplicationID\": 24, \"postpartumComplicationType\": \"Other\", \"otherPostpartumCompType\": \"slkfnskldfnskldfmskld\", \"pregOutcomeID\": 1, \"pregOutcome\": \"Live Birth\", \"postNatalComplicationID\": 29, \"postNatalComplication\": \"Other\", \"otherPostNatalComplication\": \"slfknskldfnskldfnskl\", \"newBornComplicationID\": 34, \"newBornComplication\": \"Other\", \"otherNewBornComplication\": \"sklkfnsklfnsklfsl\", \"congenitalAnomalies\": \"skldnfsklfnskldf\" } ] }, \"menstrualHistory\": { \"menstrualCycleStatus\": \"Active\", \"menstrualCycleStatusID\": \"3\", \"regularity\": \"Regular\", \"cycleLength\": \"24-28 days\", \"menstrualCyclelengthID\": \"2\", \"menstrualFlowDurationID\": \"6\", \"bloodFlowDuration\": \"3 days\", \"menstrualProblemID\": \"2\", \"problemName\": \"Irregular Menstrual Bleeding\", \"lMPDate\": \"2017-09-09T18:30:00.000Z\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"familyHistory\": { \"familyDiseaseList\": [ { \"diseaseTypeID\": \"6\", \"diseaseType\": \"Asthma\", \"otherDiseaseType\": null, \"familyMembers\": [ \"Aunt\", \"Brother\" ] }, { \"diseaseTypeID\": \"8\", \"diseaseType\": \"Other\", \"otherDiseaseType\": \"sjndajksdnakldnakl\", \"familyMembers\": [ \"Aunt\", \"Brother\" ] } ], \"isGeneticDisorder\": true, \"geneticDisorder\": \"sjknasklndklasdn\", \"isConsanguineousMarrige\": false, \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"personalHistory\": { \"dietaryType\": \"Vegetarian\", \"physicalActivityType\": \"Sedentary\", \"riskySexualPracticesStatus\": 1, \"tobaccoUseStatus\": \"Yes\", \"alcoholIntakeStatus\": \"Yes\", \"allergyStatus\": \"Yes\", \"tobaccoList\": [ { \"tobaccoUseTypeID\": 8, \"tobaccoUseType\": \"Beedi\", \"otherTobaccoUseType\": null, \"numberperDay\": \"2\", \"duration\": \"3\", \"durationUnit\": \"Days\" }, { \"tobaccoUseTypeID\": 9, \"tobaccoUseType\": \"Filter Cigarette\", \"otherTobaccoUseType\": null, \"numberperDay\": \"2\", \"duration\": \"2\", \"durationUnit\": \"Days\" } ], \"alcoholList\": [ { \"alcoholTypeID\": 16, \"typeOfAlcohol\": \"Beer\", \"otherAlcoholType\": null, \"alcoholIntakeFrequency\": \"2-3 times a month\", \"avgAlcoholConsumption\": \"120-180 ml\", \"duration\": \"23\", \"durationUnit\": \"Days\" } ], \"allergicList\": [ { \"allergyType\": \"Drugs\", \"allergyName\": \"kjasdnajksdjk\", \"typeOfAllergicReactions\": [ { \"allergicReactionTypeID\": \"9\", \"name\": \"Abdominal Cramps\" }, { \"allergicReactionTypeID\": \"1\", \"name\": \"Anaphylaxis\" } ], \"otherTypeOfAllergicReaction\": null }, { \"allergyType\": \"Food\", \"allergyName\": \"jksnfklsndfklsdf\", \"typeOfAllergicReactions\": [ { \"allergicReactionTypeID\": \"9\", \"name\": \"Abdominal Cramps\" }, { \"allergicReactionTypeID\": \"1\", \"name\": \"Anaphylaxis\" }, { \"allergicReactionTypeID\": \"3\", \"name\": \"Angioedema\" } ], \"otherTypeOfAllergicReaction\": null } ], \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"immunizationHistory\": { \"beneficiaryRegID\":\"7921\", \"createdBy\":\"786\", \"immunizationList\":[], \"providerServiceMapID\":\"1316\" }, \"childVaccineDetails\": { \"childOptionalVaccineList\": [], \"beneficiaryRegID\": \"7403\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" } } }";
		doctorSaveObjPve = "{ \"findings\":{ \"beneficiaryRegID\":\"7476\", \"benVisitID\":\"757\", \"providerServiceMapID\":\"1320\", \"clinicalObservation\":\"lorem ipsum\", \"otherSymptoms\":\"lorem ipsum\", \"significantFindings\":\"lorem ipsum\", \"complaints\":[ { \"chiefComplaint\":\"Abdominal Bloating\", \"chiefComplaintID\":1, \"duration\":\"23\", \"unitOfDuration\":\"Hours\", \"description\":\"lorem \" }, { \"chiefComplaint\":\"Abdominal Distention\", \"chiefComplaintID\":5, \"duration\":\"23\", \"unitOfDuration\":\"Hours\", \"description\":\"lorem\" } ], \"createdBy\":\"888\" }, \"diagnosis\":{ \"provisionalDiagnosis\":\"lorem ipsum\", \"specialistAdvice\":\"lorem ipsum\", \"beneficiaryRegID\":\"7476\", \"benVisitID\":\"757\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\" }, \"investigation\":{ \"externalInvestigation\":\"lorem ipsum\", \"beneficiaryRegID\":\"7476\", \"benVisitID\":\"757\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\", \"laboratoryList\":[ { \"testID\":2, \"testName\":\"Blood Glucose Measurement\", \"isRadiologyImaging\":false }, { \"testID\":12, \"testName\":\"Blood grouping and Rh typing \", \"isRadiologyImaging\":false }, { \"testID\":17, \"testName\":\"BUN, Creatinine, Uric acid\", \"isRadiologyImaging\":false }, { \"testID\":29, \"testName\":\"CT Brain\", \"isRadiologyImaging\":true }, { \"testID\":30, \"testName\":\"CT Whole Spine \", \"isRadiologyImaging\":true }, { \"testID\":30, \"testName\":\"CT Whole Spine \", \"isRadiologyImaging\":true } ] }, \"prescription\":{ \"prescribedDrugs\":[ { \"specialInstruction\":\"After Food\", \"genericDrugName\":\"Ciprofloxacin Hcl Eye /Ear Drops IP 0.3% w/v in 5 ml\", \"dose\":\"Days \", \"frequency\":\"Single Dose\", \"drugForm\":\"Syrup\", \"drugDuration\":\"23\", \"qih\":7 }, { \"specialInstruction\":\"After Food\", \"genericDrugName\":\"Glimepride Tablets IP 1mg\", \"dose\":\"Days \", \"frequency\":\"Four Times in a Day (QID)\", \"drugForm\":\"Capsule\", \"drugDuration\":\"24\", \"qih\":10 } ], \"beneficiaryRegID\":\"7476\", \"benVisitID\":\"757\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\" }, \"refer\":{ \"referredToInstituteID\":null, \"refrredToAdditionalServiceList\":[ 3, 1 ], \"beneficiaryRegID\":\"7476\", \"benVisitID\":\"757\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\" } }";
		doctorSaveObjNve = "{ \"finngs\":{ \"beneficiaryRegID\":\"7476\", \"benVisitID\":\"757\", \"providerServiceMapID\":\"1320\", \"clinicalObservation\":\"lorem ipsum\", \"otherSymptoms\":\"lorem ipsum\", \"significantFindings\":\"lorem ipsum\", \"complaints\":[ { \"chiefComplaint\":\"Abdominal Bloating\", \"chiefComplaintID\":1, \"duration\":\"23\", \"unitOfDuration\":\"Hours\", \"description\":\"lorem \" }, { \"chiefComplaint\":\"Abdominal Distention\", \"chiefComplaintID\":5, \"duration\":\"23\", \"unitOfDuration\":\"Hours\", \"description\":\"lorem\" } ], \"createdBy\":\"888\" }, \"diagnosis\":{ \"provisionalDiagnosis\":\"lorem ipsum\", \"specialistAdvice\":\"lorem ipsum\", \"beneficiaryRegID\":\"7476\", \"benVisitID\":\"757\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\" }, \"investigation\":{ \"externalInvestigation\":\"lorem ipsum\", \"beneficiaryRegID\":\"7476\", \"benVisitID\":\"757\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\", \"laboratoryList\":[ { \"testID\":2, \"testName\":\"Blood Glucose Measurement\", \"isRadiologyImaging\":false }, { \"testID\":12, \"testName\":\"Blood grouping and Rh typing \", \"isRadiologyImaging\":false }, { \"testID\":17, \"testName\":\"BUN, Creatinine, Uric acid\", \"isRadiologyImaging\":false }, { \"testID\":29, \"testName\":\"CT Brain\", \"isRadiologyImaging\":true }, { \"testID\":30, \"testName\":\"CT Whole Spine \", \"isRadiologyImaging\":true }, { \"testID\":30, \"testName\":\"CT Whole Spine \", \"isRadiologyImaging\":true } ] }, \"prescription\":{ \"prescribedDrugs\":[ { \"specialInstruction\":\"After Food\", \"genericDrugName\":\"Ciprofloxacin Hcl Eye /Ear Drops IP 0.3% w/v in 5 ml\", \"dose\":\"Days \", \"frequency\":\"Single Dose\", \"drugForm\":\"Syrup\", \"drugDuration\":\"23\", \"qih\":7 }, { \"specialInstruction\":\"After Food\", \"genericDrugName\":\"Glimepride Tablets IP 1mg\", \"dose\":\"Days \", \"frequency\":\"Four Times in a Day (QID)\", \"drugForm\":\"Capsule\", \"drugDuration\":\"24\", \"qih\":10 } ], \"beneficiaryRegID\":\"7476\", \"benVisitID\":\"757\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\" }, \"refer\":{ \"referredToInstituteID\":null, \"refrredToAdditionalServiceList\":[ 3, 1 ], \"beneficiaryRegID\":\"7476\", \"benVisitID\":\"757\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\" } }";
		
		nurseSavejsnOBJPve = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(nurseSaveObjPve);
		nurseSavejsnOBJPve = jsnElmnt.getAsJsonObject();
		
		nurseSavejsnOBJNve = new JsonObject();
		jsnElmnt = jsnParser.parse(nurseSaveObjNve);
		nurseSavejsnOBJNve = jsnElmnt.getAsJsonObject();
		
		doctorSavejsnOBJPve = new JsonObject();
		jsnElmnt = jsnParser.parse(doctorSaveObjPve);
		doctorSavejsnOBJPve = jsnElmnt.getAsJsonObject();
		
		doctorSavejsnOBJNve = new JsonObject();
		jsnElmnt = jsnParser.parse(doctorSaveObjNve);
		doctorSavejsnOBJNve = jsnElmnt.getAsJsonObject();
		
		when(benAdherenceRepoMock.save(isA(BenAdherence.class))).thenReturn(spy(BenAdherence.class));
		
		ANCCareDetails ancCare = spy(ANCCareDetails.class);
		ancCare.setID(1L);
		when(ancCareRepoMock.save(isA(ANCCareDetails.class))).thenReturn(ancCare);
		
		List<ANCWomenVaccineDetail> ancWomenVaccines = new ArrayList<ANCWomenVaccineDetail>();
		ANCWomenVaccineDetail ancWomenVaccine = spy(ANCWomenVaccineDetail.class);
		ancWomenVaccine.setID(1L);
		ancWomenVaccines.add(ancWomenVaccine);
		//when(ancWomenVaccineRepoMock.save(Matchers.any())).thenReturn(ancWomenVaccines);
		
		SysObstetricExamination obsExmn = spy(SysObstetricExamination.class);
		obsExmn.setID(1L);
		when(sysObstetricExaminationRepoMock.save(isA(SysObstetricExamination.class))).thenReturn(obsExmn);
		
		ANCDiagnosis diagnosis = spy(ANCDiagnosis.class);
		diagnosis.setID(1L);
		when(ancDiagnosisRepoMock.save(isA(ANCDiagnosis.class))).thenReturn(diagnosis);
		
		ArrayList<Object[]> resList = new ArrayList<Object[]>();
		when(benAdherenceRepoMock.getBenAdherence(beneficiaryRegID, benVisitID)).thenReturn(resList);

		when(TestCommonServices.labTestOrderDetailRepoMock.getLabTestOrderDetails(beneficiaryRegID, benVisitID)).thenReturn(resList);
		
	}
	
	@Test
	public void saveANCNurseDataPveTest()
	{

		Long response = null;
		try
		{
			//response = ancServiceImpl.saveANCNurseData(nurseSavejsnOBJPve);

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo(1);
	}
	
	@Test
	public void saveANCNurseDataNveTest()
	{

		Long response = null;
		try
		{
//			response = ancServiceImpl.saveANCNurseData(nurseSavejsnOBJNve);
			response = null;

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo(null);
	}
	
	@Test
	public void saveANCDoctorDataPveTest()
	{

		Long response = null;
		try
		{
			//response = ancServiceImpl.saveANCDoctorData(doctorSavejsnOBJPve);

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo(1);
	}
	
	@Test
	public void saveANCDoctorDataNveTest()
	{

		Long response = null;
		try
		{
			//response = ancServiceImpl.saveANCDoctorData(doctorSavejsnOBJNve);

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo(null);
	}
	
	@Test
	public void getBenVisitDetailsFrmNurseANCPveTest(){
		String response = null;
		try
		{
			response = ancServiceImpl.getBenVisitDetailsFrmNurseANC(beneficiaryRegID,benVisitID);
			System.out.println("response "+response);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo("{BenAdherence=null, BenChiefComplaints=[], ANCNurseVisitDetail=null, LabTestOrders={}}");
	}
}
