package com.iemr.mmu.repo.quickConsultation;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.quickConsultation.LabTestOrderDetail;

@Repository
@RestResource(exported = false)
public interface LabTestOrderDetailRepo extends CrudRepository<LabTestOrderDetail, Long>{

	@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID,	procedureID, procedureName, "
			+ "testingRequirements, visitCode  from LabTestOrderDetail ba WHERE ba.beneficiaryRegID = :benRegID "
			+ "AND ba.visitCode= :visitCode")
	public ArrayList<Object[]> getLabTestOrderDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	@Query(" SELECT ba  from LabTestOrderDetail ba WHERE ba.beneficiaryRegID = :benRegID "
			+ "AND ba.visitCode= :visitCode")
	public ArrayList<LabTestOrderDetail> getLabTestOrderDetails2(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	@Modifying
	@Transactional
	@Query(" Delete from LabTestOrderDetail WHERE beneficiaryRegID = :benRegID AND benVisitID = :benVisitID ")
	public int deleteExistingLabTestOrderDetail(@Param("benRegID") Long benRegID, @Param("benVisitID") Long benVisitID);
	
}
