/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.masterdata.ncdscreening;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;


@Entity
@Table(name = "m_cervicallymphnodes")
public class CervicalLymph {

	@Id
	@GeneratedValue
	@Expose
	@Column(name = "id")
	private Integer id;
	
	@Expose
	@Column(name = "name")
	private String name;
	
	@Column(name = "Deleted")
	private Boolean deleted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "id=" + id + ", name=" + name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
}
