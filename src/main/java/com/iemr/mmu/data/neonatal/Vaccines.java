package com.iemr.mmu.data.neonatal;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Vaccines {

	private String vaccineName;
	private String vaccineDose;
	private String batchNo;
	private String route;
	private String siteOfInjection;

}
