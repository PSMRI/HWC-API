package com.iemr.mmu.data.masterdata.anc;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_fundalheight")
public class FundalHeight {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "FundalHeightID")
	private Short fundalHeightID;
	
	@Expose
	@Column(name = "FundalHeight")
	private String fundalHeight;
	
	@Expose
	@Column(name = "FundalHeightDesc")
	private String fundalHeightDesc;
	
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;

	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;

	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	public FundalHeight(Short fundalHeightID, String fundalHeight, String fundalHeightDesc) {
		super();
		this.fundalHeightID = fundalHeightID;
		this.fundalHeight = fundalHeight;
		this.fundalHeightDesc = fundalHeightDesc;
	}
	
	public static ArrayList<FundalHeight> getFundalHeights(ArrayList<Object[]> resList) {
		ArrayList<FundalHeight> resArray = new ArrayList<FundalHeight>();
		for (Object[] obj : resList) {
			FundalHeight cOBJ = new FundalHeight((Short)obj[0], (String)obj[1], (String)obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
}
