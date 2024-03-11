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
package com.iemr.hwc.data.choApp;

import com.google.gson.annotations.Expose;
import lombok.Data;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "t_user_activity_logs")
public class UserActivityLogs {

    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int logId;

    @Expose
    @Column(name = "UserId")
    private String userId;

    @Expose
    @Column(name = "UserName")
    private String userName;

    @Expose
    @Column(name = "LoginType")
    private String loginType;

    @Expose
    @Column(name = "LoginOption")
    private String option;

    @Expose
    @Column(name = "LogoutType")
    private String logoutType;

    @Expose
    @Column(name = "LoginTime")
    private Timestamp loginTimeStamp;

    @Expose
    @Column(name = "LogoutTime")
    private Timestamp logoutTimeStamp;

    @Expose
    @Column(name = "IsOutOfReach")
    private Boolean isOutOfReach;

    @Lob
    @Column(name = "UserImage", columnDefinition = "MEDIUMBLOB")
    private byte[] imageData;

    @Transient
    private String userImage;

    @Expose
    @Column(name = "Latitude")
    private Double latitude ;

    @Expose
    @Column(name = "Longitude")
    private Double longitude;

}

