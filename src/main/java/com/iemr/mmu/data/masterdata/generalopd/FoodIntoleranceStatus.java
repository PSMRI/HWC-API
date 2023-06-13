/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.masterdata.generalopd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_FoodIntoleranceStatus")
public class FoodIntoleranceStatus {
	
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ID")
	private Integer id;
	
	@Expose
	@Column(name = "IntoleranceStatus")
	private String intoleranceStatus;

	@Column(name = "Deleted")
	private Boolean deleted;

}
