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
package com.iemr.hwc.data.anc;

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
