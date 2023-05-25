package com.iemr.mmu.repo.doctor;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.doctor.LabTestMaster;

@Repository
@RestResource(exported = false)
public interface LabTestMasterRepo extends CrudRepository<LabTestMaster, Integer> {
	@Query("SELECT testID, testName, isRadiologyImaging FROM LabTestMaster c where c.deleted != 1 ORDER BY testName  ")
	public ArrayList<Object[]> getLabTestMaster();
	
	@Query("SELECT testID, testName, testDesc, isRadiologyImaging FROM LabTestMaster c where c.isRadiologyImaging != 1 AND c.deleted != 1 ORDER BY testName")
	public ArrayList<Object[]> getNonRadiologyLabTests();
	
	@Query("SELECT testID, testName FROM LabTestMaster c where c.testFor=:testFor AND c.deleted != 1 ORDER BY testName")
	public ArrayList<Object[]> getTestsBYVisitCategory(@Param("testFor") String testFor);
}
