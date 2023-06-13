/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.repo.registrar;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.registrar.BeneficiaryImage;

@Repository
@RestResource(exported = false)
public interface BeneficiaryImageRepo extends CrudRepository<BeneficiaryImage, Long> {
	@Query(" SELECT benImage from BeneficiaryImage where beneficiaryRegID =:benRegID ")
	public String getBenImage(@Param("benRegID") Long benRegID);

	@Query(" SELECT beneficiaryRegID from BeneficiaryImage where beneficiaryRegID =:benRegID ")
	public Long findBenImage(@Param("benRegID") Long benRegID);

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryImage set benImage = :benImage, modifiedBy = :modifiedBy where beneficiaryRegID = :beneficiaryRegID ")
	public Integer updateBeneficiaryImage(@Param("benImage") String benImage, @Param("modifiedBy") String modifiedBy,
			@Param("beneficiaryRegID") Long beneficiaryRegID);

}
