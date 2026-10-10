package com.iemr.hwc.controller.procdureField;

import com.iemr.hwc.data.labModule.ProcedureField;
import com.iemr.hwc.data.labModule.ProcedureFieldsResponseDTO;
import com.iemr.hwc.service.procdureFieldsService.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value= "/procedureFields")
public class ProcedureController {

    @Autowired
    private ProcedureService service;

    @GetMapping("/fields")
    public ResponseEntity<?> getProcedureFields() {

        try {
            List<ProcedureField> list = service.getProcedureField();

            Map<String, Object> response = new HashMap<>();

            if (list.isEmpty()) {
                response.put("statusCode", 200);
                response.put("message", "No data found");
                response.put("data", list);
                return ResponseEntity.ok(response);
            }

            response.put("statusCode", 200);
            response.put("message", "Data fetched successfully");
            response.put("data", list);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            Map<String, Object> error = new HashMap<>();
            error.put("statusCode", 500);
            error.put("message", "Error: " + e.getMessage());
            error.put("data", null);

            return ResponseEntity.internalServerError().body(error);
        }
    }
}