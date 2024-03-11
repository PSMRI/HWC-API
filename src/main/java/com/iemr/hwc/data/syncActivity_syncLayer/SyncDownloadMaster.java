/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.hwc.data.syncActivity_syncLayer;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_downloadmastertable")
public class SyncDownloadMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "DownloadMasterTableID", updatable = false)
	private Integer downloadMasterTableID;
	@Expose
	@Column(name = "SchemaName", updatable = false)
	private String schemaName;
	@Expose
	@Column(name = "TableName", updatable = false)
	private String tableName;
	@Expose
	@Column(name = "ServerColumnName", updatable = false)
	private String serverColumnName;
	@Expose
	@Column(name = "VanColumnName", updatable = false)
	private String vanColumnName;
	@Expose
	@Column(name = "AutoIncColumnName", updatable = false)
	private String autoIncColumnName;
	@Expose
	@Column(name = "MasterType", updatable = false)
	private String masterType;
	@Expose
	@Column(name = "LastDownloadDate", updatable = true)
	private Timestamp lastDownloadDate;
	@Expose
	@Column(name = "Deleted", updatable = false)
	private Boolean deleted;
	@Expose
	@Column(name = "Processed", updatable = false)
	private String processed;
	@Expose
	@Column(name = "CreatedBy", updatable = false)
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate", updatable = false)
	private Timestamp createdDate;
	@Expose
	@Column(name = "ModifiedBy", updatable = false)
	private String modifiedBy;
	@Expose
	@Column(name = "LastModDate", updatable = false)
	private Timestamp lastModDate;

	@Transient
	@Expose
	private Integer vanID;
	@Transient
	@Expose
	private Integer providerServiceMapID;

	public SyncDownloadMaster() {
	}

	public SyncDownloadMaster(Integer downloadMasterTableID, String schemaName, String tableName,
			String serverColumnName, String vanColumnName, String autoIncColumnName, String masterType,
			Timestamp lastDownloadDate, Boolean deleted, String processed, String createdBy, Timestamp createdDate,
			String modifiedBy, Timestamp lastModDate, Integer vanID, Integer providerServiceMapID) {
		super();
		this.downloadMasterTableID = downloadMasterTableID;
		this.schemaName = schemaName;
		this.tableName = tableName;
		this.serverColumnName = serverColumnName;
		this.vanColumnName = vanColumnName;
		this.autoIncColumnName = autoIncColumnName;
		this.masterType = masterType;
		this.lastDownloadDate = lastDownloadDate;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
		this.vanID = vanID;
		this.providerServiceMapID = providerServiceMapID;
	}

	public Integer getDownloadMasterTableID() {
		return downloadMasterTableID;
	}

	public void setDownloadMasterTableID(Integer downloadMasterTableID) {
		this.downloadMasterTableID = downloadMasterTableID;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getServerColumnName() {
		return serverColumnName;
	}

	public void setServerColumnName(String serverColumnName) {
		this.serverColumnName = serverColumnName;
	}

	public String getVanColumnName() {
		return vanColumnName;
	}

	public void setVanColumnName(String vanColumnName) {
		this.vanColumnName = vanColumnName;
	}

	public String getAutoIncColumnName() {
		return autoIncColumnName;
	}

	public void setAutoIncColumnName(String autoIncColumnName) {
		this.autoIncColumnName = autoIncColumnName;
	}

	public String getMasterType() {
		return masterType;
	}

	public void setMasterType(String masterType) {
		this.masterType = masterType;
	}

	public Timestamp getLastDownloadDate() {
		return lastDownloadDate;
	}

	public void setLastDownloadDate(Timestamp lastDownloadDate) {
		this.lastDownloadDate = lastDownloadDate;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getLastModDate() {
		return lastModDate;
	}

	public void setLastModDate(Timestamp lastModDate) {
		this.lastModDate = lastModDate;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

}
