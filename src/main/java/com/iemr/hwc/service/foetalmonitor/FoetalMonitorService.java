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
package com.iemr.hwc.service.foetalmonitor;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.iemr.hwc.data.foetalmonitor.FoetalMonitor;
import com.iemr.hwc.utils.exception.IEMRException;

public interface FoetalMonitorService {

	int updateFoetalMonitorData(FoetalMonitor foetalMonitorData) throws IEMRException;

	String sendFoetalMonitorTestDetails(FoetalMonitor request, String auth) throws Exception;

	String getFoetalMonitorDetails(Long benFlowID) throws IEMRException;

	public String readPDFANDGetBase64(String filePath) throws IEMRException, IOException, FileNotFoundException;
}
