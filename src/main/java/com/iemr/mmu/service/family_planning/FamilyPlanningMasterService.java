/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.family_planning;

import org.springframework.stereotype.Service;

import com.iemr.mmu.utils.exception.IEMRException;

@Service
public interface FamilyPlanningMasterService {
	public String getMasterDataFP(Integer visitCategoryID, int psmID, String gender) throws IEMRException;
}
