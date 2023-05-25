package com.iemr.mmu.utils.km.openkm;

import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.OKMWebservicesFactory;

public class OpenKMConnector {
	private static OKMWebservices openKMService = null;

	public static OKMWebservices initialize(String url, String username, String password) {
		if (openKMService == null) {
			openKMService = OKMWebservicesFactory.newInstance(url, username, password);
		}
		return openKMService;
	}
}
