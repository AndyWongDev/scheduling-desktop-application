package utils;

import javafx.scene.control.Alert;
import model.Appointment;

import java.sql.Timestamp;
import java.time.*;

public class TimezoneUtil {

    static public Timestamp getUTCTime() {
        return Timestamp.from(Instant.now());
    }

    /**
     * Creates valid Timestamps from LocalDate and String with concatenations
     *
     * @param localDate
     * @param localTime
     * @return Timestamp
     */
    static public Timestamp timestampFormatter(LocalDate localDate, String localTime) {
        LocalDateTime ldt = localDate.atTime(LocalTime.parse(localTime));
        return Timestamp.valueOf(ldt);
    }

    /**
     * Calculates system default Timestamp offset by the hour
     *
     * @return int offset
     */
    static public int getOffsetToLocalTime() {
        return ZonedDateTime.now().getOffset().getTotalSeconds() / (60 * 60);
    }

    /**
     * Maps Timestamps with and without offset for database management
     *
     * @param timestamp
     * @param offset
     * @return
     */
    static public Timestamp timestampWithOffset(Timestamp timestamp, int offset) {
        LocalDateTime ldt = timestamp.toLocalDateTime().plusHours(offset);
        return Timestamp.valueOf(ldt);
    }

    /**
     * Private Helper Function to check for Office Hours
     *
     * @param localTime
     * @return
     */
    static private Boolean checkTimestampRange(Timestamp localTime) {
        Timestamp utcTimestamp = timestampWithOffset(localTime, -getOffsetToLocalTime());
        Timestamp estTimestamp = timestampWithOffset(utcTimestamp, -5);
        return (estTimestamp.toLocalDateTime().getHour() >= 22 || estTimestamp.toLocalDateTime().getHour() < 8) ? false : true;
    }

    /**
     * Public Helper Function to check for Office Hours
     *
     * @param appointment
     * @return
     */
    static public Boolean isOfficeHours(Appointment appointment) {
        Boolean result = checkTimestampRange(appointment.getStart()) && checkTimestampRange((appointment.getEnd()));
        if (!result) {
            Warning.generateMessage("Outside of Working Hours! (8AM-10PM EST)", Alert.AlertType.ERROR);
        }
        return result;
    }
}
