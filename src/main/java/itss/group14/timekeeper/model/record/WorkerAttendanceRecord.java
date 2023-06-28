package itss.group14.timekeeper.model.record;

import java.sql.Time;
import java.util.Date;

public class WorkerAttendanceRecord extends AttendanceRecord {
    double shiftHours1;
    double shiftHours2;
    double shiftHours3;
    public WorkerAttendanceRecord(String employeeId, Date date, Time startTime, Time endTime) {
        super(employeeId, date, startTime, endTime);
    }

}
