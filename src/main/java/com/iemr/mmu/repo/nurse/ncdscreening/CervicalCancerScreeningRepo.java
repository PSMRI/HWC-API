package com.iemr.mmu.repo.nurse.ncdscreening;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.ncdScreening.CervicalCancerScreening;

@Repository
public interface CervicalCancerScreeningRepo extends CrudRepository<CervicalCancerScreening, Long> {

//	@Query("SELECT db FROM CervicalCancerScreening db WHERE db.beneficiaryRegID = :beneficiaryRegID AND db.beneficiaryVisitId = :beneficiaryVisitId ")
//	CervicalCancerScreening fetchCervicalCancerScreeningDetails(@Param("beneficiaryRegID") Long beneficiaryRegID,
//			@Param("beneficiaryVisitId") Long beneficiaryVisitId);

	public CervicalCancerScreening findByBeneficiaryRegIdAndVisitcode(Long beneficiaryRegId, Long visitcode);

	@Transactional
	@Modifying
	@Query(" UPDATE CervicalCancerScreening SET confirmed=:confirmed WHERE beneficiaryRegId = :beneficiaryRegId AND visitcode = :visitcode ")
	public int updateCervicalScreeningConfirmedStatus(@Param("beneficiaryRegId") Long beneficiaryRegId,
			@Param("visitcode") Long visitcode, @Param("confirmed") Boolean confirmed);
}
