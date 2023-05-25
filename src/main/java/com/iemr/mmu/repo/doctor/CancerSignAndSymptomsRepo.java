package com.iemr.mmu.repo.doctor;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.doctor.CancerOralExamination;
import com.iemr.mmu.data.doctor.CancerSignAndSymptoms;
@Repository
@RestResource(exported = false)
public interface CancerSignAndSymptomsRepo extends CrudRepository<CancerSignAndSymptoms, Long>{
	
	@Query(" SELECT c from CancerSignAndSymptoms c WHERE c.beneficiaryRegID = :benRegID "
			+ "AND c.deleted = false AND c.visitCode = :visitCode")
	public CancerSignAndSymptoms getBenCancerSignAndSymptomsDetails(@Param("benRegID") Long benRegID,
	 @Param("visitCode") Long visitCode);
	
	@Query("SELECT processed from CancerSignAndSymptoms where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted=false")
	public String getCancerSignAndSymptomsStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
	@Transactional
	@Modifying
	@Query("update CancerSignAndSymptoms set providerServiceMapID=:providerServiceMapID, shortnessOfBreath=:shortnessOfBreath, "
			+ "coughgt2Weeks=:coughgt2Weeks, bloodInSputum=:bloodInSputum, difficultyInOpeningMouth=:difficultyInOpeningMouth, "
			+ "nonHealingUlcerOrPatchOrGrowth=:nonHealingUlcerOrPatchOrGrowth, changeInTheToneOfVoice=:changeInTheToneOfVoice,"
			+ "lumpInTheBreast=:lumpInTheBreast, bloodStainedDischargeFromNipple=:bloodStainedDischargeFromNipple, "
			+ " changeInShapeAndSizeOfBreasts=:changeInShapeAndSizeOfBreasts, vaginalBleedingBetweenPeriods=:vaginalBleedingBetweenPeriods, "
			+ "vaginalBleedingAfterMenopause=:vaginalBleedingAfterMenopause, vaginalBleedingAfterIntercourse=:vaginalBleedingAfterIntercourse,"
			+ " foulSmellingVaginalDischarge=:foulSmellingVaginalDischarge, breastEnlargement=:breastEnlargement, "
			+ "lymphNode_Enlarged=:lymphNode_Enlarged, observation =:observation, modifiedBy=:modifiedBy, processed=:processed where "
			+ " beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public int updateCancerSignAndSymptoms(@Param("providerServiceMapID") Integer providerServiceMapID,
			@Param("shortnessOfBreath") Boolean shortnessOfBreath, 
			@Param("coughgt2Weeks") Boolean coughgt2Weeks,
			@Param("bloodInSputum") Boolean bloodInSputum, @Param("difficultyInOpeningMouth") Boolean difficultyInOpeningMouth,
			@Param("nonHealingUlcerOrPatchOrGrowth") Boolean nonHealingUlcerOrPatchOrGrowth, 
			@Param("changeInTheToneOfVoice") Boolean changeInTheToneOfVoice,
			@Param("lumpInTheBreast") Boolean lumpInTheBreast,
			@Param("bloodStainedDischargeFromNipple") Boolean bloodStainedDischargeFromNipple,
			@Param("changeInShapeAndSizeOfBreasts") Boolean changeInShapeAndSizeOfBreasts,
			@Param("vaginalBleedingBetweenPeriods") Boolean vaginalBleedingBetweenPeriods,
			@Param("vaginalBleedingAfterMenopause") Boolean vaginalBleedingAfterMenopause,
			@Param("vaginalBleedingAfterIntercourse") Boolean vaginalBleedingAfterIntercourse,
			@Param("foulSmellingVaginalDischarge") Boolean foulSmellingVaginalDischarge,
			@Param("breastEnlargement") Boolean breastEnlargement, 
			@Param("lymphNode_Enlarged") Boolean lymphNode_Enlarged, 
			@Param("observation") String observation,
			@Param("modifiedBy") String modifiedBy,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("processed") String processed);
	
}
