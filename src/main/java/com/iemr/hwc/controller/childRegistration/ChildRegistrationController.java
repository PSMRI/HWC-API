package com.iemr.hwc.controller.childRegistration;

import com.iemr.hwc.data.childRegistration.ChildRegisterDTO;
import com.iemr.hwc.data.requestDTO.GetBenRequestHandler;
import com.iemr.hwc.service.childRegistration.ChildService;
import com.iemr.hwc.utils.JwtUtil;
import com.iemr.hwc.utils.response.OutputResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value = "/child", consumes = "application/json", produces = "application/json")
public class ChildRegistrationController {

    private final Logger logger = LoggerFactory.getLogger(ChildRegistrationController.class);



    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private ChildService childService;


    @Operation(summary = "get child register data of all beneficiaries registered with given user id")
    @RequestMapping(value = { "/getAll" }, method = { RequestMethod.POST })
    public String getAllChildRegisterDetails(@RequestBody GetBenRequestHandler requestDTO,
                                             @RequestHeader(value = "Authorization") String Authorization) {
        OutputResponse response = new OutputResponse();
        try {
            if (requestDTO != null) {
                logger.info("request object with timestamp : " + new Timestamp(System.currentTimeMillis()) + " "
                        + requestDTO);
                String s = childService.getByUserId(requestDTO);
                if (s != null)
                    response.setResponse(s);
                else
                    response.setError(5000, "No record found");
            } else
                response.setError(5000, "Invalid/NULL request obj");
        } catch (Exception e) {
            logger.error("Error in child register get data : " + e);
            response.setError(5000, "Error in child register get data : " + e);
        }
        return response.toString();
    }

    @Operation(summary = "save child register data of all beneficiaries registered with given user id")
    @RequestMapping(value = { "/saveAll" }, method = { RequestMethod.POST })
    public String saveAllChildDetails(@RequestBody List<ChildRegisterDTO> childRegisterDTOs,
                                      @RequestHeader(value = "Authorization") String Authorization) {
        OutputResponse response = new OutputResponse();
        try {

            if (childRegisterDTOs.size() != 0) {
                logger.info("Saving Child Register with timestamp : " + new Timestamp(System.currentTimeMillis()));
                String s = childService.save(childRegisterDTOs);
                if (s != null)
                    response.setResponse(s);
                else
                    response.setError(5000, "Saving child register data to db failed");
            } else
                response.setError(5000, "Invalid/NULL request obj");
        } catch (Exception e) {
            logger.error("Error in save child register details : " + e);
            response.setError(5000, "Error in save child register details : " + e);
        }
        return response.toString();
    }

}
