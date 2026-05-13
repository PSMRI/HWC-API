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
package com.iemr.hwc.utils.logging;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * Redacts beneficiary personally identifiable information (PII) and personal
 * health information (PHI) from JSON payloads before they reach application
 * logs. Field matching is case-insensitive against a curated AMRIT-domain set.
 * Unparseable payloads are replaced with a length-only summary so raw bodies
 * never leak even when JSON parsing fails.
 */
public final class LogMasker {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String MASK = "***";
	private static final String NULL_PAYLOAD = "[null payload]";

	private static final Set<String> SENSITIVE_KEYS = Arrays.stream(new String[] {
			// government identifiers
			"aadhaar", "aadhaarno", "aadharno", "aadhaarnumber", "aadharnumber", "aadhaarcardno",
			"pan", "panno", "pannumber",
			"voterid", "voteridnumber", "epicno",
			"drivinglicence", "drivinglicense", "drivinglicensenumber",
			"passportno", "passportnumber",
			// health identifiers
			"abha", "abhaaddress", "healthid", "healthidnumber", "healthaccountnumber",
			"hwid", "hpid",
			// contact
			"mobile", "mobileno", "mobilenumber", "phone", "phoneno", "phonenumber",
			"email", "emailid", "emailaddress",
			"emergencycontactname", "emergencycontactnumber",
			"fatheremergencycontactname", "fathercontactnumber",
			// names
			"firstname", "lastname", "middlename", "fullname", "name",
			"fathername", "mothername", "husbandname", "spousename", "guardianname",
			// dob
			"dob", "dateofbirth",
			// address
			"address", "address1", "address2", "addressline1", "addressline2",
			"city", "district", "state", "pincode", "zipcode", "village",
			// free text likely to contain PII
			"remarks", "notes" })
			.collect(Collectors.toUnmodifiableSet());

	private LogMasker() {
		// no-instantiation
	}

	/**
	 * Returns the input JSON with sensitive field values replaced by {@value #MASK}.
	 * Non-JSON payloads are summarised by length rather than echoed verbatim.
	 *
	 * @param payload raw request or response body; may be {@code null}
	 * @return masked payload safe for logging
	 */
	public static String maskJson(String payload) {
		if (payload == null) {
			return NULL_PAYLOAD;
		}
		if (payload.isEmpty()) {
			return payload;
		}
		try {
			JsonNode root = MAPPER.readTree(payload);
			maskNode(root);
			return MAPPER.writeValueAsString(root);
		} catch (JsonProcessingException e) {
			return "[REDACTED non-json payload, length=" + payload.length() + "]";
		}
	}

	private static void maskNode(JsonNode node) {
		if (node == null) {
			return;
		}
		if (node.isObject()) {
			ObjectNode obj = (ObjectNode) node;
			Iterator<Map.Entry<String, JsonNode>> fields = obj.fields();
			while (fields.hasNext()) {
				Map.Entry<String, JsonNode> entry = fields.next();
				if (isSensitive(entry.getKey()) && !entry.getValue().isNull()) {
					obj.set(entry.getKey(), TextNode.valueOf(MASK));
				} else {
					maskNode(entry.getValue());
				}
			}
		} else if (node.isArray()) {
			for (JsonNode item : node) {
				maskNode(item);
			}
		}
	}

	private static boolean isSensitive(String fieldName) {
		if (fieldName == null) {
			return false;
		}
		return SENSITIVE_KEYS.contains(fieldName.toLowerCase(Locale.ROOT));
	}
}
