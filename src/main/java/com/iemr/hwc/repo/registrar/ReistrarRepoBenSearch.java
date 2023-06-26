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

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.registrar.V_BenAdvanceSearch;

@Repository
@RestResource(exported = false)
public interface ReistrarRepoBenSearch extends CrudRepository<V_BenAdvanceSearch, Long> {

/*	Search with BeneficiaryID replaced with beneficiaryRegID as of now **
 * @Query("SELECT DISTINCT(beneficiaryRegID), beneficiaryID, "
			+ " UPPER( concat(IFNULL(firstName, ''), ' ',IFNULL(lastName,''))) as benName, "
			+ " Date(dob), genderID, genderName, UPPER(fatherName) as fatherName, "
			+ " districtID, districtName, districtBranchID, villageName, phoneNo " + " FROM  V_BenAdvanceSearch "
			+ " WHERE (beneficiaryID IS NULL OR beneficiaryID like :beneficiaryID ) AND"
			+ " (firstName like %:firstName% ) AND"
			+ " (Isnull(lastName) LIKE %:lastName%  OR lastName like %:lastName% ) AND"
			+ " (Isnull(phoneNo) LIKE :phoneNo OR phoneNo like :phoneNo ) AND"
			+ " (Isnull(aadharNo) LIKE :aadharNo OR aadharNo like :aadharNo ) AND"
			+ " (Isnull(govtIdentityNo) LIKE :govtIdentityNo OR govtIdentityNo like :govtIdentityNo ) AND"
			+ " (Isnull(cast(stateID as string)) LIKE :stateID OR cast(stateID as string) like :stateID) AND"
			+ " (Isnull(cast(districtID as string)) LIKE :districtID OR cast(districtID as string) like :districtID)")*/
	
	@Query("SELECT DISTINCT(beneficiaryRegID), beneficiaryID, "
			+ " UPPER( concat(IFNULL(firstName, ''), ' ',IFNULL(lastName,''))) as benName, "
			+ " Date(dob), genderID, genderName, UPPER(fatherName) as fatherName, "
			+ " districtID, districtName, districtBranchID, villageName, phoneNo " + " FROM  V_BenAdvanceSearch "
			+ " WHERE (Isnull(cast(beneficiaryRegID as string)) LIKE :beneficiaryRegID OR cast(beneficiaryRegID as string) like :beneficiaryRegID) AND"
			+ " (firstName like %:firstName% ) AND"
			+ " (Isnull(lastName) LIKE %:lastName%  OR lastName like %:lastName% ) AND"
			+ " (Isnull(phoneNo) LIKE :phoneNo OR phoneNo like :phoneNo ) AND"
			+ " (Isnull(aadharNo) LIKE :aadharNo OR aadharNo like :aadharNo ) AND"
			+ " (Isnull(govtIdentityNo) LIKE :govtIdentityNo OR govtIdentityNo like :govtIdentityNo ) AND"
			+ " (Isnull(cast(stateID as string)) LIKE :stateID OR cast(stateID as string) like :stateID) AND"
			+ " (Isnull(cast(districtID as string)) LIKE :districtID OR cast(districtID as string) like :districtID)")

	public ArrayList<Object[]> getAdvanceBenSearchList(@Param("beneficiaryRegID") String beneficiaryRegID,
			@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("phoneNo") String phoneNo,
			@Param("aadharNo") String aadharNo, @Param("govtIdentityNo") String govtIdentityNo,
			@Param("stateID") String stateID, @Param("districtID") String districtID);

	@Query(" SELECT Distinct beneficiaryRegID, beneficiaryID, concat(IFNULL(firstName, ''), ' ',IFNULL(lastName,'')) as benName, "
			+ " Date(dob), genderID, regCreatedDate, districtName, villageName "
			+ "from V_BenAdvanceSearch  WHERE beneficiaryRegID =:benRegID ")
	public List<Object[]> getBenDetails(@Param("benRegID") Long benRegID);
	
//	@Query("SELECT DISTINCT beneficiaryRegID, beneficiaryID, "
//			+ " UPPER( concat(IFNULL(firstName, ''), ' ',IFNULL(lastName,''))) as benName, "
//			+ " Date(dob), genderID, genderName, UPPER(fatherName) as fatherName, "
//			+ " districtID, districtName, districtBranchID, villageName, phoneNo " + " FROM  V_BenAdvanceSearch "
//			+ " WHERE beneficiaryID=:searchKeyword OR  "
//			+ " CONCAT(IFNULL(firstName, ''), ' ', IFNULL(lastName, '')) like '%' ||:searchKeyword || '%' "
//			+ " OR phoneNO = :searchKeyword ")
	
	@Query("SELECT DISTINCT beneficiaryRegID, beneficiaryID, "
			+ " UPPER( concat(IFNULL(firstName, ''), ' ',IFNULL(lastName,''))) as benName, "
			+ " Date(dob), genderID, genderName, UPPER(fatherName) as fatherName, "
			+ " districtID, districtName, districtBranchID, villageName, phoneNo " + " FROM  V_BenAdvanceSearch "
			+ " WHERE (Isnull(cast(beneficiaryRegID as string)) LIKE :searchKeyword OR cast(beneficiaryRegID as string) like :searchKeyword)  OR  "
			+ " CONCAT(IFNULL(firstName, ''), ' ', IFNULL(lastName, '')) like '%' ||:searchKeyword || '%' "
			+ " OR phoneNO = :searchKeyword ")

	public List<Object[]> getQuickSearch(@Param("searchKeyword") String searchKeyword);

	@Query("Select DISTINCT beneficiaryRegID, beneficiaryID, "
			+ " UPPER( concat(IFNULL(firstName, ''), ' ',IFNULL(lastName,''))) as benName, "
			+ " Date(dob), genderID, genderName, UPPER(fatherName) as fatherName, "
			+ " districtID, districtName, districtBranchID, villageName, phoneNo "
			+ " from V_BenAdvanceSearch  Where flowStatusFlag = 'R' and Date(regLastModDate) = curdate() ")
	public List<Object[]> getNurseWorkList();

}
