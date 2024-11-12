package com.iemr.hwc.service.quickConsultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.location.Districts;
import com.iemr.hwc.data.location.States;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.nurse.CDSS;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.provider.ProviderServiceMapping;
import com.iemr.hwc.data.quickConsultation.BenClinicalObservations;
import com.iemr.hwc.data.quickConsultation.ExternalLabTestOrder;
import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.data.snomedct.SCTDescription;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.location.ZoneDistrictMapping;
import com.iemr.hwc.repo.nurse.BenPhysicalVitalRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repo.quickConsultation.BenClinicalObservationsRepo;
import com.iemr.hwc.repo.quickConsultation.ExternalTestOrderRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.generalOPD.GeneralOPDDoctorServiceImpl;
import com.iemr.hwc.service.labtechnician.LabTechnicianServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;

@ExtendWith(MockitoExtension.class)
class QuickConsultationServiceImplTest {
    @Mock
    private BenAdherenceRepo benAdherenceRepo;

    @Mock
    private BenChiefComplaintRepo benChiefComplaintRepo;

    @Mock
    private BenClinicalObservationsRepo benClinicalObservationsRepo;

    @Mock
    private BenPhysicalVitalRepo benPhysicalVitalRepo;

    @Mock
    private BenVisitDetailRepo benVisitDetailRepo;

    @Mock
    private CDSSRepo cDSSRepo;

    @Mock
    private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

    @Mock
    private CommonDoctorServiceImpl commonDoctorServiceImpl;

    @Mock
    private CommonNurseServiceImpl commonNurseServiceImpl;

    @Mock
    private CommonServiceImpl commonServiceImpl;

    @Mock
    private ExternalTestOrderRepo externalTestOrderRepo;

    @Mock
    private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;

    @Mock
    private LabTechnicianServiceImpl labTechnicianServiceImpl;

    @Mock
    private PrescriptionDetailRepo prescriptionDetailRepo;

    @InjectMocks
    private QuickConsultationServiceImpl quickConsultationServiceImpl;

    @Mock
    private SMSGatewayServiceImpl sMSGatewayServiceImpl;

    @Mock
    private TeleConsultationServiceImpl teleConsultationServiceImpl;

    @Test
    void testSaveBeneficiaryChiefComplaint() {
        // Arrange, Act and Assert
        assertEquals(1L, quickConsultationServiceImpl.saveBeneficiaryChiefComplaint(new JsonObject()).longValue());
    }

    @Test
    void testSaveBeneficiaryChiefComplaint2() {
        // Arrange
        JsonObject caseSheet = new JsonObject();
        caseSheet.add("chiefComplaintList", new JsonArray(3));

        // Act and Assert
        assertEquals(1L, quickConsultationServiceImpl.saveBeneficiaryChiefComplaint(caseSheet).longValue());
    }

    @Test
    void testSaveBeneficiaryChiefComplaint3() {
        // Arrange
        JsonObject caseSheet = new JsonObject();
        caseSheet.add("Property", new JsonArray(3));
        caseSheet.add("chiefComplaintList", new JsonArray(3));

        // Act and Assert
        assertEquals(1L, quickConsultationServiceImpl.saveBeneficiaryChiefComplaint(caseSheet).longValue());
    }

    @Test
    void testSaveBeneficiaryChiefComplaint4() {
        // Arrange
        JsonObject caseSheet = new JsonObject();
        caseSheet.addProperty("chiefComplaintList", "42");

        // Act and Assert
        assertEquals(1L, quickConsultationServiceImpl.saveBeneficiaryChiefComplaint(caseSheet).longValue());
    }

    @Test
    void testSaveBeneficiaryChiefComplaint5() {
        // Arrange
        JsonObject caseSheet = new JsonObject();
        caseSheet.add("Property", new JsonArray(3));

        // Act and Assert
        assertEquals(1L, quickConsultationServiceImpl.saveBeneficiaryChiefComplaint(caseSheet).longValue());
    }

    @Test
    void testSaveBeneficiaryChiefComplaint6() {
        // Arrange
        JsonObject caseSheet = new JsonObject();
        caseSheet.addProperty("chiefComplaintList", (String) null);

        // Act and Assert
        assertEquals(1L, quickConsultationServiceImpl.saveBeneficiaryChiefComplaint(caseSheet).longValue());
    }

