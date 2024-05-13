package com.iemr.hwc.service.registrar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.login.Users;
import com.iemr.hwc.data.registrar.BenGovIdMapping;
import com.iemr.hwc.data.registrar.BeneficiaryData;
import com.iemr.hwc.data.registrar.BeneficiaryDemographicAdditional;
import com.iemr.hwc.data.registrar.BeneficiaryDemographicData;
import com.iemr.hwc.data.registrar.BeneficiaryImage;
import com.iemr.hwc.data.registrar.BeneficiaryPhoneMapping;
import com.iemr.hwc.data.registrar.FingerPrintDTO;
import com.iemr.hwc.data.registrar.UserBiometricsMapping;
import com.iemr.hwc.data.registrar.V_BenAdvanceSearch;
import com.iemr.hwc.repo.login.UserLoginRepo;
import com.iemr.hwc.repo.registrar.BeneficiaryDemographicAdditionalRepo;
import com.iemr.hwc.repo.registrar.BeneficiaryImageRepo;
import com.iemr.hwc.repo.registrar.RegistrarRepoBenData;
import com.iemr.hwc.repo.registrar.RegistrarRepoBenDemoData;
import com.iemr.hwc.repo.registrar.RegistrarRepoBenGovIdMapping;
import com.iemr.hwc.repo.registrar.RegistrarRepoBenPhoneMapData;
import com.iemr.hwc.repo.registrar.RegistrarRepoBeneficiaryDetails;
import com.iemr.hwc.repo.registrar.ReistrarRepoBenSearch;
import com.iemr.hwc.repo.registrar.UserBiometricsRepo;
import com.iemr.hwc.service.benFlowStatus.CommonBenStatusFlowServiceImpl;

@ExtendWith(MockitoExtension.class)
class RegistrarServiceImplTest {
    @Mock
    private BeneficiaryDemographicAdditionalRepo beneficiaryDemographicAdditionalRepo;

    @Mock
    private BeneficiaryImageRepo beneficiaryImageRepo;

    @Mock
    private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;

    @Mock
    private RegistrarRepoBenData registrarRepoBenData;

    @Mock
    private RegistrarRepoBenDemoData registrarRepoBenDemoData;

    @Mock
    private RegistrarRepoBenGovIdMapping registrarRepoBenGovIdMapping;

    @Mock
    private RegistrarRepoBenPhoneMapData registrarRepoBenPhoneMapData;

    @Mock
    private RegistrarRepoBeneficiaryDetails registrarRepoBeneficiaryDetails;

    @InjectMocks
    private RegistrarServiceImpl registrarServiceImpl;

    @Mock
    private ReistrarRepoBenSearch reistrarRepoBenSearch;

    @Mock
    private UserBiometricsRepo userBiometricsRepo;

    @Mock
    private UserLoginRepo userLoginRepo;

    @Test
    void testCreateBeneficiary() {
        // Arrange
        BeneficiaryData benData = new BeneficiaryData();
        benData.setAadharNo("Aadhar No");
        benData.setAge("Age");
        benData.setBenDemoData(new BeneficiaryDemographicData());
        benData.setBenPhoneMap(new HashSet<>());
        benData.setBeneficiaryID("Beneficiary ID");
        benData.setBeneficiaryName("Beneficiary Name");
        benData.setBeneficiaryRegID(1L);
        benData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benData.setCreatedDate(mock(Timestamp.class));
        benData.setDeleted(true);
        benData.setDob(mock(Timestamp.class));
        benData.setEmergencyRegistration("Emergency Registration");
        benData.setFatherName("Father Name");
        benData.setFirstName("Jane");
        benData.setFlowStatusFlag('A');
        benData.setGenderID((short) 1);
        benData.setGenderName("Gender Name");
        benData.setGovIdentityNo("Gov Identity No");
        benData.setGovIdentityTypeID((short) 1);
        benData.setImage("Ben Image");
        benData.setIsHivPos(true);
        benData.setLastModDate(mock(Timestamp.class));
        benData.setLastName("Doe");
        benData.setMaritalStatusID((short) 1);
        benData.setMiddleName("Middle Name");
        benData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benData.setPlaceOfWork("Place Of Work");
        benData.setRegisteredServiceID((short) 1);
        benData.setServicePointName("Service Point Name");
        benData.setSexuaOrientationID((short) 1);
        benData.setSourseOfInformation("Sourse Of Information");
        benData.setSpouseName("Spouse Name");
        benData.setStatusID((short) 1);
        benData.setTitleID((short) 1);

        BeneficiaryDemographicData benDemoData = new BeneficiaryDemographicData();
        benDemoData.setAddressLine1("42 Main St");
        benDemoData.setAddressLine2("42 Main St");
        benDemoData.setAddressLine3("42 Main St");
        benDemoData.setAddressLine4("42 Main St");
        benDemoData.setAddressLine5("42 Main St");
        benDemoData.setAreaID(1);
        benDemoData.setBenData(benData);
        benDemoData.setBenDemographicsID(1L);
        benDemoData.setBeneficiaryRegID(1L);
        benDemoData.setBiometricFilePath("/directory/foo.txt");
        benDemoData.setBlockID(1);
        benDemoData.setCityID(1);
        benDemoData.setCommunityID((short) 1);
        benDemoData.setCountryID(1);
        benDemoData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benDemoData.setCreatedDate(mock(Timestamp.class));
        benDemoData.setDeleted(true);
        benDemoData.setDistrictBranchID(1);
        benDemoData.setDistrictID(1);
        benDemoData.setEducationID((short) 1);
        benDemoData.setHealthCareWorkerID((short) 1);
        benDemoData.setIncomeStatusID((short) 1);
        benDemoData.setIsAddPermanent(true);
        benDemoData.setIsAddPresent(true);
        benDemoData.setIsBiometric(true);
        benDemoData.setIsPhoto(true);
        benDemoData.setLastModDate(mock(Timestamp.class));
        benDemoData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benDemoData.setOccupationID((short) 1);
        benDemoData.setPinCode("Pin Code");
        benDemoData.setPreferredLangID(1);
        benDemoData.setReligionID((short) 1);
        benDemoData.setServicePointID(1);
        benDemoData.setStateID(1);
        benDemoData.setZoneID(1);

        BeneficiaryData benData2 = new BeneficiaryData();
        benData2.setAadharNo("Aadhar No");
        benData2.setAge("Age");
        benData2.setBenDemoData(benDemoData);
        benData2.setBenPhoneMap(new HashSet<>());
        benData2.setBeneficiaryID("Beneficiary ID");
        benData2.setBeneficiaryName("Beneficiary Name");
        benData2.setBeneficiaryRegID(1L);
        benData2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benData2.setCreatedDate(mock(Timestamp.class));
        benData2.setDeleted(true);
        benData2.setDob(mock(Timestamp.class));
        benData2.setEmergencyRegistration("Emergency Registration");
        benData2.setFatherName("Father Name");
        benData2.setFirstName("Jane");
        benData2.setFlowStatusFlag('A');
        benData2.setGenderID((short) 1);
        benData2.setGenderName("Gender Name");
        benData2.setGovIdentityNo("Gov Identity No");
        benData2.setGovIdentityTypeID((short) 1);
        benData2.setImage("Ben Image");
        benData2.setIsHivPos(true);
        benData2.setLastModDate(mock(Timestamp.class));
        benData2.setLastName("Doe");
        benData2.setMaritalStatusID((short) 1);
        benData2.setMiddleName("Middle Name");
        benData2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benData2.setPlaceOfWork("Place Of Work");
        benData2.setRegisteredServiceID((short) 1);
        benData2.setServicePointName("Service Point Name");
        benData2.setSexuaOrientationID((short) 1);
        benData2.setSourseOfInformation("Sourse Of Information");
        benData2.setSpouseName("Spouse Name");
        benData2.setStatusID((short) 1);
        benData2.setTitleID((short) 1);

        BeneficiaryDemographicData benDemoData2 = new BeneficiaryDemographicData();
        benDemoData2.setAddressLine1("42 Main St");
        benDemoData2.setAddressLine2("42 Main St");
        benDemoData2.setAddressLine3("42 Main St");
        benDemoData2.setAddressLine4("42 Main St");
        benDemoData2.setAddressLine5("42 Main St");
        benDemoData2.setAreaID(1);
        benDemoData2.setBenData(benData2);
        benDemoData2.setBenDemographicsID(1L);
        benDemoData2.setBeneficiaryRegID(1L);
        benDemoData2.setBiometricFilePath("/directory/foo.txt");
        benDemoData2.setBlockID(1);
        benDemoData2.setCityID(1);
        benDemoData2.setCommunityID((short) 1);
        benDemoData2.setCountryID(1);
        benDemoData2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benDemoData2.setCreatedDate(mock(Timestamp.class));
        benDemoData2.setDeleted(true);
        benDemoData2.setDistrictBranchID(1);
        benDemoData2.setDistrictID(1);
        benDemoData2.setEducationID((short) 1);
        benDemoData2.setHealthCareWorkerID((short) 1);
        benDemoData2.setIncomeStatusID((short) 1);
        benDemoData2.setIsAddPermanent(true);
        benDemoData2.setIsAddPresent(true);
        benDemoData2.setIsBiometric(true);
        benDemoData2.setIsPhoto(true);
        benDemoData2.setLastModDate(mock(Timestamp.class));
        benDemoData2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benDemoData2.setOccupationID((short) 1);
        benDemoData2.setPinCode("Pin Code");
        benDemoData2.setPreferredLangID(1);
        benDemoData2.setReligionID((short) 1);
        benDemoData2.setServicePointID(1);
        benDemoData2.setStateID(1);
        benDemoData2.setZoneID(1);

        BeneficiaryData beneficiaryData = new BeneficiaryData();
        beneficiaryData.setAadharNo("Aadhar No");
        beneficiaryData.setAge("Age");
        beneficiaryData.setBenDemoData(benDemoData2);
        beneficiaryData.setBenPhoneMap(new HashSet<>());
        beneficiaryData.setBeneficiaryID("Beneficiary ID");
        beneficiaryData.setBeneficiaryName("Beneficiary Name");
        beneficiaryData.setBeneficiaryRegID(1L);
        beneficiaryData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryData.setCreatedDate(mock(Timestamp.class));
        beneficiaryData.setDeleted(true);
        beneficiaryData.setDob(mock(Timestamp.class));
        beneficiaryData.setEmergencyRegistration("Emergency Registration");
        beneficiaryData.setFatherName("Father Name");
        beneficiaryData.setFirstName("Jane");
        beneficiaryData.setFlowStatusFlag('A');
        beneficiaryData.setGenderID((short) 1);
        beneficiaryData.setGenderName("Gender Name");
        beneficiaryData.setGovIdentityNo("Gov Identity No");
        beneficiaryData.setGovIdentityTypeID((short) 1);
        beneficiaryData.setImage("Ben Image");
        beneficiaryData.setIsHivPos(true);
        beneficiaryData.setLastModDate(mock(Timestamp.class));
        beneficiaryData.setLastName("Doe");
        beneficiaryData.setMaritalStatusID((short) 1);
        beneficiaryData.setMiddleName("Middle Name");
        beneficiaryData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryData.setPlaceOfWork("Place Of Work");
        beneficiaryData.setRegisteredServiceID((short) 1);
        beneficiaryData.setServicePointName("Service Point Name");
        beneficiaryData.setSexuaOrientationID((short) 1);
        beneficiaryData.setSourseOfInformation("Sourse Of Information");
        beneficiaryData.setSpouseName("Spouse Name");
        beneficiaryData.setStatusID((short) 1);
        beneficiaryData.setTitleID((short) 1);
        when(registrarRepoBenData.save(Mockito.<BeneficiaryData>any())).thenReturn(beneficiaryData);

        // Act
        BeneficiaryData actualCreateBeneficiaryResult = registrarServiceImpl.createBeneficiary(new JsonObject());

        // Assert
        verify(registrarRepoBenData).save(isA(BeneficiaryData.class));
        assertSame(beneficiaryData, actualCreateBeneficiaryResult);
    }

    @Test
    void testCreateBeneficiaryDemographic() {
        // Arrange
        BeneficiaryDemographicData benDemoData = new BeneficiaryDemographicData();
        benDemoData.setAddressLine1("42 Main St");
        benDemoData.setAddressLine2("42 Main St");
        benDemoData.setAddressLine3("42 Main St");
        benDemoData.setAddressLine4("42 Main St");
        benDemoData.setAddressLine5("42 Main St");
        benDemoData.setAreaID(1);
        benDemoData.setBenData(new BeneficiaryData());
        benDemoData.setBenDemographicsID(1L);
        benDemoData.setBeneficiaryRegID(1L);
        benDemoData.setBiometricFilePath("/directory/foo.txt");
        benDemoData.setBlockID(1);
        benDemoData.setCityID(1);
        benDemoData.setCommunityID((short) 1);
        benDemoData.setCountryID(1);
        benDemoData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benDemoData.setCreatedDate(mock(Timestamp.class));
        benDemoData.setDeleted(true);
        benDemoData.setDistrictBranchID(1);
        benDemoData.setDistrictID(1);
        benDemoData.setEducationID((short) 1);
        benDemoData.setHealthCareWorkerID((short) 1);
        benDemoData.setIncomeStatusID((short) 1);
        benDemoData.setIsAddPermanent(true);
        benDemoData.setIsAddPresent(true);
        benDemoData.setIsBiometric(true);
        benDemoData.setIsPhoto(true);
        benDemoData.setLastModDate(mock(Timestamp.class));
        benDemoData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benDemoData.setOccupationID((short) 1);
        benDemoData.setPinCode("Pin Code");
        benDemoData.setPreferredLangID(1);
        benDemoData.setReligionID((short) 1);
        benDemoData.setServicePointID(1);
        benDemoData.setStateID(1);
        benDemoData.setZoneID(1);

        BeneficiaryData benData = new BeneficiaryData();
        benData.setAadharNo("Aadhar No");
        benData.setAge("Age");
        benData.setBenDemoData(benDemoData);
        benData.setBenPhoneMap(new HashSet<>());
        benData.setBeneficiaryID("Beneficiary ID");
        benData.setBeneficiaryName("Beneficiary Name");
        benData.setBeneficiaryRegID(1L);
        benData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benData.setCreatedDate(mock(Timestamp.class));
        benData.setDeleted(true);
        benData.setDob(mock(Timestamp.class));
        benData.setEmergencyRegistration("Emergency Registration");
        benData.setFatherName("Father Name");
        benData.setFirstName("Jane");
        benData.setFlowStatusFlag('A');
        benData.setGenderID((short) 1);
        benData.setGenderName("Gender Name");
        benData.setGovIdentityNo("Gov Identity No");
        benData.setGovIdentityTypeID((short) 1);
        benData.setImage("Ben Image");
        benData.setIsHivPos(true);
        benData.setLastModDate(mock(Timestamp.class));
        benData.setLastName("Doe");
        benData.setMaritalStatusID((short) 1);
        benData.setMiddleName("Middle Name");
        benData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benData.setPlaceOfWork("Place Of Work");
        benData.setRegisteredServiceID((short) 1);
        benData.setServicePointName("Service Point Name");
        benData.setSexuaOrientationID((short) 1);
        benData.setSourseOfInformation("Sourse Of Information");
        benData.setSpouseName("Spouse Name");
        benData.setStatusID((short) 1);
        benData.setTitleID((short) 1);

        BeneficiaryDemographicData benDemoData2 = new BeneficiaryDemographicData();
        benDemoData2.setAddressLine1("42 Main St");
        benDemoData2.setAddressLine2("42 Main St");
        benDemoData2.setAddressLine3("42 Main St");
        benDemoData2.setAddressLine4("42 Main St");
        benDemoData2.setAddressLine5("42 Main St");
        benDemoData2.setAreaID(1);
        benDemoData2.setBenData(benData);
        benDemoData2.setBenDemographicsID(1L);
        benDemoData2.setBeneficiaryRegID(1L);
        benDemoData2.setBiometricFilePath("/directory/foo.txt");
        benDemoData2.setBlockID(1);
        benDemoData2.setCityID(1);
        benDemoData2.setCommunityID((short) 1);
        benDemoData2.setCountryID(1);
        benDemoData2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benDemoData2.setCreatedDate(mock(Timestamp.class));
        benDemoData2.setDeleted(true);
        benDemoData2.setDistrictBranchID(1);
        benDemoData2.setDistrictID(1);
        benDemoData2.setEducationID((short) 1);
        benDemoData2.setHealthCareWorkerID((short) 1);
        benDemoData2.setIncomeStatusID((short) 1);
        benDemoData2.setIsAddPermanent(true);
        benDemoData2.setIsAddPresent(true);
        benDemoData2.setIsBiometric(true);
        benDemoData2.setIsPhoto(true);
        benDemoData2.setLastModDate(mock(Timestamp.class));
        benDemoData2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benDemoData2.setOccupationID((short) 1);
        benDemoData2.setPinCode("Pin Code");
        benDemoData2.setPreferredLangID(1);
        benDemoData2.setReligionID((short) 1);
        benDemoData2.setServicePointID(1);
        benDemoData2.setStateID(1);
        benDemoData2.setZoneID(1);

        BeneficiaryData benData2 = new BeneficiaryData();
        benData2.setAadharNo("Aadhar No");
        benData2.setAge("Age");
        benData2.setBenDemoData(benDemoData2);
        benData2.setBenPhoneMap(new HashSet<>());
        benData2.setBeneficiaryID("Beneficiary ID");
        benData2.setBeneficiaryName("Beneficiary Name");
        benData2.setBeneficiaryRegID(1L);
        benData2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benData2.setCreatedDate(mock(Timestamp.class));
        benData2.setDeleted(true);
        benData2.setDob(mock(Timestamp.class));
        benData2.setEmergencyRegistration("Emergency Registration");
        benData2.setFatherName("Father Name");
        benData2.setFirstName("Jane");
        benData2.setFlowStatusFlag('A');
        benData2.setGenderID((short) 1);
        benData2.setGenderName("Gender Name");
        benData2.setGovIdentityNo("Gov Identity No");
        benData2.setGovIdentityTypeID((short) 1);
        benData2.setImage("Ben Image");
        benData2.setIsHivPos(true);
        benData2.setLastModDate(mock(Timestamp.class));
        benData2.setLastName("Doe");
        benData2.setMaritalStatusID((short) 1);
        benData2.setMiddleName("Middle Name");
        benData2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benData2.setPlaceOfWork("Place Of Work");
        benData2.setRegisteredServiceID((short) 1);
        benData2.setServicePointName("Service Point Name");
        benData2.setSexuaOrientationID((short) 1);
        benData2.setSourseOfInformation("Sourse Of Information");
        benData2.setSpouseName("Spouse Name");
        benData2.setStatusID((short) 1);
        benData2.setTitleID((short) 1);

        BeneficiaryDemographicData beneficiaryDemographicData = new BeneficiaryDemographicData();
        beneficiaryDemographicData.setAddressLine1("42 Main St");
        beneficiaryDemographicData.setAddressLine2("42 Main St");
        beneficiaryDemographicData.setAddressLine3("42 Main St");
        beneficiaryDemographicData.setAddressLine4("42 Main St");
        beneficiaryDemographicData.setAddressLine5("42 Main St");
        beneficiaryDemographicData.setAreaID(1);
        beneficiaryDemographicData.setBenData(benData2);
        beneficiaryDemographicData.setBenDemographicsID(1L);
        beneficiaryDemographicData.setBeneficiaryRegID(1L);
        beneficiaryDemographicData.setBiometricFilePath("/directory/foo.txt");
        beneficiaryDemographicData.setBlockID(1);
        beneficiaryDemographicData.setCityID(1);
        beneficiaryDemographicData.setCommunityID((short) 1);
        beneficiaryDemographicData.setCountryID(1);
        beneficiaryDemographicData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryDemographicData.setCreatedDate(mock(Timestamp.class));
        beneficiaryDemographicData.setDeleted(true);
        beneficiaryDemographicData.setDistrictBranchID(1);
        beneficiaryDemographicData.setDistrictID(1);
        beneficiaryDemographicData.setEducationID((short) 1);
        beneficiaryDemographicData.setHealthCareWorkerID((short) 1);
        beneficiaryDemographicData.setIncomeStatusID((short) 1);
        beneficiaryDemographicData.setIsAddPermanent(true);
        beneficiaryDemographicData.setIsAddPresent(true);
        beneficiaryDemographicData.setIsBiometric(true);
        beneficiaryDemographicData.setIsPhoto(true);
        beneficiaryDemographicData.setLastModDate(mock(Timestamp.class));
        beneficiaryDemographicData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryDemographicData.setOccupationID((short) 1);
        beneficiaryDemographicData.setPinCode("Pin Code");
        beneficiaryDemographicData.setPreferredLangID(1);
        beneficiaryDemographicData.setReligionID((short) 1);
        beneficiaryDemographicData.setServicePointID(1);
        beneficiaryDemographicData.setStateID(1);
        beneficiaryDemographicData.setZoneID(1);
        when(registrarRepoBenDemoData.save(Mockito.<BeneficiaryDemographicData>any()))
                .thenReturn(beneficiaryDemographicData);

        // Act
        Long actualCreateBeneficiaryDemographicResult = registrarServiceImpl.createBeneficiaryDemographic(new JsonObject(),
                1L);

        // Assert
        verify(registrarRepoBenDemoData).save(isA(BeneficiaryDemographicData.class));
        assertEquals(1L, actualCreateBeneficiaryDemographicResult.longValue());
    }

