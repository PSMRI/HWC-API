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
package com.iemr.hwc.service.common.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.hwc.data.doctor.ChiefComplaintMaster;
import com.iemr.hwc.data.doctor.DrugDoseMaster;
import com.iemr.hwc.data.doctor.DrugDurationUnitMaster;
import com.iemr.hwc.data.doctor.DrugFormMaster;
import com.iemr.hwc.data.doctor.DrugFrequencyMaster;
import com.iemr.hwc.data.doctor.LabTestMaster;
import com.iemr.hwc.data.doctor.TempMasterDrug;
import com.iemr.hwc.data.labModule.ProcedureData;
import com.iemr.hwc.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.hwc.repo.doctor.DrugDoseMasterRepo;
import com.iemr.hwc.repo.doctor.DrugDurationUnitMasterRepo;
import com.iemr.hwc.repo.doctor.DrugFormMasterRepo;
import com.iemr.hwc.repo.doctor.DrugFrequencyMasterRepo;
import com.iemr.hwc.repo.doctor.LabTestMasterRepo;
import com.iemr.hwc.repo.doctor.TempMasterDrugRepo;
import com.iemr.hwc.repo.labModule.ProcedureRepo;

@Service
public class QCMasterDataServiceImpl implements QCMasterDataService{
	

	private ChiefComplaintMasterRepo chiefComplaintMasterRepo;
	private DrugDoseMasterRepo drugDoseMasterRepo;
	private DrugDurationUnitMasterRepo drugDurationUnitMasterRepo;
	private DrugFormMasterRepo drugFormMasterRepo;
	private DrugFrequencyMasterRepo drugFrequencyMasterRepo;
	private LabTestMasterRepo labTestMasterRepo;
	private TempMasterDrugRepo tempMasterDrugRepo;
	private ProcedureRepo procedureRepo;
	
	@Autowired
	public void setProcedureRepo(ProcedureRepo procedureRepo)
	{
		this.procedureRepo = procedureRepo;
	}
	
	@Autowired
	public void setChiefComplaintMasterRepo(ChiefComplaintMasterRepo chiefComplaintMasterRepo) {
		this.chiefComplaintMasterRepo = chiefComplaintMasterRepo;
	}
	
	@Autowired
	public void setDrugDoseMasterRepo(DrugDoseMasterRepo drugDoseMasterRepo) {
		this.drugDoseMasterRepo = drugDoseMasterRepo;
	}
	
	@Autowired
	public void setDrugDurationUnitMasterRepo(DrugDurationUnitMasterRepo drugDurationUnitMasterRepo) {
		this.drugDurationUnitMasterRepo = drugDurationUnitMasterRepo;
	}
	
	@Autowired
	public void setDrugFormMasterRepo(DrugFormMasterRepo drugFormMasterRepo) {
		this.drugFormMasterRepo = drugFormMasterRepo;
	}
	
	@Autowired
	public void setDrugFrequencyMasterRepo(DrugFrequencyMasterRepo drugFrequencyMasterRepo) {
		this.drugFrequencyMasterRepo = drugFrequencyMasterRepo;
	}
	
	@Autowired
	public void setLabTestMasterRepo(LabTestMasterRepo labTestMasterRepo) {
		this.labTestMasterRepo = labTestMasterRepo;
	}
	
	@Autowired
	public void setTempMasterDrugRepo(TempMasterDrugRepo tempMasterDrugRepo) {
		this.tempMasterDrugRepo = tempMasterDrugRepo;
	}
	
	@Override
	public String getQuickConsultMasterData(Integer providerServiceMapID, String gender) {
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		ArrayList<Object[]> ccList = chiefComplaintMasterRepo.getChiefComplaintMaster();
		ArrayList<Object[]> ddmList = drugDoseMasterRepo.getDrugDoseMaster();
		ArrayList<Object[]> ddumList = drugDurationUnitMasterRepo.getDrugDurationUnitMaster();
		ArrayList<Object[]> dfmList = drugFormMasterRepo.getDrugFormMaster();
		ArrayList<Object[]> dfrmList = drugFrequencyMasterRepo.getDrugFrequencyMaster();
		ArrayList<Object[]> ltmList = labTestMasterRepo.getLabTestMaster();
		ArrayList<Object[]> procedures = procedureRepo.getProcedureMasterData(providerServiceMapID, gender);
		ArrayList<TempMasterDrug> tempMasterDrugList = tempMasterDrugRepo.findByDeletedFalseOrderByDrugDisplayNameAsc();
		resMap.put("chiefComplaintMaster", ChiefComplaintMaster.getChiefComplaintMasters(ccList));
		resMap.put("drugDoseMaster", DrugDoseMaster.getDrugDoseMasters(ddmList));
		resMap.put("drugDurationUnitMaster", DrugDurationUnitMaster.getDrugDurationUnitMaster(ddumList));
		resMap.put("drugFormMaster", DrugFormMaster.getDrugFormMaster(dfmList));
		resMap.put("drugFrequencyMaster", DrugFrequencyMaster.getDrugFrequencyMaster(dfrmList));
		resMap.put("labTestMaster", LabTestMaster.getLabTestMasters(ltmList));
		resMap.put("tempDrugMaster", TempMasterDrug.getTempDrugMasterList(tempMasterDrugList));
		resMap.put("procedures", ProcedureData.getProcedures(procedures));
		return new Gson().toJson(resMap);
	}
}
