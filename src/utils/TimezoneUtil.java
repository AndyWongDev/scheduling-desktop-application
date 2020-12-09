package utils;

import javafx.scene.control.Alert;
import model.Appointment;

import java.sql.Timestamp;
import java.time.*;
import java.util.Date;

public class TimezoneUtil {

    static public Timestamp getUTCTime() {
        return Timestamp.from(Instant.now());
    }

    static public Timestamp timestampFormatter(LocalDate localDate, String localTime) {
        LocalDateTime ldt = localDate.atTime(LocalTime.parse(localTime));
        return Timestamp.valueOf(ldt);
    }

    static public int getOffsetToLocalTime() {
        return ZonedDateTime.now().getOffset().getTotalSeconds() / (60 * 60);
    }

    static public Timestamp timestampWithOffset(Timestamp timestamp, int offset) {
        LocalDateTime ldt = timestamp.toLocalDateTime().plusHours(offset);
        return Timestamp.valueOf(ldt);
    }

    static private Boolean checkTimestampRange(Timestamp localTime) {
        Timestamp utcTimestamp = timestampWithOffset(localTime, -getOffsetToLocalTime());
        Timestamp estTimestamp = timestampWithOffset(utcTimestamp, -5);
        return (estTimestamp.toLocalDateTime().getHour() >= 22 || estTimestamp.toLocalDateTime().getHour() < 8) ? false : true;
    }

    static public Boolean isOfficeHours(Appointment appointment) {
        Boolean result = checkTimestampRange(appointment.getStart()) && checkTimestampRange((appointment.getEnd()));
        if (!result) {
            Warning.generateMessage("Outside of Working Hours! (8AM-10PM EST)", Alert.AlertType.ERROR);
        }
        return result;
    }
}
