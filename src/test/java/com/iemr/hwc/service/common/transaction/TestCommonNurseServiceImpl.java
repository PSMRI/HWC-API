package com.iemr.hwc.service.common.transaction;

import static org.mockito.Mockito.*;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.JsonObject;
import com.iemr.hwc.data.BeneficiaryVisitDetail;
import com.iemr.hwc.data.anc.BenAdherence;
import com.iemr.hwc.data.anc.BenAllergyHistory;
import com.iemr.hwc.data.anc.BenChildDevelopmentHistory;
import com.iemr.hwc.data.anc.BenFamilyHistory;
import com.iemr.hwc.data.anc.BenMedHistory;
import com.iemr.hwc.data.anc.BenMedicationHistory;
import com.iemr.hwc.data.anc.BenMenstrualDetails;
import com.iemr.hwc.data.anc.BenPersonalHabit;
import com.iemr.hwc.data.anc.BencomrbidityCondDetails;
import com.iemr.hwc.data.anc.ChildFeedingDetails;
import com.iemr.hwc.data.anc.ChildOptionalVaccineDetail;
import com.iemr.hwc.data.anc.ChildVaccineDetail1;
import com.iemr.hwc.data.anc.FemaleObstetricHistory;
import com.iemr.hwc.data.anc.OralExamination;
import com.iemr.hwc.data.anc.PerinatalHistory;
import com.iemr.hwc.data.anc.PhyGeneralExamination;
import com.iemr.hwc.data.anc.PhyHeadToToeExamination;
import com.iemr.hwc.data.anc.SysCardiovascularExamination;
import com.iemr.hwc.data.anc.SysCentralNervousExamination;
import com.iemr.hwc.data.anc.SysGastrointestinalExamination;
import com.iemr.hwc.data.anc.SysGenitourinarySystemExamination;
import com.iemr.hwc.data.anc.SysMusculoskeletalSystemExamination;
import com.iemr.hwc.data.anc.SysRespiratoryExamination;
import com.iemr.hwc.data.anc.WrapperBenInvestigationANC;
import com.iemr.hwc.data.anc.WrapperChildOptionalVaccineDetail;
import com.iemr.hwc.data.anc.WrapperComorbidCondDetails;
import com.iemr.hwc.data.anc.WrapperFemaleObstetricHistory;
import com.iemr.hwc.data.anc.WrapperImmunizationHistory;
import com.iemr.hwc.data.anc.WrapperMedicationHistory;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.bmi.BmiCalculation;
import com.iemr.hwc.data.doctor.ProviderSpecificRequest;
import com.iemr.hwc.data.ncdScreening.IDRSData;
import com.iemr.hwc.data.ncdScreening.PhysicalActivityType;
import com.iemr.hwc.data.nurse.BenAnthropometryDetail;
import com.iemr.hwc.data.nurse.BenCancerVitalDetail;
import com.iemr.hwc.data.nurse.BenPhysicalVitalDetail;
import com.iemr.hwc.data.nurse.CDSS;
import com.iemr.hwc.data.quickConsultation.BenChiefComplaint;
import com.iemr.hwc.data.quickConsultation.LabTestOrderDetail;
import com.iemr.hwc.data.quickConsultation.PrescriptionDetail;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.repo.doctor.BenReferDetailsRepo;
import com.iemr.hwc.repo.nurse.BenAnthropometryRepo;
import com.iemr.hwc.repo.nurse.BenPhysicalVitalRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.nurse.anc.BenAllergyHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenChildDevelopmentHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenFamilyHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenMedHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenMedicationHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.BenMenstrualDetailsRepo;
import com.iemr.hwc.repo.nurse.anc.BenPersonalHabitRepo;
import com.iemr.hwc.repo.nurse.anc.BencomrbidityCondRepo;
import com.iemr.hwc.repo.nurse.anc.ChildFeedingDetailsRepo;
import com.iemr.hwc.repo.nurse.anc.ChildOptionalVaccineDetailRepo;
import com.iemr.hwc.repo.nurse.anc.ChildVaccineDetail1Repo;
import com.iemr.hwc.repo.nurse.anc.FemaleObstetricHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.OralExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.PerinatalHistoryRepo;
import com.iemr.hwc.repo.nurse.anc.PhyGeneralExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.PhyHeadToToeExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysCardiovascularExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysCentralNervousExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysGastrointestinalExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysGenitourinarySystemExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysMusculoskeletalSystemExaminationRepo;
import com.iemr.hwc.repo.nurse.anc.SysRespiratoryExaminationRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.IDRSDataRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.PhysicalActivityTypeRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.repo.quickConsultation.LabTestOrderDetailRepo;
import com.iemr.hwc.repo.quickConsultation.PrescribedDrugDetailRepo;
import com.iemr.hwc.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.hwc.repo.registrar.RegistrarRepoBenData;
import com.iemr.hwc.repo.registrar.ReistrarRepoBenSearch;
import com.iemr.hwc.repository.*;
import com.iemr.hwc.utils.exception.IEMRException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

public class TestCommonNurseServiceImpl {

	@ExtendWith(MockitoExtension.class)
	public class CommonNurseServiceImplTest {

		@Mock
		private RegistrarRepoBenData registrarRepoBenData;
		@Mock
		private BenVisitDetailRepo benVisitDetailRepo;
		@Mock
		private CDSSRepo cdssRepo;
		@Mock
		private BenChiefComplaintRepo benChiefComplaintRepo;
		@Mock
		private BenMedHistoryRepo benMedHistoryRepo;
		@Mock
		private BencomrbidityCondRepo bencomrbidityCondRepo;
		@Mock
		private BenMedicationHistoryRepo benMedicationHistoryRepo;
		@Mock
		private BenMenstrualDetailsRepo benMenstrualDetailsRepo;
		@Mock
		private BenFamilyHistoryRepo benFamilyHistoryRepo;
		@Mock
		private BenPersonalHabitRepo benPersonalHabitRepo;
		@Mock
		private BenAllergyHistoryRepo benAllergyHistoryRepo;
		@Mock
		private ChildOptionalVaccineDetailRepo childOptionalVaccineDetailRepo;
		@Mock
		private ChildVaccineDetail1Repo childVaccineDetail1Repo;
		@Mock
		private BenAnthropometryRepo benAnthropometryRepo;
		@Mock
		private IDRSDataRepo iDrsDataRepo;
		@Mock
		private PhysicalActivityTypeRepo physicalActivityTypeRepo;
		@Mock
		private BenPhysicalVitalRepo benPhysicalVitalRepo;
		@Mock
		private PhyGeneralExaminationRepo phyGeneralExaminationRepo;
		@Mock
		private SysGastrointestinalExaminationRepo sysGastrointestinalExaminationRepo;
		@Mock
		private SysCardiovascularExaminationRepo sysCardiovascularExaminationRepo;
		@Mock
		private SysRespiratoryExaminationRepo sysRespiratoryExaminationRepo;
		@Mock
		private SysCentralNervousExaminationRepo sysCentralNervousExaminationRepo;
		@Mock
		private SysMusculoskeletalSystemExaminationRepo sysMusculoskeletalSystemExaminationRepo;
		@Mock
		private SysGenitourinarySystemExaminationRepo sysGenitourinarySystemExaminationRepo;
		@Mock
		private FemaleObstetricHistoryRepo femaleObstetricHistoryRepo;
		@Mock
		private IDRSDataRepo idrsDataRepo;
		@Mock
		private PhyHeadToToeExaminationRepo phyHeadToToeExaminationRepo;
		@Mock
		private OralExaminationRepo oralExaminationRepo;
		@Mock
		private PrescriptionDetailRepo prescriptionDetailRepo;

		@Mock
		private LabTestOrderDetailRepo labTestOrderDetailRepo;

		@Mock
		private CommonNurseServiceImpl commonNurseServiceImplSpy;
		@Mock
		private ReistrarRepoBenSearch reistrarRepoBenSearch;
		@Mock
		private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
		@Mock
		private BenAdherenceRepo benAdherenceRepo;
		@Mock
		private ChildFeedingDetailsRepo childFeedingDetailsRepo;
		@Mock
		private PerinatalHistoryRepo perinatalHistoryRepo;
		@Mock
		private IDRSDataRepo iDRSDataRepo;

		@Mock
		private BenReferDetailsRepo benReferDetailsRepo;

		@Mock
		private PrescribedDrugDetailRepo prescribedDrugDetailRepo;
		@Mock
		private BmiCalculationRepo bmiCalculationRepo;

		@InjectMocks
		private CommonNurseServiceImpl commonNurseServiceImpl;
		private BeneficiaryVisitDetail beneficiaryVisitDetail;

		private CommonNurseServiceImpl commonNurseService;

		private Long beneficiaryRegID;
		private String visitReason;
		private String visitCategory;
		private Long benRegID;
		private Long visitCode;
		private CDSS cdss;
		private List<BenChiefComplaint> benChiefComplaintList;
		private BenMedHistory benMedHistory;
		private ArrayList<BenMedHistory> benMedHistoryList;
		private WrapperComorbidCondDetails wrapperComorbidCondDetails;
		private ArrayList<BencomrbidityCondDetails> bencomrbidityCondDetailsList;
		private WrapperMedicationHistory wrapperMedicationHistory;
		private ArrayList<BenMedicationHistory> benMedicationHistoryList;

		private ArrayList<FemaleObstetricHistory> femaleObstetricHistoryList;
		private BenFamilyHistory benFamilyHistory;
		private BenPersonalHabit benPersonalHabit;
		private BenAllergyHistory benAllergyHistory;
		private WrapperChildOptionalVaccineDetail wrapperChildOptionalVaccineDetail;
		private List<ChildOptionalVaccineDetail> childOptionalVaccineDetails;
		private WrapperImmunizationHistory wrapperImmunizationHistory;
		private ArrayList<ChildVaccineDetail1> childVaccineDetails;
		private BenAnthropometryDetail benAnthropometryDetail;
		private IDRSData idrsData;
		private PhysicalActivityType physicalActivityType;
		private ArrayList<BenFamilyHistory> familyHistoryList;
		private BenPhysicalVitalDetail benPhysicalVitalDetail;
		private BenPhysicalVitalDetail physicalVitalDetail;
		private PhyGeneralExamination generalExamination;
		private SysGastrointestinalExamination gastrointestinalExamination;
		private SysRespiratoryExamination sysRespiratoryExamination;
		private SysCardiovascularExamination cardiovascularExamination;
		private SysCentralNervousExamination sysCentralNervousExamination;
		private SysMusculoskeletalSystemExamination musculoskeletalSystemExamination;
		private SysGenitourinarySystemExamination examination;
		private ArrayList<Object[]> benPastHistoryDataArray;
		private List<Object[]> benPersonalHabits;
		private List<Object[]> benAllergyHistoryData;
		private List<Object[]> medicationHistoryData;
		private List<Object[]> benMenstrualDetailsList;
		private List<Object[]> mockData;
		private ArrayList<Object[]> mockResponseList;
		private ArrayList<Object[]> familyHistoryData;
		private ArrayList<Object[]> pastHistoryData;

		private ArrayList<Object[]> menstrualHistory;
		private BenMenstrualDetails benMenstrualDetails;
		private WrapperFemaleObstetricHistory wrapperFemaleObstetricHistory;
		private ArrayList<Object[]> childOptionalVaccineDetail;
		private PhyGeneralExamination phyGeneralExamination;
		private PhyHeadToToeExamination phyHeadToToeExamination;
		private SysCardiovascularExamination sysCardiovascularExamination;
		private SysMusculoskeletalSystemExamination sysMusculoskeletalSystemExamination;
		private SysGenitourinarySystemExamination expectedExamination;
		private ArrayList<BenAllergyHistory> allergyList;
		private ChildVaccineDetail1 childVaccineDetail;
		private PhyHeadToToeExamination headToToeExamination;
		private SysMusculoskeletalSystemExamination musculoskeletalSystem;
		private SysGenitourinarySystemExamination genitourinarySystem;
		private OralExamination oralExamination;
		private PrescriptionDetail prescriptionDetail;
		private JsonObject caseSheet;
		private Long prescriptionID;
		private WrapperBenInvestigationANC wrapperBenInvestigationANC;
		private LabTestOrderDetail labTestOrderDetail;
		private ArrayList<LabTestOrderDetail> labTestOrderDetails;
		private List<Object[]> nurseWorkListData;
		private Integer providerServiceMapId;
		private Integer vanID;
		private ArrayList<BeneficiaryFlowStatus> beneficiaryFlowStatusList;
		private ArrayList<BeneficiaryFlowStatus> mockBeneficiaryFlowStatusList;
		private Integer labWL;
		private long sevenDaysAgo;
		private BenAdherence benAdherence;
		private ChildFeedingDetails childFeedingDetails;
		private PerinatalHistory perinatalHistory;
		private List<Object[]> mockResultList;
		private BenChildDevelopmentHistory benChildDevelopmentHistory;
		private ArrayList<Object[]> perinatalHistoryDetail;
		private List<Object[]> mockFeedingHistoryDetail;
		private List<Object[]> mockDevelopmentHistoryDetail;

		private BenChildDevelopmentHistory childDevelopmentDetails;
		private ArrayList<Object[]> benLastSixVisitDetails;
		private ArrayList<Object[]> benAnthro;
		private ArrayList<Object[]> benVital;
		private ArrayList<BenCancerVitalDetail> benCancerVital;
		private Timestamp timestamp;
		private List<IDRSData> idrsDataList;
		private long startTime;
		private List<Object[]> resultSet;
		private List<IDRSData> resultSet1;
		private ProviderSpecificRequest request;
		private BmiCalculation bmiCalculation;

		@BeforeEach
		public void setUp() {
			// Initialize any necessary data here
		}

		@Test
		public void testUpdateBeneficiaryStatus_Success() {
			Character status = 'A';
			Long benRegID = 1L;
			Integer expectedUpdateCount = 1;

			when(registrarRepoBenData.updateBenFlowStatus(status, benRegID)).thenReturn(expectedUpdateCount);

			Integer result = commonNurseServiceImpl.updateBeneficiaryStatus(status, benRegID);

			assertEquals(expectedUpdateCount, result);
			verify(registrarRepoBenData, times(1)).updateBenFlowStatus(status, benRegID);
		}

		@Test
		public void testUpdateBeneficiaryStatus_NoUpdate() {
			Character status = 'B';
			Long benRegID = 2L;
			Integer expectedUpdateCount = 0;

			when(registrarRepoBenData.updateBenFlowStatus(status, benRegID)).thenReturn(expectedUpdateCount);

			Integer result = commonNurseServiceImpl.updateBeneficiaryStatus(status, benRegID);

			assertEquals(expectedUpdateCount, result);
			verify(registrarRepoBenData, times(1)).updateBenFlowStatus(status, benRegID);
		}

		@Test
		public void testUpdateBeneficiaryStatus_Exception() {
			Character status = 'C';
			Long benRegID = 3L;

			when(registrarRepoBenData.updateBenFlowStatus(status, benRegID))
					.thenThrow(new RuntimeException("Database error"));

			Exception exception = assertThrows(RuntimeException.class, () -> {
				commonNurseServiceImpl.updateBeneficiaryStatus(status, benRegID);
			});

			assertEquals("Database error", exception.getMessage());
			verify(registrarRepoBenData, times(1)).updateBenFlowStatus(status, benRegID);
		}
	}

	@Test
	public void testUpdateBeneficiaryStatus_Success() {
		Character status = 'A';
		Long benRegID = 1L;
		Integer expectedUpdateCount = 1;

		when(registrarRepoBenData.updateBenFlowStatus(status, benRegID)).thenReturn(expectedUpdateCount);

		Integer result = commonNurseServiceImpl.updateBeneficiaryStatus(status, benRegID);

		assertEquals(expectedUpdateCount, result);
		verify(registrarRepoBenData, times(1)).updateBenFlowStatus(status, benRegID);
	}

	@Test
	public void testUpdateBeneficiaryStatus_NoUpdate() {
		Character status = 'B';
		Long benRegID = 2L;
		Integer expectedUpdateCount = 0;

		when(registrarRepoBenData.updateBenFlowStatus(status, benRegID)).thenReturn(expectedUpdateCount);

		Integer result = commonNurseServiceImpl.updateBeneficiaryStatus(status, benRegID);

		assertEquals(expectedUpdateCount, result);
		verify(registrarRepoBenData, times(1)).updateBenFlowStatus(status, benRegID);
	}

	@Test
	public void testUpdateBeneficiaryStatus_Exception() {
		Character status = 'C';
		Long benRegID = 3L;

		when(registrarRepoBenData.updateBenFlowStatus(status, benRegID))
				.thenThrow(new RuntimeException("Database error"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonNurseServiceImpl.updateBeneficiaryStatus(status, benRegID);
		});

		assertEquals("Database error", exception.getMessage());
		verify(registrarRepoBenData, times(1)).updateBenFlowStatus(status, benRegID);
	}

	@Test
	public void testSaveBeneficiaryVisitDetails_NewVisit() {
		when(benVisitDetailRepo.getVisitCountForBeneficiary(1L)).thenReturn(null);
		when(benVisitDetailRepo.save(any(BeneficiaryVisitDetail.class))).thenReturn(beneficiaryVisitDetail);

		Long visitId = commonNurseServiceImpl.saveBeneficiaryVisitDetails(beneficiaryVisitDetail);

		assertNotNull(visitId);
		verify(benVisitDetailRepo, times(1)).save(any(BeneficiaryVisitDetail.class));
	}

	@Test
	public void testSaveBeneficiaryVisitDetails_IncrementVisitCount() {
		when(benVisitDetailRepo.getVisitCountForBeneficiary(1L)).thenReturn((short) 1);
		when(benVisitDetailRepo.save(any(BeneficiaryVisitDetail.class))).thenReturn(beneficiaryVisitDetail);

		Long visitId = commonNurseServiceImpl.saveBeneficiaryVisitDetails(beneficiaryVisitDetail);

		assertNotNull(visitId);
		assertEquals(2, beneficiaryVisitDetail.getVisitNo());
		verify(benVisitDetailRepo, times(1)).save(any(BeneficiaryVisitDetail.class));
	}

	@Test
	public void testSaveBeneficiaryVisitDetails_HandleFileIDs() {
		when(benVisitDetailRepo.getVisitCountForBeneficiary(1L)).thenReturn((short) 1);
		when(benVisitDetailRepo.save(any(BeneficiaryVisitDetail.class))).thenReturn(beneficiaryVisitDetail);

		Long visitId = commonNurseServiceImpl.saveBeneficiaryVisitDetails(beneficiaryVisitDetail);

		assertNotNull(visitId);
		assertEquals("1,2,3,", beneficiaryVisitDetail.getReportFilePath());
		verify(benVisitDetailRepo, times(1)).save(any(BeneficiaryVisitDetail.class));
	}

	@Test
	public void testSaveBeneficiaryVisitDetails_HandleFollowUpMethods() {
		when(benVisitDetailRepo.getVisitCountForBeneficiary(1L)).thenReturn((short) 1);
		when(benVisitDetailRepo.save(any(BeneficiaryVisitDetail.class))).thenReturn(beneficiaryVisitDetail);

		Long visitId = commonNurseServiceImpl.saveBeneficiaryVisitDetails(beneficiaryVisitDetail);

		assertNotNull(visitId);
		assertEquals("Method1||Method2", beneficiaryVisitDetail.getFpMethodFollowup());
		verify(benVisitDetailRepo, times(1)).save(any(BeneficiaryVisitDetail.class));
	}

	@Test
	public void testSaveBeneficiaryVisitDetails_HandleSideEffects() {
		when(benVisitDetailRepo.getVisitCountForBeneficiary(1L)).thenReturn((short) 1);
		when(benVisitDetailRepo.save(any(BeneficiaryVisitDetail.class))).thenReturn(beneficiaryVisitDetail);

		Long visitId = commonNurseServiceImpl.saveBeneficiaryVisitDetails(beneficiaryVisitDetail);

		assertNotNull(visitId);
		assertEquals("SideEffect1||SideEffect2", beneficiaryVisitDetail.getFpSideeffects());
		verify(benVisitDetailRepo, times(1)).save(any(BeneficiaryVisitDetail.class));
	}

	@Test
	public void testSaveBeneficiaryVisitDetails_GenerateVisitCode() {
		when(benVisitDetailRepo.getVisitCountForBeneficiary(1L)).thenReturn((short) 1);
		when(benVisitDetailRepo.save(any(BeneficiaryVisitDetail.class))).thenReturn(beneficiaryVisitDetail);
		when(benVisitDetailRepo.updateVisitCode(anyLong(), anyLong())).thenReturn(1);

		Long visitId = commonNurseServiceImpl.saveBeneficiaryVisitDetails(beneficiaryVisitDetail, 1);

		assertNotNull(visitId);
		verify(benVisitDetailRepo, times(1)).updateVisitCode(anyLong(), anyLong());
	}

	@Test
	public void testGetMaxCurrentdate_ValidDate() throws IEMRException, ParseException {
		String maxDate = "2023-10-10 10:10:10.0";
		when(benVisitDetailRepo.getMaxCreatedDate(beneficiaryRegID, visitReason, visitCategory)).thenReturn(maxDate);

		int result = commonNurseService.getMaxCurrentdate(beneficiaryRegID, visitReason, visitCategory);

		assertTrue(result <= 0);
		verify(benVisitDetailRepo, times(1)).getMaxCreatedDate(beneficiaryRegID, visitReason, visitCategory);
	}

	@Test
	public void testGetMaxCurrentdate_NullDate() throws IEMRException {
		when(benVisitDetailRepo.getMaxCreatedDate(beneficiaryRegID, visitReason, visitCategory)).thenReturn(null);

		int result = commonNurseService.getMaxCurrentdate(beneficiaryRegID, visitReason, visitCategory);

		assertEquals(0, result);
		verify(benVisitDetailRepo, times(1)).getMaxCreatedDate(beneficiaryRegID, visitReason, visitCategory);
	}

