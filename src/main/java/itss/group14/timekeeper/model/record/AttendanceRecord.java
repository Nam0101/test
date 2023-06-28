package itss.group14.timekeeper.model.record;

import java.sql.Time;
import java.util.Date;

public class AttendanceRecord {


    private String employeeId;
    private Date date;
    private Time startTime;
    private Time endTime;

    public AttendanceRecord(String employeeId, Date date, Time startTime, Time endTime) {
        this.employeeId = employeeId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
