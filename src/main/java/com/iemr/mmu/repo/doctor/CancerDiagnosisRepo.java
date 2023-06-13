/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.doctor;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.doctor.CancerDiagnosis;

@Repository
@RestResource(exported = false)
public interface CancerDiagnosisRepo extends CrudRepository<CancerDiagnosis, Long> {

	@Query(" SELECT c from CancerDiagnosis c  WHERE c.beneficiaryRegID = :benRegID "
			+ " AND c.deleted = false AND c.visitCode = :visitCode")
	public CancerDiagnosis getBenCancerDiagnosisDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Query(" SELECT processed  from CancerDiagnosis c  WHERE c.beneficiaryRegID = :benRegID AND c.visitCode = :visitCode AND c.deleted=false")
	public String getCancerDiagnosisStatuses(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query(" update CancerDiagnosis set provisionalDiagnosisOncologist=:provisionalDiagnosisOncologist, modifiedBy=:modifiedBy, processed=:processed "
			+ "WHERE beneficiaryRegID =:benRegID AND visitCode =:visitCode")
	public int updateDetailsByOncologist(@Param("provisionalDiagnosisOncologist") String provisionalDiagnosisOncologist,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("modifiedBy") String modifiedBy, @Param("processed") String processed);

	@Transactional
	@Modifying
	@Query(" update CancerDiagnosis set provisionalDiagnosisPrimaryDoctor=:provisionalDiagnosisPrimaryDoctor, remarks=:remarks, "
			+ "referredToInstituteID=:referredToInstituteID, refrredToAdditionalService=:refrredToAdditionalService, "
			+ "modifiedBy=:modifiedBy, processed=:processed, revisitDate =:revisitDate, referralReason =:referralReason "
			+ "WHERE beneficiaryRegID =:beneficiaryRegID AND visitCode =:visitCode")
	public int updateCancerDiagnosisDetailsByDoctor(
			@Param("provisionalDiagnosisPrimaryDoctor") String provisionalDiagnosisPrimaryDoctor,
			@Param("remarks") String remarks, @Param("referredToInstituteID") Integer referredToInstituteID,
			@Param("refrredToAdditionalService") String refrredToAdditionalService,
			@Param("modifiedBy") String modifiedBy, @Param("processed") String processed,
			@Param("beneficiaryRegID") Long beneficiaryRegID, @Param("visitCode") Long visitCode,
			@Param("revisitDate") Timestamp revisitDate, @Param("referralReason") String referralReason);
	       

}
