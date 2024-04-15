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
package com.iemr.hwc.data.masterdata.doctor;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_premalignantlesion")
public class PreMalignantLesion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "PreMalignantLesionID")
	private Integer preMalignantLesionID;
	
	@Expose
	@Column(name = "PreMalignantLesionType")
	private String preMalignantLesionType;
	
	@Expose
	@Column(name = "PreMalignantLesionDesc")
	private String preMalignantLesionDesc;
	
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;
	
	public PreMalignantLesion(Integer preMalignantLesionID, String preMalignantLesionType) {
		super();
		this.preMalignantLesionID = preMalignantLesionID;
		this.preMalignantLesionType = preMalignantLesionType;
	}
	
	public static ArrayList<PreMalignantLesion> getPreMalignantLesionMasterData(ArrayList<Object[]> resList) {
		ArrayList<PreMalignantLesion> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			PreMalignantLesion cOBJ = new PreMalignantLesion((Integer) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
}
