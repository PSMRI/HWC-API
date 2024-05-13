package com.iemr.hwc.service.benFlowStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;

@ExtendWith(MockitoExtension.class)
class CommonBenStatusFlowServiceImplTest {
    @Mock
    private BenVisitDetailRepo benVisitDetailRepo;

    @Mock
    private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

    @InjectMocks
    private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

    @Test
    void testSetBeneficiaryFlowStatusRepo() {
        (new CommonBenStatusFlowServiceImpl()).setBeneficiaryFlowStatusRepo(mock(BeneficiaryFlowStatusRepo.class));
    }

    @Test
    void testCreateBenFlowRecord() {
        assertEquals(0, (new CommonBenStatusFlowServiceImpl()).createBenFlowRecord("Request OBJ", 1L, 1L));
        assertEquals(0, (new CommonBenStatusFlowServiceImpl()).createBenFlowRecord("yyyy-MM-dd'T'HH:mm:ss.SSS", 1L, 1L));
        assertEquals(0, (new CommonBenStatusFlowServiceImpl()).createBenFlowRecord("", 1L, 1L));
    }

    @Test
    void testCreateBenFlowRecord2() {
        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(mock(BeneficiaryFlowStatusRepo.class));

        // Act and Assert
        assertEquals(0, commonBenStatusFlowServiceImpl.createBenFlowRecord("Request OBJ", 1L, 1L));
    }

    @Test
    void testCreateBenFlowRecord3() {
        commonBenStatusFlowServiceImpl.createBenFlowRecord("Request OBJ", 1L, 1L);
    }

    @Test
    void testUpdateBenFlowNurseAfterNurseActivity() {
        assertEquals(0,
                (new CommonBenStatusFlowServiceImpl()).updateBenFlowNurseAfterNurseActivity(1L, 1L, 1L, "Just cause",
                        "Visit Category", (short) 1, (short) 1, (short) 1, (short) 1, (short) 1, 1L, 1, (short) 1,
                        mock(Timestamp.class), 1));
    }

