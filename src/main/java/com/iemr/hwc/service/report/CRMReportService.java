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
package com.iemr.hwc.service.report;

import java.util.List;
import java.util.Set;

import com.iemr.hwc.data.report.ConsultationReport;
import com.iemr.hwc.data.report.ReportInput;
import com.iemr.hwc.data.report.SpokeReport;
import com.iemr.hwc.data.report.TMDailyReport;
import com.iemr.hwc.utils.exception.TMException;

public interface CRMReportService {

	Set<SpokeReport> getChiefcomplaintreport(ReportInput input)throws TMException;

	List<ConsultationReport> getConsultationReport(ReportInput input) throws TMException;

	String getTotalConsultationReport(ReportInput input) throws TMException;

	String getMonthlyReport(ReportInput input) throws TMException;

	List<TMDailyReport> getDailyReport(ReportInput input) throws TMException;

	
	
	

}
