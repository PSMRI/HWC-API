/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.registrar;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.data.registrar.BeneficiaryDemographicData;
@Repository
@RestResource(exported = false)
public interface RegistrarRepoBenDemoData extends CrudRepository<BeneficiaryDemographicData, Long> {
	
	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryDemographicData set countryID = :countryID,stateID = :stateID,districtID = :districtID,areaID = :areaID,servicePointID = :servicePointID,"
			+ " districtBranchID = :districtBranchID,communityID = :communityID,religionID = :religionID,occupationID = :occupationID, educationID = :educationID,"
			+ " incomeStatusID = :incomeStatusID, modifiedBy = :modifiedBy where beneficiaryRegID = :beneficiaryRegID ")
	public Integer updateBendemographicData(@Param("countryID") Integer countryID,
			@Param("stateID") Integer stateID,
			@Param("districtID") Integer districtID,
			@Param("areaID") Integer areaID,
			@Param("servicePointID") Integer servicePointID,
			@Param("districtBranchID") Integer districtBranchID,
			@Param("communityID") Short communityID,
			@Param("religionID") Short religionID,
			@Param("occupationID") Short occupationID,
			@Param("educationID") Short educationID,
			@Param("incomeStatusID") Short incomeStatusID,
			@Param("modifiedBy") String modifiedBy,
			@Param("beneficiaryRegID") Long beneficiaryRegID);
	
	
	
	@Query(" SELECT bd.beneficiaryRegID,bd.servicePointID,s.servicePointName from BeneficiaryDemographicData bd "
			+ "INNER JOIN bd.servicePoint s "
			+ "WHERE bd.beneficiaryRegID = :beneficiaryRegID")
	public List<Objects[]> getBeneficiaryDemographicData(@Param("beneficiaryRegID") Long beneficiaryRegID);
	
//	countryID
//	stateID
//	districtID
//	areaID
//	servicePointID
//	districtBranchID
//	communityID
//	religionID
//	occupationID
//	educationID
//	incomeStatusID

}
