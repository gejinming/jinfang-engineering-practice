package com.jinfang.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    /**
     * 默认随机数位数
     */
    private static final int RANDOM_CODE_SIZE = 5;

    public static String randomCode() {
        String[] digits = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
        for (int i = 0; i < digits.length; i++) {
            int index = Math.abs(ThreadLocalRandom.current().nextInt(10000)) % 10;
            String tmpDigit = digits[index];
            digits[index] = digits[i];
            digits[i] = tmpDigit;
        }

        StringBuilder returnStr = new StringBuilder(digits[0]);
        for (int i = 1; i < RANDOM_CODE_SIZE; i++) {
            returnStr.insert(0, digits[i]);
        }
        return returnStr.toString();
    }
}
