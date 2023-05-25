package com.iemr.mmu.repo.masterrepo.ncdScreening;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iemr.mmu.data.masterdata.ncdscreening.MouthOpening;

public interface MouthOpeningRepo extends CrudRepository<MouthOpening, Integer> {
	
	@Query("SELECT obj FROM MouthOpening obj WHERE obj.deleted is false")
	ArrayList<MouthOpening> getMouthOpeningMasters();

}
