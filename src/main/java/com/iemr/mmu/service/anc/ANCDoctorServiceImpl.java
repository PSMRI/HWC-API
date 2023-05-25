package com.iemr.mmu.service.anc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iemr.mmu.data.anc.ANCDiagnosis;
import com.iemr.mmu.repo.nurse.anc.ANCDiagnosisRepo;
import com.iemr.mmu.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.mapper.InputMapper;

@Service
public class ANCDoctorServiceImpl implements ANCDoctorService {

	private ANCDiagnosisRepo ancDiagnosisRepo;
	private PrescriptionDetailRepo prescriptionDetailRepo;

	@Autowired
	public void setPrescriptionDetailRepo(PrescriptionDetailRepo prescriptionDetailRepo) {
		this.prescriptionDetailRepo = prescriptionDetailRepo;
	}

	@Autowired
	public void setAncDiagnosisRepo(ANCDiagnosisRepo ancDiagnosisRepo) {
		this.ancDiagnosisRepo = ancDiagnosisRepo;
	}

	public Long saveBenANCDiagnosis(JsonObject obj, Long prescriptionID) throws IEMRException {
		Long ID = null;
		ANCDiagnosis ancDiagnosis = InputMapper.gson().fromJson(obj, ANCDiagnosis.class);
		ancDiagnosis.setPrescriptionID(prescriptionID);

		StringBuilder sb = new StringBuilder();

		if (ancDiagnosis != null && ancDiagnosis.getComplicationOfCurrentPregnancyList() != null
				&& ancDiagnosis.getComplicationOfCurrentPregnancyList().size() > 0) {
			int pointer = 0;
			for (Map<String, String> compMap : ancDiagnosis.getComplicationOfCurrentPregnancyList()) {
				if (compMap.containsKey("pregComplicationType") && compMap.get("pregComplicationType") != null) {
					if ((pointer + 1) == ancDiagnosis.getComplicationOfCurrentPregnancyList().size()) {
						sb.append(compMap.get("pregComplicationType"));
					} else {
						sb.append(compMap.get("pregComplicationType")).append(" , ");
					}
				}
				pointer++;
			}
		}

		ancDiagnosis.setComplicationOfCurrentPregnancy(sb.toString());

		ANCDiagnosis res = ancDiagnosisRepo.save(ancDiagnosis);
		if (null != res && res.getID() > 0) {
			ID = res.getID();
		}
		return ID;
	}

	public String getANCDiagnosisDetails(Long beneficiaryRegID, Long visitCode) {
//		String externalInvestigation = prescriptionDetailRepo.getExternalinvestigationForVisitCode(beneficiaryRegID,
//		visitCode);

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

		ANCDiagnosis ancDiagnosisDetails = new ANCDiagnosis();

		ArrayList<ANCDiagnosis> ancDiagnosisList = ancDiagnosisRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID,
				visitCode);

		if (ancDiagnosisList != null && ancDiagnosisList.size() > 0)
			ancDiagnosisDetails = ancDiagnosisList.get(0);

		ArrayList<Map<String, String>> pregCompList = new ArrayList<>();
		Map<String, String> pregCompMap;
		if (ancDiagnosisDetails.getComplicationOfCurrentPregnancy() != null) {
			String[] currPregCompArr = ancDiagnosisDetails.getComplicationOfCurrentPregnancy().split(" , ");
			for (String s : currPregCompArr) {
				pregCompMap = new HashMap<>();
				pregCompMap.put("pregComplicationType", s);
				pregCompList.add(pregCompMap);
			}
		}

		ancDiagnosisDetails.setComplicationOfCurrentPregnancy(ancDiagnosisDetails.getComplicationOfCurrentPregnancy()
				+ " , Other-complications : " + ancDiagnosisDetails.getOtherCurrPregComplication());
		ancDiagnosisDetails.setComplicationOfCurrentPregnancyList(pregCompList);

		if (externalInvestigation != null)
			ancDiagnosisDetails.setExternalInvestigation(externalInvestigation);
		if (instruction != null)
			ancDiagnosisDetails.setSpecialistDiagnosis(instruction);
		if (counsellingProvided != null)
			ancDiagnosisDetails.setCounsellingProvided(counsellingProvided);

		return new Gson().toJson(ancDiagnosisDetails);
	}

	public int updateBenANCDiagnosis(ANCDiagnosis ancDiagnosis) throws IEMRException {
		int res = 0;
		String processed = ancDiagnosisRepo.getANCDiagnosisStatus(ancDiagnosis.getBeneficiaryRegID(),
				ancDiagnosis.getVisitCode(), ancDiagnosis.getPrescriptionID());
		if (null != processed && !processed.equals("N")) {
			processed = "U";
		}
		ancDiagnosis.setProcessed(processed);

		StringBuilder sb = new StringBuilder();

		if (ancDiagnosis != null && ancDiagnosis.getComplicationOfCurrentPregnancyList() != null
				&& ancDiagnosis.getComplicationOfCurrentPregnancyList().size() > 0) {
			int pointer = 0;
			for (Map<String, String> compMap : ancDiagnosis.getComplicationOfCurrentPregnancyList()) {
				if (compMap.containsKey("pregComplicationType") && compMap.get("pregComplicationType") != null) {
					if ((pointer + 1) == ancDiagnosis.getComplicationOfCurrentPregnancyList().size()) {
						sb.append(compMap.get("pregComplicationType"));
					} else {
						sb.append(compMap.get("pregComplicationType")).append(" , ");
					}
				}
				pointer++;
			}
		}

		ancDiagnosis.setComplicationOfCurrentPregnancy(sb.toString());

		if (processed != null) {
			int i = ancDiagnosisRepo.updateANCDiagnosis(ancDiagnosis.getHighRiskStatus(),
					ancDiagnosis.getHighRiskCondition(), ancDiagnosis.getComplicationOfCurrentPregnancy(),
					ancDiagnosis.getIsMaternalDeath(), ancDiagnosis.getPlaceOfDeath(), ancDiagnosis.getDateOfDeath(),
					ancDiagnosis.getCauseOfDeath(), ancDiagnosis.getCreatedBy(), ancDiagnosis.getProcessed(),
					ancDiagnosis.getBeneficiaryRegID(), ancDiagnosis.getVisitCode(),
					ancDiagnosis.getOtherCurrPregComplication());
			if (i > 0) {
				res = 1;
			}
		} else {
			ANCDiagnosis ancDiagnosisRS = ancDiagnosisRepo.save(ancDiagnosis);
			if (ancDiagnosisRS != null && ancDiagnosisRS.getID() > 0)
				res = 1;
		}

		return res;
	}
}
