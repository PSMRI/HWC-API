/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.anc.PerinatalHistory;

@Repository
@RestResource(exported = false)
public interface PerinatalHistoryRepo extends CrudRepository<PerinatalHistory, Long> {

	@Query("select Date(createdDate), placeOfDelivery, otherPlaceOfDelivery, typeOfDelivery, complicationAtBirth, otherComplicationAtBirth, gestation, birthWeightG "
			+ "from PerinatalHistory a where a.beneficiaryRegID = :beneficiaryRegID AND (placeOfDelivery is not null OR "
			+ "typeOfDelivery is not null OR complicationAtBirth is not null)"
			+ "AND deleted = false ORDER BY createdDate DESC ")
	public ArrayList<Object[]> getBenPerinatalDetail(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query("select beneficiaryRegID, benVisitID, providerServiceMapID, deliveryPlaceID, placeOfDelivery, otherPlaceOfDelivery, deliveryTypeID, "
			+ "typeOfDelivery, complicationAtBirthID, complicationAtBirth, otherComplicationAtBirth, gestation, visitCode, birthWeightG "
			+ "from PerinatalHistory a where a.beneficiaryRegID = :beneficiaryRegID and a.visitCode = :visitCode AND deleted = false")
	public ArrayList<Object[]> getBenPerinatalDetails(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

	// enhancement of above, return class object, 11-10-2022
	@Query("select t from PerinatalHistory t where t.beneficiaryRegID = :beneficiaryRegID and t.visitCode = :visitCode AND t.deleted = false")
	public PerinatalHistory getBenPerinatalHistory(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT processed from PerinatalHistory where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted = false")
	public String getPerinatalHistoryStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update PerinatalHistory set deliveryPlaceID=:deliveryPlaceID, placeOfDelivery=:placeOfDelivery, "
			+ "otherPlaceOfDelivery=:otherPlaceOfDelivery, deliveryTypeID=:deliveryTypeID,"
			+ " typeOfDelivery=:typeOfDelivery, complicationAtBirthID=:complicationAtBirthID, complicationAtBirth=:complicationAtBirth, "
			+ "otherComplicationAtBirth=:otherComplicationAtBirth, gestation=:gestation, birthWeightG=:birthWeightG, "
			+ "  modifiedBy=:modifiedBy, processed=:processed where "
			+ "beneficiaryRegID=:beneficiaryRegID AND visitCode = :visitCode")
	public int updatePerinatalDetails(@Param("deliveryPlaceID") Short deliveryPlaceID,
			@Param("placeOfDelivery") String placeOfDelivery,
			@Param("otherPlaceOfDelivery") String otherPlaceOfDelivery, @Param("deliveryTypeID") Short deliveryTypeID,
			@Param("typeOfDelivery") String typeOfDelivery, @Param("complicationAtBirthID") Short complicationAtBirthID,
			@Param("complicationAtBirth") String complicationAtBirth,
			@Param("otherComplicationAtBirth") String otherComplicationAtBirth, @Param("gestation") String gestation,
			@Param("birthWeightG") Double birthWeightG, @Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed, @Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

}
