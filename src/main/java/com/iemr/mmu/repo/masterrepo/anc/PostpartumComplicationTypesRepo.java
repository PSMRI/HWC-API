package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.PostpartumComplicationTypes;

@Repository
@RestResource(exported = false)
public interface PostpartumComplicationTypesRepo extends CrudRepository<PostpartumComplicationTypes, Short>{

	@Query("select postpartumComplicationID, postpartumComplicationType from PostpartumComplicationTypes where "
			+ "deleted = false order by postpartumComplicationType")
	public ArrayList<Object[]> getPostpartumComplicationTypes();
}
