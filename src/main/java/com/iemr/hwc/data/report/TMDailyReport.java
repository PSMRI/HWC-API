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

import jakarta.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.utils.mapper.OutputMapper;

public class TMDailyReport {

	@Expose
	private String spokeName;
	
	@Expose
	private BigInteger currentConsultations;
	
	@Expose
	private BigInteger revisitConsultations;
	
	
	@Expose
	private BigInteger cumulativeConsultationsForMonth;
	
	@Expose
	private BigInteger cumulativeRevisitConsultationsForMonth;
	

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

	public String getSpokeName() {
		return spokeName;
	}

	public void setSpokeName(String spokeName) {
		this.spokeName = spokeName;
	}

	public BigInteger getCurrentConsultations() {
		return currentConsultations;
	}

	public void setCurrentConsultations(BigInteger currentConsultations) {
		this.currentConsultations = currentConsultations;
	}

	public BigInteger getRevisitConsultations() {
		return revisitConsultations;
	}

	public void setRevisitConsultations(BigInteger revisitConsultations) {
		this.revisitConsultations = revisitConsultations;
	}

	public BigInteger getCumulativeConsultationsForMonth() {
		return cumulativeConsultationsForMonth;
	}

	public void setCumulativeConsultationsForMonth(BigInteger cumulativeConsultationsForMonth) {
		this.cumulativeConsultationsForMonth = cumulativeConsultationsForMonth;
	}

	public BigInteger getCumulativeRevisitConsultationsForMonth() {
		return cumulativeRevisitConsultationsForMonth;
	}

	public void setCumulativeRevisitConsultationsForMonth(BigInteger cumulativeRevisitConsultationsForMonth) {
		this.cumulativeRevisitConsultationsForMonth = cumulativeRevisitConsultationsForMonth;
	}


}
