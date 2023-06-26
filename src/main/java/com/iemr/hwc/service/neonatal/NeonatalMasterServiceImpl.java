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
package com.iemr.hwc.service.neonatal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.labModule.ProcedureData;
import com.iemr.hwc.data.masterdata.anc.ComplicationTypes;
import com.iemr.hwc.repo.adolescent.OralVitaminNoOfDoseMasterRepo;
import com.iemr.hwc.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.hwc.repo.labModule.ProcedureRepo;
import com.iemr.hwc.repo.masterrepo.anc.ChildVaccinationsRepo;
import com.iemr.hwc.repo.masterrepo.anc.ComplicationTypesRepo;
import com.iemr.hwc.repo.masterrepo.anc.DeliveryPlaceRepo;
import com.iemr.hwc.repo.masterrepo.anc.DeliveryTypeRepo;
import com.iemr.hwc.repo.masterrepo.anc.GestationRepo;
import com.iemr.hwc.repo.masterrepo.pnc.DeliveryConductedByMasterRepo;
import com.iemr.hwc.repo.neonatal.BirthDoseVaccinesMasterRepo;
import com.iemr.hwc.repo.neonatal.CongenitalAnomaliesMasterRepo;
import com.iemr.hwc.repo.neonatal.CounsellingProvidedMasterRepo;
import com.iemr.hwc.repo.neonatal.CurrentImmunizationServiceMasterRepo;
import com.iemr.hwc.repo.neonatal.NextDueVaccinesMasterRepo;
import com.iemr.hwc.repo.neonatal.NextImmunizationLocationMasterRepo;
import com.iemr.hwc.repo.neonatal.TypeOfImmunizationMasterRepo;
import com.iemr.hwc.utils.exception.IEMRException;

@Service
public class NeonatalMasterServiceImpl implements NeonatalMasterService {
	@Autowired
	private BirthDoseVaccinesMasterRepo birthDoseVaccinesMasterRepo;
	@Autowired
	private CongenitalAnomaliesMasterRepo congenitalAnomaliesMasterRepo;

	@Autowired
	private CounsellingProvidedMasterRepo counsellingProvidedMasterRepo;

	@Autowired
	private CurrentImmunizationServiceMasterRepo currentImmunizationServiceMasterRepo;

	@Autowired
	private NextDueVaccinesMasterRepo nextDueVaccinesMasterRepo;

	@Autowired
	private NextImmunizationLocationMasterRepo nextImmunizationLocationMasterRepo;
	@Autowired
	private DeliveryPlaceRepo deliveryPlaceRepo;
	@Autowired
	private DeliveryTypeRepo deliveryTypeRepo;
	@Autowired
	private ComplicationTypesRepo complicationTypesRepo;
	@Autowired
	private GestationRepo gestationRepo;
	@Autowired
	private TypeOfImmunizationMasterRepo typeOfImmunizationMasterRepo;
	@Autowired
	private DeliveryConductedByMasterRepo deliveryConductedByMasterRepo;
	@Autowired
	private ChildVaccinationsRepo childVaccinationsRepo;
	@Autowired
	private ProcedureRepo procedureRepo;
	@Autowired
	private ChiefComplaintMasterRepo chiefComplaintMasterRepo;

	@Autowired
	private OralVitaminNoOfDoseMasterRepo OralVitaminNoOfDoseMasterRepo;

	public String getNurseMasterDataNeonatal(Integer visitCategoryID, int psmID, String gender) throws IEMRException {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		Map<String, Object> resMap = new HashMap<>();

		resMap.put("m_birthdosevaccinationreceivedat", birthDoseVaccinesMasterRepo.findByDeletedOrderByName(false));
		resMap.put("m_congenitalanomalies", congenitalAnomaliesMasterRepo.findByDeletedOrderByName(false));

		resMap.put("m_counsellingprovided",
				counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(false, visitCategoryID));

		resMap.put("m_currentimmunizationservice",
				currentImmunizationServiceMasterRepo.findByDeletedAndVisitCategoryIDOrderById(false, visitCategoryID));
		resMap.put("m_nextduevaccines", nextDueVaccinesMasterRepo.findByDeletedOrderById(false));
		resMap.put("m_locationofnextimmunization", nextImmunizationLocationMasterRepo.findByDeletedOrderByName(false));
		resMap.put("m_immunizationservicestype", typeOfImmunizationMasterRepo.findByDeleted(false));

		resMap.put("deliveryConductedByMaster", deliveryConductedByMasterRepo.findByDeleted(false));
		resMap.put("deliveryPlaces", deliveryPlaceRepo.findByDeleted(false));
		resMap.put("deliveryTypes", deliveryTypeRepo.findByDeleted(false));
		resMap.put("chiefComplaintMaster", chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(false));
		resMap.put("gestation", gestationRepo.findByDeletedOrderByName(false));
		resMap.put("childVaccinations", childVaccinationsRepo.findByDeletedOrderByVaccinationTime(false));

		ArrayList<Object[]> birthComplications = complicationTypesRepo.getComplicationTypes("Birth Complication");
		resMap.put("birthComplications", ComplicationTypes.getComplicationTypes(birthComplications, 0));

		ArrayList<Object[]> procedures = procedureRepo.getProcedureMasterData(psmID, gender);
		resMap.put("procedures", ProcedureData.getProcedures(procedures));

		if (visitCategoryID == 11)
			resMap.put("oralVitaminNoDose", OralVitaminNoOfDoseMasterRepo.findByDeleted(false));
		return gson.toJson(resMap);
	}

}
