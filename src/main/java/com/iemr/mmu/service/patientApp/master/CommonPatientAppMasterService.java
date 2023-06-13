/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.patientApp.master;

public interface CommonPatientAppMasterService {
	public String getChiefComplaintsMaster(Integer visitCategoryID, Integer providerServiceMapID, String gender);

	public String getCovidMaster(Integer visitCategoryID, Integer providerServiceMapID, String gender);

	public String saveCovidScreeningData(String requestObj) throws Exception;

	public String savechiefComplaintsData(String requestObj) throws Exception;

	public Integer bookTCSlotData(String requestObj, String Authorization) throws Exception;

	public String getPatientEpisodeData(String requestObj) throws Exception;

	public String getMaster(Integer stateID);

	public String getPatientBookedSlots(String requestObj) throws Exception;

	public Long saveSpecialistDiagnosisData(String requestObj) throws Exception;

	public String getSpecialistDiagnosisData(String requestObj) throws Exception;
	
	public String getPatientsLast_3_Episode(String requestObj) throws Exception;
}
