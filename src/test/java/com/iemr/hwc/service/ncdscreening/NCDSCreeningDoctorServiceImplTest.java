package com.iemr.hwc.service.ncdscreening;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.data.snomedct.SCTDescription;
import com.iemr.hwc.data.tele_consultation.TeleconsultationRequestOBJ;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.tele_consultation.SMSGatewayServiceImpl;
import com.iemr.hwc.service.tele_consultation.TeleConsultationServiceImpl;

@ExtendWith(MockitoExtension.class)
class NCDSCreeningDoctorServiceImplTest {
    @Mock
    private CommonDoctorServiceImpl commonDoctorServiceImpl;

    @Mock
    private CommonNcdScreeningService commonNcdScreeningService;

    @Mock
    private CommonNurseServiceImpl commonNurseServiceImpl;

    @Mock
    private CommonServiceImpl commonServiceImpl;

    @InjectMocks
    private NCDSCreeningDoctorServiceImpl nCDSCreeningDoctorServiceImpl;

    @Mock
    private PrescriptionDetailRepo prescriptionDetailRepo;

    @Mock
    private SMSGatewayServiceImpl sMSGatewayServiceImpl;

    @Mock
    private TeleConsultationServiceImpl teleConsultationServiceImpl;

    @Test
    void testSetPrescriptionDetailRepo() {
        (new NCDSCreeningDoctorServiceImpl()).setPrescriptionDetailRepo(mock(PrescriptionDetailRepo.class));
    }

