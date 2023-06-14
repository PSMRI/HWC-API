/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
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
