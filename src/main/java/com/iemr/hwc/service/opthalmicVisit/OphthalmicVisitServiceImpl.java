package com.iemr.hwc.service.opthalmicVisit;

import com.iemr.hwc.data.opthalmic.OphthalmicVisit;
import com.iemr.hwc.data.opthalmic.OphthalmicVisitDTO;
import com.iemr.hwc.data.opthalmic.OphthalmicVisitMapper;
import com.iemr.hwc.repo.opthalmicVisit.OphthalmicVisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OphthalmicVisitServiceImpl implements OphthalmicVisitService {

    @Autowired
    private  OphthalmicVisitRepository repository;

    @Override
    public List<OphthalmicVisitDTO> saveAll(List<OphthalmicVisitDTO> dtos, Integer userId) {

        List<OphthalmicVisit> entities = dtos.stream()
                .map(dto -> {
                    OphthalmicVisit entity = OphthalmicVisitMapper.toEntity(dto);
                    entity.setUserId(userId);
                    return entity;
                })
                .collect(Collectors.toList());

        List<OphthalmicVisit> saved = repository.saveAll(entities);

        return saved.stream()
                .map(OphthalmicVisitMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OphthalmicVisitDTO> getAll(Integer userId) {

        return repository.findByUserId(userId).stream()
                .map(OphthalmicVisitMapper::toDTO)
                .collect(Collectors.toList());
    }
}