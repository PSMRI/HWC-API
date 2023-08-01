package com.iemr.hwc.fhir.model.patient;

import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import ca.uhn.fhir.util.ElementUtil;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.StringType;

@ResourceDef(name = "Patient")
public class PatientExt extends Patient {

    @Description(shortDefinition = "Contains father's name")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.main.fatherName", isModifier = false, definedLocally = true)
    @Child(name = "fatherName")
    private StringType fatherName;

    @Description(shortDefinition = "Contains spouse's name, If beneficiary is married")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.main.spouseName", isModifier = false, definedLocally = true)
    @Child(name = "spouseName")
    private StringType spouseName;

    @Description(shortDefinition = "Contains age when married, If beneficiary is married")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.main.ageAtMarriage", isModifier = false, definedLocally = true)
    @Child(name = "ageAtMarriage")
    private StringType ageAtMarriage;

    @Description(shortDefinition = "Mentions Abha generation mode")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.main.abhaGenerationMode", isModifier = false, definedLocally = true)
    @Child(name = "abhaGenerationMode")
    private Coding abhaGenerationMode;

    @Description(shortDefinition = "Contains providerServiceMapId ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.main.providerServiceMapId", isModifier = false, definedLocally = true)
    @Child(name = "providerServiceMapId")
    private StringType providerServiceMapId;

    @Description(shortDefinition = "Contains vanID ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.main.vanID", isModifier = false, definedLocally = true)
    @Child(name = "vanID")
    private StringType vanID;

    @Description(shortDefinition = "Contains parkingPlaceID ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.main.parkingPlaceID", isModifier = false, definedLocally = true)
    @Child(name = "parkingPlaceID")
    private StringType parkingPlaceID;

    @Description(shortDefinition = "Contains createdBy ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.main.createdBy", isModifier = false, definedLocally = true)
    @Child(name = "createdBy")
    private StringType createdBy;

    @Description(shortDefinition = "Contains State details ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.demographics.state", isModifier = false, definedLocally = true)
    @Child(name = "state")
    private Coding state;

    @Description(shortDefinition = "Contains District details ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.demographics.district", isModifier = false, definedLocally = true)
    @Child(name = "district")
    private Coding district;

    @Description(shortDefinition = "Contains block details ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.demographics.block", isModifier = false, definedLocally = true)
    @Child(name = "block")
    private Coding block;

    @Description(shortDefinition = "Contains districtBranch details ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.demographics.districtBranch", isModifier = false, definedLocally = true)
    @Child(name = "districtBranch")
    private Coding districtBranch;

    @Description(shortDefinition = "Contains religion details ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.demographics.religion", isModifier = false, definedLocally = true)
    @Child(name = "religion")
    private Coding religion;

    @Description(shortDefinition = "Contains community details ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.demographics.community", isModifier = false, definedLocally = true)
    @Child(name = "community")
    private Coding community;

    @Description(shortDefinition = "Contains govtIdentityType details ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.identity.govtIdentityType", isModifier = false, definedLocally = true)
    @Child(name = "govtIdentityType")
    private Coding govtIdentityType;

    @Description(shortDefinition = "Contains govtIdentityNo ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.identity.govtIdentityNo", isModifier = false, definedLocally = true)
    @Child(name = "govtIdentityNo")
    private StringType govtIdentityNo;

    @Description(shortDefinition = "Contains govtHealthProgramType details ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.identity.govtHealthProgramType", isModifier = false, definedLocally = true)
    @Child(name = "govtHealthProgramType")
    private Coding govtHealthProgramType;

    @Description(shortDefinition = "Contains govtHealthProgramId ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Patient#Patient.identity.govtHealthProgramId", isModifier = false, definedLocally = true)
    @Child(name = "govtHealthProgramId")
    private StringType govtHealthProgramId;


    public StringType getFatherName() {
        if (fatherName == null) {
            fatherName = new StringType();
        }
        return fatherName;
    }

    public void setFatherName(StringType father_Name) {
        fatherName =  father_Name;
    }

    public StringType getSpouseName() {
        if (spouseName == null) {
            spouseName = new StringType();
        }
        return spouseName;
    }

    public void setSpouseName(StringType spouse_Name) {
        spouseName =  spouse_Name;
    }

    public StringType getAgeAtMarriage() {
        if (ageAtMarriage == null) {
            ageAtMarriage = new StringType();
        }
        return ageAtMarriage;
    }

