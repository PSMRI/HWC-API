package com.iemr.hwc.service.neonatal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.neonatal.*;
import com.iemr.hwc.repo.adolescent.OralVitaminNoOfDoseMasterRepo;
import com.iemr.hwc.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.hwc.repo.labModule.ProcedureRepo;
import com.iemr.hwc.repo.masterrepo.anc.ChildVaccinationsRepo;
import com.iemr.hwc.repo.masterrepo.anc.ComplicationTypesRepo;
import com.iemr.hwc.repo.masterrepo.anc.DeliveryPlaceRepo;
import com.iemr.hwc.repo.masterrepo.anc.DeliveryTypeRepo;
import com.iemr.hwc.repo.masterrepo.anc.GestationRepo;
import com.iemr.hwc.repo.masterrepo.pnc.DeliveryConductedByMasterRepo;
import com.iemr.hwc.repo.neonatal.BirthDoseVaccinesMasterRepo;
import com.iemr.hwc.repo.neonatal.CongenitalAnomaliesMasterRepo;
import com.iemr.hwc.repo.neonatal.CounsellingProvidedMasterRepo;
import com.iemr.hwc.repo.neonatal.CurrentImmunizationServiceMasterRepo;
import com.iemr.hwc.repo.neonatal.NextDueVaccinesMasterRepo;
import com.iemr.hwc.repo.neonatal.NextImmunizationLocationMasterRepo;
import com.iemr.hwc.repo.neonatal.TypeOfImmunizationMasterRepo;
import com.iemr.hwc.repository.neonatal.*;
import com.iemr.hwc.utils.exception.IEMRException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

