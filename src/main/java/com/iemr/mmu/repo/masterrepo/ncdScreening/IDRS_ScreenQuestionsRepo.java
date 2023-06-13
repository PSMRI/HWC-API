/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.ncdScreening;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.ncdscreening.IDRS_ScreenQuestions;

@Repository
@RestResource(exported = false)
public interface IDRS_ScreenQuestionsRepo extends CrudRepository<IDRS_ScreenQuestions, Integer> {
	ArrayList<IDRS_ScreenQuestions> findByDeleted(Boolean deleted);
}
