package com.iemr.hwc.service.uptsu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
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

import com.iemr.hwc.data.uptsu.AppointmentDetails;
import com.iemr.hwc.data.uptsu.Referred104Details;
import com.iemr.hwc.repo.uptsu.M_104ActionMasterRepo;
import com.iemr.hwc.repo.uptsu.UptsuRepository;

@ExtendWith(MockitoExtension.class)
class UptsuServiceImplTest {
    @Mock
    private M_104ActionMasterRepo m_104ActionMasterRepo;

    @Mock
    private UptsuRepository uptsuRepository;

    @InjectMocks
    private UptsuServiceImpl uptsuServiceImpl;

    @Test
    void testGetActionMaster() {
        when(m_104ActionMasterRepo.findByDeleted(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
        String actualActionMaster = uptsuServiceImpl.getActionMaster();
        verify(m_104ActionMasterRepo).findByDeleted(isA(Boolean.class));
        assertEquals("[]", actualActionMaster);
    }

    @Test
    void testGetWorklist() {
        when(uptsuRepository.getFacilityId(Mockito.<Integer>any())).thenReturn(1);
        when(uptsuRepository.getfacilityCode(Mockito.<Integer>any())).thenReturn("Getfacility Code");
        when(uptsuRepository.getAppointmentDetails(Mockito.<String>any(), Mockito.<Timestamp>any(),
                Mockito.<Timestamp>any())).thenReturn(new ArrayList<>());
        String actualWorklist = uptsuServiceImpl.getWorklist(1);
        verify(uptsuRepository).getAppointmentDetails(eq("Getfacility Code"), isA(Timestamp.class), isA(Timestamp.class));
        verify(uptsuRepository).getFacilityId(isA(Integer.class));
        verify(uptsuRepository).getfacilityCode(isA(Integer.class));
        assertEquals("[]", actualWorklist);
    }

    @Test
    void testGetWorklist2() {
        // Arrange
        AppointmentDetails appointmentDetails = new AppointmentDetails();
        appointmentDetails.setAlternateMobNo("yyyy-MM-dd HH:mm:ss");
        appointmentDetails.setAppointmentDate(mock(Timestamp.class));
        appointmentDetails.setBenCallId("42");
        appointmentDetails.setBenRegId("42");
        appointmentDetails.setBlockName("yyyy-MM-dd HH:mm:ss");
        appointmentDetails.setChoName("yyyy-MM-dd HH:mm:ss");
        appointmentDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        appointmentDetails.setCreatedDate(mock(Timestamp.class));
        appointmentDetails.setDeleted(true);
        appointmentDetails.setEmployeeCode("yyyy-MM-dd HH:mm:ss");
        appointmentDetails.setFacilityCode("yyyy-MM-dd HH:mm:ss");
        appointmentDetails.setFacilityName("yyyy-MM-dd HH:mm:ss");
        appointmentDetails.setFacilityPhoneNo("6625550144");
        appointmentDetails.setHfrId("42");
        appointmentDetails.setId(1);
        appointmentDetails.setLastModDate(mock(Timestamp.class));
        appointmentDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        appointmentDetails.setProcessed('\u0007');
        appointmentDetails.setProviderServiceMapID(1);
        appointmentDetails.setRefferedFlag(true);

        ArrayList<AppointmentDetails> appointmentDetailsList = new ArrayList<>();
        appointmentDetailsList.add(appointmentDetails);
        when(uptsuRepository.findBeneficiaryNameAndBeneficiaryIdByBenRegId(Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        when(uptsuRepository.getFacilityId(Mockito.<Integer>any())).thenReturn(1);
        when(uptsuRepository.getfacilityCode(Mockito.<Integer>any())).thenReturn("Getfacility Code");
        when(uptsuRepository.getAppointmentDetails(Mockito.<String>any(), Mockito.<Timestamp>any(),
                Mockito.<Timestamp>any())).thenReturn(appointmentDetailsList);
        String actualWorklist = uptsuServiceImpl.getWorklist(1);
        verify(uptsuRepository).findBeneficiaryNameAndBeneficiaryIdByBenRegId(eq("42"));
        verify(uptsuRepository).getAppointmentDetails(eq("Getfacility Code"), isA(Timestamp.class), isA(Timestamp.class));
        verify(uptsuRepository).getFacilityId(isA(Integer.class));
        verify(uptsuRepository).getfacilityCode(isA(Integer.class));
        assertEquals("[]", actualWorklist);
    }

    @Test
    void testSaveReferredDetails() {
        // Arrange
        Referred104Details referred104Details = new Referred104Details();
        referred104Details.setAction("Action");
        referred104Details.setActionId(1);
        referred104Details.setAlgorithm("Algorithm");
        referred104Details.setBenCallID("Ben Call ID");
        referred104Details.setBeneficiaryID(1L);
        referred104Details.setBeneficiaryRegID(1L);
        referred104Details.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        referred104Details.setId(1);
        referred104Details.setParkingPlaceID(1);
        referred104Details.setPatientAge("Patient Age");
        referred104Details.setPatientGenderID(1);
        referred104Details.setPatientName("Patient Name");
        referred104Details.setPresentChiefComplaint("Present Chief Complaint");
        referred104Details.setPresentChiefComplaintID("Present Chief Complaint ID");
        referred104Details.setProviderServiceMapID(1);
        referred104Details.setRecommendedAction("Recommended Action");
        referred104Details.setRemarks("Remarks");
        referred104Details.setSelecteDiagnosis("Selecte Diagnosis");
        referred104Details.setSelecteDiagnosisID("Selecte Diagnosis ID");
        referred104Details.setServiceID(1);
        referred104Details.setSessionID(1);
        referred104Details.setVanID(1);
        doNothing().when(uptsuRepository).updateReferredFlag(Mockito.<String>any());
        when(uptsuRepository.save(Mockito.<Referred104Details>any())).thenReturn(referred104Details);

        Referred104Details requestObj = new Referred104Details();
        requestObj.setAction("Action");
        requestObj.setActionId(1);
        requestObj.setAlgorithm("Algorithm");
        requestObj.setBenCallID("Ben Call ID");
        requestObj.setBeneficiaryID(1L);
        requestObj.setBeneficiaryRegID(1L);
        requestObj.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        requestObj.setId(1);
        requestObj.setParkingPlaceID(1);
        requestObj.setPatientAge("Patient Age");
        requestObj.setPatientGenderID(1);
        requestObj.setPatientName("Patient Name");
        requestObj.setPresentChiefComplaint("Present Chief Complaint");
        requestObj.setPresentChiefComplaintID("Present Chief Complaint ID");
        requestObj.setProviderServiceMapID(1);
        requestObj.setRecommendedAction("Recommended Action");
        requestObj.setRemarks("Remarks");
        requestObj.setSelecteDiagnosis("Selecte Diagnosis");
        requestObj.setSelecteDiagnosisID("Selecte Diagnosis ID");
        requestObj.setServiceID(1);
        requestObj.setSessionID(1);
        requestObj.setVanID(1);
        Referred104Details actualSaveReferredDetailsResult = uptsuServiceImpl.saveReferredDetails(requestObj);
        verify(uptsuRepository).updateReferredFlag(eq("Ben Call ID"));
        verify(uptsuRepository).save(isA(Referred104Details.class));
        assertSame(referred104Details, actualSaveReferredDetailsResult);
    }
}
