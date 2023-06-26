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
package com.iemr.hwc.service.patientApp.master;

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
