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
package com.iemr.hwc.repo.doctor;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.hwc.data.doctor.CancerBreastExamination;

@Repository
@RestResource(exported = false)
public interface CancerBreastExaminationRepo extends CrudRepository<CancerBreastExamination, Long> {

	@Query(" SELECT c from CancerBreastExamination c WHERE c.beneficiaryRegID = :benRegID "
			+ " AND c.deleted = false AND c.visitCode = :visitCode")
	public CancerBreastExamination getBenCancerBreastExaminationDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Query("SELECT processed from CancerBreastExamination where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getCancerBreastExaminationStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Transactional
	@Modifying
	@Query("update CancerBreastExamination set providerServiceMapID=:providerServiceMapID, everBreastFed=:everBreastFed, "
			+ "breastFeedingDurationGTE6months=:breastFeedingDurationGTE6months, breastsAppear_Normal=:breastsAppear_Normal, "
			+ "rashOnBreast=:rashOnBreast, dimplingSkinOnBreast=:dimplingSkinOnBreast, dischargeFromNipple=:dischargeFromNipple,"
			+ "peaudOrange =:peaudOrange, lumpInBreast=:lumpInBreast, lumpSize=:lumpSize, lumpShape=:lumpShape,"
			+ "lumpTexture=:lumpTexture, referredToMammogram=:referredToMammogram, mamogramReport=:mamogramReport,"
			+ "modifiedBy=:modifiedBy, processed=:processed where "
			+ " beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public int updateCancerBreastExaminatio(@Param("providerServiceMapID") Integer providerServiceMapID,
			@Param("everBreastFed") Boolean everBreastFed, 
			@Param("breastFeedingDurationGTE6months") Boolean breastFeedingDurationGTE6months,
			@Param("breastsAppear_Normal") Boolean breastsAppear_Normal, 
			@Param("rashOnBreast") Boolean rashOnBreast,
			@Param("dimplingSkinOnBreast") Boolean dimplingSkinOnBreast, 
			@Param("dischargeFromNipple") Boolean dischargeFromNipple,
			@Param("peaudOrange") Boolean peaudOrange, 
			@Param("lumpInBreast") Boolean lumpInBreast,
			@Param("lumpSize") String lumpSize, 
			@Param("lumpShape") String lumpShape,
			@Param("lumpTexture") String lumpTexture,
			@Param("referredToMammogram") Boolean referredToMammogram, 
			@Param("mamogramReport") String mamogramReport,
			@Param("modifiedBy") String modifiedBy,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("processed") String processed);
}
