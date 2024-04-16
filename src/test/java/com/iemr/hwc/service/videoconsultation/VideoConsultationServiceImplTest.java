package com.iemr.hwc.service.videoconsultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.videoconsultation.M_UserTemp;
import com.iemr.hwc.data.videoconsultation.UserJitsi;
import com.iemr.hwc.data.videoconsultation.VideoConsultationUser;
import com.iemr.hwc.repo.login.MasterVanRepo;
import com.iemr.hwc.repo.videoconsultation.UserJitsiRepo;
import com.iemr.hwc.repo.videoconsultation.VideoConsultationUserRepo;
import com.iemr.hwc.utils.exception.VideoConsultationException;
import com.iemr.hwc.utils.mapper.OutputMapper;

@ExtendWith(MockitoExtension.class)
class VideoConsultationServiceImplTest {
    @Mock
    private MasterVanRepo masterVanRepo;

    @Mock
    private UserJitsiRepo userJitsiRepo;

    @InjectMocks
    private VideoConsultationServiceImpl videoConsultationServiceImpl;

    @Mock
    private VideoConsultationUserRepo videoConsultationUserRepo;

   
    @Test
    void testLogin() throws VideoConsultationException {
        // Arrange
        VideoConsultationUser userSwymed = new VideoConsultationUser();
        userSwymed.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userSwymed.setCreatedDate(mock(Timestamp.class));
        userSwymed.setDeleted(true);
        userSwymed.setLastModDate(mock(Timestamp.class));
        userSwymed.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        userSwymed.setOutputMapper(new OutputMapper());
        userSwymed.setSwymedDomain("Swymed Domain");
        userSwymed.setSwymedEmailID("jane.doe@example.org");
        userSwymed.setSwymedID(1L);
        userSwymed.setSwymedPassword("iloveyou");
        userSwymed.setUser(new M_UserTemp());
        userSwymed.setUserID(1L);
        userSwymed.setUserSwymedMapID(1L);
        userSwymed.setUsername("janedoe");

        M_UserTemp user = new M_UserTemp();
        user.setAadhaarNo("Aadhaar No");
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setCreatedDate(mock(Timestamp.class));
        user.setDOB(mock(Timestamp.class));
        user.setDOJ(mock(Timestamp.class));
        user.setDeleted(true);
        user.setDesignationID(1);
        user.setEmailID("jane.doe@example.org");
        user.setEmergencyContactNo("Emergency Contact No");
        user.setEmergencyContactPerson("Emergency Contact Person");
        user.setFirstName("Jane");
        user.setGenderID(1);
        user.setIsSupervisor(true);
        user.setLastModDate(mock(Timestamp.class));
        user.setLastName("Doe");
        user.setMaritalStatusID(1);
        user.setMiddleName("Middle Name");
        user.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setOutputMapper(new OutputMapper());
        user.setPAN("P AN");
        user.setQualificationID(1);
        user.setServiceProviderID(1);
        user.setStatusID(1);
        user.setTitleID(1);
        user.setUserID(1L);
        user.setUserName("janedoe");
        user.setUserSwymed(userSwymed);

        VideoConsultationUser userSwymed2 = new VideoConsultationUser();
        userSwymed2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userSwymed2.setCreatedDate(mock(Timestamp.class));
        userSwymed2.setDeleted(true);
        userSwymed2.setLastModDate(mock(Timestamp.class));
        userSwymed2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        userSwymed2.setOutputMapper(new OutputMapper());
        userSwymed2.setSwymedDomain("Swymed Domain");
        userSwymed2.setSwymedEmailID("jane.doe@example.org");
        userSwymed2.setSwymedID(1L);
        userSwymed2.setSwymedPassword("iloveyou");
        userSwymed2.setUser(user);
        userSwymed2.setUserID(1L);
        userSwymed2.setUserSwymedMapID(1L);
        userSwymed2.setUsername("janedoe");

        M_UserTemp user2 = new M_UserTemp();
        user2.setAadhaarNo("Aadhaar No");
        user2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user2.setCreatedDate(mock(Timestamp.class));
        user2.setDOB(mock(Timestamp.class));
        user2.setDOJ(mock(Timestamp.class));
        user2.setDeleted(true);
        user2.setDesignationID(1);
        user2.setEmailID("jane.doe@example.org");
        user2.setEmergencyContactNo("Emergency Contact No");
        user2.setEmergencyContactPerson("Emergency Contact Person");
        user2.setFirstName("Jane");
        user2.setGenderID(1);
        user2.setIsSupervisor(true);
        user2.setLastModDate(mock(Timestamp.class));
        user2.setLastName("Doe");
        user2.setMaritalStatusID(1);
        user2.setMiddleName("Middle Name");
        user2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user2.setOutputMapper(new OutputMapper());
        user2.setPAN("P AN");
        user2.setQualificationID(1);
        user2.setServiceProviderID(1);
        user2.setStatusID(1);
        user2.setTitleID(1);
        user2.setUserID(1L);
        user2.setUserName("janedoe");
        user2.setUserSwymed(userSwymed2);

        VideoConsultationUser videoConsultationUser = new VideoConsultationUser();
        videoConsultationUser.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        videoConsultationUser.setCreatedDate(mock(Timestamp.class));
        videoConsultationUser.setDeleted(true);
        videoConsultationUser.setLastModDate(mock(Timestamp.class));
        videoConsultationUser.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        videoConsultationUser.setOutputMapper(new OutputMapper());
        videoConsultationUser.setSwymedDomain("Swymed Domain");
        videoConsultationUser.setSwymedEmailID("jane.doe@example.org");
        videoConsultationUser.setSwymedID(1L);
        videoConsultationUser.setSwymedPassword("iloveyou");
        videoConsultationUser.setUser(user2);
        videoConsultationUser.setUserID(1L);
        videoConsultationUser.setUserSwymedMapID(1L);
        videoConsultationUser.setUsername("janedoe");
        when(videoConsultationUserRepo.findOneMap(Mockito.<Long>any())).thenReturn(videoConsultationUser);

        String actualLoginResult = videoConsultationServiceImpl.login(1L);
        verify(videoConsultationUserRepo).findOneMap(isA(Long.class));
        assertEquals("swymed://psmri.swymed.com?l=jane.doe@example.org&p=iloveyou&d=Swymed Domain", actualLoginResult);
    }

