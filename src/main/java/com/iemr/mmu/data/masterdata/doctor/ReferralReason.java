package com.iemr.mmu.data.masterdata.doctor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_referralreason")
public class ReferralReason {
	
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "id")
	private Integer id;
	
	@Expose
	@Column(name = "name")
	private String name;
	
	@Expose
	@Column(name = "VisitCategoryId")
	private Integer visitCategoryId;
	
	

	@Column(name = "Deleted")
	private Boolean deleted;


}
