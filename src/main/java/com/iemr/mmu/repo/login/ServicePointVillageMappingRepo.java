package com.iemr.mmu.repo.login;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.login.ServicePointVillageMapping;

@Repository
@RestResource(exported = false)
public interface ServicePointVillageMappingRepo extends CrudRepository<ServicePointVillageMapping, Integer>{
	@Query("SELECT d.districtBranchID,d.villageName from ServicePointVillageMapping s "
			+ " INNER JOIN s.districtBranchMapping d"
			+ " WHERE s.servicePointID = :servicePointID and s.deleted != 1 ")
	public List<Object[]> getServicePointVillages(@Param("servicePointID") Integer servicePointID);
}