    @Test
    void testUpdateBenFlowNurseAfterNurseActivity2() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterNurseActivity(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Long>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Timestamp>any(), Mockito.<Integer>any())).thenReturn(1);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowNurseAfterNurseActivityResult = commonBenStatusFlowServiceImpl
                .updateBenFlowNurseAfterNurseActivity(1L, 1L, 1L, "Just cause", "Visit Category", (short) 1, (short) 1,
                        (short) 1, (short) 1, (short) 1, 1L, 1, (short) 1, mock(Timestamp.class), 1);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterNurseActivity(isA(Long.class), isA(Long.class),
                isA(Long.class), eq("Just cause"), eq("Visit Category"), isA(Short.class), isA(Short.class), isA(Short.class),
                isA(Short.class), isA(Short.class), isA(Long.class), isA(Integer.class), isA(Short.class), isA(Timestamp.class),
                isA(Integer.class));
        assertEquals(1, actualUpdateBenFlowNurseAfterNurseActivityResult);
    }

    @Test
    void testUpdateBenFlowNurseAfterNurseActivity3() {
        commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(1L, 1L, 1L, "Just cause", "Visit Category",
                (short) 1, (short) 1, (short) 1, (short) 1, (short) 1, 1L, 1, (short) 1, mock(Timestamp.class), 1);
    }

    @Test
    void testUpdateBenFlowNurseAfterNurseActivityANC() {
        assertEquals(0,
                (new CommonBenStatusFlowServiceImpl()).updateBenFlowNurseAfterNurseActivityANC(1L, 1L, 1L, "Just cause",
                        "Visit Category", (short) 1, (short) 1, (short) 1, (short) 1, (short) 1, 1L, 1, (short) 1,
                        mock(Timestamp.class), 1, (short) 1));
    }

    @Test
    void testUpdateBenFlowNurseAfterNurseActivityANC2() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterNurseActivityANC(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Long>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Timestamp>any(), Mockito.<Integer>any(), Mockito.<Short>any())).thenReturn(1);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowNurseAfterNurseActivityANCResult = commonBenStatusFlowServiceImpl
                .updateBenFlowNurseAfterNurseActivityANC(1L, 1L, 1L, "Just cause", "Visit Category", (short) 1, (short) 1,
                        (short) 1, (short) 1, (short) 1, 1L, 1, (short) 1, mock(Timestamp.class), 1, (short) 1);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterNurseActivityANC(isA(Long.class), isA(Long.class),
                isA(Long.class), eq("Just cause"), eq("Visit Category"), isA(Short.class), isA(Short.class), isA(Short.class),
                isA(Short.class), isA(Short.class), isA(Long.class), isA(Integer.class), isA(Short.class), isA(Timestamp.class),
                isA(Integer.class), isA(Short.class));
        assertEquals(1, actualUpdateBenFlowNurseAfterNurseActivityANCResult);
    }

    @Test
    void testUpdateBenFlowNurseAfterNurseActivityANC3() {
        commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivityANC(1L, 1L, 1L, "Just cause", "Visit Category",
                (short) 1, (short) 1, (short) 1, (short) 1, (short) 1, 1L, 1, (short) 1, mock(Timestamp.class), 1, (short) 1);
    }

    @Test
    void testUpdateBenFlowNurseAfterNurseUpdateNCD_Screening() {
        assertEquals(0,
                (new CommonBenStatusFlowServiceImpl()).updateBenFlowNurseAfterNurseUpdateNCD_Screening(1L, 1L, (short) 7));
        assertEquals(0,
                (new CommonBenStatusFlowServiceImpl()).updateBenFlowNurseAfterNurseUpdateNCD_Screening(5L, 5L, (short) 7));
    }

    @Test
    void testUpdateBenFlowNurseAfterNurseUpdateNCD_Screening2() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterNurseDataUpdateNCD_Screening(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Short>any())).thenReturn(3);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowNurseAfterNurseUpdateNCD_ScreeningResult = commonBenStatusFlowServiceImpl
                .updateBenFlowNurseAfterNurseUpdateNCD_Screening(1L, 1L, (short) 7);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterNurseDataUpdateNCD_Screening(isA(Long.class),
                isA(Long.class), isA(Short.class));
        assertEquals(3, actualUpdateBenFlowNurseAfterNurseUpdateNCD_ScreeningResult);
    }

    @Test
    void testUpdateBenFlowNurseAfterNurseUpdateNCD_Screening3() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterNurseDataUpdateNCD_Screening(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Short>any())).thenReturn(3);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowNurseAfterNurseUpdateNCD_ScreeningResult = commonBenStatusFlowServiceImpl
                .updateBenFlowNurseAfterNurseUpdateNCD_Screening(2L, 1L, (short) 7);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterNurseDataUpdateNCD_Screening(isA(Long.class),
                isA(Long.class), isA(Short.class));
        assertEquals(3, actualUpdateBenFlowNurseAfterNurseUpdateNCD_ScreeningResult);
    }

    @Test
    void testUpdateBenFlowNurseAfterNurseUpdateNCD_Screening4() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterNurseDataUpdateNCD_Screening(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Short>any())).thenReturn(3);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowNurseAfterNurseUpdateNCD_ScreeningResult = commonBenStatusFlowServiceImpl
                .updateBenFlowNurseAfterNurseUpdateNCD_Screening(3L, 1L, (short) 7);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterNurseDataUpdateNCD_Screening(isA(Long.class),
                isA(Long.class), isA(Short.class));
        assertEquals(3, actualUpdateBenFlowNurseAfterNurseUpdateNCD_ScreeningResult);
    }

    @Test
    void testUpdateBenFlowNurseAfterNurseUpdateNCD_Screening5() {
        commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseUpdateNCD_Screening(1L, 1L, (short) 7);
    }

    @Test
    void testUpdateBenFlowAfterDocData() {
        assertEquals(0, (new CommonBenStatusFlowServiceImpl()).updateBenFlowAfterDocData(1L, 1L, 1L, 1L, (short) 1,
                (short) 1, (short) 1, (short) 1, 1, mock(Timestamp.class), (short) 1));
    }

    @Test
    void testUpdateBenFlowAfterDocData2() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivity(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                anyInt(), Mockito.<Timestamp>any(), Mockito.<Short>any())).thenReturn(1);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowAfterDocDataResult = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocData(1L, 1L, 1L, 1L,
                (short) 1, (short) 1, (short) 1, (short) 1, 1, mock(Timestamp.class), (short) 1);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterDoctorActivity(isA(Long.class), isA(Long.class),
                isA(Long.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class), eq(1),
                isA(Timestamp.class), isA(Short.class));
        assertEquals(1, actualUpdateBenFlowAfterDocDataResult);
    }

    @Test
    void testUpdateBenFlowAfterDocData3() {
        commonBenStatusFlowServiceImpl.updateBenFlowAfterDocData(1L, 1L, 1L, 1L, (short) 1, (short) 1, (short) 1, (short) 1,
                1, mock(Timestamp.class), (short) 1);
    }

    @Test
    void testUpdateBenFlowAfterDocDataFromSpecialist() {
        assertEquals(0, (new CommonBenStatusFlowServiceImpl()).updateBenFlowAfterDocDataFromSpecialist(1L, 1L, 1L, 1L,
                (short) 7, (short) 7, (short) 7, (short) 7, (short) 7));
        assertEquals(0, (new CommonBenStatusFlowServiceImpl()).updateBenFlowAfterDocDataFromSpecialist(5L, 5L, 5L, 5L,
                (short) 7, (short) 7, (short) 7, (short) 7, (short) 7));
    }

    @Test
    void testUpdateBenFlowAfterDocDataFromSpecialist2() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivitySpecialist(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<Short>any(), Mockito.<Short>any())).thenReturn(3);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowAfterDocDataFromSpecialistResult = commonBenStatusFlowServiceImpl
                .updateBenFlowAfterDocDataFromSpecialist(1L, 1L, 1L, 1L, (short) 7, (short) 7, (short) 7, (short) 7, (short) 7);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterDoctorActivitySpecialist(isA(Long.class), isA(Long.class),
                isA(Long.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class));
        assertEquals(3, actualUpdateBenFlowAfterDocDataFromSpecialistResult);
    }

    @Test
    void testUpdateBenFlowAfterDocDataFromSpecialist3() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivitySpecialist(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<Short>any(), Mockito.<Short>any())).thenReturn(3);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowAfterDocDataFromSpecialistResult = commonBenStatusFlowServiceImpl
                .updateBenFlowAfterDocDataFromSpecialist(2L, 1L, 1L, 1L, (short) 7, (short) 7, (short) 7, (short) 7, (short) 7);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterDoctorActivitySpecialist(isA(Long.class), isA(Long.class),
                isA(Long.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class));
        assertEquals(3, actualUpdateBenFlowAfterDocDataFromSpecialistResult);
    }

    @Test
    void testUpdateBenFlowAfterDocDataFromSpecialist4() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivitySpecialist(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<Short>any(), Mockito.<Short>any())).thenReturn(3);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowAfterDocDataFromSpecialistResult = commonBenStatusFlowServiceImpl
                .updateBenFlowAfterDocDataFromSpecialist(3L, 1L, 1L, 1L, (short) 7, (short) 7, (short) 7, (short) 7, (short) 7);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterDoctorActivitySpecialist(isA(Long.class), isA(Long.class),
                isA(Long.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class));
        assertEquals(3, actualUpdateBenFlowAfterDocDataFromSpecialistResult);
    }

    @Test
    void testUpdateBenFlowAfterDocDataFromSpecialist5() {
        commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataFromSpecialist(1L, 1L, 1L, 1L, (short) 7, (short) 7,
                (short) 7, (short) 7, (short) 7);
    }

    @Test
    void testUpdateBenFlowAfterDocDataFromSpecialistANC() {
        assertEquals(0, (new CommonBenStatusFlowServiceImpl()).updateBenFlowAfterDocDataFromSpecialistANC(1L, 1L, 1L, 1L,
                (short) 7, (short) 7, (short) 7, (short) 7, (short) 7));
        assertEquals(0, (new CommonBenStatusFlowServiceImpl()).updateBenFlowAfterDocDataFromSpecialistANC(5L, 5L, 5L, 5L,
                (short) 7, (short) 7, (short) 7, (short) 7, (short) 7));
    }

    @Test
    void testUpdateBenFlowAfterDocDataFromSpecialistANC2() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivitySpecialistANC(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<Short>any(), Mockito.<Short>any())).thenReturn(3);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowAfterDocDataFromSpecialistANCResult = commonBenStatusFlowServiceImpl
                .updateBenFlowAfterDocDataFromSpecialistANC(1L, 1L, 1L, 1L, (short) 7, (short) 7, (short) 7, (short) 7,
                        (short) 7);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterDoctorActivitySpecialistANC(isA(Long.class),
                isA(Long.class), isA(Long.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class),
                isA(Short.class));
        assertEquals(3, actualUpdateBenFlowAfterDocDataFromSpecialistANCResult);
    }

    @Test
    void testUpdateBenFlowAfterDocDataFromSpecialistANC3() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivitySpecialistANC(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<Short>any(), Mockito.<Short>any())).thenReturn(3);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowAfterDocDataFromSpecialistANCResult = commonBenStatusFlowServiceImpl
                .updateBenFlowAfterDocDataFromSpecialistANC(2L, 1L, 1L, 1L, (short) 7, (short) 7, (short) 7, (short) 7,
                        (short) 7);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterDoctorActivitySpecialistANC(isA(Long.class),
                isA(Long.class), isA(Long.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class),
                isA(Short.class));
        assertEquals(3, actualUpdateBenFlowAfterDocDataFromSpecialistANCResult);
    }

    @Test
    void testUpdateBenFlowAfterDocDataFromSpecialistANC4() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivitySpecialistANC(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<Short>any(), Mockito.<Short>any())).thenReturn(3);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowAfterDocDataFromSpecialistANCResult = commonBenStatusFlowServiceImpl
                .updateBenFlowAfterDocDataFromSpecialistANC(3L, 1L, 1L, 1L, (short) 7, (short) 7, (short) 7, (short) 7,
                        (short) 7);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterDoctorActivitySpecialistANC(isA(Long.class),
                isA(Long.class), isA(Long.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class),
                isA(Short.class));
        assertEquals(3, actualUpdateBenFlowAfterDocDataFromSpecialistANCResult);
    }

    @Test
    void testUpdateBenFlowAfterDocDataFromSpecialistANC5() {
        commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataFromSpecialistANC(1L, 1L, 1L, 1L, (short) 7, (short) 7,
                (short) 7, (short) 7, (short) 7);
    }

    @Test
    void testUpdateBenFlowAfterDocDataUpdate() throws Exception {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivity(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                anyInt(), Mockito.<Timestamp>any(), Mockito.<Short>any())).thenReturn(1);
        when(beneficiaryFlowStatusRepo.getPharmaFlag(Mockito.<Long>any())).thenReturn((short) 1);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowAfterDocDataUpdateResult = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataUpdate(1L,
                1L, 1L, 1L, (short) 1, (short) 1, (short) 1, (short) 1, 1, mock(Timestamp.class), (short) 1);

        // Assert
        verify(beneficiaryFlowStatusRepo).getPharmaFlag(isA(Long.class));
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterDoctorActivity(isA(Long.class), isA(Long.class),
                isA(Long.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class), eq(1),
                isA(Timestamp.class), isA(Short.class));
        assertEquals(1, actualUpdateBenFlowAfterDocDataUpdateResult);
    }

    @Test
    void testUpdateBenFlowAfterDocDataUpdate2() throws Exception {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivity(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                anyInt(), Mockito.<Timestamp>any(), Mockito.<Short>any())).thenReturn(1);
        when(beneficiaryFlowStatusRepo.getPharmaFlag(Mockito.<Long>any())).thenReturn((short) 10);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowAfterDocDataUpdateResult = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataUpdate(1L,
                1L, 1L, 1L, (short) 1, (short) 1, (short) 1, (short) 1, 1, mock(Timestamp.class), (short) 1);

        // Assert
        verify(beneficiaryFlowStatusRepo).getPharmaFlag(isA(Long.class));
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterDoctorActivity(isA(Long.class), isA(Long.class),
                isA(Long.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class), eq(1),
                isA(Timestamp.class), isA(Short.class));
        assertEquals(1, actualUpdateBenFlowAfterDocDataUpdateResult);
    }

    @Test
    void testUpdateBenFlowAfterDocDataUpdate3() throws Exception {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivity(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                anyInt(), Mockito.<Timestamp>any(), Mockito.<Short>any())).thenReturn(1);
        when(beneficiaryFlowStatusRepo.getPharmaFlag(Mockito.<Long>any())).thenReturn(null);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowAfterDocDataUpdateResult = commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataUpdate(1L,
                1L, 1L, 1L, (short) 1, (short) 1, (short) 1, (short) 1, 1, mock(Timestamp.class), (short) 1);

        // Assert
        verify(beneficiaryFlowStatusRepo).getPharmaFlag(isA(Long.class));
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterDoctorActivity(isA(Long.class), isA(Long.class),
                isA(Long.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class), eq(1),
                isA(Timestamp.class), isA(Short.class));
        assertEquals(1, actualUpdateBenFlowAfterDocDataUpdateResult);
    }

    @Test
    void testUpdateBenFlowAfterDocDataUpdate4() throws Exception {
        commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataUpdate(1L, 1L, 1L, 1L, (short) 1, (short) 1, (short) 1,
                (short) 1, 1, mock(Timestamp.class), (short) 1);
    }

    @Test
    void testUpdateBenFlowAfterDocDataUpdateTCSpecialist() throws Exception {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivityTCSpecialist(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<Short>any())).thenReturn(1);
        when(beneficiaryFlowStatusRepo.getPharmaFlag(Mockito.<Long>any())).thenReturn((short) 1);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowAfterDocDataUpdateTCSpecialistResult = commonBenStatusFlowServiceImpl
                .updateBenFlowAfterDocDataUpdateTCSpecialist(1L, 1L, 1L, 1L, (short) 1, (short) 1, (short) 1, (short) 1, 1,
                        mock(Timestamp.class), (short) 1);

        // Assert
        verify(beneficiaryFlowStatusRepo).getPharmaFlag(isA(Long.class));
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterDoctorActivityTCSpecialist(isA(Long.class),
                isA(Long.class), isA(Long.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class));
        assertEquals(1, actualUpdateBenFlowAfterDocDataUpdateTCSpecialistResult);
    }

    @Test
    void testUpdateBenFlowAfterDocDataUpdateTCSpecialist2() throws Exception {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivityTCSpecialist(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<Short>any())).thenReturn(1);
        when(beneficiaryFlowStatusRepo.getPharmaFlag(Mockito.<Long>any())).thenReturn((short) 7);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowAfterDocDataUpdateTCSpecialistResult = commonBenStatusFlowServiceImpl
                .updateBenFlowAfterDocDataUpdateTCSpecialist(1L, 1L, 1L, 1L, (short) 1, (short) 1, (short) 1, (short) 1, 1,
                        mock(Timestamp.class), (short) 1);

        // Assert
        verify(beneficiaryFlowStatusRepo).getPharmaFlag(isA(Long.class));
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterDoctorActivityTCSpecialist(isA(Long.class),
                isA(Long.class), isA(Long.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class));
        assertEquals(1, actualUpdateBenFlowAfterDocDataUpdateTCSpecialistResult);
    }

    @Test
    void testUpdateBenFlowAfterDocDataUpdateTCSpecialist3() throws Exception {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterDoctorActivityTCSpecialist(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<Short>any())).thenReturn(1);
        when(beneficiaryFlowStatusRepo.getPharmaFlag(Mockito.<Long>any())).thenReturn(null);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateBenFlowAfterDocDataUpdateTCSpecialistResult = commonBenStatusFlowServiceImpl
                .updateBenFlowAfterDocDataUpdateTCSpecialist(1L, 1L, 1L, 1L, (short) 1, (short) 1, (short) 1, (short) 1, 1,
                        mock(Timestamp.class), (short) 1);

        // Assert
        verify(beneficiaryFlowStatusRepo).getPharmaFlag(isA(Long.class));
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterDoctorActivityTCSpecialist(isA(Long.class),
                isA(Long.class), isA(Long.class), isA(Short.class), isA(Short.class), isA(Short.class), isA(Short.class));
        assertEquals(1, actualUpdateBenFlowAfterDocDataUpdateTCSpecialistResult);
    }

    @Test
    void testUpdateBenFlowAfterDocDataUpdateTCSpecialist4() throws Exception {
        commonBenStatusFlowServiceImpl.updateBenFlowAfterDocDataUpdateTCSpecialist(1L, 1L, 1L, 1L, (short) 1, (short) 1,
                (short) 1, (short) 1, 1, mock(Timestamp.class), (short) 1);
    }

    @Test
    void testUpdateFlowAfterLabResultEntry() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterLabResultEntry(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Short>any(), Mockito.<Short>any())).thenReturn(3);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateFlowAfterLabResultEntryResult = commonBenStatusFlowServiceImpl.updateFlowAfterLabResultEntry(1L, 1L,
                1L, (short) 7, (short) 7, (short) 7);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterLabResultEntry(isA(Long.class), isA(Long.class),
                isA(Short.class), isA(Short.class));
        assertEquals(3, actualUpdateFlowAfterLabResultEntryResult);
    }

    @Test
    void testUpdateFlowAfterLabResultEntry2() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterLabResultEntry(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Short>any(), Mockito.<Short>any())).thenReturn(3);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateFlowAfterLabResultEntryResult = commonBenStatusFlowServiceImpl.updateFlowAfterLabResultEntry(2L, 1L,
                1L, (short) 7, (short) 7, (short) 7);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterLabResultEntry(isA(Long.class), isA(Long.class),
                isA(Short.class), isA(Short.class));
        assertEquals(3, actualUpdateFlowAfterLabResultEntryResult);
    }

    @Test
    void testUpdateFlowAfterLabResultEntry3() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterLabResultEntry(Mockito.<Long>any(), Mockito.<Long>any(),
                Mockito.<Short>any(), Mockito.<Short>any())).thenReturn(3);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateFlowAfterLabResultEntryResult = commonBenStatusFlowServiceImpl.updateFlowAfterLabResultEntry(3L, 1L,
                1L, (short) 7, (short) 7, (short) 7);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterLabResultEntry(isA(Long.class), isA(Long.class),
                isA(Short.class), isA(Short.class));
        assertEquals(3, actualUpdateFlowAfterLabResultEntryResult);
    }

    @Test
    void testUpdateFlowAfterLabResultEntry4() {
        commonBenStatusFlowServiceImpl.updateFlowAfterLabResultEntry(1L, 1L, 1L, (short) 7, (short) 7, (short) 7);
    }

    @Test
    void testUpdateFlowAfterLabResultEntryForTCSpecialist() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterLabResultEntryForSpecialist(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Short>any())).thenReturn(3);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateFlowAfterLabResultEntryForTCSpecialistResult = commonBenStatusFlowServiceImpl
                .updateFlowAfterLabResultEntryForTCSpecialist(1L, 1L, (short) 7);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterLabResultEntryForSpecialist(isA(Long.class),
                isA(Long.class), isA(Short.class));
        assertEquals(3, actualUpdateFlowAfterLabResultEntryForTCSpecialistResult);
    }

    @Test
    void testUpdateFlowAfterLabResultEntryForTCSpecialist2() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterLabResultEntryForSpecialist(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Short>any())).thenReturn(3);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateFlowAfterLabResultEntryForTCSpecialistResult = commonBenStatusFlowServiceImpl
                .updateFlowAfterLabResultEntryForTCSpecialist(2L, 1L, (short) 7);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterLabResultEntryForSpecialist(isA(Long.class),
                isA(Long.class), isA(Short.class));
        assertEquals(3, actualUpdateFlowAfterLabResultEntryForTCSpecialistResult);
    }

    @Test
    void testUpdateFlowAfterLabResultEntryForTCSpecialist3() {
        BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo = mock(BeneficiaryFlowStatusRepo.class);
        when(beneficiaryFlowStatusRepo.updateBenFlowStatusAfterLabResultEntryForSpecialist(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<Short>any())).thenReturn(3);

        CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl = new CommonBenStatusFlowServiceImpl();
        commonBenStatusFlowServiceImpl.setBeneficiaryFlowStatusRepo(beneficiaryFlowStatusRepo);

        // Act
        int actualUpdateFlowAfterLabResultEntryForTCSpecialistResult = commonBenStatusFlowServiceImpl
                .updateFlowAfterLabResultEntryForTCSpecialist(3L, 1L, (short) 7);

        // Assert
        verify(beneficiaryFlowStatusRepo).updateBenFlowStatusAfterLabResultEntryForSpecialist(isA(Long.class),
                isA(Long.class), isA(Short.class));
        assertEquals(3, actualUpdateFlowAfterLabResultEntryForTCSpecialistResult);
    }

    @Test
    void testUpdateFlowAfterLabResultEntryForTCSpecialist4() {
        commonBenStatusFlowServiceImpl.updateFlowAfterLabResultEntryForTCSpecialist(1L, 1L, (short) 7);
    }
}
