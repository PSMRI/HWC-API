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
package com.iemr.hwc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

import com.iemr.hwc.data.login.Users;
import com.iemr.hwc.fhir.config.fhirRestfulServer.FhirRestfulServer;
import com.iemr.hwc.utils.IEMRApplBeans;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private ApplicationContext context;

	@Bean
	public IEMRApplBeans instantiateBeans() {
		return new IEMRApplBeans();
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	// Registering new fhir servlet for CHO mobile app
	@Bean
	public ServletRegistrationBean ServletRegistrationBean() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new FhirRestfulServer(context), "/fhir/*");
		registration.setName("FhirServlet");
		return registration;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);

		// Use StringRedisSerializer for keys (userId)
		template.setKeySerializer(new StringRedisSerializer());

		// Use Jackson2JsonRedisSerializer for values (Users objects)
		Jackson2JsonRedisSerializer<Users> serializer = new Jackson2JsonRedisSerializer<>(Users.class);
		template.setValueSerializer(serializer);

		return template;
	}
}
