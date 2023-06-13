/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.anc;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.anc.BenPersonalHabit;

@Repository
@RestResource(exported = false)
public interface BenPersonalHabitRepo extends CrudRepository<BenPersonalHabit, Integer> {

	@Query("select benVisitID from BenPersonalHabit a where a.beneficiaryRegID = :beneficiaryRegID order by benVisitID desc")
	public ArrayList<Long> getBenLastVisitID(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query("select Date(createdDate), dietaryType, physicalActivityType, tobaccoUseStatus, tobaccoUseType, otherTobaccoUseType, numberperDay, "
			+ "Date(tobaccoUseDuration), riskySexualPracticesStatus, numberperWeek from BenPersonalHabit a where a.beneficiaryRegID = :beneficiaryRegID "
			+ "AND tobaccoUseStatus is not null AND deleted = false order by createdDate DESC")
	public ArrayList<Object[]> getBenPersonalTobaccoHabitDetail(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query("select Date(createdDate), dietaryType, physicalActivityType, alcoholIntakeStatus, alcoholType, otherAlcoholType, alcoholIntakeFrequency, "
			+ " avgAlcoholConsumption, Date(alcoholDuration), riskySexualPracticesStatus "
			+ " from BenPersonalHabit a where a.beneficiaryRegID = :beneficiaryRegID AND alcoholIntakeStatus is not null AND deleted = false "
			+ " order by createdDate DESC")
	public ArrayList<Object[]> getBenPersonalAlcoholHabitDetail(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID, dietaryType, physicalActivityType, tobaccoUseStatus, tobaccoUseTypeID, "
			+ "tobaccoUseType, otherTobaccoUseType, numberperDay, tobaccoUseDuration, alcoholIntakeStatus, alcoholTypeID, "
			+ "alcoholType, otherAlcoholType, alcoholIntakeFrequency, avgAlcoholConsumption, alcoholDuration, riskySexualPracticesStatus,"
			+ " createdDate, visitCode, numberperWeek   "
			+ "FROM BenPersonalHabit WHERE beneficiaryRegID = :benRegID AND deleted = false AND visitCode = :visitCode")
	public ArrayList<Object[]> getBenPersonalHabitDetail(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Modifying
	@Transactional
	@Query(" Update BenPersonalHabit set deleted=true, processed=:processed WHERE benPersonalHabitID = :benPersonalHabitID")
	public int deleteExistingBenPersonalHistory(@Param("benPersonalHabitID") Integer benPersonalHabitID,
			@Param("processed") String processed);

	@Query("SELECT benPersonalHabitID, processed from BenPersonalHabit where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted=false")
	public ArrayList<Object[]> getBenPersonalHistoryStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

}
