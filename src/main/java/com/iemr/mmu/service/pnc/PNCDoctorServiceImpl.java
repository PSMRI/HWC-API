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
package com.iemr.mmu.service.pnc;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iemr.mmu.data.pnc.PNCDiagnosis;
import com.iemr.mmu.data.snomedct.SCTDescription;
import com.iemr.mmu.repo.nurse.pnc.PNCDiagnosisRepo;
import com.iemr.mmu.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.mmu.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.mapper.InputMapper;

@Service
public class PNCDoctorServiceImpl implements PNCDoctorService {

	private PNCDiagnosisRepo pncDiagnosisRepo;
	private PrescriptionDetailRepo prescriptionDetailRepo;
	private CommonDoctorServiceImpl commonDoctorServiceImpl;

	@Autowired
	public void setCommonDoctorServiceImpl(CommonDoctorServiceImpl commonDoctorServiceImpl) {
		this.commonDoctorServiceImpl = commonDoctorServiceImpl;
	}

	@Autowired
	public void setPrescriptionDetailRepo(PrescriptionDetailRepo prescriptionDetailRepo) {
		this.prescriptionDetailRepo = prescriptionDetailRepo;
	}

	@Autowired
	public void setPncDiagnosisRepo(PNCDiagnosisRepo pncDiagnosisRepo) {
		this.pncDiagnosisRepo = pncDiagnosisRepo;
	}

	public Long saveBenPNCDiagnosis(JsonObject obj, Long prescriptionID) throws IEMRException {
		Long ID = null;
		PNCDiagnosis pncDiagnosis = InputMapper.gson().fromJson(obj, PNCDiagnosis.class);
		pncDiagnosis.setPrescriptionID(prescriptionID);

		// provisional diagnosis
		StringBuilder pdTerm = new StringBuilder();
		StringBuilder pdConceptID = new StringBuilder();

		if (pncDiagnosis != null && pncDiagnosis.getProvisionalDiagnosisList() != null
				&& pncDiagnosis.getProvisionalDiagnosisList().size() > 0) {
			int pointer = 1;
			for (SCTDescription obj1 : pncDiagnosis.getProvisionalDiagnosisList()) {
				if (obj1.getTerm() != null) {
					if (pointer == pncDiagnosis.getProvisionalDiagnosisList().size()) {
						pdTerm.append(obj1.getTerm());
						if (obj1.getConceptID() != null)
							pdConceptID.append(obj1.getConceptID());
						else
							pdConceptID.append("N/A");
					} else {
						pdTerm.append(obj1.getTerm()).append("  ||  ");
						if (obj1.getConceptID() != null)
							pdConceptID.append(obj1.getConceptID()).append("  ||  ");
						else
							pdConceptID.append("N/A").append("  ||  ");
					}
				}
				pointer++;
			}

		}
		pncDiagnosis.setProvisionalDiagnosis(pdTerm.toString());
		pncDiagnosis.setProvisionalDiagnosisSCTCode(pdConceptID.toString());
		// pncDiagnosis.setProvisionalDiagnosisSCTTerm(pdTerm.toString());

		// confirmatory diagnosis
		StringBuilder cdTerm = new StringBuilder();
		StringBuilder cdConceptID = new StringBuilder();

		if (pncDiagnosis != null && pncDiagnosis.getConfirmatoryDiagnosisList() != null
				&& pncDiagnosis.getConfirmatoryDiagnosisList().size() > 0) {
			int pointer = 1;
			for (SCTDescription obj1 : pncDiagnosis.getConfirmatoryDiagnosisList()) {
				if (obj1.getTerm() != null) {
					if (pointer == pncDiagnosis.getConfirmatoryDiagnosisList().size()) {
						cdTerm.append(obj1.getTerm());
						if (obj1.getConceptID() != null)
							cdConceptID.append(obj1.getConceptID());
						else
							cdConceptID.append("N/A");
					} else {
						cdTerm.append(obj1.getTerm()).append("  ||  ");
						if (obj1.getConceptID() != null)
							cdConceptID.append(obj1.getConceptID()).append("  ||  ");
						else
							cdConceptID.append("N/A").append("  ||  ");
					}
				}
				pointer++;
			}

		}

		pncDiagnosis.setConfirmatoryDiagnosis(cdTerm.toString());
		pncDiagnosis.setConfirmatoryDiagnosisSCTCode(cdConceptID.toString());
		// pncDiagnosis.setConfirmatoryDiagnosisSCTTerm(cdTerm.toString());

		PNCDiagnosis res = pncDiagnosisRepo.save(pncDiagnosis);
		if (null != res && res.getID() > 0) {
			ID = res.getID();
		}
		return ID;
	}

