/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.masterdata.pnc;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.data.masterdata.anc.AllergicReactionTypes;

@Entity
@Table(name = "m_newbornhealthstatus")
public class NewbornHealthStatus
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "NewBornHealthStatusID")
	private Integer newBornHealthStatusID;
	
	@Expose
	@Column(name = "NewBornHealthStatus")
	private String newBornHealthStatus;
	
	@Expose
	@Column(name = "NewBornHealthStatusDesc")
	private String newBornHealthStatusDesc;
	
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

	public NewbornHealthStatus(Integer newBornHealthStatusID, String newBornHealthStatus, String newBornHealthStatusDesc)
	{
		super();
		this.newBornHealthStatusID = newBornHealthStatusID;
		this.newBornHealthStatus = newBornHealthStatus;
		this.newBornHealthStatusDesc = newBornHealthStatusDesc;
	}

	public static ArrayList<NewbornHealthStatus> getNewbornHealthStatuses(ArrayList<Object[]> resList) {
		ArrayList<NewbornHealthStatus> resArray = new ArrayList<NewbornHealthStatus>();
		for (Object[] obj : resList) {
			NewbornHealthStatus cOBJ = new NewbornHealthStatus((Integer)obj[0], (String)obj[1], (String)obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
}
