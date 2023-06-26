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
package com.iemr.hwc.data.anc;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;

public class WrapperChildOptionalVaccineDetail {

	private Long beneficiaryRegID;
	private Long benVisitID;
	private Long visitCode;
	private Integer providerServiceMapID;
	private @SQLInjectionSafe String createdBy;

	private Integer vanID;
	private Integer parkingPlaceID;

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Long getBenVisitID() {
		return benVisitID;
	}

	public void setBenVisitID(Long benVisitID) {
		this.benVisitID = benVisitID;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	private ArrayList<ChildOptionalVaccineDetail> childOptionalVaccineList;

	public ArrayList<ChildOptionalVaccineDetail> getChildOptionalVaccineList() {
		return childOptionalVaccineList;
	}

	public void setChildOptionalVaccineList(ArrayList<ChildOptionalVaccineDetail> childOptionalVaccineList) {
		this.childOptionalVaccineList = childOptionalVaccineList;
	}

	public ArrayList<ChildOptionalVaccineDetail> getChildOptionalVaccineDetails() {

		ArrayList<ChildOptionalVaccineDetail> childOptionalVaccineListTMP = new ArrayList<>();
		if (null != childOptionalVaccineList && childOptionalVaccineList.size() > 0) {
			for (ChildOptionalVaccineDetail childOptionalVaccine : childOptionalVaccineList) {

				if (childOptionalVaccine.getVaccineName() != null) {
					childOptionalVaccine.setBeneficiaryRegID(beneficiaryRegID);
					childOptionalVaccine.setBenVisitID(benVisitID);
					childOptionalVaccine.setVisitCode(visitCode);
					childOptionalVaccine.setProviderServiceMapID(providerServiceMapID);
					childOptionalVaccine.setVanID(vanID);
					childOptionalVaccine.setParkingPlaceID(parkingPlaceID);
					childOptionalVaccine.setCreatedBy(createdBy);

					childOptionalVaccineListTMP.add(childOptionalVaccine);
				}
			}
		}
		// else {
		// ChildOptionalVaccineDetail childOptionalVaccine = new
		// ChildOptionalVaccineDetail();
		// childOptionalVaccine.setBeneficiaryRegID(beneficiaryRegID);
		// childOptionalVaccine.setBenVisitID(benVisitID);
		// childOptionalVaccine.setProviderServiceMapID(providerServiceMapID);
		// childOptionalVaccine.setCreatedBy(createdBy);
		//
		// childOptionalVaccineList = new
		// ArrayList<ChildOptionalVaccineDetail>();
		// childOptionalVaccineList.add(childOptionalVaccine);
		// }
		return childOptionalVaccineListTMP;
	}

	public static WrapperChildOptionalVaccineDetail getChildOptionalVaccineDetail(
			ArrayList<Object[]> childOptionalVaccineDetail) {
		WrapperChildOptionalVaccineDetail WCO = new WrapperChildOptionalVaccineDetail();
		WCO.childOptionalVaccineList = new ArrayList<ChildOptionalVaccineDetail>();

		if (null != childOptionalVaccineDetail && childOptionalVaccineDetail.size() > 0) {
			Object[] obj1 = childOptionalVaccineDetail.get(0);
			WCO.beneficiaryRegID = (Long) obj1[0];
			WCO.benVisitID = (Long) obj1[1];
			WCO.providerServiceMapID = (Integer) obj1[2];
			WCO.visitCode = (Long) obj1[10];
			for (Object[] obj : childOptionalVaccineDetail) {

				ChildOptionalVaccineDetail obstetricHistory = new ChildOptionalVaccineDetail((String) obj[3],
						(String) obj[4], (String) obj[5], (String) obj[6], (Timestamp) obj[7], (String) obj[8],
						(String) obj[9], (String) obj[11], (String) obj[12], (Integer) obj[13], (String) obj[14]);

				WCO.childOptionalVaccineList.add(obstetricHistory);

			}
		}
		return WCO;
	}
}
