/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.covid19;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_covidsymptoms")
public class CovidSymptomsMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "CovidsymptomID", insertable = false, updatable = false)
	private Integer covidSymptomID;
	@Expose
	@Column(name = "Symptoms")
	private String symptoms;
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = false)
	private Boolean deleted;

}