    @Test
    void testCallUser() throws VideoConsultationException {
        // Arrange
        VideoConsultationUser userSwymed = new VideoConsultationUser();
        userSwymed.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userSwymed.setCreatedDate(mock(Timestamp.class));
        userSwymed.setDeleted(true);
        userSwymed.setLastModDate(mock(Timestamp.class));
        userSwymed.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        userSwymed.setOutputMapper(new OutputMapper());
        userSwymed.setSwymedDomain("Swymed Domain");
        userSwymed.setSwymedEmailID("jane.doe@example.org");
        userSwymed.setSwymedID(1L);
        userSwymed.setSwymedPassword("iloveyou");
        userSwymed.setUser(new M_UserTemp());
        userSwymed.setUserID(1L);
        userSwymed.setUserSwymedMapID(1L);
        userSwymed.setUsername("janedoe");

        M_UserTemp user = new M_UserTemp();
        user.setAadhaarNo("Aadhaar No");
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setCreatedDate(mock(Timestamp.class));
        user.setDOB(mock(Timestamp.class));
        user.setDOJ(mock(Timestamp.class));
        user.setDeleted(true);
        user.setDesignationID(1);
        user.setEmailID("jane.doe@example.org");
        user.setEmergencyContactNo("Emergency Contact No");
        user.setEmergencyContactPerson("Emergency Contact Person");
        user.setFirstName("Jane");
        user.setGenderID(1);
        user.setIsSupervisor(true);
        user.setLastModDate(mock(Timestamp.class));
        user.setLastName("Doe");
        user.setMaritalStatusID(1);
        user.setMiddleName("Middle Name");
        user.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setOutputMapper(new OutputMapper());
        user.setPAN("P AN");
        user.setQualificationID(1);
        user.setServiceProviderID(1);
        user.setStatusID(1);
        user.setTitleID(1);
        user.setUserID(1L);
        user.setUserName("janedoe");
        user.setUserSwymed(userSwymed);

        VideoConsultationUser userSwymed2 = new VideoConsultationUser();
        userSwymed2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userSwymed2.setCreatedDate(mock(Timestamp.class));
        userSwymed2.setDeleted(true);
        userSwymed2.setLastModDate(mock(Timestamp.class));
        userSwymed2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        userSwymed2.setOutputMapper(new OutputMapper());
        userSwymed2.setSwymedDomain("Swymed Domain");
        userSwymed2.setSwymedEmailID("jane.doe@example.org");
        userSwymed2.setSwymedID(1L);
        userSwymed2.setSwymedPassword("iloveyou");
        userSwymed2.setUser(user);
        userSwymed2.setUserID(1L);
        userSwymed2.setUserSwymedMapID(1L);
        userSwymed2.setUsername("janedoe");

        M_UserTemp user2 = new M_UserTemp();
        user2.setAadhaarNo("Aadhaar No");
        user2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user2.setCreatedDate(mock(Timestamp.class));
        user2.setDOB(mock(Timestamp.class));
        user2.setDOJ(mock(Timestamp.class));
        user2.setDeleted(true);
        user2.setDesignationID(1);
        user2.setEmailID("jane.doe@example.org");
        user2.setEmergencyContactNo("Emergency Contact No");
        user2.setEmergencyContactPerson("Emergency Contact Person");
        user2.setFirstName("Jane");
        user2.setGenderID(1);
        user2.setIsSupervisor(true);
        user2.setLastModDate(mock(Timestamp.class));
        user2.setLastName("Doe");
        user2.setMaritalStatusID(1);
        user2.setMiddleName("Middle Name");
        user2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user2.setOutputMapper(new OutputMapper());
        user2.setPAN("P AN");
        user2.setQualificationID(1);
        user2.setServiceProviderID(1);
        user2.setStatusID(1);
        user2.setTitleID(1);
        user2.setUserID(1L);
        user2.setUserName("janedoe");
        user2.setUserSwymed(userSwymed2);

        VideoConsultationUser videoConsultationUser = new VideoConsultationUser();
        videoConsultationUser.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        videoConsultationUser.setCreatedDate(mock(Timestamp.class));
        videoConsultationUser.setDeleted(true);
        videoConsultationUser.setLastModDate(mock(Timestamp.class));
        videoConsultationUser.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        videoConsultationUser.setOutputMapper(new OutputMapper());
        videoConsultationUser.setSwymedDomain("Swymed Domain");
        videoConsultationUser.setSwymedEmailID("jane.doe@example.org");
        videoConsultationUser.setSwymedID(1L);
        videoConsultationUser.setSwymedPassword("iloveyou");
        videoConsultationUser.setUser(user2);
        videoConsultationUser.setUserID(1L);
        videoConsultationUser.setUserSwymedMapID(1L);
        videoConsultationUser.setUsername("janedoe");
        when(videoConsultationUserRepo.findOneMap(Mockito.<Long>any())).thenReturn(videoConsultationUser);
        String actualCallUserResult = videoConsultationServiceImpl.callUser(1L, 1L);
        verify(videoConsultationUserRepo, atLeast(1)).findOneMap(isA(Long.class));
        assertEquals("swymed://psmri.swymed.com?l=jane.doe@example.org&p=iloveyou&d=Swymed Domain&e=jane.doe@example.org",
                actualCallUserResult);
    }

