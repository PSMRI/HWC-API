/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.ncdScreening;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iemr.mmu.data.masterdata.ncdscreening.TemperomandibularJoin;

public interface TemperomandibularRepo extends CrudRepository<TemperomandibularJoin, Integer> {
	@Query("SELECT obj FROM TemperomandibularJoin obj WHERE obj.deleted is false")
	ArrayList<TemperomandibularJoin> getTemperomandibularJoinMasters();

}
