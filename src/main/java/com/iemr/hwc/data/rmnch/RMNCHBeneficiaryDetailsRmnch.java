/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.hwc.data.rmnch;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 * 
 * @author DE40034072
 *
 */
@Entity
@Table(name = "i_beneficiarydetails_rmnch",schema = "db_identity")
@Data
public class RMNCHBeneficiaryDetailsRmnch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "beneficiaryDetails_RmnchId", insertable = false, updatable = false)
	private BigInteger beneficiaryDetails_RmnchId;

	@Expose
	@Column(name = "BeneficiaryRegID")
	private BigInteger BenRegId;

	@Expose
	@Column(name = "aadhaNo")
	private String aadhaNo;

	@Expose
	@Column(name = "aadha_no")
	private String aadha_no;

	@Expose
	@Column(name = "aadha_noId")
	private Integer aadha_noId;

	@Expose
	@Column(name = "age")
	private Integer age;

	@Expose
	@Column(name = "ageAtMarriage")
	private Integer ageAtMarriage;

	@Expose
	@Column(name = "age_unit")
	private String age_unit;

	@Expose
	@Column(name = "age_unitId")
	private Integer age_unitId;

	@Expose
	@Column(name = "childRegisteredAWCID")
	private Long childRegisteredAWCID;

	@Expose
	@Column(name = "childRegisteredSchoolID")
	private Integer childRegisteredSchoolID;

	@Expose
	@Column(name = "dateofdelivey")
	private Timestamp dateofdelivey;

	@Expose
	@Column(name = "expectedDateofDelivery")
	private Timestamp expectedDateOfDelivery;

	@Expose
	@Column(name = "facilitySectionID")
	private Integer facilitySectionID;

	@Expose
	@Column(name = "guidelineId")
	private String guidelineId;

	@Expose
	@Column(name = "houseoldId")
	private Long houseoldId;

	@Expose
	@Column(name = "lastDeliveryConductedID")
	private Integer lastDeliveryConductedID;

	@Expose
	@Column(name = "lastMenstrualPeriod")
	private String lastMenstrualPeriod;

	@Expose
	@Column(name = "latitude")
	private BigDecimal latitude;

	@Expose
	@Column(name = "lengthofMenstrualCycleId")
	private Integer lengthofMenstrualCycleId;

	@Expose
	@Column(name = "literacyId")
	private Integer literacyId;

	@Expose
	@Column(name = "longitude")
	private BigDecimal longitude;

	@Expose
	@Column(name = "menstrualBFDId")
	private Integer menstrualBFDId;

	@Expose
	@Column(name = "menstrualProblemId")
	private Integer menstrualProblemId;

	@Expose
	@Column(name = "menstrualStatusId")
	private Integer menstrualStatusId;

	@Expose
	@Column(name = "mobileOthers")
	private String mobileOthers;

	@Expose
	@Column(name = "mobilenoofRelation")
	private String mobilenoofRelation;

	@Expose
	@Column(name = "mobilenoofRelationId")
	private Integer mobilenoofRelationId;

	@Expose
	@Column(name = "motherName")
	private String motherName;

	@Expose
	@Column(name = "ncd_priority")
	private Integer ncd_priority;

	@Expose
	@Column(name = "need_opcareId")
	private Integer need_opcareId;

	@Expose
	@Column(name = "previousLiveBirth")
	private String previousLiveBirth;

	@Expose
	@Column(name = "rchid")
	private String rchid;

	@Expose
	@Column(name = "registrationDate")
	private Timestamp registrationDate;

	@Expose
	@Column(name = "registrationType")
	private String registrationType;

	@Expose
	@Column(name = "regularityofMenstrualCycleId")
	private Integer regularityofMenstrualCycleId;

	@Expose
	@Column(name = "religionOthers")
	private String religionOthers;

	@Expose
	@Column(name = "reproductiveStatus")
	private String reproductiveStatus;

	@Expose
	@Column(name = "reproductiveStatusId")
	private Integer reproductiveStatusId;

	@Expose
	@Column(name = "serverUpdatedStatus")
	private Integer serverUpdatedStatus;

	@Expose
	@Column(name = "typeofSchoolID")
	private Integer typeofSchoolID;

	@Expose
	@Column(name = "whoConductedDeliveryID")
	private Integer whoConductedDeliveryID;

	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	@Expose
	@Column(name = "Processed")
	private String Processed = "N";

	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;

	@Expose
	@Column(name = "CreatedDate")
	private Timestamp createdDate;

	@Expose
	@Column(name = "Reserved")
	private Boolean reserved;

	@Expose
	@Column(name = "ReservedFor")
	private String reservedFor;

	@Expose
	@Column(name = "ReservedOn")
	private String reservedOn;

	@Expose
	@Column(name = "ReservedById")
	private Integer reservedById;

	@Expose
	@Column(name = "ModifiedBy")
	private String updatedBy;

	@Expose
	@Column(name = "LastModDate")
	private Timestamp updatedDate;

	@Expose
	@Column(name = "VanSerialNo")
	private Long id;

	@Expose
	@Column(name = "VanID")
	private Integer VanID;

	@Expose
	@Column(name = "VehicalNo")
	private String vehicalNo;

	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "SyncedBy")
	private String syncedBy;

	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer ProviderServiceMapID;

	@Expose
	@Column(name = "deviceId")
	private Integer deviceId;

	@Expose
	@Column(name = "beneficiaryId")
	private BigInteger benficieryid;

	// new fields 30-06-2021
	@Expose
	@Column(name = "RelatedBeneficiaryIds")
	private String relatedBeneficiaryIdsDB;
	@Expose
	@Column(name = "HRPStatus")
	private Boolean hrpStatus;
	@Expose
	@Column(name = "ImmunizationStatus")
	private Boolean immunizationStatus;
	@Expose
	@Column(name = "NishchayPregnancyStatus")
	private String nishchayPregnancyStatus;
	@Expose
	@Column(name = "NishchayPregnancyStatusPosition")
	private Integer nishchayPregnancyStatusPosition;
	@Expose
	@Column(name = "NishchayDeliveryStatus")
	private String nishchayDeliveryStatus;
	@Expose
	@Column(name = "NishchayDeliveryStatusPosition")
	private Integer nishchayDeliveryStatusPosition;

	@Expose
	@Column(name = "FamilyHeadRelation")
	private String familyHeadRelation;
	@Expose
	@Column(name = "FamilyHeadRelationPosition")
	private Integer familyHeadRelationPosition;

	@Expose
	@Column(name = "MenstrualStatus")
	private String menstrualStatus;
	@Expose
	@Column(name = "ComplicationsOthers")
	private String complicationsOthers;

	@Expose
	@Transient
	private Long[] relatedBeneficiaryIds;

	@Expose
	@Transient
	private String literacyStatus;

	@Expose
	@Transient
	private String branchName;

	// location
	@Expose
	@Transient
	private Integer countryId;
	@Expose
	@Transient
	private String countryName;
	@Expose
	@Transient
	private Integer stateId;
	@Expose
	@Transient
	private String stateName;
	@Expose
	@Transient
	private Integer blockId;
	@Expose
	@Transient
	private String blockName;
	@Expose
	@Transient
	private Integer villageId;
	@Expose
	@Transient
	private String villageName;

	@Expose
	@Transient
	private Integer servicePointID;
	@Expose
	@Transient
	private String servicePointName;

	@Expose
	@Transient
	private Integer zoneID;
	@Expose
	@Transient
	private String zoneName;

	@Expose
	@Transient
	private String addressLine1;
	@Expose
	@Transient
	private String addressLine2;
	@Expose
	@Transient
	private String addressLine3;

	// ----------------------------------------------

	// Extra data from platform
	@Expose
	@Transient
	private String bankAccount;
	@Expose
	@Transient
	private String community;
	@Expose
	@Transient
	private Integer communityId;
	@Expose
	@Transient
	private String contact_number;
	@Expose
	@Transient
	private Integer currSubDistrictId;
	@Expose
	@Transient
	private Integer districtid;
	@Expose
	@Transient
	private String districtname;
	@Expose
	@Transient
	private Timestamp dob;
	@Expose
	@Transient
	private String fatherName;
	@Expose
	@Transient
	private String firstName;
	@Expose
	@Transient
	private String gender;
	@Expose
	@Transient
	private Integer genderId;
	@Expose
	@Transient
	private String ifscCode;
	@Expose
	@Transient
	private String lastName;
	@Expose
	@Transient
	private String maritalstatus;
	@Expose
	@Transient
	private Integer maritalstatusId;
	@Expose
	@Transient
	private Timestamp marriageDate;
	@Expose
	@Transient
	private String nameOfBank;
	@Expose
	@Transient
	private String religion;
	@Expose
	@Transient
	private BigInteger religionID;
	@Expose
	@Transient
	private String spousename;
	@Expose
	@Transient
	private String user_image;

	// 08102020
	@Expose
	@Transient
	private String ageFull;

	// 07-07-2021
	@Expose
	@Column(name = "WhoConductedDelivery")
	private String whoConductedDelivery;
	@Expose
	@Column(name = "LastDeliveryConducted")
	private String lastDeliveryConducted;
	@Expose
	@Column(name = "FacilitySelection")
	private String facilitySelection;
	@Expose
	@Column(name = "DeliveryDate")
	private String deliveryDate;
	@Expose
	@Column(name = "ChildRegisteredSchool")
	private String childRegisteredSchool;
	@Expose
	@Column(name = "TypeOfSchool")
	private String typeOfSchool;
