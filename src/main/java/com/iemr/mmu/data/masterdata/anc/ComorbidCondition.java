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
@Table(name = "m_comorbidcondition")
public class ComorbidCondition {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "ComorbidConditionID")
	private Short comorbidConditionID;

	@Expose
	@Column(name = "ComorbidCondition")
	private String comorbidCondition;

	@Expose
	@Column(name = "ComorbidConditionDesc")
	private String comorbidConditionDesc;

	@Expose
	@Column(name = "VisitCategoryID")
	private Integer visitCategoryID;

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

	public ComorbidCondition(Short comorbidConditionID, String comorbidCondition) {
		super();
		this.comorbidConditionID = comorbidConditionID;
		this.comorbidCondition = comorbidCondition;
	}

	public static ArrayList<ComorbidCondition> getComorbidConditions(ArrayList<Object[]> resList) {
		ArrayList<ComorbidCondition> resArray = new ArrayList<ComorbidCondition>();
		for (Object[] obj : resList) {
			ComorbidCondition cOBJ = new ComorbidCondition((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
}
