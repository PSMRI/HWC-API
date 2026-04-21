package com.iemr.hwc.service.mentalHealth;

import com.iemr.hwc.data.mentalHealth.MentalHealthScreening;
import com.iemr.hwc.data.mentalHealth.MentalHealthScreeningDTO;
import com.iemr.hwc.repo.mentalHealth.MentalHealthScreeningRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MentalHealthScreeningServiceImpl implements MentalHealthScreeningService {

    @Autowired
    private MentalHealthScreeningRepository repository;

    @Override
    public List<MentalHealthScreeningDTO> saveAll(List<MentalHealthScreeningDTO> dtos, Integer userId) {
        List<MentalHealthScreening> entities = new ArrayList<>();

        for (MentalHealthScreeningDTO dto : dtos) {
            dto.setUserId(userId);
            MentalHealthScreening entity = new MentalHealthScreening();
            BeanUtils.copyProperties(dto, entity);
            entities.add(entity);
        }

        List<MentalHealthScreening> savedEntities = repository.saveAll(entities);
        List<MentalHealthScreeningDTO> result = new ArrayList<>();

        for (MentalHealthScreening entity : savedEntities) {
            MentalHealthScreeningDTO dto = new MentalHealthScreeningDTO();
            BeanUtils.copyProperties(entity, dto);
            result.add(dto);
        }

        return result;
    }

    @Override
    public List<MentalHealthScreeningDTO> getAll(Integer userId) {
        List<MentalHealthScreeningDTO> result = new ArrayList<>();

        for (MentalHealthScreening entity : repository.findByUserId(userId)) {
            MentalHealthScreeningDTO dto = new MentalHealthScreeningDTO();
            BeanUtils.copyProperties(entity, dto);
            result.add(dto);
        }

        return result;
    }
}
