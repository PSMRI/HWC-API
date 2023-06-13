/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.neonatal;

import java.sql.SQLException;
import java.text.ParseException;

import com.google.gson.JsonObject;
import com.iemr.mmu.utils.exception.IEMRException;

public interface NeonatalService {

	public String saveNurseData(JsonObject requestOBJ, String Authorization) throws SQLException, Exception;

	public String getBenVisitDetailsFrmNurseNNI(Long benRegID, Long visitCode) throws Exception;

	public String getBeneficiaryVitalDetails(Long beneficiaryRegID, Long benVisitID) throws Exception;

	public String getBirthAndImmuniHistory(Long benRegID, Long visitCode) throws IEMRException;

	public String getBeneficiaryImmunizationServiceDetails(Long beneficiaryRegID, Long benVisitID) throws Exception;

	// public String getBenImmunizationServiceHistory(Long beneficiaryRegID) throws
	// Exception;

	public int saveDoctorDataNNI(JsonObject requestOBJ, String Authorization) throws Exception;

	public String getBenCaseRecordFromDoctorNNI(Long benRegID, Long visitCode) throws IEMRException;

	public String getNurseDataNNI(Long benRegID, Long visitCode) throws Exception;

	public int updateBenVitalDetailsNNI(JsonObject vitalDetailsOBJ) throws Exception;

	public int updateBenHistoryDetails(JsonObject historyOBJ) throws IEMRException, ParseException;

	public int updateBenImmunServiceDetailsNNI(JsonObject immunServiceOBJ) throws Exception;

	public Long updateDoctorDataNNI(JsonObject requestOBJ, String Authorization) throws Exception;
}
