package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

public class DateUtils {

    public static final String ONLY_DATE_FORMAT_ISO = "yyyy-MM-dd";

    public static final String DATE_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss";   // ISO 8601

    public static final int DAY_IN_MSEC = 24 * 60 * 60 * 1000;

    public static final int MIN_IN_MSEC = 60 * 1000;

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
                .atZone(ZoneId.systemDefault())
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
                .atZone(ZoneOffset.UTC)
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
                .atZone(ZoneId.systemDefault())
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
                .atZone(ZoneOffset.UTC)
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
                .atZone(ZoneId.systemDefault())
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
                .atZone(ZoneId.systemDefault())
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
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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

    private static void validateDateString(String dateStr) {
        Objects.requireNonNull(dateStr, "The dateString passed cannot be null");
    }

    private static void validateDate(Date date) {
        Objects.requireNonNull(date, "The date passed cannot be null");
    }

    private static void validateDate(LocalDate date) {
        Objects.requireNonNull(date, "The localDate passed cannot be null");
    }

    private static void validateDate(LocalDateTime date) {
        Objects.requireNonNull(date, "The localDateTime passed cannot be null");
    }
}
