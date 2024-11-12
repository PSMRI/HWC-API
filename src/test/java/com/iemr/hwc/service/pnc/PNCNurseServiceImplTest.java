package com.iemr.hwc.service.pnc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.data.pnc.PNCCare;
import com.iemr.hwc.repo.nurse.pnc.PNCCareRepo;

@ExtendWith(MockitoExtension.class)
class PNCNurseServiceImplTest {
	@Mock
	private PNCCareRepo pNCCareRepo;

	@InjectMocks
	private PNCNurseServiceImpl pNCNurseServiceImpl;

	@Test
	void testSetPncCareRepo() {
		(new PNCNurseServiceImpl()).setPncCareRepo(mock(PNCCareRepo.class));
	}

	@Test
	void testSaveBenPncCareDetails() throws ParseException {
		// Arrange
		PNCCare pncCare = new PNCCare();
		pncCare.setBenVisitID(1L);
		pncCare.setBeneficiaryRegID(1L);
		pncCare.setBirthWeightOfNewborn(10.0d);
		pncCare.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncCare.setCreatedDate(mock(Timestamp.class));
		pncCare.setDateOfDelivery(mock(Date.class));
		pncCare.setDeleted(true);
		pncCare.setDeliveryComplication("Delivery Complication");
		pncCare.setDeliveryComplicationID((short) 1);
		pncCare.setDeliveryConductedBy("Delivery Conducted By");
		pncCare.setDeliveryConductedByID(1);
		pncCare.setDeliveryPlace("Delivery Place");
		pncCare.setDeliveryPlaceID((short) 1);
		pncCare.setDeliveryType("Delivery Type");
		pncCare.setDeliveryTypeID((short) 1);
		pncCare.setGestationID((short) 1);
		pncCare.setGestationName("Gestation Name");
		pncCare.setID(1L);
		pncCare.setLastModDate(mock(Timestamp.class));
		pncCare.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncCare.setNewBornHealthStatus("New Born Health Status");
		pncCare.setNewBornHealthStatusID(1);
		pncCare.setOtherDeliveryComplication("Other Delivery Complication");
		pncCare.setOtherDeliveryPlace("Other Delivery Place");
		pncCare.setOtherPostNatalComplication("Other Post Natal Complication");
		pncCare.setParkingPlaceID(1);
		pncCare.setPostNatalComplication("Post Natal Complication");
		pncCare.setPostNatalComplicationID((short) 1);
		pncCare.setPregOutcome("Preg Outcome");
		pncCare.setPregOutcomeID((short) 1);
		pncCare.setProcessed("Processed");
		pncCare.setProviderServiceMapID(1);
		pncCare.setReservedForChange("Reserved For Change");
		pncCare.setSyncedBy("Synced By");
		pncCare.setSyncedDate(mock(Timestamp.class));
		pncCare.setVanID(1);
		pncCare.setVanSerialNo(1L);
		pncCare.setVehicalNo("Vehical No");
		pncCare.setVisitCode(1L);
		pncCare.setVisitNo((short) 1);
		pncCare.setdDate("2020-03-01");
		when(pNCCareRepo.save(Mockito.<PNCCare>any())).thenReturn(pncCare);

		PNCCare pncCareOBJ = new PNCCare();
		pncCareOBJ.setBenVisitID(1L);
		pncCareOBJ.setBeneficiaryRegID(1L);
		pncCareOBJ.setBirthWeightOfNewborn(10.0d);
		pncCareOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncCareOBJ.setCreatedDate(mock(Timestamp.class));
		pncCareOBJ.setDateOfDelivery(mock(Date.class));
		pncCareOBJ.setDeleted(true);
		pncCareOBJ.setDeliveryComplication("Delivery Complication");
		pncCareOBJ.setDeliveryComplicationID((short) 1);
		pncCareOBJ.setDeliveryConductedBy("Delivery Conducted By");
		pncCareOBJ.setDeliveryConductedByID(1);
		pncCareOBJ.setDeliveryPlace("Delivery Place");
		pncCareOBJ.setDeliveryPlaceID((short) 1);
		pncCareOBJ.setDeliveryType("Delivery Type");
		pncCareOBJ.setDeliveryTypeID((short) 1);
		pncCareOBJ.setGestationID((short) 1);
		pncCareOBJ.setGestationName("Gestation Name");
		pncCareOBJ.setID(1L);
		pncCareOBJ.setLastModDate(mock(Timestamp.class));
		pncCareOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncCareOBJ.setNewBornHealthStatus("New Born Health Status");
		pncCareOBJ.setNewBornHealthStatusID(1);
		pncCareOBJ.setOtherDeliveryComplication("Other Delivery Complication");
		pncCareOBJ.setOtherDeliveryPlace("Other Delivery Place");
		pncCareOBJ.setOtherPostNatalComplication("Other Post Natal Complication");
		pncCareOBJ.setParkingPlaceID(1);
		pncCareOBJ.setPostNatalComplication("Post Natal Complication");
		pncCareOBJ.setPostNatalComplicationID((short) 1);
		pncCareOBJ.setPregOutcome("Preg Outcome");
		pncCareOBJ.setPregOutcomeID((short) 1);
		pncCareOBJ.setProcessed("Processed");
		pncCareOBJ.setProviderServiceMapID(1);
		pncCareOBJ.setReservedForChange("Reserved For Change");
		pncCareOBJ.setSyncedBy("Synced By");
		pncCareOBJ.setSyncedDate(mock(Timestamp.class));
		pncCareOBJ.setVanID(1);
		pncCareOBJ.setVanSerialNo(1L);
		pncCareOBJ.setVehicalNo("Vehical No");
		pncCareOBJ.setVisitCode(1L);
		pncCareOBJ.setVisitNo((short) 1);
		pncCareOBJ.setdDate("2020-03-01");

		// Act
		Long actualSaveBenPncCareDetailsResult = pNCNurseServiceImpl.saveBenPncCareDetails(pncCareOBJ);

		// Assert
		verify(pNCCareRepo).save(isA(PNCCare.class));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals("2020-03-01", simpleDateFormat.format(pncCareOBJ.getDateOfDelivery()));
		assertEquals(1L, actualSaveBenPncCareDetailsResult.longValue());
	}

