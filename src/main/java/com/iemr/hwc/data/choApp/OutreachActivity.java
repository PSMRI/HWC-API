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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_outreach_activity")
public class OutreachActivity {

    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ActivityId")
    private int activityId;

    @Expose
    @Column(name = "UserId")
    private Integer userId;

    @Expose
    @Column(name = "UserName")
    private String userName;

    @Expose
    @Column(name = "ActivityName")
    private String activityName;

    @Expose
    @Column(name = "EventDesc")
    private String eventDescription;

    @Expose
    @Column(name = "NoOfParticipants")
    private Integer noOfParticipants;

    @Expose
    @Column(name = "ActivityDate")
    private Timestamp activityDate;

    @Expose
    @Column(name = "Img1TimeStamp")
    private Timestamp img1TimeStamp;

    @Expose
    @Column(name = "Img2TimeStamp")
    private Timestamp img2TimeStamp;

    @Lob
    @Column(name = "Img1", columnDefinition = "MEDIUMBLOB")
    private byte[] img1Data;

    @Lob
    @Column(name = "Img2", columnDefinition = "MEDIUMBLOB")
    private byte[] img2Data;

    @Expose
    @Transient
    private String img1;

    @Expose
    @Transient
    private String img2;

    @Expose
    @Column(name = "Img1Latitude")
    private Double img1latitude ;

    @Expose
    @Column(name = "Img1Longitude")
    private Double img1longitude;

    @Expose
    @Column(name = "Img1Address")
    private String img1Address;

    @Expose
    @Column(name = "Img2Address")
    private String img2Address;

    @Expose
    @Column(name = "Img2Latitude")
    private Double img2latitude ;

    @Expose
    @Column(name = "Img2Longitude")
    private Double img2longitude;

    @Column(name = "Deleted", insertable = false, updatable = true)
    private Boolean deleted;

    public OutreachActivity(Integer activityId, String activityName, String eventDescription, Integer noOfParticipants,
                            Timestamp activityDate, Integer userId, String userName) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.eventDescription = eventDescription;
        this.noOfParticipants = noOfParticipants;
        this.activityDate = activityDate;
        this.userId = userId;
        this.userName = userName;
    }

    public static ArrayList<OutreachActivity> getActivitiesForUser(ArrayList<Object[]> objList) {
        ArrayList<OutreachActivity> activityList = new ArrayList<>();
        OutreachActivity obj = null;
        if (objList != null && !objList.isEmpty()) {
            for (Object[] objArr : objList) {
                obj = new OutreachActivity((Integer) objArr[0], (String) objArr[1], (String) objArr[2],
                        (Integer) objArr[3], (Timestamp) objArr[4],(Integer) objArr[5], (String) objArr[6]);
                activityList.add(obj);
            }
        }
        return activityList;
    }
}
