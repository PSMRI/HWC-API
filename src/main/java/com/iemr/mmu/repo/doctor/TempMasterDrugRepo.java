/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.doctor;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.doctor.TempMasterDrug;

@Repository
@RestResource(exported = false)
public interface TempMasterDrugRepo extends CrudRepository<TempMasterDrug, Long> {
	ArrayList<TempMasterDrug> findByDeletedFalseOrderByDrugDisplayNameAsc();

}
