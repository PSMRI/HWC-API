/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.covid19;

import com.google.gson.JsonObject;

public interface Covid19Service {
	public String saveCovid19NurseData(JsonObject requestOBJ, String Authorization) throws Exception;
}
