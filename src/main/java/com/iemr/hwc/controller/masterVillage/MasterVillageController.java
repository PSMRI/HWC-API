/*
 * AMRIT – Accessible Medical Records via Integrated Technology
 * Integrated EHR (Electronic Health Records) Solution
 *
 * Copyright (C) "Piramal Swasthya Management and Research Institute"
 *
 * This file is part of AMRIT.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.iemr.hwc.controller.masterVillage;

import com.google.gson.Gson;
import com.iemr.hwc.data.login.UserMasterVillageDTO;
import com.iemr.hwc.data.login.UsersMasterVillage;
import com.iemr.hwc.service.masterVillage.MasterVillageService;
import com.iemr.hwc.utils.response.OutputResponse;

import io.swagger.v3.oas.annotations.Operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/masterVillage", headers = "Authorization", consumes = "application/json", produces = "application/json")
public class MasterVillageController {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private MasterVillageService masterVillageService;

    @Autowired
    public void setMasterVillageService(MasterVillageService masterVillageService){
        this.masterVillageService = masterVillageService;
    }

    @Operation(summary = "set master village to a user")
    @PostMapping(value = "/set", produces = {
            "application/json" })
    public String setMasterVillage(@RequestBody UserMasterVillageDTO userMasterVillageDTO) {
        OutputResponse response = new OutputResponse();
        try {
            if (userMasterVillageDTO!=null && userMasterVillageDTO.getUserID() != null && userMasterVillageDTO.getMasterVillageID() != null) {
                String resp = masterVillageService.setMasterVillage(userMasterVillageDTO.getUserID(), userMasterVillageDTO.getMasterVillageID());

                if(resp !=null && resp.equals("not_ok")){
                    response.setError(500, "Error setting master village");
                }
                else if(resp !=null && resp.equals("villageID_not_exist")){
                    response.setError(404, "Village ID does not exist");
                }
                else if(resp !=null && resp.equals("userID_not_exist")){
                    response.setError(404, "User ID does not exist");
                }
                else if(resp!=null){
                    response.setResponse(resp);
                }
            } else {
                response.setError(400, "Invalid request");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setError(500, "Error while setting master village");
        }
        return response.toString();
    }

    @Operation(summary = "Get master village for a user")
    @GetMapping(value = "/{userID}/get")
    public String getMasterVillage(@PathVariable("userID") Long userID) {
        logger.info("Get master village by userID ..." + userID);
        OutputResponse response = new OutputResponse();
        UsersMasterVillage user = masterVillageService.getMasterVillage(userID);
        if (user != null){
            if(user.getMasterVillage()!=null){
                Gson gson = new Gson();
                response.setResponse(gson.toJson(user.getMasterVillage()));
            }
            else{
                logger.error("No master village associated with user " + userID);
                response.setError(404, "User with userID: "+userID+" do not have master village");
            }
        }
        else{
            logger.error("No active master village record for user " + userID + " found");
            response.setError(404, "No master village record found with userID: "+userID);
        }
        return response.toString();
    }
}