	@Test
	void testSaveBenPncCareDetails2() throws ParseException {
		// Arrange
		PNCCare pncCare = mock(PNCCare.class);
		when(pncCare.getID()).thenReturn(1L);
		doNothing().when(pncCare).setBenVisitID(Mockito.<Long>any());
		doNothing().when(pncCare).setBeneficiaryRegID(Mockito.<Long>any());
		doNothing().when(pncCare).setBirthWeightOfNewborn(Mockito.<Double>any());
		doNothing().when(pncCare).setCreatedBy(Mockito.<String>any());
		doNothing().when(pncCare).setCreatedDate(Mockito.<Timestamp>any());
		doNothing().when(pncCare).setDateOfDelivery(Mockito.<Date>any());
		doNothing().when(pncCare).setDeleted(Mockito.<Boolean>any());
		doNothing().when(pncCare).setDeliveryComplication(Mockito.<String>any());
		doNothing().when(pncCare).setDeliveryComplicationID(Mockito.<Short>any());
		doNothing().when(pncCare).setDeliveryConductedBy(Mockito.<String>any());
		doNothing().when(pncCare).setDeliveryConductedByID(Mockito.<Integer>any());
		doNothing().when(pncCare).setDeliveryPlace(Mockito.<String>any());
		doNothing().when(pncCare).setDeliveryPlaceID(Mockito.<Short>any());
		doNothing().when(pncCare).setDeliveryType(Mockito.<String>any());
		doNothing().when(pncCare).setDeliveryTypeID(Mockito.<Short>any());
		doNothing().when(pncCare).setGestationID(Mockito.<Short>any());
		doNothing().when(pncCare).setGestationName(Mockito.<String>any());
		doNothing().when(pncCare).setID(Mockito.<Long>any());
		doNothing().when(pncCare).setLastModDate(Mockito.<Timestamp>any());
		doNothing().when(pncCare).setModifiedBy(Mockito.<String>any());
		doNothing().when(pncCare).setNewBornHealthStatus(Mockito.<String>any());
		doNothing().when(pncCare).setNewBornHealthStatusID(Mockito.<Integer>any());
		doNothing().when(pncCare).setOtherDeliveryComplication(Mockito.<String>any());
		doNothing().when(pncCare).setOtherDeliveryPlace(Mockito.<String>any());
		doNothing().when(pncCare).setOtherPostNatalComplication(Mockito.<String>any());
		doNothing().when(pncCare).setParkingPlaceID(Mockito.<Integer>any());
		doNothing().when(pncCare).setPostNatalComplication(Mockito.<String>any());
		doNothing().when(pncCare).setPostNatalComplicationID(Mockito.<Short>any());
		doNothing().when(pncCare).setPregOutcome(Mockito.<String>any());
		doNothing().when(pncCare).setPregOutcomeID(Mockito.<Short>any());
		doNothing().when(pncCare).setProcessed(Mockito.<String>any());
		doNothing().when(pncCare).setProviderServiceMapID(Mockito.<Integer>any());
		doNothing().when(pncCare).setReservedForChange(Mockito.<String>any());
		doNothing().when(pncCare).setSyncedBy(Mockito.<String>any());
		doNothing().when(pncCare).setSyncedDate(Mockito.<Timestamp>any());
		doNothing().when(pncCare).setVanID(Mockito.<Integer>any());
		doNothing().when(pncCare).setVanSerialNo(Mockito.<Long>any());
		doNothing().when(pncCare).setVehicalNo(Mockito.<String>any());
		doNothing().when(pncCare).setVisitCode(Mockito.<Long>any());
		doNothing().when(pncCare).setVisitNo(Mockito.<Short>any());
		doNothing().when(pncCare).setdDate(Mockito.<String>any());
		pncCare.setBenVisitID(1L);
		pncCare.setBeneficiaryRegID(1L);
		pncCare.setBirthWeightOfNewborn(10.0d);
		pncCare.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncCare.setCreatedDate(mock(Timestamp.class));
		pncCare.setDateOfDelivery(mock(Date.class));
		pncCare.setDeleted(true);
		pncCare.setDeliveryComplication("Delivery Complication");
		pncCare.setDeliveryComplicationID((short) 1);
		pncCare.setDeliveryConductedBy("Delivery Conducted By");
		pncCare.setDeliveryConductedByID(1);
		pncCare.setDeliveryPlace("Delivery Place");
		pncCare.setDeliveryPlaceID((short) 1);
		pncCare.setDeliveryType("Delivery Type");
		pncCare.setDeliveryTypeID((short) 1);
		pncCare.setGestationID((short) 1);
		pncCare.setGestationName("Gestation Name");
		pncCare.setID(1L);
		pncCare.setLastModDate(mock(Timestamp.class));
		pncCare.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncCare.setNewBornHealthStatus("New Born Health Status");
		pncCare.setNewBornHealthStatusID(1);
		pncCare.setOtherDeliveryComplication("Other Delivery Complication");
		pncCare.setOtherDeliveryPlace("Other Delivery Place");
		pncCare.setOtherPostNatalComplication("Other Post Natal Complication");
		pncCare.setParkingPlaceID(1);
		pncCare.setPostNatalComplication("Post Natal Complication");
		pncCare.setPostNatalComplicationID((short) 1);
		pncCare.setPregOutcome("Preg Outcome");
		pncCare.setPregOutcomeID((short) 1);
		pncCare.setProcessed("Processed");
		pncCare.setProviderServiceMapID(1);
		pncCare.setReservedForChange("Reserved For Change");
		pncCare.setSyncedBy("Synced By");
		pncCare.setSyncedDate(mock(Timestamp.class));
		pncCare.setVanID(1);
		pncCare.setVanSerialNo(1L);
		pncCare.setVehicalNo("Vehical No");
		pncCare.setVisitCode(1L);
		pncCare.setVisitNo((short) 1);
		pncCare.setdDate("2020-03-01");
		when(pNCCareRepo.save(Mockito.<PNCCare>any())).thenReturn(pncCare);

		PNCCare pncCareOBJ = new PNCCare();
		pncCareOBJ.setBenVisitID(1L);
		pncCareOBJ.setBeneficiaryRegID(1L);
		pncCareOBJ.setBirthWeightOfNewborn(10.0d);
		pncCareOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncCareOBJ.setCreatedDate(mock(Timestamp.class));
		pncCareOBJ.setDateOfDelivery(mock(Date.class));
		pncCareOBJ.setDeleted(true);
		pncCareOBJ.setDeliveryComplication("Delivery Complication");
		pncCareOBJ.setDeliveryComplicationID((short) 1);
		pncCareOBJ.setDeliveryConductedBy("Delivery Conducted By");
		pncCareOBJ.setDeliveryConductedByID(1);
		pncCareOBJ.setDeliveryPlace("Delivery Place");
		pncCareOBJ.setDeliveryPlaceID((short) 1);
		pncCareOBJ.setDeliveryType("Delivery Type");
		pncCareOBJ.setDeliveryTypeID((short) 1);
		pncCareOBJ.setGestationID((short) 1);
		pncCareOBJ.setGestationName("Gestation Name");
		pncCareOBJ.setID(1L);
		pncCareOBJ.setLastModDate(mock(Timestamp.class));
		pncCareOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncCareOBJ.setNewBornHealthStatus("New Born Health Status");
		pncCareOBJ.setNewBornHealthStatusID(1);
		pncCareOBJ.setOtherDeliveryComplication("Other Delivery Complication");
		pncCareOBJ.setOtherDeliveryPlace("Other Delivery Place");
		pncCareOBJ.setOtherPostNatalComplication("Other Post Natal Complication");
		pncCareOBJ.setParkingPlaceID(1);
		pncCareOBJ.setPostNatalComplication("Post Natal Complication");
		pncCareOBJ.setPostNatalComplicationID((short) 1);
		pncCareOBJ.setPregOutcome("Preg Outcome");
		pncCareOBJ.setPregOutcomeID((short) 1);
		pncCareOBJ.setProcessed("Processed");
		pncCareOBJ.setProviderServiceMapID(1);
		pncCareOBJ.setReservedForChange("Reserved For Change");
		pncCareOBJ.setSyncedBy("Synced By");
		pncCareOBJ.setSyncedDate(mock(Timestamp.class));
		pncCareOBJ.setVanID(1);
		pncCareOBJ.setVanSerialNo(1L);
		pncCareOBJ.setVehicalNo("Vehical No");
		pncCareOBJ.setVisitCode(1L);
		pncCareOBJ.setVisitNo((short) 1);
		pncCareOBJ.setdDate("2020-03-01");

		// Act
		Long actualSaveBenPncCareDetailsResult = pNCNurseServiceImpl.saveBenPncCareDetails(pncCareOBJ);

		// Assert
		verify(pncCare, atLeast(1)).getID();
		verify(pncCare).setBenVisitID(isA(Long.class));
		verify(pncCare).setBeneficiaryRegID(isA(Long.class));
		verify(pncCare).setBirthWeightOfNewborn(isA(Double.class));
		verify(pncCare).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
		verify(pncCare).setCreatedDate(isA(Timestamp.class));
		verify(pncCare).setDateOfDelivery(isA(Date.class));
		verify(pncCare).setDeleted(isA(Boolean.class));
		verify(pncCare).setDeliveryComplication(eq("Delivery Complication"));
		verify(pncCare).setDeliveryComplicationID(isA(Short.class));
		verify(pncCare).setDeliveryConductedBy(eq("Delivery Conducted By"));
		verify(pncCare).setDeliveryConductedByID(isA(Integer.class));
		verify(pncCare).setDeliveryPlace(eq("Delivery Place"));
		verify(pncCare).setDeliveryPlaceID(isA(Short.class));
		verify(pncCare).setDeliveryType(eq("Delivery Type"));
		verify(pncCare).setDeliveryTypeID(isA(Short.class));
		verify(pncCare).setGestationID(isA(Short.class));
		verify(pncCare).setGestationName(eq("Gestation Name"));
		verify(pncCare).setID(isA(Long.class));
		verify(pncCare).setLastModDate(isA(Timestamp.class));
		verify(pncCare).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
		verify(pncCare).setNewBornHealthStatus(eq("New Born Health Status"));
		verify(pncCare).setNewBornHealthStatusID(isA(Integer.class));
		verify(pncCare).setOtherDeliveryComplication(eq("Other Delivery Complication"));
		verify(pncCare).setOtherDeliveryPlace(eq("Other Delivery Place"));
		verify(pncCare).setOtherPostNatalComplication(eq("Other Post Natal Complication"));
		verify(pncCare).setParkingPlaceID(isA(Integer.class));
		verify(pncCare).setPostNatalComplication(eq("Post Natal Complication"));
		verify(pncCare).setPostNatalComplicationID(isA(Short.class));
		verify(pncCare).setPregOutcome(eq("Preg Outcome"));
		verify(pncCare).setPregOutcomeID(isA(Short.class));
		verify(pncCare).setProcessed(eq("Processed"));
		verify(pncCare).setProviderServiceMapID(isA(Integer.class));
		verify(pncCare).setReservedForChange(eq("Reserved For Change"));
		verify(pncCare).setSyncedBy(eq("Synced By"));
		verify(pncCare).setSyncedDate(isA(Timestamp.class));
		verify(pncCare).setVanID(isA(Integer.class));
		verify(pncCare).setVanSerialNo(isA(Long.class));
		verify(pncCare).setVehicalNo(eq("Vehical No"));
		verify(pncCare).setVisitCode(isA(Long.class));
		verify(pncCare).setVisitNo(isA(Short.class));
		verify(pncCare).setdDate(eq("2020-03-01"));
		verify(pNCCareRepo).save(isA(PNCCare.class));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals("2020-03-01", simpleDateFormat.format(pncCareOBJ.getDateOfDelivery()));
		assertEquals(1L, actualSaveBenPncCareDetailsResult.longValue());
	}

