package itss.group14.timekeeper.model.record;

import java.sql.Time;

public class AttendanceRecord {

    private String employeeId;
    private String date;

    public AttendanceRecord(String employeeId, String date) {
        this.employeeId = employeeId;
        this.date = date;
    }
}
