package com.iemr.mmu.service.ncdscreening;

import com.iemr.mmu.data.ncdScreening.NCDScreening;

public interface NCDScreeningNurseService {

	Long saveNCDScreeningDetails(NCDScreening ncdScreening);

	String getNCDScreeningDetails(Long beneficiaryRegID, Long benVisitID);

	Integer updateNCDScreeningDetails(NCDScreening ncdScreening);

}
