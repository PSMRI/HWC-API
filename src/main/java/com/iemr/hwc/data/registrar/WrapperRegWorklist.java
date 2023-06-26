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
package com.iemr.hwc.data.registrar;

import java.sql.Date;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class WrapperRegWorklist {
	@Expose
	private Long beneficiaryRegID;
	@Expose
	private String beneficiaryID;
	@Expose
	private String benName;
	@Expose
	private Date dob;
	@Expose
	private Short genderID;
	@Expose
	private String genderName;
	@Expose
	private String fatherName;
	@Expose
	private Integer districtID;
	@Expose
	private String districtName;
	@Expose
	private Integer villageID;
	@Expose
	private String villageName;
	@Expose
	private String phoneNo;
	@Expose
	private String age;
	@Expose
	private String visitFlowStatusFlag;
	@Expose
	private String VisitCategory;
	@Expose
	private Long benVisitID;
	@Expose
	private Short benVisitNo;
	@Expose
	private String visitDate;

	@Expose
	private String statusMessage;

	public static String getDocWorkListData(List<Object[]> resList) {
		ArrayList<WrapperRegWorklist> resArray = new ArrayList<>();
		if (resList.size() > 0) {
			for (Object[] obj : resList) {
				WrapperRegWorklist wrapperRegWorklist = new WrapperRegWorklist();
				wrapperRegWorklist.beneficiaryRegID = (Long) obj[0];
				wrapperRegWorklist.beneficiaryID = (String) obj[1];
				wrapperRegWorklist.benName = (String) obj[2];
				// wrapperRegWorklist.dob = (Date) obj[3];
				if (obj[3] != null) {
					Date date = (Date) obj[3];
					Calendar cal = Calendar.getInstance();

					cal.setTime(date);

					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					int day = cal.get(Calendar.DAY_OF_MONTH);

					java.time.LocalDate todayDate = java.time.LocalDate.now();
					java.time.LocalDate birthdate = java.time.LocalDate.of(year, month, day);
					Period p = Period.between(birthdate, todayDate);

					int d = p.getDays();
					int m = p.getMonths();
					int y = p.getYears();
					//System.out.println("helloo...");

					if (y > 0) {
						wrapperRegWorklist.age = y + " years - " + m + " months";
					} else {
						if (m > 0) {
							wrapperRegWorklist.age = m + " months - " + d + " days";
						} else {
							wrapperRegWorklist.age = d + " days";
						}
					}

					//System.out.println("helloo");
				}
				wrapperRegWorklist.genderID = (Short) obj[4];
				wrapperRegWorklist.genderName = (String) obj[5];
				wrapperRegWorklist.benVisitID = (Long) obj[6];
				wrapperRegWorklist.benVisitNo = (Short) obj[7];
				wrapperRegWorklist.visitFlowStatusFlag = (String) obj[8];
				wrapperRegWorklist.VisitCategory = (String) obj[9];

				wrapperRegWorklist.fatherName = (String) obj[10];
				wrapperRegWorklist.districtName = (String) obj[11];
				wrapperRegWorklist.villageName = (String) obj[12];
				wrapperRegWorklist.phoneNo = (String) obj[13];
				wrapperRegWorklist.visitDate = (String) obj[14];

				if (obj[8].toString().equals("N")) {
				//	System.out.println("ifff");
					wrapperRegWorklist.statusMessage = "Pending For Consultation";
				} else {
					//System.out.println("else");
					if (obj[8].toString().equals("D")) {
						//System.out.println(" again ifff");
						wrapperRegWorklist.statusMessage = "Consultation Done";
					}
					else{
						wrapperRegWorklist.statusMessage = "Visit closed by Nurse";
					}
				}
				resArray.add(wrapperRegWorklist);
			//	System.out.println("helloooo");

			//	System.out.println(new Gson().toJson(resArray));
			}
		}
		return new Gson().toJson(resArray);
	}

	public static String getRegistrarWorkList(List<Object[]> resList) {
		// GsonBuilder gsonBuilder = new GsonBuilder();
		// gsonBuilder.serializeNulls();
		// Gson gson = gsonBuilder.create();

		ArrayList<WrapperRegWorklist> resArray = new ArrayList<>();
		if (resList.size() > 0) {
			for (Object[] obj : resList) {
				WrapperRegWorklist wrapperRegWorklist = new WrapperRegWorklist();
				wrapperRegWorklist.beneficiaryRegID = (Long) obj[0];
				wrapperRegWorklist.beneficiaryID = (String) obj[1];
				wrapperRegWorklist.benName = (String) obj[2];
				// wrapperRegWorklist.dob = (Date) obj[3];
				if (obj[3] != null) {
					Date date = (Date) obj[3];
					Calendar cal = Calendar.getInstance();

					cal.setTime(date);

					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					int day = cal.get(Calendar.DAY_OF_MONTH);

					java.time.LocalDate todayDate = java.time.LocalDate.now();
					java.time.LocalDate birthdate = java.time.LocalDate.of(year, month, day);
					Period p = Period.between(birthdate, todayDate);

					int d = p.getDays();
					int m = p.getMonths();
					int y = p.getYears();
					//System.out.println("helloo...");

					if (y > 0) {
						wrapperRegWorklist.age = y + " years - " + m + " months";
					} else {
						if (m > 0) {
							wrapperRegWorklist.age = m + " months - " + d + " days";
						} else {
							wrapperRegWorklist.age = d + " days";
						}
					}

				//	System.out.println("helloo");
				}
				wrapperRegWorklist.genderID = (Short) obj[4];
				wrapperRegWorklist.genderName = (String) obj[5];
				wrapperRegWorklist.fatherName = (String) obj[6];
				wrapperRegWorklist.districtName = (String) obj[8];
				wrapperRegWorklist.villageName = (String) obj[10];
				wrapperRegWorklist.phoneNo = (String) obj[11];
				resArray.add(wrapperRegWorklist);
				//System.out.println("helloooo");
			}
		}
		return new Gson().toJson(resArray);
	}
}