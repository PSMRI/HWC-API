package com.iemr.mmu.data.anc;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_Sys_MusculoskeletalSystem")
public class SysMusculoskeletalSystemExamination {

	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ID")
	private Long ID;
	
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
	@Column(name = "VisitCode")
	private Long visitCode;
	
	@Expose
	@Column(name = "Joint_TypeOfJoint")
	private String joint_TypeOfJoint;

	@Expose
	@Column(name = "Joint_Laterality")
	private String joint_Laterality;

	@Expose
	@Column(name = "Joint_Abnormality")
	private String joint_Abnormality;

	@Expose
	@Column(name = "UpperLimb_Laterality")
	private String upperLimb_Laterality;

	@Expose
	@Column(name = "UpperLimb_Abnormality")
	private String upperLimb_Abnormality;

	@Expose
	@Column(name = "LowerLimb_Laterality")
	private String lowerLimb_Laterality;

	@Expose
	@Column(name = "LowerLimb_Abnormality")
	private String lowerLimb_Abnormality;

	@Expose
	@Column(name = "ChestWall")
	private String chestWall;

	@Expose
	@Column(name = "Spine")
	private String spine;

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
	@Column(name = "SyncedBy")
	private String syncedBy;
	
	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;
	
	@Expose
	@Column(name = "ReservedForChange")
	private String reservedForChange;
	
	public SysMusculoskeletalSystemExamination() {
	}

	public SysMusculoskeletalSystemExamination(Long iD, Long beneficiaryRegID, Long benVisitID,
			String joint_TypeOfJoint, String joint_Laterality, String joint_Abnormality, String upperLimb_Laterality,
			String upperLimb_Abnormality, String lowerLimb_Laterality, String lowerLimb_Abnormality, String chestWall,
			String spine, Boolean deleted, String processed, String createdBy, Timestamp createdDate, String modifiedBy,
			Timestamp lastModDate) {
		super();
		ID = iD;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.joint_TypeOfJoint = joint_TypeOfJoint;
		this.joint_Laterality = joint_Laterality;
		this.joint_Abnormality = joint_Abnormality;
		this.upperLimb_Laterality = upperLimb_Laterality;
		this.upperLimb_Abnormality = upperLimb_Abnormality;
		this.lowerLimb_Laterality = lowerLimb_Laterality;
		this.lowerLimb_Abnormality = lowerLimb_Abnormality;
		this.chestWall = chestWall;
		this.spine = spine;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
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

	public String getJoint_TypeOfJoint() {
		return joint_TypeOfJoint;
	}

	public void setJoint_TypeOfJoint(String joint_TypeOfJoint) {
		this.joint_TypeOfJoint = joint_TypeOfJoint;
	}

	public String getJoint_Laterality() {
		return joint_Laterality;
	}

	public void setJoint_Laterality(String joint_Laterality) {
		this.joint_Laterality = joint_Laterality;
	}

	public String getJoint_Abnormality() {
		return joint_Abnormality;
	}

	public void setJoint_Abnormality(String joint_Abnormality) {
		this.joint_Abnormality = joint_Abnormality;
	}

	public String getUpperLimb_Laterality() {
		return upperLimb_Laterality;
	}

	public void setUpperLimb_Laterality(String upperLimb_Laterality) {
		this.upperLimb_Laterality = upperLimb_Laterality;
	}

	public String getUpperLimb_Abnormality() {
		return upperLimb_Abnormality;
	}

	public void setUpperLimb_Abnormality(String upperLimb_Abnormality) {
		this.upperLimb_Abnormality = upperLimb_Abnormality;
	}

	public String getLowerLimb_Laterality() {
		return lowerLimb_Laterality;
	}

	public void setLowerLimb_Laterality(String lowerLimb_Laterality) {
		this.lowerLimb_Laterality = lowerLimb_Laterality;
	}

	public String getLowerLimb_Abnormality() {
		return lowerLimb_Abnormality;
	}

	public void setLowerLimb_Abnormality(String lowerLimb_Abnormality) {
		this.lowerLimb_Abnormality = lowerLimb_Abnormality;
	}

	public String getChestWall() {
		return chestWall;
	}

	public void setChestWall(String chestWall) {
		this.chestWall = chestWall;
	}

	public String getSpine() {
		return spine;
	}

	public void setSpine(String spine) {
		this.spine = spine;
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

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
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

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public String getSyncedBy() {
		return syncedBy;
	}

	public void setSyncedBy(String syncedBy) {
		this.syncedBy = syncedBy;
	}

	public Timestamp getSyncedDate() {
		return syncedDate;
	}

	public void setSyncedDate(Timestamp syncedDate) {
		this.syncedDate = syncedDate;
	}

	public String getReservedForChange() {
		return reservedForChange;
	}

	public void setReservedForChange(String reservedForChange) {
		this.reservedForChange = reservedForChange;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

}
