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
package com.iemr.hwc.repo.snomedct;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.snomedct.SCTDescription;

@Repository
@RestResource(exported = false)
public interface SnomedRepository extends JpaRepository<SCTDescription, Long> {

	/* @Query("select u from SCTDescription u where u.term like %:term%") */
	@Query("SELECT s.conceptID,s.term, s.caseSignificanceID "
			+ " FROM SCTDescription s WHERE s.term =:term and s.active = '1'")
	public List<Object[]> findSnomedCTRecordFromTerm(@Param("term") String term);

	@Query("SELECT DISTINCT s FROM SCTDescription s WHERE s.term LIKE :term% AND s.active = '1' ")
	public Page<SCTDescription> findSnomedCTRecordList(@Param("term") String term, Pageable pageable);

}
