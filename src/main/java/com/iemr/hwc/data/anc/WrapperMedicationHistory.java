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
import java.util.Map;

import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;
import com.iemr.hwc.service.anc.Utility;

public class WrapperMedicationHistory {

	private Long beneficiaryRegID;
	private Long benVisitID;
	private Long visitCode;
	private Integer providerServiceMapID;
	private @SQLInjectionSafe String createdBy;

	private Integer vanID;
	private Integer parkingPlaceID;

	private ArrayList<BenMedicationHistory> medicationHistoryList;

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

	public ArrayList<BenMedicationHistory> getMedicationHistoryList() {
		return medicationHistoryList;
	}

	public void setMedicationHistoryList(ArrayList<BenMedicationHistory> medicationHistoryList) {
		this.medicationHistoryList = medicationHistoryList;
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

	public ArrayList<BenMedicationHistory> getBenMedicationHistoryDetails() {
		ArrayList<BenMedicationHistory> medicationHistoryListTMP = new ArrayList<>();
		for (BenMedicationHistory medicationHistory : medicationHistoryList) {
			if (medicationHistory.getCurrentMedication() != null) {
				String timePeriodUnit = medicationHistory.getTimePeriodUnit();
				Integer timePeriodAgo = medicationHistory.getTimePeriodAgo();

				medicationHistory.setBeneficiaryRegID(beneficiaryRegID);
				medicationHistory.setBenVisitID(benVisitID);
				medicationHistory.setVisitCode(visitCode);
				medicationHistory.setProviderServiceMapID(providerServiceMapID);
				medicationHistory.setVanID(vanID);
				medicationHistory.setParkingPlaceID(parkingPlaceID);
				medicationHistory.setCreatedBy(createdBy);

				medicationHistory.setYear(Utility.convertToDateFormat(timePeriodUnit, timePeriodAgo));

				medicationHistoryListTMP.add(medicationHistory);
			}
		}
		return medicationHistoryListTMP;
	}

	public static WrapperMedicationHistory getMedicationHistoryDetails(ArrayList<Object[]> medicationHistoryDetails) {
		WrapperMedicationHistory WMH = new WrapperMedicationHistory();
		WMH.medicationHistoryList = new ArrayList<BenMedicationHistory>();

		if (null != medicationHistoryDetails && medicationHistoryDetails.size() > 0) {
			Object[] obj1 = medicationHistoryDetails.get(0);
			WMH.beneficiaryRegID = (Long) obj1[0];
			WMH.benVisitID = (Long) obj1[1];
			WMH.providerServiceMapID = (Integer) obj1[2];

			Integer timePeriodAgo;
			for (Object[] obj : medicationHistoryDetails) {
				timePeriodAgo = null;

				Map<String, Object> timePeriod = Utility.convertTimeToWords((Timestamp) obj[4], (Timestamp) obj[5]);

				if (timePeriod != null && timePeriod.get("timePeriodAgo") != null
						&& timePeriod.get("timePeriodAgo") instanceof java.lang.Integer)
					timePeriodAgo = (Integer) timePeriod.get("timePeriodAgo");

				String timePeriodUnit = null;
				if (timePeriod != null && timePeriod.containsKey("timePeriodUnit")
						&& timePeriod.get("timePeriodUnit") != null)
					timePeriodUnit = timePeriod.get("timePeriodUnit").toString();

				BenMedicationHistory medicationHistory = new BenMedicationHistory((String) obj[3], timePeriodAgo,
						timePeriodUnit, (Long) obj[6]);

				WMH.medicationHistoryList.add(medicationHistory);
			}
		}
		return WMH;
	}

}
