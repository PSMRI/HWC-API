package com.iemr.hwc.service.infantRegistraion;

import com.iemr.hwc.data.infantRegistration.InfantRegisterDTO;
import com.iemr.hwc.data.requestDTO.GetBenRequestHandler;

import java.util.List;

public interface InfantService {

    String registerInfant(List<InfantRegisterDTO> infantRegisterDTOs);

    List<InfantRegisterDTO> getInfantDetails(GetBenRequestHandler dto,String userName);
}
