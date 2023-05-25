package com.iemr.mmu.data.patientApp;

public class ChiefComplaintsPatientAPP {

	private String illnessType;
	private Integer illnessTypeID;
	private Integer timePeriodAgo;
	private String timePeriodUnit;

	public String getIllnessType() {
		return illnessType;
	}

	public void setIllnessType(String illnessType) {
		this.illnessType = illnessType;
	}

	public Integer getIllnessTypeID() {
		return illnessTypeID;
	}

	public void setIllnessTypeID(Integer illnessTypeID) {
		this.illnessTypeID = illnessTypeID;
	}

	public Integer getTimePeriodAgo() {
		return timePeriodAgo;
	}

	public void setTimePeriodAgo(Integer timePeriodAgo) {
		this.timePeriodAgo = timePeriodAgo;
	}

	public String getTimePeriodUnit() {
		return timePeriodUnit;
	}

	public void setTimePeriodUnit(String timePeriodUnit) {
		this.timePeriodUnit = timePeriodUnit;
	}

}
