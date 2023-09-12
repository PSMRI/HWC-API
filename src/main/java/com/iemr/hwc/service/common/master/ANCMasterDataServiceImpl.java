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
package com.iemr.hwc.service.common.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.doctor.ChiefComplaintMaster;
import com.iemr.hwc.data.doctor.DrugDoseMaster;
import com.iemr.hwc.data.doctor.DrugDurationUnitMaster;
import com.iemr.hwc.data.doctor.DrugFrequencyMaster;
import com.iemr.hwc.data.foetalmonitor.FoetalMonitorTestMaster;
import com.iemr.hwc.data.institution.Institute;
import com.iemr.hwc.data.labModule.ProcedureData;
import com.iemr.hwc.data.masterdata.anc.ComorbidCondition;
import com.iemr.hwc.data.masterdata.anc.CompFeeds;
import com.iemr.hwc.data.masterdata.anc.ComplicationTypes;
import com.iemr.hwc.data.masterdata.anc.CounsellingType;
import com.iemr.hwc.data.masterdata.anc.DevelopmentProblems;
import com.iemr.hwc.data.masterdata.anc.DiseaseType;
import com.iemr.hwc.data.masterdata.anc.FundalHeight;
import com.iemr.hwc.data.masterdata.anc.Gestation;
import com.iemr.hwc.data.masterdata.anc.GrossMotorMilestone;
import com.iemr.hwc.data.masterdata.anc.IllnessTypes;
import com.iemr.hwc.data.masterdata.anc.JointTypes;
import com.iemr.hwc.data.masterdata.anc.MenstrualCycleRange;
import com.iemr.hwc.data.masterdata.anc.MenstrualCycleStatus;
import com.iemr.hwc.data.masterdata.anc.MenstrualProblem;
import com.iemr.hwc.data.masterdata.anc.Musculoskeletal;
import com.iemr.hwc.data.masterdata.anc.OptionalVaccinations;
import com.iemr.hwc.data.masterdata.anc.PersonalHabitType;
import com.iemr.hwc.data.masterdata.anc.PregDuration;
import com.iemr.hwc.data.masterdata.anc.PregOutcome;
import com.iemr.hwc.data.masterdata.anc.ServiceFacilityMaster;
import com.iemr.hwc.data.masterdata.anc.ServiceMaster;
import com.iemr.hwc.data.masterdata.anc.SurgeryTypes;
import com.iemr.hwc.data.masterdata.doctor.ItemFormMaster;
import com.iemr.hwc.data.masterdata.doctor.ItemMaster;
import com.iemr.hwc.data.masterdata.doctor.RouteOfAdmin;
import com.iemr.hwc.data.masterdata.doctor.V_DrugPrescription;
import com.iemr.hwc.data.masterdata.ncdcare.NCDCareType;
import com.iemr.hwc.data.masterdata.ncdscreening.NCDScreeningCondition;
import com.iemr.hwc.data.masterdata.nurse.FamilyMemberType;
import com.iemr.hwc.data.masterdata.pnc.NewbornHealthStatus;
import com.iemr.hwc.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.hwc.repo.doctor.DrugDoseMasterRepo;
import com.iemr.hwc.repo.doctor.DrugDurationUnitMasterRepo;
import com.iemr.hwc.repo.doctor.DrugFrequencyMasterRepo;
import com.iemr.hwc.repo.foetalmonitor.FoetalMonitorTestsRepo;
import com.iemr.hwc.repo.labModule.ProcedureRepo;
import com.iemr.hwc.repo.login.MasterVanRepo;
import com.iemr.hwc.repo.masterrepo.anc.AllergicReactionTypesRepo;
import com.iemr.hwc.repo.masterrepo.anc.BloodGroupsRepo;
import com.iemr.hwc.repo.masterrepo.anc.ChildVaccinationsRepo;
import com.iemr.hwc.repo.masterrepo.anc.ComorbidConditionRepo;
import com.iemr.hwc.repo.masterrepo.anc.CompFeedsRepo;
import com.iemr.hwc.repo.masterrepo.anc.ComplicationTypesRepo;
import com.iemr.hwc.repo.masterrepo.anc.CounsellingTypeRepo;
import com.iemr.hwc.repo.masterrepo.anc.DeliveryPlaceRepo;
import com.iemr.hwc.repo.masterrepo.anc.DeliveryTypeRepo;
import com.iemr.hwc.repo.masterrepo.anc.DevelopmentProblemsRepo;
import com.iemr.hwc.repo.masterrepo.anc.DiseaseTypeRepo;
import com.iemr.hwc.repo.masterrepo.anc.FundalHeightRepo;
import com.iemr.hwc.repo.masterrepo.anc.GestationRepo;
import com.iemr.hwc.repo.masterrepo.anc.GrossMotorMilestoneRepo;
import com.iemr.hwc.repo.masterrepo.anc.IllnessTypesRepo;
import com.iemr.hwc.repo.masterrepo.anc.JointTypesRepo;
import com.iemr.hwc.repo.masterrepo.anc.MenstrualCycleRangeRepo;
import com.iemr.hwc.repo.masterrepo.anc.MenstrualCycleStatusRepo;
import com.iemr.hwc.repo.masterrepo.anc.MenstrualProblemRepo;
import com.iemr.hwc.repo.masterrepo.anc.MusculoskeletalRepo;
import com.iemr.hwc.repo.masterrepo.anc.OptionalVaccinationsRepo;
import com.iemr.hwc.repo.masterrepo.anc.PersonalHabitTypeRepo;
import com.iemr.hwc.repo.masterrepo.anc.PregDurationRepo;
import com.iemr.hwc.repo.masterrepo.anc.PregOutcomeRepo;
import com.iemr.hwc.repo.masterrepo.anc.ServiceFacilityMasterRepo;
import com.iemr.hwc.repo.masterrepo.anc.ServiceMasterRepo;
import com.iemr.hwc.repo.masterrepo.anc.SurgeryTypesRepo;
import com.iemr.hwc.repo.masterrepo.covid19.CovidContactHistoryMasterRepo;
import com.iemr.hwc.repo.masterrepo.covid19.CovidRecommnedationMasterRepo;
import com.iemr.hwc.repo.masterrepo.covid19.CovidSymptomsMasterRepo;
import com.iemr.hwc.repo.masterrepo.doctor.InstituteRepo;
import com.iemr.hwc.repo.masterrepo.doctor.ItemFormMasterRepo;
import com.iemr.hwc.repo.masterrepo.doctor.ItemMasterRepo;
import com.iemr.hwc.repo.masterrepo.doctor.ReferralReasonRepo;
import com.iemr.hwc.repo.masterrepo.doctor.RouteOfAdminRepo;
import com.iemr.hwc.repo.masterrepo.doctor.V_DrugPrescriptionRepo;
import com.iemr.hwc.repo.masterrepo.generalopd.FoodIntoleranceStatusRepo;
import com.iemr.hwc.repo.masterrepo.generalopd.SubVisitCategoryRepo;
import com.iemr.hwc.repo.masterrepo.ncdCare.NCDCareTypeRepo;
import com.iemr.hwc.repo.masterrepo.nurse.FamilyMemberMasterRepo;
import com.iemr.hwc.repo.masterrepo.pnc.DeliveryConductedByMasterRepo;
import com.iemr.hwc.repo.masterrepo.pnc.NewbornHealthStatusRepo;
import com.iemr.hwc.repo.neonatal.CounsellingProvidedMasterRepo;
import com.iemr.hwc.repo.neonatal.NextDueVaccinesMasterRepo;
import com.iemr.hwc.repo.neonatal.NextImmunizationLocationMasterRepo;

