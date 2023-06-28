package itss.group14.timekeeper.model.record;

import java.sql.Time;

public class AttendanceRecord {

    private String employeeId;
    private String date;
    private Time startTime;
    private Time endTime;

    public AttendanceRecord(String employeeId, String date, Time startTime, Time endTime) {
        this.employeeId = employeeId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
