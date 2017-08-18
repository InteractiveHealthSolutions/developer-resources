/*
Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */
package com.ihsinformatics.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class contains several methods for String manupulation that are not
 * available in built in libraries
 * 
 * @author owais.hussain@ihsinformatics.com
 *
 */
public class StringUtil {

    public StringUtil() {
    }

    /**
     * Concatenates an array of Strings into single string
     * 
     * @param array
     * @param separator
     *            the string to be inserted between two concatenations, passing
     *            null or empty string will insert nothing
     * @return
     */
    public static String concatenate(String[] array, String separator) {
	StringBuffer string = new StringBuffer();
	for (String s : array) {
	    string.append(s);
	    string.append(separator == null ? "" : separator);
	}
	return string.toString();
    }

    /**
     * Generates a random string of given length
     * 
     * @param length
     * @param numeric
     *            : when true, 0-9 will be included in the string
     * @param alpha
     *            : when true, A-Z will be included in the string
     * @param caseSensitive
     *            : when true, A-Z and a-z will be included in the string
     * @return
     */
    public String randomString(int length, boolean numeric, boolean alpha,
	    boolean caseSensitive) {
	String characters = "";
	if (numeric) {
	    characters = "0123456789";
	}
	if (alpha) {
	    characters += "ACEFGHIJKLOQRSTUVWXYZ";
	    if (caseSensitive) {
		characters += "acefghijkloqrstuvwxyz";
	    }
	}
	Random rand = new Random();
	char[] text = new char[length];
	for (int i = 0; i < length; i++) {
	    text[i] = characters.charAt(rand.nextInt(characters.length()));
	}
	return new String(text);
    }

    /**
     * Generates all combinations of a given string, of given length and stores
     * in output file
     * 
     * @param str
     * @param length
     * @param output
     * @throws IOException
     */
    public void stringCombinations(String str, int length, File output)
	    throws IOException {
	short bufferSize = 10000;
	long n = 0;
	ArrayList<String> list = new ArrayList<String>(bufferSize);
	FileWriter writer = new FileWriter(output);
	int total = ((Double) Math.pow(2, str.length())).intValue() - 1;
	for (int i = 0; i < total; i++) {
	    String combination = "";
	    char[] charArray = new StringBuilder(Integer.toBinaryString(i))
		    .toString().toCharArray();
	    for (int j = 0; j < charArray.length; j++)
		if (charArray[j] == '0')
		    combination += str.charAt(j);
	    if (combination.length() == length) {
		list.add(combination);
		n++;
	    }
	    // System.out.print(combination + " ");
	    if (list.size() == bufferSize) {
		System.out.println("Generated " + n + " combinations...");
		for (String s : list) {
		    writer.write(s + "\r\n");
		}
		writer.flush();
		list = new ArrayList<String>();
	    }
	}
	for (String s : list) {
	    writer.write(s + "\r\n");
	}
	writer.flush();
	writer.close();
    }
}
