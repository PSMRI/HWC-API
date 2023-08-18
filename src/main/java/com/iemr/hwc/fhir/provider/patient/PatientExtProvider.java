package com.iemr.hwc.fhir.provider.patient;

import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import com.iemr.hwc.fhir.dto.beneficiary.identityDTO.*;
import com.iemr.hwc.fhir.model.patient.PatientExt;
import com.iemr.hwc.fhir.service.patient.PatientService;
import com.iemr.hwc.service.registrar.RegistrarServiceImpl;
import io.swagger.models.Contact;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.StringParam;

@Component
public class PatientExtProvider implements IResourceProvider {

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return PatientExt.class;
    }

    @Autowired
    private PatientService patientService;

    @Autowired
    private RegistrarServiceImpl registrarServiceImpl;

    @Create()
    public MethodOutcome createPatient(HttpServletRequest theRequest, @ResourceParam PatientExt patientExt) {

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);
        method.setResource(patientService.createNewPatient(theRequest,patientExt));
        return method;
    }

    @Search()
    public List<PatientExt> findPatientsByDistrictAndLastModifDate(HttpServletRequest theRequest, @RequiredParam(name = Patient.SP_ADDRESS) StringParam blockID, @RequiredParam(name = "lastModif") DateParam lastModifyDate) {
        List<PatientExt> listRes = new ArrayList<>();
        List<Patient> retVal = new ArrayList<>();
        try {
            String authorization = theRequest.getHeader("Authorization");
            String response = registrarServiceImpl.getBeneficiaryByBlockIDAndLastModDate(blockID.getValue(), lastModifyDate.getValue(), authorization);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = new JSONArray(jsonObject.getJSONObject("response").getString("data"));
            Type typeOfSrc = new TypeToken<List<BeneficiariesDTOSearch>>() {}.getType();
            Gson gson = new Gson();
            List<BeneficiariesDTOSearch> listBeneficiaries = gson.fromJson(jsonArray.toString(), typeOfSrc);
            for (BeneficiariesDTOSearch benef:listBeneficiaries) {
                HumanName human = new HumanName();
                PatientExt patient = new PatientExt();
                patient.setId(1L+"");
                patient.setFatherName(new StringType(benef.getBeneficiaryDetails().getFatherName()));
                patient.setSpouseName(new StringType(benef.getBeneficiaryDetails().getSpouseName()));
                patient.setAgeAtMarriage(new StringType());
                if(benef.getAbhaDetails() != null && benef.getAbhaDetails().size()>0 && benef.getAbhaDetails().get(0) != null){
                    Coding coding = new Coding();
                    coding.setCode(benef.getAbhaDetails().get(0).getHealthID());
                    coding.setDisplay(benef.getAbhaDetails().get(0).getHealthIDNumber());
                    patient.setAbhaGenerationMode(coding);
                    patient.setProviderServiceMapId(new StringType(benef.getBeneficiaryServiceMap().get(0).getBenServiceMapID()+""));
                }

                patient.setVanID(new StringType(benef.getBeneficiaryDetails().getVanID()+""));
                patient.setParkingPlaceID(new StringType(benef.getBeneficiaryDetails().getParkingPlaceID()+""));
                patient.setCreatedBy(new StringType(benef.getCreatedBy()));
                Coding codingState = new Coding();
                codingState.setCode(benef.getCurrentAddress().getStateId()+"");
                codingState.setDisplay(benef.getCurrentAddress().getState());
                patient.setState(codingState);
                Coding codingDistrict = new Coding();
                codingDistrict.setCode(benef.getCurrentAddress().getDistrictId()+"");
                codingDistrict.setDisplay(benef.getCurrentAddress().getDistrict());
                patient.setDistrict(codingDistrict);
                Coding codingBlock = new Coding();
                codingBlock.setCode(benef.getCurrentAddress().getVillageId()+"");
                codingBlock.setDisplay(benef.getCurrentAddress().getVillage());
                patient.setBlock(codingBlock);
                Coding codingDistrictBranch = new Coding();
                codingDistrictBranch.setCode(benef.getCurrentAddress().getSubDistrictId()+"");
                codingDistrictBranch.setDisplay(benef.getCurrentAddress().getSubDistrict());
                patient.setDistrictBranch(codingDistrictBranch);
                Coding codingReligion = new Coding();
                codingReligion.setCode(benef.getBeneficiaryDetails().getReligionId()+"");
                codingReligion.setDisplay(benef.getBeneficiaryDetails().getReligion());
                patient.setReligion(codingReligion);
                Coding codingCommunity = new Coding();
                codingCommunity.setCode(benef.getBeneficiaryDetails().getCommunityId()+"");
                codingCommunity.setDisplay(benef.getBeneficiaryDetails().getCommunity());
                patient.setCommunity(codingCommunity);

                if(benef.getBeneficiaryIdentites()!=null && benef.getBeneficiaryIdentites().size()>0 && benef.getBeneficiaryIdentites().get(0) !=null ){
                    Coding codingGovIdentityType = new Coding();
                    codingGovIdentityType.setCode(benef.getBeneficiaryIdentites().get(0).getIdentityTypeId()+"");
                    codingGovIdentityType.setDisplay(benef.getBeneficiaryIdentites().get(0).getIdentityType());
                    patient.setGovtIdentityType(codingGovIdentityType);
                    patient.setGovtIdentityNo(new StringType(benef.getBeneficiaryIdentites().get(0).getIdentityNo()));
                    Coding codingGovtHealthProgramType = new Coding();
                    codingGovtHealthProgramType.setCode(benef.getBeneficiaryIdentites().get(0).getIdentityTypeId()+"");
                    codingGovtHealthProgramType.setDisplay(benef.getBeneficiaryIdentites().get(0).getIdentityType());
//                patient.setGovtHealthProgramType(codingGovtHealthProgramType);
                }
//
//                patient.setGovtHealthProgramId(benef.);

                List<HumanName> listName = new ArrayList<>();
                HumanName humanName = new HumanName();
                humanName.setFamily(benef.getBeneficiaryDetails().getLastName());
                List<StringType> listGiven = new ArrayList<>();
                listGiven.add(new StringType(benef.getBeneficiaryDetails().getFirstName()));
                listGiven.add(new StringType(benef.getBeneficiaryDetails().getMiddleName()));
                humanName.setGiven(listGiven);
                listName.add(humanName);
                patient.setName(listName);

                List<ContactPoint> listContactPoint = new ArrayList<>();
                ContactPoint contact = new ContactPoint();
                contact.setSystem(ContactPoint.ContactPointSystem.PHONE);
                contact.setValue(benef.getPreferredPhoneNum());
                listContactPoint.add(contact);
                patient.setTelecom(listContactPoint);

//                Enumerations.AdministrativeGender.
//                patient.setGender(Enumerations.AdministrativeGender.MALE);
//                patient.setBirthDate(new Date())
                CodeableConcept concept = new CodeableConcept();
                List<Coding> listForConcept = new ArrayList<>();
                Coding coding1 = new Coding();
                coding1.setCode(benef.getBeneficiaryDetails().getMaritalStatusId()+"");
                coding1.setDisplay(benef.getBeneficiaryDetails().getMaritalStatus());
                listForConcept.add(coding1);
                concept.setCoding(listForConcept);
                concept.setText(benef.getBeneficiaryDetails().getMaritalStatus());
                patient.setMaritalStatus(concept);

                listRes.add(patient);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return listRes;
    }
}