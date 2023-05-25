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
@Table(name = "m_postpartumcomplication")
public class PostpartumComplicationTypes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "PostpartumComplicationID")
	private Short postpartumComplicationID;
	
	@Expose
	@Column(name = "PostpartumComplicationType")
	private String postpartumComplicationType;
	
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

	public PostpartumComplicationTypes(Short postpartumComplicationID, String postpartumComplicationType) {
		super();
		this.postpartumComplicationID = postpartumComplicationID;
		this.postpartumComplicationType = postpartumComplicationType;
	}
	
	public static ArrayList<PostpartumComplicationTypes> getPostpartumComplicationTypes(ArrayList<Object[]> resList) {
		ArrayList<PostpartumComplicationTypes> resArray = new ArrayList<PostpartumComplicationTypes>();
		for (Object[] obj : resList) {
			PostpartumComplicationTypes cOBJ = new PostpartumComplicationTypes((Short)obj[0], (String)obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
}
