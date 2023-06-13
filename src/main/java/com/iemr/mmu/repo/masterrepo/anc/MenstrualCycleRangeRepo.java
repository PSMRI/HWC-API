/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.MenstrualCycleRange;

@Repository
@RestResource(exported = false)
public interface MenstrualCycleRangeRepo extends CrudRepository<MenstrualCycleRange, Short>{
	
	@Query("select menstrualRangeID, rangeType, menstrualCycleRange, menstrualCycleRangeDesc from MenstrualCycleRange where rangeType=:rangeType and deleted = false  order by menstrualCycleRange")
	public ArrayList<Object[]> getMenstrualCycleRanges(@Param("rangeType") String rangeType);
}
