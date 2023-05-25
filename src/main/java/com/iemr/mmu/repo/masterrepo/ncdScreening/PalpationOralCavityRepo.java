package com.iemr.mmu.repo.masterrepo.ncdScreening;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iemr.mmu.data.masterdata.ncdscreening.PalpationOfOralCavity;

public interface PalpationOralCavityRepo extends CrudRepository<PalpationOfOralCavity, Integer> {
	@Query("SELECT obj FROM PalpationOfOralCavity obj WHERE obj.deleted is false")
	ArrayList<PalpationOfOralCavity> getPalpationOralCavityMasters();

}
