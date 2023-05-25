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
@Table(name = "m_postnatalcomplication")
public class PostNatalComplication {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "PostNatalComplicationID")
	private Short postNatalComplicationID;
	
	@Expose
	@Column(name = "PostNatalComplication")
	private String postNatalComplication;
	
	@Expose
	@Column(name = "PostNatalCompDesc")
	private String postNatalCompDesc;
	
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

	public PostNatalComplication(Short postNatalComplicationID, String postNatalComplication) {
		super();
		this.postNatalComplicationID = postNatalComplicationID;
		this.postNatalComplication = postNatalComplication;
	}
	
	public static ArrayList<PostNatalComplication> getPostNatalComplications(ArrayList<Object[]> resList) {
		ArrayList<PostNatalComplication> resArray = new ArrayList<PostNatalComplication>();
		for (Object[] obj : resList) {
			PostNatalComplication cOBJ = new PostNatalComplication((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
}
