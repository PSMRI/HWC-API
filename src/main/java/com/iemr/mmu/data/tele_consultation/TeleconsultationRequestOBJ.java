/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.tele_consultation;

import java.sql.Timestamp;

import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;

public class TeleconsultationRequestOBJ {
	private Integer userID;
	private Timestamp allocationDate;
	private @SQLInjectionSafe String fromTime;
	private @SQLInjectionSafe String toTime;
	private Integer specializationID;

	private Long tmRequestID;

	private Boolean walkIn;

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Timestamp getAllocationDate() {
		return allocationDate;
	}

	public void setAllocationDate(Timestamp allocationDate) {
		this.allocationDate = allocationDate;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public Integer getSpecializationID() {
		return specializationID;
	}

	public void setSpecializationID(Integer specializationID) {
		this.specializationID = specializationID;
	}

	public Long getTmRequestID() {
		return tmRequestID;
	}

	public void setTmRequestID(Long tmRequestID) {
		this.tmRequestID = tmRequestID;
	}

	public Boolean getWalkIn() {
		return walkIn;
	}

	public void setWalkIn(Boolean walkIn) {
		this.walkIn = walkIn;
	}
	

}
