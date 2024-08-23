package com.iemr.hwc.service.masterVillage;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.location.DistrictBranchMapping;
import com.iemr.hwc.data.login.Users;
import com.iemr.hwc.data.login.UsersMasterVillage;
import com.iemr.hwc.repo.location.DistrictBranchMasterRepo;
import com.iemr.hwc.repo.login.UserLoginRepo;
import com.iemr.hwc.repo.login.UserMasterVillageRepo;

@ExtendWith(MockitoExtension.class)
class MasterVillageServiceImplTest {
    @Mock
    private DistrictBranchMasterRepo districtBranchMasterRepo;

    @InjectMocks
    private MasterVillageServiceImpl masterVillageServiceImpl;

    @Mock
    private UserLoginRepo userLoginRepo;

    @Mock
    private UserMasterVillageRepo userMasterVillageRepo;

    @Test
    void testSetMasterVillage() {
        DistrictBranchMapping districtBranchMapping = new DistrictBranchMapping();
        districtBranchMapping.setActive(true);
        districtBranchMapping.setAddress("42 Main St");
        districtBranchMapping.setBlockID(1);
        districtBranchMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        districtBranchMapping.setCreatedDate(mock(Timestamp.class));
        districtBranchMapping.setDeleted(true);
        districtBranchMapping.setDistrictBranchID(1);
        districtBranchMapping.setHabitat("Habitat");
        districtBranchMapping.setLastModDate(mock(Timestamp.class));
        districtBranchMapping.setLatitude(10.0d);
        districtBranchMapping.setLoginDistance(1);
        districtBranchMapping.setLongitude(10.0d);
        districtBranchMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        districtBranchMapping.setPanchayatName("Panchayat Name");
        districtBranchMapping.setPinCode("Pin Code");
        districtBranchMapping.setVillageName("Village Name");
        when(districtBranchMasterRepo.findByDistrictBranchID(Mockito.<Integer>any())).thenReturn(districtBranchMapping);

        Users users = new Users();
        users.setAadhaarNo("Aadhaar No");
        users.setAgentID("Agent ID");
        users.setAgentPassword("iloveyou");
        users.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        users.setCreatedDate(mock(Timestamp.class));
        users.setDeleted(true);
        users.setDob(mock(Timestamp.class));
        users.setDoj(mock(Timestamp.class));
        users.setEmailID("jane.doe@example.org");
        users.setEmergencyContactNo("Emergency Contact No");
        users.setEmergencyContactPerson("Emergency Contact Person");
        users.setFirstName("Jane");
        users.setGenderID((short) 1);
        users.setIsSupervisor(true);
        users.setLastModDate(mock(Timestamp.class));
        users.setLastName("Doe");
        users.setMaritalStatusID((short) 1);
        users.setMiddleName("Middle Name");
        users.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        users.setPan("Pan");
        users.setPassword("iloveyou");
        users.setQualificationID(1);
        users.setStatusID((short) 1);
        users.setTitleID((short) 1);
        users.setUserID(1L);
        users.setUserName("janedoe");
        when(userLoginRepo.getUserByUserID(Mockito.<Long>any())).thenReturn(users);

        DistrictBranchMapping masterVillage = new DistrictBranchMapping();
        masterVillage.setActive(true);
        masterVillage.setAddress("42 Main St");
        masterVillage.setBlockID(1);
        masterVillage.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        masterVillage.setCreatedDate(mock(Timestamp.class));
        masterVillage.setDeleted(true);
        masterVillage.setDistrictBranchID(1);
        masterVillage.setHabitat("Habitat");
        masterVillage.setLastModDate(mock(Timestamp.class));
        masterVillage.setLatitude(10.0d);
        masterVillage.setLoginDistance(1);
        masterVillage.setLongitude(10.0d);
        masterVillage.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        masterVillage.setPanchayatName("Panchayat Name");
        masterVillage.setPinCode("Pin Code");
        masterVillage.setVillageName("Village Name");

        Users user = new Users();
        user.setAadhaarNo("Aadhaar No");
        user.setAgentID("Agent ID");
        user.setAgentPassword("iloveyou");
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setCreatedDate(mock(Timestamp.class));
        user.setDeleted(true);
        user.setDob(mock(Timestamp.class));
        user.setDoj(mock(Timestamp.class));
        user.setEmailID("jane.doe@example.org");
        user.setEmergencyContactNo("Emergency Contact No");
        user.setEmergencyContactPerson("Emergency Contact Person");
        user.setFirstName("Jane");
        user.setGenderID((short) 1);
        user.setIsSupervisor(true);
        user.setLastModDate(mock(Timestamp.class));
        user.setLastName("Doe");
        user.setMaritalStatusID((short) 1);
        user.setMiddleName("Middle Name");
        user.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setPan("Pan");
        user.setPassword("iloveyou");
        user.setQualificationID(1);
        user.setStatusID((short) 1);
        user.setTitleID((short) 1);
        user.setUserID(1L);
        user.setUserName("janedoe");

        UsersMasterVillage usersMasterVillage = new UsersMasterVillage();
        usersMasterVillage.setActive(true);
        usersMasterVillage.setCreatedDate(mock(Timestamp.class));
        usersMasterVillage.setId(1L);
        usersMasterVillage.setLastModDate(mock(Timestamp.class));
        usersMasterVillage.setMasterVillage(masterVillage);
        usersMasterVillage.setUser(user);

        DistrictBranchMapping masterVillage2 = new DistrictBranchMapping();
        masterVillage2.setActive(true);
        masterVillage2.setAddress("42 Main St");
        masterVillage2.setBlockID(1);
        masterVillage2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        masterVillage2.setCreatedDate(mock(Timestamp.class));
        masterVillage2.setDeleted(true);
        masterVillage2.setDistrictBranchID(1);
        masterVillage2.setHabitat("Habitat");
        masterVillage2.setLastModDate(mock(Timestamp.class));
        masterVillage2.setLatitude(10.0d);
        masterVillage2.setLoginDistance(1);
        masterVillage2.setLongitude(10.0d);
        masterVillage2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        masterVillage2.setPanchayatName("Panchayat Name");
        masterVillage2.setPinCode("Pin Code");
        masterVillage2.setVillageName("Village Name");

        Users user2 = new Users();
        user2.setAadhaarNo("Aadhaar No");
        user2.setAgentID("Agent ID");
        user2.setAgentPassword("iloveyou");
        user2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user2.setCreatedDate(mock(Timestamp.class));
        user2.setDeleted(true);
        user2.setDob(mock(Timestamp.class));
        user2.setDoj(mock(Timestamp.class));
        user2.setEmailID("jane.doe@example.org");
        user2.setEmergencyContactNo("Emergency Contact No");
        user2.setEmergencyContactPerson("Emergency Contact Person");
        user2.setFirstName("Jane");
        user2.setGenderID((short) 1);
        user2.setIsSupervisor(true);
        user2.setLastModDate(mock(Timestamp.class));
        user2.setLastName("Doe");
        user2.setMaritalStatusID((short) 1);
        user2.setMiddleName("Middle Name");
        user2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user2.setPan("Pan");
        user2.setPassword("iloveyou");
        user2.setQualificationID(1);
        user2.setStatusID((short) 1);
        user2.setTitleID((short) 1);
        user2.setUserID(1L);
        user2.setUserName("janedoe");

        UsersMasterVillage usersMasterVillage2 = new UsersMasterVillage();
        usersMasterVillage2.setActive(true);
        usersMasterVillage2.setCreatedDate(mock(Timestamp.class));
        usersMasterVillage2.setId(1L);
        usersMasterVillage2.setLastModDate(mock(Timestamp.class));
        usersMasterVillage2.setMasterVillage(masterVillage2);
        usersMasterVillage2.setUser(user2);
        when(userMasterVillageRepo.getByUserID(Mockito.<Long>any())).thenReturn(usersMasterVillage);
        when(userMasterVillageRepo.save(Mockito.<UsersMasterVillage>any())).thenReturn(usersMasterVillage2);

        // Act
        masterVillageServiceImpl.setMasterVillage(1L, 1);
    }

