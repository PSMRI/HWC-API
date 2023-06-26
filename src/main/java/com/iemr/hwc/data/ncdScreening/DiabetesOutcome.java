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
package com.iemr.hwc.data.ncdScreening;

public class DiabetesOutcome {

	Integer bloodGlucoseTypeID;

	String bloodGlucoseType;

	Integer bloodGlucose;

	public Integer getBloodGlucoseTypeID() {
		return bloodGlucoseTypeID;
	}

	public void setBloodGlucoseTypeID(Integer bloodGlucoseTypeID) {
		this.bloodGlucoseTypeID = bloodGlucoseTypeID;
	}

	public String getBloodGlucoseType() {
		return bloodGlucoseType;
	}

	public void setBloodGlucoseType(String bloodGlucoseType) {
		this.bloodGlucoseType = bloodGlucoseType;
	}

	public Integer getBloodGlucose() {
		return bloodGlucose;
	}

	public void setBloodGlucose(Integer bloodGlucose) {
		this.bloodGlucose = bloodGlucose;
	}
	
	
}
