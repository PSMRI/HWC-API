package com.iemr.hwc.service.adolescent;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.iemr.hwc.data.nurse.CommonUtilityClass;
import com.iemr.hwc.repo.nurse.BenVisitDetailRepo;
import com.iemr.hwc.repo.nurse.CDSSRepo;
import com.iemr.hwc.repo.nurse.anc.BenAdherenceRepo;
import com.iemr.hwc.repo.quickConsultation.BenChiefComplaintRepo;
import com.iemr.hwc.utils.exception.IEMRException;
import com.iemr.hwc.utils.mapper.InputMapper;

@ExtendWith(MockitoExtension.class)
class AdolescentAndChildCareServiceImplTest {

	@InjectMocks
	private AdolescentAndChildCareServiceImpl adolescentAndChildCareServiceImpl;

	@Mock
	private InputMapper inputMapper;

	@Mock
	private BenVisitDetailRepo benVisitDetailRepo;

	@Mock
	private BenChiefComplaintRepo benChiefComplaintRepo;

	@Mock
	private BenAdherenceRepo benAdherenceRepo;

	@Mock
	private CDSSRepo cdssRepo;

	@Test
	void testSaveNurseData() {
		fail("Not yet implemented");
	}

	@Test
	void testSaveDoctorDataCAC() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBenVisitDetailsFrmNurseCAC() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBirthAndImmuniHistory() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBeneficiaryVitalDetails() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBeneficiaryCdssDetails() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBeneficiaryImmunizationServiceDetails() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBenCaseRecordFromDoctorCAC() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNurseDataCAC() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateBenVitalDetailsCAC() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateBenHistoryDetails() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateBenImmunServiceDetailsCAC() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateDoctorDataCAC() {
		fail("Not yet implemented");
	}

}
