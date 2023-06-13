/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.ncdscreening;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.ncdScreening.NCDScreening;

@Repository
@RestResource(exported = false)
public interface NCDScreeningRepo extends CrudRepository<NCDScreening, Long> {

	@Query(" SELECT ba FROM NCDScreening ba WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode ")
	public NCDScreening getNCDScreeningDetails(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update NCDScreening set ncdScreeningConditionID=:ncdScreeningConditionID, "
			+ " screeningCondition=:screeningCondition, ncdScreeningReasonID=:ncdScreeningReasonID, "
			+ " reasonForScreening=:reasonForScreening,"
			+ " nextScreeningDateDB=:nextScreeningDate, actionForScreenPositive=:actionForScreenPositive, "
			+ " isScreeningComplete=:isScreeningComplete, "
			+ " isBPPrescribed = :isBPPrescribed, isBloodGlucosePrescribed = :isBloodGlucosePrescribed, "
			+ " modifiedBy=:modifiedBy where visitCode=:visitCode AND beneficiaryRegID=:beneficiaryRegID")
	public int updateNCDScreeningDetails(@Param("ncdScreeningConditionID") String ncdScreeningConditionID,
			@Param("screeningCondition") String screeningCondition,
			@Param("ncdScreeningReasonID") Integer ncdScreeningReasonID,
			@Param("reasonForScreening") String reasonForScreening,
			@Param("nextScreeningDate") Timestamp nextScreeningDate,
			@Param("actionForScreenPositive") String actionForScreenPositive,
			@Param("isScreeningComplete") Boolean isScreeningComplete, @Param("modifiedBy") String modifiedBy,
			@Param("beneficiaryRegID") Long beneficiaryRegID, @Param("visitCode") Long visitCode,
			@Param("isBPPrescribed") Boolean isBPPrescribed,
			@Param("isBloodGlucosePrescribed") Boolean isBloodGlucosePrescribed);

}
