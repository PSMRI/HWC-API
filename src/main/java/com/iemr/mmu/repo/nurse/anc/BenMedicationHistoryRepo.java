package com.iemr.mmu.repo.nurse.anc;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.anc.BenMedicationHistory;

@Repository
@RestResource(exported = false)
public interface BenMedicationHistoryRepo extends CrudRepository<BenMedicationHistory, Long>{

	@Query("select Date(createdDate), currentMedication, Date(year) from BenMedicationHistory a where a.beneficiaryRegID = :beneficiaryRegID"
			+ " AND currentMedication is not null AND deleted = false order by createdDate DESC")
		public ArrayList<Object[]> getBenMedicationHistoryDetail(@Param("beneficiaryRegID") Long beneficiaryRegID);
	
		@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID, currentMedication, year, createdDate, visitCode  FROM BenMedicationHistory "
				+ " WHERE beneficiaryRegID = :benRegID AND deleted = false AND visitCode= :visitCode")
		public ArrayList<Object[]> getBenMedicationHistoryDetail(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);
	
		@Modifying
		@Transactional
		@Query(" Update BenMedicationHistory  set deleted=true, processed=:processed WHERE ID = :ID")
		public int deleteExistingBenMedicationHistory(@Param("ID") Long ID, @Param("processed") String processed);
		
		@Query("SELECT ID, processed from BenMedicationHistory where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted=false")
		public ArrayList<Object[]> getBenMedicationHistoryStatus(@Param("benRegID") Long benRegID,
				@Param("visitCode") Long visitCode);
}
