package com.iemr.hwc.service.uptsu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.Gson;
import com.iemr.hwc.data.uptsu.AppointmentDetails;
import com.iemr.hwc.data.uptsu.M_104ActionMaster;
import com.iemr.hwc.data.uptsu.Referred104Details;
import com.iemr.hwc.repo.uptsu.M_104ActionMasterRepo;
import com.iemr.hwc.repo.uptsu.UptsuRepository;

public class TestUptsuServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private M_104ActionMasterRepo m_104ActionMasterRepo;

	@Mock
	private UptsuRepository uptsuRepository;

	@InjectMocks
	private UptsuServiceImpl uptsuServiceImpl;

	private List<M_104ActionMaster> actionMasterList;
	private UptsuServiceImpl uptsuService;

	private Referred104Details referred104Details;

	@BeforeEach
	public void setUp() {
		M_104ActionMaster actionMaster1 = new M_104ActionMaster();
		actionMaster1.setActionID(1);
		actionMaster1.setActionName("Action1");

		M_104ActionMaster actionMaster2 = new M_104ActionMaster();
		actionMaster2.setActionID(2);
		actionMaster2.setActionName("Action2");

		actionMasterList = Arrays.asList(actionMaster1, actionMaster2);
	}

	@Test
	public void testGetActionMaster() {
		when(m_104ActionMasterRepo.findByDeleted(false)).thenReturn(actionMasterList);

		String expectedJson = new Gson().toJson(actionMasterList);
		String actualJson = uptsuServiceImpl.getActionMaster();

		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void testGetActionMaster() {
		List<M_104ActionMaster> mockList = new ArrayList<>();
		mockList.add(new M_104ActionMaster());
		when(m_104ActionMasterRepo.findByDeleted(false)).thenReturn(mockList);

		String result = uptsuServiceImpl.getActionMaster();

		assertEquals(new Gson().toJson(mockList), result);
		verify(m_104ActionMasterRepo, times(1)).findByDeleted(false);
	}

	@Test
	public void testGetWorklist() {
		Integer vanID = 1;
		Integer facilityID = 1;
		String facilityCode = "FAC123";
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime then = now.minusDays(7);
		Timestamp time = Timestamp.valueOf(then);
		Timestamp currentTime = Timestamp.valueOf(now);

		when(uptsuRepository.getFacilityId(vanID)).thenReturn(facilityID);
		when(uptsuRepository.getfacilityCode(facilityID)).thenReturn(facilityCode);

		List<AppointmentDetails> appointmentDetailsList = new ArrayList<>();
		AppointmentDetails appointmentDetails = new AppointmentDetails();
		appointmentDetails.setBenRegId("BEN123");
		appointmentDetailsList.add(appointmentDetails);
		when(uptsuRepository.getAppointmentDetails(facilityCode, time, currentTime)).thenReturn(appointmentDetailsList);

		ArrayList<Object[]> data = new ArrayList<>();
		data.add(new Object[] { "BEN123", "John Doe" });
		when(uptsuRepository.findBeneficiaryNameAndBeneficiaryIdByBenRegId("BEN123")).thenReturn(data);

		String result = uptsuServiceImpl.getWorklist(vanID);

		assertNotNull(result);
		verify(uptsuRepository, times(1)).getFacilityId(vanID);
		verify(uptsuRepository, times(1)).getfacilityCode(facilityID);
		verify(uptsuRepository, times(1)).getAppointmentDetails(facilityCode, time, currentTime);
		verify(uptsuRepository, times(1)).findBeneficiaryNameAndBeneficiaryIdByBenRegId("BEN123");
	}

	@Test
	public void testSaveReferredDetails() {
		Referred104Details requestObj = new Referred104Details();
		requestObj.setBenCallID(1L);
		when(uptsuRepository.save(requestObj)).thenReturn(requestObj);

		Referred104Details result = uptsuServiceImpl.saveReferredDetails(requestObj);

		assertEquals(requestObj, result);
		verify(uptsuRepository, times(1)).save(requestObj);
		verify(uptsuRepository, times(1)).updateReferredFlag(requestObj.getBenCallID());
	}

	@Test
	public void testSaveReferredDetails() {
		when(uptsuRepository.save(any(Referred104Details.class))).thenReturn(referred104Details);

		Referred104Details result = uptsuService.saveReferredDetails(referred104Details);

		assertNotNull(result);
		assertEquals(referred104Details.getBenCallID(), result.getBenCallID());
		verify(uptsuRepository, times(1)).save(any(Referred104Details.class));
		verify(uptsuRepository, times(1)).updateReferredFlag(referred104Details.getBenCallID());
	}

	@Test
	public void testSaveReferredDetails_NullResponse() {
		when(uptsuRepository.save(any(Referred104Details.class))).thenReturn(null);

		Referred104Details result = uptsuService.saveReferredDetails(referred104Details);

		assertNull(result);
		verify(uptsuRepository, times(1)).save(any(Referred104Details.class));
		verify(uptsuRepository, never()).updateReferredFlag(anyLong());
	}
}
