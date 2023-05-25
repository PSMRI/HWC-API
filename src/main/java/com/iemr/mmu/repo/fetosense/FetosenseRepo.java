package com.iemr.mmu.repo.fetosense;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.fetosense.Fetosense;

@Repository
@RestResource(exported = false)
public interface FetosenseRepo extends CrudRepository<Fetosense, Long> {

	/***
	 * @author DU20091017
	 * @param fetosenseID
	 * @return
	 */
	@Query("SELECT f FROM Fetosense f WHERE f.partnerFetosenseId = :fetosenseID")
	public Fetosense getFetosenseDetails(@Param("fetosenseID") Long fetosenseID);

	/***
	 * @author DU20091017 get the feto sense details while lab flag update.
	 */
	@Query("SELECT f FROM Fetosense f WHERE f.benFlowID = :benFlowID AND f.deleted is false ")
	public ArrayList<Fetosense> getFetosenseDetailsByFlowId(@Param("benFlowID") Long benFlowID);

	/***
	 * @author DU20091017 update visitCode
	 */
	@Modifying
	@Transactional
	@Query("UPDATE Fetosense f SET f.visitCode = :visitCode WHERE f.benFlowID = :benFlowID")
	public int updateVisitCode(@Param("visitCode") Long visitCode, @Param("benFlowID") Long benFlowID);

	/***
	 * @author DU20091017 get the details for case record.
	 * @param beneficiaryRegID
	 * @param visitCode
	 * @return
	 */

	@Query("SELECT f FROM Fetosense f WHERE f.beneficiaryRegID = :beneficiaryRegID "
			+ " AND f.visitCode = :visitCode AND f.deleted is false ")
	public ArrayList<Fetosense> getFetosenseDetailsForCaseRecord(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

	@Query(value = "SELECT beneficiaryid FROM db_identity.m_beneficiaryregidmapping where BenRegId= :benRegId ", nativeQuery = true)
	Long getBenID(@Param("benRegId") Long benRegId);

}
