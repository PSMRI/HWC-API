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
package com.iemr.hwc.fhir.dto.beneficiary;

import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Data
public class BeneficiariesDTOSearch {
    private BigInteger benMapId; //
    private BigInteger benId;
    private BigInteger benRegId;
    private String createdBy; //
    private Timestamp createdDate; //
    private Boolean deleted; //
    private Timestamp lastModDate; //
    private String modifiedBy; //
    private Address currentAddress; //
    private Address permanentAddress; //
    private Address emergencyAddress; //
    private String preferredPhoneNum; //
    private String preferredPhoneTyp; //
    private String preferredSMSPhoneNum; //
    private String preferredSMSPhoneTyp; //
    private String emergencyContactNum; //
    private String emergencyContactTyp; //
    private String preferredEmailId; //
    private BenDetailDTO beneficiaryDetails;
    private List<BenFamilyDTO> beneficiaryFamilyTags;
    private List<BenIdentityDTO> beneficiaryIdentites;
    private List<BenServiceDTO> beneficiaryServiceMap;
    private List<Phone> contacts;

    // ABHA address
    List<AbhaAddressDTO> abhaDetails;
}