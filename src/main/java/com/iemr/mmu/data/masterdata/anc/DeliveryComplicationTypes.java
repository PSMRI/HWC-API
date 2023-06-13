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
@Table(name = "m_deliverycomplication")
public class DeliveryComplicationTypes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "DeliveryComplicationID")
	private Short deliveryComplicationID;
	
	@Expose
	@Column(name = "DeliveryComplicationType")
	private String deliveryComplicationType;
	
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

	public DeliveryComplicationTypes(Short deliveryComplicationID, String deliveryComplicationType) {
		super();
		this.deliveryComplicationID = deliveryComplicationID;
		this.deliveryComplicationType = deliveryComplicationType;
	}
	
	public static ArrayList<DeliveryComplicationTypes> getDeliveryComplicationTypes(ArrayList<Object[]> resList) {
		ArrayList<DeliveryComplicationTypes> resArray = new ArrayList<DeliveryComplicationTypes>();
		for (Object[] obj : resList) {
			DeliveryComplicationTypes cOBJ = new DeliveryComplicationTypes((Short)obj[0], (String)obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
}
