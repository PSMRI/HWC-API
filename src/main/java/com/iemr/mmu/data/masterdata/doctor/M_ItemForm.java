package com.iemr.mmu.data.masterdata.doctor;



import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.utils.mapper.OutputMapper;




@Entity
@Table(name = "m_itemform")

public class M_ItemForm {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "ItemFormID")
	private Integer itemFormID;

	@Expose
	@Column(name = "ItemFormName")
	private String itemForm;

	@Expose
	@Column(name = "ItemFormDesc")
	private String itemFormDesc;
	

	@Expose
	@Column(name = "ItemFormCode")
	private String itemFormCode;
	
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = false)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = false)
	private Character processed;

	@Expose
	@Column(name = "CreatedBy")
	private String CreatedBy;

	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Date CreatedDate;

	@Expose
	@Column(name = "ModifiedBy")
	private String ModifiedBy;

	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Date LastModDate;


//	@Transient
//	private OutputMapper outputMapper = new OutputMapper();
//
//	@Override
//	public String toString() {
//		return outputMapper.gson().toJson(this);
//	}
}
