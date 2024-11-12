package com.iemr.hwc.service.report;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.iemr.hwc.data.login.UserParkingplaceMapping;
import com.iemr.hwc.data.report.ChiefComplaintReport;
import com.iemr.hwc.data.report.ConsultationReport;
import com.iemr.hwc.data.report.ReportInput;
import com.iemr.hwc.data.report.SpokeReport;
import com.iemr.hwc.data.report.TMDailyReport;
import com.iemr.hwc.repo.login.UserParkingplaceMappingRepo;
import com.iemr.hwc.repo.report.BenChiefComplaintReportRepo;
import com.iemr.hwc.utils.exception.TMException;

@ExtendWith(MockitoExtension.class)
class CRMReportServiceImplTest {
	@InjectMocks
	private CRMReportServiceImpl cRMReportServiceImpl;

	@Mock
	private BenChiefComplaintReportRepo benChiefComplaintReportRepo;

	@Mock
	private UserParkingplaceMappingRepo userParkingplaceMappingRepo;

	@Test
	void testGetParkingplaceID() throws TMException {
		// Arrange
		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(3);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(1);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);

		// Act
		Integer actualParkingplaceID = cRMReportServiceImpl.getParkingplaceID(1, 1);

		// Assert
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
		assertEquals(1, actualParkingplaceID.intValue());
	}

	@Test
	void testGetParkingplaceID2() throws TMException {
		// Arrange
		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(3);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(null);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);

		// Act and Assert
		assertThrows(TMException.class, () -> cRMReportServiceImpl.getParkingplaceID(1, 1));
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
	}

	@Test
	void testGetParkingplaceID3() throws TMException {
		// Arrange
		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(3);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(1);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);

		// Act
		Integer actualParkingplaceID = cRMReportServiceImpl.getParkingplaceID(2, 1);

		// Assert
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
		assertEquals(1, actualParkingplaceID.intValue());
	}

	@Test
	void testGetParkingplaceID4() throws TMException {
		// Arrange
		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(3);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(1);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);

		// Act
		Integer actualParkingplaceID = cRMReportServiceImpl.getParkingplaceID(3, 1);

		// Assert
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
		assertEquals(1, actualParkingplaceID.intValue());
	}

	@Test
	void testGetParkingplaceID5() throws TMException {
		// Arrange
		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(3);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(1);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);

		// Act
		Integer actualParkingplaceID = cRMReportServiceImpl.getParkingplaceID(4, 1);

		// Assert
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
		assertEquals(1, actualParkingplaceID.intValue());
	}

	@Test
	void testGetBenChiefComplaintReportObj() {
		// Diffblue Cover was unable to create a Spring-specific test for this Spring
		// method.

		// Arrange
		BigInteger valueOfResult = BigInteger.valueOf(1L);
		BigInteger valueOfResult2 = BigInteger.valueOf(1L);
		BigInteger valueOfResult3 = BigInteger.valueOf(1L);

		// Act
		ChiefComplaintReport actualBenChiefComplaintReportObj = CRMReportServiceImpl
				.getBenChiefComplaintReportObj(new Object[] { 1, "foo", "42", "42", "42", "42", valueOfResult,
						valueOfResult2, valueOfResult3, BigInteger.valueOf(1L) });

		// Assert
		BigInteger transgender = actualBenChiefComplaintReportObj.getTransgender();
		assertEquals("1", transgender.toString());
		assertSame(transgender, actualBenChiefComplaintReportObj.getMale());
	}

	@Test
	void testCalculateTime() {
		// Arrange
		Timestamp consultedTime = mock(Timestamp.class);
		when(consultedTime.getTime()).thenReturn(10L);
		Timestamp arrivalTime = mock(Timestamp.class);
		when(arrivalTime.getTime()).thenReturn(10L);

		// Act
		String actualCalculateTimeResult = CRMReportServiceImpl.calculateTime(consultedTime, arrivalTime);

		// Assert
		verify(consultedTime).getTime();
		verify(arrivalTime).getTime();
		assertEquals("", actualCalculateTimeResult);
	}

	@Test
	void testCalculateTime2() {
		// Arrange
		Timestamp consultedTime = mock(Timestamp.class);
		when(consultedTime.getTime()).thenReturn(Long.MAX_VALUE);
		Timestamp arrivalTime = mock(Timestamp.class);
		when(arrivalTime.getTime()).thenReturn(10L);

		// Act
		String actualCalculateTimeResult = CRMReportServiceImpl.calculateTime(consultedTime, arrivalTime);

		// Assert
		verify(consultedTime).getTime();
		verify(arrivalTime).getTime();
		assertEquals("2562047788015 hrs12 min", actualCalculateTimeResult);
	}

	@Test
	void testCalculateTime3() {
		// Arrange
		Timestamp consultedTime = mock(Timestamp.class);
		when(consultedTime.getTime()).thenReturn(10L);
		Timestamp arrivalTime = mock(Timestamp.class);
		when(arrivalTime.getTime()).thenReturn(Long.MAX_VALUE);

		// Act
		String actualCalculateTimeResult = CRMReportServiceImpl.calculateTime(consultedTime, arrivalTime);

		// Assert
		verify(consultedTime).getTime();
		verify(arrivalTime).getTime();
		assertEquals("No Waiting", actualCalculateTimeResult);
	}

	@Test
	void testCalculateTime4() {
		// Arrange
		Timestamp consultedTime = mock(Timestamp.class);
		when(consultedTime.getTime()).thenReturn(1000L);
		Timestamp arrivalTime = mock(Timestamp.class);
		when(arrivalTime.getTime()).thenReturn(-1L);

		// Act
		String actualCalculateTimeResult = CRMReportServiceImpl.calculateTime(consultedTime, arrivalTime);

		// Assert
		verify(consultedTime).getTime();
		verify(arrivalTime).getTime();
		assertEquals("1 sec", actualCalculateTimeResult);
	}

	@Test
	void testGetConsultationReportObj() {
		// Diffblue Cover was unable to create a Spring-specific test for this Spring
		// method.

		// Arrange and Act
		ConsultationReport actualConsultationReportObj = CRMReportServiceImpl.getConsultationReportObj(
				new Object[] { "42", "42", "42", "foo", "42", "42", "42", "42", "foo", "42", "42", "foo",
						mock(Timestamp.class), "42", null, "42", mock(Timestamp.class), mock(Timestamp.class), "42",
						"42", "42", "42", "42", mock(Timestamp.class), mock(Timestamp.class), null, null });

		// Assert
		assertEquals("42", actualConsultationReportObj.getBeneficiaryRegID());
		assertEquals("42", actualConsultationReportObj.getSpecialistId());
		assertEquals("42", actualConsultationReportObj.getSpecializationID());
		assertEquals("42", actualConsultationReportObj.getVisitCode());
		assertEquals("NO", actualConsultationReportObj.getConsulted());
		assertEquals("foo", actualConsultationReportObj.getBeneficiaryName());
		assertEquals("foo", actualConsultationReportObj.getSpecialistName());
		assertEquals("foo", actualConsultationReportObj.getSpecialization());
	}

	@Test
	void testGetConsultationReportObj2() {

		// Arrange and Act
		ConsultationReport actualConsultationReportObj = CRMReportServiceImpl.getConsultationReportObj(
				new Object[] { "42", "42", "42", "foo", "42", "42", "42", "42", "foo", "42", "42", "foo",
						mock(Timestamp.class), "42", null, "42", mock(Timestamp.class), mock(Timestamp.class), "42",
						"42", "42", "42", "42", mock(Timestamp.class), mock(Timestamp.class), null, "42" });

		// Assert
		assertEquals("42", actualConsultationReportObj.getBeneficiaryRegID());
		assertEquals("42", actualConsultationReportObj.getSpecialistId());
		assertEquals("42", actualConsultationReportObj.getSpecializationID());
		assertEquals("42", actualConsultationReportObj.getVisitCode());
		assertEquals("NO", actualConsultationReportObj.getConsulted());
		assertEquals("foo", actualConsultationReportObj.getBeneficiaryName());
		assertEquals("foo", actualConsultationReportObj.getSpecialistName());
		assertEquals("foo", actualConsultationReportObj.getSpecialization());
	}

	@Test
	void testGetConsultationReportObj3() {

		// Arrange and Act
		ConsultationReport actualConsultationReportObj = CRMReportServiceImpl.getConsultationReportObj(
				new Object[] { "42", "42", "42", "foo", "42", "42", "42", "42", "foo", "42", "42", "foo",
						mock(Timestamp.class), "42", null, "42", mock(Timestamp.class), mock(Timestamp.class), "42",
						"42", "42", "42", "42", mock(Timestamp.class), mock(Timestamp.class), "42", null });

		// Assert
		assertEquals("42", actualConsultationReportObj.getBeneficiaryRegID());
		assertEquals("42", actualConsultationReportObj.getSpecialistId());
		assertEquals("42", actualConsultationReportObj.getSpecializationID());
		assertEquals("42", actualConsultationReportObj.getVisitCode());
		assertEquals("NO", actualConsultationReportObj.getConsulted());
		assertEquals("foo", actualConsultationReportObj.getBeneficiaryName());
		assertEquals("foo", actualConsultationReportObj.getSpecialistName());
		assertEquals("foo", actualConsultationReportObj.getSpecialization());
	}

	@Test
	void testGetConsultationReportObj4() {
		ConsultationReport actualConsultationReportObj = CRMReportServiceImpl.getConsultationReportObj(
				new Object[] { "42", "42", "42", "foo", "42", "42", "42", "42", "foo", "42", "42", "foo",
						mock(Timestamp.class), "42", "D", "42", mock(Timestamp.class), mock(Timestamp.class), "42",
						"42", "42", "42", "42", mock(Timestamp.class), mock(Timestamp.class), null, null });

		// Assert
		assertEquals("42", actualConsultationReportObj.getBeneficiaryRegID());
		assertEquals("42", actualConsultationReportObj.getSpecialistId());
		assertEquals("42", actualConsultationReportObj.getSpecializationID());
		assertEquals("42", actualConsultationReportObj.getVisitCode());
		assertEquals("YES", actualConsultationReportObj.getConsulted());
		assertEquals("foo", actualConsultationReportObj.getBeneficiaryName());
		assertEquals("foo", actualConsultationReportObj.getSpecialistName());
		assertEquals("foo", actualConsultationReportObj.getSpecialization());
	}

	@Test
	void testGetConsultationReportObj5() {
		ConsultationReport actualConsultationReportObj = CRMReportServiceImpl.getConsultationReportObj(
				new Object[] { "42", "42", "42", "foo", "42", "42", "42", "42", "foo", "42", "42", "foo",
						mock(Timestamp.class), "42", "42", "42", mock(Timestamp.class), mock(Timestamp.class), "42",
						"42", "42", "42", "42", mock(Timestamp.class), mock(Timestamp.class), null, null });

		// Assert
		assertEquals("42", actualConsultationReportObj.getBeneficiaryRegID());
		assertEquals("42", actualConsultationReportObj.getSpecialistId());
		assertEquals("42", actualConsultationReportObj.getSpecializationID());
		assertEquals("42", actualConsultationReportObj.getVisitCode());
		assertEquals("NO", actualConsultationReportObj.getConsulted());
		assertEquals("foo", actualConsultationReportObj.getBeneficiaryName());
		assertEquals("foo", actualConsultationReportObj.getSpecialistName());
		assertEquals("foo", actualConsultationReportObj.getSpecialization());
	}

	@Test
	void testGetSpokeReportObj() {
		SpokeReport actualSpokeReportObj = CRMReportServiceImpl
				.getSpokeReportObj(new Object[] { "42", "42", 1, "foo" });

		// Assert
		assertEquals("foo", actualSpokeReportObj.getVanName());
		assertEquals(1, actualSpokeReportObj.getVanID().intValue());
	}

	@Test
	void testGetSpokeReportObj1() {
		// Arrange and Act
		SpokeReport actualSpokeReportObj1 = CRMReportServiceImpl.getSpokeReportObj1(new Object[] { 1, "Obj" });

		// Assert
		assertEquals("Obj", actualSpokeReportObj1.getVanName());
		assertEquals(1, actualSpokeReportObj1.getVanID().intValue());
	}

	@Test
	void testGetTMDailyReportObj() {
		TMDailyReport actualTMDailyReportObj = CRMReportServiceImpl
				.getTMDailyReportObj(new Object[] { "42", "foo", null, null, null, null });

		// Assert
		BigInteger cumulativeConsultationsForMonth = actualTMDailyReportObj.getCumulativeConsultationsForMonth();
		assertEquals("0", cumulativeConsultationsForMonth.toString());
		BigInteger cumulativeRevisitConsultationsForMonth = actualTMDailyReportObj
				.getCumulativeRevisitConsultationsForMonth();
		assertEquals("0", cumulativeRevisitConsultationsForMonth.toString());
		BigInteger currentConsultations = actualTMDailyReportObj.getCurrentConsultations();
		assertEquals("0", currentConsultations.toString());
		BigInteger revisitConsultations = actualTMDailyReportObj.getRevisitConsultations();
		assertEquals("0", revisitConsultations.toString());
		assertEquals("foo", actualTMDailyReportObj.getSpokeName());
		assertArrayEquals(new byte[] { 0 }, cumulativeConsultationsForMonth.toByteArray());
		assertArrayEquals(new byte[] { 0 }, cumulativeRevisitConsultationsForMonth.toByteArray());
		assertArrayEquals(new byte[] { 0 }, currentConsultations.toByteArray());
		assertArrayEquals(new byte[] { 0 }, revisitConsultations.toByteArray());
	}

	@Test
	void testGetTMDailyReportObj2() {
		BigInteger valueOfResult = BigInteger.valueOf(1L);

		// Act
		TMDailyReport actualTMDailyReportObj = CRMReportServiceImpl
				.getTMDailyReportObj(new Object[] { "42", "foo", valueOfResult, null, null, null });

		// Assert
		BigInteger cumulativeConsultationsForMonth = actualTMDailyReportObj.getCumulativeConsultationsForMonth();
		assertEquals("0", cumulativeConsultationsForMonth.toString());
		BigInteger cumulativeRevisitConsultationsForMonth = actualTMDailyReportObj
				.getCumulativeRevisitConsultationsForMonth();
		assertEquals("0", cumulativeRevisitConsultationsForMonth.toString());
		BigInteger revisitConsultations = actualTMDailyReportObj.getRevisitConsultations();
		assertEquals("0", revisitConsultations.toString());
		BigInteger currentConsultations = actualTMDailyReportObj.getCurrentConsultations();
		assertEquals("1", currentConsultations.toString());
		assertEquals("foo", actualTMDailyReportObj.getSpokeName());
		BigInteger bigInteger = valueOfResult.ZERO;
		assertEquals(bigInteger, cumulativeConsultationsForMonth);
		assertEquals(bigInteger, cumulativeRevisitConsultationsForMonth);
		assertEquals(bigInteger, revisitConsultations);
		assertArrayEquals(new byte[] { 1 }, currentConsultations.toByteArray());
	}

	@Test
	void testGetChiefcomplaintreport() throws TMException {
		// Arrange
		when(benChiefComplaintReportRepo.getcmreport(Mockito.<Date>any(), Mockito.<Date>any(), Mockito.<Integer>any()))
				.thenReturn(new ArrayList<>());

		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(1);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(1);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);

		ReportInput input = new ReportInput();
		input.setFromDate(mock(Date.class));
		input.setParkingPlaceID(1);
		input.setProviderServiceMapID(1);
		input.setToDate(mock(Date.class));
		input.setUserID(1);
		input.setVanID(1);

		// Act
		Set<SpokeReport> actualChiefcomplaintreport = cRMReportServiceImpl.getChiefcomplaintreport(input);

		// Assert
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
		verify(benChiefComplaintReportRepo).getcmreport(isA(Date.class), isA(Date.class), isA(Integer.class));
		assertTrue(actualChiefcomplaintreport.isEmpty());
	}

	@Test
	void testGetChiefcomplaintreport2() throws TMException {
		// Arrange
		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(1);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(null);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);

		ReportInput input = new ReportInput();
		input.setFromDate(mock(Date.class));
		input.setParkingPlaceID(1);
		input.setProviderServiceMapID(1);
		input.setToDate(mock(Date.class));
		input.setUserID(1);
		input.setVanID(1);

		// Act and Assert
		assertThrows(TMException.class, () -> cRMReportServiceImpl.getChiefcomplaintreport(input));
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
	}

	@Test
	void testGetConsultationReport() throws TMException {
		// Arrange
		when(benChiefComplaintReportRepo.getConsultationReport(Mockito.<Date>any(), Mockito.<Date>any(),
				Mockito.<Integer>any())).thenReturn(new ArrayList<>());

		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(1);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(1);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);

		ReportInput input = new ReportInput();
		input.setFromDate(mock(Date.class));
		input.setParkingPlaceID(1);
		input.setProviderServiceMapID(1);
		input.setToDate(mock(Date.class));
		input.setUserID(1);
		input.setVanID(1);

		// Act
		List<ConsultationReport> actualConsultationReport = cRMReportServiceImpl.getConsultationReport(input);

		// Assert
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
		verify(benChiefComplaintReportRepo).getConsultationReport(isA(Date.class), isA(Date.class), isA(Integer.class));
		assertTrue(actualConsultationReport.isEmpty());
	}

	@Test
	void testGetConsultationReport2() throws TMException {
		// Arrange
		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(1);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(null);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);

		ReportInput input = new ReportInput();
		input.setFromDate(mock(Date.class));
		input.setParkingPlaceID(1);
		input.setProviderServiceMapID(1);
		input.setToDate(mock(Date.class));
		input.setUserID(1);
		input.setVanID(1);

		// Act and Assert
		assertThrows(TMException.class, () -> cRMReportServiceImpl.getConsultationReport(input));
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
	}

	@Test
	void testGetTotalConsultationReport() throws TMException {
		// Arrange
		when(benChiefComplaintReportRepo.getTotalConsultationReport(Mockito.<Date>any(), Mockito.<Date>any(),
				Mockito.<Integer>any())).thenReturn(new ArrayList<>());

		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(1);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(1);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);
		Date fromDate = mock(Date.class);
		when(fromDate.compareTo(Mockito.<java.util.Date>any())).thenReturn(1);

		ReportInput input = new ReportInput();
		input.setFromDate(fromDate);
		input.setParkingPlaceID(1);
		input.setProviderServiceMapID(1);
		input.setToDate(mock(java.sql.Date.class));
		input.setUserID(1);
		input.setVanID(1);

		// Act
		String actualTotalConsultationReport = cRMReportServiceImpl.getTotalConsultationReport(input);

		// Assert
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
		verify(benChiefComplaintReportRepo).getTotalConsultationReport(isA(java.sql.Date.class),
				isA(java.sql.Date.class), isA(Integer.class));
		verify(fromDate).compareTo(isA(java.util.Date.class));
		assertTrue(
				cRMReportServiceImpl.mapper.getSerializerProviderInstance() instanceof DefaultSerializerProvider.Impl);
		assertEquals("[]", actualTotalConsultationReport);
	}

	@Test
	void testGetTotalConsultationReport2() throws TMException {
		// Arrange
		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(1);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(null);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);

		ReportInput input = new ReportInput();
		input.setFromDate(mock(Date.class));
		input.setParkingPlaceID(1);
		input.setProviderServiceMapID(1);
		input.setToDate(mock(Date.class));
		input.setUserID(1);
		input.setVanID(1);

		// Act and Assert
		assertThrows(TMException.class, () -> cRMReportServiceImpl.getTotalConsultationReport(input));
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
	}

	@Test
	void testGetMonthlyReport() throws TMException {
		// Arrange
		when(benChiefComplaintReportRepo.getMonthlyReport(Mockito.<Date>any(), Mockito.<Date>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(new ArrayList<>());

		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(1);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(1);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);
		Date fromDate = mock(Date.class);
		when(fromDate.compareTo(Mockito.<java.util.Date>any())).thenReturn(1);

		ReportInput input = new ReportInput();
		input.setFromDate(fromDate);
		input.setParkingPlaceID(1);
		input.setProviderServiceMapID(1);
		input.setToDate(mock(java.sql.Date.class));
		input.setUserID(1);
		input.setVanID(1);

		// Act
		String actualMonthlyReport = cRMReportServiceImpl.getMonthlyReport(input);

		// Assert
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
		verify(benChiefComplaintReportRepo).getMonthlyReport(isA(java.sql.Date.class), isA(java.sql.Date.class),
				isA(Integer.class), isA(Integer.class));
		verify(fromDate).compareTo(isA(java.util.Date.class));
		assertTrue(
				cRMReportServiceImpl.mapper.getSerializerProviderInstance() instanceof DefaultSerializerProvider.Impl);
		assertEquals("[]", actualMonthlyReport);
	}

	@Test
	void testGetMonthlyReport2() throws TMException {
		// Arrange
		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(1);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(null);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);

		ReportInput input = new ReportInput();
		input.setFromDate(mock(Date.class));
		input.setParkingPlaceID(1);
		input.setProviderServiceMapID(1);
		input.setToDate(mock(Date.class));
		input.setUserID(1);
		input.setVanID(1);

		// Act and Assert
		assertThrows(TMException.class, () -> cRMReportServiceImpl.getMonthlyReport(input));
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
	}

	@Test
	void testGetMonthlyReport3() throws TMException {
		// Arrange
		when(benChiefComplaintReportRepo.getMonthlyReport(Mockito.<Date>any(), Mockito.<Date>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(new ArrayList<>());

		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(1);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(1);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);
		Date fromDate = mock(Date.class);
		when(fromDate.compareTo(Mockito.<java.util.Date>any())).thenReturn(1);
		java.sql.Date date = mock(java.sql.Date.class);
		when(date.compareTo(Mockito.<java.util.Date>any())).thenReturn(1);
		ReportInput input = mock(ReportInput.class);
		when(input.getProviderServiceMapID()).thenReturn(1);
		when(input.getUserID()).thenReturn(1);
		when(input.getVanID()).thenReturn(0);
		when(input.getFromDate()).thenReturn(date);
		when(input.getToDate()).thenReturn(mock(java.sql.Date.class));
		doNothing().when(input).setFromDate(Mockito.<java.sql.Date>any());
		doNothing().when(input).setParkingPlaceID(Mockito.<Integer>any());
		doNothing().when(input).setProviderServiceMapID(Mockito.<Integer>any());
		doNothing().when(input).setToDate(Mockito.<java.sql.Date>any());
		doNothing().when(input).setUserID(Mockito.<Integer>any());
		doNothing().when(input).setVanID(Mockito.<Integer>any());
		input.setFromDate(fromDate);
		input.setParkingPlaceID(1);
		input.setProviderServiceMapID(1);
		input.setToDate(mock(java.sql.Date.class));
		input.setUserID(1);
		input.setVanID(1);

		// Act
		String actualMonthlyReport = cRMReportServiceImpl.getMonthlyReport(input);

		// Assert
		verify(input, atLeast(1)).getFromDate();
		verify(input).getProviderServiceMapID();
		verify(input, atLeast(1)).getToDate();
		verify(input).getUserID();
		verify(input).getVanID();
		verify(input).setFromDate(isA(java.sql.Date.class));
		verify(input).setParkingPlaceID(isA(Integer.class));
		verify(input).setProviderServiceMapID(isA(Integer.class));
		verify(input).setToDate(isA(java.sql.Date.class));
		verify(input).setUserID(isA(Integer.class));
		verify(input).setVanID(isA(Integer.class));
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
		verify(benChiefComplaintReportRepo).getMonthlyReport(isA(java.sql.Date.class), isA(java.sql.Date.class),
				isA(Integer.class), isNull());
		verify(date).compareTo(isA(java.util.Date.class));
		assertEquals("[]", actualMonthlyReport);
	}

	@Test
	void testGetDailyReport() throws TMException {
		// Arrange
		when(benChiefComplaintReportRepo.getDailyReport(Mockito.<Date>any(), Mockito.<Integer>any()))
				.thenReturn(new ArrayList<>());

		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(1);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(1);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);

		ReportInput input = new ReportInput();
		input.setFromDate(mock(Date.class));
		input.setParkingPlaceID(1);
		input.setProviderServiceMapID(1);
		input.setToDate(mock(Date.class));
		input.setUserID(1);
		input.setVanID(1);

		// Act
		List<TMDailyReport> actualDailyReport = cRMReportServiceImpl.getDailyReport(input);

		// Assert
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
		verify(benChiefComplaintReportRepo).getDailyReport(isA(Date.class), isA(Integer.class));
		assertTrue(actualDailyReport.isEmpty());
	}

	@Test
	void testGetDailyReport2() throws TMException {
		// Arrange
		UserParkingplaceMapping userParkingplaceMapping = new UserParkingplaceMapping();
		userParkingplaceMapping.setDeleted(1);
		userParkingplaceMapping.setDistrictID(1);
		userParkingplaceMapping.setParkingPlaceID(null);
		userParkingplaceMapping.setProviderServiceMapId(1);
		userParkingplaceMapping.setStateID(1);
		userParkingplaceMapping.setUserID(1);
		userParkingplaceMapping.setUserParkingPlaceMapID(1);
		when(userParkingplaceMappingRepo.findOneByUserIDAndProviderServiceMapIdAndDeleted(Mockito.<Integer>any(),
				Mockito.<Integer>any(), Mockito.<Integer>any())).thenReturn(userParkingplaceMapping);

		ReportInput input = new ReportInput();
		input.setFromDate(mock(Date.class));
		input.setParkingPlaceID(1);
		input.setProviderServiceMapID(1);
		input.setToDate(mock(Date.class));
		input.setUserID(1);
		input.setVanID(1);

		// Act and Assert
		assertThrows(TMException.class, () -> cRMReportServiceImpl.getDailyReport(input));
		verify(userParkingplaceMappingRepo).findOneByUserIDAndProviderServiceMapIdAndDeleted(isA(Integer.class),
				isA(Integer.class), isA(Integer.class));
	}
}
