package com.iemr.hwc.fhir.config.fhirRestfulServer;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.narrative.DefaultThymeleafNarrativeGenerator;
import ca.uhn.fhir.narrative.INarrativeGenerator;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.*;
import ca.uhn.fhir.validation.ResultSeverityEnum;
import com.iemr.hwc.fhir.config.authInterceptor.AuthInterceptor;
import com.iemr.hwc.fhir.provider.condition.ConditionExtProvider;
import com.iemr.hwc.fhir.provider.encounter.EncounterExtProvider;
import com.iemr.hwc.fhir.provider.immunization.ImmunizationExtProvider;
import com.iemr.hwc.fhir.provider.observation.ObservationExtProvider;
import com.iemr.hwc.fhir.provider.patient.PatientExtProvider;
import org.hl7.fhir.r4.hapi.validation.FhirInstanceValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.web.cors.CorsConfiguration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/fhir/*")
public class FhirRestfulServer extends RestfulServer {
    private ApplicationContext applicationContext;

    public FhirRestfulServer(ApplicationContext context) {

        this.applicationContext = context;
    }

    @Override
    protected void initialize() throws ServletException{
        super.initialize();

        FhirContext ctx = FhirContext.forR4();
        setFhirContext(ctx);

        //Registering resource providers
        List<IResourceProvider> resourceProviders = new ArrayList<>();
        resourceProviders.add(applicationContext.getBean(PatientExtProvider.class));
        resourceProviders.add(applicationContext.getBean(EncounterExtProvider.class));
        resourceProviders.add(applicationContext.getBean(ConditionExtProvider.class));
        resourceProviders.add(applicationContext.getBean(ObservationExtProvider.class));
        resourceProviders.add(applicationContext.getBean(ImmunizationExtProvider.class));
        setResourceProviders(resourceProviders);

        //Registering Interceptors
        registerInterceptor(new ResponseHighlighterInterceptor());
        //Authentication Interceptor
        registerInterceptor(applicationContext.getBean(AuthInterceptor.class));

        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("x-fhir-starter");
        config.addAllowedHeader("Origin");
        config.addAllowedHeader("Accept");
        config.addAllowedHeader("X-Requested-With");
        config.addAllowedHeader("Content-Type");

        config.addAllowedOrigin("*");

        config.addExposedHeader("Location");
        config.addExposedHeader("Content-Location");
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        CorsInterceptor interceptor = new CorsInterceptor(config);

        //CORS interceptor
        registerInterceptor(interceptor);

        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();
        //Logging Interceptor
        registerInterceptor(loggingInterceptor);
        loggingInterceptor.setLoggerName("fhir.accesslog");
        loggingInterceptor.setMessageFormat(
                "Source[${remoteAddr}] Operation[${operationType} " +
                        "${idOrResourceName}] UA[${requestHeader.user-agent}] " +
                        "Params[${requestParameters}]"
        );

        // Creating an interceptor to validate incoming requests
        RequestValidatingInterceptor requestInterceptor = new RequestValidatingInterceptor();

        // Registering a validator module(Instance validator in this case)
        requestInterceptor.addValidatorModule(new FhirInstanceValidator());

        requestInterceptor.setFailOnSeverity(ResultSeverityEnum.ERROR);
        requestInterceptor.setAddResponseHeaderOnSeverity(ResultSeverityEnum.INFORMATION);
        requestInterceptor.setResponseHeaderValue("Validation on ${line}: ${message} ${severity}");
        requestInterceptor.setResponseHeaderValueNoIssues("No issues detected");

        //Registering the validating interceptor
        registerInterceptor(requestInterceptor);

        //Registering narrative generator interceptor
        INarrativeGenerator narrativeGen = new DefaultThymeleafNarrativeGenerator();
        getFhirContext().setNarrativeGenerator(narrativeGen);

    }


}