@Service
public class ANCMasterDataServiceImpl {

	private AllergicReactionTypesRepo allergicReactionTypesRepo;
	// private BirthComplicationRepo birthComplicationRepo;
	private BloodGroupsRepo bloodGroupsRepo;
	private ChildVaccinationsRepo childVaccinationsRepo;
	// private DeliveryComplicationTypesRepo deliveryComplicationTypesRepo;
	private DeliveryPlaceRepo deliveryPlaceRepo;
	private DeliveryTypeRepo deliveryTypeRepo;
	private DevelopmentProblemsRepo developmentProblemsRepo;
	private GestationRepo gestationRepo;
	private IllnessTypesRepo illnessTypesRepo;
	private JointTypesRepo jointTypesRepo;
	private MenstrualCycleRangeRepo menstrualCycleRangeRepo;
	private MenstrualCycleStatusRepo menstrualCycleStatusRepo;
	private MenstrualProblemRepo menstrualProblemRepo;
	private MusculoskeletalRepo musculoskeletalRepo;
	// private PostpartumComplicationTypesRepo postpartumComplicationTypesRepo;
	// private PregComplicationTypesRepo pregComplicationTypesRepo;
	private PregDurationRepo pregDurationRepo;
	private SurgeryTypesRepo surgeryTypesRepo;
	private ComorbidConditionRepo comorbidConditionRepo;
	private CompFeedsRepo compFeedsRepo;
	private FundalHeightRepo fundalHeightRepo;
	private GrossMotorMilestoneRepo grossMotorMilestoneRepo;
	private ServiceMasterRepo serviceMasterRepo;
	private CounsellingTypeRepo counsellingTypeRepo;
	private InstituteRepo instituteRepo;
	private PersonalHabitTypeRepo personalHabitTypeRepo;
	// private PostNatalComplicationRepo postNatalComplicationRepo;
	private PregOutcomeRepo pregOutcomeRepo;
	private DiseaseTypeRepo diseaseTypeRepo;
	// private NewBornComplicationRepo newBornComplicationRepo;
	private ComplicationTypesRepo complicationTypesRepo;

