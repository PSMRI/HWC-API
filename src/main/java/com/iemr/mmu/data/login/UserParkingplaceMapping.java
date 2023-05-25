package com.iemr.mmu.data.login;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_userparkingplacemap")
public class UserParkingplaceMapping {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "UserParkingPlaceMapID")
	private Integer userParkingPlaceMapID;
	@Expose
	@Column(name = "UserID")
	private Integer userID;
	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;
	@Expose
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false, insertable = false, name = "parkingPlaceID")
	private ParkingPlace m_parkingplace;

	@Expose
	@Column(name = "StateID")
	private Integer stateID;
	@Expose
	@Column(name = "DistrictID")
	private Integer districtID;
	@Expose
	@Column(name = "ProviderServiceMapId")
	private Integer providerServiceMapId;
	@Expose
	@Column(name = "Deleted")
	private Integer deleted;

//	@OneToMany(mappedBy = "userParkingplaceMapping", cascade = CascadeType.ALL)
//	private Set<MasterVan> masterVanSet;

	// 23-01-2019, report download issue, since mapping is not available in mastervan.
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "parkingPlaceID", insertable = false, updatable = false)
//	private MasterVan masterVanSet;

	public UserParkingplaceMapping() {
	}

//	public UserParkingplaceMapping(Integer userParkingPlaceMapID, Integer userID, Integer parkingPlaceID,
//			Integer stateID, Integer districtID, Integer providerServiceMapId, Integer deleted,
//			Set<MasterVan> masterVanSet) {
//		super();
//		this.userParkingPlaceMapID = userParkingPlaceMapID;
//		this.userID = userID;
//		this.parkingPlaceID = parkingPlaceID;
//		this.stateID = stateID;
//		this.districtID = districtID;
//		this.providerServiceMapId = providerServiceMapId;
//		this.deleted = deleted;
//		this.masterVanSet = masterVanSet;
//	}

	public Integer getUserParkingPlaceMapID() {
		return userParkingPlaceMapID;
	}

	public void setUserParkingPlaceMapID(Integer userParkingPlaceMapID) {
		this.userParkingPlaceMapID = userParkingPlaceMapID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public Integer getStateID() {
		return stateID;
	}

	public void setStateID(Integer stateID) {
		this.stateID = stateID;
	}

	public Integer getDistrictID() {
		return districtID;
	}

	public void setDistrictID(Integer districtID) {
		this.districtID = districtID;
	}

	public Integer getProviderServiceMapId() {
		return providerServiceMapId;
	}

	public void setProviderServiceMapId(Integer providerServiceMapId) {
		this.providerServiceMapId = providerServiceMapId;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

//	public Set<MasterVan> getMasterVanSet() {
//		return masterVanSet;
//	}
//
//	public void setMasterVanSet(Set<MasterVan> masterVanSet) {
//		this.masterVanSet = masterVanSet;
//	}

}
