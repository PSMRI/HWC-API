/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.nurse;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.data.registrar.BeneficiaryData;

@Entity
@Table(name = "t_benchiefcomplaint")
public class BeneficiaryChiefComplaint {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ID")
	private Long ID;
	
	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;
	@Expose
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false, insertable = false, name = "BeneficiaryRegID")
	private BeneficiaryData beneficiaryData;
	
	@Expose
	@Column(name = "BenVisitID")
	private Long benVisitID;
	@Expose
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false, insertable = false, name = "BenVisitID")
	private BeneficiaryVisitDetail t_benVisitDetail;
	
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	
	@Expose
	@Column(name = "VisitCode")
	private Long visitCode;

	@Expose
	@Column(name = "ChiefComplaintID")
	private Integer chiefComplaintID;
	
	@Expose
	@Column(name = "ChiefComplaint")
	private String chiefComplaint;
	
	@Expose
	@Column(name = "Duration")
	private Integer duration;
	
	@Expose
	@Column(name = "UnitOfDuration")
	private String unitOfDuration;
	
	@Expose
	@Column(name = "Description")
	private String description;
	
	@Expose
	@Column(name = "Deleted",insertable = false, updatable = true)
	private Boolean deleted; 
	@Expose
	@Column(name = "Processed",insertable = false, updatable = true)
	private String processed;
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;
	@Expose
	@Column(name = "ModifiedBy")
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
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;
	
	@Expose
	@Column(name = "SyncedBy")
	private String syncedBy;
	
	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;
	
	@Expose
	@Column(name = "ReservedForChange")
	private String reservedForChange;
	
	
}
