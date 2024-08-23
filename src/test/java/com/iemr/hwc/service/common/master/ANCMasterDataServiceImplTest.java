package com.iemr.hwc.service.common.master;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.masterdata.doctor.ItemMaster;
import com.iemr.hwc.data.masterdata.doctor.M_ItemCategory;
import com.iemr.hwc.data.masterdata.doctor.M_ItemForm;
import com.iemr.hwc.data.masterdata.doctor.M_Manufacturer;
import com.iemr.hwc.data.masterdata.doctor.M_Pharmacologicalcategory;
import com.iemr.hwc.data.masterdata.doctor.M_Route;
import com.iemr.hwc.data.masterdata.doctor.M_Uom;
import com.iemr.hwc.data.neonatal.CounsellingProvidedMasterData;
import com.iemr.hwc.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.hwc.repo.doctor.DrugDoseMasterRepo;
import com.iemr.hwc.repo.doctor.DrugDurationUnitMasterRepo;
import com.iemr.hwc.repo.doctor.DrugFrequencyMasterRepo;
import com.iemr.hwc.repo.foetalmonitor.FoetalMonitorTestsRepo;
import com.iemr.hwc.repo.labModule.ProcedureRepo;
import com.iemr.hwc.repo.login.MasterVanRepo;
import com.iemr.hwc.repo.masterrepo.anc.AllergicReactionTypesRepo;
import com.iemr.hwc.repo.masterrepo.anc.BloodGroupsRepo;
import com.iemr.hwc.repo.masterrepo.anc.ChildVaccinationsRepo;
import com.iemr.hwc.repo.masterrepo.anc.ComorbidConditionRepo;
import com.iemr.hwc.repo.masterrepo.anc.CompFeedsRepo;
import com.iemr.hwc.repo.masterrepo.anc.ComplicationTypesRepo;
import com.iemr.hwc.repo.masterrepo.anc.CounsellingTypeRepo;
import com.iemr.hwc.repo.masterrepo.anc.DeliveryPlaceRepo;
import com.iemr.hwc.repo.masterrepo.anc.DeliveryTypeRepo;
import com.iemr.hwc.repo.masterrepo.anc.DevelopmentProblemsRepo;
import com.iemr.hwc.repo.masterrepo.anc.DiseaseTypeRepo;
import com.iemr.hwc.repo.masterrepo.anc.FundalHeightRepo;
import com.iemr.hwc.repo.masterrepo.anc.GestationRepo;
import com.iemr.hwc.repo.masterrepo.anc.GrossMotorMilestoneRepo;
import com.iemr.hwc.repo.masterrepo.anc.IllnessTypesRepo;
import com.iemr.hwc.repo.masterrepo.anc.JointTypesRepo;
import com.iemr.hwc.repo.masterrepo.anc.MenstrualCycleRangeRepo;
import com.iemr.hwc.repo.masterrepo.anc.MenstrualCycleStatusRepo;
import com.iemr.hwc.repo.masterrepo.anc.MenstrualProblemRepo;
import com.iemr.hwc.repo.masterrepo.anc.MusculoskeletalRepo;
import com.iemr.hwc.repo.masterrepo.anc.OptionalVaccinationsRepo;
import com.iemr.hwc.repo.masterrepo.anc.PersonalHabitTypeRepo;
import com.iemr.hwc.repo.masterrepo.anc.PregDurationRepo;
import com.iemr.hwc.repo.masterrepo.anc.PregOutcomeRepo;
import com.iemr.hwc.repo.masterrepo.anc.ServiceFacilityMasterRepo;
import com.iemr.hwc.repo.masterrepo.anc.ServiceMasterRepo;
import com.iemr.hwc.repo.masterrepo.anc.SurgeryTypesRepo;
import com.iemr.hwc.repo.masterrepo.covid19.CovidContactHistoryMasterRepo;
import com.iemr.hwc.repo.masterrepo.covid19.CovidRecommnedationMasterRepo;
import com.iemr.hwc.repo.masterrepo.covid19.CovidSymptomsMasterRepo;
import com.iemr.hwc.repo.masterrepo.doctor.InstituteRepo;
import com.iemr.hwc.repo.masterrepo.doctor.ItemFormMasterRepo;
import com.iemr.hwc.repo.masterrepo.doctor.ItemMasterRepo;
import com.iemr.hwc.repo.masterrepo.doctor.ReferralReasonRepo;
import com.iemr.hwc.repo.masterrepo.doctor.RouteOfAdminRepo;
import com.iemr.hwc.repo.masterrepo.doctor.V_DrugPrescriptionRepo;
import com.iemr.hwc.repo.masterrepo.generalopd.FoodIntoleranceStatusRepo;
import com.iemr.hwc.repo.masterrepo.generalopd.SubVisitCategoryRepo;
import com.iemr.hwc.repo.masterrepo.ncdCare.NCDCareTypeRepo;
import com.iemr.hwc.repo.masterrepo.nurse.FamilyMemberMasterRepo;
import com.iemr.hwc.repo.masterrepo.pnc.DeliveryConductedByMasterRepo;
import com.iemr.hwc.repo.masterrepo.pnc.NewbornHealthStatusRepo;
import com.iemr.hwc.repo.neonatal.CounsellingProvidedMasterRepo;
import com.iemr.hwc.repo.neonatal.NextDueVaccinesMasterRepo;
import com.iemr.hwc.repo.neonatal.NextImmunizationLocationMasterRepo;

@ExtendWith(MockitoExtension.class)
class ANCMasterDataServiceImplTest {
    @InjectMocks
    private ANCMasterDataServiceImpl aNCMasterDataServiceImpl;

    @Mock
    private AllergicReactionTypesRepo allergicReactionTypesRepo;

    @Mock
    private BloodGroupsRepo bloodGroupsRepo;

    @Mock
    private ChiefComplaintMasterRepo chiefComplaintMasterRepo;

    @Mock
    private ChildVaccinationsRepo childVaccinationsRepo;

    @Mock
    private ComorbidConditionRepo comorbidConditionRepo;

    @Mock
    private CompFeedsRepo compFeedsRepo;

    @Mock
    private ComplicationTypesRepo complicationTypesRepo;

    @Mock
    private CounsellingProvidedMasterRepo counsellingProvidedMasterRepo;

    @Mock
    private CounsellingTypeRepo counsellingTypeRepo;

