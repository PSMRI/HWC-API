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

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.hwc.data.anc.PhyGeneralExamination;

@Repository
@RestResource(exported = false)
public interface PhyGeneralExaminationRepo extends CrudRepository<PhyGeneralExamination, Long> {

	@Query(" SELECT u FROM PhyGeneralExamination u WHERE u.beneficiaryRegID = :benRegID AND visitCode = :visitCode")
	public PhyGeneralExamination getPhyGeneralExaminationData(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT processed from PhyGeneralExamination where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getBenGeneralExaminationStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update PhyGeneralExamination set consciousness=:consciousness, cooperation=:cooperation, coherence=:coherence, "
			+ " comfortness=:comfortness, "
			+ "builtAndAppearance=:builtAndAppearance, gait=:gait, dangerSigns=:dangerSigns, "
			+ " typeOfDangerSign =:typeOfDangerSign, pallor=:pallor, jaundice=:jaundice, "
			+ "cyanosis=:cyanosis, clubbing=:clubbing, lymphadenopathy=:lymphadenopathy, lymphnodesInvolved=:lymphnodesInvolved, "
			+ "typeOfLymphadenopathy=:typeOfLymphadenopathy, edema=:edema, extentOfEdema=:extentOfEdema, edemaType=:edemaType,"
			+ "quickening=:quickening, foetalMovements=:foetalMovements ,"
			+ " modifiedBy=:modifiedBy, processed=:processed where beneficiaryRegID=:benRegID and visitCode = :visitCode ")
	public int updatePhyGeneralExamination(@Param("consciousness") String consciousness,
			@Param("coherence") String coherence, @Param("cooperation") String cooperation,
			@Param("comfortness") String comfortness, @Param("builtAndAppearance") String builtAndAppearance,
			@Param("gait") String gait, @Param("dangerSigns") String dangerSigns,
			@Param("typeOfDangerSign") String typeOfDangerSign, @Param("pallor") String pallor,
			@Param("jaundice") String jaundice, @Param("cyanosis") String cyanosis, @Param("clubbing") String clubbing,
			@Param("lymphadenopathy") String lymphadenopathy, @Param("lymphnodesInvolved") String lymphnodesInvolved,
			@Param("typeOfLymphadenopathy") String typeOfLymphadenopathy, @Param("edema") String edema,
			@Param("extentOfEdema") String extentOfEdema, @Param("edemaType") String edemaType,
			@Param("quickening") String quickening, @Param("foetalMovements") String foetalMovements,
			@Param("modifiedBy") String modifiedBy, @Param("processed") String processed,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

}
