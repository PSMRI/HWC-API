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
package com.iemr.hwc.repo.registrar;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.registrar.BeneficiaryData;

@Repository
@RestResource(exported = false)
public interface RegistrarRepoBenData extends CrudRepository<BeneficiaryData, Long> {
	// @Query(" SELECT bd.beneficiaryRegID, bd.beneficiaryID, "
	// + " concat(IFNULL(bd.firstName, ''), ' ', IFNULL(bd.middleName, ''), ' ',
	// IFNULL(bd.lastName,'')) as benName, "
	// + " bd.dob, bd.genderID, bd.fatherName, bdd.servicePointID,
	// bdd.villageID, bpm.phoneNo "
	// + " FROM BeneficiaryData bd INNER JOIN bd.benDemoData bdd INNER JOIN
	// bd.benPhoneMap bpm "
	// + " WHERE bdd.servicePointID = :spID ")
	// public List<Object[]> getRegistrarWorkList(@Param("spID") int spID);

	@Query(" SELECT bd.beneficiaryRegID, bd.beneficiaryID, "
			+ " UPPER( concat(IFNULL(bd.firstName, ''), ' ', IFNULL(bd.middleName, ''), ' ',IFNULL(bd.lastName,''))) as benName, "
			+ " bd.dob, bd.genderID,UPPER(bd.fatherName), bdd.servicePointID, bdd.districtBranchID, bpm.phoneNo "
			+ " FROM BeneficiaryData bd INNER JOIN  bd.benDemoData bdd INNER JOIN bd.benPhoneMap bpm "
			+ " WHERE bdd.servicePointID = :spID  ")
	public List<Object[]> getRegistrarWorkList(@Param("spID") int spID);

	@Query("  SELECT bd.beneficiaryRegID, bd.beneficiaryID, "
			+ " UPPER(concat(IFNULL(bd.firstName, ''), ' ', IFNULL(bd.middleName, ''), ' ',IFNULL(bd.lastName,''))) as benName, "
			+ " bd.dob, bd.genderID,UPPER(bd.fatherName), bdd.servicePointID, bdd.districtBranchID, bpm.phoneNo "
			+ " FROM BeneficiaryData bd INNER JOIN  bd.benDemoData bdd INNER JOIN bd.benPhoneMap bpm "
			+ " WHERE bd.beneficiaryID = :benID ")
	public List<Object[]> getQuickSearch(@Param("benID") String benID);

	@Query("SELECT i.beneficiaryRegID, i.beneficiaryID,"
			+ "concat(IFNULL(i.firstName, ''), ' ', IFNULL(i.middleName, ''), ' ',IFNULL(i.lastName,'')) as benName, "
			+ "  Date(i.dob), i.genderID, i.createdDate"
			+ " from BeneficiaryData i WHERE i.beneficiaryRegID =:beneficiaryRegID")
	public List<Object[]> getBenDetailsByRegID(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryData set flowStatusFlag = :flowStatusFlag, lastModDate = curdate() where beneficiaryRegID = :benRegID ")
	public Integer updateBenFlowStatus(@Param("flowStatusFlag") Character flowStatusFlag,
			@Param("benRegID") Long benRegID);

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryData set firstName = :firstName,lastName = :lastName,genderID = :genderID,dob = :dob,maritalStatusID = :maritalStatusID,"
			+ " fatherName = :fatherName,spouseName = :spouseName,aadharNo = :aadharNo,modifiedBy = :modifiedBy where beneficiaryRegID = :beneficiaryRegID ")
	public Integer updateBeneficiaryData(@Param("firstName") String firstName, @Param("lastName") String lastName,
			@Param("genderID") Short genderID, @Param("dob") Timestamp dob,
			@Param("maritalStatusID") Short maritalStatusID, @Param("fatherName") String fatherName,
			@Param("spouseName") String spouseName, @Param("aadharNo") String aadharNo,
			@Param("modifiedBy") String modifiedBy, @Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query(" SELECT firstName, lastName, Date(dob), genderID FROM BeneficiaryData WHERE beneficiaryRegID = :benRegID ")
	public ArrayList<Object[]> getBenDataForCareStream(@Param("benRegID") Long benRegID);
}
