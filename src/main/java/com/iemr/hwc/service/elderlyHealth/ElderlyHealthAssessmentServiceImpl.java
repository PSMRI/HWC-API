package com.iemr.hwc.service.elderlyHealth;

import com.iemr.hwc.data.elderlyHealth.ElderlyHealthAssessment;
import com.iemr.hwc.data.elderlyHealth.ElderlyHealthAssessmentDTO;
import com.iemr.hwc.repo.elderlyHealth.ElderlyHealthAssessmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ElderlyHealthAssessmentServiceImpl implements ElderlyHealthAssessmentService {

    @Autowired
    private ElderlyHealthAssessmentRepository repository;

    @Override
    public List<ElderlyHealthAssessmentDTO> saveAll(List<ElderlyHealthAssessmentDTO> dtos, Integer userId) {
        List<ElderlyHealthAssessment> entities = new ArrayList<>();

        for (ElderlyHealthAssessmentDTO dto : dtos) {
            dto.setUserId(userId);
            ElderlyHealthAssessment entity = new ElderlyHealthAssessment();
            BeanUtils.copyProperties(dto, entity);

            if (entity.getAssessmentId() == null && dto.getPatientId() != null && dto.getBenVisitNo() != null) {
                Optional<ElderlyHealthAssessment> existing =
                        repository.findByPatientIdAndBenVisitNo(dto.getPatientId(), dto.getBenVisitNo());
                existing.ifPresent(value -> entity.setAssessmentId(value.getAssessmentId()));
            }

            entities.add(entity);
        }

        List<ElderlyHealthAssessment> savedEntities = repository.saveAll(entities);
        List<ElderlyHealthAssessmentDTO> result = new ArrayList<>();

        for (ElderlyHealthAssessment entity : savedEntities) {
            ElderlyHealthAssessmentDTO dto = new ElderlyHealthAssessmentDTO();
            BeanUtils.copyProperties(entity, dto);
            result.add(dto);
        }

        return result;
    }

    @Override
    public List<ElderlyHealthAssessmentDTO> getAll(Integer userId) {
        List<ElderlyHealthAssessmentDTO> result = new ArrayList<>();

        for (ElderlyHealthAssessment entity : repository.findByUserId(userId)) {
            ElderlyHealthAssessmentDTO dto = new ElderlyHealthAssessmentDTO();
            BeanUtils.copyProperties(entity, dto);
            result.add(dto);
        }

        return result;
    }
}
