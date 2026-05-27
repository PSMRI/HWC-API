package com.iemr.hwc.data.infantRegistration;

import lombok.Data;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_infant_register", schema = "db_iemr")
@Data
public class InfantRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ben_id")
    private Long benId;

    @Column(name = "child_ben_id")
    private Long childBenId;

    @Column(name = "baby_name")
    private String babyName;

    @Column(name = "baby_index")
    private Integer babyIndex;

    @Column(name = "infant_term")
    private String infantTerm;

    @Column(name = "corticosteroid_given")
    private String corticosteroidGiven;

    @Column(name = "gender")
    private String gender;

    @Column(name = "cried_at_birth")
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
    private Float weight;

    @Column(name = "breast_feeding_started")
    private Boolean breastFeedingStarted;

    @Column(name = "opv0_dose")
    private Timestamp opv0Dose;

    @Column(name = "bcg_dose")
    private Timestamp bcgDose;

    @Column(name = "hepB_dose")
    private Timestamp hepBDose;

    @Column(name = "vitk_dose")
    private Timestamp vitkDose;

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

    @Column(name = "delivery_discharge_summary1")
    private String  deliveryDischargeSummary1;

    @Column(name = "delivery_discharge_summary2")
    private String deliveryDischargeSummary2;

    @Column(name = "delivery_discharge_summary3")
    private String deliveryDischargeSummary3;

    @Column(name = "delivery_discharge_summary4")
    private String deliveryDischargeSummary4;

    @Column(name = "is_sncu")
    private String isSNCU;
}
