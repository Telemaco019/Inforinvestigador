package com.unibs.zanotti.inforinvestigador.domain.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public abstract class DateUtils {
    /**
     * @param instantTimestamp expressed in seconds
     * @return
     */
    public static LocalDateTime fromInstantTimestamp(long instantTimestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(instantTimestamp),
                TimeZone.getDefault().toZoneId());
    }
}
