/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.anc;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.anc.BenMedHistory;

@Repository
@RestResource(exported = false)
public interface BenMedHistoryRepo extends CrudRepository<BenMedHistory, Long> {

	@Query(" SELECT Date(createdDate), illnessType, otherIllnessType, Date(yearofIllness), surgeryType, otherSurgeryType, "
			+ " Date(yearofSurgery)  FROM BenMedHistory "
			+ " WHERE beneficiaryRegID = :benRegID AND (illnessType is not null OR surgeryType is not null ) AND deleted = false order by createdDate DESC")
	public ArrayList<Object[]> getBenPastHistory(@Param("benRegID") Long benRegID);

	@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID, yearofIllness, illnessTypeID,  illnessType, otherIllnessType, "
			+ " surgeryID , surgeryType, yearofSurgery, otherSurgeryType, createdDate, visitCode  FROM BenMedHistory "
			+ " WHERE beneficiaryRegID = :benRegID AND deleted = false AND visitCode = :visitCode")
	public ArrayList<Object[]> getBenPastHistory(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Modifying
	@Transactional
	@Query(" update BenMedHistory set deleted=true, processed=:processed WHERE benMedHistoryID = :benMedHistoryID")
	public int deleteExistingBenMedHistory(@Param("benMedHistoryID") Long benMedHistoryID,
			@Param("processed") String processed);

	@Query("SELECT benMedHistoryID, processed from BenMedHistory where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted=false")
	public ArrayList<Object[]> getBenMedHistoryStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT benMedHistoryID FROM BenMedHistory WHERE beneficiaryRegID = :benRegID AND "
			+ "   (illnessTypeID IN (11, 13, 15, 16, 17) OR surgeryID IN (5, 15, 16 )) AND deleted is false ")
	public ArrayList<Long> getHRPStatus(@Param("benRegID") Long benRegID);

}
