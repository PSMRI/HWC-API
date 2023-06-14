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
package com.iemr.mmu.service.cancerScreening;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.mmu.data.doctor.CancerDiagnosis;
import com.iemr.mmu.repo.doctor.CancerDiagnosisRepo;

@Service
public class CSDoctorServiceImpl implements CSDoctorService {
	private CancerDiagnosisRepo cancerDiagnosisRepo;

	@Autowired
	public void setCancerDiagnosisRepo(CancerDiagnosisRepo cancerDiagnosisRepo) {
		this.cancerDiagnosisRepo = cancerDiagnosisRepo;
	}

	@Override
	public Long saveCancerDiagnosisData(CancerDiagnosis cancerDiagnosis) {

		cancerDiagnosis = getCancerDiagnosisObj(cancerDiagnosis);

		CancerDiagnosis response = cancerDiagnosisRepo.save(cancerDiagnosis);
		if (response != null)
			return response.getID();
		else
			return null;
	}

	public CancerDiagnosis getCancerDiagnosisObj(CancerDiagnosis cancerDiagnosis) {
		List<String> refrredToAdditionalServiceList = cancerDiagnosis.getRefrredToAdditionalServiceList();
		String refrredToAdditionalServiceData = "";
		if (refrredToAdditionalServiceList != null && refrredToAdditionalServiceList.size() > 0) {
			for (int i = 0; i < refrredToAdditionalServiceList.size(); i++) {
				if (i == (refrredToAdditionalServiceList.size() - 1))
					refrredToAdditionalServiceData += refrredToAdditionalServiceList.get(i);
				else
					refrredToAdditionalServiceData += refrredToAdditionalServiceList.get(i) + ",";
			}
		}
		cancerDiagnosis.setRefrredToAdditionalService(refrredToAdditionalServiceData);
		return cancerDiagnosis;
	}

	public Map<String, Object> getBenDoctorEnteredDataForCaseSheet(Long benRegID, Long visitCode) {
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("diagnosis", getBenCancerDiagnosisData(benRegID, visitCode));
		return resMap;
	}

	public CancerDiagnosis getBenCancerDiagnosisData(Long benRegID, Long visitCode) {
		CancerDiagnosis cancerDiagnosis = cancerDiagnosisRepo.getBenCancerDiagnosisDetails(benRegID, visitCode);
		if (null != cancerDiagnosis && null != cancerDiagnosis.getInstitute()) {
			cancerDiagnosis.setReferredToInstituteName(cancerDiagnosis.getInstitute().getInstitutionName());
		}
		if (cancerDiagnosis != null && cancerDiagnosis.getRefrredToAdditionalService() != null) {
			cancerDiagnosis.setRefrredToAdditionalServiceList(Arrays
					.stream(cancerDiagnosis.getRefrredToAdditionalService().split(",")).collect(Collectors.toList()));
		}

		return cancerDiagnosis;
	}

	public int updateCancerDiagnosisDetailsByDoctor(CancerDiagnosis cancerDiagnosis) {

		int response = 0;
		int recordsAvailable = 0;
		String processed = cancerDiagnosisRepo.getCancerDiagnosisStatuses(cancerDiagnosis.getBeneficiaryRegID(),
				cancerDiagnosis.getVisitCode());
		if (null != processed) {
			recordsAvailable = 1;
		}
		if (null != processed && !processed.equalsIgnoreCase("N")) {
			processed = "U";

		} else {
			processed = "N";
		}

		if (recordsAvailable > 0) {
			cancerDiagnosis = getCancerDiagnosisObj(cancerDiagnosis);
			response = cancerDiagnosisRepo.updateCancerDiagnosisDetailsByDoctor(
					cancerDiagnosis.getProvisionalDiagnosisPrimaryDoctor(), cancerDiagnosis.getRemarks(),
					cancerDiagnosis.getReferredToInstituteID(), cancerDiagnosis.getRefrredToAdditionalService(),
					cancerDiagnosis.getModifiedBy(), processed, cancerDiagnosis.getBeneficiaryRegID(),
					cancerDiagnosis.getVisitCode(), cancerDiagnosis.getRevisitDate(), cancerDiagnosis.getReferralReason());
		} else {
			// cancerDiagnosis.setCreatedBy(cancerDiagnosis.getModifiedBy());
			Long cancerDiagnosisRS = saveCancerDiagnosisData(cancerDiagnosis);
			if (cancerDiagnosisRS != null && cancerDiagnosisRS > 0) {
				response = 1;
			}
		}

		return response;

	}

}
