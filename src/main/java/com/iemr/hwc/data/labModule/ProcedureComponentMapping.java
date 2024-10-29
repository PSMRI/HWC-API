/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.hwc.data.labModule;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


@Entity
@Table(name = "m_procedurecomponentmap")
public class ProcedureComponentMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name = "ProcedureComponentMapID")
	private Integer procedureComponentMapID;
	@Expose
	@Column(name = "ProcedureID")
	private Integer procedureID;
	@Expose
	@Column(name = "TestComponentID")
	private Integer testComponentID;
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	@Expose
	@Column(name = "Deleted", insertable = false)
	private Boolean deleted;
	@Expose
	@Column(name = "Processed", insertable = false)
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
	@Column(name = "LastModDate", insertable = false)
	private Timestamp lastModDate;

	@Transient
	@Expose
	private ArrayList<Map<String, Object>> compList;

	@Expose
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "testComponentID", insertable = false, updatable = false)
	private TestComponentMaster compMaster;

	@Expose
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "procedureID", insertable = false, updatable = false)
	private ProcedureData procMaster;

	@Transient
	@Expose
	private String procedureName;
	@Transient
	@Expose
	private String mappedComponent;

	@Transient
	@Expose
	private String procedureDesc;

	public TestComponentMaster getCompMaster() {
		return compMaster;
	}

	public void setCompMaster(TestComponentMaster compMaster) {
		this.compMaster = compMaster;
	}

	public ProcedureData getProcMaster() {
		return procMaster;
	}

	public void setProcMaster(ProcedureData procMaster) {
		this.procMaster = procMaster;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getMappedComponent() {
		return mappedComponent;
	}

	public String getProcedureDesc() {
		return procedureDesc;
	}

	public void setProcedureDesc(String procedureDesc) {
		this.procedureDesc = procedureDesc;
	}

	public void setMappedComponent(String mappedComponent) {
		this.mappedComponent = mappedComponent;
	}

	public ProcedureComponentMapping() {
	}

	public ProcedureComponentMapping(Integer procedureComponentMapID, Integer procedureID, Integer testComponentID,
			Integer providerServiceMapID, Boolean deleted, String processed, String createdBy, Timestamp createdDate,
			String modifiedBy, Timestamp lastModDate, ArrayList<Map<String, Object>> compList,
			TestComponentMaster compMaster, ProcedureData procMaster, String procedureName, String mappedComponent,
			String procedureDesc) {
		super();
		this.procedureComponentMapID = procedureComponentMapID;
		this.procedureID = procedureID;
		this.testComponentID = testComponentID;
		this.providerServiceMapID = providerServiceMapID;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
		this.compList = compList;
		this.compMaster = compMaster;
		this.procMaster = procMaster;
		this.procedureName = procedureName;
		this.mappedComponent = mappedComponent;
		this.procedureDesc = procedureDesc;
	}

	public ProcedureComponentMapping(Integer procedureComponentMapID, Integer procedureID, Integer testComponentID,
			Integer providerServiceMapID, Boolean deleted, String processed, String createdBy, Timestamp createdDate,
			String modifiedBy, Timestamp lastModDate) {
		super();
		this.procedureComponentMapID = procedureComponentMapID;
		this.procedureID = procedureID;
		this.testComponentID = testComponentID;
		this.providerServiceMapID = providerServiceMapID;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
	}

	public ProcedureComponentMapping(Integer procedureComponentMapID, Integer procedureID, Integer testComponentID,
			Integer providerServiceMapID, Boolean deleted, String processed, String createdBy, Timestamp createdDate,
			String modifiedBy, Timestamp lastModDate, ArrayList<Map<String, Object>> compList) {
		super();
		this.procedureComponentMapID = procedureComponentMapID;
		this.procedureID = procedureID;
		this.testComponentID = testComponentID;
		this.providerServiceMapID = providerServiceMapID;
		this.deleted = deleted;
		this.processed = processed;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModDate = lastModDate;
		this.compList = compList;
	}

	public static ArrayList<ProcedureComponentMapping> getProcedureComponentMappingObjList(
			ArrayList<Map<String, Object>> compList, Integer pID, Integer psmID, String createdBy) {
		ArrayList<ProcedureComponentMapping> pcmList = new ArrayList<>();
		ProcedureComponentMapping pcmOBJ;
		for (Map<String, Object> cMap : compList) {
			if (cMap.containsKey("testComponentID") && cMap.get("testComponentID") != null) {
				pcmOBJ = new ProcedureComponentMapping();
				Double cID = (Double) cMap.get("testComponentID");
				pcmOBJ.setTestComponentID(cID.intValue());
				pcmOBJ.setProcedureID(pID);
				pcmOBJ.setProviderServiceMapID(psmID);
				pcmOBJ.setCreatedBy(createdBy);
				pcmList.add(pcmOBJ);
			}
		}
		return pcmList;
	}
//	@Autowired
//	private static ComponentMasterRepo componentMasterRepo;



	public ProcedureComponentMapping(Integer procedureID, String procedureName, String mappedComponent,
			String procedureDesc) {
		super();
		this.procedureID = procedureID;
		this.procedureName = procedureName;
		this.mappedComponent = mappedComponent;
		this.procedureDesc = procedureDesc;
	}

	public static ArrayList<Map<String, Object>> getProcedureComponentMappingObjListDetails(ArrayList<Object[]> obj) {
        ArrayList<ProcedureComponentMapping> pcmList = new ArrayList<>();
        ProcedureComponentMapping pcmOBJ;
        Map<String, Object> procCompListMap = null;
        ArrayList<Map<String, Object>> tmpList = new ArrayList<>();
        Map<String, Object> compMap = null;
        ArrayList<Map<String, Object>> compList = null;
        StringBuilder s = null;

 

        Integer tmpProcID = 0;
        int i = 0;
        for (Object[] obj1 : obj) {
            Integer procID = (Integer) obj1[0];

 

            if (true) {
                i = 1;
                procCompListMap = new HashMap<String, Object>();

 

                compList = new ArrayList<>();
                compMap = new HashMap<>();
                compMap.put("testComponentID", obj1[1]);
                compMap.put("testComponentName", obj1[4]);
                compMap.put("testComponentDesc", obj1[6]);
                compList.add(compMap);

 

                s = new StringBuilder("(" + i + ")" + (String) obj1[4]);
                procCompListMap.put("procedureID", procID);
                procCompListMap.put("procedureName", (String) obj1[2]);
                procCompListMap.put("procedureDesc", (String) obj1[3]);
                procCompListMap.put("compList", s);

 

                procCompListMap.put("compListDetails", compList);

 

                tmpList.add(procCompListMap);
                i++;
            } 
//            else {
//                compMap = new HashMap<>();
//                compMap.put("testComponentID", obj1[1]);
//                compMap.put("testComponentName", obj1[4]);
//                compMap.put("testComponentDesc", obj1[6]);
//                compList.add(compMap);
//
//                s.append(", (" + i + ")" + (String) obj1[4]);
//                procCompListMap.put("compList", s);
//
//                procCompListMap.put("compListDetails", compList);
//
//                i++;
//            }

 

            tmpProcID = procID;
        }
        return tmpList;
    }
	
	
	public static ArrayList<Map<String, Object>> getProcedureComponentMappedMasterDataObjListDetails(ArrayList<Object[]> obj) {
        ArrayList<ProcedureComponentMapping> pcmList = new ArrayList<>();
        ProcedureComponentMapping pcmOBJ;
        Map<String, Object> procCompListMap = null;
        ArrayList<Map<String, Object>> tmpList = new ArrayList<>();
        Map<String, Object> compMap = null;
        ArrayList<Map<String, Object>> compList = null;
        StringBuilder s = null;

 

        Integer tmpProcID = 0;
        int i = 0;
        for (Object[] obj1 : obj) {
            Integer procID = (Integer) obj1[1];

 

            if (true) {
                i = 1;
                procCompListMap = new HashMap<String, Object>();

 

                compList = new ArrayList<>();
                compMap = new HashMap<>();
                compMap.put("testComponentID", obj1[2]);
                compMap.put("testComponentName", obj1[13]);
                compMap.put("testComponentDesc", obj1[14]);
                compMap.put("loinc_num", obj1[15]);
                compMap.put("loinc_component", obj1[16]);
                compMap.put("inputType", obj1[17]);
				compMap.put("measurementUnit", obj1[18]);
				compMap.put("isDecimal",obj1[19]);
				compMap.put("range_min", obj1[20]);
				compMap.put("range_normal_min", obj1[21]);
				compMap.put("range_normal_max", obj1[22]);
				compMap.put("range_max", obj1[23]);
                compList.add(compMap);

 

                s = new StringBuilder("(" + i + ")" + (String) obj1[13]);
                procCompListMap.put("procedureID", procID);
                procCompListMap.put("procedureName", (String) obj1[6]);
                procCompListMap.put("procedureDesc", (String) obj1[7]);
		procCompListMap.put("procedureType", (String) obj1[8]);
		procCompListMap.put("gender", (String) obj1[9]);
		procCompListMap.put("IOTProcedureID", obj1[10]);
	//	procCompListMap.put("isMandatory", (String) obj1[11]);
			procCompListMap.put("isMandatory", obj1[11]);
		procCompListMap.put("isCalibration", (String) obj1[12]);
                procCompListMap.put("compList", s);

 

                procCompListMap.put("compListDetails", compList);

 

                tmpList.add(procCompListMap);
                i++;
            } 
 

            tmpProcID = procID;
        }
        return tmpList;
        }

	public Integer getProcedureComponentMapID() {
		return procedureComponentMapID;
	}

	public void setProcedureComponentMapID(Integer procedureComponentMapID) {
		this.procedureComponentMapID = procedureComponentMapID;
	}

	public Integer getProcedureID() {
		return procedureID;
	}

	public void setProcedureID(Integer procedureID) {
		this.procedureID = procedureID;
	}

	public Integer getTestComponentID() {
		return testComponentID;
	}

	public void setTestComponentID(Integer testComponentID) {
		this.testComponentID = testComponentID;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getLastModDate() {
		return lastModDate;
	}

	public void setLastModDate(Timestamp lastModDate) {
		this.lastModDate = lastModDate;
	}

	public ArrayList<Map<String, Object>> getCompList() {
		return compList;
	}

	public void setCompList(ArrayList<Map<String, Object>> compList) {
		this.compList = compList;
	}

}
