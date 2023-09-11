/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.hwc.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

<<<<<<< HEAD:src/main/java/com/iemr/hwc/utils/IEMRApplBeans.java
import com.iemr.hwc.utils.config.ConfigProperties;
import com.iemr.hwc.utils.gateway.email.EmailService;
import com.iemr.hwc.utils.gateway.email.GenericEmailServiceImpl;
import com.iemr.hwc.utils.redis.RedisStorage;
import com.iemr.hwc.utils.sessionobject.SessionObject;
import com.iemr.hwc.utils.validator.Validator;
=======
import com.iemr.mmu.utils.config.ConfigProperties;
import com.iemr.mmu.utils.gateway.email.EmailService;
import com.iemr.mmu.utils.gateway.email.GenericEmailServiceImpl;
import com.iemr.mmu.utils.redis.RedisStorage;
import com.iemr.mmu.utils.sessionobject.SessionObject;
import com.iemr.mmu.utils.validator.Validator;
>>>>>>> 56c3672c3b5cf6333dad25fcfa2e0a87b696bc17:src/main/java/com/iemr/mmu/utils/IEMRApplBeans.java

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

<<<<<<< HEAD:src/main/java/com/iemr/hwc/utils/IEMRApplBeans.java
=======
	@Bean
	public CryptoUtil cryptoUtil() {
		return new CryptoUtil();
	}

>>>>>>> 56c3672c3b5cf6333dad25fcfa2e0a87b696bc17:src/main/java/com/iemr/mmu/utils/IEMRApplBeans.java
	@Bean
	public LettuceConnectionFactory connectionFactory() {
		System.out.print("Connecting to Redis " + redisHost + ":" + redisPort);

		return new LettuceConnectionFactory(redisHost, redisPort);
	}

}