public class TestNeonatalMasterServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private BirthDoseVaccinesMasterRepo birthDoseVaccinesMasterRepo;

	@Mock
	private CongenitalAnomaliesMasterRepo congenitalAnomaliesMasterRepo;

	@Mock
	private CounsellingProvidedMasterRepo counsellingProvidedMasterRepo;

	@Mock
	private CurrentImmunizationServiceMasterRepo currentImmunizationServiceMasterRepo;

	@Mock
	private NextDueVaccinesMasterRepo nextDueVaccinesMasterRepo;

	@Mock
	private NextImmunizationLocationMasterRepo nextImmunizationLocationMasterRepo;

	@Mock
	private TypeOfImmunizationMasterRepo typeOfImmunizationMasterRepo;

	@Mock
	private DeliveryConductedByMasterRepo deliveryConductedByMasterRepo;

	@Mock
	private DeliveryPlaceRepo deliveryPlaceRepo;

	@Mock
	private DeliveryTypeRepo deliveryTypeRepo;

	@Mock
	private ChiefComplaintMasterRepo chiefComplaintMasterRepo;

	@Mock
	private GestationRepo gestationRepo;

	@Mock
	private ChildVaccinationsRepo childVaccinationsRepo;

	@Mock
	private ComplicationTypesRepo complicationTypesRepo;

	@Mock
	private ProcedureRepo procedureRepo;

	@Mock
	private OralVitaminNoOfDoseMasterRepo oralVitaminNoOfDoseMasterRepo;

	@InjectMocks
	private NeonatalMasterServiceImpl neonatalMasterServiceImpl;

	private Integer visitCategoryID;
	private int psmID;
	private String gender;

	@BeforeEach
	public void setUp() {
		visitCategoryID = 11;
		psmID = 1;
		gender = "Male";
	}

	@Test
	public void testGetNurseMasterDataNeonatal_Success() throws IEMRException {
		when(birthDoseVaccinesMasterRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(congenitalAnomaliesMasterRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(false, visitCategoryID))
				.thenReturn(new ArrayList<>());
		when(currentImmunizationServiceMasterRepo.findByDeletedAndVisitCategoryIDOrderById(false, visitCategoryID))
				.thenReturn(new ArrayList<>());
		when(nextDueVaccinesMasterRepo.findByDeletedOrderById(false)).thenReturn(new ArrayList<>());
		when(nextImmunizationLocationMasterRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(typeOfImmunizationMasterRepo.findByDeleted(false)).thenReturn(new ArrayList<>());
		when(deliveryConductedByMasterRepo.findByDeleted(false)).thenReturn(new ArrayList<>());
		when(deliveryPlaceRepo.findByDeleted(false)).thenReturn(new ArrayList<>());
		when(deliveryTypeRepo.findByDeleted(false)).thenReturn(new ArrayList<>());
		when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(false)).thenReturn(new ArrayList<>());
		when(gestationRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());
		when(childVaccinationsRepo.findByDeletedOrderByVaccinationTime(false)).thenReturn(new ArrayList<>());
		when(complicationTypesRepo.getComplicationTypes("Birth Complication")).thenReturn(new ArrayList<>());
		when(procedureRepo.getProcedureMasterData(psmID, gender)).thenReturn(new ArrayList<>());
		when(oralVitaminNoOfDoseMasterRepo.findByDeleted(false)).thenReturn(new ArrayList<>());

		String result = neonatalMasterServiceImpl.getNurseMasterDataNeonatal(visitCategoryID, psmID, gender);

		assertNotNull(result);
		assertTrue(result.contains("m_birthdosevaccinationreceivedat"));
		assertTrue(result.contains("m_congenitalanomalies"));
		assertTrue(result.contains("m_counsellingprovided"));
		assertTrue(result.contains("m_currentimmunizationservice"));
		assertTrue(result.contains("m_nextduevaccines"));
		assertTrue(result.contains("m_locationofnextimmunization"));
		assertTrue(result.contains("m_immunizationservicestype"));
		assertTrue(result.contains("deliveryConductedByMaster"));
		assertTrue(result.contains("deliveryPlaces"));
		assertTrue(result.contains("deliveryTypes"));
		assertTrue(result.contains("chiefComplaintMaster"));
		assertTrue(result.contains("gestation"));
		assertTrue(result.contains("childVaccinations"));
		assertTrue(result.contains("birthComplications"));
		assertTrue(result.contains("procedures"));
		assertTrue(result.contains("oralVitaminNoDose"));

		verify(birthDoseVaccinesMasterRepo, times(1)).findByDeletedOrderByName(false);
		verify(congenitalAnomaliesMasterRepo, times(1)).findByDeletedOrderByName(false);
		verify(counsellingProvidedMasterRepo, times(1)).findByDeletedAndVisitCategoryIDOrderByName(false,
				visitCategoryID);
		verify(currentImmunizationServiceMasterRepo, times(1)).findByDeletedAndVisitCategoryIDOrderById(false,
				visitCategoryID);
		verify(nextDueVaccinesMasterRepo, times(1)).findByDeletedOrderById(false);
		verify(nextImmunizationLocationMasterRepo, times(1)).findByDeletedOrderByName(false);
		verify(typeOfImmunizationMasterRepo, times(1)).findByDeleted(false);
		verify(deliveryConductedByMasterRepo, times(1)).findByDeleted(false);
		verify(deliveryPlaceRepo, times(1)).findByDeleted(false);
		verify(deliveryTypeRepo, times(1)).findByDeleted(false);
		verify(chiefComplaintMasterRepo, times(1)).findByDeletedOrderByChiefComplaint(false);
		verify(gestationRepo, times(1)).findByDeletedOrderByName(false);
		verify(childVaccinationsRepo, times(1)).findByDeletedOrderByVaccinationTime(false);
		verify(complicationTypesRepo, times(1)).getComplicationTypes("Birth Complication");
		verify(procedureRepo, times(1)).getProcedureMasterData(psmID, gender);
		verify(oralVitaminNoOfDoseMasterRepo, times(1)).findByDeleted(false);
	}

	@Test
	public void testGetNurseMasterDataNeonatal_Exception() {
		when(birthDoseVaccinesMasterRepo.findByDeletedOrderByName(false))
				.thenThrow(new RuntimeException("Database error"));

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			neonatalMasterServiceImpl.getNurseMasterDataNeonatal(visitCategoryID, psmID, gender);
		});

		assertEquals("Database error", exception.getMessage());
		verify(birthDoseVaccinesMasterRepo, times(1)).findByDeletedOrderByName(false);
	}
}
