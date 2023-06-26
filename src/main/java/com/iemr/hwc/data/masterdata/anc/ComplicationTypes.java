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

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.data.anc.FemaleObstetricHistory;

@Entity
@Table(name = "m_complication")
public class ComplicationTypes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "ComplicationID")
	private Short complicationID;

	@Expose
	@Column(name = "ComplicationType")
	private String complicationType;

	@Expose
	@Column(name = "ComplicationValue")
	private String complicationValue;

	@Expose
	@Column(name = "ComplicationDesc")
	private String complicationDesc;

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
	@Transient
	private Short pregComplicationID;

	@Expose
	@Transient
	private String pregComplicationType;

	@Expose
	@Transient
	private Short deliveryComplicationID;

	@Expose
	@Transient
	private String deliveryComplicationType;

	@Expose
	@Transient
	private Short postpartumComplicationID;

	@Expose
	@Transient
	private String postpartumComplicationType;

	public ComplicationTypes(Short complicationID, String complicationValue) {
		super();
		this.complicationID = complicationID;
		this.complicationValue = complicationValue;
	}

	public ComplicationTypes(Short complicationID, String complicationValue, int a) {
		super();
		this.pregComplicationID = complicationID;
		this.pregComplicationType = complicationValue;
	}

	public ComplicationTypes(Short complicationID, String complicationValue, int a, int b) {
		super();
		this.deliveryComplicationID = complicationID;
		this.deliveryComplicationType = complicationValue;
	}

	public ComplicationTypes(Short complicationID, String complicationValue, int a, int b, int c) {
		super();
		this.postpartumComplicationID = complicationID;
		this.postpartumComplicationType = complicationValue;
	}

	public static ArrayList<ComplicationTypes> getComplicationTypes(ArrayList<Object[]> resList, int masterType) {
		ArrayList<ComplicationTypes> resArray = new ArrayList<ComplicationTypes>();
		if (masterType == 1) {
			for (Object[] obj : resList) {
				ComplicationTypes cOBJ = new ComplicationTypes((Short) obj[0], (String) obj[1], 0);
				resArray.add(cOBJ);
			}
		} else if (masterType == 2) {
			for (Object[] obj : resList) {
				ComplicationTypes cOBJ = new ComplicationTypes((Short) obj[0], (String) obj[1], 0, 0);
				resArray.add(cOBJ);
			}
		} else if (masterType == 3) {
			for (Object[] obj : resList) {
				ComplicationTypes cOBJ = new ComplicationTypes((Short) obj[0], (String) obj[1], 0, 0, 0);
				resArray.add(cOBJ);
			}
		} else {
			for (Object[] obj : resList) {
				ComplicationTypes cOBJ = new ComplicationTypes((Short) obj[0], (String) obj[1]);
				resArray.add(cOBJ);
			}
		}
		return resArray;
	}
}
