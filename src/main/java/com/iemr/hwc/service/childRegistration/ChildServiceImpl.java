package com.iemr.hwc.service.childRegistration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.childRegistration.ChildRegister;
import com.iemr.hwc.data.childRegistration.ChildRegisterDTO;
import com.iemr.hwc.data.requestDTO.GetBenRequestHandler;
import com.iemr.hwc.repo.childRegsitration.ChildRegisterRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChildServiceImpl implements ChildService {

    private final Logger logger = LoggerFactory.getLogger(ChildServiceImpl.class);
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private ChildRegisterRepo childRepo;

    public String getChildDataByUserName(String  userName) {
        try {
            List<ChildRegister> childRegisterList = childRepo.getChildDetailsForUser(userName);

//            List<ChildRegisterDTO> result = new ArrayList<>();
//            childRegisterList.forEach(childRegister -> {
//                ChildRegisterDTO childDTO = modelMapper.map(childRegister, ChildRegisterDTO.class);
//                result.add(childDTO);
//            });
            List<ChildRegisterDTO> result = childRegisterList.stream()
                    .map(childRegister -> modelMapper.map(childRegister, ChildRegisterDTO.class))
                    .collect(Collectors.toList());
            Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy h:mm:ss a").create();
            return gson.toJson(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public String save(List<ChildRegisterDTO> childRegisterDTOs) throws Exception {
        List<ChildRegister> listToBeSaved = new ArrayList<>();
        childRegisterDTOs.forEach(childRegisterDTO ->
        {
            ChildRegister childRegister =
                    childRepo.findChildRegisterByBenIdAndCreatedDate(childRegisterDTO.getBenId(), childRegisterDTO.getCreatedDate());

            if (childRegister == null) {
                childRegister = new ChildRegister();
                modelMapper.map(childRegisterDTO, childRegister);
                childRegister.setId(null);
            } else {
                Long id = childRegister.getId();
                modelMapper.map(childRegisterDTO, childRegister);
                childRegister.setId(id);
            }
            listToBeSaved.add(childRegister);
        });
        childRepo.saveAll(listToBeSaved);
        return "no of child details saved: " + childRegisterDTOs.size();
    }
}
