package com.iemr.hwc.data.childRegistration;

import lombok.Data;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_child_register", schema = "db_iemr")
@Data
public class ChildRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ben_id")
    private Long benId;

    @Column(name = "baby_name")
    private String babyName;

    @Column(name = "infant_term")
    private String infantTerm;

    @Column(name = "corticosteroid_given")
    private String corticosteroidGiven;

    @Column(name = "gender")
    private String gender;

    @Column(name = "baby_cried_at_birth")
    private Boolean babyCriedAtBirth;

    @Column(name = "resuscitation")
    private Boolean resuscitation;

    @Column(name = "referred")
    private String referred;

    @Column(name = "had_birth_defect")
    private String hadBirthDefect;

    @Column(name = "birth_defect")
    private String birthDefect;

    @Column(name = "other_defect")
    private String otherDefect;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "breast_feeding_started")
    private Boolean breastFeedingStarted;

    @Column(name = "opv0_dose")
    private Timestamp opv0Dose;

    @Column(name = "bcg_dose")
    private Timestamp bcgDose;

    @Column(name = "hepb_dose")
    private Timestamp hepBDose;

    @Column(name = "vitk_dose")
    private Timestamp vitkDose;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;
}
