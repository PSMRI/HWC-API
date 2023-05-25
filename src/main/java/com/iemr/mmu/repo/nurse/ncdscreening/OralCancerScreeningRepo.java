package com.iemr.mmu.repo.nurse.ncdscreening;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.ncdScreening.OralCancerScreening;

@Repository
public interface OralCancerScreeningRepo extends CrudRepository<OralCancerScreening, Long> {

//	@Query("SELECT db FROM OralCancerScreening db WHERE db.beneficiaryRegID = :beneficiaryRegID AND db.beneficiaryVisitId = :beneficiaryVisitId ")
//	OralCancerScreening fetchOralCancerScreeningDetails(@Param("beneficiaryRegID") Long beneficiaryRegID,
//			@Param("beneficiaryVisitId") Long beneficiaryVisitId);

	public OralCancerScreening findByBeneficiaryRegIdAndVisitcode(Long beneficiaryRegId, Long visitcode);

	@Transactional
	@Modifying
	@Query(" UPDATE OralCancerScreening SET confirmed=:confirmed WHERE beneficiaryRegId = :beneficiaryRegId AND visitcode = :visitcode ")
	public int updateOralScreeningConfirmedStatus(@Param("beneficiaryRegId") Long beneficiaryRegId,
			@Param("visitcode") Long visitcode, @Param("confirmed") Boolean confirmed);
}
