package com.iemr.hwc.fhir.dto.examinationDetails;

import com.iemr.hwc.fhir.dto.examinationDetails.cardioVascularExamination.CardioVascularExaminationDTO;
import com.iemr.hwc.fhir.dto.examinationDetails.centralNervousSystemExamination.CentralNervousSystemExaminationDTO;
import com.iemr.hwc.fhir.dto.examinationDetails.gastroIntestinalExamination.GastroIntestinalExaminationDTO;
import com.iemr.hwc.fhir.dto.examinationDetails.generalExamination.GeneralExaminationDTO;
import com.iemr.hwc.fhir.dto.examinationDetails.genitoUrinarySystemExamination.GenitoUrinaryExaminationDTO;
import com.iemr.hwc.fhir.dto.examinationDetails.headToToeExamination.HeadToToeExaminationDTO;
import com.iemr.hwc.fhir.dto.examinationDetails.musculoskeletalSystemExamination.MusculoSkeletalExaminationDTO;
import com.iemr.hwc.fhir.dto.examinationDetails.respiratorySystemExamination.RespiratoryExaminationSystemDTO;
import lombok.Data;

@Data
public class ExaminationDetailsMainDTO {
    private GeneralExaminationDTO generalExamination;
    private HeadToToeExaminationDTO headToToeExamination;
    private GastroIntestinalExaminationDTO gastroIntestinalExamination;
    private CardioVascularExaminationDTO cardioVascularExamination;
    private RespiratoryExaminationSystemDTO respiratorySystemExamination;
    private CentralNervousSystemExaminationDTO centralNervousSystemExamination;
    private MusculoSkeletalExaminationDTO musculoskeletalSystemExamination;
    private GenitoUrinaryExaminationDTO genitoUrinarySystemExamination;
}
