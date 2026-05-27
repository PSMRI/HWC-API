package com.iemr.hwc.repo.labModule;

import com.iemr.hwc.data.labModule.ProcedureData;
import com.iemr.hwc.data.labModule.ProcedureField;
import com.iemr.hwc.data.labModule.ProcedureFieldsResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcedureRepository extends JpaRepository<ProcedureField, Integer> {

}