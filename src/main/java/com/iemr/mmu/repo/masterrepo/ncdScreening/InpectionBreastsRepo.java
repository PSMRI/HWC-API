/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.ncdScreening;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iemr.mmu.data.masterdata.ncdscreening.InpectionofBreasts;

public interface InpectionBreastsRepo extends CrudRepository<InpectionofBreasts, Integer> {
	@Query("SELECT obj FROM InpectionofBreasts obj WHERE obj.deleted is false")
	ArrayList<InpectionofBreasts> getBreastInspectionMasters();

}
