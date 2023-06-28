package itss.group14.timekeeper.model;

public class Request {
    private Employee employee;
    private String date;
    private String status;
    private String reason;
    private String id;

    public Request(Employee employee, String date, String status, String reason) {
        this.employee = employee;
        this.date = date;
        this.status = status;
        this.reason = reason;
    }

    public Request(Employee employee, String date, String reason) {
        this.employee = employee;
        this.date = date;
        this.reason = reason;
    }

    public Request(String id, String name, String department, String date, String status) {
        this.id = id;
        this.employee = new Employee(id, name, department);
        this.date = date;
        this.status = status;
    }

    public Request(String id, String name, String department, String date, String status, String reason) {
        this.id = id;
        this.employee = new Employee(id, name, department);
        this.date = date;
        this.status = status;
        this.reason = reason;
    }
    public String getEmployeeId() {
        return employee.getId();
    }

    public String getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String toString() {
        return "Request{" +
                "employee=" + employee +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
