package com.iemr.hwc.service.procdureFieldsService;

import com.iemr.hwc.data.labModule.ProcedureField;
import com.iemr.hwc.data.labModule.ProcedureFieldsResponseDTO;
import com.iemr.hwc.repo.labModule.ProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProcedureService {

    @Autowired
    private ProcedureRepository repository;

    public List<ProcedureField> getProcedureField() {

        return repository.findAll();
    }
}