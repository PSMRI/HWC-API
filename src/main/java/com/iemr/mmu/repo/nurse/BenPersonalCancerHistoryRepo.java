package com.iemr.mmu.repo.nurse;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.nurse.BenPersonalCancerHistory;

@Repository
@RestResource(exported = false)
public interface BenPersonalCancerHistoryRepo extends CrudRepository<BenPersonalCancerHistory, Long> {

	@Query(" SELECT bph from BenPersonalCancerHistory bph  WHERE bph.beneficiaryRegID = :benRegID AND bph.deleted = false AND bph.visitCode = :visitCode")
	public BenPersonalCancerHistory getBenPersonalHistory(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update BenPersonalCancerHistory set providerServiceMapID=:providerServiceMapID, tobaccoUse=:tobaccoUse, startAge_year=:startAge_year, endAge_year=:endAge_year, typeOfTobaccoProduct=:typeOfTobaccoProduct,"
			+ " quantityPerDay=:quantityPerDay, isFilteredCigaerette=:isFilteredCigaerette, isCigaretteExposure=:isCigaretteExposure, isBetelNutChewing=:isBetelNutChewing, "
			+ " durationOfBetelQuid=:durationOfBetelQuid, alcoholUse=:alcoholUse, ssAlcoholUsed=:ssAlcoholUsed, frequencyOfAlcoholUsed=:frequencyOfAlcoholUsed,"
			+ " modifiedBy=:modifiedBy, processed=:processed where "
			+ " beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public int updateBenPersonalCancerHistory(@Param("providerServiceMapID") Integer providerServiceMapID,
			@Param("tobaccoUse") String tobaccoUse, @Param("startAge_year") Integer startAge_year,
			@Param("endAge_year") Integer endAge_year, @Param("typeOfTobaccoProduct") String typeOfTobaccoProduct,
			@Param("quantityPerDay") Integer quantityPerDay,
			@Param("isFilteredCigaerette") Boolean isFilteredCigaerette,
			@Param("isCigaretteExposure") Boolean isCigaretteExposure,
			@Param("isBetelNutChewing") Boolean isBetelNutChewing,
			@Param("durationOfBetelQuid") Integer durationOfBetelQuid, @Param("alcoholUse") String alcoholUse,
			@Param("ssAlcoholUsed") Boolean ssAlcoholUsed,
			@Param("frequencyOfAlcoholUsed") String frequencyOfAlcoholUsed, @Param("modifiedBy") String modifiedBy,
			@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("processed") String processed);

	// @Query(" SELECT bph from BenPersonalCancerHistory bph WHERE
	// bph.beneficiaryRegID = :benRegID AND bph.benVisitID = :benVisitID "
	// + " AND DATE(createdDate) = :createdDate")
	// public BenPersonalCancerHistory getBenPersonalHistory(@Param("benRegID")
	// Long benRegID,
	// @Param("benVisitID") Long benVisitID, @Param("createdDate") Date
	// createdDate);

	@Query(" SELECT tobaccoUse, startAge_year, endAge_year, typeOfTobaccoProduct, quantityPerDay, isFilteredCigaerette, "
			+ " isCigaretteExposure, "
			+ "isBetelNutChewing, durationOfBetelQuid, alcoholUse, ssAlcoholUsed, frequencyOfAlcoholUsed, Date(createdDate) "
			+ " from BenPersonalCancerHistory bph  " + "WHERE bph.beneficiaryRegID = :benRegID and "
			+ " (tobaccoUse is not null OR alcoholUse is not null OR isCigaretteExposure is not null"
			+ " or isBetelNutChewing is not null) order by createdDate desc")
	public ArrayList<Object[]> getBenPersonalHistory(@Param("benRegID") Long benRegID);

	@Query("SELECT processed from BenPersonalCancerHistory where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getPersonalCancerHistoryStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

}
