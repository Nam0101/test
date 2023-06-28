package itss.group14.timekeeper.model.details;

import java.util.Date;

public class AttendanceReport {
//    employeeId: String
//fullName: String
//department: String
//month: Date (tháng báo cáo)
//totalWorkingHours: double (tổng số giờ làm việc)

    private String employeeId;
    private String fullName;
    private String department;
    private Date month;
    private double totalWorkingHours;

    public AttendanceReport(String employeeId, String fullName, String department, Date month, double totalWorkingHours) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.department = department;
        this.month = month;
        this.totalWorkingHours = totalWorkingHours;
    }
}
