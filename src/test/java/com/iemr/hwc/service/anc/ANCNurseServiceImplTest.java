package com.iemr.hwc.service.anc;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.anc.ANCCareDetails;
import com.iemr.hwc.data.anc.ANCWomenVaccineDetail;
import com.iemr.hwc.data.anc.BenAdherence;
import com.iemr.hwc.data.anc.SysObstetricExamination;
import com.iemr.hwc.data.anc.WrapperAncImmunization;
import com.iemr.hwc.data.anc.WrapperBenInvestigationANC;
import com.iemr.hwc.data.quickConsultation.LabTestOrderDetail;
import com.iemr.hwc.repo.nurse.anc.ANCCareRepo;
import com.iemr.hwc.repo.nurse.anc.ANCWomenVaccineRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.nurse.anc.SysObstetricExaminationRepo;
import com.iemr.hwc.repo.quickConsultation.LabTestOrderDetailRepo;

@ExtendWith(MockitoExtension.class)
class ANCNurseServiceImplTest {
    @Mock
    private ANCCareRepo aNCCareRepo;

    @InjectMocks
    private ANCNurseServiceImpl aNCNurseServiceImpl;

    @Mock
    private ANCWomenVaccineRepo aNCWomenVaccineRepo;

    @Mock
    private BenAdherenceRepo benAdherenceRepo;

    @Mock
    private LabTestOrderDetailRepo labTestOrderDetailRepo;

    @Mock
    private SysObstetricExaminationRepo sysObstetricExaminationRepo;

    @Test
    void testSaveBeneficiaryANCDetails() {
        // Arrange
        ANCCareDetails ancCareDetails = new ANCCareDetails();
        ancCareDetails.setAbortions_A((short) 1);
        ancCareDetails.setBenVisitID(1L);
        ancCareDetails.setBeneficiaryRegID(1L);
        ancCareDetails.setBloodGroup("Blood Group");
        ancCareDetails.setComolaintType("Comolaint Type");
        ancCareDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancCareDetails.setCreatedDate(mock(Timestamp.class));
        ancCareDetails.setDeleted(true);
        ancCareDetails.setDescription("The characteristics of someone or something");
        ancCareDetails.setDuration("Duration");
        ancCareDetails.setExpDelDt("Exp Del Dt");
        ancCareDetails.setExpectedDateofDelivery(mock(Date.class));
        ancCareDetails.setGestationalAgeOrPeriodofAmenorrhea_POA((short) 1);
        ancCareDetails.setGravida_G((short) 1);
        ancCareDetails.setID(1L);
        ancCareDetails.setLastMenstrualPeriod_LMP(mock(Date.class));
        ancCareDetails.setLastModDate(mock(Timestamp.class));
        ancCareDetails.setLivebirths_L((short) 1);
        ancCareDetails.setLmpDate("2020-03-01");
        ancCareDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancCareDetails.setPara((short) 1);
        ancCareDetails.setParkingPlaceID(1);
        ancCareDetails.setPretermDeliveries_P((short) 1);
        ancCareDetails.setPrimiGravida(true);
        ancCareDetails.setProcessed("Processed");
        ancCareDetails.setProviderServiceMapID(1);
        ancCareDetails.setReservedForChange("Reserved For Change");
        ancCareDetails.setStillBirth(1);
        ancCareDetails.setSyncedBy("Synced By");
        ancCareDetails.setSyncedDate(mock(Timestamp.class));
        ancCareDetails.setTermDeliveries_T((short) 1);
        ancCareDetails.setTrimesterNumber((short) 1);
        ancCareDetails.setVanID(1);
        ancCareDetails.setVanSerialNo(1L);
        ancCareDetails.setVehicalNo("Vehical No");
        ancCareDetails.setVisitCode(1L);
        ancCareDetails.setVisitNo((short) 1);
        when(aNCCareRepo.save(Mockito.<ANCCareDetails>any())).thenReturn(ancCareDetails);

        ANCCareDetails ancCareDetails2 = new ANCCareDetails();
        ancCareDetails2.setAbortions_A((short) 1);
        ancCareDetails2.setBenVisitID(1L);
        ancCareDetails2.setBeneficiaryRegID(1L);
        ancCareDetails2.setBloodGroup("Blood Group");
        ancCareDetails2.setComolaintType("Comolaint Type");
        ancCareDetails2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancCareDetails2.setCreatedDate(mock(Timestamp.class));
        ancCareDetails2.setDeleted(true);
        ancCareDetails2.setDescription("The characteristics of someone or something");
        ancCareDetails2.setDuration("Duration");
        ancCareDetails2.setExpDelDt("Exp Del Dt");
        ancCareDetails2.setExpectedDateofDelivery(mock(Date.class));
        ancCareDetails2.setGestationalAgeOrPeriodofAmenorrhea_POA((short) 1);
        ancCareDetails2.setGravida_G((short) 1);
        ancCareDetails2.setID(1L);
        ancCareDetails2.setLastMenstrualPeriod_LMP(mock(Date.class));
        ancCareDetails2.setLastModDate(mock(Timestamp.class));
        ancCareDetails2.setLivebirths_L((short) 1);
        ancCareDetails2.setLmpDate("2020-03-01");
        ancCareDetails2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancCareDetails2.setPara((short) 1);
        ancCareDetails2.setParkingPlaceID(1);
        ancCareDetails2.setPretermDeliveries_P((short) 1);
        ancCareDetails2.setPrimiGravida(true);
        ancCareDetails2.setProcessed("Processed");
        ancCareDetails2.setProviderServiceMapID(1);
        ancCareDetails2.setReservedForChange("Reserved For Change");
        ancCareDetails2.setStillBirth(1);
        ancCareDetails2.setSyncedBy("Synced By");
        ancCareDetails2.setSyncedDate(mock(Timestamp.class));
        ancCareDetails2.setTermDeliveries_T((short) 1);
        ancCareDetails2.setTrimesterNumber((short) 1);
        ancCareDetails2.setVanID(1);
        ancCareDetails2.setVanSerialNo(1L);
        ancCareDetails2.setVehicalNo("Vehical No");
        ancCareDetails2.setVisitCode(1L);
        ancCareDetails2.setVisitNo((short) 1);

        // Act
        Long actualSaveBeneficiaryANCDetailsResult = aNCNurseServiceImpl.saveBeneficiaryANCDetails(ancCareDetails2);

        // Assert
        verify(aNCCareRepo).save(isA(ANCCareDetails.class));
        assertEquals(1L, actualSaveBeneficiaryANCDetailsResult.longValue());
    }

