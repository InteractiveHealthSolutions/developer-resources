/*
Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */
package com.ihsinformatics;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ihsinformatics.util.VersionUtil;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
public class VersionUtilTest {

    VersionUtil zeroVersion;
    VersionUtil alphaVersion;
    VersionUtil betaVersion;
    VersionUtil release1;
    VersionUtil release2;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	zeroVersion = new VersionUtil(false, false, false, 0, 0, 0);
	alphaVersion = new VersionUtil(true, false, false, 0, 1, 6);
	betaVersion = new VersionUtil(false, true, false, 0, 9, 1);
	release1 = new VersionUtil(false, false, true, 1, 5, 0);
	release2 = new VersionUtil(false, false, true, 2, 0, 0);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.VersionUtil#parseVersion(java.lang.String)}.
     */
    @Test
    public void testParseVersion() {
	VersionUtil zero = new VersionUtil();
	VersionUtil alpha = new VersionUtil();
	VersionUtil beta = new VersionUtil();
	VersionUtil release = new VersionUtil();
	try {
	    zero.parseVersion("0.0.0");
	    alpha.parseVersion("0.1.6-alpha");
	    beta.parseVersion("0.9.1-BETA");
	    release.parseVersion("1.5.0");
	} catch (Exception e) {
	    e.printStackTrace();
	}
	assertTrue("Parsing zero version failed",
		zero.hashCode() == zeroVersion.hashCode());
	assertTrue("Parsing alpha version failed",
		alpha.hashCode() == alphaVersion.hashCode());
	assertTrue("Parsing beta version failed",
		beta.hashCode() == betaVersion.hashCode());
	assertTrue("Parsing release version failed",
		release.hashCode() == release1.hashCode());
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.VersionUtil#isCompatible(com.ihsinformatics.util.VersionUtil)}
     * .
     */
    @Test
    public void testIsCompatible() {
	VersionUtil alphaCompatible = new VersionUtil(true, false, false, 0, 1,
		9);
	VersionUtil betaCompatible = new VersionUtil(false, true, false, 0, 9,
		8);
	VersionUtil releaseCompatible = new VersionUtil(false, false, true, 1,
		5, 10);
	assertTrue("Alpha compatibility check failed",
		alphaVersion.isCompatible(alphaCompatible));
	assertTrue("Beta compatibility check failed",
		betaVersion.isCompatible(betaCompatible));
	assertTrue("Release compatibility check failed",
		release1.isCompatible(releaseCompatible));
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.VersionUtil#compareTo(com.ihsinformatics.util.VersionUtil)}
     * .
     */
    @Test
    public void testCompareTo() {
	assertSame("Comparing same versions failed", 0,
		release1.compareTo(release1));
	assertSame("Comparing zero and alpha versions failed", 1,
		zeroVersion.compareTo(alphaVersion));
	assertSame("Comparing alpha and beta versions failed", 1,
		alphaVersion.compareTo(betaVersion));
	assertSame("Comparing beta and release versions failed", 1,
		betaVersion.compareTo(release1));
	assertSame("Comparing release versions failed", 1,
		release1.compareTo(release2));
	assertSame("Comparing alpha and zero versions failed", -1,
		alphaVersion.compareTo(zeroVersion));
	assertSame("Comparing beta nad alpha versions failed", -1,
		betaVersion.compareTo(alphaVersion));
	assertSame("Comparing release and beta versions failed", -1,
		release1.compareTo(betaVersion));
	assertSame("Comparing release versions failed", -1,
		release2.compareTo(release1));
    }

    /**
     * Test method for {@link com.ihsinformatics.util.VersionUtil#toString()}.
     */
    @Test
    public void testToString() {
	assertTrue("Conversion to String failed", zeroVersion.toString()
		.equals("0.0.0"));
	assertTrue("Conversion to String failed", alphaVersion.toString()
		.equals("0.1.6-alpha"));
	assertTrue("Conversion to String failed", betaVersion.toString()
		.equals("0.9.1-beta"));
	assertTrue("Conversion to String failed",
		release1.toString().equals("1.5.0"));
    }
}
