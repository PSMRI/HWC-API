package com.iemr.hwc.service.quickBlox;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.iemr.hwc.repo.quickBlox.QuickBloxRepo;
import com.iemr.hwc.utils.exception.IEMRException;

@ExtendWith(MockitoExtension.class)
class QuickbloxServiceImplTest {
    @Mock
    private QuickBloxRepo quickBloxRepo;

    @InjectMocks
    private QuickbloxServiceImpl quickbloxServiceImpl;

    @Test
    void testGetQuickbloxIds() throws IEMRException {
        quickbloxServiceImpl.getQuickbloxIds("Request Obj");
    }
}
