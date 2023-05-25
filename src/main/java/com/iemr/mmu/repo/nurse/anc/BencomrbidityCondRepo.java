package com.iemr.mmu.repo.nurse.anc;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.anc.BencomrbidityCondDetails;

@Repository
@RestResource(exported = false)
public interface BencomrbidityCondRepo extends CrudRepository<BencomrbidityCondDetails, Long> {

	@Query("select Date(createdDate), comorbidCondition , otherComorbidCondition, Date(year) "
			+ "from BencomrbidityCondDetails a where a.beneficiaryRegID = :beneficiaryRegID AND a.comorbidCondition is not null "
			+ "AND a.isForHistory = true AND a.deleted = false ORDER BY a.createdDate DESC ")
	public ArrayList<Object[]> getBencomrbidityCondDetails(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID, comorbidConditionID, comorbidCondition, year, otherComorbidCondition, "
			+ " isForHistory, createdDate, visitCode  FROM BencomrbidityCondDetails "
			+ " WHERE beneficiaryRegID = :benRegID AND deleted = false AND visitCode= :visitCode")
	public ArrayList<Object[]> getBencomrbidityCondDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Modifying
	@Transactional
	@Query(" update BencomrbidityCondDetails set deleted=true, processed=:processed WHERE ID = :ID")
	public int deleteExistingBenComrbidityCondDetails(@Param("ID") Long ID, @Param("processed") String processed);

	@Query("SELECT ID, processed from BencomrbidityCondDetails where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted=false")
	public ArrayList<Object[]> getBenComrbidityCondHistoryStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT ID FROM BencomrbidityCondDetails where beneficiaryRegID=:benRegID AND "
			+ " (comorbidConditionID IN (2, 3, 5, 6)) AND deleted is false")
	public ArrayList<Long> getHRPStatus(@Param("benRegID") Long benRegID);

}
