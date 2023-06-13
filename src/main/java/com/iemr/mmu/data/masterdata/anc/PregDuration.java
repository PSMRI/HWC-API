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
@Table(name = "m_pregduration")
public class PregDuration {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "PregDurationID")
	private Short pregDurationID;
	
	@Expose
	@Column(name = "DurationType")
	private String durationType;
	
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

	public PregDuration(Short pregDurationID, String durationType) {
		super();
		this.pregDurationID = pregDurationID;
		this.durationType = durationType;
	}
	
	public static ArrayList<PregDuration> getPregDurationValues(ArrayList<Object[]> resList) {
		ArrayList<PregDuration> resArray = new ArrayList<PregDuration>();
		for (Object[] obj : resList) {
			PregDuration cOBJ = new PregDuration((Short)obj[0], (String)obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
}