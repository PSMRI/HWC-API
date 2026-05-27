package com.iemr.hwc.data.eligibleCouple;

import lombok.Data;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "ELIGIBLE_COUPLE_TRACKING", schema = "db_iemr")
@Data
public class EligibleCoupleTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ben_id")
    private Long benId;

    @Column(name = "visit_date")
    private Timestamp visitDate;

    @Column(name = "is_pregnancy_test_done")
    private String isPregnancyTestDone;

    @Column(name = "pregnancy_test_result")
    private String pregnancyTestResult;

    @Column(name = "is_pregnant")
    private String isPregnant;

    @Column(name = "using_family_planning")
    private Boolean usingFamilyPlanning;

    @Column(name = "method_of_contraception")
    private String methodOfContraception;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;


    @Column(name = "lmp_date")
    private String lmpDate;

    @Column(name = "date_of_antra_injection")
    private Timestamp dateOfAntraInjection;

    @Column(name = "due_date_of_antra_injection")
    private String dueDateOfAntraInjection;

    @Column(name = "mpa_file")
    private String mpaFile;

    @Column(name = "antra_dose")
    private String antraDose;

    @Column(name = "discharge_summary1")
    private String dischargeSummary1;

    @Column(name = "discharge_summary2")
    private String dischargeSummary2;
}
