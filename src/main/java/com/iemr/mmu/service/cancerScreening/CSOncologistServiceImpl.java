/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.cancerScreening;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.mmu.data.doctor.CancerDiagnosis;
import com.iemr.mmu.repo.doctor.CancerDiagnosisRepo;

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
