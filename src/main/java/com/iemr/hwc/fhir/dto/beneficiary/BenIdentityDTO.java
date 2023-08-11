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
public class BenIdentityDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private BigInteger benIdentityId;
	private String createdBy;
	private Timestamp createdDate;
	private Boolean deleted = false;
	private Timestamp expiryDate;
	private String identityFilePath;
	private Integer identityNameId;
	private String identityName;
	private String identityNo;
	private Integer identityTypeId;
	private String identityType;
	private Timestamp issueDate;
	private Boolean isVerified;
	private Timestamp lastModDate;
	private String modifiedBy;

	private Integer vanID;
	private Integer parkingPlaceID;
}