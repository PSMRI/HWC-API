package com.iemr.hwc.repo.procedureCompMapMaster;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.hwc.data.labModule.ProcedureComponentMapping;


@Repository
@RestResource(exported = false)
public interface ProcedureCompMappedMasterRepo extends CrudRepository<ProcedureComponentMapping, Integer> {


//	@Query(nativeQuery = true, value ="SELECT * from  ProcedureComponentMapping pcm"
//			+ "inner join pcm.procMaster p on p.procedureid=pcm.procedureid "
//			+ " inner join pcm.compMaster t on t.TestComponentID=pcm.TestComponentID"
//			+ " where pcm.deleted is false and p.Deleted is false and t.deleted is false "
//			+ " and pcm.providerservicemapid= :providerServiceMapID ")
//	public ArrayList<Object[]> getProcedureComponentMappedMasterData(@Param("providerServiceMapID") Long providerServiceMapID);

	
	@Query(nativeQuery = true, value = "SELECT pcm.ProcedureComponentMapID, pcm.procedureID, pcm.TestComponentID, "
			+ "pcm.providerservicemapid, pcm.deleted, pcm.processed, p.procedureName, p.procedureDesc, "
			+ "p.procedureType, p.gender, p.IOTProcedureID, p.isMandatory, p.isCalibration, t.testComponentName, "
			+ "t.testComponentDesc, t.loinc_num, t.loinc_component, t.inputType, t.measurementUnit, "
			+ "t.isDecimal, t.range_min, t.range_normal_min, t.range_normal_max, t.range_max "
			+ "FROM m_procedurecomponentmap pcm "
			+ "INNER JOIN m_procedure p on p.procedureid=pcm.procedureid "
			+ "INNER JOIN m_testcomponent t on t.TestComponentID=pcm.TestComponentID "
			+ "where pcm.deleted is false and p.Deleted is false and t.deleted is false "
			+ "and pcm.providerservicemapid= :providerServiceMapID ")
	public ArrayList<Object[]> getProcedureComponentMappedMasterData(@Param("providerServiceMapID") Long providerServiceMapID);

//	@Query(nativeQuery = true, value ="SELECT pcm.ProcedureComponentMapID, pcm.procedureid, pcm.TestComponentID, "
//			+ "pcm.providerservicemapid, pcm.deleted, pcm.processed, p.procedureName, p.procedureDesc, "
//			+ "p.procedureType, p.gender, p.IOTProcedureID, p.isMandatory, p.isCalibration, t.testComponentName, "
//			+ "t.testComponentDesc, t.loinc_num, t.loinc_component, t.inputType, t.measurementUnit, "
//			+ "t.isDecimal, t.range_min, t.range_normal_min, t.range_normal_max, t.range_max "
//			+ "FROM ProcedureComponentMapping pcm "
//			+ "INNER JOIN pcm.procMaster p on p.procedureid=pcm.procedureid "
//			+ "INNER JOIN pcm.compMaster t on t.TestComponentID=pcm.TestComponentID "
//			+ "where pcm.deleted is false and p.Deleted is false and t.deleted is false "
//			+ "and pcm.providerservicemapid= :providerServiceMapID ")
//	public ArrayList<Object[]> getProcedureComponentMappedMasterData(@Param("providerServiceMapID") Long providerServiceMapID);

}
