package com.iemr.mmu.repo.masterrepo.ncdScreening;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iemr.mmu.data.masterdata.ncdscreening.OralCavityFinding;

public interface OralCavityFindingRepo extends CrudRepository<OralCavityFinding, Integer> {
	@Query("SELECT obj FROM OralCavityFinding obj WHERE obj.deleted is false")
	ArrayList<OralCavityFinding> getOralCavityMasters();

}
