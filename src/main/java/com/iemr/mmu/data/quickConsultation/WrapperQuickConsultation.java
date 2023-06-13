/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.quickConsultation;

import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class WrapperQuickConsultation {
	
	@Expose
	private JsonObject quickConsultation;
	
	@Expose
	private PrescribedDrugDetail prescribedDrugDetail;

	public JsonObject getQuickConsultation() {
		return quickConsultation;
	}

	public void setQuickConsultation(JsonObject quickConsultation) {
		this.quickConsultation = quickConsultation;
	}

	public PrescribedDrugDetail getPrescribedDrugDetail() {
		return prescribedDrugDetail;
	}

	public void setPrescribedDrugDetail(PrescribedDrugDetail prescribedDrugDetail) {
		this.prescribedDrugDetail = prescribedDrugDetail;
	}


	
}
