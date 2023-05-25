package com.iemr.mmu.data.anc;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;

@Entity
@Table(name = "t_BenAllergyHistory")
public class BenAllergyHistory {

	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ID")
	private Long ID;
	
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
	@Column(name = "allergyStatus")
	private @SQLInjectionSafe String allergyStatus;

	@Expose
	@Column(name = "allergyType")
	private @SQLInjectionSafe String allergyType;

	@Expose
	@Column(name = "allergenName")
	private @SQLInjectionSafe String allergyName;

	@Expose
	@Column(name = "allergicReactionTypeID")
	private @SQLInjectionSafe String allergicReactionTypeID;

	@Expose
	@Column(name = "allergicReactionType")
	private @SQLInjectionSafe String allergicReactionType;

	@Expose
	@Column(name = "OtherAllergicReaction")
	private @SQLInjectionSafe String otherAllergicReaction;

	@Transient
	@Expose
	private List<Map<String, String>> typeOfAllergicReactions;

	@Expose
	@Column(name = "Remarks")
	private @SQLInjectionSafe String remarks;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private @SQLInjectionSafe String processed;

	@Expose
	@Column(name = "CreatedBy")
	private @SQLInjectionSafe String createdBy;

	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy")
	private @SQLInjectionSafe String modifiedBy;

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
	@Column(name = "vanID")
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

	@Transient
	@Expose
	private List<Map<String, Object>> allergicList;

	@Transient
	private Date captureDate;

	@Expose
	@Column(name = "Sctcode")
	private @SQLInjectionSafe String snomedCode;
	
	@Expose
	@Column(name = "SctTerm")
	private @SQLInjectionSafe String snomedTerm;
	
	public BenAllergyHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BenAllergyHistory(Date createdDate, String allergyStatus, String allergyType, String allergenName,
			String allergicReactionType, String otherAllergicReaction, String remarks) {
		super();
		this.captureDate = createdDate;
		this.allergyStatus = allergyStatus;
		this.allergyType = allergyType;
		this.allergyName = allergenName;
		this.allergicReactionType = allergicReactionType;
		this.otherAllergicReaction = otherAllergicReaction;
		this.remarks = remarks;
	}

	public BenAllergyHistory(String allergyStatus, String allergyType, String allergenName,
			String allergicReactionTypeID, String allergicReactionType, String otherAllergicReaction, String remarks,
			Long visitCode, String snomedCode, String snomedTerm) {
		super();
		this.allergyStatus = allergyStatus;
		this.allergyType = allergyType;
		this.allergyName = allergenName;
		this.allergicReactionTypeID = allergicReactionTypeID;
		this.allergicReactionType = allergicReactionType;
		this.otherAllergicReaction = otherAllergicReaction;
		this.remarks = remarks;
		this.visitCode = visitCode;
		this.snomedCode = snomedCode;
		this.snomedTerm = snomedTerm;
	}

	public String getSnomedCode() {
		return snomedCode;
	}

	public void setSnomedCode(String snomedCode) {
		this.snomedCode = snomedCode;
	}

	public String getSnomedTerm() {
		return snomedTerm;
	}

