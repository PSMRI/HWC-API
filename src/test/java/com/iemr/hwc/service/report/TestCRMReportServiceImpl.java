package com.iemr.hwc.service.report;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.hwc.data.login.UserParkingplaceMapping;
import com.iemr.hwc.data.report.ConsultationReport;
import com.iemr.hwc.data.report.ReportInput;
import com.iemr.hwc.data.report.SpokeReport;
import com.iemr.hwc.data.report.TMDailyReport;
import com.iemr.hwc.repo.login.UserParkingplaceMappingRepo;
import com.iemr.hwc.repo.report.BenChiefComplaintReportRepo;
import com.iemr.hwc.utils.exception.TMException;

public class TestCRMReportServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private UserParkingplaceMappingRepo userParkingplaceMappingRepo;

	@Mock
	private BenChiefComplaintReportRepo benChiefComplaintReportRepo;

	@InjectMocks
	private CRMReportServiceImpl crmReportService;

	private UserParkingplaceMapping userParkingplaceMapping;
	private CRMReportServiceImpl crmReportServiceImpl;

	private ReportInput reportInput;
	private List<Object[]> mockData;
	private List<Object[]> mockConsultationReportData;
	private List<Object[]> mockRepoResponse;

	@BeforeEach
	public void setUp() {
		userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setParkingPlaceID(100);
		userParkingplaceMapping.setDeleted(0);
	}

	@Test
	public void testGetParkingplaceID_UserMappedToParkingPlace() throws TMException {
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0))
				.thenReturn(userParkingplaceMapping);

		Integer parkingPlaceID = crmReportService.getParkingplaceID(1, 1);

		assertNotNull(parkingPlaceID);
		assertEquals(100, parkingPlaceID);
		verify(userParkingplaceMappingRepo, times(1)).findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0);
	}

	@Test
	public void testGetParkingplaceID_UserNotMappedToParkingPlace() {
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0)).thenReturn(null);

		TMException exception = assertThrows(TMException.class, () -> {
			crmReportService.getParkingplaceID(1, 1);
		});

		assertEquals("User Not mapped to any Parking Place", exception.getMessage());
		verify(userParkingplaceMappingRepo, times(1)).findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0);
	}

	@Test
	public void testCalculateTimeWithValidTimestamps() {
		Timestamp arrivalTime = Timestamp.valueOf("2023-10-01 10:00:00");
		Timestamp consultedTime = Timestamp.valueOf("2023-10-01 12:30:45");
		String result = CRMReportServiceImpl.calculateTime(consultedTime, arrivalTime);
		assertEquals("2 hrs30 min45 sec", result);
	}

	@Test
	public void testCalculateTimeWithNegativeWaitingTime() {
		Timestamp arrivalTime = Timestamp.valueOf("2023-10-01 12:00:00");
		Timestamp consultedTime = Timestamp.valueOf("2023-10-01 10:00:00");
		String result = CRMReportServiceImpl.calculateTime(consultedTime, arrivalTime);
		assertEquals("No Waiting", result);
	}

	@Test
	public void testCalculateTimeWithZeroWaitingTime() {
		Timestamp arrivalTime = Timestamp.valueOf("2023-10-01 10:00:00");
		Timestamp consultedTime = Timestamp.valueOf("2023-10-01 10:00:00");
		String result = CRMReportServiceImpl.calculateTime(consultedTime, arrivalTime);
		assertEquals("No Waiting", result);
	}

	@Test
	public void testCalculateTimeWithOnlySeconds() {
		Timestamp arrivalTime = Timestamp.valueOf("2023-10-01 10:00:00");
		Timestamp consultedTime = Timestamp.valueOf("2023-10-01 10:00:45");
		String result = CRMReportServiceImpl.calculateTime(consultedTime, arrivalTime);
		assertEquals("45 sec", result);
	}

	@Test
	public void testCalculateTimeWithOnlyMinutes() {
		Timestamp arrivalTime = Timestamp.valueOf("2023-10-01 10:00:00");
		Timestamp consultedTime = Timestamp.valueOf("2023-10-01 10:30:00");
		String result = CRMReportServiceImpl.calculateTime(consultedTime, arrivalTime);
		assertEquals("30 min", result);
	}

	@Test
	public void testCalculateTimeWithOnlyHours() {
		Timestamp arrivalTime = Timestamp.valueOf("2023-10-01 10:00:00");
		Timestamp consultedTime = Timestamp.valueOf("2023-10-01 12:00:00");
		String result = CRMReportServiceImpl.calculateTime(consultedTime, arrivalTime);
		assertEquals("2 hrs", result);
	}

	@Test
	public void testGetConsultationReportObj() {
		// Prepare sample input data
		Object[] sampleInput = new Object[27];
		sampleInput[2] = "12345"; // BeneficiaryRegID
		sampleInput[5] = "V001"; // VisitCode
		sampleInput[3] = "John Doe"; // BeneficiaryName
		sampleInput[11] = "Dr. Smith"; // SpecialistName
		sampleInput[22] = "SP001"; // SpecialistId
		sampleInput[7] = "SPC001"; // SpecializationID
		sampleInput[8] = "Cardiology"; // Specialization
		sampleInput[12] = Timestamp.valueOf("2023-10-01 10:00:00"); // RequestedDate
		sampleInput[14] = "D"; // Consulted
		sampleInput[16] = Timestamp.valueOf("2023-10-01 09:00:00"); // BeneficiaryArrivalTime
		sampleInput[17] = Timestamp.valueOf("2023-10-01 09:30:00"); // ConsultationFirstStart
		sampleInput[23] = Timestamp.valueOf("2023-10-01 09:45:00"); // SpecialistConsultationStart
		sampleInput[24] = Timestamp.valueOf("2023-10-01 10:15:00"); // ConsultationEnd
		sampleInput[25] = "30 mins"; // tATForArrivalToConsultationStart
		sampleInput[26] = "30 mins"; // tATForSpecialistConsultationStartToEnd

		// Call the method under test
		ConsultationReport report = CRMReportServiceImpl.getConsultationReportObj(sampleInput);

		// Assert the values
		assertEquals("12345", report.getBeneficiaryRegID());
		assertEquals("V001", report.getVisitCode());
		assertEquals("John Doe", report.getBeneficiaryName());
		assertEquals("Dr. Smith", report.getSpecialistName());
		assertEquals("SP001", report.getSpecialistId());
		assertEquals("SPC001", report.getSpecializationID());
		assertEquals("Cardiology", report.getSpecialization());
		assertEquals(Timestamp.valueOf("2023-10-01 10:00:00"), report.getRequestedDate());
		assertEquals("YES", report.getConsulted());
		assertEquals(Timestamp.valueOf("2023-10-01 09:00:00"), report.getBeneficiaryArrivalTime());
		assertEquals(Timestamp.valueOf("2023-10-01 09:30:00"), report.getConsultationFirstStart());
		assertEquals(Timestamp.valueOf("2023-10-01 09:45:00"), report.getSpecialistConsultationStart());
		assertEquals(Timestamp.valueOf("2023-10-01 10:15:00"), report.getConsultationEnd());
		assertEquals("30 mins", report.gettATForArrivalToConsultationStart());
		assertEquals("30 mins", report.gettATForSpecialistConsultationStartToEnd());
	}

	@Test
	public void testGetConsultationReportObj() {
		// Prepare sample input data
		Object[] sampleInput = new Object[27];
		sampleInput[2] = "12345"; // BeneficiaryRegID
		sampleInput[5] = "V001"; // VisitCode
		sampleInput[3] = "John Doe"; // BeneficiaryName
		sampleInput[11] = "Dr. Smith"; // SpecialistName
		sampleInput[22] = "SP001"; // SpecialistId
		sampleInput[7] = "SPC001"; // SpecializationID
		sampleInput[8] = "Cardiology"; // Specialization
		sampleInput[12] = Timestamp.valueOf("2023-10-01 10:00:00"); // RequestedDate
		sampleInput[14] = "D"; // Consulted
		sampleInput[16] = Timestamp.valueOf("2023-10-01 09:00:00"); // BeneficiaryArrivalTime
		sampleInput[17] = Timestamp.valueOf("2023-10-01 09:30:00"); // ConsultationFirstStart
		sampleInput[23] = Timestamp.valueOf("2023-10-01 09:45:00"); // SpecialistConsultationStart
		sampleInput[24] = Timestamp.valueOf("2023-10-01 10:15:00"); // ConsultationEnd
		sampleInput[25] = "30 mins"; // tATForArrivalToConsultationStart
		sampleInput[26] = "30 mins"; // tATForSpecialistConsultationStartToEnd

		// Call the method under test
		ConsultationReport report = CRMReportServiceImpl.getConsultationReportObj(sampleInput);

		// Assert the values
		assertEquals("12345", report.getBeneficiaryRegID());
		assertEquals("V001", report.getVisitCode());
		assertEquals("John Doe", report.getBeneficiaryName());
		assertEquals("Dr. Smith", report.getSpecialistName());
		assertEquals("SP001", report.getSpecialistId());
		assertEquals("SPC001", report.getSpecializationID());
		assertEquals("Cardiology", report.getSpecialization());
		assertEquals(Timestamp.valueOf("2023-10-01 10:00:00"), report.getRequestedDate());
		assertEquals("YES", report.getConsulted());
		assertEquals(Timestamp.valueOf("2023-10-01 09:00:00"), report.getBeneficiaryArrivalTime());
		assertEquals(Timestamp.valueOf("2023-10-01 09:30:00"), report.getConsultationFirstStart());
		assertEquals(Timestamp.valueOf("2023-10-01 09:45:00"), report.getSpecialistConsultationStart());
		assertEquals(Timestamp.valueOf("2023-10-01 10:15:00"), report.getConsultationEnd());
		assertEquals("30 mins", report.gettATForArrivalToConsultationStart());
		assertEquals("30 mins", report.gettATForSpecialistConsultationStartToEnd());
	}

	@Test
	public void testGetSpokeReportObj() {
		// Sample data
		Object[] obj = new Object[4];
		obj[2] = 1; // VanID
		obj[3] = "VanName"; // VanName

		// Call the method
		SpokeReport result = CRMReportServiceImpl.getSpokeReportObj(obj);

		// Assertions
		assertNotNull(result);
		assertEquals(1, result.getVanID());
		assertEquals("VanName", result.getVanName());
	}

	@Test
	public void testGetSpokeReportObj1_ValidData() {
		Object[] obj = new Object[] { 1, "VanName" };
		SpokeReport result = CRMReportServiceImpl.getSpokeReportObj1(obj);

		assertNotNull(result);
		assertEquals(1, result.getVanID());
		assertEquals("VanName", result.getVanName());
	}

	@Test
	public void testGetSpokeReportObj1_NullValues() {
		Object[] obj = new Object[] { null, null };
		SpokeReport result = CRMReportServiceImpl.getSpokeReportObj1(obj);

		assertNotNull(result);
		assertNull(result.getVanID());
		assertNull(result.getVanName());
	}

	@Test
	public void testGetSpokeReportObj1_PartialNullValues() {
		Object[] obj = new Object[] { 1, null };
		SpokeReport result = CRMReportServiceImpl.getSpokeReportObj1(obj);

		assertNotNull(result);
		assertEquals(1, result.getVanID());
		assertNull(result.getVanName());
	}

	@Test
	public void testGetSpokeReportObj1_InvalidDataType() {
		Object[] obj = new Object[] { "InvalidID", "VanName" };
		assertThrows(ClassCastException.class, () -> {
			CRMReportServiceImpl.getSpokeReportObj1(obj);
		});
	}

	@Test
	public void testGetTMDailyReportObj_AllValuesPresent() {
		Object[] obj = new Object[6];
		obj[1] = "SpokeName";
		obj[2] = 10L;
		obj[3] = 5L;
		obj[4] = 15L;
		obj[5] = 7L;

		TMDailyReport report = CRMReportServiceImpl.getTMDailyReportObj(obj);

		assertEquals("SpokeName", report.getSpokeName());
		assertEquals(BigInteger.valueOf(10L), report.getCurrentConsultations());
		assertEquals(BigInteger.valueOf(5L), report.getRevisitConsultations());
		assertEquals(BigInteger.valueOf(15L), report.getCumulativeConsultationsForMonth());
		assertEquals(BigInteger.valueOf(7L), report.getCumulativeRevisitConsultationsForMonth());
	}

	@Test
	public void testGetTMDailyReportObj_NullValues() {
		Object[] obj = new Object[6];
		obj[1] = "SpokeName";
		obj[2] = null;
		obj[3] = null;
		obj[4] = null;
		obj[5] = null;

		TMDailyReport report = CRMReportServiceImpl.getTMDailyReportObj(obj);

		assertEquals("SpokeName", report.getSpokeName());
		assertEquals(BigInteger.valueOf(0), report.getCurrentConsultations());
		assertEquals(BigInteger.valueOf(0), report.getRevisitConsultations());
		assertEquals(BigInteger.valueOf(0), report.getCumulativeConsultationsForMonth());
		assertEquals(BigInteger.valueOf(0), report.getCumulativeRevisitConsultationsForMonth());
	}

	@Test
	public void testGetChiefcomplaintreport() throws TMException {
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0))
				.thenReturn(new UserParkingplaceMapping(1, 1, 1));
		when(benChiefComplaintReportRepo.getcmreport(reportInput.getFromDate(), reportInput.getToDate(), 1))
				.thenReturn(mockData);

		Set<SpokeReport> result = crmReportServiceImpl.getChiefcomplaintreport(reportInput);

		assertNotNull(result);
		assertEquals(1, result.size());

		SpokeReport spokeReport = result.iterator().next();
		assertEquals("Spoke1", spokeReport.getVanName());
		assertEquals(2, spokeReport.getChiefComplaintReport().size());

		ChiefComplaintReport complaint1 = spokeReport.getChiefComplaintReport().get(0);
		assertEquals("Complaint1", complaint1.getChiefComplaint());
		assertEquals(BigInteger.valueOf(10), complaint1.getGrandTotal());

		ChiefComplaintReport complaint2 = spokeReport.getChiefComplaintReport().get(1);
		assertEquals("Complaint2", complaint2.getChiefComplaint());
		assertEquals(BigInteger.valueOf(20), complaint2.getGrandTotal());

		verify(userParkingplaceMappingRepo, times(1)).findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0);
		verify(benChiefComplaintReportRepo, times(1)).getcmreport(reportInput.getFromDate(), reportInput.getToDate(),
				1);
	}

	@Test
	public void testGetChiefcomplaintreport_UserNotMapped() {
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0)).thenReturn(null);

		TMException exception = assertThrows(TMException.class, () -> {
			crmReportServiceImpl.getChiefcomplaintreport(reportInput);
		});

		assertEquals("User Not mapped to any Parking Place", exception.getMessage());
		verify(userParkingplaceMappingRepo, times(1)).findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0);
		verify(benChiefComplaintReportRepo, times(0)).getcmreport(any(), any(), anyInt());
	}

	@Test
	public void testGetConsultationReport_Success() throws TMException {
		when(benChiefComplaintReportRepo.getConsultationReport(any(), any(), anyInt()))
				.thenReturn(mockConsultationReportData);

		List<ConsultationReport> result = crmReportServiceImpl.getConsultationReport(reportInput);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("123", result.get(0).getBeneficiaryRegID());
		assertEquals("VisitCode", result.get(0).getVisitCode());
		assertEquals("John Doe", result.get(0).getBeneficiaryName());
		assertEquals("SpecialistName", result.get(0).getSpecialistName());
	}

	@Test
	public void testGetConsultationReport_NoData() throws TMException {
		when(benChiefComplaintReportRepo.getConsultationReport(any(), any(), anyInt())).thenReturn(new ArrayList<>());

		List<ConsultationReport> result = crmReportServiceImpl.getConsultationReport(reportInput);

		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetConsultationReport_Exception() throws TMException {
		when(benChiefComplaintReportRepo.getConsultationReport(any(), any(), anyInt()))
				.thenThrow(new TMException("Database error"));

		TMException exception = assertThrows(TMException.class, () -> {
			crmReportServiceImpl.getConsultationReport(reportInput);
		});

		assertEquals("Database error", exception.getMessage());
	}

	@Test
	public void testGetTotalConsultationReport() throws TMException {
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(anyInt(), anyInt(), eq(0)))
				.thenReturn(new UserParkingplaceMapping(1, 1, 1));
		when(benChiefComplaintReportRepo.getTotalConsultationReport(any(Date.class), any(Date.class), anyInt()))
				.thenReturn(mockData);

		String result = crmReportServiceImpl.getTotalConsultationReport(reportInput);

		assertNotNull(result);
		assertTrue(result.contains("Indicator1"));
		assertTrue(result.contains("Indicator2"));
		assertTrue(result.contains("Jan-23"));
		assertTrue(result.contains("Feb-23"));
	}

	@Test
	public void testGetTotalConsultationReport_NoData() throws TMException {
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(anyInt(), anyInt(), eq(0)))
				.thenReturn(new UserParkingplaceMapping(1, 1, 1));
		when(benChiefComplaintReportRepo.getTotalConsultationReport(any(Date.class), any(Date.class), anyInt()))
				.thenReturn(new ArrayList<>());

		String result = crmReportServiceImpl.getTotalConsultationReport(reportInput);

		assertNotNull(result);
		assertEquals("[]", result);
	}

	@Test
	public void testGetTotalConsultationReport_Exception() throws TMException {
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(anyInt(), anyInt(), eq(0)))
				.thenReturn(new UserParkingplaceMapping(1, 1, 1));
		when(benChiefComplaintReportRepo.getTotalConsultationReport(any(Date.class), any(Date.class), anyInt()))
				.thenThrow(new TMException("Database error"));

		TMException exception = assertThrows(TMException.class, () -> {
			crmReportServiceImpl.getTotalConsultationReport(reportInput);
		});

		assertEquals("Database error", exception.getMessage());
	}

	@Test
	public void testGetMonthlyReport_ValidInputWithData() throws TMException {
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0))
				.thenReturn(userParkingplaceMapping);
		when(benChiefComplaintReportRepo.getMonthlyReport(reportInput.getFromDate(), reportInput.getToDate(), 1, null))
				.thenReturn(mockData);

		String result = crmReportService.getMonthlyReport(reportInput);

		assertNotNull(result);
		assertTrue(result.contains("Indicator1"));
		assertTrue(result.contains("Indicator2"));
		assertTrue(result.contains("Jan-23"));
		assertTrue(result.contains("Feb-23"));
	}

	@Test
	public void testGetMonthlyReport_ValidInputNoData() throws TMException {
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0))
				.thenReturn(userParkingplaceMapping);
		when(benChiefComplaintReportRepo.getMonthlyReport(reportInput.getFromDate(), reportInput.getToDate(), 1, null))
				.thenReturn(new ArrayList<>());

		String result = crmReportService.getMonthlyReport(reportInput);

		assertNotNull(result);
		assertEquals("[]", result);
	}

	@Test
	public void testGetMonthlyReport_InvalidUserMapping() {
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0)).thenReturn(null);

		assertThrows(TMException.class, () -> {
			crmReportService.getMonthlyReport(reportInput);
		});
	}

	@Test
	public void testGetMonthlyReport_ExceptionHandling() throws TMException {
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0))
				.thenReturn(userParkingplaceMapping);
		when(benChiefComplaintReportRepo.getMonthlyReport(reportInput.getFromDate(), reportInput.getToDate(), 1, null))
				.thenThrow(new RuntimeException("Database error"));

		assertThrows(TMException.class, () -> {
			crmReportService.getMonthlyReport(reportInput);
		});
	}

	@Test
	public void testGetDailyReport() throws TMException {
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0))
				.thenReturn(new UserParkingplaceMapping(1, 1, 1));
		when(benChiefComplaintReportRepo.getDailyReport(reportInput.getFromDate(), 1)).thenReturn(mockRepoResponse);

		List<TMDailyReport> result = crmReportServiceImpl.getDailyReport(reportInput);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Spoke1", result.get(0).getSpokeName());
		assertEquals(BigInteger.valueOf(10L), result.get(0).getCurrentConsultations());
		assertEquals(BigInteger.valueOf(5L), result.get(0).getRevisitConsultations());
		assertEquals(BigInteger.valueOf(15L), result.get(0).getCumulativeConsultationsForMonth());
		assertEquals(BigInteger.valueOf(20L), result.get(0).getCumulativeRevisitConsultationsForMonth());

		verify(userParkingplaceMappingRepo, times(1)).findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0);
		verify(benChiefComplaintReportRepo, times(1)).getDailyReport(reportInput.getFromDate(), 1);
	}

	@Test
	public void testGetDailyReport_UserNotMapped() {
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0)).thenReturn(null);

		TMException exception = assertThrows(TMException.class, () -> {
			crmReportServiceImpl.getDailyReport(reportInput);
		});

		assertEquals("User Not mapped to any Parking Place", exception.getMessage());
		verify(userParkingplaceMappingRepo, times(1)).findOneByUserIDAndProviderServiceMapIdAndDeleted(1, 1, 0);
		verify(benChiefComplaintReportRepo, times(0)).getDailyReport(any(Date.class), anyInt());
	}
}
