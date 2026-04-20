package com.iemr.hwc.controller.painSymptom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.painSymptom.PainSymptomAssessmentDTO;
import com.iemr.hwc.data.painSymptom.PainSymptomData;
import com.iemr.hwc.service.painSymptomAssessment.PainSymptomAssessmentService;
import com.iemr.hwc.service.painSymptomAssessment.PainSymptomAssessmentServiceImpl;
import com.iemr.hwc.utils.JwtUtil;
import com.iemr.hwc.utils.response.OutputResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/painSymptom")
public class PainSymptomController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PainSymptomAssessmentService painSymptomAssessmentService;

    @PostMapping("/saveAll")
    public String saveAll(@RequestBody List<PainSymptomAssessmentDTO> request,
                          @RequestHeader("Authorization") String token) {

        OutputResponse response = new OutputResponse();

        try {
            if(request!=null){
                painSymptomAssessmentService.saveAll(request);
                response.setResponse("Saved Successfully");

            }else {
                response.setError(5000, "Invalid request found");

            }
        } catch (Exception e) {
            response.setError(5000, e.getMessage());
        }

        return response.toString();
    }

    @PostMapping("/getAll")
    public String getAll(@RequestHeader("jwttoken") String token) {

        OutputResponse response = new OutputResponse();

        try {
            String user = jwtUtil.extractUsername(token);
            if (user != null) {
                List<PainSymptomAssessmentDTO> res = painSymptomAssessmentService.getPain(user);
                if (res != null) {
                    Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy h:mm:ss a").create();
                    response.setResponse(gson.toJson(res));
                } else {
                    response.setError(5000, "No record found");

                }
            } else {
                response.setError(5000, "No record found");

            }


        } catch (Exception e) {
            response.setError(5000, e.getMessage());
        }

        return response.toString();
    }

}
