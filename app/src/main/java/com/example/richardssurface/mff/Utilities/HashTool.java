package com.example.richardssurface.mff.Utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Richard's Surface on 4/29/2017.
 */

public class HashTool {

        //perform an HD5 hashing, (fix the bug that losing leading 0)
        public static final String md5Hashing(final String s) {
            final String MD5 = "MD5";
            try {
                // Create MD5 Hash
                MessageDigest digest = java.security.MessageDigest
                        .getInstance(MD5);
                digest.update(s.getBytes());
                byte messageDigest[] = digest.digest();

                // Create Hex String
                StringBuilder hexString = new StringBuilder();
                for (byte aMessageDigest : messageDigest) {
                    String h = Integer.toHexString(0xFF & aMessageDigest);
                    while (h.length() < 2)
                        h = "0" + h;//add losing leading 0
                    hexString.append(h);
                }
                return hexString.toString();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return "";
        }
}
