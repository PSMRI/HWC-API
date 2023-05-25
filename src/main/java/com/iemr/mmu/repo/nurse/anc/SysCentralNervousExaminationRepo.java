package com.iemr.mmu.repo.nurse.anc;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.anc.SysCentralNervousExamination;

@Repository
@RestResource(exported = false)
public interface SysCentralNervousExaminationRepo extends CrudRepository<SysCentralNervousExamination, Long> {

	@Query(" SELECT u FROM SysCentralNervousExamination u WHERE u.beneficiaryRegID = :benRegID AND u.visitCode = :visitCode")
	public SysCentralNervousExamination getSysCentralNervousExaminationData(@Param("benRegID") Long benRegID,
			 @Param("visitCode") Long visitCode);
	
	@Query("SELECT processed from SysCentralNervousExamination where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getBenCentralNervousExaminationStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Transactional
	@Modifying
	@Query("UPDATE SysCentralNervousExamination set handedness=:handedness, cranialNervesExamination=:cranialNervesExamination, "
			+ "motorSystem=:motorSystem, sensorySystem=:sensorySystem, autonomicSystem =:autonomicSystem, "
			+ "cerebellarSigns=:cerebellarSigns, signsOfMeningealIrritation=:signsOfMeningealIrritation, skull =:skull, "
			+ "modifiedBy=:modifiedBy, processed=:processed "
			+ "where beneficiaryRegID=:benRegID and visitCode = :visitCode ")
	public int updateSysCentralNervousExamination(
			@Param("handedness") String handedness,
			@Param("cranialNervesExamination") String cranialNervesExamination,
			@Param("motorSystem") String motorSystem,
			@Param("sensorySystem") String sensorySystem,
			@Param("autonomicSystem") String autonomicSystem,
			@Param("cerebellarSigns") String cerebellarSigns,
			@Param("signsOfMeningealIrritation") String signsOfMeningealIrritation,
			@Param("skull") String skull,
			@Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed,
			@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
}
