package com.iemr.hwc.service.pnc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.hwc.data.pnc.PNCCare;
import com.iemr.hwc.repo.nurse.pnc.PNCCareRepo;

public class TestPNCNurseServiceImpl {

	@ExtendWith(MockitoExtension.class)
	public class PNCNurseServiceImplTest {

		@Mock
		private PNCCareRepo pncCareRepo;

		@InjectMocks
		private PNCNurseServiceImpl pncNurseServiceImpl;

		private PNCCare pncCare;
		private PNCNurseServiceImpl pncNurseService;
		private PNCCare pncCareDetailsOBJ;

		@BeforeEach
		public void setUp() {
			pncCare = new PNCCare();
			pncCare.setdDate("2023-10-01T00:00:00");
			pncCare.setID(1L);
		}

		@Test
		public void testSaveBenPncCareDetails_Success() throws ParseException {
			when(pncCareRepo.save(any(PNCCare.class))).thenReturn(pncCare);

			Long result = pncNurseServiceImpl.saveBenPncCareDetails(pncCare);

			assertNotNull(result);
			assertEquals(1L, result);
			verify(pncCareRepo, times(1)).save(any(PNCCare.class));
		}

		@Test
		public void testSaveBenPncCareDetails_DateParsingError() {
			pncCare.setdDate("invalid-date");

			assertThrows(ParseException.class, () -> {
				pncNurseServiceImpl.saveBenPncCareDetails(pncCare);
			});

			verify(pncCareRepo, times(0)).save(any(PNCCare.class));
		}

		@Test
		public void testSaveBenPncCareDetails_NullDate() throws ParseException {
			pncCare.setdDate(null);
			when(pncCareRepo.save(any(PNCCare.class))).thenReturn(pncCare);

			Long result = pncNurseServiceImpl.saveBenPncCareDetails(pncCare);

			assertNotNull(result);
			assertEquals(1L, result);
			verify(pncCareRepo, times(1)).save(any(PNCCare.class));
		}

		@Test
		public void testSaveBenPncCareDetails_EmptyDate() throws ParseException {
			pncCare.setdDate("");
			when(pncCareRepo.save(any(PNCCare.class))).thenReturn(pncCare);

			Long result = pncNurseServiceImpl.saveBenPncCareDetails(pncCare);

			assertNotNull(result);
			assertEquals(1L, result);
			verify(pncCareRepo, times(1)).save(any(PNCCare.class));
		}

		@Test
		public void testSaveBenPncCareDetails_NullResponse() throws ParseException {
			when(pncCareRepo.save(any(PNCCare.class))).thenReturn(null);

			Long result = pncNurseServiceImpl.saveBenPncCareDetails(pncCare);

			assertNull(result);
			verify(pncCareRepo, times(1)).save(any(PNCCare.class));
		}
	}

	@Test
	public void testGetPNCCareDetails() {
		when(pncCareRepo.getPNCCareDetailsByVisitCode(1L, 1L)).thenReturn(pncCare);

		String response = pncNurseService.getPNCCareDetails(1L, 1L);

		assertNotNull(response);
		assertTrue(response.contains("\"ID\":1"));
		assertTrue(response.contains("\"BeneficiaryRegID\":1"));
		assertTrue(response.contains("\"VisitCode\":1"));
		assertTrue(response.contains("\"DeliveryType\":\"Normal\""));
		// Add more assertions as needed

		verify(pncCareRepo, times(1)).getPNCCareDetailsByVisitCode(1L, 1L);
	}

	@Test
	public void testGetPNCCareDetails_NoData() {
		when(pncCareRepo.getPNCCareDetailsByVisitCode(1L, 1L)).thenReturn(null);

		String response = pncNurseService.getPNCCareDetails(1L, 1L);

		assertNotNull(response);
		assertEquals("null", response);

		verify(pncCareRepo, times(1)).getPNCCareDetailsByVisitCode(1L, 1L);
	}

