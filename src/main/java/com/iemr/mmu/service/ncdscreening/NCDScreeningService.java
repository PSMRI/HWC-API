package com.iemr.mmu.service.ncdscreening;

import com.google.gson.JsonObject;
import com.iemr.mmu.utils.exception.IEMRException;

public interface NCDScreeningService {

	public String getNCDScreeningDetails(Long beneficiaryRegID, Long benVisitID);

	String saveNCDScreeningNurseData(JsonObject requestOBJ, String Authorization) throws Exception;

	Integer updateNurseNCDScreeningDetails(JsonObject jsonObject) throws Exception;

	public Integer UpdateNCDScreeningHistory(JsonObject requestOBJ) throws Exception;

	public Long UpdateIDRSScreen(JsonObject requestOBJ) throws Exception;

	public String fetchConfirmedScreeningDisease(Long beneficiaryRegId) throws IEMRException;

	public String getNCDScreeningData(Long beneficiaryRegID, Long visitCode) throws IEMRException;

	public String updateNCDScreeningData(JsonObject jsonObject) throws IEMRException;
	
	public String getCbacData(Long beneficiaryRegId, Long visitCode) throws IEMRException;
}
