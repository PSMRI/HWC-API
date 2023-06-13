/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.report;

import java.math.BigInteger;

import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.utils.mapper.OutputMapper;

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
