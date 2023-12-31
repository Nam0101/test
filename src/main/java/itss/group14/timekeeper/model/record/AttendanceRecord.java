package itss.group14.timekeeper.model.record;

public class AttendanceRecord {

    private String employeeId;
    private String date;

    public AttendanceRecord(String employeeId, String date) {
        this.employeeId = employeeId;
        this.date = date;
    }

    public AttendanceRecord(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
