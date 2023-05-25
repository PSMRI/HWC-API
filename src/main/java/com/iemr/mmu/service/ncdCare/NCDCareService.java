package com.iemr.mmu.service.ncdCare;

import com.google.gson.JsonObject;

public interface NCDCareService {

	String saveNCDCareNurseData(JsonObject requestOBJ, String Authorization) throws Exception;

}
