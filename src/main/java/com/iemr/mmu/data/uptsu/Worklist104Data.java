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
package com.iemr.mmu.data.uptsu;

import java.math.BigInteger;
import java.sql.Timestamp;

import com.iemr.mmu.data.login.MasterVan;

import lombok.Data;
@Data
public class Worklist104Data {
	private String beneficiaryRegID;
	private String benCallID;
	private boolean referredFlag;
	private String benName;
	private boolean deleted;
	private BigInteger age;
	//private int ben_age_val;
	private Timestamp dOB;
	private int genderID;
	private String genderName;
	private Timestamp appointmentDate;
	private String preferredPhoneNum;
	private String districtName;
	private int districtID;
	private int villageID;
	private String villageName;
	private String facilityName;
	private String facilityCode;
	private int providerServiceMapId;
	private BigInteger beneficiaryID;
	private char processed;
	private String beneficiaryImage;
	private String benAge;
}
