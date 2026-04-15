package com.iemr.hwc.data.earDiagnosis;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "ear_diagnosis_assessment")
public class EarDiagnosisAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_id")
    private Integer assessmentId;

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @Column(name = "ben_visit_no")
    private Integer benVisitNo;

    @Column(name = "difficulty_hearing")
    private Boolean difficultyHearing;

    @Column(name = "whisper_test_response")
    private String whisperTestResponse;

    @Column(name = "hearing_test_outcome")
    private String hearingTestOutcome;

    @Column(name = "ear_pain")
    private Boolean earPain;

    @Column(name = "ear_discharge_present")
    private Boolean earDischargePresent;

    @Column(name = "foreign_body_in_ear")
    private String foreignBodyInEar;

    @Column(name = "ear_condition_type")
    private String earConditionType;

    @Column(name = "congenital_ear_malformation")
    private Boolean congenitalEarMalformation;
}