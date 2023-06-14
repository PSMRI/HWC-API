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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_idrsscreenquestions")
public class IDRS_ScreenQuestions {

	@Id
	@GeneratedValue
	@Expose
	@Column(name = "IDRSQuestionID")
	private Integer idrsQuestionID;

	@Expose
	@Column(name = "Question")
	private String question;

	@Expose
	@Column(name = "DiseaseQuestionType")
	private String DiseaseQuestionType;

	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public Integer getIdrsQuestionID() {
		return idrsQuestionID;
	}

	public void setIdrsQuestionID(Integer idrsQuestionID) {
		this.idrsQuestionID = idrsQuestionID;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getDiseaseQuestionType() {
		return DiseaseQuestionType;
	}

	public void setDiseaseQuestionType(String diseaseQuestionType) {
		DiseaseQuestionType = diseaseQuestionType;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
