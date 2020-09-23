package com.sfb.baselib.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * App工具
 */
public class AppTool {

    private static final String TAG = "AppTool";
    private static final String KEY_TEXT = "Z5CHaG3Cz7GJApHAn8ol2A==";

    /**
     * 获取 MD5
     *
     * @param val
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5(String val) throws NoSuchAlgorithmException {
        String s = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(val.getBytes());
        byte tmp[] = md5.digest();
        char str[] = new char[16 * 2];
        int k = 0;
        for (int i = 0; i < 16; i++) {
            byte byte0 = tmp[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];

            str[k++] = hexDigits[byte0 & 0xf];
        }
        s = new String(str);
        return s;
    }

    /**
     * 加密字符串
     *
     * @param originText 原始字符串
     * @return 加密后的字符串
     */
    public static String encryptText(String originText) {
        String cipherText = null;
        if (!TextUtils.isEmpty(originText)) {
            try {
                cipherText = new String(getCipherText(originText), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return cipherText;
        }
        return cipherText;
    }

    /**
     * 解密字符串
     *
     * @param decryptText 加密字符串
     * @return 解密后的字符串
     */
    public static String decryptText(String decryptText) {
        try {
            byte[] decryptByte = GuardBase64.decodeBase64(decryptText.getBytes());

            byte[] bytes = GuardBase64.decodeBase64(KEY_TEXT.getBytes());
            SecretKeySpec keySpec = new SecretKeySpec(bytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            String decipherText = "";
            try {
                decipherText = new String(cipher.doFinal(decryptByte), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return decipherText;
        } catch (Exception e) {
            LogUtils.logException2File(TAG, e);
        }
        return null;
    }

    /**
     * @param originText 原始字符串
     * @return 加密后的字符数组
     */
    private static byte[] getCipherText(String originText) {
        try {
            byte[] bytes = GuardBase64.decodeBase64(KEY_TEXT.getBytes());
            SecretKeySpec keySpec = new SecretKeySpec(bytes, "AES");
            byte[] cipherText = encrypt(originText.getBytes(), keySpec);
            return org.apache.commons.codec.binary.Base64.encodeBase64(cipherText);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @param orginBytes
     * @param key
     * @return
     */
    private static byte[] encrypt(byte[] orginBytes, Key key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        try {
            Cipher cipher;
            try {
                cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                return cipher.doFinal(orginBytes);
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            LogUtils.e(TAG, "the encrypt algorithm no found", e);
        }
        return null;
    }

    public static String getDuration(long time) {
        StringBuffer sb = new StringBuffer();
        time = time / 1000;
        int offset = 1;
        //if time duration large than one day
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time >= 24 * 60 * 60) {
            int day = (int) (time / (24 * 3600));
            sb.append(day).append("天");
            offset = 24 * 3600;
            hour = (int) (time % offset / 3600);
            minute = (int) (time % offset % 3600 / 60);
            second = (int) (time % offset % 3600 % 60);
        } else {
            hour = (int) (time / 3600);
            minute = (int) (time % 3600 / 60);
            second = (int) (time % 3600 % 60);
        }
        if (hour > 9) {
            sb.append(hour).append(":");
        } else {
            sb.append(0).append(hour).append(":");
        }
        if (minute > 9) {
            sb.append(minute).append(":");
        } else {
            sb.append(0).append(minute).append(":");
        }
        if (second > 9) {
            sb.append(second);
        } else {
            sb.append(0).append(second);
        }
        return sb.toString();
    }

    /**
     * 分离String
     */
    public static ArrayList<String> splitString(String str, String format) {
        ArrayList<String> innerUrl = new ArrayList<String>();
        if (TextUtils.isEmpty(str)) {
            return innerUrl;
        }
        String[] array = str.split(format);
        for (int i = 0; i < array.length; i++) {
            innerUrl.add(array[i]);
        }
        return innerUrl;
    }

    public static void checkPermission(final Context context, final String errorMsg, String[] permissionArray, final Runnable completeRun,
                                       final Runnable errorRun) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TedPermission.with(context).setPermissionListener(new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    if (completeRun != null) {
                        completeRun.run();
                    }
                }

                @Override
                public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    //Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                    if (errorRun != null) {
                        errorRun.run();
                    }
                }
            }).setPermissions(permissionArray).check();
        } else {
            if (completeRun != null) {
                completeRun.run();
            }
        }
    }
}
