/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "v_get_prkngplc_dist_zone_state_from_spid")
public class V_get_prkngplc_dist_zone_state_from_spid {
	@Id
	@GeneratedValue
	private Long ID;

	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	@Expose
	@Column(name = "ServicePointID")
	private Integer servicePointID;
	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;
	@Expose
	@Column(name = "ParkingPlaceName")
	private String parkingPlaceName;
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

	public V_get_prkngplc_dist_zone_state_from_spid() {
	}

	public V_get_prkngplc_dist_zone_state_from_spid(Integer parkingPlaceID, String parkingPlaceName, Integer districtID,
			String districtName, Integer zoneID, String zoneName, Integer stateID, String stateName) {
		super();

		this.parkingPlaceID = parkingPlaceID;
		this.parkingPlaceName = parkingPlaceName;
		this.districtID = districtID;
		this.districtName = districtName;
		this.zoneID = zoneID;
		this.zoneName = zoneName;
		this.stateID = stateID;
		this.stateName = stateName;
	}

}
