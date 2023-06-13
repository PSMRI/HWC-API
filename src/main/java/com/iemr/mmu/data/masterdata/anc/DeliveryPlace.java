/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.masterdata.anc;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_deliveryplace")
public class DeliveryPlace {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "DeliveryPlaceID")
	private Short deliveryPlaceID;

	@Expose
	@Column(name = "DeliveryPlace")
	private String deliveryPlace;

	// @Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	// @Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;

	// @Expose
	@Column(name = "CreatedBy")
	private String createdBy;

	// @Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	// @Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;

	// @Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	public DeliveryPlace() {
	}

	public DeliveryPlace(Short deliveryPlaceID, String deliveryPlace) {
		super();
		this.deliveryPlaceID = deliveryPlaceID;
		this.deliveryPlace = deliveryPlace;
	}

	public static ArrayList<DeliveryPlace> getDeliveryPlace(ArrayList<Object[]> resList) {
		ArrayList<DeliveryPlace> resArray = new ArrayList<DeliveryPlace>();
		for (Object[] obj : resList) {
			DeliveryPlace cOBJ = new DeliveryPlace((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

}
