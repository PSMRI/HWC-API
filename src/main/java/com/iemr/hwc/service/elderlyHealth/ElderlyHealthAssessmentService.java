package com.iemr.hwc.service.elderlyHealth;

import com.iemr.hwc.data.elderlyHealth.ElderlyHealthAssessmentDTO;

import java.util.List;

public interface ElderlyHealthAssessmentService {

    List<ElderlyHealthAssessmentDTO> saveAll(List<ElderlyHealthAssessmentDTO> dtos, Integer userId);

    List<ElderlyHealthAssessmentDTO> getAll(Integer userId);
}
