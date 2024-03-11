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
package com.iemr.hwc.repo.nurse.anc;

import java.sql.Date;
import java.util.ArrayList;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.anc.ANCDiagnosis;

@Repository
@RestResource(exported = false)
public interface ANCDiagnosisRepo extends CrudRepository<ANCDiagnosis, Long> {

	@Query(" SELECT ID, beneficiaryRegID, benVisitID, providerServiceMapID, prescriptionID, highRiskStatus, highRiskCondition, complicationOfCurrentPregnancy, "
			+ "isMaternalDeath, placeOfDeath, dateOfDeath, causeOfDeath, visitCode from ANCDiagnosis ba "
			+ "WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode AND ba.deleted = false "
			+ " ORDER BY createdDate DESC ")
	public ArrayList<Object[]> getANCDiagnosisDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	ArrayList<ANCDiagnosis> findByBeneficiaryRegIDAndVisitCode(Long benRegID, Long visitCode);

	@Query("SELECT processed from ANCDiagnosis" + " WHERE beneficiaryRegID=:benRegID "
			+ " AND visitCode = :visitCode AND prescriptionID = :prescriptionID")
	public String getANCDiagnosisStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("prescriptionID") Long prescriptionID);

	@Transactional
	@Modifying
	@Query("update ANCDiagnosis set highRiskStatus=:highRiskStatus, highRiskCondition=:highRiskCondition, "
			+ "complicationOfCurrentPregnancy=:complicationOfCurrentPregnancy, isMaternalDeath=:isMaternalDeath, placeOfDeath=:placeOfDeath,"
			+ "dateOfDeath=:dateOfDeath, causeOfDeath=:causeOfDeath, modifiedBy=:modifiedBy, processed=:processed, "
			+ " otherCurrPregComplication = :othrPrgComp "
			+ " where visitCode=:visitCode AND beneficiaryRegID=:beneficiaryRegID")
	public int updateANCDiagnosis(@Param("highRiskStatus") String highRiskStatus,
			@Param("highRiskCondition") String highRiskCondition,
			@Param("complicationOfCurrentPregnancy") String complicationOfCurrentPregnancy,
			@Param("isMaternalDeath") Boolean isMaternalDeath, @Param("placeOfDeath") String placeOfDeath,
			@Param("dateOfDeath") Date dateOfDeath, @Param("causeOfDeath") String causeOfDeath,
			@Param("modifiedBy") String modifiedBy, @Param("processed") String processed,
			@Param("beneficiaryRegID") Long beneficiaryRegID, @Param("visitCode") Long visitCode,
			@Param("othrPrgComp") String othrPrgComp);

	@Query(" SELECT ID FROM ANCDiagnosis WHERE beneficiaryRegID = :benRegID "
			+ " AND ((complicationOfCurrentPregnancy like %:pregCompHypo%) OR (complicationOfCurrentPregnancy like %:pregCompObst%) "
			+ " OR (complicationOfCurrentPregnancy like %:pregCompSeve%) OR (complicationOfCurrentPregnancy like %:pregCompPih%) "
			+ " OR (complicationOfCurrentPregnancy like %:pregCompEcla%) OR (complicationOfCurrentPregnancy like %:pregCompSyph%) "
			+ " OR (complicationOfCurrentPregnancy like %:pregCompHiv%) OR (complicationOfCurrentPregnancy like %:pregCompGest%) "
			+ " OR (complicationOfCurrentPregnancy like %:pregCompMult%) OR (complicationOfCurrentPregnancy like %:pregCompBree%) "
			+ " OR (complicationOfCurrentPregnancy like %:pregCompTran%) OR (complicationOfCurrentPregnancy like %:pregCompAphP%) ) "
			+ " AND deleted = false ")
	public ArrayList<Long> getANCDiagnosisDataForHRP(@Param("benRegID") Long benRegID,
			@Param("pregCompHypo") String pregCompHypo, @Param("pregCompObst") String pregCompObst,
			@Param("pregCompSeve") String pregCompSeve, @Param("pregCompPih") String pregCompPih,
			@Param("pregCompEcla") String pregCompEcla, @Param("pregCompSyph") String pregCompSyph,
			@Param("pregCompHiv") String pregCompHiv, @Param("pregCompGest") String pregCompGest,
			@Param("pregCompMult") String pregCompMult, @Param("pregCompBree") String pregCompBree,
			@Param("pregCompTran") String pregCompTran, @Param("pregCompAphP") String pregCompAphP);
}
