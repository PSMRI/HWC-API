package com.iemr.mmu.repo.neonatal;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.neonatal.ImmunizationServices;

@Repository
public interface ImmunizationServicesRepo extends CrudRepository<ImmunizationServices, Long> {
	List<ImmunizationServices> findByBeneficiaryRegIDAndVisitCodeAndDeleted(long benRegId, long visitCode,
			boolean deleted);

	List<ImmunizationServices> findByBeneficiaryRegIDAndDeleted(long benRegId, boolean deleted);

	@Transactional
	@Modifying
	@Query(" UPDATE ImmunizationServices t SET t.deleted=:deleted WHERE t.beneficiaryRegID=:beneficiaryRegID"
			+ " AND t.visitCode=:visitCode ")
	public int updateDeletedFlag(@Param("deleted") Boolean deleted, @Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);
}
