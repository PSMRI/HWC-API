package com.iemr.mmu.service.common.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.mmu.data.institution.Institute;
import com.iemr.mmu.data.masterdata.anc.ServiceMaster;
import com.iemr.mmu.data.masterdata.doctor.PreMalignantLesion;
import com.iemr.mmu.repo.masterrepo.anc.ServiceMasterRepo;
import com.iemr.mmu.repo.masterrepo.doctor.InstituteRepo;
import com.iemr.mmu.repo.masterrepo.doctor.PreMalignantLesionMasterRepo;

@Service
public class DoctorMasterDataServiceImpl implements DoctorMasterDataService {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	@Autowired
	private PreMalignantLesionMasterRepo preMalignantLesionMasterRepo;

	private InstituteRepo instituteRepo;
	private ServiceMasterRepo serviceMasterRepo;

	@Autowired
	public void setInstituteRepo(InstituteRepo instituteRepo) {
		this.instituteRepo = instituteRepo;
	}

	@Autowired
	public void setServiceMasterRepo(ServiceMasterRepo serviceMasterRepo) {
		this.serviceMasterRepo = serviceMasterRepo;
	}

	public String getCancerScreeningMasterDataForDoctor(int psmID) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		ArrayList<Object[]> preMalignantLesionTypes = preMalignantLesionMasterRepo.getPreMalignantLesionMaster();
		ArrayList<Object[]> instituteDetails = instituteRepo.getInstituteDetails(psmID);
		ArrayList<Object[]> additionalServices = serviceMasterRepo.getAdditionalServices();
		try {
			Institute institute = new Institute();
			resMap.put("preMalignantLesionTypes",
					PreMalignantLesion.getPreMalignantLesionMasterData(preMalignantLesionTypes));

			resMap.put("higherHealthCare", institute.getinstituteDetails(instituteDetails));
			resMap.put("additionalServices", ServiceMaster.getServiceMaster(additionalServices));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}

		return new Gson().toJson(resMap);

	}
}
