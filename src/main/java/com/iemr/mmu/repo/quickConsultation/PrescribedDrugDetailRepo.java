package com.iemr.mmu.repo.quickConsultation;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.quickConsultation.PrescribedDrugDetail;

@Repository
@RestResource(exported = false)
public interface PrescribedDrugDetailRepo extends CrudRepository<PrescribedDrugDetail, Long> {

	@Query(" SELECT id, prescriptionID, formName, drugTradeOrBrandName, drugID, drugName, "
			+ " drugStrength, dose, route, frequency, duration, unit, relationToFood, instructions, qtyPrescribed, isEDL, "
			+ " sctCode, sctTerm, createdDate FROM PrescribedDrugDetail  WHERE beneficiaryRegID =:beneficiaryRegID "
			+ " AND visitCode=:visitCode AND deleted = false ")
	public ArrayList<Object[]> getBenPrescribedDrugDetails(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);
	
	@Query(" SELECT a FROM PrescribedDrugDetail a WHERE a.beneficiaryRegID =:beneficiaryRegID "
			+ " AND a.visitCode=:visitCode AND a.deleted = false ")
	public ArrayList<PrescribedDrugDetail> getBenPrescribedDrugDetails2(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);
	@Modifying
	@Transactional
	@Query(" UPDATE  PrescribedDrugDetail set deleted = true where id =:id ")
	public int deletePrescribedmedicine(@Param("id") Long id);
	
	@Query(" SELECT p FROM PrescribedDrugDetail p WHERE p.prescriptionID =:prescriptionID AND p.deleted = false ")
	public List<PrescribedDrugDetail> getPrescriptionDetails(@Param("prescriptionID") Long prescriptionID);
}
