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
package com.iemr.hwc.data.adolescent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
@Entity
@Table(name = "m_oralvitaminnumberofdose")
public class OralVitaminNoOfDoseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "id", insertable = false, updatable = false)
	private Short id;

	@Expose
	@Column(name = "Name", insertable = false, updatable = false)
	private String name;

	// @Expose
	@Column(name = "Deleted", insertable = false, updatable = false)
	private Boolean deleted;

}
