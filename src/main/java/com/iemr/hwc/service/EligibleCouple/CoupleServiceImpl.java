package com.iemr.hwc.service.EligibleCouple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.hwc.data.eligibleCouple.EligibleCoupleDTO;
import com.iemr.hwc.data.eligibleCouple.EligibleCoupleRegister;
import com.iemr.hwc.data.requestDTO.GetBenRequestHandler;
import com.iemr.hwc.repo.couple.EligibleCoupleRegisterRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoupleServiceImpl implements CoupleService {

    ObjectMapper mapper = new ObjectMapper();
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private EligibleCoupleRegisterRepo eligibleCoupleRegisterRepo;



    private final Logger logger = LoggerFactory.getLogger(CoupleServiceImpl.class);


    @Override
    public String registerEligibleCouple(List<EligibleCoupleDTO> eligibleCoupleDTOs, MultipartFile kitPhoto1, MultipartFile kitPhoto2) {
        try {
            List<EligibleCoupleRegister> ecrList = new ArrayList<>();
            eligibleCoupleDTOs.forEach(it -> {
                EligibleCoupleRegister existingECR =
                        eligibleCoupleRegisterRepo.findEligibleCoupleRegisterByBenId(it.getBenId());
                if (kitPhoto1 != null) {
                    String kitPhoto1base64Image = null;
                    try {
                        kitPhoto1base64Image = Base64.getEncoder().encodeToString(kitPhoto1.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    existingECR.setKitPhoto1(String.valueOf(kitPhoto1base64Image));

                }


                if (kitPhoto2 != null) {
                    String kitPhoto2base64Image = null;
                    try {
                        kitPhoto2base64Image = Base64.getEncoder().encodeToString(kitPhoto2.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    existingECR.setKitPhoto2(String.valueOf(kitPhoto2base64Image));

                }

                if (existingECR != null && null != existingECR.getNumLiveChildren()) {

                    Long id = existingECR.getId();

                    modelMapper.map(it, existingECR);
                    existingECR.setId(id);
                } else {
                    existingECR = new EligibleCoupleRegister();
                    modelMapper.map(it, existingECR);
                    existingECR.setId(null);
                }

                ecrList.add(existingECR);
            });
            eligibleCoupleRegisterRepo.saveAll(ecrList);
            return "no of ecr details saved: " + ecrList.size();
        } catch (Exception e) {
            return "error while saving ecr details: " + e.getMessage();
        }
    }
    @Override
    public String getEligibleCoupleRegRecords(GetBenRequestHandler dto) {
        try {
            List<EligibleCoupleRegister> eligibleCoupleRegisterList =
                    eligibleCoupleRegisterRepo.getECRegRecords(dto.getUserName(), dto.getFromDate(), dto.getToDate());
            List<EligibleCoupleDTO> list = eligibleCoupleRegisterList.stream()
                    .map(eligibleCoupleRegister -> mapper.convertValue(eligibleCoupleRegister, EligibleCoupleDTO.class))
                    .collect(Collectors.toList());
            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .setDateFormat("MMM dd, yyyy h:mm:ss a").create();
            return gson.toJson(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }


}
