package com.iemr.hwc.service.common.transaction;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyShort;
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
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.anc.WrapperAncFindings;
import com.iemr.hwc.data.doctor.BenReferDetails;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.quickConsultation.BenChiefComplaint;
import com.iemr.hwc.data.quickConsultation.BenClinicalObservations;
import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.data.snomedct.SCTDescription;
import com.iemr.hwc.data.tele_consultation.TcSpecialistSlotBookingRequestOBJ;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.data.tele_consultation.TeleconsultationStats;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.doctor.BenReferDetailsRepo;
import com.iemr.hwc.repo.doctor.DocWorkListRepo;
import com.iemr.hwc.repo.foetalmonitor.FoetalMonitorRepo;
import com.iemr.hwc.repo.nurse.ncdcare.NCDCareDiagnosisRepo;
import com.iemr.hwc.repo.nurse.pnc.PNCDiagnosisRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repo.quickConsultation.BenClinicalObservationsRepo;
import com.iemr.hwc.repo.quickConsultation.LabTestOrderDetailRepo;
import com.iemr.hwc.repo.quickConsultation.PrescribedDrugDetailRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.repo.tc_consultation.TCRequestModelRepo;
import com.iemr.hwc.repo.tc_consultation.TeleconsultationStatsRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.hwc.service.snomedct.SnomedServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class CommonDoctorServiceImplTest {
    @Mock
    private BenChiefComplaintRepo benChiefComplaintRepo;

    @Mock
    private BenClinicalObservationsRepo benClinicalObservationsRepo;

    @Mock
    private BenReferDetailsRepo benReferDetailsRepo;

    @Mock
    private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

    @Mock
    private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

    @InjectMocks
    private CommonDoctorServiceImpl commonDoctorServiceImpl;

    @Mock
    private DocWorkListRepo docWorkListRepo;

    @Mock
    private FoetalMonitorRepo foetalMonitorRepo;

    @Mock
    private LabTestOrderDetailRepo labTestOrderDetailRepo;

    @Mock
    private NCDCareDiagnosisRepo nCDCareDiagnosisRepo;

    @Mock
    private PNCDiagnosisRepo pNCDiagnosisRepo;

    @Mock
    private PrescribedDrugDetailRepo prescribedDrugDetailRepo;

    @Mock
    private PrescriptionDetailRepo prescriptionDetailRepo;

    @Mock
    private SMSGatewayServiceImpl sMSGatewayServiceImpl;

    @Mock
    private SnomedServiceImpl snomedServiceImpl;

    @Mock
    private TCRequestModelRepo tCRequestModelRepo;

    @Mock
    private TeleconsultationStatsRepo teleconsultationStatsRepo;

    @Test
    void testSaveFindings() throws Exception {
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

        // Act
        Integer actualSaveFindingsResult = commonDoctorServiceImpl.saveFindings(new JsonObject());

        // Assert
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1, actualSaveFindingsResult.intValue());
    }

    @Test
    void testSaveFindings2() throws Exception {
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

        // Act
        Integer actualSaveFindingsResult = commonDoctorServiceImpl.saveFindings(null);

        // Assert
        verify(benClinicalObservationsRepo).save(isNull());
        assertEquals(1, actualSaveFindingsResult.intValue());
    }

    @Test
    void testSaveFindings3() throws Exception {
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

        JsonObject obj = new JsonObject();
        obj.add("Property", new JsonArray(3));

        // Act
        Integer actualSaveFindingsResult = commonDoctorServiceImpl.saveFindings(obj);

        // Assert
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1, actualSaveFindingsResult.intValue());
    }

    @Test
    void testSaveFindings4() throws Exception {
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

        JsonObject obj = new JsonObject();
        obj.addProperty("Property", "42");

        // Act
        Integer actualSaveFindingsResult = commonDoctorServiceImpl.saveFindings(obj);

        // Assert
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1, actualSaveFindingsResult.intValue());
    }

    @Test
    void testSaveFindings5() throws Exception {
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

        JsonObject obj = new JsonObject();
        Integer value = Integer.valueOf(1);
        obj.addProperty("Property", value);

        // Act
        Integer actualSaveFindingsResult = commonDoctorServiceImpl.saveFindings(obj);

        // Assert
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1, actualSaveFindingsResult.intValue());
        assertSame(value, actualSaveFindingsResult);
    }

    @Test
    void testSaveFindings6() throws Exception {
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

        JsonObject obj = new JsonObject();
        obj.addProperty("Property", true);

        // Act
        Integer actualSaveFindingsResult = commonDoctorServiceImpl.saveFindings(obj);

        // Assert
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1, actualSaveFindingsResult.intValue());
    }

    @Test
    void testSaveDocFindings() {
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

        SCTDescription sctDescription = new SCTDescription();
        sctDescription.setActive("Active");
        sctDescription.setCaseSignificanceID("Case Significance ID");
        sctDescription.setConceptID("Concept ID");
        sctDescription.setPageNo(1);
        sctDescription.setSctDesID(1L);
        sctDescription.setTerm("Term");
        when(snomedServiceImpl.findSnomedCTRecordFromTerm(Mockito.<String>any())).thenReturn(sctDescription);

        // Act
        Integer actualSaveDocFindingsResult = commonDoctorServiceImpl
                .saveDocFindings(new WrapperAncFindings(1L, 1L, 1, "Jan 1, 2020 8:00am GMT+0100", "Clinical Observation",
                        "Other Symptoms", "Significant Findings", new ArrayList<>()));

        // Assert
        verify(snomedServiceImpl).findSnomedCTRecordFromTerm(eq("Other Symptoms"));
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1, actualSaveDocFindingsResult.intValue());
    }

    @Test
    void testSaveDocFindings2() {
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
        SCTDescription sctDescription = mock(SCTDescription.class);
        when(sctDescription.getConceptID()).thenReturn("Concept ID");
        when(sctDescription.getTerm()).thenReturn("Term");
        doNothing().when(sctDescription).setActive(Mockito.<String>any());
        doNothing().when(sctDescription).setCaseSignificanceID(Mockito.<String>any());
        doNothing().when(sctDescription).setConceptID(Mockito.<String>any());
        doNothing().when(sctDescription).setPageNo(Mockito.<Integer>any());
        doNothing().when(sctDescription).setSctDesID(Mockito.<Long>any());
        doNothing().when(sctDescription).setTerm(Mockito.<String>any());
        sctDescription.setActive("Active");
        sctDescription.setCaseSignificanceID("Case Significance ID");
        sctDescription.setConceptID("Concept ID");
        sctDescription.setPageNo(1);
        sctDescription.setSctDesID(1L);
        sctDescription.setTerm("Term");
        when(snomedServiceImpl.findSnomedCTRecordFromTerm(Mockito.<String>any())).thenReturn(sctDescription);

        // Act
        Integer actualSaveDocFindingsResult = commonDoctorServiceImpl
                .saveDocFindings(new WrapperAncFindings(1L, 1L, 1, "Jan 1, 2020 8:00am GMT+0100", "Clinical Observation",
                        "Other Symptoms", "Significant Findings", new ArrayList<>()));

        // Assert
        verify(sctDescription).getConceptID();
        verify(sctDescription).getTerm();
        verify(sctDescription).setActive(eq("Active"));
        verify(sctDescription).setCaseSignificanceID(eq("Case Significance ID"));
        verify(sctDescription).setConceptID(eq("Concept ID"));
        verify(sctDescription).setPageNo(isA(Integer.class));
        verify(sctDescription).setSctDesID(isA(Long.class));
        verify(sctDescription).setTerm(eq("Term"));
        verify(snomedServiceImpl).findSnomedCTRecordFromTerm(eq("Other Symptoms"));
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1, actualSaveDocFindingsResult.intValue());
    }

    @Test
    void testGetSnomedCTcode() {
        // Arrange
        SCTDescription sctDescription = new SCTDescription();
        sctDescription.setActive("Active");
        sctDescription.setCaseSignificanceID("Case Significance ID");
        sctDescription.setConceptID("Concept ID");
        sctDescription.setPageNo(1);
        sctDescription.setSctDesID(1L);
        sctDescription.setTerm("Term");
        when(snomedServiceImpl.findSnomedCTRecordFromTerm(Mockito.<String>any())).thenReturn(sctDescription);

        // Act
        String[] actualSnomedCTcode = commonDoctorServiceImpl.getSnomedCTcode("Request String");

        // Assert
        verify(snomedServiceImpl).findSnomedCTRecordFromTerm(eq("Request String"));
        assertArrayEquals(new String[]{"Concept ID", "Term"}, actualSnomedCTcode);
    }

    @Test
    void testGetSnomedCTcode2() {
        // Arrange
        SCTDescription sctDescription = mock(SCTDescription.class);
        when(sctDescription.getConceptID()).thenReturn("Concept ID");
        when(sctDescription.getTerm()).thenReturn("Term");
        doNothing().when(sctDescription).setActive(Mockito.<String>any());
        doNothing().when(sctDescription).setCaseSignificanceID(Mockito.<String>any());
        doNothing().when(sctDescription).setConceptID(Mockito.<String>any());
        doNothing().when(sctDescription).setPageNo(Mockito.<Integer>any());
        doNothing().when(sctDescription).setSctDesID(Mockito.<Long>any());
        doNothing().when(sctDescription).setTerm(Mockito.<String>any());
        sctDescription.setActive("Active");
        sctDescription.setCaseSignificanceID("Case Significance ID");
        sctDescription.setConceptID("Concept ID");
        sctDescription.setPageNo(1);
        sctDescription.setSctDesID(1L);
        sctDescription.setTerm("Term");
        when(snomedServiceImpl.findSnomedCTRecordFromTerm(Mockito.<String>any())).thenReturn(sctDescription);

        // Act
        String[] actualSnomedCTcode = commonDoctorServiceImpl.getSnomedCTcode("Request String");

        // Assert
        verify(sctDescription).getConceptID();
        verify(sctDescription).getTerm();
        verify(sctDescription).setActive(eq("Active"));
        verify(sctDescription).setCaseSignificanceID(eq("Case Significance ID"));
        verify(sctDescription).setConceptID(eq("Concept ID"));
        verify(sctDescription).setPageNo(isA(Integer.class));
        verify(sctDescription).setSctDesID(isA(Long.class));
        verify(sctDescription).setTerm(eq("Term"));
        verify(snomedServiceImpl).findSnomedCTRecordFromTerm(eq("Request String"));
        assertArrayEquals(new String[]{"Concept ID", "Term"}, actualSnomedCTcode);
    }

    @Test
    void testGetSnomedCTcode3() {
        // Arrange
        SCTDescription sctDescription = mock(SCTDescription.class);
        doNothing().when(sctDescription).setActive(Mockito.<String>any());
        doNothing().when(sctDescription).setCaseSignificanceID(Mockito.<String>any());
        doNothing().when(sctDescription).setConceptID(Mockito.<String>any());
        doNothing().when(sctDescription).setPageNo(Mockito.<Integer>any());
        doNothing().when(sctDescription).setSctDesID(Mockito.<Long>any());
        doNothing().when(sctDescription).setTerm(Mockito.<String>any());
        sctDescription.setActive("Active");
        sctDescription.setCaseSignificanceID("Case Significance ID");
        sctDescription.setConceptID("Concept ID");
        sctDescription.setPageNo(1);
        sctDescription.setSctDesID(1L);
        sctDescription.setTerm("Term");

        // Act
        String[] actualSnomedCTcode = commonDoctorServiceImpl.getSnomedCTcode("");

        // Assert
        verify(sctDescription).setActive(eq("Active"));
        verify(sctDescription).setCaseSignificanceID(eq("Case Significance ID"));
        verify(sctDescription).setConceptID(eq("Concept ID"));
        verify(sctDescription).setPageNo(isA(Integer.class));
        verify(sctDescription).setSctDesID(isA(Long.class));
        verify(sctDescription).setTerm(eq("Term"));
        assertArrayEquals(new String[]{"", ""}, actualSnomedCTcode);
    }

    @Test
    void testGetSnomedCTcode4() {
        // Arrange
        SCTDescription sctDescription = mock(SCTDescription.class);
        doNothing().when(sctDescription).setActive(Mockito.<String>any());
        doNothing().when(sctDescription).setCaseSignificanceID(Mockito.<String>any());
        doNothing().when(sctDescription).setConceptID(Mockito.<String>any());
        doNothing().when(sctDescription).setPageNo(Mockito.<Integer>any());
        doNothing().when(sctDescription).setSctDesID(Mockito.<Long>any());
        doNothing().when(sctDescription).setTerm(Mockito.<String>any());
        sctDescription.setActive("Active");
        sctDescription.setCaseSignificanceID("Case Significance ID");
        sctDescription.setConceptID("Concept ID");
        sctDescription.setPageNo(1);
        sctDescription.setSctDesID(1L);
        sctDescription.setTerm("Term");

        // Act
        String[] actualSnomedCTcode = commonDoctorServiceImpl.getSnomedCTcode(null);

        // Assert
        verify(sctDescription).setActive(eq("Active"));
        verify(sctDescription).setCaseSignificanceID(eq("Case Significance ID"));
        verify(sctDescription).setConceptID(eq("Concept ID"));
        verify(sctDescription).setPageNo(isA(Integer.class));
        verify(sctDescription).setSctDesID(isA(Long.class));
        verify(sctDescription).setTerm(eq("Term"));
        assertArrayEquals(new String[]{"", ""}, actualSnomedCTcode);
    }

    @Test
    void testGetSnomedCTcode5() {
        // Arrange
        SCTDescription sctDescription = mock(SCTDescription.class);
        when(sctDescription.getConceptID()).thenReturn("Concept ID");
        when(sctDescription.getTerm()).thenReturn("Term");
        doNothing().when(sctDescription).setActive(Mockito.<String>any());
        doNothing().when(sctDescription).setCaseSignificanceID(Mockito.<String>any());
        doNothing().when(sctDescription).setConceptID(Mockito.<String>any());
        doNothing().when(sctDescription).setPageNo(Mockito.<Integer>any());
        doNothing().when(sctDescription).setSctDesID(Mockito.<Long>any());
        doNothing().when(sctDescription).setTerm(Mockito.<String>any());
        sctDescription.setActive("Active");
        sctDescription.setCaseSignificanceID("Case Significance ID");
        sctDescription.setConceptID("Concept ID");
        sctDescription.setPageNo(1);
        sctDescription.setSctDesID(1L);
        sctDescription.setTerm("Term");
        when(snomedServiceImpl.findSnomedCTRecordFromTerm(Mockito.<String>any())).thenReturn(sctDescription);

        // Act
        String[] actualSnomedCTcode = commonDoctorServiceImpl.getSnomedCTcode(",Request String");

        // Assert
        verify(sctDescription, atLeast(1)).getConceptID();
        verify(sctDescription, atLeast(1)).getTerm();
        verify(sctDescription).setActive(eq("Active"));
        verify(sctDescription).setCaseSignificanceID(eq("Case Significance ID"));
        verify(sctDescription).setConceptID(eq("Concept ID"));
        verify(sctDescription).setPageNo(isA(Integer.class));
        verify(sctDescription).setSctDesID(isA(Long.class));
        verify(sctDescription).setTerm(eq("Term"));
        verify(snomedServiceImpl, atLeast(1)).findSnomedCTRecordFromTerm(Mockito.<String>any());
        assertArrayEquals(new String[]{"Concept ID,Concept ID", "Term,Term"}, actualSnomedCTcode);
    }

    @Test
    void testGetDocWorkList() {
        // Arrange
        when(docWorkListRepo.getDocWorkList()).thenReturn(new ArrayList<>());

        // Act
        String actualDocWorkList = commonDoctorServiceImpl.getDocWorkList();

        // Assert
        verify(docWorkListRepo).getDocWorkList();
        assertEquals("[]", actualDocWorkList);
    }

    @Test
    void testGetDocWorkListNew() {
        // Arrange, Act and Assert
        assertEquals("[]", commonDoctorServiceImpl.getDocWorkListNew(1, 1, 1));
        assertEquals("[]", commonDoctorServiceImpl.getDocWorkListNew(1, null, 1));
    }

    @Test
    void testGetDocWorkListNew2() {
        // Arrange
        when(beneficiaryFlowStatusRepo.getDocWorkListNew(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualDocWorkListNew = commonDoctorServiceImpl.getDocWorkListNew(1, 2, 1);

        // Assert
        verify(beneficiaryFlowStatusRepo).getDocWorkListNew(isA(Integer.class));
        assertEquals("[]", actualDocWorkListNew);
    }

    @Test
    void testGetDocWorkListNew3() {
        // Arrange
        when(beneficiaryFlowStatusRepo.getDocWorkListNewTC(Mockito.<Integer>any(), Mockito.<Timestamp>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualDocWorkListNew = commonDoctorServiceImpl.getDocWorkListNew(1, 9, 1);

        // Assert
        verify(beneficiaryFlowStatusRepo).getDocWorkListNewTC(isA(Integer.class), isA(Timestamp.class), isA(Integer.class));
        assertEquals("[]", actualDocWorkListNew);
    }

    @Test
    void testGetDocWorkListNewFutureScheduledForTM() {
        // Arrange, Act and Assert
        assertEquals("[]", commonDoctorServiceImpl.getDocWorkListNewFutureScheduledForTM(1, 1, 1));
        assertEquals("[]", commonDoctorServiceImpl.getDocWorkListNewFutureScheduledForTM(1, null, 1));
    }

    @Test
    void testGetDocWorkListNewFutureScheduledForTM2() {
        // Arrange
        when(beneficiaryFlowStatusRepo.getDocWorkListNewFutureScheduledTC(Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualDocWorkListNewFutureScheduledForTM = commonDoctorServiceImpl.getDocWorkListNewFutureScheduledForTM(1,
                9, 1);

        // Assert
        verify(beneficiaryFlowStatusRepo).getDocWorkListNewFutureScheduledTC(isA(Integer.class), isA(Integer.class));
        assertEquals("[]", actualDocWorkListNewFutureScheduledForTM);
    }

    @Test
    void testGetTCSpecialistWorkListNewForTMPatientApp() {
        // Arrange, Act and Assert
        assertEquals("[]", commonDoctorServiceImpl.getTCSpecialistWorkListNewForTMPatientApp(1, 1, 1, 1));
        assertEquals("[]", commonDoctorServiceImpl.getTCSpecialistWorkListNewForTMPatientApp(1, 1, null, 1));
    }

    @Test
    void testGetTCSpecialistWorkListNewForTMPatientApp2() {
        // Arrange
        when(beneficiaryFlowStatusRepo.getTCSpecialistWorkListNewPatientApp(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Timestamp>any(), Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualTCSpecialistWorkListNewForTMPatientApp = commonDoctorServiceImpl
                .getTCSpecialistWorkListNewForTMPatientApp(1, 1, 9, 1);

        // Assert
        verify(beneficiaryFlowStatusRepo).getTCSpecialistWorkListNewPatientApp(isA(Integer.class), isA(Integer.class),
                isA(Timestamp.class), isA(Integer.class));
        assertEquals("[]", actualTCSpecialistWorkListNewForTMPatientApp);
    }

    @Test
    void testGetTCSpecialistWorkListNewForTM() {
        // Arrange, Act and Assert
        assertEquals("[]", commonDoctorServiceImpl.getTCSpecialistWorkListNewForTM(1, 1, 1));
        assertEquals("[]", commonDoctorServiceImpl.getTCSpecialistWorkListNewForTM(1, 1, null));
    }

    @Test
    void testGetTCSpecialistWorkListNewForTM2() {
        // Arrange
        when(beneficiaryFlowStatusRepo.getTCSpecialistWorkListNew(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Timestamp>any())).thenReturn(new ArrayList<>());

        // Act
        String actualTCSpecialistWorkListNewForTM = commonDoctorServiceImpl.getTCSpecialistWorkListNewForTM(1, 1, 9);

        // Assert
        verify(beneficiaryFlowStatusRepo).getTCSpecialistWorkListNew(isA(Integer.class), isA(Integer.class),
                isA(Timestamp.class));
        assertEquals("[]", actualTCSpecialistWorkListNewForTM);
    }

    @Test
    void testGetTCSpecialistWorkListNewFutureScheduledForTM() {
        // Arrange, Act and Assert
        assertEquals("[]", commonDoctorServiceImpl.getTCSpecialistWorkListNewFutureScheduledForTM(1, 1, 1));
        assertEquals("[]", commonDoctorServiceImpl.getTCSpecialistWorkListNewFutureScheduledForTM(1, 1, null));
    }

    @Test
    void testGetTCSpecialistWorkListNewFutureScheduledForTM2() {
        // Arrange
        when(beneficiaryFlowStatusRepo.getTCSpecialistWorkListNewFutureScheduled(Mockito.<Integer>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualTCSpecialistWorkListNewFutureScheduledForTM = commonDoctorServiceImpl
                .getTCSpecialistWorkListNewFutureScheduledForTM(1, 1, 9);

        // Assert
        verify(beneficiaryFlowStatusRepo).getTCSpecialistWorkListNewFutureScheduled(isA(Integer.class), isA(Integer.class));
        assertEquals("[]", actualTCSpecialistWorkListNewFutureScheduledForTM);
    }

    @Test
    void testFetchBenPreviousSignificantFindings() {
        // Arrange
        when(benClinicalObservationsRepo.getPreviousSignificantFindings(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        String actualFetchBenPreviousSignificantFindingsResult = commonDoctorServiceImpl
                .fetchBenPreviousSignificantFindings(1L);

        // Assert
        verify(benClinicalObservationsRepo).getPreviousSignificantFindings(isA(Long.class));
        assertEquals("{\"findings\":[]}", actualFetchBenPreviousSignificantFindingsResult);
    }

    @Test
    void testFetchBenPreviousSignificantFindings2() {
        // Arrange
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(10L);

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{date, "42"});
        when(benClinicalObservationsRepo.getPreviousSignificantFindings(Mockito.<Long>any())).thenReturn(objectArrayList);

        // Act
        commonDoctorServiceImpl.fetchBenPreviousSignificantFindings(1L);

        // Assert
        verify(benClinicalObservationsRepo).getPreviousSignificantFindings(isA(Long.class));
        verify(date).getTime();
    }

    @Test
    void testFetchBenPreviousSignificantFindings3() {
        // Arrange
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(10L);

        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{date, ""});
        when(benClinicalObservationsRepo.getPreviousSignificantFindings(Mockito.<Long>any())).thenReturn(objectArrayList);

        // Act
        commonDoctorServiceImpl.fetchBenPreviousSignificantFindings(1L);

        // Assert
        verify(benClinicalObservationsRepo).getPreviousSignificantFindings(isA(Long.class));
        verify(date).getTime();
    }

    @Test
    void testSaveBenReferDetails() throws IEMRException {
        // Arrange
        BenReferDetails benReferDetails = new BenReferDetails();
        benReferDetails.setBenReferID(1L);
        benReferDetails.setBenVisitID(1L);
        benReferDetails.setBeneficiaryRegID(1L);
        benReferDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benReferDetails.setCreatedDate(mock(Timestamp.class));
        benReferDetails.setDeleted(true);
        benReferDetails.setLastModDate(mock(Timestamp.class));
        benReferDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benReferDetails.setOtherReferralReason("Just cause");
        benReferDetails.setOtherReferredToInstituteName("Other Referred To Institute Name");
        benReferDetails.setParkingPlaceID(1);
        benReferDetails.setProcessed("Processed");
        benReferDetails.setProviderServiceMapID(1);
        benReferDetails.setReferralReason("Just cause");
        benReferDetails.setReferralReasonList(new String[]{"Just cause"});
        benReferDetails.setReferredToInstituteID(1);
        benReferDetails.setReferredToInstituteName("Referred To Institute Name");
        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"Refrred To Additional Service List"});
        benReferDetails.setReservedForChange("Reserved For Change");
        benReferDetails.setRevisitDate(mock(Timestamp.class));
        benReferDetails.setServiceID((short) 1);
        benReferDetails.setServiceName("Service Name");
        benReferDetails.setSyncedBy("Synced By");
        benReferDetails.setSyncedDate(mock(Timestamp.class));
        benReferDetails.setVanID(1);
        benReferDetails.setVanSerialNo(1L);
        benReferDetails.setVehicalNo("Vehical No");
        benReferDetails.setVisitCode(1L);
        when(benReferDetailsRepo.save(Mockito.<BenReferDetails>any())).thenReturn(benReferDetails);

        // Act
        Long actualSaveBenReferDetailsResult = commonDoctorServiceImpl.saveBenReferDetails(new JsonObject(), true);

        // Assert
        verify(benReferDetailsRepo).save(isA(BenReferDetails.class));
        assertEquals(1L, actualSaveBenReferDetailsResult.longValue());
    }

    @Test
    void testSaveBenReferDetails2() throws IEMRException {
        // Arrange, Act and Assert
        assertEquals(1L, commonDoctorServiceImpl.saveBenReferDetails(null, true).longValue());
    }

    @Test
    void testSaveBenReferDetails3() throws IEMRException {
        // Arrange
        BenReferDetails benReferDetails = new BenReferDetails();
        benReferDetails.setBenReferID(1L);
        benReferDetails.setBenVisitID(1L);
        benReferDetails.setBeneficiaryRegID(1L);
        benReferDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benReferDetails.setCreatedDate(mock(Timestamp.class));
        benReferDetails.setDeleted(true);
        benReferDetails.setLastModDate(mock(Timestamp.class));
        benReferDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benReferDetails.setOtherReferralReason("Just cause");
        benReferDetails.setOtherReferredToInstituteName("Other Referred To Institute Name");
        benReferDetails.setParkingPlaceID(1);
        benReferDetails.setProcessed("Processed");
        benReferDetails.setProviderServiceMapID(1);
        benReferDetails.setReferralReason("Just cause");
        benReferDetails.setReferralReasonList(new String[]{"Just cause"});
        benReferDetails.setReferredToInstituteID(1);
        benReferDetails.setReferredToInstituteName("Referred To Institute Name");
        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"Refrred To Additional Service List"});
        benReferDetails.setReservedForChange("Reserved For Change");
        benReferDetails.setRevisitDate(mock(Timestamp.class));
        benReferDetails.setServiceID((short) 1);
        benReferDetails.setServiceName("Service Name");
        benReferDetails.setSyncedBy("Synced By");
        benReferDetails.setSyncedDate(mock(Timestamp.class));
        benReferDetails.setVanID(1);
        benReferDetails.setVanSerialNo(1L);
        benReferDetails.setVehicalNo("Vehical No");
        benReferDetails.setVisitCode(1L);
        when(benReferDetailsRepo.save(Mockito.<BenReferDetails>any())).thenReturn(benReferDetails);

        JsonObject obj = new JsonObject();
        obj.add("Property", new JsonArray(3));

        // Act
        Long actualSaveBenReferDetailsResult = commonDoctorServiceImpl.saveBenReferDetails(obj, true);

        // Assert
        verify(benReferDetailsRepo).save(isA(BenReferDetails.class));
        assertEquals(1L, actualSaveBenReferDetailsResult.longValue());
    }

    @Test
    void testSaveBenReferDetails4() throws IEMRException {
        // Arrange
        BenReferDetails benReferDetails = new BenReferDetails();
        benReferDetails.setBenReferID(1L);
        benReferDetails.setBenVisitID(1L);
        benReferDetails.setBeneficiaryRegID(1L);
        benReferDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benReferDetails.setCreatedDate(mock(Timestamp.class));
        benReferDetails.setDeleted(true);
        benReferDetails.setLastModDate(mock(Timestamp.class));
        benReferDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benReferDetails.setOtherReferralReason("Just cause");
        benReferDetails.setOtherReferredToInstituteName("Other Referred To Institute Name");
        benReferDetails.setParkingPlaceID(1);
        benReferDetails.setProcessed("Processed");
        benReferDetails.setProviderServiceMapID(1);
        benReferDetails.setReferralReason("Just cause");
        benReferDetails.setReferralReasonList(new String[]{"Just cause"});
        benReferDetails.setReferredToInstituteID(1);
        benReferDetails.setReferredToInstituteName("Referred To Institute Name");
        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"Refrred To Additional Service List"});
        benReferDetails.setReservedForChange("Reserved For Change");
        benReferDetails.setRevisitDate(mock(Timestamp.class));
        benReferDetails.setServiceID((short) 1);
        benReferDetails.setServiceName("Service Name");
        benReferDetails.setSyncedBy("Synced By");
        benReferDetails.setSyncedDate(mock(Timestamp.class));
        benReferDetails.setVanID(1);
        benReferDetails.setVanSerialNo(1L);
        benReferDetails.setVehicalNo("Vehical No");
        benReferDetails.setVisitCode(1L);
        when(benReferDetailsRepo.save(Mockito.<BenReferDetails>any())).thenReturn(benReferDetails);

        JsonObject obj = new JsonObject();
        obj.addProperty("Property", "42");

        // Act
        Long actualSaveBenReferDetailsResult = commonDoctorServiceImpl.saveBenReferDetails(obj, true);

        // Assert
        verify(benReferDetailsRepo).save(isA(BenReferDetails.class));
        assertEquals(1L, actualSaveBenReferDetailsResult.longValue());
    }

    @Test
    void testSaveBenReferDetails5() throws IEMRException {
        // Arrange
        BenReferDetails benReferDetails = new BenReferDetails();
        benReferDetails.setBenReferID(1L);
        benReferDetails.setBenVisitID(1L);
        benReferDetails.setBeneficiaryRegID(1L);
        benReferDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benReferDetails.setCreatedDate(mock(Timestamp.class));
        benReferDetails.setDeleted(true);
        benReferDetails.setLastModDate(mock(Timestamp.class));
        benReferDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benReferDetails.setOtherReferralReason("Just cause");
        benReferDetails.setOtherReferredToInstituteName("Other Referred To Institute Name");
        benReferDetails.setParkingPlaceID(1);
        benReferDetails.setProcessed("Processed");
        benReferDetails.setProviderServiceMapID(1);
        benReferDetails.setReferralReason("Just cause");
        benReferDetails.setReferralReasonList(new String[]{"Just cause"});
        benReferDetails.setReferredToInstituteID(1);
        benReferDetails.setReferredToInstituteName("Referred To Institute Name");
        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"Refrred To Additional Service List"});
        benReferDetails.setReservedForChange("Reserved For Change");
        benReferDetails.setRevisitDate(mock(Timestamp.class));
        benReferDetails.setServiceID((short) 1);
        benReferDetails.setServiceName("Service Name");
        benReferDetails.setSyncedBy("Synced By");
        benReferDetails.setSyncedDate(mock(Timestamp.class));
        benReferDetails.setVanID(1);
        benReferDetails.setVanSerialNo(1L);
        benReferDetails.setVehicalNo("Vehical No");
        benReferDetails.setVisitCode(1L);
        when(benReferDetailsRepo.save(Mockito.<BenReferDetails>any())).thenReturn(benReferDetails);

        JsonObject obj = new JsonObject();
        obj.addProperty("Property", Integer.valueOf(1));

        // Act
        Long actualSaveBenReferDetailsResult = commonDoctorServiceImpl.saveBenReferDetails(obj, true);

        // Assert
        verify(benReferDetailsRepo).save(isA(BenReferDetails.class));
        assertEquals(1L, actualSaveBenReferDetailsResult.longValue());
    }

    @Test
    void testSaveBenReferDetails6() throws IEMRException {
        // Arrange
        BenReferDetails benReferDetails = new BenReferDetails();
        benReferDetails.setBenReferID(1L);
        benReferDetails.setBenVisitID(1L);
        benReferDetails.setBeneficiaryRegID(1L);
        benReferDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benReferDetails.setCreatedDate(mock(Timestamp.class));
        benReferDetails.setDeleted(true);
        benReferDetails.setLastModDate(mock(Timestamp.class));
        benReferDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benReferDetails.setOtherReferralReason("Just cause");
        benReferDetails.setOtherReferredToInstituteName("Other Referred To Institute Name");
        benReferDetails.setParkingPlaceID(1);
        benReferDetails.setProcessed("Processed");
        benReferDetails.setProviderServiceMapID(1);
        benReferDetails.setReferralReason("Just cause");
        benReferDetails.setReferralReasonList(new String[]{"Just cause"});
        benReferDetails.setReferredToInstituteID(1);
        benReferDetails.setReferredToInstituteName("Referred To Institute Name");
        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"Refrred To Additional Service List"});
        benReferDetails.setReservedForChange("Reserved For Change");
        benReferDetails.setRevisitDate(mock(Timestamp.class));
        benReferDetails.setServiceID((short) 1);
        benReferDetails.setServiceName("Service Name");
        benReferDetails.setSyncedBy("Synced By");
        benReferDetails.setSyncedDate(mock(Timestamp.class));
        benReferDetails.setVanID(1);
        benReferDetails.setVanSerialNo(1L);
        benReferDetails.setVehicalNo("Vehical No");
        benReferDetails.setVisitCode(1L);
        when(benReferDetailsRepo.save(Mockito.<BenReferDetails>any())).thenReturn(benReferDetails);

        JsonObject obj = new JsonObject();
        obj.addProperty("Property", true);

        // Act
        Long actualSaveBenReferDetailsResult = commonDoctorServiceImpl.saveBenReferDetails(obj, true);

        // Assert
        verify(benReferDetailsRepo).save(isA(BenReferDetails.class));
        assertEquals(1L, actualSaveBenReferDetailsResult.longValue());
    }

    @Test
    void testSaveBenReferDetails7() throws IEMRException {
        // Arrange
        BenReferDetails benReferDetails = new BenReferDetails();
        benReferDetails.setBenReferID(1L);
        benReferDetails.setBenVisitID(1L);
        benReferDetails.setBeneficiaryRegID(1L);
        benReferDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benReferDetails.setCreatedDate(mock(Timestamp.class));
        benReferDetails.setDeleted(true);
        benReferDetails.setLastModDate(mock(Timestamp.class));
        benReferDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benReferDetails.setOtherReferralReason("Just cause");
        benReferDetails.setOtherReferredToInstituteName("Other Referred To Institute Name");
        benReferDetails.setParkingPlaceID(1);
        benReferDetails.setProcessed("Processed");
        benReferDetails.setProviderServiceMapID(1);
        benReferDetails.setReferralReason("Just cause");
        benReferDetails.setReferralReasonList(new String[]{"Just cause"});
        benReferDetails.setReferredToInstituteID(1);
        benReferDetails.setReferredToInstituteName("Referred To Institute Name");
        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"Refrred To Additional Service List"});
        benReferDetails.setReservedForChange("Reserved For Change");
        benReferDetails.setRevisitDate(mock(Timestamp.class));
        benReferDetails.setServiceID((short) 1);
        benReferDetails.setServiceName("Service Name");
        benReferDetails.setSyncedBy("Synced By");
        benReferDetails.setSyncedDate(mock(Timestamp.class));
        benReferDetails.setVanID(1);
        benReferDetails.setVanSerialNo(1L);
        benReferDetails.setVehicalNo("Vehical No");
        benReferDetails.setVisitCode(1L);
        when(benReferDetailsRepo.save(Mockito.<BenReferDetails>any())).thenReturn(benReferDetails);

        // Act
        Long actualSaveBenReferDetailsResult = commonDoctorServiceImpl.saveBenReferDetails(new JsonObject(), false);

        // Assert
        verify(benReferDetailsRepo).save(isA(BenReferDetails.class));
        assertEquals(1L, actualSaveBenReferDetailsResult.longValue());
    }

    @Test
    void testSaveBenReferDetails8() throws IEMRException {
        // Arrange
        BenReferDetails benReferDetails = new BenReferDetails();
        benReferDetails.setBenReferID(1L);
        benReferDetails.setBenVisitID(1L);
        benReferDetails.setBeneficiaryRegID(1L);
        benReferDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benReferDetails.setCreatedDate(mock(Timestamp.class));
        benReferDetails.setDeleted(true);
        benReferDetails.setLastModDate(mock(Timestamp.class));
        benReferDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benReferDetails.setOtherReferralReason("Just cause");
        benReferDetails.setOtherReferredToInstituteName("Other Referred To Institute Name");
        benReferDetails.setParkingPlaceID(1);
        benReferDetails.setProcessed("Processed");
        benReferDetails.setProviderServiceMapID(1);
        benReferDetails.setReferralReason("Just cause");
        benReferDetails.setReferralReasonList(new String[]{"Just cause"});
        benReferDetails.setReferredToInstituteID(1);
        benReferDetails.setReferredToInstituteName("Referred To Institute Name");
        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"Refrred To Additional Service List"});
        benReferDetails.setReservedForChange("Reserved For Change");
        benReferDetails.setRevisitDate(mock(Timestamp.class));
        benReferDetails.setServiceID((short) 1);
        benReferDetails.setServiceName("Service Name");
        benReferDetails.setSyncedBy("Synced By");
        benReferDetails.setSyncedDate(mock(Timestamp.class));
        benReferDetails.setVanID(1);
        benReferDetails.setVanSerialNo(1L);
        benReferDetails.setVehicalNo("Vehical No");
        benReferDetails.setVisitCode(1L);
        when(benReferDetailsRepo.save(Mockito.<BenReferDetails>any())).thenReturn(benReferDetails);

        // Act
        Long actualSaveBenReferDetailsResult = commonDoctorServiceImpl.saveBenReferDetails(new JsonObject(), null);

        // Assert
        verify(benReferDetailsRepo).save(isA(BenReferDetails.class));
        assertEquals(1L, actualSaveBenReferDetailsResult.longValue());
    }

    @Test
    void testGetFindingsDetails() {
        // Arrange
        when(benChiefComplaintRepo.getBenChiefComplaints(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        when(benClinicalObservationsRepo.getFindingsData(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualFindingsDetails = commonDoctorServiceImpl.getFindingsDetails(1L, 1L);

        // Assert
        verify(benChiefComplaintRepo).getBenChiefComplaints(isA(Long.class), isA(Long.class));
        verify(benClinicalObservationsRepo).getFindingsData(isA(Long.class), isA(Long.class));
        assertEquals("null", actualFindingsDetails);
    }

    @Test
    void testGetInvestigationDetails() {
        // Arrange
        when(labTestOrderDetailRepo.getLabTestOrderDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualInvestigationDetails = commonDoctorServiceImpl.getInvestigationDetails(1L, 1L);

        // Assert
        verify(labTestOrderDetailRepo).getLabTestOrderDetails(isA(Long.class), isA(Long.class));
        assertEquals("{}", actualInvestigationDetails);
    }

    @Test
    void testGetPrescribedDrugs() {
        // Arrange
        when(prescribedDrugDetailRepo.getBenPrescribedDrugDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualPrescribedDrugs = commonDoctorServiceImpl.getPrescribedDrugs(1L, 1L);

        // Assert
        verify(prescribedDrugDetailRepo).getBenPrescribedDrugDetails(isA(Long.class), isA(Long.class));
        assertEquals("[]", actualPrescribedDrugs);
    }

    @Test
    void testGetReferralDetails() {
        // Arrange
        when(benReferDetailsRepo.findByBeneficiaryRegIDAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualReferralDetails = commonDoctorServiceImpl.getReferralDetails(1L, 1L, true);

        // Assert
        verify(benReferDetailsRepo).findByBeneficiaryRegIDAndVisitCode(isA(Long.class), isA(Long.class));
        assertEquals("{}", actualReferralDetails);
    }

    @Test
    void testUpdateDocFindings() {
        // Arrange
        when(benClinicalObservationsRepo.updateBenClinicalObservations(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(1);
        when(benClinicalObservationsRepo.getBenClinicalObservationStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Clinical Observation Status");

        SCTDescription sctDescription = new SCTDescription();
        sctDescription.setActive("Active");
        sctDescription.setCaseSignificanceID("Case Significance ID");
        sctDescription.setConceptID("Concept ID");
        sctDescription.setPageNo(1);
        sctDescription.setSctDesID(1L);
        sctDescription.setTerm("Term");
        when(snomedServiceImpl.findSnomedCTRecordFromTerm(Mockito.<String>any())).thenReturn(sctDescription);

        // Act
        Integer actualUpdateDocFindingsResult = commonDoctorServiceImpl
                .updateDocFindings(new WrapperAncFindings(1L, 1L, 1, "Jan 1, 2020 8:00am GMT+0100", "Clinical Observation",
                        "Other Symptoms", "Significant Findings", new ArrayList<>()));

        // Assert
        verify(benClinicalObservationsRepo).getBenClinicalObservationStatus(isA(Long.class), isNull());
        verify(benClinicalObservationsRepo).updateBenClinicalObservations(isNull(), eq("Other Symptoms"), eq("Concept ID"),
                eq("Term"), isNull(), isNull(), eq("Jan 1, 2020 8:00am GMT+0100"), eq("U"), isA(Long.class), isNull(), isNull(),
                isNull());
        verify(snomedServiceImpl).findSnomedCTRecordFromTerm(eq("Other Symptoms"));
        assertEquals(1, actualUpdateDocFindingsResult.intValue());
    }

    @Test
    void testUpdateDocFindings2() {
        // Arrange
        when(benClinicalObservationsRepo.updateBenClinicalObservations(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(0);
        when(benClinicalObservationsRepo.getBenClinicalObservationStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Clinical Observation Status");

        SCTDescription sctDescription = new SCTDescription();
        sctDescription.setActive("Active");
        sctDescription.setCaseSignificanceID("Case Significance ID");
        sctDescription.setConceptID("Concept ID");
        sctDescription.setPageNo(1);
        sctDescription.setSctDesID(1L);
        sctDescription.setTerm("Term");
        when(snomedServiceImpl.findSnomedCTRecordFromTerm(Mockito.<String>any())).thenReturn(sctDescription);

        // Act
        Integer actualUpdateDocFindingsResult = commonDoctorServiceImpl
                .updateDocFindings(new WrapperAncFindings(1L, 1L, 1, "Jan 1, 2020 8:00am GMT+0100", "Clinical Observation",
                        "Other Symptoms", "Significant Findings", new ArrayList<>()));

        // Assert
        verify(benClinicalObservationsRepo).getBenClinicalObservationStatus(isA(Long.class), isNull());
        verify(benClinicalObservationsRepo).updateBenClinicalObservations(isNull(), eq("Other Symptoms"), eq("Concept ID"),
                eq("Term"), isNull(), isNull(), eq("Jan 1, 2020 8:00am GMT+0100"), eq("U"), isA(Long.class), isNull(), isNull(),
                isNull());
        verify(snomedServiceImpl).findSnomedCTRecordFromTerm(eq("Other Symptoms"));
        assertEquals(0, actualUpdateDocFindingsResult.intValue());
    }

    @Test
    void testUpdateDocFindings3() {
        // Arrange
        when(benClinicalObservationsRepo.updateBenClinicalObservations(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(1);
        when(benClinicalObservationsRepo.getBenClinicalObservationStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("N");

        SCTDescription sctDescription = new SCTDescription();
        sctDescription.setActive("Active");
        sctDescription.setCaseSignificanceID("Case Significance ID");
        sctDescription.setConceptID("Concept ID");
        sctDescription.setPageNo(1);
        sctDescription.setSctDesID(1L);
        sctDescription.setTerm("Term");
        when(snomedServiceImpl.findSnomedCTRecordFromTerm(Mockito.<String>any())).thenReturn(sctDescription);

        // Act
        Integer actualUpdateDocFindingsResult = commonDoctorServiceImpl
                .updateDocFindings(new WrapperAncFindings(1L, 1L, 1, "Jan 1, 2020 8:00am GMT+0100", "Clinical Observation",
                        "Other Symptoms", "Significant Findings", new ArrayList<>()));

        // Assert
        verify(benClinicalObservationsRepo).getBenClinicalObservationStatus(isA(Long.class), isNull());
        verify(benClinicalObservationsRepo).updateBenClinicalObservations(isNull(), eq("Other Symptoms"), eq("Concept ID"),
                eq("Term"), isNull(), isNull(), eq("Jan 1, 2020 8:00am GMT+0100"), eq("N"), isA(Long.class), isNull(), isNull(),
                isNull());
        verify(snomedServiceImpl).findSnomedCTRecordFromTerm(eq("Other Symptoms"));
        assertEquals(1, actualUpdateDocFindingsResult.intValue());
    }

    @Test
    void testUpdateDocFindings4() {
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
        when(benClinicalObservationsRepo.getBenClinicalObservationStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(null);

        SCTDescription sctDescription = new SCTDescription();
        sctDescription.setActive("Active");
        sctDescription.setCaseSignificanceID("Case Significance ID");
        sctDescription.setConceptID("Concept ID");
        sctDescription.setPageNo(1);
        sctDescription.setSctDesID(1L);
        sctDescription.setTerm("Term");
        when(snomedServiceImpl.findSnomedCTRecordFromTerm(Mockito.<String>any())).thenReturn(sctDescription);

        // Act
        Integer actualUpdateDocFindingsResult = commonDoctorServiceImpl
                .updateDocFindings(new WrapperAncFindings(1L, 1L, 1, "Jan 1, 2020 8:00am GMT+0100", "Clinical Observation",
                        "Other Symptoms", "Significant Findings", new ArrayList<>()));

        // Assert
        verify(benClinicalObservationsRepo).getBenClinicalObservationStatus(isA(Long.class), isNull());
        verify(snomedServiceImpl).findSnomedCTRecordFromTerm(eq("Other Symptoms"));
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1, actualUpdateDocFindingsResult.intValue());
    }

    @Test
    void testUpdateDocFindings5() {
        // Arrange
        when(benClinicalObservationsRepo.updateBenClinicalObservations(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(1);
        when(benClinicalObservationsRepo.getBenClinicalObservationStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Clinical Observation Status");
        SCTDescription sctDescription = mock(SCTDescription.class);
        when(sctDescription.getConceptID()).thenReturn("Concept ID");
        when(sctDescription.getTerm()).thenReturn("Term");
        doNothing().when(sctDescription).setActive(Mockito.<String>any());
        doNothing().when(sctDescription).setCaseSignificanceID(Mockito.<String>any());
        doNothing().when(sctDescription).setConceptID(Mockito.<String>any());
        doNothing().when(sctDescription).setPageNo(Mockito.<Integer>any());
        doNothing().when(sctDescription).setSctDesID(Mockito.<Long>any());
        doNothing().when(sctDescription).setTerm(Mockito.<String>any());
        sctDescription.setActive("Active");
        sctDescription.setCaseSignificanceID("Case Significance ID");
        sctDescription.setConceptID("Concept ID");
        sctDescription.setPageNo(1);
        sctDescription.setSctDesID(1L);
        sctDescription.setTerm("Term");
        when(snomedServiceImpl.findSnomedCTRecordFromTerm(Mockito.<String>any())).thenReturn(sctDescription);

        // Act
        Integer actualUpdateDocFindingsResult = commonDoctorServiceImpl
                .updateDocFindings(new WrapperAncFindings(1L, 1L, 1, "Jan 1, 2020 8:00am GMT+0100", "Clinical Observation",
                        "Other Symptoms", "Significant Findings", new ArrayList<>()));

        // Assert
        verify(sctDescription).getConceptID();
        verify(sctDescription).getTerm();
        verify(sctDescription).setActive(eq("Active"));
        verify(sctDescription).setCaseSignificanceID(eq("Case Significance ID"));
        verify(sctDescription).setConceptID(eq("Concept ID"));
        verify(sctDescription).setPageNo(isA(Integer.class));
        verify(sctDescription).setSctDesID(isA(Long.class));
        verify(sctDescription).setTerm(eq("Term"));
        verify(benClinicalObservationsRepo).getBenClinicalObservationStatus(isA(Long.class), isNull());
        verify(benClinicalObservationsRepo).updateBenClinicalObservations(isNull(), eq("Other Symptoms"), eq("Concept ID"),
                eq("Term"), isNull(), isNull(), eq("Jan 1, 2020 8:00am GMT+0100"), eq("U"), isA(Long.class), isNull(), isNull(),
                isNull());
        verify(snomedServiceImpl).findSnomedCTRecordFromTerm(eq("Other Symptoms"));
        assertEquals(1, actualUpdateDocFindingsResult.intValue());
    }

    @Test
    void testUpdateDoctorBenChiefComplaints() {
        // Arrange, Act and Assert
        assertEquals(1, commonDoctorServiceImpl.updateDoctorBenChiefComplaints(new ArrayList<>()));
    }

    @Test
    void testUpdateDoctorBenChiefComplaints2() {
        // Arrange
        when(benChiefComplaintRepo.saveAll(Mockito.<Iterable<BenChiefComplaint>>any())).thenReturn(new ArrayList<>());

        BenChiefComplaint benChiefComplaint = new BenChiefComplaint();
        benChiefComplaint.setBenChiefComplaintID(1L);
        benChiefComplaint.setBenVisitID(1L);
        benChiefComplaint.setBeneficiaryRegID(1L);
        benChiefComplaint.setChiefComplaint("Chief Complaint");
        benChiefComplaint.setChiefComplaintID(1);
        benChiefComplaint.setConceptID("Concept ID");
        benChiefComplaint.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benChiefComplaint.setCreatedDate(mock(Timestamp.class));
        benChiefComplaint.setDeleted(true);
        benChiefComplaint.setDescription("The characteristics of someone or something");
        benChiefComplaint.setDuration(1);
        benChiefComplaint.setLastModDate(mock(Timestamp.class));
        benChiefComplaint.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benChiefComplaint.setParkingPlaceID(1);
        benChiefComplaint.setProcessed("Processed");
        benChiefComplaint.setProviderServiceMapID(1);
        benChiefComplaint.setReservedForChange("Reserved For Change");
        benChiefComplaint.setSyncedBy("Synced By");
        benChiefComplaint.setSyncedDate(mock(Timestamp.class));
        benChiefComplaint.setUnitOfDuration("Unit Of Duration");
        benChiefComplaint.setVanID(1);
        benChiefComplaint.setVanSerialNo(1L);
        benChiefComplaint.setVehicalNo("Vehical No");
        benChiefComplaint.setVisitCode(1L);

        ArrayList<BenChiefComplaint> benChiefComplaintList = new ArrayList<>();
        benChiefComplaintList.add(benChiefComplaint);

        // Act
        int actualUpdateDoctorBenChiefComplaintsResult = commonDoctorServiceImpl
                .updateDoctorBenChiefComplaints(benChiefComplaintList);

        // Assert
        verify(benChiefComplaintRepo).saveAll(isA(Iterable.class));
        assertEquals(0, actualUpdateDoctorBenChiefComplaintsResult);
    }

    @Test
    void testUpdateDoctorBenChiefComplaints3() {
        // Arrange
        BenChiefComplaint benChiefComplaint = new BenChiefComplaint();
        benChiefComplaint.setBenChiefComplaintID(1L);
        benChiefComplaint.setBenVisitID(1L);
        benChiefComplaint.setBeneficiaryRegID(1L);
        benChiefComplaint.setChiefComplaint("Chief Complaint");
        benChiefComplaint.setChiefComplaintID(1);
        benChiefComplaint.setConceptID("Concept ID");
        benChiefComplaint.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benChiefComplaint.setCreatedDate(mock(Timestamp.class));
        benChiefComplaint.setDeleted(true);
        benChiefComplaint.setDescription("The characteristics of someone or something");
        benChiefComplaint.setDuration(1);
        benChiefComplaint.setLastModDate(mock(Timestamp.class));
        benChiefComplaint.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benChiefComplaint.setParkingPlaceID(1);
        benChiefComplaint.setProcessed("Processed");
        benChiefComplaint.setProviderServiceMapID(1);
        benChiefComplaint.setReservedForChange("Reserved For Change");
        benChiefComplaint.setSyncedBy("Synced By");
        benChiefComplaint.setSyncedDate(mock(Timestamp.class));
        benChiefComplaint.setUnitOfDuration("Unit Of Duration");
        benChiefComplaint.setVanID(1);
        benChiefComplaint.setVanSerialNo(1L);
        benChiefComplaint.setVehicalNo("Vehical No");
        benChiefComplaint.setVisitCode(1L);

        ArrayList<BenChiefComplaint> benChiefComplaintList = new ArrayList<>();
        benChiefComplaintList.add(benChiefComplaint);
        when(benChiefComplaintRepo.saveAll(Mockito.<Iterable<BenChiefComplaint>>any())).thenReturn(benChiefComplaintList);

        BenChiefComplaint benChiefComplaint2 = new BenChiefComplaint();
        benChiefComplaint2.setBenChiefComplaintID(1L);
        benChiefComplaint2.setBenVisitID(1L);
        benChiefComplaint2.setBeneficiaryRegID(1L);
        benChiefComplaint2.setChiefComplaint("Chief Complaint");
        benChiefComplaint2.setChiefComplaintID(1);
        benChiefComplaint2.setConceptID("Concept ID");
        benChiefComplaint2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benChiefComplaint2.setCreatedDate(mock(Timestamp.class));
        benChiefComplaint2.setDeleted(true);
        benChiefComplaint2.setDescription("The characteristics of someone or something");
        benChiefComplaint2.setDuration(1);
        benChiefComplaint2.setLastModDate(mock(Timestamp.class));
        benChiefComplaint2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benChiefComplaint2.setParkingPlaceID(1);
        benChiefComplaint2.setProcessed("Processed");
        benChiefComplaint2.setProviderServiceMapID(1);
        benChiefComplaint2.setReservedForChange("Reserved For Change");
        benChiefComplaint2.setSyncedBy("Synced By");
        benChiefComplaint2.setSyncedDate(mock(Timestamp.class));
        benChiefComplaint2.setUnitOfDuration("Unit Of Duration");
        benChiefComplaint2.setVanID(1);
        benChiefComplaint2.setVanSerialNo(1L);
        benChiefComplaint2.setVehicalNo("Vehical No");
        benChiefComplaint2.setVisitCode(1L);

        ArrayList<BenChiefComplaint> benChiefComplaintList2 = new ArrayList<>();
        benChiefComplaintList2.add(benChiefComplaint2);

        // Act
        int actualUpdateDoctorBenChiefComplaintsResult = commonDoctorServiceImpl
                .updateDoctorBenChiefComplaints(benChiefComplaintList2);

        // Assert
        verify(benChiefComplaintRepo).saveAll(isA(Iterable.class));
        assertEquals(1, actualUpdateDoctorBenChiefComplaintsResult);
    }

    @Test
    void testUpdateDoctorBenChiefComplaints4() {
        // Arrange
        when(benChiefComplaintRepo.saveAll(Mockito.<Iterable<BenChiefComplaint>>any())).thenReturn(new ArrayList<>());

        BenChiefComplaint benChiefComplaint = new BenChiefComplaint();
        benChiefComplaint.setBenChiefComplaintID(1L);
        benChiefComplaint.setBenVisitID(1L);
        benChiefComplaint.setBeneficiaryRegID(1L);
        benChiefComplaint.setChiefComplaint("Chief Complaint");
        benChiefComplaint.setChiefComplaintID(1);
        benChiefComplaint.setConceptID("Concept ID");
        benChiefComplaint.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benChiefComplaint.setCreatedDate(mock(Timestamp.class));
        benChiefComplaint.setDeleted(true);
        benChiefComplaint.setDescription("The characteristics of someone or something");
        benChiefComplaint.setDuration(1);
        benChiefComplaint.setLastModDate(mock(Timestamp.class));
        benChiefComplaint.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benChiefComplaint.setParkingPlaceID(1);
        benChiefComplaint.setProcessed("Processed");
        benChiefComplaint.setProviderServiceMapID(1);
        benChiefComplaint.setReservedForChange("Reserved For Change");
        benChiefComplaint.setSyncedBy("Synced By");
        benChiefComplaint.setSyncedDate(mock(Timestamp.class));
        benChiefComplaint.setUnitOfDuration("Unit Of Duration");
        benChiefComplaint.setVanID(1);
        benChiefComplaint.setVanSerialNo(1L);
        benChiefComplaint.setVehicalNo("Vehical No");
        benChiefComplaint.setVisitCode(1L);

        BenChiefComplaint benChiefComplaint2 = new BenChiefComplaint();
        benChiefComplaint2.setBenChiefComplaintID(2L);
        benChiefComplaint2.setBenVisitID(2L);
        benChiefComplaint2.setBeneficiaryRegID(2L);
        benChiefComplaint2.setChiefComplaint("benVisitID");
        benChiefComplaint2.setChiefComplaintID(2);
        benChiefComplaint2.setConceptID("benVisitID");
        benChiefComplaint2.setCreatedBy("Created By");
        benChiefComplaint2.setCreatedDate(mock(Timestamp.class));
        benChiefComplaint2.setDeleted(false);
        benChiefComplaint2.setDescription("Description");
        benChiefComplaint2.setDuration(0);
        benChiefComplaint2.setLastModDate(mock(Timestamp.class));
        benChiefComplaint2.setModifiedBy("Modified By");
        benChiefComplaint2.setParkingPlaceID(2);
        benChiefComplaint2.setProcessed("benVisitID");
        benChiefComplaint2.setProviderServiceMapID(2);
        benChiefComplaint2.setReservedForChange("benVisitID");
        benChiefComplaint2.setSyncedBy("benVisitID");
        benChiefComplaint2.setSyncedDate(mock(Timestamp.class));
        benChiefComplaint2.setUnitOfDuration("benVisitID");
        benChiefComplaint2.setVanID(2);
        benChiefComplaint2.setVanSerialNo(0L);
        benChiefComplaint2.setVehicalNo("benVisitID");
        benChiefComplaint2.setVisitCode(0L);

        ArrayList<BenChiefComplaint> benChiefComplaintList = new ArrayList<>();
        benChiefComplaintList.add(benChiefComplaint2);
        benChiefComplaintList.add(benChiefComplaint);

        // Act
        int actualUpdateDoctorBenChiefComplaintsResult = commonDoctorServiceImpl
                .updateDoctorBenChiefComplaints(benChiefComplaintList);

        // Assert
        verify(benChiefComplaintRepo).saveAll(isA(Iterable.class));
        assertEquals(0, actualUpdateDoctorBenChiefComplaintsResult);
    }

    @Test
    void testUpdateBenClinicalObservations() {
        // Arrange
        when(benClinicalObservationsRepo.updateBenClinicalObservations(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(1);
        when(benClinicalObservationsRepo.getBenClinicalObservationStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Clinical Observation Status");

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

        // Act
        int actualUpdateBenClinicalObservationsResult = commonDoctorServiceImpl
                .updateBenClinicalObservations(benClinicalObservations);

        // Assert
        verify(benClinicalObservationsRepo).getBenClinicalObservationStatus(isA(Long.class), isA(Long.class));
        verify(benClinicalObservationsRepo).updateBenClinicalObservations(eq("Clinical Observation"), eq("Other Symptoms"),
                eq("Other Symptoms SCTCode"), eq("Other Symptoms SCTTerm"), eq("Significant Findings"), isA(Boolean.class),
                eq("Jan 1, 2020 8:00am GMT+0100"), eq("U"), isA(Long.class), isA(Long.class),
                eq("Clinical Observation Sctcode"), eq("Significant Findings Sctcode"));
        assertEquals(1, actualUpdateBenClinicalObservationsResult);
    }

    @Test
    void testUpdateBenClinicalObservations2() {
        // Arrange
        when(benClinicalObservationsRepo.updateBenClinicalObservations(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(1);
        when(benClinicalObservationsRepo.getBenClinicalObservationStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("N");

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

        // Act
        int actualUpdateBenClinicalObservationsResult = commonDoctorServiceImpl
                .updateBenClinicalObservations(benClinicalObservations);

        // Assert
        verify(benClinicalObservationsRepo).getBenClinicalObservationStatus(isA(Long.class), isA(Long.class));
        verify(benClinicalObservationsRepo).updateBenClinicalObservations(eq("Clinical Observation"), eq("Other Symptoms"),
                eq("Other Symptoms SCTCode"), eq("Other Symptoms SCTTerm"), eq("Significant Findings"), isA(Boolean.class),
                eq("Jan 1, 2020 8:00am GMT+0100"), eq("N"), isA(Long.class), isA(Long.class),
                eq("Clinical Observation Sctcode"), eq("Significant Findings Sctcode"));
        assertEquals(1, actualUpdateBenClinicalObservationsResult);
    }

    @Test
    void testUpdateBenClinicalObservations3() {
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
        when(benClinicalObservationsRepo.getBenClinicalObservationStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(null);

        BenClinicalObservations benClinicalObservations2 = new BenClinicalObservations();
        benClinicalObservations2.setBenVisitID(1L);
        benClinicalObservations2.setBeneficiaryRegID(1L);
        benClinicalObservations2.setCaptureDate(mock(Date.class));
        benClinicalObservations2.setClinicalObservation("Clinical Observation");
        benClinicalObservations2.setClinicalObservationID(1L);
        benClinicalObservations2.setClinicalObservationSctcode("Clinical Observation Sctcode");
        benClinicalObservations2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benClinicalObservations2.setCreatedDate(mock(Timestamp.class));
        benClinicalObservations2.setDeleted(true);
        benClinicalObservations2.setIsForHistory(true);
        benClinicalObservations2.setLastModDate(mock(Timestamp.class));
        benClinicalObservations2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benClinicalObservations2.setOtherSymptoms("Other Symptoms");
        benClinicalObservations2.setOtherSymptomsSCTCode("Other Symptoms SCTCode");
        benClinicalObservations2.setOtherSymptomsSCTTerm("Other Symptoms SCTTerm");
        benClinicalObservations2.setParkingPlaceID(1);
        benClinicalObservations2.setProcessed("Processed");
        benClinicalObservations2.setProviderServiceMapID(1);
        benClinicalObservations2.setReservedForChange("Reserved For Change");
        benClinicalObservations2.setSignificantFindings("Significant Findings");
        benClinicalObservations2.setSignificantFindingsSctcode("Significant Findings Sctcode");
        benClinicalObservations2.setSyncedBy("Synced By");
        benClinicalObservations2.setSyncedDate(mock(Timestamp.class));
        benClinicalObservations2.setVanID(1);
        benClinicalObservations2.setVanSerialNo(1L);
        benClinicalObservations2.setVehicalNo("Vehical No");
        benClinicalObservations2.setVisitCode(1L);

        // Act
        int actualUpdateBenClinicalObservationsResult = commonDoctorServiceImpl
                .updateBenClinicalObservations(benClinicalObservations2);

        // Assert
        verify(benClinicalObservationsRepo).getBenClinicalObservationStatus(isA(Long.class), isA(Long.class));
        verify(benClinicalObservationsRepo).save(isA(BenClinicalObservations.class));
        assertEquals(1, actualUpdateBenClinicalObservationsResult);
    }

    @Test
    void testUpdateBenReferDetails() throws IEMRException {
        // Arrange
        BenReferDetails benReferDetails = new BenReferDetails();
        benReferDetails.setBenReferID(1L);
        benReferDetails.setBenVisitID(1L);
        benReferDetails.setBeneficiaryRegID(1L);
        benReferDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benReferDetails.setCreatedDate(mock(Timestamp.class));
        benReferDetails.setDeleted(true);
        benReferDetails.setLastModDate(mock(Timestamp.class));
        benReferDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benReferDetails.setOtherReferralReason("Just cause");
        benReferDetails.setOtherReferredToInstituteName("Other Referred To Institute Name");
        benReferDetails.setParkingPlaceID(1);
        benReferDetails.setProcessed("Processed");
        benReferDetails.setProviderServiceMapID(1);
        benReferDetails.setReferralReason("Just cause");
        benReferDetails.setReferralReasonList(new String[]{"Just cause"});
        benReferDetails.setReferredToInstituteID(1);
        benReferDetails.setReferredToInstituteName("Referred To Institute Name");
        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"Refrred To Additional Service List"});
        benReferDetails.setReservedForChange("Reserved For Change");
        benReferDetails.setRevisitDate(mock(Timestamp.class));
        benReferDetails.setServiceID((short) 1);
        benReferDetails.setServiceName("Service Name");
        benReferDetails.setSyncedBy("Synced By");
        benReferDetails.setSyncedDate(mock(Timestamp.class));
        benReferDetails.setVanID(1);
        benReferDetails.setVanSerialNo(1L);
        benReferDetails.setVehicalNo("Vehical No");
        benReferDetails.setVisitCode(1L);
        when(benReferDetailsRepo.save(Mockito.<BenReferDetails>any())).thenReturn(benReferDetails);

        // Act
        Long actualUpdateBenReferDetailsResult = commonDoctorServiceImpl.updateBenReferDetails(new JsonObject(), true);

        // Assert
        verify(benReferDetailsRepo).save(isA(BenReferDetails.class));
        assertEquals(1L, actualUpdateBenReferDetailsResult.longValue());
    }

    @Test
    void testUpdateBenReferDetails2() throws IEMRException {
        // Arrange, Act and Assert
        assertEquals(1L, commonDoctorServiceImpl.updateBenReferDetails(null, true).longValue());
    }

    @Test
    void testUpdateBenReferDetails3() throws IEMRException {
        // Arrange
        BenReferDetails benReferDetails = new BenReferDetails();
        benReferDetails.setBenReferID(1L);
        benReferDetails.setBenVisitID(1L);
        benReferDetails.setBeneficiaryRegID(1L);
        benReferDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benReferDetails.setCreatedDate(mock(Timestamp.class));
        benReferDetails.setDeleted(true);
        benReferDetails.setLastModDate(mock(Timestamp.class));
        benReferDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benReferDetails.setOtherReferralReason("Just cause");
        benReferDetails.setOtherReferredToInstituteName("Other Referred To Institute Name");
        benReferDetails.setParkingPlaceID(1);
        benReferDetails.setProcessed("Processed");
        benReferDetails.setProviderServiceMapID(1);
        benReferDetails.setReferralReason("Just cause");
        benReferDetails.setReferralReasonList(new String[]{"Just cause"});
        benReferDetails.setReferredToInstituteID(1);
        benReferDetails.setReferredToInstituteName("Referred To Institute Name");
        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"Refrred To Additional Service List"});
        benReferDetails.setReservedForChange("Reserved For Change");
        benReferDetails.setRevisitDate(mock(Timestamp.class));
        benReferDetails.setServiceID((short) 1);
        benReferDetails.setServiceName("Service Name");
        benReferDetails.setSyncedBy("Synced By");
        benReferDetails.setSyncedDate(mock(Timestamp.class));
        benReferDetails.setVanID(1);
        benReferDetails.setVanSerialNo(1L);
        benReferDetails.setVehicalNo("Vehical No");
        benReferDetails.setVisitCode(1L);
        when(benReferDetailsRepo.save(Mockito.<BenReferDetails>any())).thenReturn(benReferDetails);

        JsonObject referObj = new JsonObject();
        referObj.add("Property", new JsonArray(3));

        // Act
        Long actualUpdateBenReferDetailsResult = commonDoctorServiceImpl.updateBenReferDetails(referObj, true);

        // Assert
        verify(benReferDetailsRepo).save(isA(BenReferDetails.class));
        assertEquals(1L, actualUpdateBenReferDetailsResult.longValue());
    }

    @Test
    void testUpdateBenReferDetails4() throws IEMRException {
        // Arrange
        BenReferDetails benReferDetails = new BenReferDetails();
        benReferDetails.setBenReferID(1L);
        benReferDetails.setBenVisitID(1L);
        benReferDetails.setBeneficiaryRegID(1L);
        benReferDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benReferDetails.setCreatedDate(mock(Timestamp.class));
        benReferDetails.setDeleted(true);
        benReferDetails.setLastModDate(mock(Timestamp.class));
        benReferDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benReferDetails.setOtherReferralReason("Just cause");
        benReferDetails.setOtherReferredToInstituteName("Other Referred To Institute Name");
        benReferDetails.setParkingPlaceID(1);
        benReferDetails.setProcessed("Processed");
        benReferDetails.setProviderServiceMapID(1);
        benReferDetails.setReferralReason("Just cause");
        benReferDetails.setReferralReasonList(new String[]{"Just cause"});
        benReferDetails.setReferredToInstituteID(1);
        benReferDetails.setReferredToInstituteName("Referred To Institute Name");
        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"Refrred To Additional Service List"});
        benReferDetails.setReservedForChange("Reserved For Change");
        benReferDetails.setRevisitDate(mock(Timestamp.class));
        benReferDetails.setServiceID((short) 1);
        benReferDetails.setServiceName("Service Name");
        benReferDetails.setSyncedBy("Synced By");
        benReferDetails.setSyncedDate(mock(Timestamp.class));
        benReferDetails.setVanID(1);
        benReferDetails.setVanSerialNo(1L);
        benReferDetails.setVehicalNo("Vehical No");
        benReferDetails.setVisitCode(1L);
        when(benReferDetailsRepo.save(Mockito.<BenReferDetails>any())).thenReturn(benReferDetails);

        JsonObject referObj = new JsonObject();
        referObj.addProperty("Property", "42");

        // Act
        Long actualUpdateBenReferDetailsResult = commonDoctorServiceImpl.updateBenReferDetails(referObj, true);

        // Assert
        verify(benReferDetailsRepo).save(isA(BenReferDetails.class));
        assertEquals(1L, actualUpdateBenReferDetailsResult.longValue());
    }

    @Test
    void testUpdateBenReferDetails5() throws IEMRException {
        // Arrange
        BenReferDetails benReferDetails = new BenReferDetails();
        benReferDetails.setBenReferID(1L);
        benReferDetails.setBenVisitID(1L);
        benReferDetails.setBeneficiaryRegID(1L);
        benReferDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benReferDetails.setCreatedDate(mock(Timestamp.class));
        benReferDetails.setDeleted(true);
        benReferDetails.setLastModDate(mock(Timestamp.class));
        benReferDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benReferDetails.setOtherReferralReason("Just cause");
        benReferDetails.setOtherReferredToInstituteName("Other Referred To Institute Name");
        benReferDetails.setParkingPlaceID(1);
        benReferDetails.setProcessed("Processed");
        benReferDetails.setProviderServiceMapID(1);
        benReferDetails.setReferralReason("Just cause");
        benReferDetails.setReferralReasonList(new String[]{"Just cause"});
        benReferDetails.setReferredToInstituteID(1);
        benReferDetails.setReferredToInstituteName("Referred To Institute Name");
        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"Refrred To Additional Service List"});
        benReferDetails.setReservedForChange("Reserved For Change");
        benReferDetails.setRevisitDate(mock(Timestamp.class));
        benReferDetails.setServiceID((short) 1);
        benReferDetails.setServiceName("Service Name");
        benReferDetails.setSyncedBy("Synced By");
        benReferDetails.setSyncedDate(mock(Timestamp.class));
        benReferDetails.setVanID(1);
        benReferDetails.setVanSerialNo(1L);
        benReferDetails.setVehicalNo("Vehical No");
        benReferDetails.setVisitCode(1L);
        when(benReferDetailsRepo.save(Mockito.<BenReferDetails>any())).thenReturn(benReferDetails);

        JsonObject referObj = new JsonObject();
        referObj.addProperty("Property", Integer.valueOf(1));

        // Act
        Long actualUpdateBenReferDetailsResult = commonDoctorServiceImpl.updateBenReferDetails(referObj, true);

        // Assert
        verify(benReferDetailsRepo).save(isA(BenReferDetails.class));
        assertEquals(1L, actualUpdateBenReferDetailsResult.longValue());
    }

    @Test
    void testUpdateBenReferDetails6() throws IEMRException {
        // Arrange
        BenReferDetails benReferDetails = new BenReferDetails();
        benReferDetails.setBenReferID(1L);
        benReferDetails.setBenVisitID(1L);
        benReferDetails.setBeneficiaryRegID(1L);
        benReferDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benReferDetails.setCreatedDate(mock(Timestamp.class));
        benReferDetails.setDeleted(true);
        benReferDetails.setLastModDate(mock(Timestamp.class));
        benReferDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benReferDetails.setOtherReferralReason("Just cause");
        benReferDetails.setOtherReferredToInstituteName("Other Referred To Institute Name");
        benReferDetails.setParkingPlaceID(1);
        benReferDetails.setProcessed("Processed");
        benReferDetails.setProviderServiceMapID(1);
        benReferDetails.setReferralReason("Just cause");
        benReferDetails.setReferralReasonList(new String[]{"Just cause"});
        benReferDetails.setReferredToInstituteID(1);
        benReferDetails.setReferredToInstituteName("Referred To Institute Name");
        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"Refrred To Additional Service List"});
        benReferDetails.setReservedForChange("Reserved For Change");
        benReferDetails.setRevisitDate(mock(Timestamp.class));
        benReferDetails.setServiceID((short) 1);
        benReferDetails.setServiceName("Service Name");
        benReferDetails.setSyncedBy("Synced By");
        benReferDetails.setSyncedDate(mock(Timestamp.class));
        benReferDetails.setVanID(1);
        benReferDetails.setVanSerialNo(1L);
        benReferDetails.setVehicalNo("Vehical No");
        benReferDetails.setVisitCode(1L);
        when(benReferDetailsRepo.save(Mockito.<BenReferDetails>any())).thenReturn(benReferDetails);

        JsonObject referObj = new JsonObject();
        referObj.addProperty("Property", true);

        // Act
        Long actualUpdateBenReferDetailsResult = commonDoctorServiceImpl.updateBenReferDetails(referObj, true);

        // Assert
        verify(benReferDetailsRepo).save(isA(BenReferDetails.class));
        assertEquals(1L, actualUpdateBenReferDetailsResult.longValue());
    }

    @Test
    void testUpdateBenReferDetails7() throws IEMRException {
        // Arrange
        BenReferDetails benReferDetails = new BenReferDetails();
        benReferDetails.setBenReferID(1L);
        benReferDetails.setBenVisitID(1L);
        benReferDetails.setBeneficiaryRegID(1L);
        benReferDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benReferDetails.setCreatedDate(mock(Timestamp.class));
        benReferDetails.setDeleted(true);
        benReferDetails.setLastModDate(mock(Timestamp.class));
        benReferDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benReferDetails.setOtherReferralReason("Just cause");
        benReferDetails.setOtherReferredToInstituteName("Other Referred To Institute Name");
        benReferDetails.setParkingPlaceID(1);
        benReferDetails.setProcessed("Processed");
        benReferDetails.setProviderServiceMapID(1);
        benReferDetails.setReferralReason("Just cause");
        benReferDetails.setReferralReasonList(new String[]{"Just cause"});
        benReferDetails.setReferredToInstituteID(1);
        benReferDetails.setReferredToInstituteName("Referred To Institute Name");
        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"Refrred To Additional Service List"});
        benReferDetails.setReservedForChange("Reserved For Change");
        benReferDetails.setRevisitDate(mock(Timestamp.class));
        benReferDetails.setServiceID((short) 1);
        benReferDetails.setServiceName("Service Name");
        benReferDetails.setSyncedBy("Synced By");
        benReferDetails.setSyncedDate(mock(Timestamp.class));
        benReferDetails.setVanID(1);
        benReferDetails.setVanSerialNo(1L);
        benReferDetails.setVehicalNo("Vehical No");
        benReferDetails.setVisitCode(1L);
        when(benReferDetailsRepo.save(Mockito.<BenReferDetails>any())).thenReturn(benReferDetails);

        // Act
        Long actualUpdateBenReferDetailsResult = commonDoctorServiceImpl.updateBenReferDetails(new JsonObject(), false);

        // Assert
        verify(benReferDetailsRepo).save(isA(BenReferDetails.class));
        assertEquals(1L, actualUpdateBenReferDetailsResult.longValue());
    }

    @Test
    void testUpdateBenReferDetails8() throws IEMRException {
        // Arrange
        BenReferDetails benReferDetails = new BenReferDetails();
        benReferDetails.setBenReferID(1L);
        benReferDetails.setBenVisitID(1L);
        benReferDetails.setBeneficiaryRegID(1L);
        benReferDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benReferDetails.setCreatedDate(mock(Timestamp.class));
        benReferDetails.setDeleted(true);
        benReferDetails.setLastModDate(mock(Timestamp.class));
        benReferDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benReferDetails.setOtherReferralReason("Just cause");
        benReferDetails.setOtherReferredToInstituteName("Other Referred To Institute Name");
        benReferDetails.setParkingPlaceID(1);
        benReferDetails.setProcessed("Processed");
        benReferDetails.setProviderServiceMapID(1);
        benReferDetails.setReferralReason("Just cause");
        benReferDetails.setReferralReasonList(new String[]{"Just cause"});
        benReferDetails.setReferredToInstituteID(1);
        benReferDetails.setReferredToInstituteName("Referred To Institute Name");
        benReferDetails.setRefrredToAdditionalServiceList(new String[]{"Refrred To Additional Service List"});
        benReferDetails.setReservedForChange("Reserved For Change");
        benReferDetails.setRevisitDate(mock(Timestamp.class));
        benReferDetails.setServiceID((short) 1);
        benReferDetails.setServiceName("Service Name");
        benReferDetails.setSyncedBy("Synced By");
        benReferDetails.setSyncedDate(mock(Timestamp.class));
        benReferDetails.setVanID(1);
        benReferDetails.setVanSerialNo(1L);
        benReferDetails.setVehicalNo("Vehical No");
        benReferDetails.setVisitCode(1L);
        when(benReferDetailsRepo.save(Mockito.<BenReferDetails>any())).thenReturn(benReferDetails);

        // Act
        Long actualUpdateBenReferDetailsResult = commonDoctorServiceImpl.updateBenReferDetails(new JsonObject(), null);

        // Assert
        verify(benReferDetailsRepo).save(isA(BenReferDetails.class));
        assertEquals(1L, actualUpdateBenReferDetailsResult.longValue());
    }

    @Test
    void testUpdateBenFlowtableAfterDocDataSave() throws IEMRException {
        // Arrange
        TeleconsultationStats teleconsultationStats = new TeleconsultationStats();
        teleconsultationStats.setBenVisitID(1L);
        teleconsultationStats.setBeneficiaryRegID(1L);
        teleconsultationStats.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        teleconsultationStats.setCreatedDate(mock(Timestamp.class));
        teleconsultationStats.setDeleted(true);
        teleconsultationStats.setEndTime(mock(Timestamp.class));
        teleconsultationStats.setLastModDate(mock(Timestamp.class));
        teleconsultationStats.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        teleconsultationStats.setProcessed("Processed");
        teleconsultationStats.setProviderServiceMapID(1);
        teleconsultationStats.setStartTime(mock(Timestamp.class));
        teleconsultationStats.setVanID(1);
        teleconsultationStats.setVisitCode(1L);
        teleconsultationStats.settMStatsID(1L);

        TeleconsultationStats teleconsultationStats2 = new TeleconsultationStats();
        teleconsultationStats2.setBenVisitID(1L);
        teleconsultationStats2.setBeneficiaryRegID(1L);
        teleconsultationStats2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        teleconsultationStats2.setCreatedDate(mock(Timestamp.class));
        teleconsultationStats2.setDeleted(true);
        teleconsultationStats2.setEndTime(mock(Timestamp.class));
        teleconsultationStats2.setLastModDate(mock(Timestamp.class));
        teleconsultationStats2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        teleconsultationStats2.setProcessed("Processed");
        teleconsultationStats2.setProviderServiceMapID(1);
        teleconsultationStats2.setStartTime(mock(Timestamp.class));
        teleconsultationStats2.setVanID(1);
        teleconsultationStats2.setVisitCode(1L);
        teleconsultationStats2.settMStatsID(1L);
        when(teleconsultationStatsRepo.save(Mockito.<TeleconsultationStats>any())).thenReturn(teleconsultationStats2);
        when(teleconsultationStatsRepo.getLatestStartTime(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(teleconsultationStats);
        when(
                commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataFromSpecialist(Mockito.<Long>any(), Mockito.<Long>any(),
                        Mockito.<Long>any(), Mockito.<Long>any(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort()))
                .thenReturn(1);

        CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
        commonUtilityClass.setAge(1);
        commonUtilityClass.setAgeUnits("Age Units");
        commonUtilityClass.setAuthorization("JaneDoe");
        commonUtilityClass.setBenFlowID(1L);
        commonUtilityClass.setBenRegID(1L);
        commonUtilityClass.setBenVisitID(1L);
        commonUtilityClass.setBeneficiaryID(1L);
        commonUtilityClass.setBeneficiaryRegID(1L);
        commonUtilityClass.setBeneficiaryRegId(1L);
        commonUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        commonUtilityClass.setDistrictID(1);
        commonUtilityClass.setDistrictName("District Name");
        commonUtilityClass.setFacilityID(1);
        commonUtilityClass.setFirstName("Jane");
        commonUtilityClass.setGenderID((short) 1);
        commonUtilityClass.setGenderName("Gender Name");
        commonUtilityClass.setIsCovidFlowDone(true);
        commonUtilityClass.setIsMobile(true);
        commonUtilityClass.setIsSpecialist(true);
        commonUtilityClass.setLastName("Doe");
        commonUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        commonUtilityClass.setParkingPlaceID(1);
        commonUtilityClass.setParkingPlaceId(1);
        commonUtilityClass.setPrescriptionID(1L);
        commonUtilityClass.setProviderServiceMapID(1);
        commonUtilityClass.setServiceID((short) 1);
        commonUtilityClass.setSessionID(1);
        commonUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
        commonUtilityClass.setSubVisitCategory("Sub Visit Category");
        commonUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
        commonUtilityClass.setVanID(1);
        commonUtilityClass.setVanId(1);
        commonUtilityClass.setVillageId(1);
        commonUtilityClass.setVillageName("Village Name");
        commonUtilityClass.setVisitCategoryID(1);
        commonUtilityClass.setVisitCode(1L);

        TeleconsultationRequestOBJ tcRequestOBJ = new TeleconsultationRequestOBJ();
        tcRequestOBJ.setAllocationDate(mock(Timestamp.class));
        tcRequestOBJ.setFromTime("jane.doe@example.org");
        tcRequestOBJ.setSpecializationID(1);
        tcRequestOBJ.setTmRequestID(1L);
        tcRequestOBJ.setToTime("To Time");
        tcRequestOBJ.setUserID(1);
        tcRequestOBJ.setWalkIn(true);

        // Act
        int actualUpdateBenFlowtableAfterDocDataSaveResult = commonDoctorServiceImpl
                .updateBenFlowtableAfterDocDataSave(commonUtilityClass, true, true, tcRequestOBJ);

        // Assert
        verify(teleconsultationStatsRepo).getLatestStartTime(isA(Long.class), isA(Long.class));
        verify(commonBenStatusFlowServiceImpl).updateBenFlowAfterDocDataFromSpecialist(isA(Long.class), isA(Long.class),
                isA(Long.class), isA(Long.class), eq((short) 1), eq((short) 1), eq((short) 0), eq((short) 1), eq((short) 0));
        verify(teleconsultationStatsRepo).save(isA(TeleconsultationStats.class));
        assertEquals(1, actualUpdateBenFlowtableAfterDocDataSaveResult);
    }

    @Test
    void testUpdateBenFlowtableAfterDocDataUpdate() throws Exception {
        // Arrange
        TeleconsultationStats teleconsultationStats = new TeleconsultationStats();
        teleconsultationStats.setBenVisitID(1L);
        teleconsultationStats.setBeneficiaryRegID(1L);
        teleconsultationStats.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        teleconsultationStats.setCreatedDate(mock(Timestamp.class));
        teleconsultationStats.setDeleted(true);
        teleconsultationStats.setEndTime(mock(Timestamp.class));
        teleconsultationStats.setLastModDate(mock(Timestamp.class));
        teleconsultationStats.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        teleconsultationStats.setProcessed("Processed");
        teleconsultationStats.setProviderServiceMapID(1);
        teleconsultationStats.setStartTime(mock(Timestamp.class));
        teleconsultationStats.setVanID(1);
        teleconsultationStats.setVisitCode(1L);
        teleconsultationStats.settMStatsID(1L);

        TeleconsultationStats teleconsultationStats2 = new TeleconsultationStats();
        teleconsultationStats2.setBenVisitID(1L);
        teleconsultationStats2.setBeneficiaryRegID(1L);
        teleconsultationStats2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        teleconsultationStats2.setCreatedDate(mock(Timestamp.class));
        teleconsultationStats2.setDeleted(true);
        teleconsultationStats2.setEndTime(mock(Timestamp.class));
        teleconsultationStats2.setLastModDate(mock(Timestamp.class));
        teleconsultationStats2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        teleconsultationStats2.setProcessed("Processed");
        teleconsultationStats2.setProviderServiceMapID(1);
        teleconsultationStats2.setStartTime(mock(Timestamp.class));
        teleconsultationStats2.setVanID(1);
        teleconsultationStats2.setVisitCode(1L);
        teleconsultationStats2.settMStatsID(1L);
        when(teleconsultationStatsRepo.save(Mockito.<TeleconsultationStats>any())).thenReturn(teleconsultationStats2);
        when(teleconsultationStatsRepo.getLatestStartTime(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(teleconsultationStats);
        when(commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataUpdateTCSpecialist(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Long>any(), anyShort(), anyShort(), anyShort(), anyShort(),
                anyInt(), Mockito.<Timestamp>any(), anyShort())).thenReturn(1);

        CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
        commonUtilityClass.setAge(1);
        commonUtilityClass.setAgeUnits("Age Units");
        commonUtilityClass.setAuthorization("JaneDoe");
        commonUtilityClass.setBenFlowID(1L);
        commonUtilityClass.setBenRegID(1L);
        commonUtilityClass.setBenVisitID(1L);
        commonUtilityClass.setBeneficiaryID(1L);
        commonUtilityClass.setBeneficiaryRegID(1L);
        commonUtilityClass.setBeneficiaryRegId(1L);
        commonUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        commonUtilityClass.setDistrictID(1);
        commonUtilityClass.setDistrictName("District Name");
        commonUtilityClass.setFacilityID(1);
        commonUtilityClass.setFirstName("Jane");
        commonUtilityClass.setGenderID((short) 1);
        commonUtilityClass.setGenderName("Gender Name");
        commonUtilityClass.setIsCovidFlowDone(true);
        commonUtilityClass.setIsMobile(true);
        commonUtilityClass.setIsSpecialist(true);
        commonUtilityClass.setLastName("Doe");
        commonUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        commonUtilityClass.setParkingPlaceID(1);
        commonUtilityClass.setParkingPlaceId(1);
        commonUtilityClass.setPrescriptionID(1L);
        commonUtilityClass.setProviderServiceMapID(1);
        commonUtilityClass.setServiceID((short) 1);
        commonUtilityClass.setSessionID(1);
        commonUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
        commonUtilityClass.setSubVisitCategory("Sub Visit Category");
        commonUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
        commonUtilityClass.setVanID(1);
        commonUtilityClass.setVanId(1);
        commonUtilityClass.setVillageId(1);
        commonUtilityClass.setVillageName("Village Name");
        commonUtilityClass.setVisitCategoryID(1);
        commonUtilityClass.setVisitCode(1L);

        TeleconsultationRequestOBJ tcRequestOBJ = new TeleconsultationRequestOBJ();
        tcRequestOBJ.setAllocationDate(mock(Timestamp.class));
        tcRequestOBJ.setFromTime("jane.doe@example.org");
        tcRequestOBJ.setSpecializationID(1);
        tcRequestOBJ.setTmRequestID(1L);
        tcRequestOBJ.setToTime("To Time");
        tcRequestOBJ.setUserID(1);
        tcRequestOBJ.setWalkIn(true);

        // Act
        int actualUpdateBenFlowtableAfterDocDataUpdateResult = commonDoctorServiceImpl
                .updateBenFlowtableAfterDocDataUpdate(commonUtilityClass, true, true, tcRequestOBJ);

        // Assert
        verify(teleconsultationStatsRepo).getLatestStartTime(isA(Long.class), isA(Long.class));
        verify(commonBenStatusFlowServiceImpl).updateBenFlowAfterDocDataUpdateTCSpecialist(isA(Long.class), isA(Long.class),
                isA(Long.class), isA(Long.class), eq((short) 0), eq((short) 1), eq((short) 0), eq((short) 2), eq(0), isNull(),
                eq((short) 0));
        verify(teleconsultationStatsRepo).save(isA(TeleconsultationStats.class));
        assertEquals(1, actualUpdateBenFlowtableAfterDocDataUpdateResult);
    }

    @Test
    void testUpdateBenFlowtableAfterDocDataUpdate2() throws Exception {
        // Arrange
        when(commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataUpdateTCSpecialist(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Long>any(), anyShort(), anyShort(), anyShort(), anyShort(),
                anyInt(), Mockito.<Timestamp>any(), anyShort())).thenThrow(new Exception("foo"));

        CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
        commonUtilityClass.setAge(1);
        commonUtilityClass.setAgeUnits("Age Units");
        commonUtilityClass.setAuthorization("JaneDoe");
        commonUtilityClass.setBenFlowID(1L);
        commonUtilityClass.setBenRegID(1L);
        commonUtilityClass.setBenVisitID(1L);
        commonUtilityClass.setBeneficiaryID(1L);
        commonUtilityClass.setBeneficiaryRegID(1L);
        commonUtilityClass.setBeneficiaryRegId(1L);
        commonUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        commonUtilityClass.setDistrictID(1);
        commonUtilityClass.setDistrictName("District Name");
        commonUtilityClass.setFacilityID(1);
        commonUtilityClass.setFirstName("Jane");
        commonUtilityClass.setGenderID((short) 1);
        commonUtilityClass.setGenderName("Gender Name");
        commonUtilityClass.setIsCovidFlowDone(true);
        commonUtilityClass.setIsMobile(true);
        commonUtilityClass.setIsSpecialist(true);
        commonUtilityClass.setLastName("Doe");
        commonUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        commonUtilityClass.setParkingPlaceID(1);
        commonUtilityClass.setParkingPlaceId(1);
        commonUtilityClass.setPrescriptionID(1L);
        commonUtilityClass.setProviderServiceMapID(1);
        commonUtilityClass.setServiceID((short) 1);
        commonUtilityClass.setSessionID(1);
        commonUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
        commonUtilityClass.setSubVisitCategory("Sub Visit Category");
        commonUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
        commonUtilityClass.setVanID(1);
        commonUtilityClass.setVanId(1);
        commonUtilityClass.setVillageId(1);
        commonUtilityClass.setVillageName("Village Name");
        commonUtilityClass.setVisitCategoryID(1);
        commonUtilityClass.setVisitCode(1L);

        TeleconsultationRequestOBJ tcRequestOBJ = new TeleconsultationRequestOBJ();
        tcRequestOBJ.setAllocationDate(mock(Timestamp.class));
        tcRequestOBJ.setFromTime("jane.doe@example.org");
        tcRequestOBJ.setSpecializationID(1);
        tcRequestOBJ.setTmRequestID(1L);
        tcRequestOBJ.setToTime("To Time");
        tcRequestOBJ.setUserID(1);
        tcRequestOBJ.setWalkIn(true);

        // Act and Assert
        assertThrows(Exception.class, () -> commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(commonUtilityClass,
                true, true, tcRequestOBJ));
        verify(commonBenStatusFlowServiceImpl).updateBenFlowAfterDocDataUpdateTCSpecialist(isA(Long.class), isA(Long.class),
                isA(Long.class), isA(Long.class), eq((short) 0), eq((short) 1), eq((short) 0), eq((short) 2), eq(0), isNull(),
                eq((short) 0));
    }

    @Test
    void testDeletePrescribedMedicine() {
        // Arrange, Act and Assert
        assertNull(commonDoctorServiceImpl.deletePrescribedMedicine(new JSONObject()));
        assertNull(commonDoctorServiceImpl.deletePrescribedMedicine(null));
    }

    @Test
    void testDeletePrescribedMedicine2() throws JSONException {
        // Arrange
        when(prescribedDrugDetailRepo.deletePrescribedmedicine(Mockito.<Long>any())).thenReturn(1);
        JSONObject obj = mock(JSONObject.class);
        when(obj.getLong(Mockito.<String>any())).thenReturn(1L);
        when(obj.has(Mockito.<String>any())).thenReturn(true);

        // Act
        String actualDeletePrescribedMedicineResult = commonDoctorServiceImpl.deletePrescribedMedicine(obj);

        // Assert
        verify(prescribedDrugDetailRepo).deletePrescribedmedicine(isA(Long.class));
        verify(obj).getLong(eq("id"));
        verify(obj).has(eq("id"));
        assertEquals("record deleted successfully", actualDeletePrescribedMedicineResult);
    }

    @Test
    void testCallTmForSpecialistSlotBook() {
        TcSpecialistSlotBookingRequestOBJ tcSpecialistSlotBookingRequestOBJ = new TcSpecialistSlotBookingRequestOBJ();
        tcSpecialistSlotBookingRequestOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        tcSpecialistSlotBookingRequestOBJ.setDate(mock(Timestamp.class));
        tcSpecialistSlotBookingRequestOBJ.setDuration(1L);
        tcSpecialistSlotBookingRequestOBJ.setFromTime("jane.doe@example.org");
        tcSpecialistSlotBookingRequestOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        tcSpecialistSlotBookingRequestOBJ.setToTime("To Time");
        tcSpecialistSlotBookingRequestOBJ.setUserID(1);

        // Act
        commonDoctorServiceImpl.callTmForSpecialistSlotBook(tcSpecialistSlotBookingRequestOBJ, "JaneDoe");
    }

    @Test
    void testCreateTMPrescriptionSms() throws IEMRException {
        // Arrange
        when(prescribedDrugDetailRepo.getPrescriptionDetails(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
        commonUtilityClass.setAge(1);
        commonUtilityClass.setAgeUnits("Age Units");
        commonUtilityClass.setAuthorization("JaneDoe");
        commonUtilityClass.setBenFlowID(1L);
        commonUtilityClass.setBenRegID(1L);
        commonUtilityClass.setBenVisitID(1L);
        commonUtilityClass.setBeneficiaryID(1L);
        commonUtilityClass.setBeneficiaryRegID(1L);
        commonUtilityClass.setBeneficiaryRegId(1L);
        commonUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        commonUtilityClass.setDistrictID(1);
        commonUtilityClass.setDistrictName("District Name");
        commonUtilityClass.setFacilityID(1);
        commonUtilityClass.setFirstName("Jane");
        commonUtilityClass.setGenderID((short) 1);
        commonUtilityClass.setGenderName("Gender Name");
        commonUtilityClass.setIsCovidFlowDone(true);
        commonUtilityClass.setIsMobile(true);
        commonUtilityClass.setIsSpecialist(true);
        commonUtilityClass.setLastName("Doe");
        commonUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        commonUtilityClass.setParkingPlaceID(1);
        commonUtilityClass.setParkingPlaceId(1);
        commonUtilityClass.setPrescriptionID(1L);
        commonUtilityClass.setProviderServiceMapID(1);
        commonUtilityClass.setServiceID((short) 1);
        commonUtilityClass.setSessionID(1);
        commonUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
        commonUtilityClass.setSubVisitCategory("Sub Visit Category");
        commonUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
        commonUtilityClass.setVanID(1);
        commonUtilityClass.setVanId(1);
        commonUtilityClass.setVillageId(1);
        commonUtilityClass.setVillageName("Village Name");
        commonUtilityClass.setVisitCategoryID(1);
        commonUtilityClass.setVisitCode(1L);

        // Act
        commonDoctorServiceImpl.createTMPrescriptionSms(commonUtilityClass);

        // Assert that nothing has changed
        verify(prescribedDrugDetailRepo).getPrescriptionDetails(isA(Long.class));
        assertEquals("Age Units", commonUtilityClass.getAgeUnits());
        assertEquals("District Name", commonUtilityClass.getDistrictName());
        assertEquals("Doe", commonUtilityClass.getLastName());
        assertEquals("Gender Name", commonUtilityClass.getGenderName());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", commonUtilityClass.getCreatedBy());
        assertEquals("Jan 1, 2020 9:00am GMT+0100", commonUtilityClass.getModifiedBy());
        assertEquals("Jane", commonUtilityClass.getFirstName());
        assertEquals("JaneDoe", commonUtilityClass.getAuthorization());
        assertEquals("Specialist Diagnosis", commonUtilityClass.getSpecialistDiagnosis());
        assertEquals("Sub Visit Category", commonUtilityClass.getSubVisitCategory());
        assertEquals("Treatments On Side Effects", commonUtilityClass.getTreatmentsOnSideEffects());
        assertEquals("Village Name", commonUtilityClass.getVillageName());
        assertEquals("[]", commonDoctorServiceImpl.getDocWorkList());
        assertEquals(1, commonUtilityClass.getAge().intValue());
        assertEquals(1, commonUtilityClass.getDistrictID().intValue());
        assertEquals(1, commonUtilityClass.getFacilityID().intValue());
        assertEquals(1, commonUtilityClass.getParkingPlaceID().intValue());
        assertEquals(1, commonUtilityClass.getParkingPlaceId().intValue());
        assertEquals(1, commonUtilityClass.getProviderServiceMapID().intValue());
        assertEquals(1, commonUtilityClass.getSessionID().intValue());
        assertEquals(1, commonUtilityClass.getVanID().intValue());
        assertEquals(1, commonUtilityClass.getVanId().intValue());
        assertEquals(1, commonUtilityClass.getVillageId().intValue());
        assertEquals(1, commonUtilityClass.getVisitCategoryID().intValue());
        assertEquals(1L, commonUtilityClass.getBenFlowID().longValue());
        assertEquals(1L, commonUtilityClass.getBenRegID().longValue());
        assertEquals(1L, commonUtilityClass.getBenVisitID().longValue());
        assertEquals(1L, commonUtilityClass.getBeneficiaryID().longValue());
        assertEquals(1L, commonUtilityClass.getBeneficiaryRegID().longValue());
        assertEquals(1L, commonUtilityClass.getBeneficiaryRegId().longValue());
        assertEquals(1L, commonUtilityClass.getPrescriptionID().longValue());
        assertEquals(1L, commonUtilityClass.getVisitCode().longValue());
        assertEquals((short) 1, commonUtilityClass.getGenderID().shortValue());
        assertEquals((short) 1, commonUtilityClass.getServiceID().shortValue());
        assertTrue(commonUtilityClass.getIsCovidFlowDone());
        assertTrue(commonUtilityClass.getIsMobile());
        assertTrue(commonUtilityClass.getIsSpecialist());
    }

    @Test
    void testCreateTMPrescriptionSms2() throws IEMRException {
        // Arrange
        when(sMSGatewayServiceImpl.smsSenderGateway2(Mockito.<String>any(), Mockito.<List<PrescribedDrugDetail>>any(),
                Mockito.<String>any(), Mockito.<Long>any(), Mockito.<String>any(), Mockito.<List<Object>>any())).thenReturn(3);

        PrescribedDrugDetail prescribedDrugDetail = new PrescribedDrugDetail();
        prescribedDrugDetail.setBenVisitID(1L);
        prescribedDrugDetail.setBeneficiaryRegID(1L);
        prescribedDrugDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        prescribedDrugDetail.setCreatedDate(mock(Timestamp.class));
        prescribedDrugDetail.setDeleted(true);
        prescribedDrugDetail.setDose("SMS not sent for TM Prescription");
        prescribedDrugDetail.setDrugID(1);
        prescribedDrugDetail.setDrugName("SMS not sent for TM Prescription");
        prescribedDrugDetail.setDrugStrength("SMS not sent for TM Prescription");
        prescribedDrugDetail.setDrugTradeOrBrandName("SMS not sent for TM Prescription");
        prescribedDrugDetail.setDuration("SMS not sent for TM Prescription");
        prescribedDrugDetail.setFormName("SMS not sent for TM Prescription");
        prescribedDrugDetail.setFrequency("SMS not sent for TM Prescription");
        prescribedDrugDetail.setId(1L);
        prescribedDrugDetail.setInstructions("SMS not sent for TM Prescription");
        prescribedDrugDetail.setLastModDate(mock(Timestamp.class));
        prescribedDrugDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        prescribedDrugDetail.setParkingPlaceID(1);
        prescribedDrugDetail.setPrescriptionID(1L);
        prescribedDrugDetail.setProcessed("SMS not sent for TM Prescription");
        prescribedDrugDetail.setProviderServiceMapID(1);
        prescribedDrugDetail.setQtyPrescribed(1);
        prescribedDrugDetail.setRelationToFood("SMS not sent for TM Prescription");
        prescribedDrugDetail.setReservedForChange("SMS not sent for TM Prescription");
        prescribedDrugDetail.setRoute("SMS not sent for TM Prescription");
        prescribedDrugDetail.setSctCode("SMS not sent for TM Prescription");
        prescribedDrugDetail.setSctTerm("SMS not sent for TM Prescription");
        prescribedDrugDetail.setSyncedBy("SMS not sent for TM Prescription");
        prescribedDrugDetail.setSyncedDate(mock(Timestamp.class));
        prescribedDrugDetail.setUnit("SMS not sent for TM Prescription");
        prescribedDrugDetail.setVanID(1);
        prescribedDrugDetail.setVanSerialNo(1L);
        prescribedDrugDetail.setVehicalNo("SMS not sent for TM Prescription");
        prescribedDrugDetail.setVisitCode(1L);

        ArrayList<PrescribedDrugDetail> prescribedDrugDetailList = new ArrayList<>();
        prescribedDrugDetailList.add(prescribedDrugDetail);
        when(prescribedDrugDetailRepo.getPrescriptionDetails(Mockito.<Long>any())).thenReturn(prescribedDrugDetailList);

        CommonUtilityClass commonUtilityClass = new CommonUtilityClass();
        commonUtilityClass.setAge(1);
        commonUtilityClass.setAgeUnits("Age Units");
        commonUtilityClass.setAuthorization("JaneDoe");
        commonUtilityClass.setBenFlowID(1L);
        commonUtilityClass.setBenRegID(1L);
        commonUtilityClass.setBenVisitID(1L);
        commonUtilityClass.setBeneficiaryID(1L);
        commonUtilityClass.setBeneficiaryRegID(1L);
        commonUtilityClass.setBeneficiaryRegId(1L);
        commonUtilityClass.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        commonUtilityClass.setDistrictID(1);
        commonUtilityClass.setDistrictName("District Name");
        commonUtilityClass.setFacilityID(1);
        commonUtilityClass.setFirstName("Jane");
        commonUtilityClass.setGenderID((short) 1);
        commonUtilityClass.setGenderName("Gender Name");
        commonUtilityClass.setIsCovidFlowDone(true);
        commonUtilityClass.setIsMobile(true);
        commonUtilityClass.setIsSpecialist(true);
        commonUtilityClass.setLastName("Doe");
        commonUtilityClass.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        commonUtilityClass.setParkingPlaceID(1);
        commonUtilityClass.setParkingPlaceId(1);
        commonUtilityClass.setPrescriptionID(1L);
        commonUtilityClass.setProviderServiceMapID(1);
        commonUtilityClass.setServiceID((short) 1);
        commonUtilityClass.setSessionID(1);
        commonUtilityClass.setSpecialistDiagnosis("Specialist Diagnosis");
        commonUtilityClass.setSubVisitCategory("Sub Visit Category");
        commonUtilityClass.setTreatmentsOnSideEffects("Treatments On Side Effects");
        commonUtilityClass.setVanID(1);
        commonUtilityClass.setVanId(1);
        commonUtilityClass.setVillageId(1);
        commonUtilityClass.setVillageName("Village Name");
        commonUtilityClass.setVisitCategoryID(1);
        commonUtilityClass.setVisitCode(1L);

        // Act
        commonDoctorServiceImpl.createTMPrescriptionSms(commonUtilityClass);

        // Assert that nothing has changed
        verify(prescribedDrugDetailRepo).getPrescriptionDetails(isA(Long.class));
        verify(sMSGatewayServiceImpl).smsSenderGateway2(eq("prescription"), isA(List.class), eq("JaneDoe"), isA(Long.class),
                eq("Jan 1, 2020 8:00am GMT+0100"), isNull());
        assertEquals("Age Units", commonUtilityClass.getAgeUnits());
        assertEquals("District Name", commonUtilityClass.getDistrictName());
        assertEquals("Doe", commonUtilityClass.getLastName());
        assertEquals("Gender Name", commonUtilityClass.getGenderName());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", commonUtilityClass.getCreatedBy());
        assertEquals("Jan 1, 2020 9:00am GMT+0100", commonUtilityClass.getModifiedBy());
        assertEquals("Jane", commonUtilityClass.getFirstName());
        assertEquals("JaneDoe", commonUtilityClass.getAuthorization());
        assertEquals("Specialist Diagnosis", commonUtilityClass.getSpecialistDiagnosis());
        assertEquals("Sub Visit Category", commonUtilityClass.getSubVisitCategory());
        assertEquals("Treatments On Side Effects", commonUtilityClass.getTreatmentsOnSideEffects());
        assertEquals("Village Name", commonUtilityClass.getVillageName());
        assertEquals("[]", commonDoctorServiceImpl.getDocWorkList());
        assertEquals(1, commonUtilityClass.getAge().intValue());
        assertEquals(1, commonUtilityClass.getDistrictID().intValue());
        assertEquals(1, commonUtilityClass.getFacilityID().intValue());
        assertEquals(1, commonUtilityClass.getParkingPlaceID().intValue());
        assertEquals(1, commonUtilityClass.getParkingPlaceId().intValue());
        assertEquals(1, commonUtilityClass.getProviderServiceMapID().intValue());
        assertEquals(1, commonUtilityClass.getSessionID().intValue());
        assertEquals(1, commonUtilityClass.getVanID().intValue());
        assertEquals(1, commonUtilityClass.getVanId().intValue());
        assertEquals(1, commonUtilityClass.getVillageId().intValue());
        assertEquals(1, commonUtilityClass.getVisitCategoryID().intValue());
        assertEquals(1L, commonUtilityClass.getBenFlowID().longValue());
        assertEquals(1L, commonUtilityClass.getBenRegID().longValue());
        assertEquals(1L, commonUtilityClass.getBenVisitID().longValue());
        assertEquals(1L, commonUtilityClass.getBeneficiaryID().longValue());
        assertEquals(1L, commonUtilityClass.getBeneficiaryRegID().longValue());
        assertEquals(1L, commonUtilityClass.getBeneficiaryRegId().longValue());
        assertEquals(1L, commonUtilityClass.getPrescriptionID().longValue());
        assertEquals(1L, commonUtilityClass.getVisitCode().longValue());
        assertEquals((short) 1, commonUtilityClass.getGenderID().shortValue());
        assertEquals((short) 1, commonUtilityClass.getServiceID().shortValue());
        assertTrue(commonUtilityClass.getIsCovidFlowDone());
        assertTrue(commonUtilityClass.getIsMobile());
        assertTrue(commonUtilityClass.getIsSpecialist());
    }

    @Test
    void testGetFoetalMonitorData() {
        // Arrange
        when(foetalMonitorRepo.getFoetalMonitorDetailsForCaseRecord(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualFoetalMonitorData = commonDoctorServiceImpl.getFoetalMonitorData(1L, 1L);

        // Assert
        verify(foetalMonitorRepo).getFoetalMonitorDetailsForCaseRecord(isA(Long.class), isA(Long.class));
        assertEquals("[]", actualFoetalMonitorData);
    }

    @Test
    void testGettersAndSetters() {
        CommonDoctorServiceImpl commonDoctorServiceImpl = new CommonDoctorServiceImpl();

        // Act
        commonDoctorServiceImpl.setBenChiefComplaintRepo(mock(BenChiefComplaintRepo.class));
        commonDoctorServiceImpl.setBenClinicalObservationsRepo(mock(BenClinicalObservationsRepo.class));
        commonDoctorServiceImpl.setBenReferDetailsRepo(mock(BenReferDetailsRepo.class));
        commonDoctorServiceImpl.setBeneficiaryFlowStatusRepo(mock(BeneficiaryFlowStatusRepo.class));
        commonDoctorServiceImpl.setCommonBenStatusFlowServiceImpl(new CommonBenStatusFlowServiceImpl());
        commonDoctorServiceImpl.setDocWorkListRepo(mock(DocWorkListRepo.class));
        commonDoctorServiceImpl.setLabTestOrderDetailRepo(mock(LabTestOrderDetailRepo.class));
        commonDoctorServiceImpl.setPrescribedDrugDetailRepo(mock(PrescribedDrugDetailRepo.class));
        commonDoctorServiceImpl.setSnomedServiceImpl(new SnomedServiceImpl());
    }
}
