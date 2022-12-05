package com.wxm.msfast.base.common.utils;

import cn.hutool.core.util.StrUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtils {


    public static boolean isNumber(String s) {
        if (StrUtil.isBlank(s)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("[0-9]+");
            Matcher matcher = pattern.matcher(s);
            return matcher.matches();
        }
    }
}
