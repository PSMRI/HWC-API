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
package com.iemr.hwc.repo.benFlowStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;

/***
 * 
 * @author NE298657
 *
 */
@Repository
@RestResource(exported = false)
public interface BeneficiaryFlowStatusRepo extends CrudRepository<BeneficiaryFlowStatus, Long> {

	// nurse worklist
//	@Query("SELECT  t from BeneficiaryFlowStatus t WHERE (t.nurseFlag = 1 OR t.nurseFlag = 100) AND (t.specialist_flag <> 100 OR t.specialist_flag is null) AND t.deleted = false "
//			+ " AND Date(t.visitDate)  = curdate() AND t.providerServiceMapId = :providerServiceMapId "
//			+ " AND t.vanID = :vanID  ORDER BY t.visitDate DESC ")
//	public ArrayList<BeneficiaryFlowStatus> getNurseWorklistNew(
//			@Param("providerServiceMapId") Integer providerServiceMapId, @Param("vanID") Integer vanID);

	@Query("SELECT  t from BeneficiaryFlowStatus t WHERE (t.nurseFlag = 1 OR t.nurseFlag = 100) AND (t.specialist_flag <> 100 OR t.specialist_flag is null) AND t.deleted = false "
			+ " AND Date(t.visitDate)  >= Date(:fromDate) AND t.providerServiceMapId = :providerServiceMapId "
			+ " AND t.vanID = :vanID  ORDER BY t.visitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getNurseWorklistNew(
			@Param("providerServiceMapId") Integer providerServiceMapId, @Param("vanID") Integer vanID,
			@Param("fromDate") Timestamp fromDate);

	// nurse worklist TC current date
	@Query("SELECT  t from BeneficiaryFlowStatus t WHERE (t.specialist_flag != 0 AND t.specialist_flag != 100 AND t.specialist_flag is not null)"
			+ " AND t.deleted = false AND DATE(t.benVisitDate) >= DATE(:fromDate) "
			+ " AND (Date(t.tCRequestDate)  <= curdate() OR Date(t.tCRequestDate) = null) "
			+ " AND t.providerServiceMapId = :providerServiceMapId "
			+ " AND t.vanID = :vanID ORDER BY t.visitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getNurseWorklistCurrentDate(
			@Param("providerServiceMapId") Integer providerServiceMapId, @Param("fromDate") Timestamp fromDate,
			@Param("vanID") Integer vanID);

	// nurse worklist future date
	@Query("SELECT  t from BeneficiaryFlowStatus t WHERE (t.specialist_flag = 1)"
			+ " AND t.deleted = false AND (Date(t.tCRequestDate)  > curdate()) "
			+ " AND t.providerServiceMapId = :providerServiceMapId "
			+ " AND t.vanID = :vanID ORDER BY t.tCRequestDate  ")
	public ArrayList<BeneficiaryFlowStatus> getNurseWorklistFutureDate(
			@Param("providerServiceMapId") Integer providerServiceMapId, @Param("vanID") Integer vanID);

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set t.benVisitID = :benVisitID, t.VisitReason = :visitReason, "
			+ " t.VisitCategory = :visitCategory, t.nurseFlag = :nurseFlag, t.doctorFlag = :docFlag, "
			+ " t.labIteration = :labIteration, t.radiologist_flag = :radiologistFlag, "
			+ " t.oncologist_flag = :oncologistFlag, t.specialist_flag = :specialistFlag, t.benVisitDate = now(), "
			+ " t.tCRequestDate = :tcDate, t.tCSpecialistUserID = :specialistID, "
			+ " t.visitCode = :benVisitCode, t.processed = 'U', t.vanID =:vanID,t.lab_technician_flag = 0 "
			+ "  WHERE t.benFlowID = :benFlowID AND t.beneficiaryRegID = :benRegID " + " AND nurseFlag = 1  ")
	public int updateBenFlowStatusAfterNurseActivity(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("benVisitID") Long benVisitID,
			@Param("visitReason") String visitReason, @Param("visitCategory") String visitCategory,
			@Param("nurseFlag") Short nurseFlag, @Param("docFlag") Short docFlag,
			@Param("labIteration") Short labIteration, @Param("radiologistFlag") Short radiologistFlag,
			@Param("oncologistFlag") Short oncologistFlag, @Param("benVisitCode") Long benVisitCode,
			@Param("vanID") Integer vanID, @Param("specialistFlag") Short specialistFlag,
			@Param("tcDate") Timestamp tcDate, @Param("specialistID") Integer specialistID);

	/***
	 * @author DU20091017 removed lab technician flag , initially updating to 0 and
	 *         updating it according to feto sense response and lab assigned
	 */
	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set t.benVisitID = :benVisitID, t.VisitReason = :visitReason, "
			+ " t.VisitCategory = :visitCategory, t.nurseFlag = :nurseFlag, t.doctorFlag = :docFlag, "
			+ " t.labIteration = :labIteration, t.radiologist_flag = :radiologistFlag, "
			+ " t.oncologist_flag = :oncologistFlag, t.specialist_flag = :specialistFlag, t.benVisitDate = now(), "
			+ " t.tCRequestDate = :tcDate, t.tCSpecialistUserID = :specialistID, "
			+ " t.visitCode = :benVisitCode, t.processed = 'U', t.vanID =:vanID,t.lab_technician_flag = :labTechnician "
			+ "  WHERE t.benFlowID = :benFlowID AND t.beneficiaryRegID = :benRegID " + " AND nurseFlag = 1  ")
	public int updateBenFlowStatusAfterNurseActivityANC(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("benVisitID") Long benVisitID,
			@Param("visitReason") String visitReason, @Param("visitCategory") String visitCategory,
			@Param("nurseFlag") Short nurseFlag, @Param("docFlag") Short docFlag,
			@Param("labIteration") Short labIteration, @Param("radiologistFlag") Short radiologistFlag,
			@Param("oncologistFlag") Short oncologistFlag, @Param("benVisitCode") Long benVisitCode,
			@Param("vanID") Integer vanID, @Param("specialistFlag") Short specialistFlag,
			@Param("tcDate") Timestamp tcDate, @Param("specialistID") Integer specialistID,
			@Param("labTechnician") Short labTechnician);

