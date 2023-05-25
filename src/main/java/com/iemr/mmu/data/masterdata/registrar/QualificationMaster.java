package com.iemr.mmu.data.masterdata.registrar;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_beneficiaryeducation")
public class QualificationMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "EducationID")
	private Short educationID;
	@Expose
	@Column(name = "EducationType")
	private String educationType;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public QualificationMaster() {
	}

	public QualificationMaster(Short educationID, String educationType) {
		this.educationID = educationID;
		this.educationType = educationType;
	}

	public static ArrayList<QualificationMaster> getQualificationMasterData(ArrayList<Object[]> resList) {
		ArrayList<QualificationMaster> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			QualificationMaster cOBJ = new QualificationMaster((Short) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
}
