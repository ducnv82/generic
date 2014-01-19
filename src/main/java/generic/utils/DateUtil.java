package generic.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * The Class DateUtil is an utility class that provides functions for
 * {@link LocalDateTime} and {@link XMLGregorianCalendar}.
 */
public class DateUtil {

	public static LocalDateTime getCurrentDate() {
		return truncateDate(getCurrentDateTime());
	}

	public static LocalDateTime getCurrentDateTime() {
		return new LocalDateTime();
	}

	public static LocalDateTime truncateDate(final LocalDateTime date) {
		final int year = date.getYear(), month = date.getMonthOfYear(), day = date.getDayOfMonth();

		return new LocalDateTime(year, month, day, 0, 0, 0, 0);
	}

	/**
	 * Are these dates the same (not considerating the time).
	 * 
	 * @param date1
	 *            the first date
	 * @param date2
	 *            the other date
	 * @return true if this dates are the same
	 */
	public static boolean areEqual(LocalDateTime date1, LocalDateTime date2) {
		return date1.withTime(0, 0, 0, 0).isEqual(date2.withTime(0, 0, 0, 0));
	}

	/**
	 * Converts a date to GMT time and then return the date in GMT.
	 * 
	 * @param date
	 *            - date to transform
	 * @return a new date converted to GMT time
	 * @author Silviu Rosu
	 */
	public static Date convertDateToGMTDate(LocalDateTime date) {
		long dateInMiliseconds = date.toDateTime().getMillis();
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(dateInMiliseconds);
		Date gmtDate = new Date(dateInMiliseconds - calendar.get(Calendar.ZONE_OFFSET) - calendar.get(Calendar.DST_OFFSET));
		return gmtDate;
	}

	/**
	 * Converts a date to GMT time and then return the date as text in format
	 * specified. Format example: "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param date
	 *            - date to transform
	 * @param format
	 *            - format to return data
	 * @return a text representation of the date in requested format
	 * @author Silviu Rosu
	 */
	public static String convertDateToGMTString(LocalDateTime date, String format) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		return formatter.print(date);
	}

	/**
	 * The number of whole days between the two specified datetimes.
	 * 
	 * @param startDate
	 *            the start partial date, must not be null
	 * @param endDate
	 *            the end partial date, must not be null
	 * @return the period in days (> 0 if startDate < endDate, < 0 if startDate
	 *         > endDate)
	 */
	public static int daysBetween(LocalDateTime startDate, LocalDateTime endDate) {
		return Days.daysBetween(startDate, endDate).getDays();
	}

	/**
	 * Convert a {@link XMLGregorianCalendar} into a {@link LocalDateTime}.
	 * 
	 * @param gregCal
	 *            The {@link XMLGregorianCalendar}.
	 * @return the {@link LocalDateTime}.
	 */
	public static LocalDateTime toLocalDateTime(XMLGregorianCalendar gregCal) {
		return new LocalDateTime(gregCal.toGregorianCalendar().getTime());
	}

	/**
	 * Convert a {@link Date} into a {@link LocalDateTime}.
	 * 
	 * @param gregCal
	 *            The {@link Date}.
	 * @return the {@link LocalDateTime}.
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
		return new LocalDateTime(date);
	}

	/**
	 * Convert a {@link LocalDateTime} into a {@link XMLGregorianCalendar}.
	 * 
	 * @param date
	 *            The {@link LocalDateTime}
	 * @return The {@link XMLGregorianCalendar}.
	 * @throws DatatypeConfigurationException
	 */
	public static XMLGregorianCalendar toXMLGregorianCalendar(LocalDateTime date) throws DatatypeConfigurationException {
		if (date == null) {
			return null;
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date.toDateTime().toDate());
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	}

	/**
	 * Gets the XML gregorian calendar in GMT format.
	 * 
	 * @param date
	 *            the date
	 * @return the xML gregorian calendar
	 * @throws DatatypeConfigurationException
	 *             the datatype configuration exception
	 * @deprecated {@link XMLGregorianCalendar} should not be manipulated.
	 */
	@Deprecated
	public static XMLGregorianCalendar getXMLGregorianCalendarGmt(Date date) throws DatatypeConfigurationException {
		if (date == null) {
			return null;
		}
		DatatypeFactory dtf = DatatypeFactory.newInstance();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		cal.setTime(date);
		return dtf.newXMLGregorianCalendar(cal);
	}

	/**
	 * Parse a String to a {@link LocalDateTime} with the given pattern
	 * 
	 * @param dateTimeString
	 *            The date string.
	 * @param dateTimePattern
	 *            The pattern.
	 * @return The {@link LocalDateTime}.
	 */
	public static LocalDateTime toLocalDateTime(String dateTimeString, String dateTimePattern) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(dateTimePattern);
		return formatter.parseDateTime(dateTimeString).withZone(DateTimeZone.getDefault()).toLocalDateTime();
	}

	public static XMLGregorianCalendar toXMLGregorianCalendar(String dateToParse) throws DatatypeConfigurationException {
		if (dateToParse.length() > 16) {
			return DateUtil.toXMLGregorianCalendar(DateUtil.toLocalDateTime(dateToParse, "dd/MM/yyyy-HH:mm:ss"));
		} else {
			return DateUtil.toXMLGregorianCalendar(DateUtil.toLocalDateTime(dateToParse, "dd/MM/yyyy-HH:mm"));
		}

	}

	/**
	 * Format a LocalDateTime
	 * 
	 * @param localDateTime
	 *            - the LocalDateTime object to format
	 * @param format
	 *            - the desired format
	 * @return a string represented the formated LocalDateTime object
	 */
	public static String formatDate(LocalDateTime localDateTime, String format) {
		return new SimpleDateFormat(format).format(localDateTime.toDateTime().toDate());
	}

	/**
	 * Format a LocalDateTime
	 * 
	 * @param localDateTime
	 *            - the LocalDateTime object to format
	 * @param format
	 *            - the desired format
	 * @param locale
	 *            - the locale
	 * @return a string represented the formated LocalDateTime object
	 */
	public static String formatDate(LocalDateTime localDateTime, String format, Locale locale) {
		return new SimpleDateFormat(format, locale).format(localDateTime.toDateTime().toDate());
	}

	public static LocalDate toLocalDate(String value, String datePattern) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(datePattern);
		return formatter.parseLocalDate(value);
	}
}
