package com.iemr.hwc.data.painSymptom;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pain_symptom_assessment",schema = "db_iemr")
public class PainSymptomData {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Expose
    @Column(name = "id")
    private Long id;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "ben_visit_no")
    private Integer benVisitNo;

    @Column(name = "pain_severity")
    private String painSeverity;

    @Column(name = "pain_duration")
    private String painDuration;

    @Column(name = "symptoms_present")
    private Boolean symptomsPresent;

    @Column(name = "other_symptoms_severity")
    private String otherSymptomsSeverity;

    @Column(name = "immediate_relief_provided")
    private Boolean immediateReliefProvided;

    @Column(name = "persistent_pain_present")
    private Boolean persistentPainPresent;

    @Column(name = "pain_assessment_enabled")
    private Boolean painAssessmentEnabled;

    @Column(name = "distressing_symptoms_present")
    private String distressingSymptomsPresent;

    @Column(name = "bedridden_or_severely_dependent")
    private Boolean bedriddenOrSeverelyDependent;

    @Column(name = "life_limiting_illness_known")
    private Boolean lifeLimitingIllnessKnown;

    @Column(name = "caregiver_support_required")
    private Boolean caregiverSupportRequired;

    @Column(name = "palliative_care_eligible")
    private Boolean palliativeCareEligible;

    @Column(name = "referral_required")
    private Boolean referralRequired;

    @Column(name = "referral_level")
    private String referralLevel;

    @Column(name = "reason_for_referral", columnDefinition = "TEXT")
    private String reasonForReferral;

    @Column(name = "follow_up_required")
    private Boolean followUpRequired;

    @Column(name = "follow_up_date")
    private String followUpDate;

    @Column(name = "case_status")
    private String caseStatus;

    @Column(name = "date_of_death")
    private String dateOfDeath;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

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
