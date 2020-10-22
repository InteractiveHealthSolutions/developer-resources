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

import java.lang.reflect.Field;
import java.util.*;

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
     *            : array to convert
     * @return ArrayList of strings
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
     *            : class to cast
     * @param c
     *            : collection
     * @return List of objects casted into desired class
     */
    public static <T> List<T> castList(Class<? extends T> clazz,
                                       Collection<?> c) {
        List<T> r = new ArrayList<T>(c.size());
        for (Object o : c)
            r.add(clazz.cast(o));
        return r;
    }

    /**
     * Sorts an array of String using Java's built-in Merge sort algorithm
     *
     * @param list
     *            : array of strings to sort
     * @return array of sorted strings
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
     *            : array of strings
     * @param outputClass
     *            : class to convert into
     * @return converted strings
     */
    @Deprecated
    public static <T> ArrayList<T> convert(String[] values,
                                           Class<T> outputClass) {
        ArrayList<T> converted = new ArrayList<T>();
        for (String s : values) {
            converted.add(convert(s, outputClass));
        }
        return converted;
    }

    /**
     * Converts an array of String values into an ArrayList of specified class.
     * The values unconvertible are returned as null, but exception is printed
     * on console
     *
     * @param values
     *            : array of objects
     * @param outputClass
     *            : class to convert into
     * @return converted objects
     */
    public static <T> ArrayList<T> convert(Object[] values,
                                           Class<T> outputClass) {
        ArrayList<T> converted = new ArrayList<T>();
        for (Object s : values) {
            converted.add(convert(s, outputClass));
        }
        return converted;
    }

    /**
     * Generally, convert an object of one class into another.
     *
     * @param input
     *            : input object
     * @param outputClass
     *            : class to convert into
     * @return converted object
     */
    public static <I, T> T convert(I input, Class<T> outputClass) {
        try {
            return input == null ? null
                    : outputClass.getConstructor(String.class)
                    .newInstance(input.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Search for any object in given list
     *
     * @param list
     *            : List of objects
     * @param obj
     *            : object to search
     * @return object searched
     */
    public static <T> List<T> search(List<T> list, Object obj) {
        List<T> r = new ArrayList<T>();
        for (T t : list) {
            if (t.hashCode() == obj.hashCode()) {
                r.add(t);
            }
        }
        return r;
    }

    /**
     * Limit property name provided to specified depth of hierarchy. For
     * example, if child property of object A contains B and B contains C, then
     * limiting to depth 0 will nullify child of A; limiting to depth 1 will
     * keep B in A but will nullify C in child of B
     *
     * @param obj
     *            : object to restrict properties of
     * @param depth
     *            : level of search
     * @return cleansed object
     * @throws IllegalAccessException
     *             : on no access
     * @throws IllegalArgumentException
     *             : on invalid arguments
     * @throws SecurityException
     *             : on security blockage
     * @throws NoSuchFieldException
     *             : on absence of a given property
     */
    public static Object restrictNestedProperties(Object obj, String property,
                                                  int depth) throws IllegalArgumentException, IllegalAccessException,
            NoSuchFieldException, SecurityException {
        Field field = obj.getClass().getDeclaredField(property);
        field.setAccessible(true);
        if (depth == 0) {
            field.set(obj, null);
        } else if (depth == 1) {
            Object next = field.get(obj);
            field.set(next, null);
        } else {
            Object next = field.get(obj);
            if (next != null) {
                Object newNode = restrictNestedProperties(next, property,
                        depth - 1);
                field.set(obj, newNode);
            }
        }
        return obj;
    }

    /**
     * Search for cyclic objects within properties of input object and reduce
     * the hierarchy to non-cyclic child nodes. For example, if A object has B,
     * B has C and C has A, then A in C object will be nullified
     *
     * @param obj
     *            : object to restrict properties of
     * @param fieldName
     *            : property to remove
     * @param identifyingField
     *            : identifying property
     * @param queue
     *            : object queue
     * @return cleansed object
     * @throws IllegalAccessException
     *             : on no access
     * @throws IllegalArgumentException
     *             : on invalid arguments
     * @throws NoSuchFieldException
     *             : on absence of a given property
     */
    public static Object removeCyclicNodes(Object obj, String fieldName,
                                           String identifyingField, Queue<Object> queue)
            throws NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        // Initialize queue if required
        if (queue == null) {
            queue = new LinkedList<Object>();
        }
        Field field;
        Field idField;
        // Get field of given fieldName
        field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        // Get the value in the field
        Object fieldValue = field.get(obj);
        // If the value is null, then return
        if (fieldValue == null) {
            return obj;
        }
        // Get field of field value of given fieldName
        idField = fieldValue.getClass().getDeclaredField(identifyingField);
        idField.setAccessible(true);
        // If the value is equal to the object itself, then set null and return
        if (idField.get(obj).equals(idField.get(fieldValue))) {
            field.set(fieldValue, null);
            return obj;
        }
        // Search the queue; if the value already exists in the queue, then set
        // it null and return
        for (Iterator<Object> iter = queue.iterator(); iter.hasNext(); ) {
            Object queueObj = iter.next();
            Object id1 = idField.get(obj);
            Object id2 = idField.get(queueObj);
            if (id1.equals(id2)) {
                field.set(fieldValue, null);
                return obj;
            }
        }
        // Enqueue the value and recursively call the method on the field
        queue.add(obj);
        Object next = removeCyclicNodes(fieldValue, fieldName, identifyingField,
                queue);
        field.set(obj, next);
        return obj;
    }
}
