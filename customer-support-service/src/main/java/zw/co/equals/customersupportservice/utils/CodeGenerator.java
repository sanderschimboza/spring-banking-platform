package zw.co.equals.customersupportservice.utils;

import java.util.concurrent.ThreadLocalRandom;

public class CodeGenerator {
    public static Long generateId(int index) {
        int[] arr = ThreadLocalRandom
                .current()
                .ints(0, 1000000)
                .distinct()
                .limit(10)
                .toArray();
        long code = arr[index];

        System.out.println("TransRef generated -> " + code);
        return code;
    }
}
