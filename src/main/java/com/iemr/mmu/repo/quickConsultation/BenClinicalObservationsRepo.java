package com.iemr.mmu.repo.quickConsultation;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.quickConsultation.BenClinicalObservations;

@Repository
@RestResource(exported = false)
public interface BenClinicalObservationsRepo extends CrudRepository<BenClinicalObservations, Long> {

	@Query("select Date(createdDate), significantFindings  from BenClinicalObservations a where a.beneficiaryRegID = :beneficiaryRegID "
			+ "AND significantFindings is not null AND isForHistory = true AND deleted = false "
			+ " ORDER BY createdDate DESC ")
	public ArrayList<Object[]> getPreviousSignificantFindings(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID, clinicalObservation, "
			+ "otherSymptoms, significantFindings, isForHistory, visitCode, clinicalObservationSctcode, significantFindingsSctcode from BenClinicalObservations ba "
			+ "WHERE ba.beneficiaryRegID = :benRegID AND ba.deleted = false AND ba.visitCode = :visitCode")
	public ArrayList<Object[]> getFindingsData(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Query("SELECT processed from BenClinicalObservations where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getBenClinicalObservationStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update BenClinicalObservations set clinicalObservation=:clinicalObservation, "
			+ " otherSymptoms=:otherSymptoms, otherSymptomsSCTCode=:otherSymptomsSCTCode, "
			+ " otherSymptomsSCTTerm=:otherSymptomsSCTTerm, "
			+ " significantFindings=:significantFindings, isForHistory=:isForHistory, "
			+ " modifiedBy=:modifiedBy, processed=:processed, clinicalObservationSctcode=:clinicalObservationSctcode, "
			+ " significantFindingsSctcode=:significantFindingsSctcode where "
			+ "beneficiaryRegID=:beneficiaryRegID AND visitCode = :visitCode")
	public int updateBenClinicalObservations(@Param("clinicalObservation") String clinicalObservation,
			@Param("otherSymptoms") String otherSymptoms, @Param("otherSymptomsSCTCode") String otherSymptomsSCTCode,
			@Param("otherSymptomsSCTTerm") String otherSymptomsSCTTerm,
			@Param("significantFindings") String significantFindings, @Param("isForHistory") Boolean isForHistory,
			@Param("modifiedBy") String modifiedBy, @Param("processed") String processed,
			@Param("beneficiaryRegID") Long beneficiaryRegID, @Param("visitCode") Long visitCode,
			@Param("clinicalObservationSctcode") String clinicalObservationSctcode,
			@Param("significantFindingsSctcode") String significantFindingsSctcode);
}