    @Test
    void testUpdateDoctorData() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);

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
        int actualUpdateDoctorDataResult = nCDSCreeningDoctorServiceImpl.updateDoctorData(new JsonObject(), "JaneDoe");

        // Assert
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        assertEquals(1, actualUpdateDoctorDataResult);
    }

    @Test
    void testUpdateDoctorData2() throws Exception {
        // Arrange
        when(commonServiceImpl.createTcRequest(Mockito.<JsonObject>any(), Mockito.<CommonUtilityClass>any(),
                Mockito.<String>any())).thenThrow(new Exception("investigation"));

        // Act and Assert
        assertThrows(Exception.class, () -> nCDSCreeningDoctorServiceImpl.updateDoctorData(new JsonObject(), "JaneDoe"));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
    }

    @Test
    void testUpdateDoctorData3() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(0);

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
                () -> nCDSCreeningDoctorServiceImpl.updateDoctorData(new JsonObject(), "JaneDoe"));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
    }

    @Test
    void testUpdateDoctorData4() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        when(teleconsultationRequestOBJ.getWalkIn()).thenThrow(new RuntimeException("investigation"));
        when(teleconsultationRequestOBJ.getTmRequestID()).thenReturn(1L);
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

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> nCDSCreeningDoctorServiceImpl.updateDoctorData(new JsonObject(), "JaneDoe"));
        verify(teleconsultationRequestOBJ, atLeast(1)).getTmRequestID();
        verify(teleconsultationRequestOBJ).getWalkIn();
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
    }

    @Test
    void testUpdateDoctorData5() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(false);
        when(teleconsultationRequestOBJ.getSpecializationID()).thenReturn(1);
        when(teleconsultationRequestOBJ.getAllocationDate()).thenReturn(mock(Timestamp.class));
        when(teleconsultationRequestOBJ.getTmRequestID()).thenReturn(1L);
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
        int actualUpdateDoctorDataResult = nCDSCreeningDoctorServiceImpl.updateDoctorData(new JsonObject(), "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ, atLeast(1)).getAllocationDate();
        verify(teleconsultationRequestOBJ).getSpecializationID();
        verify(teleconsultationRequestOBJ, atLeast(1)).getTmRequestID();
        verify(teleconsultationRequestOBJ).getWalkIn();
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        verify(sMSGatewayServiceImpl).smsSenderGateway(eq("schedule"), isNull(), isA(Integer.class), isA(Long.class),
                isNull(), isNull(), eq("Mock for Timestamp, hashCode: 1822583634"), isNull(), eq("JaneDoe"));
        assertEquals(1, actualUpdateDoctorDataResult);
    }

    @Test
    void testUpdateDoctorData6() throws Exception {
        // Arrange
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        when(teleconsultationRequestOBJ.getTmRequestID()).thenReturn(-1L);
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

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> nCDSCreeningDoctorServiceImpl.updateDoctorData(new JsonObject(), "JaneDoe"));
        verify(teleconsultationRequestOBJ, atLeast(1)).getTmRequestID();
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
    }

    @Test
    void testUpdateDoctorData7() throws Exception {
        // Arrange
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
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

        // Act
        int actualUpdateDoctorDataResult = nCDSCreeningDoctorServiceImpl.updateDoctorData(null, "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        assertEquals(0, actualUpdateDoctorDataResult);
    }

    @Test
    void testUpdateDoctorData8() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(true);
        when(teleconsultationRequestOBJ.getTmRequestID()).thenReturn(1L);
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

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("Property", new JsonArray(3));

        // Act
        int actualUpdateDoctorDataResult = nCDSCreeningDoctorServiceImpl.updateDoctorData(requestOBJ, "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ, atLeast(1)).getTmRequestID();
        verify(teleconsultationRequestOBJ).getWalkIn();
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        assertEquals(1, actualUpdateDoctorDataResult);
    }

    @Test
    void testUpdateDoctorData9() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(true);
        when(teleconsultationRequestOBJ.getTmRequestID()).thenReturn(1L);
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

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.addProperty("Property", "42");

        // Act
        int actualUpdateDoctorDataResult = nCDSCreeningDoctorServiceImpl.updateDoctorData(requestOBJ, "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ, atLeast(1)).getTmRequestID();
        verify(teleconsultationRequestOBJ).getWalkIn();
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        assertEquals(1, actualUpdateDoctorDataResult);
    }

    @Test
    void testUpdateDoctorData10() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(true);
        when(teleconsultationRequestOBJ.getTmRequestID()).thenReturn(1L);
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

        JsonObject requestOBJ = new JsonObject();
        Integer value = Integer.valueOf(1);
        requestOBJ.addProperty("Property", value);

        // Act
        int actualUpdateDoctorDataResult = nCDSCreeningDoctorServiceImpl.updateDoctorData(requestOBJ, "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ, atLeast(1)).getTmRequestID();
        verify(teleconsultationRequestOBJ).getWalkIn();
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        assertEquals(1, actualUpdateDoctorDataResult);
        assertSame(value, actualUpdateDoctorDataResult);
    }

    @Test
    void testUpdateDoctorData11() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(true);
        when(teleconsultationRequestOBJ.getTmRequestID()).thenReturn(1L);
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

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.addProperty("Property", true);

        // Act
        int actualUpdateDoctorDataResult = nCDSCreeningDoctorServiceImpl.updateDoctorData(requestOBJ, "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ, atLeast(1)).getTmRequestID();
        verify(teleconsultationRequestOBJ).getWalkIn();
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        assertEquals(1, actualUpdateDoctorDataResult);
    }

    @Test
    void testUpdateDoctorData12() throws Exception {
        // Arrange
        when(commonDoctorServiceImpl.updateBenFlowtableAfterDocDataUpdate(Mockito.<CommonUtilityClass>any(),
                Mockito.<Boolean>any(), Mockito.<Boolean>any(), Mockito.<TeleconsultationRequestOBJ>any())).thenReturn(1);
        TeleconsultationRequestOBJ teleconsultationRequestOBJ = mock(TeleconsultationRequestOBJ.class);
        when(teleconsultationRequestOBJ.getWalkIn()).thenReturn(true);
        when(teleconsultationRequestOBJ.getTmRequestID()).thenReturn(1L);
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

        JsonObject requestOBJ = new JsonObject();
        requestOBJ.add("prescription", new JsonArray(3));
        requestOBJ.add("Property", new JsonArray(3));

        // Act
        int actualUpdateDoctorDataResult = nCDSCreeningDoctorServiceImpl.updateDoctorData(requestOBJ, "JaneDoe");

        // Assert
        verify(teleconsultationRequestOBJ, atLeast(1)).getTmRequestID();
        verify(teleconsultationRequestOBJ).getWalkIn();
        verify(teleconsultationRequestOBJ).setAllocationDate(isA(Timestamp.class));
        verify(teleconsultationRequestOBJ).setFromTime(eq("jane.doe@example.org"));
        verify(teleconsultationRequestOBJ).setSpecializationID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setTmRequestID(isA(Long.class));
        verify(teleconsultationRequestOBJ).setToTime(eq("To Time"));
        verify(teleconsultationRequestOBJ).setUserID(isA(Integer.class));
        verify(teleconsultationRequestOBJ).setWalkIn(isA(Boolean.class));
        verify(commonDoctorServiceImpl).updateBenFlowtableAfterDocDataUpdate(isA(CommonUtilityClass.class),
                isA(Boolean.class), isA(Boolean.class), isA(TeleconsultationRequestOBJ.class));
        verify(commonServiceImpl).createTcRequest(isA(JsonObject.class), isA(CommonUtilityClass.class), eq("JaneDoe"));
        assertEquals(1, actualUpdateDoctorDataResult);
    }

    @Test
    void testGetNCDDiagnosisData() {
        // Arrange
        when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        String actualNCDDiagnosisData = nCDSCreeningDoctorServiceImpl.getNCDDiagnosisData(1L, 1L);

        // Assert
        verify(prescriptionDetailRepo).findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(isA(Long.class),
                isA(Long.class));
        assertEquals("{}", actualNCDDiagnosisData);
    }

    @Test
    void testGetNCDDiagnosisData2() {
        // Arrange
        PrescriptionDetail prescriptionDetail = mock(PrescriptionDetail.class);
        when(prescriptionDetail.getDiagnosisProvided()).thenThrow(new RuntimeException("foo"));
        when(prescriptionDetail.getDiagnosisProvided_SCTCode()).thenReturn("Diagnosis Provided SCTCode");
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

        ArrayList<PrescriptionDetail> prescriptionDetailList = new ArrayList<>();
        prescriptionDetailList.add(prescriptionDetail);
        when(prescriptionDetailRepo.findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(prescriptionDetailList);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> nCDSCreeningDoctorServiceImpl.getNCDDiagnosisData(1L, 1L));
        verify(prescriptionDetail).getDiagnosisProvided();
        verify(prescriptionDetail).getDiagnosisProvided_SCTCode();
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
        verify(prescriptionDetailRepo).findByBeneficiaryRegIDAndVisitCodeOrderByPrescriptionIDDesc(isA(Long.class),
                isA(Long.class));
    }
}
