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
package com.iemr.mmu.data.report;

import java.util.List;

import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.utils.mapper.OutputMapper;

public class SpokeReport {

	@Expose
	private Integer vanID;
	@Expose
	private String vanName;
	@Expose
	private List<ChiefComplaintReport> chiefComplaintReport;

	@Transient
	private OutputMapper outputMapper = new OutputMapper();

	@Override
	public String toString() {
		return outputMapper.gson().toJson(this);
	}

	public OutputMapper getOutputMapper() {
		return outputMapper;
	}

	public void setOutputMapper(OutputMapper outputMapper) {
		this.outputMapper = outputMapper;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public String getVanName() {
		return vanName;
	}

	public void setVanName(String vanName) {
		this.vanName = vanName;
	}

	@Override
	public int hashCode() {
		return this.vanID * 37 + (this.vanName!=null?this.vanName.hashCode():0);
	}

	@Override
	public boolean equals(Object obj) {

		// if both the object references are
		// referring to the same object.
		if (this == obj)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;

		// type casting of the argument.
		SpokeReport spoke = (SpokeReport) obj;

		// comparing the state of argument with
//		// the state of 'this' Object.
//		System.out.println(this);
//		System.out.println(spoke);
//		System.out.println(spoke.vanName.equals(this.vanName) );
//		System.out.println(spoke.vanID.equals(this.vanID));
		return (spoke.vanName.equals(this.vanName) && spoke.vanID.equals(this.vanID));
	}


	public List<ChiefComplaintReport> getChiefComplaintReport() {
		return chiefComplaintReport;
	}

	public void setChiefComplaintReport(List<ChiefComplaintReport> chiefComplaintReport) {
		this.chiefComplaintReport = chiefComplaintReport;
	}
	
}
