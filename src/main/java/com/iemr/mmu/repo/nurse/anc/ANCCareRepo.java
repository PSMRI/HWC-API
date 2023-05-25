package com.iemr.mmu.repo.nurse.anc;

import java.sql.Date;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.anc.ANCCareDetails;

@Repository
@RestResource(exported = false)
public interface ANCCareRepo extends CrudRepository<ANCCareDetails, Long> {

	/*
	 * @Query(" SELECT ID, beneficiaryRegID, benVisitID, providerServiceMapID, visitNo, comolaintType, duration, description, aNCRegistrationDate,"
	 * +
	 * " aNCVisitNumber, lastMenstrualPeriod_LMP, gestationalAgeOrPeriodofAmenorrhea_POA, trimesterNumber, expectedDateofDelivery, "
	 * +
	 * "primiGravida, obstetricFormula, gravida_G, termDeliveries_T, pretermDeliveries_P, abortions_A, livebirths_L, bloodGroup "
	 * +
	 * "from ANCCareDetails ba WHERE ba.beneficiaryRegID = :benRegID AND ba.benVisitID = :benVisitID "
	 * ) public ArrayList<Object[]> getANCCareDetails(@Param("benRegID") Long
	 * benRegID,
	 * 
	 * @Param("benVisitID") Long benVisitID);
	 */

	@Query(" SELECT ID, beneficiaryRegID, benVisitID, providerServiceMapID, visitCode, visitNo, comolaintType, duration, description, "
			+ " lastMenstrualPeriod_LMP, gestationalAgeOrPeriodofAmenorrhea_POA, trimesterNumber, expectedDateofDelivery, "
			+ "primiGravida, gravida_G, termDeliveries_T, pretermDeliveries_P, abortions_A, livebirths_L, bloodGroup, stillBirth,para  "
			+ "from ANCCareDetails ba WHERE ba.beneficiaryRegID = :benRegID AND visitCode = :visitCode")
	public ArrayList<Object[]> getANCCareDetails(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Query(" SELECT processed from ANCCareDetails where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getBenANCCareDetailsStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

//	@Transactional
//	@Modifying
//	@Query("update ANCCareDetails set comolaintType=:comolaintType, duration=:duration, description=:description, "
//			+ "lastMenstrualPeriod_LMP=:lastMenstrualPeriod_LMP, "
//			+ "gestationalAgeOrPeriodofAmenorrhea_POA=:gestationalAgeOrPeriodofAmenorrhea_POA,"
//			+ "trimesterNumber=:trimesterNumber,  expectedDateofDelivery=:expectedDateofDelivery, primiGravida=:primiGravida, "
//			+ "gravida_G=:gravida_G, termDeliveries_T=:termDeliveries_T, pretermDeliveries_P=:pretermDeliveries_P,"
//			+ "abortions_A=:abortions_A,livebirths_L=:livebirths_L, bloodGroup=:bloodGroup, modifiedBy=:modifiedBy, processed=:processed, "
//			+ " stillBirth =:stillBirth " + " where visitCode=:visitCode AND beneficiaryRegID=:beneficiaryRegID")
//	public int updateANCCareDetails(@Param("comolaintType") String comolaintType, @Param("duration") String duration,
//			@Param("description") String description, @Param("lastMenstrualPeriod_LMP") Date lastMenstrualPeriod_LMP,
//			@Param("gestationalAgeOrPeriodofAmenorrhea_POA") Short gestationalAgeOrPeriodofAmenorrhea_POA,
//			@Param("trimesterNumber") Short trimesterNumber,
//			@Param("expectedDateofDelivery") Date expectedDateofDelivery, @Param("primiGravida") Boolean primiGravida,
//			@Param("gravida_G") Short gravida_G, @Param("termDeliveries_T") Short termDeliveries_T,
//			@Param("pretermDeliveries_P") Short pretermDeliveries_P, @Param("abortions_A") Short abortions_A,
//			@Param("livebirths_L") Short livebirths_L, @Param("bloodGroup") String bloodGroup,
//			@Param("modifiedBy") String modifiedBy, @Param("processed") String processed,
//			@Param("beneficiaryRegID") Long beneficiaryRegID, @Param("visitCode") Long visitCode,
//			@Param("stillBirth") Integer stillBirth);

	@Transactional
	@Modifying
	@Query("update ANCCareDetails set comolaintType=:comolaintType, duration=:duration, description=:description, "
			+ "lastMenstrualPeriod_LMP=:lastMenstrualPeriod_LMP, "
			+ "gestationalAgeOrPeriodofAmenorrhea_POA=:gestationalAgeOrPeriodofAmenorrhea_POA,"
			+ "trimesterNumber=:trimesterNumber,  expectedDateofDelivery=:expectedDateofDelivery, primiGravida=:primiGravida, "
			+ "gravida_G=:gravida_G, termDeliveries_T=:termDeliveries_T, pretermDeliveries_P=:pretermDeliveries_P,"
			+ "abortions_A=:abortions_A,livebirths_L=:livebirths_L, bloodGroup=:bloodGroup, modifiedBy=:modifiedBy, processed=:processed, "
			+ " stillBirth =:stillBirth, para=:para "
			+ " where visitCode=:visitCode AND beneficiaryRegID=:beneficiaryRegID")
	public int updateANCCareDetails(@Param("comolaintType") String comolaintType, @Param("duration") String duration,
			@Param("description") String description, @Param("lastMenstrualPeriod_LMP") Date lastMenstrualPeriod_LMP,
			@Param("gestationalAgeOrPeriodofAmenorrhea_POA") Short gestationalAgeOrPeriodofAmenorrhea_POA,
			@Param("trimesterNumber") Short trimesterNumber,
			@Param("expectedDateofDelivery") Date expectedDateofDelivery, @Param("primiGravida") Boolean primiGravida,
			@Param("gravida_G") Short gravida_G, @Param("termDeliveries_T") Short termDeliveries_T,
			@Param("pretermDeliveries_P") Short pretermDeliveries_P, @Param("abortions_A") Short abortions_A,
			@Param("livebirths_L") Short livebirths_L, @Param("bloodGroup") String bloodGroup,
			@Param("modifiedBy") String modifiedBy, @Param("processed") String processed,
			@Param("beneficiaryRegID") Long beneficiaryRegID, @Param("visitCode") Long visitCode,
			@Param("stillBirth") Integer stillBirth, @Param("para") Short para);

	@Query(nativeQuery = true, value = " SELECT t.bloodGroup from t_anccare t "
			+ " where t.beneficiaryRegID=:benRegID ORDER BY t.lastModDate DESC limit 1 ")
	public String getBenANCCareDetailsStatus(@Param("benRegID") Long benRegID);

	@Query(" SELECT a FROM ANCCareDetails a WHERE a.beneficiaryRegID = :benRegID "
			+ " AND (a.gravida_G > 5  OR a.pretermDeliveries_P > 0 OR a.abortions_A > 0 OR a.stillBirth > 0 "
			+ "  OR (a.bloodGroup = 'A-Ve' OR a.bloodGroup = 'B-Ve' OR a.bloodGroup = 'O-Ve' OR a.bloodGroup = 'AB-Ve'))"
			+ " AND a.deleted is false ")
	public ArrayList<ANCCareDetails> getANCCareDataForHRP(@Param("benRegID") Long benRegID);
}
