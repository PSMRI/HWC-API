/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.generalOPD;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;

import com.iemr.mmu.common.TestCommonServices;
import com.iemr.mmu.data.anc.BenChildDevelopmentHistory;
import com.iemr.mmu.data.anc.ChildFeedingDetails;
import com.iemr.mmu.data.anc.PerinatalHistory;
import com.iemr.mmu.data.anc.SysGastrointestinalExamination;
import com.iemr.mmu.repo.nurse.anc.BenChildDevelopmentHistoryRepo;
import com.iemr.mmu.repo.nurse.anc.ChildFeedingDetailsRepo;
import com.iemr.mmu.repo.nurse.anc.PerinatalHistoryRepo;
import com.iemr.mmu.repo.nurse.anc.SysGastrointestinalExaminationRepo;
import com.iemr.mmu.service.generalOPD.GeneralOPDNurseServiceImpl;
import com.iemr.mmu.service.generalOPD.GeneralOPDServiceImpl;

public class TestGeneralOPDServices {

	@InjectMocks
	private static GeneralOPDServiceImpl generalOPDServiceImpl = spy(GeneralOPDServiceImpl.class);
	private static GeneralOPDNurseServiceImpl generalOPDNurseServiceImpl = spy(GeneralOPDNurseServiceImpl.class);

	private static BenChildDevelopmentHistoryRepo benChildDevelopmentHistoryRepoMock = mock(
			BenChildDevelopmentHistoryRepo.class);
	private static ChildFeedingDetailsRepo childFeedingDetailsRepoMock = mock(ChildFeedingDetailsRepo.class);
	private static PerinatalHistoryRepo perinatalHistoryRepoMock = mock(PerinatalHistoryRepo.class);
	private static SysGastrointestinalExaminationRepo sysGastrointestinalExaminationRepoMock = mock(
			SysGastrointestinalExaminationRepo.class);

	public static String feedingHistoryDataPveRes = "";
	public static String perinatalHistoryDataPveRes = "";
	public static String developmentHistoryDataPveRes = "";

