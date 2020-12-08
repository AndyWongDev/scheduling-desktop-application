package utils;

import java.sql.Timestamp;
import java.time.*;

public class TimezoneConverter {
    static public Timestamp convertUTCtoLocal(Timestamp timestamp) {
        ZoneId offset = ZoneId.systemDefault();
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, offset);
        return Timestamp.from(zonedDateTime.toInstant());
    }

    static public Timestamp getUTCTime() {
        return Timestamp.from(Instant.now());
    }
}
