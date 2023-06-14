package com.iemr.mmu.service.videoconsultation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.mmu.data.videoconsultation.UserJitsi;
import com.iemr.mmu.data.videoconsultation.VideoConsultationUser;
import com.iemr.mmu.repo.login.MasterVanRepo;
import com.iemr.mmu.repo.videoconsultation.UserJitsiRepo;
import com.iemr.mmu.repo.videoconsultation.VideoConsultationUserRepo;
import com.iemr.mmu.utils.config.ConfigProperties;
import com.iemr.mmu.utils.exception.VideoConsultationException;

@Service
public class VideoConsultationServiceImpl implements VideoConsultationService {

	private String videoConsultationDNS = ConfigProperties.getPropertyByName("swymed_dnsname");
	private String jitsiDNS = ConfigProperties.getPropertyByName("jitsi_dnsname");
	private static final String noAccessToVideoConsultationError = "User doesnt have access to video consultation";
	private static final String calleeNotFoundError = "Callee couldn't be found. Please call manually";

	@Autowired
	private UserJitsiRepo userJitsiRepo;

	@Autowired
	private VideoConsultationUserRepo userRepo;

	@Autowired
	private MasterVanRepo masterVanRepo;

	@Override
	public String login(Long userid) throws VideoConsultationException {
		VideoConsultationUser user = userRepo.findOneMap(userid);

		if (user == null) {
			throw new VideoConsultationException(noAccessToVideoConsultationError);
		}
		StringBuilder data = new StringBuilder();

		data.append(videoConsultationDNS);
		data.append("?l=");
		data.append(user.getSwymedEmailID());
		data.append("&p=");
		data.append(user.getSwymedPassword());
		data.append("&d=");
		data.append(user.getSwymedDomain());

		return data.toString();
	}

	@Override
	public String callUser(Long fromuserid, Long touserid) throws VideoConsultationException {
		VideoConsultationUser user = userRepo.findOneMap(fromuserid);
		VideoConsultationUser touser = userRepo.findOneMap(touserid);

		if (user == null) {
			throw new VideoConsultationException(noAccessToVideoConsultationError);
		}
		if (touser == null) {
			throw new VideoConsultationException(calleeNotFoundError);
		}

		StringBuilder data = new StringBuilder();

		data.append(videoConsultationDNS);
		data.append("?l=");
		data.append(user.getSwymedEmailID());
		data.append("&p=");
		data.append(user.getSwymedPassword());
		data.append("&d=");
		data.append(user.getSwymedDomain());
		data.append("&e=");
		data.append(touser.getSwymedEmailID());

		return data.toString();
	}

	@Override
	public String callUserjitsi(Long fromuserid, Long touserid) throws VideoConsultationException {
		UserJitsi user = userJitsiRepo.findOneJitsiMap(fromuserid);
		UserJitsi touser = userJitsiRepo.findOneJitsiMap(touserid);

		if (user == null) {
			throw new VideoConsultationException(noAccessToVideoConsultationError);
		}
		if (touser == null) {
			throw new VideoConsultationException(calleeNotFoundError);
		}

		StringBuilder data = new StringBuilder();

		data.append(jitsiDNS);
		data.append("/");
		data.append(user.getJitsiUserName());
		if (user.getJitsiPassword() != null) {
			data.append("/");
			data.append(user.getJitsiPassword());
		}

		return data.toString();
	}

	@Override
	public String callVan(Long fromuserid, Integer vanID) throws VideoConsultationException {
		VideoConsultationUser user = userRepo.findOneMap(fromuserid);
		String vanSwymesEmail = masterVanRepo.getSpokeEmail(vanID);

		if (user == null) {
			throw new VideoConsultationException(noAccessToVideoConsultationError);
		}
		if (vanSwymesEmail == null) {
			throw new VideoConsultationException(calleeNotFoundError);
		}

		StringBuilder data = new StringBuilder();

		data.append(videoConsultationDNS);
		data.append("?l=");
		data.append(user.getSwymedEmailID());
		data.append("&p=");
		data.append(user.getSwymedPassword());
		data.append("&d=");
		data.append(user.getSwymedDomain());
		data.append("&e=");
		data.append(vanSwymesEmail);

		return data.toString();
	}

	@Override
	public String callVanJitsi(Long fromuserid, Integer vanID) throws VideoConsultationException {
		UserJitsi user = userJitsiRepo.findOneJitsiMap(fromuserid);
		UserJitsi userVan = userJitsiRepo.findOneJitsiMapVan(vanID);

		if (user == null) {
			throw new VideoConsultationException(noAccessToVideoConsultationError);
		}
		if (userVan == null) {
			throw new VideoConsultationException(calleeNotFoundError);
		}

		StringBuilder data = new StringBuilder();

		data.append(jitsiDNS);
		data.append("/");
		data.append(userVan.getJitsiUserName());
		if (user.getJitsiPassword() != null) {
			data.append("/");
			data.append(userVan.getJitsiPassword());
		}

		return data.toString();
	}

	@Override
	public String logout() {
		StringBuilder data = new StringBuilder();
		data.append(videoConsultationDNS);
		data.append("?logout");
		return data.toString();
	}

}
