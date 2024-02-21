package com.iemr.hwc.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.iemr.hwc.utils.config.ConfigProperties;
import com.iemr.hwc.utils.gateway.email.EmailService;
import com.iemr.hwc.utils.gateway.email.GenericEmailServiceImpl;
import com.iemr.hwc.utils.redis.RedisStorage;
import com.iemr.hwc.utils.sessionobject.SessionObject;
import com.iemr.hwc.utils.validator.Validator;

@Configuration
public class IEMRApplBeans {

	private @Value("${spring.redis.host}") String redisHost;
	private @Value("${spring.redis.port}") int redisPort;

	@Bean
	public Validator getVaidator() {
		Validator validator = new Validator();
		return validator;
	}

	@Bean
	public EmailService getEmailService() {
		EmailService emailService = new GenericEmailServiceImpl();
		return emailService;
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSender mailSender = new JavaMailSenderImpl();
		return mailSender;
	}

	@Bean
	public ConfigProperties configProperties() {
		return new ConfigProperties();
	}

	@Bean
	public SessionObject sessionObject() {
		return new SessionObject();
	}

	@Bean
	public RedisStorage redisStorage() {
		return new RedisStorage();
	}

	@Bean
	public LettuceConnectionFactory connectionFactory() {

		return new LettuceConnectionFactory(redisHost, redisPort);
	}

}
