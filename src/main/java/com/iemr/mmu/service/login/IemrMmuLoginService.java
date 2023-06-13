/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.login;

public interface IemrMmuLoginService {

	public String getUserServicePointVanDetails(Integer userID);

	public String getServicepointVillages(Integer servicePointID);

	public String getUserVanSpDetails(Integer userID, Integer providerServiceMapID);
	
	public String getUserSpokeDetails(Integer psmId);

}
