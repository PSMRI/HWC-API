package com.iemr.mmu.repo.masterrepo.nurse;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.nurse.IUCDinsertiondoneby;
import com.iemr.mmu.data.masterdata.nurse.TypeofIUCDinserted;

@Repository
@RestResource(exported = false)
public interface IUCDinsertiondonebyRepo extends CrudRepository<IUCDinsertiondoneby, Integer>{
	List<IUCDinsertiondoneby> findByDeletedOrderByName(Boolean deleted);
}
