/* LicenseInfo : Copyright © 2023 Piramal */ 
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
