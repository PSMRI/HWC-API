package com.iemr.mmu.data.masterdata.anc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.data.anc.FemaleObstetricHistory;

@Entity
@Table(name = "m_servicefacility")
public class ServiceFacilityMaster {

	@Id
	@Expose
	@Column(name = "ServiceFacilityID")
	private Integer serviceFacilityID;
	@Expose
	@Column(name = "FacilityName")
	private String facilityName;
	
	@Column(name = "Deleted")
	private Boolean deleted;

	public Integer getServiceFacilityID() {
		return serviceFacilityID;
	}

	public void setServiceFacilityID(Integer serviceFacilityID) {
		this.serviceFacilityID = serviceFacilityID;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
