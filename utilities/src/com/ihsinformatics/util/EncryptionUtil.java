/*
Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */
package com.ihsinformatics.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * @author owais.hussain@ihsinformatics.com
 */
public class EncryptionUtil {
    private String key;

    public EncryptionUtil() {
        key = "MyAwesomeKey";
    }

    public EncryptionUtil(String key) {
        this.key = key;
    }

    public static void main(String[] args) {
        // no change
        EncryptionUtil obj = new EncryptionUtil("AllDayIDreamAboutCode");
        try {
            byte[] enc = obj.encrypt("c1267d24-3188-11e8-9b75-0a0027000002");
            byte[] encFromStr = new String(enc).getBytes();
            String dec = obj.decrypt(encFromStr);
            for (byte b : encFromStr) {
                System.out.print(b + ", ");
            }
            System.out.println("\n" + new String(enc));
            System.out.println(dec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Encrypt given message using DESede algorithm
     *
     * @param message
     * @return encrypted byte array
     * @throws Exception
     */
    public byte[] encrypt(String message) throws Exception {
        final MessageDigest md = MessageDigest.getInstance("md5");
        final byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
        final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        for (int j = 0, k = 16; j < 8; ) {
            keyBytes[k++] = keyBytes[j++];
        }
        final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        final byte[] plainTextBytes = message.getBytes("utf-8");
        final byte[] cipherText = cipher.doFinal(plainTextBytes);
        return cipherText;
    }

    /**
     * Decrypt given encrypted message using DESede algorithm and key
     *
     * @param message
     * @return string containing decrypted text
     * @throws Exception
     */
    public String decrypt(byte[] message) throws Exception {
        final MessageDigest md = MessageDigest.getInstance("md5");
        final byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
        final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        for (int j = 0, k = 16; j < 8; ) {
            keyBytes[k++] = keyBytes[j++];
        }
        final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        decipher.init(Cipher.DECRYPT_MODE, key, iv);
        final byte[] plainText = decipher.doFinal(message);
        return new String(plainText, "UTF-8");
    }
}
