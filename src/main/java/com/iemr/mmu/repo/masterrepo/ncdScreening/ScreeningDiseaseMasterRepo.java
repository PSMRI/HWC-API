package com.iemr.mmu.repo.masterrepo.ncdScreening;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.ncdscreening.ScreeningDiseases;

@Repository
@RestResource(exported = false)
public interface ScreeningDiseaseMasterRepo extends CrudRepository<ScreeningDiseases, Integer> {
	@Query("select s from ScreeningDiseases s where s.deleted = false")
	public ArrayList<ScreeningDiseases> getScreeningDiseases();

}
