package com.iemr.mmu.repo.quickBlox;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.quickBlox.Quickblox;


@Repository
@RestResource(exported = false)
public interface QuickBloxRepo extends CrudRepository<Quickblox, Long> {
	
	//public List<Quickblox> findAll(Long specialistUserID);
	@Query("SELECT t FROM Quickblox t WHERE t.specialistUserID = :specialistUserID")
	Quickblox getQuickbloxIds(@Param("specialistUserID") Integer specialistUserID);
}