    public void setAgeAtMarriage(StringType age_At_Marriage) {
        ageAtMarriage =  age_At_Marriage;
    }

    public Coding getAbhaGenerationMode() {
        if (abhaGenerationMode == null) {
            abhaGenerationMode = new Coding();
        }
        return abhaGenerationMode;
    }

    public void setAbhaGenerationMode(Coding abha_Generation_Mode) {
        abhaGenerationMode =  abha_Generation_Mode;
    }

    public StringType getProviderServiceMapId() {
        if (providerServiceMapId == null) {
            providerServiceMapId = new StringType();
        }
        return providerServiceMapId;
    }

    public void setProviderServiceMapId(StringType provider_Service_MapId) {
        providerServiceMapId =  provider_Service_MapId;
    }

    public StringType getParkingPlaceID() {
        if (parkingPlaceID == null) {
            parkingPlaceID = new StringType();
        }
        return parkingPlaceID;
    }

    public void setParkingPlaceID(StringType parking_Place_ID) {
        parkingPlaceID =  parking_Place_ID;
    }

    public StringType getVanID() {
        if (vanID == null) {
            vanID = new StringType();
        }
        return vanID;
    }

    public void setVanID(StringType van_ID) {
        vanID =  van_ID;
    }

    public StringType getCreatedBy() {
        if (createdBy == null) {
            createdBy = new StringType();
        }
        return createdBy;
    }

    public void setCreatedBy(StringType created_By) {
        createdBy =  created_By;
    }

    public Coding getState() {
        if (state == null) {
            state = new Coding();
        }
        return state;
    }

    public void setState(Coding stateDetails) {
        state = stateDetails;
    }

    public Coding getDistrict() {
        if (district == null) {
            district = new Coding();
        }
        return district;
    }

    public void setDistrict(Coding districtDetails) {
        district = districtDetails;
    }

    public Coding getBlock() {
        if (block == null) {
            block = new Coding();
        }
        return block;
    }

    public void setBlock(Coding blockDetails) {
        block = blockDetails;
    }

    public Coding getDistrictBranch() {
        if (districtBranch == null) {
            districtBranch = new Coding();
        }
        return districtBranch;
    }

    public void setDistrictBranch(Coding district_Branch) {
        districtBranch = district_Branch;
    }

    public Coding getReligion() {
        if (religion == null) {
            religion = new Coding();
        }
        return religion;
    }

    public void setReligion(Coding religionDetails) {
        religion = religionDetails;
    }

    public Coding getCommunity() {
        if (community == null) {
            community = new Coding();
        }
        return community;
    }

    public void setCommunity(Coding communityDetails) {
        community = communityDetails;
    }

    public Coding getGovtIdentityType() {
        if (govtIdentityType == null) {
            govtIdentityType = new Coding();
        }
        return govtIdentityType;
    }

    public void setGovtIdentityType(Coding govtIdentity_Type) {
        govtIdentityType = govtIdentity_Type;
    }

    public StringType getGovtIdentityNo() {
        if (govtIdentityNo == null) {
            govtIdentityNo = new StringType();
        }
        return govtIdentityNo;
    }

    public void setGovtIdentityNo(StringType govt_Identity_No) {
        govtIdentityNo =  govt_Identity_No;
    }

    public Coding getGovtHealthProgramType() {
        if (govtHealthProgramType == null) {
            govtHealthProgramType = new Coding();
        }
        return govtHealthProgramType;
    }

    public void setGovtHealthProgramType(Coding govtHealthProgram_Type) {
        govtHealthProgramType = govtHealthProgram_Type;
    }

    public StringType getGovtHealthProgramId() {
        if (govtHealthProgramId == null) {
            govtHealthProgramId = new StringType();
        }
        return govtHealthProgramId;
    }

    public void setGovtHealthProgramId(StringType govtHealthProgram_Id) {
        govtHealthProgramId =  govtHealthProgram_Id;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && ElementUtil.isEmpty(fatherName , spouseName , ageAtMarriage , abhaGenerationMode ,
                providerServiceMapId , vanID , parkingPlaceID , createdBy ,
                state , district , block , districtBranch , religion , community ,
                govtIdentityType , govtIdentityNo , govtHealthProgramType , govtHealthProgramId);
    }

}
