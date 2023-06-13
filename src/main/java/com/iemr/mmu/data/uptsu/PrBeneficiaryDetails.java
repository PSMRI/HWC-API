/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.uptsu;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;

import lombok.Data;

@Data
public class PrBeneficiaryDetails {
	private String beneficiaryName;
	private BigInteger beneficiaryID;
	private Timestamp dob;
	private BigInteger age;
	private int genderId;
	private String gender;
	private int districtId;
	private String district;
	private int villageId;
	private String village;
	private String beneficiaryImage;
	private String phoneNo;
	
	
	public static PrBeneficiaryDetails getPrBeneficiaryDetails(ArrayList<Object[]> obj) {
		if (obj != null && obj.size() > 0) {
			Object[] obj1 = obj.get(0);
			PrBeneficiaryDetails tmpOBJ = new PrBeneficiaryDetails((String) obj1[0], (BigInteger) obj1[1], (Timestamp) obj1[2],
					(BigInteger) obj1[3], (Integer) obj1[4], (String) obj1[5], (Integer) obj1[6], (String) obj1[7],
					(Integer) obj1[8], (String) obj1[9], (String) obj1[10], (String) obj1[11]);
			return tmpOBJ;
		} else {
			return null;
		}
	}


	public PrBeneficiaryDetails(String beneficiaryName, BigInteger beneficiaryID, Timestamp dob, BigInteger age, int genderId,
			String gender, int districtId, String district, int villageId, String village, String beneficiaryImage,
			String phoneNo) {
		super();
		this.beneficiaryName = beneficiaryName;
		this.beneficiaryID = beneficiaryID;
		this.dob = dob;
		this.age = age;
		this.genderId = genderId;
		this.gender = gender;
		this.districtId = districtId;
		this.district = district;
		this.villageId = villageId;
		this.village = village;
		this.beneficiaryImage = beneficiaryImage;
		this.phoneNo = phoneNo;
	}
}
