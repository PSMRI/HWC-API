/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.neonatal;

import org.springframework.stereotype.Service;

import com.iemr.mmu.utils.exception.IEMRException;

@Service
public interface NeonatalMasterService {
	
	public  String getNurseMasterDataNeonatal(Integer visitCategoryID, int psmID, String gender) throws IEMRException;
	}


