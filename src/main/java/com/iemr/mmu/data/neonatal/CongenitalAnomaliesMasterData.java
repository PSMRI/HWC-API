package com.iemr.mmu.data.neonatal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
@Entity
@Table(name = "m_congenitalanomalies")
public class CongenitalAnomaliesMasterData {
	
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "CongenitalAnomaliesID")
	private Integer id;
	
	@Expose
	@Column(name = "CongenitalAnomalies")
	private String name;
	
	@Expose
	@Column(name = "CongenitalAnomaliesDesc")
	private String congenitalAnomaliesDesc;
	
	@Expose
	@Column(name = "ServiceID")
	private Short serviceID;
	
	
	@Column(name = "Deleted")
	private Boolean deleted;
	

}
