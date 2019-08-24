package com.unibs.zanotti.inforinvestigador.domain.utils;

import android.annotation.SuppressLint;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public abstract class DateUtils {
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

    /**
     * Return a string corresponding to the elapsed time between the two dates provided as argument. The time is
     * expressed in minutes, hours, days, months or years: if the time in minutes is greater than 60 then the time
     * is expressed in hours, but if the time in hours is greater than 24 then the time is expressed in days and so on.
     *
     * <p>If the elapsed time is less then one minute then the default string provided as argument is retured,
     * otherwise, if the default string is not provided, the elapsed time in second is returned.</p>
     *
     * @param dateFrom
     * @param dateTo
     * @param defaultMin If not null, return this string when the elapsed time is less then 1 minute
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String elapsedTime(LocalDateTime dateFrom, LocalDateTime dateTo, @Nullable String defaultMin) {
        long years = ChronoUnit.YEARS.between(dateFrom, dateTo);
        if (years > 0) {
            return String.format("%d y", years);
        }

        long months = ChronoUnit.MONTHS.between(dateFrom, dateTo);
        if (months > 0) {
            return String.format("%d m", months);
        }

        long days = ChronoUnit.DAYS.between(dateFrom, dateTo);
        if (days > 0) {
            return String.format("%d d", days);
        }

        long hours = ChronoUnit.HOURS.between(dateFrom, dateTo);
        if (hours > 0) {
            return String.format("%d h", hours);
        }

        long minutes = ChronoUnit.MINUTES.between(dateFrom, dateTo);
        if (minutes > 0) {
            return String.format("%d m", minutes);
        }

        return defaultMin == null ?
                String.format("%d s", ChronoUnit.SECONDS.between(dateFrom, dateTo)) :
                defaultMin;
    }
}