	private ChiefComplaintMasterRepo chiefComplaintMasterRepo;
	private FamilyMemberMasterRepo familyMemberMasterRepo;
	// private LabTestMasterRepo labTestMasterRepo;

	private DrugDoseMasterRepo drugDoseMasterRepo;
	private DrugDurationUnitMasterRepo drugDurationUnitMasterRepo;
	// private DrugFormMasterRepo drugFormMasterRepo;
	private DrugFrequencyMasterRepo drugFrequencyMasterRepo;

	private NewbornHealthStatusRepo newbornHealthStatusRepo;

	private NCDScreeningMasterServiceImpl ncdScreeningMasterServiceImpl;
	private NCDCareTypeRepo ncdCareTypeRepo;

	private ProcedureRepo procedureRepo;

	// private TempMasterDrugRepo tempMasterDrugRepo;

	private OptionalVaccinationsRepo optionalVaccinationsRepo;
	@Autowired
	private ItemMasterRepo itemMasterRepo;

	private ItemFormMasterRepo itemFormMasterRepo;
	private RouteOfAdminRepo routeOfAdminRepo;
	private V_DrugPrescriptionRepo v_DrugPrescriptionRepo;

	@Autowired
	private CovidSymptomsMasterRepo covidSymptomsMasterRepo;
	@Autowired
	private CovidContactHistoryMasterRepo covidContactHistoryMasterRepo;
	@Autowired
	private CovidRecommnedationMasterRepo covidRecommnedationMasterRepo;

	@Autowired
	private MasterVanRepo masterVanRepo;

	@Autowired
	private FoetalMonitorTestsRepo foetalMonitorTestRepo;
	@Autowired
	private ReferralReasonRepo referralReasonRepo;

	@Autowired
	public void setV_DrugPrescriptionRepo(V_DrugPrescriptionRepo v_DrugPrescriptionRepo) {
		this.v_DrugPrescriptionRepo = v_DrugPrescriptionRepo;
	}

	@Autowired
	public void setRouteOfAdminRepo(RouteOfAdminRepo routeOfAdminRepo) {
		this.routeOfAdminRepo = routeOfAdminRepo;
	}

	@Autowired
	public void setItemFormMasterRepo(ItemFormMasterRepo itemFormMasterRepo) {
		this.itemFormMasterRepo = itemFormMasterRepo;
	}

	// @Autowired
	// public void setItemMasterRepo(ItemMasterRepo itemMasterRepo) {
	// this.itemMasterRepo = itemMasterRepo;
	// }

	@Autowired
	public void setOptionalVaccinationsRepo(OptionalVaccinationsRepo optionalVaccinationsRepo) {
		this.optionalVaccinationsRepo = optionalVaccinationsRepo;
	}

	// @Autowired
	// public void setTempMasterDrugRepo(TempMasterDrugRepo tempMasterDrugRepo) {
	// this.tempMasterDrugRepo = tempMasterDrugRepo;
	// }

	@Autowired
	public void setProcedureRepo(ProcedureRepo procedureRepo) {
		this.procedureRepo = procedureRepo;
	}

	@Autowired
	public void setNcdScreeningMasterServiceImpl(NCDScreeningMasterServiceImpl ncdScreeningMasterServiceImpl) {
		this.ncdScreeningMasterServiceImpl = ncdScreeningMasterServiceImpl;
	}

	@Autowired
	public void setNcdCareTypeRepo(NCDCareTypeRepo ncdCareTypeRepo) {
		this.ncdCareTypeRepo = ncdCareTypeRepo;
	}

	@Autowired
	public void setNewbornHealthStatusRepo(NewbornHealthStatusRepo newbornHealthStatusRepo) {
		this.newbornHealthStatusRepo = newbornHealthStatusRepo;
	}

	@Autowired
	public void setAllergicReactionTypesRepo(AllergicReactionTypesRepo allergicReactionTypesRepo) {
		this.allergicReactionTypesRepo = allergicReactionTypesRepo;
	}

	// @Autowired
	// public void setBirthComplicationRepo(BirthComplicationRepo
	// birthComplicationRepo) {
	// this.birthComplicationRepo = birthComplicationRepo;
	// }

	@Autowired
	public void setBloodGroupsRepo(BloodGroupsRepo bloodGroupsRepo) {
		this.bloodGroupsRepo = bloodGroupsRepo;
	}

	@Autowired
	public void setChildVaccinationsRepo(ChildVaccinationsRepo childVaccinationsRepo) {
		this.childVaccinationsRepo = childVaccinationsRepo;
	}

	// @Autowired
	// public void setDeliveryComplicationTypesRepo(DeliveryComplicationTypesRepo
	// deliveryComplicationTypesRepo) {
	// this.deliveryComplicationTypesRepo = deliveryComplicationTypesRepo;
	// }

	@Autowired
	public void setDeliveryPlaceRepo(DeliveryPlaceRepo deliveryPlaceRepo) {
		this.deliveryPlaceRepo = deliveryPlaceRepo;
	}

