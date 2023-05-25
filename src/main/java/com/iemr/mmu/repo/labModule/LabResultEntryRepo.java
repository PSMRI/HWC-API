package com.iemr.mmu.repo.labModule;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.labModule.LabResultEntry;

@Repository
@RestResource(exported = false)
public interface LabResultEntryRepo extends CrudRepository<LabResultEntry, Long> {
	@Query("SELECT procedureID FROM LabResultEntry WHERE beneficiaryRegID = :benRegID AND "
			+ " benVisitID = :benVisitID ")
	ArrayList<Integer> findProcedureListByBeneficiaryRegIDAndBenVisitID(@Param("benRegID") Long benRegID,
			@Param("benVisitID") Long benVisitID);

	ArrayList<LabResultEntry> findByBeneficiaryRegIDAndVisitCodeOrderByProcedureIDAsc(Long benRegID, Long visitCode);

	@Query(nativeQuery = true, value = "SELECT DISTINCT visitCode, Date(createdDate) FROM t_lab_testresult "
			+ " WHERE beneficiaryRegID = :benRegID AND visitCode != :visitCode AND visitCode IS NOT NULL "
			+ " GROUP BY visitCode " + " ORDER BY createdDate DESC LIMIT 3 ")
	ArrayList<Object[]> getLast_3_visitForLabTestDone(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

}
