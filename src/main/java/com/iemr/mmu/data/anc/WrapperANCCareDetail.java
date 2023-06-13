/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.anc;

import java.util.List;

import com.iemr.mmu.data.doctor.CancerSignAndSymptoms;

public class WrapperANCCareDetail {
	
	private ANCCareDetails ancCareDetails;
	
	private List<ANCWomenVaccineDetail> ancWomenVaccineDetail;

	public ANCCareDetails getAncCareDetails() {
		return ancCareDetails;
	}

	public void setAncCareDetails(ANCCareDetails ancCareDetails) {
		this.ancCareDetails = ancCareDetails;
	}

	public List<ANCWomenVaccineDetail> getAncWomenVaccineDetail() {
		return ancWomenVaccineDetail;
	}

	public void setAncWomenVaccineDetail(List<ANCWomenVaccineDetail> ancWomenVaccineDetail) {
		this.ancWomenVaccineDetail = ancWomenVaccineDetail;
	}

}
