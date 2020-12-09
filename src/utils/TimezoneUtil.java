package utils;

import java.sql.Timestamp;
import java.time.*;

public class TimezoneUtil {

    static public Timestamp getUTCTime() {
        return Timestamp.from(Instant.now());
    }

    static public Timestamp timestampFormatter(LocalDate localDate, String localTime) {
        LocalDateTime ldt = localDate.atTime(LocalTime.parse(localTime));
        return Timestamp.valueOf(ldt);
    }

    static public int getOffsetToLocalTime() {
        return ZonedDateTime.now().getOffset().getTotalSeconds()/(60*60);
    }

    static public Timestamp timestampWithOffset(Timestamp timestamp, int offset) {
        LocalDateTime ldt = timestamp.toLocalDateTime().plusHours(offset);
        return Timestamp.valueOf(ldt);
    }
}
