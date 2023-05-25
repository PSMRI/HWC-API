package com.iemr.mmu.service.report;

import java.util.List;
import java.util.Set;

import com.iemr.mmu.data.report.ConsultationReport;
import com.iemr.mmu.data.report.ReportInput;
import com.iemr.mmu.data.report.SpokeReport;
import com.iemr.mmu.data.report.TMDailyReport;
import com.iemr.mmu.utils.exception.TMException;

public interface CRMReportService {

	Set<SpokeReport> getChiefcomplaintreport(ReportInput input)throws TMException;

	List<ConsultationReport> getConsultationReport(ReportInput input) throws TMException;

	String getTotalConsultationReport(ReportInput input) throws TMException;

	String getMonthlyReport(ReportInput input) throws TMException;

	List<TMDailyReport> getDailyReport(ReportInput input) throws TMException;

	
	
	

}
