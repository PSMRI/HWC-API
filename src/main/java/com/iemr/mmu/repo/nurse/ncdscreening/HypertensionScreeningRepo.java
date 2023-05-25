package com.iemr.mmu.repo.nurse.ncdscreening;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.ncdScreening.HypertensionScreening;

/**
 * 
 * @author SH20094090 17-08-2022
 */

@Repository
public interface HypertensionScreeningRepo extends CrudRepository<HypertensionScreening, Long> {
//	@Query("SELECT obj FROM Hypertension obj WHERE obj.beneficiaryRegId = :beneficiaryRegId AND "
//			+ "obj.beneficiaryVisitId = :beneficiaryVisitId")
//	HypertensionScreening fetchBeneficiaryHypertensionDetails(@Param("beneficiaryRegId") Long beneficiaryRegId,
//			@Param("beneficiaryVisitId") Long beneficiaryVisitId);

	@Query(value = " CALL db_iemr.Pr_ConfirmatoryScreening(:beneficiaryRegId) " + " ", nativeQuery = true)
	public List<Object[]> fetchConfirmedScreening(@Param("beneficiaryRegId") Long beneficiaryRegId);

	public HypertensionScreening findByBeneficiaryRegIdAndVisitcode(Long beneficiaryRegId, Long visitcode);

	@Transactional
	@Modifying
	@Query(" UPDATE HypertensionScreening SET confirmed=:confirmed WHERE beneficiaryRegId = :beneficiaryRegId AND visitcode = :visitcode ")
	public int updateHypertensionScreeningConfirmedStatus(@Param("beneficiaryRegId") Long beneficiaryRegId,
			@Param("visitcode") Long visitcode, @Param("confirmed") Boolean confirmed);

}
