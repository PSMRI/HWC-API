package com.iemr.mmu.data.fetosense;

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
@Table(name = "m_fetosensetests")
public class FetosenseTestMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "fetosensetestid")
	private Integer fetosenseTestId;
	
	@Expose
	@Column(name = "TestName") 
	private String testName;
	
	@Expose
    @Column(name = "TestDesc") 
    private String testDesc;
	
	
	
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	
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

	

	
	public FetosenseTestMaster(Integer fetosenseTestId, String testName) {
		super();
		this.fetosenseTestId = fetosenseTestId;
		this.testName = testName;
	}

	
	public static ArrayList<FetosenseTestMaster> getFetosenseMasters(ArrayList<Object[]> resList) {
		ArrayList<FetosenseTestMaster> resArray = new ArrayList<FetosenseTestMaster>();
		for (Object[] obj : resList) {
			FetosenseTestMaster fetosenseOBJ = new FetosenseTestMaster((Integer)obj[0], (String)obj[1]);
			resArray.add(fetosenseOBJ);
		}
		return resArray;
	}
	
	

}
