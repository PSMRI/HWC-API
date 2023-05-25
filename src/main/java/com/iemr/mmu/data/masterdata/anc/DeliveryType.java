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
@Table(name = "m_deliverytype")
public class DeliveryType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "DeliveryTypeID")
	private Short deliveryTypeID;

	@Expose
	@Column(name = "DeliveryType")
	private String deliveryType;

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

	public DeliveryType() {
		super();

	}

	public DeliveryType(Short deliveryTypeID, String deliveryType) {
		super();
		this.deliveryTypeID = deliveryTypeID;
		this.deliveryType = deliveryType;
	}

	public static ArrayList<DeliveryType> getDeliveryType(ArrayList<Object[]> resList) {
		ArrayList<DeliveryType> resArray = new ArrayList<DeliveryType>();
		for (Object[] obj : resList) {
			DeliveryType cOBJ = new DeliveryType((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

}
