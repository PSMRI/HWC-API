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
@Table(name = "m_musculoskeletal")
public class Musculoskeletal {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "ID")
	private Short ID;
	
	@Expose
	@Column(name = "Type")
	private String type;
	
	@Expose
	@Column(name = "Value")
	private String value;
	
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

	public Musculoskeletal(Short iD, String type, String value) {
		super();
		ID = iD;
		this.type = type;
		this.value = value;
	}
	
	public static ArrayList<Musculoskeletal> getMusculoskeletals(ArrayList<Object[]> resList) {
		ArrayList<Musculoskeletal> resArray = new ArrayList<Musculoskeletal>();
		for (Object[] obj : resList) {
			Musculoskeletal cOBJ = new Musculoskeletal((Short)obj[0], (String)obj[1], (String)obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
}
