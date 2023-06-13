/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.family_planning;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.iemr.mmu.utils.exception.IEMRException;

@Service
public interface FamilyPlanningService {

	// save
	public String saveNurseDataFP(JsonObject requestOBJ, String Authorization) throws Exception;

	public int saveDoctorDataFP(JsonObject requestOBJ, String Authorization) throws Exception;

	// get
	public String getBenVisitDetailsFrmNurseFP(Long benRegID, Long visitCode) throws Exception;

	public String getBeneficiaryVitalDetailsFP(Long beneficiaryRegID, Long visitCode) throws Exception;

	public String getBeneficiaryFPDetailsFP(Long beneficiaryRegID, Long visitCode) throws Exception;

	public String getNurseDataFP(Long benRegID, Long visitCode) throws Exception;

	public String getBenCaseRecordFromDoctorFP(Long benRegID, Long visitCode) throws IEMRException;

	// update
	public String updateFPDataFP(JsonObject requestOBJ) throws IEMRException;

	public int updateBenVitalDetailsFP(JsonObject vitalDetailsOBJ) throws Exception;

	public Long updateDoctorDataFP(JsonObject requestOBJ, String Authorization) throws Exception;

}
