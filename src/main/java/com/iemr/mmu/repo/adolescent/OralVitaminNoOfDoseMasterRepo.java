package com.iemr.mmu.repo.adolescent;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.adolescent.OralVitaminNoOfDoseMaster;

@Repository
public interface OralVitaminNoOfDoseMasterRepo extends CrudRepository<OralVitaminNoOfDoseMaster, Short> {

	List<OralVitaminNoOfDoseMaster> findByDeleted(Boolean deleted);

}
