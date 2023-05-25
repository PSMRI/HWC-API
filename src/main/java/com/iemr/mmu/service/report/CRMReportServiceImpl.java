package com.iemr.mmu.service.report;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemr.mmu.data.login.UserParkingplaceMapping;
import com.iemr.mmu.data.report.ChiefComplaintReport;
import com.iemr.mmu.data.report.ConsultationReport;
import com.iemr.mmu.data.report.ReportInput;
import com.iemr.mmu.data.report.SpokeReport;
import com.iemr.mmu.data.report.TMDailyReport;
import com.iemr.mmu.repo.login.UserParkingplaceMappingRepo;
import com.iemr.mmu.repo.report.BenChiefComplaintReportRepo;
import com.iemr.mmu.utils.exception.TMException;

@Service
public class CRMReportServiceImpl implements CRMReportService {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private BenChiefComplaintReportRepo benChiefComplaintReportRepo;

	@Autowired
	private UserParkingplaceMappingRepo userParkingplaceMappingRepo;

	ObjectMapper mapper = new ObjectMapper();

	/*
	 * Integer getParkingplaceID(Integer userid, Integer providerServiceMapId)
	 * throws TMException { UserParkingplaceMapping usermap =
	 * userParkingplaceMappingRepo
	 * .findOneByUserIDAndProviderServiceMapIdAndDeleted(userid,
	 * providerServiceMapId, 0);
	 * 
	 * if (usermap == null || usermap.getParkingPlaceID() == null) { throw new
	 * TMException("User Not mapped to any Parking Place"); } return
	 * usermap.getParkingPlaceID(); }
	 */

	public Integer getParkingplaceID(Integer userID, Integer providerServiceMapId) throws TMException {
		// Integer ppID = 0;
		UserParkingplaceMapping usermap = userParkingplaceMappingRepo
				.findOneByUserIDAndProviderServiceMapIdAndDeleted(userID, providerServiceMapId, 0);

		if (usermap == null || usermap.getParkingPlaceID() == null) {
			throw new TMException("User Not mapped to any Parking Place");

		}
		return usermap.getParkingPlaceID();
		// return ppID;
	}

	static ChiefComplaintReport getBenChiefComplaintReportObj(Object[] obj) {
		ChiefComplaintReport report = new ChiefComplaintReport();
		report.setChiefComplaintID((Integer) obj[0]);
		report.setChiefComplaint((String) obj[1]);
		report.setMale((BigInteger) obj[7]);
		report.setFemale((BigInteger) obj[8]);
		report.setTransgender((BigInteger) obj[9]);
		report.setGrandTotal((BigInteger) obj[6]);

		return report;

	}

	public static String calculateTime(Timestamp consultedTime, Timestamp arrivalTime) {
		if (consultedTime != null && arrivalTime != null) {
			Long waitingtime = consultedTime.getTime() - arrivalTime.getTime();
			Long totalsec = waitingtime / (1000);
			Long totalmin = totalsec / 60;
			if (totalmin < 0) {
				totalmin = 0L;
			}
			Long sec = totalsec % 60;
			Long min = totalmin % 60;
			Long hour = totalmin / 60;
			StringBuilder st = new StringBuilder();
			if (hour > 1) {
				st.append(hour);
				st.append(" hrs");
			} else if (hour == 1) {
				st.append(hour);
				st.append(" hr");
			}
			if (min > 0) {
				st.append(min);
				st.append(" min");
			}
			if (hour == 0 && min == 0 && sec > 0) {
				st.append(sec);
				st.append(" sec");
			}
			if (sec < 0 || hour < 0 || min < 0) {
				st.append("No Waiting");
			}
			return st.toString();
		}
		return null;
	}

