package com.iemr.hwc.service.neonatal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
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

import com.iemr.hwc.data.adolescent.OralVitaminNoOfDoseMaster;
import com.iemr.hwc.data.doctor.ChiefComplaintMaster;
import com.iemr.hwc.data.masterdata.anc.ChildVaccinations;
import com.iemr.hwc.data.neonatal.BirthDoseVaccinesMasterData;
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
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class NeonatalMasterServiceImplTest {
    @Mock
    private BirthDoseVaccinesMasterRepo birthDoseVaccinesMasterRepo;

    @Mock
    private ChiefComplaintMasterRepo chiefComplaintMasterRepo;

    @Mock
    private ChildVaccinationsRepo childVaccinationsRepo;

    @Mock
    private ComplicationTypesRepo complicationTypesRepo;

    @Mock
    private CongenitalAnomaliesMasterRepo congenitalAnomaliesMasterRepo;

    @Mock
    private CounsellingProvidedMasterRepo counsellingProvidedMasterRepo;

    @Mock
    private CurrentImmunizationServiceMasterRepo currentImmunizationServiceMasterRepo;

    @Mock
    private DeliveryConductedByMasterRepo deliveryConductedByMasterRepo;

    @Mock
    private DeliveryPlaceRepo deliveryPlaceRepo;

    @Mock
    private DeliveryTypeRepo deliveryTypeRepo;

    @Mock
    private GestationRepo gestationRepo;

    @InjectMocks
    private NeonatalMasterServiceImpl neonatalMasterServiceImpl;

    @Mock
    private NextDueVaccinesMasterRepo nextDueVaccinesMasterRepo;

    @Mock
    private NextImmunizationLocationMasterRepo nextImmunizationLocationMasterRepo;

    @Mock
    private OralVitaminNoOfDoseMasterRepo oralVitaminNoOfDoseMasterRepo;

    @Mock
    private ProcedureRepo procedureRepo;

    @Mock
    private TypeOfImmunizationMasterRepo typeOfImmunizationMasterRepo;

    @Test
    void testGetNurseMasterDataNeonatal() throws IEMRException {
        // Arrange
        when(birthDoseVaccinesMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(childVaccinationsRepo.findByDeletedOrderByVaccinationTime(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(complicationTypesRepo.getComplicationTypes(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(congenitalAnomaliesMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(currentImmunizationServiceMasterRepo.findByDeletedAndVisitCategoryIDOrderById(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(deliveryConductedByMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(deliveryPlaceRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(deliveryTypeRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(gestationRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextDueVaccinesMasterRepo.findByDeletedOrderById(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextImmunizationLocationMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(typeOfImmunizationMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualNurseMasterDataNeonatal = neonatalMasterServiceImpl.getNurseMasterDataNeonatal(1, 1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).findByDeletedOrderByChiefComplaint(isA(Boolean.class));
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(childVaccinationsRepo).findByDeletedOrderByVaccinationTime(isA(Boolean.class));
        verify(complicationTypesRepo).getComplicationTypes(eq("Birth Complication"));
        verify(deliveryPlaceRepo).findByDeleted(isA(Boolean.class));
        verify(deliveryTypeRepo).findByDeleted(isA(Boolean.class));
        verify(gestationRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(deliveryConductedByMasterRepo).findByDeleted(isA(Boolean.class));
        verify(birthDoseVaccinesMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(congenitalAnomaliesMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(counsellingProvidedMasterRepo).findByDeletedAndVisitCategoryIDOrderByName(isA(Boolean.class),
                isA(Integer.class));
        verify(currentImmunizationServiceMasterRepo).findByDeletedAndVisitCategoryIDOrderById(isA(Boolean.class),
                isA(Integer.class));
        verify(nextDueVaccinesMasterRepo).findByDeletedOrderById(isA(Boolean.class));
        verify(nextImmunizationLocationMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(typeOfImmunizationMasterRepo).findByDeleted(isA(Boolean.class));
        assertEquals(
                "{\"deliveryConductedByMaster\":[],\"m_nextduevaccines\":[],\"m_congenitalanomalies\":[],\"deliveryPlaces\":["
                        + "],\"m_counsellingprovided\":[],\"childVaccinations\":[],\"chiefComplaintMaster\":[],\"m_immunizationservicestype"
                        + "\":[],\"m_birthdosevaccinationreceivedat\":[],\"m_currentimmunizationservice\":[],\"procedures\":[],\"m"
                        + "_locationofnextimmunization\":[],\"birthComplications\":[],\"gestation\":[],\"deliveryTypes\":[]}",
                actualNurseMasterDataNeonatal);
    }

    @Test
    void testGetNurseMasterDataNeonatal2() throws IEMRException {
        // Arrange
        ArrayList<BirthDoseVaccinesMasterData> birthDoseVaccinesMasterDataList = new ArrayList<>();
        birthDoseVaccinesMasterDataList.add(new BirthDoseVaccinesMasterData());
        when(birthDoseVaccinesMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any()))
                .thenReturn(birthDoseVaccinesMasterDataList);
        when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(childVaccinationsRepo.findByDeletedOrderByVaccinationTime(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(complicationTypesRepo.getComplicationTypes(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(congenitalAnomaliesMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(currentImmunizationServiceMasterRepo.findByDeletedAndVisitCategoryIDOrderById(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(deliveryConductedByMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(deliveryPlaceRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(deliveryTypeRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(gestationRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextDueVaccinesMasterRepo.findByDeletedOrderById(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextImmunizationLocationMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(typeOfImmunizationMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualNurseMasterDataNeonatal = neonatalMasterServiceImpl.getNurseMasterDataNeonatal(1, 1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).findByDeletedOrderByChiefComplaint(isA(Boolean.class));
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(childVaccinationsRepo).findByDeletedOrderByVaccinationTime(isA(Boolean.class));
        verify(complicationTypesRepo).getComplicationTypes(eq("Birth Complication"));
        verify(deliveryPlaceRepo).findByDeleted(isA(Boolean.class));
        verify(deliveryTypeRepo).findByDeleted(isA(Boolean.class));
        verify(gestationRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(deliveryConductedByMasterRepo).findByDeleted(isA(Boolean.class));
        verify(birthDoseVaccinesMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(congenitalAnomaliesMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(counsellingProvidedMasterRepo).findByDeletedAndVisitCategoryIDOrderByName(isA(Boolean.class),
                isA(Integer.class));
        verify(currentImmunizationServiceMasterRepo).findByDeletedAndVisitCategoryIDOrderById(isA(Boolean.class),
                isA(Integer.class));
        verify(nextDueVaccinesMasterRepo).findByDeletedOrderById(isA(Boolean.class));
        verify(nextImmunizationLocationMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(typeOfImmunizationMasterRepo).findByDeleted(isA(Boolean.class));
        assertEquals(
                "{\"deliveryConductedByMaster\":[],\"m_nextduevaccines\":[],\"m_congenitalanomalies\":[],\"deliveryPlaces\":["
                        + "],\"m_counsellingprovided\":[],\"childVaccinations\":[],\"chiefComplaintMaster\":[],\"m_immunizationservicestype"
                        + "\":[],\"m_birthdosevaccinationreceivedat\":[{}],\"m_currentimmunizationservice\":[],\"procedures\":[],\"m"
                        + "_locationofnextimmunization\":[],\"birthComplications\":[],\"gestation\":[],\"deliveryTypes\":[]}",
                actualNurseMasterDataNeonatal);
    }

    @Test
    void testGetNurseMasterDataNeonatal3() throws IEMRException {
        // Arrange
        ArrayList<BirthDoseVaccinesMasterData> birthDoseVaccinesMasterDataList = new ArrayList<>();
        birthDoseVaccinesMasterDataList.add(new BirthDoseVaccinesMasterData());
        birthDoseVaccinesMasterDataList.add(new BirthDoseVaccinesMasterData());
        when(birthDoseVaccinesMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any()))
                .thenReturn(birthDoseVaccinesMasterDataList);
        when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(childVaccinationsRepo.findByDeletedOrderByVaccinationTime(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(complicationTypesRepo.getComplicationTypes(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(congenitalAnomaliesMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(currentImmunizationServiceMasterRepo.findByDeletedAndVisitCategoryIDOrderById(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(deliveryConductedByMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(deliveryPlaceRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(deliveryTypeRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(gestationRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextDueVaccinesMasterRepo.findByDeletedOrderById(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextImmunizationLocationMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(typeOfImmunizationMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualNurseMasterDataNeonatal = neonatalMasterServiceImpl.getNurseMasterDataNeonatal(1, 1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).findByDeletedOrderByChiefComplaint(isA(Boolean.class));
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(childVaccinationsRepo).findByDeletedOrderByVaccinationTime(isA(Boolean.class));
        verify(complicationTypesRepo).getComplicationTypes(eq("Birth Complication"));
        verify(deliveryPlaceRepo).findByDeleted(isA(Boolean.class));
        verify(deliveryTypeRepo).findByDeleted(isA(Boolean.class));
        verify(gestationRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(deliveryConductedByMasterRepo).findByDeleted(isA(Boolean.class));
        verify(birthDoseVaccinesMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(congenitalAnomaliesMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(counsellingProvidedMasterRepo).findByDeletedAndVisitCategoryIDOrderByName(isA(Boolean.class),
                isA(Integer.class));
        verify(currentImmunizationServiceMasterRepo).findByDeletedAndVisitCategoryIDOrderById(isA(Boolean.class),
                isA(Integer.class));
        verify(nextDueVaccinesMasterRepo).findByDeletedOrderById(isA(Boolean.class));
        verify(nextImmunizationLocationMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(typeOfImmunizationMasterRepo).findByDeleted(isA(Boolean.class));
        assertEquals(
                "{\"deliveryConductedByMaster\":[],\"m_nextduevaccines\":[],\"m_congenitalanomalies\":[],\"deliveryPlaces\":["
                        + "],\"m_counsellingprovided\":[],\"childVaccinations\":[],\"chiefComplaintMaster\":[],\"m_immunizationservicestype"
                        + "\":[],\"m_birthdosevaccinationreceivedat\":[{},{}],\"m_currentimmunizationservice\":[],\"procedures\":[],\"m"
                        + "_locationofnextimmunization\":[],\"birthComplications\":[],\"gestation\":[],\"deliveryTypes\":[]}",
                actualNurseMasterDataNeonatal);
    }

    @Test
    void testGetNurseMasterDataNeonatal4() throws IEMRException {
        // Arrange
        when(birthDoseVaccinesMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        ChiefComplaintMaster chiefComplaintMaster = new ChiefComplaintMaster();
        chiefComplaintMaster.setChiefComplaint("m_birthdosevaccinationreceivedat");
        chiefComplaintMaster.setChiefComplaintDesc("m_birthdosevaccinationreceivedat");
        chiefComplaintMaster.setChiefComplaintID(1);
        chiefComplaintMaster.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        chiefComplaintMaster.setCreatedDate(mock(Timestamp.class));
        chiefComplaintMaster.setDeleted(true);
        chiefComplaintMaster.setLastModDate(mock(Timestamp.class));
        chiefComplaintMaster.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        chiefComplaintMaster.setProcessed('\u0001');

        ArrayList<ChiefComplaintMaster> chiefComplaintMasterList = new ArrayList<>();
        chiefComplaintMasterList.add(chiefComplaintMaster);
        when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(Mockito.<Boolean>any()))
                .thenReturn(chiefComplaintMasterList);
        when(childVaccinationsRepo.findByDeletedOrderByVaccinationTime(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(complicationTypesRepo.getComplicationTypes(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(congenitalAnomaliesMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(currentImmunizationServiceMasterRepo.findByDeletedAndVisitCategoryIDOrderById(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(deliveryConductedByMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(deliveryPlaceRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(deliveryTypeRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(gestationRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextDueVaccinesMasterRepo.findByDeletedOrderById(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextImmunizationLocationMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(typeOfImmunizationMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualNurseMasterDataNeonatal = neonatalMasterServiceImpl.getNurseMasterDataNeonatal(1, 1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).findByDeletedOrderByChiefComplaint(isA(Boolean.class));
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(childVaccinationsRepo).findByDeletedOrderByVaccinationTime(isA(Boolean.class));
        verify(complicationTypesRepo).getComplicationTypes(eq("Birth Complication"));
        verify(deliveryPlaceRepo).findByDeleted(isA(Boolean.class));
        verify(deliveryTypeRepo).findByDeleted(isA(Boolean.class));
        verify(gestationRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(deliveryConductedByMasterRepo).findByDeleted(isA(Boolean.class));
        verify(birthDoseVaccinesMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(congenitalAnomaliesMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(counsellingProvidedMasterRepo).findByDeletedAndVisitCategoryIDOrderByName(isA(Boolean.class),
                isA(Integer.class));
        verify(currentImmunizationServiceMasterRepo).findByDeletedAndVisitCategoryIDOrderById(isA(Boolean.class),
                isA(Integer.class));
        verify(nextDueVaccinesMasterRepo).findByDeletedOrderById(isA(Boolean.class));
        verify(nextImmunizationLocationMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(typeOfImmunizationMasterRepo).findByDeleted(isA(Boolean.class));
        assertEquals(
                "{\"deliveryConductedByMaster\":[],\"m_nextduevaccines\":[],\"m_congenitalanomalies\":[],\"deliveryPlaces\":["
                        + "],\"m_counsellingprovided\":[],\"childVaccinations\":[],\"chiefComplaintMaster\":[{\"chiefComplaintID\":1,"
                        + "\"chiefComplaint\":\"m_birthdosevaccinationreceivedat\",\"chiefComplaintDesc\":\"m_birthdosevaccinationreceivedat"
                        + "\"}],\"m_immunizationservicestype\":[],\"m_birthdosevaccinationreceivedat\":[],\"m_currentimmunizationservice"
                        + "\":[],\"procedures\":[],\"m_locationofnextimmunization\":[],\"birthComplications\":[],\"gestation\":[],"
                        + "\"deliveryTypes\":[]}",
                actualNurseMasterDataNeonatal);
    }

    @Test
    void testGetNurseMasterDataNeonatal5() throws IEMRException {
        // Arrange
        when(birthDoseVaccinesMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());

        ArrayList<ChildVaccinations> childVaccinationsList = new ArrayList<>();
        childVaccinationsList.add(new ChildVaccinations());
        when(childVaccinationsRepo.findByDeletedOrderByVaccinationTime(Mockito.<Boolean>any()))
                .thenReturn(childVaccinationsList);
        when(complicationTypesRepo.getComplicationTypes(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(congenitalAnomaliesMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(currentImmunizationServiceMasterRepo.findByDeletedAndVisitCategoryIDOrderById(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(deliveryConductedByMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(deliveryPlaceRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(deliveryTypeRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(gestationRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextDueVaccinesMasterRepo.findByDeletedOrderById(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextImmunizationLocationMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(typeOfImmunizationMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualNurseMasterDataNeonatal = neonatalMasterServiceImpl.getNurseMasterDataNeonatal(1, 1, "Gender");

        // Assert
        verify(chiefComplaintMasterRepo).findByDeletedOrderByChiefComplaint(isA(Boolean.class));
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(childVaccinationsRepo).findByDeletedOrderByVaccinationTime(isA(Boolean.class));
        verify(complicationTypesRepo).getComplicationTypes(eq("Birth Complication"));
        verify(deliveryPlaceRepo).findByDeleted(isA(Boolean.class));
        verify(deliveryTypeRepo).findByDeleted(isA(Boolean.class));
        verify(gestationRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(deliveryConductedByMasterRepo).findByDeleted(isA(Boolean.class));
        verify(birthDoseVaccinesMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(congenitalAnomaliesMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(counsellingProvidedMasterRepo).findByDeletedAndVisitCategoryIDOrderByName(isA(Boolean.class),
                isA(Integer.class));
        verify(currentImmunizationServiceMasterRepo).findByDeletedAndVisitCategoryIDOrderById(isA(Boolean.class),
                isA(Integer.class));
        verify(nextDueVaccinesMasterRepo).findByDeletedOrderById(isA(Boolean.class));
        verify(nextImmunizationLocationMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(typeOfImmunizationMasterRepo).findByDeleted(isA(Boolean.class));
        assertEquals(
                "{\"deliveryConductedByMaster\":[],\"m_nextduevaccines\":[],\"m_congenitalanomalies\":[],\"deliveryPlaces\":["
                        + "],\"m_counsellingprovided\":[],\"childVaccinations\":[{}],\"chiefComplaintMaster\":[],\"m_immunizationservicestype"
                        + "\":[],\"m_birthdosevaccinationreceivedat\":[],\"m_currentimmunizationservice\":[],\"procedures\":[],\"m"
                        + "_locationofnextimmunization\":[],\"birthComplications\":[],\"gestation\":[],\"deliveryTypes\":[]}",
                actualNurseMasterDataNeonatal);
    }

    @Test
    void testGetNurseMasterDataNeonatal6() throws IEMRException {
        // Arrange
        when(oralVitaminNoOfDoseMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(birthDoseVaccinesMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(childVaccinationsRepo.findByDeletedOrderByVaccinationTime(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(complicationTypesRepo.getComplicationTypes(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(congenitalAnomaliesMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(currentImmunizationServiceMasterRepo.findByDeletedAndVisitCategoryIDOrderById(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(deliveryConductedByMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(deliveryPlaceRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(deliveryTypeRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(gestationRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextDueVaccinesMasterRepo.findByDeletedOrderById(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextImmunizationLocationMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(typeOfImmunizationMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualNurseMasterDataNeonatal = neonatalMasterServiceImpl.getNurseMasterDataNeonatal(11, 1, "Gender");

        // Assert
        verify(oralVitaminNoOfDoseMasterRepo).findByDeleted(isA(Boolean.class));
        verify(chiefComplaintMasterRepo).findByDeletedOrderByChiefComplaint(isA(Boolean.class));
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(childVaccinationsRepo).findByDeletedOrderByVaccinationTime(isA(Boolean.class));
        verify(complicationTypesRepo).getComplicationTypes(eq("Birth Complication"));
        verify(deliveryPlaceRepo).findByDeleted(isA(Boolean.class));
        verify(deliveryTypeRepo).findByDeleted(isA(Boolean.class));
        verify(gestationRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(deliveryConductedByMasterRepo).findByDeleted(isA(Boolean.class));
        verify(birthDoseVaccinesMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(congenitalAnomaliesMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(counsellingProvidedMasterRepo).findByDeletedAndVisitCategoryIDOrderByName(isA(Boolean.class),
                isA(Integer.class));
        verify(currentImmunizationServiceMasterRepo).findByDeletedAndVisitCategoryIDOrderById(isA(Boolean.class),
                isA(Integer.class));
        verify(nextDueVaccinesMasterRepo).findByDeletedOrderById(isA(Boolean.class));
        verify(nextImmunizationLocationMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(typeOfImmunizationMasterRepo).findByDeleted(isA(Boolean.class));
        assertEquals(
                "{\"deliveryConductedByMaster\":[],\"m_nextduevaccines\":[],\"m_congenitalanomalies\":[],\"deliveryPlaces\":["
                        + "],\"m_counsellingprovided\":[],\"oralVitaminNoDose\":[],\"childVaccinations\":[],\"chiefComplaintMaster\":[]"
                        + ",\"m_immunizationservicestype\":[],\"m_birthdosevaccinationreceivedat\":[],\"m_currentimmunizationservice"
                        + "\":[],\"procedures\":[],\"m_locationofnextimmunization\":[],\"birthComplications\":[],\"gestation\":[],"
                        + "\"deliveryTypes\":[]}",
                actualNurseMasterDataNeonatal);
    }

    @Test
    void testGetNurseMasterDataNeonatal7() throws IEMRException {
        // Arrange
        OralVitaminNoOfDoseMaster oralVitaminNoOfDoseMaster = new OralVitaminNoOfDoseMaster();
        oralVitaminNoOfDoseMaster.setDeleted(true);
        oralVitaminNoOfDoseMaster.setId((short) 1);
        oralVitaminNoOfDoseMaster.setName("m_birthdosevaccinationreceivedat");

        ArrayList<OralVitaminNoOfDoseMaster> oralVitaminNoOfDoseMasterList = new ArrayList<>();
        oralVitaminNoOfDoseMasterList.add(oralVitaminNoOfDoseMaster);
        when(oralVitaminNoOfDoseMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(oralVitaminNoOfDoseMasterList);
        when(birthDoseVaccinesMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(chiefComplaintMasterRepo.findByDeletedOrderByChiefComplaint(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(childVaccinationsRepo.findByDeletedOrderByVaccinationTime(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(complicationTypesRepo.getComplicationTypes(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(congenitalAnomaliesMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(currentImmunizationServiceMasterRepo.findByDeletedAndVisitCategoryIDOrderById(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(deliveryConductedByMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(deliveryPlaceRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(deliveryTypeRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(gestationRepo.findByDeletedOrderByName(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextDueVaccinesMasterRepo.findByDeletedOrderById(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextImmunizationLocationMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(procedureRepo.getProcedureMasterData(Mockito.<Integer>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(typeOfImmunizationMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        String actualNurseMasterDataNeonatal = neonatalMasterServiceImpl.getNurseMasterDataNeonatal(11, 1, "Gender");

        // Assert
        verify(oralVitaminNoOfDoseMasterRepo).findByDeleted(isA(Boolean.class));
        verify(chiefComplaintMasterRepo).findByDeletedOrderByChiefComplaint(isA(Boolean.class));
        verify(procedureRepo).getProcedureMasterData(isA(Integer.class), eq("Gender"));
        verify(childVaccinationsRepo).findByDeletedOrderByVaccinationTime(isA(Boolean.class));
        verify(complicationTypesRepo).getComplicationTypes(eq("Birth Complication"));
        verify(deliveryPlaceRepo).findByDeleted(isA(Boolean.class));
        verify(deliveryTypeRepo).findByDeleted(isA(Boolean.class));
        verify(gestationRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(deliveryConductedByMasterRepo).findByDeleted(isA(Boolean.class));
        verify(birthDoseVaccinesMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(congenitalAnomaliesMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(counsellingProvidedMasterRepo).findByDeletedAndVisitCategoryIDOrderByName(isA(Boolean.class),
                isA(Integer.class));
        verify(currentImmunizationServiceMasterRepo).findByDeletedAndVisitCategoryIDOrderById(isA(Boolean.class),
                isA(Integer.class));
        verify(nextDueVaccinesMasterRepo).findByDeletedOrderById(isA(Boolean.class));
        verify(nextImmunizationLocationMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(typeOfImmunizationMasterRepo).findByDeleted(isA(Boolean.class));
        assertEquals(
                "{\"deliveryConductedByMaster\":[],\"m_nextduevaccines\":[],\"m_congenitalanomalies\":[],\"deliveryPlaces\":["
                        + "],\"m_counsellingprovided\":[],\"oralVitaminNoDose\":[{\"id\":1,\"name\":\"m_birthdosevaccinationreceivedat\"}"
                        + "],\"childVaccinations\":[],\"chiefComplaintMaster\":[],\"m_immunizationservicestype\":[],\"m_birthdosevacci"
                        + "nationreceivedat\":[],\"m_currentimmunizationservice\":[],\"procedures\":[],\"m_locationofnextimmunization"
                        + "\":[],\"birthComplications\":[],\"gestation\":[],\"deliveryTypes\":[]}",
                actualNurseMasterDataNeonatal);
    }
}
