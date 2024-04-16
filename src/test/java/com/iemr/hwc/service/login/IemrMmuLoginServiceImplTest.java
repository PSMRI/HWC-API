package com.iemr.hwc.service.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.iemr.hwc.repo.login.MasterVanRepo;
import com.iemr.hwc.repo.login.ServicePointVillageMappingRepo;
import com.iemr.hwc.repo.login.UserParkingplaceMappingRepo;
import com.iemr.hwc.repo.login.UserVanSpDetails_View_Repo;
import com.iemr.hwc.repo.login.VanServicepointMappingRepo;

@ExtendWith(MockitoExtension.class)
class IemrMmuLoginServiceImplTest {
    @InjectMocks
    private IemrMmuLoginServiceImpl iemrMmuLoginServiceImpl;

    @Mock
    private MasterVanRepo masterVanRepo;

    @Mock
    private ServicePointVillageMappingRepo servicePointVillageMappingRepo;

    @Mock
    private UserParkingplaceMappingRepo userParkingplaceMappingRepo;

    @Mock
    private UserVanSpDetails_View_Repo userVanSpDetails_View_Repo;

    @Mock
    private VanServicepointMappingRepo vanServicepointMappingRepo;

    @Test
    void testGetUserServicePointVanDetails() {
        // Arrange
        when(userParkingplaceMappingRepo.getUserParkingPlce(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualUserServicePointVanDetails = iemrMmuLoginServiceImpl.getUserServicePointVanDetails(1);

        // Assert
        verify(userParkingplaceMappingRepo).getUserParkingPlce(isA(Integer.class));
        assertEquals("{}", actualUserServicePointVanDetails);
    }

    @Test
    void testGetServicepointVillages() {
        // Arrange
        when(servicePointVillageMappingRepo.getServicePointVillages(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualServicepointVillages = iemrMmuLoginServiceImpl.getServicepointVillages(1);

        // Assert
        verify(servicePointVillageMappingRepo).getServicePointVillages(isA(Integer.class));
        assertEquals("[]", actualServicepointVillages);
    }

    @Test
    void testGetUserVanSpDetails() {
        // Arrange
        when(userParkingplaceMappingRepo.getUserParkingPlce(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(userVanSpDetails_View_Repo.getUserVanSpDetails_View(Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualUserVanSpDetails = iemrMmuLoginServiceImpl.getUserVanSpDetails(1, 1);

        // Assert
        verify(userParkingplaceMappingRepo).getUserParkingPlce(isA(Integer.class));
        verify(userVanSpDetails_View_Repo).getUserVanSpDetails_View(isA(Integer.class), isA(Integer.class));
        assertEquals("{\"UserLocDetails\":{},\"UserVanSpDetails\":[]}", actualUserVanSpDetails);
    }

    @Test
    void testGetUserSpokeDetails() {
        // Arrange
        when(masterVanRepo.getVanMaster(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualUserSpokeDetails = iemrMmuLoginServiceImpl.getUserSpokeDetails(1);

        // Assert
        verify(masterVanRepo).getVanMaster(isA(Integer.class));
        assertEquals("[{\"vanID\":0,\"vehicalNo\":\"All\"}]", actualUserSpokeDetails);
    }

    @Test
    void testGettersAndSetters() {
        IemrMmuLoginServiceImpl iemrMmuLoginServiceImpl = new IemrMmuLoginServiceImpl();

        // Act
        iemrMmuLoginServiceImpl.setMasterVanRepo(mock(MasterVanRepo.class));
        iemrMmuLoginServiceImpl.setServicePointVillageMappingRepo(mock(ServicePointVillageMappingRepo.class));
        iemrMmuLoginServiceImpl.setUserParkingplaceMappingRepo(mock(UserParkingplaceMappingRepo.class));
        iemrMmuLoginServiceImpl.setUserVanSpDetails_View_Repo(mock(UserVanSpDetails_View_Repo.class));
        iemrMmuLoginServiceImpl.setVanServicepointMappingRepo(mock(VanServicepointMappingRepo.class));
    }
}
