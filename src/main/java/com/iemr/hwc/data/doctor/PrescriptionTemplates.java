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
package com.iemr.hwc.data.doctor;

import com.google.gson.annotations.Expose;
import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "t_prescription_templates")
public class PrescriptionTemplates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Expose
    @Column(name = "UserID")
    private Integer userID;

    @Expose
    @Column(name = "TemplateID")
    private Integer tempID;

    @Expose
    @Column(name = "TemplateName")
    private String templateName;

    @Expose
    @Column(name = "DrugID")
    private Integer drugId;

    @Expose
    @Column(name = "DrugName")
    private String drugName;

    @Expose
    @Column(name = "Frequency")
    private String frequency;

    @Expose
    @Column(name = "Unit")
    private String unit;

    @Expose
    @Column(name = "Duration")
    private String duration;

    @Expose
    @Column(name = "Instructions")
    private String instruction;

    @Column(name = "CreatedDate", insertable = false, updatable = false)
    private Timestamp createdDate;

    @Column(name = "ModifiedDate", insertable = false, updatable = false)
    private Timestamp modifiedDte;
}
