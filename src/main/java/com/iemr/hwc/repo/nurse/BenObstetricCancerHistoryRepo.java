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
package com.iemr.hwc.repo.nurse;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.hwc.data.nurse.BenObstetricCancerHistory;

@Repository
@RestResource(exported = false)
public interface BenObstetricCancerHistoryRepo extends CrudRepository<BenObstetricCancerHistory, Long> {

	@Transactional
	@Modifying
	@Query("update BenObstetricCancerHistory set providerServiceMapID=:providerServiceMapID, pregnancyStatus=:pregnancyStatus, "
			+ "isUrinePregTest=:isUrinePregTest, pregnant_No=:pregnant_No, noOfLivingChild=:noOfLivingChild, isAbortion=:isAbortion, "
			+ "isOralContraceptiveUsed=:isOralContraceptiveUsed, isHormoneReplacementTherapy=:isHormoneReplacementTherapy, "
			+ " menarche_Age=:menarche_Age, isMenstrualCycleRegular=:isMenstrualCycleRegular, menstrualCycleLength=:menstrualCycleLength, "
			+ " menstrualFlowDuration=:menstrualFlowDuration, menstrualFlowType=:menstrualFlowType, isDysmenorrhea=:isDysmenorrhea,"
			+ " isInterMenstrualBleeding=:isInterMenstrualBleeding, menopauseAge=:menopauseAge, isPostMenopauseBleeding=:isPostMenopauseBleeding,"
			+ " isFoulSmellingDischarge=:isFoulSmellingDischarge, modifiedBy=:modifiedBy, processed=:processed where "
			+ " beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public int updateBenObstetricCancerHistory(@Param("providerServiceMapID") Integer providerServiceMapID,
			@Param("pregnancyStatus") String pregnancyStatus, @Param("isUrinePregTest") Boolean isUrinePregTest,
			@Param("pregnant_No") String pregnant_No, @Param("noOfLivingChild") Integer noOfLivingChild,
			@Param("isAbortion") Boolean isAbortion, @Param("isOralContraceptiveUsed") Boolean isOralContraceptiveUsed,
			@Param("isHormoneReplacementTherapy") Boolean isHormoneReplacementTherapy,
			@Param("menarche_Age") Integer menarche_Age,
			@Param("isMenstrualCycleRegular") Boolean isMenstrualCycleRegular,
			@Param("menstrualCycleLength") Integer menstrualCycleLength,
			@Param("menstrualFlowDuration") Integer menstrualFlowDuration,
			@Param("menstrualFlowType") String menstrualFlowType, @Param("isDysmenorrhea") Boolean isDysmenorrhea,
			@Param("isInterMenstrualBleeding") Boolean isInterMenstrualBleeding,
			@Param("menopauseAge") Integer menopauseAge,
			@Param("isPostMenopauseBleeding") Boolean isPostMenopauseBleeding,
			@Param("isFoulSmellingDischarge") Boolean isFoulSmellingDischarge, @Param("modifiedBy") String modifiedBy,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("processed") String processed);

	@Query("SELECT boh from BenObstetricCancerHistory boh WHERE boh.beneficiaryRegID = :benRegID AND boh.deleted = false "
			+ "AND boh.visitCode = :visitCode")
	public BenObstetricCancerHistory getBenObstetricCancerHistory(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	// @Query("SELECT boh from BenObstetricCancerHistory boh WHERE
	// boh.beneficiaryRegID = :benRegID AND boh.benVisitID = :benVisitID "
	// + "AND DATE(boh.createdDate) = :createdDate")
	// public BenObstetricCancerHistory
	// getBenObstetricCancerHistory(@Param("benRegID") Long benRegID,
	// @Param("benVisitID") Long benVisitID, @Param("createdDate") Date
	// createdDate);

	@Query(" SELECT  pregnancyStatus, isUrinePregTest, pregnant_No, noOfLivingChild, isAbortion, isOralContraceptiveUsed, "
			+ " isHormoneReplacementTherapy, menarche_Age, isMenstrualCycleRegular, menstrualCycleLength, "
			+ " menstrualFlowDuration, menstrualFlowType, isDysmenorrhea, isInterMenstrualBleeding, menopauseAge, "
			+ " isPostMenopauseBleeding, isFoulSmellingDischarge, Date(createdDate)  "
			+ " from BenObstetricCancerHistory " + " WHERE beneficiaryRegID = :benRegID "
			+ " and (pregnancyStatus is not null or isUrinePregTest is not null or pregnant_No is not null or "
			+ " noOfLivingChild is not null or isAbortion is not null or isOralContraceptiveUsed is not null or "
			+ " isHormoneReplacementTherapy is not null or menarche_Age is not null or "
			+ " isMenstrualCycleRegular is not null or menstrualCycleLength is not null or "
			+ " menstrualFlowDuration is not null or menstrualFlowType is not null or "
			+ " isDysmenorrhea is not null or isInterMenstrualBleeding is not null or "
			+ " menopauseAge is not null or isPostMenopauseBleeding is not null or "
			+ " isFoulSmellingDischarge is not null) order by createdDate desc")
	public ArrayList<Object[]> getBenObstetricCancerHistoryData(@Param("benRegID") Long benRegID);

	@Query("SELECT processed from BenObstetricCancerHistory where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getObstetricCancerHistoryStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
}
