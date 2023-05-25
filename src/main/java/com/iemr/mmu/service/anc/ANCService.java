package com.iemr.mmu.service.anc;

import com.google.gson.JsonObject;
import com.iemr.mmu.data.anc.HrpStatusAndReasonsRequestModel;
import com.iemr.mmu.data.anc.SysObstetricExamination;
import com.iemr.mmu.utils.exception.IEMRException;

public interface ANCService {

	public String saveANCNurseData(JsonObject requestOBJ, String Authorization) throws Exception;

	// int UpdateANCVisitDetails(JsonObject jsnOBJ) throws Exception;

	String getBenVisitDetailsFrmNurseANC(Long benRegID, Long visitCode);

	String getBenANCHistoryDetails(Long benRegID, Long visitCode);

	String getANCExaminationDetailsData(Long benRegID, Long visitCode);

	String getBeneficiaryVitalDetails(Long beneficiaryRegID, Long visitCode);

	String getBenANCDetailsFrmNurseANC(Long benRegID, Long visitCode) throws IEMRException;

	String getHRPStatus(HrpStatusAndReasonsRequestModel hrpStatusAndReasonsRequestModel) throws Exception;

	public SysObstetricExamination getHRPInformation(Long beneficiaryRegId) throws IEMRException;

}
