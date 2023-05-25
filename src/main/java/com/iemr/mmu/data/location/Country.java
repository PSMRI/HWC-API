package com.iemr.mmu.data.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "m_country")
public class Country {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CountryID")
	@Expose
	private Integer countryID;
	@SQLInjectionSafe
	@Column(name = "CountryName")
	@Expose

	private String countryName;
	// @Column(name = "CountryCode")
//	@Expose
//	private String countryCode;
//	@Column(name = "Continent")
//	@Expose
//	private String continent;
	@Column(name = "Deleted", insertable = false, updatable = true)
	@Expose
	private Boolean deleted;
//	@Column(name = "CreatedBy")
//	@Expose
//	private String createdBy;
//	@Column(name = "CreatedDate", insertable = false, updatable = false)
//	private Timestamp createdDate;
//	@Column(name = "ModifiedBy")
//	private String modifiedBy;
//	@Column(name = "LastModDate", insertable = false, updatable = false)
//	private Timestamp lastModDate;
//	@Transient
//	private OutputMapper outputMapper = new OutputMapper();

//	protected Country() {
//	}
//
//	public Country(Integer countryID, String countryName, String countryCode, String Continent) {
//		this.countryID = Integer.valueOf(countryID);
//		this.countryName = countryName;
//		this.countryCode = countryCode;
//		this.continent = Continent;
//	}
//
//	public Country(Integer countryID, String countryName) {
//		this.countryID = Integer.valueOf(countryID);
//		this.countryName = countryName;
//	}
//
//	@Override
//	public String toString() {
//		return outputMapper.gson().toJson(this);
//	}
}
