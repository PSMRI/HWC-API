/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.nurse.BenPhysicalVitalDetail;

@Repository
@RestResource(exported = false)
public interface BenPhysicalVitalRepo extends CrudRepository<BenPhysicalVitalDetail, Long> {

	@Query("select v from BenPhysicalVitalDetail v where v.beneficiaryRegID = :beneficiaryRegID and v.visitCode = :visitCode")
	public BenPhysicalVitalDetail getBenPhysicalVitalDetail(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT processed from BenPhysicalVitalDetail where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getBenPhysicalVitalStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update BenPhysicalVitalDetail set temperature=:temperature, pulseRate=:pulseRate, respiratoryRate=:respiratoryRate, diastolicBP_1stReading=:diastolicBP_1stReading, diastolicBP_2ndReading=:diastolicBP_2ndReading, diastolicBP_3rdReading=:diastolicBP_3rdReading, "
			+ " systolicBP_1stReading=:systolicBP_1stReading, systolicBP_2ndReading=:systolicBP_2ndReading, systolicBP_3rdReading=:systolicBP_3rdReading, averageSystolicBP=:averageSystolicBP, averageDiastolicBP=:averageDiastolicBP, capillaryRefillTime=:capillaryRefillTime, "
			+ " bloodPressureStatusID=:bloodPressureStatusID, bloodPressureStatus=:bloodPressureStatus, bloodGlucose_Fasting=:bloodGlucose_Fasting, bloodGlucose_Random=:bloodGlucose_Random, bloodGlucose_2hr_PP=:bloodGlucose_2hr_PP, bloodGlucose_NotSpecified=:bloodGlucose_NotSpecified, diabeticStatusID=:diabeticStatusID, diabeticStatus=:diabeticStatus, "
			+ " modifiedBy=:modifiedBy, processed=:processed , rbsTestResult=:rbsTestResult, rbsTestRemarks=:rbsTestRemarks, sPO2=:sPO2, hemoglobin=:hemoglobin  "
			+ " where beneficiaryRegID=:beneficiaryRegID AND visitCode=:visitCode")
	public int updatePhysicalVitalDetails(@Param("temperature") Double temperature, @Param("pulseRate") Short pulseRate,
			@Param("respiratoryRate") Short respiratoryRate,
			@Param("systolicBP_1stReading") Short systolicBP_1stReading,
			@Param("diastolicBP_1stReading") Short diastolicBP_1stReading,
			@Param("systolicBP_2ndReading") Short systolicBP_2ndReading,
			@Param("diastolicBP_2ndReading") Short diastolicBP_2ndReading,
			@Param("systolicBP_3rdReading") Short systolicBP_3rdReading,
			@Param("diastolicBP_3rdReading") Short diastolicBP_3rdReading,
			@Param("averageSystolicBP") Short averageSystolicBP, @Param("averageDiastolicBP") Short averageDiastolicBP,

			@Param("bloodPressureStatusID") Short bloodPressureStatusID,
			@Param("bloodPressureStatus") String bloodPressureStatus,

			@Param("bloodGlucose_Fasting") Short bloodGlucose_Fasting,
			@Param("bloodGlucose_Random") Short bloodGlucose_Random,
			@Param("bloodGlucose_2hr_PP") Short bloodGlucose_2hr_PP,
			@Param("bloodGlucose_NotSpecified") Short bloodGlucose_NotSpecified,

			@Param("diabeticStatusID") Short diabeticStatusID, @Param("diabeticStatus") String diabeticStatus,

			@Param("capillaryRefillTime") String capillaryRefillTime, @Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed, @Param("rbsTestResult") String rbsTestResult,
			@Param("rbsTestRemarks") String rbsTestRemarks, @Param("sPO2") String sPO2,
			@Param("beneficiaryRegID") Long beneficiaryRegID, @Param("visitCode") Long visitCode,
			@Param("hemoglobin") Double hemoglobin);

	/**
	 * Vital data for graphical trends.(BP - blood pressure & BG - blood glucose)
	 */

	@Query("select v.averageSystolicBP, v.averageDiastolicBP, v.bloodGlucose_Fasting, v.bloodGlucose_Random, "
			+ " v.bloodGlucose_2hr_PP, Date(v.createdDate) from BenPhysicalVitalDetail v where v.visitCode IN :visitCodeList")
	public ArrayList<Object[]> getBenPhysicalVitalDetailForGraphTrends(
			@Param("visitCodeList") ArrayList<Long> visitCodeList);

	@Transactional
	@Modifying
	@Query("update BenPhysicalVitalDetail set rbsTestResult=:rbsTestResult, rbsTestRemarks=:rbsTestRemarks "
			+ " where beneficiaryRegID = :beneficiaryRegID AND visitCode = :visitCode")
	public int updatePhysicalVitalDetailsQCDoctor(@Param("rbsTestResult") String rbsTestResult,
			@Param("rbsTestRemarks") String rbsTestRemarks, @Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

}
