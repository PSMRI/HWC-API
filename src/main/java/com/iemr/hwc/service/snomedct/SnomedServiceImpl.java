/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.hwc.service.snomedct;

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

import com.iemr.hwc.data.snomedct.SCTDescription;
import com.iemr.hwc.repo.snomedct.SnomedRepository;
import com.iemr.hwc.utils.mapper.OutputMapper;

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
			PageRequest pr = PageRequest.of(sctdescription.getPageNo(), snomedCTPageSize);
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
