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
package com.iemr.hwc.controller.generalOPD;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.service.generalOPD.GeneralOPDServiceImpl;

@ExtendWith(MockitoExtension.class)
class GeneralOPDControllerTest {

    private static final String AUTH = "token";

    @Mock
    private GeneralOPDServiceImpl generalOPDServiceImpl;

    private GeneralOPDController controller;

    @BeforeEach
    void setUp() {
        controller = new GeneralOPDController();
        controller.setGeneralOPDServiceImpl(generalOPDServiceImpl);
    }

    private static int statusCodeIn(ResponseEntity<String> response) {
        JsonObject body = JsonParser.parseString(response.getBody()).getAsJsonObject();
        return body.get("statusCode").getAsInt();
    }

    @Test
    void saveNurseData_success_returns200() throws Exception {
        when(generalOPDServiceImpl.saveNurseData(any(JsonObject.class), eq(AUTH))).thenReturn("Data saved successfully");

        ResponseEntity<String> response = controller.saveBenGenOPDNurseData("{\"visitDetails\":{}}", AUTH);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, statusCodeIn(response));
        verify(generalOPDServiceImpl, never()).deleteVisitDetails(any());
    }

    @Test
    void saveNurseData_serviceFailure_returns500AndRollsBackVisit() throws Exception {
        when(generalOPDServiceImpl.saveNurseData(any(JsonObject.class), eq(AUTH)))
                .thenThrow(new RuntimeException("db down"));

        ResponseEntity<String> response = controller.saveBenGenOPDNurseData("{\"visitDetails\":{}}", AUTH);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(5000, statusCodeIn(response));
        verify(generalOPDServiceImpl).deleteVisitDetails(any(JsonObject.class));
    }

    @Test
    void saveNurseData_malformedJson_returns400WithStructuredError() throws Exception {
        ResponseEntity<String> response = controller.saveBenGenOPDNurseData("{\"visitDetails\":", AUTH);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, statusCodeIn(response));
        verifyNoInteractions(generalOPDServiceImpl);
    }

    @Test
    void saveNurseData_nonObjectJson_returns400() throws Exception {
        ResponseEntity<String> response = controller.saveBenGenOPDNurseData("[]", AUTH);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, statusCodeIn(response));
        verifyNoInteractions(generalOPDServiceImpl);
    }
}
