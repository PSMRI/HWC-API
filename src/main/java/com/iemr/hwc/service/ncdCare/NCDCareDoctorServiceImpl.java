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
package com.iemr.hwc.service.ncdCare;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.hwc.data.ncdcare.NCDCareDiagnosis;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.data.snomedct.SCTDescription;
import com.iemr.hwc.repo.nurse.ncdcare.NCDCareDiagnosisRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.utils.exception.IEMRException;

@Service
public class NCDCareDoctorServiceImpl implements NCDCareDoctorService {
	private NCDCareDiagnosisRepo ncdCareDiagnosisRepo;
	private PrescriptionDetailRepo prescriptionDetailRepo;

	@Autowired
	public void setPrescriptionDetailRepo(PrescriptionDetailRepo prescriptionDetailRepo) {
		this.prescriptionDetailRepo = prescriptionDetailRepo;
	}

	@Autowired
	public void setNcdCareDiagnosisRepo(NCDCareDiagnosisRepo ncdCareDiagnosisRepo) {
		this.ncdCareDiagnosisRepo = ncdCareDiagnosisRepo;
	}

	public long saveNCDDiagnosisData(NCDCareDiagnosis ncdDiagnosis) throws Exception {
		long res = 0;

		if (ncdDiagnosis.getNcdScreeningConditionArray() != null
				&& ncdDiagnosis.getNcdScreeningConditionArray().length > 0) {
			StringBuilder sb = new StringBuilder();
			for (String s : ncdDiagnosis.getNcdScreeningConditionArray()) {
				sb.append(s).append("||");
			}
			ncdDiagnosis.setNcdScreeningCondition((sb.delete((sb.length() - 2), (sb.length()))).toString());
		} else
			ncdDiagnosis.setNcdScreeningCondition(null);

		NCDCareDiagnosis diagnosis = ncdCareDiagnosisRepo.save(ncdDiagnosis);
		if (null != diagnosis) {
			res = diagnosis.getID();
		}
		return res;
	}

	public String getNCDCareDiagnosisDetails(Long beneficiaryRegID, Long visitCode) {

		ArrayList<Object[]> prescriptionData = prescriptionDetailRepo
				.getExternalinvestigationForVisitCode(beneficiaryRegID, visitCode);

		String externalInvestigation = null;
		String instruction = null;
		String counsellingProvided = null;
		if (prescriptionData != null && prescriptionData.size() > 0) {
			if (prescriptionData.get(0)[0] != null)
				externalInvestigation = String.valueOf(prescriptionData.get(0)[0]);
			if (prescriptionData.get(0)[1] != null)
				instruction = String.valueOf(prescriptionData.get(0)[1]);
			if (prescriptionData.get(0)[2] != null)
				counsellingProvided = String.valueOf(prescriptionData.get(0)[2]);
		}

//		String externalInvestigation = prescriptionDetailRepo.getExternalinvestigationForVisitCode(beneficiaryRegID,
//				visitCode);

		ArrayList<Object[]> resList = ncdCareDiagnosisRepo.getNCDCareDiagnosisDetails(beneficiaryRegID, visitCode);
		NCDCareDiagnosis ncdCareDiagnosisDetails = NCDCareDiagnosis.getNCDCareDiagnosisDetails(resList);

		// 07-09-2021 parsing the || seperated ncd_condition to array of string, if
		// condition
		// 07-09-2021

		if (ncdCareDiagnosisDetails != null && ncdCareDiagnosisDetails.getNcdScreeningCondition() != null
				&& ncdCareDiagnosisDetails.getNcdScreeningCondition().length() > 0) {

			String[] ncdConditionArr = ncdCareDiagnosisDetails.getNcdScreeningCondition().split(Pattern.quote("||"));
			if (ncdConditionArr != null)
				ncdCareDiagnosisDetails.setNcdScreeningConditionArray(ncdConditionArr);
		}

		if (externalInvestigation != null) {
			ncdCareDiagnosisDetails.setExternalInvestigation(externalInvestigation);
		}
		if (instruction != null)
			ncdCareDiagnosisDetails.setSpecialistDiagnosis(instruction);
		if (counsellingProvided != null)
			ncdCareDiagnosisDetails.setCounsellingProvided(counsellingProvided);
		PrescriptionDetail obj;
		SCTDescription sctOBJ;
		ArrayList<SCTDescription> sctOBJList = new ArrayList<>();
		
		ArrayList<PrescriptionDetail> prescriptionDetailRS = prescriptionDetailRepo
				.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(beneficiaryRegID, visitCode);

		if (prescriptionDetailRS != null && prescriptionDetailRS.size() > 0) {
			obj = prescriptionDetailRS.get(0);
			if (obj != null && obj.getDiagnosisProvided_SCTCode() != null && obj.getDiagnosisProvided() != null) {
				String[] conceptIDArr = obj.getDiagnosisProvided_SCTCode().split(Pattern.quote("  ||  "));
				String[] termArr = obj.getDiagnosisProvided().split(Pattern.quote("  ||  "));

				// StringBuilder pd = new StringBuilder();
				int pointer = 0;
				for (String s : termArr) {
					// if (termArr.length == (pointer + 1))
					// pd.append(s);
					// else
					// pd.append(s).append(" || ");
					sctOBJ = new SCTDescription();
					sctOBJ.setConceptID(conceptIDArr[pointer]);
					sctOBJ.setTerm(s);
					sctOBJList.add(sctOBJ);

					pointer++;
				}

				obj.setProvisionalDiagnosisList(sctOBJList);
				ncdCareDiagnosisDetails.setDiagnosisProvided(obj.getDiagnosisProvided());
				ncdCareDiagnosisDetails.setDiagnosisProvided_SCTCode(obj.getDiagnosisProvided_SCTCode());
				ncdCareDiagnosisDetails.setProvisionalDiagnosisList(sctOBJList);;
				// obj.setDiagnosisProvided(pd.toString());
			}
			
		} else {
			obj = new PrescriptionDetail();
		}

		return new Gson().toJson(ncdCareDiagnosisDetails);
	}

