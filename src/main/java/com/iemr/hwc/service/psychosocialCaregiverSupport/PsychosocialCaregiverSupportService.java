package com.iemr.hwc.service.psychosocialCaregiverSupport;

import com.iemr.hwc.data.psychosocialCaregiver.PsychosocialCaregiverSupportDTO;

import java.util.List;


public  interface  PsychosocialCaregiverSupportService {
    String saveAll(List<PsychosocialCaregiverSupportDTO> dtos, String user);

    List<PsychosocialCaregiverSupportDTO> getAll(String user);
}
