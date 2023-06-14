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
package com.iemr.mmu.repo.nurse.pnc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.pnc.PNCDiagnosis;

@Repository
@RestResource(exported = false)
public interface PNCDiagnosisRepo extends CrudRepository<PNCDiagnosis, Long> {

	@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID, prescriptionID, provisionalDiagnosis, "
			+ "confirmatoryDiagnosis, isMaternalDeath, placeOfDeath, dateOfDeath, causeOfDeath, visitCode "
			+ " from PNCDiagnosis ba "
			+ "WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode AND ba.deleted = false "
			+ " ORDER BY createdDate Desc ")
	public ArrayList<Object[]> getPNCDiagnosisDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	ArrayList<PNCDiagnosis> findByBeneficiaryRegIDAndVisitCode(Long benRegID, Long visitCode);

	@Query("SELECT processed from PNCDiagnosis where beneficiaryRegID=:benRegID AND visitCode = :visitCode "
			+ " AND prescriptionID =:prescriptionID ")
	public String getPNCDiagnosisStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("prescriptionID") Long prescriptionID);

	@Transactional
	@Modifying
	@Query("update PNCDiagnosis set provisionalDiagnosis=:provisionalDiagnosis, "
			+ " confirmatoryDiagnosis=:confirmatoryDiagnosis, "
			+ " isMaternalDeath=:isMaternalDeath, placeOfDeath=:placeOfDeath, "
			+ " dateOfDeath=:dateOfDeath, causeOfDeath=:causeOfDeath, modifiedBy=:modifiedBy, "
			+ " processed=:processed, provisionalDiagnosisSCTCode =:provisionalDiagnosisSCTCode, "
			+ " provisionalDiagnosisSCTTerm =:provisionalDiagnosisSCTTerm, "
			+ " confirmatoryDiagnosisSCTCode =:confirmatoryDiagnosisSCTCode, "
			+ " confirmatoryDiagnosisSCTTerm =:confirmatoryDiagnosisSCTTerm "
			+ " where visitCode=:visitCode AND beneficiaryRegID=:beneficiaryRegID "
			+ " AND prescriptionID =:prescriptionID ")
	public int updatePNCDiagnosis(@Param("provisionalDiagnosis") String provisionalDiagnosis,
			@Param("confirmatoryDiagnosis") String confirmatoryDiagnosis,
			@Param("isMaternalDeath") Boolean isMaternalDeath, @Param("placeOfDeath") String placeOfDeath,
			@Param("dateOfDeath") Date dateOfDeath, @Param("causeOfDeath") String causeOfDeath,
			@Param("modifiedBy") String modifiedBy, @Param("processed") String processed,
			@Param("beneficiaryRegID") Long beneficiaryRegID, @Param("visitCode") Long visitCode,
			@Param("provisionalDiagnosisSCTCode") String provisionalDiagnosisSCTCode,
			@Param("provisionalDiagnosisSCTTerm") String provisionalDiagnosisSCTTerm,
			@Param("confirmatoryDiagnosisSCTCode") String confirmatoryDiagnosisSCTCode,
			@Param("confirmatoryDiagnosisSCTTerm") String confirmatoryDiagnosisSCTTerm,
			@Param("prescriptionID") Long prescriptionID);
	//shubham 13-10-2020,TM Prescription SMS
	@Query("SELECT processed from PNCDiagnosis where visitCode = :visitCode "
			+ " AND prescriptionID =:prescriptionID AND (deleted <> true OR deleted IS NULL)")
	public  List<Object>  getProvisionalDiagnosis(@Param("visitCode") Long visitCode,
			@Param("prescriptionID") Long prescriptionID);

}
