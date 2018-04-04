package com.ihsinformatics;

import static org.junit.Assert.fail;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ihsinformatics.util.DateTimeUtil;

import junit.framework.Assert;

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
	Assert.assertEquals("", expected.getTime().getTime(), actual.getTime(),
		86399999);
    }

    @Test
    public final void testFromSqlDateTimeString() {
	Calendar expected = Calendar.getInstance();
	expected.set(Calendar.YEAR, 2018);
	expected.set(Calendar.MONTH, 3);
	expected.set(Calendar.DATE, 4);
	expected.set(Calendar.HOUR, 12);
	expected.set(Calendar.MINUTE, 12);
	expected.set(Calendar.SECOND, 12);
	Date actual = DateTimeUtil.fromSqlDateString("2018-04-04 12:12:12");
	Assert.assertEquals("", expected.getTime().getTime(), actual.getTime(),
		999);
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
	Assert.assertEquals("Should throw InvalidParameterException.",
		DateTimeUtil.detectDateFormat("11"));
	Assert.assertEquals("Should throw InvalidParameterException.",
		DateTimeUtil.detectDateFormat("9999"));
	Assert.assertEquals("Should throw InvalidParameterException.",
		DateTimeUtil.detectDateFormat("121"));
    }

    @Test
    public final void testDetectTimeFormats() {
	List<String> times = Arrays.asList("0000", "1212", "2359", "2859");
	String expected = "HHmm";
	for (String time : times) {
	    String actual = DateTimeUtil.detectDateFormat(time);
	    Assert.assertTrue("Incorrect time format detected for " + time,
		    actual.equals(expected));
	}
	Assert.assertTrue("Incorrect time format detected.",
		DateTimeUtil.detectDateFormat("22:50").equals("HH:mm"));
	times = Arrays.asList("00:00:00", "23:59:59", "12:00:12");
	expected = "HH:mm:ss";
	for (String time : times) {
	    String actual = DateTimeUtil.detectDateFormat(time);
	    Assert.assertTrue("Incorrect time format detected for " + time,
		    actual.equals(expected));
	}
	Assert.assertTrue("Incorrect time format detected.",
		DateTimeUtil.detectDateFormat("235959").equals("HHmmss"));
    }

    @Test
    public final void testDetectDateFormats() {
	fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testDetectDateTimeFormats() {
	fail("Not yet implemented"); // TODO
    }

}