	@Autowired
	public void setDeliveryTypeRepo(DeliveryTypeRepo deliveryTypeRepo) {
		this.deliveryTypeRepo = deliveryTypeRepo;
	}

	@Autowired
	public void setDevelopmentProblemsRepo(DevelopmentProblemsRepo developmentProblemsRepo) {
		this.developmentProblemsRepo = developmentProblemsRepo;
	}

	@Autowired
	public void setGestationRepo(GestationRepo gestationRepo) {
		this.gestationRepo = gestationRepo;
	}

	@Autowired
	public void setIllnessTypesRepo(IllnessTypesRepo illnessTypesRepo) {
		this.illnessTypesRepo = illnessTypesRepo;
	}

	@Autowired
	public void setJointTypesRepo(JointTypesRepo jointTypesRepo) {
		this.jointTypesRepo = jointTypesRepo;
	}

	@Autowired
	public void setMenstrualCycleRangeRepo(MenstrualCycleRangeRepo menstrualCycleRangeRepo) {
		this.menstrualCycleRangeRepo = menstrualCycleRangeRepo;
	}

	@Autowired
	public void setMenstrualCycleStatusRepo(MenstrualCycleStatusRepo menstrualCycleStatusRepo) {
		this.menstrualCycleStatusRepo = menstrualCycleStatusRepo;
	}

	@Autowired
	public void setMenstrualProblemRepo(MenstrualProblemRepo menstrualProblemRepo) {
		this.menstrualProblemRepo = menstrualProblemRepo;
	}

	@Autowired
	public void setMusculoskeletalRepo(MusculoskeletalRepo musculoskeletalRepo) {
		this.musculoskeletalRepo = musculoskeletalRepo;
	}

	// @Autowired
	// public void
	// setPostpartumComplicationTypesRepo(PostpartumComplicationTypesRepo
	// postpartumComplicationTypesRepo) {
	// this.postpartumComplicationTypesRepo = postpartumComplicationTypesRepo;
	// }
	//
	// @Autowired
	// public void setPregComplicationTypesRepo(PregComplicationTypesRepo
	// pregComplicationTypesRepo) {
	// this.pregComplicationTypesRepo = pregComplicationTypesRepo;
	// }

	@Autowired
	public void setPregDurationRepo(PregDurationRepo pregDurationRepo) {
		this.pregDurationRepo = pregDurationRepo;
	}

	@Autowired
	public void setSurgeryTypesRepo(SurgeryTypesRepo surgeryTypesRepo) {
		this.surgeryTypesRepo = surgeryTypesRepo;
	}

	@Autowired
	public void setChiefComplaintMasterRepo(ChiefComplaintMasterRepo chiefComplaintMasterRepo) {
		this.chiefComplaintMasterRepo = chiefComplaintMasterRepo;
	}

	@Autowired
	public void setFamilyMemberMasterRepo(FamilyMemberMasterRepo familyMemberMasterRepo) {
		this.familyMemberMasterRepo = familyMemberMasterRepo;
	}

	// @Autowired
	// public void setLabTestMasterRepo(LabTestMasterRepo labTestMasterRepo) {
	// this.labTestMasterRepo = labTestMasterRepo;
	// }

	@Autowired
	public void setDrugDoseMasterRepo(DrugDoseMasterRepo drugDoseMasterRepo) {
		this.drugDoseMasterRepo = drugDoseMasterRepo;
	}

	@Autowired
	public void setDrugDurationUnitMasterRepo(DrugDurationUnitMasterRepo drugDurationUnitMasterRepo) {
		this.drugDurationUnitMasterRepo = drugDurationUnitMasterRepo;
	}

	// @Autowired
	// public void setDrugFormMasterRepo(DrugFormMasterRepo drugFormMasterRepo) {
	// this.drugFormMasterRepo = drugFormMasterRepo;
	// }

	@Autowired
	public void setDrugFrequencyMasterRepo(DrugFrequencyMasterRepo drugFrequencyMasterRepo) {
		this.drugFrequencyMasterRepo = drugFrequencyMasterRepo;
	}

	@Autowired
	public void setComorbidConditionRepo(ComorbidConditionRepo comorbidConditionRepo) {
		this.comorbidConditionRepo = comorbidConditionRepo;
	}

	@Autowired
	public void setCompFeedsRepo(CompFeedsRepo compFeedsRepo) {
		this.compFeedsRepo = compFeedsRepo;
	}

	@Autowired
	public void setFundalHeightRepo(FundalHeightRepo fundalHeightRepo) {
		this.fundalHeightRepo = fundalHeightRepo;
	}

	@Autowired
	public void setGrossMotorMilestoneRepo(GrossMotorMilestoneRepo grossMotorMilestoneRepo) {
		this.grossMotorMilestoneRepo = grossMotorMilestoneRepo;
	}

