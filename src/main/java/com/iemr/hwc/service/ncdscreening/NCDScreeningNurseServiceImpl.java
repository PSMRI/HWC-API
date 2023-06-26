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
package com.iemr.hwc.service.ncdscreening;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.hwc.data.ncdScreening.NCDScreening;
import com.iemr.hwc.repo.nurse.ncdscreening.NCDScreeningRepo;

@Service
public class NCDScreeningNurseServiceImpl implements NCDScreeningNurseService {

	private NCDScreeningRepo ncdScreeningRepo;

	@Autowired
	public void setNcdScreeningRepo(NCDScreeningRepo ncdScreeningRepo) {
		this.ncdScreeningRepo = ncdScreeningRepo;
	}

	@Override
	public Long saveNCDScreeningDetails(NCDScreening ncdScreening) {

		ncdScreening = getNCDScreening(ncdScreening);
		NCDScreening save = ncdScreeningRepo.save(ncdScreening);
		Long ncdScreeningID = null;
		if (null != save) {
			ncdScreeningID = save.getID();
		}

		return ncdScreeningID;
	}

	private NCDScreening getNCDScreening(NCDScreening ncdScreening) {
		ArrayList<Map<String, Object>> ncdScreeningConditionList = ncdScreening.getNcdScreeningConditionList();
		if (ncdScreeningConditionList != null && ncdScreeningConditionList.size() > 0) {
			StringBuilder screeningConditionID = new StringBuilder();
			StringBuilder screeningConditionName = new StringBuilder();

			for (int i = 0; i < ncdScreeningConditionList.size(); i++) {
				if (i == (ncdScreeningConditionList.size() - 1)) {
					screeningConditionID.append(ncdScreeningConditionList.get(i).get("ncdScreeningConditionID"));
					screeningConditionName.append(ncdScreeningConditionList.get(i).get("screeningCondition"));
				} else {
					screeningConditionID.append(ncdScreeningConditionList.get(i).get("ncdScreeningConditionID"))
							.append(",");
					screeningConditionName.append(ncdScreeningConditionList.get(i).get("screeningCondition"))
							.append(",");
				}
			}
			ncdScreening.setNcdScreeningConditionID(screeningConditionID.toString());
			ncdScreening.setScreeningCondition(screeningConditionName.toString());

		}
		return ncdScreening;
	}

	@Override
	public String getNCDScreeningDetails(Long beneficiaryRegID, Long visitCode) {
		NCDScreening ncdScreeningDetails = ncdScreeningRepo.getNCDScreeningDetails(beneficiaryRegID, visitCode);

		if (ncdScreeningDetails != null && ncdScreeningDetails.getNcdScreeningConditionID() != null
				&& ncdScreeningDetails.getScreeningCondition() != null) {
			ArrayList<Map<String, Object>> screeningConditionList = new ArrayList<>();
			Map<String, Object> screeningConditionMap = null;
			String[] idArr = ncdScreeningDetails.getNcdScreeningConditionID().split(",");
			String[] nameArr = ncdScreeningDetails.getScreeningCondition().split(",");
			if (idArr != null && idArr.length > 0 && nameArr != null && nameArr.length > 0
					&& idArr.length == nameArr.length) {
				for (int i = 0; i < idArr.length; i++) {
					screeningConditionMap = new HashMap<>();
					screeningConditionMap.put("ncdScreeningConditionID", idArr[i]);
					screeningConditionMap.put("screeningCondition", nameArr[i]);
					screeningConditionList.add(screeningConditionMap);
				}
			}

			ncdScreeningDetails.setNcdScreeningConditionList(screeningConditionList);

		}

		if (ncdScreeningDetails != null && ncdScreeningDetails.getNextScreeningDateDB() != null)
			ncdScreeningDetails.setNextScreeningDate(ncdScreeningDetails.getNextScreeningDateDB().toString());

		return new Gson().toJson(ncdScreeningDetails);
	}

	@Override
	public Integer updateNCDScreeningDetails(NCDScreening ncdScreening) {
		Integer r = 0;
		ncdScreening = getNCDScreening(ncdScreening);
		r = ncdScreeningRepo.updateNCDScreeningDetails(ncdScreening.getNcdScreeningConditionID(),
				ncdScreening.getScreeningCondition(), ncdScreening.getNcdScreeningReasonID(),
				ncdScreening.getReasonForScreening(), ncdScreening.getNextScreeningDateDB(),
				ncdScreening.getActionForScreenPositive(), ncdScreening.getIsScreeningComplete(),
				ncdScreening.getModifiedBy(), ncdScreening.getBeneficiaryRegID(), ncdScreening.getVisitCode(),
				ncdScreening.getIsBPPrescribed(), ncdScreening.getIsBloodGlucosePrescribed());

		return r;
	}
}
