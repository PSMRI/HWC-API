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
package com.iemr.hwc.data.bmi;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
@Entity
@Table(name = "m_bmicalculation")
public class BmiCalculation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "Bmiid")
	private Long Bmiid;
	@Expose
	@Column(name = "YearMonth")
	private String yearMonth;
	@Expose
	@Column(name = "Month")
	private Integer month;
	@Expose
	@Column(name = "N3SD")
	private double N3SD;
	@Expose
	@Column(name = "N2SD")
	private double N2SD;
	@Expose
	@Column(name = "N1SD")
	private double N1SD;
	@Expose
	@Column(name = "P1SD")
	private double P1SD;
	@Expose
	@Column(name = "P2SD")
	private double P2SD;
	@Expose
	@Column(name = "P3SD")
	private double P3SD;
	@Expose
	@Column(name = "Gender")
	private String gender;
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = false)
	private Boolean deleted;
	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;
	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Expose
	@Column(name = "LastModDate")
	private Timestamp lastModDate;
	@Expose
	@Transient
	private double bmi;
	
	public Long getBmiid() {
		return Bmiid;
	}
	public void setBmiid(Long bmiid) {
		Bmiid = bmiid;
	}
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public double getN3SD() {
		return N3SD;
	}
	public void setN3SD(double n3sd) {
		N3SD = n3sd;
	}
	public double getN2SD() {
		return N2SD;
	}
	public void setN2SD(double n2sd) {
		N2SD = n2sd;
	}
	public double getN1SD() {
		return N1SD;
	}
	public void setN1SD(double n1sd) {
		N1SD = n1sd;
	}
	public double getP1SD() {
		return P1SD;
	}
	public void setP1SD(double p1sd) {
		P1SD = p1sd;
	}
	public double getP2SD() {
		return P2SD;
	}
	public void setP2SD(double p2sd) {
		P2SD = p2sd;
	}
	public double getP3SD() {
		return P3SD;
	}
	public void setP3SD(double p3sd) {
		P3SD = p3sd;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public String getProcessed() {
		return processed;
	}
	public void setProcessed(String processed) {
		this.processed = processed;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getLastModDate() {
		return lastModDate;
	}
	public void setLastModDate(Timestamp lastModDate) {
		this.lastModDate = lastModDate;
	}
	public double getBmi() {
		return bmi;
	}
	public void setBmi(double bmi) {
		this.bmi = bmi;
	}
	
}
