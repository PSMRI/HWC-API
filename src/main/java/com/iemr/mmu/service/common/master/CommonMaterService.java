package com.iemr.mmu.service.common.master;

import com.iemr.mmu.utils.exception.IEMRException;

public interface CommonMaterService {

	public String getVisitReasonAndCategories();

	public String getMasterDataForNurse(Integer visitCategoryID, Integer providerServiceMapID, String gender)
			throws IEMRException;

	public String getMasterDataForDoctor(Integer visitCategoryID, Integer providerServiceMapID, String gender,
			Integer facilityID, Integer vanID);

	public String getVaccineDetailsForCISID(Integer CISID, Integer visitCategoryID) throws IEMRException;

}
