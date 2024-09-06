package com.iemr.hwc.service.common.master;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import com.iemr.hwc.repo.masterrepo.nurse.CancerDiseaseMasterRepo;
import com.iemr.hwc.repo.masterrepo.nurse.CancerPersonalHabitMasterRepo;
import com.iemr.hwc.repo.masterrepo.nurse.FamilyMemberMasterRepo;
import com.iemr.hwc.repo.visit.VisitCategoryMasterRepo;
import com.iemr.hwc.repo.visit.VisitReasonMasterRepo;
import com.iemr.hwc.service.common.master.NurseMasterDataServiceImpl;
import com.iemr.hwc.model.VisitCategory;
import com.iemr.hwc.model.VisitReason;

public class TestNurseMasterDataServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private VisitCategoryMasterRepo visitCategoryMasterRepo;

	@Mock
	private VisitReasonMasterRepo visitReasonMasterRepo;

	@Mock
	private CancerDiseaseMasterRepo cancerDiseaseMasterRepo;

	@Mock
	private CancerPersonalHabitMasterRepo cancerPersonalHabitMasterRepo;

	@Mock
	private FamilyMemberMasterRepo familyMemberMasterRepo;

	@InjectMocks
	private NurseMasterDataServiceImpl nurseMasterDataService;

	private ArrayList<Object[]> mockVisitCategories;
	private ArrayList<Object[]> mockVisitReasons;
	private Map<String, Object> expectedData;

	@BeforeEach
	public void setUp() {
		mockVisitCategories = new ArrayList<>();
		mockVisitCategories.add(new Object[] { "Category1" });
		mockVisitCategories.add(new Object[] { "Category2" });

		mockVisitReasons = new ArrayList<>();
		mockVisitReasons.add(new Object[] { "Reason1" });
		mockVisitReasons.add(new Object[] { "Reason2" });
	}

	@Test
	public void testGetVisitReasonAndCategories_Positive() {
		when(visitCategoryMasterRepo.getVisitCategoryMaster()).thenReturn(mockVisitCategories);
		when(visitReasonMasterRepo.getVisitReasonMaster()).thenReturn(mockVisitReasons);

		String result = nurseMasterDataService.GetVisitReasonAndCategories();

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("visitCategories", VisitCategory.getVisitCategoryMasterData(mockVisitCategories));
		expectedMap.put("visitReasons", VisitReason.getVisitReasonMasterData(mockVisitReasons));
		String expectedJson = new Gson().toJson(expectedMap);

		assertNotNull(result);
		assertEquals(expectedJson, result);

		verify(visitCategoryMasterRepo, times(1)).getVisitCategoryMaster();
		verify(visitReasonMasterRepo, times(1)).getVisitReasonMaster();
	}

	@Test
	public void testGetVisitReasonAndCategories_EmptyData() {
		when(visitCategoryMasterRepo.getVisitCategoryMaster()).thenReturn(new ArrayList<>());
		when(visitReasonMasterRepo.getVisitReasonMaster()).thenReturn(new ArrayList<>());

		String result = nurseMasterDataService.GetVisitReasonAndCategories();

		Map<String, Object> expectedMap = new HashMap<>();
		expectedMap.put("visitCategories", VisitCategory.getVisitCategoryMasterData(new ArrayList<>()));
		expectedMap.put("visitReasons", VisitReason.getVisitReasonMasterData(new ArrayList<>()));
		String expectedJson = new Gson().toJson(expectedMap);

		assertNotNull(result);
		assertEquals(expectedJson, result);

		verify(visitCategoryMasterRepo, times(1)).getVisitCategoryMaster();
		verify(visitReasonMasterRepo, times(1)).getVisitReasonMaster();
	}

	@Test
	public void testGetVisitReasonAndCategories_Exception() {
		when(visitCategoryMasterRepo.getVisitCategoryMaster()).thenThrow(new RuntimeException("Database error"));
		when(visitReasonMasterRepo.getVisitReasonMaster()).thenThrow(new RuntimeException("Database error"));

		assertThrows(RuntimeException.class, () -> {
			nurseMasterDataService.GetVisitReasonAndCategories();
		});

		verify(visitCategoryMasterRepo, times(1)).getVisitCategoryMaster();
		verify(visitReasonMasterRepo, times(1)).getVisitReasonMaster();
	}

	@Test
	public void testGetCancerScreeningMasterDataForNurse() {
		when(cancerDiseaseMasterRepo.getCancerDiseaseMaster()).thenReturn(new ArrayList<>());
		when(cancerPersonalHabitMasterRepo.getCancerPersonalHabitTypeMaster("Tobacco Use Status"))
				.thenReturn(new ArrayList<>());
		when(cancerPersonalHabitMasterRepo.getCancerPersonalHabitTypeMaster("Type of Tobacco Product"))
				.thenReturn(new ArrayList<>());
		when(cancerPersonalHabitMasterRepo.getCancerPersonalHabitTypeMaster("Alcohol Usage"))
				.thenReturn(new ArrayList<>());
		when(cancerPersonalHabitMasterRepo.getCancerPersonalHabitTypeMaster("Frequency of Alcohol Intake"))
				.thenReturn(new ArrayList<>());
		when(cancerPersonalHabitMasterRepo.getCancerPersonalHabitTypeMaster("Dietary Type "))
				.thenReturn(new ArrayList<>());
		when(cancerPersonalHabitMasterRepo.getCancerPersonalHabitTypeMaster("Oil Consumed"))
				.thenReturn(new ArrayList<>());
		when(cancerPersonalHabitMasterRepo.getCancerPersonalHabitTypeMaster("Physical Activity Type "))
				.thenReturn(new ArrayList<>());
		when(familyMemberMasterRepo.getFamilyMemberTypeMaster()).thenReturn(new ArrayList<>());
		when(visitCategoryMasterRepo.getVisitCategoryMaster()).thenReturn(new ArrayList<>());
		when(visitReasonMasterRepo.getVisitReasonMaster()).thenReturn(new ArrayList<>());

		String response = nurseMasterDataService.getCancerScreeningMasterDataForNurse();
		String expectedResponse = new Gson().toJson(expectedData);

		assertEquals(expectedResponse, response);

		verify(cancerDiseaseMasterRepo, times(1)).getCancerDiseaseMaster();
		verify(cancerPersonalHabitMasterRepo, times(1)).getCancerPersonalHabitTypeMaster("Tobacco Use Status");
		verify(cancerPersonalHabitMasterRepo, times(1)).getCancerPersonalHabitTypeMaster("Type of Tobacco Product");
		verify(cancerPersonalHabitMasterRepo, times(1)).getCancerPersonalHabitTypeMaster("Alcohol Usage");
		verify(cancerPersonalHabitMasterRepo, times(1)).getCancerPersonalHabitTypeMaster("Frequency of Alcohol Intake");
		verify(cancerPersonalHabitMasterRepo, times(1)).getCancerPersonalHabitTypeMaster("Dietary Type ");
		verify(cancerPersonalHabitMasterRepo, times(1)).getCancerPersonalHabitTypeMaster("Oil Consumed");
		verify(cancerPersonalHabitMasterRepo, times(1)).getCancerPersonalHabitTypeMaster("Physical Activity Type ");
		verify(familyMemberMasterRepo, times(1)).getFamilyMemberTypeMaster();
		verify(visitCategoryMasterRepo, times(1)).getVisitCategoryMaster();
		verify(visitReasonMasterRepo, times(1)).getVisitReasonMaster();
	}

}
