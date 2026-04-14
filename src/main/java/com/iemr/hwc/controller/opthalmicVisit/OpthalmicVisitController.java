package com.iemr.hwc.controller.opthalmicVisit;


import com.iemr.hwc.data.opthalmic.OphthalmicVisitDTO;
import com.iemr.hwc.service.opthalmicVisit.OphthalmicVisitService;
import com.iemr.hwc.utils.JwtUtil;
import com.iemr.hwc.utils.response.OutputResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/opthalmicVisit")
public class OpthalmicVisitController {

    @Autowired
    private  OphthalmicVisitService service;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Save all ophthalmic visit data")
    @RequestMapping(value = { "/saveAll" }, method = { RequestMethod.POST })
    public String saveAllOphthalmicVisit(
            @RequestBody List<OphthalmicVisitDTO> dtos,
            @RequestHeader(value = "Authorization") String Authorization) {

        OutputResponse response = new OutputResponse();

        try {

            if (dtos != null && !dtos.isEmpty()) {

                List<OphthalmicVisitDTO> savedData = service.saveAll(dtos);

                if (savedData != null)
                    response.setResponse(savedData.toString());
                else
                    response.setError(5000, "Saving data failed");

            } else {
                response.setError(5000, "Invalid/NULL request body");
            }

        } catch (Exception e) {
            response.setError(5000, "Error in saving ophthalmic visit data : " + e);
        }

        return response.toString();
    }

    @Operation(summary = "Get all ophthalmic visit data")
    @RequestMapping(value = { "/getAll" }, method = { RequestMethod.POST })
    public String getAllOphthalmicVisit(@RequestHeader(value = "jwttoken") String jwttoken) {

        OutputResponse response = new OutputResponse();

        try {
            if (jwttoken != null) {

                List<OphthalmicVisitDTO> data = service.getAll(jwtUtil.extractUsername(jwttoken));

                if (data != null && !data.isEmpty())
                    response.setResponse(data.toString());
                else
                    response.setError(5000, "No record found");

            } else {
                response.setError(5000, "Invalid/NULL token");
            }

        } catch (Exception e) {
            response.setError(5000, "Error in getting ophthalmic visit data : " + e);
        }

        return response.toString();
    }


}