    @Test
    void testSaveBeneficiaryANCDetails2() {
        // Arrange
        ANCCareDetails ancCareDetails = mock(ANCCareDetails.class);
        when(ancCareDetails.getID()).thenReturn(1L);
        doNothing().when(ancCareDetails).setAbortions_A(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setBenVisitID(Mockito.<Long>any());
        doNothing().when(ancCareDetails).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(ancCareDetails).setBloodGroup(Mockito.<String>any());
        doNothing().when(ancCareDetails).setComolaintType(Mockito.<String>any());
        doNothing().when(ancCareDetails).setCreatedBy(Mockito.<String>any());
        doNothing().when(ancCareDetails).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetails).setDeleted(Mockito.<Boolean>any());
        doNothing().when(ancCareDetails).setDescription(Mockito.<String>any());
        doNothing().when(ancCareDetails).setDuration(Mockito.<String>any());
        doNothing().when(ancCareDetails).setExpDelDt(Mockito.<String>any());
        doNothing().when(ancCareDetails).setExpectedDateofDelivery(Mockito.<Date>any());
        doNothing().when(ancCareDetails).setGestationalAgeOrPeriodofAmenorrhea_POA(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setGravida_G(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setID(Mockito.<Long>any());
        doNothing().when(ancCareDetails).setLastMenstrualPeriod_LMP(Mockito.<Date>any());
        doNothing().when(ancCareDetails).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetails).setLivebirths_L(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setLmpDate(Mockito.<String>any());
        doNothing().when(ancCareDetails).setModifiedBy(Mockito.<String>any());
        doNothing().when(ancCareDetails).setPara(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(ancCareDetails).setPretermDeliveries_P(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setPrimiGravida(Mockito.<Boolean>any());
        doNothing().when(ancCareDetails).setProcessed(Mockito.<String>any());
        doNothing().when(ancCareDetails).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(ancCareDetails).setReservedForChange(Mockito.<String>any());
        doNothing().when(ancCareDetails).setStillBirth(Mockito.<Integer>any());
        doNothing().when(ancCareDetails).setSyncedBy(Mockito.<String>any());
        doNothing().when(ancCareDetails).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetails).setTermDeliveries_T(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setTrimesterNumber(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setVanID(Mockito.<Integer>any());
        doNothing().when(ancCareDetails).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(ancCareDetails).setVehicalNo(Mockito.<String>any());
        doNothing().when(ancCareDetails).setVisitCode(Mockito.<Long>any());
        doNothing().when(ancCareDetails).setVisitNo(Mockito.<Short>any());
        ancCareDetails.setAbortions_A((short) 1);
        ancCareDetails.setBenVisitID(1L);
        ancCareDetails.setBeneficiaryRegID(1L);
        ancCareDetails.setBloodGroup("Blood Group");
        ancCareDetails.setComolaintType("Comolaint Type");
        ancCareDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancCareDetails.setCreatedDate(mock(Timestamp.class));
        ancCareDetails.setDeleted(true);
        ancCareDetails.setDescription("The characteristics of someone or something");
        ancCareDetails.setDuration("Duration");
        ancCareDetails.setExpDelDt("Exp Del Dt");
        ancCareDetails.setExpectedDateofDelivery(mock(Date.class));
        ancCareDetails.setGestationalAgeOrPeriodofAmenorrhea_POA((short) 1);
        ancCareDetails.setGravida_G((short) 1);
        ancCareDetails.setID(1L);
        ancCareDetails.setLastMenstrualPeriod_LMP(mock(Date.class));
        ancCareDetails.setLastModDate(mock(Timestamp.class));
        ancCareDetails.setLivebirths_L((short) 1);
        ancCareDetails.setLmpDate("2020-03-01");
        ancCareDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancCareDetails.setPara((short) 1);
        ancCareDetails.setParkingPlaceID(1);
        ancCareDetails.setPretermDeliveries_P((short) 1);
        ancCareDetails.setPrimiGravida(true);
        ancCareDetails.setProcessed("Processed");
        ancCareDetails.setProviderServiceMapID(1);
        ancCareDetails.setReservedForChange("Reserved For Change");
        ancCareDetails.setStillBirth(1);
        ancCareDetails.setSyncedBy("Synced By");
        ancCareDetails.setSyncedDate(mock(Timestamp.class));
        ancCareDetails.setTermDeliveries_T((short) 1);
        ancCareDetails.setTrimesterNumber((short) 1);
        ancCareDetails.setVanID(1);
        ancCareDetails.setVanSerialNo(1L);
        ancCareDetails.setVehicalNo("Vehical No");
        ancCareDetails.setVisitCode(1L);
        ancCareDetails.setVisitNo((short) 1);
        when(aNCCareRepo.save(Mockito.<ANCCareDetails>any())).thenReturn(ancCareDetails);

        ANCCareDetails ancCareDetails2 = new ANCCareDetails();
        ancCareDetails2.setAbortions_A((short) 1);
        ancCareDetails2.setBenVisitID(1L);
        ancCareDetails2.setBeneficiaryRegID(1L);
        ancCareDetails2.setBloodGroup("Blood Group");
        ancCareDetails2.setComolaintType("Comolaint Type");
        ancCareDetails2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancCareDetails2.setCreatedDate(mock(Timestamp.class));
        ancCareDetails2.setDeleted(true);
        ancCareDetails2.setDescription("The characteristics of someone or something");
        ancCareDetails2.setDuration("Duration");
        ancCareDetails2.setExpDelDt("Exp Del Dt");
        ancCareDetails2.setExpectedDateofDelivery(mock(Date.class));
        ancCareDetails2.setGestationalAgeOrPeriodofAmenorrhea_POA((short) 1);
        ancCareDetails2.setGravida_G((short) 1);
        ancCareDetails2.setID(1L);
        ancCareDetails2.setLastMenstrualPeriod_LMP(mock(Date.class));
        ancCareDetails2.setLastModDate(mock(Timestamp.class));
        ancCareDetails2.setLivebirths_L((short) 1);
        ancCareDetails2.setLmpDate("2020-03-01");
        ancCareDetails2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancCareDetails2.setPara((short) 1);
        ancCareDetails2.setParkingPlaceID(1);
        ancCareDetails2.setPretermDeliveries_P((short) 1);
        ancCareDetails2.setPrimiGravida(true);
        ancCareDetails2.setProcessed("Processed");
        ancCareDetails2.setProviderServiceMapID(1);
        ancCareDetails2.setReservedForChange("Reserved For Change");
        ancCareDetails2.setStillBirth(1);
        ancCareDetails2.setSyncedBy("Synced By");
        ancCareDetails2.setSyncedDate(mock(Timestamp.class));
        ancCareDetails2.setTermDeliveries_T((short) 1);
        ancCareDetails2.setTrimesterNumber((short) 1);
        ancCareDetails2.setVanID(1);
        ancCareDetails2.setVanSerialNo(1L);
        ancCareDetails2.setVehicalNo("Vehical No");
        ancCareDetails2.setVisitCode(1L);
        ancCareDetails2.setVisitNo((short) 1);

        // Act
        Long actualSaveBeneficiaryANCDetailsResult = aNCNurseServiceImpl.saveBeneficiaryANCDetails(ancCareDetails2);

        // Assert
        verify(ancCareDetails, atLeast(1)).getID();
        verify(ancCareDetails).setAbortions_A(isA(Short.class));
        verify(ancCareDetails).setBenVisitID(isA(Long.class));
        verify(ancCareDetails).setBeneficiaryRegID(isA(Long.class));
        verify(ancCareDetails).setBloodGroup(eq("Blood Group"));
        verify(ancCareDetails).setComolaintType(eq("Comolaint Type"));
        verify(ancCareDetails).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(ancCareDetails).setCreatedDate(isA(Timestamp.class));
        verify(ancCareDetails).setDeleted(isA(Boolean.class));
        verify(ancCareDetails).setDescription(eq("The characteristics of someone or something"));
        verify(ancCareDetails).setDuration(eq("Duration"));
        verify(ancCareDetails).setExpDelDt(eq("Exp Del Dt"));
        verify(ancCareDetails).setExpectedDateofDelivery(isA(Date.class));
        verify(ancCareDetails).setGestationalAgeOrPeriodofAmenorrhea_POA(isA(Short.class));
        verify(ancCareDetails).setGravida_G(isA(Short.class));
        verify(ancCareDetails).setID(isA(Long.class));
        verify(ancCareDetails).setLastMenstrualPeriod_LMP(isA(Date.class));
        verify(ancCareDetails).setLastModDate(isA(Timestamp.class));
        verify(ancCareDetails).setLivebirths_L(isA(Short.class));
        verify(ancCareDetails).setLmpDate(eq("2020-03-01"));
        verify(ancCareDetails).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(ancCareDetails).setPara(isA(Short.class));
        verify(ancCareDetails).setParkingPlaceID(isA(Integer.class));
        verify(ancCareDetails).setPretermDeliveries_P(isA(Short.class));
        verify(ancCareDetails).setPrimiGravida(isA(Boolean.class));
        verify(ancCareDetails).setProcessed(eq("Processed"));
        verify(ancCareDetails).setProviderServiceMapID(isA(Integer.class));
        verify(ancCareDetails).setReservedForChange(eq("Reserved For Change"));
        verify(ancCareDetails).setStillBirth(isA(Integer.class));
        verify(ancCareDetails).setSyncedBy(eq("Synced By"));
        verify(ancCareDetails).setSyncedDate(isA(Timestamp.class));
        verify(ancCareDetails).setTermDeliveries_T(isA(Short.class));
        verify(ancCareDetails).setTrimesterNumber(isA(Short.class));
        verify(ancCareDetails).setVanID(isA(Integer.class));
        verify(ancCareDetails).setVanSerialNo(isA(Long.class));
        verify(ancCareDetails).setVehicalNo(eq("Vehical No"));
        verify(ancCareDetails).setVisitCode(isA(Long.class));
        verify(ancCareDetails).setVisitNo(isA(Short.class));
        verify(aNCCareRepo).save(isA(ANCCareDetails.class));
        assertEquals(1L, actualSaveBeneficiaryANCDetailsResult.longValue());
    }

    @Test
    void testSaveBeneficiaryANCDetails3() {
        // Arrange
        ANCCareDetails ancCareDetails = mock(ANCCareDetails.class);
        when(ancCareDetails.getID()).thenReturn(0L);
        doNothing().when(ancCareDetails).setAbortions_A(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setBenVisitID(Mockito.<Long>any());
        doNothing().when(ancCareDetails).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(ancCareDetails).setBloodGroup(Mockito.<String>any());
        doNothing().when(ancCareDetails).setComolaintType(Mockito.<String>any());
        doNothing().when(ancCareDetails).setCreatedBy(Mockito.<String>any());
        doNothing().when(ancCareDetails).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetails).setDeleted(Mockito.<Boolean>any());
        doNothing().when(ancCareDetails).setDescription(Mockito.<String>any());
        doNothing().when(ancCareDetails).setDuration(Mockito.<String>any());
        doNothing().when(ancCareDetails).setExpDelDt(Mockito.<String>any());
        doNothing().when(ancCareDetails).setExpectedDateofDelivery(Mockito.<Date>any());
        doNothing().when(ancCareDetails).setGestationalAgeOrPeriodofAmenorrhea_POA(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setGravida_G(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setID(Mockito.<Long>any());
        doNothing().when(ancCareDetails).setLastMenstrualPeriod_LMP(Mockito.<Date>any());
        doNothing().when(ancCareDetails).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetails).setLivebirths_L(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setLmpDate(Mockito.<String>any());
        doNothing().when(ancCareDetails).setModifiedBy(Mockito.<String>any());
        doNothing().when(ancCareDetails).setPara(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(ancCareDetails).setPretermDeliveries_P(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setPrimiGravida(Mockito.<Boolean>any());
        doNothing().when(ancCareDetails).setProcessed(Mockito.<String>any());
        doNothing().when(ancCareDetails).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(ancCareDetails).setReservedForChange(Mockito.<String>any());
        doNothing().when(ancCareDetails).setStillBirth(Mockito.<Integer>any());
        doNothing().when(ancCareDetails).setSyncedBy(Mockito.<String>any());
        doNothing().when(ancCareDetails).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetails).setTermDeliveries_T(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setTrimesterNumber(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setVanID(Mockito.<Integer>any());
        doNothing().when(ancCareDetails).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(ancCareDetails).setVehicalNo(Mockito.<String>any());
        doNothing().when(ancCareDetails).setVisitCode(Mockito.<Long>any());
        doNothing().when(ancCareDetails).setVisitNo(Mockito.<Short>any());
        ancCareDetails.setAbortions_A((short) 1);
        ancCareDetails.setBenVisitID(1L);
        ancCareDetails.setBeneficiaryRegID(1L);
        ancCareDetails.setBloodGroup("Blood Group");
        ancCareDetails.setComolaintType("Comolaint Type");
        ancCareDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancCareDetails.setCreatedDate(mock(Timestamp.class));
        ancCareDetails.setDeleted(true);
        ancCareDetails.setDescription("The characteristics of someone or something");
        ancCareDetails.setDuration("Duration");
        ancCareDetails.setExpDelDt("Exp Del Dt");
        ancCareDetails.setExpectedDateofDelivery(mock(Date.class));
        ancCareDetails.setGestationalAgeOrPeriodofAmenorrhea_POA((short) 1);
        ancCareDetails.setGravida_G((short) 1);
        ancCareDetails.setID(1L);
        ancCareDetails.setLastMenstrualPeriod_LMP(mock(Date.class));
        ancCareDetails.setLastModDate(mock(Timestamp.class));
        ancCareDetails.setLivebirths_L((short) 1);
        ancCareDetails.setLmpDate("2020-03-01");
        ancCareDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancCareDetails.setPara((short) 1);
        ancCareDetails.setParkingPlaceID(1);
        ancCareDetails.setPretermDeliveries_P((short) 1);
        ancCareDetails.setPrimiGravida(true);
        ancCareDetails.setProcessed("Processed");
        ancCareDetails.setProviderServiceMapID(1);
        ancCareDetails.setReservedForChange("Reserved For Change");
        ancCareDetails.setStillBirth(1);
        ancCareDetails.setSyncedBy("Synced By");
        ancCareDetails.setSyncedDate(mock(Timestamp.class));
        ancCareDetails.setTermDeliveries_T((short) 1);
        ancCareDetails.setTrimesterNumber((short) 1);
        ancCareDetails.setVanID(1);
        ancCareDetails.setVanSerialNo(1L);
        ancCareDetails.setVehicalNo("Vehical No");
        ancCareDetails.setVisitCode(1L);
        ancCareDetails.setVisitNo((short) 1);
        when(aNCCareRepo.save(Mockito.<ANCCareDetails>any())).thenReturn(ancCareDetails);

        ANCCareDetails ancCareDetails2 = new ANCCareDetails();
        ancCareDetails2.setAbortions_A((short) 1);
        ancCareDetails2.setBenVisitID(1L);
        ancCareDetails2.setBeneficiaryRegID(1L);
        ancCareDetails2.setBloodGroup("Blood Group");
        ancCareDetails2.setComolaintType("Comolaint Type");
        ancCareDetails2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancCareDetails2.setCreatedDate(mock(Timestamp.class));
        ancCareDetails2.setDeleted(true);
        ancCareDetails2.setDescription("The characteristics of someone or something");
        ancCareDetails2.setDuration("Duration");
        ancCareDetails2.setExpDelDt("Exp Del Dt");
        ancCareDetails2.setExpectedDateofDelivery(mock(Date.class));
        ancCareDetails2.setGestationalAgeOrPeriodofAmenorrhea_POA((short) 1);
        ancCareDetails2.setGravida_G((short) 1);
        ancCareDetails2.setID(1L);
        ancCareDetails2.setLastMenstrualPeriod_LMP(mock(Date.class));
        ancCareDetails2.setLastModDate(mock(Timestamp.class));
        ancCareDetails2.setLivebirths_L((short) 1);
        ancCareDetails2.setLmpDate("2020-03-01");
        ancCareDetails2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancCareDetails2.setPara((short) 1);
        ancCareDetails2.setParkingPlaceID(1);
        ancCareDetails2.setPretermDeliveries_P((short) 1);
        ancCareDetails2.setPrimiGravida(true);
        ancCareDetails2.setProcessed("Processed");
        ancCareDetails2.setProviderServiceMapID(1);
        ancCareDetails2.setReservedForChange("Reserved For Change");
        ancCareDetails2.setStillBirth(1);
        ancCareDetails2.setSyncedBy("Synced By");
        ancCareDetails2.setSyncedDate(mock(Timestamp.class));
        ancCareDetails2.setTermDeliveries_T((short) 1);
        ancCareDetails2.setTrimesterNumber((short) 1);
        ancCareDetails2.setVanID(1);
        ancCareDetails2.setVanSerialNo(1L);
        ancCareDetails2.setVehicalNo("Vehical No");
        ancCareDetails2.setVisitCode(1L);
        ancCareDetails2.setVisitNo((short) 1);

        // Act
        Long actualSaveBeneficiaryANCDetailsResult = aNCNurseServiceImpl.saveBeneficiaryANCDetails(ancCareDetails2);

        // Assert
        verify(ancCareDetails).getID();
        verify(ancCareDetails).setAbortions_A(isA(Short.class));
        verify(ancCareDetails).setBenVisitID(isA(Long.class));
        verify(ancCareDetails).setBeneficiaryRegID(isA(Long.class));
        verify(ancCareDetails).setBloodGroup(eq("Blood Group"));
        verify(ancCareDetails).setComolaintType(eq("Comolaint Type"));
        verify(ancCareDetails).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(ancCareDetails).setCreatedDate(isA(Timestamp.class));
        verify(ancCareDetails).setDeleted(isA(Boolean.class));
        verify(ancCareDetails).setDescription(eq("The characteristics of someone or something"));
        verify(ancCareDetails).setDuration(eq("Duration"));
        verify(ancCareDetails).setExpDelDt(eq("Exp Del Dt"));
        verify(ancCareDetails).setExpectedDateofDelivery(isA(Date.class));
        verify(ancCareDetails).setGestationalAgeOrPeriodofAmenorrhea_POA(isA(Short.class));
        verify(ancCareDetails).setGravida_G(isA(Short.class));
        verify(ancCareDetails).setID(isA(Long.class));
        verify(ancCareDetails).setLastMenstrualPeriod_LMP(isA(Date.class));
        verify(ancCareDetails).setLastModDate(isA(Timestamp.class));
        verify(ancCareDetails).setLivebirths_L(isA(Short.class));
        verify(ancCareDetails).setLmpDate(eq("2020-03-01"));
        verify(ancCareDetails).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(ancCareDetails).setPara(isA(Short.class));
        verify(ancCareDetails).setParkingPlaceID(isA(Integer.class));
        verify(ancCareDetails).setPretermDeliveries_P(isA(Short.class));
        verify(ancCareDetails).setPrimiGravida(isA(Boolean.class));
        verify(ancCareDetails).setProcessed(eq("Processed"));
        verify(ancCareDetails).setProviderServiceMapID(isA(Integer.class));
        verify(ancCareDetails).setReservedForChange(eq("Reserved For Change"));
        verify(ancCareDetails).setStillBirth(isA(Integer.class));
        verify(ancCareDetails).setSyncedBy(eq("Synced By"));
        verify(ancCareDetails).setSyncedDate(isA(Timestamp.class));
        verify(ancCareDetails).setTermDeliveries_T(isA(Short.class));
        verify(ancCareDetails).setTrimesterNumber(isA(Short.class));
        verify(ancCareDetails).setVanID(isA(Integer.class));
        verify(ancCareDetails).setVanSerialNo(isA(Long.class));
        verify(ancCareDetails).setVehicalNo(eq("Vehical No"));
        verify(ancCareDetails).setVisitCode(isA(Long.class));
        verify(ancCareDetails).setVisitNo(isA(Short.class));
        verify(aNCCareRepo).save(isA(ANCCareDetails.class));
        assertNull(actualSaveBeneficiaryANCDetailsResult);
    }

    @Test
    void testSaveANCWomenVaccineDetails() {
        // Arrange
        when(aNCWomenVaccineRepo.saveAll(Mockito.<Iterable<ANCWomenVaccineDetail>>any())).thenReturn(new ArrayList<>());

        // Act
        Long actualSaveANCWomenVaccineDetailsResult = aNCNurseServiceImpl.saveANCWomenVaccineDetails(new ArrayList<>());

        // Assert
        verify(aNCWomenVaccineRepo).saveAll(isA(Iterable.class));
        assertNull(actualSaveANCWomenVaccineDetailsResult);
    }

    @Test
    void testSaveANCWomenVaccineDetails2() {
        // Arrange
        ANCWomenVaccineDetail ancWomenVaccineDetail = new ANCWomenVaccineDetail();
        ancWomenVaccineDetail.setBenVisitID(1L);
        ancWomenVaccineDetail.setBeneficiaryRegID(1L);
        ancWomenVaccineDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancWomenVaccineDetail.setCreatedDate(mock(Timestamp.class));
        ancWomenVaccineDetail.setDeleted(true);
        ancWomenVaccineDetail.setID(1L);
        ancWomenVaccineDetail.setLastModDate(mock(Timestamp.class));
        ancWomenVaccineDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancWomenVaccineDetail.setParkingPlaceID(1);
        ancWomenVaccineDetail.setProcessed("Processed");
        ancWomenVaccineDetail.setProviderServiceMapID(1);
        ancWomenVaccineDetail.setReceivedDate(mock(Date.class));
        ancWomenVaccineDetail.setReceivedFacilityName("Received Facility Name");
        ancWomenVaccineDetail.setReservedForChange("Reserved For Change");
        ancWomenVaccineDetail.setStatus("Status");
        ancWomenVaccineDetail.setSyncedBy("Synced By");
        ancWomenVaccineDetail.setSyncedDate(mock(Timestamp.class));
        ancWomenVaccineDetail.setVaccineName("Vaccine Name");
        ancWomenVaccineDetail.setVanID(1);
        ancWomenVaccineDetail.setVanSerialNo(1L);
        ancWomenVaccineDetail.setVehicalNo("Vehical No");
        ancWomenVaccineDetail.setVisitCode(1L);

        ArrayList<ANCWomenVaccineDetail> ancWomenVaccineDetailList = new ArrayList<>();
        ancWomenVaccineDetailList.add(ancWomenVaccineDetail);
        when(aNCWomenVaccineRepo.saveAll(Mockito.<Iterable<ANCWomenVaccineDetail>>any()))
                .thenReturn(ancWomenVaccineDetailList);

        // Act
        Long actualSaveANCWomenVaccineDetailsResult = aNCNurseServiceImpl.saveANCWomenVaccineDetails(new ArrayList<>());

        // Assert
        verify(aNCWomenVaccineRepo).saveAll(isA(Iterable.class));
        assertEquals(1L, actualSaveANCWomenVaccineDetailsResult.longValue());
    }

    @Test
    void testSaveANCWomenVaccineDetails3() {
        // Arrange
        when(aNCWomenVaccineRepo.saveAll(Mockito.<Iterable<ANCWomenVaccineDetail>>any())).thenReturn(new ArrayList<>());

        ANCWomenVaccineDetail ancWomenVaccineDetail = new ANCWomenVaccineDetail();
        ancWomenVaccineDetail.setBenVisitID(1L);
        ancWomenVaccineDetail.setBeneficiaryRegID(1L);
        ancWomenVaccineDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancWomenVaccineDetail.setCreatedDate(mock(Timestamp.class));
        ancWomenVaccineDetail.setDeleted(true);
        ancWomenVaccineDetail.setID(1L);
        ancWomenVaccineDetail.setLastModDate(mock(Timestamp.class));
        ancWomenVaccineDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancWomenVaccineDetail.setParkingPlaceID(1);
        ancWomenVaccineDetail.setProcessed("Processed");
        ancWomenVaccineDetail.setProviderServiceMapID(1);
        ancWomenVaccineDetail.setReceivedDate(mock(Date.class));
        ancWomenVaccineDetail.setReceivedFacilityName("Received Facility Name");
        ancWomenVaccineDetail.setReservedForChange("Reserved For Change");
        ancWomenVaccineDetail.setStatus("Status");
        ancWomenVaccineDetail.setSyncedBy("Synced By");
        ancWomenVaccineDetail.setSyncedDate(mock(Timestamp.class));
        ancWomenVaccineDetail.setVaccineName("Vaccine Name");
        ancWomenVaccineDetail.setVanID(1);
        ancWomenVaccineDetail.setVanSerialNo(1L);
        ancWomenVaccineDetail.setVehicalNo("Vehical No");
        ancWomenVaccineDetail.setVisitCode(1L);

        ArrayList<ANCWomenVaccineDetail> ancWomenVaccineDetails = new ArrayList<>();
        ancWomenVaccineDetails.add(ancWomenVaccineDetail);

        // Act
        Long actualSaveANCWomenVaccineDetailsResult = aNCNurseServiceImpl
                .saveANCWomenVaccineDetails(ancWomenVaccineDetails);

        // Assert
        verify(aNCWomenVaccineRepo).saveAll(isA(Iterable.class));
        assertNull(actualSaveANCWomenVaccineDetailsResult);
    }

    @Test
    void testSaveANCWomenVaccineDetails4() {
        // Arrange
        when(aNCWomenVaccineRepo.saveAll(Mockito.<Iterable<ANCWomenVaccineDetail>>any())).thenReturn(new ArrayList<>());

        ANCWomenVaccineDetail ancWomenVaccineDetail = new ANCWomenVaccineDetail();
        ancWomenVaccineDetail.setBenVisitID(1L);
        ancWomenVaccineDetail.setBeneficiaryRegID(1L);
        ancWomenVaccineDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancWomenVaccineDetail.setCreatedDate(mock(Timestamp.class));
        ancWomenVaccineDetail.setDeleted(true);
        ancWomenVaccineDetail.setID(1L);
        ancWomenVaccineDetail.setLastModDate(mock(Timestamp.class));
        ancWomenVaccineDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancWomenVaccineDetail.setParkingPlaceID(1);
        ancWomenVaccineDetail.setProcessed("Processed");
        ancWomenVaccineDetail.setProviderServiceMapID(1);
        ancWomenVaccineDetail.setReceivedDate(mock(Date.class));
        ancWomenVaccineDetail.setReceivedFacilityName("Received Facility Name");
        ancWomenVaccineDetail.setReservedForChange("Reserved For Change");
        ancWomenVaccineDetail.setStatus("Status");
        ancWomenVaccineDetail.setSyncedBy("Synced By");
        ancWomenVaccineDetail.setSyncedDate(mock(Timestamp.class));
        ancWomenVaccineDetail.setVaccineName("Vaccine Name");
        ancWomenVaccineDetail.setVanID(1);
        ancWomenVaccineDetail.setVanSerialNo(1L);
        ancWomenVaccineDetail.setVehicalNo("Vehical No");
        ancWomenVaccineDetail.setVisitCode(1L);

        ANCWomenVaccineDetail ancWomenVaccineDetail2 = new ANCWomenVaccineDetail();
        ancWomenVaccineDetail2.setBenVisitID(2L);
        ancWomenVaccineDetail2.setBeneficiaryRegID(2L);
        ancWomenVaccineDetail2.setCreatedBy("Created By");
        ancWomenVaccineDetail2.setCreatedDate(mock(Timestamp.class));
        ancWomenVaccineDetail2.setDeleted(false);
        ancWomenVaccineDetail2.setID(2L);
        ancWomenVaccineDetail2.setLastModDate(mock(Timestamp.class));
        ancWomenVaccineDetail2.setModifiedBy("Modified By");
        ancWomenVaccineDetail2.setParkingPlaceID(2);
        ancWomenVaccineDetail2.setProcessed("java.lang.String");
        ancWomenVaccineDetail2.setProviderServiceMapID(2);
        ancWomenVaccineDetail2.setReceivedDate(mock(Date.class));
        ancWomenVaccineDetail2.setReceivedFacilityName("java.lang.String");
        ancWomenVaccineDetail2.setReservedForChange("java.lang.String");
        ancWomenVaccineDetail2.setStatus("java.lang.String");
        ancWomenVaccineDetail2.setSyncedBy("java.lang.String");
        ancWomenVaccineDetail2.setSyncedDate(mock(Timestamp.class));
        ancWomenVaccineDetail2.setVaccineName("java.lang.String");
        ancWomenVaccineDetail2.setVanID(2);
        ancWomenVaccineDetail2.setVanSerialNo(0L);
        ancWomenVaccineDetail2.setVehicalNo("java.lang.String");
        ancWomenVaccineDetail2.setVisitCode(0L);

        ArrayList<ANCWomenVaccineDetail> ancWomenVaccineDetails = new ArrayList<>();
        ancWomenVaccineDetails.add(ancWomenVaccineDetail2);
        ancWomenVaccineDetails.add(ancWomenVaccineDetail);

        // Act
        Long actualSaveANCWomenVaccineDetailsResult = aNCNurseServiceImpl
                .saveANCWomenVaccineDetails(ancWomenVaccineDetails);

        // Assert
        verify(aNCWomenVaccineRepo).saveAll(isA(Iterable.class));
        assertNull(actualSaveANCWomenVaccineDetailsResult);
    }

    @Test
    void testSaveANCWomenVaccineDetails5() {
        // Arrange
        ANCWomenVaccineDetail ancWomenVaccineDetail = mock(ANCWomenVaccineDetail.class);
        when(ancWomenVaccineDetail.getID()).thenReturn(1L);
        doNothing().when(ancWomenVaccineDetail).setBenVisitID(Mockito.<Long>any());
        doNothing().when(ancWomenVaccineDetail).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(ancWomenVaccineDetail).setCreatedBy(Mockito.<String>any());
        doNothing().when(ancWomenVaccineDetail).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(ancWomenVaccineDetail).setDeleted(Mockito.<Boolean>any());
        doNothing().when(ancWomenVaccineDetail).setID(Mockito.<Long>any());
        doNothing().when(ancWomenVaccineDetail).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(ancWomenVaccineDetail).setModifiedBy(Mockito.<String>any());
        doNothing().when(ancWomenVaccineDetail).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(ancWomenVaccineDetail).setProcessed(Mockito.<String>any());
        doNothing().when(ancWomenVaccineDetail).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(ancWomenVaccineDetail).setReceivedDate(Mockito.<Date>any());
        doNothing().when(ancWomenVaccineDetail).setReceivedFacilityName(Mockito.<String>any());
        doNothing().when(ancWomenVaccineDetail).setReservedForChange(Mockito.<String>any());
        doNothing().when(ancWomenVaccineDetail).setStatus(Mockito.<String>any());
        doNothing().when(ancWomenVaccineDetail).setSyncedBy(Mockito.<String>any());
        doNothing().when(ancWomenVaccineDetail).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(ancWomenVaccineDetail).setVaccineName(Mockito.<String>any());
        doNothing().when(ancWomenVaccineDetail).setVanID(Mockito.<Integer>any());
        doNothing().when(ancWomenVaccineDetail).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(ancWomenVaccineDetail).setVehicalNo(Mockito.<String>any());
        doNothing().when(ancWomenVaccineDetail).setVisitCode(Mockito.<Long>any());
        ancWomenVaccineDetail.setBenVisitID(1L);
        ancWomenVaccineDetail.setBeneficiaryRegID(1L);
        ancWomenVaccineDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancWomenVaccineDetail.setCreatedDate(mock(Timestamp.class));
        ancWomenVaccineDetail.setDeleted(true);
        ancWomenVaccineDetail.setID(1L);
        ancWomenVaccineDetail.setLastModDate(mock(Timestamp.class));
        ancWomenVaccineDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancWomenVaccineDetail.setParkingPlaceID(1);
        ancWomenVaccineDetail.setProcessed("Processed");
        ancWomenVaccineDetail.setProviderServiceMapID(1);
        ancWomenVaccineDetail.setReceivedDate(mock(Date.class));
        ancWomenVaccineDetail.setReceivedFacilityName("Received Facility Name");
        ancWomenVaccineDetail.setReservedForChange("Reserved For Change");
        ancWomenVaccineDetail.setStatus("Status");
        ancWomenVaccineDetail.setSyncedBy("Synced By");
        ancWomenVaccineDetail.setSyncedDate(mock(Timestamp.class));
        ancWomenVaccineDetail.setVaccineName("Vaccine Name");
        ancWomenVaccineDetail.setVanID(1);
        ancWomenVaccineDetail.setVanSerialNo(1L);
        ancWomenVaccineDetail.setVehicalNo("Vehical No");
        ancWomenVaccineDetail.setVisitCode(1L);

        ArrayList<ANCWomenVaccineDetail> ancWomenVaccineDetailList = new ArrayList<>();
        ancWomenVaccineDetailList.add(ancWomenVaccineDetail);
        when(aNCWomenVaccineRepo.saveAll(Mockito.<Iterable<ANCWomenVaccineDetail>>any()))
                .thenReturn(ancWomenVaccineDetailList);

        // Act
        Long actualSaveANCWomenVaccineDetailsResult = aNCNurseServiceImpl.saveANCWomenVaccineDetails(new ArrayList<>());

        // Assert
        verify(ancWomenVaccineDetail).getID();
        verify(ancWomenVaccineDetail).setBenVisitID(isA(Long.class));
        verify(ancWomenVaccineDetail).setBeneficiaryRegID(isA(Long.class));
        verify(ancWomenVaccineDetail).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(ancWomenVaccineDetail).setCreatedDate(isA(Timestamp.class));
        verify(ancWomenVaccineDetail).setDeleted(isA(Boolean.class));
        verify(ancWomenVaccineDetail).setID(isA(Long.class));
        verify(ancWomenVaccineDetail).setLastModDate(isA(Timestamp.class));
        verify(ancWomenVaccineDetail).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(ancWomenVaccineDetail).setParkingPlaceID(isA(Integer.class));
        verify(ancWomenVaccineDetail).setProcessed(eq("Processed"));
        verify(ancWomenVaccineDetail).setProviderServiceMapID(isA(Integer.class));
        verify(ancWomenVaccineDetail).setReceivedDate(isA(Date.class));
        verify(ancWomenVaccineDetail).setReceivedFacilityName(eq("Received Facility Name"));
        verify(ancWomenVaccineDetail).setReservedForChange(eq("Reserved For Change"));
        verify(ancWomenVaccineDetail).setStatus(eq("Status"));
        verify(ancWomenVaccineDetail).setSyncedBy(eq("Synced By"));
        verify(ancWomenVaccineDetail).setSyncedDate(isA(Timestamp.class));
        verify(ancWomenVaccineDetail).setVaccineName(eq("Vaccine Name"));
        verify(ancWomenVaccineDetail).setVanID(isA(Integer.class));
        verify(ancWomenVaccineDetail).setVanSerialNo(isA(Long.class));
        verify(ancWomenVaccineDetail).setVehicalNo(eq("Vehical No"));
        verify(ancWomenVaccineDetail).setVisitCode(isA(Long.class));
        verify(aNCWomenVaccineRepo).saveAll(isA(Iterable.class));
        assertEquals(1L, actualSaveANCWomenVaccineDetailsResult.longValue());
    }

    @Test
    void testSaveBenInvestigationFromDoc() {
        // Arrange, Act and Assert
        assertEquals(1, aNCNurseServiceImpl.saveBenInvestigationFromDoc(new WrapperBenInvestigationANC()).intValue());
        assertEquals(1,
                aNCNurseServiceImpl
                        .saveBenInvestigationFromDoc(
                                new WrapperBenInvestigationANC(1L, 1L, 1, 1L, "Jan 1, 2020 8:00am GMT+0100", new ArrayList<>()))
                        .intValue());
    }

    @Test
    void testSaveBenInvestigationFromDoc2() {
        // Arrange
        WrapperBenInvestigationANC wrapperBenInvestigationANC = mock(WrapperBenInvestigationANC.class);
        when(wrapperBenInvestigationANC.getLaboratoryList()).thenReturn(new ArrayList<>());

        // Act
        Integer actualSaveBenInvestigationFromDocResult = aNCNurseServiceImpl
                .saveBenInvestigationFromDoc(wrapperBenInvestigationANC);

        // Assert
        verify(wrapperBenInvestigationANC).getLaboratoryList();
        assertEquals(1, actualSaveBenInvestigationFromDocResult.intValue());
    }

    @Test
    void testSaveBenInvestigationFromDoc3() {
        // Arrange
        when(labTestOrderDetailRepo.saveAll(Mockito.<Iterable<LabTestOrderDetail>>any())).thenReturn(new ArrayList<>());

        LabTestOrderDetail labTestOrderDetail = new LabTestOrderDetail();
        labTestOrderDetail.setBenVisitID(1L);
        labTestOrderDetail.setBeneficiaryRegID(1L);
        labTestOrderDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        labTestOrderDetail.setCreatedDate(mock(Timestamp.class));
        labTestOrderDetail.setDeleted(true);
        labTestOrderDetail.setLabTestOrderID(1L);
        labTestOrderDetail.setLastModDate(mock(Timestamp.class));
        labTestOrderDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        labTestOrderDetail.setParkingPlaceID(1);
        labTestOrderDetail.setPrescriptionID(1L);
        labTestOrderDetail.setProcedureID(1);
        labTestOrderDetail.setProcedureName("Procedure Name");
        labTestOrderDetail.setProcessed("Processed");
        labTestOrderDetail.setProviderServiceMapID(1);
        labTestOrderDetail.setReservedForChange("Reserved For Change");
        labTestOrderDetail.setSyncedBy("Synced By");
        labTestOrderDetail.setSyncedDate(mock(Timestamp.class));
        labTestOrderDetail.setTestingRequirements("Testing Requirements");
        labTestOrderDetail.setVanID(1);
        labTestOrderDetail.setVanSerialNo(1L);
        labTestOrderDetail.setVehicalNo("Vehical No");
        labTestOrderDetail.setVisitCode(1L);

        ArrayList<LabTestOrderDetail> labTestOrderDetailList = new ArrayList<>();
        labTestOrderDetailList.add(labTestOrderDetail);
        WrapperBenInvestigationANC wrapperBenInvestigationANC = mock(WrapperBenInvestigationANC.class);
        when(wrapperBenInvestigationANC.getProviderServiceMapID()).thenReturn(1);
        when(wrapperBenInvestigationANC.getBenVisitID()).thenReturn(1L);
        when(wrapperBenInvestigationANC.getBeneficiaryRegID()).thenReturn(1L);
        when(wrapperBenInvestigationANC.getPrescriptionID()).thenReturn(1L);
        when(wrapperBenInvestigationANC.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(wrapperBenInvestigationANC.getLaboratoryList()).thenReturn(labTestOrderDetailList);

        // Act
        Integer actualSaveBenInvestigationFromDocResult = aNCNurseServiceImpl
                .saveBenInvestigationFromDoc(wrapperBenInvestigationANC);

        // Assert
        verify(wrapperBenInvestigationANC).getBenVisitID();
        verify(wrapperBenInvestigationANC).getBeneficiaryRegID();
        verify(wrapperBenInvestigationANC).getCreatedBy();
        verify(wrapperBenInvestigationANC).getLaboratoryList();
        verify(wrapperBenInvestigationANC).getPrescriptionID();
        verify(wrapperBenInvestigationANC).getProviderServiceMapID();
        verify(labTestOrderDetailRepo).saveAll(isA(Iterable.class));
        assertEquals(0, actualSaveBenInvestigationFromDocResult.intValue());
    }

    @Test
    void testSaveBenInvestigationFromDoc4() {
        // Arrange
        LabTestOrderDetail labTestOrderDetail = new LabTestOrderDetail();
        labTestOrderDetail.setBenVisitID(1L);
        labTestOrderDetail.setBeneficiaryRegID(1L);
        labTestOrderDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        labTestOrderDetail.setCreatedDate(mock(Timestamp.class));
        labTestOrderDetail.setDeleted(true);
        labTestOrderDetail.setLabTestOrderID(1L);
        labTestOrderDetail.setLastModDate(mock(Timestamp.class));
        labTestOrderDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        labTestOrderDetail.setParkingPlaceID(1);
        labTestOrderDetail.setPrescriptionID(1L);
        labTestOrderDetail.setProcedureID(1);
        labTestOrderDetail.setProcedureName("Procedure Name");
        labTestOrderDetail.setProcessed("Processed");
        labTestOrderDetail.setProviderServiceMapID(1);
        labTestOrderDetail.setReservedForChange("Reserved For Change");
        labTestOrderDetail.setSyncedBy("Synced By");
        labTestOrderDetail.setSyncedDate(mock(Timestamp.class));
        labTestOrderDetail.setTestingRequirements("Testing Requirements");
        labTestOrderDetail.setVanID(1);
        labTestOrderDetail.setVanSerialNo(1L);
        labTestOrderDetail.setVehicalNo("Vehical No");
        labTestOrderDetail.setVisitCode(1L);

        ArrayList<LabTestOrderDetail> labTestOrderDetailList = new ArrayList<>();
        labTestOrderDetailList.add(labTestOrderDetail);
        when(labTestOrderDetailRepo.saveAll(Mockito.<Iterable<LabTestOrderDetail>>any()))
                .thenReturn(labTestOrderDetailList);

        LabTestOrderDetail labTestOrderDetail2 = new LabTestOrderDetail();
        labTestOrderDetail2.setBenVisitID(1L);
        labTestOrderDetail2.setBeneficiaryRegID(1L);
        labTestOrderDetail2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        labTestOrderDetail2.setCreatedDate(mock(Timestamp.class));
        labTestOrderDetail2.setDeleted(true);
        labTestOrderDetail2.setLabTestOrderID(1L);
        labTestOrderDetail2.setLastModDate(mock(Timestamp.class));
        labTestOrderDetail2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        labTestOrderDetail2.setParkingPlaceID(1);
        labTestOrderDetail2.setPrescriptionID(1L);
        labTestOrderDetail2.setProcedureID(1);
        labTestOrderDetail2.setProcedureName("Procedure Name");
        labTestOrderDetail2.setProcessed("Processed");
        labTestOrderDetail2.setProviderServiceMapID(1);
        labTestOrderDetail2.setReservedForChange("Reserved For Change");
        labTestOrderDetail2.setSyncedBy("Synced By");
        labTestOrderDetail2.setSyncedDate(mock(Timestamp.class));
        labTestOrderDetail2.setTestingRequirements("Testing Requirements");
        labTestOrderDetail2.setVanID(1);
        labTestOrderDetail2.setVanSerialNo(1L);
        labTestOrderDetail2.setVehicalNo("Vehical No");
        labTestOrderDetail2.setVisitCode(1L);

        ArrayList<LabTestOrderDetail> labTestOrderDetailList2 = new ArrayList<>();
        labTestOrderDetailList2.add(labTestOrderDetail2);
        WrapperBenInvestigationANC wrapperBenInvestigationANC = mock(WrapperBenInvestigationANC.class);
        when(wrapperBenInvestigationANC.getProviderServiceMapID()).thenReturn(1);
        when(wrapperBenInvestigationANC.getBenVisitID()).thenReturn(1L);
        when(wrapperBenInvestigationANC.getBeneficiaryRegID()).thenReturn(1L);
        when(wrapperBenInvestigationANC.getPrescriptionID()).thenReturn(1L);
        when(wrapperBenInvestigationANC.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(wrapperBenInvestigationANC.getLaboratoryList()).thenReturn(labTestOrderDetailList2);

        // Act
        Integer actualSaveBenInvestigationFromDocResult = aNCNurseServiceImpl
                .saveBenInvestigationFromDoc(wrapperBenInvestigationANC);

        // Assert
        verify(wrapperBenInvestigationANC).getBenVisitID();
        verify(wrapperBenInvestigationANC).getBeneficiaryRegID();
        verify(wrapperBenInvestigationANC).getCreatedBy();
        verify(wrapperBenInvestigationANC).getLaboratoryList();
        verify(wrapperBenInvestigationANC).getPrescriptionID();
        verify(wrapperBenInvestigationANC).getProviderServiceMapID();
        verify(labTestOrderDetailRepo).saveAll(isA(Iterable.class));
        assertEquals(1, actualSaveBenInvestigationFromDocResult.intValue());
    }

    @Test
    void testSaveBenAncCareDetails() throws ParseException {
        ANCCareDetails ancCareDetails = new ANCCareDetails();
        ancCareDetails.setAbortions_A((short) 1);
        ancCareDetails.setBenVisitID(1L);
        ancCareDetails.setBeneficiaryRegID(1L);
        ancCareDetails.setBloodGroup("Blood Group");
        ancCareDetails.setComolaintType("Comolaint Type");
        ancCareDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancCareDetails.setCreatedDate(mock(Timestamp.class));
        ancCareDetails.setDeleted(true);
        ancCareDetails.setDescription("The characteristics of someone or something");
        ancCareDetails.setDuration("Duration");
        ancCareDetails.setExpDelDt("Exp Del Dt");
        ancCareDetails.setExpectedDateofDelivery(mock(Date.class));
        ancCareDetails.setGestationalAgeOrPeriodofAmenorrhea_POA((short) 1);
        ancCareDetails.setGravida_G((short) 1);
        ancCareDetails.setID(1L);
        ancCareDetails.setLastMenstrualPeriod_LMP(mock(Date.class));
        ancCareDetails.setLastModDate(mock(Timestamp.class));
        ancCareDetails.setLivebirths_L((short) 1);
        ancCareDetails.setLmpDate("2020-03-01");
        ancCareDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancCareDetails.setPara((short) 1);
        ancCareDetails.setParkingPlaceID(1);
        ancCareDetails.setPretermDeliveries_P((short) 1);
        ancCareDetails.setPrimiGravida(true);
        ancCareDetails.setProcessed("Processed");
        ancCareDetails.setProviderServiceMapID(1);
        ancCareDetails.setReservedForChange("Reserved For Change");
        ancCareDetails.setStillBirth(1);
        ancCareDetails.setSyncedBy("Synced By");
        ancCareDetails.setSyncedDate(mock(Timestamp.class));
        ancCareDetails.setTermDeliveries_T((short) 1);
        ancCareDetails.setTrimesterNumber((short) 1);
        ancCareDetails.setVanID(1);
        ancCareDetails.setVanSerialNo(1L);
        ancCareDetails.setVehicalNo("Vehical No");
        ancCareDetails.setVisitCode(1L);
        ancCareDetails.setVisitNo((short) 1);
        ANCCareRepo ancCareRepo = mock(ANCCareRepo.class);
        when(ancCareRepo.save(Mockito.<ANCCareDetails>any())).thenReturn(ancCareDetails);

        ANCNurseServiceImpl ancNurseServiceImpl = new ANCNurseServiceImpl();
        ancNurseServiceImpl.setAncCareRepo(ancCareRepo);

        ANCCareDetails ancCareDetailsOBJ = new ANCCareDetails();
        ancCareDetailsOBJ.setAbortions_A((short) 1);
        ancCareDetailsOBJ.setBenVisitID(1L);
        ancCareDetailsOBJ.setBeneficiaryRegID(1L);
        ancCareDetailsOBJ.setBloodGroup("Blood Group");
        ancCareDetailsOBJ.setComolaintType("Comolaint Type");
        ancCareDetailsOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancCareDetailsOBJ.setCreatedDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setDeleted(true);
        ancCareDetailsOBJ.setDescription("The characteristics of someone or something");
        ancCareDetailsOBJ.setDuration("Duration");
        ancCareDetailsOBJ.setExpectedDateofDelivery(mock(Date.class));
        ancCareDetailsOBJ.setGestationalAgeOrPeriodofAmenorrhea_POA((short) 1);
        ancCareDetailsOBJ.setGravida_G((short) 1);
        ancCareDetailsOBJ.setID(1L);
        ancCareDetailsOBJ.setLastMenstrualPeriod_LMP(mock(Date.class));
        ancCareDetailsOBJ.setLastModDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setLivebirths_L((short) 1);
        ancCareDetailsOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancCareDetailsOBJ.setPara((short) 1);
        ancCareDetailsOBJ.setParkingPlaceID(1);
        ancCareDetailsOBJ.setPretermDeliveries_P((short) 1);
        ancCareDetailsOBJ.setPrimiGravida(true);
        ancCareDetailsOBJ.setProcessed("Processed");
        ancCareDetailsOBJ.setProviderServiceMapID(1);
        ancCareDetailsOBJ.setReservedForChange("Reserved For Change");
        ancCareDetailsOBJ.setStillBirth(1);
        ancCareDetailsOBJ.setSyncedBy("Synced By");
        ancCareDetailsOBJ.setSyncedDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setTermDeliveries_T((short) 1);
        ancCareDetailsOBJ.setTrimesterNumber((short) 1);
        ancCareDetailsOBJ.setVanID(1);
        ancCareDetailsOBJ.setVanSerialNo(1L);
        ancCareDetailsOBJ.setVehicalNo("Vehical No");
        ancCareDetailsOBJ.setVisitCode(1L);
        ancCareDetailsOBJ.setVisitNo((short) 1);
        ancCareDetailsOBJ.setExpDelDt(null);
        ancCareDetailsOBJ.setLmpDate(null);

        // Act
        Long actualSaveBenAncCareDetailsResult = ancNurseServiceImpl.saveBenAncCareDetails(ancCareDetailsOBJ);

        // Assert
        verify(ancCareRepo).save(isA(ANCCareDetails.class));
        assertEquals(1L, actualSaveBenAncCareDetailsResult.longValue());
    }

    @Test
    void testSaveBenAncCareDetails2() throws ParseException {
        ANCCareDetails ancCareDetails = mock(ANCCareDetails.class);
        when(ancCareDetails.getID()).thenReturn(1L);
        doNothing().when(ancCareDetails).setAbortions_A(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setBenVisitID(Mockito.<Long>any());
        doNothing().when(ancCareDetails).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(ancCareDetails).setBloodGroup(Mockito.<String>any());
        doNothing().when(ancCareDetails).setComolaintType(Mockito.<String>any());
        doNothing().when(ancCareDetails).setCreatedBy(Mockito.<String>any());
        doNothing().when(ancCareDetails).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetails).setDeleted(Mockito.<Boolean>any());
        doNothing().when(ancCareDetails).setDescription(Mockito.<String>any());
        doNothing().when(ancCareDetails).setDuration(Mockito.<String>any());
        doNothing().when(ancCareDetails).setExpDelDt(Mockito.<String>any());
        doNothing().when(ancCareDetails).setExpectedDateofDelivery(Mockito.<Date>any());
        doNothing().when(ancCareDetails).setGestationalAgeOrPeriodofAmenorrhea_POA(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setGravida_G(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setID(Mockito.<Long>any());
        doNothing().when(ancCareDetails).setLastMenstrualPeriod_LMP(Mockito.<Date>any());
        doNothing().when(ancCareDetails).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetails).setLivebirths_L(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setLmpDate(Mockito.<String>any());
        doNothing().when(ancCareDetails).setModifiedBy(Mockito.<String>any());
        doNothing().when(ancCareDetails).setPara(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(ancCareDetails).setPretermDeliveries_P(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setPrimiGravida(Mockito.<Boolean>any());
        doNothing().when(ancCareDetails).setProcessed(Mockito.<String>any());
        doNothing().when(ancCareDetails).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(ancCareDetails).setReservedForChange(Mockito.<String>any());
        doNothing().when(ancCareDetails).setStillBirth(Mockito.<Integer>any());
        doNothing().when(ancCareDetails).setSyncedBy(Mockito.<String>any());
        doNothing().when(ancCareDetails).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetails).setTermDeliveries_T(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setTrimesterNumber(Mockito.<Short>any());
        doNothing().when(ancCareDetails).setVanID(Mockito.<Integer>any());
        doNothing().when(ancCareDetails).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(ancCareDetails).setVehicalNo(Mockito.<String>any());
        doNothing().when(ancCareDetails).setVisitCode(Mockito.<Long>any());
        doNothing().when(ancCareDetails).setVisitNo(Mockito.<Short>any());
        ancCareDetails.setAbortions_A((short) 1);
        ancCareDetails.setBenVisitID(1L);
        ancCareDetails.setBeneficiaryRegID(1L);
        ancCareDetails.setBloodGroup("Blood Group");
        ancCareDetails.setComolaintType("Comolaint Type");
        ancCareDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancCareDetails.setCreatedDate(mock(Timestamp.class));
        ancCareDetails.setDeleted(true);
        ancCareDetails.setDescription("The characteristics of someone or something");
        ancCareDetails.setDuration("Duration");
        ancCareDetails.setExpDelDt("Exp Del Dt");
        ancCareDetails.setExpectedDateofDelivery(mock(Date.class));
        ancCareDetails.setGestationalAgeOrPeriodofAmenorrhea_POA((short) 1);
        ancCareDetails.setGravida_G((short) 1);
        ancCareDetails.setID(1L);
        ancCareDetails.setLastMenstrualPeriod_LMP(mock(Date.class));
        ancCareDetails.setLastModDate(mock(Timestamp.class));
        ancCareDetails.setLivebirths_L((short) 1);
        ancCareDetails.setLmpDate("2020-03-01");
        ancCareDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancCareDetails.setPara((short) 1);
        ancCareDetails.setParkingPlaceID(1);
        ancCareDetails.setPretermDeliveries_P((short) 1);
        ancCareDetails.setPrimiGravida(true);
        ancCareDetails.setProcessed("Processed");
        ancCareDetails.setProviderServiceMapID(1);
        ancCareDetails.setReservedForChange("Reserved For Change");
        ancCareDetails.setStillBirth(1);
        ancCareDetails.setSyncedBy("Synced By");
        ancCareDetails.setSyncedDate(mock(Timestamp.class));
        ancCareDetails.setTermDeliveries_T((short) 1);
        ancCareDetails.setTrimesterNumber((short) 1);
        ancCareDetails.setVanID(1);
        ancCareDetails.setVanSerialNo(1L);
        ancCareDetails.setVehicalNo("Vehical No");
        ancCareDetails.setVisitCode(1L);
        ancCareDetails.setVisitNo((short) 1);
        ANCCareRepo ancCareRepo = mock(ANCCareRepo.class);
        when(ancCareRepo.save(Mockito.<ANCCareDetails>any())).thenReturn(ancCareDetails);

        ANCNurseServiceImpl ancNurseServiceImpl = new ANCNurseServiceImpl();
        ancNurseServiceImpl.setAncCareRepo(ancCareRepo);

        ANCCareDetails ancCareDetailsOBJ = new ANCCareDetails();
        ancCareDetailsOBJ.setAbortions_A((short) 1);
        ancCareDetailsOBJ.setBenVisitID(1L);
        ancCareDetailsOBJ.setBeneficiaryRegID(1L);
        ancCareDetailsOBJ.setBloodGroup("Blood Group");
        ancCareDetailsOBJ.setComolaintType("Comolaint Type");
        ancCareDetailsOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancCareDetailsOBJ.setCreatedDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setDeleted(true);
        ancCareDetailsOBJ.setDescription("The characteristics of someone or something");
        ancCareDetailsOBJ.setDuration("Duration");
        ancCareDetailsOBJ.setExpectedDateofDelivery(mock(Date.class));
        ancCareDetailsOBJ.setGestationalAgeOrPeriodofAmenorrhea_POA((short) 1);
        ancCareDetailsOBJ.setGravida_G((short) 1);
        ancCareDetailsOBJ.setID(1L);
        ancCareDetailsOBJ.setLastMenstrualPeriod_LMP(mock(Date.class));
        ancCareDetailsOBJ.setLastModDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setLivebirths_L((short) 1);
        ancCareDetailsOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancCareDetailsOBJ.setPara((short) 1);
        ancCareDetailsOBJ.setParkingPlaceID(1);
        ancCareDetailsOBJ.setPretermDeliveries_P((short) 1);
        ancCareDetailsOBJ.setPrimiGravida(true);
        ancCareDetailsOBJ.setProcessed("Processed");
        ancCareDetailsOBJ.setProviderServiceMapID(1);
        ancCareDetailsOBJ.setReservedForChange("Reserved For Change");
        ancCareDetailsOBJ.setStillBirth(1);
        ancCareDetailsOBJ.setSyncedBy("Synced By");
        ancCareDetailsOBJ.setSyncedDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setTermDeliveries_T((short) 1);
        ancCareDetailsOBJ.setTrimesterNumber((short) 1);
        ancCareDetailsOBJ.setVanID(1);
        ancCareDetailsOBJ.setVanSerialNo(1L);
        ancCareDetailsOBJ.setVehicalNo("Vehical No");
        ancCareDetailsOBJ.setVisitCode(1L);
        ancCareDetailsOBJ.setVisitNo((short) 1);
        ancCareDetailsOBJ.setExpDelDt(null);
        ancCareDetailsOBJ.setLmpDate(null);

        // Act
        Long actualSaveBenAncCareDetailsResult = ancNurseServiceImpl.saveBenAncCareDetails(ancCareDetailsOBJ);

        // Assert
        verify(ancCareDetails).getID();
        verify(ancCareDetails).setAbortions_A(isA(Short.class));
        verify(ancCareDetails).setBenVisitID(isA(Long.class));
        verify(ancCareDetails).setBeneficiaryRegID(isA(Long.class));
        verify(ancCareDetails).setBloodGroup(eq("Blood Group"));
        verify(ancCareDetails).setComolaintType(eq("Comolaint Type"));
        verify(ancCareDetails).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(ancCareDetails).setCreatedDate(isA(Timestamp.class));
        verify(ancCareDetails).setDeleted(isA(Boolean.class));
        verify(ancCareDetails).setDescription(eq("The characteristics of someone or something"));
        verify(ancCareDetails).setDuration(eq("Duration"));
        verify(ancCareDetails).setExpDelDt(eq("Exp Del Dt"));
        verify(ancCareDetails).setExpectedDateofDelivery(isA(Date.class));
        verify(ancCareDetails).setGestationalAgeOrPeriodofAmenorrhea_POA(isA(Short.class));
        verify(ancCareDetails).setGravida_G(isA(Short.class));
        verify(ancCareDetails).setID(isA(Long.class));
        verify(ancCareDetails).setLastMenstrualPeriod_LMP(isA(Date.class));
        verify(ancCareDetails).setLastModDate(isA(Timestamp.class));
        verify(ancCareDetails).setLivebirths_L(isA(Short.class));
        verify(ancCareDetails).setLmpDate(eq("2020-03-01"));
        verify(ancCareDetails).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(ancCareDetails).setPara(isA(Short.class));
        verify(ancCareDetails).setParkingPlaceID(isA(Integer.class));
        verify(ancCareDetails).setPretermDeliveries_P(isA(Short.class));
        verify(ancCareDetails).setPrimiGravida(isA(Boolean.class));
        verify(ancCareDetails).setProcessed(eq("Processed"));
        verify(ancCareDetails).setProviderServiceMapID(isA(Integer.class));
        verify(ancCareDetails).setReservedForChange(eq("Reserved For Change"));
        verify(ancCareDetails).setStillBirth(isA(Integer.class));
        verify(ancCareDetails).setSyncedBy(eq("Synced By"));
        verify(ancCareDetails).setSyncedDate(isA(Timestamp.class));
        verify(ancCareDetails).setTermDeliveries_T(isA(Short.class));
        verify(ancCareDetails).setTrimesterNumber(isA(Short.class));
        verify(ancCareDetails).setVanID(isA(Integer.class));
        verify(ancCareDetails).setVanSerialNo(isA(Long.class));
        verify(ancCareDetails).setVehicalNo(eq("Vehical No"));
        verify(ancCareDetails).setVisitCode(isA(Long.class));
        verify(ancCareDetails).setVisitNo(isA(Short.class));
        verify(ancCareRepo).save(isA(ANCCareDetails.class));
        assertEquals(1L, actualSaveBenAncCareDetailsResult.longValue());
    }

    @Test
    void testSaveBenAncCareDetails3() throws ParseException {
        ANCCareDetails ancCareDetailsOBJ = new ANCCareDetails();
        ancCareDetailsOBJ.setAbortions_A((short) 1);
        ancCareDetailsOBJ.setBenVisitID(1L);
        ancCareDetailsOBJ.setBeneficiaryRegID(1L);
        ancCareDetailsOBJ.setBloodGroup("Blood Group");
        ancCareDetailsOBJ.setComolaintType("Comolaint Type");
        ancCareDetailsOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancCareDetailsOBJ.setCreatedDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setDeleted(true);
        ancCareDetailsOBJ.setDescription("The characteristics of someone or something");
        ancCareDetailsOBJ.setDuration("Duration");
        ancCareDetailsOBJ.setExpDelDt("Exp Del Dt");
        ancCareDetailsOBJ.setExpectedDateofDelivery(mock(Date.class));
        ancCareDetailsOBJ.setGestationalAgeOrPeriodofAmenorrhea_POA((short) 1);
        ancCareDetailsOBJ.setGravida_G((short) 1);
        ancCareDetailsOBJ.setID(1L);
        ancCareDetailsOBJ.setLastMenstrualPeriod_LMP(mock(Date.class));
        ancCareDetailsOBJ.setLastModDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setLivebirths_L((short) 1);
        ancCareDetailsOBJ.setLmpDate("2020-03-01");
        ancCareDetailsOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancCareDetailsOBJ.setPara((short) 1);
        ancCareDetailsOBJ.setParkingPlaceID(1);
        ancCareDetailsOBJ.setPretermDeliveries_P((short) 1);
        ancCareDetailsOBJ.setPrimiGravida(true);
        ancCareDetailsOBJ.setProcessed("Processed");
        ancCareDetailsOBJ.setProviderServiceMapID(1);
        ancCareDetailsOBJ.setReservedForChange("Reserved For Change");
        ancCareDetailsOBJ.setStillBirth(1);
        ancCareDetailsOBJ.setSyncedBy("Synced By");
        ancCareDetailsOBJ.setSyncedDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setTermDeliveries_T((short) 1);
        ancCareDetailsOBJ.setTrimesterNumber((short) 1);
        ancCareDetailsOBJ.setVanID(1);
        ancCareDetailsOBJ.setVanSerialNo(1L);
        ancCareDetailsOBJ.setVehicalNo("Vehical No");
        ancCareDetailsOBJ.setVisitCode(1L);
        ancCareDetailsOBJ.setVisitNo((short) 1);

        // Act
        aNCNurseServiceImpl.saveBenAncCareDetails(ancCareDetailsOBJ);
    }

    @Test
    void testSaveAncImmunizationDetails() throws ParseException {
        // Arrange
        when(aNCWomenVaccineRepo.saveAll(Mockito.<Iterable<ANCWomenVaccineDetail>>any())).thenReturn(new ArrayList<>());

        // Act
        Long actualSaveAncImmunizationDetailsResult = aNCNurseServiceImpl
                .saveAncImmunizationDetails(new WrapperAncImmunization());

        // Assert
        verify(aNCWomenVaccineRepo).saveAll(isA(Iterable.class));
        assertNull(actualSaveAncImmunizationDetailsResult);
    }

    @Test
    void testSaveAncImmunizationDetails2() throws ParseException {
        // Arrange
        ANCWomenVaccineDetail ancWomenVaccineDetail = new ANCWomenVaccineDetail();
        ancWomenVaccineDetail.setBenVisitID(1L);
        ancWomenVaccineDetail.setBeneficiaryRegID(1L);
        ancWomenVaccineDetail.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancWomenVaccineDetail.setCreatedDate(mock(Timestamp.class));
        ancWomenVaccineDetail.setDeleted(true);
        ancWomenVaccineDetail.setID(1L);
        ancWomenVaccineDetail.setLastModDate(mock(Timestamp.class));
        ancWomenVaccineDetail.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancWomenVaccineDetail.setParkingPlaceID(1);
        ancWomenVaccineDetail.setProcessed("TT-1");
        ancWomenVaccineDetail.setProviderServiceMapID(1);
        ancWomenVaccineDetail.setReceivedDate(mock(Date.class));
        ancWomenVaccineDetail.setReceivedFacilityName("TT-1");
        ancWomenVaccineDetail.setReservedForChange("TT-1");
        ancWomenVaccineDetail.setStatus("TT-1");
        ancWomenVaccineDetail.setSyncedBy("TT-1");
        ancWomenVaccineDetail.setSyncedDate(mock(Timestamp.class));
        ancWomenVaccineDetail.setVaccineName("TT-1");
        ancWomenVaccineDetail.setVanID(1);
        ancWomenVaccineDetail.setVanSerialNo(1L);
        ancWomenVaccineDetail.setVehicalNo("TT-1");
        ancWomenVaccineDetail.setVisitCode(1L);

        ArrayList<ANCWomenVaccineDetail> ancWomenVaccineDetailList = new ArrayList<>();
        ancWomenVaccineDetailList.add(ancWomenVaccineDetail);
        when(aNCWomenVaccineRepo.saveAll(Mockito.<Iterable<ANCWomenVaccineDetail>>any()))
                .thenReturn(ancWomenVaccineDetailList);

        // Act
        Long actualSaveAncImmunizationDetailsResult = aNCNurseServiceImpl
                .saveAncImmunizationDetails(new WrapperAncImmunization());

        // Assert
        verify(aNCWomenVaccineRepo).saveAll(isA(Iterable.class));
        assertEquals(1L, actualSaveAncImmunizationDetailsResult.longValue());
    }

    @Test
    void testSaveAncImmunizationDetails3() throws ParseException {
        // Arrange
        when(aNCWomenVaccineRepo.saveAll(Mockito.<Iterable<ANCWomenVaccineDetail>>any())).thenReturn(new ArrayList<>());

        // Act
        Long actualSaveAncImmunizationDetailsResult = aNCNurseServiceImpl.saveAncImmunizationDetails(null);

        // Assert
        verify(aNCWomenVaccineRepo).saveAll(isA(Iterable.class));
        assertNull(actualSaveAncImmunizationDetailsResult);
    }

    @Test
    void testSaveAncImmunizationDetails4() throws ParseException {
        // Arrange
        when(aNCWomenVaccineRepo.saveAll(Mockito.<Iterable<ANCWomenVaccineDetail>>any())).thenReturn(new ArrayList<>());

        // Act
        Long actualSaveAncImmunizationDetailsResult = aNCNurseServiceImpl
                .saveAncImmunizationDetails(new WrapperAncImmunization(1L, 1L, 1, "Jan 1, 2020 8:00am GMT+0100", "TT-1",
                        "2020-03-01", "TT-1", "TT-1", "2020-03-01", "TT-1", "TT-1", "2020-03-01", "TT-1"));

        // Assert
        verify(aNCWomenVaccineRepo).saveAll(isA(Iterable.class));
        assertNull(actualSaveAncImmunizationDetailsResult);
    }

    @Test
    void testSaveAncImmunizationDetails5() throws ParseException {
        // Arrange
        when(aNCWomenVaccineRepo.saveAll(Mockito.<Iterable<ANCWomenVaccineDetail>>any())).thenReturn(new ArrayList<>());
        WrapperAncImmunization wrapperAncImmunizationOBJ = mock(WrapperAncImmunization.class);
        when(wrapperAncImmunizationOBJ.getParkingPlaceID()).thenReturn(1);
        when(wrapperAncImmunizationOBJ.getProviderServiceMapID()).thenReturn(1);
        when(wrapperAncImmunizationOBJ.getVanID()).thenReturn(1);
        when(wrapperAncImmunizationOBJ.getBenVisitID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.getBeneficiaryRegID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.getVisitCode()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.gettT1ID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.gettT2ID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.gettT3ID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(wrapperAncImmunizationOBJ.getDateReceivedForTT_1()).thenReturn("2020-03-01");
        when(wrapperAncImmunizationOBJ.getDateReceivedForTT_2()).thenReturn("2020-03-01");
        when(wrapperAncImmunizationOBJ.getDateReceivedForTT_3()).thenReturn("2020-03-01");
        when(wrapperAncImmunizationOBJ.getFacilityNameOfTT_1()).thenReturn("Facility Name Of TT 1");
        when(wrapperAncImmunizationOBJ.getFacilityNameOfTT_2()).thenReturn("Facility Name Of TT 2");
        when(wrapperAncImmunizationOBJ.getFacilityNameOfTT_3()).thenReturn("Facility Name Of TT 3");
        when(wrapperAncImmunizationOBJ.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(wrapperAncImmunizationOBJ.gettT_1Status()).thenReturn("Gett T 1 Status");
        when(wrapperAncImmunizationOBJ.gettT_2Status()).thenReturn("Gett T 2 Status");
        when(wrapperAncImmunizationOBJ.gettT_3Status()).thenReturn("Gett T 3 Status");

        // Act
        Long actualSaveAncImmunizationDetailsResult = aNCNurseServiceImpl
                .saveAncImmunizationDetails(wrapperAncImmunizationOBJ);

        // Assert
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getBenVisitID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getBeneficiaryRegID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getCreatedBy();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getDateReceivedForTT_1();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getDateReceivedForTT_2();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getDateReceivedForTT_3();
        verify(wrapperAncImmunizationOBJ).getFacilityNameOfTT_1();
        verify(wrapperAncImmunizationOBJ).getFacilityNameOfTT_2();
        verify(wrapperAncImmunizationOBJ).getFacilityNameOfTT_3();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getModifiedBy();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getParkingPlaceID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getProviderServiceMapID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getVanID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getVisitCode();
        verify(wrapperAncImmunizationOBJ).gettT1ID();
        verify(wrapperAncImmunizationOBJ).gettT2ID();
        verify(wrapperAncImmunizationOBJ).gettT3ID();
        verify(wrapperAncImmunizationOBJ).gettT_1Status();
        verify(wrapperAncImmunizationOBJ).gettT_2Status();
        verify(wrapperAncImmunizationOBJ).gettT_3Status();
        verify(aNCWomenVaccineRepo).saveAll(isA(Iterable.class));
        assertNull(actualSaveAncImmunizationDetailsResult);
    }

    @Test
    void testSaveAncImmunizationDetails6() throws ParseException {
        // Arrange
        when(aNCWomenVaccineRepo.saveAll(Mockito.<Iterable<ANCWomenVaccineDetail>>any())).thenReturn(new ArrayList<>());
        WrapperAncImmunization wrapperAncImmunizationOBJ = mock(WrapperAncImmunization.class);
        when(wrapperAncImmunizationOBJ.getParkingPlaceID()).thenReturn(1);
        when(wrapperAncImmunizationOBJ.getProviderServiceMapID()).thenReturn(1);
        when(wrapperAncImmunizationOBJ.getVanID()).thenReturn(1);
        when(wrapperAncImmunizationOBJ.getBenVisitID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.getBeneficiaryRegID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.getVisitCode()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.gettT1ID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.gettT2ID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.gettT3ID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(wrapperAncImmunizationOBJ.getDateReceivedForTT_1()).thenReturn("TT-1");
        when(wrapperAncImmunizationOBJ.getDateReceivedForTT_2()).thenReturn("2020-03-01");
        when(wrapperAncImmunizationOBJ.getDateReceivedForTT_3()).thenReturn("2020-03-01");
        when(wrapperAncImmunizationOBJ.getFacilityNameOfTT_1()).thenReturn("Facility Name Of TT 1");
        when(wrapperAncImmunizationOBJ.getFacilityNameOfTT_2()).thenReturn("Facility Name Of TT 2");
        when(wrapperAncImmunizationOBJ.getFacilityNameOfTT_3()).thenReturn("Facility Name Of TT 3");
        when(wrapperAncImmunizationOBJ.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(wrapperAncImmunizationOBJ.gettT_1Status()).thenReturn("Gett T 1 Status");
        when(wrapperAncImmunizationOBJ.gettT_2Status()).thenReturn("Gett T 2 Status");
        when(wrapperAncImmunizationOBJ.gettT_3Status()).thenReturn("Gett T 3 Status");

        // Act
        Long actualSaveAncImmunizationDetailsResult = aNCNurseServiceImpl
                .saveAncImmunizationDetails(wrapperAncImmunizationOBJ);

        // Assert
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getBenVisitID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getBeneficiaryRegID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getCreatedBy();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getDateReceivedForTT_1();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getDateReceivedForTT_2();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getDateReceivedForTT_3();
        verify(wrapperAncImmunizationOBJ).getFacilityNameOfTT_1();
        verify(wrapperAncImmunizationOBJ).getFacilityNameOfTT_2();
        verify(wrapperAncImmunizationOBJ).getFacilityNameOfTT_3();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getModifiedBy();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getParkingPlaceID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getProviderServiceMapID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getVanID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getVisitCode();
        verify(wrapperAncImmunizationOBJ).gettT1ID();
        verify(wrapperAncImmunizationOBJ).gettT2ID();
        verify(wrapperAncImmunizationOBJ).gettT3ID();
        verify(wrapperAncImmunizationOBJ).gettT_1Status();
        verify(wrapperAncImmunizationOBJ).gettT_2Status();
        verify(wrapperAncImmunizationOBJ).gettT_3Status();
        verify(aNCWomenVaccineRepo).saveAll(isA(Iterable.class));
        assertNull(actualSaveAncImmunizationDetailsResult);
    }

    @Test
    void testSaveAncImmunizationDetails7() throws ParseException {
        // Arrange
        when(aNCWomenVaccineRepo.saveAll(Mockito.<Iterable<ANCWomenVaccineDetail>>any())).thenReturn(new ArrayList<>());
        WrapperAncImmunization wrapperAncImmunizationOBJ = mock(WrapperAncImmunization.class);
        when(wrapperAncImmunizationOBJ.getParkingPlaceID()).thenReturn(1);
        when(wrapperAncImmunizationOBJ.getProviderServiceMapID()).thenReturn(1);
        when(wrapperAncImmunizationOBJ.getVanID()).thenReturn(1);
        when(wrapperAncImmunizationOBJ.getBenVisitID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.getBeneficiaryRegID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.getVisitCode()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.gettT1ID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.gettT2ID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.gettT3ID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(wrapperAncImmunizationOBJ.getDateReceivedForTT_1()).thenReturn("2020-03-01");
        when(wrapperAncImmunizationOBJ.getDateReceivedForTT_2()).thenReturn("TT-1");
        when(wrapperAncImmunizationOBJ.getDateReceivedForTT_3()).thenReturn("2020-03-01");
        when(wrapperAncImmunizationOBJ.getFacilityNameOfTT_1()).thenReturn("Facility Name Of TT 1");
        when(wrapperAncImmunizationOBJ.getFacilityNameOfTT_2()).thenReturn("Facility Name Of TT 2");
        when(wrapperAncImmunizationOBJ.getFacilityNameOfTT_3()).thenReturn("Facility Name Of TT 3");
        when(wrapperAncImmunizationOBJ.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(wrapperAncImmunizationOBJ.gettT_1Status()).thenReturn("Gett T 1 Status");
        when(wrapperAncImmunizationOBJ.gettT_2Status()).thenReturn("Gett T 2 Status");
        when(wrapperAncImmunizationOBJ.gettT_3Status()).thenReturn("Gett T 3 Status");

        // Act
        Long actualSaveAncImmunizationDetailsResult = aNCNurseServiceImpl
                .saveAncImmunizationDetails(wrapperAncImmunizationOBJ);

        // Assert
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getBenVisitID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getBeneficiaryRegID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getCreatedBy();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getDateReceivedForTT_1();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getDateReceivedForTT_2();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getDateReceivedForTT_3();
        verify(wrapperAncImmunizationOBJ).getFacilityNameOfTT_1();
        verify(wrapperAncImmunizationOBJ).getFacilityNameOfTT_2();
        verify(wrapperAncImmunizationOBJ).getFacilityNameOfTT_3();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getModifiedBy();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getParkingPlaceID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getProviderServiceMapID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getVanID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getVisitCode();
        verify(wrapperAncImmunizationOBJ).gettT1ID();
        verify(wrapperAncImmunizationOBJ).gettT2ID();
        verify(wrapperAncImmunizationOBJ).gettT3ID();
        verify(wrapperAncImmunizationOBJ).gettT_1Status();
        verify(wrapperAncImmunizationOBJ).gettT_2Status();
        verify(wrapperAncImmunizationOBJ).gettT_3Status();
        verify(aNCWomenVaccineRepo).saveAll(isA(Iterable.class));
        assertNull(actualSaveAncImmunizationDetailsResult);
    }

    @Test
    void testSaveAncImmunizationDetails8() throws ParseException {
        // Arrange
        when(aNCWomenVaccineRepo.saveAll(Mockito.<Iterable<ANCWomenVaccineDetail>>any())).thenReturn(new ArrayList<>());
        WrapperAncImmunization wrapperAncImmunizationOBJ = mock(WrapperAncImmunization.class);
        when(wrapperAncImmunizationOBJ.getParkingPlaceID()).thenReturn(1);
        when(wrapperAncImmunizationOBJ.getProviderServiceMapID()).thenReturn(1);
        when(wrapperAncImmunizationOBJ.getVanID()).thenReturn(1);
        when(wrapperAncImmunizationOBJ.getBenVisitID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.getBeneficiaryRegID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.getVisitCode()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.gettT1ID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.gettT2ID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.gettT3ID()).thenReturn(1L);
        when(wrapperAncImmunizationOBJ.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(wrapperAncImmunizationOBJ.getDateReceivedForTT_1()).thenReturn("2020-03-01");
        when(wrapperAncImmunizationOBJ.getDateReceivedForTT_2()).thenReturn("2020-03-01");
        when(wrapperAncImmunizationOBJ.getDateReceivedForTT_3()).thenReturn("TT-1");
        when(wrapperAncImmunizationOBJ.getFacilityNameOfTT_1()).thenReturn("Facility Name Of TT 1");
        when(wrapperAncImmunizationOBJ.getFacilityNameOfTT_2()).thenReturn("Facility Name Of TT 2");
        when(wrapperAncImmunizationOBJ.getFacilityNameOfTT_3()).thenReturn("Facility Name Of TT 3");
        when(wrapperAncImmunizationOBJ.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(wrapperAncImmunizationOBJ.gettT_1Status()).thenReturn("Gett T 1 Status");
        when(wrapperAncImmunizationOBJ.gettT_2Status()).thenReturn("Gett T 2 Status");
        when(wrapperAncImmunizationOBJ.gettT_3Status()).thenReturn("Gett T 3 Status");

        // Act
        Long actualSaveAncImmunizationDetailsResult = aNCNurseServiceImpl
                .saveAncImmunizationDetails(wrapperAncImmunizationOBJ);

        // Assert
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getBenVisitID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getBeneficiaryRegID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getCreatedBy();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getDateReceivedForTT_1();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getDateReceivedForTT_2();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getDateReceivedForTT_3();
        verify(wrapperAncImmunizationOBJ).getFacilityNameOfTT_1();
        verify(wrapperAncImmunizationOBJ).getFacilityNameOfTT_2();
        verify(wrapperAncImmunizationOBJ).getFacilityNameOfTT_3();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getModifiedBy();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getParkingPlaceID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getProviderServiceMapID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getVanID();
        verify(wrapperAncImmunizationOBJ, atLeast(1)).getVisitCode();
        verify(wrapperAncImmunizationOBJ).gettT1ID();
        verify(wrapperAncImmunizationOBJ).gettT2ID();
        verify(wrapperAncImmunizationOBJ).gettT3ID();
        verify(wrapperAncImmunizationOBJ).gettT_1Status();
        verify(wrapperAncImmunizationOBJ).gettT_2Status();
        verify(wrapperAncImmunizationOBJ).gettT_3Status();
        verify(aNCWomenVaccineRepo).saveAll(isA(Iterable.class));
        assertNull(actualSaveAncImmunizationDetailsResult);
    }

    @Test
    void testSaveSysObstetricExamination() {
        // Arrange
        SysObstetricExamination sysObstetricExamination = new SysObstetricExamination();
        sysObstetricExamination.setAbdominalScars("Abdominal Scars");
        sysObstetricExamination.setBenVisitID(1L);
        sysObstetricExamination.setBeneficiaryRegID(1L);
        sysObstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysObstetricExamination.setCreatedDate(mock(Timestamp.class));
        sysObstetricExamination.setDeleted(true);
        sysObstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        sysObstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        sysObstetricExamination.setFetalMovements("Fetal Movements");
        sysObstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        sysObstetricExamination.setFetalPresentation("Fetal Presentation");
        sysObstetricExamination.setFundalHeight("Fundal Height");
        sysObstetricExamination.setID(1L);
        sysObstetricExamination.setIsHRP(true);
        sysObstetricExamination.setLastModDate(mock(Timestamp.class));
        sysObstetricExamination.setLowLyingPlacenta(true);
        sysObstetricExamination.setMalPresentation(true);
        sysObstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysObstetricExamination.setParkingPlaceID(1);
        sysObstetricExamination.setProcessed("Processed");
        sysObstetricExamination.setProviderServiceMapID(1);
        sysObstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        sysObstetricExamination.setReasonsForHRPDB("Just cause");
        sysObstetricExamination.setReservedForChange("Reserved For Change");
        sysObstetricExamination.setSfh(10.0d);
        sysObstetricExamination.setSyncedBy("Synced By");
        sysObstetricExamination.setSyncedDate(mock(Timestamp.class));
        sysObstetricExamination.setVanID(1);
        sysObstetricExamination.setVanSerialNo(1L);
        sysObstetricExamination.setVehicalNo("Vehical No");
        sysObstetricExamination.setVertebralDeformity(true);
        sysObstetricExamination.setVisitCode(1L);
        sysObstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        sysObstetricExamination.setfHAndPOA_Status("F HAnd POA Status");
        when(sysObstetricExaminationRepo.save(Mockito.<SysObstetricExamination>any())).thenReturn(sysObstetricExamination);

        SysObstetricExamination obstetricExamination = new SysObstetricExamination();
        obstetricExamination.setAbdominalScars("Abdominal Scars");
        obstetricExamination.setBenVisitID(1L);
        obstetricExamination.setBeneficiaryRegID(1L);
        obstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        obstetricExamination.setCreatedDate(mock(Timestamp.class));
        obstetricExamination.setDeleted(true);
        obstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        obstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        obstetricExamination.setFetalMovements("Fetal Movements");
        obstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        obstetricExamination.setFetalPresentation("Fetal Presentation");
        obstetricExamination.setFundalHeight("Fundal Height");
        obstetricExamination.setID(1L);
        obstetricExamination.setIsHRP(true);
        obstetricExamination.setLastModDate(mock(Timestamp.class));
        obstetricExamination.setLowLyingPlacenta(true);
        obstetricExamination.setMalPresentation(true);
        obstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        obstetricExamination.setParkingPlaceID(1);
        obstetricExamination.setProcessed("Processed");
        obstetricExamination.setProviderServiceMapID(1);
        obstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        obstetricExamination.setReasonsForHRPDB("Just cause");
        obstetricExamination.setReservedForChange("Reserved For Change");
        obstetricExamination.setSfh(10.0d);
        obstetricExamination.setSyncedBy("Synced By");
        obstetricExamination.setSyncedDate(mock(Timestamp.class));
        obstetricExamination.setVanID(1);
        obstetricExamination.setVanSerialNo(1L);
        obstetricExamination.setVehicalNo("Vehical No");
        obstetricExamination.setVertebralDeformity(true);
        obstetricExamination.setVisitCode(1L);
        obstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        obstetricExamination.setfHAndPOA_Status("F HAnd POA Status");

        // Act
        Long actualSaveSysObstetricExaminationResult = aNCNurseServiceImpl
                .saveSysObstetricExamination(obstetricExamination);

        // Assert
        verify(sysObstetricExaminationRepo).save(isA(SysObstetricExamination.class));
        assertEquals(1L, actualSaveSysObstetricExaminationResult.longValue());
    }

    @Test
    void testSaveSysObstetricExamination2() {
        // Arrange
        SysObstetricExamination sysObstetricExamination = mock(SysObstetricExamination.class);
        when(sysObstetricExamination.getID()).thenReturn(1L);
        doNothing().when(sysObstetricExamination).setAbdominalScars(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setBenVisitID(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setCreatedBy(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(sysObstetricExamination).setDeleted(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setFetalHeartRate_BeatsPerMinute(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalHeartSounds(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalMovements(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalPositionOrLie(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalPresentation(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFundalHeight(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setID(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setIsHRP(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(sysObstetricExamination).setLowLyingPlacenta(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setMalPresentation(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setModifiedBy(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(sysObstetricExamination).setProcessed(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(sysObstetricExamination).setReasonsForHRP(Mockito.<String[]>any());
        doNothing().when(sysObstetricExamination).setReasonsForHRPDB(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setReservedForChange(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setSfh(Mockito.<Double>any());
        doNothing().when(sysObstetricExamination).setSyncedBy(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(sysObstetricExamination).setVanID(Mockito.<Integer>any());
        doNothing().when(sysObstetricExamination).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setVehicalNo(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setVertebralDeformity(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setVisitCode(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setfHAndPOA_Interpretation(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setfHAndPOA_Status(Mockito.<String>any());
        sysObstetricExamination.setAbdominalScars("Abdominal Scars");
        sysObstetricExamination.setBenVisitID(1L);
        sysObstetricExamination.setBeneficiaryRegID(1L);
        sysObstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysObstetricExamination.setCreatedDate(mock(Timestamp.class));
        sysObstetricExamination.setDeleted(true);
        sysObstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        sysObstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        sysObstetricExamination.setFetalMovements("Fetal Movements");
        sysObstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        sysObstetricExamination.setFetalPresentation("Fetal Presentation");
        sysObstetricExamination.setFundalHeight("Fundal Height");
        sysObstetricExamination.setID(1L);
        sysObstetricExamination.setIsHRP(true);
        sysObstetricExamination.setLastModDate(mock(Timestamp.class));
        sysObstetricExamination.setLowLyingPlacenta(true);
        sysObstetricExamination.setMalPresentation(true);
        sysObstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysObstetricExamination.setParkingPlaceID(1);
        sysObstetricExamination.setProcessed("Processed");
        sysObstetricExamination.setProviderServiceMapID(1);
        sysObstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        sysObstetricExamination.setReasonsForHRPDB("Just cause");
        sysObstetricExamination.setReservedForChange("Reserved For Change");
        sysObstetricExamination.setSfh(10.0d);
        sysObstetricExamination.setSyncedBy("Synced By");
        sysObstetricExamination.setSyncedDate(mock(Timestamp.class));
        sysObstetricExamination.setVanID(1);
        sysObstetricExamination.setVanSerialNo(1L);
        sysObstetricExamination.setVehicalNo("Vehical No");
        sysObstetricExamination.setVertebralDeformity(true);
        sysObstetricExamination.setVisitCode(1L);
        sysObstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        sysObstetricExamination.setfHAndPOA_Status("F HAnd POA Status");
        when(sysObstetricExaminationRepo.save(Mockito.<SysObstetricExamination>any())).thenReturn(sysObstetricExamination);

        SysObstetricExamination obstetricExamination = new SysObstetricExamination();
        obstetricExamination.setAbdominalScars("Abdominal Scars");
        obstetricExamination.setBenVisitID(1L);
        obstetricExamination.setBeneficiaryRegID(1L);
        obstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        obstetricExamination.setCreatedDate(mock(Timestamp.class));
        obstetricExamination.setDeleted(true);
        obstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        obstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        obstetricExamination.setFetalMovements("Fetal Movements");
        obstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        obstetricExamination.setFetalPresentation("Fetal Presentation");
        obstetricExamination.setFundalHeight("Fundal Height");
        obstetricExamination.setID(1L);
        obstetricExamination.setIsHRP(true);
        obstetricExamination.setLastModDate(mock(Timestamp.class));
        obstetricExamination.setLowLyingPlacenta(true);
        obstetricExamination.setMalPresentation(true);
        obstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        obstetricExamination.setParkingPlaceID(1);
        obstetricExamination.setProcessed("Processed");
        obstetricExamination.setProviderServiceMapID(1);
        obstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        obstetricExamination.setReasonsForHRPDB("Just cause");
        obstetricExamination.setReservedForChange("Reserved For Change");
        obstetricExamination.setSfh(10.0d);
        obstetricExamination.setSyncedBy("Synced By");
        obstetricExamination.setSyncedDate(mock(Timestamp.class));
        obstetricExamination.setVanID(1);
        obstetricExamination.setVanSerialNo(1L);
        obstetricExamination.setVehicalNo("Vehical No");
        obstetricExamination.setVertebralDeformity(true);
        obstetricExamination.setVisitCode(1L);
        obstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        obstetricExamination.setfHAndPOA_Status("F HAnd POA Status");

        // Act
        Long actualSaveSysObstetricExaminationResult = aNCNurseServiceImpl
                .saveSysObstetricExamination(obstetricExamination);

        // Assert
        verify(sysObstetricExamination).getID();
        verify(sysObstetricExamination).setAbdominalScars(eq("Abdominal Scars"));
        verify(sysObstetricExamination).setBenVisitID(isA(Long.class));
        verify(sysObstetricExamination).setBeneficiaryRegID(isA(Long.class));
        verify(sysObstetricExamination).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(sysObstetricExamination).setCreatedDate(isA(Timestamp.class));
        verify(sysObstetricExamination).setDeleted(isA(Boolean.class));
        verify(sysObstetricExamination).setFetalHeartRate_BeatsPerMinute(eq("Fetal Heart Rate Beats Per Minute"));
        verify(sysObstetricExamination).setFetalHeartSounds(eq("Fetal Heart Sounds"));
        verify(sysObstetricExamination).setFetalMovements(eq("Fetal Movements"));
        verify(sysObstetricExamination).setFetalPositionOrLie(eq("Fetal Position Or Lie"));
        verify(sysObstetricExamination).setFetalPresentation(eq("Fetal Presentation"));
        verify(sysObstetricExamination).setFundalHeight(eq("Fundal Height"));
        verify(sysObstetricExamination).setID(isA(Long.class));
        verify(sysObstetricExamination).setIsHRP(isA(Boolean.class));
        verify(sysObstetricExamination).setLastModDate(isA(Timestamp.class));
        verify(sysObstetricExamination).setLowLyingPlacenta(isA(Boolean.class));
        verify(sysObstetricExamination).setMalPresentation(isA(Boolean.class));
        verify(sysObstetricExamination).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(sysObstetricExamination).setParkingPlaceID(isA(Integer.class));
        verify(sysObstetricExamination).setProcessed(eq("Processed"));
        verify(sysObstetricExamination).setProviderServiceMapID(isA(Integer.class));
        verify(sysObstetricExamination).setReasonsForHRP(isA(String[].class));
        verify(sysObstetricExamination).setReasonsForHRPDB(eq("Just cause"));
        verify(sysObstetricExamination).setReservedForChange(eq("Reserved For Change"));
        verify(sysObstetricExamination).setSfh(isA(Double.class));
        verify(sysObstetricExamination).setSyncedBy(eq("Synced By"));
        verify(sysObstetricExamination).setSyncedDate(isA(Timestamp.class));
        verify(sysObstetricExamination).setVanID(isA(Integer.class));
        verify(sysObstetricExamination).setVanSerialNo(isA(Long.class));
        verify(sysObstetricExamination).setVehicalNo(eq("Vehical No"));
        verify(sysObstetricExamination).setVertebralDeformity(isA(Boolean.class));
        verify(sysObstetricExamination).setVisitCode(isA(Long.class));
        verify(sysObstetricExamination).setfHAndPOA_Interpretation(eq("F HAnd POA Interpretation"));
        verify(sysObstetricExamination).setfHAndPOA_Status(eq("F HAnd POA Status"));
        verify(sysObstetricExaminationRepo).save(isA(SysObstetricExamination.class));
        assertEquals(1L, actualSaveSysObstetricExaminationResult.longValue());
    }

    @Test
    void testGetSysObstetricExamination() {
        // Arrange
        SysObstetricExamination sysObstetricExamination = new SysObstetricExamination();
        sysObstetricExamination.setAbdominalScars("Abdominal Scars");
        sysObstetricExamination.setBenVisitID(1L);
        sysObstetricExamination.setBeneficiaryRegID(1L);
        sysObstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysObstetricExamination.setCreatedDate(mock(Timestamp.class));
        sysObstetricExamination.setDeleted(true);
        sysObstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        sysObstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        sysObstetricExamination.setFetalMovements("Fetal Movements");
        sysObstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        sysObstetricExamination.setFetalPresentation("Fetal Presentation");
        sysObstetricExamination.setFundalHeight("Fundal Height");
        sysObstetricExamination.setID(1L);
        sysObstetricExamination.setIsHRP(true);
        sysObstetricExamination.setLastModDate(mock(Timestamp.class));
        sysObstetricExamination.setLowLyingPlacenta(true);
        sysObstetricExamination.setMalPresentation(true);
        sysObstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysObstetricExamination.setParkingPlaceID(1);
        sysObstetricExamination.setProcessed("Processed");
        sysObstetricExamination.setProviderServiceMapID(1);
        sysObstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        sysObstetricExamination.setReasonsForHRPDB("Just cause");
        sysObstetricExamination.setReservedForChange("Reserved For Change");
        sysObstetricExamination.setSfh(10.0d);
        sysObstetricExamination.setSyncedBy("Synced By");
        sysObstetricExamination.setSyncedDate(mock(Timestamp.class));
        sysObstetricExamination.setVanID(1);
        sysObstetricExamination.setVanSerialNo(1L);
        sysObstetricExamination.setVehicalNo("Vehical No");
        sysObstetricExamination.setVertebralDeformity(true);
        sysObstetricExamination.setVisitCode(1L);
        sysObstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        sysObstetricExamination.setfHAndPOA_Status("F HAnd POA Status");
        when(sysObstetricExaminationRepo.getSysObstetricExaminationData(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysObstetricExamination);

        // Act
        SysObstetricExamination actualSysObstetricExamination = aNCNurseServiceImpl.getSysObstetricExamination(1L, 1L);

        // Assert
        verify(sysObstetricExaminationRepo).getSysObstetricExaminationData(isA(Long.class), isA(Long.class));
        assertSame(sysObstetricExamination, actualSysObstetricExamination);
        assertArrayEquals(new String[]{"Just cause"}, actualSysObstetricExamination.getReasonsForHRP());
    }

    @Test
    void testGetSysObstetricExamination2() {
        // Arrange
        SysObstetricExamination sysObstetricExamination = mock(SysObstetricExamination.class);
        when(sysObstetricExamination.getReasonsForHRPDB()).thenReturn("Just cause");
        doNothing().when(sysObstetricExamination).setAbdominalScars(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setBenVisitID(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setCreatedBy(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(sysObstetricExamination).setDeleted(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setFetalHeartRate_BeatsPerMinute(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalHeartSounds(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalMovements(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalPositionOrLie(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalPresentation(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFundalHeight(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setID(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setIsHRP(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(sysObstetricExamination).setLowLyingPlacenta(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setMalPresentation(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setModifiedBy(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(sysObstetricExamination).setProcessed(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(sysObstetricExamination).setReasonsForHRP(Mockito.<String[]>any());
        doNothing().when(sysObstetricExamination).setReasonsForHRPDB(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setReservedForChange(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setSfh(Mockito.<Double>any());
        doNothing().when(sysObstetricExamination).setSyncedBy(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(sysObstetricExamination).setVanID(Mockito.<Integer>any());
        doNothing().when(sysObstetricExamination).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setVehicalNo(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setVertebralDeformity(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setVisitCode(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setfHAndPOA_Interpretation(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setfHAndPOA_Status(Mockito.<String>any());
        sysObstetricExamination.setAbdominalScars("Abdominal Scars");
        sysObstetricExamination.setBenVisitID(1L);
        sysObstetricExamination.setBeneficiaryRegID(1L);
        sysObstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysObstetricExamination.setCreatedDate(mock(Timestamp.class));
        sysObstetricExamination.setDeleted(true);
        sysObstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        sysObstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        sysObstetricExamination.setFetalMovements("Fetal Movements");
        sysObstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        sysObstetricExamination.setFetalPresentation("Fetal Presentation");
        sysObstetricExamination.setFundalHeight("Fundal Height");
        sysObstetricExamination.setID(1L);
        sysObstetricExamination.setIsHRP(true);
        sysObstetricExamination.setLastModDate(mock(Timestamp.class));
        sysObstetricExamination.setLowLyingPlacenta(true);
        sysObstetricExamination.setMalPresentation(true);
        sysObstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysObstetricExamination.setParkingPlaceID(1);
        sysObstetricExamination.setProcessed("Processed");
        sysObstetricExamination.setProviderServiceMapID(1);
        sysObstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        sysObstetricExamination.setReasonsForHRPDB("Just cause");
        sysObstetricExamination.setReservedForChange("Reserved For Change");
        sysObstetricExamination.setSfh(10.0d);
        sysObstetricExamination.setSyncedBy("Synced By");
        sysObstetricExamination.setSyncedDate(mock(Timestamp.class));
        sysObstetricExamination.setVanID(1);
        sysObstetricExamination.setVanSerialNo(1L);
        sysObstetricExamination.setVehicalNo("Vehical No");
        sysObstetricExamination.setVertebralDeformity(true);
        sysObstetricExamination.setVisitCode(1L);
        sysObstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        sysObstetricExamination.setfHAndPOA_Status("F HAnd POA Status");
        when(sysObstetricExaminationRepo.getSysObstetricExaminationData(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysObstetricExamination);

        // Act
        aNCNurseServiceImpl.getSysObstetricExamination(1L, 1L);

        // Assert
        verify(sysObstetricExamination, atLeast(1)).getReasonsForHRPDB();
        verify(sysObstetricExamination).setAbdominalScars(eq("Abdominal Scars"));
        verify(sysObstetricExamination).setBenVisitID(isA(Long.class));
        verify(sysObstetricExamination).setBeneficiaryRegID(isA(Long.class));
        verify(sysObstetricExamination).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(sysObstetricExamination).setCreatedDate(isA(Timestamp.class));
        verify(sysObstetricExamination).setDeleted(isA(Boolean.class));
        verify(sysObstetricExamination).setFetalHeartRate_BeatsPerMinute(eq("Fetal Heart Rate Beats Per Minute"));
        verify(sysObstetricExamination).setFetalHeartSounds(eq("Fetal Heart Sounds"));
        verify(sysObstetricExamination).setFetalMovements(eq("Fetal Movements"));
        verify(sysObstetricExamination).setFetalPositionOrLie(eq("Fetal Position Or Lie"));
        verify(sysObstetricExamination).setFetalPresentation(eq("Fetal Presentation"));
        verify(sysObstetricExamination).setFundalHeight(eq("Fundal Height"));
        verify(sysObstetricExamination).setID(isA(Long.class));
        verify(sysObstetricExamination).setIsHRP(isA(Boolean.class));
        verify(sysObstetricExamination).setLastModDate(isA(Timestamp.class));
        verify(sysObstetricExamination).setLowLyingPlacenta(isA(Boolean.class));
        verify(sysObstetricExamination).setMalPresentation(isA(Boolean.class));
        verify(sysObstetricExamination).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(sysObstetricExamination).setParkingPlaceID(isA(Integer.class));
        verify(sysObstetricExamination).setProcessed(eq("Processed"));
        verify(sysObstetricExamination).setProviderServiceMapID(isA(Integer.class));
        verify(sysObstetricExamination, atLeast(1)).setReasonsForHRP(Mockito.<String[]>any());
        verify(sysObstetricExamination).setReasonsForHRPDB(eq("Just cause"));
        verify(sysObstetricExamination).setReservedForChange(eq("Reserved For Change"));
        verify(sysObstetricExamination).setSfh(isA(Double.class));
        verify(sysObstetricExamination).setSyncedBy(eq("Synced By"));
        verify(sysObstetricExamination).setSyncedDate(isA(Timestamp.class));
        verify(sysObstetricExamination).setVanID(isA(Integer.class));
        verify(sysObstetricExamination).setVanSerialNo(isA(Long.class));
        verify(sysObstetricExamination).setVehicalNo(eq("Vehical No"));
        verify(sysObstetricExamination).setVertebralDeformity(isA(Boolean.class));
        verify(sysObstetricExamination).setVisitCode(isA(Long.class));
        verify(sysObstetricExamination).setfHAndPOA_Interpretation(eq("F HAnd POA Interpretation"));
        verify(sysObstetricExamination).setfHAndPOA_Status(eq("F HAnd POA Status"));
        verify(sysObstetricExaminationRepo).getSysObstetricExaminationData(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetSysObstetricExamination3() {
        // Arrange
        SysObstetricExamination sysObstetricExamination = mock(SysObstetricExamination.class);
        when(sysObstetricExamination.getReasonsForHRPDB()).thenReturn(null);
        doNothing().when(sysObstetricExamination).setAbdominalScars(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setBenVisitID(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setCreatedBy(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(sysObstetricExamination).setDeleted(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setFetalHeartRate_BeatsPerMinute(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalHeartSounds(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalMovements(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalPositionOrLie(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFetalPresentation(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setFundalHeight(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setID(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setIsHRP(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(sysObstetricExamination).setLowLyingPlacenta(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setMalPresentation(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setModifiedBy(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(sysObstetricExamination).setProcessed(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(sysObstetricExamination).setReasonsForHRP(Mockito.<String[]>any());
        doNothing().when(sysObstetricExamination).setReasonsForHRPDB(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setReservedForChange(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setSfh(Mockito.<Double>any());
        doNothing().when(sysObstetricExamination).setSyncedBy(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(sysObstetricExamination).setVanID(Mockito.<Integer>any());
        doNothing().when(sysObstetricExamination).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setVehicalNo(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setVertebralDeformity(Mockito.<Boolean>any());
        doNothing().when(sysObstetricExamination).setVisitCode(Mockito.<Long>any());
        doNothing().when(sysObstetricExamination).setfHAndPOA_Interpretation(Mockito.<String>any());
        doNothing().when(sysObstetricExamination).setfHAndPOA_Status(Mockito.<String>any());
        sysObstetricExamination.setAbdominalScars("Abdominal Scars");
        sysObstetricExamination.setBenVisitID(1L);
        sysObstetricExamination.setBeneficiaryRegID(1L);
        sysObstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        sysObstetricExamination.setCreatedDate(mock(Timestamp.class));
        sysObstetricExamination.setDeleted(true);
        sysObstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        sysObstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        sysObstetricExamination.setFetalMovements("Fetal Movements");
        sysObstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        sysObstetricExamination.setFetalPresentation("Fetal Presentation");
        sysObstetricExamination.setFundalHeight("Fundal Height");
        sysObstetricExamination.setID(1L);
        sysObstetricExamination.setIsHRP(true);
        sysObstetricExamination.setLastModDate(mock(Timestamp.class));
        sysObstetricExamination.setLowLyingPlacenta(true);
        sysObstetricExamination.setMalPresentation(true);
        sysObstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        sysObstetricExamination.setParkingPlaceID(1);
        sysObstetricExamination.setProcessed("Processed");
        sysObstetricExamination.setProviderServiceMapID(1);
        sysObstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        sysObstetricExamination.setReasonsForHRPDB("Just cause");
        sysObstetricExamination.setReservedForChange("Reserved For Change");
        sysObstetricExamination.setSfh(10.0d);
        sysObstetricExamination.setSyncedBy("Synced By");
        sysObstetricExamination.setSyncedDate(mock(Timestamp.class));
        sysObstetricExamination.setVanID(1);
        sysObstetricExamination.setVanSerialNo(1L);
        sysObstetricExamination.setVehicalNo("Vehical No");
        sysObstetricExamination.setVertebralDeformity(true);
        sysObstetricExamination.setVisitCode(1L);
        sysObstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        sysObstetricExamination.setfHAndPOA_Status("F HAnd POA Status");
        when(sysObstetricExaminationRepo.getSysObstetricExaminationData(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(sysObstetricExamination);

        // Act
        aNCNurseServiceImpl.getSysObstetricExamination(1L, 1L);

        // Assert
        verify(sysObstetricExamination).getReasonsForHRPDB();
        verify(sysObstetricExamination).setAbdominalScars(eq("Abdominal Scars"));
        verify(sysObstetricExamination).setBenVisitID(isA(Long.class));
        verify(sysObstetricExamination).setBeneficiaryRegID(isA(Long.class));
        verify(sysObstetricExamination).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(sysObstetricExamination).setCreatedDate(isA(Timestamp.class));
        verify(sysObstetricExamination).setDeleted(isA(Boolean.class));
        verify(sysObstetricExamination).setFetalHeartRate_BeatsPerMinute(eq("Fetal Heart Rate Beats Per Minute"));
        verify(sysObstetricExamination).setFetalHeartSounds(eq("Fetal Heart Sounds"));
        verify(sysObstetricExamination).setFetalMovements(eq("Fetal Movements"));
        verify(sysObstetricExamination).setFetalPositionOrLie(eq("Fetal Position Or Lie"));
        verify(sysObstetricExamination).setFetalPresentation(eq("Fetal Presentation"));
        verify(sysObstetricExamination).setFundalHeight(eq("Fundal Height"));
        verify(sysObstetricExamination).setID(isA(Long.class));
        verify(sysObstetricExamination).setIsHRP(isA(Boolean.class));
        verify(sysObstetricExamination).setLastModDate(isA(Timestamp.class));
        verify(sysObstetricExamination).setLowLyingPlacenta(isA(Boolean.class));
        verify(sysObstetricExamination).setMalPresentation(isA(Boolean.class));
        verify(sysObstetricExamination).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(sysObstetricExamination).setParkingPlaceID(isA(Integer.class));
        verify(sysObstetricExamination).setProcessed(eq("Processed"));
        verify(sysObstetricExamination).setProviderServiceMapID(isA(Integer.class));
        verify(sysObstetricExamination).setReasonsForHRP(isA(String[].class));
        verify(sysObstetricExamination).setReasonsForHRPDB(eq("Just cause"));
        verify(sysObstetricExamination).setReservedForChange(eq("Reserved For Change"));
        verify(sysObstetricExamination).setSfh(isA(Double.class));
        verify(sysObstetricExamination).setSyncedBy(eq("Synced By"));
        verify(sysObstetricExamination).setSyncedDate(isA(Timestamp.class));
        verify(sysObstetricExamination).setVanID(isA(Integer.class));
        verify(sysObstetricExamination).setVanSerialNo(isA(Long.class));
        verify(sysObstetricExamination).setVehicalNo(eq("Vehical No"));
        verify(sysObstetricExamination).setVertebralDeformity(isA(Boolean.class));
        verify(sysObstetricExamination).setVisitCode(isA(Long.class));
        verify(sysObstetricExamination).setfHAndPOA_Interpretation(eq("F HAnd POA Interpretation"));
        verify(sysObstetricExamination).setfHAndPOA_Status(eq("F HAnd POA Status"));
        verify(sysObstetricExaminationRepo).getSysObstetricExaminationData(isA(Long.class), isA(Long.class));
    }

    @Test
    void testGetANCCareDetails() {
        // Arrange
        when(aNCCareRepo.getANCCareDetails(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        String actualANCCareDetails = aNCNurseServiceImpl.getANCCareDetails(1L, 1L);

        // Assert
        verify(aNCCareRepo).getANCCareDetails(isA(Long.class), isA(Long.class));
        assertEquals("null", actualANCCareDetails);
    }

    @Test
    void testGetANCWomenVaccineDetails() {
        // Arrange
        when(aNCWomenVaccineRepo.getANCWomenVaccineDetails(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualANCWomenVaccineDetails = aNCNurseServiceImpl.getANCWomenVaccineDetails(1L, 1L);

        // Assert
        verify(aNCWomenVaccineRepo).getANCWomenVaccineDetails(isA(Long.class), isA(Long.class));
        assertEquals("{}", actualANCWomenVaccineDetails);
    }

    @Test
    void testUpdateBenAdherenceDetails() {
        // Arrange
        when(benAdherenceRepo.updateBenAdherence(Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<Boolean>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(1);
        when(benAdherenceRepo.getBenAdherenceDetailsStatus(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Adherence Details Status");

        BenAdherence benAdherence = new BenAdherence();
        benAdherence.setBenVisitID(1L);
        benAdherence.setBeneficiaryRegID(1L);
        benAdherence.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benAdherence.setCreatedDate(mock(Timestamp.class));
        benAdherence.setDeleted(true);
        benAdherence.setDrugReason("Just cause");
        benAdherence.setID(1L);
        benAdherence.setLastModDate(mock(Timestamp.class));
        benAdherence.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benAdherence.setParkingPlaceID(1);
        benAdherence.setProcessed("Processed");
        benAdherence.setProgress("Progress");
        benAdherence.setProviderServiceMapID(1);
        benAdherence.setReferralReason("Just cause");
        benAdherence.setReservedForChange("Reserved For Change");
        benAdherence.setSyncedBy("Synced By");
        benAdherence.setSyncedDate(mock(Timestamp.class));
        benAdherence.setToDrugs(true);
        benAdherence.setToReferral(true);
        benAdherence.setVanSerialNo(1L);
        benAdherence.setVehicalNo("Vehical No");
        benAdherence.setVisitCode(1L);

        // Act
        int actualUpdateBenAdherenceDetailsResult = aNCNurseServiceImpl.updateBenAdherenceDetails(benAdherence);

        // Assert
        verify(benAdherenceRepo).getBenAdherenceDetailsStatus(isA(Long.class), isA(Long.class), isA(Long.class));
        verify(benAdherenceRepo).updateBenAdherence(isA(Boolean.class), eq("Just cause"), isA(Boolean.class),
                eq("Just cause"), eq("Progress"), eq("Jan 1, 2020 9:00am GMT+0100"), eq("U"), isA(Long.class), isA(Long.class),
                isA(Long.class));
        assertEquals(1, actualUpdateBenAdherenceDetailsResult);
    }

    @Test
    void testUpdateBenAdherenceDetails2() {
        // Arrange
        when(benAdherenceRepo.updateBenAdherence(Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<Boolean>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(1);
        when(benAdherenceRepo.getBenAdherenceDetailsStatus(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("N");

        BenAdherence benAdherence = new BenAdherence();
        benAdherence.setBenVisitID(1L);
        benAdherence.setBeneficiaryRegID(1L);
        benAdherence.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benAdherence.setCreatedDate(mock(Timestamp.class));
        benAdherence.setDeleted(true);
        benAdherence.setDrugReason("Just cause");
        benAdherence.setID(1L);
        benAdherence.setLastModDate(mock(Timestamp.class));
        benAdherence.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benAdherence.setParkingPlaceID(1);
        benAdherence.setProcessed("Processed");
        benAdherence.setProgress("Progress");
        benAdherence.setProviderServiceMapID(1);
        benAdherence.setReferralReason("Just cause");
        benAdherence.setReservedForChange("Reserved For Change");
        benAdherence.setSyncedBy("Synced By");
        benAdherence.setSyncedDate(mock(Timestamp.class));
        benAdherence.setToDrugs(true);
        benAdherence.setToReferral(true);
        benAdherence.setVanSerialNo(1L);
        benAdherence.setVehicalNo("Vehical No");
        benAdherence.setVisitCode(1L);

        // Act
        int actualUpdateBenAdherenceDetailsResult = aNCNurseServiceImpl.updateBenAdherenceDetails(benAdherence);

        // Assert
        verify(benAdherenceRepo).getBenAdherenceDetailsStatus(isA(Long.class), isA(Long.class), isA(Long.class));
        verify(benAdherenceRepo).updateBenAdherence(isA(Boolean.class), eq("Just cause"), isA(Boolean.class),
                eq("Just cause"), eq("Progress"), eq("Jan 1, 2020 9:00am GMT+0100"), eq("N"), isA(Long.class), isA(Long.class),
                isA(Long.class));
        assertEquals(1, actualUpdateBenAdherenceDetailsResult);
    }

    @Test
    void testUpdateBenAdherenceDetails3() {
        // Arrange
        when(benAdherenceRepo.updateBenAdherence(Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<Boolean>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(1);
        when(benAdherenceRepo.getBenAdherenceDetailsStatus(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(null);

        BenAdherence benAdherence = new BenAdherence();
        benAdherence.setBenVisitID(1L);
        benAdherence.setBeneficiaryRegID(1L);
        benAdherence.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benAdherence.setCreatedDate(mock(Timestamp.class));
        benAdherence.setDeleted(true);
        benAdherence.setDrugReason("Just cause");
        benAdherence.setID(1L);
        benAdherence.setLastModDate(mock(Timestamp.class));
        benAdherence.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benAdherence.setParkingPlaceID(1);
        benAdherence.setProcessed("Processed");
        benAdherence.setProgress("Progress");
        benAdherence.setProviderServiceMapID(1);
        benAdherence.setReferralReason("Just cause");
        benAdherence.setReservedForChange("Reserved For Change");
        benAdherence.setSyncedBy("Synced By");
        benAdherence.setSyncedDate(mock(Timestamp.class));
        benAdherence.setToDrugs(true);
        benAdherence.setToReferral(true);
        benAdherence.setVanSerialNo(1L);
        benAdherence.setVehicalNo("Vehical No");
        benAdherence.setVisitCode(1L);

        // Act
        int actualUpdateBenAdherenceDetailsResult = aNCNurseServiceImpl.updateBenAdherenceDetails(benAdherence);

        // Assert
        verify(benAdherenceRepo).getBenAdherenceDetailsStatus(isA(Long.class), isA(Long.class), isA(Long.class));
        verify(benAdherenceRepo).updateBenAdherence(isA(Boolean.class), eq("Just cause"), isA(Boolean.class),
                eq("Just cause"), eq("Progress"), eq("Jan 1, 2020 9:00am GMT+0100"), eq("N"), isA(Long.class), isA(Long.class),
                isA(Long.class));
        assertEquals(1, actualUpdateBenAdherenceDetailsResult);
    }

    @Test
    void testUpdateBenAncCareDetails() throws ParseException {
        // Arrange
        when(aNCCareRepo.updateANCCareDetails(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Date>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Date>any(), Mockito.<Boolean>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Integer>any(), Mockito.<Short>any())).thenReturn(1);
        when(aNCCareRepo.getBenANCCareDetailsStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben ANCCare Details Status");
        ANCCareDetails ancCareDetailsOBJ = mock(ANCCareDetails.class);
        when(ancCareDetailsOBJ.getPrimiGravida()).thenReturn(true);
        when(ancCareDetailsOBJ.getStillBirth()).thenReturn(1);
        when(ancCareDetailsOBJ.getBeneficiaryRegID()).thenReturn(1L);
        when(ancCareDetailsOBJ.getVisitCode()).thenReturn(1L);
        when(ancCareDetailsOBJ.getAbortions_A()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getGestationalAgeOrPeriodofAmenorrhea_POA()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getGravida_G()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getLivebirths_L()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getPara()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getPretermDeliveries_P()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getTermDeliveries_T()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getTrimesterNumber()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getBloodGroup()).thenReturn("Blood Group");
        when(ancCareDetailsOBJ.getComolaintType()).thenReturn("Comolaint Type");
        when(ancCareDetailsOBJ.getDescription()).thenReturn("The characteristics of someone or something");
        when(ancCareDetailsOBJ.getDuration()).thenReturn("Duration");
        when(ancCareDetailsOBJ.getExpDelDt()).thenReturn("N");
        when(ancCareDetailsOBJ.getLmpDate()).thenReturn("2020-03-01");
        when(ancCareDetailsOBJ.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(ancCareDetailsOBJ.getExpectedDateofDelivery()).thenReturn(mock(Date.class));
        when(ancCareDetailsOBJ.getLastMenstrualPeriod_LMP()).thenReturn(mock(Date.class));
        doNothing().when(ancCareDetailsOBJ).setAbortions_A(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setBenVisitID(Mockito.<Long>any());
        doNothing().when(ancCareDetailsOBJ).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(ancCareDetailsOBJ).setBloodGroup(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setComolaintType(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setCreatedBy(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetailsOBJ).setDeleted(Mockito.<Boolean>any());
        doNothing().when(ancCareDetailsOBJ).setDescription(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setDuration(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setExpDelDt(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setExpectedDateofDelivery(Mockito.<Date>any());
        doNothing().when(ancCareDetailsOBJ).setGestationalAgeOrPeriodofAmenorrhea_POA(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setGravida_G(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setID(Mockito.<Long>any());
        doNothing().when(ancCareDetailsOBJ).setLastMenstrualPeriod_LMP(Mockito.<Date>any());
        doNothing().when(ancCareDetailsOBJ).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetailsOBJ).setLivebirths_L(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setLmpDate(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setModifiedBy(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setPara(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(ancCareDetailsOBJ).setPretermDeliveries_P(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setPrimiGravida(Mockito.<Boolean>any());
        doNothing().when(ancCareDetailsOBJ).setProcessed(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(ancCareDetailsOBJ).setReservedForChange(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setStillBirth(Mockito.<Integer>any());
        doNothing().when(ancCareDetailsOBJ).setSyncedBy(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetailsOBJ).setTermDeliveries_T(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setTrimesterNumber(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setVanID(Mockito.<Integer>any());
        doNothing().when(ancCareDetailsOBJ).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(ancCareDetailsOBJ).setVehicalNo(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setVisitCode(Mockito.<Long>any());
        doNothing().when(ancCareDetailsOBJ).setVisitNo(Mockito.<Short>any());
        ancCareDetailsOBJ.setAbortions_A((short) 1);
        ancCareDetailsOBJ.setBenVisitID(1L);
        ancCareDetailsOBJ.setBeneficiaryRegID(1L);
        ancCareDetailsOBJ.setBloodGroup("Blood Group");
        ancCareDetailsOBJ.setComolaintType("Comolaint Type");
        ancCareDetailsOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancCareDetailsOBJ.setCreatedDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setDeleted(true);
        ancCareDetailsOBJ.setDescription("The characteristics of someone or something");
        ancCareDetailsOBJ.setDuration("Duration");
        ancCareDetailsOBJ.setExpDelDt("Exp Del Dt");
        ancCareDetailsOBJ.setExpectedDateofDelivery(mock(Date.class));
        ancCareDetailsOBJ.setGestationalAgeOrPeriodofAmenorrhea_POA((short) 1);
        ancCareDetailsOBJ.setGravida_G((short) 1);
        ancCareDetailsOBJ.setID(1L);
        ancCareDetailsOBJ.setLastMenstrualPeriod_LMP(mock(Date.class));
        ancCareDetailsOBJ.setLastModDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setLivebirths_L((short) 1);
        ancCareDetailsOBJ.setLmpDate("2020-03-01");
        ancCareDetailsOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancCareDetailsOBJ.setPara((short) 1);
        ancCareDetailsOBJ.setParkingPlaceID(1);
        ancCareDetailsOBJ.setPretermDeliveries_P((short) 1);
        ancCareDetailsOBJ.setPrimiGravida(true);
        ancCareDetailsOBJ.setProcessed("Processed");
        ancCareDetailsOBJ.setProviderServiceMapID(1);
        ancCareDetailsOBJ.setReservedForChange("Reserved For Change");
        ancCareDetailsOBJ.setStillBirth(1);
        ancCareDetailsOBJ.setSyncedBy("Synced By");
        ancCareDetailsOBJ.setSyncedDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setTermDeliveries_T((short) 1);
        ancCareDetailsOBJ.setTrimesterNumber((short) 1);
        ancCareDetailsOBJ.setVanID(1);
        ancCareDetailsOBJ.setVanSerialNo(1L);
        ancCareDetailsOBJ.setVehicalNo("Vehical No");
        ancCareDetailsOBJ.setVisitCode(1L);
        ancCareDetailsOBJ.setVisitNo((short) 1);

        // Act
        int actualUpdateBenAncCareDetailsResult = aNCNurseServiceImpl.updateBenAncCareDetails(ancCareDetailsOBJ);

        // Assert
        verify(ancCareDetailsOBJ).getAbortions_A();
        verify(ancCareDetailsOBJ, atLeast(1)).getBeneficiaryRegID();
        verify(ancCareDetailsOBJ).getBloodGroup();
        verify(ancCareDetailsOBJ).getComolaintType();
        verify(ancCareDetailsOBJ).getDescription();
        verify(ancCareDetailsOBJ).getDuration();
        verify(ancCareDetailsOBJ, atLeast(1)).getExpDelDt();
        verify(ancCareDetailsOBJ).getExpectedDateofDelivery();
        verify(ancCareDetailsOBJ).getGestationalAgeOrPeriodofAmenorrhea_POA();
        verify(ancCareDetailsOBJ).getGravida_G();
        verify(ancCareDetailsOBJ).getLastMenstrualPeriod_LMP();
        verify(ancCareDetailsOBJ).getLivebirths_L();
        verify(ancCareDetailsOBJ, atLeast(1)).getLmpDate();
        verify(ancCareDetailsOBJ).getModifiedBy();
        verify(ancCareDetailsOBJ).getPara();
        verify(ancCareDetailsOBJ).getPretermDeliveries_P();
        verify(ancCareDetailsOBJ).getPrimiGravida();
        verify(ancCareDetailsOBJ).getStillBirth();
        verify(ancCareDetailsOBJ).getTermDeliveries_T();
        verify(ancCareDetailsOBJ).getTrimesterNumber();
        verify(ancCareDetailsOBJ, atLeast(1)).getVisitCode();
        verify(ancCareDetailsOBJ).setAbortions_A(isA(Short.class));
        verify(ancCareDetailsOBJ).setBenVisitID(isA(Long.class));
        verify(ancCareDetailsOBJ).setBeneficiaryRegID(isA(Long.class));
        verify(ancCareDetailsOBJ).setBloodGroup(eq("Blood Group"));
        verify(ancCareDetailsOBJ).setComolaintType(eq("Comolaint Type"));
        verify(ancCareDetailsOBJ).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(ancCareDetailsOBJ).setCreatedDate(isA(Timestamp.class));
        verify(ancCareDetailsOBJ).setDeleted(isA(Boolean.class));
        verify(ancCareDetailsOBJ).setDescription(eq("The characteristics of someone or something"));
        verify(ancCareDetailsOBJ).setDuration(eq("Duration"));
        verify(ancCareDetailsOBJ).setExpDelDt(eq("Exp Del Dt"));
        verify(ancCareDetailsOBJ).setExpectedDateofDelivery(isA(Date.class));
        verify(ancCareDetailsOBJ).setGestationalAgeOrPeriodofAmenorrhea_POA(isA(Short.class));
        verify(ancCareDetailsOBJ).setGravida_G(isA(Short.class));
        verify(ancCareDetailsOBJ).setID(isA(Long.class));
        verify(ancCareDetailsOBJ, atLeast(1)).setLastMenstrualPeriod_LMP(Mockito.<Date>any());
        verify(ancCareDetailsOBJ).setLastModDate(isA(Timestamp.class));
        verify(ancCareDetailsOBJ).setLivebirths_L(isA(Short.class));
        verify(ancCareDetailsOBJ).setLmpDate(eq("2020-03-01"));
        verify(ancCareDetailsOBJ).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(ancCareDetailsOBJ).setPara(isA(Short.class));
        verify(ancCareDetailsOBJ).setParkingPlaceID(isA(Integer.class));
        verify(ancCareDetailsOBJ).setPretermDeliveries_P(isA(Short.class));
        verify(ancCareDetailsOBJ).setPrimiGravida(isA(Boolean.class));
        verify(ancCareDetailsOBJ).setProcessed(eq("Processed"));
        verify(ancCareDetailsOBJ).setProviderServiceMapID(isA(Integer.class));
        verify(ancCareDetailsOBJ).setReservedForChange(eq("Reserved For Change"));
        verify(ancCareDetailsOBJ).setStillBirth(isA(Integer.class));
        verify(ancCareDetailsOBJ).setSyncedBy(eq("Synced By"));
        verify(ancCareDetailsOBJ).setSyncedDate(isA(Timestamp.class));
        verify(ancCareDetailsOBJ).setTermDeliveries_T(isA(Short.class));
        verify(ancCareDetailsOBJ).setTrimesterNumber(isA(Short.class));
        verify(ancCareDetailsOBJ).setVanID(isA(Integer.class));
        verify(ancCareDetailsOBJ).setVanSerialNo(isA(Long.class));
        verify(ancCareDetailsOBJ).setVehicalNo(eq("Vehical No"));
        verify(ancCareDetailsOBJ).setVisitCode(isA(Long.class));
        verify(ancCareDetailsOBJ).setVisitNo(isA(Short.class));
        verify(aNCCareRepo).getBenANCCareDetailsStatus(isA(Long.class), isA(Long.class));
        verify(aNCCareRepo).updateANCCareDetails(eq("Comolaint Type"), eq("Duration"),
                eq("The characteristics of someone or something"), isA(Date.class), isA(Short.class), isA(Short.class),
                isA(Date.class), isA(Boolean.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class),
                isA(Short.class), eq("Blood Group"), eq("Jan 1, 2020 9:00am GMT+0100"), eq("U"), isA(Long.class),
                isA(Long.class), isA(Integer.class), isA(Short.class));
        assertEquals(1, actualUpdateBenAncCareDetailsResult);
    }

    @Test
    void testUpdateBenAncCareDetails2() throws ParseException {
        // Arrange
        when(aNCCareRepo.updateANCCareDetails(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Date>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Date>any(), Mockito.<Boolean>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Integer>any(), Mockito.<Short>any())).thenReturn(1);
        when(aNCCareRepo.getBenANCCareDetailsStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben ANCCare Details Status");
        ANCCareDetails ancCareDetailsOBJ = mock(ANCCareDetails.class);
        when(ancCareDetailsOBJ.getPrimiGravida()).thenReturn(true);
        when(ancCareDetailsOBJ.getStillBirth()).thenReturn(1);
        when(ancCareDetailsOBJ.getBeneficiaryRegID()).thenReturn(1L);
        when(ancCareDetailsOBJ.getVisitCode()).thenReturn(1L);
        when(ancCareDetailsOBJ.getAbortions_A()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getGestationalAgeOrPeriodofAmenorrhea_POA()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getGravida_G()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getLivebirths_L()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getPara()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getPretermDeliveries_P()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getTermDeliveries_T()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getTrimesterNumber()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getBloodGroup()).thenReturn("Blood Group");
        when(ancCareDetailsOBJ.getComolaintType()).thenReturn("Comolaint Type");
        when(ancCareDetailsOBJ.getDescription()).thenReturn("The characteristics of someone or something");
        when(ancCareDetailsOBJ.getDuration()).thenReturn("Duration");
        when(ancCareDetailsOBJ.getExpDelDt()).thenReturn(null);
        when(ancCareDetailsOBJ.getLmpDate()).thenReturn("2020-03-01");
        when(ancCareDetailsOBJ.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(ancCareDetailsOBJ.getExpectedDateofDelivery()).thenReturn(mock(Date.class));
        when(ancCareDetailsOBJ.getLastMenstrualPeriod_LMP()).thenReturn(mock(Date.class));
        doNothing().when(ancCareDetailsOBJ).setAbortions_A(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setBenVisitID(Mockito.<Long>any());
        doNothing().when(ancCareDetailsOBJ).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(ancCareDetailsOBJ).setBloodGroup(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setComolaintType(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setCreatedBy(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetailsOBJ).setDeleted(Mockito.<Boolean>any());
        doNothing().when(ancCareDetailsOBJ).setDescription(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setDuration(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setExpDelDt(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setExpectedDateofDelivery(Mockito.<Date>any());
        doNothing().when(ancCareDetailsOBJ).setGestationalAgeOrPeriodofAmenorrhea_POA(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setGravida_G(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setID(Mockito.<Long>any());
        doNothing().when(ancCareDetailsOBJ).setLastMenstrualPeriod_LMP(Mockito.<Date>any());
        doNothing().when(ancCareDetailsOBJ).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetailsOBJ).setLivebirths_L(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setLmpDate(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setModifiedBy(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setPara(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(ancCareDetailsOBJ).setPretermDeliveries_P(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setPrimiGravida(Mockito.<Boolean>any());
        doNothing().when(ancCareDetailsOBJ).setProcessed(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(ancCareDetailsOBJ).setReservedForChange(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setStillBirth(Mockito.<Integer>any());
        doNothing().when(ancCareDetailsOBJ).setSyncedBy(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetailsOBJ).setTermDeliveries_T(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setTrimesterNumber(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setVanID(Mockito.<Integer>any());
        doNothing().when(ancCareDetailsOBJ).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(ancCareDetailsOBJ).setVehicalNo(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setVisitCode(Mockito.<Long>any());
        doNothing().when(ancCareDetailsOBJ).setVisitNo(Mockito.<Short>any());
        ancCareDetailsOBJ.setAbortions_A((short) 1);
        ancCareDetailsOBJ.setBenVisitID(1L);
        ancCareDetailsOBJ.setBeneficiaryRegID(1L);
        ancCareDetailsOBJ.setBloodGroup("Blood Group");
        ancCareDetailsOBJ.setComolaintType("Comolaint Type");
        ancCareDetailsOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancCareDetailsOBJ.setCreatedDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setDeleted(true);
        ancCareDetailsOBJ.setDescription("The characteristics of someone or something");
        ancCareDetailsOBJ.setDuration("Duration");
        ancCareDetailsOBJ.setExpDelDt("Exp Del Dt");
        ancCareDetailsOBJ.setExpectedDateofDelivery(mock(Date.class));
        ancCareDetailsOBJ.setGestationalAgeOrPeriodofAmenorrhea_POA((short) 1);
        ancCareDetailsOBJ.setGravida_G((short) 1);
        ancCareDetailsOBJ.setID(1L);
        ancCareDetailsOBJ.setLastMenstrualPeriod_LMP(mock(Date.class));
        ancCareDetailsOBJ.setLastModDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setLivebirths_L((short) 1);
        ancCareDetailsOBJ.setLmpDate("2020-03-01");
        ancCareDetailsOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancCareDetailsOBJ.setPara((short) 1);
        ancCareDetailsOBJ.setParkingPlaceID(1);
        ancCareDetailsOBJ.setPretermDeliveries_P((short) 1);
        ancCareDetailsOBJ.setPrimiGravida(true);
        ancCareDetailsOBJ.setProcessed("Processed");
        ancCareDetailsOBJ.setProviderServiceMapID(1);
        ancCareDetailsOBJ.setReservedForChange("Reserved For Change");
        ancCareDetailsOBJ.setStillBirth(1);
        ancCareDetailsOBJ.setSyncedBy("Synced By");
        ancCareDetailsOBJ.setSyncedDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setTermDeliveries_T((short) 1);
        ancCareDetailsOBJ.setTrimesterNumber((short) 1);
        ancCareDetailsOBJ.setVanID(1);
        ancCareDetailsOBJ.setVanSerialNo(1L);
        ancCareDetailsOBJ.setVehicalNo("Vehical No");
        ancCareDetailsOBJ.setVisitCode(1L);
        ancCareDetailsOBJ.setVisitNo((short) 1);

        // Act
        int actualUpdateBenAncCareDetailsResult = aNCNurseServiceImpl.updateBenAncCareDetails(ancCareDetailsOBJ);

        // Assert
        verify(ancCareDetailsOBJ).getAbortions_A();
        verify(ancCareDetailsOBJ, atLeast(1)).getBeneficiaryRegID();
        verify(ancCareDetailsOBJ).getBloodGroup();
        verify(ancCareDetailsOBJ).getComolaintType();
        verify(ancCareDetailsOBJ).getDescription();
        verify(ancCareDetailsOBJ).getDuration();
        verify(ancCareDetailsOBJ).getExpDelDt();
        verify(ancCareDetailsOBJ).getExpectedDateofDelivery();
        verify(ancCareDetailsOBJ).getGestationalAgeOrPeriodofAmenorrhea_POA();
        verify(ancCareDetailsOBJ).getGravida_G();
        verify(ancCareDetailsOBJ).getLastMenstrualPeriod_LMP();
        verify(ancCareDetailsOBJ).getLivebirths_L();
        verify(ancCareDetailsOBJ, atLeast(1)).getLmpDate();
        verify(ancCareDetailsOBJ).getModifiedBy();
        verify(ancCareDetailsOBJ).getPara();
        verify(ancCareDetailsOBJ).getPretermDeliveries_P();
        verify(ancCareDetailsOBJ).getPrimiGravida();
        verify(ancCareDetailsOBJ).getStillBirth();
        verify(ancCareDetailsOBJ).getTermDeliveries_T();
        verify(ancCareDetailsOBJ).getTrimesterNumber();
        verify(ancCareDetailsOBJ, atLeast(1)).getVisitCode();
        verify(ancCareDetailsOBJ).setAbortions_A(isA(Short.class));
        verify(ancCareDetailsOBJ).setBenVisitID(isA(Long.class));
        verify(ancCareDetailsOBJ).setBeneficiaryRegID(isA(Long.class));
        verify(ancCareDetailsOBJ).setBloodGroup(eq("Blood Group"));
        verify(ancCareDetailsOBJ).setComolaintType(eq("Comolaint Type"));
        verify(ancCareDetailsOBJ).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(ancCareDetailsOBJ).setCreatedDate(isA(Timestamp.class));
        verify(ancCareDetailsOBJ).setDeleted(isA(Boolean.class));
        verify(ancCareDetailsOBJ).setDescription(eq("The characteristics of someone or something"));
        verify(ancCareDetailsOBJ).setDuration(eq("Duration"));
        verify(ancCareDetailsOBJ).setExpDelDt(eq("Exp Del Dt"));
        verify(ancCareDetailsOBJ).setExpectedDateofDelivery(isA(Date.class));
        verify(ancCareDetailsOBJ).setGestationalAgeOrPeriodofAmenorrhea_POA(isA(Short.class));
        verify(ancCareDetailsOBJ).setGravida_G(isA(Short.class));
        verify(ancCareDetailsOBJ).setID(isA(Long.class));
        verify(ancCareDetailsOBJ, atLeast(1)).setLastMenstrualPeriod_LMP(Mockito.<Date>any());
        verify(ancCareDetailsOBJ).setLastModDate(isA(Timestamp.class));
        verify(ancCareDetailsOBJ).setLivebirths_L(isA(Short.class));
        verify(ancCareDetailsOBJ).setLmpDate(eq("2020-03-01"));
        verify(ancCareDetailsOBJ).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(ancCareDetailsOBJ).setPara(isA(Short.class));
        verify(ancCareDetailsOBJ).setParkingPlaceID(isA(Integer.class));
        verify(ancCareDetailsOBJ).setPretermDeliveries_P(isA(Short.class));
        verify(ancCareDetailsOBJ).setPrimiGravida(isA(Boolean.class));
        verify(ancCareDetailsOBJ).setProcessed(eq("Processed"));
        verify(ancCareDetailsOBJ).setProviderServiceMapID(isA(Integer.class));
        verify(ancCareDetailsOBJ).setReservedForChange(eq("Reserved For Change"));
        verify(ancCareDetailsOBJ).setStillBirth(isA(Integer.class));
        verify(ancCareDetailsOBJ).setSyncedBy(eq("Synced By"));
        verify(ancCareDetailsOBJ).setSyncedDate(isA(Timestamp.class));
        verify(ancCareDetailsOBJ).setTermDeliveries_T(isA(Short.class));
        verify(ancCareDetailsOBJ).setTrimesterNumber(isA(Short.class));
        verify(ancCareDetailsOBJ).setVanID(isA(Integer.class));
        verify(ancCareDetailsOBJ).setVanSerialNo(isA(Long.class));
        verify(ancCareDetailsOBJ).setVehicalNo(eq("Vehical No"));
        verify(ancCareDetailsOBJ).setVisitCode(isA(Long.class));
        verify(ancCareDetailsOBJ).setVisitNo(isA(Short.class));
        verify(aNCCareRepo).getBenANCCareDetailsStatus(isA(Long.class), isA(Long.class));
        verify(aNCCareRepo).updateANCCareDetails(eq("Comolaint Type"), eq("Duration"),
                eq("The characteristics of someone or something"), isA(Date.class), isA(Short.class), isA(Short.class),
                isA(Date.class), isA(Boolean.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class),
                isA(Short.class), eq("Blood Group"), eq("Jan 1, 2020 9:00am GMT+0100"), eq("U"), isA(Long.class),
                isA(Long.class), isA(Integer.class), isA(Short.class));
        assertEquals(1, actualUpdateBenAncCareDetailsResult);
    }

    @Test
    void testUpdateBenAncCareDetails3() throws ParseException {
        // Arrange
        when(aNCCareRepo.updateANCCareDetails(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Date>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Date>any(), Mockito.<Boolean>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Integer>any(), Mockito.<Short>any())).thenReturn(1);
        when(aNCCareRepo.getBenANCCareDetailsStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben ANCCare Details Status");
        ANCCareDetails ancCareDetailsOBJ = mock(ANCCareDetails.class);
        when(ancCareDetailsOBJ.getPrimiGravida()).thenReturn(true);
        when(ancCareDetailsOBJ.getStillBirth()).thenReturn(1);
        when(ancCareDetailsOBJ.getBeneficiaryRegID()).thenReturn(1L);
        when(ancCareDetailsOBJ.getVisitCode()).thenReturn(1L);
        when(ancCareDetailsOBJ.getAbortions_A()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getGestationalAgeOrPeriodofAmenorrhea_POA()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getGravida_G()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getLivebirths_L()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getPara()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getPretermDeliveries_P()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getTermDeliveries_T()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getTrimesterNumber()).thenReturn((short) 1);
        when(ancCareDetailsOBJ.getBloodGroup()).thenReturn("Blood Group");
        when(ancCareDetailsOBJ.getComolaintType()).thenReturn("Comolaint Type");
        when(ancCareDetailsOBJ.getDescription()).thenReturn("The characteristics of someone or something");
        when(ancCareDetailsOBJ.getDuration()).thenReturn("Duration");
        when(ancCareDetailsOBJ.getExpDelDt()).thenReturn("");
        when(ancCareDetailsOBJ.getLmpDate()).thenReturn("2020-03-01");
        when(ancCareDetailsOBJ.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(ancCareDetailsOBJ.getExpectedDateofDelivery()).thenReturn(mock(Date.class));
        when(ancCareDetailsOBJ.getLastMenstrualPeriod_LMP()).thenReturn(mock(Date.class));
        doNothing().when(ancCareDetailsOBJ).setAbortions_A(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setBenVisitID(Mockito.<Long>any());
        doNothing().when(ancCareDetailsOBJ).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(ancCareDetailsOBJ).setBloodGroup(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setComolaintType(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setCreatedBy(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetailsOBJ).setDeleted(Mockito.<Boolean>any());
        doNothing().when(ancCareDetailsOBJ).setDescription(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setDuration(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setExpDelDt(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setExpectedDateofDelivery(Mockito.<Date>any());
        doNothing().when(ancCareDetailsOBJ).setGestationalAgeOrPeriodofAmenorrhea_POA(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setGravida_G(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setID(Mockito.<Long>any());
        doNothing().when(ancCareDetailsOBJ).setLastMenstrualPeriod_LMP(Mockito.<Date>any());
        doNothing().when(ancCareDetailsOBJ).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetailsOBJ).setLivebirths_L(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setLmpDate(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setModifiedBy(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setPara(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(ancCareDetailsOBJ).setPretermDeliveries_P(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setPrimiGravida(Mockito.<Boolean>any());
        doNothing().when(ancCareDetailsOBJ).setProcessed(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(ancCareDetailsOBJ).setReservedForChange(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setStillBirth(Mockito.<Integer>any());
        doNothing().when(ancCareDetailsOBJ).setSyncedBy(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(ancCareDetailsOBJ).setTermDeliveries_T(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setTrimesterNumber(Mockito.<Short>any());
        doNothing().when(ancCareDetailsOBJ).setVanID(Mockito.<Integer>any());
        doNothing().when(ancCareDetailsOBJ).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(ancCareDetailsOBJ).setVehicalNo(Mockito.<String>any());
        doNothing().when(ancCareDetailsOBJ).setVisitCode(Mockito.<Long>any());
        doNothing().when(ancCareDetailsOBJ).setVisitNo(Mockito.<Short>any());
        ancCareDetailsOBJ.setAbortions_A((short) 1);
        ancCareDetailsOBJ.setBenVisitID(1L);
        ancCareDetailsOBJ.setBeneficiaryRegID(1L);
        ancCareDetailsOBJ.setBloodGroup("Blood Group");
        ancCareDetailsOBJ.setComolaintType("Comolaint Type");
        ancCareDetailsOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        ancCareDetailsOBJ.setCreatedDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setDeleted(true);
        ancCareDetailsOBJ.setDescription("The characteristics of someone or something");
        ancCareDetailsOBJ.setDuration("Duration");
        ancCareDetailsOBJ.setExpDelDt("Exp Del Dt");
        ancCareDetailsOBJ.setExpectedDateofDelivery(mock(Date.class));
        ancCareDetailsOBJ.setGestationalAgeOrPeriodofAmenorrhea_POA((short) 1);
        ancCareDetailsOBJ.setGravida_G((short) 1);
        ancCareDetailsOBJ.setID(1L);
        ancCareDetailsOBJ.setLastMenstrualPeriod_LMP(mock(Date.class));
        ancCareDetailsOBJ.setLastModDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setLivebirths_L((short) 1);
        ancCareDetailsOBJ.setLmpDate("2020-03-01");
        ancCareDetailsOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        ancCareDetailsOBJ.setPara((short) 1);
        ancCareDetailsOBJ.setParkingPlaceID(1);
        ancCareDetailsOBJ.setPretermDeliveries_P((short) 1);
        ancCareDetailsOBJ.setPrimiGravida(true);
        ancCareDetailsOBJ.setProcessed("Processed");
        ancCareDetailsOBJ.setProviderServiceMapID(1);
        ancCareDetailsOBJ.setReservedForChange("Reserved For Change");
        ancCareDetailsOBJ.setStillBirth(1);
        ancCareDetailsOBJ.setSyncedBy("Synced By");
        ancCareDetailsOBJ.setSyncedDate(mock(Timestamp.class));
        ancCareDetailsOBJ.setTermDeliveries_T((short) 1);
        ancCareDetailsOBJ.setTrimesterNumber((short) 1);
        ancCareDetailsOBJ.setVanID(1);
        ancCareDetailsOBJ.setVanSerialNo(1L);
        ancCareDetailsOBJ.setVehicalNo("Vehical No");
        ancCareDetailsOBJ.setVisitCode(1L);
        ancCareDetailsOBJ.setVisitNo((short) 1);

        // Act
        int actualUpdateBenAncCareDetailsResult = aNCNurseServiceImpl.updateBenAncCareDetails(ancCareDetailsOBJ);

        // Assert
        verify(ancCareDetailsOBJ).getAbortions_A();
        verify(ancCareDetailsOBJ, atLeast(1)).getBeneficiaryRegID();
        verify(ancCareDetailsOBJ).getBloodGroup();
        verify(ancCareDetailsOBJ).getComolaintType();
        verify(ancCareDetailsOBJ).getDescription();
        verify(ancCareDetailsOBJ).getDuration();
        verify(ancCareDetailsOBJ, atLeast(1)).getExpDelDt();
        verify(ancCareDetailsOBJ).getExpectedDateofDelivery();
        verify(ancCareDetailsOBJ).getGestationalAgeOrPeriodofAmenorrhea_POA();
        verify(ancCareDetailsOBJ).getGravida_G();
        verify(ancCareDetailsOBJ).getLastMenstrualPeriod_LMP();
        verify(ancCareDetailsOBJ).getLivebirths_L();
        verify(ancCareDetailsOBJ, atLeast(1)).getLmpDate();
        verify(ancCareDetailsOBJ).getModifiedBy();
        verify(ancCareDetailsOBJ).getPara();
        verify(ancCareDetailsOBJ).getPretermDeliveries_P();
        verify(ancCareDetailsOBJ).getPrimiGravida();
        verify(ancCareDetailsOBJ).getStillBirth();
        verify(ancCareDetailsOBJ).getTermDeliveries_T();
        verify(ancCareDetailsOBJ).getTrimesterNumber();
        verify(ancCareDetailsOBJ, atLeast(1)).getVisitCode();
        verify(ancCareDetailsOBJ).setAbortions_A(isA(Short.class));
        verify(ancCareDetailsOBJ).setBenVisitID(isA(Long.class));
        verify(ancCareDetailsOBJ).setBeneficiaryRegID(isA(Long.class));
        verify(ancCareDetailsOBJ).setBloodGroup(eq("Blood Group"));
        verify(ancCareDetailsOBJ).setComolaintType(eq("Comolaint Type"));
        verify(ancCareDetailsOBJ).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(ancCareDetailsOBJ).setCreatedDate(isA(Timestamp.class));
        verify(ancCareDetailsOBJ).setDeleted(isA(Boolean.class));
        verify(ancCareDetailsOBJ).setDescription(eq("The characteristics of someone or something"));
        verify(ancCareDetailsOBJ).setDuration(eq("Duration"));
        verify(ancCareDetailsOBJ).setExpDelDt(eq("Exp Del Dt"));
        verify(ancCareDetailsOBJ).setExpectedDateofDelivery(isA(Date.class));
        verify(ancCareDetailsOBJ).setGestationalAgeOrPeriodofAmenorrhea_POA(isA(Short.class));
        verify(ancCareDetailsOBJ).setGravida_G(isA(Short.class));
        verify(ancCareDetailsOBJ).setID(isA(Long.class));
        verify(ancCareDetailsOBJ, atLeast(1)).setLastMenstrualPeriod_LMP(Mockito.<Date>any());
        verify(ancCareDetailsOBJ).setLastModDate(isA(Timestamp.class));
        verify(ancCareDetailsOBJ).setLivebirths_L(isA(Short.class));
        verify(ancCareDetailsOBJ).setLmpDate(eq("2020-03-01"));
        verify(ancCareDetailsOBJ).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(ancCareDetailsOBJ).setPara(isA(Short.class));
        verify(ancCareDetailsOBJ).setParkingPlaceID(isA(Integer.class));
        verify(ancCareDetailsOBJ).setPretermDeliveries_P(isA(Short.class));
        verify(ancCareDetailsOBJ).setPrimiGravida(isA(Boolean.class));
        verify(ancCareDetailsOBJ).setProcessed(eq("Processed"));
        verify(ancCareDetailsOBJ).setProviderServiceMapID(isA(Integer.class));
        verify(ancCareDetailsOBJ).setReservedForChange(eq("Reserved For Change"));
        verify(ancCareDetailsOBJ).setStillBirth(isA(Integer.class));
        verify(ancCareDetailsOBJ).setSyncedBy(eq("Synced By"));
        verify(ancCareDetailsOBJ).setSyncedDate(isA(Timestamp.class));
        verify(ancCareDetailsOBJ).setTermDeliveries_T(isA(Short.class));
        verify(ancCareDetailsOBJ).setTrimesterNumber(isA(Short.class));
        verify(ancCareDetailsOBJ).setVanID(isA(Integer.class));
        verify(ancCareDetailsOBJ).setVanSerialNo(isA(Long.class));
        verify(ancCareDetailsOBJ).setVehicalNo(eq("Vehical No"));
        verify(ancCareDetailsOBJ).setVisitCode(isA(Long.class));
        verify(ancCareDetailsOBJ).setVisitNo(isA(Short.class));
        verify(aNCCareRepo).getBenANCCareDetailsStatus(isA(Long.class), isA(Long.class));
        verify(aNCCareRepo).updateANCCareDetails(eq("Comolaint Type"), eq("Duration"),
                eq("The characteristics of someone or something"), isA(Date.class), isA(Short.class), isA(Short.class),
                isA(Date.class), isA(Boolean.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class),
                isA(Short.class), eq("Blood Group"), eq("Jan 1, 2020 9:00am GMT+0100"), eq("U"), isA(Long.class),
                isA(Long.class), isA(Integer.class), isA(Short.class));
        assertEquals(1, actualUpdateBenAncCareDetailsResult);
    }

    @Test
    void testUpdateBenAncImmunizationDetails() throws ParseException {
        // Arrange
        when(aNCWomenVaccineRepo.updateANCImmunizationDetails(Mockito.<String>any(), Mockito.<Date>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<String>any())).thenReturn(1);
        when(aNCWomenVaccineRepo.getBenANCWomenVaccineStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        int actualUpdateBenAncImmunizationDetailsResult = aNCNurseServiceImpl
                .updateBenAncImmunizationDetails(new WrapperAncImmunization());

        // Assert
        verify(aNCWomenVaccineRepo).getBenANCWomenVaccineStatus(isNull(), isNull());
        verify(aNCWomenVaccineRepo, atLeast(1)).updateANCImmunizationDetails(isNull(), isNull(), isNull(), isNull(),
                eq("N"), isNull(), isNull(), Mockito.<String>any());
        assertEquals(1, actualUpdateBenAncImmunizationDetailsResult);
    }

    @Test
    void testUpdateBenAncImmunizationDetails2() throws ParseException {
        // Arrange
        when(aNCWomenVaccineRepo.updateANCImmunizationDetails(Mockito.<String>any(), Mockito.<Date>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<String>any())).thenReturn(1);
        when(aNCWomenVaccineRepo.getBenANCWomenVaccineStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        int actualUpdateBenAncImmunizationDetailsResult = aNCNurseServiceImpl
                .updateBenAncImmunizationDetails(new WrapperAncImmunization(1L, 1L, 1, "Jan 1, 2020 8:00am GMT+0100", "TT-1",
                        "2020-03-01", "TT-1", "TT-1", "2020-03-01", "TT-1", "TT-1", "2020-03-01", "TT-1"));

        // Assert
        verify(aNCWomenVaccineRepo).getBenANCWomenVaccineStatus(isA(Long.class), isNull());
        verify(aNCWomenVaccineRepo, atLeast(1)).updateANCImmunizationDetails(eq("TT-1"), isA(Date.class), eq("TT-1"),
                isNull(), eq("N"), isA(Long.class), isNull(), Mockito.<String>any());
        assertEquals(1, actualUpdateBenAncImmunizationDetailsResult);
    }

    @Test
    void testUpdateBenAncImmunizationDetails3() throws ParseException {
        // Arrange
        when(aNCWomenVaccineRepo.updateANCImmunizationDetails(Mockito.<String>any(), Mockito.<Date>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<String>any())).thenReturn(1);
        when(aNCWomenVaccineRepo.getBenANCWomenVaccineStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        WrapperAncImmunization wrapperAncImmunization = mock(WrapperAncImmunization.class);
        when(wrapperAncImmunization.getParkingPlaceID()).thenReturn(1);
        when(wrapperAncImmunization.getProviderServiceMapID()).thenReturn(1);
        when(wrapperAncImmunization.getVanID()).thenReturn(1);
        when(wrapperAncImmunization.getBenVisitID()).thenReturn(1L);
        when(wrapperAncImmunization.getBeneficiaryRegID()).thenReturn(1L);
        when(wrapperAncImmunization.getVisitCode()).thenReturn(1L);
        when(wrapperAncImmunization.gettT1ID()).thenReturn(1L);
        when(wrapperAncImmunization.gettT2ID()).thenReturn(1L);
        when(wrapperAncImmunization.gettT3ID()).thenReturn(1L);
        when(wrapperAncImmunization.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(wrapperAncImmunization.getDateReceivedForTT_1()).thenReturn("2020-03-01");
        when(wrapperAncImmunization.getDateReceivedForTT_2()).thenReturn("2020-03-01");
        when(wrapperAncImmunization.getDateReceivedForTT_3()).thenReturn("2020-03-01");
        when(wrapperAncImmunization.getFacilityNameOfTT_1()).thenReturn("Facility Name Of TT 1");
        when(wrapperAncImmunization.getFacilityNameOfTT_2()).thenReturn("Facility Name Of TT 2");
        when(wrapperAncImmunization.getFacilityNameOfTT_3()).thenReturn("Facility Name Of TT 3");
        when(wrapperAncImmunization.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(wrapperAncImmunization.gettT_1Status()).thenReturn("Gett T 1 Status");
        when(wrapperAncImmunization.gettT_2Status()).thenReturn("Gett T 2 Status");
        when(wrapperAncImmunization.gettT_3Status()).thenReturn("Gett T 3 Status");

        // Act
        int actualUpdateBenAncImmunizationDetailsResult = aNCNurseServiceImpl
                .updateBenAncImmunizationDetails(wrapperAncImmunization);

        // Assert
        verify(wrapperAncImmunization, atLeast(1)).getBenVisitID();
        verify(wrapperAncImmunization, atLeast(1)).getBeneficiaryRegID();
        verify(wrapperAncImmunization, atLeast(1)).getCreatedBy();
        verify(wrapperAncImmunization, atLeast(1)).getDateReceivedForTT_1();
        verify(wrapperAncImmunization, atLeast(1)).getDateReceivedForTT_2();
        verify(wrapperAncImmunization, atLeast(1)).getDateReceivedForTT_3();
        verify(wrapperAncImmunization).getFacilityNameOfTT_1();
        verify(wrapperAncImmunization).getFacilityNameOfTT_2();
        verify(wrapperAncImmunization).getFacilityNameOfTT_3();
        verify(wrapperAncImmunization, atLeast(1)).getModifiedBy();
        verify(wrapperAncImmunization, atLeast(1)).getParkingPlaceID();
        verify(wrapperAncImmunization, atLeast(1)).getProviderServiceMapID();
        verify(wrapperAncImmunization, atLeast(1)).getVanID();
        verify(wrapperAncImmunization, atLeast(1)).getVisitCode();
        verify(wrapperAncImmunization).gettT1ID();
        verify(wrapperAncImmunization).gettT2ID();
        verify(wrapperAncImmunization).gettT3ID();
        verify(wrapperAncImmunization).gettT_1Status();
        verify(wrapperAncImmunization).gettT_2Status();
        verify(wrapperAncImmunization).gettT_3Status();
        verify(aNCWomenVaccineRepo).getBenANCWomenVaccineStatus(isA(Long.class), isA(Long.class));
        verify(aNCWomenVaccineRepo, atLeast(1)).updateANCImmunizationDetails(Mockito.<String>any(), isA(Date.class),
                Mockito.<String>any(), eq("Jan 1, 2020 9:00am GMT+0100"), eq("N"), isA(Long.class), isA(Long.class),
                Mockito.<String>any());
        assertEquals(1, actualUpdateBenAncImmunizationDetailsResult);
    }

    @Test
    void testUpdateBenAncImmunizationDetails4() throws ParseException {
        // Arrange
        when(aNCWomenVaccineRepo.updateANCImmunizationDetails(Mockito.<String>any(), Mockito.<Date>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<String>any())).thenReturn(1);
        when(aNCWomenVaccineRepo.getBenANCWomenVaccineStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        WrapperAncImmunization wrapperAncImmunization = mock(WrapperAncImmunization.class);
        when(wrapperAncImmunization.getParkingPlaceID()).thenReturn(1);
        when(wrapperAncImmunization.getProviderServiceMapID()).thenReturn(1);
        when(wrapperAncImmunization.getVanID()).thenReturn(1);
        when(wrapperAncImmunization.getBenVisitID()).thenReturn(1L);
        when(wrapperAncImmunization.getBeneficiaryRegID()).thenReturn(1L);
        when(wrapperAncImmunization.getVisitCode()).thenReturn(1L);
        when(wrapperAncImmunization.gettT1ID()).thenReturn(1L);
        when(wrapperAncImmunization.gettT2ID()).thenReturn(1L);
        when(wrapperAncImmunization.gettT3ID()).thenReturn(1L);
        when(wrapperAncImmunization.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(wrapperAncImmunization.getDateReceivedForTT_1()).thenReturn("TT-1");
        when(wrapperAncImmunization.getDateReceivedForTT_2()).thenReturn("2020-03-01");
        when(wrapperAncImmunization.getDateReceivedForTT_3()).thenReturn("2020-03-01");
        when(wrapperAncImmunization.getFacilityNameOfTT_1()).thenReturn("Facility Name Of TT 1");
        when(wrapperAncImmunization.getFacilityNameOfTT_2()).thenReturn("Facility Name Of TT 2");
        when(wrapperAncImmunization.getFacilityNameOfTT_3()).thenReturn("Facility Name Of TT 3");
        when(wrapperAncImmunization.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(wrapperAncImmunization.gettT_1Status()).thenReturn("Gett T 1 Status");
        when(wrapperAncImmunization.gettT_2Status()).thenReturn("Gett T 2 Status");
        when(wrapperAncImmunization.gettT_3Status()).thenReturn("Gett T 3 Status");

        // Act
        int actualUpdateBenAncImmunizationDetailsResult = aNCNurseServiceImpl
                .updateBenAncImmunizationDetails(wrapperAncImmunization);

        // Assert
        verify(wrapperAncImmunization, atLeast(1)).getBenVisitID();
        verify(wrapperAncImmunization, atLeast(1)).getBeneficiaryRegID();
        verify(wrapperAncImmunization, atLeast(1)).getCreatedBy();
        verify(wrapperAncImmunization, atLeast(1)).getDateReceivedForTT_1();
        verify(wrapperAncImmunization, atLeast(1)).getDateReceivedForTT_2();
        verify(wrapperAncImmunization, atLeast(1)).getDateReceivedForTT_3();
        verify(wrapperAncImmunization).getFacilityNameOfTT_1();
        verify(wrapperAncImmunization).getFacilityNameOfTT_2();
        verify(wrapperAncImmunization).getFacilityNameOfTT_3();
        verify(wrapperAncImmunization, atLeast(1)).getModifiedBy();
        verify(wrapperAncImmunization, atLeast(1)).getParkingPlaceID();
        verify(wrapperAncImmunization, atLeast(1)).getProviderServiceMapID();
        verify(wrapperAncImmunization, atLeast(1)).getVanID();
        verify(wrapperAncImmunization, atLeast(1)).getVisitCode();
        verify(wrapperAncImmunization).gettT1ID();
        verify(wrapperAncImmunization).gettT2ID();
        verify(wrapperAncImmunization).gettT3ID();
        verify(wrapperAncImmunization).gettT_1Status();
        verify(wrapperAncImmunization).gettT_2Status();
        verify(wrapperAncImmunization).gettT_3Status();
        verify(aNCWomenVaccineRepo).getBenANCWomenVaccineStatus(isA(Long.class), isA(Long.class));
        verify(aNCWomenVaccineRepo, atLeast(1)).updateANCImmunizationDetails(Mockito.<String>any(), Mockito.<Date>any(),
                Mockito.<String>any(), eq("Jan 1, 2020 9:00am GMT+0100"), eq("N"), isA(Long.class), isA(Long.class),
                Mockito.<String>any());
        assertEquals(1, actualUpdateBenAncImmunizationDetailsResult);
    }

    @Test
    void testUpdateBenAncImmunizationDetails5() throws ParseException {
        // Arrange
        when(aNCWomenVaccineRepo.updateANCImmunizationDetails(Mockito.<String>any(), Mockito.<Date>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<String>any())).thenReturn(1);
        when(aNCWomenVaccineRepo.getBenANCWomenVaccineStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        WrapperAncImmunization wrapperAncImmunization = mock(WrapperAncImmunization.class);
        when(wrapperAncImmunization.getParkingPlaceID()).thenReturn(1);
        when(wrapperAncImmunization.getProviderServiceMapID()).thenReturn(1);
        when(wrapperAncImmunization.getVanID()).thenReturn(1);
        when(wrapperAncImmunization.getBenVisitID()).thenReturn(1L);
        when(wrapperAncImmunization.getBeneficiaryRegID()).thenReturn(1L);
        when(wrapperAncImmunization.getVisitCode()).thenReturn(1L);
        when(wrapperAncImmunization.gettT1ID()).thenReturn(1L);
        when(wrapperAncImmunization.gettT2ID()).thenReturn(1L);
        when(wrapperAncImmunization.gettT3ID()).thenReturn(1L);
        when(wrapperAncImmunization.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(wrapperAncImmunization.getDateReceivedForTT_1()).thenReturn("2020-03-01");
        when(wrapperAncImmunization.getDateReceivedForTT_2()).thenReturn("TT-1");
        when(wrapperAncImmunization.getDateReceivedForTT_3()).thenReturn("2020-03-01");
        when(wrapperAncImmunization.getFacilityNameOfTT_1()).thenReturn("Facility Name Of TT 1");
        when(wrapperAncImmunization.getFacilityNameOfTT_2()).thenReturn("Facility Name Of TT 2");
        when(wrapperAncImmunization.getFacilityNameOfTT_3()).thenReturn("Facility Name Of TT 3");
        when(wrapperAncImmunization.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(wrapperAncImmunization.gettT_1Status()).thenReturn("Gett T 1 Status");
        when(wrapperAncImmunization.gettT_2Status()).thenReturn("Gett T 2 Status");
        when(wrapperAncImmunization.gettT_3Status()).thenReturn("Gett T 3 Status");

        // Act
        int actualUpdateBenAncImmunizationDetailsResult = aNCNurseServiceImpl
                .updateBenAncImmunizationDetails(wrapperAncImmunization);

        // Assert
        verify(wrapperAncImmunization, atLeast(1)).getBenVisitID();
        verify(wrapperAncImmunization, atLeast(1)).getBeneficiaryRegID();
        verify(wrapperAncImmunization, atLeast(1)).getCreatedBy();
        verify(wrapperAncImmunization, atLeast(1)).getDateReceivedForTT_1();
        verify(wrapperAncImmunization, atLeast(1)).getDateReceivedForTT_2();
        verify(wrapperAncImmunization, atLeast(1)).getDateReceivedForTT_3();
        verify(wrapperAncImmunization).getFacilityNameOfTT_1();
        verify(wrapperAncImmunization).getFacilityNameOfTT_2();
        verify(wrapperAncImmunization).getFacilityNameOfTT_3();
        verify(wrapperAncImmunization, atLeast(1)).getModifiedBy();
        verify(wrapperAncImmunization, atLeast(1)).getParkingPlaceID();
        verify(wrapperAncImmunization, atLeast(1)).getProviderServiceMapID();
        verify(wrapperAncImmunization, atLeast(1)).getVanID();
        verify(wrapperAncImmunization, atLeast(1)).getVisitCode();
        verify(wrapperAncImmunization).gettT1ID();
        verify(wrapperAncImmunization).gettT2ID();
        verify(wrapperAncImmunization).gettT3ID();
        verify(wrapperAncImmunization).gettT_1Status();
        verify(wrapperAncImmunization).gettT_2Status();
        verify(wrapperAncImmunization).gettT_3Status();
        verify(aNCWomenVaccineRepo).getBenANCWomenVaccineStatus(isA(Long.class), isA(Long.class));
        verify(aNCWomenVaccineRepo, atLeast(1)).updateANCImmunizationDetails(Mockito.<String>any(), Mockito.<Date>any(),
                Mockito.<String>any(), eq("Jan 1, 2020 9:00am GMT+0100"), eq("N"), isA(Long.class), isA(Long.class),
                Mockito.<String>any());
        assertEquals(1, actualUpdateBenAncImmunizationDetailsResult);
    }

    @Test
    void testUpdateBenAncImmunizationDetails6() throws ParseException {
        // Arrange
        when(aNCWomenVaccineRepo.updateANCImmunizationDetails(Mockito.<String>any(), Mockito.<Date>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<String>any())).thenReturn(1);
        when(aNCWomenVaccineRepo.getBenANCWomenVaccineStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        WrapperAncImmunization wrapperAncImmunization = mock(WrapperAncImmunization.class);
        when(wrapperAncImmunization.getParkingPlaceID()).thenReturn(1);
        when(wrapperAncImmunization.getProviderServiceMapID()).thenReturn(1);
        when(wrapperAncImmunization.getVanID()).thenReturn(1);
        when(wrapperAncImmunization.getBenVisitID()).thenReturn(1L);
        when(wrapperAncImmunization.getBeneficiaryRegID()).thenReturn(1L);
        when(wrapperAncImmunization.getVisitCode()).thenReturn(1L);
        when(wrapperAncImmunization.gettT1ID()).thenReturn(1L);
        when(wrapperAncImmunization.gettT2ID()).thenReturn(1L);
        when(wrapperAncImmunization.gettT3ID()).thenReturn(1L);
        when(wrapperAncImmunization.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(wrapperAncImmunization.getDateReceivedForTT_1()).thenReturn("2020-03-01");
        when(wrapperAncImmunization.getDateReceivedForTT_2()).thenReturn("2020-03-01");
        when(wrapperAncImmunization.getDateReceivedForTT_3()).thenReturn("TT-1");
        when(wrapperAncImmunization.getFacilityNameOfTT_1()).thenReturn("Facility Name Of TT 1");
        when(wrapperAncImmunization.getFacilityNameOfTT_2()).thenReturn("Facility Name Of TT 2");
        when(wrapperAncImmunization.getFacilityNameOfTT_3()).thenReturn("Facility Name Of TT 3");
        when(wrapperAncImmunization.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(wrapperAncImmunization.gettT_1Status()).thenReturn("Gett T 1 Status");
        when(wrapperAncImmunization.gettT_2Status()).thenReturn("Gett T 2 Status");
        when(wrapperAncImmunization.gettT_3Status()).thenReturn("Gett T 3 Status");

        // Act
        int actualUpdateBenAncImmunizationDetailsResult = aNCNurseServiceImpl
                .updateBenAncImmunizationDetails(wrapperAncImmunization);

        // Assert
        verify(wrapperAncImmunization, atLeast(1)).getBenVisitID();
        verify(wrapperAncImmunization, atLeast(1)).getBeneficiaryRegID();
        verify(wrapperAncImmunization, atLeast(1)).getCreatedBy();
        verify(wrapperAncImmunization, atLeast(1)).getDateReceivedForTT_1();
        verify(wrapperAncImmunization, atLeast(1)).getDateReceivedForTT_2();
        verify(wrapperAncImmunization, atLeast(1)).getDateReceivedForTT_3();
        verify(wrapperAncImmunization).getFacilityNameOfTT_1();
        verify(wrapperAncImmunization).getFacilityNameOfTT_2();
        verify(wrapperAncImmunization).getFacilityNameOfTT_3();
        verify(wrapperAncImmunization, atLeast(1)).getModifiedBy();
        verify(wrapperAncImmunization, atLeast(1)).getParkingPlaceID();
        verify(wrapperAncImmunization, atLeast(1)).getProviderServiceMapID();
        verify(wrapperAncImmunization, atLeast(1)).getVanID();
        verify(wrapperAncImmunization, atLeast(1)).getVisitCode();
        verify(wrapperAncImmunization).gettT1ID();
        verify(wrapperAncImmunization).gettT2ID();
        verify(wrapperAncImmunization).gettT3ID();
        verify(wrapperAncImmunization).gettT_1Status();
        verify(wrapperAncImmunization).gettT_2Status();
        verify(wrapperAncImmunization).gettT_3Status();
        verify(aNCWomenVaccineRepo).getBenANCWomenVaccineStatus(isA(Long.class), isA(Long.class));
        verify(aNCWomenVaccineRepo, atLeast(1)).updateANCImmunizationDetails(Mockito.<String>any(), Mockito.<Date>any(),
                Mockito.<String>any(), eq("Jan 1, 2020 9:00am GMT+0100"), eq("N"), isA(Long.class), isA(Long.class),
                Mockito.<String>any());
        assertEquals(1, actualUpdateBenAncImmunizationDetailsResult);
    }

    @Test
    void testUpdateSysObstetricExamination() {
        // Arrange
        when(sysObstetricExaminationRepo.updateSysObstetricExamination(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Double>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<String>any())).thenReturn(1);
        when(sysObstetricExaminationRepo.getBenObstetricExaminationStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Obstetric Examination Status");

        SysObstetricExamination obstetricExamination = new SysObstetricExamination();
        obstetricExamination.setAbdominalScars("Abdominal Scars");
        obstetricExamination.setBenVisitID(1L);
        obstetricExamination.setBeneficiaryRegID(1L);
        obstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        obstetricExamination.setCreatedDate(mock(Timestamp.class));
        obstetricExamination.setDeleted(true);
        obstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        obstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        obstetricExamination.setFetalMovements("Fetal Movements");
        obstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        obstetricExamination.setFetalPresentation("Fetal Presentation");
        obstetricExamination.setFundalHeight("Fundal Height");
        obstetricExamination.setID(1L);
        obstetricExamination.setIsHRP(true);
        obstetricExamination.setLastModDate(mock(Timestamp.class));
        obstetricExamination.setLowLyingPlacenta(true);
        obstetricExamination.setMalPresentation(true);
        obstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        obstetricExamination.setParkingPlaceID(1);
        obstetricExamination.setProcessed("Processed");
        obstetricExamination.setProviderServiceMapID(1);
        obstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        obstetricExamination.setReasonsForHRPDB("Just cause");
        obstetricExamination.setReservedForChange("Reserved For Change");
        obstetricExamination.setSfh(10.0d);
        obstetricExamination.setSyncedBy("Synced By");
        obstetricExamination.setSyncedDate(mock(Timestamp.class));
        obstetricExamination.setVanID(1);
        obstetricExamination.setVanSerialNo(1L);
        obstetricExamination.setVehicalNo("Vehical No");
        obstetricExamination.setVertebralDeformity(true);
        obstetricExamination.setVisitCode(1L);
        obstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        obstetricExamination.setfHAndPOA_Status("F HAnd POA Status");

        // Act
        int actualUpdateSysObstetricExaminationResult = aNCNurseServiceImpl
                .updateSysObstetricExamination(obstetricExamination);

        // Assert
        verify(sysObstetricExaminationRepo).getBenObstetricExaminationStatus(isA(Long.class), isA(Long.class));
        verify(sysObstetricExaminationRepo).updateSysObstetricExamination(eq("Fundal Height"), eq("F HAnd POA Status"),
                eq("F HAnd POA Interpretation"), eq("Fetal Movements"), eq("Fetal Heart Sounds"),
                eq("Fetal Heart Rate Beats Per Minute"), eq("Fetal Position Or Lie"), eq("Fetal Presentation"),
                eq("Abdominal Scars"), isA(Double.class), eq("Jan 1, 2020 9:00am GMT+0100"), eq("U"), isA(Long.class),
                isA(Long.class), isA(Boolean.class), isA(Boolean.class), isA(Boolean.class), isA(Boolean.class),
                eq("Just cause"));
        assertEquals(1, actualUpdateSysObstetricExaminationResult);
    }

    @Test
    void testUpdateSysObstetricExamination2() {
        // Arrange
        when(sysObstetricExaminationRepo.updateSysObstetricExamination(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Double>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<String>any())).thenReturn(1);
        when(sysObstetricExaminationRepo.getBenObstetricExaminationStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("N");

        SysObstetricExamination obstetricExamination = new SysObstetricExamination();
        obstetricExamination.setAbdominalScars("Abdominal Scars");
        obstetricExamination.setBenVisitID(1L);
        obstetricExamination.setBeneficiaryRegID(1L);
        obstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        obstetricExamination.setCreatedDate(mock(Timestamp.class));
        obstetricExamination.setDeleted(true);
        obstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        obstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        obstetricExamination.setFetalMovements("Fetal Movements");
        obstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        obstetricExamination.setFetalPresentation("Fetal Presentation");
        obstetricExamination.setFundalHeight("Fundal Height");
        obstetricExamination.setID(1L);
        obstetricExamination.setIsHRP(true);
        obstetricExamination.setLastModDate(mock(Timestamp.class));
        obstetricExamination.setLowLyingPlacenta(true);
        obstetricExamination.setMalPresentation(true);
        obstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        obstetricExamination.setParkingPlaceID(1);
        obstetricExamination.setProcessed("Processed");
        obstetricExamination.setProviderServiceMapID(1);
        obstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        obstetricExamination.setReasonsForHRPDB("Just cause");
        obstetricExamination.setReservedForChange("Reserved For Change");
        obstetricExamination.setSfh(10.0d);
        obstetricExamination.setSyncedBy("Synced By");
        obstetricExamination.setSyncedDate(mock(Timestamp.class));
        obstetricExamination.setVanID(1);
        obstetricExamination.setVanSerialNo(1L);
        obstetricExamination.setVehicalNo("Vehical No");
        obstetricExamination.setVertebralDeformity(true);
        obstetricExamination.setVisitCode(1L);
        obstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        obstetricExamination.setfHAndPOA_Status("F HAnd POA Status");

        // Act
        int actualUpdateSysObstetricExaminationResult = aNCNurseServiceImpl
                .updateSysObstetricExamination(obstetricExamination);

        // Assert
        verify(sysObstetricExaminationRepo).getBenObstetricExaminationStatus(isA(Long.class), isA(Long.class));
        verify(sysObstetricExaminationRepo).updateSysObstetricExamination(eq("Fundal Height"), eq("F HAnd POA Status"),
                eq("F HAnd POA Interpretation"), eq("Fetal Movements"), eq("Fetal Heart Sounds"),
                eq("Fetal Heart Rate Beats Per Minute"), eq("Fetal Position Or Lie"), eq("Fetal Presentation"),
                eq("Abdominal Scars"), isA(Double.class), eq("Jan 1, 2020 9:00am GMT+0100"), eq("N"), isA(Long.class),
                isA(Long.class), isA(Boolean.class), isA(Boolean.class), isA(Boolean.class), isA(Boolean.class),
                eq("Just cause"));
        assertEquals(1, actualUpdateSysObstetricExaminationResult);
    }

    @Test
    void testUpdateSysObstetricExamination3() {
        // Arrange
        when(sysObstetricExaminationRepo.updateSysObstetricExamination(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Double>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<String>any())).thenReturn(1);
        when(sysObstetricExaminationRepo.getBenObstetricExaminationStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(null);

        SysObstetricExamination obstetricExamination = new SysObstetricExamination();
        obstetricExamination.setAbdominalScars("Abdominal Scars");
        obstetricExamination.setBenVisitID(1L);
        obstetricExamination.setBeneficiaryRegID(1L);
        obstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        obstetricExamination.setCreatedDate(mock(Timestamp.class));
        obstetricExamination.setDeleted(true);
        obstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        obstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        obstetricExamination.setFetalMovements("Fetal Movements");
        obstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        obstetricExamination.setFetalPresentation("Fetal Presentation");
        obstetricExamination.setFundalHeight("Fundal Height");
        obstetricExamination.setID(1L);
        obstetricExamination.setIsHRP(true);
        obstetricExamination.setLastModDate(mock(Timestamp.class));
        obstetricExamination.setLowLyingPlacenta(true);
        obstetricExamination.setMalPresentation(true);
        obstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        obstetricExamination.setParkingPlaceID(1);
        obstetricExamination.setProcessed("Processed");
        obstetricExamination.setProviderServiceMapID(1);
        obstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        obstetricExamination.setReasonsForHRPDB("Just cause");
        obstetricExamination.setReservedForChange("Reserved For Change");
        obstetricExamination.setSfh(10.0d);
        obstetricExamination.setSyncedBy("Synced By");
        obstetricExamination.setSyncedDate(mock(Timestamp.class));
        obstetricExamination.setVanID(1);
        obstetricExamination.setVanSerialNo(1L);
        obstetricExamination.setVehicalNo("Vehical No");
        obstetricExamination.setVertebralDeformity(true);
        obstetricExamination.setVisitCode(1L);
        obstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        obstetricExamination.setfHAndPOA_Status("F HAnd POA Status");

        // Act
        int actualUpdateSysObstetricExaminationResult = aNCNurseServiceImpl
                .updateSysObstetricExamination(obstetricExamination);

        // Assert
        verify(sysObstetricExaminationRepo).getBenObstetricExaminationStatus(isA(Long.class), isA(Long.class));
        verify(sysObstetricExaminationRepo).updateSysObstetricExamination(eq("Fundal Height"), eq("F HAnd POA Status"),
                eq("F HAnd POA Interpretation"), eq("Fetal Movements"), eq("Fetal Heart Sounds"),
                eq("Fetal Heart Rate Beats Per Minute"), eq("Fetal Position Or Lie"), eq("Fetal Presentation"),
                eq("Abdominal Scars"), isA(Double.class), eq("Jan 1, 2020 9:00am GMT+0100"), eq("N"), isA(Long.class),
                isA(Long.class), isA(Boolean.class), isA(Boolean.class), isA(Boolean.class), isA(Boolean.class),
                eq("Just cause"));
        assertEquals(1, actualUpdateSysObstetricExaminationResult);
    }

    @Test
    void testUpdateSysObstetricExamination4() {
        // Arrange
        when(sysObstetricExaminationRepo.updateSysObstetricExamination(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Double>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Boolean>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<String>any())).thenReturn(1);
        when(sysObstetricExaminationRepo.getBenObstetricExaminationStatus(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Ben Obstetric Examination Status");
        SysObstetricExamination obstetricExamination = mock(SysObstetricExamination.class);
        when(obstetricExamination.getIsHRP()).thenReturn(true);
        when(obstetricExamination.getLowLyingPlacenta()).thenReturn(true);
        when(obstetricExamination.getMalPresentation()).thenReturn(true);
        when(obstetricExamination.getVertebralDeformity()).thenReturn(true);
        when(obstetricExamination.getSfh()).thenReturn(10.0d);
        when(obstetricExamination.getBeneficiaryRegID()).thenReturn(1L);
        when(obstetricExamination.getVisitCode()).thenReturn(1L);
        when(obstetricExamination.getAbdominalScars()).thenReturn("Abdominal Scars");
        when(obstetricExamination.getFetalHeartRate_BeatsPerMinute()).thenReturn("Fetal Heart Rate Beats Per Minute");
        when(obstetricExamination.getFetalHeartSounds()).thenReturn("Fetal Heart Sounds");
        when(obstetricExamination.getFetalMovements()).thenReturn("Fetal Movements");
        when(obstetricExamination.getFetalPositionOrLie()).thenReturn("Fetal Position Or Lie");
        when(obstetricExamination.getFetalPresentation()).thenReturn("Fetal Presentation");
        when(obstetricExamination.getFundalHeight()).thenReturn("Fundal Height");
        when(obstetricExamination.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        when(obstetricExamination.getReasonsForHRPDB()).thenReturn("Just cause");
        when(obstetricExamination.getfHAndPOA_Interpretation()).thenReturn("Getf HAnd POA Interpretation");
        when(obstetricExamination.getfHAndPOA_Status()).thenReturn("Getf HAnd POA Status");
        doNothing().when(obstetricExamination).setAbdominalScars(Mockito.<String>any());
        doNothing().when(obstetricExamination).setBenVisitID(Mockito.<Long>any());
        doNothing().when(obstetricExamination).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(obstetricExamination).setCreatedBy(Mockito.<String>any());
        doNothing().when(obstetricExamination).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(obstetricExamination).setDeleted(Mockito.<Boolean>any());
        doNothing().when(obstetricExamination).setFetalHeartRate_BeatsPerMinute(Mockito.<String>any());
        doNothing().when(obstetricExamination).setFetalHeartSounds(Mockito.<String>any());
        doNothing().when(obstetricExamination).setFetalMovements(Mockito.<String>any());
        doNothing().when(obstetricExamination).setFetalPositionOrLie(Mockito.<String>any());
        doNothing().when(obstetricExamination).setFetalPresentation(Mockito.<String>any());
        doNothing().when(obstetricExamination).setFundalHeight(Mockito.<String>any());
        doNothing().when(obstetricExamination).setID(Mockito.<Long>any());
        doNothing().when(obstetricExamination).setIsHRP(Mockito.<Boolean>any());
        doNothing().when(obstetricExamination).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(obstetricExamination).setLowLyingPlacenta(Mockito.<Boolean>any());
        doNothing().when(obstetricExamination).setMalPresentation(Mockito.<Boolean>any());
        doNothing().when(obstetricExamination).setModifiedBy(Mockito.<String>any());
        doNothing().when(obstetricExamination).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(obstetricExamination).setProcessed(Mockito.<String>any());
        doNothing().when(obstetricExamination).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(obstetricExamination).setReasonsForHRP(Mockito.<String[]>any());
        doNothing().when(obstetricExamination).setReasonsForHRPDB(Mockito.<String>any());
        doNothing().when(obstetricExamination).setReservedForChange(Mockito.<String>any());
        doNothing().when(obstetricExamination).setSfh(Mockito.<Double>any());
        doNothing().when(obstetricExamination).setSyncedBy(Mockito.<String>any());
        doNothing().when(obstetricExamination).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(obstetricExamination).setVanID(Mockito.<Integer>any());
        doNothing().when(obstetricExamination).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(obstetricExamination).setVehicalNo(Mockito.<String>any());
        doNothing().when(obstetricExamination).setVertebralDeformity(Mockito.<Boolean>any());
        doNothing().when(obstetricExamination).setVisitCode(Mockito.<Long>any());
        doNothing().when(obstetricExamination).setfHAndPOA_Interpretation(Mockito.<String>any());
        doNothing().when(obstetricExamination).setfHAndPOA_Status(Mockito.<String>any());
        obstetricExamination.setAbdominalScars("Abdominal Scars");
        obstetricExamination.setBenVisitID(1L);
        obstetricExamination.setBeneficiaryRegID(1L);
        obstetricExamination.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        obstetricExamination.setCreatedDate(mock(Timestamp.class));
        obstetricExamination.setDeleted(true);
        obstetricExamination.setFetalHeartRate_BeatsPerMinute("Fetal Heart Rate Beats Per Minute");
        obstetricExamination.setFetalHeartSounds("Fetal Heart Sounds");
        obstetricExamination.setFetalMovements("Fetal Movements");
        obstetricExamination.setFetalPositionOrLie("Fetal Position Or Lie");
        obstetricExamination.setFetalPresentation("Fetal Presentation");
        obstetricExamination.setFundalHeight("Fundal Height");
        obstetricExamination.setID(1L);
        obstetricExamination.setIsHRP(true);
        obstetricExamination.setLastModDate(mock(Timestamp.class));
        obstetricExamination.setLowLyingPlacenta(true);
        obstetricExamination.setMalPresentation(true);
        obstetricExamination.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        obstetricExamination.setParkingPlaceID(1);
        obstetricExamination.setProcessed("Processed");
        obstetricExamination.setProviderServiceMapID(1);
        obstetricExamination.setReasonsForHRP(new String[]{"Just cause"});
        obstetricExamination.setReasonsForHRPDB("Just cause");
        obstetricExamination.setReservedForChange("Reserved For Change");
        obstetricExamination.setSfh(10.0d);
        obstetricExamination.setSyncedBy("Synced By");
        obstetricExamination.setSyncedDate(mock(Timestamp.class));
        obstetricExamination.setVanID(1);
        obstetricExamination.setVanSerialNo(1L);
        obstetricExamination.setVehicalNo("Vehical No");
        obstetricExamination.setVertebralDeformity(true);
        obstetricExamination.setVisitCode(1L);
        obstetricExamination.setfHAndPOA_Interpretation("F HAnd POA Interpretation");
        obstetricExamination.setfHAndPOA_Status("F HAnd POA Status");

        // Act
        int actualUpdateSysObstetricExaminationResult = aNCNurseServiceImpl
                .updateSysObstetricExamination(obstetricExamination);

        // Assert
        verify(obstetricExamination).getAbdominalScars();
        verify(obstetricExamination, atLeast(1)).getBeneficiaryRegID();
        verify(obstetricExamination).getFetalHeartRate_BeatsPerMinute();
        verify(obstetricExamination).getFetalHeartSounds();
        verify(obstetricExamination).getFetalMovements();
        verify(obstetricExamination).getFetalPositionOrLie();
        verify(obstetricExamination).getFetalPresentation();
        verify(obstetricExamination).getFundalHeight();
        verify(obstetricExamination).getIsHRP();
        verify(obstetricExamination).getLowLyingPlacenta();
        verify(obstetricExamination).getMalPresentation();
        verify(obstetricExamination).getModifiedBy();
        verify(obstetricExamination).getReasonsForHRPDB();
        verify(obstetricExamination).getSfh();
        verify(obstetricExamination).getVertebralDeformity();
        verify(obstetricExamination, atLeast(1)).getVisitCode();
        verify(obstetricExamination).getfHAndPOA_Interpretation();
        verify(obstetricExamination).getfHAndPOA_Status();
        verify(obstetricExamination).setAbdominalScars(eq("Abdominal Scars"));
        verify(obstetricExamination).setBenVisitID(isA(Long.class));
        verify(obstetricExamination).setBeneficiaryRegID(isA(Long.class));
        verify(obstetricExamination).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(obstetricExamination).setCreatedDate(isA(Timestamp.class));
        verify(obstetricExamination).setDeleted(isA(Boolean.class));
        verify(obstetricExamination).setFetalHeartRate_BeatsPerMinute(eq("Fetal Heart Rate Beats Per Minute"));
        verify(obstetricExamination).setFetalHeartSounds(eq("Fetal Heart Sounds"));
        verify(obstetricExamination).setFetalMovements(eq("Fetal Movements"));
        verify(obstetricExamination).setFetalPositionOrLie(eq("Fetal Position Or Lie"));
        verify(obstetricExamination).setFetalPresentation(eq("Fetal Presentation"));
        verify(obstetricExamination).setFundalHeight(eq("Fundal Height"));
        verify(obstetricExamination).setID(isA(Long.class));
        verify(obstetricExamination).setIsHRP(isA(Boolean.class));
        verify(obstetricExamination).setLastModDate(isA(Timestamp.class));
        verify(obstetricExamination).setLowLyingPlacenta(isA(Boolean.class));
        verify(obstetricExamination).setMalPresentation(isA(Boolean.class));
        verify(obstetricExamination).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(obstetricExamination).setParkingPlaceID(isA(Integer.class));
        verify(obstetricExamination).setProcessed(eq("Processed"));
        verify(obstetricExamination).setProviderServiceMapID(isA(Integer.class));
        verify(obstetricExamination).setReasonsForHRP(isA(String[].class));
        verify(obstetricExamination).setReasonsForHRPDB(eq("Just cause"));
        verify(obstetricExamination).setReservedForChange(eq("Reserved For Change"));
        verify(obstetricExamination).setSfh(isA(Double.class));
        verify(obstetricExamination).setSyncedBy(eq("Synced By"));
        verify(obstetricExamination).setSyncedDate(isA(Timestamp.class));
        verify(obstetricExamination).setVanID(isA(Integer.class));
        verify(obstetricExamination).setVanSerialNo(isA(Long.class));
        verify(obstetricExamination).setVehicalNo(eq("Vehical No"));
        verify(obstetricExamination).setVertebralDeformity(isA(Boolean.class));
        verify(obstetricExamination).setVisitCode(isA(Long.class));
        verify(obstetricExamination).setfHAndPOA_Interpretation(eq("F HAnd POA Interpretation"));
        verify(obstetricExamination).setfHAndPOA_Status(eq("F HAnd POA Status"));
        verify(sysObstetricExaminationRepo).getBenObstetricExaminationStatus(isA(Long.class), isA(Long.class));
        verify(sysObstetricExaminationRepo).updateSysObstetricExamination(eq("Fundal Height"), eq("Getf HAnd POA Status"),
                eq("Getf HAnd POA Interpretation"), eq("Fetal Movements"), eq("Fetal Heart Sounds"),
                eq("Fetal Heart Rate Beats Per Minute"), eq("Fetal Position Or Lie"), eq("Fetal Presentation"),
                eq("Abdominal Scars"), isA(Double.class), eq("Jan 1, 2020 9:00am GMT+0100"), eq("U"), isA(Long.class),
                isA(Long.class), isA(Boolean.class), isA(Boolean.class), isA(Boolean.class), isA(Boolean.class),
                eq("Just cause"));
        assertEquals(1, actualUpdateSysObstetricExaminationResult);
    }

    @Test
    void testGettersAndSetters() {
        ANCNurseServiceImpl ancNurseServiceImpl = new ANCNurseServiceImpl();

        // Act
        ancNurseServiceImpl.setAncCareRepo(mock(ANCCareRepo.class));
        ancNurseServiceImpl.setAncWomenVaccineRepo(mock(ANCWomenVaccineRepo.class));
        ancNurseServiceImpl.setBenAdherenceRepo(mock(BenAdherenceRepo.class));
        ancNurseServiceImpl.setLabTestOrderDetailRepo(mock(LabTestOrderDetailRepo.class));
        ancNurseServiceImpl.setSysObstetricExaminationRepo(mock(SysObstetricExaminationRepo.class));
    }
}
