package com.iemr.hwc.service.common.master;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.repo.masterrepo.ncdCare.NCDCareTypeRepo;

@ExtendWith(MockitoExtension.class)
class NCDCareMasterDataServiceImplTest {
    @InjectMocks
    private NCDCareMasterDataServiceImpl nCDCareMasterDataServiceImpl;

    @Mock
    private NCDCareTypeRepo nCDCareTypeRepo;

    @Mock
    private NCDScreeningMasterServiceImpl nCDScreeningMasterServiceImpl;

    @Test
    void testGetNCDCareMasterData() {
        // Arrange
        when(nCDCareTypeRepo.getNCDCareTypes()).thenReturn(new ArrayList<>());
        when(nCDScreeningMasterServiceImpl.getNCDScreeningConditions()).thenReturn(new ArrayList<>());

        // Act
        String actualNCDCareMasterData = nCDCareMasterDataServiceImpl.getNCDCareMasterData();

        // Assert
        verify(nCDCareTypeRepo).getNCDCareTypes();
        verify(nCDScreeningMasterServiceImpl).getNCDScreeningConditions();
        assertEquals("{\"ncdCareTypes\":[],\"ncdCareConditions\":[]}", actualNCDCareMasterData);
    }

    @Test
    void testGettersAndSetters() {
        NCDCareMasterDataServiceImpl ncdCareMasterDataServiceImpl = new NCDCareMasterDataServiceImpl();

        // Act
        ncdCareMasterDataServiceImpl.setNcdCareTypeRepo(mock(NCDCareTypeRepo.class));
        ncdCareMasterDataServiceImpl.setNcdScreeningMasterServiceImpl(new NCDScreeningMasterServiceImpl());
    }
}
