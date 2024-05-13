package com.iemr.hwc.controller.videoconsultation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.catalina.User;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.catalina.users.MemoryUserDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.iemr.hwc.service.videoconsultation.VideoConsultationService;

@ExtendWith(MockitoExtension.class)
class VideoConsultationControllerTest {
    @InjectMocks
    private VideoConsultationController videoConsultationController;

    @Mock
    private VideoConsultationService videoConsultationService;

    @Test
    void testCall() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/videoConsultation/call/{fromUserID}/42",
                1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(videoConsultationController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testCallSwymedAndJitsi() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/videoConsultation/call/{fromUserID}/{toUserID}/{type}", 1L, 1L, "Type");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(videoConsultationController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testCallSwymedAndJitsi2() throws Exception {
        // Arrange
        User user = mock(User.class);
        when(user.getName()).thenReturn("Name");
        UserDatabaseRealm.UserDatabasePrincipal principal = new UserDatabaseRealm.UserDatabasePrincipal(user,
                new MemoryUserDatabase());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/videoConsultation/call/{fromUserID}/{toUserID}/{type}", 1L, 1L, "Type");
        requestBuilder.principal(principal);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(videoConsultationController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testCallVanSwymedAndJitsi() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/videoConsultation/callvan/{fromUserID}/{vanID}/{type}", 1L, 1, "Type");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(videoConsultationController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testCallVanSwymedAndJitsi2() throws Exception {
        // Arrange
        User user = mock(User.class);
        when(user.getName()).thenReturn("Name");
        UserDatabaseRealm.UserDatabasePrincipal principal = new UserDatabaseRealm.UserDatabasePrincipal(user,
                new MemoryUserDatabase());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/videoConsultation/callvan/{fromUserID}/{vanID}/{type}", 1L, 1, "Type");
        requestBuilder.principal(principal);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(videoConsultationController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testCallvan() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/videoConsultation/callvan/{fromUserID}/{vanID}", 1L, 1);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(videoConsultationController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testLogin() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/videoConsultation/login/{userID}", 1L);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(videoConsultationController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testLogout() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/videoConsultation/logout");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(videoConsultationController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }
}
