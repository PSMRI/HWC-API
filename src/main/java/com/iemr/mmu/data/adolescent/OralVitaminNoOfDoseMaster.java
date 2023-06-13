/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.adolescent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
@Entity
@Table(name = "m_oralvitaminnumberofdose")
public class OralVitaminNoOfDoseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "id", insertable = false, updatable = false)
	private Short id;

	@Expose
	@Column(name = "Name", insertable = false, updatable = false)
	private String name;

	// @Expose
	@Column(name = "Deleted", insertable = false, updatable = false)
	private Boolean deleted;

}
