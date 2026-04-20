package com.iemr.hwc.service.noiseDignosisAssessgment;

import com.iemr.hwc.data.noiseDiagnosis.NoseDiagnosisAssessment;
import com.iemr.hwc.data.noiseDiagnosis.NoseDiagnosisAssessmentDTO;
import com.iemr.hwc.repo.noiseDiagnosisAssessment.NoseDiagnosisAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoseDiagnosisAssessmentServiceImpl implements NoseDiagnosisAssessmentService {

    @Autowired
    private NoseDiagnosisAssessmentRepository repo;

    @Override
    public String saveAll(List<NoseDiagnosisAssessmentDTO> dtos, String user) {

        List<NoseDiagnosisAssessment> list = dtos.stream().map(dto -> {
            NoseDiagnosisAssessment e = new NoseDiagnosisAssessment();

            e.setId(dto.getId());
            e.setPatientId(dto.getPatientId());
            e.setBenVisitNo(dto.getBenVisitNo());
            e.setDifficultyBreathing(dto.getDifficultyBreathing());
            e.setOpenMouthBreathing(dto.getOpenMouthBreathing());
            e.setNoseBleed(dto.getNoseBleed());
            e.setSystolicBp(dto.getSystolicBp());
            e.setDiastolicBp(dto.getDiastolicBp());
            e.setForeignBodyNose(dto.getForeignBodyNose());
            e.setSinusitis(dto.getSinusitis());

            e.setCreatedBy(user);
            e.setCreatedDate(System.currentTimeMillis());
            e.setUpdatedBy(user);
            e.setUpdatedDate(System.currentTimeMillis());

            return e;
        }).toList();

        repo.saveAll(list);

        return "Saved Successfully";
    }

    @Override
    public List<NoseDiagnosisAssessmentDTO> getAll(String user) {

        return repo.findAll().stream().map(e -> {
            NoseDiagnosisAssessmentDTO dto = new NoseDiagnosisAssessmentDTO();

            dto.setId(e.getId());
            dto.setPatientId(e.getPatientId());
            dto.setBenVisitNo(e.getBenVisitNo());
            dto.setDifficultyBreathing(e.getDifficultyBreathing());
            dto.setOpenMouthBreathing(e.getOpenMouthBreathing());
            dto.setNoseBleed(e.getNoseBleed());
            dto.setSystolicBp(e.getSystolicBp());
            dto.setDiastolicBp(e.getDiastolicBp());
            dto.setForeignBodyNose(e.getForeignBodyNose());
            dto.setSinusitis(e.getSinusitis());

            dto.setCreatedBy(e.getCreatedBy());
            dto.setCreatedDate(e.getCreatedDate());
            dto.setUpdatedBy(e.getUpdatedBy());
            dto.setUpdatedDate(e.getUpdatedDate());

            return dto;
        }).toList();
    }
}