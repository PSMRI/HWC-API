/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.generalOPD;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.controller.generalOPD.GeneralOPDCreateController;
import com.iemr.mmu.controller.generalOPD.GeneralOPDFetchController;
import com.iemr.mmu.controller.generalOPD.GeneralOPDUpdateController;
import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.service.generalOPD.GeneralOPDServiceImpl;

public class TestGeneralOPDController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@InjectMocks
	private static GeneralOPDCreateController createControllerMock = spy(GeneralOPDCreateController.class);
	private static GeneralOPDUpdateController updateControllerMock = spy(GeneralOPDUpdateController.class);
	private static GeneralOPDFetchController  fetchControllerMock = spy(GeneralOPDFetchController.class);
	private static GeneralOPDServiceImpl generalOPDServiceImplMock = mock(GeneralOPDServiceImpl.class);
	
	static String nurseObjPve=  "";
	static JsonObject nurseJsnOBJPve;
	static String nurseObjNve =  "";
	static JsonObject nurseJsnOBJNve;
	
	static String doctorObjPve=  "";
	static JsonObject doctorJsnOBJPve;
	static String doctorObjNve =  "";
	static JsonObject doctorJsnOBJNve;
	
	static String fetchPrevisHstryObjPve = "";
	static String fetchPrevisHstryObjNve = "";
	
	static String fetchObjPve = "";
	static String fetchObjNve = "";
	static String fetchObjNveRes = "";
	
	static String updateHstryObjPve = "";
	static JsonObject updateHstryJsnPve = null;
	
	static Long beneficiaryRegID = null;
	static Long benVisitID = null;
	
	@BeforeClass
	public static void initializeParams(){
		
		createControllerMock.setGeneralOPDServiceImpl(generalOPDServiceImplMock);
		updateControllerMock.setGeneralOPDServiceImpl(generalOPDServiceImplMock);
		fetchControllerMock.setGeneralOPDServiceImpl(generalOPDServiceImplMock);
		
		nurseObjPve = "{\"visitDetails\":{ \"visitDetails\":{ \"beneficiaryRegID\":\"7469\", \"providerServiceMapID\":\"1320\", \"visitNo\":null, \"visitReason\":\"FollowUp\", \"visitCategory\":\"General OPD\", \"pregnancyStatus\":null, \"rCHID\":null, \"healthFacilityType\":null, \"healthFacilityLocation\":null, \"reportFilePath\":null, \"createdBy\":\"891\" }, \"chiefComplaints\":[ { \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"chiefComplaint\":null, \"chiefComplaintID\":null, \"duration\":null, \"unitOfDuration\":null, \"description\":null, \"createdBy\":\"891\" } ] }, \"vitalDetails\":{ \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"weight_Kg\":null, \"height_cm\":null, \"waistCircumference_cm\":null, \"hipCircumference_cm\":null, \"bMI\":null, \"waistHipRatio\":null, \"temperature\":null, \"pulseRate\":null, \"systolicBP_1stReading\":null, \"diastolicBP_1stReading\":null, \"bloodGlucose_Fasting\":null, \"bloodGlucose_Random\":null, \"bloodGlucose_2hr_PP\":null, \"respiratoryRate\":null, \"createdBy\":\"891\" }, \"historyDetails\":{ \"pastHistory\":{ \"pastIllness\":[ { \"illnessTypeID\":null, \"illnessType\":null, \"otherIllnessType\":null, \"timePeriodAgo\":null, \"timePeriodUnit\":null } ], \"pastSurgery\":[ { \"surgeryID\":null, \"surgeryType\":null, \"otherSurgeryType\":null, \"timePeriodAgo\":null, \"timePeriodUnit\":null } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"comorbidConditions\":{ \"comorbidityConcurrentConditionsList\":[ { \"comorbidConditions\":null, \"otherComorbidCondition\":null, \"timePeriodAgo\":null, \"timePeriodUnit\":null, \"isForHistory\":null } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"medicationHistory\":{ \"medicationHistoryList\":[ { \"currentMedication\":null, \"timePeriodAgo\":null, \"timePeriodUnit\":null } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"femaleObstetricHistory\":{ \"totalNoOfPreg\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\", \"femaleObstetricHistoryList\":[ ] }, \"menstrualHistory\":{ \"menstrualCycleStatus\":null, \"menstrualCycleStatusID\":null, \"regularity\":null, \"cycleLength\":null, \"menstrualCyclelengthID\":null, \"menstrualFlowDurationID\":null, \"bloodFlowDuration\":null, \"menstrualProblemID\":null, \"problemName\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"familyHistory\":{ \"familyDiseaseList\":[ { \"diseaseTypeID\":null, \"diseaseType\":null, \"otherDiseaseType\":null, \"familyMembers\":null } ], \"isGeneticDisorder\":null, \"geneticDisorder\":null, \"isConsanguineousMarrige\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"personalHistory\":{ \"dietaryType\":null, \"physicalActivityType\":null, \"riskySexualPracticesStatus\":0, \"tobaccoUseStatus\":null, \"alcoholIntakeStatus\":null, \"allergyStatus\":null, \"tobaccoList\":[ ], \"alcoholList\":[ ], \"allergicList\":[ ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"childVaccineDetails\":{ \"childOptionalVaccineList\":[ { \"vaccineName\":null, \"otherVaccineName\":null, \"actualReceivingAge\":null, \"receivedFacilityName\":null } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"immunizationHistory\":{ \"immunizationList\":[ { \"defaultReceivingAge\":\"At Birth\", \"vaccines\":[ { \"vaccine\":\"BCG\", \"status\":false }, { \"vaccine\":\"HBV-0\", \"status\":false }, { \"vaccine\":\"OPV-0\", \"status\":false } ] }, { \"defaultReceivingAge\":\"6 Weeks\", \"vaccines\":[ { \"vaccine\":\"IPV-1\", \"status\":false }, { \"vaccine\":\"OPV\", \"status\":false }, { \"vaccine\":\"Pentavalent-1\", \"status\":false }, { \"vaccine\":\"Rota Vaccine-1\", \"status\":false } ] }, { \"defaultReceivingAge\":\"10 Weeks\", \"vaccines\":[ { \"vaccine\":\"IPV-2\", \"status\":false }, { \"vaccine\":\"OPV\", \"status\":false }, { \"vaccine\":\"Pentavalent-2\", \"status\":false }, { \"vaccine\":\"Rota Vaccine-2\", \"status\":false } ] }, { \"defaultReceivingAge\":\"14 Weeks\", \"vaccines\":[ { \"vaccine\":\"IPV-3 \", \"status\":false }, { \"vaccine\":\"OPV\", \"status\":false }, { \"vaccine\":\"Pentavalent-3\", \"status\":false }, { \"vaccine\":\"Rota Vaccine-3\", \"status\":false } ] }, { \"defaultReceivingAge\":\"9 Months\", \"vaccines\":[ { \"vaccine\":\"JE Vaccine\", \"status\":false }, { \"vaccine\":\"Measles Vaccine/MR\", \"status\":false }, { \"vaccine\":\"Vitamin A\", \"status\":false } ] }, { \"defaultReceivingAge\":\"16-24 Months\", \"vaccines\":[ { \"vaccine\":\"DPT-B 1\", \"status\":false }, { \"vaccine\":\"Measles/MR Vaccine\", \"status\":false }, { \"vaccine\":\"OPV\", \"status\":false } ] }, { \"defaultReceivingAge\":\"5 Years\", \"vaccines\":[ { \"vaccine\":\"\", \"status\":false } ] }, { \"defaultReceivingAge\":\"10 Years\", \"vaccines\":[ { \"vaccine\":\"TT\", \"status\":false } ] }, { \"defaultReceivingAge\":\"16 Years\", \"vaccines\":[ { \"vaccine\":\"TT\", \"status\":false } ] } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"developmentHistory\":{ \"grossMotorMilestones\":null, \"fineMotorMilestones\":null, \"socialMilestones\":null, \"languageMilestones\":null, \"developmentalProblems\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"feedingHistory\":{ \"typeOfFeed\":null, \"compFeedStartAge\":null, \"noOfCompFeedPerDay\":null, \"foodIntoleranceStatus\":0, \"typeofFoodIntolerance\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"perinatalHistroy\":{ \"deliveryPlaceID\":null, \"placeOfDelivery\":null, \"otherPlaceOfDelivery\":null, \"deliveryTypeID\":null, \"typeOfDelivery\":null, \"complicationAtBirthID\":null, \"complicationAtBirth\":null, \"otherComplicationAtBirth\":null, \"gestation\":null, \"birthWeight_kg\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" } }, \"examinationDetails\":{ \"generalExamination\":{ \"consciousness\":null, \"coherence\":null, \"cooperation\":null, \"comfortness\":null, \"builtAndAppearance\":null, \"gait\":null, \"dangerSigns\":null, \"typeOfDangerSigns\":null, \"pallor\":null, \"jaundice\":null, \"cyanosis\":null, \"clubbing\":null, \"lymphadenopathy\":null, \"lymphnodesInvolved\":null, \"typeOfLymphadenopathy\":null, \"edema\":null, \"extentOfEdema\":null, \"edemaType\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"headToToeExamination\":{ \"headtoToeExam\":null, \"head\":null, \"eyes\":null, \"ears\":null, \"nose\":null, \"oralCavity\":null, \"throat\":null, \"breastAndNipples\":null, \"trunk\":null, \"upperLimbs\":null, \"lowerLimbs\":null, \"skin\":null, \"hair\":null, \"nails\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"gastroIntestinalExamination\":{ \"inspection\":null, \"palpation_AbdomenTexture\":null, \"palpation_Liver\":null, \"palpation_Spleen\":null, \"palpation_Tenderness\":null, \"palpation_LocationOfTenderness\":null, \"percussion\":null, \"auscultation\":null, \"analRegion\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"cardioVascularExamination\":{ \"jugularVenousPulse_JVP\":null, \"apexbeatLocation\":null, \"apexbeatType\":null, \"firstHeartSound_S1\":null, \"secondHeartSound_S2\":null, \"additionalHeartSounds\":null, \"murmurs\":null, \"pericardialRub\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"respiratorySystemExamination\":{ \"trachea\":null, \"inspection\":null, \"signsOfRespiratoryDistress\":null, \"palpation\":null, \"auscultation_Stridor\":null, \"auscultation_BreathSounds\":null, \"auscultation_Crepitations\":null, \"auscultation_Wheezing\":null, \"auscultation_PleuralRub\":null, \"auscultation_ConductedSounds\":null, \"percussion\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"centralNervousSystemExamination\":{ \"handedness\":null, \"cranialNervesExamination\":null, \"motorSystem\":null, \"sensorySystem\":null, \"autonomicSystem\":null, \"cerebellarSigns\":null, \"signsOfMeningealIrritation\":null, \"skull\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"musculoskeletalSystemExamination\":{ \"joint_TypeOfJoint\":null, \"joint_Laterality\":null, \"joint_Abnormality\":null, \"upperLimb_Laterality\":null, \"upperLimb_Abnormality\":null, \"lowerLimb_Laterality\":null, \"lowerLimb_Abnormality\":null, \"chestWall\":null, \"spine\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" }, \"genitoUrinarySystemExamination\":{ \"renalAngle\":null, \"suprapubicRegion\":null, \"externalGenitalia\":null, \"beneficiaryRegID\":\"7469\", \"benVisitID\":null, \"providerServiceMapID\":\"1320\", \"createdBy\":\"891\" } } }";
		nurseObjNve = "{}";
		
		updateHstryObjPve = "{ \"pastHistory\":{ \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\", \"pastIllness\":[ { \"illnessTypeID\":\"6\", \"illnessType\":\"Asthma\", \"otherIllnessType\":null, \"timePeriodAgo\":\"21\", \"timePeriodUnit\":\"Days\" } ], \"pastSurgery\":[ { \"surgeryID\":\"3\", \"surgeryType\":\"Appendicectomy\", \"otherSurgeryType\":null, \"timePeriodAgo\":\"4\", \"timePeriodUnit\":\"Days\" } ] }, \"comorbidConditions\":{ \"comorbidityConcurrentConditionsList\":[ { \"otherComorbidCondition\":null, \"timePeriodAgo\":null, \"timePeriodUnit\":null, \"isForHistory\":null } ], \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"medicationHistory\":{ \"medicationHistoryList\":[ { \"currentMedication\":null, \"timePeriodAgo\":0, \"timePeriodUnit\":\"\" } ], \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"femaleObstetricHistory\":{ \"totalNoOfPreg\":null, \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\", \"femaleObstetricHistoryList\":[ ] }, \"menstrualHistory\":{ \"menstrualCycleStatusID\":null, \"regularity\":null, \"menstrualCyclelengthID\":null, \"menstrualFlowDurationID\":null, \"menstrualProblemID\":null, \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"familyHistory\":{ \"familyDiseaseList\":[ { \"diseaseTypeID\":null, \"diseaseType\":null, \"otherDiseaseType\":null, \"familyMembers\":[ ] } ], \"isGeneticDisorder\":null, \"geneticDisorder\":null, \"isConsanguineousMarrige\":null, \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"personalHistory\":{ \"dietaryType\":null, \"physicalActivityType\":null, \"riskySexualPracticesStatus\":0, \"tobaccoUseStatus\":null, \"alcoholIntakeStatus\":null, \"allergyStatus\":null, \"tobaccoList\":[ { \"tobaccoUseTypeID\":null, \"tobaccoUseType\":null, \"otherTobaccoUseType\":null, \"numberperDay\":null, \"duration\":null, \"durationUnit\":null } ], \"alcoholList\":[ { \"alcoholTypeID\":null, \"typeOfAlcohol\":null, \"otherAlcoholType\":null, \"alcoholIntakeFrequency\":null, \"avgAlcoholConsumption\":null, \"duration\":null, \"durationUnit\":null } ], \"allergicList\":[ { \"allergyType\":null, \"allergyName\":null, \"typeOfAllergicReactions\":[ ], \"otherAllergicReaction\":null, \"enableOtherAllergy\":false } ], \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"childVaccineDetails\":{ \"childOptionalVaccineList\":[ ], \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"immunizationHistory\":{ \"immunizationList\":[ ], \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"developmentHistory\":{ \"grossMotorMilestones\":null, \"fineMotorMilestones\":null, \"socialMilestones\":null, \"languageMilestones\":null, \"developmentProblems\":null, \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"feedingHistory\":{ \"typeOfFeed\":null, \"compFeedStartAge\":null, \"noOfCompFeedPerDay\":null, \"foodIntoleranceStatus\":null, \"typeofFoodIntolerance\":null, \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" }, \"perinatalHistroy\":{ \"deliveryPlaceID\":null, \"placeOfDelivery\":null, \"otherPlaceOfDelivery\":null, \"deliveryTypeID\":null, \"typeOfDelivery\":null, \"complicationAtBirthID\":null, \"complicationAtBirth\":null, \"otherComplicationAtBirth\":null, \"gestation\":null, \"birthWeight_kg\":null, \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"891\", \"providerServiceMapID\":\"1320\", \"modifiedBy\":\"888\", \"createdBy\":\"888\" } }";
		doctorObjPve = "{ \"findings\":{ \"beneficiaryRegID\":\"7469\", \"benVisitID\":\"846\", \"providerServiceMapID\":\"1320\", \"clinicalObservation\":\"Lorem Ipsum is simply dummy text\", \"otherSymptoms\":\"Lorem Ipsum is simply dummy text\", \"significantFindings\":\"Lorem Ipsum \", \"complaints\":[ { \"chiefComplaint\":\"Abdominal Bloating\", \"chiefComplaintID\":1, \"duration\":\"23\", \"unitOfDuration\":\"Hours\", \"description\":\"Lorem Ipsum is simply dummy text\" }, { \"chiefComplaint\":\"Abdominal Distention\", \"chiefComplaintID\":5, \"duration\":\"24\", \"unitOfDuration\":\"Hours\", \"description\":\"Lorem Ipsum is simply dummy text\" } ], \"createdBy\":\"888\" }, \"diagnosis\":{ \"provisionalDiagnosis\":\"Lorem Ipsum is simply dummy text\", \"specialistAdvice\":\"Lorem Ipsum is simply dummy text\", \"beneficiaryRegID\":\"7469\", \"benVisitID\":\"846\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\" }, \"investigation\":{ \"externalInvestigation\":\"Lorem Ipsum is simply dummy text\", \"beneficiaryRegID\":\"7469\", \"benVisitID\":\"846\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\", \"laboratoryList\":[ { \"testID\":2, \"testName\":\"Blood Glucose Measurement\", \"isRadiologyImaging\":false }, { \"testID\":12, \"testName\":\"Blood grouping and Rh typing \", \"isRadiologyImaging\":false }, { \"testID\":17, \"testName\":\"BUN, Creatinine, Uric acid\", \"isRadiologyImaging\":false }, { \"testID\":29, \"testName\":\"CT Brain\", \"isRadiologyImaging\":true }, { \"testID\":30, \"testName\":\"CT Whole Spine \", \"isRadiologyImaging\":true }, { \"testID\":33, \"testName\":\"Flouroscopy- Urethrogram \", \"isRadiologyImaging\":true } ] }, \"prescription\":{ \"prescribedDrugs\":[ { \"specialInstruction\":\"After Food\", \"genericDrugName\":\"Glimepride Tablets IP 1mg\", \"dose\":\"Days \", \"frequency\":\"Once Daily(OD)\", \"drugForm\":\"Capsule\", \"drugDuration\":\"2\", \"qih\":10 }, { \"specialInstruction\":\"After Food\", \"genericDrugName\":\"Metformin Tablets IP 500 mg\", \"dose\":\"Days \", \"frequency\":\"Once Daily(OD)\", \"drugForm\":\"Capsule\", \"drugDuration\":\"2\", \"qih\":3 } ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":\"846\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\" }, \"refer\":{ \"referredToInstituteID\":null, \"refrredToAdditionalServiceList\":[ 3, 1, 5 ], \"beneficiaryRegID\":\"7469\", \"benVisitID\":\"846\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\" } }";
		doctorObjNve = "{}";
		
		fetchPrevisHstryObjPve = "{\"benRegID\":\"7469\"}";
		fetchPrevisHstryObjNve = "{}";
		
		fetchObjPve = "{\"benRegID\":\"7469\",\"benVisitID\":\"131\"}";
		fetchObjNve = "{}";
		fetchObjNveRes = "{\"statusCode\":5000,\"errorMessage\":\"Invalid Request Data !!!\",\"status\":\"Invalid Request Data !!!\"}";
		
		nurseJsnOBJPve = new JsonObject();
		JsonParser jsnParser = new JsonParser();
		JsonElement jsnElmnt = jsnParser.parse(nurseObjPve);
		nurseJsnOBJPve = jsnElmnt.getAsJsonObject();
		
		doctorJsnOBJPve = new JsonObject();
		JsonElement jsnElmnt2 = jsnParser.parse(doctorObjPve);
		doctorJsnOBJPve = jsnElmnt2.getAsJsonObject();
		
		updateHstryJsnPve = new JsonObject();
		jsnElmnt = jsnParser.parse(updateHstryObjPve);
		updateHstryJsnPve = jsnElmnt.getAsJsonObject();
		
		beneficiaryRegID = 7469L;
		benVisitID = 131L;
		
		try {
			// Save API mocks
			//when(generalOPDServiceImplMock.saveNurseData(nurseJsnOBJPve)).thenReturn(new Long(1L));
			
			when(generalOPDServiceImplMock.saveDoctorData(doctorJsnOBJPve,"")).thenReturn(new Long(1L));
			
			//Fetch API mocks
//			when(generalOPDServiceImplMock.getPastHistoryData(beneficiaryRegID)).thenReturn("");
//			when(generalOPDServiceImplMock.getPersonalTobaccoHistoryData(beneficiaryRegID)).thenReturn("");
//			when(generalOPDServiceImplMock.getPersonalAlcoholHistoryData(beneficiaryRegID)).thenReturn("");
//			when(generalOPDServiceImplMock.getPersonalAllergyHistoryData(beneficiaryRegID)).thenReturn("");
//			when(generalOPDServiceImplMock.getMedicationHistoryData(beneficiaryRegID)).thenReturn("");
//			when(generalOPDServiceImplMock.getFamilyHistoryData(beneficiaryRegID)).thenReturn("");
//			when(generalOPDServiceImplMock.getMenstrualHistoryData(beneficiaryRegID)).thenReturn("");
//			when(generalOPDServiceImplMock.getObstetricHistoryData(beneficiaryRegID)).thenReturn("");
//			when(generalOPDServiceImplMock.getComorbidHistoryData(beneficiaryRegID)).thenReturn("");
//			when(generalOPDServiceImplMock.getChildVaccineHistoryData(beneficiaryRegID)).thenReturn("");
//			when(generalOPDServiceImplMock.getImmunizationHistoryData(beneficiaryRegID)).thenReturn("");
//			when(generalOPDServiceImplMock.getBenPerinatalHistoryData(beneficiaryRegID)).thenReturn("");
//			when(generalOPDServiceImplMock.getBenFeedingHistoryData(beneficiaryRegID)).thenReturn("");
//			when(generalOPDServiceImplMock.getBenDevelopmentHistoryData(beneficiaryRegID)).thenReturn("");
			
			when(generalOPDServiceImplMock.getBenVisitDetailsFrmNurseGOPD(beneficiaryRegID, benVisitID)).thenReturn("");
			when(generalOPDServiceImplMock.getBenHistoryDetails(beneficiaryRegID, benVisitID)).thenReturn("");
			when(generalOPDServiceImplMock.getBeneficiaryVitalDetails(beneficiaryRegID, benVisitID)).thenReturn("");
			when(generalOPDServiceImplMock.getExaminationDetailsData(beneficiaryRegID, benVisitID)).thenReturn("");
			
			// Update API mocks
			when(generalOPDServiceImplMock.updateBenHistoryDetails(nurseJsnOBJPve)).thenReturn(1);
			
			when(generalOPDServiceImplMock.updateBenVitalDetails(nurseJsnOBJPve)).thenReturn(1);
			
			when(generalOPDServiceImplMock.updateBenExaminationDetails(nurseJsnOBJPve)).thenReturn(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Before
	public void initialize() {
		
	}
	
	@Test
	public void  saveGOPDNurseDataPveTest(){
		
//		String response = createControllerMock.saveBenGenOPDNurseData(nurseObjPve);
		
		String response = null;
		
		assertTrue("",
				response.equals("{\"data\":{\"response\":\"General OPD Nurse Entered Details stored successfully.\"},\"statusCode\":200,"
						+ "\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}
	
	
	@Test
	public void  saveGOPDNurseDataNveTest(){
		
//		String response = createControllerMock.saveBenGenOPDNurseData(nurseObjNve);
		String response  = null;
		
		assertTrue("",
				response.equals("{\"data\":{\"response\":\"Failed to store General OPD Details.\"},\"statusCode\":200,"
						+ "\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}
	
	
	@Test
	public void  saveGOPDDoctorDataPveTest(){
		
		String response = createControllerMock.saveBenGenOPDDoctorData(doctorObjPve, "");
		
		assertTrue("",
				response.equals("{\"data\":{\"response\":\"General OPD doctor Entered Details stored successfully.\"},\"statusCode\":200,"
						+ "\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}
	
	@Test
	public void  saveGOPDDoctorDataNveTest(){
		
		String response = createControllerMock.saveBenGenOPDDoctorData(doctorObjNve, "");
		
		assertTrue("",
				response.equals("{\"data\":{\"response\":\"Failed to store General OPD doctor Details.\"},\"statusCode\":200,"
						+ "\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}
	
	
//	@Test
//	public void getBenPastHistoryPveTest(){
//		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
//		
//		String response = fetchControllerMock.getBenPastHistory(fetchPrevisHstryObjPve);
//		
//		assertTrue("",
//				response.equals(expectedRes));
//	}
//	
//	@Test
//	public void getBenPastHistoryNveTest(){
//		String expectedRes = "{\"statusCode\":5000,\"errorMessage\":\"Invalid Request Data !!!\",\"status\":\"Invalid Request Data !!!\"}";
//		String response = fetchControllerMock.getBenPastHistory(fetchPrevisHstryObjNve);
//		
//		assertTrue("",
//				response.equals(expectedRes));
//	}
//	
//	@Test
//	public void getBenTobaccoHistoryPveTest(){
//		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
//		
//		String response = fetchControllerMock.getBenTobaccoHistory(fetchPrevisHstryObjPve);
//		
//		assertTrue("",
//				response.equals(expectedRes));
//	}
//	
//	@Test
//	public void getBenAlcoholHistoryPveTest(){
//		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
//		
//		String response = fetchControllerMock.getBenAlcoholHistory(fetchPrevisHstryObjPve);
//		
//		assertTrue("",
//				response.equals(expectedRes));
//	}
//	
//	@Test
//	public void getBenAllergyHistoryPveTest(){
//		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
//		
//		String response = fetchControllerMock.getBenAllergyHistory(fetchPrevisHstryObjPve);
//		
//		assertTrue("",
//				response.equals(expectedRes));
//	}
//	
//	@Test
//	public void getBenMedicationHistoryPveTest(){
//		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
//		
//		String response = fetchControllerMock.getBenMedicationHistory(fetchPrevisHstryObjPve);
//		
//		assertTrue("",
//				response.equals(expectedRes));
//	}
//	
//	@Test
//	public void getBenFamilyHistoryPveTest(){
//		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
//		
//		String response = fetchControllerMock.getBenFamilyHistory(fetchPrevisHstryObjPve);
//		
//		assertTrue("",
//				response.equals(expectedRes));
//	}
//	
//	@Test
//	public void getBenMenstrualHistoryPveTest(){
//		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
//		
//		String response = fetchControllerMock.getBenMenstrualHistory(fetchPrevisHstryObjPve);
//		
//		assertTrue("",
//				response.equals(expectedRes));
//	}
//	
//	@Test
//	public void getBenPastObstetricHistoryPveTest(){
//		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
//		
//		String response = fetchControllerMock.getBenPastObstetricHistory(fetchPrevisHstryObjPve);
//		
//		assertTrue("",
//				response.equals(expectedRes));
//	}
//	
//	@Test
//	public void getBenComorbidityConditionHistoryPveTest(){
//		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
//		
//		String response = fetchControllerMock.getBenComorbidityConditionHistory(fetchPrevisHstryObjPve);
//		
//		assertTrue("",
//				response.equals(expectedRes));
//	}
//	
//	@Test
//	public void getBenOptionalVaccineHistoryPveTest(){
//		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
//		
//		String response = fetchControllerMock.getBenOptionalVaccineHistory(fetchPrevisHstryObjPve);
//		
//		assertTrue("",
//				response.equals(expectedRes));
//	}
//	
//	@Test
//	public void getBenImmunizationHistoryPveTest(){
//		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
//		
//		String response = fetchControllerMock.getBenImmunizationHistory(fetchPrevisHstryObjPve);
//		
//		assertTrue("",
//				response.equals(expectedRes));
//	}
//	
//	@Test
//	public void getBenPerinatalHistoryPveTest(){
//		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
//		
//		String response = fetchControllerMock.getBenPerinatalHistory(fetchPrevisHstryObjPve);
//		
//		assertTrue("",
//				response.equals(expectedRes));
//	}
//	
//	@Test
//	public void getBenFeedingHistoryPveTest(){
//		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
//		
//		String response = fetchControllerMock.getBenFeedingHistory(fetchPrevisHstryObjPve);
//		
//		assertTrue("",
//				response.equals(expectedRes));
//	}
//	
//	@Test
//	public void getBenDevelopmentHistoryPveTest(){
//		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
//		
//		String response = fetchControllerMock.getBenDevelopmentHistory(fetchPrevisHstryObjPve);
//		
//		assertTrue("",
//				response.equals(expectedRes));
//	}
//	
	@Test
	public void getBenVisitDetailsFrmNurseGOPDPveTest(){
		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
		
		String response = fetchControllerMock.getBenVisitDetailsFrmNurseGOPD(fetchObjPve);
		
		System.out.println("getBenVisitDetailsFrmNurseGOPDPveTest "+response);
		assertTrue("",
				response.equals(expectedRes));
	}
	
	@Test
	public void getBenHistoryDetailsPveTest(){
		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
		
		String response = fetchControllerMock.getBenHistoryDetails(fetchObjPve);
		
		System.out.println("getBenHistoryDetailsPveTest "+response);
		assertTrue("",
				response.equals(expectedRes));
	}
	
	@Test
	public void getBenVitalDetailsFrmNursePveTest(){
		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
		
		String response = fetchControllerMock.getBenVitalDetailsFrmNurse(fetchObjPve);
		
		System.out.println("getBenVitalDetailsFrmNursePveTest "+response);
		assertTrue("",
				response.equals(expectedRes));
	}
	
	@Test
	public void getBenExaminationDetailsPveTest(){
		String expectedRes = "{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
		
		String response = fetchControllerMock.getBenExaminationDetails(fetchObjPve);
		
		System.out.println("getBenExaminationDetailsPveTest "+response);
		assertTrue("",
				response.equals(expectedRes));
	}
	
	@Test
	public void  updateHistoryNursePveTest(){
		String expectedRes = "{\"data\":{\"result\":1},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
		String response = updateControllerMock.updateHistoryNurse(nurseObjPve);
		
		assertTrue("",
				response.equals(expectedRes));
	}
	
	@Test
	public void  updateHistoryNurseNveTest(){
		String expectedRes = "{\"statusCode\":500,\"errorMessage\":\"Failed to update General OPD History Nurse Data\","
				+ "\"status\":\"Failed to update General OPD History Nurse Data\"}";
		String response = updateControllerMock.updateHistoryNurse(nurseObjNve);
		
		assertTrue("",
				response.equals(expectedRes));
	}
	
	@Test
	public void  updateVitalNursePveTest(){
		String expectedRes = "{\"data\":{\"result\":1},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
		String response = updateControllerMock.updateVitalNurse(nurseObjPve);
		
		assertTrue("",
				response.equals(expectedRes));
	}
	
	@Test
	public void  updateExaminationNursePveTest(){
		String expectedRes = "{\"data\":{\"result\":1},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}";
		String response = updateControllerMock.updateGeneralOPDExaminationNurse(nurseObjPve);
		
		assertTrue("",
				response.equals(expectedRes));
	}
	
	
	
	
}
