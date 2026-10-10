package com.iemr.hwc.service.noiseDignosisAssessgment;

import com.iemr.hwc.data.noiseDiagnosis.NoseDiagnosisAssessment;
import com.iemr.hwc.data.noiseDiagnosis.NoseDiagnosisAssessmentDTO;
import com.iemr.hwc.repo.noiseDiagnosisAssessment.NoseDiagnosisAssessmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoseDiagnosisAssessmentServiceImpl implements NoseDiagnosisAssessmentService {

    @Autowired
    private NoseDiagnosisAssessmentRepository repo;

    @Override
    public List<NoseDiagnosisAssessmentDTO> saveAll(List<NoseDiagnosisAssessmentDTO> dtos, Integer userId) {
        List<NoseDiagnosisAssessment> entities = new ArrayList<>();

        for (NoseDiagnosisAssessmentDTO dto : dtos) {
            dto.setUserId(userId);
            NoseDiagnosisAssessment entity = new NoseDiagnosisAssessment();
            BeanUtils.copyProperties(dto, entity);
            entities.add(entity);
        }

        List<NoseDiagnosisAssessment> savedEntities = repo.saveAll(entities);
        List<NoseDiagnosisAssessmentDTO> result = new ArrayList<>();

        for (NoseDiagnosisAssessment entity : savedEntities) {
            NoseDiagnosisAssessmentDTO dto = new NoseDiagnosisAssessmentDTO();
            BeanUtils.copyProperties(entity, dto);
            result.add(dto);
        }

        return result;
    }

    @Override
    public List<NoseDiagnosisAssessmentDTO> getAll(Integer userId) {
        List<NoseDiagnosisAssessmentDTO> result = new ArrayList<>();

        for (NoseDiagnosisAssessment entity : repo.findByUserId(userId)) {
            NoseDiagnosisAssessmentDTO dto = new NoseDiagnosisAssessmentDTO();
            BeanUtils.copyProperties(entity, dto);
            result.add(dto);
        }

        return result;
    }
}
