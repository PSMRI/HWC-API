package com.iemr.hwc.service.mentalHealth;

import com.iemr.hwc.data.mentalHealth.MentalHealthScreeningDTO;

import java.util.List;

public interface MentalHealthScreeningService {

    List<MentalHealthScreeningDTO> saveAll(List<MentalHealthScreeningDTO> dtos, Integer userId);

    List<MentalHealthScreeningDTO> getAll(Integer userId);
}
