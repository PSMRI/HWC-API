package com.iemr.hwc.fhir.dto.visitDetailsMain.chiefComplaints;

import lombok.Data;

@Data
public class ChiefComplaintsDTO {
    private String beneficiaryRegID;
    private String benVisitID;
    private String visitCode;
    private Integer providerServiceMapID;
    private String conceptID;
    private String chiefComplaint;
    private Integer chiefComplaintID;
    private String duration;
    private String unitOfDuration;
    private String description;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
}
