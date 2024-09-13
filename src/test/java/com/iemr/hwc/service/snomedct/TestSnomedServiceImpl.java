package com.iemr.hwc.service.snomedct;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.iemr.hwc.data.snomedct.SCTDescription;
import com.iemr.hwc.repo.snomedct.SnomedRepository;
import com.iemr.hwc.utils.mapper.OutputMapper;

import net.sourceforge.plantuml.sequencediagram.graphic.Page;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

public class TestSnomedServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private SnomedRepository snomedRepository;

	@InjectMocks
	private SnomedServiceImpl snomedService;

	private List<Object[]> mockRecords;
	private SCTDescription sctDescription;
	private Page<SCTDescription> sctPage;

	@BeforeEach
	public void setUp() {
		mockRecords = new ArrayList<>();
		Object[] record = new Object[] { "id", "term", "description" };
		mockRecords.add(record);
	}

	@Test
	public void testFindSnomedCTRecordFromTerm() {
		String term = "testTerm";
		when(snomedRepository.findSnomedCTRecordFromTerm(term)).thenReturn(mockRecords);

		SCTDescription result = snomedService.findSnomedCTRecordFromTerm(term);

		assertEquals("id", result.getId());
		assertEquals("term", result.getTerm());
		assertEquals("description", result.getDescription());
		verify(snomedRepository, times(1)).findSnomedCTRecordFromTerm(term);
	}

	@Test
	public void testFindSnomedCTRecordList_Success() throws Exception {
		when(snomedRepository.findSnomedCTRecordList(eq("testTerm"), any(PageRequest.class))).thenReturn(sctPage);

		String result = snomedService.findSnomedCTRecordList(sctDescription);

		Map<String, Object> expectedDataMap = new HashMap<>();
		expectedDataMap.put("sctMaster", sctPage.getContent());
		expectedDataMap.put("pageCount", sctPage.getTotalPages());

		String expectedJson = OutputMapper.gson().toJson(expectedDataMap);
		assertEquals(expectedJson, result);
		verify(snomedRepository, times(1)).findSnomedCTRecordList(eq("testTerm"), any(PageRequest.class));
	}

	@Test
	public void testFindSnomedCTRecordList_InvalidRequest() {
		SCTDescription invalidDescription = new SCTDescription();
		Exception exception = assertThrows(Exception.class, () -> {
			snomedService.findSnomedCTRecordList(invalidDescription);
		});

		String expectedMessage = "invalid request";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testFindSnomedCTRecordList_NullTerm() {
		sctDescription.setTerm(null);
		Exception exception = assertThrows(Exception.class, () -> {
			snomedService.findSnomedCTRecordList(sctDescription);
		});

		String expectedMessage = "invalid request";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testFindSnomedCTRecordList_NullPageNo() {
		sctDescription.setPageNo(null);
		Exception exception = assertThrows(Exception.class, () -> {
			snomedService.findSnomedCTRecordList(sctDescription);
		});

		String expectedMessage = "invalid request";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
}
