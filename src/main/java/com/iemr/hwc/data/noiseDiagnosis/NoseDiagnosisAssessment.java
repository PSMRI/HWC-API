package com.iemr.hwc.data.noiseDiagnosis;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "nose_diagnosis_assessment")
@Data
public class NoseDiagnosisAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "ben_visit_no")
    private Integer benVisitNo;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "beneficiary_id")
    private Long beneficiaryID;

    @Column(name = "beneficiary_reg_id")
    private Long beneficiaryRegID;

    @Column(name = "difficulty_breathing")
    private Boolean difficultyBreathing;

    @Column(name = "open_mouth_breathing")
    private Boolean openMouthBreathing;

    @Column(name = "nose_bleed")
    private Boolean noseBleed;

    @Column(name = "systolic_bp")
    private Integer systolicBp;

    @Column(name = "diastolic_bp")
    private Integer diastolicBp;

    @Column(name = "foreign_body_nose")
    private String foreignBodyNose;

    @Column(name = "sinusitis")
    private Boolean sinusitis;
}
