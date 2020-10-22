/*
Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */
package com.ihsinformatics.util;

/**
 * This class provides different algorithms to calculate checksum
 *
 * @author owais.hussain@ihsinformatics.com
 */
public class ChecksumUtil {

    // The multiplication table
    static int[][] d = new int[][]{{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
            {1, 2, 3, 4, 0, 6, 7, 8, 9, 5}, {2, 3, 4, 0, 1, 7, 8, 9, 5, 6},
            {3, 4, 0, 1, 2, 8, 9, 5, 6, 7}, {4, 0, 1, 2, 3, 9, 5, 6, 7, 8},
            {5, 9, 8, 7, 6, 0, 4, 3, 2, 1}, {6, 5, 9, 8, 7, 1, 0, 4, 3, 2},
            {7, 6, 5, 9, 8, 2, 1, 0, 4, 3}, {8, 7, 6, 5, 9, 3, 2, 1, 0, 4},
            {9, 8, 7, 6, 5, 4, 3, 2, 1, 0}};

    // The permutation table
    static int[][] p = new int[][]{{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
            {1, 5, 7, 6, 2, 8, 3, 0, 9, 4}, {5, 8, 0, 3, 7, 9, 6, 1, 4, 2},
            {8, 9, 1, 6, 0, 4, 3, 5, 2, 7}, {9, 4, 5, 3, 1, 2, 6, 8, 7, 0},
            {4, 2, 8, 6, 5, 7, 3, 9, 0, 1}, {2, 7, 9, 3, 8, 0, 6, 4, 1, 5},
            {7, 0, 4, 6, 9, 1, 3, 2, 5, 8}};

    // The inverse table
    static int[] inv = {0, 4, 3, 2, 1, 5, 6, 7, 8, 9};

    /**
     * Returns checksum digit calculated using Luhn algorithm
     *
     * @param id : the string ID to check
     * @return generated Luhn check digit
     * @throws Exception : if invalid character is present
     */
    public static int getLuhnChecksum(String id) throws Exception {
        String validChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVYWXZ_";
        id = id.trim().toUpperCase();
        int sum = 0;
        for (int i = 0; i < id.length(); i++) {
            char ch = id.charAt(id.length() - i - 1);
            if (validChars.indexOf(ch) == -1)
                throw new Exception("\"" + ch + "\" is an invalid character");
            int digit = ch - 48;
            int weight;
            if (i % 2 == 0) {
                weight = (2 * digit) - digit / 5 * 9;
            } else {
                weight = digit;
            }
            sum += weight;
        }
        sum = Math.abs(sum) + 10;
        return (10 - (sum % 10)) % 10;
    }

    /**
     * Matches the checksum against a string using Luhn algorithm
     *
     * @param id       : the string ID to check
     * @param checksum : the check digit to match
     * @return :true if check digit matches
     * @throws Exception : see <code>getLuhnChecksum</code>
     */
    public static boolean matchLuhnChecksum(String id, int checksum)
            throws Exception {
        return getLuhnChecksum(id) == checksum;
    }

    /**
     * Returns checksum digit calculated using Verhoeff algorithm
     *
     * @param id : the string ID to check
     * @return : generated Luhn check digit
     */
    public static int getVerhoeffCheck(String id) {
        int c = 0;
        int[] arr = stringToReversedIntArray(id);
        for (int i = 0; i < arr.length; i++) {
            c = d[c][p[((i + 1) % 8)][arr[i]]];
        }
        return inv[c];
    }

    /**
     * Validates that an entered number is Verhoeff compliant. NB: Make sure the
     * check digit is the last one.
     *
     * @param id : the string ID to validate
     * @return true if check the ID is valid
     */
    public static boolean validateVerhoeff(String id) {
        int c = 0;
        int[] myArray = stringToReversedIntArray(id);
        for (int i = 0; i < myArray.length; i++) {
            c = d[c][p[(i % 8)][myArray[i]]];
        }
        return (c == 0);
    }

    /**
     * Converts a string to a reversed integer array.
     *
     * @param id : the string ID to convert
     * @return the reversed integer array
     */
    private static int[] stringToReversedIntArray(String id) {

        int[] myArray = new int[id.length()];

        for (int i = 0; i < id.length(); i++) {
            myArray[i] = Integer.parseInt(id.substring(i, i + 1));
        }
        myArray = Reverse(myArray);
        return myArray;
    }

    /**
     * Reverses an int array
     *
     * @param array : the integer array to revers
     * @return reversed array
     */
    private static int[] Reverse(int[] array) {
        int[] reversed = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            reversed[i] = array[array.length - (i + 1)];
        }

        return reversed;
    }
}
