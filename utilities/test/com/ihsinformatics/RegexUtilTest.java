/**
 * Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
 * You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
 * Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 * Contributors: Owais
 */
package com.ihsinformatics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ihsinformatics.util.RegexUtil;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
public class RegexUtilTest {

    /**
     * Test method for
     * {@link com.ihsinformatics.util.RegexUtil#isNumeric(java.lang.String, boolean)}
     * .
     */
    @Test
    public final void testIsNumeric() {
	String[] validIntegers = {"0", "1", "9999", "-9999"};
	String[] invalidIntegers = { "1.5", "2e14" };
	String[] validFloat = {"0", "1", "9999", "-9999", "0.0", "1.1", "99.99", "-99.99"};
	String[] invalidFloat = { "1..5", ".55" };
	for (String s : validIntegers) {
	    assertTrue(s + " is valid but rejected", RegexUtil.isNumeric(s, false));
	}
	for (String s : invalidIntegers) {
	    assertFalse(s + " is invalid but accepted", RegexUtil.isNumeric(s, false));
	}
	for (String s : validFloat) {
	    assertTrue(s + " is valid but rejected", RegexUtil.isNumeric(s, true));
	}
	for (String s : invalidFloat) {
	    assertFalse(s + " is invalid but accepted", RegexUtil.isNumeric(s, true));
	}
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.RegexUtil#isWord(java.lang.String)}.
     */
    @Test
    public final void testIsWord() {
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.RegexUtil#isAlphaNumeric(java.lang.String)}.
     */
    @Test
    public final void testIsAlphaNumeric() {
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.RegexUtil#isContactNumber(java.lang.String)}.
     */
    @Test
    public final void testIsContactNumber() {
	String[] rightNumbers = {"03452345345", "+923452345345", "0345-2345345", "0345 2345345", "+92-345-2345345"};
	String[] wrongNumbers = {"0092-00-356-9887", "00-3452345345", "+92055alpha55", "99#255689", "*55554444889"};
	for (String num : rightNumbers)
	    assertTrue(num + " is valid phone number but rejected", RegexUtil.isContactNumber(num));
	for (String num : wrongNumbers)
	    assertFalse(num + " is invalid phone number but accepted", RegexUtil.isContactNumber(num));
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.RegexUtil#isEmailAddress(java.lang.String)}.
     */
    @Test
    public final void testIsEmailAddress() {
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.RegexUtil#isValidDate(java.lang.String)}.
     */
    @Test
    public final void testIsValidDate() {
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.RegexUtil#isValidTime(java.lang.String, boolean)}
     * .
     */
    @Test
    public final void testIsValidTime() {
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.RegexUtil#isValidNIC(java.lang.String)}.
     */
    @Test
    public final void testIsValidNIC() {
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.RegexUtil#isValidURL(java.lang.String)}.
     */
    @Test
    public final void testIsValidURL() {
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.RegexUtil#isValidSMS(java.lang.String)}.
     */
    @Test
    public final void testIsValidSMS() {
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.RegexUtil#isValidId(java.lang.String)}.
     */
    @Test
    public final void testIsValidId() {
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.RegexUtil#isValidCheckDigit(java.lang.String)}.
     */
    @Test
    public final void testIsValidCheckDigit() {
    }
    
    /**
     * Test method for
     * {@link com.ihsinformatics.util.RegexUtil#isValidOpenText(java.lang.String)}.
     */
    @Test
    public final void testIsValidOpenText() {
    }
}
