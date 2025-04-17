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
package com.iemr.hwc.controller.report;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.hwc.data.report.ConsultationReport;
import com.iemr.hwc.data.report.ReportInput;
import com.iemr.hwc.data.report.SpokeReport;
import com.iemr.hwc.data.report.TMDailyReport;
import com.iemr.hwc.service.report.CRMReportService;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/TMReport")
@RestController
public class CRMReportController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private CRMReportService cRMReportService;

	
	@Operation(summary = "Fetch chief complaints report")
	@PostMapping(value = "/chiefcomplaintreport", headers = "Authorization", produces = { "application/json" })
	public String chiefcomplaintreport(@RequestBody ReportInput input) {

		OutputResponse response = new OutputResponse();

		try {

			Set<SpokeReport> report = cRMReportService.getChiefcomplaintreport(input);

			response.setResponse(report.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toString();
	}

	
	@Operation(summary = "Fetch consultation report")
	@PostMapping(value = "/ConsultationReport", headers = "Authorization", produces = { "application/json" })
	public String getConsultationReport(@RequestBody ReportInput input) {

		OutputResponse response = new OutputResponse();

		try {

			List<ConsultationReport> report = cRMReportService.getConsultationReport(input);

			response.setResponse(report.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toStringWithSerialization();
	}

	
	@Operation(summary = "Fetch total consultation report")
	@PostMapping(value = "/TotalConsultationReport", headers = "Authorization", produces = { "application/json" })
	public String getTotalConsultationReport(@RequestBody ReportInput input) {

		OutputResponse response = new OutputResponse();

		try {

			String report = cRMReportService.getTotalConsultationReport(input);

			response.setResponse(report);

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toString();
	}

	
	@Operation(summary = "Fetch monthly report")
	@PostMapping(value = "/MonthlyReport", headers = "Authorization", produces = {
			"application/json" })
	public String getMonthlyReport(@RequestBody ReportInput input) {

		OutputResponse response = new OutputResponse();

		try {

			String report = cRMReportService.getMonthlyReport(input);

			response.setResponse(report);

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toString();
	}

	
	@Operation(summary = "Fetch daily report")
	@PostMapping(value = "/DailyReport", headers = "Authorization", produces = {
			"application/json" })
	public String getDailyReport(@RequestBody ReportInput input) {

		OutputResponse response = new OutputResponse();

		try {

			List<TMDailyReport> report = cRMReportService.getDailyReport(input);

			response.setResponse(report.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		/**
		 * sending the response...
		 */
		return response.toStringWithSerialization();

	}

}
