/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.anc;

import org.springframework.stereotype.Component;

@Component
public class ComplicationDuringPregnancyHRP {
	private String pregComplicationType;

	public String getPregComplicationType() {
		return pregComplicationType;
	}

	public void setPregComplicationType(String pregComplicationType) {
		this.pregComplicationType = pregComplicationType;
	}

}
