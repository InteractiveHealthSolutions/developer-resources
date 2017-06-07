/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 */

/**
 * 
 */
package com.ihsinformatics.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * This utility provides helping methods for Collections and Data structures
 * 
 * @author owais.hussain@ihsinformatics.com
 *
 */
public class CollectionsUtil {

    /**
     * Converts an array of Strings into an ArrayList object
     * 
     * @param array
     * @return
     */
    public static ArrayList<String> toArrayList(String[] array) {
	ArrayList<String> arrayList = new ArrayList<String>(array.length);
	for (String s : array)
	    arrayList.add(s);
	return arrayList;
    }

    /**
     * Casts a List into given class and returns
     * 
     * @param clazz
     * @param c
     * @return
     */
    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
	List<T> r = new ArrayList<T>(c.size());
	for (Object o : c)
	    r.add(clazz.cast(o));
	return r;
    }

    /**
     * Sorts an array of String using Java's built-in Merge sort algorithm
     * 
     * @param list
     * @return
     */
    public static String[] sortList(String[] list) {
	Arrays.sort(list);
	return list;
    }

    /**
     * Converts an array of String values into an ArrayList of specified class.
     * The values unconvertible are returned as null, but exception is printed
     * on console
     * 
     * @param values
     * @param outputClass
     * @return
     */
    public static <T> ArrayList<T> convert(String[] values, Class<T> outputClass) {
	ArrayList<T> converted = new ArrayList<T>();
	for (String s : values) {
	    converted.add(convert(s, outputClass));
	}
	return converted;
    }

    /**
     * Generally, convert an object of one class into another.
     * 
     * @param input
     * @param outputClass
     * @return
     */
    public static <I, T> T convert(I input, Class<T> outputClass) {
	try {
	    return input == null ? null : outputClass.getConstructor(
		    String.class).newInstance(input.toString());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    /**
     * Search for any object in given list
     * 
     * @param list
     * @param item
     * @return
     */
    public static <T> List<T> search(List<T> list, Object item) {
	List<T> r = new ArrayList<T>();
	for (T t : list) {
	    if (t.hashCode() == item.hashCode()) {
		r.add(t);
	    }
	}
	return r;
    }
}
