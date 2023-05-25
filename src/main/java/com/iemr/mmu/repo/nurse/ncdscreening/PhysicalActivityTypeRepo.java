package com.iemr.mmu.repo.nurse.ncdscreening;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.ncdScreening.PhysicalActivityType;



@Repository
@RestResource(exported = false)
public interface PhysicalActivityTypeRepo extends CrudRepository<PhysicalActivityType, Long> {
	
	@Query("select Date(createdDate), activityType, physicalActivityType "
			+ "from PhysicalActivityType a where a.beneficiaryRegID = :beneficiaryRegID  AND (deleted = false or deleted is null)  "
			+ "order by createdDate DESC")
	public ArrayList<Object[]> getBenPhysicalHistoryDetail(@Param("beneficiaryRegID") Long beneficiaryRegID);
	
	@Query("select a from PhysicalActivityType a where a.beneficiaryRegID = :beneficiaryRegID and  a.visitCode = :visitCode")
	public PhysicalActivityType getBenPhysicalHistoryDetails(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

}
