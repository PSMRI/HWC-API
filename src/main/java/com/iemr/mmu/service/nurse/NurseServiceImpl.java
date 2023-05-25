package com.iemr.mmu.service.nurse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.iemr.mmu.data.nurse.BenFamilyCancerHistory;
import com.iemr.mmu.data.nurse.BenObstetricCancerHistory;
import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.repo.nurse.BenVisitDetailRepo;

@Service
public class NurseServiceImpl implements NurseService {

	private BenVisitDetailRepo benVisitDetailRepo;

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

}
