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
package com.iemr.hwc.repo.nurse.ncdscreening;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.ncdScreening.IDRSData;

@Repository
@RestResource(exported = false)
public interface IDRSDataRepo extends CrudRepository<IDRSData, Long> {

	@Query(" SELECT id,beneficiaryRegID, benVisitID, providerServiceMapID, idrsQuestionID, idrsScore, question, answer, "
			+ "suspectedDisease, visitCode, diseaseQuestionType, confirmedDisease FROM IDRSData "
			+ " WHERE beneficiaryRegID = :benRegID AND deleted = false AND visitCode = :visitCode")
	public ArrayList<Object[]> getBenIdrsDetail(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Query(value="select a.* from t_idrsDetails a inner join i_ben_flow_outreach b on  a.visitcode=b.beneficiary_visit_code where (b.specialist_flag=9 OR b.doctor_Flag=9)  AND a.beneficiaryRegID = :beneficiaryRegID AND a.createdDate >= :tDate "
			+ " AND (a.diseaseQuestionType "
			+ " IN ('Asthma', 'Malaria Screening', 'Tuberculosis Screening') OR a.diseaseQuestionType is null OR (a.ConfirmedDiseases like '%Asthma%' OR " 
			+ " a.ConfirmedDiseases like '%Malaria Screening%' OR a.ConfirmedDiseases like '%Tuberculosis Screening%' )) "
			+ " ORDER BY createddate DESC, a.visitCode ",nativeQuery=true)
	public ArrayList<IDRSData> getBenIdrsDetailsLast_3_Month(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("tDate") Timestamp tDate);

	@Query(value="select count(a.idrsid) from t_idrsDetails a inner join i_ben_flow_outreach b on a.visitcode=b.beneficiary_visit_code where (b.specialist_flag=9 OR b.doctor_flag=9) and a.BeneficiaryRegID = :beneficiaryRegID and a.confirmedDiseases like '%diabetes%' "
			,nativeQuery=true)
	public Integer isDiabeticCheck(@Param("beneficiaryRegID") Long beneficiaryRegID);

//	@Query( value=" SELECT visitcode,createddate,question,answer,idrsid,idrsquestionid,diseasequestiontype from t_idrsdetails t where t.beneficiaryRegID = :beneficiaryRegID and t.deleted is false and t.DiseaseQuestionType like '%Diabetes%' " + 
//			" ORDER BY t.createddate DESC ",nativeQuery=true)
//	public ArrayList<Object[]> getBenPreviousDiabetesDetails(@Param("beneficiaryRegID") Long beneficiaryRegID);
	@Query("select a from IDRSData a where a.beneficiaryRegID = :beneficiaryRegID AND a.diseaseQuestionType = 'Diabetes' "
			+ " ORDER BY Date(a.createdDate) DESC  ")
	public ArrayList<IDRSData> getBenPreviousDiabetesDetails(@Param("beneficiaryRegID") Long beneficiaryRegID);
	
	@Query( value=" SELECT t.VisitCode,r.CreatedDate,t.SuspectedDiseases from db_iemr.t_idrsdetails t " + 
			" inner join db_iemr.t_benreferdetails r on r.VisitCode = t.VisitCode where t.beneficiaryRegID = :beneficiaryRegID " + 
			" and t.deleted is false  and  t.SuspectedDiseases is not null group by t.visitcode ORDER BY t.createddate DESC ",nativeQuery=true)
	public ArrayList<Object[]> getBenPreviousReferredDetails(@Param("beneficiaryRegID") Long beneficiaryRegID);
	
	@Query(value="select count(a.idrsid) from t_idrsdetails a inner join i_ben_flow_outreach b on a.visitcode=b.beneficiary_visit_code  where (b.specialist_flag=9 OR b.doctor_flag=9) and a.BeneficiaryRegID= :beneficiaryRegID and a.confirmedDiseases like '%vision%' ",nativeQuery=true)
	public Integer isDefectiveVisionCheck(@Param("beneficiaryRegID") Long beneficiaryRegID);
	
	@Query(value="select count(a.idrsid) from t_idrsdetails a inner join i_ben_flow_outreach b on a.visitcode=b.beneficiary_visit_code where (b.specialist_flag=9 OR b.doctor_flag=9) and a.BeneficiaryRegID= :beneficiaryRegID and a.confirmedDiseases like '%epilepsy%' ",nativeQuery=true)
	public Integer isEpilepsyCheck(@Param("beneficiaryRegID") Long beneficiaryRegID);
	
	@Query(value="select count(a.idrsid) from t_idrsdetails a inner join i_ben_flow_outreach b on a.visitcode=b.beneficiary_visit_code  where (b.specialist_flag=9 OR b.doctor_flag=9) and a.BeneficiaryRegID= :beneficiaryRegID and a.confirmedDiseases like '%hypertension%' ",nativeQuery=true)
	public Integer isHypertensionCheck(@Param("beneficiaryRegID") Long beneficiaryRegID);
	@Transactional
	@Modifying
	@Query("UPDATE IDRSData SET idrsScore = :idrsScore WHERE beneficiaryRegID = :beneficiaryRegID AND visitCode = :visitCode")
	public int updateIdrsScore(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode, @Param("idrsScore") Integer idrsScore);
	@Transactional
	@Modifying
	@Query("UPDATE IDRSData SET confirmedDisease = :confirmedDisease WHERE beneficiaryRegID = :beneficiaryRegID AND visitCode = :visitCode")
	public int updateConfirmedDiseases(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode, @Param("confirmedDisease") String confirmedDisease);
	@Transactional
	@Modifying
	@Query("UPDATE IDRSData SET suspectedDisease = :suspectedDisease WHERE beneficiaryRegID = :beneficiaryRegID AND visitCode = :visitCode")
	public int updateSuspectedDiseases(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode, @Param("suspectedDisease") String suspectedDisease);
}
