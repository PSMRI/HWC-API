package com.iemr.hwc.controller.eligibleCouple;

import com.iemr.hwc.data.eligibleCouple.EligibleCoupleDTO;
import com.iemr.hwc.service.EligibleCouple.CoupleService;
import com.iemr.hwc.utils.response.OutputResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping(value = "/couple", headers = "Authorization")
public class EligibleCoupleController {
    private final Logger logger = LoggerFactory.getLogger(EligibleCoupleController.class);
    @Autowired
    private CoupleService coupleService;

    @Operation(summary = "save eligible couple registration details")
    @RequestMapping(value = { "/register/saveAll" }, method = { RequestMethod.POST },consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveEligibleCouple(@RequestPart("data") List<EligibleCoupleDTO> eligibleCoupleDTOs,
                                     @RequestPart(value = "kitPhoto1", required = false) MultipartFile kitPhoto1,
                                     @RequestPart(value = "kitPhoto2", required = false) MultipartFile kitPhoto2,
                                     @RequestHeader(value = "Authorization") String Authorization) {
        OutputResponse response = new OutputResponse();
        try {
            logger.info("Saving All Eligible Couple Details");
            if (eligibleCoupleDTOs != null) {
                String s = coupleService.registerEligibleCouple(eligibleCoupleDTOs,kitPhoto1,kitPhoto2);
                if (s != null)
                    response.setResponse(s);
                else
                    response.setError(500, "No record found");
            } else
                response.setError(500, "Invalid/NULL request obj");
        } catch (Exception e) {
            logger.error("Error in saving eligible couple registration details, " + e);
            response.setError(500, "Error in saving eligible couple registration details" + e);
        }
        return response.toString();
    }

}