	static ConsultationReport getConsultationReportObj(Object[] obj) {
		ConsultationReport report = new ConsultationReport();
		report.setBeneficiaryRegID(obj[2].toString());
		report.setVisitCode(obj[5].toString());
		report.setBeneficiaryName((String) obj[3]);
		report.setSpecialistName((String) obj[11]);
		report.setSpecialistId(obj[22].toString());
		report.setSpecializationID(obj[7].toString());
		report.setSpecialization((String) obj[8]);
		report.setRequestedDate((Timestamp) obj[12]);

		if (obj[14] != null && ((String) obj[14]).equals("D")) {
			report.setConsulted("YES");
		} else {
			report.setConsulted("NO");
		}
		report.setBeneficiaryArrivalTime((Timestamp) obj[16]);
		report.setConsultationFirstStart((Timestamp) obj[17]);
//		report.setConsultedTime((Timestamp) obj[17]);
//		report.setWaitingTime(calculateTime(report.getConsultedTime(), report.getArrivalTime()));
		report.setSpecialistConsultationStart((Timestamp) obj[23]);
		report.setConsultationEnd((Timestamp) obj[24]);
		report.settATForArrivalToConsultationStart((obj[25] == null) ? "" : obj[25].toString());
		report.settATForSpecialistConsultationStartToEnd((obj[26] == null) ? "" : obj[26].toString());

		return report;

	}

	static SpokeReport getSpokeReportObj(Object[] obj) {
		SpokeReport spoke = new SpokeReport();
		spoke.setVanID((Integer) obj[2]);
		spoke.setVanName((String) obj[3]);

		return spoke;

	}

	static SpokeReport getSpokeReportObj1(Object[] obj) {
		SpokeReport spoke = new SpokeReport();
		spoke.setVanID((Integer) obj[0]);
		spoke.setVanName((String) obj[1]);

		return spoke;

	}

	static TMDailyReport getTMDailyReportObj(Object[] obj) {
		TMDailyReport report = new TMDailyReport();
		report.setSpokeName((String) obj[1]);
		report.setCurrentConsultations(obj[2] == null ? new BigInteger("0") : (BigInteger) obj[2]);
		report.setRevisitConsultations(obj[3] == null ? new BigInteger("0") : (BigInteger) obj[3]);
		report.setCumulativeConsultationsForMonth(obj[4] == null ? new BigInteger("0") : (BigInteger) obj[4]);
		report.setCumulativeRevisitConsultationsForMonth(obj[5] == null ? new BigInteger("0") : (BigInteger) obj[5]);

		return report;

	}

	@Override
	public Set<SpokeReport> getChiefcomplaintreport(ReportInput input) throws TMException {
		// TODO Auto-generated method stub
		Integer ppid = getParkingplaceID(input.getUserID(), input.getProviderServiceMapID());
		List<Object[]> result = benChiefComplaintReportRepo.getcmreport(input.getFromDate(), input.getToDate(), ppid);

		HashMap<SpokeReport, List<ChiefComplaintReport>> hashmap = new HashMap();

		for (Object[] obj : result) {
			if (obj[0] != null && obj[1] != null && obj[2] != null && obj[3] != null) {
				SpokeReport spoke = getSpokeReportObj(obj);
				List<ChiefComplaintReport> listcc = new ArrayList<>();
				if (hashmap.containsKey(spoke)) {
					listcc = hashmap.get(spoke);
				}
				listcc.add(getBenChiefComplaintReportObj(obj));
				hashmap.put(spoke, listcc);
			}
		}
		for (Map.Entry<SpokeReport, List<ChiefComplaintReport>> entry : hashmap.entrySet()) {
			SpokeReport key = entry.getKey();
			List<ChiefComplaintReport> value = entry.getValue();
			key.setChiefComplaintReport(value);
		}

		return hashmap.keySet();
	}

	@Override
	public List<ConsultationReport> getConsultationReport(ReportInput input) throws TMException {
		Integer ppid = getParkingplaceID(input.getUserID(), input.getProviderServiceMapID());
		List<Object[]> objarr = benChiefComplaintReportRepo.getConsultationReport(input.getFromDate(),
				input.getToDate(), ppid);
		List<ConsultationReport> report = new ArrayList<>();
		for (Object[] obj : objarr) {
			report.add(getConsultationReportObj(obj));
		}

		return report;
	}

