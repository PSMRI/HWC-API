/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.masterdata.doctor;

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
