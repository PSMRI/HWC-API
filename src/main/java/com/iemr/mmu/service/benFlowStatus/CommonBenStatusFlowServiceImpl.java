/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.benFlowStatus;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iemr.mmu.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.mmu.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.mmu.repo.nurse.BenVisitDetailRepo;
import com.iemr.mmu.utils.mapper.InputMapper;

/***
 * 
 * @author NE298657
 *
 */
@Service
public class CommonBenStatusFlowServiceImpl implements CommonBenStatusFlowService {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
	private BenVisitDetailRepo benVisitDetailRepo;
	
	@Value("${nurseWL}")
	private Integer nurseWL;

	@Autowired
	public void setBenVisitDetailRepo(BenVisitDetailRepo benVisitDetailRepo) {
		this.benVisitDetailRepo = benVisitDetailRepo;
	}

	@Autowired
	public void setBeneficiaryFlowStatusRepo(BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo) {
		this.beneficiaryFlowStatusRepo = beneficiaryFlowStatusRepo;
	}

	public int createBenFlowRecord(String requestOBJ, Long beneficiaryRegID, Long beneficiaryID) {
		BeneficiaryFlowStatus objRS = null;
		int returnOBJ = 0;
		try {
			BeneficiaryFlowStatus obj = getBenFlowRecordObj(requestOBJ, beneficiaryRegID, beneficiaryID);

			if (beneficiaryRegID != null && beneficiaryID != null && beneficiaryRegID > 0 && beneficiaryID > 0) {
				objRS = beneficiaryFlowStatusRepo.save(obj);
				if (objRS != null)
					returnOBJ = 1;
				else
					returnOBJ = 0;
			} else {
				Calendar cal = Calendar.getInstance();
				if (nurseWL != null && nurseWL > 0 && nurseWL <= 30)
					cal.add(Calendar.DAY_OF_YEAR, -nurseWL);
				else
					cal.add(Calendar.DAY_OF_YEAR, -7);
				long sevenDaysAgo = cal.getTimeInMillis();
				ArrayList<Long> benFlowIDList = beneficiaryFlowStatusRepo.checkBenAlreadyInNurseWorkList(
						obj.getBeneficiaryRegID(), obj.getProviderServiceMapID(), obj.getVanID(),new Timestamp(sevenDaysAgo));
				if (benFlowIDList != null && benFlowIDList.size() > 0) {
					// update i_ben_flow table for updated beneficiary id
					returnOBJ = 3;
				} else {
					objRS = beneficiaryFlowStatusRepo.save(obj);
					if (objRS != null)
						returnOBJ = 1;
					else
						returnOBJ = 0;
				}

			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in ben flow creation = " + e);
		}
		return returnOBJ;
	}

	public int updateBenFlowNurseAfterNurseActivity(Long benFlowID, Long benRegID, Long benVisitID, String visitReason,
			String visitCategory, Short nurseFlag, Short docFlag, Short labIteration, Short radiologistFlag,
			Short oncologistFlag, Long visitCode, Integer vanID, Short specialistFlag, Timestamp tcDate,
			Integer tcSpecialistUserID) {
		int i = 0;
		try {
			i = beneficiaryFlowStatusRepo.updateBenFlowStatusAfterNurseActivity(benFlowID, benRegID, benVisitID,
					visitReason, visitCategory, nurseFlag, docFlag, labIteration, radiologistFlag, oncologistFlag,
					visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID);
			// System.out.println("hello");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in ben flow creation = " + e);
		}
		return i;
	}
	
	public int updateBenFlowNurseAfterNurseActivityANC(Long benFlowID, Long benRegID, Long benVisitID, String visitReason,
			String visitCategory, Short nurseFlag, Short docFlag, Short labIteration, Short radiologistFlag,
			Short oncologistFlag, Long visitCode, Integer vanID, Short specialistFlag, Timestamp tcDate,
			Integer tcSpecialistUserID,Short labTechnician) {
		int i = 0;
		try {
			i = beneficiaryFlowStatusRepo.updateBenFlowStatusAfterNurseActivityANC(benFlowID, benRegID, benVisitID,
					visitReason, visitCategory, nurseFlag, docFlag, labIteration, radiologistFlag, oncologistFlag,
					visitCode, vanID, specialistFlag, tcDate, tcSpecialistUserID,labTechnician);
			// System.out.println("hello");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error in ben flow creation = " + e);
		}
		return i;
	}

	public int updateBenFlowNurseAfterNurseUpdateNCD_Screening(Long benFlowID, Long benRegID, Short nurseFlag) {
		int i = 0;
		try {
			i = beneficiaryFlowStatusRepo.updateBenFlowStatusAfterNurseDataUpdateNCD_Screening(benFlowID, benRegID,
					nurseFlag);
		} catch (Exception e) {
			logger.error("Error in ben flow updatation after nurse update NCD - screening = " + e);
		}
		return i;
	}

	private BeneficiaryFlowStatus getBenFlowRecordObj(String requestOBJ, Long beneficiaryRegID, Long beneficiaryID)
			throws Exception {

		BeneficiaryFlowStatus obj = InputMapper.gson().fromJson(requestOBJ, BeneficiaryFlowStatus.class);

		if (obj.getI_bendemographics().getDistrictID() != null)
			obj.setDistrictID(obj.getI_bendemographics().getDistrictID());
		if (obj.getI_bendemographics().getDistrictName() != null)
			obj.setDistrictName(obj.getI_bendemographics().getDistrictName());

		if (obj.getI_bendemographics().getDistrictBranchID() != null)
			obj.setVillageID(obj.getI_bendemographics().getDistrictBranchID());
		if (obj.getI_bendemographics().getDistrictBranchName() != null)
			obj.setVillageName(obj.getI_bendemographics().getDistrictBranchName());

		if (obj.getI_bendemographics().getServicePointID() != null)
			obj.setServicePointID(obj.getI_bendemographics().getServicePointID());
		if (obj.getI_bendemographics().getServicePointName() != null)
			obj.setServicePointName(obj.getI_bendemographics().getServicePointName());

		if (beneficiaryRegID != null && obj.getBeneficiaryRegID() == null)
			obj.setBeneficiaryRegID(beneficiaryRegID);

		if (beneficiaryID != null && obj.getBeneficiaryID() == null)
			obj.setBeneficiaryID(beneficiaryID);

		if (obj.getBenPhoneMaps() != null && obj.getBenPhoneMaps().size() > 0
				&& obj.getBenPhoneMaps().get(0).getPhoneNo() != null)
			obj.setPreferredPhoneNum(obj.getBenPhoneMaps().get(0).getPhoneNo());

		if (obj.getGenderID() == null)
		{
			if(obj.getM_gender() !=null)
				obj.setGenderID(obj.getM_gender().getGenderID());
		}

		if (obj.getGenderName() == null)
			obj.setGenderName(obj.getM_gender().getGenderName());

		String ageDetails = "";
		int age_val = 0;
		if (obj.getdOB() != null) {

			Date date = new Date(obj.getdOB().getTime());
			Calendar cal = Calendar.getInstance();

			cal.setTime(date);

			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);

			java.time.LocalDate todayDate = java.time.LocalDate.now();
			java.time.LocalDate birthdate = java.time.LocalDate.of(year, month, day);
			Period p = Period.between(birthdate, todayDate);

			int d = p.getDays();
			int m = p.getMonths();
			int y = p.getYears();
			// System.out.println("helloo...");

			if (y > 0) {
				ageDetails = y + " years - " + m + " months";
				age_val = y;
			} else {
				if (m > 0) {
					ageDetails = m + " months - " + d + " days";
				} else {
					ageDetails = d + " days";
				}
			}

		}

		obj.setAge(ageDetails);
		obj.setBen_age_val(age_val);

		if (obj.getLastName() != null)
			obj.setBenName(obj.getFirstName() + " " + obj.getLastName());
		else
			obj.setBenName(obj.getFirstName());

		Short benVisitCount = benVisitDetailRepo.getVisitCountForBeneficiary(obj.getBeneficiaryRegID());

		if (benVisitCount != null && benVisitCount >= 0) {
			benVisitCount = (short) (benVisitCount + 1);
		} else {
			benVisitCount = 1;
		}
		obj.setBenVisitNo(benVisitCount);
		obj.setNurseFlag((short) 1);
		obj.setDoctorFlag((short) 0);
		obj.setPharmacist_flag((short) 0);
		obj.setAgentId(obj.getCreatedBy());

		if (obj.getCreatedDate() != null)
			obj.setRegistrationDate(obj.getCreatedDate());
		else
			obj.setRegistrationDate(new Timestamp(System.currentTimeMillis()));

		return obj;
	}

