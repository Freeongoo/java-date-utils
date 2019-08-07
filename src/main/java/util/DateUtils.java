package util;

import java.text.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

public class DateUtils {

    public static final String ONLY_DATE_FORMAT_ISO = "yyyy-MM-dd";

    public static final String DATE_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss";   // ISO 8601

    public static final int DAY_IN_MSEC = 24 * 60 * 60 * 1000;

    public static final int MIN_IN_MSEC = 60 * 1000;

    public static final String DEFAULT_TIME_ZONE_STRING = "UTC";    // change this if need (Ex.: "Europe/Moscow")

    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone(DEFAULT_TIME_ZONE_STRING);

    public static final ZoneOffset DEFAULT_ZONE_OFFSET;

    static {
        int rawOffset = DEFAULT_TIME_ZONE.getRawOffset();
        long offsetInHours = TimeUnit.HOURS.convert(rawOffset, TimeUnit.MILLISECONDS);

        DEFAULT_ZONE_OFFSET = ZoneOffset.ofHours((int)offsetInHours);
    }


    public static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        return new java.sql.Date(uDate.getTime());
    }

    public static java.util.Date convertSqlToUtil(java.sql.Date sDate) {
        return new java.util.Date(sDate.getTime());
    }

    /**
     * With included boundary dates
     *
     * @param date date
     * @param dateFrom dateFrom
     * @param dateTo dateTo
     * @return true or false
     */
    public static boolean isBetween(Date date, Date dateFrom, Date dateTo) {
        validateDate(date);
        validateDate(dateFrom);
        validateDate(dateTo);

        return (date.after(dateFrom) && date.before(dateTo)) ||
                date.equals(dateFrom) || date.equals(dateTo);
    }

    /**
     * Convert LocalDate to Date
     *
     * @param localDate localDate
     * @return Date
     */
    public static Date asDate(LocalDate localDate) {
        validateDate(localDate);
        return Date.from(localDate.atStartOfDay()
                .atZone(DEFAULT_ZONE_OFFSET)
                .toInstant());
    }

    /**
     * Change date with timeZone in UTC
     *
     * @param date date
     * @return Date
     */
    public static Date asDateFromUTC(Date date) {
        validateDate(date);
        return asDateFromUTC(asLocalDateTime(date));
    }

    /**
     * Convert LocalDate to Date in timeZone UTC
     *
     * @param localDate localDate
     * @return Date
     */
    public static Date asDateFromUTC(LocalDate localDate) {
        validateDate(localDate);
        return Date.from(localDate.atStartOfDay()
                .atZone(DEFAULT_ZONE_OFFSET)
                .toInstant());
    }

    /**
     * Convert LocalDateTime to Date
     *
     * @param localDateTime localDateTime
     * @return Date
     */
    public static Date asDate(LocalDateTime localDateTime) {
        validateDate(localDateTime);
        return Date.from(localDateTime
                .atZone(DEFAULT_ZONE_OFFSET)
                .toInstant());
    }

    /**
     * Convert LocalDateTime to Date in timeZone UTC
     *
     * @param localDateTime localDateTime
     * @return Date
     */
    public static Date asDateFromUTC(LocalDateTime localDateTime) {
        validateDate(localDateTime);
        return Date.from(localDateTime
                .atZone(DEFAULT_ZONE_OFFSET)
                .toInstant());
    }

    /**
     * Convert Date to LocalDate
     *
     * @param date date
     * @return LocalDate
     */
    public static LocalDate asLocalDate(Date date) {
        validateDate(date);
        return Instant.ofEpochMilli(date.getTime())
                .atZone(DEFAULT_ZONE_OFFSET)
                .toLocalDate();
    }

    /**
     * Convert Date to LocalDateTime
     *
     * @param date date
     * @return LocalDateTime
     */
    public static LocalDateTime asLocalDateTime(Date date) {
        validateDate(date);
        return Instant.ofEpochMilli(date.getTime())
                .atZone(DEFAULT_ZONE_OFFSET)
                .toLocalDateTime();
    }

    /**
     * @param localDateTime localDateTime
     * @return LocalDateTime
     */
    public static LocalDateTime getBeginCurrDay(LocalDateTime localDateTime) {
        validateDate(localDateTime);
        return localDateTime
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
    }

    /**
     * @param date date
     * @return LocalDateTime
     */
    public static LocalDateTime getBeginCurrDay(Date date) {
        validateDate(date);
        return getBeginCurrDay(asLocalDateTime(date));
    }

    /**
     * @param date date
     * @return Date
     */
    public static Date getBeginCurrDayAsDate(Date date) {
        validateDate(date);
        return asDate(getBeginCurrDay(date));
    }

    /**
     * @param localDateTime localDateTime
     * @return LocalDateTime
     */
    public static LocalDateTime getEndCurrDay(LocalDateTime localDateTime) {
        validateDate(localDateTime);
        return localDateTime
                .withHour(23)
                .withMinute(59)
                .withSecond(59);
    }

    /**
     * @param date date
     * @return LocalDateTime
     */
    public static LocalDateTime getEndCurrDay(Date date) {
        validateDate(date);
        return getEndCurrDay(asLocalDateTime(date));
    }

    /**
     * @param date date
     * @return Date
     */
    public static Date getEndCurrDayAsDate(Date date) {
        validateDate(date);
        return asDate(getEndCurrDay(date));
    }

    /**
     * @param localDateTime localDateTime
     * @return LocalDateTime
     */
    public static LocalDateTime getBeginNextDay(LocalDateTime localDateTime) {
        validateDate(localDateTime);
        return localDateTime
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .plusDays(1);
    }

    /**
     * @param date date
     * @return LocalDateTime
     */
    public static LocalDateTime getBeginNextDay(Date date) {
        validateDate(date);
        return getBeginNextDay(asLocalDateTime(date));
    }

    /**
     * @param date date
     * @return LocalDateTime
     */
    public static LocalDateTime getBeginPreviousDay(Date date) {
        validateDate(date);
        return asLocalDateTime(date)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .minusDays(1);
    }

    /**
     * @param date date
     * @return Date
     */
    public static Date getBeginPreviousDayAsDate(Date date) {
        validateDate(date);
        return asDate(getBeginPreviousDay(date));
    }

    /**
     * @param date date
     * @return Date
     */
    public static Date getBeginNextDayAsDate(Date date) {
        validateDate(date);
        return asDate(getBeginNextDay(date));
    }

    /**
     * @param date date
     * @return LocalDateTime
     */
    public static LocalDateTime getBeginCurrMonth(Date date) {
        validateDate(date);
        return asLocalDateTime(date)
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
    }

    /**
     * @param date date
     * @return Date
     */
    public static Date getBeginCurrMonthAsDate(Date date) {
        validateDate(date);
        return asDate(getBeginCurrMonth(date));
    }

    /**
     * @param date date
     * @return LocalDateTime
     */
    public static LocalDateTime getBeginCurrYear(Date date) {
        validateDate(date);
        return asLocalDateTime(date)
                .withDayOfYear(1)
                .withMonth(1)
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
    }

    /**
     * @param date date
     * @return Date
     */
    public static Date getBeginCurrYearAsDate(Date date) {
        validateDate(date);
        return asDate(getBeginCurrYear(date));
    }

    /**
     * @param dateStr dateStr
     * @param format format
     * @return Date
     */
    public static Date parse(String dateStr, String format) {
        validateDateString(dateStr);

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(DEFAULT_TIME_ZONE);

        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param dateStr dateStr
     * @param format format
     * @return Date
     */
    public static LocalDateTime parseToLocalDateTime(String dateStr, String format) {
        validateDateString(dateStr);

        Date date = parse(dateStr, format);
        return asLocalDateTime(date);
    }

    /**
     * @param dateStr dateStr
     * @param format format
     * @return Date
     */
    public static LocalDate parseToLocalDate(String dateStr, String format) {
        validateDateString(dateStr);

        Date date = parse(dateStr, format);
        return asLocalDate(date);
    }

    /**
     * Parse format ISO with/without time
     *
     * @param dateStr dateStr
     * @return Date
     */
    public static Date parseISO(String dateStr) {
        validateDateString(dateStr);

        if (dateStr.contains("T"))
            return parse(dateStr, DATE_FORMAT_ISO);

        return parse(dateStr, ONLY_DATE_FORMAT_ISO);
    }

    /**
     * @param dateStr dateStr
     * @return LocalDateTime
     */
    public static LocalDateTime parseISOToLocalDateTime(String dateStr) {
        validateDateString(dateStr);

        Date date = parseISO(dateStr);
        return asLocalDateTime(date);
    }

    /**
     * @param dateStr dateStr
     * @return LocalDate
     */
    public static LocalDate parseISOToLocalDate(String dateStr) {
        validateDateString(dateStr);

        Date date = parseISO(dateStr);
        return asLocalDate(date);
    }

    /**
     * If dateFrom > dateTo return negative count of days
     * If dateFrom < dateTo return positive count of days
     * else == zero
     *
     * @param dateFrom dateFrom
     * @param dateTo dateTo
     * @return Long
     */
    public static Long getCountDaysBetween(Date dateFrom, Date dateTo) {
        LocalDate localFrom = DateUtils.asLocalDate(dateFrom);
        LocalDate localTo = DateUtils.asLocalDate(dateTo);

        if (localFrom.isEqual(localTo)) {
            return 0L;
        }

        return ChronoUnit.DAYS.between(localFrom, localTo);
    }

    /**
     * @param date date
     * @param countDays countDays may be positive or negative
     * @return Date
     */
    public static Date calcIncOrDecDays(Date date, long countDays) {
        validateDate(date);

        LocalDateTime localDateTime = asLocalDateTime(date);
        if (countDays < 0)
            return asDate(localDateTime.minusDays(Math.abs(countDays)));
        if (countDays > 0)
            return asDate(localDateTime.plusDays(Math.abs(countDays)));

        return date;
    }

    /**
     * @param date date
     * @param format format
     * @return date in formatted string
     */
    public static String toString(Date date, String format) {
        validateDate(date);

        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * @param date date
     * @param format format
     * @return date in formatted string
     */
    public static String toString(LocalDateTime date, String format) {
        return toString(asDate(date), format);
    }

    /**
     * @param date date
     * @param format format
     * @return date in formatted string
     */
    public static String toString(LocalDate date, String format) {
        return toString(asDate(date), format);
    }

    /**
     * @param date date
     * @return string date in ISO format
     */
    public static String toStringISO(Date date) {
        validateDate(date);
        return toString(date, DATE_FORMAT_ISO);
    }

    /**
     * @param date date
     * @return string date in ISO format without time
     */
    public static String toStringISOWithoutTime(Date date) {
        validateDate(date);
        return toString(date, ONLY_DATE_FORMAT_ISO);
    }

    /**
     * @param localDateTime localDateTime
     * @return string date in ISO format
     */
    public static String toStringISO(LocalDateTime localDateTime) {
        return toStringISO(asDate(localDateTime));
    }

    /**
     * @param localDate localDate
     * @return string date in ISO format
     */
    public static String toStringISO(LocalDate localDate) {
        return toStringISO(asDate(localDate));
    }

    /**
     * @param obj obj
     * @return string date formatted to ISO
     */
    public static String toStringISOIfDate(Object obj) {
        requireNonNull(obj, "Cannot be passed null object");

        if (obj instanceof Date) {
            return toStringISO((Date) obj);
        } else if (obj instanceof LocalDateTime) {
            return toStringISO((LocalDateTime) obj);
        } else if (obj instanceof LocalDate) {
            return toStringISO((LocalDate) obj);
        }

        return obj.toString();
    }


    private static void validateDateString(String dateStr) {
        requireNonNull(dateStr, "The dateString passed cannot be null");
    }

    private static void validateDate(Date date) {
        requireNonNull(date, "The date passed cannot be null");
    }

    private static void validateDate(LocalDate date) {
        requireNonNull(date, "The localDate passed cannot be null");
    }

    private static void validateDate(LocalDateTime date) {
        requireNonNull(date, "The localDateTime passed cannot be null");
    }
}
