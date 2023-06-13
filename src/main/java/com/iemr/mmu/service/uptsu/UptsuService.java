/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.uptsu;

import java.util.List;

import com.iemr.mmu.data.uptsu.Referred104Details;
import com.iemr.mmu.data.uptsu.Worklist104Data;

public interface UptsuService {
	
	public String getActionMaster();
	
	String getWorklist(Integer vanId);

	Referred104Details saveReferredDetails(Referred104Details requestObj);

}
