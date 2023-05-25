package com.iemr.mmu.service.pnc;

import java.text.ParseException;

import com.iemr.mmu.data.pnc.PNCCare;

public interface PNCNurseService {

	Long saveBenPncCareDetails(PNCCare pncCareOBJ) throws ParseException;

	String getPNCCareDetails(Long beneficiaryRegID, Long benVisitID);

	int updateBenPNCCareDetails(PNCCare pncCareDetailsOBJ) throws ParseException;

}
