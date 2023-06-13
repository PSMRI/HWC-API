/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.neonatal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_immunizationserviceroute")
public class ImmunizationServiceRouteMaster {

	@Id
	@GeneratedValue
	@Expose
	@Column(name = "Routeid")
	private Integer routeID;

	// @Expose
	@Column(name = "VaccinationID")
	private Integer vaccinationID;

	// @Expose
	@Column(name = "VaccinationName")
	private String vaccinationName;

	@Expose
	@Column(name = "Route")
	private String route;

	// @Expose
	@Column(name = "Sctcode")
	private String sctcode;

	// @Expose
	@Column(name = "SctTerm")
	private String sctTerm;

	@Column(name = "Deleted")
	private Boolean deleted;

}
