package com.iemr.mmu.service.swymed;

import com.iemr.mmu.utils.exception.SwymedException;

public interface SwymedService {
	
	 String login(Long userid) throws SwymedException;
	 
	 String callUser(Long fromuserid,Long touserid) throws SwymedException;
	 
	 String callUserjitsi(Long fromuserid,Long touserid) throws SwymedException;

	String callVan(Long fromuserid, Integer vanid) throws SwymedException;

	String logout();

	String callVanJitsi(Long fromuserid, Integer vanid) throws SwymedException;

}
