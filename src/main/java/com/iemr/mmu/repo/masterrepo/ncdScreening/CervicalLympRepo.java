/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.ncdScreening;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iemr.mmu.data.masterdata.ncdscreening.CervicalLymph;

public interface CervicalLympRepo extends CrudRepository<CervicalLymph, Integer> {

	@Query("SELECT obj FROM CervicalLymph obj WHERE obj.deleted is false")
	List<CervicalLymph> getCervicalScreening();
}
