package com.iemr.hwc.repo.psychosocialCaregiverSupport;

import com.iemr.hwc.data.psychosocialCaregiver.PsychosocialCaregiverSupportData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsychosocialCaregiverSupportRepository 
        extends JpaRepository<PsychosocialCaregiverSupportData, Long> {
}