    @Test
    void testSaveBeneficiaryClinicalObservations() throws Exception {
        // Arrange
        BenClinicalObservations benClinicalObservations = new BenClinicalObservations();
        benClinicalObservations.setBenVisitID(1L);
        benClinicalObservations.setBeneficiaryRegID(1L);
        benClinicalObservations.setCaptureDate(mock(Date.class));
        benClinicalObservations.setClinicalObservation("Clinical Observation");
        benClinicalObservations.setClinicalObservationID(1L);
        benClinicalObservations.setClinicalObservationSctcode("Clinical Observation Sctcode");
        benClinicalObservations.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benClinicalObservations.setCreatedDate(mock(Timestamp.class));
        benClinicalObservations.setDeleted(true);
        benClinicalObservations.setIsForHistory(true);
        benClinicalObservations.setLastModDate(mock(Timestamp.class));
        benClinicalObservations.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benClinicalObservations.setOtherSymptoms("Other Symptoms");
        benClinicalObservations.setOtherSymptomsSCTCode("Other Symptoms SCTCode");
        benClinicalObservations.setOtherSymptomsSCTTerm("Other Symptoms SCTTerm");
        benClinicalObservations.setParkingPlaceID(1);
        benClinicalObservations.setProcessed("Processed");
        benClinicalObservations.setProviderServiceMapID(1);
        benClinicalObservations.setReservedForChange("Reserved For Change");
        benClinicalObservations.setSignificantFindings("Significant Findings");
        benClinicalObservations.setSignificantFindingsSctcode("Significant Findings Sctcode");
        benClinicalObservations.setSyncedBy("Synced By");
        benClinicalObservations.setSyncedDate(mock(Timestamp.class));
        benClinicalObservations.setVanID(1);
        benClinicalObservations.setVanSerialNo(1L);
        benClinicalObservations.setVehicalNo("Vehical No");
        benClinicalObservations.setVisitCode(1L);
        when(benClinicalObservationsRepo.save(Mockito.<BenClinicalObservations>any())).thenReturn(benClinicalObservations);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});

        // Act
        Long actualSaveBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .saveBeneficiaryClinicalObservations(new JsonObject());

        // Assert
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1L, actualSaveBeneficiaryClinicalObservationsResult.longValue());
    }

    @Test
    void testSaveBeneficiaryClinicalObservations2() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> quickConsultationServiceImpl.saveBeneficiaryClinicalObservations(new JsonObject()));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
    }

    @Test
    void testSaveBeneficiaryClinicalObservations3() throws Exception {
        // Arrange
        BenClinicalObservations benClinicalObservations = mock(BenClinicalObservations.class);
        when(benClinicalObservations.getClinicalObservationID()).thenReturn(-1L);
        doNothing().when(benClinicalObservations).setBenVisitID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setCaptureDate(Mockito.<Date>any());
        doNothing().when(benClinicalObservations).setClinicalObservation(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setClinicalObservationID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setClinicalObservationSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setDeleted(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setIsForHistory(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setModifiedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptoms(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTCode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTTerm(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setProcessed(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setReservedForChange(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindings(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindingsSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setVanID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setVehicalNo(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setVisitCode(Mockito.<Long>any());
        benClinicalObservations.setBenVisitID(1L);
        benClinicalObservations.setBeneficiaryRegID(1L);
        benClinicalObservations.setCaptureDate(mock(Date.class));
        benClinicalObservations.setClinicalObservation("Clinical Observation");
        benClinicalObservations.setClinicalObservationID(1L);
        benClinicalObservations.setClinicalObservationSctcode("Clinical Observation Sctcode");
        benClinicalObservations.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benClinicalObservations.setCreatedDate(mock(Timestamp.class));
        benClinicalObservations.setDeleted(true);
        benClinicalObservations.setIsForHistory(true);
        benClinicalObservations.setLastModDate(mock(Timestamp.class));
        benClinicalObservations.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benClinicalObservations.setOtherSymptoms("Other Symptoms");
        benClinicalObservations.setOtherSymptomsSCTCode("Other Symptoms SCTCode");
        benClinicalObservations.setOtherSymptomsSCTTerm("Other Symptoms SCTTerm");
        benClinicalObservations.setParkingPlaceID(1);
        benClinicalObservations.setProcessed("Processed");
        benClinicalObservations.setProviderServiceMapID(1);
        benClinicalObservations.setReservedForChange("Reserved For Change");
        benClinicalObservations.setSignificantFindings("Significant Findings");
        benClinicalObservations.setSignificantFindingsSctcode("Significant Findings Sctcode");
        benClinicalObservations.setSyncedBy("Synced By");
        benClinicalObservations.setSyncedDate(mock(Timestamp.class));
        benClinicalObservations.setVanID(1);
        benClinicalObservations.setVanSerialNo(1L);
        benClinicalObservations.setVehicalNo("Vehical No");
        benClinicalObservations.setVisitCode(1L);
        when(benClinicalObservationsRepo.save(Mockito.<BenClinicalObservations>any())).thenReturn(benClinicalObservations);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});

        // Act
        Long actualSaveBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .saveBeneficiaryClinicalObservations(new JsonObject());

        // Assert
        verify(benClinicalObservations).getClinicalObservationID();
        verify(benClinicalObservations).setBenVisitID(isA(Long.class));
        verify(benClinicalObservations).setBeneficiaryRegID(isA(Long.class));
        verify(benClinicalObservations).setCaptureDate(isA(Date.class));
        verify(benClinicalObservations).setClinicalObservation(eq("Clinical Observation"));
        verify(benClinicalObservations).setClinicalObservationID(isA(Long.class));
        verify(benClinicalObservations).setClinicalObservationSctcode(eq("Clinical Observation Sctcode"));
        verify(benClinicalObservations).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(benClinicalObservations).setCreatedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setDeleted(isA(Boolean.class));
        verify(benClinicalObservations).setIsForHistory(isA(Boolean.class));
        verify(benClinicalObservations).setLastModDate(isA(Timestamp.class));
        verify(benClinicalObservations).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(benClinicalObservations).setOtherSymptoms(eq("Other Symptoms"));
        verify(benClinicalObservations).setOtherSymptomsSCTCode(eq("Other Symptoms SCTCode"));
        verify(benClinicalObservations).setOtherSymptomsSCTTerm(eq("Other Symptoms SCTTerm"));
        verify(benClinicalObservations).setParkingPlaceID(isA(Integer.class));
        verify(benClinicalObservations).setProcessed(eq("Processed"));
        verify(benClinicalObservations).setProviderServiceMapID(isA(Integer.class));
        verify(benClinicalObservations).setReservedForChange(eq("Reserved For Change"));
        verify(benClinicalObservations).setSignificantFindings(eq("Significant Findings"));
        verify(benClinicalObservations).setSignificantFindingsSctcode(eq("Significant Findings Sctcode"));
        verify(benClinicalObservations).setSyncedBy(eq("Synced By"));
        verify(benClinicalObservations).setSyncedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setVanID(isA(Integer.class));
        verify(benClinicalObservations).setVanSerialNo(isA(Long.class));
        verify(benClinicalObservations).setVehicalNo(eq("Vehical No"));
        verify(benClinicalObservations).setVisitCode(isA(Long.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertNull(actualSaveBeneficiaryClinicalObservationsResult);
    }

    @Test
    void testSaveBeneficiaryClinicalObservations4() throws Exception {
        // Arrange
        BenClinicalObservations benClinicalObservations = mock(BenClinicalObservations.class);
        when(benClinicalObservations.getClinicalObservationID()).thenReturn(1L);
        doNothing().when(benClinicalObservations).setBenVisitID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setCaptureDate(Mockito.<Date>any());
        doNothing().when(benClinicalObservations).setClinicalObservation(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setClinicalObservationID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setClinicalObservationSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setDeleted(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setIsForHistory(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setModifiedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptoms(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTCode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTTerm(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setProcessed(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setReservedForChange(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindings(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindingsSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setVanID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setVehicalNo(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setVisitCode(Mockito.<Long>any());
        benClinicalObservations.setBenVisitID(1L);
        benClinicalObservations.setBeneficiaryRegID(1L);
        benClinicalObservations.setCaptureDate(mock(Date.class));
        benClinicalObservations.setClinicalObservation("Clinical Observation");
        benClinicalObservations.setClinicalObservationID(1L);
        benClinicalObservations.setClinicalObservationSctcode("Clinical Observation Sctcode");
        benClinicalObservations.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benClinicalObservations.setCreatedDate(mock(Timestamp.class));
        benClinicalObservations.setDeleted(true);
        benClinicalObservations.setIsForHistory(true);
        benClinicalObservations.setLastModDate(mock(Timestamp.class));
        benClinicalObservations.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benClinicalObservations.setOtherSymptoms("Other Symptoms");
        benClinicalObservations.setOtherSymptomsSCTCode("Other Symptoms SCTCode");
        benClinicalObservations.setOtherSymptomsSCTTerm("Other Symptoms SCTTerm");
        benClinicalObservations.setParkingPlaceID(1);
        benClinicalObservations.setProcessed("Processed");
        benClinicalObservations.setProviderServiceMapID(1);
        benClinicalObservations.setReservedForChange("Reserved For Change");
        benClinicalObservations.setSignificantFindings("Significant Findings");
        benClinicalObservations.setSignificantFindingsSctcode("Significant Findings Sctcode");
        benClinicalObservations.setSyncedBy("Synced By");
        benClinicalObservations.setSyncedDate(mock(Timestamp.class));
        benClinicalObservations.setVanID(1);
        benClinicalObservations.setVanSerialNo(1L);
        benClinicalObservations.setVehicalNo("Vehical No");
        benClinicalObservations.setVisitCode(1L);
        when(benClinicalObservationsRepo.save(Mockito.<BenClinicalObservations>any())).thenReturn(benClinicalObservations);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"foo", ""});

        // Act
        Long actualSaveBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .saveBeneficiaryClinicalObservations(new JsonObject());

        // Assert
        verify(benClinicalObservations, atLeast(1)).getClinicalObservationID();
        verify(benClinicalObservations).setBenVisitID(isA(Long.class));
        verify(benClinicalObservations).setBeneficiaryRegID(isA(Long.class));
        verify(benClinicalObservations).setCaptureDate(isA(Date.class));
        verify(benClinicalObservations).setClinicalObservation(eq("Clinical Observation"));
        verify(benClinicalObservations).setClinicalObservationID(isA(Long.class));
        verify(benClinicalObservations).setClinicalObservationSctcode(eq("Clinical Observation Sctcode"));
        verify(benClinicalObservations).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(benClinicalObservations).setCreatedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setDeleted(isA(Boolean.class));
        verify(benClinicalObservations).setIsForHistory(isA(Boolean.class));
        verify(benClinicalObservations).setLastModDate(isA(Timestamp.class));
        verify(benClinicalObservations).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(benClinicalObservations).setOtherSymptoms(eq("Other Symptoms"));
        verify(benClinicalObservations).setOtherSymptomsSCTCode(eq("Other Symptoms SCTCode"));
        verify(benClinicalObservations).setOtherSymptomsSCTTerm(eq("Other Symptoms SCTTerm"));
        verify(benClinicalObservations).setParkingPlaceID(isA(Integer.class));
        verify(benClinicalObservations).setProcessed(eq("Processed"));
        verify(benClinicalObservations).setProviderServiceMapID(isA(Integer.class));
        verify(benClinicalObservations).setReservedForChange(eq("Reserved For Change"));
        verify(benClinicalObservations).setSignificantFindings(eq("Significant Findings"));
        verify(benClinicalObservations).setSignificantFindingsSctcode(eq("Significant Findings Sctcode"));
        verify(benClinicalObservations).setSyncedBy(eq("Synced By"));
        verify(benClinicalObservations).setSyncedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setVanID(isA(Integer.class));
        verify(benClinicalObservations).setVanSerialNo(isA(Long.class));
        verify(benClinicalObservations).setVehicalNo(eq("Vehical No"));
        verify(benClinicalObservations).setVisitCode(isA(Long.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1L, actualSaveBeneficiaryClinicalObservationsResult.longValue());
    }

    @Test
    void testSaveBeneficiaryClinicalObservations5() throws Exception {
        // Arrange
        BenClinicalObservations benClinicalObservations = mock(BenClinicalObservations.class);
        when(benClinicalObservations.getClinicalObservationID()).thenReturn(1L);
        doNothing().when(benClinicalObservations).setBenVisitID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setCaptureDate(Mockito.<Date>any());
        doNothing().when(benClinicalObservations).setClinicalObservation(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setClinicalObservationID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setClinicalObservationSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setDeleted(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setIsForHistory(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setModifiedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptoms(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTCode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTTerm(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setProcessed(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setReservedForChange(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindings(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindingsSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setVanID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setVehicalNo(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setVisitCode(Mockito.<Long>any());
        benClinicalObservations.setBenVisitID(1L);
        benClinicalObservations.setBeneficiaryRegID(1L);
        benClinicalObservations.setCaptureDate(mock(Date.class));
        benClinicalObservations.setClinicalObservation("Clinical Observation");
        benClinicalObservations.setClinicalObservationID(1L);
        benClinicalObservations.setClinicalObservationSctcode("Clinical Observation Sctcode");
        benClinicalObservations.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benClinicalObservations.setCreatedDate(mock(Timestamp.class));
        benClinicalObservations.setDeleted(true);
        benClinicalObservations.setIsForHistory(true);
        benClinicalObservations.setLastModDate(mock(Timestamp.class));
        benClinicalObservations.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benClinicalObservations.setOtherSymptoms("Other Symptoms");
        benClinicalObservations.setOtherSymptomsSCTCode("Other Symptoms SCTCode");
        benClinicalObservations.setOtherSymptomsSCTTerm("Other Symptoms SCTTerm");
        benClinicalObservations.setParkingPlaceID(1);
        benClinicalObservations.setProcessed("Processed");
        benClinicalObservations.setProviderServiceMapID(1);
        benClinicalObservations.setReservedForChange("Reserved For Change");
        benClinicalObservations.setSignificantFindings("Significant Findings");
        benClinicalObservations.setSignificantFindingsSctcode("Significant Findings Sctcode");
        benClinicalObservations.setSyncedBy("Synced By");
        benClinicalObservations.setSyncedDate(mock(Timestamp.class));
        benClinicalObservations.setVanID(1);
        benClinicalObservations.setVanSerialNo(1L);
        benClinicalObservations.setVehicalNo("Vehical No");
        benClinicalObservations.setVisitCode(1L);
        when(benClinicalObservationsRepo.save(Mockito.<BenClinicalObservations>any())).thenReturn(benClinicalObservations);

        // Act
        Long actualSaveBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .saveBeneficiaryClinicalObservations(null);

        // Assert
        verify(benClinicalObservations, atLeast(1)).getClinicalObservationID();
        verify(benClinicalObservations).setBenVisitID(isA(Long.class));
        verify(benClinicalObservations).setBeneficiaryRegID(isA(Long.class));
        verify(benClinicalObservations).setCaptureDate(isA(Date.class));
        verify(benClinicalObservations).setClinicalObservation(eq("Clinical Observation"));
        verify(benClinicalObservations).setClinicalObservationID(isA(Long.class));
        verify(benClinicalObservations).setClinicalObservationSctcode(eq("Clinical Observation Sctcode"));
        verify(benClinicalObservations).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(benClinicalObservations).setCreatedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setDeleted(isA(Boolean.class));
        verify(benClinicalObservations).setIsForHistory(isA(Boolean.class));
        verify(benClinicalObservations).setLastModDate(isA(Timestamp.class));
        verify(benClinicalObservations).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(benClinicalObservations).setOtherSymptoms(eq("Other Symptoms"));
        verify(benClinicalObservations).setOtherSymptomsSCTCode(eq("Other Symptoms SCTCode"));
        verify(benClinicalObservations).setOtherSymptomsSCTTerm(eq("Other Symptoms SCTTerm"));
        verify(benClinicalObservations).setParkingPlaceID(isA(Integer.class));
        verify(benClinicalObservations).setProcessed(eq("Processed"));
        verify(benClinicalObservations).setProviderServiceMapID(isA(Integer.class));
        verify(benClinicalObservations).setReservedForChange(eq("Reserved For Change"));
        verify(benClinicalObservations).setSignificantFindings(eq("Significant Findings"));
        verify(benClinicalObservations).setSignificantFindingsSctcode(eq("Significant Findings Sctcode"));
        verify(benClinicalObservations).setSyncedBy(eq("Synced By"));
        verify(benClinicalObservations).setSyncedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setVanID(isA(Integer.class));
        verify(benClinicalObservations).setVanSerialNo(isA(Long.class));
        verify(benClinicalObservations).setVehicalNo(eq("Vehical No"));
        verify(benClinicalObservations).setVisitCode(isA(Long.class));
        verify(benClinicalObservationsRepo).save(isNull());
        assertEquals(1L, actualSaveBeneficiaryClinicalObservationsResult.longValue());
    }

    @Test
    void testSaveBeneficiaryClinicalObservations6() throws Exception {
        // Arrange
        BenClinicalObservations benClinicalObservations = mock(BenClinicalObservations.class);
        when(benClinicalObservations.getClinicalObservationID()).thenReturn(1L);
        doNothing().when(benClinicalObservations).setBenVisitID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setCaptureDate(Mockito.<Date>any());
        doNothing().when(benClinicalObservations).setClinicalObservation(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setClinicalObservationID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setClinicalObservationSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setDeleted(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setIsForHistory(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setModifiedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptoms(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTCode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTTerm(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setProcessed(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setReservedForChange(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindings(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindingsSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setVanID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setVehicalNo(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setVisitCode(Mockito.<Long>any());
        benClinicalObservations.setBenVisitID(1L);
        benClinicalObservations.setBeneficiaryRegID(1L);
        benClinicalObservations.setCaptureDate(mock(Date.class));
        benClinicalObservations.setClinicalObservation("Clinical Observation");
        benClinicalObservations.setClinicalObservationID(1L);
        benClinicalObservations.setClinicalObservationSctcode("Clinical Observation Sctcode");
        benClinicalObservations.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benClinicalObservations.setCreatedDate(mock(Timestamp.class));
        benClinicalObservations.setDeleted(true);
        benClinicalObservations.setIsForHistory(true);
        benClinicalObservations.setLastModDate(mock(Timestamp.class));
        benClinicalObservations.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benClinicalObservations.setOtherSymptoms("Other Symptoms");
        benClinicalObservations.setOtherSymptomsSCTCode("Other Symptoms SCTCode");
        benClinicalObservations.setOtherSymptomsSCTTerm("Other Symptoms SCTTerm");
        benClinicalObservations.setParkingPlaceID(1);
        benClinicalObservations.setProcessed("Processed");
        benClinicalObservations.setProviderServiceMapID(1);
        benClinicalObservations.setReservedForChange("Reserved For Change");
        benClinicalObservations.setSignificantFindings("Significant Findings");
        benClinicalObservations.setSignificantFindingsSctcode("Significant Findings Sctcode");
        benClinicalObservations.setSyncedBy("Synced By");
        benClinicalObservations.setSyncedDate(mock(Timestamp.class));
        benClinicalObservations.setVanID(1);
        benClinicalObservations.setVanSerialNo(1L);
        benClinicalObservations.setVehicalNo("Vehical No");
        benClinicalObservations.setVisitCode(1L);
        when(benClinicalObservationsRepo.save(Mockito.<BenClinicalObservations>any())).thenReturn(benClinicalObservations);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});

        JsonObject caseSheet = new JsonObject();
        caseSheet.add("Property", new JsonArray(3));

        // Act
        Long actualSaveBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .saveBeneficiaryClinicalObservations(caseSheet);

        // Assert
        verify(benClinicalObservations, atLeast(1)).getClinicalObservationID();
        verify(benClinicalObservations).setBenVisitID(isA(Long.class));
        verify(benClinicalObservations).setBeneficiaryRegID(isA(Long.class));
        verify(benClinicalObservations).setCaptureDate(isA(Date.class));
        verify(benClinicalObservations).setClinicalObservation(eq("Clinical Observation"));
        verify(benClinicalObservations).setClinicalObservationID(isA(Long.class));
        verify(benClinicalObservations).setClinicalObservationSctcode(eq("Clinical Observation Sctcode"));
        verify(benClinicalObservations).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(benClinicalObservations).setCreatedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setDeleted(isA(Boolean.class));
        verify(benClinicalObservations).setIsForHistory(isA(Boolean.class));
        verify(benClinicalObservations).setLastModDate(isA(Timestamp.class));
        verify(benClinicalObservations).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(benClinicalObservations).setOtherSymptoms(eq("Other Symptoms"));
        verify(benClinicalObservations).setOtherSymptomsSCTCode(eq("Other Symptoms SCTCode"));
        verify(benClinicalObservations).setOtherSymptomsSCTTerm(eq("Other Symptoms SCTTerm"));
        verify(benClinicalObservations).setParkingPlaceID(isA(Integer.class));
        verify(benClinicalObservations).setProcessed(eq("Processed"));
        verify(benClinicalObservations).setProviderServiceMapID(isA(Integer.class));
        verify(benClinicalObservations).setReservedForChange(eq("Reserved For Change"));
        verify(benClinicalObservations).setSignificantFindings(eq("Significant Findings"));
        verify(benClinicalObservations).setSignificantFindingsSctcode(eq("Significant Findings Sctcode"));
        verify(benClinicalObservations).setSyncedBy(eq("Synced By"));
        verify(benClinicalObservations).setSyncedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setVanID(isA(Integer.class));
        verify(benClinicalObservations).setVanSerialNo(isA(Long.class));
        verify(benClinicalObservations).setVehicalNo(eq("Vehical No"));
        verify(benClinicalObservations).setVisitCode(isA(Long.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1L, actualSaveBeneficiaryClinicalObservationsResult.longValue());
    }

    @Test
    void testSaveBeneficiaryClinicalObservations7() throws Exception {
        // Arrange
        BenClinicalObservations benClinicalObservations = mock(BenClinicalObservations.class);
        when(benClinicalObservations.getClinicalObservationID()).thenReturn(1L);
        doNothing().when(benClinicalObservations).setBenVisitID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setCaptureDate(Mockito.<Date>any());
        doNothing().when(benClinicalObservations).setClinicalObservation(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setClinicalObservationID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setClinicalObservationSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setDeleted(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setIsForHistory(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setModifiedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptoms(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTCode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTTerm(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setProcessed(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setReservedForChange(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindings(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindingsSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setVanID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setVehicalNo(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setVisitCode(Mockito.<Long>any());
        benClinicalObservations.setBenVisitID(1L);
        benClinicalObservations.setBeneficiaryRegID(1L);
        benClinicalObservations.setCaptureDate(mock(Date.class));
        benClinicalObservations.setClinicalObservation("Clinical Observation");
        benClinicalObservations.setClinicalObservationID(1L);
        benClinicalObservations.setClinicalObservationSctcode("Clinical Observation Sctcode");
        benClinicalObservations.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benClinicalObservations.setCreatedDate(mock(Timestamp.class));
        benClinicalObservations.setDeleted(true);
        benClinicalObservations.setIsForHistory(true);
        benClinicalObservations.setLastModDate(mock(Timestamp.class));
        benClinicalObservations.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benClinicalObservations.setOtherSymptoms("Other Symptoms");
        benClinicalObservations.setOtherSymptomsSCTCode("Other Symptoms SCTCode");
        benClinicalObservations.setOtherSymptomsSCTTerm("Other Symptoms SCTTerm");
        benClinicalObservations.setParkingPlaceID(1);
        benClinicalObservations.setProcessed("Processed");
        benClinicalObservations.setProviderServiceMapID(1);
        benClinicalObservations.setReservedForChange("Reserved For Change");
        benClinicalObservations.setSignificantFindings("Significant Findings");
        benClinicalObservations.setSignificantFindingsSctcode("Significant Findings Sctcode");
        benClinicalObservations.setSyncedBy("Synced By");
        benClinicalObservations.setSyncedDate(mock(Timestamp.class));
        benClinicalObservations.setVanID(1);
        benClinicalObservations.setVanSerialNo(1L);
        benClinicalObservations.setVehicalNo("Vehical No");
        benClinicalObservations.setVisitCode(1L);
        when(benClinicalObservationsRepo.save(Mockito.<BenClinicalObservations>any())).thenReturn(benClinicalObservations);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});

        JsonObject caseSheet = new JsonObject();
        caseSheet.addProperty("Property", "42");

        // Act
        Long actualSaveBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .saveBeneficiaryClinicalObservations(caseSheet);

        // Assert
        verify(benClinicalObservations, atLeast(1)).getClinicalObservationID();
        verify(benClinicalObservations).setBenVisitID(isA(Long.class));
        verify(benClinicalObservations).setBeneficiaryRegID(isA(Long.class));
        verify(benClinicalObservations).setCaptureDate(isA(Date.class));
        verify(benClinicalObservations).setClinicalObservation(eq("Clinical Observation"));
        verify(benClinicalObservations).setClinicalObservationID(isA(Long.class));
        verify(benClinicalObservations).setClinicalObservationSctcode(eq("Clinical Observation Sctcode"));
        verify(benClinicalObservations).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(benClinicalObservations).setCreatedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setDeleted(isA(Boolean.class));
        verify(benClinicalObservations).setIsForHistory(isA(Boolean.class));
        verify(benClinicalObservations).setLastModDate(isA(Timestamp.class));
        verify(benClinicalObservations).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(benClinicalObservations).setOtherSymptoms(eq("Other Symptoms"));
        verify(benClinicalObservations).setOtherSymptomsSCTCode(eq("Other Symptoms SCTCode"));
        verify(benClinicalObservations).setOtherSymptomsSCTTerm(eq("Other Symptoms SCTTerm"));
        verify(benClinicalObservations).setParkingPlaceID(isA(Integer.class));
        verify(benClinicalObservations).setProcessed(eq("Processed"));
        verify(benClinicalObservations).setProviderServiceMapID(isA(Integer.class));
        verify(benClinicalObservations).setReservedForChange(eq("Reserved For Change"));
        verify(benClinicalObservations).setSignificantFindings(eq("Significant Findings"));
        verify(benClinicalObservations).setSignificantFindingsSctcode(eq("Significant Findings Sctcode"));
        verify(benClinicalObservations).setSyncedBy(eq("Synced By"));
        verify(benClinicalObservations).setSyncedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setVanID(isA(Integer.class));
        verify(benClinicalObservations).setVanSerialNo(isA(Long.class));
        verify(benClinicalObservations).setVehicalNo(eq("Vehical No"));
        verify(benClinicalObservations).setVisitCode(isA(Long.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1L, actualSaveBeneficiaryClinicalObservationsResult.longValue());
    }

    @Test
    void testSaveBeneficiaryClinicalObservations8() throws Exception {
        // Arrange
        BenClinicalObservations benClinicalObservations = mock(BenClinicalObservations.class);
        when(benClinicalObservations.getClinicalObservationID()).thenReturn(1L);
        doNothing().when(benClinicalObservations).setBenVisitID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setCaptureDate(Mockito.<Date>any());
        doNothing().when(benClinicalObservations).setClinicalObservation(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setClinicalObservationID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setClinicalObservationSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setDeleted(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setIsForHistory(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setModifiedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptoms(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTCode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTTerm(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setProcessed(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setReservedForChange(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindings(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindingsSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setVanID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setVehicalNo(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setVisitCode(Mockito.<Long>any());
        benClinicalObservations.setBenVisitID(1L);
        benClinicalObservations.setBeneficiaryRegID(1L);
        benClinicalObservations.setCaptureDate(mock(Date.class));
        benClinicalObservations.setClinicalObservation("Clinical Observation");
        benClinicalObservations.setClinicalObservationID(1L);
        benClinicalObservations.setClinicalObservationSctcode("Clinical Observation Sctcode");
        benClinicalObservations.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benClinicalObservations.setCreatedDate(mock(Timestamp.class));
        benClinicalObservations.setDeleted(true);
        benClinicalObservations.setIsForHistory(true);
        benClinicalObservations.setLastModDate(mock(Timestamp.class));
        benClinicalObservations.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benClinicalObservations.setOtherSymptoms("Other Symptoms");
        benClinicalObservations.setOtherSymptomsSCTCode("Other Symptoms SCTCode");
        benClinicalObservations.setOtherSymptomsSCTTerm("Other Symptoms SCTTerm");
        benClinicalObservations.setParkingPlaceID(1);
        benClinicalObservations.setProcessed("Processed");
        benClinicalObservations.setProviderServiceMapID(1);
        benClinicalObservations.setReservedForChange("Reserved For Change");
        benClinicalObservations.setSignificantFindings("Significant Findings");
        benClinicalObservations.setSignificantFindingsSctcode("Significant Findings Sctcode");
        benClinicalObservations.setSyncedBy("Synced By");
        benClinicalObservations.setSyncedDate(mock(Timestamp.class));
        benClinicalObservations.setVanID(1);
        benClinicalObservations.setVanSerialNo(1L);
        benClinicalObservations.setVehicalNo("Vehical No");
        benClinicalObservations.setVisitCode(1L);
        when(benClinicalObservationsRepo.save(Mockito.<BenClinicalObservations>any())).thenReturn(benClinicalObservations);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});

        JsonObject caseSheet = new JsonObject();
        caseSheet.addProperty("Property", Integer.valueOf(1));

        // Act
        Long actualSaveBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .saveBeneficiaryClinicalObservations(caseSheet);

        // Assert
        verify(benClinicalObservations, atLeast(1)).getClinicalObservationID();
        verify(benClinicalObservations).setBenVisitID(isA(Long.class));
        verify(benClinicalObservations).setBeneficiaryRegID(isA(Long.class));
        verify(benClinicalObservations).setCaptureDate(isA(Date.class));
        verify(benClinicalObservations).setClinicalObservation(eq("Clinical Observation"));
        verify(benClinicalObservations).setClinicalObservationID(isA(Long.class));
        verify(benClinicalObservations).setClinicalObservationSctcode(eq("Clinical Observation Sctcode"));
        verify(benClinicalObservations).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(benClinicalObservations).setCreatedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setDeleted(isA(Boolean.class));
        verify(benClinicalObservations).setIsForHistory(isA(Boolean.class));
        verify(benClinicalObservations).setLastModDate(isA(Timestamp.class));
        verify(benClinicalObservations).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(benClinicalObservations).setOtherSymptoms(eq("Other Symptoms"));
        verify(benClinicalObservations).setOtherSymptomsSCTCode(eq("Other Symptoms SCTCode"));
        verify(benClinicalObservations).setOtherSymptomsSCTTerm(eq("Other Symptoms SCTTerm"));
        verify(benClinicalObservations).setParkingPlaceID(isA(Integer.class));
        verify(benClinicalObservations).setProcessed(eq("Processed"));
        verify(benClinicalObservations).setProviderServiceMapID(isA(Integer.class));
        verify(benClinicalObservations).setReservedForChange(eq("Reserved For Change"));
        verify(benClinicalObservations).setSignificantFindings(eq("Significant Findings"));
        verify(benClinicalObservations).setSignificantFindingsSctcode(eq("Significant Findings Sctcode"));
        verify(benClinicalObservations).setSyncedBy(eq("Synced By"));
        verify(benClinicalObservations).setSyncedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setVanID(isA(Integer.class));
        verify(benClinicalObservations).setVanSerialNo(isA(Long.class));
        verify(benClinicalObservations).setVehicalNo(eq("Vehical No"));
        verify(benClinicalObservations).setVisitCode(isA(Long.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1L, actualSaveBeneficiaryClinicalObservationsResult.longValue());
    }

    @Test
    void testSaveBeneficiaryClinicalObservations9() throws Exception {
        // Arrange
        BenClinicalObservations benClinicalObservations = mock(BenClinicalObservations.class);
        when(benClinicalObservations.getClinicalObservationID()).thenReturn(1L);
        doNothing().when(benClinicalObservations).setBenVisitID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setCaptureDate(Mockito.<Date>any());
        doNothing().when(benClinicalObservations).setClinicalObservation(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setClinicalObservationID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setClinicalObservationSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setDeleted(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setIsForHistory(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setModifiedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptoms(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTCode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTTerm(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setProcessed(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setReservedForChange(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindings(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindingsSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setVanID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setVehicalNo(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setVisitCode(Mockito.<Long>any());
        benClinicalObservations.setBenVisitID(1L);
        benClinicalObservations.setBeneficiaryRegID(1L);
        benClinicalObservations.setCaptureDate(mock(Date.class));
        benClinicalObservations.setClinicalObservation("Clinical Observation");
        benClinicalObservations.setClinicalObservationID(1L);
        benClinicalObservations.setClinicalObservationSctcode("Clinical Observation Sctcode");
        benClinicalObservations.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benClinicalObservations.setCreatedDate(mock(Timestamp.class));
        benClinicalObservations.setDeleted(true);
        benClinicalObservations.setIsForHistory(true);
        benClinicalObservations.setLastModDate(mock(Timestamp.class));
        benClinicalObservations.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benClinicalObservations.setOtherSymptoms("Other Symptoms");
        benClinicalObservations.setOtherSymptomsSCTCode("Other Symptoms SCTCode");
        benClinicalObservations.setOtherSymptomsSCTTerm("Other Symptoms SCTTerm");
        benClinicalObservations.setParkingPlaceID(1);
        benClinicalObservations.setProcessed("Processed");
        benClinicalObservations.setProviderServiceMapID(1);
        benClinicalObservations.setReservedForChange("Reserved For Change");
        benClinicalObservations.setSignificantFindings("Significant Findings");
        benClinicalObservations.setSignificantFindingsSctcode("Significant Findings Sctcode");
        benClinicalObservations.setSyncedBy("Synced By");
        benClinicalObservations.setSyncedDate(mock(Timestamp.class));
        benClinicalObservations.setVanID(1);
        benClinicalObservations.setVanSerialNo(1L);
        benClinicalObservations.setVehicalNo("Vehical No");
        benClinicalObservations.setVisitCode(1L);
        when(benClinicalObservationsRepo.save(Mockito.<BenClinicalObservations>any())).thenReturn(benClinicalObservations);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});

        JsonObject caseSheet = new JsonObject();
        caseSheet.addProperty("Property", true);

        // Act
        Long actualSaveBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .saveBeneficiaryClinicalObservations(caseSheet);

        // Assert
        verify(benClinicalObservations, atLeast(1)).getClinicalObservationID();
        verify(benClinicalObservations).setBenVisitID(isA(Long.class));
        verify(benClinicalObservations).setBeneficiaryRegID(isA(Long.class));
        verify(benClinicalObservations).setCaptureDate(isA(Date.class));
        verify(benClinicalObservations).setClinicalObservation(eq("Clinical Observation"));
        verify(benClinicalObservations).setClinicalObservationID(isA(Long.class));
        verify(benClinicalObservations).setClinicalObservationSctcode(eq("Clinical Observation Sctcode"));
        verify(benClinicalObservations).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(benClinicalObservations).setCreatedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setDeleted(isA(Boolean.class));
        verify(benClinicalObservations).setIsForHistory(isA(Boolean.class));
        verify(benClinicalObservations).setLastModDate(isA(Timestamp.class));
        verify(benClinicalObservations).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(benClinicalObservations).setOtherSymptoms(eq("Other Symptoms"));
        verify(benClinicalObservations).setOtherSymptomsSCTCode(eq("Other Symptoms SCTCode"));
        verify(benClinicalObservations).setOtherSymptomsSCTTerm(eq("Other Symptoms SCTTerm"));
        verify(benClinicalObservations).setParkingPlaceID(isA(Integer.class));
        verify(benClinicalObservations).setProcessed(eq("Processed"));
        verify(benClinicalObservations).setProviderServiceMapID(isA(Integer.class));
        verify(benClinicalObservations).setReservedForChange(eq("Reserved For Change"));
        verify(benClinicalObservations).setSignificantFindings(eq("Significant Findings"));
        verify(benClinicalObservations).setSignificantFindingsSctcode(eq("Significant Findings Sctcode"));
        verify(benClinicalObservations).setSyncedBy(eq("Synced By"));
        verify(benClinicalObservations).setSyncedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setVanID(isA(Integer.class));
        verify(benClinicalObservations).setVanSerialNo(isA(Long.class));
        verify(benClinicalObservations).setVehicalNo(eq("Vehical No"));
        verify(benClinicalObservations).setVisitCode(isA(Long.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1L, actualSaveBeneficiaryClinicalObservationsResult.longValue());
    }

    /**
     * Method under test:
     * {@link QuickConsultationServiceImpl#saveBenPrescriptionForANC(PrescriptionDetail)}
     */
    @Test
    void testSaveBenPrescriptionForANC() {
        // Arrange
        PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
        prescriptionDetail.setBenVisitID(1L);
        prescriptionDetail.setBeneficiaryRegID(1L);
        prescriptionDetail.setBreastCancerConfirmed(true);
        prescriptionDetail.setCervicalCancerConfirmed(true);
        prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
        prescriptionDetail.setCounsellingProvided("Counselling Provided");
        prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
        prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        prescriptionDetail.setCreatedDate(mock(Timestamp.class));
        prescriptionDetail.setDeleted(true);
        prescriptionDetail.setDiabetesScreeningConfirmed(true);
        prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
        prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
        prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
        prescriptionDetail.setExternalInvestigation("External Investigation");
        prescriptionDetail.setHypertensionScreeningConfirmed(true);
        prescriptionDetail.setInstruction("Instruction");
        prescriptionDetail.setLastModDate(mock(Timestamp.class));
        prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        prescriptionDetail.setOralCancerConfirmed(true);
        prescriptionDetail.setParkingPlaceID(1);
        prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
        prescriptionDetail.setPrescriptionID(1L);
        prescriptionDetail.setProcessed("Processed");
        prescriptionDetail.setProviderServiceMapID(1);
        prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
        prescriptionDetail.setRemarks("Remarks");
        prescriptionDetail.setReservedForChange("Reserved For Change");
        prescriptionDetail.setSyncedBy("Synced By");
        prescriptionDetail.setSyncedDate(mock(Timestamp.class));
        prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
        prescriptionDetail.setVanID(1);
        prescriptionDetail.setVanSerialNo(1L);
        prescriptionDetail.setVehicalNo("Vehical No");
        prescriptionDetail.setVisitCode(1L);
        when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);

        PrescriptionDetail prescription = new PrescriptionDetail();
        prescription.setBenVisitID(1L);
        prescription.setBeneficiaryRegID(1L);
        prescription.setBreastCancerConfirmed(true);
        prescription.setCervicalCancerConfirmed(true);
        prescription.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
        prescription.setCounsellingProvided("Counselling Provided");
        prescription.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
        prescription.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        prescription.setCreatedDate(mock(Timestamp.class));
        prescription.setDeleted(true);
        prescription.setDiabetesScreeningConfirmed(true);
        prescription.setDiagnosisProvided("Diagnosis Provided");
        prescription.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
        prescription.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
        prescription.setExternalInvestigation("External Investigation");
        prescription.setHypertensionScreeningConfirmed(true);
        prescription.setInstruction("Instruction");
        prescription.setLastModDate(mock(Timestamp.class));
        prescription.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        prescription.setOralCancerConfirmed(true);
        prescription.setParkingPlaceID(1);
        prescription.setPrescribedDrugs(new ArrayList<>());
        prescription.setPrescriptionID(1L);
        prescription.setProcessed("Processed");
        prescription.setProviderServiceMapID(1);
        prescription.setProvisionalDiagnosisList(new ArrayList<>());
        prescription.setRemarks("Remarks");
        prescription.setReservedForChange("Reserved For Change");
        prescription.setSyncedBy("Synced By");
        prescription.setSyncedDate(mock(Timestamp.class));
        prescription.setTreatmentsOnSideEffects("Treatments On Side Effects");
        prescription.setVanID(1);
        prescription.setVanSerialNo(1L);
        prescription.setVehicalNo("Vehical No");
        prescription.setVisitCode(1L);

        // Act
        Long actualSaveBenPrescriptionForANCResult = quickConsultationServiceImpl.saveBenPrescriptionForANC(prescription);

        // Assert
        verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
        assertEquals(1L, actualSaveBenPrescriptionForANCResult.longValue());
    }

    @Test
    void testSaveBenPrescriptionForANC2() {
        // Arrange
        PrescriptionDetail prescriptionDetail = mock(PrescriptionDetail.class);
        when(prescriptionDetail.getPrescriptionID()).thenReturn(-1L);
        doNothing().when(prescriptionDetail).setBenVisitID(Mockito.<Long>any());
        doNothing().when(prescriptionDetail).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(prescriptionDetail).setBreastCancerConfirmed(Mockito.<Boolean>any());
        doNothing().when(prescriptionDetail).setCervicalCancerConfirmed(Mockito.<Boolean>any());
        doNothing().when(prescriptionDetail).setConfirmatoryDiagnosis(Mockito.<String>any());
        doNothing().when(prescriptionDetail).setCounsellingProvided(Mockito.<String>any());
        doNothing().when(prescriptionDetail).setCounsellingProvidedList(Mockito.<String[]>any());
        doNothing().when(prescriptionDetail).setCreatedBy(Mockito.<String>any());
        doNothing().when(prescriptionDetail).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(prescriptionDetail).setDeleted(Mockito.<Boolean>any());
        doNothing().when(prescriptionDetail).setDiabetesScreeningConfirmed(Mockito.<Boolean>any());
        doNothing().when(prescriptionDetail).setDiagnosisProvided(Mockito.<String>any());
        doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTCode(Mockito.<String>any());
        doNothing().when(prescriptionDetail).setDiagnosisProvided_SCTTerm(Mockito.<String>any());
        doNothing().when(prescriptionDetail).setExternalInvestigation(Mockito.<String>any());
        doNothing().when(prescriptionDetail).setHypertensionScreeningConfirmed(Mockito.<Boolean>any());
        doNothing().when(prescriptionDetail).setInstruction(Mockito.<String>any());
        doNothing().when(prescriptionDetail).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(prescriptionDetail).setModifiedBy(Mockito.<String>any());
        doNothing().when(prescriptionDetail).setOralCancerConfirmed(Mockito.<Boolean>any());
        doNothing().when(prescriptionDetail).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(prescriptionDetail).setPrescribedDrugs(Mockito.<ArrayList<PrescribedDrugDetail>>any());
        doNothing().when(prescriptionDetail).setPrescriptionID(Mockito.<Long>any());
        doNothing().when(prescriptionDetail).setProcessed(Mockito.<String>any());
        doNothing().when(prescriptionDetail).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(prescriptionDetail).setProvisionalDiagnosisList(Mockito.<ArrayList<SCTDescription>>any());
        doNothing().when(prescriptionDetail).setRemarks(Mockito.<String>any());
        doNothing().when(prescriptionDetail).setReservedForChange(Mockito.<String>any());
        doNothing().when(prescriptionDetail).setSyncedBy(Mockito.<String>any());
        doNothing().when(prescriptionDetail).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(prescriptionDetail).setTreatmentsOnSideEffects(Mockito.<String>any());
        doNothing().when(prescriptionDetail).setVanID(Mockito.<Integer>any());
        doNothing().when(prescriptionDetail).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(prescriptionDetail).setVehicalNo(Mockito.<String>any());
        doNothing().when(prescriptionDetail).setVisitCode(Mockito.<Long>any());
        prescriptionDetail.setBenVisitID(1L);
        prescriptionDetail.setBeneficiaryRegID(1L);
        prescriptionDetail.setBreastCancerConfirmed(true);
        prescriptionDetail.setCervicalCancerConfirmed(true);
        prescriptionDetail.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
        prescriptionDetail.setCounsellingProvided("Counselling Provided");
        prescriptionDetail.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
        prescriptionDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        prescriptionDetail.setCreatedDate(mock(Timestamp.class));
        prescriptionDetail.setDeleted(true);
        prescriptionDetail.setDiabetesScreeningConfirmed(true);
        prescriptionDetail.setDiagnosisProvided("Diagnosis Provided");
        prescriptionDetail.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
        prescriptionDetail.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
        prescriptionDetail.setExternalInvestigation("External Investigation");
        prescriptionDetail.setHypertensionScreeningConfirmed(true);
        prescriptionDetail.setInstruction("Instruction");
        prescriptionDetail.setLastModDate(mock(Timestamp.class));
        prescriptionDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        prescriptionDetail.setOralCancerConfirmed(true);
        prescriptionDetail.setParkingPlaceID(1);
        prescriptionDetail.setPrescribedDrugs(new ArrayList<>());
        prescriptionDetail.setPrescriptionID(1L);
        prescriptionDetail.setProcessed("Processed");
        prescriptionDetail.setProviderServiceMapID(1);
        prescriptionDetail.setProvisionalDiagnosisList(new ArrayList<>());
        prescriptionDetail.setRemarks("Remarks");
        prescriptionDetail.setReservedForChange("Reserved For Change");
        prescriptionDetail.setSyncedBy("Synced By");
        prescriptionDetail.setSyncedDate(mock(Timestamp.class));
        prescriptionDetail.setTreatmentsOnSideEffects("Treatments On Side Effects");
        prescriptionDetail.setVanID(1);
        prescriptionDetail.setVanSerialNo(1L);
        prescriptionDetail.setVehicalNo("Vehical No");
        prescriptionDetail.setVisitCode(1L);
        when(prescriptionDetailRepo.save(Mockito.<PrescriptionDetail>any())).thenReturn(prescriptionDetail);

        PrescriptionDetail prescription = new PrescriptionDetail();
        prescription.setBenVisitID(1L);
        prescription.setBeneficiaryRegID(1L);
        prescription.setBreastCancerConfirmed(true);
        prescription.setCervicalCancerConfirmed(true);
        prescription.setConfirmatoryDiagnosis("Confirmatory Diagnosis");
        prescription.setCounsellingProvided("Counselling Provided");
        prescription.setCounsellingProvidedList(new String[]{"Counselling Provided List"});
        prescription.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        prescription.setCreatedDate(mock(Timestamp.class));
        prescription.setDeleted(true);
        prescription.setDiabetesScreeningConfirmed(true);
        prescription.setDiagnosisProvided("Diagnosis Provided");
        prescription.setDiagnosisProvided_SCTCode("Diagnosis Provided SCTCode");
        prescription.setDiagnosisProvided_SCTTerm("Diagnosis Provided SCTTerm");
        prescription.setExternalInvestigation("External Investigation");
        prescription.setHypertensionScreeningConfirmed(true);
        prescription.setInstruction("Instruction");
        prescription.setLastModDate(mock(Timestamp.class));
        prescription.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        prescription.setOralCancerConfirmed(true);
        prescription.setParkingPlaceID(1);
        prescription.setPrescribedDrugs(new ArrayList<>());
        prescription.setPrescriptionID(1L);
        prescription.setProcessed("Processed");
        prescription.setProviderServiceMapID(1);
        prescription.setProvisionalDiagnosisList(new ArrayList<>());
        prescription.setRemarks("Remarks");
        prescription.setReservedForChange("Reserved For Change");
        prescription.setSyncedBy("Synced By");
        prescription.setSyncedDate(mock(Timestamp.class));
        prescription.setTreatmentsOnSideEffects("Treatments On Side Effects");
        prescription.setVanID(1);
        prescription.setVanSerialNo(1L);
        prescription.setVehicalNo("Vehical No");
        prescription.setVisitCode(1L);

        // Act
        Long actualSaveBenPrescriptionForANCResult = quickConsultationServiceImpl.saveBenPrescriptionForANC(prescription);

        // Assert
        verify(prescriptionDetail).getPrescriptionID();
        verify(prescriptionDetail).setBenVisitID(isA(Long.class));
        verify(prescriptionDetail).setBeneficiaryRegID(isA(Long.class));
        verify(prescriptionDetail).setBreastCancerConfirmed(isA(Boolean.class));
        verify(prescriptionDetail).setCervicalCancerConfirmed(isA(Boolean.class));
        verify(prescriptionDetail).setConfirmatoryDiagnosis(eq("Confirmatory Diagnosis"));
        verify(prescriptionDetail).setCounsellingProvided(eq("Counselling Provided"));
        verify(prescriptionDetail).setCounsellingProvidedList(isA(String[].class));
        verify(prescriptionDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(prescriptionDetail).setCreatedDate(isA(Timestamp.class));
        verify(prescriptionDetail).setDeleted(isA(Boolean.class));
        verify(prescriptionDetail).setDiabetesScreeningConfirmed(isA(Boolean.class));
        verify(prescriptionDetail).setDiagnosisProvided(eq("Diagnosis Provided"));
        verify(prescriptionDetail).setDiagnosisProvided_SCTCode(eq("Diagnosis Provided SCTCode"));
        verify(prescriptionDetail).setDiagnosisProvided_SCTTerm(eq("Diagnosis Provided SCTTerm"));
        verify(prescriptionDetail).setExternalInvestigation(eq("External Investigation"));
        verify(prescriptionDetail).setHypertensionScreeningConfirmed(isA(Boolean.class));
        verify(prescriptionDetail).setInstruction(eq("Instruction"));
        verify(prescriptionDetail).setLastModDate(isA(Timestamp.class));
        verify(prescriptionDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(prescriptionDetail).setOralCancerConfirmed(isA(Boolean.class));
        verify(prescriptionDetail).setParkingPlaceID(isA(Integer.class));
        verify(prescriptionDetail).setPrescribedDrugs(isA(ArrayList.class));
        verify(prescriptionDetail).setPrescriptionID(isA(Long.class));
        verify(prescriptionDetail).setProcessed(eq("Processed"));
        verify(prescriptionDetail).setProviderServiceMapID(isA(Integer.class));
        verify(prescriptionDetail).setProvisionalDiagnosisList(isA(ArrayList.class));
        verify(prescriptionDetail).setRemarks(eq("Remarks"));
        verify(prescriptionDetail).setReservedForChange(eq("Reserved For Change"));
        verify(prescriptionDetail).setSyncedBy(eq("Synced By"));
        verify(prescriptionDetail).setSyncedDate(isA(Timestamp.class));
        verify(prescriptionDetail).setTreatmentsOnSideEffects(eq("Treatments On Side Effects"));
        verify(prescriptionDetail).setVanID(isA(Integer.class));
        verify(prescriptionDetail).setVanSerialNo(isA(Long.class));
        verify(prescriptionDetail).setVehicalNo(eq("Vehical No"));
        verify(prescriptionDetail).setVisitCode(isA(Long.class));
        verify(prescriptionDetailRepo).save(isA(PrescriptionDetail.class));
        assertNull(actualSaveBenPrescriptionForANCResult);
    }

    @Test
    void testSaveBeneficiaryExternalLabTestOrderDetails() {
        // Arrange
        ExternalLabTestOrder externalLabTestOrder = mock(ExternalLabTestOrder.class);
        when(externalLabTestOrder.getExternalTestOrderID()).thenReturn(1L);
        doNothing().when(externalLabTestOrder).setBenVisitID(Mockito.<Long>any());
        doNothing().when(externalLabTestOrder).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(externalLabTestOrder).setCreatedBy(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(externalLabTestOrder).setDeleted(Mockito.<Boolean>any());
        doNothing().when(externalLabTestOrder).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(externalLabTestOrder).setModifiedBy(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(externalLabTestOrder).setProcessed(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(externalLabTestOrder).setReservedForChange(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setSyncedBy(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(externalLabTestOrder).setTestName(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setTestReport(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setTestResult(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(externalLabTestOrder).setVehicalNo(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setVisitCode(Mockito.<Long>any());
        externalLabTestOrder.setBenVisitID(1L);
        externalLabTestOrder.setBeneficiaryRegID(1L);
        externalLabTestOrder.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        externalLabTestOrder.setCreatedDate(mock(Timestamp.class));
        externalLabTestOrder.setDeleted(true);
        externalLabTestOrder.setLastModDate(mock(Timestamp.class));
        externalLabTestOrder.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        externalLabTestOrder.setParkingPlaceID(1);
        externalLabTestOrder.setProcessed("Processed");
        externalLabTestOrder.setProviderServiceMapID(1);
        externalLabTestOrder.setReservedForChange("Reserved For Change");
        externalLabTestOrder.setSyncedBy("Synced By");
        externalLabTestOrder.setSyncedDate(mock(Timestamp.class));
        externalLabTestOrder.setTestName("Test Name");
        externalLabTestOrder.setTestReport("Test Report");
        externalLabTestOrder.setTestResult("Test Result");
        externalLabTestOrder.setVanSerialNo(1L);
        externalLabTestOrder.setVehicalNo("Vehical No");
        externalLabTestOrder.setVisitCode(1L);
        when(externalTestOrderRepo.save(Mockito.<ExternalLabTestOrder>any())).thenReturn(externalLabTestOrder);

        // Act
        Long actualSaveBeneficiaryExternalLabTestOrderDetailsResult = quickConsultationServiceImpl
                .saveBeneficiaryExternalLabTestOrderDetails(new JsonObject());

        // Assert
        verify(externalLabTestOrder, atLeast(1)).getExternalTestOrderID();
        verify(externalLabTestOrder).setBenVisitID(isA(Long.class));
        verify(externalLabTestOrder).setBeneficiaryRegID(isA(Long.class));
        verify(externalLabTestOrder).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(externalLabTestOrder).setCreatedDate(isA(Timestamp.class));
        verify(externalLabTestOrder).setDeleted(isA(Boolean.class));
        verify(externalLabTestOrder).setLastModDate(isA(Timestamp.class));
        verify(externalLabTestOrder).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(externalLabTestOrder).setParkingPlaceID(isA(Integer.class));
        verify(externalLabTestOrder).setProcessed(eq("Processed"));
        verify(externalLabTestOrder).setProviderServiceMapID(isA(Integer.class));
        verify(externalLabTestOrder).setReservedForChange(eq("Reserved For Change"));
        verify(externalLabTestOrder).setSyncedBy(eq("Synced By"));
        verify(externalLabTestOrder).setSyncedDate(isA(Timestamp.class));
        verify(externalLabTestOrder).setTestName(eq("Test Name"));
        verify(externalLabTestOrder).setTestReport(eq("Test Report"));
        verify(externalLabTestOrder).setTestResult(eq("Test Result"));
        verify(externalLabTestOrder).setVanSerialNo(isA(Long.class));
        verify(externalLabTestOrder).setVehicalNo(eq("Vehical No"));
        verify(externalLabTestOrder).setVisitCode(isA(Long.class));
        verify(externalTestOrderRepo).save(isA(ExternalLabTestOrder.class));
        assertEquals(1L, actualSaveBeneficiaryExternalLabTestOrderDetailsResult.longValue());
    }

    @Test
    void testSaveBeneficiaryExternalLabTestOrderDetails2() {
        // Arrange
        ExternalLabTestOrder externalLabTestOrder = mock(ExternalLabTestOrder.class);
        when(externalLabTestOrder.getExternalTestOrderID()).thenReturn(0L);
        doNothing().when(externalLabTestOrder).setBenVisitID(Mockito.<Long>any());
        doNothing().when(externalLabTestOrder).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(externalLabTestOrder).setCreatedBy(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(externalLabTestOrder).setDeleted(Mockito.<Boolean>any());
        doNothing().when(externalLabTestOrder).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(externalLabTestOrder).setModifiedBy(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(externalLabTestOrder).setProcessed(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(externalLabTestOrder).setReservedForChange(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setSyncedBy(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(externalLabTestOrder).setTestName(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setTestReport(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setTestResult(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(externalLabTestOrder).setVehicalNo(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setVisitCode(Mockito.<Long>any());
        externalLabTestOrder.setBenVisitID(1L);
        externalLabTestOrder.setBeneficiaryRegID(1L);
        externalLabTestOrder.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        externalLabTestOrder.setCreatedDate(mock(Timestamp.class));
        externalLabTestOrder.setDeleted(true);
        externalLabTestOrder.setLastModDate(mock(Timestamp.class));
        externalLabTestOrder.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        externalLabTestOrder.setParkingPlaceID(1);
        externalLabTestOrder.setProcessed("Processed");
        externalLabTestOrder.setProviderServiceMapID(1);
        externalLabTestOrder.setReservedForChange("Reserved For Change");
        externalLabTestOrder.setSyncedBy("Synced By");
        externalLabTestOrder.setSyncedDate(mock(Timestamp.class));
        externalLabTestOrder.setTestName("Test Name");
        externalLabTestOrder.setTestReport("Test Report");
        externalLabTestOrder.setTestResult("Test Result");
        externalLabTestOrder.setVanSerialNo(1L);
        externalLabTestOrder.setVehicalNo("Vehical No");
        externalLabTestOrder.setVisitCode(1L);
        when(externalTestOrderRepo.save(Mockito.<ExternalLabTestOrder>any())).thenReturn(externalLabTestOrder);

        // Act
        Long actualSaveBeneficiaryExternalLabTestOrderDetailsResult = quickConsultationServiceImpl
                .saveBeneficiaryExternalLabTestOrderDetails(new JsonObject());

        // Assert
        verify(externalLabTestOrder).getExternalTestOrderID();
        verify(externalLabTestOrder).setBenVisitID(isA(Long.class));
        verify(externalLabTestOrder).setBeneficiaryRegID(isA(Long.class));
        verify(externalLabTestOrder).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(externalLabTestOrder).setCreatedDate(isA(Timestamp.class));
        verify(externalLabTestOrder).setDeleted(isA(Boolean.class));
        verify(externalLabTestOrder).setLastModDate(isA(Timestamp.class));
        verify(externalLabTestOrder).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(externalLabTestOrder).setParkingPlaceID(isA(Integer.class));
        verify(externalLabTestOrder).setProcessed(eq("Processed"));
        verify(externalLabTestOrder).setProviderServiceMapID(isA(Integer.class));
        verify(externalLabTestOrder).setReservedForChange(eq("Reserved For Change"));
        verify(externalLabTestOrder).setSyncedBy(eq("Synced By"));
        verify(externalLabTestOrder).setSyncedDate(isA(Timestamp.class));
        verify(externalLabTestOrder).setTestName(eq("Test Name"));
        verify(externalLabTestOrder).setTestReport(eq("Test Report"));
        verify(externalLabTestOrder).setTestResult(eq("Test Result"));
        verify(externalLabTestOrder).setVanSerialNo(isA(Long.class));
        verify(externalLabTestOrder).setVehicalNo(eq("Vehical No"));
        verify(externalLabTestOrder).setVisitCode(isA(Long.class));
        verify(externalTestOrderRepo).save(isA(ExternalLabTestOrder.class));
        assertNull(actualSaveBeneficiaryExternalLabTestOrderDetailsResult);
    }

    @Test
    void testSaveBeneficiaryExternalLabTestOrderDetails3() {
        // Arrange
        ExternalLabTestOrder externalLabTestOrder = mock(ExternalLabTestOrder.class);
        when(externalLabTestOrder.getExternalTestOrderID()).thenReturn(1L);
        doNothing().when(externalLabTestOrder).setBenVisitID(Mockito.<Long>any());
        doNothing().when(externalLabTestOrder).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(externalLabTestOrder).setCreatedBy(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(externalLabTestOrder).setDeleted(Mockito.<Boolean>any());
        doNothing().when(externalLabTestOrder).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(externalLabTestOrder).setModifiedBy(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(externalLabTestOrder).setProcessed(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(externalLabTestOrder).setReservedForChange(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setSyncedBy(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(externalLabTestOrder).setTestName(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setTestReport(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setTestResult(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(externalLabTestOrder).setVehicalNo(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setVisitCode(Mockito.<Long>any());
        externalLabTestOrder.setBenVisitID(1L);
        externalLabTestOrder.setBeneficiaryRegID(1L);
        externalLabTestOrder.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        externalLabTestOrder.setCreatedDate(mock(Timestamp.class));
        externalLabTestOrder.setDeleted(true);
        externalLabTestOrder.setLastModDate(mock(Timestamp.class));
        externalLabTestOrder.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        externalLabTestOrder.setParkingPlaceID(1);
        externalLabTestOrder.setProcessed("Processed");
        externalLabTestOrder.setProviderServiceMapID(1);
        externalLabTestOrder.setReservedForChange("Reserved For Change");
        externalLabTestOrder.setSyncedBy("Synced By");
        externalLabTestOrder.setSyncedDate(mock(Timestamp.class));
        externalLabTestOrder.setTestName("Test Name");
        externalLabTestOrder.setTestReport("Test Report");
        externalLabTestOrder.setTestResult("Test Result");
        externalLabTestOrder.setVanSerialNo(1L);
        externalLabTestOrder.setVehicalNo("Vehical No");
        externalLabTestOrder.setVisitCode(1L);
        when(externalTestOrderRepo.save(Mockito.<ExternalLabTestOrder>any())).thenReturn(externalLabTestOrder);

        JsonObject caseSheet = new JsonObject();
        caseSheet.addProperty("benVisitID", "42");

        // Act
        Long actualSaveBeneficiaryExternalLabTestOrderDetailsResult = quickConsultationServiceImpl
                .saveBeneficiaryExternalLabTestOrderDetails(caseSheet);

        // Assert
        verify(externalLabTestOrder, atLeast(1)).getExternalTestOrderID();
        verify(externalLabTestOrder).setBenVisitID(isA(Long.class));
        verify(externalLabTestOrder).setBeneficiaryRegID(isA(Long.class));
        verify(externalLabTestOrder).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(externalLabTestOrder).setCreatedDate(isA(Timestamp.class));
        verify(externalLabTestOrder).setDeleted(isA(Boolean.class));
        verify(externalLabTestOrder).setLastModDate(isA(Timestamp.class));
        verify(externalLabTestOrder).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(externalLabTestOrder).setParkingPlaceID(isA(Integer.class));
        verify(externalLabTestOrder).setProcessed(eq("Processed"));
        verify(externalLabTestOrder).setProviderServiceMapID(isA(Integer.class));
        verify(externalLabTestOrder).setReservedForChange(eq("Reserved For Change"));
        verify(externalLabTestOrder).setSyncedBy(eq("Synced By"));
        verify(externalLabTestOrder).setSyncedDate(isA(Timestamp.class));
        verify(externalLabTestOrder).setTestName(eq("Test Name"));
        verify(externalLabTestOrder).setTestReport(eq("Test Report"));
        verify(externalLabTestOrder).setTestResult(eq("Test Result"));
        verify(externalLabTestOrder).setVanSerialNo(isA(Long.class));
        verify(externalLabTestOrder).setVehicalNo(eq("Vehical No"));
        verify(externalLabTestOrder).setVisitCode(isA(Long.class));
        verify(externalTestOrderRepo).save(isA(ExternalLabTestOrder.class));
        assertEquals(1L, actualSaveBeneficiaryExternalLabTestOrderDetailsResult.longValue());
    }

  
    @Test
    void testSaveBeneficiaryExternalLabTestOrderDetails4() {
        // Arrange
        ExternalLabTestOrder externalLabTestOrder = mock(ExternalLabTestOrder.class);
        when(externalLabTestOrder.getExternalTestOrderID()).thenReturn(1L);
        doNothing().when(externalLabTestOrder).setBenVisitID(Mockito.<Long>any());
        doNothing().when(externalLabTestOrder).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(externalLabTestOrder).setCreatedBy(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(externalLabTestOrder).setDeleted(Mockito.<Boolean>any());
        doNothing().when(externalLabTestOrder).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(externalLabTestOrder).setModifiedBy(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(externalLabTestOrder).setProcessed(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(externalLabTestOrder).setReservedForChange(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setSyncedBy(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(externalLabTestOrder).setTestName(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setTestReport(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setTestResult(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(externalLabTestOrder).setVehicalNo(Mockito.<String>any());
        doNothing().when(externalLabTestOrder).setVisitCode(Mockito.<Long>any());
        externalLabTestOrder.setBenVisitID(1L);
        externalLabTestOrder.setBeneficiaryRegID(1L);
        externalLabTestOrder.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        externalLabTestOrder.setCreatedDate(mock(Timestamp.class));
        externalLabTestOrder.setDeleted(true);
        externalLabTestOrder.setLastModDate(mock(Timestamp.class));
        externalLabTestOrder.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        externalLabTestOrder.setParkingPlaceID(1);
        externalLabTestOrder.setProcessed("Processed");
        externalLabTestOrder.setProviderServiceMapID(1);
        externalLabTestOrder.setReservedForChange("Reserved For Change");
        externalLabTestOrder.setSyncedBy("Synced By");
        externalLabTestOrder.setSyncedDate(mock(Timestamp.class));
        externalLabTestOrder.setTestName("Test Name");
        externalLabTestOrder.setTestReport("Test Report");
        externalLabTestOrder.setTestResult("Test Result");
        externalLabTestOrder.setVanSerialNo(1L);
        externalLabTestOrder.setVehicalNo("Vehical No");
        externalLabTestOrder.setVisitCode(1L);
        when(externalTestOrderRepo.save(Mockito.<ExternalLabTestOrder>any())).thenReturn(externalLabTestOrder);

        JsonObject caseSheet = new JsonObject();
        caseSheet.addProperty("benVisitID", Integer.valueOf(1));

        // Act
        Long actualSaveBeneficiaryExternalLabTestOrderDetailsResult = quickConsultationServiceImpl
                .saveBeneficiaryExternalLabTestOrderDetails(caseSheet);

        // Assert
        verify(externalLabTestOrder, atLeast(1)).getExternalTestOrderID();
        verify(externalLabTestOrder).setBenVisitID(isA(Long.class));
        verify(externalLabTestOrder).setBeneficiaryRegID(isA(Long.class));
        verify(externalLabTestOrder).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(externalLabTestOrder).setCreatedDate(isA(Timestamp.class));
        verify(externalLabTestOrder).setDeleted(isA(Boolean.class));
        verify(externalLabTestOrder).setLastModDate(isA(Timestamp.class));
        verify(externalLabTestOrder).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(externalLabTestOrder).setParkingPlaceID(isA(Integer.class));
        verify(externalLabTestOrder).setProcessed(eq("Processed"));
        verify(externalLabTestOrder).setProviderServiceMapID(isA(Integer.class));
        verify(externalLabTestOrder).setReservedForChange(eq("Reserved For Change"));
        verify(externalLabTestOrder).setSyncedBy(eq("Synced By"));
        verify(externalLabTestOrder).setSyncedDate(isA(Timestamp.class));
        verify(externalLabTestOrder).setTestName(eq("Test Name"));
        verify(externalLabTestOrder).setTestReport(eq("Test Report"));
        verify(externalLabTestOrder).setTestResult(eq("Test Result"));
        verify(externalLabTestOrder).setVanSerialNo(isA(Long.class));
        verify(externalLabTestOrder).setVehicalNo(eq("Vehical No"));
        verify(externalLabTestOrder).setVisitCode(isA(Long.class));
        verify(externalTestOrderRepo).save(isA(ExternalLabTestOrder.class));
        assertEquals(1L, actualSaveBeneficiaryExternalLabTestOrderDetailsResult.longValue());
    }

    @Test
    void testQuickConsultNurseDataInsert() throws Exception {
        // Arrange, Act and Assert
        assertThrows(Exception.class,
                () -> quickConsultationServiceImpl.quickConsultNurseDataInsert(new JsonObject(), "JaneDoe"));
        assertThrows(Exception.class, () -> quickConsultationServiceImpl.quickConsultNurseDataInsert(null, "JaneDoe"));
    }

    @Test
    void testQuickConsultNurseDataInsert2() throws Exception {
        // Arrange
        JsonObject jsnOBJ = new JsonObject();
        jsnOBJ.add("Property", new JsonArray(3));

        // Act and Assert
        assertThrows(Exception.class, () -> quickConsultationServiceImpl.quickConsultNurseDataInsert(jsnOBJ, "JaneDoe"));
    }

    @Test
    void testQuickConsultNurseDataInsert3() throws Exception {
        // Arrange
        JsonObject jsnOBJ = new JsonObject();
        jsnOBJ.addProperty("visitDetails", (String) null);

        // Act and Assert
        assertThrows(Exception.class, () -> quickConsultationServiceImpl.quickConsultNurseDataInsert(jsnOBJ, "JaneDoe"));
    }

    @Test
    void testDeleteVisitDetails() throws Exception {
        // Arrange
        when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        quickConsultationServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
        verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails2() throws Exception {
        // Arrange
        when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenThrow(new RuntimeException("visitDetails"));

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> quickConsultationServiceImpl.deleteVisitDetails(requestOBJ));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
        verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails3() throws Exception {
        // Arrange
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(null);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        quickConsultationServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
    }

    @Test
    void testDeleteVisitDetails4() throws Exception {
        // Arrange
        when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("Property", new JsonArray(3));
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        quickConsultationServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
        verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails5() throws Exception {
        // Arrange
        when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.addProperty("Property", "visitDetails");
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        quickConsultationServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
        verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
    }

   
    @Test
    void testDeleteVisitDetails6() throws Exception {
        // Arrange
        when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.addProperty("Property", Integer.valueOf(1));
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        quickConsultationServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
        verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails7() throws Exception {
        // Arrange
        when(benAdherenceRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benChiefComplaintRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);
        when(benVisitDetailRepo.getVisitCode(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1L);
        when(cDSSRepo.deleteVisitDetails(Mockito.<Long>any())).thenReturn(1);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.addProperty("Property", false);
        requestOBJ.add("visitDetails", new JsonArray(3));

        // Act
        quickConsultationServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert
        verify(benVisitDetailRepo).deleteVisitDetails(isA(Long.class));
        verify(benVisitDetailRepo).getVisitCode(isNull(), isNull());
        verify(cDSSRepo).deleteVisitDetails(isA(Long.class));
        verify(benAdherenceRepo).deleteVisitDetails(isA(Long.class));
        verify(benChiefComplaintRepo).deleteVisitDetails(isA(Long.class));
    }

    @Test
    void testDeleteVisitDetails8() throws Exception {
        // Arrange
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("visitDetails", value);

        // Act
        quickConsultationServiceImpl.deleteVisitDetails(requestOBJ);

        // Assert that nothing has changed
        verify(value).isJsonNull();
    }

    @Test
    void testDeleteVisitDetails9() throws Exception {
        // Arrange
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenThrow(new RuntimeException("visitDetails"));

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("visitDetails", value);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> quickConsultationServiceImpl.deleteVisitDetails(requestOBJ));
        verify(value).isJsonNull();
    }

    @Test
    void testQuickConsultDoctorDataInsert() throws Exception {
        // Arrange
        BenClinicalObservations benClinicalObservations = new BenClinicalObservations();
        benClinicalObservations.setBenVisitID(1L);
        benClinicalObservations.setBeneficiaryRegID(1L);
        benClinicalObservations.setCaptureDate(mock(Date.class));
        benClinicalObservations.setClinicalObservation("Clinical Observation");
        benClinicalObservations.setClinicalObservationID(1L);
        benClinicalObservations.setClinicalObservationSctcode("Clinical Observation Sctcode");
        benClinicalObservations.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benClinicalObservations.setCreatedDate(mock(Timestamp.class));
        benClinicalObservations.setDeleted(true);
        benClinicalObservations.setIsForHistory(true);
        benClinicalObservations.setLastModDate(mock(Timestamp.class));
        benClinicalObservations.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benClinicalObservations.setOtherSymptoms("Other Symptoms");
        benClinicalObservations.setOtherSymptomsSCTCode("Other Symptoms SCTCode");
        benClinicalObservations.setOtherSymptomsSCTTerm("Other Symptoms SCTTerm");
        benClinicalObservations.setParkingPlaceID(1);
        benClinicalObservations.setProcessed("Processed");
        benClinicalObservations.setProviderServiceMapID(1);
        benClinicalObservations.setReservedForChange("Reserved For Change");
        benClinicalObservations.setSignificantFindings("Significant Findings");
        benClinicalObservations.setSignificantFindingsSctcode("Significant Findings Sctcode");
        benClinicalObservations.setSyncedBy("Synced By");
        benClinicalObservations.setSyncedDate(mock(Timestamp.class));
        benClinicalObservations.setVanID(1);
        benClinicalObservations.setVanSerialNo(1L);
        benClinicalObservations.setVehicalNo("Vehical No");
        benClinicalObservations.setVisitCode(1L);
        when(benClinicalObservationsRepo.save(Mockito.<BenClinicalObservations>any())).thenReturn(benClinicalObservations);
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});
        when(commonNurseServiceImpl.saveBeneficiaryPrescription(Mockito.<JsonObject>any())).thenReturn(1L);

        TeleconsultationRequestOBJ teleconsultationRequestOBJ = new TeleconsultationRequestOBJ();
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

        // Act
        Integer actualQuickConsultDoctorDataInsertResult = quickConsultationServiceImpl
                .quickConsultDoctorDataInsert(new JsonObject(), "JaneDoe");

        // Assert
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataSave(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPrescription(isA(JsonObject.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1, actualQuickConsultDoctorDataInsertResult.intValue());
    }

    @Test
    void testQuickConsultDoctorDataInsert2() throws Exception {
        // Arrange
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenThrow(new Exception("chiefComplaintList"));

        // Act and Assert
        assertThrows(Exception.class,
                () -> quickConsultationServiceImpl.quickConsultDoctorDataInsert(new JsonObject(), "JaneDoe"));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
    }

    @Test
    void testQuickConsultDoctorDataInsert3() throws Exception {
        // Arrange
        BenClinicalObservations benClinicalObservations = mock(BenClinicalObservations.class);
        when(benClinicalObservations.getClinicalObservationID()).thenReturn(-1L);
        doNothing().when(benClinicalObservations).setBenVisitID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setCaptureDate(Mockito.<Date>any());
        doNothing().when(benClinicalObservations).setClinicalObservation(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setClinicalObservationID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setClinicalObservationSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setDeleted(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setIsForHistory(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setModifiedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptoms(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTCode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTTerm(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setProcessed(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setReservedForChange(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindings(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindingsSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setVanID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setVehicalNo(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setVisitCode(Mockito.<Long>any());
        benClinicalObservations.setBenVisitID(1L);
        benClinicalObservations.setBeneficiaryRegID(1L);
        benClinicalObservations.setCaptureDate(mock(Date.class));
        benClinicalObservations.setClinicalObservation("Clinical Observation");
        benClinicalObservations.setClinicalObservationID(1L);
        benClinicalObservations.setClinicalObservationSctcode("Clinical Observation Sctcode");
        benClinicalObservations.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benClinicalObservations.setCreatedDate(mock(Timestamp.class));
        benClinicalObservations.setDeleted(true);
        benClinicalObservations.setIsForHistory(true);
        benClinicalObservations.setLastModDate(mock(Timestamp.class));
        benClinicalObservations.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benClinicalObservations.setOtherSymptoms("Other Symptoms");
        benClinicalObservations.setOtherSymptomsSCTCode("Other Symptoms SCTCode");
        benClinicalObservations.setOtherSymptomsSCTTerm("Other Symptoms SCTTerm");
        benClinicalObservations.setParkingPlaceID(1);
        benClinicalObservations.setProcessed("Processed");
        benClinicalObservations.setProviderServiceMapID(1);
        benClinicalObservations.setReservedForChange("Reserved For Change");
        benClinicalObservations.setSignificantFindings("Significant Findings");
        benClinicalObservations.setSignificantFindingsSctcode("Significant Findings Sctcode");
        benClinicalObservations.setSyncedBy("Synced By");
        benClinicalObservations.setSyncedDate(mock(Timestamp.class));
        benClinicalObservations.setVanID(1);
        benClinicalObservations.setVanSerialNo(1L);
        benClinicalObservations.setVehicalNo("Vehical No");
        benClinicalObservations.setVisitCode(1L);
        when(benClinicalObservationsRepo.save(Mockito.<BenClinicalObservations>any())).thenReturn(benClinicalObservations);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});
        when(commonNurseServiceImpl.saveBeneficiaryPrescription(Mockito.<JsonObject>any())).thenReturn(1L);

        TeleconsultationRequestOBJ teleconsultationRequestOBJ = new TeleconsultationRequestOBJ();
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> quickConsultationServiceImpl.quickConsultDoctorDataInsert(new JsonObject(), "JaneDoe"));
        verify(benClinicalObservations).getClinicalObservationID();
        verify(benClinicalObservations).setBenVisitID(isA(Long.class));
        verify(benClinicalObservations).setBeneficiaryRegID(isA(Long.class));
        verify(benClinicalObservations).setCaptureDate(isA(Date.class));
        verify(benClinicalObservations).setClinicalObservation(eq("Clinical Observation"));
        verify(benClinicalObservations).setClinicalObservationID(isA(Long.class));
        verify(benClinicalObservations).setClinicalObservationSctcode(eq("Clinical Observation Sctcode"));
        verify(benClinicalObservations).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(benClinicalObservations).setCreatedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setDeleted(isA(Boolean.class));
        verify(benClinicalObservations).setIsForHistory(isA(Boolean.class));
        verify(benClinicalObservations).setLastModDate(isA(Timestamp.class));
        verify(benClinicalObservations).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(benClinicalObservations).setOtherSymptoms(eq("Other Symptoms"));
        verify(benClinicalObservations).setOtherSymptomsSCTCode(eq("Other Symptoms SCTCode"));
        verify(benClinicalObservations).setOtherSymptomsSCTTerm(eq("Other Symptoms SCTTerm"));
        verify(benClinicalObservations).setParkingPlaceID(isA(Integer.class));
        verify(benClinicalObservations).setProcessed(eq("Processed"));
        verify(benClinicalObservations).setProviderServiceMapID(isA(Integer.class));
        verify(benClinicalObservations).setReservedForChange(eq("Reserved For Change"));
        verify(benClinicalObservations).setSignificantFindings(eq("Significant Findings"));
        verify(benClinicalObservations).setSignificantFindingsSctcode(eq("Significant Findings Sctcode"));
        verify(benClinicalObservations).setSyncedBy(eq("Synced By"));
        verify(benClinicalObservations).setSyncedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setVanID(isA(Integer.class));
        verify(benClinicalObservations).setVanSerialNo(isA(Long.class));
        verify(benClinicalObservations).setVehicalNo(eq("Vehical No"));
        verify(benClinicalObservations).setVisitCode(isA(Long.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonNurseServiceImpl).saveBeneficiaryPrescription(isA(JsonObject.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
    }

    @Test
    void testQuickConsultDoctorDataInsert4() throws Exception {
        // Arrange
        BenClinicalObservations benClinicalObservations = mock(BenClinicalObservations.class);
        when(benClinicalObservations.getClinicalObservationID()).thenReturn(1L);
        doNothing().when(benClinicalObservations).setBenVisitID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setCaptureDate(Mockito.<Date>any());
        doNothing().when(benClinicalObservations).setClinicalObservation(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setClinicalObservationID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setClinicalObservationSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setDeleted(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setIsForHistory(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setModifiedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptoms(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTCode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTTerm(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setProcessed(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setReservedForChange(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindings(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindingsSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setVanID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setVehicalNo(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setVisitCode(Mockito.<Long>any());
        benClinicalObservations.setBenVisitID(1L);
        benClinicalObservations.setBeneficiaryRegID(1L);
        benClinicalObservations.setCaptureDate(mock(Date.class));
        benClinicalObservations.setClinicalObservation("Clinical Observation");
        benClinicalObservations.setClinicalObservationID(1L);
        benClinicalObservations.setClinicalObservationSctcode("Clinical Observation Sctcode");
        benClinicalObservations.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benClinicalObservations.setCreatedDate(mock(Timestamp.class));
        benClinicalObservations.setDeleted(true);
        benClinicalObservations.setIsForHistory(true);
        benClinicalObservations.setLastModDate(mock(Timestamp.class));
        benClinicalObservations.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benClinicalObservations.setOtherSymptoms("Other Symptoms");
        benClinicalObservations.setOtherSymptomsSCTCode("Other Symptoms SCTCode");
        benClinicalObservations.setOtherSymptomsSCTTerm("Other Symptoms SCTTerm");
        benClinicalObservations.setParkingPlaceID(1);
        benClinicalObservations.setProcessed("Processed");
        benClinicalObservations.setProviderServiceMapID(1);
        benClinicalObservations.setReservedForChange("Reserved For Change");
        benClinicalObservations.setSignificantFindings("Significant Findings");
        benClinicalObservations.setSignificantFindingsSctcode("Significant Findings Sctcode");
        benClinicalObservations.setSyncedBy("Synced By");
        benClinicalObservations.setSyncedDate(mock(Timestamp.class));
        benClinicalObservations.setVanID(1);
        benClinicalObservations.setVanSerialNo(1L);
        benClinicalObservations.setVehicalNo("Vehical No");
        benClinicalObservations.setVisitCode(1L);
        when(benClinicalObservationsRepo.save(Mockito.<BenClinicalObservations>any())).thenReturn(benClinicalObservations);
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(0);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});
        when(commonNurseServiceImpl.saveBeneficiaryPrescription(Mockito.<JsonObject>any())).thenReturn(1L);

        TeleconsultationRequestOBJ teleconsultationRequestOBJ = new TeleconsultationRequestOBJ();
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> quickConsultationServiceImpl.quickConsultDoctorDataInsert(new JsonObject(), "JaneDoe"));
        verify(benClinicalObservations, atLeast(1)).getClinicalObservationID();
        verify(benClinicalObservations).setBenVisitID(isA(Long.class));
        verify(benClinicalObservations).setBeneficiaryRegID(isA(Long.class));
        verify(benClinicalObservations).setCaptureDate(isA(Date.class));
        verify(benClinicalObservations).setClinicalObservation(eq("Clinical Observation"));
        verify(benClinicalObservations).setClinicalObservationID(isA(Long.class));
        verify(benClinicalObservations).setClinicalObservationSctcode(eq("Clinical Observation Sctcode"));
        verify(benClinicalObservations).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(benClinicalObservations).setCreatedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setDeleted(isA(Boolean.class));
        verify(benClinicalObservations).setIsForHistory(isA(Boolean.class));
        verify(benClinicalObservations).setLastModDate(isA(Timestamp.class));
        verify(benClinicalObservations).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(benClinicalObservations).setOtherSymptoms(eq("Other Symptoms"));
        verify(benClinicalObservations).setOtherSymptomsSCTCode(eq("Other Symptoms SCTCode"));
        verify(benClinicalObservations).setOtherSymptomsSCTTerm(eq("Other Symptoms SCTTerm"));
        verify(benClinicalObservations).setParkingPlaceID(isA(Integer.class));
        verify(benClinicalObservations).setProcessed(eq("Processed"));
        verify(benClinicalObservations).setProviderServiceMapID(isA(Integer.class));
        verify(benClinicalObservations).setReservedForChange(eq("Reserved For Change"));
        verify(benClinicalObservations).setSignificantFindings(eq("Significant Findings"));
        verify(benClinicalObservations).setSignificantFindingsSctcode(eq("Significant Findings Sctcode"));
        verify(benClinicalObservations).setSyncedBy(eq("Synced By"));
        verify(benClinicalObservations).setSyncedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setVanID(isA(Integer.class));
        verify(benClinicalObservations).setVanSerialNo(isA(Long.class));
        verify(benClinicalObservations).setVehicalNo(eq("Vehical No"));
        verify(benClinicalObservations).setVisitCode(isA(Long.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataSave(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPrescription(isA(JsonObject.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
    }

    @Test
    void testQuickConsultDoctorDataInsert5() throws Exception {
        // Arrange
        BenClinicalObservations benClinicalObservations = mock(BenClinicalObservations.class);
        when(benClinicalObservations.getClinicalObservationID()).thenReturn(1L);
        doNothing().when(benClinicalObservations).setBenVisitID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setCaptureDate(Mockito.<Date>any());
        doNothing().when(benClinicalObservations).setClinicalObservation(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setClinicalObservationID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setClinicalObservationSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setDeleted(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setIsForHistory(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setModifiedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptoms(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTCode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTTerm(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setProcessed(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setReservedForChange(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindings(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindingsSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setVanID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setVehicalNo(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setVisitCode(Mockito.<Long>any());
        benClinicalObservations.setBenVisitID(1L);
        benClinicalObservations.setBeneficiaryRegID(1L);
        benClinicalObservations.setCaptureDate(mock(Date.class));
        benClinicalObservations.setClinicalObservation("Clinical Observation");
        benClinicalObservations.setClinicalObservationID(1L);
        benClinicalObservations.setClinicalObservationSctcode("Clinical Observation Sctcode");
        benClinicalObservations.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benClinicalObservations.setCreatedDate(mock(Timestamp.class));
        benClinicalObservations.setDeleted(true);
        benClinicalObservations.setIsForHistory(true);
        benClinicalObservations.setLastModDate(mock(Timestamp.class));
        benClinicalObservations.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benClinicalObservations.setOtherSymptoms("Other Symptoms");
        benClinicalObservations.setOtherSymptomsSCTCode("Other Symptoms SCTCode");
        benClinicalObservations.setOtherSymptomsSCTTerm("Other Symptoms SCTTerm");
        benClinicalObservations.setParkingPlaceID(1);
        benClinicalObservations.setProcessed("Processed");
        benClinicalObservations.setProviderServiceMapID(1);
        benClinicalObservations.setReservedForChange("Reserved For Change");
        benClinicalObservations.setSignificantFindings("Significant Findings");
        benClinicalObservations.setSignificantFindingsSctcode("Significant Findings Sctcode");
        benClinicalObservations.setSyncedBy("Synced By");
        benClinicalObservations.setSyncedDate(mock(Timestamp.class));
        benClinicalObservations.setVanID(1);
        benClinicalObservations.setVanSerialNo(1L);
        benClinicalObservations.setVehicalNo("Vehical No");
        benClinicalObservations.setVisitCode(1L);
        when(benClinicalObservationsRepo.save(Mockito.<BenClinicalObservations>any())).thenReturn(benClinicalObservations);
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataSave(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"foo", ""});
        when(commonNurseServiceImpl.saveBeneficiaryPrescription(Mockito.<JsonObject>any())).thenReturn(1L);

        TeleconsultationRequestOBJ teleconsultationRequestOBJ = new TeleconsultationRequestOBJ();
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

        // Act
        Integer actualQuickConsultDoctorDataInsertResult = quickConsultationServiceImpl
                .quickConsultDoctorDataInsert(new JsonObject(), "JaneDoe");

        // Assert
        verify(benClinicalObservations, atLeast(1)).getClinicalObservationID();
        verify(benClinicalObservations).setBenVisitID(isA(Long.class));
        verify(benClinicalObservations).setBeneficiaryRegID(isA(Long.class));
        verify(benClinicalObservations).setCaptureDate(isA(Date.class));
        verify(benClinicalObservations).setClinicalObservation(eq("Clinical Observation"));
        verify(benClinicalObservations).setClinicalObservationID(isA(Long.class));
        verify(benClinicalObservations).setClinicalObservationSctcode(eq("Clinical Observation Sctcode"));
        verify(benClinicalObservations).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(benClinicalObservations).setCreatedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setDeleted(isA(Boolean.class));
        verify(benClinicalObservations).setIsForHistory(isA(Boolean.class));
        verify(benClinicalObservations).setLastModDate(isA(Timestamp.class));
        verify(benClinicalObservations).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(benClinicalObservations).setOtherSymptoms(eq("Other Symptoms"));
        verify(benClinicalObservations).setOtherSymptomsSCTCode(eq("Other Symptoms SCTCode"));
        verify(benClinicalObservations).setOtherSymptomsSCTTerm(eq("Other Symptoms SCTTerm"));
        verify(benClinicalObservations).setParkingPlaceID(isA(Integer.class));
        verify(benClinicalObservations).setProcessed(eq("Processed"));
        verify(benClinicalObservations).setProviderServiceMapID(isA(Integer.class));
        verify(benClinicalObservations).setReservedForChange(eq("Reserved For Change"));
        verify(benClinicalObservations).setSignificantFindings(eq("Significant Findings"));
        verify(benClinicalObservations).setSignificantFindingsSctcode(eq("Significant Findings Sctcode"));
        verify(benClinicalObservations).setSyncedBy(eq("Synced By"));
        verify(benClinicalObservations).setSyncedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setVanID(isA(Integer.class));
        verify(benClinicalObservations).setVanSerialNo(isA(Long.class));
        verify(benClinicalObservations).setVehicalNo(eq("Vehical No"));
        verify(benClinicalObservations).setVisitCode(isA(Long.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataSave(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).saveBeneficiaryPrescription(isA(JsonObject.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1, actualQuickConsultDoctorDataInsertResult.intValue());
    }

    @Test
    void testQuickConsultDoctorDataInsert6() throws Exception {
        // Arrange
        BenClinicalObservations benClinicalObservations = mock(BenClinicalObservations.class);
        when(benClinicalObservations.getClinicalObservationID()).thenReturn(1L);
        doNothing().when(benClinicalObservations).setBenVisitID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setCaptureDate(Mockito.<Date>any());
        doNothing().when(benClinicalObservations).setClinicalObservation(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setClinicalObservationID(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setClinicalObservationSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setDeleted(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setIsForHistory(Mockito.<Boolean>any());
        doNothing().when(benClinicalObservations).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setModifiedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptoms(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTCode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setOtherSymptomsSCTTerm(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setProcessed(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setReservedForChange(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindings(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSignificantFindingsSctcode(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedBy(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(benClinicalObservations).setVanID(Mockito.<Integer>any());
        doNothing().when(benClinicalObservations).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(benClinicalObservations).setVehicalNo(Mockito.<String>any());
        doNothing().when(benClinicalObservations).setVisitCode(Mockito.<Long>any());
        benClinicalObservations.setBenVisitID(1L);
        benClinicalObservations.setBeneficiaryRegID(1L);
        benClinicalObservations.setCaptureDate(mock(Date.class));
        benClinicalObservations.setClinicalObservation("Clinical Observation");
        benClinicalObservations.setClinicalObservationID(1L);
        benClinicalObservations.setClinicalObservationSctcode("Clinical Observation Sctcode");
        benClinicalObservations.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benClinicalObservations.setCreatedDate(mock(Timestamp.class));
        benClinicalObservations.setDeleted(true);
        benClinicalObservations.setIsForHistory(true);
        benClinicalObservations.setLastModDate(mock(Timestamp.class));
        benClinicalObservations.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benClinicalObservations.setOtherSymptoms("Other Symptoms");
        benClinicalObservations.setOtherSymptomsSCTCode("Other Symptoms SCTCode");
        benClinicalObservations.setOtherSymptomsSCTTerm("Other Symptoms SCTTerm");
        benClinicalObservations.setParkingPlaceID(1);
        benClinicalObservations.setProcessed("Processed");
        benClinicalObservations.setProviderServiceMapID(1);
        benClinicalObservations.setReservedForChange("Reserved For Change");
        benClinicalObservations.setSignificantFindings("Significant Findings");
        benClinicalObservations.setSignificantFindingsSctcode("Significant Findings Sctcode");
        benClinicalObservations.setSyncedBy("Synced By");
        benClinicalObservations.setSyncedDate(mock(Timestamp.class));
        benClinicalObservations.setVanID(1);
        benClinicalObservations.setVanSerialNo(1L);
        benClinicalObservations.setVehicalNo("Vehical No");
        benClinicalObservations.setVisitCode(1L);
        when(benClinicalObservationsRepo.save(Mockito.<BenClinicalObservations>any())).thenReturn(benClinicalObservations);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});
        when(commonNurseServiceImpl.saveBeneficiaryPrescription(Mockito.<JsonObject>any())).thenReturn(0L);

        TeleconsultationRequestOBJ teleconsultationRequestOBJ = new TeleconsultationRequestOBJ();
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> quickConsultationServiceImpl.quickConsultDoctorDataInsert(new JsonObject(), "JaneDoe"));
        verify(benClinicalObservations, atLeast(1)).getClinicalObservationID();
        verify(benClinicalObservations).setBenVisitID(isA(Long.class));
        verify(benClinicalObservations).setBeneficiaryRegID(isA(Long.class));
        verify(benClinicalObservations).setCaptureDate(isA(Date.class));
        verify(benClinicalObservations).setClinicalObservation(eq("Clinical Observation"));
        verify(benClinicalObservations).setClinicalObservationID(isA(Long.class));
        verify(benClinicalObservations).setClinicalObservationSctcode(eq("Clinical Observation Sctcode"));
        verify(benClinicalObservations).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(benClinicalObservations).setCreatedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setDeleted(isA(Boolean.class));
        verify(benClinicalObservations).setIsForHistory(isA(Boolean.class));
        verify(benClinicalObservations).setLastModDate(isA(Timestamp.class));
        verify(benClinicalObservations).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(benClinicalObservations).setOtherSymptoms(eq("Other Symptoms"));
        verify(benClinicalObservations).setOtherSymptomsSCTCode(eq("Other Symptoms SCTCode"));
        verify(benClinicalObservations).setOtherSymptomsSCTTerm(eq("Other Symptoms SCTTerm"));
        verify(benClinicalObservations).setParkingPlaceID(isA(Integer.class));
        verify(benClinicalObservations).setProcessed(eq("Processed"));
        verify(benClinicalObservations).setProviderServiceMapID(isA(Integer.class));
        verify(benClinicalObservations).setReservedForChange(eq("Reserved For Change"));
        verify(benClinicalObservations).setSignificantFindings(eq("Significant Findings"));
        verify(benClinicalObservations).setSignificantFindingsSctcode(eq("Significant Findings Sctcode"));
        verify(benClinicalObservations).setSyncedBy(eq("Synced By"));
        verify(benClinicalObservations).setSyncedDate(isA(Timestamp.class));
        verify(benClinicalObservations).setVanID(isA(Integer.class));
        verify(benClinicalObservations).setVanSerialNo(isA(Long.class));
        verify(benClinicalObservations).setVehicalNo(eq("Vehical No"));
        verify(benClinicalObservations).setVisitCode(isA(Long.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonNurseServiceImpl).saveBeneficiaryPrescription(isA(JsonObject.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
    }

    @Test
    void testGetBenDataFrmNurseToDocVisitDetailsScreen() {
        // Arrange
        Timestamp createdDate = mock(Timestamp.class);
        when(createdDate.getTime()).thenReturn(10L);
        Timestamp lastModDate = mock(Timestamp.class);
        when(lastModDate.getTime()).thenReturn(10L);

        States states = new States();
        states.setCountryID(1);
        states.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        states.setCreatedDate(mock(Timestamp.class));
        states.setDeleted(true);
        states.setLastModDate(mock(Timestamp.class));
        states.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        states.setStateCode("MD");
        states.setStateID(1);
        states.setStateIName("MD");
        states.setStateName("MD");

        ZoneDistrictMapping zoneDistrictMapping = new ZoneDistrictMapping();
        zoneDistrictMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        zoneDistrictMapping.setCreatedDate(mock(Timestamp.class));
        zoneDistrictMapping.setDeleted(true);
        zoneDistrictMapping.setDistrictID(1);
        zoneDistrictMapping.setDistrictsSet(new HashSet<>());
        zoneDistrictMapping.setLastModDate(mock(Timestamp.class));
        zoneDistrictMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        zoneDistrictMapping.setProcessed("Processed");
        zoneDistrictMapping.setProviderServiceMapID(1);
        zoneDistrictMapping.setZoneDistrictMapID(1);
        zoneDistrictMapping.setZoneID(1);

        Districts m_district = new Districts();
        m_district.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        m_district.setCreatedDate(mock(Timestamp.class));
        m_district.setDeleted(true);
        m_district.setDistrictID(1);
        m_district.setDistrictName("District Name");
        m_district.setGovtLGDDistrictID(1);
        m_district.setGovtLGDStateID(1);
        m_district.setLastModDate(mock(Timestamp.class));
        m_district.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        m_district.setStateID(1);
        m_district.setStates(states);
        m_district.setZoneDistrictMapping(zoneDistrictMapping);

        States state = new States();
        state.setCountryID(1);
        state.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        state.setCreatedDate(mock(Timestamp.class));
        state.setDeleted(true);
        state.setLastModDate(mock(Timestamp.class));
        state.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        state.setStateCode("MD");
        state.setStateID(1);
        state.setStateIName("MD");
        state.setStateName("MD");

        ProviderServiceMapping providerServiceMapping = new ProviderServiceMapping();
        providerServiceMapping.setAddress("42 Main St");
        providerServiceMapping.setCityID(1);
        providerServiceMapping.setCountryID(1);
        providerServiceMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        providerServiceMapping.setCreatedDate(mock(Timestamp.class));
        providerServiceMapping.setDeleted(true);
        providerServiceMapping.setDistrictBlockID(1);
        providerServiceMapping.setDistrictID(1);
        providerServiceMapping.setLastModDate(mock(Timestamp.class));
        providerServiceMapping.setM_district(m_district);
        providerServiceMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        providerServiceMapping.setProviderServiceMapID(1);
        providerServiceMapping.setServiceID((short) 1);
        providerServiceMapping.setServiceProviderID(1);
        providerServiceMapping.setState(state);
        providerServiceMapping.setStateID(1);
        providerServiceMapping.setStatusID(1);
        Timestamp syncedDate = mock(Timestamp.class);
        when(syncedDate.getTime()).thenReturn(10L);
        Timestamp visitDateTime = mock(Timestamp.class);
        when(visitDateTime.getTime()).thenReturn(10L);

        BeneficiaryVisitDetail beneficiaryVisitDetail = new BeneficiaryVisitDetail();
        beneficiaryVisitDetail.setAction("Action");
        beneficiaryVisitDetail.setActionId(1);
        beneficiaryVisitDetail.setActionIdPc(1);
        beneficiaryVisitDetail.setActionPc("Action Pc");
        beneficiaryVisitDetail.setAlgorithm("Algorithm");
        beneficiaryVisitDetail.setAlgorithmPc("Algorithm Pc");
        beneficiaryVisitDetail.setBenVisitID(1L);
        beneficiaryVisitDetail.setBeneficiaryRegID(1L);
        beneficiaryVisitDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryVisitDetail.setCreatedDate(createdDate);
        beneficiaryVisitDetail.setDeleted(true);
        beneficiaryVisitDetail.setDiseaseSummary("Disease Summary");
        beneficiaryVisitDetail.setDiseaseSummaryID(1);
        beneficiaryVisitDetail.setFileIDs(new Integer[]{1});
        beneficiaryVisitDetail.setFollowUpForFpMethod(new String[]{"Follow Up For Fp Method"});
        beneficiaryVisitDetail.setFpMethodFollowup("Fp Method Followup");
        beneficiaryVisitDetail.setFpSideeffects("Fp Sideeffects");
        beneficiaryVisitDetail.setHealthFacilityLocation("Health Facility Location");
        beneficiaryVisitDetail.setHealthFacilityType("Health Facility Type");
        beneficiaryVisitDetail.setInformationGiven("Information Given");
        beneficiaryVisitDetail.setLastModDate(lastModDate);
        beneficiaryVisitDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryVisitDetail.setOtherFollowUpForFpMethod("Other Follow Up For Fp Method");
        beneficiaryVisitDetail.setOtherSideEffects("Other Side Effects");
        beneficiaryVisitDetail.setParkingPlaceID(1);
        beneficiaryVisitDetail.setPregnancyStatus("Pregnancy Status");
        beneficiaryVisitDetail.setPresentChiefComplaint("Present Chief Complaint");
        beneficiaryVisitDetail.setPresentChiefComplaintID("Present Chief Complaint ID");
        beneficiaryVisitDetail.setProcessed("Processed");
        beneficiaryVisitDetail.setProviderServiceMapID(1);
        beneficiaryVisitDetail.setProviderServiceMapping(providerServiceMapping);
        beneficiaryVisitDetail.setRecommendedAction("Recommended Action");
        beneficiaryVisitDetail.setRecommendedActionPc("Recommended Action Pc");
        beneficiaryVisitDetail.setRemarks("Remarks");
        beneficiaryVisitDetail.setRemarksPc("Remarks Pc");
        beneficiaryVisitDetail.setReportFilePath("/directory/foo.txt");
        beneficiaryVisitDetail.setReservedForChange("Reserved For Change");
        beneficiaryVisitDetail.setSelectedDiagnosis("Selected Diagnosis");
        beneficiaryVisitDetail.setSelectedDiagnosisID("Selected Diagnosis ID");
        beneficiaryVisitDetail.setServiceProviderName("Service Provider Name");
        beneficiaryVisitDetail.setSideEffects(new String[]{"Side Effects"});
        beneficiaryVisitDetail.setSubVisitCategory("Sub Visit Category");
        beneficiaryVisitDetail.setSyncedBy("Synced By");
        beneficiaryVisitDetail.setSyncedDate(syncedDate);
        beneficiaryVisitDetail.setVanID(1);
        beneficiaryVisitDetail.setVanSerialNo(1L);
        beneficiaryVisitDetail.setVehicalNo("Vehical No");
        beneficiaryVisitDetail.setVisitCategory("Visit Category");
        beneficiaryVisitDetail.setVisitCategoryID(1);
        beneficiaryVisitDetail.setVisitCode(1L);
        beneficiaryVisitDetail.setVisitDateTime(visitDateTime);
        beneficiaryVisitDetail.setVisitFlowStatusFlag("Visit Flow Status Flag");
        beneficiaryVisitDetail.setVisitNo((short) 1);
        beneficiaryVisitDetail.setVisitReason("Just cause");
        beneficiaryVisitDetail.setVisitReasonID((short) 1);
        beneficiaryVisitDetail.setrCHID("R CHID");
        Timestamp createdDate2 = mock(Timestamp.class);
        when(createdDate2.getTime()).thenReturn(10L);
        Timestamp lastModDate2 = mock(Timestamp.class);
        when(lastModDate2.getTime()).thenReturn(10L);
        Timestamp syncedDate2 = mock(Timestamp.class);
        when(syncedDate2.getTime()).thenReturn(10L);

        CDSS cdss = new CDSS();
        cdss.setAction("Action");
        cdss.setActionId(1);
        cdss.setActionIdPc(1);
        cdss.setActionPc("Action Pc");
        cdss.setAlgorithm("Algorithm");
        cdss.setAlgorithmPc("Algorithm Pc");
        cdss.setBenVisitID(1L);
        cdss.setBeneficiaryRegID(1L);
        cdss.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cdss.setCreatedDate(createdDate2);
        cdss.setDeleted(true);
        cdss.setDiseaseSummary("Disease Summary");
        cdss.setDiseaseSummaryID(1);
        cdss.setId(1L);
        cdss.setInformationGiven("Information Given");
        cdss.setLastModDate(lastModDate2);
        cdss.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cdss.setParkingPlaceID(1);
        cdss.setPresentChiefComplaint("Present Chief Complaint");
        cdss.setPresentChiefComplaintID("Present Chief Complaint ID");
        cdss.setProviderServiceMapID(1);
        cdss.setRecommendedAction("Recommended Action");
        cdss.setRecommendedActionPc("Recommended Action Pc");
        cdss.setRemarks("Remarks");
        cdss.setRemarksPc("Remarks Pc");
        cdss.setReservedForChange("Reserved For Change");
        cdss.setSelectedDiagnosis("Selected Diagnosis");
        cdss.setSelectedDiagnosisID("Selected Diagnosis ID");
        cdss.setSyncedBy("Synced By");
        cdss.setSyncedDate(syncedDate2);
        cdss.setVanID(1);
        cdss.setVanSerialNo(1L);
        cdss.setVehicalNo("Vehical No");
        cdss.setVisitCode(1L);
        when(commonNurseServiceImpl.getCSVisitDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(beneficiaryVisitDetail);
        when(commonNurseServiceImpl.getCdssDetails(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(cdss);

        // Act
        quickConsultationServiceImpl.getBenDataFrmNurseToDocVisitDetailsScreen(1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getCdssDetails(isA(Long.class), isA(Long.class));
        verify(createdDate).getTime();
        verify(lastModDate).getTime();
        verify(syncedDate).getTime();
        verify(visitDateTime).getTime();
        verify(createdDate2).getTime();
        verify(lastModDate2).getTime();
        verify(syncedDate2).getTime();
    }

    @Test
    void testGetBenDataFrmNurseToDocVisitDetailsScreen2() {
        // Arrange
        Timestamp createdDate = mock(Timestamp.class);
        when(createdDate.getTime()).thenReturn(10L);
        Timestamp lastModDate = mock(Timestamp.class);
        when(lastModDate.getTime()).thenReturn(10L);

        States states = new States();
        states.setCountryID(1);
        states.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        states.setCreatedDate(mock(Timestamp.class));
        states.setDeleted(true);
        states.setLastModDate(mock(Timestamp.class));
        states.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        states.setStateCode("MD");
        states.setStateID(1);
        states.setStateIName("MD");
        states.setStateName("MD");

        ZoneDistrictMapping zoneDistrictMapping = new ZoneDistrictMapping();
        zoneDistrictMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        zoneDistrictMapping.setCreatedDate(mock(Timestamp.class));
        zoneDistrictMapping.setDeleted(true);
        zoneDistrictMapping.setDistrictID(1);
        zoneDistrictMapping.setDistrictsSet(new HashSet<>());
        zoneDistrictMapping.setLastModDate(mock(Timestamp.class));
        zoneDistrictMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        zoneDistrictMapping.setProcessed("Processed");
        zoneDistrictMapping.setProviderServiceMapID(1);
        zoneDistrictMapping.setZoneDistrictMapID(1);
        zoneDistrictMapping.setZoneID(1);

        Districts m_district = new Districts();
        m_district.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        m_district.setCreatedDate(mock(Timestamp.class));
        m_district.setDeleted(true);
        m_district.setDistrictID(1);
        m_district.setDistrictName("District Name");
        m_district.setGovtLGDDistrictID(1);
        m_district.setGovtLGDStateID(1);
        m_district.setLastModDate(mock(Timestamp.class));
        m_district.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        m_district.setStateID(1);
        m_district.setStates(states);
        m_district.setZoneDistrictMapping(zoneDistrictMapping);

        States state = new States();
        state.setCountryID(1);
        state.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        state.setCreatedDate(mock(Timestamp.class));
        state.setDeleted(true);
        state.setLastModDate(mock(Timestamp.class));
        state.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        state.setStateCode("MD");
        state.setStateID(1);
        state.setStateIName("MD");
        state.setStateName("MD");

        ProviderServiceMapping providerServiceMapping = new ProviderServiceMapping();
        providerServiceMapping.setAddress("42 Main St");
        providerServiceMapping.setCityID(1);
        providerServiceMapping.setCountryID(1);
        providerServiceMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        providerServiceMapping.setCreatedDate(mock(Timestamp.class));
        providerServiceMapping.setDeleted(true);
        providerServiceMapping.setDistrictBlockID(1);
        providerServiceMapping.setDistrictID(1);
        providerServiceMapping.setLastModDate(mock(Timestamp.class));
        providerServiceMapping.setM_district(m_district);
        providerServiceMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        providerServiceMapping.setProviderServiceMapID(1);
        providerServiceMapping.setServiceID((short) 1);
        providerServiceMapping.setServiceProviderID(1);
        providerServiceMapping.setState(state);
        providerServiceMapping.setStateID(1);
        providerServiceMapping.setStatusID(1);
        Timestamp syncedDate = mock(Timestamp.class);
        when(syncedDate.getTime()).thenReturn(10L);
        Timestamp visitDateTime = mock(Timestamp.class);
        when(visitDateTime.getTime()).thenReturn(10L);

        BeneficiaryVisitDetail beneficiaryVisitDetail = new BeneficiaryVisitDetail();
        beneficiaryVisitDetail.setAction("Action");
        beneficiaryVisitDetail.setActionId(1);
        beneficiaryVisitDetail.setActionIdPc(1);
        beneficiaryVisitDetail.setActionPc("Action Pc");
        beneficiaryVisitDetail.setAlgorithm("Algorithm");
        beneficiaryVisitDetail.setAlgorithmPc("Algorithm Pc");
        beneficiaryVisitDetail.setBenVisitID(1L);
        beneficiaryVisitDetail.setBeneficiaryRegID(1L);
        beneficiaryVisitDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryVisitDetail.setCreatedDate(createdDate);
        beneficiaryVisitDetail.setDeleted(true);
        beneficiaryVisitDetail.setDiseaseSummary("Disease Summary");
        beneficiaryVisitDetail.setDiseaseSummaryID(1);
        beneficiaryVisitDetail.setFileIDs(new Integer[]{1});
        beneficiaryVisitDetail.setFollowUpForFpMethod(new String[]{"Follow Up For Fp Method"});
        beneficiaryVisitDetail.setFpMethodFollowup("Fp Method Followup");
        beneficiaryVisitDetail.setFpSideeffects("Fp Sideeffects");
        beneficiaryVisitDetail.setHealthFacilityLocation("Health Facility Location");
        beneficiaryVisitDetail.setHealthFacilityType("Health Facility Type");
        beneficiaryVisitDetail.setInformationGiven("Information Given");
        beneficiaryVisitDetail.setLastModDate(lastModDate);
        beneficiaryVisitDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryVisitDetail.setOtherFollowUpForFpMethod("Other Follow Up For Fp Method");
        beneficiaryVisitDetail.setOtherSideEffects("Other Side Effects");
        beneficiaryVisitDetail.setParkingPlaceID(1);
        beneficiaryVisitDetail.setPregnancyStatus("Pregnancy Status");
        beneficiaryVisitDetail.setPresentChiefComplaint("Present Chief Complaint");
        beneficiaryVisitDetail.setPresentChiefComplaintID("Present Chief Complaint ID");
        beneficiaryVisitDetail.setProcessed("Processed");
        beneficiaryVisitDetail.setProviderServiceMapID(1);
        beneficiaryVisitDetail.setProviderServiceMapping(providerServiceMapping);
        beneficiaryVisitDetail.setRecommendedAction("Recommended Action");
        beneficiaryVisitDetail.setRecommendedActionPc("Recommended Action Pc");
        beneficiaryVisitDetail.setRemarks("Remarks");
        beneficiaryVisitDetail.setRemarksPc("Remarks Pc");
        beneficiaryVisitDetail.setReportFilePath("/directory/foo.txt");
        beneficiaryVisitDetail.setReservedForChange("Reserved For Change");
        beneficiaryVisitDetail.setSelectedDiagnosis("Selected Diagnosis");
        beneficiaryVisitDetail.setSelectedDiagnosisID("Selected Diagnosis ID");
        beneficiaryVisitDetail.setServiceProviderName("Service Provider Name");
        beneficiaryVisitDetail.setSideEffects(new String[]{"Side Effects"});
        beneficiaryVisitDetail.setSubVisitCategory("Sub Visit Category");
        beneficiaryVisitDetail.setSyncedBy("Synced By");
        beneficiaryVisitDetail.setSyncedDate(syncedDate);
        beneficiaryVisitDetail.setVanID(1);
        beneficiaryVisitDetail.setVanSerialNo(1L);
        beneficiaryVisitDetail.setVehicalNo("Vehical No");
        beneficiaryVisitDetail.setVisitCategory("Visit Category");
        beneficiaryVisitDetail.setVisitCategoryID(1);
        beneficiaryVisitDetail.setVisitCode(1L);
        beneficiaryVisitDetail.setVisitDateTime(visitDateTime);
        beneficiaryVisitDetail.setVisitFlowStatusFlag("Visit Flow Status Flag");
        beneficiaryVisitDetail.setVisitNo((short) 1);
        beneficiaryVisitDetail.setVisitReason("Just cause");
        beneficiaryVisitDetail.setVisitReasonID((short) 1);
        beneficiaryVisitDetail.setrCHID("R CHID");
        Timestamp createdDate2 = mock(Timestamp.class);
        when(createdDate2.getTime()).thenReturn(10L);
        Timestamp lastModDate2 = mock(Timestamp.class);
        when(lastModDate2.getTime()).thenReturn(10L);
        Timestamp syncedDate2 = mock(Timestamp.class);
        when(syncedDate2.getTime()).thenThrow(new RuntimeException("benVisitDetails"));

        CDSS cdss = new CDSS();
        cdss.setAction("Action");
        cdss.setActionId(1);
        cdss.setActionIdPc(1);
        cdss.setActionPc("Action Pc");
        cdss.setAlgorithm("Algorithm");
        cdss.setAlgorithmPc("Algorithm Pc");
        cdss.setBenVisitID(1L);
        cdss.setBeneficiaryRegID(1L);
        cdss.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cdss.setCreatedDate(createdDate2);
        cdss.setDeleted(true);
        cdss.setDiseaseSummary("Disease Summary");
        cdss.setDiseaseSummaryID(1);
        cdss.setId(1L);
        cdss.setInformationGiven("Information Given");
        cdss.setLastModDate(lastModDate2);
        cdss.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cdss.setParkingPlaceID(1);
        cdss.setPresentChiefComplaint("Present Chief Complaint");
        cdss.setPresentChiefComplaintID("Present Chief Complaint ID");
        cdss.setProviderServiceMapID(1);
        cdss.setRecommendedAction("Recommended Action");
        cdss.setRecommendedActionPc("Recommended Action Pc");
        cdss.setRemarks("Remarks");
        cdss.setRemarksPc("Remarks Pc");
        cdss.setReservedForChange("Reserved For Change");
        cdss.setSelectedDiagnosis("Selected Diagnosis");
        cdss.setSelectedDiagnosisID("Selected Diagnosis ID");
        cdss.setSyncedBy("Synced By");
        cdss.setSyncedDate(syncedDate2);
        cdss.setVanID(1);
        cdss.setVanSerialNo(1L);
        cdss.setVehicalNo("Vehical No");
        cdss.setVisitCode(1L);
        when(commonNurseServiceImpl.getCSVisitDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(beneficiaryVisitDetail);
        when(commonNurseServiceImpl.getCdssDetails(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(cdss);

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> quickConsultationServiceImpl.getBenDataFrmNurseToDocVisitDetailsScreen(1L, 1L));
        verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getCdssDetails(isA(Long.class), isA(Long.class));
        verify(createdDate).getTime();
        verify(lastModDate).getTime();
        verify(syncedDate).getTime();
        verify(visitDateTime).getTime();
        verify(createdDate2).getTime();
        verify(lastModDate2).getTime();
        verify(syncedDate2).getTime();
    }

    @Test
    void testGetBenDataFrmNurseToDocVisitDetailsScreen3() {
        // Arrange
        Timestamp createdDate = mock(Timestamp.class);
        when(createdDate.getTime()).thenReturn(10L);
        Timestamp lastModDate = mock(Timestamp.class);
        when(lastModDate.getTime()).thenReturn(10L);

        States states = new States();
        states.setCountryID(1);
        states.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        states.setCreatedDate(mock(Timestamp.class));
        states.setDeleted(true);
        states.setLastModDate(mock(Timestamp.class));
        states.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        states.setStateCode("MD");
        states.setStateID(1);
        states.setStateIName("MD");
        states.setStateName("MD");

        ZoneDistrictMapping zoneDistrictMapping = new ZoneDistrictMapping();
        zoneDistrictMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        zoneDistrictMapping.setCreatedDate(mock(Timestamp.class));
        zoneDistrictMapping.setDeleted(true);
        zoneDistrictMapping.setDistrictID(1);
        zoneDistrictMapping.setDistrictsSet(new HashSet<>());
        zoneDistrictMapping.setLastModDate(mock(Timestamp.class));
        zoneDistrictMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        zoneDistrictMapping.setProcessed("Processed");
        zoneDistrictMapping.setProviderServiceMapID(1);
        zoneDistrictMapping.setZoneDistrictMapID(1);
        zoneDistrictMapping.setZoneID(1);

        Districts m_district = new Districts();
        m_district.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        m_district.setCreatedDate(mock(Timestamp.class));
        m_district.setDeleted(true);
        m_district.setDistrictID(1);
        m_district.setDistrictName("District Name");
        m_district.setGovtLGDDistrictID(1);
        m_district.setGovtLGDStateID(1);
        m_district.setLastModDate(mock(Timestamp.class));
        m_district.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        m_district.setStateID(1);
        m_district.setStates(states);
        m_district.setZoneDistrictMapping(zoneDistrictMapping);

        States state = new States();
        state.setCountryID(1);
        state.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        state.setCreatedDate(mock(Timestamp.class));
        state.setDeleted(true);
        state.setLastModDate(mock(Timestamp.class));
        state.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        state.setStateCode("MD");
        state.setStateID(1);
        state.setStateIName("MD");
        state.setStateName("MD");

        ProviderServiceMapping providerServiceMapping = new ProviderServiceMapping();
        providerServiceMapping.setAddress("42 Main St");
        providerServiceMapping.setCityID(1);
        providerServiceMapping.setCountryID(1);
        providerServiceMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        providerServiceMapping.setCreatedDate(mock(Timestamp.class));
        providerServiceMapping.setDeleted(true);
        providerServiceMapping.setDistrictBlockID(1);
        providerServiceMapping.setDistrictID(1);
        providerServiceMapping.setLastModDate(mock(Timestamp.class));
        providerServiceMapping.setM_district(m_district);
        providerServiceMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        providerServiceMapping.setProviderServiceMapID(1);
        providerServiceMapping.setServiceID((short) 1);
        providerServiceMapping.setServiceProviderID(1);
        providerServiceMapping.setState(state);
        providerServiceMapping.setStateID(1);
        providerServiceMapping.setStatusID(1);
        Timestamp syncedDate = mock(Timestamp.class);
        when(syncedDate.getTime()).thenReturn(10L);
        Timestamp visitDateTime = mock(Timestamp.class);
        when(visitDateTime.getTime()).thenReturn(10L);
        BeneficiaryVisitDetail beneficiaryVisitDetail = mock(BeneficiaryVisitDetail.class);
        doNothing().when(beneficiaryVisitDetail).setAction(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setActionId(Mockito.<Integer>any());
        doNothing().when(beneficiaryVisitDetail).setActionIdPc(Mockito.<Integer>any());
        doNothing().when(beneficiaryVisitDetail).setActionPc(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setAlgorithm(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setAlgorithmPc(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setBenVisitID(Mockito.<Long>any());
        doNothing().when(beneficiaryVisitDetail).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(beneficiaryVisitDetail).setCreatedBy(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryVisitDetail).setDeleted(Mockito.<Boolean>any());
        doNothing().when(beneficiaryVisitDetail).setDiseaseSummary(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setDiseaseSummaryID(Mockito.<Integer>any());
        doNothing().when(beneficiaryVisitDetail).setFileIDs(Mockito.<Integer[]>any());
        doNothing().when(beneficiaryVisitDetail).setFollowUpForFpMethod(Mockito.<String[]>any());
        doNothing().when(beneficiaryVisitDetail).setFpMethodFollowup(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setFpSideeffects(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setHealthFacilityLocation(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setHealthFacilityType(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setInformationGiven(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryVisitDetail).setModifiedBy(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setOtherFollowUpForFpMethod(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setOtherSideEffects(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(beneficiaryVisitDetail).setPregnancyStatus(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setPresentChiefComplaint(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setPresentChiefComplaintID(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setProcessed(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(beneficiaryVisitDetail).setProviderServiceMapping(Mockito.<ProviderServiceMapping>any());
        doNothing().when(beneficiaryVisitDetail).setRecommendedAction(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setRecommendedActionPc(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setRemarks(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setRemarksPc(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setReportFilePath(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setReservedForChange(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setSelectedDiagnosis(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setSelectedDiagnosisID(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setServiceProviderName(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setSideEffects(Mockito.<String[]>any());
        doNothing().when(beneficiaryVisitDetail).setSubVisitCategory(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setSyncedBy(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryVisitDetail).setVanID(Mockito.<Integer>any());
        doNothing().when(beneficiaryVisitDetail).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(beneficiaryVisitDetail).setVehicalNo(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setVisitCategory(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setVisitCategoryID(Mockito.<Integer>any());
        doNothing().when(beneficiaryVisitDetail).setVisitCode(Mockito.<Long>any());
        doNothing().when(beneficiaryVisitDetail).setVisitDateTime(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryVisitDetail).setVisitFlowStatusFlag(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setVisitNo(Mockito.<Short>any());
        doNothing().when(beneficiaryVisitDetail).setVisitReason(Mockito.<String>any());
        doNothing().when(beneficiaryVisitDetail).setVisitReasonID(Mockito.<Short>any());
        doNothing().when(beneficiaryVisitDetail).setrCHID(Mockito.<String>any());
        beneficiaryVisitDetail.setAction("Action");
        beneficiaryVisitDetail.setActionId(1);
        beneficiaryVisitDetail.setActionIdPc(1);
        beneficiaryVisitDetail.setActionPc("Action Pc");
        beneficiaryVisitDetail.setAlgorithm("Algorithm");
        beneficiaryVisitDetail.setAlgorithmPc("Algorithm Pc");
        beneficiaryVisitDetail.setBenVisitID(1L);
        beneficiaryVisitDetail.setBeneficiaryRegID(1L);
        beneficiaryVisitDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryVisitDetail.setCreatedDate(createdDate);
        beneficiaryVisitDetail.setDeleted(true);
        beneficiaryVisitDetail.setDiseaseSummary("Disease Summary");
        beneficiaryVisitDetail.setDiseaseSummaryID(1);
        beneficiaryVisitDetail.setFileIDs(new Integer[]{1});
        beneficiaryVisitDetail.setFollowUpForFpMethod(new String[]{"Follow Up For Fp Method"});
        beneficiaryVisitDetail.setFpMethodFollowup("Fp Method Followup");
        beneficiaryVisitDetail.setFpSideeffects("Fp Sideeffects");
        beneficiaryVisitDetail.setHealthFacilityLocation("Health Facility Location");
        beneficiaryVisitDetail.setHealthFacilityType("Health Facility Type");
        beneficiaryVisitDetail.setInformationGiven("Information Given");
        beneficiaryVisitDetail.setLastModDate(lastModDate);
        beneficiaryVisitDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryVisitDetail.setOtherFollowUpForFpMethod("Other Follow Up For Fp Method");
        beneficiaryVisitDetail.setOtherSideEffects("Other Side Effects");
        beneficiaryVisitDetail.setParkingPlaceID(1);
        beneficiaryVisitDetail.setPregnancyStatus("Pregnancy Status");
        beneficiaryVisitDetail.setPresentChiefComplaint("Present Chief Complaint");
        beneficiaryVisitDetail.setPresentChiefComplaintID("Present Chief Complaint ID");
        beneficiaryVisitDetail.setProcessed("Processed");
        beneficiaryVisitDetail.setProviderServiceMapID(1);
        beneficiaryVisitDetail.setProviderServiceMapping(providerServiceMapping);
        beneficiaryVisitDetail.setRecommendedAction("Recommended Action");
        beneficiaryVisitDetail.setRecommendedActionPc("Recommended Action Pc");
        beneficiaryVisitDetail.setRemarks("Remarks");
        beneficiaryVisitDetail.setRemarksPc("Remarks Pc");
        beneficiaryVisitDetail.setReportFilePath("/directory/foo.txt");
        beneficiaryVisitDetail.setReservedForChange("Reserved For Change");
        beneficiaryVisitDetail.setSelectedDiagnosis("Selected Diagnosis");
        beneficiaryVisitDetail.setSelectedDiagnosisID("Selected Diagnosis ID");
        beneficiaryVisitDetail.setServiceProviderName("Service Provider Name");
        beneficiaryVisitDetail.setSideEffects(new String[]{"Side Effects"});
        beneficiaryVisitDetail.setSubVisitCategory("Sub Visit Category");
        beneficiaryVisitDetail.setSyncedBy("Synced By");
        beneficiaryVisitDetail.setSyncedDate(syncedDate);
        beneficiaryVisitDetail.setVanID(1);
        beneficiaryVisitDetail.setVanSerialNo(1L);
        beneficiaryVisitDetail.setVehicalNo("Vehical No");
        beneficiaryVisitDetail.setVisitCategory("Visit Category");
        beneficiaryVisitDetail.setVisitCategoryID(1);
        beneficiaryVisitDetail.setVisitCode(1L);
        beneficiaryVisitDetail.setVisitDateTime(visitDateTime);
        beneficiaryVisitDetail.setVisitFlowStatusFlag("Visit Flow Status Flag");
        beneficiaryVisitDetail.setVisitNo((short) 1);
        beneficiaryVisitDetail.setVisitReason("Just cause");
        beneficiaryVisitDetail.setVisitReasonID((short) 1);
        beneficiaryVisitDetail.setrCHID("R CHID");
        Timestamp createdDate2 = mock(Timestamp.class);
        when(createdDate2.getTime()).thenReturn(10L);
        Timestamp lastModDate2 = mock(Timestamp.class);
        when(lastModDate2.getTime()).thenReturn(10L);
        Timestamp syncedDate2 = mock(Timestamp.class);
        when(syncedDate2.getTime()).thenReturn(10L);

        CDSS cdss = new CDSS();
        cdss.setAction("Action");
        cdss.setActionId(1);
        cdss.setActionIdPc(1);
        cdss.setActionPc("Action Pc");
        cdss.setAlgorithm("Algorithm");
        cdss.setAlgorithmPc("Algorithm Pc");
        cdss.setBenVisitID(1L);
        cdss.setBeneficiaryRegID(1L);
        cdss.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        cdss.setCreatedDate(createdDate2);
        cdss.setDeleted(true);
        cdss.setDiseaseSummary("Disease Summary");
        cdss.setDiseaseSummaryID(1);
        cdss.setId(1L);
        cdss.setInformationGiven("Information Given");
        cdss.setLastModDate(lastModDate2);
        cdss.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        cdss.setParkingPlaceID(1);
        cdss.setPresentChiefComplaint("Present Chief Complaint");
        cdss.setPresentChiefComplaintID("Present Chief Complaint ID");
        cdss.setProviderServiceMapID(1);
        cdss.setRecommendedAction("Recommended Action");
        cdss.setRecommendedActionPc("Recommended Action Pc");
        cdss.setRemarks("Remarks");
        cdss.setRemarksPc("Remarks Pc");
        cdss.setReservedForChange("Reserved For Change");
        cdss.setSelectedDiagnosis("Selected Diagnosis");
        cdss.setSelectedDiagnosisID("Selected Diagnosis ID");
        cdss.setSyncedBy("Synced By");
        cdss.setSyncedDate(syncedDate2);
        cdss.setVanID(1);
        cdss.setVanSerialNo(1L);
        cdss.setVehicalNo("Vehical No");
        cdss.setVisitCode(1L);
        when(commonNurseServiceImpl.getCSVisitDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(beneficiaryVisitDetail);
        when(commonNurseServiceImpl.getCdssDetails(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(cdss);

        // Act
        quickConsultationServiceImpl.getBenDataFrmNurseToDocVisitDetailsScreen(1L, 1L);

        // Assert
        verify(beneficiaryVisitDetail).setAction(eq("Action"));
        verify(beneficiaryVisitDetail).setActionId(isA(Integer.class));
        verify(beneficiaryVisitDetail).setActionIdPc(isA(Integer.class));
        verify(beneficiaryVisitDetail).setActionPc(eq("Action Pc"));
        verify(beneficiaryVisitDetail).setAlgorithm(eq("Algorithm"));
        verify(beneficiaryVisitDetail).setAlgorithmPc(eq("Algorithm Pc"));
        verify(beneficiaryVisitDetail).setBenVisitID(isA(Long.class));
        verify(beneficiaryVisitDetail).setBeneficiaryRegID(isA(Long.class));
        verify(beneficiaryVisitDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(beneficiaryVisitDetail).setCreatedDate(isA(Timestamp.class));
        verify(beneficiaryVisitDetail).setDeleted(isA(Boolean.class));
        verify(beneficiaryVisitDetail).setDiseaseSummary(eq("Disease Summary"));
        verify(beneficiaryVisitDetail).setDiseaseSummaryID(isA(Integer.class));
        verify(beneficiaryVisitDetail).setFileIDs(isA(Integer[].class));
        verify(beneficiaryVisitDetail).setFollowUpForFpMethod(isA(String[].class));
        verify(beneficiaryVisitDetail).setFpMethodFollowup(eq("Fp Method Followup"));
        verify(beneficiaryVisitDetail).setFpSideeffects(eq("Fp Sideeffects"));
        verify(beneficiaryVisitDetail).setHealthFacilityLocation(eq("Health Facility Location"));
        verify(beneficiaryVisitDetail).setHealthFacilityType(eq("Health Facility Type"));
        verify(beneficiaryVisitDetail).setInformationGiven(eq("Information Given"));
        verify(beneficiaryVisitDetail).setLastModDate(isA(Timestamp.class));
        verify(beneficiaryVisitDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(beneficiaryVisitDetail).setOtherFollowUpForFpMethod(eq("Other Follow Up For Fp Method"));
        verify(beneficiaryVisitDetail).setOtherSideEffects(eq("Other Side Effects"));
        verify(beneficiaryVisitDetail).setParkingPlaceID(isA(Integer.class));
        verify(beneficiaryVisitDetail).setPregnancyStatus(eq("Pregnancy Status"));
        verify(beneficiaryVisitDetail).setPresentChiefComplaint(eq("Present Chief Complaint"));
        verify(beneficiaryVisitDetail).setPresentChiefComplaintID(eq("Present Chief Complaint ID"));
        verify(beneficiaryVisitDetail).setProcessed(eq("Processed"));
        verify(beneficiaryVisitDetail).setProviderServiceMapID(isA(Integer.class));
        verify(beneficiaryVisitDetail).setProviderServiceMapping(isA(ProviderServiceMapping.class));
        verify(beneficiaryVisitDetail).setRecommendedAction(eq("Recommended Action"));
        verify(beneficiaryVisitDetail).setRecommendedActionPc(eq("Recommended Action Pc"));
        verify(beneficiaryVisitDetail).setRemarks(eq("Remarks"));
        verify(beneficiaryVisitDetail).setRemarksPc(eq("Remarks Pc"));
        verify(beneficiaryVisitDetail).setReportFilePath(eq("/directory/foo.txt"));
        verify(beneficiaryVisitDetail).setReservedForChange(eq("Reserved For Change"));
        verify(beneficiaryVisitDetail).setSelectedDiagnosis(eq("Selected Diagnosis"));
        verify(beneficiaryVisitDetail).setSelectedDiagnosisID(eq("Selected Diagnosis ID"));
        verify(beneficiaryVisitDetail).setServiceProviderName(eq("Service Provider Name"));
        verify(beneficiaryVisitDetail).setSideEffects(isA(String[].class));
        verify(beneficiaryVisitDetail).setSubVisitCategory(eq("Sub Visit Category"));
        verify(beneficiaryVisitDetail).setSyncedBy(eq("Synced By"));
        verify(beneficiaryVisitDetail).setSyncedDate(isA(Timestamp.class));
        verify(beneficiaryVisitDetail).setVanID(isA(Integer.class));
        verify(beneficiaryVisitDetail).setVanSerialNo(isA(Long.class));
        verify(beneficiaryVisitDetail).setVehicalNo(eq("Vehical No"));
        verify(beneficiaryVisitDetail).setVisitCategory(eq("Visit Category"));
        verify(beneficiaryVisitDetail).setVisitCategoryID(isA(Integer.class));
        verify(beneficiaryVisitDetail).setVisitCode(isA(Long.class));
        verify(beneficiaryVisitDetail).setVisitDateTime(isA(Timestamp.class));
        verify(beneficiaryVisitDetail).setVisitFlowStatusFlag(eq("Visit Flow Status Flag"));
        verify(beneficiaryVisitDetail).setVisitNo(isA(Short.class));
        verify(beneficiaryVisitDetail).setVisitReason(eq("Just cause"));
        verify(beneficiaryVisitDetail).setVisitReasonID(isA(Short.class));
        verify(beneficiaryVisitDetail).setrCHID(eq("R CHID"));
        verify(commonNurseServiceImpl).getCSVisitDetails(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getCdssDetails(isA(Long.class), isA(Long.class));
        verify(createdDate2).getTime();
        verify(lastModDate2).getTime();
        verify(syncedDate2).getTime();
    }

    @Test
    void testGetBeneficiaryVitalDetails() {
        // Arrange
        when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Beneficiary Physical Anthropometry Details");
        when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Beneficiary Physical Vital Details");

        // Act
        String actualBeneficiaryVitalDetails = quickConsultationServiceImpl.getBeneficiaryVitalDetails(1L, 1L);

        // Assert
        verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getBeneficiaryPhysicalVitalDetails(isA(Long.class), isA(Long.class));
        assertEquals(
                "{benAnthropometryDetail=Beneficiary Physical Anthropometry Details, benPhysicalVitalDetail=Beneficiary"
                        + " Physical Vital Details}",
                actualBeneficiaryVitalDetails);
    }

    @Test
    void testGetBeneficiaryVitalDetails2() {
        // Arrange
        when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenThrow(new RuntimeException("benAnthropometryDetail"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> quickConsultationServiceImpl.getBeneficiaryVitalDetails(1L, 1L));
        verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetBeneficiaryCdssDetails() {
        // Arrange
        when(commonNurseServiceImpl.getBenCdssDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Cdss Details");

        // Act
        String actualBeneficiaryCdssDetails = quickConsultationServiceImpl.getBeneficiaryCdssDetails(1L, 1L);

        // Assert
        verify(commonNurseServiceImpl, atLeast(1)).getBenCdssDetails(isA(Long.class), isA(Long.class));
        assertEquals("{diseaseSummary=Ben Cdss Details, presentChiefComplaint=Ben Cdss Details}",
                actualBeneficiaryCdssDetails);
    }

    @Test
    void testGetBeneficiaryCdssDetails2() {
        // Arrange
        when(commonNurseServiceImpl.getBenCdssDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenThrow(new RuntimeException("presentChiefComplaint"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> quickConsultationServiceImpl.getBeneficiaryCdssDetails(1L, 1L));
        verify(commonNurseServiceImpl).getBenCdssDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetBenQuickConsultNurseData() {
        // Arrange
        when(commonNurseServiceImpl.getBenCdssDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Cdss Details");
        when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Beneficiary Physical Anthropometry Details");
        when(commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Beneficiary Physical Vital Details");

        // Act
        String actualBenQuickConsultNurseData = quickConsultationServiceImpl.getBenQuickConsultNurseData(1L, 1L);

        // Assert
        verify(commonNurseServiceImpl, atLeast(1)).getBenCdssDetails(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
        verify(commonNurseServiceImpl).getBeneficiaryPhysicalVitalDetails(isA(Long.class), isA(Long.class));
        assertEquals("{vitals={benAnthropometryDetail=Beneficiary Physical Anthropometry Details, benPhysicalVitalDetail"
                + "=Beneficiary Physical Vital Details}, cdss={diseaseSummary=Ben Cdss Details, presentChiefComplaint=Ben"
                + " Cdss Details}}", actualBenQuickConsultNurseData);
    }

    @Test
    void testGetBenQuickConsultNurseData2() {
        // Arrange
        when(commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenThrow(new RuntimeException("vitals"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> quickConsultationServiceImpl.getBenQuickConsultNurseData(1L, 1L));
        verify(commonNurseServiceImpl).getBeneficiaryPhysicalAnthropometryDetails(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetBenCaseRecordFromDoctorQuickConsult() {
        // Arrange
        when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Findings Details");
        when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Investigation Details");
        when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Prescribed Drugs");
        when(generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("General OPDDiagnosis Details");
        when(labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Last 3 Archived Test Visit List");
        when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualBenCaseRecordFromDoctorQuickConsult = quickConsultationServiceImpl
                .getBenCaseRecordFromDoctorQuickConsult(1L, 1L);

        // Assert
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
        verify(generalOPDDoctorServiceImpl).getGeneralOPDDiagnosisDetails(isA(Long.class), isA(Long.class));
        verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
        verify(labTechnicianServiceImpl).getLast_3_ArchivedTestVisitList(isA(Long.class), isA(Long.class));
        assertEquals(
                "{prescription=Prescribed Drugs, findings=Findings Details, LabReport=[], diagnosis=General OPDDiagnosis"
                        + " Details, investigation=Investigation Details, ArchivedVisitcodeForLabResult=Last 3 Archived Test Visit"
                        + " List}",
                actualBenCaseRecordFromDoctorQuickConsult);
    }

    @Test
    void testGetBenCaseRecordFromDoctorQuickConsult2() {
        // Arrange
        when(commonDoctorServiceImpl.getFindingsDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Findings Details");
        when(commonDoctorServiceImpl.getInvestigationDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Investigation Details");
        when(commonDoctorServiceImpl.getPrescribedDrugs(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Prescribed Drugs");
        when(generalOPDDoctorServiceImpl.getGeneralOPDDiagnosisDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("General OPDDiagnosis Details");
        when(labTechnicianServiceImpl.getLabResultDataForBen(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenThrow(new RuntimeException("findings"));

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> quickConsultationServiceImpl.getBenCaseRecordFromDoctorQuickConsult(1L, 1L));
        verify(commonDoctorServiceImpl).getFindingsDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getInvestigationDetails(isA(Long.class), isA(Long.class));
        verify(commonDoctorServiceImpl).getPrescribedDrugs(isA(Long.class), isA(Long.class));
        verify(generalOPDDoctorServiceImpl).getGeneralOPDDiagnosisDetails(isA(Long.class), isA(Long.class));
        verify(labTechnicianServiceImpl).getLabResultDataForBen(isA(Long.class), isA(Long.class));
    }

    @Test
    void testUpdateGeneralOPDQCDoctorData() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);

        TeleconsultationRequestOBJ teleconsultationRequestOBJ = new TeleconsultationRequestOBJ();
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

        // Act
        Long actualUpdateGeneralOPDQCDoctorDataResult = quickConsultationServiceImpl
                .updateGeneralOPDQCDoctorData(new JsonObject(), "JaneDoe");

        // Assert
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).updatePrescription(isA(PrescriptionDetail.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        assertEquals(1L, actualUpdateGeneralOPDQCDoctorDataResult.longValue());
    }

    @Test
    void testUpdateGeneralOPDQCDoctorData2() throws Exception {
        // Arrange
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenThrow(new Exception("chiefComplaintList"));

        // Act and Assert
        assertThrows(Exception.class,
                () -> quickConsultationServiceImpl.updateGeneralOPDQCDoctorData(new JsonObject(), "JaneDoe"));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
    }

    @Test
    void testUpdateGeneralOPDQCDoctorData3() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(0);
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);

        TeleconsultationRequestOBJ teleconsultationRequestOBJ = new TeleconsultationRequestOBJ();
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> quickConsultationServiceImpl.updateGeneralOPDQCDoctorData(new JsonObject(), "JaneDoe"));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).updatePrescription(isA(PrescriptionDetail.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
    }

    @Test
    void testUpdateGeneralOPDQCDoctorData4() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(0);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);

        TeleconsultationRequestOBJ teleconsultationRequestOBJ = new TeleconsultationRequestOBJ();
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> quickConsultationServiceImpl.updateGeneralOPDQCDoctorData(new JsonObject(), "JaneDoe"));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        verify(commonNurseServiceImpl).updatePrescription(isA(PrescriptionDetail.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
    }

    @Test
    void testUpdateGeneralOPDQCDoctorData5() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(null);
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);

        TeleconsultationRequestOBJ teleconsultationRequestOBJ = new TeleconsultationRequestOBJ();
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

        // Act
        Long actualUpdateGeneralOPDQCDoctorDataResult = quickConsultationServiceImpl
                .updateGeneralOPDQCDoctorData(new JsonObject(), "JaneDoe");

        // Assert
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).updatePrescription(isA(PrescriptionDetail.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        assertEquals(1L, actualUpdateGeneralOPDQCDoctorDataResult.longValue());
    }

    @Test
    void testUpdateGeneralOPDQCDoctorData6() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any()))
                .thenReturn(new String[]{"chiefComplaintList", "labTestOrders"});
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);

        TeleconsultationRequestOBJ teleconsultationRequestOBJ = new TeleconsultationRequestOBJ();
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);

        // Act
        Long actualUpdateGeneralOPDQCDoctorDataResult = quickConsultationServiceImpl
                .updateGeneralOPDQCDoctorData(new JsonObject(), "JaneDoe");

        // Assert
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).updatePrescription(isA(PrescriptionDetail.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        assertEquals(1L, actualUpdateGeneralOPDQCDoctorDataResult.longValue());
    }

    @Test
    void testUpdateGeneralOPDQCDoctorData7() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(false);
        when(teleconsultationRequestOBJ.getSpecializationID()).thenReturn(1);
        when(teleconsultationRequestOBJ.getTmRequestID()).thenReturn(1L);
        when(teleconsultationRequestOBJ.getAllocationDate()).thenReturn(mock(Timestamp.class));
        doNothing().when(teleconsultationRequestOBJ).setAllocationDate(Mockito.<Timestamp>any());
        doNothing().when(teleconsultationRequestOBJ).setFromTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setSpecializationID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setTmRequestID(Mockito.<Long>any());
        doNothing().when(teleconsultationRequestOBJ).setToTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setUserID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setWalkIn(Mockito.<Boolean>any());
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);
        when(sMSGatewayServiceImpl.smsSenderGateway(Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Integer>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(3);

        // Act
        Long actualUpdateGeneralOPDQCDoctorDataResult = quickConsultationServiceImpl
                .updateGeneralOPDQCDoctorData(new JsonObject(), "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ, atLeast(1)).getAllocationDate();
        verify(teleconsultationRequestOBJ).getSpecializationID();
        verify(teleconsultationRequestOBJ).getTmRequestID();
        verify(teleconsultationRequestOBJ).getWalkIn();
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).updatePrescription(isA(PrescriptionDetail.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        verify(sMSGatewayServiceImpl).smsSenderGateway(eq("schedule"), isNull(), isA(Integer.class), isA(Long.class),
                isNull(), isNull(), eq("Mock for Timestamp, hashCode: 1692733352"), isNull(), eq("JaneDoe"));
        assertEquals(1L, actualUpdateGeneralOPDQCDoctorDataResult.longValue());
    }

    @Test
    void testUpdateGeneralOPDQCDoctorData8() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(false);
        when(teleconsultationRequestOBJ.getSpecializationID()).thenReturn(1);
        when(teleconsultationRequestOBJ.getTmRequestID()).thenReturn(1L);
        when(teleconsultationRequestOBJ.getAllocationDate()).thenReturn(mock(Timestamp.class));
        doNothing().when(teleconsultationRequestOBJ).setAllocationDate(Mockito.<Timestamp>any());
        doNothing().when(teleconsultationRequestOBJ).setFromTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setSpecializationID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setTmRequestID(Mockito.<Long>any());
        doNothing().when(teleconsultationRequestOBJ).setToTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setUserID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setWalkIn(Mockito.<Boolean>any());
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);
        when(sMSGatewayServiceImpl.smsSenderGateway(Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Integer>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(3);

        JsonObject quickConsultDoctorOBJ = new JsonObject();
        quickConsultDoctorOBJ.add("chiefComplaintList", new JsonArray(3));

        // Act
        Long actualUpdateGeneralOPDQCDoctorDataResult = quickConsultationServiceImpl
                .updateGeneralOPDQCDoctorData(quickConsultDoctorOBJ, "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ, atLeast(1)).getAllocationDate();
        verify(teleconsultationRequestOBJ).getSpecializationID();
        verify(teleconsultationRequestOBJ).getTmRequestID();
        verify(teleconsultationRequestOBJ).getWalkIn();
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).updatePrescription(isA(PrescriptionDetail.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        verify(sMSGatewayServiceImpl).smsSenderGateway(eq("schedule"), isNull(), isA(Integer.class), isA(Long.class),
                isNull(), isNull(), eq("Mock for Timestamp, hashCode: 1130579228"), isNull(), eq("JaneDoe"));
        assertEquals(1L, actualUpdateGeneralOPDQCDoctorDataResult.longValue());
    }

    @Test
    void testUpdateGeneralOPDQCDoctorData9() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(false);
        when(teleconsultationRequestOBJ.getSpecializationID()).thenReturn(1);
        when(teleconsultationRequestOBJ.getTmRequestID()).thenReturn(1L);
        when(teleconsultationRequestOBJ.getAllocationDate()).thenReturn(mock(Timestamp.class));
        doNothing().when(teleconsultationRequestOBJ).setAllocationDate(Mockito.<Timestamp>any());
        doNothing().when(teleconsultationRequestOBJ).setFromTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setSpecializationID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setTmRequestID(Mockito.<Long>any());
        doNothing().when(teleconsultationRequestOBJ).setToTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setUserID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setWalkIn(Mockito.<Boolean>any());
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);
        when(sMSGatewayServiceImpl.smsSenderGateway(Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Integer>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(3);

        JsonObject quickConsultDoctorOBJ = new JsonObject();
        quickConsultDoctorOBJ.addProperty("chiefComplaintList", "42");

        // Act
        Long actualUpdateGeneralOPDQCDoctorDataResult = quickConsultationServiceImpl
                .updateGeneralOPDQCDoctorData(quickConsultDoctorOBJ, "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ, atLeast(1)).getAllocationDate();
        verify(teleconsultationRequestOBJ).getSpecializationID();
        verify(teleconsultationRequestOBJ).getTmRequestID();
        verify(teleconsultationRequestOBJ).getWalkIn();
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).updatePrescription(isA(PrescriptionDetail.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        verify(sMSGatewayServiceImpl).smsSenderGateway(eq("schedule"), isNull(), isA(Integer.class), isA(Long.class),
                isNull(), isNull(), eq("Mock for Timestamp, hashCode: 571009752"), isNull(), eq("JaneDoe"));
        assertEquals(1L, actualUpdateGeneralOPDQCDoctorDataResult.longValue());
    }

    @Test
    void testUpdateGeneralOPDQCDoctorData10() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(false);
        when(teleconsultationRequestOBJ.getSpecializationID()).thenReturn(1);
        when(teleconsultationRequestOBJ.getTmRequestID()).thenReturn(1L);
        when(teleconsultationRequestOBJ.getAllocationDate()).thenReturn(mock(Timestamp.class));
        doNothing().when(teleconsultationRequestOBJ).setAllocationDate(Mockito.<Timestamp>any());
        doNothing().when(teleconsultationRequestOBJ).setFromTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setSpecializationID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setTmRequestID(Mockito.<Long>any());
        doNothing().when(teleconsultationRequestOBJ).setToTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setUserID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setWalkIn(Mockito.<Boolean>any());
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);
        when(sMSGatewayServiceImpl.smsSenderGateway(Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Integer>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(3);

        JsonObject quickConsultDoctorOBJ = new JsonObject();
        quickConsultDoctorOBJ.addProperty("chiefComplaintList", Integer.valueOf(1));

        // Act
        Long actualUpdateGeneralOPDQCDoctorDataResult = quickConsultationServiceImpl
                .updateGeneralOPDQCDoctorData(quickConsultDoctorOBJ, "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ, atLeast(1)).getAllocationDate();
        verify(teleconsultationRequestOBJ).getSpecializationID();
        verify(teleconsultationRequestOBJ).getTmRequestID();
        verify(teleconsultationRequestOBJ).getWalkIn();
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).updatePrescription(isA(PrescriptionDetail.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        verify(sMSGatewayServiceImpl).smsSenderGateway(eq("schedule"), isNull(), isA(Integer.class), isA(Long.class),
                isNull(), isNull(), eq("Mock for Timestamp, hashCode: 2088182599"), isNull(), eq("JaneDoe"));
        assertEquals(1L, actualUpdateGeneralOPDQCDoctorDataResult.longValue());
    }

    @Test
    void testUpdateGeneralOPDQCDoctorData11() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(false);
        when(teleconsultationRequestOBJ.getSpecializationID()).thenReturn(1);
        when(teleconsultationRequestOBJ.getTmRequestID()).thenReturn(1L);
        when(teleconsultationRequestOBJ.getAllocationDate()).thenReturn(mock(Timestamp.class));
        doNothing().when(teleconsultationRequestOBJ).setAllocationDate(Mockito.<Timestamp>any());
        doNothing().when(teleconsultationRequestOBJ).setFromTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setSpecializationID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setTmRequestID(Mockito.<Long>any());
        doNothing().when(teleconsultationRequestOBJ).setToTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setUserID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setWalkIn(Mockito.<Boolean>any());
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);
        when(sMSGatewayServiceImpl.smsSenderGateway(Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Integer>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(3);

        JsonObject quickConsultDoctorOBJ = new JsonObject();
        quickConsultDoctorOBJ.addProperty("chiefComplaintList", true);

        // Act
        Long actualUpdateGeneralOPDQCDoctorDataResult = quickConsultationServiceImpl
                .updateGeneralOPDQCDoctorData(quickConsultDoctorOBJ, "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ, atLeast(1)).getAllocationDate();
        verify(teleconsultationRequestOBJ).getSpecializationID();
        verify(teleconsultationRequestOBJ).getTmRequestID();
        verify(teleconsultationRequestOBJ).getWalkIn();
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).updatePrescription(isA(PrescriptionDetail.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        verify(sMSGatewayServiceImpl).smsSenderGateway(eq("schedule"), isNull(), isA(Integer.class), isA(Long.class),
                isNull(), isNull(), eq("Mock for Timestamp, hashCode: 1013008245"), isNull(), eq("JaneDoe"));
        assertEquals(1L, actualUpdateGeneralOPDQCDoctorDataResult.longValue());
    }

    @Test
    void testUpdateGeneralOPDQCDoctorData12() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});
        when(commonNurseServiceImpl.updatePrescription(Mockito.<PrescriptionDetail>any())).thenReturn(1);
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(false);
        when(teleconsultationRequestOBJ.getSpecializationID()).thenReturn(1);
        when(teleconsultationRequestOBJ.getTmRequestID()).thenReturn(1L);
        when(teleconsultationRequestOBJ.getAllocationDate()).thenReturn(mock(Timestamp.class));
        doNothing().when(teleconsultationRequestOBJ).setAllocationDate(Mockito.<Timestamp>any());
        doNothing().when(teleconsultationRequestOBJ).setFromTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setSpecializationID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setTmRequestID(Mockito.<Long>any());
        doNothing().when(teleconsultationRequestOBJ).setToTime(Mockito.<String>any());
        doNothing().when(teleconsultationRequestOBJ).setUserID(Mockito.<Integer>any());
        doNothing().when(teleconsultationRequestOBJ).setWalkIn(Mockito.<Boolean>any());
        teleconsultationRequestOBJ.setAllocationDate(mock(Timestamp.class));
        teleconsultationRequestOBJ.setFromTime("jane.doe@example.org");
        teleconsultationRequestOBJ.setSpecializationID(1);
        teleconsultationRequestOBJ.setTmRequestID(1L);
        teleconsultationRequestOBJ.setToTime("To Time");
        teleconsultationRequestOBJ.setUserID(1);
        teleconsultationRequestOBJ.setWalkIn(true);
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenReturn(teleconsultationRequestOBJ);
        when(sMSGatewayServiceImpl.smsSenderGateway(Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Integer>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(3);

        JsonObject quickConsultDoctorOBJ = new JsonObject();
        quickConsultDoctorOBJ.add("labTestOrders", new JsonArray(3));
        quickConsultDoctorOBJ.add("chiefComplaintList", new JsonArray(3));

        // Act
        Long actualUpdateGeneralOPDQCDoctorDataResult = quickConsultationServiceImpl
                .updateGeneralOPDQCDoctorData(quickConsultDoctorOBJ, "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ, atLeast(1)).getAllocationDate();
        verify(teleconsultationRequestOBJ).getSpecializationID();
        verify(teleconsultationRequestOBJ).getTmRequestID();
        verify(teleconsultationRequestOBJ).getWalkIn();
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonNurseServiceImpl).updatePrescription(isA(PrescriptionDetail.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        verify(sMSGatewayServiceImpl).smsSenderGateway(eq("schedule"), isNull(), isA(Integer.class), isA(Long.class),
                isNull(), isNull(), eq("Mock for Timestamp, hashCode: 1851146771"), isNull(), eq("JaneDoe"));
        assertEquals(1L, actualUpdateGeneralOPDQCDoctorDataResult.longValue());
    }

    @Test
    void testUpdateBeneficiaryClinicalObservations() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});

        // Act
        Integer actualUpdateBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .updateBeneficiaryClinicalObservations(new JsonObject());

        // Assert
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        assertEquals(1, actualUpdateBeneficiaryClinicalObservationsResult.intValue());
    }

    @Test
    void testUpdateBeneficiaryClinicalObservations2() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(null);

        // Act
        Integer actualUpdateBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .updateBeneficiaryClinicalObservations(new JsonObject());

        // Assert
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        assertEquals(1, actualUpdateBeneficiaryClinicalObservationsResult.intValue());
    }

    @Test
    void testUpdateBeneficiaryClinicalObservations3() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"foo", null});

        // Act
        Integer actualUpdateBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .updateBeneficiaryClinicalObservations(new JsonObject());

        // Assert
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        assertEquals(1, actualUpdateBeneficiaryClinicalObservationsResult.intValue());
    }

    @Test
    void testUpdateBeneficiaryClinicalObservations4() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);

        // Act
        Integer actualUpdateBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .updateBeneficiaryClinicalObservations(null);

        // Assert
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isNull());
        assertEquals(1, actualUpdateBeneficiaryClinicalObservationsResult.intValue());
    }

    @Test
    void testUpdateBeneficiaryClinicalObservations5() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});

        JsonObject caseSheet = new JsonObject();
        caseSheet.add("Property", new JsonArray(3));

        // Act
        Integer actualUpdateBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .updateBeneficiaryClinicalObservations(caseSheet);

        // Assert
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        assertEquals(1, actualUpdateBeneficiaryClinicalObservationsResult.intValue());
    }

    @Test
    void testUpdateBeneficiaryClinicalObservations6() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});

        JsonObject caseSheet = new JsonObject();
        caseSheet.addProperty("Property", "42");

        // Act
        Integer actualUpdateBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .updateBeneficiaryClinicalObservations(caseSheet);

        // Assert
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        assertEquals(1, actualUpdateBeneficiaryClinicalObservationsResult.intValue());
    }

    @Test
    void testUpdateBeneficiaryClinicalObservations7() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});

        JsonObject caseSheet = new JsonObject();
        Integer value = Integer.valueOf(1);
        caseSheet.addProperty("Property", value);

        // Act
        Integer actualUpdateBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .updateBeneficiaryClinicalObservations(caseSheet);

        // Assert
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        assertEquals(1, actualUpdateBeneficiaryClinicalObservationsResult.intValue());
        assertSame(value, actualUpdateBeneficiaryClinicalObservationsResult);
    }

   
    @Test
    void testUpdateBeneficiaryClinicalObservations8() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});

        JsonObject caseSheet = new JsonObject();
        caseSheet.addProperty("Property", true);

        // Act
        Integer actualUpdateBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .updateBeneficiaryClinicalObservations(caseSheet);

        // Assert
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        assertEquals(1, actualUpdateBeneficiaryClinicalObservationsResult.intValue());
    }

    @Test
    void testUpdateBeneficiaryClinicalObservations9() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> quickConsultationServiceImpl.updateBeneficiaryClinicalObservations(new JsonObject()));
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
    }

    @Test
    void testUpdateBeneficiaryClinicalObservations10() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any()))
                .thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> quickConsultationServiceImpl.updateBeneficiaryClinicalObservations(null));
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isNull());
    }

    @Test
    void testUpdateBeneficiaryClinicalObservations11() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenClinicalObservations(Mockito.<BenClinicalObservations>any())).thenReturn(1);
        when(commonDoctorServiceImpl.getSnomedCTcode(Mockito.<String>any())).thenReturn(new String[]{"Snomed CTcode"});

        JsonObject caseSheet = new JsonObject();
        caseSheet.add("Property", null);

        // Act
        Integer actualUpdateBeneficiaryClinicalObservationsResult = quickConsultationServiceImpl
                .updateBeneficiaryClinicalObservations(caseSheet);

        // Assert
        verify(commonDoctorServiceImpl).getSnomedCTcode(isNull());
        verify(commonDoctorServiceImpl).updateBenClinicalObservations(isA(BenClinicalObservations.class));
        assertEquals(1, actualUpdateBeneficiaryClinicalObservationsResult.intValue());
    }
}
