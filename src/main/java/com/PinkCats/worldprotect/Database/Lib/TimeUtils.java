package com.PinkCats.worldprotect.Database.Lib;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类：包含获取时间戳、时间戳转日期的静态方法
 */
public class TimeUtils {
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    public static int getCurrentTimestamp() {
        // 获取当前秒级时间戳（将毫秒级时间戳除以1000）
        return (int) (Instant.now().toEpochMilli() / 1000);
    }

    public static String timestampToDate(long timestamp) {
        return timestampToDate(timestamp, DEFAULT_DATE_FORMAT);
    }


    public static String timestampToDate(long timestamp, String format) {
        if (timestamp < 0 || (String.valueOf(timestamp).length() != 13)) {
            throw new IllegalArgumentException("请传入合法的13位毫秒级时间戳");
        }
        if (format == null || format.trim().isEmpty()) {
            format = DEFAULT_DATE_FORMAT;
        }
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), DEFAULT_ZONE_ID);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }

}