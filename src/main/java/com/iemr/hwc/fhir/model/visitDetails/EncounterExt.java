package com.iemr.hwc.fhir.model.visitDetails;

import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.StringType;

@ResourceDef(name = "Encounter")
public class EncounterExt extends Encounter {

    @Description(shortDefinition = "Contains providerServiceMapId ")
    @Extension(url = "http://http://hl7.org/fhir/StructureDefinition/Encounter#Encounter.providerServiceMapId", isModifier = false, definedLocally = true)
    @Child(name = "providerServiceMapId")
    private StringType providerServiceMapId;

    @Description(shortDefinition = "Contains vanID ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Encounter#Encounter.vanID", isModifier = false, definedLocally = true)
    @Child(name = "vanID")
    private StringType vanID;

    @Description(shortDefinition = "Contains parkingPlaceID ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Encounter#Encounter.parkingPlaceID", isModifier = false, definedLocally = true)
    @Child(name = "parkingPlaceID")
    private StringType parkingPlaceID;

    @Description(shortDefinition = "Contains createdBy ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Encounter#Encounter.createdBy", isModifier = false, definedLocally = true)
    @Child(name = "createdBy")
    private StringType createdBy;

    @Description(shortDefinition = "Contains State details ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Encounter#Encounter.sessionID", isModifier = false, definedLocally = true)
    @Child(name = "sessionID")
    private StringType sessionID;

    @Description(shortDefinition = "Contains District details ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Encounter#Encounter.beneficiaryID", isModifier = false, definedLocally = true)
    @Child(name = "beneficiaryID")
    private StringType beneficiaryID;

    @Description(shortDefinition = "Contains block details ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Encounter#Encounter.benFlowID", isModifier = false, definedLocally = true)
    @Child(name = "benFlowID")
    private StringType benFlowID;

    @Description(shortDefinition = "Contains districtBranch details ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Encounter#Encounter.beneficiaryRegID", isModifier = false, definedLocally = true)
    @Child(name = "beneficiaryRegID")
    private StringType beneficiaryRegID;

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

    public StringType getSessionID() {
        return sessionID;
    }

    public void setSessionID(StringType sessionID) {
        this.sessionID = sessionID;
    }

    public StringType getBeneficiaryID() {
        return beneficiaryID;
    }

    public void setBeneficiaryID(StringType beneficiaryID) {
        this.beneficiaryID = beneficiaryID;
    }

    public StringType getBenFlowID() {
        return benFlowID;
    }

    public void setBenFlowID(StringType benFlowID) {
        this.benFlowID = benFlowID;
    }

    public StringType getBeneficiaryRegID() {
        return beneficiaryRegID;
    }

    public void setBeneficiaryRegID(StringType beneficiaryRegID) {
        this.beneficiaryRegID = beneficiaryRegID;
    }


}
