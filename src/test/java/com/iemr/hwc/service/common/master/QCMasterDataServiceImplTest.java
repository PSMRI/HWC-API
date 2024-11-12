package com.iemr.hwc.service.common.master;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.doctor.TempMasterDrug;
import com.iemr.hwc.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.hwc.repo.doctor.DrugDoseMasterRepo;
import com.iemr.hwc.repo.doctor.DrugDurationUnitMasterRepo;
import com.iemr.hwc.repo.doctor.DrugFormMasterRepo;
import com.iemr.hwc.repo.doctor.DrugFrequencyMasterRepo;
import com.iemr.hwc.repo.doctor.LabTestMasterRepo;
import com.iemr.hwc.repo.doctor.TempMasterDrugRepo;
import com.iemr.hwc.repo.labModule.ProcedureRepo;

@ExtendWith(MockitoExtension.class)
class QCMasterDataServiceImplTest {
    @Mock
    private ChiefComplaintMasterRepo chiefComplaintMasterRepo;

    @Mock
    private DrugDoseMasterRepo drugDoseMasterRepo;

    @Mock
    private DrugDurationUnitMasterRepo drugDurationUnitMasterRepo;

    @Mock
    private DrugFormMasterRepo drugFormMasterRepo;

    @Mock
    private DrugFrequencyMasterRepo drugFrequencyMasterRepo;

    @Mock
    private LabTestMasterRepo labTestMasterRepo;

    @Mock
    private ProcedureRepo procedureRepo;

    @InjectMocks
    private QCMasterDataServiceImpl qCMasterDataServiceImpl;

    @Mock
    private TempMasterDrugRepo tempMasterDrugRepo;

    @Test
    void testGetQuickConsultMasterData() {
        // Arrange
        when(chiefComplaintMasterRepo.getChiefComplaintMaster()).thenReturn(new ArrayList<>());
        when(drugDoseMasterRepo.getDrugDoseMaster()).thenReturn(new ArrayList<>());
        when(drugDurationUnitMasterRepo.getDrugDurationUnitMaster()).thenReturn(new ArrayList<>());
        when(drugFormMasterRepo.getDrugFormMaster()).thenReturn(new ArrayList<>());
        when(drugFrequencyMasterRepo.getDrugFrequencyMaster()).thenReturn(new ArrayList<>());
        when(labTestMasterRepo.getLabTestMaster()).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(tempMasterDrugRepo.findByDeletedFalseOrderByDrugDisplayNameAsc()).thenReturn(new ArrayList<>());

        // Act
        String actualQuickConsultMasterData = qCMasterDataServiceImpl.getQuickConsultMasterData(1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).getChiefComplaintMaster();
        verify(drugDoseMasterRepo).getDrugDoseMaster();
        verify(drugDurationUnitMasterRepo).getDrugDurationUnitMaster();
        verify(drugFormMasterRepo).getDrugFormMaster();
        verify(drugFrequencyMasterRepo).getDrugFrequencyMaster();
        verify(labTestMasterRepo).getLabTestMaster();
        verify(tempMasterDrugRepo).findByDeletedFalseOrderByDrugDisplayNameAsc();
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        assertEquals(
                "{\"drugDoseMaster\":[],\"drugDurationUnitMaster\":[],\"drugFormMaster\":[],\"labTestMaster\":[],\"procedures\""
                        + ":[],\"drugFrequencyMaster\":[],\"tempDrugMaster\":[],\"chiefComplaintMaster\":[]}",
                actualQuickConsultMasterData);
    }

    @Test
    void testGetQuickConsultMasterData2() {
        // Arrange
        when(chiefComplaintMasterRepo.getChiefComplaintMaster()).thenReturn(new ArrayList<>());
        when(drugDoseMasterRepo.getDrugDoseMaster()).thenReturn(new ArrayList<>());
        when(drugDurationUnitMasterRepo.getDrugDurationUnitMaster()).thenReturn(new ArrayList<>());
        when(drugFormMasterRepo.getDrugFormMaster()).thenReturn(new ArrayList<>());
        when(drugFrequencyMasterRepo.getDrugFrequencyMaster()).thenReturn(new ArrayList<>());
        when(labTestMasterRepo.getLabTestMaster()).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        TempMasterDrug tempMasterDrug = new TempMasterDrug();
        tempMasterDrug.setDeleted(true);
        tempMasterDrug.setDrugDisplayName("chiefComplaintMaster");
        tempMasterDrug.setDrugID(1);
        tempMasterDrug.setDrugName("chiefComplaintMaster");

        ArrayList<TempMasterDrug> tempMasterDrugList = new ArrayList<>();
        tempMasterDrugList.add(tempMasterDrug);
        when(tempMasterDrugRepo.findByDeletedFalseOrderByDrugDisplayNameAsc()).thenReturn(tempMasterDrugList);

        // Act
        String actualQuickConsultMasterData = qCMasterDataServiceImpl.getQuickConsultMasterData(1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).getChiefComplaintMaster();
        verify(drugDoseMasterRepo).getDrugDoseMaster();
        verify(drugDurationUnitMasterRepo).getDrugDurationUnitMaster();
        verify(drugFormMasterRepo).getDrugFormMaster();
        verify(drugFrequencyMasterRepo).getDrugFrequencyMaster();
        verify(labTestMasterRepo).getLabTestMaster();
        verify(tempMasterDrugRepo).findByDeletedFalseOrderByDrugDisplayNameAsc();
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        assertEquals(
                "{\"drugDoseMaster\":[],\"drugDurationUnitMaster\":[],\"drugFormMaster\":[],\"labTestMaster\":[],\"procedures\""
                        + ":[],\"drugFrequencyMaster\":[],\"tempDrugMaster\":[{\"drugID\":1,\"drugDisplayName\":\"chiefComplaintMaster\"}"
                        + "],\"chiefComplaintMaster\":[]}",
                actualQuickConsultMasterData);
    }