	@Test
	void testSaveBenPncCareDetails3() throws ParseException {
		// Arrange
		PNCCare pncCare = mock(PNCCare.class);
		when(pncCare.getID()).thenReturn(0L);
		doNothing().when(pncCare).setBenVisitID(Mockito.<Long>any());
		doNothing().when(pncCare).setBeneficiaryRegID(Mockito.<Long>any());
		doNothing().when(pncCare).setBirthWeightOfNewborn(Mockito.<Double>any());
		doNothing().when(pncCare).setCreatedBy(Mockito.<String>any());
		doNothing().when(pncCare).setCreatedDate(Mockito.<Timestamp>any());
		doNothing().when(pncCare).setDateOfDelivery(Mockito.<Date>any());
		doNothing().when(pncCare).setDeleted(Mockito.<Boolean>any());
		doNothing().when(pncCare).setDeliveryComplication(Mockito.<String>any());
		doNothing().when(pncCare).setDeliveryComplicationID(Mockito.<Short>any());
		doNothing().when(pncCare).setDeliveryConductedBy(Mockito.<String>any());
		doNothing().when(pncCare).setDeliveryConductedByID(Mockito.<Integer>any());
		doNothing().when(pncCare).setDeliveryPlace(Mockito.<String>any());
		doNothing().when(pncCare).setDeliveryPlaceID(Mockito.<Short>any());
		doNothing().when(pncCare).setDeliveryType(Mockito.<String>any());
		doNothing().when(pncCare).setDeliveryTypeID(Mockito.<Short>any());
		doNothing().when(pncCare).setGestationID(Mockito.<Short>any());
		doNothing().when(pncCare).setGestationName(Mockito.<String>any());
		doNothing().when(pncCare).setID(Mockito.<Long>any());
		doNothing().when(pncCare).setLastModDate(Mockito.<Timestamp>any());
		doNothing().when(pncCare).setModifiedBy(Mockito.<String>any());
		doNothing().when(pncCare).setNewBornHealthStatus(Mockito.<String>any());
		doNothing().when(pncCare).setNewBornHealthStatusID(Mockito.<Integer>any());
		doNothing().when(pncCare).setOtherDeliveryComplication(Mockito.<String>any());
		doNothing().when(pncCare).setOtherDeliveryPlace(Mockito.<String>any());
		doNothing().when(pncCare).setOtherPostNatalComplication(Mockito.<String>any());
		doNothing().when(pncCare).setParkingPlaceID(Mockito.<Integer>any());
		doNothing().when(pncCare).setPostNatalComplication(Mockito.<String>any());
		doNothing().when(pncCare).setPostNatalComplicationID(Mockito.<Short>any());
		doNothing().when(pncCare).setPregOutcome(Mockito.<String>any());
		doNothing().when(pncCare).setPregOutcomeID(Mockito.<Short>any());
		doNothing().when(pncCare).setProcessed(Mockito.<String>any());
		doNothing().when(pncCare).setProviderServiceMapID(Mockito.<Integer>any());
		doNothing().when(pncCare).setReservedForChange(Mockito.<String>any());
		doNothing().when(pncCare).setSyncedBy(Mockito.<String>any());
		doNothing().when(pncCare).setSyncedDate(Mockito.<Timestamp>any());
		doNothing().when(pncCare).setVanID(Mockito.<Integer>any());
		doNothing().when(pncCare).setVanSerialNo(Mockito.<Long>any());
		doNothing().when(pncCare).setVehicalNo(Mockito.<String>any());
		doNothing().when(pncCare).setVisitCode(Mockito.<Long>any());
		doNothing().when(pncCare).setVisitNo(Mockito.<Short>any());
		doNothing().when(pncCare).setdDate(Mockito.<String>any());
		pncCare.setBenVisitID(1L);
		pncCare.setBeneficiaryRegID(1L);
		pncCare.setBirthWeightOfNewborn(10.0d);
		pncCare.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncCare.setCreatedDate(mock(Timestamp.class));
		pncCare.setDateOfDelivery(mock(Date.class));
		pncCare.setDeleted(true);
		pncCare.setDeliveryComplication("Delivery Complication");
		pncCare.setDeliveryComplicationID((short) 1);
		pncCare.setDeliveryConductedBy("Delivery Conducted By");
		pncCare.setDeliveryConductedByID(1);
		pncCare.setDeliveryPlace("Delivery Place");
		pncCare.setDeliveryPlaceID((short) 1);
		pncCare.setDeliveryType("Delivery Type");
		pncCare.setDeliveryTypeID((short) 1);
		pncCare.setGestationID((short) 1);
		pncCare.setGestationName("Gestation Name");
		pncCare.setID(1L);
		pncCare.setLastModDate(mock(Timestamp.class));
		pncCare.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncCare.setNewBornHealthStatus("New Born Health Status");
		pncCare.setNewBornHealthStatusID(1);
		pncCare.setOtherDeliveryComplication("Other Delivery Complication");
		pncCare.setOtherDeliveryPlace("Other Delivery Place");
		pncCare.setOtherPostNatalComplication("Other Post Natal Complication");
		pncCare.setParkingPlaceID(1);
		pncCare.setPostNatalComplication("Post Natal Complication");
		pncCare.setPostNatalComplicationID((short) 1);
		pncCare.setPregOutcome("Preg Outcome");
		pncCare.setPregOutcomeID((short) 1);
		pncCare.setProcessed("Processed");
		pncCare.setProviderServiceMapID(1);
		pncCare.setReservedForChange("Reserved For Change");
		pncCare.setSyncedBy("Synced By");
		pncCare.setSyncedDate(mock(Timestamp.class));
		pncCare.setVanID(1);
		pncCare.setVanSerialNo(1L);
		pncCare.setVehicalNo("Vehical No");
		pncCare.setVisitCode(1L);
		pncCare.setVisitNo((short) 1);
		pncCare.setdDate("2020-03-01");
		when(pNCCareRepo.save(Mockito.<PNCCare>any())).thenReturn(pncCare);

		PNCCare pncCareOBJ = new PNCCare();
		pncCareOBJ.setBenVisitID(1L);
		pncCareOBJ.setBeneficiaryRegID(1L);
		pncCareOBJ.setBirthWeightOfNewborn(10.0d);
		pncCareOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncCareOBJ.setCreatedDate(mock(Timestamp.class));
		pncCareOBJ.setDateOfDelivery(mock(Date.class));
		pncCareOBJ.setDeleted(true);
		pncCareOBJ.setDeliveryComplication("Delivery Complication");
		pncCareOBJ.setDeliveryComplicationID((short) 1);
		pncCareOBJ.setDeliveryConductedBy("Delivery Conducted By");
		pncCareOBJ.setDeliveryConductedByID(1);
		pncCareOBJ.setDeliveryPlace("Delivery Place");
		pncCareOBJ.setDeliveryPlaceID((short) 1);
		pncCareOBJ.setDeliveryType("Delivery Type");
		pncCareOBJ.setDeliveryTypeID((short) 1);
		pncCareOBJ.setGestationID((short) 1);
		pncCareOBJ.setGestationName("Gestation Name");
		pncCareOBJ.setID(1L);
		pncCareOBJ.setLastModDate(mock(Timestamp.class));
		pncCareOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncCareOBJ.setNewBornHealthStatus("New Born Health Status");
		pncCareOBJ.setNewBornHealthStatusID(1);
		pncCareOBJ.setOtherDeliveryComplication("Other Delivery Complication");
		pncCareOBJ.setOtherDeliveryPlace("Other Delivery Place");
		pncCareOBJ.setOtherPostNatalComplication("Other Post Natal Complication");
		pncCareOBJ.setParkingPlaceID(1);
		pncCareOBJ.setPostNatalComplication("Post Natal Complication");
		pncCareOBJ.setPostNatalComplicationID((short) 1);
		pncCareOBJ.setPregOutcome("Preg Outcome");
		pncCareOBJ.setPregOutcomeID((short) 1);
		pncCareOBJ.setProcessed("Processed");
		pncCareOBJ.setProviderServiceMapID(1);
		pncCareOBJ.setReservedForChange("Reserved For Change");
		pncCareOBJ.setSyncedBy("Synced By");
		pncCareOBJ.setSyncedDate(mock(Timestamp.class));
		pncCareOBJ.setVanID(1);
		pncCareOBJ.setVanSerialNo(1L);
		pncCareOBJ.setVehicalNo("Vehical No");
		pncCareOBJ.setVisitCode(1L);
		pncCareOBJ.setVisitNo((short) 1);
		pncCareOBJ.setdDate("2020-03-01");

		// Act
		Long actualSaveBenPncCareDetailsResult = pNCNurseServiceImpl.saveBenPncCareDetails(pncCareOBJ);

		// Assert
		verify(pncCare).getID();
		verify(pncCare).setBenVisitID(isA(Long.class));
		verify(pncCare).setBeneficiaryRegID(isA(Long.class));
		verify(pncCare).setBirthWeightOfNewborn(isA(Double.class));
		verify(pncCare).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
		verify(pncCare).setCreatedDate(isA(Timestamp.class));
		verify(pncCare).setDateOfDelivery(isA(Date.class));
		verify(pncCare).setDeleted(isA(Boolean.class));
		verify(pncCare).setDeliveryComplication(eq("Delivery Complication"));
		verify(pncCare).setDeliveryComplicationID(isA(Short.class));
		verify(pncCare).setDeliveryConductedBy(eq("Delivery Conducted By"));
		verify(pncCare).setDeliveryConductedByID(isA(Integer.class));
		verify(pncCare).setDeliveryPlace(eq("Delivery Place"));
		verify(pncCare).setDeliveryPlaceID(isA(Short.class));
		verify(pncCare).setDeliveryType(eq("Delivery Type"));
		verify(pncCare).setDeliveryTypeID(isA(Short.class));
		verify(pncCare).setGestationID(isA(Short.class));
		verify(pncCare).setGestationName(eq("Gestation Name"));
		verify(pncCare).setID(isA(Long.class));
		verify(pncCare).setLastModDate(isA(Timestamp.class));
		verify(pncCare).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
		verify(pncCare).setNewBornHealthStatus(eq("New Born Health Status"));
		verify(pncCare).setNewBornHealthStatusID(isA(Integer.class));
		verify(pncCare).setOtherDeliveryComplication(eq("Other Delivery Complication"));
		verify(pncCare).setOtherDeliveryPlace(eq("Other Delivery Place"));
		verify(pncCare).setOtherPostNatalComplication(eq("Other Post Natal Complication"));
		verify(pncCare).setParkingPlaceID(isA(Integer.class));
		verify(pncCare).setPostNatalComplication(eq("Post Natal Complication"));
		verify(pncCare).setPostNatalComplicationID(isA(Short.class));
		verify(pncCare).setPregOutcome(eq("Preg Outcome"));
		verify(pncCare).setPregOutcomeID(isA(Short.class));
		verify(pncCare).setProcessed(eq("Processed"));
		verify(pncCare).setProviderServiceMapID(isA(Integer.class));
		verify(pncCare).setReservedForChange(eq("Reserved For Change"));
		verify(pncCare).setSyncedBy(eq("Synced By"));
		verify(pncCare).setSyncedDate(isA(Timestamp.class));
		verify(pncCare).setVanID(isA(Integer.class));
		verify(pncCare).setVanSerialNo(isA(Long.class));
		verify(pncCare).setVehicalNo(eq("Vehical No"));
		verify(pncCare).setVisitCode(isA(Long.class));
		verify(pncCare).setVisitNo(isA(Short.class));
		verify(pncCare).setdDate(eq("2020-03-01"));
		verify(pNCCareRepo).save(isA(PNCCare.class));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals("2020-03-01", simpleDateFormat.format(pncCareOBJ.getDateOfDelivery()));
		assertNull(actualSaveBenPncCareDetailsResult);
	}

