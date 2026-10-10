package com.iemr.hwc.repo.psychosocialCaregiverSupport;

import com.iemr.hwc.data.psychosocialCaregiver.PsychosocialCaregiverSupportData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PsychosocialCaregiverSupportRepository 
        extends JpaRepository<PsychosocialCaregiverSupportData, Long> {

    List<PsychosocialCaregiverSupportData> findByUserId(Integer userId);
}
