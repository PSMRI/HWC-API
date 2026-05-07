/*
* AMRIT – Accessible Medical Records via Integrated Technology
* Integrated EHR (Electronic Health Records) Solution
*
* Copyright (C) "Piramal Swasthya Management and Research Institute"
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.hwc.service.login;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.hwc.repo.login.MasterVanRepo;
import com.iemr.hwc.repo.login.ServicePointVillageMappingRepo;
import com.iemr.hwc.repo.login.UserParkingplaceMappingRepo;
import com.iemr.hwc.repo.login.UserVanSpDetails_View_Repo;
import com.iemr.hwc.repo.login.VanServicepointMappingRepo;

@ExtendWith(MockitoExtension.class)
class IemrMmuLoginServiceImplTest {

    @Mock
    private UserParkingplaceMappingRepo userParkingplaceMappingRepo;
    @Mock
    private MasterVanRepo masterVanRepo;
    @Mock
    private VanServicepointMappingRepo vanServicepointMappingRepo;
    @Mock
    private ServicePointVillageMappingRepo servicePointVillageMappingRepo;
    @Mock
    private UserVanSpDetails_View_Repo userVanSpDetails_View_Repo;

    @InjectMocks
    private IemrMmuLoginServiceImpl loginService;

    private static final Integer USER_ID = 101;
    private static final Integer PROVIDER_SERVICE_MAP_ID = 202;
    private static final Integer SERVICE_POINT_ID = 303;

    // -------------------- getUserServicePointVanDetails --------------------

    @Test
    void getUserServicePointVanDetails_noParkingPlace_returnsEmptyResponseMap() {
        when(userParkingplaceMappingRepo.getUserParkingPlce(USER_ID))
                .thenReturn(Collections.emptyList());

        String result = loginService.getUserServicePointVanDetails(USER_ID);

        assertNotNull(result);
        assertEquals("{}", result);
    }

    @Test
    void getUserServicePointVanDetails_withParkingPlace_returnsMappedResponse() {
        List<Object[]> parkingList = new ArrayList<>();
        parkingList.add(new Object[]{1, 10, "StateName", 20, "DistrictName", 30, "BlockName"});
        when(userParkingplaceMappingRepo.getUserParkingPlce(USER_ID)).thenReturn(parkingList);

        List<Object[]> vanList = new ArrayList<>();
        vanList.add(new Object[]{5, "VAN-001"});
        when(masterVanRepo.getUserVanDatails(anySet())).thenReturn(vanList);

        List<Object[]> spList = new ArrayList<>();
        spList.add(new Object[]{7, "ServicePoint1", "SESSION_TYPE"});
        when(vanServicepointMappingRepo.getuserSpSessionDetails(anySet())).thenReturn(spList);

        String result = loginService.getUserServicePointVanDetails(USER_ID);

        assertNotNull(result);
        assertTrue(result.contains("userVanDetails"));
        assertTrue(result.contains("userSpDetails"));
        assertTrue(result.contains("parkingPlaceLocationList"));
    }

    @Test
    void getUserServicePointVanDetails_emptyVanList_addsEmptyVanEntry() {
        List<Object[]> parkingList = new ArrayList<>();
        parkingList.add(new Object[]{1, 10, "StateName", 20, "DistrictName", 30, "BlockName"});
        when(userParkingplaceMappingRepo.getUserParkingPlce(USER_ID)).thenReturn(parkingList);

        when(masterVanRepo.getUserVanDatails(anySet())).thenReturn(Collections.emptyList());
        when(vanServicepointMappingRepo.getuserSpSessionDetails(anySet())).thenReturn(Collections.emptyList());

        String result = loginService.getUserServicePointVanDetails(USER_ID);

        assertNotNull(result);
        assertTrue(result.contains("userVanDetails"));
    }

    // -------------------- getServicepointVillages --------------------

    @Test
    void getServicepointVillages_withVillages_returnsMappedList() {
        List<Object[]> villageList = new ArrayList<>();
        villageList.add(new Object[]{1, "Village A"});
        when(servicePointVillageMappingRepo.getServicePointVillages(SERVICE_POINT_ID))
                .thenReturn(villageList);

        String result = loginService.getServicepointVillages(SERVICE_POINT_ID);

        assertNotNull(result);
        assertTrue(result.contains("Village A"));
    }

    @Test
    void getServicepointVillages_noVillages_returnsEmptyJsonArray() {
        when(servicePointVillageMappingRepo.getServicePointVillages(SERVICE_POINT_ID))
                .thenReturn(Collections.emptyList());

        String result = loginService.getServicepointVillages(SERVICE_POINT_ID);

        assertEquals("[]", result);
    }

    // -------------------- getUserVanSpDetails --------------------

    @Test
    void getUserVanSpDetails_withVanSpData_returnsUserVanAndLocDetails() {
        ArrayList<Object[]> spList = new ArrayList<>();
        spList.add(new Object[]{1, 2, "VanName", (short) 1, 3, "SpName", 4, 5});
        when(userVanSpDetails_View_Repo.getUserVanSpDetails_View(USER_ID, PROVIDER_SERVICE_MAP_ID))
                .thenReturn(spList);

        List<Object[]> parkingList = new ArrayList<>();
        parkingList.add(new Object[]{10, 20, "State", 30, "District", 40, "Block"});
        when(userParkingplaceMappingRepo.getUserParkingPlce(USER_ID)).thenReturn(parkingList);

        String result = loginService.getUserVanSpDetails(USER_ID, PROVIDER_SERVICE_MAP_ID);

        assertNotNull(result);
        assertTrue(result.contains("UserVanSpDetails"));
        assertTrue(result.contains("UserLocDetails"));
    }

    @Test
    void getUserVanSpDetails_emptyVanSpList_returnsEmptyUserVanSpDetails() {
        when(userVanSpDetails_View_Repo.getUserVanSpDetails_View(USER_ID, PROVIDER_SERVICE_MAP_ID))
                .thenReturn(new ArrayList<>());
        when(userParkingplaceMappingRepo.getUserParkingPlce(USER_ID))
                .thenReturn(Collections.emptyList());

        String result = loginService.getUserVanSpDetails(USER_ID, PROVIDER_SERVICE_MAP_ID);

        assertNotNull(result);
        assertTrue(result.contains("UserVanSpDetails"));
    }

    // -------------------- getUserSpokeDetails --------------------

    @Test
    void getUserSpokeDetails_withVans_returnsListIncludingAllEntry() {
        ArrayList<Object[]> vanList = new ArrayList<>();
        vanList.add(new Object[]{1, "Spoke Van 1"});
        when(masterVanRepo.getVanMaster(PROVIDER_SERVICE_MAP_ID)).thenReturn(vanList);

        String result = loginService.getUserSpokeDetails(PROVIDER_SERVICE_MAP_ID);

        assertNotNull(result);
        assertTrue(result.contains("All"));
        assertTrue(result.contains("Spoke Van 1"));
    }

    @Test
    void getUserSpokeDetails_noVans_returnsOnlyAllEntry() {
        when(masterVanRepo.getVanMaster(PROVIDER_SERVICE_MAP_ID)).thenReturn(new ArrayList<>());

        String result = loginService.getUserSpokeDetails(PROVIDER_SERVICE_MAP_ID);

        assertNotNull(result);
        assertTrue(result.contains("All"));
    }
}
