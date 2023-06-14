package com.iemr.mmu.service.videoconsultation;

import com.iemr.mmu.utils.exception.VideoConsultationException;

public interface VideoConsultationService {

	String login(Long userId) throws VideoConsultationException;

	String callUser(Long fromUserId, Long toUserId) throws VideoConsultationException;

	String callUserjitsi(Long fromUserId, Long toUserId) throws VideoConsultationException;

	String callVan(Long fromUserId, Integer vanID) throws VideoConsultationException;

	String logout();

	String callVanJitsi(Long fromUserId, Integer vanID) throws VideoConsultationException;

}
