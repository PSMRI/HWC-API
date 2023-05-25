package com.iemr.mmu.service.anc;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.mmu.data.anc.ANCCareDetails;
import com.iemr.mmu.data.anc.ANCWomenVaccineDetail;
import com.iemr.mmu.data.anc.BenAdherence;
import com.iemr.mmu.data.anc.SysObstetricExamination;
import com.iemr.mmu.data.anc.WrapperAncImmunization;
import com.iemr.mmu.data.anc.WrapperBenInvestigationANC;
import com.iemr.mmu.data.quickConsultation.LabTestOrderDetail;
import com.iemr.mmu.repo.nurse.anc.ANCCareRepo;
import com.iemr.mmu.repo.nurse.anc.ANCWomenVaccineRepo;
import com.iemr.mmu.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.mmu.repo.nurse.anc.SysObstetricExaminationRepo;
import com.iemr.mmu.repo.quickConsultation.LabTestOrderDetailRepo;

@Service
public class ANCNurseServiceImpl implements ANCNurseService {

	private ANCCareRepo ancCareRepo;
	private ANCWomenVaccineRepo ancWomenVaccineRepo;
	private BenAdherenceRepo benAdherenceRepo;

	private SysObstetricExaminationRepo sysObstetricExaminationRepo;

	private LabTestOrderDetailRepo labTestOrderDetailRepo;

	@Autowired
	public void setLabTestOrderDetailRepo(LabTestOrderDetailRepo labTestOrderDetailRepo) {
		this.labTestOrderDetailRepo = labTestOrderDetailRepo;
	}

	@Autowired
	public void setBenAdherenceRepo(BenAdherenceRepo benAdherenceRepo) {
		this.benAdherenceRepo = benAdherenceRepo;
	}

	@Autowired
	public void setAncCareRepo(ANCCareRepo ancCareRepo) {
		this.ancCareRepo = ancCareRepo;
	}

	@Autowired
	public void setAncWomenVaccineRepo(ANCWomenVaccineRepo ancWomenVaccineRepo) {
		this.ancWomenVaccineRepo = ancWomenVaccineRepo;
	}

	@Autowired
	public void setSysObstetricExaminationRepo(SysObstetricExaminationRepo sysObstetricExaminationRepo) {
		this.sysObstetricExaminationRepo = sysObstetricExaminationRepo;
	}

	@Override
	public Long saveBeneficiaryANCDetails(ANCCareDetails ancCareDetails) {
		ANCCareDetails ancCareDetail = ancCareRepo.save(ancCareDetails);
		Long ancCareID = null;
		if (null != ancCareDetail && ancCareDetail.getID() > 0) {
			ancCareID = ancCareDetail.getID();
		}
		return ancCareID;
	}

	@Override
	public Long saveANCWomenVaccineDetails(List<ANCWomenVaccineDetail> ancWomenVaccineDetails) {
		List<ANCWomenVaccineDetail> ancWomenVaccineDetail = (List<ANCWomenVaccineDetail>) ancWomenVaccineRepo
				.save(ancWomenVaccineDetails);

		Long ancWomenVaccineID = null;
		if (null != ancWomenVaccineDetail && ancWomenVaccineDetail.size() > 0) {
			for (ANCWomenVaccineDetail ancWomenVaccine : ancWomenVaccineDetail) {
				ancWomenVaccineID = ancWomenVaccine.getID();
			}
		}
		return ancWomenVaccineID;
	}

	public Integer saveBenInvestigationFromDoc(WrapperBenInvestigationANC wrapperBenInvestigationANC) {
		int r = 0;
		ArrayList<LabTestOrderDetail> LabTestOrderDetailList = new ArrayList<>();
		ArrayList<LabTestOrderDetail> investigationList = wrapperBenInvestigationANC.getLaboratoryList();
		if (investigationList != null && investigationList.size() > 0) {

			for (LabTestOrderDetail testData : investigationList) {

				testData.setBeneficiaryRegID(wrapperBenInvestigationANC.getBeneficiaryRegID());
				testData.setBenVisitID(wrapperBenInvestigationANC.getBenVisitID());
				testData.setProviderServiceMapID(wrapperBenInvestigationANC.getProviderServiceMapID());
				testData.setCreatedBy(wrapperBenInvestigationANC.getCreatedBy());
				testData.setPrescriptionID(wrapperBenInvestigationANC.getPrescriptionID());

				LabTestOrderDetailList.add(testData);
			}
			ArrayList<LabTestOrderDetail> LabTestOrderDetailListRS = (ArrayList<LabTestOrderDetail>) labTestOrderDetailRepo
					.save(LabTestOrderDetailList);

			if (LabTestOrderDetailListRS != null && LabTestOrderDetailListRS.size() > 0) {
				r = 1;
			}
		} else {
			r = 1;
		}
		return r;
	}

