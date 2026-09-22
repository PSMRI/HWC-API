package com.iemr.hwc.service.psychosocialCaregiverSupport;

import com.iemr.hwc.data.psychosocialCaregiver.PsychosocialCaregiverSupportDTO;
import com.iemr.hwc.data.psychosocialCaregiver.PsychosocialCaregiverSupportData;
import com.iemr.hwc.repo.psychosocialCaregiverSupport.PsychosocialCaregiverSupportRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PsychosocialCaregiverSupportServiceImpl implements PsychosocialCaregiverSupportService {

    @Autowired
    private PsychosocialCaregiverSupportRepository repo;

    @Override
    public List<PsychosocialCaregiverSupportDTO> saveAll(List<PsychosocialCaregiverSupportDTO> dtos, Integer userId) {
        List<PsychosocialCaregiverSupportData> entities = new ArrayList<>();

        for (PsychosocialCaregiverSupportDTO dto : dtos) {
            dto.setUserId(userId);
            PsychosocialCaregiverSupportData entity = new PsychosocialCaregiverSupportData();
            BeanUtils.copyProperties(dto, entity);
            entities.add(entity);
        }

        List<PsychosocialCaregiverSupportData> savedEntities = repo.saveAll(entities);
        List<PsychosocialCaregiverSupportDTO> result = new ArrayList<>();

        for (PsychosocialCaregiverSupportData entity : savedEntities) {
            PsychosocialCaregiverSupportDTO dto = new PsychosocialCaregiverSupportDTO();
            BeanUtils.copyProperties(entity, dto);
            result.add(dto);
        }

        return result;
    }

    @Override
    public List<PsychosocialCaregiverSupportDTO> getAll(Integer userId) {
        List<PsychosocialCaregiverSupportDTO> result = new ArrayList<>();

        for (PsychosocialCaregiverSupportData entity : repo.findByUserId(userId)) {
            PsychosocialCaregiverSupportDTO dto = new PsychosocialCaregiverSupportDTO();
            BeanUtils.copyProperties(entity, dto);
            result.add(dto);
        }

        return result;
    }
}
