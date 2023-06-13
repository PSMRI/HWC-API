/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.nurse.pnc;

import java.sql.Date;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.pnc.PNCCare;

@Repository
@RestResource(exported = false)
public interface PNCCareRepo extends CrudRepository<PNCCare, Integer> {

	@Query(" SELECT ID, beneficiaryRegID, benVisitID, providerServiceMapID, visitNo, deliveryTypeID, deliveryType, deliveryPlaceID, "
			+ "deliveryPlace, otherDeliveryPlace, dateOfDelivery, deliveryComplicationID, deliveryComplication, otherDeliveryComplication, "
			+ "pregOutcomeID, pregOutcome, postNatalComplicationID, postNatalComplication, otherPostNatalComplication, gestationID, gestationName,"
			+ " birthWeightOfNewborn, newBornHealthStatusID, newBornHealthStatus, visitCode "
			+ "from PNCCare ba WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode ")
	public ArrayList<Object[]> getPNCCareDetails(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Query(" SELECT pnccare FROM PNCCare pnccare WHERE pnccare.beneficiaryRegID = :benRegID AND pnccare.visitCode = :visitCode ")
	public PNCCare getPNCCareDetailsByVisitCode(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Query(" SELECT processed from PNCCare where beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getBenPNCCareDetailsStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update PNCCare set deliveryTypeID=:deliveryTypeID, "
			+ "deliveryType=:deliveryType, deliveryPlaceID=:deliveryPlaceID, deliveryPlace=:deliveryPlace,"
			+ " otherDeliveryPlace=:otherDeliveryPlace, dateOfDelivery=:dateOfDelivery, "
			+ "deliveryComplicationID=:deliveryComplicationID, deliveryComplication=:deliveryComplication,"
			+ "otherDeliveryComplication=:otherDeliveryComplication,  pregOutcomeID=:pregOutcomeID, pregOutcome=:pregOutcome, "
			+ "postNatalComplicationID=:postNatalComplicationID, postNatalComplication=:postNatalComplication, "
			+ "otherPostNatalComplication=:otherPostNatalComplication, gestationID=:gestationID, gestationName=:gestationName,"
			+ "birthWeightOfNewborn=:birthWeightOfNewborn,newBornHealthStatusID=:newBornHealthStatusID, newBornHealthStatus=:newBornHealthStatus,"
			+ " modifiedBy=:modifiedBy, processed=:processed, deliveryConductedByID = :deliveryConductedByID, deliveryConductedBy = :deliveryConductedBy  "
			+ " where visitCode=:visitCode AND beneficiaryRegID=:beneficiaryRegID")
	public int updatePNCCareDetails(@Param("deliveryTypeID") Short deliveryTypeID,
			@Param("deliveryType") String deliveryType, @Param("deliveryPlaceID") Short deliveryPlaceID,
			@Param("deliveryPlace") String deliveryPlace, @Param("otherDeliveryPlace") String otherDeliveryPlace,
			@Param("dateOfDelivery") Date dateOfDelivery, @Param("deliveryComplicationID") Short deliveryComplicationID,
			@Param("deliveryComplication") String deliveryComplication,
			@Param("otherDeliveryComplication") String otherDeliveryComplication,
			@Param("pregOutcomeID") Short pregOutcomeID, @Param("pregOutcome") String pregOutcome,
			@Param("postNatalComplicationID") Short postNatalComplicationID,
			@Param("postNatalComplication") String postNatalComplication,
			@Param("otherPostNatalComplication") String otherPostNatalComplication,
			@Param("gestationID") Short gestationID, @Param("gestationName") String gestationName,
			@Param("birthWeightOfNewborn") Double birthWeightOfNewborn,
			@Param("newBornHealthStatusID") Integer newBornHealthStatusID,
			@Param("newBornHealthStatus") String newBornHealthStatus, @Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed, @Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode, @Param("deliveryConductedByID") Integer deliveryConductedByID,
			@Param("deliveryConductedBy") String deliveryConductedBy);

}
