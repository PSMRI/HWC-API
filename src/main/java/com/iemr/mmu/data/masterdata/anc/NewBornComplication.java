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
@Table(name = "m_newborncomplication")
public class NewBornComplication {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "NewBornComplicationID")
	private Short newBornComplicationID;
	
	@Expose
	@Column(name = "NewBornComplication")
	private String newBornComplication;
	
	@Expose
	@Column(name = "NewBornComplicationDesc")
	private String newBornComplicationDesc;
	
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

	public NewBornComplication(Short newBornComplicationID, String newBornComplication) {
		super();
		this.newBornComplicationID = newBornComplicationID;
		this.newBornComplication = newBornComplication;
	}
	
	public static ArrayList<NewBornComplication> getNewBornComplications(ArrayList<Object[]> resList) {
		ArrayList<NewBornComplication> resArray = new ArrayList<NewBornComplication>();
		for (Object[] obj : resList) {
			NewBornComplication cOBJ = new NewBornComplication((Short)obj[0], (String)obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
	
}
