package itss.group14.timekeeper.model.export;

import itss.group14.timekeeper.model.Employee;

public class ExportInfor {
    private Employee employee;

    public ExportInfor(Employee employee) {
        this.employee = employee;
    }

    public String getName() {
        return employee.getName();
    }

    public String getDepartment() {
        return employee.getDepartment();
    }

    public String getEmployee_id() {
        return employee.getId();
    }
}
