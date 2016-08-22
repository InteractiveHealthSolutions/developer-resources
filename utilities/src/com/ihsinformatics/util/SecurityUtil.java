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
 */
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class provides encryption and decryption utility
 * 
 * @author http://www.java2s.com
 * 
 */
public class SecurityUtil {
    private static final String HASHING_ALGORITHM = "SHA1";
    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String CHARACTER_SET = "UTF-8";
    private static final String ENCRYPTION_KEY = "Tt3tr@NN!tr0Ttoluene";
    private static final int BLOCKS = 128;
    
    /**
     * Compare the given hash and the given string-to-hash to see if they are
     * equal. The string-to-hash is usually of the form password + salt. <br/>
     * <br/>
     * This should be used so that this class can compare against the new
     * correct hashing algorithm and the old incorrect hashin algorithm.
     * 
     * @param hashedPassword
     *            a stored password that has been hashed previously
     * @param passwordToHash
     *            a string to encode/hash and compare to hashedPassword
     * @return true/false whether the two are equal
     * @throws Exception
     * @should match strings hashed with incorrect sha1 algorithm
     * @should match strings hashed with sha1 algorithm
     * @should match strings hashed with sha512 algorithm and 128 characters
     *         salt
     */
    public static boolean hashMatches(String hashedPassword,
	    String passwordToHash) throws Exception {
	if (hashedPassword == null || passwordToHash == null)
	    throw new Exception(
		    "Neither the hashed password or the password to hash cannot be null");

	return hashedPassword.equals(encodeStringSHA512(passwordToHash))
		|| hashedPassword.equals(encodeStringSHA1(passwordToHash))
		|| hashedPassword
			.equals(incorrectlyEncodeString(passwordToHash));
    }

    /**
     * This method will hash <code>strToEncode</code> using the preferred
     * algorithm
     * 
     * @param strToEncode
     *            string to encode
     * @return the SHA-512 encryption of a given string
     * @should encode strings to 128 characters
     */
    public static String encodeStringSHA512(String strToEncode) throws Exception {
	MessageDigest md;
	byte[] input;
	try {
	    md = MessageDigest.getInstance("SHA-512");
	    input = strToEncode.getBytes(CHARACTER_SET);
	} catch (NoSuchAlgorithmException e) {
	    throw new Exception(
		    "System cannot find password encryption algorithm", e);
	} catch (UnsupportedEncodingException e) {
	    throw new Exception("System cannot find " + CHARACTER_SET
		    + " CHARACTER_SET", e);
	}
	return hexString(md.digest(input));
    }

    /**
     * This method will hash <code>strToEncode</code> using the old SHA-1
     * algorithm.
     * 
     * @param strToEncode
     *            string to encode
     * @return the SHA-1 encryption of a given string
     */
    private static String encodeStringSHA1(String strToEncode) throws Exception {
	MessageDigest md;
	byte[] input;
	try {
	    md = MessageDigest.getInstance(HASHING_ALGORITHM);
	    input = strToEncode.getBytes(CHARACTER_SET);
	} catch (NoSuchAlgorithmException e) {
	    // Yikes! Can't encode password...what to do?
	    throw new Exception("System cannot find SHA1 encryption algorithm",
		    e);
	} catch (UnsupportedEncodingException e) {
	    throw new Exception("System cannot find " + CHARACTER_SET
		    + " CHARACTER_SET", e);
	}
	return hexString(md.digest(input));
    }

    /**
     * Convenience method to convert a byte array to a string
     * 
     * @param b
     *            Byte array to convert to HexString
     * @return Hexidecimal based string
     */
    private static String hexString(byte[] block) {
	StringBuffer buf = new StringBuffer();
	char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'a', 'b', 'c', 'd', 'e', 'f' };
	int len = block.length;
	int high = 0;
	int low = 0;
	for (int i = 0; i < len; i++) {
	    high = ((block[i] & 0xf0) >> 4);
	    low = (block[i] & 0x0f);
	    buf.append(hexChars[high]);
	    buf.append(hexChars[low]);
	}

