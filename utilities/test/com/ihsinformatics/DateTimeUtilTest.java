package com.ihsinformatics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ihsinformatics.util.DateTimeUtil;

public class DateTimeUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testFromString() throws ParseException {

	List<String> dates = Arrays.asList("201812312359", "200001010001",
		"200112312359");
	String format = "yyyyMMddHHmm";
	for (String date : dates) {
	    Date actual = DateTimeUtil.fromString(date, format);
	    Date expected = new SimpleDateFormat(format).parse(date);
	    assertEquals("Converted date and expected date is not same.",
		    expected.getTime(), actual.getTime(), 999);
	}
	dates = Arrays.asList("20181231 2359", "20000101 0001",
		"20011231 2359");
	format = "yyyyMMdd HHmm";
	for (String date : dates) {
	    Date actual = DateTimeUtil.fromString(date, format);
	    Date expected = new SimpleDateFormat(format).parse(date);
	    assertEquals("Converted date and expected date is not same.",
		    expected.getTime(), actual.getTime(), 999);
	}

	dates = Arrays.asList("20181231235959", "20000101000101",
		"20011231235959");
	format = "yyyyMMddHHmmss";
	for (String date : dates) {
	    Date actual = DateTimeUtil.fromString(date, format);
	    Date expected = new SimpleDateFormat(format).parse(date);
	    assertEquals("Converted date and expected date is not same.",
		    expected.getTime(), actual.getTime(), 999);
	}
	dates = Arrays.asList("20181231 235959", "20000101 000101",
		"20011231 235959");
	format = "yyyyMMdd HHmmss";
	for (String date : dates) {
	    Date actual = DateTimeUtil.fromString(date, format);
	    Date expected = new SimpleDateFormat(format).parse(date);
	    assertEquals("Converted date and expected date is not same.",
		    expected.getTime(), actual.getTime(), 999);
	}
	dates = Arrays.asList("2018-12-31 23:59", "2000-01-01 00:01",
		"2001-12-31 23:59");
	format = "yyyy-MM-dd HH:mm";
	for (String date : dates) {
	    Date actual = DateTimeUtil.fromString(date, format);
	    Date expected = new SimpleDateFormat(format).parse(date);
	    assertEquals("Converted date and expected date is not same",
		    expected.getTime(), actual.getTime(), 999);
	}

