package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.DeliveryType;

@Repository
@RestResource(exported = false)
public interface DeliveryTypeRepo extends CrudRepository<DeliveryType, Short> {

	@Query("select deliveryTypeID, deliveryType from DeliveryType where deleted = false order by deliveryType")
	public ArrayList<Object[]> getDeliveryTypes();

	List<DeliveryType> findByDeleted(Boolean deleted);

}
