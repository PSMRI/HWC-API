package com.iemr.mmu.repo.quickConsultation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.quickConsultation.ExternalLabTestOrder;

@Repository
@RestResource(exported = false)
public interface ExternalTestOrderRepo extends CrudRepository<ExternalLabTestOrder, Long>{

}
