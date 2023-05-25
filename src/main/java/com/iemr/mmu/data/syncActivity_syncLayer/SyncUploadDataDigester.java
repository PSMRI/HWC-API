package com.iemr.mmu.data.syncActivity_syncLayer;

import java.util.List;
import java.util.Map;

public class SyncUploadDataDigester {
	private String schemaName;
	private String tableName;
	private String vanAutoIncColumnName;
	private String serverColumns;
	private String syncedBy;
	private List<Map<String, Object>> syncData;

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

	public String getVanAutoIncColumnName() {
		return vanAutoIncColumnName;
	}

	public void setVanAutoIncColumnName(String vanAutoIncColumnName) {
		this.vanAutoIncColumnName = vanAutoIncColumnName;
	}

	public String getServerColumns() {
		return serverColumns;
	}

	public void setServerColumns(String serverColumns) {
		this.serverColumns = serverColumns;
	}

	public List<Map<String, Object>> getSyncData() {
		return syncData;
	}

	public void setSyncData(List<Map<String, Object>> syncData) {
		this.syncData = syncData;
	}

	public String getSyncedBy() {
		return syncedBy;
	}

	public void setSyncedBy(String syncedBy) {
		this.syncedBy = syncedBy;
	}

}
