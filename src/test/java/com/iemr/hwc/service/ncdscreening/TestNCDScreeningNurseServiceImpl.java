package com.iemr.hwc.service.ncdscreening;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.Gson;
import com.iemr.hwc.data.ncdScreening.NCDScreening;
import com.iemr.hwc.repo.nurse.ncdscreening.NCDScreeningRepo;

public class TestNCDScreeningNurseServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private NCDScreeningRepo ncdScreeningRepo;

	@InjectMocks
	private NCDScreeningNurseServiceImpl ncdScreeningNurseService;

	private NCDScreening ncdScreening;
	private ArrayList<Map<String, Object>> ncdScreeningConditionList;
	private Long beneficiaryRegID;
	private Long visitCode;

	@BeforeEach
	public void setUp() {
		ncdScreening = new NCDScreening();
		ncdScreeningConditionList = new ArrayList<>();
		Map<String, Object> condition = new HashMap<>();
		condition.put("ncdScreeningConditionID", "1");
		condition.put("screeningCondition", "Condition1");
		ncdScreeningConditionList.add(condition);
		ncdScreening.setNcdScreeningConditionList(ncdScreeningConditionList);
	}

	@Test
	public void testSaveNCDScreeningDetails() {
		when(ncdScreeningRepo.save(any(NCDScreening.class))).thenReturn(ncdScreening);

		Long result = ncdScreeningNurseService.saveNCDScreeningDetails(ncdScreening);

		assertNotNull(result);
		verify(ncdScreeningRepo, times(1)).save(any(NCDScreening.class));
	}

	@Test
	public void testGetNCDScreeningDetails() {
		ncdScreening.setNcdScreeningConditionID("1");
		ncdScreening.setScreeningCondition("Condition1");
		ncdScreening.setNextScreeningDateDB(Date.valueOf("2023-10-10"));
		when(ncdScreeningRepo.getNCDScreeningDetails(anyLong(), anyLong())).thenReturn(ncdScreening);

		String result = ncdScreeningNurseService.getNCDScreeningDetails(1L, 1L);

		assertNotNull(result);
		NCDScreening resultScreening = new Gson().fromJson(result, NCDScreening.class);
		assertEquals("1", resultScreening.getNcdScreeningConditionID());
		assertEquals("Condition1", resultScreening.getScreeningCondition());
		assertEquals("2023-10-10", resultScreening.getNextScreeningDate());
		verify(ncdScreeningRepo, times(1)).getNCDScreeningDetails(anyLong(), anyLong());
	}

	@Test
	public void testUpdateNCDScreeningDetails() {
		when(ncdScreeningRepo.updateNCDScreeningDetails(anyString(), anyString(), anyLong(), anyString(),
				any(Date.class), anyString(), anyBoolean(), anyString(), anyLong(), anyLong(), anyBoolean(),
				anyBoolean())).thenReturn(1);

		Integer result = ncdScreeningNurseService.updateNCDScreeningDetails(ncdScreening);

		assertEquals(1, result);
		verify(ncdScreeningRepo, times(1)).updateNCDScreeningDetails(anyString(), anyString(), anyLong(), anyString(),
				any(Date.class), anyString(), anyBoolean(), anyString(), anyLong(), anyLong(), anyBoolean(),
				anyBoolean());
	}

	@Test
	public void testGetNCDScreeningDetails() {
		when(ncdScreeningRepo.getNCDScreeningDetails(beneficiaryRegID, visitCode)).thenReturn(ncdScreening);

		String response = ncdScreeningNurseService.getNCDScreeningDetails(beneficiaryRegID, visitCode);

		NCDScreening expectedScreening = new NCDScreening();
		expectedScreening.setNcdScreeningConditionID("1,2");
		expectedScreening.setScreeningCondition("Condition1,Condition2");
		expectedScreening.setNextScreeningDate("2023-10-01");

		ArrayList<Map<String, Object>> screeningConditionList = new ArrayList<>();
		Map<String, Object> condition1 = new HashMap<>();
		condition1.put("ncdScreeningConditionID", "1");
		condition1.put("screeningCondition", "Condition1");
		screeningConditionList.add(condition1);

		Map<String, Object> condition2 = new HashMap<>();
		condition2.put("ncdScreeningConditionID", "2");
		condition2.put("screeningCondition", "Condition2");
		screeningConditionList.add(condition2);

		expectedScreening.setNcdScreeningConditionList(screeningConditionList);

		String expectedResponse = new Gson().toJson(expectedScreening);

		assertEquals(expectedResponse, response);
		verify(ncdScreeningRepo, times(1)).getNCDScreeningDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetNCDScreeningDetails_NoConditions() {
		ncdScreening.setNcdScreeningConditionID(null);
		ncdScreening.setScreeningCondition(null);

		when(ncdScreeningRepo.getNCDScreeningDetails(beneficiaryRegID, visitCode)).thenReturn(ncdScreening);

		String response = ncdScreeningNurseService.getNCDScreeningDetails(beneficiaryRegID, visitCode);

		NCDScreening expectedScreening = new NCDScreening();
		expectedScreening.setNextScreeningDate("2023-10-01");

		String expectedResponse = new Gson().toJson(expectedScreening);

		assertEquals(expectedResponse, response);
		verify(ncdScreeningRepo, times(1)).getNCDScreeningDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testUpdateNCDScreeningDetails_Success() {
		when(ncdScreeningRepo.updateNCDScreeningDetails(anyString(), anyString(), anyLong(), anyString(),
				any(Date.class), anyString(), anyBoolean(), anyLong(), anyLong(), anyBoolean(), anyBoolean()))
				.thenReturn(1);

		Integer result = ncdScreeningNurseService.updateNCDScreeningDetails(ncdScreening);

		assertEquals(1, result);
		verify(ncdScreeningRepo, times(1)).updateNCDScreeningDetails(anyString(), anyString(), anyLong(), anyString(),
				any(Date.class), anyString(), anyBoolean(), anyLong(), anyLong(), anyBoolean(), anyBoolean());
	}

	@Test
	public void testUpdateNCDScreeningDetails_NullValues() {
		ncdScreening.setNcdScreeningConditionList(null);
		when(ncdScreeningRepo.updateNCDScreeningDetails(anyString(), anyString(), anyLong(), anyString(),
				any(Date.class), anyString(), anyBoolean(), anyLong(), anyLong(), anyBoolean(), anyBoolean()))
				.thenReturn(1);

		Integer result = ncdScreeningNurseService.updateNCDScreeningDetails(ncdScreening);

		assertEquals(1, result);
		verify(ncdScreeningRepo, times(1)).updateNCDScreeningDetails(anyString(), anyString(), anyLong(), anyString(),
				any(Date.class), anyString(), anyBoolean(), anyLong(), anyLong(), anyBoolean(), anyBoolean());
	}

	@Test
	public void testUpdateNCDScreeningDetails_EmptyValues() {
		ncdScreening.setNcdScreeningConditionList(new ArrayList<>());
		when(ncdScreeningRepo.updateNCDScreeningDetails(anyString(), anyString(), anyLong(), anyString(),
				any(Date.class), anyString(), anyBoolean(), anyLong(), anyLong(), anyBoolean(), anyBoolean()))
				.thenReturn(1);

		Integer result = ncdScreeningNurseService.updateNCDScreeningDetails(ncdScreening);

		assertEquals(1, result);
		verify(ncdScreeningRepo, times(1)).updateNCDScreeningDetails(anyString(), anyString(), anyLong(), anyString(),
				any(Date.class), anyString(), anyBoolean(), anyLong(), anyLong(), anyBoolean(), anyBoolean());
	}
}