    @Mock
    private CovidContactHistoryMasterRepo covidContactHistoryMasterRepo;

    @Mock
    private CovidRecommnedationMasterRepo covidRecommnedationMasterRepo;

    @Mock
    private CovidSymptomsMasterRepo covidSymptomsMasterRepo;

    @Mock
    private DeliveryConductedByMasterRepo deliveryConductedByMasterRepo;

    @Mock
    private DeliveryPlaceRepo deliveryPlaceRepo;

    @Mock
    private DeliveryTypeRepo deliveryTypeRepo;

    @Mock
    private DevelopmentProblemsRepo developmentProblemsRepo;

    @Mock
    private DiseaseTypeRepo diseaseTypeRepo;

    @Mock
    private DrugDoseMasterRepo drugDoseMasterRepo;

    @Mock
    private DrugDurationUnitMasterRepo drugDurationUnitMasterRepo;

    @Mock
    private DrugFrequencyMasterRepo drugFrequencyMasterRepo;

    @Mock
    private FamilyMemberMasterRepo familyMemberMasterRepo;

    @Mock
    private FoetalMonitorTestsRepo foetalMonitorTestsRepo;

    @Mock
    private FoodIntoleranceStatusRepo foodIntoleranceStatusRepo;

    @Mock
    private FundalHeightRepo fundalHeightRepo;

    @Mock
    private GestationRepo gestationRepo;

    @Mock
    private GrossMotorMilestoneRepo grossMotorMilestoneRepo;

    @Mock
    private IllnessTypesRepo illnessTypesRepo;

    @Mock
    private InstituteRepo instituteRepo;

    @Mock
    private ItemFormMasterRepo itemFormMasterRepo;

    @Mock
    private ItemMasterRepo itemMasterRepo;

    @Mock
    private JointTypesRepo jointTypesRepo;

    @Mock
    private MasterVanRepo masterVanRepo;

    @Mock
    private MenstrualCycleRangeRepo menstrualCycleRangeRepo;

    @Mock
    private MenstrualCycleStatusRepo menstrualCycleStatusRepo;

    @Mock
    private MenstrualProblemRepo menstrualProblemRepo;

    @Mock
    private MusculoskeletalRepo musculoskeletalRepo;

    @Mock
    private NCDCareTypeRepo nCDCareTypeRepo;

    @Mock
    private NCDScreeningMasterServiceImpl nCDScreeningMasterServiceImpl;

    @Mock
    private NewbornHealthStatusRepo newbornHealthStatusRepo;

    @Mock
    private NextDueVaccinesMasterRepo nextDueVaccinesMasterRepo;

    @Mock
    private NextImmunizationLocationMasterRepo nextImmunizationLocationMasterRepo;

    @Mock
    private OptionalVaccinationsRepo optionalVaccinationsRepo;

    @Mock
    private PersonalHabitTypeRepo personalHabitTypeRepo;

    @Mock
    private PregDurationRepo pregDurationRepo;

    @Mock
    private PregOutcomeRepo pregOutcomeRepo;

    @Mock
    private ProcedureRepo procedureRepo;

    @Mock
    private ReferralReasonRepo referralReasonRepo;

    @Mock
    private RouteOfAdminRepo routeOfAdminRepo;

    @Mock
    private ServiceFacilityMasterRepo serviceFacilityMasterRepo;

    @Mock
    private ServiceMasterRepo serviceMasterRepo;

    @Mock
    private SubVisitCategoryRepo subVisitCategoryRepo;

    @Mock
    private SurgeryTypesRepo surgeryTypesRepo;

    @Mock
    private V_DrugPrescriptionRepo v_DrugPrescriptionRepo;

    @Test
    void testGetCommonNurseMasterDataForGenopdAncNcdcarePnc() {
        Integer visitCategoryID = null;
        Integer providerServiceMapID = null;
        String gender = "";

        // Act
        String actualCommonNurseMasterDataForGenopdAncNcdcarePnc = this.aNCMasterDataServiceImpl
                .getCommonNurseMasterDataForGenopdAncNcdcarePnc(visitCategoryID, providerServiceMapID, gender);

        // Assert
        // TODO: Add assertions on result
    }

