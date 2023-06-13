/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.registrar;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.registrar.BeneficiaryPhoneMapping;
@Repository
@RestResource(exported = false)
public interface RegistrarRepoBenPhoneMapData extends CrudRepository<BeneficiaryPhoneMapping, Long> {

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryPhoneMapping set phoneNo = :phoneNo, modifiedBy = :modifiedBy where benificiaryRegID = :benificiaryRegID ")
	public Integer updateBenPhoneMap(@Param("phoneNo") String phoneNo,
			@Param("modifiedBy") String modifiedBy,
			@Param("benificiaryRegID") Long benificiaryRegID);
	
}
