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
package com.iemr.mmu.repo.labModule;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.labModule.ProcedureData;

@Repository
@RestResource(exported = false)
public interface ProcedureRepo extends CrudRepository<ProcedureData, Integer> {
	/*
	 * @Query("select procedureID, procedureName, procedureDesc, gender, providerServiceMapID from ProcedureData"
	 * +
	 * " where procedureType =:procedureType and deleted = false order by procedureName"
	 * ) public ArrayList<Object[]> getProcedures(@Param("procedureType") String
	 * procedureType);
	 */

	@Query("select procedureID, procedureName, procedureDesc, procedureType, gender, providerServiceMapID from ProcedureData"
			+ " where deleted = false and providerServiceMapID = :providerServiceMapID  and (gender=:gender or gender='Unisex') order by procedureName")
	public ArrayList<Object[]> getProcedureMasterData(@Param("providerServiceMapID") Integer providerServiceMapID,
			@Param("gender") String gender);

}
