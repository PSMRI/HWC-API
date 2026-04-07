package com.iemr.hwc.data.infantRegistration;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class InfantRegisterDTO {

    private Long id;
    private Long benId;
    private Long childBenId;
    private String babyName;
    private Integer babyIndex;
    private String infantTerm;
    private String corticosteroidGiven;
    private String gender;
    private Boolean babyCriedAtBirth;
    private Boolean resuscitation;
    private String referred;
    private String hadBirthDefect;
    private String birthDefect;
    private String otherDefect;
    private Float weight;
    private Boolean breastFeedingStarted;
    private Timestamp opv0Dose;
    private Timestamp bcgDose;
    private Timestamp hepBDose;
    private Timestamp vitkDose;
    private Boolean isActive;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp updatedDate;
    private String updatedBy;
    private String  deliveryDischargeSummary1;
    private String deliveryDischargeSummary2;
    private String deliveryDischargeSummary3;
    private String deliveryDischargeSummary4;
    private String isSNCU;
}
