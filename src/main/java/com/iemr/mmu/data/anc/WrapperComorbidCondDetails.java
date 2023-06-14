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
package com.iemr.mmu.data.anc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;
import com.iemr.mmu.service.anc.Utility;

public class WrapperComorbidCondDetails {

	private Long beneficiaryRegID;
	private Long benVisitID;
	private Long visitCode;
	private Integer providerServiceMapID;
	private @SQLInjectionSafe String createdBy;

	private Integer vanID;
	private Integer parkingPlaceID;

	private ArrayList<BencomrbidityCondDetails> comorbidityConcurrentConditionsList;

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

	public ArrayList<BencomrbidityCondDetails> getComorbidityConcurrentConditionsList() {
		return comorbidityConcurrentConditionsList;
	}

	public void setComorbidityConcurrentConditionsList(
			ArrayList<BencomrbidityCondDetails> comorbidityConcurrentConditionsList) {
		this.comorbidityConcurrentConditionsList = comorbidityConcurrentConditionsList;
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

	public ArrayList<BencomrbidityCondDetails> getComrbidityConds() {
		ArrayList<BencomrbidityCondDetails> comorbidityConcurrentConditionsListTMP = new ArrayList<>();
		for (BencomrbidityCondDetails comrbidityCond : comorbidityConcurrentConditionsList) {
			if (comrbidityCond.getComorbidCondition() != null) {
				String timePeriodUnit = comrbidityCond.getTimePeriodUnit();
				Integer timePeriodAgo = comrbidityCond.getTimePeriodAgo();

				comrbidityCond.setBeneficiaryRegID(beneficiaryRegID);
				comrbidityCond.setBenVisitID(benVisitID);
				comrbidityCond.setVisitCode(visitCode);
				comrbidityCond.setProviderServiceMapID(providerServiceMapID);
				comrbidityCond.setVanID(vanID);
				comrbidityCond.setParkingPlaceID(parkingPlaceID);
				comrbidityCond.setCreatedBy(createdBy);
				comrbidityCond.setYear(Utility.convertToDateFormat(timePeriodUnit, timePeriodAgo));

				comorbidityConcurrentConditionsListTMP.add(comrbidityCond);
			}
		}

		return comorbidityConcurrentConditionsListTMP;
	}

	public static WrapperComorbidCondDetails getComorbidityDetails(ArrayList<Object[]> comrbidityCondDetails) {
		WrapperComorbidCondDetails WCD = new WrapperComorbidCondDetails();
		WCD.comorbidityConcurrentConditionsList = new ArrayList<BencomrbidityCondDetails>();

		if (null != comrbidityCondDetails && comrbidityCondDetails.size() > 0) {
			Object[] obj1 = comrbidityCondDetails.get(0);
			WCD.beneficiaryRegID = (Long) obj1[0];
			WCD.benVisitID = (Long) obj1[1];
			WCD.providerServiceMapID = (Integer) obj1[2];

			Integer timePeriodAgo;
			for (Object[] obj : comrbidityCondDetails) {
				timePeriodAgo = null;

				Map<String, Object> timePeriod = Utility.convertTimeToWords((Timestamp) obj[5], (Timestamp) obj[8]);

				if (timePeriod != null && timePeriod.get("timePeriodAgo") != null
						&& timePeriod.get("timePeriodAgo") instanceof java.lang.Integer)
					timePeriodAgo = Integer.parseInt(timePeriod.get("timePeriodAgo").toString());

				String timePeriodUnit = null;
				if (timePeriod != null && timePeriod.containsKey("timePeriodUnit")
						&& timePeriod.get("timePeriodUnit") != null)
					timePeriodUnit = timePeriod.get("timePeriodUnit").toString();

				BencomrbidityCondDetails comrbidityConds = new BencomrbidityCondDetails((Short) obj[3], (String) obj[4],
						(String) obj[6], (Boolean) obj[7], timePeriodAgo, timePeriodUnit, (Long) obj[9]);

				WCD.comorbidityConcurrentConditionsList.add(comrbidityConds);

			}
		}
		return WCD;
	}

}
