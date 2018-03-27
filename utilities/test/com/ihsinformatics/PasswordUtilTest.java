/* Copyright(C) 2018 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
*/

package com.ihsinformatics;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ihsinformatics.util.PasswordUtil;
import com.ihsinformatics.util.PasswordUtil.HashingAlgorithm;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
public class PasswordUtilTest {

    private PasswordUtil passwordUtil;
    private String salt;
    private String key;
    private int iterations;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	salt = "c1267d24-3188-11e8-9b75-0a0027000002";
	key = "AllDayIDreamAboutCode";
	iterations = 100;
	passwordUtil = new PasswordUtil(HashingAlgorithm.SHA512, salt,
		iterations);
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.PasswordUtil#encryptSalt(java.lang.String)}.
     */
    @Test
    public final void testEncryptSalt() {
	try {
	    byte[] encrypted = passwordUtil.encryptSalt(key);
	    byte[] expected = "ŠG«ø¨,j?-šÛe´KKŽBªˆø@øzj×â„AS{€ÖÉa0€ý"
		    .getBytes();
	    Assert.assertArrayEquals("Salt not ecrypted correctly.", encrypted,
		    expected);
	} catch (Exception e) {
	    fail("Exception thrown " + e.getMessage());
	}
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.PasswordUtil#decryptSalt(byte[], java.lang.String)}.
     */
    @Test
    public final void testDecryptSalt() {
	try {
	    byte[] encrypted = "ŠG«ø¨,j?-šÛe´KKŽBªˆø@øzj×â„AS{€ÖÉa0€ý"
		    .getBytes();
	    String decrypted = passwordUtil.decryptSalt(encrypted, key);
	    Assert.assertTrue("Salt not decrypted correctly.",
		    decrypted.equals(salt));
	} catch (Exception e) {
	    fail("Exception thrown " + e.getMessage());
	}
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.PasswordUtil#mergePasswordAndSalt(java.lang.String, java.lang.Object, boolean)}.
     */
    @Test
    public final void testMergePasswordAndSalt() {
	String password = "khuljasimsim";
	String expected = "khuljasimsim{" + salt + "}";
	Assert.assertTrue("Password and salt not merged correctly.",
		passwordUtil.mergePasswordAndSalt(password, true)
			.equals(expected));
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.PasswordUtil#match(java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testValidateMatch() {
	String password = "khuljasimsim";
	String passwordHash = passwordUtil.encode(password);
	Assert.assertTrue("Password should validate.",
		passwordUtil.match(passwordHash, password));
    }

}
