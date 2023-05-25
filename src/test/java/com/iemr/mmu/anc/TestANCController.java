package com.iemr.mmu.anc;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.google.gson.JsonObject;
import com.iemr.mmu.controller.anc.ANCCreateController;
import com.iemr.mmu.controller.anc.ANCFetchController;
import com.iemr.mmu.service.anc.ANCServiceImpl;

public class TestANCController
{
	
	@InjectMocks
	private static ANCCreateController createControllerSpy = spy(ANCCreateController.class);
	private static ANCFetchController fetchControllerSpy = spy(ANCFetchController.class);
	private static ANCServiceImpl ancServiceImplMock = mock(ANCServiceImpl.class);
	
	private static String nurseSaveObjPve = ""; 
	private static String fetchObjPve;
	
	static Long beneficiaryRegID = 7506L;
	static Long benVisitID = 131L;
	@BeforeClass
	public static void initializeParams(){
		createControllerSpy.setAncServiceImpl(ancServiceImplMock);
		fetchControllerSpy.setAncServiceImpl(ancServiceImplMock);
		
		nurseSaveObjPve = "{ \"visitDetails\": { \"visitDetails\": { \"beneficiaryRegID\": \"7403\", \"providerServiceMapID\": \"1316\", \"visitNo\": null, \"visitReason\": \"FollowUp\", \"visitCategory\": \"ANC\", \"pregnancyStatus\": \"Yes\", \"rCHID\": \"444\", \"healthFacilityType\": null, \"healthFacilityLocation\": null, \"reportFilePath\": null, \"createdBy\": \"786\" }, \"chiefComplaints\": [ { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"chiefComplaint\": \"Abdominal Bloating\", \"chiefComplaintID\": 1, \"duration\": \"23\", \"unitOfDuration\": \"Hours\", \"description\": \"dffjnsfklsndfklsdfl\", \"createdBy\": \"786\" }, { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"chiefComplaint\": \"Abdominal Mass \", \"chiefComplaintID\": 2, \"duration\": \"24\", \"unitOfDuration\": \"Hours\", \"description\": \"sdlkfnskdfnskdfnskdlf\", \"createdBy\": \"786\" } ], \"adherence\": { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\", \"toDrugs\": false, \"drugReason\": \"sdnfklsdnfklsdnf\", \"toReferral\": false, \"referralReason\": \"klsdmfklsdmfklsdfmskldfm\", \"progress\": \"Improved\" }, \"investigation\": { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\", \"laboratoryList\": [ { \"testID\": 17, \"testName\": \"BUN, Creatinine, Uric acid\", \"isRadiologyImaging\": false }, { \"testID\": 14, \"testName\": \"Complete blood count\", \"isRadiologyImaging\": false }, { \"testID\": 15, \"testName\": \"Haemogram\", \"isRadiologyImaging\": false } ] } }, \"ancDetails\": { \"ancObstetricDetails\": { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\", \"primiGravida\": false, \"lmpDate\": \"2017-08-20T05:07:37.000Z\", \"gestationalAgeOrPeriodofAmenorrhea_POA\": 22, \"expDelDt\": \"2018-05-27T05:07:37.000Z\", \"duration\": \"4\", \"trimesterNumber\": 2, \"gravida_G\": 4, \"termDeliveries_T\": \"1\", \"pretermDeliveries_P\": \"1\", \"abortions_A\": \"1\", \"livebirths_L\": \"1\", \"bloodGroup\": \"AB-\" }, \"ancImmunization\": { \"tT_1Status\": \"Received\", \"dateReceivedForTT_1\": \"2017-11-10T05:10:57.000Z\", \"facilityNameOfTT_1\": \"Govt Facility\", \"tT_2Status\": \"Received\", \"dateReceivedForTT_2\": \"2017-12-10T05:10:57.000Z\", \"facilityNameOfTT_2\": \"Govt Facility\", \"tT_3Status\": \"Received\", \"dateReceivedForTT_3\": \"2018-01-10T05:10:57.000Z\", \"facilityNameOfTT_3\": \"Govt Facility\", \"beneficiaryRegID\": \"7403\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" } }, \"vitalDetails\": { \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"weight_Kg\": \"32\", \"height_cm\": \"166\", \"waistCircumference_cm\": null, \"hipCircumference_cm\": null, \"bMI\": 11.6, \"waistHipRatio\": null, \"temperature\": \"98\", \"pulseRate\": \"72\", \"systolicBP_1stReading\": \"120\", \"diastolicBP_1stReading\": \"90\", \"bloodGlucose_Fasting\": null, \"bloodGlucose_Random\": null, \"bloodGlucose_2hr_PP\": null, \"respiratoryRate\": \"34\", \"createdBy\": \"786\" }, \"examinationDetails\": { \"generalExamination\": { \"consciousness\": \"Semiconscious\", \"coherence\": \"Coherent\", \"cooperation\": \"Cooperative\", \"comfortness\": \"Comfortable\", \"builtAndAppearance\": \"Thin Built\", \"gait\": \"Normal\", \"dangerSigns\": \"Yes\", \"typeOfDangerSigns\": [ 1 ], \"pallor\": \"Present\", \"jaundice\": \"Present\", \"cyanosis\": \"Present\", \"clubbing\": \"Present\", \"lymphadenopathy\": \"Present\", \"lymphnodesInvolved\": \"Cervical LN\", \"typeOfLymphadenopathy\": \"Matting\", \"edema\": \"Present\", \"extentOfEdema\": \"Facial Puffiness\", \"edemaType\": \"Pitting\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"headToToeExamination\": { \"headtoToeExam\": \"Abnormal\", \"head\": \"lorem\", \"eyes\": \"lorem\", \"ears\": \"lorem\", \"nose\": \"lorem\", \"oralCavity\": \"lorem\", \"throat\": \"lorem\", \"breastAndNipples\": \"lorem\", \"trunk\": \"lorem\", \"upperLimbs\": \"lorem\", \"lowerLimbs\": \"lorem\", \"skin\": \"lorem\", \"hair\": \"lorem\", \"nails\": \"lorem\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"gastroIntestinalExamination\": { \"inspection\": null, \"palpation_AbdomenTexture\": null, \"palpation_Liver\": null, \"palpation_Spleen\": null, \"palpation_Tenderness\": null, \"palpation_LocationOfTenderness\": null, \"percussion\": null, \"auscultation\": null, \"analRegion\": null, \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"cardioVascularExamination\": { \"jugularVenousPulse_JVP\": \"Normal\", \"apexbeatLocation\": \"lorem\", \"apexbeatType\": \"lorem\", \"firstHeartSound_S1\": \"lorem\", \"secondHeartSound_S2\": \"lorem\", \"additionalHeartSounds\": \"lorem\", \"murmurs\": \"Present\", \"pericardialRub\": \"Present\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"respiratorySystemExamination\": { \"trachea\": null, \"inspection\": \"lorem\", \"signsOfRespiratoryDistress\": \"Present\", \"palpation\": \"lorem\", \"auscultation_Stridor\": \"Present\", \"auscultation_BreathSounds\": \"Normal\", \"auscultation_Crepitations\": \"Present\", \"auscultation_Wheezing\": \"Present\", \"auscultation_PleuralRub\": \"Present\", \"auscultation_ConductedSounds\": \"Present\", \"percussion\": \"Dull\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"centralNervousSystemExamination\": { \"handedness\": \"Right Handed\", \"cranialNervesExamination\": \"lnsakldasn\", \"motorSystem\": \"klsdmfklsmfkl\", \"sensorySystem\": \"klsdmfkls\", \"autonomicSystem\": \"klndklsdf\", \"cerebellarSigns\": \"klsdfklsdfl\", \"signsOfMeningealIrritation\": \"Present\", \"skull\": \"sdjknfsjkldfnskld\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"musculoskeletalSystemExamination\": { \"joint_TypeOfJoint\": \"Ankle\", \"joint_Laterality\": \"Left\", \"joint_Abnormality\": \"Swelling\", \"upperLimb_Laterality\": \"Left\", \"upperLimb_Abnormality\": \"Swelling\", \"lowerLimb_Laterality\": \"Left\", \"lowerLimb_Abnormality\": \"Swelling\", \"chestWall\": \"sdkjfnasklfnslkdfn\", \"spine\": \"lskdnfklsdnfklsdnfl\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"genitoUrinarySystemExamination\": { \"renalAngle\": \"sjkdnfjksdnfklsnd\", \"suprapubicRegion\": \"lknsdklfnsdklf\", \"externalGenitalia\": \"lnsdklfnsdklfnsdjkl\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"obstetricExamination\": { \"fundalHeight\": \"Not Palpable\", \"fHAndPOA_Status\": \"FH=POA\", \"fHAndPOA_Interpretation\": \"Corresponding\", \"fetalMovements\": \"Present\", \"fetalHeartSounds\": \"Audible\", \"fetalHeartRate_BeatsPerMinute\": \"<120\", \"fetalPositionOrLie\": \"Longitudinal\", \"fetalPresentation\": \"Shoulder\", \"abdominalScars\": \"Absent\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": 676, \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" } }, \"historyDetails\": { \"pastHistory\": { \"pastIllness\": [ { \"illnessTypeID\": \"6\", \"illnessType\": \"Asthma\", \"otherIllnessType\": null, \"timePeriodAgo\": \"23\", \"timePeriodUnit\": \"Days\" }, { \"illnessTypeID\": \"22\", \"illnessType\": \"Other\", \"otherIllnessType\": \"skjdnaksjdnasjkdn\", \"timePeriodAgo\": \"24\", \"timePeriodUnit\": \"Days\" } ], \"pastSurgery\": [ { \"surgeryID\": \"3\", \"surgeryType\": \"Appendicectomy\", \"otherSurgeryType\": null, \"timePeriodAgo\": \"22\", \"timePeriodUnit\": \"Days\" }, { \"surgeryID\": \"6\", \"surgeryType\": \"Other\", \"otherSurgeryType\": \"jndajksdnakldnakl\", \"timePeriodAgo\": \"12\", \"timePeriodUnit\": \"Days\" } ], \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"comorbidConditions\": { \"comorbidityConcurrentConditionsList\": [ { \"otherComorbidCondition\": null, \"timePeriodAgo\": \"21\", \"timePeriodUnit\": \"Days\", \"isForHistory\": true, \"comorbidCondition\": \"Asthma\", \"comorbidConditionID\": \"6\" }, { \"otherComorbidCondition\": \"askdjnajkdnajks\", \"timePeriodAgo\": \"23\", \"timePeriodUnit\": \"Days\", \"isForHistory\": true, \"comorbidCondition\": \"Other\", \"comorbidConditionID\": \"8\" } ], \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"medicationHistory\": { \"medicationHistoryList\": [ { \"currentMedication\": \"sjkdnjkasndakjsn\", \"timePeriodAgo\": \"22\", \"timePeriodUnit\": \"Days\" }, { \"currentMedication\": \"sjkdnaklsdakldjakl\", \"timePeriodAgo\": \"24\", \"timePeriodUnit\": \"Days\" } ], \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"femaleObstetricHistory\": { \"totalNoOfPreg\": \"3\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\", \"femaleObstetricHistoryList\": [ { \"pregOrder\": 1, \"pregComplicationID\": 12, \"pregComplicationType\": \"Other\", \"otherPregComplication\": \"adhkajsdkasd\", \"pregDurationID\": 1, \"durationType\": \"Preterm\", \"deliveryTypeID\": 2, \"deliveryType\": \"Assisted Delivery\", \"deliveryPlaceID\": 9, \"deliveryPlace\": \"Other\", \"otherDeliveryPlace\": \"skldfskldfmsklf\", \"deliveryComplicationID\": 19, \"deliveryComplicationType\": \"Other\", \"otherDeliveryComplication\": \"sldfnasklfmsklfskl\", \"postpartumComplicationID\": 24, \"postpartumComplicationType\": \"Other\", \"otherPostpartumCompType\": \"slkfnskldfnskldfmskld\", \"pregOutcomeID\": 1, \"pregOutcome\": \"Live Birth\", \"postNatalComplicationID\": 29, \"postNatalComplication\": \"Other\", \"otherPostNatalComplication\": \"slfknskldfnskldfnskl\", \"newBornComplicationID\": 34, \"newBornComplication\": \"Other\", \"otherNewBornComplication\": \"sklkfnsklfnsklfsl\", \"congenitalAnomalies\": \"skldnfsklfnskldf\" } ] }, \"menstrualHistory\": { \"menstrualCycleStatus\": \"Active\", \"menstrualCycleStatusID\": \"3\", \"regularity\": \"Regular\", \"cycleLength\": \"24-28 days\", \"menstrualCyclelengthID\": \"2\", \"menstrualFlowDurationID\": \"6\", \"bloodFlowDuration\": \"3 days\", \"menstrualProblemID\": \"2\", \"problemName\": \"Irregular Menstrual Bleeding\", \"lMPDate\": \"2017-09-09T18:30:00.000Z\", \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"familyHistory\": { \"familyDiseaseList\": [ { \"diseaseTypeID\": \"6\", \"diseaseType\": \"Asthma\", \"otherDiseaseType\": null, \"familyMembers\": [ \"Aunt\", \"Brother\" ] }, { \"diseaseTypeID\": \"8\", \"diseaseType\": \"Other\", \"otherDiseaseType\": \"sjndajksdnakldnakl\", \"familyMembers\": [ \"Aunt\", \"Brother\" ] } ], \"isGeneticDisorder\": true, \"geneticDisorder\": \"sjknasklndklasdn\", \"isConsanguineousMarrige\": false, \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"personalHistory\": { \"dietaryType\": \"Vegetarian\", \"physicalActivityType\": \"Sedentary\", \"riskySexualPracticesStatus\": 1, \"tobaccoUseStatus\": \"Yes\", \"alcoholIntakeStatus\": \"Yes\", \"allergyStatus\": \"Yes\", \"tobaccoList\": [ { \"tobaccoUseTypeID\": 8, \"tobaccoUseType\": \"Beedi\", \"otherTobaccoUseType\": null, \"numberperDay\": \"2\", \"duration\": \"3\", \"durationUnit\": \"Days\" }, { \"tobaccoUseTypeID\": 9, \"tobaccoUseType\": \"Filter Cigarette\", \"otherTobaccoUseType\": null, \"numberperDay\": \"2\", \"duration\": \"2\", \"durationUnit\": \"Days\" } ], \"alcoholList\": [ { \"alcoholTypeID\": 16, \"typeOfAlcohol\": \"Beer\", \"otherAlcoholType\": null, \"alcoholIntakeFrequency\": \"2-3 times a month\", \"avgAlcoholConsumption\": \"120-180 ml\", \"duration\": \"23\", \"durationUnit\": \"Days\" } ], \"allergicList\": [ { \"allergyType\": \"Drugs\", \"allergyName\": \"kjasdnajksdjk\", \"typeOfAllergicReactions\": [ { \"allergicReactionTypeID\": \"9\", \"name\": \"Abdominal Cramps\" }, { \"allergicReactionTypeID\": \"1\", \"name\": \"Anaphylaxis\" } ], \"otherTypeOfAllergicReaction\": null }, { \"allergyType\": \"Food\", \"allergyName\": \"jksnfklsndfklsdf\", \"typeOfAllergicReactions\": [ { \"allergicReactionTypeID\": \"9\", \"name\": \"Abdominal Cramps\" }, { \"allergicReactionTypeID\": \"1\", \"name\": \"Anaphylaxis\" }, { \"allergicReactionTypeID\": \"3\", \"name\": \"Angioedema\" } ], \"otherTypeOfAllergicReaction\": null } ], \"beneficiaryRegID\": \"7403\", \"benVisitID\": \"676\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" }, \"immunizationHistory\": { \"beneficiaryRegID\":\"7921\", \"createdBy\":\"786\", \"immunizationList\":[], \"providerServiceMapID\":\"1316\" }, \"childVaccineDetails\": { \"childOptionalVaccineList\": [], \"beneficiaryRegID\": \"7403\", \"providerServiceMapID\": \"1316\", \"createdBy\": \"786\" } } }";
		fetchObjPve = "{\"benRegID\":\"7506\", \"benVisitID\":\"131\"}";
		
		//Mocking save services
		try
		{
			//when(ancServiceImplMock.saveANCNurseData(isA(JsonObject.class))).thenReturn(1L);
			
			when(ancServiceImplMock.saveANCDoctorData(isA(JsonObject.class), "")).thenReturn(1L);
			
			when(ancServiceImplMock.getBenVisitDetailsFrmNurseANC(beneficiaryRegID, benVisitID)).thenReturn("");
			
			when(ancServiceImplMock.getBenANCDetailsFrmNurseANC(beneficiaryRegID, benVisitID)).thenReturn("");
			
			when(ancServiceImplMock.getBenANCHistoryDetails(beneficiaryRegID, benVisitID)).thenReturn("");
			
			when(ancServiceImplMock.getBeneficiaryVitalDetails(beneficiaryRegID, benVisitID)).thenReturn("");
			
			when(ancServiceImplMock.getANCExaminationDetailsData(beneficiaryRegID, benVisitID)).thenReturn("");
			
//			when(ancServiceImplMock.getANCPastHistoryData(beneficiaryRegID)).thenReturn("");
//			
//			when(ancServiceImplMock.getANCPersonalTobaccoHistoryData(beneficiaryRegID)).thenReturn("");
//			
//			when(ancServiceImplMock.getANCPersonalAlcoholHistoryData(beneficiaryRegID)).thenReturn("");
//			
//			when(ancServiceImplMock.getANCPersonalAllergyHistoryData(beneficiaryRegID)).thenReturn("");
//			
//			when(ancServiceImplMock.getANCMedicationHistoryData(beneficiaryRegID)).thenReturn("");
//			
//			when(ancServiceImplMock.getANCFamilyHistoryData(beneficiaryRegID)).thenReturn("");
//			
//			when(ancServiceImplMock.getANCMenstrualHistoryData(beneficiaryRegID)).thenReturn("");
//			
//			when(ancServiceImplMock.getANCObstetricHistoryData(beneficiaryRegID)).thenReturn("");
//			
//			when(ancServiceImplMock.getANCComorbidHistoryData(beneficiaryRegID)).thenReturn("");
//			
//			when(ancServiceImplMock.getANCChildVaccineHistoryData(beneficiaryRegID)).thenReturn("");
//			
//			when(ancServiceImplMock.getANCImmunizationHistoryData(beneficiaryRegID)).thenReturn("");
//			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void saveBenANCNurseDataPveTest(){
//		String response = createControllerSpy.saveBenANCNurseData(nurseSaveObjPve);
		String response = null;
		
		assertTrue("",
				response.equals("{\"data\":{\"response\":\"ANC Nurse Entered Details stored successfully.\"},\"statusCode\":200,"
						+ "\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}
	
	@Test
	public void saveBenANCDoctorDataPveTest(){
		String response = createControllerSpy.saveBenANCDoctorData(nurseSaveObjPve, "");
		
		assertTrue("",
				response.equals("{\"data\":{\"response\":\"ANC Doc data saved successfully.\"},\"statusCode\":200,"
						+ "\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}
	
	@Test
	public void getBenVisitDetailsFrmNurseANCPveTest(){
		String response = fetchControllerSpy.getBenVisitDetailsFrmNurseANC(fetchObjPve);
		
		System.out.println("response "+response);
		
		//{"statusCode":5005,"status":"Failed with internal errors at Fri Mar 09 12:17:40 IST 2018.Please try after some time. If error is still seen, contact your administrator."}
		
		assertTrue("",
				response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}
	
	@Test
	public void getBenVisitDetailsFrmNurseANCNveTest(){
		String response = fetchControllerSpy.getBenVisitDetailsFrmNurseANC(nurseSaveObjPve);
		
		assertTrue("",
				response.equals("{\"statusCode\":5001,\"errorMessage\":\"Invalid object conversion\",\"status\":\"Invalid object conversion\"}"));
	}
	
	@Test
	public void getBenANCDetailsFrmNurseANCPveTest(){
		
		String response = fetchControllerSpy.getBenANCDetailsFrmNurseANC(fetchObjPve);
		
		System.out.println("response "+response);
		
		
		assertTrue("",
				response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}
	
	@Test
	public void getBenANCHistoryDetailsPveTest(){
		
		String response = fetchControllerSpy.getBenANCHistoryDetails(fetchObjPve);
		
		System.out.println("response "+response);
		
		
		assertTrue("",
				response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}
	
	@Test
	public void getBenANCVitalDetailsFrmNurseANCPveTest(){
		String response = fetchControllerSpy.getBenANCVitalDetailsFrmNurseANC(fetchObjPve);
		
		System.out.println("response "+response);
		
		
		assertTrue("",
				response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}
	
	@Test
	public void getBenExaminationDetailsANCPveTest(){
		String response = fetchControllerSpy.getBenExaminationDetailsANC(fetchObjPve);
		
		System.out.println("response "+response);
		
		
		assertTrue("",
				response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}
	
	// @Test
	// public void getBenANCPastHistoryPveTest(){
	// String response = fetchControllerSpy.getBenANCPastHistory(fetchObjPve);
	//
	// System.out.println("response "+response);
	//
	//
	// assertTrue("",
	// response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	// }
	//
	//
	// @Test
	// public void getBenANCTobaccoHistoryPveTest(){
	// String response = fetchControllerSpy.getBenANCTobaccoHistory(fetchObjPve);
	//
	// System.out.println("response "+response);
	//
	//
	// assertTrue("",
	// response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	// }
	//
	// @Test
	// public void getBenANCAlcoholHistoryPveTest(){
	// String response = fetchControllerSpy.getBenANCAlcoholHistory(fetchObjPve);
	//
	// System.out.println("response "+response);
	//
	//
	// assertTrue("",
	// response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	// }
	//
	//
	// @Test
	// public void getBenANCAllergyHistoryPveTest(){
	// String response = fetchControllerSpy.getBenANCAllergyHistory(fetchObjPve);
	//
	// System.out.println("response "+response);
	//
	//
	// assertTrue("",
	// response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	// }
	//
	// @Test
	// public void getBenANCMedicationHistoryPveTest(){
	// String response = fetchControllerSpy.getBenANCMedicationHistory(fetchObjPve);
	//
	// System.out.println("response "+response);
	//
	//
	// assertTrue("",
	// response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	// }
	//
	// @Test
	// public void getBenANCFamilyHistoryPveTest(){
	// String response = fetchControllerSpy.getBenANCFamilyHistory(fetchObjPve);
	//
	// System.out.println("response "+response);
	//
	//
	// assertTrue("",
	// response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	// }
	//
	// @Test
	// public void getBenANCMenstrualHistoryPveTest(){
	// String response = fetchControllerSpy.getBenANCMenstrualHistory(fetchObjPve);
	//
	// System.out.println("response "+response);
	//
	//
	// assertTrue("",
	// response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	// }
	//
	//
	// @Test
	// public void getBenANCPastObstetricHistoryPveTest(){
	// String response =
	// fetchControllerSpy.getBenANCPastObstetricHistory(fetchObjPve);
	//
	// System.out.println("response "+response);
	//
	//
	// assertTrue("",
	// response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	// }
	//
	//
	// @Test
	// public void getBenANCComorbidityConditionHistoryPveTest(){
	// String response =
	// fetchControllerSpy.getBenANCComorbidityConditionHistory(fetchObjPve);
	//
	// System.out.println("response "+response);
	//
	//
	// assertTrue("",
	// response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	// }
	//
	//
	// @Test
	// public void getBenANCOptionalVaccineHistoryPveTest(){
	// String response =
	// fetchControllerSpy.getBenANCOptionalVaccineHistory(fetchObjPve);
	//
	// System.out.println("response "+response);
	//
	//
	// assertTrue("",
	// response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	// }
	//
	//
	// @Test
	// public void getBenANCImmunizationHistoryPveTest(){
	// String response =
	// fetchControllerSpy.getBenANCImmunizationHistory(fetchObjPve);
	//
	// System.out.println("response "+response);
	//
	//
	// assertTrue("",
	// response.equals("{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	// }
}

