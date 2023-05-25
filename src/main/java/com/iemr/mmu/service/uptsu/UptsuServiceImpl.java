package com.iemr.mmu.service.uptsu;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.google.gson.Gson;
import com.iemr.mmu.data.login.MasterVan;
import com.iemr.mmu.data.uptsu.AppointmentDetails;
import com.iemr.mmu.data.uptsu.M_104ActionMaster;
import com.iemr.mmu.data.uptsu.PrBeneficiaryDetails;
import com.iemr.mmu.data.uptsu.Referred104Details;
import com.iemr.mmu.data.uptsu.Worklist104Data;
import com.iemr.mmu.repo.uptsu.M_104ActionMasterRepo;
import com.iemr.mmu.repo.uptsu.UptsuRepository;

@Service
public class UptsuServiceImpl implements UptsuService {
	@Autowired
	private M_104ActionMasterRepo m_104ActionMasterRepo;
	
	@Autowired
	private UptsuRepository uptsuRepository;
	
	
	
	public String getActionMaster() {
		List<M_104ActionMaster> resultSet =  m_104ActionMasterRepo.findByDeleted(false);
		return new Gson().toJson(resultSet);
	}

	@Override
	public String getWorklist(Integer vanID) {
		List<Worklist104Data> listOfData = new ArrayList<Worklist104Data>();

		Integer facilityID = uptsuRepository.getFacilityId(vanID);
		String facilityCode = uptsuRepository.getfacilityCode(facilityID);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime then = now.minusDays(7);
		String format2 = then.format(format);
		Timestamp time = Timestamp.valueOf(then);
		Timestamp currentTime = Timestamp.valueOf(now);

		List<AppointmentDetails> appointmentDetails = uptsuRepository.getAppointmentDetails(facilityCode, time, currentTime);
		for (AppointmentDetails appointmentDetails2 : appointmentDetails) {
			Worklist104Data worklist104Data = null;
			String benRegId = appointmentDetails2.getBenRegId();
			ArrayList<Object[]> data = uptsuRepository.findBeneficiaryNameAndBeneficiaryIdByBenRegId(benRegId);
			PrBeneficiaryDetails prBeneficiaryDetails = PrBeneficiaryDetails.getPrBeneficiaryDetails(data);
			
			worklist104Data = constuctResponce(prBeneficiaryDetails, appointmentDetails2);
			if (!ObjectUtils.isEmpty(worklist104Data)) {
				listOfData.add(worklist104Data);
			}
		}
		return new Gson().toJson(listOfData);
	}
	private String getAgeDetails(PrBeneficiaryDetails obj) {
		String ageDetails = "";
		if (obj.getDob() != null) {

			Date date = new Date(obj.getDob().getTime());
			Calendar cal = Calendar.getInstance();

			cal.setTime(date);

			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);

			java.time.LocalDate todayDate = java.time.LocalDate.now();
			java.time.LocalDate birthdate = java.time.LocalDate.of(year, month, day);
			Period p = Period.between(birthdate, todayDate);

			int d = p.getDays();
			int m = p.getMonths();
			int y = p.getYears();
			
			if (y > 0) {
				ageDetails = y + " years - " + m + " months";
				
			} else {
				if (m > 0) {
					ageDetails = m + " months - " + d + " days";
				} else {
					ageDetails = d + " days";
				}
			}

		}
		return ageDetails;
	}

	private Worklist104Data constuctResponce(PrBeneficiaryDetails prBeneficiaryDetails, AppointmentDetails appointmentDetails) {
		if (null != appointmentDetails && !ObjectUtils.isEmpty(appointmentDetails) && null != prBeneficiaryDetails
				&& !ObjectUtils.isEmpty(prBeneficiaryDetails)) {
			Worklist104Data worklist104Data = new Worklist104Data();
			String benAge = getAgeDetails(prBeneficiaryDetails);
			worklist104Data.setBenAge(benAge);
			//worklist104Data.setBen_age_val(ben_age_val);
			worklist104Data.setProcessed(appointmentDetails.getProcessed());
			worklist104Data.setBenCallID(appointmentDetails.getBenCallId());
			
			worklist104Data.setBeneficiaryRegID(appointmentDetails.getBenRegId());
			worklist104Data.setDeleted(appointmentDetails.isDeleted());
			worklist104Data.setFacilityCode(appointmentDetails.getFacilityCode());
			worklist104Data.setFacilityName(appointmentDetails.getFacilityName());
			worklist104Data.setProviderServiceMapId(appointmentDetails.getProviderServiceMapID());
			worklist104Data.setReferredFlag(appointmentDetails.isRefferedFlag());
			worklist104Data.setAppointmentDate(appointmentDetails.getAppointmentDate());
			worklist104Data.setBeneficiaryID(prBeneficiaryDetails.getBeneficiaryID());
			worklist104Data.setAge(prBeneficiaryDetails.getAge());
			worklist104Data.setDistrictID(prBeneficiaryDetails.getDistrictId());
			worklist104Data.setDistrictName(prBeneficiaryDetails.getDistrict());
			worklist104Data.setDOB(prBeneficiaryDetails.getDob());
			worklist104Data.setGenderID(prBeneficiaryDetails.getGenderId());
			worklist104Data.setGenderName(prBeneficiaryDetails.getGender());
			worklist104Data.setVillageID(prBeneficiaryDetails.getVillageId());
			worklist104Data.setPreferredPhoneNum(prBeneficiaryDetails.getPhoneNo());
			worklist104Data.setBenName(prBeneficiaryDetails.getBeneficiaryName());
			worklist104Data.setBeneficiaryImage(prBeneficiaryDetails.getBeneficiaryImage());

			worklist104Data.setVillageName(prBeneficiaryDetails.getVillage());
			return worklist104Data;
		}
		return null;
	}

	@Override
	public Referred104Details saveReferredDetails(Referred104Details requestObj) {
		Referred104Details data = uptsuRepository.save(requestObj);
		if(null != data) {
			uptsuRepository.updateReferredFlag(data.getBenCallID());
		}
		return data;
	}
	

}