	public int updateBenNCDCareDiagnosis(NCDCareDiagnosis ncdCareDiagnosis) throws IEMRException {
		int res = 0;
		String processed = ncdCareDiagnosisRepo.getNCDCareDiagnosisStatus(ncdCareDiagnosis.getBeneficiaryRegID(),
				ncdCareDiagnosis.getVisitCode(), ncdCareDiagnosis.getPrescriptionID());

		if (null != processed && !processed.equals("N")) {
			processed = "U";
		}

		if (ncdCareDiagnosis.getNcdScreeningConditionArray() != null
				&& ncdCareDiagnosis.getNcdScreeningConditionArray().length > 0) {
			StringBuilder sb = new StringBuilder();
			for (String s : ncdCareDiagnosis.getNcdScreeningConditionArray()) {
				sb.append(s).append("||");
			}
			ncdCareDiagnosis.setNcdScreeningCondition((sb.delete((sb.length() - 2), (sb.length()))).toString());
		} else
			ncdCareDiagnosis.setNcdScreeningCondition(null);

		if (processed != null) {
			// 07-09-2021, moved below line from outside if block to inside for null check
			ncdCareDiagnosis.setProcessed(processed);
			res = ncdCareDiagnosisRepo.updateNCDCareDiagnosis(ncdCareDiagnosis.getNcdCareCondition(),
					ncdCareDiagnosis.getNcdComplication(), ncdCareDiagnosis.getNcdCareType(),
					ncdCareDiagnosis.getCreatedBy(), ncdCareDiagnosis.getProcessed(),
					ncdCareDiagnosis.getBeneficiaryRegID(), ncdCareDiagnosis.getVisitCode(),
					ncdCareDiagnosis.getPrescriptionID(), ncdCareDiagnosis.getNcdScreeningConditionOther());
		} else {
			NCDCareDiagnosis ncdCareDiagnosisRes = ncdCareDiagnosisRepo.save(ncdCareDiagnosis);
			if (null != ncdCareDiagnosisRes && ncdCareDiagnosisRes.getID() > 0) {
				res = 1;
			}
		}

		return res;
	}
}
