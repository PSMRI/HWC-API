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

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.hwc.data.doctor.CancerLymphNodeDetails;

@Repository
@RestResource(exported = false)
public interface CancerLymphNodeExaminationRepo extends CrudRepository<CancerLymphNodeDetails, Long> {

	@Query(" SELECT c from CancerLymphNodeDetails c WHERE c.beneficiaryRegID = :benRegID "
			+ "AND c.deleted = false AND c.visitCode = :visitCode")
	public List<CancerLymphNodeDetails> getBenCancerLymphNodeDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT ID, processed from CancerLymphNodeDetails where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted = false")
	public ArrayList<Object[]> getCancerLymphNodeDetailsStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT ID, processed from CancerLymphNodeDetails where beneficiaryRegID=:benRegID "
			+ " AND visitCode = :visitCode AND deleted = false AND lymphNodeName IN (:nameList)")
	public ArrayList<Object[]> getCancerLymphNodeDetailsStatusForLymphnodeNameList(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode, @Param("nameList") List<String> nameList);

	@Modifying
	@Transactional
	@Query("update CancerLymphNodeDetails set deleted=true, processed=:processed WHERE ID = :ID")
	public int deleteExistingLymphNodeDetails(@Param("ID") Long ID, @Param("processed") String processed);

	/*
	 * @Transactional
	 * 
	 * @Modifying
	 * 
	 * @Query("update CancerLymphNodeDetails set providerServiceMapID=:providerServiceMapID, lymphNodeName=:lymphNodeName, "
	 * +
	 * "mobility_Left=:mobility_Left, size_Left=:size_Left, mobility_Right=:mobility_Right, "
	 * +
	 * "size_Right=:size_Right, modifiedBy=:modifiedBy, processed=:processed where "
	 * + " beneficiaryRegID=:benRegID AND benVisitID = :benVisitID") public int
	 * updateCancerLymphNodeDetails(@Param("providerServiceMapID") Integer
	 * providerServiceMapID,
	 * 
	 * @Param("lymphNodeName") String lymphNodeName,
	 * 
	 * @Param("mobility_Left") Boolean mobility_Left,
	 * 
	 * @Param("size_Left") String size_Left,
	 * 
	 * @Param("mobility_Right") Boolean mobility_Right,
	 * 
	 * @Param("size_Right") String size_Right,
	 * 
	 * @Param("modifiedBy") String modifiedBy,
	 * 
	 * @Param("benRegID") Long benRegID, @Param("benVisitID") Long benVisitID,
	 * 
	 * @Param("processed") String processed);
	 */
}
