package com.iemr.mmu.utils.km;

public interface KMService {
	String getDocumentRoot();

	String createDocument(String documentPath, String filepath);
}
