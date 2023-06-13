/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.pnc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.pnc.PNCDiagnosis;

@Repository
@RestResource(exported = false)
public interface PNCDiagnosisRepo extends CrudRepository<PNCDiagnosis, Long> {

	@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID, prescriptionID, provisionalDiagnosis, "
			+ "confirmatoryDiagnosis, isMaternalDeath, placeOfDeath, dateOfDeath, causeOfDeath, visitCode "
			+ " from PNCDiagnosis ba "
			+ "WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode AND ba.deleted = false "
			+ " ORDER BY createdDate Desc ")
	public ArrayList<Object[]> getPNCDiagnosisDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	ArrayList<PNCDiagnosis> findByBeneficiaryRegIDAndVisitCode(Long benRegID, Long visitCode);

	@Query("SELECT processed from PNCDiagnosis where beneficiaryRegID=:benRegID AND visitCode = :visitCode "
			+ " AND prescriptionID =:prescriptionID ")
	public String getPNCDiagnosisStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("prescriptionID") Long prescriptionID);

	@Transactional
	@Modifying
	@Query("update PNCDiagnosis set provisionalDiagnosis=:provisionalDiagnosis, "
			+ " confirmatoryDiagnosis=:confirmatoryDiagnosis, "
			+ " isMaternalDeath=:isMaternalDeath, placeOfDeath=:placeOfDeath, "
			+ " dateOfDeath=:dateOfDeath, causeOfDeath=:causeOfDeath, modifiedBy=:modifiedBy, "
			+ " processed=:processed, provisionalDiagnosisSCTCode =:provisionalDiagnosisSCTCode, "
			+ " provisionalDiagnosisSCTTerm =:provisionalDiagnosisSCTTerm, "
			+ " confirmatoryDiagnosisSCTCode =:confirmatoryDiagnosisSCTCode, "
			+ " confirmatoryDiagnosisSCTTerm =:confirmatoryDiagnosisSCTTerm "
			+ " where visitCode=:visitCode AND beneficiaryRegID=:beneficiaryRegID "
			+ " AND prescriptionID =:prescriptionID ")
	public int updatePNCDiagnosis(@Param("provisionalDiagnosis") String provisionalDiagnosis,
			@Param("confirmatoryDiagnosis") String confirmatoryDiagnosis,
			@Param("isMaternalDeath") Boolean isMaternalDeath, @Param("placeOfDeath") String placeOfDeath,
			@Param("dateOfDeath") Date dateOfDeath, @Param("causeOfDeath") String causeOfDeath,
			@Param("modifiedBy") String modifiedBy, @Param("processed") String processed,
			@Param("beneficiaryRegID") Long beneficiaryRegID, @Param("visitCode") Long visitCode,
			@Param("provisionalDiagnosisSCTCode") String provisionalDiagnosisSCTCode,
			@Param("provisionalDiagnosisSCTTerm") String provisionalDiagnosisSCTTerm,
			@Param("confirmatoryDiagnosisSCTCode") String confirmatoryDiagnosisSCTCode,
			@Param("confirmatoryDiagnosisSCTTerm") String confirmatoryDiagnosisSCTTerm,
			@Param("prescriptionID") Long prescriptionID);
	//shubham 13-10-2020,TM Prescription SMS
	@Query("SELECT processed from PNCDiagnosis where visitCode = :visitCode "
			+ " AND prescriptionID =:prescriptionID AND (deleted <> true OR deleted IS NULL)")
	public  List<Object>  getProvisionalDiagnosis(@Param("visitCode") Long visitCode,
			@Param("prescriptionID") Long prescriptionID);

}