    @Test
    void testCreateBeneficiaryDemographicAdditional() {
        // Arrange
        BeneficiaryDemographicAdditional beneficiaryDemographicAdditional = new BeneficiaryDemographicAdditional();
        beneficiaryDemographicAdditional.setAccountNo("3");
        beneficiaryDemographicAdditional.setBankName("Bank Name");
        beneficiaryDemographicAdditional.setBenDemoAdditionalID(1L);
        beneficiaryDemographicAdditional.setBeneficiaryRegID(1L);
        beneficiaryDemographicAdditional.setBranchName("janedoe/featurebranch");
        beneficiaryDemographicAdditional.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryDemographicAdditional.setCreatedDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setDeleted(true);
        beneficiaryDemographicAdditional.setEmailID("jane.doe@example.org");
        beneficiaryDemographicAdditional.setLastModDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setLiteracyStatus("Literacy Status");
        beneficiaryDemographicAdditional.setMarrigeDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryDemographicAdditional.setMotherName("Mother Name");
        beneficiaryDemographicAdditional.setProcessed('A');
        beneficiaryDemographicAdditional.setiFSCCode("I FSCCode");
        when(beneficiaryDemographicAdditionalRepo.save(Mockito.<BeneficiaryDemographicAdditional>any()))
                .thenReturn(beneficiaryDemographicAdditional);

        // Act
        Long actualCreateBeneficiaryDemographicAdditionalResult = registrarServiceImpl
                .createBeneficiaryDemographicAdditional(new JsonObject(), 1L);

        // Assert
        verify(beneficiaryDemographicAdditionalRepo).save(isA(BeneficiaryDemographicAdditional.class));
        assertEquals(1L, actualCreateBeneficiaryDemographicAdditionalResult.longValue());
    }

