/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.ncdcare;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.ncdcare.NCDCareDiagnosis;

@Repository
@RestResource(exported = false)
public interface NCDCareDiagnosisRepo extends CrudRepository<NCDCareDiagnosis, Long> {

	@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID, prescriptionID, "
			+ " ncdScreeningCondition, ncdComplication, ncdCareType, visitCode, ncdScreeningConditionOther "
			+ " from NCDCareDiagnosis ba " + " WHERE ba.beneficiaryRegID = :benRegID"
			+ " AND ba.visitCode = :visitCode AND ba.deleted = false " + " ORDER BY createdDate desc")
	public ArrayList<Object[]> getNCDCareDiagnosisDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT processed from NCDCareDiagnosis where beneficiaryRegID=:benRegID AND visitCode = :visitCode "
			+ " AND prescriptionID =:prescriptionID ")
	public String getNCDCareDiagnosisStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("prescriptionID") Long prescriptionID);

	@Transactional
	@Modifying
	@Query("update NCDCareDiagnosis set ncdScreeningCondition=:ncdScreeningCondition, ncdComplication=:ncdComplication, "
			+ "ncdCareType=:ncdCareType, modifiedBy=:modifiedBy, processed=:processed, "
			+ " ncdScreeningConditionOther = :ncdScreeningConditionOther "
			+ "where visitCode=:visitCode AND beneficiaryRegID=:beneficiaryRegID AND prescriptionID =:prescriptionID ")
	public int updateNCDCareDiagnosis(@Param("ncdScreeningCondition") String ncdScreeningCondition,
			@Param("ncdComplication") String ncdComplication, @Param("ncdCareType") String ncdCareType,
			@Param("modifiedBy") String modifiedBy, @Param("processed") String processed,
			@Param("beneficiaryRegID") Long beneficiaryRegID, @Param("visitCode") Long visitCode,
			@Param("prescriptionID") Long prescriptionID,
			@Param("ncdScreeningConditionOther") String ncdScreeningConditionOther);

	@Query("SELECT ncdScreeningCondition from NCDCareDiagnosis where visitCode = :visitCode "
			+ " AND prescriptionID =:prescriptionID AND (deleted <> true OR deleted IS NULL)")
	public List<Object> getNCDcondition(@Param("visitCode") Long visitCode,
			@Param("prescriptionID") Long prescriptionID);
}