    @Test
    void testGetCommonDoctorMasterDataForGenopdAncNcdcarePnc() {
        // Arrange
        when(counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(itemMasterRepo.searchEdl(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(nextDueVaccinesMasterRepo.findByDeletedOrderById(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextImmunizationLocationMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(referralReasonRepo.findByDeletedAndVisitCategoryIdOrderByName(Mockito.<Boolean>any(), Mockito.<Integer>any()))
                .thenReturn(new ArrayList<>());
        when(counsellingTypeRepo.getCounsellingTypes()).thenReturn(new ArrayList<>());
        when(drugDoseMasterRepo.getDrugDoseMaster()).thenReturn(new ArrayList<>());
        when(drugDurationUnitMasterRepo.getDrugDurationUnitMaster()).thenReturn(new ArrayList<>());
        when(drugFrequencyMasterRepo.getDrugFrequencyMaster()).thenReturn(new ArrayList<>());
        when(instituteRepo.getInstituteDetails(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(itemFormMasterRepo.getItemFormMaster()).thenReturn(new ArrayList<>());
        when(routeOfAdminRepo.getRouteOfAdminList()).thenReturn(new ArrayList<>());
        when(serviceMasterRepo.getAdditionalServices()).thenReturn(new ArrayList<>());
        when(v_DrugPrescriptionRepo.getItemListForFacility(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualCommonDoctorMasterDataForGenopdAncNcdcarePnc = aNCMasterDataServiceImpl
                .getCommonDoctorMasterDataForGenopdAncNcdcarePnc(1, 1, "Gender", 1, 1);

        // Assert
        verify(drugDoseMasterRepo).getDrugDoseMaster();
        verify(drugDurationUnitMasterRepo).getDrugDurationUnitMaster();
        verify(drugFrequencyMasterRepo).getDrugFrequencyMaster();
        verify(counsellingTypeRepo).getCounsellingTypes();
        verify(serviceMasterRepo).getAdditionalServices();
        verify(instituteRepo).getInstituteDetails(isA(Integer.class));
        verify(itemFormMasterRepo).getItemFormMaster();
        verify(itemMasterRepo).searchEdl(isA(Integer.class));
        verify(referralReasonRepo).findByDeletedAndVisitCategoryIdOrderByName(isA(Boolean.class), isA(Integer.class));
        verify(routeOfAdminRepo).getRouteOfAdminList();
        verify(v_DrugPrescriptionRepo).getItemListForFacility(isA(Integer.class));
        verify(counsellingProvidedMasterRepo).findByDeletedAndVisitCategoryIDOrderByName(isA(Boolean.class),
                isA(Integer.class));
        verify(nextDueVaccinesMasterRepo).findByDeletedOrderById(isA(Boolean.class));
        verify(nextImmunizationLocationMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        assertEquals(
                "{\"additionalServices\":[],\"routeOfAdmin\":[],\"itemMaster\":[],\"nextDueVaccines\":[],\"drugFrequencyMaster"
                        + "\":[],\"referralReasonList\":[],\"counsellingProvided\":[],\"NonEdlMaster\":[],\"counsellingTypes\":[],"
                        + "\"drugDoseMaster\":[],\"nextImmuLocations\":[],\"drugFormMaster\":[],\"drugDurationUnitMaster\":[],"
                        + "\"higherHealthCare\":[]}",
                actualCommonDoctorMasterDataForGenopdAncNcdcarePnc);
    }

    @Test
    void testGetCommonDoctorMasterDataForGenopdAncNcdcarePnc2() {
        // Arrange
        ArrayList<CounsellingProvidedMasterData> counsellingProvidedMasterDataList = new ArrayList<>();
        counsellingProvidedMasterDataList.add(new CounsellingProvidedMasterData());
        when(counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(counsellingProvidedMasterDataList);
        when(itemMasterRepo.searchEdl(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(nextDueVaccinesMasterRepo.findByDeletedOrderById(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextImmunizationLocationMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(referralReasonRepo.findByDeletedAndVisitCategoryIdOrderByName(Mockito.<Boolean>any(), Mockito.<Integer>any()))
                .thenReturn(new ArrayList<>());
        when(counsellingTypeRepo.getCounsellingTypes()).thenReturn(new ArrayList<>());
        when(drugDoseMasterRepo.getDrugDoseMaster()).thenReturn(new ArrayList<>());
        when(drugDurationUnitMasterRepo.getDrugDurationUnitMaster()).thenReturn(new ArrayList<>());
        when(drugFrequencyMasterRepo.getDrugFrequencyMaster()).thenReturn(new ArrayList<>());
        when(instituteRepo.getInstituteDetails(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(itemFormMasterRepo.getItemFormMaster()).thenReturn(new ArrayList<>());
        when(routeOfAdminRepo.getRouteOfAdminList()).thenReturn(new ArrayList<>());
        when(serviceMasterRepo.getAdditionalServices()).thenReturn(new ArrayList<>());
        when(v_DrugPrescriptionRepo.getItemListForFacility(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualCommonDoctorMasterDataForGenopdAncNcdcarePnc = aNCMasterDataServiceImpl
                .getCommonDoctorMasterDataForGenopdAncNcdcarePnc(1, 1, "Gender", 1, 1);

        // Assert
        verify(drugDoseMasterRepo).getDrugDoseMaster();
        verify(drugDurationUnitMasterRepo).getDrugDurationUnitMaster();
        verify(drugFrequencyMasterRepo).getDrugFrequencyMaster();
        verify(counsellingTypeRepo).getCounsellingTypes();
        verify(serviceMasterRepo).getAdditionalServices();
        verify(instituteRepo).getInstituteDetails(isA(Integer.class));
        verify(itemFormMasterRepo).getItemFormMaster();
        verify(itemMasterRepo).searchEdl(isA(Integer.class));
        verify(referralReasonRepo).findByDeletedAndVisitCategoryIdOrderByName(isA(Boolean.class), isA(Integer.class));
        verify(routeOfAdminRepo).getRouteOfAdminList();
        verify(v_DrugPrescriptionRepo).getItemListForFacility(isA(Integer.class));
        verify(counsellingProvidedMasterRepo).findByDeletedAndVisitCategoryIDOrderByName(isA(Boolean.class),
                isA(Integer.class));
        verify(nextDueVaccinesMasterRepo).findByDeletedOrderById(isA(Boolean.class));
        verify(nextImmunizationLocationMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        assertEquals(
                "{\"additionalServices\":[],\"routeOfAdmin\":[],\"itemMaster\":[],\"nextDueVaccines\":[],\"drugFrequencyMaster"
                        + "\":[],\"referralReasonList\":[],\"counsellingProvided\":[{}],\"NonEdlMaster\":[],\"counsellingTypes\":[],"
                        + "\"drugDoseMaster\":[],\"nextImmuLocations\":[],\"drugFormMaster\":[],\"drugDurationUnitMaster\":[],"
                        + "\"higherHealthCare\":[]}",
                actualCommonDoctorMasterDataForGenopdAncNcdcarePnc);
    }

    @Test
    void testGetCommonDoctorMasterDataForGenopdAncNcdcarePnc3() {
        // Arrange
        ArrayList<CounsellingProvidedMasterData> counsellingProvidedMasterDataList = new ArrayList<>();
        counsellingProvidedMasterDataList.add(new CounsellingProvidedMasterData());
        counsellingProvidedMasterDataList.add(new CounsellingProvidedMasterData());
        when(counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(counsellingProvidedMasterDataList);
        when(itemMasterRepo.searchEdl(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(nextDueVaccinesMasterRepo.findByDeletedOrderById(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextImmunizationLocationMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(referralReasonRepo.findByDeletedAndVisitCategoryIdOrderByName(Mockito.<Boolean>any(), Mockito.<Integer>any()))
                .thenReturn(new ArrayList<>());
        when(counsellingTypeRepo.getCounsellingTypes()).thenReturn(new ArrayList<>());
        when(drugDoseMasterRepo.getDrugDoseMaster()).thenReturn(new ArrayList<>());
        when(drugDurationUnitMasterRepo.getDrugDurationUnitMaster()).thenReturn(new ArrayList<>());
        when(drugFrequencyMasterRepo.getDrugFrequencyMaster()).thenReturn(new ArrayList<>());
        when(instituteRepo.getInstituteDetails(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(itemFormMasterRepo.getItemFormMaster()).thenReturn(new ArrayList<>());
        when(routeOfAdminRepo.getRouteOfAdminList()).thenReturn(new ArrayList<>());
        when(serviceMasterRepo.getAdditionalServices()).thenReturn(new ArrayList<>());
        when(v_DrugPrescriptionRepo.getItemListForFacility(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualCommonDoctorMasterDataForGenopdAncNcdcarePnc = aNCMasterDataServiceImpl
                .getCommonDoctorMasterDataForGenopdAncNcdcarePnc(1, 1, "Gender", 1, 1);

        // Assert
        verify(drugDoseMasterRepo).getDrugDoseMaster();
        verify(drugDurationUnitMasterRepo).getDrugDurationUnitMaster();
        verify(drugFrequencyMasterRepo).getDrugFrequencyMaster();
        verify(counsellingTypeRepo).getCounsellingTypes();
        verify(serviceMasterRepo).getAdditionalServices();
        verify(instituteRepo).getInstituteDetails(isA(Integer.class));
        verify(itemFormMasterRepo).getItemFormMaster();
        verify(itemMasterRepo).searchEdl(isA(Integer.class));
        verify(referralReasonRepo).findByDeletedAndVisitCategoryIdOrderByName(isA(Boolean.class), isA(Integer.class));
        verify(routeOfAdminRepo).getRouteOfAdminList();
        verify(v_DrugPrescriptionRepo).getItemListForFacility(isA(Integer.class));
        verify(counsellingProvidedMasterRepo).findByDeletedAndVisitCategoryIDOrderByName(isA(Boolean.class),
                isA(Integer.class));
        verify(nextDueVaccinesMasterRepo).findByDeletedOrderById(isA(Boolean.class));
        verify(nextImmunizationLocationMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        assertEquals(
                "{\"additionalServices\":[],\"routeOfAdmin\":[],\"itemMaster\":[],\"nextDueVaccines\":[],\"drugFrequencyMaster"
                        + "\":[],\"referralReasonList\":[],\"counsellingProvided\":[{},{}],\"NonEdlMaster\":[],\"counsellingTypes\":[],"
                        + "\"drugDoseMaster\":[],\"nextImmuLocations\":[],\"drugFormMaster\":[],\"drugDurationUnitMaster\":[],\"higherHealthCare"
                        + "\":[]}",
                actualCommonDoctorMasterDataForGenopdAncNcdcarePnc);
    }

    @Test
    void testGetCommonDoctorMasterDataForGenopdAncNcdcarePnc4() {
        // Arrange
        when(counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        Date createdDate = mock(Date.class);
        when(createdDate.getTime()).thenReturn(10L);
        Date createdDate2 = mock(Date.class);
        when(createdDate2.getTime()).thenReturn(10L);
        Date lastModDate = mock(Date.class);
        when(lastModDate.getTime()).thenReturn(10L);

        M_ItemCategory itemCategory = new M_ItemCategory();
        itemCategory.setAlertBeforeDays(7);
        itemCategory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        itemCategory.setCreatedDate(createdDate2);
        itemCategory.setDeleted(true);
        itemCategory.setIssueType("counsellingTypes");
        itemCategory.setItemCategoryCode("counsellingTypes");
        itemCategory.setItemCategoryDesc("counsellingTypes");
        itemCategory.setItemCategoryID(1);
        itemCategory.setItemCategoryName("counsellingTypes");
        itemCategory.setLastModDate(lastModDate);
        itemCategory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        itemCategory.setProcessed('\u0007');
        itemCategory.setProviderServiceMapID(1);
        itemCategory.setStatus("counsellingTypes");
        Date lastModDate2 = mock(Date.class);
        when(lastModDate2.getTime()).thenReturn(10L);
        Date createdDate3 = mock(Date.class);
        when(createdDate3.getTime()).thenReturn(10L);
        Date lastModDate3 = mock(Date.class);
        when(lastModDate3.getTime()).thenReturn(10L);

        M_Manufacturer manufacturer = new M_Manufacturer();
        manufacturer.setContactPerson("counsellingTypes");
        manufacturer.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        manufacturer.setCreatedDate(createdDate3);
        manufacturer.setDeleted(true);
        manufacturer.setLastModDate(lastModDate3);
        manufacturer.setManufacturerCode("counsellingTypes");
        manufacturer.setManufacturerDesc("counsellingTypes");
        manufacturer.setManufacturerID(1);
        manufacturer.setManufacturerName("counsellingTypes");
        manufacturer.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        manufacturer.setProviderServiceMapID(1);
        manufacturer.setStatus("counsellingTypes");
        manufacturer.setcST_GST_No("counsellingTypes");
        Date createdDate4 = mock(Date.class);
        when(createdDate4.getTime()).thenReturn(10L);
        Date lastModDate4 = mock(Date.class);
        when(lastModDate4.getTime()).thenReturn(10L);

        M_Pharmacologicalcategory pharmacologyCategory = new M_Pharmacologicalcategory();
        pharmacologyCategory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        pharmacologyCategory.setCreatedDate(createdDate4);
        pharmacologyCategory.setDeleted(true);
        pharmacologyCategory.setLastModDate(lastModDate4);
        pharmacologyCategory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        pharmacologyCategory.setPharmCategoryCode("counsellingTypes");
        pharmacologyCategory.setPharmCategoryDesc("counsellingTypes");
        pharmacologyCategory.setPharmCategoryName("counsellingTypes");
        pharmacologyCategory.setPharmacologyCategoryID(1);
        pharmacologyCategory.setProviderServiceMapID(1);
        pharmacologyCategory.setStatus("counsellingTypes");
        Date createdDate5 = mock(Date.class);
        when(createdDate5.getTime()).thenReturn(10L);
        Date lastModDate5 = mock(Date.class);
        when(lastModDate5.getTime()).thenReturn(10L);

        M_Uom uom = new M_Uom();
        uom.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        uom.setCreatedDate(createdDate5);
        uom.setDeleted(true);
        uom.setLastModDate(lastModDate5);
        uom.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        uom.setProcessed('\u0007');
        uom.setProviderServiceMapID(1);
        uom.setStatus("counsellingTypes");
        uom.setuOMCode("counsellingTypes");
        uom.setuOMDesc("counsellingTypes");
        uom.setuOMID(1);
        uom.setuOMName("counsellingTypes");

        ItemMaster itemMaster = new ItemMaster();
        itemMaster.setComposition("counsellingTypes");
        itemMaster.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        itemMaster.setCreatedDate(createdDate);
        itemMaster.setDeleted(true);
        itemMaster.setDiscontinued(true);
        itemMaster.setIsEDL(true);
        itemMaster.setIsMedical(true);
        itemMaster.setIsScheduledDrug(true);
        itemMaster.setItemCategory(itemCategory);
        itemMaster.setItemCategoryID(1);
        itemMaster.setItemCode("counsellingTypes");
        itemMaster.setItemDesc("counsellingTypes");
        itemMaster.setItemForm(new M_ItemForm());
        itemMaster.setItemFormID(1);
        itemMaster.setItemID(1);
        itemMaster.setItemName("counsellingTypes");
        itemMaster.setLastModDate(lastModDate2);
        itemMaster.setManufacturer(manufacturer);
        itemMaster.setManufacturerID(1);
        itemMaster.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        itemMaster.setPharmacologyCategory(pharmacologyCategory);
        itemMaster.setPharmacologyCategoryID(1);
        itemMaster.setProcessed('\u0007');
        itemMaster.setProviderServiceMapID(1);
        itemMaster.setQuantity(7);
        itemMaster.setRoute(new M_Route());
        itemMaster.setRouteID(1);
        itemMaster.setSctCode("counsellingTypes");
        itemMaster.setSctTerm("counsellingTypes");
        itemMaster.setStatus("counsellingTypes");
        itemMaster.setStrength("counsellingTypes");
        itemMaster.setUnitOfMeasurement("counsellingTypes");
        itemMaster.setUom(uom);
        itemMaster.setUomID(1);

        ArrayList<ItemMaster> itemMasterList = new ArrayList<>();
        itemMasterList.add(itemMaster);
        when(itemMasterRepo.searchEdl(Mockito.<Integer>any())).thenReturn(itemMasterList);
        when(nextDueVaccinesMasterRepo.findByDeletedOrderById(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextImmunizationLocationMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(referralReasonRepo.findByDeletedAndVisitCategoryIdOrderByName(Mockito.<Boolean>any(), Mockito.<Integer>any()))
                .thenReturn(new ArrayList<>());
        when(counsellingTypeRepo.getCounsellingTypes()).thenReturn(new ArrayList<>());
        when(drugDoseMasterRepo.getDrugDoseMaster()).thenReturn(new ArrayList<>());
        when(drugDurationUnitMasterRepo.getDrugDurationUnitMaster()).thenReturn(new ArrayList<>());
        when(drugFrequencyMasterRepo.getDrugFrequencyMaster()).thenReturn(new ArrayList<>());
        when(instituteRepo.getInstituteDetails(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(itemFormMasterRepo.getItemFormMaster()).thenReturn(new ArrayList<>());
        when(routeOfAdminRepo.getRouteOfAdminList()).thenReturn(new ArrayList<>());
        when(serviceMasterRepo.getAdditionalServices()).thenReturn(new ArrayList<>());
        when(v_DrugPrescriptionRepo.getItemListForFacility(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        aNCMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(1, 1, "Gender", 1, 1);

        // Assert
        verify(drugDoseMasterRepo).getDrugDoseMaster();
        verify(drugDurationUnitMasterRepo).getDrugDurationUnitMaster();
        verify(drugFrequencyMasterRepo).getDrugFrequencyMaster();
        verify(counsellingTypeRepo).getCounsellingTypes();
        verify(serviceMasterRepo).getAdditionalServices();
        verify(instituteRepo).getInstituteDetails(isA(Integer.class));
        verify(itemFormMasterRepo).getItemFormMaster();
        verify(itemMasterRepo).searchEdl(isA(Integer.class));
        verify(referralReasonRepo).findByDeletedAndVisitCategoryIdOrderByName(isA(Boolean.class), isA(Integer.class));
        verify(routeOfAdminRepo).getRouteOfAdminList();
        verify(v_DrugPrescriptionRepo).getItemListForFacility(isA(Integer.class));
        verify(counsellingProvidedMasterRepo).findByDeletedAndVisitCategoryIDOrderByName(isA(Boolean.class),
                isA(Integer.class));
        verify(nextDueVaccinesMasterRepo).findByDeletedOrderById(isA(Boolean.class));
        verify(nextImmunizationLocationMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        verify(createdDate).getTime();
        verify(createdDate2).getTime();
        verify(lastModDate).getTime();
        verify(lastModDate2).getTime();
        verify(createdDate3).getTime();
        verify(lastModDate3).getTime();
        verify(createdDate4).getTime();
        verify(lastModDate4).getTime();
        verify(createdDate5).getTime();
        verify(lastModDate5).getTime();
    }

    @Test
    void testGetCommonDoctorMasterDataForGenopdAncNcdcarePnc5() {
        // Arrange
        when(counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(Mockito.<Boolean>any(),
                Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        M_ItemCategory itemCategory = new M_ItemCategory();
        itemCategory.setAlertBeforeDays(7);
        itemCategory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        itemCategory.setCreatedDate(mock(Date.class));
        itemCategory.setDeleted(true);
        itemCategory.setIssueType("counsellingTypes");
        itemCategory.setItemCategoryCode("counsellingTypes");
        itemCategory.setItemCategoryDesc("counsellingTypes");
        itemCategory.setItemCategoryID(1);
        itemCategory.setItemCategoryName("counsellingTypes");
        itemCategory.setLastModDate(mock(Date.class));
        itemCategory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        itemCategory.setProcessed('\u0007');
        itemCategory.setProviderServiceMapID(1);
        itemCategory.setStatus("counsellingTypes");

        M_Manufacturer manufacturer = new M_Manufacturer();
        manufacturer.setContactPerson("counsellingTypes");
        manufacturer.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        manufacturer.setCreatedDate(mock(Date.class));
        manufacturer.setDeleted(true);
        manufacturer.setLastModDate(mock(Date.class));
        manufacturer.setManufacturerCode("counsellingTypes");
        manufacturer.setManufacturerDesc("counsellingTypes");
        manufacturer.setManufacturerID(1);
        manufacturer.setManufacturerName("counsellingTypes");
        manufacturer.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        manufacturer.setProviderServiceMapID(1);
        manufacturer.setStatus("counsellingTypes");
        manufacturer.setcST_GST_No("counsellingTypes");

        M_Pharmacologicalcategory pharmacologyCategory = new M_Pharmacologicalcategory();
        pharmacologyCategory.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        pharmacologyCategory.setCreatedDate(mock(Date.class));
        pharmacologyCategory.setDeleted(true);
        pharmacologyCategory.setLastModDate(mock(Date.class));
        pharmacologyCategory.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        pharmacologyCategory.setPharmCategoryCode("counsellingTypes");
        pharmacologyCategory.setPharmCategoryDesc("counsellingTypes");
        pharmacologyCategory.setPharmCategoryName("counsellingTypes");
        pharmacologyCategory.setPharmacologyCategoryID(1);
        pharmacologyCategory.setProviderServiceMapID(1);
        pharmacologyCategory.setStatus("counsellingTypes");

        M_Uom uom = new M_Uom();
        uom.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        uom.setCreatedDate(mock(Date.class));
        uom.setDeleted(true);
        uom.setLastModDate(mock(Date.class));
        uom.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        uom.setProcessed('\u0007');
        uom.setProviderServiceMapID(1);
        uom.setStatus("counsellingTypes");
        uom.setuOMCode("counsellingTypes");
        uom.setuOMDesc("counsellingTypes");
        uom.setuOMID(1);
        uom.setuOMName("counsellingTypes");
        M_Uom m_Uom = mock(M_Uom.class);
        when(m_Uom.getuOMName()).thenReturn("Getu OMName");
        doNothing().when(m_Uom).setCreatedBy(Mockito.<String>any());
        doNothing().when(m_Uom).setCreatedDate(Mockito.<Date>any());
        doNothing().when(m_Uom).setDeleted(Mockito.<Boolean>any());
        doNothing().when(m_Uom).setLastModDate(Mockito.<Date>any());
        doNothing().when(m_Uom).setModifiedBy(Mockito.<String>any());
        doNothing().when(m_Uom).setProcessed(Mockito.<Character>any());
        doNothing().when(m_Uom).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(m_Uom).setStatus(Mockito.<String>any());
        doNothing().when(m_Uom).setuOMCode(Mockito.<String>any());
        doNothing().when(m_Uom).setuOMDesc(Mockito.<String>any());
        doNothing().when(m_Uom).setuOMID(Mockito.<Integer>any());
        doNothing().when(m_Uom).setuOMName(Mockito.<String>any());
        m_Uom.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        m_Uom.setCreatedDate(mock(Date.class));
        m_Uom.setDeleted(true);
        m_Uom.setLastModDate(mock(Date.class));
        m_Uom.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        m_Uom.setProcessed('A');
        m_Uom.setProviderServiceMapID(1);
        m_Uom.setStatus("Status");
        m_Uom.setuOMCode("U OMCode");
        m_Uom.setuOMDesc("U OMDesc");
        m_Uom.setuOMID(1);
        m_Uom.setuOMName("U OMName");
        ItemMaster itemMaster = mock(ItemMaster.class);
        when(itemMaster.getUom()).thenReturn(m_Uom);
        doNothing().when(itemMaster).setComposition(Mockito.<String>any());
        doNothing().when(itemMaster).setCreatedBy(Mockito.<String>any());
        doNothing().when(itemMaster).setCreatedDate(Mockito.<Date>any());
        doNothing().when(itemMaster).setDeleted(Mockito.<Boolean>any());
        doNothing().when(itemMaster).setDiscontinued(Mockito.<Boolean>any());
        doNothing().when(itemMaster).setIsEDL(Mockito.<Boolean>any());
        doNothing().when(itemMaster).setIsMedical(Mockito.<Boolean>any());
        doNothing().when(itemMaster).setIsScheduledDrug(Mockito.<Boolean>any());
        doNothing().when(itemMaster).setItemCategory(Mockito.<M_ItemCategory>any());
        doNothing().when(itemMaster).setItemCategoryID(Mockito.<Integer>any());
        doNothing().when(itemMaster).setItemCode(Mockito.<String>any());
        doNothing().when(itemMaster).setItemDesc(Mockito.<String>any());
        doNothing().when(itemMaster).setItemForm(Mockito.<M_ItemForm>any());
        doNothing().when(itemMaster).setItemFormID(Mockito.<Integer>any());
        doNothing().when(itemMaster).setItemID(Mockito.<Integer>any());
        doNothing().when(itemMaster).setItemName(Mockito.<String>any());
        doNothing().when(itemMaster).setLastModDate(Mockito.<Date>any());
        doNothing().when(itemMaster).setManufacturer(Mockito.<M_Manufacturer>any());
        doNothing().when(itemMaster).setManufacturerID(Mockito.<Integer>any());
        doNothing().when(itemMaster).setModifiedBy(Mockito.<String>any());
        doNothing().when(itemMaster).setPharmacologyCategory(Mockito.<M_Pharmacologicalcategory>any());
        doNothing().when(itemMaster).setPharmacologyCategoryID(Mockito.<Integer>any());
        doNothing().when(itemMaster).setProcessed(Mockito.<Character>any());
        doNothing().when(itemMaster).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(itemMaster).setQuantity(Mockito.<Integer>any());
        doNothing().when(itemMaster).setRoute(Mockito.<M_Route>any());
        doNothing().when(itemMaster).setRouteID(Mockito.<Integer>any());
        doNothing().when(itemMaster).setSctCode(Mockito.<String>any());
        doNothing().when(itemMaster).setSctTerm(Mockito.<String>any());
        doNothing().when(itemMaster).setStatus(Mockito.<String>any());
        doNothing().when(itemMaster).setStrength(Mockito.<String>any());
        doNothing().when(itemMaster).setUnitOfMeasurement(Mockito.<String>any());
        doNothing().when(itemMaster).setUom(Mockito.<M_Uom>any());
        doNothing().when(itemMaster).setUomID(Mockito.<Integer>any());
        itemMaster.setComposition("counsellingTypes");
        itemMaster.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        itemMaster.setCreatedDate(mock(Date.class));
        itemMaster.setDeleted(true);
        itemMaster.setDiscontinued(true);
        itemMaster.setIsEDL(true);
        itemMaster.setIsMedical(true);
        itemMaster.setIsScheduledDrug(true);
        itemMaster.setItemCategory(itemCategory);
        itemMaster.setItemCategoryID(1);
        itemMaster.setItemCode("counsellingTypes");
        itemMaster.setItemDesc("counsellingTypes");
        itemMaster.setItemForm(new M_ItemForm());
        itemMaster.setItemFormID(1);
        itemMaster.setItemID(1);
        itemMaster.setItemName("counsellingTypes");
        itemMaster.setLastModDate(mock(Date.class));
        itemMaster.setManufacturer(manufacturer);
        itemMaster.setManufacturerID(1);
        itemMaster.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        itemMaster.setPharmacologyCategory(pharmacologyCategory);
        itemMaster.setPharmacologyCategoryID(1);
        itemMaster.setProcessed('\u0007');
        itemMaster.setProviderServiceMapID(1);
        itemMaster.setQuantity(7);
        itemMaster.setRoute(new M_Route());
        itemMaster.setRouteID(1);
        itemMaster.setSctCode("counsellingTypes");
        itemMaster.setSctTerm("counsellingTypes");
        itemMaster.setStatus("counsellingTypes");
        itemMaster.setStrength("counsellingTypes");
        itemMaster.setUnitOfMeasurement("counsellingTypes");
        itemMaster.setUom(uom);
        itemMaster.setUomID(1);

        ArrayList<ItemMaster> itemMasterList = new ArrayList<>();
        itemMasterList.add(itemMaster);
        when(itemMasterRepo.searchEdl(Mockito.<Integer>any())).thenReturn(itemMasterList);
        when(nextDueVaccinesMasterRepo.findByDeletedOrderById(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        when(nextImmunizationLocationMasterRepo.findByDeletedOrderByName(Mockito.<Boolean>any()))
                .thenReturn(new ArrayList<>());
        when(referralReasonRepo.findByDeletedAndVisitCategoryIdOrderByName(Mockito.<Boolean>any(), Mockito.<Integer>any()))
                .thenReturn(new ArrayList<>());
        when(counsellingTypeRepo.getCounsellingTypes()).thenReturn(new ArrayList<>());
        when(drugDoseMasterRepo.getDrugDoseMaster()).thenReturn(new ArrayList<>());
        when(drugDurationUnitMasterRepo.getDrugDurationUnitMaster()).thenReturn(new ArrayList<>());
        when(drugFrequencyMasterRepo.getDrugFrequencyMaster()).thenReturn(new ArrayList<>());
        when(instituteRepo.getInstituteDetails(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(itemFormMasterRepo.getItemFormMaster()).thenReturn(new ArrayList<>());
        when(routeOfAdminRepo.getRouteOfAdminList()).thenReturn(new ArrayList<>());
        when(serviceMasterRepo.getAdditionalServices()).thenReturn(new ArrayList<>());
        when(v_DrugPrescriptionRepo.getItemListForFacility(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualCommonDoctorMasterDataForGenopdAncNcdcarePnc = aNCMasterDataServiceImpl
                .getCommonDoctorMasterDataForGenopdAncNcdcarePnc(1, 1, "Gender", 1, 1);

        // Assert
        verify(itemMaster, atLeast(1)).getUom();
        verify(itemMaster).setComposition(eq("counsellingTypes"));
        verify(itemMaster).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(itemMaster).setCreatedDate(isA(Date.class));
        verify(itemMaster).setDeleted(isA(Boolean.class));
        verify(itemMaster).setDiscontinued(isA(Boolean.class));
        verify(itemMaster).setIsEDL(isA(Boolean.class));
        verify(itemMaster).setIsMedical(isA(Boolean.class));
        verify(itemMaster).setIsScheduledDrug(isA(Boolean.class));
        verify(itemMaster).setItemCategory(isA(M_ItemCategory.class));
        verify(itemMaster).setItemCategoryID(isA(Integer.class));
        verify(itemMaster).setItemCode(eq("counsellingTypes"));
        verify(itemMaster).setItemDesc(eq("counsellingTypes"));
        verify(itemMaster).setItemForm(isA(M_ItemForm.class));
        verify(itemMaster).setItemFormID(isA(Integer.class));
        verify(itemMaster).setItemID(isA(Integer.class));
        verify(itemMaster).setItemName(eq("counsellingTypes"));
        verify(itemMaster).setLastModDate(isA(Date.class));
        verify(itemMaster).setManufacturer(isA(M_Manufacturer.class));
        verify(itemMaster).setManufacturerID(isA(Integer.class));
        verify(itemMaster).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(itemMaster).setPharmacologyCategory(isA(M_Pharmacologicalcategory.class));
        verify(itemMaster).setPharmacologyCategoryID(isA(Integer.class));
        verify(itemMaster).setProcessed(isA(Character.class));
        verify(itemMaster).setProviderServiceMapID(isA(Integer.class));
        verify(itemMaster).setQuantity(isA(Integer.class));
        verify(itemMaster).setRoute(isA(M_Route.class));
        verify(itemMaster).setRouteID(isA(Integer.class));
        verify(itemMaster).setSctCode(eq("counsellingTypes"));
        verify(itemMaster).setSctTerm(eq("counsellingTypes"));
        verify(itemMaster).setStatus(eq("counsellingTypes"));
        verify(itemMaster).setStrength(eq("counsellingTypes"));
        verify(itemMaster, atLeast(1)).setUnitOfMeasurement(Mockito.<String>any());
        verify(itemMaster).setUom(isA(M_Uom.class));
        verify(itemMaster).setUomID(isA(Integer.class));
        verify(m_Uom).getuOMName();
        verify(m_Uom).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(m_Uom).setCreatedDate(isA(Date.class));
        verify(m_Uom).setDeleted(isA(Boolean.class));
        verify(m_Uom).setLastModDate(isA(Date.class));
        verify(m_Uom).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(m_Uom).setProcessed(isA(Character.class));
        verify(m_Uom).setProviderServiceMapID(isA(Integer.class));
        verify(m_Uom).setStatus(eq("Status"));
        verify(m_Uom).setuOMCode(eq("U OMCode"));
        verify(m_Uom).setuOMDesc(eq("U OMDesc"));
        verify(m_Uom).setuOMID(isA(Integer.class));
        verify(m_Uom).setuOMName(eq("U OMName"));
        verify(drugDoseMasterRepo).getDrugDoseMaster();
        verify(drugDurationUnitMasterRepo).getDrugDurationUnitMaster();
        verify(drugFrequencyMasterRepo).getDrugFrequencyMaster();
        verify(counsellingTypeRepo).getCounsellingTypes();
        verify(serviceMasterRepo).getAdditionalServices();
        verify(instituteRepo).getInstituteDetails(isA(Integer.class));
        verify(itemFormMasterRepo).getItemFormMaster();
        verify(itemMasterRepo).searchEdl(isA(Integer.class));
        verify(referralReasonRepo).findByDeletedAndVisitCategoryIdOrderByName(isA(Boolean.class), isA(Integer.class));
        verify(routeOfAdminRepo).getRouteOfAdminList();
        verify(v_DrugPrescriptionRepo).getItemListForFacility(isA(Integer.class));
        verify(counsellingProvidedMasterRepo).findByDeletedAndVisitCategoryIDOrderByName(isA(Boolean.class),
                isA(Integer.class));
        verify(nextDueVaccinesMasterRepo).findByDeletedOrderById(isA(Boolean.class));
        verify(nextImmunizationLocationMasterRepo).findByDeletedOrderByName(isA(Boolean.class));
        assertEquals(
                "{\"additionalServices\":[],\"routeOfAdmin\":[],\"itemMaster\":[],\"nextDueVaccines\":[],\"drugFrequencyMaster"
                        + "\":[],\"referralReasonList\":[],\"counsellingProvided\":[],\"NonEdlMaster\":[{}],\"counsellingTypes\":[],"
                        + "\"drugDoseMaster\":[],\"nextImmuLocations\":[],\"drugFormMaster\":[],\"drugDurationUnitMaster\":[],"
                        + "\"higherHealthCare\":[]}",
                actualCommonDoctorMasterDataForGenopdAncNcdcarePnc);
    }

    @Test
    void testGettersAndSetters() {
        ANCMasterDataServiceImpl ancMasterDataServiceImpl = new ANCMasterDataServiceImpl();

        // Act
        ancMasterDataServiceImpl.setAllergicReactionTypesRepo(mock(AllergicReactionTypesRepo.class));
        ancMasterDataServiceImpl.setBloodGroupsRepo(mock(BloodGroupsRepo.class));
        ancMasterDataServiceImpl.setChiefComplaintMasterRepo(mock(ChiefComplaintMasterRepo.class));
        ancMasterDataServiceImpl.setChildVaccinationsRepo(mock(ChildVaccinationsRepo.class));
        ancMasterDataServiceImpl.setComorbidConditionRepo(mock(ComorbidConditionRepo.class));
        ancMasterDataServiceImpl.setCompFeedsRepo(mock(CompFeedsRepo.class));
        ancMasterDataServiceImpl.setComplicationTypesRepo(mock(ComplicationTypesRepo.class));
        ancMasterDataServiceImpl.setCounsellingTypeRepo(mock(CounsellingTypeRepo.class));
        ancMasterDataServiceImpl.setDeliveryPlaceRepo(mock(DeliveryPlaceRepo.class));
        ancMasterDataServiceImpl.setDeliveryTypeRepo(mock(DeliveryTypeRepo.class));
        ancMasterDataServiceImpl.setDevelopmentProblemsRepo(mock(DevelopmentProblemsRepo.class));
        ancMasterDataServiceImpl.setDiseaseTypeRepo(mock(DiseaseTypeRepo.class));
        ancMasterDataServiceImpl.setDrugDoseMasterRepo(mock(DrugDoseMasterRepo.class));
        ancMasterDataServiceImpl.setDrugDurationUnitMasterRepo(mock(DrugDurationUnitMasterRepo.class));
        ancMasterDataServiceImpl.setDrugFrequencyMasterRepo(mock(DrugFrequencyMasterRepo.class));
        ancMasterDataServiceImpl.setFamilyMemberMasterRepo(mock(FamilyMemberMasterRepo.class));
        ancMasterDataServiceImpl.setFundalHeightRepo(mock(FundalHeightRepo.class));
        ancMasterDataServiceImpl.setGestationRepo(mock(GestationRepo.class));
        ancMasterDataServiceImpl.setGrossMotorMilestoneRepo(mock(GrossMotorMilestoneRepo.class));
        ancMasterDataServiceImpl.setIllnessTypesRepo(mock(IllnessTypesRepo.class));
        ancMasterDataServiceImpl.setInstituteRepo(mock(InstituteRepo.class));
        ancMasterDataServiceImpl.setItemFormMasterRepo(mock(ItemFormMasterRepo.class));
        ancMasterDataServiceImpl.setJointTypesRepo(mock(JointTypesRepo.class));
        ancMasterDataServiceImpl.setMenstrualCycleRangeRepo(mock(MenstrualCycleRangeRepo.class));
        ancMasterDataServiceImpl.setMenstrualCycleStatusRepo(mock(MenstrualCycleStatusRepo.class));
        ancMasterDataServiceImpl.setMenstrualProblemRepo(mock(MenstrualProblemRepo.class));
        ancMasterDataServiceImpl.setMusculoskeletalRepo(mock(MusculoskeletalRepo.class));
        ancMasterDataServiceImpl.setNcdCareTypeRepo(mock(NCDCareTypeRepo.class));
        ancMasterDataServiceImpl.setNcdScreeningMasterServiceImpl(new NCDScreeningMasterServiceImpl());
        ancMasterDataServiceImpl.setNewbornHealthStatusRepo(mock(NewbornHealthStatusRepo.class));
        ancMasterDataServiceImpl.setOptionalVaccinationsRepo(mock(OptionalVaccinationsRepo.class));
        ancMasterDataServiceImpl.setPersonalHabitTypeRepo(mock(PersonalHabitTypeRepo.class));
        ancMasterDataServiceImpl.setPregDurationRepo(mock(PregDurationRepo.class));
        ancMasterDataServiceImpl.setPregOutcomeRepo(mock(PregOutcomeRepo.class));
        ancMasterDataServiceImpl.setProcedureRepo(mock(ProcedureRepo.class));
        ancMasterDataServiceImpl.setRouteOfAdminRepo(mock(RouteOfAdminRepo.class));
        ancMasterDataServiceImpl.setServiceMasterRepo(mock(ServiceMasterRepo.class));
        ancMasterDataServiceImpl.setSurgeryTypesRepo(mock(SurgeryTypesRepo.class));
        ancMasterDataServiceImpl.setV_DrugPrescriptionRepo(mock(V_DrugPrescriptionRepo.class));
    }
}
