package com.iemr.mmu.service.ncdscreening;

import com.iemr.mmu.utils.exception.IEMRException;

public interface DiabetesAndHypertensionOutcomeService {
	
	public String getHypertensionOutcome(String request) throws IEMRException;
	public String getDiabetesOutcome(String request) throws IEMRException;
}
