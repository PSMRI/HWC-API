package com.iemr.mmu.data.masterdata.ncdscreening;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_physicalactivity")
public class PhysicalActivity {

	@Id
	@GeneratedValue
	@Expose
	@Column(name = "PAID")
	private Integer pAID;

	@Expose
	@Column(name = "ActivityType")
	private String activityType;

	@Expose
	@Column(name = "Score")
	private Integer score;

	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public Integer getpAID() {
		return pAID;
	}

	public void setpAID(Integer pAID) {
		this.pAID = pAID;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
