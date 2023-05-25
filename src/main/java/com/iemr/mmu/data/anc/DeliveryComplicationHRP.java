package com.iemr.mmu.data.anc;

import org.springframework.stereotype.Component;

@Component
public class DeliveryComplicationHRP {
	private String deliveryComplicationType;

	public String getDeliveryComplicationType() {
		return deliveryComplicationType;
	}

	public void setDeliveryComplicationType(String deliveryComplicationType) {
		this.deliveryComplicationType = deliveryComplicationType;
	}

}
