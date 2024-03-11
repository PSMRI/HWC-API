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
package com.iemr.hwc.data.masterdata.anc;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.data.anc.FemaleObstetricHistory;

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
