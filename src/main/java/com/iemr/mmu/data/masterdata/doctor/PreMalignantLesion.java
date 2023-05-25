package com.iemr.mmu.data.masterdata.doctor;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_premalignantlesion")
public class PreMalignantLesion {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "PreMalignantLesionID")
	private Integer preMalignantLesionID;
	
	@Expose
	@Column(name = "PreMalignantLesionType")
	private String preMalignantLesionType;
	
	@Expose
	@Column(name = "PreMalignantLesionDesc")
	private String preMalignantLesionDesc;
	
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;
	
	public PreMalignantLesion(Integer preMalignantLesionID, String preMalignantLesionType) {
		super();
		this.preMalignantLesionID = preMalignantLesionID;
		this.preMalignantLesionType = preMalignantLesionType;
	}
	
	public static ArrayList<PreMalignantLesion> getPreMalignantLesionMasterData(ArrayList<Object[]> resList) {
		ArrayList<PreMalignantLesion> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			PreMalignantLesion cOBJ = new PreMalignantLesion((Integer) obj[0], (String) obj[1]);
			resArray.add(cOBJ);
		}
		return resArray;
	}
	
}
