/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.report;

import java.sql.Timestamp;

import javax.persistence.Transient;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class ConsultationReport {

	@Expose
	private String beneficiaryRegID;
	@Expose
	private String visitCode;
	@Expose
	private String beneficiaryName;
	
	@Expose
	private String specialistName;
	
	@Expose
	private String specialistId;
		
	@Expose
	private String specialization;
	
	@Expose
	private String specializationID;
	
//	@Expose
//	private Timestamp scheduledTime;
	
	@Expose
	private Timestamp requestedDate;
		
	@Expose
	private String consulted;
	
//	@Expose
//	private Timestamp arrivalTime;
	
	@Expose
	private Timestamp beneficiaryArrivalTime;
	
//	@Expose
//	private Timestamp consultedTime;
//	
//	@Expose
//	private String waitingTime;
	
	@Expose
	private Timestamp consultationFirstStart;
	
	@Expose
	private Timestamp specialistConsultationStart;
	
	@Expose
	private Timestamp consultationEnd 
;
	
	@Expose
	private String tATForArrivalToConsultationStart
;
	
	@Expose
	private String tATForSpecialistConsultationStartToEnd
;
	
	

	@Transient
	private static GsonBuilder builder;

	@Override
	public String toString() {
		if (builder == null) {
			builder = new GsonBuilder();
			builder.setDateFormat("dd-MM-yyyy h:mm a ");
			builder.excludeFieldsWithoutExposeAnnotation();
			builder.serializeNulls();
		}
		return builder.create().toJson(this);
	}
	
	

	public static GsonBuilder getBuilder() {
		return builder;
	}



	public static void setBuilder(GsonBuilder builder) {
		ConsultationReport.builder = builder;
	}



	public String getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(String beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getSpecialistName() {
		return specialistName;
	}

	public void setSpecialistName(String specialistName) {
		this.specialistName = specialistName;
	}

//	public Timestamp getScheduledTime() {
//		return scheduledTime;
//	}
//
//	public void setScheduledTime(Timestamp scheduledTime) {
//		this.scheduledTime = scheduledTime;
//	}

	public String getConsulted() {
		return consulted;
	}

	public void setConsulted(String consulted) {
		this.consulted = consulted;
	}

//	public Timestamp getArrivalTime() {
//		return arrivalTime;
//	}
//
//	public void setArrivalTime(Timestamp arrivalTime) {
//		this.arrivalTime = arrivalTime;
//	}

//	public Timestamp getConsultedTime() {
//		return consultedTime;
//	}
//
//	public void setConsultedTime(Timestamp consultedTime) {
//		this.consultedTime = consultedTime;
//	}
//
//	public String getWaitingTime() {
//		return waitingTime;
//	}
//
//	public void setWaitingTime(String waitingTime) {
//		this.waitingTime = waitingTime;
//	}



	



	public String getSpecialistId() {
		return specialistId;
	}



	public void setSpecialistId(String specialistId) {
		this.specialistId = specialistId;
	}



	public String getSpecialization() {
		return specialization;
	}



	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}



	public String getSpecializationID() {
		return specializationID;
	}



	public void setSpecializationID(String specializationID) {
		this.specializationID = specializationID;
	}



	public Timestamp getConsultationFirstStart() {
		return consultationFirstStart;
	}



	public void setConsultationFirstStart(Timestamp consultationFirstStart) {
		this.consultationFirstStart = consultationFirstStart;
	}



	public Timestamp getRequestedDate() {
		return requestedDate;
	}



	public void setRequestedDate(Timestamp requestedDate) {
		this.requestedDate = requestedDate;
	}



	public Timestamp getBeneficiaryArrivalTime() {
		return beneficiaryArrivalTime;
	}



	public void setBeneficiaryArrivalTime(Timestamp beneficiaryArrivalTime) {
		this.beneficiaryArrivalTime = beneficiaryArrivalTime;
	}



	public Timestamp getSpecialistConsultationStart() {
		return specialistConsultationStart;
	}



	public void setSpecialistConsultationStart(Timestamp specialistConsultationStart) {
		this.specialistConsultationStart = specialistConsultationStart;
	}



	public Timestamp getConsultationEnd() {
		return consultationEnd;
	}



	public void setConsultationEnd(Timestamp consultationEnd) {
		this.consultationEnd = consultationEnd;
	}



	public String gettATForArrivalToConsultationStart() {
		return tATForArrivalToConsultationStart;
	}



	public void settATForArrivalToConsultationStart(String tATForArrivalToConsultationStart) {
		this.tATForArrivalToConsultationStart = tATForArrivalToConsultationStart;
	}



	public String gettATForSpecialistConsultationStartToEnd() {
		return tATForSpecialistConsultationStartToEnd;
	}



	public void settATForSpecialistConsultationStartToEnd(String tATForSpecialistConsultationStartToEnd) {
		this.tATForSpecialistConsultationStartToEnd = tATForSpecialistConsultationStartToEnd;
	}



	public String getVisitCode() {
		return visitCode;
	}



	public void setVisitCode(String visitCode) {
		this.visitCode = visitCode;
	}


	
	
	
}
