/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.masterrepo.ncdScreening;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.ncdscreening.BPAndDiabeticStatus;

@Repository
@RestResource(exported = false)
public interface BPAndDiabeticStatusRepo extends JpaRepository<BPAndDiabeticStatus, Short>{

	@Query("select bpAndDiabeticStatusID, bpAndDiabeticStatus from BPAndDiabeticStatus where deleted = false AND isBPStatus=:isBPStatus order by bpAndDiabeticStatus")
	public ArrayList<Object[]> getBPAndDiabeticStatus(@Param("isBPStatus") Boolean isBPStatus);
}
