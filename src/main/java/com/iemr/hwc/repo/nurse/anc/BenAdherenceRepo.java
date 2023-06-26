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
package com.iemr.hwc.repo.nurse.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.hwc.data.anc.BenAdherence;

@Repository
@RestResource(exported = false)
public interface BenAdherenceRepo extends CrudRepository<BenAdherence, Long> {

	@Query(" SELECT ID, beneficiaryRegID, benVisitID, providerServiceMapID, visitCode, toDrugs, drugReason, toReferral, referralReason, progress "
			+ "from BenAdherence ba WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode= :visitCode")
	public ArrayList<Object[]> getBenAdherence(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Query(" SELECT processed from BenAdherence where beneficiaryRegID=:benRegID AND benVisitID = :benVisitID AND ID=:ID")
	public String getBenAdherenceDetailsStatus(@Param("benRegID") Long benRegID,
			@Param("benVisitID") Long benVisitID,
			@Param("ID") Long ID);
	
	
	@Transactional
	@Modifying
	@Query("update BenAdherence set toDrugs=:toDrugs, drugReason=:drugReason, toReferral=:toReferral, referralReason=:referralReason,"
			+ " progress=:progress,  modifiedBy=:modifiedBy, processed=:processed where beneficiaryRegID=:benRegID AND benVisitID = :benVisitID AND ID=:ID")
	public int updateBenAdherence(@Param("toDrugs") Boolean toDrugs,
			@Param("drugReason") String drugReason,
			@Param("toReferral") Boolean toReferral,
			@Param("referralReason") String referralReason,
			@Param("progress") String progress,
			@Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed,
			@Param("benRegID") Long benRegID,
			@Param("benVisitID") Long benVisitID,
			@Param("ID") Long ID);
	
}
