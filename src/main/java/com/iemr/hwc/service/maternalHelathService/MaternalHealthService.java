package com.iemr.hwc.service.maternalHelathService;

import com.iemr.hwc.data.anc.ANCVisitDTO;
import com.iemr.hwc.data.pregnantWomen.PregnantWomanDTO;
import com.iemr.hwc.data.requestDTO.GetBenRequestHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MaternalHealthService {


    List<ANCVisitDTO> getANCVisits(String userName);

    String saveANCVisit(List<ANCVisitDTO> ancVisitDTOs, Integer useId);

    String registerPregnantWoman(List<PregnantWomanDTO> pregnantWomanDTOs);

    List<PregnantWomanDTO> getPregnantWoman(String  userName);
}
