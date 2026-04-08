package com.iemr.hwc.data.labModule;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "m_procedure_fields",schema = "db_iemr")
public class ProcedureField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ProcedureID")
    private Integer ProcedureID;

    @Column(name = "field_type")
    private String inputType;

    @Column(name = "min_value")
    private Float range_min;

    @Column(name = "max_value")
    private Float range_max;

    @Column(name = "unit")
    private String measurement_nit;

    @Column(name = "is_required")
    private Boolean isRequired;

    @Column(name = "display_name")
    private String test_component_name;

    @Column(name = "display_label")
    private String test_component_desc;
}