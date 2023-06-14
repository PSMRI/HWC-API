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
package com.iemr.mmu.data.masterdata.ncdscreening;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_cbacquestions")
public class CBAC {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "id")
	private Integer id;
	@Expose
	@Column(name = "questionid")
	private Integer questionId;
	@Expose
	@Column(name = "name")
	private String name;
	@Expose
	@Column(name = "Gender")
	private String gender;
	@Expose
	@Column(name = "Range")
	private String range;
	@Expose
	@Column(name = "Cbac_Score")
	private Integer cbac_Score;
	@Expose
	@Column(name = "questiontype")
	private String questionType;
	@Expose
	@Column(name = "section")
	private String section;
	@Expose
	@Column(name = "Deleted")
	private boolean deleted;
	@Expose
	@Column(name="Processed")
	private Character processed;
	@Expose
    @Column(name="CreatedBy")
	private String createdBy;
	@Expose
    @Column(name="CreatedDate")
	private Timestamp createdDate;
	@Expose
    @Column(name="ModifiedBy") 
	private String modifiedBy;
	@Expose
    @Column(name="LastModDate") 
	private Timestamp lastModDate;
	
	@Transient
	@Expose
	private List<Map<String, String>> option;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getQuestionid() {
		return questionId;
	}
	public void setQuestionid(Integer questionid) {
		this.questionId = questionid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFemale_Male() {
		return gender;
	}
	public void setFemale_Male(String female_Male) {
		this.gender = female_Male;
	}
	public String getRange() {
		return range;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public Integer getCbac_Score() {
		return cbac_Score;
	}
	public void setCbac_Score(Integer cbac_Score) {
		this.cbac_Score = cbac_Score;
	}
	public String getQuestiontype() {
		return questionType;
	}
	public void setQuestiontype(String questiontype) {
		this.questionType = questiontype;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Character getProcessed() {
		return processed;
	}
	public void setProcessed(Character processed) {
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
	
	
	public List<Map<String, String>> getOption() {
		return option;
	}
	public void setOption(List<Map<String, String>> option) {
		this.option = option;
	}
	
	
	
	
	
	
	
	
	

}
