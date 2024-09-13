package com.iemr.hwc.service.ncdscreening;
package com.iemr.hwc.service.ncdscreening;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.Gson;
import com.iemr.hwc.data.ncdScreening.DiabetesOutcome;
import com.iemr.hwc.data.ncdScreening.HypertensionOutcome;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.mapper.InputMapper;

public class TestDiabetesAndHypertensionOutcomeServiceImpl {

	@ExtendWith(MockitoExtension.class)

	private DiabetesAndHypertensionOutcomeServiceImpl service;

	@BeforeEach
	public void setUp() {
		service = new DiabetesAndHypertensionOutcomeServiceImpl();
	}

	@Test
	public void testGetHypertensionOutcome_NormalBP() throws IEMRException {
		HypertensionOutcome hypertensionOutcome = new HypertensionOutcome();
		hypertensionOutcome.setAverageSystolic(120);
		hypertensionOutcome.setAverageDiastolic(80);
		String request = new Gson().toJson(hypertensionOutcome);

		String response = service.getHypertensionOutcome(request);

		assertTrue(response.contains("Normal BP"));
	}

	@Test
	public void testGetHypertensionOutcome_PreHypertension() throws IEMRException {
		HypertensionOutcome hypertensionOutcome = new HypertensionOutcome();
		hypertensionOutcome.setAverageSystolic(130);
		hypertensionOutcome.setAverageDiastolic(85);
		String request = new Gson().toJson(hypertensionOutcome);

		String response = service.getHypertensionOutcome(request);

		assertTrue(response.contains("Pre-Hypertension"));
	}

	@Test
	public void testGetHypertensionOutcome_HypertensionStage1() throws IEMRException {
		HypertensionOutcome hypertensionOutcome = new HypertensionOutcome();
		hypertensionOutcome.setAverageSystolic(150);
		hypertensionOutcome.setAverageDiastolic(95);
		String request = new Gson().toJson(hypertensionOutcome);

		String response = service.getHypertensionOutcome(request);

		assertTrue(response.contains("Hypertension Stage-1"));
	}

	@Test
	public void testGetHypertensionOutcome_HypertensionStage2() throws IEMRException {
		HypertensionOutcome hypertensionOutcome = new HypertensionOutcome();
		hypertensionOutcome.setAverageSystolic(170);
		hypertensionOutcome.setAverageDiastolic(105);
		String request = new Gson().toJson(hypertensionOutcome);

		String response = service.getHypertensionOutcome(request);

		assertTrue(response.contains("Hypertension Stage-2"));
	}

	@Test
	public void testGetHypertensionOutcome_Exception() {
		String invalidRequest = "invalid json";

		Exception exception = assertThrows(IEMRException.class, () -> {
			service.getHypertensionOutcome(invalidRequest);
		});

		String expectedMessage = "Error while finding hypertension outcome";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testGetDiabetesOutcome_NonDiabeticRange_Type3() throws IEMRException {
		DiabetesOutcome outcome = new DiabetesOutcome();
		outcome.setBloodGlucoseTypeID(3);
		outcome.setBloodGlucose(140);

		String request = new Gson().toJson(outcome);
		String response = service.getDiabetesOutcome(request);

		assertTrue(response.contains("Non-Diabetic Range"));
	}

	@Test
	public void testGetDiabetesOutcome_PreDiabeticRange_Type3() throws IEMRException {
		DiabetesOutcome outcome = new DiabetesOutcome();
		outcome.setBloodGlucoseTypeID(3);
		outcome.setBloodGlucose(150);

		String request = new Gson().toJson(outcome);
		String response = service.getDiabetesOutcome(request);

		assertTrue(response.contains("Pre-Diabetic Range"));
	}

	@Test
	public void testGetDiabetesOutcome_DiabeticRange_Type3() throws IEMRException {
		DiabetesOutcome outcome = new DiabetesOutcome();
		outcome.setBloodGlucoseTypeID(3);
		outcome.setBloodGlucose(210);

		String request = new Gson().toJson(outcome);
		String response = service.getDiabetesOutcome(request);

		assertTrue(response.contains("Diabetic Range"));
	}

	@Test
	public void testGetDiabetesOutcome_NonDiabeticRange_Type1() throws IEMRException {
		DiabetesOutcome outcome = new DiabetesOutcome();
		outcome.setBloodGlucoseTypeID(1);
		outcome.setBloodGlucose(100);

		String request = new Gson().toJson(outcome);
		String response = service.getDiabetesOutcome(request);

		assertTrue(response.contains("Non-Diabetic Range"));
	}

	@Test
	public void testGetDiabetesOutcome_PreDiabeticRange_Type1() throws IEMRException {
		DiabetesOutcome outcome = new DiabetesOutcome();
		outcome.setBloodGlucoseTypeID(1);
		outcome.setBloodGlucose(110);

		String request = new Gson().toJson(outcome);
		String response = service.getDiabetesOutcome(request);

		assertTrue(response.contains("Pre-Diabetic Range"));
	}

	@Test
	public void testGetDiabetesOutcome_DiabeticRange_Type1() throws IEMRException {
		DiabetesOutcome outcome = new DiabetesOutcome();
		outcome.setBloodGlucoseTypeID(1);
		outcome.setBloodGlucose(130);

		String request = new Gson().toJson(outcome);
		String response = service.getDiabetesOutcome(request);

		assertTrue(response.contains("Diabetic Range"));
	}

	@Test
	public void testGetDiabetesOutcome_NormalNonDiabeticRange_Type2() throws IEMRException {
		DiabetesOutcome outcome = new DiabetesOutcome();
		outcome.setBloodGlucoseTypeID(2);
		outcome.setBloodGlucose(130);

		String request = new Gson().toJson(outcome);
		String response = service.getDiabetesOutcome(request);

		assertTrue(response.contains("Normal/Non-Diabetic Range"));
	}

	@Test
	public void testGetDiabetesOutcome_PreDiabeticRange_Type2() throws IEMRException {
		DiabetesOutcome outcome = new DiabetesOutcome();
		outcome.setBloodGlucoseTypeID(2);
		outcome.setBloodGlucose(150);

		String request = new Gson().toJson(outcome);
		String response = service.getDiabetesOutcome(request);

		assertTrue(response.contains("Pre-Diabetic Range"));
	}

	@Test
	public void testGetDiabetesOutcome_DiabeticRange_Type2() throws IEMRException {
		DiabetesOutcome outcome = new DiabetesOutcome();
		outcome.setBloodGlucoseTypeID(2);
		outcome.setBloodGlucose(210);

		String request = new Gson().toJson(outcome);
		String response = service.getDiabetesOutcome(request);

		assertTrue(response.contains("Diabetic Range"));
	}

	@Test
	public void testGetDiabetesOutcome_InvalidType() {
		DiabetesOutcome outcome = new DiabetesOutcome();
		outcome.setBloodGlucoseTypeID(4);
		outcome.setBloodGlucose(100);

		String request = new Gson().toJson(outcome);

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			service.getDiabetesOutcome(request);
		});

		assertTrue(exception.getMessage().contains("Error while finding diabetes outcome"));
	}

	@Test
	public void testGetDiabetesOutcome_ExceptionHandling() {
		String invalidRequest = "invalid json";

		IEMRException exception = assertThrows(IEMRException.class, () -> {
			service.getDiabetesOutcome(invalidRequest);
		});

		assertTrue(exception.getMessage().contains("Error while finding diabetes outcome"));
	}
}
