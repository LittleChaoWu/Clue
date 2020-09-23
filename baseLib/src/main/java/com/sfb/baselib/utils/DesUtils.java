/**
 * Copyright 2015 Software innovation and R & D center. All rights reserved.
 * File Name: DesUtils.java
 * Encoding UTF-8
 * Version: 0.0.1
 * History:	2016年10月31日
 */
package com.sfb.baselib.utils;

import android.util.Base64;

import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 3des加密解密（base64）
 */
public class DesUtils {

    // 向量
    private final static String iv = "01234567";
    // 加解密统一使用的编码方式  
    private final static String encoding = "utf-8";

    /**
     * 3DES加密
     *
     * @param plainText 内容
     * @param key       密匙不小于24
     * @return
     * @throws Exception
     * @author: qigui.su
     */
    public static String encode(String plainText, String key) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64.encodeToString(encryptData, Base64.DEFAULT);
    }

    /**
     * Title: MD5加密 生成32位md5�?
     *
     * @param inStr
     * @return 返回32位md5�?
     * @throws Exception
     */
    public static String md5Encode(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        StringBuilder hexValue = new StringBuilder();
        try {
            byte[] byteArray = inStr.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);
            for (byte md5Byte : md5Bytes) {
                int val = ((int) md5Byte) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        return hexValue.toString();
    }
}
