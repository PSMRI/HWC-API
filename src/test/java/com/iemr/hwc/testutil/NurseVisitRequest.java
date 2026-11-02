package com.iemr.hwc.testutil;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Builds the request document a nurse-visit save reads.
 *
 * <p>Every one of the visit services - antenatal, postnatal, NCD care, general OPD and the
 * rest - takes the same shape: a top-level document carrying the beneficiary and session
 * identifiers, a {@code visitDetails} section holding the visit record itself along with
 * the complaints and the tests ordered, and one further section per form the nurse filled
 * in. Each of those sections is read field by field, and a section that is absent is
 * treated as "nothing to save", so a test that wants the full save path has to name every
 * section the service looks for.
 */
public final class NurseVisitRequest {

	private final JsonObject request = new JsonObject();
	private final JsonObject visitDetails = new JsonObject();
	private JsonObject investigation = investigationOrderingTests();

	private NurseVisitRequest(String visitCategory) {
		request.addProperty("beneficiaryRegID", 1L);
		request.addProperty("benFlowID", 5L);
		request.addProperty("benVisitID", 10L);
		request.addProperty("visitCode", 20L);
		request.addProperty("vanID", 1);
		request.addProperty("sessionID", 1);
		request.addProperty("parkingPlaceID", 1);
		request.addProperty("providerServiceMapID", 1);
		request.addProperty("createdBy", "nurse1");
		visitDetails.add("visitDetails", visitRecord(visitCategory));
		visitDetails.add("chiefComplaints", new JsonArray());
		visitDetails.add("adherence", new JsonObject());
	}

	/**
	 * Every history form a visit service reads.
	 *
	 * <p>A save reports success only when each form it looks for reported a saved record, so
	 * a request that is meant to reach the end of the save carries all of them. A service
	 * that reads only some ignores the rest.
	 */
	public static final String[] HISTORY_SECTIONS = { "pastHistory", "comorbidConditions", "medicationHistory",
			"personalHistory", "familyHistory", "menstrualHistory", "femaleObstetricHistory", "immunizationHistory",
			"childVaccineDetails", "developmentHistory", "feedingHistory", "perinatalHistroy" };

	/** Every examination form a visit service reads. */
	public static final String[] EXAMINATION_SECTIONS = { "generalExamination", "headToToeExamination",
			"gastroIntestinalExamination", "cardioVascularExamination", "respiratorySystemExamination",
			"centralNervousSystemExamination", "musculoskeletalSystemExamination", "genitoUrinarySystemExamination",
			"obstetricExamination", "oralDetails" };

	/** A request for a visit of {@code visitCategory}, with the visit record filled in. */
	public static NurseVisitRequest forCategory(String visitCategory) {
		return new NurseVisitRequest(visitCategory);
	}

	/** The visit record the services read the beneficiary, reason and category out of. */
	public static JsonObject visitRecord(String visitCategory) {
		JsonObject visit = new JsonObject();
		visit.addProperty("beneficiaryRegID", 1L);
		visit.addProperty("benVisitID", 10L);
		visit.addProperty("visitReason", "New Chief Complaint");
		visit.addProperty("visitCategory", visitCategory);
		visit.addProperty("createdBy", "nurse1");
		visit.addProperty("vanID", 1);
		visit.addProperty("parkingPlaceID", 1);
		visit.addProperty("providerServiceMapID", 1);
		return visit;
	}

	/** An investigation section ordering one test, which routes the beneficiary via the lab. */
	public static JsonObject investigationOrderingTests() {
		JsonObject investigation = new JsonObject();
		JsonArray laboratoryList = new JsonArray();
		laboratoryList.add(new JsonObject());
		investigation.add("laboratoryList", laboratoryList);
		return investigation;
	}

	/** An investigation section ordering nothing, which routes the beneficiary to the doctor. */
	public static JsonObject investigationOrderingNothing() {
		JsonObject investigation = new JsonObject();
		investigation.add("laboratoryList", new JsonArray());
		return investigation;
	}

	/**
	 * An object carrying each of {@code keys} as a section.
	 *
	 * <p>A few of the wrapper records walk a list of their own sub-records while being saved
	 * and read that list without a null check, so the sections whose wrapper does that are
	 * given the empty list rather than a bare object.
	 */
	public static JsonObject sections(String... keys) {
		JsonObject json = new JsonObject();
		for (String key : keys) {
			json.add(key, section(key));
		}
		return json;
	}

	private static JsonObject section(String key) {
		JsonObject json = new JsonObject();
		String list = LISTS.get(key);
		if (list != null) {
			json.add(list, new JsonArray());
		}
		return json;
	}

	private static final java.util.Map<String, String> LISTS = java.util.Map.of("medicationHistory",
			"medicationHistoryList", "comorbidConditions", "comorbidityConcurrentConditionsList",
			"immunizationHistory", "immunizationList", "childVaccineDetails", "childOptionalVaccineList",
			"familyHistory", "familyDiseaseList", "femaleObstetricHistory", "femaleObstetricHistoryList");

	/** Replaces the investigation section, which decides where the beneficiary goes next. */
	public NurseVisitRequest ordering(JsonObject investigationSection) {
		this.investigation = investigationSection;
		return this;
	}

	/** Adds a section under {@code visitDetails}. */
	public NurseVisitRequest inVisit(String name, com.google.gson.JsonElement value) {
		visitDetails.add(name, value);
		return this;
	}

	/** Adds a top-level section holding each of {@code keys} as an empty form. */
	public NurseVisitRequest with(String name, String... keys) {
		request.add(name, sections(keys));
		return this;
	}

	/** Adds a top-level section as given. */
	public NurseVisitRequest with(String name, com.google.gson.JsonElement value) {
		request.add(name, value);
		return this;
	}

	/** Adds a top-level scalar field. */
	public NurseVisitRequest field(String name, String value) {
		request.addProperty(name, value);
		return this;
	}

	public JsonObject build() {
		visitDetails.add("investigation", investigation);
		request.add("visitDetails", visitDetails);
		request.add("investigation", investigation);
		return request;
	}
}
