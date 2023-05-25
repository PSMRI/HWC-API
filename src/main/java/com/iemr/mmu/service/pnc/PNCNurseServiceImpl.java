package com.iemr.mmu.service.pnc;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.mmu.data.pnc.PNCCare;
import com.iemr.mmu.repo.nurse.pnc.PNCCareRepo;

@Service
public class PNCNurseServiceImpl implements PNCNurseService {

	private PNCCareRepo pncCareRepo;

	@Autowired
	public void setPncCareRepo(PNCCareRepo pncCareRepo) {
		this.pncCareRepo = pncCareRepo;
	}

	@Override
	public Long saveBenPncCareDetails(PNCCare pncCareOBJ) throws ParseException {

		if (pncCareOBJ.getdDate() != null && !pncCareOBJ.getdDate().isEmpty() && pncCareOBJ.getdDate().length() >= 10) {
			String dDate = pncCareOBJ.getdDate().split("T")[0];
			pncCareOBJ.setDateOfDelivery(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dDate).getTime()));
		}

		PNCCare res = pncCareRepo.save(pncCareOBJ);
		if (null != res && res.getID() > 0) {
			return res.getID();
		} else {
			return null;
		}
	}

	@Override
	public String getPNCCareDetails(Long beneficiaryRegID, Long visitCode) {
		PNCCare pncCareDetails = pncCareRepo.getPNCCareDetailsByVisitCode(beneficiaryRegID, visitCode);
		// PNCCare pncCareDetails = PNCCare.getPNCCareDetails(resList);
		return new Gson().toJson(pncCareDetails);
	}

	@Override
	public int updateBenPNCCareDetails(PNCCare pncCareDetailsOBJ) throws ParseException {
		int res = 0;
		int recordsAvailable = 0;
		String processed = pncCareRepo.getBenPNCCareDetailsStatus(pncCareDetailsOBJ.getBeneficiaryRegID(),
				pncCareDetailsOBJ.getVisitCode());
		if (null != processed) {
			recordsAvailable = 1;
		}
		if (null != processed && !"N".equals(processed)) {
			processed = "U";
		} else {
			processed = "N";
		}
		pncCareDetailsOBJ.setProcessed(processed);
		if (recordsAvailable == 1) {
			res = updateBenPNCCare(pncCareDetailsOBJ);
		} else {
			pncCareDetailsOBJ.setCreatedBy(pncCareDetailsOBJ.getModifiedBy());
			Long successRes = saveBenPncCareDetails(pncCareDetailsOBJ);
			if (null != successRes && successRes > 0) {
				res = 1;
			}
		}
		return res;
	}

	public int updateBenPNCCare(PNCCare pncCareOBJ) throws ParseException {
		int r = 0;

		if (pncCareOBJ.getdDate() != null && !pncCareOBJ.getdDate().isEmpty() && pncCareOBJ.getdDate().length() >= 10) {
			String dDate = pncCareOBJ.getdDate().split("T")[0];
			pncCareOBJ.setDateOfDelivery(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dDate).getTime()));
		}
		r = pncCareRepo.updatePNCCareDetails(pncCareOBJ.getDeliveryTypeID(), pncCareOBJ.getDeliveryType(),
				pncCareOBJ.getDeliveryPlaceID(), pncCareOBJ.getDeliveryPlace(), pncCareOBJ.getOtherDeliveryPlace(),
				pncCareOBJ.getDateOfDelivery(), pncCareOBJ.getDeliveryComplicationID(),
				pncCareOBJ.getDeliveryComplication(), pncCareOBJ.getOtherDeliveryComplication(),
				pncCareOBJ.getPregOutcomeID(), pncCareOBJ.getPregOutcome(), pncCareOBJ.getPostNatalComplicationID(),
				pncCareOBJ.getPostNatalComplication(), pncCareOBJ.getOtherPostNatalComplication(),
				pncCareOBJ.getGestationID(), pncCareOBJ.getGestationName(), pncCareOBJ.getBirthWeightOfNewborn(),
				pncCareOBJ.getNewBornHealthStatusID(), pncCareOBJ.getNewBornHealthStatus(), pncCareOBJ.getModifiedBy(),
				pncCareOBJ.getProcessed(), pncCareOBJ.getBeneficiaryRegID(), pncCareOBJ.getVisitCode(),
				pncCareOBJ.getDeliveryConductedByID(), pncCareOBJ.getDeliveryConductedBy());
		return r;
	}
}
