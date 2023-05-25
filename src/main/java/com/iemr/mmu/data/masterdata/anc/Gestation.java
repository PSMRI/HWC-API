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
@Table(name = "m_gestation")
public class Gestation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "GestationID")
	private Short gestationID;

	@Expose
	@Column(name = "Name")
	private String name;

	@Expose
	@Column(name = "GestationDesc")
	private String gestationDesc;

	// @Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	// @Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;

	// @Expose
	@Column(name = "CreatedBy")
	private String createdBy;

//	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	// @Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;

//	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	public Gestation() {
	}

	public Gestation(Short gestationID, String name, String gestationDesc) {
		super();
		this.gestationID = gestationID;
		this.name = name;
		this.gestationDesc = gestationDesc;
	}

	public static ArrayList<Gestation> getGestations(ArrayList<Object[]> resList) {
		ArrayList<Gestation> resArray = new ArrayList<Gestation>();
		for (Object[] obj : resList) {
			Gestation cOBJ = new Gestation((Short) obj[0], (String) obj[1], (String) obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

}
