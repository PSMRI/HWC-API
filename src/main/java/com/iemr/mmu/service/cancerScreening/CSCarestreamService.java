package com.iemr.mmu.service.cancerScreening;

import java.util.ArrayList;

public interface CSCarestreamService {
	public int createMamographyRequest(ArrayList<Object[]> benDataForCareStream, long benRegID, long benVisitID,
			String Authorization);
}
