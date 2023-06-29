package itss.group14.timekeeper.model.record;

public class WorkerAttendanceRecord extends AttendanceRecord {
    double shiftHours1;
    double shiftHours2;
    double shiftHours3;

    public WorkerAttendanceRecord(String employeeId, String date) {
        super(employeeId, date);
    }

    public WorkerAttendanceRecord(String workerId, double shift1Hours, double shift2Hours, double shift3Hours) {
        super(workerId);
        this.shiftHours1 = shift1Hours;
        this.shiftHours2 = shift2Hours;
        this.shiftHours3 = shift3Hours;
    }

    public double getShiftHours1() {
        return shiftHours1;
    }

    public double getShiftHours2() {
        return shiftHours2;
    }

    public double getShiftHours3() {
        return shiftHours3;
    }

    public WorkerAttendanceRecord(String employeeId, String date, double shift1Hours, double shift2Hours, double shift3Hours) {
        super(employeeId, date);
        this.shiftHours1 = shift1Hours;
        this.shiftHours2 = shift2Hours;
        this.shiftHours3 = shift3Hours;
    }
}
