package com.iemr.hwc.service.common.master;


	
	import static org.junit.jupiter.api.Assertions.*;
	import static org.mockito.Mockito.*;

	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Map;

	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.junit.jupiter.api.extension.ExtendWith;
	import org.mockito.InjectMocks;
	import org.mockito.Mock;
	import org.mockito.junit.jupiter.MockitoExtension;

	import com.google.gson.Gson;
	import com.google.gson.GsonBuilder;
import com.iemr.hwc.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.hwc.repo.doctor.DrugDoseMasterRepo;
import com.iemr.hwc.repo.doctor.DrugDurationUnitMasterRepo;
import com.iemr.hwc.repo.doctor.DrugFrequencyMasterRepo;
import com.iemr.hwc.repo.foetalmonitor.FoetalMonitorTestsRepo;
import com.iemr.hwc.repo.labModule.ProcedureRepo;
import com.iemr.hwc.repo.login.MasterVanRepo;
import com.iemr.hwc.repo.masterrepo.anc.*;
import com.iemr.hwc.repo.masterrepo.covid19.CovidContactHistoryMasterRepo;
import com.iemr.hwc.repo.masterrepo.covid19.CovidRecommnedationMasterRepo;
import com.iemr.hwc.repo.masterrepo.covid19.CovidSymptomsMasterRepo;
import com.iemr.hwc.repo.masterrepo.doctor.*;
	import com.iemr.hwc.repo.masterrepo.generalopd.FoodIntoleranceStatusRepo;
	import com.iemr.hwc.repo.masterrepo.generalopd.SubVisitCategoryRepo;
	import com.iemr.hwc.repo.masterrepo.ncdCare.NCDCareTypeRepo;
	import com.iemr.hwc.repo.masterrepo.ncdscreening.NCDScreeningConditionRepo;
	import com.iemr.hwc.repo.masterrepo.nurse.FamilyMemberMasterRepo;
