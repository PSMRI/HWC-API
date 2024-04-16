package com.iemr.hwc.controller.report;

import com.iemr.hwc.data.report.ConsultationReport;
import com.iemr.hwc.data.report.ReportInput;
import com.iemr.hwc.data.report.SpokeReport;
import com.iemr.hwc.data.report.TMDailyReport;
import com.iemr.hwc.service.report.CRMReportService;
import com.iemr.hwc.utils.exception.TMException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CRMReportControllerTest {

    @Mock
    CRMReportService cRMReportService;
    @InjectMocks
    CRMReportController cRMReportController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testChiefcomplaintreport() throws TMException {
        when(cRMReportService.getChiefcomplaintreport(any(ReportInput.class))).thenReturn(Set.of(new SpokeReport()));

        String result = cRMReportController.chiefcomplaintreport(new ReportInput());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetConsultationReport() throws TMException {
        when(cRMReportService.getConsultationReport(any(ReportInput.class))).thenReturn(List.of(new ConsultationReport()));

        String result = cRMReportController.getConsultationReport(new ReportInput());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetTotalConsultationReport() throws TMException {
        when(cRMReportService.getTotalConsultationReport(any(ReportInput.class))).thenReturn("getTotalConsultationReportResponse");

        String result = cRMReportController.getTotalConsultationReport(new ReportInput());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetMonthlyReport() throws TMException {
        when(cRMReportService.getMonthlyReport(any(ReportInput.class))).thenReturn("getMonthlyReportResponse");

        String result = cRMReportController.getMonthlyReport(new ReportInput());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetDailyReport() throws TMException {
        when(cRMReportService.getDailyReport(any(ReportInput.class))).thenReturn(List.of(new TMDailyReport()));

        String result = cRMReportController.getDailyReport(new ReportInput());
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

