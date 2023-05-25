package com.iemr.mmu.data.location;

import lombok.Data;

@Data
public class LocationDetails {

	private Integer villageID;

	private String villageName;
	
	private Integer blockID;

	private String blockName;

	private Integer districtID;

	private String districtName;

	private Integer stateID;

	private String stateName;
	
	private Integer countryID;

	private String countryName;
	
	public LocationDetails( Integer villageID,String villageName,Integer blockID, String blockName, Integer districtID, String districtName, Integer stateID,  String stateName, Integer countryID, String countryName) {
		super();
		this.villageID = villageID;
		this.villageName = villageName;
		this.blockID = blockID;
		this.blockName = blockName;
		this.districtID = districtID;
		this.districtName = districtName;
		this.stateID = stateID;
		this.stateName = stateName;
		this.countryID = countryID;
		this.countryName = countryName;
		
	}

	public LocationDetails() {
		// TODO Auto-generated constructor stub
	}

}
