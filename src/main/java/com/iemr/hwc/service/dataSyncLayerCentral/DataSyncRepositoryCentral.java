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
package com.iemr.hwc.service.dataSyncLayerCentral;

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
		List<Map<String, Object>> resultSetList;

		// Validate and sanitize schema and table names to avoid SQL injection
		if (!isValidName(schema) || !isValidName(table)) {
			throw new IllegalArgumentException("Invalid schema or table name.");
		}

		StringBuilder queryBuilder = new StringBuilder("SELECT ");

		// Append the column names directly, they are not dynamic and safe
		queryBuilder.append(columnNames);
		queryBuilder.append(" FROM ");
		queryBuilder.append(schema).append(".").append(table);

		List<Object> params = new ArrayList<>();

		StringBuilder whereClause = new StringBuilder();
		if (masterType != null) {
			whereClause.append(" WHERE ");
			if (lastDownloadDate != null) {
				whereClause.append("Date(LastModDate) >= ?");
				params.add(lastDownloadDate);
			}

			if (masterType.equalsIgnoreCase("A")) {
				// No additional condition needed for masterType "A"
			} else if (masterType.equalsIgnoreCase("V")) {
				if (lastDownloadDate != null) {
					whereClause.append(" AND ");
				}
				whereClause.append("VanID = ?");
				params.add(vanID);
			} else if (masterType.equalsIgnoreCase("P")) {
				if (lastDownloadDate != null) {
					whereClause.append(" AND ");
				}
				whereClause.append("ProviderServiceMapID = ?");
				params.add(psmID);
			}
		}

		queryBuilder.append(whereClause);

		// Use PreparedStatement to avoid SQL injection and improve performance
		String query = queryBuilder.toString();
		Object[] queryParams = params.toArray();

		// Use PreparedStatement for the entire query
		resultSetList = jdbcTemplate.queryForList(query, queryParams);
		return resultSetList;
	}

	// Helper method to validate schema and table names
	private boolean isValidName(String name) {
		// Check if the name is not null or empty
		if (name == null || name.isEmpty()) {
			return false;
		}

		// Ensure the name adheres to the naming conventions
		// It should start with a letter (either uppercase or lowercase)
		// followed by alphanumeric characters and underscores
		return name.matches("^[A-Za-z][A-Za-z0-9_]*$");
	}

	// End of Data Download Repository
}
