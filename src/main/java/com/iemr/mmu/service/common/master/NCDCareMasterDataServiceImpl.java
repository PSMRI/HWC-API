/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
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
