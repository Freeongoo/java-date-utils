package util;

import org.hamcrest.core.IsEqual;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static util.DateUtils.*;

public class DateUtilsTest {

    @BeforeClass
    public static void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void convertUtilToSql() {
        Date date = parseISO("2018-02-02T10:10:10");
        java.sql.Date dateSql = DateUtils.convertUtilToSql(date);
        assertTrue(dateSql instanceof java.sql.Date);
        assertThat(dateSql, equalTo(new java.sql.Date(1517566210000L)));
    }

    @Test
    public void isBetween_WhenIn() {
        Date date = parseISO("2018-02-02T10:10:10");
        Date dateFrom = parseISO("2018-02-02T00:00:00");
        Date dateTo = parseISO("2018-02-02T23:59:59");
        boolean isBetween = DateUtils.isBetween(date, dateFrom, dateTo);
        assertTrue(isBetween);
    }

    @Test
    public void isBetween_WhenEqualsDateFrom() {
        Date date = parseISO("2018-02-02T00:00:00");
        Date dateFrom = parseISO("2018-02-02T00:00:00");
        Date dateTo = parseISO("2018-02-02T23:59:59");
        boolean isBetween = DateUtils.isBetween(date, dateFrom, dateTo);
        assertTrue(isBetween);
    }

    @Test
    public void isBetween_WhenEqualsDateTo() {
        Date date = parseISO("2018-02-02T23:59:59");
        Date dateFrom = parseISO("2018-02-02T00:00:00");
        Date dateTo = parseISO("2018-02-02T23:59:59");
        boolean isBetween = DateUtils.isBetween(date, dateFrom, dateTo);
        assertTrue(isBetween);
    }

    @Test
    public void isBetween_WhenOut_ShouldBeFalse() {
        Date date = parseISO("2018-02-03T00:00:00");
        Date dateFrom = parseISO("2018-02-02T00:00:00");
        Date dateTo = parseISO("2018-02-02T23:59:59");
        boolean isBetween = DateUtils.isBetween(date, dateFrom, dateTo);
        assertFalse(isBetween);
    }

    @Test
    public void getBeginCurrDay() {
        LocalDateTime localDateTime = LocalDateTime.of(2017, 2, 2, 10, 10, 10);
        LocalDateTime beginCurrDay = DateUtils.getBeginCurrDay(localDateTime);

        // expectation
        LocalDateTime expected = LocalDateTime.of(2017, 2, 2, 0, 0, 0);

        assertThat(beginCurrDay, equalTo(expected));
    }

    @Test(expected = NullPointerException.class)
    public void getBeginCurrDay_WhenNull() {
        LocalDateTime dateTime = null;
        LocalDateTime beginCurrDay = DateUtils.getBeginCurrDay(dateTime);
    }

    @Test
    public void getEndCurrDay() {
        LocalDateTime localDateTime = LocalDateTime.of(2017, 2, 2, 10, 10, 10);
        LocalDateTime endCurrDay = DateUtils.getEndCurrDay(localDateTime);

        // expectation
        LocalDateTime expected = LocalDateTime.of(2017, 2, 2, 23, 59, 59);

        assertThat(endCurrDay, equalTo(expected));
    }

    @Test(expected = NullPointerException.class)
    public void getEndCurrDay_WhenNull() {
        LocalDateTime dateTime = null;
        LocalDateTime beginCurrDay = DateUtils.getEndCurrDay(dateTime);
    }

    @Test
    public void getBeginNextDay() {
        LocalDateTime localDateTime = LocalDateTime.of(2017, 2, 2, 10, 10, 10);
        LocalDateTime beginNextDay = DateUtils.getBeginNextDay(localDateTime);

        // expectation
        LocalDateTime expected = LocalDateTime.of(2017, 2, 3, 0, 0, 0);

        assertThat(beginNextDay, equalTo(expected));
    }

    @Test
    public void parse() {
        Date date = DateUtils.parse("2016-02-02", "yyyy-MM-dd");

        Date dateExpected = new Date();
        dateExpected.setTime(1454371200000L);

        assertThat(date, equalTo(dateExpected));
    }

