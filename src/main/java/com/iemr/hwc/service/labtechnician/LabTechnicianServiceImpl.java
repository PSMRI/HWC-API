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
package com.iemr.hwc.service.labtechnician;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.labModule.LabResultEntry;
import com.iemr.hwc.data.labModule.ProcedureComponentMapping;
import com.iemr.hwc.data.labModule.WrapperLabResultEntry;
import com.iemr.hwc.data.labtechnician.V_benLabTestOrderedDetails;
import com.iemr.hwc.repo.labModule.LabResultEntryRepo;
import com.iemr.hwc.repo.labtechnician.V_benLabTestOrderedDetailsRepo;
import com.iemr.hwc.repo.procedureCompMapMaster.ProcedureCompMappedMasterRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.utils.mapper.InputMapper;

@Service
public class LabTechnicianServiceImpl implements LabTechnicianService {
	private V_benLabTestOrderedDetailsRepo v_benLabTestOrderedDetailsRepo;
	private LabResultEntryRepo labResultEntryRepo;
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	private ProcedureCompMappedMasterRepo procedureCompMappedMasterRepo;


	@Autowired
	public void setCommonBenStatusFlowServiceImpl(CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl) {
		this.commonBenStatusFlowServiceImpl = commonBenStatusFlowServiceImpl;
	}

	@Autowired
	public void setLabResultEntryRepo(LabResultEntryRepo labResultEntryRepo) {
		this.labResultEntryRepo = labResultEntryRepo;
	}

	@Autowired
	public void setV_benLabTestOrderedDetailsRepo(V_benLabTestOrderedDetailsRepo v_benLabTestOrderedDetailsRepo) {
		this.v_benLabTestOrderedDetailsRepo = v_benLabTestOrderedDetailsRepo;
	}
	
	@Autowired
	public void setProcedureCompMappedMasterRepo(ProcedureCompMappedMasterRepo procedureCompMappedMasterRepo) {
		this.procedureCompMappedMasterRepo = procedureCompMappedMasterRepo;
	}

	public String getBenePrescribedProcedureDetails(Long benRegID, Long visitCode) {
		Map<String, Object> returnOBJ = new HashMap<>();

		ArrayList<Object> radiologyList;
		ArrayList<Object> laboratoryList;

		ArrayList<Integer> resultEnteredProcList = new ArrayList<>();

		ArrayList<LabResultEntry> procedureResults = getLabResultDataForBen(benRegID, visitCode);

		if (procedureResults != null && procedureResults.size() > 0) {
			for (LabResultEntry obj : procedureResults) {
				resultEnteredProcList.add(obj.getProcedureID());
			}
		}
		resultEnteredProcList.add(0);

		ArrayList<V_benLabTestOrderedDetails> orderedLabTestListLab = v_benLabTestOrderedDetailsRepo
				.findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
						benRegID, visitCode, "Laboratory", resultEnteredProcList);

