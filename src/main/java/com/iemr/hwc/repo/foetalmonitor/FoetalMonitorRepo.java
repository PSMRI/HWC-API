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
package com.iemr.hwc.repo.foetalmonitor;

import java.util.ArrayList;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.foetalmonitor.FoetalMonitor;

@Repository
@RestResource(exported = false)
public interface FoetalMonitorRepo extends CrudRepository<FoetalMonitor, Long> {

	/***
	 * @author DU20091017
	 * @param foetalMonitorID
	 * @return
	 */
	@Query("SELECT f FROM FoetalMonitor f WHERE f.partnerFoetalMonitorId = :foetalMonitorID")
	public FoetalMonitor getFoetalMonitorDetails(@Param("foetalMonitorID") Long foetalMonitorID);

	/***
	 * @author DU20091017 get the feto sense details while lab flag update.
	 */
	@Query("SELECT f FROM FoetalMonitor f WHERE f.benFlowID = :benFlowID AND f.deleted is false ")
	public ArrayList<FoetalMonitor> getFoetalMonitorDetailsByFlowId(@Param("benFlowID") Long benFlowID);

	/***
	 * @author DU20091017 update visitCode
	 */
	@Modifying
	@Transactional
	@Query("UPDATE FoetalMonitor f SET f.visitCode = :visitCode WHERE f.benFlowID = :benFlowID")
	public int updateVisitCode(@Param("visitCode") Long visitCode, @Param("benFlowID") Long benFlowID);

	/***
	 * @author DU20091017 get the details for case record.
	 * @param beneficiaryRegID
	 * @param visitCode
	 * @return
	 */

	@Query("SELECT f FROM FoetalMonitor f WHERE f.beneficiaryRegID = :beneficiaryRegID "
			+ " AND f.visitCode = :visitCode AND f.deleted is false ")
	public ArrayList<FoetalMonitor> getFoetalMonitorDetailsForCaseRecord(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

	@Query(value = "SELECT beneficiaryid FROM db_identity.m_beneficiaryregidmapping where BenRegId= :benRegId ", nativeQuery = true)
	Long getBenID(@Param("benRegId") Long benRegId);

}
