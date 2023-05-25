package com.iemr.mmu.data.doctor;

import java.util.List;

public class WrapperCancerSymptoms {
	
	private CancerSignAndSymptoms cancerSignAndSymptoms;
	
	private List<CancerLymphNodeDetails> cancerLymphNodeDetails;

	public CancerSignAndSymptoms getCancerSignAndSymptoms() {
		return cancerSignAndSymptoms;
	}

	public void setCancerSignAndSymptoms(CancerSignAndSymptoms cancerSignAndSymptoms) {
		this.cancerSignAndSymptoms = cancerSignAndSymptoms;
	}

	public List<CancerLymphNodeDetails> getCancerLymphNodeDetails() {
		return cancerLymphNodeDetails;
	}

	public void setCancerLymphNodeDetails(List<CancerLymphNodeDetails> cancerLymphNodeDetails) {
		this.cancerLymphNodeDetails = cancerLymphNodeDetails;
	}
	
}
