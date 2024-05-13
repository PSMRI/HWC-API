package com.iemr.hwc.service.labtechnician;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonObject;
import com.iemr.hwc.data.labModule.LabResultEntry;
import com.iemr.hwc.data.labModule.ProcedureData;
import com.iemr.hwc.data.labModule.TestComponentMaster;
import com.iemr.hwc.data.labModule.WrapperLabResultEntry;
import com.iemr.hwc.data.labtechnician.V_benLabTestOrderedDetails;
import com.iemr.hwc.repo.labModule.LabResultEntryRepo;
import com.iemr.hwc.repo.labtechnician.V_benLabTestOrderedDetailsRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;

@ExtendWith(MockitoExtension.class)
class LabTechnicianServiceImplTest {
    @Mock
    private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

    @Mock
    private LabResultEntryRepo labResultEntryRepo;

    @InjectMocks
    private LabTechnicianServiceImpl labTechnicianServiceImpl;

    @Mock
    private V_benLabTestOrderedDetailsRepo v_benLabTestOrderedDetailsRepo;

    @Test
    void testGetBenePrescribedProcedureDetails() {
        // Arrange
        when(labResultEntryRepo.findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(new ArrayList<>());
        when(v_benLabTestOrderedDetailsRepo
                .findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDNotInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
                        Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(), Mockito.<ArrayList<Integer>>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualBenePrescribedProcedureDetails = labTechnicianServiceImpl.getBenePrescribedProcedureDetails(1L, 1L);

        // Assert
        verify(labResultEntryRepo).findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(isA(Long.class),
                isA(Long.class));
        verify(v_benLabTestOrderedDetailsRepo, atLeast(1))
                .findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDNotInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
                        isA(Long.class), isA(Long.class), Mockito.<String>any(), isA(ArrayList.class));
        assertEquals("{\"radiologyList\":[],\"laboratoryList\":[],\"archive\":[]}", actualBenePrescribedProcedureDetails);
    }

    @Test
    void testGetBenePrescribedProcedureDetails2() {
        // Arrange
        when(labResultEntryRepo.findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(new ArrayList<>());

        V_benLabTestOrderedDetails v_benLabTestOrderedDetails = new V_benLabTestOrderedDetails();
        v_benLabTestOrderedDetails.setBenVisitID(1L);
        v_benLabTestOrderedDetails.setBeneficiaryRegID(1L);
        v_benLabTestOrderedDetails.setCalibrationEndAPI("Laboratory");
        v_benLabTestOrderedDetails.setCalibrationStartAPI("Laboratory");
        v_benLabTestOrderedDetails.setCalibrationStatusAPI("Laboratory");
        v_benLabTestOrderedDetails.setComponentCode("Laboratory");
        v_benLabTestOrderedDetails.setComponentUnit("Laboratory");
        v_benLabTestOrderedDetails.setDiscoveryCode("Laboratory");
        v_benLabTestOrderedDetails.setIOTComponentName("Laboratory");
        v_benLabTestOrderedDetails.setIOTProcedureID("Laboratory");
        v_benLabTestOrderedDetails.setId("42");
        v_benLabTestOrderedDetails.setInputType("Laboratory");
        v_benLabTestOrderedDetails.setIotProcedureName("Laboratory");
        v_benLabTestOrderedDetails.setIsDecimal(true);
        v_benLabTestOrderedDetails.setIsLabProcedure(true);
        v_benLabTestOrderedDetails.setIsMandatory(true);
        v_benLabTestOrderedDetails.setMeasurementUnit("Laboratory");
        v_benLabTestOrderedDetails.setPrescriptionID(1L);
        v_benLabTestOrderedDetails.setProcedureCode("Laboratory");
        v_benLabTestOrderedDetails.setProcedureDesc("Laboratory");
        v_benLabTestOrderedDetails.setProcedureEndAPI("Laboratory");
        v_benLabTestOrderedDetails.setProcedureID(1);
        v_benLabTestOrderedDetails.setProcedureName("Laboratory");
        v_benLabTestOrderedDetails.setProcedureStartAPI("Laboratory");
        v_benLabTestOrderedDetails.setProcedureStatusAPI("Laboratory");
        v_benLabTestOrderedDetails.setProcedureType("Laboratory");
        v_benLabTestOrderedDetails.setRange_max(3);
        v_benLabTestOrderedDetails.setRange_min(1);
        v_benLabTestOrderedDetails.setRange_normal_max(3);
        v_benLabTestOrderedDetails.setRange_normal_min(1);
        v_benLabTestOrderedDetails.setResultValue("42");
        v_benLabTestOrderedDetails.setTestComponentDesc("Laboratory");
        v_benLabTestOrderedDetails.setTestComponentID(1);
        v_benLabTestOrderedDetails.setTestComponentName("Laboratory");
        v_benLabTestOrderedDetails.setVisitCode(1L);

        ArrayList<V_benLabTestOrderedDetails> v_benLabTestOrderedDetailsList = new ArrayList<>();
        v_benLabTestOrderedDetailsList.add(v_benLabTestOrderedDetails);
        when(v_benLabTestOrderedDetailsRepo
                .findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDNotInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
                        Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(), Mockito.<ArrayList<Integer>>any()))
                .thenReturn(v_benLabTestOrderedDetailsList);

        // Act
        String actualBenePrescribedProcedureDetails = labTechnicianServiceImpl.getBenePrescribedProcedureDetails(1L, 1L);

        // Assert
        verify(labResultEntryRepo).findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(isA(Long.class),
                isA(Long.class));
        verify(v_benLabTestOrderedDetailsRepo, atLeast(1))
                .findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDNotInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
                        isA(Long.class), isA(Long.class), Mockito.<String>any(), isA(ArrayList.class));
        assertEquals(
                "{\"radiologyList\":[{\"procedureDesc\":\"Laboratory\",\"procedureType\":\"Radiology\",\"prescriptionID\":1,"
                        + "\"compDetails\":{\"testComponentName\":\"Laboratory\",\"inputType\":\"File\",\"testComponentID\":1,\"testComponentDesc"
                        + "\":\"Laboratory\"},\"procedureName\":\"Laboratory\",\"procedureID\":1}],\"laboratoryList\":[{\"procedureDesc\":"
                        + "\"Laboratory\",\"procedureType\":\"Laboratory\",\"prescriptionID\":1,\"calibrationEndAPI\":\"Laboratory\","
                        + "\"procedureCode\":\"Laboratory\",\"calibrationStatusAPI\":\"Laboratory\",\"procedureID\":1,\"iotProcedureName\":"
                        + "\"Laboratory\",\"isLabProcedure\":true,\"calibrationStartAPI\":\"Laboratory\",\"discoveryCode\":\"Laboratory\","
                        + "\"procedureName\":\"Laboratory\",\"compListDetails\":[{\"range_normal_min\":1,\"compOpt\":[{\"name\":\"42\"}],"
                        + "\"componentCode\":\"Laboratory\",\"range_min\":1,\"testComponentID\":1,\"measurementUnit\":\"Laboratory\","
                        + "\"testComponentName\":\"Laboratory\",\"iotComponentName\":\"Laboratory\",\"range_normal_max\":3,\"iotProcedureID"
                        + "\":\"Laboratory\",\"componentUnit\":\"Laboratory\",\"isDecimal\":true,\"inputType\":\"Laboratory\",\"range_max\":3,"
                        + "\"testComponentDesc\":\"Laboratory\"}],\"procedureStatusAPI\":\"Laboratory\",\"isMandatory\":true,\"procedureEndAPI"
                        + "\":\"Laboratory\",\"procedureStartAPI\":\"Laboratory\"}],\"archive\":[]}",
                actualBenePrescribedProcedureDetails);
    }

    @Test
    void testGetBenePrescribedProcedureDetails3() {
        // Arrange
        when(labResultEntryRepo.findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(new ArrayList<>());

        V_benLabTestOrderedDetails v_benLabTestOrderedDetails = new V_benLabTestOrderedDetails();
        v_benLabTestOrderedDetails.setBenVisitID(1L);
        v_benLabTestOrderedDetails.setBeneficiaryRegID(1L);
        v_benLabTestOrderedDetails.setCalibrationEndAPI("Laboratory");
        v_benLabTestOrderedDetails.setCalibrationStartAPI("Laboratory");
        v_benLabTestOrderedDetails.setCalibrationStatusAPI("Laboratory");
        v_benLabTestOrderedDetails.setComponentCode("Laboratory");
        v_benLabTestOrderedDetails.setComponentUnit("Laboratory");
        v_benLabTestOrderedDetails.setDiscoveryCode("Laboratory");
        v_benLabTestOrderedDetails.setIOTComponentName("Laboratory");
        v_benLabTestOrderedDetails.setIOTProcedureID("Laboratory");
        v_benLabTestOrderedDetails.setId("42");
        v_benLabTestOrderedDetails.setInputType("Laboratory");
        v_benLabTestOrderedDetails.setIotProcedureName("Laboratory");
        v_benLabTestOrderedDetails.setIsDecimal(true);
        v_benLabTestOrderedDetails.setIsLabProcedure(true);
        v_benLabTestOrderedDetails.setIsMandatory(true);
        v_benLabTestOrderedDetails.setMeasurementUnit("Laboratory");
        v_benLabTestOrderedDetails.setPrescriptionID(1L);
        v_benLabTestOrderedDetails.setProcedureCode("Laboratory");
        v_benLabTestOrderedDetails.setProcedureDesc("Laboratory");
        v_benLabTestOrderedDetails.setProcedureEndAPI("Laboratory");
        v_benLabTestOrderedDetails.setProcedureID(1);
        v_benLabTestOrderedDetails.setProcedureName("Laboratory");
        v_benLabTestOrderedDetails.setProcedureStartAPI("Laboratory");
        v_benLabTestOrderedDetails.setProcedureStatusAPI("Laboratory");
        v_benLabTestOrderedDetails.setProcedureType("Laboratory");
        v_benLabTestOrderedDetails.setRange_max(3);
        v_benLabTestOrderedDetails.setRange_min(1);
        v_benLabTestOrderedDetails.setRange_normal_max(3);
        v_benLabTestOrderedDetails.setRange_normal_min(1);
        v_benLabTestOrderedDetails.setResultValue("42");
        v_benLabTestOrderedDetails.setTestComponentDesc("Laboratory");
        v_benLabTestOrderedDetails.setTestComponentID(1);
        v_benLabTestOrderedDetails.setTestComponentName("Laboratory");
        v_benLabTestOrderedDetails.setVisitCode(1L);

        V_benLabTestOrderedDetails v_benLabTestOrderedDetails2 = new V_benLabTestOrderedDetails();
        v_benLabTestOrderedDetails2.setBenVisitID(2L);
        v_benLabTestOrderedDetails2.setBeneficiaryRegID(2L);
        v_benLabTestOrderedDetails2.setCalibrationEndAPI("Radiology");
        v_benLabTestOrderedDetails2.setCalibrationStartAPI("Radiology");
        v_benLabTestOrderedDetails2.setCalibrationStatusAPI("Radiology");
        v_benLabTestOrderedDetails2.setComponentCode("Radiology");
        v_benLabTestOrderedDetails2.setComponentUnit("Radiology");
        v_benLabTestOrderedDetails2.setDiscoveryCode("Radiology");
        v_benLabTestOrderedDetails2.setIOTComponentName("Radiology");
        v_benLabTestOrderedDetails2.setIOTProcedureID("Radiology");
        v_benLabTestOrderedDetails2.setId("Laboratory");
        v_benLabTestOrderedDetails2.setInputType("Radiology");
        v_benLabTestOrderedDetails2.setIotProcedureName("Radiology");
        v_benLabTestOrderedDetails2.setIsDecimal(false);
        v_benLabTestOrderedDetails2.setIsLabProcedure(false);
        v_benLabTestOrderedDetails2.setIsMandatory(false);
        v_benLabTestOrderedDetails2.setMeasurementUnit("Radiology");
        v_benLabTestOrderedDetails2.setPrescriptionID(2L);
        v_benLabTestOrderedDetails2.setProcedureCode("Radiology");
        v_benLabTestOrderedDetails2.setProcedureDesc("Radiology");
        v_benLabTestOrderedDetails2.setProcedureEndAPI("Radiology");
        v_benLabTestOrderedDetails2.setProcedureID(2);
        v_benLabTestOrderedDetails2.setProcedureName("Radiology");
        v_benLabTestOrderedDetails2.setProcedureStartAPI("Radiology");
        v_benLabTestOrderedDetails2.setProcedureStatusAPI("Radiology");
        v_benLabTestOrderedDetails2.setProcedureType("Radiology");
        v_benLabTestOrderedDetails2.setRange_max(1);
        v_benLabTestOrderedDetails2.setRange_min(0);
        v_benLabTestOrderedDetails2.setRange_normal_max(1);
        v_benLabTestOrderedDetails2.setRange_normal_min(0);
        v_benLabTestOrderedDetails2.setResultValue("Laboratory");
        v_benLabTestOrderedDetails2.setTestComponentDesc("Radiology");
        v_benLabTestOrderedDetails2.setTestComponentID(2);
        v_benLabTestOrderedDetails2.setTestComponentName("Radiology");
        v_benLabTestOrderedDetails2.setVisitCode(0L);

        ArrayList<V_benLabTestOrderedDetails> v_benLabTestOrderedDetailsList = new ArrayList<>();
        v_benLabTestOrderedDetailsList.add(v_benLabTestOrderedDetails2);
        v_benLabTestOrderedDetailsList.add(v_benLabTestOrderedDetails);
        when(v_benLabTestOrderedDetailsRepo
                .findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDNotInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
                        Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(), Mockito.<ArrayList<Integer>>any()))
                .thenReturn(v_benLabTestOrderedDetailsList);

        // Act
        String actualBenePrescribedProcedureDetails = labTechnicianServiceImpl.getBenePrescribedProcedureDetails(1L, 1L);

        // Assert
        verify(labResultEntryRepo).findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(isA(Long.class),
                isA(Long.class));
        verify(v_benLabTestOrderedDetailsRepo, atLeast(1))
                .findDistinctByBeneficiaryRegIDAndVisitCodeAndProcedureTypeAndProcedureIDNotInOrderByProcedureIDAscTestComponentIDAscResultValueAsc(
                        isA(Long.class), isA(Long.class), Mockito.<String>any(), isA(ArrayList.class));
        assertEquals(
                "{\"radiologyList\":[{\"procedureDesc\":\"Radiology\",\"procedureType\":\"Radiology\",\"prescriptionID\":2,\"compDetails"
                        + "\":{\"testComponentName\":\"Radiology\",\"inputType\":\"File\",\"testComponentID\":2,\"testComponentDesc\":\"Radiology"
                        + "\"},\"procedureName\":\"Radiology\",\"procedureID\":2},{\"procedureDesc\":\"Laboratory\",\"procedureType\":\"Radiology"
                        + "\",\"prescriptionID\":1,\"compDetails\":{\"testComponentName\":\"Laboratory\",\"inputType\":\"File\",\"testComponentID"
                        + "\":1,\"testComponentDesc\":\"Laboratory\"},\"procedureName\":\"Laboratory\",\"procedureID\":1}],\"laboratoryList"
                        + "\":[{\"procedureDesc\":\"Radiology\",\"procedureType\":\"Laboratory\",\"prescriptionID\":2,\"calibrationEndAPI\":"
                        + "\"Radiology\",\"procedureCode\":\"Radiology\",\"calibrationStatusAPI\":\"Radiology\",\"procedureID\":2,\"iotProcedureName"
                        + "\":\"Radiology\",\"isLabProcedure\":false,\"calibrationStartAPI\":\"Radiology\",\"discoveryCode\":\"Radiology\","
                        + "\"procedureName\":\"Radiology\",\"compListDetails\":[{\"range_normal_min\":0,\"compOpt\":[{\"name\":\"Laboratory\""
                        + "}],\"componentCode\":\"Radiology\",\"range_min\":0,\"testComponentID\":2,\"measurementUnit\":\"Radiology\","
                        + "\"testComponentName\":\"Radiology\",\"iotComponentName\":\"Radiology\",\"range_normal_max\":1,\"iotProcedureID\""
                        + ":\"Radiology\",\"componentUnit\":\"Radiology\",\"isDecimal\":false,\"inputType\":\"Radiology\",\"range_max\":1,"
                        + "\"testComponentDesc\":\"Radiology\"}],\"procedureStatusAPI\":\"Radiology\",\"isMandatory\":false,\"procedureEndAPI"
                        + "\":\"Radiology\",\"procedureStartAPI\":\"Radiology\"},{\"procedureDesc\":\"Laboratory\",\"procedureType\":\"Laboratory"
                        + "\",\"prescriptionID\":1,\"calibrationEndAPI\":\"Laboratory\",\"procedureCode\":\"Laboratory\",\"calibrationStatusAPI"
                        + "\":\"Laboratory\",\"procedureID\":1,\"iotProcedureName\":\"Laboratory\",\"isLabProcedure\":true,\"calibrationStartAPI"
                        + "\":\"Laboratory\",\"discoveryCode\":\"Laboratory\",\"procedureName\":\"Laboratory\",\"compListDetails\":[{\"range"
                        + "_normal_min\":1,\"compOpt\":[{\"name\":\"42\"}],\"componentCode\":\"Laboratory\",\"range_min\":1,\"testComponentID"
                        + "\":1,\"measurementUnit\":\"Laboratory\",\"testComponentName\":\"Laboratory\",\"iotComponentName\":\"Laboratory\","
                        + "\"range_normal_max\":3,\"iotProcedureID\":\"Laboratory\",\"componentUnit\":\"Laboratory\",\"isDecimal\":true,"
                        + "\"inputType\":\"Laboratory\",\"range_max\":3,\"testComponentDesc\":\"Laboratory\"}],\"procedureStatusAPI\":"
                        + "\"Laboratory\",\"isMandatory\":true,\"procedureEndAPI\":\"Laboratory\",\"procedureStartAPI\":\"Laboratory\"}],"
                        + "\"archive\":[]}",
                actualBenePrescribedProcedureDetails);
    }

