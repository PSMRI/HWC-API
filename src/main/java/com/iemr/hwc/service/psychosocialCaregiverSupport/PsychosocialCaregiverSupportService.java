package com.iemr.hwc.service.psychosocialCaregiverSupport;

import com.iemr.hwc.data.psychosocialCaregiver.PsychosocialCaregiverSupportDTO;

import java.util.List;


public  interface  PsychosocialCaregiverSupportService {
    List<PsychosocialCaregiverSupportDTO> saveAll(List<PsychosocialCaregiverSupportDTO> dtos, Integer userId);

    List<PsychosocialCaregiverSupportDTO> getAll(Integer userId);
}