	@Override
	public String getTotalConsultationReport(ReportInput input) throws TMException {
		// TODO Auto-generated method stub
		Integer ppid = getParkingplaceID(input.getUserID(), input.getProviderServiceMapID());
		List<Object[]> objarr = benChiefComplaintReportRepo.getTotalConsultationReport(input.getFromDate(),
				input.getToDate(), ppid);

		LinkedHashMap<String, String> header = new LinkedHashMap<>();
		Date inputfromdate = input.getFromDate();
		Date inputtodate = input.getToDate();
		header.put("Spoke", "0");
		while (inputfromdate.compareTo(inputtodate) <= 0) {
			// System.out.println(inputfromdate);

			header.put(new SimpleDateFormat("MMM-yy").format(inputfromdate), "0");
			inputfromdate.setMonth(inputfromdate.getMonth() + 1);
		}

		LinkedHashMap<SpokeReport, LinkedHashMap<String, String>> report = new LinkedHashMap<>();
		for (Object[] obj : objarr) {
			SpokeReport spoke = getSpokeReportObj1(obj);
			LinkedHashMap<String, String> listcc = new LinkedHashMap();
			if (report.containsKey(spoke)) {
				listcc = report.get(spoke);
			} else {
				listcc = new LinkedHashMap<>(header);
				listcc.put("Spoke", spoke.getVanName());
			}
			listcc.put((String) obj[2], ((BigInteger) obj[3]).toString());
			report.put(spoke, listcc);

		}
		List<LinkedHashMap<String, String>> output = new ArrayList<>();
		// JSONArray arr = new JSONArray();

		for (Map.Entry<SpokeReport, LinkedHashMap<String, String>> reportitr : report.entrySet()) {
			output.add(reportitr.getValue());
		}
		String output1 = "";
		try {
			output1 = mapper.writeValueAsString(output);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return output1;
	}

	@Override
	public String getMonthlyReport(ReportInput input) throws TMException {
		Integer ppid = getParkingplaceID(input.getUserID(), input.getProviderServiceMapID());
		Integer vanID = 0;
		if (input.getVanID() == 0) {
			vanID = null;
		} else {
			vanID = input.getVanID();
		}

		List<Object[]> objarr = benChiefComplaintReportRepo.getMonthlyReport(input.getFromDate(), input.getToDate(),
				ppid, vanID);

		LinkedHashMap<String, String> header = new LinkedHashMap<>();
		Date inputfromdate = input.getFromDate();
		Date inputtodate = input.getToDate();
		header.put("Indicator", "0");
		while (inputfromdate.compareTo(inputtodate) <= 0) {
			// System.out.println(inputfromdate);

			header.put(new SimpleDateFormat("MMM-yy").format(inputfromdate), "0");
			inputfromdate.setMonth(inputfromdate.getMonth() + 1);
		}
		LinkedHashMap<String, LinkedHashMap<String, String>> report = new LinkedHashMap<>();
		for (Object[] obj : objarr) {
			String indicator = (String) obj[0];
			if (indicator == null) {
				continue;
			}
			LinkedHashMap<String, String> listcc = new LinkedHashMap();
			if (report.containsKey(indicator)) {
				listcc = report.get(indicator);
			} else {
				listcc = new LinkedHashMap<>(header);
				listcc.put("Indicator", indicator);
			}
			listcc.put((String) obj[1], ((BigInteger) obj[2]).toString());
			report.put(indicator, listcc);

		}
		List<LinkedHashMap<String, String>> output = new ArrayList<>();
		// JSONArray arr = new JSONArray();

		for (Map.Entry<String, LinkedHashMap<String, String>> reportitr : report.entrySet()) {
			output.add(reportitr.getValue());
		}
		String output1 = "";
		try {
			output1 = mapper.writeValueAsString(output);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return output1;
	}

	@Override
	public List<TMDailyReport> getDailyReport(ReportInput input) throws TMException {
		// TODO Auto-generated method stub
		Integer ppid = getParkingplaceID(input.getUserID(), input.getProviderServiceMapID());
		List<Object[]> objarr = benChiefComplaintReportRepo.getDailyReport(input.getFromDate(), ppid);

		List<TMDailyReport> tmdailyreport = new ArrayList<>();
		for (Object[] obj : objarr) {
			tmdailyreport.add(getTMDailyReportObj(obj));
		}

		return tmdailyreport;
	}

}
