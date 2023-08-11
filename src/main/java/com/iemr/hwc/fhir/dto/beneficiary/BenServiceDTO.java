/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.hwc.fhir.dto.beneficiary;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

@Data
public class BenServiceDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private BigInteger benServiceMapID;
	private String createdBy;
	private Timestamp createdDate;
	private Timestamp firstAvailedOn;
	private Timestamp lastModDate;
	private String modifiedBy;
	private String registeredByName;
	private Integer registeredById;
	private Timestamp registeredDate;
	private Integer providerServiceMapId;
	private Integer serviceId;
	private String serviceName;
	private Integer serviceProviderId;
	private String serviceProviderName;
	private Integer stateId;
	private String stateName;

	private Integer vanID;
	private Integer parkingPlaceID;
}