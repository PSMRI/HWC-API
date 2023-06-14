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
