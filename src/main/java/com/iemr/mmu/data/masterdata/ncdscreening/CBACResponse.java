package com.iemr.mmu.data.masterdata.ncdscreening;

import java.util.HashMap;
import java.util.List;

public class CBACResponse {

	String question;
	Integer questionId;
	List<HashMap<String,String>> options;
	String questionType;
	String section;
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public List<HashMap<String, String>> getOptions() {
		return options;
	}
	public void setOptions(List<HashMap<String, String>> options) {
		this.options = options;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	
}
