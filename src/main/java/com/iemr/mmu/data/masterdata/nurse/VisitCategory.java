package com.iemr.mmu.data.masterdata.nurse;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_visitcategory")
public class VisitCategory {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "VisitCategoryID")
	private Short visitCategoryID;
	
	@Expose
	@Column(name = "VisitCategory")
	private String visitCategory;
	
	@Expose
	@Column(name = "VisitCategoryDesc")
	private String visitCategoryDesc;
	
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public VisitCategory(Short visitCategoryID, String visitCategory) {
		super();
		this.visitCategoryID = visitCategoryID;
		this.visitCategory = visitCategory;
	}

	public static ArrayList<VisitCategory> getVisitCategoryMasterData(ArrayList<Object[]> resList) {
		ArrayList<VisitCategory> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			VisitCategory cOBJ = new VisitCategory((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

	
	
}