// 13-04-2022
	@Expose
	@Column(name = "dateMarriage")
	private Timestamp dateMarriage;
	@Expose
	@Column(name = "nayiPahalDeliveryStatus")
	private String nayiPahalDeliveryStatus;
	@Expose
	@Column(name = "nayiPahalDeliveryStatusPosition")
	private Integer nayiPahalDeliveryStatusPosition;
	@Expose
	@Column(name = "noOfDaysForDelivery")
	private Integer noOfDaysForDelivery;


	@Expose
	private Boolean isDeath;

	@Expose
	private String isDeathValue;

	@Expose
	private String dateOfDeath;

	@Expose
	private String timeOfDeath;

	@Expose
	private String reasonOfDeath;

	@Expose
	private Integer reasonOfDeathId;

	@Expose
	private String placeOfDeath;

	@Expose
	private Integer placeOfDeathId;

	@Expose
	private String  otherPlaceOfDeath;

	@Expose
	private Boolean isSpouseAdded;


	@Expose
	private Boolean isChildrenAdded;

	@Expose
	private Boolean isMarried;

	@Expose
	private Boolean doYouHavechildren;


	@Expose
	private Integer noofAlivechildren;

	@Expose
	private Integer noOfchildren;

	@Expose
	private Boolean isDeactivate;

}
