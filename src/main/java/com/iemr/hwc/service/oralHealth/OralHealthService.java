package com.iemr.hwc.service.oralHealth;

import com.iemr.hwc.data.oralHealth.OralHealthDTO;
import com.iemr.hwc.data.oralHealth.OralHealthData;

import java.util.List;

public interface OralHealthService {

    List<OralHealthData> saveAll(List<OralHealthDTO> dtos, Integer userId,String userName);

    List<OralHealthDTO> getAll(Integer userId);
}