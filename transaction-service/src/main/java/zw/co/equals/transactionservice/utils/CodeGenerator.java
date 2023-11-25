package zw.co.equals.transactionservice.utils;

import java.util.concurrent.ThreadLocalRandom;

public class CodeGenerator {
    public static String generateId(String prefix, int index) {
        int[] arr = ThreadLocalRandom
                .current()
                .ints(0, 1000000)
                .distinct()
                .limit(10)
                .toArray();
        String code = prefix + "-" + arr[index];

        System.out.println("TransRef generated -> " + code);
        return code;
    }
}