    @Test
    void testGetQuickConsultMasterData3() {
        // Arrange
        when(chiefComplaintMasterRepo.getChiefComplaintMaster()).thenReturn(new ArrayList<>());
        when(drugDoseMasterRepo.getDrugDoseMaster()).thenReturn(new ArrayList<>());
        when(drugDurationUnitMasterRepo.getDrugDurationUnitMaster()).thenReturn(new ArrayList<>());
        when(drugFormMasterRepo.getDrugFormMaster()).thenReturn(new ArrayList<>());
        when(drugFrequencyMasterRepo.getDrugFrequencyMaster()).thenReturn(new ArrayList<>());
        when(labTestMasterRepo.getLabTestMaster()).thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        TempMasterDrug tempMasterDrug = new TempMasterDrug();
        tempMasterDrug.setDeleted(true);
        tempMasterDrug.setDrugDisplayName("chiefComplaintMaster");
        tempMasterDrug.setDrugID(1);
        tempMasterDrug.setDrugName("chiefComplaintMaster");

        TempMasterDrug tempMasterDrug2 = new TempMasterDrug();
        tempMasterDrug2.setDeleted(false);
        tempMasterDrug2.setDrugDisplayName("drugDoseMaster");
        tempMasterDrug2.setDrugID(2);
        tempMasterDrug2.setDrugName("drugDoseMaster");

        ArrayList<TempMasterDrug> tempMasterDrugList = new ArrayList<>();
        tempMasterDrugList.add(tempMasterDrug2);
        tempMasterDrugList.add(tempMasterDrug);
        when(tempMasterDrugRepo.findByDeletedFalseOrderByDrugDisplayNameAsc()).thenReturn(tempMasterDrugList);

        // Act
        String actualQuickConsultMasterData = qCMasterDataServiceImpl.getQuickConsultMasterData(1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).getChiefComplaintMaster();
        verify(drugDoseMasterRepo).getDrugDoseMaster();
        verify(drugDurationUnitMasterRepo).getDrugDurationUnitMaster();
        verify(drugFormMasterRepo).getDrugFormMaster();
        verify(drugFrequencyMasterRepo).getDrugFrequencyMaster();
        verify(labTestMasterRepo).getLabTestMaster();
        verify(tempMasterDrugRepo).findByDeletedFalseOrderByDrugDisplayNameAsc();
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        assertEquals(
                "{\"drugDoseMaster\":[],\"drugDurationUnitMaster\":[],\"drugFormMaster\":[],\"labTestMaster\":[],\"procedures\""
                        + ":[],\"drugFrequencyMaster\":[],\"tempDrugMaster\":[{\"drugID\":2,\"drugDisplayName\":\"drugDoseMaster\"},{\"drugID"
                        + "\":1,\"drugDisplayName\":\"chiefComplaintMaster\"}],\"chiefComplaintMaster\":[]}",
                actualQuickConsultMasterData);
    }

    @Test
    void testGettersAndSetters() {
        QCMasterDataServiceImpl qcMasterDataServiceImpl = new QCMasterDataServiceImpl();

        // Act
        qcMasterDataServiceImpl.setChiefComplaintMasterRepo(mock(ChiefComplaintMasterRepo.class));
        qcMasterDataServiceImpl.setDrugDoseMasterRepo(mock(DrugDoseMasterRepo.class));
        qcMasterDataServiceImpl.setDrugDurationUnitMasterRepo(mock(DrugDurationUnitMasterRepo.class));
        qcMasterDataServiceImpl.setDrugFormMasterRepo(mock(DrugFormMasterRepo.class));
        qcMasterDataServiceImpl.setDrugFrequencyMasterRepo(mock(DrugFrequencyMasterRepo.class));
        qcMasterDataServiceImpl.setLabTestMasterRepo(mock(LabTestMasterRepo.class));
        qcMasterDataServiceImpl.setProcedureRepo(mock(ProcedureRepo.class));
        qcMasterDataServiceImpl.setTempMasterDrugRepo(mock(TempMasterDrugRepo.class));
    }
}
