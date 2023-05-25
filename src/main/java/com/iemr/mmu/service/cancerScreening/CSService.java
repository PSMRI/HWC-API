package com.iemr.mmu.service.cancerScreening;

import java.util.List;

import com.google.gson.JsonObject;
import com.iemr.mmu.data.doctor.CancerDiagnosis;
import com.iemr.mmu.data.nurse.BenCancerVitalDetail;
import com.iemr.mmu.data.nurse.BenFamilyCancerHistory;
import com.iemr.mmu.data.nurse.BenObstetricCancerHistory;
import com.iemr.mmu.data.nurse.BenPersonalCancerDietHistory;
import com.iemr.mmu.data.nurse.BenPersonalCancerHistory;

public interface CSService {

	public int UpdateCSHistoryNurseData(JsonObject jsnOBJ) throws Exception;

	public int updateBenVitalDetail(BenCancerVitalDetail benCancerVitalDetail);

	int updateCancerDiagnosisDetailsByOncologist(CancerDiagnosis cancerDiagnosis);

}
