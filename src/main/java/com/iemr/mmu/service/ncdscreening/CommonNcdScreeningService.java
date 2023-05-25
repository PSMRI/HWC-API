package com.iemr.mmu.service.ncdscreening;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.mmu.data.nurse.CommonUtilityClass;
import com.iemr.mmu.data.quickConsultation.PrescriptionDetail;
import com.iemr.mmu.repo.nurse.ncdscreening.BreastCancerScreeningRepo;
import com.iemr.mmu.repo.nurse.ncdscreening.CervicalCancerScreeningRepo;
import com.iemr.mmu.repo.nurse.ncdscreening.DiabetesScreeningRepo;
import com.iemr.mmu.repo.nurse.ncdscreening.HypertensionScreeningRepo;
import com.iemr.mmu.repo.nurse.ncdscreening.OralCancerScreeningRepo;
import com.iemr.mmu.utils.exception.IEMRException;

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
