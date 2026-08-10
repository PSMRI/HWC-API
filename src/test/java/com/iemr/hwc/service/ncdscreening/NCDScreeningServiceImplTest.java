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
package com.iemr.hwc.service.ncdscreening;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iemr.hwc.data.ncdScreening.BreastCancerScreening;
import com.iemr.hwc.data.ncdScreening.CbacDetails;
import com.iemr.hwc.data.ncdScreening.CervicalCancerScreening;
import com.iemr.hwc.data.ncdScreening.DiabetesScreening;
import com.iemr.hwc.data.ncdScreening.HypertensionScreening;
import com.iemr.hwc.data.ncdScreening.OralCancerScreening;
import com.iemr.hwc.repo.nurse.ncdscreening.BreastCancerScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.CbacDetailsRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.CervicalCancerScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.DiabetesScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.HypertensionScreeningRepo;
import com.iemr.hwc.repo.nurse.ncdscreening.OralCancerScreeningRepo;
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class NCDScreeningServiceImplTest {

    @Mock
    private DiabetesScreeningRepo diabetesScreeningRepo;
    @Mock
    private HypertensionScreeningRepo hypertensionScreeningRepo;
    @Mock
    private OralCancerScreeningRepo oralCancerScreeningRepo;
    @Mock
    private BreastCancerScreeningRepo breastCancerScreeningRepo;
    @Mock
    private CervicalCancerScreeningRepo cervicalCancerScreeningRepo;
    @Mock
    private CbacDetailsRepo cbacDetailsRepo;

    @InjectMocks
    private NCDScreeningServiceImpl ncdScreeningService;

    private DiabetesScreening diabetesScreening;
    private HypertensionScreening hypertensionScreening;
    private OralCancerScreening oralCancerScreening;
    private BreastCancerScreening breastCancerScreening;
    private CervicalCancerScreening cervicalCancerScreening;
    private CbacDetails cbacDetails;

    @BeforeEach
    void setUp() {
        diabetesScreening = new DiabetesScreening();
        diabetesScreening.setId(1L);

        hypertensionScreening = new HypertensionScreening();
        hypertensionScreening.setId(1L);

        oralCancerScreening = new OralCancerScreening();
        oralCancerScreening.setId(1L);

        breastCancerScreening = new BreastCancerScreening();
        breastCancerScreening.setId(1L);

        cervicalCancerScreening = new CervicalCancerScreening();
        cervicalCancerScreening.setId(1L);

        cbacDetails = new CbacDetails();
        cbacDetails.setId(1L);
    }

    // -------------------- saveDiabetesDetails --------------------

    @Test
    void saveDiabetesDetails_success_returnsId() throws IEMRException {
        when(diabetesScreeningRepo.save(diabetesScreening)).thenReturn(diabetesScreening);

        Long result = ncdScreeningService.saveDiabetesDetails(diabetesScreening);

        assertEquals(1L, result);
        verify(diabetesScreeningRepo).save(diabetesScreening);
    }

    @Test
    void saveDiabetesDetails_repoReturnsNull_throwsIEMRException() {
        when(diabetesScreeningRepo.save(diabetesScreening)).thenReturn(null);

        IEMRException ex = assertThrows(IEMRException.class,
                () -> ncdScreeningService.saveDiabetesDetails(diabetesScreening));

        assertEquals("Error while saving diabetes screening data", ex.getMessage());
    }

    @Test
    void saveDiabetesDetails_repoReturnsObjectWithNullId_throwsIEMRException() {
        DiabetesScreening noId = new DiabetesScreening();
        when(diabetesScreeningRepo.save(noId)).thenReturn(noId);

        IEMRException ex = assertThrows(IEMRException.class,
                () -> ncdScreeningService.saveDiabetesDetails(noId));

        assertEquals("Error while saving diabetes screening data", ex.getMessage());
    }

    // -------------------- saveHypertensionDetails --------------------

    @Test
    void saveHypertensionDetails_success_returnsId() throws IEMRException {
        when(hypertensionScreeningRepo.save(hypertensionScreening)).thenReturn(hypertensionScreening);

        Long result = ncdScreeningService.saveHypertensionDetails(hypertensionScreening);

        assertEquals(1L, result);
        verify(hypertensionScreeningRepo).save(hypertensionScreening);
    }

    @Test
    void saveHypertensionDetails_repoReturnsNull_throwsIEMRException() {
        when(hypertensionScreeningRepo.save(hypertensionScreening)).thenReturn(null);

        IEMRException ex = assertThrows(IEMRException.class,
                () -> ncdScreeningService.saveHypertensionDetails(hypertensionScreening));

        assertEquals("Error while saving hypertension screening data", ex.getMessage());
    }

    @Test
    void saveHypertensionDetails_repoReturnsObjectWithNullId_throwsIEMRException() {
        HypertensionScreening noId = new HypertensionScreening();
        when(hypertensionScreeningRepo.save(noId)).thenReturn(noId);

        IEMRException ex = assertThrows(IEMRException.class,
                () -> ncdScreeningService.saveHypertensionDetails(noId));

        assertEquals("Error while saving hypertension screening data", ex.getMessage());
    }

    // -------------------- saveOralCancerDetails --------------------

    @Test
    void saveOralCancerDetails_success_returnsId() throws IEMRException {
        when(oralCancerScreeningRepo.save(oralCancerScreening)).thenReturn(oralCancerScreening);

        Long result = ncdScreeningService.saveOralCancerDetails(oralCancerScreening);

        assertEquals(1L, result);
        verify(oralCancerScreeningRepo).save(oralCancerScreening);
    }

    @Test
    void saveOralCancerDetails_repoReturnsNull_throwsIEMRException() {
        when(oralCancerScreeningRepo.save(oralCancerScreening)).thenReturn(null);

        IEMRException ex = assertThrows(IEMRException.class,
                () -> ncdScreeningService.saveOralCancerDetails(oralCancerScreening));

        assertEquals("Error while saving oral screening", ex.getMessage());
    }

    // -------------------- saveBreastCancerDetails --------------------

    @Test
    void saveBreastCancerDetails_success_returnsId() throws IEMRException {
        when(breastCancerScreeningRepo.save(breastCancerScreening)).thenReturn(breastCancerScreening);

        Long result = ncdScreeningService.saveBreastCancerDetails(breastCancerScreening);

        assertEquals(1L, result);
        verify(breastCancerScreeningRepo).save(breastCancerScreening);
    }

    @Test
    void saveBreastCancerDetails_repoReturnsNull_throwsIEMRException() {
        when(breastCancerScreeningRepo.save(breastCancerScreening)).thenReturn(null);

        IEMRException ex = assertThrows(IEMRException.class,
                () -> ncdScreeningService.saveBreastCancerDetails(breastCancerScreening));

        assertEquals("Error while saving breast cancer screening", ex.getMessage());
    }

    // -------------------- saveCervicalDetails --------------------

    @Test
    void saveCervicalDetails_success_returnsId() throws IEMRException {
        when(cervicalCancerScreeningRepo.save(cervicalCancerScreening)).thenReturn(cervicalCancerScreening);

        Long result = ncdScreeningService.saveCervicalDetails(cervicalCancerScreening);

        assertEquals(1L, result);
        verify(cervicalCancerScreeningRepo).save(cervicalCancerScreening);
    }

    @Test
    void saveCervicalDetails_repoReturnsNull_throwsIEMRException() {
        when(cervicalCancerScreeningRepo.save(cervicalCancerScreening)).thenReturn(null);

        IEMRException ex = assertThrows(IEMRException.class,
                () -> ncdScreeningService.saveCervicalDetails(cervicalCancerScreening));

        assertEquals("Error while saving cervical screening", ex.getMessage());
    }

    // -------------------- saveCbacDetails --------------------

    @Test
    void saveCbacDetails_success_returnsId() throws IEMRException {
        when(cbacDetailsRepo.save(cbacDetails)).thenReturn(cbacDetails);

        Long result = ncdScreeningService.saveCbacDetails(cbacDetails);

        assertEquals(1L, result);
        verify(cbacDetailsRepo).save(cbacDetails);
    }

    @Test
    void saveCbacDetails_repoReturnsNull_throwsIEMRException() {
        when(cbacDetailsRepo.save(cbacDetails)).thenReturn(null);

        IEMRException ex = assertThrows(IEMRException.class,
                () -> ncdScreeningService.saveCbacDetails(cbacDetails));

        assertEquals("Error while saving Cbac details", ex.getMessage());
    }
}