    @Test
    void testCallUserjitsi() throws VideoConsultationException {
        // Arrange
        UserJitsi userJitsi = new UserJitsi();
        userJitsi.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userJitsi.setCreatedDate(mock(Timestamp.class));
        userJitsi.setDeleted(true);
        userJitsi.setJitsiID(1L);
        userJitsi.setJitsiPassword("iloveyou");
        userJitsi.setJitsiUserName("janedoe");
        userJitsi.setLastModDate(mock(Timestamp.class));
        userJitsi.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        userJitsi.setProcessed("Processed");
        userJitsi.setProviderServiceMapID(1);
        userJitsi.setUserID(1L);
        userJitsi.setVanID(1);
        userJitsi.setVanid(1);
        when(userJitsiRepo.findOneJitsiMap(Mockito.<Long>any())).thenReturn(userJitsi);
        String actualCallUserjitsiResult = videoConsultationServiceImpl.callUserjitsi(1L, 1L);
        verify(userJitsiRepo, atLeast(1)).findOneJitsiMap(isA(Long.class));
        assertEquals("https://meet.jit.si/janedoe/iloveyou", actualCallUserjitsiResult);
    }

    @Test
    void testCallUserjitsi2() throws VideoConsultationException {
        // Arrange
        UserJitsi userJitsi = new UserJitsi();
        userJitsi.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userJitsi.setCreatedDate(mock(Timestamp.class));
        userJitsi.setDeleted(true);
        userJitsi.setJitsiID(1L);
        userJitsi.setJitsiPassword(null);
        userJitsi.setJitsiUserName("janedoe");
        userJitsi.setLastModDate(mock(Timestamp.class));
        userJitsi.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        userJitsi.setProcessed("Processed");
        userJitsi.setProviderServiceMapID(1);
        userJitsi.setUserID(1L);
        userJitsi.setVanID(1);
        userJitsi.setVanid(1);
        when(userJitsiRepo.findOneJitsiMap(Mockito.<Long>any())).thenReturn(userJitsi);
        String actualCallUserjitsiResult = videoConsultationServiceImpl.callUserjitsi(1L, 1L);
        verify(userJitsiRepo, atLeast(1)).findOneJitsiMap(isA(Long.class));
        assertEquals("https://meet.jit.si/janedoe", actualCallUserjitsiResult);
    }

