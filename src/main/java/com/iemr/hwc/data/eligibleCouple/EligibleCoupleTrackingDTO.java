package com.iemr.hwc.data.eligibleCouple;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class EligibleCoupleTrackingDTO implements Serializable {

    private Long id;

    private Long benId;

    private Timestamp visitDate;

    private String isPregnancyTestDone;

    private String pregnancyTestResult;

    private String isPregnant;

    private Boolean usingFamilyPlanning;

    private String methodOfContraception;

    private Boolean isActive;

    private Timestamp createdDate;

    private String createdBy;

    private Timestamp updatedDate;

    private String updatedBy;

    private String lmpDate;

    private Timestamp dateOfAntraInjection;

    private String  dueDateOfAntraInjection;

    private String mpaFile;

    private String antraDose;

    private String dischargeSummary1;
    private String dischargeSummary2;

}
