/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.doctor;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.doctor.CancerAbdominalExamination;

@Repository
@RestResource(exported = false)
public interface CancerAbdominalExaminationRepo extends CrudRepository<CancerAbdominalExamination, Long> {

	@Query(" SELECT c from CancerAbdominalExamination c WHERE c.beneficiaryRegID = :benRegID AND c.deleted = false AND c.visitCode = :visitCode")
	public CancerAbdominalExamination getBenCancerAbdominalExaminationDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Query("SELECT processed from CancerAbdominalExamination where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getCancerAbdominalExaminationStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Transactional
	@Modifying
	@Query("update CancerAbdominalExamination set providerServiceMapID=:providerServiceMapID, abdominalInspection_Normal=:abdominalInspection_Normal, "
			+ "liver=:liver, ascites_Present=:ascites_Present, anyOtherMass_Present=:anyOtherMass_Present,"
			+ "lymphNodes_Enlarged=:lymphNodes_Enlarged, lymphNode_Inguinal_Left=:lymphNode_Inguinal_Left, "
			+ "lymphNode_Inguinal_Right=:lymphNode_Inguinal_Right, lymphNode_ExternalIliac_Left=:lymphNode_ExternalIliac_Left,"
			+ "lymphNode_ExternalIliac_Right=:lymphNode_ExternalIliac_Right,lymphNode_ParaAortic_Left=:lymphNode_ParaAortic_Left,"
			+ "lymphNode_ParaAortic_Right=:lymphNode_ParaAortic_Right, observation=:observation,"
			+ "modifiedBy=:modifiedBy, processed=:processed where "
			+ " beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public int updateCancerAbdominalExamination(@Param("providerServiceMapID") Integer providerServiceMapID,
			@Param("abdominalInspection_Normal") Boolean abdominalInspection_Normal, 
			@Param("liver") String liver,
			@Param("ascites_Present") Boolean ascites_Present, 
			@Param("anyOtherMass_Present") Boolean anyOtherMass_Present,
			@Param("lymphNodes_Enlarged") Boolean lymphNodes_Enlarged, 
			@Param("lymphNode_Inguinal_Left") Boolean lymphNode_Inguinal_Left,
			@Param("lymphNode_Inguinal_Right") Boolean lymphNode_Inguinal_Right, 
			@Param("lymphNode_ExternalIliac_Left") Boolean lymphNode_ExternalIliac_Left,
			@Param("lymphNode_ExternalIliac_Right") Boolean lymphNode_ExternalIliac_Right, 
			@Param("lymphNode_ParaAortic_Left") Boolean lymphNode_ParaAortic_Left,
			@Param("lymphNode_ParaAortic_Right") Boolean lymphNode_ParaAortic_Right,
			@Param("observation") String observation, 
			@Param("modifiedBy") String modifiedBy,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("processed") String processed);
	
	
}
