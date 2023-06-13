/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.masterdata.anc;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_menstrualcyclerange")
public class MenstrualCycleRange {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "MenstrualRangeID")
	private Short menstrualRangeID;
	
	@Expose
	@Column(name = "RangeType")
	private String rangeType;
	
	@Expose
	@Column(name = "MenstrualCycleRange")
	private String menstrualCycleRange;
	
	@Expose
	@Column(name = "MenstrualCycleRangeDesc")
	private String menstrualCycleRangeDesc;
	
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;

	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;

	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	public MenstrualCycleRange(Short menstrualRangeID, String rangeType, String menstrualCycleRange,
			String menstrualCycleRangeDesc) {
		super();
		this.menstrualRangeID = menstrualRangeID;
		this.rangeType = rangeType;
		this.menstrualCycleRange = menstrualCycleRange;
		this.menstrualCycleRangeDesc = menstrualCycleRangeDesc;
	}
	
	public static ArrayList<MenstrualCycleRange> getMenstrualCycleRanges(ArrayList<Object[]> resList) {
		ArrayList<MenstrualCycleRange> resArray = new ArrayList<MenstrualCycleRange>();
		for (Object[] obj : resList) {
			MenstrualCycleRange cOBJ = new MenstrualCycleRange((Short)obj[0], (String)obj[1], (String)obj[2], (String)obj[3]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
}
