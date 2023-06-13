/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.masterdata.nurse;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_cancerpersonalhabittype")
public class CancerPersonalHabitType {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "CancerPersonalHabitID")
	private Short cancerPersonalHabitID;
	
	@Expose
	@Column(name = "HabitType")
	private String habitType;
	
	@Expose
	@Column(name = "HabitValue")
	private String habitValue;
	
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public CancerPersonalHabitType(Short cancerPersonalHabitID, String habitType, String habitValue) {
		super();
		this.cancerPersonalHabitID = cancerPersonalHabitID;
		this.habitType = habitType;
		this.habitValue = habitValue;
	}
	
	public static ArrayList<CancerPersonalHabitType> getCancerPersonalHabitTypeMasterData(ArrayList<Object[]> resList) {
		ArrayList<CancerPersonalHabitType> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			CancerPersonalHabitType cOBJ = new CancerPersonalHabitType((Short) obj[0], (String) obj[1], (String) obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

	
}
