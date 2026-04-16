package com.iemr.hwc.data.deliveryOutcome;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "t_delivery_outcome", schema = "db_iemr")
public class DeliveryOutcome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ben_id")
    private Long benId;

    @Column(name = "delivery_date")
    private Timestamp dateOfDelivery;

    @Column(name = "delivery_time")
    private String timeOfDelivery;

    @Column(name = "delivery_place")
    private String placeOfDelivery;

    @Column(name = "delivery_type")
    private String typeOfDelivery;

    @Column(name = "had_complications")
    private Boolean hadComplications;

    @Column(name = "complication")
    private String complication;

    @Column(name = "death_cause")
    private String causeOfDeath;

    @Column(name = "other_death_cause")
    private String otherCauseOfDeath;

    @Column(name = "other_complication")
    private String otherComplication;

    @Column(name = "delivery_outcome")
    private Integer deliveryOutcome;

    @Column(name = "live_birth")
    private Integer liveBirth;

    @Column(name = "still_birth")
    private Integer stillBirth;

    @Column(name = "discharge_date")
    private Timestamp dateOfDischarge;

    @Column(name = "discharge_time")
    private String timeOfDischarge;

    @Column(name = "is_jsybeneficiary")
    private Boolean isJSYBenificiary;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @Column(name = "gestational_age_at_delivery")
    private Integer gestationalAgeAtDelivery;

    @Column(name = "delivery_conducted_by")
    private String deliveryConductedBy;

    @Column(name = "modeOf_delivery")
    private String modeOfDelivery;

    @Column(name = "mother_condition")
    private String motherCondition;

}
