package com.iemr.hwc.service.throatDiagnosis;

import com.iemr.hwc.data.throatDiagnosis.ThroatDiagnosisAssessment;
import com.iemr.hwc.data.throatDiagnosis.ThroatDiagnosisAssessmentDTO;
import com.iemr.hwc.repo.throatDiagnosis.ThroatDiagnosisAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThroatDiagnosisAssessmentServiceImpl implements ThroatDiagnosisAssessmentService {

    @Autowired
    private ThroatDiagnosisAssessmentRepository repo;

    @Override
    public String saveAll(List<ThroatDiagnosisAssessmentDTO> dtos, String user) {

        List<ThroatDiagnosisAssessment> list = dtos.stream().map(dto -> {
            ThroatDiagnosisAssessment e = new ThroatDiagnosisAssessment();

            e.setId(dto.getId());
            e.setPatientId(dto.getPatientId());
            e.setBenVisitNo(dto.getBenVisitNo());
            e.setSymptoms(dto.getSymptoms());
            e.setNeckSwelling(dto.getNeckSwelling());
            e.setDifficultySwallowing(dto.getDifficultySwallowing());
            e.setTonsillitis(dto.getTonsillitis());
            e.setPharyngitis(dto.getPharyngitis());
            e.setLaryngitis(dto.getLaryngitis());
            e.setSinusitis(dto.getSinusitis());
            e.setCleftLip(dto.getCleftLip());
            e.setCleftPalate(dto.getCleftPalate());

            // 🔥 audit
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
    public List<ThroatDiagnosisAssessmentDTO> getAll(String user) {

        return repo.findAll().stream().map(e -> {
            ThroatDiagnosisAssessmentDTO dto = new ThroatDiagnosisAssessmentDTO();

            dto.setId(e.getId());
            dto.setPatientId(e.getPatientId());
            dto.setBenVisitNo(e.getBenVisitNo());
            dto.setSymptoms(e.getSymptoms());
            dto.setNeckSwelling(e.getNeckSwelling());
            dto.setDifficultySwallowing(e.getDifficultySwallowing());
            dto.setTonsillitis(e.getTonsillitis());
            dto.setPharyngitis(e.getPharyngitis());
            dto.setLaryngitis(e.getLaryngitis());
            dto.setSinusitis(e.getSinusitis());
            dto.setCleftLip(e.getCleftLip());
            dto.setCleftPalate(e.getCleftPalate());

            dto.setCreatedBy(e.getCreatedBy());
            dto.setCreatedDate(e.getCreatedDate());
            dto.setUpdatedBy(e.getUpdatedBy());
            dto.setUpdatedDate(e.getUpdatedDate());

            return dto;
        }).toList();
    }
}