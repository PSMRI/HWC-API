/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.anc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.anc.ChildVaccineDetail1;

@Repository
@RestResource(exported = false)
public interface ChildVaccineDetail1Repo extends CrudRepository<ChildVaccineDetail1, Long> {

	@Query("select Date(createdDate), defaultReceivingAge, vaccineName, status "
			+ "from ChildVaccineDetail1 a where a.beneficiaryRegID = :beneficiaryRegID ORDER BY createdDate DESC ")
	public ArrayList<Object[]> getBenChildVaccineDetails(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query("select beneficiaryRegID, benVisitID, providerServiceMapID, defaultReceivingAge, vaccineName, status,"
			+ " visitCode, sctCode, sctTerm, vaccinationReceivedAt "
			+ " from ChildVaccineDetail1 a where a.beneficiaryRegID = :beneficiaryRegID and a.visitCode = :visitCode"
			+ " and a.deleted is false ")
	public ArrayList<Object[]> getBenChildVaccineDetails(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

	@Query("select beneficiaryRegID, benVisitID, providerServiceMapID, defaultReceivingAge, vaccineName, status,"
			+ " visitCode, sctCode, sctTerm, vaccinationReceivedAt "
			+ " from ChildVaccineDetail1 a where a.beneficiaryRegID = :beneficiaryRegID and a.deleted is false ")
	public ArrayList<Object[]> getBenChildVaccineDetailsByRegID(@Param("beneficiaryRegID") Long beneficiaryRegID);

	// new method - utility - 04-11-2022
	List<ChildVaccineDetail1> findByBeneficiaryRegIDAndVisitCodeAndDeleted(Long benRegId, Long visitCode,
			Boolean deleted);

	@Query(" SELECT defaultReceivingAge, vaccineName, processed from ChildVaccineDetail1 where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public ArrayList<Object[]> getBenChildVaccineDetailStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update ChildVaccineDetail1 set status=:status, modifiedBy=:modifiedBy, "
			+ " processed=:processed,sctCode=:sctCode,sctTerm =:sctTerm, vaccinationReceivedAt=:vaccinationReceivedAt"
			+ " where  beneficiaryRegID=:benRegID and visitCode = :visitCode  AND defaultReceivingAge=:defaultReceivingAge AND vaccineName=:vaccineName")
	public int updateChildANCImmunization(@Param("status") Boolean status, @Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed, @Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("defaultReceivingAge") String defaultReceivingAge, @Param("vaccineName") String vaccineName,
			@Param("sctCode") String sctCode, @Param("sctTerm") String sctTerm,
			@Param("vaccinationReceivedAt") String vaccinationReceivedAt);

}