	@Test
	void testGetPNCCareDetails() {
		Date dateOfDelivery = mock(Date.class);
		when(dateOfDelivery.getTime()).thenReturn(10L);

		PNCCare pncCare = new PNCCare();
		pncCare.setBenVisitID(1L);
		pncCare.setBeneficiaryRegID(1L);
		pncCare.setBirthWeightOfNewborn(10.0d);
		pncCare.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncCare.setCreatedDate(mock(Timestamp.class));
		pncCare.setDateOfDelivery(dateOfDelivery);
		pncCare.setDeleted(true);
		pncCare.setDeliveryComplication("Delivery Complication");
		pncCare.setDeliveryComplicationID((short) 1);
		pncCare.setDeliveryConductedBy("Delivery Conducted By");
		pncCare.setDeliveryConductedByID(1);
		pncCare.setDeliveryPlace("Delivery Place");
		pncCare.setDeliveryPlaceID((short) 1);
		pncCare.setDeliveryType("Delivery Type");
		pncCare.setDeliveryTypeID((short) 1);
		pncCare.setGestationID((short) 1);
		pncCare.setGestationName("Gestation Name");
		pncCare.setID(1L);
		pncCare.setLastModDate(mock(Timestamp.class));
		pncCare.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncCare.setNewBornHealthStatus("New Born Health Status");
		pncCare.setNewBornHealthStatusID(1);
		pncCare.setOtherDeliveryComplication("Other Delivery Complication");
		pncCare.setOtherDeliveryPlace("Other Delivery Place");
		pncCare.setOtherPostNatalComplication("Other Post Natal Complication");
		pncCare.setParkingPlaceID(1);
		pncCare.setPostNatalComplication("Post Natal Complication");
		pncCare.setPostNatalComplicationID((short) 1);
		pncCare.setPregOutcome("Preg Outcome");
		pncCare.setPregOutcomeID((short) 1);
		pncCare.setProcessed("Processed");
		pncCare.setProviderServiceMapID(1);
		pncCare.setReservedForChange("Reserved For Change");
		pncCare.setSyncedBy("Synced By");
		pncCare.setSyncedDate(mock(Timestamp.class));
		pncCare.setVanID(1);
		pncCare.setVanSerialNo(1L);
		pncCare.setVehicalNo("Vehical No");
		pncCare.setVisitCode(1L);
		pncCare.setVisitNo((short) 1);
		pncCare.setdDate("2020-03-01");
		when(pNCCareRepo.getPNCCareDetailsByVisitCode(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(pncCare);

		// Act
		pNCNurseServiceImpl.getPNCCareDetails(1L, 1L);
	}

	@Test
	void testUpdateBenPNCCareDetails() throws ParseException {
		// Arrange
		when(pNCCareRepo.updatePNCCareDetails(Mockito.<Short>any(), Mockito.<String>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Date>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(), Mockito.<String>any(),
				Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<Double>any(), Mockito.<Integer>any(), Mockito.<String>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
				Mockito.<Integer>any(), Mockito.<String>any())).thenReturn(1);
		when(pNCCareRepo.getBenPNCCareDetailsStatus(Mockito.<Long>any(), Mockito.<Long>any()))
				.thenReturn("Ben PNCCare Details Status");

		PNCCare pncCareDetailsOBJ = new PNCCare();
		pncCareDetailsOBJ.setBenVisitID(1L);
		pncCareDetailsOBJ.setBeneficiaryRegID(1L);
		pncCareDetailsOBJ.setBirthWeightOfNewborn(10.0d);
		pncCareDetailsOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncCareDetailsOBJ.setCreatedDate(mock(Timestamp.class));
		pncCareDetailsOBJ.setDateOfDelivery(mock(Date.class));
		pncCareDetailsOBJ.setDeleted(true);
		pncCareDetailsOBJ.setDeliveryComplication("Delivery Complication");
		pncCareDetailsOBJ.setDeliveryComplicationID((short) 1);
		pncCareDetailsOBJ.setDeliveryConductedBy("Delivery Conducted By");
		pncCareDetailsOBJ.setDeliveryConductedByID(1);
		pncCareDetailsOBJ.setDeliveryPlace("Delivery Place");
		pncCareDetailsOBJ.setDeliveryPlaceID((short) 1);
		pncCareDetailsOBJ.setDeliveryType("Delivery Type");
		pncCareDetailsOBJ.setDeliveryTypeID((short) 1);
		pncCareDetailsOBJ.setGestationID((short) 1);
		pncCareDetailsOBJ.setGestationName("Gestation Name");
		pncCareDetailsOBJ.setID(1L);
		pncCareDetailsOBJ.setLastModDate(mock(Timestamp.class));
		pncCareDetailsOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncCareDetailsOBJ.setNewBornHealthStatus("New Born Health Status");
		pncCareDetailsOBJ.setNewBornHealthStatusID(1);
		pncCareDetailsOBJ.setOtherDeliveryComplication("Other Delivery Complication");
		pncCareDetailsOBJ.setOtherDeliveryPlace("Other Delivery Place");
		pncCareDetailsOBJ.setOtherPostNatalComplication("Other Post Natal Complication");
		pncCareDetailsOBJ.setParkingPlaceID(1);
		pncCareDetailsOBJ.setPostNatalComplication("Post Natal Complication");
		pncCareDetailsOBJ.setPostNatalComplicationID((short) 1);
		pncCareDetailsOBJ.setPregOutcome("Preg Outcome");
		pncCareDetailsOBJ.setPregOutcomeID((short) 1);
		pncCareDetailsOBJ.setProcessed("Processed");
		pncCareDetailsOBJ.setProviderServiceMapID(1);
		pncCareDetailsOBJ.setReservedForChange("Reserved For Change");
		pncCareDetailsOBJ.setSyncedBy("Synced By");
		pncCareDetailsOBJ.setSyncedDate(mock(Timestamp.class));
		pncCareDetailsOBJ.setVanID(1);
		pncCareDetailsOBJ.setVanSerialNo(1L);
		pncCareDetailsOBJ.setVehicalNo("Vehical No");
		pncCareDetailsOBJ.setVisitCode(1L);
		pncCareDetailsOBJ.setVisitNo((short) 1);
		pncCareDetailsOBJ.setdDate("2020-03-01");

		// Act
		int actualUpdateBenPNCCareDetailsResult = pNCNurseServiceImpl.updateBenPNCCareDetails(pncCareDetailsOBJ);

		// Assert
		verify(pNCCareRepo).getBenPNCCareDetailsStatus(isA(Long.class), isA(Long.class));
		verify(pNCCareRepo).updatePNCCareDetails(isA(Short.class), eq("Delivery Type"), isA(Short.class),
				eq("Delivery Place"), eq("Other Delivery Place"), isA(Date.class), isA(Short.class),
				eq("Delivery Complication"), eq("Other Delivery Complication"), isA(Short.class), eq("Preg Outcome"),
				isA(Short.class), eq("Post Natal Complication"), eq("Other Post Natal Complication"), isA(Short.class),
				eq("Gestation Name"), isA(Double.class), isA(Integer.class), eq("New Born Health Status"),
				eq("Jan 1, 2020 9:00am GMT+0100"), eq("U"), isA(Long.class), isA(Long.class), isA(Integer.class),
				eq("Delivery Conducted By"));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals("2020-03-01", simpleDateFormat.format(pncCareDetailsOBJ.getDateOfDelivery()));
		assertEquals("U", pncCareDetailsOBJ.getProcessed());
		assertEquals(1, actualUpdateBenPNCCareDetailsResult);
	}

	@Test
	void testUpdateBenPNCCare() throws ParseException {
		// Arrange
		when(pNCCareRepo.updatePNCCareDetails(Mockito.<Short>any(), Mockito.<String>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Date>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(), Mockito.<String>any(),
				Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<Double>any(), Mockito.<Integer>any(), Mockito.<String>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
				Mockito.<Integer>any(), Mockito.<String>any())).thenReturn(1);

		PNCCare pncCareOBJ = new PNCCare();
		pncCareOBJ.setBenVisitID(1L);
		pncCareOBJ.setBeneficiaryRegID(1L);
		pncCareOBJ.setBirthWeightOfNewborn(10.0d);
		pncCareOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncCareOBJ.setCreatedDate(mock(Timestamp.class));
		pncCareOBJ.setDateOfDelivery(mock(Date.class));
		pncCareOBJ.setDeleted(true);
		pncCareOBJ.setDeliveryComplication("Delivery Complication");
		pncCareOBJ.setDeliveryComplicationID((short) 1);
		pncCareOBJ.setDeliveryConductedBy("Delivery Conducted By");
		pncCareOBJ.setDeliveryConductedByID(1);
		pncCareOBJ.setDeliveryPlace("Delivery Place");
		pncCareOBJ.setDeliveryPlaceID((short) 1);
		pncCareOBJ.setDeliveryType("Delivery Type");
		pncCareOBJ.setDeliveryTypeID((short) 1);
		pncCareOBJ.setGestationID((short) 1);
		pncCareOBJ.setGestationName("Gestation Name");
		pncCareOBJ.setID(1L);
		pncCareOBJ.setLastModDate(mock(Timestamp.class));
		pncCareOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncCareOBJ.setNewBornHealthStatus("New Born Health Status");
		pncCareOBJ.setNewBornHealthStatusID(1);
		pncCareOBJ.setOtherDeliveryComplication("Other Delivery Complication");
		pncCareOBJ.setOtherDeliveryPlace("Other Delivery Place");
		pncCareOBJ.setOtherPostNatalComplication("Other Post Natal Complication");
		pncCareOBJ.setParkingPlaceID(1);
		pncCareOBJ.setPostNatalComplication("Post Natal Complication");
		pncCareOBJ.setPostNatalComplicationID((short) 1);
		pncCareOBJ.setPregOutcome("Preg Outcome");
		pncCareOBJ.setPregOutcomeID((short) 1);
		pncCareOBJ.setProcessed("Processed");
		pncCareOBJ.setProviderServiceMapID(1);
		pncCareOBJ.setReservedForChange("Reserved For Change");
		pncCareOBJ.setSyncedBy("Synced By");
		pncCareOBJ.setSyncedDate(mock(Timestamp.class));
		pncCareOBJ.setVanID(1);
		pncCareOBJ.setVanSerialNo(1L);
		pncCareOBJ.setVehicalNo("Vehical No");
		pncCareOBJ.setVisitCode(1L);
		pncCareOBJ.setVisitNo((short) 1);
		pncCareOBJ.setdDate("2020-03-01");

		// Act
		int actualUpdateBenPNCCareResult = pNCNurseServiceImpl.updateBenPNCCare(pncCareOBJ);

		// Assert
		verify(pNCCareRepo).updatePNCCareDetails(isA(Short.class), eq("Delivery Type"), isA(Short.class),
				eq("Delivery Place"), eq("Other Delivery Place"), isA(Date.class), isA(Short.class),
				eq("Delivery Complication"), eq("Other Delivery Complication"), isA(Short.class), eq("Preg Outcome"),
				isA(Short.class), eq("Post Natal Complication"), eq("Other Post Natal Complication"), isA(Short.class),
				eq("Gestation Name"), isA(Double.class), isA(Integer.class), eq("New Born Health Status"),
				eq("Jan 1, 2020 9:00am GMT+0100"), eq("Processed"), isA(Long.class), isA(Long.class),
				isA(Integer.class), eq("Delivery Conducted By"));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals("2020-03-01", simpleDateFormat.format(pncCareOBJ.getDateOfDelivery()));
		assertEquals(1, actualUpdateBenPNCCareResult);
	}

	@Test
	void testUpdateBenPNCCare2() throws ParseException {
		// Arrange
		when(pNCCareRepo.updatePNCCareDetails(Mockito.<Short>any(), Mockito.<String>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Date>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(), Mockito.<String>any(),
				Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<Double>any(), Mockito.<Integer>any(), Mockito.<String>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
				Mockito.<Integer>any(), Mockito.<String>any())).thenReturn(1);
		PNCCare pncCareOBJ = mock(PNCCare.class);
		when(pncCareOBJ.getBirthWeightOfNewborn()).thenReturn(10.0d);
		when(pncCareOBJ.getDeliveryConductedByID()).thenReturn(1);
		when(pncCareOBJ.getNewBornHealthStatusID()).thenReturn(1);
		when(pncCareOBJ.getBeneficiaryRegID()).thenReturn(1L);
		when(pncCareOBJ.getVisitCode()).thenReturn(1L);
		when(pncCareOBJ.getDeliveryComplicationID()).thenReturn((short) 1);
		when(pncCareOBJ.getDeliveryPlaceID()).thenReturn((short) 1);
		when(pncCareOBJ.getDeliveryTypeID()).thenReturn((short) 1);
		when(pncCareOBJ.getGestationID()).thenReturn((short) 1);
		when(pncCareOBJ.getPostNatalComplicationID()).thenReturn((short) 1);
		when(pncCareOBJ.getPregOutcomeID()).thenReturn((short) 1);
		when(pncCareOBJ.getDeliveryComplication()).thenReturn("Delivery Complication");
		when(pncCareOBJ.getDeliveryConductedBy()).thenReturn("Delivery Conducted By");
		when(pncCareOBJ.getDeliveryPlace()).thenReturn("Delivery Place");
		when(pncCareOBJ.getDeliveryType()).thenReturn("Delivery Type");
		when(pncCareOBJ.getGestationName()).thenReturn("Gestation Name");
		when(pncCareOBJ.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
		when(pncCareOBJ.getNewBornHealthStatus()).thenReturn("New Born Health Status");
		when(pncCareOBJ.getOtherDeliveryComplication()).thenReturn("Other Delivery Complication");
		when(pncCareOBJ.getOtherDeliveryPlace()).thenReturn("Other Delivery Place");
		when(pncCareOBJ.getOtherPostNatalComplication()).thenReturn("Other Post Natal Complication");
		when(pncCareOBJ.getPostNatalComplication()).thenReturn("Post Natal Complication");
		when(pncCareOBJ.getPregOutcome()).thenReturn("Preg Outcome");
		when(pncCareOBJ.getProcessed()).thenReturn("Processed");
		when(pncCareOBJ.getdDate()).thenReturn("2020-03-01");
		when(pncCareOBJ.getDateOfDelivery()).thenReturn(mock(Date.class));
		doNothing().when(pncCareOBJ).setBenVisitID(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setBeneficiaryRegID(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setBirthWeightOfNewborn(Mockito.<Double>any());
		doNothing().when(pncCareOBJ).setCreatedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setCreatedDate(Mockito.<Timestamp>any());
		doNothing().when(pncCareOBJ).setDateOfDelivery(Mockito.<Date>any());
		doNothing().when(pncCareOBJ).setDeleted(Mockito.<Boolean>any());
		doNothing().when(pncCareOBJ).setDeliveryComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryComplicationID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setDeliveryConductedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryConductedByID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setDeliveryPlace(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryPlaceID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setDeliveryType(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryTypeID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setGestationID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setGestationName(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setID(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setLastModDate(Mockito.<Timestamp>any());
		doNothing().when(pncCareOBJ).setModifiedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setNewBornHealthStatus(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setNewBornHealthStatusID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setOtherDeliveryComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setOtherDeliveryPlace(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setOtherPostNatalComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setParkingPlaceID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setPostNatalComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setPostNatalComplicationID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setPregOutcome(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setPregOutcomeID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setProcessed(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setProviderServiceMapID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setReservedForChange(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setSyncedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setSyncedDate(Mockito.<Timestamp>any());
		doNothing().when(pncCareOBJ).setVanID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setVanSerialNo(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setVehicalNo(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setVisitCode(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setVisitNo(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setdDate(Mockito.<String>any());
		pncCareOBJ.setBenVisitID(1L);
		pncCareOBJ.setBeneficiaryRegID(1L);
		pncCareOBJ.setBirthWeightOfNewborn(10.0d);
		pncCareOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncCareOBJ.setCreatedDate(mock(Timestamp.class));
		pncCareOBJ.setDateOfDelivery(mock(Date.class));
		pncCareOBJ.setDeleted(true);
		pncCareOBJ.setDeliveryComplication("Delivery Complication");
		pncCareOBJ.setDeliveryComplicationID((short) 1);
		pncCareOBJ.setDeliveryConductedBy("Delivery Conducted By");
		pncCareOBJ.setDeliveryConductedByID(1);
		pncCareOBJ.setDeliveryPlace("Delivery Place");
		pncCareOBJ.setDeliveryPlaceID((short) 1);
		pncCareOBJ.setDeliveryType("Delivery Type");
		pncCareOBJ.setDeliveryTypeID((short) 1);
		pncCareOBJ.setGestationID((short) 1);
		pncCareOBJ.setGestationName("Gestation Name");
		pncCareOBJ.setID(1L);
		pncCareOBJ.setLastModDate(mock(Timestamp.class));
		pncCareOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncCareOBJ.setNewBornHealthStatus("New Born Health Status");
		pncCareOBJ.setNewBornHealthStatusID(1);
		pncCareOBJ.setOtherDeliveryComplication("Other Delivery Complication");
		pncCareOBJ.setOtherDeliveryPlace("Other Delivery Place");
		pncCareOBJ.setOtherPostNatalComplication("Other Post Natal Complication");
		pncCareOBJ.setParkingPlaceID(1);
		pncCareOBJ.setPostNatalComplication("Post Natal Complication");
		pncCareOBJ.setPostNatalComplicationID((short) 1);
		pncCareOBJ.setPregOutcome("Preg Outcome");
		pncCareOBJ.setPregOutcomeID((short) 1);
		pncCareOBJ.setProcessed("Processed");
		pncCareOBJ.setProviderServiceMapID(1);
		pncCareOBJ.setReservedForChange("Reserved For Change");
		pncCareOBJ.setSyncedBy("Synced By");
		pncCareOBJ.setSyncedDate(mock(Timestamp.class));
		pncCareOBJ.setVanID(1);
		pncCareOBJ.setVanSerialNo(1L);
		pncCareOBJ.setVehicalNo("Vehical No");
		pncCareOBJ.setVisitCode(1L);
		pncCareOBJ.setVisitNo((short) 1);
		pncCareOBJ.setdDate("2020-03-01");

		// Act
		int actualUpdateBenPNCCareResult = pNCNurseServiceImpl.updateBenPNCCare(pncCareOBJ);

		// Assert
		verify(pncCareOBJ).getBeneficiaryRegID();
		verify(pncCareOBJ).getBirthWeightOfNewborn();
		verify(pncCareOBJ).getDateOfDelivery();
		verify(pncCareOBJ).getDeliveryComplication();
		verify(pncCareOBJ).getDeliveryComplicationID();
		verify(pncCareOBJ).getDeliveryConductedBy();
		verify(pncCareOBJ).getDeliveryConductedByID();
		verify(pncCareOBJ).getDeliveryPlace();
		verify(pncCareOBJ).getDeliveryPlaceID();
		verify(pncCareOBJ).getDeliveryType();
		verify(pncCareOBJ).getDeliveryTypeID();
		verify(pncCareOBJ).getGestationID();
		verify(pncCareOBJ).getGestationName();
		verify(pncCareOBJ).getModifiedBy();
		verify(pncCareOBJ).getNewBornHealthStatus();
		verify(pncCareOBJ).getNewBornHealthStatusID();
		verify(pncCareOBJ).getOtherDeliveryComplication();
		verify(pncCareOBJ).getOtherDeliveryPlace();
		verify(pncCareOBJ).getOtherPostNatalComplication();
		verify(pncCareOBJ).getPostNatalComplication();
		verify(pncCareOBJ).getPostNatalComplicationID();
		verify(pncCareOBJ).getPregOutcome();
		verify(pncCareOBJ).getPregOutcomeID();
		verify(pncCareOBJ).getProcessed();
		verify(pncCareOBJ).getVisitCode();
		verify(pncCareOBJ, atLeast(1)).getdDate();
		verify(pncCareOBJ).setBenVisitID(isA(Long.class));
		verify(pncCareOBJ).setBeneficiaryRegID(isA(Long.class));
		verify(pncCareOBJ).setBirthWeightOfNewborn(isA(Double.class));
		verify(pncCareOBJ).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
		verify(pncCareOBJ).setCreatedDate(isA(Timestamp.class));
		verify(pncCareOBJ, atLeast(1)).setDateOfDelivery(Mockito.<Date>any());
		verify(pncCareOBJ).setDeleted(isA(Boolean.class));
		verify(pncCareOBJ).setDeliveryComplication(eq("Delivery Complication"));
		verify(pncCareOBJ).setDeliveryComplicationID(isA(Short.class));
		verify(pncCareOBJ).setDeliveryConductedBy(eq("Delivery Conducted By"));
		verify(pncCareOBJ).setDeliveryConductedByID(isA(Integer.class));
		verify(pncCareOBJ).setDeliveryPlace(eq("Delivery Place"));
		verify(pncCareOBJ).setDeliveryPlaceID(isA(Short.class));
		verify(pncCareOBJ).setDeliveryType(eq("Delivery Type"));
		verify(pncCareOBJ).setDeliveryTypeID(isA(Short.class));
		verify(pncCareOBJ).setGestationID(isA(Short.class));
		verify(pncCareOBJ).setGestationName(eq("Gestation Name"));
		verify(pncCareOBJ).setID(isA(Long.class));
		verify(pncCareOBJ).setLastModDate(isA(Timestamp.class));
		verify(pncCareOBJ).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
		verify(pncCareOBJ).setNewBornHealthStatus(eq("New Born Health Status"));
		verify(pncCareOBJ).setNewBornHealthStatusID(isA(Integer.class));
		verify(pncCareOBJ).setOtherDeliveryComplication(eq("Other Delivery Complication"));
		verify(pncCareOBJ).setOtherDeliveryPlace(eq("Other Delivery Place"));
		verify(pncCareOBJ).setOtherPostNatalComplication(eq("Other Post Natal Complication"));
		verify(pncCareOBJ).setParkingPlaceID(isA(Integer.class));
		verify(pncCareOBJ).setPostNatalComplication(eq("Post Natal Complication"));
		verify(pncCareOBJ).setPostNatalComplicationID(isA(Short.class));
		verify(pncCareOBJ).setPregOutcome(eq("Preg Outcome"));
		verify(pncCareOBJ).setPregOutcomeID(isA(Short.class));
		verify(pncCareOBJ).setProcessed(eq("Processed"));
		verify(pncCareOBJ).setProviderServiceMapID(isA(Integer.class));
		verify(pncCareOBJ).setReservedForChange(eq("Reserved For Change"));
		verify(pncCareOBJ).setSyncedBy(eq("Synced By"));
		verify(pncCareOBJ).setSyncedDate(isA(Timestamp.class));
		verify(pncCareOBJ).setVanID(isA(Integer.class));
		verify(pncCareOBJ).setVanSerialNo(isA(Long.class));
		verify(pncCareOBJ).setVehicalNo(eq("Vehical No"));
		verify(pncCareOBJ).setVisitCode(isA(Long.class));
		verify(pncCareOBJ).setVisitNo(isA(Short.class));
		verify(pncCareOBJ).setdDate(eq("2020-03-01"));
		verify(pNCCareRepo).updatePNCCareDetails(isA(Short.class), eq("Delivery Type"), isA(Short.class),
				eq("Delivery Place"), eq("Other Delivery Place"), isA(Date.class), isA(Short.class),
				eq("Delivery Complication"), eq("Other Delivery Complication"), isA(Short.class), eq("Preg Outcome"),
				isA(Short.class), eq("Post Natal Complication"), eq("Other Post Natal Complication"), isA(Short.class),
				eq("Gestation Name"), isA(Double.class), isA(Integer.class), eq("New Born Health Status"),
				eq("Jan 1, 2020 9:00am GMT+0100"), eq("Processed"), isA(Long.class), isA(Long.class),
				isA(Integer.class), eq("Delivery Conducted By"));
		assertEquals(1, actualUpdateBenPNCCareResult);
	}

	@Test
	void testUpdateBenPNCCare3() throws ParseException {
		// Arrange
		when(pNCCareRepo.updatePNCCareDetails(Mockito.<Short>any(), Mockito.<String>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Date>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(), Mockito.<String>any(),
				Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<Double>any(), Mockito.<Integer>any(), Mockito.<String>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
				Mockito.<Integer>any(), Mockito.<String>any())).thenReturn(1);
		PNCCare pncCareOBJ = mock(PNCCare.class);
		when(pncCareOBJ.getBirthWeightOfNewborn()).thenReturn(10.0d);
		when(pncCareOBJ.getDeliveryConductedByID()).thenReturn(1);
		when(pncCareOBJ.getNewBornHealthStatusID()).thenReturn(1);
		when(pncCareOBJ.getBeneficiaryRegID()).thenReturn(1L);
		when(pncCareOBJ.getVisitCode()).thenReturn(1L);
		when(pncCareOBJ.getDeliveryComplicationID()).thenReturn((short) 1);
		when(pncCareOBJ.getDeliveryPlaceID()).thenReturn((short) 1);
		when(pncCareOBJ.getDeliveryTypeID()).thenReturn((short) 1);
		when(pncCareOBJ.getGestationID()).thenReturn((short) 1);
		when(pncCareOBJ.getPostNatalComplicationID()).thenReturn((short) 1);
		when(pncCareOBJ.getPregOutcomeID()).thenReturn((short) 1);
		when(pncCareOBJ.getDeliveryComplication()).thenReturn("Delivery Complication");
		when(pncCareOBJ.getDeliveryConductedBy()).thenReturn("Delivery Conducted By");
		when(pncCareOBJ.getDeliveryPlace()).thenReturn("Delivery Place");
		when(pncCareOBJ.getDeliveryType()).thenReturn("Delivery Type");
		when(pncCareOBJ.getGestationName()).thenReturn("Gestation Name");
		when(pncCareOBJ.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
		when(pncCareOBJ.getNewBornHealthStatus()).thenReturn("New Born Health Status");
		when(pncCareOBJ.getOtherDeliveryComplication()).thenReturn("Other Delivery Complication");
		when(pncCareOBJ.getOtherDeliveryPlace()).thenReturn("Other Delivery Place");
		when(pncCareOBJ.getOtherPostNatalComplication()).thenReturn("Other Post Natal Complication");
		when(pncCareOBJ.getPostNatalComplication()).thenReturn("Post Natal Complication");
		when(pncCareOBJ.getPregOutcome()).thenReturn("Preg Outcome");
		when(pncCareOBJ.getProcessed()).thenReturn("Processed");
		when(pncCareOBJ.getdDate()).thenReturn("T");
		when(pncCareOBJ.getDateOfDelivery()).thenReturn(mock(Date.class));
		doNothing().when(pncCareOBJ).setBenVisitID(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setBeneficiaryRegID(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setBirthWeightOfNewborn(Mockito.<Double>any());
		doNothing().when(pncCareOBJ).setCreatedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setCreatedDate(Mockito.<Timestamp>any());
		doNothing().when(pncCareOBJ).setDateOfDelivery(Mockito.<Date>any());
		doNothing().when(pncCareOBJ).setDeleted(Mockito.<Boolean>any());
		doNothing().when(pncCareOBJ).setDeliveryComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryComplicationID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setDeliveryConductedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryConductedByID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setDeliveryPlace(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryPlaceID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setDeliveryType(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryTypeID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setGestationID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setGestationName(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setID(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setLastModDate(Mockito.<Timestamp>any());
		doNothing().when(pncCareOBJ).setModifiedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setNewBornHealthStatus(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setNewBornHealthStatusID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setOtherDeliveryComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setOtherDeliveryPlace(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setOtherPostNatalComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setParkingPlaceID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setPostNatalComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setPostNatalComplicationID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setPregOutcome(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setPregOutcomeID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setProcessed(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setProviderServiceMapID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setReservedForChange(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setSyncedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setSyncedDate(Mockito.<Timestamp>any());
		doNothing().when(pncCareOBJ).setVanID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setVanSerialNo(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setVehicalNo(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setVisitCode(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setVisitNo(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setdDate(Mockito.<String>any());
		pncCareOBJ.setBenVisitID(1L);
		pncCareOBJ.setBeneficiaryRegID(1L);
		pncCareOBJ.setBirthWeightOfNewborn(10.0d);
		pncCareOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncCareOBJ.setCreatedDate(mock(Timestamp.class));
		pncCareOBJ.setDateOfDelivery(mock(Date.class));
		pncCareOBJ.setDeleted(true);
		pncCareOBJ.setDeliveryComplication("Delivery Complication");
		pncCareOBJ.setDeliveryComplicationID((short) 1);
		pncCareOBJ.setDeliveryConductedBy("Delivery Conducted By");
		pncCareOBJ.setDeliveryConductedByID(1);
		pncCareOBJ.setDeliveryPlace("Delivery Place");
		pncCareOBJ.setDeliveryPlaceID((short) 1);
		pncCareOBJ.setDeliveryType("Delivery Type");
		pncCareOBJ.setDeliveryTypeID((short) 1);
		pncCareOBJ.setGestationID((short) 1);
		pncCareOBJ.setGestationName("Gestation Name");
		pncCareOBJ.setID(1L);
		pncCareOBJ.setLastModDate(mock(Timestamp.class));
		pncCareOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncCareOBJ.setNewBornHealthStatus("New Born Health Status");
		pncCareOBJ.setNewBornHealthStatusID(1);
		pncCareOBJ.setOtherDeliveryComplication("Other Delivery Complication");
		pncCareOBJ.setOtherDeliveryPlace("Other Delivery Place");
		pncCareOBJ.setOtherPostNatalComplication("Other Post Natal Complication");
		pncCareOBJ.setParkingPlaceID(1);
		pncCareOBJ.setPostNatalComplication("Post Natal Complication");
		pncCareOBJ.setPostNatalComplicationID((short) 1);
		pncCareOBJ.setPregOutcome("Preg Outcome");
		pncCareOBJ.setPregOutcomeID((short) 1);
		pncCareOBJ.setProcessed("Processed");
		pncCareOBJ.setProviderServiceMapID(1);
		pncCareOBJ.setReservedForChange("Reserved For Change");
		pncCareOBJ.setSyncedBy("Synced By");
		pncCareOBJ.setSyncedDate(mock(Timestamp.class));
		pncCareOBJ.setVanID(1);
		pncCareOBJ.setVanSerialNo(1L);
		pncCareOBJ.setVehicalNo("Vehical No");
		pncCareOBJ.setVisitCode(1L);
		pncCareOBJ.setVisitNo((short) 1);
		pncCareOBJ.setdDate("2020-03-01");

		// Act
		int actualUpdateBenPNCCareResult = pNCNurseServiceImpl.updateBenPNCCare(pncCareOBJ);

		// Assert
		verify(pncCareOBJ).getBeneficiaryRegID();
		verify(pncCareOBJ).getBirthWeightOfNewborn();
		verify(pncCareOBJ).getDateOfDelivery();
		verify(pncCareOBJ).getDeliveryComplication();
		verify(pncCareOBJ).getDeliveryComplicationID();
		verify(pncCareOBJ).getDeliveryConductedBy();
		verify(pncCareOBJ).getDeliveryConductedByID();
		verify(pncCareOBJ).getDeliveryPlace();
		verify(pncCareOBJ).getDeliveryPlaceID();
		verify(pncCareOBJ).getDeliveryType();
		verify(pncCareOBJ).getDeliveryTypeID();
		verify(pncCareOBJ).getGestationID();
		verify(pncCareOBJ).getGestationName();
		verify(pncCareOBJ).getModifiedBy();
		verify(pncCareOBJ).getNewBornHealthStatus();
		verify(pncCareOBJ).getNewBornHealthStatusID();
		verify(pncCareOBJ).getOtherDeliveryComplication();
		verify(pncCareOBJ).getOtherDeliveryPlace();
		verify(pncCareOBJ).getOtherPostNatalComplication();
		verify(pncCareOBJ).getPostNatalComplication();
		verify(pncCareOBJ).getPostNatalComplicationID();
		verify(pncCareOBJ).getPregOutcome();
		verify(pncCareOBJ).getPregOutcomeID();
		verify(pncCareOBJ).getProcessed();
		verify(pncCareOBJ).getVisitCode();
		verify(pncCareOBJ, atLeast(1)).getdDate();
		verify(pncCareOBJ).setBenVisitID(isA(Long.class));
		verify(pncCareOBJ).setBeneficiaryRegID(isA(Long.class));
		verify(pncCareOBJ).setBirthWeightOfNewborn(isA(Double.class));
		verify(pncCareOBJ).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
		verify(pncCareOBJ).setCreatedDate(isA(Timestamp.class));
		verify(pncCareOBJ).setDateOfDelivery(isA(Date.class));
		verify(pncCareOBJ).setDeleted(isA(Boolean.class));
		verify(pncCareOBJ).setDeliveryComplication(eq("Delivery Complication"));
		verify(pncCareOBJ).setDeliveryComplicationID(isA(Short.class));
		verify(pncCareOBJ).setDeliveryConductedBy(eq("Delivery Conducted By"));
		verify(pncCareOBJ).setDeliveryConductedByID(isA(Integer.class));
		verify(pncCareOBJ).setDeliveryPlace(eq("Delivery Place"));
		verify(pncCareOBJ).setDeliveryPlaceID(isA(Short.class));
		verify(pncCareOBJ).setDeliveryType(eq("Delivery Type"));
		verify(pncCareOBJ).setDeliveryTypeID(isA(Short.class));
		verify(pncCareOBJ).setGestationID(isA(Short.class));
		verify(pncCareOBJ).setGestationName(eq("Gestation Name"));
		verify(pncCareOBJ).setID(isA(Long.class));
		verify(pncCareOBJ).setLastModDate(isA(Timestamp.class));
		verify(pncCareOBJ).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
		verify(pncCareOBJ).setNewBornHealthStatus(eq("New Born Health Status"));
		verify(pncCareOBJ).setNewBornHealthStatusID(isA(Integer.class));
		verify(pncCareOBJ).setOtherDeliveryComplication(eq("Other Delivery Complication"));
		verify(pncCareOBJ).setOtherDeliveryPlace(eq("Other Delivery Place"));
		verify(pncCareOBJ).setOtherPostNatalComplication(eq("Other Post Natal Complication"));
		verify(pncCareOBJ).setParkingPlaceID(isA(Integer.class));
		verify(pncCareOBJ).setPostNatalComplication(eq("Post Natal Complication"));
		verify(pncCareOBJ).setPostNatalComplicationID(isA(Short.class));
		verify(pncCareOBJ).setPregOutcome(eq("Preg Outcome"));
		verify(pncCareOBJ).setPregOutcomeID(isA(Short.class));
		verify(pncCareOBJ).setProcessed(eq("Processed"));
		verify(pncCareOBJ).setProviderServiceMapID(isA(Integer.class));
		verify(pncCareOBJ).setReservedForChange(eq("Reserved For Change"));
		verify(pncCareOBJ).setSyncedBy(eq("Synced By"));
		verify(pncCareOBJ).setSyncedDate(isA(Timestamp.class));
		verify(pncCareOBJ).setVanID(isA(Integer.class));
		verify(pncCareOBJ).setVanSerialNo(isA(Long.class));
		verify(pncCareOBJ).setVehicalNo(eq("Vehical No"));
		verify(pncCareOBJ).setVisitCode(isA(Long.class));
		verify(pncCareOBJ).setVisitNo(isA(Short.class));
		verify(pncCareOBJ).setdDate(eq("2020-03-01"));
		verify(pNCCareRepo).updatePNCCareDetails(isA(Short.class), eq("Delivery Type"), isA(Short.class),
				eq("Delivery Place"), eq("Other Delivery Place"), isA(Date.class), isA(Short.class),
				eq("Delivery Complication"), eq("Other Delivery Complication"), isA(Short.class), eq("Preg Outcome"),
				isA(Short.class), eq("Post Natal Complication"), eq("Other Post Natal Complication"), isA(Short.class),
				eq("Gestation Name"), isA(Double.class), isA(Integer.class), eq("New Born Health Status"),
				eq("Jan 1, 2020 9:00am GMT+0100"), eq("Processed"), isA(Long.class), isA(Long.class),
				isA(Integer.class), eq("Delivery Conducted By"));
		assertEquals(1, actualUpdateBenPNCCareResult);
	}

	@Test
	void testUpdateBenPNCCare4() throws ParseException {
		// Arrange
		when(pNCCareRepo.updatePNCCareDetails(Mockito.<Short>any(), Mockito.<String>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Date>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(), Mockito.<String>any(),
				Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<Double>any(), Mockito.<Integer>any(), Mockito.<String>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
				Mockito.<Integer>any(), Mockito.<String>any())).thenReturn(1);
		PNCCare pncCareOBJ = mock(PNCCare.class);
		when(pncCareOBJ.getBirthWeightOfNewborn()).thenReturn(10.0d);
		when(pncCareOBJ.getDeliveryConductedByID()).thenReturn(1);
		when(pncCareOBJ.getNewBornHealthStatusID()).thenReturn(1);
		when(pncCareOBJ.getBeneficiaryRegID()).thenReturn(1L);
		when(pncCareOBJ.getVisitCode()).thenReturn(1L);
		when(pncCareOBJ.getDeliveryComplicationID()).thenReturn((short) 1);
		when(pncCareOBJ.getDeliveryPlaceID()).thenReturn((short) 1);
		when(pncCareOBJ.getDeliveryTypeID()).thenReturn((short) 1);
		when(pncCareOBJ.getGestationID()).thenReturn((short) 1);
		when(pncCareOBJ.getPostNatalComplicationID()).thenReturn((short) 1);
		when(pncCareOBJ.getPregOutcomeID()).thenReturn((short) 1);
		when(pncCareOBJ.getDeliveryComplication()).thenReturn("Delivery Complication");
		when(pncCareOBJ.getDeliveryConductedBy()).thenReturn("Delivery Conducted By");
		when(pncCareOBJ.getDeliveryPlace()).thenReturn("Delivery Place");
		when(pncCareOBJ.getDeliveryType()).thenReturn("Delivery Type");
		when(pncCareOBJ.getGestationName()).thenReturn("Gestation Name");
		when(pncCareOBJ.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
		when(pncCareOBJ.getNewBornHealthStatus()).thenReturn("New Born Health Status");
		when(pncCareOBJ.getOtherDeliveryComplication()).thenReturn("Other Delivery Complication");
		when(pncCareOBJ.getOtherDeliveryPlace()).thenReturn("Other Delivery Place");
		when(pncCareOBJ.getOtherPostNatalComplication()).thenReturn("Other Post Natal Complication");
		when(pncCareOBJ.getPostNatalComplication()).thenReturn("Post Natal Complication");
		when(pncCareOBJ.getPregOutcome()).thenReturn("Preg Outcome");
		when(pncCareOBJ.getProcessed()).thenReturn("Processed");
		when(pncCareOBJ.getdDate()).thenReturn(null);
		when(pncCareOBJ.getDateOfDelivery()).thenReturn(mock(Date.class));
		doNothing().when(pncCareOBJ).setBenVisitID(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setBeneficiaryRegID(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setBirthWeightOfNewborn(Mockito.<Double>any());
		doNothing().when(pncCareOBJ).setCreatedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setCreatedDate(Mockito.<Timestamp>any());
		doNothing().when(pncCareOBJ).setDateOfDelivery(Mockito.<Date>any());
		doNothing().when(pncCareOBJ).setDeleted(Mockito.<Boolean>any());
		doNothing().when(pncCareOBJ).setDeliveryComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryComplicationID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setDeliveryConductedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryConductedByID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setDeliveryPlace(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryPlaceID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setDeliveryType(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryTypeID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setGestationID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setGestationName(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setID(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setLastModDate(Mockito.<Timestamp>any());
		doNothing().when(pncCareOBJ).setModifiedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setNewBornHealthStatus(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setNewBornHealthStatusID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setOtherDeliveryComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setOtherDeliveryPlace(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setOtherPostNatalComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setParkingPlaceID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setPostNatalComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setPostNatalComplicationID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setPregOutcome(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setPregOutcomeID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setProcessed(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setProviderServiceMapID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setReservedForChange(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setSyncedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setSyncedDate(Mockito.<Timestamp>any());
		doNothing().when(pncCareOBJ).setVanID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setVanSerialNo(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setVehicalNo(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setVisitCode(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setVisitNo(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setdDate(Mockito.<String>any());
		pncCareOBJ.setBenVisitID(1L);
		pncCareOBJ.setBeneficiaryRegID(1L);
		pncCareOBJ.setBirthWeightOfNewborn(10.0d);
		pncCareOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncCareOBJ.setCreatedDate(mock(Timestamp.class));
		pncCareOBJ.setDateOfDelivery(mock(Date.class));
		pncCareOBJ.setDeleted(true);
		pncCareOBJ.setDeliveryComplication("Delivery Complication");
		pncCareOBJ.setDeliveryComplicationID((short) 1);
		pncCareOBJ.setDeliveryConductedBy("Delivery Conducted By");
		pncCareOBJ.setDeliveryConductedByID(1);
		pncCareOBJ.setDeliveryPlace("Delivery Place");
		pncCareOBJ.setDeliveryPlaceID((short) 1);
		pncCareOBJ.setDeliveryType("Delivery Type");
		pncCareOBJ.setDeliveryTypeID((short) 1);
		pncCareOBJ.setGestationID((short) 1);
		pncCareOBJ.setGestationName("Gestation Name");
		pncCareOBJ.setID(1L);
		pncCareOBJ.setLastModDate(mock(Timestamp.class));
		pncCareOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncCareOBJ.setNewBornHealthStatus("New Born Health Status");
		pncCareOBJ.setNewBornHealthStatusID(1);
		pncCareOBJ.setOtherDeliveryComplication("Other Delivery Complication");
		pncCareOBJ.setOtherDeliveryPlace("Other Delivery Place");
		pncCareOBJ.setOtherPostNatalComplication("Other Post Natal Complication");
		pncCareOBJ.setParkingPlaceID(1);
		pncCareOBJ.setPostNatalComplication("Post Natal Complication");
		pncCareOBJ.setPostNatalComplicationID((short) 1);
		pncCareOBJ.setPregOutcome("Preg Outcome");
		pncCareOBJ.setPregOutcomeID((short) 1);
		pncCareOBJ.setProcessed("Processed");
		pncCareOBJ.setProviderServiceMapID(1);
		pncCareOBJ.setReservedForChange("Reserved For Change");
		pncCareOBJ.setSyncedBy("Synced By");
		pncCareOBJ.setSyncedDate(mock(Timestamp.class));
		pncCareOBJ.setVanID(1);
		pncCareOBJ.setVanSerialNo(1L);
		pncCareOBJ.setVehicalNo("Vehical No");
		pncCareOBJ.setVisitCode(1L);
		pncCareOBJ.setVisitNo((short) 1);
		pncCareOBJ.setdDate("2020-03-01");

		// Act
		int actualUpdateBenPNCCareResult = pNCNurseServiceImpl.updateBenPNCCare(pncCareOBJ);

		// Assert
		verify(pncCareOBJ).getBeneficiaryRegID();
		verify(pncCareOBJ).getBirthWeightOfNewborn();
		verify(pncCareOBJ).getDateOfDelivery();
		verify(pncCareOBJ).getDeliveryComplication();
		verify(pncCareOBJ).getDeliveryComplicationID();
		verify(pncCareOBJ).getDeliveryConductedBy();
		verify(pncCareOBJ).getDeliveryConductedByID();
		verify(pncCareOBJ).getDeliveryPlace();
		verify(pncCareOBJ).getDeliveryPlaceID();
		verify(pncCareOBJ).getDeliveryType();
		verify(pncCareOBJ).getDeliveryTypeID();
		verify(pncCareOBJ).getGestationID();
		verify(pncCareOBJ).getGestationName();
		verify(pncCareOBJ).getModifiedBy();
		verify(pncCareOBJ).getNewBornHealthStatus();
		verify(pncCareOBJ).getNewBornHealthStatusID();
		verify(pncCareOBJ).getOtherDeliveryComplication();
		verify(pncCareOBJ).getOtherDeliveryPlace();
		verify(pncCareOBJ).getOtherPostNatalComplication();
		verify(pncCareOBJ).getPostNatalComplication();
		verify(pncCareOBJ).getPostNatalComplicationID();
		verify(pncCareOBJ).getPregOutcome();
		verify(pncCareOBJ).getPregOutcomeID();
		verify(pncCareOBJ).getProcessed();
		verify(pncCareOBJ).getVisitCode();
		verify(pncCareOBJ).getdDate();
		verify(pncCareOBJ).setBenVisitID(isA(Long.class));
		verify(pncCareOBJ).setBeneficiaryRegID(isA(Long.class));
		verify(pncCareOBJ).setBirthWeightOfNewborn(isA(Double.class));
		verify(pncCareOBJ).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
		verify(pncCareOBJ).setCreatedDate(isA(Timestamp.class));
		verify(pncCareOBJ).setDateOfDelivery(isA(Date.class));
		verify(pncCareOBJ).setDeleted(isA(Boolean.class));
		verify(pncCareOBJ).setDeliveryComplication(eq("Delivery Complication"));
		verify(pncCareOBJ).setDeliveryComplicationID(isA(Short.class));
		verify(pncCareOBJ).setDeliveryConductedBy(eq("Delivery Conducted By"));
		verify(pncCareOBJ).setDeliveryConductedByID(isA(Integer.class));
		verify(pncCareOBJ).setDeliveryPlace(eq("Delivery Place"));
		verify(pncCareOBJ).setDeliveryPlaceID(isA(Short.class));
		verify(pncCareOBJ).setDeliveryType(eq("Delivery Type"));
		verify(pncCareOBJ).setDeliveryTypeID(isA(Short.class));
		verify(pncCareOBJ).setGestationID(isA(Short.class));
		verify(pncCareOBJ).setGestationName(eq("Gestation Name"));
		verify(pncCareOBJ).setID(isA(Long.class));
		verify(pncCareOBJ).setLastModDate(isA(Timestamp.class));
		verify(pncCareOBJ).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
		verify(pncCareOBJ).setNewBornHealthStatus(eq("New Born Health Status"));
		verify(pncCareOBJ).setNewBornHealthStatusID(isA(Integer.class));
		verify(pncCareOBJ).setOtherDeliveryComplication(eq("Other Delivery Complication"));
		verify(pncCareOBJ).setOtherDeliveryPlace(eq("Other Delivery Place"));
		verify(pncCareOBJ).setOtherPostNatalComplication(eq("Other Post Natal Complication"));
		verify(pncCareOBJ).setParkingPlaceID(isA(Integer.class));
		verify(pncCareOBJ).setPostNatalComplication(eq("Post Natal Complication"));
		verify(pncCareOBJ).setPostNatalComplicationID(isA(Short.class));
		verify(pncCareOBJ).setPregOutcome(eq("Preg Outcome"));
		verify(pncCareOBJ).setPregOutcomeID(isA(Short.class));
		verify(pncCareOBJ).setProcessed(eq("Processed"));
		verify(pncCareOBJ).setProviderServiceMapID(isA(Integer.class));
		verify(pncCareOBJ).setReservedForChange(eq("Reserved For Change"));
		verify(pncCareOBJ).setSyncedBy(eq("Synced By"));
		verify(pncCareOBJ).setSyncedDate(isA(Timestamp.class));
		verify(pncCareOBJ).setVanID(isA(Integer.class));
		verify(pncCareOBJ).setVanSerialNo(isA(Long.class));
		verify(pncCareOBJ).setVehicalNo(eq("Vehical No"));
		verify(pncCareOBJ).setVisitCode(isA(Long.class));
		verify(pncCareOBJ).setVisitNo(isA(Short.class));
		verify(pncCareOBJ).setdDate(eq("2020-03-01"));
		verify(pNCCareRepo).updatePNCCareDetails(isA(Short.class), eq("Delivery Type"), isA(Short.class),
				eq("Delivery Place"), eq("Other Delivery Place"), isA(Date.class), isA(Short.class),
				eq("Delivery Complication"), eq("Other Delivery Complication"), isA(Short.class), eq("Preg Outcome"),
				isA(Short.class), eq("Post Natal Complication"), eq("Other Post Natal Complication"), isA(Short.class),
				eq("Gestation Name"), isA(Double.class), isA(Integer.class), eq("New Born Health Status"),
				eq("Jan 1, 2020 9:00am GMT+0100"), eq("Processed"), isA(Long.class), isA(Long.class),
				isA(Integer.class), eq("Delivery Conducted By"));
		assertEquals(1, actualUpdateBenPNCCareResult);
	}

	@Test
	void testUpdateBenPNCCare5() throws ParseException {
		// Arrange
		when(pNCCareRepo.updatePNCCareDetails(Mockito.<Short>any(), Mockito.<String>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Date>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(), Mockito.<String>any(),
				Mockito.<Short>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<Short>any(),
				Mockito.<String>any(), Mockito.<Double>any(), Mockito.<Integer>any(), Mockito.<String>any(),
				Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any(), Mockito.<Long>any(),
				Mockito.<Integer>any(), Mockito.<String>any())).thenReturn(1);
		PNCCare pncCareOBJ = mock(PNCCare.class);
		when(pncCareOBJ.getBirthWeightOfNewborn()).thenReturn(10.0d);
		when(pncCareOBJ.getDeliveryConductedByID()).thenReturn(1);
		when(pncCareOBJ.getNewBornHealthStatusID()).thenReturn(1);
		when(pncCareOBJ.getBeneficiaryRegID()).thenReturn(1L);
		when(pncCareOBJ.getVisitCode()).thenReturn(1L);
		when(pncCareOBJ.getDeliveryComplicationID()).thenReturn((short) 1);
		when(pncCareOBJ.getDeliveryPlaceID()).thenReturn((short) 1);
		when(pncCareOBJ.getDeliveryTypeID()).thenReturn((short) 1);
		when(pncCareOBJ.getGestationID()).thenReturn((short) 1);
		when(pncCareOBJ.getPostNatalComplicationID()).thenReturn((short) 1);
		when(pncCareOBJ.getPregOutcomeID()).thenReturn((short) 1);
		when(pncCareOBJ.getDeliveryComplication()).thenReturn("Delivery Complication");
		when(pncCareOBJ.getDeliveryConductedBy()).thenReturn("Delivery Conducted By");
		when(pncCareOBJ.getDeliveryPlace()).thenReturn("Delivery Place");
		when(pncCareOBJ.getDeliveryType()).thenReturn("Delivery Type");
		when(pncCareOBJ.getGestationName()).thenReturn("Gestation Name");
		when(pncCareOBJ.getModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
		when(pncCareOBJ.getNewBornHealthStatus()).thenReturn("New Born Health Status");
		when(pncCareOBJ.getOtherDeliveryComplication()).thenReturn("Other Delivery Complication");
		when(pncCareOBJ.getOtherDeliveryPlace()).thenReturn("Other Delivery Place");
		when(pncCareOBJ.getOtherPostNatalComplication()).thenReturn("Other Post Natal Complication");
		when(pncCareOBJ.getPostNatalComplication()).thenReturn("Post Natal Complication");
		when(pncCareOBJ.getPregOutcome()).thenReturn("Preg Outcome");
		when(pncCareOBJ.getProcessed()).thenReturn("Processed");
		when(pncCareOBJ.getdDate()).thenReturn("");
		when(pncCareOBJ.getDateOfDelivery()).thenReturn(mock(Date.class));
		doNothing().when(pncCareOBJ).setBenVisitID(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setBeneficiaryRegID(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setBirthWeightOfNewborn(Mockito.<Double>any());
		doNothing().when(pncCareOBJ).setCreatedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setCreatedDate(Mockito.<Timestamp>any());
		doNothing().when(pncCareOBJ).setDateOfDelivery(Mockito.<Date>any());
		doNothing().when(pncCareOBJ).setDeleted(Mockito.<Boolean>any());
		doNothing().when(pncCareOBJ).setDeliveryComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryComplicationID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setDeliveryConductedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryConductedByID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setDeliveryPlace(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryPlaceID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setDeliveryType(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setDeliveryTypeID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setGestationID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setGestationName(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setID(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setLastModDate(Mockito.<Timestamp>any());
		doNothing().when(pncCareOBJ).setModifiedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setNewBornHealthStatus(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setNewBornHealthStatusID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setOtherDeliveryComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setOtherDeliveryPlace(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setOtherPostNatalComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setParkingPlaceID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setPostNatalComplication(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setPostNatalComplicationID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setPregOutcome(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setPregOutcomeID(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setProcessed(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setProviderServiceMapID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setReservedForChange(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setSyncedBy(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setSyncedDate(Mockito.<Timestamp>any());
		doNothing().when(pncCareOBJ).setVanID(Mockito.<Integer>any());
		doNothing().when(pncCareOBJ).setVanSerialNo(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setVehicalNo(Mockito.<String>any());
		doNothing().when(pncCareOBJ).setVisitCode(Mockito.<Long>any());
		doNothing().when(pncCareOBJ).setVisitNo(Mockito.<Short>any());
		doNothing().when(pncCareOBJ).setdDate(Mockito.<String>any());
		pncCareOBJ.setBenVisitID(1L);
		pncCareOBJ.setBeneficiaryRegID(1L);
		pncCareOBJ.setBirthWeightOfNewborn(10.0d);
		pncCareOBJ.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		pncCareOBJ.setCreatedDate(mock(Timestamp.class));
		pncCareOBJ.setDateOfDelivery(mock(Date.class));
		pncCareOBJ.setDeleted(true);
		pncCareOBJ.setDeliveryComplication("Delivery Complication");
		pncCareOBJ.setDeliveryComplicationID((short) 1);
		pncCareOBJ.setDeliveryConductedBy("Delivery Conducted By");
		pncCareOBJ.setDeliveryConductedByID(1);
		pncCareOBJ.setDeliveryPlace("Delivery Place");
		pncCareOBJ.setDeliveryPlaceID((short) 1);
		pncCareOBJ.setDeliveryType("Delivery Type");
		pncCareOBJ.setDeliveryTypeID((short) 1);
		pncCareOBJ.setGestationID((short) 1);
		pncCareOBJ.setGestationName("Gestation Name");
		pncCareOBJ.setID(1L);
		pncCareOBJ.setLastModDate(mock(Timestamp.class));
		pncCareOBJ.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		pncCareOBJ.setNewBornHealthStatus("New Born Health Status");
		pncCareOBJ.setNewBornHealthStatusID(1);
		pncCareOBJ.setOtherDeliveryComplication("Other Delivery Complication");
		pncCareOBJ.setOtherDeliveryPlace("Other Delivery Place");
		pncCareOBJ.setOtherPostNatalComplication("Other Post Natal Complication");
		pncCareOBJ.setParkingPlaceID(1);
		pncCareOBJ.setPostNatalComplication("Post Natal Complication");
		pncCareOBJ.setPostNatalComplicationID((short) 1);
		pncCareOBJ.setPregOutcome("Preg Outcome");
		pncCareOBJ.setPregOutcomeID((short) 1);
		pncCareOBJ.setProcessed("Processed");
		pncCareOBJ.setProviderServiceMapID(1);
		pncCareOBJ.setReservedForChange("Reserved For Change");
		pncCareOBJ.setSyncedBy("Synced By");
		pncCareOBJ.setSyncedDate(mock(Timestamp.class));
		pncCareOBJ.setVanID(1);
		pncCareOBJ.setVanSerialNo(1L);
		pncCareOBJ.setVehicalNo("Vehical No");
		pncCareOBJ.setVisitCode(1L);
		pncCareOBJ.setVisitNo((short) 1);
		pncCareOBJ.setdDate("2020-03-01");

		// Act
		int actualUpdateBenPNCCareResult = pNCNurseServiceImpl.updateBenPNCCare(pncCareOBJ);

		// Assert
		verify(pncCareOBJ).getBeneficiaryRegID();
		verify(pncCareOBJ).getBirthWeightOfNewborn();
		verify(pncCareOBJ).getDateOfDelivery();
		verify(pncCareOBJ).getDeliveryComplication();
		verify(pncCareOBJ).getDeliveryComplicationID();
		verify(pncCareOBJ).getDeliveryConductedBy();
		verify(pncCareOBJ).getDeliveryConductedByID();
		verify(pncCareOBJ).getDeliveryPlace();
		verify(pncCareOBJ).getDeliveryPlaceID();
		verify(pncCareOBJ).getDeliveryType();
		verify(pncCareOBJ).getDeliveryTypeID();
		verify(pncCareOBJ).getGestationID();
		verify(pncCareOBJ).getGestationName();
		verify(pncCareOBJ).getModifiedBy();
		verify(pncCareOBJ).getNewBornHealthStatus();
		verify(pncCareOBJ).getNewBornHealthStatusID();
		verify(pncCareOBJ).getOtherDeliveryComplication();
		verify(pncCareOBJ).getOtherDeliveryPlace();
		verify(pncCareOBJ).getOtherPostNatalComplication();
		verify(pncCareOBJ).getPostNatalComplication();
		verify(pncCareOBJ).getPostNatalComplicationID();
		verify(pncCareOBJ).getPregOutcome();
		verify(pncCareOBJ).getPregOutcomeID();
		verify(pncCareOBJ).getProcessed();
		verify(pncCareOBJ).getVisitCode();
		verify(pncCareOBJ, atLeast(1)).getdDate();
		verify(pncCareOBJ).setBenVisitID(isA(Long.class));
		verify(pncCareOBJ).setBeneficiaryRegID(isA(Long.class));
		verify(pncCareOBJ).setBirthWeightOfNewborn(isA(Double.class));
		verify(pncCareOBJ).setCreatedBy(eq("Jan 1, 2020 8:00am GMT+0100"));
		verify(pncCareOBJ).setCreatedDate(isA(Timestamp.class));
		verify(pncCareOBJ).setDateOfDelivery(isA(Date.class));
		verify(pncCareOBJ).setDeleted(isA(Boolean.class));
		verify(pncCareOBJ).setDeliveryComplication(eq("Delivery Complication"));
		verify(pncCareOBJ).setDeliveryComplicationID(isA(Short.class));
		verify(pncCareOBJ).setDeliveryConductedBy(eq("Delivery Conducted By"));
		verify(pncCareOBJ).setDeliveryConductedByID(isA(Integer.class));
		verify(pncCareOBJ).setDeliveryPlace(eq("Delivery Place"));
		verify(pncCareOBJ).setDeliveryPlaceID(isA(Short.class));
		verify(pncCareOBJ).setDeliveryType(eq("Delivery Type"));
		verify(pncCareOBJ).setDeliveryTypeID(isA(Short.class));
		verify(pncCareOBJ).setGestationID(isA(Short.class));
		verify(pncCareOBJ).setGestationName(eq("Gestation Name"));
		verify(pncCareOBJ).setID(isA(Long.class));
		verify(pncCareOBJ).setLastModDate(isA(Timestamp.class));
		verify(pncCareOBJ).setModifiedBy(eq("Jan 1, 2020 9:00am GMT+0100"));
		verify(pncCareOBJ).setNewBornHealthStatus(eq("New Born Health Status"));
		verify(pncCareOBJ).setNewBornHealthStatusID(isA(Integer.class));
		verify(pncCareOBJ).setOtherDeliveryComplication(eq("Other Delivery Complication"));
		verify(pncCareOBJ).setOtherDeliveryPlace(eq("Other Delivery Place"));
		verify(pncCareOBJ).setOtherPostNatalComplication(eq("Other Post Natal Complication"));
		verify(pncCareOBJ).setParkingPlaceID(isA(Integer.class));
		verify(pncCareOBJ).setPostNatalComplication(eq("Post Natal Complication"));
		verify(pncCareOBJ).setPostNatalComplicationID(isA(Short.class));
		verify(pncCareOBJ).setPregOutcome(eq("Preg Outcome"));
		verify(pncCareOBJ).setPregOutcomeID(isA(Short.class));
		verify(pncCareOBJ).setProcessed(eq("Processed"));
		verify(pncCareOBJ).setProviderServiceMapID(isA(Integer.class));
		verify(pncCareOBJ).setReservedForChange(eq("Reserved For Change"));
		verify(pncCareOBJ).setSyncedBy(eq("Synced By"));
		verify(pncCareOBJ).setSyncedDate(isA(Timestamp.class));
		verify(pncCareOBJ).setVanID(isA(Integer.class));
		verify(pncCareOBJ).setVanSerialNo(isA(Long.class));
		verify(pncCareOBJ).setVehicalNo(eq("Vehical No"));
		verify(pncCareOBJ).setVisitCode(isA(Long.class));
		verify(pncCareOBJ).setVisitNo(isA(Short.class));
		verify(pncCareOBJ).setdDate(eq("2020-03-01"));
		verify(pNCCareRepo).updatePNCCareDetails(isA(Short.class), eq("Delivery Type"), isA(Short.class),
				eq("Delivery Place"), eq("Other Delivery Place"), isA(Date.class), isA(Short.class),
				eq("Delivery Complication"), eq("Other Delivery Complication"), isA(Short.class), eq("Preg Outcome"),
				isA(Short.class), eq("Post Natal Complication"), eq("Other Post Natal Complication"), isA(Short.class),
				eq("Gestation Name"), isA(Double.class), isA(Integer.class), eq("New Born Health Status"),
				eq("Jan 1, 2020 9:00am GMT+0100"), eq("Processed"), isA(Long.class), isA(Long.class),
				isA(Integer.class), eq("Delivery Conducted By"));
		assertEquals(1, actualUpdateBenPNCCareResult);
	}
}
