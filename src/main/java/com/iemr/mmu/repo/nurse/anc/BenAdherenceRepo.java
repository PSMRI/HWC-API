/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.anc.BenAdherence;

@Repository
@RestResource(exported = false)
public interface BenAdherenceRepo extends CrudRepository<BenAdherence, Long> {

	@Query(" SELECT ID, beneficiaryRegID, benVisitID, providerServiceMapID, visitCode, toDrugs, drugReason, toReferral, referralReason, progress "
			+ "from BenAdherence ba WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode= :visitCode")
	public ArrayList<Object[]> getBenAdherence(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Query(" SELECT processed from BenAdherence where beneficiaryRegID=:benRegID AND benVisitID = :benVisitID AND ID=:ID")
	public String getBenAdherenceDetailsStatus(@Param("benRegID") Long benRegID,
			@Param("benVisitID") Long benVisitID,
			@Param("ID") Long ID);
	
	
	@Transactional
	@Modifying
	@Query("update BenAdherence set toDrugs=:toDrugs, drugReason=:drugReason, toReferral=:toReferral, referralReason=:referralReason,"
			+ " progress=:progress,  modifiedBy=:modifiedBy, processed=:processed where beneficiaryRegID=:benRegID AND benVisitID = :benVisitID AND ID=:ID")
	public int updateBenAdherence(@Param("toDrugs") Boolean toDrugs,
			@Param("drugReason") String drugReason,
			@Param("toReferral") Boolean toReferral,
			@Param("referralReason") String referralReason,
			@Param("progress") String progress,
			@Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed,
			@Param("benRegID") Long benRegID,
			@Param("benVisitID") Long benVisitID,
			@Param("ID") Long ID);
	
}
