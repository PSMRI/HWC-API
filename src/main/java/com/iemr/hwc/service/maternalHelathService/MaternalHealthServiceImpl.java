package com.iemr.hwc.service.maternalHelathService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemr.hwc.data.anc.ANCVisit;
import com.iemr.hwc.data.anc.ANCVisitDTO;
import com.iemr.hwc.data.anc.AncCare;
import com.iemr.hwc.data.nurse.BeneficiaryVisitDetail;
import com.iemr.hwc.data.pncMother.PNCVisit;
import com.iemr.hwc.data.pncMother.PNCVisitDTO;
import com.iemr.hwc.data.pregnantWomen.PregnantWomanDTO;
import com.iemr.hwc.data.pregnantWomen.PregnantWomanRegister;
import com.iemr.hwc.repo.ancVisit.ANCVisitRepo;
import com.iemr.hwc.repo.ancVisit.AncCareRepo;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.pnc.PNCCareRepo;
import com.iemr.hwc.repo.pncRepo.PNCVisitRepo;
import com.iemr.hwc.repo.pragnemtWomen.PregnantWomanRegisterRepo;
import com.iemr.hwc.utils.JwtUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MaternalHealthServiceImpl implements MaternalHealthService {

    private final Logger logger = LoggerFactory.getLogger(MaternalHealthServiceImpl.class);

    @Autowired
    PregnantWomanRegisterRepo pregnantWomanRegisterRepo;

    @Autowired
    private ANCVisitRepo ancVisitRepo;

    @Autowired
    private AncCareRepo ancCareRepo;


    @Autowired
    private PNCCareRepo pncCareRepo;

    @Autowired
    private BenVisitDetailRepo benVisitDetailsRepo;



    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PNCVisitRepo pncVisitRepo;


    ObjectMapper mapper = new ObjectMapper();

    ModelMapper modelMapper = new ModelMapper();



    public static final List<String> PNC_PERIODS =
            Arrays.asList("1st Day", "3rd Day", "7th Day", "14th Day", "21st Day", "28th Day", "42nd Day");

    @Override
    public List<ANCVisitDTO> getANCVisits(String userName) {
        try {
            List<ANCVisit> ancVisits = ancVisitRepo.getANCForPW(userName);
            return ancVisits.stream()
                    .map(anc -> mapper.convertValue(anc, ANCVisitDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public String saveANCVisit(List<ANCVisitDTO> ancVisitDTOs,Integer userId) {
        try {

            logger.info("ANC save process started. Total records: {}", ancVisitDTOs.size());

            List<ANCVisit> ancList = new ArrayList<>();
            List<AncCare> ancCareList = new ArrayList<>();

            ancVisitDTOs.forEach(it -> {

                logger.info("Processing Beneficiary Id: {} ANC Visit: {}", it.getBenId(), it.getAncVisit());

                ANCVisit ancVisit =
                        ancVisitRepo.findANCVisitByBenIdAndAncVisitAndIsActive(it.getBenId(), it.getAncVisit(), true);

                logger.info("ANC visit fetch completed");

                if (ancVisit != null) {

                    logger.info("Existing ANC visit found for BenId: {}", it.getBenId());

                    Long id = ancVisit.getId();
                    modelMapper.map(it, ancVisit);
                    ancVisit.setId(id);

                } else {

                    logger.info("New ANC visit will be created for BenId: {}", it.getBenId());

                    ancVisit = new ANCVisit();
                    modelMapper.map(it, ancVisit);
                    ancVisit.setId(null);

                    Long benRegId = it.getBenRedId();

                    logger.info("Beneficiary Reg Id fetched: {}", benRegId);

                    // Saving data in BenVisitDetails table
                    PregnantWomanRegister pwr = pregnantWomanRegisterRepo
                            .findPregnantWomanRegisterByBenIdAndIsActive(it.getBenId(), true);

                    logger.info("PregnantWomanRegister fetched");

                    BeneficiaryVisitDetail benVisitDetail = new BeneficiaryVisitDetail();
                    modelMapper.map(it, benVisitDetail);

                    benVisitDetail.setBeneficiaryRegID(benRegId);
                    benVisitDetail.setVisitCategory("ANC");
                    benVisitDetail.setVisitReason("Follow Up");
                    benVisitDetail.setPregnancyStatus("Yes");
                    benVisitDetail.setProcessed("N");
                    benVisitDetail.setModifiedBy(it.getUpdatedBy());
                    benVisitDetail.setLastModDate(it.getUpdatedDate());
                    benVisitDetail.setProviderServiceMapID(it.getProviderServiceMapID());

                    logger.info("Saving BenVisitDetail");

                    benVisitDetail = benVisitDetailsRepo.save(benVisitDetail);

                    logger.info("BenVisitDetail saved with id: {}", benVisitDetail.getBenVisitID());

                    // Saving Data in AncCare table
                    AncCare ancCare = new AncCare();
                    modelMapper.map(it, ancCare);

                    ancCare.setBenVisitId(benVisitDetail.getBenVisitID());
                    ancCare.setBeneficiaryRegId(benRegId);

                    if (pwr != null) {

                        logger.info("LMP found calculating EDD");

                        ancCare.setLastMenstrualPeriodLmp(pwr.getLmpDate());

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(pwr.getLmpDate());
                        cal.add(Calendar.DAY_OF_WEEK, 280);

                        ancCare.setExpectedDateofDelivery(new Timestamp(cal.getTime().getTime()));
                    }

                    ancCare.setTrimesterNumber(it.getAncVisit().shortValue());
                    ancCare.setModifiedBy(it.getUpdatedBy());
                    ancCare.setLastModDate(it.getUpdatedDate());
                    ancCare.setProcessed("N");

                    ancCareList.add(ancCare);

                    logger.info("AncCare added to list");
                }

                ancList.add(ancVisit);
                logger.info("ANCVisit added to list");

            });

            logger.info("Saving ANCVisit list");

            ancVisitRepo.saveAll(ancList);

            logger.info("ANCVisit saved successfully");

            logger.info("Saving AncCare list");

            ancCareRepo.saveAll(ancCareList);

            logger.info("AncCare saved successfully");

            logger.info("ANC visit details saved");

            return "no of anc details saved: " + ancList.size();

        } catch (Exception e) {

            logger.error("Saving ANC visit details failed with error : {}", e.getMessage());
            logger.error("Full exception : ", e);

        }
        return null;
    }

    @Override
    public String registerPregnantWoman(List<PregnantWomanDTO> pregnantWomanDTOs) {

        try {
            List<PregnantWomanRegister> pwrList = new ArrayList<>();
            pregnantWomanDTOs.forEach(it -> {
                PregnantWomanRegister pwr =
                        pregnantWomanRegisterRepo.findPregnantWomanRegisterByBenIdAndIsActive(it.getBenId(), true);

                if (pwr != null) {
                    Long id = pwr.getId();
                    modelMapper.map(it, pwr);
                    pwr.setId(id);
                } else {
                    pwr = new PregnantWomanRegister();
                    modelMapper.map(it, pwr);
                    pwr.setId(null);
                }
                pwrList.add(pwr);
            });
            pregnantWomanRegisterRepo.saveAll(pwrList);

            logger.info(pwrList.size() + " Pregnant Woman details saved");
            return "no of pwr details saved: " + pwrList.size();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<PregnantWomanDTO> getPregnantWoman(String userName) {
        try {
            List<PregnantWomanRegister> pregnantWomanRegisterList =
                    pregnantWomanRegisterRepo.getPWRWithBen(userName);

            return pregnantWomanRegisterList.stream()
                    .map(pregnantWomanRegister -> mapper.convertValue(pregnantWomanRegister, PregnantWomanDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<PNCVisitDTO> getPNCVisits(String userName) {
        try {
            List<PNCVisit> pncVisits = pncVisitRepo.getPNCForPW(userName);
            return pncVisits.stream()
                    .map(pnc -> mapper.convertValue(pnc, PNCVisitDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public String savePNCVisit(List<PNCVisitDTO> pncVisitDTOs) {
        try {
            List<PNCVisit> pncList = new ArrayList<>();
            pncVisitDTOs.forEach(it -> {
                PNCVisit pncVisit =
                        pncVisitRepo.findPNCVisitByBenIdAndPncPeriodAndIsActive(it.getBenId(), it.getPncPeriod(), true);

                if (pncVisit != null) {
                    Long id = pncVisit.getId();
                    modelMapper.map(it, pncVisit);
                    pncVisit.setId(id);
                } else {
                    pncVisit = new PNCVisit();
                    modelMapper.map(it, pncVisit);
                    pncVisit.setId(null);
                }
                pncList.add(pncVisit);
            });
            pncVisitRepo.saveAll(pncList);
            logger.info("PNC visit details saved");
            return "no of pnc details saved: " + pncList.size();
        } catch (Exception e) {
            logger.info("Saving PNC visit details failed with error : " + e.getMessage());
        }
        return null;
    }

}