	public int updateBenFlowAfterDocData(Long benFlowID, Long benRegID, Long benID, Long benVisitID, short docFlag,
			short pharmaFlag, short oncologistFlag, short tcSpecialistFlag, int tcUserID, Timestamp tcDate,short labTechnicianFlag) {
		int i = 0;
		try {
			i = beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivity(benFlowID, benRegID, benID, docFlag,
					pharmaFlag, oncologistFlag, tcSpecialistFlag, tcUserID, tcDate,labTechnicianFlag);
		} catch (Exception e) {
			logger.error("Error in ben flow creation = " + e);
		}
		return i;
	}

	public int updateBenFlowAfterDocDataFromSpecialist(Long benFlowID, Long benRegID, Long benID, Long benVisitID,
			short docFlag, short pharmaFlag, short oncologistFlag, short tcSpecialistFlag,short labTechnicianFlag) {
		int i = 0;
		try {
			i = beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivitySpecialist(benFlowID, benRegID, benID,
					docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag,labTechnicianFlag);
		} catch (Exception e) {
			logger.error("Error in ben flow creation = " + e);
		}
		return i;
	}
	
	public int updateBenFlowAfterDocDataFromSpecialistANC(Long benFlowID, Long benRegID, Long benID, Long benVisitID,
			short docFlag, short pharmaFlag, short oncologistFlag, short tcSpecialistFlag,short labTechnicianFlag) {
		int i = 0;
		try {
			i = beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivitySpecialistANC(benFlowID, benRegID, benID,
					docFlag, pharmaFlag, oncologistFlag, tcSpecialistFlag,labTechnicianFlag);
		} catch (Exception e) {
			logger.error("Error in ben flow creation = " + e);
		}
		return i;
	}

