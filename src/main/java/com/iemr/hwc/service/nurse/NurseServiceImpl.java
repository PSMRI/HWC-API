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
package com.iemr.hwc.service.nurse;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.fhir.dto.visitDetailsMain.visitDetails.BenVisitsDTO;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.iemr.hwc.data.nurse.BenFamilyCancerHistory;
import com.iemr.hwc.data.nurse.BenObstetricCancerHistory;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;

@Service
public class NurseServiceImpl implements NurseService {

	private BenVisitDetailRepo benVisitDetailRepo;

	@Autowired
	private BeneficiaryFlowStatusRepo benFlowStatusRepo;

	@Autowired
	public void setBenVisitDetailRepo(BenVisitDetailRepo benVisitDetailRepo) {
		this.benVisitDetailRepo = benVisitDetailRepo;
	}

	@Override
	public String savePatientVisitDetails() {
		BenFamilyCancerHistory obj = new BenFamilyCancerHistory();
		BenObstetricCancerHistory obj1 = new BenObstetricCancerHistory();
		obj.setCreatedBy("neeraj");
		obj1.setCreatedBy("neeraj");

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/nurse/testrest1", obj,
				String.class);
		ResponseEntity<String> response1 = restTemplate.postForEntity("http://localhost:8080/nurse/testrest2", obj1,
				String.class);

		return "hii";
	}

	public String getBeneficiaryVisitHistory(Long benRegID) {
		Map<String, Object> resMap = new HashMap<>();
		List<BeneficiaryVisitDetail> benVisitDetailsOBJs = benVisitDetailRepo.getBeneficiaryVisitHistory(benRegID);

		List<BeneficiaryVisitDetail> benVisitDetailsList = new ArrayList<BeneficiaryVisitDetail>();
		for (BeneficiaryVisitDetail benVisitDetailsOBJ : benVisitDetailsOBJs) {
			BeneficiaryVisitDetail benVisitDetailsOBJ1 = new BeneficiaryVisitDetail(benVisitDetailsOBJ.getBenVisitID(),
					benVisitDetailsOBJ.getBeneficiaryRegID(), benVisitDetailsOBJ.getVisitCode(),
					benVisitDetailsOBJ.getProviderServiceMapID(),
					benVisitDetailsOBJ.getVisitDateTime(), benVisitDetailsOBJ.getVisitNo(),
					benVisitDetailsOBJ.getVisitReasonID(), benVisitDetailsOBJ.getVisitReason(),
					benVisitDetailsOBJ.getVisitCategoryID(), benVisitDetailsOBJ.getVisitCategory(),
					benVisitDetailsOBJ.getPregnancyStatus(), benVisitDetailsOBJ.getrCHID(),
					benVisitDetailsOBJ.getHealthFacilityType(), benVisitDetailsOBJ.getHealthFacilityLocation(),
					benVisitDetailsOBJ.getReportFilePath(), benVisitDetailsOBJ.getDeleted(),
					benVisitDetailsOBJ.getProcessed(), benVisitDetailsOBJ.getCreatedBy(),
					benVisitDetailsOBJ.getCreatedDate(), benVisitDetailsOBJ.getModifiedBy(),
					benVisitDetailsOBJ.getLastModDate());
			benVisitDetailsList.add(benVisitDetailsOBJ1);
		}

		resMap.put("benVisitDetails", benVisitDetailsList);

		return new Gson().toJson(resMap);
	}

	public List<BenVisitsDTO> getVisitByLocationAndLastModifDate(Integer villageID, Timestamp lastModifDate) {
		List<BeneficiaryFlowStatus> listBenVisitFlowsOBJs = benFlowStatusRepo.getVisitByLocationAndLastModifDate(villageID, lastModifDate);
		List<BenVisitsDTO> benVisitDetailsList = new ArrayList<>();
		for (BeneficiaryFlowStatus beneficiaryFlowStatus : listBenVisitFlowsOBJs) {
			BeneficiaryVisitDetail benVisitDetailsOBJ1 = benVisitDetailRepo.getVisitDetailsByVisitIDAndLastModifDate(beneficiaryFlowStatus.getBenVisitID(), lastModifDate);
			BenVisitsDTO benVisitsDTO = new BenVisitsDTO();
			benVisitsDTO.setBenFlowID(beneficiaryFlowStatus.getBenFlowID());
			benVisitsDTO.setSessionID(beneficiaryFlowStatus.getVisitSession());
			benVisitsDTO.setBeneficiaryID(beneficiaryFlowStatus.getBeneficiaryID());
			benVisitsDTO.setBenVisitDetails(benVisitDetailsOBJ1);
			benVisitDetailsList.add(benVisitsDTO);
		}
		return benVisitDetailsList;
	}

}
