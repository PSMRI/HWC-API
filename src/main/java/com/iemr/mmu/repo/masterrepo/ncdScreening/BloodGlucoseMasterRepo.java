/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.ncdScreening;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.ncdscreening.BloodGlucoseType;
@Repository
public interface BloodGlucoseMasterRepo extends CrudRepository<BloodGlucoseType, Integer> {
	
	@Query("select b from BloodGlucoseType b where b.deleted = false")
	public ArrayList<BloodGlucoseType> getBloodGlucoseType();


}
