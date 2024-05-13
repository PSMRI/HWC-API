package com.iemr.hwc.service.patientApp.master;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.covid19.CovidContactHistoryMaster;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.hwc.repo.masterrepo.covid19.CovidContactHistoryMasterRepo;
import com.iemr.hwc.repo.masterrepo.covid19.CovidRecommnedationMasterRepo;
import com.iemr.hwc.repo.masterrepo.covid19.CovidSymptomsMasterRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.quickBlox.QuickBloxRepo;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.common.transaction.CommonServiceImpl;
import com.iemr.hwc.service.covid19.Covid19ServiceImpl;
import com.iemr.hwc.service.generalOPD.GeneralOPDDoctorServiceImpl;

@ExtendWith(MockitoExtension.class)
class CommonPatientAppMasterServiceImplTest {
    @Mock
    private BenVisitDetailRepo benVisitDetailRepo;

    @Mock
    private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

    @Mock
    private ChiefComplaintMasterRepo chiefComplaintMasterRepo;

    @Mock
    private CommonNurseServiceImpl commonNurseServiceImpl;

    @InjectMocks
    private CommonPatientAppMasterServiceImpl commonPatientAppMasterServiceImpl;

    @Mock
    private CommonServiceImpl commonServiceImpl;

    @Mock
    private Covid19ServiceImpl covid19ServiceImpl;

    @Mock
    private CovidContactHistoryMasterRepo covidContactHistoryMasterRepo;

    @Mock
    private CovidRecommnedationMasterRepo covidRecommnedationMasterRepo;

    @Mock
    private CovidSymptomsMasterRepo covidSymptomsMasterRepo;

    @Mock
    private GeneralOPDDoctorServiceImpl generalOPDDoctorServiceImpl;

    @Mock
    private QuickBloxRepo quickBloxRepo;

