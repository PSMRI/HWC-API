/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.common.master;

import java.util.List;

public interface NCDScreeningMasterService {

	public List<Object[]> getNCDScreeningConditions(); 

	public List<Object[]> getNCDScreeningReasons();
	
	public List<Object[]> getBPAndDiabeticStatus(Boolean isBPStatus);
	
	public List<Object[]> getNCDTest();
	
//	public String getNCDScreeningMasterData();

	public List<Object[]> getChiefComplaintMaster();

	public String getNCDScreeningMasterData(Integer visitCategoryID, Integer providerServiceMapID, String gender);
	
}
