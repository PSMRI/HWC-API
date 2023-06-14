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
package com.iemr.mmu.service.quickBlox;

import java.util.ArrayList;

import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.mapper.InputMapper;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import java.util.List;
import com.iemr.mmu.data.doctor.ChiefComplaintMaster;
import com.iemr.mmu.data.quickBlox.Quickblox;
import com.iemr.mmu.repo.masterrepo.covid19.CovidSymptomsMasterRepo;
import com.iemr.mmu.repo.quickBlox.QuickBloxRepo;
@Service
public class QuickbloxServiceImpl implements QuickbloxService {
	@Autowired
	private QuickBloxRepo quickBloxRepo;
	private InputMapper inputMapper = new InputMapper();
	@Override
	public String getQuickbloxIds(String requestObj) throws IEMRException {
		Quickblox obj = inputMapper.gson().fromJson(requestObj, Quickblox.class);
		Map<String, Object> resMap = new HashMap<String, Object>();
		// obj = quickBloxRepo.getQuickbloxIds(obj.getSpecialistUserID());
		//for (Object object : ids) {
		
		//obj.setSpecialistQuickbloxID((Long) ids.get(0));
		//obj.setSpecialistBenQuickbloxID((Long) ids.get(1));
		//}
		obj = quickBloxRepo.getQuickbloxIds(obj.getSpecialistUserID());
		if(obj.getSpecialistBenQuickbloxID()!=null && obj.getSpecialistQuickbloxID()!=null) {
		resMap.put("quickbloxIds", obj);
		return new Gson().toJson(resMap);
		}
		else
			 throw new IEMRException("quickblox user id not found for specialist");
	}

}
