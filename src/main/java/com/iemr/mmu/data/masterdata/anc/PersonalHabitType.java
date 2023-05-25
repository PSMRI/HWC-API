package com.iemr.mmu.data.masterdata.anc;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_personalhabittype")
public class PersonalHabitType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "PersonalHabitTypeID")
	private Short personalHabitTypeID;
	
	@Expose
	@Column(name = "HabitType")
	private String habitType;
	
	@Expose
	@Column(name = "HabitValue")
	private String habitValue;
	
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
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
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	public PersonalHabitType(Short personalHabitTypeID, String habitType, String habitValue) {
		super();
		this.personalHabitTypeID = personalHabitTypeID;
		this.habitType = habitType;
		this.habitValue = habitValue;
	}
	
	public static ArrayList<PersonalHabitType> getPersonalHabitTypeMasterData(ArrayList<Object[]> resList) {
		ArrayList<PersonalHabitType> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			PersonalHabitType cOBJ = new PersonalHabitType((Short) obj[0], (String) obj[1], (String) obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
	
	public Short getPersonalHabitTypeID() {
		return personalHabitTypeID;
	}

	public void setPersonalHabitTypeID(Short personalHabitTypeID) {
		this.personalHabitTypeID = personalHabitTypeID;
	}

	public String getHabitType() {
		return habitType;
	}

	public void setHabitType(String habitType) {
		this.habitType = habitType;
	}

	public String getHabitValue() {
		return habitValue;
	}

	public void setHabitValue(String habitValue) {
		this.habitValue = habitValue;
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
	
}
