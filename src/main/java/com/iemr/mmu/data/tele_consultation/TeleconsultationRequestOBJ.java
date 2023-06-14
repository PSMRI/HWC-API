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
