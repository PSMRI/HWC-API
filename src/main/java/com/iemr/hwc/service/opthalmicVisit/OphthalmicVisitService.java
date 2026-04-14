package com.iemr.hwc.service.opthalmicVisit;

import com.iemr.hwc.data.opthalmic.OphthalmicVisitDTO;

import java.util.List;

public interface OphthalmicVisitService {

    List<OphthalmicVisitDTO> saveAll(List<OphthalmicVisitDTO> dtos);

    List<OphthalmicVisitDTO> getAll(String userName);
}