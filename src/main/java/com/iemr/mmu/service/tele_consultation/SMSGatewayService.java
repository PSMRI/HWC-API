package com.iemr.mmu.service.tele_consultation;

import java.util.List;

import com.iemr.mmu.data.quickConsultation.PrescribedDrugDetail;

public interface SMSGatewayService {
	public int smsSenderGateway(String smsType, Long benRegID, Integer specializationID, Long tMRequestID,
			Long tMRequestCancelID, String createdBy, String tcDate, String tcPreviousDate, String Authorization);

	public String createSMSRequest(String smsType, Long benRegID, Integer specializationID, Long tMRequestID,
			Long tMRequestCancelID, String createdBy, String tcDate, String tcPreviousDate);

	public String sendSMS(String request, String Authorization);
	
	public int smsSenderGateway2(String smsType, List<PrescribedDrugDetail> object, String Authorization,Long benregID,String createdBy
			,List<Object> diagnosis);
}
