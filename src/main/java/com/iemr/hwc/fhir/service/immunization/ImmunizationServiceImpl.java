package com.iemr.hwc.fhir.service.immunization;

import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.fhir.dto.covidVaccineStatus.CovidVaccineStatusDTO;
import com.iemr.hwc.fhir.dto.mandatoryFieldsDTO.MandatoryFieldsDTO;
import com.iemr.hwc.fhir.model.immunization.ImmunizationExt;
import com.iemr.hwc.fhir.utils.mapper.MapperUtils;
import com.iemr.hwc.fhir.utils.validation.ImmunizationValidation;
import com.iemr.hwc.utils.CookieUtil;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.MediaType;

@Service
public class ImmunizationServiceImpl implements ImmunizationService {

	public MapperUtils mapper = Mappers.getMapper(MapperUtils.class);

	@Value("${saveCovidVaccineDetailsURL}")
	private String saveCovidVaccineDetailsURL;

	@Autowired
	private ImmunizationValidation validation;
	@Autowired
	private CookieUtil cookieUtil;

	Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Override
	public ImmunizationExt addOrUpdateImmunization(HttpServletRequest theRequest, ImmunizationExt immunizationExt)
			throws Exception {
		// Validating the resource for mandatory fields
		validation.immunizationResourceValidator(immunizationExt);

		// Todo - Currently implemented considering benRegID coming in payload in
		// 'patient'.
		MandatoryFieldsDTO mandatoryFieldsDTO = mapper.immunizationResourceToMandatoryFieldsDTO(immunizationExt);

		CovidVaccineStatusDTO covidVaccineStatusDTO = mapper
				.immunizationResourceToCovidVaccineStatusDTO(immunizationExt, mandatoryFieldsDTO);

		String covidVaccineStatus = new GsonBuilder().serializeNulls().create().toJson(covidVaccineStatusDTO);

		RestTemplate restTemplate = new RestTemplate();
		HttpServletRequest requestHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String jwtTokenFromCookie = cookieUtil.getJwtTokenFromCookie(requestHeader);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON + ";charset=utf-8");
		headers.add("AUTHORIZATION", theRequest.getHeader("Authorization"));
		headers.add("Cookie", "Jwttoken=" + jwtTokenFromCookie);
		HttpEntity<Object> request = new HttpEntity<Object>(covidVaccineStatus, headers);
		ResponseEntity<String> response = restTemplate.exchange(saveCovidVaccineDetailsURL, HttpMethod.POST, request,
				String.class);

		String responseStr = response.getBody();
		JsonObject responseJson = new JsonParser().parse(responseStr).getAsJsonObject();

		String covidVSID = "";
		if (response.getStatusCodeValue() == 200 && response.hasBody()
				&& responseJson.get("statusCode").getAsInt() == 200) {

			covidVSID = responseJson.getAsJsonObject("data").get("covidVSID").getAsString();

		} else {
			logger.error("Encountered error while trying to save/update covid vaccine details.Error - "
					+ responseJson.get("errorMessage"));
			throw new InternalErrorException(
					"Encountered error while trying to save/update covid vaccine details.Error - "
							+ responseJson.get("errorMessage"));
		}

		immunizationExt.setId(covidVSID);

		return immunizationExt;
	}
}
