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

import java.util.List;

@RestController
@RequestMapping("/procedureFields")
public class ProcedureController {

    @Autowired
    private ProcedureService service;

    @GetMapping("/fields")
    public ResponseEntity<?> getProcedureFields() {

        try {
            List<ProcedureField> response = service.getProcedureField();

            if (response.isEmpty()) {
                return ResponseEntity.ok("No data found");
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Error: " + e.getMessage());
        }
    }
}