	dates = Arrays.asList("2018-12-31 23:59:59", "2000-01-01 00:01:01",
		"2001-12-31 23:59:59");
	format = "yyyy-MM-dd HH:mm:ss";
	for (String date : dates) {
	    Date actual = DateTimeUtil.fromString(date, format);
	    Date expected = new SimpleDateFormat(format).parse(date);
	    assertEquals("Converted date and expected date is not same",
		    expected.getTime(), actual.getTime(), 999);
	}

    }

    @Test
    public final void testFromSqlDateString() {
	Calendar expected = Calendar.getInstance();
	expected.set(Calendar.YEAR, 2018);
	expected.set(Calendar.MONTH, 3);
	expected.set(Calendar.DATE, 4);
	Date actual = DateTimeUtil.fromSqlDateString("2018-04-04");
	assertEquals("", expected.getTime().getTime(), actual.getTime(),
		86399999);

    }

    @Test
    public final void testFromSqlDateTimeString() {
	@SuppressWarnings("deprecation")
	Date expected = new Date(2018 - 1900, 0, 1, 12, 12, 12);
	Date actual = DateTimeUtil.fromSqlDateTimeString("2018-01-01 12:12:12");
	assertEquals("Converted date and expected date is not same",
		expected.getTime(), actual.getTime(), 999);
    }

    @Test
    public final void testToSqlDateString() {
	// TODO
    }

    @Test
    public final void testToSqlDateTimeString() {
	// TODO
    }

    @Test(expected = InvalidParameterException.class)
    public final void shouldThrowExceptionWhileDetectingFormat() {
	assertEquals("Should throw InvalidParameterException.",
		DateTimeUtil.detectDateFormat("9"));
	assertEquals("Should throw InvalidParameterException.",
		DateTimeUtil.detectDateFormat("99"));
	assertEquals("Should throw InvalidParameterException.",
		DateTimeUtil.detectDateFormat("999"));
	assertEquals("Should throw InvalidParameterException.",
		DateTimeUtil.detectDateFormat("9999"));
	assertEquals("Should throw InvalidParameterException.",
		DateTimeUtil.detectDateFormat("9:9"));
	assertEquals("Should throw InvalidParameterException.",
		DateTimeUtil.detectDateFormat("99:99"));

    }

    @Test
    public final void testDetectTimeFormats() {
	// should not pass 2859
	List<String> times = Arrays.asList("0000", "1212", "2359", "2859");
	String expectedTime = "HHmm";
	for (String time : times) {
	    String actual = DateTimeUtil.detectDateFormat(time);
	    assertTrue("Incorrect time format detected for " + time,
		    actual.equals(expectedTime));
	}
	// should not pass 28:59
	times = Arrays.asList("00:00", "12:12", "23:59", "28:59");
	expectedTime = "HH:mm";
	for (String time : times) {
	    String actual = DateTimeUtil.detectDateFormat(time);
	    assertTrue("Incorrect time format detected for " + time,
		    actual.equals(expectedTime));
	}

	// conflict HHmmss with ddMMyy
	times = Arrays.asList("000000", "121212", "235959", "285959", "295959");
	expectedTime = "HHmmss";
	String expectedDate = "yyMMdd";
	for (String time : times) {
	    String actual = DateTimeUtil.detectDateFormat(time);
	    assertTrue("Incorrect time format detected for " + time,
		    (actual.equals(expectedTime)
			    || actual.equals(expectedDate)));
	}

	times = Arrays.asList("00:00:00", "12:12:12", "23:59:59", "28:59:59",
		"29:59:59");
	expectedTime = "HH:mm:ss";
	for (String time : times) {
	    String actual = DateTimeUtil.detectDateFormat(time);
	    assertTrue("Incorrect time format detected for " + time,
		    actual.equals(expectedTime));
	}

    }

    @Test
    public final void testDetectDateFormats() {
	// Should not pass 32/13/2000
	List<String> dates = Arrays.asList("12/12/2012", "31/12/2000",
		"32/13/2000");
	String expected = "dd/MM/yyyy";
	for (String date : dates) {
	    String actual = DateTimeUtil.detectDateFormat(date);
	    assertTrue("Incorrect time format detected for " + date,
		    actual.equals(expected));
	}
	// Should not pass 32-13-2000
	dates = Arrays.asList("12-12-2012", "31-12-2000", "32-13-2000");
	expected = "dd-MM-yyyy";
	for (String date : dates) {
	    String actual = DateTimeUtil.detectDateFormat(date);
	    assertTrue("Incorrect time format detected for " + date,
		    actual.equals(expected));
	}

	// Should not pass 2000-13-32
	dates = Arrays.asList("2012-12-12", "2000-12-31", "2000-13-32");
	expected = "yyyy-MM-dd";
	for (String date : dates) {
	    String actual = DateTimeUtil.detectDateFormat(date);
	    assertTrue("Incorrect time format detected for " + date,
		    actual.equals(expected));
	}

	// Should not pass 20001332
	dates = Arrays.asList("20121212", "20001231", "20001332");
	expected = "yyyyMMdd";
	for (String date : dates) {
	    String actual = DateTimeUtil.detectDateFormat(date);
	    assertTrue("Incorrect time format detected for " + date,
		    actual.equals(expected));
	}

	// conflict dd/MM/yyyy with MM/dd/yyyy
	dates = Arrays.asList("12/12/2012", "31/12/2000");
	expected = "dd/MM/yyyy";
	for (String date : dates) {
	    String actual = DateTimeUtil.detectDateFormat(date);
	    assertTrue("Incorrect time format detected for " + date,
		    actual.equals(expected));
	}

	// conflict dd/MM/yy with MM/dd/yy
	dates = Arrays.asList("12/12/12");
	expected = "dd/MM/yy";
	for (String date : dates) {
	    String actual = DateTimeUtil.detectDateFormat(date);
	    assertTrue("Incorrect time format detected for " + date,
		    actual.equals(expected));
	}
    }

    @Test
    public final void testDetectDateTimeFormats() {
	// should not pass 200113322559
	List<String> dates = Arrays.asList("201812312359", "200001010001",
		"200113322559");
	String expected = "yyyyMMddHHmm";
	for (String date : dates) {
	    String actual = DateTimeUtil.detectDateFormat(date);
	    assertTrue("Incorrect time format detected for " + date,
		    actual.equals(expected));
	}

	// should not pass 20011332 2559
	dates = Arrays.asList("20181231 2359", "20000101 0001",
		"20011332 2559");
	expected = "yyyyMMdd HHmm";
	for (String date : dates) {
	    String actual = DateTimeUtil.detectDateFormat(date);
	    assertTrue("Incorrect time format detected for " + date,
		    actual.equals(expected));
	}

	// should not pass 20011332255959
	dates = Arrays.asList("20181231235959", "20000101000101",
		"20011332255959");
	expected = "yyyyMMddHHmmss";
	for (String date : dates) {
	    String actual = DateTimeUtil.detectDateFormat(date);
	    assertTrue("Incorrect time format detected for " + date,
		    actual.equals(expected));
	}

	// should not pass 20011332 255959
	dates = Arrays.asList("20181231 235959", "20000101 000101",
		"20011332 255959");
	expected = "yyyyMMdd HHmmss";
	for (String date : dates) {
	    String actual = DateTimeUtil.detectDateFormat(date);
	    assertTrue("Incorrect time format detected for " + date,
		    actual.equals(expected));
	}

	// should not pass 2001-13-32 25:59
	dates = Arrays.asList("2018-12-31 23:59", "2000-01-01 00:01",
		"2001-13-32 25:59");
	expected = "yyyy-MM-dd HH:mm";
	for (String date : dates) {
	    String actual = DateTimeUtil.detectDateFormat(date);
	    assertTrue("Incorrect time format detected for " + date,
		    actual.equals(expected));
	}

	// should not pass 2001-13-32 25:59:59
	dates = Arrays.asList("2018-12-31 23:59:59", "2000-01-01 00:01:01",
		"2001-13-32 25:59:59");
	expected = "yyyy-MM-dd HH:mm:ss";
	for (String date : dates) {
	    String actual = DateTimeUtil.detectDateFormat(date);
	    assertTrue("Incorrect time format detected for " + date,
		    actual.equals(expected));
	}

    }

}
