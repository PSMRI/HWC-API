package com.iemr.hwc.service.videoconsultation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.iemr.hwc.data.videoconsultation.UserJitsi;
import com.iemr.hwc.data.videoconsultation.VideoConsultationUser;
import com.iemr.hwc.repo.login.MasterVanRepo;
import com.iemr.hwc.repo.videoconsultation.UserJitsiRepo;
import com.iemr.hwc.repo.videoconsultation.VideoConsultationUserRepo;
import com.iemr.hwc.utils.config.ConfigProperties;
import com.iemr.hwc.utils.exception.VideoConsultationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

public class TestVideoConsultationServiceImpl {

	@ExtendWith(MockitoExtension.class)

	@Mock
	private VideoConsultationUserRepo userRepo;
	@Mock
	private UserJitsiRepo userJitsiRepo;

	@Mock
	private MasterVanRepo masterVanRepo;

	@InjectMocks
	private VideoConsultationServiceImpl videoConsultationService;

	private VideoConsultationUser user;
	private Long userId;
	private VideoConsultationUser fromUser;
	private VideoConsultationUser toUser;
	private String jitsiDNS = "https://jitsi.example.com";

	private VideoConsultationUser validUser;
	private String validVanEmail;
	private UserJitsi validVanUser;

	@BeforeEach
	public void setUp() {
		userId = 1L;
		user = new VideoConsultationUser();
		user.setSwymedEmailID("test@example.com");
		user.setSwymedPassword("password");
		user.setSwymedDomain("domain");

		ConfigProperties.setProperty("swymed_dnsname", "http://example.com");
	}

	@Test
	public void testLoginSuccess() throws VideoConsultationException {
		when(userRepo.findOneMap(userId)).thenReturn(user);

		String result = videoConsultationService.login(userId);

		assertNotNull(result);
		assertTrue(result.contains("http://example.com"));
		assertTrue(result.contains("test@example.com"));
		assertTrue(result.contains("password"));
		assertTrue(result.contains("domain"));
	}

	@Test
	public void testLoginUserNotFound() {
		when(userRepo.findOneMap(userId)).thenReturn(null);

		VideoConsultationException exception = assertThrows(VideoConsultationException.class, () -> {
			videoConsultationService.login(userId);
		});

		assertEquals("User doesnt have access to video consultation", exception.getMessage());
	}

	@Test
	public void testCallUser_Success() throws VideoConsultationException {
		String result = videoConsultationService.callUser(1L, 2L);
		assertTrue(result.contains("fromUser@example.com"));
		assertTrue(result.contains("toUser@example.com"));
	}

	@Test
	public void testCallUser_FromUserNotFound() {
		when(userRepo.findOneMap(1L)).thenReturn(null);

		VideoConsultationException exception = assertThrows(VideoConsultationException.class, () -> {
			videoConsultationService.callUser(1L, 2L);
		});

		assertEquals("User doesnt have access to video consultation", exception.getMessage());
	}

	@Test
	public void testCallUser_ToUserNotFound() {
		when(userRepo.findOneMap(2L)).thenReturn(null);

		VideoConsultationException exception = assertThrows(VideoConsultationException.class, () -> {
			videoConsultationService.callUser(1L, 2L);
		});

		assertEquals("Callee couldn't be found. Please call manually", exception.getMessage());
	}

	@Test
	public void testCallUserjitsi_UserNotFound() {
		Long fromUserId = 1L;
		Long toUserId = 2L;

		when(userJitsiRepo.findOneJitsiMap(fromUserId)).thenReturn(null);

		VideoConsultationException exception = assertThrows(VideoConsultationException.class, () -> {
			videoConsultationService.callUserjitsi(fromUserId, toUserId);
		});

		assertEquals("User doesnt have access to video consultation", exception.getMessage());
		verify(userJitsiRepo, times(1)).findOneJitsiMap(fromUserId);
		verify(userJitsiRepo, never()).findOneJitsiMap(toUserId);
	}

