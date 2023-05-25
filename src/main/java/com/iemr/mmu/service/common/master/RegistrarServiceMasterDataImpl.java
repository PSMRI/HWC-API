package com.iemr.mmu.service.common.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iemr.mmu.data.benFlowStatus.BeneficiaryFlowStatus;
import com.iemr.mmu.data.masterdata.registrar.AgeUnit;
import com.iemr.mmu.data.masterdata.registrar.CommunityMaster;
import com.iemr.mmu.data.masterdata.registrar.GenderMaster;
import com.iemr.mmu.data.masterdata.registrar.GovIdEntityType;
import com.iemr.mmu.data.masterdata.registrar.IncomeStatusMaster;
import com.iemr.mmu.data.masterdata.registrar.LiteracyStatus;
import com.iemr.mmu.data.masterdata.registrar.MaritalStatusMaster;
import com.iemr.mmu.data.masterdata.registrar.OccupationMaster;
import com.iemr.mmu.data.masterdata.registrar.QualificationMaster;
import com.iemr.mmu.data.masterdata.registrar.RelationshipMaster;
import com.iemr.mmu.data.masterdata.registrar.ReligionMaster;
import com.iemr.mmu.data.registrar.BeneficiaryData;
import com.iemr.mmu.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.mmu.repo.masterrepo.AgeUnitRepo;
import com.iemr.mmu.repo.masterrepo.CommunityMasterRepo;
import com.iemr.mmu.repo.masterrepo.GenderMasterRepo;
import com.iemr.mmu.repo.masterrepo.GovIdEntityTypeRepo;
import com.iemr.mmu.repo.masterrepo.IncomeStatusMasterRepo;
import com.iemr.mmu.repo.masterrepo.LiteracyStatusRepo;
import com.iemr.mmu.repo.masterrepo.MaritalStatusMasterRepo;
import com.iemr.mmu.repo.masterrepo.OccupationMasterRepo;
import com.iemr.mmu.repo.masterrepo.QualificationMasterRepo;
import com.iemr.mmu.repo.masterrepo.RelationshipMasterRepo;
import com.iemr.mmu.repo.masterrepo.ReligionMasterRepo;
import com.iemr.mmu.repo.nurse.anc.ANCCareRepo;
import com.iemr.mmu.repo.registrar.BeneficiaryImageRepo;
import com.iemr.mmu.repo.registrar.ReistrarRepoBenSearch;

@Service
@PropertySource("classpath:application.properties")
public class RegistrarServiceMasterDataImpl implements RegistrarServiceMasterData {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Value("${getBenImageFromIdentity}")
	private String getBenImageFromIdentity;

	private CommunityMasterRepo communityMasterRepo;
	private GenderMasterRepo genderMasterRepo;
	private GovIdEntityTypeRepo govIdEntityTypeRepo;
	private IncomeStatusMasterRepo incomeStatusMasterRepo;
	private MaritalStatusMasterRepo maritalStatusMasterRepo;
	private OccupationMasterRepo occupationMasterRepo;
	private QualificationMasterRepo qualificationMasterRepo;
	private ReligionMasterRepo religionMasterRepo;
	private BeneficiaryImageRepo beneficiaryImageRepo;
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
	private ANCCareRepo aNCCareRepo;
	private AgeUnitRepo ageUnitRepo;
	private LiteracyStatusRepo literacyStatusRepo;
	private RelationshipMasterRepo relationshipMasterRepo;

	@Autowired
	public void setaNCCareRepo(ANCCareRepo aNCCareRepo) {
		this.aNCCareRepo = aNCCareRepo;
	}

	@Autowired
	public void setBeneficiaryFlowStatusRepo(BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo) {
		this.beneficiaryFlowStatusRepo = beneficiaryFlowStatusRepo;
	}

	@Autowired
	public void setBeneficiaryImageRepo(BeneficiaryImageRepo beneficiaryImageRepo) {
		this.beneficiaryImageRepo = beneficiaryImageRepo;
	}

