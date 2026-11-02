package com.iemr.hwc.utils.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.rmi.ConnectIOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.exception.TMException;

@DisplayName("OutputResponse")
class OutputResponseTest {

	@Test
	@DisplayName("a fresh response reads as a generic failure until it is filled in")
	void startsAsAFailure() {
		OutputResponse response = new OutputResponse();

		assertThat(response.isSuccess()).isFalse();
		assertThat(response.getStatusCode()).isEqualTo(OutputResponse.GENERIC_FAILURE);
		assertThat(response.getStatus()).isEqualTo("FAILURE");
		assertThat(response.getErrorMessage()).isEqualTo("Failed with generic error");
	}

	@Test
	@DisplayName("a JSON object payload is embedded as an object rather than a quoted string")
	void embedsAnObjectPayload() {
		OutputResponse response = new OutputResponse();

		response.setResponse("{\"benRegID\":42}");

		assertThat(response.isSuccess()).isTrue();
		assertThat(response.getStatusCode()).isEqualTo(OutputResponse.SUCCESS);
		JSONObject json = new JSONObject(response.toString());
		assertThat(json.getJSONObject("data").getInt("benRegID")).isEqualTo(42);
	}

	@Test
	@DisplayName("a JSON array payload is embedded as an array")
	void embedsAnArrayPayload() {
		OutputResponse response = new OutputResponse();

		response.setResponse("[{\"benRegID\":42}]");

		JSONObject json = new JSONObject(response.toString());
		assertThat(json.getJSONArray("data").length()).isEqualTo(1);
	}

	@Test
	@DisplayName("a plain string payload is wrapped under a response field")
	void wrapsAPlainPayload() {
		OutputResponse response = new OutputResponse();

		response.setResponse("saved");

		JSONObject json = new JSONObject(response.toString());
		assertThat(json.getJSONObject("data").getString("response")).isEqualTo("saved");
		assertThat(response.isSuccess()).isTrue();
	}

	@Test
	@DisplayName("getData hands back the payload, and null when there is none")
	void readsThePayloadBack() {
		OutputResponse withPayload = new OutputResponse();
		withPayload.setResponse("{\"benRegID\":42}");

		assertThat(withPayload.getData()).contains("benRegID");
		assertThat(new OutputResponse().getData()).isNull();
	}

	@Test
	@DisplayName("an error code and message are reported as given")
	void reportsAnErrorCodeAndMessage() {
		OutputResponse response = new OutputResponse();

		response.setError(OutputResponse.BAD_REQUEST, "Invalid request");

		assertThat(response.getStatusCode()).isEqualTo(OutputResponse.BAD_REQUEST);
		assertThat(response.getErrorMessage()).isEqualTo("Invalid request");
		assertThat(response.getStatus()).isEqualTo("Invalid request");
		assertThat(response.isSuccess()).isFalse();
	}

	@Test
	@DisplayName("an error code, message and status are reported separately when all three are given")
	void reportsASeparateStatus() {
		OutputResponse response = new OutputResponse();

		response.setError(OutputResponse.PASSWORD_FAILURE, "wrong password", "login failed");

		assertThat(response.getStatusCode()).isEqualTo(OutputResponse.PASSWORD_FAILURE);
		assertThat(response.getErrorMessage()).isEqualTo("wrong password");
		assertThat(response.getStatus()).isEqualTo("login failed");
	}

	@Test
	@DisplayName("a login failure is reported with the user-id status code")
	void classifiesALoginFailure() {
		OutputResponse response = new OutputResponse();

		response.setError(new IEMRException("bad credentials"));

		assertThat(response.getStatusCode()).isEqualTo(OutputResponse.USERID_FAILURE);
		assertThat(response.getStatus()).isEqualTo("User login failed");
		assertThat(response.getErrorMessage()).isEqualTo("bad credentials");
	}

	@Test
	@DisplayName("an invalid input is reported with the TM status code")
	void classifiesAnInvalidInput() {
		OutputResponse response = new OutputResponse();

		response.setError(new TMException("bad input"));

		assertThat(response.getStatusCode()).isEqualTo(OutputResponse.TM_EXCEPTION);
		assertThat(response.getStatus()).isEqualTo("Invalid input");
	}

