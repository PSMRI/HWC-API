/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.doctor;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.doctor.RouteOfAdmin;

@Repository
@RestResource(exported = false)
public interface RouteOfAdminRepo extends CrudRepository<RouteOfAdmin, Long> {
	@Query("SELECT routeID, routeName FROM RouteOfAdmin WHERE deleted is false ORDER BY routeName ")
	public ArrayList<Object[]> getRouteOfAdminList();

}