		ArrayList<V_benLabTestOrderedDetails> orderedLabTestListRadio = v_benLabTestOrderedDetailsRepo
				.findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
						benRegID, visitCode, "Radiology", resultEnteredProcList);

		radiologyList = getPrescribedLabTestInJsonFormatRadiology(orderedLabTestListRadio);
		laboratoryList = getPrescribedLabTestInJsonFormatlaboratory(orderedLabTestListLab);

		returnOBJ.put("radiologyList", radiologyList);
		returnOBJ.put("laboratoryList", laboratoryList);
		returnOBJ.put("archive", procedureResults);

		return new Gson().toJson(returnOBJ);
	}

	private ArrayList<Object> getPrescribedLabTestInJsonFormatlaboratory(
			ArrayList<V_benLabTestOrderedDetails> orderedLabTestList) {

		ArrayList<Object> returnOBJ = new ArrayList<>();
		Map<String, Object> procDetails = null;
		ArrayList<Object> compList = null;
		Map<String, Object> compDetails = null;
		Map<String, Object> compOption = null;
		ArrayList<Object> compOptionList = null;

		if (orderedLabTestList != null && orderedLabTestList.size() > 0) {
			for (V_benLabTestOrderedDetails obj : orderedLabTestList) {

				if (procDetails == null || (procDetails.containsKey("procedureID")
						&& !obj.getProcedureID().equals(procDetails.get("procedureID")))) {
					procDetails = new HashMap<>();
					compList = new ArrayList<>();

					procDetails.put("procedureID", obj.getProcedureID());
					procDetails.put("procedureName", obj.getProcedureName());
					procDetails.put("procedureDesc", obj.getProcedureDesc());
					procDetails.put("procedureType", "Laboratory");
					procDetails.put("prescriptionID", obj.getPrescriptionID());
					procDetails.put("isMandatory", obj.getIsMandatory());

//					System.out.println("obj.getIsMandatory(): "+obj.getIsMandatory());

					procDetails.put("iotProcedureName", obj.getIotProcedureName());
					procDetails.put("procedureCode", obj.getProcedureCode());
					procDetails.put("procedureStartAPI", obj.getProcedureStartAPI());
					procDetails.put("procedureEndAPI", obj.getProcedureEndAPI());
					procDetails.put("procedureStatusAPI", obj.getProcedureStatusAPI());
					procDetails.put("isLabProcedure", obj.getIsLabProcedure());
					procDetails.put("discoveryCode", obj.getDiscoveryCode());
					
					procDetails.put("calibrationStartAPI", obj.getCalibrationStartAPI());
					procDetails.put("calibrationStatusAPI", obj.getCalibrationStatusAPI());
					procDetails.put("calibrationEndAPI", obj.getCalibrationEndAPI());

//					System.out.println(procDetails.get("procedureID"));
					if (procDetails.get("procedureID") instanceof Integer
							&& obj.getProcedureID() == procDetails.get("procedureID")) {
//						System.out.println("hello");
					}
					if (procDetails.containsKey("compListDetails") == false) {
						compList = new ArrayList<>();
						compDetails = new HashMap<>();
						compDetails.put("testComponentID", obj.getTestComponentID());
						compDetails.put("testComponentName", obj.getTestComponentName());
						compDetails.put("testComponentDesc", obj.getTestComponentDesc());
						compDetails.put("inputType", obj.getInputType());
						compDetails.put("measurementUnit", obj.getMeasurementUnit());
						compDetails.put("range_min", obj.getRange_min());
						compDetails.put("range_normal_min", obj.getRange_normal_min());
						compDetails.put("range_normal_max", obj.getRange_normal_max());
						compDetails.put("range_max", obj.getRange_max());
						compDetails.put("isDecimal", obj.getIsDecimal());

						compDetails.put("iotComponentName", obj.getIOTComponentName());
						compDetails.put("componentCode", obj.getComponentCode());
						compDetails.put("iotProcedureID", obj.getIOTProcedureID());
						compDetails.put("componentUnit", obj.getComponentUnit());

						compOption = new HashMap<>();
						compOptionList = new ArrayList<>();

						compOption.put("name", obj.getResultValue());
						compOptionList.add(compOption);

						compDetails.put("compOpt", compOptionList);

						compList.add(compDetails);
						procDetails.put("compListDetails", compList);

					} else {
						compDetails = new HashMap<>();
						compDetails.put("testComponentID", obj.getTestComponentID());
						compDetails.put("testComponentName", obj.getTestComponentName());
						compDetails.put("testComponentDesc", obj.getTestComponentDesc());
						compDetails.put("inputType", obj.getInputType());
						compDetails.put("isDecimal", obj.getIsDecimal());
						compDetails.put("measurementUnit", obj.getMeasurementUnit());
						compDetails.put("range_min", obj.getRange_min());
						compDetails.put("range_normal_min", obj.getRange_normal_min());
						compDetails.put("range_normal_max", obj.getRange_normal_max());
						compDetails.put("range_max", obj.getRange_max());

						compDetails.put("iotComponentName", obj.getIOTComponentName());
						compDetails.put("componentCode", obj.getComponentCode());
						compDetails.put("iotProcedureID", obj.getIOTProcedureID());
						compDetails.put("componentUnit", obj.getComponentUnit());

						compOption = new HashMap<>();
						compOptionList = new ArrayList<>();

						compOption.put("name", obj.getResultValue());
						compOptionList.add(compOption);

						compDetails.put("compOpt", compOptionList);

						compList.add(compDetails);

					}

					returnOBJ.add(procDetails);

				} else {

					if (compDetails == null || (compDetails.containsKey("testComponentID")
							&& !obj.getTestComponentID().equals(compDetails.get("testComponentID")))) {

						compDetails = new HashMap<>();
						compDetails.put("testComponentID", obj.getTestComponentID());
						compDetails.put("testComponentName", obj.getTestComponentName());
						compDetails.put("testComponentDesc", obj.getTestComponentDesc());
						compDetails.put("inputType", obj.getInputType());
						compDetails.put("isDecimal", obj.getIsDecimal());
						compDetails.put("measurementUnit", obj.getMeasurementUnit());
						compDetails.put("range_min", obj.getRange_min());
						compDetails.put("range_normal_min", obj.getRange_normal_min());
						compDetails.put("range_normal_max", obj.getRange_normal_max());
						compDetails.put("range_max", obj.getRange_max());

						compDetails.put("iotComponentName", obj.getIOTComponentName());
						compDetails.put("componentCode", obj.getComponentCode());
						compDetails.put("iotProcedureID", obj.getIOTProcedureID());
						compDetails.put("componentUnit", obj.getComponentUnit());

						compOption = new HashMap<>();
						compOptionList = new ArrayList<>();

						compOption.put("name", obj.getResultValue());
						compOptionList.add(compOption);

						compDetails.put("compOpt", compOptionList);

						compList.add(compDetails);
					} else {
						compOption = new HashMap<>();
						compOption.put("name", obj.getResultValue());
						compOptionList.add(compOption);
					}

				}
			}
		}

		return returnOBJ;
	}

	public ArrayList<LabResultEntry> getLabResultDataForBen(Long benRegID, Long visitCode) {
		ArrayList<LabResultEntry> procedureResults = new ArrayList<>();
		procedureResults = labResultEntryRepo.findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(benRegID,
				visitCode);
		procedureResults = LabResultEntry.getLabResultEntry(procedureResults);
		return procedureResults;
	}

	private ArrayList<Object> getPrescribedLabTestInJsonFormatRadiology(
			ArrayList<V_benLabTestOrderedDetails> orderedLabTestList) {
		ArrayList<Object> returnOBJ = new ArrayList<>();
		Map<String, Object> procedureCompDetails;

		Map<String, Object> compDetails;

		if (orderedLabTestList != null && orderedLabTestList.size() > 0) {
			for (V_benLabTestOrderedDetails obj : orderedLabTestList) {
				procedureCompDetails = new HashMap<>();
				compDetails = new HashMap<>();

				procedureCompDetails.put("procedureID", obj.getProcedureID());
				procedureCompDetails.put("procedureName", obj.getProcedureName());
				procedureCompDetails.put("procedureDesc", obj.getProcedureDesc());
				procedureCompDetails.put("procedureType", "Radiology");
				procedureCompDetails.put("prescriptionID", obj.getPrescriptionID());

				compDetails.put("testComponentID", obj.getTestComponentID());
				compDetails.put("testComponentName", obj.getTestComponentName());
				compDetails.put("testComponentDesc", obj.getTestComponentDesc());
				compDetails.put("inputType", "File");

				procedureCompDetails.put("compDetails", compDetails);

				returnOBJ.add(procedureCompDetails);

			}
		}

		return returnOBJ;
	}

	/*
	 * @Transactional(rollbackFor = Exception.class) public Integer
	 * saveLabTestResult(JsonObject requestOBJ) throws Exception {
	 * 
	 * Integer labResultSaveFlag = null; if (requestOBJ != null &&
	 * requestOBJ.has("labTestResults") && null != requestOBJ.get("labTestResults")
	 * && !requestOBJ.get("labTestResults").isJsonNull()) {
	 * 
	 * LabResultEntry[] labResults =
	 * InputMapper.gson().fromJson(requestOBJ.get("labTestResults"),
	 * LabResultEntry[].class); List<LabResultEntry> labResultsList =
	 * Arrays.asList(labResults);
	 * 
	 * if(null != labResultsList && labResultsList.size()>0){ List<LabResultEntry>
	 * labResultsListNew = new ArrayList<LabResultEntry>(); for(LabResultEntry
	 * labResult : labResultsList){ List<Map<String, String>> compResult =
	 * labResult.getCompList(); if(null != compResult && compResult.size()>0){
	 * for(Map<String, String> comp: compResult){ LabResultEntry labCompResult = new
	 * LabResultEntry();
	 * labCompResult.setPrescriptionID(labResult.getPrescriptionID());
	 * labCompResult.setProcedureID(labResult.getProcedureID());
	 * 
	 * if(null != comp.get("testComponentID") &&
	 * !comp.get("testComponentID").toString().isEmpty() && null !=
	 * comp.get("testResultValue") &&
	 * !comp.get("testResultValue").toString().isEmpty()){
	 * labCompResult.setTestComponentID(Integer.parseInt(comp.get(
	 * "testComponentID")));
	 * labCompResult.setTestResultValue(comp.get("testResultValue").toString());
	 * 
	 * if(requestOBJ.has("beneficiaryRegID") && null !=
	 * requestOBJ.get("beneficiaryRegID") &&
	 * !requestOBJ.get("beneficiaryRegID").isJsonArray()){
	 * labCompResult.setBeneficiaryRegID(requestOBJ.get("beneficiaryRegID").
	 * getAsLong()); }
	 * 
	 * if(requestOBJ.has("createdBy") && null != requestOBJ.get("createdBy") &&
	 * !requestOBJ.get("createdBy").isJsonArray()){
	 * labCompResult.setCreatedBy(requestOBJ.get("createdBy").toString()); }
	 * 
	 * labResultsListNew.add(labCompResult); }
	 * 
	 * } } } List<LabResultEntry> labResultEntryRes = (List<LabResultEntry>)
	 * labResultEntryRepo.save(labResultsListNew); if(null != labResultEntryRes &&
	 * labResultsListNew.size() == labResultEntryRes.size()){ labResultSaveFlag = 1;
	 * } }else{ labResultSaveFlag = 1; } }else{ labResultSaveFlag = 1; } return
	 * labResultSaveFlag; }
	 */

	@Transactional(rollbackFor = Exception.class)
	public Integer saveLabTestResult(JsonObject requestOBJ) throws Exception {

		Integer labResultSaveFlag = null;
		if (requestOBJ != null && requestOBJ.has("labTestResults") && null != requestOBJ.get("labTestResults")
				&& !requestOBJ.get("labTestResults").isJsonNull()) {

			WrapperLabResultEntry wrapperLabResults = InputMapper.gson().fromJson(requestOBJ,
					WrapperLabResultEntry.class);

			labResultSaveFlag = saveLabTestResult(wrapperLabResults);

			if (labResultSaveFlag == 1) {
				int i = updateBenFlowStatusFlagAfterLabResultEntry(wrapperLabResults.getLabCompleted(),
						wrapperLabResults.getBenFlowID(), wrapperLabResults.getBeneficiaryRegID(),
						wrapperLabResults.getVisitID(), wrapperLabResults.getNurseFlag(),
						wrapperLabResults.getDoctorFlag(), wrapperLabResults.getSpecialist_flag());
			}

		} else {
			labResultSaveFlag = 1;
		}

		return labResultSaveFlag;
	}

	private int updateBenFlowStatusFlagAfterLabResultEntry(Boolean isLabDone, Long benFlowID, Long benRegID,
			Long benVisitID, Short nurseFlag, Short doctorFlag, Short specialistFlag) {
		int returnOBJ = 0;
		short labFlag = (short) 0;

		if (specialistFlag != null && specialistFlag == 2) {
			if (isLabDone == true) {
				specialistFlag = (short) 3;
			} else {
				labFlag = (short) 1;
			}

			returnOBJ = commonBenStatusFlowServiceImpl.updateFlowAfterLabResultEntryForTCSpecialist(benFlowID, benRegID,
					specialistFlag);
		} else {
			if (isLabDone == true) {
				if (nurseFlag == 2) {
					nurseFlag = 3;
					doctorFlag = 1;
				} else {
					if (doctorFlag == 2) {
						doctorFlag = 3;
					}
				}

				labFlag = (short) 1;
			} else {
				labFlag = (short) 1;
			}
			returnOBJ = commonBenStatusFlowServiceImpl.updateFlowAfterLabResultEntry(benFlowID, benRegID, benVisitID,
					nurseFlag, doctorFlag, labFlag);
		}

		return returnOBJ;
	}

	public Integer saveLabTestResult(WrapperLabResultEntry wrapperLabResults) {
		Integer labResultSaveFlag = null;

		List<LabResultEntry> labResultsList = wrapperLabResults.getLabTestResults();

		if ((null != labResultsList && labResultsList.size() > 0)
				|| (null != wrapperLabResults.getRadiologyTestResults()
				&& wrapperLabResults.getRadiologyTestResults().size() > 0)) {
			List<LabResultEntry> labResultsListNew = new ArrayList<LabResultEntry>();
			for (LabResultEntry labResult : labResultsList) {
				List<Map<String, String>> compResult = labResult.getCompList();
				if (null != compResult && compResult.size() > 0) {
					for (Map<String, String> comp : compResult) {
						LabResultEntry labCompResult = new LabResultEntry();
						labCompResult.setPrescriptionID(labResult.getPrescriptionID());
						labCompResult.setProcedureID(labResult.getProcedureID());

						if (null != comp.get("testComponentID") && !comp.get("testComponentID").toString().isEmpty()
								&& ((null != comp.get("testResultValue") && !comp.get("testResultValue").toString().isEmpty())
										|| (null != comp.get("stripsNotAvailable") && comp.get("stripsNotAvailable").toString().equalsIgnoreCase("true")))) {
							labCompResult.setTestComponentID(Integer.parseInt(comp.get("testComponentID")));
							
							if (comp.containsKey("testResultValue") && comp.get("testResultValue") != null
									&& !comp.get("testResultValue").isEmpty())
							labCompResult.setTestResultValue(comp.get("testResultValue").toString());

							if (comp.containsKey("testResultUnit") && comp.get("testResultUnit") != null
									&& !comp.get("testResultUnit").isEmpty())
								labCompResult.setTestResultUnit(comp.get("testResultUnit"));

							if (comp.containsKey("remarks") && comp.get("remarks") != null
									&& !comp.get("remarks").isEmpty())
								labCompResult.setRemarks(comp.get("remarks"));

							if (comp.containsKey("stripsNotAvailable") && comp.get("stripsNotAvailable") != null
									&& comp.get("stripsNotAvailable").toString().equalsIgnoreCase("true"))
								labCompResult.setStripsNotAvailable(Boolean.valueOf(comp.get("stripsNotAvailable")));
							
							labCompResult.setBeneficiaryRegID(wrapperLabResults.getBeneficiaryRegID());
							labCompResult.setBenVisitID(wrapperLabResults.getVisitID());
							labCompResult.setVisitCode(wrapperLabResults.getVisitCode());
							labCompResult.setProviderServiceMapID(wrapperLabResults.getProviderServiceMapID());
							labCompResult.setCreatedBy(wrapperLabResults.getCreatedBy());

							labCompResult.setVanID(wrapperLabResults.getVanID());
							labCompResult.setParkingPlaceID(wrapperLabResults.getParkingPlaceID());
							
							labResultsListNew.add(labCompResult);
						}

					}
				}
			}
			for (LabResultEntry labResultEntry : wrapperLabResults.getRadiologyTestResults()) {
				labResultEntry.setBeneficiaryRegID(wrapperLabResults.getBeneficiaryRegID());
				labResultEntry.setBenVisitID(wrapperLabResults.getVisitID());
				labResultEntry.setVisitCode(wrapperLabResults.getVisitCode());
				labResultEntry.setProviderServiceMapID(wrapperLabResults.getProviderServiceMapID());
				labResultEntry.setCreatedBy(wrapperLabResults.getCreatedBy());

				labResultEntry.setVanID(wrapperLabResults.getVanID());
				labResultEntry.setParkingPlaceID(wrapperLabResults.getParkingPlaceID());

				// add file/doc id
				Integer[] docIdArr = labResultEntry.getFileIDs();
				StringBuilder sb = new StringBuilder();
				if (docIdArr != null && docIdArr.length > 0) {
					for (Integer i : docIdArr) {
						sb.append(i + ",");
					}
				}
				labResultEntry.setTestReportFilePath(sb.toString());

				labResultsListNew.add(labResultEntry);

			}
			if (null != labResultsListNew && labResultsListNew.size() > 0) {
				List<LabResultEntry> labResultEntryRes = (List<LabResultEntry>) labResultEntryRepo
						.saveAll(labResultsListNew);
				if (null != labResultEntryRes && labResultsListNew.size() == labResultEntryRes.size()) {
					labResultSaveFlag = 1;
				}
			} else {
				labResultSaveFlag = 1;
			}
		} else {
			labResultSaveFlag = 1;
		}
		return labResultSaveFlag;
	}

	public String getLast_3_ArchivedTestVisitList(Long benRegID, Long visitCode) {
		ArrayList<Object[]> visitCodeList = labResultEntryRepo.getLast_3_visitForLabTestDone(benRegID, visitCode);

		return new Gson().toJson(LabResultEntry.getVisitCodeAndDate(visitCodeList));
	}

	public String getLabResultForVisitcode(Long benRegID, Long visitCode) {
		ArrayList<LabResultEntry> labResultList = getLabResultDataForBen(benRegID, visitCode);
		return new Gson().toJson(labResultList);
	}
	
	public String getProcedureComponentMappedMasterData(Long providerServiceMapID) throws Exception {
		if(providerServiceMapID == null) {
			throw new IllegalArgumentException("Provider service map id cannot be null");
		}
		try {
		ArrayList<Object[]> procCompMapMasterList = procedureCompMappedMasterRepo.getProcedureComponentMappedMasterData(providerServiceMapID);
		if (procCompMapMasterList != null && procCompMapMasterList.size() > 0) {
			ArrayList<Map<String, Object>> responseList = ProcedureComponentMapping
					.getProcedureComponentMappedMasterDataObjListDetails(procCompMapMasterList);
			return new Gson().toJson(responseList);
		} else {
			return new Gson().toJson(new ArrayList<>());
		}	
	}
		catch (DataAccessResourceFailureException e) {
			throw new DataAccessResourceFailureException("Failed to fetch procedure component mapped master data", e);
	}
	
	}
	
	
}
