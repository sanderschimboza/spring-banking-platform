package zw.co.equals.transactionservice.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class DateFormatter {
    public static LocalDate formatFromString(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate localDate = LocalDate.parse(date, formatter);
        log.info(localDate.toString());
        return localDate;

    }

    @SneakyThrows
    public static Date format(Date date) {
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sm.format(date);
        return sm.parse(strDate);
    }
}
