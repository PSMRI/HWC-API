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
