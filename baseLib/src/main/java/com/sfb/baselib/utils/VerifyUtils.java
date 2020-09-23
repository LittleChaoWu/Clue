package com.sfb.baselib.utils;

import android.text.TextUtils;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 各种验证的工具类
 */
public class VerifyUtils {

    /**
     * 是否是合法的密码（数字加字母或符号组成）
     */
    public static boolean isValidPwd(String password) {
        String pwdRegex = ".*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]";
        Pattern pattern = Pattern.compile(pwdRegex);
        Matcher isNum = pattern.matcher(password);
        if (!isNum.matches()) {
            return false;
        }
        if (password.length() < 6 || password.length() > 20) {
            return false;
        }
        return true;
    }

    /**
     * 是否全是数字
     */
    public static boolean isDigit(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String pattern = "\\d*";
        return str.matches(pattern);
    }

    /**
     * 是否是合法手机号码
     */
    public static boolean isPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        String phoneRegex = "^((\\+86)?(86)?)\\s*(1[3456789])\\d{9}$";
        return phone.matches(phoneRegex);
    }

    /**
     * 是否是合法的电子邮箱
     */
    public static boolean isEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        String strPattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 是否合法的车牌号码
     */
    public static boolean isCarPlate(String carString) {
        if (TextUtils.isEmpty(carString)) {
            return false;
        }
        String carNumberRegex = "[\u4e00-\u9fa5]{1}(?i)[A-Z]{1}(?i)[A-Z_0-9]{5}";
        String specialRegex = "[\u4e00-\u9fa5]{1}(?i)[A-Z]{1}(?i)[A-Z_0-9]{4}[\u4e00-\u9fa5]{1}";
        return carString.matches(carNumberRegex) || carString.matches(specialRegex);
    }

    /**
     * 判断是否是网页链接
     *
     * @param url String
     * @return boolean
     */
    public static boolean isWebUrl(String url) {
        final String TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL = "(?:"
                + "(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])"
                + "|(?:biz|b[abdefghijmnorstvwyz])"
                + "|(?:cat|com|coop|c[acdfghiklmnoruvxyz])"
                + "|d[ejkmoz]"
                + "|(?:edu|e[cegrstu])"
                + "|f[ijkmor]"
                + "|(?:gov|g[abdefghilmnpqrstuwy])"
                + "|h[kmnrtu]"
                + "|(?:info|int|i[delmnoqrst])"
                + "|(?:jobs|j[emop])"
                + "|k[eghimnprwyz]"
                + "|l[abcikrstuvy]"
                + "|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])"
                + "|(?:name|net|n[acefgilopruz])"
                + "|(?:org|om)"
                + "|(?:pro|p[aefghklmnrstwy])"
                + "|qa"
                + "|r[eosuw]"
                + "|s[abcdeghijklmnortuvyz]"
                + "|(?:tel|travel|t[cdfghjklmnoprtvwz])"
                + "|u[agksyz]"
                + "|v[aceginu]"
                + "|w[fs]"
                + "|(?:xn\\-\\-0zwm56d|xn\\-\\-11b5bs3a9aj6g|xn\\-\\-80akhbyknj4f|xn\\-\\-9t4b11yi5a|xn\\-\\-deba0ad|xn\\-\\-g6w251d|xn\\-\\-hgbk6aj7f53bba|xn\\-\\-hlcj6aya9esc7a|xn\\-\\-jxalpdlp|xn\\-\\-kgbechtv|xn\\-\\-zckzah)"
                + "|y[etu]" + "|z[amw]))";
        final String GOOD_IRI_CHAR = "a-zA-Z0-9\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF";
        final Pattern WEB_URL = Pattern.compile("((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)"
                + "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_"
                + "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?"
                + "((?:(?:["
                + GOOD_IRI_CHAR
                + "]["
                + GOOD_IRI_CHAR
                + "\\-]{0,64}\\.)+" // named host
                + TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL
                + "|(?:(?:25[0-5]|2[0-4]" // or ip address
                + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]"
                + "|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]"
                + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                + "|[1-9][0-9]|[0-9])))"
                + "(?:\\:\\d{1,5})?)" // plus option port number
                + "(\\/(?:(?:["
                + GOOD_IRI_CHAR
                + "\\;\\/\\?\\:\\@\\&\\=\\#\\~" // plus option query params
                + "\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?"
                + "(?:\\b|$)");
        String lowerUrl = url.toLowerCase();
        if (WEB_URL.matcher(lowerUrl).matches()) {
            return true;
        }
        return false;
    }

    public static boolean isSepicalPhone() {
        boolean isSpecialHandle = false;
        if ("HUAWEI".equalsIgnoreCase(DeviceUtils.getManufacturer())) {
            if ("MT7-TL00".equalsIgnoreCase(DeviceUtils.getPhoneModel())) {
                isSpecialHandle = true;
            }
        }
        if ("HUAWEI".equalsIgnoreCase(DeviceUtils.getManufacturer())) {
            if ("Note 2".equalsIgnoreCase(DeviceUtils.getPhoneModel())) {
                isSpecialHandle = true;
            }
        }
        return isSpecialHandle;
    }

    /**
     * 判断是否是全数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 是否是合法身份证
     */
    public static boolean isIDCardNumber(String IDStr) {
        if (TextUtils.isEmpty(IDStr)) {
            return false;
        }
        IDStr = IDStr.toLowerCase();
        String Ai = "";
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            return false;
        }
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        //Ai = IDStr;
        if (!isNumeric(Ai)) {
            return false;
        }
        Ai += IDStr.substring(IDStr.length() - 1, IDStr.length());
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            return false;
        }
        if (IDStr.length() == 18) {
            if (!Ai.equals(IDStr)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    private static Hashtable GetAreaCode() {
        Hashtable<String, String> hashTable = new Hashtable<>();
        hashTable.put("11", "北京");
        hashTable.put("12", "天津");
        hashTable.put("13", "河北");
        hashTable.put("14", "山西");
        hashTable.put("15", "内蒙古");
        hashTable.put("21", "辽宁");
        hashTable.put("22", "吉林");
        hashTable.put("23", "黑龙江");
        hashTable.put("31", "上海");
        hashTable.put("32", "江苏");
        hashTable.put("33", "浙江");
        hashTable.put("34", "安徽");
        hashTable.put("35", "福建");
        hashTable.put("36", "江西");
        hashTable.put("37", "山东");
        hashTable.put("41", "河南");
        hashTable.put("42", "湖北");
        hashTable.put("43", "湖南");
        hashTable.put("44", "广东");
        hashTable.put("45", "广西");
        hashTable.put("46", "海南");
        hashTable.put("50", "重庆");
        hashTable.put("51", "四川");
        hashTable.put("52", "贵州");
        hashTable.put("53", "云南");
        hashTable.put("54", "西藏");
        hashTable.put("61", "陕西");
        hashTable.put("62", "甘肃");
        hashTable.put("63", "青海");
        hashTable.put("64", "宁夏");
        hashTable.put("65", "新疆");
        hashTable.put("71", "台湾");
        hashTable.put("81", "香港");
        hashTable.put("82", "澳门");
        hashTable.put("91", "国外");
        return hashTable;
    }
}
