package com.iemr.mmu.service.registrar;

import com.google.gson.JsonObject;
import com.iemr.mmu.data.registrar.BeneficiaryData;

public interface RegistrarService {
	public String getRegWorkList(int servicePointID);

	public BeneficiaryData createBeneficiary(JsonObject benD);

	public Long createBeneficiaryDemographic(JsonObject benD, Long benRegID);

	public Long createBeneficiaryPhoneMapping(JsonObject benD, Long benRegID);

	public Long createBeneficiaryDemographicAdditional(JsonObject benD, Long benRegID);

	public Long createBeneficiaryImage(JsonObject benD, Long benRegID);

	public String getQuickSearchBenData(String benID);

	public String getBenImage(Long benRegID);

	public String getBeneficiaryDetails(Long benRegID);

	int updateBeneficiary(JsonObject benD);

	int updateBeneficiaryDemographic(JsonObject benD, Long benRegID);

	int updateBeneficiaryPhoneMapping(JsonObject benD, Long benRegID);

	int updateBenGovIdMapping(JsonObject benD, Long benRegID);

	int updateBeneficiaryDemographicAdditional(JsonObject benD, Long benRegID);

	int updateBeneficiaryImage(JsonObject benD, Long benRegID);

}
