package com.iemr.mmu.data.anc;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
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
@Table(name = "t_benFamilyHistory")
public class BenFamilyHistory {
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
	@Column(name = "FamilyMember")
	private @SQLInjectionSafe String familyMember;

	@Expose
	@Column(name = "DiseaseTypeID")
	private Short diseaseTypeID;

	@Expose
	@Column(name = "DiseaseType")
	private @SQLInjectionSafe String diseaseType;

	@Expose
	@Column(name = "OtherDiseaseType")
	private String otherDiseaseType;

	@Expose
	@Column(name = "IsGeneticDisorder")
	private Boolean isGeneticDisorder;

	@Transient
	private @SQLInjectionSafe String IsGeneticDisorder;

	@Expose
	@Column(name = "GeneticDisorder")
	private @SQLInjectionSafe String geneticDisorder;

	@Expose
	@Column(name = "IsConsanguineousMarrige")
	private Boolean isConsanguineousMarrige;

	@Transient
	private @SQLInjectionSafe String IsConsanguineousMarrige;

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
	private List<Map<String, Object>> familyDiseaseList;

	@Expose
	@Column(name = "Sctcode")
	private @SQLInjectionSafe String snomedCode;

	@Expose
	@Column(name = "SctTerm")
	private @SQLInjectionSafe String snomedTerm;

	@Transient
	private Date captureDate;

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

	public void setIsGeneticDisorder(String isGeneticDisorder) {
		IsGeneticDisorder = isGeneticDisorder;
	}

