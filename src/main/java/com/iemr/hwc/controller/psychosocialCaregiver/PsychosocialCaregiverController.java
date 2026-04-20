package com.iemr.hwc.controller.psychosocialCaregiver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.psychosocialCaregiver.PsychosocialCaregiverSupportDTO;
import com.iemr.hwc.service.psychosocialCaregiverSupport.PsychosocialCaregiverSupportService;
import com.iemr.hwc.utils.JwtUtil;
import com.iemr.hwc.utils.response.OutputResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/psychosocialCaregiver")
public class PsychosocialCaregiverController {

    private PsychosocialCaregiverSupportService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/saveAll")
    public String saveAll(@RequestBody List<PsychosocialCaregiverSupportDTO> dtos,
                          @RequestHeader("jwttoken") String token) {

        OutputResponse response = new OutputResponse();

        try {
            String user = jwtUtil.extractUsername(token);

            if (dtos != null && !dtos.isEmpty()) {
                String res = service.saveAll(dtos, user);
                response.setResponse(res);
            } else {
                response.setError(5000, "Invalid request");
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

            List<PsychosocialCaregiverSupportDTO> list = service.getAll(user);

            Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy h:mm:ss a").create();
            response.setResponse(gson.toJson(list));

        } catch (Exception e) {
            response.setError(5000, e.getMessage());
        }

        return response.toString();
    }
}
