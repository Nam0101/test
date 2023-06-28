package itss.group14.timekeeper.model.record;

import java.sql.Time;
import java.util.Date;

public class OfficerAttendanceRecord extends AttendanceRecord {
    private boolean morningSession;
    private boolean afternoonSession;
    private double hoursLate;
    private double hoursEarlyLeave;
    OfficerAttendanceRecord(String employeeId, Date date, Time startTime, Time endTime) {
        super(employeeId, date, startTime, endTime);
    }

    public OfficerAttendanceRecord(String employeeId, Date date, Time startTime, Time endTime, boolean morningSession, boolean afternoonSession, double hoursLate, double hoursEarlyLeave) {
        super(employeeId, date, startTime, endTime);
        this.morningSession = morningSession;
        this.afternoonSession = afternoonSession;
        this.hoursLate = hoursLate;
        this.hoursEarlyLeave = hoursEarlyLeave;
    }
}