	public void setSnomedTerm(String snomedTerm) {
		this.snomedTerm = snomedTerm;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Date getCaptureDate() {
		return captureDate;
	}

	public void setCaptureDate(Date captureDate) {
		this.captureDate = captureDate;
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

	public String getAllergyStatus() {
		return allergyStatus;
	}

	public void setAllergyStatus(String allergyStatus) {
		this.allergyStatus = allergyStatus;
	}

	public String getAllergyType() {
		return allergyType;
	}

	public void setAllergyType(String allergyType) {
		this.allergyType = allergyType;
	}

	public String getAllergyName() {
		return allergyName;
	}

	public void setAllergyName(String allergyName) {
		this.allergyName = allergyName;
	}

	public String getAllergicReactionType() {
		return allergicReactionType;
	}

	public void setAllergicReactionType(String allergicReactionType) {
		this.allergicReactionType = allergicReactionType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public List<Map<String, Object>> getAllergicList() {
		return allergicList;
	}

	public void setAllergicList(List<Map<String, Object>> allergicList) {
		this.allergicList = allergicList;
	}

	public Long getID() {
		return ID;
	}

	public List<Map<String, String>> getTypeOfAllergicReactions() {
		return typeOfAllergicReactions;
	}

	public void setTypeOfAllergicReactions(List<Map<String, String>> typeOfAllergicReactions) {
		this.typeOfAllergicReactions = typeOfAllergicReactions;
	}

	public String getOtherAllergicReaction() {
		return otherAllergicReaction;
	}

	public void setOtherAllergicReaction(String otherAllergicReaction) {
		this.otherAllergicReaction = otherAllergicReaction;
	}

	public String getAllergicReactionTypeID() {
		return allergicReactionTypeID;
	}

	public void setAllergicReactionTypeID(String allergicReactionTypeID) {
		this.allergicReactionTypeID = allergicReactionTypeID;
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

	public ArrayList<BenAllergyHistory> getBenAllergicHistory() {
		ArrayList<BenAllergyHistory> benAllergyHistoryList = new ArrayList<BenAllergyHistory>();
		if (allergicList.size() > 0) {
			for (Map<String, Object> allergic : allergicList) {
				BenAllergyHistory benAllergyHistory = new BenAllergyHistory();

				if(allergic.get("snomedCode") == null) {
					allergic.put("snomedTerm", null);
				}
				if (null != allergic.get("allergyName")) {
					benAllergyHistory.setAllergyName(allergic.get("allergyName").toString());
				}
				if (null != allergic.get("allergyType")) {
					benAllergyHistory.setAllergyType(allergic.get("allergyType").toString());
				}
				if (null != allergic.get("snomedCode")) {
					benAllergyHistory.setSnomedCode(allergic.get("snomedCode").toString());
				}
				if (null != allergic.get("snomedTerm")) {
					benAllergyHistory.setSnomedTerm(allergic.get("snomedTerm").toString());
				}

				benAllergyHistory.setBeneficiaryRegID(beneficiaryRegID);
				benAllergyHistory.setBenVisitID(benVisitID);
				benAllergyHistory.setVisitCode(visitCode);
				benAllergyHistory.setProviderServiceMapID(providerServiceMapID);
				benAllergyHistory.setVanID(vanID);
				benAllergyHistory.setParkingPlaceID(parkingPlaceID);
				benAllergyHistory.setAllergyStatus(allergyStatus);
				benAllergyHistory.setCreatedBy(createdBy);

				List<Map<String, String>> reactionTypesList = (List<Map<String, String>>) allergic
						.get("typeOfAllergicReactions");
				String reactionTypes = "";
				String reactionTypeIDs = "";
				if (null != reactionTypesList) {

					int length = reactionTypesList.size();
					for (int i = 0; i < length; i++) {
						if (i == length - 1) {
							reactionTypes += reactionTypesList.get(i).get("name");
							reactionTypeIDs += reactionTypesList.get(i).get("allergicReactionTypeID");
						} else {
							reactionTypes += reactionTypesList.get(i).get("name") + ",";
							reactionTypeIDs += reactionTypesList.get(i).get("allergicReactionTypeID") + ",";
						}
					}
					// for (Map<String, String> reactionType : reactionTypesList) {
					// reactionTypes += reactionType.get("name") + ",";
					// reactionTypeIDs += reactionType.get("allergicReactionTypeID") + ",";
					// }
				}
				benAllergyHistory.setAllergicReactionTypeID(reactionTypeIDs);
				benAllergyHistory.setAllergicReactionType(reactionTypes);

				if (null != allergic.get("otherAllergicReaction")) {
					benAllergyHistory.setOtherAllergicReaction(allergic.get("otherAllergicReaction").toString());
				}

				// if (benAllergyHistory.getAllergyType() != null)
				benAllergyHistoryList.add(benAllergyHistory);
			}
		} else {
			if (allergyStatus != null) {

				BenAllergyHistory benAllergyHistory = new BenAllergyHistory();

				benAllergyHistory.setBeneficiaryRegID(beneficiaryRegID);
				benAllergyHistory.setBenVisitID(benVisitID);
				benAllergyHistory.setVisitCode(visitCode);
				benAllergyHistory.setProviderServiceMapID(providerServiceMapID);
				benAllergyHistory.setVanID(vanID);
				benAllergyHistory.setParkingPlaceID(parkingPlaceID);
				benAllergyHistory.setCreatedBy(createdBy);

				benAllergyHistory.setAllergyStatus(allergyStatus);

				benAllergyHistoryList.add(benAllergyHistory);
			}
		}
		return benAllergyHistoryList;
	}

	public static ArrayList<BenAllergyHistory> getBenAllergicHistory(ArrayList<Object[]> allergyDetails) {
		ArrayList<BenAllergyHistory> benAllergyHistoryList = new ArrayList<BenAllergyHistory>();

		if (null != allergyDetails && allergyDetails.size() > 0) {
			for (Object[] obj : allergyDetails) {

				BenAllergyHistory allergyHistory = new BenAllergyHistory((String) obj[3], (String) obj[4],
						(String) obj[5], (String) obj[6], (String) obj[7], (String) obj[8], (String) obj[9],
						(Long) obj[10], (String) obj[11], (String) obj[12]);

				String allergicReactionTypeID = allergyHistory.getAllergicReactionTypeID();
				String[] allergicReactionTypeIDs = null;
				if (null != allergicReactionTypeID) {
					allergicReactionTypeIDs = allergicReactionTypeID.toString().split(",");
				}

				String allergicReactionType = allergyHistory.getAllergicReactionType();
				String[] allergicReactionTypes = null;
				if (null != allergicReactionType) {
					allergicReactionTypes = allergicReactionType.toString().split(",");
				}

				List<Map<String, String>> reactionTypesList = new ArrayList<Map<String, String>>();
				Map<String, String> reactionTypes = null;

				if (null != allergicReactionTypes && allergicReactionTypes.length > 0) {
					for (int i = 0; i < allergicReactionTypes.length; i++) {
						reactionTypes = new HashMap<String, String>();
						reactionTypes.put("name", allergicReactionTypes[i]);
						if (allergicReactionTypeIDs != null && allergicReactionTypeIDs.length > 0)
							reactionTypes.put("allergicReactionTypeID", allergicReactionTypeIDs[i]);

						reactionTypesList.add(reactionTypes);
					}
				}

				allergyHistory.setTypeOfAllergicReactions(reactionTypesList);

				benAllergyHistoryList.add(allergyHistory);
			}

		}
		return benAllergyHistoryList;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

}
