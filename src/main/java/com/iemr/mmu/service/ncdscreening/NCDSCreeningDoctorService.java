/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.ncdscreening;

import com.google.gson.JsonObject;

public interface NCDSCreeningDoctorService {
	public int updateDoctorData(JsonObject requestOBJ, String Authorization) throws Exception;
}
