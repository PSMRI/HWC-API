package com.iemr.hwc.service.snomedct;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import com.iemr.hwc.data.snomedct.SCTDescription;
import com.iemr.hwc.repo.snomedct.SnomedRepository;

@ExtendWith(MockitoExtension.class)
class SnomedServiceImplTest {
	@Mock
	private SnomedRepository snomedRepository;

	@InjectMocks
	private SnomedServiceImpl snomedServiceImpl;
	
	private Integer snomedCTPageSize;

	@Test
	void testSetSnomedRepository() {
		(new SnomedServiceImpl()).setSnomedRepository(mock(SnomedRepository.class));
	}

	@Test
	void testFindSnomedCTRecordFromTerm() {
		SnomedRepository snomedRepository = mock(SnomedRepository.class);
		when(snomedRepository.findSnomedCTRecordFromTerm(Mockito.<String>any())).thenReturn(new ArrayList<>());

		SnomedServiceImpl snomedServiceImpl = new SnomedServiceImpl();
		snomedServiceImpl.setSnomedRepository(snomedRepository);
		SCTDescription actualFindSnomedCTRecordFromTermResult = snomedServiceImpl.findSnomedCTRecordFromTerm("Term");
		verify(snomedRepository).findSnomedCTRecordFromTerm(eq("Term"));
		assertNull(actualFindSnomedCTRecordFromTermResult);
	}

	@Test
	void testFindSnomedCTRecordFromTerm2() {
		ArrayList<Object[]> objectArrayList = new ArrayList<>();
		objectArrayList.add(new Object[] { "42", "42" });
		SnomedRepository snomedRepository = mock(SnomedRepository.class);
		when(snomedRepository.findSnomedCTRecordFromTerm(Mockito.<String>any())).thenReturn(objectArrayList);

		SnomedServiceImpl snomedServiceImpl = new SnomedServiceImpl();
		snomedServiceImpl.setSnomedRepository(snomedRepository);
		SCTDescription actualFindSnomedCTRecordFromTermResult = snomedServiceImpl.findSnomedCTRecordFromTerm("Term");
		verify(snomedRepository).findSnomedCTRecordFromTerm(eq("Term"));
		assertEquals("42", actualFindSnomedCTRecordFromTermResult.getConceptID());
		assertEquals("42", actualFindSnomedCTRecordFromTermResult.getTerm());
	}

	@Test
	void testFindSnomedCTRecordFromTerm3() {
		snomedServiceImpl.findSnomedCTRecordFromTerm("Term");
	}

	@Test
	public void testFindSnomedCTRecordList_Success() throws Exception {
		SCTDescription sctDescription = new SCTDescription();
		sctDescription.setTerm("sampleTerm");
		sctDescription.setPageNo(1);
		PageRequest pr = PageRequest.of(sctDescription.getPageNo(), snomedCTPageSize); 
		Page<SCTDescription> sctList = new PageImpl<>(new ArrayList<>()); 
		when(snomedRepository.findSnomedCTRecordList(sctDescription.getTerm(), pr)).thenReturn(sctList);
		Field field = SnomedServiceImpl.class.getDeclaredField("snomedCTPageSize");
		field.setAccessible(true);
		field.set(snomedServiceImpl, 10);
		Map<String, Object> dataMap = new HashMap<>();
		String jsonData = snomedServiceImpl.findSnomedCTRecordList(sctDescription);
		verify(snomedRepository).findSnomedCTRecordList(sctDescription.getTerm(), pr);
		assertEquals(dataMap.get("sctMaster"), sctList.getContent());
		assertEquals(dataMap.get("pageCount"), sctList.getTotalPages());
	}

	@Test
	void testFindSnomedCTRecordList() throws Exception {
		assertThrows(Exception.class, () -> (new SnomedServiceImpl()).findSnomedCTRecordList(null));
	}

	@Test
	void testFindSnomedCTRecordList2() throws Exception {
		SnomedServiceImpl snomedServiceImpl = new SnomedServiceImpl();
		SCTDescription sctdescription = new SCTDescription();
		sctdescription.setActive("Active");
		sctdescription.setCaseSignificanceID("Case Significance ID");
		sctdescription.setConceptID("Concept ID");
		sctdescription.setSctDesID(1L);
		sctdescription.setPageNo(null);
		sctdescription.setTerm(null);
		assertThrows(Exception.class, () -> snomedServiceImpl.findSnomedCTRecordList(sctdescription));
	}

	@Test
	void testFindSnomedCTRecordList3() throws Exception {
		SnomedServiceImpl snomedServiceImpl = new SnomedServiceImpl();

		SCTDescription sctdescription = new SCTDescription();
		sctdescription.setActive("Active");
		sctdescription.setCaseSignificanceID("Case Significance ID");
		sctdescription.setConceptID("Concept ID");
		sctdescription.setSctDesID(1L);
		sctdescription.setPageNo(null);
		sctdescription.setTerm("foo");
		assertThrows(Exception.class, () -> snomedServiceImpl.findSnomedCTRecordList(sctdescription));
	}

	@Test
	void testFindSnomedCTRecordList4() throws Exception {
		SCTDescription sctdescription = new SCTDescription();
		sctdescription.setActive("Active");
		sctdescription.setCaseSignificanceID("Case Significance ID");
		sctdescription.setConceptID("Concept ID");
		sctdescription.setPageNo(1);
		sctdescription.setSctDesID(1L);
		sctdescription.setTerm("Term");
		snomedServiceImpl.findSnomedCTRecordList(sctdescription);
	}
}
