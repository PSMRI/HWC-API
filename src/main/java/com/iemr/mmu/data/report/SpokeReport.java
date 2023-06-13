/* LicenseInfo : Copyright © 2023 Piramal */ 
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
