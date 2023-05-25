package com.iemr.mmu.repo.nurse;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.nurse.BenAnthropometryDetail;

@Repository
@RestResource(exported = false)
public interface BenAnthropometryRepo extends CrudRepository<BenAnthropometryDetail, Long> {

	@Query("select a from BenAnthropometryDetail a where a.beneficiaryRegID = :beneficiaryRegID and  a.visitCode = :visitCode")
	public BenAnthropometryDetail getBenAnthropometryDetail(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT processed from BenAnthropometryDetail where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getBenAnthropometryStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update BenAnthropometryDetail set weight_Kg=:weight_Kg, height_cm=:height_cm, bMI=:bMI, headCircumference_cm=:headCircumference_cm,"
			+ " midUpperArmCircumference_MUAC_cm=:midUpperArmCircumference_MUAC_cm, hipCircumference_cm=:hipCircumference_cm, "
			+ " waistCircumference_cm=:waistCircumference_cm, waistHipRatio=:waistHipRatio, "
			+ " modifiedBy=:modifiedBy, processed=:processed where beneficiaryRegID=:beneficiaryRegID AND visitCode=:visitCode")
	public int updateANCCareDetails(@Param("weight_Kg") Double weight_Kg, @Param("height_cm") Double height_cm,
			@Param("bMI") Double bMI, @Param("headCircumference_cm") Double headCircumference_cm,
			@Param("midUpperArmCircumference_MUAC_cm") Double midUpperArmCircumference_MUAC_cm,
			@Param("hipCircumference_cm") Double hipCircumference_cm,
			@Param("waistCircumference_cm") Double waistCircumference_cm, @Param("waistHipRatio") Double waistHipRatio,
			@Param("modifiedBy") String modifiedBy, @Param("processed") String processed,
			@Param("beneficiaryRegID") Long beneficiaryRegID, @Param("visitCode") Long visitCode);

	@Query("select a.weight_Kg, Date(a.createdDate) from BenAnthropometryDetail a "
			+ " where a.visitCode in :visitCodeList ")
	public ArrayList<Object[]> getBenAnthropometryDetailForGraphtrends(
			@Param("visitCodeList") ArrayList<Long> visitCodeList);

	@Query(nativeQuery = true, value = " SELECT height_cm FROM t_phy_anthropometry "
			+ " WHERE beneficiaryRegID=:benRegID ORDER By ID DESC LIMIT 1 ")
	public Double getBenLatestHeight(@Param("benRegID") Long benRegID);

}
