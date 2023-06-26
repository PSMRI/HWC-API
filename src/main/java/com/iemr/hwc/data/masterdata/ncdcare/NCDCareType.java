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
package com.iemr.hwc.data.masterdata.ncdcare;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.data.masterdata.ncdscreening.NCDScreeningCondition;

@Entity
@Table(name = "m_ncdcaretype")
public class NCDCareType
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "NCDCareTypeID")
	private Integer ncdCareTypeID;
			
	@Expose
	@Column(name = "NCDCareType")
	private String  ncdCareType;
					
	@Expose
	@Column(name = "NCDCareTypeDesc")
	private String ncdCareTypeDesc;
	
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

	public NCDCareType(Integer ncdCareTypeID, String ncdCareType)
	{
		super();
		this.ncdCareTypeID = ncdCareTypeID;
		this.ncdCareType = ncdCareType;
	}
	
	public static ArrayList<NCDCareType> getNCDCareTypes(ArrayList<Object[]> resList) {
		ArrayList<NCDCareType> resArray = new ArrayList<NCDCareType>();
		for (Object[] obj : resList) {
			NCDCareType cOBJ = new NCDCareType((Integer)obj[0], (String)obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	} 
}
