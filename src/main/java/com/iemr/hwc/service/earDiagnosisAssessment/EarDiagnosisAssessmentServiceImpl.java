package com.iemr.hwc.service.earDiagnosisAssessment;

import com.iemr.hwc.data.earDiagnosis.EarDiagnosisAssessment;
import com.iemr.hwc.data.earDiagnosis.EarDiagnosisAssessmentDTO;
import com.iemr.hwc.repo.earDiagnosisAssessment.EarDiagnosisAssessmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EarDiagnosisAssessmentServiceImpl implements EarDiagnosisAssessmentService {

    @Autowired
    private EarDiagnosisAssessmentRepository repository;

    @Override
    public List<EarDiagnosisAssessmentDTO> saveAll(List<EarDiagnosisAssessmentDTO> dtos) {

        List<EarDiagnosisAssessment> entities = new ArrayList<>();

        for (EarDiagnosisAssessmentDTO dto : dtos) {
            EarDiagnosisAssessment entity = new EarDiagnosisAssessment();
            BeanUtils.copyProperties(dto, entity);
            entities.add(entity);
        }

        List<EarDiagnosisAssessment> saved = repository.saveAll(entities);

        List<EarDiagnosisAssessmentDTO> result = new ArrayList<>();

        for (EarDiagnosisAssessment entity : saved) {
            EarDiagnosisAssessmentDTO dto = new EarDiagnosisAssessmentDTO();
            BeanUtils.copyProperties(entity, dto);
            result.add(dto);
        }

        return result;
    }

    @Override
    public List<EarDiagnosisAssessmentDTO> getAll(String userName) {

        List<EarDiagnosisAssessment> list = repository.findAll();

        List<EarDiagnosisAssessmentDTO> result = new ArrayList<>();

        for (EarDiagnosisAssessment entity : list) {
            EarDiagnosisAssessmentDTO dto = new EarDiagnosisAssessmentDTO();
            BeanUtils.copyProperties(entity, dto);
            result.add(dto);
        }

        return result;
    }
}