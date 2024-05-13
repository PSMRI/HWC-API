package com.iemr.hwc.service.anc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.anc.ANCDiagnosis;
import com.iemr.hwc.repo.nurse.anc.ANCDiagnosisRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class ANCDoctorServiceImplTest {
    @Mock
    private ANCDiagnosisRepo aNCDiagnosisRepo;

    @InjectMocks
    private ANCDoctorServiceImpl aNCDoctorServiceImpl;

    @Mock
    private PrescriptionDetailRepo prescriptionDetailRepo;

    @Test
    void testSaveBenANCDiagnosis() throws IEMRException {
        // Arrange
        ANCDiagnosis ancDiagnosis = new ANCDiagnosis();
        ancDiagnosis.setBenVisitID(1L);
        ancDiagnosis.setBeneficiaryRegID(1L);
        ancDiagnosis.setCauseOfDeath("Cause Of Death");
        ancDiagnosis.setComplicationOfCurrentPregnancy("Complication Of Current Pregnancy");
        ancDiagnosis.setComplicationOfCurrentPregnancyList(new ArrayList<>());
        ancDiagnosis.setCounsellingProvided("Counselling Provided");
        ancDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancDiagnosis.setCreatedDate(mock(Timestamp.class));
        ancDiagnosis.setDateOfDeath(mock(Date.class));
        ancDiagnosis.setDeleted(true);
        ancDiagnosis.setExternalInvestigation("External Investigation");
        ancDiagnosis.setHighRiskCondition("High Risk Condition");
        ancDiagnosis.setHighRiskStatus("High Risk Status");
        ancDiagnosis.setID(1L);
        ancDiagnosis.setIsMaternalDeath(true);
        ancDiagnosis.setLastModDate(mock(Timestamp.class));
        ancDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancDiagnosis.setOtherCurrPregComplication("Other Curr Preg Complication");
        ancDiagnosis.setParkingPlaceID(1);
        ancDiagnosis.setPlaceOfDeath("Place Of Death");
        ancDiagnosis.setPrescriptionID(1L);
        ancDiagnosis.setProcessed("Processed");
        ancDiagnosis.setProviderServiceMapID(1);
        ancDiagnosis.setReservedForChange("Reserved For Change");
        ancDiagnosis.setSpecialistDiagnosis("Specialist Diagnosis");
        ancDiagnosis.setSyncedBy("Synced By");
        ancDiagnosis.setSyncedDate(mock(Timestamp.class));
        ancDiagnosis.setVanID(1);
        ancDiagnosis.setVanSerialNo(1L);
        ancDiagnosis.setVehicalNo("Vehical No");
        ancDiagnosis.setVisitCode(1L);
        when(aNCDiagnosisRepo.save(Mockito.<ANCDiagnosis>any())).thenReturn(ancDiagnosis);

        // Act
        Long actualSaveBenANCDiagnosisResult = aNCDoctorServiceImpl.saveBenANCDiagnosis(new JsonObject(), 1L);

        // Assert
        verify(aNCDiagnosisRepo).save(isA(ANCDiagnosis.class));
        assertEquals(1L, actualSaveBenANCDiagnosisResult.longValue());
    }

    @Test
    void testSaveBenANCDiagnosis2() throws IEMRException {
        // Arrange
        ANCDiagnosis ancDiagnosis = mock(ANCDiagnosis.class);
        when(ancDiagnosis.getID()).thenReturn(-1L);
        doNothing().when(ancDiagnosis).setBenVisitID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setCauseOfDeath(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setComplicationOfCurrentPregnancy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setComplicationOfCurrentPregnancyList(Mockito.<ArrayList<Map<String, String>>>any());
        doNothing().when(ancDiagnosis).setCounsellingProvided(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setCreatedBy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(ancDiagnosis).setDateOfDeath(Mockito.<Date>any());
        doNothing().when(ancDiagnosis).setDeleted(Mockito.<Boolean>any());
        doNothing().when(ancDiagnosis).setExternalInvestigation(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setHighRiskCondition(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setHighRiskStatus(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setIsMaternalDeath(Mockito.<Boolean>any());
        doNothing().when(ancDiagnosis).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(ancDiagnosis).setModifiedBy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setOtherCurrPregComplication(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(ancDiagnosis).setPlaceOfDeath(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setPrescriptionID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setProcessed(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(ancDiagnosis).setReservedForChange(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setSpecialistDiagnosis(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setSyncedBy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(ancDiagnosis).setVanID(Mockito.<Integer>any());
        doNothing().when(ancDiagnosis).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setVehicalNo(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setVisitCode(Mockito.<Long>any());
        ancDiagnosis.setBenVisitID(1L);
        ancDiagnosis.setBeneficiaryRegID(1L);
        ancDiagnosis.setCauseOfDeath("Cause Of Death");
        ancDiagnosis.setComplicationOfCurrentPregnancy("Complication Of Current Pregnancy");
        ancDiagnosis.setComplicationOfCurrentPregnancyList(new ArrayList<>());
        ancDiagnosis.setCounsellingProvided("Counselling Provided");
        ancDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancDiagnosis.setCreatedDate(mock(Timestamp.class));
        ancDiagnosis.setDateOfDeath(mock(Date.class));
        ancDiagnosis.setDeleted(true);
        ancDiagnosis.setExternalInvestigation("External Investigation");
        ancDiagnosis.setHighRiskCondition("High Risk Condition");
        ancDiagnosis.setHighRiskStatus("High Risk Status");
        ancDiagnosis.setID(1L);
        ancDiagnosis.setIsMaternalDeath(true);
        ancDiagnosis.setLastModDate(mock(Timestamp.class));
        ancDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancDiagnosis.setOtherCurrPregComplication("Other Curr Preg Complication");
        ancDiagnosis.setParkingPlaceID(1);
        ancDiagnosis.setPlaceOfDeath("Place Of Death");
        ancDiagnosis.setPrescriptionID(1L);
        ancDiagnosis.setProcessed("Processed");
        ancDiagnosis.setProviderServiceMapID(1);
        ancDiagnosis.setReservedForChange("Reserved For Change");
        ancDiagnosis.setSpecialistDiagnosis("Specialist Diagnosis");
        ancDiagnosis.setSyncedBy("Synced By");
        ancDiagnosis.setSyncedDate(mock(Timestamp.class));
        ancDiagnosis.setVanID(1);
        ancDiagnosis.setVanSerialNo(1L);
        ancDiagnosis.setVehicalNo("Vehical No");
        ancDiagnosis.setVisitCode(1L);
        when(aNCDiagnosisRepo.save(Mockito.<ANCDiagnosis>any())).thenReturn(ancDiagnosis);

        // Act
        Long actualSaveBenANCDiagnosisResult = aNCDoctorServiceImpl.saveBenANCDiagnosis(new JsonObject(), 1L);

        // Assert
        verify(ancDiagnosis).getID();
        verify(ancDiagnosis).setBenVisitID(isA(Long.class));
        verify(ancDiagnosis).setBeneficiaryRegID(isA(Long.class));
        verify(ancDiagnosis).setCauseOfDeath(eq("Cause Of Death"));
        verify(ancDiagnosis).setComplicationOfCurrentPregnancy(eq("Complication Of Current Pregnancy"));
        verify(ancDiagnosis).setComplicationOfCurrentPregnancyList(isA(ArrayList.class));
        verify(ancDiagnosis).setCounsellingProvided(eq("Counselling Provided"));
        verify(ancDiagnosis).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(ancDiagnosis).setCreatedDate(isA(Timestamp.class));
        verify(ancDiagnosis).setDateOfDeath(isA(Date.class));
        verify(ancDiagnosis).setDeleted(isA(Boolean.class));
        verify(ancDiagnosis).setExternalInvestigation(eq("External Investigation"));
        verify(ancDiagnosis).setHighRiskCondition(eq("High Risk Condition"));
        verify(ancDiagnosis).setHighRiskStatus(eq("High Risk Status"));
        verify(ancDiagnosis).setID(isA(Long.class));
        verify(ancDiagnosis).setIsMaternalDeath(isA(Boolean.class));
        verify(ancDiagnosis).setLastModDate(isA(Timestamp.class));
        verify(ancDiagnosis).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(ancDiagnosis).setOtherCurrPregComplication(eq("Other Curr Preg Complication"));
        verify(ancDiagnosis).setParkingPlaceID(isA(Integer.class));
        verify(ancDiagnosis).setPlaceOfDeath(eq("Place Of Death"));
        verify(ancDiagnosis).setPrescriptionID(isA(Long.class));
        verify(ancDiagnosis).setProcessed(eq("Processed"));
        verify(ancDiagnosis).setProviderServiceMapID(isA(Integer.class));
        verify(ancDiagnosis).setReservedForChange(eq("Reserved For Change"));
        verify(ancDiagnosis).setSpecialistDiagnosis(eq("Specialist Diagnosis"));
        verify(ancDiagnosis).setSyncedBy(eq("Synced By"));
        verify(ancDiagnosis).setSyncedDate(isA(Timestamp.class));
        verify(ancDiagnosis).setVanID(isA(Integer.class));
        verify(ancDiagnosis).setVanSerialNo(isA(Long.class));
        verify(ancDiagnosis).setVehicalNo(eq("Vehical No"));
        verify(ancDiagnosis).setVisitCode(isA(Long.class));
        verify(aNCDiagnosisRepo).save(isA(ANCDiagnosis.class));
        assertNull(actualSaveBenANCDiagnosisResult);
    }

    @Test
    void testSaveBenANCDiagnosis3() throws IEMRException {
        // Arrange
        ANCDiagnosis ancDiagnosis = mock(ANCDiagnosis.class);
        when(ancDiagnosis.getID()).thenReturn(1L);
        doNothing().when(ancDiagnosis).setBenVisitID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setCauseOfDeath(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setComplicationOfCurrentPregnancy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setComplicationOfCurrentPregnancyList(Mockito.<ArrayList<Map<String, String>>>any());
        doNothing().when(ancDiagnosis).setCounsellingProvided(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setCreatedBy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(ancDiagnosis).setDateOfDeath(Mockito.<Date>any());
        doNothing().when(ancDiagnosis).setDeleted(Mockito.<Boolean>any());
        doNothing().when(ancDiagnosis).setExternalInvestigation(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setHighRiskCondition(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setHighRiskStatus(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setIsMaternalDeath(Mockito.<Boolean>any());
        doNothing().when(ancDiagnosis).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(ancDiagnosis).setModifiedBy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setOtherCurrPregComplication(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(ancDiagnosis).setPlaceOfDeath(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setPrescriptionID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setProcessed(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(ancDiagnosis).setReservedForChange(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setSpecialistDiagnosis(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setSyncedBy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(ancDiagnosis).setVanID(Mockito.<Integer>any());
        doNothing().when(ancDiagnosis).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setVehicalNo(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setVisitCode(Mockito.<Long>any());
        ancDiagnosis.setBenVisitID(1L);
        ancDiagnosis.setBeneficiaryRegID(1L);
        ancDiagnosis.setCauseOfDeath("Cause Of Death");
        ancDiagnosis.setComplicationOfCurrentPregnancy("Complication Of Current Pregnancy");
        ancDiagnosis.setComplicationOfCurrentPregnancyList(new ArrayList<>());
        ancDiagnosis.setCounsellingProvided("Counselling Provided");
        ancDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancDiagnosis.setCreatedDate(mock(Timestamp.class));
        ancDiagnosis.setDateOfDeath(mock(Date.class));
        ancDiagnosis.setDeleted(true);
        ancDiagnosis.setExternalInvestigation("External Investigation");
        ancDiagnosis.setHighRiskCondition("High Risk Condition");
        ancDiagnosis.setHighRiskStatus("High Risk Status");
        ancDiagnosis.setID(1L);
        ancDiagnosis.setIsMaternalDeath(true);
        ancDiagnosis.setLastModDate(mock(Timestamp.class));
        ancDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancDiagnosis.setOtherCurrPregComplication("Other Curr Preg Complication");
        ancDiagnosis.setParkingPlaceID(1);
        ancDiagnosis.setPlaceOfDeath("Place Of Death");
        ancDiagnosis.setPrescriptionID(1L);
        ancDiagnosis.setProcessed("Processed");
        ancDiagnosis.setProviderServiceMapID(1);
        ancDiagnosis.setReservedForChange("Reserved For Change");
        ancDiagnosis.setSpecialistDiagnosis("Specialist Diagnosis");
        ancDiagnosis.setSyncedBy("Synced By");
        ancDiagnosis.setSyncedDate(mock(Timestamp.class));
        ancDiagnosis.setVanID(1);
        ancDiagnosis.setVanSerialNo(1L);
        ancDiagnosis.setVehicalNo("Vehical No");
        ancDiagnosis.setVisitCode(1L);
        when(aNCDiagnosisRepo.save(Mockito.<ANCDiagnosis>any())).thenReturn(ancDiagnosis);

        JsonObject obj = new JsonObject();
        obj.add("Property", new JsonArray(3));

        // Act
        Long actualSaveBenANCDiagnosisResult = aNCDoctorServiceImpl.saveBenANCDiagnosis(obj, 1L);

        // Assert
        verify(ancDiagnosis, atLeast(1)).getID();
        verify(ancDiagnosis).setBenVisitID(isA(Long.class));
        verify(ancDiagnosis).setBeneficiaryRegID(isA(Long.class));
        verify(ancDiagnosis).setCauseOfDeath(eq("Cause Of Death"));
        verify(ancDiagnosis).setComplicationOfCurrentPregnancy(eq("Complication Of Current Pregnancy"));
        verify(ancDiagnosis).setComplicationOfCurrentPregnancyList(isA(ArrayList.class));
        verify(ancDiagnosis).setCounsellingProvided(eq("Counselling Provided"));
        verify(ancDiagnosis).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(ancDiagnosis).setCreatedDate(isA(Timestamp.class));
        verify(ancDiagnosis).setDateOfDeath(isA(Date.class));
        verify(ancDiagnosis).setDeleted(isA(Boolean.class));
        verify(ancDiagnosis).setExternalInvestigation(eq("External Investigation"));
        verify(ancDiagnosis).setHighRiskCondition(eq("High Risk Condition"));
        verify(ancDiagnosis).setHighRiskStatus(eq("High Risk Status"));
        verify(ancDiagnosis).setID(isA(Long.class));
        verify(ancDiagnosis).setIsMaternalDeath(isA(Boolean.class));
        verify(ancDiagnosis).setLastModDate(isA(Timestamp.class));
        verify(ancDiagnosis).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(ancDiagnosis).setOtherCurrPregComplication(eq("Other Curr Preg Complication"));
        verify(ancDiagnosis).setParkingPlaceID(isA(Integer.class));
        verify(ancDiagnosis).setPlaceOfDeath(eq("Place Of Death"));
        verify(ancDiagnosis).setPrescriptionID(isA(Long.class));
        verify(ancDiagnosis).setProcessed(eq("Processed"));
        verify(ancDiagnosis).setProviderServiceMapID(isA(Integer.class));
        verify(ancDiagnosis).setReservedForChange(eq("Reserved For Change"));
        verify(ancDiagnosis).setSpecialistDiagnosis(eq("Specialist Diagnosis"));
        verify(ancDiagnosis).setSyncedBy(eq("Synced By"));
        verify(ancDiagnosis).setSyncedDate(isA(Timestamp.class));
        verify(ancDiagnosis).setVanID(isA(Integer.class));
        verify(ancDiagnosis).setVanSerialNo(isA(Long.class));
        verify(ancDiagnosis).setVehicalNo(eq("Vehical No"));
        verify(ancDiagnosis).setVisitCode(isA(Long.class));
        verify(aNCDiagnosisRepo).save(isA(ANCDiagnosis.class));
        assertEquals(1L, actualSaveBenANCDiagnosisResult.longValue());
    }

    @Test
    void testSaveBenANCDiagnosis4() throws IEMRException {
        // Arrange
        ANCDiagnosis ancDiagnosis = mock(ANCDiagnosis.class);
        when(ancDiagnosis.getID()).thenReturn(1L);
        doNothing().when(ancDiagnosis).setBenVisitID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setCauseOfDeath(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setComplicationOfCurrentPregnancy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setComplicationOfCurrentPregnancyList(Mockito.<ArrayList<Map<String, String>>>any());
        doNothing().when(ancDiagnosis).setCounsellingProvided(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setCreatedBy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(ancDiagnosis).setDateOfDeath(Mockito.<Date>any());
        doNothing().when(ancDiagnosis).setDeleted(Mockito.<Boolean>any());
        doNothing().when(ancDiagnosis).setExternalInvestigation(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setHighRiskCondition(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setHighRiskStatus(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setIsMaternalDeath(Mockito.<Boolean>any());
        doNothing().when(ancDiagnosis).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(ancDiagnosis).setModifiedBy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setOtherCurrPregComplication(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(ancDiagnosis).setPlaceOfDeath(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setPrescriptionID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setProcessed(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(ancDiagnosis).setReservedForChange(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setSpecialistDiagnosis(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setSyncedBy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(ancDiagnosis).setVanID(Mockito.<Integer>any());
        doNothing().when(ancDiagnosis).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setVehicalNo(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setVisitCode(Mockito.<Long>any());
        ancDiagnosis.setBenVisitID(1L);
        ancDiagnosis.setBeneficiaryRegID(1L);
        ancDiagnosis.setCauseOfDeath("Cause Of Death");
        ancDiagnosis.setComplicationOfCurrentPregnancy("Complication Of Current Pregnancy");
        ancDiagnosis.setComplicationOfCurrentPregnancyList(new ArrayList<>());
        ancDiagnosis.setCounsellingProvided("Counselling Provided");
        ancDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancDiagnosis.setCreatedDate(mock(Timestamp.class));
        ancDiagnosis.setDateOfDeath(mock(Date.class));
        ancDiagnosis.setDeleted(true);
        ancDiagnosis.setExternalInvestigation("External Investigation");
        ancDiagnosis.setHighRiskCondition("High Risk Condition");
        ancDiagnosis.setHighRiskStatus("High Risk Status");
        ancDiagnosis.setID(1L);
        ancDiagnosis.setIsMaternalDeath(true);
        ancDiagnosis.setLastModDate(mock(Timestamp.class));
        ancDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancDiagnosis.setOtherCurrPregComplication("Other Curr Preg Complication");
        ancDiagnosis.setParkingPlaceID(1);
        ancDiagnosis.setPlaceOfDeath("Place Of Death");
        ancDiagnosis.setPrescriptionID(1L);
        ancDiagnosis.setProcessed("Processed");
        ancDiagnosis.setProviderServiceMapID(1);
        ancDiagnosis.setReservedForChange("Reserved For Change");
        ancDiagnosis.setSpecialistDiagnosis("Specialist Diagnosis");
        ancDiagnosis.setSyncedBy("Synced By");
        ancDiagnosis.setSyncedDate(mock(Timestamp.class));
        ancDiagnosis.setVanID(1);
        ancDiagnosis.setVanSerialNo(1L);
        ancDiagnosis.setVehicalNo("Vehical No");
        ancDiagnosis.setVisitCode(1L);
        when(aNCDiagnosisRepo.save(Mockito.<ANCDiagnosis>any())).thenReturn(ancDiagnosis);

        JsonObject obj = new JsonObject();
        obj.addProperty("Property", "42");

        // Act
        Long actualSaveBenANCDiagnosisResult = aNCDoctorServiceImpl.saveBenANCDiagnosis(obj, 1L);

        // Assert
        verify(ancDiagnosis, atLeast(1)).getID();
        verify(ancDiagnosis).setBenVisitID(isA(Long.class));
        verify(ancDiagnosis).setBeneficiaryRegID(isA(Long.class));
        verify(ancDiagnosis).setCauseOfDeath(eq("Cause Of Death"));
        verify(ancDiagnosis).setComplicationOfCurrentPregnancy(eq("Complication Of Current Pregnancy"));
        verify(ancDiagnosis).setComplicationOfCurrentPregnancyList(isA(ArrayList.class));
        verify(ancDiagnosis).setCounsellingProvided(eq("Counselling Provided"));
        verify(ancDiagnosis).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(ancDiagnosis).setCreatedDate(isA(Timestamp.class));
        verify(ancDiagnosis).setDateOfDeath(isA(Date.class));
        verify(ancDiagnosis).setDeleted(isA(Boolean.class));
        verify(ancDiagnosis).setExternalInvestigation(eq("External Investigation"));
        verify(ancDiagnosis).setHighRiskCondition(eq("High Risk Condition"));
        verify(ancDiagnosis).setHighRiskStatus(eq("High Risk Status"));
        verify(ancDiagnosis).setID(isA(Long.class));
        verify(ancDiagnosis).setIsMaternalDeath(isA(Boolean.class));
        verify(ancDiagnosis).setLastModDate(isA(Timestamp.class));
        verify(ancDiagnosis).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(ancDiagnosis).setOtherCurrPregComplication(eq("Other Curr Preg Complication"));
        verify(ancDiagnosis).setParkingPlaceID(isA(Integer.class));
        verify(ancDiagnosis).setPlaceOfDeath(eq("Place Of Death"));
        verify(ancDiagnosis).setPrescriptionID(isA(Long.class));
        verify(ancDiagnosis).setProcessed(eq("Processed"));
        verify(ancDiagnosis).setProviderServiceMapID(isA(Integer.class));
        verify(ancDiagnosis).setReservedForChange(eq("Reserved For Change"));
        verify(ancDiagnosis).setSpecialistDiagnosis(eq("Specialist Diagnosis"));
        verify(ancDiagnosis).setSyncedBy(eq("Synced By"));
        verify(ancDiagnosis).setSyncedDate(isA(Timestamp.class));
        verify(ancDiagnosis).setVanID(isA(Integer.class));
        verify(ancDiagnosis).setVanSerialNo(isA(Long.class));
        verify(ancDiagnosis).setVehicalNo(eq("Vehical No"));
        verify(ancDiagnosis).setVisitCode(isA(Long.class));
        verify(aNCDiagnosisRepo).save(isA(ANCDiagnosis.class));
        assertEquals(1L, actualSaveBenANCDiagnosisResult.longValue());
    }

    @Test
    void testSaveBenANCDiagnosis5() throws IEMRException {
        // Arrange
        ANCDiagnosis ancDiagnosis = mock(ANCDiagnosis.class);
        when(ancDiagnosis.getID()).thenReturn(1L);
        doNothing().when(ancDiagnosis).setBenVisitID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setCauseOfDeath(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setComplicationOfCurrentPregnancy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setComplicationOfCurrentPregnancyList(Mockito.<ArrayList<Map<String, String>>>any());
        doNothing().when(ancDiagnosis).setCounsellingProvided(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setCreatedBy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(ancDiagnosis).setDateOfDeath(Mockito.<Date>any());
        doNothing().when(ancDiagnosis).setDeleted(Mockito.<Boolean>any());
        doNothing().when(ancDiagnosis).setExternalInvestigation(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setHighRiskCondition(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setHighRiskStatus(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setIsMaternalDeath(Mockito.<Boolean>any());
        doNothing().when(ancDiagnosis).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(ancDiagnosis).setModifiedBy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setOtherCurrPregComplication(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(ancDiagnosis).setPlaceOfDeath(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setPrescriptionID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setProcessed(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(ancDiagnosis).setReservedForChange(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setSpecialistDiagnosis(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setSyncedBy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(ancDiagnosis).setVanID(Mockito.<Integer>any());
        doNothing().when(ancDiagnosis).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setVehicalNo(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setVisitCode(Mockito.<Long>any());
        ancDiagnosis.setBenVisitID(1L);
        ancDiagnosis.setBeneficiaryRegID(1L);
        ancDiagnosis.setCauseOfDeath("Cause Of Death");
        ancDiagnosis.setComplicationOfCurrentPregnancy("Complication Of Current Pregnancy");
        ancDiagnosis.setComplicationOfCurrentPregnancyList(new ArrayList<>());
        ancDiagnosis.setCounsellingProvided("Counselling Provided");
        ancDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancDiagnosis.setCreatedDate(mock(Timestamp.class));
        ancDiagnosis.setDateOfDeath(mock(Date.class));
        ancDiagnosis.setDeleted(true);
        ancDiagnosis.setExternalInvestigation("External Investigation");
        ancDiagnosis.setHighRiskCondition("High Risk Condition");
        ancDiagnosis.setHighRiskStatus("High Risk Status");
        ancDiagnosis.setID(1L);
        ancDiagnosis.setIsMaternalDeath(true);
        ancDiagnosis.setLastModDate(mock(Timestamp.class));
        ancDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancDiagnosis.setOtherCurrPregComplication("Other Curr Preg Complication");
        ancDiagnosis.setParkingPlaceID(1);
        ancDiagnosis.setPlaceOfDeath("Place Of Death");
        ancDiagnosis.setPrescriptionID(1L);
        ancDiagnosis.setProcessed("Processed");
        ancDiagnosis.setProviderServiceMapID(1);
        ancDiagnosis.setReservedForChange("Reserved For Change");
        ancDiagnosis.setSpecialistDiagnosis("Specialist Diagnosis");
        ancDiagnosis.setSyncedBy("Synced By");
        ancDiagnosis.setSyncedDate(mock(Timestamp.class));
        ancDiagnosis.setVanID(1);
        ancDiagnosis.setVanSerialNo(1L);
        ancDiagnosis.setVehicalNo("Vehical No");
        ancDiagnosis.setVisitCode(1L);
        when(aNCDiagnosisRepo.save(Mockito.<ANCDiagnosis>any())).thenReturn(ancDiagnosis);

        JsonObject obj = new JsonObject();
        obj.addProperty("Property", Integer.valueOf(1));

        // Act
        Long actualSaveBenANCDiagnosisResult = aNCDoctorServiceImpl.saveBenANCDiagnosis(obj, 1L);

        // Assert
        verify(ancDiagnosis, atLeast(1)).getID();
        verify(ancDiagnosis).setBenVisitID(isA(Long.class));
        verify(ancDiagnosis).setBeneficiaryRegID(isA(Long.class));
        verify(ancDiagnosis).setCauseOfDeath(eq("Cause Of Death"));
        verify(ancDiagnosis).setComplicationOfCurrentPregnancy(eq("Complication Of Current Pregnancy"));
        verify(ancDiagnosis).setComplicationOfCurrentPregnancyList(isA(ArrayList.class));
        verify(ancDiagnosis).setCounsellingProvided(eq("Counselling Provided"));
        verify(ancDiagnosis).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(ancDiagnosis).setCreatedDate(isA(Timestamp.class));
        verify(ancDiagnosis).setDateOfDeath(isA(Date.class));
        verify(ancDiagnosis).setDeleted(isA(Boolean.class));
        verify(ancDiagnosis).setExternalInvestigation(eq("External Investigation"));
        verify(ancDiagnosis).setHighRiskCondition(eq("High Risk Condition"));
        verify(ancDiagnosis).setHighRiskStatus(eq("High Risk Status"));
        verify(ancDiagnosis).setID(isA(Long.class));
        verify(ancDiagnosis).setIsMaternalDeath(isA(Boolean.class));
        verify(ancDiagnosis).setLastModDate(isA(Timestamp.class));
        verify(ancDiagnosis).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(ancDiagnosis).setOtherCurrPregComplication(eq("Other Curr Preg Complication"));
        verify(ancDiagnosis).setParkingPlaceID(isA(Integer.class));
        verify(ancDiagnosis).setPlaceOfDeath(eq("Place Of Death"));
        verify(ancDiagnosis).setPrescriptionID(isA(Long.class));
        verify(ancDiagnosis).setProcessed(eq("Processed"));
        verify(ancDiagnosis).setProviderServiceMapID(isA(Integer.class));
        verify(ancDiagnosis).setReservedForChange(eq("Reserved For Change"));
        verify(ancDiagnosis).setSpecialistDiagnosis(eq("Specialist Diagnosis"));
        verify(ancDiagnosis).setSyncedBy(eq("Synced By"));
        verify(ancDiagnosis).setSyncedDate(isA(Timestamp.class));
        verify(ancDiagnosis).setVanID(isA(Integer.class));
        verify(ancDiagnosis).setVanSerialNo(isA(Long.class));
        verify(ancDiagnosis).setVehicalNo(eq("Vehical No"));
        verify(ancDiagnosis).setVisitCode(isA(Long.class));
        verify(aNCDiagnosisRepo).save(isA(ANCDiagnosis.class));
        assertEquals(1L, actualSaveBenANCDiagnosisResult.longValue());
    }

    @Test
    void testSaveBenANCDiagnosis6() throws IEMRException {
        // Arrange
        ANCDiagnosis ancDiagnosis = mock(ANCDiagnosis.class);
        when(ancDiagnosis.getID()).thenReturn(1L);
        doNothing().when(ancDiagnosis).setBenVisitID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setCauseOfDeath(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setComplicationOfCurrentPregnancy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setComplicationOfCurrentPregnancyList(Mockito.<ArrayList<Map<String, String>>>any());
        doNothing().when(ancDiagnosis).setCounsellingProvided(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setCreatedBy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(ancDiagnosis).setDateOfDeath(Mockito.<Date>any());
        doNothing().when(ancDiagnosis).setDeleted(Mockito.<Boolean>any());
        doNothing().when(ancDiagnosis).setExternalInvestigation(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setHighRiskCondition(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setHighRiskStatus(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setIsMaternalDeath(Mockito.<Boolean>any());
        doNothing().when(ancDiagnosis).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(ancDiagnosis).setModifiedBy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setOtherCurrPregComplication(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(ancDiagnosis).setPlaceOfDeath(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setPrescriptionID(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setProcessed(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(ancDiagnosis).setReservedForChange(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setSpecialistDiagnosis(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setSyncedBy(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(ancDiagnosis).setVanID(Mockito.<Integer>any());
        doNothing().when(ancDiagnosis).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(ancDiagnosis).setVehicalNo(Mockito.<String>any());
        doNothing().when(ancDiagnosis).setVisitCode(Mockito.<Long>any());
        ancDiagnosis.setBenVisitID(1L);
        ancDiagnosis.setBeneficiaryRegID(1L);
        ancDiagnosis.setCauseOfDeath("Cause Of Death");
        ancDiagnosis.setComplicationOfCurrentPregnancy("Complication Of Current Pregnancy");
        ancDiagnosis.setComplicationOfCurrentPregnancyList(new ArrayList<>());
        ancDiagnosis.setCounsellingProvided("Counselling Provided");
        ancDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancDiagnosis.setCreatedDate(mock(Timestamp.class));
        ancDiagnosis.setDateOfDeath(mock(Date.class));
        ancDiagnosis.setDeleted(true);
        ancDiagnosis.setExternalInvestigation("External Investigation");
        ancDiagnosis.setHighRiskCondition("High Risk Condition");
        ancDiagnosis.setHighRiskStatus("High Risk Status");
        ancDiagnosis.setID(1L);
        ancDiagnosis.setIsMaternalDeath(true);
        ancDiagnosis.setLastModDate(mock(Timestamp.class));
        ancDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancDiagnosis.setOtherCurrPregComplication("Other Curr Preg Complication");
        ancDiagnosis.setParkingPlaceID(1);
        ancDiagnosis.setPlaceOfDeath("Place Of Death");
        ancDiagnosis.setPrescriptionID(1L);
        ancDiagnosis.setProcessed("Processed");
        ancDiagnosis.setProviderServiceMapID(1);
        ancDiagnosis.setReservedForChange("Reserved For Change");
        ancDiagnosis.setSpecialistDiagnosis("Specialist Diagnosis");
        ancDiagnosis.setSyncedBy("Synced By");
        ancDiagnosis.setSyncedDate(mock(Timestamp.class));
        ancDiagnosis.setVanID(1);
        ancDiagnosis.setVanSerialNo(1L);
        ancDiagnosis.setVehicalNo("Vehical No");
        ancDiagnosis.setVisitCode(1L);
        when(aNCDiagnosisRepo.save(Mockito.<ANCDiagnosis>any())).thenReturn(ancDiagnosis);

        JsonObject obj = new JsonObject();
        obj.addProperty("Property", true);

        // Act
        Long actualSaveBenANCDiagnosisResult = aNCDoctorServiceImpl.saveBenANCDiagnosis(obj, 1L);

        // Assert
        verify(ancDiagnosis, atLeast(1)).getID();
        verify(ancDiagnosis).setBenVisitID(isA(Long.class));
        verify(ancDiagnosis).setBeneficiaryRegID(isA(Long.class));
        verify(ancDiagnosis).setCauseOfDeath(eq("Cause Of Death"));
        verify(ancDiagnosis).setComplicationOfCurrentPregnancy(eq("Complication Of Current Pregnancy"));
        verify(ancDiagnosis).setComplicationOfCurrentPregnancyList(isA(ArrayList.class));
        verify(ancDiagnosis).setCounsellingProvided(eq("Counselling Provided"));
        verify(ancDiagnosis).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(ancDiagnosis).setCreatedDate(isA(Timestamp.class));
        verify(ancDiagnosis).setDateOfDeath(isA(Date.class));
        verify(ancDiagnosis).setDeleted(isA(Boolean.class));
        verify(ancDiagnosis).setExternalInvestigation(eq("External Investigation"));
        verify(ancDiagnosis).setHighRiskCondition(eq("High Risk Condition"));
        verify(ancDiagnosis).setHighRiskStatus(eq("High Risk Status"));
        verify(ancDiagnosis).setID(isA(Long.class));
        verify(ancDiagnosis).setIsMaternalDeath(isA(Boolean.class));
        verify(ancDiagnosis).setLastModDate(isA(Timestamp.class));
        verify(ancDiagnosis).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(ancDiagnosis).setOtherCurrPregComplication(eq("Other Curr Preg Complication"));
        verify(ancDiagnosis).setParkingPlaceID(isA(Integer.class));
        verify(ancDiagnosis).setPlaceOfDeath(eq("Place Of Death"));
        verify(ancDiagnosis).setPrescriptionID(isA(Long.class));
        verify(ancDiagnosis).setProcessed(eq("Processed"));
        verify(ancDiagnosis).setProviderServiceMapID(isA(Integer.class));
        verify(ancDiagnosis).setReservedForChange(eq("Reserved For Change"));
        verify(ancDiagnosis).setSpecialistDiagnosis(eq("Specialist Diagnosis"));
        verify(ancDiagnosis).setSyncedBy(eq("Synced By"));
        verify(ancDiagnosis).setSyncedDate(isA(Timestamp.class));
        verify(ancDiagnosis).setVanID(isA(Integer.class));
        verify(ancDiagnosis).setVanSerialNo(isA(Long.class));
        verify(ancDiagnosis).setVehicalNo(eq("Vehical No"));
        verify(ancDiagnosis).setVisitCode(isA(Long.class));
        verify(aNCDiagnosisRepo).save(isA(ANCDiagnosis.class));
        assertEquals(1L, actualSaveBenANCDiagnosisResult.longValue());
    }

    @Test
    void testGetANCDiagnosisDetails() {
        // Arrange
        when(aNCDiagnosisRepo.findByBeneficiaryRegIDAndVisitCode(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        when(prescriptionDetailRepo.getExternalinvestigationForVisitCode(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualANCDiagnosisDetails = aNCDoctorServiceImpl.getANCDiagnosisDetails(1L, 1L);

        // Assert
        verify(aNCDiagnosisRepo).findByBeneficiaryRegIDAndVisitCode(isA(Long.class), isA(Long.class));
        verify(prescriptionDetailRepo).getExternalinvestigationForVisitCode(isA(Long.class), isA(Long.class));
        assertEquals(
                "{\"complicationOfCurrentPregnancy\":\"null , Other-complications : null\",\"complicationOfCurrentPregnancyList"
                        + "\":[]}",
                actualANCDiagnosisDetails);
    }

    @Test
    void testUpdateBenANCDiagnosis() throws IEMRException {
        // Arrange
        when(aNCDiagnosisRepo.updateANCDiagnosis(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<Date>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(1);
        when(aNCDiagnosisRepo.getANCDiagnosisStatus(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Anc Diagnosis Status");

        ANCDiagnosis ancDiagnosis = new ANCDiagnosis();
        ancDiagnosis.setBenVisitID(1L);
        ancDiagnosis.setBeneficiaryRegID(1L);
        ancDiagnosis.setCauseOfDeath("Cause Of Death");
        ancDiagnosis.setComplicationOfCurrentPregnancy("Complication Of Current Pregnancy");
        ancDiagnosis.setComplicationOfCurrentPregnancyList(new ArrayList<>());
        ancDiagnosis.setCounsellingProvided("Counselling Provided");
        ancDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancDiagnosis.setCreatedDate(mock(Timestamp.class));
        ancDiagnosis.setDateOfDeath(mock(Date.class));
        ancDiagnosis.setDeleted(true);
        ancDiagnosis.setExternalInvestigation("External Investigation");
        ancDiagnosis.setHighRiskCondition("High Risk Condition");
        ancDiagnosis.setHighRiskStatus("High Risk Status");
        ancDiagnosis.setID(1L);
        ancDiagnosis.setIsMaternalDeath(true);
        ancDiagnosis.setLastModDate(mock(Timestamp.class));
        ancDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancDiagnosis.setOtherCurrPregComplication("Other Curr Preg Complication");
        ancDiagnosis.setParkingPlaceID(1);
        ancDiagnosis.setPlaceOfDeath("Place Of Death");
        ancDiagnosis.setPrescriptionID(1L);
        ancDiagnosis.setProcessed("Processed");
        ancDiagnosis.setProviderServiceMapID(1);
        ancDiagnosis.setReservedForChange("Reserved For Change");
        ancDiagnosis.setSpecialistDiagnosis("Specialist Diagnosis");
        ancDiagnosis.setSyncedBy("Synced By");
        ancDiagnosis.setSyncedDate(mock(Timestamp.class));
        ancDiagnosis.setVanID(1);
        ancDiagnosis.setVanSerialNo(1L);
        ancDiagnosis.setVehicalNo("Vehical No");
        ancDiagnosis.setVisitCode(1L);

        // Act
        int actualUpdateBenANCDiagnosisResult = aNCDoctorServiceImpl.updateBenANCDiagnosis(ancDiagnosis);

        // Assert
        verify(aNCDiagnosisRepo).getANCDiagnosisStatus(isA(Long.class), isA(Long.class), isA(Long.class));
        verify(aNCDiagnosisRepo).updateANCDiagnosis(eq("High Risk Status"), eq("High Risk Condition"), eq(""),
                isA(Boolean.class), eq("Place Of Death"), isA(Date.class), eq("Cause Of Death"),
                eq("Jan 1, 2020 8:00am GMT+0100"), eq("U"), isA(Long.class), isA(Long.class),
                eq("Other Curr Preg Complication"));
        assertEquals("", ancDiagnosis.getComplicationOfCurrentPregnancy());
        assertEquals("U", ancDiagnosis.getProcessed());
        assertEquals(1, actualUpdateBenANCDiagnosisResult);
    }

    @Test
    void testUpdateBenANCDiagnosis2() throws IEMRException {
        // Arrange
        when(aNCDiagnosisRepo.updateANCDiagnosis(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<Date>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(0);
        when(aNCDiagnosisRepo.getANCDiagnosisStatus(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Anc Diagnosis Status");

        ANCDiagnosis ancDiagnosis = new ANCDiagnosis();
        ancDiagnosis.setBenVisitID(1L);
        ancDiagnosis.setBeneficiaryRegID(1L);
        ancDiagnosis.setCauseOfDeath("Cause Of Death");
        ancDiagnosis.setComplicationOfCurrentPregnancy("Complication Of Current Pregnancy");
        ancDiagnosis.setComplicationOfCurrentPregnancyList(new ArrayList<>());
        ancDiagnosis.setCounsellingProvided("Counselling Provided");
        ancDiagnosis.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancDiagnosis.setCreatedDate(mock(Timestamp.class));
        ancDiagnosis.setDateOfDeath(mock(Date.class));
        ancDiagnosis.setDeleted(true);
        ancDiagnosis.setExternalInvestigation("External Investigation");
        ancDiagnosis.setHighRiskCondition("High Risk Condition");
        ancDiagnosis.setHighRiskStatus("High Risk Status");
        ancDiagnosis.setID(1L);
        ancDiagnosis.setIsMaternalDeath(true);
        ancDiagnosis.setLastModDate(mock(Timestamp.class));
        ancDiagnosis.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancDiagnosis.setOtherCurrPregComplication("Other Curr Preg Complication");
        ancDiagnosis.setParkingPlaceID(1);
        ancDiagnosis.setPlaceOfDeath("Place Of Death");
        ancDiagnosis.setPrescriptionID(1L);
        ancDiagnosis.setProcessed("Processed");
        ancDiagnosis.setProviderServiceMapID(1);
        ancDiagnosis.setReservedForChange("Reserved For Change");
        ancDiagnosis.setSpecialistDiagnosis("Specialist Diagnosis");
        ancDiagnosis.setSyncedBy("Synced By");
        ancDiagnosis.setSyncedDate(mock(Timestamp.class));
        ancDiagnosis.setVanID(1);
        ancDiagnosis.setVanSerialNo(1L);
        ancDiagnosis.setVehicalNo("Vehical No");
        ancDiagnosis.setVisitCode(1L);

        // Act
        int actualUpdateBenANCDiagnosisResult = aNCDoctorServiceImpl.updateBenANCDiagnosis(ancDiagnosis);

        // Assert
        verify(aNCDiagnosisRepo).getANCDiagnosisStatus(isA(Long.class), isA(Long.class), isA(Long.class));
        verify(aNCDiagnosisRepo).updateANCDiagnosis(eq("High Risk Status"), eq("High Risk Condition"), eq(""),
                isA(Boolean.class), eq("Place Of Death"), isA(Date.class), eq("Cause Of Death"),
                eq("Jan 1, 2020 8:00am GMT+0100"), eq("U"), isA(Long.class), isA(Long.class),
                eq("Other Curr Preg Complication"));
        assertEquals("", ancDiagnosis.getComplicationOfCurrentPregnancy());
        assertEquals("U", ancDiagnosis.getProcessed());
        assertEquals(0, actualUpdateBenANCDiagnosisResult);
    }

    @Test
    void testGettersAndSetters() {
        ANCDoctorServiceImpl ancDoctorServiceImpl = new ANCDoctorServiceImpl();

        // Act
        ancDoctorServiceImpl.setAncDiagnosisRepo(mock(ANCDiagnosisRepo.class));
        ancDoctorServiceImpl.setPrescriptionDetailRepo(mock(PrescriptionDetailRepo.class));
    }
}
