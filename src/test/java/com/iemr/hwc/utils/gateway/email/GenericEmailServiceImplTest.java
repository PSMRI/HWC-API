package com.iemr.hwc.utils.gateway.email;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
@DisplayName("GenericEmailServiceImpl")
class GenericEmailServiceImplTest {

	@Mock
	private JavaMailSender javaMailSender;

	private GenericEmailServiceImpl emailService;

	@BeforeEach
	void setUp() {
		emailService = new GenericEmailServiceImpl();
		emailService.setJavaMailSender(javaMailSender);
	}

	private static String request(String to) {
		return new JSONObject().put("to", to).put("from", "hwc@example.org").put("subject", "ANC reminder")
				.put("message", "Please visit the centre").toString();
	}

	private SimpleMailMessage sentMessage() {
		ArgumentCaptor<SimpleMailMessage> sent = ArgumentCaptor.forClass(SimpleMailMessage.class);
		verify(javaMailSender).send(sent.capture());
		return sent.getValue();
	}

	@Test
	@DisplayName("sends the message described by the request")
	void sendsTheMessage() {
		emailService.sendEmail(request("beneficiary@example.org"), "a-template");

		SimpleMailMessage message = sentMessage();
		assertThat(message.getTo()).containsExactly("beneficiary@example.org");
		assertThat(message.getFrom()).isEqualTo("hwc@example.org");
		assertThat(message.getSubject()).isEqualTo("ANC reminder");
		assertThat(message.getText()).isEqualTo("Please visit the centre");
	}

	@Test
	@DisplayName("splits a semicolon-separated recipient list into separate addresses")
	void splitsTheRecipientList() {
		emailService.sendEmail(request("one@example.org;two@example.org"));

		assertThat(sentMessage().getTo()).containsExactly("one@example.org", "two@example.org");
	}

	@Test
	@DisplayName("sends to a single recipient given without a separator")
	void sendsToASingleRecipient() {
		emailService.sendEmail(request("one@example.org"));

		assertThat(sentMessage().getTo()).containsExactly("one@example.org");
	}

	@Test
	@DisplayName("reports a request that is missing a field rather than sending a half-built message")
	void reportsAnIncompleteRequest() {
		String incomplete = new JSONObject().put("to", "beneficiary@example.org").toString();

		assertThatThrownBy(() -> emailService.sendEmail(incomplete)).isInstanceOf(JSONException.class);

		verifyNoInteractions(javaMailSender);
	}

	@Test
	@DisplayName("attachment sending is not implemented yet and does nothing")
	void attachmentSendingIsANoOp() {
		assertThatCode(() -> emailService.sendEmailWithAttachment(request("a@example.org"), "t"))
				.doesNotThrowAnyException();

		verifyNoInteractions(javaMailSender);
	}
}
