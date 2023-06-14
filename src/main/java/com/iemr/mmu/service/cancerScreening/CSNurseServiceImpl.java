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
package com.iemr.mmu.service.cancerScreening;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.mmu.data.doctor.CancerAbdominalExamination;
import com.iemr.mmu.data.doctor.CancerBreastExamination;
import com.iemr.mmu.data.doctor.CancerExaminationImageAnnotation;
import com.iemr.mmu.data.doctor.CancerGynecologicalExamination;
import com.iemr.mmu.data.doctor.CancerLymphNodeDetails;
import com.iemr.mmu.data.doctor.CancerOralExamination;
import com.iemr.mmu.data.doctor.CancerSignAndSymptoms;
import com.iemr.mmu.data.doctor.WrapperCancerExamImgAnotasn;
import com.iemr.mmu.data.doctor.WrapperCancerSymptoms;
import com.iemr.mmu.data.nurse.BenCancerVitalDetail;
import com.iemr.mmu.data.nurse.BenFamilyCancerHistory;
import com.iemr.mmu.data.nurse.BenObstetricCancerHistory;
import com.iemr.mmu.data.nurse.BenPersonalCancerDietHistory;
import com.iemr.mmu.data.nurse.BenPersonalCancerHistory;
import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.repo.doctor.CancerAbdominalExaminationRepo;
import com.iemr.mmu.repo.doctor.CancerBreastExaminationRepo;
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

@Service
public class CSNurseServiceImpl implements CSNurseService {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private BenFamilyCancerHistoryRepo benFamilyCancerHistoryRepo;
	private BenPersonalCancerHistoryRepo benPersonalCancerHistoryRepo;
	private BenPersonalCancerDietHistoryRepo benPersonalCancerDietHistoryRepo;
	private BenObstetricCancerHistoryRepo benObstetricCancerHistoryRepo;
	private BenCancerVitalDetailRepo benCancerVitalDetailRepo;
	private BenVisitDetailRepo benVisitDetailRepo;

	private CancerAbdominalExaminationRepo cancerAbdominalExaminationRepo;
	private CancerBreastExaminationRepo cancerBreastExaminationRepo;
	private CancerGynecologicalExaminationRepo cancerGynecologicalExaminationRepo;
	private CancerSignAndSymptomsRepo cancerSignAndSymptomsRepo;
	private CancerLymphNodeExaminationRepo cancerLymphNodeExaminationRepo;
	private CancerOralExaminationRepo cancerOralExaminationRepo;
	private CancerExaminationImageAnnotationRepo cancerExaminationImageAnnotationRepo;

	@Autowired
	public void setCancerExaminationImageAnnotationRepo(
			CancerExaminationImageAnnotationRepo cancerExaminationImageAnnotationRepo) {
		this.cancerExaminationImageAnnotationRepo = cancerExaminationImageAnnotationRepo;
	}

	@Autowired
	public void setCancerOralExaminationRepo(CancerOralExaminationRepo cancerOralExaminationRepo) {
		this.cancerOralExaminationRepo = cancerOralExaminationRepo;
	}

	@Autowired
	public void setCancerLymphNodeExaminationRepo(CancerLymphNodeExaminationRepo cancerLymphNodeExaminationRepo) {
		this.cancerLymphNodeExaminationRepo = cancerLymphNodeExaminationRepo;
	}

	@Autowired
	public void setCancerSignAndSymptomsRepo(CancerSignAndSymptomsRepo cancerSignAndSymptomsRepo) {
		this.cancerSignAndSymptomsRepo = cancerSignAndSymptomsRepo;
	}

	@Autowired
	public void setCancerGynecologicalExaminationRepo(
			CancerGynecologicalExaminationRepo cancerGynecologicalExaminationRepo) {
		this.cancerGynecologicalExaminationRepo = cancerGynecologicalExaminationRepo;
	}

	@Autowired
	public void setCancerBreastExaminationRepo(CancerBreastExaminationRepo cancerBreastExaminationRepo) {
		this.cancerBreastExaminationRepo = cancerBreastExaminationRepo;
	}

	@Autowired
	public void setCancerAbdominalExaminationRepo(CancerAbdominalExaminationRepo cancerAbdominalExaminationRepo) {
		this.cancerAbdominalExaminationRepo = cancerAbdominalExaminationRepo;
	}

	@Autowired
	public void setBenPersonalCancerHistoryRepo(BenPersonalCancerHistoryRepo benPersonalCancerHistoryRepo) {
		this.benPersonalCancerHistoryRepo = benPersonalCancerHistoryRepo;
	}

	@Autowired
	public void setBenFamilyCancerHistoryRepo(BenFamilyCancerHistoryRepo benFamilyCancerHistoryRepo) {
		this.benFamilyCancerHistoryRepo = benFamilyCancerHistoryRepo;
	}

	@Autowired
	public void setBenPersonalCancerDietHistoryRepo(BenPersonalCancerDietHistoryRepo benPersonalCancerDietHistoryRepo) {
		this.benPersonalCancerDietHistoryRepo = benPersonalCancerDietHistoryRepo;
	}

	@Autowired
	public void setBenObstetricCancerHistoryRepo(BenObstetricCancerHistoryRepo benObstetricCancerHistoryRepo) {
		this.benObstetricCancerHistoryRepo = benObstetricCancerHistoryRepo;
	}

	@Autowired
	public void setBenCancerVitalDetailRepo(BenCancerVitalDetailRepo benCancerVitalDetailRepo) {
		this.benCancerVitalDetailRepo = benCancerVitalDetailRepo;
	}

	@Autowired
	public void setBenVisitDetailRepo(BenVisitDetailRepo benVisitDetailRepo) {
		this.benVisitDetailRepo = benVisitDetailRepo;
	}

	@Override
	public int saveBenFamilyCancerHistory(List<BenFamilyCancerHistory> benFamilyCancerHistoryList) {
		List<BenFamilyCancerHistory> benFamilyCancerHistoryListFinal = new ArrayList<>();
		for (BenFamilyCancerHistory benFamilyCancerHistoryOBJ : benFamilyCancerHistoryList) {
			List<String> familyMenberList = benFamilyCancerHistoryOBJ.getFamilyMemberList();
			String familyMemberData = "";
			if (familyMenberList != null && familyMenberList.size() > 0) {
				int i = 1;
				for (String familyMember : familyMenberList) {
					if (i == familyMenberList.size()) {
						familyMemberData += familyMember;
					} else {
						familyMemberData += familyMember + ",";
					}

					i++;
				}
				benFamilyCancerHistoryOBJ.setFamilyMember(familyMemberData);
				benFamilyCancerHistoryListFinal.add(benFamilyCancerHistoryOBJ);
			}

			// System.out.println("hello...");
		}

		int responseData = 0;
		List<BenFamilyCancerHistory> response = (List<BenFamilyCancerHistory>) benFamilyCancerHistoryRepo
				.save(benFamilyCancerHistoryListFinal);
		if (benFamilyCancerHistoryListFinal.size() == response.size())
			responseData = 1;
		return responseData;
	}

	@Override
	public Long saveBenPersonalCancerHistory(BenPersonalCancerHistory benPersonalCancerHistory) {
		benPersonalCancerHistory = getBenPersonalHistoryOBJ(benPersonalCancerHistory);
		BenPersonalCancerHistory response = benPersonalCancerHistoryRepo.save(benPersonalCancerHistory);
		if (response != null)
			return response.getID();
		else
			return null;
	}

	public BenPersonalCancerHistory getBenPersonalHistoryOBJ(BenPersonalCancerHistory benPersonalCancerHistory) {
		List<String> typeOfTobaccoProductUseList = benPersonalCancerHistory.getTypeOfTobaccoProductList();
		String typeOfTobaccoProductUseConcat = "";
		if (typeOfTobaccoProductUseList != null && typeOfTobaccoProductUseList.size() > 0) {
			for (String s : typeOfTobaccoProductUseList) {
				typeOfTobaccoProductUseConcat += s + ",";
			}
		}
		benPersonalCancerHistory.setTypeOfTobaccoProduct(typeOfTobaccoProductUseConcat);
		return benPersonalCancerHistory;
	}

	@Override
	public Long saveBenPersonalCancerDietHistory(BenPersonalCancerDietHistory benPersonalCancerDietHistory) {
		benPersonalCancerDietHistory = getBenPersonalCancerDietOBJ(benPersonalCancerDietHistory);
		BenPersonalCancerDietHistory response = benPersonalCancerDietHistoryRepo.save(benPersonalCancerDietHistory);
		if (response != null)
			return response.getID();
		else
			return null;
	}

