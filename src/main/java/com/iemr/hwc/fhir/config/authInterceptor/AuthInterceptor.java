package com.iemr.hwc.fhir.config.authInterceptor;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;
import com.iemr.hwc.utils.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Interceptor
@Component
public class AuthInterceptor {

    @Autowired
    private Validator validator;

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Hook(Pointcut.SERVER_INCOMING_REQUEST_POST_PROCESSED)
    public boolean incomingFhirRequestInterceptor(
            RequestDetails theRequestDetails, HttpServletRequest theRequest, HttpServletResponse theResponse
    ) throws Exception{
        try {
            logger.debug("Intercepting the request made to fhir servlet");
            String authorization = theRequest.getHeader("Authorization");
            logger.debug("RequestURI::" + theRequest.getRequestURI() + " || Authorization ::" + authorization
                    + " || method :: " + theRequest.getMethod());
            if (!theRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
                String remoteAddress = theRequest.getHeader("X-FORWARDED-FOR");
                if (remoteAddress == null || remoteAddress.trim().isEmpty()) {
                    remoteAddress = theRequest.getRemoteAddr();
                }
                //Redirecting to already built validator service to authenticate
                validator.checkKeyExists(authorization, remoteAddress);
                theResponse.addHeader("Access-Control-Allow-Origin", "*");
            }
        }
        catch(Exception e){
            logger.error("Error while authenticating request to fhir server");
            throw new AuthenticationException();
        }
        return true;
    }

}
