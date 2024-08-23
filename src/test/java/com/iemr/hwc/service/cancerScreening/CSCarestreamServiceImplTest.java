package com.iemr.hwc.service.cancerScreening;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
class CSCarestreamServiceImplTest {
    @InjectMocks
    private CSCarestreamServiceImpl cSCarestreamServiceImpl;

    @Test
    void testCreateMamographyRequest() {
        // Arrange, Act and Assert
        assertEquals(0, cSCarestreamServiceImpl.createMamographyRequest(new ArrayList<>(), 1L, 1L, "JaneDoe"));
        assertEquals(0, cSCarestreamServiceImpl.createMamographyRequest(new ArrayList<>(), 2L, 1L, "JaneDoe"));
    }

    @Test
    void testCreateMamographyRequest2() {
        // Arrange
        ArrayList<Object[]> benDataForCareStream = new ArrayList<>();
        benDataForCareStream.add(new Object[]{"42"});

        // Act and Assert
        assertEquals(0, cSCarestreamServiceImpl.createMamographyRequest(benDataForCareStream, 1L, 1L, "JaneDoe"));
    }

    @Test
    void testCreateMamographyRequest3() {
        // Arrange
        ArrayList<Object[]> benDataForCareStream = new ArrayList<>();
        benDataForCareStream.add(new Object[]{"42"});
        benDataForCareStream.add(new Object[]{"42"});

        // Act and Assert
        assertEquals(0, cSCarestreamServiceImpl.createMamographyRequest(benDataForCareStream, 1L, 1L, "JaneDoe"));
    }

    @Test
    void testCreateMamographyRequest4() {
        // Arrange
        ArrayList<Object[]> benDataForCareStream = new ArrayList<>();
        benDataForCareStream.add(new Object[]{"42", "42"});

        // Act and Assert
        assertEquals(0, cSCarestreamServiceImpl.createMamographyRequest(benDataForCareStream, 1L, 1L, "JaneDoe"));
    }

    @Test
    void testCreateMamographyRequest5() {
        // Arrange
        ArrayList<Object[]> benDataForCareStream = new ArrayList<>();
        benDataForCareStream.add(new Object[]{mock(Date.class), "42"});

        // Act and Assert
        assertEquals(0, cSCarestreamServiceImpl.createMamographyRequest(benDataForCareStream, 1L, 1L, "JaneDoe"));
    }

    @Test
    void testCreateMamographyRequest6() {
        // Arrange
        ArrayList<Object[]> benDataForCareStream = new ArrayList<>();
        benDataForCareStream.add(new Object[]{"42", mock(Date.class)});

        // Act and Assert
        assertEquals(0, cSCarestreamServiceImpl.createMamographyRequest(benDataForCareStream, 1L, 1L, "JaneDoe"));
    }

    @Test
    void testCreateMamographyRequest7() {
        // Arrange
        ArrayList<Object[]> benDataForCareStream = new ArrayList<>();
        benDataForCareStream.add(new Object[]{"42", null});

        // Act and Assert
        assertEquals(0, cSCarestreamServiceImpl.createMamographyRequest(benDataForCareStream, 1L, 1L, "JaneDoe"));
    }
}
