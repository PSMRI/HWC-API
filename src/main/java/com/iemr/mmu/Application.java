/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.iemr.mmu.utils.IEMRApplBeans;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public IEMRApplBeans instantiateBeans() {
		return new IEMRApplBeans();
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
