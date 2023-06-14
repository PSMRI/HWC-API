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
package com.iemr.mmu.report;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;

import org.junit.Test;

import com.iemr.mmu.service.report.CRMReportServiceImpl;

public class TestReport {

	@Test
	public void TimeTest1() {

		Timestamp consultedTime = new Timestamp(2019, 01, 01, 10, 22, 5, 100);
		Timestamp arrivalTime = new Timestamp(2019, 01, 01, 10, 21, 10, 100);

		String result = CRMReportServiceImpl.calculateTime(consultedTime, arrivalTime);
		System.out.println(result);
		assertEquals(result, "55 sec");
	}

	@Test
	public void TimeTest2() {

		Timestamp consultedTime = new Timestamp(2019, 01, 01, 9, 40, 5, 100);
		Timestamp arrivalTime = new Timestamp(2019, 01, 01, 10, 21, 10, 100);

		String result = CRMReportServiceImpl.calculateTime(consultedTime, arrivalTime);
		System.out.println(result);
		assertEquals(result, "No Waiting");

		consultedTime = new Timestamp(2019, 01, 01, 8, 22, 5, 100);
		arrivalTime = new Timestamp(2019, 01, 01, 10, 21, 10, 100);

		result = CRMReportServiceImpl.calculateTime(consultedTime, arrivalTime);
		System.out.println(result);
		assertEquals(result, "No Waiting");
	}

	@Test
	public void TimeTest3() {

		Timestamp consultedTime = new Timestamp(2019, 01, 01, 11, 21, 5, 100);
		Timestamp arrivalTime = new Timestamp(2019, 01, 01, 10, 21, 10, 100);

		String result = CRMReportServiceImpl.calculateTime(consultedTime, arrivalTime);
		System.out.println(result);
		assertEquals(result, "59 min");
	}

	@Test
	public void TimeTest4() {

		Timestamp consultedTime = new Timestamp(2019, 01, 01, 10, 23, 5, 100);
		Timestamp arrivalTime = new Timestamp(2019, 01, 01, 10, 21, 10, 100);

		String result = CRMReportServiceImpl.calculateTime(consultedTime, arrivalTime);
		System.out.println(result);
		assertEquals(result, "1 min");
	}

}
