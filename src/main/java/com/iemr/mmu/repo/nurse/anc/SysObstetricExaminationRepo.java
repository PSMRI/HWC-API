/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.anc;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.anc.SysObstetricExamination;

@Repository
@RestResource(exported = false)
public interface SysObstetricExaminationRepo extends CrudRepository<SysObstetricExamination, Long> {

	@Query(" SELECT u FROM SysObstetricExamination u WHERE u.beneficiaryRegID = :benRegID AND u.visitCode = :visitCode")
	public SysObstetricExamination getSysObstetricExaminationData(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT processed from SysObstetricExamination where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getBenObstetricExaminationStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

//	@Transactional
//	@Modifying
//	@Query("update SysObstetricExamination set fundalHeight=:fundalHeight, fHAndPOA_Status=:fHAndPOA_Status, fHAndPOA_Interpretation=:fHAndPOA_Interpretation, "
//			+ "fetalMovements=:fetalMovements, fetalHeartSounds=:fetalHeartSounds, fetalHeartRate_BeatsPerMinute=:fetalHeartRate_BeatsPerMinute, "
//			+ "fetalPositionOrLie=:fetalPositionOrLie, fetalPresentation=:fetalPresentation, abdominalScars=:abdominalScars, sfh=:sfh, "
//			+ " modifiedBy=:modifiedBy, "
//			+ "processed=:processed where beneficiaryRegID=:benRegID and visitCode = :visitCode ")
//	public int updateSysObstetricExamination(@Param("fundalHeight") String fundalHeight,
//			@Param("fHAndPOA_Status") String fHAndPOA_Status,
//			@Param("fHAndPOA_Interpretation") String fHAndPOA_Interpretation,
//			@Param("fetalMovements") String fetalMovements, @Param("fetalHeartSounds") String fetalHeartSounds,
//			@Param("fetalHeartRate_BeatsPerMinute") String fetalHeartRate_BeatsPerMinute,
//			@Param("fetalPositionOrLie") String fetalPositionOrLie,
//			@Param("fetalPresentation") String fetalPresentation, @Param("abdominalScars") String abdominalScars,
//			@Param("sfh") Double sfh, @Param("modifiedBy") String modifiedBy, @Param("processed") String processed,
//			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update SysObstetricExamination set fundalHeight=:fundalHeight, fHAndPOA_Status=:fHAndPOA_Status, fHAndPOA_Interpretation=:fHAndPOA_Interpretation, "
			+ "fetalMovements=:fetalMovements, fetalHeartSounds=:fetalHeartSounds, fetalHeartRate_BeatsPerMinute=:fetalHeartRate_BeatsPerMinute, "
			+ "fetalPositionOrLie=:fetalPositionOrLie, fetalPresentation=:fetalPresentation, abdominalScars=:abdominalScars, sfh=:sfh, "
			+ " modifiedBy=:modifiedBy, " + "processed=:processed, "
			+ " malPresentation=:malPresentation, lowLyingPlacenta=:lowLyingPlacenta,vertebralDeformity=:vertebralDeformity, "
			+ " isHRP=:isHRP,reasonsForHRPDB=:reasonsForHRPDB  "
			+ "  where beneficiaryRegID=:benRegID and visitCode = :visitCode ")
	public int updateSysObstetricExamination(@Param("fundalHeight") String fundalHeight,
			@Param("fHAndPOA_Status") String fHAndPOA_Status,
			@Param("fHAndPOA_Interpretation") String fHAndPOA_Interpretation,
			@Param("fetalMovements") String fetalMovements, @Param("fetalHeartSounds") String fetalHeartSounds,
			@Param("fetalHeartRate_BeatsPerMinute") String fetalHeartRate_BeatsPerMinute,
			@Param("fetalPositionOrLie") String fetalPositionOrLie,
			@Param("fetalPresentation") String fetalPresentation, @Param("abdominalScars") String abdominalScars,
			@Param("sfh") Double sfh, @Param("modifiedBy") String modifiedBy, @Param("processed") String processed,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("malPresentation") Boolean malPresentation, @Param("lowLyingPlacenta") Boolean lowLyingPlacenta,
			@Param("vertebralDeformity") Boolean vertebralDeformity, @Param("isHRP") Boolean isHRP,
			@Param("reasonsForHRPDB") String reasonsForHRPDB);

	public SysObstetricExamination findFirstByBeneficiaryRegIDOrderByIDDesc(Long beneficiaryRegId);
}
