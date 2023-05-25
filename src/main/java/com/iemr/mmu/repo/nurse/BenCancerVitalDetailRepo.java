package com.iemr.mmu.repo.nurse;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.nurse.BenCancerVitalDetail;

@Repository
@RestResource(exported = false)
public interface BenCancerVitalDetailRepo extends CrudRepository<BenCancerVitalDetail, Long> {

	@Transactional
	@Modifying
	@Query("update BenCancerVitalDetail set providerServiceMapID=:providerServiceMapID, weight_Kg=:weight_Kg, height_cm=:height_cm, "
			+ "waistCircumference_cm=:waistCircumference_cm, bloodGlucose_Fasting=:bloodGlucose_Fasting,"
			+ " bloodGlucose_Random=:bloodGlucose_Random, bloodGlucose_2HrPostPrandial=:bloodGlucose_2HrPostPrandial, systolicBP_1stReading=:systolicBP_1stReading, "
			+ "diastolicBP_1stReading=:diastolicBP_1stReading, systolicBP_2ndReading=:systolicBP_2ndReading, diastolicBP_2ndReading=:diastolicBP_2ndReading,"
			+ " systolicBP_3rdReading=:systolicBP_3rdReading, diastolicBP_3rdReading=:diastolicBP_3rdReading, hbA1C=:hbA1C, hemoglobin=:hemoglobin, "
			+ " modifiedBy=:modifiedBy, processed=:processed, rbsTestResult=:rbsTestResult, rbsTestRemarks=:rbsTestRemarks, sPO2=:sPO2 where  "
			+ "  beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public int updateBenCancerVitalDetail(@Param("providerServiceMapID") Integer providerServiceMapID,
			@Param("weight_Kg") Double weight_Kg, @Param("height_cm") Double height_cm,
			@Param("waistCircumference_cm") Double waistCircumference_cm,
			@Param("bloodGlucose_Fasting") Short bloodGlucose_Fasting,
			@Param("bloodGlucose_Random") Short bloodGlucose_Random,
			@Param("bloodGlucose_2HrPostPrandial") Short bloodGlucose_2HrPostPrandial,
			@Param("systolicBP_1stReading") Short systolicBP_1stReading,
			@Param("diastolicBP_1stReading") Short diastolicBP_1stReading,
			@Param("systolicBP_2ndReading") Short systolicBP_2ndReading,
			@Param("diastolicBP_2ndReading") Short diastolicBP_2ndReading,
			@Param("systolicBP_3rdReading") Short systolicBP_3rdReading,
			@Param("diastolicBP_3rdReading") Short diastolicBP_3rdReading, @Param("hbA1C") Short hbA1C,
			@Param("hemoglobin") Double hemoglobin, @Param("modifiedBy") String modifiedBy,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode, @Param("processed") String processed,
			@Param("rbsTestResult") String rbsTestResult,
			@Param("rbsTestRemarks") String rbsTestRemarks,@Param("sPO2") String sPO2);

	@Query(" SELECT bvd from BenCancerVitalDetail bvd WHERE bvd.beneficiaryRegID = :benRegID AND bvd.deleted = false AND bvd.visitCode = :visitCode")
	public BenCancerVitalDetail getBenCancerVitalDetail(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Deprecated
	@Query(" SELECT bvd from BenCancerVitalDetail bvd WHERE bvd.beneficiaryRegID = :benRegID AND bvd.benVisitID = :benVisitID "
			+ " AND DATE(bvd.createdDate) = :createdDate")
	public BenCancerVitalDetail getBenCancerVitalDetail(@Param("benRegID") Long benRegID,
			@Param("benVisitID") Long benVisitID, @Param("createdDate") Date createdDate);

	@Query("SELECT processed from BenCancerVitalDetail where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getCancerVitalStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Query(" SELECT bvd from BenCancerVitalDetail bvd WHERE bvd.visitCode IN :visitCodeList ")
	public ArrayList<BenCancerVitalDetail> getBenCancerVitalDetailForGraph(
			@Param("visitCodeList") ArrayList<Long> visitCodeList);

}
