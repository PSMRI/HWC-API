package com.iemr.mmu.controller.swymedcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.service.swymed.SwymedService;
import com.iemr.mmu.utils.response.OutputResponse;

@RestController
@RequestMapping(value = "/swymed", headers = "Authorization")
public class SwymedController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private SwymedService swymedService;

	@CrossOrigin()
	@RequestMapping(value = "/login/{userID}", headers = "Authorization", method = { RequestMethod.GET }, produces = {
			"application/json" })
	public String login(@PathVariable("userID") Long userID) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = swymedService.login(userID);

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		/**
		 * sending the response...
		 */
		return response.toString();

	}

	@CrossOrigin()
	@RequestMapping(value = "/call/{fromuserID}/{touserID}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String call(@PathVariable("fromuserID") Long fromuserID, @PathVariable("touserID") Long touserID) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = swymedService.callUser(fromuserID, touserID);

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		/**
		 * sending the response...
		 */
		return response.toString();

	}

	@CrossOrigin()
	@RequestMapping(value = "/call/{fromuserID}/{touserID}/{type}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String CallSwymedAndJitsi(@PathVariable("fromuserID") Long fromuserID,
			@PathVariable("touserID") Long touserID, @PathVariable("type") String Type) {

		OutputResponse response = new OutputResponse();

		try {
			String createdData = null;
			if (Type.equalsIgnoreCase("Swymed")) {
				createdData = swymedService.callUser(fromuserID, touserID);
			} else {
				createdData = swymedService.callUserjitsi(fromuserID, touserID);
			}

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		/**
		 * sending the response...
		 */
		return response.toString();

	}

	@CrossOrigin()
	@RequestMapping(value = "/callvan/{fromuserID}/{vanID}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String callvan(@PathVariable("fromuserID") Long fromuserID, @PathVariable("vanID") Integer vanID) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = swymedService.callVan(fromuserID, vanID);

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		/**
		 * sending the response...
		 */
		return response.toString();

	}

	@CrossOrigin()
	@RequestMapping(value = "/callvan/{fromuserID}/{vanID}/{type}", headers = "Authorization", method = {
			RequestMethod.GET }, produces = { "application/json" })
	public String CallVanSwymedAndJitsi(@PathVariable("fromuserID") Long fromuserID,
			@PathVariable("vanID") Integer vanID, @PathVariable("type") String Type) {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = null;
			if (Type.equalsIgnoreCase("Swymed")) {
				createdData = swymedService.callVan(fromuserID, vanID);
			} else {
				createdData = swymedService.callVanJitsi(fromuserID, vanID);
			}

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		/**
		 * sending the response...
		 */
		return response.toString();

	}

	@CrossOrigin()
	@RequestMapping(value = "/logout", headers = "Authorization", method = { RequestMethod.GET }, produces = {
			"application/json" })
	public String logout() {

		OutputResponse response = new OutputResponse();

		try {

			String createdData = swymedService.logout();

			response.setResponse(createdData.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setError(e);

		}
		/**
		 * sending the response...
		 */
		return response.toString();

	}

}
