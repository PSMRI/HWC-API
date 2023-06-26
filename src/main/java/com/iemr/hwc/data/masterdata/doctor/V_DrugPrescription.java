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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "v_drugforprescription")
public class V_DrugPrescription {
	@Id
	@Expose
	@Column(name = "id")
	private Integer id;
	@Expose
	@Column(name = "itemID")
	private Integer itemID;
	@Expose
	@Column(name = "itemName")
	private String itemName;
	@Expose
	@Column(name = "Strength")
	private String strength;
	@Expose
	@Column(name = "unitOfMeasurement")
	private String unitOfMeasurement;
	@Expose
	@Column(name = "quantityInHand")
	private Long quantityInHand;
	@Expose
	@Column(name = "itemFormID")
	private Integer itemFormID;
	@Expose
	@Column(name = "routeID")
	private Integer routeID;
	@Expose
	@Column(name = "facilityID")
	private Integer facilityID;
	
	@Expose
	@Column(name = "Sctcode")
	private String sctCode;
	
	@Expose
	@Column(name = "SctTerm")
	private String sctTerm;
	
	@Expose
	@Column(name="isedl")
	private Boolean isEDL;

	public V_DrugPrescription() {
	}

	public V_DrugPrescription(Integer id, Integer itemID, String itemName, String strength, String unitOfMeasurement,
			Long quantityInHand, Integer itemFormID, Integer routeID, Integer facilityID) {
		super();
		this.id = id;
		this.itemID = itemID;
		this.itemName = itemName;
		this.strength = strength;
		this.unitOfMeasurement = unitOfMeasurement;
		this.quantityInHand = quantityInHand;
		this.itemFormID = itemFormID;
		this.routeID = routeID;
		this.facilityID = facilityID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemID() {
		return itemID;
	}

	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public Long getQuantityInHand() {
		return quantityInHand;
	}

	public void setQuantityInHand(Long quantityInHand) {
		this.quantityInHand = quantityInHand;
	}

	public Integer getItemFormID() {
		return itemFormID;
	}

	public void setItemFormID(Integer itemFormID) {
		this.itemFormID = itemFormID;
	}

	public Integer getRouteID() {
		return routeID;
	}

	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public Integer getFacilityID() {
		return facilityID;
	}

	public void setFacilityID(Integer facilityID) {
		this.facilityID = facilityID;
	}

	public String getSctCode() {
		return sctCode;
	}

	public void setSctCode(String sctCode) {
		this.sctCode = sctCode;
	}

	public String getSctTerm() {
		return sctTerm;
	}

	public void setSctTerm(String sctTerm) {
		this.sctTerm = sctTerm;
	}

	public Boolean getIsEDL() {
		return isEDL;
	}

	public void setIsEDL(Boolean isEDL) {
		this.isEDL = isEDL;
	}
	
	

}