	@BeforeClass
	public static void initializeParams() {

		TestCommonServices.initializeParams();

		// generalOPDServiceImpl.setCommonNurseServiceImpl(TestCommonServices.commonNurseServiceImpl);
		// generalOPDServiceImpl.setCommonDoctorServiceImpl(TestCommonServices.commonDoctorServiceImpl);
		// generalOPDServiceImpl.setGeneralOPDNurseServiceImpl(generalOPDNurseServiceImpl);

		// generalOPDNurseServiceImpl.setBenChildDevelopmentHistoryRepo(benChildDevelopmentHistoryRepoMock);
		// generalOPDNurseServiceImpl.setChildFeedingDetailsRepo(childFeedingDetailsRepoMock);
		// generalOPDNurseServiceImpl.setPerinatalHistoryRepo(perinatalHistoryRepoMock);
		// generalOPDNurseServiceImpl.setSysGastrointestinalExaminationRepo(sysGastrointestinalExaminationRepoMock);

		feedingHistoryDataPveRes = "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"childID\",\"columnName\":\"Child ID\"},{\"keyName\":\"benMotherID\",\"columnName\":\"Beneficiary Mother ID\"},{\"keyName\":\"typeOfFeed\",\"columnName\":\"Type Of Feed\"},{\"keyName\":\"compFeedStartAge\",\"columnName\":\"Comp Feed Start Age\"},{\"keyName\":\"noOfCompFeedPerDay\",\"columnName\":\"NoOf Comp Feed Per Day\"},{\"keyName\":\"foodIntoleranceStatus\",\"columnName\":\"Food Intolerance Status\"},{\"keyName\":\"typeofFoodIntolerance\",\"columnName\":\"Type of Food Intolerance\"}]}";
		perinatalHistoryDataPveRes = "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"placeOfDelivery\",\"columnName\":\"Place Of Delivery\"},{\"keyName\":\"otherPlaceOfDelivery\",\"columnName\":\"Other Place Of Delivery\"},{\"keyName\":\"typeOfDelivery\",\"columnName\":\"Type Of Delivery\"},{\"keyName\":\"complicationAtBirth\",\"columnName\":\"Complication At Birth\"},{\"keyName\":\"otherComplicationAtBirth\",\"columnName\":\"Other Complication At Birth\"},{\"keyName\":\"gestation\",\"columnName\":\"Gestation\"},{\"keyName\":\"birthWeight_kg\",\"columnName\":\"Birth Weight(kg)\"}]}";
		developmentHistoryDataPveRes = "{\"data\":[],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date of Capture\"},{\"keyName\":\"grossMotorMilestone\",\"columnName\":\"Gross Motor Milestone\"},{\"keyName\":\"isGMMAttained\",\"columnName\":\"Is GMM Attained\"},{\"keyName\":\"fineMotorMilestone\",\"columnName\":\"Fine Motor Milestone\"},{\"keyName\":\"isFMMAttained\",\"columnName\":\"Is FMM Attained\"},{\"keyName\":\"socialMilestone\",\"columnName\":\"Social Milestone\"},{\"keyName\":\"isSMAttained\",\"columnName\":\"Is SM Attained\"},{\"keyName\":\"languageMilestone\",\"columnName\":\"Language Milestone\"},{\"keyName\":\"isLMAttained\",\"columnName\":\"Is LM Attained\"},{\"keyName\":\"developmentProblem\",\"columnName\":\"Development Problem\"}]}";

		try {

			// Mocking Save Repo's
			BenChildDevelopmentHistory dvmtHsry = spy(BenChildDevelopmentHistory.class);
			dvmtHsry.setID(1L);
			when(benChildDevelopmentHistoryRepoMock.save(isA(BenChildDevelopmentHistory.class))).thenReturn(dvmtHsry);

			ChildFeedingDetails feedingHistry = spy(ChildFeedingDetails.class);
			feedingHistry.setID(1L);
			when(childFeedingDetailsRepoMock.save(isA(ChildFeedingDetails.class))).thenReturn(feedingHistry);

			PerinatalHistory perinatalHistry = spy(PerinatalHistory.class);
			perinatalHistry.setID(1L);
			when(perinatalHistoryRepoMock.save(isA(PerinatalHistory.class))).thenReturn(perinatalHistry);

			SysGastrointestinalExamination sysGastrointestinalExamination = spy(SysGastrointestinalExamination.class);
			sysGastrointestinalExamination.setID(1L);
			when(sysGastrointestinalExaminationRepoMock.save(isA(SysGastrointestinalExamination.class)))
					.thenReturn(sysGastrointestinalExamination);

			/* Mocking get History Repo's */
			ArrayList<Object[]> getGOPDRes = new ArrayList<Object[]>();
			when(perinatalHistoryRepoMock.getBenPerinatalDetail(TestCommonServices.beneficiaryRegID))
					.thenReturn(getGOPDRes);
			when(childFeedingDetailsRepoMock.getBenFeedingHistoryDetail(TestCommonServices.beneficiaryRegID))
					.thenReturn(getGOPDRes);
			when(benChildDevelopmentHistoryRepoMock.getBenDevelopmentHistoryDetail(TestCommonServices.beneficiaryRegID))
					.thenReturn(getGOPDRes);

			/* Mocking get Current Visit Repo's */

			when(benChildDevelopmentHistoryRepoMock.getBenDevelopmentDetails(TestCommonServices.beneficiaryRegID,
					TestCommonServices.benVisitID)).thenReturn(getGOPDRes);

			when(perinatalHistoryRepoMock.getBenPerinatalDetails(TestCommonServices.beneficiaryRegID,
					TestCommonServices.benVisitID)).thenReturn(getGOPDRes);

			when(childFeedingDetailsRepoMock.getBenFeedingDetails(TestCommonServices.beneficiaryRegID,
					TestCommonServices.benVisitID)).thenReturn(getGOPDRes);

			when(sysGastrointestinalExaminationRepoMock.getSSysGastrointestinalExamination(
					TestCommonServices.beneficiaryRegID, TestCommonServices.benVisitID))
							.thenReturn(new SysGastrointestinalExamination());

			when(childFeedingDetailsRepoMock.updateFeedingDetails(Matchers.anyLong(), Matchers.anyLong(),
					Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
					Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyLong(),
					Matchers.anyLong(), Matchers.anyString())).thenReturn(1);

			when(perinatalHistoryRepoMock.updatePerinatalDetails(Matchers.anyShort(), Matchers.anyString(),
					Matchers.anyString(), Matchers.anyShort(), Matchers.anyString(), Matchers.anyShort(),
					Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyDouble(),
					Matchers.anyString(), Matchers.anyString(), Matchers.anyLong(), Matchers.anyLong())).thenReturn(1);

			when(benChildDevelopmentHistoryRepoMock.updateBenChildDevelopmentHistory(Matchers.anyString(),
					Matchers.anyBoolean(), Matchers.anyString(), Matchers.anyBoolean(), Matchers.anyString(),
					Matchers.anyBoolean(), Matchers.anyString(), Matchers.anyBoolean(), Matchers.anyString(),
					Matchers.anyString(), Matchers.anyString(), Matchers.anyLong(), Matchers.anyLong())).thenReturn(1);

			when(sysGastrointestinalExaminationRepoMock.updateSysGastrointestinalExamination(Matchers.anyString(),
					Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
					Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
					Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyLong(),
					Matchers.anyLong())).thenReturn(1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void saveGOPDNurseDataPveTest() {

		Long response = null;
		try {
			// response = generalOPDServiceImpl.saveNurseData(TestCommonServices.jsnOBJPve);
			response = null;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo(1);
	}

	@Test
	public void saveGOPDNurseDataNveTest() {

		Long response = null;
		try {
			// response = generalOPDServiceImpl.saveNurseData(TestCommonServices.jsnOBJNve);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo(null);

		// assertEquals(1, response);
	}

	@Test
	public void saveGOPDDoctorDataPveTest() {

		Long response = null;
		try {
			response = generalOPDServiceImpl.saveDoctorData(TestCommonServices.doctorSaveJsnPve, "");

			System.out.println("response " + response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo(1);
	}

	// @Test
	// public void getPastHistoryDataPveTest()
	// {
	// // String expectedRes = "{\"data\":[{\"Year_Of_Illness\":\"Feb 15,
	// 2018\",\"Year_Of_Surgery\":\"Feb 9,
	// // 2018\",\"Illness_Type\":\"Cataract\",\"Surgery_Type\":\"Cesarean
	// Section/LSCS\",\"captureDate\":\"Feb 16,
	// // 2018\"}],\"columns\":[{\"keyName\":\"captureDate\",\"columnName\":\"Date
	// of
	// // Capture\"},{\"keyName\":\"Illness_Type\",\"columnName\":\"Illness
	// // Type\"},{\"keyName\":\"Other_Illness_Type\",\"columnName\":\"Other Illness
	// // Type\"},{\"keyName\":\"Year_Of_Illness\",\"columnName\":\"Year Of
	// // Illness\"},{\"keyName\":\"Surgery_Type\",\"columnName\":\"Surgery
	// // Type\"},{\"keyName\":\"Other_Surgery_Type\",\"columnName\":\"Other Surgery
	// // Type\"},{\"keyName\":\"Year_Of_Surgery\",\"columnName\":\"Year Of
	// Surgery\"}]}";
	//
	// String response = null;
	// try
	// {
	// response =
	// generalOPDServiceImpl.getPastHistoryData(TestCommonServices.beneficiaryRegID);
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// assertThat(response).isEqualTo(TestCommonServices.pastHistoryDataPveRes);
	//
	// // assertEquals(1, response);
	// }
	//
	// @Test
	// public void getPastHistoryDataNveTest()
	// {
	//
	// String response = null;
	// try
	// {
	// response = generalOPDServiceImpl.getPastHistoryData(123L);
	// System.out.println("response " + response);
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// assertThat(response).isEqualTo(TestCommonServices.pastHistoryDataNveRes);
	//
	// }
	//
	// @Test
	// public void getPersonalTobaccoHistoryDataPveTest()
	// {
	//
	// String response = null;
	// try
	// {
	// response =
	// generalOPDServiceImpl.getPersonalTobaccoHistoryData(TestCommonServices.beneficiaryRegID);
	//
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// assertThat(response).isEqualTo(TestCommonServices.tobaccoHistoryDataPveRes);
	//
	// // assertEquals(1, response);
	// }
	//
	// @Test
	// public void getPersonalAlcoholHistoryDataPveTest()
	// {
	// // String expectedRes =
	// {"data":[],"columns":[{"keyName":"captureDate","columnName":"Date of
	// // Capture"},{"keyName":"dietaryType","columnName":"Dietary
	// // Type"},{"keyName":"physicalActivityType","columnName":"Physical Activity
	// // Type"},{"keyName":"alcoholIntakeStatus","columnName":"Alcohol Intake
	// // Status"},{"keyName":"alcoholType","columnName":"Alcohol
	// // Type"},{"keyName":"otherAlcoholType","columnName":"Other Alcohol
	// // Type"},{"keyName":"alcoholIntakeFrequency","columnName":"Alcohol Intake
	// // Frequency"},{"keyName":"avgAlcoholConsumption","columnName":"Avg Alcohol
	// // Consumption"},{"keyName":"alcohol_use_duration","columnName":"Alcohol Use
	// Started
	// // Date"},{"keyName":"riskySexualPracticeStatus","columnName":"Risky Sexual
	// Practices Status"}]}
	//
	// String response = null;
	// try
	// {
	// response =
	// generalOPDServiceImpl.getPersonalAlcoholHistoryData(TestCommonServices.beneficiaryRegID);
	//
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// assertThat(response).isEqualTo(TestCommonServices.alcoholHistoryDataPveRes);
	//
	// // assertEquals(1, response);
	// }
	//
	// @Test
	// public void getPersonalAllergyHistoryDataPveTest()
	// {
	// // String expectedRes =
	// {"data":[],"columns":[{"keyName":"captureDate","columnName":"Date of
	// // Capture"},{"keyName":"allergyStatus","columnName":"Allergy
	// // Status"},{"keyName":"allergyType","columnName":"Allergy
	// // Type"},{"keyName":"allergenName","columnName":"Allergy
	// // Name"},{"keyName":"allergicReactionType","columnName":"Allergic Reaction
	// // Type"},{"keyName":"otherAllergicReaction","columnName":"Other Allergic
	// // Reaction"},{"keyName":"remarks","columnName":"Remarks"}]}
	//
	// String response = null;
	// try
	// {
	// response =
	// generalOPDServiceImpl.getPersonalAllergyHistoryData(TestCommonServices.beneficiaryRegID);
	//
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// assertThat(response).isEqualTo(TestCommonServices.allergyHistoryDataPveRes);
	//
	// // assertEquals(1, response);
	// }
	//
	// @Test
	// public void getMedicationHistoryDataPveTest()
	// {
	// // String expectedRes =
	// {"data":[],"columns":[{"keyName":"captureDate","columnName":"Date of
	// // Capture"},{"keyName":"currentMedication","columnName":"Current
	// // Medication"},{"keyName":"medication_year","columnName":"Date"}]}
	//
	// String response = null;
	// try
	// {
	// response =
	// generalOPDServiceImpl.getMedicationHistoryData(TestCommonServices.beneficiaryRegID);
	//
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// assertThat(response).isEqualTo(TestCommonServices.medicationHistoryDataPveRes);
	//
	// // assertEquals(1, response);
	// }
	//
	// @Test
	// public void getFamilyHistoryDataPveTest()
	// {
	// // String expectedRes =
	// {"data":[],"columns":[{"keyName":"captureDate","columnName":"Date of
	// // Capture"},{"keyName":"familyMember","columnName":"Family
	// // Member"},{"keyName":"diseaseType","columnName":"Disease
	// // Type"},{"keyName":"otherDiseaseType","columnName":"Other Disease
	// // Type"},{"keyName":"isGeneticDisorder","columnName":"Is Genetic
	// // Disorder"},{"keyName":"geneticDisorder","columnName":"Genetic
	// // Disorder"},{"keyName":"isConsanguineousMarrige","columnName":"Is
	// Consanguineous Marrige"}]}
	//
	// String response = null;
	// try
	// {
	// response =
	// generalOPDServiceImpl.getFamilyHistoryData(TestCommonServices.beneficiaryRegID);
	//
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// assertThat(response).isEqualTo(TestCommonServices.familyHistoryDataPveRes);
	//
	// // assertEquals(1, response);
	// }
	//
	// @Test
	// public void getMenstrualHistoryDataPveTest()
	// {
	// // String expectedRes =
	// {"data":[],"columns":[{"keyName":"captureDate","columnName":"Date of
	// //
	// Capture"},{"keyName":"regularity","columnName":"Regularity"},{"keyName":"cycleLength","columnName":"Cycle
	// // Length"},{"keyName":"bloodFlowDuration","columnName":"Blood Flow
	// // Duration"},{"keyName":"problemName","columnName":"Problem
	// // Name"},{"keyName":"lmp_date","columnName":"LMPDate"}]}
	//
	// String response = null;
	// try
	// {
	// response =
	// generalOPDServiceImpl.getMenstrualHistoryData(TestCommonServices.beneficiaryRegID);
	//
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// assertThat(response).isEqualTo(TestCommonServices.menstrualHistoryDataPveRes);
	//
	// // assertEquals(1, response);
	// }
	//
	// @Test
	// public void getObstetricHistoryDataPveTest()
	// {
	// // String expectedRes =
	// {"data":[],"columns":[{"keyName":"captureDate","columnName":"Date of
	// // Capture"},{"keyName":"pregOrder","columnName":"Preg
	// // Order"},{"keyName":"pregComplicationType","columnName":"Preg Complication
	// // Type"},{"keyName":"otherPregComplication","columnName":"Other Preg
	// // Complication"},{"keyName":"durationType","columnName":"Duration
	// // Type"},{"keyName":"deliveryType","columnName":"Delivery
	// // Type"},{"keyName":"deliveryPlace","columnName":"Delivery
	// // Place"},{"keyName":"otherDeliveryPlace","columnName":"Other Delivery
	// // Place"},{"keyName":"deliveryComplicationType","columnName":"Delivery
	// Complication
	// // Type"},{"keyName":"otherDeliveryComplication","columnName":"Other Delivery
	// // Complication"},{"keyName":"pregOutcome","columnName":"Preg
	// // Outcome"},{"keyName":"postpartumComplicationType","columnName":"Postpartum
	// Complication
	// // Type"},{"keyName":"otherPostpartumCompType","columnName":"Other Postpartum
	// // CompType"},{"keyName":"postNatalComplication","columnName":"Post Natal
	// // Complication"},{"keyName":"otherPostNatalComplication","columnName":"Other
	// Post Natal
	// // Complication"},{"keyName":"congenitalAnomalies","columnName":"Congenital
	// // Anomalies"},{"keyName":"newBornComplication","columnName":"New Born
	// // Complication"},{"keyName":"otherNewBornComplication","columnName":"Other
	// New Born Complication"}]}
	//
	// String response = null;
	// try
	// {
	// response =
	// generalOPDServiceImpl.getObstetricHistoryData(TestCommonServices.beneficiaryRegID);
	//
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// assertThat(response).isEqualTo(TestCommonServices.obstetricHistoryDataPveRes);
	//
	// // assertEquals(1, response);
	// }
	//
	// @Test
	// public void getComorbidHistoryDataPveTest()
	// {
	// // String expectedRes =
	// {"data":[],"columns":[{"keyName":"captureDate","columnName":"Date of
	// // Capture"},{"keyName":"comorbidCondition","columnName":"Comorbid
	// // Condition"},{"keyName":"otherComorbidCondition","columnName":"Other
	// Comorbid
	// // Condition"},{"keyName":"date","columnName":"Date"}]}
	//
	// String response = null;
	// try
	// {
	// response =
	// generalOPDServiceImpl.getComorbidHistoryData(TestCommonServices.beneficiaryRegID);
	//
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// assertThat(response).isEqualTo(TestCommonServices.comorbidHistoryDataPveRes);
	//
	// // assertEquals(1, response);
	// }
	//
	// @Test
	// public void getChildVaccineHistoryDataPveTest()
	// {
	// // String expectedRes =
	// {"data":[],"columns":[{"keyName":"captureDate","columnName":"Date of
	// // Capture"},{"keyName":"comorbidCondition","columnName":"Comorbid
	// // Condition"},{"keyName":"otherComorbidCondition","columnName":"Other
	// Comorbid
	// // Condition"},{"keyName":"date","columnName":"Date"}]}
	//
	// String response = null;
	// try
	// {
	// response =
	// generalOPDServiceImpl.getChildVaccineHistoryData(TestCommonServices.beneficiaryRegID);
	//
	// System.out.println("getChildVaccineHistoryData " + response);
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// assertThat(response).isEqualTo(TestCommonServices.childVaccineHistoryDataPveRes);
	//
	// // assertEquals(1, response);
	// }
	//
	// @Test
	// public void getImmunizationHistoryDataPveTest()
	// {
	// // String expectedRes =
	// {"data":[],"columns":[{"keyName":"captureDate","columnName":"Date of
	// // Capture"},{"keyName":"comorbidCondition","columnName":"Comorbid
	// // Condition"},{"keyName":"otherComorbidCondition","columnName":"Other
	// Comorbid
	// // Condition"},{"keyName":"date","columnName":"Date"}]}
	//
	// String response = null;
	// try
	// {
	// response =
	// generalOPDServiceImpl.getImmunizationHistoryData(TestCommonServices.beneficiaryRegID);
	//
	// System.out.println("getImmunizationHistoryData " + response);
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// assertThat(response).isEqualTo(TestCommonServices.immunizationHistoryDataPveRes);
	//
	// // assertEquals(1, response);
	// }
	//
	// @Test
	// public void getBenPerinatalHistoryDataPveTest()
	// {
	// // String expectedRes =
	// {"data":[],"columns":[{"keyName":"captureDate","columnName":"Date of
	// // Capture"},{"keyName":"comorbidCondition","columnName":"Comorbid
	// // Condition"},{"keyName":"otherComorbidCondition","columnName":"Other
	// Comorbid
	// // Condition"},{"keyName":"date","columnName":"Date"}]}
	//
	// String response = null;
	// try
	// {
	// response =
	// generalOPDServiceImpl.getBenPerinatalHistoryData(TestCommonServices.beneficiaryRegID);
	//
	// System.out.println("getBenPerinatalHistoryData " + response);
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// assertThat(response).isEqualTo(perinatalHistoryDataPveRes);
	//
	// // assertEquals(1, response);
	// }
	//
	// @Test
	// public void getBenFeedingHistoryDataPveTest()
	// {
	// // String expectedRes =
	// {"data":[],"columns":[{"keyName":"captureDate","columnName":"Date of
	// // Capture"},{"keyName":"comorbidCondition","columnName":"Comorbid
	// // Condition"},{"keyName":"otherComorbidCondition","columnName":"Other
	// Comorbid
	// // Condition"},{"keyName":"date","columnName":"Date"}]}
	//
	// String response = null;
	// try
	// {
	// response =
	// generalOPDServiceImpl.getBenFeedingHistoryData(TestCommonServices.beneficiaryRegID);
	//
	// System.out.println("getBenFeedingHistoryData " + response);
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// assertThat(response).isEqualTo(feedingHistoryDataPveRes);
	//
	// // assertEquals(1, response);
	// }
	//
	// @Test
	// public void getBenDevelopmentHistoryDataPveTest()
	// {
	// // String expectedRes =
	// {"data":[],"columns":[{"keyName":"captureDate","columnName":"Date of
	// // Capture"},{"keyName":"comorbidCondition","columnName":"Comorbid
	// // Condition"},{"keyName":"otherComorbidCondition","columnName":"Other
	// Comorbid
	// // Condition"},{"keyName":"date","columnName":"Date"}]}
	//
	// String response = null;
	// try
	// {
	// response =
	// generalOPDServiceImpl.getBenDevelopmentHistoryData(TestCommonServices.beneficiaryRegID);
	//
	// System.out.println("getBenDevelopmentHistoryData " + response);
	// } catch (Exception e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// assertThat(response).isEqualTo(developmentHistoryDataPveRes);
	//
	// // assertEquals(1, response);
	// }

	@Test
	public void getBenVisitDetailsPveTest() {

		String response = null;
		try {
			response = generalOPDServiceImpl.getBenVisitDetailsFrmNurseGOPD(TestCommonServices.beneficiaryRegID,
					TestCommonServices.benVisitID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo(TestCommonServices.visitDetailsPveRes);
	}

	@Test
	public void getBenHistoryDetailsPveTest() {

		String response = null;
		try {
			response = generalOPDServiceImpl.getBenHistoryDetails(TestCommonServices.beneficiaryRegID,
					TestCommonServices.benVisitID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo(TestCommonServices.historyDetailsPveRes);
	}

	@Test
	public void getBeneficiaryVitalDetailsPveTest() {

		String response = null;
		try {
			response = generalOPDServiceImpl.getBeneficiaryVitalDetails(TestCommonServices.beneficiaryRegID,
					TestCommonServices.benVisitID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo(TestCommonServices.vitalDetailsPveRes);
	}

	@Test
	public void getExaminationDetailsPveTest() {

		String response = null;
		try {
			response = generalOPDServiceImpl.getExaminationDetailsData(TestCommonServices.beneficiaryRegID,
					TestCommonServices.benVisitID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo(TestCommonServices.examinationDetailsPveRes);
	}

	// Update Test Cases
	/*
	 * @Test public void UpdateVisitDetailsPveTest() {
	 * 
	 * int response = 0; try { response =
	 * generalOPDServiceImpl.UpdateVisitDetails(TestCommonServices.jsnOBJPve); }
	 * catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace();
	 * }
	 * 
	 * assertThat(response).isEqualTo(1); }
	 */

	@Test
	public void updateBenHistoryDetailsPveTest() {

		int response = 0;
		try {
			response = generalOPDServiceImpl.updateBenHistoryDetails(TestCommonServices.updateHstryJsnPve);

			System.out.println("updateBenHistoryDetailsPveTest ---" + response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo(1);
	}

	@Test
	public void updateBenVitalDetailsPveTest() {

		int response = 0;
		try {
			response = generalOPDServiceImpl.updateBenVitalDetails(TestCommonServices.updateVitalJsnPve);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo(1);
	}

	@Test
	public void updateBenExaminationDetailsPveTest() {

		int response = 0;
		try {
			response = generalOPDServiceImpl.updateBenExaminationDetails(TestCommonServices.updateExaminationJsnPve);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertThat(response).isEqualTo(1);
	}
}
