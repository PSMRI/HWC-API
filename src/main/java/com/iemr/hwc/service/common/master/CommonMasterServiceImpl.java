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
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.neonatal.ImmunizationServiceVaccinationMaster;
import com.iemr.hwc.repo.neonatal.ImmunizationServiceDoseMasterRepo;
import com.iemr.hwc.repo.neonatal.ImmunizationServiceRouteMasterRepo;
import com.iemr.hwc.repo.neonatal.ImmunizationServiceVaccinationMasterRepo;
import com.iemr.hwc.repo.neonatal.SiteOfInjectionMasterRepo;
import com.iemr.hwc.service.family_planning.FamilyPlanningMasterService;
import com.iemr.hwc.service.neonatal.NeonatalMasterService;
import com.iemr.hwc.utils.exception.IEMRException;

@Service
public class CommonMasterServiceImpl implements CommonMaterService {

	private ANCMasterDataServiceImpl ancMasterDataServiceImpl;
	private NurseMasterDataServiceImpl nurseMasterDataServiceImpl;
	private DoctorMasterDataServiceImpl doctorMasterDataServiceImpl;

	private NCDScreeningMasterServiceImpl ncdScreeningServiceImpl;

	@Autowired
	private FamilyPlanningMasterService familyPlanningMasterService;

	@Autowired
	private NeonatalMasterService neonatalMasterService;

	@Autowired
	public void setAncMasterDataServiceImpl(ANCMasterDataServiceImpl ancMasterDataServiceImpl) {
		this.ancMasterDataServiceImpl = ancMasterDataServiceImpl;
	}

	@Autowired
	public void setNurseMasterDataServiceImpl(NurseMasterDataServiceImpl nurseMasterDataServiceImpl) {
		this.nurseMasterDataServiceImpl = nurseMasterDataServiceImpl;
	}

	@Autowired
	public void setDoctorMasterDataServiceImpl(DoctorMasterDataServiceImpl doctorMasterDataServiceImpl) {
		this.doctorMasterDataServiceImpl = doctorMasterDataServiceImpl;
	}

	@Autowired
	public void setNcdScreeningServiceImpl(NCDScreeningMasterServiceImpl ncdScreeningServiceImpl) {
		this.ncdScreeningServiceImpl = ncdScreeningServiceImpl;
	}

	@Override
	public String getVisitReasonAndCategories() {
		String visitReasonAndCategories = nurseMasterDataServiceImpl.GetVisitReasonAndCategories();
		return visitReasonAndCategories;
	}

	@Override
	public String getMasterDataForNurse(Integer visitCategoryID, Integer providerServiceMapID, String gender)
			throws IEMRException {
		String nurseMasterData = null;
		if (null != visitCategoryID) {
			switch (visitCategoryID) {
			case 1: {
				// 1 : Cancer Screening
				nurseMasterData = nurseMasterDataServiceImpl.getCancerScreeningMasterDataForNurse();
			}
				break;
			case 2: {
				// 2 : NCD screening
				nurseMasterData = ncdScreeningServiceImpl.getNCDScreeningMasterData(visitCategoryID,
						providerServiceMapID, gender);
			}
				break;
			case 3: {
				// 3 : NCD care
				// TODO: NCD Care Master Data call - tmprlly calling ANC master Data
				nurseMasterData = ancMasterDataServiceImpl
						.getCommonNurseMasterDataForGenopdAncNcdcarePnc(visitCategoryID, providerServiceMapID, gender);
			}
				break;
			case 4: {
				// 4 : ANC
				nurseMasterData = ancMasterDataServiceImpl
						.getCommonNurseMasterDataForGenopdAncNcdcarePnc(visitCategoryID, providerServiceMapID, gender);
			}
				break;
			case 5: {
				// 5 : PNC
				// TODO: PNC Master Data call - tmprlly calling ANC master Data
				nurseMasterData = ancMasterDataServiceImpl
						.getCommonNurseMasterDataForGenopdAncNcdcarePnc(visitCategoryID, providerServiceMapID, gender);
			}
				break;
			case 6: {
				// 6 : General OPD
				// TODO: General OPD Master Data call - tmprlly calling ANC master Data
				nurseMasterData = ancMasterDataServiceImpl
						.getCommonNurseMasterDataForGenopdAncNcdcarePnc(visitCategoryID, providerServiceMapID, gender);
			}
				break;
			case 7: {
				// 7 : General OPD (QC)
				nurseMasterData = "No Master Data found for QuickConsultation";
			}
				break;
			case (8): {
				// 8 : Covid 19 - pandemic
				nurseMasterData = ancMasterDataServiceImpl
						.getCommonNurseMasterDataForGenopdAncNcdcarePnc(visitCategoryID, providerServiceMapID, gender);
			}
				break;

			case (9): {
				// 9 : FP - contraceptive service
				nurseMasterData = familyPlanningMasterService.getMasterDataFP(visitCategoryID, providerServiceMapID,
						gender);
			}
				break;
			case (10): {
				// 10 : Neonatal and infant health care services
				nurseMasterData = neonatalMasterService.getNurseMasterDataNeonatal(visitCategoryID,
						providerServiceMapID, gender);

			}
				break;
			case (11): {
				// 11 : Childhood & Adolescent Healthcare Services
				nurseMasterData = neonatalMasterService.getNurseMasterDataNeonatal(visitCategoryID,
						providerServiceMapID, gender);

			}
				break;
			default: {
				nurseMasterData = "Invalid VisitCategoryID";
			}
			}
		} else {
			nurseMasterData = "Invalid VisitCategoryID";
		}
		return nurseMasterData;
	}

