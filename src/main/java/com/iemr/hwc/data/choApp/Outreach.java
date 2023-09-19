package com.iemr.hwc.data.choApp;

import com.google.gson.annotations.Expose;
import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "m_outreach")
public class Outreach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    @Column(name = "OutreachID")
    private Integer id;

    @Expose
    @Column(name = "OutreachType")
    private String outreachType;

    @Expose
    @Column(name = "OutreachDesc")
    private String outreachDesc;

    @Column(name = "State_ID")
    private Integer stateID;

    @Column(name = "Deleted")
    private Boolean deleted;

    @Column(name = "CreatedBy")
    private String createdBy;

    @Column(name = "ModifiedBy")
    private String modifiedBy;

    @Column(name = "CreatedDate", insertable = false, updatable = false)
    private Timestamp createdDate;

    @Column(name = "ModifiedDate", insertable = false, updatable = false)
    private Timestamp modifiedDte;

}