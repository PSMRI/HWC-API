package com.iemr.mmu.data.report;

import java.math.BigInteger;

import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.utils.mapper.OutputMapper;

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