	public BenPersonalCancerDietHistory getBenPersonalCancerDietOBJ(
			BenPersonalCancerDietHistory benPersonalCancerDietHistory) {
		List<String> personalOilConsumedList = benPersonalCancerDietHistory.getTypeOfOilConsumedList();
		String oilConsumedData = "";
		if (personalOilConsumedList != null && personalOilConsumedList.size() > 0) {
			for (String s : personalOilConsumedList) {
				oilConsumedData += s + ",";
			}
		}
		benPersonalCancerDietHistory.setTypeOfOilConsumed(oilConsumedData);

		return benPersonalCancerDietHistory;
	}

	@Override
	public Long saveBenObstetricCancerHistory(BenObstetricCancerHistory benObstetricCancerHistory) {
		BenObstetricCancerHistory response = benObstetricCancerHistoryRepo.save(benObstetricCancerHistory);
		if (response != null)
			return response.getID();
		else
			return null;
	}

	@Override
	public Long saveBenVitalDetail(BenCancerVitalDetail benCancerVitalDetail) {
		BenCancerVitalDetail response = benCancerVitalDetailRepo.save(benCancerVitalDetail);
		if (response != null)
			return response.getID();
		else
			return null;
	}

	@Override
	public int updateBeneficiaryFamilyCancerHistory(List<BenFamilyCancerHistory> benFamilyCancerHistoryList) {
		int response = 0;
		int delRes = 0;
		try {
			// if (benFamilyCancerHistoryList.size() > 0) {

			ArrayList<Object[]> benFamilyCancerHistoryStatuses = benFamilyCancerHistoryRepo
					.getFamilyCancerHistoryStatus(benFamilyCancerHistoryList.get(0).getBeneficiaryRegID(),
							benFamilyCancerHistoryList.get(0).getVisitCode());

			if (benFamilyCancerHistoryStatuses != null && benFamilyCancerHistoryStatuses.size() > 0) {
				for (Object[] obj : benFamilyCancerHistoryStatuses) {
					String processed = (String) obj[1];
					if (null != processed && !processed.equalsIgnoreCase("N")) {
						processed = "U";
					} else {
						processed = "N";
					}
					delRes = benFamilyCancerHistoryRepo.deleteExistingFamilyRecord((Long) obj[0], processed);
				}
			} else {
				delRes = 1;
			}

			// }
			ArrayList<BenFamilyCancerHistory> newbenFamilyCancerHistoryList = new ArrayList<BenFamilyCancerHistory>();
			if (delRes > 0) {
				for (BenFamilyCancerHistory benFamilyCancerHistory : benFamilyCancerHistoryList) {
					List<String> familyMenberList = benFamilyCancerHistory.getFamilyMemberList();
					if (null != familyMenberList && !familyMenberList.isEmpty() && familyMenberList.size() > 0) {
						String familyMemberData = "";
						for (String familyMember : familyMenberList) {
							familyMemberData += familyMember + ",";
						}
						benFamilyCancerHistory.setFamilyMember(familyMemberData);
						benFamilyCancerHistory.setCreatedBy(benFamilyCancerHistory.getModifiedBy());
						newbenFamilyCancerHistoryList.add(benFamilyCancerHistory);
					}

				}
				if (newbenFamilyCancerHistoryList.size() > 0) {
					ArrayList<BenFamilyCancerHistory> benFamilyCancerHistories = (ArrayList<BenFamilyCancerHistory>) benFamilyCancerHistoryRepo
							.save(newbenFamilyCancerHistoryList);
					if (benFamilyCancerHistories.size() > 0) {
						response = benFamilyCancerHistories.size();
					}
				} else {
					response = 1;
				}
			} else {
				response = 0;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());

		}
		return response;

	}