	@Autowired
	public void setServiceMasterRepo(ServiceMasterRepo serviceMasterRepo) {
		this.serviceMasterRepo = serviceMasterRepo;
	}

	@Autowired
	public void setCounsellingTypeRepo(CounsellingTypeRepo counsellingTypeRepo) {
		this.counsellingTypeRepo = counsellingTypeRepo;
	}

	@Autowired
	public void setInstituteRepo(InstituteRepo instituteRepo) {
		this.instituteRepo = instituteRepo;
	}

	@Autowired
	public void setPersonalHabitTypeRepo(PersonalHabitTypeRepo personalHabitTypeRepo) {
		this.personalHabitTypeRepo = personalHabitTypeRepo;
	}

	// @Autowired
	// public void setPostNatalComplicationRepo(PostNatalComplicationRepo
	// postNatalComplicationRepo) {
	// this.postNatalComplicationRepo = postNatalComplicationRepo;
	// }

	@Autowired
	public void setPregOutcomeRepo(PregOutcomeRepo pregOutcomeRepo) {
		this.pregOutcomeRepo = pregOutcomeRepo;
	}

	@Autowired
	public void setDiseaseTypeRepo(DiseaseTypeRepo diseaseTypeRepo) {
		this.diseaseTypeRepo = diseaseTypeRepo;
	}

	// @Autowired
	// public void setNewBornComplicationRepo(NewBornComplicationRepo
	// newBornComplicationRepo) {
	// this.newBornComplicationRepo = newBornComplicationRepo;
	// }

	@Autowired
	public void setComplicationTypesRepo(ComplicationTypesRepo complicationTypesRepo) {
		this.complicationTypesRepo = complicationTypesRepo;
	}

	@Autowired
	private ServiceFacilityMasterRepo serviceFacilityMasterRepo;

	@Autowired
	private DeliveryConductedByMasterRepo deliveryConductedByMasterRepo;

	@Autowired
	private FoodIntoleranceStatusRepo foodIntoleranceStatusRepo;
	
	@Autowired
	private SubVisitCategoryRepo subVisitCategoryRepo;

