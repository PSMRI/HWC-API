package com.iemr.mmu.repo.quickConsultation;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.quickConsultation.BenChiefComplaint;

@Repository
@RestResource(exported = false)
public interface BenChiefComplaintRepo extends CrudRepository<BenChiefComplaint, Long> {

	@Query(" SELECT benChiefComplaintID, beneficiaryRegID, benVisitID, providerServiceMapID, chiefComplaintID, chiefComplaint, "
			+ "duration, unitOfDuration, description, visitCode,conceptID "
			+ "from BenChiefComplaint ba WHERE ba.beneficiaryRegID = :benRegID "
			+ " AND ba.deleted = false AND ba.chiefComplaintID is not null AND ba.visitCode = :visitCode")
	public ArrayList<Object[]> getBenChiefComplaints(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Modifying
	@Transactional
	@Query(" Delete from BenChiefComplaint WHERE beneficiaryRegID = :benRegID AND visitCode = :visitCode")
	public int deleteExistingBenChiefComplaints(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

}
