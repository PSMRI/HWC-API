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
package com.iemr.hwc.data.report;

import java.math.BigInteger;

import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.utils.mapper.OutputMapper;

public class ChiefComplaintReport {

	@Expose
	private Integer chiefComplaintID;
	@Expose
	private String chiefComplaint;
	@Expose
	private Integer vanID;
	@Expose
	private Integer vanName;
	@Expose
	private BigInteger male;
	@Expose
	private BigInteger female;
	@Expose
	private BigInteger transgender;
	@Expose
	private BigInteger grandTotal;

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

	public Integer getChiefComplaintID() {
		return chiefComplaintID;
	}

	public void setChiefComplaintID(Integer chiefComplaintID) {
		this.chiefComplaintID = chiefComplaintID;
	}

	public String getChiefComplaint() {
		return chiefComplaint;
	}

	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Integer getVanName() {
		return vanName;
	}

	public void setVanName(Integer vanName) {
		this.vanName = vanName;
	}

	public BigInteger getMale() {
		return male;
	}

	public void setMale(BigInteger male) {
		this.male = male;
	}

	public BigInteger getFemale() {
		return female;
	}

	public void setFemale(BigInteger female) {
		this.female = female;
	}

	public BigInteger getTransgender() {
		return transgender;
	}

	public void setTransgender(BigInteger transgender) {
		this.transgender = transgender;
	}

	public BigInteger getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(BigInteger grandTotal) {
		this.grandTotal = grandTotal;
	}

}