    @Test
    void testCreateBeneficiaryDemographicAdditional2() {
        // Arrange
        BeneficiaryDemographicAdditional beneficiaryDemographicAdditional = mock(BeneficiaryDemographicAdditional.class);
        when(beneficiaryDemographicAdditional.getBenDemoAdditionalID()).thenReturn(1L);
        doNothing().when(beneficiaryDemographicAdditional).setAccountNo(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setBankName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setBenDemoAdditionalID(Mockito.<Long>any());
        doNothing().when(beneficiaryDemographicAdditional).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(beneficiaryDemographicAdditional).setBranchName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setCreatedBy(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setDeleted(Mockito.<Boolean>any());
        doNothing().when(beneficiaryDemographicAdditional).setEmailID(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setLiteracyStatus(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setMarrigeDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setModifiedBy(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setMotherName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setProcessed(Mockito.<Character>any());
        doNothing().when(beneficiaryDemographicAdditional).setiFSCCode(Mockito.<String>any());
        beneficiaryDemographicAdditional.setAccountNo("3");
        beneficiaryDemographicAdditional.setBankName("Bank Name");
        beneficiaryDemographicAdditional.setBenDemoAdditionalID(1L);
        beneficiaryDemographicAdditional.setBeneficiaryRegID(1L);
        beneficiaryDemographicAdditional.setBranchName("janedoe/featurebranch");
        beneficiaryDemographicAdditional.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryDemographicAdditional.setCreatedDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setDeleted(true);
        beneficiaryDemographicAdditional.setEmailID("jane.doe@example.org");
        beneficiaryDemographicAdditional.setLastModDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setLiteracyStatus("Literacy Status");
        beneficiaryDemographicAdditional.setMarrigeDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryDemographicAdditional.setMotherName("Mother Name");
        beneficiaryDemographicAdditional.setProcessed('A');
        beneficiaryDemographicAdditional.setiFSCCode("I FSCCode");
        when(beneficiaryDemographicAdditionalRepo.save(Mockito.<BeneficiaryDemographicAdditional>any()))
                .thenReturn(beneficiaryDemographicAdditional);

        // Act
        Long actualCreateBeneficiaryDemographicAdditionalResult = registrarServiceImpl
                .createBeneficiaryDemographicAdditional(new JsonObject(), 1L);

        // Assert
        verify(beneficiaryDemographicAdditional).getBenDemoAdditionalID();
        verify(beneficiaryDemographicAdditional).setAccountNo(eq("3"));
        verify(beneficiaryDemographicAdditional).setBankName(eq("Bank Name"));
        verify(beneficiaryDemographicAdditional).setBenDemoAdditionalID(isA(Long.class));
        verify(beneficiaryDemographicAdditional).setBeneficiaryRegID(isA(Long.class));
        verify(beneficiaryDemographicAdditional).setBranchName(eq("janedoe/featurebranch"));
        verify(beneficiaryDemographicAdditional).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(beneficiaryDemographicAdditional).setCreatedDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setDeleted(isA(Boolean.class));
        verify(beneficiaryDemographicAdditional).setEmailID(eq("jane.doe@example.org"));
        verify(beneficiaryDemographicAdditional).setLastModDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setLiteracyStatus(eq("Literacy Status"));
        verify(beneficiaryDemographicAdditional).setMarrigeDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(beneficiaryDemographicAdditional).setMotherName(eq("Mother Name"));
        verify(beneficiaryDemographicAdditional).setProcessed(isA(Character.class));
        verify(beneficiaryDemographicAdditional).setiFSCCode(eq("I FSCCode"));
        verify(beneficiaryDemographicAdditionalRepo).save(isA(BeneficiaryDemographicAdditional.class));
        assertEquals(1L, actualCreateBeneficiaryDemographicAdditionalResult.longValue());
    }

    @Test
    void testCreateBeneficiaryDemographicAdditional3() {
        // Arrange
        BeneficiaryDemographicAdditional beneficiaryDemographicAdditional = mock(BeneficiaryDemographicAdditional.class);
        when(beneficiaryDemographicAdditional.getBenDemoAdditionalID()).thenReturn(1L);
        doNothing().when(beneficiaryDemographicAdditional).setAccountNo(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setBankName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setBenDemoAdditionalID(Mockito.<Long>any());
        doNothing().when(beneficiaryDemographicAdditional).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(beneficiaryDemographicAdditional).setBranchName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setCreatedBy(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setDeleted(Mockito.<Boolean>any());
        doNothing().when(beneficiaryDemographicAdditional).setEmailID(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setLiteracyStatus(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setMarrigeDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setModifiedBy(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setMotherName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setProcessed(Mockito.<Character>any());
        doNothing().when(beneficiaryDemographicAdditional).setiFSCCode(Mockito.<String>any());
        beneficiaryDemographicAdditional.setAccountNo("3");
        beneficiaryDemographicAdditional.setBankName("Bank Name");
        beneficiaryDemographicAdditional.setBenDemoAdditionalID(1L);
        beneficiaryDemographicAdditional.setBeneficiaryRegID(1L);
        beneficiaryDemographicAdditional.setBranchName("janedoe/featurebranch");
        beneficiaryDemographicAdditional.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryDemographicAdditional.setCreatedDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setDeleted(true);
        beneficiaryDemographicAdditional.setEmailID("jane.doe@example.org");
        beneficiaryDemographicAdditional.setLastModDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setLiteracyStatus("Literacy Status");
        beneficiaryDemographicAdditional.setMarrigeDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryDemographicAdditional.setMotherName("Mother Name");
        beneficiaryDemographicAdditional.setProcessed('A');
        beneficiaryDemographicAdditional.setiFSCCode("I FSCCode");
        when(beneficiaryDemographicAdditionalRepo.save(Mockito.<BeneficiaryDemographicAdditional>any()))
                .thenReturn(beneficiaryDemographicAdditional);

        JsonObject benD = new JsonObject();
        benD.addProperty("literacyStatus", "42");

        // Act
        Long actualCreateBeneficiaryDemographicAdditionalResult = registrarServiceImpl
                .createBeneficiaryDemographicAdditional(benD, 1L);

        // Assert
        verify(beneficiaryDemographicAdditional).getBenDemoAdditionalID();
        verify(beneficiaryDemographicAdditional).setAccountNo(eq("3"));
        verify(beneficiaryDemographicAdditional).setBankName(eq("Bank Name"));
        verify(beneficiaryDemographicAdditional).setBenDemoAdditionalID(isA(Long.class));
        verify(beneficiaryDemographicAdditional).setBeneficiaryRegID(isA(Long.class));
        verify(beneficiaryDemographicAdditional).setBranchName(eq("janedoe/featurebranch"));
        verify(beneficiaryDemographicAdditional).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(beneficiaryDemographicAdditional).setCreatedDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setDeleted(isA(Boolean.class));
        verify(beneficiaryDemographicAdditional).setEmailID(eq("jane.doe@example.org"));
        verify(beneficiaryDemographicAdditional).setLastModDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setLiteracyStatus(eq("Literacy Status"));
        verify(beneficiaryDemographicAdditional).setMarrigeDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(beneficiaryDemographicAdditional).setMotherName(eq("Mother Name"));
        verify(beneficiaryDemographicAdditional).setProcessed(isA(Character.class));
        verify(beneficiaryDemographicAdditional).setiFSCCode(eq("I FSCCode"));
        verify(beneficiaryDemographicAdditionalRepo).save(isA(BeneficiaryDemographicAdditional.class));
        assertEquals(1L, actualCreateBeneficiaryDemographicAdditionalResult.longValue());
    }

    @Test
    void testCreateBeneficiaryDemographicAdditional4() {
        // Arrange
        BeneficiaryDemographicAdditional beneficiaryDemographicAdditional = mock(BeneficiaryDemographicAdditional.class);
        when(beneficiaryDemographicAdditional.getBenDemoAdditionalID()).thenReturn(1L);
        doNothing().when(beneficiaryDemographicAdditional).setAccountNo(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setBankName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setBenDemoAdditionalID(Mockito.<Long>any());
        doNothing().when(beneficiaryDemographicAdditional).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(beneficiaryDemographicAdditional).setBranchName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setCreatedBy(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setDeleted(Mockito.<Boolean>any());
        doNothing().when(beneficiaryDemographicAdditional).setEmailID(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setLiteracyStatus(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setMarrigeDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setModifiedBy(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setMotherName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setProcessed(Mockito.<Character>any());
        doNothing().when(beneficiaryDemographicAdditional).setiFSCCode(Mockito.<String>any());
        beneficiaryDemographicAdditional.setAccountNo("3");
        beneficiaryDemographicAdditional.setBankName("Bank Name");
        beneficiaryDemographicAdditional.setBenDemoAdditionalID(1L);
        beneficiaryDemographicAdditional.setBeneficiaryRegID(1L);
        beneficiaryDemographicAdditional.setBranchName("janedoe/featurebranch");
        beneficiaryDemographicAdditional.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryDemographicAdditional.setCreatedDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setDeleted(true);
        beneficiaryDemographicAdditional.setEmailID("jane.doe@example.org");
        beneficiaryDemographicAdditional.setLastModDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setLiteracyStatus("Literacy Status");
        beneficiaryDemographicAdditional.setMarrigeDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryDemographicAdditional.setMotherName("Mother Name");
        beneficiaryDemographicAdditional.setProcessed('A');
        beneficiaryDemographicAdditional.setiFSCCode("I FSCCode");
        when(beneficiaryDemographicAdditionalRepo.save(Mockito.<BeneficiaryDemographicAdditional>any()))
                .thenReturn(beneficiaryDemographicAdditional);

        JsonObject benD = new JsonObject();
        benD.addProperty("literacyStatus", Integer.valueOf(1));

        // Act
        Long actualCreateBeneficiaryDemographicAdditionalResult = registrarServiceImpl
                .createBeneficiaryDemographicAdditional(benD, 1L);

        // Assert
        verify(beneficiaryDemographicAdditional).getBenDemoAdditionalID();
        verify(beneficiaryDemographicAdditional).setAccountNo(eq("3"));
        verify(beneficiaryDemographicAdditional).setBankName(eq("Bank Name"));
        verify(beneficiaryDemographicAdditional).setBenDemoAdditionalID(isA(Long.class));
        verify(beneficiaryDemographicAdditional).setBeneficiaryRegID(isA(Long.class));
        verify(beneficiaryDemographicAdditional).setBranchName(eq("janedoe/featurebranch"));
        verify(beneficiaryDemographicAdditional).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(beneficiaryDemographicAdditional).setCreatedDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setDeleted(isA(Boolean.class));
        verify(beneficiaryDemographicAdditional).setEmailID(eq("jane.doe@example.org"));
        verify(beneficiaryDemographicAdditional).setLastModDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setLiteracyStatus(eq("Literacy Status"));
        verify(beneficiaryDemographicAdditional).setMarrigeDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(beneficiaryDemographicAdditional).setMotherName(eq("Mother Name"));
        verify(beneficiaryDemographicAdditional).setProcessed(isA(Character.class));
        verify(beneficiaryDemographicAdditional).setiFSCCode(eq("I FSCCode"));
        verify(beneficiaryDemographicAdditionalRepo).save(isA(BeneficiaryDemographicAdditional.class));
        assertEquals(1L, actualCreateBeneficiaryDemographicAdditionalResult.longValue());
    }

    @Test
    void testCreateBeneficiaryDemographicAdditional5() {
        // Arrange
        BeneficiaryDemographicAdditional beneficiaryDemographicAdditional = mock(BeneficiaryDemographicAdditional.class);
        when(beneficiaryDemographicAdditional.getBenDemoAdditionalID()).thenReturn(1L);
        doNothing().when(beneficiaryDemographicAdditional).setAccountNo(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setBankName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setBenDemoAdditionalID(Mockito.<Long>any());
        doNothing().when(beneficiaryDemographicAdditional).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(beneficiaryDemographicAdditional).setBranchName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setCreatedBy(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setDeleted(Mockito.<Boolean>any());
        doNothing().when(beneficiaryDemographicAdditional).setEmailID(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setLiteracyStatus(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setMarrigeDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setModifiedBy(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setMotherName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setProcessed(Mockito.<Character>any());
        doNothing().when(beneficiaryDemographicAdditional).setiFSCCode(Mockito.<String>any());
        beneficiaryDemographicAdditional.setAccountNo("3");
        beneficiaryDemographicAdditional.setBankName("Bank Name");
        beneficiaryDemographicAdditional.setBenDemoAdditionalID(1L);
        beneficiaryDemographicAdditional.setBeneficiaryRegID(1L);
        beneficiaryDemographicAdditional.setBranchName("janedoe/featurebranch");
        beneficiaryDemographicAdditional.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryDemographicAdditional.setCreatedDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setDeleted(true);
        beneficiaryDemographicAdditional.setEmailID("jane.doe@example.org");
        beneficiaryDemographicAdditional.setLastModDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setLiteracyStatus("Literacy Status");
        beneficiaryDemographicAdditional.setMarrigeDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryDemographicAdditional.setMotherName("Mother Name");
        beneficiaryDemographicAdditional.setProcessed('A');
        beneficiaryDemographicAdditional.setiFSCCode("I FSCCode");
        when(beneficiaryDemographicAdditionalRepo.save(Mockito.<BeneficiaryDemographicAdditional>any()))
                .thenReturn(beneficiaryDemographicAdditional);

        JsonObject benD = new JsonObject();
        benD.addProperty("literacyStatus", true);

        // Act
        Long actualCreateBeneficiaryDemographicAdditionalResult = registrarServiceImpl
                .createBeneficiaryDemographicAdditional(benD, 1L);

        // Assert
        verify(beneficiaryDemographicAdditional).getBenDemoAdditionalID();
        verify(beneficiaryDemographicAdditional).setAccountNo(eq("3"));
        verify(beneficiaryDemographicAdditional).setBankName(eq("Bank Name"));
        verify(beneficiaryDemographicAdditional).setBenDemoAdditionalID(isA(Long.class));
        verify(beneficiaryDemographicAdditional).setBeneficiaryRegID(isA(Long.class));
        verify(beneficiaryDemographicAdditional).setBranchName(eq("janedoe/featurebranch"));
        verify(beneficiaryDemographicAdditional).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(beneficiaryDemographicAdditional).setCreatedDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setDeleted(isA(Boolean.class));
        verify(beneficiaryDemographicAdditional).setEmailID(eq("jane.doe@example.org"));
        verify(beneficiaryDemographicAdditional).setLastModDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setLiteracyStatus(eq("Literacy Status"));
        verify(beneficiaryDemographicAdditional).setMarrigeDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(beneficiaryDemographicAdditional).setMotherName(eq("Mother Name"));
        verify(beneficiaryDemographicAdditional).setProcessed(isA(Character.class));
        verify(beneficiaryDemographicAdditional).setiFSCCode(eq("I FSCCode"));
        verify(beneficiaryDemographicAdditionalRepo).save(isA(BeneficiaryDemographicAdditional.class));
        assertEquals(1L, actualCreateBeneficiaryDemographicAdditionalResult.longValue());
    }

    @Test
    void testCreateBeneficiaryDemographicAdditional6() {
        // Arrange
        BeneficiaryDemographicAdditional beneficiaryDemographicAdditional = mock(BeneficiaryDemographicAdditional.class);
        when(beneficiaryDemographicAdditional.getBenDemoAdditionalID()).thenReturn(1L);
        doNothing().when(beneficiaryDemographicAdditional).setAccountNo(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setBankName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setBenDemoAdditionalID(Mockito.<Long>any());
        doNothing().when(beneficiaryDemographicAdditional).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(beneficiaryDemographicAdditional).setBranchName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setCreatedBy(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setDeleted(Mockito.<Boolean>any());
        doNothing().when(beneficiaryDemographicAdditional).setEmailID(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setLiteracyStatus(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setMarrigeDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setModifiedBy(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setMotherName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setProcessed(Mockito.<Character>any());
        doNothing().when(beneficiaryDemographicAdditional).setiFSCCode(Mockito.<String>any());
        beneficiaryDemographicAdditional.setAccountNo("3");
        beneficiaryDemographicAdditional.setBankName("Bank Name");
        beneficiaryDemographicAdditional.setBenDemoAdditionalID(1L);
        beneficiaryDemographicAdditional.setBeneficiaryRegID(1L);
        beneficiaryDemographicAdditional.setBranchName("janedoe/featurebranch");
        beneficiaryDemographicAdditional.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryDemographicAdditional.setCreatedDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setDeleted(true);
        beneficiaryDemographicAdditional.setEmailID("jane.doe@example.org");
        beneficiaryDemographicAdditional.setLastModDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setLiteracyStatus("Literacy Status");
        beneficiaryDemographicAdditional.setMarrigeDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryDemographicAdditional.setMotherName("Mother Name");
        beneficiaryDemographicAdditional.setProcessed('A');
        beneficiaryDemographicAdditional.setiFSCCode("I FSCCode");
        when(beneficiaryDemographicAdditionalRepo.save(Mockito.<BeneficiaryDemographicAdditional>any()))
                .thenReturn(beneficiaryDemographicAdditional);

        JsonObject benD = new JsonObject();
        benD.addProperty("emailID", "motherName");
        benD.addProperty("literacyStatus", "42");

        // Act
        Long actualCreateBeneficiaryDemographicAdditionalResult = registrarServiceImpl
                .createBeneficiaryDemographicAdditional(benD, 1L);

        // Assert
        verify(beneficiaryDemographicAdditional).getBenDemoAdditionalID();
        verify(beneficiaryDemographicAdditional).setAccountNo(eq("3"));
        verify(beneficiaryDemographicAdditional).setBankName(eq("Bank Name"));
        verify(beneficiaryDemographicAdditional).setBenDemoAdditionalID(isA(Long.class));
        verify(beneficiaryDemographicAdditional).setBeneficiaryRegID(isA(Long.class));
        verify(beneficiaryDemographicAdditional).setBranchName(eq("janedoe/featurebranch"));
        verify(beneficiaryDemographicAdditional).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(beneficiaryDemographicAdditional).setCreatedDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setDeleted(isA(Boolean.class));
        verify(beneficiaryDemographicAdditional).setEmailID(eq("jane.doe@example.org"));
        verify(beneficiaryDemographicAdditional).setLastModDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setLiteracyStatus(eq("Literacy Status"));
        verify(beneficiaryDemographicAdditional).setMarrigeDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(beneficiaryDemographicAdditional).setMotherName(eq("Mother Name"));
        verify(beneficiaryDemographicAdditional).setProcessed(isA(Character.class));
        verify(beneficiaryDemographicAdditional).setiFSCCode(eq("I FSCCode"));
        verify(beneficiaryDemographicAdditionalRepo).save(isA(BeneficiaryDemographicAdditional.class));
        assertEquals(1L, actualCreateBeneficiaryDemographicAdditionalResult.longValue());
    }

    @Test
    void testCreateBeneficiaryDemographicAdditional7() {
        // Arrange
        BeneficiaryDemographicAdditional beneficiaryDemographicAdditional = mock(BeneficiaryDemographicAdditional.class);
        when(beneficiaryDemographicAdditional.getBenDemoAdditionalID()).thenReturn(1L);
        doNothing().when(beneficiaryDemographicAdditional).setAccountNo(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setBankName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setBenDemoAdditionalID(Mockito.<Long>any());
        doNothing().when(beneficiaryDemographicAdditional).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(beneficiaryDemographicAdditional).setBranchName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setCreatedBy(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setDeleted(Mockito.<Boolean>any());
        doNothing().when(beneficiaryDemographicAdditional).setEmailID(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setLiteracyStatus(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setMarrigeDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryDemographicAdditional).setModifiedBy(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setMotherName(Mockito.<String>any());
        doNothing().when(beneficiaryDemographicAdditional).setProcessed(Mockito.<Character>any());
        doNothing().when(beneficiaryDemographicAdditional).setiFSCCode(Mockito.<String>any());
        beneficiaryDemographicAdditional.setAccountNo("3");
        beneficiaryDemographicAdditional.setBankName("Bank Name");
        beneficiaryDemographicAdditional.setBenDemoAdditionalID(1L);
        beneficiaryDemographicAdditional.setBeneficiaryRegID(1L);
        beneficiaryDemographicAdditional.setBranchName("janedoe/featurebranch");
        beneficiaryDemographicAdditional.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryDemographicAdditional.setCreatedDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setDeleted(true);
        beneficiaryDemographicAdditional.setEmailID("jane.doe@example.org");
        beneficiaryDemographicAdditional.setLastModDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setLiteracyStatus("Literacy Status");
        beneficiaryDemographicAdditional.setMarrigeDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryDemographicAdditional.setMotherName("Mother Name");
        beneficiaryDemographicAdditional.setProcessed('A');
        beneficiaryDemographicAdditional.setiFSCCode("I FSCCode");
        when(beneficiaryDemographicAdditionalRepo.save(Mockito.<BeneficiaryDemographicAdditional>any()))
                .thenReturn(beneficiaryDemographicAdditional);

        JsonObject benD = new JsonObject();
        benD.addProperty("motherName", "literacyStatus");
        benD.addProperty("literacyStatus", true);

        // Act
        Long actualCreateBeneficiaryDemographicAdditionalResult = registrarServiceImpl
                .createBeneficiaryDemographicAdditional(benD, 1L);

        // Assert
        verify(beneficiaryDemographicAdditional).getBenDemoAdditionalID();
        verify(beneficiaryDemographicAdditional).setAccountNo(eq("3"));
        verify(beneficiaryDemographicAdditional).setBankName(eq("Bank Name"));
        verify(beneficiaryDemographicAdditional).setBenDemoAdditionalID(isA(Long.class));
        verify(beneficiaryDemographicAdditional).setBeneficiaryRegID(isA(Long.class));
        verify(beneficiaryDemographicAdditional).setBranchName(eq("janedoe/featurebranch"));
        verify(beneficiaryDemographicAdditional).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(beneficiaryDemographicAdditional).setCreatedDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setDeleted(isA(Boolean.class));
        verify(beneficiaryDemographicAdditional).setEmailID(eq("jane.doe@example.org"));
        verify(beneficiaryDemographicAdditional).setLastModDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setLiteracyStatus(eq("Literacy Status"));
        verify(beneficiaryDemographicAdditional).setMarrigeDate(isA(Timestamp.class));
        verify(beneficiaryDemographicAdditional).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(beneficiaryDemographicAdditional).setMotherName(eq("Mother Name"));
        verify(beneficiaryDemographicAdditional).setProcessed(isA(Character.class));
        verify(beneficiaryDemographicAdditional).setiFSCCode(eq("I FSCCode"));
        verify(beneficiaryDemographicAdditionalRepo).save(isA(BeneficiaryDemographicAdditional.class));
        assertEquals(1L, actualCreateBeneficiaryDemographicAdditionalResult.longValue());
    }

    @Test
    void testCreateBeneficiaryImage() {
        // Arrange
        BeneficiaryImage beneficiaryImage = new BeneficiaryImage();
        beneficiaryImage.setBenImage("Ben Image");
        beneficiaryImage.setBenImageID(1L);
        beneficiaryImage.setBeneficiaryRegID(1L);
        beneficiaryImage.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryImage.setCreatedDate(mock(Timestamp.class));
        beneficiaryImage.setDeleted(true);
        beneficiaryImage.setLastModDate(mock(Timestamp.class));
        beneficiaryImage.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryImage.setProcessed(true);
        when(beneficiaryImageRepo.save(Mockito.<BeneficiaryImage>any())).thenReturn(beneficiaryImage);

        JsonObject benD = new JsonObject();
        benD.addProperty("createdBy", "image");
        benD.addProperty("image", Integer.valueOf(1));

        // Act
        Long actualCreateBeneficiaryImageResult = registrarServiceImpl.createBeneficiaryImage(benD, 1L);

        // Assert
        verify(beneficiaryImageRepo).save(isA(BeneficiaryImage.class));
        assertEquals(1L, actualCreateBeneficiaryImageResult.longValue());
    }

    @Test
    void testCreateBeneficiaryImage2() {
        // Arrange
        BeneficiaryImage beneficiaryImage = new BeneficiaryImage();
        beneficiaryImage.setBenImage("Ben Image");
        beneficiaryImage.setBenImageID(1L);
        beneficiaryImage.setBeneficiaryRegID(1L);
        beneficiaryImage.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryImage.setCreatedDate(mock(Timestamp.class));
        beneficiaryImage.setDeleted(true);
        beneficiaryImage.setLastModDate(mock(Timestamp.class));
        beneficiaryImage.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryImage.setProcessed(true);
        when(beneficiaryImageRepo.save(Mockito.<BeneficiaryImage>any())).thenReturn(beneficiaryImage);

        JsonObject benD = new JsonObject();
        benD.addProperty("createdBy", (String) null);
        benD.addProperty("image", Integer.valueOf(1));

        // Act
        Long actualCreateBeneficiaryImageResult = registrarServiceImpl.createBeneficiaryImage(benD, 1L);

        // Assert
        verify(beneficiaryImageRepo).save(isA(BeneficiaryImage.class));
        assertEquals(1L, actualCreateBeneficiaryImageResult.longValue());
    }

    @Test
    void testCreateBeneficiaryImage3() {
        // Arrange
        BeneficiaryImage beneficiaryImage = new BeneficiaryImage();
        beneficiaryImage.setBenImage("Ben Image");
        beneficiaryImage.setBenImageID(1L);
        beneficiaryImage.setBeneficiaryRegID(1L);
        beneficiaryImage.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryImage.setCreatedDate(mock(Timestamp.class));
        beneficiaryImage.setDeleted(true);
        beneficiaryImage.setLastModDate(mock(Timestamp.class));
        beneficiaryImage.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryImage.setProcessed(true);
        when(beneficiaryImageRepo.save(Mockito.<BeneficiaryImage>any())).thenReturn(beneficiaryImage);

        JsonObject benD = new JsonObject();
        benD.addProperty("createdBy", "image");
        benD.addProperty("image", (Number) null);

        // Act
        Long actualCreateBeneficiaryImageResult = registrarServiceImpl.createBeneficiaryImage(benD, 1L);

        // Assert
        verify(beneficiaryImageRepo).save(isA(BeneficiaryImage.class));
        assertEquals(1L, actualCreateBeneficiaryImageResult.longValue());
    }

    @Test
    void testCreateBeneficiaryPhoneMapping() {
        // Arrange
        BeneficiaryDemographicData benDemoData = new BeneficiaryDemographicData();
        benDemoData.setAddressLine1("42 Main St");
        benDemoData.setAddressLine2("42 Main St");
        benDemoData.setAddressLine3("42 Main St");
        benDemoData.setAddressLine4("42 Main St");
        benDemoData.setAddressLine5("42 Main St");
        benDemoData.setAreaID(1);
        benDemoData.setBenData(new BeneficiaryData());
        benDemoData.setBenDemographicsID(1L);
        benDemoData.setBeneficiaryRegID(1L);
        benDemoData.setBiometricFilePath("/directory/foo.txt");
        benDemoData.setBlockID(1);
        benDemoData.setCityID(1);
        benDemoData.setCommunityID((short) 1);
        benDemoData.setCountryID(1);
        benDemoData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benDemoData.setCreatedDate(mock(Timestamp.class));
        benDemoData.setDeleted(true);
        benDemoData.setDistrictBranchID(1);
        benDemoData.setDistrictID(1);
        benDemoData.setEducationID((short) 1);
        benDemoData.setHealthCareWorkerID((short) 1);
        benDemoData.setIncomeStatusID((short) 1);
        benDemoData.setIsAddPermanent(true);
        benDemoData.setIsAddPresent(true);
        benDemoData.setIsBiometric(true);
        benDemoData.setIsPhoto(true);
        benDemoData.setLastModDate(mock(Timestamp.class));
        benDemoData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benDemoData.setOccupationID((short) 1);
        benDemoData.setPinCode("Pin Code");
        benDemoData.setPreferredLangID(1);
        benDemoData.setReligionID((short) 1);
        benDemoData.setServicePointID(1);
        benDemoData.setStateID(1);
        benDemoData.setZoneID(1);

        BeneficiaryData benData = new BeneficiaryData();
        benData.setAadharNo("Aadhar No");
        benData.setAge("Age");
        benData.setBenDemoData(benDemoData);
        benData.setBenPhoneMap(new HashSet<>());
        benData.setBeneficiaryID("Beneficiary ID");
        benData.setBeneficiaryName("Beneficiary Name");
        benData.setBeneficiaryRegID(1L);
        benData.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benData.setCreatedDate(mock(Timestamp.class));
        benData.setDeleted(true);
        benData.setDob(mock(Timestamp.class));
        benData.setEmergencyRegistration("Emergency Registration");
        benData.setFatherName("Father Name");
        benData.setFirstName("Jane");
        benData.setFlowStatusFlag('A');
        benData.setGenderID((short) 1);
        benData.setGenderName("Gender Name");
        benData.setGovIdentityNo("Gov Identity No");
        benData.setGovIdentityTypeID((short) 1);
        benData.setImage("Ben Image");
        benData.setIsHivPos(true);
        benData.setLastModDate(mock(Timestamp.class));
        benData.setLastName("Doe");
        benData.setMaritalStatusID((short) 1);
        benData.setMiddleName("Middle Name");
        benData.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benData.setPlaceOfWork("Place Of Work");
        benData.setRegisteredServiceID((short) 1);
        benData.setServicePointName("Service Point Name");
        benData.setSexuaOrientationID((short) 1);
        benData.setSourseOfInformation("Sourse Of Information");
        benData.setSpouseName("Spouse Name");
        benData.setStatusID((short) 1);
        benData.setTitleID((short) 1);

        BeneficiaryDemographicData benDemoData2 = new BeneficiaryDemographicData();
        benDemoData2.setAddressLine1("42 Main St");
        benDemoData2.setAddressLine2("42 Main St");
        benDemoData2.setAddressLine3("42 Main St");
        benDemoData2.setAddressLine4("42 Main St");
        benDemoData2.setAddressLine5("42 Main St");
        benDemoData2.setAreaID(1);
        benDemoData2.setBenData(benData);
        benDemoData2.setBenDemographicsID(1L);
        benDemoData2.setBeneficiaryRegID(1L);
        benDemoData2.setBiometricFilePath("/directory/foo.txt");
        benDemoData2.setBlockID(1);
        benDemoData2.setCityID(1);
        benDemoData2.setCommunityID((short) 1);
        benDemoData2.setCountryID(1);
        benDemoData2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benDemoData2.setCreatedDate(mock(Timestamp.class));
        benDemoData2.setDeleted(true);
        benDemoData2.setDistrictBranchID(1);
        benDemoData2.setDistrictID(1);
        benDemoData2.setEducationID((short) 1);
        benDemoData2.setHealthCareWorkerID((short) 1);
        benDemoData2.setIncomeStatusID((short) 1);
        benDemoData2.setIsAddPermanent(true);
        benDemoData2.setIsAddPresent(true);
        benDemoData2.setIsBiometric(true);
        benDemoData2.setIsPhoto(true);
        benDemoData2.setLastModDate(mock(Timestamp.class));
        benDemoData2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benDemoData2.setOccupationID((short) 1);
        benDemoData2.setPinCode("Pin Code");
        benDemoData2.setPreferredLangID(1);
        benDemoData2.setReligionID((short) 1);
        benDemoData2.setServicePointID(1);
        benDemoData2.setStateID(1);
        benDemoData2.setZoneID(1);

        BeneficiaryData benData2 = new BeneficiaryData();
        benData2.setAadharNo("Aadhar No");
        benData2.setAge("Age");
        benData2.setBenDemoData(benDemoData2);
        benData2.setBenPhoneMap(new HashSet<>());
        benData2.setBeneficiaryID("Beneficiary ID");
        benData2.setBeneficiaryName("Beneficiary Name");
        benData2.setBeneficiaryRegID(1L);
        benData2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        benData2.setCreatedDate(mock(Timestamp.class));
        benData2.setDeleted(true);
        benData2.setDob(mock(Timestamp.class));
        benData2.setEmergencyRegistration("Emergency Registration");
        benData2.setFatherName("Father Name");
        benData2.setFirstName("Jane");
        benData2.setFlowStatusFlag('A');
        benData2.setGenderID((short) 1);
        benData2.setGenderName("Gender Name");
        benData2.setGovIdentityNo("Gov Identity No");
        benData2.setGovIdentityTypeID((short) 1);
        benData2.setImage("Ben Image");
        benData2.setIsHivPos(true);
        benData2.setLastModDate(mock(Timestamp.class));
        benData2.setLastName("Doe");
        benData2.setMaritalStatusID((short) 1);
        benData2.setMiddleName("Middle Name");
        benData2.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        benData2.setPlaceOfWork("Place Of Work");
        benData2.setRegisteredServiceID((short) 1);
        benData2.setServicePointName("Service Point Name");
        benData2.setSexuaOrientationID((short) 1);
        benData2.setSourseOfInformation("Sourse Of Information");
        benData2.setSpouseName("Spouse Name");
        benData2.setStatusID((short) 1);
        benData2.setTitleID((short) 1);

        BeneficiaryPhoneMapping beneficiaryPhoneMapping = new BeneficiaryPhoneMapping();
        beneficiaryPhoneMapping.setBenData(benData2);
        beneficiaryPhoneMapping.setBenPhMapID(1L);
        beneficiaryPhoneMapping.setBenRelationshipID((short) 1);
        beneficiaryPhoneMapping.setBenificiaryRegID(1L);
        beneficiaryPhoneMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryPhoneMapping.setCreatedDate(mock(Timestamp.class));
        beneficiaryPhoneMapping.setDeleted(true);
        beneficiaryPhoneMapping.setLastModDate(mock(Timestamp.class));
        beneficiaryPhoneMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryPhoneMapping.setNuisanceCallCount((short) 1);
        beneficiaryPhoneMapping.setParentBenRegID(1L);
        beneficiaryPhoneMapping.setPhoneNo("6625550144");
        beneficiaryPhoneMapping.setPhoneTypeID((short) 1);
        when(registrarRepoBenPhoneMapData.save(Mockito.<BeneficiaryPhoneMapping>any())).thenReturn(beneficiaryPhoneMapping);

        // Act
        Long actualCreateBeneficiaryPhoneMappingResult = registrarServiceImpl
                .createBeneficiaryPhoneMapping(new JsonObject(), 1L);

        // Assert
        verify(registrarRepoBenPhoneMapData).save(isA(BeneficiaryPhoneMapping.class));
        assertEquals(1L, actualCreateBeneficiaryPhoneMappingResult.longValue());
    }

    @Test
    void testCreateBenGovIdMapping() {
        // Arrange
        when(registrarRepoBenGovIdMapping.saveAll(Mockito.<Iterable<BenGovIdMapping>>any())).thenReturn(new ArrayList<>());

        JsonObject benD = new JsonObject();
        benD.add("govID", new JsonArray(3));

        // Act
        int actualCreateBenGovIdMappingResult = registrarServiceImpl.createBenGovIdMapping(benD, 1L);

        // Assert
        verify(registrarRepoBenGovIdMapping).saveAll(isA(Iterable.class));
        assertEquals(0, actualCreateBenGovIdMappingResult);
    }

    @Test
    void testCreateBenGovIdMapping2() {
        // Arrange
        when(registrarRepoBenGovIdMapping.saveAll(Mockito.<Iterable<BenGovIdMapping>>any())).thenReturn(new ArrayList<>());

        JsonObject benD = new JsonObject();
        benD.add("Property", new JsonArray(3));
        benD.add("govID", new JsonArray(3));

        // Act
        int actualCreateBenGovIdMappingResult = registrarServiceImpl.createBenGovIdMapping(benD, 1L);

        // Assert
        verify(registrarRepoBenGovIdMapping).saveAll(isA(Iterable.class));
        assertEquals(0, actualCreateBenGovIdMappingResult);
    }

    @Test
    void testGetRegWorkList() {
        // Arrange
        when(registrarRepoBenData.getRegistrarWorkList(anyInt())).thenReturn(new ArrayList<>());

        // Act
        String actualRegWorkList = registrarServiceImpl.getRegWorkList(1);

        // Assert
        verify(registrarRepoBenData).getRegistrarWorkList(eq(1));
        assertEquals("[]", actualRegWorkList);
    }

    @Test
    void testGetQuickSearchBenData() {
        // Arrange
        when(reistrarRepoBenSearch.getQuickSearch(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        String actualQuickSearchBenData = registrarServiceImpl.getQuickSearchBenData("Ben ID");

        // Assert
        verify(reistrarRepoBenSearch).getQuickSearch(eq("Ben ID"));
        assertEquals("[]", actualQuickSearchBenData);
    }

    @Test
    void testGetAdvanceSearchBenData() {
        // Arrange
        when(reistrarRepoBenSearch.getAdvanceBenSearchList(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());

        V_BenAdvanceSearch v_BenAdvanceSearch = new V_BenAdvanceSearch();
        v_BenAdvanceSearch.setAadharNo("Aadhar No");
        v_BenAdvanceSearch.setBeneficiaryID("Beneficiary ID");
        v_BenAdvanceSearch.setBeneficiaryRegID(1L);
        v_BenAdvanceSearch.setDistrictBranchID(1);
        v_BenAdvanceSearch.setDistrictID(1);
        v_BenAdvanceSearch.setDistrictName("District Name");
        v_BenAdvanceSearch.setDob(mock(Date.class));
        v_BenAdvanceSearch.setFatherName("Father Name");
        v_BenAdvanceSearch.setFirstName("Jane");
        v_BenAdvanceSearch.setFlowStatusFlag('A');
        v_BenAdvanceSearch.setGenderID((short) 1);
        v_BenAdvanceSearch.setGenderName("Gender Name");
        v_BenAdvanceSearch.setGovtIdentityNo("Govt Identity No");
        v_BenAdvanceSearch.setId(1L);
        v_BenAdvanceSearch.setLastName("Doe");
        v_BenAdvanceSearch.setPhoneNo("6625550144");
        v_BenAdvanceSearch.setRegCreatedDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setRegLastModDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setStateID(1);
        v_BenAdvanceSearch.setVillageName("Village Name");

        // Act
        String actualAdvanceSearchBenData = registrarServiceImpl.getAdvanceSearchBenData(v_BenAdvanceSearch);

        // Assert
        verify(reistrarRepoBenSearch).getAdvanceBenSearchList(eq("Beneficiary ID"), eq("Jane"), eq("Doe"), eq("6625550144"),
                eq("Aadhar No"), eq("Govt Identity No"), eq("1"), eq("1"));
        assertEquals("[]", actualAdvanceSearchBenData);
    }

    @Test
    void testGetAdvanceSearchBenData2() {
        // Arrange
        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        objectArrayList.add(new Object[]{"42"});
        when(reistrarRepoBenSearch.getAdvanceBenSearchList(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any())).thenReturn(objectArrayList);

        V_BenAdvanceSearch v_BenAdvanceSearch = new V_BenAdvanceSearch();
        v_BenAdvanceSearch.setAadharNo("Aadhar No");
        v_BenAdvanceSearch.setBeneficiaryID("Beneficiary ID");
        v_BenAdvanceSearch.setBeneficiaryRegID(1L);
        v_BenAdvanceSearch.setDistrictBranchID(1);
        v_BenAdvanceSearch.setDistrictID(1);
        v_BenAdvanceSearch.setDistrictName("District Name");
        v_BenAdvanceSearch.setDob(mock(Date.class));
        v_BenAdvanceSearch.setFatherName("Father Name");
        v_BenAdvanceSearch.setFirstName("Jane");
        v_BenAdvanceSearch.setFlowStatusFlag('A');
        v_BenAdvanceSearch.setGenderID((short) 1);
        v_BenAdvanceSearch.setGenderName("Gender Name");
        v_BenAdvanceSearch.setGovtIdentityNo("Govt Identity No");
        v_BenAdvanceSearch.setId(1L);
        v_BenAdvanceSearch.setLastName("Doe");
        v_BenAdvanceSearch.setPhoneNo("6625550144");
        v_BenAdvanceSearch.setRegCreatedDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setRegLastModDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setStateID(1);
        v_BenAdvanceSearch.setVillageName("Village Name");

        // Act
        String actualAdvanceSearchBenData = registrarServiceImpl.getAdvanceSearchBenData(v_BenAdvanceSearch);

        // Assert
        verify(reistrarRepoBenSearch).getAdvanceBenSearchList(eq("Beneficiary ID"), eq("Jane"), eq("Doe"), eq("6625550144"),
                eq("Aadhar No"), eq("Govt Identity No"), eq("1"), eq("1"));
        assertEquals("", actualAdvanceSearchBenData);
    }

    @Test
    void testGetAdvanceSearchBenData3() {
        // Arrange
        when(reistrarRepoBenSearch.getAdvanceBenSearchList(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());
        V_BenAdvanceSearch v_BenAdvanceSearch = mock(V_BenAdvanceSearch.class);
        when(v_BenAdvanceSearch.getDistrictID()).thenReturn(1);
        when(v_BenAdvanceSearch.getStateID()).thenReturn(1);
        when(v_BenAdvanceSearch.getAadharNo()).thenReturn("");
        when(v_BenAdvanceSearch.getBeneficiaryID()).thenReturn("Beneficiary ID");
        when(v_BenAdvanceSearch.getFatherName()).thenReturn("Father Name");
        when(v_BenAdvanceSearch.getFirstName()).thenReturn("Jane");
        when(v_BenAdvanceSearch.getGovtIdentityNo()).thenReturn("Govt Identity No");
        when(v_BenAdvanceSearch.getLastName()).thenReturn("Doe");
        when(v_BenAdvanceSearch.getPhoneNo()).thenReturn("6625550144");
        doNothing().when(v_BenAdvanceSearch).setAadharNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setBeneficiaryID(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictBranchID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setDob(Mockito.<Date>any());
        doNothing().when(v_BenAdvanceSearch).setFatherName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setFirstName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setFlowStatusFlag(Mockito.<Character>any());
        doNothing().when(v_BenAdvanceSearch).setGenderID(Mockito.<Short>any());
        doNothing().when(v_BenAdvanceSearch).setGenderName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setGovtIdentityNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setId(Mockito.<Long>any());
        doNothing().when(v_BenAdvanceSearch).setLastName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setPhoneNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setRegCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(v_BenAdvanceSearch).setRegLastModDate(Mockito.<Timestamp>any());
        doNothing().when(v_BenAdvanceSearch).setStateID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setVillageName(Mockito.<String>any());
        v_BenAdvanceSearch.setAadharNo("Aadhar No");
        v_BenAdvanceSearch.setBeneficiaryID("Beneficiary ID");
        v_BenAdvanceSearch.setBeneficiaryRegID(1L);
        v_BenAdvanceSearch.setDistrictBranchID(1);
        v_BenAdvanceSearch.setDistrictID(1);
        v_BenAdvanceSearch.setDistrictName("District Name");
        v_BenAdvanceSearch.setDob(mock(Date.class));
        v_BenAdvanceSearch.setFatherName("Father Name");
        v_BenAdvanceSearch.setFirstName("Jane");
        v_BenAdvanceSearch.setFlowStatusFlag('A');
        v_BenAdvanceSearch.setGenderID((short) 1);
        v_BenAdvanceSearch.setGenderName("Gender Name");
        v_BenAdvanceSearch.setGovtIdentityNo("Govt Identity No");
        v_BenAdvanceSearch.setId(1L);
        v_BenAdvanceSearch.setLastName("Doe");
        v_BenAdvanceSearch.setPhoneNo("6625550144");
        v_BenAdvanceSearch.setRegCreatedDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setRegLastModDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setStateID(1);
        v_BenAdvanceSearch.setVillageName("Village Name");

        // Act
        String actualAdvanceSearchBenData = registrarServiceImpl.getAdvanceSearchBenData(v_BenAdvanceSearch);

        // Assert
        verify(v_BenAdvanceSearch, atLeast(1)).getAadharNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getBeneficiaryID();
        verify(v_BenAdvanceSearch, atLeast(1)).getDistrictID();
        verify(v_BenAdvanceSearch, atLeast(1)).getFatherName();
        verify(v_BenAdvanceSearch, atLeast(1)).getFirstName();
        verify(v_BenAdvanceSearch, atLeast(1)).getGovtIdentityNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getLastName();
        verify(v_BenAdvanceSearch, atLeast(1)).getPhoneNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getStateID();
        verify(v_BenAdvanceSearch).setAadharNo(eq("Aadhar No"));
        verify(v_BenAdvanceSearch).setBeneficiaryID(eq("Beneficiary ID"));
        verify(v_BenAdvanceSearch).setBeneficiaryRegID(isA(Long.class));
        verify(v_BenAdvanceSearch).setDistrictBranchID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setDistrictID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setDistrictName(eq("District Name"));
        verify(v_BenAdvanceSearch).setDob(isA(Date.class));
        verify(v_BenAdvanceSearch).setFatherName(eq("Father Name"));
        verify(v_BenAdvanceSearch).setFirstName(eq("Jane"));
        verify(v_BenAdvanceSearch).setFlowStatusFlag(isA(Character.class));
        verify(v_BenAdvanceSearch).setGenderID(isA(Short.class));
        verify(v_BenAdvanceSearch).setGenderName(eq("Gender Name"));
        verify(v_BenAdvanceSearch).setGovtIdentityNo(eq("Govt Identity No"));
        verify(v_BenAdvanceSearch).setId(isA(Long.class));
        verify(v_BenAdvanceSearch).setLastName(eq("Doe"));
        verify(v_BenAdvanceSearch).setPhoneNo(eq("6625550144"));
        verify(v_BenAdvanceSearch).setRegCreatedDate(isA(Timestamp.class));
        verify(v_BenAdvanceSearch).setRegLastModDate(isA(Timestamp.class));
        verify(v_BenAdvanceSearch).setStateID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setVillageName(eq("Village Name"));
        verify(reistrarRepoBenSearch).getAdvanceBenSearchList(eq("Beneficiary ID"), eq("Jane"), eq("Doe"), eq("6625550144"),
                eq("%%"), eq("Govt Identity No"), eq("1"), eq("1"));
        assertEquals("[]", actualAdvanceSearchBenData);
    }

    @Test
    void testGetAdvanceSearchBenData4() {
        // Arrange
        when(reistrarRepoBenSearch.getAdvanceBenSearchList(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());
        V_BenAdvanceSearch v_BenAdvanceSearch = mock(V_BenAdvanceSearch.class);
        when(v_BenAdvanceSearch.getDistrictID()).thenReturn(1);
        when(v_BenAdvanceSearch.getStateID()).thenReturn(1);
        when(v_BenAdvanceSearch.getAadharNo()).thenReturn("Aadhar No");
        when(v_BenAdvanceSearch.getBeneficiaryID()).thenReturn("");
        when(v_BenAdvanceSearch.getFatherName()).thenReturn("Father Name");
        when(v_BenAdvanceSearch.getFirstName()).thenReturn("Jane");
        when(v_BenAdvanceSearch.getGovtIdentityNo()).thenReturn("Govt Identity No");
        when(v_BenAdvanceSearch.getLastName()).thenReturn("Doe");
        when(v_BenAdvanceSearch.getPhoneNo()).thenReturn("6625550144");
        doNothing().when(v_BenAdvanceSearch).setAadharNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setBeneficiaryID(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictBranchID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setDob(Mockito.<Date>any());
        doNothing().when(v_BenAdvanceSearch).setFatherName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setFirstName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setFlowStatusFlag(Mockito.<Character>any());
        doNothing().when(v_BenAdvanceSearch).setGenderID(Mockito.<Short>any());
        doNothing().when(v_BenAdvanceSearch).setGenderName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setGovtIdentityNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setId(Mockito.<Long>any());
        doNothing().when(v_BenAdvanceSearch).setLastName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setPhoneNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setRegCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(v_BenAdvanceSearch).setRegLastModDate(Mockito.<Timestamp>any());
        doNothing().when(v_BenAdvanceSearch).setStateID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setVillageName(Mockito.<String>any());
        v_BenAdvanceSearch.setAadharNo("Aadhar No");
        v_BenAdvanceSearch.setBeneficiaryID("Beneficiary ID");
        v_BenAdvanceSearch.setBeneficiaryRegID(1L);
        v_BenAdvanceSearch.setDistrictBranchID(1);
        v_BenAdvanceSearch.setDistrictID(1);
        v_BenAdvanceSearch.setDistrictName("District Name");
        v_BenAdvanceSearch.setDob(mock(Date.class));
        v_BenAdvanceSearch.setFatherName("Father Name");
        v_BenAdvanceSearch.setFirstName("Jane");
        v_BenAdvanceSearch.setFlowStatusFlag('A');
        v_BenAdvanceSearch.setGenderID((short) 1);
        v_BenAdvanceSearch.setGenderName("Gender Name");
        v_BenAdvanceSearch.setGovtIdentityNo("Govt Identity No");
        v_BenAdvanceSearch.setId(1L);
        v_BenAdvanceSearch.setLastName("Doe");
        v_BenAdvanceSearch.setPhoneNo("6625550144");
        v_BenAdvanceSearch.setRegCreatedDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setRegLastModDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setStateID(1);
        v_BenAdvanceSearch.setVillageName("Village Name");

        // Act
        String actualAdvanceSearchBenData = registrarServiceImpl.getAdvanceSearchBenData(v_BenAdvanceSearch);

        // Assert
        verify(v_BenAdvanceSearch, atLeast(1)).getAadharNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getBeneficiaryID();
        verify(v_BenAdvanceSearch, atLeast(1)).getDistrictID();
        verify(v_BenAdvanceSearch, atLeast(1)).getFatherName();
        verify(v_BenAdvanceSearch, atLeast(1)).getFirstName();
        verify(v_BenAdvanceSearch, atLeast(1)).getGovtIdentityNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getLastName();
        verify(v_BenAdvanceSearch, atLeast(1)).getPhoneNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getStateID();
        verify(v_BenAdvanceSearch).setAadharNo(eq("Aadhar No"));
        verify(v_BenAdvanceSearch).setBeneficiaryID(eq("Beneficiary ID"));
        verify(v_BenAdvanceSearch).setBeneficiaryRegID(isA(Long.class));
        verify(v_BenAdvanceSearch).setDistrictBranchID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setDistrictID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setDistrictName(eq("District Name"));
        verify(v_BenAdvanceSearch).setDob(isA(Date.class));
        verify(v_BenAdvanceSearch).setFatherName(eq("Father Name"));
        verify(v_BenAdvanceSearch).setFirstName(eq("Jane"));
        verify(v_BenAdvanceSearch).setFlowStatusFlag(isA(Character.class));
        verify(v_BenAdvanceSearch).setGenderID(isA(Short.class));
        verify(v_BenAdvanceSearch).setGenderName(eq("Gender Name"));
        verify(v_BenAdvanceSearch).setGovtIdentityNo(eq("Govt Identity No"));
        verify(v_BenAdvanceSearch).setId(isA(Long.class));
        verify(v_BenAdvanceSearch).setLastName(eq("Doe"));
        verify(v_BenAdvanceSearch).setPhoneNo(eq("6625550144"));
        verify(v_BenAdvanceSearch).setRegCreatedDate(isA(Timestamp.class));
        verify(v_BenAdvanceSearch).setRegLastModDate(isA(Timestamp.class));
        verify(v_BenAdvanceSearch).setStateID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setVillageName(eq("Village Name"));
        verify(reistrarRepoBenSearch).getAdvanceBenSearchList(eq("%%"), eq("Jane"), eq("Doe"), eq("6625550144"),
                eq("Aadhar No"), eq("Govt Identity No"), eq("1"), eq("1"));
        assertEquals("[]", actualAdvanceSearchBenData);
    }

    @Test
    void testGetAdvanceSearchBenData5() {
        // Arrange
        when(reistrarRepoBenSearch.getAdvanceBenSearchList(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());
        V_BenAdvanceSearch v_BenAdvanceSearch = mock(V_BenAdvanceSearch.class);
        when(v_BenAdvanceSearch.getDistrictID()).thenReturn(1);
        when(v_BenAdvanceSearch.getStateID()).thenReturn(1);
        when(v_BenAdvanceSearch.getAadharNo()).thenReturn("Aadhar No");
        when(v_BenAdvanceSearch.getBeneficiaryID()).thenReturn("Beneficiary ID");
        when(v_BenAdvanceSearch.getFatherName()).thenReturn("");
        when(v_BenAdvanceSearch.getFirstName()).thenReturn("Jane");
        when(v_BenAdvanceSearch.getGovtIdentityNo()).thenReturn("Govt Identity No");
        when(v_BenAdvanceSearch.getLastName()).thenReturn("Doe");
        when(v_BenAdvanceSearch.getPhoneNo()).thenReturn("6625550144");
        doNothing().when(v_BenAdvanceSearch).setAadharNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setBeneficiaryID(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictBranchID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setDob(Mockito.<Date>any());
        doNothing().when(v_BenAdvanceSearch).setFatherName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setFirstName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setFlowStatusFlag(Mockito.<Character>any());
        doNothing().when(v_BenAdvanceSearch).setGenderID(Mockito.<Short>any());
        doNothing().when(v_BenAdvanceSearch).setGenderName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setGovtIdentityNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setId(Mockito.<Long>any());
        doNothing().when(v_BenAdvanceSearch).setLastName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setPhoneNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setRegCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(v_BenAdvanceSearch).setRegLastModDate(Mockito.<Timestamp>any());
        doNothing().when(v_BenAdvanceSearch).setStateID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setVillageName(Mockito.<String>any());
        v_BenAdvanceSearch.setAadharNo("Aadhar No");
        v_BenAdvanceSearch.setBeneficiaryID("Beneficiary ID");
        v_BenAdvanceSearch.setBeneficiaryRegID(1L);
        v_BenAdvanceSearch.setDistrictBranchID(1);
        v_BenAdvanceSearch.setDistrictID(1);
        v_BenAdvanceSearch.setDistrictName("District Name");
        v_BenAdvanceSearch.setDob(mock(Date.class));
        v_BenAdvanceSearch.setFatherName("Father Name");
        v_BenAdvanceSearch.setFirstName("Jane");
        v_BenAdvanceSearch.setFlowStatusFlag('A');
        v_BenAdvanceSearch.setGenderID((short) 1);
        v_BenAdvanceSearch.setGenderName("Gender Name");
        v_BenAdvanceSearch.setGovtIdentityNo("Govt Identity No");
        v_BenAdvanceSearch.setId(1L);
        v_BenAdvanceSearch.setLastName("Doe");
        v_BenAdvanceSearch.setPhoneNo("6625550144");
        v_BenAdvanceSearch.setRegCreatedDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setRegLastModDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setStateID(1);
        v_BenAdvanceSearch.setVillageName("Village Name");

        // Act
        String actualAdvanceSearchBenData = registrarServiceImpl.getAdvanceSearchBenData(v_BenAdvanceSearch);

        // Assert
        verify(v_BenAdvanceSearch, atLeast(1)).getAadharNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getBeneficiaryID();
        verify(v_BenAdvanceSearch, atLeast(1)).getDistrictID();
        verify(v_BenAdvanceSearch, atLeast(1)).getFatherName();
        verify(v_BenAdvanceSearch, atLeast(1)).getFirstName();
        verify(v_BenAdvanceSearch, atLeast(1)).getGovtIdentityNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getLastName();
        verify(v_BenAdvanceSearch, atLeast(1)).getPhoneNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getStateID();
        verify(v_BenAdvanceSearch).setAadharNo(eq("Aadhar No"));
        verify(v_BenAdvanceSearch).setBeneficiaryID(eq("Beneficiary ID"));
        verify(v_BenAdvanceSearch).setBeneficiaryRegID(isA(Long.class));
        verify(v_BenAdvanceSearch).setDistrictBranchID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setDistrictID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setDistrictName(eq("District Name"));
        verify(v_BenAdvanceSearch).setDob(isA(Date.class));
        verify(v_BenAdvanceSearch).setFatherName(eq("Father Name"));
        verify(v_BenAdvanceSearch).setFirstName(eq("Jane"));
        verify(v_BenAdvanceSearch).setFlowStatusFlag(isA(Character.class));
        verify(v_BenAdvanceSearch).setGenderID(isA(Short.class));
        verify(v_BenAdvanceSearch).setGenderName(eq("Gender Name"));
        verify(v_BenAdvanceSearch).setGovtIdentityNo(eq("Govt Identity No"));
        verify(v_BenAdvanceSearch).setId(isA(Long.class));
        verify(v_BenAdvanceSearch).setLastName(eq("Doe"));
        verify(v_BenAdvanceSearch).setPhoneNo(eq("6625550144"));
        verify(v_BenAdvanceSearch).setRegCreatedDate(isA(Timestamp.class));
        verify(v_BenAdvanceSearch).setRegLastModDate(isA(Timestamp.class));
        verify(v_BenAdvanceSearch).setStateID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setVillageName(eq("Village Name"));
        verify(reistrarRepoBenSearch).getAdvanceBenSearchList(eq("Beneficiary ID"), eq("Jane"), eq("Doe"), eq("6625550144"),
                eq("Aadhar No"), eq("Govt Identity No"), eq("1"), eq("1"));
        assertEquals("[]", actualAdvanceSearchBenData);
    }

    @Test
    void testGetAdvanceSearchBenData6() {
        // Arrange
        when(reistrarRepoBenSearch.getAdvanceBenSearchList(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());
        V_BenAdvanceSearch v_BenAdvanceSearch = mock(V_BenAdvanceSearch.class);
        when(v_BenAdvanceSearch.getDistrictID()).thenReturn(1);
        when(v_BenAdvanceSearch.getStateID()).thenReturn(1);
        when(v_BenAdvanceSearch.getAadharNo()).thenReturn("Aadhar No");
        when(v_BenAdvanceSearch.getBeneficiaryID()).thenReturn("Beneficiary ID");
        when(v_BenAdvanceSearch.getFatherName()).thenReturn("Father Name");
        when(v_BenAdvanceSearch.getFirstName()).thenReturn("");
        when(v_BenAdvanceSearch.getGovtIdentityNo()).thenReturn("Govt Identity No");
        when(v_BenAdvanceSearch.getLastName()).thenReturn("Doe");
        when(v_BenAdvanceSearch.getPhoneNo()).thenReturn("6625550144");
        doNothing().when(v_BenAdvanceSearch).setAadharNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setBeneficiaryID(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictBranchID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setDob(Mockito.<Date>any());
        doNothing().when(v_BenAdvanceSearch).setFatherName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setFirstName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setFlowStatusFlag(Mockito.<Character>any());
        doNothing().when(v_BenAdvanceSearch).setGenderID(Mockito.<Short>any());
        doNothing().when(v_BenAdvanceSearch).setGenderName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setGovtIdentityNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setId(Mockito.<Long>any());
        doNothing().when(v_BenAdvanceSearch).setLastName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setPhoneNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setRegCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(v_BenAdvanceSearch).setRegLastModDate(Mockito.<Timestamp>any());
        doNothing().when(v_BenAdvanceSearch).setStateID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setVillageName(Mockito.<String>any());
        v_BenAdvanceSearch.setAadharNo("Aadhar No");
        v_BenAdvanceSearch.setBeneficiaryID("Beneficiary ID");
        v_BenAdvanceSearch.setBeneficiaryRegID(1L);
        v_BenAdvanceSearch.setDistrictBranchID(1);
        v_BenAdvanceSearch.setDistrictID(1);
        v_BenAdvanceSearch.setDistrictName("District Name");
        v_BenAdvanceSearch.setDob(mock(Date.class));
        v_BenAdvanceSearch.setFatherName("Father Name");
        v_BenAdvanceSearch.setFirstName("Jane");
        v_BenAdvanceSearch.setFlowStatusFlag('A');
        v_BenAdvanceSearch.setGenderID((short) 1);
        v_BenAdvanceSearch.setGenderName("Gender Name");
        v_BenAdvanceSearch.setGovtIdentityNo("Govt Identity No");
        v_BenAdvanceSearch.setId(1L);
        v_BenAdvanceSearch.setLastName("Doe");
        v_BenAdvanceSearch.setPhoneNo("6625550144");
        v_BenAdvanceSearch.setRegCreatedDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setRegLastModDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setStateID(1);
        v_BenAdvanceSearch.setVillageName("Village Name");

        // Act
        String actualAdvanceSearchBenData = registrarServiceImpl.getAdvanceSearchBenData(v_BenAdvanceSearch);

        // Assert
        verify(v_BenAdvanceSearch, atLeast(1)).getAadharNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getBeneficiaryID();
        verify(v_BenAdvanceSearch, atLeast(1)).getDistrictID();
        verify(v_BenAdvanceSearch, atLeast(1)).getFatherName();
        verify(v_BenAdvanceSearch, atLeast(1)).getFirstName();
        verify(v_BenAdvanceSearch, atLeast(1)).getGovtIdentityNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getLastName();
        verify(v_BenAdvanceSearch, atLeast(1)).getPhoneNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getStateID();
        verify(v_BenAdvanceSearch).setAadharNo(eq("Aadhar No"));
        verify(v_BenAdvanceSearch).setBeneficiaryID(eq("Beneficiary ID"));
        verify(v_BenAdvanceSearch).setBeneficiaryRegID(isA(Long.class));
        verify(v_BenAdvanceSearch).setDistrictBranchID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setDistrictID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setDistrictName(eq("District Name"));
        verify(v_BenAdvanceSearch).setDob(isA(Date.class));
        verify(v_BenAdvanceSearch).setFatherName(eq("Father Name"));
        verify(v_BenAdvanceSearch).setFirstName(eq("Jane"));
        verify(v_BenAdvanceSearch).setFlowStatusFlag(isA(Character.class));
        verify(v_BenAdvanceSearch).setGenderID(isA(Short.class));
        verify(v_BenAdvanceSearch).setGenderName(eq("Gender Name"));
        verify(v_BenAdvanceSearch).setGovtIdentityNo(eq("Govt Identity No"));
        verify(v_BenAdvanceSearch).setId(isA(Long.class));
        verify(v_BenAdvanceSearch).setLastName(eq("Doe"));
        verify(v_BenAdvanceSearch).setPhoneNo(eq("6625550144"));
        verify(v_BenAdvanceSearch).setRegCreatedDate(isA(Timestamp.class));
        verify(v_BenAdvanceSearch).setRegLastModDate(isA(Timestamp.class));
        verify(v_BenAdvanceSearch).setStateID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setVillageName(eq("Village Name"));
        verify(reistrarRepoBenSearch).getAdvanceBenSearchList(eq("Beneficiary ID"), eq(""), eq("Doe"), eq("6625550144"),
                eq("Aadhar No"), eq("Govt Identity No"), eq("1"), eq("1"));
        assertEquals("[]", actualAdvanceSearchBenData);
    }

    @Test
    void testGetAdvanceSearchBenData7() {
        // Arrange
        when(reistrarRepoBenSearch.getAdvanceBenSearchList(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());
        V_BenAdvanceSearch v_BenAdvanceSearch = mock(V_BenAdvanceSearch.class);
        when(v_BenAdvanceSearch.getDistrictID()).thenReturn(1);
        when(v_BenAdvanceSearch.getStateID()).thenReturn(1);
        when(v_BenAdvanceSearch.getAadharNo()).thenReturn("Aadhar No");
        when(v_BenAdvanceSearch.getBeneficiaryID()).thenReturn("Beneficiary ID");
        when(v_BenAdvanceSearch.getFatherName()).thenReturn("Father Name");
        when(v_BenAdvanceSearch.getFirstName()).thenReturn("Jane");
        when(v_BenAdvanceSearch.getGovtIdentityNo()).thenReturn("");
        when(v_BenAdvanceSearch.getLastName()).thenReturn("Doe");
        when(v_BenAdvanceSearch.getPhoneNo()).thenReturn("6625550144");
        doNothing().when(v_BenAdvanceSearch).setAadharNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setBeneficiaryID(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictBranchID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setDob(Mockito.<Date>any());
        doNothing().when(v_BenAdvanceSearch).setFatherName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setFirstName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setFlowStatusFlag(Mockito.<Character>any());
        doNothing().when(v_BenAdvanceSearch).setGenderID(Mockito.<Short>any());
        doNothing().when(v_BenAdvanceSearch).setGenderName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setGovtIdentityNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setId(Mockito.<Long>any());
        doNothing().when(v_BenAdvanceSearch).setLastName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setPhoneNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setRegCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(v_BenAdvanceSearch).setRegLastModDate(Mockito.<Timestamp>any());
        doNothing().when(v_BenAdvanceSearch).setStateID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setVillageName(Mockito.<String>any());
        v_BenAdvanceSearch.setAadharNo("Aadhar No");
        v_BenAdvanceSearch.setBeneficiaryID("Beneficiary ID");
        v_BenAdvanceSearch.setBeneficiaryRegID(1L);
        v_BenAdvanceSearch.setDistrictBranchID(1);
        v_BenAdvanceSearch.setDistrictID(1);
        v_BenAdvanceSearch.setDistrictName("District Name");
        v_BenAdvanceSearch.setDob(mock(Date.class));
        v_BenAdvanceSearch.setFatherName("Father Name");
        v_BenAdvanceSearch.setFirstName("Jane");
        v_BenAdvanceSearch.setFlowStatusFlag('A');
        v_BenAdvanceSearch.setGenderID((short) 1);
        v_BenAdvanceSearch.setGenderName("Gender Name");
        v_BenAdvanceSearch.setGovtIdentityNo("Govt Identity No");
        v_BenAdvanceSearch.setId(1L);
        v_BenAdvanceSearch.setLastName("Doe");
        v_BenAdvanceSearch.setPhoneNo("6625550144");
        v_BenAdvanceSearch.setRegCreatedDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setRegLastModDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setStateID(1);
        v_BenAdvanceSearch.setVillageName("Village Name");

        // Act
        String actualAdvanceSearchBenData = registrarServiceImpl.getAdvanceSearchBenData(v_BenAdvanceSearch);

        // Assert
        verify(v_BenAdvanceSearch, atLeast(1)).getAadharNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getBeneficiaryID();
        verify(v_BenAdvanceSearch, atLeast(1)).getDistrictID();
        verify(v_BenAdvanceSearch, atLeast(1)).getFatherName();
        verify(v_BenAdvanceSearch, atLeast(1)).getFirstName();
        verify(v_BenAdvanceSearch, atLeast(1)).getGovtIdentityNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getLastName();
        verify(v_BenAdvanceSearch, atLeast(1)).getPhoneNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getStateID();
        verify(v_BenAdvanceSearch).setAadharNo(eq("Aadhar No"));
        verify(v_BenAdvanceSearch).setBeneficiaryID(eq("Beneficiary ID"));
        verify(v_BenAdvanceSearch).setBeneficiaryRegID(isA(Long.class));
        verify(v_BenAdvanceSearch).setDistrictBranchID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setDistrictID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setDistrictName(eq("District Name"));
        verify(v_BenAdvanceSearch).setDob(isA(Date.class));
        verify(v_BenAdvanceSearch).setFatherName(eq("Father Name"));
        verify(v_BenAdvanceSearch).setFirstName(eq("Jane"));
        verify(v_BenAdvanceSearch).setFlowStatusFlag(isA(Character.class));
        verify(v_BenAdvanceSearch).setGenderID(isA(Short.class));
        verify(v_BenAdvanceSearch).setGenderName(eq("Gender Name"));
        verify(v_BenAdvanceSearch).setGovtIdentityNo(eq("Govt Identity No"));
        verify(v_BenAdvanceSearch).setId(isA(Long.class));
        verify(v_BenAdvanceSearch).setLastName(eq("Doe"));
        verify(v_BenAdvanceSearch).setPhoneNo(eq("6625550144"));
        verify(v_BenAdvanceSearch).setRegCreatedDate(isA(Timestamp.class));
        verify(v_BenAdvanceSearch).setRegLastModDate(isA(Timestamp.class));
        verify(v_BenAdvanceSearch).setStateID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setVillageName(eq("Village Name"));
        verify(reistrarRepoBenSearch).getAdvanceBenSearchList(eq("Beneficiary ID"), eq("Jane"), eq("Doe"), eq("6625550144"),
                eq("Aadhar No"), eq("%%"), eq("1"), eq("1"));
        assertEquals("[]", actualAdvanceSearchBenData);
    }

    @Test
    void testGetAdvanceSearchBenData8() {
        // Arrange
        when(reistrarRepoBenSearch.getAdvanceBenSearchList(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());
        V_BenAdvanceSearch v_BenAdvanceSearch = mock(V_BenAdvanceSearch.class);
        when(v_BenAdvanceSearch.getDistrictID()).thenReturn(1);
        when(v_BenAdvanceSearch.getStateID()).thenReturn(1);
        when(v_BenAdvanceSearch.getAadharNo()).thenReturn("Aadhar No");
        when(v_BenAdvanceSearch.getBeneficiaryID()).thenReturn("Beneficiary ID");
        when(v_BenAdvanceSearch.getFatherName()).thenReturn("Father Name");
        when(v_BenAdvanceSearch.getFirstName()).thenReturn("Jane");
        when(v_BenAdvanceSearch.getGovtIdentityNo()).thenReturn("Govt Identity No");
        when(v_BenAdvanceSearch.getLastName()).thenReturn("");
        when(v_BenAdvanceSearch.getPhoneNo()).thenReturn("6625550144");
        doNothing().when(v_BenAdvanceSearch).setAadharNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setBeneficiaryID(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictBranchID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setDob(Mockito.<Date>any());
        doNothing().when(v_BenAdvanceSearch).setFatherName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setFirstName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setFlowStatusFlag(Mockito.<Character>any());
        doNothing().when(v_BenAdvanceSearch).setGenderID(Mockito.<Short>any());
        doNothing().when(v_BenAdvanceSearch).setGenderName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setGovtIdentityNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setId(Mockito.<Long>any());
        doNothing().when(v_BenAdvanceSearch).setLastName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setPhoneNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setRegCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(v_BenAdvanceSearch).setRegLastModDate(Mockito.<Timestamp>any());
        doNothing().when(v_BenAdvanceSearch).setStateID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setVillageName(Mockito.<String>any());
        v_BenAdvanceSearch.setAadharNo("Aadhar No");
        v_BenAdvanceSearch.setBeneficiaryID("Beneficiary ID");
        v_BenAdvanceSearch.setBeneficiaryRegID(1L);
        v_BenAdvanceSearch.setDistrictBranchID(1);
        v_BenAdvanceSearch.setDistrictID(1);
        v_BenAdvanceSearch.setDistrictName("District Name");
        v_BenAdvanceSearch.setDob(mock(Date.class));
        v_BenAdvanceSearch.setFatherName("Father Name");
        v_BenAdvanceSearch.setFirstName("Jane");
        v_BenAdvanceSearch.setFlowStatusFlag('A');
        v_BenAdvanceSearch.setGenderID((short) 1);
        v_BenAdvanceSearch.setGenderName("Gender Name");
        v_BenAdvanceSearch.setGovtIdentityNo("Govt Identity No");
        v_BenAdvanceSearch.setId(1L);
        v_BenAdvanceSearch.setLastName("Doe");
        v_BenAdvanceSearch.setPhoneNo("6625550144");
        v_BenAdvanceSearch.setRegCreatedDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setRegLastModDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setStateID(1);
        v_BenAdvanceSearch.setVillageName("Village Name");

        // Act
        String actualAdvanceSearchBenData = registrarServiceImpl.getAdvanceSearchBenData(v_BenAdvanceSearch);

        // Assert
        verify(v_BenAdvanceSearch, atLeast(1)).getAadharNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getBeneficiaryID();
        verify(v_BenAdvanceSearch, atLeast(1)).getDistrictID();
        verify(v_BenAdvanceSearch, atLeast(1)).getFatherName();
        verify(v_BenAdvanceSearch, atLeast(1)).getFirstName();
        verify(v_BenAdvanceSearch, atLeast(1)).getGovtIdentityNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getLastName();
        verify(v_BenAdvanceSearch, atLeast(1)).getPhoneNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getStateID();
        verify(v_BenAdvanceSearch).setAadharNo(eq("Aadhar No"));
        verify(v_BenAdvanceSearch).setBeneficiaryID(eq("Beneficiary ID"));
        verify(v_BenAdvanceSearch).setBeneficiaryRegID(isA(Long.class));
        verify(v_BenAdvanceSearch).setDistrictBranchID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setDistrictID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setDistrictName(eq("District Name"));
        verify(v_BenAdvanceSearch).setDob(isA(Date.class));
        verify(v_BenAdvanceSearch).setFatherName(eq("Father Name"));
        verify(v_BenAdvanceSearch).setFirstName(eq("Jane"));
        verify(v_BenAdvanceSearch).setFlowStatusFlag(isA(Character.class));
        verify(v_BenAdvanceSearch).setGenderID(isA(Short.class));
        verify(v_BenAdvanceSearch).setGenderName(eq("Gender Name"));
        verify(v_BenAdvanceSearch).setGovtIdentityNo(eq("Govt Identity No"));
        verify(v_BenAdvanceSearch).setId(isA(Long.class));
        verify(v_BenAdvanceSearch).setLastName(eq("Doe"));
        verify(v_BenAdvanceSearch).setPhoneNo(eq("6625550144"));
        verify(v_BenAdvanceSearch).setRegCreatedDate(isA(Timestamp.class));
        verify(v_BenAdvanceSearch).setRegLastModDate(isA(Timestamp.class));
        verify(v_BenAdvanceSearch).setStateID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setVillageName(eq("Village Name"));
        verify(reistrarRepoBenSearch).getAdvanceBenSearchList(eq("Beneficiary ID"), eq("Jane"), eq(""), eq("6625550144"),
                eq("Aadhar No"), eq("Govt Identity No"), eq("1"), eq("1"));
        assertEquals("[]", actualAdvanceSearchBenData);
    }

    /**
     * Method under test:
     * {@link RegistrarServiceImpl#getAdvanceSearchBenData(V_BenAdvanceSearch)}
     */
    @Test
    void testGetAdvanceSearchBenData9() {
        // Arrange
        when(reistrarRepoBenSearch.getAdvanceBenSearchList(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());
        V_BenAdvanceSearch v_BenAdvanceSearch = mock(V_BenAdvanceSearch.class);
        when(v_BenAdvanceSearch.getDistrictID()).thenReturn(1);
        when(v_BenAdvanceSearch.getStateID()).thenReturn(1);
        when(v_BenAdvanceSearch.getAadharNo()).thenReturn("Aadhar No");
        when(v_BenAdvanceSearch.getBeneficiaryID()).thenReturn("Beneficiary ID");
        when(v_BenAdvanceSearch.getFatherName()).thenReturn("Father Name");
        when(v_BenAdvanceSearch.getFirstName()).thenReturn("Jane");
        when(v_BenAdvanceSearch.getGovtIdentityNo()).thenReturn("Govt Identity No");
        when(v_BenAdvanceSearch.getLastName()).thenReturn("Doe");
        when(v_BenAdvanceSearch.getPhoneNo()).thenReturn("");
        doNothing().when(v_BenAdvanceSearch).setAadharNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setBeneficiaryID(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictBranchID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setDistrictName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setDob(Mockito.<Date>any());
        doNothing().when(v_BenAdvanceSearch).setFatherName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setFirstName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setFlowStatusFlag(Mockito.<Character>any());
        doNothing().when(v_BenAdvanceSearch).setGenderID(Mockito.<Short>any());
        doNothing().when(v_BenAdvanceSearch).setGenderName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setGovtIdentityNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setId(Mockito.<Long>any());
        doNothing().when(v_BenAdvanceSearch).setLastName(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setPhoneNo(Mockito.<String>any());
        doNothing().when(v_BenAdvanceSearch).setRegCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(v_BenAdvanceSearch).setRegLastModDate(Mockito.<Timestamp>any());
        doNothing().when(v_BenAdvanceSearch).setStateID(Mockito.<Integer>any());
        doNothing().when(v_BenAdvanceSearch).setVillageName(Mockito.<String>any());
        v_BenAdvanceSearch.setAadharNo("Aadhar No");
        v_BenAdvanceSearch.setBeneficiaryID("Beneficiary ID");
        v_BenAdvanceSearch.setBeneficiaryRegID(1L);
        v_BenAdvanceSearch.setDistrictBranchID(1);
        v_BenAdvanceSearch.setDistrictID(1);
        v_BenAdvanceSearch.setDistrictName("District Name");
        v_BenAdvanceSearch.setDob(mock(Date.class));
        v_BenAdvanceSearch.setFatherName("Father Name");
        v_BenAdvanceSearch.setFirstName("Jane");
        v_BenAdvanceSearch.setFlowStatusFlag('A');
        v_BenAdvanceSearch.setGenderID((short) 1);
        v_BenAdvanceSearch.setGenderName("Gender Name");
        v_BenAdvanceSearch.setGovtIdentityNo("Govt Identity No");
        v_BenAdvanceSearch.setId(1L);
        v_BenAdvanceSearch.setLastName("Doe");
        v_BenAdvanceSearch.setPhoneNo("6625550144");
        v_BenAdvanceSearch.setRegCreatedDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setRegLastModDate(mock(Timestamp.class));
        v_BenAdvanceSearch.setStateID(1);
        v_BenAdvanceSearch.setVillageName("Village Name");

        // Act
        String actualAdvanceSearchBenData = registrarServiceImpl.getAdvanceSearchBenData(v_BenAdvanceSearch);

        // Assert
        verify(v_BenAdvanceSearch, atLeast(1)).getAadharNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getBeneficiaryID();
        verify(v_BenAdvanceSearch, atLeast(1)).getDistrictID();
        verify(v_BenAdvanceSearch, atLeast(1)).getFatherName();
        verify(v_BenAdvanceSearch, atLeast(1)).getFirstName();
        verify(v_BenAdvanceSearch, atLeast(1)).getGovtIdentityNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getLastName();
        verify(v_BenAdvanceSearch, atLeast(1)).getPhoneNo();
        verify(v_BenAdvanceSearch, atLeast(1)).getStateID();
        verify(v_BenAdvanceSearch).setAadharNo(eq("Aadhar No"));
        verify(v_BenAdvanceSearch).setBeneficiaryID(eq("Beneficiary ID"));
        verify(v_BenAdvanceSearch).setBeneficiaryRegID(isA(Long.class));
        verify(v_BenAdvanceSearch).setDistrictBranchID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setDistrictID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setDistrictName(eq("District Name"));
        verify(v_BenAdvanceSearch).setDob(isA(Date.class));
        verify(v_BenAdvanceSearch).setFatherName(eq("Father Name"));
        verify(v_BenAdvanceSearch).setFirstName(eq("Jane"));
        verify(v_BenAdvanceSearch).setFlowStatusFlag(isA(Character.class));
        verify(v_BenAdvanceSearch).setGenderID(isA(Short.class));
        verify(v_BenAdvanceSearch).setGenderName(eq("Gender Name"));
        verify(v_BenAdvanceSearch).setGovtIdentityNo(eq("Govt Identity No"));
        verify(v_BenAdvanceSearch).setId(isA(Long.class));
        verify(v_BenAdvanceSearch).setLastName(eq("Doe"));
        verify(v_BenAdvanceSearch).setPhoneNo(eq("6625550144"));
        verify(v_BenAdvanceSearch).setRegCreatedDate(isA(Timestamp.class));
        verify(v_BenAdvanceSearch).setRegLastModDate(isA(Timestamp.class));
        verify(v_BenAdvanceSearch).setStateID(isA(Integer.class));
        verify(v_BenAdvanceSearch).setVillageName(eq("Village Name"));
        verify(reistrarRepoBenSearch).getAdvanceBenSearchList(eq("Beneficiary ID"), eq("Jane"), eq("Doe"), eq("%%"),
                eq("Aadhar No"), eq("Govt Identity No"), eq("1"), eq("1"));
        assertEquals("[]", actualAdvanceSearchBenData);
    }

    @Test
    void testGetBenOBJ() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("firstName", "42");

        // Act and Assert
        assertEquals("42", registrarServiceImpl.getBenOBJ(benD).getFirstName());
    }

    @Test
    void testGetBenOBJ2() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("firstName", Integer.valueOf(1));

        // Act and Assert
        assertEquals("1", registrarServiceImpl.getBenOBJ(benD).getFirstName());
    }

    @Test
    void testGetBenOBJ3() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("firstName", true);

        // Act
        BeneficiaryData actualBenOBJ = registrarServiceImpl.getBenOBJ(benD);

        // Assert
        String expectedFirstName = Boolean.TRUE.toString();
        assertEquals(expectedFirstName, actualBenOBJ.getFirstName());
    }

    @Test
    void testGetBenOBJ4() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("lastName", "42");

        // Act and Assert
        assertEquals("42", registrarServiceImpl.getBenOBJ(benD).getLastName());
    }

    @Test
    void testGetBenOBJ5() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("gender", "42");

        // Act and Assert
        assertEquals((short) 42, registrarServiceImpl.getBenOBJ(benD).getGenderID().shortValue());
    }

    @Test
    void testGetBenOBJ6() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("maritalStatus", "42");

        // Act and Assert
        assertEquals((short) 42, registrarServiceImpl.getBenOBJ(benD).getMaritalStatusID().shortValue());
    }

    @Test
    void testGetBenOBJ7() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("createdBy", "42");

        // Act and Assert
        assertEquals("42", registrarServiceImpl.getBenOBJ(benD).getCreatedBy());
    }

    @Test
    void testGetBenOBJ8() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("fatherName", "42");

        // Act and Assert
        assertEquals("42", registrarServiceImpl.getBenOBJ(benD).getFatherName());
    }

    @Test
    void testGetBenOBJ9() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("husbandName", "42");

        // Act and Assert
        assertEquals("42", registrarServiceImpl.getBenOBJ(benD).getSpouseName());
    }

    @Test
    void testGetBenOBJ10() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("aadharNo", "42");

        // Act and Assert
        assertEquals("42", registrarServiceImpl.getBenOBJ(benD).getAadharNo());
    }

    @Test
    void testGetBenOBJ11() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("gender", Integer.valueOf(1));

        // Act and Assert
        assertEquals((short) 1, registrarServiceImpl.getBenOBJ(benD).getGenderID().shortValue());
    }

    @Test
    void testGetBenOBJ12() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("beneficiaryRegID", "42");

        // Act and Assert
        assertEquals(42L, registrarServiceImpl.getBenOBJ(benD).getBeneficiaryRegID().longValue());
    }

    @Test
    void testGetBenOBJ13() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("beneficiaryRegID", Integer.valueOf(1));

        // Act and Assert
        assertEquals(1L, registrarServiceImpl.getBenOBJ(benD).getBeneficiaryRegID().longValue());
    }

    @Test
    void testGetBenDemoOBJ() {
        // Arrange, Act and Assert
        assertEquals(1L, registrarServiceImpl.getBenDemoOBJ(new JsonObject(), 1L).getBeneficiaryRegID().longValue());
    }

    @Test
    void testGetBenDemoOBJ2() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("countryID", "42");

        // Act
        BeneficiaryDemographicData actualBenDemoOBJ = registrarServiceImpl.getBenDemoOBJ(benD, 1L);

        // Assert
        assertEquals(1L, actualBenDemoOBJ.getBeneficiaryRegID().longValue());
        assertEquals(42, actualBenDemoOBJ.getCountryID().intValue());
    }

    @Test
    void testGetBenDemoOBJ3() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("countryID", Integer.valueOf(1));

        // Act
        BeneficiaryDemographicData actualBenDemoOBJ = registrarServiceImpl.getBenDemoOBJ(benD, 1L);

        // Assert
        assertEquals(1, actualBenDemoOBJ.getCountryID().intValue());
        assertEquals(1L, actualBenDemoOBJ.getBeneficiaryRegID().longValue());
    }

    @Test
    void testGetBenDemoOBJ4() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("stateID", "42");

        // Act
        BeneficiaryDemographicData actualBenDemoOBJ = registrarServiceImpl.getBenDemoOBJ(benD, 1L);

        // Assert
        assertEquals(1L, actualBenDemoOBJ.getBeneficiaryRegID().longValue());
        assertEquals(42, actualBenDemoOBJ.getStateID().intValue());
    }

    @Test
    void testGetBenDemoOBJ5() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("districtID", "42");

        // Act
        BeneficiaryDemographicData actualBenDemoOBJ = registrarServiceImpl.getBenDemoOBJ(benD, 1L);

        // Assert
        assertEquals(1L, actualBenDemoOBJ.getBeneficiaryRegID().longValue());
        assertEquals(42, actualBenDemoOBJ.getDistrictID().intValue());
    }

    @Test
    void testGetBenDemoOBJ6() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("blockID", "42");

        // Act
        BeneficiaryDemographicData actualBenDemoOBJ = registrarServiceImpl.getBenDemoOBJ(benD, 1L);

        // Assert
        assertEquals(1L, actualBenDemoOBJ.getBeneficiaryRegID().longValue());
        assertEquals(42, actualBenDemoOBJ.getBlockID().intValue());
    }

    @Test
    void testGetBenDemoOBJ7() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("servicePointID", "42");

        // Act
        BeneficiaryDemographicData actualBenDemoOBJ = registrarServiceImpl.getBenDemoOBJ(benD, 1L);

        // Assert
        assertEquals(1L, actualBenDemoOBJ.getBeneficiaryRegID().longValue());
        assertEquals(42, actualBenDemoOBJ.getServicePointID().intValue());
    }

    @Test
    void testGetBenDemoOBJ8() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("villageID", "42");

        // Act
        BeneficiaryDemographicData actualBenDemoOBJ = registrarServiceImpl.getBenDemoOBJ(benD, 1L);

        // Assert
        assertEquals(1L, actualBenDemoOBJ.getBeneficiaryRegID().longValue());
        assertEquals(42, actualBenDemoOBJ.getDistrictBranchID().intValue());
    }

    @Test
    void testGetBenDemoOBJ9() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("createdBy", "42");

        // Act
        BeneficiaryDemographicData actualBenDemoOBJ = registrarServiceImpl.getBenDemoOBJ(benD, 1L);

        // Assert
        assertEquals("42", actualBenDemoOBJ.getCreatedBy());
        assertEquals(1L, actualBenDemoOBJ.getBeneficiaryRegID().longValue());
    }

    @Test
    void testGetBenDemoOBJ10() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("community", "42");

        // Act
        BeneficiaryDemographicData actualBenDemoOBJ = registrarServiceImpl.getBenDemoOBJ(benD, 1L);

        // Assert
        assertEquals(1L, actualBenDemoOBJ.getBeneficiaryRegID().longValue());
        assertEquals((short) 42, actualBenDemoOBJ.getCommunityID().shortValue());
    }

    @Test
    void testGetBenDemoOBJ11() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("religion", "42");

        // Act
        BeneficiaryDemographicData actualBenDemoOBJ = registrarServiceImpl.getBenDemoOBJ(benD, 1L);

        // Assert
        assertEquals(1L, actualBenDemoOBJ.getBeneficiaryRegID().longValue());
        assertEquals((short) 42, actualBenDemoOBJ.getReligionID().shortValue());
    }

    @Test
    void testGetBenDemoOBJ12() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("createdBy", Integer.valueOf(1));

        // Act
        BeneficiaryDemographicData actualBenDemoOBJ = registrarServiceImpl.getBenDemoOBJ(benD, 1L);

        // Assert
        assertEquals("1", actualBenDemoOBJ.getCreatedBy());
        assertEquals(1L, actualBenDemoOBJ.getBeneficiaryRegID().longValue());
    }

    @Test
    void testGetBenDemoOBJ13() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("community", Integer.valueOf(1));

        // Act
        BeneficiaryDemographicData actualBenDemoOBJ = registrarServiceImpl.getBenDemoOBJ(benD, 1L);

        // Assert
        assertEquals(1L, actualBenDemoOBJ.getBeneficiaryRegID().longValue());
        assertEquals((short) 1, actualBenDemoOBJ.getCommunityID().shortValue());
    }

    @Test
    void testGetBenDemoOBJ14() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("occupation", "42");

        // Act
        BeneficiaryDemographicData actualBenDemoOBJ = registrarServiceImpl.getBenDemoOBJ(benD, 1L);

        // Assert
        assertEquals(1L, actualBenDemoOBJ.getBeneficiaryRegID().longValue());
        assertEquals((short) 42, actualBenDemoOBJ.getOccupationID().shortValue());
    }

    @Test
    void testGetBenPhoneOBJ() {
        // Arrange, Act and Assert
        assertEquals(1L, registrarServiceImpl.getBenPhoneOBJ(new JsonObject(), 1L).getBenificiaryRegID().longValue());
    }

    @Test
    void testGetBenPhoneOBJ2() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("phoneNo", "42");

        // Act
        BeneficiaryPhoneMapping actualBenPhoneOBJ = registrarServiceImpl.getBenPhoneOBJ(benD, 1L);

        // Assert
        assertEquals("42", actualBenPhoneOBJ.getPhoneNo());
        assertEquals(1L, actualBenPhoneOBJ.getBenificiaryRegID().longValue());
    }

    @Test
    void testGetBenPhoneOBJ3() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("phoneNo", Integer.valueOf(1));

        // Act
        BeneficiaryPhoneMapping actualBenPhoneOBJ = registrarServiceImpl.getBenPhoneOBJ(benD, 1L);

        // Assert
        assertEquals("1", actualBenPhoneOBJ.getPhoneNo());
        assertEquals(1L, actualBenPhoneOBJ.getBenificiaryRegID().longValue());
    }

    @Test
    void testGetBenPhoneOBJ4() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("phoneNo", true);

        // Act
        BeneficiaryPhoneMapping actualBenPhoneOBJ = registrarServiceImpl.getBenPhoneOBJ(benD, 1L);

        // Assert
        assertEquals(1L, actualBenPhoneOBJ.getBenificiaryRegID().longValue());
        String expectedPhoneNo = Boolean.TRUE.toString();
        assertEquals(expectedPhoneNo, actualBenPhoneOBJ.getPhoneNo());
    }

    @Test
    void testGetBenPhoneOBJ5() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("createdBy", "42");

        // Act
        BeneficiaryPhoneMapping actualBenPhoneOBJ = registrarServiceImpl.getBenPhoneOBJ(benD, 1L);

        // Assert
        assertEquals("42", actualBenPhoneOBJ.getCreatedBy());
        assertEquals(1L, actualBenPhoneOBJ.getBenificiaryRegID().longValue());
    }

    @Test
    void testGetBenPhoneOBJ6() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("benPhMapID", "42");

        // Act
        BeneficiaryPhoneMapping actualBenPhoneOBJ = registrarServiceImpl.getBenPhoneOBJ(benD, 1L);

        // Assert
        assertEquals(1L, actualBenPhoneOBJ.getBenificiaryRegID().longValue());
        assertEquals(42L, actualBenPhoneOBJ.getBenPhMapID().longValue());
    }

    @Test
    void testGetBenPhoneOBJ7() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("modifiedBy", "42");

        // Act
        BeneficiaryPhoneMapping actualBenPhoneOBJ = registrarServiceImpl.getBenPhoneOBJ(benD, 1L);

        // Assert
        assertEquals("42", actualBenPhoneOBJ.getModifiedBy());
        assertEquals(1L, actualBenPhoneOBJ.getBenificiaryRegID().longValue());
    }

   
    @Test
    void testGetBenPhoneOBJ8() {
        // Arrange
        JsonObject benD = new JsonObject();
        benD.addProperty("benPhMapID", Integer.valueOf(1));

        // Act
        BeneficiaryPhoneMapping actualBenPhoneOBJ = registrarServiceImpl.getBenPhoneOBJ(benD, 1L);

        // Assert
        assertEquals(1L, actualBenPhoneOBJ.getBenPhMapID().longValue());
        assertEquals(1L, actualBenPhoneOBJ.getBenificiaryRegID().longValue());
    }

    @Test
    void testGetBeneficiaryDetails() {
        // Arrange
        when(registrarRepoBeneficiaryDetails.getBeneficiaryDetails(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        String actualBeneficiaryDetails = registrarServiceImpl.getBeneficiaryDetails(1L);

        // Assert
        verify(registrarRepoBeneficiaryDetails).getBeneficiaryDetails(isA(Long.class));
        assertNull(actualBeneficiaryDetails);
    }

    @Test
    void testGetBenImage() {
        // Arrange
        when(beneficiaryImageRepo.getBenImage(Mockito.<Long>any())).thenReturn("Ben Image");

        // Act
        String actualBenImage = registrarServiceImpl.getBenImage(1L);

        // Assert
        verify(beneficiaryImageRepo).getBenImage(isA(Long.class));
        assertEquals("{\"benImage\":\"Ben Image\"}", actualBenImage);
    }

    @Test
    void testGetBenImage2() {
        // Arrange
        when(beneficiaryImageRepo.getBenImage(Mockito.<Long>any())).thenReturn(null);

        // Act
        String actualBenImage = registrarServiceImpl.getBenImage(1L);

        // Assert
        verify(beneficiaryImageRepo).getBenImage(isA(Long.class));
        assertEquals("{}", actualBenImage);
    }

    @Test
    void testGetBenImage3() {
        // Arrange
        when(beneficiaryImageRepo.getBenImage(Mockito.<Long>any())).thenReturn("");

        // Act
        String actualBenImage = registrarServiceImpl.getBenImage(1L);

        // Assert
        verify(beneficiaryImageRepo).getBenImage(isA(Long.class));
        assertEquals("{\"benImage\":\"\"}", actualBenImage);
    }

    @Test
    void testUpdateBeneficiary() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(new JsonObject());

        // Assert
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary2() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);

        JsonObject benD = new JsonObject();
        benD.addProperty("firstName", "42");

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(registrarRepoBenData).updateBeneficiaryData(eq("42"), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary3() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);

        JsonObject benD = new JsonObject();
        Integer value = Integer.valueOf(1);
        benD.addProperty("firstName", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(registrarRepoBenData).updateBeneficiaryData(eq("1"), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
        assertSame(value, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary4() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);

        JsonObject benD = new JsonObject();
        benD.addProperty("firstName", true);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(registrarRepoBenData).updateBeneficiaryData(eq("true"), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary5() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);

        JsonObject benD = new JsonObject();
        Integer value = Integer.valueOf(1);
        benD.addProperty("gender", value);
        benD.addProperty("firstName", "42");

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(registrarRepoBenData).updateBeneficiaryData(eq("42"), isNull(), isA(Short.class), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
        assertSame(value, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary6() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);

        JsonObject benD = new JsonObject();
        benD.addProperty("lastName", "firstName");
        benD.addProperty("firstName", true);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(registrarRepoBenData).updateBeneficiaryData(eq("true"), eq("firstName"), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary7() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);

        JsonObject benD = new JsonObject();
        benD.add("firstName", null);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary8() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("firstName", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary9() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("lastName", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary10() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("gender", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary11() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("dob", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary12() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("maritalStatus", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary13() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("createdBy", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary14() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("fatherName", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary15() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("husbandName", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary16() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("aadharNo", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary17() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(false);
        when(value.getAsString()).thenReturn("As String");

        JsonObject benD = new JsonObject();
        benD.add("firstName", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).getAsString();
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(eq("As String"), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary18() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("beneficiaryRegID", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary19() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("modifiedBy", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary20() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(false);
        when(value.getAsString()).thenReturn("As String");

        JsonObject benD = new JsonObject();
        benD.add("lastName", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).getAsString();
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), eq("As String"), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary21() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.getAsShort()).thenReturn((short) 1);
        when(value.isJsonNull()).thenReturn(false);

        JsonObject benD = new JsonObject();
        benD.add("gender", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).getAsShort();
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isA(Short.class), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary22() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(false);
        when(value.getAsString()).thenReturn("As String");

        JsonObject benD = new JsonObject();
        benD.add("dob", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).getAsString();
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary23() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.getAsShort()).thenReturn((short) 1);
        when(value.isJsonNull()).thenReturn(false);

        JsonObject benD = new JsonObject();
        benD.add("maritalStatus", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).getAsShort();
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isA(Short.class),
                isNull(), isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary24() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(false);
        when(value.getAsString()).thenReturn("As String");

        JsonObject benD = new JsonObject();
        benD.add("createdBy", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).getAsString();
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary25() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(false);
        when(value.getAsString()).thenReturn("As String");

        JsonObject benD = new JsonObject();
        benD.add("fatherName", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).getAsString();
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(),
                eq("As String"), isNull(), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary26() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(false);
        when(value.getAsString()).thenReturn("As String");

        JsonObject benD = new JsonObject();
        benD.add("husbandName", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).getAsString();
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                eq("As String"), isNull(), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary27() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(false);
        when(value.getAsString()).thenReturn("As String");

        JsonObject benD = new JsonObject();
        benD.add("aadharNo", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).getAsString();
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), eq("As String"), isNull(), isNull());
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    void testUpdateBeneficiary28() {
        // Arrange
        when(registrarRepoBenData.updateBeneficiaryData(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
                Mockito.<Timestamp>any(), Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.getAsLong()).thenReturn(1L);
        when(value.isJsonNull()).thenReturn(false);

        JsonObject benD = new JsonObject();
        benD.add("beneficiaryRegID", value);

        // Act
        int actualUpdateBeneficiaryResult = registrarServiceImpl.updateBeneficiary(benD);

        // Assert
        verify(value).getAsLong();
        verify(value).isJsonNull();
        verify(registrarRepoBenData).updateBeneficiaryData(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryResult);
    }

    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateBeneficiary29() throws Exception {
        registrarServiceImpl.updateBeneficiary("Coming Request", "JaneDoe");
    }

    @Test
    void testUpdateBeneficiaryDemographic() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(new JsonObject(),
                1L);

        // Assert
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic2() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);

        JsonObject benD = new JsonObject();
        benD.addProperty("countryID", "42");

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(registrarRepoBenDemoData).updateBendemographicData(isA(Integer.class), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic3() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);

        JsonObject benD = new JsonObject();
        benD.addProperty("countryID", Integer.valueOf(1));

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(registrarRepoBenDemoData).updateBendemographicData(isA(Integer.class), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic4() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);

        JsonObject benD = new JsonObject();
        benD.addProperty("districtID", Integer.valueOf(1));
        benD.addProperty("countryID", "42");

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(registrarRepoBenDemoData).updateBendemographicData(isA(Integer.class), isNull(), isA(Integer.class),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic5() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);

        JsonObject benD = new JsonObject();
        benD.add("countryID", null);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic6() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("countryID", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

   
    @Test
    void testUpdateBeneficiaryDemographic7() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("stateID", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic8() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("districtID", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic9() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("blockID", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic10() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("servicePointID", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

   
    @Test
    void testUpdateBeneficiaryDemographic11() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("villageID", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic12() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("createdBy", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic13() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("community", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic14() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("religion", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic15() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(false);
        when(value.getAsInt()).thenReturn(1);

        JsonObject benD = new JsonObject();
        benD.add("countryID", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).getAsInt();
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isA(Integer.class), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic16() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("occupation", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic17() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("educationQualification", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic18() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("income", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic19() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("benDemographicsID", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic20() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("modifiedBy", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic21() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(false);
        when(value.getAsInt()).thenReturn(1);

        JsonObject benD = new JsonObject();
        benD.add("stateID", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).getAsInt();
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isA(Integer.class), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

   
    @Test
    void testUpdateBeneficiaryDemographic22() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(false);
        when(value.getAsInt()).thenReturn(1);

        JsonObject benD = new JsonObject();
        benD.add("districtID", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).getAsInt();
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isA(Integer.class), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryDemographic23() {
        // Arrange
        when(registrarRepoBenDemoData.updateBendemographicData(Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<Integer>any(),
                Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(), Mockito.<Short>any(),
                Mockito.<String>any(), Mockito.<Long>any())).thenReturn(3);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(false);
        when(value.getAsInt()).thenReturn(1);

        JsonObject benD = new JsonObject();
        benD.add("blockID", value);

        // Act
        int actualUpdateBeneficiaryDemographicResult = registrarServiceImpl.updateBeneficiaryDemographic(benD, 1L);

        // Assert
        verify(value).getAsInt();
        verify(value).isJsonNull();
        verify(registrarRepoBenDemoData).updateBendemographicData(isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(3, actualUpdateBeneficiaryDemographicResult);
    }

    @Test
    void testUpdateBeneficiaryPhoneMapping() {
        // Arrange
        when(registrarRepoBenPhoneMapData.updateBenPhoneMap(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any())).thenReturn(1);

        // Act
        int actualUpdateBeneficiaryPhoneMappingResult = registrarServiceImpl.updateBeneficiaryPhoneMapping(new JsonObject(),
                1L);

        // Assert
        verify(registrarRepoBenPhoneMapData).updateBenPhoneMap(isNull(), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryPhoneMappingResult);
    }

    @Test
    void testUpdateBeneficiaryPhoneMapping2() {
        // Arrange
        when(registrarRepoBenPhoneMapData.updateBenPhoneMap(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any())).thenReturn(1);

        JsonObject benD = new JsonObject();
        benD.addProperty("phoneNo", "42");

        // Act
        int actualUpdateBeneficiaryPhoneMappingResult = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, 1L);

        // Assert
        verify(registrarRepoBenPhoneMapData).updateBenPhoneMap(eq("42"), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryPhoneMappingResult);
    }

    @Test
    void testUpdateBeneficiaryPhoneMapping3() {
        // Arrange
        when(registrarRepoBenPhoneMapData.updateBenPhoneMap(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any())).thenReturn(1);

        JsonObject benD = new JsonObject();
        Integer value = Integer.valueOf(1);
        benD.addProperty("phoneNo", value);

        // Act
        int actualUpdateBeneficiaryPhoneMappingResult = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, 1L);

        // Assert
        verify(registrarRepoBenPhoneMapData).updateBenPhoneMap(eq("1"), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryPhoneMappingResult);
        assertSame(value, actualUpdateBeneficiaryPhoneMappingResult);
    }

    @Test
    void testUpdateBeneficiaryPhoneMapping4() {
        // Arrange
        when(registrarRepoBenPhoneMapData.updateBenPhoneMap(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any())).thenReturn(1);

        JsonObject benD = new JsonObject();
        benD.addProperty("phoneNo", true);

        // Act
        int actualUpdateBeneficiaryPhoneMappingResult = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, 1L);

        // Assert
        verify(registrarRepoBenPhoneMapData).updateBenPhoneMap(eq("true"), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryPhoneMappingResult);
    }

    @Test
    void testUpdateBeneficiaryPhoneMapping5() {
        // Arrange
        when(registrarRepoBenPhoneMapData.updateBenPhoneMap(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any())).thenReturn(1);

        JsonObject benD = new JsonObject();
        Integer value = Integer.valueOf(1);
        benD.addProperty("benPhMapID", value);
        benD.addProperty("phoneNo", "42");

        // Act
        int actualUpdateBeneficiaryPhoneMappingResult = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, 1L);

        // Assert
        verify(registrarRepoBenPhoneMapData).updateBenPhoneMap(eq("42"), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryPhoneMappingResult);
        assertSame(value, actualUpdateBeneficiaryPhoneMappingResult);
    }

    @Test
    void testUpdateBeneficiaryPhoneMapping6() {
        // Arrange
        when(registrarRepoBenPhoneMapData.updateBenPhoneMap(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any())).thenReturn(1);

        JsonObject benD = new JsonObject();
        benD.addProperty("createdBy", "phoneNo");
        benD.addProperty("phoneNo", true);

        // Act
        int actualUpdateBeneficiaryPhoneMappingResult = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, 1L);

        // Assert
        verify(registrarRepoBenPhoneMapData).updateBenPhoneMap(eq("true"), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryPhoneMappingResult);
    }

    @Test
    void testUpdateBeneficiaryPhoneMapping7() {
        // Arrange
        when(registrarRepoBenPhoneMapData.updateBenPhoneMap(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any())).thenReturn(1);

        JsonObject benD = new JsonObject();
        benD.add("phoneNo", null);

        // Act
        int actualUpdateBeneficiaryPhoneMappingResult = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, 1L);

        // Assert
        verify(registrarRepoBenPhoneMapData).updateBenPhoneMap(isNull(), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryPhoneMappingResult);
    }

    @Test
    void testUpdateBeneficiaryPhoneMapping8() {
        // Arrange
        when(registrarRepoBenPhoneMapData.updateBenPhoneMap(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("phoneNo", value);

        // Act
        int actualUpdateBeneficiaryPhoneMappingResult = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenPhoneMapData).updateBenPhoneMap(isNull(), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryPhoneMappingResult);
    }

    @Test
    void testUpdateBeneficiaryPhoneMapping9() {
        // Arrange
        when(registrarRepoBenPhoneMapData.updateBenPhoneMap(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("createdBy", value);

        // Act
        int actualUpdateBeneficiaryPhoneMappingResult = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenPhoneMapData).updateBenPhoneMap(isNull(), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryPhoneMappingResult);
    }

    @Test
    void testUpdateBeneficiaryPhoneMapping10() {
        // Arrange
        when(registrarRepoBenPhoneMapData.updateBenPhoneMap(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("benPhMapID", value);

        // Act
        int actualUpdateBeneficiaryPhoneMappingResult = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenPhoneMapData).updateBenPhoneMap(isNull(), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryPhoneMappingResult);
    }

    @Test
    void testUpdateBeneficiaryPhoneMapping11() {
        // Arrange
        when(registrarRepoBenPhoneMapData.updateBenPhoneMap(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(true);

        JsonObject benD = new JsonObject();
        benD.add("modifiedBy", value);

        // Act
        int actualUpdateBeneficiaryPhoneMappingResult = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, 1L);

        // Assert
        verify(value).isJsonNull();
        verify(registrarRepoBenPhoneMapData).updateBenPhoneMap(isNull(), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryPhoneMappingResult);
    }

    @Test
    void testUpdateBeneficiaryPhoneMapping12() {
        // Arrange
        when(registrarRepoBenPhoneMapData.updateBenPhoneMap(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(false);
        when(value.getAsString()).thenReturn("As String");

        JsonObject benD = new JsonObject();
        benD.add("phoneNo", value);

        // Act
        int actualUpdateBeneficiaryPhoneMappingResult = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, 1L);

        // Assert
        verify(value).getAsString();
        verify(value).isJsonNull();
        verify(registrarRepoBenPhoneMapData).updateBenPhoneMap(eq("As String"), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryPhoneMappingResult);
    }

    @Test
    void testUpdateBeneficiaryPhoneMapping13() {
        // Arrange
        when(registrarRepoBenPhoneMapData.updateBenPhoneMap(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(false);
        when(value.getAsString()).thenReturn("As String");

        JsonObject benD = new JsonObject();
        benD.add("createdBy", value);

        // Act
        int actualUpdateBeneficiaryPhoneMappingResult = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, 1L);

        // Assert
        verify(value).getAsString();
        verify(value).isJsonNull();
        verify(registrarRepoBenPhoneMapData).updateBenPhoneMap(isNull(), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryPhoneMappingResult);
    }

    @Test
    void testUpdateBeneficiaryPhoneMapping14() {
        // Arrange
        when(registrarRepoBenPhoneMapData.updateBenPhoneMap(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.getAsLong()).thenReturn(1L);
        when(value.isJsonNull()).thenReturn(false);

        JsonObject benD = new JsonObject();
        benD.add("benPhMapID", value);

        // Act
        int actualUpdateBeneficiaryPhoneMappingResult = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, 1L);

        // Assert
        verify(value).getAsLong();
        verify(value).isJsonNull();
        verify(registrarRepoBenPhoneMapData).updateBenPhoneMap(isNull(), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryPhoneMappingResult);
    }

    @Test
    void testUpdateBeneficiaryPhoneMapping15() {
        // Arrange
        when(registrarRepoBenPhoneMapData.updateBenPhoneMap(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Long>any())).thenReturn(1);
        JsonElement value = mock(JsonElement.class);
        when(value.isJsonNull()).thenReturn(false);
        when(value.getAsString()).thenReturn("As String");

        JsonObject benD = new JsonObject();
        benD.add("modifiedBy", value);

        // Act
        int actualUpdateBeneficiaryPhoneMappingResult = registrarServiceImpl.updateBeneficiaryPhoneMapping(benD, 1L);

        // Assert
        verify(value).getAsString();
        verify(value).isJsonNull();
        verify(registrarRepoBenPhoneMapData).updateBenPhoneMap(isNull(), eq("As String"), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryPhoneMappingResult);
    }

    @Test
    void testUpdateBenGovIdMapping() {
        // Arrange
        when(registrarRepoBenGovIdMapping.deletePreviousGovMapID(Mockito.<Long>any())).thenReturn(1);
        when(registrarRepoBenGovIdMapping.saveAll(Mockito.<Iterable<BenGovIdMapping>>any())).thenReturn(new ArrayList<>());

        JsonObject benD = new JsonObject();
        benD.add("govID", new JsonArray(3));

        // Act
        int actualUpdateBenGovIdMappingResult = registrarServiceImpl.updateBenGovIdMapping(benD, 1L);

        // Assert
        verify(registrarRepoBenGovIdMapping).deletePreviousGovMapID(isA(Long.class));
        verify(registrarRepoBenGovIdMapping).saveAll(isA(Iterable.class));
        assertEquals(0, actualUpdateBenGovIdMappingResult);
    }

    @Test
    void testUpdateBenGovIdMapping2() {
        // Arrange
        when(registrarRepoBenGovIdMapping.deletePreviousGovMapID(Mockito.<Long>any())).thenReturn(1);
        when(registrarRepoBenGovIdMapping.saveAll(Mockito.<Iterable<BenGovIdMapping>>any())).thenReturn(new ArrayList<>());

        JsonObject benD = new JsonObject();
        benD.add("Property", new JsonArray(3));
        benD.add("govID", new JsonArray(3));

        // Act
        int actualUpdateBenGovIdMappingResult = registrarServiceImpl.updateBenGovIdMapping(benD, 1L);

        // Assert
        verify(registrarRepoBenGovIdMapping).deletePreviousGovMapID(isA(Long.class));
        verify(registrarRepoBenGovIdMapping).saveAll(isA(Iterable.class));
        assertEquals(0, actualUpdateBenGovIdMappingResult);
    }

    @Test
    void testUpdateBeneficiaryDemographicAdditional() {
        // Arrange
        BeneficiaryDemographicAdditional beneficiaryDemographicAdditional = new BeneficiaryDemographicAdditional();
        beneficiaryDemographicAdditional.setAccountNo("3");
        beneficiaryDemographicAdditional.setBankName("Bank Name");
        beneficiaryDemographicAdditional.setBenDemoAdditionalID(1L);
        beneficiaryDemographicAdditional.setBeneficiaryRegID(1L);
        beneficiaryDemographicAdditional.setBranchName("janedoe/featurebranch");
        beneficiaryDemographicAdditional.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryDemographicAdditional.setCreatedDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setDeleted(true);
        beneficiaryDemographicAdditional.setEmailID("jane.doe@example.org");
        beneficiaryDemographicAdditional.setLastModDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setLiteracyStatus("Literacy Status");
        beneficiaryDemographicAdditional.setMarrigeDate(mock(Timestamp.class));
        beneficiaryDemographicAdditional.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryDemographicAdditional.setMotherName("Mother Name");
        beneficiaryDemographicAdditional.setProcessed('A');
        beneficiaryDemographicAdditional.setiFSCCode("I FSCCode");
        when(beneficiaryDemographicAdditionalRepo.updateBeneficiaryDemographicAdditional(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(1);
        when(beneficiaryDemographicAdditionalRepo.getBeneficiaryDemographicAdditional(Mockito.<Long>any()))
                .thenReturn(beneficiaryDemographicAdditional);

        // Act
        int actualUpdateBeneficiaryDemographicAdditionalResult = registrarServiceImpl
                .updateBeneficiaryDemographicAdditional(new JsonObject(), 1L);

        // Assert
        verify(beneficiaryDemographicAdditionalRepo).getBeneficiaryDemographicAdditional(isA(Long.class));
        verify(beneficiaryDemographicAdditionalRepo).updateBeneficiaryDemographicAdditional(isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryDemographicAdditionalResult);
    }

  
    @Test
    void testUpdateBeneficiaryImage() {
        // Arrange, Act and Assert
        assertEquals(1, registrarServiceImpl.updateBeneficiaryImage(new JsonObject(), 1L));
    }

    @Test
    void testUpdateBeneficiaryImage2() {
        // Arrange
        when(beneficiaryImageRepo.updateBeneficiaryImage(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any()))
                .thenReturn(1);
        when(beneficiaryImageRepo.findBenImage(Mockito.<Long>any())).thenReturn(1L);

        JsonObject benD = new JsonObject();
        benD.addProperty("image", "42");

        // Act
        int actualUpdateBeneficiaryImageResult = registrarServiceImpl.updateBeneficiaryImage(benD, 1L);

        // Assert
        verify(beneficiaryImageRepo).findBenImage(isA(Long.class));
        verify(beneficiaryImageRepo).updateBeneficiaryImage(eq("42"), isNull(), isA(Long.class));
        assertEquals(1, actualUpdateBeneficiaryImageResult);
    }

    @Test
    void testUpdateBeneficiaryImage3() {
        // Arrange
        BeneficiaryImage beneficiaryImage = new BeneficiaryImage();
        beneficiaryImage.setBenImage("Ben Image");
        beneficiaryImage.setBenImageID(1L);
        beneficiaryImage.setBeneficiaryRegID(1L);
        beneficiaryImage.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryImage.setCreatedDate(mock(Timestamp.class));
        beneficiaryImage.setDeleted(true);
        beneficiaryImage.setLastModDate(mock(Timestamp.class));
        beneficiaryImage.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryImage.setProcessed(true);
        when(beneficiaryImageRepo.save(Mockito.<BeneficiaryImage>any())).thenReturn(beneficiaryImage);
        when(beneficiaryImageRepo.findBenImage(Mockito.<Long>any())).thenReturn(null);

        JsonObject benD = new JsonObject();
        benD.addProperty("image", "42");

        // Act
        int actualUpdateBeneficiaryImageResult = registrarServiceImpl.updateBeneficiaryImage(benD, 1L);

        // Assert
        verify(beneficiaryImageRepo).findBenImage(isA(Long.class));
        verify(beneficiaryImageRepo).save(isA(BeneficiaryImage.class));
        assertEquals(1, actualUpdateBeneficiaryImageResult);
    }

    @Test
    void testUpdateBeneficiaryImage4() {
        // Arrange
        BeneficiaryImage beneficiaryImage = mock(BeneficiaryImage.class);
        when(beneficiaryImage.getBenImageID()).thenReturn(-1L);
        doNothing().when(beneficiaryImage).setBenImage(Mockito.<String>any());
        doNothing().when(beneficiaryImage).setBenImageID(Mockito.<Long>any());
        doNothing().when(beneficiaryImage).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(beneficiaryImage).setCreatedBy(Mockito.<String>any());
        doNothing().when(beneficiaryImage).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryImage).setDeleted(Mockito.<Boolean>any());
        doNothing().when(beneficiaryImage).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryImage).setModifiedBy(Mockito.<String>any());
        doNothing().when(beneficiaryImage).setProcessed(Mockito.<Boolean>any());
        beneficiaryImage.setBenImage("Ben Image");
        beneficiaryImage.setBenImageID(1L);
        beneficiaryImage.setBeneficiaryRegID(1L);
        beneficiaryImage.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryImage.setCreatedDate(mock(Timestamp.class));
        beneficiaryImage.setDeleted(true);
        beneficiaryImage.setLastModDate(mock(Timestamp.class));
        beneficiaryImage.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryImage.setProcessed(true);
        when(beneficiaryImageRepo.save(Mockito.<BeneficiaryImage>any())).thenReturn(beneficiaryImage);
        when(beneficiaryImageRepo.findBenImage(Mockito.<Long>any())).thenReturn(null);

        JsonObject benD = new JsonObject();
        benD.addProperty("image", "42");

        // Act
        int actualUpdateBeneficiaryImageResult = registrarServiceImpl.updateBeneficiaryImage(benD, 1L);

        // Assert
        verify(beneficiaryImage).getBenImageID();
        verify(beneficiaryImage).setBenImage(eq("Ben Image"));
        verify(beneficiaryImage).setBenImageID(isA(Long.class));
        verify(beneficiaryImage).setBeneficiaryRegID(isA(Long.class));
        verify(beneficiaryImage).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(beneficiaryImage).setCreatedDate(isA(Timestamp.class));
        verify(beneficiaryImage).setDeleted(isA(Boolean.class));
        verify(beneficiaryImage).setLastModDate(isA(Timestamp.class));
        verify(beneficiaryImage).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(beneficiaryImage).setProcessed(isA(Boolean.class));
        verify(beneficiaryImageRepo).findBenImage(isA(Long.class));
        verify(beneficiaryImageRepo).save(isA(BeneficiaryImage.class));
        assertEquals(0, actualUpdateBeneficiaryImageResult);
    }

    @Test
    void testUpdateBeneficiaryImage5() {
        // Arrange
        BeneficiaryImage beneficiaryImage = mock(BeneficiaryImage.class);
        doNothing().when(beneficiaryImage).setBenImage(Mockito.<String>any());
        doNothing().when(beneficiaryImage).setBenImageID(Mockito.<Long>any());
        doNothing().when(beneficiaryImage).setBeneficiaryRegID(Mockito.<Long>any());
        doNothing().when(beneficiaryImage).setCreatedBy(Mockito.<String>any());
        doNothing().when(beneficiaryImage).setCreatedDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryImage).setDeleted(Mockito.<Boolean>any());
        doNothing().when(beneficiaryImage).setLastModDate(Mockito.<Timestamp>any());
        doNothing().when(beneficiaryImage).setModifiedBy(Mockito.<String>any());
        doNothing().when(beneficiaryImage).setProcessed(Mockito.<Boolean>any());
        beneficiaryImage.setBenImage("Ben Image");
        beneficiaryImage.setBenImageID(1L);
        beneficiaryImage.setBeneficiaryRegID(1L);
        beneficiaryImage.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        beneficiaryImage.setCreatedDate(mock(Timestamp.class));
        beneficiaryImage.setDeleted(true);
        beneficiaryImage.setLastModDate(mock(Timestamp.class));
        beneficiaryImage.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        beneficiaryImage.setProcessed(true);

        JsonObject benD = new JsonObject();
        benD.addProperty("image", (String) null);

        // Act
        int actualUpdateBeneficiaryImageResult = registrarServiceImpl.updateBeneficiaryImage(benD, 1L);

        // Assert
        verify(beneficiaryImage).setBenImage(eq("Ben Image"));
        verify(beneficiaryImage).setBenImageID(isA(Long.class));
        verify(beneficiaryImage).setBeneficiaryRegID(isA(Long.class));
        verify(beneficiaryImage).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
        verify(beneficiaryImage).setCreatedDate(isA(Timestamp.class));
        verify(beneficiaryImage).setDeleted(isA(Boolean.class));
        verify(beneficiaryImage).setLastModDate(isA(Timestamp.class));
        verify(beneficiaryImage).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
        verify(beneficiaryImage).setProcessed(isA(Boolean.class));
        assertEquals(1, actualUpdateBeneficiaryImageResult);
    }

    @Test
    void testGetBeneficiaryPersonalDetails() {
        // Arrange
        when(registrarRepoBenData.getBenDetailsByRegID(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        when(registrarRepoBenDemoData.getBeneficiaryDemographicData(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        BeneficiaryData actualBeneficiaryPersonalDetails = registrarServiceImpl.getBeneficiaryPersonalDetails(1L);

        // Assert
        verify(registrarRepoBenData).getBenDetailsByRegID(isA(Long.class));
        verify(registrarRepoBenDemoData).getBeneficiaryDemographicData(isA(Long.class));
        assertNull(actualBeneficiaryPersonalDetails);
    }

    @Test
    void testRegisterBeneficiary() throws Exception {
      
        registrarServiceImpl.registerBeneficiary("Coming Request", "JaneDoe");
    }

    @Test
    void testGetBeneficiaryByBlockIDAndLastModDate() {
        // Arrange, Act and Assert
        assertNull(registrarServiceImpl.getBeneficiaryByBlockIDAndLastModDate("Village ID",
                java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "JaneDoe"));
        assertNull(registrarServiceImpl.getBeneficiaryByBlockIDAndLastModDate("42",
                java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "JaneDoe"));
        assertNull(
                registrarServiceImpl.getBeneficiaryByBlockIDAndLastModDate("Village ID", mock(java.sql.Date.class), "JaneDoe"));
        assertNull(registrarServiceImpl.getBeneficiaryByBlockIDAndLastModDate("42",
                java.util.Date.from(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "JaneDoe"));
        assertNull(registrarServiceImpl.getBeneficiaryByBlockIDAndLastModDate("42", null, "JaneDoe"));
    }

   
    @Test
    void testBeneficiaryQuickSearch() {
        
        registrarServiceImpl.beneficiaryQuickSearch("Request Obj", "JaneDoe");
    }

    @Test
    void testBeneficiaryAdvanceSearch() {
       
        registrarServiceImpl.beneficiaryAdvanceSearch("Request Obj", "JaneDoe");
    }

    
    @Test
    void testSearchAndSubmitBeneficiaryToNurse() throws Exception {
        // Arrange
        when(commonBenStatusFlowServiceImpl.createBenFlowRecord(Mockito.<String>any(), Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(1);

        // Act
        int actualSearchAndSubmitBeneficiaryToNurseResult = registrarServiceImpl
                .searchAndSubmitBeneficiaryToNurse("Request OBJ");

        // Assert
        verify(commonBenStatusFlowServiceImpl).createBenFlowRecord(eq("Request OBJ"), isNull(), isNull());
        assertEquals(1, actualSearchAndSubmitBeneficiaryToNurseResult);
    }

    @Test
    void testSaveFingerprints() {
        // Arrange
        UserBiometricsMapping userBiometricsMapping = new UserBiometricsMapping();
        userBiometricsMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userBiometricsMapping.setCreatedDate(mock(Timestamp.class));
        userBiometricsMapping.setDeleted(true);
        userBiometricsMapping.setFirstName("Jane");
        userBiometricsMapping.setGUIDLeftFinger("1234");
        userBiometricsMapping.setGUIDRightFinger("1234");
        userBiometricsMapping.setLastModDate(mock(Timestamp.class));
        userBiometricsMapping.setLastName("Doe");
        userBiometricsMapping.setLeftIndexFinger("Left Index Finger");
        userBiometricsMapping.setLeftThumb("Left Thumb");
        userBiometricsMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        userBiometricsMapping.setProcessed("Processed");
        userBiometricsMapping.setRightIndexFinger("Right Index Finger");
        userBiometricsMapping.setRightThumb("Right Thumb");
        userBiometricsMapping.setUserBiometricMapID(1L);
        userBiometricsMapping.setUserID(1L);
        userBiometricsMapping.setUserName("janedoe");
        when(userBiometricsRepo.save(Mockito.<UserBiometricsMapping>any())).thenReturn(userBiometricsMapping);

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
        when(userLoginRepo.getUserByUsername(Mockito.<String>any())).thenReturn(users);

        FingerPrintDTO comingRequest = new FingerPrintDTO();
        comingRequest.setId(1);
        comingRequest.setLeftIndexFinger("Left Index Finger");
        comingRequest.setLeftThumb("Left Thumb");
        comingRequest.setRightIndexFinger("Right Index Finger");
        comingRequest.setRightThumb("Right Thumb");
        comingRequest.setUserName("janedoe");

        // Act
        String actualSaveFingerprintsResult = registrarServiceImpl.saveFingerprints(comingRequest);

        // Assert
        verify(userLoginRepo).getUserByUsername(eq("janedoe"));
        verify(userBiometricsRepo).save(isA(UserBiometricsMapping.class));
        assertEquals("ok", actualSaveFingerprintsResult);
    }

    @Test
    void testGetFingerprintsByUserID() {
        // Arrange
        UserBiometricsMapping userBiometricsMapping = new UserBiometricsMapping();
        userBiometricsMapping.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userBiometricsMapping.setCreatedDate(mock(Timestamp.class));
        userBiometricsMapping.setDeleted(true);
        userBiometricsMapping.setFirstName("Jane");
        userBiometricsMapping.setGUIDLeftFinger("1234");
        userBiometricsMapping.setGUIDRightFinger("1234");
        userBiometricsMapping.setLastModDate(mock(Timestamp.class));
        userBiometricsMapping.setLastName("Doe");
        userBiometricsMapping.setLeftIndexFinger("Left Index Finger");
        userBiometricsMapping.setLeftThumb("Left Thumb");
        userBiometricsMapping.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        userBiometricsMapping.setProcessed("Processed");
        userBiometricsMapping.setRightIndexFinger("Right Index Finger");
        userBiometricsMapping.setRightThumb("Right Thumb");
        userBiometricsMapping.setUserBiometricMapID(1L);
        userBiometricsMapping.setUserID(1L);
        userBiometricsMapping.setUserName("janedoe");
        when(userBiometricsRepo.getFingerprintsByUserID(Mockito.<Long>any())).thenReturn(userBiometricsMapping);

        // Act
        UserBiometricsMapping actualFingerprintsByUserID = registrarServiceImpl.getFingerprintsByUserID(1L);

        // Assert
        verify(userBiometricsRepo).getFingerprintsByUserID(isA(Long.class));
        assertSame(userBiometricsMapping, actualFingerprintsByUserID);
    }

   
    @Test
    void testGettersAndSetters() {
      
        RegistrarServiceImpl registrarServiceImpl = new RegistrarServiceImpl();

        // Act
        registrarServiceImpl.setBeneficiaryDemographicAdditionalRepo(mock(BeneficiaryDemographicAdditionalRepo.class));
        registrarServiceImpl.setBeneficiaryImageRepo(mock(BeneficiaryImageRepo.class));
        registrarServiceImpl.setCommonBenStatusFlowServiceImpl(new CommonBenStatusFlowServiceImpl());
        registrarServiceImpl.setRegistrarRepoBenData(mock(RegistrarRepoBenData.class));
        registrarServiceImpl.setRegistrarRepoBenDemoData(mock(RegistrarRepoBenDemoData.class));
        registrarServiceImpl.setRegistrarRepoBenGovIdMapping(mock(RegistrarRepoBenGovIdMapping.class));
        registrarServiceImpl.setRegistrarRepoBenPhoneMapData(mock(RegistrarRepoBenPhoneMapData.class));
        registrarServiceImpl.setRegistrarRepoBeneficiaryDetails(mock(RegistrarRepoBeneficiaryDetails.class));
        registrarServiceImpl.setReistrarRepoAdvanceBenSearch(mock(ReistrarRepoBenSearch.class));
    }
}
