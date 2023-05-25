package com.iemr.mmu.repo.registrar;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.data.registrar.BeneficiaryDemographicAdditional;

@Repository
@RestResource(exported = false)
public interface BeneficiaryDemographicAdditionalRepo extends CrudRepository<BeneficiaryDemographicAdditional, Long> {

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryDemographicAdditional set literacyStatus = :literacyStatus, motherName =:motherName, emailID = :emailID, bankName = :bankName,"
			+ " branchName = :branchName, iFSCCode = :iFSCCode, accountNo = :accountNo, modifiedBy = :modifiedBy where "
			+ " beneficiaryRegID = :beneficiaryRegID ")
	public Integer updateBeneficiaryDemographicAdditional(@Param("literacyStatus") String literacyStatus,
			@Param("motherName") String motherName,
			@Param("emailID") String emailID,
			@Param("bankName") String bankName,
			@Param("branchName") String branchName,
			@Param("iFSCCode") String iFSCCode,
			@Param("accountNo") String accountNo,
			@Param("modifiedBy") String modifiedBy,
			@Param("beneficiaryRegID") Long beneficiaryRegID);
	
	@Query(" SELECT bvd from BeneficiaryDemographicAdditional bvd WHERE bvd.beneficiaryRegID = :benRegID")
	public BeneficiaryDemographicAdditional getBeneficiaryDemographicAdditional(@Param("benRegID") Long benRegID);

}
