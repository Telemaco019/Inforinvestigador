package com.unibs.zanotti.inforinvestigador.domain.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public abstract class DateUtils {
    /**
     * @param epochTimestamp expressed in seconds
     * @return
     */
    public static LocalDateTime fromEpochTimestampSec(long epochTimestamp) {
        return Instant.ofEpochSecond(epochTimestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * @param epochTimestamp expressed in milliseconds
     * @return
     */
    public static LocalDateTime fromEpochTimestampMillis(long epochTimestamp) {
        return Instant.ofEpochMilli(epochTimestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static long fromLocalDateTimeToEpochMills(LocalDateTime dateTime) {
        return dateTime.toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
    }
}