	public String getPNCDiagnosisDetails(Long beneficiaryRegID, Long visitCode) {
//		String externalInvestigation = prescriptionDetailRepo.getExternalinvestigationForVisitCode(beneficiaryRegID,
//				visitCode);

		ArrayList<Object[]> prescriptionData = prescriptionDetailRepo
				.getExternalinvestigationForVisitCode(beneficiaryRegID, visitCode);

		String externalInvestigation = null;
		String instruction = null;
		String counsellingProvided = null;
		if (prescriptionData != null && prescriptionData.size() > 0) {
			externalInvestigation = String.valueOf(prescriptionData.get(0)[0]);
			if (prescriptionData.get(0)[1] != null)
				instruction = String.valueOf(prescriptionData.get(0)[1]);
			if (prescriptionData.get(0)[2] != null)
				counsellingProvided = String.valueOf(prescriptionData.get(0)[2]);
		}

		PNCDiagnosis pncDiagnosisDetails;
		SCTDescription sctOBJ;
		ArrayList<SCTDescription> sctOBJList;

		ArrayList<PNCDiagnosis> pncDignosisList = pncDiagnosisRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID,
				visitCode);

		if (pncDignosisList != null && pncDignosisList.size() > 0) {
			pncDiagnosisDetails = pncDignosisList.get(0);
			if (externalInvestigation != null)
				pncDiagnosisDetails.setExternalInvestigation(externalInvestigation);

			if (pncDiagnosisDetails != null && pncDiagnosisDetails.getProvisionalDiagnosis() != null
					&& pncDiagnosisDetails.getProvisionalDiagnosisSCTCode() != null) {

				sctOBJList = new ArrayList<>();
				String[] conceptIDArr = pncDiagnosisDetails.getProvisionalDiagnosisSCTCode()
						.split(Pattern.quote("  ||  "));
				String[] termArr = pncDiagnosisDetails.getProvisionalDiagnosis().split(Pattern.quote("  ||  "));

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
				pncDiagnosisDetails.setProvisionalDiagnosisList(sctOBJList);
				// pncDiagnosisDetails.setProvisionalDiagnosis(pd.toString());
			}

			if (pncDiagnosisDetails != null && pncDiagnosisDetails.getConfirmatoryDiagnosis() != null
					&& pncDiagnosisDetails.getConfirmatoryDiagnosisSCTCode() != null) {

				sctOBJList = new ArrayList<>();
				String[] conceptIDArr = pncDiagnosisDetails.getConfirmatoryDiagnosisSCTCode()
						.split(Pattern.quote("  ||  "));
				String[] termArr = pncDiagnosisDetails.getConfirmatoryDiagnosis().split(Pattern.quote("  ||  "));

				// StringBuilder cd = new StringBuilder();
				int pointer = 0;

				for (String s : termArr) {
					// if (termArr.length == (pointer + 1))
					// cd.append(s);
					// else
					// cd.append(s).append(" || ");
					sctOBJ = new SCTDescription();
					sctOBJ.setConceptID(conceptIDArr[pointer]);
					sctOBJ.setTerm(s);
					sctOBJList.add(sctOBJ);

					pointer++;
				}
				pncDiagnosisDetails.setConfirmatoryDiagnosisList(sctOBJList);
				// pncDiagnosisDetails.setConfirmatoryDiagnosis(cd.toString());
			}

		} else {
			pncDiagnosisDetails = new PNCDiagnosis();
		}
		if (instruction != null && pncDiagnosisDetails != null)
			pncDiagnosisDetails.setSpecialistDiagnosis(instruction);
		if (counsellingProvided != null)
			pncDiagnosisDetails.setCounsellingProvided(counsellingProvided);

