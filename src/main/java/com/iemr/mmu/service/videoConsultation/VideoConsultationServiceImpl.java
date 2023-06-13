/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.service.videoConsultation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.mmu.data.videoConsultation.UserJitsi;
import com.iemr.mmu.data.videoConsultation.VideoConsultationUser;
import com.iemr.mmu.repo.login.MasterVanRepo;
import com.iemr.mmu.repo.videoConsultation.UserJitsiRepo;
import com.iemr.mmu.repo.videoConsultation.VideoConsultationUserRepo;
import com.iemr.mmu.utils.config.ConfigProperties;
import com.iemr.mmu.utils.exception.VideoConsultationException;

@Service
public class VideoConsultationServiceImpl implements VideoConsultationService {

	// swymed://dnsname?l=mylogin&p=mypassword&d=mydomain&c=callnumber&m=1
	private String videoConsultationDNS = ConfigProperties.getPropertyByName("swymed_dnsname");

	private String jitsi_dnsname = ConfigProperties.getPropertyByName("jitsi_dnsname");

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
			throw new VideoConsultationException("User doesnt have access to video consultation");
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
			throw new VideoConsultationException("User doesnt have access to video consultation");
		}
		if (touser == null) {
			throw new VideoConsultationException("Callee couldn't be found. Please call manually");
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
			throw new VideoConsultationException("User doesnt have access to video consultation");
		}
		if (touser == null) {
			throw new VideoConsultationException("Callee couldn't be found. Please call manually");
		}

		StringBuilder data = new StringBuilder();

		data.append(jitsi_dnsname);
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
			throw new VideoConsultationException("User doesnt have access to video consultation");
		}
		if (vanSwymesEmail == null) {
			throw new VideoConsultationException("Callee couldn't be found. Please call manually");
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
			throw new VideoConsultationException("User doesnt have access to video consultation");
		}
		if (userVan == null) {
			throw new VideoConsultationException("Callee couldn't be found. Please call manually");
		}

		StringBuilder data = new StringBuilder();

		data.append(jitsi_dnsname);
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
