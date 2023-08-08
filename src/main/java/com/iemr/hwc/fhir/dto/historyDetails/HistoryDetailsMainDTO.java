package com.iemr.hwc.fhir.dto.historyDetails;

import com.iemr.hwc.fhir.dto.historyDetails.childVaccineDetails.ChildVaccineDetailsDTO;
import com.iemr.hwc.fhir.dto.historyDetails.comorbidConditions.ComorbidConditionsDTO;
import com.iemr.hwc.fhir.dto.historyDetails.familyHistory.FamilyHistoryDTO;
import com.iemr.hwc.fhir.dto.historyDetails.feedingHistory.FeedingHistoryDTO;
import com.iemr.hwc.fhir.dto.historyDetails.femaleObstetricHistory.FemaleObstetricHistoryDTO;
import com.iemr.hwc.fhir.dto.historyDetails.immunizationHistory.ImmunizationDTO;
import com.iemr.hwc.fhir.dto.historyDetails.medicationHistory.MedicationHistoryDTO;
import com.iemr.hwc.fhir.dto.historyDetails.menstrualHistory.MenstrualHistoryDTO;
import com.iemr.hwc.fhir.dto.historyDetails.pastHistory.PastHistoryDTO;
import com.iemr.hwc.fhir.dto.historyDetails.perinatalHistroy.PerinatalHistoryDTO;
import com.iemr.hwc.fhir.dto.historyDetails.personalHistory.PersonalHistoryDTO;
import lombok.Data;

@Data
public class HistoryDetailsMainDTO {
    private PastHistoryDTO pastHistory;
    private ComorbidConditionsDTO comorbidConditions;
    private MedicationHistoryDTO medicationHistory;
    private FemaleObstetricHistoryDTO femaleObstetricHistory;
    private MenstrualHistoryDTO menstrualHistory;
    private FamilyHistoryDTO familyHistory;
    private PersonalHistoryDTO personalHistory;
    private ChildVaccineDetailsDTO childVaccineDetails;
    private ImmunizationDTO immunizationHistory;
    private FeedingHistoryDTO feedingHistory;
    private PerinatalHistoryDTO perinatalHistroy;
}
