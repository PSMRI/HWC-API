/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.adolescent;

import java.sql.SQLException;
import java.text.ParseException;

import com.google.gson.JsonObject;
import com.iemr.mmu.utils.exception.IEMRException;

public interface AdolescentAndChildCareService {

	public String saveNurseData(JsonObject requestOBJ, String Authorization) throws SQLException, Exception;

	public String getBenVisitDetailsFrmNurseCAC(Long benRegID, Long visitCode) throws Exception;

	public String getBirthAndImmuniHistory(Long benRegID, Long visitCode) throws IEMRException;

	public String getBeneficiaryVitalDetails(Long beneficiaryRegID, Long visitCode) throws Exception;

	public String getBeneficiaryImmunizationServiceDetails(Long beneficiaryRegID, Long visitCode) throws Exception;

	public int updateBenVitalDetailsCAC(JsonObject vitalDetailsOBJ) throws Exception;

	public int updateBenHistoryDetails(JsonObject historyOBJ) throws IEMRException, ParseException;

	public int updateBenImmunServiceDetailsCAC(JsonObject immunServiceOBJ) throws Exception;

	public int saveDoctorDataCAC(JsonObject requestOBJ, String Authorization) throws Exception;

	public String getBenCaseRecordFromDoctorCAC(Long benRegID, Long visitCode) throws IEMRException;

	public Long updateDoctorDataCAC(JsonObject requestOBJ, String Authorization) throws Exception;

	public String getNurseDataCAC(Long benRegID, Long visitCode) throws Exception;

}
