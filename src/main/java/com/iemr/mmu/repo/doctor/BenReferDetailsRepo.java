package com.iemr.mmu.repo.doctor;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.doctor.BenReferDetails;

@Repository
@RestResource(exported = false)
public interface BenReferDetailsRepo extends CrudRepository<BenReferDetails, Long> {

//	@Query(" SELECT benReferID, beneficiaryRegID, benVisitID, providerServiceMapID, referredToInstituteID, "
//			+ "referredToInstituteName, serviceID, serviceName, visitCode, revisitDate , referralReason, "
//			+ " createdDate, otherReferredToInstituteName, otherReferralReason "
//			+ "from BenReferDetails ba WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode AND ba.deleted = false")
//	public ArrayList<Object[]> getBenReferDetails(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	public ArrayList<BenReferDetails> findByBeneficiaryRegIDAndVisitCode(Long benRegId, Long visitCode);

	@Query(" SELECT ba "
			+ "from BenReferDetails ba WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode AND ba.deleted = false")
	public ArrayList<BenReferDetails> getBenReferDetails2(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	/*
	 * @Query("SELECT processed from BenReferDetails where beneficiaryRegID=:benRegID AND benVisitID = :benVisitID"
	 * ) public String getBenReferDetailsStatus(@Param("benRegID") Long benRegID,
	 * 
	 * @Param("benVisitID") Long benVisitID);
	 */

	@Query("SELECT benReferID, processed from BenReferDetails where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted=false")
	public ArrayList<Object[]> getBenReferDetailsStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Modifying
	@Transactional
	@Query(" Update BenReferDetails  set referredToInstituteID=:referredToInstituteID, "
			+ "referredToInstituteName=:referredToInstituteName,revisitDate=:revisitDate,referralReason=:referralReason, processed=:processed "
			+ "WHERE benReferID =:benReferID")
	public int updateReferredInstituteName(@Param("referredToInstituteID") Integer referredToInstituteID,
			@Param("referredToInstituteName") String referredToInstituteName,
			@Param("revisitDate") Timestamp revisitDate, @Param("referralReason") String referralReason,
			@Param("benReferID") Long benReferID, @Param("processed") String processed);
}