	@Test
	public void testCallUserjitsi_CalleeNotFound() {
		Long fromUserId = 1L;
		Long toUserId = 2L;

		UserJitsi fromUser = new UserJitsi();
		fromUser.setJitsiUserName("fromUser");

		when(userJitsiRepo.findOneJitsiMap(fromUserId)).thenReturn(fromUser);
		when(userJitsiRepo.findOneJitsiMap(toUserId)).thenReturn(null);

		VideoConsultationException exception = assertThrows(VideoConsultationException.class, () -> {
			videoConsultationService.callUserjitsi(fromUserId, toUserId);
		});

		assertEquals("Callee couldn't be found. Please call manually", exception.getMessage());
		verify(userJitsiRepo, times(1)).findOneJitsiMap(fromUserId);
		verify(userJitsiRepo, times(1)).findOneJitsiMap(toUserId);
	}

	@Test
	public void testCallUserjitsi_Success() throws VideoConsultationException {
		Long fromUserId = 1L;
		Long toUserId = 2L;

		UserJitsi fromUser = new UserJitsi();
		fromUser.setJitsiUserName("fromUser");
		fromUser.setJitsiPassword("password");

		UserJitsi toUser = new UserJitsi();
		toUser.setJitsiUserName("toUser");

		when(userJitsiRepo.findOneJitsiMap(fromUserId)).thenReturn(fromUser);
		when(userJitsiRepo.findOneJitsiMap(toUserId)).thenReturn(toUser);

		String expectedUrl = jitsiDNS + "/fromUser/password";
		String actualUrl = videoConsultationService.callUserjitsi(fromUserId, toUserId);

		assertEquals(expectedUrl, actualUrl);
		verify(userJitsiRepo, times(1)).findOneJitsiMap(fromUserId);
		verify(userJitsiRepo, times(1)).findOneJitsiMap(toUserId);
	}

	@Test
	public void testCallVan_UserNotFound() {
		when(userRepo.findOneMap(1L)).thenReturn(null);

		VideoConsultationException exception = assertThrows(VideoConsultationException.class, () -> {
			videoConsultationService.callVan(1L, 1);
		});

		assertEquals("User doesnt have access to video consultation", exception.getMessage());
	}

	@Test
	public void testCallVan_VanNotFound() {
		when(userRepo.findOneMap(1L)).thenReturn(validUser);
		when(masterVanRepo.getSpokeEmail(1)).thenReturn(null);

		VideoConsultationException exception = assertThrows(VideoConsultationException.class, () -> {
			videoConsultationService.callVan(1L, 1);
		});

		assertEquals("Callee couldn't be found. Please call manually", exception.getMessage());
	}

	@Test
	public void testCallVan_Success() throws VideoConsultationException {
		when(userRepo.findOneMap(1L)).thenReturn(validUser);
		when(masterVanRepo.getSpokeEmail(1)).thenReturn(validVanEmail);

		String result = videoConsultationService.callVan(1L, 1);

		assertTrue(result.contains("user@example.com"));
		assertTrue(result.contains("password"));
		assertTrue(result.contains("domain"));
		assertTrue(result.contains("van@example.com"));
	}

	@Test
	public void testCallVanJitsi_InvalidFromUserId() {
		when(userJitsiRepo.findOneJitsiMap(1L)).thenReturn(null);

		VideoConsultationException exception = assertThrows(VideoConsultationException.class, () -> {
			videoConsultationService.callVanJitsi(1L, 1);
		});

		assertEquals("User doesnt have access to video consultation", exception.getMessage());
	}

	@Test
	public void testCallVanJitsi_InvalidVanId() {
		when(userJitsiRepo.findOneJitsiMap(1L)).thenReturn(validUser);
		when(userJitsiRepo.findOneJitsiMapVan(1)).thenReturn(null);

		VideoConsultationException exception = assertThrows(VideoConsultationException.class, () -> {
			videoConsultationService.callVanJitsi(1L, 1);
		});

		assertEquals("Callee couldn't be found. Please call manually", exception.getMessage());
	}

	@Test
	public void testCallVanJitsi_ValidInputs() throws VideoConsultationException {
		when(userJitsiRepo.findOneJitsiMap(1L)).thenReturn(validUser);
		when(userJitsiRepo.findOneJitsiMapVan(1)).thenReturn(validVanUser);

		String expectedUrl = "jitsi_dnsname/validVanUser/vanPassword";
		String actualUrl = videoConsultationService.callVanJitsi(1L, 1);

		assertEquals(expectedUrl, actualUrl);
	}
}
