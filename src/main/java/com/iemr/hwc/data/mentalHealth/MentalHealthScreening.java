package com.iemr.hwc.data.mentalHealth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "mental_health_screening")
@Data
public class MentalHealthScreening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long screeningId;

    @Column(name = "patient_id", nullable = false)
    private String patientID;

    @Column(name = "ben_visit_no")
    private Integer benVisitNo;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "beneficiary_id")
    private Long beneficiaryID;

    @Column(name = "beneficiary_reg_id")
    private Long beneficiaryRegID;

    @Column(name = "emotional_behavioural_concerns")
    private Boolean emotionalBehaviouralConcerns;

    @Column(name = "substance_use_concerns")
    private Boolean substanceUseConcerns;

    @Column(name = "self_harm_suicide_thoughts")
    private Boolean selfHarmSuicideThoughts;

    @Column(name = "memory_loss_confusion")
    private Boolean memoryLossConfusion;

    @Column(name = "seizures_fits_loc")
    private Boolean seizuresFitsLoc;

    @Column(name = "is_postpartum")
    private Boolean isPostpartum;

    @Column(name = "phq9_little_interest")
    private Integer phq9LittleInterest;

    @Column(name = "phq9_feeling_down")
    private Integer phq9FeelingDown;

    @Column(name = "phq9_sleep_trouble")
    private Integer phq9SleepTrouble;

    @Column(name = "phq9_feeling_tired")
    private Integer phq9FeelingTired;

    @Column(name = "phq9_appetite")
    private Integer phq9Appetite;

    @Column(name = "phq9_feeling_bad")
    private Integer phq9FeelingBad;

    @Column(name = "phq9_concentration")
    private Integer phq9Concentration;

    @Column(name = "phq9_moving_slowly")
    private Integer phq9MovingSlowly;

    @Column(name = "phq9_self_harm_thoughts")
    private Integer phq9SelfHarmThoughts;

    @Column(name = "phq9_total_score")
    private Integer phq9TotalScore;

    @Column(name = "phq9_depression_severity")
    private String phq9DepressionSeverity;

    @Column(name = "phq9_system_action")
    private String phq9SystemAction;

    @Column(name = "substance_current_tobacco_use")
    private Boolean substanceCurrentTobaccoUse;

    @Column(name = "substance_tobacco_type")
    private String substanceTobaccoType;

    @Column(name = "substance_tobacco_frequency")
    private String substanceTobaccoFrequency;

    @Column(name = "substance_tobacco_outcome")
    private String substanceTobaccoOutcome;

    @Column(name = "substance_system_action")
    private String substanceSystemAction;

    @Column(name = "substance_alcohol_use")
    private Boolean substanceAlcoholUse;

    @Column(name = "substance_tobacco_use")
    private Boolean substanceTobaccoUse;

    @Column(name = "substance_other_use")
    private Boolean substanceOtherUse;

    @Column(name = "substance_other_specify")
    private String substanceOtherSpecify;

    @Column(name = "substance_frequency")
    private String substanceFrequency;

    @Column(name = "brief_intervention_given")
    private Boolean briefInterventionGiven;

    @Column(name = "substance_alcohol_impact")
    private Boolean substanceAlcoholImpact;

    @Column(name = "substance_alcohol_withdrawal")
    private Boolean substanceAlcoholWithdrawal;

    @Column(name = "substance_alcohol_problematic")
    private Boolean substanceAlcoholProblematic;

    @Column(name = "substance_alcohol_classification")
    private String substanceAlcoholClassification;

    @Column(name = "substance_alcohol_system_action")
    private String substanceAlcoholSystemAction;

    @Column(name = "substance_alcohol_frequency")
    private String substanceAlcoholFrequency;

    @Column(name = "substance_alcohol_loss")
    private Boolean substanceAlcoholLoss;

    @Column(name = "suicide_current_thoughts")
    private Boolean suicideCurrentThoughts;

    @Column(name = "suicide_plan")
    private Boolean suicidePlan;

    @Column(name = "suicide_previous_attempt")
    private Boolean suicidePreviousAttempt;

    @Column(name = "suicide_hopelessness")
    private Boolean suicideHopelessness;

    @Column(name = "suicide_immediate_assess")
    private Boolean suicideImmediateAssess;

    @Column(name = "suicide_risk_level")
    private String suicideRiskLevel;

    @Column(name = "dementia_progressive_memory_loss")
    private Boolean dementiaProgressiveMemoryLoss;

    @Column(name = "dementia_forgetting_recent")
    private Boolean dementiaForgettingRecent;

    @Column(name = "dementia_disorientation")
    private Boolean dementiaDisorientation;

    @Column(name = "dementia_daily_activities")
    private Boolean dementiaDailyActivities;

    @Column(name = "dementia_behavioural_changes")
    private Boolean dementiaBehaviouralChanges;

    @Column(name = "epilepsy_recurrent_seizures")
    private Boolean epilepsyRecurrentSeizures;

    @Column(name = "epilepsy_jerky_movements")
    private Boolean epilepsyJerkyMovements;

    @Column(name = "epilepsy_tongue_bite")
    private Boolean epilepsyTongueBite;

    @Column(name = "epilepsy_confusion_after")
    private Boolean epilepsyConfusionAfter;

    @Column(name = "epilepsy_loc_duration")
    private String epilepsyLocDuration;

    @Column(name = "edRecurrentEpisodeloss")
    private Boolean edRecurrentEpisodeloss;

    @Column(name = "ed_recurrent_jerky_movements")
    private Boolean edRecurrentJerkyMovements;

    @Column(name = "ed_progressive_memory_loss")
    private Boolean edProgressiveMemoryLoss;

    @Column(name = "ed_confusion_disorientation")
    private Boolean edConfusionDisorientation;

    @Column(name = "ed_functional_decline")
    private Boolean edFunctionalDecline;

    @Column(name = "ed_screening_outcome")
    private String edScreeningOutcome;

    @Column(name = "ed_psychosocial_intervention_provided")
    private Boolean edPsychosocialInterventionProvided;

    @Column(name = "ed_intervention_type")
    private String edInterventionType;

    @Column(name = "ed_session_date")
    private String edSessionDate;

    @Column(name = "ed_duration_minutes")
    private Integer edDurationMinutes;

    @Column(name = "ed_remarks", columnDefinition = "TEXT")
    private String edRemarks;

    @Column(name = "ed_referral_required")
    private String edReferralRequired;

    @Column(name = "referral_required")
    private Boolean referralRequired;

    @Column(name = "referral_level")
    private String referralLevel;

    @Column(name = "reason_for_referral", columnDefinition = "TEXT")
    private String reasonForReferral;

    @Column(name = "referral_date")
    private String referralDate;

    @Column(name = "follow_up_required")
    private Boolean followUpRequired;

    @Column(name = "follow_up_date")
    private String followUpDate;

    @Column(name = "improvement_noted")
    private String improvementNoted;

    @Column(name = "referral_escalation_required")
    private Boolean referralEscalationRequired;

    @Column(name = "case_closure_reason")
    private String caseClosureReason;
}
