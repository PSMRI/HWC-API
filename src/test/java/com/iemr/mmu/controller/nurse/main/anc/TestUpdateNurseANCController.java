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
package com.iemr.mmu.controller.nurse.main.anc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.iemr.hwc.Application;
import com.iemr.hwc.data.anc.BenMedHistory;
import com.iemr.hwc.repo.nurse.anc.BenMedHistoryRepo;
import com.iemr.hwc.service.anc.ANCNurseServiceImpl;

/*@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@SpringBootTest(classes = Application.class)
@Configuration*/
@ExtendWith(MockitoExtension.class)
public class TestUpdateNurseANCController {

	
	@Autowired
	private BenMedHistoryRepo benMedHistoryRepo;

	/*@Test
	public void saveMedHistory() {
		BenMedHistory medHistory = new BenMedHistory();
		medHistory.setBeneficiaryRegID(new Long(7509));
		medHistory.setIllnessTypeID(1);
		medHistory.setIllnessType("waeq");
		medHistory.setCreatedBy("Navya");
		
		 * entityManager.persist(medHistory); entityManager.flush();
		 
		BenMedHistory History = benMedHistoryRepo.save(medHistory);

		System.out.println(History);
		assertThat(History).isEqualTo(medHistory);
		
		BenMedHistory result = benMedHistoryRepo.findOne(History.getBenMedHistoryID());
		System.out.println(result.getIllnessType());
		
	}*/
	
	@Test
	public void saveMedHistory() {
		BenMedHistory medHistory = new BenMedHistory();
		medHistory.setBeneficiaryRegID(new Long(7509));
		medHistory.setIllnessTypeID(1);
		medHistory.setIllnessType("waeq");
		medHistory.setCreatedBy("Navya");

		 
		BenMedHistory History = benMedHistoryRepo.save(medHistory);

		System.out.println(History);
		assertThat(History).isEqualTo(medHistory);
		
		BenMedHistory result = benMedHistoryRepo.findById(History.getBenMedHistoryID()).get();
		System.out.println(result.getIllnessType());
		
	}
	
	@Test
	public void getMedHistory(){
		
	}

