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
@Table(name = "m_pregoutcome")
public class PregOutcome {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "PregOutcomeID")
	private Short pregOutcomeID;

	@Expose
	@Column(name = "PregOutcome")
	private String pregOutcome;

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

	public PregOutcome(Short pregOutcomeID, String pregOutcome) {
		super();
		this.pregOutcomeID = pregOutcomeID;
		this.pregOutcome = pregOutcome;
	}

	public static ArrayList<PregOutcome> getPregOutcomes(ArrayList<Object[]> resList) {
		ArrayList<PregOutcome> resArray = new ArrayList<PregOutcome>();
		for (Object[] obj : resList) {
			PregOutcome cOBJ = new PregOutcome((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

}
