package com.org.shbvn.svbsimo.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

	protected static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

	
	public static String MIN_TIME = "01:00:00";

	public static String MAX_TIME = "23:59:59";

	public static String DATE_FORMAT = "dd-MM-yyyy";

	public static String DATEFORMAT = "dd/MM/yyyy";

	public static String MM_dd_yyyy = "MM/dd/yyyy";

	public static String HH_mm = "HH:mm";

	public static String dd_MM_yyyy_HH_mm = "dd-MM-yyyy HH:mm";

	public static String dd_MM_yyyy_HH_mm_ss = "dd-MM-yyyy HH:mm:ss";

	public static String ddMMyyyy_HH_mm_ss = "dd/MM/yyyy HH:mm:ss";

	public static String yyyy_MM_dd = "yyyy-MM-dd";

	public static String dd_MM_YYYY = "dd_MM_YYYY";

	public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

	public static String ddmmyyyyhhmmss = "ddmmyyyyhhmmss";

	public static String ddMMyyyyhhmmss = "ddMMyyyyhhmmss";
	
	public static String ddMMyyyyhhmmssSSSSSS = "ddMMyyyyhhmmssSSSSSS";

	public static String DATE_FULL = "yyyy-MM-dd HH:mm:ss.S";

	public static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

	public static String ddMMyyyy = "ddMMyyyy";

	public static String ddMMMyyyy = "dd-MMM-yyyy";
	
	public static String ddMMMyy = "dd-MMM-yy";

	public static String MMyyyy = "MM-yyyy";

	public static String yyyyMMdd = "yyyyMMdd";

	public static String yyyyMMdd_HHmmss = "yyyyMMdd_HHmmss";

	public static String yyMMddHHmmss = "yyMMddHHmmss";
	public static Calendar calendar = Calendar.getInstance();
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyMMddHHmmss);

	public static String[] ftStrings = { DATE_FORMAT, yyyyMMddHHmmss, "yyyy_MM_dd", yyyy_MM_dd, ddmmyyyyhhmmss,
			DATE_FULL, ddMMyyyy, ddMMMyyyy, MMyyyy, DATEFORMAT,"yyyyMMdd", "MM/dd/yyyy",ddMMyyyyhhmmssSSSSSS };

	public DateUtils() {
	}

	public static void main(String[] args) {
		
		// Calendar startDate = Calendar.getInstance(TimeZone.getDefault());
		// startDate.set(Calendar.DATE, 1);
		// startDate.set(Calendar.MONTH, Calendar.SEPTEMBER);
		// startDate.add(Calendar.DAY_OF_MONTH, 2);

		// Calendar endDate = Calendar.getInstance(TimeZone.getDefault());
		// endDate.set(Calendar.DATE, 1);
		// endDate.set(Calendar.MONTH, Calendar.SEPTEMBER);

		// long daysBetween = daysBetween(startDate, endDate);
		// System.out.println("TOTAL DAY: " + calculateDate(startDate, endDate,
		// Calendar.HOUR)/24);
		// System.out.println(getTatTime(endDate, 2));

		/*
		 * System.out.println(formatToString(getSqlDate("12-12-2015"), ""));
		 * System.out.println(formatToString(new Date(), "dd/MM/yyyy"));
		 * System.out.println(formatToString(formatToDate("12-12-2015"), ""));
		 * 
		 * System.out.println("getTime: " + getTime(getSqlDate("10-06-2015"),
		 * getSqlDate("20-06-2015"), 5));
		 * 
		 * System.out.println("getTimeDiff: " + getTimeDiff(getSqlDate("10-06-2015"),
		 * getSqlDate("20-06-2015")));
		 */

		// System.out.println(getCurrentDate());
		// System.out.println("getTime: " + getTime(getSqlDate("01-05-2015"),
		// getCurrentDate(), 5));

		// System.out.println(isValidate("02-2015"));

		// System.out.println(getDateFormat(new Date(),
		// "yyyy_MM_dd_IIWPB170101TMTFQPB_SHEETTYPE"));

		try {
			
			String strDate = "10-Apr-2017";
			Date date = convertDate(strDate, ddMMMyyyy);
			strDate = "20-Apr-2017";
			Date enddate = convertDate(strDate, ddMMMyyyy);
			String str = "gdfgfdgdfg\\gdfgdgdfg";
			System.out.println("getYesterday(Date date): " + str.matches("[a-zA-Z]"));
			System.out.println("getYesterday(Date date): " + str.split("\\\\")[0]);
			System.out.println("getYesterday(Date date): " + getYesterday());
			System.out.println("getListStringDate(Date date): " + getListStringDate(date,enddate));
			System.out.println("getValidFormat(Date date): " + getValidFormat("5/4/2021"));
			System.out.println("isValid(Date date): " + isValidate("20200714 07:37:19"));
			System.out.println("getFirstDayOfMonth(Date date): " + getFirstDayOfMonth(date));
			System.out.println("getFirstDayOfMonth(String strDate): " + getFirstDayOfMonth(strDate));

			System.out.println("getLastDayOfMonth(Date date): " + getLastDayOfMonth(date));
			System.out.println("getLastDayOfMonth(String strDate): " + getLastDayOfMonth(strDate));

			System.out.println("getFirstDayOfQuarter(Date date): " + getFirstDayOfQuarter(date));
			System.out.println("getFirstDayOfQuarter(String strDate): " + getFirstDayOfQuarter(strDate));

			System.out.println("getLastDayOfQuarter(Date date): " + getLastDayOfQuarter(date));
			System.out.println("getLastDayOfQuarter(String strDate): " + getLastDayOfQuarter(strDate));

			System.out.println("getFirstDayOfYear(Date date): " + getFirstDayOfYear(date));
			System.out.println("getFirstDayOfYear(String strDate): " + getFirstDayOfYear(strDate));

			System.out.println("getLastDayOfYear(Date date): " + getLastDayOfYear(date));
			System.out.println("getLastDayOfYear(String strDate): " + getLastDayOfYear(strDate));

		} catch (Exception e) {
			e.printStackTrace();
		}

		String valueFinal = DateUtils.formatDate(DateUtils.convertDate("2017", "yyyy"), DateUtils.ddMMMyyyy);
		System.out.println(valueFinal);
	}

	public static boolean isValidate(String date) {
		if (CommonUtils.isBlank(date)) {
			return false;
		}
		for (String frString : ftStrings) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(frString);
			dateFormat.setLenient(false);
			try {
				dateFormat.parse(date.trim());
				return true;
			} catch (ParseException pe) {
			}
		}
		return false;
	}

	public static String getValidFormat(String date) {
		if (CommonUtils.isBlank(date)) {
			return "";
		}
		for (String frString : ftStrings) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(frString);
			dateFormat.setLenient(false);
			try {
				dateFormat.parse(date.trim());
				return frString;
			} catch (ParseException pe) {
			}
		}
		return "";
	}

	/**
	 * Get first date of month
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getFirstDayOfMonth(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat(ddMMMyyyy);
		return sdf.parse(sdf.format(cal.getTime()));
	}

	/**
	 * Get last date of month
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getLastDayOfMonth(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat sdf = new SimpleDateFormat(ddMMMyyyy);
		return sdf.parse(sdf.format(cal.getTime()));
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getFirstDayOfMonth(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(convertDate(date, ddMMMyyyy));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat(ddMMMyyyy);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getFirstDayOfLastMonth(String format){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, 1);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getLastDayOfMonth(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(convertDate(date, ddMMMyyyy));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat sdf = new SimpleDateFormat(ddMMMyyyy);
		return sdf.format(cal.getTime());
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getFirstDayOfQuarter(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat(ddMMMyyyy);
		return sdf.parse(sdf.format(cal.getTime()));
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getLastDayOfQuarter(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3 + 2);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat sdf = new SimpleDateFormat(ddMMMyyyy);
		return sdf.parse(sdf.format(cal.getTime()));
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getFirstDayOfQuarter(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(convertDate(date, ddMMMyyyy));
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		SimpleDateFormat sdf = new SimpleDateFormat(ddMMMyyyy);
		return sdf.format(cal.getTime());
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getLastDayOfQuarter(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(convertDate(date, ddMMMyyyy));
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3 + 2);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat sdf = new SimpleDateFormat(ddMMMyyyy);
		return sdf.format(cal.getTime());
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getFirstDayOfYear(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR, 1);

		SimpleDateFormat sdf = new SimpleDateFormat(ddMMMyyyy);
		return sdf.parse(sdf.format(cal.getTime()));
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getLastDayOfYear(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));

		SimpleDateFormat sdf = new SimpleDateFormat(ddMMMyyyy);
		return sdf.parse(sdf.format(cal.getTime()));
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getFirstDayOfYear(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(convertDate(date, ddMMMyyyy));
		cal.set(Calendar.DAY_OF_YEAR, 1);

		SimpleDateFormat sdf = new SimpleDateFormat(ddMMMyyyy);
		return sdf.format(cal.getTime());
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getLastDayOfYear(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(convertDate(date, ddMMMyyyy));
		cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));

		SimpleDateFormat sdf = new SimpleDateFormat(ddMMMyyyy);
		return sdf.format(cal.getTime());
	}

	public static Date getDate(String date) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(DATE_FULL);

			if (date != null)
				return dateFormat.parse(date);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		try {
			DateFormat dateFormat = new SimpleDateFormat(DATE_FULL);

			java.util.Date date = new java.util.Date();

			String dateStr = dateFormat.format(date);

			return dateFormat.parse(dateStr);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Get
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date getSqlDate(String strDate) {

		java.sql.Date dateFormat = null;

		try {
			DateFormat format = new SimpleDateFormat(getValidFormat(strDate));

			dateFormat = new java.sql.Date(format.parse(strDate).getTime());

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return dateFormat;
	}

	public static Date getSqlDate(String strDate, String strFormat) {

		java.sql.Date dateFormat = null;

		try {
			DateFormat format = new SimpleDateFormat(strFormat);

			dateFormat = new java.sql.Date(format.parse(strDate).getTime());

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return dateFormat;
	}

	/**
	 * Format String date to date
	 * 
	 * @param strDate
	 * @param type
	 * @return
	 */
	public static Date formatStringToDate(String strDate, String type) {
		Date dateFormat = null;
		DateFormat format = new SimpleDateFormat(type);
		try {
			dateFormat = format.parse(strDate);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return dateFormat;
	}

	/**
	 * Format String date to date
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date formatToDate(String strDate) {
		Date dateFormat = null;
		DateFormat format = new SimpleDateFormat(DATE_FORMAT);
		try {
			dateFormat = format.parse(strDate);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return dateFormat;
	}

	public static Date parseTo(Date date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.parse(sdf.format(date));

		} catch (ParseException e) {
		}
		return null;
	}

	public static String formatDate(Date date, String formatType) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatType);
		if (date == null)
			return "";
		try {
			return sdf.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	public static String getDateFormat(Date date, String formatType) {
		if (date == null)
			return "";
		if (CommonUtils.isBlank(formatType)) {
			return formatDate(date, yyyy_MM_dd);
		} else {
			for (String frString : ftStrings) {
				Pattern word = Pattern.compile(frString);
				Matcher match = word.matcher(formatType);
				while (match.find()) {
					String strDate = formatDate(date, frString);
					formatType = formatType.replace(frString, strDate);
				}
			}
		}
		return formatType;
	}

	public static boolean checkDate(String inDate, String formatType) {
		inDate = !"".equals(inDate) ? inDate : null;
		if (inDate == null)
			return false;
		// set the format to use as a constructor argument
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatType);
		if (inDate.trim().length() != dateFormat.toPattern().length())
			return false;
		dateFormat.setLenient(false);
		try {
			// parse the inDate parameter
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	public static Date convertDate(String dateString, String formatType) {
		if (dateString == null || "".equals(dateString))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(formatType);
		if (!checkDate(dateString, formatType))
			return null;

		Date date = null;
		try {
			date = (!"".equals(dateString) && dateString != null) ? sdf.parse(dateString) : null;
		} catch (ParseException e) {
			return null;
		}
		return date;
	}

	public static Date convertDate(String dateString, String formatType, String formatTypeToConvert) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatType);
		SimpleDateFormat sdfConvert = new SimpleDateFormat(formatTypeToConvert);

		if (!checkDate(dateString, formatType))
			return null;

		Date date = null;
		try {
			date = (!"".equals(dateString) && dateString != null) ? sdf.parse(dateString) : null;
			date = sdfConvert.parse(sdfConvert.format(date));
		} catch (ParseException e) {
			return null;
		}
		return date;
	}

	public static String fromDateToString(Date startDate) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String date = df.format(startDate);
		return date;
	}

	public static Date converToDate(String startDateString) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date startDate = null;
		try {
			startDate = df.parse(startDateString);

			return startDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startDate;

	}

	public static Date convertDate(Date date, String formatType) {
		String strDate = formatDate(date, formatType);
		date = convertDate(strDate, formatType);
		return date;
	}

	public static String formatToDate(String strDate, String strFormat) {
		String dateFormat = null;
		if (strFormat == null) {
			strFormat = DATE_FORMAT;
		}
		DateFormat format = new SimpleDateFormat(strFormat);

		format.setLenient(false);

		try {

			Date sqlDate = getSqlDate(strDate);

			dateFormat = formatToString(sqlDate, strFormat);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return dateFormat;
	}

	/**
	 * Format Date to String date
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatToString(Date date, String format) {

		String strDate = "";

		if (format == null || format.equals("")) {
			format = DATE_FORMAT;
		}

		DateFormat dateFormat = new SimpleDateFormat(format);

		try {
			strDate = dateFormat.format(date);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return strDate;
	}

	/**
	 * Compare two date
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return true if fromDate > toDate
	 */
	public static Boolean compareDate(Date fromDate, Date toDate) {
		Calendar calFromDate = Calendar.getInstance();
		Calendar calToDate = Calendar.getInstance();
		calFromDate.setTime(fromDate);
		calToDate.setTime(toDate);

		return calFromDate.after(calToDate);
	}

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static Long hoursDifference(Date fromDate, Date toDate) {

		int MILLI_TO_HOUR = 1000 * 60 * 60;

		return (Long) ((toDate.getTime() - fromDate.getTime()) / MILLI_TO_HOUR);
	}

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param timeUnit
	 * @return
	 */
	public static long getTime(Date fromDate, Date toDate, int timeUnit) {

		long diff = toDate.getTime() - fromDate.getTime();

		if (timeUnit == Calendar.YEAR) {
			// Years
			return (diff / (60 * 60 * 1000 * 24 * 365));
		} else if (timeUnit == Calendar.MONTH) {
			// Months
			return (long) (diff / (60 * 60 * 1000 * 24 * 30.41666666));
		} else if (timeUnit == Calendar.DATE) {
			// Days
			return (diff / (24 * 60 * 60 * 1000));
		} else if (timeUnit == Calendar.HOUR) {
			// Hours
			return (diff / (60 * 60 * 1000) % 24);
		} else if (timeUnit == Calendar.MINUTE) {
			// Minutes
			return (diff / (60 * 1000) % 60);
		} else {
			// Seconds
			return (diff / 1000 % 60);
		}
	}

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static long getTimeDiff(Date fromDate, Date toDate) {

		long timeDifferenceMilliseconds = toDate.getTime() - fromDate.getTime();

		long diffSeconds = timeDifferenceMilliseconds / 1000;

		long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);

		long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);

		long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);

		long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);

		long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));

		long diffYears = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 365));

		if (diffSeconds < 1) {
			return diffSeconds;
		} else if (diffMinutes < 1) {
			return diffMinutes;
		} else if (diffHours < 1) {
			return diffHours;
		} else if (diffDays < 1) {
			return diffHours;
		} else if (diffWeeks < 1) {
			return diffDays;
		} else if (diffMonths < 1) {
			return diffWeeks;
		} else if (diffYears < 1) {
			return diffMonths;
		}
		return diffYears;
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long daysBetween(Calendar startDate, Calendar endDate) {
		long daysBetween = 0;
		while (startDate.before(endDate)) {
			if (startDate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& startDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				daysBetween++;
			}
			startDate.add(Calendar.DAY_OF_MONTH, 1);
		}
		return daysBetween;
	}

	public static List<Integer> getListYear(Integer year) {
		List<Integer> ls = new ArrayList<Integer>();
		if (year == null) {
			Calendar date = Calendar.getInstance(TimeZone.getDefault());
			year = date.get(Calendar.YEAR);
		}
		for (int i = (year - 4); i <= year; i++) {
			ls.add(i);
		}
		return ls;
	}

	public static List<Integer> getListStartYear(Integer starYear) {
		List<Integer> ls = new ArrayList<Integer>();
		Calendar date = Calendar.getInstance(TimeZone.getDefault());
		Integer year = date.get(Calendar.YEAR);
		if (starYear != null && year != null && year >= starYear) {
			for (int i = starYear; i <= year; i++) {
				ls.add(i);
			}
		} else {
			ls.add(year);
		}
		return ls;
	}

	/**
	 * Check is Holiday
	 * 
	 * @param cBefore
	 * @return
	 */
	public static Boolean isHoliday(Calendar cBefore, List<Calendar> lsCalHoliday) {
		if (lsCalHoliday != null && lsCalHoliday.size() > 0) {
			for (Calendar calendar : lsCalHoliday) {
				if (cBefore.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
						&& cBefore.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
						&& cBefore.get(Calendar.DATE) == calendar.get(Calendar.DATE)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param month
	 * @return
	 */
	public static Calendar getCalendar(int month) {

		Calendar calendarStart = Calendar.getInstance();

		calendarStart.set(Calendar.MONTH, month);
		calendarStart.set(Calendar.DAY_OF_MONTH, 15);

		return calendarStart;
	}

	/**
	 * 
	 * @param month
	 * @return
	 */
	public static Calendar getMaxCalendar(int month) {
		Calendar cal = getCalendar(month);

		Calendar calStart = Calendar.getInstance();
		calStart.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		calStart.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		calStart.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

		return calStart;
	}

	/**
	 * 
	 * @param month
	 * @return
	 */
	public static Calendar getMinCalendar(int month) {
		Calendar cal = getCalendar(month);

		Calendar calStart = Calendar.getInstance();
		calStart.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		calStart.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		calStart.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

		return calStart;
	}

	public static String getQuarterName(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		int month = calDate.get(Calendar.MONTH);
		return (month >= Calendar.JANUARY && month <= Calendar.MARCH) ? ("Q1 " + calDate.get(Calendar.YEAR))
				: (month >= Calendar.APRIL && month <= Calendar.JUNE) ? ("Q2 " + calDate.get(Calendar.YEAR))
						: (month >= Calendar.JULY && month <= Calendar.SEPTEMBER) ? ("Q3 " + calDate.get(Calendar.YEAR))
								: ("Q4 " + calDate.get(Calendar.YEAR));
	}

	public static long getDayOfDate(Date date1) {
		long aa = 24 * 3600 * 1000;
		Date date2 = new Date();
		long msDiff = date2.getTime() - date1.getTime();
		long daysDiff = Math.round(msDiff / ((double) aa));
		return daysDiff;
	}

	public static long getDayOfDateCor(Date date1) {
		long aa = 24 * 3600 * 1000;
		Date date2 = removeTimeFromDate( new Date());
		long msDiff = date2.getTime() - date1.getTime();
		long daysDiff = Math.round(msDiff / ((double) aa));
		return daysDiff;
	}
	
	public static Date removeTimeFromDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static String getSystemDateStr(String formatType) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatType);
		return simpleDateFormat.format(calendar.getTime());
	}
	
	public static boolean isPreviousMonth(String Date) {
		if( CommonUtils.isBlank(Date) ) {
			return false;
		}
		Calendar calendar = Calendar.getInstance();
		Calendar inptDate = Calendar.getInstance();
		Date compDate =  formatStringToDate(Date, DateUtils.DATEFORMAT);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		inptDate.setTime(compDate);
		
		return compDate.compareTo(removeTimeFromDate(calendar.getTime()))< 0 ? true:false ;
	}

	/**
	 * @param trxDt
	 * @return
	 */
	public static boolean isPreviousMonth(Date trxDt) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return trxDt.compareTo(removeTimeFromDate(calendar.getTime())) < 0 ? true:false ;
	}

	public static List<String> getListStringDate(Date startDate, Date endDate){
		List<String> rs = new ArrayList<>(); 
		int index = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		while(cal.getTime().compareTo(endDate) <0) {
			cal.setTime(startDate);
			cal.add(Calendar.DATE, index);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
			rs.add(simpleDateFormat.format(cal.getTime()));
			index++;
		}
		return rs;
		
	}
	
	public static int getFieldOfDate(Date date, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.get(field);
	}
	public static int getFieldOfDate(String date, int field) {
		Calendar cal = Calendar.getInstance();
		Date compDate =  formatStringToDate(date, DateUtils.DATEFORMAT);
		cal.setTime(compDate);
		return cal.get(field);
	}
	
	public static Date getYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		
		return calendar.getTime();
	}
}