//	@Query("SELECT  t.benFlowID, t.beneficiaryRegID, t.visitDate, t.benName, t.age, t.ben_age_val, t.genderID, t.genderName, "
//			+ " t.villageName, t.districtName, t.beneficiaryID, t.servicePointName, t.VisitReason, t.VisitCategory, t.benVisitID,  "
//			+ " t.registrationDate, t.benVisitDate, t.visitCode, t.consultationDate FROM BeneficiaryFlowStatus t "
//			+ " Where t.beneficiaryRegID = :benRegID AND t.benFlowID = :benFlowID ")
//	public ArrayList<Object[]> getBenDetailsForLeftSidePanel(@Param("benRegID") Long benRegID,
//			@Param("benFlowID") Long benFlowID);

	// HWC, 2.0 dob in response
	@Query("SELECT t FROM BeneficiaryFlowStatus t Where t.benFlowID = :benFlowID ")
	public BeneficiaryFlowStatus getBenDetailsForLeftSidePanel(@Param("benFlowID") Long benFlowID);

	// MMU doc work-list
	@Query("SELECT t from BeneficiaryFlowStatus t WHERE (t.doctorFlag = 1 OR t.doctorFlag = 2 OR "
			+ " t.doctorFlag = 3 OR t.nurseFlag = 2 OR t.doctorFlag = 9) AND t.deleted = false "
			+ " AND t.providerServiceMapId = :providerServiceMapId " + " ORDER BY benVisitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getDocWorkListNew(
			@Param("providerServiceMapId") Integer providerServiceMapId);

	// TC doc work-list, 04-12-2018
	@Query("SELECT t from BeneficiaryFlowStatus t WHERE (t.doctorFlag = 1 OR t.doctorFlag = 2 OR "
			+ " t.doctorFlag = 3 OR t.nurseFlag = 2 OR t.doctorFlag = 9 ) AND t.vanID = :vanID "
			+ " AND t.benVisitDate >= Date(:fromDate) "
			+ " AND (t.tCRequestDate is null OR DATE(t.tCRequestDate) <= curdate() ) "
			+ " AND t.deleted = false AND t.providerServiceMapId = :providerServiceMapId "
			+ " ORDER BY t.benVisitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getDocWorkListNewTC(
			@Param("providerServiceMapId") Integer providerServiceMapId, @Param("fromDate") Timestamp fromDate,
			@Param("vanID") Integer vanID);

	// TC doc work-list, future scheduled 13-12-2018
	@Query("SELECT t from BeneficiaryFlowStatus t "
			+ " WHERE t.specialist_flag = 1 AND t.tCRequestDate is not null AND t.tCSpecialistUserID is not null "
			+ " AND t.vanID = :vanID AND DATE(t.tCRequestDate) > curdate() "
			+ " AND t.deleted = false AND t.providerServiceMapId = :providerServiceMapId "
			+ " ORDER BY t.tCRequestDate ")
	public ArrayList<BeneficiaryFlowStatus> getDocWorkListNewFutureScheduledTC(
			@Param("providerServiceMapId") Integer providerServiceMapId, @Param("vanID") Integer vanID);

	// TC Specialist work-list, 13-12-2018
	@Query("SELECT t from BeneficiaryFlowStatus t WHERE Date(t.benVisitDate) >= DATE(:fromDate) "
			+ " AND t.specialist_flag NOT IN (0,4) AND t.tCRequestDate is not null AND t.tCSpecialistUserID is not null "
			+ " AND t.tCSpecialistUserID =:tCSpecialistUserID AND DATE(t.tCRequestDate) <= curdate() "
			+ " AND t.deleted = false AND t.providerServiceMapId = :providerServiceMapId "
			+ " ORDER BY t.benVisitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getTCSpecialistWorkListNew(
			@Param("providerServiceMapId") Integer providerServiceMapId,
			@Param("tCSpecialistUserID") Integer tCSpecialistUserID, @Param("fromDate") Timestamp fromDate);

	// TC Specialist work-list, patient app, 14-08-2020
	@Query("SELECT t from BeneficiaryFlowStatus t WHERE  Date(t.benVisitDate) >= DATE(:fromDate) "
			+ " AND t.specialist_flag NOT IN (0,4) AND t.tCRequestDate is not null AND t.tCSpecialistUserID is not null "
			+ " AND t.tCSpecialistUserID =:tCSpecialistUserID AND DATE(t.tCRequestDate) <= curdate() "
			+ " AND t.vanID =:vanID AND t.deleted = false AND t.providerServiceMapId = :providerServiceMapId "
			+ " ORDER BY t.benVisitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getTCSpecialistWorkListNewPatientApp(
			@Param("providerServiceMapId") Integer providerServiceMapId,
			@Param("tCSpecialistUserID") Integer tCSpecialistUserID, @Param("fromDate") Timestamp fromDate,
			@Param("vanID") Integer vanID);

	// TC Specialist work-list, future scheduled, 13-12-2018
	@Query("SELECT t from BeneficiaryFlowStatus t "
			+ " WHERE t.specialist_flag NOT IN (0,4) AND t.tCRequestDate is not null AND t.tCSpecialistUserID is not null "
			+ " AND t.tCSpecialistUserID =:tCSpecialistUserID AND DATE(t.tCRequestDate) > curdate() "
			+ " AND t.deleted = false AND t.providerServiceMapId = :providerServiceMapId "
			+ " ORDER BY t.tCRequestDate ")
	public ArrayList<BeneficiaryFlowStatus> getTCSpecialistWorkListNewFutureScheduled(
			@Param("providerServiceMapId") Integer providerServiceMapId,
			@Param("tCSpecialistUserID") Integer tCSpecialistUserID);

