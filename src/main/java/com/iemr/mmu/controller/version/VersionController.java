/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.controller.version;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.utils.response.OutputResponse;

@RestController
public class VersionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@CrossOrigin()
	@RequestMapping(value = "/version", method = { RequestMethod.GET })
	public String versionInformation() {
		OutputResponse output = new OutputResponse();
		try {
			logger.info("version Controller Start");
			output.setResponse(readGitProperties());
	    } catch (Exception e) {
	    	output.setError(e);
	    }
		
		logger.info("version Controller End");
		return output.toString();
	}
	private String readGitProperties() throws Exception {
	    ClassLoader classLoader = getClass().getClassLoader();
	    InputStream inputStream = classLoader.getResourceAsStream("git.properties");
	    
	        return readFromInputStream(inputStream);
	}
	private String readFromInputStream(InputStream inputStream)
	throws IOException {
	    StringBuilder resultStringBuilder = new StringBuilder();
	    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            resultStringBuilder.append(line).append("\n");
	        }
	    }
	    return resultStringBuilder.toString();
	}
}
