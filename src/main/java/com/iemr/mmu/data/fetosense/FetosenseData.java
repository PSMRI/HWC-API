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
package com.iemr.mmu.data.fetosense;

import java.sql.Timestamp;

public class FetosenseData {

	private Long partnerFetosenseId;
	private Long beneficiaryRegID;
	private String motherLMPDate;
	private String motherName;
	private String testName;
	private String deviceId;
	
	public Long getPartnerFetosenseID() {
		return partnerFetosenseId;
	}
	public void setPartnerFetosenseID(Long partnerFetosenseID) {
		this.partnerFetosenseId = partnerFetosenseID;
	}
	public String getMotherLMPDate() {
		return motherLMPDate;
	}
	public void setMotherLMPDate(String motherLMPDate) {
		this.motherLMPDate = motherLMPDate;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName; 
	}
	
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}
	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}
	public String getDeviceID() {
		return deviceId;
	}
	public void setDeviceID(String deviceID) {
		this.deviceId = deviceID;
	}
	

}