	public String getCommonNurseMasterDataForGenopdAncNcdcarePnc(Integer visitCategoryID, Integer providerServiceMapID,
			String gender) {
		Map<String, Object> resMap = new HashMap<String, Object>();

		// ArrayList<Object[]> allergicReactionTypes =
		// allergicReactionTypesRepo.getAllergicReactionTypes();

		ArrayList<Object[]> bloodGroups = bloodGroupsRepo.getBloodGroups();
		ArrayList<Object[]> childVaccinations = childVaccinationsRepo.getChildVaccinations();

		ArrayList<Object[]> deliveryPlace = deliveryPlaceRepo.getDeliveryPlaces();
		ArrayList<Object[]> deliveryType = deliveryTypeRepo.getDeliveryTypes();
		ArrayList<Object[]> developmentProblems = developmentProblemsRepo.getDevelopmentProblems();
		ArrayList<Object[]> gestation = gestationRepo.getGestationTypes();
		// # illness history
		ArrayList<Object[]> illnessTypes = illnessTypesRepo.getIllnessTypes(visitCategoryID);
		ArrayList<Object[]> jointTypes = jointTypesRepo.getJointTypes();
		ArrayList<Object[]> menstrualCycleLengths = menstrualCycleRangeRepo.getMenstrualCycleRanges("Cycle Length");
		ArrayList<Object[]> menstrualCycleBloodFlowDuration = menstrualCycleRangeRepo
				.getMenstrualCycleRanges(" Blood Flow Duration");
		ArrayList<Object[]> menstrualCycleStatus = menstrualCycleStatusRepo.getMenstrualCycleStatuses(visitCategoryID);
		ArrayList<Object[]> menstrualProblem = menstrualProblemRepo.getMenstrualProblems();
		ArrayList<Object[]> musculoskeletalLateralityTypes = musculoskeletalRepo.getMusculoskeletalvalues("Laterality");
		ArrayList<Object[]> musculoskeletalAbnormalityTypes = musculoskeletalRepo
				.getMusculoskeletalvalues("Abnormality");

		ArrayList<Object[]> pregDuration = pregDurationRepo.getPregDurationTypes();
		// # surgery history
		ArrayList<Object[]> surgeryTypes = surgeryTypesRepo.getSurgeryTypes(visitCategoryID, gender);
		// # comorbidity history
		ArrayList<Object[]> comorbidConditions = comorbidConditionRepo.getComorbidConditions(visitCategoryID);
		ArrayList<Object[]> grossMotorMilestones = grossMotorMilestoneRepo.getGrossMotorMilestones();
		ArrayList<Object[]> fundalHeights = fundalHeightRepo.getFundalHeights();
		ArrayList<Object[]> feedTypes = compFeedsRepo.getCompFeeds("Feed Type");
		ArrayList<Object[]> compFeedAges = compFeedsRepo.getCompFeeds("Comp Feed Age");
		ArrayList<Object[]> compFeedServings = compFeedsRepo.getCompFeeds("Comp Feed Serving ");

		ArrayList<Object[]> pregOutcomes = pregOutcomeRepo.getPregOutcomes();

		// complications - moved to m_complication table
		ArrayList<Object[]> birthComplications = complicationTypesRepo.getComplicationTypes("Birth Complication");
		ArrayList<Object[]> deliveryComplicationTypes = complicationTypesRepo
				.getComplicationTypes("Delivery Complication");
		ArrayList<Object[]> postpartumComplicationTypes = complicationTypesRepo
				.getComplicationTypes("Postpartum Complication");
		ArrayList<Object[]> pregComplicationTypes = complicationTypesRepo
				.getComplicationTypes("Pregnancy Complication");
		ArrayList<Object[]> postNatalComplications = complicationTypesRepo
				.getComplicationTypes("Postnatal Complication");

		// newely added masters, 10-07-2020
		ArrayList<Object[]> typeOfAbbortion = complicationTypesRepo.getComplicationTypes("typeOfAbortion");
		ArrayList<Object[]> postAbortionComplications = complicationTypesRepo
				.getComplicationTypes("PostAbortionComplications");

		ArrayList<ServiceFacilityMaster> serviceFacility = serviceFacilityMasterRepo.findByDeleted(false);

		resMap.put("typeOfAbortion", ComplicationTypes.getComplicationTypes(typeOfAbbortion, 0));
		resMap.put("postAbortionComplications", ComplicationTypes.getComplicationTypes(postAbortionComplications, 0));
		resMap.put("serviceFacilities", serviceFacility);

		// existing
		// ArrayList<Object[]> ccList =
		// chiefComplaintMasterRepo.getChiefComplaintMaster();

		ArrayList<Object[]> DiseaseTypes = diseaseTypeRepo.getDiseaseTypes();

		ArrayList<Object[]> tobaccoUseStatus = personalHabitTypeRepo.getPersonalHabitTypeMaster("Tobacco Use Status");
		ArrayList<Object[]> typeOfTobaccoProducts = personalHabitTypeRepo
				.getPersonalHabitTypeMaster("Type of Tobacco Use");
		ArrayList<Object[]> alcoholUseStatus = personalHabitTypeRepo
				.getPersonalHabitTypeMaster("Alcohol Intake Status");
		ArrayList<Object[]> typeOfAlcoholProducts = personalHabitTypeRepo.getPersonalHabitTypeMaster("Type of Alcohol");
		ArrayList<Object[]> frequencyOfAlcoholIntake = personalHabitTypeRepo
				.getPersonalHabitTypeMaster("Frequency of Alcohol Intake");
		ArrayList<Object[]> quantityOfAlcoholIntake = personalHabitTypeRepo
				.getPersonalHabitTypeMaster("Average Quantity of Alcohol consumption");
		ArrayList<Object[]> familyMemberTypes = familyMemberMasterRepo.getFamilyMemberTypeMaster();
		// ArrayList<Object[]> labTests = labTestMasterRepo.getLabTestMaster();
		ArrayList<Object[]> procedures = procedureRepo.getProcedureMasterData(providerServiceMapID, gender);

		// PNC specific master data
		if (visitCategoryID == 5) {
			ArrayList<Object[]> healthStatuses = newbornHealthStatusRepo.getnewBornHealthStatuses();
			resMap.put("newbornHealthStatuses", NewbornHealthStatus.getNewbornHealthStatuses(healthStatuses));
		}

		ArrayList<Object[]> optionalVaccinations = optionalVaccinationsRepo.getOptionalVaccinations();

		// deliveryConductedByMasterRepo
		resMap.put("deliveryConductedByMaster", deliveryConductedByMasterRepo.findByDeleted(false));

//		resMap.put("AllergicReactionTypes", AllergicReactionTypes.getAllergicReactionTypes(allergicReactionTypes));
		resMap.put("AllergicReactionTypes", allergicReactionTypesRepo.findByDeleted(false));
		resMap.put("bloodGroups", bloodGroupsRepo.findByDeleted(false));
		resMap.put("childVaccinations", childVaccinationsRepo.findByDeletedOrderByVaccinationTime(false));
		resMap.put("deliveryPlaces", deliveryPlaceRepo.findByDeleted(false));
		resMap.put("deliveryTypes", deliveryTypeRepo.findByDeleted(false));

		resMap.put("developmentProblems", DevelopmentProblems.getDevelopmentProblems(developmentProblems));
		resMap.put("gestation", Gestation.getGestations(gestation));
		resMap.put("illnessTypes", IllnessTypes.getIllnessTypes(illnessTypes));
		resMap.put("jointTypes", JointTypes.getJointTypes(jointTypes));
		resMap.put("menstrualCycleLengths", MenstrualCycleRange.getMenstrualCycleRanges(menstrualCycleLengths));
		resMap.put("menstrualCycleBloodFlowDuration",
				MenstrualCycleRange.getMenstrualCycleRanges(menstrualCycleBloodFlowDuration));
		resMap.put("menstrualCycleStatus", MenstrualCycleStatus.getMenstrualCycleStatuses(menstrualCycleStatus));
		resMap.put("menstrualProblem", MenstrualProblem.getMenstrualProblems(menstrualProblem));
		resMap.put("musculoskeletalLateralityTypes",
				Musculoskeletal.getMusculoskeletals(musculoskeletalLateralityTypes));
		resMap.put("musculoskeletalAbnormalityTypes",
				Musculoskeletal.getMusculoskeletals(musculoskeletalAbnormalityTypes));
		resMap.put("pregDuration", PregDuration.getPregDurationValues(pregDuration));
		resMap.put("surgeryTypes", SurgeryTypes.getSurgeryTypes(surgeryTypes));
		resMap.put("comorbidConditions", ComorbidCondition.getComorbidConditions(comorbidConditions));
		resMap.put("grossMotorMilestones", GrossMotorMilestone.getGrossMotorMilestone(grossMotorMilestones));
		resMap.put("fundalHeights", FundalHeight.getFundalHeights(fundalHeights));
		resMap.put("feedTypes", CompFeeds.getCompFeeds(feedTypes));
		resMap.put("compFeedAges", CompFeeds.getCompFeeds(compFeedAges));
		resMap.put("compFeedServings", CompFeeds.getCompFeeds(compFeedServings));
		resMap.put("pregOutcomes", PregOutcome.getPregOutcomes(pregOutcomes));

		resMap.put("birthComplications", ComplicationTypes.getComplicationTypes(birthComplications, 0));
		resMap.put("deliveryComplicationTypes", ComplicationTypes.getComplicationTypes(deliveryComplicationTypes, 2));
		resMap.put("postpartumComplicationTypes",
				ComplicationTypes.getComplicationTypes(postpartumComplicationTypes, 3));
		resMap.put("pregComplicationTypes", ComplicationTypes.getComplicationTypes(pregComplicationTypes, 1));
		resMap.put("postNatalComplications", ComplicationTypes.getComplicationTypes(postNatalComplications, 0));
		resMap.put("newBornComplications", ComplicationTypes.getComplicationTypes(birthComplications, 0));

		// existing
		// resMap.put("chiefComplaintMaster",
		// ChiefComplaintMaster.getChiefComplaintMasters(ccList));
		resMap.put("chiefComplaintMaster", chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(false));

		resMap.put("DiseaseTypes", DiseaseType.getDiseaseTypes(DiseaseTypes));
		resMap.put("tobaccoUseStatus", PersonalHabitType.getPersonalHabitTypeMasterData(tobaccoUseStatus));
		resMap.put("typeOfTobaccoProducts", PersonalHabitType.getPersonalHabitTypeMasterData(typeOfTobaccoProducts));
		resMap.put("alcoholUseStatus", PersonalHabitType.getPersonalHabitTypeMasterData(alcoholUseStatus));
		resMap.put("typeOfAlcoholProducts", PersonalHabitType.getPersonalHabitTypeMasterData(typeOfAlcoholProducts));
		resMap.put("frequencyOfAlcoholIntake",
				PersonalHabitType.getPersonalHabitTypeMasterData(frequencyOfAlcoholIntake));
		resMap.put("quantityOfAlcoholIntake",
				PersonalHabitType.getPersonalHabitTypeMasterData(quantityOfAlcoholIntake));
		resMap.put("familyMemberTypes", FamilyMemberType.getFamilyMemberTypeMasterData(familyMemberTypes));

		resMap.put("procedures", ProcedureData.getProcedures(procedures));
		resMap.put("vaccineMasterData", OptionalVaccinations.getOptionalVaccinations(optionalVaccinations));

		resMap.put("foodIntoleranceStatus", foodIntoleranceStatusRepo.findByDeletedOrderByIntoleranceStatus(false));

		if (visitCategoryID == 8 || visitCategoryID == 10) {
			resMap.put("covidSymptomsMaster", covidSymptomsMasterRepo.findByDeleted(false));
			resMap.put("covidContactHistoryMaster", covidContactHistoryMasterRepo.findByDeleted(false));
			resMap.put("covidRecommendationMaster", covidRecommnedationMasterRepo.findByDeleted(false));
		}

		// To Fetch Foetal Monitor Test Master Details
		if (visitCategoryID == 4) {
			ArrayList<Object[]> fetoTestList = foetalMonitorTestRepo.getFoetalMonitorTestsDetails(providerServiceMapID);
			resMap.put("fetosenseTestMaster", FoetalMonitorTestMaster.getFoetalMonitorMasters(fetoTestList));
		}
		
		//To fetch sub visit category master details for general opd
		if (visitCategoryID == 6) {
			resMap.put("subVisitCategories",subVisitCategoryRepo.findByDeletedOrderByName(false));
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		return gson.toJson(resMap);
	}

	@Autowired
	private CounsellingProvidedMasterRepo counsellingProvidedMasterRepo;
	@Autowired
	private NextDueVaccinesMasterRepo nextDueVaccinesMasterRepo;
	@Autowired
	private NextImmunizationLocationMasterRepo nextImmunizationLocationMasterRepo;

	public String getCommonDoctorMasterDataForGenopdAncNcdcarePnc(Integer visitCategoryID, int psmID, String gender,
			Integer facilityID, Integer vanID) {
		Map<String, Object> resMap = new HashMap<>();

		if (visitCategoryID != 7) {
			ArrayList<Object[]> counsellingTypes = counsellingTypeRepo.getCounsellingTypes();
			ArrayList<Object[]> additionalServices = serviceMasterRepo.getAdditionalServices();
			// Institute institute = new Institute();

			ArrayList<Object[]> instituteDetails = instituteRepo.getInstituteDetails(psmID);
			resMap.put("counsellingTypes", CounsellingType.getCounsellingType(counsellingTypes));
			resMap.put("higherHealthCare", Institute.getinstituteDetails(instituteDetails));
			resMap.put("additionalServices", ServiceMaster.getServiceMaster(additionalServices));

		} else {
			ArrayList<Object[]> procedures = procedureRepo.getProcedureMasterData(psmID, gender);
			ArrayList<Object[]> ccList = chiefComplaintMasterRepo.getChiefComplaintMaster();

			resMap.put("procedures", ProcedureData.getProcedures(procedures));
			resMap.put("chiefComplaintMaster", ChiefComplaintMaster.getChiefComplaintMasters(ccList));
		}

		ArrayList<Object[]> ifmList = itemFormMasterRepo.getItemFormMaster();
		ArrayList<Object[]> ddmList = drugDoseMasterRepo.getDrugDoseMaster();
		ArrayList<Object[]> ddumList = drugDurationUnitMasterRepo.getDrugDurationUnitMaster();
		ArrayList<Object[]> dfrmList = drugFrequencyMasterRepo.getDrugFrequencyMaster();
		ArrayList<Object[]> roaList = routeOfAdminRepo.getRouteOfAdminList();
		// ArrayList<Object[]> edlList=itemMasterRepo.searchEdl(psmID);
		ArrayList<ItemMaster> NonedlList = itemMasterRepo.searchEdl(psmID);
		// edlist.get()
		// foreach()
		for (int i = 0; i < NonedlList.size(); i++) {
			if (NonedlList.get(i).getUom() != null)
				NonedlList.get(i).setUnitOfMeasurement(NonedlList.get(i).getUom().getuOMName());
		}
		ArrayList<V_DrugPrescription> itemList = new ArrayList<>();
		if (facilityID == null || facilityID <= 0) {
			Integer fID = masterVanRepo.getFacilityID(vanID);
			if (fID != null && fID > 0)
				facilityID = fID;
		}

		itemList = v_DrugPrescriptionRepo.getItemListForFacility(facilityID);
		// ArrayList<ItemMaster> edlList=new ArrayList<>();
		// edlList=itemMasterRepo.findByEdl();
		resMap.put("drugFormMaster", ItemFormMaster.getItemFormList(ifmList));
		resMap.put("drugDoseMaster", DrugDoseMaster.getDrugDoseMasters(ddmList));
		resMap.put("drugDurationUnitMaster", DrugDurationUnitMaster.getDrugDurationUnitMaster(ddumList));
		resMap.put("drugFrequencyMaster", DrugFrequencyMaster.getDrugFrequencyMaster(dfrmList));
		resMap.put("routeOfAdmin", RouteOfAdmin.getRouteOfAdminList(roaList));
		resMap.put("itemMaster", itemList);
		resMap.put("NonEdlMaster", NonedlList);
		// NCD Care specific master data
		if (visitCategoryID == 3) {
			resMap.put("ncdCareConditions", NCDScreeningCondition.getNCDScreeningCondition(
					(ArrayList<Object[]>) ncdScreeningMasterServiceImpl.getNCDScreeningConditions()));
			resMap.put("ncdCareTypes",
					NCDCareType.getNCDCareTypes((ArrayList<Object[]>) ncdCareTypeRepo.getNCDCareTypes()));
		}

		resMap.put("referralReasonList",
				referralReasonRepo.findByDeletedAndVisitCategoryIdOrderByName(false, visitCategoryID));

		resMap.put("counsellingProvided", counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(false, visitCategoryID));
		resMap.put("nextDueVaccines", nextDueVaccinesMasterRepo.findByDeletedOrderById(false));
		resMap.put("nextImmuLocations", nextImmunizationLocationMasterRepo.findByDeletedOrderByName(false));

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		return gson.toJson(resMap);

	}

}
