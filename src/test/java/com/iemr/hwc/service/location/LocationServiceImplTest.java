package com.iemr.hwc.service.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.choApp.Outreach;
import com.iemr.hwc.data.location.Country;
import com.iemr.hwc.data.location.CountryCityMaster;
import com.iemr.hwc.data.location.DistrictBranchMapping;
import com.iemr.hwc.repo.choApp.OutreachRepo;
import com.iemr.hwc.repo.location.CountryCityMasterRepo;
import com.iemr.hwc.repo.location.CountryMasterRepo;
import com.iemr.hwc.repo.location.DistrictBlockMasterRepo;
import com.iemr.hwc.repo.location.DistrictBranchMasterRepo;
import com.iemr.hwc.repo.location.DistrictMasterRepo;
import com.iemr.hwc.repo.location.LocationMasterRepo;
import com.iemr.hwc.repo.location.ParkingPlaceMasterRepo;
import com.iemr.hwc.repo.location.ServicePointMasterRepo;
import com.iemr.hwc.repo.location.StateMasterRepo;
import com.iemr.hwc.repo.location.V_GetLocDetailsFromSPidAndPSMidRepo;
import com.iemr.hwc.repo.location.V_getVanLocDetailsRepo;
import com.iemr.hwc.repo.location.V_get_prkngplc_dist_zone_state_from_spidRepo;
import com.iemr.hwc.repo.location.ZoneMasterRepo;
import com.iemr.hwc.repo.login.ServicePointVillageMappingRepo;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {
    @Mock
    private CountryCityMasterRepo countryCityMasterRepo;

    @Mock
    private CountryMasterRepo countryMasterRepo;

    @Mock
    private DistrictBlockMasterRepo districtBlockMasterRepo;

    @Mock
    private DistrictBranchMasterRepo districtBranchMasterRepo;

    @Mock
    private DistrictMasterRepo districtMasterRepo;

    @Mock
    private LocationMasterRepo locationMasterRepo;

    @InjectMocks
    private LocationServiceImpl locationServiceImpl;

    @Mock
    private OutreachRepo outreachRepo;

    @Mock
    private ParkingPlaceMasterRepo parkingPlaceMasterRepo;

    @Mock
    private ServicePointMasterRepo servicePointMasterRepo;

    @Mock
    private ServicePointVillageMappingRepo servicePointVillageMappingRepo;

    @Mock
    private StateMasterRepo stateMasterRepo;

    @Mock
    private V_GetLocDetailsFromSPidAndPSMidRepo v_GetLocDetailsFromSPidAndPSMidRepo;

    @Mock
    private V_getVanLocDetailsRepo v_getVanLocDetailsRepo;

    @Mock
    private V_get_prkngplc_dist_zone_state_from_spidRepo v_get_prkngplc_dist_zone_state_from_spidRepo;

    @Mock
    private ZoneMasterRepo zoneMasterRepo;

    @Test
    void testGetCountryList() {
        // Arrange
        when(countryMasterRepo.findAllCountries()).thenReturn(new ArrayList<>());

        // Act
        String actualCountryList = locationServiceImpl.getCountryList();

        // Assert
        verify(countryMasterRepo).findAllCountries();
        assertEquals("[]", actualCountryList);
    }

    @Test
    void testGetCountryList2() {
        // Arrange
        ArrayList<Country> countryList = new ArrayList<>();
        countryList.add(new Country());
        when(countryMasterRepo.findAllCountries()).thenReturn(countryList);

        // Act
        String actualCountryList = locationServiceImpl.getCountryList();

        // Assert
        verify(countryMasterRepo).findAllCountries();
        assertEquals("[{}]", actualCountryList);
    }

    @Test
    void testGetCountryList3() {
        // Arrange
        ArrayList<Country> countryList = new ArrayList<>();
        countryList.add(new Country());
        countryList.add(new Country());
        when(countryMasterRepo.findAllCountries()).thenReturn(countryList);

        // Act
        String actualCountryList = locationServiceImpl.getCountryList();

        // Assert
        verify(countryMasterRepo).findAllCountries();
        assertEquals("[{},{}]", actualCountryList);
    }

    @Test
    void testGetCountryCityList() {
        // Arrange
        when(countryCityMasterRepo.findByCountryIDAndDeleted(anyInt(), anyBoolean())).thenReturn(new ArrayList<>());

        // Act
        String actualCountryCityList = locationServiceImpl.getCountryCityList(1);

        // Assert
        verify(countryCityMasterRepo).findByCountryIDAndDeleted(eq(1), eq(false));
        assertEquals("[]", actualCountryCityList);
    }

    @Test
    void testGetCountryCityList2() {
        // Arrange
        ArrayList<CountryCityMaster> countryCityMasterList = new ArrayList<>();
        countryCityMasterList.add(new CountryCityMaster());
        when(countryCityMasterRepo.findByCountryIDAndDeleted(anyInt(), anyBoolean())).thenReturn(countryCityMasterList);

        // Act
        String actualCountryCityList = locationServiceImpl.getCountryCityList(1);

        // Assert
        verify(countryCityMasterRepo).findByCountryIDAndDeleted(eq(1), eq(false));
        assertEquals("[{}]", actualCountryCityList);
    }

    @Test
    void testGetCountryCityList3() {
        // Arrange
        ArrayList<CountryCityMaster> countryCityMasterList = new ArrayList<>();
        countryCityMasterList.add(new CountryCityMaster());
        countryCityMasterList.add(new CountryCityMaster());
        when(countryCityMasterRepo.findByCountryIDAndDeleted(anyInt(), anyBoolean())).thenReturn(countryCityMasterList);

        // Act
        String actualCountryCityList = locationServiceImpl.getCountryCityList(1);

        // Assert
        verify(countryCityMasterRepo).findByCountryIDAndDeleted(eq(1), eq(false));
        assertEquals("[{},{}]", actualCountryCityList);
    }

    @Test
    void testGetStateList() {
        // Arrange
        when(stateMasterRepo.getStateMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualStateList = locationServiceImpl.getStateList();

        // Assert
        verify(stateMasterRepo).getStateMaster();
        assertEquals("[]", actualStateList);
    }

    @Test
    void testGetZoneList() {
        // Arrange
        when(zoneMasterRepo.getZoneMaster(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualZoneList = locationServiceImpl.getZoneList(1);

        // Assert
        verify(zoneMasterRepo).getZoneMaster(isA(Integer.class));
        assertEquals("[]", actualZoneList);
    }

    @Test
    void testGetDistrictList() {
        // Arrange
        when(districtMasterRepo.getDistrictMaster(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualDistrictList = locationServiceImpl.getDistrictList(1);

        // Assert
        verify(districtMasterRepo).getDistrictMaster(isA(Integer.class));
        assertEquals("[]", actualDistrictList);
    }

    @Test
    void testGetLocationMasterData() {
        // Arrange
        when(locationMasterRepo.getLocationMaster(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualLocationMasterData = locationServiceImpl.getLocationMasterData(1);

        // Assert
        verify(locationMasterRepo).getLocationMaster(isA(Integer.class));
        assertEquals("{}", actualLocationMasterData);
    }

    @Test
    void testGetDistrictBlockList() {
        // Arrange
        when(districtBlockMasterRepo.getDistrictBlockMaster(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualDistrictBlockList = locationServiceImpl.getDistrictBlockList(1);

        // Assert
        verify(districtBlockMasterRepo).getDistrictBlockMaster(isA(Integer.class));
        assertEquals("[]", actualDistrictBlockList);
    }

    @Test
    void testGetVillageListByDistrictID() {
        // Arrange
        when(districtBlockMasterRepo.getDistrictBlockMaster(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualVillageListByDistrictID = locationServiceImpl.getVillageListByDistrictID(1);

        // Assert
        verify(districtBlockMasterRepo).getDistrictBlockMaster(isA(Integer.class));
        assertEquals("[]", actualVillageListByDistrictID);
    }

    @Test
    void testGetParkingPlaceList() {
        // Arrange
        when(parkingPlaceMasterRepo.getParkingPlaceMaster(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualParkingPlaceList = locationServiceImpl.getParkingPlaceList(1);

        // Assert
        verify(parkingPlaceMasterRepo).getParkingPlaceMaster(isA(Integer.class));
        assertEquals("[]", actualParkingPlaceList);
    }

    @Test
    void testGetServicePointPlaceList() {
        // Arrange
        when(servicePointMasterRepo.getServicePointMaster(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualServicePointPlaceList = locationServiceImpl.getServicePointPlaceList(1);

        // Assert
        verify(servicePointMasterRepo).getServicePointMaster(isA(Integer.class));
        assertEquals("[]", actualServicePointPlaceList);
    }

    @Test
    void testGetVillageMasterFromBlockID() {
        // Arrange
        when(districtBranchMasterRepo.findByBlockID(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualVillageMasterFromBlockID = locationServiceImpl.getVillageMasterFromBlockID(1);

        // Assert
        verify(districtBranchMasterRepo).findByBlockID(isA(Integer.class));
        assertEquals("[]", actualVillageMasterFromBlockID);
    }

    @Test
    void testGetLocDetails() {
        // Arrange
        when(servicePointVillageMappingRepo.getServicePointVillages(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(stateMasterRepo.getStateMaster()).thenReturn(new ArrayList<>());
        when(v_GetLocDetailsFromSPidAndPSMidRepo
                .findByServicepointidAndSpproviderservicemapidAndPpproviderservicemapidAndZdmproviderservicemapid(
                        Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn(new ArrayList<>());

        // Act
        String actualLocDetails = locationServiceImpl.getLocDetails(1, 1);

        // Assert
        verify(stateMasterRepo).getStateMaster();
        verify(v_GetLocDetailsFromSPidAndPSMidRepo)
                .findByServicepointidAndSpproviderservicemapidAndPpproviderservicemapidAndZdmproviderservicemapid(
                        isA(Integer.class), isA(Integer.class), isA(Integer.class), isA(Integer.class));
        verify(servicePointVillageMappingRepo).getServicePointVillages(isA(Integer.class));
        assertEquals("{\"stateMaster\":[],\"villageMaster\":[]}", actualLocDetails);
    }

    @Test
    void testGetLocDetailsNew() {
        // Arrange
        when(v_getVanLocDetailsRepo.getVanLocDetails(Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        when(stateMasterRepo.getStateMaster()).thenReturn(new ArrayList<>());

        // Act
        String actualLocDetailsNew = locationServiceImpl.getLocDetailsNew(1, 1, new JSONObject());

        // Assert
        verify(stateMasterRepo).getStateMaster();
        verify(v_getVanLocDetailsRepo).getVanLocDetails(isA(Integer.class));
        assertEquals("{\"otherLoc\":{},\"stateMaster\":[]}", actualLocDetailsNew);
    }

    @Test
    void testGetLocDetailsNew2() throws JSONException {
        // Arrange
        when(v_getVanLocDetailsRepo.getVanLocDetailsWithUserID(Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn(new ArrayList<>());
        when(stateMasterRepo.getStateMaster()).thenReturn(new ArrayList<>());
        JSONObject obj = mock(JSONObject.class);
        when(obj.getInt(Mockito.<String>any())).thenReturn(1);
        when(obj.has(Mockito.<String>any())).thenReturn(true);

        // Act
        String actualLocDetailsNew = locationServiceImpl.getLocDetailsNew(1, 1, obj);

        // Assert
        verify(stateMasterRepo).getStateMaster();
        verify(v_getVanLocDetailsRepo).getVanLocDetailsWithUserID(isA(Integer.class), isA(Integer.class));
        verify(obj).getInt(eq("userID"));
        verify(obj).has(eq("userID"));
        assertEquals("{\"otherLoc\":{},\"stateMaster\":[]}", actualLocDetailsNew);
    }

    @Test
    void testUpdateGeolocationByDistrictBranchID() {
        // Arrange
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
        when(districtBranchMasterRepo.findAllByDistrictBranchID(Mockito.<Integer>any())).thenReturn(districtBranchMapping);

        // Act
        int actualUpdateGeolocationByDistrictBranchIDResult = locationServiceImpl.updateGeolocationByDistrictBranchID(10.0d,
                10.0d, 1, "42 Main St");

        // Assert
        verify(districtBranchMasterRepo).findAllByDistrictBranchID(isA(Integer.class));
        assertEquals(101, actualUpdateGeolocationByDistrictBranchIDResult);
    }

    @Test
    void testUpdateGeolocationByDistrictBranchID2() {
        // Arrange
        DistrictBranchMapping districtBranchMapping = mock(DistrictBranchMapping.class);
        when(districtBranchMapping.getActive()).thenReturn(true);
        doNothing().when(districtBranchMapping).setActive(Mockito.<Boolean>any());
        doNothing().when(districtBranchMapping).setAddress(Mockito.<String>any());
        doNothing().when(districtBranchMapping).setBlockID(anyInt());
        doNothing().when(districtBranchMapping).setCreatedBy(Mockito.<String>any());
        doNothing().when(districtBranchMapping).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(districtBranchMapping).setDeleted(anyBoolean());
        doNothing().when(districtBranchMapping).setDistrictBranchID(anyInt());
        doNothing().when(districtBranchMapping).setHabitat(Mockito.<String>any());
        doNothing().when(districtBranchMapping).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(districtBranchMapping).setLatitude(Mockito.<Double>any());
        doNothing().when(districtBranchMapping).setLoginDistance(Mockito.<Integer>any());
        doNothing().when(districtBranchMapping).setLongitude(Mockito.<Double>any());
        doNothing().when(districtBranchMapping).setModifiedBy(Mockito.<String>any());
        doNothing().when(districtBranchMapping).setPanchayatName(Mockito.<String>any());
        doNothing().when(districtBranchMapping).setPinCode(Mockito.<String>any());
        doNothing().when(districtBranchMapping).setVillageName(Mockito.<String>any());
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
        when(districtBranchMasterRepo.findAllByDistrictBranchID(Mockito.<Integer>any())).thenReturn(districtBranchMapping);

        // Act
        int actualUpdateGeolocationByDistrictBranchIDResult = locationServiceImpl.updateGeolocationByDistrictBranchID(10.0d,
                10.0d, 1, "42 Main St");

        // Assert
        verify(districtBranchMapping).getActive();
        verify(districtBranchMapping).setActive(isA(Boolean.class));
        verify(districtBranchMapping).setAddress(eq("42 Main St"));
        verify(districtBranchMapping).setBlockID(eq(1));
        verify(districtBranchMapping).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(districtBranchMapping).setCreatedDate(isA(Timestamp.class));
        verify(districtBranchMapping).setDeleted(eq(true));
        verify(districtBranchMapping).setDistrictBranchID(eq(1));
        verify(districtBranchMapping).setHabitat(eq("Habitat"));
        verify(districtBranchMapping).setLastModDate(isA(Timestamp.class));
        verify(districtBranchMapping).setLatitude(isA(Double.class));
        verify(districtBranchMapping).setLoginDistance(isA(Integer.class));
        verify(districtBranchMapping).setLongitude(isA(Double.class));
        verify(districtBranchMapping).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(districtBranchMapping).setPanchayatName(eq("Panchayat Name"));
        verify(districtBranchMapping).setPinCode(eq("Pin Code"));
        verify(districtBranchMapping).setVillageName(eq("Village Name"));
        verify(districtBranchMasterRepo).findAllByDistrictBranchID(isA(Integer.class));
        assertEquals(101, actualUpdateGeolocationByDistrictBranchIDResult);
    }

    @Test
    void testUpdateGeolocationByDistrictBranchID3() {
        // Arrange
        DistrictBranchMapping districtBranchMapping = mock(DistrictBranchMapping.class);
        when(districtBranchMapping.getActive()).thenReturn(false);
        doNothing().when(districtBranchMapping).setActive(Mockito.<Boolean>any());
        doNothing().when(districtBranchMapping).setAddress(Mockito.<String>any());
        doNothing().when(districtBranchMapping).setBlockID(anyInt());
        doNothing().when(districtBranchMapping).setCreatedBy(Mockito.<String>any());
        doNothing().when(districtBranchMapping).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(districtBranchMapping).setDeleted(anyBoolean());
        doNothing().when(districtBranchMapping).setDistrictBranchID(anyInt());
        doNothing().when(districtBranchMapping).setHabitat(Mockito.<String>any());
        doNothing().when(districtBranchMapping).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(districtBranchMapping).setLatitude(Mockito.<Double>any());
        doNothing().when(districtBranchMapping).setLoginDistance(Mockito.<Integer>any());
        doNothing().when(districtBranchMapping).setLongitude(Mockito.<Double>any());
        doNothing().when(districtBranchMapping).setModifiedBy(Mockito.<String>any());
        doNothing().when(districtBranchMapping).setPanchayatName(Mockito.<String>any());
        doNothing().when(districtBranchMapping).setPinCode(Mockito.<String>any());
        doNothing().when(districtBranchMapping).setVillageName(Mockito.<String>any());
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
        when(districtBranchMasterRepo.updateGeolocationByDistrictBranchID(Mockito.<Double>any(), Mockito.<Double>any(),
                Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<Integer>any())).thenReturn(1);
        when(districtBranchMasterRepo.findAllByDistrictBranchID(Mockito.<Integer>any())).thenReturn(districtBranchMapping);

        // Act
        int actualUpdateGeolocationByDistrictBranchIDResult = locationServiceImpl.updateGeolocationByDistrictBranchID(10.0d,
                10.0d, 1, "42 Main St");

        // Assert
        verify(districtBranchMapping).getActive();
        verify(districtBranchMapping).setActive(isA(Boolean.class));
        verify(districtBranchMapping).setAddress(eq("42 Main St"));
        verify(districtBranchMapping).setBlockID(eq(1));
        verify(districtBranchMapping).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(districtBranchMapping).setCreatedDate(isA(Timestamp.class));
        verify(districtBranchMapping).setDeleted(eq(true));
        verify(districtBranchMapping).setDistrictBranchID(eq(1));
        verify(districtBranchMapping).setHabitat(eq("Habitat"));
        verify(districtBranchMapping).setLastModDate(isA(Timestamp.class));
        verify(districtBranchMapping).setLatitude(isA(Double.class));
        verify(districtBranchMapping).setLoginDistance(isA(Integer.class));
        verify(districtBranchMapping).setLongitude(isA(Double.class));
        verify(districtBranchMapping).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(districtBranchMapping).setPanchayatName(eq("Panchayat Name"));
        verify(districtBranchMapping).setPinCode(eq("Pin Code"));
        verify(districtBranchMapping).setVillageName(eq("Village Name"));
        verify(districtBranchMasterRepo).findAllByDistrictBranchID(isA(Integer.class));
        verify(districtBranchMasterRepo).updateGeolocationByDistrictBranchID(isA(Double.class), isA(Double.class),
                isA(Boolean.class), eq("42 Main St"), isA(Integer.class));
        assertEquals(1, actualUpdateGeolocationByDistrictBranchIDResult);
    }

    @Test
    void testGetOutreachProgramsList() {
        // Arrange
        when(outreachRepo.getOutreachListByStateID(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        String actualOutreachProgramsList = locationServiceImpl.getOutreachProgramsList(1);

        // Assert
        verify(outreachRepo).getOutreachListByStateID(isA(Integer.class));
        assertEquals("[]", actualOutreachProgramsList);
    }

    @Test
    void testGetOutreachProgramsList2() {
        // Arrange
        Outreach outreach = new Outreach();
        outreach.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        outreach.setCreatedDate(mock(Timestamp.class));
        outreach.setDeleted(true);
        outreach.setId(1);
        outreach.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        outreach.setModifiedDte(mock(Timestamp.class));
        outreach.setOutreachDesc("Outreach Desc");
        outreach.setOutreachType("Outreach Type");
        outreach.setStateID(1);

        ArrayList<Outreach> outreachList = new ArrayList<>();
        outreachList.add(outreach);
        when(outreachRepo.getOutreachListByStateID(Mockito.<Integer>any())).thenReturn(outreachList);

        // Act
        String actualOutreachProgramsList = locationServiceImpl.getOutreachProgramsList(1);

        // Assert
        verify(outreachRepo).getOutreachListByStateID(isA(Integer.class));
        assertEquals("[{\"id\":1,\"outreachType\":\"Outreach Type\",\"outreachDesc\":\"Outreach Desc\"}]",
                actualOutreachProgramsList);
    }

    @Test
    void testGetOutreachProgramsList3() {
        // Arrange
        Outreach outreach = new Outreach();
        outreach.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        outreach.setCreatedDate(mock(Timestamp.class));
        outreach.setDeleted(true);
        outreach.setId(1);
        outreach.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        outreach.setModifiedDte(mock(Timestamp.class));
        outreach.setOutreachDesc("Outreach Desc");
        outreach.setOutreachType("Outreach Type");
        outreach.setStateID(1);

        Outreach outreach2 = new Outreach();
        outreach2.setCreatedBy("Created By");
        outreach2.setCreatedDate(mock(Timestamp.class));
        outreach2.setDeleted(false);
        outreach2.setId(2);
        outreach2.setModifiedBy("Modified By");
        outreach2.setModifiedDte(mock(Timestamp.class));
        outreach2.setOutreachDesc("Outreach(id=");
        outreach2.setOutreachType("Outreach(id=");
        outreach2.setStateID(2);

        ArrayList<Outreach> outreachList = new ArrayList<>();
        outreachList.add(outreach2);
        outreachList.add(outreach);
        when(outreachRepo.getOutreachListByStateID(Mockito.<Integer>any())).thenReturn(outreachList);

        // Act
        String actualOutreachProgramsList = locationServiceImpl.getOutreachProgramsList(1);

        // Assert
        verify(outreachRepo).getOutreachListByStateID(isA(Integer.class));
        assertEquals(
                "[{\"id\":2,\"outreachType\":\"Outreach(id\\u003d\",\"outreachDesc\":\"Outreach(id\\u003d\"},{\"id\":1,\"outreachType"
                        + "\":\"Outreach Type\",\"outreachDesc\":\"Outreach Desc\"}]",
                actualOutreachProgramsList);
    }

    @Test
    void testGetOutreachProgramsList4() {
        // Arrange
        Outreach outreach = mock(Outreach.class);
        doNothing().when(outreach).setCreatedBy(Mockito.<String>any());
        doNothing().when(outreach).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(outreach).setDeleted(Mockito.<Boolean>any());
        doNothing().when(outreach).setId(Mockito.<Integer>any());
        doNothing().when(outreach).setModifiedBy(Mockito.<String>any());
        doNothing().when(outreach).setModifiedDte(Mockito.<Timestamp>any());
        doNothing().when(outreach).setOutreachDesc(Mockito.<String>any());
        doNothing().when(outreach).setOutreachType(Mockito.<String>any());
        doNothing().when(outreach).setStateID(Mockito.<Integer>any());
        outreach.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        outreach.setCreatedDate(mock(Timestamp.class));
        outreach.setDeleted(true);
        outreach.setId(1);
        outreach.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        outreach.setModifiedDte(mock(Timestamp.class));
        outreach.setOutreachDesc("Outreach Desc");
        outreach.setOutreachType("Outreach Type");
        outreach.setStateID(1);

        ArrayList<Outreach> outreachList = new ArrayList<>();
        outreachList.add(outreach);
        when(outreachRepo.getOutreachListByStateID(Mockito.<Integer>any())).thenReturn(outreachList);

        // Act
        String actualOutreachProgramsList = locationServiceImpl.getOutreachProgramsList(1);

        // Assert
        verify(outreach).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(outreach).setCreatedDate(isA(Timestamp.class));
        verify(outreach).setDeleted(isA(Boolean.class));
        verify(outreach).setId(isA(Integer.class));
        verify(outreach).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(outreach).setModifiedDte(isA(Timestamp.class));
        verify(outreach).setOutreachDesc(eq("Outreach Desc"));
        verify(outreach).setOutreachType(eq("Outreach Type"));
        verify(outreach).setStateID(isA(Integer.class));
        verify(outreachRepo).getOutreachListByStateID(isA(Integer.class));
        assertEquals("[{\"id\":null,\"outreachType\":null,\"outreachDesc\":null}]", actualOutreachProgramsList);
    }

    @Test
    void testGettersAndSetters() {
        LocationServiceImpl locationServiceImpl = new LocationServiceImpl();

        // Act
        locationServiceImpl.setDistrictBlockMasterRepo(mock(DistrictBlockMasterRepo.class));
        locationServiceImpl.setDistrictBranchMasterRepo(mock(DistrictBranchMasterRepo.class));
        locationServiceImpl.setDistrictMasterRepo(mock(DistrictMasterRepo.class));
        locationServiceImpl.setOutreachRepo(mock(OutreachRepo.class));
        locationServiceImpl.setParkingPlaceMasterRepo(mock(ParkingPlaceMasterRepo.class));
        locationServiceImpl.setServicePointMasterRepo(mock(ServicePointMasterRepo.class));
        locationServiceImpl.setServicePointVillageMappingRepo(mock(ServicePointVillageMappingRepo.class));
        locationServiceImpl.setStateMasterRepo(mock(StateMasterRepo.class));
        locationServiceImpl.setV_GetLocDetailsFromSPidAndPSMidRepo(mock(V_GetLocDetailsFromSPidAndPSMidRepo.class));
        locationServiceImpl
                .setV_get_prkngplc_dist_zone_state_from_spidRepo(mock(V_get_prkngplc_dist_zone_state_from_spidRepo.class));
        locationServiceImpl.setZoneMasterRepo(mock(ZoneMasterRepo.class));
    }
}
