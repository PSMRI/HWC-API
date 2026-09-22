package com.iemr.hwc.data.childRegistration;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChildRegisterDTO {

    private Long id;
    private Long benId;
    private String babyName;
    private String infantTerm;
    private String corticosteroidGiven;
    private String gender;
    private Boolean babyCriedAtBirth;
    private Boolean resuscitation;
    private String referred;
    private String hadBirthDefect;
    private String birthDefect;
    private String otherDefect;
    private Double weight;
    private Boolean breastFeedingStarted;
    private Timestamp opv0Dose;
    private Timestamp bcgDose;
    private Timestamp hepBDose;
    private Timestamp vitkDose;
    private Timestamp createdDate;
    private String createdBy;

    private Timestamp updatedDate;

    private String updatedBy;
}