    @Test
    void testGetLabResultDataForBen() {
        // Arrange
        when(labResultEntryRepo.findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        ArrayList<LabResultEntry> actualLabResultDataForBen = labTechnicianServiceImpl.getLabResultDataForBen(1L, 1L);

        // Assert
        verify(labResultEntryRepo).findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(isA(Long.class),
                isA(Long.class));
        assertTrue(actualLabResultDataForBen.isEmpty());
    }

    @Test
    void testGetLabResultDataForBen2() {
        // Arrange
        ProcedureData procedureData = new ProcedureData();
        procedureData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        procedureData.setCreatedDate(mock(Timestamp.class));
        procedureData.setDeleted(true);
        procedureData.setGender("Gender");
        procedureData.setLabResultEntry(new HashSet<>());
        procedureData.setLastModDate(mock(Timestamp.class));
        procedureData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        procedureData.setProcedureDesc("Procedure Desc");
        procedureData.setProcedureID(1);
        procedureData.setProcedureName("Procedure Name");
        procedureData.setProcedureType("Procedure Type");
        procedureData.setProcessed("Processed");
        procedureData.setProviderServiceMapID(1);

        TestComponentMaster testComponentMaster = new TestComponentMaster();
        testComponentMaster.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        testComponentMaster.setCreatedDate(mock(Timestamp.class));
        testComponentMaster.setDeleted(true);
        testComponentMaster.setInputType("Input Type");
        testComponentMaster.setLabResultEntry(new HashSet<>());
        testComponentMaster.setLastModDate(mock(Timestamp.class));
        testComponentMaster.setLionicNum("Lionic Num");
        testComponentMaster.setLionicTerm("Lionic Term");
        testComponentMaster.setMeasurementUnit("Measurement Unit");
        testComponentMaster.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        testComponentMaster.setProcessed("Processed");
        testComponentMaster.setProviderServiceMapID(1);
        testComponentMaster.setRange_max(3);
        testComponentMaster.setRange_min(1);
        testComponentMaster.setRange_normal_max(3);
        testComponentMaster.setRange_normal_min(1);
        testComponentMaster.setTestComponentDesc("Test Component Desc");
        testComponentMaster.setTestComponentID(1);
        testComponentMaster.setTestComponentName("Test Component Name");

        ProcedureData procedureData2 = new ProcedureData();
        procedureData2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        procedureData2.setCreatedDate(mock(Timestamp.class));
        procedureData2.setDeleted(true);
        procedureData2.setGender("Gender");
        procedureData2.setLabResultEntry(new HashSet<>());
        procedureData2.setLastModDate(mock(Timestamp.class));
        procedureData2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        procedureData2.setProcedureDesc("Procedure Desc");
        procedureData2.setProcedureID(1);
        procedureData2.setProcedureName("Procedure Name");
        procedureData2.setProcedureType("Procedure Type");
        procedureData2.setProcessed("Processed");
        procedureData2.setProviderServiceMapID(1);

        TestComponentMaster testComponentMaster2 = new TestComponentMaster();
        testComponentMaster2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        testComponentMaster2.setCreatedDate(mock(Timestamp.class));
        testComponentMaster2.setDeleted(true);
        testComponentMaster2.setInputType("Input Type");
        testComponentMaster2.setLabResultEntry(new HashSet<>());
        testComponentMaster2.setLastModDate(mock(Timestamp.class));
        testComponentMaster2.setLionicNum("Lionic Num");
        testComponentMaster2.setLionicTerm("Lionic Term");
        testComponentMaster2.setMeasurementUnit("Measurement Unit");
        testComponentMaster2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        testComponentMaster2.setProcessed("Processed");
        testComponentMaster2.setProviderServiceMapID(1);
        testComponentMaster2.setRange_max(3);
        testComponentMaster2.setRange_min(1);
        testComponentMaster2.setRange_normal_max(3);
        testComponentMaster2.setRange_normal_min(1);
        testComponentMaster2.setTestComponentDesc("Test Component Desc");
        testComponentMaster2.setTestComponentID(1);
        testComponentMaster2.setTestComponentName("Test Component Name");
        LabResultEntry labResultEntry = mock(LabResultEntry.class);
        when(labResultEntry.getStripsNotAvailable()).thenReturn(true);
        when(labResultEntry.getRemarks()).thenReturn("Remarks");
        when(labResultEntry.getTestReportFilePath()).thenReturn("42");
        when(labResultEntry.getTestResultUnit()).thenReturn("Test Result Unit");
        when(labResultEntry.getTestResultValue()).thenReturn("42");
        when(labResultEntry.getTestComponentMaster()).thenReturn(testComponentMaster2);
        when(labResultEntry.getTestComponentID()).thenReturn(1);
        when(labResultEntry.getCreatedDate()).thenReturn(mock(Timestamp.class));
        when(labResultEntry.getProcedureData()).thenReturn(procedureData2);
        when(labResultEntry.getProcedureID()).thenReturn(1);
        when(labResultEntry.getPrescriptionID()).thenReturn(1L);
        doNothing().when(labResultEntry).setBenVisitID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setCompList(Mockito.<List<Map<String, String>>>any());
        doNothing().when(labResultEntry).setComponentList(Mockito.<ArrayList<Map<String, Object>>>any());
        doNothing().when(labResultEntry).setCreatedBy(Mockito.<String>any());
        doNothing().when(labResultEntry).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(labResultEntry).setDate(Mockito.<Date>any());
        doNothing().when(labResultEntry).setDeleted(Mockito.<Boolean>any());
        doNothing().when(labResultEntry).setFileIDs(Mockito.<Integer[]>any());
        doNothing().when(labResultEntry).setID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setLabCompleted(Mockito.<Boolean>any());
        doNothing().when(labResultEntry).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(labResultEntry).setModifiedBy(Mockito.<String>any());
        doNothing().when(labResultEntry).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setPrescriptionID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setProcedureData(Mockito.<ProcedureData>any());
        doNothing().when(labResultEntry).setProcedureID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setProcedureName(Mockito.<String>any());
        doNothing().when(labResultEntry).setProcedureType(Mockito.<String>any());
        doNothing().when(labResultEntry).setProcessed(Mockito.<String>any());
        doNothing().when(labResultEntry).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setRemarks(Mockito.<String>any());
        doNothing().when(labResultEntry).setReservedForChange(Mockito.<String>any());
        doNothing().when(labResultEntry).setStripsNotAvailable(Mockito.<Boolean>any());
        doNothing().when(labResultEntry).setSyncedBy(Mockito.<String>any());
        doNothing().when(labResultEntry).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(labResultEntry).setTestComponentID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setTestComponentMaster(Mockito.<TestComponentMaster>any());
        doNothing().when(labResultEntry).setTestReportFilePath(Mockito.<String>any());
        doNothing().when(labResultEntry).setTestResultUnit(Mockito.<String>any());
        doNothing().when(labResultEntry).setTestResultValue(Mockito.<String>any());
        doNothing().when(labResultEntry).setVanID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(labResultEntry).setVehicalNo(Mockito.<String>any());
        doNothing().when(labResultEntry).setVisitCode(Mockito.<Long>any());
        labResultEntry.setBenVisitID(1L);
        labResultEntry.setBeneficiaryRegID(1L);
        labResultEntry.setCompList(new ArrayList<>());
        labResultEntry.setComponentList(new ArrayList<>());
        labResultEntry.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        labResultEntry.setCreatedDate(mock(Timestamp.class));
        labResultEntry.setDate(mock(Date.class));
        labResultEntry.setDeleted(true);
        labResultEntry.setFileIDs(new Integer[]{1});
        labResultEntry.setID(1L);
        labResultEntry.setLabCompleted(true);
        labResultEntry.setLastModDate(mock(Timestamp.class));
        labResultEntry.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        labResultEntry.setParkingPlaceID(1);
        labResultEntry.setPrescriptionID(1L);
        labResultEntry.setProcedureData(procedureData);
        labResultEntry.setProcedureID(1);
        labResultEntry.setProcedureName("Procedure Name");
        labResultEntry.setProcedureType("Procedure Type");
        labResultEntry.setProcessed("Processed");
        labResultEntry.setProviderServiceMapID(1);
        labResultEntry.setRemarks("Remarks");
        labResultEntry.setReservedForChange("Reserved For Change");
        labResultEntry.setStripsNotAvailable(true);
        labResultEntry.setSyncedBy("Synced By");
        labResultEntry.setSyncedDate(mock(Timestamp.class));
        labResultEntry.setTestComponentID(1);
        labResultEntry.setTestComponentMaster(testComponentMaster);
        labResultEntry.setTestReportFilePath("/directory/foo.txt");
        labResultEntry.setTestResultUnit("Test Result Unit");
        labResultEntry.setTestResultValue("42");
        labResultEntry.setVanID(1);
        labResultEntry.setVanSerialNo(1L);
        labResultEntry.setVehicalNo("Vehical No");
        labResultEntry.setVisitCode(1L);

        ArrayList<LabResultEntry> labResultEntryList = new ArrayList<>();
        labResultEntryList.add(labResultEntry);
        when(labResultEntryRepo.findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(labResultEntryList);

        // Act
        ArrayList<LabResultEntry> actualLabResultDataForBen = labTechnicianServiceImpl.getLabResultDataForBen(1L, 1L);

        // Assert
        verify(labResultEntry).getCreatedDate();
        verify(labResultEntry).getPrescriptionID();
        verify(labResultEntry, atLeast(1)).getProcedureData();
        verify(labResultEntry, atLeast(1)).getProcedureID();
        verify(labResultEntry).getRemarks();
        verify(labResultEntry).getStripsNotAvailable();
        verify(labResultEntry).getTestComponentID();
        verify(labResultEntry, atLeast(1)).getTestComponentMaster();
        verify(labResultEntry, atLeast(1)).getTestReportFilePath();
        verify(labResultEntry).getTestResultUnit();
        verify(labResultEntry).getTestResultValue();
        verify(labResultEntry).setBenVisitID(isA(Long.class));
        verify(labResultEntry).setBeneficiaryRegID(isA(Long.class));
        verify(labResultEntry).setCompList(isA(List.class));
        verify(labResultEntry).setComponentList(isA(ArrayList.class));
        verify(labResultEntry).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(labResultEntry).setCreatedDate(isA(Timestamp.class));
        verify(labResultEntry).setDate(isA(Date.class));
        verify(labResultEntry).setDeleted(isA(Boolean.class));
        verify(labResultEntry).setFileIDs(isA(Integer[].class));
        verify(labResultEntry).setID(isA(Long.class));
        verify(labResultEntry).setLabCompleted(isA(Boolean.class));
        verify(labResultEntry).setLastModDate(isA(Timestamp.class));
        verify(labResultEntry).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(labResultEntry).setParkingPlaceID(isA(Integer.class));
        verify(labResultEntry).setPrescriptionID(isA(Long.class));
        verify(labResultEntry).setProcedureData(isA(ProcedureData.class));
        verify(labResultEntry).setProcedureID(isA(Integer.class));
        verify(labResultEntry).setProcedureName(eq("Procedure Name"));
        verify(labResultEntry).setProcedureType(eq("Procedure Type"));
        verify(labResultEntry).setProcessed(eq("Processed"));
        verify(labResultEntry).setProviderServiceMapID(isA(Integer.class));
        verify(labResultEntry).setRemarks(eq("Remarks"));
        verify(labResultEntry).setReservedForChange(eq("Reserved For Change"));
        verify(labResultEntry).setStripsNotAvailable(isA(Boolean.class));
        verify(labResultEntry).setSyncedBy(eq("Synced By"));
        verify(labResultEntry).setSyncedDate(isA(Timestamp.class));
        verify(labResultEntry).setTestComponentID(isA(Integer.class));
        verify(labResultEntry).setTestComponentMaster(isA(TestComponentMaster.class));
        verify(labResultEntry).setTestReportFilePath(eq("/directory/foo.txt"));
        verify(labResultEntry).setTestResultUnit(eq("Test Result Unit"));
        verify(labResultEntry).setTestResultValue(eq("42"));
        verify(labResultEntry).setVanID(isA(Integer.class));
        verify(labResultEntry).setVanSerialNo(isA(Long.class));
        verify(labResultEntry).setVehicalNo(eq("Vehical No"));
        verify(labResultEntry).setVisitCode(isA(Long.class));
        verify(labResultEntryRepo).findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(isA(Long.class),
                isA(Long.class));
        assertEquals(1, actualLabResultDataForBen.size());
    }

    @Test
    void testGetLabResultDataForBen3() {
        // Arrange
        ProcedureData procedureData = new ProcedureData();
        procedureData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        procedureData.setCreatedDate(mock(Timestamp.class));
        procedureData.setDeleted(true);
        procedureData.setGender("Gender");
        procedureData.setLabResultEntry(new HashSet<>());
        procedureData.setLastModDate(mock(Timestamp.class));
        procedureData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        procedureData.setProcedureDesc("Procedure Desc");
        procedureData.setProcedureID(1);
        procedureData.setProcedureName("Procedure Name");
        procedureData.setProcedureType("Procedure Type");
        procedureData.setProcessed("Processed");
        procedureData.setProviderServiceMapID(1);

        TestComponentMaster testComponentMaster = new TestComponentMaster();
        testComponentMaster.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        testComponentMaster.setCreatedDate(mock(Timestamp.class));
        testComponentMaster.setDeleted(true);
        testComponentMaster.setInputType("Input Type");
        testComponentMaster.setLabResultEntry(new HashSet<>());
        testComponentMaster.setLastModDate(mock(Timestamp.class));
        testComponentMaster.setLionicNum("Lionic Num");
        testComponentMaster.setLionicTerm("Lionic Term");
        testComponentMaster.setMeasurementUnit("Measurement Unit");
        testComponentMaster.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        testComponentMaster.setProcessed("Processed");
        testComponentMaster.setProviderServiceMapID(1);
        testComponentMaster.setRange_max(3);
        testComponentMaster.setRange_min(1);
        testComponentMaster.setRange_normal_max(3);
        testComponentMaster.setRange_normal_min(1);
        testComponentMaster.setTestComponentDesc("Test Component Desc");
        testComponentMaster.setTestComponentID(1);
        testComponentMaster.setTestComponentName("Test Component Name");

        ProcedureData procedureData2 = new ProcedureData();
        procedureData2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        procedureData2.setCreatedDate(mock(Timestamp.class));
        procedureData2.setDeleted(true);
        procedureData2.setGender("Gender");
        procedureData2.setLabResultEntry(new HashSet<>());
        procedureData2.setLastModDate(mock(Timestamp.class));
        procedureData2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        procedureData2.setProcedureDesc("Procedure Desc");
        procedureData2.setProcedureID(1);
        procedureData2.setProcedureName("Procedure Name");
        procedureData2.setProcedureType("Procedure Type");
        procedureData2.setProcessed("Processed");
        procedureData2.setProviderServiceMapID(1);

        TestComponentMaster testComponentMaster2 = new TestComponentMaster();
        testComponentMaster2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        testComponentMaster2.setCreatedDate(mock(Timestamp.class));
        testComponentMaster2.setDeleted(true);
        testComponentMaster2.setInputType("Input Type");
        testComponentMaster2.setLabResultEntry(new HashSet<>());
        testComponentMaster2.setLastModDate(mock(Timestamp.class));
        testComponentMaster2.setLionicNum("Lionic Num");
        testComponentMaster2.setLionicTerm("Lionic Term");
        testComponentMaster2.setMeasurementUnit("Measurement Unit");
        testComponentMaster2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        testComponentMaster2.setProcessed("Processed");
        testComponentMaster2.setProviderServiceMapID(1);
        testComponentMaster2.setRange_max(3);
        testComponentMaster2.setRange_min(1);
        testComponentMaster2.setRange_normal_max(3);
        testComponentMaster2.setRange_normal_min(1);
        testComponentMaster2.setTestComponentDesc("Test Component Desc");
        testComponentMaster2.setTestComponentID(1);
        testComponentMaster2.setTestComponentName("Test Component Name");
        LabResultEntry labResultEntry = mock(LabResultEntry.class);
        when(labResultEntry.getStripsNotAvailable()).thenReturn(true);
        when(labResultEntry.getRemarks()).thenReturn("Remarks");
        when(labResultEntry.getTestReportFilePath()).thenReturn("");
        when(labResultEntry.getTestResultUnit()).thenReturn("Test Result Unit");
        when(labResultEntry.getTestResultValue()).thenReturn("42");
        when(labResultEntry.getTestComponentMaster()).thenReturn(testComponentMaster2);
        when(labResultEntry.getTestComponentID()).thenReturn(1);
        when(labResultEntry.getCreatedDate()).thenReturn(mock(Timestamp.class));
        when(labResultEntry.getProcedureData()).thenReturn(procedureData2);
        when(labResultEntry.getProcedureID()).thenReturn(1);
        when(labResultEntry.getPrescriptionID()).thenReturn(1L);
        doNothing().when(labResultEntry).setBenVisitID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setCompList(Mockito.<List<Map<String, String>>>any());
        doNothing().when(labResultEntry).setComponentList(Mockito.<ArrayList<Map<String, Object>>>any());
        doNothing().when(labResultEntry).setCreatedBy(Mockito.<String>any());
        doNothing().when(labResultEntry).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(labResultEntry).setDate(Mockito.<Date>any());
        doNothing().when(labResultEntry).setDeleted(Mockito.<Boolean>any());
        doNothing().when(labResultEntry).setFileIDs(Mockito.<Integer[]>any());
        doNothing().when(labResultEntry).setID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setLabCompleted(Mockito.<Boolean>any());
        doNothing().when(labResultEntry).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(labResultEntry).setModifiedBy(Mockito.<String>any());
        doNothing().when(labResultEntry).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setPrescriptionID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setProcedureData(Mockito.<ProcedureData>any());
        doNothing().when(labResultEntry).setProcedureID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setProcedureName(Mockito.<String>any());
        doNothing().when(labResultEntry).setProcedureType(Mockito.<String>any());
        doNothing().when(labResultEntry).setProcessed(Mockito.<String>any());
        doNothing().when(labResultEntry).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setRemarks(Mockito.<String>any());
        doNothing().when(labResultEntry).setReservedForChange(Mockito.<String>any());
        doNothing().when(labResultEntry).setStripsNotAvailable(Mockito.<Boolean>any());
        doNothing().when(labResultEntry).setSyncedBy(Mockito.<String>any());
        doNothing().when(labResultEntry).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(labResultEntry).setTestComponentID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setTestComponentMaster(Mockito.<TestComponentMaster>any());
        doNothing().when(labResultEntry).setTestReportFilePath(Mockito.<String>any());
        doNothing().when(labResultEntry).setTestResultUnit(Mockito.<String>any());
        doNothing().when(labResultEntry).setTestResultValue(Mockito.<String>any());
        doNothing().when(labResultEntry).setVanID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(labResultEntry).setVehicalNo(Mockito.<String>any());
        doNothing().when(labResultEntry).setVisitCode(Mockito.<Long>any());
        labResultEntry.setBenVisitID(1L);
        labResultEntry.setBeneficiaryRegID(1L);
        labResultEntry.setCompList(new ArrayList<>());
        labResultEntry.setComponentList(new ArrayList<>());
        labResultEntry.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        labResultEntry.setCreatedDate(mock(Timestamp.class));
        labResultEntry.setDate(mock(Date.class));
        labResultEntry.setDeleted(true);
        labResultEntry.setFileIDs(new Integer[]{1});
        labResultEntry.setID(1L);
        labResultEntry.setLabCompleted(true);
        labResultEntry.setLastModDate(mock(Timestamp.class));
        labResultEntry.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        labResultEntry.setParkingPlaceID(1);
        labResultEntry.setPrescriptionID(1L);
        labResultEntry.setProcedureData(procedureData);
        labResultEntry.setProcedureID(1);
        labResultEntry.setProcedureName("Procedure Name");
        labResultEntry.setProcedureType("Procedure Type");
        labResultEntry.setProcessed("Processed");
        labResultEntry.setProviderServiceMapID(1);
        labResultEntry.setRemarks("Remarks");
        labResultEntry.setReservedForChange("Reserved For Change");
        labResultEntry.setStripsNotAvailable(true);
        labResultEntry.setSyncedBy("Synced By");
        labResultEntry.setSyncedDate(mock(Timestamp.class));
        labResultEntry.setTestComponentID(1);
        labResultEntry.setTestComponentMaster(testComponentMaster);
        labResultEntry.setTestReportFilePath("/directory/foo.txt");
        labResultEntry.setTestResultUnit("Test Result Unit");
        labResultEntry.setTestResultValue("42");
        labResultEntry.setVanID(1);
        labResultEntry.setVanSerialNo(1L);
        labResultEntry.setVehicalNo("Vehical No");
        labResultEntry.setVisitCode(1L);

        ArrayList<LabResultEntry> labResultEntryList = new ArrayList<>();
        labResultEntryList.add(labResultEntry);
        when(labResultEntryRepo.findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(labResultEntryList);

        // Act
        ArrayList<LabResultEntry> actualLabResultDataForBen = labTechnicianServiceImpl.getLabResultDataForBen(1L, 1L);

        // Assert
        verify(labResultEntry).getCreatedDate();
        verify(labResultEntry).getPrescriptionID();
        verify(labResultEntry, atLeast(1)).getProcedureData();
        verify(labResultEntry, atLeast(1)).getProcedureID();
        verify(labResultEntry).getRemarks();
        verify(labResultEntry).getStripsNotAvailable();
        verify(labResultEntry).getTestComponentID();
        verify(labResultEntry, atLeast(1)).getTestComponentMaster();
        verify(labResultEntry, atLeast(1)).getTestReportFilePath();
        verify(labResultEntry).getTestResultUnit();
        verify(labResultEntry).getTestResultValue();
        verify(labResultEntry).setBenVisitID(isA(Long.class));
        verify(labResultEntry).setBeneficiaryRegID(isA(Long.class));
        verify(labResultEntry).setCompList(isA(List.class));
        verify(labResultEntry).setComponentList(isA(ArrayList.class));
        verify(labResultEntry).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(labResultEntry).setCreatedDate(isA(Timestamp.class));
        verify(labResultEntry).setDate(isA(Date.class));
        verify(labResultEntry).setDeleted(isA(Boolean.class));
        verify(labResultEntry).setFileIDs(isA(Integer[].class));
        verify(labResultEntry).setID(isA(Long.class));
        verify(labResultEntry).setLabCompleted(isA(Boolean.class));
        verify(labResultEntry).setLastModDate(isA(Timestamp.class));
        verify(labResultEntry).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(labResultEntry).setParkingPlaceID(isA(Integer.class));
        verify(labResultEntry).setPrescriptionID(isA(Long.class));
        verify(labResultEntry).setProcedureData(isA(ProcedureData.class));
        verify(labResultEntry).setProcedureID(isA(Integer.class));
        verify(labResultEntry).setProcedureName(eq("Procedure Name"));
        verify(labResultEntry).setProcedureType(eq("Procedure Type"));
        verify(labResultEntry).setProcessed(eq("Processed"));
        verify(labResultEntry).setProviderServiceMapID(isA(Integer.class));
        verify(labResultEntry).setRemarks(eq("Remarks"));
        verify(labResultEntry).setReservedForChange(eq("Reserved For Change"));
        verify(labResultEntry).setStripsNotAvailable(isA(Boolean.class));
        verify(labResultEntry).setSyncedBy(eq("Synced By"));
        verify(labResultEntry).setSyncedDate(isA(Timestamp.class));
        verify(labResultEntry).setTestComponentID(isA(Integer.class));
        verify(labResultEntry).setTestComponentMaster(isA(TestComponentMaster.class));
        verify(labResultEntry).setTestReportFilePath(eq("/directory/foo.txt"));
        verify(labResultEntry).setTestResultUnit(eq("Test Result Unit"));
        verify(labResultEntry).setTestResultValue(eq("42"));
        verify(labResultEntry).setVanID(isA(Integer.class));
        verify(labResultEntry).setVanSerialNo(isA(Long.class));
        verify(labResultEntry).setVehicalNo(eq("Vehical No"));
        verify(labResultEntry).setVisitCode(isA(Long.class));
        verify(labResultEntryRepo).findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(isA(Long.class),
                isA(Long.class));
        assertEquals(1, actualLabResultDataForBen.size());
    }

    @Test
    void testSaveLabTestResult() throws Exception {
        // Arrange, Act and Assert
        assertEquals(1, labTechnicianServiceImpl.saveLabTestResult(new JsonObject()).intValue());
        assertEquals(1, labTechnicianServiceImpl.saveLabTestResult((JsonObject) null).intValue());
    }

    @Test
    void testSaveLabTestResult2() throws Exception {
        // Arrange
        JsonObject requestOBJ = new JsonObject();
        requestOBJ.addProperty("Property", "42");

        // Act and Assert
        assertEquals(1, labTechnicianServiceImpl.saveLabTestResult(requestOBJ).intValue());
    }

    @Test
    void testSaveLabTestResult3() throws Exception {
        // Arrange
        JsonObject requestOBJ = new JsonObject();
        requestOBJ.addProperty("labTestResults", (String) null);

        // Act and Assert
        assertEquals(1, labTechnicianServiceImpl.saveLabTestResult(requestOBJ).intValue());
    }

    @Test
    void testSaveLabTestResult4() {
        // Arrange
        WrapperLabResultEntry wrapperLabResults = new WrapperLabResultEntry();
        wrapperLabResults.setBenFlowID(1L);
        wrapperLabResults.setBeneficiaryRegID(1L);
        wrapperLabResults.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperLabResults.setDoctorFlag((short) 1);
        wrapperLabResults.setLabCompleted(true);
        wrapperLabResults.setLabTestResults(new ArrayList<>());
        wrapperLabResults.setNurseFlag((short) 1);
        wrapperLabResults.setParkingPlaceID(1);
        wrapperLabResults.setProviderServiceMapID(1);
        wrapperLabResults.setRadiologyTestResults(new ArrayList<>());
        wrapperLabResults.setSpecialist_flag((short) 1);
        wrapperLabResults.setVanID(1);
        wrapperLabResults.setVisitCode(1L);
        wrapperLabResults.setVisitID(1L);

        // Act and Assert
        assertEquals(1, labTechnicianServiceImpl.saveLabTestResult(wrapperLabResults).intValue());
    }

    @Test
    void testSaveLabTestResult5() {
        // Arrange
        WrapperLabResultEntry wrapperLabResults = mock(WrapperLabResultEntry.class);
        when(wrapperLabResults.getLabTestResults()).thenReturn(new ArrayList<>());
        when(wrapperLabResults.getRadiologyTestResults()).thenReturn(new ArrayList<>());
        doNothing().when(wrapperLabResults).setBenFlowID(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setCreatedBy(Mockito.<String>any());
        doNothing().when(wrapperLabResults).setDoctorFlag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setLabCompleted(Mockito.<Boolean>any());
        doNothing().when(wrapperLabResults).setLabTestResults(Mockito.<List<LabResultEntry>>any());
        doNothing().when(wrapperLabResults).setNurseFlag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setRadiologyTestResults(Mockito.<List<LabResultEntry>>any());
        doNothing().when(wrapperLabResults).setSpecialist_flag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setVanID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setVisitCode(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setVisitID(Mockito.<Long>any());
        wrapperLabResults.setBenFlowID(1L);
        wrapperLabResults.setBeneficiaryRegID(1L);
        wrapperLabResults.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperLabResults.setDoctorFlag((short) 1);
        wrapperLabResults.setLabCompleted(true);
        wrapperLabResults.setLabTestResults(new ArrayList<>());
        wrapperLabResults.setNurseFlag((short) 1);
        wrapperLabResults.setParkingPlaceID(1);
        wrapperLabResults.setProviderServiceMapID(1);
        wrapperLabResults.setRadiologyTestResults(new ArrayList<>());
        wrapperLabResults.setSpecialist_flag((short) 1);
        wrapperLabResults.setVanID(1);
        wrapperLabResults.setVisitCode(1L);
        wrapperLabResults.setVisitID(1L);

        // Act
        Integer actualSaveLabTestResultResult = labTechnicianServiceImpl.saveLabTestResult(wrapperLabResults);

        // Assert
        verify(wrapperLabResults).getLabTestResults();
        verify(wrapperLabResults, atLeast(1)).getRadiologyTestResults();
        verify(wrapperLabResults).setBenFlowID(isA(Long.class));
        verify(wrapperLabResults).setBeneficiaryRegID(isA(Long.class));
        verify(wrapperLabResults).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(wrapperLabResults).setDoctorFlag(isA(Short.class));
        verify(wrapperLabResults).setLabCompleted(isA(Boolean.class));
        verify(wrapperLabResults).setLabTestResults(isA(List.class));
        verify(wrapperLabResults).setNurseFlag(isA(Short.class));
        verify(wrapperLabResults).setParkingPlaceID(isA(Integer.class));
        verify(wrapperLabResults).setProviderServiceMapID(isA(Integer.class));
        verify(wrapperLabResults).setRadiologyTestResults(isA(List.class));
        verify(wrapperLabResults).setSpecialist_flag(isA(Short.class));
        verify(wrapperLabResults).setVanID(isA(Integer.class));
        verify(wrapperLabResults).setVisitCode(isA(Long.class));
        verify(wrapperLabResults).setVisitID(isA(Long.class));
        assertEquals(1, actualSaveLabTestResultResult.intValue());
    }

    @Test
    void testSaveLabTestResult6() {
        // Arrange
        ProcedureData procedureData = new ProcedureData();
        procedureData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        procedureData.setCreatedDate(mock(Timestamp.class));
        procedureData.setDeleted(true);
        procedureData.setGender("Gender");
        procedureData.setLabResultEntry(new HashSet<>());
        procedureData.setLastModDate(mock(Timestamp.class));
        procedureData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        procedureData.setProcedureDesc("Procedure Desc");
        procedureData.setProcedureID(1);
        procedureData.setProcedureName("Procedure Name");
        procedureData.setProcedureType("Procedure Type");
        procedureData.setProcessed("Processed");
        procedureData.setProviderServiceMapID(1);

        TestComponentMaster testComponentMaster = new TestComponentMaster();
        testComponentMaster.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        testComponentMaster.setCreatedDate(mock(Timestamp.class));
        testComponentMaster.setDeleted(true);
        testComponentMaster.setInputType("Input Type");
        testComponentMaster.setLabResultEntry(new HashSet<>());
        testComponentMaster.setLastModDate(mock(Timestamp.class));
        testComponentMaster.setLionicNum("Lionic Num");
        testComponentMaster.setLionicTerm("Lionic Term");
        testComponentMaster.setMeasurementUnit("Measurement Unit");
        testComponentMaster.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        testComponentMaster.setProcessed("Processed");
        testComponentMaster.setProviderServiceMapID(1);
        testComponentMaster.setRange_max(3);
        testComponentMaster.setRange_min(1);
        testComponentMaster.setRange_normal_max(3);
        testComponentMaster.setRange_normal_min(1);
        testComponentMaster.setTestComponentDesc("Test Component Desc");
        testComponentMaster.setTestComponentID(1);
        testComponentMaster.setTestComponentName("Test Component Name");

        LabResultEntry labResultEntry = new LabResultEntry();
        labResultEntry.setBenVisitID(1L);
        labResultEntry.setBeneficiaryRegID(1L);
        labResultEntry.setCompList(new ArrayList<>());
        labResultEntry.setComponentList(new ArrayList<>());
        labResultEntry.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        labResultEntry.setCreatedDate(mock(Timestamp.class));
        labResultEntry.setDate(mock(Date.class));
        labResultEntry.setDeleted(true);
        labResultEntry.setFileIDs(new Integer[]{1});
        labResultEntry.setID(1L);
        labResultEntry.setLabCompleted(true);
        labResultEntry.setLastModDate(mock(Timestamp.class));
        labResultEntry.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        labResultEntry.setParkingPlaceID(1);
        labResultEntry.setPrescriptionID(1L);
        labResultEntry.setProcedureData(procedureData);
        labResultEntry.setProcedureID(1);
        labResultEntry.setProcedureName("Procedure Name");
        labResultEntry.setProcedureType("Procedure Type");
        labResultEntry.setProcessed("Processed");
        labResultEntry.setProviderServiceMapID(1);
        labResultEntry.setRemarks("Remarks");
        labResultEntry.setReservedForChange("Reserved For Change");
        labResultEntry.setStripsNotAvailable(true);
        labResultEntry.setSyncedBy("Synced By");
        labResultEntry.setSyncedDate(mock(Timestamp.class));
        labResultEntry.setTestComponentID(1);
        labResultEntry.setTestComponentMaster(testComponentMaster);
        labResultEntry.setTestReportFilePath("/directory/foo.txt");
        labResultEntry.setTestResultUnit("Test Result Unit");
        labResultEntry.setTestResultValue("42");
        labResultEntry.setVanID(1);
        labResultEntry.setVanSerialNo(1L);
        labResultEntry.setVehicalNo("Vehical No");
        labResultEntry.setVisitCode(1L);

        ArrayList<LabResultEntry> labResultEntryList = new ArrayList<>();
        labResultEntryList.add(labResultEntry);
        WrapperLabResultEntry wrapperLabResults = mock(WrapperLabResultEntry.class);
        when(wrapperLabResults.getLabTestResults()).thenReturn(labResultEntryList);
        when(wrapperLabResults.getRadiologyTestResults()).thenReturn(new ArrayList<>());
        doNothing().when(wrapperLabResults).setBenFlowID(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setCreatedBy(Mockito.<String>any());
        doNothing().when(wrapperLabResults).setDoctorFlag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setLabCompleted(Mockito.<Boolean>any());
        doNothing().when(wrapperLabResults).setLabTestResults(Mockito.<List<LabResultEntry>>any());
        doNothing().when(wrapperLabResults).setNurseFlag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setRadiologyTestResults(Mockito.<List<LabResultEntry>>any());
        doNothing().when(wrapperLabResults).setSpecialist_flag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setVanID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setVisitCode(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setVisitID(Mockito.<Long>any());
        wrapperLabResults.setBenFlowID(1L);
        wrapperLabResults.setBeneficiaryRegID(1L);
        wrapperLabResults.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperLabResults.setDoctorFlag((short) 1);
        wrapperLabResults.setLabCompleted(true);
        wrapperLabResults.setLabTestResults(new ArrayList<>());
        wrapperLabResults.setNurseFlag((short) 1);
        wrapperLabResults.setParkingPlaceID(1);
        wrapperLabResults.setProviderServiceMapID(1);
        wrapperLabResults.setRadiologyTestResults(new ArrayList<>());
        wrapperLabResults.setSpecialist_flag((short) 1);
        wrapperLabResults.setVanID(1);
        wrapperLabResults.setVisitCode(1L);
        wrapperLabResults.setVisitID(1L);

        // Act
        Integer actualSaveLabTestResultResult = labTechnicianServiceImpl.saveLabTestResult(wrapperLabResults);

        // Assert
        verify(wrapperLabResults).getLabTestResults();
        verify(wrapperLabResults).getRadiologyTestResults();
        verify(wrapperLabResults).setBenFlowID(isA(Long.class));
        verify(wrapperLabResults).setBeneficiaryRegID(isA(Long.class));
        verify(wrapperLabResults).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(wrapperLabResults).setDoctorFlag(isA(Short.class));
        verify(wrapperLabResults).setLabCompleted(isA(Boolean.class));
        verify(wrapperLabResults).setLabTestResults(isA(List.class));
        verify(wrapperLabResults).setNurseFlag(isA(Short.class));
        verify(wrapperLabResults).setParkingPlaceID(isA(Integer.class));
        verify(wrapperLabResults).setProviderServiceMapID(isA(Integer.class));
        verify(wrapperLabResults).setRadiologyTestResults(isA(List.class));
        verify(wrapperLabResults).setSpecialist_flag(isA(Short.class));
        verify(wrapperLabResults).setVanID(isA(Integer.class));
        verify(wrapperLabResults).setVisitCode(isA(Long.class));
        verify(wrapperLabResults).setVisitID(isA(Long.class));
        assertEquals(1, actualSaveLabTestResultResult.intValue());
    }

    @Test
    void testSaveLabTestResult7() {
        // Arrange
        when(labResultEntryRepo.saveAll(Mockito.<Iterable<LabResultEntry>>any())).thenReturn(new ArrayList<>());

        ProcedureData procedureData = new ProcedureData();
        procedureData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        procedureData.setCreatedDate(mock(Timestamp.class));
        procedureData.setDeleted(true);
        procedureData.setGender("Gender");
        procedureData.setLabResultEntry(new HashSet<>());
        procedureData.setLastModDate(mock(Timestamp.class));
        procedureData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        procedureData.setProcedureDesc("Procedure Desc");
        procedureData.setProcedureID(1);
        procedureData.setProcedureName("Procedure Name");
        procedureData.setProcedureType("Procedure Type");
        procedureData.setProcessed("Processed");
        procedureData.setProviderServiceMapID(1);

        TestComponentMaster testComponentMaster = new TestComponentMaster();
        testComponentMaster.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        testComponentMaster.setCreatedDate(mock(Timestamp.class));
        testComponentMaster.setDeleted(true);
        testComponentMaster.setInputType("Input Type");
        testComponentMaster.setLabResultEntry(new HashSet<>());
        testComponentMaster.setLastModDate(mock(Timestamp.class));
        testComponentMaster.setLionicNum("Lionic Num");
        testComponentMaster.setLionicTerm("Lionic Term");
        testComponentMaster.setMeasurementUnit("Measurement Unit");
        testComponentMaster.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        testComponentMaster.setProcessed("Processed");
        testComponentMaster.setProviderServiceMapID(1);
        testComponentMaster.setRange_max(3);
        testComponentMaster.setRange_min(1);
        testComponentMaster.setRange_normal_max(3);
        testComponentMaster.setRange_normal_min(1);
        testComponentMaster.setTestComponentDesc("Test Component Desc");
        testComponentMaster.setTestComponentID(1);
        testComponentMaster.setTestComponentName("Test Component Name");

        LabResultEntry labResultEntry = new LabResultEntry();
        labResultEntry.setBenVisitID(1L);
        labResultEntry.setBeneficiaryRegID(1L);
        labResultEntry.setCompList(new ArrayList<>());
        labResultEntry.setComponentList(new ArrayList<>());
        labResultEntry.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        labResultEntry.setCreatedDate(mock(Timestamp.class));
        labResultEntry.setDate(mock(Date.class));
        labResultEntry.setDeleted(true);
        labResultEntry.setFileIDs(new Integer[]{1});
        labResultEntry.setID(1L);
        labResultEntry.setLabCompleted(true);
        labResultEntry.setLastModDate(mock(Timestamp.class));
        labResultEntry.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        labResultEntry.setParkingPlaceID(1);
        labResultEntry.setPrescriptionID(1L);
        labResultEntry.setProcedureData(procedureData);
        labResultEntry.setProcedureID(1);
        labResultEntry.setProcedureName("Procedure Name");
        labResultEntry.setProcedureType("Procedure Type");
        labResultEntry.setProcessed("Processed");
        labResultEntry.setProviderServiceMapID(1);
        labResultEntry.setRemarks("Remarks");
        labResultEntry.setReservedForChange("Reserved For Change");
        labResultEntry.setStripsNotAvailable(true);
        labResultEntry.setSyncedBy("Synced By");
        labResultEntry.setSyncedDate(mock(Timestamp.class));
        labResultEntry.setTestComponentID(1);
        labResultEntry.setTestComponentMaster(testComponentMaster);
        labResultEntry.setTestReportFilePath("/directory/foo.txt");
        labResultEntry.setTestResultUnit("Test Result Unit");
        labResultEntry.setTestResultValue("42");
        labResultEntry.setVanID(1);
        labResultEntry.setVanSerialNo(1L);
        labResultEntry.setVehicalNo("Vehical No");
        labResultEntry.setVisitCode(1L);

        ArrayList<LabResultEntry> labResultEntryList = new ArrayList<>();
        labResultEntryList.add(labResultEntry);
        WrapperLabResultEntry wrapperLabResults = mock(WrapperLabResultEntry.class);
        when(wrapperLabResults.getParkingPlaceID()).thenReturn(1);
        when(wrapperLabResults.getProviderServiceMapID()).thenReturn(1);
        when(wrapperLabResults.getVanID()).thenReturn(1);
        when(wrapperLabResults.getBeneficiaryRegID()).thenReturn(1L);
        when(wrapperLabResults.getVisitCode()).thenReturn(1L);
        when(wrapperLabResults.getVisitID()).thenReturn(1L);
        when(wrapperLabResults.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(wrapperLabResults.getLabTestResults()).thenReturn(new ArrayList<>());
        when(wrapperLabResults.getRadiologyTestResults()).thenReturn(labResultEntryList);
        doNothing().when(wrapperLabResults).setBenFlowID(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setCreatedBy(Mockito.<String>any());
        doNothing().when(wrapperLabResults).setDoctorFlag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setLabCompleted(Mockito.<Boolean>any());
        doNothing().when(wrapperLabResults).setLabTestResults(Mockito.<List<LabResultEntry>>any());
        doNothing().when(wrapperLabResults).setNurseFlag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setRadiologyTestResults(Mockito.<List<LabResultEntry>>any());
        doNothing().when(wrapperLabResults).setSpecialist_flag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setVanID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setVisitCode(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setVisitID(Mockito.<Long>any());
        wrapperLabResults.setBenFlowID(1L);
        wrapperLabResults.setBeneficiaryRegID(1L);
        wrapperLabResults.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperLabResults.setDoctorFlag((short) 1);
        wrapperLabResults.setLabCompleted(true);
        wrapperLabResults.setLabTestResults(new ArrayList<>());
        wrapperLabResults.setNurseFlag((short) 1);
        wrapperLabResults.setParkingPlaceID(1);
        wrapperLabResults.setProviderServiceMapID(1);
        wrapperLabResults.setRadiologyTestResults(new ArrayList<>());
        wrapperLabResults.setSpecialist_flag((short) 1);
        wrapperLabResults.setVanID(1);
        wrapperLabResults.setVisitCode(1L);
        wrapperLabResults.setVisitID(1L);

        // Act
        Integer actualSaveLabTestResultResult = labTechnicianServiceImpl.saveLabTestResult(wrapperLabResults);

        // Assert
        verify(wrapperLabResults).getBeneficiaryRegID();
        verify(wrapperLabResults).getCreatedBy();
        verify(wrapperLabResults).getLabTestResults();
        verify(wrapperLabResults).getParkingPlaceID();
        verify(wrapperLabResults).getProviderServiceMapID();
        verify(wrapperLabResults, atLeast(1)).getRadiologyTestResults();
        verify(wrapperLabResults).getVanID();
        verify(wrapperLabResults).getVisitCode();
        verify(wrapperLabResults).getVisitID();
        verify(wrapperLabResults).setBenFlowID(isA(Long.class));
        verify(wrapperLabResults).setBeneficiaryRegID(isA(Long.class));
        verify(wrapperLabResults).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(wrapperLabResults).setDoctorFlag(isA(Short.class));
        verify(wrapperLabResults).setLabCompleted(isA(Boolean.class));
        verify(wrapperLabResults).setLabTestResults(isA(List.class));
        verify(wrapperLabResults).setNurseFlag(isA(Short.class));
        verify(wrapperLabResults).setParkingPlaceID(isA(Integer.class));
        verify(wrapperLabResults).setProviderServiceMapID(isA(Integer.class));
        verify(wrapperLabResults).setRadiologyTestResults(isA(List.class));
        verify(wrapperLabResults).setSpecialist_flag(isA(Short.class));
        verify(wrapperLabResults).setVanID(isA(Integer.class));
        verify(wrapperLabResults).setVisitCode(isA(Long.class));
        verify(wrapperLabResults).setVisitID(isA(Long.class));
        verify(labResultEntryRepo).saveAll(isA(Iterable.class));
        assertNull(actualSaveLabTestResultResult);
    }

    @Test
    void testSaveLabTestResult8() {
        // Arrange
        ProcedureData procedureData = new ProcedureData();
        procedureData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        procedureData.setCreatedDate(mock(Timestamp.class));
        procedureData.setDeleted(true);
        procedureData.setGender(",");
        procedureData.setLabResultEntry(new HashSet<>());
        procedureData.setLastModDate(mock(Timestamp.class));
        procedureData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        procedureData.setProcedureDesc(",");
        procedureData.setProcedureID(1);
        procedureData.setProcedureName(",");
        procedureData.setProcedureType(",");
        procedureData.setProcessed(",");
        procedureData.setProviderServiceMapID(1);

        TestComponentMaster testComponentMaster = new TestComponentMaster();
        testComponentMaster.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        testComponentMaster.setCreatedDate(mock(Timestamp.class));
        testComponentMaster.setDeleted(true);
        testComponentMaster.setInputType(",");
        testComponentMaster.setLabResultEntry(new HashSet<>());
        testComponentMaster.setLastModDate(mock(Timestamp.class));
        testComponentMaster.setLionicNum(",");
        testComponentMaster.setLionicTerm(",");
        testComponentMaster.setMeasurementUnit(",");
        testComponentMaster.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        testComponentMaster.setProcessed(",");
        testComponentMaster.setProviderServiceMapID(1);
        testComponentMaster.setRange_max(3);
        testComponentMaster.setRange_min(1);
        testComponentMaster.setRange_normal_max(3);
        testComponentMaster.setRange_normal_min(1);
        testComponentMaster.setTestComponentDesc(",");
        testComponentMaster.setTestComponentID(1);
        testComponentMaster.setTestComponentName(",");

        LabResultEntry labResultEntry = new LabResultEntry();
        labResultEntry.setBenVisitID(1L);
        labResultEntry.setBeneficiaryRegID(1L);
        labResultEntry.setCompList(new ArrayList<>());
        labResultEntry.setComponentList(new ArrayList<>());
        labResultEntry.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        labResultEntry.setCreatedDate(mock(Timestamp.class));
        labResultEntry.setDate(mock(Date.class));
        labResultEntry.setDeleted(true);
        labResultEntry.setFileIDs(new Integer[]{1});
        labResultEntry.setID(1L);
        labResultEntry.setLabCompleted(true);
        labResultEntry.setLastModDate(mock(Timestamp.class));
        labResultEntry.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        labResultEntry.setParkingPlaceID(1);
        labResultEntry.setPrescriptionID(1L);
        labResultEntry.setProcedureData(procedureData);
        labResultEntry.setProcedureID(1);
        labResultEntry.setProcedureName(",");
        labResultEntry.setProcedureType(",");
        labResultEntry.setProcessed(",");
        labResultEntry.setProviderServiceMapID(1);
        labResultEntry.setRemarks(",");
        labResultEntry.setReservedForChange(",");
        labResultEntry.setStripsNotAvailable(true);
        labResultEntry.setSyncedBy(",");
        labResultEntry.setSyncedDate(mock(Timestamp.class));
        labResultEntry.setTestComponentID(1);
        labResultEntry.setTestComponentMaster(testComponentMaster);
        labResultEntry.setTestReportFilePath("/directory/foo.txt");
        labResultEntry.setTestResultUnit(",");
        labResultEntry.setTestResultValue("42");
        labResultEntry.setVanID(1);
        labResultEntry.setVanSerialNo(1L);
        labResultEntry.setVehicalNo(",");
        labResultEntry.setVisitCode(1L);

        ArrayList<LabResultEntry> labResultEntryList = new ArrayList<>();
        labResultEntryList.add(labResultEntry);
        when(labResultEntryRepo.saveAll(Mockito.<Iterable<LabResultEntry>>any())).thenReturn(labResultEntryList);

        ProcedureData procedureData2 = new ProcedureData();
        procedureData2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        procedureData2.setCreatedDate(mock(Timestamp.class));
        procedureData2.setDeleted(true);
        procedureData2.setGender("Gender");
        procedureData2.setLabResultEntry(new HashSet<>());
        procedureData2.setLastModDate(mock(Timestamp.class));
        procedureData2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        procedureData2.setProcedureDesc("Procedure Desc");
        procedureData2.setProcedureID(1);
        procedureData2.setProcedureName("Procedure Name");
        procedureData2.setProcedureType("Procedure Type");
        procedureData2.setProcessed("Processed");
        procedureData2.setProviderServiceMapID(1);

        TestComponentMaster testComponentMaster2 = new TestComponentMaster();
        testComponentMaster2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        testComponentMaster2.setCreatedDate(mock(Timestamp.class));
        testComponentMaster2.setDeleted(true);
        testComponentMaster2.setInputType("Input Type");
        testComponentMaster2.setLabResultEntry(new HashSet<>());
        testComponentMaster2.setLastModDate(mock(Timestamp.class));
        testComponentMaster2.setLionicNum("Lionic Num");
        testComponentMaster2.setLionicTerm("Lionic Term");
        testComponentMaster2.setMeasurementUnit("Measurement Unit");
        testComponentMaster2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        testComponentMaster2.setProcessed("Processed");
        testComponentMaster2.setProviderServiceMapID(1);
        testComponentMaster2.setRange_max(3);
        testComponentMaster2.setRange_min(1);
        testComponentMaster2.setRange_normal_max(3);
        testComponentMaster2.setRange_normal_min(1);
        testComponentMaster2.setTestComponentDesc("Test Component Desc");
        testComponentMaster2.setTestComponentID(1);
        testComponentMaster2.setTestComponentName("Test Component Name");

        LabResultEntry labResultEntry2 = new LabResultEntry();
        labResultEntry2.setBenVisitID(1L);
        labResultEntry2.setBeneficiaryRegID(1L);
        labResultEntry2.setCompList(new ArrayList<>());
        labResultEntry2.setComponentList(new ArrayList<>());
        labResultEntry2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        labResultEntry2.setCreatedDate(mock(Timestamp.class));
        labResultEntry2.setDate(mock(Date.class));
        labResultEntry2.setDeleted(true);
        labResultEntry2.setFileIDs(new Integer[]{1});
        labResultEntry2.setID(1L);
        labResultEntry2.setLabCompleted(true);
        labResultEntry2.setLastModDate(mock(Timestamp.class));
        labResultEntry2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        labResultEntry2.setParkingPlaceID(1);
        labResultEntry2.setPrescriptionID(1L);
        labResultEntry2.setProcedureData(procedureData2);
        labResultEntry2.setProcedureID(1);
        labResultEntry2.setProcedureName("Procedure Name");
        labResultEntry2.setProcedureType("Procedure Type");
        labResultEntry2.setProcessed("Processed");
        labResultEntry2.setProviderServiceMapID(1);
        labResultEntry2.setRemarks("Remarks");
        labResultEntry2.setReservedForChange("Reserved For Change");
        labResultEntry2.setStripsNotAvailable(true);
        labResultEntry2.setSyncedBy("Synced By");
        labResultEntry2.setSyncedDate(mock(Timestamp.class));
        labResultEntry2.setTestComponentID(1);
        labResultEntry2.setTestComponentMaster(testComponentMaster2);
        labResultEntry2.setTestReportFilePath("/directory/foo.txt");
        labResultEntry2.setTestResultUnit("Test Result Unit");
        labResultEntry2.setTestResultValue("42");
        labResultEntry2.setVanID(1);
        labResultEntry2.setVanSerialNo(1L);
        labResultEntry2.setVehicalNo("Vehical No");
        labResultEntry2.setVisitCode(1L);

        ArrayList<LabResultEntry> labResultEntryList2 = new ArrayList<>();
        labResultEntryList2.add(labResultEntry2);
        WrapperLabResultEntry wrapperLabResults = mock(WrapperLabResultEntry.class);
        when(wrapperLabResults.getParkingPlaceID()).thenReturn(1);
        when(wrapperLabResults.getProviderServiceMapID()).thenReturn(1);
        when(wrapperLabResults.getVanID()).thenReturn(1);
        when(wrapperLabResults.getBeneficiaryRegID()).thenReturn(1L);
        when(wrapperLabResults.getVisitCode()).thenReturn(1L);
        when(wrapperLabResults.getVisitID()).thenReturn(1L);
        when(wrapperLabResults.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(wrapperLabResults.getLabTestResults()).thenReturn(new ArrayList<>());
        when(wrapperLabResults.getRadiologyTestResults()).thenReturn(labResultEntryList2);
        doNothing().when(wrapperLabResults).setBenFlowID(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setCreatedBy(Mockito.<String>any());
        doNothing().when(wrapperLabResults).setDoctorFlag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setLabCompleted(Mockito.<Boolean>any());
        doNothing().when(wrapperLabResults).setLabTestResults(Mockito.<List<LabResultEntry>>any());
        doNothing().when(wrapperLabResults).setNurseFlag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setRadiologyTestResults(Mockito.<List<LabResultEntry>>any());
        doNothing().when(wrapperLabResults).setSpecialist_flag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setVanID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setVisitCode(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setVisitID(Mockito.<Long>any());
        wrapperLabResults.setBenFlowID(1L);
        wrapperLabResults.setBeneficiaryRegID(1L);
        wrapperLabResults.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperLabResults.setDoctorFlag((short) 1);
        wrapperLabResults.setLabCompleted(true);
        wrapperLabResults.setLabTestResults(new ArrayList<>());
        wrapperLabResults.setNurseFlag((short) 1);
        wrapperLabResults.setParkingPlaceID(1);
        wrapperLabResults.setProviderServiceMapID(1);
        wrapperLabResults.setRadiologyTestResults(new ArrayList<>());
        wrapperLabResults.setSpecialist_flag((short) 1);
        wrapperLabResults.setVanID(1);
        wrapperLabResults.setVisitCode(1L);
        wrapperLabResults.setVisitID(1L);

        // Act
        Integer actualSaveLabTestResultResult = labTechnicianServiceImpl.saveLabTestResult(wrapperLabResults);

        // Assert
        verify(wrapperLabResults).getBeneficiaryRegID();
        verify(wrapperLabResults).getCreatedBy();
        verify(wrapperLabResults).getLabTestResults();
        verify(wrapperLabResults).getParkingPlaceID();
        verify(wrapperLabResults).getProviderServiceMapID();
        verify(wrapperLabResults, atLeast(1)).getRadiologyTestResults();
        verify(wrapperLabResults).getVanID();
        verify(wrapperLabResults).getVisitCode();
        verify(wrapperLabResults).getVisitID();
        verify(wrapperLabResults).setBenFlowID(isA(Long.class));
        verify(wrapperLabResults).setBeneficiaryRegID(isA(Long.class));
        verify(wrapperLabResults).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(wrapperLabResults).setDoctorFlag(isA(Short.class));
        verify(wrapperLabResults).setLabCompleted(isA(Boolean.class));
        verify(wrapperLabResults).setLabTestResults(isA(List.class));
        verify(wrapperLabResults).setNurseFlag(isA(Short.class));
        verify(wrapperLabResults).setParkingPlaceID(isA(Integer.class));
        verify(wrapperLabResults).setProviderServiceMapID(isA(Integer.class));
        verify(wrapperLabResults).setRadiologyTestResults(isA(List.class));
        verify(wrapperLabResults).setSpecialist_flag(isA(Short.class));
        verify(wrapperLabResults).setVanID(isA(Integer.class));
        verify(wrapperLabResults).setVisitCode(isA(Long.class));
        verify(wrapperLabResults).setVisitID(isA(Long.class));
        verify(labResultEntryRepo).saveAll(isA(Iterable.class));
        assertEquals(1, actualSaveLabTestResultResult.intValue());
    }

    @Test
    void testSaveLabTestResult9() {
        // Arrange
        when(labResultEntryRepo.saveAll(Mockito.<Iterable<LabResultEntry>>any())).thenReturn(new ArrayList<>());

        ProcedureData procedureData = new ProcedureData();
        procedureData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        procedureData.setCreatedDate(mock(Timestamp.class));
        procedureData.setDeleted(true);
        procedureData.setGender("Gender");
        procedureData.setLabResultEntry(new HashSet<>());
        procedureData.setLastModDate(mock(Timestamp.class));
        procedureData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        procedureData.setProcedureDesc("Procedure Desc");
        procedureData.setProcedureID(1);
        procedureData.setProcedureName("Procedure Name");
        procedureData.setProcedureType("Procedure Type");
        procedureData.setProcessed("Processed");
        procedureData.setProviderServiceMapID(1);

        TestComponentMaster testComponentMaster = new TestComponentMaster();
        testComponentMaster.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        testComponentMaster.setCreatedDate(mock(Timestamp.class));
        testComponentMaster.setDeleted(true);
        testComponentMaster.setInputType("Input Type");
        testComponentMaster.setLabResultEntry(new HashSet<>());
        testComponentMaster.setLastModDate(mock(Timestamp.class));
        testComponentMaster.setLionicNum("Lionic Num");
        testComponentMaster.setLionicTerm("Lionic Term");
        testComponentMaster.setMeasurementUnit("Measurement Unit");
        testComponentMaster.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        testComponentMaster.setProcessed("Processed");
        testComponentMaster.setProviderServiceMapID(1);
        testComponentMaster.setRange_max(3);
        testComponentMaster.setRange_min(1);
        testComponentMaster.setRange_normal_max(3);
        testComponentMaster.setRange_normal_min(1);
        testComponentMaster.setTestComponentDesc("Test Component Desc");
        testComponentMaster.setTestComponentID(1);
        testComponentMaster.setTestComponentName("Test Component Name");
        LabResultEntry labResultEntry = mock(LabResultEntry.class);
        when(labResultEntry.getFileIDs()).thenReturn(new Integer[]{1});
        doNothing().when(labResultEntry).setBenVisitID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setCompList(Mockito.<List<Map<String, String>>>any());
        doNothing().when(labResultEntry).setComponentList(Mockito.<ArrayList<Map<String, Object>>>any());
        doNothing().when(labResultEntry).setCreatedBy(Mockito.<String>any());
        doNothing().when(labResultEntry).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(labResultEntry).setDate(Mockito.<Date>any());
        doNothing().when(labResultEntry).setDeleted(Mockito.<Boolean>any());
        doNothing().when(labResultEntry).setFileIDs(Mockito.<Integer[]>any());
        doNothing().when(labResultEntry).setID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setLabCompleted(Mockito.<Boolean>any());
        doNothing().when(labResultEntry).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(labResultEntry).setModifiedBy(Mockito.<String>any());
        doNothing().when(labResultEntry).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setPrescriptionID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setProcedureData(Mockito.<ProcedureData>any());
        doNothing().when(labResultEntry).setProcedureID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setProcedureName(Mockito.<String>any());
        doNothing().when(labResultEntry).setProcedureType(Mockito.<String>any());
        doNothing().when(labResultEntry).setProcessed(Mockito.<String>any());
        doNothing().when(labResultEntry).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setRemarks(Mockito.<String>any());
        doNothing().when(labResultEntry).setReservedForChange(Mockito.<String>any());
        doNothing().when(labResultEntry).setStripsNotAvailable(Mockito.<Boolean>any());
        doNothing().when(labResultEntry).setSyncedBy(Mockito.<String>any());
        doNothing().when(labResultEntry).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(labResultEntry).setTestComponentID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setTestComponentMaster(Mockito.<TestComponentMaster>any());
        doNothing().when(labResultEntry).setTestReportFilePath(Mockito.<String>any());
        doNothing().when(labResultEntry).setTestResultUnit(Mockito.<String>any());
        doNothing().when(labResultEntry).setTestResultValue(Mockito.<String>any());
        doNothing().when(labResultEntry).setVanID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(labResultEntry).setVehicalNo(Mockito.<String>any());
        doNothing().when(labResultEntry).setVisitCode(Mockito.<Long>any());
        labResultEntry.setBenVisitID(1L);
        labResultEntry.setBeneficiaryRegID(1L);
        labResultEntry.setCompList(new ArrayList<>());
        labResultEntry.setComponentList(new ArrayList<>());
        labResultEntry.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        labResultEntry.setCreatedDate(mock(Timestamp.class));
        labResultEntry.setDate(mock(Date.class));
        labResultEntry.setDeleted(true);
        labResultEntry.setFileIDs(new Integer[]{1});
        labResultEntry.setID(1L);
        labResultEntry.setLabCompleted(true);
        labResultEntry.setLastModDate(mock(Timestamp.class));
        labResultEntry.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        labResultEntry.setParkingPlaceID(1);
        labResultEntry.setPrescriptionID(1L);
        labResultEntry.setProcedureData(procedureData);
        labResultEntry.setProcedureID(1);
        labResultEntry.setProcedureName("Procedure Name");
        labResultEntry.setProcedureType("Procedure Type");
        labResultEntry.setProcessed("Processed");
        labResultEntry.setProviderServiceMapID(1);
        labResultEntry.setRemarks("Remarks");
        labResultEntry.setReservedForChange("Reserved For Change");
        labResultEntry.setStripsNotAvailable(true);
        labResultEntry.setSyncedBy("Synced By");
        labResultEntry.setSyncedDate(mock(Timestamp.class));
        labResultEntry.setTestComponentID(1);
        labResultEntry.setTestComponentMaster(testComponentMaster);
        labResultEntry.setTestReportFilePath("/directory/foo.txt");
        labResultEntry.setTestResultUnit("Test Result Unit");
        labResultEntry.setTestResultValue("42");
        labResultEntry.setVanID(1);
        labResultEntry.setVanSerialNo(1L);
        labResultEntry.setVehicalNo("Vehical No");
        labResultEntry.setVisitCode(1L);

        ArrayList<LabResultEntry> labResultEntryList = new ArrayList<>();
        labResultEntryList.add(labResultEntry);
        WrapperLabResultEntry wrapperLabResults = mock(WrapperLabResultEntry.class);
        when(wrapperLabResults.getParkingPlaceID()).thenReturn(1);
        when(wrapperLabResults.getProviderServiceMapID()).thenReturn(1);
        when(wrapperLabResults.getVanID()).thenReturn(1);
        when(wrapperLabResults.getBeneficiaryRegID()).thenReturn(1L);
        when(wrapperLabResults.getVisitCode()).thenReturn(1L);
        when(wrapperLabResults.getVisitID()).thenReturn(1L);
        when(wrapperLabResults.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(wrapperLabResults.getLabTestResults()).thenReturn(new ArrayList<>());
        when(wrapperLabResults.getRadiologyTestResults()).thenReturn(labResultEntryList);
        doNothing().when(wrapperLabResults).setBenFlowID(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setCreatedBy(Mockito.<String>any());
        doNothing().when(wrapperLabResults).setDoctorFlag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setLabCompleted(Mockito.<Boolean>any());
        doNothing().when(wrapperLabResults).setLabTestResults(Mockito.<List<LabResultEntry>>any());
        doNothing().when(wrapperLabResults).setNurseFlag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setRadiologyTestResults(Mockito.<List<LabResultEntry>>any());
        doNothing().when(wrapperLabResults).setSpecialist_flag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setVanID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setVisitCode(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setVisitID(Mockito.<Long>any());
        wrapperLabResults.setBenFlowID(1L);
        wrapperLabResults.setBeneficiaryRegID(1L);
        wrapperLabResults.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperLabResults.setDoctorFlag((short) 1);
        wrapperLabResults.setLabCompleted(true);
        wrapperLabResults.setLabTestResults(new ArrayList<>());
        wrapperLabResults.setNurseFlag((short) 1);
        wrapperLabResults.setParkingPlaceID(1);
        wrapperLabResults.setProviderServiceMapID(1);
        wrapperLabResults.setRadiologyTestResults(new ArrayList<>());
        wrapperLabResults.setSpecialist_flag((short) 1);
        wrapperLabResults.setVanID(1);
        wrapperLabResults.setVisitCode(1L);
        wrapperLabResults.setVisitID(1L);

        // Act
        Integer actualSaveLabTestResultResult = labTechnicianServiceImpl.saveLabTestResult(wrapperLabResults);

        // Assert
        verify(labResultEntry).getFileIDs();
        verify(labResultEntry, atLeast(1)).setBenVisitID(isA(Long.class));
        verify(labResultEntry, atLeast(1)).setBeneficiaryRegID(isA(Long.class));
        verify(labResultEntry).setCompList(isA(List.class));
        verify(labResultEntry).setComponentList(isA(ArrayList.class));
        verify(labResultEntry, atLeast(1)).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(labResultEntry).setCreatedDate(isA(Timestamp.class));
        verify(labResultEntry).setDate(isA(Date.class));
        verify(labResultEntry).setDeleted(isA(Boolean.class));
        verify(labResultEntry).setFileIDs(isA(Integer[].class));
        verify(labResultEntry).setID(isA(Long.class));
        verify(labResultEntry).setLabCompleted(isA(Boolean.class));
        verify(labResultEntry).setLastModDate(isA(Timestamp.class));
        verify(labResultEntry).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(labResultEntry, atLeast(1)).setParkingPlaceID(isA(Integer.class));
        verify(labResultEntry).setPrescriptionID(isA(Long.class));
        verify(labResultEntry).setProcedureData(isA(ProcedureData.class));
        verify(labResultEntry).setProcedureID(isA(Integer.class));
        verify(labResultEntry).setProcedureName(eq("Procedure Name"));
        verify(labResultEntry).setProcedureType(eq("Procedure Type"));
        verify(labResultEntry).setProcessed(eq("Processed"));
        verify(labResultEntry, atLeast(1)).setProviderServiceMapID(isA(Integer.class));
        verify(labResultEntry).setRemarks(eq("Remarks"));
        verify(labResultEntry).setReservedForChange(eq("Reserved For Change"));
        verify(labResultEntry).setStripsNotAvailable(isA(Boolean.class));
        verify(labResultEntry).setSyncedBy(eq("Synced By"));
        verify(labResultEntry).setSyncedDate(isA(Timestamp.class));
        verify(labResultEntry).setTestComponentID(isA(Integer.class));
        verify(labResultEntry).setTestComponentMaster(isA(TestComponentMaster.class));
        verify(labResultEntry, atLeast(1)).setTestReportFilePath(Mockito.<String>any());
        verify(labResultEntry).setTestResultUnit(eq("Test Result Unit"));
        verify(labResultEntry).setTestResultValue(eq("42"));
        verify(labResultEntry, atLeast(1)).setVanID(isA(Integer.class));
        verify(labResultEntry).setVanSerialNo(isA(Long.class));
        verify(labResultEntry).setVehicalNo(eq("Vehical No"));
        verify(labResultEntry, atLeast(1)).setVisitCode(isA(Long.class));
        verify(wrapperLabResults).getBeneficiaryRegID();
        verify(wrapperLabResults).getCreatedBy();
        verify(wrapperLabResults).getLabTestResults();
        verify(wrapperLabResults).getParkingPlaceID();
        verify(wrapperLabResults).getProviderServiceMapID();
        verify(wrapperLabResults, atLeast(1)).getRadiologyTestResults();
        verify(wrapperLabResults).getVanID();
        verify(wrapperLabResults).getVisitCode();
        verify(wrapperLabResults).getVisitID();
        verify(wrapperLabResults).setBenFlowID(isA(Long.class));
        verify(wrapperLabResults).setBeneficiaryRegID(isA(Long.class));
        verify(wrapperLabResults).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(wrapperLabResults).setDoctorFlag(isA(Short.class));
        verify(wrapperLabResults).setLabCompleted(isA(Boolean.class));
        verify(wrapperLabResults).setLabTestResults(isA(List.class));
        verify(wrapperLabResults).setNurseFlag(isA(Short.class));
        verify(wrapperLabResults).setParkingPlaceID(isA(Integer.class));
        verify(wrapperLabResults).setProviderServiceMapID(isA(Integer.class));
        verify(wrapperLabResults).setRadiologyTestResults(isA(List.class));
        verify(wrapperLabResults).setSpecialist_flag(isA(Short.class));
        verify(wrapperLabResults).setVanID(isA(Integer.class));
        verify(wrapperLabResults).setVisitCode(isA(Long.class));
        verify(wrapperLabResults).setVisitID(isA(Long.class));
        verify(labResultEntryRepo).saveAll(isA(Iterable.class));
        assertNull(actualSaveLabTestResultResult);
    }

    @Test
    void testSaveLabTestResult10() {
        // Arrange
        when(labResultEntryRepo.saveAll(Mockito.<Iterable<LabResultEntry>>any())).thenReturn(new ArrayList<>());

        ProcedureData procedureData = new ProcedureData();
        procedureData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        procedureData.setCreatedDate(mock(Timestamp.class));
        procedureData.setDeleted(true);
        procedureData.setGender("Gender");
        procedureData.setLabResultEntry(new HashSet<>());
        procedureData.setLastModDate(mock(Timestamp.class));
        procedureData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        procedureData.setProcedureDesc("Procedure Desc");
        procedureData.setProcedureID(1);
        procedureData.setProcedureName("Procedure Name");
        procedureData.setProcedureType("Procedure Type");
        procedureData.setProcessed("Processed");
        procedureData.setProviderServiceMapID(1);

        TestComponentMaster testComponentMaster = new TestComponentMaster();
        testComponentMaster.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        testComponentMaster.setCreatedDate(mock(Timestamp.class));
        testComponentMaster.setDeleted(true);
        testComponentMaster.setInputType("Input Type");
        testComponentMaster.setLabResultEntry(new HashSet<>());
        testComponentMaster.setLastModDate(mock(Timestamp.class));
        testComponentMaster.setLionicNum("Lionic Num");
        testComponentMaster.setLionicTerm("Lionic Term");
        testComponentMaster.setMeasurementUnit("Measurement Unit");
        testComponentMaster.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        testComponentMaster.setProcessed("Processed");
        testComponentMaster.setProviderServiceMapID(1);
        testComponentMaster.setRange_max(3);
        testComponentMaster.setRange_min(1);
        testComponentMaster.setRange_normal_max(3);
        testComponentMaster.setRange_normal_min(1);
        testComponentMaster.setTestComponentDesc("Test Component Desc");
        testComponentMaster.setTestComponentID(1);
        testComponentMaster.setTestComponentName("Test Component Name");
        LabResultEntry labResultEntry = mock(LabResultEntry.class);
        when(labResultEntry.getFileIDs()).thenReturn(null);
        doNothing().when(labResultEntry).setBenVisitID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setCompList(Mockito.<List<Map<String, String>>>any());
        doNothing().when(labResultEntry).setComponentList(Mockito.<ArrayList<Map<String, Object>>>any());
        doNothing().when(labResultEntry).setCreatedBy(Mockito.<String>any());
        doNothing().when(labResultEntry).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(labResultEntry).setDate(Mockito.<Date>any());
        doNothing().when(labResultEntry).setDeleted(Mockito.<Boolean>any());
        doNothing().when(labResultEntry).setFileIDs(Mockito.<Integer[]>any());
        doNothing().when(labResultEntry).setID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setLabCompleted(Mockito.<Boolean>any());
        doNothing().when(labResultEntry).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(labResultEntry).setModifiedBy(Mockito.<String>any());
        doNothing().when(labResultEntry).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setPrescriptionID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setProcedureData(Mockito.<ProcedureData>any());
        doNothing().when(labResultEntry).setProcedureID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setProcedureName(Mockito.<String>any());
        doNothing().when(labResultEntry).setProcedureType(Mockito.<String>any());
        doNothing().when(labResultEntry).setProcessed(Mockito.<String>any());
        doNothing().when(labResultEntry).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setRemarks(Mockito.<String>any());
        doNothing().when(labResultEntry).setReservedForChange(Mockito.<String>any());
        doNothing().when(labResultEntry).setStripsNotAvailable(Mockito.<Boolean>any());
        doNothing().when(labResultEntry).setSyncedBy(Mockito.<String>any());
        doNothing().when(labResultEntry).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(labResultEntry).setTestComponentID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setTestComponentMaster(Mockito.<TestComponentMaster>any());
        doNothing().when(labResultEntry).setTestReportFilePath(Mockito.<String>any());
        doNothing().when(labResultEntry).setTestResultUnit(Mockito.<String>any());
        doNothing().when(labResultEntry).setTestResultValue(Mockito.<String>any());
        doNothing().when(labResultEntry).setVanID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(labResultEntry).setVehicalNo(Mockito.<String>any());
        doNothing().when(labResultEntry).setVisitCode(Mockito.<Long>any());
        labResultEntry.setBenVisitID(1L);
        labResultEntry.setBeneficiaryRegID(1L);
        labResultEntry.setCompList(new ArrayList<>());
        labResultEntry.setComponentList(new ArrayList<>());
        labResultEntry.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        labResultEntry.setCreatedDate(mock(Timestamp.class));
        labResultEntry.setDate(mock(Date.class));
        labResultEntry.setDeleted(true);
        labResultEntry.setFileIDs(new Integer[]{1});
        labResultEntry.setID(1L);
        labResultEntry.setLabCompleted(true);
        labResultEntry.setLastModDate(mock(Timestamp.class));
        labResultEntry.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        labResultEntry.setParkingPlaceID(1);
        labResultEntry.setPrescriptionID(1L);
        labResultEntry.setProcedureData(procedureData);
        labResultEntry.setProcedureID(1);
        labResultEntry.setProcedureName("Procedure Name");
        labResultEntry.setProcedureType("Procedure Type");
        labResultEntry.setProcessed("Processed");
        labResultEntry.setProviderServiceMapID(1);
        labResultEntry.setRemarks("Remarks");
        labResultEntry.setReservedForChange("Reserved For Change");
        labResultEntry.setStripsNotAvailable(true);
        labResultEntry.setSyncedBy("Synced By");
        labResultEntry.setSyncedDate(mock(Timestamp.class));
        labResultEntry.setTestComponentID(1);
        labResultEntry.setTestComponentMaster(testComponentMaster);
        labResultEntry.setTestReportFilePath("/directory/foo.txt");
        labResultEntry.setTestResultUnit("Test Result Unit");
        labResultEntry.setTestResultValue("42");
        labResultEntry.setVanID(1);
        labResultEntry.setVanSerialNo(1L);
        labResultEntry.setVehicalNo("Vehical No");
        labResultEntry.setVisitCode(1L);

        ArrayList<LabResultEntry> labResultEntryList = new ArrayList<>();
        labResultEntryList.add(labResultEntry);
        WrapperLabResultEntry wrapperLabResults = mock(WrapperLabResultEntry.class);
        when(wrapperLabResults.getParkingPlaceID()).thenReturn(1);
        when(wrapperLabResults.getProviderServiceMapID()).thenReturn(1);
        when(wrapperLabResults.getVanID()).thenReturn(1);
        when(wrapperLabResults.getBeneficiaryRegID()).thenReturn(1L);
        when(wrapperLabResults.getVisitCode()).thenReturn(1L);
        when(wrapperLabResults.getVisitID()).thenReturn(1L);
        when(wrapperLabResults.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(wrapperLabResults.getLabTestResults()).thenReturn(new ArrayList<>());
        when(wrapperLabResults.getRadiologyTestResults()).thenReturn(labResultEntryList);
        doNothing().when(wrapperLabResults).setBenFlowID(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setCreatedBy(Mockito.<String>any());
        doNothing().when(wrapperLabResults).setDoctorFlag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setLabCompleted(Mockito.<Boolean>any());
        doNothing().when(wrapperLabResults).setLabTestResults(Mockito.<List<LabResultEntry>>any());
        doNothing().when(wrapperLabResults).setNurseFlag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setRadiologyTestResults(Mockito.<List<LabResultEntry>>any());
        doNothing().when(wrapperLabResults).setSpecialist_flag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setVanID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setVisitCode(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setVisitID(Mockito.<Long>any());
        wrapperLabResults.setBenFlowID(1L);
        wrapperLabResults.setBeneficiaryRegID(1L);
        wrapperLabResults.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperLabResults.setDoctorFlag((short) 1);
        wrapperLabResults.setLabCompleted(true);
        wrapperLabResults.setLabTestResults(new ArrayList<>());
        wrapperLabResults.setNurseFlag((short) 1);
        wrapperLabResults.setParkingPlaceID(1);
        wrapperLabResults.setProviderServiceMapID(1);
        wrapperLabResults.setRadiologyTestResults(new ArrayList<>());
        wrapperLabResults.setSpecialist_flag((short) 1);
        wrapperLabResults.setVanID(1);
        wrapperLabResults.setVisitCode(1L);
        wrapperLabResults.setVisitID(1L);

        // Act
        Integer actualSaveLabTestResultResult = labTechnicianServiceImpl.saveLabTestResult(wrapperLabResults);

        // Assert
        verify(labResultEntry).getFileIDs();
        verify(labResultEntry, atLeast(1)).setBenVisitID(isA(Long.class));
        verify(labResultEntry, atLeast(1)).setBeneficiaryRegID(isA(Long.class));
        verify(labResultEntry).setCompList(isA(List.class));
        verify(labResultEntry).setComponentList(isA(ArrayList.class));
        verify(labResultEntry, atLeast(1)).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(labResultEntry).setCreatedDate(isA(Timestamp.class));
        verify(labResultEntry).setDate(isA(Date.class));
        verify(labResultEntry).setDeleted(isA(Boolean.class));
        verify(labResultEntry).setFileIDs(isA(Integer[].class));
        verify(labResultEntry).setID(isA(Long.class));
        verify(labResultEntry).setLabCompleted(isA(Boolean.class));
        verify(labResultEntry).setLastModDate(isA(Timestamp.class));
        verify(labResultEntry).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(labResultEntry, atLeast(1)).setParkingPlaceID(isA(Integer.class));
        verify(labResultEntry).setPrescriptionID(isA(Long.class));
        verify(labResultEntry).setProcedureData(isA(ProcedureData.class));
        verify(labResultEntry).setProcedureID(isA(Integer.class));
        verify(labResultEntry).setProcedureName(eq("Procedure Name"));
        verify(labResultEntry).setProcedureType(eq("Procedure Type"));
        verify(labResultEntry).setProcessed(eq("Processed"));
        verify(labResultEntry, atLeast(1)).setProviderServiceMapID(isA(Integer.class));
        verify(labResultEntry).setRemarks(eq("Remarks"));
        verify(labResultEntry).setReservedForChange(eq("Reserved For Change"));
        verify(labResultEntry).setStripsNotAvailable(isA(Boolean.class));
        verify(labResultEntry).setSyncedBy(eq("Synced By"));
        verify(labResultEntry).setSyncedDate(isA(Timestamp.class));
        verify(labResultEntry).setTestComponentID(isA(Integer.class));
        verify(labResultEntry).setTestComponentMaster(isA(TestComponentMaster.class));
        verify(labResultEntry, atLeast(1)).setTestReportFilePath(Mockito.<String>any());
        verify(labResultEntry).setTestResultUnit(eq("Test Result Unit"));
        verify(labResultEntry).setTestResultValue(eq("42"));
        verify(labResultEntry, atLeast(1)).setVanID(isA(Integer.class));
        verify(labResultEntry).setVanSerialNo(isA(Long.class));
        verify(labResultEntry).setVehicalNo(eq("Vehical No"));
        verify(labResultEntry, atLeast(1)).setVisitCode(isA(Long.class));
        verify(wrapperLabResults).getBeneficiaryRegID();
        verify(wrapperLabResults).getCreatedBy();
        verify(wrapperLabResults).getLabTestResults();
        verify(wrapperLabResults).getParkingPlaceID();
        verify(wrapperLabResults).getProviderServiceMapID();
        verify(wrapperLabResults, atLeast(1)).getRadiologyTestResults();
        verify(wrapperLabResults).getVanID();
        verify(wrapperLabResults).getVisitCode();
        verify(wrapperLabResults).getVisitID();
        verify(wrapperLabResults).setBenFlowID(isA(Long.class));
        verify(wrapperLabResults).setBeneficiaryRegID(isA(Long.class));
        verify(wrapperLabResults).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(wrapperLabResults).setDoctorFlag(isA(Short.class));
        verify(wrapperLabResults).setLabCompleted(isA(Boolean.class));
        verify(wrapperLabResults).setLabTestResults(isA(List.class));
        verify(wrapperLabResults).setNurseFlag(isA(Short.class));
        verify(wrapperLabResults).setParkingPlaceID(isA(Integer.class));
        verify(wrapperLabResults).setProviderServiceMapID(isA(Integer.class));
        verify(wrapperLabResults).setRadiologyTestResults(isA(List.class));
        verify(wrapperLabResults).setSpecialist_flag(isA(Short.class));
        verify(wrapperLabResults).setVanID(isA(Integer.class));
        verify(wrapperLabResults).setVisitCode(isA(Long.class));
        verify(wrapperLabResults).setVisitID(isA(Long.class));
        verify(labResultEntryRepo).saveAll(isA(Iterable.class));
        assertNull(actualSaveLabTestResultResult);
    }

    @Test
    void testSaveLabTestResult11() {
        // Arrange
        when(labResultEntryRepo.saveAll(Mockito.<Iterable<LabResultEntry>>any())).thenReturn(new ArrayList<>());

        ProcedureData procedureData = new ProcedureData();
        procedureData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        procedureData.setCreatedDate(mock(Timestamp.class));
        procedureData.setDeleted(true);
        procedureData.setGender("Gender");
        procedureData.setLabResultEntry(new HashSet<>());
        procedureData.setLastModDate(mock(Timestamp.class));
        procedureData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        procedureData.setProcedureDesc("Procedure Desc");
        procedureData.setProcedureID(1);
        procedureData.setProcedureName("Procedure Name");
        procedureData.setProcedureType("Procedure Type");
        procedureData.setProcessed("Processed");
        procedureData.setProviderServiceMapID(1);

        TestComponentMaster testComponentMaster = new TestComponentMaster();
        testComponentMaster.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        testComponentMaster.setCreatedDate(mock(Timestamp.class));
        testComponentMaster.setDeleted(true);
        testComponentMaster.setInputType("Input Type");
        testComponentMaster.setLabResultEntry(new HashSet<>());
        testComponentMaster.setLastModDate(mock(Timestamp.class));
        testComponentMaster.setLionicNum("Lionic Num");
        testComponentMaster.setLionicTerm("Lionic Term");
        testComponentMaster.setMeasurementUnit("Measurement Unit");
        testComponentMaster.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        testComponentMaster.setProcessed("Processed");
        testComponentMaster.setProviderServiceMapID(1);
        testComponentMaster.setRange_max(3);
        testComponentMaster.setRange_min(1);
        testComponentMaster.setRange_normal_max(3);
        testComponentMaster.setRange_normal_min(1);
        testComponentMaster.setTestComponentDesc("Test Component Desc");
        testComponentMaster.setTestComponentID(1);
        testComponentMaster.setTestComponentName("Test Component Name");
        LabResultEntry labResultEntry = mock(LabResultEntry.class);
        when(labResultEntry.getFileIDs()).thenReturn(new Integer[]{});
        doNothing().when(labResultEntry).setBenVisitID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setCompList(Mockito.<List<Map<String, String>>>any());
        doNothing().when(labResultEntry).setComponentList(Mockito.<ArrayList<Map<String, Object>>>any());
        doNothing().when(labResultEntry).setCreatedBy(Mockito.<String>any());
        doNothing().when(labResultEntry).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(labResultEntry).setDate(Mockito.<Date>any());
        doNothing().when(labResultEntry).setDeleted(Mockito.<Boolean>any());
        doNothing().when(labResultEntry).setFileIDs(Mockito.<Integer[]>any());
        doNothing().when(labResultEntry).setID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setLabCompleted(Mockito.<Boolean>any());
        doNothing().when(labResultEntry).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(labResultEntry).setModifiedBy(Mockito.<String>any());
        doNothing().when(labResultEntry).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setPrescriptionID(Mockito.<Long>any());
        doNothing().when(labResultEntry).setProcedureData(Mockito.<ProcedureData>any());
        doNothing().when(labResultEntry).setProcedureID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setProcedureName(Mockito.<String>any());
        doNothing().when(labResultEntry).setProcedureType(Mockito.<String>any());
        doNothing().when(labResultEntry).setProcessed(Mockito.<String>any());
        doNothing().when(labResultEntry).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setRemarks(Mockito.<String>any());
        doNothing().when(labResultEntry).setReservedForChange(Mockito.<String>any());
        doNothing().when(labResultEntry).setStripsNotAvailable(Mockito.<Boolean>any());
        doNothing().when(labResultEntry).setSyncedBy(Mockito.<String>any());
        doNothing().when(labResultEntry).setSyncedDate(Mockito.<Timestamp>any());
        doNothing().when(labResultEntry).setTestComponentID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setTestComponentMaster(Mockito.<TestComponentMaster>any());
        doNothing().when(labResultEntry).setTestReportFilePath(Mockito.<String>any());
        doNothing().when(labResultEntry).setTestResultUnit(Mockito.<String>any());
        doNothing().when(labResultEntry).setTestResultValue(Mockito.<String>any());
        doNothing().when(labResultEntry).setVanID(Mockito.<Integer>any());
        doNothing().when(labResultEntry).setVanSerialNo(Mockito.<Long>any());
        doNothing().when(labResultEntry).setVehicalNo(Mockito.<String>any());
        doNothing().when(labResultEntry).setVisitCode(Mockito.<Long>any());
        labResultEntry.setBenVisitID(1L);
        labResultEntry.setBeneficiaryRegID(1L);
        labResultEntry.setCompList(new ArrayList<>());
        labResultEntry.setComponentList(new ArrayList<>());
        labResultEntry.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        labResultEntry.setCreatedDate(mock(Timestamp.class));
        labResultEntry.setDate(mock(Date.class));
        labResultEntry.setDeleted(true);
        labResultEntry.setFileIDs(new Integer[]{1});
        labResultEntry.setID(1L);
        labResultEntry.setLabCompleted(true);
        labResultEntry.setLastModDate(mock(Timestamp.class));
        labResultEntry.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        labResultEntry.setParkingPlaceID(1);
        labResultEntry.setPrescriptionID(1L);
        labResultEntry.setProcedureData(procedureData);
        labResultEntry.setProcedureID(1);
        labResultEntry.setProcedureName("Procedure Name");
        labResultEntry.setProcedureType("Procedure Type");
        labResultEntry.setProcessed("Processed");
        labResultEntry.setProviderServiceMapID(1);
        labResultEntry.setRemarks("Remarks");
        labResultEntry.setReservedForChange("Reserved For Change");
        labResultEntry.setStripsNotAvailable(true);
        labResultEntry.setSyncedBy("Synced By");
        labResultEntry.setSyncedDate(mock(Timestamp.class));
        labResultEntry.setTestComponentID(1);
        labResultEntry.setTestComponentMaster(testComponentMaster);
        labResultEntry.setTestReportFilePath("/directory/foo.txt");
        labResultEntry.setTestResultUnit("Test Result Unit");
        labResultEntry.setTestResultValue("42");
        labResultEntry.setVanID(1);
        labResultEntry.setVanSerialNo(1L);
        labResultEntry.setVehicalNo("Vehical No");
        labResultEntry.setVisitCode(1L);

        ArrayList<LabResultEntry> labResultEntryList = new ArrayList<>();
        labResultEntryList.add(labResultEntry);
        WrapperLabResultEntry wrapperLabResults = mock(WrapperLabResultEntry.class);
        when(wrapperLabResults.getParkingPlaceID()).thenReturn(1);
        when(wrapperLabResults.getProviderServiceMapID()).thenReturn(1);
        when(wrapperLabResults.getVanID()).thenReturn(1);
        when(wrapperLabResults.getBeneficiaryRegID()).thenReturn(1L);
        when(wrapperLabResults.getVisitCode()).thenReturn(1L);
        when(wrapperLabResults.getVisitID()).thenReturn(1L);
        when(wrapperLabResults.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(wrapperLabResults.getLabTestResults()).thenReturn(new ArrayList<>());
        when(wrapperLabResults.getRadiologyTestResults()).thenReturn(labResultEntryList);
        doNothing().when(wrapperLabResults).setBenFlowID(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setCreatedBy(Mockito.<String>any());
        doNothing().when(wrapperLabResults).setDoctorFlag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setLabCompleted(Mockito.<Boolean>any());
        doNothing().when(wrapperLabResults).setLabTestResults(Mockito.<List<LabResultEntry>>any());
        doNothing().when(wrapperLabResults).setNurseFlag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setParkingPlaceID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setProviderServiceMapID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setRadiologyTestResults(Mockito.<List<LabResultEntry>>any());
        doNothing().when(wrapperLabResults).setSpecialist_flag(Mockito.<Short>any());
        doNothing().when(wrapperLabResults).setVanID(Mockito.<Integer>any());
        doNothing().when(wrapperLabResults).setVisitCode(Mockito.<Long>any());
        doNothing().when(wrapperLabResults).setVisitID(Mockito.<Long>any());
        wrapperLabResults.setBenFlowID(1L);
        wrapperLabResults.setBeneficiaryRegID(1L);
        wrapperLabResults.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        wrapperLabResults.setDoctorFlag((short) 1);
        wrapperLabResults.setLabCompleted(true);
        wrapperLabResults.setLabTestResults(new ArrayList<>());
        wrapperLabResults.setNurseFlag((short) 1);
        wrapperLabResults.setParkingPlaceID(1);
        wrapperLabResults.setProviderServiceMapID(1);
        wrapperLabResults.setRadiologyTestResults(new ArrayList<>());
        wrapperLabResults.setSpecialist_flag((short) 1);
        wrapperLabResults.setVanID(1);
        wrapperLabResults.setVisitCode(1L);
        wrapperLabResults.setVisitID(1L);

        // Act
        Integer actualSaveLabTestResultResult = labTechnicianServiceImpl.saveLabTestResult(wrapperLabResults);

        // Assert
        verify(labResultEntry).getFileIDs();
        verify(labResultEntry, atLeast(1)).setBenVisitID(isA(Long.class));
        verify(labResultEntry, atLeast(1)).setBeneficiaryRegID(isA(Long.class));
        verify(labResultEntry).setCompList(isA(List.class));
        verify(labResultEntry).setComponentList(isA(ArrayList.class));
        verify(labResultEntry, atLeast(1)).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(labResultEntry).setCreatedDate(isA(Timestamp.class));
        verify(labResultEntry).setDate(isA(Date.class));
        verify(labResultEntry).setDeleted(isA(Boolean.class));
        verify(labResultEntry).setFileIDs(isA(Integer[].class));
        verify(labResultEntry).setID(isA(Long.class));
        verify(labResultEntry).setLabCompleted(isA(Boolean.class));
        verify(labResultEntry).setLastModDate(isA(Timestamp.class));
        verify(labResultEntry).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(labResultEntry, atLeast(1)).setParkingPlaceID(isA(Integer.class));
        verify(labResultEntry).setPrescriptionID(isA(Long.class));
        verify(labResultEntry).setProcedureData(isA(ProcedureData.class));
        verify(labResultEntry).setProcedureID(isA(Integer.class));
        verify(labResultEntry).setProcedureName(eq("Procedure Name"));
        verify(labResultEntry).setProcedureType(eq("Procedure Type"));
        verify(labResultEntry).setProcessed(eq("Processed"));
        verify(labResultEntry, atLeast(1)).setProviderServiceMapID(isA(Integer.class));
        verify(labResultEntry).setRemarks(eq("Remarks"));
        verify(labResultEntry).setReservedForChange(eq("Reserved For Change"));
        verify(labResultEntry).setStripsNotAvailable(isA(Boolean.class));
        verify(labResultEntry).setSyncedBy(eq("Synced By"));
        verify(labResultEntry).setSyncedDate(isA(Timestamp.class));
        verify(labResultEntry).setTestComponentID(isA(Integer.class));
        verify(labResultEntry).setTestComponentMaster(isA(TestComponentMaster.class));
        verify(labResultEntry, atLeast(1)).setTestReportFilePath(Mockito.<String>any());
        verify(labResultEntry).setTestResultUnit(eq("Test Result Unit"));
        verify(labResultEntry).setTestResultValue(eq("42"));
        verify(labResultEntry, atLeast(1)).setVanID(isA(Integer.class));
        verify(labResultEntry).setVanSerialNo(isA(Long.class));
        verify(labResultEntry).setVehicalNo(eq("Vehical No"));
        verify(labResultEntry, atLeast(1)).setVisitCode(isA(Long.class));
        verify(wrapperLabResults).getBeneficiaryRegID();
        verify(wrapperLabResults).getCreatedBy();
        verify(wrapperLabResults).getLabTestResults();
        verify(wrapperLabResults).getParkingPlaceID();
        verify(wrapperLabResults).getProviderServiceMapID();
        verify(wrapperLabResults, atLeast(1)).getRadiologyTestResults();
        verify(wrapperLabResults).getVanID();
        verify(wrapperLabResults).getVisitCode();
        verify(wrapperLabResults).getVisitID();
        verify(wrapperLabResults).setBenFlowID(isA(Long.class));
        verify(wrapperLabResults).setBeneficiaryRegID(isA(Long.class));
        verify(wrapperLabResults).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(wrapperLabResults).setDoctorFlag(isA(Short.class));
        verify(wrapperLabResults).setLabCompleted(isA(Boolean.class));
        verify(wrapperLabResults).setLabTestResults(isA(List.class));
        verify(wrapperLabResults).setNurseFlag(isA(Short.class));
        verify(wrapperLabResults).setParkingPlaceID(isA(Integer.class));
        verify(wrapperLabResults).setProviderServiceMapID(isA(Integer.class));
        verify(wrapperLabResults).setRadiologyTestResults(isA(List.class));
        verify(wrapperLabResults).setSpecialist_flag(isA(Short.class));
        verify(wrapperLabResults).setVanID(isA(Integer.class));
        verify(wrapperLabResults).setVisitCode(isA(Long.class));
        verify(wrapperLabResults).setVisitID(isA(Long.class));
        verify(labResultEntryRepo).saveAll(isA(Iterable.class));
        assertNull(actualSaveLabTestResultResult);
    }

    @Test
    void testGetLast_3_ArchivedTestVisitList() {
        // Arrange
        when(labResultEntryRepo.getLast_3_visitForLabTestDone(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualLast_3_ArchivedTestVisitList = labTechnicianServiceImpl.getLast_3_ArchivedTestVisitList(1L, 1L);

        // Assert
        verify(labResultEntryRepo).getLast_3_visitForLabTestDone(isA(Long.class), isA(Long.class));
        assertEquals("[]", actualLast_3_ArchivedTestVisitList);
    }

    @Test
    void testGetLabResultForVisitcode() {
        // Arrange
        when(labResultEntryRepo.findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        String actualLabResultForVisitcode = labTechnicianServiceImpl.getLabResultForVisitcode(1L, 1L);

        // Assert
        verify(labResultEntryRepo).findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(isA(Long.class),
                isA(Long.class));
        assertEquals("[]", actualLabResultForVisitcode);
    }

    @Test
    void testGettersAndSetters() {
        LabTechnicianServiceImpl labTechnicianServiceImpl = new LabTechnicianServiceImpl();

        // Act
        labTechnicianServiceImpl.setCommonBenStatusFlowServiceImpl(new CommonBenStatusFlowServiceImpl());
        labTechnicianServiceImpl.setLabResultEntryRepo(mock(LabResultEntryRepo.class));
        labTechnicianServiceImpl.setV_benLabTestOrderedDetailsRepo(mock(V_benLabTestOrderedDetailsRepo.class));
    }
}