import com.iemr.hwc.repo.masterrepo.pnc.DeliveryConductedByMasterRepo;
import com.iemr.hwc.repo.masterrepo.pnc.NewbornHealthStatusRepo;
	import com.iemr.hwc.repo.neonatal.CounsellingProvidedMasterRepo;
	import com.iemr.hwc.repo.neonatal.NextDueVaccinesMasterRepo;
	import com.iemr.hwc.repo.neonatal.NextImmunizationLocationMasterRepo;
	
	
	public class TestANCMasterDataServiceImpl {

	@ExtendWith(MockitoExtension.class)
	
	    @Mock
	    private AllergicReactionTypesRepo allergicReactionTypesRepo;
	    @Mock
	    private BloodGroupsRepo bloodGroupsRepo;
	    @Mock
	    private ChildVaccinationsRepo childVaccinationsRepo;
	    @Mock
	    private DeliveryPlaceRepo deliveryPlaceRepo;
	    @Mock
	    private DeliveryTypeRepo deliveryTypeRepo;
	    @Mock
	    private DevelopmentProblemsRepo developmentProblemsRepo;
	    @Mock
	    private GestationRepo gestationRepo;
	    @Mock
	    private IllnessTypesRepo illnessTypesRepo;
	    @Mock
	    private JointTypesRepo jointTypesRepo;
	    @Mock
	    private MenstrualCycleRangeRepo menstrualCycleRangeRepo;
	    @Mock
	    private MenstrualCycleStatusRepo menstrualCycleStatusRepo;
	    @Mock
	    private MenstrualProblemRepo menstrualProblemRepo;
	    @Mock
	    private MusculoskeletalRepo musculoskeletalRepo;
	    @Mock
	    private PregDurationRepo pregDurationRepo;
	    @Mock
	    private SurgeryTypesRepo surgeryTypesRepo;
	    @Mock
	    private ComorbidConditionRepo comorbidConditionRepo;
	    @Mock
	    private CompFeedsRepo compFeedsRepo;
	    @Mock
	    private FundalHeightRepo fundalHeightRepo;
	    @Mock
	    private GrossMotorMilestoneRepo grossMotorMilestoneRepo;
	    @Mock
	    private PersonalHabitTypeRepo personalHabitTypeRepo;
	    @Mock
	    private PregOutcomeRepo pregOutcomeRepo;
	    @Mock
	    private DiseaseTypeRepo diseaseTypeRepo;
	    @Mock
	    private ComplicationTypesRepo complicationTypesRepo;
	  	@Mock
	    private FamilyMemberMasterRepo familyMemberMasterRepo;
	    
	    @Mock
	    private NewbornHealthStatusRepo newbornHealthStatusRepo;
	   
	    @Mock
	    private OptionalVaccinationsRepo optionalVaccinationsRepo;
	   
	    @Mock
	    private CovidSymptomsMasterRepo covidSymptomsMasterRepo;
	    @Mock
	    private CovidContactHistoryMasterRepo covidContactHistoryMasterRepo;
	    @Mock
	    private CovidRecommnedationMasterRepo covidRecommnedationMasterRepo;
	    
	    @Mock
	    private FoetalMonitorTestsRepo foetalMonitorTestRepo;
	    
	    @Mock
	    private ServiceFacilityMasterRepo serviceFacilityMasterRepo;
	    @Mock
	    private DeliveryConductedByMasterRepo deliveryConductedByMasterRepo;
	    @Mock
	    private FoodIntoleranceStatusRepo foodIntoleranceStatusRepo;
	    @Mock
	    private SubVisitCategoryRepo subVisitCategoryRepo;
	   
	    @Mock
	    private CounsellingTypeRepo counsellingTypeRepo;
	    @Mock
	    private ServiceMasterRepo serviceMasterRepo;
	    @Mock
	    private InstituteRepo instituteRepo;
	    @Mock
	    private ProcedureRepo procedureRepo;
	    @Mock
	    private ChiefComplaintMasterRepo chiefComplaintMasterRepo;
	    @Mock
	    private ItemFormMasterRepo itemFormMasterRepo;
	    @Mock
	    private DrugDoseMasterRepo drugDoseMasterRepo;
	    @Mock
	    private DrugDurationUnitMasterRepo drugDurationUnitMasterRepo;
	    @Mock
	    private DrugFrequencyMasterRepo drugFrequencyMasterRepo;
	    @Mock
	    private RouteOfAdminRepo routeOfAdminRepo;
	    @Mock
	    private ItemMasterRepo itemMasterRepo;
	    @Mock
	    private MasterVanRepo masterVanRepo;
	    @Mock
	    private V_DrugPrescriptionRepo v_DrugPrescriptionRepo;
	    @Mock
	    private NCDScreeningMasterServiceImpl ncdScreeningMasterServiceImpl;
	    @Mock
	    private NCDCareTypeRepo ncdCareTypeRepo;
	    @Mock
	    private ReferralReasonRepo referralReasonRepo;
	    @Mock
	    private CounsellingProvidedMasterRepo counsellingProvidedMasterRepo;
	    @Mock
	    private NextDueVaccinesMasterRepo nextDueVaccinesMasterRepo;
	    @Mock
	    private NextImmunizationLocationMasterRepo nextImmunizationLocationMasterRepo;

	    @InjectMocks
	    private ANCMasterDataServiceImpl ancMasterDataServiceImpl;

	    private Gson gson;

	    @BeforeEach
	    public void setUp() {
	        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	    }

	    @Test
	    public void testGetCommonNurseMasterDataForGenopdAncNcdcarePnc() {
	        // Mocking repository methods
	        when(bloodGroupsRepo.getBloodGroups()).thenReturn(new ArrayList<>());
	        when(childVaccinationsRepo.getChildVaccinations()).thenReturn(new ArrayList<>());
	        when(deliveryPlaceRepo.getDeliveryPlaces()).thenReturn(new ArrayList<>());
	        when(deliveryTypeRepo.getDeliveryTypes()).thenReturn(new ArrayList<>());
	        when(developmentProblemsRepo.getDevelopmentProblems()).thenReturn(new ArrayList<>());
	        when(gestationRepo.getGestationTypes()).thenReturn(new ArrayList<>());
	        when(illnessTypesRepo.getIllnessTypes(anyInt())).thenReturn(new ArrayList<>());
	        when(jointTypesRepo.getJointTypes()).thenReturn(new ArrayList<>());
	        when(menstrualCycleRangeRepo.getMenstrualCycleRanges(anyString())).thenReturn(new ArrayList<>());
	        when(menstrualCycleStatusRepo.getMenstrualCycleStatuses(anyInt())).thenReturn(new ArrayList<>());
	        when(menstrualProblemRepo.getMenstrualProblems()).thenReturn(new ArrayList<>());
	        when(musculoskeletalRepo.getMusculoskeletalvalues(anyString())).thenReturn(new ArrayList<>());
	        when(pregDurationRepo.getPregDurationTypes()).thenReturn(new ArrayList<>());
	        when(surgeryTypesRepo.getSurgeryTypes(anyInt(), anyString())).thenReturn(new ArrayList<>());
	        when(comorbidConditionRepo.getComorbidConditions(anyInt())).thenReturn(new ArrayList<>());
	        when(grossMotorMilestoneRepo.getGrossMotorMilestones()).thenReturn(new ArrayList<>());
	        when(fundalHeightRepo.getFundalHeights()).thenReturn(new ArrayList<>());
	        when(compFeedsRepo.getCompFeeds(anyString())).thenReturn(new ArrayList<>());
	        when(pregOutcomeRepo.getPregOutcomes()).thenReturn(new ArrayList<>());
	        when(complicationTypesRepo.getComplicationTypes(anyString())).thenReturn(new ArrayList<>());
	        when(serviceFacilityMasterRepo.findByDeleted(false)).thenReturn(new ArrayList<>());
	        when(diseaseTypeRepo.getDiseaseTypes()).thenReturn(new ArrayList<>());
	        when(personalHabitTypeRepo.getPersonalHabitTypeMaster(anyString())).thenReturn(new ArrayList<>());
	        when(familyMemberMasterRepo.getFamilyMemberTypeMaster()).thenReturn(new ArrayList<>());
	        when(procedureRepo.getProcedureMasterData(anyInt(), anyString())).thenReturn(new ArrayList<>());
	        when(newbornHealthStatusRepo.getnewBornHealthStatuses()).thenReturn(new ArrayList<>());
	        when(optionalVaccinationsRepo.getOptionalVaccinations()).thenReturn(new ArrayList<>());
	        when(deliveryConductedByMasterRepo.findByDeleted(false)).thenReturn(new ArrayList<>());
	        when(foodIntoleranceStatusRepo.findByDeletedOrderByIntoleranceStatus(false)).thenReturn(new ArrayList<>());
	        when(covidSymptomsMasterRepo.findByDeleted(false)).thenReturn(new ArrayList<>());
	        when(covidContactHistoryMasterRepo.findByDeleted(false)).thenReturn(new ArrayList<>());
	        when(covidRecommnedationMasterRepo.findByDeleted(false)).thenReturn(new ArrayList<>());
	        when(foetalMonitorTestRepo.getFoetalMonitorTestsDetails(anyInt())).thenReturn(new ArrayList<>());
	        when(subVisitCategoryRepo.findByDeletedOrderByName(false)).thenReturn(new ArrayList<>());

	        // Call the method
	        String result = ancMasterDataServiceImpl.getCommonNurseMasterDataForGenopdAncNcdcarePnc(1, 1, "M");

	        // Verify interactions and assert results
	        verify(bloodGroupsRepo, times(1)).getBloodGroups();
	        verify(childVaccinationsRepo, times(1)).getChildVaccinations();
	        verify(deliveryPlaceRepo, times(1)).getDeliveryPlaces();
	        verify(deliveryTypeRepo, times(1)).getDeliveryTypes();
	        verify(developmentProblemsRepo, times(1)).getDevelopmentProblems();
	        verify(gestationRepo, times(1)).getGestationTypes();
	        verify(illnessTypesRepo, times(1)).getIllnessTypes(anyInt());
	        verify(jointTypesRepo, times(1)).getJointTypes();
	        verify(menstrualCycleRangeRepo, times(1)).getMenstrualCycleRanges(anyString());
	        verify(menstrualCycleStatusRepo, times(1)).getMenstrualCycleStatuses(anyInt());
	        verify(menstrualProblemRepo, times(1)).getMenstrualProblems();
	        verify(musculoskeletalRepo, times(1)).getMusculoskeletalvalues(anyString());
	        verify(pregDurationRepo, times(1)).getPregDurationTypes();
	        verify(surgeryTypesRepo, times(1)).getSurgeryTypes(anyInt(), anyString());
	        verify(comorbidConditionRepo, times(1)).getComorbidConditions(anyInt());
	        verify(grossMotorMilestoneRepo, times(1)).getGrossMotorMilestones();
	        verify(fundalHeightRepo, times(1)).getFundalHeights();
	        verify(compFeedsRepo, times(1)).getCompFeeds(anyString());
	        verify(pregOutcomeRepo, times(1)).getPregOutcomes();
	        verify(complicationTypesRepo, times(1)).getComplicationTypes(anyString());
	        verify(serviceFacilityMasterRepo, times(1)).findByDeleted(false);
	        verify(diseaseTypeRepo, times(1)).getDiseaseTypes();
	        verify(personalHabitTypeRepo, times(1)).getPersonalHabitTypeMaster(anyString());
	        verify(familyMemberMasterRepo, times(1)).getFamilyMemberTypeMaster();
	        verify(procedureRepo, times(1)).getProcedureMasterData(anyInt(), anyString());
	        verify(newbornHealthStatusRepo, times(1)).getnewBornHealthStatuses();
	        verify(optionalVaccinationsRepo, times(1)).getOptionalVaccinations();
	        verify(deliveryConductedByMasterRepo, times(1)).findByDeleted(false);
	        verify(foodIntoleranceStatusRepo, times(1)).findByDeletedOrderByIntoleranceStatus(false);
	        verify(covidSymptomsMasterRepo, times(1)).findByDeleted(false);
	        verify(covidContactHistoryMasterRepo, times(1)).findByDeleted(false);
	        verify(covidRecommnedationMasterRepo, times(1)).findByDeleted(false);
	        verify(foetalMonitorTestRepo, times(1)).getFoetalMonitorTestsDetails(anyInt());
	        verify(subVisitCategoryRepo, times(1)).findByDeletedOrderByName(false);

	        assertNotNull(result);
	        assertTrue(result.contains("bloodGroups"));
	        assertTrue(result.contains("childVaccinations"));
	        assertTrue(result.contains("deliveryPlaces"));
	        assertTrue(result.contains("deliveryTypes"));
	        assertTrue(result.contains("developmentProblems"));
	        assertTrue(result.contains("gestation"));
	        assertTrue(result.contains("illnessTypes"));
	        assertTrue(result.contains("jointTypes"));
	        assertTrue(result.contains("menstrualCycleLengths"));
	        assertTrue(result.contains("menstrualCycleBloodFlowDuration"));
	        assertTrue(result.contains("menstrualCycleStatus"));
	        assertTrue(result.contains("menstrualProblem"));
	        assertTrue(result.contains("musculoskeletalLateralityTypes"));
	        assertTrue(result.contains("musculoskeletalAbnormalityTypes"));
	        assertTrue(result.contains("pregDuration"));
	        assertTrue(result.contains("surgeryTypes"));
	        assertTrue(result.contains("comorbidConditions"));
	        assertTrue(result.contains("grossMotorMilestones"));
	        assertTrue(result.contains("fundalHeights"));
	        assertTrue(result.contains("feedTypes"));
	        assertTrue(result.contains("compFeedAges"));
	        assertTrue(result.contains("compFeedServings"));
	        assertTrue(result.contains("pregOutcomes"));
	        assertTrue(result.contains("birthComplications"));
	        assertTrue(result.contains("deliveryComplicationTypes"));
	        assertTrue(result.contains("postpartumComplicationTypes"));
	        assertTrue(result.contains("pregComplicationTypes"));
	        assertTrue(result.contains("postNatalComplications"));
	        assertTrue(result.contains("newBornComplications"));
	        assertTrue(result.contains("chiefComplaintMaster"));
	        assertTrue(result.contains("DiseaseTypes"));
	        assertTrue(result.contains("tobaccoUseStatus"));
	        assertTrue(result.contains("typeOfTobaccoProducts"));
	        assertTrue(result.contains("alcoholUseStatus"));
	        assertTrue(result.contains("typeOfAlcoholProducts"));
	        assertTrue(result.contains("frequencyOfAlcoholIntake"));
	        assertTrue(result.contains("quantityOfAlcoholIntake"));
	        assertTrue(result.contains("familyMemberTypes"));
	        assertTrue(result.contains("procedures"));
	        assertTrue(result.contains("vaccineMasterData"));
	        assertTrue(result.contains("foodIntoleranceStatus"));
	        assertTrue(result.contains("covidSymptomsMaster"));
	        assertTrue(result.contains("covidContactHistoryMaster"));
	        assertTrue(result.contains("covidRecommendationMaster"));
	        assertTrue(result.contains("fetosenseTestMaster"));
	        assertTrue(result.contains("subVisitCategories"));
	    }

	    @Test
	    public void testGetCommonDoctorMasterDataForGenopdAncNcdcarePnc_Positive() {
	        // Mocking repository methods
	        when(counsellingTypeRepo.getCounsellingTypes()).thenReturn(new ArrayList<>());
	        when(serviceMasterRepo.getAdditionalServices()).thenReturn(new ArrayList<>());
	        when(instituteRepo.getInstituteDetails(anyInt())).thenReturn(new ArrayList<>());
	        when(procedureRepo.getProcedureMasterData(anyInt(), anyString())).thenReturn(new ArrayList<>());
	        when(chiefComplaintMasterRepo.getChiefComplaintMaster()).thenReturn(new ArrayList<>());
	        when(itemFormMasterRepo.getItemFormMaster()).thenReturn(new ArrayList<>());
	        when(drugDoseMasterRepo.getDrugDoseMaster()).thenReturn(new ArrayList<>());
	        when(drugDurationUnitMasterRepo.getDrugDurationUnitMaster()).thenReturn(new ArrayList<>());
	        when(drugFrequencyMasterRepo.getDrugFrequencyMaster()).thenReturn(new ArrayList<>());
	        when(routeOfAdminRepo.getRouteOfAdminList()).thenReturn(new ArrayList<>());
	        when(itemMasterRepo.searchEdl(anyInt())).thenReturn(new ArrayList<>());
	        when(masterVanRepo.getFacilityID(anyInt())).thenReturn(1);
	        when(v_DrugPrescriptionRepo.getItemListForFacility(anyInt())).thenReturn(new ArrayList<>());
	        when(ncdScreeningMasterServiceImpl.getNCDScreeningConditions()).thenReturn(new ArrayList<>());
	        when(ncdCareTypeRepo.getNCDCareTypes()).thenReturn(new ArrayList<>());
	        when(referralReasonRepo.findByDeletedAndVisitCategoryIdOrderByName(anyBoolean(), anyInt())).thenReturn(new ArrayList<>());
	        when(counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(anyBoolean(), anyInt())).thenReturn(new ArrayList<>());
	        when(nextDueVaccinesMasterRepo.findByDeletedOrderById(anyBoolean())).thenReturn(new ArrayList<>());
	        when(nextImmunizationLocationMasterRepo.findByDeletedOrderByName(anyBoolean())).thenReturn(new ArrayList<>());

	        // Call the method
	        String result = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(1, 1, "M", 1, 1);

	        // Assert the result
	        assertNotNull(result);
	        assertTrue(result.contains("counsellingTypes"));
	        assertTrue(result.contains("higherHealthCare"));
	        assertTrue(result.contains("additionalServices"));
	    }

	    @Test
	    public void testGetCommonDoctorMasterDataForGenopdAncNcdcarePnc_Negative() {
	        // Mocking repository methods to return empty or null data
	        when(counsellingTypeRepo.getCounsellingTypes()).thenReturn(null);
	        when(serviceMasterRepo.getAdditionalServices()).thenReturn(null);
	        when(instituteRepo.getInstituteDetails(anyInt())).thenReturn(null);
	        when(procedureRepo.getProcedureMasterData(anyInt(), anyString())).thenReturn(null);
	        when(chiefComplaintMasterRepo.getChiefComplaintMaster()).thenReturn(null);
	        when(itemFormMasterRepo.getItemFormMaster()).thenReturn(null);
	        when(drugDoseMasterRepo.getDrugDoseMaster()).thenReturn(null);
	        when(drugDurationUnitMasterRepo.getDrugDurationUnitMaster()).thenReturn(null);
	        when(drugFrequencyMasterRepo.getDrugFrequencyMaster()).thenReturn(null);
	        when(routeOfAdminRepo.getRouteOfAdminList()).thenReturn(null);
	        when(itemMasterRepo.searchEdl(anyInt())).thenReturn(null);
	        when(masterVanRepo.getFacilityID(anyInt())).thenReturn(null);
	        when(v_DrugPrescriptionRepo.getItemListForFacility(anyInt())).thenReturn(null);
	        when(ncdScreeningMasterServiceImpl.getNCDScreeningConditions()).thenReturn(null);
	        when(ncdCareTypeRepo.getNCDCareTypes()).thenReturn(null);
	        when(referralReasonRepo.findByDeletedAndVisitCategoryIdOrderByName(anyBoolean(), anyInt())).thenReturn(null);
	        when(counsellingProvidedMasterRepo.findByDeletedAndVisitCategoryIDOrderByName(anyBoolean(), anyInt())).thenReturn(null);
	        when(nextDueVaccinesMasterRepo.findByDeletedOrderById(anyBoolean())).thenReturn(null);
	        when(nextImmunizationLocationMasterRepo.findByDeletedOrderByName(anyBoolean())).thenReturn(null);

	        // Call the method with invalid inputs
	        String result = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(null, -1, null, null, -1);

	        // Assert the result
	        assertNotNull(result);
	        assertFalse(result.contains("counsellingTypes"));
	        assertFalse(result.contains("higherHealthCare"));
	        assertFalse(result.contains("additionalServices"));
	    }
	}