	@Test
	public void testGetMaxCurrentdate_ParseException() throws IEMRException {
		String maxDate = "invalid-date";
		when(benVisitDetailRepo.getMaxCreatedDate(beneficiaryRegID, visitReason, visitCategory)).thenReturn(maxDate);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseService.getMaxCurrentdate(beneficiaryRegID, visitReason, visitCategory);
		});

		assertTrue(exception.getMessage().contains("Error while parseing created date"));
		verify(benVisitDetailRepo, times(1)).getMaxCreatedDate(beneficiaryRegID, visitReason, visitCategory);
	}

	@Test
	public void testGenerateVisitCode_ValidInputs() {
		Long visitID = 12345L;
		Integer vanID = 678;
		Integer sessionID = 9;

		when(benVisitDetailRepo.updateVisitCode(anyLong(), eq(visitID))).thenReturn(1);

		Long visitCode = commonNurseServiceImpl.generateVisitCode(visitID, vanID, sessionID);

		assertNotNull(visitCode);
		assertEquals("900067800012345", visitCode.toString());
		verify(benVisitDetailRepo, times(1)).updateVisitCode(anyLong(), eq(visitID));
	}

	@Test
	public void testGenerateVisitCode_MinimumValues() {
		Long visitID = 1L;
		Integer vanID = 1;
		Integer sessionID = 1;

		when(benVisitDetailRepo.updateVisitCode(anyLong(), eq(visitID))).thenReturn(1);

		Long visitCode = commonNurseServiceImpl.generateVisitCode(visitID, vanID, sessionID);

		assertNotNull(visitCode);
		assertEquals("100000100000001", visitCode.toString());
		verify(benVisitDetailRepo, times(1)).updateVisitCode(anyLong(), eq(visitID));
	}

	@Test
	public void testGenerateVisitCode_MaximumValues() {
		Long visitID = 99999999L;
		Integer vanID = 99999;
		Integer sessionID = 999;

		when(benVisitDetailRepo.updateVisitCode(anyLong(), eq(visitID))).thenReturn(1);

		Long visitCode = commonNurseServiceImpl.generateVisitCode(visitID, vanID, sessionID);

		assertNotNull(visitCode);
		assertEquals("999999999999999999", visitCode.toString());
		verify(benVisitDetailRepo, times(1)).updateVisitCode(anyLong(), eq(visitID));
	}

	@Test
	public void testGenerateVisitCode_UpdateVisitCodeReturnsZero() {
		Long visitID = 12345L;
		Integer vanID = 678;
		Integer sessionID = 9;

		when(benVisitDetailRepo.updateVisitCode(anyLong(), eq(visitID))).thenReturn(0);

		Long visitCode = commonNurseServiceImpl.generateVisitCode(visitID, vanID, sessionID);

		assertNotNull(visitCode);
		assertEquals(0L, visitCode);
		verify(benVisitDetailRepo, times(1)).updateVisitCode(anyLong(), eq(visitID));
	}

	@Test
	public void testUpdateVisitCodeInVisitDetailsTable_Success() {
		Long visitCode = 12345678901234L;
		Long visitID = 1L;

		when(benVisitDetailRepo.updateVisitCode(visitCode, visitID)).thenReturn(1);

		int result = commonNurseServiceImpl.updateVisitCodeInVisitDetailsTable(visitCode, visitID);

		assertEquals(1, result);
		verify(benVisitDetailRepo, times(1)).updateVisitCode(visitCode, visitID);
	}

	@Test
	public void testUpdateVisitCodeInVisitDetailsTable_Failure() {
		Long visitCode = 12345678901234L;
		Long visitID = 1L;

		when(benVisitDetailRepo.updateVisitCode(visitCode, visitID)).thenReturn(0);

		int result = commonNurseServiceImpl.updateVisitCodeInVisitDetailsTable(visitCode, visitID);

		assertEquals(0, result);
		verify(benVisitDetailRepo, times(1)).updateVisitCode(visitCode, visitID);
	}

	@Test
	public void testGetBenVisitCount_WhenVisitCountIsNotNullAndGreaterThanOrEqualToZero() {
		Short visitCount = 5;
		when(benVisitDetailRepo.getVisitCountForBeneficiary(benRegID)).thenReturn(visitCount);

		Short result = commonNurseServiceImpl.getBenVisitCount(benRegID);

		assertNotNull(result);
		assertEquals(6, result);
		verify(benVisitDetailRepo, times(1)).getVisitCountForBeneficiary(benRegID);
	}

	@Test
	public void testGetBenVisitCount_WhenVisitCountIsNull() {
		when(benVisitDetailRepo.getVisitCountForBeneficiary(benRegID)).thenReturn(null);

		Short result = commonNurseServiceImpl.getBenVisitCount(benRegID);

		assertNotNull(result);
		assertEquals(1, result);
		verify(benVisitDetailRepo, times(1)).getVisitCountForBeneficiary(benRegID);
	}

	@Test
	public void testUpdateBeneficiaryVisitDetails_Success() {
		when(benVisitDetailRepo.updateBeneficiaryVisitDetail(anyLong(), anyString(), anyLong(), anyString(),
				anyString(), anyLong(), anyString(), anyString(), anyString(), anyLong())).thenReturn(1);

		int response = commonNurseServiceImpl.updateBeneficiaryVisitDetails(beneficiaryVisitDetail);

		assertEquals(1, response);
		verify(benVisitDetailRepo, times(1)).updateBeneficiaryVisitDetail(eq(1L), eq("Reason"), eq(1L), eq("Category"),
				eq("Pregnant"), eq(1L), eq("Type"), eq("Location"), eq("User"), eq(1L));
	}

	@Test
	public void testUpdateBeneficiaryVisitDetails_Exception() {
		when(benVisitDetailRepo.updateBeneficiaryVisitDetail(anyLong(), anyString(), anyLong(), anyString(),
				anyString(), anyLong(), anyString(), anyString(), anyString(), anyLong()))
				.thenThrow(new RuntimeException("Database error"));

		int response = commonNurseServiceImpl.updateBeneficiaryVisitDetails(beneficiaryVisitDetail);

		assertEquals(0, response);
		verify(benVisitDetailRepo, times(1)).updateBeneficiaryVisitDetail(eq(1L), eq("Reason"), eq(1L), eq("Category"),
				eq("Pregnant"), eq(1L), eq("Type"), eq("Location"), eq("User"), eq(1L));
	}

	@Test
	public void testGetCSVisitDetails_Found() {
		when(benVisitDetailRepo.getVisitDetails(1L, 1L)).thenReturn(beneficiaryVisitDetail);

		BeneficiaryVisitDetail result = commonNurseServiceImpl.getCSVisitDetails(1L, 1L);

		assertNotNull(result);
		assertEquals(1L, result.getBeneficiaryRegID());
		assertEquals(1L, result.getVisitCode());
		assertArrayEquals(new Integer[] { 1, 2, 3 }, result.getFileIDs());
		assertArrayEquals(new String[] { "method1", "method2" }, result.getFollowUpForFpMethod());
		assertArrayEquals(new String[] { "sideEffect1", "sideEffect2" }, result.getSideEffects());
	}

	@Test
	public void testGetCSVisitDetails_NotFound() {
		when(benVisitDetailRepo.getVisitDetails(1L, 1L)).thenReturn(null);

		BeneficiaryVisitDetail result = commonNurseServiceImpl.getCSVisitDetails(1L, 1L);

		assertNull(result);
	}

	@Test
	public void testGetCSVisitDetails_WithFileIDs() {
		when(benVisitDetailRepo.getVisitDetails(1L, 1L)).thenReturn(beneficiaryVisitDetail);

		BeneficiaryVisitDetail result = commonNurseServiceImpl.getCSVisitDetails(1L, 1L);

		assertNotNull(result);
		assertArrayEquals(new Integer[] { 1, 2, 3 }, result.getFileIDs());
	}

	@Test
	public void testGetCSVisitDetails_WithFollowUpMethodsAndSideEffects() {
		when(benVisitDetailRepo.getVisitDetails(1L, 1L)).thenReturn(beneficiaryVisitDetail);

		BeneficiaryVisitDetail result = commonNurseServiceImpl.getCSVisitDetails(1L, 1L);

		assertNotNull(result);
		assertArrayEquals(new String[] { "method1", "method2" }, result.getFollowUpForFpMethod());
		assertArrayEquals(new String[] { "sideEffect1", "sideEffect2" }, result.getSideEffects());
	}

	@Test
	public void testGetCdssDetails_ValidData() {
		when(cdssRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode)).thenReturn(cdss);

		CDSS result = commonNurseServiceImpl.getCdssDetails(benRegID, visitCode);

		assertNotNull(result);
		assertEquals(benRegID, result.getBeneficiaryRegID());
		assertEquals(visitCode, result.getVisitCode());
		verify(cdssRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
	}

	@Test
	public void testGetCdssDetails_InvalidData() {
		when(cdssRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode)).thenReturn(null);

		CDSS result = commonNurseServiceImpl.getCdssDetails(benRegID, visitCode);

		assertNull(result);
		verify(cdssRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
	}

	@Test
	public void testGetCdssDetails_NullReturn() {
		when(cdssRepo.findByBeneficiaryRegIDAndVisitCode(anyLong(), anyLong())).thenReturn(null);

		CDSS result = commonNurseServiceImpl.getCdssDetails(benRegID, visitCode);

		assertNull(result);
		verify(cdssRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
	}

	@Test
	public void testSaveBenChiefComplaints_EmptyList() {
		int result = commonNurseServiceImpl.saveBenChiefComplaints(benChiefComplaintList);
		assertEquals(1, result);
		verify(benChiefComplaintRepo, never()).saveAll(anyList());
	}

	@Test
	public void testSaveBenChiefComplaints_ValidList() {
		BenChiefComplaint complaint1 = new BenChiefComplaint();
		complaint1.setChiefComplaintID(1L);
		BenChiefComplaint complaint2 = new BenChiefComplaint();
		complaint2.setChiefComplaintID(2L);
		benChiefComplaintList.add(complaint1);
		benChiefComplaintList.add(complaint2);

		when(benChiefComplaintRepo.saveAll(anyList())).thenReturn(benChiefComplaintList);

		int result = commonNurseServiceImpl.saveBenChiefComplaints(benChiefComplaintList);
		assertEquals(1, result);
		verify(benChiefComplaintRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testSaveBenChiefComplaints_ListWithNullID() {
		BenChiefComplaint complaint1 = new BenChiefComplaint();
		complaint1.setChiefComplaintID(null);
		BenChiefComplaint complaint2 = new BenChiefComplaint();
		complaint2.setChiefComplaintID(2L);
		benChiefComplaintList.add(complaint1);
		benChiefComplaintList.add(complaint2);

		List<BenChiefComplaint> filteredList = new ArrayList<>();
		filteredList.add(complaint2);

		when(benChiefComplaintRepo.saveAll(anyList())).thenReturn(filteredList);

		int result = commonNurseServiceImpl.saveBenChiefComplaints(benChiefComplaintList);
		assertEquals(1, result);
		verify(benChiefComplaintRepo, times(1)).saveAll(filteredList);
	}

	@Test
	public void testSaveBenPastHistory_WithNonNullList() {
		when(benMedHistoryRepo.saveAll(benMedHistoryList)).thenReturn(benMedHistoryList);

		Long result = commonNurseServiceImpl.saveBenPastHistory(benMedHistory);

		assertEquals(Long.valueOf(1), result);
		verify(benMedHistoryRepo, times(1)).saveAll(benMedHistoryList);
	}

	@Test
	public void testSaveBenPastHistory_WithNullList() {
		benMedHistory.setBenPastHistory(null);

		Long result = commonNurseServiceImpl.saveBenPastHistory(benMedHistory);

		assertEquals(Long.valueOf(1), result);
		verify(benMedHistoryRepo, times(0)).saveAll(any());
	}

	@Test
	public void testSaveBenPastHistory_WithEmptyList() {
		benMedHistoryList.clear();
		benMedHistory.setBenPastHistory(benMedHistoryList);

		Long result = commonNurseServiceImpl.saveBenPastHistory(benMedHistory);

		assertEquals(Long.valueOf(1), result);
		verify(benMedHistoryRepo, times(0)).saveAll(any());
	}

	@Test
	public void testSaveBenComorbidConditions_NonEmptyList() {
		BencomrbidityCondDetails detail = new BencomrbidityCondDetails();
		detail.setID(1L);
		bencomrbidityCondDetailsList.add(detail);

		when(bencomrbidityCondRepo.saveAll(bencomrbidityCondDetailsList)).thenReturn(bencomrbidityCondDetailsList);

		Long result = commonNurseServiceImpl.saveBenComorbidConditions(wrapperComorbidCondDetails);

		assertEquals(1L, result);
		verify(bencomrbidityCondRepo, times(1)).saveAll(bencomrbidityCondDetailsList);
	}

	@Test
	public void testSaveBenComorbidConditions_EmptyList() {
		Long result = commonNurseServiceImpl.saveBenComorbidConditions(wrapperComorbidCondDetails);

		assertEquals(1L, result);
		verify(bencomrbidityCondRepo, never()).saveAll(anyList());
	}

	@Test
	public void testSaveBenMedicationHistory_EmptyList() {
		Long result = commonNurseServiceImpl.saveBenMedicationHistory(wrapperMedicationHistory);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenMedicationHistory_NonEmptyList_Success() {
		BenMedicationHistory history = new BenMedicationHistory();
		history.setID(1L);
		benMedicationHistoryList.add(history);

		when(benMedicationHistoryRepo.saveAll(benMedicationHistoryList)).thenReturn(benMedicationHistoryList);

		Long result = commonNurseServiceImpl.saveBenMedicationHistory(wrapperMedicationHistory);
		assertEquals(1L, result);
	}

	@Test
	public void testSaveBenMedicationHistory_NonEmptyList_Failure() {
		BenMedicationHistory history = new BenMedicationHistory();
		benMedicationHistoryList.add(history);

		when(benMedicationHistoryRepo.saveAll(benMedicationHistoryList)).thenReturn(new ArrayList<>());

		Long result = commonNurseServiceImpl.saveBenMedicationHistory(wrapperMedicationHistory);
		assertNull(result);
	}

	@Test
	public void testGetFemaleObstetricHistoryObj_EmptyList() {
		ArrayList<FemaleObstetricHistory> result = commonNurseService
				.getFemaleObstetricHistoryObj(femaleObstetricHistoryList);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetFemaleObstetricHistoryObj_WithPregnancyComplications() {
		FemaleObstetricHistory history = new FemaleObstetricHistory();
		ArrayList<Map<String, Object>> pregComplications = new ArrayList<>();
		Map<String, Object> complication = new HashMap<>();
		complication.put("pregComplicationID", "1");
		complication.put("pregComplicationType", "Type1");
		pregComplications.add(complication);
		history.setPregComplicationList(pregComplications);
		femaleObstetricHistoryList.add(history);

		ArrayList<FemaleObstetricHistory> result = commonNurseService
				.getFemaleObstetricHistoryObj(femaleObstetricHistoryList);
		assertEquals(1, result.size());
		assertEquals("1", result.get(0).getPregComplicationID());
		assertEquals("Type1", result.get(0).getPregComplicationType());
	}

	@Test
	public void testGetFemaleObstetricHistoryObj_WithDeliveryComplications() {
		FemaleObstetricHistory history = new FemaleObstetricHistory();
		ArrayList<Map<String, Object>> deliveryComplications = new ArrayList<>();
		Map<String, Object> complication = new HashMap<>();
		complication.put("deliveryComplicationID", "2");
		complication.put("deliveryComplicationType", "Type2");
		deliveryComplications.add(complication);
		history.setDeliveryComplicationList(deliveryComplications);
		femaleObstetricHistoryList.add(history);

		ArrayList<FemaleObstetricHistory> result = commonNurseService
				.getFemaleObstetricHistoryObj(femaleObstetricHistoryList);
		assertEquals(1, result.size());
		assertEquals("2", result.get(0).getDeliveryComplicationID());
		assertEquals("Type2", result.get(0).getDeliveryComplicationType());
	}

	@Test
	public void testGetFemaleObstetricHistoryObj_WithPostpartumComplications() {
		FemaleObstetricHistory history = new FemaleObstetricHistory();
		ArrayList<Map<String, Object>> postpartumComplications = new ArrayList<>();
		Map<String, Object> complication = new HashMap<>();
		complication.put("postpartumComplicationID", "3");
		complication.put("postpartumComplicationType", "Type3");
		postpartumComplications.add(complication);
		history.setPostpartumComplicationList(postpartumComplications);
		femaleObstetricHistoryList.add(history);

		ArrayList<FemaleObstetricHistory> result = commonNurseService
				.getFemaleObstetricHistoryObj(femaleObstetricHistoryList);
		assertEquals(1, result.size());
		assertEquals("3", result.get(0).getPostpartumComplicationID());
		assertEquals("Type3", result.get(0).getPostpartumComplicationType());
	}

	@Test
	public void testGetFemaleObstetricHistoryObj_WithPostAbortionComplications() {
		FemaleObstetricHistory history = new FemaleObstetricHistory();
		ArrayList<Map<String, Object>> postAbortionComplications = new ArrayList<>();
		Map<String, Object> complication = new HashMap<>();
		complication.put("complicationID", 4.0);
		complication.put("complicationValue", "Type4");
		postAbortionComplications.add(complication);
		history.setPostAbortionComplication(postAbortionComplications);
		femaleObstetricHistoryList.add(history);

		ArrayList<FemaleObstetricHistory> result = commonNurseService
				.getFemaleObstetricHistoryObj(femaleObstetricHistoryList);
		assertEquals(1, result.size());
		assertEquals("4", result.get(0).getPostAbortionComplication_db());
		assertEquals("Type4", result.get(0).getPostAbortionComplicationsValues());
	}

	@Test
	public void testGetFemaleObstetricHistoryObj_WithAllComplications() {
		FemaleObstetricHistory history = new FemaleObstetricHistory();

		ArrayList<Map<String, Object>> pregComplications = new ArrayList<>();
		Map<String, Object> pregComplication = new HashMap<>();
		pregComplication.put("pregComplicationID", "1");
		pregComplication.put("pregComplicationType", "Type1");
		pregComplications.add(pregComplication);
		history.setPregComplicationList(pregComplications);

		ArrayList<Map<String, Object>> deliveryComplications = new ArrayList<>();
		Map<String, Object> deliveryComplication = new HashMap<>();
		deliveryComplication.put("deliveryComplicationID", "2");
		deliveryComplication.put("deliveryComplicationType", "Type2");
		deliveryComplications.add(deliveryComplication);
		history.setDeliveryComplicationList(deliveryComplications);

		ArrayList<Map<String, Object>> postpartumComplications = new ArrayList<>();
		Map<String, Object> postpartumComplication = new HashMap<>();
		postpartumComplication.put("postpartumComplicationID", "3");
		postpartumComplication.put("postpartumComplicationType", "Type3");
		postpartumComplications.add(postpartumComplication);
		history.setPostpartumComplicationList(postpartumComplications);

		ArrayList<Map<String, Object>> postAbortionComplications = new ArrayList<>();
		Map<String, Object> postAbortionComplication = new HashMap<>();
		postAbortionComplication.put("complicationID", 4.0);
		postAbortionComplication.put("complicationValue", "Type4");
		postAbortionComplications.add(postAbortionComplication);
		history.setPostAbortionComplication(postAbortionComplications);

		femaleObstetricHistoryList.add(history);

		ArrayList<FemaleObstetricHistory> result = commonNurseService
				.getFemaleObstetricHistoryObj(femaleObstetricHistoryList);
		assertEquals(1, result.size());
		assertEquals("1", result.get(0).getPregComplicationID());
		assertEquals("Type1", result.get(0).getPregComplicationType());
		assertEquals("2", result.get(0).getDeliveryComplicationID());
		assertEquals("Type2", result.get(0).getDeliveryComplicationType());
		assertEquals("3", result.get(0).getPostpartumComplicationID());
		assertEquals("Type3", result.get(0).getPostpartumComplicationType());
		assertEquals("4", result.get(0).getPostAbortionComplication_db());
		assertEquals("Type4", result.get(0).getPostAbortionComplicationsValues());
	}

	@Test
	public void testSaveBenMenstrualHistory_Success() {
		BenMenstrualDetails benMenstrualDetails = new BenMenstrualDetails();
		ArrayList<Map<String, Object>> menstrualProblemList = new ArrayList<>();
		Map<String, Object> problem = new HashMap<>();
		problem.put("menstrualProblemID", 1);
		problem.put("problemName", "Problem1");
		menstrualProblemList.add(problem);
		benMenstrualDetails.setMenstrualProblemList(menstrualProblemList);

		BenMenstrualDetails savedDetails = new BenMenstrualDetails();
		savedDetails.setBenMenstrualID(1);
		when(benMenstrualDetailsRepo.save(any(BenMenstrualDetails.class))).thenReturn(savedDetails);

		Integer result = commonNurseServiceImpl.saveBenMenstrualHistory(benMenstrualDetails);

		assertNotNull(result);
		assertEquals(1, result);
		verify(benMenstrualDetailsRepo, times(1)).save(any(BenMenstrualDetails.class));
	}

	@Test
	public void testSaveBenMenstrualHistory_EmptyList() {
		BenMenstrualDetails benMenstrualDetails = new BenMenstrualDetails();
		benMenstrualDetails.setMenstrualProblemList(new ArrayList<>());

		BenMenstrualDetails savedDetails = new BenMenstrualDetails();
		savedDetails.setBenMenstrualID(1);
		when(benMenstrualDetailsRepo.save(any(BenMenstrualDetails.class))).thenReturn(savedDetails);

		Integer result = commonNurseServiceImpl.saveBenMenstrualHistory(benMenstrualDetails);

		assertNotNull(result);
		assertEquals(1, result);
		verify(benMenstrualDetailsRepo, times(1)).save(any(BenMenstrualDetails.class));
	}

	@Test
	public void testSaveBenMenstrualHistory_NullList() {
		BenMenstrualDetails benMenstrualDetails = new BenMenstrualDetails();
		benMenstrualDetails.setMenstrualProblemList(null);

		BenMenstrualDetails savedDetails = new BenMenstrualDetails();
		savedDetails.setBenMenstrualID(1);
		when(benMenstrualDetailsRepo.save(any(BenMenstrualDetails.class))).thenReturn(savedDetails);

		Integer result = commonNurseServiceImpl.saveBenMenstrualHistory(benMenstrualDetails);

		assertNotNull(result);
		assertEquals(1, result);
		verify(benMenstrualDetailsRepo, times(1)).save(any(BenMenstrualDetails.class));
	}

	@Test
	public void testSaveBenFamilyHistory_Success() {
		ArrayList<BenFamilyHistory> familyHistoryList = new ArrayList<>();
		familyHistoryList.add(new BenFamilyHistory());
		benFamilyHistory.setBenFamilyHistory(familyHistoryList);

		when(benFamilyHistoryRepo.saveAll(familyHistoryList)).thenReturn(familyHistoryList);

		Long result = commonNurseService.saveBenFamilyHistory(benFamilyHistory);

		assertEquals(Long.valueOf(1), result);
		verify(benFamilyHistoryRepo, times(1)).saveAll(familyHistoryList);
	}

	@Test
	public void testSaveBenFamilyHistory_EmptyList() {
		ArrayList<BenFamilyHistory> familyHistoryList = new ArrayList<>();
		benFamilyHistory.setBenFamilyHistory(familyHistoryList);

		Long result = commonNurseService.saveBenFamilyHistory(benFamilyHistory);

		assertEquals(Long.valueOf(1), result);
		verify(benFamilyHistoryRepo, never()).saveAll(any());
	}

	@Test
	public void testSaveBenFamilyHistory_SaveFails() {
		ArrayList<BenFamilyHistory> familyHistoryList = new ArrayList<>();
		familyHistoryList.add(new BenFamilyHistory());
		benFamilyHistory.setBenFamilyHistory(familyHistoryList);

		when(benFamilyHistoryRepo.saveAll(familyHistoryList)).thenReturn(new ArrayList<>());

		Long result = commonNurseService.saveBenFamilyHistory(benFamilyHistory);

		assertNull(result);
		verify(benFamilyHistoryRepo, times(1)).saveAll(familyHistoryList);
	}

	@Test
	public void testSavePersonalHistory_EmptyPersonalHabits() {
		benPersonalHabit.setPersonalHistory(new ArrayList<>());

		Integer result = commonNurseServiceImpl.savePersonalHistory(benPersonalHabit);

		assertEquals(1, result);
		verify(benPersonalHabitRepo, never()).saveAll(anyList());
	}

	@Test
	public void testSavePersonalHistory_NonEmptyPersonalHabits_Success() {
		BenPersonalHabit habit1 = new BenPersonalHabit();
		BenPersonalHabit habit2 = new BenPersonalHabit();
		benPersonalHabit.setPersonalHistory(new ArrayList<>(Arrays.asList(habit1, habit2)));

		when(benPersonalHabitRepo.saveAll(anyList())).thenReturn(new ArrayList<>(Arrays.asList(habit1, habit2)));

		Integer result = commonNurseServiceImpl.savePersonalHistory(benPersonalHabit);

		assertEquals(1, result);
		verify(benPersonalHabitRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testSavePersonalHistory_NonEmptyPersonalHabits_Failure() {
		BenPersonalHabit habit1 = new BenPersonalHabit();
		BenPersonalHabit habit2 = new BenPersonalHabit();
		benPersonalHabit.setPersonalHistory(new ArrayList<>(Arrays.asList(habit1, habit2)));

		when(benPersonalHabitRepo.saveAll(anyList())).thenReturn(new ArrayList<>());

		Integer result = commonNurseServiceImpl.savePersonalHistory(benPersonalHabit);

		assertNull(result);
		verify(benPersonalHabitRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testSaveAllergyHistory_WithNonEmptyList() {
		ArrayList<BenAllergyHistory> allergyList = new ArrayList<>();
		allergyList.add(new BenAllergyHistory());
		benAllergyHistory.setBenAllergicHistory(allergyList);

		when(benAllergyHistoryRepo.saveAll(anyList())).thenReturn(allergyList);

		Long result = commonNurseServiceImpl.saveAllergyHistory(benAllergyHistory);

		assertNotNull(result);
		assertEquals(Long.valueOf(1), result);
		verify(benAllergyHistoryRepo, times(1)).saveAll(allergyList);
	}

	@Test
	public void testSaveAllergyHistory_WithEmptyList() {
		ArrayList<BenAllergyHistory> allergyList = new ArrayList<>();
		benAllergyHistory.setBenAllergicHistory(allergyList);

		Long result = commonNurseServiceImpl.saveAllergyHistory(benAllergyHistory);

		assertNotNull(result);
		assertEquals(Long.valueOf(1), result);
		verify(benAllergyHistoryRepo, never()).saveAll(anyList());
	}

	@Test
	public void testSaveAllergyHistory_SuccessfulSave() {
		ArrayList<BenAllergyHistory> allergyList = new ArrayList<>();
		allergyList.add(new BenAllergyHistory());
		benAllergyHistory.setBenAllergicHistory(allergyList);

		when(benAllergyHistoryRepo.saveAll(anyList())).thenReturn(allergyList);

		Long result = commonNurseServiceImpl.saveAllergyHistory(benAllergyHistory);

		assertNotNull(result);
		assertEquals(Long.valueOf(1), result);
		verify(benAllergyHistoryRepo, times(1)).saveAll(allergyList);
	}

	@Test
	public void testSaveChildOptionalVaccineDetail_EmptyList() {
		Long result = commonNurseServiceImpl.saveChildOptionalVaccineDetail(wrapperChildOptionalVaccineDetail);
		assertEquals(Long.valueOf(1), result);
		verify(childOptionalVaccineDetailRepo, never()).saveAll(anyList());
	}

	@Test
	public void testSaveChildOptionalVaccineDetail_NonEmptyList_Success() {
		ChildOptionalVaccineDetail detail = new ChildOptionalVaccineDetail();
		childOptionalVaccineDetails.add(detail);
		when(childOptionalVaccineDetailRepo.saveAll(anyList())).thenReturn(childOptionalVaccineDetails);

		Long result = commonNurseServiceImpl.saveChildOptionalVaccineDetail(wrapperChildOptionalVaccineDetail);
		assertEquals(Long.valueOf(1), result);
		verify(childOptionalVaccineDetailRepo, times(1)).saveAll(childOptionalVaccineDetails);
	}

	@Test
	public void testSaveChildOptionalVaccineDetail_NonEmptyList_Failure() {
		ChildOptionalVaccineDetail detail = new ChildOptionalVaccineDetail();
		childOptionalVaccineDetails.add(detail);
		when(childOptionalVaccineDetailRepo.saveAll(anyList())).thenReturn(new ArrayList<>());

		Long result = commonNurseServiceImpl.saveChildOptionalVaccineDetail(wrapperChildOptionalVaccineDetail);
		assertNull(result);
		verify(childOptionalVaccineDetailRepo, times(1)).saveAll(childOptionalVaccineDetails);
	}

	@Test
	public void testSaveImmunizationHistory() {
		when(childVaccineDetail1Repo.saveAll(childVaccineDetails)).thenReturn(childVaccineDetails);

		Long result = commonNurseServiceImpl.saveImmunizationHistory(wrapperImmunizationHistory);

		assertEquals(Long.valueOf(1), result);
		verify(childVaccineDetail1Repo, times(1)).saveAll(childVaccineDetails);
	}

	@Test
	public void testSaveBeneficiaryPhysicalAnthropometryDetails_Success() {
		when(benAnthropometryRepo.save(any(BenAnthropometryDetail.class))).thenReturn(benAnthropometryDetail);

		Long result = commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(benAnthropometryDetail);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(benAnthropometryRepo, times(1)).save(any(BenAnthropometryDetail.class));
	}

	@Test
	public void testSaveBeneficiaryPhysicalAnthropometryDetails_Failure() {
		when(benAnthropometryRepo.save(any(BenAnthropometryDetail.class))).thenReturn(null);

		Long result = commonNurseServiceImpl.saveBeneficiaryPhysicalAnthropometryDetails(benAnthropometryDetail);

		assertNull(result);
		verify(benAnthropometryRepo, times(1)).save(any(BenAnthropometryDetail.class));
	}

	@Test
	public void testSaveIDRSSuccess() {
		when(iDrsDataRepo.save(any(IDRSData.class))).thenReturn(idrsData);

		Long result = commonNurseServiceImpl.saveIDRS(idrsData);

		assertNotNull(result);
		assertEquals(idrsData.getId(), result);
		verify(iDrsDataRepo, times(1)).save(idrsData);
	}

	@Test
	public void testSaveIDRSFailure() {
		when(iDrsDataRepo.save(any(IDRSData.class))).thenReturn(null);

		Long result = commonNurseServiceImpl.saveIDRS(idrsData);

		assertNull(result);
		verify(iDrsDataRepo, times(1)).save(idrsData);
	}

	@Test
	public void testSavePhysicalActivity_Success() {
		when(physicalActivityTypeRepo.save(any(PhysicalActivityType.class))).thenReturn(physicalActivityType);

		Long result = commonNurseServiceImpl.savePhysicalActivity(physicalActivityType);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(physicalActivityTypeRepo, times(1)).save(any(PhysicalActivityType.class));
	}

	@Test
	public void testSavePhysicalActivity_Failure() {
		when(physicalActivityTypeRepo.save(any(PhysicalActivityType.class))).thenReturn(null);

		Long result = commonNurseServiceImpl.savePhysicalActivity(physicalActivityType);

		assertNull(result);
		verify(physicalActivityTypeRepo, times(1)).save(any(PhysicalActivityType.class));
	}

	@Test
	public void testSaveBenFamilyHistoryNCDScreening_Success() {
		familyHistoryList.add(benFamilyHistory);
		benFamilyHistory.setBenFamilyHist(familyHistoryList);

		when(benFamilyHistoryRepo.saveAll(familyHistoryList)).thenReturn(familyHistoryList);

		Long result = commonNurseServiceImpl.saveBenFamilyHistoryNCDScreening(benFamilyHistory);

		assertEquals(Long.valueOf(1), result);
		verify(benFamilyHistoryRepo, times(1)).saveAll(familyHistoryList);
	}

	@Test
	public void testSaveBenFamilyHistoryNCDScreening_EmptyList() {
		benFamilyHistory.setBenFamilyHist(familyHistoryList);

		Long result = commonNurseServiceImpl.saveBenFamilyHistoryNCDScreening(benFamilyHistory);

		assertEquals(Long.valueOf(1), result);
		verify(benFamilyHistoryRepo, never()).saveAll(anyList());
	}

	@Test
	public void testSaveBenFamilyHistoryNCDScreening_SaveFails() {
		familyHistoryList.add(benFamilyHistory);
		benFamilyHistory.setBenFamilyHist(familyHistoryList);

		when(benFamilyHistoryRepo.saveAll(familyHistoryList)).thenReturn(new ArrayList<>());

		Long result = commonNurseServiceImpl.saveBenFamilyHistoryNCDScreening(benFamilyHistory);

		assertNull(result);
		verify(benFamilyHistoryRepo, times(1)).saveAll(familyHistoryList);
	}

	@Test
	public void testSaveBeneficiaryPhysicalVitalDetails_AllReadingsProvided() {
		BenPhysicalVitalDetail detail = new BenPhysicalVitalDetail();
		detail.setSystolicBP_1stReading((short) 120);
		detail.setDiastolicBP_1stReading((short) 80);
		detail.setSystolicBP_2ndReading((short) 130);
		detail.setDiastolicBP_2ndReading((short) 85);
		detail.setSystolicBP_3rdReading((short) 125);
		detail.setDiastolicBP_3rdReading((short) 82);

		when(benPhysicalVitalRepo.save(any(BenPhysicalVitalDetail.class))).thenReturn(detail);

		Long result = commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(detail);

		assertNotNull(result);
		assertEquals(125, detail.getAverageSystolicBP());
		assertEquals(82, detail.getAverageDiastolicBP());
		verify(benPhysicalVitalRepo, times(1)).save(detail);
	}

	@Test
	public void testSaveBeneficiaryPhysicalVitalDetails_TwoReadingsProvided() {
		BenPhysicalVitalDetail detail = new BenPhysicalVitalDetail();
		detail.setSystolicBP_1stReading((short) 120);
		detail.setDiastolicBP_1stReading((short) 80);
		detail.setSystolicBP_2ndReading((short) 130);
		detail.setDiastolicBP_2ndReading((short) 85);

		when(benPhysicalVitalRepo.save(any(BenPhysicalVitalDetail.class))).thenReturn(detail);

		Long result = commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(detail);

		assertNotNull(result);
		assertEquals(125, detail.getAverageSystolicBP());
		assertEquals(82, detail.getAverageDiastolicBP());
		verify(benPhysicalVitalRepo, times(1)).save(detail);
	}

	@Test
	public void testSaveBeneficiaryPhysicalVitalDetails_OneReadingProvided() {
		BenPhysicalVitalDetail detail = new BenPhysicalVitalDetail();
		detail.setSystolicBP_1stReading((short) 120);
		detail.setDiastolicBP_1stReading((short) 80);

		when(benPhysicalVitalRepo.save(any(BenPhysicalVitalDetail.class))).thenReturn(detail);

		Long result = commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(detail);

		assertNotNull(result);
		assertEquals(120, detail.getAverageSystolicBP());
		assertEquals(80, detail.getAverageDiastolicBP());
		verify(benPhysicalVitalRepo, times(1)).save(detail);
	}

	@Test
	public void testSaveBeneficiaryPhysicalVitalDetails_SaveSuccess() {
		BenPhysicalVitalDetail detail = new BenPhysicalVitalDetail();
		detail.setID(1L);

		when(benPhysicalVitalRepo.save(any(BenPhysicalVitalDetail.class))).thenReturn(detail);

		Long result = commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(detail);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(benPhysicalVitalRepo, times(1)).save(detail);
	}

	@Test
	public void testSaveBeneficiaryPhysicalVitalDetails_SaveFailure() {
		BenPhysicalVitalDetail detail = new BenPhysicalVitalDetail();

		when(benPhysicalVitalRepo.save(any(BenPhysicalVitalDetail.class))).thenReturn(null);

		Long result = commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(detail);

		assertNull(result);
		verify(benPhysicalVitalRepo, times(1)).save(detail);
	}

	@Test
	public void testGetBeneficiaryPhysicalAnthropometryDetails_Success() {
		when(benAnthropometryRepo.getBenAnthropometryDetail(beneficiaryRegID, visitCode))
				.thenReturn(benAnthropometryDetail);

		String response = commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);

		assertNotNull(response);
		assertTrue(response.contains("\"beneficiaryRegID\":1"));
		verify(benAnthropometryRepo, times(1)).getBenAnthropometryDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryPhysicalAnthropometryDetails_NoData() {
		when(benAnthropometryRepo.getBenAnthropometryDetail(beneficiaryRegID, visitCode)).thenReturn(null);

		String response = commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID,
				visitCode);

		assertNotNull(response);
		assertEquals("null", response);
		verify(benAnthropometryRepo, times(1)).getBenAnthropometryDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryPhysicalAnthropometryDetails_Exception() {
		when(benAnthropometryRepo.getBenAnthropometryDetail(beneficiaryRegID, visitCode))
				.thenThrow(new RuntimeException("Database error"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonNurseServiceImpl.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode);
		});

		assertEquals("Database error", exception.getMessage());
		verify(benAnthropometryRepo, times(1)).getBenAnthropometryDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBenCdssDetails_Success() {
		when(cdssRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode)).thenReturn(cdss);

		String response = commonNurseServiceImpl.getBenCdssDetails(benRegID, visitCode);

		assertNotNull(response);
		assertTrue(response.contains("\"beneficiaryRegID\":1"));
		assertTrue(response.contains("\"visitCode\":1"));
		verify(cdssRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
	}

	@Test
	public void testGetBenCdssDetails_NoData() {
		when(cdssRepo.findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode)).thenReturn(null);

		String response = commonNurseServiceImpl.getBenCdssDetails(benRegID, visitCode);

		assertNotNull(response);
		assertEquals("null", response);
		verify(cdssRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(benRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryPhysicalVitalDetails_Success() {
		when(benPhysicalVitalRepo.getBenPhysicalVitalDetail(beneficiaryRegID, visitCode))
				.thenReturn(benPhysicalVitalDetail);

		String response = commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);

		assertNotNull(response);
		assertTrue(response.contains("\"beneficiaryRegID\":1"));
		assertTrue(response.contains("\"visitCode\":1"));
		verify(benPhysicalVitalRepo, times(1)).getBenPhysicalVitalDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryPhysicalVitalDetails_NoData() {
		when(benPhysicalVitalRepo.getBenPhysicalVitalDetail(beneficiaryRegID, visitCode)).thenReturn(null);

		String response = commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);

		assertNotNull(response);
		assertEquals("null", response);
		verify(benPhysicalVitalRepo, times(1)).getBenPhysicalVitalDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testUpdateANCAnthropometryDetails_Success() throws IEMRException {
		BenAnthropometryDetail detail = new BenAnthropometryDetail();
		detail.setBeneficiaryRegID(1L);
		detail.setVisitCode(1L);
		detail.setWeight_Kg(70.0);
		detail.setHeight_cm(170.0);
		detail.setbMI(24.2);
		detail.setHeadCircumference_cm(50.0);
		detail.setMidUpperArmCircumference_MUAC_cm(30.0);
		detail.setHipCircumference_cm(90.0);
		detail.setWaistCircumference_cm(80.0);
		detail.setWaistHipRatio(0.89);
		detail.setModifiedBy("testUser");

		when(benAnthropometryRepo.getBenAnthropometryStatus(1L, 1L)).thenReturn("N");
		when(benAnthropometryRepo.updateANCCareDetails(anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(),
				anyDouble(), anyDouble(), anyDouble(), anyString(), anyString(), anyLong(), anyLong())).thenReturn(1);

		int result = commonNurseServiceImpl.updateANCAnthropometryDetails(detail);

		assertEquals(1, result);
		verify(benAnthropometryRepo, times(1)).updateANCCareDetails(anyDouble(), anyDouble(), anyDouble(), anyDouble(),
				anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyString(), anyString(), anyLong(), anyLong());
	}

	@Test
	public void testUpdateANCAnthropometryDetails_NullInput() throws IEMRException {
		int result = commonNurseServiceImpl.updateANCAnthropometryDetails(null);

		assertEquals(0, result);
		verify(benAnthropometryRepo, times(0)).updateANCCareDetails(anyDouble(), anyDouble(), anyDouble(), anyDouble(),
				anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyString(), anyString(), anyLong(), anyLong());
	}

	@Test
	public void testUpdateANCAnthropometryDetails_Exception() throws IEMRException {
		BenAnthropometryDetail detail = new BenAnthropometryDetail();
		detail.setBeneficiaryRegID(1L);
		detail.setVisitCode(1L);

		when(benAnthropometryRepo.getBenAnthropometryStatus(1L, 1L)).thenThrow(new IEMRException("Test Exception"));

		assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.updateANCAnthropometryDetails(detail);
		});

		verify(benAnthropometryRepo, times(1)).getBenAnthropometryStatus(1L, 1L);
	}

	@Test
	public void testUpdateANCPhysicalVitalDetails_Success() throws IEMRException {
		when(benPhysicalVitalRepo.getBenPhysicalVitalStatus(1L, 1L)).thenReturn("Y");
		when(benPhysicalVitalRepo.updatePhysicalVitalDetails(anyDouble(), anyInt(), anyInt(), anyShort(), anyShort(),
				anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyInt(), anyString(),
				anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyInt(), anyString(), anyInt(), anyString(),
				anyString(), anyString(), anyDouble(), anyLong(), anyLong(), anyDouble())).thenReturn(1);

		int result = commonNurseServiceImpl.updateANCPhysicalVitalDetails(physicalVitalDetail);

		assertEquals(1, result);
		verify(benPhysicalVitalRepo, times(1)).getBenPhysicalVitalStatus(1L, 1L);
		verify(benPhysicalVitalRepo, times(1)).updatePhysicalVitalDetails(anyDouble(), anyInt(), anyInt(), anyShort(),
				anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyInt(),
				anyString(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyInt(), anyString(), anyInt(),
				anyString(), anyString(), anyString(), anyDouble(), anyLong(), anyLong(), anyDouble());
	}

	@Test
	public void testUpdateANCPhysicalVitalDetails_NullInput() throws IEMRException {
		int result = commonNurseServiceImpl.updateANCPhysicalVitalDetails(null);

		assertEquals(0, result);
		verify(benPhysicalVitalRepo, times(0)).getBenPhysicalVitalStatus(anyLong(), anyLong());
		verify(benPhysicalVitalRepo, times(0)).updatePhysicalVitalDetails(anyDouble(), anyInt(), anyInt(), anyShort(),
				anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyInt(),
				anyString(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyInt(), anyString(), anyInt(),
				anyString(), anyString(), anyString(), anyDouble(), anyLong(), anyLong(), anyDouble());
	}

	@Test
	public void testUpdateANCPhysicalVitalDetails_Exception() throws IEMRException {
		when(benPhysicalVitalRepo.getBenPhysicalVitalStatus(1L, 1L)).thenThrow(new IEMRException("Test Exception"));

		assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.updateANCPhysicalVitalDetails(physicalVitalDetail);
		});

		verify(benPhysicalVitalRepo, times(1)).getBenPhysicalVitalStatus(1L, 1L);
		verify(benPhysicalVitalRepo, times(0)).updatePhysicalVitalDetails(anyDouble(), anyInt(), anyInt(), anyShort(),
				anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyShort(), anyInt(),
				anyString(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyInt(), anyString(), anyInt(),
				anyString(), anyString(), anyString(), anyDouble(), anyLong(), anyLong(), anyDouble());
	}

	@Test
	public void testSavePhyGeneralExamination_Success() throws IEMRException {
		generalExamination.setTypeOfDangerSigns(Arrays.asList("Sign1", "Sign2"));
		when(phyGeneralExaminationRepo.save(any(PhyGeneralExamination.class))).thenReturn(generalExamination);

		Long result = commonNurseServiceImpl.savePhyGeneralExamination(generalExamination);

		assertNotNull(result);
		assertEquals(1L, result);
		assertEquals("Sign1,Sign2,", generalExamination.getTypeOfDangerSign());
		verify(phyGeneralExaminationRepo, times(1)).save(any(PhyGeneralExamination.class));
	}

	@Test
	public void testSavePhyGeneralExamination_NoDangerSigns() throws IEMRException {
		generalExamination.setTypeOfDangerSigns(Collections.emptyList());
		when(phyGeneralExaminationRepo.save(any(PhyGeneralExamination.class))).thenReturn(generalExamination);

		Long result = commonNurseServiceImpl.savePhyGeneralExamination(generalExamination);

		assertNotNull(result);
		assertEquals(1L, result);
		assertNull(generalExamination.getTypeOfDangerSign());
		verify(phyGeneralExaminationRepo, times(1)).save(any(PhyGeneralExamination.class));
	}

	@Test
	public void testSavePhyGeneralExamination_SaveFailure() {
		generalExamination.setTypeOfDangerSigns(Arrays.asList("Sign1", "Sign2"));
		when(phyGeneralExaminationRepo.save(any(PhyGeneralExamination.class))).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.savePhyGeneralExamination(generalExamination);
		});

		assertEquals("DB-Error in saving General Examination", exception.getMessage());
		verify(phyGeneralExaminationRepo, times(1)).save(any(PhyGeneralExamination.class));
	}

	@Test
	public void testSavePhyGeneralExamination_Success() throws IEMRException {
		generalExamination.setTypeOfDangerSigns(Arrays.asList("Sign1", "Sign2"));
		when(phyGeneralExaminationRepo.save(any(PhyGeneralExamination.class))).thenReturn(generalExamination);

		Long result = commonNurseServiceImpl.savePhyGeneralExamination(generalExamination);

		assertNotNull(result);
		assertEquals(1L, result);
		assertEquals("Sign1,Sign2,", generalExamination.getTypeOfDangerSign());
		verify(phyGeneralExaminationRepo, times(1)).save(any(PhyGeneralExamination.class));
	}

	@Test
	public void testSavePhyGeneralExamination_NoDangerSigns() throws IEMRException {
		generalExamination.setTypeOfDangerSigns(Collections.emptyList());
		when(phyGeneralExaminationRepo.save(any(PhyGeneralExamination.class))).thenReturn(generalExamination);

		Long result = commonNurseServiceImpl.savePhyGeneralExamination(generalExamination);

		assertNotNull(result);
		assertEquals(1L, result);
		assertNull(generalExamination.getTypeOfDangerSign());
		verify(phyGeneralExaminationRepo, times(1)).save(any(PhyGeneralExamination.class));
	}

	@Test
	public void testSavePhyGeneralExamination_SaveFailure() {
		generalExamination.setTypeOfDangerSigns(Arrays.asList("Sign1", "Sign2"));
		when(phyGeneralExaminationRepo.save(any(PhyGeneralExamination.class))).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.savePhyGeneralExamination(generalExamination);
		});

		assertEquals("DB-Error in saving General Examination", exception.getMessage());
		verify(phyGeneralExaminationRepo, times(1)).save(any(PhyGeneralExamination.class));
	}

	@Test
	public void testSaveSysGastrointestinalExamination_Success() throws IEMRException {
		when(sysGastrointestinalExaminationRepo.save(any(SysGastrointestinalExamination.class)))
				.thenReturn(gastrointestinalExamination);

		Long result = commonNurseServiceImpl.saveSysGastrointestinalExamination(gastrointestinalExamination);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(sysGastrointestinalExaminationRepo, times(1)).save(any(SysGastrointestinalExamination.class));
	}

	@Test
	public void testSaveSysGastrointestinalExamination_SaveFailure() {
		when(sysGastrointestinalExaminationRepo.save(any(SysGastrointestinalExamination.class))).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.saveSysGastrointestinalExamination(gastrointestinalExamination);
		});

		assertEquals("DB-Error in saving Gastrointestinal Examination", exception.getMessage());
		verify(sysGastrointestinalExaminationRepo, times(1)).save(any(SysGastrointestinalExamination.class));
	}

	@Test
	public void testSaveSysGastrointestinalExamination_InvalidID() {
		gastrointestinalExamination.setID(0L);
		when(sysGastrointestinalExaminationRepo.save(any(SysGastrointestinalExamination.class)))
				.thenReturn(gastrointestinalExamination);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.saveSysGastrointestinalExamination(gastrointestinalExamination);
		});

		assertEquals("DB-Error in saving Gastrointestinal Examination", exception.getMessage());
		verify(sysGastrointestinalExaminationRepo, times(1)).save(any(SysGastrointestinalExamination.class));
	}

	@Test
	public void testSaveSysCardiovascularExamination_Success() throws IEMRException {
		when(sysCardiovascularExaminationRepo.save(any(SysCardiovascularExamination.class)))
				.thenReturn(cardiovascularExamination);

		Long result = commonNurseServiceImpl.saveSysCardiovascularExamination(cardiovascularExamination);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(sysCardiovascularExaminationRepo, times(1)).save(any(SysCardiovascularExamination.class));
	}

	@Test
	public void testSaveSysCardiovascularExamination_SaveFailure() {
		when(sysCardiovascularExaminationRepo.save(any(SysCardiovascularExamination.class))).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.saveSysCardiovascularExamination(cardiovascularExamination);
		});

		assertEquals("DB-Error in saving Cardiovascular Examination", exception.getMessage());
		verify(sysCardiovascularExaminationRepo, times(1)).save(any(SysCardiovascularExamination.class));
	}

	@Test
	public void testSaveSysCardiovascularExamination_InvalidID() {
		cardiovascularExamination.setID(0L);
		when(sysCardiovascularExaminationRepo.save(any(SysCardiovascularExamination.class)))
				.thenReturn(cardiovascularExamination);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.saveSysCardiovascularExamination(cardiovascularExamination);
		});

		assertEquals("DB-Error in saving Cardiovascular Examination", exception.getMessage());
		verify(sysCardiovascularExaminationRepo, times(1)).save(any(SysCardiovascularExamination.class));
	}

	@Test
	public void testSaveSysRespiratoryExamination_Success() throws IEMRException {
		when(sysRespiratoryExaminationRepo.save(any(SysRespiratoryExamination.class)))
				.thenReturn(sysRespiratoryExamination);

		Long result = commonNurseServiceImpl.saveSysRespiratoryExamination(sysRespiratoryExamination);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(sysRespiratoryExaminationRepo, times(1)).save(any(SysRespiratoryExamination.class));
	}

	@Test
	public void testSaveSysRespiratoryExamination_NullResponse() {
		when(sysRespiratoryExaminationRepo.save(any(SysRespiratoryExamination.class))).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.saveSysRespiratoryExamination(sysRespiratoryExamination);
		});

		assertEquals("DB-Error in saving Respiratory Examination", exception.getMessage());
		verify(sysRespiratoryExaminationRepo, times(1)).save(any(SysRespiratoryExamination.class));
	}

	@Test
	public void testSaveSysRespiratoryExamination_Exception() {
		when(sysRespiratoryExaminationRepo.save(any(SysRespiratoryExamination.class)))
				.thenThrow(new RuntimeException("Database error"));

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.saveSysRespiratoryExamination(sysRespiratoryExamination);
		});

		assertEquals("DB-Error in saving Respiratory Examination", exception.getMessage());
		verify(sysRespiratoryExaminationRepo, times(1)).save(any(SysRespiratoryExamination.class));
	}

	@Test
	public void testSaveSysCentralNervousExamination_Success() throws IEMRException {
		when(sysCentralNervousExaminationRepo.save(any(SysCentralNervousExamination.class)))
				.thenReturn(sysCentralNervousExamination);

		Long result = commonNurseServiceImpl.saveSysCentralNervousExamination(sysCentralNervousExamination);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(sysCentralNervousExaminationRepo, times(1)).save(any(SysCentralNervousExamination.class));
	}

	@Test
	public void testSaveSysCentralNervousExamination_DBError() {
		when(sysCentralNervousExaminationRepo.save(any(SysCentralNervousExamination.class))).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.saveSysCentralNervousExamination(sysCentralNervousExamination);
		});

		assertEquals("DB-Error in saving Central Nervous Examination", exception.getMessage());
		verify(sysCentralNervousExaminationRepo, times(1)).save(any(SysCentralNervousExamination.class));
	}

	@Test
	public void testSaveSysMusculoskeletalSystemExamination_Success() throws IEMRException {
		when(sysMusculoskeletalSystemExaminationRepo.save(any(SysMusculoskeletalSystemExamination.class)))
				.thenReturn(musculoskeletalSystemExamination);

		Long result = commonNurseServiceImpl.saveSysMusculoskeletalSystemExamination(musculoskeletalSystemExamination);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(sysMusculoskeletalSystemExaminationRepo, times(1)).save(any(SysMusculoskeletalSystemExamination.class));
	}

	@Test
	public void testSaveSysMusculoskeletalSystemExamination_Failure() {
		when(sysMusculoskeletalSystemExaminationRepo.save(any(SysMusculoskeletalSystemExamination.class)))
				.thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.saveSysMusculoskeletalSystemExamination(musculoskeletalSystemExamination);
		});

		assertEquals("DB-Error in saving Musculoskeletal System Examination", exception.getMessage());
		verify(sysMusculoskeletalSystemExaminationRepo, times(1)).save(any(SysMusculoskeletalSystemExamination.class));
	}

	@Test
	public void testSaveSysGenitourinarySystemExamination_Success() throws IEMRException {
		when(sysGenitourinarySystemExaminationRepo.save(any(SysGenitourinarySystemExamination.class)))
				.thenReturn(examination);

		Long result = commonNurseServiceImpl.saveSysGenitourinarySystemExamination(examination);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(sysGenitourinarySystemExaminationRepo, times(1)).save(any(SysGenitourinarySystemExamination.class));
	}

	@Test
	public void testSaveSysGenitourinarySystemExamination_NullID() {
		examination.setID(null);
		when(sysGenitourinarySystemExaminationRepo.save(any(SysGenitourinarySystemExamination.class)))
				.thenReturn(examination);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.saveSysGenitourinarySystemExamination(examination);
		});

		assertEquals("DB-Error in saving Genitourinary System Examination", exception.getMessage());
		verify(sysGenitourinarySystemExaminationRepo, times(1)).save(any(SysGenitourinarySystemExamination.class));
	}

	@Test
	public void testSaveSysGenitourinarySystemExamination_Exception() {
		when(sysGenitourinarySystemExaminationRepo.save(any(SysGenitourinarySystemExamination.class)))
				.thenThrow(new RuntimeException("DB error"));

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.saveSysGenitourinarySystemExamination(examination);
		});

		assertEquals("DB-Error in saving Genitourinary System Examination", exception.getMessage());
		verify(sysGenitourinarySystemExaminationRepo, times(1)).save(any(SysGenitourinarySystemExamination.class));
	}

	@Test
	public void testFetchBenPastMedicalHistory_WithData() {
		when(benMedHistoryRepo.getBenPastHistory(benRegID)).thenReturn(benPastHistoryDataArray);

		String result = commonNurseServiceImpl.fetchBenPastMedicalHistory(benRegID);

		assertNotNull(result);
		assertTrue(result.contains("Illness1"));
		verify(benMedHistoryRepo, times(1)).getBenPastHistory(benRegID);
	}

	@Test
	public void testFetchBenPastMedicalHistory_NoData() {
		when(benMedHistoryRepo.getBenPastHistory(benRegID)).thenReturn(new ArrayList<>());

		String result = commonNurseServiceImpl.fetchBenPastMedicalHistory(benRegID);

		assertNotNull(result);
		assertTrue(result.contains("\"data\":[]"));
		verify(benMedHistoryRepo, times(1)).getBenPastHistory(benRegID);
	}

	@Test
	public void testFetchBenPastMedicalHistory_NullData() {
		when(benMedHistoryRepo.getBenPastHistory(benRegID)).thenReturn(null);

		String result = commonNurseServiceImpl.fetchBenPastMedicalHistory(benRegID);

		assertNotNull(result);
		assertTrue(result.contains("\"data\":[]"));
		verify(benMedHistoryRepo, times(1)).getBenPastHistory(benRegID);
	}

	@Test
	public void testFetchBenPersonalTobaccoHistory() {
		when(benPersonalHabitRepo.getBenPersonalTobaccoHabitDetail(beneficiaryRegID)).thenReturn(benPersonalHabits);

		String response = commonNurseServiceImpl.fetchBenPersonalTobaccoHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, Map.class);
		assertNotNull(responseMap);
		assertTrue(responseMap.containsKey("columns"));
		assertTrue(responseMap.containsKey("data"));

		List<Map<String, Object>> columns = (List<Map<String, Object>>) responseMap.get("columns");
		assertEquals(10, columns.size());

		List<BenPersonalHabit> data = (List<BenPersonalHabit>) responseMap.get("data");
		assertEquals(1, data.size());
		assertEquals("DietaryType1", data.get(0).getDietaryType());

		verify(benPersonalHabitRepo, times(1)).getBenPersonalTobaccoHabitDetail(beneficiaryRegID);
	}

	@Test
	public void testFetchBenPersonalAlcoholHistory_WithData() {
		when(benPersonalHabitRepo.getBenPersonalAlcoholHabitDetail(beneficiaryRegID)).thenReturn(benPersonalHabits);

		String response = commonNurseServiceImpl.fetchBenPersonalAlcoholHistory(beneficiaryRegID);

		assertNotNull(response);
		assertTrue(response.contains("DietaryType"));
		verify(benPersonalHabitRepo, times(1)).getBenPersonalAlcoholHabitDetail(beneficiaryRegID);
	}

	@Test
	public void testFetchBenPersonalAlcoholHistory_NoData() {
		when(benPersonalHabitRepo.getBenPersonalAlcoholHabitDetail(beneficiaryRegID)).thenReturn(null);

		String response = commonNurseServiceImpl.fetchBenPersonalAlcoholHistory(beneficiaryRegID);

		assertNotNull(response);
		assertTrue(response.contains("\"data\":[]"));
		verify(benPersonalHabitRepo, times(1)).getBenPersonalAlcoholHabitDetail(beneficiaryRegID);
	}

	@Test
	public void testFetchBenPersonalAlcoholHistory_EmptyData() {
		when(benPersonalHabitRepo.getBenPersonalAlcoholHabitDetail(beneficiaryRegID)).thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.fetchBenPersonalAlcoholHistory(beneficiaryRegID);

		assertNotNull(response);
		assertTrue(response.contains("\"data\":[]"));
		verify(benPersonalHabitRepo, times(1)).getBenPersonalAlcoholHabitDetail(beneficiaryRegID);
	}

	@Test
	public void testFetchBenPersonalAllergyHistory_ValidData() {
		when(benAllergyHistoryRepo.getBenPersonalAllergyDetail(beneficiaryRegID)).thenReturn(benAllergyHistoryData);

		String response = commonNurseServiceImpl.fetchBenPersonalAllergyHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, HashMap.class);
		assertNotNull(responseMap);
		assertTrue(responseMap.containsKey("columns"));
		assertTrue(responseMap.containsKey("data"));

		List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");
		assertEquals(1, data.size());
	}

	@Test
	public void testFetchBenPersonalAllergyHistory_EmptyData() {
		when(benAllergyHistoryRepo.getBenPersonalAllergyDetail(beneficiaryRegID)).thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.fetchBenPersonalAllergyHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, HashMap.class);
		assertNotNull(responseMap);
		assertTrue(responseMap.containsKey("columns"));
		assertTrue(responseMap.containsKey("data"));

		List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");
		assertEquals(0, data.size());
	}

	@Test
	public void testFetchBenPersonalAllergyHistory_NullData() {
		when(benAllergyHistoryRepo.getBenPersonalAllergyDetail(beneficiaryRegID)).thenReturn(null);

		String response = commonNurseServiceImpl.fetchBenPersonalAllergyHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, HashMap.class);
		assertNotNull(responseMap);
		assertTrue(responseMap.containsKey("columns"));
		assertTrue(responseMap.containsKey("data"));

		List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");
		assertEquals(0, data.size());
	}

	@Test
	public void testFetchBenPersonalMedicationHistory_NonEmptyList() {
		when(benMedicationHistoryRepo.getBenMedicationHistoryDetail(beneficiaryRegID))
				.thenReturn(medicationHistoryData);

		String response = commonNurseServiceImpl.fetchBenPersonalMedicationHistory(beneficiaryRegID);

		assertNotNull(response);
		assertTrue(response.contains("Medication1"));
		assertTrue(response.contains("Medication2"));

		verify(benMedicationHistoryRepo, times(1)).getBenMedicationHistoryDetail(beneficiaryRegID);
	}

	@Test
	public void testFetchBenPersonalMedicationHistory_EmptyList() {
		when(benMedicationHistoryRepo.getBenMedicationHistoryDetail(beneficiaryRegID)).thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.fetchBenPersonalMedicationHistory(beneficiaryRegID);

		assertNotNull(response);
		assertTrue(response.contains("\"data\":[]"));

		verify(benMedicationHistoryRepo, times(1)).getBenMedicationHistoryDetail(beneficiaryRegID);
	}

	@Test
	public void testFetchBenPersonalMedicationHistory_NullList() {
		when(benMedicationHistoryRepo.getBenMedicationHistoryDetail(beneficiaryRegID)).thenReturn(null);

		String response = commonNurseServiceImpl.fetchBenPersonalMedicationHistory(beneficiaryRegID);

		assertNotNull(response);
		assertTrue(response.contains("\"data\":[]"));

		verify(benMedicationHistoryRepo, times(1)).getBenMedicationHistoryDetail(beneficiaryRegID);
	}

	@Test
	public void testFetchBenPersonalFamilyHistory_withData() {
		List<Object[]> mockData = new ArrayList<>();
		mockData.add(new Object[] { new Date(), "DiseaseType1", "OtherDiseaseType1", "FamilyMember1", true,
				"GeneticDisorder1", true });
		when(benFamilyHistoryRepo.getBenFamilyHistoryDetail(beneficiaryRegID)).thenReturn(mockData);

		String response = commonNurseServiceImpl.fetchBenPersonalFamilyHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, Map.class);
		List<Map<String, Object>> columns = (List<Map<String, Object>>) responseMap.get("columns");
		List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");

		assertNotNull(columns);
		assertNotNull(data);
		assertEquals(8, columns.size());
		assertEquals(1, data.size());
		assertEquals("DiseaseType1", data.get(0).get("diseaseType"));
	}

	@Test
	public void testFetchBenPersonalFamilyHistory_noData() {
		when(benFamilyHistoryRepo.getBenFamilyHistoryDetail(beneficiaryRegID)).thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.fetchBenPersonalFamilyHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, Map.class);
		List<Map<String, Object>> columns = (List<Map<String, Object>>) responseMap.get("columns");
		List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");

		assertNotNull(columns);
		assertNotNull(data);
		assertEquals(8, columns.size());
		assertEquals(0, data.size());
	}

	@Test
	public void testFetchBenPersonalFamilyHistory_withNullValues() {
		List<Object[]> mockData = new ArrayList<>();
		mockData.add(new Object[] { null, null, null, null, null, null, null });
		when(benFamilyHistoryRepo.getBenFamilyHistoryDetail(beneficiaryRegID)).thenReturn(mockData);

		String response = commonNurseServiceImpl.fetchBenPersonalFamilyHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, Map.class);
		List<Map<String, Object>> columns = (List<Map<String, Object>>) responseMap.get("columns");
		List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");

		assertNotNull(columns);
		assertNotNull(data);
		assertEquals(8, columns.size());
		assertEquals(1, data.size());
		assertNull(data.get(0).get("diseaseType"));
	}

	@Test
	public void testFetchBenPhysicalHistory_withData() {
		List<Object[]> mockData = new ArrayList<>();
		mockData.add(new Object[] { new Date(), "Activity1", "Type1" });
		mockData.add(new Object[] { new Date(), "Activity2", "Type2" });

		when(physicalActivityTypeRepo.getBenPhysicalHistoryDetail(beneficiaryRegID)).thenReturn(mockData);

		String response = commonNurseServiceImpl.fetchBenPhysicalHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, Map.class);
		List<Map<String, Object>> columns = (List<Map<String, Object>>) responseMap.get("columns");
		List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");

		assertEquals(3, columns.size());
		assertEquals(2, data.size());
		assertEquals("Activity1", data.get(0).get("activityType"));
		assertEquals("Type1", data.get(0).get("physicalActivityType"));

		verify(physicalActivityTypeRepo, times(1)).getBenPhysicalHistoryDetail(beneficiaryRegID);
	}

	@Test
	public void testFetchBenPhysicalHistory_noData() {
		when(physicalActivityTypeRepo.getBenPhysicalHistoryDetail(beneficiaryRegID)).thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.fetchBenPhysicalHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, Map.class);
		List<Map<String, Object>> columns = (List<Map<String, Object>>) responseMap.get("columns");
		List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");

		assertEquals(3, columns.size());
		assertTrue(data.isEmpty());

		verify(physicalActivityTypeRepo, times(1)).getBenPhysicalHistoryDetail(beneficiaryRegID);
	}

	@Test
	public void testFetchBenPhysicalHistory_withNullValues() {
		List<Object[]> mockData = new ArrayList<>();
		mockData.add(new Object[] { null, null, null });

		when(physicalActivityTypeRepo.getBenPhysicalHistoryDetail(beneficiaryRegID)).thenReturn(mockData);

		String response = commonNurseServiceImpl.fetchBenPhysicalHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, Map.class);
		List<Map<String, Object>> columns = (List<Map<String, Object>>) responseMap.get("columns");
		List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");

		assertEquals(3, columns.size());
		assertEquals(1, data.size());
		assertNull(data.get(0).get("activityType"));
		assertNull(data.get(0).get("physicalActivityType"));

		verify(physicalActivityTypeRepo, times(1)).getBenPhysicalHistoryDetail(beneficiaryRegID);
	}

	@Test
	public void testFetchBenMenstrualHistory_ValidData() {
		when(benMenstrualDetailsRepo.getBenMenstrualDetail(beneficiaryRegID)).thenReturn(benMenstrualDetailsList);

		String response = commonNurseServiceImpl.fetchBenMenstrualHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, HashMap.class);
		assertNotNull(responseMap);
		assertTrue(responseMap.containsKey("columns"));
		assertTrue(responseMap.containsKey("data"));
		assertEquals(1, ((List<?>) responseMap.get("data")).size());
	}

	@Test
	public void testFetchBenMenstrualHistory_NoData() {
		when(benMenstrualDetailsRepo.getBenMenstrualDetail(beneficiaryRegID)).thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.fetchBenMenstrualHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, HashMap.class);
		assertNotNull(responseMap);
		assertTrue(responseMap.containsKey("columns"));
		assertTrue(responseMap.containsKey("data"));
		assertEquals(0, ((List<?>) responseMap.get("data")).size());
	}

	@Test
	public void testFetchBenMenstrualHistory_Exception() {
		when(benMenstrualDetailsRepo.getBenMenstrualDetail(beneficiaryRegID))
				.thenThrow(new RuntimeException("Database error"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonNurseServiceImpl.fetchBenMenstrualHistory(beneficiaryRegID);
		});

		assertEquals("Database error", exception.getMessage());
	}

	@Test
	public void testFetchBenPastObstetricHistory_withValidData() {
		when(femaleObstetricHistoryRepo.getBenFemaleObstetricHistoryDetail(beneficiaryRegID)).thenReturn(mockData);

		String response = commonNurseServiceImpl.fetchBenPastObstetricHistory(beneficiaryRegID);

		assertNotNull(response);
		assertTrue(response.contains("columns"));
		assertTrue(response.contains("data"));
		verify(femaleObstetricHistoryRepo, times(1)).getBenFemaleObstetricHistoryDetail(beneficiaryRegID);
	}

	@Test
	public void testFetchBenPastObstetricHistory_withEmptyData() {
		when(femaleObstetricHistoryRepo.getBenFemaleObstetricHistoryDetail(beneficiaryRegID))
				.thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.fetchBenPastObstetricHistory(beneficiaryRegID);

		assertNotNull(response);
		assertTrue(response.contains("columns"));
		assertTrue(response.contains("data"));
		verify(femaleObstetricHistoryRepo, times(1)).getBenFemaleObstetricHistoryDetail(beneficiaryRegID);
	}

	@Test
	public void testFetchBenPastObstetricHistory_withNullData() {
		when(femaleObstetricHistoryRepo.getBenFemaleObstetricHistoryDetail(beneficiaryRegID)).thenReturn(null);

		String response = commonNurseServiceImpl.fetchBenPastObstetricHistory(beneficiaryRegID);

		assertNotNull(response);
		assertTrue(response.contains("columns"));
		assertTrue(response.contains("data"));
		verify(femaleObstetricHistoryRepo, times(1)).getBenFemaleObstetricHistoryDetail(beneficiaryRegID);
	}

	@Test
	public void testFetchBenComorbidityHistory_withValidData() {
		List<Object[]> mockData = new ArrayList<>();
		mockData.add(new Object[] { new Date(), "Condition1", "OtherCondition1", new Date() });
		when(bencomrbidityCondRepo.getBencomrbidityCondDetails(beneficiaryRegID)).thenReturn(mockData);

		String response = commonNurseServiceImpl.fetchBenComorbidityHistory(beneficiaryRegID);

		Map<String, Object> expectedResponse = new HashMap<>();
		List<Map<String, Object>> columns = new ArrayList<>();
		Map<String, Object> column = new HashMap<>();

		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Comorbid Condition");
		column.put("keyName", "comorbidCondition");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Other Comorbid Condition");
		column.put("keyName", "otherComorbidCondition");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Date");
		column.put("keyName", "date");
		columns.add(column);

		List<BencomrbidityCondDetails> data = new ArrayList<>();
		data.add(new BencomrbidityCondDetails((Date) mockData.get(0)[0], (String) mockData.get(0)[1],
				(String) mockData.get(0)[2], (Date) mockData.get(0)[3]));

		expectedResponse.put("columns", columns);
		expectedResponse.put("data", data);

		assertEquals(new Gson().toJson(expectedResponse), response);
		verify(bencomrbidityCondRepo, times(1)).getBencomrbidityCondDetails(beneficiaryRegID);
	}

	@Test
	public void testFetchBenComorbidityHistory_withNoData() {
		when(bencomrbidityCondRepo.getBencomrbidityCondDetails(beneficiaryRegID)).thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.fetchBenComorbidityHistory(beneficiaryRegID);

		Map<String, Object> expectedResponse = new HashMap<>();
		List<Map<String, Object>> columns = new ArrayList<>();
		Map<String, Object> column = new HashMap<>();

		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Comorbid Condition");
		column.put("keyName", "comorbidCondition");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Other Comorbid Condition");
		column.put("keyName", "otherComorbidCondition");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Date");
		column.put("keyName", "date");
		columns.add(column);

		expectedResponse.put("columns", columns);
		expectedResponse.put("data", new ArrayList<>());

		assertEquals(new Gson().toJson(expectedResponse), response);
		verify(bencomrbidityCondRepo, times(1)).getBencomrbidityCondDetails(beneficiaryRegID);
	}

	@Test
	public void testFetchBenComorbidityHistory_withNullData() {
		when(bencomrbidityCondRepo.getBencomrbidityCondDetails(beneficiaryRegID)).thenReturn(null);

		String response = commonNurseServiceImpl.fetchBenComorbidityHistory(beneficiaryRegID);

		Map<String, Object> expectedResponse = new HashMap<>();
		List<Map<String, Object>> columns = new ArrayList<>();
		Map<String, Object> column = new HashMap<>();

		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Comorbid Condition");
		column.put("keyName", "comorbidCondition");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Other Comorbid Condition");
		column.put("keyName", "otherComorbidCondition");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Date");
		column.put("keyName", "date");
		columns.add(column);

		expectedResponse.put("columns", columns);
		expectedResponse.put("data", new ArrayList<>());

		assertEquals(new Gson().toJson(expectedResponse), response);
		verify(bencomrbidityCondRepo, times(1)).getBencomrbidityCondDetails(beneficiaryRegID);
	}

	@Test
	public void testFetchBenImmunizationHistory_withValidData() {
		when(childVaccineDetail1Repo.getBenChildVaccineDetails(beneficiaryRegID)).thenReturn(mockData);

		String response = commonNurseServiceImpl.fetchBenImmunizationHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, Map.class);
		List<Map<String, Object>> columns = (List<Map<String, Object>>) responseMap.get("columns");
		List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");

		assertNotNull(responseMap);
		assertEquals(4, columns.size());
		assertEquals(1, data.size());
		assertEquals("6 months", ((Map<String, Object>) data.get(0)).get("defaultReceivingAge"));
		assertEquals("Polio", ((Map<String, Object>) data.get(0)).get("vaccineName"));
		assertEquals(true, ((Map<String, Object>) data.get(0)).get("status"));

		verify(childVaccineDetail1Repo, times(1)).getBenChildVaccineDetails(beneficiaryRegID);
	}

	@Test
	public void testFetchBenImmunizationHistory_withEmptyData() {
		when(childVaccineDetail1Repo.getBenChildVaccineDetails(beneficiaryRegID)).thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.fetchBenImmunizationHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, Map.class);
		List<Map<String, Object>> columns = (List<Map<String, Object>>) responseMap.get("columns");
		List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");

		assertNotNull(responseMap);
		assertEquals(4, columns.size());
		assertTrue(data.isEmpty());

		verify(childVaccineDetail1Repo, times(1)).getBenChildVaccineDetails(beneficiaryRegID);
	}

	@Test
	public void testFetchBenImmunizationHistory_withNullData() {
		when(childVaccineDetail1Repo.getBenChildVaccineDetails(beneficiaryRegID)).thenReturn(null);

		String response = commonNurseServiceImpl.fetchBenImmunizationHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, Map.class);
		List<Map<String, Object>> columns = (List<Map<String, Object>>) responseMap.get("columns");
		List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");

		assertNotNull(responseMap);
		assertEquals(4, columns.size());
		assertTrue(data.isEmpty());

		verify(childVaccineDetail1Repo, times(1)).getBenChildVaccineDetails(beneficiaryRegID);
	}

	@Test
	public void testFetchBenOptionalVaccineHistory_EmptyList() {
		when(childOptionalVaccineDetailRepo.getBenOptionalVaccineDetail(beneficiaryRegID))
				.thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.fetchBenOptionalVaccineHistory(beneficiaryRegID);

		assertNotNull(response);
		assertTrue(response.contains("\"columns\""));
		assertTrue(response.contains("\"data\""));
		assertTrue(response.contains("[]"));
	}

	@Test
	public void testFetchBenOptionalVaccineHistory_NonEmptyList() {
		List<Object[]> mockData = new ArrayList<>();
		mockData.add(new Object[] { new Date(), "Age1", "Vaccine1", "OtherVaccine1", "Facility1" });
		when(childOptionalVaccineDetailRepo.getBenOptionalVaccineDetail(beneficiaryRegID)).thenReturn(mockData);

		String response = commonNurseServiceImpl.fetchBenOptionalVaccineHistory(beneficiaryRegID);

		assertNotNull(response);
		assertTrue(response.contains("\"columns\""));
		assertTrue(response.contains("\"data\""));
		assertTrue(response.contains("Age1"));
		assertTrue(response.contains("Vaccine1"));
		assertTrue(response.contains("OtherVaccine1"));
		assertTrue(response.contains("Facility1"));
	}

	@Test
	public void testFetchBenOptionalVaccineHistory_SpecificData() {
		List<Object[]> mockData = new ArrayList<>();
		mockData.add(new Object[] { new Date(), "Age1", "Vaccine1", "OtherVaccine1", "Facility1" });
		when(childOptionalVaccineDetailRepo.getBenOptionalVaccineDetail(beneficiaryRegID)).thenReturn(mockData);

		String response = commonNurseServiceImpl.fetchBenOptionalVaccineHistory(beneficiaryRegID);

		assertNotNull(response);
		assertTrue(response.contains("\"columns\""));
		assertTrue(response.contains("\"data\""));

		Gson gson = new Gson();
		var responseMap = gson.fromJson(response, Map.class);
		var columns = (List<Map<String, String>>) responseMap.get("columns");
		var data = (List<Map<String, String>>) responseMap.get("data");

		assertEquals(8, columns.size());
		assertEquals(1, data.size());
		assertEquals("Age1", data.get(0).get("defaultReceivingAge"));
		assertEquals("Vaccine1", data.get(0).get("vaccineName"));
		assertEquals("OtherVaccine1", data.get(0).get("otherVaccineName"));
		assertEquals("Facility1", data.get(0).get("receivedFacilityName"));
	}

	@Test
	public void testGetBenChiefComplaints() {
		when(benChiefComplaintRepo.getBenChiefComplaints(beneficiaryRegID, visitCode)).thenReturn(mockResponseList);

		String response = commonNurseServiceImpl.getBenChiefComplaints(beneficiaryRegID, visitCode);

		assertNotNull(response);
		assertTrue(response.contains("complaint1"));
		assertTrue(response.contains("description1"));
		assertTrue(response.contains("complaint2"));
		assertTrue(response.contains("description2"));

		verify(benChiefComplaintRepo, times(1)).getBenChiefComplaints(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBenChiefComplaints_EmptyResponse() {
		when(benChiefComplaintRepo.getBenChiefComplaints(beneficiaryRegID, visitCode)).thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.getBenChiefComplaints(beneficiaryRegID, visitCode);

		assertNotNull(response);
		assertEquals("[]", response);

		verify(benChiefComplaintRepo, times(1)).getBenChiefComplaints(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBenChiefComplaints_NullResponse() {
		when(benChiefComplaintRepo.getBenChiefComplaints(beneficiaryRegID, visitCode)).thenReturn(null);

		String response = commonNurseServiceImpl.getBenChiefComplaints(beneficiaryRegID, visitCode);

		assertNotNull(response);
		assertEquals("[]", response);

		verify(benChiefComplaintRepo, times(1)).getBenChiefComplaints(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBenCdss_ReturnsValidData() {
		when(cdssRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode)).thenReturn(cdss);

		String response = commonNurseService.getBenCdss(beneficiaryRegID, visitCode);

		Map<String, Object> cdssData = new HashMap<>();
		Map<String, Object> presentChiefComplaint = new HashMap<>();
		Map<String, Object> diseaseSummary = new HashMap<>();

		presentChiefComplaint.put("selectedDiagnosisID", cdss.getSelectedDiagnosisID());
		presentChiefComplaint.put("selectedDiagnosis", cdss.getSelectedDiagnosis());
		presentChiefComplaint.put("presentChiefComplaint", cdss.getPresentChiefComplaint());
		presentChiefComplaint.put("presentChiefComplaintID", cdss.getPresentChiefComplaintID());
		presentChiefComplaint.put("algorithmPc", cdss.getAlgorithmPc());
		presentChiefComplaint.put("recommendedActionPc", cdss.getRecommendedActionPc());
		presentChiefComplaint.put("remarksPc", cdss.getRemarksPc());
		presentChiefComplaint.put("actionPc", cdss.getActionPc());
		presentChiefComplaint.put("actionIdPc", cdss.getActionIdPc());
		diseaseSummary.put("diseaseSummaryID", cdss.getDiseaseSummaryID());
		diseaseSummary.put("diseaseSummary", cdss.getDiseaseSummary());
		diseaseSummary.put("algorithm", cdss.getAlgorithm());
		diseaseSummary.put("recommendedAction", cdss.getRecommendedAction());
		diseaseSummary.put("remarks", cdss.getRemarks());
		diseaseSummary.put("action", cdss.getAction());
		diseaseSummary.put("actionId", cdss.getActionId());
		diseaseSummary.put("informationGiven", cdss.getInformationGiven());

		cdssData.put("presentChiefComplaint", presentChiefComplaint);
		cdssData.put("diseaseSummary", diseaseSummary);
		cdssData.put("vanID", cdss.getVanID());
		cdssData.put("parkingPlaceID", cdss.getParkingPlaceID());
		cdssData.put("providerServiceMapID", cdss.getProviderServiceMapID());
		cdssData.put("beneficiaryRegID", cdss.getBeneficiaryRegID());
		cdssData.put("benVisitID", cdss.getBenVisitID());
		cdssData.put("createdBy", cdss.getCreatedBy());

		String expectedResponse = new Gson().toJson(cdssData);

		assertEquals(expectedResponse, response);
	}

	@Test
	public void testGetBenCdss_ReturnsEmptyData() {
		when(cdssRepo.findByBeneficiaryRegIDAndVisitCode(beneficiaryRegID, visitCode)).thenReturn(null);

		String response = commonNurseService.getBenCdss(beneficiaryRegID, visitCode);

		assertEquals("{}", response);
	}

	@Test
	public void testGetFamilyHistoryDetail_Success() {
		when(benFamilyHistoryRepo.getBenFamilyHisDetail(beneficiaryRegID, visitCode)).thenReturn(familyHistoryData);

		BenFamilyHistory result = commonNurseServiceImpl.getFamilyHistoryDetail(beneficiaryRegID, visitCode);

		assertNotNull(result);
		verify(benFamilyHistoryRepo, times(1)).getBenFamilyHisDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetFamilyHistoryDetail_NoData() {
		when(benFamilyHistoryRepo.getBenFamilyHisDetail(beneficiaryRegID, visitCode)).thenReturn(new ArrayList<>());

		BenFamilyHistory result = commonNurseServiceImpl.getFamilyHistoryDetail(beneficiaryRegID, visitCode);

		assertNull(result);
		verify(benFamilyHistoryRepo, times(1)).getBenFamilyHisDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetFamilyHistoryDetail_Exception() {
		when(benFamilyHistoryRepo.getBenFamilyHisDetail(beneficiaryRegID, visitCode))
				.thenThrow(new RuntimeException("Database error"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonNurseServiceImpl.getFamilyHistoryDetail(beneficiaryRegID, visitCode);
		});

		assertEquals("Database error", exception.getMessage());
		verify(benFamilyHistoryRepo, times(1)).getBenFamilyHisDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetPhysicalActivityType_Success() {
		when(physicalActivityTypeRepo.getBenPhysicalHistoryDetails(beneficiaryRegID, visitCode))
				.thenReturn(physicalActivityType);

		PhysicalActivityType result = commonNurseServiceImpl.getPhysicalActivityType(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertEquals(beneficiaryRegID, result.getBeneficiaryRegID());
		assertEquals(visitCode, result.getVisitCode());
		verify(physicalActivityTypeRepo, times(1)).getBenPhysicalHistoryDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetPhysicalActivityType_NotFound() {
		when(physicalActivityTypeRepo.getBenPhysicalHistoryDetails(beneficiaryRegID, visitCode)).thenReturn(null);

		PhysicalActivityType result = commonNurseServiceImpl.getPhysicalActivityType(beneficiaryRegID, visitCode);

		assertNull(result);
		verify(physicalActivityTypeRepo, times(1)).getBenPhysicalHistoryDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetPhysicalActivityType_Exception() {
		when(physicalActivityTypeRepo.getBenPhysicalHistoryDetails(beneficiaryRegID, visitCode))
				.thenThrow(new RuntimeException("Database error"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonNurseServiceImpl.getPhysicalActivityType(beneficiaryRegID, visitCode);
		});

		assertEquals("Database error", exception.getMessage());
		verify(physicalActivityTypeRepo, times(1)).getBenPhysicalHistoryDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryIdrsDetails_Success() {
		ArrayList<Object[]> mockData = new ArrayList<>();
		Object[] data = new Object[] { "someData" };
		mockData.add(data);

		when(idrsDataRepo.getBenIdrsDetail(beneficiaryRegID, visitCode)).thenReturn(mockData);

		IDRSData result = commonNurseService.getBeneficiaryIdrsDetails(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertEquals("someData", result.getSomeField()); // Replace with actual field and value
		verify(idrsDataRepo, times(1)).getBenIdrsDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBeneficiaryIdrsDetails_NoDataFound() {
		when(idrsDataRepo.getBenIdrsDetail(beneficiaryRegID, visitCode)).thenReturn(new ArrayList<>());

		IDRSData result = commonNurseService.getBeneficiaryIdrsDetails(beneficiaryRegID, visitCode);

		assertNull(result);
		verify(idrsDataRepo, times(1)).getBenIdrsDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetPastHistoryData_withValidData() {
		when(benMedHistoryRepo.getBenPastHistory(beneficiaryRegID, visitCode)).thenReturn(pastHistoryData);

		BenMedHistory result = commonNurseService.getPastHistoryData(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertEquals("data1", result.getSomeField()); // Replace with actual field and expected value
		verify(benMedHistoryRepo, times(1)).getBenPastHistory(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetPastHistoryData_withEmptyData() {
		when(benMedHistoryRepo.getBenPastHistory(beneficiaryRegID, visitCode)).thenReturn(new ArrayList<>());

		BenMedHistory result = commonNurseService.getPastHistoryData(beneficiaryRegID, visitCode);

		assertNull(result);
		verify(benMedHistoryRepo, times(1)).getBenPastHistory(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetPastHistoryData_withNullData() {
		when(benMedHistoryRepo.getBenPastHistory(beneficiaryRegID, visitCode)).thenReturn(null);

		BenMedHistory result = commonNurseService.getPastHistoryData(beneficiaryRegID, visitCode);

		assertNull(result);
		verify(benMedHistoryRepo, times(1)).getBenPastHistory(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetComorbidityConditionsHistory_ValidData() {
		ArrayList<Object[]> mockData = new ArrayList<>();
		mockData.add(new Object[] { "2023-01-01", "Condition1", "OtherCondition1", "2023-01-01" });
		when(bencomrbidityCondRepo.getBencomrbidityCondDetails(beneficiaryRegID, visitCode)).thenReturn(mockData);

		WrapperComorbidCondDetails result = commonNurseServiceImpl.getComorbidityConditionsHistory(beneficiaryRegID,
				visitCode);

		assertNotNull(result);
		assertEquals(1, result.getComorbidityDetails().size());
		assertEquals("Condition1", result.getComorbidityDetails().get(0).getComorbidCondition());
		verify(bencomrbidityCondRepo, times(1)).getBencomrbidityCondDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetComorbidityConditionsHistory_EmptyData() {
		ArrayList<Object[]> mockData = new ArrayList<>();
		when(bencomrbidityCondRepo.getBencomrbidityCondDetails(beneficiaryRegID, visitCode)).thenReturn(mockData);

		WrapperComorbidCondDetails result = commonNurseServiceImpl.getComorbidityConditionsHistory(beneficiaryRegID,
				visitCode);

		assertNotNull(result);
		assertTrue(result.getComorbidityDetails().isEmpty());
		verify(bencomrbidityCondRepo, times(1)).getBencomrbidityCondDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetComorbidityConditionsHistory_NullData() {
		when(bencomrbidityCondRepo.getBencomrbidityCondDetails(beneficiaryRegID, visitCode)).thenReturn(null);

		WrapperComorbidCondDetails result = commonNurseServiceImpl.getComorbidityConditionsHistory(beneficiaryRegID,
				visitCode);

		assertNotNull(result);
		assertTrue(result.getComorbidityDetails().isEmpty());
		verify(bencomrbidityCondRepo, times(1)).getBencomrbidityCondDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetMedicationHistory_Success() {
		ArrayList<Object[]> mockData = new ArrayList<>();
		mockData.add(new Object[] { "data1", "data2" });
		when(benMedicationHistoryRepo.getBenMedicationHistoryDetail(beneficiaryRegID, visitCode)).thenReturn(mockData);

		WrapperMedicationHistory result = commonNurseService.getMedicationHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertEquals(1, result.getMedicationHistoryDetails().size());
		verify(benMedicationHistoryRepo, times(1)).getBenMedicationHistoryDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetMedicationHistory_EmptyResult() {
		ArrayList<Object[]> mockData = new ArrayList<>();
		when(benMedicationHistoryRepo.getBenMedicationHistoryDetail(beneficiaryRegID, visitCode)).thenReturn(mockData);

		WrapperMedicationHistory result = commonNurseService.getMedicationHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertTrue(result.getMedicationHistoryDetails().isEmpty());
		verify(benMedicationHistoryRepo, times(1)).getBenMedicationHistoryDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetMedicationHistory_NullResult() {
		when(benMedicationHistoryRepo.getBenMedicationHistoryDetail(beneficiaryRegID, visitCode)).thenReturn(null);

		WrapperMedicationHistory result = commonNurseService.getMedicationHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertTrue(result.getMedicationHistoryDetails().isEmpty());
		verify(benMedicationHistoryRepo, times(1)).getBenMedicationHistoryDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetPersonalHistory_WithPersonalAndAllergyDetails() {
		ArrayList<Object[]> personalDetails = new ArrayList<>();
		personalDetails.add(new Object[] { "detail1" });
		when(benPersonalHabitRepo.getBenPersonalHabitDetail(beneficiaryRegID, visitCode)).thenReturn(personalDetails);

		ArrayList<Object[]> allergyDetails = new ArrayList<>();
		allergyDetails.add(new Object[] { "allergy1" });
		when(benAllergyHistoryRepo.getBenPersonalAllergyDetail(beneficiaryRegID, visitCode)).thenReturn(allergyDetails);

		BenPersonalHabit result = commonNurseServiceImpl.getPersonalHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertEquals("detail1", result.getPersonalDetails().get(0));
		assertEquals("allergy1", result.getAllergicList().get(0).getAllergyStatus());
		verify(benPersonalHabitRepo, times(1)).getBenPersonalHabitDetail(beneficiaryRegID, visitCode);
		verify(benAllergyHistoryRepo, times(1)).getBenPersonalAllergyDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetPersonalHistory_WithNullPersonalDetails() {
		when(benPersonalHabitRepo.getBenPersonalHabitDetail(beneficiaryRegID, visitCode)).thenReturn(null);

		BenPersonalHabit result = commonNurseServiceImpl.getPersonalHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertTrue(result.getPersonalDetails().isEmpty());
		verify(benPersonalHabitRepo, times(1)).getBenPersonalHabitDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetPersonalHistory_WithAllergyDetails() {
		ArrayList<Object[]> personalDetails = new ArrayList<>();
		personalDetails.add(new Object[] { "detail1" });
		when(benPersonalHabitRepo.getBenPersonalHabitDetail(beneficiaryRegID, visitCode)).thenReturn(personalDetails);

		ArrayList<Object[]> allergyDetails = new ArrayList<>();
		allergyDetails.add(new Object[] { "allergy1" });
		when(benAllergyHistoryRepo.getBenPersonalAllergyDetail(beneficiaryRegID, visitCode)).thenReturn(allergyDetails);

		BenPersonalHabit result = commonNurseServiceImpl.getPersonalHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertEquals("allergy1", result.getAllergicList().get(0).getAllergyStatus());
		verify(benAllergyHistoryRepo, times(1)).getBenPersonalAllergyDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetPersonalHistory_WithNullAllergyDetails() {
		ArrayList<Object[]> personalDetails = new ArrayList<>();
		personalDetails.add(new Object[] { "detail1" });
		when(benPersonalHabitRepo.getBenPersonalHabitDetail(beneficiaryRegID, visitCode)).thenReturn(personalDetails);

		when(benAllergyHistoryRepo.getBenPersonalAllergyDetail(beneficiaryRegID, visitCode)).thenReturn(null);

		BenPersonalHabit result = commonNurseServiceImpl.getPersonalHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertTrue(result.getAllergicList().isEmpty());
		verify(benAllergyHistoryRepo, times(1)).getBenPersonalAllergyDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetFamilyHistory_ValidData() {
		ArrayList<Object[]> familyHistoryData = new ArrayList<>();
		familyHistoryData.add(new Object[] { "data1", "data2" });
		when(benFamilyHistoryRepo.getBenFamilyHistoryDetail(beneficiaryRegID, visitCode)).thenReturn(familyHistoryData);

		BenFamilyHistory result = commonNurseServiceImpl.getFamilyHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		verify(benFamilyHistoryRepo, times(1)).getBenFamilyHistoryDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetFamilyHistory_NullData() {
		when(benFamilyHistoryRepo.getBenFamilyHistoryDetail(beneficiaryRegID, visitCode)).thenReturn(null);

		BenFamilyHistory result = commonNurseServiceImpl.getFamilyHistory(beneficiaryRegID, visitCode);

		assertNull(result);
		verify(benFamilyHistoryRepo, times(1)).getBenFamilyHistoryDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetFamilyHistory_EmptyData() {
		ArrayList<Object[]> familyHistoryData = new ArrayList<>();
		when(benFamilyHistoryRepo.getBenFamilyHistoryDetail(beneficiaryRegID, visitCode)).thenReturn(familyHistoryData);

		BenFamilyHistory result = commonNurseServiceImpl.getFamilyHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertTrue(result.getFamilyHistoryList().isEmpty());
		verify(benFamilyHistoryRepo, times(1)).getBenFamilyHistoryDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetMenstrualHistoryReturnsValidData() {
		when(benMenstrualDetailsRepo.getBenMenstrualDetail(beneficiaryRegID, visitCode)).thenReturn(menstrualHistory);
		when(BenMenstrualDetails.getBenMenstrualDetails(menstrualHistory)).thenReturn(benMenstrualDetails);

		BenMenstrualDetails result = commonNurseServiceImpl.getMenstrualHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertEquals("1,2", result.getMenstrualProblemID());
		assertEquals("Problem1,Problem2", result.getProblemName());
		verify(benMenstrualDetailsRepo, times(1)).getBenMenstrualDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetMenstrualHistoryReturnsNull() {
		when(benMenstrualDetailsRepo.getBenMenstrualDetail(beneficiaryRegID, visitCode)).thenReturn(null);

		BenMenstrualDetails result = commonNurseServiceImpl.getMenstrualHistory(beneficiaryRegID, visitCode);

		assertNull(result);
		verify(benMenstrualDetailsRepo, times(1)).getBenMenstrualDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetMenstrualHistoryReturnsEmptyList() {
		when(benMenstrualDetailsRepo.getBenMenstrualDetail(beneficiaryRegID, visitCode)).thenReturn(new ArrayList<>());

		BenMenstrualDetails result = commonNurseServiceImpl.getMenstrualHistory(beneficiaryRegID, visitCode);

		assertNull(result);
		verify(benMenstrualDetailsRepo, times(1)).getBenMenstrualDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetWrapperFemaleObstetricHistory_ValidData() {
		WrapperFemaleObstetricHistory result = commonNurseServiceImpl
				.getWrapperFemaleObstetricHistory(wrapperFemaleObstetricHistory);

		assertNotNull(result);
		assertEquals(1, result.getFemaleObstetricHistoryList().size());

		FemaleObstetricHistory history = result.getFemaleObstetricHistoryList().get(0);
		assertNotNull(history.getPregComplicationList());
		assertEquals(2, history.getPregComplicationList().size());

		Map<String, Object> pregComplication = history.getPregComplicationList().get(0);
		assertEquals("1", pregComplication.get("pregComplicationID"));
		assertEquals("Type1", pregComplication.get("pregComplicationType"));
	}

	@Test
	public void testGetWrapperFemaleObstetricHistory_NullValues() {
		wrapperFemaleObstetricHistory.getFemaleObstetricHistoryList().get(0).setPregComplicationID(null);
		wrapperFemaleObstetricHistory.getFemaleObstetricHistoryList().get(0).setPregComplicationType(null);

		WrapperFemaleObstetricHistory result = commonNurseServiceImpl
				.getWrapperFemaleObstetricHistory(wrapperFemaleObstetricHistory);

		assertNotNull(result);
		assertEquals(1, result.getFemaleObstetricHistoryList().size());

		FemaleObstetricHistory history = result.getFemaleObstetricHistoryList().get(0);
		assertNull(history.getPregComplicationList());
	}

	@Test
	public void testGetWrapperFemaleObstetricHistory_EmptyLists() {
		wrapperFemaleObstetricHistory.setFemaleObstetricHistoryList(new ArrayList<>());

		WrapperFemaleObstetricHistory result = commonNurseServiceImpl
				.getWrapperFemaleObstetricHistory(wrapperFemaleObstetricHistory);

		assertNotNull(result);
		assertTrue(result.getFemaleObstetricHistoryList().isEmpty());
	}

	@Test
	public void testGetChildOptionalVaccineHistory_WithData() {
		when(childOptionalVaccineDetailRepo.getBenOptionalVaccineDetail(beneficiaryRegID, visitCode))
				.thenReturn(childOptionalVaccineDetail);

		WrapperChildOptionalVaccineDetail result = commonNurseServiceImpl
				.getChildOptionalVaccineHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertEquals(1, result.getChildOptionalVaccineDetailList().size());
		verify(childOptionalVaccineDetailRepo, times(1)).getBenOptionalVaccineDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetChildOptionalVaccineHistory_NoData() {
		when(childOptionalVaccineDetailRepo.getBenOptionalVaccineDetail(beneficiaryRegID, visitCode))
				.thenReturn(new ArrayList<>());

		WrapperChildOptionalVaccineDetail result = commonNurseServiceImpl
				.getChildOptionalVaccineHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertTrue(result.getChildOptionalVaccineDetailList().isEmpty());
		verify(childOptionalVaccineDetailRepo, times(1)).getBenOptionalVaccineDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetChildOptionalVaccineHistory_NullData() {
		when(childOptionalVaccineDetailRepo.getBenOptionalVaccineDetail(beneficiaryRegID, visitCode)).thenReturn(null);

		WrapperChildOptionalVaccineDetail result = commonNurseServiceImpl
				.getChildOptionalVaccineHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertTrue(result.getChildOptionalVaccineDetailList().isEmpty());
		verify(childOptionalVaccineDetailRepo, times(1)).getBenOptionalVaccineDetail(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetImmunizationHistoryWithVisitCode() {
		ArrayList<Object[]> mockData = new ArrayList<>();
		when(childVaccineDetail1Repo.getBenChildVaccineDetails(beneficiaryRegID, visitCode)).thenReturn(mockData);

		WrapperImmunizationHistory result = commonNurseServiceImpl.getImmunizationHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		verify(childVaccineDetail1Repo, times(1)).getBenChildVaccineDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetImmunizationHistoryWithoutVisitCode() {
		ArrayList<Object[]> mockData = new ArrayList<>();
		when(childVaccineDetail1Repo.getBenChildVaccineDetailsByRegID(beneficiaryRegID)).thenReturn(mockData);

		WrapperImmunizationHistory result = commonNurseServiceImpl.getImmunizationHistory(beneficiaryRegID, null);

		assertNotNull(result);
		verify(childVaccineDetail1Repo, times(1)).getBenChildVaccineDetailsByRegID(beneficiaryRegID);
	}

	@Test
	public void testGetImmunizationHistoryWithEmptyData() {
		ArrayList<Object[]> mockData = new ArrayList<>();
		when(childVaccineDetail1Repo.getBenChildVaccineDetails(beneficiaryRegID, visitCode)).thenReturn(mockData);

		WrapperImmunizationHistory result = commonNurseServiceImpl.getImmunizationHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertTrue(result.getChildVaccineDetail().isEmpty());
		verify(childVaccineDetail1Repo, times(1)).getBenChildVaccineDetails(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetGeneralExaminationData_ValidData() {
		when(phyGeneralExaminationRepo.getPhyGeneralExaminationData(benRegID, visitCode))
				.thenReturn(phyGeneralExamination);

		PhyGeneralExamination result = commonNurseServiceImpl.getGeneralExaminationData(benRegID, visitCode);

		assertNotNull(result);
		assertEquals(phyGeneralExamination, result);
		assertEquals(2, result.getTypeOfDangerSigns().size());
		assertTrue(result.getTypeOfDangerSigns().contains("sign1"));
		assertTrue(result.getTypeOfDangerSigns().contains("sign2"));
		verify(phyGeneralExaminationRepo, times(1)).getPhyGeneralExaminationData(benRegID, visitCode);
	}

	@Test
	public void testGetGeneralExaminationData_NoDangerSigns() {
		phyGeneralExamination.setTypeOfDangerSign(null);
		when(phyGeneralExaminationRepo.getPhyGeneralExaminationData(benRegID, visitCode))
				.thenReturn(phyGeneralExamination);

		PhyGeneralExamination result = commonNurseServiceImpl.getGeneralExaminationData(benRegID, visitCode);

		assertNotNull(result);
		assertEquals(phyGeneralExamination, result);
		assertNotNull(result.getTypeOfDangerSigns());
		assertTrue(result.getTypeOfDangerSigns().isEmpty());
		verify(phyGeneralExaminationRepo, times(1)).getPhyGeneralExaminationData(benRegID, visitCode);
	}

	@Test
	public void testGetGeneralExaminationData_NullData() {
		when(phyGeneralExaminationRepo.getPhyGeneralExaminationData(benRegID, visitCode)).thenReturn(null);

		PhyGeneralExamination result = commonNurseServiceImpl.getGeneralExaminationData(benRegID, visitCode);

		assertNull(result);
		verify(phyGeneralExaminationRepo, times(1)).getPhyGeneralExaminationData(benRegID, visitCode);
	}

	@Test
	public void testGetHeadToToeExaminationData_Found() {
		when(phyHeadToToeExaminationRepo.getPhyHeadToToeExaminationData(benRegID, visitCode))
				.thenReturn(phyHeadToToeExamination);

		PhyHeadToToeExamination result = commonNurseService.getHeadToToeExaminationData(benRegID, visitCode);

		assertNotNull(result);
		assertEquals(benRegID, result.getBenRegID());
		assertEquals(visitCode, result.getVisitCode());
		verify(phyHeadToToeExaminationRepo, times(1)).getPhyHeadToToeExaminationData(benRegID, visitCode);
	}

	@Test
	public void testGetHeadToToeExaminationData_NotFound() {
		when(phyHeadToToeExaminationRepo.getPhyHeadToToeExaminationData(benRegID, visitCode)).thenReturn(null);

		PhyHeadToToeExamination result = commonNurseService.getHeadToToeExaminationData(benRegID, visitCode);

		assertNull(result);
		verify(phyHeadToToeExaminationRepo, times(1)).getPhyHeadToToeExaminationData(benRegID, visitCode);
	}

	@Test
	public void testGetSysGastrointestinalExamination_ReturnsValidData() {
		SysGastrointestinalExamination expectedExamination = new SysGastrointestinalExamination();
		expectedExamination.setId(1L);
		expectedExamination.setExaminationDetail("Detail");

		when(sysGastrointestinalExaminationRepo.getSSysGastrointestinalExamination(benRegID, visitCode))
				.thenReturn(expectedExamination);

		SysGastrointestinalExamination actualExamination = commonNurseServiceImpl
				.getSysGastrointestinalExamination(benRegID, visitCode);

		assertNotNull(actualExamination);
		assertEquals(expectedExamination, actualExamination);
		verify(sysGastrointestinalExaminationRepo, times(1)).getSSysGastrointestinalExamination(benRegID, visitCode);
	}

	@Test
	public void testGetSysGastrointestinalExamination_ReturnsNull() {
		when(sysGastrointestinalExaminationRepo.getSSysGastrointestinalExamination(benRegID, visitCode))
				.thenReturn(null);

		SysGastrointestinalExamination actualExamination = commonNurseServiceImpl
				.getSysGastrointestinalExamination(benRegID, visitCode);

		assertNull(actualExamination);
		verify(sysGastrointestinalExaminationRepo, times(1)).getSSysGastrointestinalExamination(benRegID, visitCode);
	}

	@Test
	public void testGetCardiovascularExamination_ReturnsValidExamination() {
		when(sysCardiovascularExaminationRepo.getSysCardiovascularExaminationData(benRegID, visitCode))
				.thenReturn(sysCardiovascularExamination);

		SysCardiovascularExamination result = commonNurseServiceImpl.getCardiovascularExamination(benRegID, visitCode);

		assertNotNull(result);
		assertEquals(benRegID, result.getBenRegID());
		assertEquals(visitCode, result.getVisitCode());
		verify(sysCardiovascularExaminationRepo, times(1)).getSysCardiovascularExaminationData(benRegID, visitCode);
	}

	@Test
	public void testGetCardiovascularExamination_ReturnsNull() {
		when(sysCardiovascularExaminationRepo.getSysCardiovascularExaminationData(benRegID, visitCode))
				.thenReturn(null);

		SysCardiovascularExamination result = commonNurseServiceImpl.getCardiovascularExamination(benRegID, visitCode);

		assertNull(result);
		verify(sysCardiovascularExaminationRepo, times(1)).getSysCardiovascularExaminationData(benRegID, visitCode);
	}

	@Test
	public void testGetRespiratoryExamination_ReturnsValidObject() {
		SysRespiratoryExamination expectedExamination = new SysRespiratoryExamination();
		when(sysRespiratoryExaminationRepo.getSysRespiratoryExaminationData(benRegID, visitCode))
				.thenReturn(expectedExamination);

		SysRespiratoryExamination actualExamination = commonNurseServiceImpl.getRespiratoryExamination(benRegID,
				visitCode);

		assertNotNull(actualExamination);
		assertEquals(expectedExamination, actualExamination);
		verify(sysRespiratoryExaminationRepo, times(1)).getSysRespiratoryExaminationData(benRegID, visitCode);
	}

	@Test
	public void testGetRespiratoryExamination_ReturnsNull() {
		when(sysRespiratoryExaminationRepo.getSysRespiratoryExaminationData(benRegID, visitCode)).thenReturn(null);

		SysRespiratoryExamination actualExamination = commonNurseServiceImpl.getRespiratoryExamination(benRegID,
				visitCode);

		assertNull(actualExamination);
		verify(sysRespiratoryExaminationRepo, times(1)).getSysRespiratoryExaminationData(benRegID, visitCode);
	}

	@Test
	public void testGetSysCentralNervousExamination_ReturnsValidObject() {
		when(sysCentralNervousExaminationRepo.getSysCentralNervousExaminationData(benRegID, visitCode))
				.thenReturn(sysCentralNervousExamination);

		SysCentralNervousExamination result = commonNurseServiceImpl.getSysCentralNervousExamination(benRegID,
				visitCode);

		assertNotNull(result);
		assertEquals(benRegID, result.getBenRegID());
		assertEquals(visitCode, result.getVisitCode());
		verify(sysCentralNervousExaminationRepo, times(1)).getSysCentralNervousExaminationData(benRegID, visitCode);
	}

	@Test
	public void testGetSysCentralNervousExamination_ReturnsNull() {
		when(sysCentralNervousExaminationRepo.getSysCentralNervousExaminationData(benRegID, visitCode))
				.thenReturn(null);

		SysCentralNervousExamination result = commonNurseServiceImpl.getSysCentralNervousExamination(benRegID,
				visitCode);

		assertNull(result);
		verify(sysCentralNervousExaminationRepo, times(1)).getSysCentralNervousExaminationData(benRegID, visitCode);
	}

	@Test
	public void testGetMusculoskeletalExamination_Success() {
		when(sysMusculoskeletalSystemExaminationRepo.getSysMusculoskeletalSystemExamination(benRegID, visitCode))
				.thenReturn(sysMusculoskeletalSystemExamination);

		SysMusculoskeletalSystemExamination result = commonNurseServiceImpl.getMusculoskeletalExamination(benRegID,
				visitCode);

		assertNotNull(result);
		assertEquals("Details", result.getExaminationDetails());
		verify(sysMusculoskeletalSystemExaminationRepo, times(1)).getSysMusculoskeletalSystemExamination(benRegID,
				visitCode);
	}

	@Test
	public void testGetMusculoskeletalExamination_NotFound() {
		when(sysMusculoskeletalSystemExaminationRepo.getSysMusculoskeletalSystemExamination(benRegID, visitCode))
				.thenReturn(null);

		SysMusculoskeletalSystemExamination result = commonNurseServiceImpl.getMusculoskeletalExamination(benRegID,
				visitCode);

		assertNull(result);
		verify(sysMusculoskeletalSystemExaminationRepo, times(1)).getSysMusculoskeletalSystemExamination(benRegID,
				visitCode);
	}

	@Test
	public void testGetGenitourinaryExamination_ReturnsExamination() {
		when(sysGenitourinarySystemExaminationRepo.getSysGenitourinarySystemExaminationData(benRegID, visitCode))
				.thenReturn(expectedExamination);

		SysGenitourinarySystemExamination result = commonNurseServiceImpl.getGenitourinaryExamination(benRegID,
				visitCode);

		assertNotNull(result);
		assertEquals(expectedExamination, result);
		verify(sysGenitourinarySystemExaminationRepo, times(1)).getSysGenitourinarySystemExaminationData(benRegID,
				visitCode);
	}

	@Test
	public void testGetGenitourinaryExamination_ReturnsNull() {
		when(sysGenitourinarySystemExaminationRepo.getSysGenitourinarySystemExaminationData(benRegID, visitCode))
				.thenReturn(null);

		SysGenitourinarySystemExamination result = commonNurseServiceImpl.getGenitourinaryExamination(benRegID,
				visitCode);

		assertNull(result);
		verify(sysGenitourinarySystemExaminationRepo, times(1)).getSysGenitourinarySystemExaminationData(benRegID,
				visitCode);
	}

	@Test
	public void testGetOralExamination_WithPreMalignantLesionType() throws IEMRException {
		OralExamination oralExamination = new OralExamination();
		oralExamination.setPreMalignantLesionType("lesion1||lesion2");
		when(oralExaminationRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(benRegID, visitCode, false))
				.thenReturn(oralExamination);

		OralExamination result = commonNurseServiceImpl.getOralExamination(benRegID, visitCode);

		assertNotNull(result);
		assertEquals(oralExamination, result);
		assertNotNull(result.getPreMalignantLesionTypeList());
		assertEquals(2, result.getPreMalignantLesionTypeList().length);
		assertEquals("lesion1", result.getPreMalignantLesionTypeList()[0]);
		assertEquals("lesion2", result.getPreMalignantLesionTypeList()[1]);
		verify(oralExaminationRepo, times(1)).findByBeneficiaryRegIDAndVisitCodeAndDeleted(benRegID, visitCode, false);
	}

	@Test
	public void testGetOralExamination_WithoutPreMalignantLesionType() throws IEMRException {
		OralExamination oralExamination = new OralExamination();
		when(oralExaminationRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(benRegID, visitCode, false))
				.thenReturn(oralExamination);

		OralExamination result = commonNurseServiceImpl.getOralExamination(benRegID, visitCode);

		assertNotNull(result);
		assertEquals(oralExamination, result);
		assertNull(result.getPreMalignantLesionTypeList());
		verify(oralExaminationRepo, times(1)).findByBeneficiaryRegIDAndVisitCodeAndDeleted(benRegID, visitCode, false);
	}

	@Test
	public void testGetOralExamination_ReturnsNull() throws IEMRException {
		when(oralExaminationRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted(benRegID, visitCode, false))
				.thenReturn(null);

		OralExamination result = commonNurseServiceImpl.getOralExamination(benRegID, visitCode);

		assertNull(result);
		verify(oralExaminationRepo, times(1)).findByBeneficiaryRegIDAndVisitCodeAndDeleted(benRegID, visitCode, false);
	}

	@Test
	public void testUpdateBenChiefComplaints_NullList() {
		int result = commonNurseServiceImpl.updateBenChiefComplaints(null);
		assertEquals(0, result);
		verify(benChiefComplaintRepo, never()).deleteExistingBenChiefComplaints(anyLong(), anyLong());
		verify(benChiefComplaintRepo, never()).saveAll(anyList());
	}

	@Test
	public void testUpdateBenChiefComplaints_EmptyList() {
		int result = commonNurseServiceImpl.updateBenChiefComplaints(new ArrayList<>());
		assertEquals(0, result);
		verify(benChiefComplaintRepo, never()).deleteExistingBenChiefComplaints(anyLong(), anyLong());
		verify(benChiefComplaintRepo, never()).saveAll(anyList());
	}

	@Test
	public void testUpdateBenChiefComplaints_NonEmptyList() {
		when(benChiefComplaintRepo.saveAll(anyList())).thenReturn(benChiefComplaintList);

		int result = commonNurseServiceImpl.updateBenChiefComplaints(benChiefComplaintList);

		assertEquals(benChiefComplaintList.size(), result);
		verify(benChiefComplaintRepo, times(1)).deleteExistingBenChiefComplaints(anyLong(), anyLong());
		verify(benChiefComplaintRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testUpdateBenPastHistoryDetails_Success() throws ParseException, IEMRException {
		when(benMedHistoryRepo.getBenMedHistoryStatus(anyLong(), anyLong())).thenReturn(new ArrayList<>());
		when(benMedHistoryRepo.saveAll(anyList())).thenReturn(benMedHistoryList);

		int result = commonNurseServiceImpl.updateBenPastHistoryDetails(benMedHistory);

		assertEquals(1, result);
		verify(benMedHistoryRepo, times(1)).deleteExistingBenMedHistory(anyLong(), anyString());
		verify(benMedHistoryRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testUpdateBenPastHistoryDetails_NullBenMedHistory() throws ParseException, IEMRException {
		int result = commonNurseServiceImpl.updateBenPastHistoryDetails(null);

		assertEquals(0, result);
		verify(benMedHistoryRepo, times(0)).deleteExistingBenMedHistory(anyLong(), anyString());
		verify(benMedHistoryRepo, times(0)).saveAll(anyList());
	}

	@Test
	public void testUpdateBenPastHistoryDetails_NoHistory() throws ParseException, IEMRException {
		benMedHistory.setBenPastHistory(new ArrayList<>());

		int result = commonNurseServiceImpl.updateBenPastHistoryDetails(benMedHistory);

		assertEquals(1, result);
		verify(benMedHistoryRepo, times(1)).deleteExistingBenMedHistory(anyLong(), anyString());
		verify(benMedHistoryRepo, times(0)).saveAll(anyList());
	}

	@Test
	public void testUpdateBenComorbidConditions_Success() throws IEMRException {
		when(bencomrbidityCondRepo.getBenComrbidityCondHistoryStatus(anyLong(), anyLong()))
				.thenReturn(new ArrayList<>());
		when(bencomrbidityCondRepo.saveAll(anyList())).thenReturn(bencomrbidityCondDetailsList);

		int result = commonNurseServiceImpl.updateBenComorbidConditions(wrapperComorbidCondDetails);

		assertEquals(1, result);
		verify(bencomrbidityCondRepo, times(1)).deleteExistingBenComrbidityCondDetails(anyLong(), anyString());
		verify(bencomrbidityCondRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testUpdateBenComorbidConditions_NullWrapper() throws IEMRException {
		int result = commonNurseServiceImpl.updateBenComorbidConditions(null);

		assertEquals(0, result);
		verify(bencomrbidityCondRepo, never()).deleteExistingBenComrbidityCondDetails(anyLong(), anyString());
		verify(bencomrbidityCondRepo, never()).saveAll(anyList());
	}

	@Test
	public void testUpdateBenComorbidConditions_EmptyDetails() throws IEMRException {
		wrapperComorbidCondDetails.setComrbidityConds(new ArrayList<>());

		int result = commonNurseServiceImpl.updateBenComorbidConditions(wrapperComorbidCondDetails);

		assertEquals(1, result);
		verify(bencomrbidityCondRepo, times(1)).deleteExistingBenComrbidityCondDetails(anyLong(), anyString());
		verify(bencomrbidityCondRepo, never()).saveAll(anyList());
	}

	@Test
	public void testUpdateBenMedicationHistory_NullWrapper() throws IEMRException {
		WrapperMedicationHistory wrapperMedicationHistory = null;

		int result = commonNurseServiceImpl.updateBenMedicationHistory(wrapperMedicationHistory);

		assertEquals(0, result);
	}

	@Test
	public void testUpdateBenMedicationHistory_DeleteAndSave() throws IEMRException {
		WrapperMedicationHistory wrapperMedicationHistory = new WrapperMedicationHistory();
		wrapperMedicationHistory.setBeneficiaryRegID(1L);
		wrapperMedicationHistory.setVisitCode(1L);

		ArrayList<BenMedicationHistory> medicationHistoryList = new ArrayList<>();
		BenMedicationHistory medicationHistory = new BenMedicationHistory();
		medicationHistoryList.add(medicationHistory);
		wrapperMedicationHistory.setBenMedicationHistoryDetails(medicationHistoryList);

		ArrayList<Object[]> medicationHistoryStatuses = new ArrayList<>();
		Object[] status = new Object[] { 1L, "N" };
		medicationHistoryStatuses.add(status);

		when(benMedicationHistoryRepo.getBenMedicationHistoryStatus(1L, 1L)).thenReturn(medicationHistoryStatuses);
		when(benMedicationHistoryRepo.deleteExistingBenMedicationHistory(1L, "N")).thenReturn(1);
		when(benMedicationHistoryRepo.saveAll(medicationHistoryList)).thenReturn(medicationHistoryList);

		int result = commonNurseServiceImpl.updateBenMedicationHistory(wrapperMedicationHistory);

		assertEquals(1, result);
		verify(benMedicationHistoryRepo, times(1)).getBenMedicationHistoryStatus(1L, 1L);
		verify(benMedicationHistoryRepo, times(1)).deleteExistingBenMedicationHistory(1L, "N");
		verify(benMedicationHistoryRepo, times(1)).saveAll(medicationHistoryList);
	}

	@Test
	public void testUpdateBenMedicationHistory_NoNewDetails() throws IEMRException {
		WrapperMedicationHistory wrapperMedicationHistory = new WrapperMedicationHistory();
		wrapperMedicationHistory.setBeneficiaryRegID(1L);
		wrapperMedicationHistory.setVisitCode(1L);

		ArrayList<BenMedicationHistory> medicationHistoryList = new ArrayList<>();
		wrapperMedicationHistory.setBenMedicationHistoryDetails(medicationHistoryList);

		ArrayList<Object[]> medicationHistoryStatuses = new ArrayList<>();
		Object[] status = new Object[] { 1L, "N" };
		medicationHistoryStatuses.add(status);

		when(benMedicationHistoryRepo.getBenMedicationHistoryStatus(1L, 1L)).thenReturn(medicationHistoryStatuses);
		when(benMedicationHistoryRepo.deleteExistingBenMedicationHistory(1L, "N")).thenReturn(1);

		int result = commonNurseServiceImpl.updateBenMedicationHistory(wrapperMedicationHistory);

		assertEquals(1, result);
		verify(benMedicationHistoryRepo, times(1)).getBenMedicationHistoryStatus(1L, 1L);
		verify(benMedicationHistoryRepo, times(1)).deleteExistingBenMedicationHistory(1L, "N");
		verify(benMedicationHistoryRepo, never()).saveAll(anyList());
	}

	@Test
	public void testUpdateBenPersonalHistory_NullBenPersonalHabit() throws IEMRException {
		int result = commonNurseServiceImpl.updateBenPersonalHistory(null);
		assertEquals(0, result);
		verify(benPersonalHabitRepo, never()).deleteExistingBenPersonalHistory(anyInt(), anyString());
		verify(benPersonalHabitRepo, never()).saveAll(anyList());
	}

	@Test
	public void testUpdateBenPersonalHistory_EmptyPersonalHabits() throws IEMRException {
		benPersonalHabit.setPersonalHistory(personalHabits);
		when(benPersonalHabitRepo.getBenPersonalHistoryStatus(1L, 1L)).thenReturn(new ArrayList<>());

		int result = commonNurseServiceImpl.updateBenPersonalHistory(benPersonalHabit);
		assertEquals(1, result);
		verify(benPersonalHabitRepo, times(1)).getBenPersonalHistoryStatus(1L, 1L);
		verify(benPersonalHabitRepo, never()).saveAll(anyList());
	}

	@Test
	public void testUpdateBenPersonalHistory_NonEmptyPersonalHabits() throws IEMRException {
		personalHabits.add(benPersonalHabit);
		benPersonalHabit.setPersonalHistory(personalHabits);
		when(benPersonalHabitRepo.getBenPersonalHistoryStatus(1L, 1L)).thenReturn(new ArrayList<>());
		when(benPersonalHabitRepo.saveAll(personalHabits)).thenReturn(personalHabits);

		int result = commonNurseServiceImpl.updateBenPersonalHistory(benPersonalHabit);
		assertEquals(1, result);
		verify(benPersonalHabitRepo, times(1)).getBenPersonalHistoryStatus(1L, 1L);
		verify(benPersonalHabitRepo, times(1)).saveAll(personalHabits);
	}

	@Test
	public void testUpdateBenPersonalHistory_DeleteFails() throws IEMRException {
		personalHabits.add(benPersonalHabit);
		benPersonalHabit.setPersonalHistory(personalHabits);
		ArrayList<Object[]> statusList = new ArrayList<>();
		statusList.add(new Object[] { 1, "N" });
		when(benPersonalHabitRepo.getBenPersonalHistoryStatus(1L, 1L)).thenReturn(statusList);
		when(benPersonalHabitRepo.deleteExistingBenPersonalHistory(1, "N")).thenReturn(0);

		int result = commonNurseServiceImpl.updateBenPersonalHistory(benPersonalHabit);
		assertEquals(0, result);
		verify(benPersonalHabitRepo, times(1)).getBenPersonalHistoryStatus(1L, 1L);
		verify(benPersonalHabitRepo, times(1)).deleteExistingBenPersonalHistory(1, "N");
		verify(benPersonalHabitRepo, never()).saveAll(personalHabits);
	}

	@Test
	public void testUpdateBenAllergicHistory_Success() throws IEMRException {
		when(benAllergyHistoryRepo.getBenAllergyHistoryStatus(1L, 1L)).thenReturn(new ArrayList<>());
		when(benAllergyHistoryRepo.saveAll(allergyList)).thenReturn(allergyList);

		int result = commonNurseServiceImpl.updateBenAllergicHistory(benAllergyHistory);

		assertEquals(1, result);
		verify(benAllergyHistoryRepo, times(1)).deleteExistingBenAllergyHistory(anyLong(), anyString());
		verify(benAllergyHistoryRepo, times(1)).saveAll(allergyList);
	}

	@Test
	public void testUpdateBenAllergicHistory_EmptyAllergyList() throws IEMRException {
		benAllergyHistory.setBenAllergicHistory(new ArrayList<>());

		int result = commonNurseServiceImpl.updateBenAllergicHistory(benAllergyHistory);

		assertEquals(0, result);
		verify(benAllergyHistoryRepo, never()).deleteExistingBenAllergyHistory(anyLong(), anyString());
		verify(benAllergyHistoryRepo, never()).saveAll(anyList());
	}

	@Test
	public void testUpdateBenAllergicHistory_NullInput() throws IEMRException {
		int result = commonNurseServiceImpl.updateBenAllergicHistory(null);

		assertEquals(0, result);
		verify(benAllergyHistoryRepo, never()).getBenAllergyHistoryStatus(anyLong(), anyLong());
		verify(benAllergyHistoryRepo, never()).deleteExistingBenAllergyHistory(anyLong(), anyString());
		verify(benAllergyHistoryRepo, never()).saveAll(anyList());
	}

	@Test
	public void testUpdateBenFamilyHistory_NullInput() throws IEMRException {
		int result = commonNurseServiceImpl.updateBenFamilyHistory(null);
		assertEquals(1, result);
	}

	@Test
	public void testUpdateBenFamilyHistory_Success() throws IEMRException {
		when(benFamilyHistoryRepo.getBenFamilyHistoryStatus(anyLong(), anyLong())).thenReturn(new ArrayList<>());
		when(benFamilyHistoryRepo.saveAll(anyList())).thenReturn(new ArrayList<>());

		int result = commonNurseServiceImpl.updateBenFamilyHistory(benFamilyHistory);

		verify(benFamilyHistoryRepo, times(1)).getBenFamilyHistoryStatus(anyLong(), anyLong());
		verify(benFamilyHistoryRepo, times(1)).deleteExistingBenFamilyHistory(anyLong(), anyString());
		verify(benFamilyHistoryRepo, times(1)).saveAll(anyList());
		assertEquals(1, result);
	}

	@Test
	public void testUpdateBenFamilyHistory_Exception() throws IEMRException {
		when(benFamilyHistoryRepo.getBenFamilyHistoryStatus(anyLong(), anyLong()))
				.thenThrow(new IEMRException("Error"));

		assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.updateBenFamilyHistory(benFamilyHistory);
		});
	}

	@Test
	public void testUpdateMenstrualHistory_NullInput() throws IEMRException {
		int result = commonNurseServiceImpl.updateMenstrualHistory(null);
		assertEquals(0, result);
	}

	@Test
	public void testUpdateMenstrualHistory_UpdateExistingRecord() throws IEMRException {
		when(benMenstrualDetailsRepo.getBenMenstrualDetailStatus(1L, 1L)).thenReturn("Y");
		when(benMenstrualDetailsRepo.updateMenstrualDetails(anyLong(), anyString(), anyString(), anyLong(), anyString(),
				anyLong(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(),
				anyLong())).thenReturn(1);

		int result = commonNurseServiceImpl.updateMenstrualHistory(benMenstrualDetails);

		assertEquals(1, result);
		verify(benMenstrualDetailsRepo, times(1)).getBenMenstrualDetailStatus(1L, 1L);
		verify(benMenstrualDetailsRepo, times(1)).updateMenstrualDetails(anyLong(), anyString(), anyString(), anyLong(),
				anyString(), anyLong(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
				anyLong(), anyLong());
	}

	@Test
	public void testUpdateMenstrualHistory_SaveNewRecord() throws IEMRException {
		when(benMenstrualDetailsRepo.getBenMenstrualDetailStatus(1L, 1L)).thenReturn(null);
		when(benMenstrualDetailsRepo.save(any(BenMenstrualDetails.class))).thenReturn(benMenstrualDetails);

		int result = commonNurseServiceImpl.updateMenstrualHistory(benMenstrualDetails);

		assertEquals(1, result);
		verify(benMenstrualDetailsRepo, times(1)).getBenMenstrualDetailStatus(1L, 1L);
		verify(benMenstrualDetailsRepo, times(1)).save(any(BenMenstrualDetails.class));
	}

	@Test
	public void testUpdatePastObstetricHistory_Success() throws IEMRException {
		when(femaleObstetricHistoryRepo.getBenObstetricHistoryStatus(anyLong(), anyLong()))
				.thenReturn(new ArrayList<>());
		when(femaleObstetricHistoryRepo.saveAll(anyList()))
				.thenReturn(wrapperFemaleObstetricHistory.getFemaleObstetricHistoryDetails());

		int result = commonNurseServiceImpl.updatePastObstetricHistory(wrapperFemaleObstetricHistory);

		assertEquals(1, result);
		verify(femaleObstetricHistoryRepo, times(1)).getBenObstetricHistoryStatus(anyLong(), anyLong());
		verify(femaleObstetricHistoryRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testUpdatePastObstetricHistory_NullInput() throws IEMRException {
		int result = commonNurseServiceImpl.updatePastObstetricHistory(null);

		assertEquals(0, result);
		verify(femaleObstetricHistoryRepo, times(0)).getBenObstetricHistoryStatus(anyLong(), anyLong());
		verify(femaleObstetricHistoryRepo, times(0)).saveAll(anyList());
	}

	@Test
	public void testUpdatePastObstetricHistory_DeleteExistingHistory() throws IEMRException {
		ArrayList<Object[]> existingHistoryStatuses = new ArrayList<>();
		existingHistoryStatuses.add(new Object[] { 1L, "Y" });
		when(femaleObstetricHistoryRepo.getBenObstetricHistoryStatus(anyLong(), anyLong()))
				.thenReturn(existingHistoryStatuses);
		when(femaleObstetricHistoryRepo.saveAll(anyList()))
				.thenReturn(wrapperFemaleObstetricHistory.getFemaleObstetricHistoryDetails());

		int result = commonNurseServiceImpl.updatePastObstetricHistory(wrapperFemaleObstetricHistory);

		assertEquals(1, result);
		verify(femaleObstetricHistoryRepo, times(1)).getBenObstetricHistoryStatus(anyLong(), anyLong());
		verify(femaleObstetricHistoryRepo, times(1)).deleteExistingObstetricHistory(anyLong(), anyString());
		verify(femaleObstetricHistoryRepo, times(1)).saveAll(anyList());
	}

	@Test
	    public void testUpdatePastObstetricHistory_UpdateFailure() throws IEMRException {
	        when(femaleObstetricHistoryRepo.getBenObstetricHistoryStatus(anyLong(), anyLong()))
	                .thenReturn(new ArrayList<>());
	        when(femaleObstetricHistoryRepo.saveAll(anyList())).thenReturn(new ArrayList<>());

	        int result = commonNurseServiceImpl.updatePastObstetricHistory(wrapperFemaleObstetricHistory);

	        assertEquals(0, result);
	        verify(femaleObstetricHistoryRepo, times(1)).getBenObstetricHistoryStatus(anyLong(), anyLong());
	        verify(femaleObstetricHistoryRepo, times(1)).saveAll(anyList());
	        @Test
	        public void testUpdateChildOptionalVaccineDetail_NullWrapper() throws IEMRException {
	            int result = commonNurseServiceImpl.updateChildOptionalVaccineDetail(null);
	            assertEquals(0, result);
	            verify(childOptionalVaccineDetailRepo, never()).getBenChildOptionalVaccineHistoryStatus(anyLong(), anyLong());
	        }

	        @Test
	        public void testUpdateChildOptionalVaccineDetail_ValidData() throws IEMRException {
	            ChildOptionalVaccineDetail detail = new ChildOptionalVaccineDetail();
	            childOptionalVaccineDetails.add(detail);
	            wrapperChildOptionalVaccineDetail.setBeneficiaryRegID(1L);
	            wrapperChildOptionalVaccineDetail.setVisitCode(1L);

	            when(childOptionalVaccineDetailRepo.getBenChildOptionalVaccineHistoryStatus(anyLong(), anyLong()))
	                    .thenReturn(new ArrayList<>());

	            int result = commonNurseServiceImpl.updateChildOptionalVaccineDetail(wrapperChildOptionalVaccineDetail);

	            assertEquals(1, result);
	            verify(childOptionalVaccineDetailRepo, times(1)).getBenChildOptionalVaccineHistoryStatus(anyLong(), anyLong());
	            verify(childOptionalVaccineDetailRepo, times(1)).saveAll(anyList());
	        }

	        @Test
	        public void testUpdateChildOptionalVaccineDetail_NoDetails() throws IEMRException {
	            wrapperChildOptionalVaccineDetail.setBeneficiaryRegID(1L);
	            wrapperChildOptionalVaccineDetail.setVisitCode(1L);

	            when(childOptionalVaccineDetailRepo.getBenChildOptionalVaccineHistoryStatus(anyLong(), anyLong()))
	                    .thenReturn(new ArrayList<>());

	            int result = commonNurseServiceImpl.updateChildOptionalVaccineDetail(wrapperChildOptionalVaccineDetail);

	            assertEquals(1, result);
	            verify(childOptionalVaccineDetailRepo, times(1)).getBenChildOptionalVaccineHistoryStatus(anyLong(), anyLong());
	            verify(childOptionalVaccineDetailRepo, never()).saveAll(anyList());
	        }

	        @Test
	        public void testUpdateChildOptionalVaccineDetail_StatusNotN() throws IEMRException {
	            wrapperChildOptionalVaccineDetail.setBeneficiaryRegID(1L);
	            wrapperChildOptionalVaccineDetail.setVisitCode(1L);

	            ArrayList<Object[]> statusList = new ArrayList<>();
	            statusList.add(new Object[]{1L, "U"});
	            when(childOptionalVaccineDetailRepo.getBenChildOptionalVaccineHistoryStatus(anyLong(), anyLong()))
	                    .thenReturn(statusList);

	            int result = commonNurseServiceImpl.updateChildOptionalVaccineDetail(wrapperChildOptionalVaccineDetail);

	            assertEquals(1, result);
	            verify(childOptionalVaccineDetailRepo, times(1)).getBenChildOptionalVaccineHistoryStatus(anyLong(), anyLong());
	            verify(childOptionalVaccineDetailRepo, times(1)).deleteExistingChildOptionalVaccineDetail(anyLong(), anyString());
	            verify(childOptionalVaccineDetailRepo, times(1)).saveAll(anyList());
	        }	    }

	@Test
	public void testUpdateChildImmunizationDetail() throws IEMRException {
		when(childVaccineDetail.getBeneficiaryRegID()).thenReturn("regID");
		when(childVaccineDetail.getVisitCode()).thenReturn("visitCode");
		when(childVaccineDetail1Repo.getBenChildVaccineDetailStatus("regID", "visitCode"))
				.thenReturn(new ArrayList<Object[]>());

		when(childVaccineDetail.getDefaultReceivingAge()).thenReturn("age");
		when(childVaccineDetail.getVaccineName()).thenReturn("vaccineName");
		when(childVaccineDetail1Repo.updateChildANCImmunization(anyString(), anyString(), anyString(), anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(1);

		int result = commonNurseServiceImpl.updateChildImmunizationDetail(wrapperImmunizationHistory);

		verify(childVaccineDetail1Repo, times(1)).updateChildANCImmunization(anyString(), anyString(), anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
		assertEquals(1, result);
	}

	@Test
	public void testUpdatePhyHeadToToeExamination_NullExamination() throws IEMRException {
		int response = commonNurseServiceImpl.updatePhyHeadToToeExamination(null);
		assertEquals(0, response);
	}

	@Test
	public void testUpdatePhyHeadToToeExamination_ProcessedN() throws IEMRException {
		when(phyHeadToToeExaminationRepo.getBenHeadToToeExaminationStatus(1L, 1L)).thenReturn("N");

		int response = commonNurseServiceImpl.updatePhyHeadToToeExamination(headToToeExamination);

		assertEquals(1, response);
		verify(phyHeadToToeExaminationRepo, times(1)).updatePhyHeadToToeExamination(eq("Normal"), eq("Normal"),
				eq("Normal"), eq("Normal"), eq("Normal"), eq("Normal"), eq("Normal"), eq("Normal"), eq("Normal"),
				eq("Normal"), eq("Normal"), eq("Normal"), eq("Normal"), eq("Normal"), eq("Tester"), eq("N"), eq(1L),
				eq(1L), eq("Normal"));
	}

	@Test
	public void testUpdatePhyHeadToToeExamination_ProcessedNotN() throws IEMRException {
		when(phyHeadToToeExaminationRepo.getBenHeadToToeExaminationStatus(1L, 1L)).thenReturn("U");

		int response = commonNurseServiceImpl.updatePhyHeadToToeExamination(headToToeExamination);

		assertEquals(1, response);
		verify(phyHeadToToeExaminationRepo, times(1)).updatePhyHeadToToeExamination(eq("Normal"), eq("Normal"),
				eq("Normal"), eq("Normal"), eq("Normal"), eq("Normal"), eq("Normal"), eq("Normal"), eq("Normal"),
				eq("Normal"), eq("Normal"), eq("Normal"), eq("Normal"), eq("Normal"), eq("Tester"), eq("U"), eq(1L),
				eq(1L), eq("Normal"));
	}

	@Test
	public void testUpdateSysCardiovascularExamination_Success() throws IEMRException {
		when(sysCardiovascularExaminationRepo.getBenCardiovascularExaminationStatus(1L, 1L)).thenReturn("N");
		when(sysCardiovascularExaminationRepo.updateSysCardiovascularExamination(anyString(), anyString(), anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong(),
				anyLong())).thenReturn(1);

		int response = commonNurseServiceImpl.updateSysCardiovascularExamination(sysCardiovascularExamination);

		assertEquals(1, response);
		verify(sysCardiovascularExaminationRepo, times(1)).updateSysCardiovascularExamination(anyString(), anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
				anyLong(), anyLong());
	}

	@Test
	public void testUpdateSysCardiovascularExamination_NullData() throws IEMRException {
		int response = commonNurseServiceImpl.updateSysCardiovascularExamination(null);

		assertEquals(0, response);
		verify(sysCardiovascularExaminationRepo, times(0)).updateSysCardiovascularExamination(anyString(), anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
				anyLong(), anyLong());
	}

	@Test
	public void testUpdateSysCardiovascularExamination_AlreadyProcessed() throws IEMRException {
		when(sysCardiovascularExaminationRepo.getBenCardiovascularExaminationStatus(1L, 1L)).thenReturn("U");

		int response = commonNurseServiceImpl.updateSysCardiovascularExamination(sysCardiovascularExamination);

		assertEquals(1, response);
		verify(sysCardiovascularExaminationRepo, times(1)).updateSysCardiovascularExamination(anyString(), anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
				anyLong(), anyLong());
	}

	@Test
	public void testUpdateSysCentralNervousExamination_NullInput() throws IEMRException {
		int result = commonNurseServiceImpl.updateSysCentralNervousExamination(null);
		assertEquals(0, result);
	}

	@Test
	public void testUpdateSysCentralNervousExamination_ProcessedN() throws IEMRException {
		when(sysCentralNervousExaminationRepo.getBenCentralNervousExaminationStatus(1L, 1L)).thenReturn("N");

		int result = commonNurseServiceImpl.updateSysCentralNervousExamination(sysCentralNervousExamination);

		assertEquals(1, result);
		verify(sysCentralNervousExaminationRepo, times(1)).updateSysCentralNervousExamination(eq("Right"), eq("Normal"),
				eq("Normal"), eq("Normal"), eq("Normal"), eq("Normal"), eq("None"), eq("Normal"), eq("Tester"), eq("N"),
				eq(1L), eq(1L));
	}

	@Test
	public void testUpdateSysCentralNervousExamination_ProcessedNotN() throws IEMRException {
		when(sysCentralNervousExaminationRepo.getBenCentralNervousExaminationStatus(1L, 1L)).thenReturn("U");

		int result = commonNurseServiceImpl.updateSysCentralNervousExamination(sysCentralNervousExamination);

		assertEquals(1, result);
		verify(sysCentralNervousExaminationRepo, times(1)).updateSysCentralNervousExamination(eq("Right"), eq("Normal"),
				eq("Normal"), eq("Normal"), eq("Normal"), eq("Normal"), eq("None"), eq("Normal"), eq("Tester"), eq("U"),
				eq(1L), eq(1L));
	}

	@Test
	public void testUpdateSysMusculoskeletalSystemExamination_NullInput() throws IEMRException {
		int result = commonNurseServiceImpl.updateSysMusculoskeletalSystemExamination(null);
		assertEquals(0, result);
	}

	@Test
	public void testUpdateSysMusculoskeletalSystemExamination_ProcessedN() throws IEMRException {
		when(sysMusculoskeletalSystemExaminationRepo.getBenMusculoskeletalSystemExaminationStatus(1L, 1L))
				.thenReturn("N");

		int result = commonNurseServiceImpl.updateSysMusculoskeletalSystemExamination(musculoskeletalSystem);

		assertEquals(1, result);
		verify(sysMusculoskeletalSystemExaminationRepo, times(1)).updateSysMusculoskeletalSystemExamination(
				eq("Normal"), eq("Bilateral"), eq("None"), eq("Bilateral"), eq("None"), eq("Bilateral"), eq("None"),
				eq("Normal"), eq("Normal"), eq("Tester"), eq("N"), eq(1L), eq(1L));
	}

	@Test
	public void testUpdateSysMusculoskeletalSystemExamination_ProcessedNotN() throws IEMRException {
		when(sysMusculoskeletalSystemExaminationRepo.getBenMusculoskeletalSystemExaminationStatus(1L, 1L))
				.thenReturn("U");

		int result = commonNurseServiceImpl.updateSysMusculoskeletalSystemExamination(musculoskeletalSystem);

		assertEquals(1, result);
		verify(sysMusculoskeletalSystemExaminationRepo, times(1)).updateSysMusculoskeletalSystemExamination(
				eq("Normal"), eq("Bilateral"), eq("None"), eq("Bilateral"), eq("None"), eq("Bilateral"), eq("None"),
				eq("Normal"), eq("Normal"), eq("Tester"), eq("U"), eq(1L), eq(1L));
	}

	@Test
	public void testUpdateSysGenitourinarySystemExamination_NullInput() throws IEMRException {
		int result = commonNurseServiceImpl.updateSysGenitourinarySystemExamination(null);
		assertEquals(0, result);
	}

	@Test
	public void testUpdateSysGenitourinarySystemExamination_ProcessedN() throws IEMRException {
		when(sysGenitourinarySystemExaminationRepo.getBenGenitourinarySystemExaminationStatus(1L, 1L)).thenReturn("N");

		int result = commonNurseServiceImpl.updateSysGenitourinarySystemExamination(genitourinarySystem);

		assertEquals(1, result);
		verify(sysGenitourinarySystemExaminationRepo, times(1)).updateSysGenitourinarySystemExamination(eq("Normal"),
				eq("Normal"), eq("Normal"), eq("Tester"), eq("N"), eq(1L), eq(1L));
	}

	@Test
	public void testUpdateSysGenitourinarySystemExamination_ProcessedNotN() throws IEMRException {
		when(sysGenitourinarySystemExaminationRepo.getBenGenitourinarySystemExaminationStatus(1L, 1L)).thenReturn("U");

		int result = commonNurseServiceImpl.updateSysGenitourinarySystemExamination(genitourinarySystem);

		assertEquals(1, result);
		verify(sysGenitourinarySystemExaminationRepo, times(1)).updateSysGenitourinarySystemExamination(eq("Normal"),
				eq("Normal"), eq("Normal"), eq("Tester"), eq("U"), eq(1L), eq(1L));
	}

	@Test
	public void testUpdateOralExamination_WhenOralExaminationExists() throws IEMRException {
		OralExamination oralExaminationRS = new OralExamination();
		when(oralExaminationRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted("123", "visit1", false))
				.thenReturn(oralExaminationRS);

		int result = commonNurseServiceImpl.updateOralExamination(oralExamination);

		assertEquals(1, result);
		verify(oralExaminationRepo).save(oralExaminationRS);
		assertEquals("U", oralExaminationRS.getProcessed());
		assertEquals("Yes", oralExaminationRS.getLimitedMouthOpening());
		assertEquals("No", oralExaminationRS.getPremalignantLesions());
		assertEquals("Type1", oralExaminationRS.getPreMalignantLesionType());
		assertEquals("No", oralExaminationRS.getProlongedIrritation());
		assertEquals("No", oralExaminationRS.getChronicBurningSensation());
		assertEquals("Observation", oralExaminationRS.getObservation());
		assertEquals("User1", oralExaminationRS.getModifiedBy());
	}

	@Test
	public void testUpdateOralExamination_WhenOralExaminationDoesNotExist() throws IEMRException {
		when(oralExaminationRepo.findByBeneficiaryRegIDAndVisitCodeAndDeleted("123", "visit1", false)).thenReturn(null);

		int result = commonNurseServiceImpl.updateOralExamination(oralExamination);

		assertEquals(1, result);
		verify(oralExaminationRepo).save(oralExamination);
		assertEquals("User1", oralExamination.getCreatedBy());
	}

	@Test
	public void testSavePrescriptionDetailsAndGetPrescriptionID() {
		when(prescriptionDetailRepo.save(any(PrescriptionDetail.class))).thenReturn(1L);

		Long prescriptionID = commonNurseServiceImpl.savePrescriptionDetailsAndGetPrescriptionID(1L, 1L, 1, "User1",
				"Investigation", 1L, 1, 1, "Instruction", "Yes", new ArrayList<>());

		assertNotNull(prescriptionID);
		assertEquals(1L, prescriptionID);
		verify(prescriptionDetailRepo).save(any(PrescriptionDetail.class));
	}

	@Test
	public void testSavePrescriptionCovid_Success() {
		// Arrange
		Long benRegID = 1L;
		Long benVisitID = 1L;
		Integer psmID = 1;
		String createdBy = "testUser";
		String externalInvestigation = "testInvestigation";
		Long benVisitCode = 1L;
		Integer vanID = 1;
		Integer parkingPlaceID = 1;
		String instruction = "testInstruction";
		String doctorDiagnosis = "testDiagnosis";
		String counsellingProvided = "Yes";

		PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
		prescriptionDetail.setPrescriptionID(1L);
		when(prescriptionDetailRepo.save(any(PrescriptionDetail.class))).thenReturn(prescriptionDetail);

		// Act
		Long result = commonNurseServiceImpl.savePrescriptionCovid(benRegID, benVisitID, psmID, createdBy,
				externalInvestigation, benVisitCode, vanID, parkingPlaceID, instruction, doctorDiagnosis,
				counsellingProvided);

		// Assert
		assertNotNull(result);
		assertEquals(1L, result);
		verify(prescriptionDetailRepo, times(1)).save(any(PrescriptionDetail.class));
	}

	@Test
	public void testSavePrescriptionCovid_NullOptionalFields() {
		// Arrange
		Long benRegID = 1L;
		Long benVisitID = 1L;
		Integer psmID = 1;
		String createdBy = "testUser";
		String externalInvestigation = null;
		Long benVisitCode = 1L;
		Integer vanID = 1;
		Integer parkingPlaceID = 1;
		String instruction = null;
		String doctorDiagnosis = null;
		String counsellingProvided = "Yes";

		PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
		prescriptionDetail.setPrescriptionID(1L);
		when(prescriptionDetailRepo.save(any(PrescriptionDetail.class))).thenReturn(prescriptionDetail);

		// Act
		Long result = commonNurseServiceImpl.savePrescriptionCovid(benRegID, benVisitID, psmID, createdBy,
				externalInvestigation, benVisitCode, vanID, parkingPlaceID, instruction, doctorDiagnosis,
				counsellingProvided);

		// Assert
		assertNotNull(result);
		assertEquals(1L, result);
		verify(prescriptionDetailRepo, times(1)).save(any(PrescriptionDetail.class));
	}

	@Test
	public void testSavePrescriptionCovid_MissingRequiredFields() {
		// Arrange
		Long benRegID = null;
		Long benVisitID = 1L;
		Integer psmID = 1;
		String createdBy = "testUser";
		String externalInvestigation = "testInvestigation";
		Long benVisitCode = 1L;
		Integer vanID = 1;
		Integer parkingPlaceID = 1;
		String instruction = "testInstruction";
		String doctorDiagnosis = "testDiagnosis";
		String counsellingProvided = "Yes";

		// Act & Assert
		assertThrows(NullPointerException.class, () -> {
			commonNurseServiceImpl.savePrescriptionCovid(benRegID, benVisitID, psmID, createdBy, externalInvestigation,
					benVisitCode, vanID, parkingPlaceID, instruction, doctorDiagnosis, counsellingProvided);
		});
	}

	@Test
	public void testSaveBeneficiaryPrescription() throws Exception {
		when(prescriptionDetailRepo.save(any(PrescriptionDetail.class))).thenReturn(prescriptionDetail);

		Long result = commonNurseServiceImpl.saveBeneficiaryPrescription(caseSheet);

		assertNotNull(result);
		assertEquals(prescriptionDetail.getBeneficiaryRegID(), result);
		verify(prescriptionDetailRepo, times(1)).save(any(PrescriptionDetail.class));
	}

	@Test
	public void testSaveBenPrescription() {
		when(prescriptionDetailRepo.save(any(PrescriptionDetail.class))).thenReturn(prescriptionDetail);

		Long result = commonNurseServiceImpl.saveBenPrescription(prescriptionDetail);

		assertNotNull(result);
		assertEquals(prescriptionDetail.getBeneficiaryRegID(), result);
		verify(prescriptionDetailRepo, times(1)).save(any(PrescriptionDetail.class));
	}

	@Test
	public void testUpdatePrescription_NewPrescription() {
		when(prescriptionDetailRepo.getGeneralOPDDiagnosisStatus(anyLong(), anyLong(), anyLong())).thenReturn(null);
		when(prescriptionDetailRepo.save(any(PrescriptionDetail.class))).thenReturn(prescriptionDetail);

		int result = commonNurseServiceImpl.updatePrescription(prescriptionDetail);

		assertEquals(1, result);
		verify(prescriptionDetailRepo, times(1)).save(any(PrescriptionDetail.class));
	}

	@Test
	public void testUpdatePrescription_ExistingPrescription() {
		PrescriptionDetail existingPrescription = new PrescriptionDetail();
		existingPrescription.setProcessed("U");
		when(prescriptionDetailRepo.getGeneralOPDDiagnosisStatus(anyLong(), anyLong(), anyLong()))
				.thenReturn(existingPrescription);
		when(prescriptionDetailRepo.save(any(PrescriptionDetail.class))).thenReturn(prescriptionDetail);

		int result = commonNurseServiceImpl.updatePrescription(prescriptionDetail);

		assertEquals(1, result);
		verify(prescriptionDetailRepo, times(1)).save(any(PrescriptionDetail.class));
	}

	@Test
	public void testUpdatePrescription_WithProvisionalDiagnosisList() {
		prescriptionDetail.getProvisionalDiagnosisList().add(new SCTDescription("Term1", "ConceptID1"));
		prescriptionDetail.getProvisionalDiagnosisList().add(new SCTDescription("Term2", "ConceptID2"));

		when(prescriptionDetailRepo.getGeneralOPDDiagnosisStatus(anyLong(), anyLong(), anyLong())).thenReturn(null);
		when(prescriptionDetailRepo.save(any(PrescriptionDetail.class))).thenReturn(prescriptionDetail);

		int result = commonNurseServiceImpl.updatePrescription(prescriptionDetail);

		assertEquals(1, result);
		verify(prescriptionDetailRepo, times(1)).save(any(PrescriptionDetail.class));
	}

	@Test
	public void testSaveBeneficiaryLabTestOrderDetails_ValidInput() {
		ArrayList<LabTestOrderDetail> labTestOrderDetails = new ArrayList<>();
		labTestOrderDetails.add(new LabTestOrderDetail());
		when(labTestOrderDetailRepo.saveAll(anyList())).thenReturn(labTestOrderDetails);

		Long result = commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(caseSheet, prescriptionID);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(labTestOrderDetailRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testSaveBeneficiaryLabTestOrderDetails_EmptyInput() {
		ArrayList<LabTestOrderDetail> labTestOrderDetails = new ArrayList<>();
		when(labTestOrderDetailRepo.saveAll(anyList())).thenReturn(labTestOrderDetails);

		Long result = commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(caseSheet, prescriptionID);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(labTestOrderDetailRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testSaveBeneficiaryLabTestOrderDetails_NullInput() {
		Long result = commonNurseServiceImpl.saveBeneficiaryLabTestOrderDetails(null, prescriptionID);

		assertNull(result);
		verify(labTestOrderDetailRepo, times(0)).saveAll(anyList());
	}

	@Test
	public void testGetQtyForOneDay_Tablet_OnceDaily() {
		double result = commonNurseService.getQtyForOneDay("Tablet", "One Tab", "Once Daily(OD)");
		assertEquals(1.0, result);
	}

	@Test
	public void testGetQtyForOneDay_Tablet_TwiceDaily() {
		double result = commonNurseService.getQtyForOneDay("Tablet", "One Tab", "Twice Daily(BD)");
		assertEquals(2.0, result);
	}

	@Test
	public void testGetQtyForOneDay_Tablet_ThriceDaily() {
		double result = commonNurseService.getQtyForOneDay("Tablet", "One Tab", "Thrice Daily (TID)");
		assertEquals(3.0, result);
	}

	@Test
	public void testGetQtyForOneDay_Tablet_FourTimesDaily() {
		double result = commonNurseService.getQtyForOneDay("Tablet", "One Tab", "Four Times in a Day (QID)");
		assertEquals(4.0, result);
	}

	@Test
	public void testGetQtyForOneDay_Capsule_OnceDaily() {
		double result = commonNurseService.getQtyForOneDay("Capsule", "One Tab", "Once Daily(OD)");
		assertEquals(1.0, result);
	}

	@Test
	public void testGetQtyForOneDay_Capsule_TwiceDaily() {
		double result = commonNurseService.getQtyForOneDay("Capsule", "One Tab", "Twice Daily(BD)");
		assertEquals(2.0, result);
	}

	@Test
	public void testGetQtyForOneDay_InvalidForm() {
		double result = commonNurseService.getQtyForOneDay("InvalidForm", "One Tab", "Once Daily(OD)");
		assertEquals(0.0, result);
	}

	@Test
	public void testGetQtyForOneDay_InvalidDose() {
		double result = commonNurseService.getQtyForOneDay("Tablet", "InvalidDose", "Once Daily(OD)");
		assertEquals(0.0, result);
	}

	@Test
	public void testGetQtyForOneDay_InvalidFrequency() {
		double result = commonNurseService.getQtyForOneDay("Tablet", "One Tab", "InvalidFrequency");
		assertEquals(0.0, result);
	}

	@Test
	public void testSaveBenInvestigationDetails_ValidData() {
		when(prescriptionDetailRepo.save(any())).thenReturn(new PrescriptionDetail());
		when(labTestOrderDetailRepo.saveAll(any())).thenReturn(wrapperBenInvestigationANC.getLaboratoryList());

		int result = commonNurseServiceImpl.saveBenInvestigationDetails(wrapperBenInvestigationANC);

		assertEquals(1, result);
		verify(prescriptionDetailRepo, times(1)).save(any());
		verify(labTestOrderDetailRepo, times(1)).saveAll(any());
	}

	@Test
	public void testSaveBenInvestigationDetails_NullData() {
		int result = commonNurseServiceImpl.saveBenInvestigationDetails(null);

		assertEquals(0, result);
		verify(prescriptionDetailRepo, times(0)).save(any());
		verify(labTestOrderDetailRepo, times(0)).saveAll(any());
	}

	@Test
	public void testSaveBenInvestigationDetails_InvalidData() {
		wrapperBenInvestigationANC.setLaboratoryList(null);

		int result = commonNurseServiceImpl.saveBenInvestigationDetails(wrapperBenInvestigationANC);

		assertEquals(0, result);
		verify(prescriptionDetailRepo, times(1)).save(any());
		verify(labTestOrderDetailRepo, times(0)).saveAll(any());
	}

	@Test
	public void testSaveBenInvestigation_ValidInput() {
		when(labTestOrderDetailRepo.saveAll(anyList())).thenReturn(labTestOrderDetails);

		Long result = commonNurseServiceImpl.saveBenInvestigation(wrapperBenInvestigationANC);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(labTestOrderDetailRepo, times(1)).saveAll(anyList());
	}

	@Test
	public void testSaveBenInvestigation_NullInput() {
		Long result = commonNurseServiceImpl.saveBenInvestigation(null);

		assertNull(result);
		verify(labTestOrderDetailRepo, times(0)).saveAll(anyList());
	}

	@Test
	public void testSaveBenInvestigation_EmptyInvestigationList() {
		wrapperBenInvestigationANC.setLaboratoryList(new ArrayList<>());

		Long result = commonNurseServiceImpl.saveBenInvestigation(wrapperBenInvestigationANC);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(labTestOrderDetailRepo, times(0)).saveAll(anyList());
	}

	@Test
	public void testUpdateBenVisitStatusFlag() {
		Long benVisitID = 1L;
		String status = "Completed";

		doReturn("Success").when(commonNurseServiceImplSpy).updateBenStatus(benVisitID, status);

		String result = commonNurseServiceImplSpy.updateBenVisitStatusFlag(benVisitID, status);

		assertEquals("Success", result);
		verify(commonNurseServiceImplSpy, times(1)).updateBenStatus(benVisitID, status);
	}

	@Test
	public void testUpdateBenStatus_Success() {
		Long benVisitID = 1L;
		String status = "U";

		when(benVisitDetailRepo.updateBenFlowStatus(status, benVisitID)).thenReturn(1);

		String response = commonNurseServiceImpl.updateBenStatus(benVisitID, status);

		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("status", "Updated Successfully");

		assertEquals(new Gson().toJson(expectedResponse), response);
		verify(benVisitDetailRepo, times(1)).updateBenFlowStatus(status, benVisitID);
	}

	@Test
	public void testUpdateBenStatus_Failure() {
		Long benVisitID = 1L;
		String status = "U";

		when(benVisitDetailRepo.updateBenFlowStatus(status, benVisitID)).thenReturn(0);

		String response = commonNurseServiceImpl.updateBenStatus(benVisitID, status);

		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("status", "Updated Successfully");

		assertNotEquals(new Gson().toJson(expectedResponse), response);
		verify(benVisitDetailRepo, times(1)).updateBenFlowStatus(status, benVisitID);
	}

	@Test
	public void testGetNurseWorkList() {
		when(reistrarRepoBenSearch.getNurseWorkList()).thenReturn(nurseWorkListData);

		String response = commonNurseServiceImpl.getNurseWorkList();

		String expectedResponse = WrapperRegWorklist.getRegistrarWorkList(nurseWorkListData);
		assertEquals(expectedResponse, response);

		verify(reistrarRepoBenSearch, times(1)).getNurseWorkList();
	}

	@Test
	public void testGetNurseWorkListNew() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();
		Timestamp timestamp = new Timestamp(sevenDaysAgo);

		when(beneficiaryFlowStatusRepo.getNurseWorklistNew(providerServiceMapId, vanID, timestamp))
				.thenReturn(beneficiaryFlowStatusList);

		String response = commonNurseServiceImpl.getNurseWorkListNew(providerServiceMapId, vanID);

		assertNotNull(response);
		assertTrue(response.contains("BeneficiaryFlowStatus"));
		verify(beneficiaryFlowStatusRepo, times(1)).getNurseWorklistNew(providerServiceMapId, vanID, timestamp);
	}

	@Test
	public void testGetNurseWorkListNewWithNullProviderServiceMapId() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();
		Timestamp timestamp = new Timestamp(sevenDaysAgo);

		when(beneficiaryFlowStatusRepo.getNurseWorklistNew(null, vanID, timestamp))
				.thenReturn(beneficiaryFlowStatusList);

		String response = commonNurseServiceImpl.getNurseWorkListNew(null, vanID);

		assertNotNull(response);
		assertTrue(response.contains("BeneficiaryFlowStatus"));
		verify(beneficiaryFlowStatusRepo, times(1)).getNurseWorklistNew(null, vanID, timestamp);
	}

	@Test
	public void testGetNurseWorkListNewWithNullVanID() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();
		Timestamp timestamp = new Timestamp(sevenDaysAgo);

		when(beneficiaryFlowStatusRepo.getNurseWorklistNew(providerServiceMapId, null, timestamp))
				.thenReturn(beneficiaryFlowStatusList);

		String response = commonNurseServiceImpl.getNurseWorkListNew(providerServiceMapId, null);

		assertNotNull(response);
		assertTrue(response.contains("BeneficiaryFlowStatus"));
		verify(beneficiaryFlowStatusRepo, times(1)).getNurseWorklistNew(providerServiceMapId, null, timestamp);
	}

	@Test
	public void testGetNurseWorkListNewWithEmptyList() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();
		Timestamp timestamp = new Timestamp(sevenDaysAgo);

		when(beneficiaryFlowStatusRepo.getNurseWorklistNew(providerServiceMapId, vanID, timestamp))
				.thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.getNurseWorkListNew(providerServiceMapId, vanID);

		assertNotNull(response);
		assertFalse(response.contains("BeneficiaryFlowStatus"));
		verify(beneficiaryFlowStatusRepo, times(1)).getNurseWorklistNew(providerServiceMapId, vanID, timestamp);
	}

	@Test
	public void testGetNurseWorkListTcCurrentDate_Success() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		Timestamp sevenDaysAgo = new Timestamp(cal.getTimeInMillis());

		when(beneficiaryFlowStatusRepo.getNurseWorklistCurrentDate(providerServiceMapId, sevenDaysAgo, vanID))
				.thenReturn(mockBeneficiaryFlowStatusList);

		String response = commonNurseServiceImpl.getNurseWorkListTcCurrentDate(providerServiceMapId, vanID);

		assertNotNull(response);
		assertTrue(response.contains("beneficiaryRegID"));
		verify(beneficiaryFlowStatusRepo, times(1)).getNurseWorklistCurrentDate(providerServiceMapId, sevenDaysAgo,
				vanID);
	}

	@Test
	public void testGetNurseWorkListTcCurrentDate_EmptyList() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		Timestamp sevenDaysAgo = new Timestamp(cal.getTimeInMillis());

		when(beneficiaryFlowStatusRepo.getNurseWorklistCurrentDate(providerServiceMapId, sevenDaysAgo, vanID))
				.thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.getNurseWorkListTcCurrentDate(providerServiceMapId, vanID);

		assertNotNull(response);
		assertTrue(response.contains("[]"));
		verify(beneficiaryFlowStatusRepo, times(1)).getNurseWorklistCurrentDate(providerServiceMapId, sevenDaysAgo,
				vanID);
	}

	@Test
	public void testGetNurseWorkListTcCurrentDate_Exception() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		Timestamp sevenDaysAgo = new Timestamp(cal.getTimeInMillis());

		when(beneficiaryFlowStatusRepo.getNurseWorklistCurrentDate(providerServiceMapId, sevenDaysAgo, vanID))
				.thenThrow(new RuntimeException("Database error"));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			commonNurseServiceImpl.getNurseWorkListTcCurrentDate(providerServiceMapId, vanID);
		});

		assertEquals("Database error", exception.getMessage());
		verify(beneficiaryFlowStatusRepo, times(1)).getNurseWorklistCurrentDate(providerServiceMapId, sevenDaysAgo,
				vanID);
	}

	@Test
	public void testGetNurseWorkListTcFutureDate() {
		when(beneficiaryFlowStatusRepo.getNurseWorklistFutureDate(providerServiceMapId, vanID))
				.thenReturn(beneficiaryFlowStatusList);

		String response = commonNurseServiceImpl.getNurseWorkListTcFutureDate(providerServiceMapId, vanID);

		assertNotNull(response);
		assertTrue(response.contains("\"beneficiaryRegID\":1"));
		assertTrue(response.contains("\"visitCode\":1"));
		verify(beneficiaryFlowStatusRepo, times(1)).getNurseWorklistFutureDate(providerServiceMapId, vanID);
	}

	@Test
	public void testGetLabWorkListNew() {
		// Arrange
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -labWL);
		long sevenDaysAgo = cal.getTimeInMillis();
		ArrayList<BeneficiaryFlowStatus> mockList = new ArrayList<>();
		BeneficiaryFlowStatus status = new BeneficiaryFlowStatus();
		mockList.add(status);

		when(beneficiaryFlowStatusRepo.getLabWorklistNew(providerServiceMapId, new Timestamp(sevenDaysAgo), vanID))
				.thenReturn(mockList);

		// Act
		String result = commonNurseServiceImpl.getLabWorkListNew(providerServiceMapId, vanID);

		// Assert
		assertNotNull(result);
		assertTrue(result.contains("BeneficiaryFlowStatus"));
		verify(beneficiaryFlowStatusRepo, times(1)).getLabWorklistNew(providerServiceMapId, new Timestamp(sevenDaysAgo),
				vanID);
	}

	@Test
	public void testGetRadiologistWorkListNew_ValidInputs() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();

		when(beneficiaryFlowStatusRepo.getRadiologistWorkListNew(providerServiceMapId, new Timestamp(sevenDaysAgo),
				vanID)).thenReturn(beneficiaryFlowStatusList);

		String response = commonNurseServiceImpl.getRadiologistWorkListNew(providerServiceMapId, vanID);

		assertNotNull(response);
		assertTrue(response.contains("beneficiaryRegID"));
		verify(beneficiaryFlowStatusRepo, times(1)).getRadiologistWorkListNew(providerServiceMapId,
				new Timestamp(sevenDaysAgo), vanID);
	}

	@Test
	public void testGetRadiologistWorkListNew_EmptyResults() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();

		when(beneficiaryFlowStatusRepo.getRadiologistWorkListNew(providerServiceMapId, new Timestamp(sevenDaysAgo),
				vanID)).thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.getRadiologistWorkListNew(providerServiceMapId, vanID);

		assertNotNull(response);
		assertFalse(response.contains("beneficiaryRegID"));
		verify(beneficiaryFlowStatusRepo, times(1)).getRadiologistWorkListNew(providerServiceMapId,
				new Timestamp(sevenDaysAgo), vanID);
	}

	@Test
	public void testGetRadiologistWorkListNew_NullResults() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();

		when(beneficiaryFlowStatusRepo.getRadiologistWorkListNew(providerServiceMapId, new Timestamp(sevenDaysAgo),
				vanID)).thenReturn(null);

		String response = commonNurseServiceImpl.getRadiologistWorkListNew(providerServiceMapId, vanID);

		assertNotNull(response);
		assertFalse(response.contains("beneficiaryRegID"));
		verify(beneficiaryFlowStatusRepo, times(1)).getRadiologistWorkListNew(providerServiceMapId,
				new Timestamp(sevenDaysAgo), vanID);
	}

	@Test
	public void testGetOncologistWorkListNew() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();
		Timestamp timestamp = new Timestamp(sevenDaysAgo);

		when(beneficiaryFlowStatusRepo.getOncologistWorkListNew(providerServiceMapId, timestamp, vanID))
				.thenReturn(beneficiaryFlowStatusList);

		String expectedJson = new Gson().toJson(beneficiaryFlowStatusList);
		String actualJson = commonNurseServiceImpl.getOncologistWorkListNew(providerServiceMapId, vanID);

		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void testGetPharmaWorkListNew() {
		when(beneficiaryFlowStatusRepo.getPharmaWorkListNew(providerServiceMapId, new Timestamp(sevenDaysAgo), vanID))
				.thenReturn(beneficiaryFlowStatusList);

		String response = commonNurseServiceImpl.getPharmaWorkListNew(providerServiceMapId, vanID);

		assertNotNull(response);
		assertTrue(response.contains("BeneficiaryFlowStatus"));
		verify(beneficiaryFlowStatusRepo, times(1)).getPharmaWorkListNew(providerServiceMapId,
				new Timestamp(sevenDaysAgo), vanID);
	}

	@Test
	public void testGetPharmaWorkListNew_EmptyList() {
		when(beneficiaryFlowStatusRepo.getPharmaWorkListNew(providerServiceMapId, new Timestamp(sevenDaysAgo), vanID))
				.thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.getPharmaWorkListNew(providerServiceMapId, vanID);

		assertNotNull(response);
		assertFalse(response.contains("BeneficiaryFlowStatus"));
		verify(beneficiaryFlowStatusRepo, times(1)).getPharmaWorkListNew(providerServiceMapId,
				new Timestamp(sevenDaysAgo), vanID);
	}

	@Test
	public void testSaveBenAdherenceDetails_Success() throws IEMRException {
		when(benAdherenceRepo.save(any(BenAdherence.class))).thenReturn(benAdherence);

		int result = commonNurseServiceImpl.saveBenAdherenceDetails(benAdherence);

		assertEquals(1, result);
		verify(benAdherenceRepo, times(1)).save(any(BenAdherence.class));
	}

	@Test
	public void testSaveBenAdherenceDetails_Failure() throws IEMRException {
		when(benAdherenceRepo.save(any(BenAdherence.class))).thenReturn(null);

		int result = commonNurseServiceImpl.saveBenAdherenceDetails(benAdherence);

		assertEquals(0, result);
		verify(benAdherenceRepo, times(1)).save(any(BenAdherence.class));
	}

	@Test
	public void testSaveCdssDetails_Success() throws IEMRException {
		when(cdssRepo.save(any(CDSS.class))).thenReturn(cdss);

		Long result = commonNurseService.saveCdssDetails(cdss);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(cdssRepo, times(1)).save(cdss);
	}

	@Test
	public void testSaveCdssDetails_Exception() {
		when(cdssRepo.save(any(CDSS.class))).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseService.saveCdssDetails(cdss);
		});

		assertEquals("DB-Error in saving cdss details", exception.getMessage());
		verify(cdssRepo, times(1)).save(cdss);
	}

	@Test
	public void testSaveChildDevelopmentHistory_Success() throws IEMRException {
		when(benChildDevelopmentHistoryRepo.save(any(BenChildDevelopmentHistory.class)))
				.thenReturn(benChildDevelopmentHistory);

		Long result = commonNurseService.saveChildDevelopmentHistory(benChildDevelopmentHistory);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(benChildDevelopmentHistoryRepo, times(1)).save(any(BenChildDevelopmentHistory.class));
	}

	@Test
	public void testSaveChildDevelopmentHistory_NullInput() {
		assertThrows(IEMRException.class, () -> {
			commonNurseService.saveChildDevelopmentHistory(null);
		});
		verify(benChildDevelopmentHistoryRepo, times(0)).save(any(BenChildDevelopmentHistory.class));
	}

	@Test
	public void testSaveChildDevelopmentHistory_InvalidData() throws IEMRException {
		when(benChildDevelopmentHistoryRepo.save(any(BenChildDevelopmentHistory.class))).thenReturn(null);

		Long result = commonNurseService.saveChildDevelopmentHistory(benChildDevelopmentHistory);

		assertNull(result);
		verify(benChildDevelopmentHistoryRepo, times(1)).save(any(BenChildDevelopmentHistory.class));
	}

	@Test
	public void testSaveChildFeedingHistory_Success() {
		when(childFeedingDetailsRepo.save(any(ChildFeedingDetails.class))).thenReturn(childFeedingDetails);

		Long result = commonNurseServiceImpl.saveChildFeedingHistory(childFeedingDetails);

		assertNotNull(result);
		assertEquals(1L, result);
		verify(childFeedingDetailsRepo, times(1)).save(any(ChildFeedingDetails.class));
	}

	@Test
	public void testSaveChildFeedingHistory_NullInput() {
		Long result = commonNurseServiceImpl.saveChildFeedingHistory(null);

		assertNull(result);
		verify(childFeedingDetailsRepo, times(0)).save(any(ChildFeedingDetails.class));
	}

	@Test
	public void testSaveChildFeedingHistory_SaveFailure() {
		when(childFeedingDetailsRepo.save(any(ChildFeedingDetails.class))).thenReturn(null);

		Long result = commonNurseServiceImpl.saveChildFeedingHistory(childFeedingDetails);

		assertNull(result);
		verify(childFeedingDetailsRepo, times(1)).save(any(ChildFeedingDetails.class));
	}

	@Test
	public void testSavePerinatalHistory_Success() {
		when(perinatalHistoryRepo.save(any(PerinatalHistory.class))).thenReturn(perinatalHistory);

		Long result = commonNurseService.savePerinatalHistory(perinatalHistory);

		assertNotNull(result);
		assertEquals(perinatalHistory.getID(), result);
		verify(perinatalHistoryRepo, times(1)).save(any(PerinatalHistory.class));
	}

	@Test
	public void testSavePerinatalHistory_NullData() {
		Long result = commonNurseService.savePerinatalHistory(null);

		assertNull(result);
		verify(perinatalHistoryRepo, times(0)).save(any(PerinatalHistory.class));
	}

	@Test
	public void testSavePerinatalHistory_InvalidData() {
		perinatalHistory.setID(null); // Assuming ID should not be null for a valid save
		when(perinatalHistoryRepo.save(any(PerinatalHistory.class))).thenReturn(perinatalHistory);

		Long result = commonNurseService.savePerinatalHistory(perinatalHistory);

		assertNotNull(result);
		assertEquals(perinatalHistory.getID(), result);
		verify(perinatalHistoryRepo, times(1)).save(any(PerinatalHistory.class));
	}

	@Test
	public void testGetBenAdherence() {
		when(benAdherenceRepo.getBenAdherence(beneficiaryRegID, visitCode)).thenReturn(mockResultList);

		String response = commonNurseServiceImpl.getBenAdherence(beneficiaryRegID, visitCode);

		BenAdherence expectedAdherence = BenAdherence.getBenAdherences(mockResultList);
		String expectedResponse = new Gson().toJson(expectedAdherence);

		assertEquals(expectedResponse, response);
		verify(benAdherenceRepo, times(1)).getBenAdherence(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBenAdherence_EmptyResult() {
		when(benAdherenceRepo.getBenAdherence(beneficiaryRegID, visitCode)).thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.getBenAdherence(beneficiaryRegID, visitCode);

		BenAdherence expectedAdherence = BenAdherence.getBenAdherences(new ArrayList<>());
		String expectedResponse = new Gson().toJson(expectedAdherence);

		assertEquals(expectedResponse, response);
		verify(benAdherenceRepo, times(1)).getBenAdherence(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetBenAdherence_NullResult() {
		when(benAdherenceRepo.getBenAdherence(beneficiaryRegID, visitCode)).thenReturn(null);

		String response = commonNurseServiceImpl.getBenAdherence(beneficiaryRegID, visitCode);

		BenAdherence expectedAdherence = BenAdherence.getBenAdherences(null);
		String expectedResponse = new Gson().toJson(expectedAdherence);

		assertEquals(expectedResponse, response);
		verify(benAdherenceRepo, times(1)).getBenAdherence(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetLabTestOrders() {
		ArrayList<Object[]> mockResult = new ArrayList<>();
		Object[] mockData = new Object[] { "test1", "test2" };
		mockResult.add(mockData);

		when(labTestOrderDetailRepo.getLabTestOrderDetails(beneficiaryRegID, visitCode)).thenReturn(mockResult);

		String expectedJson = new Gson().toJson(LabTestOrderDetail.getLabTestOrderDetails(mockResult));
		String actualJson = commonNurseServiceImpl.getLabTestOrders(beneficiaryRegID, visitCode);

		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void testGetLabTestOrders_EmptyResult() {
		ArrayList<Object[]> mockResult = new ArrayList<>();

		when(labTestOrderDetailRepo.getLabTestOrderDetails(beneficiaryRegID, visitCode)).thenReturn(mockResult);

		String expectedJson = new Gson().toJson(LabTestOrderDetail.getLabTestOrderDetails(mockResult));
		String actualJson = commonNurseServiceImpl.getLabTestOrders(beneficiaryRegID, visitCode);

		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void testGetLabTestOrders_Exception() {
		when(labTestOrderDetailRepo.getLabTestOrderDetails(beneficiaryRegID, visitCode))
				.thenThrow(new RuntimeException("DB Error"));

		try {
			commonNurseServiceImpl.getLabTestOrders(beneficiaryRegID, visitCode);
		} catch (Exception e) {
			assertEquals("DB Error", e.getMessage());
		}
	}

	@Test
	public void testGetDevelopmentHistory_Success() throws IEMRException {
		when(benChildDevelopmentHistoryRepo.getBenDevelopmentHistory(beneficiaryRegID, visitCode))
				.thenReturn(benChildDevelopmentHistory);

		BenChildDevelopmentHistory result = commonNurseService.getDevelopmentHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertEquals(Arrays.asList("milestone1", "milestone2"), result.getGrossMotorMilestones());
		assertEquals(Arrays.asList("milestone3", "milestone4"), result.getFineMotorMilestones());
		assertEquals(Arrays.asList("milestone5", "milestone6"), result.getSocialMilestones());
		assertEquals(Arrays.asList("milestone7", "milestone8"), result.getLanguageMilestones());
		assertEquals(Arrays.asList("problem1", "problem2"), result.getDevelopmentProblems());
	}

	@Test
	public void testGetDevelopmentHistory_NoData() throws IEMRException {
		when(benChildDevelopmentHistoryRepo.getBenDevelopmentHistory(beneficiaryRegID, visitCode)).thenReturn(null);

		BenChildDevelopmentHistory result = commonNurseService.getDevelopmentHistory(beneficiaryRegID, visitCode);

		assertNull(result);
	}

	@Test
	public void testGetDevelopmentHistory_EmptyMilestones() throws IEMRException {
		benChildDevelopmentHistory.setGrossMotorMilestone(null);
		benChildDevelopmentHistory.setFineMotorMilestone(null);
		benChildDevelopmentHistory.setSocialMilestone(null);
		benChildDevelopmentHistory.setLanguageMilestone(null);
		benChildDevelopmentHistory.setDevelopmentProblem(null);

		when(benChildDevelopmentHistoryRepo.getBenDevelopmentHistory(beneficiaryRegID, visitCode))
				.thenReturn(benChildDevelopmentHistory);

		BenChildDevelopmentHistory result = commonNurseService.getDevelopmentHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertNull(result.getGrossMotorMilestones());
		assertNull(result.getFineMotorMilestones());
		assertNull(result.getSocialMilestones());
		assertNull(result.getLanguageMilestones());
		assertNull(result.getDevelopmentProblems());
	}

	@Test
	public void testGetPerinatalHistory_Success() throws IEMRException {
		when(perinatalHistoryRepo.getBenPerinatalHistory(beneficiaryRegID, visitCode)).thenReturn(perinatalHistory);

		PerinatalHistory result = commonNurseServiceImpl.getPerinatalHistory(beneficiaryRegID, visitCode);

		assertNotNull(result);
		assertEquals(perinatalHistory.getID(), result.getID());
		assertEquals(perinatalHistory.getBeneficiaryRegID(), result.getBeneficiaryRegID());
		assertEquals(perinatalHistory.getVisitCode(), result.getVisitCode());
		verify(perinatalHistoryRepo, times(1)).getBenPerinatalHistory(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetPerinatalHistory_NotFound() throws IEMRException {
		when(perinatalHistoryRepo.getBenPerinatalHistory(beneficiaryRegID, visitCode)).thenReturn(null);

		PerinatalHistory result = commonNurseServiceImpl.getPerinatalHistory(beneficiaryRegID, visitCode);

		assertNull(result);
		verify(perinatalHistoryRepo, times(1)).getBenPerinatalHistory(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetPerinatalHistory_Exception() throws IEMRException {
		when(perinatalHistoryRepo.getBenPerinatalHistory(beneficiaryRegID, visitCode))
				.thenThrow(new IEMRException("DB Error"));

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.getPerinatalHistory(beneficiaryRegID, visitCode);
		});

		assertEquals("DB Error", exception.getMessage());
		verify(perinatalHistoryRepo, times(1)).getBenPerinatalHistory(beneficiaryRegID, visitCode);
	}

	@Test
	public void testGetFeedingHistory() throws IEMRException {
		when(childFeedingDetailsRepo.getBenFeedingHistory(beneficiaryRegID, 1L)).thenReturn(childFeedingDetails);

		ChildFeedingDetails result = commonNurseServiceImpl.getFeedingHistory(beneficiaryRegID, 1L);

		assertNotNull(result);
		assertEquals(beneficiaryRegID, result.getBeneficiaryRegID());
		assertArrayEquals(new String[] { "Lactose", "Gluten" }, result.getTypeOfFoodIntolerances());
		verify(childFeedingDetailsRepo, times(1)).getBenFeedingHistory(beneficiaryRegID, 1L);
	}

	@Test
	public void testGetFeedingHistory_NoIntolerance() throws IEMRException {
		childFeedingDetails.setTypeofFoodIntolerance(null);
		when(childFeedingDetailsRepo.getBenFeedingHistory(beneficiaryRegID, 1L)).thenReturn(childFeedingDetails);

		ChildFeedingDetails result = commonNurseServiceImpl.getFeedingHistory(beneficiaryRegID, 1L);

		assertNotNull(result);
		assertEquals(beneficiaryRegID, result.getBeneficiaryRegID());
		assertNull(result.getTypeOfFoodIntolerances());
		verify(childFeedingDetailsRepo, times(1)).getBenFeedingHistory(beneficiaryRegID, 1L);
	}

	@Test
	public void testGetFeedingHistory_Exception() {
		when(childFeedingDetailsRepo.getBenFeedingHistory(beneficiaryRegID, 1L)).thenThrow(new IEMRException("Error"));

		assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.getFeedingHistory(beneficiaryRegID, 1L);
		});

		verify(childFeedingDetailsRepo, times(1)).getBenFeedingHistory(beneficiaryRegID, 1L);
	}

	@Test
	public void testFetchBenPerinatalHistory_Success() {
		when(perinatalHistoryRepo.getBenPerinatalDetail(beneficiaryRegID)).thenReturn(perinatalHistoryDetail);

		String response = commonNurseServiceImpl.fetchBenPerinatalHistory(beneficiaryRegID);

		Map<String, Object> expectedResponse = new HashMap<>();
		List<Map<String, Object>> columns = new ArrayList<>();
		Map<String, Object> column = new HashMap<>();

		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Place of Delivery");
		column.put("keyName", "placeOfDelivery");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Other Place of Delivery");
		column.put("keyName", "otherPlaceOfDelivery");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Type of Delivery");
		column.put("keyName", "typeOfDelivery");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Complication at Birth");
		column.put("keyName", "complicationAtBirth");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Other Complication at Birth");
		column.put("keyName", "otherComplicationAtBirth");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Gestation");
		column.put("keyName", "gestation");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Birth Weight(g)");
		column.put("keyName", "birthWeight_gram");
		columns.add(column);

		ArrayList<PerinatalHistory> perinatalHistoryDetails = new ArrayList<>();
		perinatalHistoryDetails.add(
				new PerinatalHistory((Date) perinatalHistoryDetail.get(0)[0], (String) perinatalHistoryDetail.get(0)[1],
						(String) perinatalHistoryDetail.get(0)[2], (String) perinatalHistoryDetail.get(0)[3],
						(String) perinatalHistoryDetail.get(0)[4], (String) perinatalHistoryDetail.get(0)[5],
						(String) perinatalHistoryDetail.get(0)[6], (Double) perinatalHistoryDetail.get(0)[7]));

		expectedResponse.put("columns", columns);
		expectedResponse.put("data", perinatalHistoryDetails);

		assertEquals(new Gson().toJson(expectedResponse), response);
		verify(perinatalHistoryRepo, times(1)).getBenPerinatalDetail(beneficiaryRegID);
	}

	@Test
	public void testFetchBenPerinatalHistory_EmptyResult() {
		when(perinatalHistoryRepo.getBenPerinatalDetail(beneficiaryRegID)).thenReturn(new ArrayList<>());

		String response = commonNurseServiceImpl.fetchBenPerinatalHistory(beneficiaryRegID);

		Map<String, Object> expectedResponse = new HashMap<>();
		List<Map<String, Object>> columns = new ArrayList<>();
		Map<String, Object> column = new HashMap<>();

		column.put("columnName", "Date of Capture");
		column.put("keyName", "captureDate");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Place of Delivery");
		column.put("keyName", "placeOfDelivery");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Other Place of Delivery");
		column.put("keyName", "otherPlaceOfDelivery");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Type of Delivery");
		column.put("keyName", "typeOfDelivery");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Complication at Birth");
		column.put("keyName", "complicationAtBirth");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Other Complication at Birth");
		column.put("keyName", "otherComplicationAtBirth");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Gestation");
		column.put("keyName", "gestation");
		columns.add(column);

		column = new HashMap<>();
		column.put("columnName", "Birth Weight(g)");
		column.put("keyName", "birthWeight_gram");
		columns.add(column);

		expectedResponse.put("columns", columns);
		expectedResponse.put("data", new ArrayList<>());

		assertEquals(new Gson().toJson(expectedResponse), response);
		verify(perinatalHistoryRepo, times(1)).getBenPerinatalDetail(beneficiaryRegID);
	}

	@Test
	public void testFetchBenFeedingHistory() {
		when(childFeedingDetailsRepo.getBenFeedingHistoryDetail(beneficiaryRegID)).thenReturn(mockFeedingHistoryDetail);

		String response = commonNurseServiceImpl.fetchBenFeedingHistory(beneficiaryRegID);

		Map<String, Object> responseMap = new Gson().fromJson(response, Map.class);
		assertNotNull(responseMap);
		assertTrue(responseMap.containsKey("columns"));
		assertTrue(responseMap.containsKey("data"));

		List<Map<String, Object>> columns = (List<Map<String, Object>>) responseMap.get("columns");
		assertEquals(9, columns.size());

		List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");
		assertEquals(1, data.size());

		Map<String, Object> firstRecord = data.get(0);
		assertEquals("Breastfeeding", firstRecord.get("typeOfFeed"));
		assertEquals("6 months", firstRecord.get("compFeedStartAge"));
		assertEquals("5 times", firstRecord.get("noOfCompFeedPerDay"));
		assertEquals("Yes", firstRecord.get("foodIntoleranceStatus"));
		assertEquals("Milk", firstRecord.get("typeofFoodIntolerance"));
		assertEquals("None", firstRecord.get("otherFoodIntolerance"));
	}

	@Test
	public void testFetchBenDevelopmentHistory() throws Exception {
		Long beneficiaryRegID = 1L;

		when(benChildDevelopmentHistoryRepo.getBenDevelopmentHistoryDetail(beneficiaryRegID))
				.thenReturn(mockDevelopmentHistoryDetail);

		String response = commonNurseServiceImpl.fetchBenDevelopmentHistory(beneficiaryRegID);

		assertTrue(response.contains("Gross Motor"));
		assertTrue(response.contains("Fine Motor"));
		assertTrue(response.contains("Social"));
		assertTrue(response.contains("Language"));
		assertTrue(response.contains("Problem"));
	}

	@Test
	public void testUpdateChildFeedingHistory_UpdateExistingRecord() throws IEMRException {
		when(childFeedingDetailsRepo.getBenChildFeedingDetailStatus(anyLong(), anyLong())).thenReturn("Y");
		when(childFeedingDetailsRepo.updateFeedingDetails(anyLong(), anyLong(), anyString(), anyInt(), anyInt(),
				anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString())).thenReturn(1);

		int response = commonNurseService.updateChildFeedingHistory(childFeedingDetails);

		assertEquals(1, response);
		verify(childFeedingDetailsRepo, times(1)).updateFeedingDetails(anyLong(), anyLong(), anyString(), anyInt(),
				anyInt(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString());
	}

	@Test
	public void testUpdateChildFeedingHistory_SaveNewRecord() throws IEMRException {
		when(childFeedingDetailsRepo.getBenChildFeedingDetailStatus(anyLong(), anyLong())).thenReturn(null);
		when(childFeedingDetailsRepo.save(any(ChildFeedingDetails.class))).thenReturn(childFeedingDetails);

		int response = commonNurseService.updateChildFeedingHistory(childFeedingDetails);

		assertEquals(1, response);
		verify(childFeedingDetailsRepo, times(1)).save(any(ChildFeedingDetails.class));
	}

	@Test
	public void testUpdateChildFeedingHistory_NullInput() throws IEMRException {
		int response = commonNurseService.updateChildFeedingHistory(null);

		assertEquals(0, response);
		verify(childFeedingDetailsRepo, times(0)).getBenChildFeedingDetailStatus(anyLong(), anyLong());
		verify(childFeedingDetailsRepo, times(0)).updateFeedingDetails(anyLong(), anyLong(), anyString(), anyInt(),
				anyInt(), anyString(), anyString(), anyString(), anyString(), anyLong(), anyLong(), anyString());
		verify(childFeedingDetailsRepo, times(0)).save(any(ChildFeedingDetails.class));
	}

	@Test
	public void testUpdatePerinatalHistory_UpdateExistingRecord() throws IEMRException {
		when(perinatalHistoryRepo.getPerinatalHistoryStatus(anyLong(), anyLong())).thenReturn("Y");
		when(perinatalHistoryRepo.updatePerinatalDetails(anyLong(), anyString(), anyString(), anyLong(), anyString(),
				anyLong(), anyString(), anyString(), anyString(), anyDouble(), anyString(), anyString(), anyLong(),
				anyLong())).thenReturn(1);

		int response = commonNurseServiceImpl.updatePerinatalHistory(perinatalHistory);

		assertEquals(1, response);
		verify(perinatalHistoryRepo, times(1)).updatePerinatalDetails(anyLong(), anyString(), anyString(), anyLong(),
				anyString(), anyLong(), anyString(), anyString(), anyString(), anyDouble(), anyString(), anyString(),
				anyLong(), anyLong());
	}

	@Test
	public void testUpdatePerinatalHistory_SaveNewRecord() throws IEMRException {
		when(perinatalHistoryRepo.getPerinatalHistoryStatus(anyLong(), anyLong())).thenReturn(null);
		when(perinatalHistoryRepo.save(any(PerinatalHistory.class))).thenReturn(perinatalHistory);

		int response = commonNurseServiceImpl.updatePerinatalHistory(perinatalHistory);

		assertEquals(1, response);
		verify(perinatalHistoryRepo, times(1)).save(any(PerinatalHistory.class));
	}

	@Test
	public void testUpdatePerinatalHistory_NullInput() throws IEMRException {
		int response = commonNurseServiceImpl.updatePerinatalHistory(null);

		assertEquals(0, response);
		verify(perinatalHistoryRepo, times(0)).getPerinatalHistoryStatus(anyLong(), anyLong());
		verify(perinatalHistoryRepo, times(0)).updatePerinatalDetails(anyLong(), anyString(), anyString(), anyLong(),
				anyString(), anyLong(), anyString(), anyString(), anyString(), anyDouble(), anyString(), anyString(),
				anyLong(), anyLong());
		verify(perinatalHistoryRepo, times(0)).save(any(PerinatalHistory.class));
	}

	@Test
	public void testUpdateChildDevelopmentHistory_UpdateExistingRecord() throws IEMRException {
		when(benChildDevelopmentHistoryRepo.getDevelopmentHistoryStatus(anyLong(), anyLong())).thenReturn("Y");
		when(benChildDevelopmentHistoryRepo.updateBenChildDevelopmentHistory(anyString(), anyBoolean(), anyString(),
				anyBoolean(), anyString(), anyBoolean(), anyString(), anyBoolean(), anyString(), anyString(),
				anyString(), anyLong(), anyLong())).thenReturn(1);

		int response = commonNurseServiceImpl.updateChildDevelopmentHistory(childDevelopmentDetails);

		assertEquals(1, response);
		verify(benChildDevelopmentHistoryRepo, times(1)).updateBenChildDevelopmentHistory(anyString(), anyBoolean(),
				anyString(), anyBoolean(), anyString(), anyBoolean(), anyString(), anyBoolean(), anyString(),
				anyString(), anyString(), anyLong(), anyLong());
	}

	@Test
	public void testUpdateChildDevelopmentHistory_SaveNewRecord() throws IEMRException {
		when(benChildDevelopmentHistoryRepo.getDevelopmentHistoryStatus(anyLong(), anyLong())).thenReturn(null);
		when(benChildDevelopmentHistoryRepo.save(any(BenChildDevelopmentHistory.class)))
				.thenReturn(childDevelopmentDetails);

		int response = commonNurseServiceImpl.updateChildDevelopmentHistory(childDevelopmentDetails);

		assertEquals(1, response);
		verify(benChildDevelopmentHistoryRepo, times(1)).save(any(BenChildDevelopmentHistory.class));
	}

	@Test
	public void testUpdateChildDevelopmentHistory_NullInput() throws IEMRException {
		int response = commonNurseServiceImpl.updateChildDevelopmentHistory(null);

		assertEquals(0, response);
		verify(benChildDevelopmentHistoryRepo, times(0)).getDevelopmentHistoryStatus(anyLong(), anyLong());
		verify(benChildDevelopmentHistoryRepo, times(0)).updateBenChildDevelopmentHistory(anyString(), anyBoolean(),
				anyString(), anyBoolean(), anyString(), anyBoolean(), anyString(), anyBoolean(), anyString(),
				anyString(), anyString(), anyLong(), anyLong());
		verify(benChildDevelopmentHistoryRepo, times(0)).save(any(BenChildDevelopmentHistory.class));
	}

	@Test
	public void testUpdateSysGastrointestinalExamination_Success() throws IEMRException {
		when(sysGastrointestinalExaminationRepo.getBenGastrointestinalExaminationStatus(1L, 1L)).thenReturn("N");
		when(sysGastrointestinalExaminationRepo.updateSysGastrointestinalExamination(anyString(), anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
				anyString(), anyString(), anyLong(), anyLong())).thenReturn(1);

		int response = commonNurseServiceImpl.updateSysGastrointestinalExamination(examination);

		assertEquals(1, response);
		verify(sysGastrointestinalExaminationRepo, times(1)).getBenGastrointestinalExaminationStatus(1L, 1L);
		verify(sysGastrointestinalExaminationRepo, times(1)).updateSysGastrointestinalExamination(anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
				anyString(), anyString(), anyString(), anyLong(), anyLong());
	}

	@Test
	public void testUpdateSysGastrointestinalExamination_NullInput() throws IEMRException {
		int response = commonNurseServiceImpl.updateSysGastrointestinalExamination(null);

		assertEquals(0, response);
		verify(sysGastrointestinalExaminationRepo, times(0)).getBenGastrointestinalExaminationStatus(anyLong(),
				anyLong());
		verify(sysGastrointestinalExaminationRepo, times(0)).updateSysGastrointestinalExamination(anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
				anyString(), anyString(), anyString(), anyLong(), anyLong());
	}

	@Test
	public void testUpdateSysGastrointestinalExamination_Exception() throws IEMRException {
		when(sysGastrointestinalExaminationRepo.getBenGastrointestinalExaminationStatus(1L, 1L)).thenReturn("N");
		when(sysGastrointestinalExaminationRepo.updateSysGastrointestinalExamination(anyString(), anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
				anyString(), anyString(), anyLong(), anyLong())).thenThrow(new IEMRException("Database error"));

		assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.updateSysGastrointestinalExamination(examination);
		});

		verify(sysGastrointestinalExaminationRepo, times(1)).getBenGastrointestinalExaminationStatus(1L, 1L);
		verify(sysGastrointestinalExaminationRepo, times(1)).updateSysGastrointestinalExamination(anyString(),
				anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
				anyString(), anyString(), anyString(), anyLong(), anyLong());
	}

	@Test
	public void testGetGraphicalTrendData() {
		when(benVisitDetailRepo.getLastSixVisitDetailsForBeneficiary(benRegID, visitCategory))
				.thenReturn(benLastSixVisitDetails);
		when(benAnthropometryRepo.getBenAnthropometryDetailForGraphtrends(anyList())).thenReturn(benAnthro);
		when(benPhysicalVitalRepo.getBenPhysicalVitalDetailForGraphTrends(anyList())).thenReturn(benVital);
		when(benCancerVitalDetailRepo.getBenCancerVitalDetailForGraph(anyList())).thenReturn(benCancerVital);

		Map<String, Object> result = commonNurseServiceImpl.getGraphicalTrendData(benRegID, visitCategory);

		assertNotNull(result);
		assertTrue(result.containsKey("weightList"));
		assertTrue(result.containsKey("bpList"));
		assertTrue(result.containsKey("bgList"));

		ArrayList<Map<String, Object>> weightList = (ArrayList<Map<String, Object>>) result.get("weightList");
		ArrayList<Map<String, Object>> bpList = (ArrayList<Map<String, Object>>) result.get("bpList");
		ArrayList<Map<String, Object>> bgList = (ArrayList<Map<String, Object>>) result.get("bgList");

		assertEquals(2, weightList.size());
		assertEquals(2, bpList.size());
		assertEquals(2, bgList.size());

		Map<String, Object> weightEntry = weightList.get(0);
		assertEquals(70.0, weightEntry.get("weight"));
		assertEquals(Date.valueOf("2023-01-01"), weightEntry.get("date"));

		Map<String, Object> bpEntry = bpList.get(0);
		assertEquals(120, bpEntry.get("avgSysBP"));
		assertEquals(80, bpEntry.get("avgDysBP"));
		assertEquals(Date.valueOf("2023-01-01"), bpEntry.get("date"));

		Map<String, Object> bgEntry = bgList.get(0);
		assertEquals(90, bgEntry.get("bg_fasting"));
		assertEquals(100, bgEntry.get("bg_random"));
		assertEquals(110, bgEntry.get("bg_2hr_pp"));
		assertEquals(Date.valueOf("2023-01-01"), bgEntry.get("date"));
	}

	@Test
	public void testUpdateBenFamilyHistoryNCDScreening_EmptyList() {
		benFamilyHistory.setBenFamilyHist(new ArrayList<>());

		int result = commonNurseServiceImpl.updateBenFamilyHistoryNCDScreening(benFamilyHistory);

		assertEquals(0, result);
	}

	@Test
	public void testUpdateBenFamilyHistoryNCDScreening_SuccessfulSave() {
		List<BenFamilyHistory> familyHistoryList = new ArrayList<>();
		familyHistoryList.add(new BenFamilyHistory());
		benFamilyHistory.setBenFamilyHist(familyHistoryList);

		when(benFamilyHistoryRepo.saveAll(familyHistoryList)).thenReturn(familyHistoryList);

		int result = commonNurseServiceImpl.updateBenFamilyHistoryNCDScreening(benFamilyHistory);

		assertEquals(1, result);
		verify(benFamilyHistoryRepo, times(1)).saveAll(familyHistoryList);
	}

	@Test
	public void testUpdateBenFamilyHistoryNCDScreening_UnsuccessfulSave() {
		List<BenFamilyHistory> familyHistoryList = new ArrayList<>();
		familyHistoryList.add(new BenFamilyHistory());
		benFamilyHistory.setBenFamilyHist(familyHistoryList);

		when(benFamilyHistoryRepo.saveAll(familyHistoryList)).thenReturn(new ArrayList<>());

		int result = commonNurseServiceImpl.updateBenFamilyHistoryNCDScreening(benFamilyHistory);

		assertEquals(0, result);
		verify(benFamilyHistoryRepo, times(1)).saveAll(familyHistoryList);
	}

	@Test
	public void testUpdateBenPhysicalActivityHistoryNCDScreening_WithID() throws IEMRException {
		physicalActivityType.setID(1L);
		when(physicalActivityTypeRepo.save(any(PhysicalActivityType.class))).thenReturn(physicalActivityType);

		int result = commonNurseServiceImpl.updateBenPhysicalActivityHistoryNCDScreening(physicalActivityType);

		assertEquals(1, result);
		assertEquals("U", physicalActivityType.getProcessed());
		verify(physicalActivityTypeRepo, times(1)).save(physicalActivityType);
	}

	@Test
	public void testUpdateBenPhysicalActivityHistoryNCDScreening_WithoutID() throws IEMRException {
		physicalActivityType.setID(null);
		when(physicalActivityTypeRepo.save(any(PhysicalActivityType.class))).thenReturn(physicalActivityType);

		int result = commonNurseServiceImpl.updateBenPhysicalActivityHistoryNCDScreening(physicalActivityType);

		assertEquals(1, result);
		assertEquals("N", physicalActivityType.getProcessed());
		verify(physicalActivityTypeRepo, times(1)).save(physicalActivityType);
	}

	@Test
	public void testUpdateBenPhysicalActivityHistoryNCDScreening_SaveReturnsNull() throws IEMRException {
		physicalActivityType.setID(null);
		when(physicalActivityTypeRepo.save(any(PhysicalActivityType.class))).thenReturn(null);

		int result = commonNurseServiceImpl.updateBenPhysicalActivityHistoryNCDScreening(physicalActivityType);

		assertEquals(0, result);
		verify(physicalActivityTypeRepo, times(1)).save(physicalActivityType);
	}

	@Test
	public void testGetBenSymptomaticData_ValidData() throws Exception {
		when(iDRSDataRepo.getBenIdrsDetailsLast_3_Month(benRegID, timestamp))
				.thenReturn((ArrayList<IDRSData>) idrsDataList);
		when(iDRSDataRepo.isDiabeticCheck(benRegID)).thenReturn(1);
		when(iDRSDataRepo.isEpilepsyCheck(benRegID)).thenReturn(1);
		when(iDRSDataRepo.isDefectiveVisionCheck(benRegID)).thenReturn(1);
		when(iDRSDataRepo.isHypertensionCheck(benRegID)).thenReturn(1);

		String response = commonNurseServiceImpl.getBenSymptomaticData(benRegID);

		assertNotNull(response);
		assertTrue(response.contains("Diabetes"));
		assertTrue(response.contains("Hypertension"));
		assertTrue(response.contains("isDiabetic"));
		assertTrue(response.contains("isEpilepsy"));
		assertTrue(response.contains("isDefectiveVision"));
		assertTrue(response.contains("isHypertension"));

		verify(iDRSDataRepo, times(1)).getBenIdrsDetailsLast_3_Month(benRegID, timestamp);
		verify(iDRSDataRepo, times(1)).isDiabeticCheck(benRegID);
		verify(iDRSDataRepo, times(1)).isEpilepsyCheck(benRegID);
		verify(iDRSDataRepo, times(1)).isDefectiveVisionCheck(benRegID);
		verify(iDRSDataRepo, times(1)).isHypertensionCheck(benRegID);
	}

	@Test
	public void testGetBenSymptomaticData_NoData() throws Exception {
		when(iDRSDataRepo.getBenIdrsDetailsLast_3_Month(benRegID, timestamp)).thenReturn(new ArrayList<>());
		when(iDRSDataRepo.isDiabeticCheck(benRegID)).thenReturn(0);
		when(iDRSDataRepo.isEpilepsyCheck(benRegID)).thenReturn(0);
		when(iDRSDataRepo.isDefectiveVisionCheck(benRegID)).thenReturn(0);
		when(iDRSDataRepo.isHypertensionCheck(benRegID)).thenReturn(0);

		String response = commonNurseServiceImpl.getBenSymptomaticData(benRegID);

		assertNotNull(response);
		assertFalse(response.contains("isDiabetic"));
		assertFalse(response.contains("isEpilepsy"));
		assertFalse(response.contains("isDefectiveVision"));
		assertFalse(response.contains("isHypertension"));

		verify(iDRSDataRepo, times(1)).getBenIdrsDetailsLast_3_Month(benRegID, timestamp);
		verify(iDRSDataRepo, times(1)).isDiabeticCheck(benRegID);
		verify(iDRSDataRepo, times(1)).isEpilepsyCheck(benRegID);
		verify(iDRSDataRepo, times(1)).isDefectiveVisionCheck(benRegID);
		verify(iDRSDataRepo, times(1)).isHypertensionCheck(benRegID);
	}

	@Test
	public void testGetBenSymptomaticData_Exception() throws Exception {
		when(iDRSDataRepo.getBenIdrsDetailsLast_3_Month(benRegID, timestamp))
				.thenThrow(new IEMRException("Database error"));

		Exception exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.getBenSymptomaticData(benRegID);
		});

		assertEquals("Database error", exception.getMessage());
		verify(iDRSDataRepo, times(1)).getBenIdrsDetailsLast_3_Month(benRegID, timestamp);
	}

	@Test
	public void testGetBenPreviousDiabetesData_Success() throws Exception {
		when(iDRSDataRepo.getBenPreviousDiabetesDetails(benRegID)).thenReturn(idrsDataList);

		String response = commonNurseService.getBenPreviousDiabetesData(benRegID);

		Map<String, Object> expectedResponse = new HashMap<>();
		ArrayList<Map<String, String>> columns = new ArrayList<>();
		Map<String, String> column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "createdDate");
		columns.add(column);
		column = new HashMap<>();
		column.put("columnName", "Visit Code");
		column.put("keyName", "visitCode");
		columns.add(column);
		column = new HashMap<>();
		column.put("columnName", "Question");
		column.put("keyName", "question");
		columns.add(column);
		column = new HashMap<>();
		column.put("columnName", "Answer");
		column.put("keyName", "answer");
		columns.add(column);
		expectedResponse.put("columns", columns);
		expectedResponse.put("data", idrsDataList);

		assertEquals(new Gson().toJson(expectedResponse), response);
		verify(iDRSDataRepo, times(1)).getBenPreviousDiabetesDetails(benRegID);
	}

	@Test
	public void testGetBenPreviousDiabetesData_Empty() throws Exception {
		when(iDRSDataRepo.getBenPreviousDiabetesDetails(benRegID)).thenReturn(new ArrayList<>());

		String response = commonNurseService.getBenPreviousDiabetesData(benRegID);

		Map<String, Object> expectedResponse = new HashMap<>();
		ArrayList<Map<String, String>> columns = new ArrayList<>();
		Map<String, String> column = new HashMap<>();
		column.put("columnName", "Date of Capture");
		column.put("keyName", "createdDate");
		columns.add(column);
		column = new HashMap<>();
		column.put("columnName", "Visit Code");
		column.put("keyName", "visitCode");
		columns.add(column);
		column = new HashMap<>();
		column.put("columnName", "Question");
		column.put("keyName", "question");
		columns.add(column);
		column = new HashMap<>();
		column.put("columnName", "Answer");
		column.put("keyName", "answer");
		columns.add(column);
		expectedResponse.put("columns", columns);
		expectedResponse.put("data", new ArrayList<>());

		assertEquals(new Gson().toJson(expectedResponse), response);
		verify(iDRSDataRepo, times(1)).getBenPreviousDiabetesDetails(benRegID);
	}

	@Test
	public void testGetBenPreviousDiabetesData_Exception() throws Exception {
		when(iDRSDataRepo.getBenPreviousDiabetesDetails(benRegID)).thenThrow(new IEMRException("Database error"));

		Exception exception = assertThrows(IEMRException.class, () -> {
			commonNurseService.getBenPreviousDiabetesData(benRegID);
		});

		assertEquals("Database error", exception.getMessage());
		verify(iDRSDataRepo, times(1)).getBenPreviousDiabetesDetails(benRegID);
	}

	@Test
	public void testGetMmuNurseWorkListNew() {
		when(beneficiaryFlowStatusRepo.getMmuNurseWorklistNew(providerServiceMapId, vanID, new Timestamp(startTime)))
				.thenReturn((ArrayList<BeneficiaryFlowStatus>) beneficiaryFlowStatusList);

		String expectedJson = new Gson().toJson(beneficiaryFlowStatusList);
		String actualJson = commonNurseServiceImpl.getMmuNurseWorkListNew(providerServiceMapId, vanID);

		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void testGetBenPreviousReferralData() throws Exception {
		when(iDRSDataRepo.getBenPreviousReferredDetails(benRegID)).thenReturn(resultSet);

		String response = commonNurseService.getBenPreviousReferralData(benRegID);

		assertNotNull(response);
		assertTrue(response.contains("Date of Referral"));
		assertTrue(response.contains("Visit Code"));
		assertTrue(response.contains("Suspected Diseases"));

		verify(iDRSDataRepo, times(1)).getBenPreviousReferredDetails(benRegID);
	}

	@Test
	public void testGetBenPreviousReferralData_EmptyResultSet() throws Exception {
		when(iDRSDataRepo.getBenPreviousReferredDetails(benRegID)).thenReturn(new ArrayList<>());

		String response = commonNurseService.getBenPreviousReferralData(benRegID);

		assertNotNull(response);
		assertTrue(response.contains("Date of Referral"));
		assertTrue(response.contains("Visit Code"));
		assertTrue(response.contains("Suspected Diseases"));

		verify(iDRSDataRepo, times(1)).getBenPreviousReferredDetails(benRegID);
	}

	@Test
	public void testGetBenPreviousReferralData_Exception() throws Exception {
		when(iDRSDataRepo.getBenPreviousReferredDetails(benRegID)).thenThrow(new IEMRException("Error"));

		Exception exception = assertThrows(IEMRException.class, () -> {
			commonNurseService.getBenPreviousReferralData(benRegID);
		});

		assertEquals("Error", exception.getMessage());
		verify(iDRSDataRepo, times(1)).getBenPreviousReferredDetails(benRegID);
	}

	@Test
	public void testFetchProviderSpecificdata_Prescription() throws IEMRException {
		ArrayList<Object[]> resList = new ArrayList<>();
		Object[] data = new Object[] { "Drug1", "500mg", "7 days", "days", "Tablet", "Twice a day", "14",
				"2023-01-01" };
		resList.add(data);

		when(prescribedDrugDetailRepo.getBenPrescribedDrugDetails(request.getBenRegID(), request.getVisitCode()))
				.thenReturn(resList);

		String response = commonNurseService.fetchProviderSpecificdata(new Gson().toJson(request));

		assertNotNull(response);
		assertTrue(response.contains("Drug Name"));
		assertTrue(response.contains("Strength"));
		assertTrue(response.contains("Duration"));
		assertTrue(response.contains("Unit of duration"));
		assertTrue(response.contains("Form"));
		assertTrue(response.contains("Frequency"));
		assertTrue(response.contains("Quantity Prescribed"));
		assertTrue(response.contains("Prescribed Date"));

		verify(prescribedDrugDetailRepo, times(1)).getBenPrescribedDrugDetails(request.getBenRegID(),
				request.getVisitCode());
	}

	@Test
	public void testFetchProviderSpecificdata_Investigation() throws IEMRException {
		request.setFetchMMUDataFor("investigation");

		ArrayList<Object[]> labTestOrders = new ArrayList<>();
		Object[] data = new Object[] { "Test1" };
		labTestOrders.add(data);

		when(labTestOrderDetailRepo.getLabTestOrderDetails(request.getBenRegID(), request.getVisitCode()))
				.thenReturn(labTestOrders);

		String response = commonNurseService.fetchProviderSpecificdata(new Gson().toJson(request));

		assertNotNull(response);
		assertTrue(response.contains("Test Name"));

		verify(labTestOrderDetailRepo, times(1)).getLabTestOrderDetails(request.getBenRegID(), request.getVisitCode());
	}

	@Test
	public void testFetchProviderSpecificdata_Referral() throws IEMRException {
		request.setFetchMMUDataFor("referral");

		ArrayList<BenReferDetails> resList = new ArrayList<>();
		BenReferDetails referDetails = new BenReferDetails();
		referDetails.setReferredToInstituteName("Institute1");
		referDetails.setRefrredToAdditionalServiceList("Service1");
		referDetails.setReferralReason("Reason1");
		referDetails.setRevisitDate("2023-01-01");
		referDetails.setCreatedDate("2023-01-01");
		resList.add(referDetails);

		when(benReferDetailsRepo.findByBeneficiaryRegIDAndVisitCode(request.getBenRegID(), request.getVisitCode()))
				.thenReturn(resList);

		String response = commonNurseService.fetchProviderSpecificdata(new Gson().toJson(request));

		assertNotNull(response);
		assertTrue(response.contains("Higher Healthcare Centre"));
		assertTrue(response.contains("Additional Services"));
		assertTrue(response.contains("Referral Reason"));
		assertTrue(response.contains("Revisit Date"));
		assertTrue(response.contains("Date of Referral"));

		verify(benReferDetailsRepo, times(1)).findByBeneficiaryRegIDAndVisitCode(request.getBenRegID(),
				request.getVisitCode());
	}

	@Test
	public void testFetchProviderSpecificdata_InvalidMasterParam() throws IEMRException {
		request.setFetchMMUDataFor("invalid");

		String response = commonNurseService.fetchProviderSpecificdata(new Gson().toJson(request));

		assertEquals("Invalid master param to fetch data", response);
	}

	@Test
	public void testFetchProviderSpecificdata_Exception() {
		request.setFetchMMUDataFor("prescription");

		when(prescribedDrugDetailRepo.getBenPrescribedDrugDetails(request.getBenRegID(), request.getVisitCode()))
				.thenThrow(new RuntimeException("Database error"));

		Exception exception = assertThrows(IEMRException.class, () -> {
			commonNurseService.fetchProviderSpecificdata(new Gson().toJson(request));
		});

		assertEquals("Database error", exception.getMessage());
	}

	@Test
	public void testCalculateBMIStatus_Normal() throws IEMRException {
		bmiCalculation.setN1SD(18.5);
		bmiCalculation.setP1SD(24.9);
		when(bmiCalculationRepo.getBMIDetails(anyInt(), anyString())).thenReturn(bmiCalculation);

		String request = new Gson().toJson(new BmiCalculation("5 years 6 months", "male", 20.0));
		String response = commonNurseServiceImpl.calculateBMIStatus(request);

		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("bmiStatus", "Normal");
		assertEquals(new Gson().toJson(expectedResponse), response);
	}

	@Test
	public void testCalculateBMIStatus_MildMalnourished() throws IEMRException {
		bmiCalculation.setN2SD(16.0);
		bmiCalculation.setN1SD(18.5);
		when(bmiCalculationRepo.getBMIDetails(anyInt(), anyString())).thenReturn(bmiCalculation);

		String request = new Gson().toJson(new BmiCalculation("5 years 6 months", "male", 17.0));
		String response = commonNurseServiceImpl.calculateBMIStatus(request);

		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("bmiStatus", "Mild malnourished");
		assertEquals(new Gson().toJson(expectedResponse), response);
	}

	@Test
	public void testCalculateBMIStatus_ModeratelyMalnourished() throws IEMRException {
		bmiCalculation.setN3SD(14.0);
		bmiCalculation.setN2SD(16.0);
		when(bmiCalculationRepo.getBMIDetails(anyInt(), anyString())).thenReturn(bmiCalculation);

		String request = new Gson().toJson(new BmiCalculation("5 years 6 months", "male", 15.0));
		String response = commonNurseServiceImpl.calculateBMIStatus(request);

		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("bmiStatus", "Moderately Malnourished");
		assertEquals(new Gson().toJson(expectedResponse), response);
	}

	@Test
	public void testCalculateBMIStatus_SeverelyMalnourished() throws IEMRException {
		bmiCalculation.setN3SD(14.0);
		when(bmiCalculationRepo.getBMIDetails(anyInt(), anyString())).thenReturn(bmiCalculation);

		String request = new Gson().toJson(new BmiCalculation("5 years 6 months", "male", 13.0));
		String response = commonNurseServiceImpl.calculateBMIStatus(request);

		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("bmiStatus", "Severely Malnourished");
		assertEquals(new Gson().toJson(expectedResponse), response);
	}

	@Test
	public void testCalculateBMIStatus_Overweight() throws IEMRException {
		bmiCalculation.setP1SD(24.9);
		bmiCalculation.setP2SD(29.9);
		when(bmiCalculationRepo.getBMIDetails(anyInt(), anyString())).thenReturn(bmiCalculation);

		String request = new Gson().toJson(new BmiCalculation("5 years 6 months", "male", 25.0));
		String response = commonNurseServiceImpl.calculateBMIStatus(request);

		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("bmiStatus", "Overweight");
		assertEquals(new Gson().toJson(expectedResponse), response);
	}

	@Test
	public void testCalculateBMIStatus_Obese() throws IEMRException {
		bmiCalculation.setP2SD(29.9);
		bmiCalculation.setP3SD(34.9);
		when(bmiCalculationRepo.getBMIDetails(anyInt(), anyString())).thenReturn(bmiCalculation);

		String request = new Gson().toJson(new BmiCalculation("5 years 6 months", "male", 30.0));
		String response = commonNurseServiceImpl.calculateBMIStatus(request);

		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("bmiStatus", "Obese");
		assertEquals(new Gson().toJson(expectedResponse), response);
	}

	@Test
	public void testCalculateBMIStatus_SeverelyObese() throws IEMRException {
		bmiCalculation.setP3SD(34.9);
		when(bmiCalculationRepo.getBMIDetails(anyInt(), anyString())).thenReturn(bmiCalculation);

		String request = new Gson().toJson(new BmiCalculation("5 years 6 months", "male", 35.0));
		String response = commonNurseServiceImpl.calculateBMIStatus(request);

		Map<String, String> expectedResponse = new HashMap<>();
		expectedResponse.put("bmiStatus", "Severely Obese");
		assertEquals(new Gson().toJson(expectedResponse), response);
	}

	@Test
	public void testCalculateBMIStatus_InvalidInput() {
		String request = new Gson().toJson(new BmiCalculation(null, "male", 20.0));
		Exception exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.calculateBMIStatus(request);
		});

		String expectedMessage = "Error while calculating BMI status";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testSaveOralExamination_Success() throws IEMRException {
		when(oralExaminationRepo.save(any(OralExamination.class))).thenReturn(oralExamination);

		Long result = commonNurseServiceImpl.saveOralExamination(oralExamination);

		assertNotNull(result);
		assertEquals(oralExamination.getID(), result);
		verify(oralExaminationRepo, times(1)).save(any(OralExamination.class));
	}

	@Test
	public void testSaveOralExamination_NullOralDetails() {
		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.saveOralExamination(null);
		});

		assertEquals("OralExamination is NULL", exception.getMessage());
		verify(oralExaminationRepo, times(0)).save(any(OralExamination.class));
	}

	@Test
	public void testSaveOralExamination_SaveFailure() {
		when(oralExaminationRepo.save(any(OralExamination.class))).thenReturn(null);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.saveOralExamination(oralExamination);
		});

		assertEquals("error in saving OralExamination to DB", exception.getMessage());
		verify(oralExaminationRepo, times(1)).save(any(OralExamination.class));
	}

	@Test
	public void testGetOralExaminationDetails_NullOralExamination() {
		assertThrows(IEMRException.class, () -> {
			commonNurseServiceImpl.getOralExaminationDetails(null);
		});
	}

	@Test
	public void testGetOralExaminationDetails_WithPreMalignantLesionTypeList() throws IEMRException {
		String[] preMalignantLesionTypeList = { "Type1", "Type2" };
		oralExamination.setPreMalignantLesionTypeList(preMalignantLesionTypeList);

		OralExamination result = commonNurseServiceImpl.getOralExaminationDetails(oralExamination);

		assertNotNull(result);
		assertEquals("Type1||Type2", result.getPreMalignantLesionType());
	}

	@Test
	public void testGetOralExaminationDetails_EmptyPreMalignantLesionTypeList() throws IEMRException {
		String[] preMalignantLesionTypeList = {};
		oralExamination.setPreMalignantLesionTypeList(preMalignantLesionTypeList);

		OralExamination result = commonNurseServiceImpl.getOralExaminationDetails(oralExamination);

		assertNotNull(result);
		assertNull(result.getPreMalignantLesionType());
	}
}
