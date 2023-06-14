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
package com.iemr.mmu.data.ncdScreening;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_idrsDetails")
public class IDRSData {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "Idrsid")
	private Long id;

	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "BenVisitID")
	private Long benVisitID;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	@Expose
	@Column(name = "IDRSQuestionID")
	private Integer idrsQuestionID;
	@Expose
	@Column(name = "IDRSScore")
	private Integer idrsScore;
	@Expose
	@Column(name = "Question")
	private String question;
	@Expose
	@Column(name = "Answer")
	private String answer;
	@Expose
	@Column(name = "SuspectedDiseases")
	private String suspectedDisease;

	@Expose
	@Column(name = "ConfirmedDiseases")
	private String confirmedDisease;

	@Transient
	private IDRSData[] questionArray;
	@Transient
	private String[] suspectArray;

	@Transient
	private String[] confirmArray;

//    @OneToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "visitCode",insertable = false, updatable = false)
//	@Expose
//	private BeneficiaryFlowStatus beneficiaryFlowStatus;
	@Expose
	@Column(name = "DiseaseQuestionType")
	private String diseaseQuestionType;

	@Expose
	@Column(name = "VisitCode")
	private Long visitCode;
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;
	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed = "N";
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
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;

	@Expose
	@Column(name = "VehicalNo")
	private String vehicalNo;

	@Expose
	@Column(name = "vanID")
	private Integer vanID;

	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;
	@Expose
	@Column(name = "isDiabetic")
	private Boolean isDiabetic;
	@Transient
	@Expose
	private List<Map<String, Object>> idrsDetails;
	@Transient
	@Expose
	private List<Map<String, Object>> suspectDetails;

	public String getConfirmedDisease() {
		return confirmedDisease;
	}

	public void setConfirmedDisease(String confirmedDisease) {
		this.confirmedDisease = confirmedDisease;
	}

	public String[] getConfirmArray() {
		return confirmArray;
	}

	public void setConfirmArray(String[] confirmArray) {
		this.confirmArray = confirmArray;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsDiabetic() {
		return isDiabetic;
	}

	public void setIsDiabetic(Boolean isDiabetic) {
		this.isDiabetic = isDiabetic;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Long getBenVisitID() {
		return benVisitID;
	}

	public void setBenVisitID(Long benVisitID) {
		this.benVisitID = benVisitID;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Integer getIdrsQuestionID() {
		return idrsQuestionID;
	}

	public void setIdrsQuestionID(Integer idrsQuestionID) {
		this.idrsQuestionID = idrsQuestionID;
	}

	public Integer getIdrsScore() {
		return idrsScore;
	}

	public void setIdrsScore(Integer idrsScore) {
		this.idrsScore = idrsScore;
	}

	public String getQuestion() {
		return question;
	}

	public String getSuspectedDisease() {
		return suspectedDisease;
	}

	public void setSuspectedDisease(String suspectedDisease) {
		this.suspectedDisease = suspectedDisease;
	}

	public String getAnswer() {
		return answer;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getDiseaseQuestionType() {
		return diseaseQuestionType;
	}

	public void setDiseaseQuestionType(String diseaseQuestionType) {
		this.diseaseQuestionType = diseaseQuestionType;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public IDRSData[] getQuestionArray() {
		return questionArray;
	}

	public void setQuestionArray(IDRSData[] questionArray) {
		this.questionArray = questionArray;
	}

	public String[] getSuspectArray() {
		return suspectArray;
	}

	public void setSuspectArray(String[] suspectArray) {
		this.suspectArray = suspectArray;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

	public Long getVanSerialNo() {
		return vanSerialNo;
	}

	public void setVanSerialNo(Long vanSerialNo) {
		this.vanSerialNo = vanSerialNo;
	}

	public String getVehicalNo() {
		return vehicalNo;
	}

	public void setVehicalNo(String vehicalNo) {
		this.vehicalNo = vehicalNo;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public List<Map<String, Object>> getIdrsDetails() {
		return idrsDetails;
	}

	public void setIdrsDetails(List<Map<String, Object>> idrsDetails) {
		this.idrsDetails = idrsDetails;
	}

	public List<Map<String, Object>> getSuspectDetails() {
		return suspectDetails;
	}

	public void setSuspectDetails(List<Map<String, Object>> suspectDetails) {
		this.suspectDetails = suspectDetails;
	}

	public IDRSData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IDRSData(Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID, Integer idrsScore,
			String suspectedDisease, String confirmedDisease, Long visitCode) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.idrsScore = idrsScore;
		this.suspectedDisease = suspectedDisease;
		this.confirmedDisease = confirmedDisease;
		this.visitCode = visitCode;

	}

	public IDRSData(Long ID, Integer idrsQuestionID, String question, String answer, String diseaseQuestionType) {
		super();
		this.id = ID;
		this.idrsQuestionID = idrsQuestionID;
		this.question = question;
		this.answer = answer;
		this.diseaseQuestionType = diseaseQuestionType;

	}

	public IDRSData(Long visitCode, Timestamp createdDate, String suspected) {
		super();
		this.visitCode = visitCode;
		this.createdDate = createdDate;
		this.suspectedDisease = suspected;
	}

	public IDRSData(Long visitCode, Timestamp createdDate, String question, String answer, Long idrsID,
			Integer idrsQuestionID, String diseaseQuestionType) {
		super();
		this.visitCode = visitCode;
		this.createdDate = createdDate;
		this.question = question;
		this.answer = answer;
		this.id = idrsID;
		this.idrsQuestionID = idrsQuestionID;
		this.diseaseQuestionType = diseaseQuestionType;
	}

	public static IDRSData getIDRSData(ArrayList<Object[]> idrsHistory) {
		IDRSData benIdrsHistory = null;
		if (null != idrsHistory && idrsHistory.size() > 0) {
			Object[] obj1 = idrsHistory.get(0);

			benIdrsHistory = new IDRSData((Long) obj1[1], (Long) obj1[2], (Integer) obj1[3], (Integer) obj1[5],
					(String) obj1[8], (String) obj1[11], (Long) obj1[9]);

			List<Map<String, Object>> idrsDetails = new ArrayList<Map<String, Object>>();

			for (Object[] obj : idrsHistory) {
				IDRSData idDetails = new IDRSData((Long) obj[0], (Integer) obj[4], (String) obj[6], (String) obj[7],
						(String) obj[10]);

				Map<String, Object> idrsData = new HashMap<String, Object>();
				idrsData.put("ID", idDetails.getId());
				idrsData.put("idrsQuestionId", idDetails.getIdrsQuestionID());

				idrsData.put("question", idDetails.getQuestion());
				idrsData.put("answer", idDetails.getAnswer());
				idrsData.put("suspectDisease", idDetails.getDiseaseQuestionType());

				idrsDetails.add(idrsData);
			}

			benIdrsHistory.setIdrsDetails(idrsDetails);
		}
		return benIdrsHistory;
	}

}