//	@Query("SELECT  t.benFlowID from BeneficiaryFlowStatus t WHERE t.beneficiaryRegID = :benRegID "
//			+ "AND t.providerServiceMapId = :provoderSerMapID AND t.vanID = :vanID AND "
//			+ " (t.nurseFlag = 1 OR t.nurseFlag = 100) AND Date(t.visitDate)  = curdate() AND t.deleted = false")
//	public ArrayList<Long> checkBenAlreadyInNurseWorkList(@Param("benRegID") Long benRegID,
//			@Param("provoderSerMapID") Integer provoderSerMapID, @Param("vanID") Integer vanID);
	@Query("SELECT  t.benFlowID from BeneficiaryFlowStatus t WHERE t.beneficiaryRegID = :benRegID "
			+ "AND t.providerServiceMapId = :provoderSerMapID AND t.vanID = :vanID AND "
			+ " (t.nurseFlag = 1 OR t.nurseFlag = 100) AND Date(t.visitDate) >= Date(:fromDate) AND t.deleted = false")
	public ArrayList<Long> checkBenAlreadyInNurseWorkList(@Param("benRegID") Long benRegID,
			@Param("provoderSerMapID") Integer provoderSerMapID, @Param("vanID") Integer vanID,
			@Param("fromDate") Timestamp fromDate);

	@Query("SELECT t from BeneficiaryFlowStatus t " + " WHERE t.benVisitDate >= Date(:fromDate) AND t.vanID = :vanID "
			+ " AND (t.nurseFlag = 2 OR t.doctorFlag = 2 OR t.specialist_flag = 2)  AND t.deleted = false "
			+ " AND t.providerServiceMapId = :providerServiceMapId ORDER BY consultationDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getLabWorklistNew(
			@Param("providerServiceMapId") Integer providerServiceMapId, @Param("fromDate") Timestamp fromDate,
			@Param("vanID") Integer vanID);

	/***
	 * @author DU20091017 updating lab technician flag as well after feto sense.
	 */
	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set t.doctorFlag = :docFlag , t.pharmacist_flag = :pharmaFlag, "
			+ " t.oncologist_flag = :oncologistFlag, t.consultationDate = now(), t.processed = 'U', "
			+ " t.specialist_flag = :tcSpecialistFlag, t.tCSpecialistUserID = :tcSpecialistUserID, "
			+ "t.tCRequestDate = :tcDate, t.lab_technician_flag = :labTechnicianFlag "
			+ " WHERE t.benFlowID = :benFlowID AND " + " t.beneficiaryRegID = :benRegID AND t.beneficiaryID = :benID ")
	public int updateBenFlowStatusAfterDoctorActivity(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("benID") Long benID, @Param("docFlag") Short docFlag,
			@Param("pharmaFlag") Short pharmaFlag, @Param("oncologistFlag") Short oncologistFlag,
			@Param("tcSpecialistFlag") Short tcSpecialistFlag, @Param("tcSpecialistUserID") int tcSpecialistUserID,
			@Param("tcDate") Timestamp tcDate, @Param("labTechnicianFlag") Short labTechnicianFlag);

	/***
	 * @author DU20091017 updating lab technician flag as well after feto sense.
	 */
	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set t.doctorFlag = :docFlag , t.pharmacist_flag = :pharmaFlag, "
			+ " t.oncologist_flag = :oncologistFlag, t.consultationDate = now(), t.processed = 'U', "
			+ " t.specialist_flag = :tcSpecialistFlag , t.lab_technician_flag = :labTechnicianFlag "
			+ " WHERE t.benFlowID = :benFlowID AND t.beneficiaryRegID = :benRegID AND t.beneficiaryID = :benID ")
	public int updateBenFlowStatusAfterDoctorActivitySpecialist(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("benID") Long benID, @Param("docFlag") Short docFlag,
			@Param("pharmaFlag") Short pharmaFlag, @Param("oncologistFlag") Short oncologistFlag,
			@Param("tcSpecialistFlag") Short tcSpecialistFlag, @Param("labTechnicianFlag") Short labTechnicianFlag);

	/***
	 * @author DU20091017 updating lab technician flag as well after feto sense.
	 */
	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set t.doctorFlag = :docFlag , t.pharmacist_flag = :pharmaFlag, "
			+ " t.oncologist_flag = :oncologistFlag, t.consultationDate = now(), t.processed = 'U', "
			+ " t.specialist_flag = :tcSpecialistFlag , t.lab_technician_flag = :labTechnicianFlag "
			+ " WHERE t.benFlowID = :benFlowID AND t.beneficiaryRegID = :benRegID AND t.beneficiaryID = :benID ")
	public int updateBenFlowStatusAfterDoctorActivitySpecialistANC(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("benID") Long benID, @Param("docFlag") Short docFlag,
			@Param("pharmaFlag") Short pharmaFlag, @Param("oncologistFlag") Short oncologistFlag,
			@Param("tcSpecialistFlag") Short tcSpecialistFlag, @Param("labTechnicianFlag") Short labTechnicianFlag);

	/***
	 * @author DU20091017 updating lab technician flag as well after feto sense.
	 */
	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set t.pharmacist_flag = :pharmaFlag, "
			+ " t.oncologist_flag = :oncologistFlag, t.processed = 'U', t.specialist_flag = :tcSpecialistFlag, "
			+ "t.lab_technician_flag = :labTechnicianFlag"
			+ " WHERE t.benFlowID = :benFlowID AND  t.beneficiaryRegID = :benRegID AND t.beneficiaryID = :benID ")
	public int updateBenFlowStatusAfterDoctorActivityTCSpecialist(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("benID") Long benID, @Param("pharmaFlag") Short pharmaFlag,
			@Param("oncologistFlag") Short oncologistFlag, @Param("tcSpecialistFlag") Short tcSpecialistFlag,
			@Param("labTechnicianFlag") Short labTechnicianFlag);

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set t.doctorFlag = :docFlag , t.pharmacist_flag = :pharmaFlag, "
			+ " t.oncologist_flag = :oncologistFlag , t.processed = 'U' " + " WHERE t.benFlowID = :benFlowID AND "
			+ " t.beneficiaryRegID = :benRegID AND t.beneficiaryID = :benID ")
	public int updateBenFlowStatusAfterDoctorActivityUpdate(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("benID") Long benID, @Param("docFlag") Short docFlag,
			@Param("pharmaFlag") Short pharmaFlag, @Param("oncologistFlag") Short oncologistFlag);

	@Query("SELECT t from BeneficiaryFlowStatus t "
			+ " WHERE t.benVisitDate >= Date(:fromDate) AND t.vanID = :vanID AND t.radiologist_flag = 1 "
			+ " AND t.providerServiceMapId= :providerServiceMapId ORDER BY t.benVisitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getRadiologistWorkListNew(
			@Param("providerServiceMapId") Integer providerServiceMapId, @Param("fromDate") Timestamp fromDate,
			@Param("vanID") Integer vanID);

	@Query("SELECT t from BeneficiaryFlowStatus t "
			+ "  WHERE t.benVisitDate >= Date(:fromDate) AND t.vanID = :vanID AND t.oncologist_flag = 1 "
			+ " AND t.providerServiceMapId= :providerServiceMapId ORDER BY t.benVisitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getOncologistWorkListNew(
			@Param("providerServiceMapId") Integer providerServiceMapId, @Param("fromDate") Timestamp fromDate,
			@Param("vanID") Integer vanID);

	@Query("SELECT t from BeneficiaryFlowStatus t "
			+ " WHERE t.benVisitDate >= Date(:fromDate) AND t.vanID = :vanID AND t.pharmacist_flag = 1 "
			+ "  AND t.providerServiceMapId= :providerServiceMapId AND (doctorFlag = 9 OR specialist_flag = 9) "
			+ "  ORDER BY consultationDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getPharmaWorkListNew(
			@Param("providerServiceMapId") Integer providerServiceMapId, @Param("fromDate") Timestamp fromDate,
			@Param("vanID") Integer vanID);

	@Query("SELECT t.pharmacist_flag from BeneficiaryFlowStatus t WHERE t.benFlowID = :benFlowID")
	public Short getPharmaFlag(@Param("benFlowID") Long benFlowID);

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set t.nurseFlag = :nurseFlag, t.processed = 'U' "
			+ " where t.benFlowID = :benFlowID AND t.beneficiaryRegID = :benRegID ")
	public int updateBenFlowStatusAfterNurseDataUpdateNCD_Screening(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("nurseFlag") Short nurseFlag);

	/***
	 * @author DU20091017 removing update of lab technician flag, as it is used from
	 *         know for feto sense.
	 */
	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set t.nurseFlag = :nurseFlag, t.doctorFlag = :doctorFlag,"
			+ " t.processed = 'U' WHERE t.benFlowID = :benFlowID AND t.beneficiaryRegID = :benRegID ")
	public int updateBenFlowStatusAfterLabResultEntry(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("nurseFlag") Short nurseFlag,
			@Param("doctorFlag") Short doctorFlag);

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set  t.processed = 'U', t.specialist_flag = :specialist_flag "
			+ " WHERE t.benFlowID = :benFlowID AND t.beneficiaryRegID = :benRegID ")
	public int updateBenFlowStatusAfterLabResultEntryForSpecialist(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("specialist_flag") Short specialist_flag);

	// beneficiary previous visit history
	@Query("SELECT benFlowID, beneficiaryRegID, visitCode, "
			+ " benVisitDate, benVisitNo, VisitReason, VisitCategory  from BeneficiaryFlowStatus "
			+ " WHERE beneficiaryRegID = :beneficiaryRegID AND ((doctorFlag = 9) "
			+ " OR (nurseFlag = 9 AND doctorFlag = 0) OR specialist_flag = 9) AND providerServiceMapId IN :psmIDList "
			+ " ORDER BY benVisitDate DESC ")
	public ArrayList<Object[]> getBenPreviousHistory(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("psmIDList") List<Integer> psmIDList);

	@Query(" SELECT COUNT(benFlowID) FROM BeneficiaryFlowStatus "
			+ " WHERE beneficiaryRegID = :beneficiaryRegID AND VisitCategory = 'NCD screening' ")
	public Long getNcdScreeningVisitCount(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Transactional
	@Modifying
	@Query(" UPDATE BeneficiaryFlowStatus t SET t.benArrivedFlag = :arrivalFlag, "
			+ " t.modified_by = :modifiedBy WHERE t.benFlowID = :benFlowID AND t.tCSpecialistUserID = :userID "
			+ " AND t.beneficiaryRegID = :benRegID AND t.visitCode = :visitCode ")
	public int updateBeneficiaryArrivalStatus(@Param("benFlowID") Long benFlowID, @Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode, @Param("arrivalFlag") Boolean arrivalFlag,
			@Param("modifiedBy") String modifiedBy, @Param("userID") Integer userID);

	@Transactional
	@Modifying
	@Query(" UPDATE BeneficiaryFlowStatus t SET t.benArrivedFlag = false, t.modified_by = :modifiedBy, "
			+ " t.tCSpecialistUserID = null, t.tCRequestDate = null, t.specialist_flag = 4  "
			+ " WHERE t.benFlowID = :benFlowID AND t.tCSpecialistUserID = :userID "
			+ " AND t.beneficiaryRegID = :benRegID AND t.visitCode = :visitCode ")
	public int updateBeneficiaryStatusToCancelRequest(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("modifiedBy") String modifiedBy, @Param("userID") Integer userID);

	@Query(" SELECT t FROM BeneficiaryFlowStatus t "
			+ " WHERE t.benFlowID = :benFlowID AND t.tCSpecialistUserID = :userID "
			+ " AND t.beneficiaryRegID = :benRegID AND t.visitCode = :visitCode ")
	public ArrayList<BeneficiaryFlowStatus> checkBeneficiaryArrivalStatus(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode, @Param("userID") Integer userID);

	@Transactional
	@Modifying
	@Query(" UPDATE BeneficiaryFlowStatus t SET t.specialist_flag = 1, t.tCSpecialistUserID = :userID, "
			+ " t.tCRequestDate = :reqDate, t.processed = 'U' "
			+ " WHERE t.benFlowID = :benFlowID AND t.beneficiaryRegID = :benRegID AND t.visitCode = :visitCode ")
	public int updateFlagAfterTcRequestCreatedFromWorklist(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode, @Param("userID") Integer userID,
			@Param("reqDate") Timestamp reqDate);

	// TC request list
	@Query("SELECT t from BeneficiaryFlowStatus t WHERE t.tCSpecialistUserID = :tCSpecialistUserID "
			+ "  AND t.providerServiceMapId = :providerServiceMapId AND DATE(t.tCRequestDate) = :reqDate ")
	public ArrayList<BeneficiaryFlowStatus> getTCRequestList(
			@Param("providerServiceMapId") Integer providerServiceMapId,
			@Param("tCSpecialistUserID") Integer tCSpecialistUserID, @Param("reqDate") Timestamp reqDate);

	@Transactional
	@Modifying
	@Query(" UPDATE BeneficiaryFlowStatus t SET t.specialist_flag = 9 "
			+ " WHERE t.benFlowID = :benFlowID AND t.beneficiaryRegID = :benRegID AND t.visitCode = :visitCode ")
	public int updateBenFlowAfterTCSpcialistDoneForCanceScreening(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Query("SELECT  t.tCSpecialistUserID FROM BeneficiaryFlowStatus t "
			+ " Where t.beneficiaryRegID = :benRegID AND t.benFlowID = :benFlowID ")
	public Integer getTCspecialistID(@Param("benRegID") Long benRegID, @Param("benFlowID") Long benFlowID);

	@Query(" SELECT benName, Date(dOB), genderID FROM BeneficiaryFlowStatus WHERE benFlowID = :benFlowID ")
	public ArrayList<Object[]> getBenDataForCareStream(@Param("benFlowID") Long benFlowID);

	@Query(" SELECT t FROM BeneficiaryFlowStatus t WHERE t.preferredPhoneNum = :phoneNo "
			+ " AND t.tCRequestDate >= curdate() AND t.specialist_flag IN (1) ")
	public ArrayList<BeneficiaryFlowStatus> getBenSlotDetails(@Param("phoneNo") String phoneNo);

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set  t.processed = 'U', t.specialist_flag = :specialist_flag "
			+ " WHERE t.beneficiaryRegID = :benRegID AND t.visitCode =:visitCode ")
	public int updateBenFlowStatusAfterSpecialistMobileAPP(@Param("visitCode") Long visitCode,
			@Param("benRegID") Long benRegID, @Param("specialist_flag") Short specialist_flag);

	@Query(value = " SELECT * FROM db_iemr.i_ben_flow_outreach t WHERE t.beneficiary_reg_id =:benRegID AND t.specialist_flag = 9 "
			+ " ORDER BY t.created_date DESC limit 3", nativeQuery = true)
	public ArrayList<BeneficiaryFlowStatus> getPatientLat_3_Episode(@Param("benRegID") Long benRegID);

	// nurse worklist coming from MMU
	@Query("SELECT  t from BeneficiaryFlowStatus t WHERE t.specialist_flag = 100 AND t.deleted = false AND t.referredVisitCode IS NOT NULL "
			+ " AND t.processed = 'M' AND t.providerServiceMapId = :providerServiceMapId AND t.visitDate >= Date(:fromDate) "
			+ " AND t.vanID = :vanID  ORDER BY t.visitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getMmuNurseWorklistNew(
			@Param("providerServiceMapId") Integer providerServiceMapId, @Param("vanID") Integer vanID,
			@Param("fromDate") Timestamp fromDate);

	/**
	 * updating lab technician flag to 3 from 3 , as soon as receive response from
	 * foetal monitor
	 * 
	 * @author DU20091017
	 * @param lab_technician_flag
	 * @param visitCode
	 * @param benRegID
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set  t.lab_technician_flag = :lab_technician_flag "
			+ " WHERE t.benFlowID =:benFlowID ")
	public int updateLabTechnicianFlag(@Param("lab_technician_flag") Short lab_technician_flag,
			@Param("benFlowID") Long benFlowID);

	// get ben age, HRP
	@Query(nativeQuery = true, value = " SELECT ben_dob FROM i_ben_flow_outreach WHERE beneficiary_reg_id = :benRegID "
			+ " AND ben_gender_val = 2 AND ben_dob is not null order by ben_flow_id DESC LIMIT 1 ")
	public Timestamp getBenAgeVal(@Param("benRegID") Long benRegID);

//	/**
//	 * get labtechnician flag and update for foetal monitor test
//	 * @author SH20094090
//	 * @param benFlowID
//	 */
//	@Query("SELECT  t.lab_technician_flag from BeneficiaryFlowStatus t WHERE t.benFlowID =:benFlowID ")
//	public Short getLabTechnicianFlag(@Param("benFlowID") Long benFlowID);

	@Query(value = " SELECT beneficiary_visit_code FROM db_iemr.i_ben_flow_outreach WHERE beneficiary_reg_id =:benRegId AND "
			+ " visit_category =:vc AND beneficiary_visit_code is not null  ORDER BY ben_flow_id DESC LIMIT 1 ", nativeQuery = true)
	public Long getLatestVisitCode(@Param("benRegId") Long benRegId, @Param("vc") String vc);

	// get visit by location and modify_date
	@Query("SELECT  t from BeneficiaryFlowStatus t WHERE t.villageID = :villageID AND t.modified_date > :lastModDate ORDER BY t.visitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getVisitByLocationAndLastModifDate(@Param("villageID") Integer villageID, @Param("lastModDate") Timestamp lastModDate);

	//get ben flow status records based on villageId and last sync date to sync to app local dB
	@Query("SELECT  t from BeneficiaryFlowStatus t WHERE t.villageID = :villageID AND t.modified_date > :lastModDate ")
    ArrayList<BeneficiaryFlowStatus> getFlowRecordsToSync(@Param("villageID") Integer villageID, @Param("lastModDate") Timestamp lastModDate);

}
