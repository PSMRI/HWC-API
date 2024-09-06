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


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.hwc.controller.cancerscreening.CancerScreeningController;
import com.iemr.hwc.service.cancerScreening.CSServiceImpl;
@ExtendWith(MockitoExtension.class)
public class TestCSController {

	private static CancerScreeningController createController = spy(CancerScreeningController.class);
	private static CSServiceImpl cSServiceImplMock = mock(CSServiceImpl.class);

	static String nurseObjPve = "";
	static String doctorObjPve = "";
	static String fetchObjPve = "";
	static String fetchHstryObjPve = "";
	static String fetchHstryObjNve = "";

	static String updateHstryObjPve;
	static String updateVitalObjPve;

	static Long beneficiaryRegID = 7506L;
	static Long benVisitID = 131L;

	@BeforeAll
	public static void initializeParams() {
		nurseObjPve = "{\"visitDetails\": { \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"visitNo\": null, \"visitReason\": \"Screening\", \"visitCategory\": \"Cancer Screening\", \"pregnancyStatus\": \"Yes\", \"rCHID\": \"7777\", \"healthFacilityType\": null, \"healthFacilityLocation\": null, \"reportFilePath\": null, \"createdBy\": \"888\" }, \"vitalsDetails\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"weight_Kg\": \"64\", \"height_cm\": \"166\", \"waistCircumference_cm\": \"56\", \"systolicBP_1stReading\": \"120\", \"diastolicBP_1stReading\": \"65\", \"systolicBP_2ndReading\": \"113\", \"diastolicBP_2ndReading\": \"73\", \"systolicBP_3rdReading\": \"123\", \"diastolicBP_3rdReading\": \"66\", \"hbA1C\": \"4\", \"hemoglobin\": \"14\", \"bloodGlucose_Fasting\": \"123\", \"bloodGlucose_Random\": \"143\", \"bloodGlucose_2HrPostPrandial\": \"145\", \"createdBy\": \"888\" }, \"examinationDetails\": { \"signsDetails\": { \"cancerSignAndSymptoms\": { \"shortnessOfBreath\": null, \"coughgt2Weeks\": true, \"bloodInSputum\": true, \"difficultyInOpeningMouth\": true, \"nonHealingUlcerOrPatchOrGrowth\": null, \"changeInTheToneOfVoice\": null, \"lumpInTheBreast\": null, \"bloodStainedDischargeFromNipple\": null, \"changeInShapeAndSizeOfBreasts\": null, \"vaginalBleedingBetweenPeriods\": null, \"vaginalBleedingAfterMenopause\": null, \"vaginalBleedingAfterIntercourse\": null, \"foulSmellingVaginalDischarge\": null, \"lymphNode_Enlarged\": true, \"breastEnlargement\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"cancerLymphNodeDetails\": [ { \"lymphNodeName\": \" Sub Mandibular\", \"size_Left\": \"3-6 cm\", \"mobility_Left\": true, \"size_Right\": \"3-6 cm\", \"mobility_Right\": true, \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" } ] }, \"oralDetails\": { \"limitedMouthOpening\": \"+\", \"premalignantLesions\": true, \"preMalignantLesionTypeList\": [ \"Sub muscus fibrosis\" ], \"prolongedIrritation\": null, \"chronicBurningSensation\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"breastDetails\": { \"everBreastFed\": true, \"breastFeedingDurationGTE6months\": true, \"breastsAppear_Normal\": true, \"rashOnBreast\": null, \"dimplingSkinOnBreast\": true, \"dischargeFromNipple\": null, \"peaudOrange\": null, \"lumpInBreast\": null, \"lumpSize\": null, \"lumpShape\": null, \"lumpTexture\": null, \"referredToMammogram\": null, \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"abdominalDetails\": { \"abdominalInspection_Normal\": true, \"liver\": \"Not palpable\", \"ascites_Present\": true, \"anyOtherMass_Present\": true, \"lymphNodes_Enlarged\": null, \"lymphNode_Inguinal_Left\": null, \"lymphNode_Inguinal_Right\": null, \"lymphNode_ExternalIliac_Left\": null, \"lymphNode_ExternalIliac_Right\": null, \"lymphNode_ParaAortic_Left\": null, \"lymphNode_ParaAortic_Right\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"gynecologicalDetails\": { \"appearanceOfCervix\": null, \"typeOfLesionList\": null, \"vulvalInvolvement\": null, \"vaginalInvolvement\": true, \"uterus_Normal\": true, \"sufferedFromRTIOrSTI\": null, \"rTIOrSTIDetail\": null, \"filePath\": null, \"experiencedPostCoitalBleeding\": null, \"observation\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry\", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"imageCoordinates\": [ { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 3, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 97, \"yCord\": 174, \"description\": \"one\", \"point\": 1 } ] }, { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 1, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 146, \"yCord\": 184, \"description\": \"three\", \"point\": 1 } ] }, { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 4, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 102, \"yCord\": 154, \"description\": \"four\", \"point\": 1 } ] }, { \"beneficiaryRegID\": \"7506\", \"visitID\": null, \"createdBy\": \"888\", \"imageID\": 2, \"providerServiceMapID\": \"1320\", \"markers\": [ { \"xCord\": 171, \"yCord\": 156, \"description\": \"two\", \"point\": 1 } ] } ] }, \"historyDetails\": { \"familyHistory\": { \"diseases\": [ { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"cancerDiseaseType\": \"Breast Cancer\", \"otherDiseaseType\": null, \"familyMemberList\": [ \"Aunt\", \"Brother\" ], \"createdBy\": \"888\" }, { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"cancerDiseaseType\": \"lorem ipsum\", \"otherDiseaseType\": \"lorem ipsum\", \"familyMemberList\": [ \"Grand Father\" ], \"createdBy\": \"888\" } ], \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"personalHistory\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"tobaccoUse\": \"Used in Past\", \"startAge_year\": \"23\", \"endAge_year\": \"24\", \"typeOfTobaccoProductList\": [ \"Beedi\", \"Chewing\", \"Cigarettes\" ], \"quantityPerDay\": \"2\", \"isFilteredCigaerette\": true, \"isCigaretteExposure\": false, \"isBetelNutChewing\": true, \"durationOfBetelQuid\": \"12\", \"alcoholUse\": \"Currently Using\", \"ssAlcoholUsed\": true, \"frequencyOfAlcoholUsed\": \"1-4 days/week\", \"dietType\": \"Eggetarian\", \"otherDiet\": null, \"intakeOfOutsidePreparedMeal\": \"2\", \"fruitConsumptionDays\": \"2\", \"fruitQuantityPerDay\": \"2\", \"vegetableConsumptionDays\": \"2\", \"vegetableQuantityPerDay\": \"2\", \"typeOfOilConsumedList\": [ \"Coconut Oil\", \"Corn Oil\" ], \"otherOilType\": null, \"physicalActivityType\": \"Light Activity\", \"ssRadiationExposure\": false, \"isThyroidDisorder\": false, \"createdBy\": \"888\" }, \"pastObstetricHistory\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"pregnancyStatus\": \"Yes\", \"isUrinePregTest\": null, \"pregnant_No\": \"1\", \"noOfLivingChild\": \"1\", \"isAbortion\": false, \"isOralContraceptiveUsed\": false, \"isHormoneReplacementTherapy\": false, \"menarche_Age\": \"13\", \"isMenstrualCycleRegular\": true, \"menstrualCycleLength\": \"28\", \"menstrualFlowDuration\": \"3\", \"menstrualFlowType\": \"Little\", \"isDysmenorrhea\": false, \"isInterMenstrualBleeding\": false, \"menopauseAge\": null, \"isPostMenopauseBleeding\": null, \"createdBy\": \"888\" } } }";
		doctorObjPve = "{\"diagnosis\": { \"referredToInstituteID\": null, \"refrredToAdditionalServiceList\": [ 3, 1, 5 ], \"provisionalDiagnosisPrimaryDoctor\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. \", \"remarks\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. \", \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"benVisitID\": \"937\", \"createdBy\": \"888\" } }";
		fetchObjPve = "{\"benRegID\":\"7506\", \"benVisitID\":\"131\"}";

		fetchHstryObjPve = "{\"benRegID\":\"7506\"}";
		fetchHstryObjNve = "{}";

		updateHstryObjPve = "{ \"historyDetails\": { \"familyHistory\": { \"diseases\": [ { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"cancerDiseaseType\": \"Breast Cancer\", \"otherDiseaseType\": null, \"familyMemberList\": [ \"Aunt\", \"Brother\" ], \"createdBy\": \"888\" }, { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"cancerDiseaseType\": \"lorem ipsum\", \"otherDiseaseType\": \"lorem ipsum\", \"familyMemberList\": [ \"Grand Father\" ], \"createdBy\": \"888\" } ], \"beneficiaryRegID\": \"7506\", \"providerServiceMapID\": \"1320\", \"createdBy\": \"888\" }, \"personalHistory\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"tobaccoUse\": \"Used in Past\", \"startAge_year\": \"23\", \"endAge_year\": \"24\", \"typeOfTobaccoProductList\": [ \"Beedi\", \"Chewing\", \"Cigarettes\" ], \"quantityPerDay\": \"2\", \"isFilteredCigaerette\": true, \"isCigaretteExposure\": false, \"isBetelNutChewing\": true, \"durationOfBetelQuid\": \"12\", \"alcoholUse\": \"Currently Using\", \"ssAlcoholUsed\": true, \"frequencyOfAlcoholUsed\": \"1-4 days/week\", \"dietType\": \"Eggetarian\", \"otherDiet\": null, \"intakeOfOutsidePreparedMeal\": \"2\", \"fruitConsumptionDays\": \"2\", \"fruitQuantityPerDay\": \"2\", \"vegetableConsumptionDays\": \"2\", \"vegetableQuantityPerDay\": \"2\", \"typeOfOilConsumedList\": [ \"Coconut Oil\", \"Corn Oil\" ], \"otherOilType\": null, \"physicalActivityType\": \"Light Activity\", \"ssRadiationExposure\": false, \"isThyroidDisorder\": false, \"createdBy\": \"888\" }, \"pastObstetricHistory\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"pregnancyStatus\": \"Yes\", \"isUrinePregTest\": null, \"pregnant_No\": \"1\", \"noOfLivingChild\": \"1\", \"isAbortion\": false, \"isOralContraceptiveUsed\": false, \"isHormoneReplacementTherapy\": false, \"menarche_Age\": \"13\", \"isMenstrualCycleRegular\": true, \"menstrualCycleLength\": \"28\", \"menstrualFlowDuration\": \"3\", \"menstrualFlowType\": \"Little\", \"isDysmenorrhea\": false, \"isInterMenstrualBleeding\": false, \"menopauseAge\": null, \"isPostMenopauseBleeding\": null, \"createdBy\": \"888\" } } }";
		updateVitalObjPve = "{ \"vitalsDetails\": { \"beneficiaryRegID\": \"7506\", \"benVisitID\": null, \"providerServiceMapID\": \"1320\", \"weight_Kg\": \"64\", \"height_cm\": \"166\", \"waistCircumference_cm\": \"56\", \"systolicBP_1stReading\": \"120\", \"diastolicBP_1stReading\": \"65\", \"systolicBP_2ndReading\": \"113\", \"diastolicBP_2ndReading\": \"73\", \"systolicBP_3rdReading\": \"123\", \"diastolicBP_3rdReading\": \"66\", \"hbA1C\": \"4\", \"hemoglobin\": \"14\", \"bloodGlucose_Fasting\": \"123\", \"bloodGlucose_Random\": \"143\", \"bloodGlucose_2HrPostPrandial\": \"145\", \"createdBy\": \"888\" } }";

		createController.setCancerScreeningServiceImpl(cSServiceImplMock);
		
		try {
			// when(cSServiceImplMock.saveCancerScreeningNurseData(isA(JsonObject.class))).thenReturn(1L);

		//	when(cSServiceImplMock.saveCancerScreeningDoctorData(isA(JsonObject.class))).thenReturn(1L);

			when(cSServiceImplMock.getBenDataFrmNurseToDocVisitDetailsScreen(beneficiaryRegID, benVisitID))
					.thenReturn("");

			when(cSServiceImplMock.getBenDataFrmNurseToDocHistoryScreen(beneficiaryRegID, benVisitID)).thenReturn("");

			when(cSServiceImplMock.getBenDataFrmNurseToDocVitalScreen(beneficiaryRegID, benVisitID)).thenReturn("");

			when(cSServiceImplMock.getBenDataFrmNurseToDocExaminationScreen(beneficiaryRegID, benVisitID))
					.thenReturn("");

		//	when(cSServiceImplMock.getCancerCasesheetData(isA(JSONObject.class))).thenReturn("");

			when(cSServiceImplMock.getBenFamilyHistoryData(beneficiaryRegID)).thenReturn("");

			when(cSServiceImplMock.getBenPersonalHistoryData(beneficiaryRegID)).thenReturn("");

			when(cSServiceImplMock.getBenPersonalDietHistoryData(beneficiaryRegID)).thenReturn("");

			when(cSServiceImplMock.getBenObstetricHistoryData(beneficiaryRegID)).thenReturn("");

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void saveCSNurseDataPveTest() {

		// String response =
		// createController.saveBenCancerScreeningNurseData(nurseObjPve);
		// temp code by neeraj due to carestream integration
		String response = "{\'data\':{\'response\':\'Nurse data saved successfully.\'},\'statusCode\':200,\'errorMessage\':\'Success\',\'status\':\'Success\'}";

		Assertions.assertEquals("", response.equals(
				"{\"data\":{\"response\":\"Nurse data saved successfully.\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

	@Test
	public void saveCSDoctorDataPveTest() {

	//	String response = createController.saveBenCancerScreeningDoctorData(nurseObjPve);
		String response = "";
		Assertions.assertEquals("", response.equals(
				"{\"data\":{\"response\":\"Doc data saved successfully.\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

	@Test
	public void getBenDataFrmNurseScrnToDocScrnVisitDetailsPveTest() {

		String response = createController.getBenDataFrmNurseScrnToDocScrnVisitDetails(fetchObjPve);

		Assertions.assertEquals("", response.equals(
				"{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

	@Test
	public void getBenDataFrmNurseScrnToDocScrnHistoryPveTest() {

		String response = createController.getBenDataFrmNurseScrnToDocScrnHistory(fetchObjPve);

		Assertions.assertEquals("", response.equals(
				"{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

	@Test
	public void getBenDataFrmNurseScrnToDocScrnVitalPveTest() {

		String response = createController.getBenDataFrmNurseScrnToDocScrnVital(fetchObjPve);

		Assertions.assertEquals("", response.equals(
				"{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

	@Test
	public void getBenDataFrmNurseScrnToDocScrnExaminationPveTest() {

		String response = createController.getBenDataFrmNurseScrnToDocScrnExamination(fetchObjPve);

		Assertions.assertEquals("", response.equals(
				"{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

	@Test
	public void getBenDataForCaseSheetPveTest() {

		//String response = fetchController.getBenDataForCaseSheet(fetchObjPve);

		// assertTrue("", response.equals(
		//	"{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

	@Test
	public void getBenCancerFamilyHistoryPveTest() {

		String response = createController.getBenCancerFamilyHistory(fetchHstryObjPve);

		Assertions.assertEquals("", response.equals(
				"{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

	@Test
	public void getBenCancerFamilyHistoryNveTest() {

		String response = createController.getBenCancerFamilyHistory(fetchHstryObjNve);

		Assertions.assertEquals("", response.equals(
				"{\"statusCode\":5000,\"errorMessage\":\"Invalid Request Data !!!\",\"status\":\"Invalid Request Data !!!\"}"));
	}

	@Test
	public void getBenCancerPersonalHistoryPveTest() {

		String response = createController.getBenCancerPersonalHistory(fetchHstryObjPve);

		Assertions.assertEquals("", response.equals(
				"{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

	@Test
	public void getBenCancerPersonalHistoryNveTest() {

		String response = createController.getBenCancerPersonalHistory(fetchHstryObjNve);

		Assertions.assertEquals("", response.equals(
				"{\"statusCode\":5000,\"errorMessage\":\"Invalid Request Data !!!\",\"status\":\"Invalid Request Data !!!\"}"));
	}

	@Test
	public void getBenCancerPersonalDietHistoryPveTest() {

		String response = createController.getBenCancerPersonalDietHistory(fetchHstryObjPve);

		Assertions.assertEquals("", response.equals(
				"{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

	@Test
	public void getBenCancerObstetricHistoryPveTest() {

		String response = createController.getBenCancerObstetricHistory(fetchHstryObjPve);

		Assertions.assertEquals("", response.equals(
				"{\"data\":{\"response\":\"\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

	@Test
	public void updateCSHistoryNursePveTest() {

		String response = createController.updateCSHistoryNurse(updateHstryObjPve);

		System.out.println(response);
		Assertions.assertEquals("", response.equals(
				"{\"data\":{\"response\":\"Beneficiary history data updated successfully.\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

	@Test
	public void upodateBenVitalDetailPveTest() {

		String response = createController.upodateBenVitalDetail(updateVitalObjPve);

		System.out.println(response);
		Assertions.assertEquals("", response.equals(
				"{\"data\":{\"response\":\"Beneficiary Vital Details updated Successfully\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

	@Test
	public void upodateBenExaminationDetailPveTest() {

		String response = createController.upodateBenExaminationDetail(updateVitalObjPve);

		System.out.println(response);
		Assertions.assertEquals("", response.equals(
				"{\"data\":{\"response\":\"Beneficiary Examination Details updated Successfully\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

	@Test
	public void updateCancerDiagnosisDetailsByOncologistPveTest() {

		String response = createController.updateCancerDiagnosisDetailsByOncologist(updateVitalObjPve);

		System.out.println(response);
		Assertions.assertEquals("", response.equals(
				"{\"data\":{\"response\":\"Cancer Diagnosis Details updated By Oncologist Successfully\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"));
	}

}
