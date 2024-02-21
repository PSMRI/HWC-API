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
package com.iemr.hwc.data.masterdata.registrar;

import java.sql.Timestamp;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_benrelationshiptype")
public class RelationshipMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "BenRelationshipID")
	private Integer benRelationshipID;
	@Expose
	@Column(name = "BenRelationshipType")
	private String benRelationshipType;
	@Expose
	@Column(name = "Gender")
	private String gender;
	
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
    
	
	
    
	

	
	
		
	
	

}