	@Autowired
	public void setCommunityMasterRepo(CommunityMasterRepo communityMasterRepo) {
		this.communityMasterRepo = communityMasterRepo;
	}

	@Autowired
	public void setGenderMasterRepo(GenderMasterRepo genderMasterRepo) {
		this.genderMasterRepo = genderMasterRepo;
	}

	@Autowired
	public void setGovIdEntityTypeRepo(GovIdEntityTypeRepo govIdEntityTypeRepo) {
		this.govIdEntityTypeRepo = govIdEntityTypeRepo;
	}

	@Autowired
	public void setIncomeStatusMasterRepo(IncomeStatusMasterRepo incomeStatusMasterRepo) {
		this.incomeStatusMasterRepo = incomeStatusMasterRepo;
	}

	@Autowired
	public void setMaritalStatusMasterRepo(MaritalStatusMasterRepo maritalStatusMasterRepo) {
		this.maritalStatusMasterRepo = maritalStatusMasterRepo;
	}

	@Autowired
	public void setOccupationMasterRepo(OccupationMasterRepo occupationMasterRepo) {
		this.occupationMasterRepo = occupationMasterRepo;
	}

	@Autowired
	public void setQualificationMasterRepo(QualificationMasterRepo qualificationMasterRepo) {
		this.qualificationMasterRepo = qualificationMasterRepo;
	}

	@Autowired
	public void setReligionMasterRepo(ReligionMasterRepo religionMasterRepo) {
		this.religionMasterRepo = religionMasterRepo;
	}

	@Autowired
	public void setAgeUnitRepo(AgeUnitRepo ageUnitRepo) {
		this.ageUnitRepo = ageUnitRepo;
	}

	@Autowired
	public void setLiteracyStatusRepo(LiteracyStatusRepo literacyStatusRepo) {
		this.literacyStatusRepo = literacyStatusRepo;
	}

	@Autowired
	public void setRelationshipMasterRepo(RelationshipMasterRepo relationshipMasterRepo) {
		this.relationshipMasterRepo = relationshipMasterRepo;
	}

	public String getRegMasterData() {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		ArrayList<Object[]> cm = communityMasterRepo.getCommunityMaster();
		ArrayList<Object[]> gm = genderMasterRepo.getGenderMaster();
		ArrayList<Object[]> gietm = govIdEntityTypeRepo.getGovIdEntityMaster();
		ArrayList<Object[]> ogietm = govIdEntityTypeRepo.getOtherGovIdEntityMaster();
		ArrayList<Object[]> ism = incomeStatusMasterRepo.getIncomeStatusMaster();
		ArrayList<Object[]> msm = maritalStatusMasterRepo.getMaritalStatusMaster();
		ArrayList<Object[]> om = occupationMasterRepo.getOccupationMaster();
		ArrayList<Object[]> qm = qualificationMasterRepo.getQualificationMaster();
		ArrayList<Object[]> rm = religionMasterRepo.getReligionMaster();
		ArrayList<AgeUnit> au = ageUnitRepo.getAgeUnit();
		ArrayList<LiteracyStatus> ls = literacyStatusRepo.getLiteracyStatus();
		ArrayList<RelationshipMaster> re = relationshipMasterRepo.getRelationshipMaster();

		// System.out.println("hello....");
		try {
			resMap.put("communityMaster", CommunityMaster.getCommunityMasterData(cm));
			resMap.put("genderMaster", GenderMaster.getGenderMasterData(gm));
			resMap.put("govIdEntityMaster", GovIdEntityType.getGovIdEntityTypeData(gietm));
			resMap.put("otherGovIdEntityMaster", GovIdEntityType.getGovIdEntityTypeData(ogietm));
			resMap.put("incomeMaster", IncomeStatusMaster.getIncomeStatusMasterData(ism));
			resMap.put("maritalStatusMaster", MaritalStatusMaster.getMaritalStatusMasterData(msm));
			resMap.put("occupationMaster", OccupationMaster.getOccupationMasterData(om));
			resMap.put("qualificationMaster", QualificationMaster.getQualificationMasterData(qm));
			resMap.put("religionMaster", ReligionMaster.getReligionMasterData(rm));
			resMap.put("ageUnit", au);
			resMap.put("literacyStatus", ls);
			resMap.put("relationshipMaster", re);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}

		// System.out.println("helloo");
		// System.out.println(new Gson().toJson(resMap));
		return gson.toJson(resMap);

	}

