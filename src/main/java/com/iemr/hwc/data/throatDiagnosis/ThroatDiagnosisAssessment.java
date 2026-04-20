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
    private Long id;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "ben_visit_no")
    private Integer benVisitNo;

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

    @Column(name = "created_date")
    private Long createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    private Long updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;
}