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
package com.iemr.hwc.data.login;

import com.google.gson.annotations.Expose;
import com.iemr.hwc.data.location.DistrictBranchMapping;
import lombok.Data;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "t_usermastervillagemapping")
public class UsersMasterVillage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Expose
    @Column(name = "id")
    private Long id;
    @Expose
    @ManyToOne
    @JoinColumn(name="user_id")
    private Users user;
    @Expose
    @ManyToOne
    @JoinColumn(name="masterVillage_id")
    private DistrictBranchMapping masterVillage;
    @Expose
    @Column(name = "CreatedDate")
    private Timestamp createdDate;
    @Expose
    @Column(name = "LastModDate")
    private Timestamp lastModDate;
    @Expose
    @Column(name = "active")
    private Boolean active;

}