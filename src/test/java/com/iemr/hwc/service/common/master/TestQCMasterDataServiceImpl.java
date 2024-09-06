package com.iemr.hwc.service.common.master;



	import static org.mockito.Mockito.*;
	import static org.junit.jupiter.api.Assertions.*;

	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Map;

	import com.google.gson.Gson;
	import com.iemr.hwc.data.doctor.ChiefComplaintMaster;
	import com.iemr.hwc.data.doctor.DrugDoseMaster;
	import com.iemr.hwc.data.doctor.DrugDurationUnitMaster;
	import com.iemr.hwc.data.doctor.DrugFormMaster;
	import com.iemr.hwc.data.doctor.DrugFrequencyMaster;
	import com.iemr.hwc.data.doctor.LabTestMaster;
	import com.iemr.hwc.data.doctor.TempMasterDrug;
	import com.iemr.hwc.data.labModule.ProcedureData;
	import com.iemr.hwc.repo.doctor.ChiefComplaintMasterRepo;
	import com.iemr.hwc.repo.doctor.DrugDoseMasterRepo;
	import com.iemr.hwc.repo.doctor.DrugDurationUnitMasterRepo;
	import com.iemr.hwc.repo.doctor.DrugFormMasterRepo;
	import com.iemr.hwc.repo.doctor.DrugFrequencyMasterRepo;
	import com.iemr.hwc.repo.doctor.LabTestMasterRepo;
	import com.iemr.hwc.repo.doctor.TempMasterDrugRepo;
	import com.iemr.hwc.repo.labModule.ProcedureRepo;

	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.mockito.InjectMocks;
	import org.mockito.Mock;
	import org.mockito.MockitoAnnotations;

	public class TestQCMasterDataServiceImpl {
		

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
	    private TempMasterDrugRepo tempMasterDrugRepo;
	    @Mock
	    private ProcedureRepo procedureRepo;

	    @InjectMocks
	    private QCMasterDataServiceImpl qcMasterDataService;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testGetQuickConsultMasterData() {
	        Integer providerServiceMapID = 1;
	        String gender = "Male";

	        // Mocking repository methods
	        when(chiefComplaintMasterRepo.getChiefComplaintMaster()).thenReturn(new ArrayList<>());
	        when(drugDoseMasterRepo.getDrugDoseMaster()).thenReturn(new ArrayList<>());
	        when(drugDurationUnitMasterRepo.getDrugDurationUnitMaster()).thenReturn(new ArrayList<>());
	        when(drugFormMasterRepo.getDrugFormMaster()).thenReturn(new ArrayList<>());
	        when(drugFrequencyMasterRepo.getDrugFrequencyMaster()).thenReturn(new ArrayList<>());
	        when(labTestMasterRepo.getLabTestMaster()).thenReturn(new ArrayList<>());
	        when(procedureRepo.getProcedureMasterData(providerServiceMapID, gender)).thenReturn(new ArrayList<>());
	        when(tempMasterDrugRepo.findByDeletedFalseOrderByDrugDisplayNameAsc()).thenReturn(new ArrayList<>());

	        // Call the method under test
	        String result = qcMasterDataService.getQuickConsultMasterData(providerServiceMapID, gender);

	        // Verify interactions with mocks
	        verify(chiefComplaintMasterRepo, times(1)).getChiefComplaintMaster();
	        verify(drugDoseMasterRepo, times(1)).getDrugDoseMaster();
	        verify(drugDurationUnitMasterRepo, times(1)).getDrugDurationUnitMaster();
	        verify(drugFormMasterRepo, times(1)).getDrugFormMaster();
	        verify(drugFrequencyMasterRepo, times(1)).getDrugFrequencyMaster();
	        verify(labTestMasterRepo, times(1)).getLabTestMaster();
	        verify(procedureRepo, times(1)).getProcedureMasterData(providerServiceMapID, gender);
	        verify(tempMasterDrugRepo, times(1)).findByDeletedFalseOrderByDrugDisplayNameAsc();

	        // Assert the result
	        Map<String, Object> expectedMap = new HashMap<>();
	        expectedMap.put("chiefComplaintMaster", ChiefComplaintMaster.getChiefComplaintMasters(new ArrayList<>()));
	        expectedMap.put("drugDoseMaster", DrugDoseMaster.getDrugDoseMasters(new ArrayList<>()));
	        expectedMap.put("drugDurationUnitMaster", DrugDurationUnitMaster.getDrugDurationUnitMaster(new ArrayList<>()));
	        expectedMap.put("drugFormMaster", DrugFormMaster.getDrugFormMaster(new ArrayList<>()));
	        expectedMap.put("drugFrequencyMaster", DrugFrequencyMaster.getDrugFrequencyMaster(new ArrayList<>()));
	        expectedMap.put("labTestMaster", LabTestMaster.getLabTestMasters(new ArrayList<>()));
	        expectedMap.put("tempDrugMaster", TempMasterDrug.getTempDrugMasterList(new ArrayList<>()));
	        expectedMap.put("procedures", ProcedureData.getProcedures(new ArrayList<>()));

	        String expectedJson = new Gson().toJson(expectedMap);
	        assertEquals(expectedJson, result);
	    }
	}

