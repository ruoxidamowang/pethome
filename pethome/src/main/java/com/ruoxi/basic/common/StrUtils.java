package com.ruoxi.basic.common;

import java.util.concurrent.ThreadLocalRandom;

public class StrUtils {
    public static String getComplexRandomString(int n) {
        String str = "0123456789qwertyuiopasdfghjklmnbvcxz";
        StringBuffer sb = new StringBuffer();
        int length = str.length();
        for (int i = 0; i < n; i++) {
            sb.append(ThreadLocalRandom.current().nextInt(0,length));
        }
        return sb.toString();
    }
}
