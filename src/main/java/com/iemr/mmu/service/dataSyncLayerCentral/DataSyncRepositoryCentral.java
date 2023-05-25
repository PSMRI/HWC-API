package com.iemr.mmu.service.dataSyncLayerCentral;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/***
 * 
 * @author NE298657
 *
 */

@Service
public class DataSyncRepositoryCentral {
	@Autowired
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	private JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource);

	}

	// Data Upload Repository
	public int checkRecordIsAlreadyPresentOrNot(String schemaName, String tableName, String vanSerialNo, String vanID,
			String vanAutoIncColumnName) {
		jdbcTemplate = getJdbcTemplate();
		String query = " SELECT " + vanAutoIncColumnName + " FROM " + schemaName + "." + tableName
				+ " WHERE VanSerialNo = " + vanSerialNo + " AND VanID = " + vanID;
		List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(query);
		if (resultSet != null && resultSet.size() > 0)
			return 1;
		else
			return 0;
	}

	public int[] syncDataToCentralDB(String query, List<Object[]> syncDataList) {
		// get JDBC template
		jdbcTemplate = getJdbcTemplate();
		// start batch insert/update
		int[] i = jdbcTemplate.batchUpdate(query, syncDataList);

		return i;
	}

	// End of Data Upload Repository

	// Data Download Repository
	public List<Map<String, Object>> getMasterDataFromTable(String schema, String table, String columnNames,
			String masterType, Timestamp lastDownloadDate, Integer vanID, Integer psmID) throws Exception {

		jdbcTemplate = getJdbcTemplate();
		List<Map<String, Object>> resultSetList = new ArrayList<>();
		String baseQuery = "";

		if (masterType != null) {
			if (lastDownloadDate != null) {
				if (masterType.equalsIgnoreCase("A"))
					baseQuery += " SELECT " + columnNames + " FROM " + schema + "." + table
							+ " WHERE Date(LastModDate) >= '" + lastDownloadDate + "' ";
				else if (masterType.equalsIgnoreCase("V"))
					baseQuery += " SELECT " + columnNames + " FROM " + schema + "." + table
							+ " WHERE Date(LastModDate) >= '" + lastDownloadDate + "' AND VanID = " + vanID;
				else if (masterType.equalsIgnoreCase("P"))
					baseQuery += " SELECT " + columnNames + " FROM " + schema + "." + table
							+ " WHERE Date(LastModDate) >= '" + lastDownloadDate + "' AND ProviderServiceMapID = "
							+ psmID;
			} else {
				if (masterType.equalsIgnoreCase("A"))
					baseQuery += " SELECT " + columnNames + " FROM " + schema + "." + table;
				else if (masterType.equalsIgnoreCase("V"))
					baseQuery += " SELECT " + columnNames + " FROM " + schema + "." + table + " WHERE VanID = " + vanID;
				else if (masterType.equalsIgnoreCase("P"))
					baseQuery += " SELECT " + columnNames + " FROM " + schema + "." + table
							+ " WHERE ProviderServiceMapID = " + psmID;

			}
		}

		resultSetList = jdbcTemplate.queryForList(baseQuery);

		return resultSetList;
	}

	// End of Data Download Repository
}
