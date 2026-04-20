package com.iemr.hwc.service.oralHealth;


import com.iemr.hwc.data.oralHealth.OralHealthDTO;
import com.iemr.hwc.data.oralHealth.OralHealthData;
import com.iemr.hwc.repo.oralHealth.OralHealthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OralHealthServiceImpl implements OralHealthService {

    @Autowired
    private OralHealthRepository repo;

    @Override
    public List<OralHealthData> saveAll(List<OralHealthDTO> dtos, Integer userId,String userName) {

        List<OralHealthData> list = dtos.stream().map(dto -> {
            OralHealthData e = new OralHealthData();

            e.setId(dto.getId());
            e.setPatientId(dto.getPatientId());
            e.setBenVisitNo(dto.getBenVisitNo());
            e.setToothDecayPresent(dto.getToothDecayPresent());
            e.setToothDecaySymptoms(dto.getToothDecaySymptoms());
            e.setGumDiseasePresent(dto.getGumDiseasePresent());
            e.setGumDiseaseSymptoms(dto.getGumDiseaseSymptoms());
            e.setIrregularTeethJaws(dto.getIrregularTeethJaws());
            e.setAbnormalGrowthUlcer(dto.getAbnormalGrowthUlcer());
            e.setCleftLipPalate(dto.getCleftLipPalate());
            e.setDentalFluorosis(dto.getDentalFluorosis());
            e.setDentalEmergency(dto.getDentalEmergency());

            e.setCreatedBy(userName);
            e.setCreatedDate(System.currentTimeMillis());
            e.setUpdatedBy(userName);
            e.setUserId(userId);
            e.setBeneficiaryID(dto.getBeneficiaryID());
            e.setBeneficiaryRegID(dto.getBeneficiaryRegID());
            e.setUpdatedDate(System.currentTimeMillis());

            return e;
        }).toList();

        repo.saveAll(list);

        return list;
    }

    @Override
    public List<OralHealthDTO> getAll(Integer userId) {

        return repo.findByUserId(userId).stream().map(e -> {
            OralHealthDTO dto = new OralHealthDTO();

            dto.setId(e.getId());
            dto.setPatientId(e.getPatientId());
            dto.setBenVisitNo(e.getBenVisitNo());
            dto.setToothDecayPresent(e.getToothDecayPresent());
            dto.setToothDecaySymptoms(e.getToothDecaySymptoms());
            dto.setGumDiseasePresent(e.getGumDiseasePresent());
            dto.setGumDiseaseSymptoms(e.getGumDiseaseSymptoms());
            dto.setIrregularTeethJaws(e.getIrregularTeethJaws());
            dto.setAbnormalGrowthUlcer(e.getAbnormalGrowthUlcer());
            dto.setCleftLipPalate(e.getCleftLipPalate());
            dto.setDentalFluorosis(e.getDentalFluorosis());
            dto.setDentalEmergency(e.getDentalEmergency());

            dto.setCreatedBy(e.getCreatedBy());
            dto.setCreatedDate(e.getCreatedDate());
            dto.setUpdatedBy(e.getUpdatedBy());
            dto.setUpdatedDate(e.getUpdatedDate());

            return dto;
        }).toList();
    }
}