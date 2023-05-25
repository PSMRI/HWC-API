package com.iemr.mmu.data.masterdata.pnc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_deliveryconductedby")
public class DeliveryConductedByMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "id")
	private Integer deliveryConductedByID;

	@Expose
	@Column(name = "Name")
	private String deliveryConductedBy;

	//@Expose
	@Column(name = "Deleted", insertable = false, updatable = false)
	private Boolean deleted;

	public Integer getDeliveryConductedByID() {
		return deliveryConductedByID;
	}

	public void setDeliveryConductedByID(Integer deliveryConductedByID) {
		this.deliveryConductedByID = deliveryConductedByID;
	}

	public String getDeliveryConductedBy() {
		return deliveryConductedBy;
	}

	public void setDeliveryConductedBy(String deliveryConductedBy) {
		this.deliveryConductedBy = deliveryConductedBy;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
