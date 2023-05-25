package com.iemr.mmu.data.masterdata.ncdscreening;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_screeningcondition")
public class ScreeningDiseases {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "id")
	private Integer id;
	@Expose
	@Column(name = "name")
	private String name;
	
	@Column(name = "Deleted")
	private Boolean deleted;
	
	@Column(name="Processed")
	private Character processed;
	
    @Column(name="CreatedBy")
	private String createdBy;
	
    @Column(name="CreatedDate")
	private Timestamp createdDate;
	
    @Column(name="ModifiedBy") 
	private String modifiedBy;
	
    @Column(name="LastModDate") 
	private Timestamp lastModDate;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
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
	
	
	
	
	
	

}
