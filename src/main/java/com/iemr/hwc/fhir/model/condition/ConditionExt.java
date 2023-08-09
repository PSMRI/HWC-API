package com.iemr.hwc.fhir.model.condition;

import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import ca.uhn.fhir.util.ElementUtil;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Condition;
import org.hl7.fhir.r4.model.StringType;

@ResourceDef(name = "Condition")
public class ConditionExt extends Condition {
    @Description(shortDefinition = "Contains providerServiceMapId ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Condition#Condition.providerServiceMapId", isModifier = false, definedLocally = true)
    @Child(name = "providerServiceMapId")
    private StringType providerServiceMapId;

    @Description(shortDefinition = "Contains vanID ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Condition#Condition.vanID", isModifier = false, definedLocally = true)
    @Child(name = "vanID")
    private StringType vanID;

    @Description(shortDefinition = "Contains parkingPlaceID ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Condition#Condition.parkingPlaceID", isModifier = false, definedLocally = true)
    @Child(name = "parkingPlaceID")
    private StringType parkingPlaceID;

    @Description(shortDefinition = "Contains createdBy ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Condition#Condition.createdBy", isModifier = false, definedLocally = true)
    @Child(name = "createdBy")
    private StringType createdBy;

    @Description(shortDefinition = "Contains beneficiaryID ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Condition#Condition.beneficiaryID", isModifier = false, definedLocally = true)
    @Child(name = "beneficiaryID")
    private StringType beneficiaryID;

    @Description(shortDefinition = "Contains benFlowID ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Condition#Condition.benFlowID", isModifier = false, definedLocally = true)
    @Child(name = "benFlowID")
    private StringType benFlowID;

    @Description(shortDefinition = "Contains beneficiaryRegID ")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Condition#Condition.beneficiaryRegID", isModifier = false, definedLocally = true)
    @Child(name = "beneficiaryRegID")
    private StringType beneficiaryRegID;

    @Description(shortDefinition = "Mentions duration and duration unit for the chief complaint")
    @Extension(url = "http://hl7.org/fhir/StructureDefinition/Condition#Condition.duration", isModifier = false, definedLocally = true)
    @Child(name = "duration")
    private Coding duration;

    public StringType getProviderServiceMapId() {
        if (providerServiceMapId == null) {
            providerServiceMapId = new StringType();
        }
        return providerServiceMapId;
    }

    public void setProviderServiceMapId(StringType providerService_MapId) {
        providerServiceMapId =  providerService_MapId;
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

    public StringType getParkingPlaceID() {
        if (parkingPlaceID == null) {
            parkingPlaceID = new StringType();
        }
        return parkingPlaceID;
    }

    public void setParkingPlaceID(StringType parking_PlaceID) {
        parkingPlaceID =  parking_PlaceID;
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

    public StringType getBeneficiaryID() {
        if (beneficiaryID == null) {
            beneficiaryID = new StringType();
        }
        return beneficiaryID;
    }

    public void setBeneficiaryID(StringType beneficiary_ID) {
        beneficiaryID =  beneficiary_ID;
    }

    public StringType getBenFlowID() {
        if (benFlowID == null) {
            benFlowID = new StringType();
        }
        return benFlowID;
    }

    public void setBenFlowID(StringType benFlow_ID) {
        benFlowID =  benFlow_ID;
    }

    public StringType getBeneficiaryRegID() {
        if (beneficiaryRegID == null) {
            beneficiaryRegID = new StringType();
        }
        return beneficiaryRegID;
    }

    public void setBeneficiaryRegID(StringType beneficiary_RegID) {
        beneficiaryRegID =  beneficiary_RegID;
    }

    public Coding getDuration() {
        if (duration == null) {
            duration = new Coding();
        }
        return duration;
    }

    public void setDuration(Coding durationOfComplaint) {
        duration = durationOfComplaint;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && ElementUtil.isEmpty(beneficiaryID , beneficiaryRegID , benFlowID ,
                providerServiceMapId , vanID , parkingPlaceID , createdBy , duration );
    }

}
