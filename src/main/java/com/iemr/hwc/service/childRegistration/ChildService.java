package com.iemr.hwc.service.childRegistration;

import com.iemr.hwc.data.childRegistration.ChildRegisterDTO;
import com.iemr.hwc.data.requestDTO.GetBenRequestHandler;

import java.util.List;

public interface ChildService {

    String save(List<ChildRegisterDTO> childRegisterDTOList) throws Exception;

    String getChildDataByUserName( String  userName);
}
