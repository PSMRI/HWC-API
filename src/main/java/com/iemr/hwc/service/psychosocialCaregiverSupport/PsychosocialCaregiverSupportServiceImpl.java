package com.iemr.hwc.service.psychosocialCaregiverSupport;

import com.iemr.hwc.data.psychosocialCaregiver.PsychosocialCaregiverSupportDTO;
import com.iemr.hwc.data.psychosocialCaregiver.PsychosocialCaregiverSupportData;
import com.iemr.hwc.repo.psychosocialCaregiverSupport.PsychosocialCaregiverSupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PsychosocialCaregiverSupportServiceImpl implements PsychosocialCaregiverSupportService {

    @Autowired
    private PsychosocialCaregiverSupportRepository repo;

    @Override
    public String saveAll(List<PsychosocialCaregiverSupportDTO> dtos, String user) {

        List<PsychosocialCaregiverSupportData> list = dtos.stream().map(dto -> {
            PsychosocialCaregiverSupportData e = new PsychosocialCaregiverSupportData();

            e.setId(dto.getId());
            e.setPatientId(dto.getPatientId());
            e.setBenVisitNo(dto.getBenVisitNo());
            e.setPsychosocialCounsellingProvided(dto.getPsychosocialCounsellingProvided());
            e.setCaregiverCounsellingProvided(dto.getCaregiverCounsellingProvided());
            e.setCaregiverDistressIdentified(dto.getCaregiverDistressIdentified());
            e.setCounsellingRemarks(dto.getCounsellingRemarks());
            e.setReferralRequired(dto.getReferralRequired());
            e.setReferralLevel(dto.getReferralLevel());
            e.setReasonForReferral(dto.getReasonForReferral());
            e.setFollowUpRequired(dto.getFollowUpRequired());
            e.setFollowUpDate(dto.getFollowUpDate());
            e.setCaseStatus(dto.getCaseStatus());
            e.setDateOfDeath(dto.getDateOfDeath());
            e.setRemarks(dto.getRemarks());

            e.setCreatedBy(user);
            e.setCreatedDate(System.currentTimeMillis());
            e.setUpdatedBy(user);
            e.setUpdatedDate(System.currentTimeMillis());

            return e;
        }).toList();

        repo.saveAll(list);

        return "Saved Successfully";
    }

    @Override
    public List<PsychosocialCaregiverSupportDTO> getAll(String user) {

        return repo.findAll().stream().map(e -> {
            PsychosocialCaregiverSupportDTO dto = new PsychosocialCaregiverSupportDTO();

            dto.setId(e.getId());
            dto.setPatientId(e.getPatientId());
            dto.setBenVisitNo(e.getBenVisitNo());
            dto.setPsychosocialCounsellingProvided(e.getPsychosocialCounsellingProvided());
            dto.setCaregiverCounsellingProvided(e.getCaregiverCounsellingProvided());
            dto.setCaregiverDistressIdentified(e.getCaregiverDistressIdentified());
            dto.setCounsellingRemarks(e.getCounsellingRemarks());
            dto.setReferralRequired(e.getReferralRequired());
            dto.setReferralLevel(e.getReferralLevel());
            dto.setReasonForReferral(e.getReasonForReferral());
            dto.setFollowUpRequired(e.getFollowUpRequired());
            dto.setFollowUpDate(e.getFollowUpDate());
            dto.setCaseStatus(e.getCaseStatus());
            dto.setDateOfDeath(e.getDateOfDeath());
            dto.setRemarks(e.getRemarks());

            dto.setCreatedBy(e.getCreatedBy());
            dto.setCreatedDate(e.getCreatedDate());
            dto.setUpdatedBy(e.getUpdatedBy());
            dto.setUpdatedDate(e.getUpdatedDate());

            return dto;
        }).toList();
    }
}