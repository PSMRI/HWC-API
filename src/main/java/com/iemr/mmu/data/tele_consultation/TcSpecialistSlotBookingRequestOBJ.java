/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.tele_consultation;

import java.sql.Timestamp;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;

public class TcSpecialistSlotBookingRequestOBJ {
	@Expose
	private Integer userID;
	@Expose
	private Timestamp date;
	@Expose
	private @SQLInjectionSafe String fromTime;
	@Expose
	private @SQLInjectionSafe String toTime;
	@Expose
	private Long duration;
	@Expose
	private @SQLInjectionSafe String modifiedBy;
	@Expose
	private @SQLInjectionSafe String createdBy;

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
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

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
