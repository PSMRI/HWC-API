/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.anc;

import java.sql.Date;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.anc.ANCWomenVaccineDetail;

@Repository
@RestResource(exported = false)
public interface ANCWomenVaccineRepo extends CrudRepository<ANCWomenVaccineDetail, Long>{
	
	@Query(" SELECT ID, beneficiaryRegID, benVisitID, providerServiceMapID, vaccineName, status, receivedDate, receivedFacilityName, visitCode "
			+ "from ANCWomenVaccineDetail ba WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode")
	public ArrayList<Object[]> getANCWomenVaccineDetails(@Param("benRegID") Long benRegID,
			 @Param("visitCode") Long visitCode);
	
	@Query(" SELECT vaccineName, processed from ANCWomenVaccineDetail where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted = false")
	public ArrayList<Object[]> getBenANCWomenVaccineStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Transactional
	@Modifying
	@Query("update ANCWomenVaccineDetail set status=:status, receivedDate=:receivedDate, receivedFacilityName=:receivedFacilityName, "
			+ "modifiedBy=:modifiedBy, processed=:processed "
			+ "where visitCode=:visitCode AND beneficiaryRegID=:beneficiaryRegID AND vaccineName=:vaccineName")
	public int updateANCImmunizationDetails(@Param("status") String status,
			@Param("receivedDate") Date receivedDate,
			@Param("receivedFacilityName") String receivedFacilityName,
			@Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed,
			@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode,
			@Param("vaccineName") String vaccineName);
	
}
