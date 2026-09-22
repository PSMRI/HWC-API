package com.iemr.hwc.data.eligibleCouple;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "t_eligible_couple_register", schema = "db_iemr")
@Data
@JsonInclude(JsonInclude.Include.ALWAYS) // Null values included
public class EligibleCoupleRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ben_id")
    private Long benId;

    @Column(name = "registration_date")
    private Timestamp registrationDate;

    @Column(name = "rch_id")
    private Long rchId;

    @Column(name = "age_at_marriage")
    private Integer ageAtMarriage;

    @Column(name = "aadhaar_number")
    private Long aadhaarNumber;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "ifsc")
    private String ifsc;

    @Column(name = "num_of_children")
    private Integer numChildren;

    @Column(name = "num_live_children")
    private Integer numLiveChildren;

    @Column(name = "dob1")
    private Timestamp dob1;

    @Column(name = "gender1")
    private String gender1;

    @Column(name = "marriage_and_first_child_gap")
    private Integer marriageFirstChildGap;

    @Column(name = "dob2")
    private Timestamp dob2;

    @Column(name = "gender2")
    private String gender2;

    @Column(name = "first_and_second_child_gap")
    private Integer firstAndSecondChildGap;

    @Column(name = "dob3")
    private Timestamp dob3;

    @Column(name = "gender3")
    private String gender3;

//    @Column(name = "second_and_third_child_gap")
//    private Integer secondAndThirdChildGap;

    @Column(name = "dob4")
    private Timestamp dob4;

    @Column(name = "gender4")
    private String gender4;

//    @Column(name = "third_and_fourth_child_gap")
//    private Integer thirdAndFourthChildGap;

    @Column(name = "dob5")
    private Timestamp dob5;

    @Column(name = "gender5")
    private String gender5;

//    @Column(name = "fourth_and_fifth_child_gap")
//    private Integer fourthAndFifthChildGap;

    @Column(name = "dob6")
    private Timestamp dob6;

    @Column(name = "gender6")
    private String gender6;

//    @Column(name = "fifth_and_sixth_child_gap")
//    private Integer fifthAndSixthChildGap;

    @Column(name = "dob7")
    private Timestamp dob7;

    @Column(name = "gender7")
    private String gender7;

//    @Column(name = "sixth_and_seventh_child_gap")
//    private Integer sixthAndSeventhChildGap;

    @Column(name = "dob8")
    private Timestamp dob8;

    @Column(name = "gender8")
    private String gender8;

//    @Column(name = "seventh_and_eighth_child_gap")
//    private Integer seventhAndEighthChildGap;

    @Column(name = "dob9")
    private Timestamp dob9;

    @Column(name = "gender9")
    private String gender9;

    @Column(name = "mis_carriage_check")
    private String misCarriage;

    @Column(name = "home_delivery_check")
    private String homeDelivery;

    @Column(name = "medical_issues_check")
    private String medicalIssues;

    @Column(name = "past_csection_check")
    private String pastCSection;

    @Column(name = "is_registered")
    private Boolean isRegistered;

    @Column(name = "is_high_risk")
    private Boolean isHighRisk;

//    @Column(name = "eighth_and_ninth_child_gap")
//    private Integer eighthAndNinthChildGap;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;


    @Column(name = "lmp_date")
    private String lmpDate;

    @Column(name = "is_kit_handed_over")
    private Boolean isKitHandedOver;

    @Column(name = "is_kit_handed_over_date")
    private String kitHandedOverDate;

    @Lob
    @Column(name = "kit_photo1", columnDefinition = "LONGTEXT")
    private String kitPhoto1;

    @Column(name = "kit_photo2", columnDefinition = "LONGTEXT")
    private String kitPhoto2;
}

