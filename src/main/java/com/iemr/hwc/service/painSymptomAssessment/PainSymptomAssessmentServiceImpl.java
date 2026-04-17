package com.iemr.hwc.service.painSymptomAssessment;

import com.iemr.hwc.data.painSymptom.PainSymptomAssessmentDTO;
import com.iemr.hwc.data.painSymptom.PainSymptomData;
import com.iemr.hwc.repo.painSymptomAssessment.PainSymptomAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PainSymptomAssessmentServiceImpl implements PainSymptomAssessmentService {

    @Autowired
    private PainSymptomAssessmentRepository painRepo;




    @Override
    public String saveAll(List<PainSymptomAssessmentDTO> painList) {
       try {
           if (painList != null) {
               List<PainSymptomData> list = painList.stream().map(this::mapPain).toList();
               painRepo.saveAll(list);
               return "Data save successfully";
           }
       }catch (Exception e){
           return e.getMessage();
       }


      return null;

    }

    @Override
    public List<PainSymptomAssessmentDTO> getPain (String user){
        return painRepo.findAll()
                .stream()
                .map(this::mapPainToDTO)
                .toList();
    }
    private PainSymptomAssessmentDTO mapPainToDTO(PainSymptomData e) {
        PainSymptomAssessmentDTO dto = new PainSymptomAssessmentDTO();

        dto.setId(e.getId());
        dto.setPatientId(e.getPatientId());
        dto.setBenVisitNo(e.getBenVisitNo());
        dto.setPainSeverity(e.getPainSeverity());
        dto.setPainDuration(e.getPainDuration());
        dto.setSymptomsPresent(e.getSymptomsPresent());
        dto.setOtherSymptomsSeverity(e.getOtherSymptomsSeverity());
        dto.setImmediateReliefProvided(e.getImmediateReliefProvided());
        dto.setPersistentPainPresent(e.getPersistentPainPresent());
        dto.setPainAssessmentEnabled(e.getPainAssessmentEnabled());
        dto.setDistressingSymptomsPresent(e.getDistressingSymptomsPresent());
        dto.setBedriddenOrSeverelyDependent(e.getBedriddenOrSeverelyDependent());
        dto.setLifeLimitingIllnessKnown(e.getLifeLimitingIllnessKnown());
        dto.setCaregiverSupportRequired(e.getCaregiverSupportRequired());
        dto.setPalliativeCareEligible(e.getPalliativeCareEligible());
        dto.setReferralRequired(e.getReferralRequired());
        dto.setReferralLevel(e.getReferralLevel());
        dto.setReasonForReferral(e.getReasonForReferral());
        dto.setFollowUpRequired(e.getFollowUpRequired());
        dto.setFollowUpDate(e.getFollowUpDate());
        dto.setCaseStatus(e.getCaseStatus());
        dto.setDateOfDeath(e.getDateOfDeath());
        dto.setRemarks(e.getRemarks());

        dto.setCreatedDate(e.getCreatedDate());
        dto.setCreatedBy(e.getCreatedBy());
        dto.setUpdatedDate(e.getUpdatedDate());
        dto.setUpdatedBy(e.getUpdatedBy());

        return dto;
    }
    private PainSymptomData mapPain(PainSymptomAssessmentDTO dto) {
        PainSymptomData e = new PainSymptomData();

        e.setId(dto.getId());
        e.setPatientId(dto.getPatientId());
        e.setBenVisitNo(dto.getBenVisitNo());
        e.setPainSeverity(dto.getPainSeverity());
        e.setPainDuration(dto.getPainDuration());
        e.setSymptomsPresent(dto.getSymptomsPresent());
        e.setOtherSymptomsSeverity(dto.getOtherSymptomsSeverity());
        e.setImmediateReliefProvided(dto.getImmediateReliefProvided());
        e.setPersistentPainPresent(dto.getPersistentPainPresent());
        e.setPainAssessmentEnabled(dto.getPainAssessmentEnabled());
        e.setDistressingSymptomsPresent(dto.getDistressingSymptomsPresent());
        e.setBedriddenOrSeverelyDependent(dto.getBedriddenOrSeverelyDependent());
        e.setLifeLimitingIllnessKnown(dto.getLifeLimitingIllnessKnown());
        e.setCaregiverSupportRequired(dto.getCaregiverSupportRequired());
        e.setPalliativeCareEligible(dto.getPalliativeCareEligible());
        e.setReferralRequired(dto.getReferralRequired());
        e.setReferralLevel(dto.getReferralLevel());
        e.setReasonForReferral(dto.getReasonForReferral());
        e.setFollowUpRequired(dto.getFollowUpRequired());
        e.setFollowUpDate(dto.getFollowUpDate());
        e.setCaseStatus(dto.getCaseStatus());
        e.setDateOfDeath(dto.getDateOfDeath());
        e.setRemarks(dto.getRemarks());

        e.setCreatedDate(dto.getCreatedDate());
        e.setCreatedBy(dto.getCreatedBy());
        e.setUpdatedDate(dto.getUpdatedDate());
        e.setUpdatedBy(dto.getUpdatedBy());

        return e;
    }

}