package com.iemr.mmu.data.brd;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.iemr.mmu.data.uptsu.PrBeneficiaryDetails;

import lombok.Data;

@Data

public class BRDIntegrationData {
	
	private Timestamp dateTime;
	private BigInteger beneficiaryId;
	private BigInteger beneficiaryRegId;
	private BigInteger bencallId;
	private String receivedRoleName;
	private String gender;
	private BigInteger age;
	private String martialStatus;
	private String state;
	private String district;
	private String block;
	private String village;
	//private String address;
	private String pincode;
	private String callGroupType;
	
	private String callType;
	//private String diseasesummary;
	//private String selectedDiagnosis;
	//private String categoryName;
	//private String subCategoryName;
	private BigInteger prescriptionId;
	private String drugName;
	
	public static List<BRDIntegrationData> getBRDDetails(ArrayList<Object[]> obj) {
		List<BRDIntegrationData> list = new ArrayList<BRDIntegrationData>();
		if (obj != null && obj.size() > 0) {
			for (Object[] obj1 : obj) {
				BRDIntegrationData tmpOBJ = new BRDIntegrationData((Timestamp) obj1[0], (BigInteger) obj1[1],
						(BigInteger) obj1[2], (BigInteger) obj1[3], (String) obj1[4], (String) obj1[5],
						(BigInteger) obj1[6], (String) obj1[7], (String) obj1[8], (String) obj1[9], (String) obj1[10],
						(String) obj1[11], (String) obj1[12], (String) obj1[13], (String) obj1[14],(BigInteger) obj1[15],
						//(String) obj1[16], (String) obj1[17], (String) obj1[18], (String) obj1[19],
						(String) obj1[16]);
				list.add(tmpOBJ);
			}
		} else {
			return null;
		}
		return list;
	}

	public BRDIntegrationData(Timestamp dateTime, BigInteger beneficiaryId, BigInteger beneficiaryRegId,
			BigInteger bencallId, String receivedRoleName, String gender, BigInteger age, String martialStatus,
			String state, String district, String block, String village,
			/* String address, */ String pincode,
			String callGroupType, String callType,
			//String diseasesummary, String selectedDiagnosis, String categoryName,String subCategoryName,
			BigInteger prescriptionId, String drugName) {
		super();
	
		this.dateTime = dateTime;
		this.beneficiaryId = beneficiaryId;
		this.beneficiaryRegId = beneficiaryRegId;
		this.bencallId = bencallId;
		this.receivedRoleName = receivedRoleName;
		this.gender = gender;
		this.age = age;
		this.martialStatus = martialStatus;
		this.state = state;
		this.district = district;
		this.block = block;
		this.village = village;
		//this.address = address;
		this.pincode = pincode;
		this.callGroupType = callGroupType;
		this.callType = callType;
		//this.diseasesummary = diseasesummary;
		//this.selectedDiagnosis = selectedDiagnosis;
		//this.categoryName = categoryName;
		//this.subCategoryName = subCategoryName;
		this.prescriptionId = prescriptionId;
		this.drugName = drugName;
	}
	
	


}
