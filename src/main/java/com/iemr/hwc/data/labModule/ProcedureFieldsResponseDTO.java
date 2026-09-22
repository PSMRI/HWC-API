package com.iemr.hwc.data.labModule;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcedureFieldsResponseDTO {

    private Integer procedureId;
    private String procedureName;

    private String fieldType;
    private Float minValue;
    private Float maxValue;
    private String unit;
    private Boolean isRequired;

    private String displayName;
    private String displayLabel;
}