package com.iemr.mmu.data.doctor;

import java.util.ArrayList;
import java.util.Map;

public class WrapperCancerExamImgAnotasn {
	private Long beneficiaryRegID;
	private Long visitID;
	private Long visitCode;
	private Integer providerServiceMapID;
	private Integer imageID;
	private ArrayList<Map<String, Object>> markers;
	private String createdBy;

	private Integer vanID;
	private Integer parkingPlaceID;

	public WrapperCancerExamImgAnotasn() {
	}

	public WrapperCancerExamImgAnotasn(Long beneficiaryRegID, Long visitID, Integer providerServiceMapID,
			Integer imageID, ArrayList<Map<String, Object>> markers, String createdBy) {
		super();
		this.beneficiaryRegID = beneficiaryRegID;
		this.visitID = visitID;
		this.providerServiceMapID = providerServiceMapID;
		this.imageID = imageID;
		this.markers = markers;
		this.createdBy = createdBy;
	}

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Long getVisitID() {
		return visitID;
	}

	public void setVisitID(Long visitID) {
		this.visitID = visitID;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Integer getImageID() {
		return imageID;
	}

	public void setImageID(Integer imageID) {
		this.imageID = imageID;
	}

	public ArrayList<Map<String, Object>> getMarkers() {
		return markers;
	}

	public void setMarkers(ArrayList<Map<String, Object>> markers) {
		this.markers = markers;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
