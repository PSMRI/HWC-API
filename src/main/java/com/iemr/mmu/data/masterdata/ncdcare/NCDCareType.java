/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.masterdata.ncdcare;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.iemr.mmu.data.masterdata.ncdscreening.NCDScreeningCondition;

@Entity
@Table(name = "m_ncdcaretype")
public class NCDCareType
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "NCDCareTypeID")
	private Integer ncdCareTypeID;
			
	@Expose
	@Column(name = "NCDCareType")
	private String  ncdCareType;
					
	@Expose
	@Column(name = "NCDCareTypeDesc")
	private String ncdCareTypeDesc;
	
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

	public NCDCareType(Integer ncdCareTypeID, String ncdCareType)
	{
		super();
		this.ncdCareTypeID = ncdCareTypeID;
		this.ncdCareType = ncdCareType;
	}
	
	public static ArrayList<NCDCareType> getNCDCareTypes(ArrayList<Object[]> resList) {
		ArrayList<NCDCareType> resArray = new ArrayList<NCDCareType>();
		for (Object[] obj : resList) {
			NCDCareType cOBJ = new NCDCareType((Integer)obj[0], (String)obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	} 
}
