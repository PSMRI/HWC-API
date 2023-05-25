package com.iemr.mmu.repo.nurse.ncdscreening;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.ncdScreening.DiabetesScreening;

/**
 * 
 * @author SH20094090 17-08-2022
 */

@Repository
public interface DiabetesScreeningRepo extends CrudRepository<DiabetesScreening, Long> {
//
//	@Query("SELECT obj FROM Diabetes obj WHERE obj.beneficiaryRegId = :beneficiaryRegId AND "
//			+ "obj.beneficiaryVisitId = :beneficiaryVisitId")
//	DiabetesScreening fetchBeneficiaryDiabetesDetails(@Param("beneficiaryRegId") Long beneficiaryRegId,
//			@Param("beneficiaryVisitId") Long beneficiaryVisitId);

	public DiabetesScreening findByBeneficiaryRegIdAndVisitcode(Long beneficiaryRegId, Long visitCode);

	@Transactional
	@Modifying
	@Query(" UPDATE DiabetesScreening SET confirmed=:confirmed WHERE beneficiaryRegId = :beneficiaryRegId AND visitcode = :visitcode ")
	public int updateDiabetesScreeningConfirmedStatus(@Param("beneficiaryRegId") Long beneficiaryRegId,
			@Param("visitcode") Long visitcode, @Param("confirmed") Boolean confirmed);
}
