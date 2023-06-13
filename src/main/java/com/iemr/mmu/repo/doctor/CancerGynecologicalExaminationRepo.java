/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.doctor;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.doctor.CancerGynecologicalExamination;
@Repository
@RestResource(exported = false)
public interface CancerGynecologicalExaminationRepo extends CrudRepository<CancerGynecologicalExamination, Long> {
	
	@Query(" SELECT c from CancerGynecologicalExamination c WHERE c.beneficiaryRegID = :benRegID "
			+ "AND c.deleted = false And c.visitCode = :visitCode")
	public CancerGynecologicalExamination getBenCancerGynecologicalExaminationDetails(@Param("benRegID") Long benRegID,
	@Param("visitCode") Long visitCode);
	
	@Query("SELECT processed from CancerGynecologicalExamination where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getCancerGynecologicalExaminationStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	
	@Transactional
	@Modifying
	@Query("update CancerGynecologicalExamination set providerServiceMapID=:providerServiceMapID, appearanceOfCervix=:appearanceOfCervix, "
			+ "typeOfLesion=:typeOfLesion, vulvalInvolvement=:vulvalInvolvement, vaginalInvolvement=:vaginalInvolvement,"
			+ "uterus_Normal=:uterus_Normal, sufferedFromRTIOrSTI=:sufferedFromRTIOrSTI, rTIOrSTIDetail=:rTIOrSTIDetail,"
			+ "filePath=:filePath, experiencedPostCoitalBleeding=:experiencedPostCoitalBleeding, observation=:observation,"
			+ "modifiedBy=:modifiedBy, processed=:processed where "
			+ " beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public int updateCancerGynecologicalExamination(@Param("providerServiceMapID") Integer providerServiceMapID,
			@Param("appearanceOfCervix") String appearanceOfCervix, 
			@Param("typeOfLesion") String typeOfLesion,
			@Param("vulvalInvolvement") Boolean vulvalInvolvement, 
			@Param("vaginalInvolvement") Boolean vaginalInvolvement,
			@Param("uterus_Normal") Boolean uterus_Normal, 
			@Param("sufferedFromRTIOrSTI") Boolean sufferedFromRTIOrSTI,
			@Param("rTIOrSTIDetail") String rTIOrSTIDetail, 
			@Param("filePath") String filePath,
			@Param("experiencedPostCoitalBleeding") Boolean experiencedPostCoitalBleeding, 
			@Param("observation") String observation,
			@Param("modifiedBy") String modifiedBy,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("processed") String processed);
}
