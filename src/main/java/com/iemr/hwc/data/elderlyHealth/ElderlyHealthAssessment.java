package com.iemr.hwc.data.elderlyHealth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "elderly_health_assessment")
@Data
public class ElderlyHealthAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long assessmentId;

    @Column(name = "patient_id", nullable = false)
    private String patientID;

    @Column(name = "ben_visit_no", nullable = false)
    private Integer benVisitNo;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "beneficiary_id")
    private Long beneficiaryID;

    @Column(name = "beneficiary_reg_id")
    private Long beneficiaryRegID;

    @Column(name = "geriatric_complaints")
    private Boolean geriatricComplaints;

    @Column(name = "multiple_chronic_conditions")
    private Boolean multipleChronicConditions;

    @Column(name = "recent_falls")
    private Boolean recentFalls;

    @Column(name = "difficulty_walking_balance")
    private Boolean difficultyWalkingBalance;

    @Column(name = "visual_hearing_difficulty")
    private Boolean visualHearingDifficulty;

    @Column(name = "functional_decline")
    private Boolean functionalDecline;

    @Column(name = "memory_loss")
    private Boolean memoryLoss;

    @Column(name = "dementia_memory_loss")
    private Boolean dementiaMemoryLoss;

    @Column(name = "dementia_disorientation")
    private Boolean dementiaDisorientation;

    @Column(name = "dementia_behavioural_changes")
    private Boolean dementiaBehaviouralChanges;

    @Column(name = "dementia_self_care_decline")
    private Boolean dementiaSelfCareDecline;

    @Column(name = "dementia_screening_outcome")
    private String dementiaScreeningOutcome;

    @Column(name = "dementia_referral_required")
    private Boolean dementiaReferralRequired;

    @Column(name = "referral_required")
    private Boolean referralRequired;

    @Column(name = "referral_level")
    private String referralLevel;

    @Column(name = "reason_for_referral")
    private String reasonForReferral;

    @Column(name = "follow_up_required")
    private Boolean followUpRequired;

    @Column(name = "follow_up_date")
    private String followUpDate;

    @Column(name = "case_status")
    private String caseStatus;

    @Column(name = "date_of_death")
    private String dateOfDeath;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "bathing")
    private Integer bathing;

    @Column(name = "dressing")
    private Integer dressing;

    @Column(name = "toileting")
    private Integer toileting;

    @Column(name = "transferring")
    private Integer transferring;

    @Column(name = "continence")
    private Integer continence;

    @Column(name = "feeding")
    private Integer feeding;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "functional_status")
    private String functionalStatus;

    @Column(name = "functional_decline_flag")
    private Boolean functionalDeclineFlag;
}
