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
@Table(name = "m_covidcontacthistory")
public class CovidContactHistoryMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "CovidcontacthistoryID", insertable = false, updatable = false)
	private Integer covidcontacthistoryID;
	@Expose
	@Column(name = "ContactHistory")
	private String contactHistory;
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = false)
	private Boolean deleted;

}