    @Test
    void testGetMasterVillage() {
        // Arrange
        DistrictBranchMapping masterVillage = new DistrictBranchMapping();
        masterVillage.setActive(true);
        masterVillage.setAddress("42 Main St");
        masterVillage.setBlockID(1);
        masterVillage.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        masterVillage.setCreatedDate(mock(Timestamp.class));
        masterVillage.setDeleted(true);
        masterVillage.setDistrictBranchID(1);
        masterVillage.setHabitat("Habitat");
        masterVillage.setLastModDate(mock(Timestamp.class));
        masterVillage.setLatitude(10.0d);
        masterVillage.setLoginDistance(1);
        masterVillage.setLongitude(10.0d);
        masterVillage.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        masterVillage.setPanchayatName("Panchayat Name");
        masterVillage.setPinCode("Pin Code");
        masterVillage.setVillageName("Village Name");

        Users user = new Users();
        user.setAadhaarNo("Aadhaar No");
        user.setAgentID("Agent ID");
        user.setAgentPassword("iloveyou");
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setCreatedDate(mock(Timestamp.class));
        user.setDeleted(true);
        user.setDob(mock(Timestamp.class));
        user.setDoj(mock(Timestamp.class));
        user.setEmailID("jane.doe@example.org");
        user.setEmergencyContactNo("Emergency Contact No");
        user.setEmergencyContactPerson("Emergency Contact Person");
        user.setFirstName("Jane");
        user.setGenderID((short) 1);
        user.setIsSupervisor(true);
        user.setLastModDate(mock(Timestamp.class));
        user.setLastName("Doe");
        user.setMaritalStatusID((short) 1);
        user.setMiddleName("Middle Name");
        user.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        user.setPan("Pan");
        user.setPassword("iloveyou");
        user.setQualificationID(1);
        user.setStatusID((short) 1);
        user.setTitleID((short) 1);
        user.setUserID(1L);
        user.setUserName("janedoe");

        UsersMasterVillage usersMasterVillage = new UsersMasterVillage();
        usersMasterVillage.setActive(true);
        usersMasterVillage.setCreatedDate(mock(Timestamp.class));
        usersMasterVillage.setId(1L);
        usersMasterVillage.setLastModDate(mock(Timestamp.class));
        usersMasterVillage.setMasterVillage(masterVillage);
        usersMasterVillage.setUser(user);
        when(userMasterVillageRepo.getByUserID(Mockito.<Long>any())).thenReturn(usersMasterVillage);

        // Act
        UsersMasterVillage actualMasterVillage = masterVillageServiceImpl.getMasterVillage(1L);

        // Assert
        verify(userMasterVillageRepo).getByUserID(isA(Long.class));
        assertSame(usersMasterVillage, actualMasterVillage);
    }
}
