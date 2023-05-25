package com.iemr.mmu.data.anc;

import java.util.ArrayList;

import com.iemr.mmu.annotation.sqlInjectionSafe.SQLInjectionSafe;

public class WrapperFemaleObstetricHistory {

	private Long beneficiaryRegID;
	private Long benVisitID;
	private Long visitCode;
	private Integer providerServiceMapID;
	private Integer vanID;
	private Integer parkingPlaceID;
	private @SQLInjectionSafe String createdBy;
	private Short totalNoOfPreg;

	private ArrayList<FemaleObstetricHistory> femaleObstetricHistoryList;

	public Integer getVanID() {
		return vanID;
	}

	public void setVanID(Integer vanID) {
		this.vanID = vanID;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public ArrayList<FemaleObstetricHistory> getFemaleObstetricHistoryList() {
		return femaleObstetricHistoryList;
	}

	public void setFemaleObstetricHistoryList(ArrayList<FemaleObstetricHistory> femaleObstetricHistoryList) {
		this.femaleObstetricHistoryList = femaleObstetricHistoryList;
	}

	public ArrayList<FemaleObstetricHistory> getFemaleObstetricHistoryDetails() {
		if (femaleObstetricHistoryList != null && femaleObstetricHistoryList.size() > 0) {
			for (FemaleObstetricHistory femaleObstetricHistory : femaleObstetricHistoryList) {

				femaleObstetricHistory.setBeneficiaryRegID(beneficiaryRegID);
				femaleObstetricHistory.setBenVisitID(benVisitID);
				femaleObstetricHistory.setVisitCode(visitCode);
				femaleObstetricHistory.setProviderServiceMapID(providerServiceMapID);
				femaleObstetricHistory.setVanID(vanID);
				femaleObstetricHistory.setParkingPlaceID(parkingPlaceID);
				femaleObstetricHistory.setCreatedBy(createdBy);
				femaleObstetricHistory.setTotalNoOfPreg(totalNoOfPreg);

			}
		} else {
			if (totalNoOfPreg != null) {
				FemaleObstetricHistory tmpOBJ = new FemaleObstetricHistory();

				tmpOBJ.setBeneficiaryRegID(beneficiaryRegID);
				tmpOBJ.setBenVisitID(benVisitID);
				tmpOBJ.setVisitCode(visitCode);
				tmpOBJ.setProviderServiceMapID(providerServiceMapID);
				tmpOBJ.setVanID(vanID);
				tmpOBJ.setParkingPlaceID(parkingPlaceID);
				tmpOBJ.setCreatedBy(createdBy);
				tmpOBJ.setTotalNoOfPreg(totalNoOfPreg);
				femaleObstetricHistoryList = new ArrayList<FemaleObstetricHistory>();
				femaleObstetricHistoryList.add(tmpOBJ);
			}
		}
		return femaleObstetricHistoryList;
	}

	public static WrapperFemaleObstetricHistory getFemaleObstetricHistory(ArrayList<Object[]> FemaleObstetricHistory) {
		WrapperFemaleObstetricHistory WFO = new WrapperFemaleObstetricHistory();
		WFO.femaleObstetricHistoryList = new ArrayList<FemaleObstetricHistory>();

		if (null != FemaleObstetricHistory && FemaleObstetricHistory.size() > 0) {
			Object[] obj1 = FemaleObstetricHistory.get(0);
			WFO.beneficiaryRegID = (Long) obj1[0];
			WFO.benVisitID = (Long) obj1[1];
			WFO.providerServiceMapID = (Integer) obj1[2];
			WFO.totalNoOfPreg = (Short) obj1[4];
			if (null != WFO.totalNoOfPreg && WFO.totalNoOfPreg > 0) {
				for (Object[] obj : FemaleObstetricHistory) {

					FemaleObstetricHistory obstetricHistory = new FemaleObstetricHistory((Short) obj[3],
							(String) obj[5], (String) obj[6], (String) obj[7], (Short) obj[8], (String) obj[9],
							(Short) obj[10], (String) obj[11], (Short) obj[12], (String) obj[13], (String) obj[14],
							(String) obj[15], (String) obj[16], (String) obj[17], (Short) obj[18], (String) obj[19],
							(String) obj[20], (String) obj[21], (String) obj[22], (Short) obj[23], (String) obj[24],
							(String) obj[25], (String) obj[26], (Short) obj[27], (String) obj[28], (String) obj[29],
							(Long) obj[30], (Integer) obj[31], (String) obj[32], (Integer) obj[33], (Integer) obj[34],
							(String) obj[35], (String) obj[36], (String) obj[37]);

					WFO.femaleObstetricHistoryList.add(obstetricHistory);

				}
			}
		}
		return WFO;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Long getBenVisitID() {
		return benVisitID;
	}

	public void setBenVisitID(Long benVisitID) {
		this.benVisitID = benVisitID;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Short getTotalNoOfPreg() {
		return totalNoOfPreg;
	}

	public void setTotalNoOfPreg(Short totalNoOfPreg) {
		this.totalNoOfPreg = totalNoOfPreg;
	}

}