	/*
	 * @Test public void updateBenAdherence() { try { String json =
	 * "{\"beneficiaryRegID\":\"7921\",\"benVisitID\":561,\"providerServiceMapID\":\"1316\",\"createdBy\":\"786\",\"toDrugs\":false,\"drugReason\":\"kjasdnajksndjkasdn\",\"toReferral\":false,\"referralReason\":\"ksjandajksndakjnd\",\"progress\":\"Unchanged\"}";
	 * 
	 * mockMvc.perform(
	 * post("/anc/update/visitDetails/adherence").contentType(MediaType.
	 * APPLICATION_JSON).content(json)) .andExpect(content().string(
	 * "{\"data\":{\"response\":\"Ben Adherence data update successfully.\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * @Test public void updateBenchiefComplaints() { try { String json =
	 * "[{\"beneficiaryRegID\":\"7921\",\"benVisitID\":561,\"providerServiceMapID\":\"1316\",\"chiefComplaint\":\"Abdominal Bloating\",\"chiefComplaintID\":1,\"duration\":\"12\",\"unitOfDuration\":\"Hours\",\"description\":\"sdfjsdkfnksjdfn\",\"createdBy\":\"786\"},{\"beneficiaryRegID\":\"7921\",\"benVisitID\":561,\"providerServiceMapID\":\"1316\",\"chiefComplaint\":\"Abdominal Mass \",\"chiefComplaintID\":2,\"duration\":\"12\",\"unitOfDuration\":\"Hours\",\"description\":\"dfsnmklfnaskldn\",\"createdBy\":\"786\"}]"
	 * ;
	 * 
	 * mockMvc.perform(post("/anc/update/visitDetails/chiefComplaints").
	 * contentType(MediaType.APPLICATION_JSON) .content(json))
	 * .andExpect(content().string(
	 * "{\"data\":{\"response\":\"Chief-complaints data updated successfully.\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * @Test public void updateBenInvestigations() { try { String json =
	 * "{\"beneficiaryRegID\":\"7921\",\"benVisitID\":561,\"providerServiceMapID\":\"1316\",\"createdBy\":\"786\",\"laboratoryList\":[{\"testID\":1,\"testName\":\"Hemoglobin Estimation\",\"isRadiologyImaging\":false},{\"testID\":2,\"testName\":\"Blood Glucose Measurement\",\"isRadiologyImaging\":false},{\"testID\":3,\"testName\":\"Microscopy - Blood; Sputum and Urine\",\"isRadiologyImaging\":false}]}"
	 * ;
	 * 
	 * mockMvc.perform(post("/anc/update/visitDetails/investigations").
	 * contentType(MediaType.APPLICATION_JSON) .content(json))
	 * .andExpect(content().string(
	 * "{\"data\":{\"response\":\"Investigation data updated successfully.\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * @Test public void updateBenANCDetails() { try { String json =
	 * "{\"beneficiaryRegID\":\"7921\",\"benVisitID\":561,\"providerServiceMapID\":\"1316\",\"createdBy\":\"786\",\"primiGravida\":false,\"lmpDate\":\"2017-06-10T10:46:08.000Z\",\"gestationalAgeOrPeriodofAmenorrhea_POA\":30,\"expDelDt\":\"2018-03-17T10:46:08.000Z\",\"duration\":\"6\",\"trimesterNumber\":3,\"gravida_G\":7,\"termDeliveries_T\":\"2\",\"pretermDeliveries_P\":\"2\",\"abortions_A\":\"2\",\"livebirths_L\":\"2\",\"bloodGroup\":\"A+\"}";
	 * 
	 * mockMvc.perform(post("/anc/update/ANC/ANCDetails").contentType(MediaType.
	 * APPLICATION_JSON).content(json)) .andExpect(content().string(
	 * "{\"data\":{\"response\":\"ANC Care data updated successfully.\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * @Test public void updateBenANCImmunizationDetails() { try { String json =
	 * "{\"tT_1Status\":\"Received\",\"dateReceivedForTT_1\":\"2017-08-10T10:47:05.000Z\",\"facilityNameOfTT_1\":\"Govt Facility\",\"tT_2Status\":\"Received\",\"dateReceivedForTT_2\":\"2017-09-10T10:47:05.000Z\",\"facilityNameOfTT_2\":\"Govt Facility\",\"tT_3Status\":\"Received\",\"dateReceivedForTT_3\":\"2017-11-10T10:47:05.000Z\",\"facilityNameOfTT_3\":\"Govt Facility\",\"beneficiaryRegID\":\"7921\",\"benVisitID\":561,\"providerServiceMapID\":\"1316\",\"createdBy\":\"786\"}"
	 * ;
	 * 
	 * mockMvc.perform(post("/anc/update/ANC/ANCImmunizationDetails").
	 * contentType(MediaType.APPLICATION_JSON) .content(json))
	 * .andExpect(content().string(
	 * "{\"data\":{\"response\":\"ANC Immunization data updated successfully.\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * @Test public void updateBenANCPastHistoryDetails() { try { String json =
	 * "{\"pastIllness\":[{\"illnessTypeID\":\"6\",\"illnessType\":\"Asthma\",\"otherIllnessType\":null,\"timePeriodAgo\":\"12\",\"timePeriodUnit\":\"Days\"},{\"illnessTypeID\":\"22\",\"illnessType\":\"Other\",\"otherIllnessType\":\"illness\",\"timePeriodAgo\":\"13\",\"timePeriodUnit\":\"Days\"}],\"pastSurgery\":[{\"surgeryID\":\"3\",\"surgeryType\":\"Appendicectomy\",\"otherSurgeryType\":null,\"timePeriodAgo\":\"12\",\"timePeriodUnit\":\"Days\"},{\"surgeryID\":\"6\",\"surgeryType\":\"Other\",\"otherSurgeryType\":\"djnfjkasndkasjd\",\"timePeriodAgo\":\"14\",\"timePeriodUnit\":\"Days\"}],\"beneficiaryRegID\":\"7506\",\"benVisitID\":\"560\",\"providerServiceMapID\":\"1316\",\"createdBy\":\"786\"}";
	 * 
	 * mockMvc.perform(post("/anc/update/history/pastHistoryDetails").
	 * contentType(MediaType.APPLICATION_JSON) .content(json))
	 * .andExpect(content().string(
	 * "{\"data\":{\"response\":\"ANC Past History data updated successfully.\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * @Test public void updateANCBenComorbidConditions() { try { String json =
	 * "{\"comorbidityConcurrentConditionsList\":[{\"otherComorbidCondition\":null,\"timePeriodAgo\":\"12\",\"timePeriodUnit\":\"Days\",\"isForHistory\":true,\"comorbidCondition\":\"Asthma\",\"comorbidConditionID\":\"6\"},{\"otherComorbidCondition\":\"comorbidity\",\"timePeriodAgo\":\"12\",\"timePeriodUnit\":\"Days\",\"isForHistory\":true,\"comorbidCondition\":\"Other\",\"comorbidConditionID\":\"8\"}],\"beneficiaryRegID\":\"7506\",\"benVisitID\":\"560\",\"providerServiceMapID\":\"1316\",\"createdBy\":\"786\"}";
	 * 
	 * mockMvc.perform(post("/anc/update/history/comorbidConditions").
	 * contentType(MediaType.APPLICATION_JSON) .content(json))
	 * .andExpect(content().string(
	 * "{\"data\":{\"response\":\"Beneficairy ComorbidCondition Details updated successfully\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * @Test public void testPositiveUpdateANCMedicationHstory() { try { String
	 * json = "{\"medicationHistoryList\":[{\"currentMedication\": \"abc\"," +
	 * "\"timePeriodAgo\":\"3\", \"timePeriodUnit\":\"Days\"}], \"beneficiaryRegID\":\"7398\", \"benVisitID\":\"455\", "
	 * + "\"providerServiceMapID\":\"1316\", \"createdBy\":\"786\"}";
	 * 
	 * mockMvc.perform(
	 * post("/anc/update/history/medicationHistory").contentType(MediaType.
	 * APPLICATION_JSON).content(json)) .andExpect(content().string(
	 * "{\"data\":{\"response\":\"Beneficiary ANC Medication History Details updated successfully\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * @Test public void testNegativeUpdateANCMedicationHstory() { try { String
	 * json = "{\"medicationHistoryList\":[{\"currentMedication\": \"abc\"," +
	 * "\"timePeriodAgo\":\"3\", \"timePeriodUnit\":\"Days\"}], \"beneficiaryRegID\":\"7398\", \"benVisitID\":\"455\", "
	 * + "\"providerServiceMapID\":\"1316\", \"createdBy\":\"786\"}";
	 * 
	 * mockMvc.perform(
	 * post("/anc/update/history/medicationHistory").contentType(MediaType.
	 * APPLICATION_JSON).content(json)) .andExpect(content().string(
	 * "{\"statusCode\":5000,\"errorMessage\":\"Something went wrong\",\"status\":\"Something went wrong\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * @Test public void updateANCBenPersonalHistory() { try { String json =
	 * "{\"dietaryType\":\"Vegetarian\",\"physicalActivityType\":\"Sedentary\",\"riskySexualPracticesStatus\":1,\"tobaccoUseStatus\":\"Yes\",\"alcoholIntakeStatus\":\"Yes\",\"allergyStatus\":\"Yes\",\"tobaccoList\":[{\"tobaccoUseTypeID\":8,\"tobaccoUseType\":\"Beedi\",\"otherTobaccoUseType\":null,\"numberperDay\":\"24\",\"duration\":\"12\",\"durationUnit\":\"Weeks\"},{\"tobaccoUseTypeID\":9,\"tobaccoUseType\":\"Filter Cigarette\",\"otherTobaccoUseType\":null,\"numberperDay\":\"13\",\"duration\":\"13\",\"durationUnit\":\"Days\"}],\"alcoholList\":[{\"alcoholTypeID\":21,\"typeOfAlcohol\":\"Scotch\",\"otherAlcoholType\":null,\"alcoholIntakeFrequency\":\"Daily\",\"avgAlcoholConsumption\":\"120-180 ml\",\"duration\":\"12\",\"durationUnit\":\"Weeks\"},{\"alcoholTypeID\":16,\"typeOfAlcohol\":\"Beer\",\"otherAlcoholType\":null,\"alcoholIntakeFrequency\":\"2-3 times a month\",\"avgAlcoholConsumption\":\"120-180 ml\",\"duration\":\"12\",\"durationUnit\":\"Days\"}],\"allergicList\":[{\"allergyType\":\"Drugs\",\"allergyName\":\"askldnaskldnaskl\",\"typeOfAllergicReactions\":[{\"allergicReactionTypeID\":\"9\",\"name\":\"Abdominal Cramps\"},{\"allergicReactionTypeID\":\"1\",\"name\":\"Anaphylaxis\"}],\"otherTypeOfAllergicReaction\":null},{\"allergyType\":\"Food\",\"allergyName\":\"sdfkndaklsdnaskl\",\"typeOfAllergicReactions\":[{\"allergicReactionTypeID\":\"3\",\"name\":\"Angioedema\"},{\"allergicReactionTypeID\":\"10\",\"name\":\"Diarrhea\"}],\"otherTypeOfAllergicReaction\":null}],\"beneficiaryRegID\":\"7506\",\"benVisitID\":\"560\",\"providerServiceMapID\":\"1316\",\"createdBy\":\"786\"}"
	 * ;
	 * 
	 * mockMvc.perform(
	 * post("/anc/update/history/personalHistory").contentType(MediaType.
	 * APPLICATION_JSON).content(json)) .andExpect(content().string(
	 * "{\"data\":{\"response\":\"Beneficiary ANC Personal History Details updated successfully\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * @Test public void updateANCBenFamilyHistory() { try { String json =
	 * "{\"familyDiseaseList\":[{\"diseaseTypeID\":\"6\",\"diseaseType\":\"Asthma\",\"otherDiseaseType\":null,\"familyMembers\":[\"Aunt\",\"Brother\"]},{\"diseaseTypeID\":\"8\",\"diseaseType\":\"Other\",\"otherDiseaseType\":\"alksdnaskldnalsd\",\"familyMembers\":[\"Aunt\",\"Brother\"]}],\"isGeneticDisorder\":true,\"geneticDisorder\":\"sdnaklsdmnaklsdnmaskl\",\"isConsanguineousMarrige\":true,\"beneficiaryRegID\":\"7506\",\"benVisitID\":\"560\",\"providerServiceMapID\":\"1316\",\"createdBy\":\"786\"}";
	 * 
	 * mockMvc.perform(
	 * post("/anc/update/history/familyHistory").contentType(MediaType.
	 * APPLICATION_JSON).content(json)) .andExpect(content().string(
	 * "{\"data\":{\"response\":\"Beneficiary ANC Family History Details updated successfully\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * // @Test // public void updateANCChildOptionalVaccineHistory() { // try {
	 * // String json = "{\r\n" + // " \"benVisitID\": 545,\r\n" + //
	 * " \"providerServiceMapID\": \"1316\",\r\n" + //
	 * " \"weight_Kg\": \"62\",\r\n" + // " \"height_cm\": \"172\",\r\n" + //
	 * " \"waistCircumference_cm\": null,\r\n" + //
	 * " \"hipCircumference_cm\": null,\r\n" + // " \"bMI\": 22.5,\r\n" + //
	 * " \"waistHipRatio\": null,\r\n" + // " \"temperature\": \"102\",\r\n" +
	 * // " \"pulseRate\": \"72\",\r\n" + // " \"systolicBP\": \"99\",\r\n" + //
	 * " \"diastolicBP\": \"99\",\r\n" + // " \"respiratoryRate\": \"36\",\r\n"
	 * + // " \"createdBy\": \"786\"\r\n" + // "}"; // //
	 * mockMvc.perform(post("/anc/update/history/childOptionalVaccineHistory")
	 * // .contentType(MediaType.APPLICATION_JSON) //
	 * .content(json)).andExpect(content().string(
	 * "{\"data\":{\"response\":\"Beneficiary // ANC Child Optional Vaccine
	 * History Details updated // successfully\
	 * "},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"
	 * )); // // } catch (Exception e) { // e.printStackTrace(); // } // }
	 * 
	 * @Test public void updateSysGastrointestinalExamination() { try { String
	 * json =
	 * "{\"inspection\":\"asdnasndjka\",\"palpation_AbdomenTexture\":\"Soft\",\"palpation_Liver\":\"Not Palpable\",\"palpation_Spleen\":\"Not Palpable\",\"palpation_Tenderness\":\"Absent\",\"palpation_LocationOfTenderness\":null,\"percussion\":\"asdnaskjdnasjkd\",\"auscultation\":\"jkdnasjkdnajksdnajk\",\"analRegion\":\"jkasndjknaskjdnasjk\",\"beneficiaryRegID\":\"7921\",\"benVisitID\":561,\"providerServiceMapID\":\"1316\",\"createdBy\":\"786\"}"
	 * ;
	 * 
	 * mockMvc.perform(post(
	 * "/anc/update/examination/gastrointestinalExamination")
	 * .contentType(MediaType.APPLICATION_JSON).content(json))
	 * .andExpect(content().string(
	 * "{\"data\":{\"response\":\"Ben Gastrointestinal Examination data updated successfully.\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * @Test public void updateSysCardiovascularExamination() { try { String
	 * json =
	 * "{\"jugularVenousPulse_JVP\":\"Normal\",\"apexbeatLocation\":\"jksndaksjdnajksdn\",\"apexbeatType\":\"jknasjkdnasjkdnasjkd\",\"firstHeartSound_S1\":\"ndasjkdnajksdnajksdnjk\",\"secondHeartSound_S2\":\"ndjnasjkdnajksdnajksdn\",\"additionalHeartSounds\":\"jndajksdnajkdnajksdnasjkn\",\"murmurs\":\"Present\",\"pericardialRub\":\"Present\",\"beneficiaryRegID\":\"7921\",\"benVisitID\":561,\"providerServiceMapID\":\"1316\",\"createdBy\":\"786\"}";
	 * 
	 * mockMvc.perform(post("/anc/update/examination/cardiovascularExamination")
	 * .contentType(MediaType.APPLICATION_JSON).content(json))
	 * .andExpect(content().string(
	 * "{\"data\":{\"response\":\"Ben Cardiovascular Examination data updated successfully.\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * @Test public void updateRespiratorySystemExamination() { try { String
	 * json =
	 * "{\"trachea\":\"Central\",\"inspection\":\"asjdnjkasndjkas\",\"signsOfRespiratoryDistress\":\"Present\",\"palpation\":\"asdknmasjkdnaskjld\",\"auscultation_Stridor\":\"Present\",\"auscultation_BreathSounds\":\"Normal\",\"auscultation_Crepitations\":\"Present\",\"auscultation_Wheezing\":\"Present\",\"auscultation_PleuralRub\":\"Present\",\"auscultation_ConductedSounds\":\"Present\",\"percussion\":\"Stony Dull\",\"beneficiaryRegID\":\"7921\",\"benVisitID\":561,\"providerServiceMapID\":\"1316\",\"createdBy\":\"786\"}"
	 * ;
	 * 
	 * mockMvc.perform(post(
	 * "/anc/update/examination/respiratorySystemExamination")
	 * .contentType(MediaType.APPLICATION_JSON).content(json))
	 * .andExpect(content().string(
	 * "{\"data\":{\"response\":\"Beneficairy Respiratory System Examination Details updated successfully\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * @Test public void testPositiveUpdateANCVitals() { try { String json =
	 * "{\"beneficiaryRegID\":\"7921\",\"benVisitID\":561,\"providerServiceMapID\":\"1316\",\"weight_Kg\":\"64\",\"height_cm\":\"166\",\"waistCircumference_cm\":null,\"hipCircumference_cm\":null,\"bMI\":23.2,\"waistHipRatio\":null,\"temperature\":\"98\",\"pulseRate\":\"72\",\"systolicBP\":\"99\",\"diastolicBP\":\"99\",\"respiratoryRate\":\"99\",\"createdBy\":\"786\"}";
	 * 
	 * mockMvc.perform(post("/anc/update/vitals").contentType(MediaType.
	 * APPLICATION_JSON).content(json)) .andExpect(content().string(
	 * "{\"data\":{\"response\":\"Beneficiary ANC Vitals Details updated successfully\"},\"statusCode\":200,\"errorMessage\":\"Success\",\"status\":\"Success\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * @Test public void testNegativeUpdateANCVitals() { try { String json =
	 * "{\"benVisitID\":561,\"providerServiceMapID\":\"1316\",\"weight_Kg\":\"64\",\"height_cm\":\"166\",\"waistCircumference_cm\":null,\"hipCircumference_cm\":null,\"bMI\":23.2,\"waistHipRatio\":null,\"temperature\":\"98\",\"pulseRate\":\"72\",\"systolicBP\":\"99\",\"diastolicBP\":\"99\",\"respiratoryRate\":\"99\",\"createdBy\":\"786\"}";
	 * 
	 * mockMvc.perform(post("/anc/update/vitals").contentType(MediaType.
	 * APPLICATION_JSON).content(json)) .andExpect(content().string(
	 * "{\"statusCode\":5000,\"errorMessage\":\"Something went wrong\",\"status\":\"Something went wrong\"}"
	 * ));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */
}
