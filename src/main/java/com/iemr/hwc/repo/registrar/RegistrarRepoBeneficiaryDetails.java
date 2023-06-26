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

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.registrar.FetchBeneficiaryDetails;

@Repository
@RestResource(exported = false)
public interface RegistrarRepoBeneficiaryDetails extends CrudRepository<FetchBeneficiaryDetails, Long> {

	@Query(" SELECT d.beneficiaryRegID, d.beneficiaryID, d.firstName, d.lastName, d.gender, "
			+ "Date(d.dob), d.maritalStatus, d.husbandName, d.income, d.educationQualification, d.occupation,  "
			+ " d.blockID, d.blockName, d.stateID, d.stateName,"
			+ " d.community, d.religion, d.fatherName, d.aadharNo, d.districtID, d.districtName, d.villageID,  "
			+ " d.villageName, d.phoneNo, "
			+ " d.govtIdentityTypeID, d.govtIdentityNo, d.isGovtID, Date(d.marrigeDate), d.literacyStatus, d.motherName, d.emailID, "
			+ " d.bankName, d.branchName, "
			+ " d.IFSCCode, d.accountNumber, d.benGovMapID from FetchBeneficiaryDetails d where d.beneficiaryRegID=:beneficiaryRegID")
	public List<Object[]> getBeneficiaryDetails(@Param("beneficiaryRegID") Long beneficiaryRegID);
}