	@Override
	public int updateBenObstetricCancerHistory(BenObstetricCancerHistory benObstetricCancerHistory) {
		int response = 0;
		int recordsAvailable = 0;
		String processed = benObstetricCancerHistoryRepo.getObstetricCancerHistoryStatus(
				benObstetricCancerHistory.getBeneficiaryRegID(), benObstetricCancerHistory.getVisitCode());
		if (null != processed) {
			recordsAvailable = 1;
		}
		if (null != processed && !processed.equalsIgnoreCase("N")) {
			processed = "U";
		} else {
			processed = "N";
		}
		try {
			benObstetricCancerHistory.setProcessed(processed);
			if (recordsAvailable == 1) {
				response = updateBenObstetricHistory(benObstetricCancerHistory);
			} else {
				benObstetricCancerHistory.setCreatedBy(benObstetricCancerHistory.getModifiedBy());
				Long saveRes = saveBenObstetricCancerHistory(benObstetricCancerHistory);
				if (null != saveRes && saveRes > 0) {
					response = 1;
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());

		}
		return response;

	}

	public int updateBenObstetricHistory(BenObstetricCancerHistory benObstetricCancerHistory) {
		int response = benObstetricCancerHistoryRepo.updateBenObstetricCancerHistory(
				benObstetricCancerHistory.getProviderServiceMapID(), benObstetricCancerHistory.getPregnancyStatus(),
				benObstetricCancerHistory.getIsUrinePregTest(), benObstetricCancerHistory.getPregnant_No(),
				benObstetricCancerHistory.getNoOfLivingChild(), benObstetricCancerHistory.getIsAbortion(),
				benObstetricCancerHistory.getIsOralContraceptiveUsed(),
				benObstetricCancerHistory.getIsHormoneReplacementTherapy(), benObstetricCancerHistory.getMenarche_Age(),
				benObstetricCancerHistory.getIsMenstrualCycleRegular(),
				benObstetricCancerHistory.getMenstrualCycleLength(),
				benObstetricCancerHistory.getMenstrualFlowDuration(), benObstetricCancerHistory.getMenstrualFlowType(),
				benObstetricCancerHistory.getIsDysmenorrhea(), benObstetricCancerHistory.getIsInterMenstrualBleeding(),
				benObstetricCancerHistory.getMenopauseAge(), benObstetricCancerHistory.getIsPostMenopauseBleeding(),
				benObstetricCancerHistory.getIsFoulSmellingDischarge(), benObstetricCancerHistory.getModifiedBy(),
				benObstetricCancerHistory.getBeneficiaryRegID(), benObstetricCancerHistory.getVisitCode(),
				benObstetricCancerHistory.getProcessed());
		return response;
	}

	@Override
	public int updateBenPersonalCancerHistory(BenPersonalCancerHistory benPersonalCancerHistory) {
		int response = 0;
		int recordsAvailable = 0;
		String processed = benPersonalCancerHistoryRepo.getPersonalCancerHistoryStatus(
				benPersonalCancerHistory.getBeneficiaryRegID(), benPersonalCancerHistory.getVisitCode());
		if (null != processed) {
			recordsAvailable = 1;
		}
		if (null != processed && !processed.equalsIgnoreCase("N")) {
			processed = "U";
		} else {
			processed = "N";
		}

		try {
			benPersonalCancerHistory.setProcessed(processed);
			if (recordsAvailable == 1) {
				response = updateBenPersonalHistory(benPersonalCancerHistory);
			} else {
				benPersonalCancerHistory.setCreatedBy(benPersonalCancerHistory.getModifiedBy());
				Long saveRes = saveBenPersonalCancerHistory(benPersonalCancerHistory);
				if (null != saveRes && saveRes > 0) {
					response = 1;
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());

		}
		return response;

	}

	public int updateBenPersonalHistory(BenPersonalCancerHistory benPersonalCancerHistory) {
		benPersonalCancerHistory = getBenPersonalHistoryOBJ(benPersonalCancerHistory);
		int response = benPersonalCancerHistoryRepo.updateBenPersonalCancerHistory(
				benPersonalCancerHistory.getProviderServiceMapID(), benPersonalCancerHistory.getTobaccoUse(),
				benPersonalCancerHistory.getStartAge_year(), benPersonalCancerHistory.getEndAge_year(),
				benPersonalCancerHistory.getTypeOfTobaccoProduct(), benPersonalCancerHistory.getQuantityPerDay(),
				benPersonalCancerHistory.getIsFilteredCigaerette(), benPersonalCancerHistory.getIsCigaretteExposure(),
				benPersonalCancerHistory.getIsBetelNutChewing(), benPersonalCancerHistory.getDurationOfBetelQuid(),
				benPersonalCancerHistory.getAlcoholUse(), benPersonalCancerHistory.getSsAlcoholUsed(),
				benPersonalCancerHistory.getFrequencyOfAlcoholUsed(), benPersonalCancerHistory.getModifiedBy(),
				benPersonalCancerHistory.getBeneficiaryRegID(), benPersonalCancerHistory.getVisitCode(),
				benPersonalCancerHistory.getProcessed());
		return response;
	}

	@Override
	public int updateBenPersonalCancerDietHistory(BenPersonalCancerDietHistory benPersonalCancerDietHistory) {
		int response = 0;
		int recordsAvailable = 0;
		String processed = benPersonalCancerDietHistoryRepo.getPersonalCancerDietHistoryStatus(
				benPersonalCancerDietHistory.getBeneficiaryRegID(), benPersonalCancerDietHistory.getVisitCode());
		if (null != processed) {
			recordsAvailable = 1;
		}
		if (null != processed && !processed.equalsIgnoreCase("N")) {
			processed = "U";
		} else {
			processed = "N";
		}

		try {
			benPersonalCancerDietHistory.setProcessed(processed);
			if (recordsAvailable == 1) {
				response = updateBenPersonalDietHistory(benPersonalCancerDietHistory);
			} else {
				benPersonalCancerDietHistory.setCreatedBy(benPersonalCancerDietHistory.getModifiedBy());
				Long saveRes = saveBenPersonalCancerDietHistory(benPersonalCancerDietHistory);
				if (null != saveRes && saveRes > 0) {
					response = 1;
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());

		}
		return response;

	}

	public int updateBenPersonalDietHistory(BenPersonalCancerDietHistory benPersonalCancerDietHistory) {
		benPersonalCancerDietHistory = getBenPersonalCancerDietOBJ(benPersonalCancerDietHistory);

		int response = benPersonalCancerDietHistoryRepo.updateBenPersonalCancerDietHistory(
				benPersonalCancerDietHistory.getProviderServiceMapID(), benPersonalCancerDietHistory.getDietType(),
				benPersonalCancerDietHistory.getFruitConsumptionDays(),
				benPersonalCancerDietHistory.getFruitQuantityPerDay(),
				benPersonalCancerDietHistory.getVegetableConsumptionDays(),
				benPersonalCancerDietHistory.getVegetableQuantityPerDay(),
				benPersonalCancerDietHistory.getIntakeOfOutsidePreparedMeal(),
				benPersonalCancerDietHistory.getTypeOfOilConsumed(),
				benPersonalCancerDietHistory.getPhysicalActivityType(),
				benPersonalCancerDietHistory.getSsRadiationExposure(),
				benPersonalCancerDietHistory.getIsThyroidDisorder(), benPersonalCancerDietHistory.getModifiedBy(),
				benPersonalCancerDietHistory.getBeneficiaryRegID(), benPersonalCancerDietHistory.getVisitCode(),
				benPersonalCancerDietHistory.getProcessed());
		return response;
	}

	@Override
	public int updateBenVitalDetail(BenCancerVitalDetail benCancerVitalDetail) {
		int response = 0;
		int i = 0;
		String processed = benCancerVitalDetailRepo.getCancerVitalStatus(benCancerVitalDetail.getBeneficiaryRegID(),
				benCancerVitalDetail.getVisitCode());
		if (processed != null) {
			i = 1;
		}
		if (null != processed && !processed.equalsIgnoreCase("N")) {
			processed = "U";
		} else {
			processed = "N";
		}
		if (i > 0) {
			response = benCancerVitalDetailRepo.updateBenCancerVitalDetail(
					benCancerVitalDetail.getProviderServiceMapID(), benCancerVitalDetail.getWeight_Kg(),
					benCancerVitalDetail.getHeight_cm(), benCancerVitalDetail.getWaistCircumference_cm(),
					benCancerVitalDetail.getBloodGlucose_Fasting(), benCancerVitalDetail.getBloodGlucose_Random(),
					benCancerVitalDetail.getBloodGlucose_2HrPostPrandial(),
					benCancerVitalDetail.getSystolicBP_1stReading(), benCancerVitalDetail.getDiastolicBP_1stReading(),
					benCancerVitalDetail.getSystolicBP_2ndReading(), benCancerVitalDetail.getDiastolicBP_2ndReading(),
					benCancerVitalDetail.getSystolicBP_3rdReading(), benCancerVitalDetail.getDiastolicBP_3rdReading(),
					benCancerVitalDetail.getHbA1C(), benCancerVitalDetail.getHemoglobin(),
					benCancerVitalDetail.getModifiedBy(), benCancerVitalDetail.getBeneficiaryRegID(),
					benCancerVitalDetail.getVisitCode(), processed,
					benCancerVitalDetail.getRbsTestResult(),benCancerVitalDetail.getRbsTestRemarks(),benCancerVitalDetail.getsPO2());
		} else {
			benCancerVitalDetail.setCreatedBy(benCancerVitalDetail.getModifiedBy());
			BenCancerVitalDetail benCancerVitalDetailRS = benCancerVitalDetailRepo.save(benCancerVitalDetail);
			if (benCancerVitalDetailRS != null) {
				response = 1;
			}
		}

		return response;
	}

	@Override
	public List<BenFamilyCancerHistory> getBenFamilyHisData(Long benRegID, Long visitCode) {
		List<BenFamilyCancerHistory> benFamilyCancerHistoryList = benFamilyCancerHistoryRepo
				.getBenFamilyHistory(benRegID, visitCode);
		if (benFamilyCancerHistoryList.size() > 0) {
			for (BenFamilyCancerHistory obj : benFamilyCancerHistoryList) {
				String s = obj.getFamilyMember();
				List<String> famMemlist = new ArrayList<>();
				if (s != null) {
					String[] arr = s.split(",");
					for (int i = 0; i < arr.length; i++) {
						famMemlist.add(arr[i]);
					}
				}
				obj.setFamilyMemberList(famMemlist);
				// System.out.println("hello");
			}
		}

		return benFamilyCancerHistoryList;
	}

	@Override
	public BenObstetricCancerHistory getBenObstetricDetailsData(Long benRegID, Long visitCode) {
		BenObstetricCancerHistory benObstetricCancerHistoryData = benObstetricCancerHistoryRepo
				.getBenObstetricCancerHistory(benRegID, visitCode);
		return benObstetricCancerHistoryData;
	}

	@Override
	public BenPersonalCancerHistory getBenPersonalCancerHistoryData(Long benRegID, Long visitCode) {
		BenPersonalCancerHistory benPersonalCancerHistory = benPersonalCancerHistoryRepo.getBenPersonalHistory(benRegID,
				visitCode);
		if (null != benPersonalCancerHistory) {
			List<String> typeOfTobaccoProductList = new ArrayList<>();
			String s = benPersonalCancerHistory.getTypeOfTobaccoProduct();
			if (s != null) {
				String[] arr = s.split(",");
				for (int i = 0; i < arr.length; i++) {
					typeOfTobaccoProductList.add(arr[i]);
				}
				benPersonalCancerHistory.setTypeOfTobaccoProductList(typeOfTobaccoProductList);

			}
		}
		return benPersonalCancerHistory;
	}

	@Override
	public BenPersonalCancerDietHistory getBenPersonalCancerDietHistoryData(Long benRegID, Long visitCode) {
		BenPersonalCancerDietHistory benPersonalCancerDietHistory = benPersonalCancerDietHistoryRepo
				.getBenPersonaDietHistory(benRegID, visitCode);

		if (null != benPersonalCancerDietHistory) {
			String s = benPersonalCancerDietHistory.getTypeOfOilConsumed();
			List<String> oilConsumedList = new ArrayList<>();
			if (s != null) {
				String[] arr = s.split(",");
				for (int i = 0; i < arr.length; i++) {
					oilConsumedList.add(arr[i]);
				}
			}

			benPersonalCancerDietHistory.setTypeOfOilConsumedList(oilConsumedList);
		}
		return benPersonalCancerDietHistory;
	}

	@Override
	public BenCancerVitalDetail getBenCancerVitalDetailData(Long benRegID, Long visitCode) {
		BenCancerVitalDetail benCancerVitalDetail = benCancerVitalDetailRepo.getBenCancerVitalDetail(benRegID,
				visitCode);
		return benCancerVitalDetail;
	}

	public CancerAbdominalExamination getBenCancerAbdominalExaminationData(Long benRegID, Long visitCode) {
		CancerAbdominalExamination cancerAbdominalExamination = cancerAbdominalExaminationRepo
				.getBenCancerAbdominalExaminationDetails(benRegID, visitCode);
		return cancerAbdominalExamination;
	}

	public CancerBreastExamination getBenCancerBreastExaminationData(Long benRegID, Long visitCode) {
		CancerBreastExamination cancerBreastExamination = cancerBreastExaminationRepo
				.getBenCancerBreastExaminationDetails(benRegID, visitCode);
		return cancerBreastExamination;
	}

	public CancerGynecologicalExamination getBenCancerGynecologicalExaminationData(Long benRegID, Long visitCode) {
		CancerGynecologicalExamination cancerGynecologicalExamination = cancerGynecologicalExaminationRepo
				.getBenCancerGynecologicalExaminationDetails(benRegID, visitCode);
		return cancerGynecologicalExamination;
	}

	public CancerSignAndSymptoms getBenCancerSignAndSymptomsData(Long benRegID, Long visitCode) {
		CancerSignAndSymptoms cancerSignAndSymptoms = cancerSignAndSymptomsRepo
				.getBenCancerSignAndSymptomsDetails(benRegID, visitCode);
		return cancerSignAndSymptoms;
	}

	public List<CancerLymphNodeDetails> getBenCancerLymphNodeDetailsData(Long benRegID, Long visitCode) {
		List<CancerLymphNodeDetails> cancerLymphNodeDetails = cancerLymphNodeExaminationRepo
				.getBenCancerLymphNodeDetails(benRegID, visitCode);
		return cancerLymphNodeDetails;
	}

	public CancerOralExamination getBenCancerOralExaminationData(Long benRegID, Long visitCode) {
		CancerOralExamination cancerOralExamination = cancerOralExaminationRepo
				.getBenCancerOralExaminationDetails(benRegID, visitCode);
		return cancerOralExamination;
	}

	public ArrayList<WrapperCancerExamImgAnotasn> getCancerExaminationImageAnnotationCasesheet(Long benRegID,
			Long visitCode) {
		ArrayList<WrapperCancerExamImgAnotasn> resList = new ArrayList<>();
		// System.out.println("hello");
		List<CancerExaminationImageAnnotation> cancerExaminationImageAnnotationList = cancerExaminationImageAnnotationRepo
				.getCancerExaminationImageAnnotationList(benRegID, visitCode);

		if (cancerExaminationImageAnnotationList.size() > 0) {
			int a = 0;
			int b = 0;

			ArrayList<Map<String, Object>> markerList = new ArrayList<>();
			Map<String, Object> markerMap;

			WrapperCancerExamImgAnotasn wrapperCancerExamImgAnotasnOBJ = null;

			for (CancerExaminationImageAnnotation obj : cancerExaminationImageAnnotationList) {
				markerMap = new HashMap<String, Object>();
				b = obj.getCancerImageID();
				if (a != b) {
					wrapperCancerExamImgAnotasnOBJ = new WrapperCancerExamImgAnotasn();
					wrapperCancerExamImgAnotasnOBJ.setBeneficiaryRegID(benRegID);
					wrapperCancerExamImgAnotasnOBJ.setVisitID(obj.getBenVisitID());
					wrapperCancerExamImgAnotasnOBJ.setProviderServiceMapID(obj.getProviderServiceMapID());
					wrapperCancerExamImgAnotasnOBJ.setCreatedBy(obj.getCreatedBy());
					wrapperCancerExamImgAnotasnOBJ.setImageID(obj.getCancerImageID());
					wrapperCancerExamImgAnotasnOBJ.setVisitCode(obj.getVisitCode());
					markerList = new ArrayList<>();
					markerMap.put("xCord", obj.getxCoordinate());
					markerMap.put("yCord", obj.getyCoordinate());
					markerMap.put("description", obj.getPointDesc());
					markerMap.put("point", obj.getPoint());

					markerList.add(markerMap);

					wrapperCancerExamImgAnotasnOBJ.setMarkers(markerList);

					resList.add(wrapperCancerExamImgAnotasnOBJ);

				} else {
					markerMap.put("xCord", obj.getxCoordinate());
					markerMap.put("yCord", obj.getyCoordinate());
					markerMap.put("description", obj.getPointDesc());
					markerMap.put("point", obj.getPoint());

					markerList.add(markerMap);
					if (wrapperCancerExamImgAnotasnOBJ != null)
						wrapperCancerExamImgAnotasnOBJ.setMarkers(markerList);
				}

				a = b;

			}

		} else {

		}
		// System.out.println("hello");
		return resList;
	}

	@Deprecated
	public Map<String, Object> getBenNurseDataForCaseSheet(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();

		resMap.put("benVisitDetail", getBeneficiaryVisitDetails(benRegID, visitCode));

		resMap.put("familyDiseaseHistory", getBenFamilyHisData(benRegID, visitCode));

		resMap.put("patientObstetricHistory", getBenObstetricDetailsData(benRegID, visitCode));

		resMap.put("patientPersonalHistory", getBenPersonalCancerHistoryData(benRegID, visitCode));

		resMap.put("benPersonalDietHistory", getBenPersonalCancerDietHistoryData(benRegID, visitCode));

		resMap.put("currentVitals", getBenCancerVitalDetailData(benRegID, visitCode));

		resMap.put("abdominalExamination", getBenCancerAbdominalExaminationData(benRegID, visitCode));

		resMap.put("breastExamination", getBenCancerBreastExaminationData(benRegID, visitCode));

		resMap.put("gynecologicalExamination", getBenCancerGynecologicalExaminationData(benRegID, visitCode));

		resMap.put("signsAndSymptoms", getBenCancerSignAndSymptomsData(benRegID, visitCode));

		resMap.put("BenCancerLymphNodeDetails", getBenCancerLymphNodeDetailsData(benRegID, visitCode));

		resMap.put("oralExamination", getBenCancerOralExaminationData(benRegID, visitCode));

		return resMap;
	}

	public BeneficiaryVisitDetail getBeneficiaryVisitDetails(Long benRegID, Long visitCode) {
		List<Objects[]> beneficiaryVisitDetail = benVisitDetailRepo.getBeneficiaryVisitDetails(benRegID, visitCode);
		BeneficiaryVisitDetail beneficiaryVisit = null;
		if (null != beneficiaryVisitDetail) {
			for (Object[] obj : beneficiaryVisitDetail) {
				beneficiaryVisit = new BeneficiaryVisitDetail((Long) obj[0], (Long) obj[1], (Integer) obj[2],
						(Timestamp) obj[3], (Short) obj[4], (Short) obj[5], (String) obj[6], (Integer) obj[7],
						(String) obj[8], (String) obj[9], (String) obj[10], (String) obj[11], (String) obj[12],
						(String) obj[13], (String) obj[14]);
			}
		}

		return beneficiaryVisit;
	}

	/***
	 * Fetch beneficiary past all visit family history.
	 * 
	 * @param beneficiaryRegID
	 * @return
	 */

	public String getBenCancerFamilyHistory(Long beneficiaryRegID) {
		Map<String, Object> resMap = new HashMap<>();
		Map<String, String> columnMap = new HashMap<>();
		List<Map<String, String>> columns = new ArrayList<Map<String, String>>();
		ArrayList<BenFamilyCancerHistory> benMedHistoryArrayList = new ArrayList<>();

		ArrayList<Object[]> benCancerFamilyHistoryDataArray = benFamilyCancerHistoryRepo
				.getBenCancerFamilyHistory(beneficiaryRegID);

		if (benCancerFamilyHistoryDataArray != null && benCancerFamilyHistoryDataArray.size() > 0) {
			BenFamilyCancerHistory benFamilyCancerHistory;
			for (Object[] obj : benCancerFamilyHistoryDataArray) {
				benFamilyCancerHistory = new BenFamilyCancerHistory((String) obj[0], (String) obj[1], (Date) obj[2]);
				benMedHistoryArrayList.add(benFamilyCancerHistory);
			}
		}

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Date of Capture");
		columnMap.put("keyName", "captureDate");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Cancer Disease Type");
		columnMap.put("keyName", "cancerDiseaseType");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Family Members");
		columnMap.put("keyName", "familyMember");
		columns.add(columnMap);

		resMap.put("columns", columns);
		resMap.put("data", benMedHistoryArrayList);

		return new Gson().toJson(resMap);
	}

	/***
	 * Fetch beneficiary past all visit personal history.
	 * 
	 * @param beneficiaryRegID
	 * @return
	 */

	public String getBenCancerPersonalHistory(Long beneficiaryRegID) {
		Map<String, Object> resMap = new HashMap<>();
		Map<String, String> columnMap = new HashMap<>();
		List<Map<String, String>> columns = new ArrayList<Map<String, String>>();
		ArrayList<BenPersonalCancerHistory> benPersonalHistoryArrayList = new ArrayList<>();

		ArrayList<Object[]> benPersonalHistory = benPersonalCancerHistoryRepo.getBenPersonalHistory(beneficiaryRegID);

		if (benPersonalHistory != null && benPersonalHistory.size() > 0) {
			for (Object[] obj : benPersonalHistory) {
				BenPersonalCancerHistory personalCancerHistory = new BenPersonalCancerHistory((String) obj[0],
						(Integer) obj[1], (Integer) obj[2], (String) obj[3], (Integer) obj[4], (Boolean) obj[5],
						(Boolean) obj[6], (Boolean) obj[7], (Integer) obj[8], (String) obj[9], (Boolean) obj[10],
						(String) obj[11], (Date) obj[12]);
				benPersonalHistoryArrayList.add(personalCancerHistory);
			}
		}

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Date of Capture");
		columnMap.put("keyName", "captureDate");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Tobacco Use Status");
		columnMap.put("keyName", "tobaccoUse");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Start Age(Years)");
		columnMap.put("keyName", "startAge_year");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Stop Age(Years)");
		columnMap.put("keyName", "endAge_year");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Type Of Tobacco Product");
		columnMap.put("keyName", "typeOfTobaccoProduct");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Quantity(Per Day)");
		columnMap.put("keyName", "quantityPerDay");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Filtered Cigarette");
		columnMap.put("keyName", "IsFilteredCigaerette");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Exposure to Cigarette");
		columnMap.put("keyName", "IsCigaretteExposure");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Betel Nut Chewing");
		columnMap.put("keyName", "IsBetelNutChewing");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Duration Of Betel Quid in Mouth(Mins)");
		columnMap.put("keyName", "durationOfBetelQuid");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Alcohol use Status");
		columnMap.put("keyName", "alcoholUse");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Alcohol Consumed(within 12 months)");
		columnMap.put("keyName", "IsAlcoholUsed");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Frequency Of Alcohol Use(within 12 months)");
		columnMap.put("keyName", "frequencyOfAlcoholUsed");
		columns.add(columnMap);

		resMap.put("columns", columns);
		resMap.put("data", benPersonalHistoryArrayList);

		return new Gson().toJson(resMap);
	}

	/***
	 * Fetch beneficiary All past visit personal diet history.
	 * 
	 * @param beneficiaryRegID
	 * @return
	 */
	public String getBenCancerPersonalDietHistory(Long beneficiaryRegID) {

		Map<String, Object> resMap = new HashMap<>();
		Map<String, String> columnMap = new HashMap<>();
		List<Map<String, String>> columns = new ArrayList<Map<String, String>>();
		ArrayList<BenPersonalCancerDietHistory> benPersonalDietArrayList = new ArrayList<>();

		ArrayList<Object[]> benPersonalCancerDietHistory = benPersonalCancerDietHistoryRepo
				.getBenPersonaDietHistory(beneficiaryRegID);

		if (benPersonalCancerDietHistory != null && benPersonalCancerDietHistory.size() > 0) {
			for (Object[] obj : benPersonalCancerDietHistory) {
				BenPersonalCancerDietHistory personalCancerDietHistory = new BenPersonalCancerDietHistory(
						(String) obj[0], (Integer) obj[1], (Integer) obj[2], (Integer) obj[3], (Integer) obj[4],
						(Integer) obj[5], (String) obj[6], (String) obj[7], (Boolean) obj[8], (Boolean) obj[9],
						(Date) obj[10]);
				benPersonalDietArrayList.add(personalCancerDietHistory);
			}
		}

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Date of Capture");
		columnMap.put("keyName", "captureDate");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Diet Type");
		columnMap.put("keyName", "dietType");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Fruit Consumption(Days/week)");
		columnMap.put("keyName", "fruitConsumptionDays");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Fruit Quantity(Cups/day)");
		columnMap.put("keyName", "fruitQuantityPerDay");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Vegetable Consumption(Days/week)");
		columnMap.put("keyName", "vegetableConsumptionDays");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Vegetable Quantity(Cups/day)");
		columnMap.put("keyName", "vegetableQuantityPerDay");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Weekly Intake Of Outside Prepared Meal");
		columnMap.put("keyName", "intakeOfOutsidePreparedMeal");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Type Of Oil Consumed");
		columnMap.put("keyName", "typeOfOilConsumed");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Physical Activity Type");
		columnMap.put("keyName", "physicalActivityType");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "History of exposure to radiation");
		columnMap.put("keyName", "IsRadiationExposure");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "History of any thyroid disorder");
		columnMap.put("keyName", "IsThyroidDisorder");
		columns.add(columnMap);

		resMap.put("columns", columns);
		resMap.put("data", benPersonalDietArrayList);

		return new Gson().toJson(resMap);
	}

	/***
	 * Fetch beneficiary past all visit obstetric history.
	 * 
	 * @param beneficiaryRegID
	 * @return
	 */

	public String getBenCancerObstetricHistory(Long beneficiaryRegID) {
		Map<String, Object> resMap = new HashMap<>();
		Map<String, String> columnMap = new HashMap<>();
		List<Map<String, String>> columns = new ArrayList<Map<String, String>>();
		ArrayList<BenObstetricCancerHistory> benObstetricCancerHistoryArrayList = new ArrayList<>();

		ArrayList<Object[]> benObstetricCancerHistoryList = benObstetricCancerHistoryRepo
				.getBenObstetricCancerHistoryData(beneficiaryRegID);

		if (benObstetricCancerHistoryList != null && benObstetricCancerHistoryList.size() > 0) {
			BenObstetricCancerHistory benObstetricCancerHistory;
			for (Object[] obj : benObstetricCancerHistoryList) {
				benObstetricCancerHistory = new BenObstetricCancerHistory((String) obj[0], (Boolean) obj[1],
						(String) obj[2], (Integer) obj[3], (Boolean) obj[4], (Boolean) obj[5], (Boolean) obj[6],
						(Integer) obj[7], (Boolean) obj[8], (Integer) obj[9], (Integer) obj[10], (String) obj[11],
						(Boolean) obj[12], (Boolean) obj[13], (Integer) obj[14], (Boolean) obj[15], (Boolean) obj[16],
						(Date) obj[17]);
				benObstetricCancerHistoryArrayList.add(benObstetricCancerHistory);
			}
		}

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Date of Capture");
		columnMap.put("keyName", "captureDate");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Pregnancy Status");
		columnMap.put("keyName", "pregnancyStatus");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Urine pregnancy test");
		columnMap.put("keyName", "IsUrinePregTest");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "No of times Pregnant");
		columnMap.put("keyName", "pregnant_No");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "No of Living Children");
		columnMap.put("keyName", "noOfLivingChild");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Abortions");
		columnMap.put("keyName", "IsAbortion");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Oral Contraceptives used in 5 years");
		columnMap.put("keyName", "IsOralContraceptiveUsed");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Hormone replacement Therapy in 5yrs");
		columnMap.put("keyName", "IsHormoneReplacementTherapy");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Age at menarche(Years)");
		columnMap.put("keyName", "menarche_Age");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Regularity of Menstrual Cycle");
		columnMap.put("keyName", "IsMenstrualCycleRegular");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Length of Menstrual Cycle(in days)");
		columnMap.put("keyName", "menstrualCycleLength");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Menstrual Flow Duration(Days)");
		columnMap.put("keyName", "menstrualFlowDuration");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Type of Flow");
		columnMap.put("keyName", "menstrualFlowType");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Dysmenorrhea");
		columnMap.put("keyName", "IsDysmenorrhea");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Inter menstrual bleeding");
		columnMap.put("keyName", "IsInterMenstrualBleeding");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Age at Menopause(Years)");
		columnMap.put("keyName", "menopauseAge");
		columns.add(columnMap);

