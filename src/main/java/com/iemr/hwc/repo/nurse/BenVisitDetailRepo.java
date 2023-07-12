/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.hwc.repo.nurse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;

@Repository
@RestResource(exported = false)
public interface BenVisitDetailRepo extends CrudRepository<BeneficiaryVisitDetail, Long> {

	@Transactional
	@Modifying
	@Query("update BeneficiaryVisitDetail set visitReasonID=:visitReasonID, visitReason=:visitReason, visitCategoryID=:visitCategoryID, "
			+ " visitCategory=:visitCategory, pregnancyStatus=:pregnancyStatus, "
			+ "rCHID=:rCHID, healthFacilityType=:healthFacilityType, healthFacilityLocation=:healthFacilityLocation "
			+ ", modifiedBy=:modifiedBy where benVisitID=:benVisitID")
	public int updateBeneficiaryVisitDetail(@Param("visitReasonID") Short visitReasonID,
			@Param("visitReason") String visitReason, @Param("visitCategoryID") Integer visitCategoryID,
			@Param("visitCategory") String visitCategory, @Param("pregnancyStatus") String pregnancyStatus,
			@Param("rCHID") String rCHID, @Param("healthFacilityType") String healthFacilityType,
			@Param("healthFacilityLocation") String healthFacilityLocation,
			// @Param("reportFilePath") String reportFilePath,
			@Param("modifiedBy") String modifiedBy, @Param("benVisitID") Long benVisitID);

	@Query(" SELECT bvd from BeneficiaryVisitDetail bvd WHERE bvd.beneficiaryRegID = :benRegID AND bvd.visitCode = :visitCode")
	public BeneficiaryVisitDetail getVisitDetails(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);
	
	@Query(" SELECT bvd from BeneficiaryVisitDetail bvd WHERE bvd.beneficiaryRegID = :benRegID AND bvd.visitCode = :visitCode")
	public BeneficiaryVisitDetail getSubVisitCategory(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Query(" SELECT bvd from BeneficiaryVisitDetail bvd WHERE bvd.beneficiaryRegID = :benRegID and DATE(CreatedDate)<curdate()")
	public List<BeneficiaryVisitDetail> getBeneficiaryVisitHistory(@Param("benRegID") Long benRegID);

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryVisitDetail set visitFlowStatusFlag = :visitFlowStatusFlag, lastModDate = curdate() where benVisitID = :benVisitID ")
	public Integer updateBenFlowStatus(@Param("visitFlowStatusFlag") String visitFlowStatusFlag,
			@Param("benVisitID") Long benVisitID);

	@Query(" SELECT bvd.benVisitID, bvd.beneficiaryRegID, bvd.providerServiceMapID, bvd.visitDateTime, bvd.visitNo, bvd.visitReasonID, bvd.visitReason, "
			+ "bvd.visitCategoryID, bvd.visitCategory, bvd.pregnancyStatus, bvd.rCHID, bvd.healthFacilityType, bvd.healthFacilityLocation, "
			+ "bvd.reportFilePath,sp.serviceProviderName from BeneficiaryVisitDetail bvd "
			+ "INNER JOIN bvd.providerServiceMapping p " + "INNER JOIN p.serviceProvider sp "
			+ "WHERE bvd.beneficiaryRegID = :benRegID AND bvd.visitCode = :visitCode ")
	public List<Objects[]> getBeneficiaryVisitDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Query(" SELECT COUNT(benVisitID) FROM BeneficiaryVisitDetail WHERE beneficiaryRegID = :benRegID GROUP BY beneficiaryRegID ")
	public Short getVisitCountForBeneficiary(@Param("benRegID") Long benRegID);

	@Query(nativeQuery = true, value = " SELECT v.benVisitID, v.visitCategory, v.visitCode FROM t_benvisitdetail v "
			+ " WHERE v.beneficiaryRegID = :benRegID "
			+ " AND v.visitCategory IS NOT NULL ORDER BY v.createdDate DESC limit 6 ")
	public ArrayList<Object[]> getLastSixVisitDetailsForBeneficiary(@Param("benRegID") Long benRegID);

	// updating record with visitcode.
	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryVisitDetail set visitCode = :visitCode where benVisitID = :benVisitID ")
	public Integer updateVisitCode(@Param("visitCode") Long visitCode, @Param("benVisitID") Long benVisitID);
	
	//updating subVisitCategory
	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryVisitDetail set subVisitCategory = :subVisitCategory where visitCode = :visitCode ")
	public Integer updateSubVisitCategory(@Param("subVisitCategory") String subVisitCategory, @Param("visitCode") Long visitCode);


	// get file uuid from file id
	@Query(nativeQuery = true, value = " SELECT FileUID from t_kmfilemanager where KmFileManagerID = :fileID ")
	public String getFileUUID(@Param("fileID") int fileID);

	// get file uuid from file id
	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryVisitDetail set reportFilePath = concat(IFNULL(reportFilePath, ''),IFNULL(:fileIDs, '')) "
			+ " WHERE beneficiaryRegID = :regID AND visitCode = :visitCode ")
	public int updateFileID(@Param("fileIDs") String fileIDs, @Param("regID") Long regID,
			@Param("visitCode") Long visitCode);

	@Query(nativeQuery = true, value = " SELECT v.visitCode FROM t_benvisitdetail v "
			+ " WHERE v.beneficiaryRegID = :benRegID AND v.visitCategory =:vc ORDER BY BenVisitID DESC LIMIT 1 ")
	public Long getLatestVisitCodeForGivenVC(@Param("benRegID") Long benRegID, @Param("vc") String vc);

	@Query("SELECT MAX(bvd.createdDate) from BeneficiaryVisitDetail bvd WHERE bvd.beneficiaryRegID = :benRegID AND bvd.visitReason = :visitreason AND bvd.visitCategory = :visitcategory ")
    public String getMaxCreatedDate(@Param("benRegID") Long benRegID, @Param("visitreason") String visitreason,@Param("visitcategory") String visitcategory);

}
