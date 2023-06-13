/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.neonatal;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Entity
@Table(name = "t_followupforimmunization")
@Data
public class FollowUpForImmunization {

	@Id
	@GeneratedValue
	@Expose
	@Column(name = "Id", insertable = false)
	private Long id;

	@Expose
	@Column(name = "Beneficiaryregid")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "Benvisitid")
	private Long benVisitID;

	@Expose
	@Column(name = "Visitcode")
	private Long visitCode;

	@Expose
	@Column(name = "Duedatefornextimmunization")
	private Timestamp dueDateForNextImmunization;

	@Expose
	@Column(name = "Nextduevaccines")
	private String nextDueVaccines;

	@Expose
	@Column(name = "Nextduevaccinesid")
	private Short nextDueVaccinesID;

	@Expose
	@Column(name = "LocationOfnextimmunization")
	private String locationOfNextImmunization;

	@Expose
	@Column(name = "LocationOfnextimmunizationid")
	private Short locationOfNextImmunizationID;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;

	@Expose
	@Column(name = "CreatedBy", updatable = false)
	private String createdBy;

	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy", insertable = false, updatable = true)
	private String modifiedBy;

	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;

	@Expose
	@Column(name = "VehicalNo")
	private String vehicalNo;

	@Expose
	@Column(name = "vanID")
	private Integer vanID;

	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "SyncedBy")
	private String syncedBy;

	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;

	@Transient
	private String[] nextDueVaccinesList;

	@Transient
	private String[] locationOfNextImmunizationList;

}
