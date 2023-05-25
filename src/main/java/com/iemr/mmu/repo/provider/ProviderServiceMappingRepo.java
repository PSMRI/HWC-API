package com.iemr.mmu.repo.provider;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.provider.ProviderServiceMapping;

@Repository
@RestResource(exported = false)
public interface ProviderServiceMappingRepo extends CrudRepository<ProviderServiceMapping, Integer> {
	@Query(" SELECT t.providerServiceMapID FROM ProviderServiceMapping t "
			+ " WHERE t.serviceID = :serviceID AND deleted is false ")
	List<Integer> getProviderServiceMapIdForServiceID(@Param("serviceID") Short serviceID);
}
