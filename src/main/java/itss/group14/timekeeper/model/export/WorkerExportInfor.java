package itss.group14.timekeeper.model.export;

import itss.group14.timekeeper.model.Employee;

public class WorkerExportInfor extends ExportInfor {
    private double workingTime;
    private double overtime;
    private String month;

    public WorkerExportInfor(Employee employee) {
        super(employee);
    }

    public WorkerExportInfor(Employee employee, double workingTime, double overtime) {
        super(employee);
        this.workingTime = workingTime;
        this.overtime = overtime;
    }

    public WorkerExportInfor(Employee employee, double workingTime, double overtime, String month) {
        super(employee);
        this.workingTime = workingTime;
        this.overtime = overtime;
        this.month = month;
    }

    public double getWorkingTime() {
        return workingTime;
    }

    public double getOvertime() {
        return overtime;
    }

    public String getMonth() {
        return month;
    }
}
