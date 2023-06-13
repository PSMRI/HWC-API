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
@Table(name = "m_developmentproblem")
public class DevelopmentProblems {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "ID")
	private Short ID;
	
	@Expose
	@Column(name = "DevelopmentProblem")
	private String developmentProblem;
	
	@Expose
	@Column(name = "ProblemDesc")
	private String problemDesc;
	
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

	public DevelopmentProblems(Short iD, String developmentProblem, String problemDesc) {
		super();
		ID = iD;
		this.developmentProblem = developmentProblem;
		this.problemDesc = problemDesc;
	}
	
	public static ArrayList<DevelopmentProblems> getDevelopmentProblems(ArrayList<Object[]> resList) {
		ArrayList<DevelopmentProblems> resArray = new ArrayList<DevelopmentProblems>();
		for (Object[] obj : resList) {
			DevelopmentProblems cOBJ = new DevelopmentProblems((Short)obj[0], (String)obj[1], (String)obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
}
