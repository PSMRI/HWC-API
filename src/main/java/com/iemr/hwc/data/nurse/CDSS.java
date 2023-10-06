package com.iemr.hwc.data.nurse;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.annotation.sqlInjectionSafe.SQLInjectionSafe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_hwc_cdss")
@Entity
public class CDSS {
	
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ID", insertable = false)
	private Long id;
	
	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "BenVisitID")
	private Long benVisitID;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;

	@Expose
	@Column(name = "VisitCode")
	private Long visitCode;

	@Expose
	@Column(name = "SelecteDiagnosisID")
	private String selectedDiagnosisID;
	
	@Expose
	@Column(name = "SelecteDiagnosis")
	private String selectedDiagnosis;
	
	@Expose
	@Column(name = "PresentChiefComplaint")
    private String presentChiefComplaint;
	
	@Expose
	@Column(name = "PresentChiefComplaintID")
	private String presentChiefComplaintID;
	
	@Expose
	@Column(name = "RecommendedAction")
	private String recommendedAction;
	
	@Expose
	@Column(name = "Remarks")
	private String remarks;
	
	@Expose
	@Column(name = "Algorithm_cdss")
	private String algorithm;
	
	@Expose
	@Column(name = "RecommendedActionPc")
	private String recommendedActionPc;
	
	@Expose
	@Column(name = "RemarksPc")
	private String remarksPc;
	
	@Expose
	@Column(name = "AlgorithmPc")
	private String algorithmPc;
	
	@Expose
	@Column(name = "DiseasesummaryID")
	private Integer diseaseSummaryID;
	
	@Expose
	@Column(name = "DiseaseSummary")
	private String diseaseSummary;
	
	@Expose
	@Column(name = "ActionPc")
	private String actionPc;
	
	@Expose
	@Column(name = "ActionIdPc")
	private Integer actionIdPc;
	
	@Expose
	@Column(name = "Action")
	private String action;
	
	@Expose
	@Column(name = "ActionId")
	private Integer actionId;
	
	@Expose
	@Column(name = "informationGiven")
	private String informationGiven;
	
	@Expose
	@Column(name = "Deleted", insertable = false)
	private Boolean deleted;
	
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
	private @SQLInjectionSafe String vehicalNo;

	@Expose
	@Column(name = "VanID")
	private Integer vanID;

	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "SyncedBy")
	private @SQLInjectionSafe String syncedBy;

	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;

	@Expose
	@Column(name = "ReservedForChange")
	private @SQLInjectionSafe String reservedForChange;
	


}
