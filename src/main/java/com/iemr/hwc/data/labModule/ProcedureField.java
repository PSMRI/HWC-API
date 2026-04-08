package com.iemr.hwc.data.labModule;

import jakarta.persistence.*;

@Entity
@Table(name = "m_procedure_fields")
public class ProcedureField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ProcedureID")
    private Integer ProcedureID;
    @Column(name = "field_type")
    private String fieldType;
    @Column(name = "min_value")
    private Float minValue;
    @Column(name = "max_value")
    private Float maxValue;
    @Column(name = "unit")
    private String unit;
    @Column(name = "is_required")
    private Boolean isRequired;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "display_label")
    private String displayLabel;
}