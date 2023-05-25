package com.iemr.mmu.service.fetosense;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.iemr.mmu.data.fetosense.Fetosense;
import com.iemr.mmu.utils.exception.IEMRException;

public interface FetosenseService {

	int updateFetosenseData(Fetosense fetosenseData) throws IEMRException;

	String sendFetosenseTestDetails(Fetosense request, String auth) throws Exception;

	String getFetosenseDetails(Long benFlowID) throws IEMRException;

	public String readPDFANDGetBase64(String filePath) throws IEMRException, IOException, FileNotFoundException;
}
