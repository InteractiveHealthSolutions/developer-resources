package com.ihsinformatics;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
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
    public final void testConvertToSQL() {
	fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetSqlDate() {
	fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetSqlDateTime() {
	fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testFromString() {
	fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testToStringDateString() {
	fail("Not yet implemented"); // TODO
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
	// should pass
	Calendar expected = Calendar.getInstance();
	expected.set(Calendar.YEAR, 2018);
	expected.set(Calendar.MONTH, 3);
	expected.set(Calendar.DATE, 4);
	expected.set(Calendar.HOUR, 12);
	expected.set(Calendar.MINUTE, 12);
	expected.set(Calendar.SECOND, 12);
	Date actual = DateTimeUtil.fromSqlDateString("2018-04-04 12:12:12");
	assertEquals("", expected.getTime().getTime(), actual.getTime(), 999);

    }

    @Test
    public final void testToSqlDateString() {
	fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testToSqlDateTimeString() {
	fail("Not yet implemented"); // TODO
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
	String expected = "HHmm";
	for (String time : times) {
	    String actual = DateTimeUtil.detectDateFormat(time);
	    assertTrue("Incorrect time format detected for " + time,
		    actual.equals(expected));
	}
	// should not pass 28:59
	times = Arrays.asList("00:00", "12:12", "23:59", "28:59");
	expected = "HH:mm";
	for (String time : times) {
	    String actual = DateTimeUtil.detectDateFormat(time);
	    assertTrue("Incorrect time format detected for " + time,
		    actual.equals(expected));
	}

	// conflict HHmmss with ddMMyy
	times = Arrays.asList("000000", "121212", "235959", "285959", "295959");
	expected = "HHmmss";
	for (String time : times) {
	    String actual = DateTimeUtil.detectDateFormat(time);
	    assertTrue("Incorrect time format detected for " + time,
		    actual.equals(expected));
	}

	times = Arrays.asList("00:00:00", "12:12:12", "23:59:59", "28:59:59",
		"29:59:59");
	expected = "HH:mm:ss";
	for (String time : times) {
	    String actual = DateTimeUtil.detectDateFormat(time);
	    assertTrue("Incorrect time format detected for " + time,
		    actual.equals(expected));
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
	dates = Arrays.asList("12/12/2012", "12/31/2000");
	expected = "MM/dd/yy";
	for (String date : dates) {
	    String actual = DateTimeUtil.detectDateFormat(date);
	    assertTrue("Incorrect time format detected for " + date,
		    actual.equals(expected));
	}

	// conflict dd/MM/yy with MM/dd/yy
	dates = Arrays.asList("12/12/12");
	expected = "MM/dd/yy";
	for (String date : dates) {
	    String actual = DateTimeUtil.detectDateFormat(date);
	    assertTrue("Incorrect time format detected for " + date,
		    actual.equals(expected));
	}
    }

    @Test
    public final void testDetectDateTimeFormats() {
	List<String> dates = Arrays.asList("31/12/2000 23:59:59",
		"12/12/2012 00:00:00", "32/13/2000 28:59:59");
	String expected = "dd/MM/yyyy HH:mm:ss";
	for (String date : dates) {
	    String actual = DateTimeUtil.detectDateFormat(date);
	    System.out.println(date);
	    assertTrue("Incorrect time format detected for " + date,
		    actual.equals(expected));
	}
    }

}
