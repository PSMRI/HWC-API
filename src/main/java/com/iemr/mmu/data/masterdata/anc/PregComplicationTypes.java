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
@Table(name = "m_pregcomplication")
public class PregComplicationTypes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "PregComplicationID")
	private Integer pregComplicationID;
	
	@Expose
	@Column(name = "PregComplicationType")
	private String pregComplicationType;
	
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

	public PregComplicationTypes(Integer pregComplicationID, String pregComplicationType) {
		super();
		this.pregComplicationID = pregComplicationID;
		this.pregComplicationType = pregComplicationType;
	}
	
	public static ArrayList<PregComplicationTypes> getPregComplicationTypes(ArrayList<Object[]> resList) {
		ArrayList<PregComplicationTypes> resArray = new ArrayList<PregComplicationTypes>();
		for (Object[] obj : resList) {
			PregComplicationTypes cOBJ = new PregComplicationTypes((Integer)obj[0], (String)obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
}
