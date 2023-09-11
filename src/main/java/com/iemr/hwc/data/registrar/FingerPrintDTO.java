package com.iemr.hwc.data.registrar;

import lombok.Data;

import java.util.List;

@Data
public class FingerPrintDTO {
    private Integer id;
    private String userName;
    private String rightThumb;
    private String rightIndexFinger;
    private String leftThumb;
    private String leftIndexFinger;
}