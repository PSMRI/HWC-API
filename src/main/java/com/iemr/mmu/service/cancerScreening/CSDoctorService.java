/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.cancerScreening;

import java.util.List;

import com.iemr.mmu.data.doctor.CancerAbdominalExamination;
import com.iemr.mmu.data.doctor.CancerBreastExamination;
import com.iemr.mmu.data.doctor.CancerDiagnosis;
import com.iemr.mmu.data.doctor.CancerGynecologicalExamination;
import com.iemr.mmu.data.doctor.CancerLymphNodeDetails;
import com.iemr.mmu.data.doctor.CancerOralExamination;
import com.iemr.mmu.data.doctor.CancerSignAndSymptoms;
import com.iemr.mmu.data.doctor.WrapperCancerExamImgAnotasn;

public interface CSDoctorService {

	//public Long saveCancerSignAndSymptomsData(CancerSignAndSymptoms cancerSignAndSymptoms);

	//public Long saveLymphNodeDetails(List<CancerLymphNodeDetails> cancerLymphNodeDetails);

	//public Long saveCancerOralExaminationData(CancerOralExamination cancerOralExamination);

	//public Long saveCancerBreastExaminationData(CancerBreastExamination cancerBreastExamination);

	//public Long saveCancerAbdominalExaminationData(CancerAbdominalExamination cancerAbdominalExamination);

	//public Long saveCancerGynecologicalExaminationData(CancerGynecologicalExamination cancerGynecologicalExamination);

	//public Long saveDocExaminationImageAnnotation(List<WrapperCancerExamImgAnotasn> wrapperCancerExamImgAnotasnList);

	public Long saveCancerDiagnosisData(CancerDiagnosis cancerDiagnosis);

	//public Long saveCancerSignAndSymptomsData(CancerSignAndSymptoms cancerSignAndSymptoms, Long benVisitID);

}
