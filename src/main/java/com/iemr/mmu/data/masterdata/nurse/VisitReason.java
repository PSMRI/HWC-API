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
@Table(name = "m_visitreason")
public class VisitReason {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "VisitReasonID")
	private Short visitReasonID;
	
	@Expose
	@Column(name = "VisitReason")
	private String visitReason;
	
	@Expose
	@Column(name = "VisitReasonDesc")
	private String visitReasonDesc;
	
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public VisitReason(Short visitReasonID, String visitReason) {
		super();
		this.visitReasonID = visitReasonID;
		this.visitReason = visitReason;
	}

	public static ArrayList<VisitReason> getVisitReasonMasterData(ArrayList<Object[]> resList) {
		ArrayList<VisitReason> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			VisitReason cOBJ = new VisitReason((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

}
