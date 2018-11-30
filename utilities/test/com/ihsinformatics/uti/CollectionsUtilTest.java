/* Copyright(C) 2017 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 */

package com.ihsinformatics.uti;

import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.ihsinformatics.util.CollectionsUtil;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
public class CollectionsUtilTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.CollectionsUtil#toArrayList(java.lang.String[])}
     * .
     */
    @Test
    @Ignore
    public final void testToArrayList() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.CollectionsUtil#castList(java.lang.Class, java.util.Collection)}
     * .
     */
    @Test
    @Ignore
    public final void testCastList() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.CollectionsUtil#sortList(java.lang.String[])}
     * .
     */
    @Test
    @Ignore
    public final void testSortList() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.CollectionsUtil#convert(java.lang.String[], java.lang.Class)}
     * .
     */
    @Test
    @Ignore
    public final void testConvertStringArrayClassOfT() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.CollectionsUtil#convert(java.lang.Object, java.lang.Class)}
     * .
     */
    @Test
    @Ignore
    public final void testConvertIClassOfT() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.CollectionsUtil#search(java.util.List, java.lang.Object)}
     * .
     */
    @Test
    @Ignore
    public final void testSearch() {
	fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.CollectionsUtil#restrictNestedProperties(java.lang.Object, java.lang.String, int)}
     * .
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @Test
    public final void testRestrictNestedPropertiesToLevel0()
	    throws IllegalArgumentException, IllegalAccessException,
	    NoSuchFieldException, SecurityException {
	TestLocation parent = new TestLocation("Parent");
	parent.setLocationId(1);
	TestLocation child = new TestLocation("Child");
	child.setLocationId(2);
	child.setParent(parent);
	TestLocation object = (TestLocation) CollectionsUtil.restrictNestedProperties(
		child, "parent", 0);
	Assert.assertTrue(object.getParent() == null);
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.CollectionsUtil#restrictNestedProperties(java.lang.Object, java.lang.String, int)}
     * .
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @Test
    public final void testRestrictNestedPropertiesToLevel1()
	    throws IllegalArgumentException, IllegalAccessException,
	    NoSuchFieldException, SecurityException {
	TestLocation grandParent = new TestLocation("Grand Parent");
	grandParent.setLocationId(1);
	TestLocation parent = new TestLocation("Parent");
	parent.setLocationId(2);
	parent.setParent(grandParent);
	TestLocation child = new TestLocation("Child");
	child.setLocationId(3);
	child.setParent(parent);
	TestLocation object = (TestLocation) CollectionsUtil.restrictNestedProperties(
		child, "parent", 1);
	Assert.assertTrue(object.getParent() == parent);
	Assert.assertTrue(object.getParent().getParent() == null);
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.CollectionsUtil#restrictNestedProperties(java.lang.Object, java.lang.String, int)}
     * .
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @Test
    public final void testRestrictNestedPropertiesToLevel2()
	    throws IllegalArgumentException, IllegalAccessException,
	    NoSuchFieldException, SecurityException {
	TestLocation greatGrandParent = new TestLocation("Great Grand Parent");
	greatGrandParent.setLocationId(1);
	TestLocation grandParent = new TestLocation("Grand Parent");
	grandParent.setLocationId(2);
	grandParent.setParent(greatGrandParent);
	TestLocation parent = new TestLocation("Parent");
	parent.setLocationId(3);
	parent.setParent(grandParent);
	TestLocation child = new TestLocation("Child");
	child.setLocationId(4);
	child.setParent(parent);
	TestLocation object = (TestLocation) CollectionsUtil.restrictNestedProperties(
		child, "parent", 2);
	Assert.assertTrue(object.getParent() == parent);
	Assert.assertTrue(object.getParent().getParent() == grandParent);
	Assert.assertTrue(object.getParent().getParent().getParent() == null);
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.CollectionsUtil#removeCyclicNodes(java.lang.Object, java.lang.String, java.lang.String, java.util.Queue)}
     * .
     * 
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws NoSuchFieldException
     */
    @Test
    public final void testRemoveReflexiveCyclicNodes()
	    throws NoSuchFieldException, IllegalArgumentException,
	    IllegalAccessException {
	TestLocation location = new TestLocation("Minar-e-Pakistan");
	location.setLocationId(1);
	location.setParent(location);
	location = (TestLocation) CollectionsUtil.removeCyclicNodes(location,
		"parent", "locationId", null);
	Assert.assertTrue(location.getParent() == null);
    }

    /**
     * Test method for
     * {@link com.ihsinformatics.util.CollectionsUtil#removeCyclicNodes(java.lang.Object, java.lang.String, java.lang.String, java.util.Queue)}
     * .
     * 
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws NoSuchFieldException
     */
    @Test
    public final void testRemoveNestedCyclicNodes()
	    throws NoSuchFieldException, IllegalArgumentException,
	    IllegalAccessException {
	TestLocation grandParent = new TestLocation("Grand Parent");
	grandParent.setLocationId(1);
	TestLocation parent = new TestLocation("Parent");
	parent.setLocationId(2);
	parent.setParent(grandParent);
	TestLocation child = new TestLocation("Child");
	child.setLocationId(3);
	child.setParent(parent);
	grandParent.setParent(child);

	TestLocation cleanChild = (TestLocation) CollectionsUtil.removeCyclicNodes(
		child, "parent", "locationId", null);
	Assert.assertTrue(cleanChild.getParent() == parent);
	Assert.assertTrue(cleanChild.getParent().getParent() == grandParent);
	// TODO:
	// Assert.assertTrue(cleanChild.getParent().getParent().getParent() ==
	// null);
    }
}
