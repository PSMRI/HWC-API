package com.iemr.hwc.data.psychosocialCaregiver;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "psychosocial_caregiver_support",schema = "db_iemr")
public class PsychosocialCaregiverSupportData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "ben_visit_no")
    private Integer benVisitNo;

    @Column(name = "psychosocial_counselling_provided")
    private Boolean psychosocialCounsellingProvided;

    @Column(name = "caregiver_counselling_provided")
    private Boolean caregiverCounsellingProvided;

    @Column(name = "caregiver_distress_identified")
    private Boolean caregiverDistressIdentified;

    @Column(name = "counselling_remarks", columnDefinition = "TEXT")
    private String counsellingRemarks;

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
}
