package com.iemr.hwc.fhir.provider.condition;

import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import com.iemr.hwc.fhir.model.condition.ConditionExt;
import com.iemr.hwc.fhir.service.condition.ConditionService;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConditionExtProvider implements IResourceProvider {

    @Autowired
    private ConditionService conditionService;

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return ConditionExt.class;
    }

    public ConditionExt getMockCondition(){
        ConditionExt mockCondition = new ConditionExt();
        List<CodeableConcept> category = new ArrayList<>();

        Coding code = new Coding();
        code.setSystem("http://snomed.info/sct");
        code.setCode("55607006");
        code.setDisplay("Problem");
        CodeableConcept snomedCat = new CodeableConcept(code);
        category.add(snomedCat);

        Coding code2 = new Coding();
        code2.setSystem("http://terminology.hl7.org/CodeSystem/condition-category");
        code2.setCode("problem-list-item");
        CodeableConcept snomedSubCat = new CodeableConcept(code2);
        category.add(snomedSubCat);

        mockCondition.setCategory(category);
        return mockCondition;
    }


    @Create()
    public MethodOutcome createCondition(HttpServletRequest theRequest, @ResourceParam ConditionExt conditionExt) throws Exception {

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);
        method.setResource(conditionService.addNewChiefComplaint(theRequest,conditionExt));
        return method;
    }
}
