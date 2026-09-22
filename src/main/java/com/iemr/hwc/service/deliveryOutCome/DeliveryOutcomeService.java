package com.iemr.hwc.service.deliveryOutCome;

import com.iemr.hwc.data.deliveryOutcome.DeliveryOutcomeDTO;
import com.iemr.hwc.data.requestDTO.GetBenRequestHandler;

import java.util.List;

public interface DeliveryOutcomeService {

    String registerDeliveryOutcome(List<DeliveryOutcomeDTO> deliveryOutcomeDTOS);

    List<DeliveryOutcomeDTO> getDeliveryOutcome(String userName);
}
