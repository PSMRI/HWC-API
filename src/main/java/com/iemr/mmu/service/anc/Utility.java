package com.iemr.mmu.service.anc;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Utility {

	public static Timestamp convertToDateFormat(String timePeriodUnit, Integer timePeriodAgo) {
		if (null != timePeriodUnit && null != timePeriodAgo) {
			Calendar cal = Calendar.getInstance();
			if (timePeriodUnit.equals("Years")) {
				cal.add(Calendar.YEAR, -timePeriodAgo);
			} else if (timePeriodUnit.equals("Months")) {
				cal.add(Calendar.MONTH, -timePeriodAgo);
			} else if (timePeriodUnit.equals("Weeks")) {
				cal.add(Calendar.DATE, -(7 * timePeriodAgo));
			} else if (timePeriodUnit.equals("Days")) {
				cal.add(Calendar.DATE, -timePeriodAgo);
			}
			return new Timestamp(cal.getTimeInMillis());
		}

		return null;
	}

	public static Map<String, Object> convertTimeToWords(Timestamp yearOfIllness, Timestamp createdDate) {
		Integer timePeriodAgo = null;
		String timePeriodUnit = "";

		Map<String, Object> timePeriod = new HashMap<String, Object>();
		if (null != yearOfIllness && null != createdDate) {

			Calendar CD = Calendar.getInstance();
			CD.setTime(createdDate);

			CD.set(Calendar.HOUR_OF_DAY, 0);
			CD.set(Calendar.MINUTE, 0);
			CD.set(Calendar.SECOND, 0);
			CD.set(Calendar.MILLISECOND, 0);

			Calendar YOI = Calendar.getInstance();
			YOI.setTime(yearOfIllness);

			YOI.set(Calendar.HOUR_OF_DAY, 0);
			YOI.set(Calendar.MINUTE, 0);
			YOI.set(Calendar.SECOND, 0);
			YOI.set(Calendar.MILLISECOND, 0);

			long createDate = CD.getTimeInMillis();
			long illnessDate = YOI.getTimeInMillis();

			long diffInMilis = createDate - illnessDate;

			long diffDays = diffInMilis / (24 * 60 * 60 * 1000);

			// int year = CD.get(Calendar.YEAR) - YOI.get(Calendar.YEAR);
			// int month = CD.get(Calendar.MONTH) - YOI.get(Calendar.MONTH);
			// int day = CD.get(Calendar.DATE) - YOI.get(Calendar.DATE);
			// if ((year != 0 || month != 0) && (month != 0 || day != 0) && (year != 0 ||
			// day != 0)) {
			// timePeriodUnit = "Weeks";
			// int days = year * 365 + month * 30 + day;
			// int weeks = days / 7;
			// timePeriodAgo = weeks;
			// } else {
			// if (year != 0) {
			// timePeriodUnit = "Years";
			// timePeriodAgo = year;
			// } else if (month != 0) {
			// timePeriodUnit = "Months";
			// timePeriodAgo = month;
			// } else if (day != 0) {
			// timePeriodUnit = "Days";
			// timePeriodAgo = day;
			// }
			// }

			if (diffDays >= 365) {
				if (diffDays % 365 == 0) {
					timePeriodUnit = "Years";
					timePeriodAgo = (int) (long) (diffDays / 365);
				} else {
					if (diffDays % 365 >= 182) {
						timePeriodUnit = "Years";
						timePeriodAgo = (int) (long) ((diffDays / 365) + 1);
					} else {
						timePeriodUnit = "Years";
						timePeriodAgo = (int) (long) (diffDays / 365);
					}
				}
			} else {
				if (diffDays >= 30) {
					if (diffDays % 30 == 0) {
						timePeriodUnit = "Months";
						timePeriodAgo = (int) (long) (diffDays / 30);
					} else {
						if (diffDays % 7 == 0) {
							timePeriodUnit = "Weeks";
							timePeriodAgo = (int) (long) (diffDays / 7);
						} else {
							timePeriodUnit = "Weeks";
							timePeriodAgo = (int) (long) ((diffDays / 7) + 1);
						}
					}
				} else {
					timePeriodUnit = "Days";
					timePeriodAgo = (int) (long) diffDays;
				}
			}
		}

		timePeriod.put("timePeriodAgo", timePeriodAgo);
		timePeriod.put("timePeriodUnit", timePeriodUnit);

		return timePeriod;
	}

	public static Timestamp combineDateAndTimeToDateTime(String tcDate, String tcTime) throws ParseException {
		Timestamp tcScheduleDateTime = null;
		if (tcDate != null && tcTime != null) {
			tcDate = tcDate.split(" ")[0];
			LocalDate datePart = LocalDate.parse(tcDate);
			LocalTime timePart = LocalTime.parse(tcTime);
			LocalDateTime dateTime = LocalDateTime.of(datePart, timePart);

			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			TimeZone timeZone = TimeZone.getTimeZone("IST");

			sdf.setTimeZone(timeZone);

			Date d = sdf.parse(dateTime.format(format));
			// Date d = new SimpleDateFormat("yyyy-MM-dd
			// hh:mm:ss.SSS").parse(dateTime.format(format));
			tcScheduleDateTime = new Timestamp(d.getTime());
		}
		return tcScheduleDateTime;
	}

	public static long timeDiff(String fromTime, String toTime) {
		Long duration = null;
		if (fromTime != null && toTime != null) {
			LocalTime fromT = LocalTime.parse(fromTime);
			LocalTime toT = LocalTime.parse(toTime);
			Duration d = Duration.between(fromT, toT);
			duration = d.toMinutes();
		}
		return duration;
	}
}
