/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.login;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_vanservicepointmap")
public class VanServicepointMapping {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "VanServicePointMapID")
	private Integer VanServicePointMapID;
	@Expose
	@Column(name = "VanID")
	private Integer vanID;
	@Expose
	@Column(name = "ServicePointID")
	private Integer servicePointID;
	@Expose
	@Column(name = "VanSession")
	private Short vanSession;
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	@ManyToOne
	@JoinColumn(name = "ServicePointID", insertable = false, updatable = false)
	public MasterServicePoint masterServicePoint;
}
