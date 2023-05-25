package com.iemr.mmu.service.common.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.mmu.data.doctor.ChiefComplaintMaster;
import com.iemr.mmu.data.doctor.DrugDoseMaster;
import com.iemr.mmu.data.doctor.DrugDurationUnitMaster;
import com.iemr.mmu.data.doctor.DrugFormMaster;
import com.iemr.mmu.data.doctor.DrugFrequencyMaster;
import com.iemr.mmu.data.doctor.LabTestMaster;
import com.iemr.mmu.data.doctor.TempMasterDrug;
import com.iemr.mmu.data.labModule.ProcedureData;
import com.iemr.mmu.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.mmu.repo.doctor.DrugDoseMasterRepo;
import com.iemr.mmu.repo.doctor.DrugDurationUnitMasterRepo;
import com.iemr.mmu.repo.doctor.DrugFormMasterRepo;
import com.iemr.mmu.repo.doctor.DrugFrequencyMasterRepo;
import com.iemr.mmu.repo.doctor.LabTestMasterRepo;
import com.iemr.mmu.repo.doctor.TempMasterDrugRepo;
import com.iemr.mmu.repo.labModule.ProcedureRepo;

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
