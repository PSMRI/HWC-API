/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.anc;

import org.springframework.stereotype.Component;

@Component
public class TypeOfDeliveryHRP {

	private String deliveryType;

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

}
