/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.uptsu;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "m_104actionmaster")
public class M_104ActionMaster {
	
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "id", insertable = false)
	private Integer id;
	
	@Expose
	@Column(name = "name")
	private String name;
	
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;
	
	@Expose
	@Column(name = "Processed", insertable = false)
	private String processed;
	
	@Expose
	@Column(name = "CreatedBy", updatable = false)
	private String createdBy;
	
	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;
	
	@Expose
	@Column(name = "ModifiedBy", insertable = false, updatable = true)
	private String modifiedBy;
	
	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	

}
