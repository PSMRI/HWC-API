package com.iemr.hwc.fhir.service.condition;

import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.hwc.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.hwc.data.quickConsultation.BenChiefComplaint;
import com.iemr.hwc.data.snomedct.SCTDescription;
import com.iemr.hwc.fhir.dto.mandatoryFieldsDTO.MandatoryFieldsDTO;
import com.iemr.hwc.fhir.dto.visitDetailsMain.chiefComplaints.ChiefComplaintsDTO;
import com.iemr.hwc.fhir.model.condition.ConditionExt;
import com.iemr.hwc.fhir.utils.mapper.MapperUtils;
import com.iemr.hwc.fhir.utils.validation.ConditionValidation;
import com.iemr.hwc.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.hwc.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.hwc.service.snomedct.SnomedService;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.mapper.InputMapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Service
public class ConditionServiceImpl implements ConditionService {

    public MapperUtils mapper = Mappers.getMapper(MapperUtils.class);

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private CommonNurseServiceImpl commonNurseServiceImpl;

    @Autowired
    private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

    @Autowired
    private SnomedService snomedService;

    @Autowired
    private ConditionValidation validation;

    @Override
    public ConditionExt addNewChiefComplaint(HttpServletRequest theRequest, ConditionExt conditionExt) throws Exception {

        //Validating the resource for mandatory fields
        validation.conditionResourceValidator(conditionExt);

        //Todo - Currently implemented considering all relevant IDs(beneficiaryID, benRegID, benFlowID) are coming in payload.
        //Todo - If not, might need to write new APIs to fetch necessary IDs through some sort of logic. And then use those further.
        MandatoryFieldsDTO mandatoryFieldsDTO = mapper.conditionResourceToMandatoryFieldsDTO(conditionExt);

        BeneficiaryFlowStatus beneficiaryFlowStatus = beneficiaryFlowStatusRepo.getBenDetailsForLeftSidePanel(Long.parseLong(mandatoryFieldsDTO.getBenFlowID()));

        if (beneficiaryFlowStatus !=null ) {
            mandatoryFieldsDTO.setBenVisitID(beneficiaryFlowStatus.getBenVisitID().toString());
            mandatoryFieldsDTO.setVisitCode(beneficiaryFlowStatus.getBenVisitCode().toString());
        }
        else {
            logger.error("No beneficiary flow status record found for the provided benFlowID");
            throw new ResourceNotFoundException("No record found for given benFlowID");
        }

        String conceptID = null;
        SCTDescription sctDescription = snomedService.findSnomedCTRecordFromTerm(conditionExt.getCode().getCodingFirstRep().getDisplay());
        if (sctDescription != null){
            conceptID = sctDescription.getConceptID();
        }

        ChiefComplaintsDTO chiefComplaintsDTO = mapper.conditionResourceToChiefComplaintDTO(conditionExt, mandatoryFieldsDTO , conceptID);


        String chiefComplaintGson = new GsonBuilder().serializeNulls().create().toJson(chiefComplaintsDTO);
        JsonObject chiefComplaintJson = new JsonParser().parse(chiefComplaintGson).getAsJsonObject();

        try {
            BenChiefComplaint benChiefComplaint = InputMapper.gson()
                    .fromJson(chiefComplaintJson, BenChiefComplaint.class);
            List<BenChiefComplaint> benChiefComplaintList = Arrays.asList(benChiefComplaint);

            commonNurseServiceImpl.saveBenChiefComplaints(benChiefComplaintList);
        }catch (IEMRException e){
            logger.error("Encountered custom exception - IEMRException while trying to map chiefComplaint Json with BenChiefComplaint class using Input Mapper " + e);
            throw new InternalErrorException("Error mapping json to BenChiefComplaint class " + e);
        }

        return conditionExt;
    }
}
