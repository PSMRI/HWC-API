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
package com.iemr.mmu.repo.uptsu;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.login.MasterVan;
import com.iemr.mmu.data.uptsu.AppointmentDetails;
import com.iemr.mmu.data.uptsu.Referred104Details;

@Repository
@RestResource(exported = false)
public interface UptsuRepository  extends CrudRepository<Referred104Details, Integer>{
	
	@Transactional
	@Modifying
	@Query("UPDATE AppointmentDetails t SET t.refferedFlag = true WHERE t.benCallId = :benCallID")
	void updateReferredFlag(@Param("benCallID") String benCallID);

	
	@Query("Select facilityID from MasterVan WHERE vanID = :vanID")
	public Integer getFacilityId(@Param("vanID") Integer vanID);

	
	@Query("Select facilityCode from M_Facility WHERE facilityID = :facilityID")
	String getfacilityCode(@Param("facilityID")Integer facilityID);


	@Query(" SELECT c from AppointmentDetails c WHERE c.facilityCode = :facilityCode AND c.createdDate >= :timestamp AND c.appointmentDate <= :currentTime")
	List<AppointmentDetails> getAppointmentDetails(@Param("facilityCode")String facilityCode,@Param("timestamp")Timestamp timestamp,@Param("currentTime")Timestamp currentTime);

	@Query(value = "call Pr_BeneficiaryDetails(:benRegId)", nativeQuery = true)
	ArrayList<Object[]> findBeneficiaryNameAndBeneficiaryIdByBenRegId(@Param("benRegId") String benRegId);

}
