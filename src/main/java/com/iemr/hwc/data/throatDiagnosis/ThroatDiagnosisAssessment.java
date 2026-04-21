package com.iemr.hwc.data.throatDiagnosis;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "throat_diagnosis_assessment")
@Data
public class ThroatDiagnosisAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long assessmentId;

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @Column(name = "ben_visit_no")
    private Integer benVisitNo;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "beneficiary_id")
    private Long beneficiaryID;

    @Column(name = "beneficiary_reg_id")
    private Long beneficiaryRegID;

    @Column(name = "symptoms", columnDefinition = "TEXT")
    private String symptoms; // JSON string

    @Column(name = "neck_swelling")
    private Boolean neckSwelling;

    @Column(name = "difficulty_swallowing")
    private Boolean difficultySwallowing;

    @Column(name = "tonsillitis")
    private Boolean tonsillitis;

    @Column(name = "pharyngitis")
    private Boolean pharyngitis;

    @Column(name = "laryngitis")
    private Boolean laryngitis;

    @Column(name = "sinusitis")
    private Boolean sinusitis;

    @Column(name = "cleft_lip")
    private Boolean cleftLip;

    @Column(name = "cleft_palate")
    private Boolean cleftPalate;
}
