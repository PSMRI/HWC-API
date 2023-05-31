package com.iemr.mmu.controller.videoConsultation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.service.videoConsultation.VideoConsultationService;
import com.iemr.mmu.utils.response.OutputResponse;

@RestController
@RequestMapping(value = "/videoConsultation", headers = "Authorization")
public class VideoConsultationController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private VideoConsultationService videoConsultationService;

	@CrossOrigin()
	@RequestMapping(value = "/login/{userID}", headers = "Authorization", method = { RequestMethod.GET }, produces = {
			"application/json" })
	public String login(@PathVariable("userID") Long userID) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = videoConsultationService.login(userID);

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toString();

	}

	@CrossOrigin()
	@RequestMapping(value = "/call/{fromUserID}/{touserID}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String call(@PathVariable("fromUserID") Long fromUserID, @PathVariable("toUserID") Long toUserID) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = videoConsultationService.callUser(fromUserID, toUserID);

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toString();

	}

	@CrossOrigin()
	@RequestMapping(value = "/call/{fromUserID}/{toUserID}/{type}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String CallSwymedAndJitsi(@PathVariable("fromUserID") Long fromUserID,
			@PathVariable("toUserID") Long toUserID, @PathVariable("type") String Type) {

		OutputResponse response = new OutputResponse();

		try {
			String createdData = null;
			if (Type.equalsIgnoreCase("Swymed")) {
				createdData = videoConsultationService.callUser(fromUserID, toUserID);
			} else {
				createdData = videoConsultationService.callUserjitsi(fromUserID, toUserID);
			}

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toString();

	}

	@CrossOrigin()
	@RequestMapping(value = "/callvan/{fromUserID}/{vanID}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String callvan(@PathVariable("fromUserID") Long fromUserID, @PathVariable("vanID") Integer vanID) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = videoConsultationService.callVan(fromUserID, vanID);

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toString();

	}

	@CrossOrigin()
	@RequestMapping(value = "/callvan/{fromUserID}/{vanID}/{type}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String CallVanSwymedAndJitsi(@PathVariable("fromUserID") Long fromUserID,
			@PathVariable("vanID") Integer vanID, @PathVariable("type") String type) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = null;
			if (type.equalsIgnoreCase("Swymed")) {
				createdData = videoConsultationService.callVan(fromUserID, vanID);
			} else {
				createdData = videoConsultationService.callVanJitsi(fromUserID, vanID);
			}

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toString();

	}

	@CrossOrigin()
	@RequestMapping(value = "/logout", headers = "Authorization", method = { RequestMethod.GET }, produces = {
			"application/json" })
	public String logout() {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = videoConsultationService.logout();

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		return response.toString();

	}

}
