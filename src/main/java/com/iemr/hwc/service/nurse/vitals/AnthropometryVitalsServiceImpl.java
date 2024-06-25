package com.iemr.hwc.service.nurse.vitals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.hwc.repo.nurse.BenAnthropometryRepo;

@Service
public class AnthropometryVitalsServiceImpl implements AnthropometryVitalsService {

	@Autowired
	private BenAnthropometryRepo benAnthropometryRepo;
	
	@Override
	public String getBeneficiaryHeightDetails(Long benRegID) {
		// TODO Auto-generated method stub
		Long visitCode = benAnthropometryRepo.getBenLatestVisitCode(benRegID);
		if(visitCode==null)
		{
			return "Visit code is not found";
		}
		Double benHeight = benAnthropometryRepo.getBenLatestHeightDetails(visitCode, visitCode);
        if(benHeight == null)
        	return "No data found";
	
        return new Gson().toJson(benHeight);
	}
}
