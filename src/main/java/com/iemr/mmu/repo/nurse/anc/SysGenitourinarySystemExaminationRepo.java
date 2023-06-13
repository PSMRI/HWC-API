/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.anc;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.anc.SysGenitourinarySystemExamination;

@Repository
@RestResource(exported = false)
public interface SysGenitourinarySystemExaminationRepo extends CrudRepository<SysGenitourinarySystemExamination, Long> {
	
	@Query(" SELECT u FROM SysGenitourinarySystemExamination u WHERE u.beneficiaryRegID = :benRegID AND u.visitCode = :visitCode ")
	public SysGenitourinarySystemExamination getSysGenitourinarySystemExaminationData(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	

	@Query("SELECT processed from SysGenitourinarySystemExamination where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getBenGenitourinarySystemExaminationStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Transactional
	@Modifying
	@Query("update SysGenitourinarySystemExamination set renalAngle=:renalAngle, suprapubicRegion=:suprapubicRegion, "
			+ "externalGenitalia=:externalGenitalia, modifiedBy=:modifiedBy, processed=:processed "
			+ "where beneficiaryRegID=:benRegID and visitCode = :visitCode ")
	public int updateSysGenitourinarySystemExamination(@Param("renalAngle") String renalAngle,
			@Param("suprapubicRegion") String suprapubicRegion,
			@Param("externalGenitalia") String externalGenitalia,
			@Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed,
			@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
}
