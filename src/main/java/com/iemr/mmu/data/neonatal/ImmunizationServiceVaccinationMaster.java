/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.neonatal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
@Entity
@Table(name = "m_immunizationservicevaccination")
public class ImmunizationServiceVaccinationMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "VaccinationID")
	private Integer vaccinationID;

	// @Expose
	@Column(name = "Currentimmunizationserviceid")
	private Integer currentImmunizationServiceId;

	@Expose
	@Column(name = "VaccineName")
	private String vaccineName;
	
	@Expose
	@Column(name = "VisitCategoryID")
	private Integer visitCategoryID;

	@Column(name = "Deleted")
	private Boolean deleted;
}
