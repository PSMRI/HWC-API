package com.iemr.mmu.data.nurse;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_cancerpersonalhistory")
public class BenPersonalCancerHistory {
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
	@Column(name = "TobaccoUse")
	private String tobaccoUse;
	@Expose
	@Column(name = "StartAge_year")
	private Integer startAge_year;
	@Expose
	@Column(name = "EndAge_year")
	private Integer endAge_year;
	@Expose
	@Column(name = "TypeOfTobaccoProduct")
	private String typeOfTobaccoProduct;
	@Expose
	@Column(name = "QuantityPerDay")
	private Integer quantityPerDay;
	@Expose
	@Column(name = "IsFilteredCigaerette")
	private Boolean isFilteredCigaerette;

	@Transient
	private String IsFilteredCigaerette;

	@Expose
	@Column(name = "IsCigaretteExposure")
	private Boolean isCigaretteExposure;

	@Transient
	private String IsCigaretteExposure;

	@Expose
	@Column(name = "IsBetelNutChewing")
	private Boolean isBetelNutChewing;

	@Transient
	private String IsBetelNutChewing;

	@Expose
	@Column(name = "DurationOfBetelQuid")
	private Integer durationOfBetelQuid;
	@Expose
	@Column(name = "AlcoholUse")
	private String alcoholUse;
	@Expose
	@Column(name = "IsAlcoholUsed")
	private Boolean ssAlcoholUsed;

	@Transient
	private String IsAlcoholUsed;

	@Expose
	@Column(name = "FrequencyOfAlcoholUsed")
	private String frequencyOfAlcoholUsed;
	@Expose
	@Column(name = "Deleted", insertable = false)
	private Boolean deleted;
	@Expose
	@Column(name = "Processed", insertable = false)
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

	@Expose
	@Column(name = "ReservedForChange")
	private String reservedForChange;

	@Transient
	@JsonIgnore
	private List<String> typeOfTobaccoProductList;

	public BenPersonalCancerHistory() {
	}

	public BenPersonalCancerHistory(Long iD, Long beneficiaryRegID, Long benVisitID, Integer providerServiceMapID,
			String tobaccoUse, Integer startAge_year, Integer endAge_year, String typeOfTobaccoProduct,
			Integer quantityPerDay, Boolean isFilteredCigaerette, Boolean isCigaretteExposure,
			Boolean isBetelNutChewing, Integer durationOfBetelQuid, String alcoholUse, Boolean ssAlcoholUsed,
			String frequencyOfAlcoholUsed, Boolean deleted, String processed, String createdBy, Timestamp createdDate,
			String modifiedBy, Timestamp lastModDate, List<String> typeOfTobaccoProductList) {
		super();
		ID = iD;
		this.beneficiaryRegID = beneficiaryRegID;
		this.benVisitID = benVisitID;
		this.providerServiceMapID = providerServiceMapID;
		this.tobaccoUse = tobaccoUse;
		this.startAge_year = startAge_year;
		this.endAge_year = endAge_year;
		this.typeOfTobaccoProduct = typeOfTobaccoProduct;
		this.quantityPerDay = quantityPerDay;
		this.isFilteredCigaerette = isFilteredCigaerette;
		this.isCigaretteExposure = isCigaretteExposure;
		this.isBetelNutChewing = isBetelNutChewing;
		this.durationOfBetelQuid = durationOfBetelQuid;
		this.alcoholUse = alcoholUse;
		this.ssAlcoholUsed = ssAlcoholUsed;
		this.frequencyOfAlcoholUsed = frequencyOfAlcoholUsed;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
		this.typeOfTobaccoProductList = typeOfTobaccoProductList;
	}

	@Transient
	private Date captureDate;

