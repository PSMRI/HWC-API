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
package com.iemr.mmu.data.syncActivity_syncLayer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "db_iemr.m_synctabledetail")
public class SyncUtilityClass {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "SyncTableDetailID")
	private Integer syncTableDetailID;
	@Expose
	@Column(name = "SchemaName")
	private String schemaName;
	@Expose
	@Column(name = "TableName")
	private String tableName;
	@Expose
	@Column(name = "DataClassName")
	private String dataClassName;
	@Expose
	@Column(name = "ServerColumnName")
	private String serverColumnName;
	@Expose
	@Column(name = "VanColumnName")
	private String vanColumnName;
	@Expose
	@Column(name = "VanAutoIncColumnName")
	private String vanAutoIncColumnName;
	@Expose
	@Column(name = "SyncTableGroupID")
	private Integer syncTableGroupID;
	@Expose
	@Column(name = "IsMaster")
	private Boolean isMaster;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public Integer getSyncTableDetailID() {
		return syncTableDetailID;
	}

	public void setSyncTableDetailID(Integer syncTableDetailID) {
		this.syncTableDetailID = syncTableDetailID;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDataClassName() {
		return dataClassName;
	}

	public void setDataClassName(String dataClassName) {
		this.dataClassName = dataClassName;
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

	public Boolean getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(Boolean isMaster) {
		this.isMaster = isMaster;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getVanAutoIncColumnName() {
		return vanAutoIncColumnName;
	}

	public void setVanAutoIncColumnName(String vanAutoIncColumnName) {
		this.vanAutoIncColumnName = vanAutoIncColumnName;
	}

	public Integer getSyncTableGroupID() {
		return syncTableGroupID;
	}

	public void setSyncTableGroupID(Integer syncTableGroupID) {
		this.syncTableGroupID = syncTableGroupID;
	}

}
