package com.iemr.mmu.service.snomedct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.iemr.mmu.data.snomedct.SCTDescription;
import com.iemr.mmu.repo.snomedct.SnomedRepository;
import com.iemr.mmu.utils.mapper.OutputMapper;

@Service
@PropertySource("classpath:application.properties")
public class SnomedServiceImpl implements SnomedService {

	@Value("${snomedCTPageSize}")
	private Integer snomedCTPageSize;

	private SnomedRepository snomedRepository;

	@Autowired
	public void setSnomedRepository(SnomedRepository snomedRepository) {
		this.snomedRepository = snomedRepository;
	}

	@Override
	public SCTDescription findSnomedCTRecordFromTerm(String term) {

		List<Object[]> records = snomedRepository.findSnomedCTRecordFromTerm(term);
		SCTDescription obj = SCTDescription.getSnomedCTOBJ(records);
		return obj;

	}

	@Override
	public String findSnomedCTRecordList(SCTDescription sctdescription) throws Exception {
		Page<SCTDescription> sctList;
		Map<String, Object> dataMap = new HashMap<>();
		if (sctdescription != null && sctdescription.getTerm() != null && sctdescription.getPageNo() != null) {
			PageRequest pr = new PageRequest(sctdescription.getPageNo(), snomedCTPageSize);
			sctList = snomedRepository.findSnomedCTRecordList(sctdescription.getTerm(), pr);

			// System.out.println(sctList.getTotalPages());
			// System.out.println(sctList.getNumberOfElements());
			dataMap.put("sctMaster", sctList.getContent());
			dataMap.put("pageCount", sctList.getTotalPages());
			return OutputMapper.gson().toJson(dataMap);
		} else
			throw new Exception("invalid request");

	}

}
