package itss.group14.timekeeper.model.record;

import java.sql.Time;

public class WorkerAttendanceRecord extends AttendanceRecord {
    double shiftHours1;
    double shiftHours2;
    double shiftHours3;

    public WorkerAttendanceRecord(String employeeId, String date, Time startTime, Time endTime) {
        super(employeeId, date, startTime, endTime);
    }


    public WorkerAttendanceRecord(String employeeId, String date, double shift1Hours, double shift2Hours, double shift3Hours) {
        super(employeeId, date, null, null);
        this.shiftHours1 = shift1Hours;
        this.shiftHours2 = shift2Hours;
        this.shiftHours3 = shift3Hours;
    }
}
