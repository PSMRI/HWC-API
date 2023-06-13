/* LicenseInfo : Copyright © 2023 Piramal */ 
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