    @Test
    void testCallVan() throws VideoConsultationException {
        // Arrange
        when(masterVanRepo.getSpokeEmail(Mockito.<Integer>any())).thenReturn("jane.doe@example.org");

        VideoConsultationUser userSwymed = new VideoConsultationUser();
        userSwymed.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userSwymed.setCreatedDate(mock(Timestamp.class));
        userSwymed.setDeleted(true);
        userSwymed.setLastModDate(mock(Timestamp.class));
        userSwymed.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        userSwymed.setOutputMapper(new OutputMapper());
        userSwymed.setSwymedDomain("Swymed Domain");
        userSwymed.setSwymedEmailID("jane.doe@example.org");
        userSwymed.setSwymedID(1L);
        userSwymed.setSwymedPassword("iloveyou");
        userSwymed.setUser(new M_UserTemp());
        userSwymed.setUserID(1L);
        userSwymed.setUserSwymedMapID(1L);
        userSwymed.setUsername("janedoe");

        M_UserTemp user = new M_UserTemp();
        user.setAadhaarNo("Aadhaar No");
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setCreatedDate(mock(Timestamp.class));
        user.setDOB(mock(Timestamp.class));
        user.setDOJ(mock(Timestamp.class));
        user.setDeleted(true);
        user.setDesignationID(1);
        user.setEmailID("jane.doe@example.org");
        user.setEmergencyContactNo("Emergency Contact No");
        user.setEmergencyContactPerson("Emergency Contact Person");
        user.setFirstName("Jane");
        user.setGenderID(1);
        user.setIsSupervisor(true);
        user.setLastModDate(mock(Timestamp.class));
        user.setLastName("Doe");
        user.setMaritalStatusID(1);
        user.setMiddleName("Middle Name");
        user.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setOutputMapper(new OutputMapper());
        user.setPAN("P AN");
        user.setQualificationID(1);
        user.setServiceProviderID(1);
        user.setStatusID(1);
        user.setTitleID(1);
        user.setUserID(1L);
        user.setUserName("janedoe");
        user.setUserSwymed(userSwymed);

        VideoConsultationUser userSwymed2 = new VideoConsultationUser();
        userSwymed2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userSwymed2.setCreatedDate(mock(Timestamp.class));
        userSwymed2.setDeleted(true);
        userSwymed2.setLastModDate(mock(Timestamp.class));
        userSwymed2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        userSwymed2.setOutputMapper(new OutputMapper());
        userSwymed2.setSwymedDomain("Swymed Domain");
        userSwymed2.setSwymedEmailID("jane.doe@example.org");
        userSwymed2.setSwymedID(1L);
        userSwymed2.setSwymedPassword("iloveyou");
        userSwymed2.setUser(user);
        userSwymed2.setUserID(1L);
        userSwymed2.setUserSwymedMapID(1L);
        userSwymed2.setUsername("janedoe");

        M_UserTemp user2 = new M_UserTemp();
        user2.setAadhaarNo("Aadhaar No");
        user2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user2.setCreatedDate(mock(Timestamp.class));
        user2.setDOB(mock(Timestamp.class));
        user2.setDOJ(mock(Timestamp.class));
        user2.setDeleted(true);
        user2.setDesignationID(1);
        user2.setEmailID("jane.doe@example.org");
        user2.setEmergencyContactNo("Emergency Contact No");
        user2.setEmergencyContactPerson("Emergency Contact Person");
        user2.setFirstName("Jane");
        user2.setGenderID(1);
        user2.setIsSupervisor(true);
        user2.setLastModDate(mock(Timestamp.class));
        user2.setLastName("Doe");
        user2.setMaritalStatusID(1);
        user2.setMiddleName("Middle Name");
        user2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user2.setOutputMapper(new OutputMapper());
        user2.setPAN("P AN");
        user2.setQualificationID(1);
        user2.setServiceProviderID(1);
        user2.setStatusID(1);
        user2.setTitleID(1);
        user2.setUserID(1L);
        user2.setUserName("janedoe");
        user2.setUserSwymed(userSwymed2);

        VideoConsultationUser videoConsultationUser = new VideoConsultationUser();
        videoConsultationUser.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        videoConsultationUser.setCreatedDate(mock(Timestamp.class));
        videoConsultationUser.setDeleted(true);
        videoConsultationUser.setLastModDate(mock(Timestamp.class));
        videoConsultationUser.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        videoConsultationUser.setOutputMapper(new OutputMapper());
        videoConsultationUser.setSwymedDomain("Swymed Domain");
        videoConsultationUser.setSwymedEmailID("jane.doe@example.org");
        videoConsultationUser.setSwymedID(1L);
        videoConsultationUser.setSwymedPassword("iloveyou");
        videoConsultationUser.setUser(user2);
        videoConsultationUser.setUserID(1L);
        videoConsultationUser.setUserSwymedMapID(1L);
        videoConsultationUser.setUsername("janedoe");
        when(videoConsultationUserRepo.findOneMap(Mockito.<Long>any())).thenReturn(videoConsultationUser);
        String actualCallVanResult = videoConsultationServiceImpl.callVan(1L, 1);
        verify(masterVanRepo).getSpokeEmail(isA(Integer.class));
        verify(videoConsultationUserRepo).findOneMap(isA(Long.class));
        assertEquals("swymed://psmri.swymed.com?l=jane.doe@example.org&p=iloveyou&d=Swymed Domain&e=jane.doe@example.org",
                actualCallVanResult);
    }

