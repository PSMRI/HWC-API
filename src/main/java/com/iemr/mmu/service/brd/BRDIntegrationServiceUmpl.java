package com.iemr.mmu.service.brd;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.iemr.mmu.data.brd.BRDIntegrationData;
import com.iemr.mmu.repo.brd.BRDIntegrationRepository;

@Service
public class BRDIntegrationServiceUmpl implements BRDIntegrationService{
	
	@Autowired
	private BRDIntegrationRepository repo;

	@Override
	public String getData(String startDate, String endDate) {
		ArrayList<Object[]> data = repo.getData(startDate,endDate);
		List<BRDIntegrationData> brdDetails = BRDIntegrationData.getBRDDetails(data);
		
		return  new Gson().toJson(brdDetails);
	}

}