	@Test
	public void testUpdateBenPNCCareDetails_RecordsAvailableProcessedNotN() throws ParseException {
		when(pncCareRepo.getBenPNCCareDetailsStatus(1L, 1L)).thenReturn("Y");
		when(pncNurseServiceImpl.updateBenPNCCare(any(PNCCare.class))).thenReturn(1);

		int result = pncNurseServiceImpl.updateBenPNCCareDetails(pncCareDetailsOBJ);

		assertEquals(1, result);
		verify(pncCareRepo, times(1)).getBenPNCCareDetailsStatus(1L, 1L);
		verify(pncNurseServiceImpl, times(1)).updateBenPNCCare(any(PNCCare.class));
	}

	@Test
	public void testUpdateBenPNCCareDetails_RecordsAvailableProcessedN() throws ParseException {
		when(pncCareRepo.getBenPNCCareDetailsStatus(1L, 1L)).thenReturn("N");
		when(pncNurseServiceImpl.updateBenPNCCare(any(PNCCare.class))).thenReturn(1);

		int result = pncNurseServiceImpl.updateBenPNCCareDetails(pncCareDetailsOBJ);

		assertEquals(1, result);
		verify(pncCareRepo, times(1)).getBenPNCCareDetailsStatus(1L, 1L);
		verify(pncNurseServiceImpl, times(1)).updateBenPNCCare(any(PNCCare.class));
	}

	@Test
	public void testUpdateBenPNCCareDetails_NoRecordsAvailable() throws ParseException {
		when(pncCareRepo.getBenPNCCareDetailsStatus(1L, 1L)).thenReturn(null);
		when(pncCareRepo.save(any(PNCCare.class))).thenReturn(1L);

		int result = pncNurseServiceImpl.updateBenPNCCareDetails(pncCareDetailsOBJ);

		assertEquals(1, result);
		verify(pncCareRepo, times(1)).getBenPNCCareDetailsStatus(1L, 1L);
		verify(pncCareRepo, times(1)).save(any(PNCCare.class));
	}

	@Test
	public void testUpdateBenPNCCare_Success() throws ParseException {
		when(pncCareRepo.updatePNCCareDetails(anyLong(), anyString(), anyLong(), anyString(), anyString(),
				any(Date.class), anyLong(), anyString(), anyString(), anyLong(), anyString(), anyLong(), anyString(),
				anyString(), anyLong(), anyString(), anyDouble(), anyLong(), anyString(), anyString(), anyString(),
				anyLong(), anyLong(), anyLong(), anyString())).thenReturn(1);

		int result = pncNurseService.updateBenPNCCare(pncCare);

		assertEquals(1, result);
		verify(pncCareRepo, times(1)).updatePNCCareDetails(anyLong(), anyString(), anyLong(), anyString(), anyString(),
				any(Date.class), anyLong(), anyString(), anyString(), anyLong(), anyString(), anyLong(), anyString(),
				anyString(), anyLong(), anyString(), anyDouble(), anyLong(), anyString(), anyString(), anyString(),
				anyLong(), anyLong(), anyLong(), anyString());
	}

	@Test
	public void testUpdateBenPNCCare_ParseException() {
		pncCare.setdDate("invalid-date");

		assertThrows(ParseException.class, () -> {
			pncNurseService.updateBenPNCCare(pncCare);
		});
	}

	@Test
	public void testUpdateBenPNCCare_NullOrEmptyDate() throws ParseException {
		pncCare.setdDate(null);

		when(pncCareRepo.updatePNCCareDetails(anyLong(), anyString(), anyLong(), anyString(), anyString(), isNull(),
				anyLong(), anyString(), anyString(), anyLong(), anyString(), anyLong(), anyString(), anyString(),
				anyLong(), anyString(), anyDouble(), anyLong(), anyString(), anyString(), anyString(), anyLong(),
				anyLong(), anyLong(), anyString())).thenReturn(1);

		int result = pncNurseService.updateBenPNCCare(pncCare);

		assertEquals(1, result);
		verify(pncCareRepo, times(1)).updatePNCCareDetails(anyLong(), anyString(), anyLong(), anyString(), anyString(),
				isNull(), anyLong(), anyString(), anyString(), anyLong(), anyString(), anyLong(), anyString(),
				anyString(), anyLong(), anyString(), anyDouble(), anyLong(), anyString(), anyString(), anyString(),
				anyLong(), anyLong(), anyLong(), anyString());
	}
}
