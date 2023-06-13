/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.location;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "v_get_prkngplc_blok_dist_zone_state")
public class V_GetLocDetailsFromSPidAndPSMid {

	@Id
	@GeneratedValue
	private Long ID;

	@Expose
	@Column(name = "Servicepointid")
	private Integer servicepointid;
	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;
	@Expose
	@Column(name = "ParkingPlaceName")
	private String parkingPlaceName;
	@Expose
	@Column(name = "BlockID")
	private Integer blockID;
	@Expose
	@Column(name = "BlockName")
	private String blockName;
	@Expose
	@Column(name = "DistrictID")
	private Integer districtID;
	@Expose
	@Column(name = "DistrictName")
	private String districtName;
	@Expose
	@Column(name = "ZoneID")
	private Integer zoneID;
	@Expose
	@Column(name = "ZoneName")
	private String zoneName;
	@Expose
	@Column(name = "StateID")
	private Integer stateID;
	@Expose
	@Column(name = "StateName")
	private String stateName;
	@Expose
	@Column(name = "SPproviderservicemapid")
	private Integer spproviderservicemapid;
	@Expose
	@Column(name = "PPproviderservicemapid")
	private Integer ppproviderservicemapid;
	@Expose
	@Column(name = "ZDMproviderservicemapid")
	private Integer zdmproviderservicemapid;

	public V_GetLocDetailsFromSPidAndPSMid() {
	}

	public V_GetLocDetailsFromSPidAndPSMid(Integer parkingPlaceID, String parkingPlaceName, Integer blockID,
			String blockName, Integer districtID, String districtName, Integer zoneID, String zoneName, Integer stateID,
			String stateName) {
		super();

		this.parkingPlaceID = parkingPlaceID;
		this.parkingPlaceName = parkingPlaceName;
		this.blockID = blockID;
		this.blockName = blockName;
		this.districtID = districtID;
		this.districtName = districtName;
		this.zoneID = zoneID;
		this.zoneName = zoneName;
		this.stateID = stateID;
		this.stateName = stateName;
	}

	public static V_GetLocDetailsFromSPidAndPSMid getOtherLocDetails(ArrayList<Object[]> obj) {
		V_GetLocDetailsFromSPidAndPSMid dataOBJ = null;

		for (Object[] obj1 : obj) {
			dataOBJ = new V_GetLocDetailsFromSPidAndPSMid((Integer) obj1[0], (String) obj1[1], (Integer) obj1[2],
					(String) obj1[3], (Integer) obj1[4], (String) obj1[5], (Integer) obj1[6], (String) obj1[7],
					(Integer) obj1[8], (String) obj1[9]);
		}

		return dataOBJ;
	}

	public Integer getServicepointid() {
		return servicepointid;
	}

	public void setServicepointid(Integer servicepointid) {
		this.servicepointid = servicepointid;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public String getParkingPlaceName() {
		return parkingPlaceName;
	}

	public void setParkingPlaceName(String parkingPlaceName) {
		this.parkingPlaceName = parkingPlaceName;
	}

	public Integer getBlockID() {
		return blockID;
	}

	public void setBlockID(Integer blockID) {
		this.blockID = blockID;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public Integer getDistrictID() {
		return districtID;
	}

	public void setDistrictID(Integer districtID) {
		this.districtID = districtID;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Integer getZoneID() {
		return zoneID;
	}

	public void setZoneID(Integer zoneID) {
		this.zoneID = zoneID;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public Integer getStateID() {
		return stateID;
	}

	public void setStateID(Integer stateID) {
		this.stateID = stateID;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Integer getSpproviderservicemapid() {
		return spproviderservicemapid;
	}

	public void setSpproviderservicemapid(Integer spproviderservicemapid) {
		this.spproviderservicemapid = spproviderservicemapid;
	}

	public Integer getPpproviderservicemapid() {
		return ppproviderservicemapid;
	}

	public void setPpproviderservicemapid(Integer ppproviderservicemapid) {
		this.ppproviderservicemapid = ppproviderservicemapid;
	}

	public Integer getZdmproviderservicemapid() {
		return zdmproviderservicemapid;
	}

	public void setZdmproviderservicemapid(Integer zdmproviderservicemapid) {
		this.zdmproviderservicemapid = zdmproviderservicemapid;
	}

}
