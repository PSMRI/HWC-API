package com.iemr.hwc.service.ncdscreening;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.iemr.hwc.utils.exception.IEMRException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
class DiabetesAndHypertensionOutcomeServiceImplTest {
    @InjectMocks
    private DiabetesAndHypertensionOutcomeServiceImpl diabetesAndHypertensionOutcomeServiceImpl;

    @Test
    void testGetHypertensionOutcome() throws IEMRException {
        // Arrange, Act and Assert
        assertThrows(IEMRException.class,
                () -> diabetesAndHypertensionOutcomeServiceImpl.getHypertensionOutcome("Request"));
        assertThrows(IEMRException.class, () -> diabetesAndHypertensionOutcomeServiceImpl
                .getHypertensionOutcome("Error while finding hypertension outcome :"));
        assertThrows(IEMRException.class, () -> diabetesAndHypertensionOutcomeServiceImpl.getHypertensionOutcome("42"));
        assertThrows(IEMRException.class, () -> diabetesAndHypertensionOutcomeServiceImpl.getHypertensionOutcome(""));
        assertThrows(IEMRException.class, () -> diabetesAndHypertensionOutcomeServiceImpl
                .getHypertensionOutcome("42Error while finding hypertension outcome :"));
    }

    @Test
    void testGetDiabetesOutcome() throws IEMRException {
        // Arrange, Act and Assert
        assertThrows(IEMRException.class, () -> diabetesAndHypertensionOutcomeServiceImpl.getDiabetesOutcome("Request"));
        assertThrows(IEMRException.class,
                () -> diabetesAndHypertensionOutcomeServiceImpl.getDiabetesOutcome("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        assertThrows(IEMRException.class,
                () -> diabetesAndHypertensionOutcomeServiceImpl.getDiabetesOutcome("Error while finding diabetes outcome :"));
        assertThrows(IEMRException.class, () -> diabetesAndHypertensionOutcomeServiceImpl.getDiabetesOutcome("42"));
        assertThrows(IEMRException.class, () -> diabetesAndHypertensionOutcomeServiceImpl.getDiabetesOutcome(""));
        assertThrows(IEMRException.class,
                () -> diabetesAndHypertensionOutcomeServiceImpl.getDiabetesOutcome("42Error while finding diabetes outcome :"));
    }
}
