package itss.group14.timekeeper.model.export;

import itss.group14.timekeeper.model.Employee;

public class OfficerExportInfor extends ExportInfor {
    private int totalSessions;
    private double totalHoursLate;
    private double totalHoursEarly;
    private String month;

    public OfficerExportInfor(Employee employee) {
        super(employee);
    }

    public OfficerExportInfor(Employee employee, int totalSessions, double totalHoursLate, double totalHoursEarly) {
        super(employee);
        this.totalSessions = totalSessions;
        this.totalHoursLate = totalHoursLate;
        this.totalHoursEarly = totalHoursEarly;
    }

    public OfficerExportInfor(Employee employee, int totalSessions, double totalHoursLate, double totalHoursEarly, String month) {
        super(employee);
        this.totalSessions = totalSessions;
        this.totalHoursLate = totalHoursLate;
        this.totalHoursEarly = totalHoursEarly;
        this.month = month;
    }

    public int getTotalSessions() {
        return totalSessions;
    }

    public double getTotalHoursLate() {
        return totalHoursLate;
    }

    public String getemployeeId() {
        return super.getEmployee_id();
    }

    public double getTotalHoursEarly() {
        return totalHoursEarly;
    }

    public String getMonth() {
        return month;
    }
}
