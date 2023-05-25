package com.iemr.mmu.service.dataSyncActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.iemr.mmu.data.syncActivity_syncLayer.SyncUtilityClass;
import com.iemr.mmu.repo.syncActivity_syncLayer.SyncUtilityClassRepo;

/***
 * 
 * @author NE298657
 *
 */

@Service
public class DataSyncRepository {
	@Autowired
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SyncUtilityClassRepo syncutilityClassRepo;

	private JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource);

	}

	// ---------------------------- Upload repository
	public List<Map<String, Object>> getDataForGivenSchemaAndTable(String schema, String table, String columnNames)
			throws Exception {
		jdbcTemplate = getJdbcTemplate();
		String baseQuery;
		List<Map<String, Object>> resultSetList = new ArrayList<>();

		if (table.equalsIgnoreCase("m_beneficiaryregidmapping")) {
			baseQuery = " SELECT " + columnNames + " FROM " + schema + "." + table
					+ " WHERE provisioned is true AND processed != 'P' AND vanID is not null ";
		} else {
			baseQuery = " SELECT " + columnNames + " FROM " + schema + "." + table
					+ " WHERE processed != 'P' AND vanID is not null ";

		}
		// resultSetList = jdbcTemplate.queryForList(baseQuery, "P");
		resultSetList = jdbcTemplate.queryForList(baseQuery);
		return resultSetList;
	}

	public List<SyncUtilityClass> getVanAndServerColumnList(Integer groupID) throws Exception {
		List<SyncUtilityClass> syncUtilityClassList = syncutilityClassRepo
				.findBySyncTableGroupIDAndDeletedOrderBySyncTableDetailID(groupID, false);
		return syncUtilityClassList;
	}

	public int updateProcessedFlagInVan(String schemaName, String tableName, StringBuilder vanSerialNos,
			String autoIncreamentColumn, String user) throws Exception {
		jdbcTemplate = getJdbcTemplate();
		String query = " UPDATE " + schemaName + "." + tableName
				+ " SET processed = 'P' , SyncedDate = now(), Syncedby = '" + user + "' WHERE " + autoIncreamentColumn
				+ " IN (" + vanSerialNos + ")";
		System.out.println("hello");

		int i = jdbcTemplate.update(query);

		return i;

	}

	// ---------------------------------- End of Upload repository

	//
	//

	// ---------------------------------- Download Repository
	public int[] updateLatestMasterInLocal(String query, List<Object[]> syncDataList) {
		int[] i = null;
		// get JDBC template
		jdbcTemplate = getJdbcTemplate();
		// start batch insert/update
		i = jdbcTemplate.batchUpdate(query, syncDataList);

		return i;

	}

	// ---------------------------------- End of Download Repository

}
