package com.android.qdhhh.fakebeizhongyi.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by qdhhh on 2016/9/23.
 */

public class MD5Utils {
    public static String get_MD5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hei, MD5 should be supported!Check it please!", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Hei, UTF-8 should be supported!Check it please!", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