	return buf.toString();
    }

    /**
     * This method will hash <code>strToEncode</code> using SHA-1 and the
     * incorrect hashing method that sometimes dropped out leading zeros.
     * 
     * @param strToEncode
     *            string to encode
     * @return the SHA-1 encryption of a given string
     */
    private static String incorrectlyEncodeString(String strToEncode)
	    throws Exception {
	String algorithm = "SHA1";
	MessageDigest md;
	byte[] input;
	try {
	    md = MessageDigest.getInstance(algorithm);
	    input = strToEncode.getBytes(CHARACTER_SET);
	} catch (NoSuchAlgorithmException e) {
	    throw new Exception("System cannot find SHA1 encryption algorithm",
		    e);
	} catch (UnsupportedEncodingException e) {
	    throw new Exception("System cannot find " + CHARACTER_SET
		    + " CHARACTER_SET", e);
	}
	return incorrectHexString(md.digest(input));
    }

    /**
     * This method used to be the simple hexString method, however, as pointed
     * out in ticket http://dev.openmrs.org/ticket/1178, it was not working
     * correctly. Authenticated still needs to occur against both this method
     * and the correct hex string, so this wrong implementation will remain
     * until we either force users to change their passwords, or we just decide
     * to invalidate them.
     * 
     * @param b
     * @return the old possibly less than 40 characters hashed string
     */
    private static String incorrectHexString(byte[] b) {
	if (b == null || b.length < 1)
	    return "";
	StringBuffer s = new StringBuffer();
	for (int i = 0; i < b.length; i++) {
	    s.append(Integer.toHexString(b[i] & 0xFF));
	}
	return new String(s);
    }

    /**
     * This method will generate a random string
     * 
     * @return a secure random token.
     */
    public static String getRandomToken() throws Exception {
	Random rng = new Random();
	return encodeStringSHA512(Long.toString(System.currentTimeMillis())
		+ Long.toString(rng.nextLong()));
    }

    /**
     * generate a new secret key; should only be called once in order to not
     * invalidate all encrypted data
     * 
     * @return generated secret key byte array
     * @throws Exception
     * @since 1.9
     */
    public static byte[] generateNewSecretKey() throws Exception {
	// Get the KeyGenerator
	KeyGenerator kgen = null;
	try {
	    kgen = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
	} catch (NoSuchAlgorithmException e) {
	    throw new Exception("Could not generate cipher key", e);
	}
	kgen.init(BLOCKS);
	SecretKey skey = kgen.generateKey();
	return skey.getEncoded();
    }

    /**
     * Encrypt a string using pre-defined Algorithm and Key
     * 
     * @param text
     * @return
     */
    public static byte[] encrypt(String text) {
	try {
	    byte[] rawKey = getRawKey(ENCRYPTION_KEY.getBytes(CHARACTER_SET));
	    SecretKeySpec skeySpec = new SecretKeySpec(rawKey,
		    ENCRYPTION_ALGORITHM);
	    Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	    return cipher.doFinal(text.getBytes(CHARACTER_SET));
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	} catch (IllegalBlockSizeException e) {
	    e.printStackTrace();
	} catch (BadPaddingException e) {
	    e.printStackTrace();
	} catch (InvalidKeyException e) {
	    e.printStackTrace();
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	} catch (NoSuchPaddingException e) {
	    e.printStackTrace();
	}
	return null;
    }

    /**
     * Decrypt a string using pre-defined Algorithm and Key
     * 
     * @param data
     * @return
     */
    public static String decrypt(byte[] data) {
	try {
	    byte[] rawKey = getRawKey(ENCRYPTION_KEY.getBytes(CHARACTER_SET));
	    SecretKeySpec skeySpec = new SecretKeySpec(rawKey,
		    ENCRYPTION_ALGORITHM);
	    Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
	    cipher.init(Cipher.DECRYPT_MODE, skeySpec);
	    byte[] decrypted = cipher.doFinal(data);
	    return new String(decrypted);
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	} catch (NoSuchPaddingException e) {
	    e.printStackTrace();
	} catch (InvalidKeyException e) {
	    e.printStackTrace();
	} catch (IllegalBlockSizeException e) {
	    e.printStackTrace();
	} catch (BadPaddingException e) {
	    e.printStackTrace();
	}
	return null;
    }

    private static byte[] getRawKey(byte[] seed) {
	try {
	    KeyGenerator kgen = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
	    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
	    sr.setSeed(seed);
	    kgen.init(BLOCKS, sr);
	    SecretKey skey = kgen.generateKey();
	    byte[] raw = skey.getEncoded();
	    return raw;
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	}
	return null;
    }
}
