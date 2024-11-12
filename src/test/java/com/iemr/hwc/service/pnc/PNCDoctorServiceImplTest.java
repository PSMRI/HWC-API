package com.iemr.hwc.service.pnc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.pnc.PNCDiagnosis;
import com.iemr.hwc.data.snomedct.SCTDescription;
import com.iemr.hwc.repo.nurse.pnc.PNCDiagnosisRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class PNCDoctorServiceImplTest {
	@Mock
	private CommonDoctorServiceImpl commonDoctorServiceImpl;

	@Mock
	private PNCDiagnosisRepo pNCDiagnosisRepo;

	@InjectMocks
	private PNCDoctorServiceImpl pNCDoctorServiceImpl;

	@Mock
	private PrescriptionDetailRepo prescriptionDetailRepo;

	@Test
	void testSaveBenPNCDiagnosis() throws IEMRException {
		// Arrange
		PNCDiagnosis pncDiagnosis = new PNCDiagnosis();
		pncDiagnosis.setBenVisitID(1L);
		pncDiagnosis.setBeneficiaryRegID(1L);
		pncDiagnosis.setCauseOfDeath("Cause Of Death");
		pncDiagnosis.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
		pncDiagnosis.setConfirmatoryDiagnosisList(new ArrayList<>());
		pncDiagnosis.setConfirmatoryDiagnosisSCTCode("Confirmatory Diagnosis SCTCode");
		pncDiagnosis.setConfirmatoryDiagnosisSCTTerm("Confirmatory Diagnosis SCTTerm");
		pncDiagnosis.setCounsellingProvided("Counselling Provided");
		pncDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncDiagnosis.setCreatedDate(mock(Timestamp.class));
		pncDiagnosis.setDateOfDeath(mock(Date.class));
		pncDiagnosis.setDeleted(true);
		pncDiagnosis.setExternalInvestigation("External Investigation");
		pncDiagnosis.setID(1L);
		pncDiagnosis.setIsMaternalDeath(true);
		pncDiagnosis.setLastModDate(mock(Timestamp.class));
		pncDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncDiagnosis.setParkingPlaceID(1);
		pncDiagnosis.setPlaceOfDeath("Place Of Death");
		pncDiagnosis.setPrescriptionID(1L);
		pncDiagnosis.setProcessed("Processed");
		pncDiagnosis.setProviderServiceMapID(1);
		pncDiagnosis.setProvisionalDiagnosis("Provisional Diagnosis");
		pncDiagnosis.setProvisionalDiagnosisList(new ArrayList<>());
		pncDiagnosis.setProvisionalDiagnosisSCTCode("Provisional Diagnosis SCTCode");
		pncDiagnosis.setProvisionalDiagnosisSCTTerm("Provisional Diagnosis SCTTerm");
		pncDiagnosis.setReservedForChange("Reserved For Change");
		pncDiagnosis.setSpecialistDiagnosis("Specialist Diagnosis");
		pncDiagnosis.setSyncedBy("Synced By");
		pncDiagnosis.setSyncedDate(mock(Timestamp.class));
		pncDiagnosis.setVanID(1);
		pncDiagnosis.setVanSerialNo(1L);
		pncDiagnosis.setVehicalNo("Vehical No");
		pncDiagnosis.setVisitCode(1L);
		when(pNCDiagnosisRepo.save(Mockito.<PNCDiagnosis>any())).thenReturn(pncDiagnosis);

		// Act
		Long actualSaveBenPNCDiagnosisResult = pNCDoctorServiceImpl.saveBenPNCDiagnosis(new JsonObject(), 1L);

		// Assert
		verify(pNCDiagnosisRepo).save(isA(PNCDiagnosis.class));
		assertEquals(1L, actualSaveBenPNCDiagnosisResult.longValue());
	}

	@Test
	void testSaveBenPNCDiagnosis2() throws IEMRException {
		// Arrange
		PNCDiagnosis pncDiagnosis = mock(PNCDiagnosis.class);
		when(pncDiagnosis.getID()).thenReturn(-1L);
		doNothing().when(pncDiagnosis).setBenVisitID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setBeneficiaryRegID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setCauseOfDeath(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosis(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosisSCTCode(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosisSCTTerm(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setCounsellingProvided(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setCreatedBy(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setCreatedDate(Mockito.<Timestamp>any());
		doNothing().when(pncDiagnosis).setDateOfDeath(Mockito.<Date>any());
		doNothing().when(pncDiagnosis).setDeleted(Mockito.<Boolean>any());
		doNothing().when(pncDiagnosis).setExternalInvestigation(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setIsMaternalDeath(Mockito.<Boolean>any());
		doNothing().when(pncDiagnosis).setLastModDate(Mockito.<Timestamp>any());
		doNothing().when(pncDiagnosis).setModifiedBy(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setParkingPlaceID(Mockito.<Integer>any());
		doNothing().when(pncDiagnosis).setPlaceOfDeath(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setPrescriptionID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setProcessed(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setProviderServiceMapID(Mockito.<Integer>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosis(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosisSCTCode(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosisSCTTerm(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setReservedForChange(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setSpecialistDiagnosis(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setSyncedBy(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setSyncedDate(Mockito.<Timestamp>any());
		doNothing().when(pncDiagnosis).setVanID(Mockito.<Integer>any());
		doNothing().when(pncDiagnosis).setVanSerialNo(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setVehicalNo(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setVisitCode(Mockito.<Long>any());
		pncDiagnosis.setBenVisitID(1L);
		pncDiagnosis.setBeneficiaryRegID(1L);
		pncDiagnosis.setCauseOfDeath("Cause Of Death");
		pncDiagnosis.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
		pncDiagnosis.setConfirmatoryDiagnosisList(new ArrayList<>());
		pncDiagnosis.setConfirmatoryDiagnosisSCTCode("Confirmatory Diagnosis SCTCode");
		pncDiagnosis.setConfirmatoryDiagnosisSCTTerm("Confirmatory Diagnosis SCTTerm");
		pncDiagnosis.setCounsellingProvided("Counselling Provided");
		pncDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncDiagnosis.setCreatedDate(mock(Timestamp.class));
		pncDiagnosis.setDateOfDeath(mock(Date.class));
		pncDiagnosis.setDeleted(true);
		pncDiagnosis.setExternalInvestigation("External Investigation");
		pncDiagnosis.setID(1L);
		pncDiagnosis.setIsMaternalDeath(true);
		pncDiagnosis.setLastModDate(mock(Timestamp.class));
		pncDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncDiagnosis.setParkingPlaceID(1);
		pncDiagnosis.setPlaceOfDeath("Place Of Death");
		pncDiagnosis.setPrescriptionID(1L);
		pncDiagnosis.setProcessed("Processed");
		pncDiagnosis.setProviderServiceMapID(1);
		pncDiagnosis.setProvisionalDiagnosis("Provisional Diagnosis");
		pncDiagnosis.setProvisionalDiagnosisList(new ArrayList<>());
		pncDiagnosis.setProvisionalDiagnosisSCTCode("Provisional Diagnosis SCTCode");
		pncDiagnosis.setProvisionalDiagnosisSCTTerm("Provisional Diagnosis SCTTerm");
		pncDiagnosis.setReservedForChange("Reserved For Change");
		pncDiagnosis.setSpecialistDiagnosis("Specialist Diagnosis");
		pncDiagnosis.setSyncedBy("Synced By");
		pncDiagnosis.setSyncedDate(mock(Timestamp.class));
		pncDiagnosis.setVanID(1);
		pncDiagnosis.setVanSerialNo(1L);
		pncDiagnosis.setVehicalNo("Vehical No");
		pncDiagnosis.setVisitCode(1L);
		when(pNCDiagnosisRepo.save(Mockito.<PNCDiagnosis>any())).thenReturn(pncDiagnosis);

		// Act
		Long actualSaveBenPNCDiagnosisResult = pNCDoctorServiceImpl.saveBenPNCDiagnosis(new JsonObject(), 1L);

		// Assert
		verify(pncDiagnosis).getID();
		verify(pncDiagnosis).setBenVisitID(isA(Long.class));
		verify(pncDiagnosis).setBeneficiaryRegID(isA(Long.class));
		verify(pncDiagnosis).setCauseOfDeath(eq("Cause Of Death"));
		verify(pncDiagnosis).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
		verify(pncDiagnosis).setConfirmatoryDiagnosisList(isA(ArrayList.class));
		verify(pncDiagnosis).setConfirmatoryDiagnosisSCTCode(eq("Confirmatory Diagnosis SCTCode"));
		verify(pncDiagnosis).setConfirmatoryDiagnosisSCTTerm(eq("Confirmatory Diagnosis SCTTerm"));
		verify(pncDiagnosis).setCounsellingProvided(eq("Counselling Provided"));
		verify(pncDiagnosis).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
		verify(pncDiagnosis).setCreatedDate(isA(Timestamp.class));
		verify(pncDiagnosis).setDateOfDeath(isA(Date.class));
		verify(pncDiagnosis).setDeleted(isA(Boolean.class));
		verify(pncDiagnosis).setExternalInvestigation(eq("External Investigation"));
		verify(pncDiagnosis).setID(isA(Long.class));
		verify(pncDiagnosis).setIsMaternalDeath(isA(Boolean.class));
		verify(pncDiagnosis).setLastModDate(isA(Timestamp.class));
		verify(pncDiagnosis).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
		verify(pncDiagnosis).setParkingPlaceID(isA(Integer.class));
		verify(pncDiagnosis).setPlaceOfDeath(eq("Place Of Death"));
		verify(pncDiagnosis).setPrescriptionID(isA(Long.class));
		verify(pncDiagnosis).setProcessed(eq("Processed"));
		verify(pncDiagnosis).setProviderServiceMapID(isA(Integer.class));
		verify(pncDiagnosis).setProvisionalDiagnosis(eq("Provisional Diagnosis"));
		verify(pncDiagnosis).setProvisionalDiagnosisList(isA(ArrayList.class));
		verify(pncDiagnosis).setProvisionalDiagnosisSCTCode(eq("Provisional Diagnosis SCTCode"));
		verify(pncDiagnosis).setProvisionalDiagnosisSCTTerm(eq("Provisional Diagnosis SCTTerm"));
		verify(pncDiagnosis).setReservedForChange(eq("Reserved For Change"));
		verify(pncDiagnosis).setSpecialistDiagnosis(eq("Specialist Diagnosis"));
		verify(pncDiagnosis).setSyncedBy(eq("Synced By"));
		verify(pncDiagnosis).setSyncedDate(isA(Timestamp.class));
		verify(pncDiagnosis).setVanID(isA(Integer.class));
		verify(pncDiagnosis).setVanSerialNo(isA(Long.class));
		verify(pncDiagnosis).setVehicalNo(eq("Vehical No"));
		verify(pncDiagnosis).setVisitCode(isA(Long.class));
		verify(pNCDiagnosisRepo).save(isA(PNCDiagnosis.class));
		assertNull(actualSaveBenPNCDiagnosisResult);
	}

	@Test
	void testSaveBenPNCDiagnosis3() throws IEMRException {
		// Arrange
		PNCDiagnosis pncDiagnosis = mock(PNCDiagnosis.class);
		when(pncDiagnosis.getID()).thenReturn(1L);
		doNothing().when(pncDiagnosis).setBenVisitID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setBeneficiaryRegID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setCauseOfDeath(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosis(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosisSCTCode(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosisSCTTerm(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setCounsellingProvided(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setCreatedBy(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setCreatedDate(Mockito.<Timestamp>any());
		doNothing().when(pncDiagnosis).setDateOfDeath(Mockito.<Date>any());
		doNothing().when(pncDiagnosis).setDeleted(Mockito.<Boolean>any());
		doNothing().when(pncDiagnosis).setExternalInvestigation(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setIsMaternalDeath(Mockito.<Boolean>any());
		doNothing().when(pncDiagnosis).setLastModDate(Mockito.<Timestamp>any());
		doNothing().when(pncDiagnosis).setModifiedBy(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setParkingPlaceID(Mockito.<Integer>any());
		doNothing().when(pncDiagnosis).setPlaceOfDeath(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setPrescriptionID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setProcessed(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setProviderServiceMapID(Mockito.<Integer>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosis(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosisSCTCode(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosisSCTTerm(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setReservedForChange(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setSpecialistDiagnosis(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setSyncedBy(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setSyncedDate(Mockito.<Timestamp>any());
		doNothing().when(pncDiagnosis).setVanID(Mockito.<Integer>any());
		doNothing().when(pncDiagnosis).setVanSerialNo(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setVehicalNo(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setVisitCode(Mockito.<Long>any());
		pncDiagnosis.setBenVisitID(1L);
		pncDiagnosis.setBeneficiaryRegID(1L);
		pncDiagnosis.setCauseOfDeath("Cause Of Death");
		pncDiagnosis.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
		pncDiagnosis.setConfirmatoryDiagnosisList(new ArrayList<>());
		pncDiagnosis.setConfirmatoryDiagnosisSCTCode("Confirmatory Diagnosis SCTCode");
		pncDiagnosis.setConfirmatoryDiagnosisSCTTerm("Confirmatory Diagnosis SCTTerm");
		pncDiagnosis.setCounsellingProvided("Counselling Provided");
		pncDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncDiagnosis.setCreatedDate(mock(Timestamp.class));
		pncDiagnosis.setDateOfDeath(mock(Date.class));
		pncDiagnosis.setDeleted(true);
		pncDiagnosis.setExternalInvestigation("External Investigation");
		pncDiagnosis.setID(1L);
		pncDiagnosis.setIsMaternalDeath(true);
		pncDiagnosis.setLastModDate(mock(Timestamp.class));
		pncDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncDiagnosis.setParkingPlaceID(1);
		pncDiagnosis.setPlaceOfDeath("Place Of Death");
		pncDiagnosis.setPrescriptionID(1L);
		pncDiagnosis.setProcessed("Processed");
		pncDiagnosis.setProviderServiceMapID(1);
		pncDiagnosis.setProvisionalDiagnosis("Provisional Diagnosis");
		pncDiagnosis.setProvisionalDiagnosisList(new ArrayList<>());
		pncDiagnosis.setProvisionalDiagnosisSCTCode("Provisional Diagnosis SCTCode");
		pncDiagnosis.setProvisionalDiagnosisSCTTerm("Provisional Diagnosis SCTTerm");
		pncDiagnosis.setReservedForChange("Reserved For Change");
		pncDiagnosis.setSpecialistDiagnosis("Specialist Diagnosis");
		pncDiagnosis.setSyncedBy("Synced By");
		pncDiagnosis.setSyncedDate(mock(Timestamp.class));
		pncDiagnosis.setVanID(1);
		pncDiagnosis.setVanSerialNo(1L);
		pncDiagnosis.setVehicalNo("Vehical No");
		pncDiagnosis.setVisitCode(1L);
		when(pNCDiagnosisRepo.save(Mockito.<PNCDiagnosis>any())).thenReturn(pncDiagnosis);

		JsonObject obj = new JsonObject();
		obj.add("Property", new JsonArray(3));

		// Act
		Long actualSaveBenPNCDiagnosisResult = pNCDoctorServiceImpl.saveBenPNCDiagnosis(obj, 1L);

		// Assert
		verify(pncDiagnosis, atLeast(1)).getID();
		verify(pncDiagnosis).setBenVisitID(isA(Long.class));
		verify(pncDiagnosis).setBeneficiaryRegID(isA(Long.class));
		verify(pncDiagnosis).setCauseOfDeath(eq("Cause Of Death"));
		verify(pncDiagnosis).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
		verify(pncDiagnosis).setConfirmatoryDiagnosisList(isA(ArrayList.class));
		verify(pncDiagnosis).setConfirmatoryDiagnosisSCTCode(eq("Confirmatory Diagnosis SCTCode"));
		verify(pncDiagnosis).setConfirmatoryDiagnosisSCTTerm(eq("Confirmatory Diagnosis SCTTerm"));
		verify(pncDiagnosis).setCounsellingProvided(eq("Counselling Provided"));
		verify(pncDiagnosis).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
		verify(pncDiagnosis).setCreatedDate(isA(Timestamp.class));
		verify(pncDiagnosis).setDateOfDeath(isA(Date.class));
		verify(pncDiagnosis).setDeleted(isA(Boolean.class));
		verify(pncDiagnosis).setExternalInvestigation(eq("External Investigation"));
		verify(pncDiagnosis).setID(isA(Long.class));
		verify(pncDiagnosis).setIsMaternalDeath(isA(Boolean.class));
		verify(pncDiagnosis).setLastModDate(isA(Timestamp.class));
		verify(pncDiagnosis).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
		verify(pncDiagnosis).setParkingPlaceID(isA(Integer.class));
		verify(pncDiagnosis).setPlaceOfDeath(eq("Place Of Death"));
		verify(pncDiagnosis).setPrescriptionID(isA(Long.class));
		verify(pncDiagnosis).setProcessed(eq("Processed"));
		verify(pncDiagnosis).setProviderServiceMapID(isA(Integer.class));
		verify(pncDiagnosis).setProvisionalDiagnosis(eq("Provisional Diagnosis"));
		verify(pncDiagnosis).setProvisionalDiagnosisList(isA(ArrayList.class));
		verify(pncDiagnosis).setProvisionalDiagnosisSCTCode(eq("Provisional Diagnosis SCTCode"));
		verify(pncDiagnosis).setProvisionalDiagnosisSCTTerm(eq("Provisional Diagnosis SCTTerm"));
		verify(pncDiagnosis).setReservedForChange(eq("Reserved For Change"));
		verify(pncDiagnosis).setSpecialistDiagnosis(eq("Specialist Diagnosis"));
		verify(pncDiagnosis).setSyncedBy(eq("Synced By"));
		verify(pncDiagnosis).setSyncedDate(isA(Timestamp.class));
		verify(pncDiagnosis).setVanID(isA(Integer.class));
		verify(pncDiagnosis).setVanSerialNo(isA(Long.class));
		verify(pncDiagnosis).setVehicalNo(eq("Vehical No"));
		verify(pncDiagnosis).setVisitCode(isA(Long.class));
		verify(pNCDiagnosisRepo).save(isA(PNCDiagnosis.class));
		assertEquals(1L, actualSaveBenPNCDiagnosisResult.longValue());
	}

	@Test
	void testSaveBenPNCDiagnosis4() throws IEMRException {
		// Arrange
		PNCDiagnosis pncDiagnosis = mock(PNCDiagnosis.class);
		when(pncDiagnosis.getID()).thenReturn(1L);
		doNothing().when(pncDiagnosis).setBenVisitID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setBeneficiaryRegID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setCauseOfDeath(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosis(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosisSCTCode(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosisSCTTerm(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setCounsellingProvided(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setCreatedBy(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setCreatedDate(Mockito.<Timestamp>any());
		doNothing().when(pncDiagnosis).setDateOfDeath(Mockito.<Date>any());
		doNothing().when(pncDiagnosis).setDeleted(Mockito.<Boolean>any());
		doNothing().when(pncDiagnosis).setExternalInvestigation(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setIsMaternalDeath(Mockito.<Boolean>any());
		doNothing().when(pncDiagnosis).setLastModDate(Mockito.<Timestamp>any());
		doNothing().when(pncDiagnosis).setModifiedBy(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setParkingPlaceID(Mockito.<Integer>any());
		doNothing().when(pncDiagnosis).setPlaceOfDeath(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setPrescriptionID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setProcessed(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setProviderServiceMapID(Mockito.<Integer>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosis(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosisSCTCode(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosisSCTTerm(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setReservedForChange(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setSpecialistDiagnosis(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setSyncedBy(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setSyncedDate(Mockito.<Timestamp>any());
		doNothing().when(pncDiagnosis).setVanID(Mockito.<Integer>any());
		doNothing().when(pncDiagnosis).setVanSerialNo(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setVehicalNo(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setVisitCode(Mockito.<Long>any());
		pncDiagnosis.setBenVisitID(1L);
		pncDiagnosis.setBeneficiaryRegID(1L);
		pncDiagnosis.setCauseOfDeath("Cause Of Death");
		pncDiagnosis.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
		pncDiagnosis.setConfirmatoryDiagnosisList(new ArrayList<>());
		pncDiagnosis.setConfirmatoryDiagnosisSCTCode("Confirmatory Diagnosis SCTCode");
		pncDiagnosis.setConfirmatoryDiagnosisSCTTerm("Confirmatory Diagnosis SCTTerm");
		pncDiagnosis.setCounsellingProvided("Counselling Provided");
		pncDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncDiagnosis.setCreatedDate(mock(Timestamp.class));
		pncDiagnosis.setDateOfDeath(mock(Date.class));
		pncDiagnosis.setDeleted(true);
		pncDiagnosis.setExternalInvestigation("External Investigation");
		pncDiagnosis.setID(1L);
		pncDiagnosis.setIsMaternalDeath(true);
		pncDiagnosis.setLastModDate(mock(Timestamp.class));
		pncDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncDiagnosis.setParkingPlaceID(1);
		pncDiagnosis.setPlaceOfDeath("Place Of Death");
		pncDiagnosis.setPrescriptionID(1L);
		pncDiagnosis.setProcessed("Processed");
		pncDiagnosis.setProviderServiceMapID(1);
		pncDiagnosis.setProvisionalDiagnosis("Provisional Diagnosis");
		pncDiagnosis.setProvisionalDiagnosisList(new ArrayList<>());
		pncDiagnosis.setProvisionalDiagnosisSCTCode("Provisional Diagnosis SCTCode");
		pncDiagnosis.setProvisionalDiagnosisSCTTerm("Provisional Diagnosis SCTTerm");
		pncDiagnosis.setReservedForChange("Reserved For Change");
		pncDiagnosis.setSpecialistDiagnosis("Specialist Diagnosis");
		pncDiagnosis.setSyncedBy("Synced By");
		pncDiagnosis.setSyncedDate(mock(Timestamp.class));
		pncDiagnosis.setVanID(1);
		pncDiagnosis.setVanSerialNo(1L);
		pncDiagnosis.setVehicalNo("Vehical No");
		pncDiagnosis.setVisitCode(1L);
		when(pNCDiagnosisRepo.save(Mockito.<PNCDiagnosis>any())).thenReturn(pncDiagnosis);

		JsonObject obj = new JsonObject();
		obj.addProperty("Property", "42");

		// Act
		Long actualSaveBenPNCDiagnosisResult = pNCDoctorServiceImpl.saveBenPNCDiagnosis(obj, 1L);

		// Assert
		verify(pncDiagnosis, atLeast(1)).getID();
		verify(pncDiagnosis).setBenVisitID(isA(Long.class));
		verify(pncDiagnosis).setBeneficiaryRegID(isA(Long.class));
		verify(pncDiagnosis).setCauseOfDeath(eq("Cause Of Death"));
		verify(pncDiagnosis).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
		verify(pncDiagnosis).setConfirmatoryDiagnosisList(isA(ArrayList.class));
		verify(pncDiagnosis).setConfirmatoryDiagnosisSCTCode(eq("Confirmatory Diagnosis SCTCode"));
		verify(pncDiagnosis).setConfirmatoryDiagnosisSCTTerm(eq("Confirmatory Diagnosis SCTTerm"));
		verify(pncDiagnosis).setCounsellingProvided(eq("Counselling Provided"));
		verify(pncDiagnosis).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
		verify(pncDiagnosis).setCreatedDate(isA(Timestamp.class));
		verify(pncDiagnosis).setDateOfDeath(isA(Date.class));
		verify(pncDiagnosis).setDeleted(isA(Boolean.class));
		verify(pncDiagnosis).setExternalInvestigation(eq("External Investigation"));
		verify(pncDiagnosis).setID(isA(Long.class));
		verify(pncDiagnosis).setIsMaternalDeath(isA(Boolean.class));
		verify(pncDiagnosis).setLastModDate(isA(Timestamp.class));
		verify(pncDiagnosis).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
		verify(pncDiagnosis).setParkingPlaceID(isA(Integer.class));
		verify(pncDiagnosis).setPlaceOfDeath(eq("Place Of Death"));
		verify(pncDiagnosis).setPrescriptionID(isA(Long.class));
		verify(pncDiagnosis).setProcessed(eq("Processed"));
		verify(pncDiagnosis).setProviderServiceMapID(isA(Integer.class));
		verify(pncDiagnosis).setProvisionalDiagnosis(eq("Provisional Diagnosis"));
		verify(pncDiagnosis).setProvisionalDiagnosisList(isA(ArrayList.class));
		verify(pncDiagnosis).setProvisionalDiagnosisSCTCode(eq("Provisional Diagnosis SCTCode"));
		verify(pncDiagnosis).setProvisionalDiagnosisSCTTerm(eq("Provisional Diagnosis SCTTerm"));
		verify(pncDiagnosis).setReservedForChange(eq("Reserved For Change"));
		verify(pncDiagnosis).setSpecialistDiagnosis(eq("Specialist Diagnosis"));
		verify(pncDiagnosis).setSyncedBy(eq("Synced By"));
		verify(pncDiagnosis).setSyncedDate(isA(Timestamp.class));
		verify(pncDiagnosis).setVanID(isA(Integer.class));
		verify(pncDiagnosis).setVanSerialNo(isA(Long.class));
		verify(pncDiagnosis).setVehicalNo(eq("Vehical No"));
		verify(pncDiagnosis).setVisitCode(isA(Long.class));
		verify(pNCDiagnosisRepo).save(isA(PNCDiagnosis.class));
		assertEquals(1L, actualSaveBenPNCDiagnosisResult.longValue());
	}

	@Test
	void testSaveBenPNCDiagnosis5() throws IEMRException {
		// Arrange
		PNCDiagnosis pncDiagnosis = mock(PNCDiagnosis.class);
		when(pncDiagnosis.getID()).thenReturn(1L);
		doNothing().when(pncDiagnosis).setBenVisitID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setBeneficiaryRegID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setCauseOfDeath(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosis(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosisSCTCode(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosisSCTTerm(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setCounsellingProvided(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setCreatedBy(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setCreatedDate(Mockito.<Timestamp>any());
		doNothing().when(pncDiagnosis).setDateOfDeath(Mockito.<Date>any());
		doNothing().when(pncDiagnosis).setDeleted(Mockito.<Boolean>any());
		doNothing().when(pncDiagnosis).setExternalInvestigation(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setIsMaternalDeath(Mockito.<Boolean>any());
		doNothing().when(pncDiagnosis).setLastModDate(Mockito.<Timestamp>any());
		doNothing().when(pncDiagnosis).setModifiedBy(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setParkingPlaceID(Mockito.<Integer>any());
		doNothing().when(pncDiagnosis).setPlaceOfDeath(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setPrescriptionID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setProcessed(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setProviderServiceMapID(Mockito.<Integer>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosis(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosisSCTCode(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosisSCTTerm(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setReservedForChange(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setSpecialistDiagnosis(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setSyncedBy(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setSyncedDate(Mockito.<Timestamp>any());
		doNothing().when(pncDiagnosis).setVanID(Mockito.<Integer>any());
		doNothing().when(pncDiagnosis).setVanSerialNo(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setVehicalNo(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setVisitCode(Mockito.<Long>any());
		pncDiagnosis.setBenVisitID(1L);
		pncDiagnosis.setBeneficiaryRegID(1L);
		pncDiagnosis.setCauseOfDeath("Cause Of Death");
		pncDiagnosis.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
		pncDiagnosis.setConfirmatoryDiagnosisList(new ArrayList<>());
		pncDiagnosis.setConfirmatoryDiagnosisSCTCode("Confirmatory Diagnosis SCTCode");
		pncDiagnosis.setConfirmatoryDiagnosisSCTTerm("Confirmatory Diagnosis SCTTerm");
		pncDiagnosis.setCounsellingProvided("Counselling Provided");
		pncDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncDiagnosis.setCreatedDate(mock(Timestamp.class));
		pncDiagnosis.setDateOfDeath(mock(Date.class));
		pncDiagnosis.setDeleted(true);
		pncDiagnosis.setExternalInvestigation("External Investigation");
		pncDiagnosis.setID(1L);
		pncDiagnosis.setIsMaternalDeath(true);
		pncDiagnosis.setLastModDate(mock(Timestamp.class));
		pncDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncDiagnosis.setParkingPlaceID(1);
		pncDiagnosis.setPlaceOfDeath("Place Of Death");
		pncDiagnosis.setPrescriptionID(1L);
		pncDiagnosis.setProcessed("Processed");
		pncDiagnosis.setProviderServiceMapID(1);
		pncDiagnosis.setProvisionalDiagnosis("Provisional Diagnosis");
		pncDiagnosis.setProvisionalDiagnosisList(new ArrayList<>());
		pncDiagnosis.setProvisionalDiagnosisSCTCode("Provisional Diagnosis SCTCode");
		pncDiagnosis.setProvisionalDiagnosisSCTTerm("Provisional Diagnosis SCTTerm");
		pncDiagnosis.setReservedForChange("Reserved For Change");
		pncDiagnosis.setSpecialistDiagnosis("Specialist Diagnosis");
		pncDiagnosis.setSyncedBy("Synced By");
		pncDiagnosis.setSyncedDate(mock(Timestamp.class));
		pncDiagnosis.setVanID(1);
		pncDiagnosis.setVanSerialNo(1L);
		pncDiagnosis.setVehicalNo("Vehical No");
		pncDiagnosis.setVisitCode(1L);
		when(pNCDiagnosisRepo.save(Mockito.<PNCDiagnosis>any())).thenReturn(pncDiagnosis);

		JsonObject obj = new JsonObject();
		obj.addProperty("Property", Integer.valueOf(1));

		// Act
		Long actualSaveBenPNCDiagnosisResult = pNCDoctorServiceImpl.saveBenPNCDiagnosis(obj, 1L);

		// Assert
		verify(pncDiagnosis, atLeast(1)).getID();
		verify(pncDiagnosis).setBenVisitID(isA(Long.class));
		verify(pncDiagnosis).setBeneficiaryRegID(isA(Long.class));
		verify(pncDiagnosis).setCauseOfDeath(eq("Cause Of Death"));
		verify(pncDiagnosis).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
		verify(pncDiagnosis).setConfirmatoryDiagnosisList(isA(ArrayList.class));
		verify(pncDiagnosis).setConfirmatoryDiagnosisSCTCode(eq("Confirmatory Diagnosis SCTCode"));
		verify(pncDiagnosis).setConfirmatoryDiagnosisSCTTerm(eq("Confirmatory Diagnosis SCTTerm"));
		verify(pncDiagnosis).setCounsellingProvided(eq("Counselling Provided"));
		verify(pncDiagnosis).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
		verify(pncDiagnosis).setCreatedDate(isA(Timestamp.class));
		verify(pncDiagnosis).setDateOfDeath(isA(Date.class));
		verify(pncDiagnosis).setDeleted(isA(Boolean.class));
		verify(pncDiagnosis).setExternalInvestigation(eq("External Investigation"));
		verify(pncDiagnosis).setID(isA(Long.class));
		verify(pncDiagnosis).setIsMaternalDeath(isA(Boolean.class));
		verify(pncDiagnosis).setLastModDate(isA(Timestamp.class));
		verify(pncDiagnosis).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
		verify(pncDiagnosis).setParkingPlaceID(isA(Integer.class));
		verify(pncDiagnosis).setPlaceOfDeath(eq("Place Of Death"));
		verify(pncDiagnosis).setPrescriptionID(isA(Long.class));
		verify(pncDiagnosis).setProcessed(eq("Processed"));
		verify(pncDiagnosis).setProviderServiceMapID(isA(Integer.class));
		verify(pncDiagnosis).setProvisionalDiagnosis(eq("Provisional Diagnosis"));
		verify(pncDiagnosis).setProvisionalDiagnosisList(isA(ArrayList.class));
		verify(pncDiagnosis).setProvisionalDiagnosisSCTCode(eq("Provisional Diagnosis SCTCode"));
		verify(pncDiagnosis).setProvisionalDiagnosisSCTTerm(eq("Provisional Diagnosis SCTTerm"));
		verify(pncDiagnosis).setReservedForChange(eq("Reserved For Change"));
		verify(pncDiagnosis).setSpecialistDiagnosis(eq("Specialist Diagnosis"));
		verify(pncDiagnosis).setSyncedBy(eq("Synced By"));
		verify(pncDiagnosis).setSyncedDate(isA(Timestamp.class));
		verify(pncDiagnosis).setVanID(isA(Integer.class));
		verify(pncDiagnosis).setVanSerialNo(isA(Long.class));
		verify(pncDiagnosis).setVehicalNo(eq("Vehical No"));
		verify(pncDiagnosis).setVisitCode(isA(Long.class));
		verify(pNCDiagnosisRepo).save(isA(PNCDiagnosis.class));
		assertEquals(1L, actualSaveBenPNCDiagnosisResult.longValue());
	}

	@Test
	void testSaveBenPNCDiagnosis6() throws IEMRException {
		// Arrange
		PNCDiagnosis pncDiagnosis = mock(PNCDiagnosis.class);
		when(pncDiagnosis.getID()).thenReturn(1L);
		doNothing().when(pncDiagnosis).setBenVisitID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setBeneficiaryRegID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setCauseOfDeath(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosis(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosisSCTCode(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setConfirmatoryDiagnosisSCTTerm(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setCounsellingProvided(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setCreatedBy(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setCreatedDate(Mockito.<Timestamp>any());
		doNothing().when(pncDiagnosis).setDateOfDeath(Mockito.<Date>any());
		doNothing().when(pncDiagnosis).setDeleted(Mockito.<Boolean>any());
		doNothing().when(pncDiagnosis).setExternalInvestigation(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setIsMaternalDeath(Mockito.<Boolean>any());
		doNothing().when(pncDiagnosis).setLastModDate(Mockito.<Timestamp>any());
		doNothing().when(pncDiagnosis).setModifiedBy(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setParkingPlaceID(Mockito.<Integer>any());
		doNothing().when(pncDiagnosis).setPlaceOfDeath(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setPrescriptionID(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setProcessed(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setProviderServiceMapID(Mockito.<Integer>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosis(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosisSCTCode(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setProvisionalDiagnosisSCTTerm(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setReservedForChange(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setSpecialistDiagnosis(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setSyncedBy(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setSyncedDate(Mockito.<Timestamp>any());
		doNothing().when(pncDiagnosis).setVanID(Mockito.<Integer>any());
		doNothing().when(pncDiagnosis).setVanSerialNo(Mockito.<Long>any());
		doNothing().when(pncDiagnosis).setVehicalNo(Mockito.<String>any());
		doNothing().when(pncDiagnosis).setVisitCode(Mockito.<Long>any());
		pncDiagnosis.setBenVisitID(1L);
		pncDiagnosis.setBeneficiaryRegID(1L);
		pncDiagnosis.setCauseOfDeath("Cause Of Death");
		pncDiagnosis.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
		pncDiagnosis.setConfirmatoryDiagnosisList(new ArrayList<>());
		pncDiagnosis.setConfirmatoryDiagnosisSCTCode("Confirmatory Diagnosis SCTCode");
		pncDiagnosis.setConfirmatoryDiagnosisSCTTerm("Confirmatory Diagnosis SCTTerm");
		pncDiagnosis.setCounsellingProvided("Counselling Provided");
		pncDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncDiagnosis.setCreatedDate(mock(Timestamp.class));
		pncDiagnosis.setDateOfDeath(mock(Date.class));
		pncDiagnosis.setDeleted(true);
		pncDiagnosis.setExternalInvestigation("External Investigation");
		pncDiagnosis.setID(1L);
		pncDiagnosis.setIsMaternalDeath(true);
		pncDiagnosis.setLastModDate(mock(Timestamp.class));
		pncDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncDiagnosis.setParkingPlaceID(1);
		pncDiagnosis.setPlaceOfDeath("Place Of Death");
		pncDiagnosis.setPrescriptionID(1L);
		pncDiagnosis.setProcessed("Processed");
		pncDiagnosis.setProviderServiceMapID(1);
		pncDiagnosis.setProvisionalDiagnosis("Provisional Diagnosis");
		pncDiagnosis.setProvisionalDiagnosisList(new ArrayList<>());
		pncDiagnosis.setProvisionalDiagnosisSCTCode("Provisional Diagnosis SCTCode");
		pncDiagnosis.setProvisionalDiagnosisSCTTerm("Provisional Diagnosis SCTTerm");
		pncDiagnosis.setReservedForChange("Reserved For Change");
		pncDiagnosis.setSpecialistDiagnosis("Specialist Diagnosis");
		pncDiagnosis.setSyncedBy("Synced By");
		pncDiagnosis.setSyncedDate(mock(Timestamp.class));
		pncDiagnosis.setVanID(1);
		pncDiagnosis.setVanSerialNo(1L);
		pncDiagnosis.setVehicalNo("Vehical No");
		pncDiagnosis.setVisitCode(1L);
		when(pNCDiagnosisRepo.save(Mockito.<PNCDiagnosis>any())).thenReturn(pncDiagnosis);

		JsonObject obj = new JsonObject();
		obj.addProperty("Property", true);

		// Act
		Long actualSaveBenPNCDiagnosisResult = pNCDoctorServiceImpl.saveBenPNCDiagnosis(obj, 1L);

		// Assert
		verify(pncDiagnosis, atLeast(1)).getID();
		verify(pncDiagnosis).setBenVisitID(isA(Long.class));
		verify(pncDiagnosis).setBeneficiaryRegID(isA(Long.class));
		verify(pncDiagnosis).setCauseOfDeath(eq("Cause Of Death"));
		verify(pncDiagnosis).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
		verify(pncDiagnosis).setConfirmatoryDiagnosisList(isA(ArrayList.class));
		verify(pncDiagnosis).setConfirmatoryDiagnosisSCTCode(eq("Confirmatory Diagnosis SCTCode"));
		verify(pncDiagnosis).setConfirmatoryDiagnosisSCTTerm(eq("Confirmatory Diagnosis SCTTerm"));
		verify(pncDiagnosis).setCounsellingProvided(eq("Counselling Provided"));
		verify(pncDiagnosis).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
		verify(pncDiagnosis).setCreatedDate(isA(Timestamp.class));
		verify(pncDiagnosis).setDateOfDeath(isA(Date.class));
		verify(pncDiagnosis).setDeleted(isA(Boolean.class));
		verify(pncDiagnosis).setExternalInvestigation(eq("External Investigation"));
		verify(pncDiagnosis).setID(isA(Long.class));
		verify(pncDiagnosis).setIsMaternalDeath(isA(Boolean.class));
		verify(pncDiagnosis).setLastModDate(isA(Timestamp.class));
		verify(pncDiagnosis).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
		verify(pncDiagnosis).setParkingPlaceID(isA(Integer.class));
		verify(pncDiagnosis).setPlaceOfDeath(eq("Place Of Death"));
		verify(pncDiagnosis).setPrescriptionID(isA(Long.class));
		verify(pncDiagnosis).setProcessed(eq("Processed"));
		verify(pncDiagnosis).setProviderServiceMapID(isA(Integer.class));
		verify(pncDiagnosis).setProvisionalDiagnosis(eq("Provisional Diagnosis"));
		verify(pncDiagnosis).setProvisionalDiagnosisList(isA(ArrayList.class));
		verify(pncDiagnosis).setProvisionalDiagnosisSCTCode(eq("Provisional Diagnosis SCTCode"));
		verify(pncDiagnosis).setProvisionalDiagnosisSCTTerm(eq("Provisional Diagnosis SCTTerm"));
		verify(pncDiagnosis).setReservedForChange(eq("Reserved For Change"));
		verify(pncDiagnosis).setSpecialistDiagnosis(eq("Specialist Diagnosis"));
		verify(pncDiagnosis).setSyncedBy(eq("Synced By"));
		verify(pncDiagnosis).setSyncedDate(isA(Timestamp.class));
		verify(pncDiagnosis).setVanID(isA(Integer.class));
		verify(pncDiagnosis).setVanSerialNo(isA(Long.class));
		verify(pncDiagnosis).setVehicalNo(eq("Vehical No"));
		verify(pncDiagnosis).setVisitCode(isA(Long.class));
		verify(pNCDiagnosisRepo).save(isA(PNCDiagnosis.class));
		assertEquals(1L, actualSaveBenPNCDiagnosisResult.longValue());
	}

	@Test
	void testGetPNCDiagnosisDetails() {
		// Arrange
		when(pNCDiagnosisRepo.findByBeneficiaryRegIDAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any()))
				.thenReturn(new ArrayList<>());
		when(prescriptionDetailRepo.getExternalinvestigationForVisitCode(Mockito.<Long>any(), Mockito.<Long>any()))
				.thenReturn(new ArrayList<>());

		// Act
		String actualPNCDiagnosisDetails = pNCDoctorServiceImpl.getPNCDiagnosisDetails(1L, 1L);

		// Assert
		verify(pNCDiagnosisRepo).findByBeneficiaryRegIDAndVisitCode(isA(Long.class), isA(Long.class));
		verify(prescriptionDetailRepo).getExternalinvestigationForVisitCode(isA(Long.class), isA(Long.class));
		assertEquals("{}", actualPNCDiagnosisDetails);
	}

	@Test
	void testUpdateBenPNCDiagnosis() throws IEMRException {
		// Arrange
		when(pNCDiagnosisRepo.updatePNCDiagnosis(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any(),
				Mockito.<String>any(), Mockito.<Date>any(), Mockito.<String>any(), Mockito.<String>any(),
				Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any()))
						.thenReturn(1);
		when(pNCDiagnosisRepo.getPNCDiagnosisStatus(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Long>any()))
				.thenReturn("Pnc Diagnosis Status");

		PNCDiagnosis pncDiagnosis = new PNCDiagnosis();
		pncDiagnosis.setBenVisitID(1L);
		pncDiagnosis.setBeneficiaryRegID(1L);
		pncDiagnosis.setCauseOfDeath("Cause Of Death");
		pncDiagnosis.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
		pncDiagnosis.setConfirmatoryDiagnosisList(new ArrayList<>());
		pncDiagnosis.setConfirmatoryDiagnosisSCTCode("Confirmatory Diagnosis SCTCode");
		pncDiagnosis.setConfirmatoryDiagnosisSCTTerm("Confirmatory Diagnosis SCTTerm");
		pncDiagnosis.setCounsellingProvided("Counselling Provided");
		pncDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncDiagnosis.setCreatedDate(mock(Timestamp.class));
		pncDiagnosis.setDateOfDeath(mock(Date.class));
		pncDiagnosis.setDeleted(true);
		pncDiagnosis.setExternalInvestigation("External Investigation");
		pncDiagnosis.setID(1L);
		pncDiagnosis.setIsMaternalDeath(true);
		pncDiagnosis.setLastModDate(mock(Timestamp.class));
		pncDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncDiagnosis.setParkingPlaceID(1);
		pncDiagnosis.setPlaceOfDeath("Place Of Death");
		pncDiagnosis.setPrescriptionID(1L);
		pncDiagnosis.setProcessed("Processed");
		pncDiagnosis.setProviderServiceMapID(1);
		pncDiagnosis.setProvisionalDiagnosis("Provisional Diagnosis");
		pncDiagnosis.setProvisionalDiagnosisList(new ArrayList<>());
		pncDiagnosis.setProvisionalDiagnosisSCTCode("Provisional Diagnosis SCTCode");
		pncDiagnosis.setProvisionalDiagnosisSCTTerm("Provisional Diagnosis SCTTerm");
		pncDiagnosis.setReservedForChange("Reserved For Change");
		pncDiagnosis.setSpecialistDiagnosis("Specialist Diagnosis");
		pncDiagnosis.setSyncedBy("Synced By");
		pncDiagnosis.setSyncedDate(mock(Timestamp.class));
		pncDiagnosis.setVanID(1);
		pncDiagnosis.setVanSerialNo(1L);
		pncDiagnosis.setVehicalNo("Vehical No");
		pncDiagnosis.setVisitCode(1L);

		// Act
		int actualUpdateBenPNCDiagnosisResult = pNCDoctorServiceImpl.updateBenPNCDiagnosis(pncDiagnosis);

		// Assert
		verify(pNCDiagnosisRepo).getPNCDiagnosisStatus(isA(Long.class), isA(Long.class), isA(Long.class));
		verify(pNCDiagnosisRepo).updatePNCDiagnosis(eq(""), eq(""), isA(Boolean.class), eq("Place Of Death"),
				isA(Date.class), eq("Cause Of Death"), eq("Jan 1, 2020 8:00am GMT+0100"), eq("U"), isA(Long.class),
				isA(Long.class), eq(""), eq("Provisional Diagnosis SCTTerm"), eq(""),
				eq("Confirmatory Diagnosis SCTTerm"), isA(Long.class));
		assertEquals("", pncDiagnosis.getConfirmatoryDiagnosis());
		assertEquals("", pncDiagnosis.getConfirmatoryDiagnosisSCTCode());
		assertEquals("", pncDiagnosis.getProvisionalDiagnosis());
		assertEquals("", pncDiagnosis.getProvisionalDiagnosisSCTCode());
		assertEquals(1, actualUpdateBenPNCDiagnosisResult);
	}

	@Test
	void testGettersAndSetters() {
		PNCDoctorServiceImpl pncDoctorServiceImpl = new PNCDoctorServiceImpl();

		// Act
		pncDoctorServiceImpl.setCommonDoctorServiceImpl(new CommonDoctorServiceImpl());
		pncDoctorServiceImpl.setPncDiagnosisRepo(mock(PNCDiagnosisRepo.class));
		pncDoctorServiceImpl.setPrescriptionDetailRepo(mock(PrescriptionDetailRepo.class));
	}
}