	private ReistrarRepoBenSearch reistrarRepoBenSearch;

	@Autowired
	public void setReistrarRepoAdvanceBenSearch(ReistrarRepoBenSearch reistrarRepoBenSearch) {
		this.reistrarRepoBenSearch = reistrarRepoBenSearch;
	}

	@Override
	public String getBenDetailsByRegID(Long beneficiaryRegID) {
		// List<Object[]> benDetailsList =
		// registrarRepoBenData.getBenDetailsByRegID(beneficiaryRegID);
		List<Object[]> benDetailsList = reistrarRepoBenSearch.getBenDetails(beneficiaryRegID);
		BeneficiaryData benDetails = BeneficiaryData.getBeneficiaryData(benDetailsList).get(0);
		// String benImage = beneficiaryImageRepo.getBenImage(beneficiaryRegID);
		benDetails.setImage(beneficiaryImageRepo.getBenImage(beneficiaryRegID));
		if (benDetails != null) {
			if (benDetails.getGenderID() != null) {
				if (benDetails.getGenderID() == 1) {
					benDetails.setGenderName("Male");
				} else if (benDetails.getGenderID() == 2) {
					benDetails.setGenderName("Female");
				} else {
					if (benDetails.getGenderID() == 3) {
						benDetails.setGenderName("Transgender");
					}
				}
			}
		}
		return new Gson().toJson(benDetails);
	}

	// Beneficiary details for left side of the screen new
	public String getBenDetailsForLeftSideByRegIDNew(Long beneficiaryRegID, Long benFlowID, String Authorization,
			String comingRequest) {

		BeneficiaryFlowStatus benFlowOBJ = beneficiaryFlowStatusRepo.getBenDetailsForLeftSidePanel(benFlowID);

		// BeneficiaryFlowStatus returnBenFlowOBJ =
		// BeneficiaryFlowStatus.getBeneficiaryFlowStatusForLeftPanel(benFlowOBJ);
		// have to add image also from common and identity.

		if (benFlowOBJ.getBenName() != null)
			benFlowOBJ.setBeneficiaryName(benFlowOBJ.getBenName());

		if (benFlowOBJ.getBen_age_val() != null)
			benFlowOBJ.setAgeVal(benFlowOBJ.getBen_age_val());

		if (benFlowOBJ.getBenVisitDate() != null)
			benFlowOBJ.setServiceDate(benFlowOBJ.getBenVisitDate());

		if (benFlowOBJ.getRegistrationDate() != null)
			benFlowOBJ.setCreatedDate(benFlowOBJ.getRegistrationDate());

		String bloodGroup = aNCCareRepo.getBenANCCareDetailsStatus(beneficiaryRegID);
		if (bloodGroup != null)
			benFlowOBJ.setBloodGroup(bloodGroup);

		return new Gson().toJson(benFlowOBJ);
	}

	public String getBenImageFromIdentityAPI(String Authorization, String comingRequest) throws Exception {
		String returnOBJ = null;
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		headers.add("AUTHORIZATION", Authorization);

		HttpEntity<Object> request = new HttpEntity<Object>(comingRequest, headers);
		ResponseEntity<String> response = restTemplate.exchange(getBenImageFromIdentity, HttpMethod.POST, request,
				String.class);
		// if()
		returnOBJ = response.getBody();
		return returnOBJ;
	}
}
