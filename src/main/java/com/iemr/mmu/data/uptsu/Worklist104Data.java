/* LicenseInfo : Copyright © 2023 Piramal */ 
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
