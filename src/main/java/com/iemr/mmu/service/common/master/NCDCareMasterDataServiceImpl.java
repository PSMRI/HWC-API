/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.common.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.mmu.data.masterdata.ncdcare.NCDCareType;
import com.iemr.mmu.data.masterdata.ncdscreening.NCDScreeningCondition;
import com.iemr.mmu.repo.masterrepo.ncdCare.NCDCareTypeRepo;

@Service
public class NCDCareMasterDataServiceImpl implements NCDCareMasterDataService
{
	private NCDScreeningMasterServiceImpl ncdScreeningMasterServiceImpl;
	private NCDCareTypeRepo ncdCareTypeRepo;
	
	@Autowired
	public void setNcdScreeningMasterServiceImpl(NCDScreeningMasterServiceImpl ncdScreeningMasterServiceImpl)
	{
		this.ncdScreeningMasterServiceImpl = ncdScreeningMasterServiceImpl;
	}
	
	@Autowired
	public void setNcdCareTypeRepo(NCDCareTypeRepo ncdCareTypeRepo)
	{
		this.ncdCareTypeRepo = ncdCareTypeRepo;
	}
	
	@Deprecated
	@Override
	public String getNCDCareMasterData() {
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		resMap.put("ncdCareConditions", NCDScreeningCondition.getNCDScreeningCondition((ArrayList<Object[]>) 
																	ncdScreeningMasterServiceImpl.getNCDScreeningConditions()));
		resMap.put("ncdCareTypes", NCDCareType.getNCDCareTypes((ArrayList<Object[]>) ncdCareTypeRepo.getNCDCareTypes()));
		
		return new Gson().toJson(resMap);
	}

}
