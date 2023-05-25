package com.iemr.mmu.repo.nurse.ncdscreening;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.ncdScreening.BreastCancerScreening;

@Repository
public interface BreastCancerScreeningRepo extends CrudRepository<BreastCancerScreening, Long> {

//	@Query("SELECT db FROM BreastCancerScreening db WHERE db.beneficiaryRegID = :beneficiaryRegID AND db.beneficiaryVisitId = :beneficiaryVisitId ")
//	BreastCancerScreening fetchBreastCancerScreeningDetails(@Param("beneficiaryRegID") Long beneficiaryRegID, @Param("beneficiaryVisitId") Long beneficiaryVisitId );

	public BreastCancerScreening findByBeneficiaryRegIdAndVisitcode(Long beneficiaryRegId, Long visitcode);

	@Transactional
	@Modifying
	@Query(" UPDATE BreastCancerScreening SET confirmed=:confirmed WHERE beneficiaryRegId = :beneficiaryRegId AND visitcode = :visitcode ")
	public int updateBreastScreeningConfirmedStatus(@Param("beneficiaryRegId") Long beneficiaryRegId,
			@Param("visitcode") Long visitcode, @Param("confirmed") Boolean confirmed);
}
