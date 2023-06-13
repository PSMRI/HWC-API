/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.neonatal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_immunizationservicedose")
public class ImmunizationServiceDoseMaster {
	
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "Doseid")
	private Integer doseID;

	// @Expose
	@Column(name = "VaccinationID")
	private Integer vaccinationID;

	//@Expose
//	@Column(name = "VaccinationName")
//	private String vaccinationName;
	
	@Expose
	@Column(name = "Dose")
	private String dose;


	@Column(name = "Deleted")
	private Boolean deleted;

}
