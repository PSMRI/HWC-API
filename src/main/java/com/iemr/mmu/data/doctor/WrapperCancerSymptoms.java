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
package com.iemr.mmu.data.doctor;

import java.util.List;

public class WrapperCancerSymptoms {
	
	private CancerSignAndSymptoms cancerSignAndSymptoms;
	
	private List<CancerLymphNodeDetails> cancerLymphNodeDetails;

	public CancerSignAndSymptoms getCancerSignAndSymptoms() {
		return cancerSignAndSymptoms;
	}

	public void setCancerSignAndSymptoms(CancerSignAndSymptoms cancerSignAndSymptoms) {
		this.cancerSignAndSymptoms = cancerSignAndSymptoms;
	}

	public List<CancerLymphNodeDetails> getCancerLymphNodeDetails() {
		return cancerLymphNodeDetails;
	}

	public void setCancerLymphNodeDetails(List<CancerLymphNodeDetails> cancerLymphNodeDetails) {
		this.cancerLymphNodeDetails = cancerLymphNodeDetails;
	}
	
}