		columnMap = new HashMap<>();
		columnMap.put("columnName", "Post-menopausal Bleeding");
		columnMap.put("keyName", "IsPostMenopauseBleeding");
		columns.add(columnMap);

		// columnMap = new HashMap<>();
		// columnMap.put("columnName", "Foul Smelling Discharge");
		// columnMap.put("keyName", "IsFoulSmellingDischarge");
		// columns.add(columnMap);

		resMap.put("columns", columns);
		resMap.put("data", benObstetricCancerHistoryArrayList);

		return new Gson().toJson(resMap);
	}

	public Long saveLymphNodeDetails(List<CancerLymphNodeDetails> cancerLymphNodeDetails, Long benVisitID,
			Long benVisitCode) {
		Long responseData = null;
		for (CancerLymphNodeDetails cancerLymphNodeDetail : cancerLymphNodeDetails) {
			cancerLymphNodeDetail.setBenVisitID(benVisitID);
			cancerLymphNodeDetail.setVisitCode(benVisitCode);
		}

		List<CancerLymphNodeDetails> response = (List<CancerLymphNodeDetails>) cancerLymphNodeExaminationRepo
				.save(cancerLymphNodeDetails);
		if (null != response && response.size() > 0) {
			responseData = response.get(response.size() - 1).getID();
		}
		return responseData;
	}

	public Long saveCancerSignAndSymptomsData(CancerSignAndSymptoms cancerSignAndSymptoms, Long benVisitID,
			Long benVisitCode) {
		cancerSignAndSymptoms.setBenVisitID(benVisitID);
		cancerSignAndSymptoms.setVisitCode(benVisitCode);
		Long response = saveCancerSignAndSymptomsData(cancerSignAndSymptoms);

		return response;
	}

	public Long saveCancerSignAndSymptomsData(CancerSignAndSymptoms cancerSignAndSymptoms) {
		CancerSignAndSymptoms response = cancerSignAndSymptomsRepo.save(cancerSignAndSymptoms);
		if (response != null)
			return response.getID();
		else
			return null;
	}

	public Long saveCancerOralExaminationData(CancerOralExamination cancerOralExamination) {

		cancerOralExamination = getCancerOralExaminationDetails(cancerOralExamination);

		CancerOralExamination response = cancerOralExaminationRepo.save(cancerOralExamination);
		if (response != null)
			return response.getID();
		else
			return null;
	}

	public CancerOralExamination getCancerOralExaminationDetails(CancerOralExamination cancerOralExamination) {

		List<String> preMalignantLesionTypeList = cancerOralExamination.getPreMalignantLesionTypeList();
		String preMalignantLesionTypeData = "";
		if (preMalignantLesionTypeList != null && preMalignantLesionTypeList.size() > 0) {
			for (String preMalignantLesionType : cancerOralExamination.getPreMalignantLesionTypeList()) {
				preMalignantLesionTypeData += preMalignantLesionType + ",";
			}
		}
		cancerOralExamination.setPreMalignantLesionType(preMalignantLesionTypeData);
		return cancerOralExamination;
	}

	public Long saveCancerBreastExaminationData(CancerBreastExamination cancerBreastExamination) {
		CancerBreastExamination response = cancerBreastExaminationRepo.save(cancerBreastExamination);
		if (response != null)
			return response.getID();
		else
			return null;
	}

	public Long saveCancerAbdominalExaminationData(CancerAbdominalExamination cancerAbdominalExamination) {
		CancerAbdominalExamination response = cancerAbdominalExaminationRepo.save(cancerAbdominalExamination);
		if (response != null)
			return response.getID();
		else
			return null;
	}

	public Long saveCancerGynecologicalExaminationData(CancerGynecologicalExamination cancerGynecologicalExamination) {

		cancerGynecologicalExamination = getCancerGynecologicalExamination(cancerGynecologicalExamination);
		CancerGynecologicalExamination response = cancerGynecologicalExaminationRepo
				.save(cancerGynecologicalExamination);
		if (response != null)
			return response.getID();
		else
			return null;
	}

	private CancerGynecologicalExamination getCancerGynecologicalExamination(
			CancerGynecologicalExamination cancerGynecologicalExamination) {
		List<String> typeOfLesionList = cancerGynecologicalExamination.getTypeOfLesionList();
		String typeOfLesionData = "";
		if (typeOfLesionList != null && typeOfLesionList.size() > 0) {
			for (String typeOfLesion : typeOfLesionList) {
				typeOfLesionData += typeOfLesion + ",";
			}
		}
		cancerGynecologicalExamination.setTypeOfLesion(typeOfLesionData);
		return cancerGynecologicalExamination;
	}

	public Long saveDocExaminationImageAnnotation(List<WrapperCancerExamImgAnotasn> wrapperCancerExamImgAnotasnList,
			Long benVisitID, Long benVisitCode) {
		// System.out.println("hello");
		Long x = null;
		for (WrapperCancerExamImgAnotasn wrapperCancerExamImgAnotasn : wrapperCancerExamImgAnotasnList) {
			wrapperCancerExamImgAnotasn.setVisitID(benVisitID);
		}
		List<CancerExaminationImageAnnotation> objList = (List<CancerExaminationImageAnnotation>) cancerExaminationImageAnnotationRepo
				.save(getCancerExaminationImageAnnotationList(wrapperCancerExamImgAnotasnList, benVisitCode));
		if (objList != null && objList.size() > 0) {
			x = (long) objList.size();
		}
		return x;
	}

	public List<CancerExaminationImageAnnotation> getCancerExaminationImageAnnotationList(
			List<WrapperCancerExamImgAnotasn> wrapperCancerExamImgAnotasnList, Long visitCode) {
		List<CancerExaminationImageAnnotation> objList = new ArrayList<>();

		if (wrapperCancerExamImgAnotasnList.size() > 0) {
			for (WrapperCancerExamImgAnotasn obj : wrapperCancerExamImgAnotasnList) {
				if (obj != null) {
					ArrayList<Map<String, Object>> markersList = obj.getMarkers();
					if (markersList != null && markersList.size() > 0) {
						for (Map<String, Object> marker : markersList) {
							CancerExaminationImageAnnotation cancerExaminationImageAnnotation = new CancerExaminationImageAnnotation();
							cancerExaminationImageAnnotation.setBeneficiaryRegID(obj.getBeneficiaryRegID());
							cancerExaminationImageAnnotation.setBenVisitID(obj.getVisitID());
							cancerExaminationImageAnnotation.setVisitCode(visitCode);
							cancerExaminationImageAnnotation.setProviderServiceMapID(obj.getProviderServiceMapID());
							cancerExaminationImageAnnotation.setVanID(obj.getVanID());
							cancerExaminationImageAnnotation.setParkingPlaceID(obj.getParkingPlaceID());
							cancerExaminationImageAnnotation.setCreatedBy(obj.getCreatedBy());
							cancerExaminationImageAnnotation.setCancerImageID(obj.getImageID());

							Double a = (Double) marker.get("xCord");
							cancerExaminationImageAnnotation.setxCoordinate(a.intValue());
							Double b = (Double) marker.get("yCord");
							cancerExaminationImageAnnotation.setyCoordinate(b.intValue());
							Double c = (Double) marker.get("point");
							cancerExaminationImageAnnotation.setPoint(c.intValue());
							cancerExaminationImageAnnotation.setPointDesc((String) marker.get("description"));

							objList.add(cancerExaminationImageAnnotation);
						}
					} else {
						// CancerExaminationImageAnnotation
						// cancerExaminationImageAnnotation = new
						// CancerExaminationImageAnnotation();
						// cancerExaminationImageAnnotation.setCancerImageID(obj.getImageID());
						// cancerExaminationImageAnnotation.setBeneficiaryRegID(obj.getBeneficiaryRegID());
						// cancerExaminationImageAnnotation.setBenVisitID(obj.getVisitID());
						// cancerExaminationImageAnnotation.setCreatedBy(obj.getCreatedBy());
						// objList.add(cancerExaminationImageAnnotation);
					}
				}
			}
		}

		return objList;
	}

	public int updateSignAndSymptomsExaminationDetails(CancerSignAndSymptoms cancerSignAndSymptoms) {
		int response = 0;
		int recordsAvailable = 0;
		String processed = cancerSignAndSymptomsRepo.getCancerSignAndSymptomsStatus(
				cancerSignAndSymptoms.getBeneficiaryRegID(), cancerSignAndSymptoms.getVisitCode());
		if (null != processed) {
			recordsAvailable = 1;
		}
		if (null != processed && !processed.equalsIgnoreCase("N")) {
			processed = "U";

		} else {
			processed = "N";
		}
		try {
			cancerSignAndSymptoms.setProcessed(processed);
			if (recordsAvailable == 1) {
				response = updateSignAndSymptomsExamination(cancerSignAndSymptoms);
			} else {
				cancerSignAndSymptoms.setCreatedBy(cancerSignAndSymptoms.getModifiedBy());
				Long saveRes = saveCancerSignAndSymptomsData(cancerSignAndSymptoms);
				if (null != saveRes && saveRes > 0) {
					response = 1;
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());

		}
		return response;

	}

	public int updateSignAndSymptomsExamination(CancerSignAndSymptoms cancerSignAndSymptoms) {
		int response = cancerSignAndSymptomsRepo.updateCancerSignAndSymptoms(
				cancerSignAndSymptoms.getProviderServiceMapID(), cancerSignAndSymptoms.getShortnessOfBreath(),
				cancerSignAndSymptoms.getCoughGTE2Weeks(), cancerSignAndSymptoms.getBloodInSputum(),
				cancerSignAndSymptoms.getDifficultyInOpeningMouth(),
				cancerSignAndSymptoms.getNonHealingUlcerOrPatchOrGrowth(),
				cancerSignAndSymptoms.getChangeInTheToneOfVoice(), cancerSignAndSymptoms.getLumpInTheBreast(),
				cancerSignAndSymptoms.getBloodStainedDischargeFromNipple(),
				cancerSignAndSymptoms.getChangeInShapeAndSizeOfBreasts(),
				cancerSignAndSymptoms.getVaginalBleedingBetweenPeriods(),
				cancerSignAndSymptoms.getVaginalBleedingAfterMenopause(),
				cancerSignAndSymptoms.getVaginalBleedingAfterIntercourse(),
				cancerSignAndSymptoms.getFoulSmellingVaginalDischarge(), cancerSignAndSymptoms.getBreastEnlargement(),
				cancerSignAndSymptoms.getLymphNode_Enlarged(), cancerSignAndSymptoms.getBriefHistory(),
				cancerSignAndSymptoms.getModifiedBy(), cancerSignAndSymptoms.getBeneficiaryRegID(),
				cancerSignAndSymptoms.getVisitCode(), cancerSignAndSymptoms.getProcessed());
		return response;
	}

	public int updateLymphNodeExaminationDetails(WrapperCancerSymptoms wrapperOBJ) {

		int response = 0;
		int delRes = 0;
		try {
			// if (cancerLymphNodeDetails.size() > 0) {
			List<CancerLymphNodeDetails> cancerLymphNodeDetails = wrapperOBJ.getCancerLymphNodeDetails();
			List<CancerLymphNodeDetails> cancerLymphNodeDetailsFinal = new ArrayList<>();
			List<String> lymphNodeNameList = new ArrayList<>();

			if (wrapperOBJ.getCancerSignAndSymptoms().getLymphNode_Enlarged() != null
					&& wrapperOBJ.getCancerSignAndSymptoms().getLymphNode_Enlarged() == false) {

				delRes = deleteLymphNode(wrapperOBJ);

			} else {
				for (CancerLymphNodeDetails objTMP : cancerLymphNodeDetails) {
					if (lymphNodeNameList.contains(objTMP.getLymphNodeName())) {

					} else {
						lymphNodeNameList.add(objTMP.getLymphNodeName());
					}
				}

				if (lymphNodeNameList.size() > 0) {
					delRes = deleteLymphNodeWithNameList(wrapperOBJ, lymphNodeNameList);
				} else {
					delRes = 1;
				}
			}

			// }
			if (delRes > 0) {
				for (CancerLymphNodeDetails o : cancerLymphNodeDetails) {
					if (o.getMobility_Left() != null || o.getMobility_Right() != null || o.getSize_Left() != null
							|| o.getSize_Right() != null) {
						cancerLymphNodeDetailsFinal.add(o);
					}
				}

				if (cancerLymphNodeDetailsFinal.size() > 0) {
					ArrayList<CancerLymphNodeDetails> cancerLymphNodes = (ArrayList<CancerLymphNodeDetails>) cancerLymphNodeExaminationRepo
							.save(cancerLymphNodeDetailsFinal);
					if (cancerLymphNodes.size() > 0) {
						response = cancerLymphNodes.size();
					}
				} else {
					response = 1;
				}
			} else {
				response = 0;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());

		}
		return response;
	}

	private int deleteLymphNode(WrapperCancerSymptoms wrapperOBJ) {
		int delRes = 0;

		ArrayList<Object[]> lymphNodeDetailsStatuses = cancerLymphNodeExaminationRepo.getCancerLymphNodeDetailsStatus(
				wrapperOBJ.getCancerSignAndSymptoms().getBeneficiaryRegID(),
				wrapperOBJ.getCancerSignAndSymptoms().getVisitCode());

		if (lymphNodeDetailsStatuses != null && lymphNodeDetailsStatuses.size() > 0) {
			for (Object[] obj : lymphNodeDetailsStatuses) {
				String processed = (String) obj[1];
				if (null != processed && !processed.equalsIgnoreCase("N")) {
					processed = "U";
				} else {
					processed = "N";
				}
				delRes = cancerLymphNodeExaminationRepo.deleteExistingLymphNodeDetails((Long) obj[0], processed);
			}
		} else {
			delRes = 1;
		}
		return delRes;
	}

	private int deleteLymphNodeWithNameList(WrapperCancerSymptoms wrapperOBJ, List<String> lymphNodeNameList) {
		int delRes = 0;
		ArrayList<Object[]> lymphNodeDetailsStatuses = cancerLymphNodeExaminationRepo
				.getCancerLymphNodeDetailsStatusForLymphnodeNameList(
						wrapperOBJ.getCancerSignAndSymptoms().getBeneficiaryRegID(),
						wrapperOBJ.getCancerSignAndSymptoms().getVisitCode(), lymphNodeNameList);

		if (lymphNodeDetailsStatuses != null && lymphNodeDetailsStatuses.size() > 0) {
			for (Object[] obj : lymphNodeDetailsStatuses) {
				String processed = (String) obj[1];
				if (null != processed && !processed.equalsIgnoreCase("N")) {
					processed = "U";
				} else {
					processed = "N";
				}
				delRes = cancerLymphNodeExaminationRepo.deleteExistingLymphNodeDetails((Long) obj[0], processed);
			}
		} else {
			delRes = 1;
		}
		return delRes;
	}

	public int updateCancerOralDetails(CancerOralExamination cancerOralExamination) {
		int response = 0;
		int recordsAvailable = 0;
		String processed = cancerOralExaminationRepo.getCancerOralExaminationStatus(
				cancerOralExamination.getBeneficiaryRegID(), cancerOralExamination.getVisitCode());
		if (null != processed) {
			recordsAvailable = 1;
		}

		if (null != processed && !processed.equalsIgnoreCase("N")) {
			processed = "U";
		} else {
			processed = "N";
		}
		try {
			cancerOralExamination.setProcessed(processed);
			if (recordsAvailable == 1) {
				response = updateCancerOralExamination(cancerOralExamination);
			} else {
				cancerOralExamination.setCreatedBy(cancerOralExamination.getModifiedBy());
				Long saveRes = saveCancerOralExaminationData(cancerOralExamination);
				if (null != saveRes && saveRes > 0) {
					response = 1;
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());

		}
		return response;

	}

	public int updateCancerOralExamination(CancerOralExamination cancerOralExamination) {
		cancerOralExamination = getCancerOralExaminationDetails(cancerOralExamination);

		int response = cancerOralExaminationRepo.updateCancerOralExaminationDetails(
				cancerOralExamination.getProviderServiceMapID(), cancerOralExamination.getLimitedMouthOpening(),
				cancerOralExamination.getPremalignantLesions(), cancerOralExamination.getPreMalignantLesionType(),
				cancerOralExamination.getProlongedIrritation(), cancerOralExamination.getChronicBurningSensation(),
				cancerOralExamination.getObservation(), cancerOralExamination.getModifiedBy(),
				cancerOralExamination.getBeneficiaryRegID(), cancerOralExamination.getVisitCode(),
				cancerOralExamination.getProcessed());
		return response;
	}

	public int updateCancerBreastDetails(CancerBreastExamination cancerBreastExamination) {
		int response = 0;
		int recordsAvailable = 0;
		String processed = cancerBreastExaminationRepo.getCancerBreastExaminationStatus(
				cancerBreastExamination.getBeneficiaryRegID(), cancerBreastExamination.getVisitCode());
		if (null != processed) {
			recordsAvailable = 1;
		}
		if (null != processed && !processed.equalsIgnoreCase("N")) {
			processed = "U";
		} else {
			processed = "N";
		}
		try {
			cancerBreastExamination.setProcessed(processed);
			if (recordsAvailable == 1) {
				response = updateCancerBreastExamination(cancerBreastExamination);
			} else {
				cancerBreastExamination.setCreatedBy(cancerBreastExamination.getModifiedBy());
				Long saveRes = saveCancerBreastExaminationData(cancerBreastExamination);
				if (null != saveRes && saveRes > 0) {
					response = 1;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());

		}
		return response;

	}

	public int updateCancerBreastExamination(CancerBreastExamination cancerBreastExamination) {
		int response = cancerBreastExaminationRepo.updateCancerBreastExaminatio(
				cancerBreastExamination.getProviderServiceMapID(), cancerBreastExamination.getEverBreastFed(),
				cancerBreastExamination.getBreastFeedingDurationGTE6months(),
				cancerBreastExamination.getBreastsAppear_Normal(), cancerBreastExamination.getRashOnBreast(),
				cancerBreastExamination.getDimplingSkinOnBreast(), cancerBreastExamination.getDischargeFromNipple(),
				cancerBreastExamination.getPeaudOrange(), cancerBreastExamination.getLumpInBreast(),
				cancerBreastExamination.getLumpSize(), cancerBreastExamination.getLumpShape(),
				cancerBreastExamination.getLumpTexture(), cancerBreastExamination.getReferredToMammogram(),
				cancerBreastExamination.getMamogramReport(), cancerBreastExamination.getModifiedBy(),
				cancerBreastExamination.getBeneficiaryRegID(), cancerBreastExamination.getVisitCode(),
				cancerBreastExamination.getProcessed());
		return response;
	}

	public int updateCancerAbdominalExaminationDetails(CancerAbdominalExamination cancerAbdominalExamination) {
		int response = 0;
		int recordsAvailable = 0;
		String processed = cancerAbdominalExaminationRepo.getCancerAbdominalExaminationStatus(
				cancerAbdominalExamination.getBeneficiaryRegID(), cancerAbdominalExamination.getVisitCode());
		if (null != processed) {
			recordsAvailable = 1;
		}
		if (null != processed && !processed.equalsIgnoreCase("N")) {
			processed = "U";
		} else {
			processed = "N";
		}
		try {
			cancerAbdominalExamination.setProcessed(processed);
			if (recordsAvailable == 1) {
				response = updateCancerAbdominalExamination(cancerAbdominalExamination);
			} else {
				cancerAbdominalExamination.setCreatedBy(cancerAbdominalExamination.getModifiedBy());
				Long saveRes = saveCancerAbdominalExaminationData(cancerAbdominalExamination);
				if (null != saveRes && saveRes > 0) {
					response = 1;
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());

		}
		return response;

	}

	public int updateCancerAbdominalExamination(CancerAbdominalExamination cancerAbdominalExamination) {
		int response = cancerAbdominalExaminationRepo.updateCancerAbdominalExamination(
				cancerAbdominalExamination.getProviderServiceMapID(),
				cancerAbdominalExamination.getAbdominalInspection_Normal(), cancerAbdominalExamination.getLiver(),
				cancerAbdominalExamination.getAscites_Present(), cancerAbdominalExamination.getAnyOtherMass_Present(),
				cancerAbdominalExamination.getLymphNodes_Enlarged(),
				cancerAbdominalExamination.getLymphNode_Inguinal_Left(),
				cancerAbdominalExamination.getLymphNode_Inguinal_Right(),
				cancerAbdominalExamination.getLymphNode_ExternalIliac_Left(),
				cancerAbdominalExamination.getLymphNode_ExternalIliac_Right(),
				cancerAbdominalExamination.getLymphNode_ParaAortic_Left(),
				cancerAbdominalExamination.getLymphNode_ParaAortic_Right(), cancerAbdominalExamination.getObservation(),
				cancerAbdominalExamination.getModifiedBy(), cancerAbdominalExamination.getBeneficiaryRegID(),
				cancerAbdominalExamination.getVisitCode(), cancerAbdominalExamination.getProcessed());
		return response;
	}

	public int updateCancerGynecologicalExaminationDetails(
			CancerGynecologicalExamination cancerGynecologicalExamination) {
		int response = 0;
		int recordsAvailable = 0;
		String processed = cancerGynecologicalExaminationRepo.getCancerGynecologicalExaminationStatus(
				cancerGynecologicalExamination.getBeneficiaryRegID(), cancerGynecologicalExamination.getVisitCode());
		if (null != processed) {
			recordsAvailable = 1;
		}
		if (null != processed && !processed.equalsIgnoreCase("N")) {
			processed = "U";
		} else {
			processed = "N";
		}
		try {
			cancerGynecologicalExamination.setProcessed(processed);
			if (recordsAvailable == 1) {
				response = updateCancerGynecologicalExamination(cancerGynecologicalExamination);
			} else {
				cancerGynecologicalExamination.setCreatedBy(cancerGynecologicalExamination.getModifiedBy());
				Long saveRes = saveCancerGynecologicalExaminationData(cancerGynecologicalExamination);
				if (null != saveRes && saveRes > 0) {
					response = 1;
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());

		}
		return response;

	}

	public int updateCancerGynecologicalExamination(CancerGynecologicalExamination cancerGynecologicalExamination) {
		cancerGynecologicalExamination = getCancerGynecologicalExamination(cancerGynecologicalExamination);

		int response = cancerGynecologicalExaminationRepo.updateCancerGynecologicalExamination(
				cancerGynecologicalExamination.getProviderServiceMapID(),
				cancerGynecologicalExamination.getAppearanceOfCervix(),
				cancerGynecologicalExamination.getTypeOfLesion(), cancerGynecologicalExamination.getVulvalInvolvement(),
				cancerGynecologicalExamination.getVaginalInvolvement(),
				cancerGynecologicalExamination.getUterus_Normal(),
				cancerGynecologicalExamination.getSufferedFromRTIOrSTI(),
				cancerGynecologicalExamination.getrTIOrSTIDetail(), cancerGynecologicalExamination.getFilePath(),
				cancerGynecologicalExamination.getExperiencedPostCoitalBleeding(),
				cancerGynecologicalExamination.getObservation(), cancerGynecologicalExamination.getModifiedBy(),
				cancerGynecologicalExamination.getBeneficiaryRegID(), cancerGynecologicalExamination.getVisitCode(),
				cancerGynecologicalExamination.getProcessed());
		return response;
	}

	public int updateCancerExamImgAnotasnDetails(
			List<CancerExaminationImageAnnotation> cancerExaminationImageAnnotationList) {

		int response = 0;
		int delRes = 0;
		try {

			List<Integer> imgIDList = new ArrayList<>();
			for (CancerExaminationImageAnnotation tmpOBJ : cancerExaminationImageAnnotationList) {
				if (imgIDList.contains(tmpOBJ.getCancerImageID())) {
				} else {
					imgIDList.add(tmpOBJ.getCancerImageID());
				}
			}

			ArrayList<Object[]> cancerExaminationImageAnnotationStatuses = null;
			if (imgIDList.size() > 0) {
				cancerExaminationImageAnnotationStatuses = cancerExaminationImageAnnotationRepo
						.getCancerExaminationImageAnnotationDetailsStatus(
								cancerExaminationImageAnnotationList.get(0).getBeneficiaryRegID(),
								cancerExaminationImageAnnotationList.get(0).getVisitCode(), imgIDList);
			}

			if (cancerExaminationImageAnnotationStatuses != null
					&& cancerExaminationImageAnnotationStatuses.size() > 0) {
				for (Object[] obj : cancerExaminationImageAnnotationStatuses) {
					String processed = (String) obj[1];
					if (null != processed && !processed.equalsIgnoreCase("N")) {
						processed = "U";
					} else {
						processed = "N";
					}
					delRes = cancerExaminationImageAnnotationRepo.deleteExistingImageAnnotationDetails((Long) obj[0],
							processed);
				}
			} else {
				delRes = 1;
			}

			if (delRes > 0) {

				ArrayList<CancerExaminationImageAnnotation> cancerImageAnnotationsFinal = new ArrayList<>();

				if (cancerExaminationImageAnnotationList.size() > 0) {
					for (CancerExaminationImageAnnotation obj : cancerExaminationImageAnnotationList) {
						if (obj.getxCoordinate() != null && obj.getyCoordinate() != null
								&& obj.getCreatedBy() != null) {
							obj.setModifiedBy(obj.getCreatedBy());
							cancerImageAnnotationsFinal.add(obj);
						}
					}
					ArrayList<CancerExaminationImageAnnotation> cancerImageAnnotations = (ArrayList<CancerExaminationImageAnnotation>) cancerExaminationImageAnnotationRepo
							.save(cancerImageAnnotationsFinal);
					if (cancerImageAnnotations.size() == cancerImageAnnotationsFinal.size()) {
						response = 1;
					}
				} else {
					response = 1;
				}
			} else {
				response = 0;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());

		}
		return response;
	}

}
