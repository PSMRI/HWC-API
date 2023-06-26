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
package com.iemr.hwc.service.ncdscreening;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.repo.nurse.ncdscreening.BreastCancerScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.CervicalCancerScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.DiabetesScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.HypertensionScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.OralCancerScreeningRepo;
import com.iemr.hwc.utils.exception.IEMRException;

@Service
public class CommonNcdScreeningService {

	@Autowired
	private BreastCancerScreeningRepo breastCancerScreeningRepo;

	@Autowired
	private CervicalCancerScreeningRepo cervicalCancerScreeningRepo;

	@Autowired
	private DiabetesScreeningRepo diabetesScreeningRepo;

	@Autowired
	private HypertensionScreeningRepo hypertensionScreeningRepo;

	@Autowired
	private OralCancerScreeningRepo oralCancerScreeningRepo;

	public int updateScreeningConfirmedStatusByDocSpecialist(PrescriptionDetail prescriptionDetail,
			CommonUtilityClass commonUtilityClass) throws IEMRException {
		// diabetes outcome
		if (prescriptionDetail.getDiabetesScreeningConfirmed() != null) {
			int i = diabetesScreeningRepo.updateDiabetesScreeningConfirmedStatus(
					commonUtilityClass.getBeneficiaryRegID(), commonUtilityClass.getVisitCode(),
					prescriptionDetail.getDiabetesScreeningConfirmed());
		}
		// htn outcome
		if (prescriptionDetail.getHypertensionScreeningConfirmed() != null) {
			int i = hypertensionScreeningRepo.updateHypertensionScreeningConfirmedStatus(
					commonUtilityClass.getBeneficiaryRegID(), commonUtilityClass.getVisitCode(),
					prescriptionDetail.getHypertensionScreeningConfirmed());
		}
		// oral outcome
		if (prescriptionDetail.getOralCancerConfirmed() != null) {
			int i = oralCancerScreeningRepo.updateOralScreeningConfirmedStatus(commonUtilityClass.getBeneficiaryRegID(),
					commonUtilityClass.getVisitCode(), prescriptionDetail.getOralCancerConfirmed());
		}
		// breast outcome
		if (prescriptionDetail.getBreastCancerConfirmed() != null) {
			int i = breastCancerScreeningRepo.updateBreastScreeningConfirmedStatus(
					commonUtilityClass.getBeneficiaryRegID(), commonUtilityClass.getVisitCode(),
					prescriptionDetail.getBreastCancerConfirmed());
		}
		// cervical outcome
		if (prescriptionDetail.getCervicalCancerConfirmed() != null) {
			int i = cervicalCancerScreeningRepo.updateCervicalScreeningConfirmedStatus(
					commonUtilityClass.getBeneficiaryRegID(), commonUtilityClass.getVisitCode(),
					prescriptionDetail.getCervicalCancerConfirmed());

		}
		return 1;
	}

}
