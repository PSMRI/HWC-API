package com.iemr.mmu.service.common.transaction;

import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.utils.exception.IEMRException;

public interface CommonNurseService {
	public Long saveBeneficiaryVisitDetails(BeneficiaryVisitDetail beneficiaryVisitDetail);

	public String getBenSymptomaticData(Long benRegID) throws Exception;

	public String getBenPreviousDiabetesData(Long benRegID) throws Exception;

	public String getBenPreviousReferralData(Long benRegID) throws Exception;

	public String calculateBMIStatus(String request) throws IEMRException;
}
