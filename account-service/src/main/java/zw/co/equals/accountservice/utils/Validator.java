package zw.co.equals.accountservice.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean validateAccount(String accountNumber) {
        String regex = "^[0-9]{9,18}$";
        Pattern p = Pattern.compile(regex);
        if (accountNumber == null) {
            return false;
        }
        Matcher m = p.matcher(accountNumber);
        return m.matches();
    }
}
