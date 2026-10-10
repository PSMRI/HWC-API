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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class LogMaskerTest {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Test
	void nullPayloadReturnsPlaceholder() {
		assertEquals("[null payload]", LogMasker.maskJson(null));
	}

	@Test
	void emptyPayloadIsReturnedUnchanged() {
		assertEquals("", LogMasker.maskJson(""));
	}

	@Test
	void nonJsonPayloadIsReplacedByLengthOnlySummary() {
		String raw = "9876543210 is the mobile";
		String masked = LogMasker.maskJson(raw);
		assertFalse(masked.contains("9876543210"), "Raw PII must not survive a non-JSON fallback.");
		assertTrue(masked.contains("length=" + raw.length()));
	}

	@Test
	void topLevelIdentifierFieldsAreMasked() throws Exception {
		String input = "{\"aadhaarNo\":\"123412341234\",\"mobileNo\":\"9876543210\",\"benRegID\":555}";
		JsonNode masked = MAPPER.readTree(LogMasker.maskJson(input));
		assertEquals("***", masked.get("aadhaarNo").asText());
		assertEquals("***", masked.get("mobileNo").asText());
		assertEquals(555, masked.get("benRegID").asInt(),
				"Internal numeric IDs are not direct PII and remain visible for traceability.");
	}

	@Test
	void fieldMatchingIsCaseInsensitive() throws Exception {
		String input = "{\"AADHAAR\":\"123412341234\",\"DateOfBirth\":\"1990-01-01\"}";
		JsonNode masked = MAPPER.readTree(LogMasker.maskJson(input));
		assertEquals("***", masked.get("AADHAAR").asText());
		assertEquals("***", masked.get("DateOfBirth").asText());
	}

	@Test
	void nestedObjectsAreTraversedRecursively() throws Exception {
		String input = "{"
				+ "\"beneficiary\":{\"firstName\":\"Asha\",\"address\":{\"pincode\":\"560001\",\"city\":\"Bengaluru\"}},"
				+ "\"visitCode\":12345"
				+ "}";
		JsonNode masked = MAPPER.readTree(LogMasker.maskJson(input));
		assertEquals("***", masked.get("beneficiary").get("firstName").asText());
		assertEquals("***", masked.get("beneficiary").get("address").get("pincode").asText());
		assertEquals("***", masked.get("beneficiary").get("address").get("city").asText());
		assertEquals(12345, masked.get("visitCode").asInt());
	}

	@Test
	void arraysOfBeneficiariesAreMaskedElementByElement() throws Exception {
		String input = "{\"familyMembers\":["
				+ "{\"name\":\"Rita\",\"mobile\":\"9000000000\"},"
				+ "{\"name\":\"Sita\",\"mobile\":\"9111111111\"}"
				+ "]}";
		JsonNode masked = MAPPER.readTree(LogMasker.maskJson(input));
		assertEquals("***", masked.get("familyMembers").get(0).get("name").asText());
		assertEquals("***", masked.get("familyMembers").get(0).get("mobile").asText());
		assertEquals("***", masked.get("familyMembers").get(1).get("name").asText());
		assertEquals("***", masked.get("familyMembers").get(1).get("mobile").asText());
	}

	@Test
	void nullSensitiveValueIsPreservedSoConsumersCanDistinguishMissingFromMasked() throws Exception {
		String input = "{\"firstName\":null,\"mobileNo\":\"9876543210\"}";
		JsonNode masked = MAPPER.readTree(LogMasker.maskJson(input));
		assertTrue(masked.get("firstName").isNull());
		assertEquals("***", masked.get("mobileNo").asText());
	}

	@Test
	void nonSensitiveFieldsAreUntouched() throws Exception {
		String input = "{\"providerServiceMapID\":42,\"vanID\":7,\"parkingPlaceID\":2}";
		JsonNode masked = MAPPER.readTree(LogMasker.maskJson(input));
		assertEquals(42, masked.get("providerServiceMapID").asInt());
		assertEquals(7, masked.get("vanID").asInt());
		assertEquals(2, masked.get("parkingPlaceID").asInt());
	}

	@Test
	void healthIdAndAbhaAreTreatedAsSensitive() throws Exception {
		String input = "{\"healthId\":\"abc@abdm\",\"abhaAddress\":\"abc@abdm\"}";
		JsonNode masked = MAPPER.readTree(LogMasker.maskJson(input));
		assertEquals("***", masked.get("healthId").asText());
		assertEquals("***", masked.get("abhaAddress").asText());
	}

	@Test
	void freeTextRemarksAreMaskedToProtectAccidentalPiiLeakage() throws Exception {
		String input = "{\"remarks\":\"Patient Asha 9876543210 reports fever\"}";
		JsonNode masked = MAPPER.readTree(LogMasker.maskJson(input));
		assertEquals("***", masked.get("remarks").asText());
	}
}