    @Test(expected = RuntimeException.class)
    public void parse_WhenInvalidDateString() {
        DateUtils.parse("2016_02-02", "yyyy-MM-dd");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_WhenInvalidDateFormat() {
        DateUtils.parse("2016-02-02", "unknown format");
    }

    @Test
    public void parseToLocalDateTime_WhenDateAndTime() {
        LocalDateTime localDateTime = DateUtils.parseToLocalDateTime("2016-02-02T10:10:10", DATE_FORMAT_ISO);
        LocalDateTime dateExpected = LocalDateTime.of(2016, Month.FEBRUARY, 2, 10, 10, 10);

        assertThat(localDateTime, equalTo(dateExpected));
    }

    @Test
    public void parseToLocalDateTime_WhenDateOnly() {
        LocalDateTime localDateTime = DateUtils.parseToLocalDateTime("2016-02-02", ONLY_DATE_FORMAT_ISO);
        LocalDateTime dateExpected = LocalDateTime.of(2016, Month.FEBRUARY, 2, 0, 0, 0);

        assertThat(localDateTime, equalTo(dateExpected));
    }

    @Test
    public void parseISO_WhenDateWithTime() {
        Date date = parseISO("2017-02-02T10:10:10");
        assertThat(date.getTime(), equalTo(1486030210000L));
    }

    @Test
    public void parseISO_WhenDateWithoutTime_ShouldBeOk() {
        Date date = parseISO("2017-02-02");
        assertThat(date.getTime(), equalTo(1485993600000L));
    }

    @Test(expected = RuntimeException.class)
    public void parseISO_WhenInvalidFormat() {
        Date date = parseISO("02022017");
    }

    @Test(expected = NullPointerException.class)
    public void parseISO_WhenNull() {
        Date date = parseISO(null);
    }

    @Test(expected = NullPointerException.class)
    public void getCountDays_WhenFromNullDate() {
        Date dateTo = parseISO("2017-02-02T10:10:10");
        Long countDays = DateUtils.getCountDaysBetween(null, dateTo);
    }

    @Test(expected = NullPointerException.class)
    public void getCountDays_WhenToNullDate() {
        Date dateFrom = parseISO("2017-02-02T10:10:10");
        Long countDays = DateUtils.getCountDaysBetween(dateFrom, null);
    }

    @Test
    public void getCountDays_WhenSameDate() {
        Date dateFrom = parseISO("2017-02-02T10:10:10");
        Date dateTo = parseISO("2017-02-02T10:10:10");
        Long countDays = DateUtils.getCountDaysBetween(dateFrom, dateTo);

        // expected
        Long expectedCountDays = 0L;

        assertThat(countDays, equalTo(expectedCountDays));
    }

    @Test
    public void getCountDays_WhenSameDateButOtherTime() {
        Date dateFrom = parseISO("2017-02-02T10:10:10");
        Date dateTo = parseISO("2017-02-02T23:59:10");
        Long countDays = DateUtils.getCountDaysBetween(dateFrom, dateTo);

        // expected
        Long expectedCountDays = 0L;

        assertThat(countDays, equalTo(expectedCountDays));
    }

    @Test
    public void getCountDays_WhenFromLessTo() {
        Date dateFrom = parseISO("2017-02-02T10:10:10");
        Date dateTo = parseISO("2017-02-04T08:10:10");
        Long countDays = DateUtils.getCountDaysBetween(dateFrom, dateTo);

        // expected
        Long expectedCountDays = 2L;

        assertThat(countDays, equalTo(expectedCountDays));
    }

    @Test
    public void getCountDays_WhenFromMoreTo() {
        Date dateFrom = parseISO("2017-02-04T10:10:10");
        Date dateTo = parseISO("2017-02-02T08:10:10");
        Long countDays = DateUtils.getCountDaysBetween(dateFrom, dateTo);

        // expected
        Long expectedCountDays = -2L;

        assertThat(countDays, equalTo(expectedCountDays));
    }

    @Test
    public void calcIncOrDecDays_WhenIncrease() {
        Date date = parseISO("2017-02-04T10:10:10");
        Date actual = DateUtils.calcIncOrDecDays(date, 2);

        // expected
        Date expected = parseISO("2017-02-06T10:10:10");

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void calcIncOrDecDays_WhenDecrease() {
        Date date = parseISO("2017-02-04T10:10:10");
        Date actual = DateUtils.calcIncOrDecDays(date, -2);

        // expected
        Date expected = parseISO("2017-02-02T10:10:10");

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void calcIncOrDecDays_WhenZero() {
        Date date = parseISO("2017-02-04T10:10:10");
        Date actual = DateUtils.calcIncOrDecDays(date, 0);

        // expected
        Date expected = parseISO("2017-02-04T10:10:10");

        assertThat(actual, equalTo(expected));
    }

    @Test(expected = NullPointerException.class)
    public void calcIncOrDecDays_WhenNull() {
        Date actual = DateUtils.calcIncOrDecDays(null, 0);
    }

    @Test
    public void toString_WhenConvertWithoutTime() {
        String strDate = DateUtils.toString(parseISO("2017-02-02T10:10:10"), DateUtils.ONLY_DATE_FORMAT_ISO);
        assertThat(strDate, IsEqual.equalTo("2017-02-02"));
    }

    @Test
    public void toString_WhenConvertWithTime() {
        String strDate = DateUtils.toString(parseISO("2017-02-02"), DateUtils.DATE_FORMAT_ISO);
        assertThat(strDate, IsEqual.equalTo("2017-02-02T00:00:00"));
    }

    @Test
    public void toString_WhenObjectDate() {
        Object obj = parseISO("2017-02-02");
        String strDate = DateUtils.toStringISOIfDate(obj);
        assertThat(strDate, IsEqual.equalTo("2017-02-02T00:00:00"));
    }

    @Test
    public void toString_WhenObjectLong() {
        Object obj = 123L;
        String strDate = DateUtils.toStringISOIfDate(obj);
        assertThat(strDate, IsEqual.equalTo("123"));
    }
}