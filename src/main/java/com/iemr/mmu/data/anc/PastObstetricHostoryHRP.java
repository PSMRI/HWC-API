/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.anc;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PastObstetricHostoryHRP {
	private List<ComplicationDuringPregnancyHRP> complicationDuringPregnancy;
	private List<DeliveryComplicationHRP> deliveryComplication;
	private String congenitalAnomalies;

	private DurationOfPregnancyHRP durationOfPregnancy;
	private TypeOfDeliveryHRP typeOfDelivery;

	public DurationOfPregnancyHRP getDurationOfPregnancy() {
		return durationOfPregnancy;
	}

	public void setDurationOfPregnancy(DurationOfPregnancyHRP durationOfPregnancy) {
		this.durationOfPregnancy = durationOfPregnancy;
	}

	public TypeOfDeliveryHRP getTypeOfDelivery() {
		return typeOfDelivery;
	}

	public void setTypeOfDelivery(TypeOfDeliveryHRP typeOfDelivery) {
		this.typeOfDelivery = typeOfDelivery;
	}

	public List<ComplicationDuringPregnancyHRP> getComplicationDuringPregnancy() {
		return complicationDuringPregnancy;
	}

	public void setComplicationDuringPregnancy(List<ComplicationDuringPregnancyHRP> complicationDuringPregnancy) {
		this.complicationDuringPregnancy = complicationDuringPregnancy;
	}

	public List<DeliveryComplicationHRP> getDeliveryComplication() {
		return deliveryComplication;
	}

	public void setDeliveryComplication(List<DeliveryComplicationHRP> deliveryComplication) {
		this.deliveryComplication = deliveryComplication;
	}

	public String getCongenitalAnomalies() {
		return congenitalAnomalies;
	}

	public void setCongenitalAnomalies(String congenitalAnomalies) {
		this.congenitalAnomalies = congenitalAnomalies;
	}

}
