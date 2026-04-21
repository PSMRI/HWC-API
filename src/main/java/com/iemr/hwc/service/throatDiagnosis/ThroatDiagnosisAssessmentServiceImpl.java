package com.iemr.hwc.service.throatDiagnosis;

import com.iemr.hwc.data.throatDiagnosis.ThroatDiagnosisAssessment;
import com.iemr.hwc.data.throatDiagnosis.ThroatDiagnosisAssessmentDTO;
import com.iemr.hwc.repo.throatDiagnosis.ThroatDiagnosisAssessmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThroatDiagnosisAssessmentServiceImpl implements ThroatDiagnosisAssessmentService {

    @Autowired
    private ThroatDiagnosisAssessmentRepository repo;

    @Override
    public List<ThroatDiagnosisAssessmentDTO> saveAll(List<ThroatDiagnosisAssessmentDTO> dtos, Integer userId) {
        List<ThroatDiagnosisAssessment> entities = new ArrayList<>();

        for (ThroatDiagnosisAssessmentDTO dto : dtos) {
            dto.setUserId(userId);
            ThroatDiagnosisAssessment entity = new ThroatDiagnosisAssessment();
            BeanUtils.copyProperties(dto, entity);
            entities.add(entity);
        }

        List<ThroatDiagnosisAssessment> savedEntities = repo.saveAll(entities);
        List<ThroatDiagnosisAssessmentDTO> result = new ArrayList<>();

        for (ThroatDiagnosisAssessment entity : savedEntities) {
            ThroatDiagnosisAssessmentDTO dto = new ThroatDiagnosisAssessmentDTO();
            BeanUtils.copyProperties(entity, dto);
            result.add(dto);
        }

        return result;
    }

    @Override
    public List<ThroatDiagnosisAssessmentDTO> getAll(Integer userId) {
        List<ThroatDiagnosisAssessmentDTO> result = new ArrayList<>();

        for (ThroatDiagnosisAssessment entity : repo.findByUserId(userId)) {
            ThroatDiagnosisAssessmentDTO dto = new ThroatDiagnosisAssessmentDTO();
            BeanUtils.copyProperties(entity, dto);
            result.add(dto);
        }

        return result;
    }
}
