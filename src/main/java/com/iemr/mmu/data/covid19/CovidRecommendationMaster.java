package com.iemr.mmu.data.covid19;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_covidrecommendation")
public class CovidRecommendationMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name = "CovidrecommendationID", insertable = false, updatable = false)
	private Integer CovidrecommendationID;
	@Expose
	@Column(name = "Recommendation")
	private String recommendation;
	@Expose
	@Column(name = "Deleted", insertable = false, updatable = false)
	private Boolean deleted;

}
