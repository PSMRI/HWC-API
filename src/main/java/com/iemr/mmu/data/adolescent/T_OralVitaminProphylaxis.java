/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.adolescent;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
@Entity
@Table(name = "t_oralvitaminprophylaxis")
public class T_OralVitaminProphylaxis {

	@Id
	@GeneratedValue
	@Expose
	@Column(name = "Id", insertable = false)
	private Long id;

	@Expose
	@Column(name = "Beneficiaryregid")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "Visitcode")
	private Long visitCode;

	@Expose
	@Column(name = "Dateofvisit")
	private Timestamp dateOfVisit;
	@Expose
	@Column(name = "Vaccinename")
	private String vaccineName;
	@Expose
	@Column(name = "Oralvitaminstatus")
	private String oralVitaminAStatus;
	@Expose
	@Column(name = "Oralvitaminnoofdoseid")
	private Integer noOfOralVitaminADoseID;
	@Expose
	@Column(name = "Oralvitaminnoofdose")
	private String noOfOralVitaminADose;
	@Expose
	@Column(name = "Dose")
	private String dose;
	@Expose
	@Column(name = "Batchno")
	private String batchNo;
	@Expose
	@Column(name = "Route")
	private String route;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

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
	@Column(name = "vanID")
	private Integer vanID;

	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;

}
