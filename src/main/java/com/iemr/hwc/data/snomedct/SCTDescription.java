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
package com.iemr.hwc.data.snomedct;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "sct_description")
public class SCTDescription {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@Expose
	@Column(name = "sctDesID")
	private Long sctDesID;

	/*
	 * @Expose
	 * 
	 * @Column(name = "sctCode") private String sctCode;
	 */

	// @Expose
	@Column(name = "Active")
	private String active;

	@Expose
	@Column(name = "ConceptID")
	private String conceptID;

	@Expose
	@Column(name = "Term")
	private String term;

	// @Expose
	@Column(name = "CaseSignificanceID")
	private String caseSignificanceID;

	@Transient
	private Integer pageNo;

	public SCTDescription() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SCTDescription(String conceptID, String term) {
		this.conceptID = conceptID;
		this.term = term;
	}

	public static SCTDescription getSnomedCTOBJ(List<Object[]> resultSet) {
		SCTDescription sCTDescription = null;
		if (resultSet != null && resultSet.size() > 0) {
			int length = resultSet.size();
			for (int i = 0; i < resultSet.size(); i++) {
				Object[] objArr = resultSet.get(i);
				if (i == length - 1) {
					sCTDescription = new SCTDescription((String) objArr[0], (String) objArr[1]);
				} else {
					if (((String) objArr[2]).equalsIgnoreCase("900000000000448009")) {
						sCTDescription = new SCTDescription((String) objArr[0], (String) objArr[1]);
						break;
					} else {
						continue;
					}
				}
			}
		}
		return sCTDescription;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getCaseSignificanceID() {
		return caseSignificanceID;
	}

	public void setCaseSignificanceID(String caseSignificanceID) {
		this.caseSignificanceID = caseSignificanceID;
	}

	public Long getSctDesID() {
		return sctDesID;
	}

	public void setSctDesID(Long sctDesID) {
		this.sctDesID = sctDesID;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getConceptID() {
		return conceptID;
	}

	public void setConceptID(String conceptID) {
		this.conceptID = conceptID;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

}
