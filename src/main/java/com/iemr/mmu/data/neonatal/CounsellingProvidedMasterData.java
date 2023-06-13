/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.neonatal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
@Entity
@Table(name = "m_counsellingprovided")
public class CounsellingProvidedMasterData {
	
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "id")
	private Integer id;
	
	@Expose
	@Column(name = "Name")
	private String name;
	
	@Expose
	@Column(name = "VisitCategoryID")
	private Integer visitCategoryID;
	
	@Column(name = "Deleted")
	private Boolean deleted;

}
