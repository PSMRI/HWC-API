package com.iemr.hwc.service.oralHealth;

import com.iemr.hwc.data.oralHealth.OralHealthDTO;
import java.util.List;

public interface OralHealthService {

    String saveAll(List<OralHealthDTO> dtos, String user);

    List<OralHealthDTO> getAll(String user);
}