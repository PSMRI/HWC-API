/* LicenseInfo : Copyright © 2023 Piramal */ 
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
