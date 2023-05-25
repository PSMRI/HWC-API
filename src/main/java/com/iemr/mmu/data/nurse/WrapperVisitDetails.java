package com.iemr.mmu.data.nurse;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class WrapperVisitDetails {
	
	@Expose
	private JsonObject visitDetails;

	public WrapperVisitDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WrapperVisitDetails(JsonObject visitDetails) {
		super();
		this.visitDetails = visitDetails;
	}

	public JsonObject getVisitDetails() {
		return visitDetails;
	}

	public void setVisitDetails(JsonObject visitDetails) {
		this.visitDetails = visitDetails;
	}
		

}
