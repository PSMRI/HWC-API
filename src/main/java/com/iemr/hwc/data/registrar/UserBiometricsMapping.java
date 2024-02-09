package com.iemr.hwc.data.registrar;

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

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "m_userbiometricmapping")
public class UserBiometricsMapping {

    @Id
    @GeneratedValue
    @Expose
    @Column(name = "UserBiometricMapID", insertable = false)
    private Long userBiometricMapID;

    @Expose
    @Column(name = "UserID")
    private Long userID;

    @Expose
    @Column(name = "FirstName")
    private String firstName;

    @Expose
    @Column(name = "LastName")
    private String lastName;

    @Expose
    @Column(name = "UserName")
    private String userName;

    @Expose
    @Column(name = "GUID_LeftFinger")
    private String gUIDLeftFinger;

    @Expose
    @Column(name = "GUID_RightFinger")
    private String gUIDRightFinger;

    @Expose
    @Column(name = "Deleted", insertable = false, updatable = true)
    private Boolean deleted;

    @Expose
    @Column(name = "Processed", insertable = false)
    private String processed;

    @Expose
    @Column(name = "CreatedBy", updatable = false)
    private String createdBy;

    @Expose
    @Column(name = "CreatedDate", insertable = false, updatable = false)
    private Timestamp createdDate;

    @Expose
    @Column(name = "ModifiedBy", insertable = false, updatable = true)
    private String modifiedBy;

    @Expose
    @Column(name = "LastModDate", insertable = false, updatable = false)
    private Timestamp lastModDate;

    @Expose
    @Lob
    @Column(name = "right_thumb")
    private String rightThumb;

    @Expose
    @Lob
    @Column(name = "right_index_finger")
    private String rightIndexFinger;

    @Expose
    @Lob
    @Column(name = "left_thumb")
    private String leftThumb;

    @Expose
    @Lob
    @Column(name = "left_index_finger")
    private String leftIndexFinger;

}