package com.iemr.hwc.fhir.dto.historyDetails.perinatalHistroy;

import lombok.Data;

@Data
public class PerinatalHistoryDTO {
    private String beneficiaryRegID;
    private Integer providerServiceMapID;
    private String benVisitID;
    private String createdBy;
    private Integer vanID;
    private Integer parkingPlaceID;
    private String deliveryPlaceID;
    private String placeOfDelivery;
    private String otherPlaceOfDelivery;
    private String deliveryTypeID;
    private String typeOfDelivery;
    private String complicationAtBirthID;
    private String complicationAtBirth;
    private String otherComplicationAtBirth;
    private String gestation;
    private String birthWeightG;

}
