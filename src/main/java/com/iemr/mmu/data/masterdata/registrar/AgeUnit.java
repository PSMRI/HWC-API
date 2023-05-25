package com.iemr.mmu.data.masterdata.registrar;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_ageunits")
public class AgeUnit {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "Ageunitid")
	private Integer id;
	@Expose
	@Column(name = "AgeUnit")
	private String name;

	@Column(name = "Deleted")
	private Boolean deleted;

	@Column(name = "Processed")
	private Character processed;

	@Column(name = "CreatedBy")
	private String createdBy;

	@Column(name = "CreatedDate")
	private Timestamp createdDate;

	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Column(name = "LastModDate")
	private Timestamp lastModDate;

}
