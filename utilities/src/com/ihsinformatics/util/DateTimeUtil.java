/*
Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */
package com.ihsinformatics.util;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for several Date and Time functions
 * 
 * @author owais.hussain@ihsinformatics.com
 *
 */
public class DateTimeUtil {
    @Deprecated
    public static final String FE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    @Deprecated
    public static final String FE_FORMAT_TRUNC = "dd/MM/yyyy";
    @Deprecated
    public static final String DOB_FORMAT = "dd/MM/yyyy";

    public static final String STANDARD_DATE = "dd/MM/yyyy";
    public static final String STANDARD_DATETIME = "dd/MM/yyyy HH:mm:ss";

    public static final String STANDARD_DATE_HYPHENATED = "dd-MM-yyyy";
    public static final String STANDARD_DATETIME_HYPHENATED = "dd-MM-yyyy HH:mm:ss";

    public static final String SQL_DATE = "yyyy-MM-dd";
    public static final String SQL_DATESTAMP = "yyyyMMdd";
    public static final String SQL_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String SQL_TIMESTAMP = "yyyyMMddHHmmss";

    public static final String US_DATE = "MM/dd/yyyy";
    public static final String US_SHORT_DATE = "MM/dd/yy";

    public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss"; // 1970-01-01T00:00:00.000
    public static final String ISO8601_FULL = "yyyy-MM-dd'T'HH:mm:ss.'Z'"; // 1970-01-01T00:00:00.000+0000

    @SuppressWarnings("serial")
    private static final Map<String, String> DATE_FORMATS = new HashMap<String, String>() {
	{
	    // Time only
	    put("^[0-2]\\d[0-5]\\d$", "HHmm");
	    put("^[0-2]\\d:[0-5]\\d$", "HH:mm");
	    put("^[0-2]\\d([0-5]\\d){2}$", "HHmmss");
	    put("^[0-2]\\d:[0-5]\\d:[0-5]\\d$", "HH:mm:ss");
	    // Date only
	    put("^\\d{2}[0-1]\\d[0-3]\\d$", "yyMMdd");
	    put("^\\d{4}[0-1]\\d[0-3]\\d$", "yyyyMMdd");
	    put("^\\d{2}-[0-1]\\d-[0-3]\\d$", "yy-MM-dd");
	    put("^\\d{4}-[0-1]\\d-[0-3]\\d$", "yyyy-MM-dd");
	    put("^[0-3]\\d-[0-1]\\d-\\d{2}$", "dd-MM-yy");
	    put("^[0-3]\\d-[0-1]\\d-\\d{4}$", "dd-MM-yyyy");
	    put("^[0-3]\\d [0-1]\\d \\d{2}$", "dd MM yy");
	    put("^[0-3]\\d [0-1]\\d \\d{4}$", "dd MM yyyy");
	    put("^[0-3]\\d/[0-1]\\d/\\d{2}$", "dd/MM/yy");
	    put("^[0-3]\\d/[0-1]\\d/\\d{4}$", "dd/MM/yyyy");
	    put("^[0-3]\\d [a-z]{3} \\d{2}$", "dd MMM yy");
	    put("^[0-3]\\d [a-z]{3} \\d{4}$", "dd MMM yyyy");
	    // Date and Time
	    put("^\\d{12,14}$", "tt");
	    put("^\\d{4}[0-1]\\d[0-3]\\d[0-2]\\d[0-5]\\d$", "yyyyMMddHHmm");
	    put("^\\d{4}[0-1]\\d[0-3]\\d [0-2]\\d[0-5]\\d$", "yyyyMMdd HHmm");
	    put("^\\d{4}[0-1]\\d[0-3]\\d[0-2]\\d([0-5]\\d){2}$",
		    "yyyyMMddHHmmss");
	    put("^\\d{4}[0-1]\\d[0-3]\\d [0-2]\\d([0-5]\\d){2}$",
		    "yyyyMMdd HHmmss");
	    put("^\\d{4}-[0-1]\\d-[0-3]\\d [0-2]\\d:[0-5]\\d$",
		    "yyyy-MM-dd HH:mm");
	    put("^\\d{4}-[0-1]\\d-[0-3]\\d [0-2]\\d:[0-5]\\d:[0-5]\\d$",
		    "yyyy-MM-dd HH:mm:ss");
	    // ISO8601
	    put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}\\.\\d{2,4}$",
		    "yyyy-MM-dd HH:mm:ss.'Z'");
	}
    };

    @Deprecated
    public static Date getDateFromString(String string, String format)
	    throws ParseException {
	SimpleDateFormat sdf = new SimpleDateFormat(format);
	return sdf.parse(string);
    }

    @Deprecated
    public static String convertToSQL(String string, String format) {
	SimpleDateFormat sdf = new SimpleDateFormat(format);
	Date date1;
	try {
	    date1 = sdf.parse(string);
	} catch (ParseException e) {
	    e.printStackTrace();
	    return null;
	}
	sdf.applyPattern(SQL_DATETIME);
	return sdf.format(date1);
    }

    @Deprecated
    public static String getSqlDate(Date date) {
	SimpleDateFormat sdf = new SimpleDateFormat(SQL_DATE);
	return sdf.format(date);
    }

    @Deprecated
    public static String getSqlDateTime(Date date) {
	SimpleDateFormat sdf = new SimpleDateFormat(SQL_DATETIME);
	return sdf.format(date);
    }

    public static Date fromString(String string, String format) {
	if (string == null) {
	    return null;
	}
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
	Date date;
	try {
	    date = simpleDateFormat.parse(string);
	} catch (ParseException e) {
	    try {
		simpleDateFormat = new SimpleDateFormat(
			detectDateFormat(string));
		date = simpleDateFormat.parse(string);
	    } catch (ParseException e2) {
		e2.printStackTrace();
		return null;
	    }
	}
	return date;
    }

    public static String toString(Date date, String format) {
	if (date == null) {
	    return null;
	}
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
	return simpleDateFormat.format(date);
    }

    public static Date fromSqlDateString(String sqlDate) {
	return fromString(sqlDate, SQL_DATE);
    }

    public static Date fromSqlDateTimeString(String sqlDate) {
	return fromString(sqlDate, SQL_DATETIME);
    }

    public static String toSqlDateString(Date date) {
	return toString(date, SQL_DATE);
    }

    public static String toSqlDateTimeString(Date date) {
	return toString(date, SQL_DATETIME);
    }

    /**
     * Returns the closed matching date format of given date as string
     * 
     * @param dateString
     * @return
     */
    public static String detectDateFormat(String dateString) {
	for (String regexp : DATE_FORMATS.keySet()) {
	    if (dateString.toLowerCase().matches(regexp)) {
		return DATE_FORMATS.get(regexp);
	    }
	}
	throw new InvalidParameterException(
		"Given date does not match any of the standad conventions.");
    }
}
