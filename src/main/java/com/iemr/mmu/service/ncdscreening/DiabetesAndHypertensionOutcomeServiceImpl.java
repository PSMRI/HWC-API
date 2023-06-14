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
package com.iemr.mmu.service.ncdscreening;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.mmu.data.ncdScreening.DiabetesOutcome;
import com.iemr.mmu.data.ncdScreening.HypertensionOutcome;
import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.mapper.InputMapper;
@Service
public class DiabetesAndHypertensionOutcomeServiceImpl implements DiabetesAndHypertensionOutcomeService {

	@Override
	public String getHypertensionOutcome(String request) throws IEMRException {
		String screeningResult = null;
		try {
		HypertensionOutcome hypertensionObj = InputMapper.gson().fromJson(request, HypertensionOutcome.class);
		if (hypertensionObj.getAverageSystolic() <= 120 && hypertensionObj.getAverageDiastolic() <= 80) {
			screeningResult = "Normal BP";
		} else if ((hypertensionObj.getAverageSystolic() > 120 && hypertensionObj.getAverageSystolic() <= 140)
				|| (hypertensionObj.getAverageDiastolic() > 80 && hypertensionObj.getAverageDiastolic() <= 90)) {
			screeningResult = "Pre-Hypertension";
		} else if ((hypertensionObj.getAverageSystolic() > 140 && hypertensionObj.getAverageSystolic() <= 160)
				|| (hypertensionObj.getAverageDiastolic() > 90 && hypertensionObj.getAverageDiastolic() <= 100)) {
			screeningResult = "Hypertension Stage-1";
		} else if (hypertensionObj.getAverageSystolic() > 160 || hypertensionObj.getAverageDiastolic() > 100) {
			screeningResult = "Hypertension Stage-2";
		}
		} catch (Exception e) {
			throw new IEMRException("Error while finding hypertension outcome :" + e.getLocalizedMessage());
		}
		HashMap<String, String> map = new HashMap<String, String>();
		if (screeningResult != null)
			map.put("status", screeningResult);
		else
			throw new IEMRException("Error while finding hypertension outcome");
		return new Gson().toJson(map);
	}

	@Override
	public String getDiabetesOutcome(String request) throws IEMRException {
		String screeningResult = null;
		try {
			DiabetesOutcome diabetesRequestObj = InputMapper.gson().fromJson(request, DiabetesOutcome.class);
			switch (diabetesRequestObj.getBloodGlucoseTypeID()) {
			case 3: {
				screeningResult = diabetesRequestObj.getBloodGlucose() <= 140 ? "Non-Diabetic Range"
						: ((diabetesRequestObj.getBloodGlucose() > 140 && diabetesRequestObj.getBloodGlucose() <= 200)
								? "Pre-Diabetic Range"
								: diabetesRequestObj.getBloodGlucose() > 200 ? "Diabetic Range" : "");
			}
				break;
			case 1: {
				screeningResult = diabetesRequestObj.getBloodGlucose() <= 100 ? "Non-Diabetic Range"
						: ((diabetesRequestObj.getBloodGlucose() > 100 && diabetesRequestObj.getBloodGlucose() <= 125)
								? "Pre-Diabetic Range"
								: diabetesRequestObj.getBloodGlucose() >= 126 ? "Diabetic Range" : "");
			}
				break;
			case 2: {
				screeningResult = diabetesRequestObj.getBloodGlucose() < 140 ? "Normal/Non-Diabetic Range"
						: ((diabetesRequestObj.getBloodGlucose() >= 140 && diabetesRequestObj.getBloodGlucose() <= 200)
								? "Pre-Diabetic Range"
								: diabetesRequestObj.getBloodGlucose() > 200 ? "Diabetic Range" : "");
			}
				break;
			default: {
				screeningResult = null;
			}
			}
		} catch (Exception e) {
			throw new IEMRException("Error while finding diabetes outcome :" + e.getLocalizedMessage());
		}
		HashMap<String, String> map = new HashMap<String, String>();
		if (screeningResult != null)
			map.put("status", screeningResult);
		else
			throw new IEMRException("Error while finding diabetes outcome");
		return new Gson().toJson(map);
	}

}
