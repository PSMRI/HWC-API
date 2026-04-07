package com.iemr.hwc.service.EligibleCouple;


import com.iemr.hwc.data.eligibleCouple.EligibleCoupleDTO;
import com.iemr.hwc.data.eligibleCouple.EligibleCoupleTrackingDTO;
import com.iemr.hwc.data.requestDTO.GetBenRequestHandler;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CoupleService {


    String registerEligibleCouple(List<EligibleCoupleDTO> eligibleCoupleDTOs, MultipartFile kitPhoto1, MultipartFile kitPhoto2);

    String getEligibleCoupleRegRecords(GetBenRequestHandler dto);


    List<EligibleCoupleTrackingDTO> getEligibleCoupleTracking(GetBenRequestHandler requestDTO);

    String registerEligibleCoupleTracking(List<EligibleCoupleTrackingDTO> eligibleCoupleTrackingDTOs);
}
