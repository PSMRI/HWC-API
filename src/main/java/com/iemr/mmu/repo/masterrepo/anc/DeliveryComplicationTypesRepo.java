package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.DeliveryComplicationTypes;

@Repository
@RestResource(exported = false)
public interface DeliveryComplicationTypesRepo extends CrudRepository<DeliveryComplicationTypes, Short>{
	
	@Query("select deliveryComplicationID, deliveryComplicationType from DeliveryComplicationTypes where deleted = false order by deliveryComplicationType")
	public ArrayList<Object[]> getDeliveryComplicationTypes();
}
