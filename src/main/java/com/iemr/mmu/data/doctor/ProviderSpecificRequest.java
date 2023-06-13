/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.doctor;

public class ProviderSpecificRequest {
	Long visitCode;
	String benVisitID;
	String fetchMMUDataFor;
	Long benRegID;
	public Long getVisitCode() {
		return visitCode;
	}
	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}
	public String getBenVisitID() {
		return benVisitID;
	}
	public void setBenVisitID(String benVisitID) {
		this.benVisitID = benVisitID;
	}
	public String getFetchMMUDataFor() {
		return fetchMMUDataFor;
	}
	public void setFetchMMUDataFor(String fetchMMUDataFor) {
		this.fetchMMUDataFor = fetchMMUDataFor;
	}
	public Long getBenRegID() {
		return benRegID;
	}
	public void setBenRegID(Long benRegID) {
		this.benRegID = benRegID;
	}
	

}
