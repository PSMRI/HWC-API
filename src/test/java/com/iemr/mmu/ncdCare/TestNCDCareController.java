package com.iemr.mmu.ncdCare;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.junit.BeforeClass;
import org.junit.Test;

import com.iemr.mmu.controller.ncdCare.NCDCareCreateController;
import com.iemr.mmu.service.ncdCare.NCDCareServiceImpl;

public class TestNCDCareController {
	private static NCDCareCreateController createController = spy(NCDCareCreateController.class);
	private static NCDCareServiceImpl ncdCareServiceImplMock = mock(NCDCareServiceImpl.class);

	private static String nurseObjPve;
	private static String doctorObjPve;

	@BeforeClass
	public static void initializeParams() {
		createController.setNcdCareServiceImpl(ncdCareServiceImplMock);
		nurseObjPve = "{\"visitDetails\": { \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"visitNo\": null, \"visitReason\": \"Screening\", \"visitCategory\": \"Cancer Screening\", \"pregnancyStatus\": \"Yes\", \"rCHID\": \"7777\", \"healthFacilityType\": null, \"healthFacilityLocation\": null, \"reportFilePath\": null, \"createdBy\": \"888\" }, \"vitalsDetails\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"weight_Kg\": \"64\", \"height_cm\": \"166\", \"waistCircumference_cm\": \"56\", \"systolicBP_1stReading\": \"120\", \"diastolicBP_1stReading\": \"65\", \"systolicBP_2ndReading\": \"113\", \"diastolicBP_2ndReading\": \"73\", \"systolicBP_3rdReading\": \"123\", \"diastolicBP_3rdReading\": \"66\", \"hbA1C\": \"4\", \"hemoglobin\": \"14\", \"bloodGlucose_Fasting\": \"123\", \"bloodGlucose_Random\": \"143\", \"bloodGlucose_2HrPostPrandial\": \"145\", \"createdBy\": \"888\" }, \"examinationDetails\": { \"signsDetails\": { \"cancerSignAndSymptoms\": { \"shortnessOfBreath\": null, \"coughgt2Weeks\": true, \"bloodInSputum\": true, \"difficultyInOpeningMouth\": true, \"nonHealingUlcerOrPatchOrGrowth\": null, \"changeInTheToneOfVoice\": null, \"lumpInTheBreast\": null, \"bloodStainedDischargeFromNipple\": null, \"changeInShapeAndSizeOfBreasts\": null, \"vaginalBleedingBetweenPeriods\": null, \"vaginalBleedingAfterMenopause\": null, \"vaginalBleedingAfterIntercourse\": null, \"foulSmellingVaginalDischarge\": null, \"lymphNode_Enlarged\": true, \"breastEnlargement\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"cancerLymphNodeDetails\": [ { \"lymphNodeName\": \" Sub Mandibular\", \"size_Left\": \"3-6 cm\", \"mobility_Left\": true, \"size_Right\": \"3-6 cm\", \"mobility_Right\": true, \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" } ] }, \"oralDetails\": { \"limitedMouthOpening\": \"+\", \"premalignantLesions\": true, \"preMalignantLesionTypeList\": [ \"Sub muscus fibrosis\" ], \"prolongedIrritation\": null, \"chronicBurningSensation\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"breastDetails\": { \"everBreastFed\": true, \"breastFeedingDurationGTE6months\": true, \"breastsAppear_Normal\": true, \"rashOnBreast\": null, \"dimplingSkinOnBreast\": true, \"dischargeFromNipple\": null, \"peaudOrange\": null, \"lumpInBreast\": null, \"lumpSize\": null, \"lumpShape\": null, \"lumpTexture\": null, \"referredToMammogram\": null, \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"abdominalDetails\": { \"abdominalInspection_Normal\": true, \"liver\": \"Not palpable\", \"ascites_Present\": true, \"anyOtherMass_Present\": true, \"lymphNodes_Enlarged\": null, \"lymphNode_Inguinal_Left\": null, \"lymphNode_Inguinal_Right\": null, \"lymphNode_ExternalIliac_Left\": null, \"lymphNode_ExternalIliac_Right\": null, \"lymphNode_ParaAortic_Left\": null, \"lymphNode_ParaAortic_Right\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"gynecologicalDetails\": { \"appearanceOfCervix\": null, \"typeOfLesionList\": null, \"vulvalInvolvement\": null, \"vaginalInvolvement\": true, \"uterus_Normal\": true, \"sufferedFromRTIOrSTI\": null, \"rTIOrSTIDetail\": null, \"filePath\": null, \"experiencedPostCoitalBleeding\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"imageCoordinates\": [ { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 3, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 97, \"yCord\": 174, \"description\": \"one\", \"point\": 1 } ] }, { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 1, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 146, \"yCord\": 184, \"description\": \"three\", \"point\": 1 } ] }, { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 4, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 102, \"yCord\": 154, \"description\": \"four\", \"point\": 1 } ] }, { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 2, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 171, \"yCord\": 156, \"description\": \"two\", \"point\": 1 } ] } ] }, \"historyDetails\": { \"familyHistory\": { \"diseases\": [ { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"cancerDiseaseType\": \"Breast Cancer\", \"otherDiseaseType\": null, \"familyMemberList\": [ \"Aunt\", \"Brother\" ], \"createdBy\": \"888\" }, { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"cancerDiseaseType\": \"lorem ipsum\", \"otherDiseaseType\": \"lorem ipsum\", \"familyMemberList\": [ \"Grand Father\" ], \"createdBy\": \"888\" } ], \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"personalHistory\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"tobaccoUse\": \"Used in Past\", \"startAge_year\": \"23\", \"endAge_year\": \"24\", \"typeOfTobaccoProductList\": [ \"Beedi\", \"Chewing\", \"Cigarettes\" ], \"quantityPerDay\": \"2\", \"isFilteredCigaerette\": true, \"isCigaretteExposure\": false, \"isBetelNutChewing\": true, \"durationOfBetelQuid\": \"12\", \"alcoholUse\": \"Currently Using\", \"ssAlcoholUsed\": true, \"frequencyOfAlcoholUsed\": \"1-4 days/week\", \"dietType\": \"Eggetarian\", \"otherDiet\": null, \"intakeOfOutsidePreparedMeal\": \"2\", \"fruitConsumptionDays\": \"2\", \"fruitQuantityPerDay\": \"2\", \"vegetableConsumptionDays\": \"2\", \"vegetableQuantityPerDay\": \"2\", \"typeOfOilConsumedList\": [ \"Coconut Oil\", \"Corn Oil\" ], \"otherOilType\": null, \"physicalActivityType\": \"Light Activity\", \"ssRadiationExposure\": false, \"isThyroidDisorder\": false, \"createdBy\": \"888\" }, \"pastObstetricHistory\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"pregnancyStatus\": \"Yes\", \"isUrinePregTest\": null, \"pregnant_No\": \"1\", \"noOfLivingChild\": \"1\", \"isAbortion\": false, \"isOralContraceptiveUsed\": false, \"isHormoneReplacementTherapy\": false, \"menarche_Age\": \"13\", \"isMenstrualCycleRegular\": true, \"menstrualCycleLength\": \"28\", \"menstrualFlowDuration\": \"3\", \"menstrualFlowType\": \"Little\", \"isDysmenorrhea\": false, \"isInterMenstrualBleeding\": false, \"menopauseAge\": null, \"isPostMenopauseBleeding\": null, \"createdBy\": \"888\" } } }";
		doctorObjPve = "{ \"findings\":{ \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"1040\", \"providerServiceMapID\":\"1320\", \"clinicalObservation\":\"Content here, content here\", \"otherSymptoms\":\"Content here, content here\", \"significantFindings\":\"Content here, content here\", \"complaints\":[ { \"chiefComplaint\":\"Abdominal Bloating\", \"chiefComplaintID\":1, \"duration\":\"23\", \"unitOfDuration\":\"Hours\", \"description\":\"content here, content here\" } ], \"createdBy\":\"888\" }, \"diagnosis\":{ \"ncdCareConditionID\":null, \"ncdCareCondition\":null, \"ncdCareTypeID\":null, \"ncdCareType\":null, \"ncdComplication\":\"Content here, content here\", \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"1040\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\" }, \"investigation\":{ \"externalInvestigation\":\"Content here, content here\", \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"1040\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\", \"laboratoryList\":[ { \"testID\":2, \"testName\":\"Blood Glucose Measurement\", \"isRadiologyImaging\":false }, { \"testID\":12, \"testName\":\"Blood grouping and Rh typing \", \"isRadiologyImaging\":false }, { \"testID\":17, \"testName\":\"BUN, Creatinine, Uric acid\", \"isRadiologyImaging\":false }, { \"testID\":29, \"testName\":\"CT Brain\", \"isRadiologyImaging\":true }, { \"testID\":30, \"testName\":\"CT Whole Spine \", \"isRadiologyImaging\":true }, { \"testID\":33, \"testName\":\"Flouroscopy- Urethrogram \", \"isRadiologyImaging\":true } ] }, \"prescription\":{ \"prescribedDrugs\":[ { \"specialInstruction\":\"After Food\", \"genericDrugName\":\"Ciprofloxacin Hcl Eye /Ear Drops IP 0.3% w/v in 5 ml\", \"dose\":\"Days \", \"frequency\":\"Thrice Daily (TID)\", \"drugForm\":\"Tablet\", \"drugDuration\":\"21\", \"qih\":3 } ], \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"1040\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\" }, \"refer\":{ \"referredToInstituteID\":null, \"refrredToAdditionalServiceList\":[ 3, 1, 5 ], \"beneficiaryRegID\":\"7866\", \"benVisitID\":\"1040\", \"providerServiceMapID\":\"1320\", \"createdBy\":\"888\" } }";

		try {
			// when(ncdCareServiceImplMock.saveNCDCareNurseData(Matchers.any(JsonObject.class))).thenReturn(1L);
			// when(ncdCareServiceImplMock.saveDoctorData(Matchers.any(JsonObject.class))).thenReturn(1L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void saveNCDCareNurseDataPveTest() {

		// String response = createController.saveBenNCDCareNurseData(nurseObjPve);
		String response = null;

		assertTrue("", response.equals(
				"{\"data\":{\"response\":\"NCD Care Nurse Entered Details stored successfully.\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

	@Test
	public void saveNCDCareDoctorDataPveTest() {

		String response = "";
		// createController.saveBenNCDCareDoctorData(doctorObjPve);

		// System.out.println("response "+response);
		assertTrue("", response.equals(
				"{\"data\":{\"response\":\"NCD Care doctor Entered Details stored successfully.\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

}
