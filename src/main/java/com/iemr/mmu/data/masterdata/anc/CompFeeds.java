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
@Table(name = "m_compfeeds")
public class CompFeeds {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "FeedID")
	private Integer feedID;
	
	@Expose
	@Column(name = "Type")
	private String type;
	
	@Expose
	@Column(name = "Value")
	private String value;
	
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

	public CompFeeds(Integer feedID, String type, String value) {
		super();
		this.feedID = feedID;
		this.type = type;
		this.value = value;
	}
	
	public static ArrayList<CompFeeds> getCompFeeds(ArrayList<Object[]> resList) {
		ArrayList<CompFeeds> resArray = new ArrayList<CompFeeds>();
		for (Object[] obj : resList) {
			CompFeeds cOBJ = new CompFeeds((Integer)obj[0], (String)obj[1], (String)obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
}