	@Override
	public String getMasterDataForDoctor(Integer visitCategoryID, Integer providerServiceMapID, String gender,
			Integer facilityID, Integer vanID) {
		String doctorMasterData = null;
		if (null != visitCategoryID) {
			switch (visitCategoryID) {
			case 1: {
				// 1 : Cancer Screening
				// neeraj passed one parameter for tem reason
				doctorMasterData = doctorMasterDataServiceImpl
						.getCancerScreeningMasterDataForDoctor(providerServiceMapID);
			}
				break;
			case 2: {
				// 2 : NCD screening
				// TODO: NCD SCreening Master Data call
//				doctorMasterData = "No Master Data found for NCD SCreening";
				doctorMasterData = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(
						visitCategoryID, providerServiceMapID, gender, facilityID, vanID);
			}
				break;
			case 3: {
				// 3 : NCD care
				// TODO: NCD Care Master Data call
				doctorMasterData = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(
						visitCategoryID, providerServiceMapID, gender, facilityID, vanID);
			}
				break;
			case 4: {
				// 4 : ANC
				doctorMasterData = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(
						visitCategoryID, providerServiceMapID, gender, facilityID, vanID);
			}
				break;
			case 5: {
				// 5 : PNC
				// TODO: PNC Master Data call - tmprlly calling ANC master Data
				doctorMasterData = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(
						visitCategoryID, providerServiceMapID, gender, facilityID, vanID);
			}
				break;
			case 6: {
				// 6 : General OPD
				// TODO: General OPD Master Data call - tmprlly calling ANC master Data
				doctorMasterData = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(
						visitCategoryID, providerServiceMapID, gender, facilityID, vanID);
			}
				break;
			case 7: {
				// 7 : General OPD (QC)
				// doctorMasterData =
				// qCMasterDataServiceImpl.getQuickConsultMasterData(providerServiceMapID,
				// gender,
				// facilityID);

				// ne298657 have commented the code on 27-07-2018
				doctorMasterData = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(
						visitCategoryID, providerServiceMapID, gender, facilityID, vanID);
			}
				break;
			case 8: {
				// 8, covid
				doctorMasterData = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(
						visitCategoryID, providerServiceMapID, gender, facilityID, vanID);
			}
				break;

			case 9: {
				// 9 : FP - contraceptive service
				doctorMasterData = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(
						visitCategoryID, providerServiceMapID, gender, facilityID, vanID);
			}
				break;
			case (10): {
				// 12 : Neonatal and infant health care services
				doctorMasterData = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(
						visitCategoryID, providerServiceMapID, gender, facilityID, vanID);

			}
				break;
			case (11): {
				//11 : Childhood & Adolescent Healthcare Services
				doctorMasterData = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(
						visitCategoryID, providerServiceMapID, gender, facilityID, vanID);
				
			}
			    break;

			default: {
				doctorMasterData = "Invalid VisitCategoryID";
			}
			}
		} else {
			doctorMasterData = "Invalid VisitCategoryID";
		}
		return doctorMasterData;
	}

	@Autowired
	private ImmunizationServiceVaccinationMasterRepo immunizationServiceVaccinationMasterRepo;
	@Autowired
	private ImmunizationServiceDoseMasterRepo immunizationServiceDoseMasterRepo;
	@Autowired
	private ImmunizationServiceRouteMasterRepo immunizationServiceRouteMasterRepo;
	@Autowired
	private SiteOfInjectionMasterRepo siteOfInjectionMasterRepo;

	@Override
	public String getVaccineDetailsForCISID(Integer CISID, Integer visitCategoryID) throws IEMRException {

		Map<String, List<Map<String, Object>>> responseMap = new HashMap<>();

		List<Map<String, Object>> responseList = new ArrayList<Map<String, Object>>();
		Map<String, Object> vaccineMap;

		if (CISID != null && visitCategoryID != null ) {
			List<ImmunizationServiceVaccinationMaster> vaccineList = immunizationServiceVaccinationMasterRepo
					.findByCurrentImmunizationServiceIdAndDeletedAndVisitCategoryID(CISID, false, visitCategoryID);

			if (vaccineList != null && vaccineList.size() > 0) {
				for (ImmunizationServiceVaccinationMaster vaccine : vaccineList) {
					vaccineMap = new HashMap<String, Object>();

					vaccineMap.put("vaccineId", vaccine.getVaccinationID());
					vaccineMap.put("vaccine", vaccine.getVaccineName());

					vaccineMap.put("dose", immunizationServiceDoseMasterRepo
							.findByVaccinationIDAndDeletedOrderByDose(vaccine.getVaccinationID(), false));
					vaccineMap.put("route", immunizationServiceRouteMasterRepo
							.findByVaccinationIDAndDeletedOrderByRoute(vaccine.getVaccinationID(), false));
					vaccineMap.put("siteOfInjection", siteOfInjectionMasterRepo
							.findByVaccinationIDAndDeletedOrderBySiteofinjection(vaccine.getVaccinationID(), false));

					responseList.add(vaccineMap);

				}
			}
		}

		responseMap.put("vaccineList", responseList);

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(responseMap);

	}

}
