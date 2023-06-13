/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.data.masterdata.doctor;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_itemform")
public class ItemFormMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ItemFormID")
	private Integer itemFormID;
	@Expose
	@Column(name = "ItemFormName")
	private String itemFormName;
	@Expose
	@Column(name = "ItemFormDesc")
	private String itemFormDesc;
	@Expose
	@Column(name = "ItemFormCode")
	private String itemFormCode;
	@Expose
	@Column(name = "Status")
	private String status;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;
	@Expose
	@Column(name = "Processed")
	private String processed;
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate")
	private Timestamp createdDate;
	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Expose
	@Column(name = "LastModDate")
	private Timestamp lastModDate;

	public ItemFormMaster() {
		super();
	}

	public ItemFormMaster(Integer itemFormID, String itemFormName) {
		this.itemFormID = itemFormID;
		this.itemFormName = itemFormName;
	}

	public static ArrayList<ItemFormMaster> getItemFormList(ArrayList<Object[]> resultSet) {
		ArrayList<ItemFormMaster> returnOBJ = new ArrayList<>();
		ItemFormMaster itemOBJ;
		if (resultSet.size() > 0) {
			for (Object[] obj : resultSet) {
				itemOBJ = new ItemFormMaster((Integer) obj[0], (String) obj[1]);
				returnOBJ.add(itemOBJ);
			}
		}
		return returnOBJ;
	}

}
