package com.iemr.mmu.data.quickConsultation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.iemr.mmu.data.anc.WrapperBenInvestigationANC;
import com.iemr.mmu.data.nurse.BenPhysicalVitalDetail;

@Entity
@Table(name = "t_lab_testorder")
public class LabTestOrderDetail {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ID")
	private Long labTestOrderID;

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
	@Column(name = "PrescriptionID")
	private Long prescriptionID;

	@Expose
	@Column(name = "ProcedureID")
	private Integer procedureID;

	@Expose
	@Column(name = "ProcedureName")
	private String procedureName;

	@Expose
	@Column(name = "TestingRequirements")
	private String testingRequirements;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
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
	@Column(name = "VanID")
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

	@Expose
	@Column(name = "ReservedForChange")
	private String reservedForChange;

	public LabTestOrderDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Long getBenVisitID() {
		return benVisitID;
	}

	public void setBenVisitID(Long benVisitID) {
		this.benVisitID = benVisitID;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Long getPrescriptionID() {
		return prescriptionID;
	}

	public void setPrescriptionID(Long prescriptionID) {
		this.prescriptionID = prescriptionID;
	}

	public Integer getProcedureID() {
		return procedureID;
	}

	public void setProcedureID(Integer procedureID) {
		this.procedureID = procedureID;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getTestingRequirements() {
		return testingRequirements;
	}

	public void setTestingRequirements(String testingRequirements) {
		this.testingRequirements = testingRequirements;
	}

	public void setLabTestOrderID(Long labTestOrderID) {
		this.labTestOrderID = labTestOrderID;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getLastModDate() {
		return lastModDate;
	}

	public void setLastModDate(Timestamp lastModDate) {
		this.lastModDate = lastModDate;
	}

	public Long getLabTestOrderID() {
		return labTestOrderID;
	}

	public Long getVanSerialNo() {
		return vanSerialNo;
	}

	public void setVanSerialNo(Long vanSerialNo) {
		this.vanSerialNo = vanSerialNo;
	}

	public String getVehicalNo() {
		return vehicalNo;
	}

	public void setVehicalNo(String vehicalNo) {
		this.vehicalNo = vehicalNo;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public String getSyncedBy() {
		return syncedBy;
	}

	public void setSyncedBy(String syncedBy) {
		this.syncedBy = syncedBy;
	}

	public Timestamp getSyncedDate() {
		return syncedDate;
	}

	public void setSyncedDate(Timestamp syncedDate) {
		this.syncedDate = syncedDate;
	}

	public String getReservedForChange() {
		return reservedForChange;
	}

	public void setReservedForChange(String reservedForChange) {
		this.reservedForChange = reservedForChange;
	}

	public static ArrayList<LabTestOrderDetail> getLabTestOrderDetailList(JsonObject emrgCasesheet,
			Long prescriptionID) {
		ArrayList<LabTestOrderDetail> resArray = new ArrayList<>();
		LabTestOrderDetail labTestOrderDetail = null;
		if (emrgCasesheet.has("labTestOrders") && !emrgCasesheet.get("labTestOrders").isJsonNull()
				&& emrgCasesheet.get("labTestOrders").isJsonArray()) {
			for (JsonElement csobj : emrgCasesheet.getAsJsonArray("labTestOrders")) {
				labTestOrderDetail = new LabTestOrderDetail();

				if (emrgCasesheet.has("benVisitID") && !emrgCasesheet.get("benVisitID").isJsonNull())
					labTestOrderDetail.setBenVisitID(emrgCasesheet.get("benVisitID").getAsLong());

				if (emrgCasesheet.has("visitCode") && !emrgCasesheet.get("visitCode").isJsonNull())
					labTestOrderDetail.setVisitCode(emrgCasesheet.get("visitCode").getAsLong());

				if (emrgCasesheet.has("beneficiaryRegID") && !emrgCasesheet.get("beneficiaryRegID").isJsonNull())
					labTestOrderDetail.setBeneficiaryRegID(emrgCasesheet.get("beneficiaryRegID").getAsLong());

				if (emrgCasesheet.has("providerServiceMapID")
						&& !emrgCasesheet.get("providerServiceMapID").isJsonNull())
					labTestOrderDetail.setProviderServiceMapID(emrgCasesheet.get("providerServiceMapID").getAsInt());

				if (emrgCasesheet.has("vanID") && !emrgCasesheet.get("vanID").isJsonNull())
					labTestOrderDetail.setVanID(emrgCasesheet.get("vanID").getAsInt());

				if (emrgCasesheet.has("parkingPlaceID") && !emrgCasesheet.get("parkingPlaceID").isJsonNull())
					labTestOrderDetail.setParkingPlaceID(emrgCasesheet.get("parkingPlaceID").getAsInt());

				labTestOrderDetail.setPrescriptionID(prescriptionID);

				JsonObject obj = csobj.getAsJsonObject();

				if (obj.has("procedureID") && !obj.get("procedureID").isJsonNull())
					labTestOrderDetail.setProcedureID(obj.get("procedureID").getAsInt());

				if (obj.has("procedureName") && !obj.get("procedureName").isJsonNull())
					labTestOrderDetail.setProcedureName(obj.get("procedureName").getAsString());
				;

				if (obj.has("testingRequirements") && !obj.get("testingRequirements").isJsonNull())
					labTestOrderDetail.setTestingRequirements(obj.get("testingRequirements").getAsString());

				if (emrgCasesheet.has("createdBy") && !emrgCasesheet.get("createdBy").isJsonNull())
					labTestOrderDetail.setCreatedBy(emrgCasesheet.get("createdBy").getAsString());

				resArray.add(labTestOrderDetail);
			}
		}

		return resArray;
	}

	public LabTestOrderDetail(Integer procedureID, String procedureName) {
		super();
		this.procedureID = procedureID;
		this.procedureName = procedureName;
	}
	
	public LabTestOrderDetail(String procedureName) {
		super();
		this.procedureName = procedureName;
	}

	public static WrapperBenInvestigationANC getLabTestOrderDetails(ArrayList<Object[]> resList) {
		ArrayList<LabTestOrderDetail> resArray = new ArrayList<LabTestOrderDetail>();

		WrapperBenInvestigationANC testOrders = new WrapperBenInvestigationANC();
		if (resList != null && resList.size() > 0) {
			Object[] obj1 = resList.get(0);
			testOrders.setBeneficiaryRegID((Long) obj1[0]);
			testOrders.setBenVisitID((Long) obj1[1]);
			testOrders.setVisitCode((Long) obj1[6]);
			testOrders.setProviderServiceMapID((Integer) obj1[2]);
			ArrayList<LabTestOrderDetail> laboratoryList = new ArrayList<LabTestOrderDetail>();
			for (Object[] obj : resList) {

				LabTestOrderDetail cOBJ = new LabTestOrderDetail((Integer) obj[3], (String) obj[4]);
				laboratoryList.add(cOBJ);
				// resArray.add(cOBJ);
			}
			testOrders.setLaboratoryList(laboratoryList);
		}
		return testOrders;
	}
	
	public static WrapperBenInvestigationANC getRBSTestOrderDetailsFromVitals(BenPhysicalVitalDetail resList) {
	

		WrapperBenInvestigationANC testOrders = new WrapperBenInvestigationANC();
		if (resList != null) {
			testOrders.setBeneficiaryRegID(resList.getBeneficiaryRegID());
			testOrders.setBenVisitID(resList.getBenVisitID());
			testOrders.setVisitCode(resList.getVisitCode());
			testOrders.setProviderServiceMapID(resList.getProviderServiceMapID());
			ArrayList<LabTestOrderDetail> laboratoryList = new ArrayList<LabTestOrderDetail>();
			
                 if(!Objects.equals(resList.getRbsTestResult(), null))
                 {
				   LabTestOrderDetail cOBJ = new LabTestOrderDetail("RBS Test");
				  laboratoryList.add(cOBJ);
                 }
				// resArray.add(cOBJ);
			
			testOrders.setLaboratoryList(laboratoryList);
		}
		return testOrders;
	}


	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

}