	public BenPersonalCancerHistory(String tobaccoUse, Integer startAge_year, Integer endAge_year,
			String typeOfTobaccoProduct, Integer quantityPerDay, Boolean isFilteredCigaerette,
			Boolean isCigaretteExposure, Boolean isBetelNutChewing, Integer durationOfBetelQuid, String alcoholUse,
			Boolean ssAlcoholUsed, String frequencyOfAlcoholUsed, Date captureDate) {
		super();
		this.tobaccoUse = tobaccoUse;
		this.startAge_year = startAge_year;
		this.endAge_year = endAge_year;
		this.typeOfTobaccoProduct = typeOfTobaccoProduct;
		this.quantityPerDay = quantityPerDay;

		if (null != isFilteredCigaerette && isFilteredCigaerette)
			this.IsFilteredCigaerette = "Yes";
		else
			this.IsFilteredCigaerette = "No";

		if (null != isCigaretteExposure && isCigaretteExposure)
			this.IsCigaretteExposure = "Yes";
		else
			this.IsCigaretteExposure = "No";

		if (null != isBetelNutChewing && isBetelNutChewing)
			this.IsBetelNutChewing = "Yes";
		else
			this.IsBetelNutChewing = "No";

		this.durationOfBetelQuid = durationOfBetelQuid;
		this.alcoholUse = alcoholUse;

		if (null != ssAlcoholUsed && ssAlcoholUsed)
			this.IsAlcoholUsed = "Yes";
		else
			this.IsAlcoholUsed = "No";

		this.frequencyOfAlcoholUsed = frequencyOfAlcoholUsed;
		this.captureDate = captureDate;
	}

	public String getIsAlcoholUsed() {
		return IsAlcoholUsed;
	}

	public void setIsAlcoholUsed(String isAlcoholUsed) {
		IsAlcoholUsed = isAlcoholUsed;
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

	public void setIsFilteredCigaerette(String isFilteredCigaerette) {
		IsFilteredCigaerette = isFilteredCigaerette;
	}

	public void setIsCigaretteExposure(String isCigaretteExposure) {
		IsCigaretteExposure = isCigaretteExposure;
	}

	public void setIsBetelNutChewing(String isBetelNutChewing) {
		IsBetelNutChewing = isBetelNutChewing;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
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

	public String getTobaccoUse() {
		return tobaccoUse;
	}

	public void setTobaccoUse(String tobaccoUse) {
		this.tobaccoUse = tobaccoUse;
	}

	public Integer getStartAge_year() {
		return startAge_year;
	}

	public void setStartAge_year(Integer startAge_year) {
		this.startAge_year = startAge_year;
	}

	public Integer getEndAge_year() {
		return endAge_year;
	}

	public void setEndAge_year(Integer endAge_year) {
		this.endAge_year = endAge_year;
	}

	public String getTypeOfTobaccoProduct() {
		return typeOfTobaccoProduct;
	}

	public void setTypeOfTobaccoProduct(String typeOfTobaccoProduct) {
		this.typeOfTobaccoProduct = typeOfTobaccoProduct;
	}

	public Integer getQuantityPerDay() {
		return quantityPerDay;
	}

	public void setQuantityPerDay(Integer quantityPerDay) {
		this.quantityPerDay = quantityPerDay;
	}

	public Boolean getIsFilteredCigaerette() {
		return isFilteredCigaerette;
	}

	public void setIsFilteredCigaerette(Boolean isFilteredCigaerette) {
		this.isFilteredCigaerette = isFilteredCigaerette;
	}

	public Boolean getIsCigaretteExposure() {
		return isCigaretteExposure;
	}

	public void setIsCigaretteExposure(Boolean isCigaretteExposure) {
		this.isCigaretteExposure = isCigaretteExposure;
	}

	public Boolean getIsBetelNutChewing() {
		return isBetelNutChewing;
	}

	public void setIsBetelNutChewing(Boolean isBetelNutChewing) {
		this.isBetelNutChewing = isBetelNutChewing;
	}

	public Integer getDurationOfBetelQuid() {
		return durationOfBetelQuid;
	}

	public void setDurationOfBetelQuid(Integer durationOfBetelQuid) {
		this.durationOfBetelQuid = durationOfBetelQuid;
	}

	public String getAlcoholUse() {
		return alcoholUse;
	}

	public void setAlcoholUse(String alcoholUse) {
		this.alcoholUse = alcoholUse;
	}

	public Boolean getSsAlcoholUsed() {
		return ssAlcoholUsed;
	}

	public void setSsAlcoholUsed(Boolean ssAlcoholUsed) {
		this.ssAlcoholUsed = ssAlcoholUsed;
	}

	public String getFrequencyOfAlcoholUsed() {
		return frequencyOfAlcoholUsed;
	}

	public void setFrequencyOfAlcoholUsed(String frequencyOfAlcoholUsed) {
		this.frequencyOfAlcoholUsed = frequencyOfAlcoholUsed;
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

	public List<String> getTypeOfTobaccoProductList() {
		return typeOfTobaccoProductList;
	}

	public void setTypeOfTobaccoProductList(List<String> typeOfTobaccoProductList) {
		this.typeOfTobaccoProductList = typeOfTobaccoProductList;
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

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

}