	public void setIsConsanguineousMarrige(String isConsanguineousMarrige) {
		IsConsanguineousMarrige = isConsanguineousMarrige;
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

	public String getFamilyMember() {
		return familyMember;
	}

	public void setFamilyMember(String familyMember) {
		this.familyMember = familyMember;
	}

	public String getDiseaseType() {
		return diseaseType;
	}

	public void setDiseaseType(String diseaseType) {
		this.diseaseType = diseaseType;
	}

	public Boolean getIsGeneticDisorder() {
		return isGeneticDisorder;
	}

	public void setIsGeneticDisorder(Boolean isGeneticDisorder) {
		this.isGeneticDisorder = isGeneticDisorder;
	}

	public String getGeneticDisorder() {
		return geneticDisorder;
	}

	public void setGeneticDisorder(String geneticDisorder) {
		this.geneticDisorder = geneticDisorder;
	}

	public Boolean getIsConsanguineousMarrige() {
		return isConsanguineousMarrige;
	}

	public void setIsConsanguineousMarrige(Boolean isConsanguineousMarrige) {
		this.isConsanguineousMarrige = isConsanguineousMarrige;
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

	public List<Map<String, Object>> getFamilyDiseaseList() {
		return familyDiseaseList;
	}

	public void setFamilyDiseaseList(List<Map<String, Object>> familyDiseaseList) {
		this.familyDiseaseList = familyDiseaseList;
	}

	public Long getID() {
		return ID;
	}

	public Short getDiseaseTypeID() {
		return diseaseTypeID;
	}

	public void setDiseaseTypeID(Short diseaseTypeID) {
		this.diseaseTypeID = diseaseTypeID;
	}

	public String getOtherDiseaseType() {
		return otherDiseaseType;
	}

	public void setOtherDiseaseType(String otherDiseaseType) {
		this.otherDiseaseType = otherDiseaseType;
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

	public BenFamilyHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BenFamilyHistory(Date createdDate, String familyMember, String diseaseType, String otherDiseaseType,
			Boolean isGeneticDisorder, String geneticDisorder, Boolean isConsanguineousMarrige) {
		super();
		this.captureDate = createdDate;
		this.familyMember = familyMember;
		this.diseaseType = diseaseType;
		this.otherDiseaseType = otherDiseaseType;
		if (null != isGeneticDisorder && isGeneticDisorder)
			this.IsGeneticDisorder = "Yes";
		else
			this.IsGeneticDisorder = "No";
		this.geneticDisorder = geneticDisorder;
		if (null != isConsanguineousMarrige && isConsanguineousMarrige)
			this.IsConsanguineousMarrige = "Yes";
		else
			this.IsConsanguineousMarrige = "No";
	}

	public ArrayList<BenFamilyHistory> getBenFamilyHistory() {

		ArrayList<BenFamilyHistory> benFamilyHistoryList = new ArrayList<BenFamilyHistory>();
		if (null != familyDiseaseList) {
			for (Map<String, Object> disease : familyDiseaseList) {
				BenFamilyHistory benFamilyHistory = new BenFamilyHistory();
				benFamilyHistory.setBeneficiaryRegID(beneficiaryRegID);
				benFamilyHistory.setBenVisitID(benVisitID);
				benFamilyHistory.setVisitCode(visitCode);
				benFamilyHistory.setProviderServiceMapID(providerServiceMapID);
				benFamilyHistory.setVanID(vanID);
				benFamilyHistory.setParkingPlaceID(parkingPlaceID);
				benFamilyHistory.setCreatedBy(createdBy);

				benFamilyHistory.setGeneticDisorder(geneticDisorder);
				benFamilyHistory.setIsGeneticDisorder(isGeneticDisorder);
				benFamilyHistory.setIsConsanguineousMarrige(isConsanguineousMarrige);

				if (null != disease.get("diseaseTypeID")) {
					benFamilyHistory.setDiseaseTypeID(new Short(disease.get("diseaseTypeID").toString()));
				}
				if (null != disease.get("diseaseType")) {
					benFamilyHistory.setDiseaseType(disease.get("diseaseType").toString());
				}

				if (null != disease.get("otherDiseaseType")) {
					benFamilyHistory.setOtherDiseaseType(disease.get("otherDiseaseType").toString());
				}

				if (null != disease.get("snomedCode")) {
					benFamilyHistory.setSnomedCode(disease.get("snomedCode").toString());
				}
				if (null != disease.get("snomedTerm")) {
					benFamilyHistory.setSnomedTerm(disease.get("snomedTerm").toString());
				}

				List<String> familyMemberList = (List<String>) disease.get("familyMembers");

				String familyMembers = "";
				if (null != familyMemberList) {
					int length = familyMemberList.size();
					for (int i = 0; i < length; i++) {
						if (i == length - 1)
							familyMembers += familyMemberList.get(i);
						else
							familyMembers += familyMemberList.get(i) + ",";
					}

					// for (String familyMember : familyMemberList) {
					// familyMembers += familyMember + ",";
					// }
				}
				benFamilyHistory.setFamilyMember(familyMembers);
				// code changed by Neeraj on 18-05-2018
				if (benFamilyHistory.getDiseaseTypeID() != null || benFamilyHistory.getFamilyMember() != null
						|| geneticDisorder != null || isGeneticDisorder != null || isConsanguineousMarrige != null)
					benFamilyHistoryList.add(benFamilyHistory);
			}
		}
		return benFamilyHistoryList;
	}

	public ArrayList<BenFamilyHistory> getBenFamilyHist() {

		ArrayList<BenFamilyHistory> benFamilyHistoryList = new ArrayList<BenFamilyHistory>();
		if (null != familyDiseaseList) {
			for (Map<String, Object> disease : familyDiseaseList) {
				BenFamilyHistory benFamilyHistory = new BenFamilyHistory();
				if (disease.containsKey("ID") && disease.get("ID") != null) {					
					benFamilyHistory.setID(((Double) disease.get("ID")).longValue());
				}
				benFamilyHistory.setBeneficiaryRegID(beneficiaryRegID);
				benFamilyHistory.setBenVisitID(benVisitID);
				benFamilyHistory.setVisitCode(visitCode);
				benFamilyHistory.setProviderServiceMapID(providerServiceMapID);
				benFamilyHistory.setVanID(vanID);
				benFamilyHistory.setParkingPlaceID(parkingPlaceID);
				benFamilyHistory.setCreatedBy(createdBy);
				if (benFamilyHistory.getID() != null)
					benFamilyHistory.setProcessed("U");
				else
					benFamilyHistory.setProcessed("N");

				if (disease.get("deleted").toString().equalsIgnoreCase("false"))
					benFamilyHistory.setDeleted(false);
				else if (disease.get("deleted").toString().equalsIgnoreCase("true"))
					benFamilyHistory.setDeleted(true);

				benFamilyHistory.setGeneticDisorder(geneticDisorder);
				benFamilyHistory.setIsGeneticDisorder(isGeneticDisorder);
				benFamilyHistory.setIsConsanguineousMarrige(isConsanguineousMarrige);

				if (null != disease.get("diseaseTypeID")) {
					benFamilyHistory.setDiseaseTypeID(new Short(disease.get("diseaseTypeID").toString()));
				}
				if (null != disease.get("diseaseType")) {
					benFamilyHistory.setDiseaseType(disease.get("diseaseType").toString());
				}

				if (null != disease.get("otherDiseaseType")) {
					benFamilyHistory.setOtherDiseaseType(disease.get("otherDiseaseType").toString());
				}

				if (null != disease.get("snomedCode")) {
					benFamilyHistory.setSnomedCode(disease.get("snomedCode").toString());
				}
				if (null != disease.get("snomedTerm")) {
					benFamilyHistory.setSnomedTerm(disease.get("snomedTerm").toString());
				}

				List<String> familyMemberList = (List<String>) disease.get("familyMembers");

				String familyMembers = "";
				if (null != familyMemberList) {
					int length = familyMemberList.size();
					for (int i = 0; i < length; i++) {
						if (i == length - 1)
							familyMembers += familyMemberList.get(i);
						else
							familyMembers += familyMemberList.get(i) + ",";
					}

					// for (String familyMember : familyMemberList) {
					// familyMembers += familyMember + ",";
					// }
				}
				benFamilyHistory.setFamilyMember(familyMembers);
				// code changed by Neeraj on 18-05-2018
				if (benFamilyHistory.getDiseaseTypeID() != null || benFamilyHistory.getFamilyMember() != null
						|| geneticDisorder != null || isGeneticDisorder != null || isConsanguineousMarrige != null)
					benFamilyHistoryList.add(benFamilyHistory);
			}
		}
		return benFamilyHistoryList;
	}

	public BenFamilyHistory(Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			Boolean isGeneticDisorder, String geneticDisorder, Boolean isConsanguineousMarrige, Long visitCode) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.isGeneticDisorder = isGeneticDisorder;
		this.geneticDisorder = geneticDisorder;
		this.isConsanguineousMarrige = isConsanguineousMarrige;
		this.visitCode = visitCode;
	}

	public BenFamilyHistory(String familyMember, Short diseaseTypeID, String diseaseType, String otherDiseaseType,
			String snomedCode, String snomedTerm) {
		super();
		this.familyMember = familyMember;
		this.diseaseTypeID = diseaseTypeID;
		this.diseaseType = diseaseType;
		this.otherDiseaseType = otherDiseaseType;
		this.snomedCode = snomedCode;
		this.snomedTerm = snomedTerm;
	}

	public BenFamilyHistory(Long ID, String familyMember, Short diseaseTypeID, String diseaseType,
			String otherDiseaseType, String snomedCode, String snomedTerm) {
		super();
		this.ID = ID;
		this.familyMember = familyMember;
		this.diseaseTypeID = diseaseTypeID;
		this.diseaseType = diseaseType;
		this.otherDiseaseType = otherDiseaseType;
		this.snomedCode = snomedCode;
		this.snomedTerm = snomedTerm;
	}

	public static BenFamilyHistory getBenFamilyHistory(ArrayList<Object[]> familyHistory) {
		BenFamilyHistory benfamilyHistory = null;
		if (null != familyHistory && familyHistory.size() > 0) {
			Object[] obj1 = familyHistory.get(0);

			benfamilyHistory = new BenFamilyHistory((Long) obj1[0], (Long) obj1[1], (Integer) obj1[2],
					(Boolean) obj1[7], (String) obj1[8], (Boolean) obj1[9], (Long) obj1[10]);

			List<Map<String, Object>> familyDiseaseList = new ArrayList<Map<String, Object>>();

			for (Object[] obj : familyHistory) {
				BenFamilyHistory familyDetails = new BenFamilyHistory((String) obj[3], (Short) obj[4], (String) obj[5],
						(String) obj[6], (String) obj[11], (String) obj[12]);

				Map<String, Object> familyDisease = new HashMap<String, Object>();
				familyDisease.put("diseaseTypeID", familyDetails.getDiseaseTypeID());
				familyDisease.put("diseaseType", familyDetails.getDiseaseType());
				familyDisease.put("otherDiseaseType", familyDetails.getOtherDiseaseType());
				familyDisease.put("snomedCode", familyDetails.getSnomedCode());
				familyDisease.put("snomedTerm", familyDetails.getSnomedTerm());

				if (null != familyDetails.getFamilyMember()) {
					String[] familyMembers = familyDetails.getFamilyMember().split(",");

					List<String> familyMembersList = new ArrayList<String>();
					for (String familyMember : familyMembers) {
						familyMembersList.add(familyMember);
					}
					
					familyDisease.put("familyMembers", familyMembersList);
					
				}
				
				familyDiseaseList.add(familyDisease);
			}
			benfamilyHistory.setFamilyDiseaseList(familyDiseaseList);
		}
		return benfamilyHistory;
	}

	public static BenFamilyHistory getBenFamilyHist(ArrayList<Object[]> familyHistory) {
		BenFamilyHistory benfamilyHistory = null;
		if (null != familyHistory && familyHistory.size() > 0) {
			Object[] obj1 = familyHistory.get(0);

			benfamilyHistory = new BenFamilyHistory((Long) obj1[1], (Long) obj1[2], (Integer) obj1[3],
					(Boolean) obj1[8], (String) obj1[9], (Boolean) obj1[10], (Long) obj1[11]);

			List<Map<String, Object>> familyDiseaseList = new ArrayList<Map<String, Object>>();

			for (Object[] obj : familyHistory) {
				BenFamilyHistory familyDetails = new BenFamilyHistory((Long) obj[0], (String) obj[4], (Short) obj[5],
						(String) obj[6], (String) obj[7], (String) obj[12], (String) obj[13]);

				Map<String, Object> familyDisease = new HashMap<String, Object>();
				familyDisease.put("ID", familyDetails.getID());
				familyDisease.put("deleted", false);
				familyDisease.put("diseaseTypeID", familyDetails.getDiseaseTypeID());
				familyDisease.put("diseaseType", familyDetails.getDiseaseType());
				familyDisease.put("otherDiseaseType", familyDetails.getOtherDiseaseType());
				familyDisease.put("snomedCode", familyDetails.getSnomedCode());
				familyDisease.put("snomedTerm", familyDetails.getSnomedTerm());

				if (null != familyDetails.getFamilyMember()) {
					String[] familyMembers = familyDetails.getFamilyMember().split(",");

					List<String> familyMembersList = new ArrayList<String>();
					for (String familyMember : familyMembers) {
						familyMembersList.add(familyMember);
					}
					familyDisease.put("familyMembers", familyMembersList);
				}
				familyDiseaseList.add(familyDisease);
			}
			benfamilyHistory.setFamilyDiseaseList(familyDiseaseList);
		}
		return benfamilyHistory;
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
