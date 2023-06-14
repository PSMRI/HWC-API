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
package com.iemr.mmu.service.cancerScreening;

import java.util.List;

import com.iemr.mmu.data.nurse.BenCancerVitalDetail;
import com.iemr.mmu.data.nurse.BenFamilyCancerHistory;
import com.iemr.mmu.data.nurse.BenObstetricCancerHistory;
import com.iemr.mmu.data.nurse.BenPersonalCancerDietHistory;
import com.iemr.mmu.data.nurse.BenPersonalCancerHistory;

public interface CSNurseService {

	int saveBenFamilyCancerHistory(List<BenFamilyCancerHistory> benFamilyCancerHistoryList);

	Long saveBenPersonalCancerHistory(BenPersonalCancerHistory benPersonalCancerHistory);

	Long saveBenPersonalCancerDietHistory(BenPersonalCancerDietHistory benPersonalCancerDietHistory);

	Long saveBenObstetricCancerHistory(BenObstetricCancerHistory benObstetricCancerHistory);

	Long saveBenVitalDetail(BenCancerVitalDetail benCancerVitalDetail);

	int updateBeneficiaryFamilyCancerHistory(List<BenFamilyCancerHistory> benFamilyCancerHistoryList);

	int updateBenObstetricCancerHistory(BenObstetricCancerHistory benObstetricCancerHistory);

	int updateBenPersonalCancerHistory(BenPersonalCancerHistory benPersonalCancerHistory);

	int updateBenPersonalCancerDietHistory(BenPersonalCancerDietHistory benPersonalCancerDietHistory);

	int updateBenVitalDetail(BenCancerVitalDetail benCancerVitalDetail);

	List<BenFamilyCancerHistory> getBenFamilyHisData(Long benRegID, Long visitCode);

	BenObstetricCancerHistory getBenObstetricDetailsData(Long benRegID, Long visitCode);

	BenPersonalCancerDietHistory getBenPersonalCancerDietHistoryData(Long benRegID, Long visitCode);

	BenCancerVitalDetail getBenCancerVitalDetailData(Long benRegID, Long visitCode);

	BenPersonalCancerHistory getBenPersonalCancerHistoryData(Long benRegID, Long visitCode);


}
