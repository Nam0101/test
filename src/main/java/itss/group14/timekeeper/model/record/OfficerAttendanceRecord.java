package itss.group14.timekeeper.model.record;

import java.sql.Time;

public class OfficerAttendanceRecord extends AttendanceRecord {
    private boolean morningSession;
    private boolean afternoonSession;
    private double hoursLate;
    private double hoursEarlyLeave;
    public OfficerAttendanceRecord(String employeeId, String date,boolean morningSession, boolean afternoonSession, double hoursLate, double hoursEarlyLeave) {
        super(employeeId, date);
        this.morningSession = morningSession;
        this.afternoonSession = afternoonSession;
        this.hoursLate = hoursLate;
        this.hoursEarlyLeave = hoursEarlyLeave;
    }

    public boolean isMorningSession() {
        return morningSession;
    }

    public boolean isAfternoonSession() {
        return afternoonSession;
    }

    public double getHoursLate() {
        return hoursLate;
    }

    public double getHoursEarlyLeave() {
        return hoursEarlyLeave;
    }
}
