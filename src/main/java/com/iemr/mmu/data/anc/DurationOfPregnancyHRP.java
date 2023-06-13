/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.anc;

import org.springframework.stereotype.Component;

@Component
public class DurationOfPregnancyHRP {

	private String durationType;

	public String getDurationType() {
		return durationType;
	}

	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}

}
