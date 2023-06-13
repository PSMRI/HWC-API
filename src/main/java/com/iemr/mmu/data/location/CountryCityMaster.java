/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_countrycity")
public class CountryCityMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CityID")
	@Expose
	private Integer cityID;

	@Column(name = "CityName")
	@Expose
	private String cityName;

	@Column(name = "CountryID")
	@Expose
	private Integer countryID;

	@Column(name = "Deleted", insertable = false, updatable = true)
	@Expose
	private Boolean deleted;
}