	@Test
	@DisplayName("a JSON conversion failure is reported without leaking the parser message")
	void classifiesAConversionFailure() {
		OutputResponse response = new OutputResponse();

		response.setError(new JSONException("unbalanced brace at index 7"));

		assertThat(response.getStatusCode()).isEqualTo(OutputResponse.OBJECT_FAILURE);
		assertThat(response.getStatus()).isEqualTo("Invalid object conversion");
		assertThat(response.getErrorMessage()).isEqualTo("Invalid object conversion");
	}

	@Test
	@DisplayName("an internal failure is reported with the code-exception status code")
	void classifiesAnInternalFailure() {
		for (Throwable thrown : new Throwable[] { new SQLException("db"), new ParseException("p", 0),
				new NullPointerException("npe") }) {
			OutputResponse response = new OutputResponse();

			response.setError(thrown);

			assertThat(response.getStatusCode()).as("%s", thrown.getClass().getSimpleName())
					.isEqualTo(OutputResponse.CODE_EXCEPTION);
			assertThat(response.getStatus()).contains("Failed with internal errors");
		}
	}

	@Test
	@DisplayName("a connectivity failure is reported with the environment status code")
	void classifiesAConnectivityFailure() {
		for (Throwable thrown : new Throwable[] { new IOException("io"), new ConnectIOException("connect") }) {
			OutputResponse response = new OutputResponse();

			response.setError(thrown);

			assertThat(response.getStatusCode()).as("%s", thrown.getClass().getSimpleName())
					.isEqualTo(OutputResponse.ENVIRONMENT_EXCEPTION);
			assertThat(response.getStatus()).contains("connection issues");
		}
	}

	@Test
	@DisplayName("an unrecognised failure falls back to the generic status code")
	void classifiesAnUnknownFailure() {
		OutputResponse response = new OutputResponse();

		response.setError(new IllegalStateException("something odd"));

		assertThat(response.getStatusCode()).isEqualTo(OutputResponse.GENERIC_FAILURE);
		assertThat(response.getStatus()).contains("something odd");
	}

	@Test
	@DisplayName("toString leaves out the fields that are not exposed")
	void serialisesOnlyTheExposedFields() {
		OutputResponse response = new OutputResponse();
		response.setResponse("{\"a\":1}");

		JSONObject json = new JSONObject(response.toString());

		assertThat(json.keySet()).containsExactlyInAnyOrder("data", "statusCode", "errorMessage", "status");
	}

	@Test
	@DisplayName("the serialize-nulls form keeps a null payload in the document")
	void keepsANullPayloadWhenAsked() {
		OutputResponse response = new OutputResponse();

		assertThat(response.toStringWithSerializeNulls()).contains("\"data\":null");
		assertThat(response.toStringWithSerialization()).contains("\"data\":null");
		assertThat(response.toString()).doesNotContain("\"data\"");
	}

	@Test
	@DisplayName("a success is carried out as 200")
	void mapsSuccessToOk() {
		OutputResponse response = new OutputResponse();
		response.setResponse("{\"a\":1}");

		ResponseEntity<String> entity = response.toStringWithHttpStatus();

		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("\"statusCode\":200");
	}

	@Test
	@DisplayName("a generic failure is carried out as 500, a bad request as 400 and anything else as 503")
	void mapsFailuresToTheirStatus() {
		OutputResponse generic = new OutputResponse();
		generic.setError(OutputResponse.GENERIC_FAILURE, "failed");
		assertThat(generic.toStringWithHttpStatus().getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

		OutputResponse badRequest = new OutputResponse();
		badRequest.setError(OutputResponse.BAD_REQUEST, "bad");
		assertThat(badRequest.toStringWithHttpStatus().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

		OutputResponse other = new OutputResponse();
		other.setError(OutputResponse.PREVILAGE_FAILURE, "no privilege");
		assertThat(other.toStringWithHttpStatus().getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Test
	@DisplayName("the body of the HTTP form carries the same exposed fields as toString")
	void theHttpBodyCarriesTheExposedFields() {
		OutputResponse response = new OutputResponse();
		response.setError(OutputResponse.SUCCESS, "ok");

		JSONObject body = new JSONObject(response.toStringWithHttpStatus().getBody());

		assertThat(body.keySet()).containsExactlyInAnyOrder("statusCode", "errorMessage", "status");
		assertThat(body.getInt("statusCode")).isEqualTo(OutputResponse.SUCCESS);
	}
}
