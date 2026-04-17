package com.iemr.hwc.controller.earDiagnosisAssessment;

import com.iemr.hwc.data.earDiagnosis.EarDiagnosisAssessmentDTO;
import com.iemr.hwc.service.earDiagnosisAssessment.EarDiagnosisAssessmentService;
import com.iemr.hwc.utils.JwtUtil;
import com.iemr.hwc.utils.response.OutputResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/earDiagnosis")
public class EarDiagnosisAssessmentController {

    @Autowired
    private EarDiagnosisAssessmentService service;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Save all ear diagnosis assessment data")
    @RequestMapping(value = {"/saveAll"}, method = {RequestMethod.POST})
    public String saveAllEarDiagnosis(
            @RequestBody List<EarDiagnosisAssessmentDTO> dtos,
            @RequestHeader(value = "Authorization") String Authorization) {

        OutputResponse response = new OutputResponse();

        try {

            if (dtos != null && !dtos.isEmpty()) {

                List<EarDiagnosisAssessmentDTO> savedData = service.saveAll(dtos);

                if (savedData != null)
                    response.setResponse(savedData.toString());
                else
                    response.setError(5000, "Saving data failed");

            } else {
                response.setError(5000, "Invalid/NULL request body");
            }

        } catch (Exception e) {
            response.setError(5000, "Error in saving ear diagnosis data : " + e);
        }

        return response.toString();
    }

    @Operation(summary = "Get all ear diagnosis assessment data")
    @RequestMapping(value = {"/getAll"}, method = {RequestMethod.POST})
    public String getAllEarDiagnosis(
            @RequestHeader(value = "jwttoken") String jwttoken) {

        OutputResponse response = new OutputResponse();

        try {

            if (jwttoken != null) {

                List<EarDiagnosisAssessmentDTO> data = service.getAll(jwtUtil.extractUsername(jwttoken));

                if (data != null && !data.isEmpty())
                    response.setResponse(data.toString());
                else
                    response.setError(5000, "No record found");

            } else {
                response.setError(5000, "Invalid/NULL token");
            }

        } catch (Exception e) {
            response.setError(5000, "Error in getting ear diagnosis data : " + e);
        }

        return response.toString();
    }
}