    @Test
    void testGetChiefComplaintsMaster() {
        // Arrange
        when(chiefComplaintMasterRepo.getChiefComplaintMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualChiefComplaintsMaster = commonPatientAppMasterServiceImpl.getChiefComplaintsMaster(1, 1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).getChiefComplaintMaster();
        assertEquals("{\"chiefComplaintMaster\":[]}", actualChiefComplaintsMaster);
    }

    @Test
    void testGetChiefComplaintsMaster2() {
        // Arrange
        when(chiefComplaintMasterRepo.getChiefComplaintMaster()).thenThrow(new RuntimeException("chiefComplaintMaster"));

        // Act and Assert
        assertThrows(RuntimeException.class,
                () -> commonPatientAppMasterServiceImpl.getChiefComplaintsMaster(1, 1, "Gender"));
        verify(chiefComplaintMasterRepo).getChiefComplaintMaster();
    }

    @Test
    void testGetCovidMaster() {
        // Arrange
        when(covidContactHistoryMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(covidRecommnedationMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(covidSymptomsMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualCovidMaster = commonPatientAppMasterServiceImpl.getCovidMaster(1, 1, "Gender");

        // Assert
        verify(covidContactHistoryMasterRepo).findByDeleted(isA(Boolean.class));
        verify(covidRecommnedationMasterRepo).findByDeleted(isA(Boolean.class));
        verify(covidSymptomsMasterRepo).findByDeleted(isA(Boolean.class));
        assertEquals("{\"covidSymptomsMaster\":[],\"covidContactHistoryMaster\":[],\"covidRecommendationMaster\":[]}",
                actualCovidMaster);
    }

    @Test
    void testGetCovidMaster2() {
        // Arrange
        when(covidSymptomsMasterRepo.findByDeleted(Mockito.<Boolean>any()))
                .thenThrow(new RuntimeException("covidSymptomsMaster"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> commonPatientAppMasterServiceImpl.getCovidMaster(1, 1, "Gender"));
        verify(covidSymptomsMasterRepo).findByDeleted(isA(Boolean.class));
    }

    @Test
    void testGetCovidMaster3() {
        // Arrange
        ArrayList<CovidContactHistoryMaster> covidContactHistoryMasterList = new ArrayList<>();
        covidContactHistoryMasterList.add(new CovidContactHistoryMaster());
        when(covidContactHistoryMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(covidContactHistoryMasterList);
        when(covidRecommnedationMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(covidSymptomsMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualCovidMaster = commonPatientAppMasterServiceImpl.getCovidMaster(1, 1, "Gender");

        // Assert
        verify(covidContactHistoryMasterRepo).findByDeleted(isA(Boolean.class));
        verify(covidRecommnedationMasterRepo).findByDeleted(isA(Boolean.class));
        verify(covidSymptomsMasterRepo).findByDeleted(isA(Boolean.class));
        assertEquals("{\"covidSymptomsMaster\":[],\"covidContactHistoryMaster\":[{}],\"covidRecommendationMaster\":[]}",
                actualCovidMaster);
    }

    @Test
    void testGetCovidMaster4() {
        // Arrange
        ArrayList<CovidContactHistoryMaster> covidContactHistoryMasterList = new ArrayList<>();
        covidContactHistoryMasterList.add(new CovidContactHistoryMaster());
        covidContactHistoryMasterList.add(new CovidContactHistoryMaster());
        when(covidContactHistoryMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(covidContactHistoryMasterList);
        when(covidRecommnedationMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(covidSymptomsMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualCovidMaster = commonPatientAppMasterServiceImpl.getCovidMaster(1, 1, "Gender");

        // Assert
        verify(covidContactHistoryMasterRepo).findByDeleted(isA(Boolean.class));
        verify(covidRecommnedationMasterRepo).findByDeleted(isA(Boolean.class));
        verify(covidSymptomsMasterRepo).findByDeleted(isA(Boolean.class));
        assertEquals("{\"covidSymptomsMaster\":[],\"covidContactHistoryMaster\":[{},{}],\"covidRecommendationMaster\":[]}",
                actualCovidMaster);
    }

    @Test
    void testGetMaster() {
        // Arrange, Act and Assert
        assertEquals(
                "{\"authKey\":\"8BfRcBAGkTQkbQ6\",\"vanID\":220,\"authSecret\":\"jgQRyySBGu2YcaJ\",\"providerID\":500,\"appId\":85696"
                        + ",\"servicePointID\":235,\"parkingPlaceID\":233,\"providerServiceMapID\":1261,\"serviceID\":4,\"schedulingSlotSize"
                        + "\":5}",
                commonPatientAppMasterServiceImpl.getMaster(1));
    }

    @Test
    void testSaveCovidScreeningData() throws Exception {
        commonPatientAppMasterServiceImpl.saveCovidScreeningData("Request Obj");
    }

    @Test
    void testSavechiefComplaintsData() throws Exception {
        commonPatientAppMasterServiceImpl.savechiefComplaintsData("Request Obj");
    }

    @Test
    void testBookTCSlotData() throws Exception {
        commonPatientAppMasterServiceImpl.bookTCSlotData("Request Obj", "JaneDoe");
    }

    @Test
    void testGetPatientEpisodeData() throws Exception {
        // Arrange, Act and Assert
        assertThrows(RuntimeException.class, () -> commonPatientAppMasterServiceImpl.getPatientEpisodeData(""));
        assertThrows(RuntimeException.class, () -> commonPatientAppMasterServiceImpl.getPatientEpisodeData(null));
    }

    @Test
    void testGetPatientBookedSlots() throws Exception {
        // Arrange, Act and Assert
        assertThrows(RuntimeException.class, () -> commonPatientAppMasterServiceImpl.getPatientBookedSlots(""));
        assertThrows(RuntimeException.class, () -> commonPatientAppMasterServiceImpl.getPatientBookedSlots(null));
    }

    @Test
    void testSaveSpecialistDiagnosisData() throws Exception {
        // Arrange, Act and Assert
        assertThrows(RuntimeException.class, () -> commonPatientAppMasterServiceImpl.saveSpecialistDiagnosisData(""));
        assertThrows(RuntimeException.class, () -> commonPatientAppMasterServiceImpl.saveSpecialistDiagnosisData(null));
    }

    @Test
    void testGetSpecialistDiagnosisData() throws Exception {
        // Arrange, Act and Assert
        assertThrows(RuntimeException.class, () -> commonPatientAppMasterServiceImpl.getSpecialistDiagnosisData(""));
        assertThrows(RuntimeException.class, () -> commonPatientAppMasterServiceImpl.getSpecialistDiagnosisData(null));
    }

    @Test
    void testGetPatientsLast_3_Episode() throws Exception {
        // Arrange, Act and Assert
        assertThrows(RuntimeException.class, () -> commonPatientAppMasterServiceImpl.getPatientsLast_3_Episode(""));
        assertThrows(RuntimeException.class, () -> commonPatientAppMasterServiceImpl.getPatientsLast_3_Episode(null));
    }
}
