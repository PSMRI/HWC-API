package com.iemr.mmu.data.registrar;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class WrapperBeneficiaryRegistration {

	@Expose
	private JsonObject benD;

	public WrapperBeneficiaryRegistration() {
	}

	public WrapperBeneficiaryRegistration(JsonObject benD) {
		super();
		this.benD = benD;
	}

	public JsonObject getBenD() {
		return benD;
	}

	public void setBenD(JsonObject benD) {
		this.benD = benD;
	}

}