		return new Gson().toJson(pncDiagnosisDetails);
	}

	public int updateBenPNCDiagnosis(PNCDiagnosis pncDiagnosis) throws IEMRException {
		int res = 0;

		// provisional diagnosis
		StringBuilder pdTerm = new StringBuilder();
		StringBuilder pdConceptID = new StringBuilder();

		if (pncDiagnosis != null && pncDiagnosis.getProvisionalDiagnosisList() != null
				&& pncDiagnosis.getProvisionalDiagnosisList().size() > 0) {
			int pointer = 1;
			for (SCTDescription obj1 : pncDiagnosis.getProvisionalDiagnosisList()) {
				if (obj1.getTerm() != null) {
					if (pointer == pncDiagnosis.getProvisionalDiagnosisList().size()) {
						pdTerm.append(obj1.getTerm());
						if (obj1.getConceptID() != null)
							pdConceptID.append(obj1.getConceptID());
						else
							pdConceptID.append("N/A");
					} else {
						pdTerm.append(obj1.getTerm()).append("  ||  ");
						if (obj1.getConceptID() != null)
							pdConceptID.append(obj1.getConceptID()).append("  ||  ");
						else
							pdConceptID.append("N/A").append("  ||  ");
					}
				}
				pointer++;
			}

		}
		pncDiagnosis.setProvisionalDiagnosis(pdTerm.toString());
		pncDiagnosis.setProvisionalDiagnosisSCTCode(pdConceptID.toString());
		// pncDiagnosis.setProvisionalDiagnosisSCTTerm(pdTerm.toString());

		// confirmatory diagnosis
		StringBuilder cdTerm = new StringBuilder();
		StringBuilder cdConceptID = new StringBuilder();

		if (pncDiagnosis != null && pncDiagnosis.getConfirmatoryDiagnosisList() != null
				&& pncDiagnosis.getConfirmatoryDiagnosisList().size() > 0) {
			int pointer = 1;
			for (SCTDescription obj1 : pncDiagnosis.getConfirmatoryDiagnosisList()) {
				if (obj1.getTerm() != null) {
					if (pointer == pncDiagnosis.getConfirmatoryDiagnosisList().size()) {
						cdTerm.append(obj1.getTerm());
						if (obj1.getConceptID() != null)
							cdConceptID.append(obj1.getConceptID());
						else
							cdConceptID.append("N/A");
					} else {
						cdTerm.append(obj1.getTerm()).append("  ||  ");
						if (obj1.getConceptID() != null)
							cdConceptID.append(obj1.getConceptID()).append("  ||  ");
						else
							cdConceptID.append("N/A").append("  ||  ");
					}
				}
				pointer++;
			}

		}

		pncDiagnosis.setConfirmatoryDiagnosis(cdTerm.toString());
		pncDiagnosis.setConfirmatoryDiagnosisSCTCode(cdConceptID.toString());
		// pncDiagnosis.setConfirmatoryDiagnosisSCTTerm(cdTerm.toString());

		String processed = pncDiagnosisRepo.getPNCDiagnosisStatus(pncDiagnosis.getBeneficiaryRegID(),
				pncDiagnosis.getVisitCode(), pncDiagnosis.getPrescriptionID());

		if (null != processed && !processed.equals("N")) {
			processed = "U";
		}

		if (processed != null) {
			res = pncDiagnosisRepo.updatePNCDiagnosis(pncDiagnosis.getProvisionalDiagnosis(),
					pncDiagnosis.getConfirmatoryDiagnosis(), pncDiagnosis.getIsMaternalDeath(),
					pncDiagnosis.getPlaceOfDeath(), pncDiagnosis.getDateOfDeath(), pncDiagnosis.getCauseOfDeath(),
					pncDiagnosis.getCreatedBy(), processed, pncDiagnosis.getBeneficiaryRegID(),
					pncDiagnosis.getVisitCode(), pncDiagnosis.getProvisionalDiagnosisSCTCode(),
					pncDiagnosis.getProvisionalDiagnosisSCTTerm(), pncDiagnosis.getConfirmatoryDiagnosisSCTCode(),
					pncDiagnosis.getConfirmatoryDiagnosisSCTTerm(), pncDiagnosis.getPrescriptionID());
		} else {
			PNCDiagnosis pncDiagnosisRes = pncDiagnosisRepo.save(pncDiagnosis);
			if (null != pncDiagnosisRes && pncDiagnosisRes.getID() > 0) {
				res = 1;
			}
		}
		return res;
	}
}
