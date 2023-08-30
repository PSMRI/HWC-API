package com.iemr.hwc.fhir.dto.visitDetailsMain;

import com.iemr.hwc.fhir.dto.visitDetailsMain.adherence.AdherenceDTO;
import com.iemr.hwc.fhir.dto.visitDetailsMain.chiefComplaints.ChiefComplaintsDTO;
import com.iemr.hwc.fhir.dto.visitDetailsMain.visitDetails.VisitDetailsDTO;
import lombok.Data;

import java.util.List;

@Data
public class VisitDetailsMainDTO {
    private VisitDetailsDTO visitDetails;
    private List<ChiefComplaintsDTO> chiefComplaints;
    private AdherenceDTO adherence;
}
