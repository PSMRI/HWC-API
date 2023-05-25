package com.iemr.mmu.data.syncActivity_syncLayer;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_synctablegroup")
public class DataSyncGroups {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "SyncTableGroupID")
	private Integer syncTableGroupID;
	@Expose
	@Column(name = "SyncTableGroupName")
	private String syncTableGroupName;
	@Expose
	@Column(name = "SyncTableGroupDesc")
	private String syncTableGroupDesc;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;
	@Expose
	@Column(name = "Processed")
	private String processed;
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate")
	private Timestamp createdDate;
	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Expose
	@Column(name = "LastModDate")
	private Timestamp lastModDate;

	public DataSyncGroups() {
	}

	public DataSyncGroups(Integer syncTableGroupID, String syncTableGroupName, String syncTableGroupDesc,
			Boolean deleted, String processed, String createdBy, Timestamp createdDate, String modifiedBy,
			Timestamp lastModDate) {
		super();
		this.syncTableGroupID = syncTableGroupID;
		this.syncTableGroupName = syncTableGroupName;
		this.syncTableGroupDesc = syncTableGroupDesc;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}

}
