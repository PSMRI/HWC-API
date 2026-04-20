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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/opthalmicVisit")
public class OpthalmicVisitController {

    @Autowired
    private  OphthalmicVisitService service;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Save all ophthalmic visit data")
    @RequestMapping(value = { "/saveAll" }, method = { RequestMethod.POST })
    public ResponseEntity<Map<String, Object>> saveAllOphthalmicVisit(
            @RequestBody List<OphthalmicVisitDTO> dtos,
            @RequestHeader(value = "jwtToken") String token) {

        Map<String, Object> response = new HashMap<>();

        try {

            if (dtos != null && !dtos.isEmpty()) {
                if(token!=null){
                    Integer userId = jwtUtil.extractUserId(token);
                    List<OphthalmicVisitDTO> savedData = service.saveAll(dtos,userId);

                    if (savedData != null){
                        response.put("message","Record save successfully");
                        response.put("statusCode",200);
                        response.put("data",savedData);
                    }else {
                        response.put("statusCode",5000);
                        response.put("message","Saving data failed");
                    }
                }




            } else {
                response.put("statusCode",5000);
                response.put("message","Invalid/NULL request body");
            }

        } catch (Exception e) {
            response.put("statusCode",5000);
            response.put("message","Error in saving ophthalmic visit data : " + e);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all ophthalmic visit data")
    @RequestMapping(value = { "/getAll" }, method = { RequestMethod.POST })
    public ResponseEntity<Map<String, Object>> getAllOphthalmicVisit(@RequestHeader(value = "jwttoken") String jwttoken) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (jwttoken != null) {

                List<OphthalmicVisitDTO> data = service.getAll(jwtUtil.extractUserId(jwttoken));

                if (data != null && !data.isEmpty()){
                    response.put("statusCode",200);
                    response.put("data",data);
                }else {
                    response.put("statusCode",5000);
                    response.put("message", "No record found");

                }


            } else {
                response.put("statusCode",5000);
                response.put("message", "Invalid/NULL token");
            }

        } catch (Exception e) {
            response.put("statusCode",5000);
            response.put("message", "Error in getting ophthalmic visit data : " + e);
        }

        return ResponseEntity.ok(response);
    }


}