	public int updateBenFlowAfterDocDataUpdate(Long benFlowID, Long benRegID, Long benID, Long benVisitID,
			short docFlag, short pharmaFlag, short oncologistFlag, short tcSpecialistFlag, int tcUserID,
			Timestamp tcDate,short labTechnicianFlag) throws Exception {
		int i = 0;
		try {
			Short pharmaF = beneficiaryFlowStatusRepo.getPharmaFlag(benFlowID);
			Short pharmaF1;

			if (pharmaF != null && pharmaF == 1)
				pharmaF1 = pharmaF;
			else
				pharmaF1 = pharmaFlag;

			i = beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivity(benFlowID, benRegID, benID, docFlag,
					pharmaF1, oncologistFlag, tcSpecialistFlag, tcUserID, tcDate,labTechnicianFlag);
		} catch (Exception e) {
			logger.error("Error in ben flow creation = " + e);
			throw new Exception(e);
		}
		return i;
	}

	public int updateBenFlowAfterDocDataUpdateTCSpecialist(Long benFlowID, Long benRegID, Long benID, Long benVisitID,
			short docFlag, short pharmaFlag, short oncologistFlag, short tcSpecialistFlag, int tcUserID,
			Timestamp tcDate, short labTechnicianFlag) throws Exception {
		int i = 0;
		try {
			Short pharmaF = beneficiaryFlowStatusRepo.getPharmaFlag(benFlowID);
			Short pharmaF1;

			if (pharmaF != null && pharmaF == 1)
				pharmaF1 = pharmaF;
			else
				pharmaF1 = pharmaFlag;

			i = beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivityTCSpecialist(benFlowID, benRegID, benID,
					pharmaF1, oncologistFlag, tcSpecialistFlag,labTechnicianFlag);
		} catch (Exception e) {
			logger.error("Error in ben flow creation = " + e);
			throw new Exception(e);
		}
		return i;
	}

	public int updateFlowAfterLabResultEntry(Long benFlowID, Long benRegID, Long benVisitID, Short nurseFlag,
			Short doctorFlag, Short labFlag) {
		int i = beneficiaryFlowStatusRepo.updateBenFlowStatusAfterLabResultEntry(benFlowID, benRegID, nurseFlag,
				doctorFlag);
		return i;

	}

	public int updateFlowAfterLabResultEntryForTCSpecialist(Long benFlowID, Long benRegID, Short specialistFlag) {
		int i = beneficiaryFlowStatusRepo.updateBenFlowStatusAfterLabResultEntryForSpecialist(benFlowID, benRegID,
				specialistFlag);
		return i;

	}

}