    @Test
    void testCallVan2() throws VideoConsultationException {
        // Arrange
        when(masterVanRepo.getSpokeEmail(Mockito.<Integer>any())).thenReturn(null);

        VideoConsultationUser userSwymed = new VideoConsultationUser();
        userSwymed.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userSwymed.setCreatedDate(mock(Timestamp.class));
        userSwymed.setDeleted(true);
        userSwymed.setLastModDate(mock(Timestamp.class));
        userSwymed.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        userSwymed.setOutputMapper(new OutputMapper());
        userSwymed.setSwymedDomain("Swymed Domain");
        userSwymed.setSwymedEmailID("jane.doe@example.org");
        userSwymed.setSwymedID(1L);
        userSwymed.setSwymedPassword("iloveyou");
        userSwymed.setUser(new M_UserTemp());
        userSwymed.setUserID(1L);
        userSwymed.setUserSwymedMapID(1L);
        userSwymed.setUsername("janedoe");

        M_UserTemp user = new M_UserTemp();
        user.setAadhaarNo("Aadhaar No");
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setCreatedDate(mock(Timestamp.class));
        user.setDOB(mock(Timestamp.class));
        user.setDOJ(mock(Timestamp.class));
        user.setDeleted(true);
        user.setDesignationID(1);
        user.setEmailID("jane.doe@example.org");
        user.setEmergencyContactNo("Emergency Contact No");
        user.setEmergencyContactPerson("Emergency Contact Person");
        user.setFirstName("Jane");
        user.setGenderID(1);
        user.setIsSupervisor(true);
        user.setLastModDate(mock(Timestamp.class));
        user.setLastName("Doe");
        user.setMaritalStatusID(1);
        user.setMiddleName("Middle Name");
        user.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setOutputMapper(new OutputMapper());
        user.setPAN("P AN");
        user.setQualificationID(1);
        user.setServiceProviderID(1);
        user.setStatusID(1);
        user.setTitleID(1);
        user.setUserID(1L);
        user.setUserName("janedoe");
        user.setUserSwymed(userSwymed);

        VideoConsultationUser userSwymed2 = new VideoConsultationUser();
        userSwymed2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userSwymed2.setCreatedDate(mock(Timestamp.class));
        userSwymed2.setDeleted(true);
        userSwymed2.setLastModDate(mock(Timestamp.class));
        userSwymed2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        userSwymed2.setOutputMapper(new OutputMapper());
        userSwymed2.setSwymedDomain("Swymed Domain");
        userSwymed2.setSwymedEmailID("jane.doe@example.org");
        userSwymed2.setSwymedID(1L);
        userSwymed2.setSwymedPassword("iloveyou");
        userSwymed2.setUser(user);
        userSwymed2.setUserID(1L);
        userSwymed2.setUserSwymedMapID(1L);
        userSwymed2.setUsername("janedoe");

        M_UserTemp user2 = new M_UserTemp();
        user2.setAadhaarNo("Aadhaar No");
        user2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user2.setCreatedDate(mock(Timestamp.class));
        user2.setDOB(mock(Timestamp.class));
        user2.setDOJ(mock(Timestamp.class));
        user2.setDeleted(true);
        user2.setDesignationID(1);
        user2.setEmailID("jane.doe@example.org");
        user2.setEmergencyContactNo("Emergency Contact No");
        user2.setEmergencyContactPerson("Emergency Contact Person");
        user2.setFirstName("Jane");
        user2.setGenderID(1);
        user2.setIsSupervisor(true);
        user2.setLastModDate(mock(Timestamp.class));
        user2.setLastName("Doe");
        user2.setMaritalStatusID(1);
        user2.setMiddleName("Middle Name");
        user2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user2.setOutputMapper(new OutputMapper());
        user2.setPAN("P AN");
        user2.setQualificationID(1);
        user2.setServiceProviderID(1);
        user2.setStatusID(1);
        user2.setTitleID(1);
        user2.setUserID(1L);
        user2.setUserName("janedoe");
        user2.setUserSwymed(userSwymed2);

        VideoConsultationUser videoConsultationUser = new VideoConsultationUser();
        videoConsultationUser.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        videoConsultationUser.setCreatedDate(mock(Timestamp.class));
        videoConsultationUser.setDeleted(true);
        videoConsultationUser.setLastModDate(mock(Timestamp.class));
        videoConsultationUser.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        videoConsultationUser.setOutputMapper(new OutputMapper());
        videoConsultationUser.setSwymedDomain("Swymed Domain");
        videoConsultationUser.setSwymedEmailID("jane.doe@example.org");
        videoConsultationUser.setSwymedID(1L);
        videoConsultationUser.setSwymedPassword("iloveyou");
        videoConsultationUser.setUser(user2);
        videoConsultationUser.setUserID(1L);
        videoConsultationUser.setUserSwymedMapID(1L);
        videoConsultationUser.setUsername("janedoe");
        when(videoConsultationUserRepo.findOneMap(Mockito.<Long>any())).thenReturn(videoConsultationUser);
        assertThrows(VideoConsultationException.class, () -> videoConsultationServiceImpl.callVan(1L, 1));
        verify(masterVanRepo).getSpokeEmail(isA(Integer.class));
        verify(videoConsultationUserRepo).findOneMap(isA(Long.class));
    }

