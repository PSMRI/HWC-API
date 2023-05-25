package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.MenstrualCycleStatus;

@Repository
@RestResource(exported = false)
public interface MenstrualCycleStatusRepo extends CrudRepository<MenstrualCycleStatus, Short> {

	@Query(" SELECT menstrualCycleStatusID, name, menstrualCycleStatusDesc FROM MenstrualCycleStatus "
			+ " WHERE deleted = false AND (visitCategoryID = :visitCategoryID OR visitCategoryID is null) "
			+ " ORDER BY name")
	public ArrayList<Object[]> getMenstrualCycleStatuses(@Param("visitCategoryID") Integer visitCategoryID);
}
