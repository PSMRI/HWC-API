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
package com.iemr.hwc.service.cancerScreening;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.hwc.data.doctor.CancerDiagnosis;
import com.iemr.hwc.repo.doctor.CancerDiagnosisRepo;

@Service
public class CSOncologistServiceImpl implements CSOncologistService {

	private CancerDiagnosisRepo cancerDiagnosisRepo;

	@Autowired
	public void setCancerDiagnosisRepo(CancerDiagnosisRepo cancerDiagnosisRepo) {
		this.cancerDiagnosisRepo = cancerDiagnosisRepo;
	}

	@Override
	public int updateCancerDiagnosisDetailsByOncologist(CancerDiagnosis cancerDiagnosis) {

		int response = 0;
		int recordsAvailable = 0;
		String processed= cancerDiagnosisRepo.getCancerDiagnosisStatuses(cancerDiagnosis.getBeneficiaryRegID(),
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
			response = cancerDiagnosisRepo.updateDetailsByOncologist(
					cancerDiagnosis.getProvisionalDiagnosisOncologist(), cancerDiagnosis.getBeneficiaryRegID(),
					cancerDiagnosis.getVisitCode(), cancerDiagnosis.getModifiedBy(), processed);
		} else {
			cancerDiagnosis.setCreatedBy(cancerDiagnosis.getModifiedBy());
			CancerDiagnosis cancerDiagnosisRS = cancerDiagnosisRepo.save(cancerDiagnosis);
			if (cancerDiagnosisRS != null) {
				response = 1;
			}
		}

		return response;

	}
}
