package utils;

import java.sql.Timestamp;
import java.time.*;


public class TimezoneConverter {

    static public Timestamp getUTCTime() {
        return Timestamp.from(Instant.now());
    }

    static public Timestamp timestampFormatter(LocalDate localDate, String localTime) {
        LocalDateTime ldt = localDate.atTime(LocalTime.parse(localTime));
        return Timestamp.valueOf(ldt);
    }
}
