package com.iemr.hwc.data.oralHealth;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "oral_health",schema = "db_iemr")
public class OralHealthData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "ben_visit_no")
    private Integer benVisitNo;

    @Column(name = "tooth_decay_present")
    private Boolean toothDecayPresent;

    @Column(name = "tooth_decay_symptoms", columnDefinition = "TEXT")
    private String toothDecaySymptoms;

    @Column(name = "gum_disease_present")
    private Boolean gumDiseasePresent;

    @Column(name = "gum_disease_symptoms", columnDefinition = "TEXT")
    private String gumDiseaseSymptoms;

    @Column(name = "irregular_teeth_jaws")
    private Boolean irregularTeethJaws;

    @Column(name = "abnormal_growth_ulcer")
    private Boolean abnormalGrowthUlcer;

    @Column(name = "cleft_lip_palate")
    private Boolean cleftLipPalate;

    @Column(name = "dental_fluorosis")
    private Boolean dentalFluorosis;

    @Column(name = "dental_emergency")
    private String dentalEmergency;

    @Column(name = "created_date")
    private Long createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    private Long updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "beneficiary_id")
    private Long beneficiaryID;

    @Column(name = "beneficiary_reg_id")
    private Long beneficiaryRegID;
}
