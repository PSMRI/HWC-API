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
