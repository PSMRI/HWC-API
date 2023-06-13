/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.masterdata.anc;

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
import com.iemr.mmu.data.anc.FemaleObstetricHistory;

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
