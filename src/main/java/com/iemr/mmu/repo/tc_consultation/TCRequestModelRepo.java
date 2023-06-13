/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.tc_consultation;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.tele_consultation.TCRequestModel;

@Repository
@RestResource(exported = false)
public interface TCRequestModelRepo extends CrudRepository<TCRequestModel, Long> {
	@Transactional
	@Modifying
	@Query(" UPDATE TCRequestModel t SET t.status = :statusFlag, t.modifiedBy = :modifiedBy, "
			+ " t.deleted = :deleted, t.beneficiaryArrivalTime = IFNULL(t.beneficiaryArrivalTime, now()) "
			+ " WHERE t.beneficiaryRegID = :benRegID AND t.visitCode = :visitCode "
			+ " AND t.deleted is false AND t.userID = :userID AND t.status IN ('N', 'A', 'O') ")
	public int updateBeneficiaryStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("statusFlag") String statusFlag, @Param("modifiedBy") String modifiedBy,
			@Param("userID") Integer userID, @Param("deleted") Boolean deleted);

	@Transactional
	@Modifying
	@Query(" UPDATE TCRequestModel t SET t.status = :statusFlag, t.modifiedBy = :modifiedBy, t.deleted = :deleted "
			+ " WHERE t.beneficiaryRegID = :benRegID AND t.visitCode = :visitCode "
			+ " AND t.deleted is false AND t.userID = :userID AND t.status IN ('N', 'A', 'O') ")
	public int updateBeneficiaryStatusCancel(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("statusFlag") String statusFlag, @Param("modifiedBy") String modifiedBy,
			@Param("userID") Integer userID, @Param("deleted") Boolean deleted);

	@Query(" SELECT t from TCRequestModel t WHERE t.beneficiaryRegID = :benRegID AND t.visitCode = :visitCode "
			+ " AND t.deleted is false AND t.userID = :userID AND t.status IN ('A', 'O', 'D') ")
	public ArrayList<TCRequestModel> checkBenTcStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode, @Param("userID") Integer userID);

	@Query(" SELECT a FROM TCRequestModel a WHERE a.beneficiaryRegID = :benRegID AND a.visitCode = :visitCode "
			+ " AND a.userID = :userID AND a.deleted is false AND a.status IN :statusSet ")
	public ArrayList<TCRequestModel> getTcDetailsList(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode, @Param("userID") Integer userID,
			@Param("statusSet") Set<String> statusSet);

	@Query(value = " SELECT SMSTypeID FROM db_iemr.m_smstype " + " WHERE SMSType = :smsType ", nativeQuery = true)
	public Integer getSMSTypeID(@Param("smsType") String smsType);

	@Query(value = " SELECT SMSTemplateID FROM db_iemr.m_smstemplate "
			+ " WHERE SMSTypeID = :smsTypeID AND deleted<>true ", nativeQuery = true)
	public ArrayList<Integer> getSMSTemplateID(@Param("smsTypeID") Integer smsTypeID);

	// @Query(value = " SELECT Specialization FROM db_iemr.m_specialization "
	// + " WHERE SpecializationID = :specializationID ", nativeQuery = true)
	// public String getSpecializationDetail(@Param("specializationID") Integer
	// specializationID);

	@Query(" SELECT t FROM TCRequestModel t WHERE t.beneficiaryRegID = :benRegID AND t.visitCode = :visitCode "
			+ " AND t.deleted is false AND t.userID = :userID AND t.status IN ('N', 'A', 'O') ")
	public TCRequestModel getSpecializationID(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("userID") Integer userID);

	@Transactional
	@Modifying
	@Query("UPDATE TCRequestModel t SET t.status = :status WHERE t.beneficiaryRegID = :benRegID "
			+ " AND t.visitCode = :visitCode AND t.deleted is false ")
	public int updateStatusIfConsultationCompleted(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("status") String status);

	@Transactional
	@Modifying
	@Query("UPDATE TCRequestModel t SET t.consultation_FirstStart = IFNULL(t.consultation_FirstStart, now())"
			+ " WHERE t.beneficiaryRegID = :benRegID "
			+ " AND t.visitCode = :visitCode AND t.deleted is false ")
	public Integer updateStartConsultationTime(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

}