    @Test
    void testCallVanJitsi() throws VideoConsultationException {
        // Arrange
        UserJitsi userJitsi = new UserJitsi();
        userJitsi.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userJitsi.setCreatedDate(mock(Timestamp.class));
        userJitsi.setDeleted(true);
        userJitsi.setJitsiID(1L);
        userJitsi.setJitsiPassword("iloveyou");
        userJitsi.setJitsiUserName("janedoe");
        userJitsi.setLastModDate(mock(Timestamp.class));
        userJitsi.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        userJitsi.setProcessed("Processed");
        userJitsi.setProviderServiceMapID(1);
        userJitsi.setUserID(1L);
        userJitsi.setVanID(1);
        userJitsi.setVanid(1);

        UserJitsi userJitsi2 = new UserJitsi();
        userJitsi2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userJitsi2.setCreatedDate(mock(Timestamp.class));
        userJitsi2.setDeleted(true);
        userJitsi2.setJitsiID(1L);
        userJitsi2.setJitsiPassword("iloveyou");
        userJitsi2.setJitsiUserName("janedoe");
        userJitsi2.setLastModDate(mock(Timestamp.class));
        userJitsi2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        userJitsi2.setProcessed("Processed");
        userJitsi2.setProviderServiceMapID(1);
        userJitsi2.setUserID(1L);
        userJitsi2.setVanID(1);
        userJitsi2.setVanid(1);
        when(userJitsiRepo.findOneJitsiMap(Mockito.<Long>any())).thenReturn(userJitsi);
        when(userJitsiRepo.findOneJitsiMapVan(Mockito.<Integer>any())).thenReturn(userJitsi2);
        String actualCallVanJitsiResult = videoConsultationServiceImpl.callVanJitsi(1L, 1);
        verify(userJitsiRepo).findOneJitsiMap(isA(Long.class));
        verify(userJitsiRepo).findOneJitsiMapVan(isA(Integer.class));
        assertEquals("https://meet.jit.si/janedoe/iloveyou", actualCallVanJitsiResult);
    }

    @Test
    void testLogout() {
        assertEquals("swymed://psmri.swymed.com?logout", videoConsultationServiceImpl.logout());
    }
}