	@Override
	public Long saveBenAncCareDetails(ANCCareDetails ancCareDetailsOBJ) throws ParseException {
		Long ancCareSuccessFlag = null;
		if (ancCareDetailsOBJ.getLmpDate() != null && !ancCareDetailsOBJ.getLmpDate().isEmpty()
				&& ancCareDetailsOBJ.getLmpDate().length() >= 10) {
			String lmpDate = ancCareDetailsOBJ.getLmpDate().split("T")[0];
			ancCareDetailsOBJ
					.setLastMenstrualPeriod_LMP(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(lmpDate).getTime()));
		}
		if (ancCareDetailsOBJ.getExpDelDt() != null && !ancCareDetailsOBJ.getExpDelDt().isEmpty()
				&& ancCareDetailsOBJ.getExpDelDt().length() >= 10) {
			String edDate = ancCareDetailsOBJ.getExpDelDt().split("T")[0];
			ancCareDetailsOBJ
					.setExpectedDateofDelivery(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(edDate).getTime()));
		}
		ANCCareDetails ancCareDetailsRS = ancCareRepo.save(ancCareDetailsOBJ);
		if (ancCareDetailsRS != null) {
			ancCareSuccessFlag = ancCareDetailsRS.getID();
		}
		return ancCareSuccessFlag;
	}

	@Override
	public Long saveAncImmunizationDetails(WrapperAncImmunization wrapperAncImmunizationOBJ) throws ParseException {
		Long successFlag = null;
		List<ANCWomenVaccineDetail> ancWomenVaccineDetailList = getANCWomenVaccineDetail(wrapperAncImmunizationOBJ);
		List<ANCWomenVaccineDetail> ancWomenVaccineDetailRSList = (List<ANCWomenVaccineDetail>) ancWomenVaccineRepo
				.save(ancWomenVaccineDetailList);
		if (ancWomenVaccineDetailRSList != null && ancWomenVaccineDetailRSList.size() > 0) {
			successFlag = ancWomenVaccineDetailRSList.get(0).getID();
		}
		return successFlag;
	}

	private List<ANCWomenVaccineDetail> getANCWomenVaccineDetail(WrapperAncImmunization wrapperAncImmunizationOBJ)
			throws ParseException {
		List<ANCWomenVaccineDetail> ancWomenVaccineDetailList = new ArrayList<ANCWomenVaccineDetail>();
		ANCWomenVaccineDetail ancWomenVaccineDetail;
		if (wrapperAncImmunizationOBJ != null) {

			// TT-1 details
			ancWomenVaccineDetail = new ANCWomenVaccineDetail();
			ancWomenVaccineDetail.setBeneficiaryRegID(wrapperAncImmunizationOBJ.getBeneficiaryRegID());
			ancWomenVaccineDetail.setBenVisitID(wrapperAncImmunizationOBJ.getBenVisitID());
			ancWomenVaccineDetail.setVisitCode(wrapperAncImmunizationOBJ.getVisitCode());
			ancWomenVaccineDetail.setProviderServiceMapID(wrapperAncImmunizationOBJ.getProviderServiceMapID());
			ancWomenVaccineDetail.setVanID(wrapperAncImmunizationOBJ.getVanID());
			ancWomenVaccineDetail.setParkingPlaceID(wrapperAncImmunizationOBJ.getParkingPlaceID());
			ancWomenVaccineDetail.setCreatedBy(wrapperAncImmunizationOBJ.getCreatedBy());
			ancWomenVaccineDetail.setID(wrapperAncImmunizationOBJ.gettT1ID());
			ancWomenVaccineDetail.setVaccineName("TT-1");
			ancWomenVaccineDetail.setStatus(wrapperAncImmunizationOBJ.gettT_1Status());
			if (wrapperAncImmunizationOBJ.getDateReceivedForTT_1() != null
					&& wrapperAncImmunizationOBJ.getDateReceivedForTT_1().length() >= 10) {
				String TT_1 = wrapperAncImmunizationOBJ.getDateReceivedForTT_1().split("T")[0];
				ancWomenVaccineDetail
						.setReceivedDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(TT_1).getTime()));
			}
			ancWomenVaccineDetail.setReceivedFacilityName(wrapperAncImmunizationOBJ.getFacilityNameOfTT_1());
			ancWomenVaccineDetail.setModifiedBy(wrapperAncImmunizationOBJ.getModifiedBy());
			ancWomenVaccineDetailList.add(ancWomenVaccineDetail);

			// TT-2 details
			ancWomenVaccineDetail = new ANCWomenVaccineDetail();
			ancWomenVaccineDetail.setBeneficiaryRegID(wrapperAncImmunizationOBJ.getBeneficiaryRegID());
			ancWomenVaccineDetail.setBenVisitID(wrapperAncImmunizationOBJ.getBenVisitID());
			ancWomenVaccineDetail.setProviderServiceMapID(wrapperAncImmunizationOBJ.getProviderServiceMapID());
			ancWomenVaccineDetail.setVanID(wrapperAncImmunizationOBJ.getVanID());
			ancWomenVaccineDetail.setParkingPlaceID(wrapperAncImmunizationOBJ.getParkingPlaceID());
			ancWomenVaccineDetail.setVisitCode(wrapperAncImmunizationOBJ.getVisitCode());
			ancWomenVaccineDetail.setCreatedBy(wrapperAncImmunizationOBJ.getCreatedBy());
			ancWomenVaccineDetail.setID(wrapperAncImmunizationOBJ.gettT2ID());
			ancWomenVaccineDetail.setVaccineName("TT-2");
			ancWomenVaccineDetail.setStatus(wrapperAncImmunizationOBJ.gettT_2Status());
			if (wrapperAncImmunizationOBJ.getDateReceivedForTT_2() != null
					&& wrapperAncImmunizationOBJ.getDateReceivedForTT_2().length() >= 10) {
				String TT_2 = wrapperAncImmunizationOBJ.getDateReceivedForTT_2().split("T")[0];
				ancWomenVaccineDetail
						.setReceivedDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(TT_2).getTime()));
			}
			ancWomenVaccineDetail.setReceivedFacilityName(wrapperAncImmunizationOBJ.getFacilityNameOfTT_2());
			ancWomenVaccineDetail.setModifiedBy(wrapperAncImmunizationOBJ.getModifiedBy());
			ancWomenVaccineDetailList.add(ancWomenVaccineDetail);

			// TT-3 (Booster) details
			ancWomenVaccineDetail = new ANCWomenVaccineDetail();
			ancWomenVaccineDetail.setBeneficiaryRegID(wrapperAncImmunizationOBJ.getBeneficiaryRegID());
			ancWomenVaccineDetail.setBenVisitID(wrapperAncImmunizationOBJ.getBenVisitID());
			ancWomenVaccineDetail.setProviderServiceMapID(wrapperAncImmunizationOBJ.getProviderServiceMapID());
			ancWomenVaccineDetail.setVanID(wrapperAncImmunizationOBJ.getVanID());
			ancWomenVaccineDetail.setParkingPlaceID(wrapperAncImmunizationOBJ.getParkingPlaceID());
			ancWomenVaccineDetail.setVisitCode(wrapperAncImmunizationOBJ.getVisitCode());
			ancWomenVaccineDetail.setCreatedBy(wrapperAncImmunizationOBJ.getCreatedBy());
			ancWomenVaccineDetail.setID(wrapperAncImmunizationOBJ.gettT3ID());
			ancWomenVaccineDetail.setVaccineName("TT-Booster");
			ancWomenVaccineDetail.setStatus(wrapperAncImmunizationOBJ.gettT_3Status());
			if (wrapperAncImmunizationOBJ.getDateReceivedForTT_3() != null
					&& wrapperAncImmunizationOBJ.getDateReceivedForTT_3().length() >= 10) {
				String TT_3 = wrapperAncImmunizationOBJ.getDateReceivedForTT_3().split("T")[0];
				ancWomenVaccineDetail
						.setReceivedDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(TT_3).getTime()));
			}
			ancWomenVaccineDetail.setReceivedFacilityName(wrapperAncImmunizationOBJ.getFacilityNameOfTT_3());
			ancWomenVaccineDetail.setModifiedBy(wrapperAncImmunizationOBJ.getModifiedBy());
			ancWomenVaccineDetailList.add(ancWomenVaccineDetail);

		}
		return ancWomenVaccineDetailList;
	}

	@Override
	public Long saveSysObstetricExamination(SysObstetricExamination obstetricExamination) {
		// TODO Auto-generated method stub
		Long r = null;
		SysObstetricExamination obstetricExaminationRS = sysObstetricExaminationRepo.save(obstetricExamination);

		if (obstetricExaminationRS != null)
			r = obstetricExaminationRS.getID();
		return r;
	}

	public SysObstetricExamination getSysObstetricExamination(Long benRegID, Long visitCode) {
		SysObstetricExamination sysObstetricExaminationData = sysObstetricExaminationRepo
				.getSysObstetricExaminationData(benRegID, visitCode);

		if (sysObstetricExaminationData.getReasonsForHRPDB() != null)
			sysObstetricExaminationData
					.setReasonsForHRP(sysObstetricExaminationData.getReasonsForHRPDB().split("\\|\\|"));

		return sysObstetricExaminationData;
	}

	@Override
	public String getANCCareDetails(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> resList = ancCareRepo.getANCCareDetails(beneficiaryRegID, visitCode);
		ANCCareDetails ancCareDetails = ANCCareDetails.getANCCareDetails(resList, 0);
		return new Gson().toJson(ancCareDetails);
	}

	@Override
	public String getANCWomenVaccineDetails(Long beneficiaryRegID, Long visitCode) {
		ArrayList<Object[]> resList = ancWomenVaccineRepo.getANCWomenVaccineDetails(beneficiaryRegID, visitCode);
		WrapperAncImmunization ancWomenVaccineDetails = ANCWomenVaccineDetail.getANCWomenVaccineDetails(resList);
		return new Gson().toJson(ancWomenVaccineDetails);
	}

	@Override
	public int updateBenAdherenceDetails(BenAdherence benAdherence) {
		int r = 0;
		String processed = benAdherenceRepo.getBenAdherenceDetailsStatus(benAdherence.getBeneficiaryRegID(),
				benAdherence.getBenVisitID(), benAdherence.getID());
		if (null != processed && !"N".equals(processed)) {
			processed = "U";
		} else {
			processed = "N";
		}
		r = benAdherenceRepo.updateBenAdherence(benAdherence.getToDrugs(), benAdherence.getDrugReason(),
				benAdherence.getToReferral(), benAdherence.getReferralReason(), benAdherence.getProgress(),
				benAdherence.getModifiedBy(), processed, benAdherence.getBeneficiaryRegID(),
				benAdherence.getBenVisitID(), benAdherence.getID());
		/*
		 * BenAdherence adherence= benAdherenceRepo.save(benAdherence); if(null
		 * !=adherence){ r=1; }
		 */
		return r;
	}

	@Override
	public int updateBenAncCareDetails(ANCCareDetails ancCareDetailsOBJ) throws ParseException {
		int r = 0;

		String processed = ancCareRepo.getBenANCCareDetailsStatus(ancCareDetailsOBJ.getBeneficiaryRegID(),
				ancCareDetailsOBJ.getVisitCode());
		if (null != processed && !"N".equals(processed)) {
			processed = "U";
		} else {
			processed = "N";
		}

		if (ancCareDetailsOBJ.getLmpDate() != null && !ancCareDetailsOBJ.getLmpDate().isEmpty()
				&& ancCareDetailsOBJ.getLmpDate().length() >= 10) {
			String lmpDate = ancCareDetailsOBJ.getLmpDate().split("T")[0];
			ancCareDetailsOBJ
					.setLastMenstrualPeriod_LMP(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(lmpDate).getTime()));
		}
		if (ancCareDetailsOBJ.getExpDelDt() != null && !ancCareDetailsOBJ.getExpDelDt().isEmpty()
				&& ancCareDetailsOBJ.getExpDelDt().length() >= 10) {
			String edDate = ancCareDetailsOBJ.getExpDelDt().split("T")[0];
			ancCareDetailsOBJ
					.setExpectedDateofDelivery(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(edDate).getTime()));
		}

		r = ancCareRepo.updateANCCareDetails(ancCareDetailsOBJ.getComolaintType(), ancCareDetailsOBJ.getDuration(),
				ancCareDetailsOBJ.getDescription(), ancCareDetailsOBJ.getLastMenstrualPeriod_LMP(),
				ancCareDetailsOBJ.getGestationalAgeOrPeriodofAmenorrhea_POA(), ancCareDetailsOBJ.getTrimesterNumber(),
				ancCareDetailsOBJ.getExpectedDateofDelivery(), ancCareDetailsOBJ.getPrimiGravida(),
				ancCareDetailsOBJ.getGravida_G(), ancCareDetailsOBJ.getTermDeliveries_T(),
				ancCareDetailsOBJ.getPretermDeliveries_P(), ancCareDetailsOBJ.getAbortions_A(),
				ancCareDetailsOBJ.getLivebirths_L(), ancCareDetailsOBJ.getBloodGroup(),
				ancCareDetailsOBJ.getModifiedBy(), processed, ancCareDetailsOBJ.getBeneficiaryRegID(),
				ancCareDetailsOBJ.getVisitCode(), ancCareDetailsOBJ.getStillBirth(), ancCareDetailsOBJ.getPara());
		return r;
	}

	@Override
	public int updateBenAncImmunizationDetails(WrapperAncImmunization wrapperAncImmunization) throws ParseException {
		int r = 0;

		List<ANCWomenVaccineDetail> ancWomenVaccineDetailList = getANCWomenVaccineDetail(wrapperAncImmunization);

		if (null != ancWomenVaccineDetailList) {

			String processed = "N";
			ANCWomenVaccineDetail ancWomenVaccine = ancWomenVaccineDetailList.get(0);
			ArrayList<Object[]> ancWomenVaccineStatuses = ancWomenVaccineRepo
					.getBenANCWomenVaccineStatus(ancWomenVaccine.getBeneficiaryRegID(), ancWomenVaccine.getVisitCode());
			Map<String, String> womenVaccineStatuses = new HashMap<String, String>();

			for (Object[] obj : ancWomenVaccineStatuses) {
				womenVaccineStatuses.put((String) obj[0], (String) obj[1]);
			}

			for (ANCWomenVaccineDetail ancWomenVaccineDetail : ancWomenVaccineDetailList) {
				processed = womenVaccineStatuses.get(ancWomenVaccineDetail.getVaccineName());
				if (null != processed && !processed.equals("N")) {
					processed = "U";
				} else {
					processed = "N";
				}

				r = ancWomenVaccineRepo.updateANCImmunizationDetails(ancWomenVaccineDetail.getStatus(),
						ancWomenVaccineDetail.getReceivedDate(), ancWomenVaccineDetail.getReceivedFacilityName(),
						ancWomenVaccineDetail.getModifiedBy(), processed, ancWomenVaccineDetail.getBeneficiaryRegID(),
						ancWomenVaccineDetail.getVisitCode(), ancWomenVaccineDetail.getVaccineName());

			}
		}
		return r;
	}

	@Override
	public int updateSysObstetricExamination(SysObstetricExamination obstetricExamination) {
		int r = 0;
		if (null != obstetricExamination) {
			String processed = sysObstetricExaminationRepo.getBenObstetricExaminationStatus(
					obstetricExamination.getBeneficiaryRegID(), obstetricExamination.getVisitCode());
			if (null != processed && !"N".equals(processed)) {
				processed = "U";
			} else {
				processed = "N";
			}
//			r = sysObstetricExaminationRepo.updateSysObstetricExamination(obstetricExamination.getFundalHeight(),
//					obstetricExamination.getfHAndPOA_Status(), obstetricExamination.getfHAndPOA_Interpretation(),
//					obstetricExamination.getFetalMovements(), obstetricExamination.getFetalHeartSounds(),
//					obstetricExamination.getFetalHeartRate_BeatsPerMinute(),
//					obstetricExamination.getFetalPositionOrLie(), obstetricExamination.getFetalPresentation(),
//					obstetricExamination.getAbdominalScars(), obstetricExamination.getSfh(),
//					obstetricExamination.getModifiedBy(), processed, obstetricExamination.getBeneficiaryRegID(),
//					obstetricExamination.getVisitCode());

			r = sysObstetricExaminationRepo.updateSysObstetricExamination(obstetricExamination.getFundalHeight(),
					obstetricExamination.getfHAndPOA_Status(), obstetricExamination.getfHAndPOA_Interpretation(),
					obstetricExamination.getFetalMovements(), obstetricExamination.getFetalHeartSounds(),
					obstetricExamination.getFetalHeartRate_BeatsPerMinute(),
					obstetricExamination.getFetalPositionOrLie(), obstetricExamination.getFetalPresentation(),
					obstetricExamination.getAbdominalScars(), obstetricExamination.getSfh(),
					obstetricExamination.getModifiedBy(), processed, obstetricExamination.getBeneficiaryRegID(),
					obstetricExamination.getVisitCode(),

					obstetricExamination.getMalPresentation(), obstetricExamination.getLowLyingPlacenta(),
					obstetricExamination.getVertebralDeformity(), obstetricExamination.getIsHRP(),
					obstetricExamination.getReasonsForHRPDB());
		}
		return r;
	}

}
