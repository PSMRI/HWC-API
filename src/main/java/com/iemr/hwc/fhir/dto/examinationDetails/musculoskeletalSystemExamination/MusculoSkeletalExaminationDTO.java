package com.iemr.hwc.fhir.dto.examinationDetails.musculoskeletalSystemExamination;

import lombok.Data;

@Data
public class MusculoSkeletalExaminationDTO {
    private String joint_TypeOfJoint;
    private String joint_Laterality;
    private String joint_Abnormality;
    private String upperLimb_Laterality;
    private String upperLimb_Abnormality;
    private String lowerLimb_Laterality;
    private String lowerLimb_Abnormality;
    private String chestWall;
    private String spine;
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
}
