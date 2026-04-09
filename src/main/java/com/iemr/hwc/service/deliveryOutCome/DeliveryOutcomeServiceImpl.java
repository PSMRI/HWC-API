package com.iemr.hwc.service.deliveryOutCome;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.iemr.hwc.data.deliveryOutcome.DeliveryOutcome;
import com.iemr.hwc.data.deliveryOutcome.DeliveryOutcomeDTO;
import com.iemr.hwc.data.requestDTO.GetBenRequestHandler;
import com.iemr.hwc.repo.deliveryOutCome.DeliveryOutcomeRepo;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.Validate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DeliveryOutcomeServiceImpl implements DeliveryOutcomeService {

    @Autowired
    private DeliveryOutcomeRepo deliveryOutcomeRepo;


    private Gson gson = new Gson();

    private final Logger logger = LoggerFactory.getLogger(DeliveryOutcomeServiceImpl.class);

    ObjectMapper mapper = new ObjectMapper();

    ModelMapper modelMapper = new ModelMapper();

    boolean institutionalDelivery = false;

    @Override
    public String registerDeliveryOutcome(List<DeliveryOutcomeDTO> deliveryOutcomeDTOS) {

        try {
            List<DeliveryOutcome> delOutList = new ArrayList<>();
            deliveryOutcomeDTOS.forEach(it -> {
                DeliveryOutcome deliveryoutcome = deliveryOutcomeRepo.findDeliveryOutcomeByBenIdAndIsActive(it.getBenId(), true);

                if (deliveryoutcome != null) {
                    Long id = deliveryoutcome.getId();
                    modelMapper.map(it, deliveryoutcome);
                    deliveryoutcome.setId(id);
                } else {
                    deliveryoutcome = new DeliveryOutcome();
                    modelMapper.map(it, deliveryoutcome);
                    deliveryoutcome.setId(null);
                }
                delOutList.add(deliveryoutcome);
            });
            deliveryOutcomeRepo.saveAll(delOutList);

            return "no of delivery outcome details saved: " + delOutList.size();
        } catch (Exception e) {
            return "error while saving delivery outcome details: " + e.getMessage();
        }
    }

    @Override
    public List<DeliveryOutcomeDTO> getDeliveryOutcome(String userName) {
        try {
            List<DeliveryOutcome> deliveryOutcomeList = deliveryOutcomeRepo.getDeliveryOutcomeByAshaId(userName);
            logger.info("DeliveryOutcome Response{}",deliveryOutcomeList);
            return deliveryOutcomeList.stream()
                    .map(deliveryOutcome -> mapper.convertValue(deliveryOutcome, DeliveryOutcomeDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("DeliveryOutcome Exception:"+e.getMessage());
        }
        return null;
    }


}
