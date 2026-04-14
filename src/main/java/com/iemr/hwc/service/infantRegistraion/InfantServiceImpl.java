package com.iemr.hwc.service.infantRegistraion;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.iemr.hwc.data.infantRegistration.InfantRegister;
import com.iemr.hwc.data.infantRegistration.InfantRegisterDTO;
import com.iemr.hwc.data.requestDTO.GetBenRequestHandler;
import com.iemr.hwc.repo.infantRegistraion.InfantRegisterRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InfantServiceImpl implements InfantService {

    private final Logger logger = LoggerFactory.getLogger(InfantServiceImpl.class);

    @Autowired
    private InfantRegisterRepo infantRegisterRepo;



    ObjectMapper mapper = new ObjectMapper();

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public String registerInfant(List<InfantRegisterDTO> infantRegisterDTOs) {

        try {
            List<InfantRegister> infantList = new ArrayList<>();
            infantRegisterDTOs.forEach(it -> {
                InfantRegister infantRegister = infantRegisterRepo.findInfantRegisterByBenIdAndBabyIndexAndIsActive(it.getBenId(), it.getBabyIndex(), true);

                if (infantRegister != null) {
                    Long id = infantRegister.getId();
                    modelMapper.map(it, infantRegister);
                    infantRegister.setId(id);
                } else {
                    infantRegister = new InfantRegister();
                    modelMapper.map(it, infantRegister);
                    infantRegister.setId(null);
                }
                infantList.add(infantRegister);
            });
            infantRegisterRepo.saveAll(infantList);
            return "no of infant register details saved: " + infantList.size();
        } catch (Exception e) {
            logger.info("error while saving infant register details: " + e.getMessage());
        }
        return null;
    }



    @Override
    public List<InfantRegisterDTO> getInfantDetails(GetBenRequestHandler dto,String userName) {
        try{
            List<InfantRegister> infantRegisterList =
                    infantRegisterRepo.getInfantDetailsForUser(userName);

            return infantRegisterList.stream()
                    .map(infantRegister -> mapper.convertValue(infantRegister, InfantRegisterDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.info("error while fetching infant register details: " + e.getMessage());
        }
        return null;
    }
}
